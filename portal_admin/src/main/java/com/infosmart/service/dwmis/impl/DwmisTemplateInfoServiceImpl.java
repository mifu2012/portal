package com.infosmart.service.dwmis.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.infosmart.po.dwmis.DwmisModuleInfo;
import com.infosmart.po.dwmis.DwmisSubjectInfo;
import com.infosmart.po.dwmis.DwmisTemplateInfo;
import com.infosmart.service.dwmis.DwmisTemplateInfoService;
import com.infosmart.service.impl.BaseServiceImpl;
import com.infosmart.util.StringUtils;

@Service
public class DwmisTemplateInfoServiceImpl extends BaseServiceImpl implements
		DwmisTemplateInfoService {

	/**
	 * 模板列表
	 */
	@Override
	public List<DwmisTemplateInfo> listPageTemplateInfo(
			DwmisTemplateInfo dwmisTemplateInfo) {
		if (dwmisTemplateInfo.getTemplateName() != null
				&& dwmisTemplateInfo.getTemplateName() != "") {
			dwmisTemplateInfo.setTemplateName(dwmisTemplateInfo
					.getTemplateName().trim());
		}
		return this.myBatisDao
				.getList(
						"com.infosmart.DwmisTemplateInfoMapper.listPageDwmisTemplateInfo",
						dwmisTemplateInfo);
	}

	/**
	 * 删除模板
	 */
	@Override
	public void delDwmisTemplateInfo(String templateId) {
		if (!StringUtils.notNullAndSpace(templateId)) {
			this.logger.warn("删除模板失败：模板Id为空!");
			return;
		}
		this.myBatisDao.delete(
				"com.infosmart.DwmisTemplateInfoMapper.delDwmisTemplateInfo",
				templateId);
	}

	/**
	 * 修改模板
	 */
	@Override
	public void editDwmisTemplateInfo(DwmisTemplateInfo dwmisTemplateInfo) {
		this.myBatisDao
				.update("com.infosmart.DwmisTemplateInfoMapper.updateDwmisTemplateInfo",
						dwmisTemplateInfo);
	}

	/**
	 * 根据ID查询模板信息
	 */
	@Override
	public DwmisTemplateInfo getDwmisTemplateInfoById(String templateId) {
		if (!StringUtils.notNullAndSpace(templateId)) {
			this.logger.warn("根据ID查询模板信息失败：模板Id为空!");
			return null;
		}
		return this.myBatisDao
				.get("com.infosmart.DwmisTemplateInfoMapper.getDwmisTemplateInfoById",
						templateId);
	}

	/**
	 * 新增模板
	 */
	@Override
	public void addDwmisTemplateInfo(DwmisTemplateInfo dwmisTemplateInfo) {
		this.myBatisDao.save(
				"com.infosmart.DwmisTemplateInfoMapper.addDwmisTemplateInfo",
				dwmisTemplateInfo);
	}

	/**
	 * 复制模板
	 */
	@Override
	public void saveCopyTemplateInfo(DwmisTemplateInfo dwmisTemplateInfo) {
		if (dwmisTemplateInfo == null) {
			this.logger.warn("复制模板失败：未知源模板");
			return;
		}
		String oldTid = dwmisTemplateInfo.getTemplateId();// 源ID
		dwmisTemplateInfo.setGmtUser("admin");
		String newTid = UUID.randomUUID().toString();
		newTid = newTid.replace("-", "");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dwmisTemplate", dwmisTemplateInfo);
		map.put("newTid", newTid);
		map.put("oldTid", oldTid);
		// 复制模板信息
		this.myBatisDao.save(
				"com.infosmart.DwmisTemplateInfoMapper.copyTemplate", map);
		// 复制主题信息
		this.myBatisDao.save(
				"com.infosmart.DwmisSubjectInfoMapper.copySubjectInfo", map);
		// 复制模块信息
		this.myBatisDao.save("com.infosmart.DwmisModuleInfoMapper.copyModule",
				map);
		// 复制模块图例关联信息
		this.myBatisDao.save(
				"com.infosmart.DwmisModuleInfoMapper.copyModuleAndLegend", map);
	}

	/**
	 * 删除模板
	 */
	@Override
	public void deleteTemplate(String templateId) {
		if (!StringUtils.notNullAndSpace(templateId)) {
			this.logger.warn("删除模板失败：模板Id为空!");
			return;
		}
		List<DwmisSubjectInfo> subjectList = this.myBatisDao
				.getList(
						"com.infosmart.DwmisSubjectInfoMapper.getSubjectInfoByTemplateId",
						templateId);
		if (subjectList == null || subjectList.size() == 0) {
			this.delDwmisTemplateInfo(templateId);
			return;
		} else {
			// 删除模板
			this.delDwmisTemplateInfo(templateId);
		}
		// 删除主题
		this.myBatisDao
				.delete("com.infosmart.DwmisSubjectInfoMapper.delSubjectInfoByTemplateId",
						templateId);
		String[] subjects = null;
		String[] moduleids = null;
		if (subjectList != null && subjectList.size() > 0) {
			subjects = new String[subjectList.size()];
			for (int i = 0; i < subjectList.size(); i++) {
				subjects[i] = subjectList.get(i).getSubjectId();
			}
		}
		if (subjects != null && subjects.length > 0) {
			List<DwmisModuleInfo> moduleList = this.myBatisDao
					.getList(
							"com.infosmart.DwmisModuleInfoMapper.getModuleInfoBySubjectIds",
							Arrays.asList(subjects));
			if (moduleList != null && moduleList.size() > 0) {
				moduleids = new String[moduleList.size()];
				for (int i = 0; i < moduleList.size(); i++) {
					moduleids[i] = moduleList.get(i).getModuleId();
				}
			}

		}
		if (moduleids != null && moduleids.length > 0) {
			this.myBatisDao
					.delete("com.infosmart.DwmisModuleInfoMapper.delModuleAndLegendByModuleId",
							Arrays.asList(moduleids));
		}
		// 删除模块信息
		this.myBatisDao
				.delete("com.infosmart.DwmisModuleInfoMapper.delModuleInfoBySubjectIds",
						Arrays.asList(subjects));
	}

	@Override
	public List<DwmisTemplateInfo> listAllTemplateInfo() {
		return myBatisDao
				.getList("com.infosmart.DwmisTemplateInfoMapper.listAllDwmisTemplateInfo");
	}

}
