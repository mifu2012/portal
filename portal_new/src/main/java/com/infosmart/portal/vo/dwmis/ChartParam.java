package com.infosmart.portal.vo.dwmis;

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
	private int staCode; // 统计方式
	private String eventType; // 搜索的大事件类型
	private String eventSearchKey;// 搜索的大事件关键字
	private String[] kpiCodes; // 趋势图的kpiCode
	private String[] chartTypes; // 图的类型
	private int kpiType = 0;// 数据类型
	private String[] colors; // 线的颜色
	private String beginDate;// 查询开始日期
	private String endDate; // 查询结束时间
	private String lastYearValue;// 是否查询去年同期值 1为是 0为否 默认为0
	private String isProphaseValue;// 是否计算前期值 和环比
	private int dateType;
	private String kpiCode;

	private String yesOrNoDetailsAnalysis;// 是否详细分析指标图
	private Map<String, String> colorMap;

	public String getYesOrNoDetailsAnalysis() {
		return yesOrNoDetailsAnalysis;
	}

	public void setYesOrNoDetailsAnalysis(String yesOrNoDetailsAnalysis) {
		this.yesOrNoDetailsAnalysis = yesOrNoDetailsAnalysis;
	}

	public String getIsProphaseValue() {
		return isProphaseValue;
	}

	public void setIsProphaseValue(String isProphaseValue) {
		this.isProphaseValue = isProphaseValue;
	}

	public String getLastYearValue() {
		return lastYearValue;
	}

	public void setLastYearValue(String lastYearValue) {
		this.lastYearValue = lastYearValue;
	}

	public int getStaCode() {
		return staCode;
	}

	public void setStaCode(int staCode) {
		this.staCode = staCode;
	}

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
			String eventType, String eventSearchKey, int staCode,
			String endDate, String beginDate, String[] kpiCodes,
			String[] colors, String lastYearValue, String isProphaseValue,
			String yesOrNoDetailsAnalysis) {
		initChartParam(needPercent, hasEvent, isMIS, eventType, eventSearchKey,
				staCode, endDate, beginDate, kpiCodes, colors, lastYearValue,
				isProphaseValue, yesOrNoDetailsAnalysis);
	}

	private void initChartParam(String needPercent, String hasEvent,
			String isMIS, String eventType, String eventSearchKey, int staCode,
			String endDate, String beginDate, String[] kpiCodes,
			String[] colors, String lastYearValue, String isProphaseValue,
			String yesOrNoDetailsAnalysis) {
		setNeedPercent(needPercent);
		setHasEvent(hasEvent);
		setIsMIS(isMIS);
		setEventType(eventType);
		setEventSearchKey(eventSearchKey);
		setKpiCodes(kpiCodes);
		setColors(colors);
		setEndDate(endDate);
		setBeginDate(beginDate);
		setStaCode(staCode);
		setLastYearValue(lastYearValue);
		setIsProphaseValue(isProphaseValue);
		setYesOrNoDetailsAnalysis(yesOrNoDetailsAnalysis);
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

}
