package com.infosmart.portal.util.dwmis;

public class CoreConstant {
	/**
	 * 前年天数
	 */
	public final static int PREVIOUS_YEAR_COUNT = 2;
	
	/**
	 * 默认天数
	 */
	public final static int PREVIOUS_DAYS_COUNT = 60;

	/**
	 * 前几周数
	 */
	public final static int PREVIOUS_WEEK_COUNT = 4;

	/**
	 * 前几月数
	 */
	public final static int PREVIOUS_MONTH_COUNT = 6;
	/**
	 * 前几天数
	 */
	public final static int PREVIOUS_DAY_COUNT = 7;

	/**
	 * 数据时间周期粒度
	 */
	public final static int KPI_PERIOD = 1000;

	/**
	 * 数据统计方式
	 */
	public final static int KPI_STAT = 2000;

	/**
	 * 绩效类别
	 */
	public final static int KPI_GOAL_TYPE = 3000;

	/**
	 * 指标类型
	 */
	public final static int KPI_TYPE = 4000;

	/**
	 * 指标单位
	 */
	public final static int KPI_UNIT = 5000;

	/**
	 * 指标数量级
	 */
	public final static int KPI_SIZE = 6000;

	/**
	 * 大事记类型
	 */
	public final static int MIS_EVENT = 7000;

	/**
	 * 维度类型
	 */
	public final static int MIS_DMNSN = 8000;

	/**
	 * 一级指标
	 */
	public final static int KPI_LEVEL_TYPE_ONE = 9000;

	/**
	 * 二级指标
	 */
	public final static int KPI_LEVEL_TYPE_TWO = 10000;

	/**
	 * 审核状态
	 */
	public final static int AUDIT_STATUS = 11000;

	/**
	 * 是否可用
	 */
	public final static int IS_USED = 12000;

	public final static int GROUP_13000 = 13000;

	/**
	 * 默认的数字格式化字符串，在MIS_TYPE中对应的名字
	 * 
	 * @author yuwei.zheng
	 * @version 2011年11月7日, AM 10:00:37
	 */
	public final static int MIS_TYPE_DEFAULT_NUMBERFORMAT = 13005;
	/**
	 * 部门分类
	 */
	public final static int DEPT_TYPE = 14000;

	/**
	 * 瞭望塔指标监控 ----按部门分类
	 */
	public final static int DEPT_TYPE_DEPT = 14001;

	/** 变化率方式 ： 一般 */
	public final static int MIS_DATA_GENERAL = 15001;

	/** 变化率方式 ：同比 */
	public final static int MIS_DATA_YEAR_ON_YEAR = 15002;

	/** 变化率方式 ： 环比 */
	public final static int MIS_DATA_ANNULUS_COMPARE = 15003;
	/**
	 * 商户看板
	 */
	public final static int MERCHANTS_BILLBOARDS = 16000;

	/**
	 * 商户看板表格总信息配置
	 */
	public final static int MERCHANTS_TABLE = 16020;

	/**
	 * 用户看板
	 */
	public final static int USER_BILLBOARDS = 17000;

	/**
	 * 跨年ID
	 */
	public final static int NEW_YEAR_TYPE_ID = 18001;
	/**
	 * 默认保留的小数位数
	 */
	public final static int DEFAULT_DIGIT = 2;
	/**
	 * 用于读取KPI Data表的数据时间的默认 延迟时间（小时）
	 */
	public final static int DEFAULT_HOUR_OFFSET = 4;

	/************************************************ 周期period *************************************************/

	/**
	 * 时间周期粒度 天
	 */
	public final static int DAY_PERIOD = 1002;
	/**
	 * 时间周期粒度 周
	 */
	public final static int WEEK_PERIOD = 1003;
	/**
	 * 时间周期粒度 月
	 */
	public final static int MONTH_PERIOD = 1004;
	/**
	 * 时间周期粒度 季
	 */
	public final static int QUARTER_PERIOD = 1005;
	/**
	 * 一天的毫秒时间
	 */
	public final static long ONE_DAY_TMIE = 86400000l;

	/************************************************ 数据统计方式 *************************************************/
	/**
	 * 当期值
	 */
	public final static int STA_CURRENT = 2001;
	/**
	 * 期末值
	 */
	public final static int STA_FINAL = 2002;
	/**
	 * 峰值
	 */
	public final static int STA_PEAK = 2003;
	/**
	 * 谷值
	 */
	public final static int STA_VALLEY = 2004;
	/**
	 * 周日平均
	 */
	public final static int STA_SUNDAY_AVERAGE = 2005;
	/**
	 * 七日平均
	 */
	public final static int STA_SEVEN_DAY_AVERAGE = 2006;

	/**
	 * 月峰值
	 */
	public final static int MONTH_MAX_VALUE = 3001;

	/**
	 * 月谷值
	 */
	public final static int MONTH_MIN_VALUE = 3002;

	/**
	 * 日累积值
	 */
	public final static int DAY_COUNT_VALUE = 3005;

	/**
	 * 七日均值
	 */
	public final static int SEVEN_VALUE = 3007;

	/**
	 * 一年一个值
	 */
	public final static int ONE_YEAR_ONE_VALUE = 3009;

	/**
	 * 用于返回到页面中，标记该数据找不到，但是Double.MIN_VALUE对其有太小的时候给的默认值为0.0
	 */
	public final static double DEFAULT_NUMBER_FOR_AMCHART = 0.0;

	/**
	 * 金融专题详情页面的扩大范围比率
	 */
	public final static double DOMAIN_FINANCE_ADD_AREA = 1.05;

	/**
	 * 没有目标值的时候，给的默认值为0.0
	 */
	public final static double DEFAULT_GOAL_VALUE = 0.0;

