package com.infosmart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.po.SysLog;
import com.infosmart.service.SysLogService;
import com.infosmart.util.StringUtils;

@Controller
@RequestMapping("/sysLog")
public class SysLogController extends BaseController {

	@Autowired
	private SysLogService sysLogService;

	@RequestMapping
	public ModelAndView getList(HttpServletRequest request,
			HttpServletResponse response, SysLog sysLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		String userCode = sysLog.getUser_code();
		String userName = sysLog.getUser_name();
		String operatorContent = sysLog.getOperator_content();
		if (userCode != null && userCode.length() > 0) {
			sysLog.setUser_code(userCode.trim());
		}
		if (userName != null && userName.length() > 0) {
			sysLog.setUser_name(userName.trim());
		}
		if (operatorContent != null && operatorContent.length() > 0) {
			sysLog.setOperator_content(operatorContent.trim());
		}
		List<SysLog> sysLogList = sysLogService.getAllList(sysLog);
		map.put("sysLog", sysLog);
		map.put("sysLogList", sysLogList);
		if (userCode != null || userName != null || operatorContent != null) {
			this.insertLog(request, "查询日志列表");
		}
		// this.insertLog(request, "查询日志列表");
		return new ModelAndView("/sysLog/sysLog", map);
	}
	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/deleteData")
	public void deleteInfo(HttpServletRequest request, HttpServletResponse response) {
		String monthNum = request.getParameter("monthNum");
		if(!StringUtils.notNullAndSpace(monthNum)){
			this.logger.error("删除日志信息失败！" );
			this.sendMsgToClient(isFailed, response);
			return ;
		}
		try {
			sysLogService.deleteSysDataByMonthNum(monthNum);
			this.insertLog(request, "删除"+monthNum+"个月前的日志信息");
			this.sendMsgToClient(isSuccess, response);
		} catch (Exception e) {

			e.printStackTrace();
			this.sendMsgToClient(isFailed, response);
			this.logger.error("删除日志信息失败！" + e.getMessage(), e);
			
		}
		

	}
}
