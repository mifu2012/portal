package com.infosmart.portal.vo.dwpas;

import java.math.BigDecimal;

import com.infosmart.portal.util.Constants;

public class ProductUsingCharacter implements
		Comparable<ProductUsingCharacter> {

	public int compareTo(ProductUsingCharacter param) {
		//��ͼ��Ҫ��������������
		if (sortType != null && sortType.equals("byCnt")) {
			value = value ==null?Constants.ZERO:value;
			BigDecimal valueParam = param.getValue();
			valueParam = valueParam ==null?Constants.ZERO:valueParam;
			
			return valueParam.compareTo(value);
			
			
		} else {
			if (getFeatureTypeId() > param.getFeatureTypeId()) {
				return 1;
			} else if (getFeatureTypeId() > param.getFeatureTypeId()) {
				return -1;
			}
			return 0;
		}
	}

	private String sortType;

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	private String prodId;

	private String productName;

	private String color;

	private int featureTypeId;

	public int getFeatureTypeId() {
		return featureTypeId;
	}

	public void setFeatureTypeId(int featureTypeId) {
		this.featureTypeId = featureTypeId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	private String featureType;

	private String featureName;

	private String date;

	private BigDecimal value;

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getFeatureType() {
		return featureType;
	}

	public void setFeatureType(String featureType) {
		this.featureType = featureType;
	}

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public BigDecimal getBaseValue() {
		return baseValue;
	}

	public void setBaseValue(BigDecimal baseValue) {
		this.baseValue = baseValue;
	}

	private BigDecimal baseValue;

}
