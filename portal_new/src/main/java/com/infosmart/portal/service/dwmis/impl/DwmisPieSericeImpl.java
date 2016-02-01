package com.infosmart.portal.service.dwmis.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.dwmis.DwmisKpiData;
import com.infosmart.portal.pojo.dwmis.DwmisKpiInfo;
import com.infosmart.portal.service.dwmis.DwmisKpiDataService;
import com.infosmart.portal.service.dwmis.DwmisKpiInfoService;
import com.infosmart.portal.service.dwmis.DwmisPieService;
import com.infosmart.portal.service.impl.BaseServiceImpl;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.dwmis.CoreConstant;
import com.infosmart.portal.util.dwmis.TimeFormatProcessor;
import com.infosmart.portal.vo.PieData;

@Service
public class DwmisPieSericeImpl extends BaseServiceImpl implements
		DwmisPieService {

	@Autowired
	private DwmisKpiDataService dwmisKpiDataService;

	@Autowired
	private DwmisKpiInfoService dwmisKpiInfoService;

	@Override
	public List<PieData> getDwmisPieDateByCodeList(List<String> kpiCodelist,
			List<String> colorList, int dateType, int staCode, String queryDate)
			throws Exception {
		this.logger.info("根据指标编码查询并指标数据并生成饼图数据");
		if (kpiCodelist == null || kpiCodelist.isEmpty()) {
			this.logger.error("根据指标编码查询并指标数据并生成饼图数据失败:指标编码参数为空");
			return null;
		}
		if (colorList == null || colorList.isEmpty()) {
			this.logger.error("根据指标编码查询并指标数据并生成饼图数据失败:颜色集合参数为空");
			return null;
		}
		List<PieData> pieDataList = new ArrayList<PieData>();
		for (int i = 0; i < kpiCodelist.size(); i++) {
			DwmisKpiInfo kpiInfo = dwmisKpiInfoService
					.getDwmisKpiInfoByCode(kpiCodelist.get(i));
			if (kpiInfo == null) {
				continue;
			}
			Date dateDate = null;

			if (dateType == CoreConstant.DAY_PERIOD) {
				// 日粒度 取得查询时间
				dateDate = DateUtils.parseByFormatRule(queryDate, "yyyy-MM-dd");
			} else if (dateType == CoreConstant.WEEK_PERIOD) {
				// 周粒度 取得该星期的周日
				dateDate = TimeFormatProcessor.getBelongSunDay(DateUtils
						.parseByFormatRule(queryDate, "yyyy-MM-dd"));
			} else {
				// 月粒度 取得该月的最后一天
				dateDate = TimeFormatProcessor.getLastDayOfMonth(DateUtils
						.parseByFormatRule(queryDate, "yyyy-MM-dd"));
			}

			List<DwmisKpiData> kpiDataList = dwmisKpiDataService
					.getKpiDataByCodeAndDate(kpiCodelist.get(i), dateDate,
							dateType, staCode);
			if (kpiDataList == null || kpiDataList.isEmpty()) {
				continue;
			}
			PieData pieData = null;
			DecimalFormat df = new java.text.DecimalFormat("#.0");
			// df.format(你要格式化的数字);
			for (DwmisKpiData kpiData : kpiDataList) {
				pieData = new PieData();
				if (StringUtils.notNullAndSpace(kpiData.getDwmisKpiInfo()
						.getKpiNameShow())) {
					if (kpiData.getDwmisKpiInfo().getUnitName().equals("%")
							|| kpiData.getDwmisKpiInfo().getSizName()
									.equals("个")) {
						pieData.setTitle(kpiData.getDwmisKpiInfo()
								.getKpiNameShow()
								+ "("
								+ kpiData.getDwmisKpiInfo().getUnitName() + ")");
					} else {
						pieData.setTitle(kpiData.getDwmisKpiInfo()
								.getKpiNameShow()
								+ "("
								+ kpiData.getDwmisKpiInfo().getSizName()
								+ kpiData.getDwmisKpiInfo().getUnitName() + ")");
					}
					// if (kpiData.getDwmisKpiInfo().getUnitName().equals("个"))
					// {
					pieData.setValue(String.valueOf(kpiData.getShowValue()));
					// } else {
					// BigDecimal value = kpiData.getValue().setScale(1,
					// BigDecimal.ROUND_HALF_UP);
					// pieData.setValue(String.valueOf(value));
					// }
				}
				// 值
				pieData.setColor(colorList.get(i));
				pieData.setKpicode(kpiData.getKpiCode());

				pieDataList.add(pieData);
			}
		}
		return pieDataList;
	}
}
