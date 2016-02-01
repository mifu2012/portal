package com.infosmart.po.dwmis;

/**
 * 维度关联表
 * 
 * @author hgt
 * 
 */
public class DwmisKpiDmnsnr {
	private String kpiCode;
	private Integer dmnsnId;
	private String subKpiCode;

	public String getKpiCode() {
		return kpiCode;
	}

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

	public Integer getDmnsnId() {
		return dmnsnId;
	}

	public void setDmnsnId(Integer dmnsnId) {
		this.dmnsnId = dmnsnId;
	}

	public String getSubKpiCode() {
		return subKpiCode;
	}

	public void setSubKpiCode(String subKpiCode) {
		this.subKpiCode = subKpiCode;
	}
}
