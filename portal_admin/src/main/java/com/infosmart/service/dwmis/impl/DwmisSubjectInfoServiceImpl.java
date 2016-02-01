package com.infosmart.service.dwmis.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.infosmart.po.dwmis.DwmisModuleInfo;
import com.infosmart.po.dwmis.DwmisSubjectInfo;
import com.infosmart.service.dwmis.DwmisSubjectService;
import com.infosmart.service.impl.BaseServiceImpl;
import com.infosmart.util.StringUtils;

@Service
public class DwmisSubjectInfoServiceImpl extends BaseServiceImpl implements
		DwmisSubjectService {

	/**
	 * 主题列表
	 */
	@Override
	public List<DwmisSubjectInfo> getAllSubjectInfos() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 保存主题信息
	 */
	@Override
	public void saveSubjectInfo(DwmisSubjectInfo subjectInfo) {
		if (subjectInfo == null) {
			this.logger.warn("保存主题信息失败:主题信息为空!");
			return;
		}
		this.myBatisDao.update(
				"com.infosmart.DwmisSubjectInfoMapper.updateSubjectInfo",
				subjectInfo);
		if (subjectInfo.getModuleInfoList() == null
				|| subjectInfo.getModuleInfoList().isEmpty()) {
			this.logger.warn("保存模板信息失败：该模板无模块信息！");
			return;
		}
		for (DwmisModuleInfo moduleInfo : subjectInfo.getModuleInfoList()) {
			this.myBatisDao.save(
					"com.infosmart.DwmisModuleInfoMapper.updateModuleInfo",
					moduleInfo);
		}
	}

	/**
	 * 根据ID查询主题信息
	 */
	@Override
	public DwmisSubjectInfo getSubjectInfoById(String subjectid) {
		if (!StringUtils.notNullAndSpace(subjectid)) {
			this.logger.warn("根据主题ID查询主题信息失败:主题Id为空！");
			return null;
		}
		return this.myBatisDao.get(
				"com.infosmart.DwmisSubjectInfoMapper.getSubjectInfoById",
				subjectid);
	}

	/**
	 * 根据模板ID查询主题信息集
	 */
	@Override
	public List<DwmisSubjectInfo> getSubjectInfoByTemplateId(String templateId) {
		return this.myBatisDao
				.getList(
						"com.infosmart.DwmisSubjectInfoMapper.getSubjectInfoByTemplateIdNoIsShow",
						templateId);
	}

	/**
	 * 新增主题信息
	 */
	@Override
	public void addSubjectInfo(DwmisSubjectInfo dwmisSubjectInfo) {
		String subjectId = UUID.randomUUID().toString();
		subjectId = subjectId.replace("-", "");
		dwmisSubjectInfo.setSubjectId(subjectId);
		String subjectCode = UUID.randomUUID().toString();
		subjectCode = subjectId.replace("-", "");
		dwmisSubjectInfo.setSubjectCode(subjectCode);
		this.myBatisDao.save(
				"com.infosmart.DwmisSubjectInfoMapper.addSubjectInfo",
				dwmisSubjectInfo);
	}

	/**
	 * 修改主题信息
	 */
	@Override
	public void updateSubjectInfo(DwmisSubjectInfo dwmisSubjectInfo) {
		this.myBatisDao.save(
				"com.infosmart.DwmisSubjectInfoMapper.updateSubjectInfo",
				dwmisSubjectInfo);

	}

	/**
	 * 删除主题信息
	 */
	@Override
	public void deleteSubjectInfo(String subjectId) {
		this.logger.info("删除主题：" + subjectId);
		List<DwmisModuleInfo> moduleList = this.myBatisDao
				.getList(
						"com.infosmart.DwmisModuleInfoMapper.getModluleInfoListBySubjectId",
						subjectId);
		if (moduleList != null && moduleList.size() > 0) {
			List<String> moduleIds = new ArrayList<String>();
			DwmisModuleInfo dwmisModuleInfo = null;
			for (int i = 0; i < moduleList.size(); i++) {
				dwmisModuleInfo = moduleList.get(i);
				moduleIds.add(dwmisModuleInfo.getModuleId());
			}
			this.myBatisDao
					.delete("com.infosmart.DwmisModuleInfoMapper.delModuleAndLegendByModuleId",
							moduleIds);
			this.myBatisDao
					.delete("com.infosmart.DwmisModuleInfoMapper.delModuleInfoByModuleIds",
							moduleIds);
		}
		this.myBatisDao.delete(
				"com.infosmart.DwmisModuleInfoMapper.delModuleInfoBySubjectId",
				subjectId);

		this.myBatisDao.delete(
				"com.infosmart.DwmisSubjectInfoMapper.delSubjectInfoById",
				subjectId);
	}

}
