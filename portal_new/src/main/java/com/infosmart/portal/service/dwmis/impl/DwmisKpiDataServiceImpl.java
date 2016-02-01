package com.infosmart.portal.service.dwmis.impl;

import static java.util.Locale.CHINA;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasStKpiData;
import com.infosmart.portal.pojo.dwmis.DwmisKpiData;
import com.infosmart.portal.pojo.dwmis.DwmisKpiGoal;
import com.infosmart.portal.pojo.dwmis.DwmisKpiInfo;
import com.infosmart.portal.pojo.dwmis.DwmisMisDptmnt;
import com.infosmart.portal.service.dwmis.DwmisKpiDataService;
import com.infosmart.portal.service.dwmis.DwmisKpiInfoService;
import com.infosmart.portal.service.dwmis.KPIDataManager;
import com.infosmart.portal.service.dwmis.KPIGoalManager;
import com.infosmart.portal.service.dwmis.SysDateForFixedYear;
import com.infosmart.portal.service.impl.BaseServiceImpl;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.dwmis.CoreConstant;
import com.infosmart.portal.util.dwmis.DoubleUtility;
import com.infosmart.portal.util.dwmis.TimeFormatProcessor;
import com.infosmart.portal.util.dwmis.UnitTransformer;
import com.infosmart.portal.vo.dwmis.DwmisDeptKpiData;
import com.infosmart.portal.vo.dwmis.KPITrends;

