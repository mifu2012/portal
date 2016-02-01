package com.infosmart.portal.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

import com.infosmart.portal.pojo.BigEventPo;
import com.infosmart.portal.pojo.DwpasCColumnInfo;
import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCModuleInfo;
import com.infosmart.portal.pojo.DwpasCSysParam;
import com.infosmart.portal.pojo.DwpasCSystemMenu;
import com.infosmart.portal.pojo.DwpasRColumnComKpi;
import com.infosmart.portal.pojo.DwpasStDeptKpiData;
import com.infosmart.portal.pojo.DwpasStDrogueData;
import com.infosmart.portal.pojo.DwpasStKpiData;
import com.infosmart.portal.pojo.DwpasStPlatformData;
import com.infosmart.portal.pojo.ProdTransMonitor;
import com.infosmart.portal.pojo.TrendListDTO;
import com.infosmart.portal.pojo.UserCountStatistics;
import com.infosmart.portal.service.AskWindVaneManager;
import com.infosmart.portal.service.BigEventService;
import com.infosmart.portal.service.DwpasCColumnInfoService;
import com.infosmart.portal.service.DwpasCSystemMenuService;
import com.infosmart.portal.service.DwpasPageSettingService;
import com.infosmart.portal.service.DwpasStDeptKpiDateService;
import com.infosmart.portal.service.DwpasStKpiDataService;
import com.infosmart.portal.service.InstrumentPanelManager;
import com.infosmart.portal.service.UserConsultingService;
import com.infosmart.portal.util.Const;
import com.infosmart.portal.util.MathUtils;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.SystemColumnEnum;
import com.infosmart.portal.util.SystemModuleInfo;
import com.infosmart.portal.vo.Constants;
import com.infosmart.portal.vo.dwpas.PlatFormData;
import com.infosmart.portal.vo.dwpas.ProdKpiData;

/**
 * 主页显示模块
 * 
 * @author infosmart
 * 
 */
@Controller
public class HomePageController extends BaseController {

	// 大事件个数参数_GROUP_ID
	private final int groupIdOfBigEvent = 2;

	@Autowired
	private AskWindVaneManager askWindVaneManager;

	@Autowired
	private InstrumentPanelManager instrumentPanelManager;

	@Autowired
	private UserConsultingService userConsultingService;

	@Autowired
	private BigEventService bigEventService;

	@Autowired
	private DwpasPageSettingService pageSettingService;

	@Autowired
	private DwpasCSystemMenuService dwpasCSystemMenuService;
	@Autowired
	private DwpasCColumnInfoService dwpasCColumnInfoService;
	@Autowired
	private DwpasStKpiDataService dwpasStKpiDataService;
	@Autowired
	private DwpasStDeptKpiDateService dwpasStDeptKpiDateService;

