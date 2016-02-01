package com.infosmart.portal.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 产品配置DTO
 * @author gentai.huang
 * 
 */
public class ProdInfoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 产品ID */
	private String productId;

	/** 产品名称 */
	private String productName;

	/** 是否在首页显示 */
	private String isIndexShow;

	/** 首页显示顺序 */
	private int indexShowOrder;

	/** 父Id */
	private String parentId;

	/** 是否目录 */
	private String isFolder;

	/** 是否可用 */
	private String isUse;

	/** 显示图标URL */
	private String iconUrl;

	/** 漏斗ID */
	private String funnelId;

	/** 漏斗名称 */
	private String funnelName;

	/** 创建时间 */
	private Date gmtCreate;

	/** 修改时间 */
	private Date gmtModified;

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
}
