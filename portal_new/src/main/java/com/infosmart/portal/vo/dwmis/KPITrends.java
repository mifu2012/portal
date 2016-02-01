package com.infosmart.portal.vo.dwmis;

import java.util.List;

import com.infosmart.portal.pojo.dwmis.DwmisKpiInfo;
import com.infosmart.portal.util.dwmis.CoreConstant;
import com.infosmart.portal.util.dwmis.UnitTransformer;

public class KPITrends {
	// 指标信息
	private DwmisKpiInfo kpiInfo;
	// 当前完成数值（有可能为百分比）
	private double amountDone;

	// 当前完成率
	private double percent;

	// 预算值 3.5分
	private double goal35;

	// 预算值5分
	private double goal5;

	// 去年KPI值
	private double lastYearKPI;

	// pt值
	private double ptValue;

	// 不要没删除哦
	private boolean isPTValue;

	// 是否有前钻指标
	private boolean hasSubKPITrends = false;

	// 该指标的所有前钻指标列表
	private List<KPITrends> subKPITrendsList;

	// 是否已经达标，用于展现“预算完成率”的打勾打叉，不同绩效考核类型判断方法不同
	private boolean isPassGoal = false;

	private int passGoal = 1;

	public int getPassGoal() {
		this.passGoal = isPassGoal ? 1 : 0;
		return passGoal;
	}

	public KPITrends(DwmisKpiInfo kpiInfo) {
		super();
		this.kpiInfo = kpiInfo;
	}

	public DwmisKpiInfo getKpiInfo() {
		return kpiInfo;
	}

	public void setKpiInfo(DwmisKpiInfo kpiInfo) {
		this.kpiInfo = kpiInfo;
	}

	// 是否该指标为峰值/谷值类型，为peak的图像将使用十二个月粒度的
	public boolean getIsPeakValue() {

		if (kpiInfo == null) {
			return false;
		}
		if (this.kpiInfo.getGoalType().equals(3001)
				|| this.kpiInfo.getGoalType().equals(3002)
				|| this.kpiInfo.getGoalType().equals(3009)
				|| this.kpiInfo.getGoalType().equals(30011)
				|| this.kpiInfo.getGoalType().equals(30012)
				|| this.kpiInfo.getGoalType().equals(30021)) {

			return true;
		}

		return false;
	}

	// 是否显示为pt值，指定“KPI走势”页面中“完成率”使用PT展现方式：3.5pt
	public boolean getIsPTValue() {
		if (kpiInfo == null) {
			return false;
		}
		if (this.kpiInfo.getGoalType().equals(30011)
				|| this.kpiInfo.getGoalType().equals(30021)
				|| this.kpiInfo.getGoalType().equals(30051)
				|| this.kpiInfo.getGoalType().equals(3009)) {

			return true;
		}

		return false;
	}

	public double getAmountDone() {
		return amountDone;
	}

	public void setAmountDone(double amountDone) {
		this.amountDone = amountDone;
	}

	public double getGoal35() {
		goal35 = UnitTransformer.keepDecimal(goal35,
				CoreConstant.DEFAULT_DIGIT, this.kpiInfo);
		return goal35;
	}

	public void setGoal35(double goal35) {
		this.goal35 = goal35;
	}

	public double getGoal5() {
		goal5 = UnitTransformer.keepDecimal(goal5, CoreConstant.DEFAULT_DIGIT,
				this.kpiInfo);
		return goal5;
	}

	public void setGoal5(double goal5) {
		this.goal5 = goal5;
	}

	/**
	 * @param percent
	 *            the percent to set
	 */
	public void setPercent(double percent) {
		this.percent = percent;
	}

	/**
	 * @return the percent
	 */
	public double getPercent() {
		if (this.percent == CoreConstant.DEFAULT_DATA_NOT_FOUND) {
			this.percent = 0;
		}
		return percent <= 0 ? 0 : percent;
	}

	public void setPTValue(boolean isPTValue) {
		this.isPTValue = isPTValue;
	}

	public void setPassGoal(int passGoal) {
		this.passGoal = passGoal;
	}

	public boolean isHasSubKPITrends() {
		this.hasSubKPITrends = this.subKPITrendsList == null
				|| this.subKPITrendsList.isEmpty() ? false : true;
		return hasSubKPITrends;
	}

	public void setHasSubKPITrends(boolean hasSubKPITrends) {
		this.hasSubKPITrends = hasSubKPITrends;
	}

	public List<KPITrends> getSubKPITrendsList() {
		return subKPITrendsList;
	}

	public void setSubKPITrendsList(List<KPITrends> subKPITrendsList) {
		this.subKPITrendsList = subKPITrendsList;
	}

	/**
	 * @param isPassGoal
	 *            the isPassGoal to set
	 */
	public void setPassGoal(boolean isPassGoal) {
		this.isPassGoal = isPassGoal;
	}

	/**
	 * @return the isPassGoal
	 */
	public boolean isPassGoal() {
		return isPassGoal;
	}

	/**
	 * @param lastYearKPI
	 *            the lastYearKPI to set
	 */
	public void setLastYearKPI(double lastYearKPI) {
		this.lastYearKPI = lastYearKPI;
	}

	/**
	 * @return the lastYearKPI
	 */
	public double getLastYearKPI() {
		lastYearKPI = UnitTransformer.keepDecimal(lastYearKPI,
				CoreConstant.DEFAULT_DIGIT, this.kpiInfo);
		return lastYearKPI;
	}

	/**
	 * @param ptValue
	 *            the ptValue to set
	 */
	public void setPtValue(double ptValue) {
		this.ptValue = ptValue;
	}

	/**
	 * @return the ptValue
	 */
	public double getPtValue() {
		return ptValue;
	}

}
