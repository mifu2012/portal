package com.infosmart.portal.service;

import com.infosmart.portal.pojo.User;

public interface SysLogService {

	/**
	 * 增加操作内容日志
	 * 
	 * @param user用户对象
	 * @param module操作内容
	 * @param user_ip用户IP
	 */
	public void insertLog(User user, String module, String user_ip);

}
