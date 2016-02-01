package com.infosmart.portal.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsDateJsonBeanProcessor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.infosmart.portal.pojo.DwpasCPrdInfo;
import com.infosmart.portal.pojo.User;
import com.infosmart.portal.service.DwpasCColumnInfoService;
import com.infosmart.portal.service.DwpasCComKpiInfoService;
import com.infosmart.portal.service.DwpasCKpiInfoService;
import com.infosmart.portal.service.DwpasCPrdInfoService;
import com.infosmart.portal.service.DwpasStKpiDataService;
import com.infosmart.portal.service.ProdInfoService;
import com.infosmart.portal.service.SysLogService;
import com.infosmart.portal.service.dwmis.DwmisKpiDataService;
import com.infosmart.portal.service.dwmis.DwmisKpiInfoService;
import com.infosmart.portal.util.Const;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.StringUtils;

/**
 * 控制层基类
 * 
 * @author infosmart
 * 
 */
public class BaseController {
	protected final Logger logger = Logger.getLogger(this.getClass());
	// 操作成功
	protected final String isSuccess = "success";
	// 操作失败
	protected final String isFailed = "failed";
	// 默认找不到页面或数据的页面
	protected final String NOT_FOUND_PAGE = "/common/noModule";
	protected final String NOT_FOUND_ACTION = "/common/notFound";

	/**
	 * 栏目管理
	 */
	@Autowired
	protected DwpasCColumnInfoService columnInfoService;

	/**
	 * kpi信息
	 */
	@Autowired
	protected DwpasCKpiInfoService dwpasCKpiInfoService;

	/**
	 * 指标数据
	 */
	@Autowired
	protected DwpasStKpiDataService dwpasStKpiDataService;

	/**
	 * 产品管理
	 */
	@Autowired
	protected DwpasCPrdInfoService dwpasCPrdInfoService;

	/**
	 * 瞭望塔kpi数据
	 */
	@Autowired
	protected DwmisKpiDataService dwmisKpiDataService;
	/**
	 * 瞭望塔kpi信息
	 */
	@Autowired
	protected DwmisKpiInfoService dwmisKpiInfoService;

	/**
	 * 日志信息
	 */
	@Autowired
	protected SysLogService sysLogService;

	/**
	 * 通用指标关联
	 */
	@Autowired
	protected DwpasCComKpiInfoService comKpiInfoService;

	// 产品选择
	@Autowired
	private ProdInfoService prodInfoService;

	// // 报表彩票彩种
	// @Autowired
	// private LotTradNumService lotTradNumService;

	/**
	 * 得到查询的产品ID
	 * 
	 * @return
	 */
	public String getCrtProductIdOfReport(HttpServletRequest request) {
		Object productId = request.getSession().getAttribute(
				Constants.REPORT_REPORT_QUERY_PRODUCT_ID);
		Object crtProdInfo = request.getSession().getAttribute("productInfo");
		if (productId == null
				|| !StringUtils.notNullAndSpace(productId.toString())) {
			// 取默认产品ID
			List<DwpasCPrdInfo> prdInfoList = this.prodInfoService
					.getAllProducts(this.getCrtUserTemplateId(request));
			if (prdInfoList != null && !prdInfoList.isEmpty()) {
				// 默认取模板关联的产品列表的第一个
				request.getSession().setAttribute(
						Constants.REPORT_REPORT_QUERY_PRODUCT_ID,
						prdInfoList.get(0).getProductId());
				request.getSession().setAttribute("productInfo",
						prdInfoList.get(0));
				crtProdInfo = prdInfoList.get(0);
				request.getSession().setAttribute("productInfo", crtProdInfo);
				return prdInfoList.get(0).getProductId();
			} else {
				this.logger.error("当前模板未关联任何产品");
				return null;
			}
		}
		return productId.toString();
	}

	/**
	 * 查询的产品ID
	 * 
	 * @return
	 */
	public void setCrtProductIdOfReport(HttpServletRequest request,
			final String productId) {
		if (productId == null || productId.length() == 0) {
			this.logger.error("当前产品ID为空:" + productId);
			return;
		}
		DwpasCPrdInfo productInfo = this.dwpasCPrdInfoService
				.getDwpasCPrdInfoByProductId(productId);
		if (productInfo != null) {
			request.getSession().setAttribute("productInfo", productInfo);
		}
		request.getSession().setAttribute(
				Constants.REPORT_REPORT_QUERY_PRODUCT_ID, productId);
	}

