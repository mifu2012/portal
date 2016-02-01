package com.infosmart.portal.service.report;

import java.util.List;
import java.util.Map;

import com.infosmart.portal.pojo.report.ReportChartFiled;
import com.infosmart.portal.pojo.report.ReportDataSource;
import com.infosmart.portal.pojo.report.ReportDesign;

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
			String fileds, final String reportSql,
			final Map<String, Object> paramValMap, String pageNo,
			ReportDataSource dataSource);

	/**
	 * 生成饼图数据
	 * 
	 * @param querySql
	 * @param chartField
	 * @param dataSource
	 * @return
	 */
	List<Map<Object, Object>> getPieChartDates(final String querySql,
			final Map<String, Object> paramValMap, ReportChartFiled chartField,
			ReportDataSource dataSource);
}
