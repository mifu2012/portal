package com.infosmart.portal.service;

import java.util.List;

import com.infosmart.portal.pojo.DwpasCPrdInfo;

/**
 * 产品配置组件
 * 
 * @author gentai.huang
 * 
 */
public interface ProdInfoService {

	/**
	 * 查询所有的产品信息
	 * 
	 * @return 产品信息列表
	 */
	public List<DwpasCPrdInfo> getAllProducts(String templateId);

	/**
	 * 根据产品ID获取产品信息
	 * 
	 * @param productId
	 *            产品ID
	 * @return 产品信息
	 */
	public DwpasCPrdInfo getProdInfoById(String productId);
	
	/**500w
	 * 根据用户行为ID获取产品信息
	 * @param productId
	 * @return 信息
	 */
	public DwpasCPrdInfo getUserActionInfoById(String productId);
}
