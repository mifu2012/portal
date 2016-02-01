package com.infosmart.portal.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCSystemMenu;
import com.infosmart.portal.pojo.DwpasCTemplateInfo;
import com.infosmart.portal.pojo.Role;
import com.infosmart.portal.pojo.User;
import com.infosmart.portal.pojo.report.ReportDesign;
import com.infosmart.portal.service.DwpasCSystemMenuService;
import com.infosmart.portal.service.DwpasRPrdKpiService;
import com.infosmart.portal.service.UserLoginService;
import com.infosmart.portal.service.report.ReportService;
import com.infosmart.portal.util.Const;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.MD5;
import com.infosmart.portal.util.StringDes;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.dwpas.RightsHelper;

/**
 * 用户登录
 * 
 * @author xbb
 * 
 */
@Controller
public class UserLoginController extends BaseController {
	private Logger logger = Logger.getLogger(this.getClass());

	private final String SUCCESS_ACTION = "/common/save_result";

	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private DwpasCSystemMenuService systemMenuService;
	@Autowired
	private DwpasRPrdKpiService dwpasRPrdKpiService;
	@Autowired
	private ReportService reportService;

	// 递归选中报表权限
	private List<ReportDesign> reportChecked(List<ReportDesign> reportList,
			String userRights, String reportRights) {
		ReportDesign report = null;
		if (reportList == null)
			return reportList;
		for (int i = 0; i < reportList.size(); i++) {
			report = reportList.get(i);
			report.setHasReport(RightsHelper.testRights(userRights,
					report.getReportId())
					|| RightsHelper.testRights(reportRights,
							report.getReportId()));
			if (report.isHasReport()) {
				reportChecked(report.getSubReport(), userRights, reportRights);
			} else {
				reportList.remove(i);
				i--;
			}
		}
		return reportList;
	}

	/**
	 * 更换模板
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/UserLogin/getDwpasTemplateId")
	public ModelAndView getTemplateToSession(HttpSession session,
			HttpServletRequest request) {
		String dwpasTempLateId = request.getParameter("dwpasTemplateId");
		String type = request.getParameter("type");
		request.getSession().setAttribute(Const.SESSION_DWPAS_TEMPLATEID,
				dwpasTempLateId);
		request.getSession().setAttribute(Const.SESSION_DWPAS_MENU_LIST, null);
		request.getSession().setAttribute("productInfo", null);
		request.getSession().setAttribute(
				Constants.REPORT_REPORT_QUERY_PRODUCT_ID, null);
		return new ModelAndView("redirect:/UserLogin/mainPage", "type", type);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/UserLogin/mainPage")
	public ModelAndView mainPage(HttpSession session, HttpServletRequest request) {
		this.logger.info("to main page......");
		/**
		 * 从session中获取，暂时先写死
		 */
		// List<DwpasCSystemMenu> totalsystemMenuList = this.systemMenuService
		// .listAllDwpasCSystemMenu(new Integer(1));
		String navId = "";
		String reportMenuName = "";
		User crtUser = this.getCrtUser(session);
		// 获取所有模板信息
		// List<DwpasCTemplateInfo> templateInfoList =

