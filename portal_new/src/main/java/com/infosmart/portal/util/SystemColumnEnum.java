package com.infosmart.portal.util;

/**
 * 系统栏目枚举
 * 
 * @author infosmart
 * 
 */
public enum SystemColumnEnum {

	// *****************************首页
	/**
	 * 暂定为用户求助预警图(无子产品,仪表图)
	 */
	INDEX_ALERT_DASHBOARD_CHART("INDEX_ALERT_DASHBOARD_CHART", "用户咨询情况_无子产品"), // 如果没有子产品,则公用指标为CKC0001
	/**
	 * 有子产品线_暂定为用户求助预警图_分母:如果有子产品，计算产品线的求助率 = sum（产品线下的产品指标【用户求助率_分子】值）/
	 * sum（产品线下的产品指标【用户求助率_分母】值）
	 */
	INDEX_ALERT_DENOMINATOR_DASHBOARD_CHART(
			"INDEX_ALERT_DENOMINATOR_DASHBOARD_CHART", "用户咨询情况_有子产品线_分母"),
	/**
	 * 有子产品线_暂定为用户求助预警图_分子:如果有子产品，计算产品线的求助率 = sum（产品线下的产品指标【用户求助率_分子】值）/
	 * sum（产品线下的产品指标【用户求助率_分母】值）
	 */
	INDEX_ALERT_MOLECULES_DASHBOARD_CHART(
			"INDEX_ALERT_MOLECULES_DASHBOARD_CHART", "用户咨询情况_有子产品线_分子"),
	/**
	 * 用户咨询风向标(关键字飘窗) 即用户咨询前十的关键字
	 */
	INDEX_TOP10_KEYWORD_LIST("INDEX_TOP10_KEYWORD_LIST", "用户风向标"),
	/**
	 * 业务笔数监控(矩形图)
	 */
	INDEX_RECTANGLE_CHART_COUNTS("INDEX_RECTANGLE_CHART_COUNTS", "业务笔数监控"),
	/**
	 * 产品全图(饼图)
	 */
	INDEX_PRODUCT_PIE_CHART("INDEX_PRODUCT_PIE_CHART", "产品全图"),
	// 今日销量
	INDEX_PRODUCT_PIE_CHART_TODAY_SALE("INDEX_PRODUCT_PIE_CHART_TODAY_SALE",
			"今日销量"),
	// 今日目标销量
	INDEX_PRODUCT_PIE_CHART_TODAY_GOAL_SALE(
			"INDEX_PRODUCT_PIE_CHART_TODAY_GOAL_SALE", "今日目标销量"),

	// 月销量
	INDEX_PRODUCT_PIE_CHART_MONTH_SALE("INDEX_PRODUCT_PIE_CHART_MONTH_SALE",
			"产品全图-月销量"),
	// 月目标销量
	INDEX_PRODUCT_PIE_CHART_MONTH_GOAL_SALE(
			"INDEX_PRODUCT_PIE_CHART_MONTH_GOAL_SALE", "产品全图-月目标销量"),

	// 年销量
	INDEX_PRODUCT_PIE_CHART_YEAR_SALE("INDEX_PRODUCT_PIE_CHART_YEAR_SALE",
			"产品全图-年销量"),
	// 年目标销量
	INDEX_PRODUCT_PIE_CHART_YEAR_GOAL_SALE(
			"INDEX_PRODUCT_PIE_CHART_YEAR_GOAL_SALE", "产品全图-年目标销量"),

	// 总用户数
	INDEX_PRODUCT_PIE_CHART_TOTAL_USER_COUNT(
			"INDEX_PRODUCT_PIE_CHART_TOTAL_USER_COUNT", "总用户数-总用户数"),
	// 总销量数
	INDEX_PRODUCT_PIE_CHART_TOTAL_SALE_AMOUNT(
			"INDEX_PRODUCT_PIE_CHART_TOTAL_SALE_AMOUNT", "总销量数-总销量数"),
	/**
	 * 用户数统计列表
	 */
	INDEX_USER_COUNT_STAT_LIST("INDEX_USER_COUNT_STAT_LIST", "用户数统计"),
	/**
	 * 产品聚焦(产品大事列表)
	 */
	INDEX_PRODUCT_EVENT_LIST("INDEX_PRODUCT_IMPORTANT_EVENT_LIST", "产品聚焦"),

	// *********************************产品龙虎榜

