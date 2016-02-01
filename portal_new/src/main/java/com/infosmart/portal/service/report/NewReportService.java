package com.infosmart.portal.service.report;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.infosmart.portal.pojo.report.GroupingField;
import com.infosmart.portal.pojo.report.ReportCell;
import com.infosmart.portal.pojo.report.ReportDataSource;
import com.infosmart.portal.pojo.report.ReportDesign;

public interface NewReportService {
	/**
	 * 得到报表定义信息
	 * 
	 * @param rptDesignId
	 * @return
	 */
	ReportDesign getReportDesignById(Integer rptDesignId);

	/**
	 * 获取报表数据源
	 * 
	 * @param id
	 * @return
	 */
	ReportDataSource getDataSourceById(Integer id);

	/**
	 * 列出报表数据
	 * 
	 * @param querySql
	 * @return
	 */
	List<Map<String, String>> listReportData(String querySql,
			List<ReportCell> queryFieldList, String dataSourceId,
			Map<String, Object> paramValMap);

	/**
	 * 查询报表数据
	 * 
	 * @param reportConfig
	 * @param pageNo
	 * @return
	 */
	List<List<String>> queryReport(ReportDesign reportDesign, int pageNo,
			List<ReportCell> reportCellList, Map<String, Object> paramValMap);

	/**
	 * 分页查询报表数据
	 * 
	 * @param reportConfig
	 * @return
	 */
	JSONObject queryReportJsonDataByPaging(ReportDesign reportInfo,
			List<ReportCell> reportCellList, int pageNo,
			List<GroupingField> groupFieldList, Map<String, Object> paramValMap);
	
	/**
	 * 获取动态列
	 * @param reportDesign
	 * @param queryColumn
	 * @param paramValMap
	 * @return
	 */
	List<String> getDynamicColumns(ReportDesign reportDesign,
			String queryColumn, Map<String, Object> paramValMap);
	
	/**
	 * 查询导出数据
	 * @param reportDesign 报表配置
	 * @param fieldCellList 数据列
	 * @param paramValMap 查询参数Map
	 * @param dynamicColumnsList 动态表头数组
	 * @return 导出数据
	 */
	List<List<String>> queryExportData(ReportDesign reportDesign,
			List<ReportCell> fieldCellList, Map<String, Object> paramValMap);
}
