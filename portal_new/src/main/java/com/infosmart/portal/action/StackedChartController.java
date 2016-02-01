package com.infosmart.portal.action;

import java.io.PrintWriter;
import java.io.StringWriter;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCPrdInfo;
import com.infosmart.portal.service.DwpasCPrdInfoService;
import com.infosmart.portal.service.StackedChartService;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.vo.Chart;

/**
 * 产品堆积图
 * 
 * @author infosmart
 * 
 */
@Controller
public class StackedChartController extends BaseController {

	@Autowired
	private StackedChartService stackedChartService;

	@Autowired
	private DwpasCPrdInfoService prdInfoService;

	/**
	 * 显示堆积图
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/stackedChart/showStackedChart")
	public void showStackedChart(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		// 考虑到产品可能比较多，页面排版不下，不列出所有的产品堆积图
		String productIds = request.getParameter("productIds");
		String isExport = request.getParameter("isExport");
		this.logger.info("生成产品排行趋势图:" + productIds);
		// 通用指标
		String commKpiCode = request.getParameter("commKpiCode");
		this.logger.info("new通用指标:" + modelMap.get("commKpiCode"));
		// 指标类型
		String kpiType = request.getParameter("kpiType");
		if (!StringUtils.notNullAndSpace(kpiType)) {
			kpiType = String.valueOf(DwpasCKpiInfo.KPI_TYPE_OF_MONTH);
		}
		// 报表日期
		String reportDate = request.getParameter("reportDate");
		List<DwpasCPrdInfo> productInfoList = null;
		if (StringUtils.notNullAndSpace(productIds)) {
			productInfoList = this.prdInfoService.getDwpasCPrdInfoList(
					this.getCrtUserTemplateId(request),
					Arrays.asList(productIds.split(";")));
		} else {
			productInfoList = this.prdInfoService.getDwpasCPrdInfoList(
					this.getCrtUserTemplateId(request), null);
		}
		Chart chart = stackedChartService.getStackedChart(productInfoList,
				commKpiCode, reportDate, Integer.parseInt(kpiType));
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

			Template template = ve.getTemplate("stockChartData.vm", "UTF-8");
			// 取得velocity的上下文context
			VelocityContext context = new VelocityContext();

			// 把数据填入上下文
			context.put("chart", chart);
			context.put("type", kpiType);
			context.put("isExport", isExport);
			context.put("needPercent", "0");
			context.put("hasEvent", "0");
			String showRightMenu = request.getParameter("showRightMenu");
			this.logger.info("是否显示右键菜单:" + showRightMenu);
			if (!StringUtils.notNullAndSpace(showRightMenu))
				showRightMenu = "1";// 默认显示右键菜单
			this.logger.info("showRightMenu:" + showRightMenu);
			context.put("showRightMenu", showRightMenu);// 是否显示右键菜单

			// 输出流
			StringWriter writer = new StringWriter();

			// 转换输出
			template.merge(context, writer);
			response.setContentType("text/xml;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(writer.toString());
			out.flush();
			this.logger.info("生成趋势图<" + chart == null ? "未生成图" : chart
					.getChartName() + ">结束...");
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}
}
