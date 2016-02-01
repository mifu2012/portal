package com.infosmart.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Service;

import com.infosmart.po.SysLog;
import com.infosmart.po.User;
import com.infosmart.service.SysLogService;
import com.infosmart.util.StringUtils;

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
	public void insertLog(User user, String opContent, String user_ip)throws Exception {
		if(user == null){
			this.logger.warn("插入日志失败：参数user为空");
			throw new Exception("插入日志失败：参数user为空");
		}
		SysLog sysLog = new SysLog();
		sysLog.setUser_id(user.getUserId().toString());
		sysLog.setUser_code(user.getLoginname());
		sysLog.setUser_name(user.getUsername());
		sysLog.setOperator_content(opContent);
		sysLog.setUser_ip(user_ip);
		this.myBatisDao.save("com.infosmart.mapper.SysLogMapper.INSERT_SYSLOG",
				sysLog);
	}

	@Override
	public List<SysLog> getAllList(SysLog sysLog) {
		if (sysLog == null) {
			this.logger.warn("获取日志操作时间失败：参数syslog为空");
		} else {
			if (sysLog.getOperator_time() != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String op_time = dateFormat.format(sysLog.getOperator_time());
				sysLog.setOp_time(op_time);
			}
		}
		List<SysLog> list = this.myBatisDao.getList(
				"com.infosmart.mapper.SysLogMapper.listPageSysLog", sysLog);
		return list;
	}
	
	/**
	 * 删除几个月前日志相关数据
	 * @param monthNum
	 */
	@Override
	public void deleteSysDataByMonthNum(String monthNum)throws Exception{
		if(!StringUtils.notNullAndSpace(monthNum)){
			this.logger.error("删除日志信息失败,参数monthNum为空!" );
			throw new Exception("删除日志信息失败,参数monthNum为空!");
		}
		this.myBatisDao.delete("com.infosmart.mapper.SysLogMapper.deleteSysDataByMonthNum", monthNum);
	}
}
