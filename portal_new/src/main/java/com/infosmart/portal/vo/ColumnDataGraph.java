package com.infosmart.portal.vo;

import java.io.Serializable;

/**
 * 矩形图数据
 * 
 * @author infosmart
 * 
 */
public class ColumnDataGraph implements Serializable {
	private String kpicode;
	private String kpiname;
	private String color;
	private String type;
	private String unit;
	private String tip;
	private String id;

	public String getKpicode() {
		return kpicode;
	}

	public void setKpicode(String kpicode) {
		this.kpicode = kpicode;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getKpiname() {
		return kpiname;
	}

	public void setKpiname(String kpiname) {
		this.kpiname = kpiname;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}