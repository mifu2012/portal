package com.infosmart.po.dwmis;

import java.util.Date;
import java.util.List;

import com.infosmart.po.Page;

public class DwmisLegendCategory {

	private String categoryId;
	// 父级id
	private String categoryPid;
	// 文件夹名
	private String categoryName;
	private String remark;
	private Date gmtDate;
	private Page page;

	private List<DwmisLegendInfo> legendInfoList;

	public List<DwmisLegendInfo> getLegendInfoList() {
		return legendInfoList;
	}

	public void setLegendInfoList(List<DwmisLegendInfo> legendInfoList) {
		this.legendInfoList = legendInfoList;
	}

	public Page getPage() {
		if (page == null)
			page = new Page();
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryPid() {
		return categoryPid;
	}

	public void setCategoryPid(String categoryPid) {
		this.categoryPid = categoryPid;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
}
