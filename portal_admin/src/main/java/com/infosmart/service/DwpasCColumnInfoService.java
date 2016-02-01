package com.infosmart.service;

import java.util.List;
import java.util.Map;

import com.infosmart.po.DwpasCColumnInfo;
import com.infosmart.po.DwpasCComKpiInfo;
import com.infosmart.po.DwpasCmoduleInfo;

public interface DwpasCColumnInfoService {
	/**
	 * 获得所有栏目信息
	 * 
	 * @param columnInfo
	 * @return
	 */
	List<DwpasCColumnInfo> listPageDwpasCColumnInfo(DwpasCColumnInfo columnInfo);

	/**
	 * 批量保存模块关联的栏目信息
	 * 
	 * @param moduleInfo
	 */
	void saveDwpasCColumnInfoByBatch(DwpasCmoduleInfo moduleInfo) throws Exception;

	/**
	 * 复制栏目信息
	 * 
	 * @param map
	 */
	void saveDwpasCColumnInfo(Map<String, Integer> map) throws Exception;

	/**
	 * 删除栏目信息
	 * 
	 * @param moduleId
	 */
	void deleteDwpasCColumnInfo(List<String> moduleId) throws Exception;

	/**
	 * 按模块ID查询到对应的栏目
	 * 
	 * @param moduleId
	 * @return
	 */
	List<DwpasCColumnInfo> getCColumnInfoByModuleId(String moduleId);

	/**
	 * 按模块ID查询到对应的栏目及关联的通用指标
	 * 
	 * @param moduleId
	 * @return
	 */
	List<DwpasCColumnInfo> listCColumnInfoAndRelCommKpiInfoByModuleId(
			String moduleId);

	/**
	 * 获得所有通用指标信息
	 * 
	 * @return
	 */
	List<DwpasCComKpiInfo> getAllDwpasCComKpiInfo(
			DwpasCComKpiInfo dwpasCComKpiInfo);

	/**
	 * 根据模板ID查询栏目信息
	 * 
	 * @return
	 */
	List<DwpasCColumnInfo> getMoBan();

	/**
	 * 添加栏目信息
	 * 
	 * @param columnInfo
	 */
	void insertColumnInfo(DwpasCColumnInfo columnInfo) throws Exception;

	/**
	 * 更新栏目信息
	 * 
	 * @param columnInfo
	 */
	void updateColumnInfo(DwpasCColumnInfo columnInfo) throws Exception;

	/**
	 * 大类集合
	 * 
	 * @param moduleId
	 * @return
	 */
	List<DwpasCColumnInfo> getDaleiByModuleId(String moduleId);

	/**
	 * 根据栏目ID找到栏目信息
	 * 
	 * @param moduleIds
	 * @return
	 */
	List<DwpasCColumnInfo> getBYModuleIds(List<String> moduleIds);
	/**
	 * 根据栏目Id查询栏目信息及指标信息
	 * @param moduleId
	 * @return
	 */
	List<DwpasCColumnInfo> getAllColumnInfoAndComkpilist(String moduleId);
}
