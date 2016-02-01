package com.infosmart.portal.service.dwmis;

import java.util.Date;

public interface SysDateForFixedYear {

	/**
	 * 返回为固定年图表（从该年一月份到十二月份）准备的时间源
	 * 
	 * 1，获取当前日历时间与用户在MISType里配置指定的年份
	 * 2，如果指定年份小于当前日历年份，返回时间源为指定年份的第二年1月一号，如指定年份为2010，则返回 2011-01-01
	 * 3，否则，返回旧时间源时间：TimeProcessor.getSysDate(int)
	 * 
	 * @param dayOffSet
	 * @return
	 * @author jin.zheng
	 * @version $Id: SysDateForFixedYear.java, v 0.1 2011-11-29 下午05:18:32
	 *          jin.zheng Exp $
	 */
	public Date getSysDateForFixedYear(Integer dayOffSet);

	/**
	 * 返回当前要观察的年份 如果被观察年份等于或超过当前时间年份，返回当前时间年份 否则，返回被观察年份
	 * 
	 * 这个接口用于查询年峰值、年谷值等SQL中
	 * 
	 * @return
	 * @author jin.zheng
	 * @version $Id: SysDateForFixedYearImpl.java, v 0.1 2011-12-12 上午11:13:43
	 *          jin.zheng Exp $
	 */
	public int getFixedYear();

	/**
	 * 返回当前要观察的年份（这个是给页面VM使用的）
	 * 
	 * 用于展示类似于（ “2011年快照”） 如果该年份与当前系统年份一致，则页面上不应显示上述字样，这里这个接口返回 0
	 * 
	 * @return
	 * @author jin.zheng
	 * @version $Id: SysDateForFixedYearImpl.java, v 0.1 2011-12-12 下午03:54:21
	 *          jin.zheng Exp $
	 */
	public int getFixedYearForPage();

	/**
	 * 用于在页面展示报告日期的日期 这个日期就是 getSysDateForFixedYear() 日期的前一天
	 * 
	 * @return
	 * @author jin.zheng
	 * @version $Id: SysDateForFixedYearImpl.java, v 0.1 2011-12-12 下午04:20:34
	 *          jin.zheng Exp $
	 */
	public Date getReportDateForFixedYear();

	/**
	 * 当前是否采用跨年方案模式
	 * 
	 * 用于业绩跟踪模块（KPITrendManagerImpl）中根据时间判断是否达标（isPassGoal()）
	 * 
	 * @return
	 * @author jin.zheng
	 * @version $Id: SysDateForFixedYearImpl.java, v 0.1 2011-12-12 下午08:49:46
	 *          jin.zheng Exp $
	 */
	public boolean isFixedYearMode();

}