		String type = request.getParameter("type");
		if (!StringUtils.notNullAndSpace(type)) {

			// type为空,取默认的子系统类型
			// 默认的类型
			if (!"0".equals(crtUser.getDwmisRights())
					|| !"0".equals(crtUser.getRole().getDwmisRights())) {
				type = Const.SYSTEM_TYPE_DWMIS;
			} else if ("0" != crtUser.getDwpasRights()
					|| "0" != crtUser.getRole().getDwpasRights()) {
				type = Const.SYSTEM_TYPE_DWPAS;
			} else {
				type = Const.SYSTEM_TYPE_REPORT;
			}
		} else {
			// /////////////////////开始：对type参数数据进行了加密处理，后台进行解密处理
			try {
				// 对type参数数据进行了加密处理，后台进行解密处理
				type = StringDes.StringToDec(type);
				// 如果type不能转到整数，则表示type已篡改啦
				Integer.parseInt(type);
			} catch (Exception e) {
				this.logger.warn("非法的子系统类型TYPE参数");
				return new ModelAndView("/common/noPrivilege");
			}
			// ///////////////////////结束：对type参数数据进行了加密处理，后台进行解密处理
		}
		this.logger.info("----------->system-type:" + type);
		List<DwpasCSystemMenu> systemMenuList = new ArrayList<DwpasCSystemMenu>();
		// for (DwpasCSystemMenu dwpasCSystemMenu : totalsystemMenuList) {
		// if (type.equals(dwpasCSystemMenu.getMenuType())) {
		// systemMenuList.add(dwpasCSystemMenu);
		// }
		// }
		// if (systemMenuList.size() > 0) {
		// request.setAttribute("defaultUrl", systemMenuList.get(0)
		// .getMenuUrl());
		// }
		// 瞭望塔
		if (Const.SYSTEM_TYPE_DWMIS.equals(type)) {

			request.setAttribute("defaultUrl",
					"/dwmisHomePage/getAllSubjects.html");
		}
		// 经纬仪
		if (Const.SYSTEM_TYPE_DWPAS.equals(type)) {
			String userDwpasRights = (String) session
					.getAttribute(Const.SESSION_USERDWPAS_RIGHTS);
			String roleDwpasRights = (String) session
					.getAttribute(Const.SESSION_ROLEDWPAS_RIGHTS);
			List<String> allDwpasTemplateIdList = new ArrayList<String>();
			if (StringUtils.notNullAndSpace(userDwpasRights)
					&& !"0".equals(userDwpasRights)) {
				allDwpasTemplateIdList.addAll(Arrays.asList(userDwpasRights
						.split(",")));
			}
			session.setAttribute("allDwpasTemplateIdList",
					allDwpasTemplateIdList);
			if (StringUtils.notNullAndSpace(roleDwpasRights)
					&& !"0".equals(roleDwpasRights)) {
				String[] roleTemplateId = roleDwpasRights.split(",");
				for (String tempId : roleTemplateId) {
					if (!allDwpasTemplateIdList.contains(tempId)) {
						allDwpasTemplateIdList.add(tempId);
					}
				}
			}

			List<DwpasCTemplateInfo> templateInfoList = userLoginService
					.getTemplateInfos(allDwpasTemplateIdList);
			request.setAttribute("templateInfoList", templateInfoList);

			String dwpasTemplateId = "0";
			// 经纬仪模板Id
			// 经纬仪模板Id
			if (session.getAttribute(Const.SESSION_DWPAS_TEMPLATEID) == null) {
				dwpasTemplateId = allDwpasTemplateIdList.get(0);
				// 将经纬仪模板I的放入session
				session.setAttribute(Const.SESSION_DWPAS_TEMPLATEID,
						dwpasTemplateId);
			} else {
				dwpasTemplateId = (String) session
						.getAttribute(Const.SESSION_DWPAS_TEMPLATEID);
			}
			this.logger.info("当前用户模板------->" + dwpasTemplateId);
			// 判断是否关联的模板
			if (dwpasTemplateId == null
					|| Integer.parseInt(dwpasTemplateId) == 0) {
				this.logger.error("当前用户未关联任何模板");
				session.setAttribute("var_template_msg", "当前用户未关联任何模板");
				request.setAttribute("defaultUrl",
						"/jsp/common/errorDefaulltTemplate.jsp");
				return new ModelAndView("index");
			}
			// 判断是否关联的产品
			String defaultPrdId = this.getCrtProductIdOfReport(request);
			this.logger.info("--->默认产品ID:" + defaultPrdId);
			if (defaultPrdId == null) {
				this.logger.error("当前模板未关联任何产品");
				session.setAttribute("var_template_msg", "当前模板未关联任何产品");
				request.setAttribute("defaultUrl",
						"/jsp/common/errorDefaulltTemplate.jsp");
				return new ModelAndView("index");
			} else {
				// 判断当前产品是否关联的指标
				this.logger.info("判断当前产品是否关联的指标");
				List<DwpasCKpiInfo> kpiInfoList = this.dwpasRPrdKpiService
						.listDwpasCKpiInfoByPrdId(defaultPrdId);
				if (kpiInfoList == null || kpiInfoList.isEmpty()) {
					session.setAttribute("var_template_msg", "当前产品未关联任何指标");
					request.setAttribute("defaultUrl",
							"/jsp/common/errorDefaulltTemplate.jsp");
					return new ModelAndView("index");
				}
			}
			if (session.getAttribute(Const.SESSION_DWPAS_MENU_LIST) == null) {
				// 根据经纬仪模板Id获得经纬仪菜单，并将经纬仪菜单列表放入session,暂时加了type条件
				systemMenuList = this.systemMenuService
						.listOneDwpasCSystemMenu(
								Integer.parseInt(dwpasTemplateId), type);
				// systemMenuList=this.systemMenuService.listAllDwpasCSystemMenu(dwpasTemplateId);
				session.setAttribute(Const.SESSION_DWPAS_MENU_LIST,
						systemMenuList);
			} else {
				systemMenuList = (List<DwpasCSystemMenu>) session
						.getAttribute(Const.SESSION_DWPAS_MENU_LIST);
			}
			if (systemMenuList.size() > 0) {
				request.setAttribute("defaultUrl", systemMenuList.get(0)
						.getMenuUrl()
						+ "&menuId="
						+ systemMenuList.get(0).getMenuId());
				// 将首页menuId放入session
				request.getSession().setAttribute(Const.INDEX_MENU_ID,
						systemMenuList.get(0).getMenuId());
				navId = systemMenuList.get(0).getMenuId();
			} else {
				request.setAttribute("defaultUrl", "/jsp/common/noMenu.jsp");
			}
		}

