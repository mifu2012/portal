package com.infosmart.portal.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasStKpiData;
import com.infosmart.portal.service.ColumnChartService;
import com.infosmart.portal.service.DwpasCKpiInfoService;
import com.infosmart.portal.service.DwpasStKpiDataService;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.vo.Chart;
import com.infosmart.portal.vo.ChartParam;
import com.infosmart.portal.vo.Graph;
import com.infosmart.portal.vo.GraphDataElement;

@Service
public class ColumnChartServiceImpl extends BaseServiceImpl implements
		ColumnChartService {
	// kpi数据
	@Autowired
	private DwpasStKpiDataService dwpasStKpiDataService;
	// kpi信息
	@Autowired
	private DwpasCKpiInfoService dwpasCKpiInfoService;

	private final String chart_type_column = "column";

	private final String chart_type_line = "line";

	@Override
	public Chart getColumnOrLineChart(ChartParam chartParam) {
		this.logger.info("查询并封装趋势图数据:" + chartParam.getEndDate());
		if (chartParam == null || chartParam.getKpiCodes().length == 0) {
			this.logger.warn("得到趋势图数据失败:参数为空");
			return null;
		}
		this.logger.info("------>" + chartParam.getKpiCodes().length);
		// 查询指标信息
		List<DwpasCKpiInfo> kpiInfoList = this.dwpasCKpiInfoService
				.listDwpasCKpiInfoByCodes(Arrays.asList(chartParam
						.getKpiCodes()));
		if (kpiInfoList == null || kpiInfoList.isEmpty()) {
			this.logger.warn("查询指标信息失败,没有数据");
			return null;
		}
		Map<String, DwpasCKpiInfo> kpiInfoMap = new HashMap<String, DwpasCKpiInfo>();
		for (DwpasCKpiInfo kpiInfo : kpiInfoList) {
			kpiInfoMap.put(kpiInfo.getKpiCode(), kpiInfo);
		}
		if (chartParam.getBeginDate() == null
				|| chartParam.getBeginDate().length() == 0) {
			// 默认前两年的数据
			String endDate = chartParam.getEndDate().replaceAll("-", "");
			Date beginDate = null;
			if (chartParam.getKpiType() == DwpasCKpiInfo.KPI_TYPE_OF_MONTH) {
				beginDate = DateUtils.getPreviousMonth(
						DateUtils.parseByFormatRule(endDate, "yyyyMM"),
						Constants.TIME_SPAN_STOCK);
				chartParam.setBeginDate(DateUtils.formatByFormatRule(beginDate,
						"yyyyMM"));
			} else {
				beginDate = DateUtils.getPreviousMonth(
						DateUtils.parseByFormatRule(endDate, "yyyyMMdd"),
						Constants.TIME_SPAN_STOCK);
				chartParam.setBeginDate(DateUtils.formatByFormatRule(beginDate,
						"yyyyMMdd"));
			}
		}
		// 查询指标数据
		Map<String, List<DwpasStKpiData>> kpiDataMap = this.dwpasStKpiDataService
				.listDwpasStKpiDataByKpiCode(kpiInfoList,
						chartParam.getBeginDate(), chartParam.getEndDate(),
						chartParam.getKpiType());
		if (kpiDataMap == null || kpiDataMap.isEmpty()) {
			this.logger.warn("查询指标数据失败,没有数据");
			return null;
		}
		Chart chart = new Chart();
		chart.setChartName("指标矩形图");
		// X轴
		chart.setSeriesMap(this.getSeriesMap(chartParam));
		// y轴
		chart.setGraphList(this.listGraphDataList(kpiDataMap, kpiInfoMap,
				Arrays.asList(chartParam.getChartTypes())));
		return chart;
	}

	/**
	 * x轴的数据集合<日期，日期描述>
	 * 
	 * @param kpiDataMap
	 * @return
	 */
	private LinkedHashMap<String, String> getSeriesMap(ChartParam chartParam) {
		if (chartParam == null || chartParam.getBeginDate() == null
				|| chartParam.getEndDate() == null) {
			this.logger.warn("查询指标数据失败,参数错误");
		}
		Date beginDate = null, endDate = null;
		if (chartParam.getKpiType() == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
			beginDate = DateUtils.parseByFormatRule(chartParam.getBeginDate(),
					"yyyyMMdd");
			endDate = DateUtils.parseByFormatRule(chartParam.getEndDate(),
					"yyyyMMdd");
		} else {
			beginDate = DateUtils.parseByFormatRule(chartParam.getBeginDate(),
					"yyyyMM");
			endDate = DateUtils.parseByFormatRule(chartParam.getEndDate(),
					"yyyyMM");
		}
		LinkedHashMap<String, String> seriesMap = new LinkedHashMap<String, String>();
		while (beginDate.getTime() <= endDate.getTime()) {
			if (chartParam.getKpiType() == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
				seriesMap.put(
						DateUtils.formatByFormatRule(beginDate, "yyyyMMdd"),
						DateUtils.formatByFormatRule(beginDate, "yyyy-MM-dd"));
				// 下一个日期
				beginDate = DateUtils.getNextDate(beginDate);

			} else {
				seriesMap.put(
						DateUtils.formatByFormatRule(beginDate, "yyyyMM"),
						DateUtils.formatByFormatRule(beginDate, "yyyy-MM"));
				// 下一个月份
				beginDate = DateUtils.getNextMonth(beginDate);
			}
		}
		return seriesMap;
	}

	/**
	 * 
	 * @param kpiDataMap
	 *            <kpi_code,数据<日期，数据>>
	 * @return
	 */
	private List<Graph> listGraphDataList(
			Map<String, List<DwpasStKpiData>> kpiDataMap,
			Map<String, DwpasCKpiInfo> kpiInfoMap, List<String> chartTypeList) {
		if (kpiDataMap == null || kpiDataMap.isEmpty()) {
			this.logger.warn("生成矩形图没有数据");
			return null;
		}
		if (kpiInfoMap == null || kpiInfoMap.isEmpty()) {
			this.logger.warn("生成矩形图没有数据");
			return null;
		}
		List<Graph> graphList = new ArrayList<Graph>();
		Graph graph = null;
		Entry entry = null;
		List<DwpasStKpiData> kpiDataList = null;
		List<GraphDataElement> graphValueList = null;
		GraphDataElement graphDataElement = null;
		int i = 0;
		for (Iterator it = kpiDataMap.entrySet().iterator(); it.hasNext();) {
			entry = (Entry) it.next();
			graph = new Graph();
			graph.setGraphId(entry.getKey().toString());
			graph.setGraphColor(Constants.CHART_COLOR_LIST.get(i));
			graph.setGraphName(kpiInfoMap.get(entry.getKey().toString())
					.getDispName());
			try {
				if (this.chart_type_column.equalsIgnoreCase(chartTypeList
						.get(i))) {
					graph.setGraphType(Graph.GRAPH_TYPE_COLUMN);
				} else {
					graph.setGraphType(Graph.GRAPH_TYPE_LINE);
				}
			} catch (Exception e) {
				graph.setGraphType(Graph.GRAPH_TYPE_COLUMN);
			}
			kpiDataList = (List<DwpasStKpiData>) entry.getValue();
			graphValueList = new ArrayList<GraphDataElement>();
			for (DwpasStKpiData kpiData : kpiDataList) {
				graphDataElement = new GraphDataElement();
				graphDataElement.setValueDate(kpiData.getReportDate());
				//
				graphDataElement.setValue(kpiData.getShowValue().toString());
				graphValueList.add(graphDataElement);
			}
			graph.setDataList(graphValueList);
			//
			graphList.add(graph);
			//
			i++;
		}
		return graphList;
	}
}
