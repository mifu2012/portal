package com.infosmart.po;

import java.util.Date;
import java.util.List;

/**
 * 产品信息
 * 
 * @author infosmart
 * 
 */
public class DwpasCPrdInfo {
	// ========== properties ==========

	/**
	 * This property corresponds to db column <tt>PRODUCT_ID</tt>.
	 */
	private String productId;

	/**
	 * 父节点id
	 */
	private String parentId;

	/**
	 * 是否为目录.
	 */
	private String isFolder;

	/**
	 * 产品名称
	 */
	private String productName;

	/**
	 * 产品标记
	 */
	private String productMark;

	private String productMarkDesc;

	/**
	 * 是否在首页显示
	 */
	private String isIndexShow;

	/**
	 * 在首页显示顺序
	 */
	private int indexShowOrder;

	/**
	 * 是否可用
	 */
	private String isUse;

	/**
	 * 显示图标的url
	 */
	private String iconUrl;

	/**
	 * 漏斗ID
	 */
	private String funnelId;

	/**
	 * 漏斗名称
	 */
	private String funnelName;

	/**
	 * 子产品数
	 */
	private List<DwpasCPrdInfo> childPrdInfoList;

	/**
	 * This property corresponds to db column <tt>GMT_CREATE</tt>.
	 */
	private Date gmtCreate;

	/**
	 * This property corresponds to db column <tt>GMT_MODIFIED</tt>.
	 */
	private Date gmtModified;

	public String getProductMarkDesc() {
		return productMarkDesc;
	}

	public void setProductMarkDesc(String productMarkDesc) {
		this.productMarkDesc = productMarkDesc;
	}

	public List<DwpasCPrdInfo> getChildPrdInfoList() {
		return childPrdInfoList;
	}

	public void setChildPrdInfoList(List<DwpasCPrdInfo> childPrdInfoList) {
		this.childPrdInfoList = childPrdInfoList;
	}

	public String getProductName() {
		return productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getIsFolder() {
		return isFolder;
	}

	public void setIsFolder(String isFolder) {
		this.isFolder = isFolder;
	}

	public String queryChildCountByPrdId() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductMark() {
		return productMark;
	}

	public void setProductMark(String productMark) {
		this.productMark = productMark;
	}

	public String getIsIndexShow() {
		return isIndexShow;
	}

	public void setIsIndexShow(String isIndexShow) {
		this.isIndexShow = isIndexShow;
	}

	public int getIndexShowOrder() {
		return indexShowOrder;
	}

	public void setIndexShowOrder(int indexShowOrder) {
		this.indexShowOrder = indexShowOrder;
	}

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getFunnelId() {
		return funnelId;
	}

	public void setFunnelId(String funnelId) {
		this.funnelId = funnelId;
	}

	public String getFunnelName() {
		return funnelName;
	}

	public void setFunnelName(String funnelName) {
		this.funnelName = funnelName;
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