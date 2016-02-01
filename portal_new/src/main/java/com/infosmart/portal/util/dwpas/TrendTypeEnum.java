package com.infosmart.portal.util.dwpas;

/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2011 All Rights Reserved.
 */


/**
 * 
 * @author yufei.sun
 * @version $Id: TrendTypeEnum.java, v 0.1 2011-12-8 下午07:40:09 yufei.sun Exp $
 */
public enum TrendTypeEnum {
    
    NORMAL_CHART("1","一般趋势图"),
    
    
    CROSS_USER_CHART("2","场景交叉趋势图");
    
    /**
     * 枚举值
     */
    private String code;

    /**
     * 描述信息
     */
    private String message;
    
    private TrendTypeEnum(String code,String message){
        this.code = code;
        this.message = message;
    }
    
    public String getCode() {
        return code;
    }

    /**
     * @param code The code to set.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return Returns the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
