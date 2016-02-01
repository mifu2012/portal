package com.infosmart.portal.action.dwmis;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.infosmart.portal.action.BaseController;
import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.dwmis.DwmisKpiInfo;
import com.infosmart.portal.service.dwmis.DwmisColumnCharService;
import com.infosmart.portal.service.dwmis.DwmisKpiInfoService;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.dwmis.CoreConstant;
import com.infosmart.portal.util.dwmis.TimeFormatProcessor;
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
public class DwmisColumnOrLineChartController extends BaseController {
	private final String chart_type_column = "column";
	private final String chart_type_line = "line";

	@Autowired
	private DwmisKpiInfoService kpiInfoService;

	@Autowired
	private DwmisColumnCharService columnChartService;

	/*
	 * @Autowired private ColumnChartService columnChartService;
	 */
	/**
	 * 得到矩形图或折线图的数据XML
	 */
	@RequestMapping("/DwmisColumnOrLineChart/showChartData")
	public void getColumnOrLineChartData(HttpServletRequest request,
			HttpServletResponse response) {
		// 查询的KPI指标
		String kpiCodes = request.getParameter("kpiCodes");// 以";"分隔
		if (!StringUtils.notNullAndSpace(kpiCodes)) {
			this.logger.warn("得到矩形图或折线图的设置XML失败：指标为空");
			return;
		}
		this.logger.info("准备生成矩形或折线图:" + kpiCodes);
		// 时间类型
		int dateType = CoreConstant.DAY_PERIOD;
		if (request.getParameter("dateType") != null) {
			dateType = Integer.valueOf(request.getParameter("dateType"));
		}
		// 统计方式
		int staCode = CoreConstant.STA_CURRENT;
		if (request.getParameter("staCode") != null) {
			staCode = Integer.valueOf(request.getParameter("staCode"));
		}
		// 查询日期
		String queryDate = request.getParameter("queryDate");
		if (StringUtils.notNullAndSpace(queryDate)) {
			this.setCrtQueryDateOfDwmis(request, queryDate);
		} else {
			queryDate = this.getCrtQueryDateOfDwmis(request);
		}

		ChartParam chartParam = new ChartParam();
		chartParam.setKpiCodes(kpiCodes.split(";"));
		// 图的类型
		String chartTypes = request.getParameter("chartTypes");// 以";"分隔
		chartParam.setChartTypes(chartTypes.split(";"));
		chartParam.setDateType(dateType);
		chartParam.setEndDate(queryDate);
		// 查询数据
		Chart chart = this.columnChartService.getColumnOrLineChart(chartParam,
				staCode);
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
			context.put("chart", chart);

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
	 * 得到矩形图或折线图的设置XML
	 */
	@RequestMapping("/DwmisColumnOrLineChart/showChartSetting")
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
		List<DwmisKpiInfo> kpiInfoList = this.kpiInfoService
				.getDwmisKpiInfoListByCodes(Arrays.asList(kpiCodes.split(";")));
		List<Graph> graphList = new ArrayList<Graph>();
		Graph graph = null;
		int i = 0;
		for (DwmisKpiInfo kpiInfo : kpiInfoList) {
			graph = new Graph();
			graph.setGraphId(kpiInfo.getKpiCode());
			if (kpiInfo.getUnitName().equals("%")
					|| kpiInfo.getSizName().equals("个")) {
				graph.setGraphName(kpiInfo.getKpiNameShow() + "("
						+ kpiInfo.getUnitName() + ")");
			} else {
				graph.setGraphName(kpiInfo.getKpiNameShow() + "("
						+ kpiInfo.getSizName() + kpiInfo.getUnitName() + ")");
			}
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
					+ Constants.VM_FILE_PATH + "/dwmis");
			ve.init(properties);

			// 取得velocity的模版
			// ve.setProperty(Velocity.RESOURCE_LOADER, "file");
			// ve.setProperty("file.resource.loader.class",
			// "org.apache.velocity.runtime.resource.loader.FileResourceLoader");

			Template template = ve.getTemplate("column_settings.vm", "UTF-8");
			// 取得velocity的上下文context
			VelocityContext context = new VelocityContext();
			int dateType = CoreConstant.DAY_PERIOD;
			if (request.getParameter("dateType") != null) {
				dateType = Integer.valueOf(request.getParameter("dateType"));
			}
			if (dateType == CoreConstant.DAY_PERIOD) {
				context.put("frequency", CoreConstant.PREVIOUS_DAY_COUNT);
			} else if (dateType == CoreConstant.MONTH_PERIOD) {
				context.put("frequency", CoreConstant.PREVIOUS_MONTH_COUNT);
			} else {
				context.put("frequency", CoreConstant.PREVIOUS_WEEK_COUNT);
			}
			// 把数据填入上下文
			context.put("chart", chart);

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
	 * 得到矩形图或折线图的设置XML
	 */
	@RequestMapping("/dwmisColumnOrLineChart/showLineChartSetting")
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

	@RequestMapping(value = "/DwmisColumnChart/showChartData")
	public void getColumnChartData_New(HttpServletRequest request,
			HttpServletResponse response) {
		// 查询的KPI指标
		String kpiCode = request.getParameter("kpiCode");// 以";"分隔
		if (!StringUtils.notNullAndSpace(kpiCode)) {
			this.logger.warn("得到矩形图或折线图的设置XML失败：指标为空");
			return;
		}
		this.logger.info("准备生成矩形或折线图:" + kpiCode);
		// 时间类型
		int dateType = CoreConstant.DAY_PERIOD;
		if (request.getParameter("dateType") != null) {
			dateType = Integer.valueOf(request.getParameter("dateType"));
		}
		// 查询日期
		String queryDate = request.getParameter("queryDate");
		if (StringUtils.notNullAndSpace(queryDate)) {
			this.setCrtQueryDateOfDwmis(request, queryDate);
		} else {
			queryDate = this.getCrtQueryDateOfDwmis(request);
		}
		ChartParam chartParam = new ChartParam();
		chartParam.setKpiCode(kpiCode);
		chartParam.setEndDate(queryDate);
		chartParam.setKpiType(dateType);
		// 图的类型
		// String chartTypes = request.getParameter("chartTypes");// 以";"分隔
		// chartParam.setChartTypes(chartTypes.split(";"));
		// 查询数据
		Chart chart = this.columnChartService.getColumnChart(chartParam);
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

			Template template = ve.getTemplate("dwmis/column_data.vm", "UTF-8");
			// 取得velocity的上下文context
			VelocityContext context = new VelocityContext();

			// 把数据填入上下文
			context.put("chart", chart);

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
}
