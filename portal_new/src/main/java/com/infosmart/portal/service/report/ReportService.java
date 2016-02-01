package com.infosmart.portal.service.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.portal.action.BaseController;
import com.infosmart.portal.dao.MyBatisDao;
import com.infosmart.portal.pojo.User;
import com.infosmart.portal.pojo.report.DimensionDetail;
import com.infosmart.portal.pojo.report.ReportConfig;
import com.infosmart.portal.pojo.report.ReportDesign;

/**
 * 
 * @author hgt
 * 
 */
@Service
public class ReportService extends BaseController {

	@Autowired
	private MyBatisDao myBatisDao;

	/**
	 * 获取报表菜单
	 * 
	 * @param userId
	 * @return
	 */
	public List<ReportDesign> listAllParentReport() {
		return myBatisDao
				.getList("com.infosmart.mapper.ReportMapper.listAllParentReport");
	}

	/**
	 * 通过Id查询维度从表详细信息
	 * 
	 * @param primaryKeyId
	 * @return
	 */
	public DimensionDetail getDimensionDetailById(Integer primaryKeyId) {
		return myBatisDao.get(
				"com.infosmart.mapper.ReportMapper.getDimensionDetailById",
				primaryKeyId);
	}

	/**
	 * 通过Id查询维度组
	 * 
	 * @param primaryKeyId
	 * @return
	 */
	public List<Object> listDimensionDetail(Integer primaryKeyId) {
		return myBatisDao.getList(
				"com.infosmart.mapper.ReportMapper.listDimensionDetail",
				primaryKeyId);
	}

	/**
	 * 获取某目录下一级报表
	 * 
	 * @param userId
	 * @return
	 */
	public List<ReportDesign> listSubReportByParentId(Integer parentId) {
		return myBatisDao.getList(
				"com.infosmart.mapper.ReportMapper.listSubReportByParentId",
				parentId);
	}

	/**
	 * 获取某目录下所有报表
	 * 
	 * @param userId
	 * @return
	 */
	public List<ReportDesign> listTreeReportByParentId(Integer parentId) {
		List<ReportDesign> subList = this.listSubReportByParentId(parentId);
		for (ReportDesign subReport : subList) {
			List<ReportDesign> threeList = this.listSubReportByParentId(subReport
					.getReportId());
			subReport.setSubReport(threeList);
		}
		return subList;
	}

	/**
	 * 获取所有报表
	 * 
	 * @param userId
	 * @return
	 */
	public List<ReportDesign> listAllReport() {
		List<ReportDesign> rl = this.listAllParentReport();
		for (ReportDesign report : rl) {
			List<ReportDesign> subList = this.listSubReportByParentId(report
					.getReportId());
			report.setSubReport(subList);
			for (ReportDesign subReport : subList) {
				List<ReportDesign> threeList = this.listSubReportByParentId(subReport
						.getReportId());
				subReport.setSubReport(threeList);
			}
		}
		return rl;
	}

	/**
	 * 用户权限
	 * 
	 * @param userId
	 * @return
	 */
	public User getUserAndRoleById(Integer userId) {
		User user = (User) myBatisDao.get(
				"com.infosmart.mapper.ReportMapper.getUserAndRoleById", userId);
		return user;
	}

	public ReportDesign queryReportById(Integer reportId) {
		ReportDesign report = null;
		report = myBatisDao.get(
				"com.infosmart.mapper.ReportMapper.queryReportById", reportId);
		return report;
	}

	/**
	 * 查询报表关联控件
	 * 
	 * @return
	 */
	public List<ReportConfig> queryConfigById(Integer reportId) {
		List<ReportConfig> configList = null;
		configList = myBatisDao.getList(
				"com.infosmart.mapper.ReportMapper.queryConfigById", reportId);
		return configList;
	}

}
