package com.infosmart.service.report;

import java.util.List;

import com.infosmart.po.DimensionDetail;
import com.infosmart.po.report.ReportCell;
import com.infosmart.po.report.ReportConfig;
import com.infosmart.po.report.ReportDesign;

public interface ReportService {
	/**
	 * date 2012-06-04 infosmart
	 * @return
	 */
	List<ReportDesign> listAllParentReport();

	/**
	 * 添加报表查询
	 * date 2012-06-04 infosmart
	 * @return
	 */
	List<ReportDesign> listAllToAdd();

	/**
	 * 保存报表
	 * date 2012-06-04 infosmart
	 * @param report
	 */
	void saveReport(ReportDesign report);

	/**
	 * 展开二级菜单
	 * date 2012-06-04 infosmart
	 * @param parentId
	 * @return
	 */
	List<ReportDesign> listSubReportByParentId(Integer parentId);

	/**
	 * 根据id删除相关报表
	 * date 2012-06-04 infosmart
	 * @param reportId
	 */
	void deleteReportById(Integer reportId);

	/**
	 * 单个查询报表BY id
	 * date 2012-06-04 infosmart
	 * @param reportId
	 * @return
	 */
	ReportDesign getReportById(Integer reportId);

	/**
	 * 栏目查询所有报表
	 * date 2012-06-04 infosmart
	 * @return
	 */
	// List<Report> reportList();
	List<ReportDesign> listAllReport();
	/**
	 * date 2012-06-04 infosmart
	 * @param reportId
	 * @return
	 */
	ReportDesign queryReportById(String reportId);

	List<ReportConfig> listPageConfigById(String reportId);

	/**
	 * 通过Id查询维度组
	 * 
	 * @param primaryKeyId
	 * @return
	 */
	List<DimensionDetail> listDimensionDetail(Integer dimensionId);
    /**
     * select * from report 所有数据
     */
	List<ReportDesign> listAllReports();
	
	/**
	 * 查询报表数据
	 * @param reportConfig
	 * @param pageNo
	 * @return
	 */
	List<List<String>> queryReport(ReportDesign reportConfig, int pageNo,List<ReportCell> reportCellList);
}
