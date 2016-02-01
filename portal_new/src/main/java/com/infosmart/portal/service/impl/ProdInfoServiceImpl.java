package com.infosmart.portal.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasCPrdInfo;
import com.infosmart.portal.service.ProdInfoService;

@Service
public class ProdInfoServiceImpl extends BaseServiceImpl implements
		ProdInfoService {

	public List<DwpasCPrdInfo> getAllProducts(String templateId) {
		this.logger.info("*******查询产品信息*******");
		return this.myBatisDao
				.getList(
						"com.infosmart.portal.pojo.DwpasCPrdInfoMapper.GET_ALL_PRODUCTS",
						templateId);
	}

	public DwpasCPrdInfo getProdInfoById(String productId) {
		this.logger.info("****根据产品ID查询产品信息****" + productId);
		DwpasCPrdInfo dwpasCPrdInfo = this.myBatisDao
				.get("com.infosmart.portal.pojo.DwpasCPrdInfoMapper.GET_PRODINFO_BY_ID",
						productId);
		return dwpasCPrdInfo;
	}

	// 500W:用户行为
	public DwpasCPrdInfo getUserActionInfoById(String productId) {
		this.logger.info("****根据用户行为ID查询产品信息****" + productId);
		DwpasCPrdInfo dwpasCPrdInfo = this.myBatisDao
				.get("com.infosmart.portal.pojo.DwpasCPrdInfoMapper.getUserActionInfoById",
						productId);
		return dwpasCPrdInfo;
	}

}
