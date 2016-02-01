package com.infosmart.portal.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasStKpiData;
import com.infosmart.portal.pojo.ProkpiAnalyze;
import com.infosmart.portal.service.DwpasCKpiInfoService;
import com.infosmart.portal.service.DwpasStKpiDataService;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.StringUtils;

/**
 * kpi数据管理
 * 
 * @author infosmart
 * 
 */
@Service
public class DwpasStKpiDataServiceImpl extends BaseServiceImpl implements
		DwpasStKpiDataService {
	@Autowired
	private DwpasCKpiInfoService dwpasCKpiInfoService;

	@Override
	public List<DwpasStKpiData> listDwpasStKpiData(DwpasCKpiInfo dwpasCKpiInfo,
			String reportBeginDate, String reportEndDate) {
		this.logger.info("根据KPI数据及KPI信息查询一定时间范围内的KPI数据");
		if (dwpasCKpiInfo == null) {
			this.logger.warn("根据KPI数据及KPI信息查询一定时间范围内的KPI数据,参数为空");
			return null;
		}
		List<DwpasCKpiInfo> kpiInfoList = new ArrayList<DwpasCKpiInfo>();
		kpiInfoList.add(dwpasCKpiInfo);
		Map<String, List<DwpasStKpiData>> allKpiDataMap = this
				.listDwpasStKpiDataByKpiCode(kpiInfoList, reportBeginDate,
						reportEndDate,
						Integer.parseInt(dwpasCKpiInfo.getKpiType()));
		return allKpiDataMap == null || allKpiDataMap.isEmpty() ? null
				: allKpiDataMap.get(dwpasCKpiInfo.getKpiCode());
	}

	@Override
	public Map<String, BigDecimal> statDwpasStKpiDataByKpiCode(
			List<DwpasCKpiInfo> kpiInfoList, String reportBeginDate,
			String reportEndDate, int kpiType) {
		// 指标数据
		List<DwpasStKpiData> allKpiDataList = new ArrayList<DwpasStKpiData>();
		// 正常的指标编码
		List<String> kpiCodeListOfNormal = new ArrayList<String>();
		for (DwpasCKpiInfo kpiInfo : kpiInfoList) {
			if (kpiInfo == null)
				continue;
			if (!"1".equals(kpiInfo.getIsCalKpi())) {
				// 正常的指标
				kpiCodeListOfNormal.add(kpiInfo.getKpiCode());
			} else {
				this.logger.info("按规则计算指标数据:" + kpiInfo.getKpiCode());
				// 按规则计算
				List<DwpasStKpiData> caculateKpiDataList = this
						.caculateRuleValue(kpiInfo, reportBeginDate,
								reportEndDate);
				if (caculateKpiDataList != null) {
					allKpiDataList.addAll(caculateKpiDataList);
				}
			}
		}
		// 计算正常的指标数据
		if (!kpiCodeListOfNormal.isEmpty()) {
			// 查询参数
			Map parameterMap = new HashMap();
			parameterMap.put("kpiCodes", kpiCodeListOfNormal);
			// 报表类型,日，周，月
			String dateType = DwpasStKpiData.DATE_TYPE_OF_MONTH;
			if (kpiType == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
				dateType = DwpasStKpiData.DATE_TYPE_OF_DAY;
			} else if (kpiType == DwpasCKpiInfo.KPI_TYPE_OF_WEEK) {
				dateType = DwpasStKpiData.DATE_TYPE_OF_WEEK;
			}
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
			List<DwpasStKpiData> normalKpiDataList = this.myBatisDao
					.getList(
							"com.infosmart.mapper.DwpasStKpiDataMapper.statKpiDataAndKpiInfoBetweenDateByMultiKpi",
							parameterMap);
			if (normalKpiDataList != null) {
				allKpiDataList.addAll(normalKpiDataList);
			}
		}
		// 重新计算
		BigDecimal kpiDataVal = null;
		Map<String, BigDecimal> kpiDataMap = new HashMap<String, BigDecimal>();
		for (DwpasStKpiData kpiData : allKpiDataList) {
			if (kpiData == null)
				continue;
			if (kpiDataMap.containsKey(kpiData.getKpiCode())) {
				kpiDataVal = kpiDataMap.get(kpiData.getKpiCode());
				if (kpiDataVal == null)
					kpiDataVal = new BigDecimal(0);
				kpiDataVal = kpiDataVal.add(kpiData.getBaseValue());
				kpiDataMap.put(kpiData.getKpiCode(), kpiDataVal);
			} else {
				kpiDataVal = kpiData.getBaseValue() == null ? new BigDecimal(0)
						: kpiData.getBaseValue();
				kpiDataMap.put(kpiData.getKpiCode(), kpiDataVal);
			}
		}
		return kpiDataMap;
	}

	@Override
	public Map<String, List<DwpasStKpiData>> listDwpasStKpiDataByKpiCode(
			List<DwpasCKpiInfo> kpiInfoList, String reportBeginDate,
			String reportEndDate, int kpiType) {
		this.logger.info("列出多个指定的指标在一定时间范围内[ " + reportBeginDate + " 至  "
				+ reportEndDate + " ]的指标数据");
		if (kpiInfoList == null || kpiInfoList.isEmpty()) {
			this.logger.warn("列出多个指定的指标在一定时间范围内的指标数据错误：参数为空");
			return new HashMap<String, List<DwpasStKpiData>>();
		}
		// 指标数据
		List<DwpasStKpiData> allKpiDataList = new ArrayList<DwpasStKpiData>();
		// 正常的指标编码
		List<String> kpiCodeListOfNormal = new ArrayList<String>();
		for (DwpasCKpiInfo kpiInfo : kpiInfoList) {
			if (kpiInfo == null)
				continue;
			if (!"1".equals(kpiInfo.getIsCalKpi())) {
				// 正常的指标
				kpiCodeListOfNormal.add(kpiInfo.getKpiCode());
			} else {
				this.logger.info("按规则计算指标数据:" + kpiInfo.getKpiCode());
				// 按规则计算
				List<DwpasStKpiData> caculateKpiDataList = this
						.caculateRuleValue(kpiInfo, reportBeginDate,
								reportEndDate);
				if (caculateKpiDataList != null) {
					allKpiDataList.addAll(caculateKpiDataList);
				}
			}
		}
		// 计算正常的指标数据
		if (!kpiCodeListOfNormal.isEmpty()) {
			// 查询参数
			Map parameterMap = new HashMap();
			parameterMap.put("kpiCodes", kpiCodeListOfNormal);
			// 报表类型,日，周，月
			String dateType = DwpasStKpiData.DATE_TYPE_OF_MONTH;
			if (kpiType == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
				dateType = DwpasStKpiData.DATE_TYPE_OF_DAY;
			} else if (kpiType == DwpasCKpiInfo.KPI_TYPE_OF_WEEK) {
				dateType = DwpasStKpiData.DATE_TYPE_OF_WEEK;
			}
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

			List<DwpasStKpiData> normalKpiDataList = this.myBatisDao
					.getList(
							"com.infosmart.mapper.DwpasStKpiDataMapper.queryKpiDataAndKpiInfoBetweenDateByMultiKpi",
							parameterMap);
			if (normalKpiDataList != null) {
				allKpiDataList.addAll(normalKpiDataList);
			}
		}
		if (allKpiDataList == null || allKpiDataList.isEmpty()) {
			this.logger.warn("没有找到指定指标相关的统计数据");
			return new HashMap<String, List<DwpasStKpiData>>();
		}
		// 所有统计数据<kpiCode,kpiDataList>
		Map<String, List<DwpasStKpiData>> allKpiDataMap = new HashMap<String, List<DwpasStKpiData>>();
		List<DwpasStKpiData> kpiDataList = null;
		for (DwpasStKpiData kpiData : allKpiDataList) {
			if (allKpiDataMap.containsKey(kpiData.getKpiCode())) {
				// 存在
				allKpiDataMap.get(kpiData.getKpiCode()).add(kpiData);
			} else {
				// 不存在
				kpiDataList = new ArrayList<DwpasStKpiData>();
				kpiDataList.add(kpiData);
				allKpiDataMap.put(kpiData.getKpiCode(), kpiDataList);
			}
		}
		// 重新计算统计数据
		Map<String, List<DwpasStKpiData>> stKpiDataMap = new HashMap<String, List<DwpasStKpiData>>();
		for (DwpasCKpiInfo kpiInfo : kpiInfoList) {
			kpiDataList = allKpiDataMap.get(kpiInfo.getKpiCode());
			if (kpiDataList == null || kpiDataList.isEmpty())
				continue;
			// 如果没有开始时间,则默认开始时间为最早的记录时间
			if (!StringUtils.notNullAndSpace(reportBeginDate)) {
				reportBeginDate = kpiDataList.get(0).getReportDate();
			}
			// 如果没有结束日期，则默认为记录最晚的时间
			if (!StringUtils.notNullAndSpace(reportEndDate)) {
				reportEndDate = kpiDataList.get(kpiDataList.size() - 1)
						.getReportDate();
			}
			Date startDate = null, endDate = null;
			try {
				DateFormat df = null;
				if (kpiType == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
					df = new SimpleDateFormat("yyyyMMdd");
					// 日统计
					startDate = df.parse(reportBeginDate);
					endDate = df.parse(reportEndDate);
				} else {
					df = new SimpleDateFormat("yyyyMM");
					startDate = df.parse(reportBeginDate);
					endDate = df.parse(reportEndDate);
				}
			} catch (Exception e) {
				DateFormat df = null;
				try {
					// month
					if (reportBeginDate.length() == 6) {
						df = new SimpleDateFormat("yyyyMM");
						startDate = df.parse(reportBeginDate);
					} else {
						// day
						df = new SimpleDateFormat("yyyyMMdd");
						startDate = df.parse(reportBeginDate);
					}
					// month
					if (reportEndDate.length() == 6) {
						df = new SimpleDateFormat("yyyyMM");
						endDate = df.parse(reportEndDate);
					} else {
						// day
						df = new SimpleDateFormat("yyyyMMdd");
						endDate = df.parse(reportEndDate);
					}
				} catch (Exception e2) {
					e.printStackTrace();
					this.logger.error(e.getMessage(), e);
				}
			}
			while (startDate.getTime() <= endDate.getTime()) {
				if (kpiType == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
					// 判断记录是否存在,如果不存在则默认初始化数
					this.existsKpiDateOfOneDate(
							kpiDataList,
							DateUtils.formatByFormatRule(startDate, "yyyyMMdd"),
							kpiInfo);
					// 下一个日期
					startDate = DateUtils.getNextDate(startDate);
				} else {
					// 判断记录是否存在,如果不存在则默认初始化数
					this.existsKpiDateOfOneDate(kpiDataList,
							DateUtils.formatByFormatRule(startDate, "yyyyMM"),
							kpiInfo);
					// 下一个月份
					startDate = DateUtils.getNextMonth(startDate);
				}
			}
			// 数据再按日期进行排序
			Collections.sort(kpiDataList, new Comparator() {

				public int compare(Object o1, Object o2) {
					DwpasStKpiData data1 = (DwpasStKpiData) o1;
					DwpasStKpiData data2 = (DwpasStKpiData) o2;
					// 因为报表日期格式为yyyyMMdd或yyyyMM,故可转整数
					if (Integer.parseInt(data1.getReportDate()) > Integer
							.parseInt(data2.getReportDate())) {
						return 1;
					} else if (Integer.parseInt(data1.getReportDate()) < Integer
							.parseInt(data2.getReportDate())) {
						return -1;
					}
					return 0;
				}

			});
			stKpiDataMap.put(kpiInfo.getKpiCode(), kpiDataList);
		}
		return stKpiDataMap;
	}

	/**
	 * 按指标编码查询某时间段的指标数据
	 * 
	 * @param codeList
	 * @param reportBeginDate
	 * @param reportEndDate
	 * @param kpiType
	 * @return
	 */
	private List<DwpasStKpiData> listDwpasStKpiDataByCodeList(
			List<String> codeList, String reportBeginDate,
			String reportEndDate, int kpiType) {
		if (codeList == null || codeList.isEmpty()) {
			this.logger.warn("参数为空");
			return null;
		}
		// 默认是按日
		if (kpiType <= 0) {
			kpiType = DwpasCKpiInfo.KPI_TYPE_OF_DAY;
		}
		List<DwpasCKpiInfo> kpiInfoList = this.dwpasCKpiInfoService
				.listDwpasCKpiInfoByCodes(codeList);
		Map<String, List<DwpasStKpiData>> kpiDataMap = this
				.listDwpasStKpiDataByKpiCode(kpiInfoList, reportBeginDate,
						reportEndDate, kpiType);
		if (kpiDataMap == null || kpiDataMap.isEmpty())
			return null;
		List<DwpasStKpiData> kpiDataList = new ArrayList<DwpasStKpiData>();
		Set set = kpiDataMap.entrySet();
		Entry entry = null;
		for (Iterator it = set.iterator(); it.hasNext();) {
			entry = (Entry) it.next();
			kpiDataList.addAll((List<DwpasStKpiData>) entry.getValue());
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
	private void existsKpiDateOfOneDate(List<DwpasStKpiData> stKpiDataList,
			String reportDate, DwpasCKpiInfo dwpasCKpiInfo) {
		this.logger.debug("判断该天的记录是否存在:" + reportDate);
		if (stKpiDataList == null || stKpiDataList.isEmpty())
			return;
		for (DwpasStKpiData stKpiData : stKpiDataList) {
			// 判断日期是否大于reportDate,如果大于reportDate,则列表不存在该记录
			// 因为报表日期格式为yyyyMMdd或yyyyMM,故可转整数
			if (Integer.parseInt(stKpiData.getReportDate()) < Integer
					.parseInt(reportDate)) {
				continue;
			} else if (Integer.parseInt(stKpiData.getReportDate()) == Integer
					.parseInt(reportDate)) {
				// 记录已存在
				this.logger.debug("记录已存在:" + reportDate);
				// 设置关联指标信息
				stKpiData.setDwpasCKpiInfo(dwpasCKpiInfo);
				return;
			} else if (Integer.parseInt(stKpiData.getReportDate()) > Integer
					.parseInt(reportDate)) {
				this.logger.debug("该天确定不存在记录：" + reportDate);
				break;
			}
		}
		this.logger.debug("该天不存在记录：" + reportDate);
		// 不存在的记录,则默认初始数
		DwpasStKpiData kpiData = new DwpasStKpiData();
		// 关联指标信息
		kpiData.setDwpasCKpiInfo(dwpasCKpiInfo);
		kpiData.setAgeValue(new BigDecimal(0));
		// 默认为0
		kpiData.setBaseValue(new BigDecimal(0));
		// 日期类型
		if (Integer.parseInt(dwpasCKpiInfo.getKpiType()) == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
			kpiData.setDateType(DwpasStKpiData.DATE_TYPE_OF_DAY);
		} else {
			kpiData.setDateType(DwpasStKpiData.DATE_TYPE_OF_MONTH);

		}
		// 报表时间
		kpiData.setReportDate(reportDate);
		// 指标编码
		kpiData.setKpiCode(dwpasCKpiInfo.getKpiCode());
		kpiData.setMaxValue(new BigDecimal(0));
		kpiData.setMinValue(new BigDecimal(0));
		kpiData.setPerValue(new BigDecimal(0));
		//
		stKpiDataList.add(kpiData);
	}

	@Override
	public Map<String, DwpasStKpiData> listDwpasStKpiDataByKpiCode(
			List<String> kpiCodeList, String reportDate, String dateType) {
		this.logger.info("根据KPI编码(多个)，报表时间和时间类型查询KPI数据:" + reportDate);
		List<DwpasStKpiData> kpiDataList = this.listDwpasStKpiData(kpiCodeList,
				reportDate, dateType);
		if (kpiDataList == null || kpiDataList.isEmpty()) {
			return new HashMap<String, DwpasStKpiData>();
		}
		// 统计数据
		Map<String, DwpasStKpiData> stKpiDataMap = new HashMap<String, DwpasStKpiData>();
		if (kpiDataList != null && kpiDataList.size() > 0) {
			for (DwpasStKpiData kpiData : kpiDataList) {
				stKpiDataMap.put(kpiData.getKpiCode(), kpiData);
			}
		}
		return stKpiDataMap;
	}

	@Override
	public DwpasStKpiData listDwpasStKpiDataAndKpiInfo(
			DwpasCKpiInfo dwpasCKpiInfo, String reportDate) {
		this.logger.info("根据KPI指标和日期查询KPI数据");
		if (dwpasCKpiInfo == null) {
			this.logger.error("根据KPI指标和日期查询KPI数据,指标信息参数为空");
			return null;
		}
		if (!StringUtils.notNullAndSpace(reportDate)) {
			this.logger.info("根据KPI指标和日期查询KPI数据,日期为空");
			return null;
		}
		// 对日期格式进行转换
		if (reportDate != null && reportDate.indexOf("-") != -1) {
			reportDate = StringUtils.replace(reportDate, "-", "");
		}
		// 根据规则计算
		if ("1".equals(dwpasCKpiInfo.getIsCalKpi())) {
			this.logger.info("按规则计算指标数据:" + dwpasCKpiInfo.getKpiCode());
			// 按规则计算
			return this.caculateRuleValue(dwpasCKpiInfo, reportDate);
		}
		// 查询参数
		Map parameterMap = new HashMap();
		parameterMap.put("kpiCode", dwpasCKpiInfo.getKpiCode());
		// 报表类型,日，周，月
		int kpiType = Integer.parseInt(dwpasCKpiInfo.getKpiType());
		String dateType = DwpasStKpiData.DATE_TYPE_OF_MONTH;
		if (kpiType == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
			dateType = DwpasStKpiData.DATE_TYPE_OF_DAY;
		} else if (kpiType == DwpasCKpiInfo.KPI_TYPE_OF_WEEK) {
			dateType = DwpasStKpiData.DATE_TYPE_OF_WEEK;
		}
		parameterMap.put("dateType", dateType);
		parameterMap.put("reportDate", reportDate);
		DwpasStKpiData stKpiData = this.myBatisDao
				.get("com.infosmart.mapper.DwpasStKpiDataMapper.queryKpiDataAndKpiInfoByCondition",
						parameterMap);
		return stKpiData;
	}

	@Override
	public List<DwpasStKpiData> listDwpasStKpiData(List<String> kpiCodeList,
			String reportDate, String dateType) {
		this.logger.info("根据KPI编码(多个)，报表时间和时间类型查询KPI数据:" + reportDate);
		if (kpiCodeList == null || kpiCodeList.isEmpty()) {
			this.logger.error("查询指标数据,但指标参数为空");
			return null;
		}
		int kpiType = DwpasCKpiInfo.KPI_TYPE_OF_DAY;
		if (dateType.equalsIgnoreCase(DwpasStKpiData.DATE_TYPE_OF_MONTH)) {
			kpiType = DwpasCKpiInfo.KPI_TYPE_OF_MONTH;
		} else if (dateType.equalsIgnoreCase(DwpasStKpiData.DATE_TYPE_OF_WEEK)) {
			kpiType = DwpasCKpiInfo.KPI_TYPE_OF_WEEK;
		}
		return listDwpasStKpiDataByCodeList(kpiCodeList, reportDate,
				reportDate, kpiType);
	}

	/**
	 * 将kpiDate封装成ProkpiAnalyze
	 */
	@Override
	public List<ProkpiAnalyze> getHistory(List<String> codeList,
			String kpiType, String beginDateStr, String endDateStr) {
		if (codeList == null || codeList.isEmpty()) {
			this.logger.warn("将kpiDate封装成ProkpiAnalyze失败:codeList为空");
			return null;
		}
		if (!StringUtils.notNullAndSpace(kpiType)) {
			kpiType = String.valueOf(DwpasCKpiInfo.KPI_TYPE_OF_DAY);
		}
		if (StringUtils.notNullAndSpace(endDateStr)) {
			endDateStr = endDateStr.replace("-", "");
		}
		List<ProkpiAnalyze> results = new ArrayList<ProkpiAnalyze>();
		List<String> kpiNames = new ArrayList<String>();
		List<DwpasCKpiInfo> kpiInfoList = this.dwpasCKpiInfoService
				.listDwpasCKpiInfoByCodes(codeList);
		Map<String, List<DwpasStKpiData>> stKpiDataMap = listDwpasStKpiDataByKpiCode(
				kpiInfoList, beginDateStr, endDateStr,
				Integer.parseInt(kpiType));
		if (stKpiDataMap == null || stKpiDataMap.isEmpty()) {
			this.logger.warn("没有找到相关的数据");
			return null;
		}

		String dateFormat = "yyyyMM";
		if (Integer.valueOf(kpiType) == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
			dateFormat = "yyyyMMdd";
		}
		Date endDate = DateUtils.parseByFormatRule(endDateStr, dateFormat);
		Date beginDate = DateUtils.getPreviousMonth(endDate,
				Constants.TIME_SPAN_STOCK);

		if (stKpiDataMap == null || stKpiDataMap.isEmpty()) {
			this.logger.warn("查询指标数据失败,没有指标数据,将用默认数据");
			// 人造数据,防止出错
			stKpiDataMap = new HashMap<String, List<DwpasStKpiData>>();
			List<DwpasStKpiData> tmpKpiDataList = null;
			for (DwpasCKpiInfo kpiInfo : kpiInfoList) {
				tmpKpiDataList = new ArrayList<DwpasStKpiData>();
				while (beginDate.getTime() <= endDate.getTime()) {
					tmpKpiDataList.add(new DwpasStKpiData(kpiInfo, DateUtils
							.formatByFormatRule(beginDate, dateFormat),
							new BigDecimal(0)));
					beginDate = DateUtils.getNextMonth(beginDate);
				}
				//
				this.logger.info("用默认数据:"
						+ (tmpKpiDataList == null ? 0 : tmpKpiDataList.size()));
				stKpiDataMap.put(kpiInfo.getKpiCode(), tmpKpiDataList);
			}
		}
		List<DwpasStKpiData> tmpKpiDataList = null;
		Map.Entry<String, List<DwpasStKpiData>> entry = null;
		List<String> reportDateList = null;
		for (DwpasCKpiInfo kpiInfo : kpiInfoList) {
			if (!stKpiDataMap.containsKey(kpiInfo.getKpiCode())) {
				stKpiDataMap.put(kpiInfo.getKpiCode(),
						new ArrayList<DwpasStKpiData>());
			}
		}
		for (Iterator it = stKpiDataMap.entrySet().iterator(); it.hasNext();) {
			entry = (Map.Entry<String, List<DwpasStKpiData>>) it.next();
			tmpKpiDataList = entry.getValue();
			//
			reportDateList = new ArrayList<String>();
			beginDate = DateUtils.getPreviousMonth(endDate,
					Constants.TIME_SPAN_STOCK);
			for (DwpasStKpiData kpiData : tmpKpiDataList) {
				reportDateList.add(kpiData.getReportDate());
			}
			this.logger.info("---------->" + entry.getKey() + ","
					+ reportDateList.size());
			while (beginDate.getTime() <= endDate.getTime()) {
				if (!reportDateList.contains(DateUtils.formatByFormatRule(
						beginDate, dateFormat))) {
					this.logger.info("该日期没有数据:"
							+ DateUtils.formatByFormatRule(beginDate,
									dateFormat));
					stKpiDataMap.get(entry.getKey()).add(
							new DwpasStKpiData(new DwpasCKpiInfo(
									entry.getKey(), entry.getKey()), DateUtils
									.formatByFormatRule(beginDate, dateFormat),
									new BigDecimal(0)));
				}
				//
				beginDate = DateUtils.getNextMonth(beginDate);
			}
			// 数据再按日期进行排序
			Collections.sort(stKpiDataMap.get(entry.getKey()),
					new Comparator() {

						public int compare(Object o1, Object o2) {
							DwpasStKpiData data1 = (DwpasStKpiData) o1;
							DwpasStKpiData data2 = (DwpasStKpiData) o2;
							// 因为报表日期格式为yyyyMMdd或yyyyMM,故可转整数
							if (Integer.parseInt(data1.getReportDate()) > Integer
									.parseInt(data2.getReportDate())) {
								return 1;
							} else if (Integer.parseInt(data1.getReportDate()) < Integer
									.parseInt(data2.getReportDate())) {
								return -1;
							}
							return 0;
						}

					});
		}
		// 单个kpi的kpidata的个数
		int len = 0;
		if (stKpiDataMap != null) {
			len = stKpiDataMap.get(codeList.get(0)) == null ? 0 : stKpiDataMap
					.get(codeList.get(0)).size();
		}
		// 列名加单位
		for (DwpasCKpiInfo kpiInfo : kpiInfoList) {
			if (null != kpiInfo
					&& StringUtils.notNullAndSpace(kpiInfo.getDispName())) {
				if (kpiInfo.getIsPercent().equals("1")) {
					kpiNames.add(kpiInfo.getDispName() + "(%)");
				} else {
					kpiNames.add(kpiInfo.getDispName() + "("
							+ kpiInfo.getUnit() + ")");
				}
			}
		}
		// 封装成ProkpiAnalyze
		for (int i = 0; i < len; i++) {
			ProkpiAnalyze prokpiAnalyze = new ProkpiAnalyze();
			if (0 == i) {
				prokpiAnalyze.setKpiNames(kpiNames);
			}
			List<DwpasStKpiData> dskds = new ArrayList<DwpasStKpiData>();
			for (String kpiCode : codeList) {
				List<DwpasStKpiData> tmpList = stKpiDataMap.get(kpiCode);
				if (null != tmpList && tmpList.size() > i) {
					int index = len - 1 - i;
					if (index >= 0 && index < tmpList.size()) {
						dskds.add(tmpList.get(index));
						if (null == prokpiAnalyze.getDate()) {
							prokpiAnalyze.setDate(tmpList.get(index)
									.getReportDate());
						}
					} else {
						dskds.add(new DwpasStKpiData());
					}
				} else {
					dskds.add(new DwpasStKpiData());
				}
			}
			prokpiAnalyze.setDataList(dskds);
			results.add(prokpiAnalyze);
		}
		return results;
	}

	@Override
	public List<DwpasStKpiData> listDwpasStKpiDataByCodes(
			List<String> kpiCodeList, String reportDate) {
		this.logger.info("根据KPI编码(多个)，报表时间和时间类型查询KPI数据:" + reportDate);
		if (kpiCodeList == null || kpiCodeList.isEmpty()) {
			this.logger.error("查询指标数据,但指标参数为空");
			return null;
		}
		// 对日期格式进行转换
		if (reportDate != null && reportDate.indexOf("-") != -1) {
			reportDate = StringUtils.replace(reportDate, "-", "");
		}
		this.logger.debug("查询该日期之前的数据:" + reportDate);
		return this.listDwpasStKpiDataByCodeList(kpiCodeList, reportDate,
				reportDate, DwpasCKpiInfo.KPI_TYPE_OF_DAY);
	}

}
