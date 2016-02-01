package com.infosmart.portal.service.report.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.User;
import com.infosmart.portal.pojo.report.ReportDesign;
import com.infosmart.portal.pojo.report.SelfApply;
import com.infosmart.portal.pojo.report.SelfReport;
import com.infosmart.portal.service.impl.BaseServiceImpl;
import com.infosmart.portal.service.report.SelfApplyService;

@Service
public class SelfApplyServiceImpl extends BaseServiceImpl implements
		SelfApplyService {
	/**
	 * 首页查询
	 */
	@Override
	public List<ReportDesign> queryReportPages(ReportDesign report) {
		return myBatisDao.getList(
				"com.infosmart.portal.pojo.SelfApplyMapper.listPageReports",
				report);
	}

	@Override
	public List<SelfReport> queryReports(Map<Object, Object> map) {
		return myBatisDao.getList(
				"com.infosmart.portal.pojo.SelfApplyMapper.listReports", map);
	}

	/* 获取自服务权限 */
	@Override
	public String getSelfRights(Integer userId) {
		return myBatisDao
				.get("com.infosmart.mapper.User.getSelfRights", userId);
	}

	/* 获取所有子菜单 */
	@Override
	public List<ReportDesign> getChildReport() {
		return myBatisDao
				.getList("com.infosmart.mapper.ReportMapper.getChildReport");
	}

	@Override
	public void insertApplyReport(SelfApply selfApply) {
		myBatisDao.save(
				"com.infosmart.portal.pojo.SelfApplyMapper.insertApplyReport",
				selfApply);
	}

	@Override
	public List<SelfApply> getSelfApplyById(SelfApply selfApply) {
		return myBatisDao.getList(
				"com.infosmart.portal.pojo.SelfApplyMapper.getSelfApplyById",
				selfApply);
	}

	@Override
	public List<SelfReport> getreportMenuList(Integer userId) {
		List<SelfReport> list= null;
		list= myBatisDao.getList(
				"com.infosmart.portal.pojo.SelfApplyMapper.getreportMenuList", userId);
		return list;
	}

	@Override
	public void updateSelfRights(User user) {
		// TODO Auto-generated method stub
		myBatisDao.update("com.infosmart.portal.pojo.SelfApplyMapper.updateSelfRights", user);
	}

}
