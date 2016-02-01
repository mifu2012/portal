package com.infosmart.portal.vo;

import java.util.ArrayList;
import java.util.List;

import com.infosmart.portal.pojo.BigEventPo;
import com.infosmart.portal.pojo.dwmis.MisEventPo;

/**
 * 图表中线的描述类
 * 
 * @author wb-songxd
 */
public class Graph {

	/**
	 * 线的id
	 */
	private String graphId;

	/**
	 * 线是否隐藏，默认是显示
	 * */
	private String ishidden = "1";

	/**
	 * 线的名称
	 */
	private String graphName;

	/**
	 * 线的类型：1：实际趋势线；2:目标线 3：去年同期
	 */
	private int graphType;

	public static final int GRAPH_TYPE_TREND_LINE = 1;

	public static final int GRAPH_TYPE_GOAL_LINE = 2;

	public static final int GRAPH_TYPE_YEAR_ON_GROWTH = 3;

	public static final int GRAPH_TYPE_COLUMN = 4;

	public static final int GRAPH_TYPE_LINE = 5;

	private String graphTypeDesc;

	/**
	 * 是否在详情页面锁定数据范围的标识,0不勾选，1是勾选有效
	 */
	private Integer isDataRangeFix;

	/**
	 * 锁定数据范围的下限
	 */
	private Double dataRangeBottom;

	/**
	 * 锁定数据范围的上限
	 */
	private Double dataRangeTop;

	/**
	 * 单位名称
	 * */

	private String unitStr;

	/**
	 * 线的颜色 如 红色：#CC0000
	 */
	private String graphColor;

	private String remark;

	private String balloonColor;

	private String balloonText;

	// 单位Id
	private Integer unitId;
	/**
	 * 数据集合：date 、value
	 */
	private List<GraphDataElement> dataList = new ArrayList<GraphDataElement>();

	/**
	 * 大事件集合
	 */
	private List<BigEventPo> eventList;
	/**
	 * 大事件集合
	 */
	private List<MisEventPo> misEventList;

	// ================setter and getter==================================
	public String getGraphTypeDesc() {
		if (graphType == 4) {
			graphTypeDesc = "column";
		} else if (graphType == 5) {
			graphTypeDesc = "line";
		}
		return graphTypeDesc;
	}

	public void setGraphTypeDesc(String graphTypeDesc) {
		this.graphTypeDesc = graphTypeDesc;
	}

	/**
	 * 增加数据
	 * 
	 * @param graphDataElement
	 */
	public void addGraphDatElement(GraphDataElement graphDataElement) {
		if (this.dataList == null)
			dataList = new ArrayList<GraphDataElement>();
		this.dataList.add(graphDataElement);
	}

	public String getGraphId() {
		return graphId;
	}

	public void setGraphId(String graphId) {
		this.graphId = graphId;
	}

	public String getIshidden() {
		return ishidden;
	}

	public void setIshidden(String ishidden) {
		this.ishidden = ishidden;
	}

	public Integer getIsDataRangeFix() {
		return isDataRangeFix;
	}

	public void setIsDataRangeFix(Integer isDataRangeFix) {
		this.isDataRangeFix = isDataRangeFix;
	}

	public Double getDataRangeBottom() {
		return dataRangeBottom;
	}

	public void setDataRangeBottom(Double dataRangeBottom) {
		this.dataRangeBottom = dataRangeBottom;
	}

	public Double getDataRangeTop() {
		return dataRangeTop;
	}

	public void setDataRangeTop(Double dataRangeTop) {
		this.dataRangeTop = dataRangeTop;
	}

	public String getUnitStr() {
		return unitStr;
	}

	public void setUnitStr(String unitStr) {
		this.unitStr = unitStr;
	}

	public String getGraphName() {
		return graphName;
	}

	public void setGraphName(String graphName) {
		this.graphName = graphName;
	}

	public int getGraphType() {
		return graphType;
	}

	public void setGraphType(int graphType) {
		this.graphType = graphType;
	}

	public List<GraphDataElement> getDataList() {
		return dataList;
	}

	public void setDataList(List<GraphDataElement> dataList) {
		this.dataList = dataList;
	}

	public String getGraphColor() {
		return graphColor;
	}

	public void setGraphColor(String graphColor) {
		this.graphColor = graphColor;
	}

	public List<BigEventPo> getEventList() {
		return eventList;
	}

	public void setEventList(List<BigEventPo> eventList) {
		this.eventList = eventList;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBalloonColor() {
		return balloonColor;
	}

	public void setBalloonColor(String balloonColor) {
		this.balloonColor = balloonColor;
	}

	public String getBalloonText() {
		return balloonText;
	}

	public void setBalloonText(String balloonText) {
		this.balloonText = balloonText;
	}

	public static int getGRAPH_TYPE_TREND_LINE() {
		return GRAPH_TYPE_TREND_LINE;
	}

	public static int getGRAPH_TYPE_GOAL_LINE() {
		return GRAPH_TYPE_GOAL_LINE;
	}

	public static int getGRAPH_TYPE_YEAR_ON_GROWTH() {
		return GRAPH_TYPE_YEAR_ON_GROWTH;
	}

	public List<MisEventPo> getMisEventList() {
		return misEventList;
	}

	public void setMisEventList(List<MisEventPo> misEventList) {
		this.misEventList = misEventList;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

}
