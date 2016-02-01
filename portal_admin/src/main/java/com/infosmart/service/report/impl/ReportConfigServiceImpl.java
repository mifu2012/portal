package com.infosmart.service.report.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.orm.mybatis.MyBatisDao;
import com.infosmart.po.report.ReportConfig;
import com.infosmart.po.report.ReportDesign;
import com.infosmart.service.report.ReportConfigService;
import com.infosmart.util.StringUtils;

@Service
public class ReportConfigServiceImpl extends SqlSessionDaoSupport implements
		ReportConfigService {
	@Autowired
	private MyBatisDao myBatisDao;

	/**
	 * date 2012-06-04 infosmart
	 */
	public List<ReportDesign> listAllParentReport() {
		return myBatisDao
				.getList("com.infosmart.mapper.ReportConfigMapper.listAllParentReport");
	}

	/**
	 * date 2012-06-04 infosmart
	 */
	public List<ReportConfig> listConfigById(Integer reportId) {
		return myBatisDao.getList(
				"com.infosmart.mapper.ReportConfigMapper.listAllConfigById",
				reportId);
	}

	/**
	 * date 2012-06-04 infosmart
	 */
	public ReportDesign queryReportById(Integer reportId) {
		return myBatisDao.get(
				"com.infosmart.mapper.ReportConfigMapper.queryReportById",
				reportId);
	}

	/**
	 * date 2012-06-04 infosmart
	 */
	@Override
	public void deleteReportConfigById(Integer configId) {
		if (configId == null) {
			this.logger.warn("deleteReportConfigById方法失败：参数configId为空");
			return;
		}
		myBatisDao
				.delete("com.infosmart.mapper.ReportConfigMapper.deleteReportConfigById",
						configId);

	}

	@Override
	public ReportConfig getReportConfigById(Integer configId) {
		return myBatisDao.get(
				"com.infosmart.mapper.ReportConfigMapper.getReportConfigById",
				configId);
	}

	public void saveReportConfig(ReportConfig reportConfig,
			HttpServletRequest request) {
		if (reportConfig == null) {
			this.logger.warn("saveReportConfig方法失败：参数reportConfig为空");
			return;
		}
		if (reportConfig.getConfigId() != null
				&& reportConfig.getConfigId().intValue() > 0) {
			myBatisDao
					.update("com.infosmart.mapper.ReportConfigMapper.updateReportConfig",
							reportConfig);
			// bc.insertLog(request,
			// "更新报表查询条件，配置ID为："+reportConfig.getConfigId());
		} else {
			myBatisDao
					.save("com.infosmart.mapper.ReportConfigMapper.insertReportConfig",
							reportConfig);
			// bc.insertLog(request, "新增报表查询条件");
		}
	}

	/**
	 * 报表查询时间
	 */
	/*
	 * public void saveReportTime(ReportDesign report) { if (report == null) {
	 * this.logger.warn("saveReportTime方法失败：参数report为空"); return; }
	 * myBatisDao.update(
	 * "com.infosmart.mapper.ReportConfigMapper.updateReportTime", report); }
	 */

	@Override
	public List<Map<String, Object>> getReportList() {
		return myBatisDao
				.getList("com.infosmart.mapper.ReportConfigMapper.getReportList");
	}

	/**
	 * 校验查询字段
	 */
	@Override
	public String checkColumnCode(ReportConfig reportconfig) {
		List<ReportConfig> reportConfigList = myBatisDao.getList(
				"com.infosmart.mapper.ReportConfigMapper.checkColumnCode",
				reportconfig);
		if (reportConfigList == null || reportConfigList.isEmpty()) {
			return "true";
		} else {
			return "false";
		}
	}

	@Override
	/**
	 * 根据前后ID 和 type值（上升还是下降） 改变SORT值
	 * @param beforeId
	 * @param afterId
	 * @param type
	 */
	public void updateColumnSort(String beforeId, String afterId) throws Exception {
		if (!StringUtils.notNullAndSpace(beforeId)
				|| !StringUtils.notNullAndSpace(afterId)) {
			this.logger.error("排序失败！");
		}
		try {
			this.myBatisDao.update("com.infosmart.mapper.ReportConfigMapper.updateColumnSortBefore", beforeId);
			this.myBatisDao.update("com.infosmart.mapper.ReportConfigMapper.updateColumnSortAfter", afterId);
		} catch (Exception e) {
			throw e;

		}
		
		
	}

}
