package com.infosmart.controller;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.po.ComKpiInfo;
import com.infosmart.po.DwpasCColumnInfo;
import com.infosmart.po.DwpasCComKpiInfo;
import com.infosmart.po.DwpasCSystemMenu;
import com.infosmart.po.DwpasCTemplateInfo;
import com.infosmart.po.DwpasCmoduleInfo;
import com.infosmart.po.DwpasRColumnComKpi;
import com.infosmart.po.Role;
import com.infosmart.po.User;
import com.infosmart.service.ComKpiInfoService;
import com.infosmart.service.DwpasCColumnInfoService;
import com.infosmart.service.DwpasCModuleInfoService;
import com.infosmart.service.DwpasCSystemMenuService;
import com.infosmart.service.DwpasCTemplateInfoService;
import com.infosmart.service.DwpasRColumnComKpiService;
import com.infosmart.service.DwpasRPrdTemplateService;
import com.infosmart.service.DwpasTemplatePopedomSerivce;
import com.infosmart.service.RoleService;
import com.infosmart.service.UserService;
import com.infosmart.util.StringUtils;

@Controller
public class DwpasCTemplateInfoController extends BaseController {
	@Autowired
	private DwpasCTemplateInfoService templateInfoService;
	@Autowired
	private DwpasCColumnInfoService columnInfoService;
	@Autowired
	private DwpasRColumnComKpiService ComKpiService;
	@Autowired
	private DwpasTemplatePopedomSerivce popedomService;
	@Autowired
	private DwpasCModuleInfoService moduleService;
	@Autowired
	private DwpasCSystemMenuService menuService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	@Autowired
	private DwpasRPrdTemplateService rPrdTemplateService;
	@Autowired
	private ComKpiInfoService comKpiInfoService;

	private final String SUCCESS_ACTION = "/common/save_result";

	/**
	 * 模板列表
	 * 
	 * @param request
	 * @param response
	 * @param templateInfo
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping("/dwpas/listPageTemplateInfo")
	public ModelAndView listPageTemplateInfo(HttpServletRequest request,
			HttpServletResponse response, DwpasCTemplateInfo templateInfo) {
		// DwpasCTemplateInfo info = new DwpasCTemplateInfo();
		if (templateInfo.getTemplateName() != null
				&& !templateInfo.getTemplateName().equals("")) {
			templateInfo.setTemplateName(templateInfo.getTemplateName().trim());
		}
		List<DwpasCTemplateInfo> templateInfos = templateInfoService
				.listPageTemplateInfo(templateInfo);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/dwpas/templateInfo");
		mv.addObject("templateInfos", templateInfos);
		mv.addObject("templateInfo", templateInfo);
		this.insertLog(request, "查看模板列表");
		return mv;
	}

	/**
	 * 进入复制
	 * 
	 * @param templateId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dwpas/getTemplateInfoByID{templateId}")
	public ModelAndView getTemplateInfoByID(@PathVariable Long templateId,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (templateId == null) {
			this.logger.warn("获得模板信息时传递的模板id为null");
		}
		DwpasCTemplateInfo info = templateInfoService
				.getTemplateByID(templateId);
		return new ModelAndView("/admin/dwpas/copytemplateInfo", "template",
				info);
	}

	/**
	 * 进行模板复制
	 * 
	 * @param request
	 * @param response
	 * @param templateInfo
	 * @return
	 */
	@RequestMapping("/dwpas/copyTemplate")
	public ModelAndView copyTemplate(HttpServletRequest request,
			HttpServletResponse response, DwpasCTemplateInfo templateInfo)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(SUCCESS_ACTION);
		if (templateInfo == null) {
			this.logger.warn("模板复制时传递的模板信息对象为null");
			mv.addObject("msg", "failed");
			return mv;
		}
		Map<String, Integer> map = new HashMap<String, Integer>();

		try {
			if (templateInfo != null) {
				map.put("oldTid", templateInfo.getTemplateId());// 源ID
				// 复制模板信息
				templateInfoService.saveTemplateInfo(templateInfo);
				map.put("newTid", templateInfo.getTemplateId());
			}
			// 复制菜单信息
			menuService.saveSystemMenu(map);
			// 复制模块信息
			moduleService.insertDwpasCmoduleInfo(map);
			// 复制栏目信息
			columnInfoService.saveDwpasCColumnInfo(map);
			// 复制栏目关联的指标信息
			ComKpiService.saveDwpasRColumnComKpi(map);
			// 复制模板关联的产品信息
			rPrdTemplateService.insertDwpasRPrdTemplate(map);
			mv.addObject("msg", "success");
		} catch (Exception e) {

			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			mv.addObject("msg", "failed");
		}

