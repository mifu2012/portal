package com.infosmart.portal.pojo;

import java.util.Date;
import java.util.List;

/**
 * 通用指标
 * 
 */
public class DwpasCComKpiInfo {

	/** 通用指标CODE */
	private String comKpiCode;

	/** 通用指标名称 */
	private String comKpiName;

	/** 是否在龙虎榜显示 */
	private String isShowRank;

	/** 龙虎榜排序 */
	private int rankShowOrder;

	/** 创建时间 */
	private Date gmtCreate;

	/** 修改时间 */
	private Date gmtModified;

	/** 通用指标关联的指标列表 */
	private List<DwpasCKpiInfo> kpiInfoList;

	/**
	 * Getter method for property <tt>comKpiCode</tt>.
	 * 
	 * @return property value of comKpiCode
	 */
	public String getComKpiCode() {
		return comKpiCode;
	}

	public List<DwpasCKpiInfo> getKpiInfoList() {
		return kpiInfoList;
	}

	public void setKpiInfoList(List<DwpasCKpiInfo> kpiInfoList) {
		this.kpiInfoList = kpiInfoList;
	}

	/**
	 * Setter method for property <tt>comKpiCode</tt>.
	 * 
	 * @param comKpiCode
	 *            value to be assigned to property comKpiCode
	 */
	public void setComKpiCode(String comKpiCode) {
		this.comKpiCode = comKpiCode;
	}

	/**
	 * Getter method for property <tt>comKpiName</tt>.
	 * 
	 * @return property value of comKpiName
	 */
	public String getComKpiName() {
		return comKpiName;
	}

	/**
	 * Setter method for property <tt>comKpiName</tt>.
	 * 
	 * @param comKpiName
	 *            value to be assigned to property comKpiName
	 */
	public void setComKpiName(String comKpiName) {
		this.comKpiName = comKpiName;
	}

	/**
	 * Getter method for property <tt>isShowRank</tt>.
	 * 
	 * @return property value of isShowRank
	 */
	public String getIsShowRank() {
		return isShowRank;
	}

	/**
	 * Setter method for property <tt>isShowRank</tt>.
	 * 
	 * @param isShowRank
	 *            value to be assigned to property isShowRank
	 */
	public void setIsShowRank(String isShowRank) {
		this.isShowRank = isShowRank;
	}

	/**
	 * Getter method for property <tt>rankShowOrder</tt>.
	 * 
	 * @return property value of rankShowOrder
	 */
	public int getRankShowOrder() {
		return rankShowOrder;
	}

	/**
	 * Setter method for property <tt>rankShowOrder</tt>.
	 * 
	 * @param rankShowOrder
	 *            value to be assigned to property rankShowOrder
	 */
	public void setRankShowOrder(int rankShowOrder) {
		this.rankShowOrder = rankShowOrder;
	}

	/**
	 * Getter method for property <tt>gmtCreate</tt>.
	 * 
	 * @return property value of gmtCreate
	 */
	public Date getGmtCreate() {
		return gmtCreate;
	}

	/**
	 * Setter method for property <tt>gmtCreate</tt>.
	 * 
	 * @param gmtCreate
	 *            value to be assigned to property gmtCreate
	 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	/**
	 * Getter method for property <tt>gmtModified</tt>.
	 * 
	 * @return property value of gmtModified
	 */
	public Date getGmtModified() {
		return gmtModified;
	}

	/**
	 * Setter method for property <tt>gmtModified</tt>.
	 * 
	 * @param gmtModified
	 *            value to be assigned to property gmtModified
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}