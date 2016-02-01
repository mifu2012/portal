package com.infosmart.portal.service.dwmis.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.dwmis.DwmisKpiInfo;
import com.infosmart.portal.service.dwmis.DwmisKpiInfoService;
import com.infosmart.portal.service.impl.BaseServiceImpl;
import com.infosmart.portal.util.StringUtils;

@Service
public class DwmisKpiInfoServiceImpl extends BaseServiceImpl implements
		DwmisKpiInfoService {

	@Override
	public DwmisKpiInfo getDwmisKpiInfoByCode(String kpiCode) {
		if (!StringUtils.notNullAndSpace(kpiCode)) {
			this.logger.warn("根据指标编码得到指标信息失败：指标Code为空!");
			return null;
		}
		if (StringUtils.notNullAndSpace(kpiCode)) {
			return this.myBatisDao
					.get("com.infosmart.dwmis.dwmisKpiInfoMapper.queryKpiInfoByKpiCode",
							kpiCode);
		}
		return null;
	}

	@Override
	public List<DwmisKpiInfo> listKpiInfoByDeptId(String deptId) {
		if (!StringUtils.notNullAndSpace(deptId)) {
			this.logger.warn("根据部门Id查询某部门的指标及子指标信息 失败:部门Id为空!");
			return null;
		}
		return this.myBatisDao.getList(
				"com.infosmart.dwmis.dwmisKpiInfoMapper.listKpiAndSubKpiInfo",
				deptId);
	}

	@Override
	public List<DwmisKpiInfo> listLinkKpiInfoByCode(String kpiCode) {
		if (!StringUtils.notNullAndSpace(kpiCode)) {
			this.logger.warn("查询指标及关联的子指标信息失败：指标Code为空!");
			return null;
		}
		return this.myBatisDao.getList(
				"com.infosmart.dwmis.dwmisKpiInfoMapper.listLinkKpiByCode",
				kpiCode);
	}

	/**
	 * 根据KPI编码得到指标数据
	 * 
	 * @param kpiData
	 * @return
	 */
	@Override
	public List<DwmisKpiInfo> getKpiDataByCondition(List<String> kpiCodeList) {
		if (kpiCodeList == null || kpiCodeList.isEmpty()) {
			this.logger.warn("根据KPI编码得到指标数据失败：指标Code列表为空!");
			return null;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("kpiCodeList", kpiCodeList);
		return this.myBatisDao
				.getList(
						"com.infosmart.dwmis.dwmisKpiInfoMapper.queryDwmisKpiInfoByKpiCode",
						paramMap);
	}

	@Override
	public List<DwmisKpiInfo> getDwmisKpiInfoListByCodes(List<String> kpiCodes) {
		if (kpiCodes == null || kpiCodes.isEmpty()) {
			this.logger.warn("根据指标Codes查询指标列表失败：指标Codes为空！");
			return null;
		}
		return this.myBatisDao
				.getList(
						"com.infosmart.dwmis.dwmisKpiInfoMapper.getDwmisKpiInfoListByCodes",
						kpiCodes);
	}

}
