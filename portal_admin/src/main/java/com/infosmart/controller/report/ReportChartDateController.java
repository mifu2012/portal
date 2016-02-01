package com.infosmart.controller.report;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.infosmart.controller.BaseController;
import com.infosmart.po.Highchart;
import com.infosmart.po.report.PieChart;
import com.infosmart.po.report.ReportCell;
import com.infosmart.po.report.ReportChartFiled;
import com.infosmart.po.report.ReportConfig;
import com.infosmart.po.report.ReportDataSource;
import com.infosmart.po.report.ReportDefine;
import com.infosmart.po.report.ReportDesign;
import com.infosmart.service.report.NewReportService;
import com.infosmart.service.report.ReportChartDateService;
import com.infosmart.service.report.ReportChartService;
import com.infosmart.service.report.ReportConfigService;
import com.infosmart.util.DateUtils;
import com.infosmart.util.StringUtils;

/**
 * 报表图表数据配置
 * 
 * @author infosmart
 * 
 */
@Controller
@RequestMapping("/reportChartDate")
public class ReportChartDateController extends BaseController {
	@Autowired
	private ReportChartService reportChartService;
	@Autowired
	private ReportChartDateService reportChartDateService;
	@Autowired
	private NewReportService newReportService;
	@Autowired
	private ReportConfigService reportConfigService;

