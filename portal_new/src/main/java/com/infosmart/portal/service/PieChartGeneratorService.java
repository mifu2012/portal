package com.infosmart.portal.service;

import java.util.List;

import com.infosmart.portal.vo.PieData;

public interface PieChartGeneratorService {
	/**
	 * 生成饼图数据
	 * 
	 * @param kpiCodeList
	 *            kpi指标集合
	 * @param colorList
	 *            颜色集合
	 * @param reportDate
	 *            查询日期 格式为yyyyMM或yyyyMMdd
	 * @param dateType
	 *            报表类型 D/W/M
	 * @return
	 */
	List<PieData> getPieDate(List<String> kpiCodeList, List<String> colorList,
			String reportDate, String dateType);
}
