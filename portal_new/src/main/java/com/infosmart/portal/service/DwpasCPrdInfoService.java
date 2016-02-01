package com.infosmart.portal.service;

import java.util.List;

import com.infosmart.portal.pojo.DwpasCPrdInfo;
import com.infosmart.portal.pojo.DwpasRColumnComKpi;
import com.infosmart.portal.pojo.ProdTransMonitor;

/**
 * 产品管理
 * 
 * @author infosmart
 * 
 */
public interface DwpasCPrdInfoService {
	/**
	 * 根据产品ID得到产品信息
	 * 
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	DwpasCPrdInfo getDwpasCPrdInfoByProductId(String productId);

	/**
	 * 根据产品ID得到产品信息及其子产品列表
	 * 
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	DwpasCPrdInfo getPrdInfoAndChildPrdByProductId(String productId);

	/**
	 * 列出产品及其子产品
	 * 
	 * @return
	 */
	List<DwpasCPrdInfo> queryAllPrdAndChildPrdInfo();

	/**
	 * 得到产品全图指标数据
	 * 
	 * @param commonKpiCode
	 * @param queryDate
	 * @return
	 */
	List<ProdTransMonitor> getProdTransInfos(
			List<DwpasRColumnComKpi> dwpasRColumnComKpi, String queryDate,
			String lastDate);

	/**
	 * 
	 * 业务笔数监控
	 */
	List<ProdTransMonitor> getServiceInfos(
			List<DwpasRColumnComKpi> dwpasRColumnComKpi, String queryDate);

	/**
	 * 查询所有产品信息
	 * 
	 * @return
	 */
	List<DwpasCPrdInfo> queryAllDwpasCPrdInfo();

	/**
	 * 查询所有产品信息（非文件夹）
	 * 
	 * @param productIdList
	 *            查询的产品ID
	 * @return
	 */
	List<DwpasCPrdInfo> getDwpasCPrdInfoList(List<String> productIdList);
	/**
	 * 产品排行趋势图  要根据模块id templateId
	 * 
	 * @param productIdList
	 *            查询的产品ID
	 * @return
	 */
	List<DwpasCPrdInfo> getDwpasCPrdInfoList(String templateId,List<String> productIdList);
}
