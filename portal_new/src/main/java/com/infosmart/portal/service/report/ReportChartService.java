package com.infosmart.portal.service.report;

import java.util.List;

import com.infosmart.portal.pojo.report.ReportChartFiled;

/**
 * 
 * @author infosmart
 * 
 */
public interface ReportChartService {
	/**
	 * 获取所有饼图字段对象
	 * @param reportId
	 * @return
	 */
	public List<ReportChartFiled> getAllReportChart(int reportId);
	/**
	 * 获取图表已选择字段和未选择字段
	 * @param chartId
	 * @return
	 */
	public ReportChartFiled getIsSelOrNotTableFiled(int chartId);
}
