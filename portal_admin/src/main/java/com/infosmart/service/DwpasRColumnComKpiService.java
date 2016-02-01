package com.infosmart.service;

import java.util.List;
import java.util.Map;

import com.infosmart.po.DwpasCColumnInfo;
import com.infosmart.po.DwpasRColumnComKpi;

public interface DwpasRColumnComKpiService {
	/**
	 * 复制指标信息
	 * 
	 * @param map
	 */
	void saveDwpasRColumnComKpi(Map<String, Integer> map) throws Exception;

	/**
	 * 根据id集合删除指标信息
	 * 
	 * @param columnId
	 */
	void deleteDwpasRColumnComKpi(List<String> columnId) throws Exception;

	/**
	 * 根据栏目id获得指标信息
	 * 
	 * @param columnId
	 * @return
	 */
	List<DwpasRColumnComKpi> getDwpasRColumnComKpiByTidAndCode(String columnId);

	/**
	 * 添加栏目和指标关联信息
	 * 
	 * @param dwpasRColumnComKpi
	 */
	void insertCommonCode(DwpasRColumnComKpi dwpasRColumnComKpi)
			throws Exception;

	/**
	 * 根据栏目id删除栏目和指标关联信息
	 * 
	 * @param columnId
	 */
	void deleteCommonCode(String columnId) throws Exception;
}
