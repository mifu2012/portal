package com.infosmart.portal.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.portal.dao.MyBatisDao;
import com.infosmart.portal.dialect.DB2Dialect;
import com.infosmart.portal.dialect.Dialect;
import com.infosmart.portal.dialect.MySQLDialect;
import com.infosmart.portal.dialect.OracleDialect;
import com.infosmart.portal.dialect.PostgreSQLDialect;
import com.infosmart.portal.dialect.SQLServer2005Dialect;
import com.infosmart.portal.dialect.SQLServerDialect;
import com.infosmart.portal.dialect.SybaseDialect;
import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasStKpiData;
import com.infosmart.portal.pojo.report.ReportDataSource;
import com.infosmart.portal.pojo.report.ReportDesign;
import com.infosmart.portal.util.Const;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.InterpreterUtil;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.report.PoolManager;

@Service
public class BaseServiceImpl {
	@Autowired
	protected MyBatisDao myBatisDao;

	protected final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 按指标的计算规则统计该指标的一定时间范围的数据
	 * 
	 * @param dwpasCKpiInfo
	 * @param reportBeginDate
	 * @param reportEndDate
	 * @return
	 */
	protected List<DwpasStKpiData> caculateRuleValue(
			DwpasCKpiInfo dwpasCKpiInfo, String reportBeginDate,
			String reportEndDate) {
		this.logger.info("按规则计算指标数据");
		if (dwpasCKpiInfo == null) {
			this.logger.warn("错误的参数");
			return null;
		}
		if (!"1".equals(dwpasCKpiInfo.getIsCalKpi())) {
			// 不需要计算
			this.logger.warn(dwpasCKpiInfo.getKpiCode() + "不需要计算");
			return null;
		}
		// 指标类型
		int kpiType = Integer.parseInt(dwpasCKpiInfo.getKpiType());
		// 如果没有结束日期
		if (!StringUtils.notNullAndSpace(reportEndDate)) {
			// 默认是今天
			if (kpiType == DwpasCKpiInfo.KPI_TYPE_OF_MONTH) {
				reportEndDate = DateUtils.formatUtilDate(
						DateUtils.getPreviousDate(new Date()), "yyyyMM");
			} else {
				reportEndDate = DateUtils.formatUtilDate(
						DateUtils.getPreviousDate(new Date()), "yyyyMMdd");
			}
		}
		// 数据类型
		String dateType = DwpasStKpiData.DATE_TYPE_OF_MONTH;
		if (kpiType == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
			dateType = DwpasStKpiData.DATE_TYPE_OF_DAY;
		} else if (kpiType == DwpasCKpiInfo.KPI_TYPE_OF_WEEK) {
			dateType = DwpasStKpiData.DATE_TYPE_OF_WEEK;
		}
		// 计算规则
		String ruleFormula = dwpasCKpiInfo.getRoleFormula();
		this.logger.debug("计算规则:" + ruleFormula);
		if (!StringUtils.notNullAndSpace(ruleFormula)) {
			this.logger.warn("没有计算规则");
			// 没有计算规则
			return null;
		}
		// 计算规则涉及的指标
		List<String> kpiCodeList = new ArrayList<String>();
		int st = 0, ed = 0;
		String kpiCode = "";
		for (int i = 0; i < ruleFormula.length(); i++) {
			String tmp = ruleFormula.substring(i, i + 1);
			if (tmp.equals("[")) {
				// 该位�??'[', 标记�??�??
				st = i + 1;
			} else if (tmp.equals("]")) {
				ed = i;
				kpiCode = ruleFormula.substring(st, ed);
				kpiCodeList.add(kpiCode);
			}
		}
		// 查询数据
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("kpiCodes", kpiCodeList);
		parameterMap.put("dateType", dateType);
		// 开始计算日期
		if (StringUtils.notNullAndSpace(reportBeginDate)) {
			parameterMap.put("reportBeginDate",
					StringUtils.replace(reportBeginDate, "-", ""));
		}
		// 结束计算日期
		if (StringUtils.notNullAndSpace(reportEndDate)) {
			parameterMap.put("reportEndDate",
					StringUtils.replace(reportEndDate, "-", ""));
		}
		// 查询某时间段的记录，数据已按日期排序
		List<DwpasStKpiData> kpiDataList = this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasStKpiDataMapper.queryKpiDataAndKpiInfoBetweenDateByMultiKpi",
						parameterMap);
		if (kpiDataList == null || kpiDataList.isEmpty()) {
			// 没有数据
			this.logger.warn("没有数据");
			return null;
		}
		// 指标数据集合
		Map<String, DwpasStKpiData> kpiDataMap = new HashMap<String, DwpasStKpiData>();
		for (DwpasStKpiData kpiData : kpiDataList) {
			kpiDataMap.put(
					kpiData.getKpiCode() + "_" + kpiData.getReportDate(),
					kpiData);
		}
		// 如果没有开始时间,则默认开始时间为最早的记录时间
		if (!StringUtils.notNullAndSpace(reportBeginDate)) {
			reportBeginDate = kpiDataList.get(0).getReportDate();
		}
		// 如果没有结束日期，则默认为记录最晚的时间
		if (!StringUtils.notNullAndSpace(reportEndDate)) {
			reportEndDate = kpiDataList.get(kpiDataList.size() - 1)
					.getReportDate();
		}
		String dateFormat = "yyyyMMdd";
		if (kpiType == DwpasCKpiInfo.KPI_TYPE_OF_MONTH) {
			dateFormat = "yyyyMM";
		}
		// 开始日期
		Date startDate = DateUtils.parseByFormatRule(
				StringUtils.replace(reportBeginDate, "-", ""), dateFormat);
		// 结束日期
		Date endDate = DateUtils.parseByFormatRule(
				StringUtils.replace(reportEndDate, "-", ""), dateFormat);
		// 计算的指标的数据列表
		List<DwpasStKpiData> stKpiDataList = new ArrayList<DwpasStKpiData>();
		//
		DwpasStKpiData tmpKpiData = null;
		while (startDate.getTime() <= endDate.getTime()) {
			ruleFormula = dwpasCKpiInfo.getRoleFormula();
			for (String tempKpiCode : kpiCodeList) {
				tmpKpiData = kpiDataMap.get(tempKpiCode + "_"
						+ DateUtils.formatUtilDate(startDate, dateFormat));
				if (tmpKpiData == null) {
					ruleFormula = StringUtils.replace(ruleFormula, "["
							+ tempKpiCode + "]", "0");
				} else {
					ruleFormula = StringUtils.replace(ruleFormula, "["
							+ tempKpiCode + "]", String.valueOf(tmpKpiData
							.getBaseValue().doubleValue()));
				}
			}
			// this.logger.info("计算规则转换后：" + ruleFormula);
			//String showValue = Engine.playExpression(ruleFormula);
			Object returnVal = InterpreterUtil.eval(ruleFormula);
			String showValue = returnVal == null ? "0" : returnVal.toString();
			// this.logger.info("根据规则计算值:" + showValue);
			// 当前指标的数据
			DwpasStKpiData kpiData = new DwpasStKpiData();
			try {
				kpiData.setBaseValue(new BigDecimal(showValue));
			} catch (NumberFormatException e) {
				this.logger.warn("计算指标数据业务处理异常：" + e.getMessage(), e);
				kpiData.setBaseValue(new BigDecimal(0));
			}
			kpiData.setAgeValue(new BigDecimal(0));
			kpiData.setMaxValue(new BigDecimal(0));
			kpiData.setMinValue(new BigDecimal(0));
			kpiData.setPerValue(new BigDecimal(0));
			kpiData.setDateType(dateType);
			kpiData.setDwpasCKpiInfo(dwpasCKpiInfo);
			kpiData.setGmtCreate(startDate);
			kpiData.setKpiCode(dwpasCKpiInfo.getKpiCode());
			kpiData.setKpiName(dwpasCKpiInfo.getKpiName());
			kpiData.setReportDate(DateUtils.formatUtilDate(startDate,
					dateFormat));
			kpiData.setUnit(dwpasCKpiInfo.getUnit());
			// add
			stKpiDataList.add(kpiData);
			// 下个时间
			if (kpiType == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
				// 下一个日期
				startDate = DateUtils.getNextDate(startDate);
			} else if (kpiType == DwpasCKpiInfo.KPI_TYPE_OF_WEEK) {
				// 下周的日期
				startDate = DateUtils.getNextWeek(startDate);
			} else {
				// 下一个月份
				startDate = DateUtils.getNextMonth(startDate);
			}
		}
		return stKpiDataList;
	}

	/**
	 * 按规则计算某指标KPI某天的数据
	 * 
	 * @param dwpasCKpiInfo
	 * @param reportDate
	 * @return
	 */
	protected DwpasStKpiData caculateRuleValue(DwpasCKpiInfo dwpasCKpiInfo,
			String reportDate) {
		List<DwpasStKpiData> kpiDataList = this.caculateRuleValue(
				dwpasCKpiInfo, reportDate, reportDate);
		return kpiDataList == null || kpiDataList.isEmpty() ? new DwpasStKpiData()
				: kpiDataList.get(0);
	}

	/**
	 * 
	 * @param dataSourceId
	 * @return
	 */
	public PoolManager getPoolManagerByDsId(String dataSourceId) {
		if (Const.CONNECTION_POOL_MAP.containsKey(dataSourceId)) {
			return Const.CONNECTION_POOL_MAP.get(dataSourceId);
		} else {
			// 查询数据库信息，初始化连接池
			// ReportDataSource dataSource = new ReportDataSource();
			// dataSource.setId(Integer.valueOf(dataSourceId));
			ReportDataSource reportDataSource = this.myBatisDao
					.get("com.infosmart.mapper.ReportDesignMapper.getDataSourceById",
							Integer.valueOf(dataSourceId));
			PoolManager poolManager = new PoolManager(reportDataSource);
			Const.CONNECTION_POOL_MAP.put(dataSourceId, poolManager);
			return poolManager;
		}
	}

	/**
	 * 获取不同数据库下的分页sql
	 * 
	 * @param reportInfo
	 * @param dataSource
	 * @param pageNo
	 * @return
	 */
	public String getNewQuerySql(ReportDesign reportInfo,
			ReportDataSource dataSource, String newQuerySql, int pageNo) {
		Dialect dialect = null;
		// String newQuerySql="";
		if (reportInfo.getPageSize() > 0) {
			switch (dataSource.getDbType()) {
			case ReportDataSource.DB_TYPE_MYSQL: {
				dialect = new MySQLDialect();
				break;
			}
			case ReportDataSource.DB_TYPE_ORACLE: {
				dialect = new OracleDialect();
				break;
			}
			case ReportDataSource.DB_TYPE_DB2: {
				dialect = new DB2Dialect();
				break;
			}
			case ReportDataSource.DB_TYPE_POSTGRESQL: {
				dialect = new PostgreSQLDialect();
				break;
			}
			case ReportDataSource.DB_TYPE_SQLSERVER_2000: {
				dialect = new SQLServerDialect();
				break;
			}
			case ReportDataSource.DB_TYPE_SQLSERVER_2005: {
				dialect = new SQLServer2005Dialect();
				break;
			}
			case ReportDataSource.DB_TYPE_SYBASE: {
				dialect = new SybaseDialect();
				break;
			}
			}
			newQuerySql = dialect.getLimitString(newQuerySql, (pageNo - 1)
					* reportInfo.getPageSize(), reportInfo.getPageSize());
		}
		return newQuerySql;
	}
}
