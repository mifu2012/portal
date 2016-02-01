package com.infosmart.portal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasCComKpiInfo;
import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.service.DwpasCComKpiInfoService;
import com.infosmart.portal.taglib.PageInfo;
import com.infosmart.portal.util.StringUtils;

@Service
public class DwpasCComKpiInfoServiceImpl extends BaseServiceImpl implements
		DwpasCComKpiInfoService {

	public PageInfo listComKpiInfoByPagination(
			DwpasCComKpiInfo dwpasCComKpiInfo, int pageNo, int pageSize) {
		this.logger.info("分页列出通用指标");
		return this.myBatisDao
				.getListByPagination(
						"com.infosmart.mapper.DwpasCComKpiInfoMapper.listCommonKpiCodeByPagination",
						dwpasCComKpiInfo, pageNo, pageSize);
	}

	@Override
	public Map<String, List<DwpasCKpiInfo>> listKpiInfoByPrdIdAndComKpiCode(
			String productId, List<String> commKpiCodeList, int kpiType) {
		this.logger.info("根据多个通用指标，产品ID得到通用指标关联的指标信息");
		if (!StringUtils.notNullAndSpace(productId)) {
			this.logger.warn("参数产品ID为空");
			return null;
		}
		if (commKpiCodeList == null || commKpiCodeList.isEmpty()) {
			this.logger.warn("参数通用指标为空");
			return null;
		}
		Map paramMap = new HashMap();
		paramMap.put("productId", productId);
		paramMap.put("commKpiCodes", commKpiCodeList);
		paramMap.put("kpiType", kpiType);
		List<DwpasCComKpiInfo> commKpiInfoList = this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCComKpiInfoMapper.listComCodeAndKpiInfoByMultiCommonKpiAndKpiTypeAndProdId",
						paramMap);
		if (commKpiInfoList != null && !commKpiInfoList.isEmpty()) {
			// <comm_kpi_code,kpi_info>
			Map<String, List<DwpasCKpiInfo>> comKpiAndKpiInfoMap = new HashMap<String, List<DwpasCKpiInfo>>();
			for (DwpasCComKpiInfo comKpiInfo : commKpiInfoList) {
				comKpiAndKpiInfoMap.put(comKpiInfo.getComKpiCode(),
						comKpiInfo.getKpiInfoList());
			}
			return comKpiAndKpiInfoMap;
		}
		return null;
	}

	public List<DwpasCComKpiInfo> listDwpasCComKpiInfo(
			DwpasCComKpiInfo dwpasCComKpiInfo) {
		this.logger.info("根据条件查询通用指标信息");
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCComKpiInfoMapper.listCommonKpiCode",
						dwpasCComKpiInfo);
	}

	public List<DwpasCKpiInfo> listDwpasCKpiInfoByCommonKpiCode(
			String commonKpiCode) {
		this.logger.info("列出通用指标下的所有指标信息:" + commonKpiCode);
		if (!StringUtils.notNullAndSpace(commonKpiCode)) {
			this.logger.error("列出通用指标下的所有指标信息，传入的参数为空");
			return null;
		}
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCComKpiInfoMapper.listKpiInfoByCommonKpiCode",
						commonKpiCode);
	}

}
