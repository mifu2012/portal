package com.infosmart.portal.service.report;

import java.util.List;

import net.sf.json.JSONObject;

import com.infosmart.portal.pojo.User;
import com.infosmart.portal.pojo.report.DimensionDetail;
import com.infosmart.portal.pojo.report.DimensionDetailSec;
import com.infosmart.portal.pojo.report.ReportConfig;
import com.infosmart.portal.pojo.report.ReportDesign;
import com.infosmart.portal.pojo.report.SelfReport;
import com.infosmart.portal.pojo.report.SelfReportColumn;

public interface SelfReportService {
	/**
	 * 得到报表信息
	 * 
	 * @param reportId
	 * @return
	 */
	ReportDesign getSelfReportById(String reportId);

//	ReportDesign getSelfReportConfigById(String reportId);

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
	 * @param selfReport
	 * @return
	 */
	String saveSelfReport(SelfReport selfReport);

	/**
	 * 列出报表数据
	 * 
	 * @param reportId
	 * @return
	 */
	List<List<String>> queryReportDataByConfig(ReportDesign reportConfig);

	/**
	 * 分页查询报表数据
	 * 
	 * @param reportConfig
	 * @return
	 */
	JSONObject queryReportJsonDataByPaging(ReportDesign reportConfig, int pageNo);
	
	/**
	 * 查询报表数据
	 * @param reportConfig
	 * @param pageNo
	 * @return
	 */
	List<List<String>> queryReport(ReportDesign reportConfig, int pageNo);
	

	/**
	 * 用户权限
	 * 
	 * @param userId
	 * @return
	 */
	User getUserAndRoleById(Integer userId);

	List<SelfReport> listAllParentReport();

	List<SelfReport> listTreeReportByParentId(Integer parentId);

	List<ReportConfig> queryConfigById(String reportId);

	List<DimensionDetail> listDimensionDetail(Integer dimensionId);
	/**
	 * 获取二级维度
	 * @param parentId
	 * @return
	 */
	public List<DimensionDetailSec> getDimensionDetailSecList(String parentId);
}