	/***
	 * 获取图表数据
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping("/getReportChartDate")
	public void getReportChartDate(HttpServletRequest request,
			HttpServletResponse response) {
		String reportId = request.getParameter("reportId");
		String chartId = request.getParameter("chartId");
		String pageNo = request.getParameter("pageNo");
		String rowNum = request.getParameter("rowNum");
		ReportChartFiled reportChartFiled = null;
		ReportDataSource dataSource = null;
		String fileds = "";
		String reportSql = "";
		if (!StringUtils.notNullAndSpace(reportId)) {
			this.logger.warn("加载图表数据出错：reportId为空");
			return;
		}
		if (!StringUtils.notNullAndSpace(chartId)) {
			this.logger.warn("报表chartId未知--->");
			return;
		}
		if (!StringUtils.notNullAndSpace(pageNo)) {
			pageNo = "1";
		}
		// 查询条件
		List<ReportConfig> rptConfigList = this.reportConfigService
				.listConfigById(new Integer(reportId));
		Map<String, Object> paramValMap = new HashMap<String, Object>();
		if (rptConfigList != null && !rptConfigList.isEmpty()) {
			try {
				String paramVal = null;
				for (ReportConfig rptConfig : rptConfigList) {
					paramVal = request.getParameter(rptConfig.getColumnCode());
					this.logger.info("参数名：" + rptConfig.getColumnCode()
							+ "，参数值为:" + paramVal);
					if (StringUtils.notNullAndSpace(paramVal)) {
						// 处理中文乱码
						paramVal = new String(paramVal.getBytes("iso-8859-1"),
								"utf-8");
						// 如果是日期型
						if (rptConfig.getControlType().intValue() == 3) {
							// 日期
							String dateFormat = "yyyy-MM-dd";
							if ("3".equals(rptConfig.getDateFormat())) {
								dateFormat = "yyyyMMdd";
							} else if ("1".equals(rptConfig.getDateFormat())) {
								dateFormat = "yyyy/MM/dd";
							}
							paramVal = DateUtils.formatUtilDate(
									DateUtils.parseStringToUtilDate(paramVal),
									dateFormat);
						} else if (rptConfig.getControlType() == 4) {
							// 月份
							String dateFormat = "yyyy-MM";
							if ("3".equals(rptConfig.getDateFormat())) {
								dateFormat = "yyyyMM";
							} else if ("1".equals(rptConfig.getDateFormat())) {
								dateFormat = "yyyy/MM";
							}
							paramVal = DateUtils.formatUtilDate(
									DateUtils.parseStringToUtilDate(paramVal),
									dateFormat);
						}
						this.logger.info("---------------->参数:"
								+ rptConfig.getColumnCode());
						this.logger.info("---------------->参数值:" + paramVal);
						paramValMap.put(rptConfig.getColumnCode(), paramVal);
					} else {
						this.logger.info("---------------->参数无值:"
								+ rptConfig.getColumnCode());
						paramValMap.put(rptConfig.getColumnCode(), null);
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				this.logger.warn("获取列表数据出错：", e);
			} catch (Exception e) {
				e.printStackTrace();
				this.logger.warn("获取列表数据出错：", e);
			}
		}
		// 获得字段数据
		reportChartFiled = reportChartService.getIsSelOrNotTableFiled(Integer
				.parseInt(chartId));
		// 取字段名
		if (reportChartFiled == null) {
			this.logger.warn("加载图表数据出错：未取得字段对象");
			return;
		}
		if (!StringUtils.notNullAndSpace(reportChartFiled.getFields())
				|| !StringUtils.notNullAndSpace(reportChartFiled.getxFields())) {
			this.logger.warn("查询图表数据报错：x或y轴字段为空----->");
			this.sendMsgToClient("", response);
			return;
		}
		ReportDesign reportDesign = this.newReportService
				.getReportDesignById(Integer.valueOf(reportId));

		if (reportDesign == null) {
			this.logger.warn("查询图表数据报错：reportDesign为空");
			return;
		}
		if (!StringUtils.notNullAndSpace(reportDesign.getDataSourceId())) {
			this.logger.warn("查询图表数据报错：DataSourceId为空");
			return;
		}
		reportSql = reportDesign.getReportSql();
		// 获取dataSource
		dataSource = this.newReportService.getDataSourceById(Integer
				.parseInt(reportDesign.getDataSourceId()));
		Highchart highchart = null;
		Map<String, List<Object>> mapIntegerList = null;
		// x轴数据
		List<String> stringList = null;
		// 每个图表中的Data个数
		List<Highchart> highchartList = null;
		List<Object> chartdate = null;
		// chartType:1.饼图 2.折线图 3.柱状图 4. 直线 5.柱状加折线
		highchartList = new ArrayList<Highchart>();

		fileds = reportChartFiled.getFields() + ","
				+ reportChartFiled.getxFields();
		// 获取数据源信息
		List<Object> objList = null;
		// 图表字段对应表头名称
		Map<String, String> reportCellMap = new HashMap<String, String>();
		String arry[] = null;
		// 获取json对象
		String jsonData = reportDesign.getReportDefine();
		ReportDefine reportDefine = null;
		List<ReportCell> reportCellList = new ArrayList<ReportCell>();
		if (!StringUtils.notNullAndSpace(jsonData)) {
			this.logger.warn("获取图表数据出错：jsonData为空");
			return;
		}
		reportDefine = this.parseReportJsonData(jsonData);
		reportCellList = reportDefine.getReportCellList();
		if (reportCellList != null && reportCellList.size() > 0) {
			for (ReportCell reportCell : reportCellList) {
				reportCellMap.put(reportCell.getDataField(),
						reportCell.getContent());
			}
		}
		// 获取页面每页条数，不为空时则把该值设置为pageSize
		if (StringUtils.notNullAndSpace(rowNum)) {
			reportDesign.setPageSize(Integer.parseInt(rowNum));
		} else {
			reportDesign.setPageSize(reportDefine.getPageSize());
		}

		// 根据字段取数据 key为字段名 value为字段值List对象
		objList = reportChartDateService.getReportChartDate(reportDesign,
				fileds, reportSql, paramValMap, pageNo, dataSource);
		if (objList == null || objList.size() < 2) {
			this.logger.warn("查询图表数据出错：objList为空或个数少于2");
			return;
		}
		mapIntegerList = (Map<String, List<Object>>) objList.get(0);
		stringList = (List<String>) objList.get(1);
		arry = fileds.split(",");
		int index = 1;
		for (int i = 0; i < arry.length - 1; i++) {
			// 设置图表数据格式
			highchart = new Highchart();
			highchart.setName(reportCellMap.get(arry[i]) == null ? arry[i]
					: reportCellMap.get(arry[i]));
			if (reportChartFiled.getChartType() == 1) {
				highchart.setType("pie");
			} else if (reportChartFiled.getChartType() == 2) {
				highchart.setType("spline");
			} else if (reportChartFiled.getChartType() == 3) {
				highchart.setType("column");
			} else if (reportChartFiled.getChartType() == 4) {
				highchart.setType("line");
			} else if (reportChartFiled.getChartType() == 5) {
				if (index % 2 == 0) {
					highchart.setType("spline");
				} else {
					highchart.setType("column");
				}
				index++;
			}
			chartdate = new ArrayList<Object>();
			chartdate = mapIntegerList.get(arry[i]);
			highchart.setData(chartdate);
			highchartList.add(highchart);
		}
		String chartType = request.getParameter("chartType");
		// 趋势图
		if (chartType != null && chartType.equals("2")) {
			for (int i = 0; i < highchartList.size(); i++) {
				List data = highchartList.get(i).getData();
				if (data == null) continue;
				for (int j = 0; j < data.size(); j++) {
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						if (stringList.get(j) == null) continue;
						Date date = sdf.parse(stringList.get(j));
						//　加了8个小时，用来符合前台显示
						if (data.get(j) == null) {
							BigDecimal[] temp = {new BigDecimal(date.getTime()+8*3600*1000), new BigDecimal(0)};
							data.set(j, temp);
						} else {
							BigDecimal[] temp = {new BigDecimal(date.getTime()+8*3600*1000), (BigDecimal) data.get(j)};
							data.set(j, temp);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				highchartList.get(i).setData(data);
			}
		}
		
		JSONArray jsonArray = null;
		JSONArray jsonArray2 = null;
		jsonArray = JSONArray.fromObject(highchartList);
		jsonArray2 = JSONArray.fromObject(stringList);
		this.sendMsgToClient(
				jsonArray.toString() + "&" + jsonArray2.toString(), response);
	}

	/**
	 * 加载饼图数据
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getPieChartDate")
	public void getPieChartDate(HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.info("加载饼图数据.....");
		String reportId = request.getParameter("reportId");
		if (!StringUtils.notNullAndSpace(reportId)) {
			this.logger.info("加载饼图数据出错：reportId为空");
			return;
		}
		String chartId = request.getParameter("chartId");
		// 查询条件
		List<ReportConfig> rptConfigList = this.reportConfigService
				.listConfigById(new Integer(reportId));
		Map<String, Object> paramValMap = new HashMap<String, Object>();
		if (rptConfigList != null && !rptConfigList.isEmpty()) {
			try {
				String paramVal = null;
				for (ReportConfig rptConfig : rptConfigList) {
					paramVal = request.getParameter(rptConfig.getColumnCode());
					this.logger.info("参数名：" + rptConfig.getColumnCode()
							+ "，参数值为:" + paramVal);
					if (StringUtils.notNullAndSpace(paramVal)) {
						// 处理中文乱码
						paramVal = new String(paramVal.getBytes("iso-8859-1"),
								"utf-8");
						// 如果是日期型
						if (rptConfig.getControlType().intValue() == 3) {
							// 日期
							String dateFormat = "yyyy-MM-dd";
							if ("3".equals(rptConfig.getDateFormat())) {
								dateFormat = "yyyyMMdd";
							} else if ("1".equals(rptConfig.getDateFormat())) {
								dateFormat = "yyyy/MM/dd";
							}
							paramVal = DateUtils.formatUtilDate(
									DateUtils.parseStringToUtilDate(paramVal),
									dateFormat);
						} else if (rptConfig.getControlType() == 4) {
							// 月份
							String dateFormat = "yyyy-MM";
							if ("3".equals(rptConfig.getDateFormat())) {
								dateFormat = "yyyyMM";
							} else if ("1".equals(rptConfig.getDateFormat())) {
								dateFormat = "yyyy/MM";
							}
							paramVal = DateUtils.formatUtilDate(
									DateUtils.parseStringToUtilDate(paramVal),
									dateFormat);
						}
						this.logger.info("---------------->参数:"
								+ rptConfig.getColumnCode());
						this.logger.info("---------------->参数值:" + paramVal);
						paramValMap.put(rptConfig.getColumnCode(), paramVal);
					} else {
						this.logger.info("---------------->参数无值:"
								+ rptConfig.getColumnCode());
						paramValMap.put(rptConfig.getColumnCode(), null);
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				this.logger.warn("获取列表数据出错：", e);
			} catch (Exception e) {
				e.printStackTrace();
				this.logger.warn("获取列表数据出错：", e);
			}
		}
		if (!StringUtils.notNullAndSpace(chartId)) {
			this.logger.warn("加载饼图数据失败：chartId未知--->");
			return;
		}
		ReportChartFiled reportChartFiled = null;
		reportChartFiled = reportChartService.getIsSelOrNotTableFiled(Integer
				.parseInt(chartId));
		if (reportChartFiled == null) {
			this.logger.warn("查询图表数据报错：报表字段信息为空-为空");
			return;
		}
		if (!StringUtils.notNullAndSpace(reportChartFiled.getFields())
				|| !StringUtils.notNullAndSpace(reportChartFiled.getxFields())) {
			this.logger.warn("查询图表数据报错：x或y轴字段为空----->");
			return;
		}
		ReportDesign reportDesign = this.newReportService
				.getReportDesignById(Integer.valueOf(reportId));
		if (reportDesign == null) {
			this.logger.warn("查询图表数据报错：reportDesign为空");
			return;
		}
		String reportSql = reportDesign.getReportSql();
		if (!StringUtils.notNullAndSpace(reportDesign.getDataSourceId())) {
			this.logger.warn("查询图表数据报错：DataSourceId为空");
			return;
		}
		// 根据字段取数据 key为字段名 value为数据
		List<Map<Object, Object>> listMap = new ArrayList<Map<Object, Object>>();
		// 获取数据源信息
		ReportDataSource dataSource = this.newReportService
				.getDataSourceById(Integer.parseInt(reportDesign
						.getDataSourceId()));
		if (dataSource == null) {
			this.logger.warn("加载饼图数据失败:dataSource为空--->");
			return;
		}
		listMap = reportChartDateService.getPieChartDates(reportSql,
				paramValMap, reportChartFiled, dataSource);
		if (listMap == null || listMap.size() <= 0) {
			this.logger.warn("查询饼图数据为空");
			return;
		}
		List<PieChart> piChartList = new ArrayList<PieChart>();
		PieChart pieChart = new PieChart();
		pieChart.setType("pie");
		pieChart.setData(listMap);
		piChartList.add(pieChart);
		JSONArray jsonArray = null;
		try {
			jsonArray = JSONArray.fromObject(piChartList);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		String str = jsonArray.toString();
		if (str != null && !"".equals(str)) {
			str = str.substring(0, str.indexOf(":") + 1)
					+ (str.substring(str.indexOf(":") + 1, str.length() - 2)
							.replace("[{", "[[").replace("}]", "]]")).replace(
							"},{", "],[")
					+ str.substring(str.length() - 2, str.length());
			str = str.substring(0, str.indexOf("[["))
					+ str.substring(str.indexOf("[["), str.lastIndexOf("]]"))
							.replace(":", ",")
					+ str.substring(str.lastIndexOf("]]"), str.length());
		}
		this.sendMsgToClient(str, response);
	}

	/**
	 * 解析json数据
	 * 
	 * @param jsonData
	 * @return
	 */
	private ReportDefine parseReportJsonData(String jsonData) {
		JSONObject gtJsonObject = (JSONObject) JSONSerializer.toJSON(jsonData);
		// 属性类，用于解析属性类
		Map<String, Object> classMap = new HashMap<String, Object>();
		classMap.put("reportCellList", ReportCell.class);
		ReportDefine reportDefine = (ReportDefine) JSONObject.toBean(
				gtJsonObject, ReportDefine.class, classMap);
		return reportDefine;

	}
}
