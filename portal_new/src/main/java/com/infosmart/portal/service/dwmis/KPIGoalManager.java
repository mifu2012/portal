package com.infosmart.portal.service.dwmis;

import java.util.List;
import java.util.Map;

import com.infosmart.portal.pojo.dwmis.DwmisKpiGoal;
import com.infosmart.portal.pojo.dwmis.DwmisKpiInfo;

public interface KPIGoalManager {
	/**
	 * 
	 * 根据指标CODE获取当前年度的绩效目标
	 * 
	 * @param kpiCode
	 * @return
	 */
	public DwmisKpiGoal getKPIGoalOnCurrentYearByKPICode(String kpiCode);

	/**
	 * 
	 * 根据多个指标CODE获取当前年度的绩效目标
	 * 
	 * @param kpiCode
	 * @return
	 */
	Map<String, DwmisKpiGoal> listKPIGoalOnCurrentYear(
			List<DwmisKpiInfo> kpiIngfoList,String queryDate);

	/**
	 * 根据kpicode获取相关的指标绩效记录(十二个月记录)
	 * 
	 * @param kpiCode
	 * @return
	 * @throws Exception
	 */
	public List<DwmisKpiGoal> getKPIGoals(String kpiCode) throws Exception;

	/**
	 * 根据kpicode获取相关的指标绩效记录(年纪录)
	 * 
	 * @param kpiCode
	 * @return
	 * @throws Exception
	 */
	public DwmisKpiGoal getKPIGoal(String kpiCode) throws Exception;

	/**
	 * 修订了 getKPIGoalOnCurrentYearByKPICode(String kpiCode)
	 * 获取目标值的缺陷，无值时返回double最小值
	 * 
	 * @param kpiCode
	 * @return
	 */
	public DwmisKpiGoal getKPIGoalOnCurrentYearByKPICodeWithPatch(String kpiCode);

	/**
	 * 根据KpiCode和时间取绩效信息
	 * @param kpiCode
	 * @param date
	 * @return
	 */
	public DwmisKpiGoal getKpiGoalByKpiCodeAndDate(String kpiCode, String date);
}
