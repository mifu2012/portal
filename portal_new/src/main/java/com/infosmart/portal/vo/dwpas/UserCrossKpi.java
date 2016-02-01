package com.infosmart.portal.vo.dwpas;

/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2011 All Rights Reserved.
 */


import java.util.List;

import com.infosmart.portal.pojo.DwpasStKpiData;

/**
 * 产品龙虎榜DTO
 * 
 * @author hongbi.wang
 * @version $Id: ProdRankDto.java, v 0.1 2011-10-11 下午02:53:28 hongbi.wang Exp $
 */
public class UserCrossKpi {

	/** 平台类型 */
	private String type;

	/** 平台名称 */
	private String flatName;

	/** 指标值 */
	private List<DwpasStKpiData> kpiDatas;

	/**
	 * Getter method for property <tt>type</tt>.
	 * 
	 * @return property value of type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Setter method for property <tt>type</tt>.
	 * 
	 * @param type
	 *            value to be assigned to property type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Getter method for property <tt>flatName</tt>.
	 * 
	 * @return property value of flatName
	 */
	public String getFlatName() {
		return flatName;
	}

	/**
	 * Setter method for property <tt>flatName</tt>.
	 * 
	 * @param flatName
	 *            value to be assigned to property flatName
	 */
	public void setFlatName(String flatName) {
		this.flatName = flatName;
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
