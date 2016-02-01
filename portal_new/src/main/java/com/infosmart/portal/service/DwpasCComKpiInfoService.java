package com.infosmart.portal.service;

import java.util.List;
import java.util.Map;

import com.infosmart.portal.pojo.DwpasCComKpiInfo;
import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.taglib.PageInfo;

/**
 * 通用指标管理
 * 
 * @author infosmart
 * 
 */
public interface DwpasCComKpiInfoService {
	/**
	 * 分页列出通用指标
	 * 
	 * @param dwpasCComKpiInfo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	PageInfo listComKpiInfoByPagination(DwpasCComKpiInfo dwpasCComKpiInfo,
			int pageNo, int pageSize);

	/**
	 * 根据（多个）通用编码指标，产品ID,指标类型列出通用指标关联的指标信息
	 * 
	 * @param productId
	 * @param commKpiCodeList
	 * @param kpiType
	 * @return map《comm_kpi_code,List《kpi_info_list》 》
	 */
	Map<String, List<DwpasCKpiInfo>> listKpiInfoByPrdIdAndComKpiCode(
			String productId, List<String> commKpiCodeList, int kpiType);

	/**
	 * 根据通用指标条件查询并列出通用指标信息
	 * 
	 * @param dwpasCComKpiInfo
	 * @return
	 * @throws Exception
	 */
	List<DwpasCComKpiInfo> listDwpasCComKpiInfo(
			DwpasCComKpiInfo dwpasCComKpiInfo);

	/**
	 * 列出某通用指标下的所有指标
	 * 
	 * @param commonKpiCode
	 * @return
	 * @throws Exception
	 */
	List<DwpasCKpiInfo> listDwpasCKpiInfoByCommonKpiCode(String commonKpiCode);
}
