package com.infosmart.service.dwmis.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.infosmart.po.dwmis.DwmisKpiGoal;
import com.infosmart.service.dwmis.KpiGoalService;
import com.infosmart.service.impl.BaseServiceImpl;
import com.infosmart.util.DateUtils;

@Service
public class KpiGoalServiceImpl extends BaseServiceImpl implements
		KpiGoalService {
	private final String[] MONTHS = { "01", "02", "03", "04", "05", "06", "07",
			"08", "09", "10", "11", "12" };
	public final String GOAL_TYPE = "MONTH";

	@Override
	public DwmisKpiGoal getKPIGoal(String kpiCode) {
		if (StringUtils.isBlank(kpiCode)) {
			this.logger.warn("查询绩效信息失败：指标Code为空！");
			return null;
		}
		return myBatisDao.get("dmisKpiGoalMapper.getkpiGoal", kpiCode);
	}

	/**
	 * 修改指标绩效记录
	 */
	public void updateKPIGoal(List<DwmisKpiGoal> kpiGoalList, String goalType) {
		if (kpiGoalList == null || kpiGoalList.isEmpty()) {
			this.logger.warn("KPIGoal更新失败：参数kpiGoalList为空");
			return;
		}
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		calendar.add(Calendar.YEAR, 0);
		for (int i = 0; i < kpiGoalList.size(); i++) {
			DwmisKpiGoal kpiGoalInfo = kpiGoalList.get(i);
			if (this.GOAL_TYPE.equals(goalType)) {
				kpiGoalInfo.setGoalDate(format.format(calendar.getTime())
						+ this.MONTHS[i]);
			} else {
				kpiGoalInfo.setGoalDate(format.format(calendar.getTime()));
			}
		}
		try {
			for (DwmisKpiGoal kpiGoal : kpiGoalList) {
				myBatisDao.update("dmisKpiGoalMapper.updateKpiGoal", kpiGoal);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("KpiGoalServiceImpl.updateKPIGoal()访问数据库出错", e);
		}
	}

	/**
	 * 插入指标绩效记录
	 * 
	 */
	public void addKPIGoal(List<DwmisKpiGoal> kpiGoalList, String goalType) {
		if (kpiGoalList == null || kpiGoalList.isEmpty()) {
			this.logger.warn("添加KPIGoal失败，kpiGoalList参数为空");
			return;
		}
//		Calendar calendar = Calendar.getInstance();
//		SimpleDateFormat format = new SimpleDateFormat("yyyy");
//		calendar.add(Calendar.YEAR, 0);
//		for (int i = 0; i < kpiGoalList.size(); i++) {
//			DwmisKpiGoal kpiGoalInfo = kpiGoalList.get(i);
//			if (this.GOAL_TYPE.equals(goalType)) {
//				kpiGoalInfo.setGoalDate(format.format(calendar.getTime())
//						+ this.MONTHS[i]);
//			} else {
//				kpiGoalInfo.setGoalDate(format.format(calendar.getTime()));
//			}
//		}
		try {
			for (DwmisKpiGoal kpiGoal : kpiGoalList) {
				myBatisDao.save("dmisKpiGoalMapper.insertKpiGoal", kpiGoal);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("KpiGoalServiceImpl.addKPIGoal()访问数据库出错", e);

		}
	}

	/**
	 * 根据kpiCode获取相关的指标绩效记录(十二个月记录)
	 */
	public List<DwmisKpiGoal> getKPIGoals(String kpiCode) {
		if (StringUtils.isBlank(kpiCode)) {
			this.logger.warn("根据kpiCode获取相关的指标绩效记录(十二个月记录)：指标Code为空！");
			return null;
		}
//		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
//		List<Date> date = DateUtils.getMonths("THIS_YEAR");
//		List<String> list = new ArrayList<String>();
//		for (Date temp : date) {
//			list.add(format.format(temp));
//		}
		DwmisKpiGoal kpiGoal = new DwmisKpiGoal();
//		kpiGoal.setGoalDateList(list);
		kpiGoal.setKpiCode(kpiCode);

		return myBatisDao.getList("dmisKpiGoalMapper.getKpiGoals", kpiGoal);
	}

	public void deleteKpiGoal(DwmisKpiGoal dwmisKpiGoal) {
		if (dwmisKpiGoal == null) {
			this.logger.warn("删除绩效信息失败：绩效信息为空！");
			return;
		}
		this.myBatisDao.delete("dmisKpiGoalMapper.deleteKpiGoal", dwmisKpiGoal);
	}
}
