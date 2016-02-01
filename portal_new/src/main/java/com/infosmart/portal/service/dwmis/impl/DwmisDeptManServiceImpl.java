package com.infosmart.portal.service.dwmis.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.dwmis.DwmisMisDptmnt;
import com.infosmart.portal.service.dwmis.DwmisDeptManService;
import com.infosmart.portal.service.impl.BaseServiceImpl;
import com.infosmart.portal.util.StringUtils;

@Service
public class DwmisDeptManServiceImpl extends BaseServiceImpl implements
		DwmisDeptManService {

	@Override
	public List<DwmisMisDptmnt> listAllMonitorKpiDept(int groupId) {
		return this.myBatisDao
				.getList(
						"com.infosmart.dwmis.DwmisMisDptmntMapper.listAllMonitorDeptAndKpiByGroupId",
						groupId);
	}

	@Override
	public List<DwmisMisDptmnt> listAllDept(int groupId) {
		return this.myBatisDao
				.getList(
						"com.infosmart.dwmis.DwmisMisDptmntMapper.listAllDeptByGroupId",
						groupId);
	}

	@Override
	public DwmisMisDptmnt getDwmisMisDptmntById(String deptId) {
		if (!StringUtils.notNullAndSpace(deptId)) {
			this.logger.warn("根据部门Id查询部门信息失败：部门Id为空!");
			return null;
		}
		return this.myBatisDao.get(
				"com.infosmart.dwmis.DwmisMisDptmntMapper.getDeptInfoById",
				deptId);
	}

}
