package com.infosmart.portal.service.dwmis.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.dwmis.DwmisKpiData;
import com.infosmart.portal.pojo.dwmis.DwmisKpiInfo;
import com.infosmart.portal.pojo.dwmis.MisEventPo;
import com.infosmart.portal.service.dwmis.DwmisKpiDataService;
import com.infosmart.portal.service.dwmis.DwmisKpiInfoService;
import com.infosmart.portal.service.dwmis.MisEventService;
import com.infosmart.portal.service.dwmis.MisStockChartService;
import com.infosmart.portal.service.dwmis.SysDateForFixedYear;
import com.infosmart.portal.service.impl.BaseServiceImpl;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.vo.Chart;
import com.infosmart.portal.vo.Graph;
import com.infosmart.portal.vo.GraphDataElement;
import com.infosmart.portal.vo.dwmis.ChartParam;

@Service
public class MisStockChartServiceImpl extends BaseServiceImpl implements
		MisStockChartService {

	// kpi数据
	@Autowired
	private DwmisKpiDataService dwmisKpiDataService;
	// kpi信息
	@Autowired
	private DwmisKpiInfoService dwmisKpiInfoService;
	// dwmis event
	@Autowired
	private MisEventService misEventService;

	private static final String MIN_KPI_DATA_BASE_VALUE = "minKpiDataValue";

	private static final String MAX_KPI_DATA_BASE_VALUE = "maxKpiDataValue";

	public Chart getStockChart(ChartParam chartParam) {

		if (chartParam == null || chartParam.getKpiCodes().length == 0) {
			this.logger.warn("得到趋势图数据失败:参数为空");
			return null;
		}

		this.logger.info("KpiCode的个数------>" + chartParam.getKpiCodes().length);
		Chart chart = new Chart();
		chart.setChartName("指标趋势图");
		List<Graph> graphList = new ArrayList<Graph>();
		TreeMap<String, String> areaDataMap = new TreeMap<String, String>();
		for (int j = 0; j < chartParam.getKpiCodes().length; j++) {
			// 查询指标信息
			DwmisKpiInfo kpiInfoByCode = this.dwmisKpiInfoService
					.getDwmisKpiInfoByCode(chartParam.getKpiCodes()[j]
							.toString());
			if (kpiInfoByCode == null)
				continue;
			// 根据日期粒度 获取查询时间
			// Date mySysDate = this.sysDateForFixedYear
			// .getSysDateForFixedYear(kpiInfoByCode.getDayOffSet());
			// String endDate = DateUtils.formatByFormatRule(mySysDate,
			// "yyyy-MM-dd");
			String endDate = chartParam.getEndDate();
			// 去年
			String beginDate = DateUtils.fotmatDate4(DateUtils
					.getPreviousYear(DateUtils.parseByFormatRule(endDate,
							"yyyy-MM-dd")));
			if ("1".equals(chartParam.getLastYearValue())
					&& "no".equals(chartParam.getIsProphaseValue())) {

			} else {
				chartParam.setBeginDate(beginDate);
				chartParam.setEndDate(endDate);
			}

			this.logger.info("查询并封装趋势图数据:" + chartParam.getEndDate());
			if (chartParam.getKpiType() == 0) {
				chartParam.setKpiType(1002); // 默认为日
				this.logger.warn("kpiType值为null,采用默认值为1002~");
			}
			if (chartParam.getHasEvent() == null
					|| "".equals(chartParam.getHasEvent())) {
				// 是否有大事记，默认为“有”
				chartParam.setHasEvent("1");
				this.logger.warn("标记是否有大事记的值为null");
			}
			// 查询指标数据
			List<DwmisKpiData> kpiDataList = this.dwmisKpiDataService
					.kpiDataListByParam(chartParam.getKpiCodes()[j].toString(),
							chartParam.getKpiType(), chartParam.getStaCode(),
							DateUtils.parseByFormatRule(
									chartParam.getBeginDate(), "yyyy-MM-dd"),
							DateUtils.parseByFormatRule(
									chartParam.getEndDate(), "yyyy-MM-dd"));
			if (kpiDataList == null || kpiDataList.isEmpty()) {
				this.logger.warn("查询指标数据失败,没有数据");
				continue;
			}
			List<MisEventPo> misEventList = new ArrayList<MisEventPo>();
			// 大事件
			if ("1".equals(chartParam.getHasEvent())) {

				this.logger.debug("查询《瞭望塔》大事件");
				List<MisEventPo> allmisEventList = misEventService
						.getEventsByKpiCode(chartParam.getEventSearchKey(),
								chartParam.getKpiCodes()[j].toString(),
								chartParam.getEventType());
				if (allmisEventList != null && allmisEventList.size() > 0) {
					for (MisEventPo misEvent : allmisEventList) {
						if (misEvent == null)
							continue;

						if (chartParam.getKpiType() != DwmisKpiInfo.KPI_TYPE_OF_MONTH) {
							// 按日
							if (StringUtils.notNullAndSpace(misEvent
									.getEventEndDate())) {
								Date startDate = DateUtils.parseByFormatRule(
										misEvent.getEventStartDate().trim(),
										"yyyy-MM-dd");// 开始时间
								Date endEventDate = DateUtils
										.parseByFormatRule(misEvent
												.getEventEndDate().trim(),
												"yyyy-MM-dd");// 结束时间
								while (startDate.getTime() <= endEventDate
										.getTime()) {
									MisEventPo misEventPo = new MisEventPo();
									try {
										BeanUtils.copyProperties(misEventPo,
												misEvent);
									} catch (Exception e) {
										e.printStackTrace();
									}
									misEventPo.setEventDate(DateUtils
											.formatByFormatRule(startDate,
													"yyyyMMdd"));
									startDate = DateUtils
											.getNextDate(startDate);
									misEventList.add(misEventPo);
								}
							} else {
								misEvent.setEventDate(misEvent
										.getEventStartDate().substring(0, 4)
										+ misEvent.getEventStartDate()
												.substring(5, 7));

								misEventList.add(misEvent);
							}
						} else {
							// 按月
							if (StringUtils.notNullAndSpace(misEvent
									.getEventEndDate())) {
								Date startDate = DateUtils.parseByFormatRule(
										misEvent.getEventStartDate().trim(),
										"yyyy-MM");// 开始时间
								Date endEventDate = DateUtils
										.parseByFormatRule(misEvent
												.getEventEndDate().trim(),
												"yyyy-MM");// 结束时间
								while (startDate.getTime() <= endEventDate
										.getTime()) {
									MisEventPo misEventPo = new MisEventPo();
									try {
										BeanUtils.copyProperties(misEventPo,
												misEvent);
									} catch (Exception e) {
										e.printStackTrace();
									}
									misEventPo.setEventDate(DateUtils
											.formatByFormatRule(startDate,
													"yyyyMM"));
									startDate = DateUtils
											.getNextMonth(startDate);
									misEventList.add(misEventPo);
								}
							} else {
								misEvent.setEventDate(misEvent
										.getEventStartDate().substring(0, 4));
								misEventList.add(misEvent);
							}
						}

					}
				}
			}
			// 指标数据
			this.logger.info("指标编码:" + kpiInfoByCode.getKpiCode());
			// 查询某段时间内的kpi数据
			if ("1".equals(chartParam.getNeedPercent())) {
				this.logger.debug("需要归一化....");
				// 需要归一化
				this.getPercentData(areaDataMap, kpiDataList);
			} else {
				this.logger.debug("不需要归一化....");
				// 不需要归一化
				if ("yes".equals(chartParam.getIsProphaseValue())
						&& chartParam.getKpiCodes().length == 1
						&& chartParam.getKpiType() == 1002) {
					this.getProphaseValueData(areaDataMap, kpiDataList,
							kpiInfoByCode);
				} else if ("yes".equals(chartParam.getIsProphaseValue())
						&& chartParam.getKpiCodes().length == 1
						&& chartParam.getKpiType() == 1003) {
					this.getProphaseValueDataByWeek(areaDataMap, kpiDataList,
							kpiInfoByCode);
				} else if ("yes".equals(chartParam.getIsProphaseValue())
						&& chartParam.getKpiCodes().length == 1
						&& chartParam.getKpiType() == 1004) {
					this.getProphaseValueDataByMonth(areaDataMap, kpiDataList,
							kpiInfoByCode);
				} else {
					this.getOriginalData(areaDataMap, kpiDataList,
							kpiInfoByCode);
				}

			}
			// 获得图表中线的描述信息
			Graph graph = new Graph();
			graph.setGraphId(kpiInfoByCode.getKpiCode());
			// 趋势图
			if ("1".equals(chartParam.getLastYearValue())
					&& StringUtils.notNullAndSpace(chartParam
							.getYesOrNoDetailsAnalysis())) {
				graph.setGraphType(Graph.GRAPH_TYPE_YEAR_ON_GROWTH);
			} else if ("0".equals(chartParam.getLastYearValue())
					&& StringUtils.notNullAndSpace(chartParam
							.getIsProphaseValue())
					&& StringUtils.notNullAndSpace(chartParam
							.getYesOrNoDetailsAnalysis())) {

				graph.setGraphType(Graph.GRAPH_TYPE_COLUMN);
			} else {
				graph.setGraphType(Graph.GRAPH_TYPE_TREND_LINE);
			}
			// 单位
			graph.setUnitStr("("
					+ this.graphUnitStr(kpiInfoByCode.getUnitId().toString(),
							kpiInfoByCode.getUnitStrPost()) + ")");
			// 名称
			if (kpiInfoByCode.getUnitName().equals("%")
					|| kpiInfoByCode.getSizName().equals("个")) {
				graph.setGraphName(kpiInfoByCode.getKpiNameShow() + "("
						+ kpiInfoByCode.getUnitName() + ")");
			} else {
				graph.setGraphName(kpiInfoByCode.getKpiNameShow() + "("
						+ kpiInfoByCode.getSizName()
						+ kpiInfoByCode.getUnitName() + ")");
			}

			// 颜色
			graph.setGraphColor(chartParam.getColors()[j]);
			// 大事件
			if ("1".equals(chartParam.getHasEvent())) {

				this.logger.debug("查询大事件:" + kpiInfoByCode.getKpiCode());
				graph.setMisEventList(misEventList == null ? null
						: misEventList);
			}
			// 数据
			graph.setDataList(getGraphDatas(kpiDataList));
			graphList.add(graph);

			chart.setAreaDataMap(areaDataMap);
			chart.setGraphList(graphList);
			//
			this.logger.info("名称:" + graph.getGraphName());
		}

		return chart;
	}

	/**
	 * 
	 * 
	 * 
	 * 
	 */
	public String graphUnitStr(String unitId, String UnitStrPost) {
		if ("5004".equals(unitId)) {
			return "%";
		} else if ("5005".equals(unitId)) {
			return "基点";
		} else {
			return UnitStrPost;
		}

	}

	private List<GraphDataElement> getGraphDatas(List<DwmisKpiData> kpiDataList) {
		if (kpiDataList == null)
			return null;
		List<GraphDataElement> dataElements = new ArrayList<GraphDataElement>();
		for (DwmisKpiData kpiData : kpiDataList) {
			GraphDataElement graphDataElement = new GraphDataElement();
			graphDataElement.setValueDate(DateUtils.formatUtilDate(
					kpiData.getReportDate(), "yyyyMMdd"));
			graphDataElement.setValue(String.valueOf(kpiData.getValue()));
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
			final List<DwmisKpiData> stKpiDateList) {
		if (stKpiDateList == null || stKpiDateList.isEmpty())
			return;
		this.logger.debug("－－－－－－－＞获取归一化值");
		String reportDate = "";
		Map<String, BigDecimal> maxAndMinKpiValue = getMaxAndMinKpiValue(stKpiDateList);
		for (DwmisKpiData DwmisKpiData : stKpiDateList) {
			reportDate = DateUtils.formatUtilDate(DwmisKpiData.getReportDate(),
					"yyyyMMdd");
			// 原有的数据
			String oldDataValue = areaDataMap.get(reportDate);
			if (null == DwmisKpiData.getValue()) {
				areaDataMap.put(reportDate, StringUtils
						.notNullAndSpace(oldDataValue) ? oldDataValue + ",0"
						: "0");
			} else {
				String dataValue = String.valueOf(getPercentValue(
						DwmisKpiData.getValue(),
						maxAndMinKpiValue.get(MAX_KPI_DATA_BASE_VALUE),
						maxAndMinKpiValue.get(MIN_KPI_DATA_BASE_VALUE)))
						+ ","
						+ String.valueOf(DwmisKpiData.getShowValue());
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
			final List<DwmisKpiData> stKpiDateList, final DwmisKpiInfo kpiInfo) {
		if (stKpiDateList == null || stKpiDateList.isEmpty()) {
			this.logger.info("---->没有得到指标数据:" + kpiInfo.getKpiCode());
			return;
		}
		String reportDate = "";
		for (DwmisKpiData kpiData : stKpiDateList) {
			reportDate = DateUtils.formatUtilDate(kpiData.getReportDate(),
					"yyyyMMdd");
			String oldDataValue = areaDataMap.get(reportDate);

			if (null == kpiData.getValue()) {
				String dataValue = "0";
				areaDataMap.put(reportDate, StringUtils
						.notNullAndSpace(oldDataValue) ? oldDataValue + ","
						+ dataValue : dataValue);
			} else {
				String dataValue = String.valueOf(kpiData.getShowValue()) + ","
						+ String.valueOf(kpiData.getShowValue());
				areaDataMap.put(reportDate, StringUtils
						.notNullAndSpace(oldDataValue) ? oldDataValue + ","
						+ dataValue : dataValue);

			}

		}
	}

	/**
	 * 
	 * 按日查看 计算前期值 和环比
	 * 
	 * */
	private void getProphaseValueData(TreeMap<String, String> areaDataMap,
			final List<DwmisKpiData> stKpiDateList, final DwmisKpiInfo kpiInfo) {
		if (stKpiDateList == null || stKpiDateList.isEmpty()) {
			this.logger.info("---->没有得到指标数据:" + kpiInfo.getKpiCode());
			return;
		}
		String reportDate = "";
		for (DwmisKpiData kpiData : stKpiDateList) {
			reportDate = DateUtils.formatUtilDate(kpiData.getReportDate(),
					"yyyyMMdd");
			String oldDataValue = areaDataMap.get(reportDate);
			// 前期值
			String prophaseValue = "0.0";
			if (!StringUtils.notNullAndSpace(prophaseValue = areaDataMap
					.get(DateUtils.formatByFormatRule(DateUtils
							.getTheNextDay(DateUtils.parseByFormatRule(
									reportDate, "yyyyMMdd")), "yyyyMMdd")))) {
				prophaseValue = "0.0,0.0";
			}
			prophaseValue = prophaseValue.substring(0,
					prophaseValue.indexOf(","));
			// 环比
			String linkRelativeRatio = "0.0";

			// 如果前期值 不为空的话 计算 环比指标
			if (StringUtils.notNullAndSpace(prophaseValue)) {
				// 当前值
				BigDecimal currentDoubleValue = new BigDecimal(
						kpiData.getShowValue());
				BigDecimal prophaseDoubleValue = new BigDecimal(
						Double.valueOf(prophaseValue));
				Double linkRelativeRatioByDouble = 0.0;
				try {
					linkRelativeRatioByDouble = currentDoubleValue
							.subtract(prophaseDoubleValue)
							.divide(prophaseDoubleValue, 4,
									BigDecimal.ROUND_HALF_EVEN)
							.multiply(new BigDecimal(100)).doubleValue();
				} catch (Exception e) {

					linkRelativeRatioByDouble = 0.0;
				}

				linkRelativeRatio = linkRelativeRatioByDouble.toString();
			}
			// String oldDataValue = areaDataMap.get(reportDate);
			if (null == kpiData.getValue()) {
				String dataValue = "0";
				areaDataMap.put(reportDate, StringUtils
						.notNullAndSpace(oldDataValue) ? oldDataValue + ","
						+ dataValue : dataValue);
			} else {
				String dataValue = String.valueOf(kpiData.getShowValue()) + ","
						+ String.valueOf(kpiData.getShowValue());
				areaDataMap.put(reportDate, StringUtils
						.notNullAndSpace(oldDataValue) ? oldDataValue + ","
						+ dataValue : dataValue + "," + prophaseValue + ","
						+ linkRelativeRatio);

			}
		}
	}

	/**
	 * 
	 * 按周查看 计算前期值 和环比
	 * 
	 * */
	private void getProphaseValueDataByWeek(
			TreeMap<String, String> areaDataMap,
			final List<DwmisKpiData> stKpiDateList, final DwmisKpiInfo kpiInfo) {
		if (stKpiDateList == null || stKpiDateList.isEmpty()) {
			this.logger.info("---->没有得到指标数据:" + kpiInfo.getKpiCode());
			return;
		}
		String reportDate = "";
		for (DwmisKpiData kpiData : stKpiDateList) {
			reportDate = DateUtils.formatUtilDate(kpiData.getReportDate(),
					"yyyyMMdd");
			String oldDataValue = areaDataMap.get(reportDate);
			// 前期值
			String prophaseValue = "0.0";
			if (!StringUtils.notNullAndSpace(prophaseValue = areaDataMap
					.get(DateUtils.formatByFormatRule(DateUtils
							.getPrevious7Date(DateUtils.parseByFormatRule(
									reportDate, "yyyyMMdd")), "yyyyMMdd")))) {
				prophaseValue = "0.0,0.0";
			}
			prophaseValue = prophaseValue.substring(0,
					prophaseValue.indexOf(","));
			// 环比
			String linkRelativeRatio = "0.0";

			// 如果前期值 不为空的话 计算 环比指标
			if (StringUtils.notNullAndSpace(prophaseValue)) {
				// 当前值
				BigDecimal currentDoubleValue = new BigDecimal(
						kpiData.getShowValue());
				BigDecimal prophaseDoubleValue = new BigDecimal(
						Double.valueOf(prophaseValue));
				Double linkRelativeRatioByDouble = 0.0;
				try {
					linkRelativeRatioByDouble = currentDoubleValue
							.subtract(prophaseDoubleValue)
							.divide(prophaseDoubleValue, 4,
									BigDecimal.ROUND_HALF_EVEN)
							.multiply(new BigDecimal(100)).doubleValue();
				} catch (Exception e) {

					linkRelativeRatioByDouble = 0.0;
				}

				linkRelativeRatio = linkRelativeRatioByDouble.toString();
			}
			// String oldDataValue = areaDataMap.get(reportDate);
			if (null == kpiData.getValue()) {
				String dataValue = "0";
				areaDataMap.put(reportDate, StringUtils
						.notNullAndSpace(oldDataValue) ? oldDataValue + ","
						+ dataValue : dataValue);
			} else {
				String dataValue = String.valueOf(kpiData.getShowValue()) + ","
						+ String.valueOf(kpiData.getShowValue());
				areaDataMap.put(reportDate, StringUtils
						.notNullAndSpace(oldDataValue) ? oldDataValue + ","
						+ dataValue : dataValue + "," + prophaseValue + ","
						+ linkRelativeRatio);

			}
		}
	}

	/**
	 * 
	 * 按月查看 计算前期值 和环比
	 * 
	 * */
	private void getProphaseValueDataByMonth(
			TreeMap<String, String> areaDataMap,
			final List<DwmisKpiData> stKpiDateList, final DwmisKpiInfo kpiInfo) {
		if (stKpiDateList == null || stKpiDateList.isEmpty()) {
			this.logger.info("---->没有得到指标数据:" + kpiInfo.getKpiCode());
			return;
		}
		String reportDate = "";
		for (DwmisKpiData kpiData : stKpiDateList) {
			reportDate = DateUtils.formatUtilDate(kpiData.getReportDate(),
					"yyyyMM");
			String oldDataValue = areaDataMap.get(reportDate);
			// 前期值
			String prophaseValue = "0.0";
			if (!StringUtils.notNullAndSpace(prophaseValue = areaDataMap
					.get(DateUtils.formatByFormatRule(DateUtils
							.getTheNextDay(DateUtils.parseByFormatRule(
									reportDate, "yyyyMM")), "yyyyMM")))) {
				prophaseValue = "0.0,0.0";
			}
			prophaseValue = prophaseValue.substring(0,
					prophaseValue.indexOf(","));
			// 环比
			String linkRelativeRatio = "0.0";

			// 如果前期值 不为空的话 计算 环比指标
			if (StringUtils.notNullAndSpace(prophaseValue)) {
				// 当前值
				BigDecimal currentDoubleValue = new BigDecimal(
						kpiData.getShowValue());
				BigDecimal prophaseDoubleValue = new BigDecimal(
						Double.valueOf(prophaseValue));
				Double linkRelativeRatioByDouble = 0.0;
				try {
					linkRelativeRatioByDouble = currentDoubleValue
							.subtract(prophaseDoubleValue)
							.divide(prophaseDoubleValue, 4,
									BigDecimal.ROUND_HALF_EVEN)
							.multiply(new BigDecimal(100)).doubleValue();
				} catch (Exception e) {

					linkRelativeRatioByDouble = 0.0;
				}

				linkRelativeRatio = linkRelativeRatioByDouble.toString();
			}
			// String oldDataValue = areaDataMap.get(reportDate);
			if (null == kpiData.getValue()) {
				String dataValue = "0";
				areaDataMap.put(reportDate, StringUtils
						.notNullAndSpace(oldDataValue) ? oldDataValue + ","
						+ dataValue : dataValue);
			} else {
				String dataValue = String.valueOf(kpiData.getShowValue()) + ","
						+ String.valueOf(kpiData.getShowValue());
				areaDataMap.put(reportDate, StringUtils
						.notNullAndSpace(oldDataValue) ? oldDataValue + ","
						+ dataValue : dataValue + "," + prophaseValue + ","
						+ linkRelativeRatio);

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
			List<DwmisKpiData> dataList) {
		Map<String, BigDecimal> minAndMaxValue = new HashMap<String, BigDecimal>();
		if (dataList != null && dataList.size() > 0) {
			BigDecimal max = dataList.get(0).getValue();
			BigDecimal min = dataList.get(0).getValue();
			for (DwmisKpiData kpiData : dataList) {
				if (max.compareTo(kpiData.getValue()) < 0) {
					max = kpiData.getValue();
				}
				if (min.compareTo(kpiData.getValue()) > 0) {
					if (kpiData.getValue().compareTo(new BigDecimal(0)) == 0)
						continue;
					min = kpiData.getValue();
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
			if (max.compareTo(min) < 0) {
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
