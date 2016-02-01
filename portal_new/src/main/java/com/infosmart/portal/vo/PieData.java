package com.infosmart.portal.vo;

import java.io.Serializable;

/**
 * 饼图数据
 * 
 * @author infosmart
 * 
 */
public class PieData implements Serializable {
	private String title;

	private String value;

	private String color;

	private String kpicode;

	public String getKpicode() {
		return kpicode;
	}

	public void setKpicode(String kpicode) {
		this.kpicode = kpicode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}