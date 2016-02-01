package com.infosmart.po.report;

import java.util.Date;

/**
 * 报表图表设置
 * @author infosmart
 *
 */
public class ReportChart {
	private int chartId;//主键Id
	private String fields;//图表对应显示数据，以“，”分开
	private String url;//图表路径
	private int reportId;//对应报表id
	private int updateId;//修改人ID
	private Date gmtCreate;//创建时间
	private Date gmtModified;//修改时间
	public int getChartId() {
		return chartId;
	}
	public void setChartId(int chartId) {
		this.chartId = chartId;
	}
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getReportId() {
		return reportId;
	}
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	public int getUpdateId() {
		return updateId;
	}
	public void setUpdateId(int updateId) {
		this.updateId = updateId;
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
