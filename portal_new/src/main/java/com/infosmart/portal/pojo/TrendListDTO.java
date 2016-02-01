package com.infosmart.portal.pojo;



import java.util.List;

/**
 * 新用户统计DTO
 * @author yufei.sun
 * @version $Id: NewUserCountDTO.java, v 0.1 2011-12-8 上午11:06:03 yufei.sun Exp $
 */
public class TrendListDTO {
    
    /** 名字  */
    String name;
    
    /** code */
    String value;
    
    /** 下拉框B中的属性 */
    List<DropDownList> properties;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<DropDownList> getProperties() {
        return properties;
    }

    public void setProperties(List<DropDownList> properties) {
        this.properties = properties;
    }

    
    

}
