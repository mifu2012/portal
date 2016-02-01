package com.infosmart.portal.action;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.portal.pojo.BigEventPo;
import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCSysParam;
import com.infosmart.portal.pojo.DwpasRColumnComKpi;
import com.infosmart.portal.pojo.DwpasStDrogueData;
import com.infosmart.portal.pojo.DwpasStPlatformData;
import com.infosmart.portal.pojo.ProdTransMonitor;
import com.infosmart.portal.service.AskWindVaneManager;
import com.infosmart.portal.service.BigEventService;
import com.infosmart.portal.service.DwpasPageSettingService;
import com.infosmart.portal.service.InstrumentPanelManager;
import com.infosmart.portal.util.DateConvertEditor;
import com.infosmart.portal.util.MathUtils;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.SystemColumnEnum;
import com.infosmart.portal.vo.Constants;
import com.infosmart.portal.vo.dwpas.PlatFormData;

@Controller
public class DWIndexController extends BaseController {
	final static Logger LOG = Logger.getLogger(DWIndexController.class);
	// 大事件个数参数_GROUP_ID
	private final int groupIdOfBigEvent = 2;
	@Autowired
	private AskWindVaneManager askWindVaneManager;
	@Autowired
	private InstrumentPanelManager instrumentPanelManager;
	@Autowired
	private BigEventService bigEventService;
	@Autowired
	private DwpasPageSettingService pageSettingService;

	@RequestMapping("/index/getInto")
	public ModelAndView instrumentPanelShow(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.logger.info("显示首页信息....");
		Map map = new HashMap();
		// 获取日期=====================================
		String queryDate = request.getParameter("queryDate");
		if (StringUtils.notNullAndSpace(queryDate)) {
			// 变更了日期=====================================
			this.setCrtQueryDateOfReport(request, queryDate);
		} else {
			// 取默认日期=====================================
			queryDate = this.getCrtQueryDateOfReport(request);
		}
		String queryValueDate = queryDate.replace("-", "");
		map.put("date", queryDate);
		map.put("queryType", DwpasCKpiInfo.KPI_TYPE_OF_DAY);

		// //
		// ================================用户咨询风向标=====================================
		//
		// List<DwpasStDrogueData> askWindVaneDatas = askWindVaneManager
		// .getAskWindVaneData(queryValueDate);
		// map.put("askWindVanes", askWindVaneDatas);

		// ================================产品全图===========================================

		DwpasStPlatformData stplatFormData = instrumentPanelManager
				.queryPlatFormUser(queryValueDate);
		if (stplatFormData != null) {
			PlatFormData platFormDataTB = new PlatFormData("WAP",
					getValueDisplay(stplatFormData.getTaobaoRate()), MathUtils
							.exceptFroNull(stplatFormData.getTaobaoRateTrend())
							.intValue());
			PlatFormData platFormDataIN = new PlatFormData("站内",
					getValueDisplay(stplatFormData.getInnerRate()), MathUtils
							.exceptFroNull(stplatFormData.getInnerRateTrend())
							.intValue());
			PlatFormData platFormDataOUT = new PlatFormData("外部",
					getValueDisplay(stplatFormData.getOutRate()), MathUtils
							.exceptFroNull(stplatFormData.getOutRateTrend())
							.intValue());
			map.put("platFormDataTB", platFormDataTB);
			map.put("platFormDataIN", platFormDataIN);
			map.put("platFormDataOUT", platFormDataOUT);

		}
		// 获取模块名字===============================
		String menuId = request.getParameter("menuId");// 菜单ID
		map.put("menuId", menuId);
		// 当前菜单的所有模块信息
		Map<String, Object> moduleInfoMap = pageSettingService
				.getModuleInfoByMenuIdAndDateType(menuId,
						DwpasCKpiInfo.KPI_TYPE_OF_DAY);
		map.put("moduleInfoMap", moduleInfoMap);
		// 获得通用指标===============================
		String templateId = this.getCrtUserTemplateId(request);
		List<DwpasRColumnComKpi> dwpasRColumnComKpi = columnInfoService
				.getCommonCodeByColumnCode(templateId,
						SystemColumnEnum.INDEX_RECTANGLE_CHART_COUNTS
								.getColumnCode());

		// 获得产品全图数据=============================
		if (dwpasRColumnComKpi != null && dwpasRColumnComKpi.size() != 0) {
			List<ProdTransMonitor> prodTranInfosRet = dwpasCPrdInfoService
					.getProdTransInfos(dwpasRColumnComKpi, queryValueDate,
							DateConvertEditor.getSpecifiedDayBefore(queryValueDate));
			map.put("prodTranInfosRanks", prodTranInfosRet);
		}

		// =======================================业务笔数监控=================================

		// List<ProdTransMonitor> prodTransCountVOs = dwpasCPrdInfoService
		// .getServiceInfos(dwpasRColumnComKpi, queryValueDate);
		// map.put("prodTranInfos", prodTransCountVOs);
		// // 变化幅度================================
		// map.put("transAmchartData",
		// getTransAmchartData(request, prodTransCountVOs).replaceAll(
		// "\r\n", ""));
		// map.put("transAmchartSettings",
		// getTransAmchartSettings(request, prodTransCountVOs).replaceAll(
		// "\r\n", ""));
		// // 图形设置================================
		// if (prodTransCountVOs != null && prodTransCountVOs.size() != 0) {
		// map.put("unit", prodTransCountVOs.get(0).getUnit());
		// }

		// ======================================支付宝用户数统计===============================

		// List<UserCountStatistics> userCountStatistics = askWindVaneManager
		// .getUserCountStatistics(
		// String.valueOf(DwpasCKpiInfo.KPI_TYPE_OF_DAY),
		// queryValueDate);
		// map.put("userCountStatistics", userCountStatistics);
		//
		// //
		// ===============================支付宝用户数统计趋势图===============================
		//
		// List<TrendListDTO> leftList = askWindVaneManager.init();
		// map.put("leftList", leftList);

		// ===============================用户咨询情况=======================================

		// List<ProdKpiDataDTO> queryProductClasses = userConsultingService
		// .queryProductClasse(this.getCrtUserTemplateId(request),
		// queryDate.replace("-", ""),
		// DwpasCKpiInfo.KPI_TYPE_OF_DAY);
		// map.put("productCatalogs", queryProductClasses);
		// // 获取所有产品线的预警，警告，最大，最小值=========================
		// map.put("allProdRateHelp", queryProductClasses);

		// ===============================产品聚焦==========================================

		DwpasCSysParam qryOneByParamId = bigEventService
				.qryOneByParamId(groupIdOfBigEvent);
		int bigEventCount = 5;
		if (qryOneByParamId != null) {
			try {
				bigEventCount = Integer.parseInt(qryOneByParamId
						.getParamValue());
			} catch (Exception e) {
				bigEventCount = 5;
			}
		}
		// 查询大事件=================================
		List<BigEventPo> profocuslist = this.bigEventService
				.listLatelyBigEventAndRelateKpiInfo(bigEventCount, queryDate);
		map.put("profocuslist", profocuslist);
		return new ModelAndView("/homePage/index", map);
	}
	
