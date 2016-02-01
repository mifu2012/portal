package com.infosmart.portal.vo;

import java.util.HashMap;
import java.util.Map;

import com.infosmart.portal.pojo.DwpasCKpiInfo;

/**
 * 趋势图显示参数
 * 
 * @author changwei.ye
 * @version $Id: ChartParam.java, v 0.1 2011-10-31 下午06:08:51 changwei.ye Exp $
 */
public class ChartParam {
	private String needPercent; // 是否归一化
	private String isMIS;
	private String hasEvent; // 是否需要大事件
	private String eventType; // 搜索的大事件类型
	private String eventSearchKey;// 搜索的大事件关键字
	private String[] kpiCodes; // 趋势图的kpiCode
	private String[] chartTypes; // 图的类型
	private int kpiType = 0;// 数据类型
	private String[] colors; // 线的颜色
	private String beginDate;// 查询开始日期
	private String endDate; // 查询结束时间
	private String value1;
	private String value2;
	private String value3;
	private String value4;
	private String kpiCode;

	private int dateType;// 时间粒度

	private String isPrd;// 1只显示绑定指标的大事件

	public String getIsPrd() {
		return isPrd;
	}

	public void setIsPrd(String isPrd) {
		this.isPrd = isPrd;
	}

	public int getDateType() {
		return dateType;
	}

	public void setDateType(int dateType) {
		this.dateType = dateType;
	}

	public String getKpiCode() {
		return kpiCode;
	}

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}

	public String getValue4() {
		return value4;
	}

	public void setValue4(String value4) {
		this.value4 = value4;
	}

	private Map<String, String> colorMap;

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String[] getChartTypes() {
		return chartTypes;
	}

	public void setChartTypes(String[] chartTypes) {
		this.chartTypes = chartTypes;
	}

	public int getKpiType() {
		if (endDate != null && this.kpiType <= 0) {
			String reportDate = endDate.replace("-", "");
			this.kpiType = reportDate.length() == 8 ? DwpasCKpiInfo.KPI_TYPE_OF_DAY
					: DwpasCKpiInfo.KPI_TYPE_OF_MONTH;
		}
		return kpiType;
	}

	public void setKpiType(int kpiType) {
		this.kpiType = kpiType;
	}

	public ChartParam() {
		colorMap = new HashMap<String, String>();
	}

	public ChartParam(String needPercent, String hasEvent, String isMIS,
			String eventType, String eventSearchKey, String endDate,
			String[] kpiCodes, String[] colors, String isPrd) {
		initChartParam(needPercent, hasEvent, isMIS, eventType, eventSearchKey,
				endDate, kpiCodes, colors, isPrd);
	}

	private void initChartParam(String needPercent, String hasEvent,
			String isMIS, String eventType, String eventSearchKey,
			String endDate, String[] kpiCodes, String[] colors, String isPrd) {
		setNeedPercent(needPercent);
		setHasEvent(hasEvent);
		setIsMIS(isMIS);
		setEventType(eventType);
		setEventSearchKey(eventSearchKey);
		setKpiCodes(kpiCodes);
		setColors(colors);
		setEndDate(endDate);
		setIsPrd(isPrd);
	}

	public String getNeedPercent() {
		return needPercent;
	}

	public void setNeedPercent(String needPercent) {
		this.needPercent = needPercent;
	}

	public String getHasEvent() {
		return hasEvent;
	}

	public void setHasEvent(String hasEvent) {
		this.hasEvent = hasEvent;
	}

	public String[] getKpiCodes() {
		return kpiCodes;
	}

	public void setKpiCodes(String[] kpiCodes) {
		this.kpiCodes = kpiCodes;
	}

	public String[] getColors() {
		return colors;
	}

	public void setColors(String[] colors) {
		this.colors = colors;
		colorMap = new HashMap<String, String>();
		if (colors == null || colors.length == 0) {
			return;
		}
		if (kpiCodes == null || kpiCodes.length == 0) {
			return;
		}
		int len = kpiCodes.length >= colors.length ? colors.length
				: kpiCodes.length;
		for (int i = 0; i < len; i++) {
			colorMap.put(kpiCodes[i], colors[i]);
		}
	}

	public String getColorByKpiCode(String kpiCode) {
		return colorMap.get(kpiCode);
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEventSearchKey() {
		return eventSearchKey;
	}

	public void setEventSearchKey(String eventSearchKey) {
		this.eventSearchKey = eventSearchKey;
	}

	public String getIsMIS() {
		return isMIS;
	}

	public void setIsMIS(String isMIS) {
		this.isMIS = isMIS;
	}
}
