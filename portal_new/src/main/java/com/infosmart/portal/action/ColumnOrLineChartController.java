package com.infosmart.portal.action;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.service.ColumnChartService;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.vo.Chart;
import com.infosmart.portal.vo.ChartParam;
import com.infosmart.portal.vo.Graph;

/**
 * 矩形图或折线图
 * 
 * @author infosmart
 * 
 */
@Controller
public class ColumnOrLineChartController extends BaseController {
	private final String chart_type_column = "column";
	private final String chart_type_line = "line";

	@Autowired
	private ColumnChartService columnChartService;

	/**
	 * 得到矩形图或折线图的数据XML
	 */
	@RequestMapping("/columnOrLineChart/showChartData")
	public void getColumnOrLineChartData(HttpServletRequest request,
			HttpServletResponse response) {
		// 查询的KPI指标
		String kpiCodes = request.getParameter("kpiCodes");// 以";"分隔
		if (!StringUtils.notNullAndSpace(kpiCodes)) {
			this.logger.warn("得到矩形图或折线图的设置XML失败：指标为空");
			return;
		}
		this.logger.info("准备生成矩形或折线图:" + kpiCodes);
		// 指标类型
		String kpiType = request.getParameter("kpiType");
		if (!StringUtils.notNullAndSpace(kpiType)) {
			kpiType = String.valueOf(DwpasCKpiInfo.KPI_TYPE_OF_DAY);
		}
		String beginDate = null;
		// 查询日期
		String endDate = request.getParameter("reportDate");
		if (!StringUtils.notNullAndSpace(endDate)) {
			// 如果日期为空,则默认为当天或当月
			if (Integer.parseInt(kpiType) == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
				endDate = DateUtils.formatUtilDate(new Date(), "yyyyMMdd");
				// 默认查询前七天的数据
				beginDate = DateUtils.formatByFormatRule(
						DateUtils.getPrevious7Date(new Date()), "yyyyMMdd");
			} else {
				endDate = DateUtils.formatUtilDate(new Date(), "yyyyMM");//
				// 默认查询前一年的数据
				beginDate = DateUtils.formatByFormatRule(
						DateUtils.getPreviousYear(new Date()), "yyyyMM");
			}
		} else {
			endDate = StringUtils.replace(endDate, "-", "");
			if (Integer.parseInt(kpiType) == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
				// 默认查询前七天的数据
				beginDate = DateUtils.formatByFormatRule(DateUtils
						.getPrevious7Date(DateUtils.parseByFormatRule(endDate,
								"yyyyMMdd")), "yyyyMMdd");
			} else {
				// 默认查询前一年的数据
				beginDate = DateUtils.formatByFormatRule(DateUtils
						.getNextMonth(DateUtils.getPreviousYear(DateUtils
								.parseByFormatRule(endDate, "yyyyMM"))),
						"yyyyMM");
			}
		}
		ChartParam chartParam = new ChartParam();
		chartParam.setKpiCodes(kpiCodes.split(";"));
		chartParam.setKpiType(Integer.parseInt(kpiType));
		// 图的类型（折线为line; 矩形为 column;）
		String chartTypes = request.getParameter("chartTypes");// 以";"分隔
		chartParam.setChartTypes(chartTypes.split(";"));
		// 开始日期
		if (chartTypes.toLowerCase().indexOf("column") != -1) {
			// 为矩形或组合图,默认查询前七天或一年的数据
			chartParam.setBeginDate(beginDate);
		} else {
			// 为折线图,默认查询两年的数据
			String endStatDate = endDate.replaceAll("-", "");
			Date beginStatDate = null;
			if (Integer.parseInt(kpiType) == DwpasCKpiInfo.KPI_TYPE_OF_MONTH) {
				beginStatDate = DateUtils.getPreviousMonth(
						DateUtils.parseByFormatRule(endStatDate, "yyyyMM"),
						Constants.TIME_SPAN_STOCK);
				chartParam.setBeginDate(DateUtils.formatByFormatRule(
						beginStatDate, "yyyyMM"));
			} else {
				beginStatDate = DateUtils.getPreviousMonth(
						DateUtils.parseByFormatRule(endStatDate, "yyyyMMdd"),
						Constants.TIME_SPAN_STOCK);
				chartParam.setBeginDate(DateUtils.formatByFormatRule(
						beginStatDate, "yyyyMMdd"));
			}
		}
		// 结束日期
		chartParam.setEndDate(endDate);
		// 指标类型
		chartParam.setKpiType(Integer.parseInt(kpiType));
		// 查询数据
		Chart chart = this.columnChartService.getColumnOrLineChart(chartParam);
		try {
			String rootPath = request.getSession().getServletContext()
					.getRealPath("/");
			// 初始化并取得Velocity引擎
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			// properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH,cfgMap.get("templatePath")+"/prodana/screen/amchartDataVM");
			properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, rootPath
					+ Constants.VM_FILE_PATH);
			ve.init(properties);

			// 取得velocity的模版
			// ve.setProperty(Velocity.RESOURCE_LOADER, "file");
			// ve.setProperty("file.resource.loader.class",
			// "org.apache.velocity.runtime.resource.loader.FileResourceLoader");

			Template template = ve.getTemplate("column_data.vm", "UTF-8");
			// 取得velocity的上下文context
			VelocityContext context = new VelocityContext();

			// 把数据填入上下文
			context.put("chart", chart == null ? new Chart() : chart);// 防止发生出现图的异常

			// 输出流
			StringWriter writer = new StringWriter();

			// 转换输出
			template.merge(context, writer);
			response.setContentType("text/xml;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(writer.toString());
			out.flush();
			this.logger.info("生成矩形图结束...");
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 得到矩形图或组合图的设置XML
	 */
	@RequestMapping("/columnOrLineChart/showChartSetting")
	public void getColumnOrLineChartSetting(HttpServletRequest request,
			HttpServletResponse response) {
		// 查询的KPI指标
		String kpiCodes = request.getParameter("kpiCodes");// 以";"分隔
		if (!StringUtils.notNullAndSpace(kpiCodes)) {
			this.logger.warn("得到矩形图或折线图的设置XML失败：指标为空");
			return;
		}
		this.logger.info("生成矩形图或折线图的设置信息:" + kpiCodes);
		// 图的类型
		String chartTypes = request.getParameter("chartTypes");// 以";"分隔
		this.logger.info("线类型:" + chartTypes);
		List<DwpasCKpiInfo> kpiInfoList = this.dwpasCKpiInfoService
				.listDwpasCKpiInfoByCodes(Arrays.asList(kpiCodes.split(";")));
		List<Graph> graphList = new ArrayList<Graph>();
		Graph graph = null;
		int i = 0;
		for (DwpasCKpiInfo kpiInfo : kpiInfoList) {
			graph = new Graph();
			graph.setGraphId(kpiInfo.getKpiCode());
			graph.setGraphName(kpiInfo.getDispName() + "(" + kpiInfo.getUnit()
					+ ")");
			try {
				if (this.chart_type_line
						.equalsIgnoreCase(chartTypes.split(";")[i])) {
					graph.setGraphType(Graph.GRAPH_TYPE_LINE);
				} else {
					graph.setGraphType(Graph.GRAPH_TYPE_COLUMN);
				}
				if (!graph.getGraphTypeDesc().equalsIgnoreCase(
						this.chart_type_column)
						&& !graph.getGraphTypeDesc().equalsIgnoreCase(
								this.chart_type_line)) {
					this.logger.info(kpiInfo.getKpiCode()
							+ "---------->设置默认为矩形");
					graph.setGraphType(Graph.GRAPH_TYPE_COLUMN);
				}
				this.logger.info(kpiInfo.getKpiCode() + " -->线类型:"
						+ graph.getGraphType());
			} catch (Exception e) {
				this.logger.warn(kpiInfo.getKpiCode() + " 取线类型失败:"
						+ e.getMessage());
				graph.setGraphType(Graph.GRAPH_TYPE_COLUMN);
			}
			this.logger.info("显示图类型:" + graph.getGraphType());
			graph.setGraphColor(Constants.CHART_COLOR_LIST.get(i));
			//
			graphList.add(graph);
			//
			i++;
		}
		//
		Chart chart = new Chart();
		chart.setChartName("矩形图或折线图");
		//
		chart.setGraphList(graphList);
		// 图类型
		if (StringUtils.notNullAndSpace(chartTypes)) {
			if (chartTypes.equalsIgnoreCase("bar")) {
				// 条形图
				chart.setChartType("bar");
			} else {
				// 矩形或矩形+折线
				chart.setChartType("column");
			}
		} else {
			// 矩形或矩形+折线
			chart.setChartType("column");
		}
		this.logger.info("-->图类型:" + chart.getChartType());
		try {
			String rootPath = request.getSession().getServletContext()
					.getRealPath("/");
			// 初始化并取得Velocity引擎
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			// properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH,cfgMap.get("templatePath")+"/prodana/screen/amchartDataVM");
			properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, rootPath
					+ Constants.VM_FILE_PATH);
			ve.init(properties);

			// 取得velocity的模版
			// ve.setProperty(Velocity.RESOURCE_LOADER, "file");
			// ve.setProperty("file.resource.loader.class",
			// "org.apache.velocity.runtime.resource.loader.FileResourceLoader");

			Template template = ve.getTemplate("column_settings.vm", "UTF-8");
			// 取得velocity的上下文context
			VelocityContext context = new VelocityContext();

			// 把数据填入上下文
			context.put("chart", chart);
			context.put("kpiCodes", kpiCodes);
			context.put("kpiType", request.getParameter("kpiType"));
			// 输出流
			StringWriter writer = new StringWriter();

			// 转换输出
			template.merge(context, writer);
			response.setContentType("text/xml;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(writer.toString());
			out.flush();
			this.logger.info("生成矩形图或折线图的设置信息结束...");
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 得到折线图的设置XML
	 */
	@RequestMapping("/columnOrLineChart/showLineChartSetting")
	public void getLineChartSetting(HttpServletRequest request,
			HttpServletResponse response) {
		// 查询的KPI指标
		String kpiCodes = request.getParameter("kpiCodes");// 以";"分隔
		if (!StringUtils.notNullAndSpace(kpiCodes)) {
			this.logger.warn("得到折线图的设置XML失败：指标为空");
			return;
		}
		this.logger.info("生成折线图的设置信息:" + kpiCodes);
		List<DwpasCKpiInfo> kpiInfoList = this.dwpasCKpiInfoService
				.listDwpasCKpiInfoByCodes(Arrays.asList(kpiCodes.split(";")));
		List<Graph> graphList = new ArrayList<Graph>();
		Graph graph = null;
		int i = 0;
		for (DwpasCKpiInfo kpiInfo : kpiInfoList) {
			graph = new Graph();
			graph.setGraphId(kpiInfo.getKpiCode());
			graph.setGraphName(kpiInfo.getDispName() + "(" + kpiInfo.getUnit()
					+ ")");
			graph.setGraphColor(Constants.CHART_COLOR_LIST.get(i));
			//
			graphList.add(graph);
			//
			i++;
		}
		//
		Chart chart = new Chart();
		chart.setChartName("折线图");
		//
		chart.setGraphList(graphList);
		try {
			String rootPath = request.getSession().getServletContext()
					.getRealPath("/");
			// 初始化并取得Velocity引擎
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			// properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH,cfgMap.get("templatePath")+"/prodana/screen/amchartDataVM");
			properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, rootPath
					+ Constants.VM_FILE_PATH);
			ve.init(properties);

			// 取得velocity的模版
			// ve.setProperty(Velocity.RESOURCE_LOADER, "file");
			// ve.setProperty("file.resource.loader.class",
			// "org.apache.velocity.runtime.resource.loader.FileResourceLoader");

			Template template = ve.getTemplate("line_settings.vm", "UTF-8");
			// 取得velocity的上下文context
			VelocityContext context = new VelocityContext();

			// 把数据填入上下文
			context.put("chart", chart);
			context.put("kpiCodes", kpiCodes);
			context.put("kpiType", request.getParameter("kpiType"));
			if (kpiInfoList != null && kpiInfoList.size() > 0) {
				context.put("type", kpiInfoList.get(0).getKpiType());
			} else {
				context.put("type", DwpasCKpiInfo.KPI_TYPE_OF_DAY);
			}

			// 输出流
			StringWriter writer = new StringWriter();

			// 转换输出
			template.merge(context, writer);
			response.setContentType("text/xml;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(writer.toString());
			out.flush();
			this.logger.info("生成折线图的设置信息结束...");
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}
}