	//大事记弹页面
		@RequestMapping(value = "/index/eventPopup{id}")
		public ModelAndView editEvent(@PathVariable Integer id, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			List<BigEventPo> eventList = bigEventService.listLatelyBigEventAndRelateKpiInfoById(id);
			ModelAndView mav = new ModelAndView();
			mav.addObject("eventList",eventList);
			mav.addObject("eventId",id);
			mav.setViewName("/homePage/eventPopup");
			return mav;
			
		}

	private String getValueDisplay(BigDecimal rate) {
		if (rate == null) {
			return "-";
		}
		return String.valueOf(MathUtils.multi(Constants.ONE_HANDREN, rate)
				.setScale(2, BigDecimal.ROUND_HALF_UP)) + "%";
	}

	public static List<ProdTransMonitor> getProdTransCountVOs(
			List<ProdTransMonitor> prodTranInfoList) {
		for (ProdTransMonitor prodTranInfo : prodTranInfoList) {
			if ((null != prodTranInfo.getChangeRate())) {
				if (prodTranInfo.getChangeRate().compareTo(new BigDecimal(0)) == 1) {
					prodTranInfo.setTrend("up");
				}
				BigDecimal changeRate = prodTranInfo.getChangeRate()
						.multiply(new BigDecimal(100))
						.setScale(2, BigDecimal.ROUND_HALF_UP);
				prodTranInfo.setChangeRate(changeRate);
			}
		}
		return prodTranInfoList;
	}

