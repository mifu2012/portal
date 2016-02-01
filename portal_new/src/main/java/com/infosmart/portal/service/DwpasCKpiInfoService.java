package com.infosmart.portal.service;

import java.util.List;
import java.util.Map;

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasLongHuBang;
import com.infosmart.portal.taglib.PageInfo;

/**
 * 指标信息管理
 * 
 * @author infosmart
 * 
 */
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

	/**
	 * 根据KPI指标（多个）查询对应的（多个）KPI指标信息
	 * 
	 * @param kpiCodeList
	 * @return
	 */
	List<DwpasCKpiInfo> listDwpasCKpiInfoByCodes(List<String> kpiCodeList);

	/**
	 * 根据产品ID,通用指标，指标类型查询指标信息
	 * 
	 * @param productId
	 *            产品ID
	 * @param commonKpiCode
	 *            通用指标编码
	 * @param kpiType
	 *            指标类型
	 * @return
	 */
	DwpasCKpiInfo getDwpasCKpiInfoByProductIdAndCommonKpiCodeAndKpiType(
			String productId, String commonKpiCode, int kpiType);

	/**
	 * 根据通用编码和指标类型得到大盘指标信息
	 * 
	 * @param commonKpiCode
	 * @param kpiType
	 * @return
	 */
	DwpasCKpiInfo getOverallKpiCodeByCommonKpiCodeAndKpiType(
			String commonKpiCode, int kpiType);

	/**
	 * 根据产品ID,通用指标，指标类型查询指标信息
	 * 
	 * @param productId
	 *            产品ID
	 * @param commonKpiCodeList
	 *            多个通用指标编码
	 * @param kpiType
	 *            指标类型
	 * @return
	 */
	List<DwpasCKpiInfo> listDwpasCKpiInfoByProductIdAndMultiCommonKpiCodeAndKpiType(
			String productId, List<String> commonKpiCodeList, int kpiType);

	// 根据productId List对象 commonKpiCodeList List对象查询数据
	Map<String, List<DwpasLongHuBang>> listDwpasCKpiInfoByProductIdsAndMultiCommonKpiCodeAndKpiType(
			String templateId, int kpiType, String reportDate,
			List<String> commKpiCodeList);

	/**
	 * 根据日期类型查询大大盘指标
	 * 
	 * @return
	 */

	List<DwpasCKpiInfo> getAllOverallKpis(String kpiType);

	/**
	 * 查询指标小类
	 * 
	 * @param productId
	 * @param typeIds
	 * @param kpiType
	 * @return
	 */
	List<DwpasCKpiInfo> getChildCodeByTypeIds(String productId,
			List<String> typeIds, String kpiType);

	/**
	 * 根据通用指标,kpiType,多个产品id获得指标对象，产品ID为key，KpiInfoDTO对象为VALUE
	 * 
	 * @param commonCode
	 *            通用指标code
	 * @param kpiType
	 *            指标类型
	 * @param prodId
	 *            产品idList
	 * @return 指标对象
	 */
	Map<String, DwpasCKpiInfo> queryKpiInfoByProdId(String commonCode,
			int kpiType, List<String> prodId);

	public List<DwpasCKpiInfo> queryEventRelKpiCode(String eventId);

}
