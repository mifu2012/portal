package com.infosmart.portal.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasRPrdKpi;
import com.infosmart.portal.service.DwpasRPrdKpiService;
import com.infosmart.portal.util.StringUtils;

@Service
public class DwpasRPrdKpiServiceImpl extends BaseServiceImpl implements
		DwpasRPrdKpiService {

	public boolean insertDwpasRPrdKpi(DwpasRPrdKpi dwpasRPrdKpi) {
		this.logger.info("新增产品与指标关联");
		if (dwpasRPrdKpi == null) {
			return false;
		}
		if (!StringUtils.notNullAndSpace(dwpasRPrdKpi.getKpiCode())) {
			this.logger.error("新增产品与指标关联失败：指标编码为空");
			return false;
		}
		if (!StringUtils.notNullAndSpace(dwpasRPrdKpi.getProductId())) {
			this.logger.error("新增产品与指标关联失败：产品ID为空");
			return false;
		}
		try {
			Map paramMap = new HashMap();
			paramMap.put("productId", dwpasRPrdKpi.getProductId());
			paramMap.put("kpiCode", dwpasRPrdKpi.getKpiCode());
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

	public boolean deleteDwpasRPrdKpi(DwpasRPrdKpi dwpasRPrdKpi) {
		this.logger.info("删除产品与指标关联");
		if (dwpasRPrdKpi == null) {
			return false;
		}
		if (!StringUtils.notNullAndSpace(dwpasRPrdKpi.getKpiCode())) {
			this.logger.error("删除产品与指标关联失败：指标编码为空");
			return false;
		}
		if (!StringUtils.notNullAndSpace(dwpasRPrdKpi.getProductId())) {
			this.logger.error("删除产品与指标关联失败：产品ID为空");
			return false;
		}
		try {
			Map paramMap = new HashMap();
			paramMap.put("productId", dwpasRPrdKpi.getProductId());
			paramMap.put("kpiCode", dwpasRPrdKpi.getKpiCode());
			this.myBatisDao.delete(
					"com.infosmart.mapper.DwpasRPrdKpiMapper.delete", paramMap);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			return false;
		}
	}

	public List<DwpasCKpiInfo> listDwpasCKpiInfoByPrdId(String productId) {
		this.logger.info("根据产品ID列出所有指标信息");
		if (!StringUtils.notNullAndSpace(productId)) {
			this.logger.error("根据产品ID列出所有指标信息失败，产品ID为空");
			return null;
		}
		return this.myBatisDao.getList(
				"com.infosmart.mapper.DwpasRPrdKpiMapper.listKpiInfoByPrdId",
				productId);
	}

}
