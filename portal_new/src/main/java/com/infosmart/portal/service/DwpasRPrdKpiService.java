package com.infosmart.portal.service;

import java.util.List;

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasRPrdKpi;

/**
 * 产品与指标关联管理
 * 
 * @author infosmart
 * 
 */
public interface DwpasRPrdKpiService {
	/**
	 * 新增产品与指标关联
	 * 
	 * @param DwpasRPrdKpi
	 */
	boolean insertDwpasRPrdKpi(DwpasRPrdKpi dwpasRPrdKpi);

	/**
	 * 删除产品与指标关联
	 * 
	 * @param DwpasRPrdKpi
	 */
	boolean deleteDwpasRPrdKpi(DwpasRPrdKpi dwpasRPrdKpi);

	/**
	 * 列出某产品的所有指标
	 * 
	 * @return
	 */
	List<DwpasCKpiInfo> listDwpasCKpiInfoByPrdId(String productId);
}
