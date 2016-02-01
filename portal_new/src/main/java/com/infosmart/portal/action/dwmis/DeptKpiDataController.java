package com.infosmart.portal.action.dwmis;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.portal.action.BaseController;
import com.infosmart.portal.pojo.dwmis.DwmisKpiData;
import com.infosmart.portal.pojo.dwmis.DwmisKpiInfo;
import com.infosmart.portal.pojo.dwmis.DwmisMisDptmnt;
import com.infosmart.portal.service.dwmis.AmchartDataFacade;
import com.infosmart.portal.service.dwmis.DwmisDeptManService;
import com.infosmart.portal.service.dwmis.DwmisKpiChartService;
import com.infosmart.portal.service.dwmis.DwmisKpiDataService;
import com.infosmart.portal.service.dwmis.DwmisKpiInfoService;
import com.infosmart.portal.service.dwmis.SysDateForFixedYear;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.NumberFormatter;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.dwmis.CoreConstant;
import com.infosmart.portal.util.dwmis.TimeFormatProcessor;
import com.infosmart.portal.vo.Chart;
import com.infosmart.portal.vo.dwmis.DwmisDeptKpiData;

/**
 * 部门指标数据
 * 
 * @author infosmart
 * 
 */
@Controller
public class DeptKpiDataController extends BaseController {

	// 部门管理
	@Autowired
	private DwmisDeptManService deptManService;

	// KPI数据
	@Autowired
	private DwmisKpiDataService kpiDataService;

	// kpi内容
	@Autowired
	private DwmisKpiInfoService kpiInfoService;

	// 指标走势
	@Autowired
	private DwmisKpiChartService kpiChartManager;

	// 详情分析
	@Autowired
	private AmchartDataFacade amchartDataFacade;

//	@Autowired
//	private SysDateForFixedYear sysDateForFixedYear;

