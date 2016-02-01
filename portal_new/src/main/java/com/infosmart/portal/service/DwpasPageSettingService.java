package com.infosmart.portal.service;

import java.util.List;
import java.util.Map;

import com.infosmart.portal.pojo.DwpasCModuleInfo;
import com.infosmart.portal.pojo.DwpasCSystemMenu;
import com.infosmart.portal.pojo.DwpasRColumnComKpi;

public interface DwpasPageSettingService {
	/**
	 * @deprecated
	 * @param map
	 * @return
	 * 
	 */
	List<DwpasCModuleInfo> getModuleInfoByMenuIdAndDateType(Map map);

	/**
	 * @deprecated
	 * @param map
	 * @return
	 * 
	 */
	List<DwpasCModuleInfo> getModuleInfoAndColumnInfoByMenuIdAndDateType(Map map);

	/**
	 * 根据菜单ID和查询日期类型查询关联的栏目
	 * @param menuId
	 * @param kpiType
	 * @return
	 */
	List<DwpasCModuleInfo> listDwpasCModuleInfoByByMenuIdAndDateType(
			String menuId, int kpiType);

	List<DwpasRColumnComKpi> getCommonCodeByMenuId(String menuId);

	DwpasCSystemMenu getDwpasCSystemByChildMenuId(String menuId);

	/**
	 * 查询菜单对应的模块信息(不含栏目信息)
	 * 
	 * @param menuId
	 * @param kpiType
	 * @return
	 */
	Map<String, Object> getModuleInfoByMenuIdAndDateType(String menuId,
			int kpiType);

	/**
	 * 查询菜单对应的模块信息(包含栏目信息)
	 * 
	 * @param menuId
	 * @param kpiType
	 * @return
	 */
	Map<String, Object> getModuleInfoAndColumnInfoByMenuIdAndDateType(
			String menuId, int kpiType);
	/**
	 * 根据templateId和menuCode查询menuId
	 * @param templateId
	 * @param menuCode
	 * @return
	 */
	String getMenuIdByTemplateIdAndMenuCode(String templateId, String menuCode);
}
