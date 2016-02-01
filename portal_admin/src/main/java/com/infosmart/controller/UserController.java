package com.infosmart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

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
import com.infosmart.po.User;
import com.infosmart.po.dwmis.DwmisTemplateInfo;
import com.infosmart.po.report.ReportDesign;
import com.infosmart.po.report.SelfApply;
import com.infosmart.service.DwpasCTemplateInfoService;
import com.infosmart.service.MenuService;
import com.infosmart.service.RoleService;
import com.infosmart.service.UserService;
import com.infosmart.service.dwmis.DwmisTemplateInfoService;
import com.infosmart.service.report.ReportService;
import com.infosmart.service.report.SelfApplyService;
import com.infosmart.util.MD5;
import com.infosmart.util.RightsHelper;
import com.infosmart.util.Tools;
import com.infosmart.view.UserExcelView;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
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
	
	@Autowired
	private SelfApplyService selfApplyService;
	private final String SUCCESS_ACTION = "/common/save_result";

	/**
	 * 显示用户列表
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping
	public ModelAndView list(HttpServletRequest request, User user) {
		if (user.getLastLoginEnd() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(user.getLastLoginEnd());
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			user.setLastLoginEnd(cal.getTime());
			// Date d = user.getLastLoginEnd();
			// d.setHours(23);
			// d.setMinutes(59);
			// d.setSeconds(59);
			// user.setLastLoginEnd(d);
		}
		String loginname = user.getLoginname();
		if (!"".equals(loginname) && loginname != null) {
			user.setLoginname(loginname.trim());
		}
		List<User> userList = userService.listPageUser(user);
		List<Role> roleList = roleService.listAllRoles();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/users");
		mv.addObject("userList", userList);
		mv.addObject("roleList", roleList);
		mv.addObject("user", user);
		this.insertLog(request, "查看用户列表");
		return mv;
	}

	/**
	 * 请求新增用户页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String toAdd(Model model) {
		List<Role> roleList = roleService.listAllRoles();
		model.addAttribute("roleList", roleList);
		return "user/user_info";
	}

	/**
	 * 保存用户信息
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveUser(HttpServletRequest request, User user) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("common/save_result");
		if (user == null) {
			this.logger.warn("保存用户信息时传递的User对象为null");
			mv.addObject("msg", isFailed);
			return mv;
		}
		MD5 md = new MD5();
		this.logger.info("password:" + user.getPassword());
		String passWord = "";
		if (user.getPassword() != null && user.getPassword() != "") {
			passWord = md.getkeyBeanofStr(user.getPassword());
		}
		user.setPassword(passWord);

		if (user.getUserId() == null || user.getUserId().intValue() == 0) {
			if (userService.insertUser(user) == false) {
				mv.addObject("msg", "failed");
			} else {
				mv.addObject("msg", "success");
				this.insertLog(request, "添加用户" + user.getLoginname());
			}
		} else {
			try {
				userService.updateUserBaseInfo(user);
				this.insertLog(request, "修改用户" + user.getLoginname() + "信息");
				mv.addObject("msg", "success");
			} catch (Exception e) {

				e.printStackTrace();
				this.logger.error("编辑用户保存失败：" + e.getMessage(), e);
				mv.addObject("msg", "failed");

			}

		}

		return mv;
	}

	/**
	 * 请求编辑用户页面
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/edit{userId}")
	public ModelAndView toEdit(@PathVariable int userId) {
		ModelAndView mv = new ModelAndView();
		User user = userService.getUserById(userId);
		List<Role> roleList = roleService.listAllRoles();
		mv.addObject("user", user);
		mv.addObject("roleList", roleList);
		mv.setViewName("user/user_info");
		return mv;
	}

	/**
	 * 删除某个用户
	 * 
	 * @param userId
	 * @param out
	 */
	@RequestMapping(value = "/delete{userId}")
	public void deleteUser(@PathVariable int userId, PrintWriter out,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		try {
			userService.deleteUser(userId);
			out.write("success");
			this.insertLog(request, "删除用户信息");
		} catch (Exception e) {

			e.printStackTrace();
			this.logger.error("删除用户失败：" + e.getMessage(), e);
			out.write("failed");
		}
		out.close();

	}

	/**
	 * 请求用户授权页面
	 * 
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/auth{userId}")
	public String auth(@PathVariable int userId, Model model) {
		List<Menu> menuList = menuService.listAllMenu();
		User user = userService.getUserById(userId);
		String userRights = "";
		if (user != null) {
			userRights = user.getRights();
		}
		if (Tools.notEmpty(userRights) && menuList != null
				&& menuList.size() > 0) {
			for (Menu menu : menuList) {
				menu.setHasMenu(RightsHelper.testRights(userRights,
						menu.getMenuId()));
				if (menu.isHasMenu()) {
					List<Menu> subRightsList = menu.getSubMenu();
					for (Menu sub : subRightsList) {
						sub.setHasMenu(RightsHelper.testRights(userRights,
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
		model.addAttribute("userId", userId);
		return "user/authorization";
	}

	/**
	 * 保存用户权限
	 * 
	 * @param userId
	 * @param menuIds
	 * @param out
	 */
	@RequestMapping(value = "/auth/save")
	public void saveAuth(@RequestParam int userId,
			@RequestParam String menuIds, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		User user = userService.getUserById(userId);
		if (user != null) {

			BigInteger rights = RightsHelper.sumRights(Tools
					.str2StrArray(menuIds));

			user.setRights(rights.toString());

			try {
				userService.updateUserRights(user);
				this.insertLog(request, "修改用户权限");
				out.write("success");
			} catch (Exception e) {

				e.printStackTrace();
				this.logger.error("修改用户权限失败：" + e.getMessage(), e);
				out.write("failed");
			}
		} else {
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
	@RequestMapping(value = "/authDwpas{userId}")
	public ModelAndView authDwpas(@PathVariable int userId, Model model) {
		List<DwpasCTemplateInfo> dwpasCTemplateInfoList = dwpasCTemplateInfoService
				.listAllTemplateInfo();
		User user = userService.getUserById(userId);
		String[] dwpasRights = user.getDwpasRights().split(",");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/user/authorizationDwpas2");
		mv.addObject("dwpasCTemplateInfo", dwpasCTemplateInfoList);
		mv.addObject("dwpasRights", dwpasRights);
		mv.addObject("user", user);
		mv.addObject("userId", userId);
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
		Integer userId = Integer.parseInt(request.getParameter("userId"));
		String[] templateIds = request.getParameterValues("templateIds");
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
		
		User user = new User();
		user = userService.getUserById(userId);
		user.setDwpasRights(dwpasRights);
		
		
		try {
			userService.updateDwpasRights(user);
			this.insertLog(request, "修改用户" + user.getLoginname()
					+ "经纬仪权限");
			mv.addObject("msg", "success");
			
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("保存经纬仪权限失败：" + e.getMessage(), e);
			mv.addObject("msg", "failed");

		}
		
		mv.addObject("userId", userId);
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
	@RequestMapping(value = "/authDwmis{userId}")
	public ModelAndView authDwmis(@PathVariable int userId, Model model) {
		List<DwmisTemplateInfo> dwmisTemplateInfoList = dwmisTemplateInfoService
				.listAllTemplateInfo();
		User user = userService.getUserById(userId);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/user/authorizationDwmis1");
		mv.addObject("dwmisTemplateInfoList", dwmisTemplateInfoList);
		mv.addObject("user", user);
		mv.addObject("userId", userId);
		return mv;
	}

	/**
	 * 保存瞭望塔限页面
	 * 
	 * @param roleId
	 * @param menuIds
	 * @param out
	 */
	@RequestMapping(value = "/saveAuthDwmis", method = RequestMethod.POST)
	public ModelAndView saveAuthDwmis(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String userIdString = request.getParameter("userId");
		if(com.infosmart.util.StringUtils.notNullAndSpace(userIdString)){
			Integer userId = Integer.parseInt(userIdString);
			String[] templateIds = request.getParameterValues("templateIds");
			User user = new User();
			user = userService.getUserById(userId);
			if (templateIds != null && templateIds.toString() != "") {
				for (int i = 0; i < templateIds.length; i++) {

					user.setUserId(userId);
					user.setDwmisRights(templateIds[i]);
					try {
						userService.updateDwmisRights(user);
						this.insertLog(request, "修改用户" + user.getLoginname()
								+ "瞭望塔权限");
						mv.addObject("msg", "success");
					} catch (Exception e) {

						e.printStackTrace();
						this.logger.error("保存瞭望塔权限失败：" + e.getMessage(), e);
						mv.addObject("msg", "failed");
					}

				}
			} else {
				try {
					user.setUserId(userId);
					user.setDwpasRights("0");
					userService.updateDwmisRights(user);
					this.insertLog(request, "修改用户" + user.getLoginname() + "瞭望塔权限");
					mv.addObject("msg", "success");
				} catch (Exception e) {

					e.printStackTrace();
					this.logger.error("保存瞭望塔权限失败：" + e.getMessage(), e);
					mv.addObject("msg", "failed");
				}

			}
			mv.addObject("userId", userId);
			mv.setViewName(SUCCESS_ACTION);

			return mv;
		}else{
			this.logger.error("保存瞭望塔权限失败userId为空");
			mv.addObject("msg", "failed");
			return mv;
		}
		
	}

	/**
	 * 递归选中报表权限
	 * 
	 * @param reportList
	 * @param reportRights
	 * @return
	 */
	private List<ReportDesign> reportChecked(List<ReportDesign> reportList,
			String reportRights) {
		if (reportList != null && reportList.size() > 0
				&& Tools.notEmpty(reportRights)) {
			for (ReportDesign report : reportList) {
				report.setHasReport(RightsHelper.testRights(reportRights,
						report.getReportId()));
				if (null != report.getSubReport()) {
					reportChecked(report.getSubReport(), reportRights);
				}
			}
		}
		return reportList;
	}

	// 角色权限zy
	/*
	 * private List<Report> roleReportChecked(List<Report> reportList, String
	 * roleRights) {
	 * 
	 * for (Report report : reportList) { if(report.isHasReport()==false){
	 * report.setHasReport(RightsHelper.testRights(roleRights,
	 * report.getReportId())); } if (null != report.getSubReport()) {
	 * roleReportChecked(report.getSubReport(), roleRights); } } return
	 * reportList; }
	 */

	/**
	 * 请求报表权限页面
	 * 
	 * @param roleId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/authReport{userId}")
	public String authReport(@PathVariable int userId, Model model) {
		List<ReportDesign> reportList = reportService.listAllReport();
		User user = userService.getUserById(userId);
		// 获得用户的角色权限
		if (user != null) {
			int roleId = user.getRoleId();
			Role role = roleService.getRoleById(roleId);
			String roleRights = role.getReportRights();
			String reportRights = user.getReportRights();
			if (reportList != null && reportList.size() > 0
					&& Tools.notEmpty(reportRights)) {
				reportChecked(reportList, reportRights);
				// 角色权限
				// roleReportChecked(reportList, roleRights);
			}
		}
		// if (Tools.notEmpty(reportRights)) {
		// reportChecked(reportList, reportRights);
		// }
		JSONArray arr = JSONArray.fromObject(reportList);
		String json = arr.toString();
		json = json.replaceAll("reportId", "id")
				.replaceAll("reportName", "name")
				.replaceAll("subReport", "nodes")
				.replaceAll("hasReport", "checked");
		model.addAttribute("zTreeNodes", json);
		model.addAttribute("userId", userId);
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
			@RequestParam int userId, @RequestParam String reportIds,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		BigInteger reportRights = RightsHelper.sumRights(Tools
				.str2StrArray(reportIds));
		User user = userService.getUserById(userId);
		if(user == null){
			this.sendMsgToClient(isFailed, response);
			this.logger.warn("修改用户报表权限失败,user为null");
			return;
		}
		user.setReportRights(reportRights.toString());
		try {
			userService.updateReportRights(user);
			this.insertLog(request, "修改用户" + user.getLoginname() + "报表权限");
			out.write("success");
		} catch (Exception e) {

			e.printStackTrace();
			this.logger.error("修改用户报表权限失败：" + e.getMessage(), e);
			out.write("failed");
		}

		out.close();
	}

	/**
	 * 导出用户信息到excel
	 * 
	 * @return
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView export2Excel(HttpServletRequest request) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("登录名称");
		titles.add("用户名称");
		titles.add("角色名称");
		titles.add("最近登录");
		dataMap.put("titles", titles);
		List<User> userList = userService.listAllUser();
		dataMap.put("userList", userList);
		UserExcelView erv = new UserExcelView();
		ModelAndView mv = new ModelAndView(erv, dataMap);
		this.insertLog(request, "导出用户信息到excel");
		return mv;
	}

	// @InitBinder
	// public void initBinder(WebDataBinder binder){
	// DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	// binder.registerCustomEditor(Date.class, new
	// CustomDateEditor(format,true));
	// }

	/**
	 * 自服务报表权限页面 hgt date:2012-05-07
	 * 
	 * @param roleId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/authSelfReport{userId}")
	public String authSelfReport(@PathVariable int userId,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		List<SelfApply> selfApplyList = selfApplyService
				.querySelfApplyByUsrID(userId);
		User user = userService.getUserById(userId);
		this.insertLog(request, "查看用户: " + user.getUsername() + " 的自服务报表权限");
		// 获得用户的角色权限
		// int roleId=user.getRoleId();
		// Role role = roleService.getRoleById(roleId);
		// String roleRights = role.getReportRights();
		// String selfRights = user.getSelfRights();
		// if(selfApplyList != null && selfApplyList.size()>0 &&
		// Tools.notEmpty(selfRights)){
		// selfApplyChecked(selfApplyList, selfRights,roleRights);
		// 角色权限
		// roleReportChecked(reportList, roleRights);
		// }
		// if (Tools.notEmpty(reportRights)) {
		// reportChecked(reportList, reportRights);
		// }
		String json = "";
		if (selfApplyList != null && !selfApplyList.isEmpty()) {
			JSONArray arr = JSONArray.fromObject(selfApplyList);
			json = arr.toString();
			json = json.replaceAll("reportId", "id")
					.replaceAll("reportName", "name")
					.replaceAll("hasRights", "checked");
		}
		model.addAttribute("zTreeNodes", json);
		model.addAttribute("userId", userId);
		return "user/authorizationSelfApply";
	}

	// 递归选中报表权限
	// private List<SelfApply> selfApplyChecked(List<SelfApply> selfApplyList,
	// String selfRights,String reportRights) {
	// SelfApply selfApply = null;
	// if (selfApplyList == null)
	// return selfApplyList;
	// for (int i = 0; i < selfApplyList.size(); i++) {
	// selfApply = selfApplyList.get(i);
	// selfApply.setHasRights(RightsHelper.testRights(selfRights,selfApply.getReportId())
	// ||RightsHelper.testRights(reportRights,selfApply.getReportId()));
	// if (selfApply.isHasRights()) {
	// selfApplyChecked(selfApplyList, selfRights, reportRights);
	// } else {
	// selfApplyList.remove(i);
	// i--;
	// }
	// }
	// return selfApplyList;
	// }

}
