package com.infosmart.portal.vo.dwmis;

import com.infosmart.portal.pojo.dwmis.DwmisMisDptmnt;

/**
 * 部门KPI数据值
 * 
 * @author infosmart
 * 
 */
public class DwmisDeptKpiData {
	/**
	 * 部门信息
	 */
	private DwmisMisDptmnt dwmisMisDptmnt;

	// 指标走势
	private KPITrends kpiTreands;

	public DwmisDeptKpiData(DwmisMisDptmnt dwmisMisDptmnt, KPITrends kpiTreands) {
		super();
		this.dwmisMisDptmnt = dwmisMisDptmnt;
		this.kpiTreands = kpiTreands;
	}

	public DwmisMisDptmnt getDwmisMisDptmnt() {
		return dwmisMisDptmnt;
	}

	public void setDwmisMisDptmnt(DwmisMisDptmnt dwmisMisDptmnt) {
		this.dwmisMisDptmnt = dwmisMisDptmnt;
	}

	public KPITrends getKpiTreands() {
		return kpiTreands;
	}

	public void setKpiTreands(KPITrends kpiTreands) {
		this.kpiTreands = kpiTreands;
	}

}
