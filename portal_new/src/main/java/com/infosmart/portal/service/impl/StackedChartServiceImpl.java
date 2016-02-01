package com.infosmart.portal.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasCPrdInfo;
import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasStKpiData;
import com.infosmart.portal.service.DwpasStKpiDataService;
import com.infosmart.portal.service.ProdInfoService;
import com.infosmart.portal.service.StackedChartService;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.vo.Chart;
import com.infosmart.portal.vo.Graph;
import com.infosmart.portal.vo.GraphDataElement;

@Service
public class StackedChartServiceImpl extends BaseServiceImpl implements
		StackedChartService {
	/**
	 * 产品管理
	 */
	@Autowired
	private ProdInfoService prodInfoService;

	/**
	 * 指标数据
	 */
	@Autowired
	protected DwpasStKpiDataService dwpasStKpiDataService;

	@Override
	public Chart getStackedChart(List<DwpasCPrdInfo> productInfoList,
			String commKpiCode, String reportDate, int kpiType) {
		if (productInfoList == null || productInfoList.isEmpty()) {
			this.logger.warn("产品列表为空");
			return null;
		}
		if (!StringUtils.notNullAndSpace(commKpiCode)) {
			this.logger.warn("通用指标为空");
			return null;
		}
		this.logger.info("得到产品的多个产品的指标堆积图");
		Chart chart = new Chart();
		chart.setChartName("产品趋势图");
		// 通用指标
		List<String> commKpiCodeList = new ArrayList<String>();
		commKpiCodeList.add(commKpiCode);
		// <日期，产品1值,产品值2....>
		TreeMap<String, String> areaDataMap = new TreeMap<String, String>();
		// 产品ID
		List<String> productIdList = new ArrayList<String>();
		for (DwpasCPrdInfo prdInfo : productInfoList) {
			productIdList.add(prdInfo.getProductId());
		}
		// 查询条件
		Map paramMap = new HashMap();
		paramMap.put("productIds", productIdList);
		if (kpiType == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
			paramMap.put("dateType", DwpasStKpiData.DATE_TYPE_OF_DAY);
		} else {
			paramMap.put("dateType", DwpasStKpiData.DATE_TYPE_OF_MONTH);
		}
		paramMap.put("reportDate", reportDate.replaceAll("-", ""));
		paramMap.put("commKpiCode", commKpiCode);
		paramMap.put(
				"allProductId",
				productIdList.toString().substring(1,
						productIdList.toString().length() - 1));
		List<Map> prdKpiDataList = this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasLongHuBangMapper.queryKpiDataByProdIds",
						paramMap);
		if (prdKpiDataList == null || prdKpiDataList.isEmpty())
			return null;
		Map<String, List<DwpasStKpiData>> kpiDataOfPrdMap = new HashMap<String, List<DwpasStKpiData>>();
		DwpasStKpiData kpiData = null;
		List<DwpasStKpiData> kpiDataList = null;
		for (Map kpiDataMap : prdKpiDataList) {
			if (areaDataMap.containsKey(kpiDataMap.get("REPORT_DATE")
					.toString())) {
				String oldValue = areaDataMap.get(kpiDataMap.get("REPORT_DATE")
						.toString());
				String newValue = oldValue
						+ ","
						+ (kpiDataMap.get("BASE_VALUE") == null ? "0,0"
								: kpiDataMap.get("BASE_VALUE").toString()
										+ ","
										+ kpiDataMap.get("BASE_VALUE")
												.toString());
				// this.logger.info("NEW_VALUE:"+newValue);
				areaDataMap.put(kpiDataMap.get("REPORT_DATE").toString(),
						newValue);
				//
				if (!kpiDataOfPrdMap.containsKey(kpiDataMap.get("product_id")
						.toString())) {
					kpiDataList = new ArrayList<DwpasStKpiData>();
					kpiDataOfPrdMap.put(
							kpiDataMap.get("product_id").toString(),
							kpiDataList);
				}
				kpiData = new DwpasStKpiData();
				kpiData.setReportDate(kpiDataMap.get("REPORT_DATE").toString());
				kpiData.setBaseValue(new BigDecimal(kpiDataMap
						.get("BASE_VALUE") == null ? "0" : kpiDataMap.get(
						"BASE_VALUE").toString()));
				kpiDataOfPrdMap.get(kpiDataMap.get("product_id").toString())
						.add(kpiData);
			} else {
				areaDataMap.put(kpiDataMap.get("REPORT_DATE").toString(),
						kpiDataMap.get("BASE_VALUE") == null ? "0,0"
								: kpiDataMap.get("BASE_VALUE").toString()
										+ ","
										+ kpiDataMap.get("BASE_VALUE")
												.toString());
				//
				if (!kpiDataOfPrdMap.containsKey(kpiDataMap.get("product_id")
						.toString())) {
					kpiDataList = new ArrayList<DwpasStKpiData>();
					kpiDataOfPrdMap.put(
							kpiDataMap.get("product_id").toString(),
							kpiDataList);
				}
				kpiData = new DwpasStKpiData();
				kpiData.setReportDate(kpiDataMap.get("REPORT_DATE").toString());
				kpiData.setBaseValue(new BigDecimal(kpiDataMap
						.get("BASE_VALUE") == null ? "0" : kpiDataMap.get(
						"BASE_VALUE").toString()));
				kpiDataOfPrdMap.get(kpiDataMap.get("product_id").toString())
						.add(kpiData);

			}
		}
		// 图表
		List<Graph> graphList = new ArrayList<Graph>();
		Graph graph = null;
		int i = 0;
		for (DwpasCPrdInfo prdInfo : productInfoList) {
			if (prdInfo == null)
				continue;
			graph = new Graph();
			graph.setGraphId(prdInfo.getProductId());
			// 趋势图
			graph.setGraphType(Graph.GRAPH_TYPE_TREND_LINE);
			// 名称
			graph.setGraphName(prdInfo.getProductName());
			// 颜色
			graph.setGraphColor(Constants.CHART_COLOR_LIST.get(i++));
			// 数据
			graph.setDataList(getGraphDatas(kpiDataOfPrdMap.get(prdInfo
					.getProductId())));
			graphList.add(graph);
		}
		chart.setAreaDataMap(areaDataMap);
		chart.setGraphList(graphList);
		return chart;
	}

	private List<GraphDataElement> getGraphDatas(
			List<DwpasStKpiData> kpiDataList) {
		if (kpiDataList == null || kpiDataList.isEmpty())
			return null;
		List<GraphDataElement> dataElements = new ArrayList<GraphDataElement>();
		for (DwpasStKpiData kpiData : kpiDataList) {
			GraphDataElement graphDataElement = new GraphDataElement();
			graphDataElement.setValueDate(kpiData.getReportDate());
			graphDataElement.setValue(String.valueOf(kpiData.getBaseValue()));
			dataElements.add(graphDataElement);
		}
		return dataElements;
	}
}
