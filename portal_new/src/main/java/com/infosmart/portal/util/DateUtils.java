package com.infosmart.portal.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang.time.DateFormatUtils;

public class DateUtils {
	public static final String FM_PATTERN_CN_MD_HM = "MM月dd日 HH:mm";
	public static final String FM_PATTERN_CN_MD_NO = "MM月dd日";
	public static final String FM_PATTERN_CN_YMD_HM = "yyyy年MM月dd日 HH:mm";
	public static final String FM_PATTERN_CN_YMD_NO = "yyyy年MM月dd日";
	public static final String FM_PATTERN_CN_YM_NO = "yyyy年MM月";
	public static final String FM_PATTERN_EN_MD_HM = "MM-dd HH:mm";
	public static final String FM_PATTERN_EN_MD_NO = "MM-dd";
	public static final String FM_PATTERN_EN_YMD_HM = "yyyy/MM/dd HH:mm";
	public static final String FM_PATTERN_EN_YMD_NO = "yyyy/MM/dd";
	public static final String FM_PATTERN_EN_YM_NO = "yyyy/MM";

	public static java.sql.Date getNowDate() {
		Calendar cal = Calendar.getInstance();
		return new java.sql.Date(cal.getTimeInMillis());
	}

	/**
	 * 得到下一个日期
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date getNextDate(java.util.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}

	/**
	 * 当前时间的前一天
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date getTheNextDay(java.util.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 得到上一个日期
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date getPreviousDate(java.util.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 前七天
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date getPrevious7Date(java.util.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -7);
		return calendar.getTime();
	}

	/**
	 * 前几天Str
	 * 
	 * @param date
	 * @return
	 */
	public static String getPrevious7DateStr(String date, int dayCount) {
		dayCount = dayCount <= 0 ? 7 : dayCount;
		Date starDate = parseByFormatRule(date, "yyyy-MM-dd");
		Date before7Day = getPreviousDateCount(starDate, dayCount);
		String beginDate = DateUtils.fotmatDate4(before7Day).replace("-", "");
		return beginDate;
	}

	/**
	 * 当前日期前几天
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date getPreviousDateCount(java.util.Date date,
			int dayCount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -dayCount);
		return calendar.getTime();
	}

	/**
	 * 得到下一个月份
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date getNextMonth(java.util.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 得到下一个周
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date getNextWeek(java.util.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 7);
		return calendar.getTime();
	}

	/**
	 * 得到上几个周
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date getPreviousWeek(java.util.Date date,
			int weekCount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -7 * weekCount);
		return calendar.getTime();
	}

	/**
	 * 得到上一个月份
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date getPreviousMonth(java.util.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		return calendar.getTime();
	}

	/**
	 * 得到上几个月份
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date getPreviousMonth(java.util.Date date,
			int monthCount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -monthCount);
		return calendar.getTime();
	}

	/**
	 * 得到上一个年份的日期（去年同期）
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date getPreviousYear(java.util.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, -1);
		return calendar.getTime();
	}

	/**
	 * 得到上一个年份最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static String lastYearAtTheEndOfTheDay(java.util.Date date) {

		return formatByFormatRule(
				getPreviousYear(parseByFormatRule(
						DateUtils.formatByFormatRule(date, "yyyy-MM-dd")
								.substring(0, 4) + "-12" + "-31", "yyyy-MM-dd")),
				"yyyy-MM-dd");
	}

	/**
	 * 得到上N个年份的日期
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date getPreviousYear(java.util.Date date,
			int yearCount) {
		yearCount = yearCount <= 0 ? 1 : yearCount;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, -yearCount);
		return calendar.getTime();
	}

	/**
	 * 获取当前日期未xxxx年xx周
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekNo(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		int weekNo = calendar.get(Calendar.WEEK_OF_YEAR);
		return DateUtils.formatByFormatRule(date, "yyyy") + "年" + weekNo + "周";
	}

	public static Timestamp getNowTimestamp() {
		Calendar cal = Calendar.getInstance();
		return new Timestamp(cal.getTimeInMillis());
	}

	public static long getNowDateTimeLong() {
		Calendar cal = Calendar.getInstance();
		return cal.getTimeInMillis();
	}

	public static String getNowDateTimeString() {
		Calendar cal = Calendar.getInstance();
		long millis = cal.getTimeInMillis();
		return formatDateLong(millis);
	}

	public static java.util.Date getDate(long millis) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);
		return cal.getTime();
	}

	public static java.sql.Date getDate(java.util.Date date) {
		if (date == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return new java.sql.Date(cal.getTimeInMillis());
	}

	public static java.sql.Date getDate(String dateString) {
		return java.sql.Date.valueOf(dateString);
	}

	public static String formatDateShort(long millis) {
		String pattern = "MM-dd HH:mm";
		String date = DateFormatUtils.format(millis, pattern);
		return date;
	}

	public static String formatDateLong(long millis) {
		String pattern = "yyyy-MM-dd HH:mm";
		String date = DateFormatUtils.format(millis, pattern, Locale.CHINA);
		return date;
	}

	/**
	 * 按规则转换日期格式
	 * 
	 * @param date
	 * @param formmat
	 * @return
	 */
	public static String formatByFormatRule(java.util.Date date, String formmat) {
		if (formmat == null || formmat.length() == 0)
			formmat = "yyyy-MM-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(formmat);
		String strDate = formatter.format(date);
		return strDate;
	}

