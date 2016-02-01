package com.infosmart.portal.vo.dwmis;

import java.util.List;

import com.infosmart.portal.pojo.dwmis.DwmisKpiInfo;

public class LineData {
	// 指标数据的DO
	private DwmisKpiInfo kpiInfo;

	// 该指标是否有数据
	private boolean hasData;

	// 线类型
	private int lineType;

	// 线左右位置
	private String axis;

	public static final int NORMAL_LINE = 1; // 一般类型
	public static final int GOAL_LINE = 2; // 目标线
	public static final int LAST_YEAR_LINE = 3; // 去年同期线
	public static final int MAIN_LINE = 4; // 主线，用于详情分析、前钻分析页面中保存“观察对象指标”的。

	public static final int NEW_LINE_FOR_FINANCE = 5; // 主线，用于详情分析、前钻分析页面中保存“观察对象指标”的。

	public static final int OLD_LINE_FOR_FINANCE = 6; // 主线，用于详情分析、前钻分析页面中保存“观察对象指标”的。
	// 该线数据值
	private List<LineElem> dataList;

	// 大事记数据
	private List<ChartEvent> chartEvent;

	public List<ChartEvent> getChartEvent() {
		return chartEvent;
	}

	public void setChartEvent(List<ChartEvent> chartEvent) {
		this.chartEvent = chartEvent;
	}

	public void setDataList(List<LineElem> dataList) {
		this.dataList = dataList;
	}

	public List<LineElem> getDataList() {
		return dataList;
	}

	/**
	 * @param lineType
	 *            the lineType to set
	 */
	public void setLineType(int lineType) {
		this.lineType = lineType;
	}

	/**
	 * @return the lineType
	 */
	public int getLineType() {
		return lineType;
	}

	public DwmisKpiInfo getKpiInfo() {
		return kpiInfo;
	}

	public void setKpiInfo(DwmisKpiInfo kpiInfo) {
		this.kpiInfo = kpiInfo;
	}

	public boolean getHasData() {
		return hasData;
	}

	public void setHasData(boolean hasData) {
		this.hasData = hasData;
	}

	public String getAxis() {
		return axis;
	}

	public void setAxis(String axis) {
		this.axis = axis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((chartEvent == null) ? 0 : chartEvent.hashCode());
		result = prime * result
				+ ((dataList == null) ? 0 : dataList.hashCode());
		result = prime * result + (hasData ? 1231 : 1237);
		result = prime * result + ((kpiInfo == null) ? 0 : kpiInfo.hashCode());
		result = prime * result + lineType;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LineData other = (LineData) obj;
		if (chartEvent == null) {
			if (other.chartEvent != null)
				return false;
		} else if (!chartEvent.equals(other.chartEvent))
			return false;
		if (dataList == null) {
			if (other.dataList != null)
				return false;
		} else if (!dataList.equals(other.dataList))
			return false;
		if (hasData != other.hasData)
			return false;
		if (kpiInfo == null) {
			if (other.kpiInfo != null)
				return false;
		} else if (!kpiInfo.equals(other.kpiInfo))
			return false;
		if (lineType != other.lineType)
			return false;
		return true;
	}
}
