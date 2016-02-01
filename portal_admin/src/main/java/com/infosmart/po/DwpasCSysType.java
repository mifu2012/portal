package com.infosmart.po;

import java.util.Date;

public class DwpasCSysType {
	

    //========== properties ==========

	/**
	 * This property corresponds to db column <tt>TYPE_ID</tt>.
	 */
	private Integer typeId;
	
	/** 事记类别 */
	private int eventType;

	/**
	 * This property corresponds to db column <tt>GROUP_ID</tt>.
	 */
	private Integer groupId;

	/**
	 * This property corresponds to db column <tt>TYPE_NAME</tt>.
	 */
	private String typeName;

	/**
	 * This property corresponds to db column <tt>DETAIL</tt>.
	 */
	private String detail;

	/**
	 * This property corresponds to db column <tt>GMT_CREATE</tt>.
	 */
	private Date gmtCreate;

	/**
	 * This property corresponds to db column <tt>GMT_MODIFIED</tt>.
	 */
	private Date gmtModified;
	
	/** 指标Code */
    private String kpiCode;
    
    /** 指标名称 */
    private String kpiName;
    
    
    /** 备注 */
    private String detal;
    
    /** 是否选择 */
    private boolean marked;

    //========== getters and setters ==========

    /**
     * Getter method for property <tt>typeId</tt>.
     *
     * @return property value of typeId
     */
	public Integer getTypeId() {
		return typeId;
	}
	
	/**
	 * Setter method for property <tt>typeId</tt>.
	 * 
	 * @param typeId value to be assigned to property typeId
     */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

    /**
     * Getter method for property <tt>groupId</tt>.
     *
     * @return property value of groupId
     */
	public Integer getGroupId() {
		return groupId;
	}
	
	/**
	 * Setter method for property <tt>groupId</tt>.
	 * 
	 * @param groupId value to be assigned to property groupId
     */
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

    /**
     * Getter method for property <tt>typeName</tt>.
     *
     * @return property value of typeName
     */
	public String getTypeName() {
		return typeName;
	}
	
	/**
	 * Setter method for property <tt>typeName</tt>.
	 * 
	 * @param typeName value to be assigned to property typeName
     */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

    /**
     * Getter method for property <tt>detail</tt>.
     *
     * @return property value of detail
     */
	public String getDetail() {
		return detail;
	}
	
	/**
	 * Setter method for property <tt>detail</tt>.
	 * 
	 * @param detail value to be assigned to property detail
     */
	public void setDetail(String detail) {
		this.detail = detail;
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

	public String getKpiCode() {
		return kpiCode;
	}

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

	public String getKpiName() {
		return kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	public String getDetal() {
		return detal;
	}

	public void setDetal(String detal) {
		this.detal = detal;
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
	
}
