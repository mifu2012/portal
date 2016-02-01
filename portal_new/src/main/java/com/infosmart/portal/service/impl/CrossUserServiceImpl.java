package com.infosmart.portal.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.CrossComparator;
import com.infosmart.portal.pojo.CrossUser;
import com.infosmart.portal.pojo.DwpasCPrdInfo;
import com.infosmart.portal.pojo.DwpasStCrossAnalyseData;
import com.infosmart.portal.service.CrossUserService;
import com.infosmart.portal.util.StringUtils;

@Service
public class CrossUserServiceImpl extends BaseServiceImpl implements
		CrossUserService {

	@Override
	public List<CrossUser> getCrossUserInfo(String templateId, String productId,
			String queryDate) {
		if (!StringUtils.notNullAndSpace(productId)) {
			this.logger.warn("查询交叉用户信息失败  产品Id为空");
			return null;
		}
		if (!StringUtils.notNullAndSpace(queryDate)) {
			this.logger.warn("查询交叉用户信息失败  查询日期为空");
			return null;
		}
		BigDecimal mul = new BigDecimal(100);
		List<CrossUser> dtos = new ArrayList<CrossUser>();
		DwpasCPrdInfo dwpasCPrdInfo = new DwpasCPrdInfo();
		dwpasCPrdInfo.setProductMark("4001");
		dwpasCPrdInfo.setIsUse("1");
		dwpasCPrdInfo.setTemplateId(templateId);
		// 查询所有已经标记的产品
		List<DwpasCPrdInfo> prods = this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCPrdInfoMapper.queryDwpasCPrdInfoList",
						dwpasCPrdInfo);
		if (prods == null || prods.isEmpty()) {
			this.logger.warn("没有找到已标记的产品");
			return null;
		}
		for (int i = 0; i < prods.size(); i++) {
			DwpasCPrdInfo prod = prods.get(i);
			if (productId.equals(prod.getProductId())) {
				continue;
			}
			// 根据产品id和月份查询交叉用户信息
			Map param = new HashMap();
			param.put("productId", productId);
			param.put("reportDate", queryDate);
			param.put("relProductId", prod.getProductId());
			List<DwpasStCrossAnalyseData> crosses = this.myBatisDao
					.getList(
							"com.infosmart.mapper.DwpasStCrossAnalyseDataMapper.getDwpasStCrossAnalyseData",
							param);
			CrossUser dto = new CrossUser();
			dto.setRelProductId(prod.getProductId());
			dto.setRelProductName(prod.getProductName());
			if (crosses != null && crosses.size() <= 0) {
				dto.setCrossUserCnt(-1);
				dto.setCrossUserRate(null);
			} else {
				dto.setCrossUserCnt(crosses.get(0).getCrossUserCnt());
				BigDecimal tmp = crosses.get(0).getCrossUserRate()
						.multiply(mul);
				tmp = tmp.setScale(2, BigDecimal.ROUND_UP);
				dto.setCrossUserRate(tmp);
			}
			dtos.add(dto);
			CrossComparator cmp = new CrossComparator();
			Collections.sort(dtos, cmp);
		}
		return dtos;
	}

	@Override
	public List<CrossUser> getCrossUserActionInfo(String productId, String queryDate) {
		if (!StringUtils.notNullAndSpace(productId)) {
			this.logger.warn("查询交叉用户信息失败  产品Id为空");
			return null;
		}
		if (!StringUtils.notNullAndSpace(queryDate)) {
			this.logger.warn("查询交叉用户信息失败  查询日期为空");
			return null;
		}
		BigDecimal mul = new BigDecimal(100);
		List<CrossUser> dtos = new ArrayList<CrossUser>();
		DwpasCPrdInfo dwpasCPrdInfo = new DwpasCPrdInfo();
		dwpasCPrdInfo.setProductMark("4003");
		dwpasCPrdInfo.setIsUse("1");
		// 查询所有已经标记的产品
		List<DwpasCPrdInfo> prods = this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCPrdInfoMapper.queryUserActionList",
						dwpasCPrdInfo);
		if (prods == null || prods.isEmpty()) {
			this.logger.warn("没有找到已标记的产品");
			return null;
		}
		for (int i = 0; i < prods.size(); i++) {
			DwpasCPrdInfo prod = prods.get(i);
			if (productId.equals(prod.getProductId())) {
				continue;
			}
			// 根据产品id和月份查询交叉用户信息
			Map param = new HashMap();
			param.put("productId", productId);
			param.put("reportDate", queryDate);
			param.put("relProductId", prod.getProductId());
			List<DwpasStCrossAnalyseData> crosses = this.myBatisDao
					.getList(
							"com.infosmart.mapper.DwpasStCrossAnalyseDataMapper.getDwpasStCrossAnalyseData",
							param);
			CrossUser dto = new CrossUser();
			dto.setRelProductId(prod.getProductId());
			dto.setRelProductName(prod.getProductName());
			if (crosses != null && crosses.size() <= 0) {
				dto.setCrossUserCnt(-1);
				dto.setCrossUserRate(null);
			} else {
				dto.setCrossUserCnt(crosses.get(0).getCrossUserCnt());
				BigDecimal tmp = crosses.get(0).getCrossUserRate()
						.multiply(mul);
				tmp = tmp.setScale(2, BigDecimal.ROUND_UP);
				dto.setCrossUserRate(tmp);
			}
			dtos.add(dto);
			CrossComparator cmp = new CrossComparator();
			Collections.sort(dtos, cmp);
		}
		return dtos;
	}

}
