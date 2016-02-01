package com.infosmart.portal.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCPrdInfo;
import com.infosmart.portal.pojo.DwpasRColumnComKpi;
import com.infosmart.portal.pojo.DwpasStUserFeatureDs;
import com.infosmart.portal.service.DwpasCColumnInfoService;
import com.infosmart.portal.service.DwpasCKpiInfoService;
import com.infosmart.portal.service.DwpasCPrdInfoService;
import com.infosmart.portal.service.DwpasPageSettingService;
import com.infosmart.portal.service.DwpasStUserFeatureDsService;
import com.infosmart.portal.util.Const;
import com.infosmart.portal.util.MapConst;
import com.infosmart.portal.util.MathUtils;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.SystemColumnEnum;
import com.infosmart.portal.vo.dwpas.ProductUsingCharacter;

@Service
public class DwpasStUserFeatureDsServiceImpl extends BaseServiceImpl implements
		DwpasStUserFeatureDsService {
	@Autowired
	private DwpasCKpiInfoService dwpasCKpiInfoService;
	@Autowired
	protected DwpasCColumnInfoService columnInfoService;
	@Autowired
	private DwpasCPrdInfoService dwpasCPrdInfoService;
	@Autowired
	private DwpasPageSettingService pageSettingService;

	public List<DwpasStUserFeatureDs> listDwpasStUserFeatureDs(
			String productId, String reportDate, int featureType) {
		this.logger.info("查询某产品某时间的某特征数据" + productId);
		this.logger.debug("产品ID:" + productId);
		this.logger.debug("统计日期:" + reportDate);
		this.logger.debug("特征类型:" + featureType);
		if (!StringUtils.notNullAndSpace(productId)) {
			this.logger.warn("查询某产品某时间的某特征数据失败，产品ID为空");
			return null;
		}
		if (!StringUtils.notNullAndSpace(String.valueOf(featureType))) {
			this.logger.warn("查询某产品某时间的某特征数据失败，用户类型为空");
			return null;
		}
		if (!StringUtils.notNullAndSpace(reportDate)) {
			this.logger.warn("查询某产品某时间的某特征数据失败，日期为空");
			return null;
		}
		// 查询参数
		DwpasStUserFeatureDs dwpasStUserFeatureDs = new DwpasStUserFeatureDs();
		dwpasStUserFeatureDs.setReportDate(reportDate);
		dwpasStUserFeatureDs.setFeatureType(String.valueOf(featureType));
		dwpasStUserFeatureDs.setProductId(productId);
		List<DwpasStUserFeatureDs> userFeatureDsList = this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasStUserFeatureDsMapper.queryDataByPrdIdAndFeatureTypeAndReportDate",
						dwpasStUserFeatureDs);
		return userFeatureDsList;
	}

	@Override
	public List<Map<String, Object>> qryAllByPrdIdAndFeatureType(
			String productId, String featureType, String reportDate) {
		if (!StringUtils.notNullAndSpace(productId)) {
			this.logger.warn("查询所有产品用户特征数据失败，产品ID为空");
			return null;
		}
		if (!StringUtils.notNullAndSpace(featureType)) {
			this.logger.warn("查询所有产品用户特征数据失败，用户类型为空");
			return null;
		}
		if (!StringUtils.notNullAndSpace(reportDate)) {
			this.logger.warn("查询所有产品用户特征数据失败，日期为空");
			return null;
		}
		Map param = new HashMap();
		param.put("productId", productId);
		param.put("featureType", featureType);
		param.put("reportDate", reportDate);
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasStUserFeatureDsMapper.queryDwpasStUserFeatureByfeatureTypeAndproductIdAndReportDate",
						param);
	}

	@Override
	public List<DwpasStUserFeatureDs> getAmmapDate(String productId,
			String queryMonth, String featureType) {
		if (!StringUtils.notNullAndSpace(productId)) {
			this.logger.warn("查询所有产品用户特征数据失败，产品ID为空");
			return null;
		}
		if (!StringUtils.notNullAndSpace(featureType)) {
			this.logger.warn("查询所有产品用户特征数据失败，用户类型为空");
			return null;
		}
		if (!StringUtils.notNullAndSpace(queryMonth)) {
			this.logger.warn("查询所有产品用户特征数据失败，日期为空");
			return null;
		}
		Map param = new HashMap();
		param.put("productId", productId);
		param.put("featureType", featureType);
		param.put("reportDate", queryMonth);
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasStUserFeatureDsMapper.queryAmmapByPrdIdAndFeatureTypeAndReportDate",
						param);
	}

	@Override
	public Map<String, String> queryRealKpiCodeMap(String productId,
			int kpiType, String menuId) {
		Map<String, String> realCodeMap = new HashMap<String, String>();
		List<DwpasRColumnComKpi> commonKpiCodes = pageSettingService
				.getCommonCodeByMenuId(menuId);
		if (commonKpiCodes != null && commonKpiCodes.size() > 0) {
			for (DwpasRColumnComKpi comkpi : commonKpiCodes) {
				if (comkpi == null)
					continue;
				this.logger.info("------------->用户特征：comkpi："
						+ comkpi.getColumnCode());
				if (comkpi
						.getColumnCode()
						.trim()
						.equalsIgnoreCase(
								SystemColumnEnum.USER_CHARACTER_FEMALE_USER_RATE_PIE_CHART
										.getColumnCode())) {
					// 女用户数占比
					DwpasCKpiInfo female = getKpiInfo(comkpi.getComKpiCode(),
							kpiType, productId);
					if (female != null
							&& StringUtils.notNullAndSpace(female.getKpiCode())) {
						realCodeMap
								.put(SystemColumnEnum.USER_CHARACTER_FEMALE_USER_RATE_PIE_CHART
										.getColumnCode(), female.getKpiCode());
					} else {
						realCodeMap
								.put(SystemColumnEnum.USER_CHARACTER_FEMALE_USER_RATE_PIE_CHART
										.getColumnCode(),
										SystemColumnEnum.USER_CHARACTER_FEMALE_USER_RATE_PIE_CHART
												.getColumnCode()
												+ " Not Exists");
					}
				} else if (comkpi
						.getColumnCode()
						.trim()
						.equalsIgnoreCase(
								SystemColumnEnum.USER_CHARACTER_FEMALE_USER_OVERALL_PIE_CHART
										.getColumnCode())) {
					this.logger.info("----------------->女用户数占比大盘");
					// 女用户数占比大盘
					DwpasCKpiInfo female_dp = this.dwpasCKpiInfoService
							.getOverallKpiCodeByCommonKpiCodeAndKpiType(
									comkpi.getComKpiCode(), kpiType);// getKpiInfo(comkpi.getComKpiCode(),
																		// kpiType,
																		// Const.DA_PAN);
					if (female_dp != null
							&& StringUtils.notNullAndSpace(female_dp
									.getKpiCode())) {
						realCodeMap
								.put(SystemColumnEnum.USER_CHARACTER_FEMALE_USER_OVERALL_PIE_CHART
										.getColumnCode(), female_dp
										.getKpiCode());
					} else {
						realCodeMap
								.put(SystemColumnEnum.USER_CHARACTER_FEMALE_USER_OVERALL_PIE_CHART
										.getColumnCode(),
										SystemColumnEnum.USER_CHARACTER_FEMALE_USER_OVERALL_PIE_CHART
												.getColumnCode()
												+ " Not Exists");
					}
				} else if (comkpi
						.getColumnCode()
						.trim()
						.equalsIgnoreCase(
								SystemColumnEnum.USER_CHARACTER_USED_WIRELESS_RATE_PIE_CHART
										.getColumnCode())) {
					// 使用过无线用户数占比
					DwpasCKpiInfo useWifi = getKpiInfo(comkpi.getComKpiCode(),
							kpiType, productId);
					if (useWifi != null
							&& StringUtils
									.notNullAndSpace(useWifi.getKpiCode())) {
						realCodeMap
								.put(SystemColumnEnum.USER_CHARACTER_USED_WIRELESS_RATE_PIE_CHART
										.getColumnCode(), useWifi.getKpiCode());
					} else {
						realCodeMap
								.put(SystemColumnEnum.USER_CHARACTER_USED_WIRELESS_RATE_PIE_CHART
										.getColumnCode(),
										SystemColumnEnum.USER_CHARACTER_USED_WIRELESS_RATE_PIE_CHART
												.getColumnCode()
												+ " Not Exists");
					}
				} else if (comkpi
						.getColumnCode()
						.trim()
						.equalsIgnoreCase(
								SystemColumnEnum.USER_CHARACTER_USED_WIRELESS_OVERALL_PIE_CHART
										.getColumnCode())) {
					this.logger.info("----------------->使用过无线用户数占比大盘");
					// 使用过无线用户数占比大盘
					DwpasCKpiInfo useWifi_dp = this.dwpasCKpiInfoService
							.getOverallKpiCodeByCommonKpiCodeAndKpiType(
									comkpi.getComKpiCode(), kpiType);// getKpiInfo(comkpi.getComKpiCode(),
																		// kpiType,
																		// Const.DA_PAN);
					if (useWifi_dp != null
							&& StringUtils.notNullAndSpace(useWifi_dp
									.getKpiCode())) {
						realCodeMap
								.put(SystemColumnEnum.USER_CHARACTER_USED_WIRELESS_OVERALL_PIE_CHART
										.getColumnCode(), useWifi_dp
										.getKpiCode());
					} else {
						realCodeMap
								.put(SystemColumnEnum.USER_CHARACTER_USED_WIRELESS_OVERALL_PIE_CHART
										.getColumnCode(),
										SystemColumnEnum.USER_CHARACTER_USED_WIRELESS_OVERALL_PIE_CHART
												.getColumnCode()
												+ " Not Exists");
					}
				} else if (comkpi
						.getColumnCode()
						.trim()
						.equalsIgnoreCase(
								SystemColumnEnum.USER_CHARACTER_NO_USE_WIRELESS_RATE_PIE_CHART
										.getColumnCode())) {
					// 未使用过无线用户数占比
					DwpasCKpiInfo notuseWifi = getKpiInfo(
							comkpi.getComKpiCode(), kpiType, productId);
					if (notuseWifi != null
							&& StringUtils.notNullAndSpace(notuseWifi
									.getKpiCode())) {
						realCodeMap
								.put(SystemColumnEnum.USER_CHARACTER_NO_USE_WIRELESS_RATE_PIE_CHART
										.getColumnCode(), notuseWifi
										.getKpiCode());
					} else {
						realCodeMap
								.put(SystemColumnEnum.USER_CHARACTER_NO_USE_WIRELESS_RATE_PIE_CHART
										.getColumnCode(),
										SystemColumnEnum.USER_CHARACTER_NO_USE_WIRELESS_RATE_PIE_CHART
												.getColumnCode()
												+ " Not Exists");
					}
				} else if (comkpi
						.getColumnCode()
						.trim()
						.equalsIgnoreCase(
								SystemColumnEnum.USER_CHARACTER_NO_USE_WIRELESS_OVERALL_PIE_CHART
										.getColumnCode())) {
					this.logger.info("----------------->未使用过无线用户数占比大盘");
					// 未使用过无线用户数占比大盘
					DwpasCKpiInfo notuseWifi_dp = this.dwpasCKpiInfoService
							.getOverallKpiCodeByCommonKpiCodeAndKpiType(
									comkpi.getComKpiCode(), kpiType);// getKpiInfo(comkpi.getComKpiCode(),
																		// kpiType,
																		// Const.DA_PAN);
					if (notuseWifi_dp != null
							&& StringUtils.notNullAndSpace(notuseWifi_dp
									.getKpiCode())) {
						realCodeMap
								.put(SystemColumnEnum.USER_CHARACTER_NO_USE_WIRELESS_OVERALL_PIE_CHART
										.getColumnCode(), notuseWifi_dp
										.getKpiCode());
					} else {
						realCodeMap
								.put(SystemColumnEnum.USER_CHARACTER_NO_USE_WIRELESS_OVERALL_PIE_CHART
										.getColumnCode(),
										SystemColumnEnum.USER_CHARACTER_NO_USE_WIRELESS_OVERALL_PIE_CHART
												.getColumnCode()
												+ " Not Exists");
					}
				} else if (comkpi
						.getColumnCode()
						.trim()
						.equalsIgnoreCase(
								SystemColumnEnum.USER_CHARACTER_DEEP_USER_RATE_THREAD_CHART
										.getColumnCode())) {
					// 深度活跃用户
					DwpasCKpiInfo deepAactiveUser = getKpiInfo(
							comkpi.getComKpiCode(), kpiType, productId);
					if (deepAactiveUser != null
							&& StringUtils.notNullAndSpace(deepAactiveUser
									.getKpiCode())) {
						realCodeMap
								.put(SystemColumnEnum.USER_CHARACTER_DEEP_USER_RATE_THREAD_CHART
										.getColumnCode(), deepAactiveUser
										.getKpiCode());
					} else {
						realCodeMap
								.put(SystemColumnEnum.USER_CHARACTER_DEEP_USER_RATE_THREAD_CHART
										.getColumnCode(),
										SystemColumnEnum.USER_CHARACTER_DEEP_USER_RATE_THREAD_CHART
												.getColumnCode()
												+ " Not Exists");
					}
				} else if (comkpi
						.getColumnCode()
						.trim()
						.equalsIgnoreCase(
								SystemColumnEnum.USER_CHARACTER_DEEP_USER_RATE_OVERALL_THREAD_CHART
										.getColumnCode())) {
					this.logger.info("----------------->深度活跃用户大盘");
					// 深度活跃用户大盘
					DwpasCKpiInfo overRall_deepAactiveUser = this.dwpasCKpiInfoService
							.getOverallKpiCodeByCommonKpiCodeAndKpiType(
									comkpi.getComKpiCode(), kpiType);// getKpiInfo(comkpi.getComKpiCode(),
																		// kpiType,
																		// Const.DA_PAN);
					if (overRall_deepAactiveUser != null
							&& StringUtils
									.notNullAndSpace(overRall_deepAactiveUser
											.getKpiCode())) {
						realCodeMap
								.put(SystemColumnEnum.USER_CHARACTER_DEEP_USER_RATE_OVERALL_THREAD_CHART
										.getColumnCode(),
										overRall_deepAactiveUser.getKpiCode());
					} else {
						realCodeMap
								.put(SystemColumnEnum.USER_CHARACTER_DEEP_USER_RATE_OVERALL_THREAD_CHART
										.getColumnCode(),
										SystemColumnEnum.USER_CHARACTER_DEEP_USER_RATE_OVERALL_THREAD_CHART
												.getColumnCode()
												+ " Not Exists");
					}
				} else if (comkpi
						.getColumnCode()
						.trim()
						.equalsIgnoreCase(
								SystemColumnEnum.USER_CHARACTER_MAN_USER_RATE_PIE_CHART
										.getColumnCode())) {
					// 男用户数占比
					DwpasCKpiInfo male = getKpiInfo(comkpi.getComKpiCode(),
							kpiType, productId);
					if (male != null
							&& StringUtils.notNullAndSpace(male.getKpiCode())) {
						realCodeMap
								.put(SystemColumnEnum.USER_CHARACTER_MAN_USER_RATE_PIE_CHART
										.getColumnCode(), male.getKpiCode());
					} else {
						realCodeMap
								.put(SystemColumnEnum.USER_CHARACTER_MAN_USER_RATE_PIE_CHART
										.getColumnCode(),
										SystemColumnEnum.USER_CHARACTER_MAN_USER_RATE_PIE_CHART
												.getColumnCode()
												+ " Not Exists");
					}
				} else if (comkpi
						.getColumnCode()
						.trim()
						.equalsIgnoreCase(
								SystemColumnEnum.USER_CHARACTER_MAN_USER_OVERALL_PIE_CHART
										.getColumnCode())) {
					this.logger.info("----------------->男用户数占比大盘");
					// 男用户数占比大盘
					DwpasCKpiInfo male_dp = this.dwpasCKpiInfoService
							.getOverallKpiCodeByCommonKpiCodeAndKpiType(
									comkpi.getComKpiCode(), kpiType);// getKpiInfo(comkpi.getComKpiCode(),kpiType,Const.DA_PAN);
					if (male_dp != null
							&& StringUtils
									.notNullAndSpace(male_dp.getKpiCode())) {
						this.logger.warn("----------------->男用户大盘存在哦");
						realCodeMap
								.put(SystemColumnEnum.USER_CHARACTER_MAN_USER_OVERALL_PIE_CHART
										.getColumnCode(), male_dp.getKpiCode());
					} else {
						this.logger.warn("----------------->男用户大盘不存在");
						realCodeMap
								.put(SystemColumnEnum.USER_CHARACTER_MAN_USER_OVERALL_PIE_CHART
										.getColumnCode(),
										SystemColumnEnum.USER_CHARACTER_MAN_USER_OVERALL_PIE_CHART
												.getColumnCode()
												+ " Not Exists");
					}
				}
			}
		}

		return realCodeMap;
	}

	@Override
	public String getMapData(List<String> proIds, String queryMonth,
			String featureType) {
		if (proIds == null || proIds.isEmpty()) {
			this.logger.warn("产品ID为空");
			return null;
		}
		Map<String, String> hmColor = new HashMap<String, String>();
		hmColor.put("18岁以下", "#4572A7");
		hmColor.put("18-24岁", "#AA4643");
		hmColor.put("25-29岁", "#89A54E");
		hmColor.put("30-34岁", "#71588F");
		hmColor.put("35-39岁", "#4499B0");
		hmColor.put("40-49岁", "#DB843D");
		hmColor.put("50-59岁", "#93A9CF");
		hmColor.put("60岁以上", "#D19392");

		Map<String, List<ProductUsingCharacter>> ret = new HashMap<String, List<ProductUsingCharacter>>();
		List<DwpasCPrdInfo> qryAll = this.dwpasCPrdInfoService
				.queryAllDwpasCPrdInfo();
		Map<String, String> prodCache = createProdCache(qryAll);
		for (String prodID : proIds) {
			List<Map<String, Object>> userCharactors = this
					.qryAllByPrdIdAndFeatureType(prodID, featureType,
							queryMonth);
			ret.put(prodID,
					createUsingCharDTO(prodID, userCharactors, prodCache,
							hmColor));
		}

		return buildFlashVar(ret);
	}

	/**
	 * 获取产品通用指标对应的指标
	 * 
	 * @param commmoncode
	 * @param kpiType
	 * @param prodID
	 * @return
	 */
	private DwpasCKpiInfo getKpiInfo(String commmoncode, int kpiType,
			String prodID) {
		return this.dwpasCKpiInfoService
				.getDwpasCKpiInfoByProductIdAndCommonKpiCodeAndKpiType(prodID,
						commmoncode.trim(), kpiType);
	}

	/**
	 * 获取通用指标的大盘指标
	 * 
	 * @param commmoncode
	 * @param kpiType
	 * @param prodID
	 * @return
	 */
	private DwpasCKpiInfo getKpiInfoDP(String commmoncode, int kpiType) {
		return this.dwpasCKpiInfoService
				.getOverallKpiCodeByCommonKpiCodeAndKpiType(commmoncode.trim(),
						kpiType);
	}

	/**
	 * 转换地图数据格式
	 * 
	 * @param queryUsingChar
	 * @return
	 */
	private String buildFlashVar(
			Map<String, List<ProductUsingCharacter>> queryUsingChar) {

		StringBuffer flashVar = new StringBuffer();
		Iterator<Entry<String, List<ProductUsingCharacter>>> iterator = queryUsingChar
				.entrySet().iterator();

		if (iterator.hasNext()) {
			Entry<String, List<ProductUsingCharacter>> next = iterator.next();
			List<ProductUsingCharacter> dtos = next.getValue();
			for (ProductUsingCharacter productUsingCharacterDTO : dtos) {
				productUsingCharacterDTO.setSortType("byCnt");
			}
			Collections.sort(dtos);
			// cqv=10872197&cqt=1
			if (dtos != null && dtos.size() > 0) {
				for (int i = 0; i < dtos.size(); i++) {
					ProductUsingCharacter productUsingCharacterDTO = dtos
							.get(i);

					flashVar.append(
							nameCvt(productUsingCharacterDTO.getFeatureName()))
							.append("v").append("=")
							.append(productUsingCharacterDTO.getValue());
					flashVar.append("&");
					flashVar.append(
							nameCvt(productUsingCharacterDTO.getFeatureName()))
							.append("t").append("=").append((i + 1));
					flashVar.append("&");

				}
			}
			if (flashVar.length() > 0) {
				flashVar.deleteCharAt(flashVar.length() - 1);
			}
		}

		return flashVar.toString();
	}

	private String nameCvt(String name) {
		if (name.equals("西藏自治区")) {
			return "西藏";
		}
		if (name.equals("宁夏回族自治区")) {
			return "宁夏";
		}
		if (name.equals("澳门特别行政区")) {
			return "澳门";
		}
		if (name.equals("内蒙古自治区")) {
			return "内蒙古";
		}
		if (name.equals("广西壮族自治区")) {
			return "广西";
		}
		if (name.equals("香港特别行政区")) {
			return "香港";
		}
		if (name.equals("新疆维吾尔自治区")) {
			return "新疆";
		}
		if (name.equals("黑龙江省")) {
			return "黑龙江";
		}
		return name;
	}

	private Map<String, String> createProdCache(List<DwpasCPrdInfo> qryAll) {
		Map<String, String> cache = new HashMap<String, String>();
		for (DwpasCPrdInfo dwpasCPrdInfo : qryAll) {
			cache.put(dwpasCPrdInfo.getProductId(),
					dwpasCPrdInfo.getProductName());
		}
		return cache;
	}

	private List<ProductUsingCharacter> createUsingCharDTO(String prodID,
			List<Map<String, Object>> userCharactors,
			Map<String, String> prodCache, Map<String, String> hmColor) {
		List<ProductUsingCharacter> retList = new ArrayList<ProductUsingCharacter>();
		for (Map<String, Object> mapData : userCharactors) {
			ProductUsingCharacter dto = new ProductUsingCharacter();
			populate(dto, mapData, hmColor);
			dto.setProdId(prodID);
			dto.setProductName(prodCache.get(prodID));
			retList.add(dto);

		}
		return retList;
	}

	private void populate(ProductUsingCharacter dto,
			Map<String, Object> mapData, Map<String, String> hmColor) {
		if (mapData.get("report_date") != null) {
			dto.setDate((String) mapData.get("report_date"));
		}
		this.logger.info("feature_id=" + mapData.get("feature_id"));
		dto.setFeatureTypeId(Integer.parseInt((String) mapData
				.get("feature_id")));
		dto.setBaseValue((BigDecimal) mapData.get("user_rate"));
		dto.setValue(MathUtils.getBigDecimal(mapData.get("user_cnt")));
		dto.setFeatureName((String) mapData.get("feature_name"));
		if (hmColor.get(dto.getFeatureName()) != null) {
			dto.setColor(hmColor.get(dto.getFeatureName()));
		}

	}
}
