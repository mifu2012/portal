package com.infosmart.portal.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasStCrossAnalyseData;
import com.infosmart.portal.service.PrdCrossStockChartService;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.vo.Chart;
import com.infosmart.portal.vo.Graph;
import com.infosmart.portal.vo.dwpas.PrdCrossDataTypeEnum;
import com.infosmart.portal.vo.dwpas.PrdCrossParam;

@Service
public class PrdCrossStockChartServiceImpl extends BaseServiceImpl implements
		PrdCrossStockChartService {

	@Override
	public Chart getStockChart(PrdCrossParam prdCrossParam) {
		if (prdCrossParam == null) {
			this.logger.warn("查询产品的交叉分析数据趋势图失败 :产品交叉参数为空");
			return null;
		}
		Chart chart = new Chart();
		chart.setChartName("指标趋势图");
		List<DwpasStCrossAnalyseData> crossAnalyseDataDOList = getPrdCrossDataMap(prdCrossParam);
		if (crossAnalyseDataDOList != null && !crossAnalyseDataDOList.isEmpty()) {
			this.logger.info("补全没有的数据");
			Date endDate = DateUtils.parseByFormatRule(crossAnalyseDataDOList
					.get(crossAnalyseDataDOList.size() - 1).getReportDate(),
					"yyyyMM");
			endDate=DateUtils.getNextMonth(endDate);
			prdCrossParam.setEndDate(prdCrossParam.getEndDate()
					.replace("-", ""));
			Date reportDate = DateUtils.parseByFormatRule(
					prdCrossParam.getEndDate(), "yyyyMM");
			DwpasStCrossAnalyseData analyseData = null;
			while (endDate.getTime() < DateUtils.getNextMonth(reportDate)
					.getTime()) {
				analyseData = new DwpasStCrossAnalyseData();
				analyseData.setCrossUserCnt(0);
				analyseData.setCrossUserRate(new BigDecimal(0));
				analyseData.setProductId(prdCrossParam.getPrdId());
				analyseData.setRelProductId(prdCrossParam.getRelPrdId());
				analyseData.setReportDate(DateUtils.formatByFormatRule(endDate,
						"yyyyMM"));
				//
				crossAnalyseDataDOList.add(analyseData);
				//
				endDate = DateUtils.getNextMonth(endDate);
			}
		}
		chart.setAreaDataMap(getAreaDataMap(crossAnalyseDataDOList,
				prdCrossParam));
		chart.setGraphList(getGraphs(prdCrossParam));
		return chart;
	}

	private List<DwpasStCrossAnalyseData> getPrdCrossDataMap(
			PrdCrossParam prdCrossParam) {
		if (prdCrossParam == null) {
			this.logger.warn("查询产品的交叉分析数据失败 :产品交叉参数为空");
			return null;
		}
		Map map = new HashMap();
		map.put("productId", prdCrossParam.getPrdId());
		map.put("relProductId", prdCrossParam.getRelPrdId());
		map.put("reportDate", prdCrossParam.getEndDate());
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasStCrossAnalyseDataMapper.queryProdCrossAnalyseData",
						map);
	}

	/**
	 * 获得面积图数据集
	 * 
	 * @param crossAnalyseDataDOList
	 *            产品交叉的数据集合
	 * @return 面积图数据集
	 */
	private TreeMap<String, String> getAreaDataMap(
			List<DwpasStCrossAnalyseData> crossAnalyseDataDOList,
			PrdCrossParam param) {
		if (crossAnalyseDataDOList == null) {
			this.logger.warn("获得面积图数据集失败：交叉数据集为空");
			return null;
		}
		if (param == null) {
			this.logger.warn("获得面积图数据集失败：传入参数param为空");
			return null;
		}
		TreeMap<String, String> areaDataMap = new TreeMap<String, String>();
		DwpasStCrossAnalyseData crossAnalyseData = new DwpasStCrossAnalyseData();
		for (int i = 0; i < crossAnalyseDataDOList.size(); i++) {
			crossAnalyseData = crossAnalyseDataDOList.get(i);
			String reportDate = crossAnalyseData.getReportDate();
			PrdCrossDataTypeEnum prdCrossDataTypeEnum = param
					.getPrdCrossDataTypeEnum();
			if (prdCrossDataTypeEnum == PrdCrossDataTypeEnum.CROSS_USER_CNT) {
				String dataValue = String.valueOf(crossAnalyseData
						.getCrossUserCnt())
						+ Constants.CHART_DATA_SEPERATOR
						+ String.valueOf(crossAnalyseData.getCrossUserCnt());
				areaDataMap.put(reportDate, dataValue);
			} else if (prdCrossDataTypeEnum == PrdCrossDataTypeEnum.CROSS_USER_RATE) {
				if (null == crossAnalyseData.getCrossUserRate()) {
					String dataValue = Constants.CHART_DATA_SEPERATOR;
					areaDataMap.put(reportDate, dataValue);
				} else {
					String dataValue = String.valueOf(crossAnalyseData
							.getCrossUserRate().multiply(new BigDecimal(100)))
							+ Constants.CHART_DATA_SEPERATOR
							+ String.valueOf(crossAnalyseData
									.getCrossUserRate().multiply(
											new BigDecimal(100)));
					areaDataMap.put(reportDate, dataValue);
				}
			}
		}
		return areaDataMap;
	}

	/**
	 * 获得折线集合
	 * 
	 * @param crossAnalyseDataDOList
	 *            数据信息
	 * @return 折线集合
	 */
	private List<Graph> getGraphs(PrdCrossParam param) {
		if (param == null) {
			this.logger.warn("获得折线集合失败:传入参数param为空");
			return null;
		}
		List<Graph> graphs = new ArrayList<Graph>();
		Graph kpiGraph = getKpiGraph(param);
		graphs.add(kpiGraph);
		return graphs;
	}

	/**
	 * 获得图表中线的描述信息
	 * 
	 * @param kpiInfoDTO
	 *            指标信息
	 * @param cf
	 *            颜色工厂
	 * @return 图表中线的描述信息
	 */
	private Graph getKpiGraph(PrdCrossParam param) {
		if (param == null) {
			this.logger.warn("获得图表中线的描述信息失败：传入参数param为空");
			return null;
		}
		Graph graph = new Graph();
		graph.setGraphId(param.getPrdId() + param.getRelPrdId());
		PrdCrossDataTypeEnum prdCrossDataTypeEnum = param
				.getPrdCrossDataTypeEnum();
		if (prdCrossDataTypeEnum == PrdCrossDataTypeEnum.CROSS_USER_CNT) {
			graph.setGraphName("交叉用户数");
		} else if (prdCrossDataTypeEnum == PrdCrossDataTypeEnum.CROSS_USER_RATE) {
			graph.setGraphName("交叉度");
		}
		graph.setGraphType(1);
		graph.setGraphColor(Constants.CHART_COLOR_LIST.get(0));
		return graph;
	}

}