	/**
	 * 平台交叉分析??????????????????????????
	 */
	LHB_CROSS_PLATFORM("LHB_CROSS_PLATFORM", "龙虎榜平台交叉"),
	/**
	 * 产品排行??????????????????????????????
	 */
	LHB_PRODUCT_RANKING("LHB_PRODUCT_RANKING", "龙虎榜产品排行"),
	// *********************************产品健康度
	RADAR_PRODUCT_HEALTH("RADAR_PRODUCT_HEALTH", "产品健康度"),
	// *********************************产品发展,按日
	// ########产品使用量(业务量)增长趋势(趋势图对应的KPI_CODE取下面两通用指标得到对应的KPI_CODE)
	/**
	 * 产品使用量增长趋势_业务量_日指标(千万)
	 */
	PRODUCT_TOTAL_TRANSACTIONS_AMOUNT("PRODUCT_TOTAL_TRANSACTIONS_AMOUNT",
			"产品使用量增长趋势_业务量_日指标"),
	/**
	 * 产品使用量增长趋势_业务笔数_日指标(千笔)
	 */
	PRODUCT_TOTAL_TRANSACTIONS_TIMES("PRODUCT_TOTAL_TRANSACTIONS_TIMES",
			"产品使用量增长趋势_业务笔数_日指标"),
	/**
	 * 产品使用量增长趋势图
	 */
	// PRODUCT_USED_AMOUNT_RISE_THREAD_CHART("PRODUCT_USED_AMOUNT_RISE_THREAD_CHART",
	// "产品使用量增长趋势"),
	// ###############产品用户(用户数)(趋势图对应的KPI_CODE取下面两通用指标得到对应的KPI_CODE)
	/**
	 * 产品使用用户数趋势_产品用户数_日指标(当日)
	 */
	PRODUCT_DAY_USER_COUNT("PRODUCT_DAY_USER_COUNT", "产品使用用户数趋势_产品用户数_日指标"),
	/**
	 * 产品使用用户数趋势_累计产品用户数_日指标
	 */
	PRODUCT_TOTAL_USER_COUNT("PRODUCT_TOTAL_USER_COUNT",
			"产品使用用户数趋势_累计产品用户数_日指标"),
	/**
	 * 产品使用用户趋势图
	 */
	// PRODUCT_USER_RISE_THREAD_CHART("PRODUCT_USER_RISE_THREAD_CHART",
	// "产品使用用户趋势"),
	// ##################户均贡献变化趋势(趋势图对应的KPI_CODE取下面两通用指标得到对应的KPI_CODE)
	/**
	 * 户均贡献变化趋势_户均消费金额_日指标
	 */
	PRODUCT_PER_USER_ADMOUNT("PRODUCT_PER_USER_ADMOUNT", "户均贡献变化趋势_户均消费金额_日指标"),
	/**
	 * 户均贡献变化趋势_户均消费笔数_日指标(次数)
	 */
	PRODUCT_PER_USER_TIMES("PRODUCT_PER_USER_TIMES", "户均贡献变化趋势_户均消费笔数_日指标"),

	/**
	 * 户均贡献变化趋势图
	 */
	// PRODUCT_PER_USER_AMOUNT_RISE_THREAD_CHART("PRODUCT_PER_USER_AMOUNT_RISE_THREAD_CHART",
	// "户均贡献变化趋势"),

