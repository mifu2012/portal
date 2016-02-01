package com.infosmart.portal.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCSysType;
import com.infosmart.portal.pojo.ProkpiAnalyze;
import com.infosmart.portal.service.DwpasCSysTypeService;
import com.infosmart.portal.service.DwpasPageSettingService;
import com.infosmart.portal.util.StringUtils;

/**
 * 产品指标分析
 * 
 * @author mf
 * 
 */
@Controller
public class ProdAnalyzeController extends BaseController {
	protected final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private DwpasPageSettingService pageSettingService;
	@Autowired
	private DwpasCSysTypeService dwpasCSysTypeService;

	@RequestMapping("/prodAnalyze/kpiAnalyze")
	public ModelAndView kpiAnalyze(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");// 菜单ID
		Map<String, Object> map = new HashMap<String, Object>();
		// 指标小类CODE
		String childKpiCode = request.getParameter("childKpiCode");
		// 大盘指标CODE
		String overallKpiCode = request.getParameter("overallKpiCode");
		String kpiType = request.getParameter("kpiType");
		String queryMonth = request.getParameter("queryMonth");
		String queryDate = request.getParameter("queryDate");
		if (!StringUtils.notNullAndSpace(overallKpiCode)) {
			overallKpiCode = "";
		}
		// 产品ID，默认从页面参数取产品ID（用于选择产品）
		String productId = request.getParameter("productId");
		if (!StringUtils.notNullAndSpace(productId)) {
			// 如果没取到产品ID,则取session中产品ID，初始是web.xml定义的的产品ID
			productId = this.getCrtProductIdOfReport(request);
			if (!StringUtils.notNullAndSpace(productId)) {
				return new ModelAndView("/common/noProduct");
			}
		} else {
			// 选择了其他的产品,保存在session中
			this.setCrtProductIdOfReport(request, productId);
		}
		// 查询日期,从URL参数取得(用于变更日期)

		if (!StringUtils.notNullAndSpace(queryDate)) {
			// 默认是取session中日期，初始是当前日期
			queryDate = this.getCrtQueryDateOfReport(request);
		} else {
			// 变更为其他的日期
			this.setCrtQueryDateOfReport(request, queryDate);
		}

		if (!StringUtils.notNullAndSpace(queryMonth)) {
			// 如果为空的话,则取session的queryMonth;
			String kpitype = String.valueOf(DwpasCKpiInfo.KPI_TYPE_OF_MONTH);
			queryMonth = this.getCrtQueryMonthOfReport(request);
			if (StringUtils.equals(kpitype, kpiType)) {
				queryDate = queryMonth;
			}
		} else {
			// 将月时间放入到session
			this.setCrtQueryMonthOfReport(request, queryMonth);
			queryDate = queryMonth;
		}

		if (!StringUtils.notNullAndSpace(kpiType)) {
			kpiType = String.valueOf(DwpasCKpiInfo.KPI_TYPE_OF_DAY);
		}
		String tansKpis[] = new String[0];
		String kpiCodes = "";
		if (StringUtils.notNullAndSpace(childKpiCode)) {
			kpiCodes = childKpiCode;
		}
		if (StringUtils.notNullAndSpace(overallKpiCode)) {
			if (StringUtils.notNullAndSpace(childKpiCode)) {
				kpiCodes = childKpiCode + ";" + overallKpiCode;
			} else {
				kpiCodes = overallKpiCode;
			}
		}

		if (StringUtils.notNullAndSpace(kpiCodes)) {
			// kpiCodeOfAmchart = kpiCodes;
			map.put("amchart", kpiCodes);
		}
		List<String> kpiNames = new ArrayList<String>();
		// 计算开始时间用于表格显示
		String beginDate = "";
		if (Integer.parseInt(kpiType) == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
			DateFormat format = new SimpleDateFormat("yyyyMMdd");
			Date dd = format.parse(queryDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dd);
			calendar.add(Calendar.DAY_OF_MONTH, -100);
			beginDate = format.format(calendar.getTime());
		}
		// 大盘指标
		List<DwpasCKpiInfo> grailKpis = dwpasCKpiInfoService
				.getAllOverallKpis(kpiType);
		// 判断大盘指标是否被选中
		if (StringUtils.notNullAndSpace(overallKpiCode)) {
			String kpiCodeArray[] = overallKpiCode.split(",");
			if (kpiCodeArray != null) {
				for (String kpiCode : kpiCodeArray) {
					grailKpis = markCommonKpi(grailKpis, kpiCode);
				}
			}
		}
		// 第一次访问
		Boolean firstVisit = true;
		if (StringUtils.notNullAndSpace(kpiCodes.replace(";", ""))) {
			firstVisit = false;
			tansKpis = StringUtils.split(kpiCodes, ";");
		}
		List<String> typeIds = new ArrayList<String>();
		// 指标大类
		List<DwpasCSysType> parentKpis = dwpasCSysTypeService.getParentKpi();
		if (parentKpis != null) {
			for (DwpasCSysType parentKpi : parentKpis) {
				typeIds.add(parentKpi.getTypeId());
			}
		}
		// 通过typeId查询指标小类
		List<DwpasCKpiInfo> childKpis = dwpasCKpiInfoService
				.getChildCodeByTypeIds(productId, typeIds, kpiType);
		Map<String, List<DwpasCKpiInfo>> mapCodeKpiInfo = new HashMap<String, List<DwpasCKpiInfo>>();// <parentName,dwpasCkpiInfos>
		List<DwpasCKpiInfo> tempCkpInfo = null;
		if (childKpis != null && childKpis.size() > 0) {
			for (DwpasCKpiInfo childKpi : childKpis) {
				if (!mapCodeKpiInfo.containsKey(childKpi.getParentName())) {
					tempCkpInfo = new ArrayList<DwpasCKpiInfo>();
					tempCkpInfo.add(childKpi);
					mapCodeKpiInfo.put(childKpi.getParentName(), tempCkpInfo);
				} else {
					mapCodeKpiInfo.get(childKpi.getParentName()).add(childKpi);
				}
				// 指标小类是否被选
				if (isChoose(childKpi, tansKpis)) {
					childKpi.setMarked(true);
				}
			}
			// 设置第第一个指标小类为默认选择的指标
			if (firstVisit) {
				DwpasCKpiInfo defaultKpi = childKpis.get(0);
				defaultKpi.setMarked(true);
				childKpiCode = defaultKpi.getKpiCode();
				tansKpis = new String[1];
				tansKpis[0] = defaultKpi.getKpiCode();
				kpiCodes = defaultKpi.getKpiCode();
				map.put("amchart", defaultKpi.getKpiCode());
			}
		}

		// 判定用于趋势图中ispercent参数
		int ispercent = 0;
		if (tansKpis.length == 1) {
			ispercent = 0;
		} else {
			ispercent = 1;
		}
		// 通过codeList查询kpiDate
		List<ProkpiAnalyze> showHistries = dwpasStKpiDataService.getHistory(
				Arrays.asList(tansKpis), kpiType, beginDate, queryDate);
		if (null != showHistries && showHistries.size() > 0) {
			kpiNames.add("日期");
			kpiNames.addAll(showHistries.get(0).getKpiNames());
		}
		// 当前菜单的所有模块信息
		Map<String, Object> moduleInfoMap = pageSettingService
				.getModuleInfoByMenuIdAndDateType(menuId,
						Integer.parseInt(kpiType));
		map.put("overallKpiCode", overallKpiCode);
		map.put("childKpiCode", childKpiCode);
		map.put("ispercent", ispercent);
		map.put("productId", productId);
		map.put("kpiType", kpiType);
		map.put("downloadkpiCodes", kpiCodes);// 用于下载
		map.put("mapCodeKpiInfo", mapCodeKpiInfo);
		map.put("kpiNames", kpiNames);
		map.put("grailKpis", grailKpis);
		map.put("showHistries", showHistries);
		map.put("queryDate", queryDate);
		map.put("menuId", menuId);
		map.put("modulemap", moduleInfoMap);
		this.insertLog(request, "查看产品ID为" + productId + "，时间为" + queryDate
				+ "的产品指标分析数据");
		return new ModelAndView("/prodAnalyze/kpiAnalyze", map);
	}

	private Boolean isChoose(DwpasCKpiInfo dwpasCKpiInfo, String kpiCodes[]) {
		if (null == kpiCodes || kpiCodes.length == 0) {
			return false;
		}
		for (String kpiCode : kpiCodes) {
			if (kpiCode.equals(dwpasCKpiInfo.getKpiCode())) {
				return true;
			}
		}
		return false;
	}

	private List<DwpasCKpiInfo> markCommonKpi(
			List<DwpasCKpiInfo> dwpasCKpiInfos, String kpiCode) {
		for (DwpasCKpiInfo dwpasCKpiInfo : dwpasCKpiInfos) {
			if (kpiCode.equalsIgnoreCase(dwpasCKpiInfo.getKpiCode())) {
				dwpasCKpiInfo.setMarked(true);
			}
		}
		return dwpasCKpiInfos;
	}
}
