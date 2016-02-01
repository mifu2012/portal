package com.infosmart.portal.service;

import com.infosmart.portal.pojo.DwpasCPrdDim;
import com.infosmart.portal.pojo.ProductDimHealth;

/**
 * 产品健康
 * 
 * @author infosmart
 * 
 */
public interface ProductHealthService {

	/**
	 * 查询六维度指标数据
	 * 
	 * @param dwpasCPrdDim
	 *            指标信息
	 * @param reportDate
	 *            统计时间
	 * @param dateType
	 *            统计日期类型,D/W/M
	 */
	ProductDimHealth getDwpasCPrdDim(DwpasCPrdDim dwpasCPrdDim,
			String reportDate, String dateType);
}
