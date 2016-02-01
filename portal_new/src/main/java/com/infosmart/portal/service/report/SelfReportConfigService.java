package com.infosmart.portal.service.report;

import java.util.List;

import net.sf.json.JSONObject;

import com.infosmart.portal.pojo.report.SelfReportColumn;
import com.infosmart.portal.pojo.report.SelfReportConfig;

public interface SelfReportConfigService {
	/**
	 * 得到报表信息
	 * 
	 * @param reportId
	 * @return
	 */
	SelfReportConfig getSelfReportConfigById(String reportId);

	/**
	 * 得到列配置信息
	 * 
	 * @param reportId
	 * @return
	 */
	List<SelfReportColumn> listSelfReportColumnConfigById(String reportId);

	/**
	 * 测试SQL语句，得到列定义
	 * 
	 * @param querySql
	 * @return
	 */
	List<SelfReportColumn> listSelfReportColumnConfigBySQL(String querySql);

	/**
	 * 保存报表配置信息
	 * 
	 * @param selfReportConfig
	 * @return
	 */
	String saveSelfReportConfig(SelfReportConfig selfReportConfig);

	/**
	 * 列出报表数据
	 * 
	 * @param reportId
	 * @return
	 */
	List<List<String>> queryReportDataByConfig(SelfReportConfig reportConfig);

	/**
	 * 分页查询报表数据
	 * @param reportConfig
	 * @return
	 */
	JSONObject queryReportJsonDataByPaging(SelfReportConfig reportConfig,int pageNo);

}
