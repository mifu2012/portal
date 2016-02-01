package com.infosmart.portal.service;



import java.util.List;

import com.infosmart.portal.pojo.DwpasStDrogueData;
import com.infosmart.portal.pojo.TrendListDTO;
import com.infosmart.portal.pojo.UserCountStatistics;
public interface AskWindVaneManager {

	/**
	 * 查询所有风向标数据
	 * 
	 */
	//风向标
	public List<DwpasStDrogueData> getAskWindVaneData(String queryDate);
	//支付宝用户数统计
	public List<UserCountStatistics> getUserCountStatistics(String kpiType,String queryDate);
	//支付宝趋势图
	 public List<TrendListDTO> init();
	
}
