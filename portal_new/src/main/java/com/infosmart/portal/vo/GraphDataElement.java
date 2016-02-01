package com.infosmart.portal.vo;

/**
 * 形成线的数据单元
 * 
 * @author wb-songxd
 */
public class GraphDataElement {

	/**
	 * 数据时间 数据格式为：yyyy-MM-dd
	 */
	private String valueDate;

	/** 数据值 */
	private String value;

	// ================setter and getter==================================

	public GraphDataElement()
	{
		
	}
	public GraphDataElement(String valueDate, String value) {
		super();
		this.valueDate = valueDate;
		this.value = value;
	}

	public String getValueDate() {
		return valueDate;
	}

	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}