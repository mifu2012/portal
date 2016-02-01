package com.infosmart.controller.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.controller.BaseController;
import com.infosmart.po.report.ReportChartColumn;
import com.infosmart.po.report.ReportChartFiled;
import com.infosmart.po.report.ReportDataSource;
import com.infosmart.po.report.ReportDesign;
import com.infosmart.service.report.NewReportService;
import com.infosmart.service.report.ReportChartService;
import com.infosmart.util.StringUtils;
/**
 * 报表图表配置
 * @author infosmart
 *
 */
@Controller
@RequestMapping("/reportChart")
public class ReportChartController extends BaseController {
	@Autowired
	private ReportChartService reportChartService;
	@Autowired
	private NewReportService newReportService;

	/**
	 * 获取图表已选择和未选择字段(Y轴)
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getIsSelOrNotTableFiled")
	public ModelAndView getIsSelTableFiled(HttpServletRequest request,
			HttpServletResponse response) {
		String chartId = request.getParameter("chartId");// 图表Id
		String selY = request.getParameter("selY");// 判断要显示的是x轴字段还是y轴字段 1：y 0:x
		String chartType = request.getParameter("chartType");// 图表类型
		String reportId = request.getParameter("reportId");
		String querySql = "";
		ReportDataSource dataSource = null;
		// 获取报表reportSql 防止reportId为空
		ReportDesign reportDesign = new ReportDesign();
		if (reportId == null || "".equals(reportId)) {
			this.logger.warn("reportId为空");
			return null;
		}
		reportDesign = this.newReportService.getReportDesignById(Integer
				.valueOf(reportId));
		if (reportDesign == null) {
			this.logger.warn("查询图表字段出错：reportDesign为空");
			return null;
		}
		querySql = reportDesign.getReportSql();
		if (!StringUtils.notNullAndSpace(reportDesign.getDataSourceId())) {
			this.logger.warn("获取字段数据出错：DataSourceId为空");
			return null;
		}
		dataSource = this.newReportService.getDataSourceById(Integer
				.valueOf(reportDesign.getDataSourceId()));
		if (dataSource == null) {
			this.logger.warn("ReportChartController--->>>>>dataSource为空");
			return null;
		}
		// querySql = "select * from dwpas_c_kpi_info";
		Map map = new HashMap();
		List<ReportChartColumn> reportChartColumnList = null;
		ReportChartFiled IsSelTableFiled = null;
		List<String> filedList = new ArrayList<String>();
		List<String> IsNotSelTableFiledList = new ArrayList<String>();

		// 字段
		String fileds = "";
		// 获得对象
		if (chartId == null || "".equals(chartId)) {
			this.logger.warn("chartId为空");
			return null;
		}
		IsSelTableFiled = reportChartService.getIsSelOrNotTableFiled(Integer
				.parseInt(chartId));
		if (IsSelTableFiled == null) {
			this.logger.warn("查询字段数据为空");
			return null;
		}
		// 直接查询表的列数据
		if (querySql != null && !"".equals(querySql)) {
			// y轴
			if ("1".equals(selY)) {
				reportChartColumnList = reportChartService.getTableFiledY(
						querySql, dataSource);
				fileds = IsSelTableFiled.getFields();
			} else {
				// X轴
				reportChartColumnList = reportChartService.getTableFiledX(
						querySql, dataSource);
				fileds = IsSelTableFiled.getxFields();
			}

		} else {
			return new ModelAndView("/reportChart/tableFiled");
		}

		String str[] = null;
		if (fileds != null && !fileds.equals("")) {
			str = fileds.split(",");
			for (int i = 0; i < str.length; i++) {
				filedList.add(str[i]);
			}

		}
		if (reportChartColumnList == null) {
			this.logger.warn("获取图表字段报错：元数据为空");
			return null;
		}
		// 去掉已选择
		List<ReportChartColumn> rf = new ArrayList<ReportChartColumn>();
		if (filedList.size() > 0) {
			for (ReportChartColumn reportChartColumn : reportChartColumnList) {
				for (int i = 0; i < filedList.size(); i++) {
					if (reportChartColumn.getColumnLabel().equals(
							filedList.get(i))
							|| reportChartColumn.getColumnLabel() == filedList
									.get(i)) {
						ReportChartColumn rc = new ReportChartColumn();
						rc.setColumnLabel(filedList.get(i));
						rf.add(reportChartColumn);

					}
				}

			}
			for (ReportChartColumn reportChartColumn : rf) {
				reportChartColumnList.remove(reportChartColumn);
			}
			// 如果已选择的字段不是当前查询表中的字段
			if (rf.size() == 0) {
				filedList = new ArrayList<String>();
			}
		}
		for (ReportChartColumn reportChartColumn : reportChartColumnList) {
			// 曲线图（趋势图）X轴只能为日期型数据
			if (selY.equals("0") && chartType.equals("2")
					&& !reportChartColumn.getFileType().equalsIgnoreCase("DATE")) {
				continue;
			}
			IsNotSelTableFiledList.add(reportChartColumn.getColumnLabel());
		}
		map.put("chartId", chartId);
		map.put("chartType", chartType);
		map.put("selY", selY);
		map.put("IsNotSelTableFiledList", IsNotSelTableFiledList);
		map.put("IsSelTableFiledList", filedList);
		return new ModelAndView("/reportChart/tableFiled", map);
	}

	/**
	 * 保存图表数据
	 * 
	 * @param request
	 * @param response
	 * @param reportChartFiled
	 * @return
	 */
	@RequestMapping("/saveTableFiled")
	public ModelAndView saveTableFiled(HttpServletRequest request,
			HttpServletResponse response, ReportChartFiled reportChartFiled) {
		Map map = new HashMap();
		try {
			reportChartService.updateChartFiled(reportChartFiled);
			map.put("msg", "success");
			this.insertLog(request, "保存图表数据成功,图表ID为："+reportChartFiled);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.warn(e.getMessage(), e);
			map.put("msg", "filed");
			this.insertLog(request, "保存图表数据失败,图表ID为："+reportChartFiled);
		}
		return new ModelAndView("/common/save_result", map);
	}

