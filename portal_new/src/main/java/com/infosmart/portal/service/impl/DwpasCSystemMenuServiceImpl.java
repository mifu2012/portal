package com.infosmart.portal.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasCSystemMenu;
import com.infosmart.portal.service.DwpasCSystemMenuService;
import com.infosmart.portal.util.StringUtils;

@Service
public class DwpasCSystemMenuServiceImpl extends BaseServiceImpl implements
		DwpasCSystemMenuService {

	@Override
	public List<DwpasCSystemMenu> listAllDwpasCSystemMenu(
			Serializable templateId) {
		return this.myBatisDao.getList(
				"com.infosmart.mapper.DwpasCSysMenu.queryAllSystemMenu",
				templateId);
	}

	@Override
	public List<DwpasCSystemMenu> listOneDwpasCSystemMenu(Integer templateId,
			String menuType) {
		Map map = new HashMap();
		map.put("templateId", templateId);
		map.put("menuType", menuType);
		return this.myBatisDao.getList(
				"com.infosmart.mapper.DwpasCSysMenu.queryOneSystemMenu", map);
	}

	@Override
	public DwpasCSystemMenu getDwpasCSystemMenuById(String menuId) {
		if (!StringUtils.notNullAndSpace(menuId)) {
			this.logger.warn("查询菜单信息失败：参数为空");
			return null;
		}
		return this.myBatisDao.get(
				"com.infosmart.mapper.DwpasCSysMenu.getMenuAndParentMenuInfo",
				menuId);
	}

	@Override
	public DwpasCSystemMenu getMenuInfoByMenuCodeAndTemplateId(String menuCode,
			String templateId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("menuCode", menuCode);
		map.put("templateId", templateId);
		if (!StringUtils.notNullAndSpace(menuCode)
				|| !StringUtils.notNullAndSpace(templateId)) {
			this.logger.warn("查询菜单信息失败：参数为空");
			return null;
		}
		return this.myBatisDao
				.get("com.infosmart.mapper.DwpasCSysMenu.getMenuInfoByMenuCodeAndTemplateId",
						map);
	}

}