	/**
	 * 得到当前报表查询日期（返回格式为yyyy-MM-dd）
	 * 
	 * @param request
	 * @return
	 */
	public String getCrtQueryDateOfReport(HttpServletRequest request) {
		Object queryDate = request.getSession().getAttribute(
				Constants.PORTAL_REPORT_QUERY_DATE);
		if (queryDate == null || queryDate.toString().length() == 0) {
			Object isDemoVersion = request.getSession().getServletContext()
					.getInitParameter("isDemoVersion");
			if (isDemoVersion != null
					&& "1".equalsIgnoreCase(isDemoVersion.toString())) {
				// 是否演示版本，设置默认时间
				return "2011-11-11";
			} else {
				// 默认取昨天
				Date defaultDate = DateUtils.getPreviousDate(new Date());
				this.setCrtQueryDateOfReport(request,
						DateUtils.formatByFormatRule(defaultDate, "yyyy-MM-dd"));
				return DateUtils.formatByFormatRule(defaultDate, "yyyy-MM-dd");
			}
		}
		if (queryDate.toString().indexOf("-") == -1) {
			return DateUtils.formatByFormatRule(DateUtils.parseByFormatRule(
					queryDate.toString(), "yyyyMMdd"), "yyyy-MM-dd");
		}
		request.getSession().setAttribute("date", queryDate.toString());
		return queryDate.toString();
	}

	/**
	 * 设置当前报表查询日期（传入格式为yyyy-MM-dd）
	 * 
	 * @param request
	 * @return
	 */
	public void setCrtQueryDateOfReport(HttpServletRequest request,
			String queryDate) {
		if (queryDate == null || queryDate.length() == 0) {
			this.logger.error("设置当前报表查询日期:" + queryDate);
			return;
		}
		if (queryDate.indexOf("-") == -1) {
			request.getSession().setAttribute(
					Constants.PORTAL_REPORT_QUERY_DATE,
					DateUtils.formatByFormatRule(
							DateUtils.parseByFormatRule(queryDate, "yyyyMMdd"),
							"yyyy-MM-dd"));
		} else {
			request.getSession().setAttribute(
					Constants.PORTAL_REPORT_QUERY_DATE, queryDate);
		}
		//
		this.setCrtQueryMonthOfReport(request, DateUtils.formatByFormatRule(
				DateUtils.parseByFormatRule(
						request.getSession()
								.getAttribute(
										Constants.PORTAL_REPORT_QUERY_DATE)
								.toString(), "yyyy-MM-dd"), "yyyy-MM"));
		request.getSession().setAttribute("date", queryDate);
	}

	/**
	 * 得到当前报表查询月份(返回的格式为yyyy-MM)
	 * 
	 * @param request
	 * @return
	 */
	public String getCrtQueryMonthOfReport(HttpServletRequest request) {
		Object queryMonth = request.getSession().getAttribute(
				Constants.REPORT_REPORT_QUERY_MONTH);
		this.logger.info("------------>得到当前报表查询月份："
				+ (queryMonth == null ? "null" : queryMonth.toString()));
		if (queryMonth == null || queryMonth.toString().length() == 0) {
			this.logger.info("取默认日期的月份。、。。。");
			String crtDate = this.getCrtQueryDateOfReport(request);
			// 默认当前时间
			this.setCrtQueryMonthOfReport(request, DateUtils
					.formatByFormatRule(
							DateUtils.parseByFormatRule(crtDate, "yyyy-MM-dd"),
							"yyyy-MM"));
			return DateUtils.formatByFormatRule(
					DateUtils.parseByFormatRule(crtDate, "yyyy-MM-dd"),
					"yyyy-MM");
		}
		if (queryMonth.toString().indexOf("-") == -1) {
			return DateUtils.formatByFormatRule(DateUtils.parseByFormatRule(
					queryMonth.toString(), "yyyyMM"), "yyyy-MM");
		}
		return queryMonth.toString();
	}

