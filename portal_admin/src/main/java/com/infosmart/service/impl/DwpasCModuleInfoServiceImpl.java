package com.infosmart.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infosmart.po.DwpasCmoduleInfo;
import com.infosmart.service.DwpasCModuleInfoService;
import com.infosmart.util.StringUtils;

@Service
public class DwpasCModuleInfoServiceImpl extends BaseServiceImpl implements
		DwpasCModuleInfoService {

	@Override
	public void deleteDwpasCmoduleInfoById(String moduleId)throws Exception {
		this.logger.info("删除模块信息:" + moduleId);
		if (!StringUtils.notNullAndSpace(moduleId)) {
			this.logger.warn("删除模块信息失败：参数为空");
			throw new Exception("删除模块信息失败：参数为空");
		}
		try {
			this.myBatisDao.delete(
					"DwpasRColumnComKpiMapper.deleteDwpasRColumnComKpiByModuleId",
					moduleId);
			// 删除栏目信息
			List<String> moduleIdList = new ArrayList<String>();
			moduleIdList.add(moduleId);
			myBatisDao.delete("DwpasCColumnInfoMapper.deleteDwpasCColumnInfo",
					moduleIdList);
			// 删除模块信息
			this.myBatisDao.delete("DwpasCModuleInfoMapper.delModuleInfoById",
					moduleId);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("删除模块信息失败:"+e.getMessage(), e);
			throw e;

		}
		// 删除栏目关联通用指标
		
	}

	@Override
	public DwpasCmoduleInfo getDwpasCmoduleInfoById(String moduleId) {
		if (!StringUtils.notNullAndSpace(moduleId)) {
			this.logger.warn("查询模块信息失败：参数为空");
			return null;
		}
		return this.myBatisDao.get("DwpasCModuleInfoMapper.getModuleInfoById",
				moduleId);
	}

	@Override
	public List<DwpasCmoduleInfo> getDwpasCmoduleInfoByMenuId(String menuId,
			String dateType) {
		if (!StringUtils.notNullAndSpace(menuId)) {
			this.logger.warn("列出某菜单下的所有模块失败：参数为空");
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("menuId", menuId);
		map.put("dateType", dateType);
		return this.myBatisDao.getList(
				"DwpasCModuleInfoMapper.selectModuleByMenuId", map);
	}

	@Override
	public void insertDwpasCmoduleInfo(Map<String, Integer> map)throws Exception {
		if (map == null || map.isEmpty()) {
			this.logger.warn("insertDwpasCmoduleInfo方法失败：参数map为空");
			throw new Exception("insertDwpasCmoduleInfo方法失败：参数map为空");
		}
		this.myBatisDao.save("DwpasCModuleInfoMapper.insertDwpasCModuleInfo",
				map);
	}

	@Override
	public void deleteDwpasCmoduleInfo(List<String> menuId)throws Exception {
		if (menuId == null || menuId.isEmpty()) {
			this.logger.warn("deleteDwpasCmoduleInfo方法失败：参数menId为空");
			throw new Exception("deleteDwpasCmoduleInfo方法失败：参数menId为空");
		}
		this.myBatisDao.delete("DwpasCModuleInfoMapper.deleteDwpasCModuleInfo",
				menuId);
	}

	@Override
	public List<DwpasCmoduleInfo> getDwpasCmoduleInfoByMenuIds(
			List<String> menuIds) {
		return this.myBatisDao.getList(
				"DwpasCModuleInfoMapper.getModuleByMenuIds", menuIds);
	}

	@Override
	public void updateDwpasCModuleInfo(DwpasCmoduleInfo info)throws Exception  {
		if (info == null) {
			this.logger.warn("updateDwpasCModuleInfo方法失败：参数DwpasCmoduleInfo为空");
			throw new Exception("updateDwpasCModuleInfo方法失败：参数DwpasCmoduleInfo为空");
		}
		this.myBatisDao.update("DwpasCModuleInfoMapper.updateDwpasCModuleInfo",
				info);
	}

}
