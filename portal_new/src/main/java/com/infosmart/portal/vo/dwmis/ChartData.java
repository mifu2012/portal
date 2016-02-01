package com.infosmart.portal.vo.dwmis;

import java.util.List;

public class ChartData {

	// 指定该图表的类别
	// 1-KPI跟踪页面的，2-异动分析页面的，3-详情分析页面的，4-前钻分析页面的，5-大事记页面的
	private int pageID;

	// 该图标上各条线的数据
	private List<LineData> lineList;

	// 主指标线
	private LineData mainLine;

	// 去年同期线
	private LineData lastYearLine;

	/*
	 * // 大事记数据 private List<ChartEvent> chartEvent;
	 */

	// 记录主指标的最大数
	private double maxNumber;

	/*
	 * public List<ChartEvent> getChartEvent() { return chartEvent; }
	 * 
	 * public void setChartEvent(List<ChartEvent> chartEvent) { this.chartEvent
	 * = chartEvent; }
	 */

	/**
	 * @param pageID
	 *            the pageID to set
	 */
	public void setPageID(int pageID) {
		this.pageID = pageID;
	}

	/**
	 * @return the pageID
	 */
	public int getPageID() {
		return pageID;
	}

	public List<LineData> getLineList() {
		return lineList;
	}

	public void setLineList(List<LineData> lineList) {
		this.lineList = lineList;
	}

	public LineData getMainLine() {
		return mainLine;
	}

	public void setMainLine(LineData mainLine) {
		this.mainLine = mainLine;
	}

	public LineData getLastYearLine() {
		return lastYearLine;
	}

	public void setLastYearLine(LineData lastYearLine) {
		this.lastYearLine = lastYearLine;
	}

	public double getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(double maxNumber) {
		this.maxNumber = maxNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((lastYearLine == null) ? 0 : lastYearLine.hashCode());
		result = prime * result
				+ ((lineList == null) ? 0 : lineList.hashCode());
		result = prime * result
				+ ((mainLine == null) ? 0 : mainLine.hashCode());
		long temp;
		temp = Double.doubleToLongBits(maxNumber);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + pageID;
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
		ChartData other = (ChartData) obj;
		if (lastYearLine == null) {
			if (other.lastYearLine != null)
				return false;
		} else if (!lastYearLine.equals(other.lastYearLine))
			return false;
		if (lineList == null) {
			if (other.lineList != null)
				return false;
		} else if (!lineList.equals(other.lineList))
			return false;
		if (mainLine == null) {
			if (other.mainLine != null)
				return false;
		} else if (!mainLine.equals(other.mainLine))
			return false;
		if (Double.doubleToLongBits(maxNumber) != Double
				.doubleToLongBits(other.maxNumber))
			return false;
		if (pageID != other.pageID)
			return false;
		return true;
	}
}
