package com.infosmart.portal.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.infosmart.portal.util.report.PoolManager;

public class Const {
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";
	public static final String SESSION_USER = "sessionUser";
	public static final String SESSION_USER_RIGHTS = "sessionUserRights";
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
	public static final String SESSION_USERREPORT_RIGHTS = "sessionUserReportRights";
	public static final String SESSION_ROLEREPORT_RIGHTS = "sessionRoleReportRights";
	public static final String SESSION_USERDWMIS_RIGHTS = "sessionUserDwmisRights";
	public static final String SESSION_ROLEDWMIS_RIGHTS = "sessionRoleDwmisRights";
	public static final String SESSION_USERDWPAS_RIGHTS = "sessionUserDwpasRights";
	public static final String SESSION_ROLEDWPAS_RIGHTS = "sessionRoleDwpasRights";
	public static final String SESSION_SELF_RIGHTS = "sessionSelfRights";
	public static final String SESSION_DWPAS_MENU_LIST="dwpasMenuList";
	public static final String SESSION_DWMIS_MENU_LIST="dwmisMenuList";
	public static final String SESSION_DWPAS_TEMPLATEID="dwpasTemplateId";
	public static final String SESSION_DWMIS_TEMPLATEID="dwmisTemplateId";
	public static final String SESSION_SELF_REPORT_MENU_LIST = "selfReportMenuList";
	public static final String SESSION_REPORT_MENU_LIST = "reportMenuList";
	public static final String SESSION_REPORT_INFO = "reportInfo";
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)).*"; // 不对匹配该值的访问路径拦截（正则）
	public static ApplicationContext WEB_APP_CONTEXT= null; // 该值会在web容器启动时由WebAppContextListener初始化
	
	// 报表系统
	public final static String SYSTEM_TYPE_REPORT = "3";
	// 经纬仪
	public final static String SYSTEM_TYPE_DWPAS = "2";
	// 瞭望塔
	public final static String SYSTEM_TYPE_DWMIS = "1";
	
	//首页menuId
	public final static String INDEX_MENU_ID="indexMenuId";
	//DBCP链接
	public static Map<String, PoolManager> CONNECTION_POOL_MAP = new HashMap<String, PoolManager>();
    //初始化大盘
	public final static String DA_PAN="0"; 
}
