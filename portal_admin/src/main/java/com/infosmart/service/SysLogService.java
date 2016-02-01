package com.infosmart.service;

import java.util.List;

import com.infosmart.po.SysLog;
import com.infosmart.po.User;

public interface SysLogService {

	/**
	 * 增加操作内容日志
	 * 
	 * @param user用户对象
	 * @param module操作内容
	 * @param user_ip用户IP
	 */
	public void insertLog(User user, String module, String user_ip) throws Exception;

	/**
	 * 查询所有日志
	 * 
	 * @return
	 */
	public List<SysLog> getAllList(SysLog sysLog);
	/**
	 * 删除几个月前日志相关数据
	 * @param monthNum
	 */
	public void deleteSysDataByMonthNum(String monthNum)throws Exception;

}