	/**
	 * 设置报表查询月份(传入格式为yyyy-MM)
	 * 
	 * @return
	 */
	public void setCrtQueryMonthOfReport(HttpServletRequest request,
			final String queryMonth) {
		if (queryMonth == null || queryMonth.length() == 0) {
			this.logger.error("设置报表查询月份:" + queryMonth);
			return;
		}
		if (queryMonth.indexOf("-") == -1) {
			request.getSession().setAttribute(
					Constants.REPORT_REPORT_QUERY_MONTH,
					DateUtils.formatByFormatRule(
							DateUtils.parseByFormatRule(queryMonth, "yyyyMM"),
							"yyyy-MM"));
		} else {
			request.getSession().setAttribute(
					Constants.REPORT_REPORT_QUERY_MONTH, queryMonth);
		}
		// 当前月
		Date crtMonth = DateUtils.parseByFormatRule(request.getSession()
				.getAttribute(Constants.REPORT_REPORT_QUERY_MONTH).toString(),
				"yyyy-MM");
		// 当前日
		if (request.getSession().getAttribute(
				Constants.PORTAL_REPORT_QUERY_DATE) == null) {
			request.getSession().setAttribute(
					Constants.PORTAL_REPORT_QUERY_DATE,
					this.getCrtQueryDateOfReport(request));
		}
		Date crtDate = DateUtils.parseByFormatRule(request.getSession()
				.getAttribute(Constants.PORTAL_REPORT_QUERY_DATE).toString(),
				"yyyy-MM-dd");
		if (!DateUtils.formatByFormatRule(crtMonth, "yyyy-MM").equals(
				DateUtils.formatByFormatRule(crtDate, "yyyy-MM"))) {
			// 重新设置日期为查询月的第一天
			this.setCrtQueryDateOfReport(request, queryMonth + "-01");
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
	 * 返回信息到客户端，并关闭窗口
	 * 
	 * @param showMsg
	 *            String
	 * @throws Exception
	 */
	public void sendMsgToClientAndCloseWindow(HttpServletResponse response) {
		sendMsgToClientAndCloseWindow(true, response);
	}

	/**
	 * 返回信息到客户端，并关闭窗口
	 * 
	 * @param showMsg
	 *            String
	 * @throws Exception
	 */
	public void sendMsgToClientAndCloseWindow(boolean returnFlag,
			HttpServletResponse response) {
		StringBuffer returnVal = new StringBuffer();
		returnVal.append("<script language='javascript'>\n");
		if (returnFlag) {
			returnVal.append("window.returnValue=true;\n");
		} else {
			returnVal.append("window.returnValue=false;\n");
		}
		returnVal.append("top.close();\n");
		returnVal.append("</script>");
		sendMsgToClient(returnVal.toString(), response);
	}

	/**
	 * 返回信息到客户端，并关闭窗口
	 * 
	 * @param showMsg
	 *            String
	 * @throws Exception
	 */
	public void sendMsgToClientAndCloseWindow(String returnMessage,
			HttpServletResponse response) {
		StringBuffer returnVal = new StringBuffer();
		returnVal.append("<script language='javascript'>\n");
		if (returnMessage != null) {
			returnVal.append("window.returnValue='").append(returnMessage)
					.append("';\n");
		}
		returnVal.append("window.close();\n");
		returnVal.append("</script>");
		sendMsgToClient(returnVal.toString(), response);
	}

	/**
	 * 返回信息到客户端，并关闭窗口
	 * 
	 * @param showMsg
	 *            String
	 * @throws Exception
	 */
	public void sendMsgToClient(Object showMsg, HttpServletResponse response) {
		this.sendMsgToClient(String.valueOf(showMsg), response);
	}

	/**
	 * 返回信息到客户端，并关闭窗口
	 * 
	 * @param showMsg
	 *            String
	 * @throws Exception
	 */
	public void sendMsgToClient(boolean showMsg, HttpServletResponse response) {
		this.sendMsgToClient(String.valueOf(showMsg), response);
	}

	/**
	 * 获得当前用户
	 * 
	 * @param session
	 * @return
	 */
	protected User getCrtUser(HttpSession session) {
		Object crtUser = session.getAttribute(Constants.SESSION_USER_INFO);
		if (crtUser != null) {
			return (User) crtUser;
		}
		return null;
	}

	/**
	 * 得到用户默认模板
	 * 
	 * @param request
	 * @return
	 */
	protected String getCrtUserTemplateId(HttpServletRequest request) {
		this.logger.info("取当前用户默认经纬仪模板ID");
		Object userTemplateId = request.getSession().getAttribute(
				Const.SESSION_DWPAS_TEMPLATEID);
		if (userTemplateId == null) {
			User user = this.getCrtUser(request.getSession());
			if (user != null && !user.getDwpasRights().equals("0")) {
				userTemplateId = user.getDwpasRights().split(",")[0];
			} else if (user != null
					&& !user.getRole().getDwpasRights().equals("0")) {
				userTemplateId = user.getRole().getDwpasRights().split(",")[0];
			}
		}
		return userTemplateId == null ? "" : String.valueOf(userTemplateId);
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
		Object crtUser = request.getSession().getAttribute(
				Constants.SESSION_USER_INFO);
		if (crtUser != null) {
			User user = (User) crtUser;
			String user_ip = getClientIp(request);
			sysLogService.insertLog(user, opContent, user_ip);
		}
	}

	/**
	 * 清除本地缓存
	 * 
	 * @param response
	 */
	protected void clearCache(HttpServletResponse response) {
		response.setHeader("Pragma", "cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "cache");
	}

	/**
	 * 设置瞭望塔的日期
	 * 
	 * @param request
	 * @return
	 */
	public String getCrtQueryDateOfDwmis(HttpServletRequest request) {
		Object queryDate = request.getSession().getAttribute(
				Constants.PORTAL_REPORT_QUERY_DATE);
		if (queryDate == null || queryDate.toString().length() == 0) {
			Object isDemoVersion = request.getSession().getServletContext()
					.getInitParameter("isDemoVersion");
			if (isDemoVersion != null
					&& "1".equalsIgnoreCase(isDemoVersion.toString())) {
				// 是否演示版本，设置默认时间
				return "2011-11-11";
			} else {
				// 默认取昨天
				Date defaultDate = DateUtils.getPreviousDate(new Date());
				this.setCrtQueryDateOfReport(request,
						DateUtils.formatByFormatRule(defaultDate, "yyyy-MM-dd"));
				return DateUtils.formatByFormatRule(defaultDate, "yyyy-MM-dd");
			}
		}
		if (queryDate.toString().indexOf("-") == -1) {
			return DateUtils.formatByFormatRule(DateUtils.parseByFormatRule(
					queryDate.toString(), "yyyyMMdd"), "yyyy-MM-dd");
		}
		request.getSession().setAttribute("date", queryDate.toString());
		return queryDate.toString();
	}

	/**
	 * 设置瞭望塔的日期（传入格式为yyyy-MM-dd）
	 * 
	 * @param request
	 * @return
	 */
	public void setCrtQueryDateOfDwmis(HttpServletRequest request,
			String queryDate) {
		if (queryDate == null || queryDate.length() == 0) {
			this.logger.error("设置当前报表查询日期:" + queryDate);
			return;
		}
		if (queryDate.indexOf("-") == -1) {
			request.getSession().setAttribute(
					Constants.PORTAL_REPORT_QUERY_DATE,
					DateUtils.formatByFormatRule(
							DateUtils.parseByFormatRule(queryDate, "yyyyMMdd"),
							"yyyy-MM-dd"));
		} else {
			request.getSession().setAttribute(
					Constants.PORTAL_REPORT_QUERY_DATE, queryDate);
		}
		//
		this.setCrtQueryMonthOfReport(request, DateUtils.formatByFormatRule(
				DateUtils.parseByFormatRule(
						request.getSession()
								.getAttribute(
										Constants.PORTAL_REPORT_QUERY_DATE)
								.toString(), "yyyy-MM-dd"), "yyyy-MM"));
		request.getSession().setAttribute("date", queryDate);
	}
}
