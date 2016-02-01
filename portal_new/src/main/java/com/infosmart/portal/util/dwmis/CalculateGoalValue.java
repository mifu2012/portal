package com.infosmart.portal.util.dwmis;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import com.infosmart.portal.util.DateUtils;

/**
 * 去目标数据
 * 
 * @author Administrator
 * 
 */
public class CalculateGoalValue {

	/**
	 * 该年当日的值
	 * 
	 * @param value
	 * @param date
	 * @return
	 */
	public static double CalculateGoalByDay(double value, Date date) {
		int countDay = DateUtils.orderDate(date);
		int maxDays = DateUtils.getMaxDaysOfYear(date);
		double newValue = value / maxDays * countDay;
		return getTwoPoint(newValue);
		// return new java.math.BigDecimal(newValue);
	}

	/**
	 * 该年当周的值
	 * 
	 * @param value
	 * @param date
	 * @return
	 */
	public static double CalculateGoalByWeek(double value, Date date) {
		int countWeek = DateUtils.getWeekOfYear(date);
		int maxWeek = DateUtils.getMaxWeekNumOfYear(date);
		double newValue = value / maxWeek * countWeek;
		return getTwoPoint(newValue);
	}

	/**
	 * 该年当月的值
	 * 
	 * @param value
	 * @param date
	 * @return
	 */
	public static double CalculateGoalByMonth(double value, Date date) {
		int countMonth = date.getMonth();
		double newValue = value / 12 * countMonth;
		return getTwoPoint(newValue);
	}

	/**
	 * 该月当天的值
	 * 
	 * @param value
	 * @param date
	 * @return
	 */
	public static BigDecimal CalculateGoalByMonthAndDay(double value, Date date) {
		int countday = DateUtils.getDaysOfTheMonth(date);
		int day = date.getDate();
		double newValue = value / countday * day;
		return new java.math.BigDecimal(newValue);
	}

	/**
	 * 取小数点后两位
	 * 
	 * @param value
	 * @return
	 */
	public static double getTwoPoint(double value) {
		DecimalFormat myFormatter = new DecimalFormat("####.##");
		return Double.valueOf(myFormatter.format(value));
	}
}
