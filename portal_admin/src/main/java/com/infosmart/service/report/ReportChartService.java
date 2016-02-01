package com.infosmart.service.report;

import java.util.List;

import com.infosmart.po.report.ReportChartColumn;
import com.infosmart.po.report.ReportChartFiled;
import com.infosmart.po.report.ReportDataSource;

/**
 * ReportChartService
 * 
 * @author infosmart
 * 
 */
public interface ReportChartService {
	/**
	 * 获取值系列字段
	 * 
	 * @param querySql
	 * @param dataSource
	 * @return
	 */
	public List<ReportChartColumn> getTableFiledY(String querySql,
			ReportDataSource dataSource);

	/**
	 * 获得类别系列字段
	 * 
	 * @param querySql
	 * @param dataSource
	 * @return
	 */
	public List<ReportChartColumn> getTableFiledX(String querySql,
			ReportDataSource dataSource);

	/**
	 * 获得已选择和未选择的字段
	 * 
	 * @param chartId
	 * @return
	 */
	public ReportChartFiled getIsSelOrNotTableFiled(int chartId);

	/**
	 * 获得所有的图表
	 * 
	 * @param isShow
	 * @param reportId
	 * @return
	 */
	public List<ReportChartFiled> getAllReportChart(int reportId);

	/**
	 * 修改图表定义
	 * 
	 * @param reportChartFiled
	 */
	public void updateChartFiled(ReportChartFiled reportChartFiled);

	/**
	 * 删除图表
	 * 
	 * @param chartId
	 */
	public void deleteChartFiled(int chartId);

	/**
	 * 新增图表
	 * 
	 * @param reportChartFiled
	 */
	public void insertChartFiled(ReportChartFiled reportChartFiled);
}
