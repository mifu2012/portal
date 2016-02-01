package com.infosmart.po;

import java.util.Date;

public class DwpasRPrdTemplate {
private Integer templateId;
private String productId;
private Date gmtCreate;
private Date gmtModified;

public Integer getTemplateId() {
	return templateId;
}
public void setTemplateId(Integer templateId) {
	this.templateId = templateId;
}
public String getProductId() {
	return productId;
}
public void setProductId(String productId) {
	this.productId = productId;
}
public Date getGmtCreate() {
	return gmtCreate;
}
public void setGmtCreate(Date gmtCreate) {
	this.gmtCreate = gmtCreate;
}
public Date getGmtModified() {
	return gmtModified;
}
public void setGmtModified(Date gmtModified) {
	this.gmtModified = gmtModified;
}
}
