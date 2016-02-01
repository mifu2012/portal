package com.infosmart.portal.pojo;

import java.util.Date;

/**
 * 平台交叉用户指标关联
 *  A data object class directly models database table
 * <tt>DWPAS_C_CROSS_USER_KPI</tt>.
 * 
 * @author gentai.huang
 * 
 */
public class DwpasCCrossUserKpi {
	private static final long serialVersionUID = 741231858441822688L;

	// ========== properties ==========

	/**
	 * This property corresponds to db column <tt>ID</tt>.
	 */
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

	// ========== getters and setters ==========

	/**
	 * Getter method for property <tt>id</tt>.
	 * 
	 * @return property value of id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter method for property <tt>id</tt>.
	 * 
	 * @param id
	 *            value to be assigned to property id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Getter method for property <tt>kpiName</tt>.
	 * 
	 * @return property value of kpiName
	 */
	public String getKpiName() {
		return kpiName;
	}

	/**
	 * Setter method for property <tt>kpiName</tt>.
	 * 
	 * @param kpiName
	 *            value to be assigned to property kpiName
	 */
	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	/**
	 * Getter method for property <tt>taobaoCode</tt>.
	 * 
	 * @return property value of taobaoCode
	 */
	public String getTaobaoCode() {
		return taobaoCode;
	}

	/**
	 * Setter method for property <tt>taobaoCode</tt>.
	 * 
	 * @param taobaoCode
	 *            value to be assigned to property taobaoCode
	 */
	public void setTaobaoCode(String taobaoCode) {
		this.taobaoCode = taobaoCode;
	}

	/**
	 * Getter method for property <tt>alipayCode</tt>.
	 * 
	 * @return property value of alipayCode
	 */
	public String getAlipayCode() {
		return alipayCode;
	}

	/**
	 * Setter method for property <tt>alipayCode</tt>.
	 * 
	 * @param alipayCode
	 *            value to be assigned to property alipayCode
	 */
	public void setAlipayCode(String alipayCode) {
		this.alipayCode = alipayCode;
	}

	/**
	 * Getter method for property <tt>outCode</tt>.
	 * 
	 * @return property value of outCode
	 */
	public String getOutCode() {
		return outCode;
	}

	/**
	 * Setter method for property <tt>outCode</tt>.
	 * 
	 * @param outCode
	 *            value to be assigned to property outCode
	 */
	public void setOutCode(String outCode) {
		this.outCode = outCode;
	}

	/**
	 * Getter method for property <tt>taobaoOnlyCode</tt>.
	 * 
	 * @return property value of taobaoOnlyCode
	 */
	public String getTaobaoOnlyCode() {
		return taobaoOnlyCode;
	}

	/**
	 * Setter method for property <tt>taobaoOnlyCode</tt>.
	 * 
	 * @param taobaoOnlyCode
	 *            value to be assigned to property taobaoOnlyCode
	 */
	public void setTaobaoOnlyCode(String taobaoOnlyCode) {
		this.taobaoOnlyCode = taobaoOnlyCode;
	}

	/**
	 * Getter method for property <tt>alipayOnlyCode</tt>.
	 * 
	 * @return property value of alipayOnlyCode
	 */
	public String getAlipayOnlyCode() {
		return alipayOnlyCode;
	}

	/**
	 * Setter method for property <tt>alipayOnlyCode</tt>.
	 * 
	 * @param alipayOnlyCode
	 *            value to be assigned to property alipayOnlyCode
	 */
	public void setAlipayOnlyCode(String alipayOnlyCode) {
		this.alipayOnlyCode = alipayOnlyCode;
	}

	/**
	 * Getter method for property <tt>outOnlyCode</tt>.
	 * 
	 * @return property value of outOnlyCode
	 */
	public String getOutOnlyCode() {
		return outOnlyCode;
	}

	/**
	 * Setter method for property <tt>outOnlyCode</tt>.
	 * 
	 * @param outOnlyCode
	 *            value to be assigned to property outOnlyCode
	 */
	public void setOutOnlyCode(String outOnlyCode) {
		this.outOnlyCode = outOnlyCode;
	}

	/**
	 * Getter method for property <tt>taobaoAlipayCode</tt>.
	 * 
	 * @return property value of taobaoAlipayCode
	 */
	public String getTaobaoAlipayCode() {
		return taobaoAlipayCode;
	}

	/**
	 * Setter method for property <tt>taobaoAlipayCode</tt>.
	 * 
	 * @param taobaoAlipayCode
	 *            value to be assigned to property taobaoAlipayCode
	 */
	public void setTaobaoAlipayCode(String taobaoAlipayCode) {
		this.taobaoAlipayCode = taobaoAlipayCode;
	}

	/**
	 * Getter method for property <tt>alipayOutCode</tt>.
	 * 
	 * @return property value of alipayOutCode
	 */
	public String getAlipayOutCode() {
		return alipayOutCode;
	}

	/**
	 * Setter method for property <tt>alipayOutCode</tt>.
	 * 
	 * @param alipayOutCode
	 *            value to be assigned to property alipayOutCode
	 */
	public void setAlipayOutCode(String alipayOutCode) {
		this.alipayOutCode = alipayOutCode;
	}

	/**
	 * Getter method for property <tt>taobaoOutCode</tt>.
	 * 
	 * @return property value of taobaoOutCode
	 */
	public String getTaobaoOutCode() {
		return taobaoOutCode;
	}

	/**
	 * Setter method for property <tt>taobaoOutCode</tt>.
	 * 
	 * @param taobaoOutCode
	 *            value to be assigned to property taobaoOutCode
	 */
	public void setTaobaoOutCode(String taobaoOutCode) {
		this.taobaoOutCode = taobaoOutCode;
	}

	/**
	 * Getter method for property <tt>allCode</tt>.
	 * 
	 * @return property value of allCode
	 */
	public String getAllCode() {
		return allCode;
	}

	/**
	 * Setter method for property <tt>allCode</tt>.
	 * 
	 * @param allCode
	 *            value to be assigned to property allCode
	 */
	public void setAllCode(String allCode) {
		this.allCode = allCode;
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