@Service
public class DwmisKpiDataServiceImpl extends BaseServiceImpl implements
		DwmisKpiDataService {
	@Autowired
	private KPIDataManager kPIDataManager;

	@Autowired
	private KPIGoalManager kPIGoalManager;

	@Autowired
	private SysDateForFixedYear sysDateForFixedYear;

	@Autowired
	private DwmisKpiInfoService kpiInfoService;

	@Override
	public List<DwmisDeptKpiData> listDeptMonitorKpiData(
			List<DwmisMisDptmnt> deptInfoList, String queryDate) {
		this.logger.info("列出监控部门的KPI数据");
		if (deptInfoList == null || deptInfoList.isEmpty())
			return null;
		List<DwmisDeptKpiData> deptKpiDataList = new ArrayList<DwmisDeptKpiData>();
		Map<String, Double> kpiDataMap = null;
		Map<String, DwmisKpiGoal> kpiGoalMap = null;
		Map<String, Double> subKpiDataMap = null;
		Map<String, DwmisKpiGoal> subKpiGoalMap = null;
		List<DwmisKpiInfo> subKpiInfoList = null;
		for (DwmisMisDptmnt deptInfo : deptInfoList) {
			this.logger.info("查询部门的指标数据:" + deptInfo.getDepName());
			// 查询部门关联的指标及子指标
			List<DwmisKpiInfo> kpiIngfoList = this.kpiInfoService
					.listKpiInfoByDeptId(deptInfo.getDepId());
			if (kpiIngfoList == null || kpiIngfoList.isEmpty())
				continue;
			// 查询指标数据
			KPITrends kpiTreands = null;
			KPITrends subKpiTreands = null;
			// 查询指标数据值
			kpiDataMap = this.kPIDataManager.getMultiKpiFullValue(kpiIngfoList,
					queryDate);

			// 查询指标绩效考核值
			kpiGoalMap = kPIGoalManager.listKPIGoalOnCurrentYear(kpiIngfoList,
					queryDate);
			for (DwmisKpiInfo kpiInfo : kpiIngfoList) {
				if (kpiInfo == null || kpiInfo.getKpiCode() == null
						|| kpiInfo.getGoalType() == null)
					continue;
				this.logger.info("查询指标者数据:" + kpiInfo.getKpiCode());
				// 转换数据
				kpiTreands = transformToKpiTrends(kpiInfo, kpiDataMap,
						kpiGoalMap);
				if (kpiTreands == null)
					continue;
				// 子指标
				subKpiInfoList = kpiInfo.getSubKpiInfoList();
				if (subKpiInfoList != null && !subKpiInfoList.isEmpty()) {
					List<KPITrends> subKpiThreadsList = new ArrayList<KPITrends>();
					// 查询指标数据值
					subKpiDataMap = this.kPIDataManager.getMultiKpiFullValue(
							subKpiInfoList, queryDate);

					// 查询指标绩效考核值
					subKpiGoalMap = kPIGoalManager.listKPIGoalOnCurrentYear(
							subKpiInfoList, queryDate);

					for (DwmisKpiInfo subKpiInfo : subKpiInfoList) {
						if (subKpiInfo == null
								|| subKpiInfo.getGoalType() == null)
							continue;
						this.logger.info("查询指标[" + kpiInfo.getKpiCode()
								+ "]关联的子指标者数据:" + subKpiInfo.getKpiCode());
						subKpiTreands = this.transformToKpiTrends(subKpiInfo,
								subKpiDataMap, subKpiGoalMap);
						if (subKpiTreands == null)
							continue;
						subKpiThreadsList.add(subKpiTreands);
					}
					if (!subKpiThreadsList.isEmpty()) {
						kpiTreands.setSubKPITrendsList(subKpiThreadsList);
					}
				}
				// add
				deptKpiDataList.add(new DwmisDeptKpiData(deptInfo, kpiTreands));
			}
			break;
		}
		return deptKpiDataList;
	}

	/**
	 * 得到指标数据
	 * 
	 * @param kpiInfo
	 * @return
	 */
	private KPITrends transformToKpiTrends(DwmisKpiInfo kpiInfo,
			Map<String, Double> kpiDataMap, Map<String, DwmisKpiGoal> kpiGoalMap) {
		if (kpiInfo == null)
			return null;
		if (kpiDataMap == null)
			kpiDataMap = new HashMap<String, Double>();
		if (kpiGoalMap == null)
			kpiGoalMap = new HashMap<String, DwmisKpiGoal>();
		this.logger.info("转到得到指标数据:" + kpiInfo.getKpiCode());
		KPITrends kpiTreands = new KPITrends(kpiInfo);
		DwmisKpiGoal kpiGoal = kpiGoalMap.get(kpiInfo.getKpiCode());
		if (kpiGoal == null) {
			kpiGoal = new DwmisKpiGoal();
			kpiGoal.setKpiCode(kpiInfo.getKpiCode());
			kpiGoal.setScroll35(new BigDecimal(CoreConstant.DEFAULT_GOAL_VALUE));
			kpiGoal.setScroll50(new BigDecimal(CoreConstant.DEFAULT_GOAL_VALUE));
			kpiGoal.setLastYearKpi(new BigDecimal(
					CoreConstant.DEFAULT_GOAL_VALUE));
		} else {
			if (kpiGoal.getScroll35() == null) {
				kpiGoal.setScroll35(new BigDecimal(
						CoreConstant.DEFAULT_GOAL_VALUE));
			}
			if (kpiGoal.getScroll50() == null) {
				kpiGoal.setScroll50(new BigDecimal(
						CoreConstant.DEFAULT_GOAL_VALUE));
			}
			if (kpiGoal.getLastYearKpi() == null) {
				kpiGoal.setLastYearKpi(new BigDecimal(
						CoreConstant.DEFAULT_GOAL_VALUE));
			}
		}
		kpiTreands.setGoal35(kpiGoal.getScroll35().doubleValue());
		kpiTreands.setGoal5(kpiGoal.getScroll50().doubleValue());
		kpiTreands.setLastYearKPI(kpiGoal.getLastYearKpi().doubleValue());
		// 已完成数量
		// TODO 跨年方案 - 需要修改成获取指定年的当完成值
		Double amount = kpiDataMap.containsKey(kpiInfo.getKpiCode()) ? kpiDataMap
				.get(kpiInfo.getKpiCode()) : 0;
		// 对完成值进行单位换算，并设入页面对象PO
		kpiTreands.setAmountDone(UnitTransformer.transform(amount,
				kpiInfo.getSizeId(), kpiInfo));
		// 计算完成率
		Double percent = makePercent(amount, kpiInfo, kpiGoal);
		// 对 完成率 进行小数位四舍五入，并设入页面对象PO
		kpiTreands.setPercent(UnitTransformer.keepDecimal(percent,
				CoreConstant.DEFAULT_DIGIT, kpiInfo));
		// 计算pt值，只用于30011、30021两种类型
		kpiTreands.setPtValue(makePTValue(amount, kpiInfo, kpiGoal));

		// 是否有前钻指标
		kpiTreands.setHasSubKPITrends(kpiInfo.getSubKpiInfoList() == null
				|| kpiInfo.getSubKpiInfoList().isEmpty() ? false : true);

		// 是否已经达标，用于展现“预算完成率”的打勾打叉，不同绩效考核类型判断方法不同
		// 由于amount数据为未转换数量级的，Score数据为已转换数量级的
		// 需要把Score变成未转换数量级的，如200，数量级为万，则变成 2000000
		boolean isPassGoal = isPassGoal(kpiInfo, amount,
				UnitTransformer.transformReverse(kpiGoal.getScroll35()
						.doubleValue(), kpiInfo.getSizeId(), kpiInfo), percent);
		kpiTreands.setPassGoal(isPassGoal);
		return kpiTreands;
	}

	/**
	 * 注意：该方法应该在计算percent后调用
	 * 
	 * 是否已经达标，用于展现“预算完成率”的打勾打叉，不同绩效考核类型判断方法不同
	 * 
	 * 绩效考核类型：
	 * 
	 * 所有月粒度的都采用与时间进度轴相比 即如当前完成值为30%，当前为5月22号，看的是四月的数据，时间轴进度为 4/12 = 33% 30% <
	 * 33% 没有跟上时间轴，所以 not pass goal
	 * 
	 * 
	 * 3005、3007：日累积值、七日均值 与当前时间的前一天时间轴对比，如2月2号的为：（31+2-1）/365
	 * 
	 * 
	 * 如果在数据库中找不到绩效数据，返回false
	 * 
	 * @param kpi
	 * @param amount
	 * @param score35
	 * @param percent
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private boolean isPassGoal(DwmisKpiInfo kpiInfo, Double amount,
			Double score35, Double percent) {

		// TODO 跨年方案 - 如果当前是跨年方案模式，则直接看percent是否等于、超过1
		if (sysDateForFixedYear.isFixedYearMode()) {

			if (percent >= 100) {
				return true;
			} else {
				return false;
			}
		}

		if (kpiInfo.getGoalType().intValue() == 3001
				|| kpiInfo.getGoalType().intValue() == 30011
				|| kpiInfo.getGoalType().intValue() == 30012
				|| kpiInfo.getGoalType().intValue() == 3002
				|| kpiInfo.getGoalType().intValue() == 30021) {

			/**
			 * 所有月粒度的都采用与时间进度轴相比 即如当前完成值为30%，当前为5月22号，看的是四月的数据，时间轴进度为 4/12 = 33%
			 * 30% < 33% 没有跟上时间轴，所以 not pass goal
			 */
			Date now = TimeFormatProcessor.getSysDate(1);

			if (percent >= ((double) Integer.parseInt(DateUtils.formatUtilDate(
					now, "MM")) * 100 / 12)) {
				return true;
			} else {
				return false;
			}

		} else if (kpiInfo.getGoalType().intValue() == 3005
				|| kpiInfo.getGoalType().intValue() == 3007
				|| kpiInfo.getGoalType().intValue() == 30052
				|| kpiInfo.getGoalType().intValue() == 30051
				|| kpiInfo.getGoalType().intValue() == 3009) {

			// 3005、3007：日累积值、七日均值
			// 与当天的时间进度比
			Date now = TimeFormatProcessor.getSysDate(2);

			Calendar calendar = Calendar.getInstance(CHINA);
			calendar.setTime(now);
			int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

			// 本年度总共多少天
			int daysInYear = 365;
			if (TimeFormatProcessor.isLeapYear(calendar.get(Calendar.YEAR))) {
				daysInYear = 366;
			}

			if (percent >= ((double) dayOfYear * 100 / daysInYear)) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	/**
	 * 计算PT值，只用于30011、30021两种类型
	 * 
	 * @param amount
	 * @param kpiPO
	 * @return
	 */
	private double makePTValue(Double amount, DwmisKpiInfo kpiInfo,
			DwmisKpiGoal kpiGoal) {
		if (kpiInfo == null || kpiGoal == null)
			return 0;
		// a-35
		if (kpiInfo.getGoalType().intValue() == 30011
				|| kpiInfo.getGoalType().intValue() == 30051
				|| kpiInfo.getGoalType().intValue() == 3009) {

			// 先把goal35、lastyear转成未换算单位的
			double goal35 = UnitTransformer.transformReverse(kpiGoal
					.getScroll35().doubleValue(), kpiInfo.getSizeId(), kpiInfo);

			double result = DoubleUtility.minus(amount, goal35);

			return UnitTransformer.transform(result, kpiInfo.getSizeId(),
					kpiInfo);
		} else if (kpiInfo.getGoalType().intValue() == 30021) {
			// 35-a
			// 先把goal35、lastyear转成未换算单位的
			double goal35 = UnitTransformer.transformReverse(kpiGoal
					.getScroll35().doubleValue(), kpiInfo.getSizeId(), kpiInfo);

			double result = DoubleUtility.minus(goal35, amount);

			return UnitTransformer.transform(result, kpiInfo.getSizeId(),
					kpiInfo);
		}

		return 0.0;
	}

	/**
	 * 计算完成率，各种绩效类型各不相同
	 * 
	 * 
	 * @return
	 */
	private Double makePercent(Double amount, DwmisKpiInfo kpiInfo,
			DwmisKpiGoal kpiGoal) {

		if (kpiGoal.getScroll35().compareTo(new BigDecimal(0)) == 0
				|| amount == CoreConstant.DEFAULT_DATA_NOT_FOUND) {
			return CoreConstant.DEFAULT_DATA_NOT_FOUND;
		}

		/*
		 * 直接比较 // 为谷值 if (kpiInfo.getGoalType().equals(3002)) {
		 * 
		 * if (amount == 0) {
		 * 
		 * return CoreConstant.DEFAULT_DATA_NOT_FOUND;
		 * 
		 * } else { double result = kpiGoal.getScroll35().doubleValue() / amount
		 * 100; return UnitTransformer.transformReverse(result,
		 * kpiInfo.getSizeId(), kpiInfo); } }
		 * 
		 * // 月峰值（计去年）、日累积（计去年） // 除了进度条百分比数字，其他与月峰值类型相同。进度数字：(a-ly)/35-ly，小于0为0
		 * if (kpiInfo.getGoalType().equals(30012) ||
		 * kpiInfo.getGoalType().equals(30052) ||
		 * kpiInfo.getGoalType().equals(3007) ||
		 * kpiInfo.getGoalType().equals(30011) ||
		 * kpiInfo.getGoalType().equals(30021) ||
		 * kpiInfo.getGoalType().equals(30051) ||
		 * kpiInfo.getGoalType().equals(3009)) {
		 * 
		 * if (amount == CoreConstant.DEFAULT_DATA_NOT_FOUND) {
		 * 
		 * return 0.0;
		 * 
		 * } else { // (a-ly)/35-ly，小于0为0
		 * 
		 * // 先把goal35、lastyear转成未换算单位的 double goal35 =
		 * UnitTransformer.transformReverse(kpiGoal
		 * .getScroll35().doubleValue(), kpiInfo.getSizeId(), kpiInfo); double
		 * lastYear = UnitTransformer.transformReverse(kpiGoal
		 * .getLastYearKpi().doubleValue(), kpiInfo.getSizeId(), kpiInfo);
		 * 
		 * double result = 0.0;
		 * 
		 * if (goal35 != lastYear) { result = (DoubleUtility.minus(amount,
		 * lastYear)) / (DoubleUtility.minus(goal35, lastYear)); } else { //
		 * 如果公式的分母计算结果为0的情况下 // 直接使用最下面的 amount/goal35 的公式 result = amount /
		 * goal35; }
		 * 
		 * if (result < 0) { return 0.0; } else { return result * 100; } } }
		 */

		// 默认公式
		// 为峰值、日累积值、七日均值 及 其他
		double result = amount / kpiGoal.getScroll35().doubleValue() * 100;
		return UnitTransformer.transform(result, kpiInfo.getSizeId(), kpiInfo);
	}

	@Override
	public BigDecimal getFullYearValueByKpiCode(String kpiCode, int yearNo) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据KPI编码得到指标数据（指定日期，指标编码，时间粒度，统计方式）
	 * 
	 * @param kpiData
	 * @return
	 */
	@Override
	public List<DwmisKpiData> getKpiDataByCondition(List<String> kpiCodeList) {
		this.logger.info("****根据KPI编码得到指标数据****");
		if (kpiCodeList == null || kpiCodeList.isEmpty()) {
			return null;
		}
		String codeStr = ",";
		for (String code : kpiCodeList) {
			codeStr = codeStr + code + ",";
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("codeStr", codeStr);
		paramMap.put("kpiCodeList", kpiCodeList);
		return this.myBatisDao
				.getList(
						"com.infosmart.dwmis.DwmisMisKpiDataMapper.queryDwmisKpiDataByKpiCode",
						paramMap);
	}

	@Override
	public Map<String, List<DwmisKpiData>> listdwmisKpiDataByKpiCode(
			List<DwmisKpiData> kpiDataList, String reportBeginDate,
			String reportEndDate, String dateType) {
		this.logger.info("列出多个指定的指标在一定时间范围内[ " + reportBeginDate + " 至  "
				+ reportEndDate + " ]的指标数据");
		if (kpiDataList == null || kpiDataList.isEmpty()) {
			this.logger.warn("列出多个指定的指标在一定时间范围内的指标数据错误：参数为空");
			return new HashMap<String, List<DwmisKpiData>>();
		}
		// 指标数据
		List<DwmisKpiData> allKpiDataList = new ArrayList<DwmisKpiData>();
		// 正常的指标编码
		List<String> kpiCodeListOfNormal = new ArrayList<String>();
		for (DwmisKpiData kpiData : kpiDataList) {
			if (kpiData == null) {
				continue;
			}
			kpiCodeListOfNormal.add(kpiData.getKpiCode());
		}
		// 计算正常的指标数据
		if (!kpiCodeListOfNormal.isEmpty()) {
			// 查询参数
			Map parameterMap = new HashMap();
			parameterMap.put("kpiCodes", kpiCodeListOfNormal);
			// 报表类型,日，周，月
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

			List<DwmisKpiData> normalKpiDataList = this.myBatisDao
					.getList(
							"com.infosmart.dwmis.DwmisMisKpiDataMapper.queryKpiDataBetweenDateByKpiCode",
							parameterMap);
			if (normalKpiDataList != null) {
				allKpiDataList.addAll(normalKpiDataList);
			}
		}
		if (allKpiDataList == null || allKpiDataList.isEmpty()) {
			this.logger.warn("没有找到指定指标相关的统计数据");
			return new HashMap<String, List<DwmisKpiData>>();
		}
		// 所有统计数据<kpiCode,kpiDataList>
		Map<String, List<DwmisKpiData>> allKpiDataMap = new HashMap<String, List<DwmisKpiData>>();
		List<DwmisKpiData> dwmisKpiDataList = null;
		for (DwmisKpiData kpiData : allKpiDataList) {
			if (allKpiDataMap.containsKey(kpiData.getKpiCode())) {
				// 存在
				allKpiDataMap.get(kpiData.getKpiCode()).add(kpiData);
			} else {
				// 不存在
				dwmisKpiDataList = new ArrayList<DwmisKpiData>();
				dwmisKpiDataList.add(kpiData);
				allKpiDataMap.put(kpiData.getKpiCode(), dwmisKpiDataList);
			}
		}
		// 重新计算统计数据
		Map<String, List<DwmisKpiData>> stKpiDataMap = new HashMap<String, List<DwmisKpiData>>();
		for (DwmisKpiData kpiData : kpiDataList) {
			dwmisKpiDataList = allKpiDataMap.get(kpiData.getKpiCode());
			if (dwmisKpiDataList == null || dwmisKpiDataList.isEmpty())
				continue;
			// 如果没有开始时间,则默认开始时间为最早的记录时间
			if (!StringUtils.notNullAndSpace(reportBeginDate)) {
				reportBeginDate = dwmisKpiDataList.get(0).getReportDate()
						.toString();
			}
			// 如果没有结束日期，则默认为记录最晚的时间
			if (!StringUtils.notNullAndSpace(reportEndDate)) {
				reportEndDate = dwmisKpiDataList
						.get(dwmisKpiDataList.size() - 1).getReportDate()
						.toString();
			}

			stKpiDataMap.put(kpiData.getKpiCode(), dwmisKpiDataList);
		}
		return stKpiDataMap;
	}

	/**
	 * 根据单个指标查询指标数据
	 * 
	 * @param dwmisKpiData
	 * @return
	 */
	@Override
	public List<DwmisKpiData> getKpiDataByKpiCode(DwmisKpiData dwmisKpiData,
			List<String> staCodeList) {
		Map param = new HashMap();
		param.put("kpiCode", dwmisKpiData.getKpiCode());
		if (dwmisKpiData.getReportDate() != null) {
			param.put("queryDate", DateUtils.formatByFormatRule(
					dwmisKpiData.getReportDate(), "yyyyMMdd"));
		}
		if (dwmisKpiData.getDateType() != 0) {
			param.put("dateType", String.valueOf(dwmisKpiData.getDateType()));
		}
		if (staCodeList == null || staCodeList.size() <= 0) {
			if (dwmisKpiData.getStaCode() != 0) {
				param.put("staCode", String.valueOf(dwmisKpiData.getStaCode()));
			}
		} else {
			param.put("staCodeList", staCodeList);

		}
		List<DwmisKpiData> KpiDataList = this.myBatisDao
				.getList(
						"com.infosmart.dwmis.DwmisMisKpiDataMapper.queryKpiDataByKpiCode",
						param);
		return KpiDataList;
	}

	@Override
	public Map<String, DwmisKpiData> getKpiData(List<String> kpiCodeList,
			int dateType, int staCode, Date reportDate) {
		this.logger.info("根据指定的指标查询某日/周/月的指标数据");
		if (kpiCodeList == null) {
			this.logger.warn("传入的指标编码参数为空");
			return null;
		}
		if (reportDate == null) {
			this.logger.warn("传入的查询日期参数为空");
			return null;
		}
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		// 查询指标
		parameterMap.put("kpiCodeList", kpiCodeList);
		// 时间粒度
		parameterMap.put("dateType", String.valueOf(dateType));
		// 统计方式
		parameterMap.put("staCode", String.valueOf(staCode));
		// 统计时间
		switch (dateType) {
		case CoreConstant.DAY_PERIOD: {
			// 日粒度
			parameterMap.put("report_date",
					DateUtils.formatByFormatRule(reportDate, "yyyyMMdd"));
			break;
		}
		case CoreConstant.WEEK_PERIOD: {
			// 周粒度
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			cal.setTime(reportDate);
			cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6); // Sunday
			// 本周的最后一天
			parameterMap.put("report_date",
					DateUtils.formatByFormatRule(cal.getTime(), "yyyyMMdd"));
			break;
		}
		case CoreConstant.MONTH_PERIOD: {
			// 月粒度
			Calendar cal = Calendar.getInstance();
			cal.setTime(reportDate);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
			// 本月最后一天
			parameterMap.put("report_date",
					DateUtils.formatByFormatRule(cal.getTime(), "yyyyMMdd"));
			break;
		}
		default: {
			// 日粒度
			parameterMap.put("report_date",
					DateUtils.formatByFormatRule(reportDate, "yyyyMMdd"));
			break;
		}
		}
		List<DwmisKpiData> kpiDataList = this.myBatisDao
				.getList(
						"com.infosmart.dwmis.DwmisMisKpiDataMapper.queryKpiDataByMultiKpiCode",
						parameterMap);
		if (kpiDataList == null || kpiDataList.isEmpty()) {
			this.logger.warn("没有找到任何指标数据");
			return null;
		}
		Map<String, DwmisKpiData> kpiDataMap = new HashMap<String, DwmisKpiData>();
		for (DwmisKpiData kd : kpiDataList) {
			if (kd == null)
				continue;
			kpiDataMap.put(kd.getKpiCode(), kd);
		}
		return kpiDataMap;
	}

	@Override
	public Map<String, List<DwmisKpiData>> listKpiData(
			List<String> kpiCodeList, int dateType, int staCode,
			Date beginDate, Date endDate) {
		this.logger.info("根据指定的指标查询某段时间内的指标数据");
		if (kpiCodeList == null) {
			this.logger.warn("传入的指标编码参数为空");
			return null;
		}
		if (beginDate == null) {
			this.logger.warn("传入的查询日期(开始)参数为空");
			return null;
		}
		if (endDate == null) {
			this.logger.warn("传入的查询日期(结束)参数为空");
			return null;
		}
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		// 查询指标
		parameterMap.put("kpiCodeList", kpiCodeList);
		// 时间粒度
		parameterMap.put("dateType", String.valueOf(dateType));
		// 统计方式
		parameterMap.put("staCode", String.valueOf(staCode));
		// 统计时间
		switch (dateType) {
		case CoreConstant.DAY_PERIOD: {
			// 日粒度
			parameterMap.put("beginDate",
					DateUtils.formatByFormatRule(beginDate, "yyyy-MM-dd"));
			parameterMap.put("endDate",
					DateUtils.formatByFormatRule(endDate, "yyyy-MM-dd"));
			break;
		}
		case CoreConstant.WEEK_PERIOD: {
			// 周粒度
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			// 开始日期所在周最后一天
			cal.setTime(beginDate);
			cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6); // Sunday
			parameterMap.put("beginDate",
					DateUtils.formatByFormatRule(cal.getTime(), "yyyy-MM-dd"));
			// 结束日期所在周最后一天
			cal.setTime(endDate);
			cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6); // Sunday
			parameterMap.put("endDate",
					DateUtils.formatByFormatRule(cal.getTime(), "yyyy-MM-dd"));
			break;
		}
		case CoreConstant.MONTH_PERIOD: {
			// 月粒度
			Calendar cal = Calendar.getInstance();
			// 开始日期所在月最后一天
			cal.setTime(beginDate);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
			parameterMap.put("beginDate",
					DateUtils.formatByFormatRule(cal.getTime(), "yyyy-MM-dd"));
			// 结束日期所在月最后一天
			cal.setTime(endDate);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
			parameterMap.put("endDate",
					DateUtils.formatByFormatRule(cal.getTime(), "yyyy-MM-dd"));
			break;
		}
		default: {
			// 日粒度
			parameterMap.put("beginDate",
					DateUtils.formatByFormatRule(beginDate, "yyyy-MM-dd"));
			parameterMap.put("endDate",
					DateUtils.formatByFormatRule(endDate, "yyyy-MM-dd"));
			break;
		}
		}
		List<DwmisKpiData> kpiDataList = this.myBatisDao
				.getList(
						"com.infosmart.dwmis.DwmisMisKpiDataMapper.listKpiDataInTimeZonesByMultiKpiCode",
						parameterMap);
		if (kpiDataList == null || kpiDataList.isEmpty()) {
			this.logger.warn("没有找到任何指标数据");
			return null;
		}
		Map<String, List<DwmisKpiData>> kpiDataMap = new HashMap<String, List<DwmisKpiData>>();
		for (DwmisKpiData kd : kpiDataList) {
			if (kd == null)
				continue;
			if (kpiDataMap.containsKey(kd.getKpiCode())) {
				kpiDataMap.get(kd.getKpiCode()).add(kd);
			} else {
				List<DwmisKpiData> kdList = new ArrayList<DwmisKpiData>();
				kdList.add(kd);
				kpiDataMap.put(kd.getKpiCode(), kdList);
			}
		}
		return kpiDataMap;
	}

	/**
	 * 根据KPI编码、时间粒度、统计方式得到指标数据的值
	 * 
	 * @param kpiData
	 * @return
	 */
	@Override
	public Double getKpiDataValueByYear(String kpiCode, int dateType,
			int staCode, Date reportDate) {
		this.logger.info("根据指定的指标查询相应的累计值");
		if (!StringUtils.notNullAndSpace(kpiCode)) {
			this.logger.warn("传入的指标编码参数为空");
			return 0.0;
		}
		if (reportDate == null) {
			reportDate = new Date();
		}
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("kpiCode", kpiCode);
		parameterMap.put("dateType", dateType);
		parameterMap.put("staCode", staCode);
		parameterMap.put("reportDate", reportDate);
		Double values = myBatisDao
				.get("com.infosmart.dwmis.DwmisMisKpiDataMapper.getKpiDataValueByYear",
						parameterMap);
		if (values == null) {
			values = 0.0;
		}
		return values;
	}

	/**
	 * 根据KPI编码、时间粒度、统计方式得到指标数据的值
	 * 
	 * @param kpiData
	 * @return
	 */
	@Override
	public Double getKpiDataValueByMonth(String kpiCode, int dateType,
			int staCode, Date reportDate) {
		this.logger.info("根据指定的指标查询相应的累计值");
		if (!StringUtils.notNullAndSpace(kpiCode)) {
			this.logger.warn("传入的指标编码参数为空");
			return 0.0;
		}
		if (reportDate == null) {
			reportDate = new Date();
		}
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("kpiCode", kpiCode);
		parameterMap.put("dateType", dateType);
		parameterMap.put("staCode", staCode);
		parameterMap.put("reportDate", reportDate);
		Double values = myBatisDao
				.get("com.infosmart.dwmis.DwmisMisKpiDataMapper.getKpiDataValueByMonth",
						parameterMap);
		if (values == null) {
			values = 0.0;
		}
		return values;
	}

	/**
	 * 根据KPI编码得到指标数据（指定日期，指标编码，时间粒度，统计方式）
	 * 
	 * @param kpiData
	 * @return
	 */
	@Override
	public List<DwmisKpiData> getKpiDataByParams(Object param,
			List<String> kpiCodeList, int dateType, int staCode,
			String beginDate, String endDate) {
		this.logger.info("****根据KPI编码,时间粒度,统计方式得到指标数据****");
		if (kpiCodeList == null) {
			this.logger.warn("传入的指标编码参数为空");
			return null;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("param", param);
		paramMap.put("dateType", dateType);
		paramMap.put("staCode", staCode);
		paramMap.put("kpiCodeList", kpiCodeList);
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate", endDate);
		return this.myBatisDao.getList(
				"com.infosmart.dwmis.DwmisMisKpiDataMapper.getKpiDataMessage",
				paramMap);
	}

	@Override
	public Map<String, List<DwmisKpiData>> listKpiDataByDate(
			List<String> kpiCodeList, int dateType, int staCode,
			Date beginDate, Date endDate) {
		this.logger.info("根据指定的指标查询某段时间内的指标数据");
		if (kpiCodeList == null) {
			this.logger.warn("传入的指标编码参数为空");
			return null;
		}
		if (beginDate == null) {
			this.logger.warn("传入的查询日期(开始)参数为空");
			return null;
		}
		if (endDate == null) {
			this.logger.warn("传入的查询日期(结束)参数为空");
			return null;
		}
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		// 查询指标
		parameterMap.put("kpiCodeList", kpiCodeList);
		// 时间粒度
		parameterMap.put("dateType", String.valueOf(dateType));
		// 统计方式
		parameterMap.put("staCode", String.valueOf(staCode));
		// 统计时间
		switch (dateType) {
		case CoreConstant.DAY_PERIOD: {
			// 日粒度
			parameterMap.put("beginDate",
					DateUtils.formatByFormatRule(beginDate, "yyyy-MM-dd"));
			parameterMap.put("endDate",
					DateUtils.formatByFormatRule(endDate, "yyyy-MM-dd"));
			break;
		}
		case CoreConstant.WEEK_PERIOD: {
			// 周粒度
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			// 开始日期所在周最后一天
			cal.setTime(beginDate);
			cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6); // Sunday
			parameterMap.put("beginDate",
					DateUtils.formatByFormatRule(cal.getTime(), "yyyy-MM-dd"));
			// 结束日期所在周最后一天
			cal.setTime(endDate);
			cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6); // Sunday
			parameterMap.put("endDate",
					DateUtils.formatByFormatRule(cal.getTime(), "yyyy-MM-dd"));
			break;
		}
		case CoreConstant.MONTH_PERIOD: {
			// 月粒度
			Calendar cal = Calendar.getInstance();
			// 开始日期所在月最后一天
			cal.setTime(beginDate);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
			parameterMap.put("beginDate",
					DateUtils.formatByFormatRule(cal.getTime(), "yyyy-MM-dd"));
			// 结束日期所在月最后一天
			cal.setTime(endDate);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
			parameterMap.put("endDate",
					DateUtils.formatByFormatRule(cal.getTime(), "yyyy-MM-dd"));
			break;
		}
		default: {
			// 日粒度
			parameterMap.put("beginDate",
					DateUtils.formatByFormatRule(beginDate, "yyyy-MM-dd"));
			parameterMap.put("endDate",
					DateUtils.formatByFormatRule(endDate, "yyyy-MM-dd"));
			break;
		}
		}
		List<DwmisKpiData> kpiDataList = this.myBatisDao
				.getList(
						"com.infosmart.dwmis.DwmisMisKpiDataMapper.listKpiDataInTimeZonesByMultiKpiCode",
						parameterMap);
		if (kpiDataList == null || kpiDataList.isEmpty()) {
			this.logger.warn("没有找到任何指标数据");
			return null;
		}
		Map<String, List<DwmisKpiData>> kpiDataMap = new HashMap<String, List<DwmisKpiData>>();
		for (DwmisKpiData kd : kpiDataList) {
			if (kd == null)
				continue;
			// this.logger.info("------------>kpi_date:"+kd.getShowReportDate());
			if (kpiDataMap.containsKey(kd.getShowReportDate())) {
				kpiDataMap.get(kd.getShowReportDate()).add(kd);
			} else {
				List<DwmisKpiData> kdList = new ArrayList<DwmisKpiData>();
				kdList.add(kd);
				kpiDataMap.put(kd.getShowReportDate(), kdList);
			}
		}
		return kpiDataMap;
	}

	@Override
	public List<DwmisKpiData> kpiDataListByParam(String kpiCode, int dateType,
			int staCode, Date beginDate, Date endDate) {
		this.logger.info("根据指定的指标查询某段时间内的指标数据");
		if (!StringUtils.notNullAndSpace(kpiCode)) {
			this.logger.warn("传入的指标编码参数为空");
			return null;
		}
		if (beginDate == null) {
			this.logger.warn("传入的查询日期(开始)参数为空");
			return null;
		}
		if (endDate == null) {
			this.logger.warn("传入的查询日期(结束)参数为空");
			return null;
		}
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		// 查询指标
		parameterMap.put("kpiCode", kpiCode);
		// 时间粒度
		parameterMap.put("dateType", String.valueOf(dateType));
		// 统计方式
		parameterMap.put("staCode", String.valueOf(staCode));
		// 统计时间
		switch (dateType) {
		case CoreConstant.DAY_PERIOD: {
			// 日粒度
			parameterMap.put("beginDate",
					DateUtils.formatByFormatRule(beginDate, "yyyy-MM-dd"));
			parameterMap.put("endDate",
					DateUtils.formatByFormatRule(endDate, "yyyy-MM-dd"));
			break;
		}
		case CoreConstant.WEEK_PERIOD: {
			// 周粒度
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			// 开始日期所在周最后一天
			cal.setTime(beginDate);
			cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6); // Sunday
			parameterMap.put("beginDate",
					DateUtils.formatByFormatRule(cal.getTime(), "yyyy-MM-dd"));
			// 结束日期所在周最后一天

			Calendar c = Calendar.getInstance();
			c.setFirstDayOfWeek(Calendar.MONDAY);
			c.setTime(endDate);
			c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
			parameterMap.put("endDate",
					DateUtils.formatByFormatRule(c.getTime(), "yyyy-MM-dd"));
			break;
		}
		case CoreConstant.MONTH_PERIOD: {
			// 月粒度
			Calendar cal = Calendar.getInstance();
			// 开始日期所在月最后一天
			cal.setTime(beginDate);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
			parameterMap.put("beginDate",
					DateUtils.formatByFormatRule(cal.getTime(), "yyyy-MM-dd"));
			// 结束日期所在月最后一天
			cal.setTime(endDate);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
			parameterMap.put("endDate",
					DateUtils.formatByFormatRule(cal.getTime(), "yyyy-MM-dd"));
			break;
		}
		default: {
			// 日粒度
			parameterMap.put("beginDate",
					DateUtils.formatByFormatRule(beginDate, "yyyy-MM-dd"));
			parameterMap.put("endDate",
					DateUtils.formatByFormatRule(endDate, "yyyy-MM-dd"));
			break;
		}
		}
		List<DwmisKpiData> kpiDataList = this.myBatisDao
				.getList(
						"com.infosmart.dwmis.DwmisMisKpiDataMapper.listKpiDataInTimeZonesByKpiCode",
						parameterMap);
		if (kpiDataList == null || kpiDataList.isEmpty()) {
			this.logger.warn("没有找到任何指标数据");
			return null;
		}

		// // 如果没有开始时间,则默认开始时间为最早的记录时间
		// if (beginDate == null) {
		// beginDate = kpiDataList.get(0).getReportDate();
		// }
		// // 如果没有结束日期，则默认为记录最晚的时间
		// if (endDate == null) {
		// endDate = kpiDataList.get(kpiDataList.size() - 1).getReportDate();
		// }
		DwmisKpiInfo kpiInfo = this.kpiInfoService
				.getDwmisKpiInfoByCode(kpiCode);
		Date startDate = beginDate, overDate = endDate;
		while (startDate.getTime() <= overDate.getTime()) {
			if (dateType == DwmisKpiInfo.KPI_TYPE_OF_DAY) {
				// 判断记录是否存在,如果不存在则默认初始化数
				this.existsKpiDateOfOneDate(kpiDataList, startDate, kpiInfo,
						dateType);
				// 下一个日期
				startDate = DateUtils.getNextDate(startDate);
			} else if (dateType == DwmisKpiInfo.KPI_TYPE_OF_WEEK) {
				// 判断记录是否存在,如果不存在则默认初始化数
				Date weekDate = DateUtils.getWeekendDay(startDate);
				this.existsKpiDateOfOneDate(kpiDataList, weekDate, kpiInfo,
						dateType);
				// 下一个月份
				startDate = DateUtils.getNextWeek(weekDate);
			} else {
				this.existsKpiDateOfOneDate(kpiDataList,
						DateUtils.getLastDayofMonth(startDate), kpiInfo,
						dateType);
				// 下一个月份
				startDate = DateUtils.getNextMonth(startDate);
			}
		}
		return kpiDataList;
	}

	/**
	 * 判断某日期的kpi数据是否存在,如果存在,则判断是否按规则计算,则重新计算
	 * 
	 * @param stKpiDataList
	 * @param reportDate
	 * @param dwpasCKpiInfo
	 * @return
	 */
	private void existsKpiDateOfOneDate(List<DwmisKpiData> stKpiDataList,
			Date reportDate, DwmisKpiInfo dwmisKpiInfo, int dateType) {
		this.logger.debug("判断该天的记录是否存在:" + reportDate);
		if (stKpiDataList == null || stKpiDataList.isEmpty())
			return;
		int reportDateInt = Integer.valueOf(DateUtils.formatByFormatRule(
				reportDate, "yyyyMMdd"));
		for (DwmisKpiData stKpiData : stKpiDataList) {
			// 判断日期是否大于reportDate,如果大于reportDate,则列表不存在该记录
			// 因为报表日期格式为yyyyMMdd或yyyyMM,故可转整数
			int stReportDateInt = Integer.valueOf(DateUtils.formatByFormatRule(
					stKpiData.getReportDate(), "yyyyMMdd"));
			if (stReportDateInt < reportDateInt) {
				continue;
			} else if (stReportDateInt == reportDateInt) {
				// 记录已存在
				this.logger.debug("记录已存在:" + reportDate);
				// 设置关联指标信息
				stKpiData.setDwmisKpiInfo(dwmisKpiInfo);
				return;
			} else {
				this.logger.debug("该天确定不存在记录：" + reportDate);
				break;
			}
		}
		this.logger.debug("该天不存在记录：" + reportDate);
		// 不存在的记录,则默认初始数
		DwmisKpiData kpiData = new DwmisKpiData();
		kpiData.setDwmisKpiInfo(dwmisKpiInfo);
		// 默认为0
		kpiData.setValue(new BigDecimal(0));
		kpiData.setReportDate(reportDate);
		kpiData.setKpiCode(dwmisKpiInfo.getKpiCode());
		kpiData.setDateType(dateType);
		stKpiDataList.add(kpiData);
	}

	@Override
	public List<DwmisKpiData> getKpiDataByCodeAndDate(String kpiCode,
			Date reportDate, int dateType, int staCode) {
		if (!StringUtils.notNullAndSpace(kpiCode)) {
			this.logger.warn("根据KpiCode和日期查询指标数据失败：指标Code为空!");
			return null;
		}
		if (reportDate == null) {
			this.logger.warn("根据KpiCode和日期查询指标数据失败：查询时间为空Code为空");
			return null;
		}
		Map map = new HashMap();
		map.put("kpiCode", kpiCode);
		map.put("reportDate",
				DateUtils.formatByFormatRule(reportDate, "yyyy-MM-dd"));
		map.put("staCode", staCode);
		map.put("dateType", dateType);
		return this.myBatisDao
				.getList(
						"com.infosmart.dwmis.DwmisMisKpiDataMapper.getKpiDataByCodeAndDate",
						map);
	}
}
