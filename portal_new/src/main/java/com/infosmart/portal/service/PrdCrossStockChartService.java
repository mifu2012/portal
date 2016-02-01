package com.infosmart.portal.service;

import com.infosmart.portal.vo.Chart;
import com.infosmart.portal.vo.dwpas.PrdCrossParam;

/**
 * 产品交叉 趋势图
 * 
 * @author Administrator
 * 
 */
public interface PrdCrossStockChartService {
	/**
	 * 产品交叉 趋势图 
	 * 
	 * @param chartParam
	 * @return
	 */
	Chart getStockChart(PrdCrossParam prdCrossParam);
}
