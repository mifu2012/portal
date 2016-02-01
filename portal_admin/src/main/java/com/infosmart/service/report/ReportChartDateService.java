package com.infosmart.service.report;

import java.util.List;
import java.util.Map;

import com.infosmart.po.report.ReportChartFiled;
import com.infosmart.po.report.ReportDataSource;
import com.infosmart.po.report.ReportDesign;

/**
 * 图表service
 * 
 * @author infosmart
 * 
 */
public interface ReportChartDateService {
	/**
	 * 获得图表数据（除饼图）
	 * 
	 * @param fileds
	 * @param tableName
	 * @param dataSource
	 * @return
	 */
	public List<Object> getReportChartDate(ReportDesign reportDesign,
			String fileds, String reportSql, Map<String, Object> paramValMap,
			String pageNo, ReportDataSource dataSource);

	/**
	 * 生成饼图数据
	 * 
	 * @param querySql
	 * @param chartField
	 * @param dataSource
	 * @return
	 */
	List<Map<Object, Object>> getPieChartDates(String querySql,
			Map<String, Object> paramValMap, ReportChartFiled chartField,
			ReportDataSource dataSource);
}
