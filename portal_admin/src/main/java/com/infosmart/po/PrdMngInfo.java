package com.infosmart.po;

/*
 * Alipay.com Inc.
 * Copyright (c) 2004 All Rights Reserved.
 */
// auto generated imports
import java.util.Date;

/**
 * A data object class directly models database table <tt>DWPAS_C_PRD_INFO</tt>.
 * 
 * This file is generated by <tt>paygw-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may be
 * OVERWRITTEN by someone else. To modify the file, you should go to directory
 * <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and find the corresponding
 * configuration file (<tt>tables/dwpas_c_prd_info.xml</tt>). Modify the
 * configuration file according to your needs, then run <tt>paygw-dalgen</tt> to
 * generate this file.
 * 
 * @author Cheng Li
 */
public class PrdMngInfo {
	private static final long serialVersionUID = 741231858441822688L;

	// ========== properties ==========

	/**
	 * This property corresponds to db column <tt>PRODUCT_ID</tt>.
	 */
	private String productId;

	/**
	 * This property corresponds to db column <tt>PARENT_ID</tt>.
	 */
	private String parentId;

	/**
	 * This property corresponds to db column <tt>IS_FOLDER</tt>.
	 */
	private String isFolder;

	/**
	 * This property corresponds to db column <tt>PRODUCT_NAME</tt>.
	 */
	private String productName;

	/**
	 * This property corresponds to db column <tt>IS_INDEX_SHOW</tt>.
	 */
	private String isIndexShow;

	/**
	 * This property corresponds to db column <tt>INDEX_SHOW_ORDER</tt>.
	 */
	private int indexShowOrder;

	/**
	 * This property corresponds to db column <tt>IS_USE</tt>.
	 */
	private String isUse;

	/**
	 * This property corresponds to db column <tt>ICON_URL</tt>.
	 */
	private String iconUrl;

	/**
	 * This property corresponds to db column <tt>FUNNEL_ID</tt>.
	 */
	private String funnelId;

	/**
	 * This property corresponds to db column <tt>FUNNEL_NAME</tt>.
	 */
	private String funnelName;

	/**
	 * This property corresponds to db column <tt>GMT_CREATE</tt>.
	 */
	private Date gmtCreate;

	/**
	 * This property corresponds to db column <tt>GMT_MODIFIED</tt>.
	 */
	private Date gmtModified;

	/**
	 * This property corresponds to db column <tt>PRODUCT_MARK</tt>.
	 */
	private String productMark;

	private String productMarkDesc;

	private String keyWord1;

	private String keyWord2;
	private Page page;

	public Page getPage() {
		if (page == null)
			page = new Page();
		return page;
	}

	public String getProductMarkDesc() {
		return productMarkDesc;
	}

	public void setProductMarkDesc(String productMarkDesc) {
		this.productMarkDesc = productMarkDesc;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getKeyWord1() {
		return keyWord1;
	}

	public void setKeyWord1(String keyWord1) {
		this.keyWord1 = keyWord1;
	}

	public String getKeyWord2() {
		return keyWord2;
	}

	public void setKeyWord2(String keyWord2) {
		this.keyWord2 = keyWord2;
	}

	// ========== getters and setters ==========
	private String helpRate;

	public String getHelpRate() {
		return helpRate;
	}

	public void setHelpRate(String helpRate) {
		this.helpRate = helpRate;
	}

	private String kpiCodes;

	public String getKpiCodes() {
		return kpiCodes;
	}

	public void setKpiCodes(String kpiCodes) {
		this.kpiCodes = kpiCodes;
	}

	private String operFlag;

	public String getOperFlag() {
		return operFlag;
	}

	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}

	/**
	 * Getter method for property <tt>productId</tt>.
	 * 
	 * @return property value of productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * Setter method for property <tt>productId</tt>.
	 * 
	 * @param productId
	 *            value to be assigned to property productId
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * Getter method for property <tt>parentId</tt>.
	 * 
	 * @return property value of parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * Setter method for property <tt>parentId</tt>.
	 * 
	 * @param parentId
	 *            value to be assigned to property parentId
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * Getter method for property <tt>isFolder</tt>.
	 * 
	 * @return property value of isFolder
	 */
	public String getIsFolder() {
		return isFolder;
	}

	/**
	 * Setter method for property <tt>isFolder</tt>.
	 * 
	 * @param isFolder
	 *            value to be assigned to property isFolder
	 */
	public void setIsFolder(String isFolder) {
		this.isFolder = isFolder;
	}

	/**
	 * Getter method for property <tt>productName</tt>.
	 * 
	 * @return property value of productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * Setter method for property <tt>productName</tt>.
	 * 
	 * @param productName
	 *            value to be assigned to property productName
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * Getter method for property <tt>isIndexShow</tt>.
	 * 
	 * @return property value of isIndexShow
	 */
	public String getIsIndexShow() {
		return isIndexShow;
	}

	/**
	 * Setter method for property <tt>isIndexShow</tt>.
	 * 
	 * @param isIndexShow
	 *            value to be assigned to property isIndexShow
	 */
	public void setIsIndexShow(String isIndexShow) {
		this.isIndexShow = isIndexShow;
	}

	/**
	 * Getter method for property <tt>indexShowOrder</tt>.
	 * 
	 * @return property value of indexShowOrder
	 */
	public int getIndexShowOrder() {
		return indexShowOrder;
	}

	/**
	 * Setter method for property <tt>indexShowOrder</tt>.
	 * 
	 * @param indexShowOrder
	 *            value to be assigned to property indexShowOrder
	 */
	public void setIndexShowOrder(int indexShowOrder) {
		this.indexShowOrder = indexShowOrder;
	}

	/**
	 * Getter method for property <tt>isUse</tt>.
	 * 
	 * @return property value of isUse
	 */
	public String getIsUse() {
		return isUse;
	}

	/**
	 * Setter method for property <tt>isUse</tt>.
	 * 
	 * @param isUse
	 *            value to be assigned to property isUse
	 */
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	/**
	 * Getter method for property <tt>iconUrl</tt>.
	 * 
	 * @return property value of iconUrl
	 */
	public String getIconUrl() {
		return iconUrl;
	}

	/**
	 * Setter method for property <tt>iconUrl</tt>.
	 * 
	 * @param iconUrl
	 *            value to be assigned to property iconUrl
	 */
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	/**
	 * Getter method for property <tt>funnelId</tt>.
	 * 
	 * @return property value of funnelId
	 */
	public String getFunnelId() {
		return funnelId;
	}

	/**
	 * Setter method for property <tt>funnelId</tt>.
	 * 
	 * @param funnelId
	 *            value to be assigned to property funnelId
	 */
	public void setFunnelId(String funnelId) {
		this.funnelId = funnelId;
	}

	/**
	 * Getter method for property <tt>funnelName</tt>.
	 * 
	 * @return property value of funnelName
	 */
	public String getFunnelName() {
		return funnelName;
	}

	/**
	 * Setter method for property <tt>funnelName</tt>.
	 * 
	 * @param funnelName
	 *            value to be assigned to property funnelName
	 */
	public void setFunnelName(String funnelName) {
		this.funnelName = funnelName;
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

	/**
	 * Getter method for property <tt>productMark</tt>.
	 * 
	 * @return property value of productMark
	 */
	public String getProductMark() {
		return productMark;
	}

	/**
	 * Setter method for property <tt>productMark</tt>.
	 * 
	 * @param productMark
	 *            value to be assigned to property productMark
	 */
	public void setProductMark(String productMark) {
		this.productMark = productMark;
	}
}