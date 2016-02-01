package com.infosmart.service.report.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.infosmart.po.User;
import com.infosmart.po.report.ReportDesign;
import com.infosmart.po.report.SelfApply;
import com.infosmart.service.impl.BaseServiceImpl;
import com.infosmart.service.report.SelfApplyService;

@Service
public class SelfApplyServiceImpl extends BaseServiceImpl implements
		SelfApplyService {
	
	/** 分页查询*/
	@Override
	public List<SelfApply> queryItemPages(SelfApply selfApply) {
		return myBatisDao.getList(
				"com.infosmart.po.selfApplyMapper.listPageItem", selfApply);
	}

	 /**通过ID单一查询*/
	@Override
	public SelfApply getSelfApplyById(Integer id) {
		SelfApply selfApply = myBatisDao.get(
				"com.infosmart.po.selfApplyMapper.getSelfApplyById", id);
		return selfApply;
	}

	
	 /**更新*/
	@Override
	public void updateSelfApply(SelfApply selfApply) {
		if(selfApply == null){
			this.logger.warn("updateSelfApply失败：参数selfApply为空");
			return;
		}
		myBatisDao.update("com.infosmart.po.selfApplyMapper.updateSelfApply", selfApply);
	}
	/**获取所有子节点 date 2012-06-04 infosmart*/
	@Override
	public List<ReportDesign> getChildReport() {
		return myBatisDao.getList("com.infosmart.mapper.ReportMapper.getChildReport");
	}
	/**更新自服务权限*/
	@Override
	public void updateSelfRights(User user) {
		if(user == null){
			this.logger.warn("updateSelfRights失败：参数user为空");
			return;
		}
		myBatisDao.update("com.infosmart.mapper.UserMapper.updateSelfRights", user);
	}

	@Override
	public String getSelfRights(Integer userId) {
		return myBatisDao.get("com.infosmart.mapper.UserMapper.getSelfRights", userId);
	}

	@Override
	public List<SelfApply> querySelfApplyByUsrID(int userId) {
		// TODO Auto-generated method stub
		return myBatisDao.getList("com.infosmart.po.selfApplyMapper.querySelfApplyByUsrID", userId);
	}

	@Override
	public void updateSelfReportName(ReportDesign report) {
		// TODO Auto-generated method stub
		myBatisDao.update("com.infosmart.po.selfApplyMapper.updateSelfReportName", report);
	}

	@Override
	public void deleteSelfApplyByIds(List<Integer> reportIds) {
		// TODO Auto-generated method stub
		myBatisDao.delete("com.infosmart.po.selfApplyMapper.deleteSelfApplyByIds", reportIds);
	}

}
