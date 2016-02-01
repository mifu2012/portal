package com.infosmart.portal.util.dwmis;

import java.util.Calendar;
import java.util.Date;

public class CoreDateToWeekUtil {

	/**
	 * 传入一个日期判断这个日期是今年的第几周
	 * 
	 * @param day
	 *            日期
	 * @return 几年的第几周
	 * @throws DwmisBizSharedException
	 * 
	 */
	public static String dayToWeekend(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int week = calendar.get(Calendar.WEEK_OF_YEAR) - 1;

		// 如果是第12个月并且显示是第一周的话，那么肯定是今年的第53周
		if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER
				&& (calendar.get(Calendar.WEEK_OF_YEAR) == Calendar.SUNDAY || calendar
						.get(Calendar.WEEK_OF_YEAR) == Calendar.SATURDAY)) {
			week = 53;
		}

		String dateValue = "";
		if (week == 0) {
			dateValue = "";
		} else if (week < 10) {
			dateValue = year + "年0" + week + "周";
		} else {
			dateValue = year + "年" + week + "周";
		}

		return dateValue;

	}
}