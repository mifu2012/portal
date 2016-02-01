package com.infosmart.po;

public class DimensionDetail {
	private Integer primaryKeyId;
	private Integer dimensionId;
	private String key;
	private String value;

	private String querySql;

	public String getQuerySql() {
		return querySql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	public Integer getPrimaryKeyId() {
		return primaryKeyId;
	}

	public void setPrimaryKeyId(Integer primaryKeyId) {
		this.primaryKeyId = primaryKeyId;
	}

	public Integer getDimensionId() {
		return dimensionId;
	}

	public void setDimensionId(Integer dimensionId) {
		this.dimensionId = dimensionId;
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
