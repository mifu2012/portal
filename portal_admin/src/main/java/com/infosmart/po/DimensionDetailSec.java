package com.infosmart.po;

/**
 * 报表二级纬度
 * 
 * @author Administrator
 * 
 */
public class DimensionDetailSec {
	private int primary_id;
	private int parent_id;
	private String key;
	private String value;
	private String querySql;
	
	public String getQuerySql() {
		return querySql;
	}
	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}
	public int getPrimary_id() {
		return primary_id;
	}
	public void setPrimary_id(int primary_id) {
		this.primary_id = primary_id;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
