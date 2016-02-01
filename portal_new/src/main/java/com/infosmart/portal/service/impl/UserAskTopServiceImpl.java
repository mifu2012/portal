package com.infosmart.portal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasRColumnComKpi;
import com.infosmart.portal.pojo.UserAskTop;
import com.infosmart.portal.service.UserAskTopService;
import com.infosmart.portal.util.StringUtils;

@Service
public class UserAskTopServiceImpl extends BaseServiceImpl implements
		UserAskTopService {
	@Override
	public List<UserAskTop> listUserTop10Date(String productId,
			String queryDate, List<String> dayUserTypeList) {
		if (!StringUtils.notNullAndSpace(productId)) {
			this.logger.warn("产品Id为空");
			return null;
		}
		if (!StringUtils.notNullAndSpace(queryDate)) {
			this.logger.warn("查询日期为空");
			return null;
		}
		if (dayUserTypeList == null) {
			this.logger.warn("日指标下用户类型为空");
			return null;
		}

		Map map = new HashMap();
		map.put("productId", productId);
		map.put("reportDate", queryDate.replace("-", ""));
		map.put("dayUserTypeList", dayUserTypeList);
		return this.myBatisDao.getList(
				"com.infosmart.mapper.DwpasStCatTop10Date.listUserTop10Date",
				map);
	}

	@Override
	public List<UserAskTop> listUserTop10Month(String productId,
			String queryDate, List<String> monthUserTypeList) {
		if (!StringUtils.notNullAndSpace(productId)) {
			this.logger.warn("产品Id为空");
			return null;
		}
		if (!StringUtils.notNullAndSpace(queryDate)) {
			this.logger.warn("查询日期为空");
			return null;
		}
		if (monthUserTypeList == null) {
			this.logger.warn("月指标下用户类型为空");
			return null;
		}
		Map map = new HashMap();
		map.put("productId", productId);
		map.put("reportDate", queryDate.replace("-", ""));
		map.put("monthUserTypeList", monthUserTypeList);
		return this.myBatisDao.getList(
				"com.infosmart.mapper.DwpasStCatTop10Month.listUserTop10Month",
				map);
	}

	@Override
	public List<DwpasRColumnComKpi> queryComKpiInfoByColumnCodeAndKpiCode(
			String menuId, String moduleCode, String kpiCode, String dateType) {
		Map map = new HashMap();
		map.put("menuId", menuId);
		map.put("moduleCode", moduleCode);
		map.put("kpiCode", kpiCode);
		map.put("dateType", dateType);
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasStCatTop10Date.queryComKpiInfoByColumnCodeAndKpiCode",
						map);
	}
}
