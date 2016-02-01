package com.infosmart.portal.util;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

public class DateConvertEditor extends PropertyEditorSupport {
	private SimpleDateFormat datetimeFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final Logger logger = Logger
			.getLogger(DateConvertEditor.class);

	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasText(text)) {
			try {
				if (text.indexOf(":") == -1 && text.length() == 10) {
					logger.debug("yyyy-MM-dd:" + text);
					setValue(this.dateFormat.parse(text));
				} else if (text.indexOf(":") > 0 && text.length() == 19) {
					logger.debug("yyyy-MM-dd HH:mm:ss" + text);
					setValue(this.datetimeFormat.parse(text));
				} else {
					logger.debug("Could not parse date, date format is error ");
					throw new IllegalArgumentException(
							"Could not parse date, date format is error ");
				}
			} catch (ParseException ex) {
				logger.debug("Could not parse date: " + ex.getMessage());
				IllegalArgumentException iae = new IllegalArgumentException(
						"Could not parse date: " + ex.getMessage());
				iae.initCause(ex);
				throw iae;
			}
		} else {
			setValue(null);
		}
	}

	public static Object obj = new Object();
	public static final DateFormat FORMAT_DAY = new SimpleDateFormat("yyyyMMdd");
	public static final DateFormat FORMAT_MONTH = new SimpleDateFormat("yyyyMM");

	static final Logger LOG = Logger.getLogger(DateConvertEditor.class);

	public static String getSpecifiedDayBefore(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {

			synchronized (obj) {
				date = FORMAT_DAY.parse(specifiedDay);
			}
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		String dayBefore = null;
		synchronized (obj) {
			dayBefore = FORMAT_DAY.format(c.getTime());
			return dayBefore;
		}
	}

	public static String getSpecifiedMonthBefore(String specifiedMonth) {

		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			synchronized (obj) {
				date = FORMAT_MONTH.parse(specifiedMonth);
			}
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		c.set(Calendar.MONTH, month - 1);
		String monthBefore = null;
		synchronized (obj) {
			monthBefore = FORMAT_MONTH.format(c.getTime());
			return monthBefore;
		}

	}

	/**
	 * 获得当前月的前一个月
	 * 
	 * @return
	 */
	public static String getBeforeMonth() {
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		c.set(Calendar.MONTH, month - 1);
		synchronized (obj) {
			return FORMAT_MONTH.format(c.getTime());
		}
	}

	/**
	 * 时间格式化，201106-->2011年06月,20110601-->2011年06月01
	 * 
	 * @param date
	 *            需要格式化的时间
	 * @return 格式化好的时间
	 */
	public static String formatDate(String date) {
		// if (StringUtil.isBlank(date)) {
		// return null;
		// }
		StringBuilder finalDate = new StringBuilder(date);
		if (date.length() == 6) {
			finalDate.insert(4, "年").insert(finalDate.length(), "月");
		}
		if (date.length() == 8) {
			finalDate.insert(4, "年").insert(7, "月")
					.insert(finalDate.length(), "日");
		}
		return finalDate.toString();
	}

	/**
	 * 时间格式化，如果为空则返回 -- 201106-->2011年06月,20110601-->2011年06月01
	 * 
	 * @param date
	 *            需要格式化的时间
	 * @return 格式化好的时间
	 */
	public static String formatDate_p(String date) {
		// if (StringUtil.isBlank(date)) {
		// return "-";
		// }
		StringBuilder finalDate = new StringBuilder(date);
		if (date.length() == 6) {
			finalDate.insert(4, "-").insert(finalDate.length(), "-");
		}
		if (date.length() == 8) {
			finalDate.insert(4, "-").insert(7, "-")
					.insert(finalDate.length(), "");
		}
		return finalDate.toString();
	}

	/**
	 * 按格式获得上一日/月的日期
	 * 
	 * @param date
	 *            输入日期
	 * @return 输出日期
	 */
	public static synchronized String getYesterday(String date) {
		// if (StringUtil.isBlank(date)) {
		// return null;
		// }
		try {
			// date为日时间
			if (date.length() == 8) {

				Date tmp = FORMAT_DAY.parse(date);
				Calendar cal = Calendar.getInstance();
				cal.setTime(tmp);
				cal.add(Calendar.DATE, -1);
				String yesterday = FORMAT_DAY.format(cal.getTime());
				return yesterday;
			} else if (date.length() == 6) {
				Date tmp = FORMAT_MONTH.parse(date);
				Calendar cal = Calendar.getInstance();
				cal.setTime(tmp);
				cal.add(Calendar.MONTH, -1);
				String yesterday = FORMAT_MONTH.format(cal.getTime());
				return yesterday;
			}
		} catch (ParseException e) {
			LOG.error("日期格式错误!", e);
		}
		return null;
	}

	/**
	 * 按格式获得下一日/月的日期
	 * 
	 * @param date
	 *            输入日期
	 * @return 输出日期
	 */
	public static synchronized String getTomorrow(String date) {
		// if (StringUtil.isBlank(date)) {
		// return null;
		// }
		try {
			// date为日时间
			if (date.length() == 8) {

				Date tmp = FORMAT_DAY.parse(date);
				Calendar cal = Calendar.getInstance();
				cal.setTime(tmp);
				cal.add(Calendar.DATE, 1);
				String yesterday = FORMAT_DAY.format(cal.getTime());
				return yesterday;
			} else if (date.length() == 6) {
				Date tmp = FORMAT_MONTH.parse(date);
				Calendar cal = Calendar.getInstance();
				cal.setTime(tmp);
				cal.add(Calendar.MONTH, 1);
				String yesterday = FORMAT_MONTH.format(cal.getTime());
				return yesterday;
			}
		} catch (ParseException e) {
			LOG.error("日期格式错误!", e);
		}
		return null;
	}
}
