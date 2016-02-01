package com.infosmart.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.po.Menu;
import com.infosmart.po.Role;
import com.infosmart.po.User;
import com.infosmart.service.MenuService;
import com.infosmart.service.UserService;
import com.infosmart.util.Const;
import com.infosmart.util.MD5;
import com.infosmart.util.RightsHelper;
import com.infosmart.util.Tools;

@Controller
public class LoginController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private MenuService menuService;

	/**
	 * 访问登录页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGet() {
		return "login";
	}

	/**
	 * 请求登录，验证用户
	 * 
	 * @param session
	 * @param loginname
	 * @param password
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginPost(HttpServletRequest request,
			HttpSession session, @RequestParam String loginname,
			@RequestParam String password, @RequestParam String code)
			throws Exception {
		String sessionCode = (String) session
				.getAttribute(Const.SESSION_SECURITY_CODE);
		ModelAndView mv = new ModelAndView();
		String errInfo = "";
		// 是否需要验证码
		String needVerification = (String) request.getSession()
				.getServletContext().getAttribute("needVerification");
		if (Integer.valueOf(needVerification) != 0) {
			if (Tools.notEmpty(sessionCode)
					&& sessionCode.equalsIgnoreCase(code)) {
				MD5 md5 = new MD5();
				password = md5.getkeyBeanofStr(password);
				User user = userService
						.getUserByNameAndPwd(loginname, password);
				if (user != null) {
					user.setLastLogin(new Date());
					userService.updateLastLogin(user);
					session.setAttribute(Const.SESSION_USER, user);
					session.removeAttribute(Const.SESSION_SECURITY_CODE);
					this.insertLog(request, "登陆数据管理平台");
				} else {
					errInfo = "用户名或密码有误！";
					sysLogService.insertLog(new User(-1, loginname, loginname),
							"试图登陆数据管理平台：用户名或密码错误", this.getClientIp(request));
				}
			} else {
				errInfo = "验证码输入有误！";
				sysLogService.insertLog(new User(-1, loginname, loginname),
						"试图登陆数据管理平台：验证码", this.getClientIp(request));
			}
		} else {
			MD5 md5 = new MD5();
			password = md5.getkeyBeanofStr(password);
			User user = userService.getUserByNameAndPwd(loginname, password);
			if (user != null) {
				user.setLastLogin(new Date());
				userService.updateLastLogin(user);
				session.setAttribute(Const.SESSION_USER, user);
				session.removeAttribute(Const.SESSION_SECURITY_CODE);
				this.insertLog(request, "登陆数据管理平台");
			} else {
				errInfo = "用户名或密码有误！";
				sysLogService.insertLog(new User(-1, loginname, loginname),
						"试图登陆数据管理平台：用户名或密码错误", this.getClientIp(request));
			}
		}
		if (Tools.isEmpty(errInfo)) {
			mv.setViewName("redirect:index.html");
		} else {
			mv.addObject("errInfo", errInfo);
			mv.addObject("loginname", loginname);
			mv.addObject("password", password);
			mv.setViewName("login");
		}
		return mv;
	}

	/**
	 * 访问系统首页
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(HttpSession session, Model model) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		if (user == null) {
			return "login";
		}
		user = userService.getUserAndRoleById(user.getUserId());
		Role role = user.getRole();
		String roleRights = role != null ? role.getRights() : "";
		String userRights = user.getRights();
		// 避免每次拦截用户操作时查询数据库，以下将用户所属角色权限、用户权限限都存入session
		session.setAttribute(Const.SESSION_ROLE_RIGHTS, roleRights); // 将角色权限存入session
		session.setAttribute(Const.SESSION_USER_RIGHTS, userRights); // 将用户权限存入session

		List<Menu> menuList = menuService.listAllMenu();
		if (Tools.notEmpty(userRights) || Tools.notEmpty(roleRights)) {
			if (menuList != null && menuList.size() > 0) {
				for (Menu menu : menuList) {
					menu.setHasMenu(RightsHelper.testRights(userRights,
							menu.getMenuId())
							|| RightsHelper.testRights(roleRights,
									menu.getMenuId()));
					if (menu.isHasMenu()) {
						List<Menu> subMenuList = menu.getSubMenu();
						for (Menu sub : subMenuList) {
							sub.setHasMenu(RightsHelper.testRights(userRights,
									sub.getMenuId())
									|| RightsHelper.testRights(roleRights,
											sub.getMenuId()));
						}
					}
				}
			}
		}
		model.addAttribute("user", user);
		model.addAttribute("menuList", menuList);
		return "index";
	}

	/**
	 * 进入首页后的默认页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/default")
	public String defaultPage() {
		return "default";
	}

	/**
	 * 用户注销
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session, HttpServletRequest request) {
		this.insertLog(request, "退出数据管理平台");
		session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(Const.SESSION_ROLE_RIGHTS);
		session.removeAttribute(Const.SESSION_USER_RIGHTS);
		return "login";
	}
}
