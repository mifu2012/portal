package com.infosmart.portal.service.dwmis.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.dwmis.DwmisKpiData;
import com.infosmart.portal.pojo.dwmis.DwmisKpiGoal;
import com.infosmart.portal.pojo.dwmis.DwmisKpiInfo;
import com.infosmart.portal.pojo.dwmis.MisEventPo;
import com.infosmart.portal.service.dwmis.DwmisKpiInfoService;
import com.infosmart.portal.service.dwmis.DwmisMisTypeService;
import com.infosmart.portal.service.dwmis.KPIGoalManager;
import com.infosmart.portal.service.dwmis.SysDateForFixedYear;
import com.infosmart.portal.service.impl.BaseServiceImpl;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.dwmis.CoreConstant;
import com.infosmart.portal.util.dwmis.CoreDateToWeekUtil;
import com.infosmart.portal.util.dwmis.DoubleUtility;
import com.infosmart.portal.util.dwmis.KPICommonQueryParam;
import com.infosmart.portal.util.dwmis.TimeFormatProcessor;
import com.infosmart.portal.util.dwmis.UnitTransformer;
import com.infosmart.portal.vo.Chart;
import com.infosmart.portal.vo.Graph;
import com.infosmart.portal.vo.GraphDataElement;
import com.infosmart.portal.vo.dwmis.ChartData;
import com.infosmart.portal.vo.dwmis.ChartEvent;
import com.infosmart.portal.vo.dwmis.LineData;
import com.infosmart.portal.vo.dwmis.LineElem;

@Service
public class KPIChartManagerImpl extends BaseServiceImpl {

	@Autowired
	protected DwmisKpiInfoService kpiInfoService;

	@Autowired
	protected SysDateForFixedYear sysDateForFixedYear;

	@Autowired
	protected DwmisMisTypeService misTypeService;

	@Autowired
	protected KPIGoalManager kpiGoalManager;

