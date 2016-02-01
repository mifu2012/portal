package com.infosmart.portal.action;

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

import com.infosmart.portal.pojo.CrossUserKpi;
import com.infosmart.portal.pojo.DwpasCComKpiInfo;
import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCPrdInfo;
import com.infosmart.portal.pojo.DwpasCSystemMenu;
import com.infosmart.portal.pojo.DwpasRColumnComKpi;
import com.infosmart.portal.pojo.KpiNameUnitRelation;
import com.infosmart.portal.pojo.ProdKpiInfoDTO;
import com.infosmart.portal.pojo.TrendListDTO;
import com.infosmart.portal.service.DwpasCColumnInfoService;
import com.infosmart.portal.service.DwpasCPrdInfoService;
import com.infosmart.portal.service.DwpasCSystemMenuService;
import com.infosmart.portal.service.DwpasPageSettingService;
import com.infosmart.portal.service.ProdRankService;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.SystemColumnEnum;
import com.infosmart.portal.vo.dwpas.UserCrossKpi;

/**
 * 产品龙虎榜
 * 
 * @author gentai.huang
 * 
 */
@Controller
public class ProdRankController extends BaseController {
	/** 产品龙虎榜展现页面 */
	private static final String JSP_PROD_RANK = "/Productlhb/productlhb";
	/**
	 * 产品龙虎榜服务
	 */
	@Autowired
	private DwpasCPrdInfoService prdInfoService;
	@Autowired
	private ProdRankService prodRankService;
	@Autowired
	private DwpasPageSettingService pageSettingService;
	@Autowired
	private DwpasCSystemMenuService menuService;
	@Autowired
	private DwpasCColumnInfoService dwpasCColumnInfoService;

	/**
	 * 产品龙虎榜请求
	 * 
	 * @param req
	 * @param res
	 * @param prodRankForm
	 * @return
	 */
	@RequestMapping(value = "/Champion/getChampions")
	public ModelAndView doGet(HttpServletRequest req, HttpServletResponse res) {
		String menuId = req.getParameter("menuId");// 菜单ID
		String queryMonth = req.getParameter("queryMonth");
		if (StringUtils.notNullAndSpace(queryMonth)) {
			// 变更了日期
			this.setCrtQueryMonthOfReport(req, queryMonth);
		} else {
			// 取默认日期
			queryMonth = this.getCrtQueryMonthOfReport(req);
		}
		String queryValueDate = queryMonth.replace("-", "");

		// 平台交叉分析
		List<Object> crossUserKpiDatas = prodRankService
				.getCrossUserKpiDataByDate(queryValueDate);
		// 产品排行 月指标为3,加入根据模板ID得到关联的产品ID
		List<Object> prodKpiDatas = prodRankService.getProdKpiDatas(
				this.getCrtUserTemplateId(req), queryValueDate,
				String.valueOf(DwpasCKpiInfo.KPI_TYPE_OF_MONTH));
		List<KpiNameUnitRelation> kpiNameUnitRelations = null;
		List<ProdKpiInfoDTO> dataList = new ArrayList<ProdKpiInfoDTO>();
		if (prodKpiDatas == null || prodKpiDatas.isEmpty()) {
			kpiNameUnitRelations = new ArrayList<KpiNameUnitRelation>();
		} else {
			kpiNameUnitRelations = (List<KpiNameUnitRelation>) prodKpiDatas
					.get(0);
		}
		if (prodKpiDatas != null && prodKpiDatas.size()>1) {
			for(int n =1; n< prodKpiDatas.size(); n++){
				dataList.add((ProdKpiInfoDTO)prodKpiDatas.get(n));
			}
			req.getSession().setAttribute("dataList", dataList);
		}
		req.getSession().setAttribute("kpiNameList", kpiNameUnitRelations);
		

		// 栏目名称
		List<KpiNameUnitRelation> kpiNameUnitRelationList = crossUserKpiDatas == null
				|| crossUserKpiDatas.isEmpty() ? new ArrayList<KpiNameUnitRelation>()
				: (List<KpiNameUnitRelation>) crossUserKpiDatas.get(0);
				
		//平台交叉 表头信息
		req.getSession().setAttribute("platNameList", kpiNameUnitRelationList);
		//平台交叉数据信息
		List<UserCrossKpi> platDataList = new ArrayList<UserCrossKpi>();
		if(crossUserKpiDatas != null && crossUserKpiDatas.size()>1){
			for(int n=1; n<crossUserKpiDatas.size(); n++){
				platDataList.add((UserCrossKpi)crossUserKpiDatas.get(n));
			}
			req.getSession().setAttribute("platDataList", platDataList);
		}

		// 平台交叉分析趋势图
		List<TrendListDTO> leftList = prodRankService.init();
		// 当前菜单的所有模块信息
		Map<String, Object> moduleInfoMap = pageSettingService
				.getModuleInfoByMenuIdAndDateType(menuId,
						DwpasCKpiInfo.KPI_TYPE_OF_MONTH);
		Map<String, Object> map = new HashMap<String, Object>();
		// 菜单信息(跳入产品健康度需要用到)
		Map<String, String> menumap = new HashMap<String, String>();
		List<DwpasCSystemMenu> menulist = menuService.listOneDwpasCSystemMenu(
				Integer.parseInt(this.getCrtUserTemplateId(req)), "2");
		if (menulist != null && menulist.size() > 0) {
			for (DwpasCSystemMenu menu : menulist) {
				menumap.put("m_" + menu.getMenuCode(), menu.getMenuId());
			}
		}
		map.put("menuId", menuId);
		map.put("dateTime", queryMonth);
		map.put("date", queryValueDate);
		map.put("crossUserKpiDatas", crossUserKpiDatas);// 上部用户
		map.put("prodKpiDatas", prodKpiDatas);// 下部产品排行
		map.put("kpiNameUnitRelationList", kpiNameUnitRelationList);
		map.put("kpiNameUnitRelations", kpiNameUnitRelations);
		
		map.put("leftList", leftList);
		map.put("menuId", menuId);
		map.put("modulemap", moduleInfoMap);
		map.put("menumap", menumap);
		this.insertLog(req, "查看产品龙虎榜");
		return new ModelAndView(JSP_PROD_RANK, map);
	}

