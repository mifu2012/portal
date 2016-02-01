package com.infosmart.portal.util;

import java.util.HashMap;
import java.util.Map;

import com.infosmart.portal.vo.AmMapCode;

public final class MapConst {
	public static Map<String, AmMapCode> CHINA_AREA_CODE_MAP = new HashMap<String, AmMapCode>();
	static {
		CHINA_AREA_CODE_MAP.put("1", new AmMapCode("1", "CN_BJ", "北京市"));
		CHINA_AREA_CODE_MAP.put("2", new AmMapCode("2", "CN_TJ", "天津市"));
		CHINA_AREA_CODE_MAP.put("3", new AmMapCode("3", "CN_HB", "河北省"));
		CHINA_AREA_CODE_MAP.put("4", new AmMapCode("4", "CN_SX", "山西省"));
		CHINA_AREA_CODE_MAP.put("5", new AmMapCode("5", "CN_NM", "内蒙古自治区"));

		CHINA_AREA_CODE_MAP.put("6", new AmMapCode("6", "CN_LN", "辽宁省"));
		CHINA_AREA_CODE_MAP.put("7", new AmMapCode("7", "CN_JL", "吉林省"));
		CHINA_AREA_CODE_MAP.put("8", new AmMapCode("8", "CN_HL", "黑龙江省"));
		CHINA_AREA_CODE_MAP.put("9", new AmMapCode("9", "CN_SH", "上海市"));
		CHINA_AREA_CODE_MAP.put("10", new AmMapCode("10", "CN_JS", "江苏省"));

		CHINA_AREA_CODE_MAP.put("11", new AmMapCode("11", "CN_ZJ", "浙江省"));
		CHINA_AREA_CODE_MAP.put("12", new AmMapCode("12", "CN_AH", "安徽省"));
		CHINA_AREA_CODE_MAP.put("13", new AmMapCode("13", "CN_FJ", "福建省"));
		CHINA_AREA_CODE_MAP.put("14", new AmMapCode("14", "CN_JX", "江西省"));
		CHINA_AREA_CODE_MAP.put("15", new AmMapCode("15", "CN_SD", "山东省"));

		CHINA_AREA_CODE_MAP.put("16", new AmMapCode("16", "CN_HE", "河南省"));
		CHINA_AREA_CODE_MAP.put("17", new AmMapCode("17", "CN_HU", "湖北省"));
		CHINA_AREA_CODE_MAP.put("18", new AmMapCode("18", "CN_HN", "湖南省"));
		CHINA_AREA_CODE_MAP.put("19", new AmMapCode("19", "CN_GD", "广东省"));
		CHINA_AREA_CODE_MAP.put("20", new AmMapCode("20", "CN_GX", "广西壮族自治区"));

		CHINA_AREA_CODE_MAP.put("21", new AmMapCode("21", "CN_HA", "海南省"));
		CHINA_AREA_CODE_MAP.put("22", new AmMapCode("22", "CN_CQ", "重庆市"));
		CHINA_AREA_CODE_MAP.put("23", new AmMapCode("23", "CN_SC", "四川省"));
		CHINA_AREA_CODE_MAP.put("24", new AmMapCode("24", "CN_GZ", "贵州省"));
		CHINA_AREA_CODE_MAP.put("25", new AmMapCode("25", "CN_YN", "云南省"));

		CHINA_AREA_CODE_MAP.put("26", new AmMapCode("26", "CN_XZ", "西藏自治区"));
		CHINA_AREA_CODE_MAP.put("27", new AmMapCode("27", "CN_SA", "陕西省"));
		CHINA_AREA_CODE_MAP.put("28", new AmMapCode("28", "CN_GS", "甘肃省"));
		CHINA_AREA_CODE_MAP.put("29", new AmMapCode("29", "CN_QH", "青海省"));
		CHINA_AREA_CODE_MAP.put("30", new AmMapCode("30", "CN_NX", "宁夏回族自治区"));

		CHINA_AREA_CODE_MAP.put("31", new AmMapCode("31", "CN_XJ", "新疆维吾尔自治区"));
		CHINA_AREA_CODE_MAP.put("32", new AmMapCode("32", "TW", "台湾省"));
		CHINA_AREA_CODE_MAP.put("33", new AmMapCode("33", "HK", "香港特别行政区"));
		CHINA_AREA_CODE_MAP.put("34", new AmMapCode("34", "MO", "澳门特别行政区"));
	}
}