		// 报表
		if (Const.SYSTEM_TYPE_REPORT.equals(type)) {
			String roleRights = session.getAttribute(
					Const.SESSION_ROLEREPORT_RIGHTS).toString();
			String userRights = session.getAttribute(
					Const.SESSION_USERREPORT_RIGHTS).toString();

			List<ReportDesign> reportMenuList = null;
			// 避免每次拦截用户操作时查询数据库，以下将用户所属报表菜单都存入session
			if (session.getAttribute(Const.SESSION_REPORT_MENU_LIST) == null) {
				reportMenuList = reportService.listAllParentReport();
				if (StringUtils.notNullAndSpace(userRights)
						|| StringUtils.notNullAndSpace(roleRights)) {
					reportChecked(reportMenuList, userRights, roleRights);
				}
				if (reportMenuList != null && reportMenuList.size() > 0)
					session.setAttribute(Const.SESSION_REPORT_MENU_LIST,
							reportMenuList);
			} else {
				reportMenuList = (List<ReportDesign>) session
						.getAttribute(Const.SESSION_REPORT_MENU_LIST);
			}
			if (reportMenuList != null && reportMenuList.size() > 0) {
				reportMenuName = reportMenuList.get(0).getReportName();
				request.setAttribute("defaultUrl", "/selfReport/"
						+ reportMenuList.get(0).getReportId() + ".html?reportMenuName="+reportMenuName);
				navId = String.valueOf(reportMenuList.get(0).getReportId());
				
			} else {
				request.setAttribute("defaultUrl", "/jsp/common/apply.jsp");
			}
		}

