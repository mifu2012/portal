package com.infosmart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsDateJsonBeanProcessor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.infosmart.po.User;
import com.infosmart.service.SysLogService;
import com.infosmart.service.report.NewReportService;
import com.infosmart.util.Const;

public class BaseController {

	protected final Logger logger = Logger.getLogger(this.getClass());
	// 操作成功
	protected final String isSuccess = "success";
	// 操作失败
	protected final String isFailed = "failed";

	protected final String SUCCESS_ACTION = "/common/save_result";

	protected final String NOT_FOUND_ACTION = "/common/notFound";

	@Autowired
	protected SysLogService sysLogService;
	@Autowired
	private NewReportService newReportService;

	/**
	 * 发送JSON信息到客户端
	 * 
	 * @param showMsg
	 *            String
	 */
	protected void responseJsonMsg(String showMsg, HttpServletResponse response) {
		// this.LOG.info("输出字符" + showMsg);
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			if (showMsg != null) {
				showMsg = showMsg.trim();
			}
			// this.log.info("JSOM_MSG:"+showMsg);
			out.print(showMsg);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 把java对象转换成javascript对象发送到客户端
	 * 
	 * @param object
	 */
	public void sendJsonMsgToClient(Object object, HttpServletResponse response) {
		try {
			JsDateJsonBeanProcessor beanProcessor = new JsDateJsonBeanProcessor();
			JsonConfig config = new JsonConfig();
			config.registerJsonBeanProcessor(java.sql.Date.class, beanProcessor);
			JSONObject jsonObj = JSONObject.fromObject(object, config);
			JSON json = JSONSerializer.toJSON(jsonObj, config);
			this.responseJsonMsg(json.toString(), response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 把java对象数组转换成javascript对象数组发送到客户端
	 * 
	 * @param object
	 */
	public void sendJsonMsgToClient(Object[] objectAry,
			HttpServletResponse response) {
		JsDateJsonBeanProcessor beanProcessor = new JsDateJsonBeanProcessor();
		JsonConfig config = new JsonConfig();
		config.registerJsonBeanProcessor(java.util.Date.class, beanProcessor);
		JSONArray jsonObjAry = JSONArray.fromObject(objectAry, config);
		JSON json = JSONSerializer.toJSON(jsonObjAry, config);
		this.responseJsonMsg(json.toString(), response);
	}

	/**
	 * 转LIST对象转为JSON,发送到客户端
	 * 
	 * @param objectLst
	 * @param response
	 */
	public void sendJsonMsgToClient(List objectLst, HttpServletResponse response) {
		JsDateJsonBeanProcessor beanProcessor = new JsDateJsonBeanProcessor();
		JsonConfig config = new JsonConfig();
		try {
			config.registerJsonBeanProcessor(java.sql.Date.class, beanProcessor);
			JSONArray jsonObjAry = JSONArray.fromObject(objectLst, config);
			JSON json = JSONSerializer.toJSON(jsonObjAry, config);
			this.responseJsonMsg(json.toString(), response);
		} catch (Exception e) {
			config.registerJsonBeanProcessor(java.util.Date.class,
					beanProcessor);
			JSONArray jsonObjAry = JSONArray.fromObject(objectLst, config);
			JSON json = JSONSerializer.toJSON(jsonObjAry, config);
			this.responseJsonMsg(json.toString(), response);
		}
	}

	/**
	 * 转map对象转为JSON,发送到客户端
	 * 
	 * @param objectLst
	 * @param response
	 */
	public void sendJsonMsgToClient(Map objectMap, HttpServletResponse response) {
		JsonConfig config = new JsonConfig();
		JsDateJsonBeanProcessor beanProcessor = new JsDateJsonBeanProcessor();
		config.registerJsonBeanProcessor(java.sql.Date.class, beanProcessor);
		JSONObject jsonObjAry = JSONObject.fromObject(objectMap, config);
		JSON json = JSONSerializer.toJSON(jsonObjAry, config);
		this.responseJsonMsg(json.toString(), response);
	}

	/**
	 * 返回信息到客户端
	 * 
	 * @param showMsg
	 *            String
	 * @throws Exception
	 */
	public void sendMsgToClient(String showMsg, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			if (showMsg != null) {
				showMsg = showMsg.trim();
			}
			out.print(showMsg);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}

	/**
	 * 客户端IP地址
	 * 
	 * @return
	 */
	protected String getClientIp(HttpServletRequest request) {
		// 客户端访问IP
		String clientIp = request.getHeader("x-forwarded-for");
		// 客户端真实IP地址
		if (clientIp == null || clientIp.length() == 0
				|| "unknown".equalsIgnoreCase(clientIp)) {
			clientIp = request.getHeader("Proxy-Client-IP");
		}
		if (clientIp == null || clientIp.length() == 0
				|| "unknown".equalsIgnoreCase(clientIp)) {
			clientIp = request.getHeader("WL-Proxy-Client-IP");
		}
		if (clientIp == null || clientIp.length() == 0
				|| "unknown".equalsIgnoreCase(clientIp)) {
			clientIp = request.getRemoteAddr();
		}
		return clientIp;
	}

	/**
	 * 新增日志
	 * 
	 * @param Module
	 *            模块名称
	 * @param date
	 *            查询模块数据时间
	 */
	public void insertLog(HttpServletRequest request, String opContent) {
		// User user = (User) request.getSession().getAttribute(
		Object crtUser = request.getSession().getAttribute(Const.SESSION_USER);
		if (crtUser != null) {
			User user = (User) crtUser;
			String user_ip = getClientIp(request);
			try {
				sysLogService.insertLog(user, opContent, user_ip);
			} catch (Exception e) {

				e.printStackTrace();
				this.logger.error("插入日志信息失败："+e.getMessage(), e);
			}
			
		}
	}

}
