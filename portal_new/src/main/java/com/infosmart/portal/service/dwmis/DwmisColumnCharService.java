package com.infosmart.portal.service.dwmis;

import com.infosmart.portal.vo.Chart;
import com.infosmart.portal.vo.ChartParam;

public interface DwmisColumnCharService {
	
	public Chart getColumnOrLineChart(ChartParam chartParam,int staCode);

	public Chart getColumnChart(ChartParam chartParam);
}
