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
import com.infosmart.portal.pojo.dwmis.DwmisKpiInfo;
import com.infosmart.portal.service.dwmis.DwmisKpiInfoService;
import com.infosmart.portal.service.dwmis.DwmisLineService;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.dwmis.CoreConstant;
import com.infosmart.portal.vo.Chart;
import com.infosmart.portal.vo.Graph;
import com.infosmart.portal.vo.dwmis.ChartParam;

@Controller
public class DwmisLineController extends BaseController {
	@Autowired
	private DwmisLineService dwmisGraphService;
	@Autowired
	private DwmisKpiInfoService dwmisKpiInfoService;

	/**
	 * 生成折线图
	 * 
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping("/DwmisLineGraph/ShowLineGraph")
	public void showLineGraph(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 指标编码
		String kpiCodes = request.getParameter("kpiCodes");

		// 是否有目标线
		String showWarnLine = request.getParameter("showWarnLine");

		String[] kpiCodeList = kpiCodes.split(";");
		// 日期类型
		String dateType = request.getParameter("dateType");
		// 查询日期
		String queryDate = request.getParameter("queryDate");
		if (StringUtils.notNullAndSpace(queryDate)) {
			this.setCrtQueryDateOfDwmis(request, queryDate);
		} else {
			queryDate = this.getCrtQueryDateOfDwmis(request);
		}
		// 计算开始时间
		try {
			Integer.parseInt(dateType);
		} catch (Exception e) {
			dateType = String.valueOf(CoreConstant.DAY_PERIOD);
		}
		// 统计方式
		String staCode = request.getParameter("staCode");
		// 默认统计方式为 当期值
		if (!StringUtils.notNullAndSpace(staCode)) {
			staCode = String.valueOf(CoreConstant.STA_CURRENT);
		}
		// 指明该指标是关注什么（峰值、谷值、平均值、周日平均值等）。 select * from MIS_TYPE mt where
		// mt.group_id=3000 30012 月峰值（计去年） 30021 月谷值（pt） 30052 日累积值（计去年） 30051
		// 日累积值（pt） 3008 月当期 3009 月期末 3001 月峰值 3002 月谷值 3003 日峰值 3004 日谷值 3005
		// 日累积值
		// 3006 月累积值 3007 七日均值峰值 30011 月峰值（pt）
		String goalType = request.getParameter("goalType");
		if (StringUtils.notNullAndSpace(goalType)) {
			if ((Integer.valueOf(goalType) == 3001)
					|| (Integer.valueOf(goalType) == 30011)
					|| (Integer.valueOf(goalType) == 30012)) {
				dateType = "1004";
				staCode = "2001";
			} else if ((Integer.valueOf(goalType) == 3002)
					|| (Integer.valueOf(goalType) == 30021)) {
				dateType = "1004";
				staCode = "2001";
			} else if ((Integer.valueOf(goalType) == 3005)
					|| (Integer.valueOf(goalType) == 30052)
					|| (Integer.valueOf(goalType) == 30051)) {
				dateType = "1002";
				staCode = "2002";
			} else if (Integer.valueOf(goalType) == 3007) {
				dateType = "1002";
				staCode = "2006";
			} else if (Integer.valueOf(goalType) == 3008) {
				dateType = "1004";
				staCode = "2001";
			} else if (Integer.valueOf(goalType) == 3009) {
				dateType = "1004";
				staCode = "2002";
			}
		}

		ChartParam chartParam = new ChartParam();
		chartParam.setEndDate(queryDate);
		chartParam.setKpiCodes(kpiCodeList);
		String chartTypes = "line";
		// 全是线形
		for (int i = 0; i < kpiCodeList.length; i++) {
			chartTypes = chartTypes + ";";
		}
		chartParam.setChartTypes(chartTypes.split(";"));
		chartParam.setDateType(Integer.valueOf(dateType));
		Chart chart = null;
		if (StringUtils.notNullAndSpace(showWarnLine)
				&& Integer.valueOf(showWarnLine) == 1) {
			// 有目标线
			chartParam.setKpiCode(Arrays.asList(kpiCodeList).get(0));
			chart = this.dwmisGraphService.getLineChartShowWarnLine(chartParam,
					Integer.valueOf(staCode));
		} else {
			// 无目标线
			chart = this.dwmisGraphService.getLineChart(chartParam,
					Integer.valueOf(staCode));
		}
		try {
			String rootPath = request.getSession().getServletContext()
					.getRealPath("/");
			// 初始化并取得Velocity引擎
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, rootPath
					+ Constants.VM_FILE_PATH + "/dwmis/");
			ve.init(properties);

			Template template = ve.getTemplate("dwmis_ine_data.vm", "UTF-8");
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
			this.logger.info("生成折线图结束...");
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 折线图设置文件
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/DwmisLineGraph/ShowLineGraphSetting")
	public void ShowLineGraphSetting(HttpServletRequest request,
			HttpServletResponse response) {
		String kpiCodes = request.getParameter("kpiCodes");// 以";"分隔
		// 是否有目标线
		String showWarnLine = request.getParameter("showWarnLine");
		if (!StringUtils.notNullAndSpace(kpiCodes)) {
			this.logger.warn("得到折线图的设置XML失败：指标为空");
			return;
		}
		List<DwmisKpiInfo> kpiInfoList = dwmisKpiInfoService
				.getDwmisKpiInfoListByCodes(Arrays.asList(kpiCodes.split(";")));
		if (kpiInfoList == null) {
			return;
		}
		List<Graph> graphList = new ArrayList<Graph>();
		Graph graph = null;
		Graph goalgraph = null;
		int i = 0;
		// 单位是百分比的个数
		int j = 0;
		if (Integer.valueOf(showWarnLine) == 1) {
			for (DwmisKpiInfo kpiInfo : kpiInfoList) {
				graph = new Graph();
				goalgraph = new Graph();
				graph.setGraphId(kpiInfo.getKpiCode());
				goalgraph.setGraphId("目标");
				graph.setGraphName(kpiInfo.getKpiNameShow() + "("
						+ kpiInfo.getUnitName() + ")");
				goalgraph.setGraphName("目标线");
				try {
					graph.setGraphType(Graph.GRAPH_TYPE_LINE);
					goalgraph.setGraphType(Graph.GRAPH_TYPE_LINE);
				} catch (Exception e) {
					this.logger.warn(kpiInfo.getKpiCode() + " 取线类型失败:"
							+ e.getMessage());
					graph.setGraphType(Graph.GRAPH_TYPE_LINE);
					goalgraph.setGraphType(Graph.GRAPH_TYPE_LINE);
				}
				this.logger.info("显示图类型:" + graph.getGraphType());
				graph.setGraphColor(Constants.CHART_COLOR_LIST.get(i));
				//
				graphList.add(graph);
				graphList.add(goalgraph);
				//
				i++;
			}
		} else {
			for (DwmisKpiInfo kpiInfo : kpiInfoList) {
				graph = new Graph();
				graph.setGraphId(kpiInfo.getKpiCode());
				if (kpiInfo.getUnitName().equals("%")
						|| kpiInfo.getSizName().equals("个")) {
					graph.setGraphName(kpiInfo.getKpiNameShow() + "("
							+ kpiInfo.getUnitName() + ")");
				} else {
					graph.setGraphName(kpiInfo.getKpiNameShow() + "("
							+ kpiInfo.getSizName() + kpiInfo.getUnitName()
							+ ")");
				}

				graph.setUnitId(kpiInfo.getUnitId());
				try {
					graph.setGraphType(Graph.GRAPH_TYPE_LINE);
				} catch (Exception e) {
					this.logger.warn(kpiInfo.getKpiCode() + " 取线类型失败:"
							+ e.getMessage());
					graph.setGraphType(Graph.GRAPH_TYPE_LINE);
				}
				this.logger.info("显示图类型:" + graph.getGraphType());
				graph.setGraphColor(Constants.CHART_COLOR_LIST.get(i));
				//
				graphList.add(graph);
				//
				if (kpiInfo.getUnitId() == 5004) {
					j++;
				}
				i++;
			}
		}
		//

		Chart chart = new Chart();
		chart.setChartName("折线图");
		//
		if (j == kpiInfoList.size()) {
			// 全为百分比
			chart.setAllPercent(1);
		} else {
			// 不全为百分比
			chart.setAllPercent(0);
		}
		chart.setGraphList(graphList);
		try {
			String rootPath = request.getSession().getServletContext()
					.getRealPath("/");
			// 初始化并取得Velocity引擎
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, rootPath
					+ Constants.VM_FILE_PATH + "/dwmis/");
			ve.init(properties);
			Template template;
			// 是否显示选择指标按钮
			String isShowSelectButton = request
					.getParameter("isShowSelectButton");
			if (!StringUtils.notNullAndSpace(isShowSelectButton)) {
				template = ve.getTemplate("dwmis_line_settings.vm", "UTF-8");
			} else {
				template = ve.getTemplate("dwmis_line_settings_monitoring.vm",
						"UTF-8");
			}

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
}
