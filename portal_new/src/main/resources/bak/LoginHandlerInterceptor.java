package com.infosmart.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.infosmart.portal.pojo.User;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.db.CustomerContextHolder;
import com.infosmart.portal.util.db.SpObserver;

public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = Logger.getLogger(this.getClass());
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(quit)).*";

	private final String SESSION_CRT_DATASOURCE = "_USER_CRT_DS_CODE";

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		this.logger.info("------->" + request.getServletPath());
		String path = request.getServletPath();
		if (path.matches(NO_INTERCEPTOR_PATH)) {
			String loginName = request.getParameter("loginName");
			/*
			 * if ("admin".equalsIgnoreCase(loginName)) {
			 * CustomerContextHolder.setDataSourceType("dataSource_500w");
			 * this.logger.info("500W用户登陆了......................."); } else {
			 * this.logger.info("其他用户登陆了.......................");
			 * CustomerContextHolder.setDataSourceType("dataSource"); }
			 */
			// 未登陆，根据用户名后缀(如xxx@20090109),查询所属数据源--》SpObserver.putDbName("20090109");//20090109为导向库表ID
			// SpObserver.putDbName("20090109");
			// 默认取用户信息关联的数据源
			String crtDataSource = "dataSource";
			if ("admin".equalsIgnoreCase(loginName)) {
				crtDataSource = "dataSource_500w";
				this.logger.info("500W用户登陆了.......................");
			} else {
				crtDataSource = "dataSource";
				this.logger.info("其他用户登陆了.......................");
			}
			SpObserver.putDbName(crtDataSource);
			request.getSession().setAttribute(SESSION_CRT_DATASOURCE,
					crtDataSource);
			return true;
		} else {
			Object user = request.getSession().getAttribute(
					Constants.SESSION_USER_INFO);
			if (user != null) {
				User loginUser = (User) user;
				this.logger.info("NEW-->当前操作用户：" + loginUser.getUserName()
						+ ",当前数据源:"
						+ request.getSession().getAttribute(SESSION_CRT_DATASOURCE));
				/*
				 * if ("admin".equalsIgnoreCase(loginUser.getLoginName())) {
				 * CustomerContextHolder.setDataSourceType("dataSource_500w");
				 * this.logger.info("500W用户登陆......................."); } else {
				 * this.logger.info("其他用户登陆.......................");
				 * CustomerContextHolder.setDataSourceType("dataSource"); }
				 */
				// 默认取用户信息关联的数据源
				SpObserver.putDbName(request.getSession().getAttribute(
						SESSION_CRT_DATASOURCE) == null ? "dataSource"
						: request.getSession()
								.getAttribute(SESSION_CRT_DATASOURCE)
								.toString());
				return true;
			} else {
				this.logger.info("退出系统....");
				response.sendRedirect(request.getContextPath() + "/quit");
				this.logger.info("------->" + request.getContextPath());
				return false;
			}

		}

	}

}
