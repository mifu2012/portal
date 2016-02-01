package com.infosmart.po;

import java.util.Date;

public class SysParamInfo {
	private static final long serialVersionUID = 741231858441822688L;

    //========== properties ==========

	/**
	 * This property corresponds to db column <tt>PARAM_ID</tt>.
	 */
	private String paramId;

	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	/**
	 * This property corresponds to db column <tt>PARAM_NAME</tt>.
	 */
	private String paramName;

	/**
	 * This property corresponds to db column <tt>PARAM_VALUE</tt>.
	 */
	private String paramValue;

	/**
	 * This property corresponds to db column <tt>GMT_CREATE</tt>.
	 */
	private Date gmtCreate;

	/**
	 * This property corresponds to db column <tt>GMT_MODIFIED</tt>.
	 */
	private Date gmtModified;
	private Page page;
	public Page getPage() {
		if (page == null)
			page = new Page();
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

    //========== getters and setters ==========

    /**
     * Getter method for property <tt>paramId</tt>.
     *
     * @return property value of paramId
     */
	

    /**
     * Getter method for property <tt>paramName</tt>.
     *
     * @return property value of paramName
     */
	public String getParamName() {
		return paramName;
	}
	
	/**
	 * Setter method for property <tt>paramName</tt>.
	 * 
	 * @param paramName value to be assigned to property paramName
     */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

    /**
     * Getter method for property <tt>paramValue</tt>.
     *
     * @return property value of paramValue
     */
	public String getParamValue() {
		return paramValue;
	}
	
	/**
	 * Setter method for property <tt>paramValue</tt>.
	 * 
	 * @param paramValue value to be assigned to property paramValue
     */
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
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
	 * @param gmtCreate value to be assigned to property gmtCreate
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
	 * @param gmtModified value to be assigned to property gmtModified
     */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
}
