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
import com.infosmart.portal.pojo.dwmis.DwmisKpiGoal;
import com.infosmart.portal.pojo.dwmis.DwmisKpiInfo;
import com.infosmart.portal.service.dwmis.DwmisKpiDataService;
import com.infosmart.portal.service.dwmis.DwmisKpiInfoService;
import com.infosmart.portal.service.dwmis.DwmisLineService;
import com.infosmart.portal.service.dwmis.KPIGoalManager;
import com.infosmart.portal.service.impl.BaseServiceImpl;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.dwmis.CalculateGoalValue;
import com.infosmart.portal.util.dwmis.CoreConstant;
import com.infosmart.portal.util.dwmis.TimeFormatProcessor;
import com.infosmart.portal.vo.Chart;
import com.infosmart.portal.vo.Graph;
import com.infosmart.portal.vo.GraphDataElement;
import com.infosmart.portal.vo.dwmis.ChartParam;

@Service
public class DwmisLineChartServiceImpl extends BaseServiceImpl implements
		DwmisLineService {

	@Autowired
	private DwmisKpiInfoService dwmisKpiInfoService;
	@Autowired
	private DwmisKpiDataService dwmisKpiDataService;
	@Autowired
	private KPIGoalManager kPIGoalManager;

	@Override
	public Chart getLineChartShowWarnLine(ChartParam chartParam, int staCode)
			throws Exception {
		if (chartParam == null || chartParam.getKpiCodes().length == 0) {
			this.logger.warn("得到折线图数据失败:参数为空");
			return new Chart();
		}

		DwmisKpiInfo kpiInfo = dwmisKpiInfoService
				.getDwmisKpiInfoByCode(chartParam.getKpiCode());
		if (kpiInfo == null) {
			return new Chart();
		}
		List<DwmisKpiInfo> kpiInfoList = new ArrayList<DwmisKpiInfo>();
		Date endDate = null;
		Date beginDate = null;
		if (chartParam.getDateType() == CoreConstant.DAY_PERIOD) {
			// 日粒度 取得查询时间
			// endDate = DateUtils.parseByFormatRule(
			// TimeFormatProcessor.getCurrentDate(kpiInfo.getDayOffSet()),
			// "yyyy-MM-dd");
			endDate = DateUtils.parseByFormatRule(chartParam.getEndDate(),
					"yyyy-MM-dd");
			beginDate = DateUtils.getPrevious7Date(endDate);
		} else if (chartParam.getDateType() == CoreConstant.WEEK_PERIOD) {
			// 周粒度 取得该星期的周日
			// endDate = TimeFormatProcessor.getBelongSunDay(TimeFormatProcessor
			// .getSysDate(kpiInfo.getDayOffSet()));
			endDate = TimeFormatProcessor.getBelongSunDay(DateUtils
					.parseByFormatRule(chartParam.getEndDate(), "yyyy-MM-dd"));
			beginDate = DateUtils.getPreviousWeek(endDate,
					CoreConstant.PREVIOUS_WEEK_COUNT);
		} else {
			// 月粒度 取得该月的最后一天
			// endDate =
			// TimeFormatProcessor.getLastDayOfMonth(TimeFormatProcessor
			// .getSysDate(kpiInfo.getDayOffSet()));
			endDate = TimeFormatProcessor.getLastDayOfMonth(DateUtils
					.parseByFormatRule(chartParam.getEndDate(), "yyyy-MM-dd"));
			beginDate = DateUtils.getPreviousMonth(endDate,
					CoreConstant.PREVIOUS_MONTH_COUNT);
		}
		chartParam.setBeginDate(DateUtils.fotmatDate4(beginDate));
		chartParam.setEndDate(DateUtils.fotmatDate4(endDate));

		List<DwmisKpiData> kpiDataList = dwmisKpiDataService
				.kpiDataListByParam(chartParam.getKpiCode(),
						chartParam.getDateType(), staCode, beginDate, endDate);

		Map<String, List<DwmisKpiData>> kpiDataMap = new HashMap<String, List<DwmisKpiData>>();
		DwmisKpiGoal kpiGoal = kPIGoalManager.getKpiGoalByKpiCodeAndDate(
				chartParam.getKpiCode(),
				chartParam.getEndDate().replace("-", "").substring(0, 4));

		Map<String, DwmisKpiInfo> kpiInfoMap = new HashMap<String, DwmisKpiInfo>();

		if (kpiDataList == null || kpiDataList.isEmpty()) {
			return new Chart();
		}
		// 所有指标数据 原数据+目标线数据
		List<DwmisKpiData> allDataList = new ArrayList<DwmisKpiData>();
		// 有年绩效值则显示目标线 无年绩效值则只显示Kpi值

		// 造目标线 kpiCode和ShowValue不同 其他属性相同
		for (DwmisKpiData kpiData : kpiDataList) {
			DwmisKpiData goalKpiData = new DwmisKpiData();
			if (kpiData.getDateType() == CoreConstant.DAY_PERIOD) {
				if (kpiGoal != null) {
					// 按日的目标值 全年日均乘以当前第多少日
					goalKpiData.setShowValue(CalculateGoalValue
							.CalculateGoalByDay(kpiGoal.getScore5(),
									kpiData.getReportDate()));
					goalKpiData.setKpiCode("目标");
					goalKpiData.setKpiName("目标线");
					goalKpiData.setReportDate(kpiData.getReportDate());
					goalKpiData.setDwmisKpiInfo(kpiData.getDwmisKpiInfo());
					goalKpiData.setDateType(kpiData.getDateType());
					allDataList.add(goalKpiData);
				}
			} else if (kpiData.getDateType() == CoreConstant.WEEK_PERIOD) {
				if (kpiGoal != null) {
					// 按周的目标值 全年周平均乘以当前第多少周
					goalKpiData.setShowValue(CalculateGoalValue
							.CalculateGoalByWeek(kpiGoal.getScore5(),
									kpiData.getReportDate()));
					goalKpiData.setKpiCode("目标");
					goalKpiData.setKpiName("目标线");
					goalKpiData.setReportDate(kpiData.getReportDate());
					goalKpiData.setDwmisKpiInfo(kpiData.getDwmisKpiInfo());
					goalKpiData.setDateType(kpiData.getDateType());
					allDataList.add(goalKpiData);
				}

			} else if (kpiData.getDateType() == CoreConstant.MONTH_PERIOD) {
				// 每个月不同的指标绩效值
				DwmisKpiGoal kpiGoalMonth = kPIGoalManager
						.getKpiGoalByKpiCodeAndDate(chartParam.getKpiCode(),
								DateUtils.fotmatDate8(kpiData.getReportDate()));
				if (kpiGoalMonth != null) {
					goalKpiData.setShowValue(kpiGoalMonth.getScore5());
					goalKpiData.setKpiCode("目标");
					goalKpiData.setKpiName("目标线");
					goalKpiData.setReportDate(kpiData.getReportDate());
					goalKpiData.setDwmisKpiInfo(kpiData.getDwmisKpiInfo());
					goalKpiData.setDateType(kpiData.getDateType());
					allDataList.add(goalKpiData);
				} else {
					if (kpiGoal != null) {
						// 按月的目标值 全年月平均乘以当前第几月
						goalKpiData.setShowValue(CalculateGoalValue
								.CalculateGoalByMonth(kpiGoal.getScore5(),
										kpiData.getReportDate()));
						goalKpiData.setKpiCode("目标");
						goalKpiData.setKpiName("目标线");
						goalKpiData.setReportDate(kpiData.getReportDate());
						goalKpiData.setDwmisKpiInfo(kpiData.getDwmisKpiInfo());
						goalKpiData.setDateType(kpiData.getDateType());
						allDataList.add(goalKpiData);
					}
				}
			}
			allDataList.add(kpiData);
			kpiInfoList.add(kpiData.getDwmisKpiInfo());
		}

		if (allDataList == null || allDataList.isEmpty()) {
			return new Chart();
		}
		// kpiCode ----kpiData
		for (DwmisKpiData kpiData : allDataList) {
			if (!kpiDataMap.containsKey(kpiData.getKpiCode())) {
				List<DwmisKpiData> tempKpiData = new ArrayList<DwmisKpiData>();
				tempKpiData.add(kpiData);
				kpiDataMap.put(kpiData.getKpiCode(), tempKpiData);
			} else {
				kpiDataMap.get(kpiData.getKpiCode()).add(kpiData);
			}
		}
		if (kpiInfoList == null || kpiInfoList.isEmpty()) {
			return new Chart();
		}
		for (DwmisKpiInfo tmpKpiInfo : kpiInfoList) {
			kpiInfoMap.put(tmpKpiInfo.getKpiCode(), kpiInfo);
		}

		Chart chart = new Chart();
		chart.setChartName("指标折线图");
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
		Date beginDate = null;
		Date endDate = null;

		switch (chartParam.getKpiType()) {
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
	 * 
	 * @param kpiDataMap
	 *            <kpi_code,数据<日期，数据>>
	 * @return
	 */
	private List<Graph> listGraphDataList(
			Map<String, List<DwmisKpiData>> kpiDataMap,
			Map<String, DwmisKpiInfo> kpiInfoMap, List<String> chartTypeList) {
		if (kpiDataMap == null || kpiDataMap.isEmpty()) {
			this.logger.warn("生成折线图没有数据");
			return null;
		}
		if (kpiInfoMap == null || kpiInfoMap.isEmpty()) {
			this.logger.warn("生成折线图没有数据");
			return null;
		}
		List<Graph> graphList = new ArrayList<Graph>();
		Graph graph = null;
		Entry entry = null;
		List<DwmisKpiData> kpiDataList = null;
		List<GraphDataElement> graphValueList = null;
		GraphDataElement graphDataElement = null;
		int i = 0;
		for (Iterator it = kpiDataMap.entrySet().iterator(); it.hasNext();) {
			entry = (Entry) it.next();
			graph = new Graph();
			graph.setGraphId(entry.getKey().toString());
			graph.setGraphColor(Constants.CHART_COLOR_LIST.get(i));
			// graph.setGraphName(kpiInfoMap.get(entry.getKey().toString())
			// .getKpiName());
			try {
				graph.setGraphType(Graph.GRAPH_TYPE_LINE);
			} catch (Exception e) {
				graph.setGraphType(Graph.GRAPH_TYPE_LINE);
			}
			kpiDataList = (List<DwmisKpiData>) entry.getValue();
			graphValueList = new ArrayList<GraphDataElement>();
			for (DwmisKpiData kpiData : kpiDataList) {
				graphDataElement = new GraphDataElement();
				if (kpiData.getDateType() == CoreConstant.WEEK_PERIOD) {
					// 周
					Calendar cal = Calendar.getInstance();
					cal.setTime(kpiData.getReportDate());
					cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
					graphDataElement.setValueDate(DateUtils.fotmatDate4(cal
							.getTime()));
				} else if (kpiData.getDateType() == CoreConstant.MONTH_PERIOD) {
					// 月
					graphDataElement.setValueDate(DateUtils.formatByFormatRule(
							kpiData.getReportDate(), "yyyy-MM"));
				} else {
					// 日
					graphDataElement.setValueDate(DateUtils.fotmatDate4(kpiData
							.getReportDate()));
				}
				graphDataElement
						.setValue(String.valueOf(kpiData.getShowValue()));
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

	@Override
	public Chart getLineChart(ChartParam chartParam, int staCode)
			throws Exception {
		if (chartParam == null || chartParam.getKpiCodes().length == 0) {
			this.logger.warn("得到折线图数据失败:参数为空");
			return new Chart();
		}
		List<String> kpiCodelist = Arrays.asList(chartParam.getKpiCodes());
		Map<String, List<DwmisKpiData>> kpiDataMap = new HashMap<String, List<DwmisKpiData>>();
		Map<String, DwmisKpiInfo> kpiInfoMap = new HashMap<String, DwmisKpiInfo>();
		for (int i = 0; i < kpiCodelist.size(); i++) {
			DwmisKpiInfo kpiInfo = dwmisKpiInfoService
					.getDwmisKpiInfoByCode(kpiCodelist.get(i));
			kpiInfoMap.put(kpiCodelist.get(i), kpiInfo);
			if (kpiInfo == null) {
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

			List<DwmisKpiData> kpiDataList = dwmisKpiDataService
					.kpiDataListByParam(kpiCodelist.get(i),
							chartParam.getDateType(), staCode, beginDate,
							endDate);
			if (kpiDataList == null || kpiDataList.isEmpty()) {
				return new Chart();
			}
			kpiDataMap.put(kpiCodelist.get(i), kpiDataList);
		}

		if (kpiDataMap == null || kpiDataMap.isEmpty()) {
			this.logger.warn("查询指标数据失败,没有数据");
			return new Chart();
		}
		Chart chart = new Chart();
		chart.setChartName("指标折线图");
		// X轴
		chart.setSeriesMap(this.getSeriesMap(chartParam));
		// y轴
		chart.setGraphList(this.listGraphDataList(kpiDataMap, kpiInfoMap,
				Arrays.asList(chartParam.getChartTypes())));
		return chart;
	}
}
