package com.infosmart.portal.action;

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

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasStKpiData;
import com.infosmart.portal.service.PieChartGeneratorService;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.vo.PieData;

/**
 * 生成餅圖
 * 
 * @author infosmart
 * 
 */
@Controller
public class PieChartController extends BaseController {

	@Autowired
	private PieChartGeneratorService pieChartGeneratorService;

	/**
	 * 显示饼图
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/PieChart/showPieChart")
	public void showPieChart(HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.info("开始生成饼图");
		// 指标编码，如果有多个，以";"分隔
		String kpiCode = request.getParameter("kpiCode");
		if (!StringUtils.notNullAndSpace(kpiCode)) {
			this.logger.warn("生成饼图失败，指标编码为空");
			return;
		}
		// 报表日期
		String reportDate = request.getParameter("reportDate");
		if (!StringUtils.notNullAndSpace(reportDate)) {
			reportDate = this.getCrtQueryDateOfReport(request);
		}
		// 报表类型 M/W/D
		String dateType = request.getParameter("dateType");
		if (!StringUtils.notNullAndSpace(dateType)) {
			// 默认为D
			dateType = DwpasStKpiData.DATE_TYPE_OF_DAY;
		}
		// 查询饼图数据
		List<String> colorList = Constants.CHART_COLOR_LIST;
		// 饼图颜色,多个以“;”分割
		String color = request.getParameter("color");
		if (StringUtils.notNullAndSpace(color)) {
			colorList = Arrays.asList(color.split(";"));
		}
		List<PieData> pieDataList = this.pieChartGeneratorService.getPieDate(
				Arrays.asList(kpiCode.split(";")), colorList, reportDate,
				dateType);
		List<PieData> tempList = new ArrayList<PieData>();
		if (pieDataList != null && pieDataList.size() > 0) {
			this.logger.info("生成饼图数据");
			for (PieData pieData : pieDataList) {
				pieData.setTitle(pieData.getTitle() + ":");
				tempList.add(pieData);
			}
		} else {
			this.logger.info("--->没有生成饼图数据");
			List<DwpasCKpiInfo> kpiInfoList = this.dwpasCKpiInfoService
					.listDwpasCKpiInfoByCodes(Arrays.asList(kpiCode.split(";")));
			if (kpiInfoList != null && kpiInfoList.size() > 0) {
				int i = 0;
				for (DwpasCKpiInfo kpiInfo : kpiInfoList) {
					PieData pieData = new PieData();
					pieData.setTitle(kpiInfo.getKpiName() + ":");
					pieData.setValue("0");
					pieData.setKpicode(kpiInfo.getKpiCode());
					pieData.setColor(colorList.get(i));
					//
					tempList.add(pieData);
					i++;
				}
			}
		}

		String path = request.getSession().getServletContext().getRealPath("/");
		try {
			// 初始化并取得Velocity引擎
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			// properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH,cfgMap.get("templatePath")+"/prodana/screen/amchartDataVM");
			properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, path
					+ Constants.VM_FILE_PATH);
			ve.init(properties);

			// 取得velocity的模版
			// ve.setProperty(Velocity.RESOURCE_LOADER, "file");
			// ve.setProperty("file.resource.loader.class",
			// "org.apache.velocity.runtime.resource.loader.FileResourceLoader");

			Template template = ve.getTemplate("commonPieData.vm", "UTF-8");
			// 取得velocity的上下文context
			VelocityContext context = new VelocityContext();

			// 把数据填入上下文
			this.logger
					.info("饼图数据:" + (tempList == null ? 0 : tempList.size()));
			context.put("pieDataList", tempList);
			context.put("dateType", dateType);

			// 输出流
			StringWriter writer = new StringWriter();

			// 转换输出
			template.merge(context, writer);
			response.setContentType("text/xml;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(writer.toString());
			out.flush();
			this.logger.info("结束生成饼图...");
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}
}
