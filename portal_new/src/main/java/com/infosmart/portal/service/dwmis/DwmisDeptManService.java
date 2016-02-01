package com.infosmart.portal.service.dwmis;

import java.util.List;

import com.infosmart.portal.pojo.dwmis.DwmisMisDptmnt;

/**
 * 部门管理
 * 
 * @author infosmart
 * 
 */
public interface DwmisDeptManService {
	/**
	 * 列出所有业务监控的部门(包括关联的指标信息)
	 * 
	 * @return
	 */
	List<DwmisMisDptmnt> listAllMonitorKpiDept(int groupId);

	/**
	 * 列出所有的部门
	 * 
	 * @param groupId
	 * @return
	 */
	List<DwmisMisDptmnt> listAllDept(int groupId);

	/**
	 * 得到部门信息
	 * 
	 * @param deptId
	 * @return
	 */
	DwmisMisDptmnt getDwmisMisDptmntById(String deptId);
}
