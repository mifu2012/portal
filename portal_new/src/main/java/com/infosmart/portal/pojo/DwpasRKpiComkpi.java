package com.infosmart.portal.pojo;

import java.util.Date;

/**
 * 指标与通用指标的关联
 * 
 * @author infosmart
 * 
 */
public class DwpasRKpiComkpi {
	private String kpiCode;
	private String comKpiCode;
	/** 创建时间 */
	private Date gmtCreate;

	/** 修改时间 */
	private Date gmtModified;

	public String getKpiCode() {
		return kpiCode;
	}

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

	public String getComKpiCode() {
		return comKpiCode;
	}

	public void setComKpiCode(String comKpiCode) {
		this.comKpiCode = comKpiCode;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}