	private String getTransAmchartData(HttpServletRequest request,
			List<ProdTransMonitor> prodTransCountVOs) {
		String path = request.getRealPath("/");
		try {
			// 初始化并取得Velocity引擎=================================
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, path + "/vm");
			ve.init(properties);
			Template t = ve.getTemplate("prodTransData.vm", "UTF-8");
			// 取得velocity的上下文context
			VelocityContext context = new VelocityContext();
			// 把数据填入上下文
			context.put("prodTransCountVOs", prodTransCountVOs);
			// 输出流
			StringWriter writer = new StringWriter();
			// 转换输出
			t.merge(context, writer);
			return writer.toString();

		} catch (Exception e) {
			if (LOG.isInfoEnabled()) {
				LOG.info("读取prodTransData.vm出错" + e.getMessage());
			}
		}
		return "";
	}

	@RequestMapping("/index/showAskWind")
	public void getAskWind(HttpServletRequest request,
			HttpServletResponse response) {
		String queryDate = request.getParameter("queryDate");
		if (StringUtils.notNullAndSpace(queryDate)) {
			// 变更了日期=====================================
			this.setCrtQueryDateOfReport(request, queryDate);
		} else {
			// 取默认日期=====================================
			queryDate = this.getCrtQueryDateOfReport(request);
		}
		String queryValueDate = queryDate.replace("-", "");
		try {
			if (askWindVaneManager == null) {
				this.logger.info("------------------>对象未初始化");
			}
			List<DwpasStDrogueData> askWindVaneDatas = askWindVaneManager
					.getAskWindVaneData(queryValueDate);
			// askWindVaneDatas=askWindVaneDatas.subList(0, 1);
			// askWindVaneDatas.remove(0);
			// for(int i=0;i<30;i++){
			// DwpasStDrogueData askWindVaneData=new DwpasStDrogueData();
			// askWindVaneData.setCatTitle("因特网");
			// askWindVaneDatas.add(askWindVaneData);
			// }
			String rootPath = request.getSession().getServletContext()
					.getRealPath("/");
			
			// 获取产品ID,用以点击风向标跳转到用户声音用
			String productId = request.getParameter("productId");
			if (!StringUtils.notNullAndSpace(productId)) {
				// 如果没取到产品ID,则取session中产品ID，初始是web.xml定义的的产品ID
				productId = this.getCrtProductIdOfReport(request);
				
			} else {
				// 选择了其他的产品,保存在session中
				this.setCrtProductIdOfReport(request, productId);
			}

			String templateId = this.getCrtUserTemplateId(request);
			String voiceMenuCode = "0304";
			String voiceMenuId = pageSettingService.getMenuIdByTemplateIdAndMenuCode(templateId, voiceMenuCode);
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			//String voiceUrl = basePath+"userVoice/showUserVoice?menuId="+voiceMenuId+"&productId="+productId;
			
			// 初始化并取得Velocity引擎
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, rootPath
					+ "/vm");
			ve.init(properties);
			Template template = ve.getTemplate("tagcloud.vm", "UTF-8");
			// 取得velocity的上下文context
			VelocityContext context = new VelocityContext();
			// 把数据填入上下文
			if (null != askWindVaneDatas && askWindVaneDatas.size() != 0) {
				context.put("askWindVaneDatas", askWindVaneDatas);
				context.put("basePath", basePath);
				//context.put("voiceUrl", voiceUrl);
				context.put("voiceMenuId", voiceMenuId);
				context.put("productId", productId);
				context.put("queryDate", queryValueDate);
			}
			// 输出流
			StringWriter writer = new StringWriter();

			// 转换输出
			template.merge(context, writer);
			response.setContentType("text/plain;charset=UTF-8");
			// 转换输出
			PrintWriter out = response.getWriter();
			out.print(writer.toString());

		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}

	private String getTransAmchartSettings(HttpServletRequest request,
			List<ProdTransMonitor> prodTransCountVOs) {
		String path = request.getRealPath("/");
		try {
			// 初始化并取得Velocity引擎=================================
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, path + "/vm");
			ve.init(properties);
			Template t = ve.getTemplate("prodTransSettings.vm", "UTF-8");
			// 取得velocity的上下文context
			VelocityContext context = new VelocityContext();
			// 把数据填入上下文
			if (null != prodTransCountVOs && prodTransCountVOs.size() != 0) {
				context.put("unit", prodTransCountVOs.get(0).getUnit());
			}
			// 输出流
			StringWriter writer = new StringWriter();
			// 转换输出
			t.merge(context, writer);
			return writer.toString();

		} catch (Exception e) {
			if (LOG.isInfoEnabled()) {
				LOG.info("读取prodTransSettings.vm出错" + e.getMessage());
			}
			return "";
		}
	}
}