	/**
	 * 转到首页
	 * 
	 * @return
	 */
	@RequestMapping("/index/gotoHomePage")
	public ModelAndView gotoHomePage(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		// 获取模块名字===============================
		String menuId = request.getParameter("menuId");// 菜单ID
		this.logger.info("转到菜单页面：" + menuId);
		// 查询菜单信息
		DwpasCSystemMenu systemMenu = this.dwpasCSystemMenuService
				.getDwpasCSystemMenuById(menuId);
		if (systemMenu == null) {
			this.logger.warn("未取到菜单信息:" + menuId);
			systemMenu = new DwpasCSystemMenu();
		}
		this.logger.info("转到菜单:" + systemMenu.getMenuName());
		map.put("systemMenu", systemMenu);
		map.put("menuId", menuId);
		String kpiType = request.getParameter("kpiType");
		if (StringUtils.notNullAndSpace(kpiType)) {
			try {
				Integer.parseInt(kpiType);
			} catch (Exception e) {
				kpiType = String.valueOf(DwpasCKpiInfo.KPI_TYPE_OF_DAY);
			}
		} else {
			try {
				if (Integer.parseInt(systemMenu.getDateType()) == DwpasCSystemMenu.MONTH_OF_DATE_TYPE) {
					kpiType = String.valueOf(DwpasCKpiInfo.KPI_TYPE_OF_MONTH);
				} else {
					kpiType = String.valueOf(DwpasCKpiInfo.KPI_TYPE_OF_DAY);
				}
			} catch (Exception e) {
				kpiType = String.valueOf(DwpasCKpiInfo.KPI_TYPE_OF_DAY);
			}
		}
		this.logger.info("指标类型：" + kpiType);
		map.put("kpiType", kpiType);
		// 当前菜单的所有模块信息
		List<DwpasCModuleInfo> moduleInfoList = pageSettingService
				.listDwpasCModuleInfoByByMenuIdAndDateType(menuId,
						Integer.parseInt(kpiType));
		if (moduleInfoList == null || moduleInfoList.isEmpty()) {
			moduleInfoList = pageSettingService
					.listDwpasCModuleInfoByByMenuIdAndDateType(menuId,
							DwpasCKpiInfo.KPI_TYPE_OF_MONTH);
		}

		if (moduleInfoList == null || moduleInfoList.isEmpty()) {
			moduleInfoList = new ArrayList<DwpasCModuleInfo>();
			this.logger.warn("当前菜单" + menuId + "没有关联任何模块");
		}
		map.put("moduleInfoList", moduleInfoList);
		// 获取日期=====================================
		String queryDate = request.getParameter("queryDate");
		if (Integer.parseInt(kpiType) == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
			if (StringUtils.notNullAndSpace(queryDate)) {
				// 变更了日期=====================================
				this.setCrtQueryDateOfReport(request, queryDate);
			} else {
				// 取默认日期=====================================
				queryDate = this.getCrtQueryDateOfReport(request);
			}
			map.put("date", queryDate);
		} else {
			if (StringUtils.notNullAndSpace(queryDate)) {
				// 变更了日期=====================================
				this.setCrtQueryMonthOfReport(request, queryDate);
			} else {
				// 取默认日期=====================================
				queryDate = this.getCrtQueryMonthOfReport(request);
			}
			map.put("date", queryDate);
		}
		return new ModelAndView("/homePage/homePage", map);
	}

