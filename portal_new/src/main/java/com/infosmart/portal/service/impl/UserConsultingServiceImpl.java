package com.infosmart.portal.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasCHelprateShow;
import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCPrdInfo;
import com.infosmart.portal.service.DwpasCColumnInfoService;
import com.infosmart.portal.service.DwpasCKpiInfoService;
import com.infosmart.portal.service.DwpasCPrdInfoService;
import com.infosmart.portal.service.DwpasStKpiDataService;
import com.infosmart.portal.service.UserConsultingService;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.MathUtils;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.SystemColumnEnum;
import com.infosmart.portal.vo.dwpas.ProdKpiData;

@Service
public class UserConsultingServiceImpl extends BaseServiceImpl implements
		UserConsultingService {

	@Autowired
	protected DwpasCColumnInfoService columnInfoService;
	@Autowired
	protected DwpasCKpiInfoService dwpasCKpiInfoService;
	@Autowired
	protected DwpasStKpiDataService dwpasStKpiDataService;
	// 产品管理
	@Autowired
	private DwpasCPrdInfoService dwpasCPrdInfoService;

	@Override
	public List<ProdKpiData> queryProductClasse(String systeTemplateId,
			String queryDate, int queryType) {
		this.logger.info("查询产品的求助率信息");
		// 查询所有产品线
		List<DwpasCPrdInfo> allProds = dwpasCPrdInfoService
				.queryAllPrdAndChildPrdInfo();
		if (allProds == null || allProds.isEmpty()) {
			this.logger.warn("没有设置产品线");
			return null;
		}
		List<ProdKpiData> prodKpiDataList = new ArrayList<ProdKpiData>();
		// 有子产品线的产品
		List<DwpasCPrdInfo> hasChildPrdList = new ArrayList<DwpasCPrdInfo>();
		// 无子产品线的产品
		List<DwpasCPrdInfo> noChildPrdList = new ArrayList<DwpasCPrdInfo>();
		for (DwpasCPrdInfo prod : allProds) {
			if (prod == null)
				continue;
			if (prod.getChildPrdInfoList() == null
					|| prod.getChildPrdInfoList().isEmpty()) {
				noChildPrdList.add(prod);
			} else {
				hasChildPrdList.add(prod);
			}
		}
		// 有子产品线的产品求助率
		prodKpiDataList.addAll(queryHasChildProdHelpRate(systeTemplateId,
				hasChildPrdList, queryDate, queryType));
		// 无子产品线的产品求助率
		prodKpiDataList.addAll(queryNoChildProdHelpRate(systeTemplateId,
				noChildPrdList, queryDate, queryType));
		return prodKpiDataList;
	}

	/**
	 * 查询无子产品的产品求助率
	 * 
	 * @param prodInfoList
	 *            产品列表（无子产品线）
	 * @param queryDate
	 * @param queryType
	 */
	private List<ProdKpiData> queryNoChildProdHelpRate(
			String systeTemplateId, List<DwpasCPrdInfo> prodInfoList,
			String queryDate, int queryType) {
		this.logger.info("查询无子产品线的求助率");
		List<String> prodIdList = new ArrayList<String>();
		if (prodInfoList == null) {
			this.logger.warn("查询无子产品的产品求助率失败：产品信息列表为空");
			return null;
		}
		for (DwpasCPrdInfo productInfo : prodInfoList) {
			prodIdList.add(productInfo.getProductId());
		}
		// 用户声音_求助率趋势 INDEX_ALERT_DASHBOARD_CHART
		String helpChartCommKpiCode = columnInfoService
				.getCommonCodeByColumnCode(
						systeTemplateId,
						SystemColumnEnum.INDEX_ALERT_DASHBOARD_CHART
								.getColumnCode()).get(0).getComKpiCode();
		// 产品及其关联指标信息
		Map<String, DwpasCKpiInfo> prodAndKpiInfoMap = this.dwpasCKpiInfoService
				.queryKpiInfoByProdId(helpChartCommKpiCode,
						DwpasCKpiInfo.KPI_TYPE_OF_DAY, prodIdList);
		// 产品及其关联指标数据
		Map<String, BigDecimal> prodAndKpiDataMap = queryProdKpiData(
				prodAndKpiInfoMap, queryDate, queryType);
		// 产品及其关联求助率
		List<DwpasCHelprateShow> helpRateShowList = this.myBatisDao.getList(
				"com.infosmart.mapper.DwpasCHelprateShow.qryAllByProductIds",
				prodIdList);
		if (helpRateShowList == null || helpRateShowList.isEmpty()) {
			this.logger.warn("没有配置产品求助率信息");
			return new ArrayList<ProdKpiData>();
		}
		Map<String, DwpasCHelprateShow> prodAndHelpRateMap = new HashMap<String, DwpasCHelprateShow>();
		for (DwpasCHelprateShow helprateShow : helpRateShowList) {
			prodAndHelpRateMap.put(helprateShow.getProductId(), helprateShow);
		}
		ProdKpiData prodKpiDataDTO = null;
		List<ProdKpiData> prodKpiDataList = new ArrayList<ProdKpiData>();
		for (DwpasCPrdInfo productInfo : prodInfoList) {
			if (productInfo == null)
				continue;
			prodKpiDataDTO = new ProdKpiData(productInfo.getProductId());
			// 计算求助率
			BigDecimal kpiDataVal = prodAndKpiDataMap.get(productInfo
					.getProductId());
			if (kpiDataVal != null) {
				// 求助率
				prodKpiDataDTO.setBaseValue(kpiDataVal
						.multiply(Constants.ONE_HANDREN));
			} else {
				prodKpiDataDTO.setBaseValue(Constants.ZERO);
			}
			DwpasCHelprateShow helpRateDO = prodAndHelpRateMap.get(productInfo
					.getProductId());
			if (helpRateDO != null) {
				// 最大值
				prodKpiDataDTO.setMaxValue(helpRateDO.getMaxValue().multiply(
						Constants.ONE_HANDREN));
				// 最小值
				prodKpiDataDTO.setMinValue(helpRateDO.getMinValue().multiply(
						Constants.ONE_HANDREN));
				// 警告值
				prodKpiDataDTO.setAlertValue(helpRateDO.getAlertValue()
						.multiply(Constants.ONE_HANDREN));
				// 是否超过警报阀值
				prodKpiDataDTO.setOverAlert(prodKpiDataDTO.getBaseValue()
						.compareTo(prodKpiDataDTO.getAlertValue()) > 0);

				prodKpiDataDTO.setPreAlertValue(helpRateDO.getPreAlertValue()
						.multiply(Constants.ONE_HANDREN));
				// 是否超过 预警阀值
				prodKpiDataDTO.setOverPreAlert(prodKpiDataDTO.getBaseValue()
						.compareTo(prodKpiDataDTO.getPreAlertValue()) > 0);
				//
				if (prodKpiDataDTO.getBaseValue().compareTo(
						prodKpiDataDTO.getMaxValue()) >= 0) {
					prodKpiDataDTO.setBaseValue(prodKpiDataDTO.getBaseValue());
				}
			}
			// 产品ID
			prodKpiDataDTO.setProductId(productInfo.getProductId());
			// 是否有子产品线
			prodKpiDataDTO.setHasChild(false);
			// 产品名称
			prodKpiDataDTO.setProductName(productInfo.getProductName());
			// 生成矩形图
			prodKpiDataDTO.setFlashPar(createFlashPar(prodKpiDataDTO));
			//
			prodKpiDataList.add(prodKpiDataDTO);
		}
		return prodKpiDataList;
	}

	/**
	 * 查询有子产品线的产品求助率
	 * 
	 * @param prodInfoList
	 * @param queryDate
	 * @param queryType
	 * @return
	 */
	private List<ProdKpiData> queryHasChildProdHelpRate(
			String systeTemplateId, List<DwpasCPrdInfo> prodInfoList,
			String queryDate, int queryType) {
		this.logger.info("查询有子产品线的求助率");
		if(prodInfoList==null){
			this.logger.warn("查询有子产品线的产品求助率失败：产品信息列表为空");
			return null;
		}
		List<String> prodIdList = new ArrayList<String>();
		for (DwpasCPrdInfo productInfo : prodInfoList) {
			prodIdList.add(productInfo.getProductId());
		}
		// 产品及其关联求助率
		List<DwpasCHelprateShow> helpRateShowList = this.myBatisDao.getList(
				"com.infosmart.mapper.DwpasCHelprateShow.qryAllByProductIds",
				prodIdList);
		if (helpRateShowList == null || helpRateShowList.isEmpty()) {
			this.logger.warn("没有配置产品求助率信息");
			return new ArrayList<ProdKpiData>();
		}
		Map<String, DwpasCHelprateShow> prodAndHelpRateMap = new HashMap<String, DwpasCHelprateShow>();

		for (DwpasCHelprateShow helprateShow : helpRateShowList) {
			prodAndHelpRateMap.put(helprateShow.getProductId(), helprateShow);
		}
		// 用户求助率_分子 INDEX_ALERT_MOLECULES_DASHBOARD_CHART
		String helpMoleculesCommKpiCode = columnInfoService
				.getCommonCodeByColumnCode(
						systeTemplateId,
						SystemColumnEnum.INDEX_ALERT_MOLECULES_DASHBOARD_CHART
								.getColumnCode()).get(0).getComKpiCode();
		// 用户求助率_分母 INDEX_ALERT_DENOMINATOR_DASHBOARD_CHART
		String helpDenominatorCommKpiCode = columnInfoService
				.getCommonCodeByColumnCode(
						systeTemplateId,
						SystemColumnEnum.INDEX_ALERT_DENOMINATOR_DASHBOARD_CHART
								.getColumnCode()).get(0).getComKpiCode();
		ProdKpiData prodKpiDataDTO = null;
		List<ProdKpiData> prodKpiDataList = new ArrayList<ProdKpiData>();
		// 所有的子产品
		List<String> allChildProdIdList = new ArrayList<String>();
		for (DwpasCPrdInfo productInfo : prodInfoList) {
			if (productInfo == null)
				continue;
			List<DwpasCPrdInfo> childPrdInfoList = productInfo
					.getChildPrdInfoList();
			if (childPrdInfoList == null || childPrdInfoList.isEmpty())
				continue;
			for (DwpasCPrdInfo prodInfo : childPrdInfoList) {
				allChildProdIdList.add(prodInfo.getProductId());
			}
		}
		// 所有的子产品的分子
		Map<String, BigDecimal> allNumeratorMap = this
				.calculateChildProdHelpRate(allChildProdIdList,
						helpMoleculesCommKpiCode, queryDate, queryType);
		// 所有的子产品的分母
		Map<String, BigDecimal> allDenominatorMap = this
				.calculateChildProdHelpRate(allChildProdIdList,
						helpDenominatorCommKpiCode, queryDate, queryType);
		for (DwpasCPrdInfo productInfo : prodInfoList) {
			if (productInfo == null)
				continue;
			if (this.logger.isInfoEnabled()) {
				this.logger.debug("计算某产品（有子产品线）的求助率:"
						+ productInfo.getProductName());
			}
			prodKpiDataDTO = new ProdKpiData(productInfo.getProductId());
			// 计算求助率
			// 该产品的所有子产品线
			List<DwpasCPrdInfo> childPrdInfoList = productInfo
					.getChildPrdInfoList();
			if (childPrdInfoList == null || childPrdInfoList.isEmpty()) {
				this.logger.warn("该产品没有子产品线:" + productInfo.getProductName());
			}
			// 分子
			BigDecimal numerator = new BigDecimal(0);
			// 分母
			BigDecimal denominator = new BigDecimal(0);
			// 该产品的所有子产品
			for (DwpasCPrdInfo prodInfo : childPrdInfoList) {
				// 所有子产品的分子之和
				numerator = numerator.add(allNumeratorMap.get(prodInfo
						.getProductId()) == null ? new BigDecimal(0)
						: allNumeratorMap.get(prodInfo.getProductId()));
				// 所有子产品的分母之和
				denominator = denominator.add(allDenominatorMap.get(prodInfo
						.getProductId()) == null ? new BigDecimal(0)
						: allDenominatorMap.get(prodInfo.getProductId()));
			}
			// 默认是1，防止出错
			if (denominator.compareTo(new BigDecimal(0)) == 0)
				denominator = new BigDecimal(1);
			/**
			 * 计算产品线的求助率 = sum（产品线下的产品指标【用户求助率_分子】值）/ sum（产品线下的产品指标【用户求助率_分母】值）
			 */
			// 计算哦
			BigDecimal helpRate = MathUtils.div(numerator, denominator)
					.multiply(Constants.ONE_HANDREN)
					.setScale(4, BigDecimal.ROUND_HALF_UP);
			// 求助率
			prodKpiDataDTO.setBaseValue(helpRate);
			// 产品求助率参数
			DwpasCHelprateShow helpRateDO = prodAndHelpRateMap.get(productInfo
					.getProductId());
			if (helpRateDO != null) {
				// 最大值
				prodKpiDataDTO.setMaxValue(helpRateDO.getMaxValue().multiply(
						Constants.ONE_HANDREN));
				// 最小值
				prodKpiDataDTO.setMinValue(helpRateDO.getMinValue().multiply(
						Constants.ONE_HANDREN));
				// 警告值
				prodKpiDataDTO.setAlertValue(helpRateDO.getAlertValue()
						.multiply(Constants.ONE_HANDREN));
				// 是否超过警报阀值
				prodKpiDataDTO.setOverAlert(prodKpiDataDTO.getBaseValue()
						.compareTo(prodKpiDataDTO.getAlertValue()) > 0);

				prodKpiDataDTO.setPreAlertValue(helpRateDO.getPreAlertValue()
						.multiply(Constants.ONE_HANDREN));
				// 是否超过 预警阀值
				prodKpiDataDTO.setOverPreAlert(prodKpiDataDTO.getBaseValue()
						.compareTo(prodKpiDataDTO.getPreAlertValue()) > 0);
				//
				if (prodKpiDataDTO.getBaseValue().compareTo(
						prodKpiDataDTO.getMaxValue()) >= 0) {
					prodKpiDataDTO.setBaseValue(prodKpiDataDTO.getBaseValue());
				}
			}
			// 产品ID
			prodKpiDataDTO.setProductId(productInfo.getProductId());
			// 是否有子产品线
			prodKpiDataDTO.setHasChild(true);
			// 产品名称
			prodKpiDataDTO.setProductName(productInfo.getProductName());
			// 生成矩形图
			prodKpiDataDTO.setFlashPar(createFlashPar(prodKpiDataDTO));
			//
			prodKpiDataList.add(prodKpiDataDTO);
		}
		return prodKpiDataList;
	}

	/**
	 * 计算所有的子产品线的分子或分母求助率数据
	 * 
	 * @param childProdIdList
	 * @param commCode
	 * @param reportDate
	 * @param queryType
	 * @return
	 */
	private Map<String, BigDecimal> calculateChildProdHelpRate(
			List<String> childProdIdList, String commCode, String reportDate,
			int queryType) {
		this.logger.info("计算所有的子产品线的分子或分母求助率数据");
		if (childProdIdList == null || childProdIdList.isEmpty()) {
			this.logger.warn("产品参数为空或产品的子产品线为空");
			return new HashMap<String, BigDecimal>();
		}
		// 产品及其关联指标信息
		Map<String, DwpasCKpiInfo> prodAndKpiInfoMap = this.dwpasCKpiInfoService
				.queryKpiInfoByProdId(commCode, DwpasCKpiInfo.KPI_TYPE_OF_DAY,
						childProdIdList);
		if(prodAndKpiInfoMap.isEmpty()){
			return null;
		}
		// 所有子产品的指标数据
		Map<String, BigDecimal> childPrdKpiDataMap = queryProdKpiData(
				prodAndKpiInfoMap, reportDate, queryType);
		if (childPrdKpiDataMap == null) {
			childPrdKpiDataMap = new HashMap<String, BigDecimal>();
		}
		return childPrdKpiDataMap;
	}

	/**
	 * 产品关联的指标数据map[prod_id,kpi_data]
	 * 
	 * @param queryType
	 * @param reportDate
	 * @return
	 */
	private Map<String, BigDecimal> queryProdKpiData(
			Map<String, DwpasCKpiInfo> prodAndKpiMap, String reportDate,
			int queryType) {
		this.logger.info("列出产品关联的在时间<" + reportDate + ">的指标数据");
		if (prodAndKpiMap == null || prodAndKpiMap.isEmpty()) {
			this.logger.warn("产品关联的指标信息为空");
			return new HashMap<String, BigDecimal>();
		}
		// 产品关联的指标列表
		List<DwpasCKpiInfo> kpiInfoList = new ArrayList<DwpasCKpiInfo>();
		Entry entry = null;
		for (Iterator it = prodAndKpiMap.entrySet().iterator(); it.hasNext();) {
			entry = (Entry) it.next();
			kpiInfoList.add((DwpasCKpiInfo) entry.getValue());
		}
		// 统计指标数据
		Map<String, BigDecimal> kpiDataMap = this.dwpasStKpiDataService
				.statDwpasStKpiDataByKpiCode(kpiInfoList, reportDate,
						reportDate, queryType);
		Map<String, BigDecimal> prodIdAndKpiDataMap = new HashMap<String, BigDecimal>();
		String kpiCode = null;
		for (Iterator it = prodAndKpiMap.entrySet().iterator(); it.hasNext();) {
			entry = (Entry) it.next();
			kpiCode = ((DwpasCKpiInfo) entry.getValue()).getKpiCode();
			if (kpiDataMap.get(kpiCode) == null)
				continue;
			// key为产品ID
			prodIdAndKpiDataMap.put(entry.getKey().toString(),
					kpiDataMap.get(kpiCode));
		}
		return prodIdAndKpiDataMap;
	}

	/**
	 * 
	 * @param prodKpiDataDTO
	 * @return
	 */
	private String createFlashPar(ProdKpiData prodKpiDataDTO) {
		StringBuffer sbUrl = new StringBuffer();
		sbUrl.append("max=")
				.append(MathUtils.div_100(prodKpiDataDTO.getMaxValue())
						.doubleValue()).append("&");
		sbUrl.append("min=")
				.append(MathUtils.div_100(prodKpiDataDTO.getMinValue())
						.doubleValue()).append("&");
		sbUrl.append("value=")
				.append(MathUtils.div_100(prodKpiDataDTO.getBaseValue())
						.doubleValue()).append("&");
		sbUrl.append("warn=")
				.append(MathUtils.div_100(prodKpiDataDTO.getAlertValue())
						.doubleValue()).append("&");
		sbUrl.append("product_name=").append(encodeName(prodKpiDataDTO))
				.append("&");
		if (prodKpiDataDTO.isOverAlert()) {
			sbUrl.append("color=#CF1111");
		} else {
			sbUrl.append("color=#79BA16");
		}
		return sbUrl.toString();
	}

	private String encodeName(ProdKpiData prodKpiDataDTO) {
		if (null == prodKpiDataDTO.getProductName()) {
			return prodKpiDataDTO.getParentId();
		}
		try {
			return URLEncoder.encode(prodKpiDataDTO.getProductName(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return prodKpiDataDTO.getProductId();
		}
	}

	@Override
	public List<ProdKpiData> queryChildProductClasseByPrdId(
			String systeTemplateId, String productId, String queryDate,
			int queryType) {
		this.logger.info("查询某产品子产品线的求助率信息");
		DwpasCPrdInfo productInfo = this.dwpasCPrdInfoService
				.getPrdInfoAndChildPrdByProductId(productId);
		if (productInfo == null)
			return null;
		if (productInfo.getChildPrdInfoList() == null
				|| productInfo.getChildPrdInfoList().isEmpty()) {
			return new ArrayList<ProdKpiData>();
		} else {
			return this.queryNoChildProdHelpRate(systeTemplateId,
					productInfo.getChildPrdInfoList(), queryDate, queryType);
		}
	}

}
