package com.infosmart.portal.action;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCPrdInfo;
import com.infosmart.portal.pojo.DwpasCSystemMenu;
import com.infosmart.portal.pojo.DwpasCUserFeature;
import com.infosmart.portal.pojo.DwpasStUserFeatureDs;
import com.infosmart.portal.pojo.KpiNameUnitRelation;
import com.infosmart.portal.pojo.ProdKpiInfoDTO;
import com.infosmart.portal.service.DwpasPageSettingService;
import com.infosmart.portal.service.DwpasStUserFeatureDsService;
import com.infosmart.portal.util.Const;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.MapConst;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.vo.Chart;
import com.infosmart.portal.vo.ColumnDataGraph;
import com.infosmart.portal.vo.GraphDataElement;

/**
 * 用户特征
 * 
 * @author infosmart
 * 
 */
@Controller
public class UserFeatureController extends BaseController {
	// 用户特征管理
	@Autowired
	private DwpasStUserFeatureDsService dwpasStUserFeatureDsService;
	@Autowired
	private DwpasPageSettingService pageSettingService;

	/**
	 * 显示用户特征分布图
	 */
	@RequestMapping("/userFeature/showUserFeatureChart")
	public void showUserFeatureChart(HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.info("某产品的显示用户特征分布图");
		// 产品ID,如果有多个，以";"分隔
		String productIds = request.getParameter("productId");
		if (!StringUtils.notNullAndSpace(productIds)) {
			this.logger.warn("显示用户特征分布图失败：产品ID为空");
			return;
		}
		// 报表日期
		String reportDate = request.getParameter("reportDate");
		if (!StringUtils.notNullAndSpace(reportDate)) {
			// 默认取内存的值
			reportDate = this.getCrtQueryMonthOfReport(request);
		}
		// 特征类型：1-使用次数，2-时间偏好，3-年龄段，4-地区，等
		String featureType = request.getParameter("featureType");//
		// 报表类型 为2时，矩形上加线
		String showfor = request.getParameter("showfor");
		this.logger.info("showfor:" + showfor);
		// column;line 如果有多个，以逗号分隔
		String types = request.getParameter("types");
		String productName = "";
		if (!StringUtils.notNullAndSpace(featureType)) {
			// 默认是使用次数
			featureType = String
					.valueOf(DwpasCUserFeature.FEATURE_TYPE_OF_USE_FREQUENCY);
		}
		// 查询产品信息
		DwpasCPrdInfo dwpasCPrdInfo = null;
		// 用户特征数据
		List<DwpasStUserFeatureDs> userFeatureDsList = null;
		// 坐标数据
		Chart chart = new Chart();
		// X坐标
		GraphDataElement graphData = null;
		List<GraphDataElement> graphDataList = null;
		ColumnDataGraph columnGraph = null;
		// 坐标信息
		List<ColumnDataGraph> columnGraphList = new ArrayList<ColumnDataGraph>();
		int i = 0;
		List<String> headList = new ArrayList<String>();
		List<List<String>> dataList = new ArrayList<List<String>>();
		if(featureType.equals("1")){
			headList.add("使用次数");
			request.getSession().setAttribute("excelHeadInfo_user", headList);
			request.getSession().setAttribute("excelDataList_user", dataList);
		}else if(featureType.equals("2")){
			headList.add("时间偏好");
			request.getSession().setAttribute("excelHeadInfo_time", headList);
			request.getSession().setAttribute("excelDataList_time", dataList);
		}else if(featureType.equals("5")){
			headList.add("性别");
			request.getSession().setAttribute("excelHeadInfo_sex", headList);
			request.getSession().setAttribute("excelDataList_sex", dataList);
		}else if(featureType.equals("3")){
			headList.add("年龄");
			request.getSession().setAttribute("excelHeadInfo_age", headList);
			request.getSession().setAttribute("excelDataList_age", dataList);
		}
		
		for (String productId : productIds.split(";")) {
			// 查询产品信息
			dwpasCPrdInfo = this.dwpasCPrdInfoService
					.getDwpasCPrdInfoByProductId(productId);
			// 查询用户特征数据
			userFeatureDsList = this.dwpasStUserFeatureDsService
					.listDwpasStUserFeatureDs(productId, reportDate,
							Integer.parseInt(featureType));
			headList.add(dwpasCPrdInfo.getProductName()+"用户数(万人)");
			if (userFeatureDsList == null || userFeatureDsList.isEmpty())
				continue;
			// X坐标数据
			graphDataList = new ArrayList<GraphDataElement>();
			// 封装矩形图形参数
			columnGraph = new ColumnDataGraph();
			// ID
			columnGraph.setId(productId);
			// 颜色
			columnGraph.setColor(Constants.CHART_COLOR_LIST.get(i));
			// 产品ID
			columnGraph.setKpicode(productId);
			// 产品名称
			if (dwpasCPrdInfo != null) {
				columnGraph.setKpiname(dwpasCPrdInfo.getProductName());
			}
			columnGraph.setUnit("");
			if (StringUtils.notNullAndSpace(types)) {
				columnGraph.setType(types.split(";")[i]);
			} else {
				columnGraph.setColor("column");
			}
			//
			columnGraphList.add(columnGraph);
			// 生成矩形X坐标
			for (DwpasStUserFeatureDs dwpasStUserFeatureDs : userFeatureDsList) {
				// X坐标
				graphData = new GraphDataElement();
				// 设置ID
				graphData.setValueDate(dwpasStUserFeatureDs.getFeatureName());
				// 设置Y坐标数据
				graphData.setValue(new BigDecimal(dwpasStUserFeatureDs
						.getUserCnt()).divide(new BigDecimal("10000.00"), 2, 4)
						.toString());
				// 图形X坐标增加数据
				graphDataList.add(graphData);
				List<String> rowDatas = new ArrayList<String>();
				if (i == 0) {
					// 生成X坐标各矩形名称
					chart.getSeriesMap().put(
							dwpasStUserFeatureDs.getFeatureName(),
							dwpasStUserFeatureDs.getFeatureName());
					rowDatas.add(dwpasStUserFeatureDs.getFeatureName());
					rowDatas.add(new BigDecimal(dwpasStUserFeatureDs.getUserCnt()).divide(new BigDecimal("10000.00"), 2, 4).toString());
				}
				dataList.add(rowDatas);
			}
			chart.addGraphs(productId, graphDataList);
			//
			i++;
		}
		
		try {
			// 初始化并取得Velocity引擎
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			// properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH,cfgMap.get("templatePath")+"/prodana/screen/amchartDataVM");
			properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, request
					.getSession().getServletContext().getRealPath("/")
					+ Constants.VM_FILE_PATH);
			ve.init(properties);
			Template template = null;
			if ("2".equals(showfor)) {
				this.logger.info("VM_FILE_NAME:productUsingCharLineData");
				template = ve.getTemplate("productUsingCharLineData.vm",
						"UTF-8");
			} else {
				this.logger.info("VM_FILE_NAME:productUsingCharChartData");
				template = ve.getTemplate("productUsingCharChartData.vm",
						"UTF-8");
			}
			// 取得velocity的上下文context
			VelocityContext context = new VelocityContext();
			// 把数据填入上下文
			context.put("columnGraphList", columnGraphList);
			// 图片信息
			context.put("chart", chart);
			// 用户特征类型
			context.put("featureType", featureType);
			// 输出流
			StringWriter writer = new StringWriter();
			// 转换输出
			template.merge(context, writer);
			response.setContentType("text/xml;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(writer.toString());
			out.flush();
			this.logger.info("结束生成某产品的显示用户特征分布矩形图...");
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 用户特征
	 */
	@RequestMapping("/userFeature/PrepareUserFeature")
	public ModelAndView PrepareUserFeature(HttpServletRequest request,
			HttpServletResponse response) {
		String menuId = request.getParameter("menuId");// 菜单ID
		DwpasCSystemMenu parentMenu = pageSettingService
				.getDwpasCSystemByChildMenuId(menuId);// 父菜单信息
		String productId = request.getParameter("productId");
		if (StringUtils.notNullAndSpace(productId)) {
			// 保存在session中
			this.setCrtProductIdOfReport(request, productId);
		} else {
			// 默认从session中取
			productId = this.getCrtProductIdOfReport(request);
			if (!StringUtils.notNullAndSpace(productId)) {
				return new ModelAndView("/common/noProduct");
			}
		}
		// 默认按月查询
		int kpiType = DwpasCKpiInfo.KPI_TYPE_OF_MONTH;
		String queryMonth = request.getParameter("queryMonth");// 日期格式为yyyy-MM
		if (!StringUtils.notNullAndSpace(queryMonth)) {
			queryMonth = this.getCrtQueryMonthOfReport(request);// 默认取session中的
			this.logger.info("默认取SESSION查询年月值:" + queryMonth);
		} else {
			this.setCrtQueryMonthOfReport(request, queryMonth);
		}
		// 查询产品信息和全彩种信息
		DwpasCPrdInfo productInfo = this.dwpasCPrdInfoService
				.getDwpasCPrdInfoByProductId(productId);
		DwpasCPrdInfo productDpInfo = this.dwpasCPrdInfoService
				.getDwpasCPrdInfoByProductId(Const.DA_PAN);
		if (productDpInfo == null) {
			productDpInfo = new DwpasCPrdInfo();
			productDpInfo.setProductName("大盘");
		} else if (StringUtils.notNullAndSpace(productDpInfo.getProductName())) {
			productDpInfo.setProductName("大盘");
		}
		Map<String, String> realCodeMap = this.dwpasStUserFeatureDsService
				.queryRealKpiCodeMap(productId, kpiType, menuId);

		// 以下为地图准备数据
		List<String> prodIDs = new ArrayList<String>();
		prodIDs.add(productId);
		List<DwpasStUserFeatureDs> sflist = dwpasStUserFeatureDsService
				.getAmmapDate(productId,
						StringUtils.replace(queryMonth, "-", ""), "4");
		// 当前菜单的所有模块信息
		Map<String, Object> moduleInfoMap = pageSettingService
				.getModuleInfoAndColumnInfoByMenuIdAndDateType(menuId, kpiType);
		Map modelMap = new HashMap();
		modelMap.put("prodInfo", productInfo);
		modelMap.put("queryRealKpiCode", realCodeMap);
		// 产品大盘编码2000
		modelMap.put("productDp", productDpInfo);
		modelMap.put("mapdata", sflist);
		modelMap.put("date", queryMonth);
		modelMap.put("enddate", StringUtils.replace(queryMonth, "-", ""));
		modelMap.put("menuId", menuId);
		modelMap.put("parentMenu", parentMenu);
		modelMap.put("modulemap", moduleInfoMap);
		return new ModelAndView("/UserFeature/UserFeature", modelMap);
	}

	/**
	 * ammap中国地图
	 */
	@RequestMapping("/userFeature/showAmmap")
	public void showAmmap(HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.info("加载各份省的销量及用户地图数据");
		String productId = request.getParameter("productId");// 产品ID
		String reportDate = request.getParameter("reportDate");// 报表日期
		List<DwpasStUserFeatureDs> userFeatureDataList = dwpasStUserFeatureDsService
				.getAmmapDate(productId, reportDate, "4");
		if (userFeatureDataList != null && userFeatureDataList.size() > 0) {
			request.getSession().setAttribute("areaDataList", userFeatureDataList);
			// 地区颜色（根据比较显示深浅）
			double maxValue = userFeatureDataList.get(0).getUserPercent();
			double minValue = userFeatureDataList.get(
					userFeatureDataList.size() - 1).getUserPercent();
			for (DwpasStUserFeatureDs userFeature : userFeatureDataList) {
				// 编码(根据ID取编码和名称)
				if (MapConst.CHINA_AREA_CODE_MAP.get(String.valueOf(userFeature
						.getFeatureId())) != null) {
					userFeature.setFeatureCode(MapConst.CHINA_AREA_CODE_MAP
							.get(String.valueOf(userFeature.getFeatureId()))
							.getAreaCode());
					userFeature.setFeatureName(MapConst.CHINA_AREA_CODE_MAP
							.get(String.valueOf(userFeature.getFeatureId()))
							.getAreaName());
				}
				// 颜色
				userFeature.setAreaMapColor(this.getMapColor(
						userFeature.getUserPercent(), maxValue, minValue));
			}
		} else {
			userFeatureDataList = new ArrayList<DwpasStUserFeatureDs>();
		}
		try {
			// 初始化并取得Velocity引擎
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, request
					.getSession().getServletContext().getRealPath("/")
					+ Constants.VM_FILE_PATH);
			ve.init(properties);
			Template template = null;

			template = ve.getTemplate("amChinaMap.vm", "UTF-8");
			// 取得velocity的上下文context
			VelocityContext context = new VelocityContext();
			// 把数据填入上下文
			context.put("userFeatureDataList", userFeatureDataList);
			StringWriter writer = new StringWriter();
			// 转换输出
			template.merge(context, writer);
			response.setContentType("text/xml;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(writer.toString());
			out.flush();
			this.logger.info("结束生成某产品的显示地区特征中国地图......");
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}
	/**
	 * 地区分布图 EXCEL导出
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/userFeature/areaExcelDown")
	public ModelAndView excelDown(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> excelHeadInfo = new ArrayList<String>();
		List<List<String>> excelDataList = new ArrayList<List<String>>();
		List<DwpasStUserFeatureDs> dataList= (List<DwpasStUserFeatureDs>) request.getSession().getAttribute("areaDataList");
		String productName = request.getParameter("productName");
		if(StringUtils.notNullAndSpace(productName)){
			try {
				productName = java.net.URLDecoder.decode(productName, "UTF-8");
				productName = new String(productName.getBytes("ISO-8859-1"),"UTF-8");
			} catch (Exception e) {

			}
		}
		String reportDate = request.getParameter("reportDate");
		if(dataList!= null && dataList.size()>0){
			excelHeadInfo.add("省份");
			excelHeadInfo.add("会员数");
			excelHeadInfo.add("占比");
			excelHeadInfo.add("排名");
			excelHeadInfo.add("销量");
			for(int i=0; i<dataList.size(); i++){
				List<String> dataInfos = new ArrayList<String>();
				dataInfos.add(dataList.get(i).getFeatureName());
				dataInfos.add(dataList.get(i).getUserCnt().toString());
				dataInfos.add(dataList.get(i).getUfPercent());
				dataInfos.add(dataList.get(i).getRanking().toString());
				dataInfos.add(String.valueOf(dataList.get(i).getSalesVolume()));
				excelDataList.add(dataInfos);
			}
			map.put("excelHeadInfo", excelHeadInfo);
			map.put("excelData", excelDataList);
		}
		map.put("fileName", productName+"地区分布"+"_"+reportDate);
		return new ModelAndView(new ExcelDownloadController(), map);
	}

	private String getMapColor(double value, double max, double min) {
		if (value < 0)
			value = 0;
		double userRate = this.getPercentValue(new BigDecimal(value),
				new BigDecimal(max), new BigDecimal(min)) / 100;
		// ///////////////////////////////
		String r, g, b;
		r = Integer.toHexString(
				new BigDecimal(245 * (1 - userRate)).setScale(0,
						BigDecimal.ROUND_HALF_UP).intValue()).toUpperCase();
		g = Integer.toHexString(
				new BigDecimal(245 * (1 - userRate)).setScale(0,
						BigDecimal.ROUND_HALF_UP).intValue()).toUpperCase();
		b = Integer.toHexString(
				new BigDecimal(255).setScale(0, BigDecimal.ROUND_HALF_UP)
						.intValue()).toUpperCase();
		r = r.length() == 1 ? "0" + r : r;
		g = g.length() == 1 ? "0" + g : g;
		b = b.length() == 1 ? "0" + b : b;
		return "#" + r + g + b;
	}

	/**
	 * 作归一化处理
	 * 
	 * @param value
	 * @param max
	 * @param min
	 * @return
	 */
	private double getPercentValue(BigDecimal value, BigDecimal max,
			BigDecimal min) {
		try {
			if (max.compareTo(min) < 0) {
				return 0.00;
			} else {
				return value
						.subtract(min)
						.divide(max.subtract(min), 4,
								BigDecimal.ROUND_HALF_EVEN)
						.multiply(new BigDecimal(100)).doubleValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * column;line图
	 */
	@RequestMapping("/userFeature/showColumnLine")
	public void showColumnLine(HttpServletRequest request,
			HttpServletResponse response) {
		String productId = request.getParameter("productId");
		String featureType = request.getParameter("featureType");
		response.setHeader("Pragma", "cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "cache");
		DwpasCPrdInfo prod = dwpasCPrdInfoService
				.getDwpasCPrdInfoByProductId(productId);
		/*
		 * ProductDTO productDTO = new ProductDTO();
		 * BeanUtils.copyProperties(prod, productDTO);
		 */
		String path = request.getRealPath("/");
		try {
			// 初始化并取得Velocity引擎
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			// properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH,cfgMap.get("templatePath")+"/prodana/screen/amchartDataVM");
			properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, request
					.getSession().getServletContext().getRealPath("/")
					+ Constants.VM_FILE_PATH);
			ve.init(properties);
			// 取得velocity的模版
			// ve.setProperty(Velocity.RESOURCE_LOADER, "file");
			// ve.setProperty("file.resource.loader.class",
			// "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
			Template t = ve.getTemplate("productUsingCharLineData-settings.vm",
					"UTF-8");

			// 取得velocity的上下文context

			VelocityContext context = new VelocityContext();

			// 把数据填入上下文

			// context.put("prodName", productDTO.getProductName());
			context.put("prodName", prod == null ? "" : prod.getProductName());
			context.put("prodCode", productId);
			context.put("featureType", featureType);
			context.put("dapanCode", "2000");
			context.put("dapanName", "销量");

			// 输出流

			StringWriter writer = new StringWriter();

			// 转换输出

			t.merge(context, writer);
			response.setContentType("text/xml;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(writer.toString());
			out.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 使用次数、时间偏差EXCEL导出
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/userFeature/columnExcelDown")
	public ModelAndView columnExcelDown(HttpServletRequest request,
			HttpServletResponse response) {
		String featureType = request.getParameter("featureType");
		String reportDate = this.getCrtQueryMonthOfReport(request);

		Map<String, Object> map = new HashMap<String, Object>();
		List<String> excelHeadInfo = new ArrayList<String>();
		List<List<String>> excelDataList = new ArrayList<List<String>>();
		if(featureType.equals("1")){
			map.put("fileName", "使用次数分布"+"_"+reportDate);
			excelHeadInfo = (List<String>) request.getSession().getAttribute("excelHeadInfo_user");
			excelDataList =(List<List<String>>)request.getSession().getAttribute("excelDataList_user");
		}else if(featureType.equals("2")){
			map.put("fileName", "时间分布"+"_"+reportDate);
			excelHeadInfo = (List<String>) request.getSession().getAttribute("excelHeadInfo_time");
			excelDataList =(List<List<String>>)request.getSession().getAttribute("excelDataList_time");
		}else if(featureType.equals("5")){
			map.put("fileName", "性别特征"+"_"+reportDate);
			excelHeadInfo = (List<String>) request.getSession().getAttribute("excelHeadInfo_sex");
			excelDataList =(List<List<String>>)request.getSession().getAttribute("excelDataList_sex");
		}else if(featureType.equals("3")){
			map.put("fileName", "年龄特征"+"_"+reportDate);
			excelHeadInfo = (List<String>) request.getSession().getAttribute("excelHeadInfo_age");
			excelDataList =(List<List<String>>)request.getSession().getAttribute("excelDataList_age");
		}
		
		if(excelDataList!= null && excelDataList.size()>0){
			
			map.put("excelHeadInfo", excelHeadInfo);
			map.put("excelData", excelDataList);
		}
		
		
		return new ModelAndView(new ExcelDownloadController(), map);
	}
	
	
	@RequestMapping(value="/userFeature/showExcelDiv")
	public ModelAndView showExcelDiv(HttpServletRequest request,
			HttpServletResponse response) {
		String featureType = request.getParameter("featureType");
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> excelHeadInfo = new ArrayList<String>();
		List<List<String>> excelDataList = new ArrayList<List<String>>();
		if(featureType.equals("1")){
			excelHeadInfo = (List<String>) request.getSession().getAttribute("excelHeadInfo_user");
			excelDataList =(List<List<String>>)request.getSession().getAttribute("excelDataList_user");
		}else if(featureType.equals("2")){
			excelHeadInfo = (List<String>) request.getSession().getAttribute("excelHeadInfo_time");
			excelDataList =(List<List<String>>)request.getSession().getAttribute("excelDataList_time");
		}else if(featureType.equals("5")){
			excelHeadInfo = (List<String>) request.getSession().getAttribute("excelHeadInfo_sex");
			excelDataList =(List<List<String>>)request.getSession().getAttribute("excelDataList_sex");
		}else if(featureType.equals("3")){
			excelHeadInfo = (List<String>) request.getSession().getAttribute("excelHeadInfo_age");
			excelDataList =(List<List<String>>)request.getSession().getAttribute("excelDataList_age");
		}
		
		if(excelDataList!= null && excelDataList.size()>0){
			
			map.put("excelHeadInfos", excelHeadInfo);
			map.put("excelDataList", excelDataList);
			map.put("featureType", featureType);
		}
		
		
		return new ModelAndView("/UserFeature/featureData", map);
	}

}
