package com.infosmart.portal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasCColumnInfo;
import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCModuleInfo;
import com.infosmart.portal.pojo.DwpasCSystemMenu;
import com.infosmart.portal.pojo.DwpasRColumnComKpi;
import com.infosmart.portal.service.DwpasPageSettingService;
import com.infosmart.portal.util.StringUtils;

@Service
public class DwpasPageSettingServiceImpl extends BaseServiceImpl implements
		DwpasPageSettingService {

	@Override
	public List<DwpasCModuleInfo> listDwpasCModuleInfoByByMenuIdAndDateType(
			String menuId, int kpiType) {
		String dateType = "D";
		if (kpiType == DwpasCKpiInfo.KPI_TYPE_OF_MONTH) {
			dateType = "M";
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("menuId", menuId);
		paramMap.put("dateType", dateType);
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasPageSettingSql.getModuleInfoMapByMenuIdAndDateType",
						paramMap);
	}

	@Override
	public Map<String, Object> getModuleInfoByMenuIdAndDateType(String menuId,
			int kpiType) {
		this.logger.info("列出某菜单下所有的模块");
		if (!StringUtils.notNullAndSpace(menuId)) {
			this.logger.warn("列出某菜单下所有的模块");
			return new HashMap<String, Object>();
		}
		String dateType = "D";
		if (kpiType == DwpasCKpiInfo.KPI_TYPE_OF_MONTH) {
			dateType = "M";
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("menuId", menuId);
		paramMap.put("dateType", dateType);
		List<DwpasCModuleInfo> moduleInfoList = this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasPageSettingSql.getModuleInfoMapByMenuIdAndDateType",
						paramMap);
		if (moduleInfoList == null || moduleInfoList.isEmpty()) {
			return new HashMap<String, Object>();
		}
		Map<String, Object> moduleInfoMap = new HashMap<String, Object>();
		for (DwpasCModuleInfo moduleInfo : moduleInfoList) {
			moduleInfoMap.put(moduleInfo.getModuleCode(), moduleInfo);
		}
		return moduleInfoMap;
	}

	@Override
	public List<DwpasCModuleInfo> getModuleInfoByMenuIdAndDateType(Map map) {
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasPageSettingSql.getModuleInfoMapByMenuIdAndDateType",
						map);
	}

	@Override
	public List<DwpasCModuleInfo> getModuleInfoAndColumnInfoByMenuIdAndDateType(
			Map map) {
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasPageSettingSql.getModuleColumnInfoByMenuIdAndDateType",
						map);
	}

	@Override
	public List<DwpasRColumnComKpi> getCommonCodeByMenuId(String menuId) {
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasPageSettingSql.queryCommonCodeBymenuId",
						menuId);
	}

	@Override
	public DwpasCSystemMenu getDwpasCSystemByChildMenuId(String menuId) {
		return this.myBatisDao
				.get("com.infosmart.mapper.DwpasPageSettingSql.DwpasCSystemMenuByChildMenuId",
						menuId);
	}

	@Override
	public Map<String, Object> getModuleInfoAndColumnInfoByMenuIdAndDateType(
			String menuId, int kpiType) {
		if (!StringUtils.notNullAndSpace(menuId)) {
			this.logger.warn("列出某菜单下所有的模块,但该菜单ID为空");
			return new HashMap<String, Object>();
		}
		this.logger.info("列出某菜单下所有的模块以及栏目信息:" + menuId);
		String dateType = "D";
		if (kpiType == DwpasCKpiInfo.KPI_TYPE_OF_MONTH) {
			dateType = "M";
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("menuId", menuId);
		paramMap.put("dateType", dateType);
		List<DwpasCModuleInfo> moduleInfoList = this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasPageSettingSql.getModuleColumnInfoByMenuIdAndDateType",
						paramMap);
		if (moduleInfoList == null || moduleInfoList.isEmpty()) {
			this.logger.warn("未找到该菜单下的模块及栏目信息:" + menuId);
			return new HashMap<String, Object>();
		}
		Map<String, Object> moduleInfoMap = new HashMap<String, Object>();
		for (DwpasCModuleInfo moduleInfo : moduleInfoList) {
			// 模块信息
			moduleInfoMap.put(moduleInfo.getModuleCode(), moduleInfo);
			if (moduleInfo.getColumnlist() != null
					&& moduleInfo.getColumnlist().size() > 0) {
				this.logger.info("该模块有相关的栏目信息:" + moduleInfo.getModuleName());
				for (DwpasCColumnInfo columnInfo : moduleInfo.getColumnlist()) {
					// 栏目信息
					this.logger.info("栏目信息:" + columnInfo.getColumnCode() + ","
							+ columnInfo.getColumnName());
					moduleInfoMap.put(columnInfo.getColumnCode(), columnInfo);
				}
			}
		}
		return moduleInfoMap;
	}
	
	@Override
	public String getMenuIdByTemplateIdAndMenuCode(String templateId, String menuCode){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("templateId", templateId);
		map.put("menuCode", menuCode);
		return this.myBatisDao.get("com.infosmart.mapper.DwpasPageSettingSql.getMenuIdByTemplateIdAndMenuCode", map);
	}

}
