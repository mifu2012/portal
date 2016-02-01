package com.infosmart.portal.service.dwmis;

import java.util.List;

import com.infosmart.portal.vo.PieData;

public interface DwmisPieService {

	/**
	 * 生产饼图数据--多个指标
	 * 
	 * @param kpiCodelist
	 *            指标编码集合
	 * @param colorList
	 *            饼图颜色集合
	 * @param reportDate
	 *            查询日期
	 * @param dateType
	 *            日期类型
	 * @return
	 * @throws Exception
	 */
	List<PieData> getDwmisPieDateByCodeList(List<String> kpiCodelist,
			List<String> colorList, int dateType, int staCode, String queryDate)
			throws Exception;
}
