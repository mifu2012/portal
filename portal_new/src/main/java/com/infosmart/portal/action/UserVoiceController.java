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
import com.infosmart.portal.pojo.DwpasRColumnComKpi;
import com.infosmart.portal.pojo.KpiNameUnitRelation;
import com.infosmart.portal.pojo.ProdKpiInfoDTO;
import com.infosmart.portal.pojo.UserAskTop;
import com.infosmart.portal.service.DwpasPageSettingService;
import com.infosmart.portal.service.UserAskTopService;
import com.infosmart.portal.util.StringUtils;

/**
 * 用户声音
 * 
 * @author infosmart
 * 
 */
@Controller
public class UserVoiceController extends BaseController {

	// 所有用户类型
	final String DEFAULT_USER = "99";
	// 月指标用户类型
	final String UNUSED_USER_MONTH = "0"; // 未使用用户
	final String NEW_USER_MONTH = "1"; // 新用户
	final String OLD_USER_MONTH = "2"; // 老用户
	final String SLEEP_USER_MONTH = "3"; // 休眠用户
	final String AWAY_USER_MONTH = "4"; // 流失用户
	// 日指标用户类型
	final String UNUSED_USER_DAY = "5"; // 未使用用户
	final String ONE_USER_DAY = "6"; // 一次使用用户
	final String MANY_USER_DAY = "7"; // 多次使用用户

