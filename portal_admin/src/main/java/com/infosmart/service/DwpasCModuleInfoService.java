package com.infosmart.service;

import java.util.List;
import java.util.Map;

import com.infosmart.po.DwpasCmoduleInfo;

public interface DwpasCModuleInfoService {
	/**
	 * 根据菜单id,dateType获得模块信息
	 * 
	 * @param dateType
	 * @param menuId
	 * @return
	 */
	List<DwpasCmoduleInfo> getDwpasCmoduleInfoByMenuId(String menuId,
			String dateType);

	/**
	 * 根据ID查询模块信息
	 * 
	 * @param moduleId
	 * @return
	 */
	DwpasCmoduleInfo getDwpasCmoduleInfoById(String moduleId);

	/**
	 * 插入模块信息
	 * 
	 * @param map
	 */
	void insertDwpasCmoduleInfo(Map<String, Integer> map) throws Exception;

	/**
	 * 删除模块信息
	 * 
	 * @param menuId
	 */
	void deleteDwpasCmoduleInfo(List<String> menuId) throws Exception;
	
	/**
	 * 删除模块信息
	 * 
	 * @param menuId
	 */
	void deleteDwpasCmoduleInfoById(String moduleId) throws Exception;

	/**
	 * 根据菜单id集合获得模块信息
	 * 
	 * @param menuIds
	 * @return
	 */
	List<DwpasCmoduleInfo> getDwpasCmoduleInfoByMenuIds(List<String> menuIds);

	/**
	 * 根据菜单id更新模块信息
	 * 
	 * @param info
	 */
	void updateDwpasCModuleInfo(DwpasCmoduleInfo info) throws Exception;
}
