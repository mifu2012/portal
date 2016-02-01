package com.infosmart.portal.service.dwmis;

import com.infosmart.portal.vo.Chart;
import com.infosmart.portal.vo.dwmis.ChartParam;

public interface DwmisLineService {

	/**
	 * 无目标线的折线图信息
	 * @param chartParam
	 * @param staCode
	 * @return
	 * @throws Exception
	 */
	public Chart getLineChart(ChartParam chartParam, int staCode)
			throws Exception;

	/**
	 * 有目标线的折线图信息
	 * @param chartParam
	 * @param staCode
	 * @return
	 * @throws Exception
	 */
	public Chart getLineChartShowWarnLine(ChartParam chartParam, int staCode)
			throws Exception;;
}
