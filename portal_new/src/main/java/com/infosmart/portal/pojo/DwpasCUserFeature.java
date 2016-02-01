package com.infosmart.portal.pojo;

import java.util.Date;

/**
 * 产品用户特征信息表
 * 
 * @author infosmart
 * 
 */
public class DwpasCUserFeature {
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column dwpas_c_user_feature.id
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	private Integer id;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column dwpas_c_user_feature.product_id 产品ID(关联产品信息表) 产品ID
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	private String productId;

	// 关联产品信息(通过产品ID关联)
	private DwpasCPrdInfo dwpasCPrdInfo;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column dwpas_c_user_feature.product_name 产品名称 产品名称
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	private String productName;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column dwpas_c_user_feature.feature_type
	 * 特征类型:1-使用次数，2-时间偏好，3-年龄段，4-地区，等
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	private String featureType;

	// 次数
	public final static int FEATURE_TYPE_OF_USE_FREQUENCY = 1;
	// 时间
	public final static int FEATURE_TYPE_OF_USE_TIME = 2;
	// 年龄
	public final static int FEATURE_TYPE_OF_USER_AGE = 3;
	// 区域
	public final static int FEATURE_TYPE_OF_USER_AREA = 4;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column dwpas_c_user_feature.feature_type_name 特征类型名称
	 * 特征类型名称
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	private String featureTypeName;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column dwpas_c_user_feature.feature_id 特征编码ID 特征编码ID
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	private String featureId;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column dwpas_c_user_feature.feature_name 特征名称 特征名称
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	private String featureName;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column dwpas_c_user_feature.GMT_CREATE
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	private Date gmtCreate;

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds
	 * to the database column dwpas_c_user_feature.GMT_MODIFIED
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	private Date gmtModified;

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column dwpas_c_user_feature.id
	 * 
	 * @return the value of dwpas_c_user_feature.id
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column dwpas_c_user_feature.id
	 * 
	 * @param id
	 *            the value for dwpas_c_user_feature.id
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column dwpas_c_user_feature.product_id
	 * 
	 * @return the value of dwpas_c_user_feature.product_id
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column dwpas_c_user_feature.product_id
	 * 
	 * @param productId
	 *            the value for dwpas_c_user_feature.product_id
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	public void setProductId(String productId) {
		this.productId = productId == null ? null : productId.trim();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column dwpas_c_user_feature.product_name
	 * 
	 * @return the value of dwpas_c_user_feature.product_name
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column dwpas_c_user_feature.product_name
	 * 
	 * @param productName
	 *            the value for dwpas_c_user_feature.product_name
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	public void setProductName(String productName) {
		this.productName = productName == null ? null : productName.trim();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column dwpas_c_user_feature.feature_type
	 * 
	 * @return the value of dwpas_c_user_feature.feature_type
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	public String getFeatureType() {
		return featureType;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column dwpas_c_user_feature.feature_type
	 * 
	 * @param featureType
	 *            the value for dwpas_c_user_feature.feature_type
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	public void setFeatureType(String featureType) {
		this.featureType = featureType == null ? null : featureType.trim();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column dwpas_c_user_feature.feature_type_name
	 * 
	 * @return the value of dwpas_c_user_feature.feature_type_name
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	public String getFeatureTypeName() {
		return featureTypeName;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column dwpas_c_user_feature.feature_type_name
	 * 
	 * @param featureTypeName
	 *            the value for dwpas_c_user_feature.feature_type_name
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	public void setFeatureTypeName(String featureTypeName) {
		this.featureTypeName = featureTypeName == null ? null : featureTypeName
				.trim();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column dwpas_c_user_feature.feature_id
	 * 
	 * @return the value of dwpas_c_user_feature.feature_id
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	public String getFeatureId() {
		return featureId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column dwpas_c_user_feature.feature_id
	 * 
	 * @param featureId
	 *            the value for dwpas_c_user_feature.feature_id
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	public void setFeatureId(String featureId) {
		this.featureId = featureId == null ? null : featureId.trim();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column dwpas_c_user_feature.feature_name
	 * 
	 * @return the value of dwpas_c_user_feature.feature_name
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	public String getFeatureName() {
		return featureName;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column dwpas_c_user_feature.feature_name
	 * 
	 * @param featureName
	 *            the value for dwpas_c_user_feature.feature_name
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	public void setFeatureName(String featureName) {
		this.featureName = featureName == null ? null : featureName.trim();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column dwpas_c_user_feature.GMT_CREATE
	 * 
	 * @return the value of dwpas_c_user_feature.GMT_CREATE
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	public Date getGmtCreate() {
		return gmtCreate;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column dwpas_c_user_feature.GMT_CREATE
	 * 
	 * @param gmtCreate
	 *            the value for dwpas_c_user_feature.GMT_CREATE
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns
	 * the value of the database column dwpas_c_user_feature.GMT_MODIFIED
	 * 
	 * @return the value of dwpas_c_user_feature.GMT_MODIFIED
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	public Date getGmtModified() {
		return gmtModified;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the
	 * value of the database column dwpas_c_user_feature.GMT_MODIFIED
	 * 
	 * @param gmtModified
	 *            the value for dwpas_c_user_feature.GMT_MODIFIED
	 * 
	 * @ibatorgenerated Tue Feb 28 10:20:55 CST 2012
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public DwpasCPrdInfo getDwpasCPrdInfo() {
		return dwpasCPrdInfo;
	}

	public void setDwpasCPrdInfo(DwpasCPrdInfo dwpasCPrdInfo) {
		this.dwpasCPrdInfo = dwpasCPrdInfo;
	}
}