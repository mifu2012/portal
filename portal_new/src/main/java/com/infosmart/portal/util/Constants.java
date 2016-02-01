package com.infosmart.portal.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * 定义一些常量
 * 
 * @author infosmart
 * 
 */
public class Constants {

	/**
	 * 报表查询日期
	 */
	public static final String PORTAL_REPORT_QUERY_DATE = "VAR_PORTAL_REPORT_QUERY_DATE";

	/**
	 * 用户对象
	 */
	public static final String SESSION_USER_INFO = "VAR_SESSION_USER_INFO";

	/**
	 * 用户权限
	 */
	public static final String SESSION_USER_RIGHTS = "VAR_SESSION_USER_RIGHTS";
	/**
	 * 角色权限
	 */
	public static final String SESSION_ROLE_RIGHTS = "VAR_SESSION_ROLE_RIGHTS";

	/**
	 * 报表查询月份
	 */
	public static final String REPORT_REPORT_QUERY_MONTH = "VAR_PORTAL_REPORT_QUERY_MONTH";

	/**
	 * 系统当前查询产品ID
	 */
	public static final String REPORT_REPORT_QUERY_PRODUCT_ID = "REPORT_REPORT_QUERY_PRODUCT_ID";

	public static final String CHART_DATA_SEPERATOR = ",";

	public static final String FUNNEL_SEPARATOR_CHARS = ";";

	public static final String VM_FILE_PATH = "/vm";

	/**
	 * 图表颜色库(系统启动时,初始化)
	 */
	public static List<String> CHART_COLOR_LIST = new ArrayList<String>();

	static {
		final String defaultColor = "#0d8fc1; #ff0f00; #b0e10c; #fecd00; 8BAA5C; 89759A; 4E7DA7; B85D5A; 8B1A1A; FFD700; 00B2EE; 8AAC57;EE7600; RED; BLUE; GREE;CDB79E; 5C5C5C; FF2D2D; 46A3FF; E1E100; C6A300; 28FF28; 9393F; 000079; BF0060; 5B00AE; F7500; A6A600;ACD6FF;4A4AFF; 4DFFFF;FF8000; A6A600; AD5A5A;A3D1D1;8F4586; BB3D00; 8600FF";
		CHART_COLOR_LIST.addAll(Arrays.asList(defaultColor.split(";")));
	}

	public static DecimalFormat amtFmt = new DecimalFormat(
			"##,###,###,###,##0.00");

	public static boolean isMakeData = true;

	public static BigDecimal ONE_HANDREN = new BigDecimal("100.00");

	public static BigDecimal ONE_THOUSAND = new BigDecimal("1000.00");

	public static BigDecimal ZERO = new BigDecimal("0.00");

	public static DateFormat format_day = new SimpleDateFormat("yyyyMMdd");
	public static DateFormat format_month = new SimpleDateFormat("yyyyMM");

	public static DateFormat format_day_display = new SimpleDateFormat(
			"yyyy年MM月dd日");
	public static DateFormat format_month_dispaly = new SimpleDateFormat(
			"yyyy年MM月");

	// 首页柱状图显示用到-求助率正常范围内
	public static final String Help_Rate_Normal = "#80A935";

	// 首页柱状图显示用到- 预警值<求助率<警报值
	public static final String Help_Rate_Pre_Alert = "#FFC200";

	// 首页柱状图显示用到- 求助率>警报值
	public static final String Help_Rate_Alert = "#D53E28";
	// 获取用户的模板ID
	public static final String USER_TEMPLATE_ID = "USER_TEMPLATE_ID";
	
	// 默认趋势图时间跨度
	public static final int TIME_SPAN_STOCK = 24;
}