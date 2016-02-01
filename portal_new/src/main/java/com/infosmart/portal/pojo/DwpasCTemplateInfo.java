package com.infosmart.portal.pojo;

import java.util.Date;

public class DwpasCTemplateInfo {
    private Integer templateId;
    private String templateName;
    private String remark;
    private Date gmtCreate;
    //以下为临时字段
    private String[] columnCodes;
    private String[] columnNames;
    private String[] commCodes;
    private String[] remarks;
    private String[] userTypes;
	public String[] getUserTypes() {
		return userTypes;
	}
	public void setUserTypes(String[] userTypes) {
		this.userTypes = userTypes;
	}
	public String[] getColumnCodes() {
		return columnCodes;
	}
	public void setColumnCodes(String[] columnCodes) {
		this.columnCodes = columnCodes;
	}
	public String[] getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}
	public String[] getCommCodes() {
		return commCodes;
	}
	public void setCommCodes(String[] commCodes) {
		this.commCodes = commCodes;
	}
	public String[] getRemarks() {
		return remarks;
	}
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}
	public Integer getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	
}
