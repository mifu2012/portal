package com.infosmart.portal.service.dwmis.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.dwmis.DwmisKpiGoal;
import com.infosmart.portal.pojo.dwmis.DwmisKpiInfo;
import com.infosmart.portal.service.dwmis.KPIGoalManager;
import com.infosmart.portal.service.dwmis.SysDateForFixedYear;
import com.infosmart.portal.service.impl.BaseServiceImpl;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.dwmis.CoreConstant;
import com.infosmart.portal.util.dwmis.KPICommonQueryParam;

@Service
public class KPIGoalManagerImpl extends BaseServiceImpl implements
		KPIGoalManager {

	@Autowired
	private SysDateForFixedYear sysDateForFixedYear;

	@Override
	public DwmisKpiGoal getKPIGoalOnCurrentYearByKPICode(String kpiCode) {
		if (kpiCode == null) {
			this.logger
					.warn("KPIGoalManagerImpl.getKPIGoalOnCurrentYearByKPICode()查询参数为空");
			return null;

		}
		DwmisKpiGoal goalBO = new DwmisKpiGoal();

		try {

			// 跨年方案 - 获取被观察年份
			int fixedYear = sysDateForFixedYear.getFixedYear();

			KPICommonQueryParam queryParam = new KPICommonQueryParam();
			queryParam.setKpiCode(kpiCode);
			queryParam.setFixedYear(fixedYear);

			goalBO = this.myBatisDao
					.get("com.infosmart.dwmis.DwmisKpiGoalMapper.QUERY_KPIGOAL_FOR_YEAR",
							queryParam);

			if (goalBO == null) {

				goalBO = new DwmisKpiGoal();
				goalBO.setKpiCode(kpiCode);
				goalBO.setScroll35(new BigDecimal(
						CoreConstant.DEFAULT_GOAL_VALUE));
				goalBO.setScroll50(new BigDecimal(
						CoreConstant.DEFAULT_GOAL_VALUE));
				goalBO.setLastYearKpi(new BigDecimal(
						CoreConstant.DEFAULT_GOAL_VALUE));

				this.logger
						.warn("KPIGoalManagerImpl.getKPIGoalOnCurrentYearByKPICode()"
								+ "查询不到对应KPI GOAL信息，将返回0，KPI CODE= " + kpiCode);

			}

			if (goalBO.getScroll35() == null) {
				goalBO.setScroll35(new BigDecimal(
						CoreConstant.DEFAULT_GOAL_VALUE));
			}

			if (goalBO.getScroll50() == null) {
				goalBO.setScroll50(new BigDecimal(
						CoreConstant.DEFAULT_GOAL_VALUE));
			}

			if (goalBO.getLastYearKpi() == null) {
				goalBO.setLastYearKpi(new BigDecimal(
						CoreConstant.DEFAULT_GOAL_VALUE));
			}
		} catch (Exception e) {
			this.logger
					.error("KPIGoalManagerImpl.getKPIGoalOnCurrentYearByKPICode()访问数据库出错",
							e);
			return null;
		}

		return goalBO;
	}

	@Override
	public Map<String, DwmisKpiGoal> listKPIGoalOnCurrentYear(
			List<DwmisKpiInfo> kpiIngfoList, String queryDate) {
		if (kpiIngfoList == null || kpiIngfoList.isEmpty())
			return null;
		KPICommonQueryParam queryParam = new KPICommonQueryParam();
		List<DwmisKpiGoal> kpiGoalList = new ArrayList<DwmisKpiGoal>();
		for (DwmisKpiInfo kpiInfo : kpiIngfoList) {
			queryParam.setKpiCode(kpiInfo.getKpiCode());
			queryParam.setGoalDate(queryDate);
			if (kpiInfo.getGoalType() == null)
				continue;
			if (kpiInfo.getGoalType() == 3001 || kpiInfo.getGoalType() == 3002
					|| kpiInfo.getGoalType() == 3009
					|| kpiInfo.getGoalType() == 30011
					|| kpiInfo.getGoalType() == 30012
					|| kpiInfo.getGoalType() == 30021) {
				// 3001(月峰值)，3002(月谷值)，3009(月末值)，30011(月峰值-pt)，30012(月峰值-计去年)，30021(月谷值-pt)
				queryParam.setGoalDate(queryDate.substring(0, 4));

			} else if (kpiInfo.getGoalType() == 3005
					|| kpiInfo.getGoalType() == 3007
					|| kpiInfo.getGoalType() == 30052
					|| kpiInfo.getGoalType() == 30051) {
				// 3005(日累积值)，3007(七日均值峰值)，30051(日积累值-pt)，30052(日积累值-计入年)
				queryParam.setGoalDate(queryDate.replace("-", "").substring(0,
						6));
			}
			DwmisKpiGoal kpiGoal = this.myBatisDao
					.get("com.infosmart.dwmis.DwmisKpiGoalMapper.SelectKpiGoalByKpiGodeAndDate",
							queryParam);
			if (kpiGoal != null) {
				kpiGoalList.add(kpiGoal);
			}
		}
		// 跨年方案 - 获取被观察年份
		// queryParam.setFixedYear(sysDateForFixedYear.getFixedYear());
		// queryParam.setFixedYear(Integer.valueOf(queryDate.substring(0, 4)));
		// List<DwmisKpiGoal> kpiGoalList = this.myBatisDao
		// .getList(
		// "com.infosmart.dwmis.DwmisKpiGoalMapper.getKpiGoalByKopiCodeAndDate",
		// queryParam);
		Map<String, DwmisKpiGoal> kpiGoalMap = new HashMap<String, DwmisKpiGoal>();
		if (kpiGoalList == null || kpiGoalList.isEmpty()) {
			this.logger.warn("指标绩效列表为空!");
			return null;
		}
		for (DwmisKpiGoal kpiGoal : kpiGoalList) {
			if (kpiGoalMap.containsKey(kpiGoal.getKpiCode()))
				continue;
			kpiGoalMap.put(kpiGoal.getKpiCode(), kpiGoal);
		}
		return kpiGoalMap;
	}

	@Override
	public List<DwmisKpiGoal> getKPIGoals(String kpiCode) throws Exception {
		if (!StringUtils.notNullAndSpace(kpiCode)) {
			this.logger.warn("根据指标Code查询12个月的绩效值失败:指标Code为空!");
			return null;
		}
		final String[] MONTHS = { "01", "02", "03", "04", "05", "06", "07",
				"08", "09", "10", "11", "12" };
		List<String> list = new ArrayList<String>();
		for (String month : MONTHS) {
			list.add(DateUtils.getThisYear() + month);
		}
		DwmisKpiGoal kGoalDO = new DwmisKpiGoal();
		kGoalDO.setGoalDateList(list);
		kGoalDO.setKpiCode(kpiCode);
		return this.myBatisDao.getList(
				"com.infosmart.dwmis.DwmisKpiGoalMapper.GET_KPIGOALS", kGoalDO);
	}

	@Override
	public DwmisKpiGoal getKPIGoal(String kpiCode) throws Exception {
		if (!StringUtils.notNullAndSpace(kpiCode)) {
			this.logger.warn("根据指标Code查询指标绩效值失败:指标Code为空!");
			return null;
		}
		return this.myBatisDao.get(
				"com.infosmart.dwmis.DwmisKpiGoalMapper.GET_ONE_KPIGOAL",
				kpiCode);
	}

	@Override
	public DwmisKpiGoal getKPIGoalOnCurrentYearByKPICodeWithPatch(String kpiCode) {
		if (!StringUtils.notNullAndSpace(kpiCode)) {
			this.logger
					.warn("KPIGoalManagerImpl.getKPIGoalOnCurrentYearByKPICode()查询参数为空");
			return null;

		}
		DwmisKpiGoal goalBO = new DwmisKpiGoal();

		try {

			// 跨年方案 - 获取被观察年份
			int fixedYear = sysDateForFixedYear.getFixedYear();

			KPICommonQueryParam queryParam = new KPICommonQueryParam();
			queryParam.setKpiCode(kpiCode);
			queryParam.setFixedYear(fixedYear);

			goalBO = this.myBatisDao
					.get("com.infosmart.dwmis.DwmisKpiGoalMapper.QUERY_KPIGOAL_FOR_YEAR",
							queryParam);

			if (goalBO == null) {

				goalBO = new DwmisKpiGoal();
				goalBO.setKpiCode(kpiCode);
				goalBO.setScroll35(new BigDecimal(
						CoreConstant.DEFAULT_GOAL_VALUE));
				goalBO.setScroll50(new BigDecimal(
						CoreConstant.DEFAULT_GOAL_VALUE));
				goalBO.setLastYearKpi(new BigDecimal(
						CoreConstant.DEFAULT_GOAL_VALUE));

				this.logger
						.warn("KPIGoalManagerImpl.getKPIGoalOnCurrentYearByKPICode()"
								+ "查询不到对应KPI GOAL信息，将返回0，KPI CODE= " + kpiCode);

			}

			if (goalBO.getScroll35() == null) {
				goalBO.setScroll35(new BigDecimal(
						CoreConstant.DEFAULT_GOAL_VALUE));
			}

			if (goalBO.getScroll50() == null) {
				goalBO.setScroll50(new BigDecimal(
						CoreConstant.DEFAULT_GOAL_VALUE));
			}

			if (goalBO.getLastYearKpi() == null) {
				goalBO.setLastYearKpi(new BigDecimal(
						CoreConstant.DEFAULT_GOAL_VALUE));
			}

		} catch (Exception e) {
			this.logger
					.error("KPIGoalManagerImpl.getKPIGoalOnCurrentYearByKPICode()访问数据库出错",
							e);

			return null;
		}
		return goalBO;
	}

	@Override
	public DwmisKpiGoal getKpiGoalByKpiCodeAndDate(String kpiCode, String date) {
		if (!StringUtils.notNullAndSpace(kpiCode)
				|| !StringUtils.notNullAndSpace(date)) {
			this.logger.warn("根据指标绩效失败：指标Code或时间为空!");
			return null;
		}
		Map map = new HashMap();
		map.put("kpiCode", kpiCode);
		map.put("date", date);
		return this.myBatisDao
				.get("com.infosmart.dwmis.DwmisKpiGoalMapper.getKpiGoalByKopiCodeAndDate",
						map);

	}
}
