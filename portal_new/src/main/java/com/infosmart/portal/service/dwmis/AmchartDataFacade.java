package com.infosmart.portal.service.dwmis;

import com.infosmart.portal.vo.Chart;

public interface AmchartDataFacade {
	/**
	 * 详情分析图表展现,为了不改懂以前的代码，添加了该方法。<br/>
	 * 原有的方法，再调用改方法<br/>
	 * 
	 * @param kpicode
	 *            kpi 标识
	 * @param period
	 *            日(1002)，周1003，月：1004 季：1005 周期 30天 12周 12月
	 * @param needPercent 
	 *            是否需要归一化 （true or false）
	 * @param linkedpagecharttype original：原始值（默认值） percent： 归一化后的值
	 *            0归一，不是初始化的，给的全部的归一值 1 不归一,s是初始化的请求，给的数据只有主指标和去年的证实值
	 * @param hidden graphId
	 *            隐藏
	 * @param domain
	 *            是否是金融模块
	 * @param haveGoal
	 *            是否加上目标线 true加上，false 不加
	 * @param haveEvent  
	 *            是否要显示大事记(true or false)
	 * @param lineType
	 *            每条线对应的类型
	 * @return Chart 图表信息
	 * */
	Chart getKpiDetailData(String kpiCode, String period,
			String linkedPageChartType, String needPercent, String hidden,
			String domain, boolean haveGoal, String haveEvent, String lineType);

}