	/**
	 * 指标名
	 */
	public final static String KPI_NAME = "0";

	/**
	 * 指标周期没选中
	 */
	public final static String KPI_PERIOD_ZERO = "0";

	/**
	 * 指标周期选中
	 */
	public final static String KPI_PERIOD_ONE = "1";

	/**
	 * 周期为年
	 */
	public final static int YEAR = 0;

	/**
	 * 周期为季
	 */
	public final static int SEASON = 1;

	/**
	 * 周期月
	 */
	public final static int MONTH = 2;

	/**
	 * 周期为周
	 */
	public final static int WEEK = 3;

	/**
	 * 周期为天
	 */
	public final static int DAY = 4;

	/**
	 * 表示时间粒度与大事记的显示无关
	 */
	public final static int NOT_CONSIDER_PERIOD = 0;

	/**
	 * 绩效类型为月值
	 */
	public final static String GOAL_TYPE = "MONTH";

	/***************************************** Amchart 详情分析数据类型 **************************************/
	/**
	 * 普通数据类型
	 * */
	public final static int NOMAL_DATA = 1;

	/**
	 * 归一百分比数据
	 * */
	public final static int ZERO_TO_ONE_PERCENT_DATA = 2;

	/***************************************** 业务展示类型（KPI跟踪、异动分析） **************************************/
	// 业务类型是否被选中，1表示选中，0表示未选
	public final static String KPI_TREND_TYPE_YES = "1";

	public final static String KPI_TREND_TYPE_NO = "0";

	public final static String ALERT_REPORT_TYPE_YES = "1";

	public final static String ALERT_REPORT_TYPE_NO = "0";

	// 各个业务类型在十位码中的序号（位置）
	public final static int KPI_TREND_TYPE_INDEX = 0;

	public final static int ALERT_REPORT_TYPE_INDEX = 1;

	/************************************ 数值的精度 **************************************************************/

	// 保留一位小数
	public final static int DATA_DIGIT_ONE = 1;
	// 保留两位小数
	public final static int DATA_DIGIT_TWO = 2;
	// 保留4位小数 为了获取数据库中的真实数据 wb_yingpf 2011年8月4日15:33:41
	public final static int DATA_DIGIT_F = 4;

	/********************** dashboard·下公司报表和金融看板页面对应的常量 ****************************/
	// 公司报表
	public final static String DASHBOARD_GSBB_TAB = "101";
	// 金融看板
	public final static String DASHBOARD_JRKB_TAB = "102";

	/*-------------------------------------------量天尺------------------------------------*/

	/**
	 * KPI_WIKI我的审核、我的审批页面的单页记录数的大小
	 */
	public final static int MIS_TYPE_KPI_WIKI_MYLIST_PAGESIZE = 13003;
	/**
	 * 指定指标级别的最多层数，如现在默认是4层。
	 */
	public final static int MIS_TYPE_KPI_LEVEL_HIGHEST = 4;

	/** wiki第一版本写入默认信息 */
	public final static String DEFAULT_CREATE_NOTE = "此版本为本指标第一版本";

	/************************************ 量天尺 - 指标数据 **********************************/

	/** 日期排列方式 :升序 */
	public final static int MIS_DATA_ORDER_ASC = 0;

	/** 日期排列方式 :降序 */
	public final static int MIS_DATA_ORDER_DESC = 1;

	/** 指标数据趋势图需要归一化 */
	public final static boolean NEED_PERCENT = true;

	/** 指标数据趋势图不需要归一化 */
	public final static boolean WITHOUT_PERCENT = true;
	/**
	 * 指标数据图表模型在session中的名称
	 * */
	public final static String MIS_SKY_TABLE_MODEL = "MIS_SKY_TABLE_MODEL";
	/**
	 * 百科转到数据kpiId在session中的名称
	 * */
	public final static String MIS_SKY_WIKI_TO_TEMPLATE_KPIID = "MIS_SKY_WIKI_TO_TEMPLATE_KPIID";
	/**
	 * 指标数据 中 tablemodel对应的data在session中的名称
	 * */
	public final static String MIS_SKY_TABLE_DATA = "MIS_SKY_TABLE_DATA";

	/**
	 * 指标数据中父(主)kpi的假kpi code
	 * */
	public final static String MIS_SKY_FAKE_KPI_CODE = "00000000000";

	/**
	 * 指标数据中信息隔离的符号
	 * */
	public final static String MIS_SKY_ID_SEP = "-";

	/**
	 * 指标数据同比
	 * */
	public final static String MIS_SKY_NAME_YEAR = "-同比";

	/**
	 * 指标数据环比
	 * */
	public final static String MIS_SKY_NAME_ANNULUS = "-环比";

	/********************* 组合图 *************************************/
	/**
	 * 组合图展现12个数
	 */
	public final static int BAR_AND_LINE_NUMBER = 12;

	// Field descriptor #11 D
	public static final double DEFAULT_DATA_NOT_FOUND = 4.9E-324;

	// Field descriptor #15 Ljava/lang/String;
	public static final java.lang.String TEMPLATE_KPI_WIKI_ID = "0";

	/**
	 * 今年
	 */
	public final static String FLAG = "THIS_YEAR";

	/**
	 * 用于标识请求是否来自金融专题
	 */
	public final static String DOMAIN_FINANCE = "FINANCE";

	/**
	 * 用于标识请求是否来自金融快捷
	 */
	public final static String DOMAIN_QUICKFINANCE = "QUICKFINANCE";

	/**
	 * 默认保留的小数位数 金融专题
	 */
	public final static int DEFAULT_DIGIT_FINANCE = 2;

}
