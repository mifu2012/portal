package com.infosmart.portal.service;

import java.util.List;

import com.infosmart.portal.pojo.DwpasStDeptKpiData;

public interface DwpasStDeptKpiDateService {
	public List<DwpasStDeptKpiData> getDwpasStDeptNames(String reportDate);
	public List<DwpasStDeptKpiData> getAllDwpasStDeptDates(String deptId, String reportDate);
	List<DwpasStDeptKpiData> getDetInfoByKpiCode(String kpiCode);
}
