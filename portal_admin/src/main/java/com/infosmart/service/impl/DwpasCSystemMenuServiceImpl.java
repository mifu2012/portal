package com.infosmart.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.po.DwpasCColumnInfo;
import com.infosmart.po.DwpasCSystemMenu;
import com.infosmart.po.DwpasCmoduleInfo;
import com.infosmart.po.DwpasRColumnComKpi;
import com.infosmart.service.DwpasCColumnInfoService;
import com.infosmart.service.DwpasCModuleInfoService;
import com.infosmart.service.DwpasCSystemMenuService;
import com.infosmart.service.DwpasRColumnComKpiService;

@Service
public class DwpasCSystemMenuServiceImpl extends BaseServiceImpl implements
		DwpasCSystemMenuService {
	@Autowired
	private DwpasCModuleInfoService moduleService;
	@Autowired
	private DwpasCColumnInfoService columnInfoService;
	@Autowired
	private DwpasRColumnComKpiService ComKpiService;

	@Override
	public DwpasCSystemMenu getDwpasCSystemMenuById(String menuId,
			String dateType) {
		if (StringUtils.isEmpty(menuId)) {
			this.logger.warn("获取菜单信息失败，菜单ID为空");
			return null;
		}
		Map map = new HashMap();
		map.put("menuId", menuId);
		map.put("dateType", dateType);
		return this.myBatisDao.get(
				"DwpasCSystemMenuMapper.getMenuAndAllModuleByMenuId", map);
	}

	@Override
	public void saveDwpasCSystemMenu(DwpasCSystemMenu systemMenu)
			throws Exception {
		if (systemMenu == null) {
			this.logger.warn("保存菜单信息失败：参数为空");
			throw new Exception("保存菜单信息失败：参数为空");
		}
		// 保存关联的菜单信息
		this.myBatisDao.update("DwpasCSystemMenuMapper.updateDwpasCSystemMenu",
				systemMenu);
		if (systemMenu.getModuleInfoList() == null
				|| systemMenu.getModuleInfoList().isEmpty()) {
			this.logger.warn("该菜单未关联任何模块:" + systemMenu.getMenuName());
			throw new Exception("该菜单未关联任何模块:" + systemMenu.getMenuName());
		}
		try {
			for (DwpasCmoduleInfo moduleInfo : systemMenu.getModuleInfoList()) {
				// 更新模块信息
				if (moduleInfo == null)
					continue;
				this.logger.info("模块信息:" + moduleInfo.getModuleName()
						+ "，是否显示:" + moduleInfo.getIsShow());
				this.logger.info("帮助信息:" + moduleInfo.getRemark());
				this.myBatisDao.update(
						"DwpasCModuleInfoMapper.updateModuleInfo", moduleInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("保存菜单信息失败：" + e.getMessage(), e);
			throw e;

		}

	}

	@Override
	public List<DwpasCSystemMenu> getAllParentMenu(Serializable templateId) {
		if (templateId == null) {
			this.logger.warn("查询父级菜单信息失败：模板Id为空！");
			return null;
		}
		return this.myBatisDao.getList(
				"DwpasCSystemMenuMapper.listALLParentSystemMemus", templateId);
	}

	@Override
	public List<DwpasCSystemMenu> getAllChildrenMenu(String menuId) {
		if (StringUtils.isBlank(menuId)) {
			this.logger.warn("查询所有子菜单信息失败：菜单Id为空！");
			return null;
		}
		return this.myBatisDao.getList(
				"DwpasCSystemMenuMapper.listALLChildrenSystemMemus", menuId);
	}
	/**
	 * 根据templateId 父级menuCode 获取经纬仪子菜单
	 * @param menuInfo
	 * @return
	 */
	@Override
	public List<DwpasCSystemMenu> listChildrenSystemMemus(DwpasCSystemMenu menuInfo){
		return this.myBatisDao.getList(
				"DwpasCSystemMenuMapper.listChildrenSystemMemus", menuInfo);
	}

	@Override
	public void deleteSystemMenu(long tid) {
		myBatisDao.delete("DwpasCSystemMenuMapper.deleteSystemMenuByMenuId",
				tid);
	}

	@Override
	public List<DwpasCSystemMenu> getByTemplateId(Integer tid) {
		return myBatisDao.getList(
				"DwpasCSystemMenuMapper.getSingalSYstenMenuBYMenuId", tid);
	}

	@Override
	public void saveSystemMenu(Map<String, Integer> map) throws Exception {
		if (map == null || map.isEmpty()) {
			this.logger.warn("saveSystemMenu方法失败：参数map为空");
			throw new Exception("saveSystemMenu方法失败：参数map为空");
		}
		this.myBatisDao.save("DwpasCSystemMenuMapper.insertDwpasCSystemMenu",
				map);
	}

	@Override
	public DwpasCSystemMenu getByMenuId(String menuId) {
		if (StringUtils.isBlank(menuId)) {
			this.logger.warn("查询菜单信息失败：菜单Id为空！");
			return null;
		}
		return this.myBatisDao
				.get("DwpasCSystemMenuMapper.getBYMenuId", menuId);
	}

	@Override
	public void deleteMenu(String menuId) throws Exception {
		if (StringUtils.isBlank(menuId)) {
			this.logger.warn("deleteMenu方法失败：参数menuId为空");
			throw new Exception("deleteMenu方法失败：参数menuId为空");
		}
		this.myBatisDao.delete("DwpasCSystemMenuMapper.deleteSystemMenuBymid",
				menuId);
	}

	@Override
	public void updateMenu(DwpasCSystemMenu systemMenu) throws Exception {
		if (systemMenu == null) {
			this.logger.warn("updateMenu方法失败：参数systemMenu为空");
			throw new Exception("updateMenu方法失败：参数systemMenu为空");
		}
		this.myBatisDao.update("DwpasCSystemMenuMapper.updateDwpasCSystemMenu",
				systemMenu);
	}

	@Override
	public List<DwpasCSystemMenu> getChildMenus(Serializable templateId) {
		return this.myBatisDao.getList(
				"DwpasCSystemMenuMapper.getdwpasChildrenMenusByTemplateId",
				templateId);
	}

	@Override
	public List<DwpasCSystemMenu> getChildMenusAndDateType(
			Serializable templateId) {
		return this.myBatisDao
				.getList(
						"DwpasCSystemMenuMapper.getdwpasChildrenMenusByTemplateIdAndDateType",
						templateId);
	}

	@Override
	public List<DwpasCSystemMenu> getParentMenusAndDateType(
			Serializable templateId) {
		return this.myBatisDao
				.getList(
						"DwpasCSystemMenuMapper.getdwpasParentMenusByTemplateIdAndDateType",
						templateId);
	}

	@Override
	public void deleteParentAndChildMenu(List<String> ids) throws Exception {
		if (ids == null || ids.size() < 1) {
			this.logger.warn("批量删除菜单，参数为空！");
			throw new Exception("批量删除菜单，参数为空！");
		}
		this.myBatisDao.delete(
				"DwpasCSystemMenuMapper.batchdeleteSystemMenuBymid", ids);
	}

	@Override
	public void updateMenuUrl(DwpasCSystemMenu systemMenu) throws Exception {
		if (systemMenu == null) {
			this.logger.warn("修改菜单url失败，参数为空！");
			throw new Exception("修改菜单url失败，参数为空！");
		}
		this.myBatisDao.update(
				"DwpasCSystemMenuMapper.updateDwpasCSystemMenuAndUrl",
				systemMenu);
	}

	@Override
	public void saveDwpascMenu(DwpasCSystemMenu menu) throws Exception {
		if (menu == null) {
			this.logger.warn("新增菜单失败，参数为空！");
			throw new Exception("新增菜单失败，参数为空！");
		}
		this.myBatisDao.save("DwpasCSystemMenuMapper.saveDwpasCSystemMenuInfo",
				menu);
	}

	@Override
	public void updateDevelopMonth(DwpasCSystemMenu systemMenu)
			throws Exception {
		if (systemMenu == null) {
			this.logger.warn("保存产品发展月类型失败，参数为空！");
			throw new Exception("保存产品发展月类型失败，参数为空！");
		}
		try {
			// 1.修改菜单信息
			this.updateMenu(systemMenu);
			if (systemMenu != null) {
				if (systemMenu.getModuleIds() != null
						&& systemMenu.getModuleIds().length > 0) {
					// 2.修改菜单对应的模块的信息
					for (int i = 0; i < systemMenu.getModuleIds().length; i++) {
						DwpasCmoduleInfo module = new DwpasCmoduleInfo();
						module.setModuleId(systemMenu.getModuleIds()[i]);
						module.setModuleName(systemMenu.getModuleNames()[i]);
						module.setRemark(systemMenu.getModuleRemark()[i]);
						moduleService.updateDwpasCModuleInfo(module);
					}
				}
				if (systemMenu.getColumnIds() != null
						&& systemMenu.getColumnIds().length > 0) {
					// 3.修改栏目信息
					for (int j = 0; j < systemMenu.getColumnIds().length; j++) {
						DwpasCColumnInfo column = new DwpasCColumnInfo();
						column.setColumnId(systemMenu.getColumnIds()[j]);
						column.setColumnDisplayName(systemMenu
								.getDisplayNames()[j]);
						column.setColumnOrder(j);
						columnInfoService.updateColumnInfo(column);
						// 4.修改每个栏目对应的关联指标----先删除后插入
						ComKpiService.deleteCommonCode(systemMenu
								.getColumnIds()[j]);
						DwpasRColumnComKpi comkpi = new DwpasRColumnComKpi();
						comkpi.setColumnId(systemMenu.getColumnIds()[j]);
						comkpi.setComKpiCode(systemMenu.getCommCodes1()[j] == "" ? null
								: systemMenu.getCommCodes1()[j]);
						comkpi.setValue1(systemMenu.getTypes1()[j]);
						comkpi.setValue3(systemMenu.getValue1()[j] == "" ? null
								: systemMenu.getValue1()[j]);

						DwpasRColumnComKpi comkpi2 = new DwpasRColumnComKpi();
						comkpi2.setColumnId(systemMenu.getColumnIds()[j]);
						comkpi2.setComKpiCode(systemMenu.getCommCodes2()[j] == "" ? null
								: systemMenu.getCommCodes2()[j]);
						comkpi2.setValue1(systemMenu.getTypes2()[j]);
						comkpi2.setValue3(systemMenu.getValue2()[j] == "" ? null
								: systemMenu.getValue2()[j]);

						DwpasRColumnComKpi comkpi3 = new DwpasRColumnComKpi();
						comkpi3.setColumnId(systemMenu.getColumnIds()[j]);
						comkpi3.setComKpiCode(systemMenu.getCommCodes3()[j] == "" ? null
								: systemMenu.getCommCodes3()[j]);
						comkpi3.setValue1(systemMenu.getTypes3()[j]);
						comkpi3.setValue3(systemMenu.getValue3()[j] == "" ? null
								: systemMenu.getValue3()[j]);

						DwpasRColumnComKpi comkpi4 = new DwpasRColumnComKpi();
						comkpi4.setColumnId(systemMenu.getColumnIds()[j]);
						comkpi4.setComKpiCode(systemMenu.getCommCodes4()[j] == "" ? null
								: systemMenu.getCommCodes4()[j]);
						comkpi4.setValue1(systemMenu.getTypes4()[j]);
						comkpi4.setValue3(systemMenu.getValue4()[j] == "" ? null
								: systemMenu.getValue4()[j]);

						DwpasRColumnComKpi comkpi5 = new DwpasRColumnComKpi();
						comkpi5.setColumnId(systemMenu.getColumnIds()[j]);
						comkpi5.setComKpiCode(systemMenu.getCommCodes5()[j] == "" ? null
								: systemMenu.getCommCodes5()[j]);
						comkpi5.setValue1(systemMenu.getTypes5()[j]);
						comkpi5.setValue3(systemMenu.getValue5()[j] == "" ? null
								: systemMenu.getValue5()[j]);
						ComKpiService.insertCommonCode(comkpi);
						ComKpiService.insertCommonCode(comkpi2);
						ComKpiService.insertCommonCode(comkpi3);
						ComKpiService.insertCommonCode(comkpi4);
						ComKpiService.insertCommonCode(comkpi5);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("保存产品发展月类型失败"+e.getMessage(), e);
			throw e;

		}

	}

	// 关联产品保存 zy
	@Override
	public void updateMenuNameAndUrl(DwpasCSystemMenu systemMenu)
			throws Exception {
		if (systemMenu == null) {
			this.logger.warn("关联产品保存失败，参数为空！");
			throw new Exception("关联产品保存失败，参数为空！");
		}
		this.myBatisDao.update("DwpasCSystemMenuMapper.updateMenuNameAndUrl",
				systemMenu);
	}

}
