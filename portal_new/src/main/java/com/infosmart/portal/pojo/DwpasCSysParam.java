package com.infosmart.portal.pojo;

/**
 * 系统指标信息 对应表:dwpas_c_sys_type
 * 
 * @author Administrator
 * 
 */
public class DwpasCSysParam {
	private String parmaId;
	private String parmaName;
	private String  paramValue;
	private String  getCreate;
	private String  getModified;
	public String getParmaId() {
		return parmaId;
	}
	public void setParmaId(String parmaId) {
		this.parmaId = parmaId;
	}
	public String getParmaName() {
		return parmaName;
	}
	public void setParmaName(String parmaName) {
		this.parmaName = parmaName;
	}
	public String getGetCreate() {
		return getCreate;
	}
	public void setGetCreate(String getCreate) {
		this.getCreate = getCreate;
	}
	public String getGetModified() {
		return getModified;
	}
	public void setGetModified(String getModified) {
		this.getModified = getModified;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

}
