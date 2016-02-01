package com.infosmart.portal.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统模块信息
 * 
 * @author infosmart
 * 
 */
public class SystemModuleInfo {

	public static Map<String, String> SYSTEM_MODULE_URL_MAP = new HashMap<String, String>();

	static {
		/**
		 * 产品全图
		 */
		SYSTEM_MODULE_URL_MAP.put("INDEX_PRODUCT_CHART",
				"/index/showAllPlatFormUser");
		/**
		 * 风向标
		 */
		SYSTEM_MODULE_URL_MAP.put("INDEX_USER_HELP_KEYWORD_LIST",
				"/index/showAskWindVane");
		/**
		 * 业务笔数监控
		 */
		SYSTEM_MODULE_URL_MAP.put("INDEX_RECTANGLE_CHART",
				"/index/prodTransMonitorChart");
		/**
		 * 用户数统计
		 */
		SYSTEM_MODULE_URL_MAP.put("INDEX_USER_COUNT_TABLE",
				"/index/userCountStatistics");
		/**
		 * 用户咨询情况:
		 */
		SYSTEM_MODULE_URL_MAP.put("INDEX_ALERT_DASHBOARD_CHART",
				"/index/askBashBoardChart");
		/**
		 * 大事件
		 */
		SYSTEM_MODULE_URL_MAP.put("INDEX_IMPORTANT_EVENT_LIST",
				"/index/listLeastBigEvent");
	}

	// 首页
	/**
	 * 用户咨询情况
	 */
	public static final String INDEX_USER_HELP_CHART = "INDEX_ALERT_DASHBOARD_CHART";
	/**
	 * 业务咨询风标
	 */
	public static final String INDEX_USER_HELP_KEYWORD_LIST = "INDEX_USER_HELP_KEYWORD_LIST";
	/**
	 * 业务笔数监控
	 */
	public static final String INDEX_RECTANGLE_CHART = "INDEX_RECTANGLE_CHART";
	/**
	 * 产品全图
	 */
	public static final String INDEX_PRODUCT_CHART = "INDEX_PRODUCT_CHART";
	/**
	 * 用户数统计
	 */
	public static final String INDEX_USER_COUNT_TABLE = "INDEX_USER_COUNT_TABLE";
	/**
	 * 产品聚焦
	 */
	public static final String INDEX_IMPORTANT_EVENT_LIST = "INDEX_IMPORTANT_EVENT_LIST";
	// 产品龙虎榜
	/**
	 * 平台交叉分析
	 */
	public static final String LHB_PLATFORM_CROSS_CHART = "LHB_PLATFORM_CROSS_CHART";
	/**
	 * 产品排行
	 */
	public static final String LHB_PRODUCT_RANKLIST = "LHB_PRODUCT_RANKLIST";
	// 产品健康度
	/**
	 * 产品健康度
	 */
	public static final String PRODUCT_HEALTH_SIX_DIMENSIONS = "PRODUCT_HEALTH_SIX_DIMENSIONS";
	/**
	 * 产品健康度趋势
	 */
	public static final String PRODUCT_HEALTH_SIX_DIMENSIONS_THREAD_CHART = "PRODUCT_HEALTH_SIX_DIMENSIONS_THREAD_CHART";
	// 产品发展
	/**
	 * 产品使用量增长趋势
	 */
	public static final String PRODUCT_DEVELOP_AMOUNT_RISE_THREAD_CHART = "PRODUCT_DEVELOP_AMOUNT_RISE_THREAD_CHART";
	/**
	 * 产品使用用户趋势
	 */
	public static final String PRODUCT_DEVELOP_USER_RISE_THREAD_CHART = "PRODUCT_DEVELOP_USER_RISE_THREAD_CHART";
	/**
	 * 户均贡献变化趋势
	 */
	public static final String PRODUCT_DEVELOP_PER_RISE_THREAD_CHART = "PRODUCT_DEVELOP_PER_RISE_THREAD_CHART";
	// 用户存留
	/**
	 * 全年用户产品构造情况
	 */
	public static final String USER_KEEP_YEAR_PROD_DISTRIBUTION_CHART = "USER_KEEP_YEAR_PROD_DISTRIBUTION_CHART";
	/**
	 * 上月产品用户留存
	 */
	public static final String USER_KEEP_LAST_MONTH_PROD_DISTRIBUTION_CHART = "USER_KEEP_LAST_MONTH_PROD_DISTRIBUTION_CHART";
	/**
	 * 细分用户复活或留存趋势
	 */
	public static final String USER_KEEP_USER_THREAD_CHART = "USER_KEEP_USER_THREAD_CHART";
	// 用户体验
	/**
	 * 会员整体转换情况
	 */
	public static final String USER_EXPERIENCE_USER_CONVERT_CHART = "USER_EXPERIENCE_USER_CONVERT_CHART";
	/**
	 * 会员整体转换情况大类名称
	 */
	public static final String USER_EXPERIENCE_USER_CONVERT_CHART_KIND = "USER_EXPERIENCE_USER_CONVERT_CHART_KIND";
	/**
	 * 新老用户整体转换情况
	 */
	public static final String USER_EXPERIENCE__NEW_OLD_USER_CONVERT_CHART = "USER_EXPERIENCE__NEW_OLD_USER_CONVERT_CHART";
	/**
	 * 新老用户整体转换情况大类名称
	 * 
	 */
	public static final String USER_EXPERIENCE__NEW_OLD_USER_CONVERT_KIND = "USER_EXPERIENCE__NEW_OLD_USER_CONVERT_KIND";
	/**
	 * 用户体验渠道引入情况
	 */
	public static final String USER_EXPERIENCE_CHANNEL_INTO_SITUATION = "USER_EXPERIENCE_CHANNEL_INTO_SITUATION";
	// 用户声音
	/**
	 * 求助率趋势
	 */
	public static final String USER_VOICE_USER_HELP_THREAD_CHART = "USER_VOICE_USER_HELP_THREAD_CHART";
	/**
	 * 求助用户构成与问题TOP10
	 */
	public static final String USER_VOICE_USER_HELP_PIE_CHART = "USER_VOICE_USER_HELP_PIE_CHART";
	// 用户特征
	/**
	 * 深度活跃会员占比趋势
	 */
	public static final String USER_FEATURE_DEEP_USER_RATE_CHART = "USER_FEATURE_DEEP_USER_RATE_CHART";
	/**
	 * 使用特征
	 */
	public static final String USER_FEATURE_USER_USED_CHART = "USER_FEATURE_USER_USED_CHART";
	/**
	 * 使用特征对应的大类名称
	 * 
	 */
	public static final String USER_FEATURE_USER_USED_KIND = "USER_FEATURE_USER_USED_KIND";
	/**
	 * 人口特征
	 */
	public static final String USER_FEATURE_USER_AGE_CHART = "USER_FEATURE_USER_AGE_CHART";
	/**
	 * 人口特征对应的大类名称
	 */
	public static final String USER_FEATURE_USER_AGE_KIND = "USER_FEATURE_USER_AGE_KIND";
	/**
	 * 地区分布
	 */
	public static final String USER_FEATURE_USER_AREA_CHART = "USER_FEATURE_USER_AREA_CHART";
	// 场景交叉
	/**
	 * 场景交叉射线图
	 */
	public static final String PROD_CROSS_CHART = "PROD_CROSS_CHART";
	/**
	 * 场景交叉情况
	 */
	public static final String PROD_CROSS_INFO = "PROD_CROSS_INFO";
	// 产品指标分析
	/**
	 * 投入监控
	 */
	public static final String INPUT_MONITORING = "INPUT_MONITORING";
}
