package com.infosmart.portal.service.dwmis.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.dwmis.DwmisKpiData;
import com.infosmart.portal.pojo.dwmis.DwmisKpiInfo;
import com.infosmart.portal.service.dwmis.KPILinkChartManager;
import com.infosmart.portal.util.dwmis.CoreConstant;
import com.infosmart.portal.util.dwmis.KPICommonQueryParam;
import com.infosmart.portal.util.dwmis.TimeFormatProcessor;
import com.infosmart.portal.vo.dwmis.ChartData;
import com.infosmart.portal.vo.dwmis.LineData;
import com.infosmart.portal.vo.dwmis.LineElem;

@Service
public class KPILinkChartManagerImpl extends KPIChartManagerImpl implements
		KPILinkChartManager {

	@Override
	public ChartData getKPIDataForLinkedKPIPage(String kpiCode, int period,
			boolean isTable, String linkedPageChartType, String domian,
			boolean havaGoal, String haveEvent) {
		// 是否加关联指标的线
		boolean isRelatedLines = true;

		// 是否是原始数据
		boolean isOriginal = !isTable;

		// 原始数据不关联其它指标
		if ("original".equals(linkedPageChartType)
				|| CoreConstant.DOMAIN_FINANCE.equalsIgnoreCase(domian)) {
			// isOriginal = !isTable;
			isRelatedLines = false;
		}

		DwmisKpiInfo kpiInfo = this.kpiInfoService
				.getDwmisKpiInfoByCode(kpiCode);

		// 关联的KPICODE集
		List<DwmisKpiInfo> relatedKPIInfo = this.kpiInfoService
				.listLinkKpiInfoByCode(kpiCode);
		KPICommonQueryParam param = new KPICommonQueryParam();
		param.setDateType(period);

		// 主指标、主指标去年同期、各个关联指标的线元素的集合
		List<LineData> lines = new ArrayList<LineData>();
		// List<MISEventDO> events=new ArrayList<MISEventDO>();
		// 要原始数据时，不用加
		if (isRelatedLines) {
			// 关联指标
			// 根据不同的关联指标KPICODE进行分类并组装点、线元素
			for (DwmisKpiInfo infoDO : relatedKPIInfo) {
				param.setKpiCode(infoDO.getKpiCode());
				param.setCurrentDate(TimeFormatProcessor.getCurrentDate(infoDO
						.getDayOffSet()));
				// 某一关联指标的数据
				List<DwmisKpiData> relatedKPIDataList = this.myBatisDao
						.getList(
								"com.infosmart.dwmis.DwmisMisKpiDataMapper.QUERY_RELATED_KPIDATA_FOR_DETAIL_VIEW",
								param);
				List<LineElem> elems = this.getDataLineElems(infoDO,
						relatedKPIDataList, false, isOriginal, isOriginal,
						domian);
				LineData lineData = this.getLineData(elems,
						LineData.NORMAL_LINE, infoDO);
				lines.add(lineData);

				// 金融界面添加了大事记的隐藏和显示的开关 wb-yingpf 6.20
				if ("true".equals(haveEvent)) {
					// 与关联标关联的大事记列表（两年内的）
					lineData.setChartEvent(this.getChartEvents(
							this.getEventsByKPIOnCondition(param), period));
				}

			}
		}

		// 重新设置主指标的参数
		param.setKpiCode(kpiCode);
		param.setCurrentDate(TimeFormatProcessor.getCurrentDate(kpiInfo
				.getDayOffSet()));

		// 主指标数据集
		List<DwmisKpiData> mainDatas = this.myBatisDao
				.getList(
						"com.infosmart.dwmis.DwmisMisKpiDataMapper.QUERY_RELATED_MAIN_KPIDATA_FOR_DETAIL_VIEW",
						param);
		// need to judge here
		List<LineElem> main_elems = this.getDataLineElems(kpiInfo, mainDatas,
				false, isOriginal, isOriginal, domian);
		LineData main_lineData = this.getLineData(main_elems,
				LineData.MAIN_LINE, kpiInfo);

		// 金融界面添加了大事记的隐藏和显示的开关 wb-yingpf 6.20
		if ("true".equals(haveEvent)) {
			main_lineData.setChartEvent(this.getChartEvents(
					this.getEventsByKPIOnCondition(param), period));
		}
		lines.add(main_lineData);

		LineData last_main_lineData = null;
		// 非金融专题添加去年同期
		if (!CoreConstant.DOMAIN_FINANCE.equalsIgnoreCase(domian)) {
			// 如果时间粒度为周就不再显示去年同期的数据
			if (period != 1003) {
				// 特殊处理(能更好区分当前主线和去年主线)
				DwmisKpiInfo temp = kpiInfo;
				temp.setKpiCode(kpiCode + "_LastYear");
				temp.setKpiNameShow("去年同期");
				temp.setKpiName("去年同期");
				// 主指标去年同期数据集
				List<DwmisKpiData> last_mianDatas = this.myBatisDao
						.getList(
								"com.infosmart.dwmis.DwmisMisKpiDataMapper.QUERY_RELATED_MAI_KPIDATA_LAST_PERIOD",
								param);
				List<LineElem> last_main_elems = this.getDataLineElems(temp,
						last_mianDatas, true, isOriginal, isOriginal, domian);
				last_main_lineData = this.getLineData(last_main_elems,
						LineData.LAST_YEAR_LINE, temp);
				lines.add(last_main_lineData);
			}
		}

		/*
		 * if("true".equals(haveEvent)){ //与主指标关联的大事记列表（两年内的）
		 * events.addAll(this.getEventsByKPIOnCondition(param)); }
		 * List<ChartEvent> charEvents = this.getChartEvents(events, period);
		 */

		ChartData chartData = null;

		// 3表示详情分析页面,组装返回视图对象
		if (havaGoal) {
			// 金融，有目标线
			chartData = this.getCharFinanceDate(kpiInfo, lines, 3, kpiCode);
		} else {
			// 没有目标线，详情和金融的没有目标线使用
			chartData = this.getCharDate(lines, 3);
		}

		chartData.setMainLine(main_lineData);
		chartData.setLastYearLine(last_main_lineData);
		return chartData;
	}

}
