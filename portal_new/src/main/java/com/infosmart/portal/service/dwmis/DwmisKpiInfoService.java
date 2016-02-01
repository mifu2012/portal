package com.infosmart.portal.service.dwmis;

import java.util.List;

import com.infosmart.portal.pojo.dwmis.DwmisKpiInfo;

/**
 * 指标管理
 * 
 * @author infosmart
 * 
 */
public interface DwmisKpiInfoService {
	/**
	 * 根据指标编码得到指标信息
	 * 
	 * @param kpiCode
	 * @return
	 */
	DwmisKpiInfo getDwmisKpiInfoByCode(final String kpiCode);

	/**
	 * 根据kpiCodes 查询指标列表
	 * 
	 * @param kpiCodes
	 * @return
	 */
	List<DwmisKpiInfo> getDwmisKpiInfoListByCodes(List<String> kpiCodes);

	/**
	 * 根据指标编码得到及关联的指标信息
	 * 
	 * @param kpiCode
	 * @return
	 */
	List<DwmisKpiInfo> listLinkKpiInfoByCode(final String kpiCode);

	/**
	 * 列出部门关联的指标及关联的子指标
	 * 
	 * @param deptId
	 * @return
	 */
	List<DwmisKpiInfo> listKpiInfoByDeptId(String deptId);
	
	/**
	 * 根据KPI编码得到指标数据
	 * 
	 * @param DwmisKpiInfo
	 * @return
	 */
	List<DwmisKpiInfo> getKpiDataByCondition(List<String> kpiCodeList);
}
