package com.infosmart.portal.pojo.report;

import java.util.Date;
import java.util.List;

import com.infosmart.portal.util.StringDes;
import com.infosmart.portal.util.StringUtils;

/**
 * 报表数据
 * 
 * @author hgt
 * 
 */
public class SelfReport {
	private Integer reportId;
	private String reportName;
	private String querySql;
	private int pageSize;
	private String gmtCreator;
	private Date gmtCreate;
	private Date gmtModified;
	private Integer parentId;// 父节点ID
	private String parentName;// 父节点名称
	private String remark;// 备注
	private String isReport;// 是否为报表
	private String queryWhere;// 查询条件
	private List<SelfReportColumn> columnList;
	private SelfApply selfApply;
	private String reportDesId;// 加密后的报表ID

	// 500w定制两个字段
	private Integer rptFlag500w;// 是否为500W定制
	private String rptUrl500w; // 报表url

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
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

	public String getReportDesId() {
		if (StringUtils.notNullAndSpace(String.valueOf(reportId))) {
			this.reportDesId = StringDes.StringToEnc(String.valueOf(reportId));
		}
		return reportDesId;
	}

	public SelfApply getSelfApply() {
		return selfApply;
	}

	public void setSelfApply(SelfApply selfApply) {
		this.selfApply = selfApply;
	}

	private boolean hasReport = false;
	private List<SelfReport> subReport;

	public String getQueryWhere() {
		return queryWhere;
	}

	public void setQueryWhere(String queryWhere) {
		this.queryWhere = queryWhere;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getIsReport() {
		return isReport;
	}

	public void setIsReport(String isReport) {
		this.isReport = isReport;
	}

	public List<SelfReport> getSubReport() {
		return subReport;
	}

	public void setSubReport(List<SelfReport> subReport) {
		this.subReport = subReport;
	}

	public boolean isHasReport() {
		return hasReport;
	}

	public void setHasReport(boolean hasReport) {
		this.hasReport = hasReport;
	}

	public List<SelfReportColumn> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<SelfReportColumn> columnList) {
		this.columnList = columnList;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public String getQuerySql() {
		return querySql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGmtCreator() {
		return gmtCreator;
	}

	public void setGmtCreator(String gmtCreator) {
		this.gmtCreator = gmtCreator;
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
