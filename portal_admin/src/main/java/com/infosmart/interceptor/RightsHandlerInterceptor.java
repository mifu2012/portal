package com.infosmart.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.infosmart.po.Menu;
import com.infosmart.po.User;
import com.infosmart.util.Const;
import com.infosmart.util.RightsHelper;
import com.infosmart.util.ServiceHelper;
import com.infosmart.util.Tools;

public class RightsHandlerInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = Logger
			.getLogger(RightsHandlerInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String path = request.getServletPath();
		if (path.matches(Const.NO_INTERCEPTOR_PATH))
			return true;
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		Integer menuId = null;
		List<Menu> subList = ServiceHelper.getMenuService().listAllSubMenu();
		for (Menu m : subList) {
			String menuUrl = m.getMenuUrl();
			if (Tools.notEmpty(menuUrl)) {
				if (path.contains(menuUrl)) {
					menuId = m.getMenuId();
					break;
				} else {
					String[] arr = menuUrl.split("\\.");
					String regex = "";
					if (arr.length == 2) {
						regex = "/?" + arr[0] + "(/.*)?." + arr[1];

					} else {
						regex = "/?" + menuUrl + "(/.*)?.html";
					}
					if (path.matches(regex)) {
						menuId = m.getMenuId();
						break;
					}
				}
			}
		}
		//logger.debug(path + "====" + menuId);
		if (menuId != null) {
			String userRights = (String) session
					.getAttribute(Const.SESSION_USER_RIGHTS);
			String roleRights = (String) session
					.getAttribute(Const.SESSION_ROLE_RIGHTS);
			if (RightsHelper.testRights(userRights, menuId)
					|| RightsHelper.testRights(roleRights, menuId)) {
				return true;
			} else {
				logger.debug("用户：" + user.getLoginname() + "试图访问" + path + "被阻止！");
				logger.info("用户：" + user.getLoginname() + "试图访问" + path + "被阻止！");
				ModelAndView mv = new ModelAndView();
				mv.setViewName("role/no_rights");
				throw new ModelAndViewDefiningException(mv);
			}
		}
		return super.preHandle(request, response, handler);
	}

}
