package com.infosmart.portal.pojo;

/**
 * 交叉用户指标模型
 * 
 * @author gentai.huang
 * 
 */
public class CrossUserKpiDTO {

	/** 指标名称 */
	private String kpiName;

	/** 淘宝总数 */
	private String taobaoCode;

	/** 淘宝总数指标 */
	private KpiDataDTO tabaoKpiData;

	/** 站内总数 */
	private String alipayCode;

	/** 站内指标 */
	private KpiDataDTO alipayKpiData;

	/** 站外指标Code */
	private String outCode;

	/** 站外指标 */
	private KpiDataDTO outKpiData;

	/** 仅使用淘宝CODE */
	private String onlyTaobaoCode;

	/** 仅使用淘宝指标 */
	private KpiDataDTO onlyTaobaoKpiData;

	/** 仅使用站内 */
	private String onlyAlipayCode;

	/** 仅使用站内指标 */
	private KpiDataDTO onlyAlipayKpiData;

	/** 仅使用 站外CODE */
	private String onlyOutCode;

	/** 仅使用站外指标 */
	private KpiDataDTO onlyOutKpiData;

	/** 淘宝 + 站内 CODE */
	private String taobaoAlipayCode;

	/** 淘宝 + 站内 指标 */
	private KpiDataDTO taobaoAlipayData;

	/** 站内 + 站外 CODE */
	private String alipayOutCode;

	/** 站内 + 站外指标 */
	private KpiDataDTO alipayOutKpiData;

	/** 淘宝 + 站外 CODE */
	private String taobaoOutCode;

	/** 淘宝 + 站外 指标 */
	private KpiDataDTO taobaoOutData;

	/** 站内 + 淘宝 + 站外 CODE */
	private String allCode;

	/** 站内 + 淘宝 + 站外 指标 */
	private KpiDataDTO allKpiData;

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
	 * Getter method for property <tt>tabaoKpiData</tt>.
	 * 
	 * @return property value of tabaoKpiData
	 */
	public KpiDataDTO getTabaoKpiData() {
		return tabaoKpiData;
	}

