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
import com.infosmart.portal.service.dwmis.DwmisPieService;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.vo.PieData;

@Controller
public class DwmisPieChartController extends BaseController {

	@Autowired
	private DwmisPieService dwmisPieService;

	@RequestMapping("/DwmisPieChart/PieChartByOneKpiCode")
	public void pieChart(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.logger.info("开始生成饼图《瞭望塔》");
		// 指标编码 以 “；”分隔
		String kpiCode = request.getParameter("kpiCode");
		// 日期类型
		String dateType = request.getParameter("dateType");
		// 统计方式
		String staCode = request.getParameter("staCode");
		// 查询日期
		// 查询日期
		String queryDate = request.getParameter("queryDate");
		if (StringUtils.notNullAndSpace(queryDate)) {
			this.setCrtQueryDateOfDwmis(request, queryDate);
		} else {
			queryDate = this.getCrtQueryDateOfDwmis(request);
		}
		// 取颜色
		List<String> colorList = Constants.CHART_COLOR_LIST;
		String color = request.getParameter("color");
		if (StringUtils.notNullAndSpace(color)) {
			colorList = Arrays.asList(color.split(";"));
		}
		String[] codeList = kpiCode.split(";");

		// Date queryDate = DateUtils.parseByFormatRule(date, "yyyy-MM-dd");
		// 饼图数据
		List<PieData> pieDataList = dwmisPieService.getDwmisPieDateByCodeList(
				Arrays.asList(codeList), colorList, Integer.valueOf(dateType),
				Integer.valueOf(staCode), queryDate);

		List<PieData> tempList = new ArrayList<PieData>();
		if (pieDataList != null && pieDataList.size() > 0) {
			for (PieData pieData : pieDataList) {
				pieData.setTitle(pieData.getTitle() + ":"+pieData.getValue());
				tempList.add(pieData);
			}
		}
		String path = request.getSession().getServletContext().getRealPath("/");
		try {
			// 初始化并取得Velocity引擎
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, path
					+ Constants.VM_FILE_PATH + "/dwmis/");
			ve.init(properties);

			Template template = ve.getTemplate("dwmis_pie_data.vm", "UTF-8");
			// 取得velocity的上下文context
			VelocityContext context = new VelocityContext();

			// 把数据填入上下文
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
