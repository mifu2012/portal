package com.infosmart.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.infosmart.po.DwpasDepartmentKpiInfo;
import com.infosmart.service.DwpasDepartmentKpiMonitorService;
import com.infosmart.util.StringUtils;

@Service
public class DwpasDepartmentKpiMonitorServiceImpl extends BaseServiceImpl
		implements DwpasDepartmentKpiMonitorService {

	@Override
	public List<DwpasDepartmentKpiInfo> listPageAllDepartmentKpiInfos(
			DwpasDepartmentKpiInfo departmentKpiInfo) {
		if (departmentKpiInfo == null) {
			this.logger.warn("查询部门信息失败！");
			return null;
		}
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.dwpasstDepartmentKpiMapper.listPageAllDepartmentKpiInfos",
						departmentKpiInfo);
	}

	@Override
	public void deleteDepartmentKpiInfoById(String id) throws Exception {
		if (id == null || id.isEmpty()) {
			this.logger.warn("删除部门指标监控信息失败，参数为空！");
			throw new Exception("删除部门指标监控信息失败，参数为空！");
		}
		this.myBatisDao
				.save("com.infosmart.mapper.dwpasstDepartmentKpiMapper.deleteDepartmentKpiInfoById",
						id);
	}

	@Override
	public void insertDepartmentKpiInfo(DwpasDepartmentKpiInfo departmentKpiInfo)
			throws Exception {
		if (departmentKpiInfo == null) {
			this.logger.warn("插入部门指标监控信息失败，参数为空！");
			throw new Exception("插入部门指标监控信息失败，参数为空！");
		}
		this.myBatisDao
				.save("com.infosmart.mapper.dwpasstDepartmentKpiMapper.insertDepartmentKpiInfo",
						departmentKpiInfo);
	}

	@Override
	public void updateDepartmentKpiInfo(DwpasDepartmentKpiInfo departmentKpiInfo)
			throws Exception {
		if (departmentKpiInfo == null) {
			this.logger.warn("修改部门指标监控信息失败，参数为空！");
			throw new Exception("修改部门指标监控信息失败，参数为空！");
		}
		this.myBatisDao
				.save("com.infosmart.mapper.dwpasstDepartmentKpiMapper.updateDepartmentKpiInfo",
						departmentKpiInfo);
	}

	@Override
	public DwpasDepartmentKpiInfo getDepartmentKpiInfoById(String id) {
		if (!StringUtils.notNullAndSpace(id)) {
			this.logger.warn("根据Id查询部门指标信息失败：部门指标Id为空!");
			return null;
		}
		return this.myBatisDao
				.get("com.infosmart.mapper.dwpasstDepartmentKpiMapper.getDepartmentKpiInfoById",
						id);
	}

}
