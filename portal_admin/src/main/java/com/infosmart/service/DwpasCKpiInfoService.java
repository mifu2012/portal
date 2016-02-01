package com.infosmart.service;

import com.infosmart.po.DwpasCKpiInfo;
import com.infosmart.view.PageInfo;

public interface DwpasCKpiInfoService {
	/**
	 * 分页列出指标信息
	 * 
	 * @param dwpasCKpiInfo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageInfo listDwpasCKpiInfoByPagination(DwpasCKpiInfo dwpasCKpiInfo,
			int pageNo, int pageSize);

	/**
	 * 根据KPI指标查询KPI指标信息
	 * 
	 * @param kpiCode
	 * @return
	 */
	DwpasCKpiInfo getDwpasCKpiInfoByCode(String kpiCode);
}
