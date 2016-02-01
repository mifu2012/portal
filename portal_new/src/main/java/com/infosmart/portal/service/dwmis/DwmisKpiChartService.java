package com.infosmart.portal.service.dwmis;

import com.infosmart.portal.vo.Chart;

public interface DwmisKpiChartService {
	/**
	 * 得到指标走势图
	 * 
	 * @param kpiCode
	 * @return
	 */
	Chart getTendChartData(String kpiCode);
}
