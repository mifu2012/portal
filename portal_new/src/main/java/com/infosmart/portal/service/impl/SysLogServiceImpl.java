package com.infosmart.portal.service.impl;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.SysLog;
import com.infosmart.portal.pojo.User;
import com.infosmart.portal.service.SysLogService;

@Service
public class SysLogServiceImpl extends BaseServiceImpl implements SysLogService {

	/**
	 * 增加操作内容日志
	 * 
	 * @param user用户对象
	 * @param module操作内容
	 * @param user_ip用户IP
	 */
	@Override
	public void insertLog(User user, String opContent,String user_ip) {
		SysLog sysLog = new SysLog();
		sysLog.setUser_id(user.getUserId().toString());
		sysLog.setUser_code(user.getLoginName());
		sysLog.setUser_name(user.getUserName());
		sysLog.setOperator_content(opContent);
		sysLog.setUser_ip(user_ip);
		this.myBatisDao.save("com.infosmart.mapper.SysLogMapper.INSERT_SYSLOG",
				sysLog);
	}
}
