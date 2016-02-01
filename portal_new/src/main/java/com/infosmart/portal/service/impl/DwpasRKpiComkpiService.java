package com.infosmart.portal.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.infosmart.portal.pojo.DwpasRKpiComkpi;
import com.infosmart.portal.util.StringUtils;

public class DwpasRKpiComkpiService extends BaseServiceImpl implements
		com.infosmart.portal.service.DwpasRKpiComkpiService {

	public boolean insertDwpasRKpiComkpi(DwpasRKpiComkpi dwpasRKpiComkpi) {
		this.logger.info("新增通用指标与指标关联");
		if (dwpasRKpiComkpi == null) {
			return false;
		}
		if (!StringUtils.notNullAndSpace(dwpasRKpiComkpi.getKpiCode())) {
			this.logger.error("新增通用指标与指标关联：指标编码为空");
			return false;
		}
		if (!StringUtils.notNullAndSpace(dwpasRKpiComkpi.getComKpiCode())) {
			this.logger.error("新增通用指标与指标关联：通用指标为空");
			return false;
		}
		try {
			Map paramMap = new HashMap();
			paramMap.put("comKpiCode", dwpasRKpiComkpi.getComKpiCode());
			paramMap.put("kpiCode", dwpasRKpiComkpi.getKpiCode());
			paramMap.put("createDate", new Date());
			paramMap.put("modifyDate", new Date());
			this.myBatisDao.save(
					"com.infosmart.mapper.DwpasRPrdKpiMapper.insert", paramMap);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			return false;
		}
	}

	public boolean deleteDwpasRKpiComkpi(DwpasRKpiComkpi dwpasRKpiComkpi) {
		this.logger.info("删除通用指标与指标关联");
		if (dwpasRKpiComkpi == null) {
			return false;
		}
		if (!StringUtils.notNullAndSpace(dwpasRKpiComkpi.getKpiCode())) {
			this.logger.error("删除通用指标与指标关联：指标编码为空");
			return false;
		}
		if (!StringUtils.notNullAndSpace(dwpasRKpiComkpi.getComKpiCode())) {
			this.logger.error("删除通用指标与指标关联：通用指标为空");
			return false;
		}
		try {
			Map paramMap = new HashMap();
			paramMap.put("comKpiCode", dwpasRKpiComkpi.getComKpiCode());
			paramMap.put("kpiCode", dwpasRKpiComkpi.getKpiCode());
			this.myBatisDao.delete(
					"com.infosmart.mapper.DwpasRPrdKpiMapper.delete", paramMap);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			return false;
		}
	}

}
