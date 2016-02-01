package com.infosmart.portal.service.dwmis.impl;

import static java.util.Locale.CHINA;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.dwmis.DwmisMisTypePo;
import com.infosmart.portal.service.dwmis.DwmisMisTypeService;
import com.infosmart.portal.service.dwmis.SysDateForFixedYear;
import com.infosmart.portal.service.impl.BaseServiceImpl;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.dwmis.CoreConstant;

@Service
public class SysDateForFixedYearImpl extends BaseServiceImpl implements
		SysDateForFixedYear {
	@Autowired
	private DwmisMisTypeService misTypeService;

	public static int CRT_YEAR_NO = 0;

	@Override
	public Date getSysDateForFixedYear(Integer dayOffSet) {
		if (CRT_YEAR_NO == 0) {
			// 1，获取当前日历时间与用户在MISType里配置指定的年份
			DwmisMisTypePo misType = this.misTypeService
					.getMisTypeByTypeId(CoreConstant.NEW_YEAR_TYPE_ID);
			try {
				CRT_YEAR_NO = Integer.parseInt(misType.getDetail().trim());
			} catch (NumberFormatException e) {
				this.logger.warn("获取到MISType id=18001 中value为非数字 "
						+ "或 配置数字超出正常年份范围（1800~2100），" + "将返回默认时间源，不做跨年处理。", e);
				return getSysDate(dayOffSet);
			}
		}
		int yearNow = Integer.parseInt(DateUtils.formatUtilDate(new Date(),
				"yyyy"));

		if (CRT_YEAR_NO < yearNow) {
			// 2，如果指定年份小于当前日历年份，返回时间源为指定年份的第二年1月一号
			// 如指定年份为2010，则返回 2011-01-01
			return DateUtils.parseByFormatRule((CRT_YEAR_NO + 1) + "-01-01",
					"yyyy-MM-dd");
		} else {
			// 3，否则，返回旧时间源时间：TimeProcessor.getSysDate(int)
			return getSysDate(dayOffSet);
		}
	}

	@Override
	public int getFixedYear() {
		// 使用上面的接口获取被观察的日期
		Date date = getSysDateForFixedYear(1);

		// 如果该日期是一月一号，则返回其上年年份
		// 否则，返回该日期的年份
		String monthAndDay = DateUtils.formatUtilDate(date, "MMdd");
		if ("0101".equalsIgnoreCase(monthAndDay)) {
			return Integer.parseInt(DateUtils.formatUtilDate(date, "yyyy")) - 1;
		} else {
			return Integer.parseInt(DateUtils.formatUtilDate(date, "yyyy"));
		}
	}

	@Override
	public int getFixedYearForPage() {
		int year = getFixedYear();

		// 如果该年份与当前系统年份一致，这里这个接口返回 0
		if (year == Integer.parseInt(DateUtils.formatUtilDate(new Date(),
				"yyyy"))) {
			return 0;
		} else {
			return year;
		}
	}

	@Override
	public Date getReportDateForFixedYear() {
		Calendar calendar = Calendar.getInstance(CHINA);
		calendar.setTime(getSysDateForFixedYear(1));

		// 指标定制偏移量，以天为单位
		calendar.add(Calendar.DAY_OF_YEAR, -1);

		return calendar.getTime();
	}

	@Override
	public boolean isFixedYearMode() {
		if (getFixedYearForPage() != 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取当前系统时间，用于 KPI DATA 表的 Report Data 列 该方法用于替换 new Date()，sysdate
	 * 
	 * 由于数据清洗出来的时间会延迟，有可能是凌晨四点，也可能是要等1、2天才出来
	 * 
	 * 所以这里设置一个时间转换器，根据指定的时间偏移量在当前系统时间基础上得出带刷选的数据时间， 用这个时间到KPI
	 * Data匹配ReportDate列。
	 * 
	 * 时间偏移量： 这里有两种： 一、全局偏移量，以小时为单位，现在默认为 4 小时，见 ConstantClass类。
	 * 二、指标定制偏移量，以天为单位，每个指标可以指定不同的 T + n
	 * 
	 * @param dayOffSet
	 * @return
	 */
	private static Date getSysDate(Integer dayOffSet) {

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
	}

}
