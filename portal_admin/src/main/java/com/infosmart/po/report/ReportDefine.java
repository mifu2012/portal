package com.infosmart.po.report;

import java.util.Arrays;
import java.util.List;

import com.infosmart.util.StringUtils;

/**
 * 报表定义
 * 
 * @author infosmart
 * 
 */
public class ReportDefine {
	// id
	private Integer reportId;
	// 姓名
	private String reportName;
	// 描述
	private String reportDesc;

	private String width;

	private String height;

	/**
	 * 是否分页显示
	 */
	private int paging;

	/**
	 * 自动宽度
	 */
	private int autowidth;

	/**
	 * 每页大小
	 */
	private Integer pageSize;
	/**
	 * 显示行号
	 */
	private int showRowNum;
	/**
	 * 锁定的前几个列
	 */
	private int lockColumnCount;

	/**
	 * 合并的前几个列
	 */
	private int mergeColumnCount;
	
	/**
	 * 是否分组统计
	 */
	private int grouping;

	/**
	 * 分组统计列(暂只支持一个)
	 */
	/*private GroupingField groupingField;*/

	/**
	 * 分组统计列
	 */
	private List<GroupingField> groupFieldList;

	/**
	 * 是否汇总
	 */
	private int summary;

	/**
	 * 隔行换色
	 */
	private int altRows;

	// 报表列
	private List<ReportCell> reportCellList;
	
	/**
	 * 动态列数量
	 */
	private int dynamicColNum;
	
	public int getDynamicColNum() {
		return dynamicColNum;
	}

	public void setDynamicColNum(int dynamicColNum) {
		this.dynamicColNum = dynamicColNum;
	}

	public void setGrouping(int grouping) {
		this.grouping = grouping;
	}

	public void setGroupFieldList(List<GroupingField> groupFieldList) {
		this.groupFieldList = groupFieldList;
	}

	public List<ReportCell> getReportCellList() {
		return reportCellList;
	}

	public void setReportCellList(List<ReportCell> reportCellList) {
		this.reportCellList = reportCellList;
	}

	public List<GroupingField> getGroupFieldList() {
		return groupFieldList;
	}

	public int getPaging() {
		paging = this.pageSize > 0 ? 1 : 0;
		return paging;
	}

	public void setPaging(int paging) {
		this.paging = paging;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public int getAutowidth() {
		return autowidth;
	}

	public void setAutowidth(int autowidth) {
		this.autowidth = autowidth;
	}

	public int getSummary() {
		return summary;
	}

	public void setSummary(int summary) {
		this.summary = summary;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
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

	public String getReportDesc() {
		return reportDesc;
	}

	public void setReportDesc(String reportDesc) {
		this.reportDesc = reportDesc;
	}

	public int getShowRowNum() {
		return showRowNum;
	}

	public void setShowRowNum(int showRowNum) {
		this.showRowNum = showRowNum;
	}

	public int getLockColumnCount() {
		return lockColumnCount;
	}

	public void setLockColumnCount(int lockColumnCount) {
		this.lockColumnCount = lockColumnCount;
	}

	public int getGrouping() {
		if (groupFieldList == null) {
			this.grouping = 0;
		} else {
			this.grouping = 1;
		}
		return grouping;
	}

	/*public GroupingField getGroupingField() {
		return groupingField;
	}

	public void setGroupingField(GroupingField groupingField) {
		this.groupingField = groupingField;
	}*/

	public int getAltRows() {
		return altRows;
	}

	public void setAltRows(int altRows) {
		this.altRows = altRows;
	}

	public int getMergeColumnCount() {
		return mergeColumnCount;
	}

	public void setMergeColumnCount(int mergeColumnCount) {
		this.mergeColumnCount = mergeColumnCount;
	}

}
