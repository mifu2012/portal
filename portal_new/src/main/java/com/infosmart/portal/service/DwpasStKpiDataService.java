package com.infosmart.portal.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasStKpiData;
import com.infosmart.portal.pojo.ProkpiAnalyze;

/**
 * kpi数据
 * 
 * @author infosmart
 * 
 */
public interface DwpasStKpiDataService {
	/**
	 * 根据指标信息统计指标数据 (返回以KPI_CODE为主键的指标统计数据（MAP）)
	 * 
	 * @param kpiInfoList
	 * @param reportBeginDate
	 * @param reportEndDate
	 * @param kpiType
	 * @return
	 */
	Map<String, BigDecimal> statDwpasStKpiDataByKpiCode(
			List<DwpasCKpiInfo> kpiInfoList, String reportBeginDate,
			String reportEndDate, int kpiType);

	/**
	 * 根据某KPI指标列出某时间范围KPI数据
	 * 
	 * @param dwpasCKpiInfo
	 *            kpi信息
	 * @param reportBeginDate
	 *            开始时间 格式为yyyyMM
	 * @param reportEndDate
	 *            结束时间 格式为yyyyMM
	 * @return
	 */
	List<DwpasStKpiData> listDwpasStKpiData(DwpasCKpiInfo dwpasCKpiInfo,
			String reportBeginDate, String reportEndDate);

	/**
	 * 根据KPI编码(多个)，报表时间和时间类型查询KPI数据 (返回以KPI_CODE为主键的KPI数据集（MAP）)
	 * 
	 * @param kpiCodeList
	 * @param reportDate
	 * @param kpiType
	 *            数据类型
	 * @return 返回以KPI_CODE为主键的KPI数据集（MAP）
	 */
	Map<String, List<DwpasStKpiData>> listDwpasStKpiDataByKpiCode(
			List<DwpasCKpiInfo> kpiInfoList, String reportBeginDate,
			String reportEndDate, int kpiType);

	/**
	 * 根据KPI编码(多个)，报表时间和时间类型查询KPI数据 (返回以KPI_CODE为主键的KPI数据集（MAP）)
	 * 
	 * @param kpiCodeList
	 * @param reportDate
	 * @param dateType
	 *            数据类型:D/W/M
	 * @return 返回以KPI_CODE为主键的KPI数据集（MAP）
	 */
	Map<String, DwpasStKpiData> listDwpasStKpiDataByKpiCode(
			List<String> kpiCodeList, String reportDate, String dateType);

	/**
	 * 根据多个KPI指标列出某时间范KPI数据
	 * 
	 * @param kpiCodeList
	 * @param reportDate
	 * @param dateType
	 * @return
	 */
	List<DwpasStKpiData> listDwpasStKpiData(List<String> kpiCodeList,
			String reportDate, String dateType);

	/**
	 * 根据kpiCodeList查询指标信息
	 */
	List<DwpasStKpiData> listDwpasStKpiDataByCodes(List<String> kpiCodeList,
			String reportDate);

	/**
	 * 根据KPI指标和日期查询KPI数据
	 * 
	 * @param dwpasStKpiData
	 *            查询的kpi数据参数
	 * @return
	 */
	DwpasStKpiData listDwpasStKpiDataAndKpiInfo(DwpasCKpiInfo dwpasCKpiInfo,
			String reportDate);

	/**
	 * 
	 * 根据CodeList，日期类型，开始和结束时间查询ProkpiAnalyze
	 */
	List<ProkpiAnalyze> getHistory(List<String> CodeList, String kpiType,
			String beginDate, String endDate);
}