	/**
	 * 为bundle图表提供数据
	 * */
	protected Chart getTendChartData(ChartData chartData, String period) {
		Chart chart = new Chart();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		try {
			/**
			 * 生成x轴显示信息。 series 保存x轴坐标值
			 */
			TreeSet<Date> dates = new TreeSet<Date>();
			for (LineData ld : chartData.getLineList()) {
				for (LineElem le : ld.getDataList()) {
					dates.add(le.date);
				}
			}
			LinkedHashMap<String, String> series = new LinkedHashMap<String, String>();
			// 定义日期格式为yyyy年MM月dd日

			if (period.equals("1003")) {
				String nowDateValue = "";
				for (Date d : dates) {
					String nowDate = sdf.format(d);

					// 判断是第几周
					nowDateValue = CoreDateToWeekUtil.dayToWeekend(d);
					if (!"".equals(nowDateValue)) {
						series.put(nowDate, nowDateValue);
					}
				}
			} else {

				for (Date d : dates) {
					String nowDate = sdf.format(d);
					if (!"".equals(nowDate)) {
						series.put(nowDate, nowDate);
					}
				}
			}

			// **********************************封装数据信息*****************************************
			List<Graph> graphs = new ArrayList<Graph>();
			List<LineData> linelist = chartData.getLineList();
			int gid = 0;
			for (LineData ld : linelist) {
				Graph graph = new Graph();
				graph.setGraphId(String.valueOf(gid));
				graph.setDataList(convert(ld.getDataList()));
				graph.setGraphType(ld.getLineType());
				graph.setGraphName(ld.getKpiInfo().getKpiNameShow());
				graphs.add(graph);
				gid++;
			}
			// *********************************封装图表信息*****************************************
			chart.setGraphList(graphs);
			chart.setSeriesMap(series);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
		return chart;
	}

	/**
	 * List<LineElem> 集合转换为 List<GraphDataElement>
	 * 
	 * */
	protected List<GraphDataElement> convert(List<LineElem> dataList) {
		List<GraphDataElement> values = new ArrayList<GraphDataElement>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		try {
			for (LineElem le : dataList) {
				GraphDataElement dge = new GraphDataElement();
				dge.setValue(String.valueOf(le.value));
				dge.setValueDate(sdf.format(le.date));
				values.add(dge);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
		return values;
	}

	protected ChartData getChartDataForKPITrendPage(String kpiCode) {
		if (!StringUtils.notNullAndSpace(kpiCode)) {
			this.logger.warn("指标编码为空");
			return null;
		}
		DwmisKpiInfo kpiInfo = this.kpiInfoService
				.getDwmisKpiInfoByCode(kpiCode);
		if (kpiInfo == null) {
			this.logger.warn("指标编码为空");
			return null;
		}
		ChartData chartData = null;
		// 提取日粒度数据 用于 日累积等类型
		if (kpiInfo.getGoalType() == 3005 || kpiInfo.getGoalType() == 3007
				|| kpiInfo.getGoalType() == 30052
				|| kpiInfo.getGoalType() == 30051) {
			// TODO 日累积类型
			chartData = getKPIDataForDailySum(kpiInfo);
		}

		// 提取十二个月 月粒度数据 用于月峰、月谷等类型
		else {
			// TODO 十二个月 月粒度数据 用于月峰、月谷等类型
			chartData = getKPIDataForMonthCurrent(kpiInfo);
		}
		return chartData;
	}

	/**
	 * 根据KPICODE获取KPI对应的日累计值数据，步骤（1）（2）（3）（5）
	 * 通过条件判断后被getChartDataForKPITrendPage调用
	 * 
	 * @see com.alipay.dwmis.biz.shared.KPISingleService#getKPIDataForDailySum(java.lang.String)
	 */
	public ChartData getKPIDataForDailySum(DwmisKpiInfo kpiInfoDO) {
		// 指标数据集、指标绩效集
		List<DwmisKpiData> kpiDatas = this
				.getKPIDatasRecordsByCondition(kpiInfoDO.getKpiCode());
		List<DwmisKpiGoal> kpiGoals = this
				.getKPIGoalsOnMonthsByKPICode(kpiInfoDO.getKpiCode());

		// 指标数据点元素集、指标绩效点元素集
		List<LineElem> dataElems = this.getDataLineElems(kpiInfoDO, kpiDatas,
				false, true, true, "");
		List<LineElem> goalElems = this.getGoalLineElems(kpiGoals,
				kpiInfoDO.getKpiCode());

		// 指标数据线元素、指标绩效线元素
		LineData dataLine = this.getLineData(dataElems, LineData.NORMAL_LINE,
				kpiInfoDO);
		LineData goalLine = this.getLineData(goalElems, LineData.GOAL_LINE,
				kpiInfoDO);

		// 组装视图子元素（各条线）
		List<LineData> lineList = new ArrayList<LineData>();
		lineList.add(dataLine);
		lineList.add(goalLine);
		// 1表示KPI跟踪页面
		ChartData chartData = this.getCharDate(lineList, 1);
		return chartData;
	}

	/**
	 * 
	 * （5）组装返回视图对象
	 * 
	 * @param lineList
	 * @param pageID
	 * @param chartEvents
	 * @return
	 */
	protected ChartData getCharDate(List<LineData> lineList, int pageID) {
		ChartData chartData = new ChartData();
		chartData.setPageID(pageID);

		// 把所有线的日期数据都填充完整
		fillValueForAllLines(lineList);

		// 对每条线按日期进行排序
		lineSortByDate(lineList);

		chartData.setLineList(lineList);
		/*
		 * if (chartEvents != null) { chartData.setChartEvent(chartEvents); }
		 */
		return chartData;
	}

	/**
	 * 对每条线按日期进行排序
	 * 
	 * @param lineList
	 */
	@SuppressWarnings("unchecked")
	protected void lineSortByDate(List<LineData> lineList) {
		for (LineData lineDate : lineList) {

			List<LineElem> lineElem = lineDate.getDataList();
			// 用集合的sort来进行排序，主要是按时间的大小来派
			Collections.sort(lineElem, new Comparator() {

				@Override
				public int compare(Object o1, Object o2) {
					LineElem lineElem1 = (LineElem) o1;
					LineElem lineElem2 = (LineElem) o2;

					return lineElem2.date.compareTo(lineElem1.date);
				}

			});

		}
	}

	/**
	 * 把所有线的日期数据都填充完整 即如果某线有2010-05-22的数据，某线在该日期上没有，则为该线该日期填上
	 * ConstantClass.DEFAULT_DATA_NOT_FOUND
	 * 
	 * @param lineList
	 */
	public void fillValueForAllLines(List<LineData> lineList) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		// 获得所有线上的日期
		Set<String> dateSet = findAllLineDate(lineList, dateFormat);

		// 一个个日期遍历
		for (String dateStr : dateSet) {

			// 一条条线遍历
			for (LineData lineDate : lineList) {

				boolean hasDataInThisDay = false;
				List<LineElem> lineElem = lineDate.getDataList();

				// 遍历该线上各个点
				for (LineElem elem : lineElem) {

					if (dateStr.equals(dateFormat.format(elem.date))) {

						hasDataInThisDay = true;
						break;
					}

				}
				if (!hasDataInThisDay) {

					LineElem elem = new LineElem();

					try {
						elem.date = dateFormat.parse(dateStr);
					} catch (ParseException e) {
						e.printStackTrace();
					}

					elem.value = CoreConstant.DEFAULT_DATA_NOT_FOUND;
					lineElem.add(elem);
				}
			}
		}
	}

	/**
	 * 获得所有线上的日期
	 * 
	 * @param lineList
	 * @param dateFormat
	 *            日期格式
	 * @return
	 */
	protected Set<String> findAllLineDate(List<LineData> lineList,
			SimpleDateFormat dateFormat) {
		// 把所有线的所有日期取出来
		Set<String> dateSet = new TreeSet<String>();
		for (LineData linedate : lineList) {
			for (LineElem elem : linedate.getDataList()) {
				dateSet.add(dateFormat.format(elem.date));
			}
		}
		return dateSet;
	}

	/**
	 * 
	 * （3）根据点指标元素和类型获取线指标元素
	 * 
	 * @param elems
	 * @return
	 */
	protected LineData getLineData(List<LineElem> elems, int lineType,
			DwmisKpiInfo kpiInfo) {
		if (elems == null) {
			return null;
		}
		LineData lineData = new LineData();
		if (elems.size() == 0 || elems == null) {
			lineData.setHasData(false);
		} else {
			lineData.setHasData(true);
		}
		lineData.setDataList(elems);
		lineData.setLineType(lineType);
		lineData.setKpiInfo(kpiInfo);
		return lineData;
	}

	/**
	 * 
	 * （2）获取指标绩效目标点元素集（kpiGoals是月绩效）
	 * 
	 * @param kpiGoals
	 * @return
	 */
	protected List<LineElem> getGoalLineElems(List<DwmisKpiGoal> kpiGoals,
			String kpiCode) {
		List<LineElem> elems = new ArrayList<LineElem>();

		DwmisKpiGoal specialGoalDO = new DwmisKpiGoal();

		// 为无线事业部的后两个指标做特殊处理
		// 目标线初始值使用的不是1月1号的真实值，而是第一个月的目标值，使得整个目标线是一条水平线
		if (kpiCode.equalsIgnoreCase("WAP20000001")
				|| kpiCode.equalsIgnoreCase("WAP30000018")) {

			if (kpiGoals.size() != 0) {
				specialGoalDO.setScroll35(kpiGoals.get(0).getScroll35());
			} else {
				specialGoalDO.setScroll35(new BigDecimal(0.0));
			}

		} else {

			// 把该kpiCode对应的一月一日的日累积指标数据值作为目标绩效值的原点值
			specialGoalDO.setScore35(this.getTheKPIDataValueForStep2(kpiCode));

		}
		kpiGoals.add(0, specialGoalDO);

		for (int i = 1; i < kpiGoals.size(); i++) {
			DwmisKpiGoal goalDO = kpiGoals.get(i - 1);
			DwmisKpiGoal nextGoalDO = kpiGoals.get(i);
			int maxDays = TimeFormatProcessor
					.getMonthMaxDaysByCurrentDate(nextGoalDO.getGoalDate());
			for (int j = 1; j <= maxDays; j++) {
				LineElem elem = new LineElem();
				if (j < 10) {
					elem.date = DateUtils.parseByFormatRule(
							nextGoalDO.getGoalDate() + "0" + j, "yyyyMMdd");
				} else {
					elem.date = DateUtils.parseByFormatRule(
							nextGoalDO.getGoalDate() + j, "yyyyMMdd");
				}
				if (i == 1 && j == 1) {
					// 1月1日的数据
					elem.value = goalDO.getScore35();
				} else if (i == 1 && j != 1) {
					// 一月份因为原点的关系，所以计算上区别于其他月份
					elem.value = this.calculation(goalDO.getScore35(),
							nextGoalDO.getScore35(), maxDays - 1)
							* (j - 1)
							+ goalDO.getScore35();
				} else {
					elem.value = this.calculation(goalDO.getScore35(),
							nextGoalDO.getScore35(), maxDays)
							* j
							+ goalDO.getScore35();
				}
				DwmisKpiInfo kpiDO = new DwmisKpiInfo();
				kpiDO.setKpiCode(kpiCode);
				elem.value = UnitTransformer.keepDecimal(elem.value,
						CoreConstant.DEFAULT_DIGIT, kpiDO);
				elems.add(elem);
			}
		}
		return elems;
	}

	/**
	 * 
	 * 辅助方法：对计算:"(b-a)/c"的结果
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	protected double calculation(double a, double b, int c) {
		return DoubleUtility.minus(b, a) / c;
	}

	/**
	 * 
	 * 辅助方法：为步骤（2）提供当前年一月一日的日累积指标数据值 作为目标绩效的原点值
	 * 
	 * @param kpiCode
	 * @return
	 */
	protected Double getTheKPIDataValueForStep2(String kpiCode) {
		KPICommonQueryParam param = new KPICommonQueryParam();
		param.setKpiCode(kpiCode);
		param.setDateType(1002);
		param.setStaCode(2002);
		param.setCurrentDate(TimeFormatProcessor.getCurrentYearFirstDayOrMonth(
				TimeFormatProcessor.FIRST_DAY, 0));
		DwmisKpiData kpiData = this.myBatisDao
				.get("com.infosmart.dwmis.DwmisMisKpiDataMapper.GET_KPIDATA_ON_CONDITION",
						param);
		if (kpiData == null) {
			return 0.0;
		}
		DwmisKpiInfo infoDO = this.kpiInfoService
				.getDwmisKpiInfoByCode(kpiCode);
		return UnitTransformer.transform(kpiData.getShowValue(),
				infoDO.getSizeId(), infoDO);
	}

	/**
	 * 
	 * （1）获取指标数据点元素集
	 * 
	 * @param kpiDatas
	 * @param kpiInfo
	 * @param isTransformUnit
	 *            true 需要转换单位 false 无需转换单位
	 * @param isHasUnitForKPIName
	 *            true KPINameShow中带单位 false KPINameShow中不带单位
	 * @return
	 */
	protected List<LineElem> getDataLineElems(DwmisKpiInfo kpiInfo,
			List<DwmisKpiData> kpiDatas, boolean isLastYearLine,
			boolean isTransformUnit, boolean isHasUnitForKPIName, String domain) {
		List<LineElem> elems = new ArrayList<LineElem>();
		Integer sizeID = kpiInfo.getSizeId();
		if (sizeID == null) {
			sizeID = 0;
		}
		for (DwmisKpiData dataDO : kpiDatas) {
			LineElem elem = new LineElem();

			// 如果是去年同期线，把该线向前平移一年
			if (isLastYearLine) {
				elem.date = TimeFormatProcessor.addOneYear(dataDO
						.getReportDate());
			} else {
				elem.date = dataDO.getReportDate();
			}

			if (dataDO.getValue() != null) {
				// 转换成指定数量级别
				if (isTransformUnit) {
					// 金融专题两位小数展示
					if (CoreConstant.DOMAIN_FINANCE.equalsIgnoreCase(domain)
							|| CoreConstant.DOMAIN_QUICKFINANCE
									.equalsIgnoreCase(domain)) {
						double result = UnitTransformer.transformFullValue(
								dataDO.getShowValue(), sizeID, kpiInfo);
						elem.value = UnitTransformer.keepDecimal(result,
								CoreConstant.DEFAULT_DIGIT_FINANCE, kpiInfo);
					} else {
						elem.value = UnitTransformer.transform(
								dataDO.getShowValue(), sizeID, kpiInfo);
					}

				} else {
					elem.value = dataDO.getShowValue();
				}
			} else {
				elem.value = CoreConstant.DEFAULT_DATA_NOT_FOUND;
			}
			elems.add(elem);
		}

		// 获取单位数量级String
		String size = misTypeService.getMisTypeByTypeId(kpiInfo.getSizeId())
				.getTypeName();
		String unit = misTypeService.getMisTypeByTypeId(kpiInfo.getUnitId())
				.getTypeName();

		String unitStrPost = "";
		if (size == null || "个".equals(size) || "".equals(size)) {

		} else {
			// 改变所有KPI的NameShow，加上数量级
			if (isHasUnitForKPIName) {
				kpiInfo.setKpiNameShow(kpiInfo.getKpiNameShow() + "(" + size
						+ ")");
				unitStrPost += "(" + size + ")";
			}
		}

		if ("%".equals(unit)) {
			kpiInfo.setKpiNameShow(kpiInfo.getKpiNameShow() + "(%)");
			unitStrPost += "(%)";
		} else {
			kpiInfo.setUnitStr(unit);
		}

		// 指标单位字符（跟随在KPI Name后面的，形如 生活助手新增用户数(万)，这里的 (万)
		kpiInfo.setUnitStrPost(unitStrPost);

		return elems;
	}

	/**
	 * 
	 * 根据指标CODE获取当前年每个月度的绩效目标（提供给KPI跟踪情况）
	 * 
	 * @param kpiCode
	 * @return
	 */
	protected List<DwmisKpiGoal> getKPIGoalsOnMonthsByKPICode(String kpiCode) {
		KPICommonQueryParam param = new KPICommonQueryParam();
		param.setKpiCode(kpiCode);

		param.setFixedYear(sysDateForFixedYear.getFixedYear());

		return this.myBatisDao
				.getList(
						"com.infosmart.dwmis.DwmisKpiGoalMapper.QUERY_KPIGOAL_LIST_FOR_MONTH",
						param);
	}

	/**
	 * 
	 * 根据指标CODE获取当前年第一天到目前的日累积值（提供给KPI跟踪情况）
	 * 
	 * @param dataQueryParam
	 * @return
	 */
	protected List<DwmisKpiData> getKPIDatasRecordsByCondition(String kpiCode) {

		KPICommonQueryParam dataQueryParam = new KPICommonQueryParam();

		dataQueryParam.setFixedYear(sysDateForFixedYear.getFixedYear());

		dataQueryParam.setKpiCode(kpiCode);

		// 时间粒度：日
		dataQueryParam.setDateType(1002);
		// 统计方式：期末值
		dataQueryParam.setStaCode(2002);

		// “KPI跟踪amChart图” 中 无线业务笔数 比较特殊，看的是七日均值，而不是日累积值
		if (kpiCode.equals("WAP20000001")) {
			dataQueryParam.setStaCode(2006);
		}
		return this.myBatisDao
				.getList(
						"com.infosmart.dwmis.DwmisMisKpiDataMapper.QUERY_KPIDATA_RECORD_LIST_BY_RULES",
						dataQueryParam);
	}

	/**
	 * 根据KPICODE获取KPI对应的月当期值数据，步骤（1）（3）（5）（6）
	 * 通过条件判断后被getChartDataForKPITrendPage调用
	 * 
	 * @see com.alipay.dwmis.biz.kpidata.KPISingleService#getKPIDataForMonthCurrent(java.lang.String)
	 */
	public ChartData getKPIDataForMonthCurrent(DwmisKpiInfo kpiInfoDO) {
		// 指标数据集、指标绩效集
		List<DwmisKpiData> datas = this.queryKPIDataForMonthCurrent(kpiInfoDO
				.getKpiCode());
		List<DwmisKpiGoal> goals = this
				.getKPIGoalInCurrentYearByKPICodeToLine(kpiInfoDO.getKpiCode());

		// 指标数据点元素集、指标绩效点元素集
		List<LineElem> dataElems = this.getDataLineElems(kpiInfoDO, datas,
				false, true, true, "");
		List<LineElem> goalElems = this
				.getSpecialGoalLineElem(kpiInfoDO, goals);

		// 指标数据线元素、指标绩效线元素
		LineData dataLine = this.getLineData(dataElems, LineData.NORMAL_LINE,
				kpiInfoDO);
		LineData goalLine = this.getLineData(goalElems, LineData.GOAL_LINE,
				kpiInfoDO);

		// 组装视图子元素（各条线）
		List<LineData> lineList = new ArrayList<LineData>();

		lineList.add(dataLine);

		if (goalLine != null) {
			lineList.add(goalLine);
		}

		// 1表示KPI跟踪页面
		ChartData chartData = this.getCharDate(lineList, 1);
		return chartData;
	}

	/**
	 * 
	 * （6）获取指标绩效目标点元素集（kpiGoals是年绩效值经处 changeWayToShow理后的）
	 * 
	 * @param kpiInfoDO
	 * @param kpiGoals
	 * @param kpiCode
	 * @return
	 */
	protected List<LineElem> getSpecialGoalLineElem(DwmisKpiInfo kpiInfoDO,
			List<DwmisKpiGoal> kpiGoals) {
		if (kpiGoals == null) {
			return null;
		}
		List<LineElem> elems = new ArrayList<LineElem>();
		for (int i = 0; i < kpiGoals.size(); i++) {
			int maxDays = TimeFormatProcessor
					.getMonthMaxDaysByCurrentDate(kpiGoals.get(i).getGoalDate());
			LineElem elem = new LineElem();
			elem.date = DateUtils.parseByFormatRule(kpiGoals.get(i)
					.getGoalDate() + maxDays, "yyyyMMdd");
			elem.value = UnitTransformer.keepDecimal(kpiGoals.get(i)
					.getScore35(), CoreConstant.DEFAULT_DIGIT, kpiInfoDO);
			elems.add(elem);
		}
		return elems;
	}

	/**
	 * 
	 * 根据指标CODE获取当前年的绩效目标（提供给KPI跟踪情况） 以线的形式展示，所以把该年绩效值以每个月的绩效都等于 该值的平行线表示
	 * 
	 * @param kpiCode
	 * @return
	 */
	protected List<DwmisKpiGoal> getKPIGoalInCurrentYearByKPICodeToLine(
			String kpiCode) {
		DwmisKpiGoal kpiGoalDO = null;
		List<DwmisKpiGoal> goals = null;
		try {
			KPICommonQueryParam queryParam = new KPICommonQueryParam();

			int fixedYear = sysDateForFixedYear.getFixedYear();

			queryParam.setFixedYear(fixedYear);
			queryParam.setKpiCode(kpiCode);

			kpiGoalDO = this.myBatisDao
					.get("com.infosmart.dwmis.DwmisKpiGoalMapper.QUERY_KPIGOAL_FOR_CURRENT_YEAR",
							queryParam);
			if (kpiGoalDO == null) {
				return null;
			}
			goals = this.changeWayToShow(kpiGoalDO, fixedYear);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			return null;
		}
		return goals;
	}

	/**
	 * 
	 * 把年的绩效值A，以12个月都等于该值A的平行线方式展示
	 * 
	 * @param kpiGoalDO
	 * @param fixedYear
	 * @return
	 */
	protected List<DwmisKpiGoal> changeWayToShow(DwmisKpiGoal kpiGoalDO,
			int fixedYear) {
		List<DwmisKpiGoal> goals = new ArrayList<DwmisKpiGoal>();
		// 当前年
		String currentYear = String.valueOf(fixedYear);
		for (int i = 1; i <= 12; i++) {
			DwmisKpiGoal goalDO = new DwmisKpiGoal();
			goalDO.setKpiCode(kpiGoalDO.getKpiCode());
			if (i < 10) {
				goalDO.setGoalDate(currentYear + "0" + i);
			} else {
				goalDO.setGoalDate(currentYear + i);
			}
			goalDO.setScore35(kpiGoalDO.getScore35());
			goalDO.setScore5(kpiGoalDO.getScore5());
			goals.add(goalDO);
		}
		return goals;
	}

	/**
	 * 
	 * 根据条件获取对应的月当期值（提供给KPI跟踪情况）
	 * 
	 * @param kpiCode
	 * @return
	 */
	protected List<DwmisKpiData> queryKPIDataForMonthCurrent(String kpiCode) {
		KPICommonQueryParam param = new KPICommonQueryParam();
		param.setKpiCode(kpiCode);
		param.setDateType(1004);

		// 如果是“技术部”的指标，强制取期末值
		if (kpiCode.startsWith("TEC") || kpiCode.startsWith("tec")) {
			param.setStaCode(2002);
		} else {
			param.setStaCode(2001);
		}

		param.setFixedYear(sysDateForFixedYear.getFixedYear());
		return this.myBatisDao
				.getList(
						"com.infosmart.dwmis.DwmisMisKpiDataMapper.GET_KPIDATA_FOR_MONTH_CURRENT",
						param);
	}

	/**
	 * 
	 * 根据指标CODE获取时间范围在两年内的大事记列表(公用与详情视图的前钻分析视图)
	 * 
	 * @param param
	 * @return
	 */
	protected List<MisEventPo> getEventsByKPIOnCondition(
			KPICommonQueryParam param) {
		return this.myBatisDao
				.getList(
						"com.infosmart.dwmis.DwmisMisKpiDataMapper.QUERY_EVENT_LIST_BY_KPICODE",
						param);

	}

	/**
	 * 
	 * (4)把根据条件获取的大事记集转换为ChartEvent列表
	 * 
	 * @param misEvents
	 * @return
	 */
	protected List<ChartEvent> getChartEvents(List<MisEventPo> misEvents,
			int period) {
		if (misEvents == null) {
			return null;
		}
		List<ChartEvent> chartEvents = new ArrayList<ChartEvent>();
		for (MisEventPo eventDO : misEvents) {
			ChartEvent chartEvent = new ChartEvent();
			chartEvent.event = eventDO.getTitle();
			chartEvent.eventID = Integer.parseInt(eventDO.getEventId());
			chartEvent.isPublic = eventDO.getIsPublic();
			chartEvent.originalDate = DateUtils.parseByFormatRule(
					eventDO.getEventStartDate(), "yyyy-MM-dd");
			if (period == 1003) {
				// 周粒度的情况，让事件的日期以所在周的周日日期作为amChart的显示日期
				chartEvent.date = TimeFormatProcessor.getBelongSunDay(DateUtils
						.parseByFormatRule(eventDO.getEventStartDate(),
								"yyyy-MM-dd"));
			} else if (period == 1004) {
				// 月粒度的情况，让事件的日期以所在月的最后一天作为amChart的显示日期
				chartEvent.date = TimeFormatProcessor
						.getLastDayOfMonth(DateUtils.parseByFormatRule(
								eventDO.getEventStartDate(), "yyyy-MM-dd"));
			} else {
				chartEvent.date = DateUtils.parseByFormatRule(
						eventDO.getEventStartDate(), "yyyy-MM-dd");
			}
			chartEvents.add(chartEvent);
		}
		return chartEvents;
	}

	/**
	 * 
	 * 为金融专题 组装返回视图对象
	 * 
	 * @param lineList
	 * @param pageID
	 * @param chartEvents
	 * @param kpiCode
	 * @return
	 */
	protected ChartData getCharFinanceDate(DwmisKpiInfo kpiInfo,
			List<LineData> lineList, int pageID, String kpiCode) {
		try {
			ChartData chartData = new ChartData();
			chartData.setPageID(pageID);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			// 获得所有线上的日期
			Set<String> dateSet = findAllLineDate(lineList, dateFormat);

			// 获取目标值
			DwmisKpiGoal goalBO = kpiGoalManager
					.getKPIGoalOnCurrentYearByKPICodeWithPatch(kpiCode);

			double goalValue = goalBO.getScore5();

			LineData lineDate = new LineData();
			List<LineElem> lineElem = new ArrayList<LineElem>();
			LineElem elem = null;

			// 目标值为空时，将不填充数据
			if (goalValue != CoreConstant.DEFAULT_DATA_NOT_FOUND) {
				// 填充目标值
				for (String str : dateSet) {
					elem = new LineElem();
					elem.date = dateFormat.parse(str);
					elem.value = goalValue;
					lineElem.add(elem);
				}
			}
			// 获取单位数量级String
			String unitString = "";
			String size = misTypeService
					.getMisTypeByTypeId(kpiInfo.getSizeId()).getTypeName();
			String unit = misTypeService
					.getMisTypeByTypeId(kpiInfo.getUnitId()).getTypeName();

			if (size == null || "个".equals(size) || "".equals(size)) {

			} else {
				unitString = "(" + size + ")";
			}

			if ("%".equals(unit)) {
				unitString = "(%)";
			}

			DwmisKpiInfo kpiInfoDO = new DwmisKpiInfo();
			kpiInfoDO.setKpiNameShow("目标线" + unitString + "");
			kpiInfoDO.setKpiCode(kpiCode + "Goal");
			lineDate.setKpiInfo(kpiInfoDO);
			lineDate.setLineType(LineData.GOAL_LINE);
			lineDate.setDataList(lineElem);
			// 添加一条线
			lineList.add(lineDate);
			chartData.setLineList(lineList);
			return chartData;
		} catch (ParseException e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 根据主指标kpiCode获取该主指标数据、其去年同期数据 及其关联指标数据构成详情视图 步骤（1）（3）（5）
	 * 
	 * @see com.alipay.dwmis.biz.kpidata.KPISingleService#getKPIDataForLinkedKPIPage(java.lang.String,
	 *      int)
	 * 
	 * @param isTable
	 *            true 本接口是为表格准备数据的（无需带单位，无需单位转换） false 为amCharts准备的（带单位，单位转换）
	 * @param domain
	 *            领域，代表是否来自金融专题 或者 是快捷专题
	 * @param havaGoal
	 *            true是有目标线 false没有目标线
	 */
	protected ChartData getKPIDataForLinkedKPIPage(String kpiCode, int period,
			boolean isTable, String linkedPageChartType, String domain,
			boolean havaGoal, String haveEvent) {

		// 是否加关联指标的线
		boolean isRelatedLines = true;

		// 是否是原始数据
		boolean isOriginal = !isTable;

		// 原始数据不关联其它指标
		if ("original".equals(linkedPageChartType)
				|| CoreConstant.DOMAIN_FINANCE.equalsIgnoreCase(domain)) {
			// isOriginal = !isTable;
			isRelatedLines = false;
		}

		DwmisKpiInfo kpiInfo = this.kpiInfoService
				.getDwmisKpiInfoByCode(kpiCode);
		// 关联的指标
		List<DwmisKpiInfo> linkKpiInfoList = this.kpiInfoService
				.listLinkKpiInfoByCode(kpiCode);
		KPICommonQueryParam param = new KPICommonQueryParam();
		param.setDateType(period);

		// 主指标、主指标去年同期、各个关联指标的线元素的集合
		List<LineData> lines = new ArrayList<LineData>();
		// List<MISEventDO> events=new ArrayList<MISEventDO>();
		// 要原始数据时，不用加
		if (isRelatedLines) {
			// 关联指标
			// 根据不同的关联指标KPICODE进行分类并组装点、线元素
			for (DwmisKpiInfo infoDO : linkKpiInfoList) {
				param.setKpiCode(infoDO.getKpiCode());
				param.setCurrentDate(TimeFormatProcessor.getCurrentDate(infoDO
						.getDayOffSet()));
				// 某一关联指标的数据
				List<DwmisKpiData> relatedKPIData = this.myBatisDao
						.getList(
								"com.infosmart.dwmis.DwmisMisKpiDataMapper.QUERY_RELATED_KPIDATA_FOR_DETAIL_VIEW",
								param);
				List<LineElem> elems = this.getDataLineElems(infoDO,
						relatedKPIData, false, isOriginal, isOriginal, domain);
				LineData lineData = this.getLineData(elems,
						LineData.NORMAL_LINE, infoDO);
				lines.add(lineData);

				// 金融界面添加了大事记的隐藏和显示的开关 wb-yingpf 6.20
				if ("true".equals(haveEvent)) {
					// 与关联标关联的大事记列表（两年内的）
					lineData.setChartEvent(this.getChartEvents(
							this.getEventsByKPIOnCondition(param), period));
				}

			}
		}

		// 重新设置主指标的参数
		param.setKpiCode(kpiCode);
		param.setCurrentDate(TimeFormatProcessor.getCurrentDate(kpiInfo
				.getDayOffSet()));

		// 主指标数据集
		List<DwmisKpiData> mainDatas = this.myBatisDao
				.getList(
						"com.infosmart.dwmis.DwmisMisKpiDataMapper.QUERY_RELATED_MAIN_KPIDATA_FOR_DETAIL_VIEW",
						param);
		// need to judge here
		List<LineElem> main_elems = this.getDataLineElems(kpiInfo, mainDatas,
				false, isOriginal, isOriginal, domain);
		LineData main_lineData = this.getLineData(main_elems,
				LineData.MAIN_LINE, kpiInfo);

		// 金融界面添加了大事记的隐藏和显示的开关 wb-yingpf 6.20
		if ("true".equals(haveEvent)) {
			main_lineData.setChartEvent(this.getChartEvents(
					this.getEventsByKPIOnCondition(param), period));
		}
		lines.add(main_lineData);

		LineData last_main_lineData = null;
		// 非金融专题添加去年同期
		if (!CoreConstant.DOMAIN_FINANCE.equalsIgnoreCase(domain)) {
			// 如果时间粒度为周就不再显示去年同期的数据
			if (period != 1003) {
				// 特殊处理(能更好区分当前主线和去年主线)
				DwmisKpiInfo temp = new DwmisKpiInfo();
				BeanUtils.copyProperties(kpiInfo, temp);
				temp.setKpiCode(kpiCode + "_LastYear");
				temp.setKpiNameShow("去年同期");
				temp.setKpiName("去年同期");
				// 主指标去年同期数据集 QUERY_RELATED_MAI_KPIDATA_LAST_PERIOD
				List<DwmisKpiData> last_mianDatas = this.myBatisDao
						.getList(
								"com.infosmart.dwmis.DwmisMisKpiDataMapper.QUERY_RELATED_MAI_KPIDATA_LAST_PERIOD",
								param);
				List<LineElem> last_main_elems = this.getDataLineElems(temp,
						last_mianDatas, true, isOriginal, isOriginal, domain);
				last_main_lineData = this.getLineData(last_main_elems,
						LineData.LAST_YEAR_LINE, temp);
				lines.add(last_main_lineData);
			}
		}

		/*
		 * if("true".equals(haveEvent)){ //与主指标关联的大事记列表（两年内的）
		 * events.addAll(this.getEventsByKPIOnCondition(param)); }
		 * List<ChartEvent> charEvents = this.getChartEvents(events, period);
		 */

		ChartData chartData = null;

		// 3表示详情分析页面,组装返回视图对象
		if (havaGoal) {
			// 金融，有目标线
			chartData = this.getCharFinanceDate(kpiInfo, lines, 3, kpiCode);
		} else {
			// 没有目标线，详情和金融的没有目标线使用
			chartData = this.getCharDate(lines, 3);
		}

		chartData.setMainLine(main_lineData);
		chartData.setLastYearLine(last_main_lineData);
		return chartData;
	}

	/**
	 * 金融: 根据主指标kpiCode获取 新老用户的指标数据
	 * 
	 * 统计方式：都为“当期值” 时间范围：所有粒度都是往前推两年 时间粒度：根据period参数决定，默认为日粒度
	 * 同时取出和主指标关联的大事记列表(往前推两年)
	 * 
	 * @param kpiCode
	 *            (新老指标)
	 * @param period
	 * @param domian
	 *            是否是金融模块
	 * @param haveEvent
	 *            是否要显示大事记
	 * @return
	 */
	protected ChartData getKPIDataForFinanceSonPage(String[] kpiCodes,
			String[] lineTypes, int period, String domian, String haveEvent) {

		boolean isOriginal = true;

		KPICommonQueryParam param = new KPICommonQueryParam();
		param.setDateType(period);

		// 新老用户指标
		List<LineData> lines = new ArrayList<LineData>();
		for (int i = 0; i < kpiCodes.length; i++) {

			DwmisKpiInfo kpiInfo = this.kpiInfoService
					.getDwmisKpiInfoByCode(kpiCodes[i]);
			param.setKpiCode(kpiCodes[i]);
			param.setCurrentDate(TimeFormatProcessor.getCurrentDate(kpiInfo
					.getDayOffSet()));
			// 主指标数据集
			List<DwmisKpiData> datas = this.myBatisDao
					.getList(
							"com.infosmart.dwmis.DwmisMisKpiDataMapper.QUERY_RELATED_MAIN_KPIDATA_FOR_DETAIL_VIEW",
							param);
			// need to judge here
			List<LineElem> elems = this.getDataLineElems(kpiInfo, datas, false,
					isOriginal, isOriginal, domian);
			LineData lineData = this.getLineData(elems,
					Integer.parseInt(lineTypes[i]), kpiInfo);

			// 金融界面添加了大事记的隐藏和显示的开关 wb-yingpf 6.20
			if ("true".equals(haveEvent)) {
				// 与关联标关联的大事记列表（两年内的）
				lineData.setChartEvent(this.getChartEvents(
						this.getEventsByKPIOnCondition(param), period));
			}
			lines.add(lineData);
		}

		ChartData chartData = null;
		chartData = this.getCharDate(lines, 3);
		return chartData;
	}

}
