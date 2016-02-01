package com.infosmart.portal.action.dwmis;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

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
import com.infosmart.portal.pojo.dwmis.MisEventPo;
import com.infosmart.portal.service.dwmis.MisEventService;
import com.infosmart.portal.service.dwmis.MisStockChartService;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.dwmis.CoreConstant;
import com.infosmart.portal.vo.Chart;
import com.infosmart.portal.vo.Graph;
import com.infosmart.portal.vo.dwmis.ChartParam;

/**
 * 生成趋势图
 * 
 * @author infosmart
 * 
 */
@Controller
public class MisStockChartController extends BaseController {

	@Autowired
	private MisStockChartService misStockChartService;

	@Autowired
	private MisEventService misEventService;

	/**
	 * http://localhost:8080/testProj/stockChart/showStockChart?1=1&kpiCodes=
	 * CUS102000AE01M
	 * &lineColors=8aac57&needPercent=0&kpiType=3&reportDate=201110
	 * http://localhost
	 * :8080/testProj/stockChart/showStockChart?1=1&kpiCodes=CRM2106000301D
	 * &lineColors=8aac57&needPercent=0&kpiType=1&reportDate=20111020 生成趋势图
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/misStockChart/showStockChart")
	public void showStockChart(HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.info("准备生成趋势图");
		// 查询日期
		String queryDate = request.getParameter("queryDate");
		if (StringUtils.notNullAndSpace(queryDate)) {
			this.setCrtQueryDateOfDwmis(request, queryDate);
		} else {
			queryDate = this.getCrtQueryDateOfDwmis(request);
		}
		// 是否需要是否归一化
		String needPercent = request.getParameter("needPercent");
		// 是否需要大事件
		String hasEvent = "1";
		// 是否瞭望塔
		String isMIS = request.getParameter("isMIS");
		// 大事件类型
		String eventType = request.getParameter("eventType");
		// 大事件搜索关键字
		String eventSearchKey = request.getParameter("eventSearchKey");
		// 是否查询去年同期值
		String lastYearValue = request.getParameter("lastYearValue");
		// 是否详细分析指标图
		String yesOrNoDetailsAnalysis = request
				.getParameter("yesOrNoDetailsAnalysis");
		// 是否计算指标 前期值 和 环比
		String isProphaseValue = request.getParameter("isProphaseValue");
		// 指标类型
		String kpiType = request.getParameter("kpiType");
		String staCode = request.getParameter("staCode");
		// KPICODE
		String kpiCodes = request.getParameter("kpiCodes");// 以";"分隔
		if (!StringUtils.notNullAndSpace(kpiCodes)) {
			this.logger.warn("生成趋势图失败，指标编码为空");
			return;
		}
		if (!StringUtils.notNullAndSpace(staCode)) {
			staCode = DwmisKpiData.DEFAULT_STATISTICS;
		}

		if (!StringUtils.notNullAndSpace(kpiType)) {
			kpiType = String.valueOf(DwmisKpiInfo.KPI_TYPE_OF_DAY);
		}

		try {
			Integer.parseInt(kpiType);
		} catch (Exception e) {
			kpiType = String.valueOf(CoreConstant.DAY_PERIOD);
		}

		// 查询的KPI指标

		this.logger.info("NEW------------------>kpiCodes:" + kpiCodes);
		this.logger.info("显示指标的趋势图:" + kpiCodes);
		ChartParam chartParam = new ChartParam(needPercent, hasEvent, isMIS,
				eventType, eventSearchKey, Integer.parseInt(staCode), null,
				null, kpiCodes.split(";"),
				(String[]) Constants.CHART_COLOR_LIST.toArray(), "0",
				isProphaseValue, yesOrNoDetailsAnalysis);
		// 指标类型
		chartParam.setKpiType(Integer.parseInt(kpiType));
		chartParam.setEndDate(queryDate);
		// 得到图数据
		Chart chart = this.misStockChartService.getStockChart(chartParam);
		if (chart == null)
			chart = new Chart();
		// 得到去年同期图数据
		if ("1".equals(lastYearValue) && chart != null) {
			Date endDateOfPreviousYear = DateUtils.getPreviousYear(DateUtils
					.parseByFormatRule(chartParam.getEndDate(), "yyyy-MM-dd"));
			Date beginDateOfPreviousYear = DateUtils
					.getPreviousYear(DateUtils.parseByFormatRule(
							chartParam.getBeginDate(), "yyyy-MM-dd"));
			// 结束时间改为去年同期
			chartParam.setEndDate(DateUtils.formatByFormatRule(
					endDateOfPreviousYear, "yyyyMMdd"));
			chartParam.setLastYearValue(lastYearValue);
			chartParam.setIsProphaseValue("no");
			// 开始时间改为去年同期
			chartParam.setBeginDate(DateUtils.formatByFormatRule(
					beginDateOfPreviousYear, "yyyyMMdd"));
			// 查询第一个KpiCode的去年同期数据，KpiCode为主
			String[] newKpiCodes = chartParam.getKpiCodes();
			String[] hisKpiCode = new String[1];
			hisKpiCode[0] = newKpiCodes[0];
			chartParam.setKpiCodes(hisKpiCode);
			chartParam.setHasEvent("0");
			Chart lastYearChart = this.misStockChartService
					.getStockChart(chartParam);
			if (lastYearChart != null) {
				TreeMap<String, String> crtYearDataMap = chart.getAreaDataMap();

				Map.Entry entry = null;
				for (Iterator it = crtYearDataMap.entrySet().iterator(); it
						.hasNext();) {
					entry = (Map.Entry) it.next();
					if (lastYearChart.getAreaDataMap() == null)
						continue;
					String lastYearData = null;
					if (entry.getKey().toString().length() == 6) {
						lastYearData = lastYearChart.getAreaDataMap().get(
								DateUtils.formatByFormatRule(DateUtils
										.getPreviousYear(DateUtils
												.parseByFormatRule(entry
														.getKey().toString(),
														"yyyy-MM")), "yyyyMM")

						);
					} else {
						lastYearData = lastYearChart.getAreaDataMap().get(
								DateUtils.formatByFormatRule(DateUtils
										.getPreviousYear(DateUtils
												.parseByFormatRule(entry
														.getKey().toString(),
														"yyyy-MM-dd")),
										"yyyyMMdd")

						);
					}

					if (!StringUtils.notNullAndSpace(lastYearData)) {
						crtYearDataMap.put(entry.getKey().toString(), entry
								.getValue().toString()
								+ ","
								+ "0.0"
								+ ","
								+ "0.0");
					} else {
						crtYearDataMap.put(entry.getKey().toString(), entry
								.getValue().toString() + "," + lastYearData);

					}
				}

				List<Graph> crtYearGraphList = chart.getGraphList();
				if (lastYearChart.getGraphList() != null
						&& lastYearChart.getGraphList().size() > 0) {
					// 颜色
					lastYearChart.getGraphList().get(0)
							.setGraphColor(chartParam.getColors()[7]);
					// 显示KPI名字
					lastYearChart
							.getGraphList()
							.get(0)
							.setGraphName(
									"(去年同期)"
											+ lastYearChart.getGraphList()
													.get(0).getGraphName());
					// 区别KPI_CODE
					lastYearChart
							.getGraphList()
							.get(0)
							.setGraphId(
									lastYearChart.getGraphList().get(0)
											.getGraphId()
											+ "_QNTQ");
					crtYearGraphList.add(lastYearChart.getGraphList().get(0));
				}
			}
		}

		// 查询指标信息
		DwmisKpiInfo kpiInfo = this.dwmisKpiInfoService
				.getDwmisKpiInfoByCode(kpiCodes.split(";")[0]);
		try {
			String rootPath = request.getSession().getServletContext()
					.getRealPath("/");
			// 初始化并取得Velocity引擎
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, rootPath
					+ Constants.VM_FILE_PATH + "/dwmis/");
			ve.init(properties);

			// 取得velocity的模版
			Template template;
			if ("yes".equals(yesOrNoDetailsAnalysis)) {
				template = ve.getTemplate("DetailsAnalysisStockChart.vm",
						"UTF-8");
			} else {
				template = ve.getTemplate("stockChart.vm", "UTF-8");
			}
			VelocityContext context = new VelocityContext();

			// 把数据填入上下文
			context.put("chart", chart);
			context.put("type", kpiInfo == null ? kpiType : kpiType);
			context.put("needPercent", needPercent);
			context.put("hasEvent", hasEvent);

			// 输出流
			StringWriter writer = new StringWriter();

			// 转换输出
			template.merge(context, writer);
			response.setContentType("text/xml;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(writer.toString());
			out.flush();
			this.logger.info("生成趋势图结束...");
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 堆积图（面积图）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/misStockChart/showStockAreaChart")
	public void showStockAreaChart(HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.info("准备生成堆积图");
		// 查询日期
		String queryDate = request.getParameter("queryDate");
		if (StringUtils.notNullAndSpace(queryDate)) {
			this.setCrtQueryDateOfDwmis(request, queryDate);
		} else {
			queryDate = this.getCrtQueryDateOfDwmis(request);
		}
		// 是否需要是否归一化
		String needPercent = request.getParameter("needPercent");
		// 是否需要大事件
		String hasEvent = "1";
		// 是否瞭望塔
		String isMIS = request.getParameter("isMIS");
		// 大事件类型
		String eventType = request.getParameter("eventType");
		// 大事件搜索关键字
		String eventSearchKey = request.getParameter("eventSearchKey");
		// 是否查询去年同期值
		String lastYearValue = request.getParameter("lastYearValue");
		// 指标类型
		String kpiType = request.getParameter("kpiType");
		// 是否详细分析指标图
		String yesOrNoDetailsAnalysis = request
				.getParameter("yesOrNoDetailsAnalysis");
		String staCode = request.getParameter("staCode");
		if (!StringUtils.notNullAndSpace(staCode)) {
			staCode = DwmisKpiData.DEFAULT_STATISTICS;
		}

		if (!StringUtils.notNullAndSpace(kpiType)) {
			kpiType = String.valueOf(DwmisKpiInfo.KPI_TYPE_OF_DAY);
		}
		// 查询的KPI指标
		String kpiCodes = request.getParameter("kpiCodes");// 以";"分隔
		if (!StringUtils.notNullAndSpace(kpiCodes)) {
			this.logger.warn("显示面积图失败，没有指定KPI CODE");
			return;
		}
		this.logger.info("NEW------------------>kpiCodes:" + kpiCodes);
		this.logger.info("显示指标的堆积图:" + kpiCodes);
		ChartParam chartParam = new ChartParam(needPercent, hasEvent, isMIS,
				eventType, eventSearchKey, Integer.parseInt(staCode), null,
				null, kpiCodes.split(";"),
				(String[]) Constants.CHART_COLOR_LIST.toArray(), lastYearValue,
				"no", yesOrNoDetailsAnalysis);
		// 指标类型
		chartParam.setKpiType(Integer.parseInt(kpiType));
		chartParam.setEndDate(queryDate);
		// 得到图数据
		Chart chart = this.misStockChartService.getStockChart(chartParam);
		chart = chart == null ? new Chart() : chart;
		// 查询指标信息
		DwmisKpiInfo kpiInfo = this.dwmisKpiInfoService
				.getDwmisKpiInfoByCode(kpiCodes.split(";")[0]);
		try {
			String rootPath = request.getSession().getServletContext()
					.getRealPath("/");
			// 初始化并取得Velocity引擎
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			// properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH,cfgMap.get("templatePath")+"/prodana/screen/amchartDataVM");
			properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, rootPath
					+ Constants.VM_FILE_PATH + "/dwmis/");
			ve.init(properties);

			// 取得velocity的模版
			// ve.setProperty(Velocity.RESOURCE_LOADER, "file");
			// ve.setProperty("file.resource.loader.class",
			// "org.apache.velocity.runtime.resource.loader.FileResourceLoader");

			Template template = ve.getTemplate("stockAreaChart.vm", "UTF-8");
			// 取得velocity的上下文context
			VelocityContext context = new VelocityContext();

			// 把数据填入上下文
			context.put("chart", chart);
			context.put("type", kpiInfo == null ? kpiType : kpiType);
			context.put("needPercent", needPercent);
			context.put("hasEvent", hasEvent);

			// 输出流
			StringWriter writer = new StringWriter();

			// 转换输出
			template.merge(context, writer);
			response.setContentType("text/xml;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(writer.toString());
			out.flush();
			this.logger.info("生成堆积图结束...");
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 趋势图中的大事件信息
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/misStockChart/misBigEvent")
	public ModelAndView getMisBigEvent(HttpServletRequest req,
			HttpServletResponse res) {
		String eventId = req.getParameter("eventId");
		MisEventPo misEvent = new MisEventPo();
		if (!StringUtils.notNullAndSpace(eventId)) {
			this.logger.warn("获取大事件详细数据出错：eventId为空");
			misEvent.setContent("获取大事件详细数据出错：eventId为空");
		} else {
			misEvent = misEventService.getEventMessageById(eventId);
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("misEvent", misEvent);
		mv.setViewName("dwmis/misEventMassage/misEventMassage");
		return mv;
	}
}
