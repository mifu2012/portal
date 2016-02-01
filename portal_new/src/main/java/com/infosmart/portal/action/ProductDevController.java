package com.infosmart.portal.action;

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

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCSystemMenu;
import com.infosmart.portal.pojo.DwpasStKpiData;
import com.infosmart.portal.service.DwpasCColumnInfoService;
import com.infosmart.portal.service.DwpasPageSettingService;
import com.infosmart.portal.service.ProductDevService;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.SystemColumnEnum;

/**
 * 产品发展
 * 
 * 
 */
@Controller
public class ProductDevController extends BaseController {
	@Autowired
	private ProductDevService productDevService;

	@Autowired
	protected DwpasCColumnInfoService columnInfoService;
	@Autowired
	private DwpasPageSettingService pageSettingService;

	@RequestMapping("/ProductDev/doPost")
	public ModelAndView doPost(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 菜单ID
		String menuId = request.getParameter("menuId");
		this.logger.info("进入产品发展页面:" + menuId);
		DwpasCSystemMenu parentMenu = pageSettingService
				.getDwpasCSystemByChildMenuId(menuId);// 父菜单信息
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String productId = request.getParameter("productId");
		if (StringUtils.notNullAndSpace(productId)) {
			// 变更了产品ID
			this.setCrtProductIdOfReport(request, productId);
		} else {
			productId = this.getCrtProductIdOfReport(request);
			if (!StringUtils.notNullAndSpace(productId)) {
				return new ModelAndView("/common/noProduct");
			}
		}
		String kpiType = request.getParameter("kpiType");
		int queryType = DwpasCKpiInfo.KPI_TYPE_OF_DAY;
		if (!StringUtils.notNullAndSpace(kpiType)) {
			queryType = DwpasCKpiInfo.KPI_TYPE_OF_DAY;
		} else {
			queryType = Integer.valueOf(kpiType);
		}
		// 查询日期
		String queryDate = request.getParameter("queryDate");
		if (queryType == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
			if (!StringUtils.notNullAndSpace(queryDate)) {
				// 默认是取session中日期，初始是当前日期
				queryDate = this.getCrtQueryDateOfReport(request);
			} else {
				// 变更为其他的日期
				this.setCrtQueryDateOfReport(request, queryDate);
			}

		} else if (queryType == DwpasCKpiInfo.KPI_TYPE_OF_MONTH) {
			if (!StringUtils.notNullAndSpace(queryDate)) {
				// 如果为空的话,则取session的queryMonth;
				queryDate = this.getCrtQueryMonthOfReport(request);

			} else {
				// 将月时间放入到session
				this.setCrtQueryMonthOfReport(request, queryDate);
			}
		}
		modelMap.put("date", queryDate);
		this.logger.info("查询日期：" + queryDate);
		this.logger.info("productId：" + productId + ",kpiType:" + queryType);
		modelMap.put("productId", productId);
		modelMap.put("kpiType", queryType);
		modelMap.put("queryDate", queryDate.replace("-", ""));
		modelMap.put("endDate", queryDate.replace("-", ""));
		modelMap.put("productName",
				productDevService.queryProdNameByID(productId));
		// 当前菜单的所有模块信息
		Map<String, Object> moduleInfoMap = pageSettingService
				.getModuleInfoAndColumnInfoByMenuIdAndDateType(menuId,
						queryType);
		Map<String, List<DwpasCKpiInfo>> columnAndKpiInfoMap = null;
		Map<String, DwpasStKpiData> columnAndKpiDataMap = new HashMap<String, DwpasStKpiData>();
		List<String> columnCodeList = new ArrayList<String>();
		// 按日
		if (queryType == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
			// 业务量-按日
			columnCodeList
					.add(SystemColumnEnum.PRODUCT_TOTAL_TRANSACTIONS_AMOUNT
							.getColumnCode());
			// 业务笔数，按日
			columnCodeList
					.add(SystemColumnEnum.PRODUCT_TOTAL_TRANSACTIONS_TIMES
							.getColumnCode());
			// 产品用户数-按日
			columnCodeList.add(SystemColumnEnum.PRODUCT_DAY_USER_COUNT
					.getColumnCode());
			// 累计产品用户数-按日
			columnCodeList.add(SystemColumnEnum.PRODUCT_TOTAL_USER_COUNT
					.getColumnCode());
			// 户均金额-按日
			columnCodeList.add(SystemColumnEnum.PRODUCT_PER_USER_ADMOUNT
					.getColumnCode());
			// 户均笔数-按日
			columnCodeList.add(SystemColumnEnum.PRODUCT_PER_USER_TIMES
					.getColumnCode());
			// 栏目及关联的指标信息
			columnAndKpiInfoMap = this.columnInfoService
					.listColumnInfoByPrdAndColumnCode(
							this.getCrtUserTemplateId(request), columnCodeList,
							productId, DwpasCKpiInfo.KPI_TYPE_OF_DAY);
			if (columnAndKpiInfoMap != null
					&& columnAndKpiInfoMap.entrySet().size() > 0) {
				//
				Entry<String, List<DwpasCKpiInfo>> entry = null;
				// 指标编码
				List<DwpasCKpiInfo> kpiInfoList = new ArrayList<DwpasCKpiInfo>();
				for (Iterator it = columnAndKpiInfoMap.entrySet().iterator(); it
						.hasNext();) {
					entry = (Entry<String, List<DwpasCKpiInfo>>) it.next();
					if (entry.getValue() != null) {
						// this.logger.info("此栏目有关联指标:" + entry.getKey());
						kpiInfoList.addAll(entry.getValue());
					}
				}
				// 指标code及关联的指标数据
				Map<String, List<DwpasStKpiData>> kpiCodeAndKpiDataMap = this.dwpasStKpiDataService
						.listDwpasStKpiDataByKpiCode(kpiInfoList,
								queryDate.replace("-", ""),
								queryDate.replace("-", ""),
								DwpasCKpiInfo.KPI_TYPE_OF_DAY);
				// 栏目及关联的指标数据<栏目编码，指标数据>
				// Map<String, DwpasStKpiData> columnAndKpiDataMap = new
				// HashMap<String, DwpasStKpiData>();
				String columnCode = "";
				DwpasCKpiInfo kpiInfo = null;
				for (Iterator it = columnAndKpiInfoMap.entrySet().iterator(); it
						.hasNext();) {
					if (entry == null)
						continue;
					entry = (Entry<String, List<DwpasCKpiInfo>>) it.next();
					// 栏目编码
					columnCode = entry.getKey();
					//
					if (entry.getValue() != null && !entry.getValue().isEmpty()) {
						kpiInfo = entry.getValue().get(0);
						if (kpiInfo != null
								&& kpiCodeAndKpiDataMap.containsKey(kpiInfo
										.getKpiCode())) {
							// // 栏目及关联的指标数据<栏目编码，指标数据>
							// this.logger.info("此栏目有数据:" + columnCode);
							columnAndKpiDataMap.put(
									columnCode,
									kpiCodeAndKpiDataMap.get(
											kpiInfo.getKpiCode()).get(0));
						}
					}

				}
			}
		}
		// 按月
		else if (queryType == DwpasCKpiInfo.KPI_TYPE_OF_MONTH) {
			// //////////////// 产品使用量增长趋势_业务量_月指标
			// 业务量
			columnCodeList
					.add(SystemColumnEnum.PRODUCT_MONTH_TOTAL_TRANSACTIONS_AMOUNT_NEW
							.getColumnCode());
			// /////////////产品使用量增长趋势_业务笔数_月指标
			// 业务笔数
			columnCodeList
					.add(SystemColumnEnum.PRODUCT_MONTH_TOTAL_TRANSACTIONS_TIMES_NEW
							.getColumnCode());
			// ///////////////产品使用量增长趋势_产品用户数_月指标
			// 产品用户数
			columnCodeList.add(SystemColumnEnum.PRODUCT_MONTH_USER_COUNT_NEW
					.getColumnCode());
			// ///////////////产品使用量增长趋势_累计产品用户数_月指标
			// 累计产品用户数
			columnCodeList
					.add(SystemColumnEnum.PRODUCT_MONTH_TOTAL_USER_COUNT_NEW
							.getColumnCode());
			// //////////////产品使用量增长趋势_户均金额_月指标
			// 户均金额
			columnCodeList
					.add(SystemColumnEnum.PRODUCT_MONTH_PER_USER_ADMOUNT_NEW
							.getColumnCode());
			// /////////////产品使用量增长趋势_户均笔数_月指标
			// 户均笔数
			columnCodeList
					.add(SystemColumnEnum.PRODUCT_MONTH_PER_USER_TIMES_NEW
							.getColumnCode());
			// 栏目及关联的指标信息(调用产品发展专栏)
			columnAndKpiInfoMap = this.columnInfoService
					.listColumnInfoByPrdAndColumnCodeForPrdDevelopColumn(
							this.getCrtUserTemplateId(request), columnCodeList,
							productId, DwpasCKpiInfo.KPI_TYPE_OF_MONTH);
			if (columnAndKpiInfoMap != null
					&& columnAndKpiInfoMap.entrySet().size() > 0) {
				// 趋势图用到的栏目关联的指标信息
				modelMap.putAll(columnAndKpiInfoMap);
				//
				Entry<String, List<DwpasCKpiInfo>> entry = null;
				// 指标编码
				List<DwpasCKpiInfo> kpiInfoList = new ArrayList<DwpasCKpiInfo>();
				for (Iterator it = columnAndKpiInfoMap.entrySet().iterator(); it
						.hasNext();) {
					entry = (Entry<String, List<DwpasCKpiInfo>>) it.next();
					if (entry.getValue() != null) {
						kpiInfoList.addAll(entry.getValue());
					}
				}
				// 指标code及关联的指标数据
				Map<String, List<DwpasStKpiData>> kpiCodeAndKpiDataMap = this.dwpasStKpiDataService
						.listDwpasStKpiDataByKpiCode(kpiInfoList,
								queryDate.replace("-", ""),
								queryDate.replace("-", ""),
								DwpasCKpiInfo.KPI_TYPE_OF_MONTH);
				// 栏目及关联的指标数据<栏目编码，指标数据>
				// Map<String, DwpasStKpiData> columnAndKpiDataMap = new
				// HashMap<String, DwpasStKpiData>();
				String columnCode = "";
				DwpasCKpiInfo kpiInfo = null;
				for (Iterator it = columnAndKpiInfoMap.entrySet().iterator(); it
						.hasNext();) {
					if (entry == null)
						continue;
					entry = (Entry<String, List<DwpasCKpiInfo>>) it.next();
					// 栏目编码
					columnCode = entry.getKey();
					// this.logger.info("-------------->栏目CODE:" + columnCode);
					//
					if (entry.getValue() != null && !entry.getValue().isEmpty()) {
						kpiInfo = entry.getValue().get(0);
						if (kpiInfo != null
								&& kpiCodeAndKpiDataMap.containsKey(kpiInfo
										.getKpiCode())) {
							// // 栏目及关联的指标数据<栏目编码+栏目类型，指标数据>
							columnAndKpiDataMap.put(
									columnCode,
									kpiCodeAndKpiDataMap.get(
											kpiInfo.getKpiCode()).get(0));
						}
					}
				}
			}
		}
		// 栏目关联的指标
		Map<String, DwpasCKpiInfo> productKpiCodeMap = new HashMap<String, DwpasCKpiInfo>();
		for (String columnCode : columnCodeList) {
			if (columnAndKpiInfoMap == null || columnAndKpiInfoMap.isEmpty())
				break;
			if (columnAndKpiInfoMap.containsKey(columnCode)) {
				this.logger.info("找到该栏目对应的指标:" + columnCode + ","
						+ columnAndKpiInfoMap.get(columnCode).get(0));
				productKpiCodeMap.put(columnCode,
						columnAndKpiInfoMap.get(columnCode).get(0));
			} else if (columnAndKpiInfoMap.containsKey(columnCode + "_1")) {
				this.logger.info("找到该栏目对应的月指标:" + columnCode + "_01");
				productKpiCodeMap.put(columnCode + "_1", columnAndKpiInfoMap
						.get(columnCode + "_1").get(0));
			} else {
				this.logger.info("未找到该栏目对应的指标:" + columnCode);
				productKpiCodeMap.put(columnCode, new DwpasCKpiInfo());
				productKpiCodeMap.put(columnCode + "_1", new DwpasCKpiInfo());
			}
			// 默认数据
			if (columnAndKpiDataMap != null
					&& !columnAndKpiDataMap.containsKey(columnCode)) {
				columnAndKpiDataMap.put(columnCode, new DwpasStKpiData());
			} else if (columnAndKpiDataMap != null
					&& columnAndKpiDataMap.containsKey(columnCode)) {
				columnAndKpiDataMap.put(columnCode,
						columnAndKpiDataMap.get(columnCode));
			}
		}
		Map<String, Object> porductDataMap = new HashMap<String, Object>();
		// 栏目关联的数据
		porductDataMap.put("productData", modelMap);
		porductDataMap.put("productKpiData", columnAndKpiDataMap);
		porductDataMap.put("productKpiCode", productKpiCodeMap);
		porductDataMap.put("parentMenu", parentMenu);
		porductDataMap.put("menuId", menuId);
		porductDataMap.put("modulemap", moduleInfoMap);
		this.insertLog(request, "查看产品ID为" + productId + "，时间为" + queryDate
				+ "的产品发展数据");
		return new ModelAndView("/ProductDevelop/ProductDevelop",
				porductDataMap);

	}
}