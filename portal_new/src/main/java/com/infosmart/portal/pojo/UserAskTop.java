package com.infosmart.portal.pojo;

import java.util.Date;


/**
 * 产品求助数据
 * @author 
 * @version 
 */
public class UserAskTop {

    /** 产品编号 */
    private String productId;

    /** 数据统计时间 */
    private String reportDate;

    /** 类型，即产品CODE */
    private String userType;

    /**排序  */
    private int    sortId;

    /** 问题 */
    private String question;

    /**创建时间  */
    private Date   gmtCreate;

    /**修改时间  */
    private Date   gmtModified;

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
     * @param productId value to be assigned to property productId
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
     * @param reportDate value to be assigned to property reportDate
     */
    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    /**
     * Getter method for property <tt>userType</tt>.
     * 
     * @return property value of userType
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Setter method for property <tt>userType</tt>.
     * 
     * @param userType value to be assigned to property userType
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * Getter method for property <tt>sortId</tt>.
     * 
     * @return property value of sortId
     */
    public int getSortId() {
        return sortId;
    }

    /**
     * Setter method for property <tt>sortId</tt>.
     * 
     * @param sortId value to be assigned to property sortId
     */
    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    /**
     * Getter method for property <tt>question</tt>.
     * 
     * @return property value of question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Setter method for property <tt>question</tt>.
     * 
     * @param question value to be assigned to property question
     */
    public void setQuestion(String question) {
        this.question = question;
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