	/**
	 * 保存拖拉图表数据 高度、宽度等
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/saveSlowChart")
	public void saveSlowChart(HttpServletRequest request,
			HttpServletResponse response) {
		ReportChartFiled reportChartFiled = new ReportChartFiled();
		String chartId = request.getParameter("chartId");
		String left = request.getParameter("left");
		String top = request.getParameter("top");
		String width = request.getParameter("width");
		String height = request.getParameter("height");
		if (chartId == null || "".equals(chartId)) {
			this.logger.warn("保存图表出错：chartId为空");
			return;
		}
		if(!StringUtils.notNullAndSpace(left)){
			this.logger.warn("left值为空");
			left="0";
		}
		if(!StringUtils.notNullAndSpace(top)){
			this.logger.warn("top值为空");
            top="0";			
		}
		if(!StringUtils.notNullAndSpace(width)){
			this.logger.warn("width值为空");
			return;
		}
		if(!StringUtils.notNullAndSpace(height)){
			this.logger.warn("height值为空");
			return;
		}
		reportChartFiled.setChartId(Integer.parseInt(chartId));
		reportChartFiled.setLeft(Integer.parseInt(left));
		reportChartFiled.setTop(Integer.parseInt(top));
		reportChartFiled.setWidth(Integer.parseInt(width));
		reportChartFiled.setHeight(Integer.parseInt(height));
		try {
			reportChartService.updateChartFiled(reportChartFiled);
			this.insertLog(request, "保存拖拉图表数据 高度、宽度.图表ID为："+reportChartFiled);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.warn(e.getMessage(), e);
		}
	}

	/**
	 * 图表拖拉设计
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/designReportChart")
	public ModelAndView getAllReportChart(HttpServletRequest request,
			HttpServletResponse response) {
		String reportId = request.getParameter("reportId");
		if (reportId == null || "".equals(reportId)) {
			this.logger.warn("reportId为空");
			return null;
		}
		List<ReportChartFiled> reportChartFiledList = reportChartService
				.getAllReportChart(Integer.parseInt(reportId));
		Map map = new HashMap();
		map.put("reportId", reportId);
		map.put("reportChartFiledList", reportChartFiledList);
		this.insertLog(request, "图表拖拉设计");
		return new ModelAndView("/reportChart/designReportChart", map);
	}

	/**
	 * 图表拖拉图片浏览
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/imgReportChart")
	public ModelAndView imgReportChart(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		String reportId = request.getParameter("reportId");
		if (reportId == null || "".equals(reportId)) {
			this.logger.warn("reportId为空");
			return null;
		}
		List<ReportChartFiled> reportFieldList = reportChartService
				.getAllReportChart(Integer.parseInt(reportId));
		map.put("reportId", reportId);
		map.put("reportFieldList", reportFieldList);
		this.insertLog(request, "图表拖拉图片浏览");
		return new ModelAndView("/reportChart/imgReportChart", map);
	}

	/**
	 * 删除图表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/delReportChart")
	public void delReportChart(HttpServletRequest request,
			HttpServletResponse response) {
		String chartId = request.getParameter("chartId");
		if (chartId == null || "".equals(chartId)) {
			this.logger.warn("chartId为空");
			this.sendMsgToClient("failed", response);
		} else {
			try {
				reportChartService.deleteChartFiled(Integer.parseInt(chartId));
				this.sendMsgToClient("success", response);
				this.insertLog(request, "删除图表成功，图表ID为："+chartId);
			} catch (Exception e) {
				e.printStackTrace();
				this.logger.warn(e.getMessage(), e);
				this.sendMsgToClient("failed", response);
				this.insertLog(request, "删除图表失败，图表ID为："+chartId);
			}
		}
	}

	/**
	 * 新增图表
	 * 
	 * @param request
	 * @param response
	 * @param reportChartFiled
	 * @return
	 */
	@RequestMapping("/addReportChart")
	public ModelAndView addReportChart(HttpServletRequest request,
			HttpServletResponse response, ReportChartFiled reportChartFiled) {
		Map map = new HashMap();
		try {
			reportChartService.insertChartFiled(reportChartFiled);
			map.put("msg", "success");
			map.put("reportChartId", reportChartFiled.getChartId());
			map.put("widgh", reportChartFiled.getWidth());
			map.put("height", reportChartFiled.getHeight());
			this.insertLog(request, "新增图表成功，图表ID为："+reportChartFiled.getChartId());
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.warn(e.getMessage(), e);
			map.put("msg", "filed");
			this.insertLog(request, "删除图表失败，图表ID为："+reportChartFiled.getChartId());
		}
		return new ModelAndView("/common/reportChartResult", map);
	}

