package com.infosmart.po.dwmis;

import java.util.Date;

import com.infosmart.po.Page;

public class DwmisTemplateInfo {

	private String templateId;
	private String templateName;
	private String remark;
	private Date gmtDate;
	private String gmtUser;
	private Page page;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
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

	public Date getGmtDate() {
		return gmtDate;
	}

	public void setGmtDate(Date gmtDate) {
		this.gmtDate = gmtDate;
	}

	public String getGmtUser() {
		return gmtUser;
	}

	public void setGmtUser(String gmtUser) {
		this.gmtUser = gmtUser;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
