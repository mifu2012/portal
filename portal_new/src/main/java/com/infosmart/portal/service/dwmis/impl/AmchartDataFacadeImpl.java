package com.infosmart.portal.service.dwmis.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.BigEventPo;
import com.infosmart.portal.service.dwmis.AmchartDataFacade;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.dwmis.CoreConstant;
import com.infosmart.portal.vo.Chart;
import com.infosmart.portal.vo.Graph;
import com.infosmart.portal.vo.dwmis.ChartData;
import com.infosmart.portal.vo.dwmis.ChartEvent;
import com.infosmart.portal.vo.dwmis.LineData;
import com.infosmart.portal.vo.dwmis.LineElem;

@Service
public class AmchartDataFacadeImpl extends KPIChartManagerImpl implements
		AmchartDataFacade {

	@Override
	public Chart getKpiDetailData(String kpiCode, String period,
			String linkedPageChartType, String needPercent, String hidden,
			String domain, boolean haveGoal, String haveEvent, String lineType) {
		Chart chart = new Chart();
		if (kpiCode == null || "".equals(kpiCode)) {
			this.logger.warn("kpi详情中kpiCode值为null");
			return chart;
		}
		int pd = 0;
		if (period == null || "".equals(period)) {
			pd = 1002; // 默认为日
			this.logger.warn("period值为null,采用默认值为1002~");
		} else {
			pd = Integer.valueOf(period);
		}

		if (linkedPageChartType == null || "".equals(linkedPageChartType)) {
			this.logger.warn("数据是否归一化的值没有指明");
			return chart;
		}
		if (haveEvent == null || "".equals(haveEvent)) {
			// 是否有大事记，默认为“有”
			haveEvent = "true";
			this.logger.warn("标记是否有大事记的值为null");
		}

		try {
			this.logger.info("数据开始从 ChartData --> char的格式转换");
			ChartData chartData = null;

			// 区分是金融的主界面，还是子界面请求
			if (kpiCode.indexOf(',') == -1) {
				// ----need to be updated
				chartData = this.getKPIDataForLinkedKPIPage(kpiCode, pd, false,
						linkedPageChartType, domain, haveGoal, haveEvent);
			} else {
				if (lineType == null || "".equals(lineType)) {
					this.logger.warn("金融支付成功率线的类型的值为null");
					return chart;
				}
				String[] kpiCodes = kpiCode.substring(0, kpiCode.length() - 1)
						.split(",");
				String[] lineTypes = lineType.substring(0,
						lineType.length() - 1).split(",");

				chartData = this.getKPIDataForFinanceSonPage(kpiCodes,
						lineTypes, pd, domain, haveEvent);
			}		

			// char格式转换
			chart = getZeroAndOnePercentData(chartData, linkedPageChartType,
					needPercent, hidden, haveGoal, domain);

			this.logger.info("转换数据完成");
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
		return chart;
	}

	/**
	 * 为KPI详情页面提供归一百分比数据
	 * 
	 * @param chartData
	 *            数据源
	 * @param linkedPageChartType
	 *            归一化或者源数据展现的标识
	 * @param needPercentParm
	 *            是否需要归一化
	 * @param hidden
	 *            线隐藏的标识
	 * @param haveGoal
	 *            是否需要目标线
	 * @param domain
	 *            来自哪个模块
	 * @return chart 展现的数据
	 * @author wb-yingpf
	 */
	private Chart getZeroAndOnePercentData(ChartData chartData,
			String linkedPageChartType, String needPercentParm, String hidden,
			boolean haveGoal, String domain) {

		Chart chart = new Chart();
		// 初始化颜色池
		// ColorFactory cf = new ColorFactory(initColorList);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		// 保存最大最小值
		Map<String, Double> chartValue = new HashMap<String, Double>();
		chartValue.put("max", Double.MIN_VALUE);
		chartValue.put("min", Double.MAX_VALUE);

		try {
			// 封装面积图的所有数据以日期date为key。每条线的value以逗号拼接成的字符串为value
			TreeMap<String, String> areaMap = new TreeMap<String, String>();

			List<Graph> graphs = new ArrayList<Graph>();

			List<LineData> linelist = chartData.getLineList();
			// 获得去年同期线和主KPI线
			// 去年同期线 数据
			List<LineElem> lastYearLine = null;
			// 主KPI线数据
			List<LineElem> mianKpiLine = null;
			for (LineData ld : linelist) {
				if (ld.getLineType() == LineData.LAST_YEAR_LINE) {
					lastYearLine = new ArrayList<LineElem>(ld.getDataList());
				}
				if (ld.getLineType() == LineData.MAIN_LINE) {
					mianKpiLine = new ArrayList<LineElem>(ld.getDataList());
				}
			}
			// 创建去年同期与主KPI的同比数据线
			Map<Date, Double> lastyearyoyRate = null;
			if (lastYearLine != null && mianKpiLine != null) {
				lastyearyoyRate = getYearonyearGrowthRate(lastYearLine,
						mianKpiLine);
			}
			// 创建主KPI的环比数据线
			Map<Date, Map<String, Double>> mainKpiacgrRate = null;
			if (mianKpiLine != null) {
				mainKpiacgrRate = getAnnulusComparingGrowthRate(mianKpiLine);
			}
			if ("percent".equals(linkedPageChartType)) {

				// 有无归一化的标识,默认是归一化
				boolean needPercent = true;
				if (StringUtils.notNullAndSpace(needPercentParm)
						&& String.valueOf(Boolean.FALSE)
								.equals(needPercentParm)) {
					needPercent = false;
				}
				int i = 0;
				for (LineData ld : linelist) {
					Graph graph = new Graph();

					/**
					 * 处理数据
					 */

					// 获取集合中的最大值和最小值
					Map<String, Double> MinAndMaxMap = getMaxAndMinValue(ld
							.getDataList());
					if (MinAndMaxMap != null && MinAndMaxMap.get("min") != null
							&& MinAndMaxMap.get("max") != null) {
						for (LineElem le : ld.getDataList()) {
							String dateValue = sdf.format(le.date);

							/**
							 * modified by @author wb-yingpf 2011年9月14日
							 */
							String value = null;
							if (needPercent) {
								// 计算百分比归一后的数据
								value = String.valueOf(getPercentValue(
										le.value, MinAndMaxMap.get("max"),
										MinAndMaxMap.get("min")));
							} else {
								value = String.valueOf(le.value);
							}
							/**
							 * end
							 */

							// 数据默认值转换 DoubleDouble.MIN_VALUE --> 0.0
							value = dateTransform(value);
							// 封装AreaMap数据
							encapAreaMap(
									areaMap,
									dateValue,
									value,
									String.valueOf(le.value == Double.MIN_VALUE ? ""
											: le.value));

							// 设置setting文件的max属性
							if (!"".equals(value)) {

								// 保存最大最小值
								keepMaxAndMinValue(value, chartValue);
							}
						}
					}

					// 封装Graph信息
					encapGraph(ld, graph, Constants.CHART_COLOR_LIST.get(i++));
					graphs.add(graph);
				}
			} else if ("original".equals(linkedPageChartType)) {
				int i = 0;
				for (LineData ld : linelist) {
					Graph graph = new Graph();

					/**
					 * 处理数据
					 */
					for (LineElem le : ld.getDataList()) {
						String dateValue = sdf.format(le.date);
						String value = String.valueOf(le.value);
						value = dateTransform(value);

						// 去年线、主线、金融类型的线、其他类型的线
						if (ld.getLineType() == LineData.LAST_YEAR_LINE) {
							String lastyearyoyValue = "0.0";
							if (lastyearyoyRate.get(le.date) != null) {
								lastyearyoyValue = String
										.valueOf(lastyearyoyRate.get(le.date));
							} else {
								lastyearyoyValue = "0.0";
							}

							// 去年线要封装 真实值和同比值
							String otherValue = value + "," + lastyearyoyValue;
							// 封装AreaMap数据
							encapAreaMap(areaMap, dateValue, value, otherValue);

						} else if (ld.getLineType() == LineData.MAIN_LINE
								&& !CoreConstant.DOMAIN_FINANCE
										.equalsIgnoreCase(domain)) {

							String mainKpiacgrValue = "0.0";
							String lastTimeValue = "0.0";
							if (mainKpiacgrRate.get(le.date) != null
									&& mainKpiacgrRate.get(le.date).get(
											"acgrValue") != null
									&& mainKpiacgrRate.get(le.date).get(
											"lastTimeValue") != null) {
								mainKpiacgrValue = String
										.valueOf(mainKpiacgrRate.get(le.date)
												.get("acgrValue"));

								double lastTimeValueTemp = mainKpiacgrRate.get(
										le.date).get("lastTimeValue");
								if (lastTimeValueTemp == CoreConstant.DEFAULT_DATA_NOT_FOUND) {
									lastTimeValue = "";
								} else {
									lastTimeValue = String
											.valueOf(lastTimeValueTemp);
								}
							} else {
								mainKpiacgrValue = "0.0";
								lastTimeValue = "";
							}

							// 主线要封装 真实值、前期值和环比值
							String otherValue = value + "," + lastTimeValue
									+ "," + mainKpiacgrValue;
							// 封装AreaMap数据
							encapAreaMap(areaMap, dateValue, value, otherValue);

							// wb-yingpf 2011年8月8日12:18:29 金融特殊化
						} else if (CoreConstant.DOMAIN_FINANCE
								.equalsIgnoreCase(domain)) {

							String otherValue = "";
							if (le.value != CoreConstant.DEFAULT_DATA_NOT_FOUND) {

								// 金融线要封装数据和放大的数据
								otherValue = String.valueOf(le.value
										* CoreConstant.DOMAIN_FINANCE_ADD_AREA);
							}
							// 封装AreaMap数据
							encapAreaMap(areaMap, dateValue, value, otherValue);

						} else {

							// 封装AreaMap数据
							encapAreaMap(areaMap, dateValue, value, value);

						}

						// 主线、去年的主线或者新老用户都要在原数据下记录最大值
						if (!"".equals(value)) {

							// 保存最大最小值
							keepMaxAndMinValue(value, chartValue);

						}

					}

					// 封装Graph信息
					encapGraph(ld, graph, Constants.CHART_COLOR_LIST.get(i++));
					graphs.add(graph);
				}
			}

			// 设置各种特殊线的颜色和隐藏
			setGraphColor(hidden, haveGoal, domain, graphs);

			this.logger.debug(" 最大值:" + chartValue.get("max") + "    最小值:"
					+ chartValue.get("min"));

			// *********************************封装图表信息*****************************************
			chart.setChartValue(chartValue);
			chart.setGraphList(graphs);
			chart.setAreaDataMap(areaMap);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);

		}
		return chart;
	}

	/**
	 * 封装graph线的信息
	 * 
	 * @param ld
	 * @param graph
	 */
	private void encapGraph(LineData ld, Graph graph, String color) {

		graph.setGraphId(ld.getKpiInfo().getKpiCode());

		// 只有主线的标识才有作用，该设置只有详情对详情有效果
		if (ld.getKpiInfo().getIsDataRangeFix() != null
				&& ld.getLineType() == LineData.MAIN_LINE) {
			// 是否在详情页面锁定数据范围的标识
			graph.setIsDataRangeFix(ld.getKpiInfo().getIsDataRangeFix());
			if (ld.getKpiInfo().getIsDataRangeFix() == 1
					&& ld.getKpiInfo().getDataRangeBottom() >= 0
					&& ld.getKpiInfo().getDataRangeTop() >= 0) {
				graph.setDataRangeBottom(ld.getKpiInfo().getDataRangeBottom());
				graph.setDataRangeTop(ld.getKpiInfo().getDataRangeTop());
			}
		} else {
			// 空时，默认为0，是否在详情页面锁定数据范围的标识
			graph.setIsDataRangeFix(0);
		}

		// 有时可能没有大事记，所以要判断是否为空
		if (ld.getChartEvent() != null) {
			graph.setEventList(convertEvent(ld.getChartEvent()));
		}

		graph.setUnitStr(ld.getKpiInfo().getUnitStrPost());
		graph.setGraphType(ld.getLineType());
		graph.setGraphColor(color);
		graph.setGraphName(ld.getKpiInfo().getKpiNameShow());
	}

	/**
	 * List<ChartEvent> 集合转换为 List<BigEvent>
	 * 
	 * @param eventlist
	 * @return
	 */
	private List<BigEventPo> convertEvent(List<ChartEvent> eventlist) {
		List<BigEventPo> values = new ArrayList<BigEventPo>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			for (ChartEvent ce : eventlist) {
				BigEventPo be = new BigEventPo();
				be.setContent(String.valueOf(ce.event));
				be.setEventDate(sdf.format(ce.date));
				be.setEventId(String.valueOf(ce.eventID));
				be.setIsPublic(ce.isPublic);
				values.add(be);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
		return values;
	}

	/**
	 * 设置各种特殊线的颜色和隐藏
	 * 
	 * @param hidden
	 *            隐藏 标识
	 * @param haveGoal
	 *            有无目标线
	 * @param domain
	 *            界面类型
	 * @param graphs
	 *            线
	 */
	private void setGraphColor(String hidden, boolean haveGoal, String domain,
			List<Graph> graphs) {
		if (!CoreConstant.DOMAIN_FINANCE.equalsIgnoreCase(domain)) {
			// 设置线是否隐藏
			hiddenGraph(hidden, graphs);
		} else {
			// 金融的线线是全部显示的
			for (Graph graph : graphs) {

				// 目标线的颜色
				if (graph.getGraphType() == LineData.GOAL_LINE
						|| graph.getGraphType() == LineData.OLD_LINE_FOR_FINANCE) {
					graph.setGraphColor("#0066FF");
				}

				// 有目标线的线的颜色不一样，有目标线和新用户的颜色
				if (graph.getGraphType() == LineData.MAIN_LINE
						&& haveGoal
						|| graph.getGraphType() == LineData.NEW_LINE_FOR_FINANCE) {
					graph.setGraphColor("#FF9933");
				}

				graph.setIshidden("0");
			}
		}
	}

	/**
	 * 设置线是否隐藏
	 * 
	 * @param hidden
	 * @param graphs
	 * 
	 */
	private void hiddenGraph(String hidden, List<Graph> graphs) {

		// 没有关联指标
		if (hidden == null) {
			for (Graph graph : graphs) {
				if (graph.getGraphType() == LineData.MAIN_LINE) {
					graph.setIshidden("0");
				} else if (graph.getGraphType() == LineData.LAST_YEAR_LINE) {
					graph.setIshidden("1");
				}
			}
		} else {
			this.logger.info("hidden:" + hidden + "  graphs.size():"
					+ graphs.size());
			// 关联指标
			for (Graph graph : graphs) {
				if (graph.getGraphType() == LineData.NORMAL_LINE) {
					if (hidden.indexOf(graph.getGraphId()) != -1) {
						graph.setIshidden("0");
					}
				} else if (graph.getGraphType() == LineData.MAIN_LINE) {
					graph.setIshidden("0");
				} else if (graph.getGraphType() == LineData.LAST_YEAR_LINE) {
					graph.setIshidden("1");
				}
			}
		}
	}

	/**
	 * 计算归一百分比值
	 * */
	private double getPercentValue(double value, double max, double min) {
		double percentValue = 0.00;
		try {
			if (max < min) {
				this.logger.warn("在计算百分比数值，最大值小于最小值");
			} else {
				if (value == CoreConstant.DEFAULT_DATA_NOT_FOUND) {
					percentValue = CoreConstant.DEFAULT_DATA_NOT_FOUND;
				} else {
					percentValue = ((value - min) / (max - min)) * 100;
				}
			}
		} catch (Exception e) {
			this.logger.error("计算归一百分比值", e);
		}
		// return UnitTransformer.keepDecimal(percentValue,
		// ConstantClass.DEFAULT_DIGIT);
		return percentValue;
	}

	/**
	 * 获得List<LineElem> 集合中的最大值和最小值
	 * 
	 * */
	private Map<String, Double> getMaxAndMinValue(List<LineElem> dataList) {
		Map<String, Double> MMValue = new HashMap<String, Double>();

		if (dataList != null && dataList.size() > 0) {
			double max = dataList.get(0).value;
			double min = dataList.get(0).value;
			for (LineElem le : dataList) {
				double temp = le.value;
				if (temp == CoreConstant.DEFAULT_DATA_NOT_FOUND)
					temp = CoreConstant.DEFAULT_NUMBER_FOR_AMCHART;
				if (max < temp) {
					max = temp;
				}
				if (min > temp) {
					min = temp;
				}
			}

			MMValue.put("max", max);
			MMValue.put("min", min);
		}

		return MMValue;
	}

	/**
	 * 
	 * @param value
	 * @param chartValue
	 */
	private void keepMaxAndMinValue(String value, Map<String, Double> chartValue) {

		double temp = Double.parseDouble(value);

		if (chartValue.get("max") < temp) {
			chartValue.put("max", temp);
		}

		if (chartValue.get("min") > temp) {
			chartValue.put("min", temp);
		}
	}

	/**
	 * 封装AreaMap的数据形式
	 * 
	 * @param areaMap
	 *            封装所放的主体map
	 * @param dateValue
	 *            日期
	 * @param value
	 *            日期对应的值
	 * @param value
	 *            另外的值
	 */
	private void encapAreaMap(TreeMap<String, String> areaMap,
			String dateValue, String value, String otherValue) {

		if (areaMap.containsKey(dateValue)) {
			areaMap.put(dateValue, areaMap.get(dateValue) + "," + value + ","
					+ otherValue);
		} else {
			areaMap.put(dateValue, value + "," + otherValue);
		}
	}

	/**
	 * 计算去年同期与主KPI的同比增长率:Year-on-year growth rate
	 * 
	 * @return Map<Date,Double> 日期和同比增长率
	 * */
	private Map<Date, Double> getYearonyearGrowthRate(
			List<LineElem> lastYearLineData, List<LineElem> mainKpiDate) {

		Map<Date, Double> yoyMap = new HashMap<Date, Double>();

		// 获得去年同期的数值集合
		List<LineElem> lastYearDataList = lastYearLineData;

		// 获得主KPI的数据集合(只有一年的数据)
		List<LineElem> mainDataList = mainKpiDate;
		try {
			for (LineElem lastle : lastYearDataList) {
				for (LineElem mainle : mainDataList) {
					if (lastle.date.getTime() == mainle.date.getTime()) {
						double value = 0.0000;
						if (lastle.value != 4.9E-324 && lastle.value != 0) {
							value = (mainle.value - lastle.value)
									/ lastle.value * 100;
						}
						yoyMap.put(lastle.date, value);
						continue;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}

		return yoyMap;

	}

	/**
	 * 计算主KPI的环比增长率：Annulus comparing growth rate
	 * */

	@SuppressWarnings("unchecked")
	private Map<Date, Map<String, Double>> getAnnulusComparingGrowthRate(
			List<LineElem> KpiData) {

		Map<Date, Map<String, Double>> acgrMap = new HashMap<Date, Map<String, Double>>();

		List<LineElem> dataList = new ArrayList<LineElem>(KpiData);

		// 对datalist 按日期进行从小到大排序
		Collections.sort(dataList, new Comparator() {

			@Override
			public int compare(Object o1, Object o2) {
				LineElem lineElem1 = (LineElem) o1;
				LineElem lineElem2 = (LineElem) o2;

				return lineElem1.date.compareTo(lineElem2.date);
			}

		});
		try {
			if (dataList != null && dataList.size() > 0) {
				double lastTimeDate = CoreConstant.DEFAULT_DATA_NOT_FOUND;
				int i = 0;
				for (LineElem le : dataList) {
					double value = 0.0000;
					if (i != 0
							&& lastTimeDate != CoreConstant.DEFAULT_DATA_NOT_FOUND
							&& lastTimeDate != 0) {
						value = (le.value - lastTimeDate) / lastTimeDate * 100;
					}

					Map<String, Double> valueMap = new HashMap<String, Double>();
					valueMap.put("acgrValue", value);
					valueMap.put("lastTimeValue", lastTimeDate);
					acgrMap.put(le.date, valueMap);
					lastTimeDate = le.value;
					i++;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}

		return acgrMap;
	}

	/**
	 * 数据转换，将数据等于ConstantClass.DEFAULT_DATA_NOT_FOUND的数据默认改为“”
	 * 
	 * @param value
	 * @return
	 * */
	private String dateTransform(String value) {
		String dateValue = value;
		if (value.equals(String.valueOf(CoreConstant.DEFAULT_DATA_NOT_FOUND))) {
			dateValue = ""; // String.valueOf(ConstantClass.DEFAULT_NUMBER_FOR_AMCHART);
		}
		return dateValue;
	}

	/**
	 * 数据转换，将数据等于ConstantClass.DEFAULT_DATA_NOT_FOUND的数据默认改为0.0 专门为前钻的数据提供服务
	 * 
	 * 1.假如value是double的最小值 2.将值替换成“0.0”
	 * 
	 * @param value
	 * @return
	 * */
	private String dateTransFromArea(String value) {
		String dateValue = value;
		if (value.equals(String.valueOf(CoreConstant.DEFAULT_DATA_NOT_FOUND))) {// 1
			dateValue = String.valueOf(CoreConstant.DEFAULT_NUMBER_FOR_AMCHART);// 2
		}
		return dateValue;
	}

}