	/**
	 * 按规则转换字符为日期格式
	 * 
	 * @param date
	 * @param formmat
	 * @return
	 */
	public static java.util.Date parseByFormatRule(String date, String formmat) {
		if (formmat == null || formmat.length() == 0)
			formmat = "yyyy-MM-dd";
		SimpleDateFormat dateFormat = new SimpleDateFormat(formmat);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			dateFormat = new SimpleDateFormat(formmat.replaceAll("-", ""));
			try {
				return dateFormat.parse(date);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		}
	}

	public static String fotmatDate1(java.util.Date myDate) {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年MM月dd日 HH时mm分ss秒");
		String strDate = formatter.format(myDate);
		return strDate;
	}

	public static String fotmatDate2(java.util.Date myDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
		String strDate = formatter.format(myDate);
		return strDate;
	}

	public static String fotmatDate3(java.util.Date myDate) {
		String strDate = null;
		if (myDate == null)
			strDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		strDate = formatter.format(myDate);
		return strDate;
	}

	public static String fotmatDate4(java.util.Date myDate) {
		String strDate = null;
		if (myDate == null)
			strDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		strDate = formatter.format(myDate);
		return strDate;
	}

	public static String fotmatDate5(java.util.Date myDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String strDate = formatter.format(myDate);
		return strDate;
	}

	public static String fotmatDate6(java.util.Date myDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
		String strDate = formatter.format(myDate);
		return strDate;
	}

	public static String fotmatDate7(java.util.Date myDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String strDate = formatter.format(myDate);
		return strDate;
	}

	public static String fotmatDate8(java.util.Date myDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		String strDate = formatter.format(myDate);
		return strDate;
	}

	public static Timestamp convertDateToTimestampMin(java.sql.Date date) {
		return Timestamp.valueOf(date.toString() + " 00:00:00.000");
	}

	public static Timestamp convertDateToTimestampMax(java.sql.Date date) {
		return Timestamp.valueOf(date.toString() + " 23:59:59.999");
	}

