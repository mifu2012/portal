package com.infosmart.portal.service;

import com.infosmart.portal.vo.Chart;
import com.infosmart.portal.vo.ChartParam;

/**
 * 矩形图或折线图
 * 
 * @author infosmart
 * 
 */
public interface ColumnChartService {
	/**
	 * 矩形图或折线图
	 * 
	 * @param chartParam
	 * @return
	 */
	Chart getColumnOrLineChart(ChartParam chartParam);
}
