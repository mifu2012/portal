package com.infosmart.service;

import java.util.List;

import com.infosmart.po.DwpasDepartmentKpiInfo;

public interface DwpasDepartmentKpiMonitorService {

	/**
	 * 查询所有部门指标信息
	 * 
	 * @param departmentKpiInfo
	 * @return
	 */
	List<DwpasDepartmentKpiInfo> listPageAllDepartmentKpiInfos(
			DwpasDepartmentKpiInfo departmentKpiInfo);

	/**
	 * 根据Id查询部门指标信息(modify by mf 2012/10/16)
	 * 
	 * @param id
	 * @return
	 */
	DwpasDepartmentKpiInfo getDepartmentKpiInfoById(String id);

	/**
	 * 根据id删除部门指标信息
	 * 
	 * @param id
	 */
	void deleteDepartmentKpiInfoById(String id) throws Exception;

	/**
	 * 新增部门指标信息
	 * 
	 * @param departmentKpiInfo
	 */
	void insertDepartmentKpiInfo(DwpasDepartmentKpiInfo departmentKpiInfo)
			throws Exception;

	/**
	 * 修改部门指标信息(modify by mf 2012/10/16)
	 * 
	 * @param departmentKpiInfo
	 * @throws Exception
	 */
	void updateDepartmentKpiInfo(DwpasDepartmentKpiInfo departmentKpiInfo)
			throws Exception;
}
