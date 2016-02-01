package com.infosmart.portal.service;

import com.infosmart.portal.vo.Chart;
import com.infosmart.portal.vo.ChartParam;

public interface StockChartService {
	/**
	 * 得到趋势图
	 * 
	 * @param chartParam
	 * @return
	 */
	Chart getStockChart(ChartParam chartParam);
}
