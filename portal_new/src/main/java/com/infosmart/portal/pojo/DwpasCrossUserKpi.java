package com.infosmart.portal.pojo;

import java.util.Date;

/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2011 All Rights Reserved.
 */


/**
 * 交叉用户指标模型
 * 
 * @author hongbi.wang
 * @version $Id: CrossUserKpiDTO.java, v 0.1 2011-10-12 下午02:06:24 hongbi.wang
 *          Exp $
 */
public class DwpasCrossUserKpi {
	private String id;

	/**
	 * This property corresponds to db column <tt>KPI_NAME</tt>.
	 */
	private String kpiName;

	/**
	 * This property corresponds to db column <tt>TAOBAO_CODE</tt>.
	 */
	private String taobaoCode;

	/**
	 * This property corresponds to db column <tt>ALIPAY_CODE</tt>.
	 */
	private String alipayCode;

	/**
	 * This property corresponds to db column <tt>OUT_CODE</tt>.
	 */
	private String outCode;

	/**
	 * This property corresponds to db column <tt>TAOBAO_ONLY_CODE</tt>.
	 */
	private String taobaoOnlyCode;

	/**
	 * This property corresponds to db column <tt>ALIPAY_ONLY_CODE</tt>.
	 */
	private String alipayOnlyCode;

	/**
	 * This property corresponds to db column <tt>OUT_ONLY_CODE</tt>.
	 */
	private String outOnlyCode;

	/**
	 * This property corresponds to db column <tt>TAOBAO_ALIPAY_CODE</tt>.
	 */
	private String taobaoAlipayCode;

	/**
	 * This property corresponds to db column <tt>ALIPAY_OUT_CODE</tt>.
	 */
	private String alipayOutCode;

	/**
	 * This property corresponds to db column <tt>TAOBAO_OUT_CODE</tt>.
	 */
	private String taobaoOutCode;

	/**
	 * This property corresponds to db column <tt>ALL_CODE</tt>.
	 */
	private String allCode;

	/**
	 * This property corresponds to db column <tt>GMT_CREATE</tt>.
	 */
	private Date gmtCreate;

	/**
	 * This property corresponds to db column <tt>GMT_MODIFIED</tt>.
	 */
	private Date gmtModified;

	/** 淘宝总数指标 */
	private DwpasStKpiData tabaoKpiData;


	/** 站内指标 */
	private DwpasStKpiData alipayKpiData;

	/** 站外指标 */
	private DwpasStKpiData outKpiData;

	/** 仅使用淘宝CODE */
	private String onlyTaobaoCode;

	/** 仅使用淘宝指标 */
	private DwpasStKpiData onlyTaobaoKpiData;

	/** 仅使用站内 */
	private String onlyAlipayCode;

	/** 仅使用站内指标 */
	private DwpasStKpiData onlyAlipayKpiData;

	/** 仅使用 站外CODE */
	private String onlyOutCode;

	/** 仅使用站外指标 */
	private DwpasStKpiData onlyOutKpiData;

	/** 淘宝 + 站内 指标 */
	private DwpasStKpiData taobaoAlipayData;

	/** 站内 + 站外指标 */
	private DwpasStKpiData alipayOutKpiData;

	/** 淘宝 + 站外 指标 */
	private DwpasStKpiData taobaoOutData;

