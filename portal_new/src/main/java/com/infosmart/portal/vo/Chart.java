package com.infosmart.portal.vo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 指标走势图表描述信息 1：折线图 2：峰值图：现在峰值图不需要后台提供数据信息。在打开复层时由父页面提供数据。
 * 
 * @author wb-songxd
 */
public class Chart {
	/**
	 * 图类型
	 */
	private String chartType = "column";
	/**
	 * 图表的名称
	 */
	private String chartName;

	private String legendPosition = "top";

	/**
	 * 折线集合
	 */
	private List<Graph> graphList;

	/**
	 * 为详情和金融dashboard保存最大、最小值
	 */
	private Map<String, Double> chartValue;

	/**
	 * 面积图数据集 map<data,values>
	 */
	private TreeMap<String, String> areaDataMap = new TreeMap<String, String>();

	/** x轴的数据集合<日期，日期描述> */
	private LinkedHashMap<String, String> seriesMap = new LinkedHashMap<String, String>();

	private TreeMap<String, List<GraphDataElement>> graphs = new TreeMap<String, List<GraphDataElement>>();

	/** 大事记和详情记录主指标的最大数（详情的maxNumber是通过chartValue来计算） */
	private double maxNumber;

	private int allPercent;// 全为百分比 1为不都是 ，0 为不全是

	public int getAllPercent() {
		return allPercent;
	}

	public void setAllPercent(int allPercent) {
		this.allPercent = allPercent;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public String getLegendPosition() {
		return legendPosition;
	}

	public void setLegendPosition(String legendPosition) {
		this.legendPosition = legendPosition;
	}

	// =================getter and setter=============================
	/**
	 * 增加数据
	 * 
	 * @param graphDataElement
	 */
	public void addGraphs(String key, List<GraphDataElement> graphList) {
		if (this.graphs == null)
			graphs = new TreeMap<String, List<GraphDataElement>>();
		graphs.put(key, graphList);
	}

	public String getChartName() {
		return chartName;
	}

	public Map<String, Double> getChartValue() {
		return chartValue;
	}

	public void setChartValue(Map<String, Double> chartValue) {
		this.chartValue = chartValue;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
	}

	public List<Graph> getGraphList() {
		return graphList;
	}

	public void setGraphList(List<Graph> graphList) {
		this.graphList = graphList;
	}

	public LinkedHashMap<String, String> getSeriesMap() {
		if (this.seriesMap == null) {
			this.seriesMap = new LinkedHashMap<String, String>();
		}
		return seriesMap;
	}

	public void setSeriesMap(LinkedHashMap<String, String> seriesMap) {
		this.seriesMap = seriesMap;
	}

	public TreeMap<String, String> getAreaDataMap() {
		return areaDataMap;
	}

	public void setAreaDataMap(TreeMap<String, String> areaDataMap) {
		this.areaDataMap = areaDataMap;
	}

	public double getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(double maxNumber) {
		this.maxNumber = maxNumber;
	}

	public TreeMap<String, List<GraphDataElement>> getGraphs() {
		return graphs;
	}

	public void setGraphs(TreeMap<String, List<GraphDataElement>> graphs) {
		this.graphs = graphs;
	}

}