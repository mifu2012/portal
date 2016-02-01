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

import com.infosmart.portal.pojo.DwpasCColumnInfo;
import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCSystemMenu;
import com.infosmart.portal.pojo.DwpasStKpiData;
import com.infosmart.portal.service.DwpasPageSettingService;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.SystemColumnEnum;

/**
 * 用户留存模块
 * 
 * @author Administrator
 * 
 */
@Controller
public class UserKeepController extends BaseController {
	@Autowired
	private DwpasPageSettingService pageSettingService;

	@RequestMapping("/UserKeep/showUserKeep")
	public ModelAndView showUserKeep(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		Map<String, Object> map = new HashMap<String, Object>();
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
		Integer kpiType = DwpasCKpiInfo.KPI_TYPE_OF_MONTH;
		try {
			kpiType = Integer.valueOf(request.getParameter("kpiType"));
		} catch (Exception e) {
			kpiType = DwpasCKpiInfo.KPI_TYPE_OF_MONTH;
		}
		// //////////////////全年用户产品构造情况
		List<String> yearProdConstrust = new ArrayList<String>();
		yearProdConstrust
				.add(SystemColumnEnum.PRODUCT_HEALTH_FULL_YEAR_USER_KEEP_PIE_CHART
						.getColumnCode());
		DwpasCColumnInfo dwpasCColumnInfo_Year = columnInfoService
				.getColumnAndKpiInfoByColumnCodeAndPrdId(
						this.getCrtUserTemplateId(request), yearProdConstrust,
						productId, kpiType);
		// 栏目名称
		// String columnName_Year = dwpasCColumnInfo_Year == null ? ""
		// : dwpasCColumnInfo_Year.getColumnName();
		// 全年用户产品构造情况关联的指标CODE
		String codes = dwpasCColumnInfo_Year == null ? ""
				: dwpasCColumnInfo_Year.getKpiCodes();
		// /////////////////上月新用户
		List<String> lastMonthNewUsers = new ArrayList<String>();
		lastMonthNewUsers
				.add(SystemColumnEnum.PRODUCT_HEALTH_LAST_MONTH_NEW_USER_VALUE
						.getColumnCode());
		DwpasCColumnInfo dwpas_beforeNewAmmount = columnInfoService
				.getColumnAndKpiInfoByColumnCodeAndPrdId(
						this.getCrtUserTemplateId(request), lastMonthNewUsers,
						productId, kpiType);
		// 栏目名称
		String columnName_New = "dwpas_beforeNewAmmount";
		try {
			columnName_New = dwpas_beforeNewAmmount == null ? ""
					: dwpas_beforeNewAmmount.getKpiInfoList() == null
							|| dwpas_beforeNewAmmount.getKpiInfoList()
									.isEmpty() ? "" : dwpas_beforeNewAmmount
							.getKpiInfoList().get(0).getDispName();
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}

		// 流失率饼图
		List<String> lastMonthLossUsers = new ArrayList<String>();
		lastMonthLossUsers
				.add(SystemColumnEnum.PRODUCT_HEALTH_LAST_MONTH_TOTAL_LOSE_USER_VALUE
						.getColumnCode());
		DwpasCColumnInfo dwpas_beforeAwayAmmount = columnInfoService
				.getColumnAndKpiInfoByColumnCodeAndPrdId(
						this.getCrtUserTemplateId(request), lastMonthLossUsers,
						productId, kpiType);
		DwpasStKpiData beforeAwayAmmount = null;
		if (dwpas_beforeAwayAmmount != null) {
			if (dwpas_beforeAwayAmmount.getKpiInfoList() != null
					&& dwpas_beforeAwayAmmount.getKpiInfoList().size() > 0) {
				beforeAwayAmmount = this.dwpasStKpiDataService
						.listDwpasStKpiDataAndKpiInfo(dwpas_beforeAwayAmmount
								.getKpiInfoList().get(0), queryDate);
			}
		}
		// 栏目名称
		String columnName_Away = "上月流失用户";
		try {
			columnName_Away = dwpas_beforeAwayAmmount == null ? ""
					: dwpas_beforeAwayAmmount.getKpiInfoList() == null
							|| dwpas_beforeAwayAmmount.getKpiInfoList()
									.isEmpty() ? "" : dwpas_beforeAwayAmmount
							.getKpiInfoList().get(0).getDispName();
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
		if (dwpas_beforeAwayAmmount != null
				&& dwpas_beforeAwayAmmount.getKpiInfoList() != null
				&& dwpas_beforeAwayAmmount.getKpiInfoList().size() > 0) {
			// 1
			String loseUserRateCode = dwpas_beforeAwayAmmount.getKpiInfoList()
					.get(0).getKpiCode();
			map.put("loseUserRateCode", loseUserRateCode);
		}

		// 新老用户构成饼图
		// ////////////////老用户数
		List<String> currSleepUsersKeep = new ArrayList<String>();
		currSleepUsersKeep
				.add(SystemColumnEnum.PRODUCT_HEALTH_LAST_MONTH_SLEEP_USER_RATE_PIE_CHART
						.getColumnCode());
		DwpasCColumnInfo lastMonthSleepUserRate = this.columnInfoService
				.getColumnAndKpiInfoByColumnCodeAndPrdId(
						this.getCrtUserTemplateId(request), currSleepUsersKeep,
						productId, kpiType);
		DwpasStKpiData beforeSleepRate = null;
		if (lastMonthSleepUserRate != null) {
			if (lastMonthSleepUserRate.getKpiInfoList() != null
					&& lastMonthSleepUserRate.getKpiInfoList().size() > 0) {
				beforeSleepRate = this.dwpasStKpiDataService
						.listDwpasStKpiDataAndKpiInfo(lastMonthSleepUserRate
								.getKpiInfoList().get(0), queryDate);
			}
		}
		if (lastMonthSleepUserRate != null
				&& lastMonthSleepUserRate.getKpiInfoList() != null
				&& lastMonthSleepUserRate.getKpiInfoList().size() > 0) {
			String oldUserCode = lastMonthSleepUserRate.getKpiInfoList().get(0)
					.getKpiCode();
			map.put("oldUserCode", oldUserCode);
		}
		// 新用户数
		List<String> lastMonthSleepUsers = new ArrayList<String>();
		lastMonthSleepUsers
				.add(SystemColumnEnum.PRODUCT_HEALTH_LAST_MONTH_SLEEP_USER_VALUE
						.getColumnCode());
		DwpasCColumnInfo dwpas_beforeSleepAmmount = columnInfoService
				.getColumnAndKpiInfoByColumnCodeAndPrdId(
						this.getCrtUserTemplateId(request),
						lastMonthSleepUsers, productId, kpiType);
		DwpasStKpiData beforeSleepAmmount = null;
		if (dwpas_beforeSleepAmmount != null) {
			if (dwpas_beforeSleepAmmount.getKpiInfoList() != null
					&& dwpas_beforeSleepAmmount.getKpiInfoList().size() > 0) {
				beforeSleepAmmount = this.dwpasStKpiDataService
						.listDwpasStKpiDataAndKpiInfo(dwpas_beforeSleepAmmount
								.getKpiInfoList().get(0), queryDate);
			}
		}
		String columnName_Sleep = "上月休眠用户";
		try {
			columnName_Sleep = dwpas_beforeSleepAmmount == null ? ""
					: dwpas_beforeSleepAmmount.getKpiInfoList().get(0)
							.getDispName();
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
		if (dwpas_beforeSleepAmmount != null
				&& dwpas_beforeSleepAmmount.getKpiInfoList() != null
				&& dwpas_beforeSleepAmmount.getKpiInfoList().size() > 0) {
			String newUserCode = dwpas_beforeSleepAmmount.getKpiInfoList()
					.get(0).getKpiCode();
			map.put("newUserCode", newUserCode);
		}

		// ////新用户构成
		// 转化用户数
		List<String> lastMonthNewUserCounts = new ArrayList<String>();
		lastMonthNewUserCounts
				.add(SystemColumnEnum.PRODUCT_HEALTH_LAST_MONTH_NEW_USER_VALUE
						.getColumnCode());
		DwpasCColumnInfo beforeNewUserColumnInfo = this.columnInfoService
				.getColumnAndKpiInfoByColumnCodeAndPrdId(
						this.getCrtUserTemplateId(request),
						lastMonthNewUserCounts, productId, kpiType);
		DwpasStKpiData beforeNewAmmountKpiData = null;
		if (beforeNewUserColumnInfo != null
				&& beforeNewUserColumnInfo.getKpiInfoList() != null
				&& !beforeNewUserColumnInfo.getKpiInfoList().isEmpty()) {
			// 查询指标的某日期指标数据
			beforeNewAmmountKpiData = this.dwpasStKpiDataService
					.listDwpasStKpiDataAndKpiInfo(beforeNewUserColumnInfo
							.getKpiInfoList().get(0), queryDate);
			String zhuanhuaUserCode = beforeNewUserColumnInfo.getKpiInfoList()
					.get(0).getKpiCode();
			map.put("zhuanhuaUserCode", zhuanhuaUserCode);
		}
		// 注册用户数
		List<String> lastMontOldUser = new ArrayList<String>();
		lastMontOldUser
				.add(SystemColumnEnum.PRODUCT_HEALTH_LAST_MONTH_OLD_USER_VALUE
						.getColumnCode());
		DwpasCColumnInfo dwpas_beforeOldAmmount = columnInfoService
				.getColumnAndKpiInfoByColumnCodeAndPrdId(
						this.getCrtUserTemplateId(request), lastMontOldUser,
						productId, kpiType);
		DwpasStKpiData beforeOldAmmount = null;
		if (dwpas_beforeOldAmmount != null) {
			if (dwpas_beforeOldAmmount.getKpiInfoList() != null
					&& dwpas_beforeOldAmmount.getKpiInfoList().size() > 0) {
				beforeOldAmmount = this.dwpasStKpiDataService
						.listDwpasStKpiDataAndKpiInfo(dwpas_beforeOldAmmount
								.getKpiInfoList().get(0), queryDate);
			}
		}
		// 栏目名称
		String columnName_Old = "上月老用户";
		try {
			columnName_Old = dwpas_beforeOldAmmount == null ? ""
					: dwpas_beforeNewAmmount.getKpiInfoList() == null
							|| dwpas_beforeOldAmmount.getKpiInfoList()
									.isEmpty() ? "" : dwpas_beforeOldAmmount
							.getKpiInfoList().get(0).getDispName();
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
		if (dwpas_beforeOldAmmount != null
				&& dwpas_beforeOldAmmount.getKpiInfoList() != null
				&& dwpas_beforeOldAmmount.getKpiInfoList().size() > 0) {
			// 注册用户
			String zhuceUserCode = dwpas_beforeOldAmmount.getKpiInfoList()
					.get(0).getKpiCode();
			map.put("zhuceUserCode", zhuceUserCode);
		}

		// /////////////老用户构成
		// 休眠用户数
		List<String> lastMonthNewUsersKeep = new ArrayList<String>();
		lastMonthNewUsersKeep
				.add(SystemColumnEnum.PRODUCT_HEALTH_LAST_MONTH_NEW_USER_RATE_PIE_CHART
						.getColumnCode());
		DwpasCColumnInfo lastMonthNewUserColumn = this.columnInfoService
				.getColumnAndKpiInfoByColumnCodeAndPrdId(
						this.getCrtUserTemplateId(request),
						lastMonthNewUsersKeep, productId, kpiType);
		DwpasStKpiData beforeNewRate = null;
		if (lastMonthNewUserColumn != null) {
			if (lastMonthNewUserColumn.getKpiInfoList() != null
					&& lastMonthNewUserColumn.getKpiInfoList().size() > 0) {
				beforeNewRate = this.dwpasStKpiDataService
						.listDwpasStKpiDataAndKpiInfo(lastMonthNewUserColumn
								.getKpiInfoList().get(0), queryDate);
			}
		}
		if (lastMonthNewUserColumn != null
				&& lastMonthNewUserColumn.getKpiInfoList() != null
				&& lastMonthNewUserColumn.getKpiInfoList().size() > 0) {
			String sleepUserCode = lastMonthNewUserColumn.getKpiInfoList()
					.get(0).getKpiCode();
			map.put("sleepUserCode", sleepUserCode);
		}

		// 活跃用户数
		List<String> lastMonthOldUsersKeep = new ArrayList<String>();
		lastMonthOldUsersKeep
				.add(SystemColumnEnum.PRODUCT_HEALTH_LAST_MONTH_OLD_USER_RATE_PIE_CHART
						.getColumnCode());
		DwpasCColumnInfo lastMOnthOldUserRateColumn = this.columnInfoService
				.getColumnAndKpiInfoByColumnCodeAndPrdId(
						this.getCrtUserTemplateId(request),
						lastMonthOldUsersKeep, productId, kpiType);
		DwpasStKpiData beforeOldRate = null;
		if (lastMOnthOldUserRateColumn != null) {
			if (lastMOnthOldUserRateColumn.getKpiInfoList() != null
					&& lastMOnthOldUserRateColumn.getKpiInfoList().size() > 0) {
				beforeOldRate = this.dwpasStKpiDataService
						.listDwpasStKpiDataAndKpiInfo(
								lastMOnthOldUserRateColumn.getKpiInfoList()
										.get(0), queryDate);
			}
		}
		if (lastMOnthOldUserRateColumn != null
				&& lastMOnthOldUserRateColumn.getKpiInfoList() != null
				&& lastMOnthOldUserRateColumn.getKpiInfoList().size() > 0) {
			String huoyueUserCode = lastMOnthOldUserRateColumn.getKpiInfoList()
					.get(0).getKpiCode();
			map.put("huoyueUserCode", huoyueUserCode);
		}

		// 复活用户
		List<String> currMonthLossUsersKeep = new ArrayList<String>();
		currMonthLossUsersKeep
				.add(SystemColumnEnum.PRODUCT_HEALTH_LAST_MONTH_TOTAL_LOSE_USER_RATE_PIE_CHART
						.getColumnCode());
		DwpasCColumnInfo lastMonthTotalLostUserColumn = this.columnInfoService
				.getColumnAndKpiInfoByColumnCodeAndPrdId(
						this.getCrtUserTemplateId(request),
						currMonthLossUsersKeep, productId, kpiType);
		DwpasStKpiData beforeAwayRate = null;
		if (lastMonthTotalLostUserColumn != null) {
			if (lastMonthTotalLostUserColumn.getKpiInfoList() != null
					&& lastMonthTotalLostUserColumn.getKpiInfoList().size() > 0) {
				this.logger.info("流失用户留存率关联指标："
						+ lastMonthTotalLostUserColumn.getKpiInfoList().get(0)
								.getKpiCode());
				beforeAwayRate = this.dwpasStKpiDataService
						.listDwpasStKpiDataAndKpiInfo(
								lastMonthTotalLostUserColumn.getKpiInfoList()
										.get(0), queryDate);
			}
		}
		if (lastMonthTotalLostUserColumn != null
				&& lastMonthTotalLostUserColumn.getKpiInfoList() != null
				&& lastMonthTotalLostUserColumn.getKpiInfoList().size() > 0) {
			String fuhuoUserCode = lastMonthTotalLostUserColumn
					.getKpiInfoList().get(0).getKpiCode();
			map.put("fuhuoUserCode", fuhuoUserCode);
		}

		// 趋势图
		List<String> qushitu = new ArrayList<String>();
		qushitu.add(SystemColumnEnum.PRODUCT_HEALTH_USER_TREND_CHART
				.getColumnCode());
		DwpasCColumnInfo stockCColumnInfo = columnInfoService
				.getColumnAndKpiInfoByColumnCodeAndPrdId(
						this.getCrtUserTemplateId(request), qushitu, productId,
						kpiType);
		// 栏目名称
		// String stockChartName = stockCColumnInfo == null ? ""
		// : stockCColumnInfo.getColumnName();
		String stockChartCodes = stockCColumnInfo == null ? ""
				: stockCColumnInfo.getKpiCodes();
		// 当前菜单的所有模块信息
		Map<String, Object> moduleInfoMap = pageSettingService
				.getModuleInfoByMenuIdAndDateType(menuId, kpiType);
		// // 父菜单信息
		DwpasCSystemMenu parentMenu = pageSettingService
				.getDwpasCSystemByChildMenuId(menuId);
		map.put("codes", codes);
		map.put("columnName_New", columnName_New);
		map.put("beforeNewAmmount", beforeNewAmmountKpiData);
		map.put("beforeNewRate", beforeNewRate);
		map.put("columnName_Old", columnName_Old);
		map.put("beforeOldAmmount", beforeOldAmmount);
		map.put("beforeOldRate", beforeOldRate);
		map.put("columnName_Away", columnName_Away);
		map.put("beforeAwayAmmount", beforeAwayAmmount);
		map.put("beforeAwayRate", beforeAwayRate);
		map.put("columnName_Sleep", columnName_Sleep);
		map.put("beforeSleepAmmount", beforeSleepAmmount);
		map.put("beforeSleepRate", beforeSleepRate);
		map.put("stockCColumnInfo", stockCColumnInfo);
		map.put("stockChartCodes", stockChartCodes);
		map.put("date", queryDate);
		map.put("menuId", menuId);
		map.put("moduleInfoMap", moduleInfoMap);
		map.put("parentMenu", parentMenu);
		this.insertLog(request, "查询产品ID为" + productId + "，时间为" + queryDate
				+ "的用户留存数据");
		return new ModelAndView("/ProdHealthKeep/prodHealthKeep", map);
	}

}