	// *********************************产品发展,按月
	// ##产品使用量增长趋势
	PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_NEW(
			"PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_NEW", "产品使用量增长趋势_业务量_月指标"),
	// 产品使用业务笔数
	PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_NEW(
			"PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_NEW", "产品使用量增长趋势_业务笔数_月指标"),
	// ##产品使用用户增长趋势
	PRODUCT_MONTH_USER_COUNT_NEW("PRODUCT_MONTH_USER_COUNT_NEW",
			"产品使用量增长趋势_产品用户数_月指标"),
	// 累计产品用户数
	PRODUCT_MONTH_TOTAL_USER_COUNT_NEW("PRODUCT_MONTH_TOTAL_USER_COUNT_NEW",
			"产品使用量增长趋势_累计产品用户数_月指标"),
	// 户均金额
	PRODUCT_MONTH_PER_USER_ADMOUNT_NEW("PRODUCT_MONTH_PER_USER_ADMOUNT_NEW",
			"户均贡献变化趋势_户均金额_月指标"),
	// 户均笔数
	PRODUCT_MONTH_PER_USER_TIMES_NEW("PRODUCT_MONTH_PER_USER_TIMES_NEW",
			"户均贡献变化趋势_户均笔数_月指标"),
	// ##############产品使用量增长趋势
	/**
	 * 产品使用量增长趋势_业务量_月指标
	 */
	PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT(
			"PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT", "产品使用量增长趋势_业务量_月指标"),
	// 上月值_add
	PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_LAST_MONTH(
			"PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_LAST_MONTH",
			"产品使用量增长趋势_上月业务量_月指标"),
	// 环比_add
	PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_LINK_RELATIVE(
			"PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_LINK_RELATIVE",
			"产品使用量增长趋势_业务量环比_月指标"),
	// 去年同期值_add
	PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_SAME_PERIOD_LAST_YEAR(
			"PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_SAME_PERIOD_LAST_YEAR",
			"产品使用量增长趋势_去年同期值_月指标"),
	// 同比_add
	PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_COMPARED_TO_THE_SAME_PERIOD(
			"PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_COMPARED_TO_THE_SAME_PERIOD",
			"产品使用量增长趋势_业务量同比_月指标"),
	/**
	 * 产品使用量增长趋势_业务笔数_月指标
	 */
	PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES(
			"PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES", "产品使用量增长趋势_业务笔数_月指标"),
	// 上月值_add
	PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_LAST_MONTH(
			"PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_LAST_MONTH",
			"产品使用量增长趋势_上月业务笔数_月指标"),
	// 环比_add
	PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_LINK_RELATIVE(
			"PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_LINK_RELATIVE",
			"产品使用量增长趋势_业务笔数环比_月指标"),
	// 去年同期值_add
	PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_SAME_PERIOD_LAST_YEAR(
			"PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_SAME_PERIOD_LAST_YEAR",
			"产品使用量增长趋势_去年同期值_月指标"),
	// 同比_add
	PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_COMPARED_TO_THE_SAME_PERIOD(
			"PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_COMPARED_TO_THE_SAME_PERIOD",
			"产品使用量增长趋势_业务笔数同比_月指标"),
	// ##############产品使用用户趋势
	/**
	 * 产品使用量增长趋势_产品用户数_月指标
	 */
	PRODUCT_MONTH_USER_COUNT("PRODUCT_MONTH_USER_COUNT", "产品使用量增长趋势_产品用户数_月指标"),
	// 上月值_add
	PRODUCT_MONTH_USER_COUNT_LAST_MONTH("PRODUCT_MONTH_USER_COUNT_LAST_MONTH",
			"产品使用量增长趋势_上月产品用户数_月指标"),
	// 环比_add
	PRODUCT_MONTH_USER_COUNT_TIMES_LINK_RELATIVE(
			"PRODUCT_MONTH_USER_COUNT_TIMES_LINK_RELATIVE",
			"产品使用量增长趋势_产品用户数环比_月指标"),
	// 去年同期值_add
	PRODUCT_MONTH_USER_COUNT_SAME_PERIOD_LAST_YEAR(
			"PRODUCT_MONTH_USER_COUNT_SAME_PERIOD_LAST_YEAR",
			"产品使用量增长趋势_去年同期值_月指标"),
	// 同比_add
	PRODUCT_MONTH_USER_COUNT_COMPARED_TO_THE_SAME_PERIOD(
			"PRODUCT_MONTH_USER_COUNT_COMPARED_TO_THE_SAME_PERIOD",
			"产品使用量增长趋势_产品用户数同比_月指标"),
	/**
	 * 产品使用量增长趋势_累计产品用户数_月指标
	 */
	PRODUCT_MONTH_TOTAL_USER_COUNT("PRODUCT_MONTH_TOTAL_USER_COUNT",
			"产品使用量增长趋势_累计产品用户数_月指标"),
	// 上月值_add
	PRODUCT_MONTH_TOTAL_USER_COUNT_LAST_MONTH(
			"PRODUCT_MONTH_TOTAL_USER_COUNT_LAST_MONTH",
			"产品使用量增长趋势_上月累计产品用户数_月指标"),
	// 环比_add
	PRODUCT_MONTH_TOTAL_USER_COUNT_LINK_RELATIVE(
			"PRODUCT_MONTH_TOTAL_USER_COUNT_LINK_RELATIVE",
			"产品使用量增长趋势_累计产品用户数环比_月指标"),
	// 去年同期值_add
	PRODUCT_MONTH_TOTAL_USER_COUNT_SAME_PERIOD_LAST_YEAR(
			"PRODUCT_MONTH_TOTAL_USER_COUNT_SAME_PERIOD_LAST_YEAR",
			"产品使用量增长趋势_去年同期值_月指标"),
	// 同比_add
	PRODUCT_MONTH_TOTAL_USER_COUNT_COMPARED_TO_THE_SAME_PERIOD(
			"PRODUCT_MONTH_TOTAL_USER_COUNT_COMPARED_TO_THE_SAME_PERIOD",
			"产品使用量增长趋势_累计产品用户数同比_月指标"),
	// ##############户均贡献变化趋势
	/**
	 * 户均贡献变化趋势_户均金额_月指标
	 */
	PRODUCT_MONTH_PER_USER_ADMOUNT("PRODUCT_MONTH_PER_USER_ADMOUNT",
			"户均贡献变化趋势_户均金额_月指标"),
	// 上月值_add
	PRODUCT_MONTH_PER_USER_ADMOUNT_LAST_MONTH(
			"PRODUCT_MONTH_PER_USER_ADMOUNT_LAST_MONTH", "户均贡献变化趋势_上月户均金额_月指标"),
	// 环比_add
	PRODUCT_MONTH_PER_USER_ADMOUNT_LINK_RELATIVE(
			"PRODUCT_MONTH_PER_USER_ADMOUNT_LINK_RELATIVE",
			"户均贡献变化趋势_户均金额环比_月指标"),
	// 去年同期值_add
	PRODUCT_MONTH_PER_USER_ADMOUNT_SAME_PERIOD_LAST_YEAR(
			"PRODUCT_MONTH_PER_USER_ADMOUNT_SAME_PERIOD_LAST_YEAR",
			"户均贡献变化趋势_去年同期值_月指标"),
	// 同比_add
	PRODUCT_MONTH_PER_USER_ADMOUNT_COMPARED_TO_THE_SAME_PERIOD(
			"PRODUCT_MONTH_PER_USER_ADMOUNT_COMPARED_TO_THE_SAME_PERIOD",
			"户均贡献变化趋势_户均金额同比_月指标"),
	/**
	 * 户均贡献变化趋势_户均笔数_月指标
	 */
	PRODUCT_MONTH_PER_USER_TIMES("PRODUCT_MONTH_PER_USER_TIMES",
			"户均贡献变化趋势_户均笔数_月指标"),
	// 上月值_add
	PRODUCT_MONTH_PER_USER_TIMES_LAST_MONTH(
			"PRODUCT_MONTH_PER_USER_TIMES_LAST_MONTH", "户均贡献变化趋势_上月户均笔数_月指标"),
	// 环比_add
	PRODUCT_MONTH_PER_USER_TIMES_LINK_RELATIVE(
			"PRODUCT_MONTH_PER_USER_TIMES_LINK_RELATIVE", "户均贡献变化趋势_户均笔数环比_月指标"),
	// 去年同期值_add
	PRODUCT_MONTH_PER_USER_TIMES_SAME_PERIOD_LAST_YEAR(
			"PRODUCT_MONTH_PER_USER_TIMES_SAME_PERIOD_LAST_YEAR",
			"户均贡献变化趋势_去年同期值_月指标"),
	// 同比_add
	PRODUCT_MONTH_PER_USER_TIMES_COMPARED_TO_THE_SAME_PERIOD(
			"PRODUCT_MONTH_PER_USER_TIMES_COMPARED_TO_THE_SAME_PERIOD",
			"户均贡献变化趋势_户均笔数同比_月指标"),

