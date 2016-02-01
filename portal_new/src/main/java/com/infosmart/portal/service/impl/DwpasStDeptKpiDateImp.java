package com.infosmart.portal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasStDeptKpiData;
import com.infosmart.portal.service.DwpasStDeptKpiDateService;
import com.infosmart.portal.util.StringUtils;

@Service
public class DwpasStDeptKpiDateImp extends BaseServiceImpl implements
		DwpasStDeptKpiDateService {
	// 查询所有部门名称
	@Override
	public List<DwpasStDeptKpiData> getDwpasStDeptNames(String reportDate) {

		return this.myBatisDao.getList(
				"com.infosmart.mapper.dwpasstDeptKpiDate.getAllDeptNames",
				reportDate);
	}

	// 根据部门Id获得部门数据
	@Override
	public List<DwpasStDeptKpiData> getAllDwpasStDeptDates(String deptId,
			String reportDate) {
		this.logger.info("加载部门指标信息:" + deptId);
		if (!StringUtils.notNullAndSpace(deptId)) {
			this.logger.warn("加载部门指标数据失败：部门ID为空");
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deptId", deptId);
		if (StringUtils.notNullAndSpace(reportDate)) {
			reportDate = reportDate.replace("-", "");
			map.put("reportDate", reportDate);
		}
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.dwpasstDeptKpiDate.getAllDwpasStDeptDates",
						map);
	}

	public List<DwpasStDeptKpiData> getDetInfoByKpiCode(String kpiCode) {
		if(!StringUtils.notNullAndSpace(kpiCode)){
			this.logger.error("获取部门指标信息失败，kpiCode为空");
			return null;
		}
		return this.myBatisDao.getList(
				"com.infosmart.mapper.dwpasstDeptKpiDate.getDetInfoByKpiCode",
				kpiCode);
	}
}
