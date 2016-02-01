package com.infosmart.service.report;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.infosmart.po.report.GroupingField;
import com.infosmart.po.report.ReportCell;
import com.infosmart.po.report.ReportDataSource;
import com.infosmart.po.report.ReportDesign;
import com.infosmart.po.report.ReportField;

/**
 * 新报表service
 * 
 * @author infosmart
 * 
 */
public interface NewReportService {
	/**
	 * 列出报表数据
	 * 
	 * @param querySql
	 * @return
	 */
	List<Map<String, String>> listReportData(String querySql,
			List<ReportCell> queryFieldList, String dataSourceId);

	/**
	 * 列出报表数据
	 * 
	 * @param querySql
	 * @return
	 */
	List<ReportField> listReportField(String querySql, String dataSourceId);

	/**
	 * 得到报表定义信息
	 * 
	 * @param rptDesignId
	 * @return
	 */
	ReportDesign getReportDesignById(Integer rptDesignId);

	/**
	 * 更新报表定义信息
	 * 
	 * @param repDesign
	 */
	void updateReportDesign(ReportDesign repDesign);

	/**
	 * 获取报表数据源
	 * 
	 * @param reportId
	 * @return
	 */
	ReportDataSource getDataSourceById(Integer id);

	/**
	 * 查询所有数据源
	 * 
	 * @return
	 */
	List<ReportDataSource> getListDataSource();

	/**
	 * 编辑/存数据源信息
	 * 
	 * @param dataSource
	 * @param request
	 */
	void saveDataSource(ReportDataSource dataSource, String sourceId,
			HttpServletRequest request);

	/**
	 * 更新报表SQL
	 * 
	 * @param reportDesign
	 */
	void updateReportSql(ReportDesign reportDesign);

	/**
	 * 测试连接池
	 * 
	 * @param reportDataSource
	 * @return
	 */
	boolean testDataSource(ReportDataSource reportDataSource);

	/**
	 * 测试报表语句
	 * 
	 * @param dataSource
	 * @param reportSql
	 * @return
	 */
	int testReportSql(ReportDataSource dataSource, String reportSql);

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
	 * 校验驱动名
	 * 
	 * @param dataSource
	 * @return
	 */
	String checkDriverName(ReportDataSource dataSource, String aliasDriverName);

	/**
	 * 通过数据源ID获取报表
	 * 
	 * @param dataSourceId
	 * @return
	 */
	List<ReportDataSource> getListReportById(String dataSourceId);

	/**
	 * 删除数据源
	 * 
	 * @param dataSourceId
	 */
	void deleteDataSourceById(Integer dataSourceId);

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
