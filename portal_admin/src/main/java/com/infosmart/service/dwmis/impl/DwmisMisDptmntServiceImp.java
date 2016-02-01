package com.infosmart.service.dwmis.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.infosmart.po.dwmis.DwmisKpiInfo;
import com.infosmart.po.dwmis.DwmisMisDptmnt;
import com.infosmart.service.dwmis.DwmisMisDptmntService;
import com.infosmart.service.impl.BaseServiceImpl;

@Service
public class DwmisMisDptmntServiceImp extends BaseServiceImpl implements
		DwmisMisDptmntService {

	@Override
	public List<DwmisMisDptmnt> getALLDwmisMisDptmntPage(Object param) {
		return this.myBatisDao
				.getList(
						"com.infosmart.DwmisMisDptmntMapper.getALLDwmisMisDptmntlistPage",
						param);
	}

	@Override
	public boolean insertDwmisMisDptmnt(DwmisMisDptmnt dwmisMisDptmnt) {
		if (dwmisMisDptmnt == null) {
			this.logger.warn("插入部门信息失败：参数为空");
			return false;
		}
		try {
			myBatisDao.save(
					"com.infosmart.DwmisMisDptmntMapper.insertDwmisMisDptmnt",
					dwmisMisDptmnt);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteDwmisMisDptmnt(String depId) {
		if (StringUtils.isBlank(depId)) {
			this.logger.warn("删除部门失败:部门ID为空");
			return false;
		}
		try {
			myBatisDao.delete(
					"com.infosmart.DwmisMisDptmntMapper.deleteDwmisMisDptmnt",
					depId);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	@Override
	public DwmisMisDptmnt queryDwmisMisDptmntBydepId(String depId) {
		if (StringUtils.isBlank(depId)) {
			this.logger.warn("查询部门信息失败：部门Id为空");
			return null;
		}
		return myBatisDao
				.get("com.infosmart.DwmisMisDptmntMapper.queryDwmisMisDptmntBydepId",
						depId);
	}

	@Override
	public boolean updateDwmisMisDptmnt(DwmisMisDptmnt dwmisMisDptmnt) {
		if (dwmisMisDptmnt == null) {
			this.logger.warn("更新部门失败:参数dwmisMisDptmnt为空");
			return false;
		}
		try {
			this.myBatisDao.update(
					"com.infosmart.DwmisMisDptmntMapper.updateDwmisMisDptmnt",
					dwmisMisDptmnt);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteDwmisMisDptmntByIds(List<String> depIds) {
		if (depIds == null || depIds.isEmpty()) {
			this.logger.warn("删除部门列表失败:参数部门id列表为空");
			return false;
		}
		try {
			this.myBatisDao
					.delete("com.infosmart.DwmisMisDptmntMapper.deleteDwmisMisDptmntByIds",
							depIds);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	@Override
	public List<DwmisKpiInfo> getRelativeDwmisMisDptmntKPI(String depId)
			throws Exception {
		if (StringUtils.isBlank(depId)) {
			this.logger.warn("查询部门关联指标信息失败：部门Id为空");
			return null;
		}
		List<DwmisKpiInfo> dwmisKpiInfos = null;
		dwmisKpiInfos = this.myBatisDao
				.getList(
						"com.infosmart.DptmentRelKpiMapper.getRelativeDwmisMisDptmntKPI",
						depId);
		return dwmisKpiInfos;
	}

	@Override
	public List<DwmisKpiInfo> getUnRelativeDwmisMisDptmntKPIByKey(String depId,
			String keyCode) throws Exception {
		if (StringUtils.isBlank(depId)) {
			this.logger.warn("查询部门不关联指标信息失败：部门Id为空");
			return null;
		}
		List<DwmisKpiInfo> dwmisKpiInfos = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("depId", depId);
		map.put("keyCode", keyCode);
		dwmisKpiInfos = this.myBatisDao
				.getList(
						"com.infosmart.DptmentRelKpiMapper.getUnRelativeDwmisMisDptmntKPIByKeyCode",
						map);
		return dwmisKpiInfos;
	}

	@Override
	public boolean deleteRelativeDwmisMisDptmntKPI(String depId)
			throws Exception {
		if (StringUtils.isBlank(depId)) {
			this.logger.warn("删除部门关联指标失败:参数部门Id为空");
			return false;
		}
		try {

			this.myBatisDao
					.delete("com.infosmart.DptmentRelKpiMapper.deleteRelativeDwmisMisDptmntKPI",
							depId);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	@Override
	public boolean insertRelativeDwmisMisDptmntKPI(String depId,
			String[] codeList) throws Exception {
		if (codeList == null) {
			this.logger.warn("插入部门关联指标失败:参数dwmisMisDptmnt为空");
			return false;
		}
		DwmisKpiInfo dwmisKpiInfo = null;
		List<DwmisKpiInfo> kpiInfoList = new ArrayList<DwmisKpiInfo>();
		if (codeList.length > 0) {
			for (int i = 0; i < codeList.length; i++) {
				dwmisKpiInfo = new DwmisKpiInfo();
				dwmisKpiInfo.setDepId(depId);
				dwmisKpiInfo.setKpiCode(codeList[i]);
				kpiInfoList.add(dwmisKpiInfo);
			}
		}
		try {
			this.myBatisDao
					.save("com.infosmart.DptmentRelKpiMapper.insertRelativeDwmisMisDptmntKPI",
							kpiInfoList);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	@Override
	public List<DwmisKpiInfo> listKpiInfos()
			throws Exception {
		return this.myBatisDao
				.getList(
						"com.infosmart.DptmentRelKpiMapper.listAllKpiInfos");
	}
	
	@Override
	public List<DwmisKpiInfo> getKPIInfoByCodes(List<String> kpiCodes) {
		if(kpiCodes.isEmpty()&&kpiCodes.size()<=0){
			this.logger.warn("通过指标Codes查询指标信息失败：指标Codes为空");
			return null;
		}
		return this.myBatisDao.getList("com.infosmart.DptmentRelKpiMapper.getKPIInfoByCodes",kpiCodes);
	}
}
