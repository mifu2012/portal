package com.infosmart.portal.service;

import java.util.List;
import java.util.Map;

import com.infosmart.portal.pojo.ChannelInfo;
import com.infosmart.portal.pojo.DwpasStKpiData;
import com.infosmart.portal.pojo.UserExperienceDataInfo;

public interface UserExperienceService {

	/**
	 * 查询用户引入渠道信息 prodectId 产品Id reportDate 时间
	 */
	List<ChannelInfo> listChannelInfo(String productId, String reportDate);
	
	
	/**
	 * 查询所有用户引入渠道信息 prodectId 产品Id reportDate 时间
	 */
	List<ChannelInfo> listAllChannelInfo(String productId, String reportDate);

	/**
	 * 根据kpiCode和reportDate查询kpidate数据 kpiCode reportDate
	 */
	DwpasStKpiData qryOneByKpiCodeAndDate(String kpiCode, String reportDate);

	Map<String, UserExperienceDataInfo> listKpiDataByColumnCodeListAndProductIdAndDate(
			String productId, int kpiType, String reportDate,
			List<String> columnCodeList);
	/**
	 * 查询渠道引入信息
	 * @param productId
	 * @param reportDate
	 * @param orderId
	 * @param isAsc
	 * @return
	 */
	List<ChannelInfo> listChannelInfoOrderBy(String productId, String reportDate, String orderId, String ascOrDesc);

}
