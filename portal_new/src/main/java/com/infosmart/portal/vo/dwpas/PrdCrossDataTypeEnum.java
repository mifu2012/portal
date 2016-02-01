package com.infosmart.portal.vo.dwpas;

/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2011 All Rights Reserved.
 */


/**
 * 产品交叉数据类型枚举
 * 
 * @author changwei.ye
 * @version $Id: PrdCrossDataTypeEnum.java, v 0.1 2011-12-5 下午07:17:23 changwei.ye Exp $
 */
public enum PrdCrossDataTypeEnum {

    /**
     * 交叉用户数
     */
    CROSS_USER_CNT("1", "交叉用户数"),

    /**
     * 交叉用户数占比
     */
    CROSS_USER_RATE("2", "交叉用户数占比");

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
    private PrdCrossDataTypeEnum(String code, String message) {
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
