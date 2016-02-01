package com.infosmart.portal.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.portal.pojo.ChannelInfo;
import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCSystemMenu;
import com.infosmart.portal.pojo.DwpasStKpiData;
import com.infosmart.portal.pojo.KpiNameUnitRelation;
import com.infosmart.portal.pojo.ProdKpiInfoDTO;
import com.infosmart.portal.pojo.UserExperienceInfo;
import com.infosmart.portal.service.DwpasPageSettingService;
import com.infosmart.portal.service.UserExperienceService;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.SystemColumnEnum;

/**
 * 用户体验
 * 
 * @author infosmart
 * 
 */
@Controller
public class UserExperienceController extends BaseController {
	static final int R = 75;

	@Autowired
	UserExperienceService userExperienceService;
	@Autowired
	private DwpasPageSettingService pageSettingService;

	@RequestMapping("/userExperience/showUserExperience")
	public ModelAndView showUserExperience(HttpServletRequest request,
			HttpServletResponse response) {
		String menuId = request.getParameter("menuId");
		DwpasCSystemMenu parentMenu = pageSettingService
				.getDwpasCSystemByChildMenuId(menuId);// 父菜单信息
		Map map = new HashMap();
		String productId = request.getParameter("productId");
		String queryDate = request.getParameter("queryDate");
		int kpiType = DwpasCKpiInfo.KPI_TYPE_OF_DAY;
		if (!StringUtils.notNullAndSpace(productId)) {
			productId = this.getCrtProductIdOfReport(request);
			if (!StringUtils.notNullAndSpace(productId)) {
				return new ModelAndView("/common/noProduct");
			}
		} else {
			this.setCrtProductIdOfReport(request, productId);
		}
		if (!StringUtils.notNullAndSpace(queryDate)) {
			queryDate = this.getCrtQueryDateOfReport(request);
		} else {
			this.setCrtQueryDateOfReport(request, queryDate);
		}
		List<String> columnCodeList = new ArrayList<String>();
		columnCodeList.add(SystemColumnEnum.KPI_COM_MEMBER_TOTAL_IN
				.getColumnCode());
		columnCodeList.add(SystemColumnEnum.KPI_COM_MEMBER_TOTAL_TRY
				.getColumnCode());
		columnCodeList.add(SystemColumnEnum.KPI_COM_MEMBER_TOTAL_SUC
				.getColumnCode());
		columnCodeList.add(SystemColumnEnum.KPI_COM_MEMBER_TOTAL_OLDANDNEW
				.getColumnCode());
		columnCodeList.add(SystemColumnEnum.KPI_COM_MEMBER_OLD_TRY
				.getColumnCode());
		columnCodeList.add(SystemColumnEnum.KPI_COM_MEMBER_OLD_SUC
				.getColumnCode());
		columnCodeList.add(SystemColumnEnum.KPI_COM_MEMBER_NEW_TRY
				.getColumnCode());
		columnCodeList.add(SystemColumnEnum.KPI_COM_MEMBER_NEW_SUC
				.getColumnCode());
		// <column_code,kpi_info>s
		Map<String, List<DwpasCKpiInfo>> dwpasCKpiInfoMap = this.columnInfoService
				.listColumnInfoByPrdAndColumnCode(
						this.getCrtUserTemplateId(request), columnCodeList,
						productId, kpiType);
		// <column_code,kpi_data>
		Map<String, DwpasStKpiData> kpiDataMapOfColumn = new HashMap<String, DwpasStKpiData>();
		Entry entry = null;
		if (dwpasCKpiInfoMap != null) {
			// kpi_code_list
			List<DwpasCKpiInfo> kpiInfoList = new ArrayList<DwpasCKpiInfo>();
			List<DwpasCKpiInfo> tempKpiList = null;
			for (Iterator it = dwpasCKpiInfoMap.entrySet().iterator(); it
					.hasNext();) {
				entry = (Entry) it.next();
				tempKpiList = (List<DwpasCKpiInfo>) entry.getValue();
				if (tempKpiList != null) {
					kpiInfoList.addAll(tempKpiList);
				}
			}
			// <kpi_code,kpi_data_list>
			Map<String, List<DwpasStKpiData>> kpiDataMap = this.dwpasStKpiDataService
					.listDwpasStKpiDataByKpiCode(kpiInfoList, queryDate,
							queryDate, DwpasCKpiInfo.KPI_TYPE_OF_DAY);
			List<DwpasStKpiData> kpiDataList = null;
			if (kpiDataMap != null) {
				for (Iterator it = dwpasCKpiInfoMap.entrySet().iterator(); it
						.hasNext();) {
					entry = (Entry) it.next();
					tempKpiList = (List<DwpasCKpiInfo>) entry.getValue();
					if (tempKpiList == null || tempKpiList.size() == 0)
						continue;
					kpiDataList = kpiDataMap.get(tempKpiList.get(0)
							.getKpiCode());
					if (kpiDataList != null && kpiDataList.size() > 0) {
						// entry_key:column_code
						// entry_value:kpi_code
						kpiDataMapOfColumn.put(entry.getKey().toString(),
								kpiDataList.get(kpiDataList.size() - 1));
					}
				}
			}
		}

		UserExperienceInfo vo = new UserExperienceInfo();
		try {
			// 获得用户数量转化String传到页面显示
			DwpasStKpiData totalInMemberData = kpiDataMapOfColumn
					.get(SystemColumnEnum.KPI_COM_MEMBER_TOTAL_IN
							.getColumnCode());
			// if (totalInMemberData == null) {
			// totalInMemberData = new DwpasStKpiData();
			// totalInMemberData.setBaseValue(new BigDecimal(1));
			// }
			DwpasStKpiData totalTryMemberData = kpiDataMapOfColumn
					.get(SystemColumnEnum.KPI_COM_MEMBER_TOTAL_TRY
							.getColumnCode());
			// if (totalTryMemberData == null) {
			// totalTryMemberData = new DwpasStKpiData();
			// totalTryMemberData.setBaseValue(new BigDecimal(1));
			// }
			DwpasStKpiData totalSucMemberData = kpiDataMapOfColumn
					.get(SystemColumnEnum.KPI_COM_MEMBER_TOTAL_SUC
							.getColumnCode());
			// if (totalSucMemberData == null)
			// totalSucMemberData = new DwpasStKpiData();
			DwpasStKpiData totalOldAndNewData = kpiDataMapOfColumn
					.get(SystemColumnEnum.KPI_COM_MEMBER_TOTAL_OLDANDNEW
							.getColumnCode());

			DwpasStKpiData oldTryMemberData = kpiDataMapOfColumn
					.get(SystemColumnEnum.KPI_COM_MEMBER_OLD_TRY
							.getColumnCode());
			// if (oldTryMemberData == null) {
			// oldTryMemberData = new DwpasStKpiData();
			// oldTryMemberData.setBaseValue(new BigDecimal(1));
			// }
			DwpasStKpiData oldSucMemberData = kpiDataMapOfColumn
					.get(SystemColumnEnum.KPI_COM_MEMBER_OLD_SUC
							.getColumnCode());
			// if (oldSucMemberData == null)
			// oldSucMemberData = new DwpasStKpiData();
			DwpasStKpiData newTryMemberData = kpiDataMapOfColumn
					.get(SystemColumnEnum.KPI_COM_MEMBER_NEW_TRY
							.getColumnCode());
			// if (newTryMemberData == null) {
			// newTryMemberData = new DwpasStKpiData();
			// newTryMemberData.setBaseValue(new BigDecimal(1));
			// }
			DwpasStKpiData newSucMemberData = kpiDataMapOfColumn
					.get(SystemColumnEnum.KPI_COM_MEMBER_NEW_SUC
							.getColumnCode());
			// if (newSucMemberData == null)
			// newSucMemberData = new DwpasStKpiData();
			java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
			vo.setTotalMemberIn(nf.format(totalInMemberData.getBaseValue()
					.doubleValue()));
			vo.setTotalTry(nf.format(totalTryMemberData.getBaseValue()
					.doubleValue()));
			vo.setTotalSuccess(nf.format(totalSucMemberData.getBaseValue()
					.doubleValue()));
			vo.setTotalOldAndNew(nf.format(totalOldAndNewData.getBaseValue()
					.doubleValue()));
			vo.setOldMemberTry(nf.format(oldTryMemberData.getBaseValue()
					.doubleValue()));
			vo.setOldMemberSuccess(nf.format(oldSucMemberData.getBaseValue()
					.doubleValue()));
			vo.setNewMemberTry(nf.format(newTryMemberData.getBaseValue()
					.doubleValue()));
			vo.setNewMemberSuccess(nf.format(newSucMemberData.getBaseValue()
					.doubleValue()));
			// 计算百分比
			vo.setTotalTryPer((totalTryMemberData.getBaseValue().divide(
					totalInMemberData.getBaseValue(), 4,
					BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100))
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			vo.setTotalSucPer((totalSucMemberData.getBaseValue().divide(
					totalTryMemberData.getBaseValue(), 4,
					BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100))
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			vo.setOldMemberTryPer((oldTryMemberData.getBaseValue().divide(
					totalOldAndNewData.getBaseValue(), 4,
					BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100))
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			vo.setOldMemberSucPer((oldSucMemberData.getBaseValue().divide(
					oldTryMemberData.getBaseValue(), 4,
					BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100))
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			vo.setNewMemberTryPer((newTryMemberData.getBaseValue().divide(
					totalOldAndNewData.getBaseValue(), 4,
					BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100))
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			vo.setNewMemberSucPer((newSucMemberData.getBaseValue().divide(
					newTryMemberData.getBaseValue(), 4,
					BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100))
					.setScale(2, BigDecimal.ROUND_HALF_UP));
			// 页面圆的半径计算
			vo.setTotalTryPie(getPieRForTotal(R, vo.getTotalTryPer()));
			vo.setTotalSucPie(getPieRForTotal(vo.getTotalTryPie(),
					vo.getTotalSucPer()));
			vo.setOldMemberTryPie(getPieR(R, vo.getOldMemberTryPer()));
			vo.setOldMemberSucPie(getPieR(vo.getOldMemberTryPie(),
					vo.getOldMemberSucPer()));
			vo.setNewMemberTryPie(getPieR(R, vo.getNewMemberTryPer()));
			vo.setNewMemberSucPie(getPieR(vo.getNewMemberTryPie(),
					vo.getNewMemberSucPer()));

		} catch (Exception e) {
			this.logger.error("获取数据失败:" + e.getMessage(), e);

		}
		// 引入渠道
		// List<ChannelInfo> channelInfo =
		// userExperienceService.listChannelInfo(
		// productId, queryDate);
		// 产品名称
		Map<String, Object> moduleInfoMap = pageSettingService
				.getModuleInfoAndColumnInfoByMenuIdAndDateType(menuId, kpiType);
		// map.put("channelInfo", channelInfo);
		map.put("vo", vo);
		map.put("queryDate", queryDate);
		map.put("parentMenu", parentMenu);
		map.put("menuId", menuId);
		map.put("modulemap", moduleInfoMap);
		map.put("productId", productId);
		this.insertLog(request, "查询产品ID为" + productId + "，时间为" + queryDate
				+ "的用户体验数据");
		return new ModelAndView("UserExperience/UserExperience", map);
	}

	/**
	 * 显示用户体验渠道引入数据情况
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/userExperience/showChannelInfo")
	public ModelAndView showChannelInfo(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String orderId = request.getParameter("orderId");
		String ascOrDesc = request.getParameter("ascOrDesc");
		String productId = request.getParameter("productId");
		String queryDate = request.getParameter("queryDate");
		if (!StringUtils.notNullAndSpace(productId)) {
			productId = this.getCrtProductIdOfReport(request);
			if (!StringUtils.notNullAndSpace(productId)) {
				return new ModelAndView("/common/noProduct");
			}
		} else {
			this.setCrtProductIdOfReport(request, productId);
		}
		if (!StringUtils.notNullAndSpace(queryDate)) {
			queryDate = this.getCrtQueryDateOfReport(request);
		} else {
			this.setCrtQueryDateOfReport(request, queryDate);
		}
		List<ChannelInfo> channelInfo = null;
		if (orderId != null && orderId.length() > 0) {
			channelInfo = userExperienceService.listChannelInfoOrderBy(
					productId, queryDate, orderId, ascOrDesc);
		} else {
			channelInfo = userExperienceService.listChannelInfo(productId,
					queryDate);
		}
		mv.addObject("channelInfo", channelInfo);
		mv.addObject("productId", productId);
		mv.addObject("queryDate", queryDate);
		mv.setViewName("UserExperience/channelInfo");
		return mv;
	}

	// 新老会员引入调用
	private int getPieR(double r, BigDecimal per) {

		int newR = (int) (r * Math.sqrt(per.doubleValue() / 100));
		if (newR < 5) {
			return 5;
		}
		if (newR > 35) {
			return 35;
		}
		return newR;

	}

	// 总体引入调用
	private int getPieRForTotal(double r, BigDecimal per) {

		int newR = (int) (r * Math.sqrt(per.doubleValue() / 100));
		if (newR < 5) {
			return 5;
		}
		if (newR > 75) {
			return 75;
		}
		return newR;

	}
	
	@RequestMapping(value="/userExperience/excelDown")
	public ModelAndView excelDown(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		String productId = req.getParameter("productId");
		String queryDate = req.getParameter("queryDate");
		List<String> excelHeadInfo = new ArrayList<String>();
		excelHeadInfo.add("渠道编号");
		excelHeadInfo.add("渠道描述");
		excelHeadInfo.add("引入会员数");
		excelHeadInfo.add("尝试使用会员数");
		excelHeadInfo.add("成功使用会员数");
		List<List<String>> excelDataList = new ArrayList<List<String>>();
		map.put("excelHeadInfo", excelHeadInfo);
		List<ChannelInfo> channelInfoList = userExperienceService.listAllChannelInfo(productId, queryDate);
		
		if(channelInfoList!= null && channelInfoList.size()>0){
			
			for(int i=0; i<channelInfoList.size(); i++){
				List<String> dataInfos = new ArrayList<String>();
				dataInfos.add(channelInfoList.get(i).getCampCode());
				dataInfos.add(channelInfoList.get(i).getCampMethod());
				dataInfos.add(Long.toString(channelInfoList.get(i).getCampUv()));
				dataInfos.add(Long.toString(channelInfoList.get(i).getCreateUser()));
				dataInfos.add(Long.toString(channelInfoList.get(i).getSuccUser()));
				
				excelDataList.add(dataInfos);
			}
			map.put("excelData", excelDataList);
		}
		map.put("fileName", "channelInfo");
		return new ModelAndView(new ExcelDownloadController(), map);
	}
	
	
}
