package com.infosmart.portal.action;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.portal.pojo.BigEventPo;
import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.ProkpiAnalyze;
import com.infosmart.portal.service.BigEventService;
import com.infosmart.portal.service.PrdCrossStockChartService;
import com.infosmart.portal.service.StockChartService;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.dwpas.KpiTypeEnum;
import com.infosmart.portal.vo.Chart;
import com.infosmart.portal.vo.ChartParam;
import com.infosmart.portal.vo.dwpas.PrdCrossDataTypeEnum;
import com.infosmart.portal.vo.dwpas.PrdCrossParam;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 生成趋势图
 * 
 * @author infosmart
 * 
 */
@Controller
public class StockChartController extends BaseController {

	@Autowired
	private StockChartService stockChartService;
	@Autowired
	private PrdCrossStockChartService prdCrossStockChartService;
	@Autowired
	private BigEventService bigEventService;

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
	@RequestMapping("/stockChart/showStockChart")
	public void showStockChart(HttpServletRequest request,
			HttpServletResponse response) {
		// 是否需要在趋势图上有3月的按钮
		String isThreeMonth = request.getParameter("isThreeMonth");
		this.logger.info("准备生成趋势图");
		// 是否需要是否归一化
		String needPercent = request.getParameter("needPercent");
		// 是否需要大事件
		String hasEvent = request.getParameter("hasEvent");
		// 默认显示大事记
		if (!StringUtils.notNullAndSpace(hasEvent)) {
			hasEvent = "1";
		}
		// 是否瞭望塔
		String isMIS = request.getParameter("isMIS");
		// 大事件类型
		String eventType = request.getParameter("eventType");
		// 大事件搜索关键字
		String eventSearchKey = request.getParameter("eventSearchKey");
		// 指标类型
		String kpiType = request.getParameter("kpiType");
		// 是否需要显示全部的大事件 isPrd=1只显示绑定指标的大事件
		String isPrd = request.getParameter("isPrd");
		if (!StringUtils.notNullAndSpace(kpiType)) {
			kpiType = String.valueOf(DwpasCKpiInfo.KPI_TYPE_OF_DAY);
		}
		// 查询日期
		String beginDate = request.getParameter("beginDate");
		String reportDate = request.getParameter("reportDate");
		if (!StringUtils.notNullAndSpace(reportDate)) {
			// 如果日期为空,则默认为当天或当月
			if (Integer.parseInt(kpiType) == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
				reportDate = DateUtils.formatUtilDate(new Date(), "yyyyMMdd");
			} else {
				reportDate = DateUtils.formatUtilDate(new Date(), "yyyyMM");
			}
		} else {
			reportDate = StringUtils.replace(reportDate, "-", "");
		}
		if (StringUtils.notNullAndSpace(beginDate)) {
			beginDate = beginDate.replace("-", "");
		}

		// 查询的KPI指标
		String kpiCodes = request.getParameter("kpiCodes");// 以";"分隔
		this.logger.info("NEW------------------>kpiCodes:" + kpiCodes);
		this.logger.info("显示指标的趋势图:" + kpiCodes);
		ChartParam chartParam = new ChartParam(needPercent, hasEvent, isMIS,
				eventType, eventSearchKey, reportDate, kpiCodes.split(";"),
				(String[]) Constants.CHART_COLOR_LIST.toArray(), isPrd);
		// 指标类型
		chartParam.setKpiType(Integer.parseInt(kpiType));
		// 得到图数据
		Chart chart = this.stockChartService.getStockChart(chartParam);
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
			long betweenDay = 0;
			if (StringUtils.notNullAndSpace(beginDate)
					&& StringUtils.notNullAndSpace(reportDate)) {
				Date startDate = DateUtils.parseByFormatRule(beginDate,
						"yyyyMMdd");
				Date endDate = DateUtils.parseByFormatRule(reportDate,
						"yyyyMMdd");
				betweenDay = (endDate.getTime() - startDate.getTime())
						/ (24 * 60 * 60 * 1000);
			}
			context.put("betweenDay", betweenDay);
			// 把数据填入上下文
			context.put("chart", chart);
			// 关联KPI
			context.put("kpiCodes", kpiCodes);
			context.put("kpiType", kpiType);
			if (StringUtils.notNullAndSpace(kpiCodes)
					&& kpiCodes.split(";").length > 0) {
				// 查询指标信息
				DwpasCKpiInfo kpiInfo = this.dwpasCKpiInfoService
						.getDwpasCKpiInfoByCode(kpiCodes.split(";")[0]);
				context.put("type",
						kpiInfo == null ? kpiType : kpiInfo.getKpiType());
			}
			context.put("needPercent", needPercent);
			context.put("hasEvent", hasEvent);
			context.put("isThreeMonth", isThreeMonth);
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
	@RequestMapping("/stockChart/showStockAreaChart")
	public void showStockAreaChart(HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.info("准备生成堆积图");
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
		// 是否需要显示全部的大事件 isPrd=1只显示绑定指标的大事件
		String isPrd = request.getParameter("isPrd");
		// 指标类型
		String kpiType = request.getParameter("kpiType");
		if (!StringUtils.notNullAndSpace(kpiType)) {
			kpiType = String.valueOf(DwpasCKpiInfo.KPI_TYPE_OF_DAY);
		}
		// 查询日期
		String reportDate = request.getParameter("reportDate");
		if (!StringUtils.notNullAndSpace(reportDate)) {
			// 如果日期为空,则默认为当天或当月
			if (Integer.parseInt(kpiType) == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
				reportDate = DateUtils.formatUtilDate(new Date(), "yyyyMMdd");
			} else {
				reportDate = DateUtils.formatUtilDate(new Date(), "yyyyMM");
			}
		} else {
			reportDate = StringUtils.replace(reportDate, "-", "");
		}
		// 查询的KPI指标
		String kpiCodes = request.getParameter("kpiCodes");// 以";"分隔
		this.logger.info("NEW------------------>kpiCodes:" + kpiCodes);
		this.logger.info("显示指标的堆积图:" + kpiCodes);
		ChartParam chartParam = new ChartParam(needPercent, hasEvent, isMIS,
				eventType, eventSearchKey, reportDate, kpiCodes.split(";"),
				(String[]) Constants.CHART_COLOR_LIST.toArray(), isPrd);
		// 指标类型
		chartParam.setKpiType(Integer.parseInt(kpiType));
		// 得到图数据
		Chart chart = this.stockChartService.getStockChart(chartParam);
		// 查询指标信息
		DwpasCKpiInfo kpiInfo = this.dwpasCKpiInfoService
				.getDwpasCKpiInfoByCode(kpiCodes.split(";")[0]);
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

			Template template = ve
					.getTemplate("stockAreaChartData.vm", "UTF-8");
			// 取得velocity的上下文context
			VelocityContext context = new VelocityContext();

			// 把数据填入上下文
			context.put("chart", chart);
			context.put("type",
					kpiInfo == null ? kpiType : kpiInfo.getKpiType());
			context.put("needPercent", needPercent);
			String showRightMenu = request.getParameter("showRightMenu");
			this.logger.info("是否显示右键菜单:" + showRightMenu);
			if (!StringUtils.notNullAndSpace(showRightMenu))
				showRightMenu = "1";// 默认显示右键菜单
			this.logger.info("showRightMenu:" + showRightMenu);
			context.put("showRightMenu", showRightMenu);// 是否显示右键菜单
			context.put("hasEvent", hasEvent);
			// 关联KPI
			context.put("kpiCodes", kpiCodes);
			context.put("kpiType", kpiType);
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
	 * 场景交叉趋势图
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/stockChart/getPrdCrossStock")
	public void getPrdCrossStock(HttpServletRequest request,
			HttpServletResponse response) {
		String prdId = request.getParameter("prdId");
		String relPrdId = request.getParameter("relPrdId");
		String dataType = request.getParameter("dataType");
		String endDate = request.getParameter("endDate");
		setStockResponseHeader(response);
		PrdCrossParam prdCrossParam = new PrdCrossParam();
		prdCrossParam.setEndDate(endDate);
		prdCrossParam.setPrdId(prdId);
		prdCrossParam.setRelPrdId(relPrdId);
		if (PrdCrossDataTypeEnum.CROSS_USER_CNT.getCode().equals(dataType)) {
			prdCrossParam
					.setPrdCrossDataTypeEnum(PrdCrossDataTypeEnum.CROSS_USER_CNT);
		} else if (PrdCrossDataTypeEnum.CROSS_USER_RATE.getCode().equals(
				dataType)) {
			prdCrossParam
					.setPrdCrossDataTypeEnum(PrdCrossDataTypeEnum.CROSS_USER_RATE);
		}

		// 获得趋势图的指标趋势数据
		Chart chart = prdCrossStockChartService.getStockChart(prdCrossParam);
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
			if (PrdCrossDataTypeEnum.CROSS_USER_RATE.getCode().equals(dataType)) {
				context.put("unitMark", Boolean.TRUE);
			}
			context.put("chart", chart);
			context.put("type", KpiTypeEnum.KPI_TYPE_MONTH.getCode());
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
			this.logger.info("生成趋势图结束...");
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}

	private void setStockResponseHeader(HttpServletResponse response) {
		response.setHeader("Pragma", "cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "cache");
	}

	/**
	 * 双击大事件，显示详细数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/stockChart/getDetailEvent")
	public ModelAndView getDetailEvent(HttpServletRequest request,
			HttpServletResponse response) {
		String eventId = request.getParameter("eventId");
		BigEventPo bigEventPo = new BigEventPo();
		if (!StringUtils.notNullAndSpace(eventId)) {
			this.logger.warn("获取大事件详细数据出错：eventId为空");
			bigEventPo.setContent("获取大事件详细数据出错：eventId为空");
		} else {
			bigEventPo = this.bigEventService.getMISEventById(eventId);
		}
		Map map = new HashMap();
		map.put("bigEvent", bigEventPo);
		return new ModelAndView("/common/detailEvent", map);
	}

	/**
	 * 导出为图片
	 * 
	 * @param request
	 * @param response
	 * @param file
	 */
	@RequestMapping("/stockChart/exportChart")
	public void exportChartImg(HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.info("导出为图片.....");
		// 页面flash的宽度和高度
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		BufferedImage result = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 页面是将一个个像素作为参数传递进来的,所以如果图表越大,处理时间越长
		for (int y = 0; y < height; y++) {
			int x = 0;
			String[] row = request.getParameter("r" + y).split(",");
			for (int c = 0; c < row.length; c++) {
				String[] pixel = row[c].split(":"); // 十六进制颜色数组
				int repeat = pixel.length > 1 ? Integer.parseInt(pixel[1]) : 1;
				for (int l = 0; l < repeat; l++) {
					result.setRGB(x, y, Integer.parseInt(pixel[0], 16));
					x++;
				}
			}
		}
		response.setContentType("image/jpeg");
		response.addHeader("Content-Disposition",
				"attachment; filename=\"amchart.jpg\"");
		Graphics2D g = result.createGraphics();
		// 处理图形平滑度
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(result, 0, 0, width, height, null);
		g.dispose();
		ServletOutputStream imgFile;
		try {
			imgFile = response.getOutputStream();
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(imgFile);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(result);
			param.setQuality((float) (100 / 100.0), true);
			// 设置图片质量,100最大,默认70
			encoder.encode(result, param);
			ImageIO.write(result, "JPEG", response.getOutputStream());
			// 输出图片
			imgFile.flush();
			imgFile.close();
		} catch (IOException e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 趋势图（多个一起显示）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/stockChart/showMultiStockChart")
	public void showMultiStockChart(HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.info("准备生成堆积图");
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
		// 指标类型
		String kpiType = request.getParameter("kpiType");
		// 是否显示大事件
		String isPrd = request.getParameter("isPrd");
		if (!StringUtils.notNullAndSpace(kpiType)) {
			kpiType = String.valueOf(DwpasCKpiInfo.KPI_TYPE_OF_DAY);
		}
		// 查询日期
		String beginDate = request.getParameter("beginDate");
		String reportDate = request.getParameter("reportDate");
		if (!StringUtils.notNullAndSpace(reportDate)) {
			// 如果日期为空,则默认为当天或当月
			if (Integer.parseInt(kpiType) == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
				reportDate = DateUtils.formatUtilDate(new Date(), "yyyyMMdd");
			} else {
				reportDate = DateUtils.formatUtilDate(new Date(), "yyyyMM");
			}
		} else {
			reportDate = StringUtils.replace(reportDate, "-", "");
		}
		// 查询的KPI指标
		String kpiCodes = request.getParameter("kpiCodes");// 以";"分隔
		this.logger.info("NEW------------------>kpiCodes:" + kpiCodes);
		this.logger.info("显示指标的堆积图:" + kpiCodes);
		ChartParam chartParam = new ChartParam(needPercent, hasEvent, isMIS,
				eventType, eventSearchKey, reportDate, kpiCodes.split(";"),
				(String[]) Constants.CHART_COLOR_LIST.toArray(), isPrd);
		// 指标类型
		chartParam.setKpiType(Integer.parseInt(kpiType));
		// 得到图数据
		Chart chart = this.stockChartService.getStockChart(chartParam);
		// 查询指标信息
		DwpasCKpiInfo kpiInfo = this.dwpasCKpiInfoService
				.getDwpasCKpiInfoByCode(kpiCodes.split(";")[0]);
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

			Template template = ve.getTemplate("stockMultiChartData.vm",
					"UTF-8");
			// 取得velocity的上下文context
			VelocityContext context = new VelocityContext();

			// 把数据填入上下文
			context.put("chart", chart);
			// 关联KPI
			context.put("kpiCodes", kpiCodes);
			context.put("kpiType", kpiType);
			context.put("type",
					kpiInfo == null ? kpiType : kpiInfo.getKpiType());
			context.put("needPercent", needPercent);
			context.put("hasEvent", hasEvent);
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
			this.logger.info("生成堆积图结束...");
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 右键查看详细数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/stockChart/getDetailData")
	public ModelAndView getDetailData(HttpServletRequest request,
			HttpServletResponse response) {
		String kpiType = request.getParameter("kpiType");
		String kpiCodes = request.getParameter("kpiCodes");
		String queryDate = "";
		// 开始时间
		String beginDate = "";
		if (Integer.valueOf(kpiType) == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
			queryDate = this.getCrtQueryDateOfReport(request);
			DateFormat format = new SimpleDateFormat("yyyyMMdd");
			Date dd = null;
			try {
				dd = format.parse(queryDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dd);
			calendar.add(Calendar.DAY_OF_MONTH, -100);
			beginDate = format.format(calendar.getTime());
		} else {
			queryDate = this.getCrtQueryMonthOfReport(request);
		}
		String tansKpis[] = kpiCodes.split(";");

		List<ProkpiAnalyze> showHistries = dwpasStKpiDataService.getHistory(
				Arrays.asList(tansKpis), kpiType, beginDate, queryDate);
		List<String> kpiNames = new ArrayList<String>();
		if (null != showHistries && showHistries.size() > 0) {
			kpiNames.add("日期");
			kpiNames.addAll(showHistries.get(0).getKpiNames());
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("kpiType", kpiType);
		mv.addObject("kpiCodes", kpiCodes);
		mv.addObject("kpiNames", kpiNames);
		mv.addObject("showHistries", showHistries);
		mv.setViewName("/common/detailData");
		return mv;
	}
}
