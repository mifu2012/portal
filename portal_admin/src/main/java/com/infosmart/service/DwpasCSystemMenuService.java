package com.infosmart.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.infosmart.po.DwpasCSystemMenu;

public interface DwpasCSystemMenuService {
	/**
	 * 得到菜单信息及关联的栏目信息
	 * 
	 * @param menuId
	 * @param dateType
	 * @return
	 */
	DwpasCSystemMenu getDwpasCSystemMenuById(String menuId, String dateType);

	/**
	 * 保存系统菜单及关联的栏目信息
	 * 
	 * @param systemMenu
	 * @return
	 */
	void saveDwpasCSystemMenu(DwpasCSystemMenu systemMenu) throws Exception;

	/**
	 * 获取父级菜单
	 * 
	 * @param templateId
	 * @return
	 */
	List<DwpasCSystemMenu> getAllParentMenu(Serializable templateId);

	/**
	 * 获取子级菜单
	 * 
	 * @param menuId
	 * @return
	 */
	List<DwpasCSystemMenu> getAllChildrenMenu(String menuId);
	/**
	 * 根据templateId 父级menuCode 获取经纬仪子菜单
	 * @param menuInfo
	 * @return
	 */
	List<DwpasCSystemMenu> listChildrenSystemMemus(DwpasCSystemMenu menuInfo);

	/**
	 * 根据模板id获得对应菜单
	 * 
	 * @param tid
	 * @return
	 */
	List<DwpasCSystemMenu> getByTemplateId(Integer tid);

	/**
	 * 根据模板id删除系统菜单
	 * 
	 * @param tid
	 */
	void deleteSystemMenu(long tid);

	/**
	 * 复杂插入系统菜单
	 * 
	 * @param systemMenu
	 */
	void saveSystemMenu(Map<String, Integer> map) throws Exception;

	/**
	 * 根据菜单id选择对应菜单
	 * 
	 * @param menuId
	 * @return
	 */
	DwpasCSystemMenu getByMenuId(String menuId);

	/**
	 * 根据id删除对应菜单
	 * 
	 * @param menuId
	 */
	void deleteMenu(String menuId) throws Exception;

	/**
	 * 批量删除菜单
	 * 
	 * @param ids
	 */
	void deleteParentAndChildMenu(List<String> ids) throws Exception;

	/**
	 * 更新菜单
	 * 
	 * @param systemMenu
	 */
	void updateMenu(DwpasCSystemMenu systemMenu) throws Exception;

	/**
	 * 修改菜单url(产品,渠道,用户行为)
	 * @param systemMenu
	 */
	void updateMenuUrl(DwpasCSystemMenu systemMenu) throws Exception;
	//  zy
	/**
	 * 关联产品保存
	 * @param systemMenu
	 */
	void updateMenuNameAndUrl(DwpasCSystemMenu systemMenu) throws Exception;
	/**
	 * 获取经纬仪系统所有的菜单
	 */
	List<DwpasCSystemMenu> getChildMenus(Serializable templateId);

	/**
	 * 子菜单分日月的情况
	 * 
	 * @param templateId
	 * @return
	 */
	List<DwpasCSystemMenu> getChildMenusAndDateType(Serializable templateId);

	/**
	 * 父菜单分日月的情况
	 * 
	 * @param templateId
	 * @return
	 */
	List<DwpasCSystemMenu> getParentMenusAndDateType(Serializable templateId);
	/**
	 * 新增菜单
	 * @param menu
	 */
	void saveDwpascMenu(DwpasCSystemMenu menu) throws Exception;
	/**
	 * 保存产品发展月类型
	 * @param menu
	 */
	void updateDevelopMonth(DwpasCSystemMenu systemMenu) throws Exception;
}
