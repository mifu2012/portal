package com.infosmart.portal.service.dwmis;

import com.infosmart.portal.vo.dwmis.ChartData;

public interface KPILinkChartManager {
	/**
	 * 详情:（特殊接口：可同时被amChart和Web调用） 根据主指标kpiCode获取该 主指标数据、其去年同期数据 及其 其关联指标数据
	 * 
	 * 统计方式：都为“当期值” 时间范围：所有粒度都是往前推两年，去年同期指标则是在去年同期再往前推两年。
	 * 时间粒度：根据period参数决定，默认为日粒度 同时取出和主指标关联的大事记列表(往前推两年)
	 * 
	 * @param kpiCode
	 *            (主指标)
	 * @param period
	 * @param isTable
	 *            true 本接口是为表格准备数据的（无需带单位，无需单位转换） false 为amCharts准备的（带单位，单位转换）'
	 * @param linkedpagecharttype
	 *            0归一，不是初始化的，给的全部的归一值 1 不归一,s是初始化的请求，给的数据只有主指标和去年的证实值
	 * @param domian
	 *            是否是金融模块
	 * @param havaGoal
	 *            是否加上目标线 true加上，false 不加
	 * @param haveEvent
	 *            是否要显示大事记
	 * @return
	 */
	public ChartData getKPIDataForLinkedKPIPage(String kpiCode, int period,
			boolean isTable, String linkedpagecharttype, String domian,
			boolean havaGoal, String haveEvent);
}
