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

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCModuleInfo;
import com.infosmart.portal.pojo.DwpasCSystemMenu;
import com.infosmart.portal.service.DwpasCSystemMenuService;
import com.infosmart.portal.service.DwpasPageSettingService;
import com.infosmart.portal.util.StringUtils;

/**
 * 跳转到指定的菜单（菜单在后台维护，可手工新增）
 * 
 * @author infosmart
 * 
 */
@Controller
public class JumpToNewMenuUrl extends BaseController {

	@Autowired
	private DwpasCSystemMenuService dwpasCSystemMenuService;

	@Autowired
	private DwpasPageSettingService pageSettingService;

	/**
	 * 转到指定的页面（用于后台新增的菜单）
	 * 
	 * @return
	 */
	@RequestMapping("/jumpToUrl/gotoNewPage")
	public ModelAndView gotoNewPage(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		// 获取模块名字===============================
		String menuId = request.getParameter("menuId");// 菜单ID
		this.logger.info("转到菜单ID:" + menuId);
		
		
		String productId = request.getParameter("productId");
		if (!StringUtils.notNullAndSpace(productId)) {
			productId = this.getCrtProductIdOfReport(request);
			if (!StringUtils.notNullAndSpace(productId)) {
				return new ModelAndView("/common/noProduct");
			}
		} else {
			this.setCrtProductIdOfReport(request, productId);
		}
		
		// 查询菜单信息
		DwpasCSystemMenu systemMenu = this.dwpasCSystemMenuService
				.getDwpasCSystemMenuById(menuId);
		if (systemMenu == null) {
			this.logger.warn("未取到菜单信息:" + menuId);
			systemMenu = new DwpasCSystemMenu();
		}
		this.logger.info("转到菜单:" + systemMenu.getMenuName() + ",日期类型:"
				+ systemMenu.getDateType());
		map.put("systemMenu", systemMenu);
		map.put("menuId", menuId);
		// 指标类型
		String kpiType = request.getParameter("kpiType");
		// 根据菜单的日期类型来取指标类型
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
}
