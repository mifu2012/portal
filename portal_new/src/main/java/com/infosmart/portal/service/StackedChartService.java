package com.infosmart.portal.service;

import java.util.List;

import com.infosmart.portal.pojo.DwpasCPrdInfo;
import com.infosmart.portal.vo.Chart;

public interface StackedChartService {
	/**
	 * 得到堆积图
	 * 
	 * @param chartParam
	 * @return
	 */
	Chart getStackedChart(List<DwpasCPrdInfo> productInfoList, String commKpiCode,
			String reportDate, int kpiType);
}
