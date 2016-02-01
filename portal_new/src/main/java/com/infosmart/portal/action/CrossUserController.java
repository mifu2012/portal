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

import com.infosmart.portal.pojo.CrossUser;
import com.infosmart.portal.pojo.DropDownList;
import com.infosmart.portal.pojo.DwpasCSystemMenu;
import com.infosmart.portal.service.CrossUserService;
import com.infosmart.portal.service.DwpasPageSettingService;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.pojo.DwpasCKpiInfo;

/**
 * 场景交叉
 * 
 * @author Administrator
 * 
 */
@Controller
public class CrossUserController extends BaseController {
	/** 交叉用户数 */
	private final String CROSS_COUNT = "1";

	/** 交叉用户占比 */
	private final String CROSS_RATE = "2";

	// 场景交叉趋势图
	private final String CROSS_USER_CHART = "2";

	@Autowired
	private CrossUserService crossUserService;
	@Autowired
	private DwpasPageSettingService pageSettingService;

	@RequestMapping("/CrossUser/doGet")
	public ModelAndView doGet(HttpServletRequest request,
			HttpServletResponse response) {
		String menuId = request.getParameter("menuId");// 菜单ID
		Map map = new HashMap();
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
		// 查询月份,从URL参数取得(用于变更日期)
		String queryDate = request.getParameter("queryDate");
		if (!StringUtils.notNullAndSpace(queryDate)) {
			// 默认是取session中日期，初始是当前日期
			queryDate = this.getCrtQueryMonthOfReport(request);
		} else {
			// 变更为其他的日期
			this.setCrtQueryMonthOfReport(request, queryDate);
		}
		String date = queryDate;
		queryDate = queryDate.replace("-", "");
		Integer kpiType = null;
		try {
			kpiType = Integer.valueOf(request.getParameter("kpiType"));
		} catch (Exception e) {
			kpiType = DwpasCKpiInfo.KPI_TYPE_OF_MONTH;
		}

		List<CrossUser> crossUsers = crossUserService.getCrossUserInfo(
				this.getCrtUserTemplateId(request), productId, queryDate);

		// 当前菜单对应的父菜单信息
		DwpasCSystemMenu parentMenu = pageSettingService
				.getDwpasCSystemByChildMenuId(menuId);
		// 当前菜单的所有模块信息
		Map<String, Object> moduleInfoMap = pageSettingService
				.getModuleInfoByMenuIdAndDateType(menuId, kpiType);
		request.getSession().setAttribute("excelDataList", crossUsers);
		map.put("productId", productId);
		map.put("type", CROSS_USER_CHART);
		map.put("crossUsers", crossUsers);
		map.put("date", date);
		map.put("menuId", menuId);
		map.put("modulemap", moduleInfoMap);
		map.put("parentMenu", parentMenu);
		this.insertLog(request, "查询产品ID为" + productId + "，时间为" + date
				+ "的场景交叉数据");
		return new ModelAndView("CrossUser/crossUser", map);
	}

	/**
	 * 产品交叉情况趋势图
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/crossUser/showProductStock")
	public ModelAndView showProductStock(HttpServletRequest request,
			HttpServletResponse response) {
		String productId = request.getParameter("productId");
		String queryDate = request.getParameter("queryDate");
		List<CrossUser> crossUsers = crossUserService.getCrossUserInfo(
				this.getCrtUserTemplateId(request), productId, queryDate);
		List<DropDownList> rightList = new ArrayList<DropDownList>();
		DropDownList count = new DropDownList();
		count.setName("交叉用户数");
		count.setValue(CROSS_COUNT);
		rightList.add(count);
		DropDownList rate = new DropDownList();
		rate.setName("交叉用户占比");
		rate.setValue(CROSS_RATE);
		rightList.add(rate);
		Map map = new HashMap();
		map.put("crossUsers", crossUsers);
		map.put("rightList", rightList);
		map.put("productId", productId);
		map.put("date", queryDate);
		this.insertLog(request, "产品交叉情况趋势图");
		return new ModelAndView("CrossUser/productStock", map);
	}
	/**
	 * 产品交叉列表EXCEL导出
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/crossUser/excelDown")
	public ModelAndView excelDown(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		String productName = req.getParameter("productName");
		String reportDate = this.getCrtQueryMonthOfReport(req);
		if(StringUtils.notNullAndSpace(productName)){
			try {
				productName = java.net.URLDecoder.decode(productName,"utf-8");
				productName = new String(productName.getBytes("ISO-8859-1"), "UTF-8");
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		List<CrossUser> dataList =(List<CrossUser>) req.getSession().getAttribute("excelDataList");
		List<String> excelHeadInfo = new ArrayList<String>();
		List<List<String>> excelDataList = new ArrayList<List<String>>();
		excelHeadInfo.add("交叉产品名称");
		excelHeadInfo.add("交叉用户数(人)");
		excelHeadInfo.add("交叉度(%)");
		map.put("excelHeadInfo", excelHeadInfo);
		if(dataList != null && dataList.size()>0){
			for(int i=0; i<dataList.size(); i++){
				List<String> strList = new ArrayList<String>();
				strList.add(dataList.get(i).getRelProductName());
				strList.add(String.valueOf(dataList.get(i).getCrossUserCnt()<0 ? "-" : dataList.get(i).getCrossUserCnt()));
				strList.add(String.valueOf(dataList.get(i).getCrossUserRate()==null ? "-" : dataList.get(i).getCrossUserRate()));
				excelDataList.add(strList);
			}
			
		}
		map.put("excelData", excelDataList);
		map.put("fileName", productName+"产品交叉情况"+"_"+reportDate);
		return new ModelAndView(new ExcelDownloadController(), map);
	}
	
	
}
