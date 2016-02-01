package com.infosmart.portal.service.dwmis.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.dwmis.DwmisKpiData;
import com.infosmart.portal.pojo.dwmis.DwmisKpiInfo;
import com.infosmart.portal.service.dwmis.DwmisColumnCharService;
import com.infosmart.portal.service.dwmis.DwmisKpiDataService;
import com.infosmart.portal.service.dwmis.DwmisKpiInfoService;
import com.infosmart.portal.service.impl.BaseServiceImpl;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.dwmis.CoreConstant;
import com.infosmart.portal.util.dwmis.TimeFormatProcessor;
import com.infosmart.portal.vo.Chart;
import com.infosmart.portal.vo.ChartParam;
import com.infosmart.portal.vo.Graph;
import com.infosmart.portal.vo.GraphDataElement;

@Service
public class DwmisColumnChartServiceImpl extends BaseServiceImpl implements
		DwmisColumnCharService {

	@Autowired
	private DwmisKpiDataService dwmisKpiDataService;

	@Autowired
	private DwmisKpiInfoService dwmisKpiInfoService;

	private final String chart_type_column = "column";

	@Override
	public Chart getColumnOrLineChart(ChartParam chartParam, int staCode) {

		this.logger.info("查询并封装趋势图数据:" + chartParam.getEndDate());
		if (chartParam == null || chartParam.getKpiCodes().length == 0) {
			this.logger.warn("得到趋势图数据失败:参数为空");
			return null;
		}
		this.logger.info("------>" + chartParam.getKpiCodes().length);
		// 查询指标数据
		Map<String, List<DwmisKpiData>> dwmisKpiDataMap = new HashMap<String, List<DwmisKpiData>>();
		for (int i = 0; i < chartParam.getKpiCodes().length; i++) {
			// 查询指标信息
			DwmisKpiInfo kpiInfo = this.dwmisKpiInfoService
					.getDwmisKpiInfoByCode(chartParam.getKpiCodes()[i]
							.toString());
			if (kpiInfo == null) {
				this.logger.warn("取指标信息失败："
						+ chartParam.getKpiCodes()[i].toString());
				continue;
			}
			Date endDate = null;
			Date beginDate = null;
			if (chartParam.getDateType() == CoreConstant.DAY_PERIOD) {
				// 日粒度 取得查询时间
				// endDate = DateUtils.parseByFormatRule(TimeFormatProcessor
				// .getCurrentDate(kpiInfo.getDayOffSet()), "yyyy-MM-dd");
				endDate = DateUtils.parseByFormatRule(chartParam.getEndDate(),
						"yyyy-MM-dd");

				beginDate = DateUtils.getPrevious7Date(endDate);
			} else if (chartParam.getDateType() == CoreConstant.WEEK_PERIOD) {
				// 周粒度 取得该星期的周日
				// endDate = TimeFormatProcessor
				// .getBelongSunDay(TimeFormatProcessor.getSysDate(kpiInfo
				// .getDayOffSet()));
				endDate = TimeFormatProcessor.getBelongSunDay(DateUtils
						.parseByFormatRule(chartParam.getEndDate(),
								"yyyy-MM-dd"));
				beginDate = DateUtils.getPreviousWeek(endDate,
						CoreConstant.PREVIOUS_WEEK_COUNT);
			} else {
				// 月粒度 取得该月的最后一天
				// endDate = TimeFormatProcessor
				// .getLastDayOfMonth(TimeFormatProcessor
				// .getSysDate(kpiInfo.getDayOffSet()));
				endDate = TimeFormatProcessor.getLastDayOfMonth(DateUtils
						.parseByFormatRule(chartParam.getEndDate(),
								"yyyy-MM-dd"));
				beginDate = DateUtils.getPreviousMonth(endDate,
						CoreConstant.PREVIOUS_MONTH_COUNT);
			}
			chartParam.setBeginDate(DateUtils.fotmatDate4(beginDate));
			chartParam.setEndDate(DateUtils.fotmatDate4(endDate));

			// 查询指标数据
			List<DwmisKpiData> kpiDataList = this.dwmisKpiDataService
					.kpiDataListByParam(chartParam.getKpiCodes()[i].toString(),
							chartParam.getDateType(), staCode, DateUtils
									.parseByFormatRule(
											chartParam.getBeginDate(),
											"yyyy-MM-dd"), DateUtils
									.parseByFormatRule(chartParam.getEndDate(),
											"yyyy-MM-dd"));
			if (kpiDataList == null || kpiDataList.isEmpty()) {
				this.logger.warn("查询指标数据失败,没有数据");
				continue;
			}
			for (DwmisKpiData kd : kpiDataList) {
				if (kd == null)
					continue;
				// this.logger.info("------------>kpi_date:"+kd.getShowReportDate());
				if (dwmisKpiDataMap.containsKey(kd.getKpiCode())) {
					dwmisKpiDataMap.get(kd.getKpiCode()).add(kd);
				} else {
					List<DwmisKpiData> kdList = new ArrayList<DwmisKpiData>();
					kdList.add(kd);
					dwmisKpiDataMap.put(kd.getKpiCode(), kdList);
				}
			}

		}
		if (dwmisKpiDataMap == null || dwmisKpiDataMap.isEmpty()) {
			this.logger.warn("查询指标数据失败,没有数据");
			return null;
		}
		Map<String, DwmisKpiInfo> dwmisKpiInfoMap = new HashMap<String, DwmisKpiInfo>();
		Map.Entry entry = null;
		DwmisKpiData kpiData = null;
		for (Iterator it = dwmisKpiDataMap.entrySet().iterator(); it.hasNext();) {
			entry = (Map.Entry) it.next();
			if (entry.getValue() == null
					|| ((List<DwmisKpiData>) entry.getValue()).isEmpty())
				continue;
			kpiData = ((List<DwmisKpiData>) entry.getValue()).get(0);
			if (!dwmisKpiInfoMap.containsKey(kpiData.getKpiCode())) {
				dwmisKpiInfoMap.put(kpiData.getKpiCode(),
						kpiData.getDwmisKpiInfo());
			}
		}
		Chart chart = new Chart();
		chart.setChartName("指标矩形图");
		// X轴
		chart.setSeriesMap(this.getSeriesMap(chartParam));
		// y轴
		chart.setGraphList(this.listGraphDataList(dwmisKpiDataMap,
				dwmisKpiInfoMap, Arrays.asList(chartParam.getChartTypes())));
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
		Date beginDate = null;
		Date endDate = null;

		switch (chartParam.getDateType()) {
		case CoreConstant.DAY_PERIOD: {
			// 日粒度
			beginDate = DateUtils.parseByFormatRule(chartParam.getBeginDate()
					.replace("-", ""), "yyyyMMdd");
			endDate = DateUtils.parseByFormatRule(chartParam.getEndDate()
					.replace("-", ""), "yyyyMMdd");
			break;
		}
		case CoreConstant.WEEK_PERIOD: {
			// 周粒度
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			// 开始日期所在周最后一天
			cal.setTime(DateUtils.parseByFormatRule(chartParam.getBeginDate()
					.replace("-", ""), "yyyyMMdd"));
			cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6); // Sunday
			beginDate = cal.getTime();
			// 结束日期所在周最后一天
			cal.setTime(DateUtils.parseByFormatRule(chartParam.getEndDate()
					.replace("-", ""), "yyyyMMdd"));
			cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6); // Sunday
			endDate = cal.getTime();
			break;
		}
		case CoreConstant.MONTH_PERIOD: {
			// 月粒度
			Calendar cal = Calendar.getInstance();
			// 开始日期所在月最后一天
			cal.setTime(DateUtils.parseByFormatRule(chartParam.getBeginDate()
					.replace("-", ""), "yyyyMMdd"));
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
			beginDate = cal.getTime();
			// 结束日期所在月最后一天
			cal.setTime(DateUtils.parseByFormatRule(chartParam.getEndDate()
					.replace("-", ""), "yyyyMMdd"));
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
			endDate = cal.getTime();
			break;
		}
		default: {
			// 日粒度
			beginDate = DateUtils.parseByFormatRule(chartParam.getBeginDate()
					.replace("-", ""), "yyyyMMdd");
			endDate = DateUtils.parseByFormatRule(chartParam.getEndDate()
					.replace("-", ""), "yyyyMMdd");
			break;
		}
		}
		LinkedHashMap<String, String> seriesMap = new LinkedHashMap<String, String>();
		while (beginDate.getTime() <= endDate.getTime()) {
			// 下一个日期
			if (chartParam.getDateType() == CoreConstant.DAY_PERIOD) {
				// 日
				seriesMap.put(
						DateUtils.formatByFormatRule(beginDate, "yyyy-MM-dd"),
						DateUtils.formatByFormatRule(beginDate, "yyyy-MM-dd"));
				beginDate = DateUtils.getNextDate(beginDate);
			} else if (chartParam.getDateType() == CoreConstant.WEEK_PERIOD) {
				// 周
				Calendar cal = Calendar.getInstance();
				cal.setFirstDayOfWeek(Calendar.MONDAY);
				cal.setTime(beginDate);
				cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6);
				beginDate = cal.getTime();
				seriesMap.put(
						DateUtils.formatByFormatRule(beginDate, "yyyy-MM-dd"),
						DateUtils.getWeekNo(beginDate));
				cal.setTime(DateUtils.getNextWeek(beginDate));
				beginDate = cal.getTime();
			} else if (chartParam.getDateType() == CoreConstant.MONTH_PERIOD) {
				// 月
				seriesMap.put(
						DateUtils.formatByFormatRule(beginDate, "yyyy-MM"),
						DateUtils.formatByFormatRule(beginDate, "yyyy-MM"));
				// 取下月最后一天
				Calendar cal = Calendar.getInstance();
				cal.setTime(DateUtils.getNextMonth(beginDate));
				cal.set(Calendar.DAY_OF_MONTH,
						cal.getActualMaximum(Calendar.DATE));
				beginDate = cal.getTime();
			}
		}
		return seriesMap;
	}

	/**
	 * y轴
	 * 
	 * @param kpiDataMap
	 *            <kpi_code,数据<日期，数据>>
	 * @return
	 */
	private List<Graph> listGraphDataList(
			Map<String, List<DwmisKpiData>> dwmisKpiDataMap,
			Map<String, DwmisKpiInfo> kpiDataMap, List<String> chartTypeList) {
		if (dwmisKpiDataMap == null || dwmisKpiDataMap.isEmpty()) {
			this.logger.warn("生成矩形图没有数据");
			return null;
		}
		if (kpiDataMap == null || kpiDataMap.isEmpty()) {
			this.logger.warn("生成矩形图没有数据");
			return null;
		}
		List<Graph> graphList = new ArrayList<Graph>();
		Graph graph = null;
		Entry entry = null;
		List<DwmisKpiData> kpiDataList = null;
		List<GraphDataElement> graphValueList = null;
		GraphDataElement graphDataElement = null;
		int i = 0;
		for (Iterator it = dwmisKpiDataMap.entrySet().iterator(); it.hasNext();) {
			entry = (Entry) it.next();
			graph = new Graph();
			graph.setGraphId(entry.getKey().toString());
			graph.setGraphColor(Constants.CHART_COLOR_LIST.get(i));
			graph.setGraphName(kpiDataMap.get(entry.getKey().toString())
					.getKpiNameShow());
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
			kpiDataList = (List<DwmisKpiData>) entry.getValue();
			graphValueList = new ArrayList<GraphDataElement>();
			for (DwmisKpiData dwmisKpiData : kpiDataList) {
				graphDataElement = new GraphDataElement();
				if (dwmisKpiData.getDateType() == CoreConstant.WEEK_PERIOD) {
					// 周
					Calendar cal = Calendar.getInstance();
					cal.setTime(dwmisKpiData.getReportDate());
					cal.setFirstDayOfWeek(cal.MONDAY);
					cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6);
					graphDataElement.setValueDate(DateUtils.fotmatDate4(cal
							.getTime()));
				} else if (dwmisKpiData.getDateType() == CoreConstant.MONTH_PERIOD) {
					// 月
					graphDataElement.setValueDate(DateUtils.formatByFormatRule(
							dwmisKpiData.getReportDate(),
							DateUtils.formatByFormatRule(
									dwmisKpiData.getReportDate(), "yyyy-MM")));
				} else {
					// 日
					graphDataElement.setValueDate(DateUtils
							.fotmatDate4(dwmisKpiData.getReportDate()));
				}
				graphDataElement.setValue(String.valueOf(dwmisKpiData
						.getShowValue()));
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

	public Chart getColumnChart(ChartParam chartParam) {

		this.logger.info("查询并封装趋势图数据:" + chartParam.getEndDate());
		List<String> staCodeList = new ArrayList<String>();
		DwmisKpiData dwmisKpiData = new DwmisKpiData();
		dwmisKpiData.setKpiCode(chartParam.getKpiCode());
		dwmisKpiData.setDateType(CoreConstant.DAY_PERIOD);
		dwmisKpiData.setStaCode(CoreConstant.STA_PEAK);
		// 峰值
		List<DwmisKpiData> kpiDataList_Peak = dwmisKpiDataService
				.getKpiDataByKpiCode(dwmisKpiData, null);
		if (kpiDataList_Peak == null || kpiDataList_Peak.isEmpty()) {
			this.logger.warn("指标数据为空！");
			return new Chart();
		}
		// 查询指标数据
		dwmisKpiData = new DwmisKpiData();
		dwmisKpiData.setKpiCode(chartParam.getKpiCode());
		dwmisKpiData.setDateType(chartParam.getDateType());
		dwmisKpiData.setReportDate(DateUtils.parseByFormatRule(
				chartParam.getEndDate(), "yyyy-MM-dd"));
		List<DwmisKpiData> dwmisKpiDataList = this.dwmisKpiDataService
				.getKpiDataByKpiCode(dwmisKpiData, staCodeList);
		if (dwmisKpiDataList == null || dwmisKpiDataList.isEmpty()) {
			this.logger.warn("查询指标数据失败,没有数据");
			return new Chart();
		}
		Map<String, DwmisKpiData> dwmisKpiInfoMap = new HashMap<String, DwmisKpiData>();
		Map.Entry entry = null;
		DwmisKpiData kpiData = null;
		for (int i = 0; i < dwmisKpiDataList.size(); i++) {
			kpiData = dwmisKpiDataList.get(i);
			dwmisKpiInfoMap.put(String.valueOf(kpiData.getStaCode()), kpiData);
		}
		Chart chart = new Chart();
		chart.setChartName("指标矩形图");
		// X轴
		chart.setSeriesMap(this.getSeriesMap_New(dwmisKpiDataList,
				kpiDataList_Peak));
		// y轴
		chart.setGraphList(this.listGraphDataList_New(dwmisKpiDataList,
				dwmisKpiInfoMap, kpiDataList_Peak));
		return chart;
	}

	/**
	 * x轴的数据集合<日期，日期描述>
	 * 
	 * @param kpiDataMap
	 * @return
	 */
	private LinkedHashMap<String, String> getSeriesMap_New(
			List<DwmisKpiData> dwmisKpiDataList,
			List<DwmisKpiData> kpiDataList_Peak) {
		if (dwmisKpiDataList == null || dwmisKpiDataList.size() <= 0) {
			this.logger.warn("查询指标数据失败,参数错误");
		}

		LinkedHashMap<String, String> seriesMap = new LinkedHashMap<String, String>();
		for (int i = 0; i < dwmisKpiDataList.size(); i++) {
			DwmisKpiData kpiData = dwmisKpiDataList.get(i);
			if (kpiData.getStaCode() == CoreConstant.STA_CURRENT) {
				seriesMap.put(String.valueOf(kpiData.getStaCode()), "当日值");
			}
			if (kpiData.getStaCode() == CoreConstant.STA_SEVEN_DAY_AVERAGE) {
				seriesMap.put(String.valueOf(kpiData.getStaCode()), "日均值");
			}
		}
		if (kpiDataList_Peak != null) {
			String date = DateUtils.formatByFormatRule(kpiDataList_Peak.get(0)
					.getReportDate(), "MM月dd日");
			seriesMap.put(String.valueOf(kpiDataList_Peak.get(0).getStaCode()),
					"历史日峰值(" + date + ")");
		}

		return seriesMap;
	}

	/**
	 * y轴
	 * 
	 * @param kpiDataMap
	 *            <kpi_code,数据<日期，数据>>
	 * @return
	 */
	private List<Graph> listGraphDataList_New(
			List<DwmisKpiData> dwmisKpiDataList,
			Map<String, DwmisKpiData> kpiDataMap,
			List<DwmisKpiData> kpiDataList_Peak) {
		if (dwmisKpiDataList == null || dwmisKpiDataList.size() <= 0) {
			this.logger.warn("生成矩形图没有数据");
			return null;
		}
		if (kpiDataMap == null || kpiDataMap.isEmpty()) {
			this.logger.warn("生成矩形图没有数据");
			return null;
		}
		List<Graph> graphList = new ArrayList<Graph>();
		Graph graph = null;
		List<GraphDataElement> graphValueList = new ArrayList<GraphDataElement>();
		GraphDataElement graphDataElement = null;
		int i = 0;
		for (DwmisKpiData dwmisKpiData : dwmisKpiDataList) {
			graph = new Graph();
			graph.setGraphId(String.valueOf(dwmisKpiData.getStaCode()));
			graph.setGraphColor(Constants.CHART_COLOR_LIST.get(i));
			graph.setGraphName("测试");

			graphDataElement = new GraphDataElement();
			if (dwmisKpiData.getStaCode() == CoreConstant.STA_CURRENT) {
				graphDataElement.setValueDate(String.valueOf(dwmisKpiData
						.getStaCode()));
			}
			if (dwmisKpiData.getStaCode() == CoreConstant.STA_SEVEN_DAY_AVERAGE) {
				graphDataElement.setValueDate(String.valueOf(dwmisKpiData
						.getStaCode()));
			}
			graphDataElement.setValue(String.valueOf(dwmisKpiData
					.getShowValue()));
			graphValueList.add(graphDataElement);
		}
		graph = new Graph();
		graph.setGraphId(String.valueOf(kpiDataList_Peak.get(0).getStaCode()));
		graph.setGraphColor(Constants.CHART_COLOR_LIST.get(i));
		graph.setGraphName("测试");
		graphDataElement = new GraphDataElement();
		graphDataElement.setValueDate(String.valueOf(kpiDataList_Peak.get(0)
				.getStaCode()));
		graphDataElement.setValue(String.valueOf(kpiDataList_Peak.get(0)
				.getShowValue()));
		graph.setDataList(graphValueList);
		graphValueList.add(graphDataElement);
		graphList.add(graph);
		return graphList;
	}
}