		return mv;
	}

	/**
	 * 删除模板
	 * 
	 * @param templateId
	 * @param out
	 */
	@RequestMapping(value = "/dwpas/deleteTemplate{templateId}")
	public void deleteTemplate(@PathVariable Integer templateId,
			PrintWriter out, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/plain");
		if (templateId == null) {
			this.logger.warn("删除模板时传递的模板id为null");
			this.sendMsgToClient(isFailed, response);
			return;
		}
		try {
			templateInfoService.deleteTemplateInfo(templateId);// 删除模板
			rPrdTemplateService.deleteDwpasRPrdTemplate(templateId);// 删除模板关联的
			List<DwpasCSystemMenu> menuList = menuService
					.getByTemplateId(templateId);
			menuService.deleteSystemMenu(templateId);// 删除系统菜单
			String[] menuids = null;
			String[] moduleids = null;
			String[] columnids = null;
			if (menuList != null && menuList.size() > 0) {
				menuids = new String[menuList.size()];
				for (int i = 0; i < menuList.size(); i++) {
					menuids[i] = menuList.get(i).getMenuId();
				}
			}
			if (menuids != null && menuids.length > 0) {
				List<DwpasCmoduleInfo> moduleList = moduleService
						.getDwpasCmoduleInfoByMenuIds(Arrays.asList(menuids));
				moduleService.deleteDwpasCmoduleInfo(Arrays.asList(menuids));// 删除模块表
				if (moduleList != null && moduleList.size() > 0) {
					moduleids = new String[moduleList.size()];
					for (int i = 0; i < moduleList.size(); i++) {
						moduleids[i] = moduleList.get(i).getModuleId();
					}
				}

			}
			if (moduleids != null && moduleids.length > 0) {
				List<DwpasCColumnInfo> columnList = columnInfoService
						.getBYModuleIds(Arrays.asList(moduleids));
				columnInfoService.deleteDwpasCColumnInfo(Arrays
						.asList(moduleids));// 删除栏目
				if (columnList != null && columnList.size() > 0) {
					columnids = new String[columnList.size()];
					for (int i = 0; i < columnList.size(); i++) {
						columnids[i] = columnList.get(i).getColumnId();
					}
				}
			}
			if (columnids != null && columnids.length > 0) {
				ComKpiService
						.deleteDwpasRColumnComKpi(Arrays.asList(columnids));
			}
			out.write("success");
			this.insertLog(request, "删除模板");
		} catch (Exception e) {

			out.write("failed");
		}

		out.close();
	}

	/**
	 * 进入授权
	 * 
	 * @param templateId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dwpas/accessControl{templateId}")
	public ModelAndView accessControl(@PathVariable Long templateId,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (templateId == null) {
			this.logger.warn("进入授权时传递的模板id为null");
		}
		List<User> ulist = popedomService.listTBUsers();
		List<Role> rlist = popedomService.listTBRoles();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/dwpas/accessControl");
		mv.addObject("ulist", ulist);
		mv.addObject("rlist", rlist);
		mv.addObject("templateId", templateId);
		return mv;
	}

	@RequestMapping("/dwpas/managePopedom")
	public ModelAndView managePopedom(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String tid = request.getParameter("templateId");
		String[] roleIds = request.getParameterValues("roleIds");
		String[] userIds = request.getParameterValues("userIds");
		List<Role> rolelist = roleService.listAllRoles();
		List<User> userlist = userService.listAllUser();
		ModelAndView mv = new ModelAndView();
		try {
			if (roleIds != null && roleIds.toString() != "") {
				for (int i = 0; i < roleIds.length; i++) {
					if (rolelist != null && rolelist.size() > 0) {
						for (Role r : rolelist) {
							if (r.getRoleId().intValue() == Integer
									.parseInt(roleIds[i])) {
								rolelist.remove(r);
								break;
							}
						}
					}
					Role role = new Role();
					role.setRoleId(Integer.parseInt(roleIds[i]));
					role = roleService.getRoleById(role.getRoleId());
					Boolean flag = false;
					// 原先该角色权限包含了这个模板就不进行更新
					if (role.getDwpasRightList() != null
							&& role.getDwpasRightList().length > 0) {
						for (int n = 0; n < role.getDwpasRightList().length; n++) {
							if (role.getDwpasRightList()[n].equals(tid)) {
								flag = true;
							}
						}
					}
					if (!flag) {
						if (StringUtils.notNullAndSpace(role.getDwpasRights())
								&& !"0".equals(role.getDwpasRights())) {
							role.setDwpasRights(role.getDwpasRights() + ","
									+ tid);
						} else {
							role.setDwpasRights(tid);
						}

					}
					popedomService.updateRoleTemplate(role);
					this.insertLog(request, "更改角色" + role.getRoleName() + "权限");
				}
			}
			if (userIds != null && userIds.toString() != "") {
				for (int i = 0; i < userIds.length; i++) {
					if (userlist != null && userlist.size() > 0) {
						for (User u : userlist) {
							if (u.getUserId().intValue() == Integer
									.parseInt(userIds[i])) {
								userlist.remove(u);
								break;
							}
						}
					}
					User user = new User();
					user.setUserId(Integer.parseInt(userIds[i]));
					// user.setDwpasRights(tid);
					user = userService.getUserById(user.getUserId());
					Boolean flag = false;
					if (user.getDwpasRightList() != null
							&& user.getDwpasRightList().length > 0) {
						for (int n = 0; n < user.getDwpasRightList().length; n++) {
							if (user.getDwpasRightList()[n].equals(tid)) {
								flag = true;
							}
						}
					}
					if (flag) {
					} else {
						if (StringUtils.notNullAndSpace(user.getDwpasRights())
								&& !"0".equals(user.getDwpasRights())) {
							user.setDwpasRights(user.getDwpasRights() + ","
									+ tid);
						} else {
							user.setDwpasRights(tid);
						}
					}
					popedomService.updateUserTemplate(user);
					this.insertLog(request, "更改用户" + user.getUsername()
							+ "经纬仪权限");
				}
			}

			if (userlist != null && userlist.size() > 0) {
				for (User u : userlist) {
					this.logger.info("设置当前用户：" + u.getLoginname() + "取消模板ID为："
							+ tid + "权限");
					// user.setDwpasRights(tid);

					// 没打勾的用户权限不为""或者"0"(存在权限)
					Boolean flag = false;
					if (StringUtils.notNullAndSpace(u.getDwpasRights())
							&& !"0".equals(u.getDwpasRights())) {
						List<String> list = new ArrayList<String>(
								Arrays.asList(u.getDwpasRightList()));
						for (int i = 0; i < u.getDwpasRightList().length; i++) {
							if (u.getDwpasRightList()[i].equals(tid)) {
								flag = true;
								list.remove(u.getDwpasRightList()[i]);
							}
						}
						if (list.size() == 0) {
							u.setDwpasRights("0");
						} else {
							String dwpasRights = "0";
							for (int n = 0; n < list.size(); n++) {
								if (n == 0) {
									dwpasRights = list.get(0);
								} else {
									dwpasRights = dwpasRights + ","
											+ list.get(n);
								}
							}
							u.setDwpasRights(dwpasRights);
						}
						if (flag) {
							popedomService.updateUserTemplate(u);
						}

					}
					// 没打勾的用户 若是无权限的，不进行任何操作
					this.insertLog(request, "更改用户" + u.getUsername() + "经纬仪权限");
				}
			}
			if (rolelist != null && rolelist.size() > 0) {
				for (Role r : rolelist) {
					r.setDwpasRights("0");

					// 没打勾的用户权限不为""或者"0"(存在权限)
					Boolean flag = false;
					if (StringUtils.notNullAndSpace(r.getDwpasRights())
							&& !"0".equals(r.getDwpasRights())) {
						List<String> list = new ArrayList<String>(
								Arrays.asList(r.getDwpasRightList()));
						for (int i = 0; i < r.getDwpasRightList().length; i++) {
							if (r.getDwpasRightList()[i].equals(tid)) {
								flag = true;
								list.remove(r.getDwpasRightList()[i]);
							}
						}
						if (list.size() == 0) {
							r.setDwpasRights("0");
						} else {
							String dwpasRights = "0";
							for (int n = 0; n < list.size(); n++) {
								if (n == 0) {
									dwpasRights = list.get(0);
								} else {
									dwpasRights = dwpasRights + ","
											+ list.get(n);
								}
							}
							r.setDwpasRights(dwpasRights);
						}
						if (flag) {
							popedomService.updateRoleTemplate(r);
						}

						popedomService.updateRoleNpTemplate(r);
						this.insertLog(request, "更改角色" + r.getRoleName()
								+ "经纬仪权限");
					}
				}
			}
			mv.addObject("msg", "success");
		} catch (Exception e) {

			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			mv.addObject("msg", this.isFailed);
		}

		mv.setViewName(SUCCESS_ACTION);
		return mv;
	}

	/**
	 * 获取通用指标集
	 * 
	 * @param sign
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/dwpas/getAllComKpiInfo{sign}_{kpiType}")
	public ModelAndView getAllComKpiInfo(@PathVariable String sign,
			@PathVariable String kpiType, HttpServletRequest request,
			HttpServletResponse response, DwpasCComKpiInfo dwpasCComKpiInfo) {
		if (kpiType == null || kpiType == " ") {
			logger.error("日期类型参数kpiType为空!");
		}
		if (kpiType.equals("D")) {
			dwpasCComKpiInfo.setKpiType(new String("1"));
		} else {
			dwpasCComKpiInfo.setKpiType(new String("3"));
		}

		List<DwpasCComKpiInfo> commonkpiCodeList = columnInfoService
				.getAllDwpasCComKpiInfo(dwpasCComKpiInfo);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/dwpas/choicecode");
		mv.addObject("sign", sign);
		mv.addObject("kpiType", kpiType);
		mv.addObject("commonkpiCodeList", commonkpiCodeList);
		return mv;
	}

	/**
	 * 查询所有通用指标
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping("/dwpas/queryByComKpiName")
	public void queryByComKpiName(HttpServletRequest req,
			HttpServletResponse res) {
		this.logger.info("查询指标信息");
		DwpasCComKpiInfo kpiInfo = new DwpasCComKpiInfo();
		String kpiType = req.getParameter("kpiType");
		if (kpiType == null || kpiType == " ") {
			logger.error("日期类型参数kpiType为空!");
		}
		if (kpiType.equals("D")) {
			kpiInfo.setKpiType(new String("1"));
		} else {
			kpiInfo.setKpiType(new String("3"));
		}
		String kpiName = req.getParameter("comKpiName");
		kpiInfo.setComKpiName(kpiName);
		List<DwpasCComKpiInfo> commonkpiCodeList = new ArrayList<DwpasCComKpiInfo>();
		try {
			commonkpiCodeList = columnInfoService
					.getAllDwpasCComKpiInfo(kpiInfo);
			this.sendJsonMsgToClient(commonkpiCodeList, res);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 编辑用户特征
	 * 
	 * @param request
	 * @param response
	 * @param systemMenu
	 * @return
	 */
	@RequestMapping("/dwpas/updateFeature")
	public ModelAndView updateFeature(HttpServletRequest request,
			HttpServletResponse response, DwpasCSystemMenu systemMenu)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(SUCCESS_ACTION);
		if (systemMenu == null) {
			this.logger.warn("编辑用户特征时DwpasCSystemMenu为null");
			mv.addObject("msg", "failed");
			return mv;
		}
		try {
			// 1.修改菜单信息
			menuService.updateMenu(systemMenu);
			if (systemMenu != null) {
				if (systemMenu.getModuleIds() != null
						&& systemMenu.getModuleIds().length > 0) {
					// 2.修改菜单对应的模块的信息
					for (int i = 0; i < systemMenu.getModuleIds().length; i++) {
						DwpasCmoduleInfo module = new DwpasCmoduleInfo();
						module.setModuleId(systemMenu.getModuleIds()[i]);
						module.setModuleName(systemMenu.getModuleNames()[i]);
						module.setRemark(systemMenu.getModuleRemark()[i]);
						module.setIsShow(1);
						moduleService.updateDwpasCModuleInfo(module);
					}
				}
				if (systemMenu.getColumnIds() != null
						&& systemMenu.getColumnIds().length > 0) {
					// 3.修改栏目信息
					for (int j = 0; j < systemMenu.getColumnIds().length; j++) {
						DwpasCColumnInfo column = new DwpasCColumnInfo();
						column.setColumnId(systemMenu.getColumnIds()[j]);
						column.setRemark(systemMenu.getRemarks()[j]);
						column.setColumnKind(systemMenu.getColumnKinds()[j]);
						columnInfoService.updateColumnInfo(column);
						// 4.修改每个栏目对应的关联指标----先删除后插入
						ComKpiService.deleteCommonCode(systemMenu
								.getColumnIds()[j]);
						if (StringUtils.notNullAndSpace(systemMenu
								.getCommCodes()[j])) {
							String[] comkpicode = systemMenu.getCommCodes()[j]
									.split(",");
							for (int k = 0; k < comkpicode.length; k++) {
								DwpasRColumnComKpi comkpi = new DwpasRColumnComKpi();
								comkpi.setColumnId(systemMenu.getColumnIds()[j]);
								comkpi.setComKpiCode(comkpicode[k]);
								ComKpiService.insertCommonCode(comkpi);
							}
						}
					}
				}
			}
			mv.addObject("msg", "success");
			this.insertLog(request, "编辑用户特征");
		} catch (Exception e) {

			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			mv.addObject("msg", this.isFailed);
		}
		return mv;
	}

	/**
	 * 编辑产品健康度
	 * 
	 * @param request
	 * @param response
	 * @param systemMenu
	 * @return
	 */
	@RequestMapping("/dwpas/updateHealth")
	public ModelAndView updateHealth(HttpServletRequest request,
			HttpServletResponse response, DwpasCSystemMenu systemMenu)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(SUCCESS_ACTION);
		if (systemMenu == null) {
			this.logger.warn("编辑产品健康度时DwpasCSystemMenu对象为null");
			mv.addObject("msg", "failed");
			return mv;
		}
		try {
			// 1.修改菜单信息
			menuService.updateMenu(systemMenu);
			if (systemMenu != null) {
				if (systemMenu.getModuleIds() != null
						&& systemMenu.getModuleIds().length > 0) {
					// 2.修改菜单对应的模块的信息
					for (int i = 0; i < systemMenu.getModuleIds().length; i++) {
						DwpasCmoduleInfo module = new DwpasCmoduleInfo();
						module.setModuleId(systemMenu.getModuleIds()[i]);
						module.setModuleName(systemMenu.getModuleNames()[i]);
						module.setRemark(systemMenu.getModuleRemark()[i]);
						module.setIsShow(1);
						moduleService.updateDwpasCModuleInfo(module);
					}
				}
			}
			mv.addObject("msg", "success");
			this.insertLog(request, "编辑产品健康度");
		} catch (Exception e) {

			mv.addObject("msg", this.isFailed);
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
		return mv;
	}

	/**
	 * 编辑首页
	 * 
	 * @param request
	 * @param response
	 * @param systemMenu
	 * @return
	 */
	@RequestMapping("/dwpas/updateIndex")
	public ModelAndView updateIndex(HttpServletRequest request,
			HttpServletResponse response, DwpasCSystemMenu systemMenu)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(SUCCESS_ACTION);
		if (systemMenu == null) {
			this.logger.warn("编辑首页时传递的DwpasCSystemMenu对象为null");
			mv.addObject("msg", "failed");
			return mv;
		}
		try {
			// 1.修改菜单信息
			menuService.updateMenu(systemMenu);
			if (systemMenu != null) {
				if (systemMenu.getModuleIds() != null
						&& systemMenu.getModuleIds().length > 0) {
					// 2.修改菜单对应的模块的信息
					for (int i = 0; i < systemMenu.getModuleIds().length; i++) {
						DwpasCmoduleInfo module = new DwpasCmoduleInfo();
						module.setModuleId(systemMenu.getModuleIds()[i]);
						module.setModuleName(systemMenu.getModuleNames()[i]);
						module.setRemark(systemMenu.getModuleRemark()[i]);
						module.setIsShow(1);
						moduleService.updateDwpasCModuleInfo(module);
					}
				}
				if (systemMenu.getColumnIds() != null
						&& systemMenu.getColumnIds().length > 0) {
					// 3.修改栏目信息
					for (int j = 0; j < systemMenu.getColumnIds().length; j++) {
						DwpasCColumnInfo column = new DwpasCColumnInfo();
						column.setColumnId(systemMenu.getColumnIds()[j]);
						column.setColumnName(systemMenu.getColumnNames()[j]);
						column.setRemark(systemMenu.getRemarks()[j]);
						column.setColumnDisplayName(systemMenu
								.getDisplayNames()[j]);
						columnInfoService.updateColumnInfo(column);
						// 4.修改每个栏目对应的关联指标----先删除后插入
						ComKpiService.deleteCommonCode(systemMenu
								.getColumnIds()[j]);
						if (StringUtils.notNullAndSpace(systemMenu
								.getCommCodes()[j])) {
							String[] comkpicode = systemMenu.getCommCodes()[j]
									.split(",");
							for (int k = 0; k < comkpicode.length; k++) {
								DwpasRColumnComKpi comkpi = new DwpasRColumnComKpi();
								comkpi.setColumnId(systemMenu.getColumnIds()[j]);
								comkpi.setComKpiCode(comkpicode[k]);
								ComKpiService.insertCommonCode(comkpi);
							}
						}
					}
				}
			}
			mv.addObject("msg", "success");
			this.insertLog(request, "编辑首页");
		} catch (Exception e) {

			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			mv.addObject("msg", this.isFailed);
		}
		return mv;
	}

	/**
	 * 编辑产品龙虎榜
	 * 
	 * @param request
	 * @param response
	 * @param systemMenu
	 * @return
	 */
	@RequestMapping("/dwpas/updateTiger")
	public ModelAndView updateTiger(HttpServletRequest request,
			HttpServletResponse response, DwpasCSystemMenu systemMenu)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(SUCCESS_ACTION);
		if (systemMenu == null) {
			this.logger.warn("编辑产品龙虎榜时传递的DwpasCSystemMenu对象为null");
			mv.addObject("msg", "failed");
			return mv;
		}
		try {
			this.logger.info("菜单名:" + systemMenu.getMenuName());
			this.logger.info("菜单URL:" + systemMenu.getMenuUrl());
			// 1.修改菜单信息
			menuService.updateMenuUrl(systemMenu);
			if (systemMenu != null) {
				if (systemMenu.getModuleIds() != null
						&& systemMenu.getModuleIds().length > 0) {
					// 2.修改菜单对应的模块的信息
					for (int i = 0; i < systemMenu.getModuleIds().length; i++) {
						DwpasCmoduleInfo module = new DwpasCmoduleInfo();
						module.setModuleId(systemMenu.getModuleIds()[i]);
						module.setModuleName(systemMenu.getModuleNames()[i]);
						module.setRemark(systemMenu.getModuleRemark()[i]);
						module.setIsShow(1);
						moduleService.updateDwpasCModuleInfo(module);
					}
				}
			}
			mv.addObject("msg", "success");
			this.insertLog(request, "编辑产品龙虎榜");
		} catch (Exception e) {

			mv.addObject("msg", this.isFailed);
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
		return mv;
	}

	/**
	 * 编辑产品指标分析
	 * 
	 * @param request
	 * @param response
	 * @param systemMenu
	 * @return
	 */
	@RequestMapping("/dwpas/updateAnalyse")
	public ModelAndView updateAnalyse(HttpServletRequest request,
			HttpServletResponse response, DwpasCSystemMenu systemMenu)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(SUCCESS_ACTION);
		if (systemMenu == null) {
			this.logger.warn("编辑产品指标分析时传递的DwpasCSystemMenu对象为null");
			mv.addObject("msg", "failed");
			return mv;
		}
		try {
			// 1.修改菜单信息
			menuService.updateMenu(systemMenu);
			if (systemMenu != null) {
				if (systemMenu.getModuleIds() != null
						&& systemMenu.getModuleIds().length > 0) {
					// 2.修改菜单对应的模块的信息
					for (int i = 0; i < systemMenu.getModuleIds().length; i++) {
						DwpasCmoduleInfo module = new DwpasCmoduleInfo();
						module.setModuleId(systemMenu.getModuleIds()[i]);
						module.setModuleName(systemMenu.getModuleNames()[i]);
						module.setRemark(systemMenu.getModuleRemark()[i]);
						module.setIsShow(1);
						moduleService.updateDwpasCModuleInfo(module);
					}
				}
			}
			mv.addObject("msg", "success");
			this.insertLog(request, "编辑产品指标分析");
		} catch (Exception e) {

			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			mv.addObject("msg", this.isFailed);
		}
		return mv;
	}

	/**
	 * 编辑产品发展（日类型）
	 * 
	 * @param request
	 * @param response
	 * @param systemMenu
	 * @return
	 */
	@RequestMapping("/dwpas/updateDevelop")
	public ModelAndView updateDevelop(HttpServletRequest request,
			HttpServletResponse response, DwpasCSystemMenu systemMenu)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(SUCCESS_ACTION);
		if (systemMenu == null) {
			this.logger.warn("编辑产品发展时传递的DwpasCSystemMenu对象为null");
			mv.addObject("msg", "failed");
			return mv;
		}
		try {
			// 1.修改菜单信息
			menuService.updateMenu(systemMenu);
			if (systemMenu != null) {
				if (systemMenu.getModuleIds() != null
						&& systemMenu.getModuleIds().length > 0) {
					// 2.修改菜单对应的模块的信息
					for (int i = 0; i < systemMenu.getModuleIds().length; i++) {
						DwpasCmoduleInfo module = new DwpasCmoduleInfo();
						module.setModuleId(systemMenu.getModuleIds()[i]);
						module.setModuleName(systemMenu.getModuleNames()[i]);
						module.setRemark(systemMenu.getModuleRemark()[i]);
						module.setIsShow(1);
						moduleService.updateDwpasCModuleInfo(module);
					}
				}
				if (systemMenu.getColumnIds() != null
						&& systemMenu.getColumnIds().length > 0) {
					// 3.修改栏目信息
					for (int j = 0; j < systemMenu.getColumnIds().length; j++) {
						DwpasCColumnInfo column = new DwpasCColumnInfo();
						column.setColumnId(systemMenu.getColumnIds()[j]);
						column.setRemark(systemMenu.getRemarks()[j]);
						column.setColumnDisplayName(systemMenu
								.getDisplayNames()[j]);
						column.setColumnOrder(j);
						columnInfoService.updateColumnInfo(column);
						// 4.修改每个栏目对应的关联指标----先删除后插入
						ComKpiService.deleteCommonCode(systemMenu
								.getColumnIds()[j]);
						if (StringUtils.notNullAndSpace(systemMenu
								.getCommCodes()[j])) {
							String[] comkpicode = systemMenu.getCommCodes()[j]
									.split(",");
							for (int k = 0; k < comkpicode.length; k++) {
								DwpasRColumnComKpi comkpi = new DwpasRColumnComKpi();
								comkpi.setColumnId(systemMenu.getColumnIds()[j]);
								comkpi.setComKpiCode(comkpicode[k]);
								ComKpiService.insertCommonCode(comkpi);
							}
						}
					}
				}
			}
			mv.addObject("msg", "success");
			this.insertLog(request, "编辑产品发展");
		} catch (Exception e) {

			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			mv.addObject("msg", this.isFailed);
		}
		return mv;
	}

	/**
	 * 编辑场景交叉
	 * 
	 * @param request
	 * @param response
	 * @param systemMenu
	 * @return
	 */
	@RequestMapping("/dwpas/updateChangjing")
	public ModelAndView updateChangjing(HttpServletRequest request,
			HttpServletResponse response, DwpasCSystemMenu systemMenu)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(SUCCESS_ACTION);
		if (systemMenu == null) {
			this.logger.warn("编辑场景交叉时传递的DwpasCSystemMenu对象为null");
			mv.addObject("msg", "failed");
			return mv;
		}
		try {
			// 1.修改菜单信息
			if (systemMenu.getMenuName().equals("产品交叉")) {
				systemMenu.setMenuUrl("/CrossUser/doGet.html?kpiType=3");
			} else if (systemMenu.getMenuName().equals("渠道交叉")) {
				systemMenu.setMenuUrl("/CrossUser/doGet.html?kpiType=3");
			} else if (systemMenu.getMenuName().equals("用户行为")) {
				systemMenu.setMenuUrl("/UserAction/doGet.html?1=1");
			}
			menuService.updateMenuUrl(systemMenu);
			if (systemMenu != null) {
				if (systemMenu.getModuleIds() != null
						&& systemMenu.getModuleIds().length > 0) {
					// 2.修改菜单对应的模块的信息
					for (int i = 0; i < systemMenu.getModuleIds().length; i++) {
						DwpasCmoduleInfo module = new DwpasCmoduleInfo();
						module.setModuleId(systemMenu.getModuleIds()[i]);
						module.setModuleName(systemMenu.getModuleNames()[i]);
						module.setRemark(systemMenu.getModuleRemark()[i]);
						module.setIsShow(1);
						moduleService.updateDwpasCModuleInfo(module);
					}
				}
			}
			mv.addObject("msg", "success");
			this.insertLog(request, "编辑场景交叉");
		} catch (Exception e) {

			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			mv.addObject("msg", this.isFailed);
		}
		return mv;
	}

	/**
	 * 编辑用户体验
	 * 
	 * @param request
	 * @param response
	 * @param systemMenu
	 * @return
	 */
	@RequestMapping("/dwpas/updateExperience")
	public ModelAndView updateExperience(HttpServletRequest request,
			HttpServletResponse response, DwpasCSystemMenu systemMenu)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(SUCCESS_ACTION);
		if (systemMenu == null) {
			this.logger.warn("编辑用户体验时传递的DwpasCSystemMenu对象为null");
			mv.addObject("msg", "failed");
			return mv;
		}
		try {
			// 1.修改菜单信息
			menuService.updateMenuUrl(systemMenu);
			if (systemMenu != null) {
				if (systemMenu.getModuleIds() != null
						&& systemMenu.getModuleIds().length > 0) {
					// 2.修改菜单对应的模块的信息
					for (int i = 0; i < systemMenu.getModuleIds().length; i++) {
						DwpasCmoduleInfo module = new DwpasCmoduleInfo();
						module.setModuleId(systemMenu.getModuleIds()[i]);
						module.setModuleName(systemMenu.getModuleNames()[i]);
						module.setRemark(systemMenu.getModuleRemark()[i]);
						module.setIsShow(Integer.valueOf(systemMenu
								.getModuleIsShows()[i]));
						moduleService.updateDwpasCModuleInfo(module);
					}
				}
				if (systemMenu.getColumnIds() != null
						&& systemMenu.getColumnIds().length > 0) {
					// 3.修改栏目信息
					for (int j = 0; j < systemMenu.getColumnIds().length; j++) {
						DwpasCColumnInfo column = new DwpasCColumnInfo();
						column.setColumnId(systemMenu.getColumnIds()[j]);
						column.setColumnName(systemMenu.getColumnNames()[j]);
						column.setRemark(systemMenu.getRemarks()[j]);
						column.setColumnDisplayName(systemMenu
								.getDisplayNames()[j]);
						column.setColumnKind(systemMenu.getColumnKinds()[j]);
						columnInfoService.updateColumnInfo(column);
						// 4.修改每个栏目对应的关联指标----先删除后插入
						ComKpiService.deleteCommonCode(systemMenu
								.getColumnIds()[j]);
						if (StringUtils.notNullAndSpace(systemMenu
								.getCommCodes()[j])) {
							String[] comkpicode = systemMenu.getCommCodes()[j]
									.split(",");
							for (int k = 0; k < comkpicode.length; k++) {
								DwpasRColumnComKpi comkpi = new DwpasRColumnComKpi();
								comkpi.setColumnId(systemMenu.getColumnIds()[j]);
								comkpi.setComKpiCode(comkpicode[k]);
								ComKpiService.insertCommonCode(comkpi);
							}
						}
					}
				}

			}
			mv.addObject("msg", "success");
			this.insertLog(request, "编辑用户体验");
		} catch (Exception e) {

			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			mv.addObject("msg", this.isFailed);
		}
		return mv;
	}

	/**
	 * 编辑用户存留
	 * 
	 * @param request
	 * @param response
	 * @param systemMenu
	 * @return
	 */
	@RequestMapping("/dwpas/updateCunliu")
	public ModelAndView updateCunliu(HttpServletRequest request,
			HttpServletResponse response, DwpasCSystemMenu systemMenu)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(SUCCESS_ACTION);
		if (systemMenu == null) {
			this.logger.warn("编辑用户存留时传递的DwpasCSystemMenu为null");
			mv.addObject("msg", "failed");
			return mv;
		}
		try {

			// 1.修改菜单信息
			menuService.updateMenu(systemMenu);
			if (systemMenu != null) {
				if (systemMenu.getModuleIds() != null
						&& systemMenu.getModuleIds().length > 0) {
					// 2.修改菜单对应的模块的信息
					for (int i = 0; i < systemMenu.getModuleIds().length; i++) {
						DwpasCmoduleInfo module = new DwpasCmoduleInfo();
						module.setModuleId(systemMenu.getModuleIds()[i]);
						module.setModuleName(systemMenu.getModuleNames()[i]);
						module.setRemark(systemMenu.getModuleRemark()[i]);
						module.setIsShow(systemMenu.getIsShow());
						module.setIsShow(1);
						moduleService.updateDwpasCModuleInfo(module);
					}
				}
				if (systemMenu.getColumnIds() != null
						&& systemMenu.getColumnIds().length > 0) {
					// 3.修改栏目信息
					for (int j = 0; j < systemMenu.getColumnIds().length; j++) {
						DwpasCColumnInfo column = new DwpasCColumnInfo();
						column.setColumnId(systemMenu.getColumnIds()[j]);
						column.setRemark(systemMenu.getRemarks()[j]);
						columnInfoService.updateColumnInfo(column);
						// 4.修改每个栏目对应的关联指标----先删除后插入
						ComKpiService.deleteCommonCode(systemMenu
								.getColumnIds()[j]);
						if (StringUtils.notNullAndSpace(systemMenu
								.getCommCodes()[j])) {
							String[] comkpicode = systemMenu.getCommCodes()[j]
									.split(",");
							for (int k = 0; k < comkpicode.length; k++) {
								DwpasRColumnComKpi comkpi = new DwpasRColumnComKpi();
								comkpi.setColumnId(systemMenu.getColumnIds()[j]);
								comkpi.setComKpiCode(comkpicode[k]);
								ComKpiService.insertCommonCode(comkpi);
							}
						}
					}
				}
			}
			mv.addObject("msg", "success");
			this.insertLog(request, "编辑用户存留");

		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			mv.addObject("msg", this.isFailed);
		}
		return mv;
	}

	/**
	 * 编辑用户声音
	 * 
	 * @param request
	 * @param response
	 * @param systemMenu
	 * @return
	 */
	@RequestMapping("/dwpas/updateVoice")
	public ModelAndView updateVoice(HttpServletRequest request,
			HttpServletResponse response, DwpasCSystemMenu systemMenu)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(SUCCESS_ACTION);
		if (systemMenu == null) {
			this.logger.warn("编辑用户声音时传递的DwpasCSystemMenu对象为null");
			mv.addObject("msg", "failed");
			return mv;
		}
		try {
			// 1.修改菜单信息
			menuService.updateMenu(systemMenu);
			if (systemMenu != null) {
				if (systemMenu.getModuleIds() != null
						&& systemMenu.getModuleIds().length > 0) {
					// 2.修改菜单对应的模块的信息
					for (int i = 0; i < systemMenu.getModuleIds().length; i++) {
						DwpasCmoduleInfo module = new DwpasCmoduleInfo();
						module.setModuleId(systemMenu.getModuleIds()[i]);
						module.setModuleName(systemMenu.getModuleNames()[i]);
						module.setRemark(systemMenu.getModuleRemark()[i]);
						module.setIsShow(1);
						moduleService.updateDwpasCModuleInfo(module);
					}
				}
				if (systemMenu.getColumnIds() != null
						&& systemMenu.getColumnIds().length > 0) {
					// 3.修改栏目信息
					for (int j = 0; j < systemMenu.getColumnIds().length; j++) {
						DwpasCColumnInfo column = new DwpasCColumnInfo();
						column.setColumnId(systemMenu.getColumnIds()[j]);
						column.setRemark(systemMenu.getRemarks()[j]);
						columnInfoService.updateColumnInfo(column);
						// 4.修改每个栏目对应的关联指标----先删除后插入
						ComKpiService.deleteCommonCode(systemMenu
								.getColumnIds()[j]);
						if (StringUtils.notNullAndSpace(systemMenu
								.getCommCodes()[j])) {
							String[] comkpicode = systemMenu.getCommCodes()[j]
									.split(",");
							for (int k = 0; k < comkpicode.length; k++) {
								DwpasRColumnComKpi comkpi = new DwpasRColumnComKpi();
								comkpi.setColumnId(systemMenu.getColumnIds()[j]);
								comkpi.setComKpiCode(comkpicode[k]);
								comkpi.setUserType(systemMenu.getUserTypes()[j]);
								ComKpiService.insertCommonCode(comkpi);
							}
						}
					}
				}
			}
			mv.addObject("msg", "success");
			this.insertLog(request, "编辑用户声音");
		} catch (Exception e) {

			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			mv.addObject("msg", this.isFailed);
		}
		return mv;
	}

	/**
	 * 进入通用编辑页面
	 * 
	 * @param menuId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/dwpas/editMudole{menuId}_{dateType}")
	public ModelAndView editMudole(@PathVariable String menuId,
			@PathVariable String dateType, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (org.apache.commons.lang.StringUtils.isBlank(menuId)) {
			this.logger.warn("进入通用编辑页面时传递的menuId为null");
		}
		List<DwpasCmoduleInfo> modulelist = moduleService
				.getDwpasCmoduleInfoByMenuId(menuId, dateType);
		if (modulelist != null && modulelist.size() > 0) {
			for (DwpasCmoduleInfo module : modulelist) {
				List<DwpasCColumnInfo> columnlist = columnInfoService
						.getCColumnInfoByModuleId(module.getModuleId());
				module.setColumnlist(columnlist);
			}
		}
		DwpasCSystemMenu menu = menuService.getByMenuId(menuId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("modulelist", modulelist);
		mv.addObject("menu", menu);
		mv.addObject("dateType", dateType);
		mv.addObject(
				"templateName",
				new String(request.getParameter("templateName").getBytes(
						"ISO-8859-1"), "UTF-8"));
		mv.addObject("menuName", new String(request.getParameter("menuName")
				.getBytes("ISO-8859-1"), "UTF-8"));
		String url = decideMenu(menu.getMenuCode());
		mv.setViewName(url);
		this.insertLog(request, "编辑通用页面");
		return mv;
	}

	@RequestMapping(value = "/dwpas/editDesMonth{menuId}_{dateType}")
	public ModelAndView editDesMonth(@PathVariable String menuId,
			@PathVariable String dateType, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (org.apache.commons.lang.StringUtils.isBlank(menuId)) {
			this.logger.warn("进入通用编辑页面时传递的menuId为null");
		}
		List<DwpasCmoduleInfo> modulelist = moduleService
				.getDwpasCmoduleInfoByMenuId(menuId, dateType);
		Map<String, String> comcodeMap = new HashMap<String, String>();
		if (modulelist != null && modulelist.size() > 0) {
			for (DwpasCmoduleInfo module : modulelist) {
				List<DwpasCColumnInfo> columnlist = columnInfoService
						.getAllColumnInfoAndComkpilist(module.getModuleId());
				if (columnlist != null && columnlist.size() > 0) {
					for (DwpasCColumnInfo column : columnlist) {
						comcodeMap = new HashMap<String, String>();
						if (column.getKpiCodelist() != null
								&& column.getKpiCodelist().size() > 0) {
							for (DwpasRColumnComKpi kpi : column
									.getKpiCodelist()) {
								comcodeMap.put("com_code_" + kpi.getValue1(),
										kpi.getComKpiCode());
								comcodeMap.put("com_name_" + kpi.getValue1(),
										kpi.getValue3());
							}
						}
						column.setComcodeMap(comcodeMap);
					}
				}
				module.setColumnlist(columnlist);
			}
		}
		DwpasCSystemMenu menu = menuService.getByMenuId(menuId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("modulelist", modulelist);
		mv.addObject("menu", menu);
		mv.addObject("dateType", dateType);
		mv.addObject(
				"templateName",
				new String(request.getParameter("templateName").getBytes(
						"ISO-8859-1"), "UTF-8"));
		mv.addObject("menuName", new String(request.getParameter("menuName")
				.getBytes("ISO-8859-1"), "UTF-8"));
		mv.setViewName("/admin/dwpas/eidtdevelopMonth");
		this.insertLog(request, "编辑通用--产品发展月指标模板页面");
		return mv;
	}

	private String decideMenu(String menuCode) {
		String url = "";
		if (menuCode.equals("04")) {
			url = "/admin/dwpas/editanalys";
		} else if (menuCode.equals("0306")) {
			url = "/admin/dwpas/editchangjing";
		} else if (menuCode.equals("01")) {
			url = "/admin/dwpas/editindex";
		} else if (menuCode.equals("02")) {
			url = "/admin/dwpas/edittiger";
		} else if (menuCode.equals("03")) {
			url = "/admin/dwpas/eidthealth";
		} else if (menuCode.equals("0301")) {
			url = "/admin/dwpas/eidtdevelopday";
		} else if (menuCode.equals("0302")) {
			url = "/admin/dwpas/editcunliu";
		} else if (menuCode.equals("0305")) {
			url = "/admin/dwpas/editfeature";
		} else if (menuCode.equals("0303")) {
			url = "/admin/dwpas/edittiyan";
		} else if (menuCode.equals("0304")) {
			url = "/admin/dwpas/editvoice";
		}
		return url;
	}

	/**
	 * 编辑产品发展（月类型）
	 * 
	 * @param request
	 * @param response
	 * @param systemMenu
	 * @return
	 */
	@RequestMapping("/dwpas/updateDevelopMonth")
	public ModelAndView updateDevelopMonth(HttpServletRequest request,
			HttpServletResponse response, DwpasCSystemMenu systemMenu)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("msg", "success");
		mv.setViewName(SUCCESS_ACTION);
		if (systemMenu == null) {
			this.logger.warn("编辑产品发展月类型时传递的DwpasCSystemMenu对象为null");
			mv.addObject("msg", "failed");
			return mv;
		}
		try {
			menuService.updateDevelopMonth(systemMenu);
			mv.addObject("msg", "success");
			this.insertLog(request, "编辑产品发展");
		} catch (Exception e) {

			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			mv.addObject("msg", "failed");
		}

		return mv;
	}

	/**
	 * 配置雷达图
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/dwpas/editRadarSetting")
	public ModelAndView editRadarSetting(HttpServletRequest request,
			HttpServletResponse response) {
		String moduleId = request.getParameter("moduleId");
		//
		Map<String, ComKpiInfo> showCommKpiMap = new HashMap<String, ComKpiInfo>();
		// 已关联
		List<ComKpiInfo> isRelKpiList = comKpiInfoService
				.listCommKpiInfo(moduleId);
		if (isRelKpiList != null) {
			for (ComKpiInfo comKpiInfo : isRelKpiList) {
				showCommKpiMap.put(comKpiInfo.getComKpiCode(), comKpiInfo);
			}
		}
		// 所有
		List<ComKpiInfo> allCommKpiList = comKpiInfoService.searchCommonKPI();
		// 未关联
		List<ComKpiInfo> noRelKpiList = new ArrayList<ComKpiInfo>();
		if (allCommKpiList != null) {
			for (ComKpiInfo comKpiInfo : allCommKpiList) {
				if (showCommKpiMap.containsKey(comKpiInfo.getComKpiCode())) {
					continue;
				}
				noRelKpiList.add(comKpiInfo);
			}
		}
		ModelAndView mv = new ModelAndView("/admin/dwpas/radarComkpiinfo");
		mv.addObject("isRelKpiList", isRelKpiList);
		mv.addObject("noRelKpiList", noRelKpiList);
		mv.addObject("moduleId", moduleId);
		return mv;
	}

	/**
	 * 保存雷达图配置
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/dwpas/saveRadarSetting")
	public void saveRadarSetting(HttpServletRequest request,
			HttpServletResponse response) {
		String moduleId = request.getParameter("moduleId");
		try {
			String relComKpi = request.getParameter("relComKpi");
			this.logger.info("关联的指标：" + relComKpi);
			DwpasCColumnInfo columnInfo = new DwpasCColumnInfo();
			columnInfo.setColumnName("产品健康度");
			columnInfo.setColumnCode("RADAR_PRODUCT_HEALTH");
			columnInfo.setModuleId(moduleId);
			columnInfo.setColumnDisplayName("产品健康度");
			comKpiInfoService.saveCommKpiOfModule(columnInfo,
					Arrays.asList(relComKpi.split(",")), moduleId);
			this.insertLog(request, "编辑雷达图配置");
			this.sendMsgToClient(this.isSuccess, response);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			this.sendMsgToClient(this.isFailed, response);
		}
	}
}
