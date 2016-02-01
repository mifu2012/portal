package com.infosmart.portal.service.dwmis.impl;

import org.springframework.stereotype.Service;

import com.infosmart.portal.service.dwmis.DwmisKpiChartService;
import com.infosmart.portal.vo.Chart;
import com.infosmart.portal.vo.dwmis.ChartData;

@Service
public class DwmisKpiChartServiceImpl extends KPIChartManagerImpl implements
		DwmisKpiChartService {

	@Override
	public Chart getTendChartData(String kpiCode) {
		ChartData chartData = this.getChartDataForKPITrendPage(kpiCode);
		if (chartData == null) {
			return null;
		}
		return getTendChartData(chartData, "");
	}

}
