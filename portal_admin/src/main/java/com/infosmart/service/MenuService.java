package com.infosmart.service;

import java.util.List;

import com.infosmart.po.Menu;

public interface MenuService {
	/**
	 * 获得所有菜单
	 * 
	 * @return
	 */
	List<Menu> listAllMenu();

	/**
	 * 获得所有父级菜单
	 * 
	 * @return
	 */
	List<Menu> listAllParentMenu();

	/**
	 * 根据父级菜单id获得其下的子级菜单
	 * 
	 * @param parentId
	 * @return
	 */
	List<Menu> listSubMenuByParentId(Integer parentId);

	/**
	 * 根据id获得菜单
	 * 
	 * @param menuId
	 * @return
	 */
	Menu getMenuById(Integer menuId);

	/**
	 * 保存菜单
	 * 
	 * @param menu
	 */
	void saveMenu(Menu menu) throws Exception;

	/**
	 * 根据id删除菜单
	 * 
	 * @param menuId
	 */
	void deleteMenuById(Integer menuId) throws Exception;

	/**
	 * 列出所有子级菜单
	 * 
	 * @return
	 */
	List<Menu> listAllSubMenu();
	/**
	 * 根据菜单name查询
	 * @param MenuName
	 * @return
	 */
	Menu getMenuByName(String MenuName);
}