	// ***********************************用户留存
	/**
	 * 全年用户产品构造情况(大饼图)
	 */
	PRODUCT_HEALTH_FULL_YEAR_USER_KEEP_PIE_CHART(
			"PRODUCT_HEALTH_FULL_YEAR_USER_KEEP_PIE_CHART", "全年用户产品构造情况"),
	// ###########上月新用户
	/**
	 * 上月新用户数
	 */
	PRODUCT_HEALTH_LAST_MONTH_NEW_USER_VALUE(
			"PRODUCT_HEALTH_LAST_MONTH_NEW_USER_VALUE", "上月新用户数"),
	/**
	 * 上月新用户留存率
	 */
	PRODUCT_HEALTH_LAST_MONTH_NEW_USER_RATE_PIE_CHART(
			"PRODUCT_HEALTH_LAST_MONTH_NEW_USER_RATE_PIE_CHART", "上月新用户留存率"),
	// ###########上月老用户
	/**
	 * 上月老用户数
	 */
	PRODUCT_HEALTH_LAST_MONTH_OLD_USER_VALUE(
			"PRODUCT_HEALTH_LAST_MONTH_OLD_USER_VALUE", "上月老用户数"),
	/**
	 * 上月老用户数留存率
	 */
	PRODUCT_HEALTH_LAST_MONTH_OLD_USER_RATE_PIE_CHART(
			"PRODUCT_HEALTH_LAST_MONTH_OLD_USER_RATE_PIE_CHART", "上月老用户数留存率"),

