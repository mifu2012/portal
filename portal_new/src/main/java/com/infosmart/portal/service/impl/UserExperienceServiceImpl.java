package com.infosmart.portal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.ChannelInfo;
import com.infosmart.portal.pojo.DwpasStKpiData;
import com.infosmart.portal.pojo.UserExperienceDataInfo;
import com.infosmart.portal.service.UserExperienceService;

@Service
public class UserExperienceServiceImpl extends BaseServiceImpl implements
		UserExperienceService {

	@Override
	public List<ChannelInfo> listChannelInfo(String productId, String reportDate) {
		Map map = new HashMap();
		map.put("productId", productId);
		map.put("reportDate", reportDate.replace("-", ""));
		return this.myBatisDao.getList(
				"com.infosmart.mapper.DwpasStLogCampMapping.listChannelInfo",
				map);
	}
	@Override
	public List<ChannelInfo> listAllChannelInfo(String productId, String reportDate){
		Map map = new HashMap();
		map.put("productId", productId);
		map.put("reportDate", reportDate.replace("-", ""));
		return this.myBatisDao.getList(
				"com.infosmart.mapper.DwpasStLogCampMapping.listAllChannelInfo",
				map);
	}
	
	@Override
	public List<ChannelInfo> listChannelInfoOrderBy(String productId, String reportDate, String orderId, String ascOrDesc){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("reportDate", reportDate.replace("-", ""));
		map.put("orderId", orderId);
		map.put("ascOrDesc", ascOrDesc);
		return this.myBatisDao.getList("com.infosmart.mapper.DwpasStLogCampMapping.listChannelInfoOrderBy",map);
	}

	@Override
	public DwpasStKpiData qryOneByKpiCodeAndDate(String kpiCode,
			String reportDate) {
		Map map = new HashMap();
		map.put("kpiCode", kpiCode);
		map.put("reportDate", reportDate.replace("-", ""));
		return this.myBatisDao
				.get("com.infosmart.mapper.DwpasStKpiDataMapper.qryOneByKpiCodeAndDate",
						map);
	}

	@Override
	public Map<String, UserExperienceDataInfo> listKpiDataByColumnCodeListAndProductIdAndDate(
			String productId, int kpiType, String reportDate,
			List<String> columnCodeList) {
		Map map = new HashMap();
		map.put("productId", productId);
		map.put("kpiType", kpiType);
		map.put("reportDate", reportDate.replace("-", ""));
		map.put("columnCodeList", columnCodeList);
		List<UserExperienceDataInfo> userExperienceDateList = this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasStKpiDataMapper.listKpiDataByColumnCodeListAndPrdId",
						map);
		Map<String, UserExperienceDataInfo> userExperienceDateInfo = new HashMap<String, UserExperienceDataInfo>();
		if (null != userExperienceDateList && userExperienceDateList.size() > 0) {
			for (UserExperienceDataInfo userExperienceDate : userExperienceDateList) {
				userExperienceDateInfo.put(userExperienceDate.getColumnCode(), userExperienceDate);
			}
		}
		return userExperienceDateInfo;
	}
}
