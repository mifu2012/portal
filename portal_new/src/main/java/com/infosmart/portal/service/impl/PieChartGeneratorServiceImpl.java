package com.infosmart.portal.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasStKpiData;
import com.infosmart.portal.service.DwpasStKpiDataService;
import com.infosmart.portal.service.PieChartGeneratorService;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.vo.PieData;

@Service
public class PieChartGeneratorServiceImpl extends BaseServiceImpl implements
		PieChartGeneratorService {
	@Autowired
	private DwpasStKpiDataService dwpasStKpiDataService;

	public List<PieData> getPieDate(List<String> kpiCodeList,
			List<String> colorList, String reportDate, String dateType) {
		this.logger.info("根据指标编码查询并指标数据并生成饼图数据");
		if (kpiCodeList == null || kpiCodeList.isEmpty()) {
			this.logger.error("根据指标编码查询并指标数据并生成饼图数据失败:指标编码参数为空");
			return null;
		}
		if (colorList == null || colorList.isEmpty()) {
			this.logger.error("根据指标编码查询并指标数据并生成饼图数据失败:颜色集合参数为空");
			return null;
		}
		this.logger.info("KPI_CODE:"+kpiCodeList.get(0)+",COLOR:"+colorList.get(0));
		Map<String, DwpasStKpiData> kpiDataMap = this.dwpasStKpiDataService
				.listDwpasStKpiDataByKpiCode(kpiCodeList, reportDate, dateType);
		if (kpiDataMap == null || kpiDataMap.isEmpty()) {
			this.logger.info("没有找到该指标的指标数据");
			return null;
		}
		// 饼图数据
		List<PieData> pieDataList = new ArrayList<PieData>();
		Entry<String, DwpasStKpiData> entry = null;
		PieData pieData = null;
		DwpasCKpiInfo kpiInfo = null;
		DwpasStKpiData kpiData = null;
		int i = 0;
		// 总的比例
		BigDecimal totalRate = new BigDecimal(0);
		for (Iterator it = kpiDataMap.entrySet().iterator(); it.hasNext();) {
			entry = (Entry<String, DwpasStKpiData>) it.next();
			if (entry == null)
				continue;
			kpiData = entry.getValue();
			kpiInfo = kpiData.getDwpasCKpiInfo();
			pieData = new PieData();
			if (StringUtils.notNullAndSpace(kpiInfo.getDispName())) {
				if ("%".equals(kpiInfo.getUnit())) {
					pieData.setTitle(kpiInfo.getDispName());
				} else {
					pieData.setTitle(kpiInfo.getDispName() + "("
							+ kpiInfo.getUnit() + ")");
				}
			} else {
				if ("%".equals(kpiInfo.getUnit())) {
					pieData.setTitle(kpiInfo.getKpiName());
				} else {
					pieData.setTitle(kpiInfo.getKpiName() + "("
							+ kpiInfo.getUnit() + ")");
				}
			}
			// 总的比例
			totalRate = totalRate.add(kpiData.getShowValue());
			// 比例值
			pieData.setValue(String.valueOf(kpiData.getShowValue()));
			pieData.setColor("#" + colorList.get(i));
			pieData.setKpicode(kpiData.getKpiCode());
			//
			pieDataList.add(pieData);
			//
			i++;
		}
		// this.logger.info("---------------->"+totalRate);
		if (kpiCodeList.size() == 1) {
			if (totalRate.compareTo(Constants.ONE_HANDREN) < 0) {
				pieData = new PieData();
				pieData.setTitle("其他");
				// 比例值
				pieData.setValue(String.valueOf(Constants.ONE_HANDREN.subtract(
						totalRate).setScale(2, BigDecimal.ROUND_HALF_UP)));
				// 颜色
				pieData.setColor("#" + colorList.get(i));
				pieData.setKpicode("");
				//
				pieDataList.add(pieData);
			}
		}
		return pieDataList;
	}

}