		request.setAttribute("navId", navId);
		request.setAttribute("systemMenuList", systemMenuList);
		request.setAttribute("type", type);
		return new ModelAndView("index");
	}

	/**
	 * 登陆
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public ModelAndView signIn(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// yanzm
		String loginName = request.getParameter("loginName");
		this.logger.info("--->loginName:" + loginName);
		MD5 md = new MD5();
		String passWord = request.getParameter("passWord");
		String errInfo = "";
		Map map = new HashMap();
		String sessionCode = request.getSession().getAttribute("validateing") == null ? "xxx"
				: request.getSession().getAttribute("validateing").toString();
		String inptCode = request.getParameter("validateing") == null ? "www"
				: request.getParameter("validateing");
		String needVerification = (String) request.getSession()
				.getServletContext().getAttribute("needVerification");

		if (Integer.valueOf(needVerification) == 1) {
			if (sessionCode != null && sessionCode.equalsIgnoreCase(inptCode)) {
				if (null != passWord && "" != passWord) {
					passWord = md.getkeyBeanofStr(passWord.trim());
				}

				User user = userLoginService.getUser(loginName, passWord);
				// 如果数据库中可查询到该用户
				if (null != user) {
					this.sysLogService.insertLog(user, "登陆经纬仪系统",
							this.getClientIp(request));
					user.setLastLogin(new Date());
					this.logger.info("默认模板ID:" + user.getDwpasRights());
					// 获得封装有role的user
					user = userLoginService
							.getUserAndRoleById(user.getUserId());
					Role role = user.getRole();
					if (user.getStatus() == 0 && role != null) {
						// 有经纬仪或瞭望塔或报表系统权限
						if ("0" != user.getDwpasRights()
								|| "0" != role.getDwpasRights()
								|| !"0".equals(user.getDwmisRights())
								|| !"0".equals(role.getDwmisRights())
								|| !"0".equals(user.getReportRights())
								|| !"0".equals(role.getReportRights())) {
							String userDwmisRights = user.getDwmisRights();
							String roleDwmisRights = role.getDwmisRights();

							String userDwpasRights = user.getDwpasRights();
							String roleDwpasRights = role.getDwpasRights();

							String userReportRights = user.getReportRights();
							String roleReportRights = role.getReportRights();
							// 将用户信息以及各个权限放入session
							request.getSession().setAttribute(
									Constants.SESSION_USER_INFO, user);
							request.getSession().setAttribute(
									Const.SESSION_USERDWMIS_RIGHTS,
									userDwmisRights);
							request.getSession().setAttribute(
									Const.SESSION_ROLEDWMIS_RIGHTS,
									roleDwmisRights);

							request.getSession().setAttribute(
									Const.SESSION_USERDWPAS_RIGHTS,
									userDwpasRights);
							request.getSession().setAttribute(
									Const.SESSION_ROLEDWPAS_RIGHTS,
									roleDwpasRights);

							request.getSession().setAttribute(
									Const.SESSION_USERREPORT_RIGHTS,
									userReportRights);
							request.getSession().setAttribute(
									Const.SESSION_ROLEREPORT_RIGHTS,
									roleReportRights);
							request.getSession().removeAttribute("validateing");

						} else {
							errInfo = "该用户无权限浏览模板";
						}
					} else {
						errInfo = "该用户被冻结";
						if (role == null) {
							errInfo = "该用户不属于任何角色，无法登陆！";
						}
					}
					// // 如果用户没有设置s默认模版
					// if (!StringUtils.notNullAndSpace(user.getDwpasRights()))
					// {
					// // 查询角色的模板
					// DwpasCTemplateInfo templateInfo = userLoginService
					// .getRoleTemplate(user.getRoleId());
					// if (templateInfo != null) {
					// user.setDwpasRights(templateInfo.getTemplateId()
					// .toString());
					// }
					// }
				} else {
					errInfo = "登录名或者密码有误";
					this.sysLogService.insertLog(new User(-1, "loginName",
							"未知用户"), "试图登陆经纬仪系统：用户名或密码错误", this
							.getClientIp(request));
				}
			} else {
				this.sysLogService.insertLog(new User(-1, "loginName", "未知用户"),
						"试图登陆经纬仪系统：验证码错误", this.getClientIp(request));
				errInfo = "验证码输入有误";
			}
		} else {
			if (null != passWord && "" != passWord) {
				passWord = md.getkeyBeanofStr(passWord.trim());
			}

			User user = userLoginService.getUser(loginName, passWord);
			// 如果数据库中可查询到该用户
			if (null != user) {
				this.sysLogService.insertLog(user, "登陆经纬仪系统",
						this.getClientIp(request));
				user.setLastLogin(new Date());
				this.logger.info("默认模板ID:" + user.getDwpasRights());
				// 获得封装有role的user
				user = userLoginService.getUserAndRoleById(user.getUserId());
				Role role = user.getRole();
				if (user.getStatus() == 0 && role != null) {
					// 有经纬仪或瞭望塔或报表系统权限
					if ("0" != user.getDwpasRights()
							|| "0" != role.getDwpasRights()
							|| !"0".equals(user.getDwmisRights())
							|| !"0".equals(role.getDwmisRights())
							|| !"0".equals(user.getReportRights())
							|| !"0".equals(role.getReportRights())) {
						String userDwmisRights = user.getDwmisRights();
						String roleDwmisRights = role.getDwmisRights();

						String userDwpasRights = user.getDwpasRights();
						String roleDwpasRights = role.getDwpasRights();

						String userReportRights = user.getReportRights();
						String roleReportRights = role.getReportRights();
						// 将用户信息以及各个权限放入session
						request.getSession().setAttribute(
								Constants.SESSION_USER_INFO, user);
						request.getSession()
								.setAttribute(Const.SESSION_USERDWMIS_RIGHTS,
										userDwmisRights);
						request.getSession()
								.setAttribute(Const.SESSION_ROLEDWMIS_RIGHTS,
										roleDwmisRights);

						request.getSession()
								.setAttribute(Const.SESSION_USERDWPAS_RIGHTS,
										userDwpasRights);
						request.getSession()
								.setAttribute(Const.SESSION_ROLEDWPAS_RIGHTS,
										roleDwpasRights);

						request.getSession().setAttribute(
								Const.SESSION_USERREPORT_RIGHTS,
								userReportRights);
						request.getSession().setAttribute(
								Const.SESSION_ROLEREPORT_RIGHTS,
								roleReportRights);
						request.getSession().removeAttribute("validateing");

					} else {
						errInfo = "该用户无权限浏览模板";
					}
				} else {
					errInfo = "该用户被冻结";
					if (role == null) {
						errInfo = "该用户不属于任何角色，无法登陆！";
					}
				}
			} else {
				errInfo = "登录名或者密码有误";
				this.sysLogService.insertLog(new User(-1, "loginName", "未知用户"),
						"试图登陆经纬仪系统：用户名或密码错误", this.getClientIp(request));
			}
		}
		if ("".equals(errInfo) || null == errInfo) {
			return new ModelAndView("redirect:/UserLogin/mainPage");
		} else {
			map.put("errInfo", errInfo);
			return new ModelAndView("/UserLogin/loginResult", map);
		}

	}

	/**
	 * 登出
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/quit")
	public ModelAndView quit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = this.getCrtUser(request.getSession());
		if (user != null) {
			this.logger.info(user.getUserName() + "退出系统");
			this.sysLogService.insertLog(this.getCrtUser(request.getSession()),
					"退出经纬仪系统", this.getClientIp(request));
		}
		// clear session
		request.getSession().invalidate();
		return new ModelAndView("/UserLogin/login");

	}

	/**
	 * 请求修改密码页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/UserLogin/updatePassWord")
	public ModelAndView updatePassWord(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("UserLogin/user_info");
		return mv;
	}

	/**
	 * 
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/UserLogin/save", method = RequestMethod.POST)
	public ModelAndView saveUser(HttpServletRequest request,
			HttpServletResponse response) {
		this.sysLogService.insertLog(this.getCrtUser(request.getSession()),
				"修改经纬仪系统密码", this.getClientIp(request));
		ModelAndView mv = new ModelAndView();
		String passWord = request.getParameter("passWord");
		MD5 md = new MD5();
		User user = (User) request.getSession().getAttribute(
				Constants.SESSION_USER_INFO);
		userLoginService.updatePassWord(md.getkeyBeanofStr(passWord.trim()),
				user.getUserId());

		this.insertLog(request, "修改用户密码");
		mv.setViewName(SUCCESS_ACTION);
		return mv;
	}

}