	@Autowired
	private UserAskTopService userAskTopService;
    @Autowired
    private DwpasPageSettingService pageSettingService;
	/**
	 * 显示用户声音
	 * 
	 * @param request
	 * @param response
	 * @param productId
	 *            产品Id
	 * @param kpiType
	 *            指标类型
	 * @param queryDate
	 *            按日的日期
	 * @param queryMonth
	 *            按月的日期
	 */
	@RequestMapping("/userVoice/showUserVoice")
	public ModelAndView showUserVoice(HttpServletRequest request,
			HttpServletRequest response) {
		String menuId = request.getParameter("menuId");// 菜单ID
		DwpasCSystemMenu parentMenu = pageSettingService
				.getDwpasCSystemByChildMenuId(menuId);// 父菜单信息
		Map<String, Object> map = new HashMap<String, Object>();
		String productId = request.getParameter("productId");
		String queryDate = request.getParameter("queryDate");
		String queryMonth = request.getParameter("queryMonth");
		String kpiType = request.getParameter("kpiType");
		String date = "";
		if (!StringUtils.notNullAndSpace(productId)) {
			// 如果为空的话,则取session的产品Id;
			productId = this.getCrtProductIdOfReport(request);
			if(!StringUtils.notNullAndSpace(productId)){
				return new ModelAndView("/common/noProduct");
			}
		} else {
			this.logger.info("保存PROD_ID_IN SESSION.....");
			// 将产品Id放入session
			this.setCrtProductIdOfReport(request, productId);
		}
		if (!StringUtils.notNullAndSpace(queryDate)) {
			// 如果为空的话,则取session的queryDate;
			queryDate = this.getCrtQueryDateOfReport(request);
		} else {
			// 将日的时间放入session
			this.setCrtQueryDateOfReport(request, queryDate);
		}
		if (!StringUtils.notNullAndSpace(queryMonth)) {
			// 如果为空的话,则取session的queryMonth;
			queryMonth = this.getCrtQueryMonthOfReport(request);
		} else {
			// 将月时间放入到session
			this.setCrtQueryMonthOfReport(request, queryMonth);

		}
		if (!StringUtils.notNullAndSpace(kpiType)) {
			// 如果为空的话,则取日指标;
			kpiType = String.valueOf(DwpasCKpiInfo.KPI_TYPE_OF_DAY);
		}
		// 定义用户构成
		DwpasCColumnInfo userConstitute = new DwpasCColumnInfo();
		DwpasCColumnInfo helpRateColumn = new DwpasCColumnInfo();
		// 求助率
	    List<String> helplist = new ArrayList<String>();
		List<UserAskTop> defaultTopUserAsks = new ArrayList<UserAskTop>();
		// 日指标的处理
		if (kpiType.equals(String.valueOf(DwpasCKpiInfo.KPI_TYPE_OF_DAY))) {
			List<String> list = new ArrayList<String>();
			list.add("ASK_USER_CONSTITUTE_DAY_PIE_CHART");
			list.add("PRODUCR_ONE_USER_COUNT");
			list.add("ASK_USER_CONSTITUTE_MONTH_PIE_CHART");
			date = queryDate;
			userConstitute = this.columnInfoService
					.getColumnAndKpiInfoByColumnCodeAndPrdId(this
							.getCrtUserTemplateId(request),
							list, productId,
							DwpasCKpiInfo.KPI_TYPE_OF_DAY);

			// 日指标具体用户
			List<String> dayUserTypeList = new ArrayList<String>();
			dayUserTypeList.add(UNUSED_USER_DAY);
			dayUserTypeList.add(ONE_USER_DAY);
			dayUserTypeList.add(MANY_USER_DAY);
			dayUserTypeList.add(DEFAULT_USER);
			List<UserAskTop> userAskTops = userAskTopService.listUserTop10Date(
					productId, queryDate, dayUserTypeList);
			List<UserAskTop> unUser = new ArrayList<UserAskTop>();
			List<UserAskTop> oneUser = new ArrayList<UserAskTop>();
			List<UserAskTop> manyUser = new ArrayList<UserAskTop>();
			if (null != userAskTops && userAskTops.size() > 0) {
				for (UserAskTop theUserAskTop : userAskTops) {
					if (theUserAskTop.getUserType().equals(DEFAULT_USER)) {
						defaultTopUserAsks.add(theUserAskTop);
					} else if (theUserAskTop.getUserType().equals(ONE_USER_DAY)) {
						oneUser.add(theUserAskTop);
					} else if (theUserAskTop.getUserType().equals(
							UNUSED_USER_DAY)) {
						unUser.add(theUserAskTop);
					} else if (theUserAskTop.getUserType()
							.equals(MANY_USER_DAY)) {
						manyUser.add(theUserAskTop);
					}
				}
			}
			
			request.getSession().setAttribute("userAskTops_day", userAskTops);
			map.put("allUser", userAskTops);
			map.put("unUser", unUser);
			map.put("oneUser", oneUser);
			map.put("manyUser", manyUser);
			helplist.add("HELP_RISE_THREAD_DAY_CHART");
			 helpRateColumn = this.columnInfoService
					.getColumnAndKpiInfoByColumnCodeAndPrdId(
							this.getCrtUserTemplateId(request),
							helplist,
							productId, Integer.valueOf(kpiType));

		} else {// 月指标处理
			List<String> list = new ArrayList<String>();
			list.add("USER_VOICE_LOSS_COUNT");
			list.add("USER_VOICE_MONTH_NOTUSE_COUNT");
			list.add("USER_VOCIE_MONTH_NEW_USER_COUNT");
			list.add("USER_VOICE_MONTH_OLD_USER_COUNT");
			list.add("USER_VOICE_MONTH_SLEEP_USER_COUNT");
			date = queryMonth;
			userConstitute = this.columnInfoService
					.getColumnAndKpiInfoByColumnCodeAndPrdId(
							this.getCrtUserTemplateId(request),
							list, productId,
							DwpasCKpiInfo.KPI_TYPE_OF_MONTH);
			// 月指标具体用户
			List<String> monthUserTypeList = new ArrayList<String>();
			monthUserTypeList.add(DEFAULT_USER);
			monthUserTypeList.add(UNUSED_USER_MONTH);
			monthUserTypeList.add(NEW_USER_MONTH);
			monthUserTypeList.add(OLD_USER_MONTH);
			monthUserTypeList.add(SLEEP_USER_MONTH);
			monthUserTypeList.add(AWAY_USER_MONTH);
			List<UserAskTop> userAskTops = userAskTopService
					.listUserTop10Month(productId, queryMonth,
							monthUserTypeList);
			List<UserAskTop> unUsedUserMonth = new ArrayList<UserAskTop>();
			List<UserAskTop> newUser = new ArrayList<UserAskTop>();
			List<UserAskTop> oldUser = new ArrayList<UserAskTop>();
			List<UserAskTop> sleepUser = new ArrayList<UserAskTop>();
			List<UserAskTop> awayUser = new ArrayList<UserAskTop>();
			if (null != userAskTops && userAskTops.size() > 0) {
				for (UserAskTop theUserAskTop : userAskTops) {
					if (theUserAskTop.getUserType().equals(DEFAULT_USER)) {
						defaultTopUserAsks.add(theUserAskTop);
					} else if (theUserAskTop.getUserType().equals(
							UNUSED_USER_MONTH)) {
						unUsedUserMonth.add(theUserAskTop);
					} else if (theUserAskTop.getUserType().equals(
							NEW_USER_MONTH)) {
						newUser.add(theUserAskTop);
					} else if (theUserAskTop.getUserType().equals(
							OLD_USER_MONTH)) {
						oldUser.add(theUserAskTop);
					} else if (theUserAskTop.getUserType().equals(
							SLEEP_USER_MONTH)) {
						sleepUser.add(theUserAskTop);
					} else if (theUserAskTop.getUserType().equals(
							AWAY_USER_MONTH)) {
						awayUser.add(theUserAskTop);
					}
				}
			}
			map.put("unUserbyMonth", unUsedUserMonth);
			map.put("newUser", newUser);
			map.put("oldUser", oldUser);
			map.put("sleepUser", sleepUser);
			map.put("awayUser", awayUser);
			helplist.add("HELP_RISE_THREAD_MONTH_CHART");
			 helpRateColumn = this.columnInfoService
						.getColumnAndKpiInfoByColumnCodeAndPrdId(
								this.getCrtUserTemplateId(request),
								helplist,
								productId, Integer.valueOf(kpiType));
		}
		
		// 用户求助构成kpiCodes
		StringBuffer buffer = new StringBuffer();
		if(userConstitute !=null){
			if(userConstitute.getKpiInfoList()!=null && userConstitute.getKpiInfoList().size()>0){
				for(int i=0;i<userConstitute.getKpiInfoList().size();i++){
					DwpasCKpiInfo kpiInfo = userConstitute.getKpiInfoList().get(i);
					if(i == userConstitute.getKpiInfoList().size()-1){
						buffer.append(kpiInfo.getKpiCode());
					}else{
						buffer.append(kpiInfo.getKpiCode()+";");
					}
				}
			}
		}
		map.put("userConstituteCodes",buffer.toString());
		// 求助率kpiCode
		StringBuffer buffer1 = new StringBuffer();
		if(helpRateColumn !=null){
			if(helpRateColumn.getKpiInfoList()!=null && helpRateColumn.getKpiInfoList().size()>0){
				for(int i=0;i<helpRateColumn.getKpiInfoList().size();i++){
					DwpasCKpiInfo kpiInfo = helpRateColumn.getKpiInfoList().get(i);
					if(i == helpRateColumn.getKpiInfoList().size()-1){
						buffer1.append(kpiInfo.getKpiCode());
					}else{
						buffer1.append(kpiInfo.getKpiCode()+";");
					}
				}
			}
		}
		//判读needPercent的值
		String kpiCode=helpRateColumn == null ? "" : buffer1.toString();
		map.put("kpiCode", kpiCode);
		String [] kpiCodeList =kpiCode.split(",");
		int needPercent=0;
		if(kpiCodeList.length>1){
			needPercent=1;
		}
		map.put("needPercent", needPercent);
		// 产品名称
		map.put("defaultTopUserAsks", defaultTopUserAsks);
		map.put("date", date);
		map.put("kpiType", kpiType);
		map.put("productId", productId);
		String showDate = "";
		if (date.length() == 7) {
			showDate = date.substring(0, 4) + "年" + date.substring(5, 7) + "月";
		} else {
			showDate = date.substring(0, 4) + "年" + date.substring(5, 7) + "月"
					+ date.substring(8, 10) + "日";
		}
		// 当前菜单的所有模块信息
		Map<String, Object> moduleInfoMap = pageSettingService
				.getModuleInfoByMenuIdAndDateType(menuId, Integer.parseInt(kpiType));
		map.put("showDate", showDate);
		map.put("menuId",menuId);
		map.put("parentMenu", parentMenu);
		map.put("moduleInfoMap",moduleInfoMap);
		this.insertLog(request, "查询产品ID为" + productId + "，时间为" + date
				+ "的用户声音数据");
		return new ModelAndView("/UserVoice/UserVoice", map);
	}
	
	
	/**
	 * 求助问题列表EXCEL导出
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/userVoice/excelDown")
	public ModelAndView excelDown(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		String reportDate = this.getCrtQueryDateOfReport(req);
		String productName = req.getParameter("productName");
		if(StringUtils.notNullAndSpace(productName)){
			try {
				productName = java.net.URLDecoder.decode(productName,"utf-8");
				productName = new String(productName.getBytes("ISO-8859-1"), "UTF-8");
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		List<UserAskTop> dataList =(List<UserAskTop>) req.getSession().getAttribute("userAskTops_day");
		List<String> excelHeadInfo = new ArrayList<String>();
		excelHeadInfo.add("用户咨询的问题");
		excelHeadInfo.add("求助量");
		List<List<String>> excelDataList = new ArrayList<List<String>>();
		
		List<List<String>> allUserList = new ArrayList<List<String>>();
		allUserList.add(0, excelHeadInfo);
		List<List<String>> unUserList = new ArrayList<List<String>>();
		unUserList.add(0, excelHeadInfo);
		List<List<String>> onUserList = new ArrayList<List<String>>();
		onUserList.add(0, excelHeadInfo);
		List<List<String>> manyUserList = new ArrayList<List<String>>();
		manyUserList.add(0, excelHeadInfo);
		if(dataList!=null && dataList.size()>0){
			for(UserAskTop theUserAskTop : dataList){
				List<String> allUser = new ArrayList<String>();
				List<String> unUser = new ArrayList<String>();
				List<String> onUser = new ArrayList<String>();
				List<String> manyUser = new ArrayList<String>();
				if (theUserAskTop.getUserType().equals(DEFAULT_USER)) {
					allUser.add(theUserAskTop.getQuestion());
					allUser.add(String.valueOf(theUserAskTop.getSortId()));
				} else if (theUserAskTop.getUserType().equals(
						UNUSED_USER_DAY)) {
					unUser.add(theUserAskTop.getQuestion());
					unUser.add(String.valueOf(theUserAskTop.getSortId()));
				} else if (theUserAskTop.getUserType().equals(
						ONE_USER_DAY)) {
					onUser.add(theUserAskTop.getQuestion());
					onUser.add(String.valueOf(theUserAskTop.getSortId()));
				} else if (theUserAskTop.getUserType().equals(
						MANY_USER_DAY)) {
					manyUser.add(theUserAskTop.getQuestion());
					manyUser.add(String.valueOf(theUserAskTop.getSortId()));
				} 
				allUserList.add(allUser);
				unUserList.add(unUser);
				onUserList.add(onUser);
				manyUserList.add(manyUser);
			}
		}
		Map<String, List<List<String>>> dataListMap = new HashMap<String, List<List<String>>>();
		dataListMap.put("整体会员求助", allUserList);
		dataListMap.put("未使用会员求助", unUserList);
		dataListMap.put("一次使用会员求助", onUserList);
		dataListMap.put("多次使用会员求助", manyUserList);
		
		
		map.put("excelSheetList",dataListMap);
		
		map.put("fileName", productName+"用户求助情况"+"_"+reportDate);
		return new ModelAndView(new ExcelDownloadController(), map);
	}
	
	

	@RequestMapping("/userVoice/getUserType")
	public void getUserType(HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.info("--------------------->getUserType");
		String userType = "";
		String kpiType = request.getParameter("kpiType");
		String kpiCode = request.getParameter("kpiCode");
		String menuId = request.getParameter("menuId");
		String moduleCode = request.getParameter("moduleCode");
		System.out.println(menuId);
		if(kpiType.equals("1")){
			kpiType="D";
		}else if(kpiType.equals("3")){
			kpiType="M";
		}
		List<DwpasRColumnComKpi> dwpasRColumnComKpi = userAskTopService.queryComKpiInfoByColumnCodeAndKpiCode(menuId,moduleCode,kpiCode,kpiType);
		if(dwpasRColumnComKpi!=null&&dwpasRColumnComKpi.size()>0){
			userType=dwpasRColumnComKpi.get(0).getUserType();
		}else {
			userType="";
		}
		try {
			java.io.PrintWriter write = response.getWriter();
			write.println(userType);
			write.flush();
			write.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}

}
