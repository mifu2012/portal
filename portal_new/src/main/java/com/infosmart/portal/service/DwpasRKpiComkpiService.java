package com.infosmart.portal.service;

import com.infosmart.portal.pojo.DwpasRKpiComkpi;

/**
 * 指标与通用指标的关联维护
 * 
 * @author infosmart
 * 
 */
public interface DwpasRKpiComkpiService {

	/**
	 * 新增指标与通用指标关联
	 * 
	 * @param dwpasRKpiComkpi
	 * @return
	 */
	boolean insertDwpasRKpiComkpi(DwpasRKpiComkpi dwpasRKpiComkpi);

	/**
	 * 删除指标与通用指标关联
	 * 
	 * @param dwpasRKpiComkpi
	 * @return
	 */
	boolean deleteDwpasRKpiComkpi(DwpasRKpiComkpi dwpasRKpiComkpi);
}
