package com.infosmart.service;

import java.util.List;

import com.infosmart.po.DwpasCKpiInfo;
import com.infosmart.po.DwpasCSysType;
import com.infosmart.po.MisTypeInfo;

public interface ParentTypeRelKpiService {

	/**
	 * 查询所有的大类指标
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<DwpasCSysType> getAllParentKpi() throws Exception;

	/**
	 * 查询与此大类指标相联的指标
	 * 
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	public List<DwpasCKpiInfo> getRelativeKpi(String typeId) throws Exception;

	/**
	 * 查询与此大类指标不关联的指标
	 * 
	 * @param groupId
	 * @param keyCode
	 * @return
	 * @throws Exception
	 */
	public List<DwpasCKpiInfo> getUnRelativeKpi(String typeId)
			throws Exception;

	/**
	 * 批量插入此大类指标的关联的指标
	 * 
	 * @param kList
	 * @return
	 * @throws Exception
	 */
	public boolean insertParentKPI(MisTypeInfo parentKpi) throws Exception;

	/**
	 * 更改大类指标关联的指标
	 * 
	 * @param typeId
	 * @param kpiCode
	 */
	public void updateRelateKpiInfo(String typeId, List<String> kpiCodeList)
			throws Exception;

	/**
	 * 更新大类指标的不关联指标
	 * 
	 * @param typeId
	 * @param kpiCodeList
	 */
	public void updateUnRelateKpiInfo(String typeId, List<String> kpiCodeList)
			throws Exception;



	/**
	 * 通过typeId查询大类指标
	 * 
	 * @param typeId
	 * @return
	 */
	public MisTypeInfo getParentKpiByid(String typeId);

	/**
	 * 修改大类指标
	 * 
	 * @param parentKpi
	 * @return
	 * @throws Exception
	 */
	boolean updateParentKpi(MisTypeInfo parentKpi) throws Exception;

	/**
	 * 删除大类指标
	 * 
	 * @param typeId
	 * @return
	 */
	boolean deleteParentKpi(String typeId);

	/**
	 * 根据指标Codes查询指标信息
	 * 
	 * @param kpiCodes
	 * @return
	 */
	public List<DwpasCKpiInfo> getUnRelativeKpiBycodes(List<String> codeList);
	/**
	 * 搜索时，搜索关键字、已经在关联列表里的codeList查询未关联指标
	 * @param keyCode
	 * @param kpicodeList
	 * @return
	 */
	List<DwpasCKpiInfo> getUnRelKpiBySerach(String keyCode, List<String> kpiCodeList);
}
