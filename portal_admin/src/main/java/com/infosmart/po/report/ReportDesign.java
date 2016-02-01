package com.infosmart.po.report;

import java.util.Date;
import java.util.List;

import com.infosmart.po.Page;
import com.infosmart.util.StringDes;
import com.infosmart.util.StringUtils;

/**
 * 报表设计Bean
 * 
 * @author infosmart
 * 
 */
public class ReportDesign {
	private Integer reportId;
	// 报表名称
	private String reportName;
	// 查询数据SQL
	private String reportSql;
	// 报表定义--JSON数据
	private String reportDefine;
	// 数据源ID
	private String dataSourceId;
	
	// 数据源
	private ReportDataSource reportDataSource;
	// 备注
	private String remark;

	// 500w定制的字段
	// 是否为500w定制，1为定制
	private Integer rptFlag500w;
	// 500w定制报表URL
	private String rptUrl500w;

	public ReportDataSource getReportDataSource() {
		return reportDataSource;
	}

	public void setReportDataSource(ReportDataSource reportDataSource) {
		this.reportDataSource = reportDataSource;
	}

	public Integer getRptFlag500w() {
		return rptFlag500w;
	}

	public void setRptFlag500w(Integer rptFlag500w) {
		this.rptFlag500w = rptFlag500w;
	}

	public String getRptUrl500w() {
		return rptUrl500w;
	}

	public void setRptUrl500w(String rptUrl500w) {
		this.rptUrl500w = rptUrl500w;
	}

	private int isOk = 0;

	// ***************整合*****************//

	// 加密后的报表ID
	private String reportDesId;
	private Integer parentId;// 父节点,
	private String isReport;// 是否为报表

	// 排序字段
	private String orderFieldName;
	private String sortOrder = "asc";
	private String queryWhere;
	// private List<SelfReportColumnConfig> columnConfigList;

	private List<ReportDesign> subReportList;

	// 临时使用
	private Integer pageSize;

	// private String querySql;

	// public String getQuerySql() {
	// return querySql;
	// }
	//
	// public void setQuerySql(String querySql) {
	// this.querySql = querySql;
	// }

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public int getIsOk() {
		isOk = StringUtils.notNullAndSpace(reportDefine) ? 1 : 0;
		return isOk;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportSql() {
		return reportSql;
	}

	public void setReportSql(String reportSql) {
		this.reportSql = reportSql;
	}

	public String getReportDefine() {
		return reportDefine;
	}

	public void setReportDefine(String reportDefine) {
		this.reportDefine = reportDefine;
	}

	public String getDataSourceId() {
		return dataSourceId;
	}

	public void setDataSourceId(String dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReportDesId() {
		if (reportId != null) {
			this.reportDesId = StringDes.StringToEnc(reportId.toString());
		}
		return reportDesId;
	}

	public String getOrderFieldName() {
		return orderFieldName;
	}

	public void setOrderFieldName(String orderFieldName) {
		this.orderFieldName = orderFieldName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public List<ReportDesign> getSubReportList() {
		return subReportList;
	}

	public void setSubReportList(List<ReportDesign> subReportList) {
		this.subReportList = subReportList;
	}

	public String getQueryWhere() {
		return queryWhere;
	}

	public void setQueryWhere(String queryWhere) {
		this.queryWhere = queryWhere;
	}

	/*
	 * public List<SelfReportColumnConfig> getColumnConfigList() { return
	 * columnConfigList; }
	 * 
	 * public void setColumnConfigList( List<SelfReportColumnConfig>
	 * columnConfigList) { this.columnConfigList = columnConfigList; }
	 */

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	private Date gmtModified;

	public String getIsReport() {
		return isReport;
	}

	public void setIsReport(String isReport) {
		this.isReport = isReport;
	}

	private String pageNumber;// 分页页数
	private Page page;

	public Page getPage() {
		if (page == null)
			page = new Page();
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	private ReportDesign parentReport;
	private List<ReportDesign> subReport;
	private List<ReportDesign> reportIds;

	public List<ReportDesign> getReportIds() {
		return reportIds;
	}

	public void setReportIds(List<ReportDesign> reportIds) {
		this.reportIds = reportIds;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	private boolean hasReport = false;

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public ReportDesign getParentReport() {
		return parentReport;
	}

	public void setParentReport(ReportDesign parentReport) {
		this.parentReport = parentReport;
	}

	public List<ReportDesign> getSubReport() {
		return subReport;
	}

	public void setSubReport(List<ReportDesign> subReport) {
		this.subReport = subReport;
	}

	public boolean isHasReport() {
		return hasReport;
	}

	public void setHasReport(boolean hasReport) {
		this.hasReport = hasReport;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

}
