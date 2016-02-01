package com.infosmart.portal.pojo;

/**
 * 产品与指标关系
 * 
 * @author infosmart
 * 
 */
public class DwpasRPrdKpi {
	// 产品
	private String productId;
	// 指标
	private String kpiCode;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getKpiCode() {
		return kpiCode;
	}

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

}