package com.infosmart.portal.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.BigEventPo;
import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasStKpiData;
import com.infosmart.portal.pojo.dwmis.MisEventPo;
import com.infosmart.portal.service.BigEventService;
import com.infosmart.portal.service.DwpasCKpiInfoService;
import com.infosmart.portal.service.DwpasStKpiDataService;
import com.infosmart.portal.service.StockChartService;
import com.infosmart.portal.service.dwmis.MisEventService;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.vo.Chart;
import com.infosmart.portal.vo.ChartParam;
import com.infosmart.portal.vo.Graph;
import com.infosmart.portal.vo.GraphDataElement;

@Service
public class StockChartServiceImpl extends BaseServiceImpl implements
		StockChartService {

	// kpi数据
	@Autowired
	private DwpasStKpiDataService dwpasStKpiDataService;
	// kpi信息
	@Autowired
	private DwpasCKpiInfoService dwpasCKpiInfoService;
	// dwpas event
	@Autowired
	private BigEventService bigEventService;
	// dwmis event
	@Autowired
	private MisEventService misEventService;

	private static final String MIN_KPI_DATA_BASE_VALUE = "minKpiDataBaseValue";

	private static final String MAX_KPI_DATA_BASE_VALUE = "maxKpiDataBaseValue";

	public Chart getStockChart(ChartParam chartParam) {
		if (chartParam == null) {
			this.logger.warn("得到趋势图数据失败:参数为空");
			return null;
		}
		this.logger.info("查询并封装趋势图数据:" + chartParam.getEndDate());
		if (chartParam.getKpiCodes() == null
				|| chartParam.getKpiCodes().length == 0) {
			this.logger.warn("得到趋势图数据失败:指标CODE参数为空");
			return null;
		}
		this.logger.info("------>" + chartParam.getKpiCodes().length);
		Chart chart = new Chart();
		chart.setChartName("指标趋势图");
		List<Graph> graphList = new ArrayList<Graph>();
		TreeMap<String, String> areaDataMap = new TreeMap<String, String>();
		int i = 0;
		// 查询指标信息
		List<DwpasCKpiInfo> kpiInfoList = this.dwpasCKpiInfoService
				.listDwpasCKpiInfoByCodes(Arrays.asList(chartParam
						.getKpiCodes()));
		int kpiType = 0;
		if (kpiInfoList == null || kpiInfoList.isEmpty()) {
			this.logger.warn("查询指标信息失败,没有指标数据");
			return null;
		} else {
			// 指标类型
			kpiType = Integer.parseInt(kpiInfoList.get(0).getKpiType());
			chartParam.setKpiType(kpiType);
			// 查询时间
			String reportDate = chartParam.getEndDate().replace("-", "");
			if (kpiType == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
				// 按日查看
				if (reportDate.length() == 6) {
					// 如果传入的月份，则默认第一天
					reportDate = reportDate + "01";
				}
			} else {
				if (reportDate.length() == 8) {
					// 如果传入的日期，默认取月份
					reportDate = reportDate.substring(0, 6);
				}
			}
			chartParam.setEndDate(reportDate);
		}
		String dateFormat = "yyyyMM";
		if (chartParam.getKpiType() == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
			dateFormat = "yyyyMMdd";
		}
		Date endDate = DateUtils.parseByFormatRule(chartParam.getEndDate(),
				dateFormat);
		Date beginDate = DateUtils.getPreviousMonth(endDate,
				Constants.TIME_SPAN_STOCK);
		// 查询指标数据
		Map<String, List<DwpasStKpiData>> kpiDataMap = this.dwpasStKpiDataService
				.listDwpasStKpiDataByKpiCode(kpiInfoList,
						DateUtils.formatByFormatRule(beginDate, dateFormat),
						chartParam.getEndDate(), chartParam.getKpiType());
		this.logger.info("---------------------------->数据:"
				+ (kpiDataMap == null ? 0 : kpiDataMap.size()));
		if (kpiDataMap == null || kpiDataMap.isEmpty()) {
			this.logger.warn("查询指标数据失败,没有指标数据,将用默认数据");
			// 人造数据,防止出错
			kpiDataMap = new HashMap<String, List<DwpasStKpiData>>();
			List<DwpasStKpiData> tmpKpiDataList = null;
			for (DwpasCKpiInfo kpiInfo : kpiInfoList) {
				tmpKpiDataList = new ArrayList<DwpasStKpiData>();
				while (beginDate.getTime() <= endDate.getTime()) {
					tmpKpiDataList.add(new DwpasStKpiData(kpiInfo, DateUtils
							.formatByFormatRule(beginDate, dateFormat),
							new BigDecimal(0)));
					beginDate = DateUtils.getNextMonth(beginDate);
				}
				//
				this.logger.info("用默认数据:"
						+ (tmpKpiDataList == null ? 0 : tmpKpiDataList.size()));
				kpiDataMap.put(kpiInfo.getKpiCode(), tmpKpiDataList);
			}
		}
		List<DwpasStKpiData> tmpKpiDataList = null;
		Map.Entry<String, List<DwpasStKpiData>> entry = null;
		List<String> reportDateList = null;
		for (DwpasCKpiInfo kpiInfo : kpiInfoList) {
			if (!kpiDataMap.containsKey(kpiInfo.getKpiCode())) {
				kpiDataMap.put(kpiInfo.getKpiCode(),
						new ArrayList<DwpasStKpiData>());
			}
		}
		for (Iterator it = kpiDataMap.entrySet().iterator(); it.hasNext();) {
			entry = (Map.Entry<String, List<DwpasStKpiData>>) it.next();
			tmpKpiDataList = entry.getValue();
			//
			reportDateList = new ArrayList<String>();
			beginDate = DateUtils.getPreviousMonth(endDate,
					Constants.TIME_SPAN_STOCK);
			for (DwpasStKpiData kpiData : tmpKpiDataList) {
				reportDateList.add(kpiData.getReportDate());
			}
			this.logger.info("---------->" + entry.getKey() + ","
					+ reportDateList.size());
			while (beginDate.getTime() <= endDate.getTime()) {
				if (!reportDateList.contains(DateUtils.formatByFormatRule(
						beginDate, dateFormat))) {
					this.logger.info("该日期没有数据:"
							+ DateUtils.formatByFormatRule(beginDate,
									dateFormat));
					kpiDataMap.get(entry.getKey()).add(
							new DwpasStKpiData(new DwpasCKpiInfo(
									entry.getKey(), entry.getKey()), DateUtils
									.formatByFormatRule(beginDate, dateFormat),
									new BigDecimal(0)));
				}
				//
				beginDate = DateUtils.getNextMonth(beginDate);
			}
			// 数据再按日期进行排序
			Collections.sort(kpiDataMap.get(entry.getKey()), new Comparator() {

				public int compare(Object o1, Object o2) {
					DwpasStKpiData data1 = (DwpasStKpiData) o1;
					DwpasStKpiData data2 = (DwpasStKpiData) o2;
					// 因为报表日期格式为yyyyMMdd或yyyyMM,故可转整数
					if (Integer.parseInt(data1.getReportDate()) > Integer
							.parseInt(data2.getReportDate())) {
						return 1;
					} else if (Integer.parseInt(data1.getReportDate()) < Integer
							.parseInt(data2.getReportDate())) {
						return -1;
					}
					return 0;
				}

			});
		}

		Map<String, List<MisEventPo>> misEventMap = null;
		List<BigEventPo> bigEventList = null;
		// 大事件
		if ("1".equals(chartParam.getHasEvent())) {
			if (StringUtils.notNullAndSpace(chartParam.getIsMIS())) {
				this.logger.debug("查询《瞭望塔》大事件");
				List<String> kpiCodeList = Arrays.asList(chartParam
						.getKpiCodes());
				misEventMap = misEventService.listEventByCodes(
						chartParam.getEventSearchKey(), kpiCodeList,
						chartParam.getEventType());
			} else {
				this.logger.debug("查询《经纬仪》大事件");
				List<String> kpiCodeList = Arrays.asList(chartParam
						.getKpiCodes());
				bigEventList = bigEventService.listEventByCodes(
						chartParam.getEventSearchKey(), kpiCodeList,
						chartParam.getEventType(), chartParam.getIsPrd());
			}
		}
		// 指标数据
		List<DwpasStKpiData> stKpiDateList = null;
		for (DwpasCKpiInfo kpiInfo : kpiInfoList) {
			// 查询KPI信息
			if (kpiInfo == null) {
				this.logger.error("没有找到指标信息");
				continue;
			}
			this.logger.info("指标编码:" + kpiInfo.getKpiCode() + ",指标类型:"
					+ kpiInfo.getKpiType());
			// 查询某段时间内的kpi数据
			stKpiDateList = kpiDataMap.get(kpiInfo.getKpiCode());
			if (stKpiDateList == null)
				stKpiDateList = new ArrayList<DwpasStKpiData>();
			if ("1".equals(chartParam.getNeedPercent())) {
				this.logger.debug("需要归一化....");
				// 需要归一化
				this.getPercentData(areaDataMap, stKpiDateList);
			} else {
				this.logger.debug("不需要归一化....");
				// 不需要归一化
				this.getOriginalData(areaDataMap, stKpiDateList, kpiInfo);
			}
			// 获得图表中线的描述信息
			Graph graph = new Graph();
			graph.setGraphId(kpiInfo.getKpiCode());
			// 趋势图
			graph.setGraphType(Graph.GRAPH_TYPE_TREND_LINE);
			// 名称
			if (StringUtils.notNullAndSpace(kpiInfo.getUnit())) {
				graph.setGraphName(kpiInfo.getDispName() + "("
						+ kpiInfo.getUnit() + ")");
			} else {
				graph.setGraphName(kpiInfo.getDispName());
			}
			// 颜色
			graph.setGraphColor(chartParam.getColors()[i]);
			// 大事件
			if ("1".equals(chartParam.getHasEvent())) {
				if (StringUtils.notNullAndSpace(chartParam.getIsMIS())) {
					this.logger.debug("查询大事件:" + kpiInfo.getKpiCode());
					graph.setMisEventList(misEventMap == null ? null
							: misEventMap.get(kpiInfo.getKpiCode()));
				} else {
					this.logger.debug("查询大事件:" + kpiInfo.getKpiCode());
					// 取大事记
					List<BigEventPo> eventList = new ArrayList<BigEventPo>();
					if (bigEventList != null && bigEventList.size() > 0) {
						Map<String, String> eventColorMap = new HashMap<String, String>();
						for (BigEventPo event : bigEventList) {
							if (event == null)
								continue;
							// 非大盘指标
							//*****************remove by yangwg，
							/*
							if (!"1".equalsIgnoreCase(kpiInfo.getIsOverall())) {
								//非大盘指标
								if (kpiInfo.getKpiCode().equalsIgnoreCase(
										event.getKpiCode())) {
									continue;
								}
							}
							*/
							
							
							if (!eventColorMap
									.containsKey(String.valueOf(event.getEventType()))) {
								eventColorMap.put(String.valueOf(event
										.getEventType()),
										Constants.CHART_COLOR_LIST
												.get(eventColorMap.size()));
							}
							event.setEventColor(eventColorMap.get(String
									.valueOf(event.getEventType())));
							if (event.getKpiCode() == null) {
								event.setKpiCode("");
							}
							if (chartParam.getIsPrd() == null) {
								chartParam.setIsPrd("0");
							}
							if (!chartParam.getIsPrd().equals("1")) {
								event.setKpiCode(kpiInfo.getKpiCode());
							}
							if (event.getKpiCode().equals(kpiInfo.getKpiCode())) {
								if (event.getEventEndDate() != null) {
									event.setKpiCode(kpiInfo.getKpiCode());
									// 有结束日期
									//天指标 添加持续大事件
									if(kpiType == DwpasCKpiInfo.KPI_TYPE_OF_DAY){
										Date startDate = DateUtils
												.parseByFormatRule(
														event.getEventStartDate(),
														"yyyy-MM-dd");// 开始时间
										Date endEventDate = DateUtils
												.parseByFormatRule(
														event.getEventEndDate(),
														"yyyy-MM-dd");// 结束时间
										while (startDate.getTime() <= endEventDate
												.getTime()) {
											// 时间段的大事记
											BigEventPo newEvent = new BigEventPo();
											// newEvent.setIsPublic(event.getIsPublic());
											// newEvent.setTitle(event.getTitle());
											// newEvent=event;
											try {
												BeanUtils.copyProperties(newEvent,
														event);
											} catch (IllegalAccessException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (InvocationTargetException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											newEvent.setEventDate(DateUtils
													.formatByFormatRule(startDate,
															"yyyyMMdd"));
											startDate = DateUtils
													.getNextDate(startDate);
											eventList.add(newEvent);
										}
										//月指标添加持续大事件
									}else if(kpiType == DwpasCKpiInfo.KPI_TYPE_OF_MONTH){
										Date startDate = DateUtils
												.parseByFormatRule(
														event.getEventStartDate(),
														"yyyy-MM");// 开始时间
										Date endEventDate = DateUtils
												.parseByFormatRule(
														event.getEventEndDate(),
														"yyyy-MM");// 结束时间
										while (startDate.getTime() <= endEventDate
												.getTime()) {
											// 时间段的大事记
											BigEventPo newEvent = new BigEventPo();
											// newEvent.setIsPublic(event.getIsPublic());
											// newEvent.setTitle(event.getTitle());
											// newEvent=event;
											try {
												BeanUtils.copyProperties(newEvent,
														event);
											} catch (IllegalAccessException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (InvocationTargetException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											newEvent.setEventDate(DateUtils
													.formatByFormatRule(startDate,
															"yyyyMMdd"));
											startDate = DateUtils
													.getNextMonth(startDate);
											eventList.add(newEvent);
										}
									}
									
									
								} else {
									// 没有结束日期
									eventList.add(event);
								}
							}
						}
					}

					graph.setEventList(eventList);
				}
			}
			// 数据
			graph.setDataList(getGraphDatas(stKpiDateList));
			graphList.add(graph);
			//
			i++;
		}
		chart.setAreaDataMap(areaDataMap);
		chart.setGraphList(graphList);
		return chart;
	}

	private List<GraphDataElement> getGraphDatas(
			List<DwpasStKpiData> kpiDataList) {
		List<GraphDataElement> dataElements = new ArrayList<GraphDataElement>();
		for (DwpasStKpiData kpiData : kpiDataList) {
			GraphDataElement graphDataElement = new GraphDataElement();
			graphDataElement.setValueDate(kpiData.getReportDate());
			graphDataElement.setValue(String.valueOf(kpiData.getBaseValue()));
			dataElements.add(graphDataElement);
		}
		return dataElements;
	}

	/**
	 * 获取归一化值
	 * 
	 * @param areaDataMap
	 * @param kpiDatas
	 */
	private void getPercentData(TreeMap<String, String> areaDataMap,
			final List<DwpasStKpiData> stKpiDateList) {
		if (stKpiDateList == null || stKpiDateList.isEmpty())
			return;
		this.logger.debug("－－－－－－－＞获取归一化值");
		String reportDate = "";
		Map<String, BigDecimal> maxAndMinKpiValue = getMaxAndMinKpiValue(stKpiDateList);
		for (DwpasStKpiData dwpasStKpiData : stKpiDateList) {
			reportDate = dwpasStKpiData.getReportDate().trim();
			// 原有的数据
			String oldDataValue = areaDataMap.get(reportDate);
			if (null == dwpasStKpiData.getBaseValue()) {
				areaDataMap.put(reportDate, StringUtils
						.notNullAndSpace(oldDataValue) ? oldDataValue + ",0"
						: "0");
			} else {
				String dataValue = String.valueOf(getPercentValue(
						dwpasStKpiData.getBaseValue(),
						maxAndMinKpiValue.get(MAX_KPI_DATA_BASE_VALUE),
						maxAndMinKpiValue.get(MIN_KPI_DATA_BASE_VALUE)))
						+ "," + String.valueOf(dwpasStKpiData.getShowValue());
				areaDataMap.put(reportDate, StringUtils
						.notNullAndSpace(oldDataValue) ? oldDataValue + ","
						+ dataValue : dataValue);
			}
		}
	}

	/**
	 * 获取原始值(XML数据默认加上了换行符----->XML换行符转义符"&#x000A;") 如果数据不出现,则MAP值加上换行符的转义符
	 * 
	 * @param areaDataMap
	 * @param stKpiDateList
	 * @param kpiInfo
	 */
	private void getOriginalData(TreeMap<String, String> areaDataMap,
			final List<DwpasStKpiData> stKpiDateList,
			final DwpasCKpiInfo kpiInfo) {
		if (stKpiDateList == null || stKpiDateList.isEmpty()) {
			this.logger.info("---->没有得到指标数据:" + kpiInfo.getKpiCode());
			return;
		}
		String reportDate = "";
		for (DwpasStKpiData kpiData : stKpiDateList) {
			reportDate = kpiData.getReportDate().trim();
			String oldDataValue = areaDataMap.get(reportDate);
			if ("1".equals(kpiInfo.getIsPercent())) {
				this.logger.debug("百比分.....");
				if (null == kpiData.getShowValue()) {
					String dataValue = "0";
					areaDataMap.put(reportDate, StringUtils
							.notNullAndSpace(oldDataValue) ? oldDataValue + ","
							+ dataValue : dataValue);
				} else {
					String dataValue = String.valueOf(kpiData.getShowValue())
							+ "," + String.valueOf(kpiData.getShowValue());
					areaDataMap.put(reportDate, StringUtils
							.notNullAndSpace(oldDataValue) ? oldDataValue + ","
							+ dataValue : dataValue);
				}
			} else {
				if (null == kpiData.getBaseValue()) {
					String dataValue = "0";
					areaDataMap.put(reportDate, StringUtils
							.notNullAndSpace(oldDataValue) ? oldDataValue + ","
							+ dataValue : dataValue);
				} else {
					String dataValue = String.valueOf(kpiData.getShowValue())
							+ "," + String.valueOf(kpiData.getShowValue());
					areaDataMap.put(reportDate, StringUtils
							.notNullAndSpace(oldDataValue) ? oldDataValue + ","
							+ dataValue : dataValue);
				}
			}
		}
	}

	/**
	 * 获得List<KpiDataDTO> 集合中的最大值和最小值
	 * 
	 * @param dataList
	 *            所有数据
	 * @return 包含最大值和最小值的Map对象，key为"max"或"min"
	 */
	private Map<String, BigDecimal> getMaxAndMinKpiValue(
			List<DwpasStKpiData> dataList) {
		Map<String, BigDecimal> minAndMaxValue = new HashMap<String, BigDecimal>();
		if (dataList != null && dataList.size() > 0) {
			BigDecimal max = dataList.get(0).getBaseValue();
			BigDecimal min = dataList.get(0).getBaseValue();
			for (DwpasStKpiData kpiData : dataList) {
				if (max.compareTo(kpiData.getBaseValue()) < 0) {
					max = kpiData.getBaseValue();
				}
				if (min.compareTo(kpiData.getBaseValue()) > 0) {
					if (kpiData.getBaseValue().compareTo(new BigDecimal(0)) == 0)
						continue;
					min = kpiData.getBaseValue();
				}
			}
			minAndMaxValue.put(MAX_KPI_DATA_BASE_VALUE, max);
			minAndMaxValue.put(MIN_KPI_DATA_BASE_VALUE, min);
		}
		return minAndMaxValue;
	}

	/**
	 * 计算归一百分比值 转化公式：(value-min)/(max-min)*100
	 * 
	 * @param value
	 *            当前值
	 * @param max
	 *            最大值
	 * @param min
	 *            最小值
	 * @return 归一百分比值
	 */
	private double getPercentValue(BigDecimal value, BigDecimal max,
			BigDecimal min) {
		try {
			if (max.compareTo(min) <= 0) {
				logger.warn("在计算百分比数值，最大值小于最小值");
				return 0.00;
			} else {
				return value
						.subtract(min)
						.divide(max.subtract(min), 4,
								BigDecimal.ROUND_HALF_EVEN)
						.multiply(new BigDecimal(100)).doubleValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return 0;
		}
	}
}
