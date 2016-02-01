package com.infosmart.service.dwmis.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.infosmart.po.dwmis.DwmisLegendInfo;
import com.infosmart.po.dwmis.DwmisLegendModuleR;
import com.infosmart.po.dwmis.DwmisModuleInfo;
import com.infosmart.service.dwmis.DwmisModuleService;
import com.infosmart.service.impl.BaseServiceImpl;
import com.infosmart.util.StringUtils;

@Service
public class DwmisModuleServiceImpl extends BaseServiceImpl implements
		DwmisModuleService {

	@Override
	public List<DwmisModuleInfo> getModluleInfoListBySubjectIdAndTemplateId(
			String subjectId, String templateId) {
		if (!StringUtils.notNullAndSpace(subjectId)
				|| !StringUtils.notNullAndSpace(templateId)) {
			this.logger.warn("根据主题Id查询模块信息失败：主题Id或模板Id为空!");
			return null;
		}
		Map map = new HashMap();
		map.put("subjectId", subjectId);
		map.put("templateId", templateId);
		// 该主题下所有的模块信息
		List<DwmisModuleInfo> moduleInfoList = new ArrayList<DwmisModuleInfo>();
		moduleInfoList = this.myBatisDao
				.getList(
						"com.infosmart.DwmisModuleInfoMapper.getModluleInfoListBySubjectIdAndTemplateId",
						map);
		List<String> moduleIdList = new ArrayList<String>();
		if (moduleInfoList.isEmpty() || moduleInfoList == null) {
			this.logger.warn("当前主题的无模块信息");
			return null;
		}
		for (DwmisModuleInfo moduleInfo : moduleInfoList) {
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
		this.logger.info("图例个数:" + legengInfoList == null ? 0 : legengInfoList
				.size());
		for (DwmisLegendInfo legengInfo : legengInfoList) {
			// this.logger.info("legengInfo-->模块ID:" +
			// legengInfo.getModuleId());
			if (legendListMap.containsKey(legengInfo.getModuleId())) {
				legendListMap.get(legengInfo.getModuleId()).add(legengInfo);
			} else {
				List<DwmisLegendInfo> legendList = new ArrayList<DwmisLegendInfo>();
				legendList.add(legengInfo);
				legendListMap.put(legengInfo.getModuleId(), legendList);
			}
		}
		this.logger.info("图例信息："
				+ (legendListMap == null ? 0 : legendListMap.size()));

		// 将每一个模块对应的图例setter
		for (DwmisModuleInfo moduleInfo : moduleInfoList) {
			moduleInfo.setLegendInfos(legendListMap.get(moduleInfo
					.getModuleId()));
		}
		return moduleInfoList;
	}

	@Override
	public void saveModuleInfo(DwmisModuleInfo moduleInfo) {
		if (moduleInfo == null
				|| !StringUtils.notNullAndSpace(moduleInfo.getLegendIds())) {
			this.logger.warn("新增模块信息失败：模块信息为空!");
			return;
		}
		String[] legengIdArray = moduleInfo.getLegendIds().split(",");
		if (!StringUtils.notNullAndSpace(moduleInfo.getModuleId())) {
			String moduleId = UUID.randomUUID().toString().replace("-", "");
			moduleInfo.setModuleId(moduleId);
			// 批量插入关联信息
			List<DwmisLegendModuleR> legendModuleList = new ArrayList<DwmisLegendModuleR>();
			if (legengIdArray == null || legengIdArray.length <= 0)
				return;
			for (int i = 0; i < legengIdArray.length; i++) {
				DwmisLegendModuleR legendModule = new DwmisLegendModuleR();
				legendModule.setLegendId(legengIdArray[i]);
				legendModule.setSort(i);
				legendModule.setModuleId(moduleInfo.getModuleId());
				legendModuleList.add(legendModule);
			}
			this.myBatisDao.save(
					"com.infosmart.DwmisModuleInfoMapper.saveModuleInfo",
					moduleInfo);
			this.myBatisDao
					.save("com.infosmart.DwmisModuleInfoMapper.insertRelLegendInfoList",
							legendModuleList);
		}
	}

	@Override
	public void updateModuleInfo(DwmisModuleInfo moduleInfo) {
		if (moduleInfo == null
				|| !StringUtils.notNullAndSpace(moduleInfo.getLegendIds())) {
			this.logger.warn("修改模块信息失败：模块信息为空或主题Id为空");
			return;
		}
		this.myBatisDao.save(
				"com.infosmart.DwmisModuleInfoMapper.updateModuleInfo",
				moduleInfo);
		// 删除关联信息
		this.myBatisDao.delete(
				"com.infosmart.DwmisModuleInfoMapper.deleteUnRelLegendInfo",
				moduleInfo.getModuleId());
		// 批量插入关联信息
		List<DwmisLegendModuleR> legendModuleList = new ArrayList<DwmisLegendModuleR>();
		DwmisLegendModuleR legendModule = null;
		String[] legengIdArray = moduleInfo.getLegendIds().split(",");
		this.logger.info("----------------" + moduleInfo.getLegendIds());
		if (legengIdArray==null||legengIdArray.length <= 0)
			return;
		for (int i = 0; i < legengIdArray.length; i++) {
			if (!StringUtils.notNullAndSpace(legengIdArray[i]))
				continue;
			legendModule = new DwmisLegendModuleR();
			legendModule.setLegendId(legengIdArray[i]);
			legendModule.setSort(i);
			legendModule.setModuleId(moduleInfo.getModuleId());
			legendModuleList.add(legendModule);
		}
		this.myBatisDao.save(
				"com.infosmart.DwmisModuleInfoMapper.insertRelLegendInfoList",
				legendModuleList);
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
	public void deleteModuleInfo(String moduleId) {
		if (!StringUtils.notNullAndSpace(moduleId)) {
			this.logger.warn("删除模块信息失败：模块ID为空！");
			return;
		}
		// 删除模块图例关联表中的信息
		this.myBatisDao.delete(
				"com.infosmart.DwmisModuleInfoMapper.deleteLegendModule",
				moduleId);

		this.myBatisDao.delete(
				"com.infosmart.DwmisModuleInfoMapper.deleteModuleInfo",
				moduleId);

	}

	@Override
	public List<DwmisLegendInfo> getUnRelLegendInfoByModuleId(String moduleId) {
		if (!StringUtils.notNullAndSpace(moduleId)) {
			this.logger.warn("查询与该模块不关联的图例信息失败：模块ID为空！");
			return null;
		}
		try {
			return this.myBatisDao
					.getList(
							"com.infosmart.DwmisModuleInfoMapper.getUnRelLegendInfoByModuleId",
							moduleId);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			return null;
		}

	}

	@Override
	public List<DwmisLegendInfo> getRelLegendInfoByModuleId(String moduleId) {
		if (!StringUtils.notNullAndSpace(moduleId)) {
			this.logger.warn("查询与该模块关联的图例信息失败：模块ID为空！");
			return null;
		}
		try {
			return this.myBatisDao
					.getList(
							"com.infosmart.DwmisModuleInfoMapper.getRelLegendInfoByModuleId",
							moduleId);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			return null;
		}
	}

}
