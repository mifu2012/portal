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
import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCPrdInfo;
import com.infosmart.portal.pojo.DwpasCSystemMenu;
import com.infosmart.portal.pojo.DwpasCUserActionInfo;
import com.infosmart.portal.pojo.UserAction;
import com.infosmart.portal.service.CrossUserService;
import com.infosmart.portal.service.DwpasPageSettingService;
import com.infosmart.portal.service.ProdInfoService;
import com.infosmart.portal.service.UserActionService;
import com.infosmart.portal.util.StringUtils;

/**
 * 用户行为
 * 
 * @author Administrator
 * 
 */
@Controller
public class UserActionController extends BaseController {
	/** 交叉用户行为数 */
	private final String CROSS_COUNT = "1";

	/** 交叉用户行为占比 */
	private final String CROSS_RATE = "2";

	// 用户行为交叉趋势图
	private final String CROSS_USER_CHART = "2";

	@Autowired
	private ProdInfoService prodInfoService;

	@Autowired
	private CrossUserService crossUserService;
	@Autowired
	private UserActionService userActionService;
	@Autowired
	private DwpasPageSettingService pageSettingService;

	@RequestMapping("/UserAction/doGet")
	public ModelAndView doGet(HttpServletRequest request,
			HttpServletResponse response) {
		String menuId = request.getParameter("menuId");// 菜单ID
		Map map = new HashMap();
		// 产品ID，默认从页面参数取产品ID（用于选择产品）
		/*
		 * String productId = request.getParameter("productId"); if
		 * (!StringUtils.notNullAndSpace(productId)) { //
		 * 如果没取到产品ID,则取session中产品ID，初始是web.xml定义的的产品ID productId =
		 * this.getCrtProductIdOfReport(request); } else { //
		 * 选择了其他的产品,保存在session中 this.setCrtProductIdOfReport(request,
		 * productId); }
		 */
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
		if (StringUtils.notNullAndSpace(queryDate)) {
			queryDate = queryDate.replace("-", "");
		}
		Integer kpiType = null;
		try {
			kpiType = Integer.valueOf(request.getParameter("kpiType"));
		} catch (Exception e) {
			kpiType = DwpasCKpiInfo.KPI_TYPE_OF_MONTH;
		}

		// 500w新加内容：用户行为
		String productId = "";

		productId = request.getParameter("userActionId");
		if (productId == null || productId.length() == 0) {
			productId = "10000001";
		}
		List<CrossUser> crossUsers = crossUserService.getCrossUserActionInfo(
				productId, queryDate);
		DwpasCPrdInfo userActionInfo = dwpasCPrdInfoService
				.getDwpasCPrdInfoByProductId(productId);
		List<DropDownList> rightList = new ArrayList<DropDownList>();
		DropDownList count = new DropDownList();
		count.setName("交叉用户数");
		count.setValue(CROSS_COUNT);
		rightList.add(count);
		DropDownList rate = new DropDownList();
		rate.setName("交叉用户占比");
		rate.setValue(CROSS_RATE);
		rightList.add(rate);
		// 当前菜单对应的父菜单信息
		DwpasCSystemMenu parentMenu = pageSettingService
				.getDwpasCSystemByChildMenuId(menuId);
		// 当前菜单的所有模块信息
		Map<String, Object> moduleInfoMap = pageSettingService
				.getModuleInfoByMenuIdAndDateType(menuId, kpiType);
		map.put("userActionInfo", userActionInfo);
		map.put("productId", productId);
		map.put("type", CROSS_USER_CHART);
		map.put("rightList", rightList);
		map.put("crossUsers", crossUsers);
		map.put("date", date);
		map.put("menuId", menuId);
		map.put("modulemap", moduleInfoMap);
		map.put("parentMenu", parentMenu);
		this.insertLog(request, "查询用户行为Code" + productId + "，时间为" + date
				+ "的用户行为数据");
		return new ModelAndView("UserAction/userAction", map);
	}

}
