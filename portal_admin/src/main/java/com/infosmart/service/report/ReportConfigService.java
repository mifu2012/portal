package com.infosmart.service.report;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.infosmart.po.report.ReportConfig;
import com.infosmart.po.report.ReportDesign;

/**
 * ReportConfigService
 * 
 * @author infosmart
 * 
 */
public interface ReportConfigService {
	/**
	 * 查询所有表结构
	 * 
	 * @return
	 */
	List<ReportDesign> listAllParentReport();

	/**
	 * 分页查询
	 * 
	 * @param reportId
	 * @return
	 */
	List<ReportConfig> listConfigById(Integer reportId);

	/**
	 * 查询报表主表插件
	 * 
	 * @param reportId
	 * @return
	 */
	ReportDesign queryReportById(Integer reportId);

	/**
	 * 通过ID查询数据
	 * 
	 * @param configId
	 * @return
	 */
	ReportConfig getReportConfigById(Integer configId);

	/**
	 * 删除配置
	 * 
	 * @param configId
	 */
	void deleteReportConfigById(Integer configId);

	/**
	 * 添加控件
	 * 
	 * @param reportConfig
	 */
	void saveReportConfig(ReportConfig reportConfig, HttpServletRequest request);

	/**
	 * 测试报表
	 * 
	 * @return
	 */
	List<Map<String, Object>> getReportList();

	/**
	 * 校验查询字段
	 * 
	 * @param reportconfig
	 * @return
	 */
	String checkColumnCode(ReportConfig reportconfig);
	/**
	 * 根据前后ID 和 type值（上升还是下降） 改变SORT值
	 * @param beforeId
	 * @param afterId
	 * @param type
	 */
	void updateColumnSort(String beforeId, String afterId)throws Exception ;

}