	/**
	 * 转到栏目定义到的路径
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index/redirectColumnUrl")
	public ModelAndView redirectColumnUrl(HttpServletRequest request,
			HttpServletResponse response) {
		// 模块ID
		String moduleId = request.getParameter("moduleId");
		this.logger.info("转到系统模块:" + moduleId);
		// 指标类型
		String kpiType = request.getParameter("kpiType");
		try {
			Integer.parseInt(kpiType);
		} catch (Exception e) {
			kpiType = String.valueOf(DwpasCKpiInfo.KPI_TYPE_OF_DAY);
		}
		List<DwpasCColumnInfo> columnInfoList = this.columnInfoService
				.listColumnAndRelKpiCodeByModuleId(moduleId,
						this.getCrtProductIdOfReport(request),
						Integer.parseInt(kpiType));
		DwpasCColumnInfo columnInfo = null;
		if (columnInfoList != null) {
			for (DwpasCColumnInfo column : columnInfoList) {
				if (column == null)
					continue;
				if (StringUtils.notNullAndSpace(column.getColumnUrl())) {
					// this.logger.info("新的路径:" + column.getColumnUrl());
					columnInfo = column;
					break;
				}
			}
		}
		if (columnInfo == null
				|| !StringUtils.notNullAndSpace(columnInfo.getColumnUrl())) {
			this.logger.warn("没有找到该模块:" + moduleId);
			return new ModelAndView(this.NOT_FOUND_PAGE);
		} else if (columnInfo.getColumnUrl().trim().startsWith("?")) {
			this.logger.info("取系统栏目");
			columnInfo.setColumnUrl(SystemModuleInfo.SYSTEM_MODULE_URL_MAP
					.get(request.getParameter("moduleCode")));
		}
		String url = columnInfo.getColumnUrl();
		this.logger.info("系统栏目:" + url);
		if (!StringUtils.notNullAndSpace(url)) {
			this.logger.warn("没有找到该模块:" + url);
			return new ModelAndView(this.NOT_FOUND_PAGE);
		}
		if (url.toLowerCase().startsWith("http://")) {
			this.logger.info("转到指定的页面:" + url);
			try {
				response.sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
				this.logger.error(e.getMessage(), e);
			}
			return null;
		}
		if (url.indexOf("?") == -1)
			url += "?1=1";
		url += "&columnId=" + columnInfo.getColumnId() + "&moduleId="
				+ moduleId + "&kpiCodes=" + columnInfo.getKpiCodes()
				+ "&kpiType=" + kpiType;
		this.logger.info("将转到新URL：" + url);
		return new ModelAndView("redirect:" + url);
	}

	/**
	 * 转到报表页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index/redirectReportUrl")
	public ModelAndView redirectReportUrl(HttpServletRequest request,
			HttpServletResponse response) {
		// 模块ID
		String moduleId = request.getParameter("moduleId");
		this.logger.info("转到报表模块:" + moduleId);
		// 指标类型
		String kpiType = request.getParameter("kpiType");
		try {
			Integer.parseInt(kpiType);
		} catch (Exception e) {
			kpiType = String.valueOf(DwpasCKpiInfo.KPI_TYPE_OF_DAY);
		}
		List<DwpasCColumnInfo> columnInfoList = this.columnInfoService
				.listColumnAndRelKpiCodeByModuleId(moduleId,
						this.getCrtProductIdOfReport(request),
						Integer.parseInt(kpiType));
		DwpasCColumnInfo columnInfo = null;
		if (columnInfoList != null) {
			for (DwpasCColumnInfo column : columnInfoList) {
				if (column == null)
					continue;
				if (StringUtils.notNullAndSpace(column.getColumnUrl())) {
					// this.logger.info("新的路径:" + column.getColumnUrl());
					columnInfo = column;
					break;
				}
			}
		}
		if (columnInfo == null
				|| !StringUtils.notNullAndSpace(columnInfo.getColumnUrl())) {
			this.logger.warn("没有找到该报表模块:" + moduleId);
			return new ModelAndView(this.NOT_FOUND_PAGE);
		}
		// columnInfo.getColumnUrl()为报表ID
		this.logger.info("-->报表ID:" + columnInfo.getColumnUrl());
		String url = columnInfo.getColumnUrl();
		if (url.indexOf("?") == -1)
			url += "?1=1";
		url += "&columnId=" + columnInfo.getColumnId() + "&moduleId="
				+ moduleId;
		this.logger.info("将转到报表URL：" + url);
		return new ModelAndView("redirect:" + url);
	}

	/**
	 * 转到图显示页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index/forwardChartUrl")
	public ModelAndView forwardChartUrl(HttpServletRequest request,
			HttpServletResponse response) {
		// 菜单ID
		String moduleId = request.getParameter("moduleId");
		this.logger.info("转到自定义图表模块:" + moduleId);
		// 指标类型
		String kpiType = request.getParameter("kpiType");
		
		String productId = request.getParameter("productId");
		if (!StringUtils.notNullAndSpace(productId)) {
			// 如果为空的话,则取session的产品Id;
			productId = this.getCrtProductIdOfReport(request);
			if (!StringUtils.notNullAndSpace(productId)) {
				return new ModelAndView("/common/noProduct");
			}
		} else {
			this.logger.info("保存PROD_ID_IN SESSION.....");
			// 将产品Id放入session
			this.setCrtProductIdOfReport(request, productId);
		}

		
		// 显示方式
		String tabShow = request.getParameter("tabShow");
		String queryDate = request.getParameter("queryDate");
		if (Integer.parseInt(kpiType) == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
			if (StringUtils.notNullAndSpace(queryDate)) {
				// 变更了日期=====================================
				this.setCrtQueryDateOfReport(request, queryDate);
			} else {
				// 取默认日期=====================================
				queryDate = this.getCrtQueryDateOfReport(request);
			}

		} else {
			if (StringUtils.notNullAndSpace(queryDate)) {
				// 变更了日期=====================================
				this.setCrtQueryMonthOfReport(request, queryDate);
			} else {
				// 取默认日期=====================================
				queryDate = this.getCrtQueryMonthOfReport(request);
			}
		}
		try {
			Integer.parseInt(kpiType);
		} catch (Exception e) {
			kpiType = String.valueOf(DwpasCKpiInfo.KPI_TYPE_OF_DAY);
		}
		
		String hasProd = "1";
		List<DwpasRColumnComKpi> rColumnComKpiInfoList = this.columnInfoService
				.listRColumnComKpiInfoByModuleId(moduleId);
		if (rColumnComKpiInfoList != null && rColumnComKpiInfoList.size() > 0) {
			hasProd = rColumnComKpiInfoList.get(0).getValue2() == null ? "1"
					: rColumnComKpiInfoList.get(0).getValue2();
		}
		List<DwpasCColumnInfo> columnInfoList = null;
		if ("-1".equals(hasProd)) {// 后台未配置产品
			columnInfoList = this.columnInfoService
					.listColumnInfoListByModuleIdAndKpiTypeAndProductId(
							moduleId, Integer.parseInt(kpiType), productId);
		} else {
			// xbb修改,自定义图形，配置的都是通用指标，后台配置时配置上产品Id，且将产品Id赋予dwpas_r_column_com_kpi表的value_2字段里
			columnInfoList = this.columnInfoService
					.listColumnInfoListByModuleIdAndKpiType(moduleId,
							Integer.parseInt(kpiType));
		}
		Map map = new HashMap();
		map.put("moduleId", moduleId);
		map.put("tabShow", tabShow);
		map.put("kpiType", kpiType);
		map.put("queryDate", queryDate);
		map.put("columnInfoList",
				columnInfoList == null ? new ArrayList<DwpasCColumnInfo>()
						: columnInfoList);
		return new ModelAndView("/homePage/showChartPage", map);
	}

	/**
	 * 风向标 INDEX_USER_HELP_KEYWORD_LIST
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index/showAskWindVane")
	public ModelAndView showAskWindVane(HttpServletRequest request,
			HttpServletResponse response) {
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
		map.put("date", queryDate);
		map.put("queryType", DwpasCKpiInfo.KPI_TYPE_OF_DAY);
		// List<DwpasStDrogueData> askWindVaneDatasList = askWindVaneManager
		// .getAskWindVaneData(queryDate.replace("-", ""));
		// if (askWindVaneDatasList == null)
		// askWindVaneDatasList = new ArrayList<DwpasStDrogueData>();
		// map.put("askWindVaneDatasList", askWindVaneDatasList);
		return new ModelAndView("/homePage/askWindVane", map);
	}

	/**
	 * 风向标（新式风格(云标签)） INDEX_USER_HELP_KEYWORD_LIST
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index/showAskTagcloud")
	public void showAskTagcloud(HttpServletRequest request,
			HttpServletResponse response) {
		// 获取日期=====================================
		String queryDate = request.getParameter("queryDate");
		if (StringUtils.notNullAndSpace(queryDate)) {
			// 变更了日期=====================================
			this.setCrtQueryDateOfReport(request, queryDate);
		} else {
			// 取默认日期=====================================
			queryDate = this.getCrtQueryDateOfReport(request);
		}
		try {
			if (askWindVaneManager == null) {
				this.logger.info("------------------>对象未初始化");
			}
			List<DwpasStDrogueData> askWindVaneDatas = askWindVaneManager
					.getAskWindVaneData(queryDate);
			String rootPath = request.getSession().getServletContext()
					.getRealPath("/");
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

	/**
	 * 产品全图统计 INDEX_PRODUCT_CHART
	 */
	@RequestMapping("/index/showAllPlatFormUser")
	public ModelAndView showAllPlatFormUser(HttpServletRequest request,
			HttpServletResponse response) {
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
		map.put("date", queryDate.replace("-", ""));
		map.put("queryType", DwpasCKpiInfo.KPI_TYPE_OF_DAY);
		// 查询不同平台的统计数据(饼图数据)
		DwpasStPlatformData stplatFormData = instrumentPanelManager
				.queryPlatFormUser(queryDate.replace("-", ""));
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
		// 当前用户模板
		String templateId = this.getCrtUserTemplateId(request);
		// 根据moudleId查询栏目数据
		String moduleId = request.getParameter("moduleId");
		// 之前是大盘指标，现在时产品关联的通用指标获取kpiCode
		// List<DwpasCColumnInfo> dwpasCColumnInfoList = dwpasCColumnInfoService
		// .listDwpasCColumnInfoSByMoudleId(moduleId);
		List<DwpasCColumnInfo> dwpasCColumnInfoList = dwpasCColumnInfoService
				.listAllCoumnInfosByModelIdAndKpiType(moduleId,
						DwpasCKpiInfo.KPI_TYPE_OF_DAY);

		List<String> kpiCodeList = new ArrayList<String>();
		// 栏目编码，栏目信息
		Map<String, DwpasCColumnInfo> columnInfoMap = new HashMap<String, DwpasCColumnInfo>();
		// 栏目编码，指标数据
		Map<String, DwpasStKpiData> columnCodeRKpiDataMap = new HashMap<String, DwpasStKpiData>();
		// 指标编码，栏目编码
		Map<String, String> kpiCodeRColumnCodeMap = new HashMap<String, String>();
		if (dwpasCColumnInfoList != null && !dwpasCColumnInfoList.isEmpty()) {
			for (DwpasCColumnInfo columnInfo : dwpasCColumnInfoList) {
				// 防止未关联指标
				if (columnInfo.getKpiInfoList() != null
						&& !columnInfo.getKpiInfoList().isEmpty()) {
					kpiCodeList.add(columnInfo.getKpiInfoList().get(0)
							.getKpiCode());
					kpiCodeRColumnCodeMap.put(columnInfo.getKpiInfoList()
							.get(0).getKpiCode(), columnInfo.getColumnCode());
				}
				columnInfoMap.put(columnInfo.getColumnCode(), columnInfo);
			}
		}
		// 栏目信息
		map.put("columnInfoMap", columnInfoMap);
		// 查询指标数据
		Map<String, DwpasStKpiData> dwpasStKpiDataMapList = dwpasStKpiDataService
				.listDwpasStKpiDataByKpiCode(kpiCodeList, queryDate,
						DwpasStKpiData.DATE_TYPE_OF_DAY);
		if (dwpasStKpiDataMapList != null && !dwpasStKpiDataMapList.isEmpty()) {
			Entry entry = null;
			for (Iterator it = dwpasStKpiDataMapList.entrySet().iterator(); it
					.hasNext();) {
				entry = (Entry) it.next();
				// <栏目编码，栏目对应的数据>
				columnCodeRKpiDataMap.put(
						kpiCodeRColumnCodeMap.get(entry.getKey().toString()),
						(DwpasStKpiData) entry.getValue());
			}
		}
		// 500w 月目标值
//		String queryMoth = this.getCrtQueryMonthOfReport(request);
//		String monthTargetKpiCode = "KPICP" + queryMoth.replace("-", "").trim()
//				+ "001";
//		List<DwpasStDeptKpiData> deptInfo = dwpasStDeptKpiDateService
//				.getDetInfoByKpiCode(monthTargetKpiCode);
//		String monthTargateData = "";
//		if (deptInfo != null && deptInfo.size() > 0) {
//			monthTargateData = deptInfo.get(0).getKpiTaskValue().toString();
//		}
//		map.put("monthTargateData", monthTargateData);

		// 栏目对应的指标数据
		map.put("columnCodeRKpiDataMap", columnCodeRKpiDataMap);
		DwpasStKpiData ddd = columnCodeRKpiDataMap.get("INDEX_PRODUCT_PIE_CHART_TOTAL_SALE_AMOUNT");
		// 产品全图统计数据关联指标
		/*
		 * List<DwpasRColumnComKpi> dwpasRColumnComKpiList = columnInfoService
		 * .getCommonCodeByColumnCode(templateId,
		 * SystemColumnEnum.INDEX_PRODUCT_PIE_CHART .getColumnCode()); //
		 * 获得产品全图统计数据============================= if (dwpasRColumnComKpiList !=
		 * null && dwpasRColumnComKpiList.size() != 0) { List<ProdTransMonitor>
		 * prodTranInfosRetList = dwpasCPrdInfoService
		 * .getProdTransInfos(dwpasRColumnComKpiList, queryDate .replace("-",
		 * ""), DateConvertEditor .getSpecifiedDayBefore(queryDate.replace("-",
		 * ""))); if (prodTranInfosRetList == null) prodTranInfosRetList = new
		 * ArrayList<ProdTransMonitor>(); map.put("prodTranInfosRetList",
		 * prodTranInfosRetList); }
		 */
		return new ModelAndView("/homePage/platFormUser", map);
	}

