package com.infosmart.portal.action.report;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

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
import com.infosmart.portal.pojo.report.DimensionDetailSec;
import com.infosmart.portal.pojo.report.GroupingField;
import com.infosmart.portal.pojo.report.ReportCell;
import com.infosmart.portal.pojo.report.ReportChartFiled;
import com.infosmart.portal.pojo.report.ReportConfig;
import com.infosmart.portal.pojo.report.ReportDataSource;
import com.infosmart.portal.pojo.report.ReportDefine;
import com.infosmart.portal.pojo.report.ReportDesign;
import com.infosmart.portal.pojo.report.SelfApply;
import com.infosmart.portal.pojo.report.SelfReport;
import com.infosmart.portal.service.report.NewReportService;
import com.infosmart.portal.service.report.ReportChartService;
import com.infosmart.portal.service.report.SelfApplyService;
import com.infosmart.portal.service.report.SelfReportService;
import com.infosmart.portal.util.Const;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.StringDes;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.dwpas.RightsHelper;

/**
 * 新报表设计器
 * 
 * @author infosmart
 * 
 */
@Controller
@RequestMapping(value = "/NewReport")
public class NewReportController extends BaseController {

	@Autowired
	private NewReportService newReportService;
	@Autowired
	private SelfReportService selfReportService;
	@Autowired
	private ReportChartService reportChartService;
	@Autowired
	private SelfApplyService selfApplyService;

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
			reportMenuName = reportMenuList.get(0).getReportName();
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
			return "newReport/reportMenu";
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
	 * @return
	 */
	@RequestMapping(value = "/previewReport")
	public ModelAndView previewReport(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		this.logger.info("预览报表");
		final long startTime = new java.util.Date().getTime();
		ModelAndView mv = new ModelAndView();
		String reportId = request.getParameter("reportId");
		if (StringUtils.notNullAndSpace(reportId)) {
			reportId = StringDes.StringToDec(reportId);
		}
		// 预览报表类型
		String type = request.getParameter("type");
		if (type != null && type.equals("drill")) {
			mv.addObject("title", "钻取报表");
		} else {
			mv.addObject("title", "报表预览");
		}
		String divHeight = request.getParameter("divHeight");
		mv.addObject("divHeight", divHeight);
		// 报表图表
		List<ReportChartFiled> reportFieldList = new ArrayList<ReportChartFiled>();
		if (reportId != null) {
			reportFieldList = reportChartService.getAllReportChart(Integer
					.valueOf(reportId));
		}
		mv.addObject("reportFieldList", reportFieldList);

		/* 获取查询条件 */
		List<ReportConfig> configList = this.getConfigList(reportId);
		ReportDesign reportDesign = this.newReportService
				.getReportDesignById(Integer.valueOf(reportId));
		// 得到定义的JSON数据
		String jsonData = reportDesign == null ? "" : reportDesign
				.getReportDefine();
		ReportDefine reportDefine = null;
		if (StringUtils.notNullAndSpace(jsonData)) {
			reportDefine = this.parseReportJsonData(jsonData);
		}
		if (reportDefine == null) {
			reportDefine = new ReportDefine();
			reportDefine.setReportId(Integer.valueOf(reportId));
			reportDefine.setReportName(reportDesign.getReportName());
		}
		mv.addObject("reportDefine", reportDefine);
		// 查询数据库类型
		if (reportDesign != null
				&& StringUtils.notNullAndSpace(reportDesign.getDataSourceId())) {
			ReportDataSource dataSource = this.newReportService
					.getDataSourceById(Integer.parseInt(reportDesign
							.getDataSourceId()));
			if (dataSource != null) {
				mv.addObject("dataBaseType", dataSource.getDbType());
			}
		}
		// 报表其他参数
		Map paramMap = request.getParameterMap();
		if (paramMap != null && !paramMap.isEmpty()) {
			// 用于下钻报表，数据值直接从URL中取,设置为默认值
			for (ReportConfig config : configList) {
				if (paramMap.containsKey(config.getColumnCode())) {
					if (paramMap.get(config.getColumnCode()) == null)
						continue;
					String[] paramData = (String[]) paramMap.get(config.getColumnCode());
					String[] paramData1 = (String[]) paramMap.get(config.getColumnCode());
					try {
						if (paramData.length != 0) {
							paramData[0] = new String(paramData[0].getBytes("iso-8859-1"), "utf-8");
							config.setDefaultValue(paramData[0]);
						}
						if (paramData1.length != 0) {
							paramData1[0] = new String(paramData1[0].getBytes("iso-8859-1"), "utf-8");
							config.setDefaultValueSec(paramData1[0]);
						}
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						this.logger.warn("转码出错了！");
					}
				}
			}
		}
		/* 自服务数据 */
		User user = this.getCrtUser(session);
		SelfApply selfApply = new SelfApply();
		selfApply.setReportId(Integer.valueOf(reportId));
		selfApply.setUserId(user.getUserId());
		List<SelfApply> selfApplyList = selfApplyService
				.getSelfApplyById(selfApply);
		mv.addObject("selfApplyList", selfApplyList);
		// 报表数据
		mv.addObject("reportDesign", reportDesign);
		mv.addObject("configList", configList);
		mv.setViewName("/newReport/reportPreview");
		this.insertLog(request, "预览报表");
		this.logger.info("-------->预览报表用时："
				+ (new java.util.Date().getTime() - startTime));
		return mv;
	}
	
