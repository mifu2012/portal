package com.infosmart.portal.service.dwmis;

import com.infosmart.portal.vo.Chart;
import com.infosmart.portal.vo.dwmis.ChartParam;


public interface MisStockChartService {
	/**
	 * 得到趋势图
	 * 
	 * @param chartParam
	 * @return
	 */
	Chart getStockChart(ChartParam chartParam);
}
