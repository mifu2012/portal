package com.infosmart.service;

import com.infosmart.po.PrdDimInfo;

/**
 * 产品健康度信息service
 */
public interface PrdDimInfoService {

	/**
	 * 查询指定产品id产品健康度信息
	 * 
	 * @param productId
	 * @return
	 */
	public PrdDimInfo qryPrdDimByProductId(String productId);

	/**
	 * 保存产品健康度信息
	 * 
	 * @param dwpasCPrdDimDO
	 * @return
	 */
	boolean savePrdDim(PrdDimInfo dwpasCPrdDimDO);

	/**
	 * 更新健康度信息
	 */
	public boolean updatePrddimInfo(PrdDimInfo prddiminfo);
}
