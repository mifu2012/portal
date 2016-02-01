package com.infosmart.service.dwmis;

import java.util.List;

import com.infosmart.po.dwmis.DwmisKpiInfo;

public interface KPIInfoService {
	/**
	 * 获得kpi信息
	 * 
	 * @param dwmisKpiInfo
	 * @return
	 */
	public List<DwmisKpiInfo> getKPIInfo(DwmisKpiInfo dwmisKpiInfo);
	/**
	 * 添加kpi信息
	 * 
	 * @param dwmisKpiInfo
	 */
	public void addKpiInfo(DwmisKpiInfo dwmisKpiInfo);

	/**
	 * 更新kpi信息
	 * 
	 * @param dwmisKpiInfo
	 */
	public void updateKpiInfo(DwmisKpiInfo dwmisKpiInfo);

	/**
	 * 根据kpiCode删除kpi信息
	 * 
	 * @param kpiCode
	 */
	public void deleteKpiInfo(String kpiCode);

	/**
	 * 根据kpiCode列出kpiInfo
	 * 
	 * @param kpiCode
	 * @return
	 */
	public DwmisKpiInfo selectKpiInfo(String kpiCode);
	/**
	 * 根据kpiName列出kpiInfo
	 * @param kpiName
	 * @return
	 */
	public DwmisKpiInfo seleKpiInfoByName(String kpiName);

	/**
	 * 根据指标查询其关联指标的数据
	 * 
	 * @param relateKpiCode
	 * @return
	 */
	public List<DwmisKpiInfo> getRelateKpiInfo(String relateKpiCode);
	//点击链接查询未关联指标
	public List<DwmisKpiInfo> searchNotRelateBySetValue(String kpiCode);
    //搜索未关联指标数据
	public List<DwmisKpiInfo> searchNotRelateKpiCodes(String kpiCode,String linkedKpiCode);
	/**
	 * 删除指标关联指标数据
	 * 
	 * @param kpiCode
	 */
	public void deleteRelateKpiInfo(String kpiCode);

	/**
	 * 批量添加数据
	 * 
	 * @param kpiCode
	 * @param linkedKpiCode
	 */
	public void updateRelateKpiInfo(String kpiCode, String linkedKpiCode);

	/**
	 * 分页查询
	 * 
	 * @param dwmisKpiInfo
	 * @return
	 */
	public List<DwmisKpiInfo> getKPIInfoPages(DwmisKpiInfo dwmisKpiInfo);
	
	/**
	 * 根据指标CODE或指标名称查询
	 * @param dwmisKpiInfo
	 * @return
	 */
	public List<DwmisKpiInfo> getKpiInfoByNameOrCode(DwmisKpiInfo dwmisKpiInfo);
}
