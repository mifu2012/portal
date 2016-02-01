package com.infosmart.portal.util.dwpas;

/**
 * 产品龙虎榜列名与样式关系枚举(暂时引用)
 * 后期通过后台配置
 * 
 * @author gentai.huang
 * 
 */
public enum ProdRankRelationEnum {

	/** 淘宝总数 */
	TAOBAO("jt", "使用过WAP"),

	/** 商户总数 */
	OUT("js", "使用过主站"),

	/** 站内总数 */
	ALIPAY("jz", "使用过合作平台"),

	/** 仅使用淘宝 */
	ONLYTAOBAO("jt", "仅使用过WAP"),

	/** 仅使用商户 */
	ONLYOUT("js", "仅使用过主站"),

	/** 仅使用站内 */
	ONLYALIPAY("jz", "仅使用过合作平台"),

	/** WAP + 主站 */
	TAOBAOANDOUT("jt js", "仅使用过WAP和主站"),

	/** WAP + 合作平台 */
	TAOBAOANDALIPAY("jt jz", "仅使用过WAP和合作平台"),

	/** 主站 + 合作平台 */
	ALIPAYANDOUT("js jz", "仅使用过主站和合作平台"),

	/** WAP + 主站 + 合作平台 */
	ALL("jt js jz", "使用过全部平台");

	/** 样式编码 */
	private String code;

	/** 行名称 */
	private String message;

	private ProdRankRelationEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * Getter method for property <tt>code</tt>.
	 * 
	 * @return property value of code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Setter method for property <tt>code</tt>.
	 * 
	 * @param code
	 *            value to be assigned to property code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Getter method for property <tt>message</tt>.
	 * 
	 * @return property value of message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter method for property <tt>message</tt>.
	 * 
	 * @param message
	 *            value to be assigned to property message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
