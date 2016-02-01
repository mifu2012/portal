package com.infosmart.service.dwmis;

import java.util.List;

import com.infosmart.po.dwmis.DwmisKpiGoal;

public interface KpiGoalService {
	/**
	 * 查询年指标数据
	 * 
	 * @param kpiCode
	 * @return
	 */
	public DwmisKpiGoal getKPIGoal(String kpiCode);

	/**
	 * 查询月指标数据
	 * 
	 * @param kpiCode
	 * @return
	 */
	public List<DwmisKpiGoal> getKPIGoals(String kpiCode);

	/**
	 * 添加指标绩效记录
	 */
	public void addKPIGoal(List<DwmisKpiGoal> kpiGoalList, String goalType);

	/**
	 * 修改指标绩效记录
	 */
	public void updateKPIGoal(List<DwmisKpiGoal> kpiGoalList, String goalType);

	/**
	 * 删除绩效信息
	 * 
	 * @param dwmisKpiGoal
	 */
	public void deleteKpiGoal(DwmisKpiGoal dwmisKpiGoal);

}
