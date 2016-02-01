package com.infosmart.portal.service;

import java.util.List;

import com.infosmart.portal.pojo.TrendListDTO;

public interface ProdRankService {

	/**
	 * 根据时间获取交叉用户指标值
	 * 
	 * @param date
	 *            查询日期
	 * @return 指标对象集合
	 */
	public List<Object> getCrossUserKpiDataByDate(String date);

	/**
	 * 根据时间获取产品排行指标值
	 * 
	 * @param templateId
	 *            模板ID
	 * @param date
	 *            查询时间
	 * @return 指标值对象集合
	 */
	public List<Object> getProdKpiDatas(String templateId, String date,
			String kpiType);

	/**
	 * 平台交叉分析趋势图
	 * 
	 * @return
	 */
	public List<TrendListDTO> init();

}
