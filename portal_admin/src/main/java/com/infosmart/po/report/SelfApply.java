package com.infosmart.po.report;

import java.util.Date;

import com.infosmart.po.Page;
/**
 * 报表子服务bean
 * @author infosmart
 *
 */
public class SelfApply {
	private Integer id;
	private Integer userId;
	private String userName;
	private Integer reportId;
	private String reportName;
	private Integer state;
	private String memo;
	private Integer updateId;
	private Date updateTime;
	private boolean hasRights = true;

	public boolean isHasRights() {
		return hasRights;
	}

	public void setHasRights(boolean hasRights) {
		this.hasRights = hasRights;
	}

	private Page page;

	public Page getPage() {
		if (page == null)
			page = new Page();
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
