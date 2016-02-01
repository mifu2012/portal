package com.infosmart.portal.util.dwpas;

/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2011 All Rights Reserved.
 */


/**
 * 指标类型枚举
 * @author hongbi.wang
 * @version $Id: KpiTypeEnum.java, v 0.1 2011-10-17 下午01:37:56 hongbi.wang Exp $
 */
public enum KpiTypeEnum {

    /**
     * 月指标
     */
    KPI_TYPE_MONTH("3", "月指标"),

    /**
     * 日指标
     */
    KPI_TYPE_DAY("1", "日指标");

    /**
     * 类型编码
     */
    private String code;

    /**
     *类型名称 
     */
    private String message;

    /**
     * 枚举构造方法
     * @param code     类型编码
     * @param message  类型名称
     */
    private KpiTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Getter method for property <tt>code</tt>.
     * 
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     * 
     * @param code value to be assigned to property code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter method for property <tt>message</tt>.
     * 
     * @return property value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter method for property <tt>message</tt>.
     * 
     * @param message value to be assigned to property message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
