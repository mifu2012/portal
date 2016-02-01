package com.infosmart.portal.pojo;

/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2011 All Rights Reserved.
 */


import java.util.List;

/**
 * 产品指标模型
 * 
 * @author hongbi.wang
 * @version $Id: ProdKpiInfoDTO.java, v 0.1 2011-10-12 下午06:19:02 hongbi.wang
 *          Exp $
 */
public class ProdKpiInfoDTO {

	/** 产品名称 */
	private String prodName;

	/** 产品指标值 */
	private List<DwpasStKpiData> kpiDatas;
	/** 产品id */
	private String prodId;

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * Getter method for property <tt>prodName</tt>.
	 * 
	 * @return property value of prodName
	 */
	public String getProdName() {
		return prodName;
	}

	/**
	 * Setter method for property <tt>prodName</tt>.
	 * 
	 * @param prodName
	 *            value to be assigned to property prodName
	 */
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public List<DwpasStKpiData> getKpiDatas() {
		return kpiDatas;
	}

	public void setKpiDatas(List<DwpasStKpiData> kpiDatas) {
		this.kpiDatas = kpiDatas;
	}

	/**
	 * Getter method for property <tt>kpiDatas</tt>.
	 * 
	 * @return property value of kpiDatas
	 */
	

}
