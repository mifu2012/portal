package com.infosmart.portal.pojo.report;


import java.util.Date;

public class ReportChartFiled {
	private int chartId;
	private int chartType;
	private int reportId;
	private String fields;
	private String xFields;
	private String url;
	private String updateId;
	private Date GMT_CREATE;
	private Date GMT_MODIFIED;
	private int left;
	private int top;
	private int width;
	private int height;

	private int summaryType = -1;// 未定义

	// 合计
	public final static int SUMMARY_TYPE_SUM = 1;

	// 个数
	public final static int SUMMARY_TYPE_TOTAL = 2;

	public int getSummaryType() {
		if (chartType != 1) {
			// 不为饼图
			summaryType = -1;
		}
		return summaryType;
	}

	public void setSummaryType(int summaryType) {
		this.summaryType = summaryType;
	}

	public int getLeft() {
		this.left = this.left <= 0 ? 30 : this.left;
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getTop() {
		this.top = this.top <= 0 ? 30 : this.top;
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getChartId() {
		return chartId;
	}

	public void setChartId(int chartId) {
		this.chartId = chartId;
	}

	public int getChartType() {
		return chartType;
	}

	public void setChartType(int chartType) {
		this.chartType = chartType;
	}

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
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

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public Date getGMT_CREATE() {
		return GMT_CREATE;
	}

	public void setGMT_CREATE(Date gMT_CREATE) {
		GMT_CREATE = gMT_CREATE;
	}

	public Date getGMT_MODIFIED() {
		return GMT_MODIFIED;
	}

	public void setGMT_MODIFIED(Date gMT_MODIFIED) {
		GMT_MODIFIED = gMT_MODIFIED;
	}

	public String getxFields() {
		return xFields;
	}

	public void setxFields(String xFields) {
		this.xFields = xFields;
	}

}