	/**
	 * 列出某月的所有日期
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Date[] getMonthDays(java.sql.Date date) {
		Calendar cale = Calendar.getInstance();
		cale.setTime(date);

		int today = cale.get(5);
		int days = cale.getActualMaximum(5);
		long millis = cale.getTimeInMillis();

		java.sql.Date[] dates = new java.sql.Date[days];
		for (int i = 1; i <= days; i++) {
			long sub = (today - i) * 24 * 60 * 60 * 1000L;
			dates[(i - 1)] = new java.sql.Date(millis - sub);
		}

		cale = null;
		return dates;
	}

	/**
	 * 列出某周的所有日期
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Date[] getWeekDays(java.sql.Date date) {
		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		cale.setFirstDayOfWeek(Calendar.MONDAY);

		int days = 7;
		int today = cale.get(7);
		long millis = cale.getTimeInMillis();

		java.sql.Date[] dates = new java.sql.Date[days];
		for (int i = 1; i <= days; i++) {
			long sub = (today - i) * 24 * 60 * 60 * 1000L;
			dates[(i - 1)] = new java.sql.Date(millis - sub);
		}

		cale = null;
		return dates;
	}

	public static String getTimeSlice(Timestamp startTime, Timestamp endTime) {
		String rtn = "";

		Calendar caleStart = Calendar.getInstance();
		Calendar caleEnd = Calendar.getInstance();
		caleStart.setTimeInMillis(startTime.getTime());
		caleEnd.setTimeInMillis(endTime.getTime());

		String dayStart = caleStart.get(1) + "年" + (caleStart.get(2) + 1) + "月"
				+ caleStart.get(5) + "日";
		String dayEnd = caleEnd.get(1) + "年" + (caleEnd.get(2) + 1) + "月"
				+ caleEnd.get(5) + "日";

		if (dayStart.equals(dayEnd)) {
			rtn = caleStart.get(11) + "点" + caleStart.get(12) + "分-"
					+ caleEnd.get(11) + "点" + caleEnd.get(12) + "分";
		} else {
			rtn = caleStart.get(2) + 1 + "月" + caleStart.get(5) + "日" + "-"
					+ (caleEnd.get(2) + 1) + "月" + caleEnd.get(5) + "日";
		}

		caleStart = null;
		caleEnd = null;
		return rtn;
	}

	public static String getDayWeek(java.util.Date date) {
		String[] days = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		cale.setFirstDayOfWeek(1);

		return date.toString() + " " + days[(cale.get(7) - 1)];
	}

	public static Timestamp getMinDayInMonth(java.sql.Date date) {
		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		cale.set(5, cale.getActualMinimum(5));
		java.sql.Date newDate = new java.sql.Date(cale.getTimeInMillis());

		cale = null;
		return Timestamp.valueOf(newDate.toString() + " 00:00:00.000");
	}

	public static Timestamp getMaxDayInMonth(java.sql.Date date) {
		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		cale.set(5, cale.getActualMaximum(5));
		java.sql.Date newDate = new java.sql.Date(cale.getTimeInMillis());

		cale = null;
		return Timestamp.valueOf(newDate.toString() + " 23:59:59.999");
	}

	public static String getThisYearAndMonth() {
		String dateString = "";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
			java.util.Date currentTime_1 = new java.util.Date();

			dateString = formatter.format(currentTime_1);
		} catch (Exception localException) {
		}

		return dateString;
	}

	public static String getThisYear() {
		String dateString = "";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
			java.util.Date currentTime_1 = new java.util.Date();
			dateString = formatter.format(currentTime_1);
		} catch (Exception localException) {
		}

		return dateString;
	}

	public static String getThisMonth() {
		String dateString = "";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("MM");
			java.util.Date currentTime_1 = new java.util.Date();
			dateString = formatter.format(currentTime_1);
		} catch (Exception localException) {
		}

		return dateString;
	}

	public static String getThisQuarter() {
		String quarter = "";
		String month = getThisMonth();

		if ((month.equals("01")) || (month.equals("02"))
				|| (month.equals("03"))) {
			quarter = "第一季度";
		} else if ((month.equals("04")) || (month.equals("05"))
				|| (month.equals("06"))) {
			quarter = "第二季度";
		} else if ((month.equals("07")) || (month.equals("08"))
				|| (month.equals("09"))) {
			quarter = "第三季度";
		} else {
			quarter = "第四季度";
		}

		return quarter;
	}

	public static String formatUtilDate(java.util.Date aDate, String format) {
		try {
			SimpleDateFormat myFmt = new SimpleDateFormat(format);
			return myFmt.format(aDate);
		} catch (Exception e) {
		}
		return null;
	}

	public static String formatUtilDateUsingDot(java.util.Date adate) {
		try {
			SimpleDateFormat myFmt = new SimpleDateFormat("yyyy.MM.dd");
			return myFmt.format(adate);
		} catch (Exception e) {
		}
		return null;
	}

	public static String formatUtilDateUsingLine(java.util.Date adate) {
		try {
			SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
			return myFmt.format(adate);
		} catch (Exception e) {
		}
		return null;
	}

	public static String formatUtilDateUsingChinese(java.util.Date adate) {
		try {
			SimpleDateFormat myFmt = new SimpleDateFormat("yyyy年MM月dd日");
			return myFmt.format(adate);
		} catch (Exception e) {
		}
		return null;
	}

	public static String formatUtilDatetimeUsingBlank(java.util.Date adate) {
		try {
			SimpleDateFormat myFmt = new SimpleDateFormat("yyyyMMdd");
			return myFmt.format(adate);
		} catch (Exception e) {
		}
		return null;
	}

	public static java.sql.Date parseStringToSqlDate(String strDate) {
		boolean hasGetDate = false;
		try {
			SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date temp1 = myFmt.parse(strDate);
			java.sql.Date result1 = new java.sql.Date(temp1.getTime());
			hasGetDate = true;
			return result1;
		} catch (Exception localException2) {
			try {
				SimpleDateFormat myFmt = new SimpleDateFormat("yyyy年MM月dd日");
				java.util.Date temp2 = myFmt.parse(strDate);
				java.sql.Date result2 = new java.sql.Date(temp2.getTime());
				hasGetDate = true;
				return result2;
			} catch (Exception e1) {
				try {
					SimpleDateFormat myFmt = new SimpleDateFormat("yyyy.MM.dd");
					java.util.Date temp3 = myFmt.parse(strDate);
					java.sql.Date result3 = new java.sql.Date(temp3.getTime());
					hasGetDate = true;
					return result3;
				} catch (Exception e2) {
				}
			}
		}
		return null;
	}

	public static long getDateNumToNow(String strDate) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		long day = 0L;
		if ((strDate != null) && (!strDate.equals(""))) {
			try {
				date = myFormatter.parse(fotmatDate4(new java.util.Date()));
				mydate = myFormatter.parse(strDate);
			} catch (ParseException localParseException) {
			}
			day = (mydate.getTime() - date.getTime()) / 86400000L;
		}
		return day;
	}

	public static String modifyDate(java.util.Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(5, day);
		java.util.Date newdate = cal.getTime();
		String nowdate = fotmatDate4(newdate);
		return nowdate;
	}

	public static java.util.Date parseStringToUtilDate(String strDate) {
		java.util.Date result = null;
		try {
			SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
			result = myFmt.parse(strDate);
		} catch (Exception localException) {
		}
		return result;
	}

	public static int getQuanter(int month) {
		int ret = 1;
		switch (month) {
		case 1:
			ret = 1;
			break;
		case 2:
			ret = 1;
			break;
		case 3:
			ret = 1;
			break;
		case 4:
			ret = 2;
			break;
		case 5:
			ret = 2;
			break;
		case 6:
			ret = 2;
			break;
		case 7:
			ret = 3;
			break;
		case 8:
			ret = 3;
			break;
		case 9:
			ret = 3;
			break;
		case 10:
			ret = 4;
			break;
		case 11:
			ret = 4;
			break;
		case 12:
			ret = 4;
		}

		return ret;
	}

	public static String getFirstMonth(int quarter) {
		String ret = "01";
		switch (quarter) {
		case 1:
			ret = "01";
			break;
		case 2:
			ret = "04";
			break;
		case 3:
			ret = "07";
			break;
		case 4:
			ret = "10";
		}

		return ret;
	}

	public static String getFirstDayThisMonth() {
		String monthbegin = "";
		monthbegin = getThisYearAndMonth() + "-01";
		return monthbegin;
	}

	public static String getFirstDayThisQuarter() {
		String quarbegin = "";
		int month = Integer.parseInt(getThisMonth());
		System.out.println("ThisMonth=" + Integer.parseInt(getThisMonth()));
		quarbegin = getThisYear() + "-" + getFirstMonth(getQuanter(month))
				+ "-01";

		return quarbegin;
	}

	public static String getFirstDayThisYear() {
		String yearbegin = "";
		yearbegin = getThisYear() + "-01-01";
		return yearbegin;
	}

	public static Timestamp getTimestamp(String year, String month, String day) {
		return getTimestamp(year, month, day, "00", "00", "00");
	}

	public static Timestamp getTimestamp(String year, String month, String day,
			String hour, String i, String s) {
		return setTimestamp(year + "-" + month + "-" + day + " " + hour + ":"
				+ i + ":" + s);
	}

	public static Timestamp setTimestamp(String Str) {
		Timestamp time = null;
		try {
			time = Timestamp.valueOf(Str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return time;
	}

	// 计算当天是当年的第几天
	public static int orderDate(Date date) {
		int dateSum = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = format.format(date);
		System.out.println("dateStr=" + dateStr);
		int year = Integer.valueOf(dateStr.substring(0, 4));
		int month = Integer.valueOf(dateStr.substring(5, 7));
		int day = Integer.valueOf(dateStr.substring(8, 10));
		for (int i = 1; i < month; i++) {
			switch (i) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				dateSum += 31;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				dateSum += 30;
				break;
			case 2:
				if (((year % 4 == 0) & (year % 100 != 0)) | (year % 400 == 0))
					dateSum += 29;
				else
					dateSum += 28;
			}
		}

		return dateSum = dateSum + day;
	}

	// 当天所在年有多少天
	public static int getMaxDaysOfYear(Date date) {
		int year = Integer.valueOf(formatByFormatRule(date, "yyyy"));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);

		return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
	}

	// 获取当前时间所在年的周数
	public static int getWeekOfYear(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);

		return c.get(Calendar.WEEK_OF_YEAR);
	}

	// 获取当前时间所在年的最大周数
	public static int getMaxWeekNumOfYear(Date date) {
		int year = Integer.valueOf(formatByFormatRule(date, "yyyy"));
		Calendar c = new GregorianCalendar();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
		return getWeekOfYear(c.getTime());
	}

	// //获得明年第一天的日期
	// public static String getNextYearFirst(){
	// String str = "";
	// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	//
	// Calendar lastDate = Calendar.getInstance();
	// lastDate.add(Calendar.YEAR,1);//加一个年
	// lastDate.set(Calendar.DAY_OF_YEAR, 1);
	// str=sdf.format(lastDate.getTime());
	// return str;
	//
	// }
	/**
	 * 得到下一个年份的日期（后年同期）
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date getNextYear(java.util.Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, +1);
		return calendar.getTime();
	}

	/**
	 * 获取当月天数
	 * 
	 * @param date
	 * @return
	 */
	public static int getDaysOfTheMonth(Date date) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		int days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
		return days;
	}

	/**
	 * 获取当前日期所在的周末的日期
	 * 
	 * @param dae
	 * @return
	 */
	public static java.util.Date getWeekendDay(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 7);
		return cal.getTime();

	}

	/**
	 * 当前日期所在月的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static java.util.Date getLastDayofMonth(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	public static void main(String[] args) {
		Date aaaa = DateUtils.getNextYear(new Date());
		String dddd = DateUtils.formatByFormatRule(aaaa, "yyyyMMdd");
		System.out.println("获取明年第一天日期:" + dddd);

		java.sql.Date date = getDate("2012-10-22");
		System.out
				.print("--------------当前日期所在周末"
						+ DateUtils.formatByFormatRule(getWeekendDay(date),
								"yyyyMMdd"));
		java.sql.Date[] dateArray = getMonthDays(date);
		System.err.println("月份最后一天:" + dateArray[(dateArray.length - 1)]);
		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		cale.setFirstDayOfWeek(1);

		int thisWeekDay = cale.get(7) - 1;
		System.err.println("今天星期几:" + thisWeekDay);

		int maxDay = cale.getActualMaximum(5);
		System.err.println("最大日期:" + maxDay);
		java.sql.Date[] weeklyDate = getWeekDays(new java.sql.Date(
				new java.util.Date().getTime()));
		System.out.println("本周的最后一天:" + weeklyDate[6]);

		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(new java.util.Date());
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		System.out.println("本周的最后一天:" + c.getTime());

		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date());
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		System.out.println("本月的最后一天:" + cal.getTime());

		System.out.println("getPreviousWeek:" + getPreviousWeek(new Date(), 1));

		System.out.println(getWeekOfYear(date) + "1111111");
	}
}
