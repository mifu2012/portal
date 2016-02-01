package com.infosmart.po.report;

import java.util.List;
import java.util.Map;

public class PieChart {
	private String type;

	private String name;

	private List<Map<Object, Object>> data;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Map<Object, Object>> getData() {
		return data;
	}

	public void setData(List<Map<Object, Object>> data) {
		this.data = data;
	}


}