	/**
	 * 平台交叉趋势图
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/Champion/crossPlatformStock")
	public ModelAndView crossPlatformStock(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		String date = req.getParameter("date");
		// 平台交叉分析趋势图
		List<TrendListDTO> leftList = prodRankService.init();

		map.put("leftList", leftList);
		map.put("date", date);
		this.insertLog(req, "查看产品龙虎榜：平台交叉趋势图");
		return new ModelAndView("/Productlhb/crossPlatformStock", map);
	}

	/**
	 * 产品趋势图
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/Champion/prdRankStock")
	public ModelAndView prdRankStock(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		String date = req.getParameter("date");
		
		// 所有龙虎榜可显示产品
		List<DwpasCPrdInfo> productInfoList = this.prdInfoService
				.getDwpasCPrdInfoList(this.getCrtUserTemplateId(req), null);
		
		// 所有龙虎榜可显示通用指标
//		DwpasCComKpiInfo dwpasCComKpiInfo = new DwpasCComKpiInfo();
//		dwpasCComKpiInfo.setIsShowRank("1");
//		List<DwpasCComKpiInfo> comKpiInfoList = this.comKpiInfoService
//				.listDwpasCComKpiInfo(dwpasCComKpiInfo);
		List<DwpasRColumnComKpi> columnRComKpiList = dwpasCColumnInfoService
				.getCommonCodeByColumnCode(this.getCrtUserTemplateId(req),
						SystemColumnEnum.LHB_PRODUCT_RANKING.getColumnCode());
		
		map.put("productInfoList", productInfoList);
		map.put("columnRComKpiList", columnRComKpiList);
		map.put("date", date);
		this.insertLog(req, "查看产品龙虎榜：产品趋势图");
		return new ModelAndView("/Productlhb/proRankStock", map);
	}
	@RequestMapping(value="/Champion/excelDown")
	public ModelAndView excelDown(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<KpiNameUnitRelation> kpiNameList =(List<KpiNameUnitRelation>) req.getSession().getAttribute("kpiNameList");
		List<ProdKpiInfoDTO> dataList =(List<ProdKpiInfoDTO>) req.getSession().getAttribute("dataList");
		List<String> excelHeadInfo = new ArrayList<String>();
		List<List<String>> excelDataList = new ArrayList<List<String>>();
		if(kpiNameList!= null && kpiNameList.size()>0){
			excelHeadInfo.add("产品名称");
			for(int i=0; i<kpiNameList.size(); i++){
				excelHeadInfo.add(kpiNameList.get(i).getKpiName()+kpiNameList.get(i).getUnit());
			}
			map.put("excelHeadInfo", excelHeadInfo);
		}
		
		if(dataList!= null && dataList.size()>0){
			
			for(int i=0; i<dataList.size(); i++){
				List<String> dataInfos = new ArrayList<String>();
				dataInfos.add(dataList.get(i).getProdName());
				for(int j=0; j<dataList.get(i).getKpiDatas().size(); j++){
					dataInfos.add(dataList.get(i).getKpiDatas().get(j).getShowValue().toString());
				}
				excelDataList.add(dataInfos);
			}
			map.put("excelData", excelDataList);
		}
		map.put("fileName", "prdRan");
		return new ModelAndView(new ExcelDownloadController(), map);
	}
	
	
	@RequestMapping(value="/Champion/excelDownPlatform")
	public ModelAndView excelDownPlatform(HttpServletRequest req,
			HttpServletResponse res) {
		String reportDate = this.getCrtQueryMonthOfReport(req);
		Map<String, Object> map = new HashMap<String, Object>();
		List<KpiNameUnitRelation> kpiNameList =(List<KpiNameUnitRelation>) req.getSession().getAttribute("platNameList");
		List<UserCrossKpi> dataList =(List<UserCrossKpi>) req.getSession().getAttribute("platDataList");
		List<String> excelHeadInfo = new ArrayList<String>();
		List<List<String>> excelDataList = new ArrayList<List<String>>();
		if(kpiNameList!= null && kpiNameList.size()>0){
			excelHeadInfo.add("平台名称");
			for(int i=0; i<kpiNameList.size(); i++){
				String unit = kpiNameList.get(i).getUnit()==null ? "": kpiNameList.get(i).getUnit();
				excelHeadInfo.add(kpiNameList.get(i).getKpiName()+"("+unit+")");
			}
			map.put("excelHeadInfo", excelHeadInfo);
		}
		
		if(dataList!= null && dataList.size()>0){
			
			for(int i=0; i<dataList.size(); i++){
				List<String> dataInfos = new ArrayList<String>();
				dataInfos.add(dataList.get(i).getFlatName());
				for(int j=0; j<dataList.get(i).getKpiDatas().size(); j++){
					String data = dataList.get(i).getKpiDatas().get(j)== null ? "0" : dataList.get(i).getKpiDatas().get(j).getShowValue().toString();
					dataInfos.add(data);
				}
				excelDataList.add(dataInfos);
			}
			map.put("excelData", excelDataList);
		}
		map.put("fileName", "平台交叉信息"+"_"+reportDate);
		return new ModelAndView(new ExcelDownloadController(), map);
	}
	
}
