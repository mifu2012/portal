package com.infosmart.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.infosmart.util.Const;

public class WebAppContextListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent event) {
	}

	public void contextInitialized(ServletContextEvent event) {
		Const.WEB_APP_CONTEXT = WebApplicationContextUtils
				.getWebApplicationContext(event.getServletContext());
		// System.out.println("========获取Spring WebApplicationContext");
		event.getServletContext().setAttribute("systemUrlList",
				Const.SYSTEM_URL_LIST);
		event.getServletContext().setAttribute("systemUrlListMonth",
				Const.SYSTEM_URL_LIST_MONTH);
		// 是否需要验证码
		String needVerification = event.getServletContext().getInitParameter(
				"needVerification");
		event.getServletContext().setAttribute("needVerification",
				needVerification);
	}

}
