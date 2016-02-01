package com.infosmart.service.dwmis;

import java.util.List;

import com.infosmart.po.dwmis.DwmisKpiInfo;
import com.infosmart.po.dwmis.DwmisMisDptmnt;

public interface DwmisMisDptmntService {
	/**
	 * 获取部门信息
	 * 
	 * @return
	 */
	public List<DwmisMisDptmnt> getALLDwmisMisDptmntPage(Object param);

	/**
	 * 根据部门Id查询部门信息
	 * 
	 * @param depId
	 * @return
	 */
	public DwmisMisDptmnt queryDwmisMisDptmntBydepId(String depId);

	/**
	 * 插入新部门信息
	 * 
	 * @param dwmisMisDptmnt
	 */
	public boolean insertDwmisMisDptmnt(DwmisMisDptmnt dwmisMisDptmnt);

	/**
	 * 根据部门Id删除部门信息
	 * 
	 * @param depId
	 */
	public boolean deleteDwmisMisDptmnt(String depId);

	/**
	 * 批量删除部门信息
	 * 
	 * @param depIds
	 */
	public boolean deleteDwmisMisDptmntByIds(List<String> depIds);

	/**
	 * 修改部门信息
	 * 
	 * @param dwmisMisDptmnt
	 */
	public boolean updateDwmisMisDptmnt(DwmisMisDptmnt dwmisMisDptmnt);

	/**
	 * 获取此部门关联的指标
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	public List<DwmisKpiInfo> getRelativeDwmisMisDptmntKPI(String depId)
			throws Exception;

	/**
	 * 获取所有指标
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	public List<DwmisKpiInfo> listKpiInfos()
			throws Exception;

	/**
	 * 获取此部门不关联的指标
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	public List<DwmisKpiInfo> getUnRelativeDwmisMisDptmntKPIByKey(String depId,
			String keyCode) throws Exception;

	/**
	 * 删除此部门关联的指标
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	public boolean deleteRelativeDwmisMisDptmntKPI(String depId) throws Exception;

	/**
	 * 批量插入此部门关联的指标
	 * 
	 * @param kList
	 * @return
	 * @throws Exception
	 */
	public boolean insertRelativeDwmisMisDptmntKPI(String depId, String[] codeList)
			throws Exception;

	/**
	 * 根据指标Codes查询指标信息
	 * @param kpiCodes
	 * @return
	 */
	List<DwmisKpiInfo> getKPIInfoByCodes(List<String> kpiCodes);

}