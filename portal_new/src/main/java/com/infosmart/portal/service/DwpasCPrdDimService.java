package com.infosmart.portal.service;

import com.infosmart.portal.pojo.DwpasCPrdDim;

public interface DwpasCPrdDimService {
	/**
	 * 得到某产品的六维度配置信息
	 * 
	 * @param productId
	 * @return
	 */
	DwpasCPrdDim getDwpasCPrdDimByProductId(String productId,String templateId);

}
