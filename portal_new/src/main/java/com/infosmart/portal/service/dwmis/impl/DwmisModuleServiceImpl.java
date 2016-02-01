package com.infosmart.portal.service.dwmis.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.dwmis.DwmisLegendInfo;
import com.infosmart.portal.pojo.dwmis.DwmisModuleInfo;
import com.infosmart.portal.pojo.dwmis.DwmisSubjectInfo;
import com.infosmart.portal.service.dwmis.DwmisModuleService;
import com.infosmart.portal.service.impl.BaseServiceImpl;
import com.infosmart.portal.util.StringUtils;

@Service
public class DwmisModuleServiceImpl extends BaseServiceImpl implements
		DwmisModuleService {

	@Override
	public List<DwmisModuleInfo> getModluleInfoListBySubjectId(String subjectId) {
		if (!StringUtils.notNullAndSpace(subjectId)) {
			this.logger.warn("根据主题Id查询模块信息失败：主题Id为空!");
			return null;
		}
		// 该主题下所有的模块信息
		List<DwmisModuleInfo> moduleInfoList = new ArrayList<DwmisModuleInfo>();
		moduleInfoList = this.myBatisDao
				.getList(
						"com.infosmart.DwmisModuleInfoMapper.getModluleInfoListBySubjectId",
						subjectId);
		if (moduleInfoList == null || moduleInfoList.isEmpty()) {
			this.logger.warn("该主题下的模块信息为空!");
			return null;
		}
		List<String> moduleIdList = new ArrayList<String>();
		for (DwmisModuleInfo moduleInfo : moduleInfoList) {
			if (moduleInfo == null
					|| !StringUtils.notNullAndSpace(moduleInfo.getModuleId()))
				continue;
			moduleIdList.add(moduleInfo.getModuleId());
		}
		// 该主题所有关联的图例
		List<DwmisLegendInfo> legengInfoList = this.myBatisDao
				.getList(
						"com.infosmart.DwmisModuleInfoMapper.getLegendInfoListByModuleIds",
						moduleIdList);
		Map<String, List<DwmisLegendInfo>> legendListMap = new HashMap<String, List<DwmisLegendInfo>>();
		if (legengInfoList == null || legengInfoList.isEmpty()) {
			this.logger.warn("没有任何图例信息");
			return null;
		}

		for (DwmisLegendInfo legengInfo : legengInfoList) {
			if (legengInfo == null
					|| !StringUtils.notNullAndSpace(legengInfo.getModuleId()))
				continue;
			if (legendListMap.containsKey(legengInfo.getModuleId())) {
				legendListMap.get(legengInfo.getModuleId()).add(legengInfo);
			} else {
				List<DwmisLegendInfo> legendList = new ArrayList<DwmisLegendInfo>();
				legendList.add(legengInfo);
				legendListMap.put(legengInfo.getModuleId(), legendList);
			}
		}

		// 将每一个模块对应的图例
		for (DwmisModuleInfo moduleInfo : moduleInfoList) {
			moduleInfo.setLegendInfos(legendListMap.get(moduleInfo
					.getModuleId()));
		}
		return moduleInfoList;
	}

	@Override
	public DwmisModuleInfo getModuleInfoByModuleId(String ModuleId) {
		if (!StringUtils.notNullAndSpace(ModuleId)) {
			this.logger.warn("根据模块Id查询模块信息失败：模块Id为空！");
			return null;
		}
		try {
			return this.myBatisDao
					.get("com.infosmart.DwmisModuleInfoMapper.getModuleInfoByModuleId",
							ModuleId);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public List<DwmisSubjectInfo> getAllSubjectByTempId(String templateId) {
		if (!StringUtils.notNullAndSpace(templateId)) {
			this.logger.warn("根据模板Id查询主题信息失败：模板Id为空！");
			return null;
		}
		return this.myBatisDao.getList(
				"com.infosmart.DwmisModuleInfoMapper.getAllSubjectByTempId",
				templateId);
	}

	@Override
	public DwmisSubjectInfo getSubjectInfoById(String subjectId) {
		if (!StringUtils.notNullAndSpace(subjectId)) {
			this.logger.warn("根据主题ID查询主题信息失败：主题Id为空！");
			return null;
		}
		return this.myBatisDao
				.get("com.infosmart.DwmisModuleInfoMapper.getSubjectInfoBySubjectId",
						subjectId);
	}
}