	/**
	 * 业务笔数监控 INDEX_RECTANGLE_CHART
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index/prodTransMonitorChart")
	public ModelAndView prodTransMonitorChart(HttpServletRequest request,
			HttpServletResponse response) {
		// 获取日期=====================================
		String queryDate = request.getParameter("queryDate");
		if (StringUtils.notNullAndSpace(queryDate)) {
			// 变更了日期=====================================
			this.setCrtQueryDateOfReport(request, queryDate);
		} else {
			// 取默认日期=====================================
			queryDate = this.getCrtQueryDateOfReport(request);
		}
		Map map = new HashMap();
		// 当前用户模板
		String templateId = this.getCrtUserTemplateId(request);
		// 产品全图统计数据关联指标
		List<DwpasRColumnComKpi> dwpasRColumnComKpiList = columnInfoService
				.getCommonCodeByColumnCode(templateId,
						SystemColumnEnum.INDEX_RECTANGLE_CHART_COUNTS
								.getColumnCode());
		List<ProdTransMonitor> prodTransCountList = dwpasCPrdInfoService
				.getServiceInfos(dwpasRColumnComKpiList,
						queryDate.replace("-", ""));
		if (prodTransCountList == null) {
			prodTransCountList = new ArrayList<ProdTransMonitor>();
		}
		map.put("prodTransCountList", prodTransCountList);
		// 变化幅度================================
		// 图的数据
		map.put("transAmchartData",
				getTransAmchartData(request, prodTransCountList).replaceAll(
						"\r\n", ""));
		// 图的设置
		map.put("transAmchartSettings",
				getTransAmchartSettings(request, prodTransCountList)
						.replaceAll("\r\n", ""));
		// 图形设置================================
		if (prodTransCountList != null && prodTransCountList.size() != 0) {
			map.put("unit", prodTransCountList.get(0).getUnit());
		}
		return new ModelAndView("/homePage/prodTransMonitorChart", map);
	}

	/**
	 * 用户数统计 INDEX_USER_COUNT_TABLE
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index/userCountStatistics")
	public ModelAndView userCountStatistics(HttpServletRequest request,
			HttpServletResponse response) {
		// 获取日期=====================================
		String queryDate = request.getParameter("queryDate");
		if (StringUtils.notNullAndSpace(queryDate)) {
			// 变更了日期=====================================
			this.setCrtQueryDateOfReport(request, queryDate);
		} else {
			// 取默认日期=====================================
			queryDate = this.getCrtQueryDateOfReport(request);
		}
		Map map = new HashMap();
		List<UserCountStatistics> userCountStatisticsList = askWindVaneManager
				.getUserCountStatistics(
						String.valueOf(DwpasCKpiInfo.KPI_TYPE_OF_DAY),
						queryDate.replace("-", ""));
		if (userCountStatisticsList == null)
			userCountStatisticsList = new ArrayList<UserCountStatistics>();
		map.put("userCountStatisticsList", userCountStatisticsList);
		// 用户变化趋势图
		List<TrendListDTO> trendListList = askWindVaneManager.init();
		if (trendListList == null)
			trendListList = new ArrayList<TrendListDTO>();
		map.put("trendListList", trendListList);
		return new ModelAndView("/homePage/userCountStatistics", map);
	}

	/**
	 * 用户咨询情况（预警图） INDEX_ALERT_DASHBOARD_CHART
	 * 
	 * @return
	 */
	@RequestMapping("/index/askBashBoardChart")
	public ModelAndView askBashBoardChart(HttpServletRequest request,
			HttpServletResponse response) {
		// 获取日期=====================================
		String queryDate = request.getParameter("queryDate");
		if (StringUtils.notNullAndSpace(queryDate)) {
			// 变更了日期=====================================
			this.setCrtQueryDateOfReport(request, queryDate);
		} else {
			// 取默认日期=====================================
			queryDate = this.getCrtQueryDateOfReport(request);
		}
		Map map = new HashMap();
		List<ProdKpiData> queryKpiDataList = userConsultingService
				.queryProductClasse(this.getCrtUserTemplateId(request),
						queryDate.replace("-", ""),
						DwpasCKpiInfo.KPI_TYPE_OF_DAY);
		if (queryKpiDataList == null)
			queryKpiDataList = new ArrayList<ProdKpiData>();
		map.put("date", queryDate.replace("-", ""));
		map.put("queryKpiDataList", queryKpiDataList);
		// 获取所有产品线的预警，警告，最大，最小值=========================
		map.put("queryKpiDataList", queryKpiDataList);
		return new ModelAndView("/homePage/askBashBoardChart", map);
	}

