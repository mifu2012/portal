package com.infosmart.portal.pojo.dwmis;

import java.util.List;

/**
 * 部门信息
 * 
 * @author infosmart
 * 
 */
public class DwmisMisDptmnt {
	private String depId;
	private String depName;
	private int depOrder;
	private int depGroupId;
	// 关联的KPI列表
	private List<DwmisKpiInfo> kpiInfoList;

	public List<DwmisKpiInfo> getKpiInfoList() {
		return kpiInfoList;
	}

	public void setKpiInfoList(List<DwmisKpiInfo> kpiInfoList) {
		this.kpiInfoList = kpiInfoList;
	}

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public int getDepOrder() {
		return depOrder;
	}

	public void setDepOrder(int depOrder) {
		this.depOrder = depOrder;
	}

	public int getDepGroupId() {
		return depGroupId;
	}

	public void setDepGroupId(int depGroupId) {
		this.depGroupId = depGroupId;
	}

}