	/**
	 * 显示部门指标监控
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/dwmisDeptKpiData/showDeptKpiData")
	public ModelAndView showDeptKpiData(HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.info("转到部门指标监控");
		// 14001 部门分组
		// 列出所有部门
		String depGroupId = request.getParameter("depGroupId");
		if (!StringUtils.notNullAndSpace(depGroupId)) {
			depGroupId = String.valueOf(CoreConstant.DEPT_TYPE_DEPT);
		}

		// 当年业务累计量
		String AccumulatedVolumeOfBusinessByYear = request.getSession()
				.getServletContext()
				.getInitParameter("AccumulatedVolumeOfBusinessByYear");
		// 当年累计业务笔数
		String TotalNumberOfBusinessPenByYear = request.getSession()
				.getServletContext()
				.getInitParameter("TotalNumberOfBusinessPenByYear");
		// 上月活跃账户数
		String PreviousMonthActiveAccountNumber = request.getSession()
				.getServletContext()
				.getInitParameter("PreviousMonthActiveAccountNumber");
		// 动态一年活跃商户数
		String ActiveMerchantsNumberByYear = request.getSession()
				.getServletContext()
				.getInitParameter("ActiveMerchantsNumberByYear");
//		DwmisKpiInfo kpiInfo = kpiInfoService
//				.getDwmisKpiInfoByCode(AccumulatedVolumeOfBusinessByYear);
		// 根据日期粒度 获取查询时间
//		Date mySysDate = this.sysDateForFixedYear
//				.getSysDateForFixedYear(kpiInfo.getDayOffSet());
//		this.logger.info("根据日期粒度 获取查询时间---------->"
//				+ DateUtils.fotmatDate4(mySysDate));
		// String reportDate = DateUtils.fotmatDate4(sysDateForFixedYear
		// .getReportDateForFixedYear());

		String queryDate = request.getParameter("queryDate");
		if (StringUtils.notNullAndSpace(queryDate)) {
			this.setCrtQueryDateOfDwmis(request, queryDate);
		} else {
			queryDate = this.getCrtQueryDateOfDwmis(request);
		}

		// // 上一年最后一天
		// String dataDate = DateUtils.formatByFormatRule(mySysDate,
		// "yyyyMMdd");
		// 根据所获取时间 判断是否为上一年快照
//		int hasBeenDate = Integer.parseInt((String) DateUtils
//				.formatByFormatRule(mySysDate, "yyyyMMdd"));
		// if (hasBeenDate <= Integer.parseInt((String) DateUtils
		// .formatByFormatRule(new Date(), "yyyy") + "01" + "01")) {
		// 跨年
		// queryDate = DateUtils.lastYearAtTheEndOfTheDay(mySysDate);
		// request.setAttribute("estimateDate", "1");
//		request.setAttribute("headDate", Integer.parseInt((String) DateUtils
//				.formatByFormatRule(mySysDate, "yyyy")) - 1);
		// } else {
		// 不跨年
		request.setAttribute("estimateDate", "0");
		// }

		// // 从session中获得查询数据时间
		// String dataDate = this.getCrtQueryDateOfReport(request);
		// if (!StringUtils.notNullAndSpace(dataDate)) {
		// dataDate = DateUtils.fotmatDate2(DateUtils
		// .getTheNextDay(new Date()));
		// }
		List<DwmisMisDptmnt> deptInfoList = this.deptManService
				.listAllDept(Integer.parseInt(depGroupId));
		// 从上下文中获得指标监控默认 显示4个累计指标

		// 当年业务累计量 AccumulatedVolumeOfBusinessByYearMap
		Double AccumulatedVolumeOfBusinessByYearMap = this.kpiDataService
				.getKpiDataValueByYear(AccumulatedVolumeOfBusinessByYear,
						CoreConstant.MONTH_PERIOD, CoreConstant.STA_CURRENT,
						DateUtils.parseByFormatRule(queryDate, "yyyy-MM-dd"));
		// 单位是元 数据是十万元级别 故作处理
		if (AccumulatedVolumeOfBusinessByYearMap != null) {
			BigDecimal b1 = new BigDecimal(
					Double.toString(AccumulatedVolumeOfBusinessByYearMap));
			BigDecimal b2 = new BigDecimal(Double.toString(100000.0));
			AccumulatedVolumeOfBusinessByYearMap = b1.multiply(b2)
					.doubleValue();
		}

		// 当年累计业务笔数
		Double TotalNumberOfBusinessPenByYearMap = this.kpiDataService
				.getKpiDataValueByYear(TotalNumberOfBusinessPenByYear,
						CoreConstant.MONTH_PERIOD, CoreConstant.STA_CURRENT,
						DateUtils.parseByFormatRule(queryDate, "yyyy-MM-dd"));
		// 上月活跃账户数
		Double PreviousMonthActiveAccountNumberMap = this.kpiDataService
				.getKpiDataValueByMonth(PreviousMonthActiveAccountNumber,
						CoreConstant.DAY_PERIOD, CoreConstant.STA_FINAL,
						DateUtils.getPreviousMonth(DateUtils.parseByFormatRule(
								queryDate, "yyyy-MM-dd")));
		// 动态一年活跃商户数
		Double ActiveMerchantsNumberByYearMap = this.kpiDataService
				.getKpiDataValueByYear(ActiveMerchantsNumberByYear,
						CoreConstant.MONTH_PERIOD, CoreConstant.STA_FINAL,
						DateUtils.parseByFormatRule(queryDate, "yyyy-MM-dd"));
		request.setAttribute("deptInfoList", deptInfoList);
		request.setAttribute(
				"AccumulatedVolumeOfBusinessByYearMap",
				NumberFormatter.format(
						(AccumulatedVolumeOfBusinessByYearMap).toString(), 0));

		request.setAttribute(
				"TotalNumberOfBusinessPenByYearMap",
				NumberFormatter.format(
						TotalNumberOfBusinessPenByYearMap.toString(), 0));

		request.setAttribute(
				"PreviousMonthActiveAccountNumberMap",
				NumberFormatter.format(
						PreviousMonthActiveAccountNumberMap.toString(), 0));

		request.setAttribute(
				"ActiveMerchantsNumberByYearMap",
				NumberFormatter.format(
						ActiveMerchantsNumberByYearMap.toString(), 0));

		request.setAttribute("queryDate", queryDate);

		/*
		 * // 列出部门的指标数据 List<DwmisDeptKpiData> deptKpiDataList = kpiDataService
		 * .listDeptMonitorKpiData(deptInfoList);
		 * request.setAttribute("deptKpiDataList", deptKpiDataList);
		 */
		return new ModelAndView("/dwmis/KpiDataMonitoring/DeptKpiData");
	}

	/**
	 * 加载部门指标监控
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/dwmisDeptKpiData/loadDeptKpiData")
	public ModelAndView loadDeptKpiData(HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.info("加载部门指标监控");
		String deptId = request.getParameter("deptId");
		String queryDate = request.getParameter("queryDate");
		if (StringUtils.notNullAndSpace(queryDate)) {
			this.setCrtQueryDateOfDwmis(request, queryDate);
		} else {
			queryDate = this.getCrtQueryDateOfDwmis(request);
		}
		DwmisMisDptmnt deptInfo = this.deptManService
				.getDwmisMisDptmntById(deptId);
		if (deptInfo == null) {
			deptInfo = new DwmisMisDptmnt();
			deptInfo.setDepId(deptId);
		}
		List<DwmisMisDptmnt> deptInfoList = new ArrayList<DwmisMisDptmnt>();
		deptInfoList.add(deptInfo);
		// Long a = System.currentTimeMillis();
		// Long b = System.currentTimeMillis();
		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+(b-a)+"毫秒");
		// 列出部门的指标数据
		List<DwmisDeptKpiData> deptKpiDataList = kpiDataService
				.listDeptMonitorKpiData(deptInfoList, queryDate);
		request.setAttribute("queryDate", queryDate);
		request.setAttribute("deptKpiDataList", deptKpiDataList);
		return new ModelAndView("/dwmis/KpiDataMonitoring/ListDeptKpi");
	}

	/**
	 * 加载详情分析页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/dwmisDeptKpiData/detailsAnalysis")
	public ModelAndView detailsAnalysis(HttpServletRequest request,
			DwmisKpiData dwmisKpiData) {

		this.logger.info("加载详细分析");

		// String kpiCodes = request.getParameter("kpiCodes");
		// if(!StringUtils.notNullAndSpace(kpiCodes)){
		// kpiCodes = kpiCode;
		// }
		// 接收查询时间粒度 kpiType
		String flag = "";
		String kpiType = request.getParameter("kpiType");
		if (!StringUtils.notNullAndSpace(kpiType)) {
			kpiType = String.valueOf(DwmisKpiInfo.KPI_TYPE_OF_DAY);
		}
		String staCode = request.getParameter("staCode");
		if (!StringUtils.notNullAndSpace(staCode)) {
			staCode = DwmisKpiData.DEFAULT_STATISTICS;
		}
		String kpiCode = request.getParameter("kpiCode");
		String kpiCodes = request.getParameter("kpiCodes");
		String[] arrayKpiCodes = null;
		String queryDate = request.getParameter("queryDate");
		if (StringUtils.notNullAndSpace(queryDate)) {
			this.setCrtQueryDateOfDwmis(request, queryDate);
		} else {
			queryDate = this.getCrtQueryDateOfDwmis(request);
		}
		if (!StringUtils.notNullAndSpace(kpiCodes)
				&& StringUtils.notNullAndSpace(kpiCode)) {
			this.logger.info("查询单个指标的详情分析");
			arrayKpiCodes = kpiCode.split(";");
			DwmisKpiInfo kpiInfo = kpiInfoService
					.getDwmisKpiInfoByCode(kpiCode);
			/**
			 * 30012 月峰值（计去年） 30021 月谷值（pt） 30052 日累积值（计去年） 30051 日累积值（pt） 3008
			 * 月当期 3009 月期末 3001 月峰值 3002 月谷值 3003 日峰值 3004 日谷值 3005 日累积值 3006
			 * 月累积值 3007 七日均值峰值 30011 月峰值（pt）
			 */
			if (kpiInfo.getGoalType() == 30012
					|| kpiInfo.getGoalType() == 30021
					|| kpiInfo.getGoalType() == 3008
					|| kpiInfo.getGoalType() == 3009
					|| kpiInfo.getGoalType() == 3001
					|| kpiInfo.getGoalType() == 3002
					|| kpiInfo.getGoalType() == 3006
					|| kpiInfo.getGoalType() == 30011) {
				kpiType = String.valueOf(DwmisKpiInfo.KPI_TYPE_OF_MONTH);
				flag = String.valueOf(DwmisKpiInfo.KPI_TYPE_OF_MONTH);
			}
			// 根据日期粒度 获取查询时间
			// Date mySysDate = this.sysDateForFixedYear
			// .getSysDateForFixedYear(kpiInfo.getDayOffSet());
			// if (StringUtils.notNullAndSpace(queryDate)) {
			// mySysDate = DateUtils
			// .parseByFormatRule(queryDate, "yyyy-MM-dd");
			// }
			String endDate = queryDate;
			// if (StringUtils.notNullAndSpace(queryDate)
			// && StringUtils.notNullAndSpace(DateUtils
			// .formatByFormatRule(new Date(), "yyyyMM"))) {
			// // if (Integer.parseInt(DateUtils.formatByFormatRule(new Date(),
			// // "yyyyMM")) > Integer.parseInt(queryDate)) {
			// // // 调整结束时间为上一年的最后一天
			// // endDate = DateUtils.lastYearAtTheEndOfTheDay(mySysDate);
			// // } else {
			// endDate = queryDate;
			// // }
			//
			// } else {
			// endDate = DateUtils
			// .formatByFormatRule(new Date(), "yyyy-MM-dd");
			// }
			String beginDate = DateUtils.formatByFormatRule(DateUtils
					.getPreviousDateCount(
							DateUtils.parseByFormatRule(endDate, "yyyy-MM-dd"),
							30), "yyyyMMdd");
			List<DwmisKpiData> dwmisKpiDataList = kpiDataService
					.getKpiDataByParams(dwmisKpiData,
							Arrays.asList(arrayKpiCodes),
							Integer.parseInt(kpiType),
							Integer.parseInt(staCode), beginDate, endDate);

			// 查询子指标数据
			List<DwmisKpiInfo> listLinkKpiInfoMassageList = kpiInfoService
					.listLinkKpiInfoByCode(arrayKpiCodes[0]);
			if (listLinkKpiInfoMassageList.isEmpty()) {
				request.setAttribute("yesOrNoLinkKpiInfoMassage", 0);
			} else {
				request.setAttribute("yesOrNoLinkKpiInfoMassage", 1);
			}
			request.setAttribute("listLinkKpiInfoMassageList",
					listLinkKpiInfoMassageList);
			request.setAttribute("dwmisKpiDataList", dwmisKpiDataList);
			request.setAttribute("quaryDate", endDate);
			request.setAttribute("dataDate", DateUtils.fotmatDate2(DateUtils
					.parseByFormatRule(endDate, "yyyy-MM-dd")));
		} else {
			this.logger.info("查询指标及子指标的详情分析");
			List<DwmisKpiInfo> dwmisKpiInfoList = new ArrayList<DwmisKpiInfo>();
			// 根据arrayKpiCodes查询指标数据 用于前台动态table循环
			Map<String, List<DwmisKpiData>> kpiDataByCodesMap = new LinkedHashMap<String, List<DwmisKpiData>>();
			arrayKpiCodes = kpiCodes.split(";");
			for (int i = 0; i < kpiCodes.split(";").length; i++) {
				DwmisKpiInfo kpiInfo = kpiInfoService
						.getDwmisKpiInfoByCode(kpiCodes.split(";")[i]
								.toString());
				// add by yangwg
				if (kpiInfo == null)
					continue;
				dwmisKpiInfoList.add(kpiInfo);
//				Date mySysDate = this.sysDateForFixedYear
//						.getSysDateForFixedYear(kpiInfo.getDayOffSet());
//				if (StringUtils.notNullAndSpace(queryDate)) {
//					mySysDate = DateUtils.parseByFormatRule(queryDate,
//							"yyyy-MM-dd");
//				}

				String endDate = queryDate;
				// if (StringUtils.notNullAndSpace(DateUtils.formatByFormatRule(
				// mySysDate, "yyyyMM"))
				// && StringUtils.notNullAndSpace(DateUtils
				// .formatByFormatRule(new Date(), "yyyyMM"))) {
				// if (Integer.parseInt(DateUtils.formatByFormatRule(
				// new Date(), "yyyyMM")) > Integer.parseInt(DateUtils
				// .formatByFormatRule(mySysDate, "yyyyMM"))) {
				// // 调整结束时间为上一年的最后一天
				// endDate = DateUtils.lastYearAtTheEndOfTheDay(mySysDate);
				// } else {
				// endDate = DateUtils.formatByFormatRule(mySysDate,
				// "yyyy-MM-dd");
				// }
				//
				// } else {
				// endDate = DateUtils.formatByFormatRule(new Date(),
				// "yyyy-MM-dd");
				// }
				String beginDate = DateUtils.formatByFormatRule(DateUtils
						.getPreviousDateCount(DateUtils.parseByFormatRule(
								endDate, "yyyy-MM-dd"), 30), "yyyyMMdd");
				List<DwmisKpiData> kpiDataByCode = kpiDataService
						.kpiDataListByParam(kpiCodes.split(";")[i].toString(),
								Integer.parseInt(kpiType), Integer
										.parseInt(staCode), DateUtils
										.parseByFormatRule(beginDate,
												"yyyyMMdd"), DateUtils
										.parseByFormatRule(endDate,
												"yyyy-MM-dd"));
				// modify by yangwg
				// modify by mifu 20120713
				if (kpiDataByCode != null) {
					for (int j = kpiDataByCode.size() - 1; j >= 0; j--) {

						if (kpiDataByCode.get(j) == null)
							continue;
						// this.logger.info("------------>kpi_date:"+kd.getShowReportDate());
						if (kpiDataByCodesMap.containsKey(kpiDataByCode.get(j)
								.getShowReportDate())) {
							kpiDataByCodesMap.get(
									kpiDataByCode.get(j).getShowReportDate())
									.add(kpiDataByCode.get(j));
						} else {
							List<DwmisKpiData> kdList = new ArrayList<DwmisKpiData>();
							kdList.add(kpiDataByCode.get(j));
							kpiDataByCodesMap.put(kpiDataByCode.get(j)
									.getShowReportDate(), kdList);
						}
					}
				}
				request.setAttribute("quaryDate", endDate);
				request.setAttribute("dataDate", DateUtils
						.fotmatDate2(DateUtils.parseByFormatRule(endDate,
								"yyyy-MM-dd")));

			}
			// 查询子指标数据
			List<DwmisKpiInfo> listLinkKpiInfoMassageList = kpiInfoService
					.listLinkKpiInfoByCode(arrayKpiCodes[0]);
			if (listLinkKpiInfoMassageList.isEmpty()) {
				request.setAttribute("yesOrNoLinkKpiInfoMassage", 0);
			} else {
				request.setAttribute("yesOrNoLinkKpiInfoMassage", 1);
			}
			request.setAttribute("listLinkKpiInfoMassageList",
					listLinkKpiInfoMassageList);

			request.setAttribute("windowLocalKpiCodes", kpiCodes);
			// 区分关联指标趋势图还是默认趋势图
			request.setAttribute("kpiChartType", "1");
			request.setAttribute("kpiDataByCodesMap", kpiDataByCodesMap);

			request.setAttribute("dwmisKpiInfoList", dwmisKpiInfoList);
		}
		// 传回页面刷新趋势图查询
		request.setAttribute("kpiType", kpiType);
		request.setAttribute("flag", flag);
		// 根据主指标kpiCode查询kpiInfo 信息
		DwmisKpiInfo dwmisKpiInfo = kpiInfoService
				.getDwmisKpiInfoByCode(arrayKpiCodes[0].toString());
		request.setAttribute("dwmisKpiInfo", dwmisKpiInfo);

		return new ModelAndView("/dwmis/KpiDataMonitoring/DetailsAnalysis");
	}

	/**
	 * 部门指标监控之指标走势
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/dwmisDeptKpiData/generateTrendChartData")
	public void generateTrendChartData(HttpServletRequest request,
			HttpServletResponse response) {
		String kpiCode = request.getParameter("kpiCode");
		this.logger.info("生成部门指标走势图:" + kpiCode);
		this.clearCache(response);
		try {
			// 生成趋势图数据
			Chart chart = this.kpiChartManager.getTendChartData(kpiCode);
			String rootPath = request.getSession().getServletContext()
					.getRealPath("/");
			// 初始化并取得Velocity引擎
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, rootPath
					+ Constants.VM_FILE_PATH + "/dwmis/");
			ve.init(properties);

			Template template = ve.getTemplate("tendChartData.vm", "UTF-8");
			// 取得velocity的上下文context
			VelocityContext context = new VelocityContext();
			context.put("chartData", chart);
			context.put("pageId", "kpiTrends");
			String period = request.getParameter("period");
			context.put("chart", StringUtils.notNullAndSpace(period) ? period
					: "1002");
			context.put("NaN", CoreConstant.DEFAULT_DATA_NOT_FOUND);
			// 输出流
			StringWriter writer = new StringWriter();

			// 转换输出
			template.merge(context, writer);
			response.setContentType("text/xml;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(writer.toString());
			out.flush();
			this.logger.info("生成部门指标走势图结束...");
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 趋势图设置XML
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/dwmisDeptKpiData/generateTrendChartSeting")
	public void generateTrendChartSeting(HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.info("生成趋势图设置文件");
		this.clearCache(response);
		try {
			// 单位
			String kpiUnit = request.getParameter("kpiUnit");
			String rootPath = request.getSession().getServletContext()
					.getRealPath("/");
			// 初始化并取得Velocity引擎
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, rootPath
					+ Constants.VM_FILE_PATH + "/dwmis/");
			ve.init(properties);

			Template template = ve.getTemplate("tendChartSetting.vm", "UTF-8");
			// 取得velocity的上下文context
			VelocityContext context = new VelocityContext();
			if ("perSign".equals(kpiUnit)) {
				context.put("kpiUnit", "%");
			} else {
				context.put("kpiUnit", "");
			}
			// 输出流
			StringWriter writer = new StringWriter();

			// 转换输出
			template.merge(context, writer);
			response.setContentType("text/xml;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(writer.toString());
			out.flush();
			this.logger.info("生成趋势图设置文件结束...");
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 生成指标详情分析趋势图
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/dwmisDeptKpiData/generateKpiDetailThreadChart")
	public void generateKpiDetailThreadChart(HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.info("生成指标详情分析趋势图");
		this.clearCache(response);
		String kpicode = request.getParameter("kpiCode");
		String period = request.getParameter("period");
		String haveEvent = request.getParameter("haveEvent");
		if (haveEvent == null || "".equals(haveEvent)) {
			haveEvent = "true";
		}
		// original：原始值（默认值） percent： 归一化后的值
		String linkedPageChartType = request
				.getParameter("linkedpagecharttype");
		// 归一化的标识
		String needPercent = request.getParameter("needPercent");

		if (linkedPageChartType == null || "".equals(linkedPageChartType)) {
			linkedPageChartType = "original";
		}

		// hidden从界面带给来要隐藏的指标
		String hidden = request.getParameter("hidden");
		if (hidden == null) {
			hidden = "";
		}
		try {
			// 生成详情分析趋势图数据
			Chart chart = amchartDataFacade.getKpiDetailData(kpicode, period,
					linkedPageChartType, needPercent, hidden,
					CoreConstant.DOMAIN_QUICKFINANCE, false, haveEvent, "");
			String rootPath = request.getSession().getServletContext()
					.getRealPath("/");
			// 初始化并取得Velocity引擎
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, rootPath
					+ Constants.VM_FILE_PATH + "/dwmis/");
			ve.init(properties);

			Template template = ve.getTemplate("stockKPIDetailChart.vm",
					"UTF-8");
			// 取得velocity的上下文context
			VelocityContext context = new VelocityContext();
			// 对技术部的特殊处理，数据精度标识
			if (kpicode.startsWith("TEC") || kpicode.startsWith("tec")) {
				context.put("isTecKPI", 2);
			} else {
				context.put("isTecKPI", 1);
			}
			// 计算页面展现的最大值
			double maxNumber = chart.getChartValue().get("max");
			double minNumber = chart.getChartValue().get("min");

			double maxNumberForChart = (maxNumber - minNumber) * 0.1
					+ maxNumber;
			chart.setMaxNumber(maxNumberForChart);
			if (String.valueOf(Boolean.TRUE).equals(needPercent)) {
				context.put("needPercent", needPercent);
			}
			// original：原始值（默认值） percent： 归一化后的值
			context.put("linkedpagecharttype", linkedPageChartType);
			context.put("chartData", chart);
			context.put("period", period);
			// 输出流
			StringWriter writer = new StringWriter();

			// 转换输出
			template.merge(context, writer);
			response.setContentType("text/xml;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(writer.toString());
			out.flush();
			this.logger.info("生成指标详情分析趋势图结束...");
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}

	}
}
