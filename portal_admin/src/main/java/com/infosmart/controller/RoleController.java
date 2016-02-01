package com.infosmart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.po.DwpasCTemplateInfo;
import com.infosmart.po.Menu;
import com.infosmart.po.Role;
import com.infosmart.po.dwmis.DwmisTemplateInfo;
import com.infosmart.po.report.ReportDesign;
import com.infosmart.service.DwpasCTemplateInfoService;
import com.infosmart.service.MenuService;
import com.infosmart.service.RoleService;
import com.infosmart.service.dwmis.DwmisTemplateInfoService;
import com.infosmart.service.report.ReportService;
import com.infosmart.util.RightsHelper;
import com.infosmart.util.Tools;

@Controller
@RequestMapping(value = "/role")
public class RoleController extends BaseController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private DwpasCTemplateInfoService dwpasCTemplateInfoService;
	
	@Autowired
	private DwmisTemplateInfoService dwmisTemplateInfoService;
	private final String SUCCESS_ACTION = "/common/save_result";

	/**
	 * 显示角色列表
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping
	public String list(Map<String, Object> map, HttpServletRequest request) {
		List<Role> roleList = roleService.listAllRoles();
		map.put("roleList", roleList);
		this.insertLog(request, "查看角色权限");
		return "role/roles";
	}

	/**
	 * 保存角色信息
	 * 
	 * @param out
	 * @param role
	 */
	@RequestMapping(value = "/save")
	public void save(HttpServletRequest request, HttpServletResponse response,
			Role role, PrintWriter out) {
		response.setContentType("text/html;charset=UTF-8");
		if (role == null) {
			this.logger.warn("保存角色信息时传递的Role对象为null");
		}
		boolean flag = false;
		if (role != null) {
			try {
				if (role.getRoleId() != null && role.getRoleId().intValue() > 0) {
					flag = roleService.updateRoleBaseInfo(role);
					this.insertLog(request, "修改角色:" + role.getRoleName()
							+ "相关信息");
				} else {
					flag = roleService.insertRole(role);
					this.insertLog(request, "新增新角色:" + role.getRoleName());
				}

			} catch (Exception e) {
				e.printStackTrace();
				this.logger.error("" + e.getMessage(), e);
				flag = false;

			}

		}
		if (flag) {
			out.write("success");
		} else {
			out.write("failed");
		}
		out.flush();
		out.close();

	}

	/**
	 * 请求角色授权页面
	 * 
	 * @param roleId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/auth{roleId}")
	public String auth(@PathVariable int roleId, Model model) {
		List<Menu> menuList = menuService.listAllMenu();
		Role role = roleService.getRoleById(roleId);
		String roleRights = role.getRights();
		if (Tools.notEmpty(roleRights) && menuList != null
				&& menuList.size() > 0) {
			for (Menu menu : menuList) {
				menu.setHasMenu(RightsHelper.testRights(roleRights,
						menu.getMenuId()));
				if (menu.isHasMenu()) {
					List<Menu> subMenuList = menu.getSubMenu();
					for (Menu sub : subMenuList) {
						sub.setHasMenu(RightsHelper.testRights(roleRights,
								sub.getMenuId()));
					}
				}
			}
		}
		JSONArray arr = JSONArray.fromObject(menuList);
		String json = arr.toString();
		json = json.replaceAll("menuId", "id").replaceAll("menuName", "name")
				.replaceAll("subMenu", "nodes")
				.replaceAll("hasMenu", "checked");
		model.addAttribute("zTreeNodes", json);
		model.addAttribute("roleId", roleId);
		return "user/authorization";
	}

	/**
	 * 保存角色权限
	 * 
	 * @param roleId
	 * @param menuIds
	 * @param out
	 * @throws IOException
	 */
	@RequestMapping(value = "/authsave")
	public void saveAuth(@RequestParam int roleId,
			@RequestParam String menuIds, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Role role = roleService.getRoleById(roleId);
		role.setRights(rights.toString());
		try {
			roleService.updateRoleRights(role);
			out.write("success");
			this.insertLog(request, "编辑角色:" + role.getRoleName() + "对后台的权限设置");
		} catch (Exception e) {

			out.write("failed");
		}
		out.close();

	}

	/**
	 * 请求经纬仪权限页面
	 * 
	 * @param roleId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/authDwpas{roleId}")
	public ModelAndView authDwpas(@PathVariable int roleId, Model model) {
		List<DwpasCTemplateInfo> dwpasCTemplateInfo = dwpasCTemplateInfoService
				.listAllTemplateInfo();
		Role role = roleService.getRoleById(roleId);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/user/authorizationDwpas1");
		mv.addObject("dwpasCTemplateInfo", dwpasCTemplateInfo);
		mv.addObject("roleId", roleId);
		mv.addObject("role", role);
		return mv;
	}

	/**
	 * 保存经纬仪权限页面
	 * 
	 * @param roleId
	 * @param menuIds
	 * @param out
	 */
	@RequestMapping(value = "/saveAuthDwpas", method = RequestMethod.POST)
	public ModelAndView saveAuthDwpas(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		Integer roleId = Integer.parseInt(request.getParameter("roleId"));
		String[] templateIds = request.getParameterValues("templateIds");
		Role role = new Role();
		role = roleService.getRoleById(roleId);
		role.setRoleId(roleId);	
		
		String dwpasRights = "0";
		
		if(templateIds != null && templateIds.length>0){
			for(int i = 0; i < templateIds.length; i ++) {
				if(i==0){
					dwpasRights = templateIds[i];
				}else {
					dwpasRights = dwpasRights + "," + templateIds[i];
				}
			}
		}
		role.setDwpasRights(dwpasRights);
		try {
			roleService.updateDwpasRights(role);
			mv.addObject("msg", "success");
			this.insertLog(request, "编辑角色:" + role.getRoleName()
					+ "对前台经纬仪的权限设置");
		} catch (Exception e) {
			this.logger.warn("对前台经纬仪的权限设置失败！");
			mv.addObject("msg", "failed");

		}
		
		
		mv.setViewName(SUCCESS_ACTION);

		return mv;
	}
	
	
	/**
	 * 请求瞭望塔权限页面
	 * 
	 * @param roleId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/authDwmis{roleId}")
	public ModelAndView authDwmis(@PathVariable int roleId, Model model) {
		List<DwmisTemplateInfo> dwmisTemplateInfoList = dwmisTemplateInfoService
				.listAllTemplateInfo();
		Role role = roleService.getRoleById(roleId);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/user/authorizationDwmis2");
		mv.addObject("dwmisTemplateInfoList", dwmisTemplateInfoList);
		mv.addObject("roleId", roleId);
		mv.addObject("role", role);
		return mv;
	}

	/**
	 * 保存瞭望塔权限页面
	 * 
	 * @param roleId
	 * @param menuIds
	 * @param out
	 */
	@RequestMapping(value = "/saveAuthDwmis", method = RequestMethod.POST)
	public ModelAndView saveAuthDwmis(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String roleIdString = request.getParameter("roleId");
		if(com.infosmart.util.StringUtils.notNullAndSpace(roleIdString)){
			Integer roleId = Integer.parseInt(roleIdString);
			String[] templateIds = request.getParameterValues("templateIds");
			Role role = new Role();
			role = roleService.getRoleById(roleId);
			if (templateIds != null && templateIds.toString() != "") {
				for (int i = 0; i < templateIds.length; i++) {
					role.setDwmisRights(templateIds[i]);
					try {
						roleService.updateDwmisRights(role);
						mv.addObject("msg", "success");
						this.insertLog(request, "编辑角色:" + role.getRoleName()
								+ "对前台瞭望塔的权限设置");
					} catch (Exception e) {

						mv.addObject("msg", "failed");
					}

				}
			} else {
				try {
					role.setDwpasRights("0");
					roleService.updateDwmisRights(role);
					mv.addObject("msg", "success");
					this.insertLog(request, "编辑角色:" + role.getRoleName()
							+ "对前台瞭望塔的权限设置");
				} catch (Exception e) {

					mv.addObject("msg", "failed");
				}

			}
			mv.setViewName(SUCCESS_ACTION);

			return mv;
		}else{
			this.logger.error("保存瞭望塔权限失败roleId为空");
			mv.addObject("msg", "failed");
			return mv;
		}
		
	}
	
	

	/**
	 * 递归选中报表权限
	 * 
	 * @param reportList
	 * @param reportRights
	 */
	private void reportChecked(List<ReportDesign> reportList,
			String reportRights) {
		if (Tools.notEmpty(reportRights) &&reportList != null && reportList.size() > 0) {
			for (ReportDesign report : reportList) {
				report.setHasReport(RightsHelper.testRights(reportRights,
						report.getReportId()));
				if (null != report.getSubReport()) {
					reportChecked(report.getSubReport(), reportRights);
				}
			}
		}
	}

	/**
	 * 请求报表权限页面
	 * 
	 * @param roleId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/authReport{roleId}")
	public String authReport(@PathVariable int roleId, Model model) {
		List<ReportDesign> reportList = reportService.listAllReport();
		Role role = roleService.getRoleById(roleId);
		String reportRights = role.getReportRights();
		if (Tools.notEmpty(reportRights) && reportList != null
				&& reportList.size() > 0) {
			reportChecked(reportList, reportRights);
		}

		JSONArray arr = JSONArray.fromObject(reportList);
		String json = arr.toString();
		json = json.replaceAll("reportId", "id")
				.replaceAll("reportName", "name")
				.replaceAll("subReport", "nodes")
				.replaceAll("hasReport", "checked");
		model.addAttribute("zTreeNodes", json);
		model.addAttribute("roleId", roleId);
		return "user/authorizationReport";
	}

	/**
	 * 保存报表权限页面
	 * 
	 * @param roleId
	 * @param menuIds
	 * @param out
	 */
	@RequestMapping(value = "/authsaveReport")
	public void saveAuthReport(HttpServletRequest request,
			HttpServletResponse response, PrintWriter out) {
		response.setContentType("text/html;charset=UTF-8");
		String reportIds = request.getParameter("reportIds");
		String roleId = request.getParameter("roleId");
		if (StringUtils.isBlank(reportIds)) {
			this.logger.warn("保存报表权限页面时传递的reportIds为null");
		}
		BigInteger reportRights = RightsHelper.sumRights(Tools
				.str2StrArray(reportIds));
		Role role = roleService.getRoleById(Integer.valueOf(roleId));
		role.setReportRights(reportRights.toString());
		try {
			roleService.updateReportRights(role);
			this.insertLog(request, "编辑角色:" + role.getRoleName() + "对前台报表权限设置");
			out.write("success");
		} catch (Exception e) {

			e.printStackTrace();
			this.logger.error("报表权限编辑保存失败:"+e.getMessage(), e);
			out.write("failed");
		}

		out.close();
	}

	/**
	 * 删除某个角色
	 * 
	 * @param userId
	 * @param out
	 */
	@RequestMapping(value = "/delete{roleId}")
	public void deleteUser(@PathVariable int roleId, PrintWriter out,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		try {
			roleService.deleteRoleAndUsersById(roleId);
			this.insertLog(request, "删除角色");
			out.write("success");
		} catch (Exception e) {

			out.write("failed");
			this.insertLog(request, "删除角色失败");
		}
		out.close();

	}
}
