package com.infosmart.portal.util.dwmis;

import static java.util.Locale.CHINA;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.taglibs.standard.lang.jstl.parser.ParseException;

import com.infosmart.portal.util.DateUtils;

public class TimeFormatProcessor {
    protected final static Logger logger = Logger
            .getLogger(TimeFormatProcessor.class);

    /**
     * 常量
     */
    public static final String FIRST_MONTH = "01";
    public static final String FIRST_DAY = "0101";

    /**
     * 获取当前年的第一天或者第一月字符格式
     *
     * @param expression (如“expression = 01表示当前年的一月份;expression = 0101表是当前年的一月一日”)
     * @return
     */
    public static String getCurrentYearFirstDayOrMonth(String expression,
                                                       Integer dayOffSet) {
        Date date = getSysDate(dayOffSet);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String currentFirstDate = (sdf.format(date) + expression).trim();
        return currentFirstDate;
    }

    /**
     * 根据偏移量获取当前日期的相对日期的字符串格式
     *
     * @return
     */
    public static String getCurrentDate(Integer dayOffSet) {
        Date date = getSysDate(dayOffSet);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(date);
    }

    /**
     * 获取日期对应的月的天数（参数类型为String类型）
     *
     * @param date 例如：201103
     * @return 该年三月份的天数
     */
    public static int getMonthMaxDaysByCurrentDate(String date) {
        Calendar rightNow = Calendar.getInstance();
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4));
        rightNow.clear();
        rightNow.set(year, month - 1, 1);
        int maxDays = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
        return maxDays;
    }

    /**
     * 获取当前系统时间，用于 KPI DATA 表的 Report Data 列 该方法用于替换 new Date()，sysdate
     * <p/>
     * 由于数据清洗出来的时间会延迟，有可能是凌晨四点，也可能是要等1、2天才出来
     * <p/>
     * 所以这里设置一个时间转换器，根据指定的时间偏移量在当前系统时间基础上得出带刷选的数据时间， 用这个时间到KPI
     * Data匹配ReportDate列。
     * <p/>
     * 时间偏移量： 这里有两种： 一、全局偏移量，以小时为单位，现在默认为 4 小时，见 ConstantClass类。
     * 二、指标定制偏移量，以天为单位，每个指标可以指定不同的 T + n
     *
     * @param dayOffSet
     * @return
     */
    public static Date getSysDate(Integer dayOffSet) {

        // MIS系统 数据时间 唯一的时间源
        Date now = new Date();

        Integer dayOffS = dayOffSet;

        if (dayOffS == null) {

            dayOffS = 0;

        } else {

            // 由于系统设计时就是按 T+1 设计的，所以对于 T+1的数据，这里不应该叠加偏移量
            // 对于 T+2 的数据，这里的偏移量应该是相当于 T+1 的，也就是 1
            dayOffS = dayOffS - 1;
        }

        Calendar calendar = Calendar.getInstance(CHINA);
        calendar.setTime(now);

        // 全局偏移量（小时单位）
        calendar.add(Calendar.HOUR_OF_DAY, -1
                * CoreConstant.DEFAULT_HOUR_OFFSET);

        // 指标定制偏移量，以天为单位
        calendar.add(Calendar.DAY_OF_YEAR, -1 * dayOffS);

        // 清除时间信息，成为 00:00:00
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
        // 默认时间
//		return DateUtils.parseByFormatRule("2011-11-11", "yyyy-MM-dd");
    }

    /**
     * 根据日期获取该日期所在周的星期天的日期
     *
     * @param date
     * @return
     */
    public static Date getBelongSunDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String time = format.format(date);
        Calendar c = Calendar.getInstance();
        int year = Integer.parseInt(time.substring(0, 4));
        int month = Integer.parseInt(time.substring(4, 6));
        int day = Integer.parseInt(time.substring(6));
        c.set(year, month - 1, day);
        // 确定该日期在所在周是第几天
        int index = c.get(Calendar.DAY_OF_WEEK);
        if (index != 1) {
            // API里的算法是周日为第一天（因此index为1的时候就是某一星期日的日期），所以用8来减，而不是用7来减
            c.set(year, month - 1, day + (8 - index));
        }
        return c.getTime();
    }

    /**
     * 根据日期获取该月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String time = format.format(date);
        String year = time.substring(0, 4);
        String month = time.substring(4, 6);
        String flag = year + month;
        int maxDays = getMonthMaxDaysByCurrentDate(flag);
        Date lastDay = null;
        try {
            lastDay = format.parse(year + month + maxDays);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return null;
        }
        return lastDay;
    }

    /**
     * 是否是闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        if ((year % 4 == 0) && ((year % 100 != 0) | (year % 400 == 0))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 在根据系统的报告日期和不同的粒度（周和月）查找上个周期的对应日期值：周：上个周末，月：上个月末的值
     *
     * @param reportDate 报告日期
     * @param period     时间粒度
     * @author wb-songxd
     */
    public static Date getReportDateForPeriod(Date reportDate, int period) {
        Calendar cal = Calendar.getInstance();

        try {
            cal.setTime(reportDate);
            // 如果是周粒度计算上个周末的日期，如果是月粒度，计算出上个月末的日期
            if (period == CoreConstant.WEEK_PERIOD) {
                int index = cal.get(Calendar.DAY_OF_WEEK);
                cal.add(Calendar.DAY_OF_MONTH, -(index - 1));
            } else if (period == CoreConstant.MONTH_PERIOD) {
                int index = cal.get(Calendar.DAY_OF_MONTH);
                cal.add(Calendar.DAY_OF_MONTH, -index);
            }
        } catch (Exception e) {
            logger.error("根据系统的报告日期和不同的粒度（周和月）查找上个周期的对应日期值", e);
        }
        return cal.getTime();
    }

    /***
     * 如果currentDate 在报告日期所在的月或者周，并且报告日期不是月末，不是周末，那么取相对报告日期的上个周末或者上个月末的日期值
     *
     * @param currentDate 当前日期
     * @param reportDate  报告日期
     * @param period      时间粒度：日、周、月
     * @author wb-songxd
     */
    public static Date getLastPeriodDateForData(Date currentDate,
                                                Date reportDate, int period) {
        Calendar cal = Calendar.getInstance();
        Calendar reportCal = Calendar.getInstance();
        // 月末值或者周末值
        Date endOfDate = currentDate;
        try {
            cal.setTime(currentDate);
            reportCal.setTime(reportDate);
            // 如果当前日期和报告日期在同一个周或者同一个月，则获得报告日期的的月末和周末值
            if (cal.get(Calendar.MONTH) == reportCal.get(Calendar.MONTH)) {
                if (period == CoreConstant.WEEK_PERIOD) {
                    // 判断当前日期是否在报告日期的周粒度范围内
                    if (cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == reportCal
                            .get(Calendar.DAY_OF_WEEK_IN_MONTH)) {
                        int weekValue = cal.get(Calendar.DAY_OF_WEEK);
                        if (weekValue != Calendar.SUNDAY) {
                            endOfDate = getReportDateForPeriod(reportDate,
                                    period);
                        }
                    }
                } else if (period == CoreConstant.MONTH_PERIOD) {
                    int monthMaxValue = cal.getMaximum(Calendar.DAY_OF_MONTH);
                    int monthValue = cal.get(Calendar.DAY_OF_MONTH);
                    if (monthValue < monthMaxValue) {
                        endOfDate = getReportDateForPeriod(reportDate, period);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("在周粒度或者月粒度下查找最近的数据日期", e);
        }
        return endOfDate;
    }

    /**
     * 在指定日期的基础上加一年
     *
     * @param date
     * @return
     */
    public static Date addOneYear(Date date) {
        Calendar calendar = Calendar.getInstance(CHINA);
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 1);

        return calendar.getTime();
    }
}