	/**
	 * 列出最近的大事记 模块编码：INDEX_IMPORTANT_EVENT_LIST
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index/listLeastBigEvent")
	public ModelAndView listLeastBigEvent(HttpServletRequest request,
			HttpServletResponse response) {
		String queryDate = request.getParameter("queryDate");
		if (StringUtils.notNullAndSpace(queryDate)) {
			// 变更了日期=====================================
			this.setCrtQueryDateOfReport(request, queryDate);
		} else {
			// 取默认日期=====================================
			queryDate = this.getCrtQueryDateOfReport(request);
		}
		Map map = new HashMap();
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
		List<BigEventPo> bigEventList = this.bigEventService
				.listLatelyBigEventAndRelateKpiInfo(bigEventCount, queryDate);
		if (bigEventList == null)
			bigEventList = new ArrayList<BigEventPo>();
		map.put("bigEventList", bigEventList);
		map.put("date", queryDate);
		return new ModelAndView("/homePage/listLeastBigEvent", map);
	}

	/**
	 * 业务笔数监控（类似矩形图）数据
	 * 
	 * @param request
	 * @param prodTransCountVOs
	 * @return
	 */
	private String getTransAmchartData(HttpServletRequest request,
			List<ProdTransMonitor> prodTransCountVOs) {
		String rootPath = request.getSession().getServletContext()
				.getRealPath("/");
		try {
			// 初始化并取得Velocity引擎=================================
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, rootPath
					+ "/vm");
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
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
		return "";
	}

	/**
	 * 业务笔数监控类似矩形图设置
	 * 
	 * @param request
	 * @param prodTransCountVOs
	 * @return
	 */
	private String getTransAmchartSettings(HttpServletRequest request,
			List<ProdTransMonitor> prodTransCountVOs) {
		String rootPath = request.getSession().getServletContext()
				.getRealPath("/");
		try {
			// 初始化并取得Velocity引擎=================================
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, rootPath
					+ "/vm");
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
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			return "";
		}
	}

	/**
	 * 数据格式转换
	 * 
	 * @param rate
	 * @return
	 */
	private String getValueDisplay(BigDecimal rate) {
		if (rate == null) {
			return "-";
		}
		return String.valueOf(MathUtils.multi(Constants.ONE_HANDREN, rate)
				.setScale(2, BigDecimal.ROUND_HALF_UP)) + "%";
	}
}