	/**
	 * 图表字段配置
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/slowChartConfig")
	public ModelAndView slowChartConfig(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		String reportId = request.getParameter("reportId");
		String left = request.getParameter("left");
		String top = request.getParameter("top");
		String width = request.getParameter("width");
		String height = request.getParameter("height");
		String chartId = request.getParameter("chartId");
		map.put("left", left);
		map.put("top", top);
		map.put("width", width);
		map.put("height", height);
		map.put("reportId", reportId);
		if (chartId == null || "".equals(chartId)) {
			this.logger.warn("chartId为空");
			return null;
		}
		ReportChartFiled reportChartFiled = reportChartService
				.getIsSelOrNotTableFiled(Integer.parseInt(chartId));
		map.put("reportChartFiled", reportChartFiled);
		this.insertLog(request, "图表字段配置,图表ID为："+chartId);
		return new ModelAndView("/reportChart/slowChartConfig", map);
	}

	/**
	 * 校验添加的图表是否配置字段
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/chartChartConfig")
	public void chartChartConfig(HttpServletRequest request,
			HttpServletResponse response) {
		String reportId = request.getParameter("reportId");
		if (!StringUtils.notNullAndSpace(reportId)) {
			this.logger.warn("校验图表字段是否配置出错：reportId为空");
			this.responseJsonMsg("校验失败：reportId为空", response);
			return;
		}
		List<ReportChartFiled> reportChartList = reportChartService
				.getAllReportChart(Integer.parseInt(reportId));
		if (reportChartList != null && reportChartList.size() > 0) {
			for (ReportChartFiled reportChartFiled : reportChartList) {
				if (!StringUtils.notNullAndSpace(reportChartFiled.getxFields())
						|| !StringUtils.notNullAndSpace(reportChartFiled
								.getFields())) {
					if (reportChartFiled.getChartType() == 1) {
						this.sendMsgToClient("饼图图表未配置字段，请配置", response);
					} else if (reportChartFiled.getChartType() == 2) {
						this.sendMsgToClient("曲线图表未配置字段，请配置", response);
					} else if (reportChartFiled.getChartType() == 3) {
						this.sendMsgToClient("柱状图表未配置字段，请配置", response);
					} else if (reportChartFiled.getChartType() == 4) {
						this.sendMsgToClient("折线图表未配置字段，请配置", response);
					} else if (reportChartFiled.getChartType() == 5) {
						this.sendMsgToClient("组合图表未配置字段，请配置", response);
					}
					break;
				}
			}
		}
		this.sendMsgToClient("ok", response);
	}
}
