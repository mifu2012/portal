package com.infosmart.portal.action.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsDateJsonBeanProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.portal.action.BaseController;
import com.infosmart.portal.pojo.Page;
import com.infosmart.portal.pojo.Role;
import com.infosmart.portal.pojo.User;
import com.infosmart.portal.pojo.report.DimensionDetail;
import com.infosmart.portal.pojo.report.ReportChartFiled;
import com.infosmart.portal.pojo.report.ReportConfig;
import com.infosmart.portal.pojo.report.ReportDesign;
import com.infosmart.portal.pojo.report.SelfApply;
import com.infosmart.portal.pojo.report.SelfReport;
import com.infosmart.portal.service.report.ReportChartService;
import com.infosmart.portal.service.report.SelfApplyService;
import com.infosmart.portal.service.report.SelfReportService;
import com.infosmart.portal.util.Const;
import com.infosmart.portal.util.StringDes;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.dwpas.RightsHelper;

@Controller
@RequestMapping(value = "/selfReport")
public class SelfReportController extends BaseController {

	@Autowired
	private SelfReportService selfReportService;
	@Autowired
	private SelfApplyService selfApplyService;
	@Autowired
	private ReportChartService reportChartService;

	@RequestMapping(value = "{parentId}")
	public String reportMenuList(@PathVariable Integer parentId, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Model model) {
		/* 校验权限 */
		String roleRights = "";
		String userRights = "";
		String reportMenuName = request.getParameter("reportMenuName");
		if(StringUtils.notNullAndSpace(reportMenuName)){
			try {
				reportMenuName = new String(reportMenuName.getBytes("iso-8859-1"),"utf-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (session.getAttribute(Const.SESSION_ROLEREPORT_RIGHTS) == null
				|| session.getAttribute(Const.SESSION_USERREPORT_RIGHTS) == null) {
			User user = this.getCrtUser(session);
			user = selfReportService.getUserAndRoleById(user.getUserId());
			Role role = user.getRole();
			roleRights = role != null ? role.getRights() : "";
			userRights = user.getRights();
			// 避免每次拦截用户操作时查询数据库，以下将用户所属角色权限、用户权限都存入session
			session.setAttribute(Const.SESSION_ROLEREPORT_RIGHTS, roleRights); // 将角色权限存入session
			session.setAttribute(Const.SESSION_USERREPORT_RIGHTS, userRights); // 将用户权限存入session
		} else {
			roleRights = session.getAttribute(Const.SESSION_ROLEREPORT_RIGHTS)
					.toString();
			userRights = session.getAttribute(Const.SESSION_USERREPORT_RIGHTS)
					.toString();
		}

		List<SelfReport> reportMenuList = null;
		// 避免每次拦截用户操作时查询数据库，以下将用户所属报表菜单都存入session
		if (session.getAttribute(Const.SESSION_REPORT_MENU_LIST) == null) {
			reportMenuList = selfReportService.listAllParentReport();
			if (StringUtils.notNullAndSpace(userRights)
					|| StringUtils.notNullAndSpace(roleRights)) {
				reportChecked(reportMenuList, userRights, roleRights);
			}
			if (reportMenuList != null && reportMenuList.size() > 0)
				session.setAttribute(Const.SESSION_REPORT_MENU_LIST,
						reportMenuList);
		} else {
			reportMenuList = (List<SelfReport>) session
					.getAttribute(Const.SESSION_REPORT_MENU_LIST);
		}

		if (parentId == null && reportMenuList != null
				&& reportMenuList.size() > 0) {
			parentId = reportMenuList.get(0).getReportId();
		}
		List<SelfReport> reportList = selfReportService
				.listTreeReportByParentId(parentId);

		if (StringUtils.notNullAndSpace(userRights)
				|| StringUtils.notNullAndSpace(roleRights)) {
			reportChecked(reportList, userRights, roleRights);
		}
		if (reportList != null && reportList.size() > 0) {
			JSONArray arr = JSONArray.fromObject(reportList);
			String json = arr.toString();
			json = json.replaceAll("reportDesId", "id")
					.replaceAll("reportName", "name")
					.replaceAll("subReport", "nodes")
					.replaceAll("isReport", "isReport")
					.replaceAll("hasReport", "checked");
			model.addAttribute("zTreeNodes", json);
			model.addAttribute("reportMenuName", reportMenuName);
			return "newReport/selfReportMenu";
		} else {
			return "common/apply";
		}
	}

	/**
	 * 查询报表菜单
	 */
	// 递归选中报表权限
	private List<SelfReport> reportChecked(List<SelfReport> reportList,
			String userRights, String reportRights) {
		SelfReport report = null;
		if (reportList == null)
			return reportList;
		for (int i = 0; i < reportList.size(); i++) {
			report = reportList.get(i);
			report.setHasReport(RightsHelper.testRights(userRights,
					report.getReportId())
					|| RightsHelper.testRights(reportRights,
							report.getReportId()));
			if (report.isHasReport()) {
				reportChecked(report.getSubReport(), userRights, reportRights);
			} else {
				reportList.remove(i);
				i--;
			}
		}
		return reportList;
	}

	public Page returnPage(Page page) {
		return page;
	}

	/**
	 * 预览报表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	/*@RequestMapping(value = "/previewReport{reportId}")
	public ModelAndView previewReport(@PathVariable String reportId,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		if (StringUtils.notNullAndSpace(reportId)) {
			reportId = StringDes.StringToDec(reportId);
		}
		Map<Object, List<Object>> infoList = new HashMap<Object, List<Object>>();// 报表下拉参数
		ModelAndView mv = new ModelAndView("/selfReport/reportPreview");

		// 图表 zy
		List<ReportChartFiled> reportFieldList = reportChartService
				.getAllReportChart(Integer.parseInt(reportId));
		mv.addObject("reportFieldList", reportFieldList);

		ReportDesign selfReportConfig = this.selfReportService.getSelfReportConfigById(reportId);
		if (selfReportConfig == null) {
			this.logger.warn("没有找到该报表：" + reportId);
			return new ModelAndView(this.NOT_FOUND_PAGE);
		} else if (selfReportConfig.getColumnConfigList() == null
				|| selfReportConfig.getColumnConfigList().isEmpty()) {
			this.logger.warn("未定义报表SQL数据源或报表列");
			return new ModelAndView(this.NOT_FOUND_PAGE);
		}
		selfReportConfig.setReportId(new Integer(reportId));
		// 查询列
		List<ReportConfig> configList = this.selfReportService
				.queryConfigById(reportId);
		if (configList != null && !configList.isEmpty()) {
			for (int i = 0; i < configList.size(); i++) {
				if (configList.get(i).getControlType() == 6) {
					this.logger.info("维度类型:" + configList.get(i).getCode());
					if (configList.get(i) == null
							|| !StringUtils.notNullAndSpace(configList.get(i)
									.getCode()))
						continue;
					this.logger.info("----------->查询维度信息:"
							+ configList.get(i).getCode());
					try {
						// 维度型（下拉项）
						List<DimensionDetail> dimensionDetail = this.selfReportService
								.listDimensionDetail(Integer
										.parseInt(configList.get(i).getCode()));
						configList.get(i).setDimensionDetailList(
								dimensionDetail);
					} catch (Exception e) {
						e.printStackTrace();
						this.logger.warn("转整数数失败:"
								+ configList.get(i).getCode());
						continue;
					}
				}
			}
		}
		 查询条件 

		if (selfReportConfig != null) {
			this.logger.info("查询报表头");
			// 报表头
			List<ReportConfig> columnConfigList = selfReportConfig.getColumnConfigList();
			mv.addObject("columnConfigList", columnConfigList);
			
			 * // 不分页 if (columnConfigList != null &&
			 * selfReportConfig.getPageSize() == 0) { // 查询数据 List<List<String>>
			 * reportDataList = this.selfReportService
			 * .queryReportDataByConfig(selfReportConfig);
			 * mv.addObject("reportDataList", reportDataList); }
			 
		}
		User user = this.getCrtUser(session);
		SelfApply selfApply = new SelfApply();
		selfApply.setReportId(Integer.valueOf(reportId));
		selfApply.setUserId(user.getUserId());
		List<SelfApply> selfApplyList = selfApplyService
				.getSelfApplyById(selfApply);
		mv.addObject("selfApplyList", selfApplyList);
		mv.addObject("selfReportConfig", selfReportConfig);
		mv.addObject("configList", configList);
		mv.addObject("infoList", infoList);
		this.insertLog(request, "用户前台查看报表，报表ID为:" + reportId);
		return mv;
	}*/

	/**
	 * 异步加载分页查询的报表数据
	 * 
	 * @param request
	 * @param response
	 */
	/*@RequestMapping(value = "/loadReportDataByPaging")
	public void loadReportDataByPaging(HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.info("异步加载分页查询的报表数据");
		String reportId = request.getParameter("reportId");
		if (!StringUtils.notNullAndSpace(reportId)) {
			this.logger.warn("未找到报表信息:未知报表ID-->" + reportId);
			return;
		}
		ReportDesign selfReportConfig = this.selfReportService
				.getSelfReportConfigById(reportId);
		if (selfReportConfig == null) {
			this.logger.warn("未找到报表信息:" + reportId);
			return;
		}
		// 异步查询数据(翻页)
		String gtJson = request.getParameter("_gt_json");
		// this.logger.info("GT-JSON数据："+gtJson);
		// 当前页码
		int pageNo = 1;
		if (StringUtils.notNullAndSpace(gtJson)) {
			JSONObject gtJsonObject = (JSONObject) JSONSerializer
					.toJSON(gtJson);
			// 分页信息
			JSONObject pageJsonObject = (JSONObject) gtJsonObject
					.get("pageInfo");
			// 每页大小
			selfReportConfig.setPageSize(pageJsonObject.getInt("pageSize"));
			pageNo = pageJsonObject.getInt("pageNum");
			// 排序信息
			JSONArray sortJsonObject = (JSONArray) gtJsonObject
					.getJSONArray("sortInfo");
			if (sortJsonObject != null && sortJsonObject.size() > 0) {
				JSONObject orderJsonObject = null;
				for (int i = 0; i < sortJsonObject.size(); i++) {
					orderJsonObject = sortJsonObject.getJSONObject(i);
					if (orderJsonObject != null) {
						selfReportConfig.setOrderFieldName(orderJsonObject
								.getString("fieldName"));
						selfReportConfig.setSortOrder(orderJsonObject
								.getString("sortOrder"));
					}
				}
			}
			this.logger.info("排序字段:" + selfReportConfig.getOrderFieldName());
		}
		// 查询条件
		String queryWhere = request.getParameter("queryWhere");
		// request.getSession().setAttribute("varQueryWhere", queryWhere);
		this.logger.info("查询条件:" + queryWhere);
		if (StringUtils.notNullAndSpace(queryWhere)) {
			if (queryWhere.toLowerCase().trim().startsWith("and ")) {
				selfReportConfig.setQueryWhere(queryWhere);
			} else {
				selfReportConfig.setQueryWhere(" AND " + queryWhere);
			}
		}
		// 查询数据
		JSONObject reportData = this.selfReportService
				.queryReportJsonDataByPaging(selfReportConfig, pageNo);
		// 转为JSON数据
		JsDateJsonBeanProcessor beanProcessor = new JsDateJsonBeanProcessor();
		JsonConfig config = new JsonConfig();
		config.registerJsonBeanProcessor(java.sql.Date.class, beanProcessor);
		JSONObject jsonObj = JSONObject.fromObject(reportData, config);
		JSON json = JSONSerializer.toJSON(jsonObj, config);
		this.sendMsgToClient(json.toString(), response);
		this.insertLog(request, "前台用户查看报表，报表ID为：" + reportId);
	}*/

	/**
	 * 导出信息
	 */
	/*@RequestMapping(value = "/exportAllList")
	public ModelAndView exportAllList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap)
			throws ServletException, IOException {
		String reportId = request.getParameter("reportId");
		if (!StringUtils.notNullAndSpace(reportId)) {
			this.logger.error("报表ID为空，下载失败！");
			return null;
		}
		 下载时间 
		String exportDate = new java.sql.Date(new java.util.Date().getTime())
				.toString();
		// 获取要下载的数据

		ReportDesign reportInfo = selfReportService.getSelfReportById(reportId);
		ReportDesign selfReportConfig = this.selfReportService
				.getSelfReportConfigById(reportId);

		if (selfReportConfig == null) {
			this.logger.warn("未找到报表信息:" + reportId);
			return null;
		}
		// 异步查询数据(翻页)
		String gtJson = request.getParameter("_gt_json");
		// 当前页码
		int pageNo = 1;
		if (StringUtils.notNullAndSpace(gtJson)) {
			JSONObject gtJsonObject = (JSONObject) JSONSerializer
					.toJSON(gtJson);
			// 分页信息
			JSONObject pageJsonObject = (JSONObject) gtJsonObject
					.get("pageInfo");
			// 每页大小
			selfReportConfig.setPageSize(pageJsonObject.getInt("pageSize"));
			pageNo = pageJsonObject.getInt("pageNum");
			// 排序信息
			JSONArray sortJsonObject = (JSONArray) gtJsonObject
					.getJSONArray("sortInfo");
			if (sortJsonObject != null && sortJsonObject.size() > 0) {
				JSONObject orderJsonObject = null;
				for (int i = 0; i < sortJsonObject.size(); i++) {
					orderJsonObject = sortJsonObject.getJSONObject(i);
					if (orderJsonObject != null) {
						selfReportConfig.setOrderFieldName(orderJsonObject
								.getString("fieldName"));
						selfReportConfig.setSortOrder(orderJsonObject
								.getString("sortOrder"));
					}
				}
			}
		}
		// 查询条件
		String queryWhere = new String(request.getParameter("queryWhere")
				.getBytes("ISO-8859-1"), "UTF-8");
		if (StringUtils.notNullAndSpace(queryWhere)) {
			if (queryWhere.toLowerCase().trim().startsWith("and ")) {
				selfReportConfig.setQueryWhere(queryWhere);
			} else {
				selfReportConfig.setQueryWhere(" AND " + queryWhere);
			}
		}

		// 查询数据
		List<List<String>> reportDataList = this.selfReportService.queryReport(
				selfReportConfig, pageNo);
		 下载excel名称 
		String fileName = reportInfo.getReportName() + "-截止日期为：" + exportDate;
		if (!StringUtils.notNullAndSpace(fileName)) {
			fileName = "报表数据" + "-日期为：" + exportDate;
		}
		modelMap.put("fileName", fileName);
		List<String> excelTitleList = new ArrayList<String>();
		List<ReportConfig> queryColumnList = selfReportConfig
				.getColumnConfigList();
		for (ReportConfig column : queryColumnList) {
			if (column == null
					|| !StringUtils.notNullAndSpace(column.getColumnLabel()))
				continue;
			if (excelTitleList.toString().length() > 0) {
				excelTitleList.add(column.getColumnLabel());
			}
		}
		modelMap.put("excelTitleList", excelTitleList);
		if (reportDataList != null) {
			modelMap.put("reportDataList", reportDataList);
		}
		this.insertLog(request, "用户下载报表ID为：" + reportId + "的数据，下载文件名为："
				+ fileName);
		return new ModelAndView(new SelfExcelDownloadController(), modelMap);
	}*/

}
