package com.infosmart.portal.pojo.report;

/**
 * 维度从表
 * 
 * @author hgt
 * 
 */
public class DimensionDetail {
	private Integer primaryKeyId;// 主键
	private Integer dimensionId;// 关联ID
	private String key;// 对应的KEY
	private String value;// 对应的VALUE

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