	/**
	 * 查询表格数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/gridData")
	public ModelAndView gridData(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String reportId = request.getParameter("reportId");
		if (!StringUtils.notNullAndSpace(reportId)) {
			mv.addObject("exception", "未知报表ID");
			mv.setViewName(this.NOT_FOUND_ACTION);
			return mv;
		}
		reportId = StringDes.StringToDec(reportId);
		// 生成报表头
		List<ReportCell> reportCellList = new ArrayList<ReportCell>();
		// 二级表头
		List<ReportCell> secondLevelHeaderList = new ArrayList<ReportCell>();
		// 三级表头
		List<ReportCell> thirdLevelHeaderList = new ArrayList<ReportCell>();
		// 标题列
		List<ReportCell> mergeCellList = new ArrayList<ReportCell>();
		// 第一行表头
		List<ReportCell> firstRowList = new ArrayList<ReportCell>();
		ReportDesign reportDesign = this.newReportService
				.getReportDesignById(Integer.valueOf(reportId));
		// 得到定义的JSON数据
		String jsonData = reportDesign == null ? "" : reportDesign
				.getReportDefine();
		ReportDefine reportDefine = null;
		if (StringUtils.notNullAndSpace(jsonData)) {
			reportDefine = this.parseReportJsonData(jsonData);
			if (reportDefine != null
					&& reportDefine.getReportCellList() != null) {
				reportDefine.setReportId(Integer.valueOf(reportId));
				Map<String, Object> paramValMap = getParamVal(reportId, request);
				Map<String, List<String>> dynamicColumnsListMap = new HashMap<String, List<String>>();
				for (ReportCell reportCell : reportDefine.getReportCellList()) {
					if (reportCell.getRowNum() == 0) {
						//动态列
						if (reportCell.getDynamicColumnField() != null) {
							List<String> dynamicColumnsList = this.newReportService.
									getDynamicColumns(reportDesign, reportCell.getDynamicColumnField(), paramValMap);
							for (String dynamicColumn:dynamicColumnsList) {
								ReportCell tempCell = (ReportCell) reportCell.clone();
								tempCell.setContent(dynamicColumn);
								firstRowList.add(tempCell);
							}
						} else {
							firstRowList.add(reportCell);
						}
						//firstRowList.add(reportCell);
					}
					if (reportCell.getDataType() == 2) {
						// 二级表头
						// 动态列
						if (reportCell.getDynamicColumnField() != null) {
							List<String> dynamicColumnsList = this.newReportService.
									getDynamicColumns(reportDesign, reportCell.getDynamicColumnField(), paramValMap);
							for (String dynamicColumn:dynamicColumnsList) {
								ReportCell tempCell = (ReportCell) reportCell.clone();
								tempCell.setContent(dynamicColumn);
								//tempCell.setDataField(dynamicColumn+"_"+reportCell.getDataField());
								tempCell.setFirstChildCol(dynamicColumn+"_"+tempCell.getFirstChildCol());
								secondLevelHeaderList.add(tempCell);
								mergeCellList.add(tempCell);
							}
							dynamicColumnsListMap.put(reportCell.getDynamicColumnField(), dynamicColumnsList);
						} else {
							secondLevelHeaderList.add(reportCell);
							mergeCellList.add(reportCell);
						}
					} else if (reportCell.getDataType() == 3) {
						// 三级表头
						thirdLevelHeaderList.add(reportCell);
						mergeCellList.add(reportCell);
					} else {
						if (reportCell.getDynamicColumnField() != null) {
							// 动态列
							List<String> dynamicColumnsList = this.newReportService.
									getDynamicColumns(reportDesign, reportCell.getDynamicColumnField(), paramValMap);
							for (String dynamicColumn:dynamicColumnsList) {
								ReportCell tempCell = (ReportCell) reportCell.clone();
								tempCell.setContent(dynamicColumn);
								tempCell.setDataField(dynamicColumn+"_"+reportCell.getDataField());
								reportCellList.add(tempCell);
							}
						} else if (reportCell.getDynamicHeaderFieldList() != null) {
							String tempDataField = reportCell.getDataField();
							List<String> tempList = dynamicColumnsListMap.get(reportCell.getDynamicHeaderFieldList());
							for (int i = 0; i < tempList.size(); i++) {
								ReportCell tempCell = (ReportCell) reportCell.clone();
								tempCell.setDataField(tempList.get(i)+"_"+tempDataField);
								// 同一动态列下小于10条
								tempCell.setDynamicColNum(tempCell.getColNum()+i*10);
								reportCellList.add(tempCell);
							}
							/*for (int i = 0; i < dynamicColumnNum; i++) {
								tempCell.setDataField(tempDataField+"_"+i);
								reportCellList.add(tempCell);
							}*/
						} else {
							reportCellList.add(reportCell);
						}
					}
				}
			} else {
				this.logger.warn("解析JSON出错了");
			}
		} else {
			this.logger.warn("解析JSON出错了:未知JSON数据");
		}
		// 第一行表头
		mv.addObject("firstRowList", firstRowList);
		// 二级表头
		mv.addObject("secondLevelHeaderList", secondLevelHeaderList);
		// 三级
		mv.addObject("thirdLevelHeaderList", thirdLevelHeaderList);
		if (reportDefine == null) {
			reportDefine = new ReportDefine();
			reportDefine.setReportId(Integer.valueOf(reportId));
			reportDefine.setReportName(reportDesign.getReportName());
		}
		final List<ReportCell> reportCellList1 = mergeCellList;
		// 报表定义
		mv.addObject("reportDefine", reportDefine);
		// 一级报表列
		// 数据列排序
		Collections.sort(reportCellList, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				ReportCell cell1 = (ReportCell) o1;
				ReportCell cell2 = (ReportCell) o2;
				if (cell1.getRowNum() > cell2.getRowNum()) {
					cell1 = findParentCell(cell1, cell2, reportCellList1, 2);
					cell2 = findParentCell(cell2, cell1, reportCellList1, 1);
				} else if (cell1.getRowNum() < cell2.getRowNum()) {
					cell2 = findParentCell(cell2, cell1, reportCellList1, 2);
					cell1 = findParentCell(cell1, cell2, reportCellList1, 1);
				}
				if (cell1.getDynamicHeaderFieldList() != null
						&& cell2.getDynamicHeaderFieldList() != null) {
					if (cell1.getDynamicColNum() > cell2.getDynamicColNum()) {
						return 1;
					} else if (cell1.getDynamicColNum() < cell2.getDynamicColNum()) {
						return -1;
					}
				} else {
					if (cell1.getColNum() > cell2.getColNum()) {
						return 1;
					} else if (cell1.getColNum() < cell2.getColNum()) {
						return -1;
					}
				}
				return 0;
			}
		});
		// 反转
		// Collections.reverse(reportCellList);
		mv.addObject("reportCellList", reportCellList);
		// 报表数据
		mv.addObject("reportDesign", reportDesign);
		mv.setViewName("/newReport/gridData");
		return mv;
	}

	/**
	 * 异步加载分页查询的报表数据
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/loadReportDataByPaging")
	public void loadReportDataByPaging(HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.info("异步加载分页查询的报表数据");
		final long startTime = new java.util.Date().getTime();
		String reportId = request.getParameter("reportId");
		if (!StringUtils.notNullAndSpace(reportId)) {
			this.logger.warn("未找到报表信息:未知报表ID-->" + reportId);
			return;
		}
		/* 从json获取查询条件 */
		// ReportDesign selfReportConfig = null;
		// this.selfReportConfigService.getSelfReportConfigById(reportId);
		ReportDesign reportInfo = this.newReportService
				.getReportDesignById(Integer.valueOf(reportId));
		if (reportInfo == null) {
			this.logger.warn("未找到报表信息:" + reportId);
			return;
		}

		// 查询列
		// 生成报表头
		List<ReportCell> reportCellList = new ArrayList<ReportCell>();
		List<ReportCell> queryFieldList = new ArrayList<ReportCell>();
		String jsonData = reportInfo.getReportDefine();
		ReportDefine reportDefine = null;
		if (StringUtils.notNullAndSpace(jsonData)) {
			reportDefine = this.parseReportJsonData(jsonData);
			if (reportDefine != null
					&& reportDefine.getReportCellList() != null) {
				reportDefine.setReportId(Integer.valueOf(reportId));
				for (ReportCell reportCell : reportDefine.getReportCellList()) {
					if (reportCell.getColSpan() >= 2) {
						// 合并列
						// mergeCellList.add(reportCell);
					} else {
						reportCellList.add(reportCell);
					}
					if (reportCell.getDynamicColumnField() != null 
							&& reportCell.getDataField() == null) {
						queryFieldList.add(reportCell);
					}
				}
			} else {
				this.logger.warn("解析JSON出错了");
			}
		} else {
			this.logger.warn("解析JSON出错了:未知JSON数据");
		}
		final List<ReportCell> reportCellList1 = reportDefine
				.getReportCellList();
		// 报表定义
		// mv.addObject("reportDefine", reportDefine);
		// 数据列排序
		Collections.sort(reportCellList, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				ReportCell cell1 = (ReportCell) o1;
				ReportCell cell2 = (ReportCell) o2;
				if (cell1.getRowNum() > cell2.getRowNum()) {
					cell1 = findParentCell(cell1, cell2, reportCellList1, 2);
					cell2 = findParentCell(cell2, cell1, reportCellList1, 1);
				} else if (cell1.getRowNum() < cell2.getRowNum()) {
					cell2 = findParentCell(cell2, cell1, reportCellList1, 2);
					cell1 = findParentCell(cell1, cell2, reportCellList1, 1);
				}
				if (cell1.getColNum() > cell2.getColNum()) {
					return 1;
				} else if (cell1.getColNum() < cell2.getColNum()) {
					return -1;
				}
				return 0;
			}
		});
		this.logger.info("reportCellList==" + reportCellList);
		// 异步查询数据(翻页)
		String page = request.getParameter("page");// 当前页 get the requested page
		String limit = request.getParameter("rows");// 每页显示 get how many rows we
													// want to have into the
													// grid
		String sidx = request.getParameter("sidx"); // get index row - i.e. user
													// click to sort
		String sord = request.getParameter("sord");// 排序方式 get the direction

		// 当前页码
		int pageNo = 1;
		if (StringUtils.notNullAndSpace(page)
				&& StringUtils.notNullAndSpace(limit)) {
			reportInfo.setPageSize(Integer.valueOf(limit));
			pageNo = Integer.valueOf(page);
			reportInfo.setOrderFieldName(sidx);
			reportInfo.setSortOrder(sord);
			// 排序信息
			this.logger.info("排序字段:" + reportInfo.getOrderFieldName());
		} else {
			reportInfo.setPageSize(reportDefine.getPageSize());
		}
		// 查询条件
		Map<String, Object> paramValMap = getParamVal(reportId, request);
		if (queryFieldList.size() > 0) {
			reportCellList.addAll(queryFieldList);
		}
		// 查询数据
		JSONObject reportData = this.newReportService
				.queryReportJsonDataByPaging(reportInfo, reportCellList,
						pageNo, reportDefine.getGroupFieldList(), paramValMap);
		// 转为JSON数据
		this.logger.info("reportData=" + reportData.toString());
		this.sendMsgToClient(reportData.toString(), response);
		this.insertLog(request, "异步加载分页查询的报表数据");
		this.logger.info("-------->异步加载分页查询的报表数据用时："
				+ (new java.util.Date().getTime() - startTime));
	}
	
	/**
	 * 获取查询条件
	 * @param reportId
	 * @param request
	 * @return
	 */
	private Map<String, Object> getParamVal(String reportId,
			HttpServletRequest request) {
		// 查询条件
		List<ReportConfig> rptConfigList = this.getConfigList(reportId);
		Map<String, Object> paramValMap = new HashMap<String, Object>();
		if (rptConfigList != null && !rptConfigList.isEmpty()) {
			try {
				String paramVal = null;
				for (ReportConfig rptConfig : rptConfigList) {
					paramVal = request.getParameter(rptConfig.getColumnCode());
					this.logger.info("参数名：" + rptConfig.getColumnCode()
							+ "，参数值为:" + paramVal);
					if (StringUtils.notNullAndSpace(paramVal)) {
						// 处理中文乱码
						paramVal = new String(paramVal.getBytes("iso-8859-1"),
								"utf-8");
						paramVal = paramVal.trim();
						// 如果是日期型
						if (rptConfig.getControlType().intValue() == 3) {
							// 日期
							String dateFormat = "yyyy-MM-dd";
							if ("3".equals(rptConfig.getDateFormat())) {
								dateFormat = "yyyyMMdd";
							} else if ("1".equals(rptConfig.getDateFormat())) {
								dateFormat = "yyyy/MM/dd";
							}
							paramVal = DateUtils.formatUtilDate(
									DateUtils.parseStringToUtilDate(paramVal),
									dateFormat);
						} else if (rptConfig.getControlType() == 4) {
							// 月份
							String dateFormat = "yyyy-MM";
							if ("3".equals(rptConfig.getDateFormat())) {
								dateFormat = "yyyyMM";
							} else if ("1".equals(rptConfig.getDateFormat())) {
								dateFormat = "yyyy/MM";
							}
							paramVal = DateUtils.formatUtilDate(
									DateUtils.parseStringToUtilDate(paramVal),
									dateFormat);
						}
						this.logger.info("---------------->参数:"
								+ rptConfig.getColumnCode());
						this.logger.info("---------------->参数值:" + paramVal);
						paramValMap.put(rptConfig.getColumnCode(), paramVal);
					} else {
						this.logger.info("---------------->参数无值:"
								+ rptConfig.getColumnCode());
						paramValMap.put(rptConfig.getColumnCode(), null);
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				this.logger.warn("获取列表数据出错：", e);
			} catch (Exception e) {
				e.printStackTrace();
				this.logger.warn("获取列表数据出错：", e);
			}
		}
		return paramValMap;
	}

	/**
	 * 获取报表查询条件
	 * 
	 * @param reportId
	 * @return
	 */
	private List<ReportConfig> getConfigList(String reportId) {
		if (!StringUtils.notNullAndSpace(reportId)) {
			this.logger.warn("查询报表条件失败，未知的报表ID.");
			return null;
		}
		List<ReportConfig> configList = selfReportService
				.queryConfigById(reportId);
		if (configList != null && !configList.isEmpty()) {
			for (int i = 0; i < configList.size(); i++) {
				if (configList.get(i) == null
						|| !StringUtils.notNullAndSpace(configList.get(i)
								.getCode()))
					continue;
				if (configList.get(i).getControlType() == 6) {
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
				} else if (configList.get(i).getControlType() == 7) {
					try {
						// 维度型（下拉项）
						List<DimensionDetail> dimensionDetail = this.selfReportService
								.listDimensionDetail(Integer
										.parseInt(configList.get(i).getCode()));
						// 二级维度
						List<DimensionDetailSec> dimensionDetailSecList = selfReportService
								.getDimensionDetailSecList(configList.get(i)
										.getDefaultValue());

						configList.get(i).setDimensionDetailList(
								dimensionDetail);
						configList.get(i).setDimensionDetailSecList(
								dimensionDetailSecList);
					} catch (Exception e) {
						e.printStackTrace();
						this.logger.warn("转整数数失败:"
								+ configList.get(i).getCode());
						continue;
					}
				}
			}
		}
		return configList;
	}

	/**
	 * 获取二级维度数据
	 * 
	 * @param request
	 * @param response
	 * @param out
	 * @return
	 */
	@RequestMapping(value = "/getDimensionDetailSecList")
	public void getDimensionDetailSecList(HttpServletRequest request,
			HttpServletResponse response) {
		String parentId = request.getParameter("parentId");
		if (!StringUtils.notNullAndSpace(parentId)) {
			this.logger.warn("获取二级维度出错：parentId为空");
			return;
		}
		List<DimensionDetailSec> dimensionDetailSecList = selfReportService
				.getDimensionDetailSecList(parentId);
		this.sendJsonMsgToClient(dimensionDetailSecList, response);
	}

	/**
	 * 用于添加查询条件动态获取维度信息
	 * 
	 * @param request
	 * @param response
	 * @param out
	 * @return
	 */
	/*
	 * @RequestMapping(value = "/getDimensionDetailList") public String
	 * getDimensionDetailList(HttpServletRequest request, HttpServletResponse
	 * response, PrintWriter out) { Integer dimensionId = null; if
	 * ("".equals(request.getParameter("dimensionId")) ||
	 * request.getParameter("dimensionId") == null) { dimensionId = 0; } else {
	 * dimensionId = Integer.parseInt(request.getParameter("dimensionId")); }
	 * List<DimensionDetail> dimensionByCodeList = new
	 * ArrayList<DimensionDetail>(); dimensionByCodeList = dimensionalityService
	 * .getDimensionDetailList(dimensionId);
	 * this.sendJsonMsgToClient(dimensionByCodeList, response); return null; }
	 */

	/**
	 * 解析JSON数据
	 * 
	 * @param jsonData
	 * @return
	 */
	private ReportDefine parseReportJsonData(String jsonData) {
		JSONObject gtJsonObject = (JSONObject) JSONSerializer.toJSON(jsonData);
		// 属性类，用于解析属性类
		Map<String, Object> classMap = new HashMap<String, Object>();
		classMap.put("reportCellList", ReportCell.class);
		classMap.put("groupFieldList", GroupingField.class);
		ReportDefine reportDefine = (ReportDefine) JSONObject.toBean(
				gtJsonObject, ReportDefine.class, classMap);
		return reportDefine;

	}

	/**
	 * 导出信息
	 */
	@RequestMapping(value = "/exportAllList")
	public ModelAndView exportAllList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap)
			throws ServletException, IOException {
		String reportId = request.getParameter("reportId");
		this.logger.info("reportId=" + reportId);
		if (!StringUtils.notNullAndSpace(reportId)) {
			this.logger.error("报表ID为空，下载失败！");
			return null;
		}
		/* 下载时间 */
		String exportDate = new java.sql.Date(new java.util.Date().getTime())
				.toString();
		// 获取要下载的数据
		ReportDesign reportDesign = selfReportService
				.getSelfReportById(reportId);
		if (reportDesign == null) {
			this.logger.warn("未找到报表信息:" + reportId);
			return null;
		}
		// 查询列
		// 生成报表头
		List<ReportCell> reportCellList = new ArrayList<ReportCell>();
		// 合并列表头
		List<ReportCell> mergeCellList = new ArrayList<ReportCell>();
		String jsonData = reportDesign.getReportDefine();
		ReportDefine reportDefine = null;
		if (StringUtils.notNullAndSpace(jsonData)) {
			reportDefine = this.parseReportJsonData(jsonData);
			if (reportDefine != null
					&& reportDefine.getReportCellList() != null) {
				reportDefine.setReportId(Integer.valueOf(reportId));
				for (ReportCell reportCell : reportDefine.getReportCellList()) {
					if (reportCell.getColSpan() >= 2) {
						// 合并列
						mergeCellList.add(reportCell);
					} else {
						reportCellList.add(reportCell);
					}
				}
			} else {
				this.logger.warn("解析JSON出错了");
			}
		} else {
			this.logger.warn("解析JSON出错了:未知JSON数据");
		}
		this.logger.info("mergeCellList===" + mergeCellList);
		final List<ReportCell> reportCellList1 = mergeCellList;
		// 数据列排序
		Collections.sort(reportCellList, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				ReportCell cell1 = (ReportCell) o1;
				ReportCell cell2 = (ReportCell) o2;
				if (cell1.getRowNum() > cell2.getRowNum()) {
					cell1 = findParentCell(cell1, cell2, reportCellList1, 2);
					cell2 = findParentCell(cell2, cell1, reportCellList1, 1);
				} else if (cell1.getRowNum() < cell2.getRowNum()) {
					cell2 = findParentCell(cell2, cell1, reportCellList1, 2);
					cell1 = findParentCell(cell1, cell2, reportCellList1, 1);
				}
				if (cell1.getColNum() > cell2.getColNum()) {
					return 1;
				} else if (cell1.getColNum() < cell2.getColNum()) {
					return -1;
				}
				return 0;
			}
		});
		this.logger.info("reportCellList==" + reportCellList);

		// 异步查询数据(翻页)
		String page = request.getParameter("page");// 当前页 get the requested page
		String limit = request.getParameter("rows");// 每页显示 get how many rows we
													// want to have into the
													// grid
		String sidx = request.getParameter("sidx"); // get index row - i.e. user
													// click to sort
		String sord = request.getParameter("sord");// 排序方式 get the direction

		// 当前页码
		int pageNo = 1;
		if (StringUtils.notNullAndSpace(page)
				&& StringUtils.notNullAndSpace(limit)) {
			reportDesign.setPageSize(Integer.valueOf(limit));
			pageNo = Integer.valueOf(page);
			reportDesign.setOrderFieldName(sidx);
			reportDesign.setSortOrder(sord);
			// 排序信息
			this.logger.info("排序字段:" + reportDesign.getOrderFieldName());
		} else {
			this.logger.info("reportDefine.getPageSize()="
					+ reportDefine.getPageSize());
			reportDesign.setPageSize(reportDefine.getPageSize());
		}
		// 查询条件
		List<ReportConfig> rptConfigList = this.getConfigList(reportId);
		Map<String, Object> paramValMap = new HashMap<String, Object>();
		if (rptConfigList != null && !rptConfigList.isEmpty()) {
			try {
				String paramVal = null;
				for (ReportConfig rptConfig : rptConfigList) {
					paramVal = request.getParameter(rptConfig.getColumnCode());
					this.logger.info("参数名：" + rptConfig.getColumnCode()
							+ "，参数值为:" + paramVal);
					if (StringUtils.notNullAndSpace(paramVal)) {
						// 处理中文乱码
						paramVal = new String(paramVal.getBytes("iso-8859-1"),
								"utf-8");
						// 如果是日期型
						if (rptConfig.getControlType().intValue() == 3) {
							// 日期
							String dateFormat = "yyyy-MM-dd";
							if ("3".equals(rptConfig.getDateFormat())) {
								dateFormat = "yyyyMMdd";
							} else if ("1".equals(rptConfig.getDateFormat())) {
								dateFormat = "yyyy/MM/dd";
							}
							paramVal = DateUtils.formatUtilDate(
									DateUtils.parseStringToUtilDate(paramVal),
									dateFormat);
						} else if (rptConfig.getControlType() == 4) {
							// 月份
							String dateFormat = "yyyy-MM";
							if ("3".equals(rptConfig.getDateFormat())) {
								dateFormat = "yyyyMM";
							} else if ("1".equals(rptConfig.getDateFormat())) {
								dateFormat = "yyyy/MM";
							}
							paramVal = DateUtils.formatUtilDate(
									DateUtils.parseStringToUtilDate(paramVal),
									dateFormat);
						}
						this.logger.info("---------------->参数:"
								+ rptConfig.getColumnCode());
						this.logger.info("---------------->参数值:" + paramVal);
						paramValMap.put(rptConfig.getColumnCode(), paramVal);
					} else {
						this.logger.info("---------------->参数无值:"
								+ rptConfig.getColumnCode());
						paramValMap.put(rptConfig.getColumnCode(), null);
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				this.logger.warn("获取列表数据出错：", e);
			} catch (Exception e) {
				e.printStackTrace();
				this.logger.warn("获取列表数据出错：", e);
			}
		}
		// 查询数据
		// List<List<String>> reportDataList =
		// this.reportService.queryReport(reportDesign, pageNo,reportCellList);
		List<List<String>> reportDataList = this.newReportService.queryReport(
				reportDesign, pageNo, reportCellList, paramValMap);
		/* 下载excel名称 */
		String fileName = reportDesign.getReportName() + "-下载日期为：" + exportDate;
		if (!StringUtils.notNullAndSpace(fileName)) {
			fileName = "报表数据" + "-下载日期为：" + exportDate;
		}
		modelMap.put("fileName", fileName);
		List<String> excelTitleList = new ArrayList<String>();
		// List<SelfReportColumnConfig> queryColumnList =
		// reportDesign.getColumnConfigList();
		for (ReportCell reportCell : reportCellList) {
			if (reportCell == null)
				continue;
			if (excelTitleList.toString().length() > 0) {
				excelTitleList.add(reportCell.getContent());
			}
		}
		modelMap.put("excelTitleList", excelTitleList);
		modelMap.put("mergeCellList", mergeCellList);
		if (reportDataList != null) {
			modelMap.put("reportDataList", reportDataList);
		}
		this.insertLog(request, "用户下载报表ID为：" + reportId + "的数据，下载文件名为："
				+ fileName);
		return new ModelAndView(new SelfExcelDownloadController(), modelMap);
	}

	/**
	 * 导出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/exportReport")
	public ModelAndView exportReport(HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.info("----------------->导出报表");
		List<ReportCell> reportCellList = new ArrayList<ReportCell>();
		ModelAndView mv = new ModelAndView();
		// 报表设计JSON文件
		String reportId = request.getParameter("reportId");
		if (!StringUtils.notNullAndSpace(reportId)) {
			this.logger.warn("未知报表ID");
			mv.addObject("exception", "未知报表ID");
			mv.setViewName(this.NOT_FOUND_ACTION);
			return mv;
		} else {
			try {
				Integer.valueOf(reportId);
			} catch (Exception e) {
				mv.addObject("exception", "未知报表ID");
				mv.setViewName(this.NOT_FOUND_ACTION);
				return mv;
			}
		}
		// 报表定义
		ReportDesign reportDesign = this.newReportService
				.getReportDesignById(Integer.valueOf(reportId));
		if (reportDesign == null) {
			mv.addObject("exception", "未知报表定义");
			mv.setViewName(this.NOT_FOUND_ACTION);
			return mv;
		}
		// 报表设计JSON文件
		String jsonData = reportDesign == null ? "" : reportDesign
				.getReportDefine();
		this.logger.info("-------->jsonData:" + jsonData);
		// 查询条件
		Map<String, Object> paramValMap = getParamVal(reportId, request);
		// 数据列
		List<ReportCell> fieldCellList = new ArrayList<ReportCell>();
		// 动态表头数组
		List<String> dynamicColumnsList = new ArrayList<String>();
		Map<String, List<String>> dynamicColumnsMap = new HashMap<String, List<String>>();
		//报表设计定义
		ReportDefine reportDefine = null;
		if (StringUtils.notNullAndSpace(jsonData)) {
			reportDefine = this.parseReportJsonData(jsonData);
			if (reportDefine != null
					&& reportDefine.getReportCellList() != null) {
				mv.addObject("filename", reportDefine.getReportName());
				// 判断是否拥有动态列
				if (reportDefine.getDynamicColNum() > 0) {
					for (ReportCell reportCell : reportDefine.getReportCellList()) {
						if (reportCell.getDynamicColumnField() != null) {
							// 动态列表头
							dynamicColumnsList = this.newReportService.
									getDynamicColumns(reportDesign, reportCell.getDynamicColumnField(), paramValMap);
							dynamicColumnsMap.put(reportCell.getDynamicColumnField(), dynamicColumnsList);
							for (String dynamicColumn:dynamicColumnsList) {
								ReportCell tempCell = (ReportCell) reportCell.clone();
								tempCell.setContent(dynamicColumn);
								reportCellList.add(tempCell);
							}
						} else if (reportCell.getDynamicHeaderFieldList() != null) {
							// 动态列值
							if (dynamicColumnsMap.get(reportCell.getDynamicHeaderFieldList()) != null) {
								for (int i = 0; i < dynamicColumnsMap.get(reportCell.getDynamicHeaderFieldList()).size(); i++) {
									ReportCell tempCell = (ReportCell) reportCell.clone();
									tempCell.setDynamicColNum(tempCell.getColNum()+i*100);
									reportCellList.add(tempCell);
								}
							}
						} else {
							reportCellList.add(reportCell);
						}
					}
				} else {
					reportCellList = reportDefine.getReportCellList();
				}
				for (ReportCell reportCell : reportDefine.getReportCellList()) {
					if (reportCell.getDataType() == 1 || reportCell.getDynamicColumnField() != null) {
						fieldCellList.add(reportCell);
					}
				}
			} else {
				this.logger.warn("解析JSON出错了");
			}
		} else {
			this.logger.warn("解析JSON出错了:未知JSON数据");
		}
		if (reportDefine.getDynamicColNum() > 0) {
			// 动态表头排序
			Collections.sort(reportCellList, new Comparator() {
				@Override
				public int compare(Object o1, Object o2) {
					ReportCell cell1 = (ReportCell) o1;
					ReportCell cell2 = (ReportCell) o2;
					if (cell1.getDynamicColNum() != -1 && cell2.getDynamicColNum() != -1) {
						if (cell1.getDynamicColNum() > cell2.getDynamicColNum()) {
							return 1;
						} else if (cell1.getDynamicColNum() < cell2.getDynamicColNum()) {
							return -1;
						}
					}
					return 0;
				}
			});
		}
		mv.addObject("reportCellList", reportCellList);
		final List<ReportCell> reportCellList1 = reportDefine.getReportCellList();
		// 数据列排序
		Collections.sort(fieldCellList, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				ReportCell cell1 = (ReportCell) o1;
				ReportCell cell2 = (ReportCell) o2;
				if (cell1.getRowNum() > cell2.getRowNum()) {
					cell1 = findParentCell(cell1, cell2, reportCellList1, 2);
					cell2 = findParentCell(cell2, cell1, reportCellList1, 1);
				} else if (cell1.getRowNum() < cell2.getRowNum()) {
					cell2 = findParentCell(cell2, cell1, reportCellList1, 2);
					cell1 = findParentCell(cell1, cell2, reportCellList1, 1);
				}
				if (cell1.getColNum() > cell2.getColNum()) {
					return 1;
				} else if (cell1.getColNum() < cell2.getColNum()) {
					return -1;
				}
				return 0;
			}
		});
		// 数据列
		mv.addObject("fieldCellList", fieldCellList);
		// 查询数据
		List<List<String>> reportDataList = this.newReportService.queryExportData(
				reportDesign, fieldCellList, paramValMap);
		List<ReportCell> fieldList = new ArrayList<ReportCell>();
		if (fieldCellList != null) {
			for (ReportCell reportCell : fieldCellList) {
				if (reportCell.getDataType() == 1) {
					fieldList.add(reportCell);
				}
			}
		}
		mv.addObject("fieldList", fieldList);
		mv.addObject("reportDataList", reportDataList);
		mv.setViewName("newReport/exportReport");
		return mv;
	}

	/**
	 * 自定义平台
	 * 
	 * @param reportId
	 * @return
	 */
	@RequestMapping(value = "/customPlatform")
	public ModelAndView customPlatform(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String reportId = request.getParameter("reportId");
		if (!StringUtils.notNullAndSpace(reportId)) {
			mv.addObject("exception", "未知报表ID");
			mv.setViewName(this.NOT_FOUND_ACTION);
			return mv;
		}
		// 生成报表头
		List<ReportCell> reportCellList = new ArrayList<ReportCell>();
		// 二级表头
		List<ReportCell> secondLevelHeaderList = new ArrayList<ReportCell>();
		// 三级表头
		List<ReportCell> thirdLevelHeaderList = new ArrayList<ReportCell>();
		ReportDesign reportDesign = this.newReportService
				.getReportDesignById(Integer.valueOf(reportId));
		// 得到定义的JSON数据
		String jsonData = reportDesign == null ? "" : reportDesign
				.getReportDefine();
		ReportDefine reportDefine = null;
		if (StringUtils.notNullAndSpace(jsonData)) {
			reportDefine = this.parseReportJsonData(jsonData);
			if (reportDefine != null
					&& reportDefine.getReportCellList() != null) {
				reportDefine.setReportId(Integer.valueOf(reportId));
				for (ReportCell reportCell : reportDefine.getReportCellList()) {
					if (reportCell.getDataType() == 2) {
						// 二级表头
						secondLevelHeaderList.add(reportCell);
					} else if (reportCell.getDataType() == 3) {
						// 三级表头
						thirdLevelHeaderList.add(reportCell);
					} else {
						reportCellList.add(reportCell);
					}
				}
			} else {
				this.logger.warn("解析JSON出错了");
			}
		} else {
			this.logger.warn("解析JSON出错了:未知JSON数据");
		}
		mv.addObject("reportDefine", reportDefine);
		mv.addObject("reportCellList", reportCellList);
		mv.addObject("secondLevelHeaderList", secondLevelHeaderList);
		mv.addObject("thirdLevelHeaderList", thirdLevelHeaderList);
		mv.setViewName("newReport/customPlatform");
		return mv;
	}

	/**
	 * 获取同级父节点
	 * 
	 * @param cell
	 * @param cellList
	 * @param i
	 * @return cell
	 */
	private ReportCell findParentCell(ReportCell cell, ReportCell cell2,
			List<ReportCell> cellList, int i) {
		// 如果已经是一级节点则返回
		if (cell.getpId().equals("1")) {
			return cell;
		}
		// 如果已经在同一层
		if (cell.getRowNum() == cell2.getRowNum()) {
			return cell;
		}
		// 查找父节点
		for (ReportCell tempCell : cellList) {
			if (tempCell.getId().equals(cell.getpId())) {
				cell = tempCell;
				i--;
				break;
			}
		}
		if (i != 0) {
			cell = findParentCell(cell, cell2, cellList, i);
		}
		return cell;
	}
}
