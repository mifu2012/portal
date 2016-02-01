package com.infosmart.portal.service.dwmis;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.infosmart.portal.pojo.dwmis.DwmisKpiData;
import com.infosmart.portal.pojo.dwmis.DwmisMisDptmnt;
import com.infosmart.portal.vo.dwmis.DwmisDeptKpiData;

public interface DwmisKpiDataService {
	/**
	 * 列出部门监控的KPI指标数据
	 * 
	 * @param reportDate
	 *            报表日期
	 * @return
	 */
	List<DwmisDeptKpiData> listDeptMonitorKpiData(
			List<DwmisMisDptmnt> deptInfoList, String queryDate);

	/**
	 * 查询某年某指标总量（额）
	 * 
	 * @param kpiCode
	 * @param yearNo
	 * @return
	 */
	BigDecimal getFullYearValueByKpiCode(String kpiCode, int yearNo);

	/**
	 * 根据KPI编码得到指标数据
	 * 
	 * @param DwmisKpiInfo
	 * @return
	 */
	List<DwmisKpiData> getKpiDataByCondition(List<String> kpiCodeList);

	/**
	 * 根据KPI编码(多个)，报表时间和时间类型查询KPI数据 (返回以KPI_CODE为主键的KPI数据集（MAP）)
	 * 
	 * @param kpiCodeList
	 * @param reportDate
	 * @param kpiType
	 *            数据类型
	 * @return 返回以KPI_CODE为主键的KPI数据集（MAP）
	 */
	Map<String, List<DwmisKpiData>> listdwmisKpiDataByKpiCode(
			List<DwmisKpiData> kpiDataList, String reportBeginDate,
			String reportEndDate, String dateType);

	/**
	 * 根据单个指标查询指标数据
	 * 
	 * @param dwmisKpiData
	 * @return
	 */
	List<DwmisKpiData> getKpiDataByKpiCode(DwmisKpiData dwmisKpiData,
			List<String> staCodeList);

	/**
	 * 查询指定指标某日期的指标数据
	 * 
	 * @param kpiCodeList
	 *            多个指标列表
	 * @param dateType
	 *            时间粒度
	 * @param staCode
	 *            统计类型
	 * @param reportDate
	 *            统计时间----将根据时间粒度转为相应的统计时间
	 * @return
	 */
	Map<String, DwmisKpiData> getKpiData(List<String> kpiCodeList,
			int dateType, int staCode, Date reportDate);

	/**
	 * 列出指定指标某段日期内的指标数据
	 * 
	 * @param kpiCodeList
	 *            多个指标列表
	 * @param dateType
	 *            时间粒度
	 * @param staCode
	 *            统计类型
	 * @param beginDate
	 *            开始时间----将根据时间粒度转为相应的统计时间
	 * @param endDate
	 *            结束时间----将根据时间粒度转为相应的统计时间
	 * @return
	 */
	Map<String, List<DwmisKpiData>> listKpiData(List<String> kpiCodeList,
			int dateType, int staCode, Date beginDate, Date endDate);

	/**
	 * 列出指定指标某段日期内的指标数据 Map 用日期reportDate作为Key
	 * 
	 * @param kpiCodeList
	 *            多个指标列表
	 * @param dateType
	 *            时间粒度
	 * @param staCode
	 *            统计类型
	 * @param beginDate
	 *            开始时间----将根据时间粒度转为相应的统计时间
	 * @param endDate
	 *            结束时间----将根据时间粒度转为相应的统计时间
	 * @return 返回以reportDate为主键的KPI数据集（MAP）
	 */
	Map<String, List<DwmisKpiData>> listKpiDataByDate(List<String> kpiCodeList,
			int dateType, int staCode, Date beginDate, Date endDate);

	/**
	 * 查询指标年累计值
	 * 
	 * @param kpiCode
	 *            指标
	 * @param dateType
	 *            时间粒度
	 * @param staCode
	 *            统计类型
	 * @param reportDate
	 *            统计时间----将根据时间粒度转为相应的统计时间
	 * @return
	 */

	Double getKpiDataValueByYear(String kpiCode, int dateType, int staCode,
			Date reportDate);

	/**
	 * 查询指标年月计值
	 * 
	 * @param kpiCode
	 *            指标
	 * @param dateType
	 *            时间粒度
	 * @param staCode
	 *            统计类型
	 * @param reportDate
	 *            统计时间----将根据时间粒度转为相应的统计时间
	 * @return
	 */
	Double getKpiDataValueByMonth(String kpiCode, int dateType, int staCode,
			Date reportDate);

	/**
	 * 查询指标年月计值
	 * 
	 * @param kpiCodeList
	 *            指标
	 * @param dateType
	 *            时间粒度
	 * @param staCode
	 *            统计类型
	 * @return
	 */
	List<DwmisKpiData> getKpiDataByParams(Object param,
			List<String> kpiCodeList, int dateType, int staCode,
			String beginDate, String endDate);

	/**
	 * 列出指定指标某段日期内的指标数据
	 * 
	 * @param kpiCode
	 *            指标
	 * @param dateType
	 *            时间粒度
	 * @param staCode
	 *            统计类型
	 * @param beginDate
	 *            开始时间----将根据时间粒度转为相应的统计时间
	 * @param endDate
	 *            结束时间----将根据时间粒度转为相应的统计时间
	 * @return
	 */
	List<DwmisKpiData> kpiDataListByParam(String kpiCode, int dateType,
			int staCode, Date beginDate, Date endDate);

	/**
	 * 根据KpiCode和日期查询指标数据
	 * 
	 * @param kpiCode
	 * @param reportDate
	 * @param dateType
	 * @param staCode
	 * @return
	 */
	List<DwmisKpiData> getKpiDataByCodeAndDate(String kpiCode, Date reportDate,
			int dateType, int staCode);
}