	/** 站内 + 淘宝 + 站外 指标 */
	private DwpasStKpiData allKpiData;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKpiName() {
		return kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	public String getTaobaoCode() {
		return taobaoCode;
	}

	public void setTaobaoCode(String taobaoCode) {
		this.taobaoCode = taobaoCode;
	}

	public String getAlipayCode() {
		return alipayCode;
	}

	public void setAlipayCode(String alipayCode) {
		this.alipayCode = alipayCode;
	}

	public String getOutCode() {
		return outCode;
	}

	public void setOutCode(String outCode) {
		this.outCode = outCode;
	}

	public String getTaobaoOnlyCode() {
		return taobaoOnlyCode;
	}

	public void setTaobaoOnlyCode(String taobaoOnlyCode) {
		this.taobaoOnlyCode = taobaoOnlyCode;
	}

	public String getAlipayOnlyCode() {
		return alipayOnlyCode;
	}

	public void setAlipayOnlyCode(String alipayOnlyCode) {
		this.alipayOnlyCode = alipayOnlyCode;
	}

	public String getOutOnlyCode() {
		return outOnlyCode;
	}

	public void setOutOnlyCode(String outOnlyCode) {
		this.outOnlyCode = outOnlyCode;
	}

	public String getTaobaoAlipayCode() {
		return taobaoAlipayCode;
	}

	public void setTaobaoAlipayCode(String taobaoAlipayCode) {
		this.taobaoAlipayCode = taobaoAlipayCode;
	}

	public String getAlipayOutCode() {
		return alipayOutCode;
	}

	public void setAlipayOutCode(String alipayOutCode) {
		this.alipayOutCode = alipayOutCode;
	}

	public String getTaobaoOutCode() {
		return taobaoOutCode;
	}

	public void setTaobaoOutCode(String taobaoOutCode) {
		this.taobaoOutCode = taobaoOutCode;
	}

	public String getAllCode() {
		return allCode;
	}

	public void setAllCode(String allCode) {
		this.allCode = allCode;
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

	public DwpasStKpiData getTabaoKpiData() {
		return tabaoKpiData;
	}

	public void setTabaoKpiData(DwpasStKpiData tabaoKpiData) {
		this.tabaoKpiData = tabaoKpiData;
	}

	public DwpasStKpiData getAlipayKpiData() {
		return alipayKpiData;
	}

	public void setAlipayKpiData(DwpasStKpiData alipayKpiData) {
		this.alipayKpiData = alipayKpiData;
	}

	public DwpasStKpiData getOutKpiData() {
		return outKpiData;
	}

	public void setOutKpiData(DwpasStKpiData outKpiData) {
		this.outKpiData = outKpiData;
	}

	public String getOnlyTaobaoCode() {
		return onlyTaobaoCode;
	}

	public void setOnlyTaobaoCode(String onlyTaobaoCode) {
		this.onlyTaobaoCode = onlyTaobaoCode;
	}

	public DwpasStKpiData getOnlyTaobaoKpiData() {
		return onlyTaobaoKpiData;
	}

	public void setOnlyTaobaoKpiData(DwpasStKpiData onlyTaobaoKpiData) {
		this.onlyTaobaoKpiData = onlyTaobaoKpiData;
	}

	public String getOnlyAlipayCode() {
		return onlyAlipayCode;
	}

	public void setOnlyAlipayCode(String onlyAlipayCode) {
		this.onlyAlipayCode = onlyAlipayCode;
	}

	public DwpasStKpiData getOnlyAlipayKpiData() {
		return onlyAlipayKpiData;
	}

	public void setOnlyAlipayKpiData(DwpasStKpiData onlyAlipayKpiData) {
		this.onlyAlipayKpiData = onlyAlipayKpiData;
	}

	public String getOnlyOutCode() {
		return onlyOutCode;
	}

	public void setOnlyOutCode(String onlyOutCode) {
		this.onlyOutCode = onlyOutCode;
	}

	public DwpasStKpiData getOnlyOutKpiData() {
		return onlyOutKpiData;
	}

	public void setOnlyOutKpiData(DwpasStKpiData onlyOutKpiData) {
		this.onlyOutKpiData = onlyOutKpiData;
	}

	public DwpasStKpiData getTaobaoAlipayData() {
		return taobaoAlipayData;
	}

	public void setTaobaoAlipayData(DwpasStKpiData taobaoAlipayData) {
		this.taobaoAlipayData = taobaoAlipayData;
	}

	public DwpasStKpiData getAlipayOutKpiData() {
		return alipayOutKpiData;
	}

	public void setAlipayOutKpiData(DwpasStKpiData alipayOutKpiData) {
		this.alipayOutKpiData = alipayOutKpiData;
	}

	public DwpasStKpiData getTaobaoOutData() {
		return taobaoOutData;
	}

	public void setTaobaoOutData(DwpasStKpiData taobaoOutData) {
		this.taobaoOutData = taobaoOutData;
	}

	public DwpasStKpiData getAllKpiData() {
		return allKpiData;
	}

	public void setAllKpiData(DwpasStKpiData allKpiData) {
		this.allKpiData = allKpiData;
	}
	
}