	// ###########上月休眠用户
	/**
	 * 上月休眠用户数
	 */
	PRODUCT_HEALTH_LAST_MONTH_SLEEP_USER_VALUE(
			"PRODUCT_HEALTH_LAST_MONTH_SLEEP_USER_VALUE", "上月休眠用户数"),
	/**
	 * 上月休眠用户复活率
	 */
	PRODUCT_HEALTH_LAST_MONTH_SLEEP_USER_RATE_PIE_CHART(
			"PRODUCT_HEALTH_LAST_MONTH_SLEEP_USER_RATE_PIE_CHART", "上月休眠用户复活率"),

	// ###########上月累计流失用户
	/**
	 * 上月累计流失用户数
	 */
	PRODUCT_HEALTH_LAST_MONTH_TOTAL_LOSE_USER_VALUE(
			"PRODUCT_HEALTH_LAST_MONTH_TOTAL_LOSE_USER_VALUE", "上月累计流失用户数"),
	/**
	 * 上月累计流失用户率
	 */
	PRODUCT_HEALTH_LAST_MONTH_TOTAL_LOSE_USER_RATE_PIE_CHART(
			"PRODUCT_HEALTH_LAST_MONTH_TOTAL_LOSE_USER_RATE_PIE_CHART",
			"上月累计流失用户率"),
	// 趋势率
	/**
	 * 产品健康趋势图
	 */
	PRODUCT_HEALTH_USER_TREND_CHART("PRODUCT_HEALTH_USER_TREND_CHART",
			"细分用户复活或留存趋势"),

	// ***********************************用户体验
	/**
	 * 总体引入会员数
	 */
	KPI_COM_MEMBER_TOTAL_IN("KPI_COM_MEMBER_TOTAL_IN", "总体引入会员数"),
	/**
	 * 总体尝试使用数
	 */
	KPI_COM_MEMBER_TOTAL_TRY("KPI_COM_MEMBER_TOTAL_TRY", "总体尝试使用数"),
	/**
	 * 总体成功使用数
	 */
	KPI_COM_MEMBER_TOTAL_SUC("KPI_COM_MEMBER_TOTAL_SUC", "总体成功使用数"),
	/**
	 * 新老会员总体会员数
	 */
	KPI_COM_MEMBER_TOTAL_OLDANDNEW("KPI_COM_MEMBER_TOTAL_OLDANDNEW",
			"新老会员总体会员数"),
	/**
	 * 老会员成功使用数
	 */
	KPI_COM_MEMBER_OLD_TRY("KPI_COM_MEMBER_OLD_TRY", "老会员成功使用数"),
	/**
	 * 老会员成功使用数
	 */
	KPI_COM_MEMBER_OLD_SUC("KPI_COM_MEMBER_OLD_SUC", "老会员成功使用数"),
	/**
	 * 新会员尝试使用数
	 */
	KPI_COM_MEMBER_NEW_TRY("KPI_COM_MEMBER_NEW_TRY", "新会员尝试使用数"),
	/**
	 * 新会员成功使用数
	 */
	KPI_COM_MEMBER_NEW_SUC("KPI_COM_MEMBER_NEW_SUC", "新会员成功使用数"),

	// ***********************************用户声音
	/**
	 * 求助率趋势图 USER_VOICE_USER_HELP_THREAD_CHART
	 */
	HELP_RISE_THREAD_DAY_CHART("HELP_RISE_THREAD_DAY_CHART", "用户声音_日求助率趋势"), HELP_RISE_THREAD_MONTH_CHART(
			"HELP_RISE_THREAD_MONTH_CHART", "用户声音_日求助率趋势"),
	/**
	 * 求助用户构成饼图_日指标
	 */
	ASK_USER_CONSTITUTE_DAY_PIE_CHART("ASK_USER_CONSTITUTE_DAY_PIE_CHART",
			"求助用户构成_日指标"),
	/**
	 * 求助用户构成饼图_月指标
	 */
	ASK_USER_CONSTITUTE_MONTH_PIE_CHART("ASK_USER_CONSTITUTE_MONTH_PIE_CHART",
			"求助用户构成_月指标"),

