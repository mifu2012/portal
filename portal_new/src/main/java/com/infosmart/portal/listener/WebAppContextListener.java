package com.infosmart.portal.listener;

import java.util.Arrays;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.infosmart.portal.util.Const;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.db.DynamicDataSource;

/**
 * 系统初始化监听器
 * 
 * @author infosmart
 * 
 */
public class WebAppContextListener implements ServletContextListener {
	protected final Logger logger = Logger.getLogger(this.getClass());

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		this.logger.info("系统终止中....");
	}

	public void contextInitialized(ServletContextEvent event) {
		this.logger.info("系统初始化中...");
		Const.WEB_APP_CONTEXT = WebApplicationContextUtils
				.getWebApplicationContext(event.getServletContext());
		logger.info("@@@@@@@@@@@@@@@@@@@@@:" + (Const.WEB_APP_CONTEXT == null));
		// amchart
		String amchartKey = event.getServletContext().getInitParameter(
				"amchartKey");
		// 是否需要验证码
		String needVerification = event.getServletContext().getInitParameter(
				"needVerification");
		// this.logger.info("-------------------->" + amchartKey);
		event.getServletContext().setAttribute("amchartKey", amchartKey);
		event.getServletContext().setAttribute("needVerification",
				needVerification);
		// systemName
		event.getServletContext().setAttribute("systemName",
				event.getServletContext().getInitParameter("systemName"));
		// 初始化颜色库
		String chartColor = event.getServletContext().getInitParameter(
				"chartColor");
		final String defaultColor = "52A4BA;8BAA5C;89759A;4E7DA7;B85D5A;8B1A1A;FFD700;00B2EE;8AAC57;EE7600;RED;BLUE;GREE;CDB79E;5C5C5C";
		Constants.CHART_COLOR_LIST = Arrays.asList((chartColor == null
				|| chartColor.length() == 0 ? defaultColor : chartColor)
				.split(";"));
		if (Constants.CHART_COLOR_LIST == null
				|| Constants.CHART_COLOR_LIST.isEmpty()) {
			Constants.CHART_COLOR_LIST = Arrays.asList(defaultColor.split(";"));
		}

		//

	}

}
