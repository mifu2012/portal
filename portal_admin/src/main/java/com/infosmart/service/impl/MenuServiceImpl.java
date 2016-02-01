package com.infosmart.service.impl;

import java.util.List;

import com.infosmart.mapper.MenuMapper;
import com.infosmart.po.Menu;
import com.infosmart.service.MenuService;

public class MenuServiceImpl extends BaseServiceImpl implements MenuService {

	private MenuMapper menuMapper;

	public void deleteMenuById(Integer menuId) throws Exception {
		if (menuId == null) {
			this.logger.warn("删除菜单失败：参数菜单id为空");
			throw new Exception("删除菜单失败：参数菜单id为空");
		}
		menuMapper.deleteMenuById(menuId);
	}

	public Menu getMenuById(Integer menuId) {
		if (menuId == null) {
			this.logger.warn("获取菜单失败：参数菜单id为空");
			return null;
		}
		return menuMapper.getMenuById(menuId);
	}

	public List<Menu> listAllParentMenu() {
		return menuMapper.listAllParentMenu();
	}

	public void saveMenu(Menu menu) throws Exception {
		if (menu == null) {
			this.logger.warn("保存菜单失败：参数menu为空");
			throw new Exception("保存菜单失败：参数menu为空");
		}
		if (menu.getMenuId() != null && menu.getMenuId().intValue() > 0) {
			menuMapper.updateMenu(menu);
		} else {
			menuMapper.insertMenu(menu);
		}
	}

	public List<Menu> listSubMenuByParentId(Integer parentId) {
		if (parentId == null) {
			this.logger.warn("获取菜单失败：参数菜单id为空");
			return null;
		}
		return menuMapper.listSubMenuByParentId(parentId);
	}

	public List<Menu> listAllMenu() {
		List<Menu> rl = this.listAllParentMenu();
		if (rl != null && rl.size() > 0) {
			for (Menu menu : rl) {
				List<Menu> subList = this.listSubMenuByParentId(menu
						.getMenuId());
				menu.setSubMenu(subList);
			}
		}
		return rl;
	}

	public List<Menu> listAllSubMenu() {
		return menuMapper.listAllSubMenu();
	}

	public MenuMapper getMenuMapper() {
		return menuMapper;
	}

	public void setMenuMapper(MenuMapper menuMapper) {
		this.menuMapper = menuMapper;
	}

	@Override
	public Menu getMenuByName(String MenuName) {
		// TODO Auto-generated method stub
		return menuMapper.QuerySubMenuByName(MenuName);
	}
}
