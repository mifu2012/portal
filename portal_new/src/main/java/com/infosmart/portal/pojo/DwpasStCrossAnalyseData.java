package com.infosmart.portal.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品交叉分析数据
 * 
 * @author Administrator
 * 
 */
public class DwpasStCrossAnalyseData {

	private static final long serialVersionUID = 741231858441822688L;

	// ========== properties ==========

	/**
	 * This property corresponds to db column <tt>PRODUCT_ID</tt>.
	 */
	private String productId;

	/**
	 * This property corresponds to db column <tt>REPORT_DATE</tt>.
	 */
	private String reportDate;

	/**
	 * This property corresponds to db column <tt>REL_PRODUCT_ID</tt>.
	 */
	private String relProductId;

	/**
	 * F This property corresponds to db column <tt>CROSS_USER_CNT</tt>.
	 */
	private long crossUserCnt;

	/**
	 * This property corresponds to db column <tt>CROSS_USER_RATE</tt>.
	 */
	private BigDecimal crossUserRate;

	/**
	 * This property corresponds to db column <tt>GMT_CREATE</tt>.
	 */
	private Date gmtCreate;

	/**
	 * This property corresponds to db column <tt>GMT_MODIFIED</tt>.
	 */
	private Date gmtModified;

	// ========== getters and setters ==========

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
	 * Getter method for property <tt>reportDate</tt>.
	 * 
	 * @return property value of reportDate
	 */
	public String getReportDate() {
		return reportDate;
	}

	/**
	 * Setter method for property <tt>reportDate</tt>.
	 * 
	 * @param reportDate
	 *            value to be assigned to property reportDate
	 */
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	/**
	 * Getter method for property <tt>relProductId</tt>.
	 * 
	 * @return property value of relProductId
	 */
	public String getRelProductId() {
		return relProductId;
	}

	/**
	 * Setter method for property <tt>relProductId</tt>.
	 * 
	 * @param relProductId
	 *            value to be assigned to property relProductId
	 */
	public void setRelProductId(String relProductId) {
		this.relProductId = relProductId;
	}

	/**
	 * Getter method for property <tt>crossUserCnt</tt>.
	 * 
	 * @return property value of crossUserCnt
	 */
	public long getCrossUserCnt() {
		return crossUserCnt;
	}

	/**
	 * Setter method for property <tt>crossUserCnt</tt>.
	 * 
	 * @param crossUserCnt
	 *            value to be assigned to property crossUserCnt
	 */
	public void setCrossUserCnt(long crossUserCnt) {
		this.crossUserCnt = crossUserCnt;
	}

	/**
	 * Getter method for property <tt>crossUserRate</tt>.
	 * 
	 * @return property value of crossUserRate
	 */
	public BigDecimal getCrossUserRate() {
		return crossUserRate;
	}

	/**
	 * Setter method for property <tt>crossUserRate</tt>.
	 * 
	 * @param crossUserRate
	 *            value to be assigned to property crossUserRate
	 */
	public void setCrossUserRate(BigDecimal crossUserRate) {
		this.crossUserRate = crossUserRate;
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