	/**
	 * Setter method for property <tt>tabaoKpiData</tt>.
	 * 
	 * @param tabaoKpiData
	 *            value to be assigned to property tabaoKpiData
	 */
	public void setTabaoKpiData(KpiDataDTO tabaoKpiData) {
		this.tabaoKpiData = tabaoKpiData;
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
	 * Getter method for property <tt>alipayKpiData</tt>.
	 * 
	 * @return property value of alipayKpiData
	 */
	public KpiDataDTO getAlipayKpiData() {
		return alipayKpiData;
	}

	/**
	 * Setter method for property <tt>alipayKpiData</tt>.
	 * 
	 * @param alipayKpiData
	 *            value to be assigned to property alipayKpiData
	 */
	public void setAlipayKpiData(KpiDataDTO alipayKpiData) {
		this.alipayKpiData = alipayKpiData;
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
	 * Getter method for property <tt>outKpiData</tt>.
	 * 
	 * @return property value of outKpiData
	 */
	public KpiDataDTO getOutKpiData() {
		return outKpiData;
	}

	/**
	 * Setter method for property <tt>outKpiData</tt>.
	 * 
	 * @param outKpiData
	 *            value to be assigned to property outKpiData
	 */
	public void setOutKpiData(KpiDataDTO outKpiData) {
		this.outKpiData = outKpiData;
	}

	/**
	 * Getter method for property <tt>onlyTaobaoCode</tt>.
	 * 
	 * @return property value of onlyTaobaoCode
	 */
	public String getOnlyTaobaoCode() {
		return onlyTaobaoCode;
	}

	/**
	 * Setter method for property <tt>onlyTaobaoCode</tt>.
	 * 
	 * @param onlyTaobaoCode
	 *            value to be assigned to property onlyTaobaoCode
	 */
	public void setOnlyTaobaoCode(String onlyTaobaoCode) {
		this.onlyTaobaoCode = onlyTaobaoCode;
	}

	/**
	 * Getter method for property <tt>onlyTaobaoKpiData</tt>.
	 * 
	 * @return property value of onlyTaobaoKpiData
	 */
	public KpiDataDTO getOnlyTaobaoKpiData() {
		return onlyTaobaoKpiData;
	}

	/**
	 * Setter method for property <tt>onlyTaobaoKpiData</tt>.
	 * 
	 * @param onlyTaobaoKpiData
	 *            value to be assigned to property onlyTaobaoKpiData
	 */
	public void setOnlyTaobaoKpiData(KpiDataDTO onlyTaobaoKpiData) {
		this.onlyTaobaoKpiData = onlyTaobaoKpiData;
	}

	/**
	 * Getter method for property <tt>onlyAlipayCode</tt>.
	 * 
	 * @return property value of onlyAlipayCode
	 */
	public String getOnlyAlipayCode() {
		return onlyAlipayCode;
	}

	/**
	 * Setter method for property <tt>onlyAlipayCode</tt>.
	 * 
	 * @param onlyAlipayCode
	 *            value to be assigned to property onlyAlipayCode
	 */
	public void setOnlyAlipayCode(String onlyAlipayCode) {
		this.onlyAlipayCode = onlyAlipayCode;
	}

	/**
	 * Getter method for property <tt>onlyAlipayKpiData</tt>.
	 * 
	 * @return property value of onlyAlipayKpiData
	 */
	public KpiDataDTO getOnlyAlipayKpiData() {
		return onlyAlipayKpiData;
	}

	/**
	 * Setter method for property <tt>onlyAlipayKpiData</tt>.
	 * 
	 * @param onlyAlipayKpiData
	 *            value to be assigned to property onlyAlipayKpiData
	 */
	public void setOnlyAlipayKpiData(KpiDataDTO onlyAlipayKpiData) {
		this.onlyAlipayKpiData = onlyAlipayKpiData;
	}

	/**
	 * Getter method for property <tt>onlyOutCode</tt>.
	 * 
	 * @return property value of onlyOutCode
	 */
	public String getOnlyOutCode() {
		return onlyOutCode;
	}

	/**
	 * Setter method for property <tt>onlyOutCode</tt>.
	 * 
	 * @param onlyOutCode
	 *            value to be assigned to property onlyOutCode
	 */
	public void setOnlyOutCode(String onlyOutCode) {
		this.onlyOutCode = onlyOutCode;
	}

	/**
	 * Getter method for property <tt>onlyOutKpiData</tt>.
	 * 
	 * @return property value of onlyOutKpiData
	 */
	public KpiDataDTO getOnlyOutKpiData() {
		return onlyOutKpiData;
	}

	/**
	 * Setter method for property <tt>onlyOutKpiData</tt>.
	 * 
	 * @param onlyOutKpiData
	 *            value to be assigned to property onlyOutKpiData
	 */
	public void setOnlyOutKpiData(KpiDataDTO onlyOutKpiData) {
		this.onlyOutKpiData = onlyOutKpiData;
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
	 * Getter method for property <tt>taobaoAlipayData</tt>.
	 * 
	 * @return property value of taobaoAlipayData
	 */
	public KpiDataDTO getTaobaoAlipayData() {
		return taobaoAlipayData;
	}

	/**
	 * Setter method for property <tt>taobaoAlipayData</tt>.
	 * 
	 * @param taobaoAlipayData
	 *            value to be assigned to property taobaoAlipayData
	 */
	public void setTaobaoAlipayData(KpiDataDTO taobaoAlipayData) {
		this.taobaoAlipayData = taobaoAlipayData;
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
	 * Getter method for property <tt>alipayOutKpiData</tt>.
	 * 
	 * @return property value of alipayOutKpiData
	 */
	public KpiDataDTO getAlipayOutKpiData() {
		return alipayOutKpiData;
	}

	/**
	 * Setter method for property <tt>alipayOutKpiData</tt>.
	 * 
	 * @param alipayOutKpiData
	 *            value to be assigned to property alipayOutKpiData
	 */
	public void setAlipayOutKpiData(KpiDataDTO alipayOutKpiData) {
		this.alipayOutKpiData = alipayOutKpiData;
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
	 * Getter method for property <tt>taobaoOutData</tt>.
	 * 
	 * @return property value of taobaoOutData
	 */
	public KpiDataDTO getTaobaoOutData() {
		return taobaoOutData;
	}

	/**
	 * Setter method for property <tt>taobaoOutData</tt>.
	 * 
	 * @param taobaoOutData
	 *            value to be assigned to property taobaoOutData
	 */
	public void setTaobaoOutData(KpiDataDTO taobaoOutData) {
		this.taobaoOutData = taobaoOutData;
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
	 * Getter method for property <tt>allKpiData</tt>.
	 * 
	 * @return property value of allKpiData
	 */
	public KpiDataDTO getAllKpiData() {
		return allKpiData;
	}

	/**
	 * Setter method for property <tt>allKpiData</tt>.
	 * 
	 * @param allKpiData
	 *            value to be assigned to property allKpiData
	 */
	public void setAllKpiData(KpiDataDTO allKpiData) {
		this.allKpiData = allKpiData;
	}

}
