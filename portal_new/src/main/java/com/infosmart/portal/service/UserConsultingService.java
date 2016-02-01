package com.infosmart.portal.service;

import java.util.List;

import com.infosmart.portal.vo.dwpas.ProdKpiData;

public interface UserConsultingService {
	/**
	 * 用户咨询情况(产品求助率)
	 * 
	 * @return 产品指标数据DTO列表
	 */
	List<ProdKpiData> queryProductClasse(String systeTemplateId,
			String queryDate, int queryType);

	/**
	 * 查询某产品的子产品线求助率信息
	 * 
	 * @param productId
	 * @param queryDate
	 * @param queryType
	 * @return
	 */
	List<ProdKpiData> queryChildProductClasseByPrdId(String systeTemplateId,
			String productId, String queryDate, int queryType);
}