	// ***********************************用户特征
	/**
	 * 使用特征_深度活跃会员占比趋势图
	 */
	USER_CHARACTER_DEEP_USER_RATE_THREAD_CHART(
			"USER_CHARACTER_DEEP_USER_RATE_THREAD_CHART", "使用特征_深度活跃会员占比趋势"),
	/**
	 * 使用特征_深度活跃会员占比大盘趋势图
	 */
	USER_CHARACTER_DEEP_USER_RATE_OVERALL_THREAD_CHART(
			"USER_CHARACTER_DEEP_USER_RATE_OVERALL_THREAD_CHART",
			"使用特征_深度活跃会员占比趋势"),
	/**
	 * 使用特征_女用户数占比大盘
	 */
	USER_CHARACTER_FEMALE_USER_OVERALL_PIE_CHART(
			"USER_CHARACTER_FEMALE_USER_OVERALL_PIE_CHART", "使用特征_女用户数占比大盘"),

	/**
	 * 使用特征_男用户数占比大盘
	 */
	USER_CHARACTER_MAN_USER_OVERALL_PIE_CHART(
			"USER_CHARACTER_MAN_USER_OVERALL_PIE_CHART", "使用特征_男用户数占比大盘"),

	/**
	 * 使用特征_未使用过无线用户数占比大盘
	 */
	USER_CHARACTER_NO_USE_WIRELESS_OVERALL_PIE_CHART(
			"USER_CHARACTER_NO_USE_WIRELESS_OVERALL_PIE_CHART",
			"使用特征_未使用过无线用户数占比大盘"),

	/**
	 * 使用特征_使用过无线用户数占比大盘
	 */
	USER_CHARACTER_USED_WIRELESS_OVERALL_PIE_CHART(
			"USER_CHARACTER_USED_WIRELESS_OVERALL_PIE_CHART",
			"使用特征_使用过无线用户数占比大盘"),

	/**
	 * 使用特征_活跃用户大盘
	 */
	USER_CHARACTER_ACTIVE_USER_WIRELESS_OVERALL_PIE_CHART(
			"USER_CHARACTER_ACTIVE_USER_WIRELESS_OVERALL_PIE_CHART",
			"使用特征_活跃用户大盘"),

	/**
	 * 使用特征_女用户数占比
	 */
	USER_CHARACTER_FEMALE_USER_RATE_PIE_CHART(
			"USER_CHARACTER_FEMALE_USER_RATE_PIE_CHART", "使用特征_女用户数占比"),

	/**
	 * 使用特征_男用户数占比
	 */
	USER_CHARACTER_MAN_USER_RATE_PIE_CHART(
			"USER_CHARACTER_MAN_USER_RATE_PIE_CHART", "使用特征_男用户数占比"),

	/**
	 * 使用特征_未使用过无线用户数占比
	 */
	USER_CHARACTER_NO_USE_WIRELESS_RATE_PIE_CHART(
			"USER_CHARACTER_NO_USE_WIRELESS_RATE_PIE_CHART", "使用特征_未使用过无线用户数占比"),

	/**
	 * 使用特征_使用过无线用户数占比
	 */
	USER_CHARACTER_USED_WIRELESS_RATE_PIE_CHART(
			"USER_CHARACTER_USED_WIRELESS_RATE_PIE_CHART", "使用特征_使用过无线用户数占比"),
	/**
	 * 使用特征_活跃会员数
	 */
	USER_CHARACTER_ACTIVE_USER_PIE_CHART(
			"USER_CHARACTER_ACTIVE_USER_PIE_CHART", "使用特征_活跃会员数");

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

	// ***********************************场景交叉
	/**
	 * 产品指标分析???????????????????
	 */

	/**
	 * 通用指标code
	 */
	private String columnCode;

	/**
	 * 描述信息
	 */
	private String message;

	private SystemColumnEnum(String columnCode, String message,
			String columnName) {
		this.columnCode = columnCode;
		this.message = message;
		this.columnName = columnName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 饼图对应的颜色
	 */
	private String columnName;

	private SystemColumnEnum(String columnCode, String columnName) {
		this.columnCode = columnCode;
		this.columnName = columnName;
	}

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}