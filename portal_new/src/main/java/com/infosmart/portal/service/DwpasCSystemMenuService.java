package com.infosmart.portal.service;

import java.io.Serializable;
import java.util.List;

import com.infosmart.portal.pojo.DwpasCSystemMenu;

/**
 * 系统菜单管理
 * 
 * @author infosmart
 * 
 */
public interface DwpasCSystemMenuService {

	/**
	 * 列出所有的菜单
	 * 
	 * @return
	 */
	List<DwpasCSystemMenu> listAllDwpasCSystemMenu(Serializable templateId);

	List<DwpasCSystemMenu> listOneDwpasCSystemMenu(Integer templateId,
			String menuType);

	/**
	 * 查询菜单信息及父级信息
	 * 
	 * @param menuId
	 * @return
	 */
	DwpasCSystemMenu getDwpasCSystemMenuById(String menuId);
	
	DwpasCSystemMenu getMenuInfoByMenuCodeAndTemplateId(String menuCode,String templateId);
}
