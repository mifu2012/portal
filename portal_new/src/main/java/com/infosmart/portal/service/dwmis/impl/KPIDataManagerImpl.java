package com.infosmart.portal.service.dwmis.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.dwmis.DwmisKpiData;
import com.infosmart.portal.pojo.dwmis.DwmisKpiInfo;
import com.infosmart.portal.pojo.dwmis.KPIDataCheck;
import com.infosmart.portal.service.dwmis.DwmisKpiInfoService;
import com.infosmart.portal.service.dwmis.KPIDataManager;
import com.infosmart.portal.service.dwmis.SysDateForFixedYear;
import com.infosmart.portal.service.impl.BaseServiceImpl;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.dwmis.CoreConstant;
import com.infosmart.portal.util.dwmis.KPICommonQueryParam;
import com.infosmart.portal.util.dwmis.UnitTransformer;

@Service
public class KPIDataManagerImpl extends BaseServiceImpl implements
		KPIDataManager {

	@Autowired
	private DwmisKpiInfoService kpiInfoService;

	@Autowired
	private SysDateForFixedYear sysDateForFixedYear;

	@Override
	public double getKPIValue(String kpiCode) {
		this.logger.info("根据KPICODE获取对应的指标数据值:" + kpiCode);
		DwmisKpiInfo kpiInfo = this.kpiInfoService
				.getDwmisKpiInfoByCode(kpiCode);
		if (kpiInfo == null) {
			return CoreConstant.DEFAULT_DATA_NOT_FOUND;
		}
		double result = getKPIFullValue(kpiCode);
		return UnitTransformer.transform(result, kpiInfo.getSizeId(), kpiInfo);
	}

	@Override
	public KPIDataCheck checkKPIDataNullValue() {
		this.logger.info("统计指标各个字段有空值的记录数");
		return this.myBatisDao
				.get("com.infosmart.dwmis.DwmisMisKpiDataMapper.queryKPIDataNullValue",
						"");
	}

	@Override
	public List<String> getKPICodesForNullReportDate() {
		this.logger.info("查找（统计）数据时间为空的对应的无重复的指标CODE集");
		return this.myBatisDao
				.getList("com.infosmart.dwmis.DwmisMisKpiDataMapper.GET_KPI_CODE_FOR_NULL_REPORT_DATE");
	}

	@Override
	public List<String> getKPICodesForNullValue() {
		this.logger.info("查找（统计）指标数据值为空的对应的无重复的指标CODE集");
		return this.myBatisDao
				.getList("com.infosmart.dwmis.DwmisMisKpiDataMapper.GET_KPI_CODE_FOR_NULL_VALUE");
	}

	@Override
	public List<String> getKPICodesForNullDateType() {
		this.logger.info("查找（统计）时间粒度为空的对应的无重复的指标CODE集");
		return this.myBatisDao
				.getList("com.infosmart.dwmis.DwmisMisKpiDataMapper.GET_KPI_CODE_FOR_NULL_DATE_TYPE");
	}

	@Override
	public List<String> getKPICodesForNullStaCode() {
		this.logger.info("查找（统计）统计方式为空的对应的无重复的指标CODE集");
		return this.myBatisDao
				.getList("com.infosmart.dwmis.DwmisMisKpiDataMapper.GET_KPI_CODE_FOR_NULL_STA_CODE");
	}

	@Override
	public Double getLastAmountByKPICodePeriod(String kpiCode, int period,
			Date mySysDate) {
		this.logger.info("根据周期［" + period
				+ "］获取指定KPI Code 上期值（日为前天、周为上上周、月为上上月，都为当期值）");
		KPICommonQueryParam param = new KPICommonQueryParam();
		param.setKpiCode(kpiCode);
		param.setDateType(period);
		param.setMySysDate(mySysDate);

		Object result = this.myBatisDao
				.get("com.infosmart.dwmis.DwmisMisKpiDataMapper.GET_LAST_AMOUNT_BY_KPICODE_PERIOD",
						param);

		if (result == null) {
			return CoreConstant.DEFAULT_DATA_NOT_FOUND;
		}
		return (Double) result;
	}

	@Override
	public Double getCurrentAmountByKPICodePeriod(String kpiCode, int period,
			Date mySysDate) {
		this.logger.info("根据周期[" + period
				+ "]获取指定KPI Code当前值（日为昨天、周为上周、月为上月，都为当期值）");
		KPICommonQueryParam param = new KPICommonQueryParam();
		param.setKpiCode(kpiCode);
		param.setDateType(period);
		param.setMySysDate(mySysDate);

		Object result = this.myBatisDao
				.get("com.infosmart.dwmis.DwmisMisKpiDataMapper.GET_CURRENT_AMOUNT_BY_KPICODE_PERIOD",
						param);

		if (result == null) {
			return CoreConstant.DEFAULT_DATA_NOT_FOUND;
		}
		return (Double) result;
	}

	public Map<String, Double> listCurrentAmountByKPICodePeriod(
			KPICommonQueryParam dataQueryParam) {
		this.logger.info("根据周期获取指定KPI Code当前值（日为昨天、周为上周、月为上月，都为当期值）");
		List<DwmisKpiData> kpiDataList = this.myBatisDao
				.getList(
						"com.infosmart.dwmis.DwmisMisKpiDataMapper.LIST_CURRENT_AMOUNT_BY_KPICODE_PERIOD",
						dataQueryParam);
		if (kpiDataList == null || kpiDataList.isEmpty()) {
			return new HashMap<String, Double>();
		}
		Map<String, Double> kpiDataMap = new HashMap<String, Double>();
		for (DwmisKpiData kpiData : kpiDataList) {
			if (kpiDataMap.containsKey(kpiData.getKpiCode()))
				continue;
			kpiDataMap.put(
					kpiData.getKpiCode(),
					kpiData.getShowValue() == 0.0 ? 0.0 : kpiData
							.getShowValue());
		}
		return kpiDataMap;
	}

	@Override
	public Double getRecentValue(String kpiCode, int dateType, int staCode) {
		this.logger.info("根据日期粒度、统计方式获取前一天数据:" + kpiCode);
		DwmisKpiInfo kpiInfo = this.kpiInfoService
				.getDwmisKpiInfoByCode(kpiCode);
		if (kpiInfo == null) {
			return CoreConstant.DEFAULT_DATA_NOT_FOUND;
		}
		KPICommonQueryParam param = new KPICommonQueryParam();
		param.setKpiCode(kpiCode);
		param.setDateType(dateType);
		param.setStaCode(staCode);
		Date mySysDate = this.sysDateForFixedYear
				.getSysDateForFixedYear(kpiInfo.getDayOffSet());
		param.setMySysDate(mySysDate);
		return this.getRecentValueFromKPIData(param);
	}

	private Double getRecentValueFromKPIData(KPICommonQueryParam dataQueryParam) {
		if (dataQueryParam == null) {
			return CoreConstant.DEFAULT_DATA_NOT_FOUND;
		}
		this.logger.info("根据时间粒度，统计方式和KPICODE获取最近一天的指标数据:"
				+ dataQueryParam.getKpiCode());
		Object result = this.myBatisDao
				.get("com.infosmart.dwmis.DwmisMisKpiDataMapper.QUERY_KPIDATA_RECENT_VALUE",
						dataQueryParam);
		if (result == null) {
			return CoreConstant.DEFAULT_DATA_NOT_FOUND;
		}
		return (Double) result;
	}

	private Map<String, Double> listRecentValueFromKPIData(
			KPICommonQueryParam dataQueryParam) {
		if (dataQueryParam == null) {
			return new HashMap<String, Double>();
		}
		this.logger.info("根据时间粒度，统计方式和KPICODE获取最近一天的指标数据:"
				+ dataQueryParam.getKpiCodeList().toString());
		List<DwmisKpiData> kpiDataList = this.myBatisDao
				.getList(
						"com.infosmart.dwmis.DwmisMisKpiDataMapper.LIST_KPI_DATA_OF_RECENT",
						dataQueryParam);
		if (kpiDataList == null || kpiDataList.isEmpty()) {
			return new HashMap<String, Double>();
		}
		Map<String, Double> kpiDataMap = new HashMap<String, Double>();
		for (DwmisKpiData kpiData : kpiDataList) {
			if (kpiDataMap.containsKey(kpiData.getKpiCode()))
				continue;
			kpiDataMap.put(
					kpiData.getKpiCode(),
					kpiData.getShowValue() == 0.0 ? 0.0 : kpiData
							.getShowValue());
		}
		return kpiDataMap;
	}

	@Override
	public List<DwmisKpiInfo> getKPIInfo(String actionType) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DwmisKpiInfo> getKPINameByKPIName(String kpiName)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getKPIDataInfoByKPICode(String kpiCode)
			throws Exception {
		this.logger.info("根据指标code搜索相关指标数据信息:" + kpiCode);
		return this.myBatisDao
				.getList(
						"com.infosmart.dwmis.DwmisMisKpiDataMapper.LIST_KPICODE_OF_KPIDATA",
						kpiCode);
	}

	@Override
	public double getKPIValueByDetail(String kpiCode, Integer stateCode,
			Integer dateType, Date date) throws Exception {
		this.logger.info("根据指标的code，统计方式，时间粒度，时间获取指标的值:" + kpiCode);
		KPICommonQueryParam param = new KPICommonQueryParam();
		param.setCurrentDate(DateUtils.formatUtilDate(new Date(), "yyyyMMdd"));
		param.setMySysDate(new Date());
		param.setDateType(dateType);
		param.setStaCode(stateCode);
		param.setKpiCode(kpiCode);

		DwmisKpiData kpiDataDO = this.myBatisDao
				.get("com.infosmart.dwmis.DwmisMisKpiDataMapper.GET_KPIDATA_ON_CONDITION",
						param);
		return kpiDataDO.getShowValue() == 0.0 ? 0.0 : kpiDataDO.getShowValue();
	}

	@Override
	public Double getCurrentAmount(String kpiCode, int period) {
		this.logger.info("获取当期值，使用跨年方案时间源:" + kpiCode);
		DwmisKpiInfo kpiInfo = this.kpiInfoService
				.getDwmisKpiInfoByCode(kpiCode);
		if (kpiInfo == null) {
			return CoreConstant.DEFAULT_DATA_NOT_FOUND;
		}
		Date mySysDate = this.sysDateForFixedYear
				.getSysDateForFixedYear(kpiInfo.getDayOffSet());

		double result = getCurrentAmountByKPICodePeriod(kpiInfo.getKpiCode(),
				period, mySysDate).doubleValue();
		return Double.valueOf(result);
	}

	private Double getCurrentMaxValueFromKPIData(
			KPICommonQueryParam dataQueryParam) {
		if (dataQueryParam == null) {
			return CoreConstant.DEFAULT_DATA_NOT_FOUND;
		}
		this.logger.info("根据时间粒度，统计方式和KPICODE获取指标数据（峰值）:"
				+ dataQueryParam.getKpiCode());
		DwmisKpiData kpiDataDO = this.myBatisDao
				.get("com.infosmart.dwmis.DwmisMisKpiDataMapper.GET_KPIDATA_ON_CONDITION",
						dataQueryParam);
		return kpiDataDO.getShowValue() == 0.0 ? 0.0 : kpiDataDO.getShowValue();
	}

	/**
	 * 根据条件查询多个特定的指标数据
	 * 
	 * @param dataQueryParam
	 * @return
	 */
	private Map<String, Double> listCurrentMaxValueFromKPIData(
			KPICommonQueryParam dataQueryParam) {
		if (dataQueryParam == null) {
			return new HashMap<String, Double>();
		}
		this.logger.info("根据时间粒度，统计方式和KPICODE获取指标数据（峰值）:"
				+ dataQueryParam.getKpiCode());
		this.logger.info("根据时间粒度，统计方式和KPICODE获取指标数据（峰值）:"
				+ DateUtils.fotmatDate4(dataQueryParam.getMySysDate()));
		List<DwmisKpiData> kpiDataList = this.myBatisDao
				.getList(
						"com.infosmart.dwmis.DwmisMisKpiDataMapper.LIST_KPIDATA_ON_CONDITION",
						dataQueryParam);
		if (kpiDataList == null || kpiDataList.isEmpty())
			return new HashMap<String, Double>();
		Map<String, Double> kpiDataMap = new HashMap<String, Double>();
		for (DwmisKpiData kpiData : kpiDataList) {
			if (kpiDataMap.containsKey(kpiData.getKpiCode())) {
				continue;
			}
			kpiDataMap.put(kpiData.getKpiCode(), kpiData.getShowValue());
		}
		return kpiDataMap;
	}

	private Double getCurrentMinValueFromKPIData(
			KPICommonQueryParam dataQueryParam) {
		if (dataQueryParam == null) {
			return CoreConstant.DEFAULT_DATA_NOT_FOUND;
		}
		this.logger.info("根据时间粒度，统计方式和KPICODE获取指标数据（谷值）:"
				+ dataQueryParam.getKpiCode());
		DwmisKpiData kpiDataDO = this.myBatisDao
				.get("com.infosmart.dwmis.DwmisMisKpiDataMapper.GET_KPIDATA_ON_CONDITION",
						dataQueryParam);
		return kpiDataDO.getShowValue() == 0.0 ? 0.0 : kpiDataDO.getShowValue();
	}

	private Map<String, Double> listCurrentMinValueFromKPIData(
			KPICommonQueryParam dataQueryParam) {
		if (dataQueryParam == null) {
			return new HashMap<String, Double>();
		}
		this.logger.info("根据时间粒度，统计方式和KPICODE获取指标数据（谷值）:"
				+ dataQueryParam.getKpiCode());
		List<DwmisKpiData> kpiDataList = this.myBatisDao
				.getList(
						"com.infosmart.dwmis.DwmisMisKpiDataMapper.LIST_KPIDATA_ON_CONDITION",
						dataQueryParam);
		if (kpiDataList == null || kpiDataList.isEmpty()) {
			return new HashMap<String, Double>();
		}
		Map<String, Double> kpiDataMap = new HashMap<String, Double>();
		for (DwmisKpiData kpiData : kpiDataList) {
			if (kpiDataMap.containsKey(kpiData.getKpiCode())) {
				continue;
			}
			kpiDataMap.put(
					kpiData.getKpiCode(),
					kpiData.getShowValue() == 0.0 ? 0.0 : kpiData
							.getShowValue());
		}
		return kpiDataMap;
	}

	/**
	 * 最新日期的数据
	 * 
	 * @return
	 */
	private Double getLastKPIDataOnCondition(KPICommonQueryParam dataQueryParam) {
		if (dataQueryParam == null) {
			return CoreConstant.DEFAULT_DATA_NOT_FOUND;
		}
		this.logger.info("根据最新日期的数据获取指标的值:" + dataQueryParam.getKpiCode());
		DwmisKpiData kpiDataDO = this.myBatisDao
				.get("com.infosmart.dwmis.DwmisMisKpiDataMapper.GET_LAST_KPIDATA_ON_CONDITION",
						dataQueryParam);
		return kpiDataDO == null ? 0 : kpiDataDO.getShowValue();
	}

	/**
	 * 列出多个指标的KPI指标最新数据
	 * 
	 * @param dataQueryParam
	 * @return Map<String, Double> key:kpi_code,val:kpi_data_value
	 */
	private Map<String, Double> listLastKPIDataOnCondition(
			KPICommonQueryParam dataQueryParam) {
		if (dataQueryParam == null)
			return new HashMap<String, Double>();
		this.logger.info("列出多个指标最新的数据:"
				+ dataQueryParam.getKpiCodeList().toString());
		List<DwmisKpiData> kpiDataList = this.myBatisDao
				.getList(
						"com.infosmart.dwmis.DwmisMisKpiDataMapper.ListLastKpiDataByCondition",
						dataQueryParam);
		if (kpiDataList == null || kpiDataList.isEmpty())
			return new HashMap<String, Double>();
		Map<String, Double> lastKpiDataMap = new HashMap<String, Double>();
		for (DwmisKpiData kpiData : kpiDataList) {
			if (lastKpiDataMap.containsKey(kpiData.getKpiCode())) {
				continue;
			}
			lastKpiDataMap.put(
					kpiData.getKpiCode(),
					kpiData.getShowValue() == 0.0 ? 0.0 : kpiData
							.getShowValue());
		}
		return lastKpiDataMap;
	}

	@Override
	public double getKPIFullValue(DwmisKpiInfo kpiInfo) {
		if (kpiInfo == null) {
			return CoreConstant.DEFAULT_DATA_NOT_FOUND;
		}
		this.logger.info("根据KPICODE获取对应的指标数据值 本方法将返回整个数值，不经过单位数量级转换:"
				+ kpiInfo.getKpiCode());
		KPICommonQueryParam dataQueryParam = new KPICommonQueryParam();
		dataQueryParam.setKpiCode(kpiInfo.getKpiCode());
		dataQueryParam.setFixedYear(this.sysDateForFixedYear.getFixedYear());

		double result = 4.9E-324D;

		if ((kpiInfo.getGoalType().intValue() == 3001)
				|| (kpiInfo.getGoalType().intValue() == 30011)
				|| (kpiInfo.getGoalType().intValue() == 30012)) {
			dataQueryParam.setDateType(Integer.valueOf(1004));

			dataQueryParam.setStaCode(Integer.valueOf(2001));
			result = getCurrentMaxValueFromKPIData(dataQueryParam)
					.doubleValue();
		} else if ((kpiInfo.getGoalType().intValue() == 3002)
				|| (kpiInfo.getGoalType().intValue() == 30021)) {
			dataQueryParam.setDateType(Integer.valueOf(1004));

			dataQueryParam.setStaCode(Integer.valueOf(2001));
			result = getCurrentMinValueFromKPIData(dataQueryParam)
					.doubleValue();
		} else if ((kpiInfo.getGoalType().intValue() == 3005)
				|| (kpiInfo.getGoalType().intValue() == 30052)
				|| (kpiInfo.getGoalType().intValue() == 30051)) {
			dataQueryParam.setDateType(Integer.valueOf(1002));

			dataQueryParam.setStaCode(Integer.valueOf(2002));

			Date mySysDate = this.sysDateForFixedYear
					.getSysDateForFixedYear(kpiInfo.getDayOffSet());

			dataQueryParam.setMySysDate(mySysDate);
			result = getRecentValueFromKPIData(dataQueryParam).doubleValue();
		} else if (kpiInfo.getGoalType().intValue() == 3007) {
			dataQueryParam.setDateType(Integer.valueOf(1002));

			dataQueryParam.setStaCode(Integer.valueOf(2006));
			result = getCurrentMaxValueFromKPIData(dataQueryParam)
					.doubleValue();
		} else if (kpiInfo.getGoalType().intValue() == 3008) {
			Date mySysDate = this.sysDateForFixedYear
					.getSysDateForFixedYear(kpiInfo.getDayOffSet());

			result = getCurrentAmountByKPICodePeriod(kpiInfo.getKpiCode(),
					1004, mySysDate).doubleValue();
		} else if (kpiInfo.getGoalType().intValue() == 3009) {
			dataQueryParam.setDateType(Integer.valueOf(1004));

			dataQueryParam.setStaCode(Integer.valueOf(2002));

			result = getLastKPIDataOnCondition(dataQueryParam).doubleValue();
		}

		return result;
	}

	@Override
	public Map<String, Double> getMultiKpiFullValue(
			List<DwmisKpiInfo> kpiInfoList, String queryDate) {
		if (kpiInfoList == null || kpiInfoList.isEmpty()) {
			this.logger.warn("查询指标数据值kpiInfoList 为空！！！");
			return null;
		}
		// 查询指标数据
		Map<String, Double> kpiValueMap = new HashMap<String, Double>();
		// 当前日峰值 dateType:1002(日),staCode:2006(某月四周日平均)
		KPICommonQueryParam queryParam_MaxValueOfDay = new KPICommonQueryParam();
		// 月峰值 dateType:1004(月),staCode:2001(当期值)
		KPICommonQueryParam queryParam_MaxValueOfMonth = new KPICommonQueryParam();
		// 月谷值 dateType:1004(月) staCode:2001(当期值)
		KPICommonQueryParam queryParam_MinValueOfMonth = new KPICommonQueryParam();
		// 获取最近一天的指标数据 dateType:1002(日) staCode:2002(期末值)
		KPICommonQueryParam queryParam_RecentValueOfDay = new KPICommonQueryParam();
		// 当前值　dateType:1004(月),staCode:2001(当期值)
		KPICommonQueryParam queryParam_CurrentValueOfMonth = new KPICommonQueryParam();
		// 最新值 dateType:1004(月),staCode:2002(期末值)
		KPICommonQueryParam queryParam_LastlyValueOfMonth = new KPICommonQueryParam();
		DwmisKpiInfo kpiInfo = null;
		for (int i = 0; i < kpiInfoList.size(); i++) {
			kpiInfo = kpiInfoList.get(i);
			if (kpiInfo == null || kpiInfo.getGoalType() == null)
				continue;
			this.logger.info("--->查询指标数据：" + kpiInfo.getKpiCode());
			if ((kpiInfo.getGoalType().intValue() == 3001)
					|| (kpiInfo.getGoalType().intValue() == 30011)
					|| (kpiInfo.getGoalType().intValue() == 30012)) {
				// 3001月峰值 30011月峰值-pt 30012 月峰值-计去年
				queryParam_MaxValueOfMonth.setMySysDate(DateUtils
						.parseByFormatRule(queryDate, "yyyy-MM-dd"));
				queryParam_MaxValueOfMonth.setDateType(Integer.valueOf(1004));
				queryParam_MaxValueOfMonth.setStaCode(Integer.valueOf(2001));
				// 查询月峰值
				this.logger.info("查询月峰值:" + kpiInfo.getKpiCode());
				queryParam_MaxValueOfMonth.setKpiCodeList(kpiInfo.getKpiCode());
				/* if (i == kpiInfoList.size() - 1) { */// 去掉有用了
				kpiValueMap
						.putAll(listCurrentMaxValueFromKPIData(queryParam_MaxValueOfMonth));
				/* } */
			} else if ((kpiInfo.getGoalType().intValue() == 3002)
					|| (kpiInfo.getGoalType().intValue() == 30021)) {
				// 3002 月谷值 30021 月谷值-pt

				queryParam_MinValueOfMonth.setMySysDate(DateUtils
						.parseByFormatRule(queryDate, "yyyy-MM-dd"));
				queryParam_MinValueOfMonth.setDateType(Integer.valueOf(1004));
				queryParam_MinValueOfMonth.setStaCode(Integer.valueOf(2001));
				// 查询谷值
				this.logger.info("查询月谷值:" + kpiInfo.getKpiCode());
				queryParam_MinValueOfMonth.setKpiCodeList(kpiInfo.getKpiCode());
				/* if (i == kpiInfoList.size() - 1) { */
				kpiValueMap
						.putAll(listCurrentMinValueFromKPIData(queryParam_MinValueOfMonth));
				/* } */
			} else if ((kpiInfo.getGoalType().intValue() == 3005)
					|| (kpiInfo.getGoalType().intValue() == 30052)
					|| (kpiInfo.getGoalType().intValue() == 30051)) {
				// 3005日积累值 30051日积累值-pt 30052 日积累值-计去年
				queryParam_RecentValueOfDay.setDateType(Integer.valueOf(1002));
				queryParam_RecentValueOfDay.setStaCode(Integer.valueOf(2002));
				Date mySysDate = DateUtils.parseByFormatRule(queryDate,
						"yyyy-MM-dd");
				queryParam_RecentValueOfDay.setMySysDate(mySysDate);
				// 获取最近一天的指标数据
				this.logger.info("获取最近一天的指标数据:" + kpiInfo.getKpiCode());
				queryParam_RecentValueOfDay
						.setKpiCodeList(kpiInfo.getKpiCode());
				/* if (i == kpiInfoList.size() - 1) { */
				kpiValueMap
						.putAll(this
								.listRecentValueFromKPIData(queryParam_RecentValueOfDay));
				/* } */
			} else if (kpiInfo.getGoalType().intValue() == 3007) {
				// 3007(七日均值峰值)
				queryParam_MaxValueOfDay.setMySysDate(DateUtils
						.parseByFormatRule(queryDate, "yyyy-MM-dd"));
				queryParam_MaxValueOfDay.setDateType(Integer.valueOf(1002));

				queryParam_MaxValueOfDay.setStaCode(Integer.valueOf(2006));
				// 日峰值
				this.logger.info("日峰值:" + kpiInfo.getKpiCode());
				queryParam_MaxValueOfDay.setKpiCodeList(kpiInfo.getKpiCode());
				/* if (i == kpiInfoList.size() - 1) { */
				kpiValueMap
						.putAll(listCurrentMaxValueFromKPIData(queryParam_MaxValueOfDay));
				// }
			} else if (kpiInfo.getGoalType().intValue() == 3008) {
				// 3008(月当值)
				Date mySysDate = DateUtils.parseByFormatRule(queryDate,
						"yyyy-MM-dd");
				queryParam_CurrentValueOfMonth.setMySysDate(mySysDate);
				queryParam_CurrentValueOfMonth.setDateType(1004);
				queryParam_CurrentValueOfMonth.setStaCode(2001);
				// 当前值
				this.logger.info("当前值:" + kpiInfo.getKpiCode());
				queryParam_CurrentValueOfMonth.setKpiCodeList(kpiInfo
						.getKpiCode());
				// if (i == kpiInfoList.size() - 1) {
				kpiValueMap
						.putAll(listCurrentAmountByKPICodePeriod(queryParam_CurrentValueOfMonth));
				// }
			} else if (kpiInfo.getGoalType().intValue() == 3009) {
				// 3009(月末值)
				Date mySysDate = DateUtils.parseByFormatRule(queryDate,
						"yyyy-MM-dd");
				queryParam_LastlyValueOfMonth.setMySysDate(mySysDate);
				queryParam_LastlyValueOfMonth
						.setDateType(Integer.valueOf(1004));

				queryParam_LastlyValueOfMonth.setStaCode(Integer.valueOf(2002));
				// 最新值
				this.logger.info("最新值:" + kpiInfo.getKpiCode());
				queryParam_LastlyValueOfMonth.setKpiCodeList(kpiInfo
						.getKpiCode());
				// if (i == kpiInfoList.size() - 1) {
				kpiValueMap
						.putAll(this
								.listLastKPIDataOnCondition(queryParam_LastlyValueOfMonth));
				// } //?
			}
		}
		return kpiValueMap;
	}

	@Override
	public double getKPIFullValue(String kpiCode) {
		this.logger
				.info("根据KPICODE获取对应的指标数据值 本方法将返回整个数值，不经过单位数量级转换:" + kpiCode);
		DwmisKpiInfo kpiInfo = this.kpiInfoService
				.getDwmisKpiInfoByCode(kpiCode);
		if (kpiInfo == null) {
			return CoreConstant.DEFAULT_DATA_NOT_FOUND;
		}
		return this.getKPIFullValue(kpiInfo);
	}

}
