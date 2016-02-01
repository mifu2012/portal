package com.infosmart.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.infosmart.util.report.PoolManager;
import com.infosmart.view.SystemUrl;

public class Const {
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";
	public static final String SESSION_USER = "sessionUser";
	public static final String SESSION_USER_RIGHTS = "sessionUserRights";
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
	public static final String SESSION_USERREPORT_RIGHTS = "sessionUserReportRights";
	public static final String SESSION_ROLEREPORT_RIGHTS = "sessionRoleReportRights";
	public static final String SESSION_TESTSQL_INFO = "testSqlInfo";// 测试SQL信息
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)).*"; // 不对匹配该值的访问路径拦截（正则）
	public static ApplicationContext WEB_APP_CONTEXT = null; // 该值会在web容器启动时由WebAppContextListener初始化

	public static final String SESSION_TEMPLATE_ID = "sessionTemplateId"; // 进入经纬仪模板后，点击某个模板将templateId放入session;

	/* 产品发展栏目类型 */
	// 当月值
	public static final int PRODUCT_DEVELOP_COLUMN_TYPE_CRT_MONTH = 1;
	// 上月值
	public static final int PRODUCT_DEVELOP_COLUMN_TYPE_LAST_MONTH = 2;
	// 同比
	public static final int PRODUCT_DEVELOP_COLUMN_TYPE_TB = 3;
	// 去年同期
	public static final int PRODUCT_DEVELOP_COLUMN_TYPE_CRT_YEAR_AND_MONTH = 4;
	// 环比
	public static final int PRODUCT_DEVELOP_COLUMN_TYPE_HB = 5;
	// DBCP链接
	public static Map<String, PoolManager> CONNECTION_POOL_MAP = new HashMap<String, PoolManager>();

	/**
	 * 系统栏目（用于首页）
	 */
	public static final List<SystemUrl> SYSTEM_URL_LIST = new ArrayList<SystemUrl>();
	/**
	 * 系统栏目（月模块）
	 */
	public static final List<SystemUrl> SYSTEM_URL_LIST_MONTH = new ArrayList<SystemUrl>();

	/**
	 * 系统栏目（用于雷达图）
	 */
	public static final List<SystemUrl> RADAR_MAP_URL_LIST = new ArrayList<SystemUrl>();

	static {
		try {
			if (SYSTEM_URL_LIST.isEmpty()) {
				String configXmlFolder = Const.class.getClassLoader()
						.getResource("").getPath();
				if (!configXmlFolder.endsWith("/"))
					configXmlFolder += "/";
				InputStream is = new FileInputStream(new File(configXmlFolder
						+ "config/SystemUrl.xml"));
				java.util.Properties properties = new java.util.Properties();
				properties.loadFromXML(is);
				if (properties != null && !properties.isEmpty()) {
					Enumeration names = properties.propertyNames();
					while (names.hasMoreElements()) {
						String strKey = (String) names.nextElement();
						String strValue = properties.getProperty(strKey);
						SYSTEM_URL_LIST.add(new SystemUrl(strKey, strValue));
					}
				}
				is = new FileInputStream(new File(configXmlFolder
						+ "config/SystemUrlMonth.xml"));
				properties = new java.util.Properties();
				properties.loadFromXML(is);
				if (properties != null && !properties.isEmpty()) {
					Enumeration names = properties.propertyNames();
					while (names.hasMoreElements()) {
						String strKey = (String) names.nextElement();
						String strValue = properties.getProperty(strKey);
						SYSTEM_URL_LIST_MONTH.add(new SystemUrl(strKey, strValue));
					}
				}
				if (is != null) {
					is.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println(Const.SYSTEM_URL_LIST.size());
	}
}
