package com.infosmart.controller.report;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import com.infosmart.controller.BaseController;
import com.infosmart.po.Dimension;
import com.infosmart.po.DimensionDetail;
import com.infosmart.po.DimensionDetailSec;
import com.infosmart.po.report.GroupingField;
import com.infosmart.po.report.ReportCell;
import com.infosmart.po.report.ReportChartFiled;
import com.infosmart.po.report.ReportConfig;
import com.infosmart.po.report.ReportDataSource;
import com.infosmart.po.report.ReportDefine;
import com.infosmart.po.report.ReportDesign;
import com.infosmart.po.report.ReportField;
import com.infosmart.service.DimensionalityService;
import com.infosmart.service.report.NewReportService;
import com.infosmart.service.report.ReportChartService;
import com.infosmart.service.report.ReportConfigService;
import com.infosmart.service.report.ReportService;
import com.infosmart.util.DateUtils;
import com.infosmart.util.StringUtils;

/**
 * 新报表设计器----包含数据源配置、列表配置、查询条件配置、图表配置、预览功能
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
	private ReportService reportService;
	@Autowired
	private DimensionalityService dimensionalityService;

	@Autowired
	private ReportConfigService reportConfigService;
	@Autowired
	private ReportChartService reportChartService;

	/**
	 * 递归选中报表权限
	 *
	 * @param reportList
	 * @return
	 */
	private List<ReportDesign> reportChecked(List<ReportDesign> reportList) {
		for (ReportDesign report : reportList) {
			report.setHasReport((null != report.getSubReport() && report
					.getSubReport().size() > 0));
			if (null != report.getSubReport()
					&& report.getSubReport().size() > 0) {
				reportChecked(report.getSubReport());
			}
		}
		return reportList;
	}

	/**
	 * 查询报表菜单 infosmart date 2012-04-26
	 *
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/reportMenuList")
	public String list(HttpSession session, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		List<ReportDesign> reportList = reportService.listAllReport();
		List<ReportDesign> rptList = new ArrayList<ReportDesign>();
		if (reportList != null && reportList.size() > 0) {
			for (ReportDesign rptDesign : reportList) {
				if (rptDesign.getRptFlag500w() != 1) {
					rptList.add(rptDesign);
				}
			}
		}

		model.addAttribute("reportList", rptList);
		if (rptList != null && rptList.size() > 0) {
			reportChecked(rptList);
		}
		//

		JSONArray arr = JSONArray.fromObject(rptList);
		String json = arr.toString();
		json = json.replaceAll("reportId", "id")
				.replaceAll("reportName", "name")
				.replaceAll("subReport", "nodes")
				.replaceAll("hasReport", "checked");
		model.addAttribute("zTreeNodes", json);
		this.insertLog(request, "查询报表菜单");
		return "reportConfig/reportConfig";
	}

	/**
	 * 报表设计器 http://localhost:8080/portal//NewReport/designReportTemplate.html
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/designReportTemplate{reportId}")
	public ModelAndView designReportTemplate(@PathVariable Integer reportId,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		this.logger.info("----------------->转到报表设计器");
		if (reportId == null) {
			this.logger.warn("未知报表ID");
			mv.addObject("exception", "未知报表ID");
			mv.setViewName(this.NOT_FOUND_ACTION);
			return mv;
		} else {
			// 报表定义配置
			ReportDesign reportDesign = this.newReportService
					.getReportDesignById(reportId);
			mv.addObject("reportDesign", reportDesign);
			/* 查询条件 */
			List<ReportConfig> configList = this.getConfigList(reportId
					.toString());
			mv.addObject("configList", configList);
		}
		mv.setViewName("/newReport/designReport");
		this.insertLog(request, "转到报表设计器");
		return mv;
	}

	/**
	 * 报表查询 http://localhost:8080/portal//NewReport/searchConfig.html
	 *
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value = "/searchConfig")
	public ModelAndView searchConfigList(HttpServletRequest request,
			HttpServletResponse reponse) {
		ModelAndView mv = new ModelAndView();
		String reportId = request.getParameter("reportId");
		if (!StringUtils.notNullAndSpace(reportId)) {
			this.logger.warn("报表查询,报表ID为空-->");
			mv.addObject("exception", "未知报表ID");
			mv.setViewName(this.NOT_FOUND_ACTION);
			return mv;
		} else {
			List<ReportConfig> configList = this.getConfigList(reportId
					.toString());
			mv.addObject("configList", configList);
		}
		mv.setViewName("newReport/searchConfig");
		return mv;
	}

	/**
	 * 解析报表(报表保存数据源SQL及报表模板JSON数据)
	 * http://localhost:8080/portal//NewReport/parseReportTemplate.html
	 *
	 * @return
	 */
	@RequestMapping(value = "/parseReportTemplate")
	public ModelAndView parseReportTemplate(HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.info("----------------->报表解析器");
		List<ReportCell> reportCellList = null;
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
		// 报表设计定义
		ReportDefine reportDefine = null;
		if (StringUtils.notNullAndSpace(jsonData)) {
			reportDefine = this.parseReportJsonData(jsonData);
			if (reportDefine != null) {
				reportDefine.setReportId(reportDesign.getReportId());
				reportCellList = reportDefine.getReportCellList();
			}
			// 报表定义
			mv.addObject("reportDefine", reportDefine);
		} else {
			// 默认模板
			String querySql = reportDesign.getReportSql();
			List<ReportField> reportFieldList = this.newReportService
					.listReportField(querySql, reportDesign.getDataSourceId());
			if (reportFieldList != null && reportFieldList.size() > 0) {
				reportCellList = new ArrayList<ReportCell>();
				ReportCell reportCell = null;
				int colCount = 0;
				for (ReportField field : reportFieldList) {
					reportCell = new ReportCell();
					reportCell.setId(UUID.randomUUID().toString()
							.replace("-", ""));
					reportCell.setpId("1");
					// 默认第一行
					reportCell.setRowNum(0);
					reportCell.setColNum(colCount);
					reportCell.setColSpan(1);
					reportCell.setRowSpan(1);
					reportCell.setContent(field.getColumnCode());
					reportCell.setDataField(field.getColumnLabel());
					reportCell.setDataShowType(0);
					reportCell.setDataType(0);
					//
					reportCellList.add(reportCell);
					colCount++;
				}
			}
			reportDefine = new ReportDefine();
			reportDefine.setReportId(Integer.valueOf(reportId));
			reportDefine.setReportName(reportDesign.getReportName());
			reportDefine.setReportCellList(reportCellList);
			// 报表定义
			mv.addObject("reportDefine", reportDefine);
		}
		final List<ReportCell> reportCellList1 = reportCellList;
		// 数据列
		List<ReportCell> fieldCellList = new ArrayList<ReportCell>();
		for (ReportCell fieldCell : reportCellList) {
			if (fieldCell.getDataType() == 1) {
				fieldCellList.add(fieldCell);
			}
		}
		this.logger.info("数据列_个数:" + fieldCellList.size());
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
		// 反转
		// Collections.reverse(fieldCellList);
		// 数据列
		mv.addObject("reportCellList", reportCellList);
		mv.addObject("fieldCellList", fieldCellList);
		mv.setViewName("/newReport/parseReport");
		return mv;
	}

	/**
	 * 报表头设计器 http://localhost:8080/portal//NewReport/designReportHeader.html
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/designReportHeader")
	public ModelAndView designReportHeader(HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.info("----------------->转到报表表头设计器");
		ModelAndView mv = new ModelAndView();
		// 报表设计JSON文件
		String reportId = request.getParameter("reportId");
		if (!StringUtils.notNullAndSpace(reportId)) {
			mv.addObject("exception", "未知报表ID");
			mv.setViewName(this.NOT_FOUND_ACTION);
			return mv;
		}
		// 报表定义
		ReportDesign reportDesign = this.newReportService
				.getReportDesignById(Integer.valueOf(reportId));
		// 报表定义JSON数据
		String jsonData = reportDesign == null ? "" : reportDesign
				.getReportDefine();
		this.logger.info("-->jsonData:" + jsonData);
		ReportDefine reportDefine = null;
		if (StringUtils.notNullAndSpace(jsonData)) {
			reportDefine = this.parseReportJsonData(jsonData);
			if (reportDefine != null) {
				reportDefine.setReportId(Integer.valueOf(reportId));
			}
		}
		if (reportDefine == null) {
			reportDefine = new ReportDefine();
			reportDefine.setReportId(Integer.valueOf(reportId));
			reportDefine.setReportName(reportDesign.getReportName());
		}
		mv.addObject("reportDefine", reportDefine);
		// 报表数据列
		String querySql = reportDesign.getReportSql();
		List<ReportField> reportFieldList = this.newReportService
				.listReportField(querySql, reportDesign.getDataSourceId());
		mv.addObject("reportFieldList", reportFieldList);
		mv.setViewName("/newReport/designReportHeader");
		return mv;
	}

	/**
	 * 预览报表
	 *
	 * @return
	 */
	@RequestMapping(value = "/previewReport")
	public ModelAndView previewReport(HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.info("预览报表");
		long begin = new Date().getTime();
		ModelAndView mv = new ModelAndView();
		String reportId = request.getParameter("reportId");
		if (!StringUtils.notNullAndSpace(reportId)) {
			mv.addObject("exception", "未知报表ID");
			mv.setViewName(this.NOT_FOUND_ACTION);
			return mv;
		}
		// 预览报表类型
		String type = request.getParameter("type");
		if (type != null && type.equals("drill")) {
			mv.addObject("title", "钻取报表");
		} else {
			mv.addObject("title", "报表预览");
		}
		// 报表图表
		List<ReportChartFiled> reportFieldList = new ArrayList<ReportChartFiled>();
		if (reportId != null) {
			reportFieldList = reportChartService.getAllReportChart(Integer
					.parseInt(reportId));
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
		mv.addObject("reportDefine", reportDefine);
		// 查询数据库类型
		if (reportDesign != null
				&& StringUtils.notNullAndSpace(reportDesign.getDataSourceId())) {
			ReportDataSource dataSource = this.newReportService
					.getDataSourceById(Integer.parseInt(reportDesign
							.getDataSourceId()));
			request.setAttribute("dataBaseType",
					dataSource == null ? ReportDataSource.DB_TYPE_MYSQL
							: dataSource.getDbType());
		} else {
			this.logger.info("未知数据库类型,默认为MYSQL");
			request.setAttribute("dataBaseType", ReportDataSource.DB_TYPE_MYSQL);
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
		// 报表数据
		mv.addObject("reportDesign", reportDesign);
		mv.addObject("configList", configList);
		mv.setViewName("/newReport/reportPreview");
		this.insertLog(request, "预览报表");
		this.logger.info("预览报表--------------------->"
				+ (new Date().getTime() - begin));
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
						//数据列
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
								tempCell.setpDataField(tempList.get(i));
								tempCell.setDataField(tempList.get(i)+"_"+tempDataField);
								// 同一动态列下小于100条
								tempCell.setDynamicColNum(tempCell.getColNum()+i*100);
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
		long begin = new Date().getTime();
		String reportId = request.getParameter("reportId");
		if (!StringUtils.notNullAndSpace(reportId)) {
			this.logger.warn("未找到报表信息:未知报表ID-->" + reportId);
			// this.sendMsgToClient(new JSONObject().toString(), response);
			return;
		}
		/* 从json获取查询条件 */
		// ReportDesign selfReportConfig = null;
		// this.selfReportConfigService.getSelfReportConfigById(reportId);
		ReportDesign reportInfo = this.newReportService
				.getReportDesignById(Integer.valueOf(reportId));
		if (reportInfo == null) {
			this.logger.warn("未找到报表信息:" + reportId);
			// this.sendMsgToClient(new JSONObject().toString(), response);
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
		this.logger.info("异步加载分页查询的报表数据--------------------->"
				+ (new Date().getTime() - begin));
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
	 * 保存报表定义数据
	 */
	@RequestMapping(value = "/updateReportDesign")
	public ModelAndView updateReportDesign(HttpServletRequest request,
			HttpServletResponse response, ReportDesign reportDesign) {
		boolean isSuccess = true;
		try {
			this.newReportService.updateReportDesign(reportDesign);
		} catch (Exception e) {
			isSuccess = false;
			this.logger.error(e.getMessage(), e);
		}
		ModelAndView mv = new ModelAndView(this.SUCCESS_ACTION);
		mv.addObject("msg", isSuccess ? this.isSuccess : this.isFailed);
		return mv;
	}

	/**
	 * 报表查询列配置列表
	 *
	 * @param reportId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/reportSearch{reportId}")
	public ModelAndView reportSearchList(@PathVariable Integer reportId,
			ReportConfig reportConfig, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		if (reportId == null) {
			this.logger.warn("查询报表查询列失败,报表ID未知-->");
			mv.addObject("exception", "未知报表ID");
			mv.setViewName(this.NOT_FOUND_ACTION);
			return mv;
		}
		List<ReportConfig> reportReachList = this.reportService
				.listPageConfigById(reportId.toString());
		/* 判断是否维度类型 */
		for (ReportConfig config : reportReachList) {
			if (config.getControlType() == 6) {
				if (StringUtils.notNullAndSpace(config.getDefaultValue())
						&& config.getDefaultValue().trim().length() > 0) {
					this.logger.info("---->默认值:[" + config.getDefaultValue()
							+ "]");
					DimensionDetail dimensionDetail = dimensionalityService
							.getDimensionDetaiById(Integer.valueOf(config
									.getDefaultValue()));
					if (dimensionDetail != null)
						config.setCodeName(dimensionDetail.getValue());

				}
			} else if (config.getControlType() == 7) {
				if (StringUtils.notNullAndSpace(config.getDefaultValue())
						&& config.getDefaultValue().trim().length() > 0) {
					this.logger.info("---->默认值:[" + config.getDefaultValue()
							+ "]");
					DimensionDetail dimensionDetail = dimensionalityService
							.getDimensionDetaiById(Integer.valueOf(config
									.getDefaultValue()));
					// 二级维度对象
					DimensionDetailSec dimensionDetailSec = dimensionalityService
							.getDimensionDetailSecById(!StringUtils
									.notNullAndSpace(config
											.getDefaultValueSec()) ? 0
									: Integer.parseInt(config
											.getDefaultValueSec()));

					if (dimensionDetail != null)
						config.setCodeName(dimensionDetail.getValue());
					if (dimensionDetailSec != null) {
						config.setCodeNameSec(dimensionDetailSec.getValue());
					}
				}
			}
		}
		mv.setViewName("newReport/reportSearch");
		mv.addObject("reportId", reportId);
		mv.addObject("reportConfig", reportConfig);
		mv.addObject("reportReachList", reportReachList);
		return mv;
	}

	/**
	 * 请求新增页面 添加控件 hgt date 2012-04-26
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add{reportId}")
	public String toAdd(@PathVariable Integer reportId, Model model) {
		this.logger.info("reportId==" + reportId);
		// 查询字段
		ReportDesign reportDesign = this.newReportService
				.getReportDesignById(Integer.valueOf(reportId));
		String querySql = reportDesign.getReportSql();
		List<ReportField> reportFieldList = this.newReportService
				.listReportField(querySql, reportDesign.getDataSourceId());
		// 查询维度
		List<Dimension> dimensionList = dimensionalityService
				.getDimensionList();
		model.addAttribute("reportId", reportId);
		model.addAttribute("dimensionList", dimensionList);
		model.addAttribute("reportFieldList", reportFieldList);
		return "newReport/add_info";
	}

	/**
	 * 编辑 查询项 infosmart date 2012-04-26
	 *
	 * @param reportId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit{configId}-{reportId}")
	public String toEdit(@PathVariable Integer configId,
			@PathVariable Integer reportId, HttpServletRequest req, Model model) {
		if (configId == null || reportId == null) {
			this.logger.warn("编辑select时传递的ID为null");
			ModelAndView mv = new ModelAndView();
			mv.addObject("exception", "编辑select时传递的ID为null");
			mv.setViewName(this.NOT_FOUND_ACTION);
			return mv.getViewName();
		}
		ReportConfig config = new ReportConfig();
		Dimension dimensionById = new Dimension();
		List<DimensionDetail> dimensionDetailList = null;
		List<DimensionDetailSec> dimensionDetailSecList = null;
		/**
		 * // 报表查询字段 ReportDesign reportDesign = this.newReportService
		 * .getReportDesignById(Integer.valueOf(reportId)); String querySql =
		 * reportDesign.getReportSql(); List<ReportField> reportFieldList =
		 * this.newReportService .listReportField(querySql,
		 * reportDesign.getDataSourceId());
		 * model.addAttribute("reportFieldList", reportFieldList);
		 */
		// 维度
		List<Dimension> dimensionList = dimensionalityService
				.getDimensionList();
		config = reportConfigService.getReportConfigById(configId);
		if ((config.getCode() != null) && (!"".equals(config.getCode().trim()))) {
			dimensionById = dimensionalityService.getDimensionById(Integer
					.parseInt(config.getCode()));
			dimensionDetailList = dimensionalityService
					.getDimensionDetailList(Integer.parseInt(config.getCode()));
			// 根据一级维度defaultValue获取所有子二级维度
			dimensionDetailSecList = dimensionalityService
					.getDimensionDetailSecList(config.getDefaultValue());

			model.addAttribute("dimensionDetailList", dimensionDetailList);
			model.addAttribute("dimensionDetailSecList", dimensionDetailSecList);
			model.addAttribute("dimensionById", dimensionById);
		}

		model.addAttribute("dimensionList", dimensionList);
		// model.addAttribute("reportFieldList", reportFieldList);
		model.addAttribute("config", config);
		model.addAttribute("configId", configId);
		this.insertLog(req, "编辑 查询项");
		return "newReport/edit_info";
	}

	/**
	 * 删除 infoamsrt date 2012-04-26
	 *
	 * @param res
	 * @param configId
	 */
	@RequestMapping(value = "/del{configId}")
	public void delete(@PathVariable Integer configId, HttpServletRequest req,
			HttpServletResponse res) {
		if (configId == null) {
			this.logger.warn("删除时传递的configId为null");
			return;
		}
		try {
			reportConfigService.deleteReportConfigById(configId);
			this.sendMsgToClient(isSuccess, res);
		} catch (Exception e) {
			this.sendMsgToClient(isFailed, res);
			this.logger.error(e.getMessage(), e);
		}
		this.insertLog(req, "删除报表配置，ID为：" + configId);
	}

	/**
	 * 用于添加查询条件动态获取维度信息
	 *
	 * @param request
	 * @param response
	 * @param out
	 * @return
	 */
	@RequestMapping(value = "/getDimensionDetailList")
	public String getDimensionDetailList(HttpServletRequest request,
			HttpServletResponse response, PrintWriter out) {
		Integer dimensionId = null;
		if ("".equals(request.getParameter("dimensionId"))
				|| request.getParameter("dimensionId") == null) {
			dimensionId = 0;
		} else {
			dimensionId = Integer.parseInt(request.getParameter("dimensionId"));
		}
		List<DimensionDetail> dimensionByCodeList = new ArrayList<DimensionDetail>();
		dimensionByCodeList = dimensionalityService
				.getDimensionDetailList(dimensionId);
		this.sendJsonMsgToClient(dimensionByCodeList, response);
		return null;
	}

	/**
	 * 获取二级维度数据
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getDimensionDetailSecList")
	public void getDimensionDetailSecList(HttpServletRequest request,
			HttpServletResponse response) {
		String parentId = request.getParameter("parentId");
		if (!StringUtils.notNullAndSpace(parentId)) {
			this.logger.warn("获取二级维度出错：parentId为空");
			this.sendMsgToClient("", response);
			return;
		}
		List<DimensionDetailSec> dimensionDetailSecList = dimensionalityService
				.getDimensionDetailSecList(parentId);
		this.sendJsonMsgToClient(dimensionDetailSecList, response);
	}

	/**
	 * 校验查询字段是否重复
	 *
	 * @param request
	 * @param response
	 * @param reportConfig
	 * @return
	 */
	@RequestMapping(value = "/checkColumnCode")
	public String checkColumnCode(HttpServletRequest request,
			HttpServletResponse response, ReportConfig reportConfig) {
		String message = "";
		String reportId = request.getParameter("reportId");
		String columnCode = request.getParameter("columnCode");
		if (!StringUtils.notNullAndSpace(reportId)) {
			this.logger.info("校验查询字段失败，报表id未知--------->");
			return null;
		}
		reportConfig.setReportId(Integer.valueOf(reportId));
		reportConfig.setColumnCode(columnCode);
		message = this.reportConfigService.checkColumnCode(reportConfig);
		this.sendMsgToClient(message, response);
		return null;

	}

	/**
	 * 校验驱动名
	 *
	 * @param request
	 * @param response
	 * @param dataSource
	 * @return
	 */
	@RequestMapping(value = "/checkDriverName")
	public String checkDriverName(HttpServletRequest request,
			HttpServletResponse response, ReportDataSource dataSource) {
		String message = "";
		String aliasDriverName = request.getParameter("driverName1");
		String driverName = request.getParameter("driverName");
		dataSource.setDriverName(driverName);
		message = this.newReportService.checkDriverName(dataSource,
				aliasDriverName);
		this.sendMsgToClient(message, response);
		return null;
	}

	/**
	 * 校验数据源是否关联报表
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/validateDataSource")
	public void validateDataSource(HttpServletRequest request,
			HttpServletResponse response) {
		String msg = "true";
		String dataSourceId = request.getParameter("dataSourceId");
		if (!StringUtils.notNullAndSpace(dataSourceId)) {
			this.logger.info("数据源未知------>");
		}
		List<ReportDataSource> list = this.newReportService
				.getListReportById(dataSourceId);
		if (list != null && !(list.isEmpty())) {
			msg = "false";
		}
		this.sendMsgToClient(msg, response);
	}

	/**
	 * 删除数据源
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/deleteDataSource")
	public void deleteDataSource(HttpServletRequest request,
			HttpServletResponse response) {
		String dataSourceId = request.getParameter("dataSourceId");
		if (!StringUtils.notNullAndSpace(dataSourceId)) {
			this.logger.info("数据源未知------>");
			return;
		}
		try {
			this.newReportService.deleteDataSourceById(Integer
					.valueOf(dataSourceId));
			this.sendMsgToClient(isSuccess, response);
		} catch (Exception e) {
			this.sendMsgToClient(isFailed, response);
			this.logger.error(e.getMessage(), e);
		}
		this.insertLog(request, "删除数据源，ID为：" + dataSourceId);
	}

	/**
	 * 保存报表查询信息
	 *
	 * @param reportConfig
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save")
	public String save(ReportConfig reportConfig, HttpServletRequest req,
			HttpServletResponse res, Model model) {
		boolean isSuccess = true;
		try {
			reportConfigService.saveReportConfig(reportConfig, req);
		} catch (Exception e) {
			isSuccess = false;
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
		model.addAttribute("msg", isSuccess ? this.isSuccess : this.isFailed);
		this.insertLog(req, "保存查询信息");
		return "common/save_result";
	}

	/**
	 * 报表链接配置（报表下钻）
	 * @param request (reportId：报表ID；setting：链接配置)
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/linkSetting")
	public ModelAndView linkSetting(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		// 报表ID
		String reportId = request.getParameter("reportId");
		if (reportId == null) {
			this.logger.warn("报表链接配置,报表ID未知-->");
			mv.addObject("exception", "报表链接配置失败,报表ID未知");
			return mv;
		}
		// 关联报表ID
		String relRptId = request.getParameter("relRptId");
		List<ReportConfig> configList = new ArrayList<ReportConfig>();
		if (StringUtils.notNullAndSpace(relRptId)) {
			ReportDesign relRptDesign = this.reportService.getReportById(Integer.valueOf(relRptId));
			// 关联报表名称
			mv.addObject("relRptName", relRptDesign.getReportName());
			configList = this.getConfigList(relRptId);
			mv.addObject("relRptId", relRptId);
		}
		String setting = request.getParameter("setting");
		try {
			// 处理中文乱码
			if (setting != null) {
				setting = new String(setting.getBytes("iso-8859-1"), "utf-8");
				String settingList[] = setting.split(";");
				for (int i = 0; i < configList.size(); i++) {
					for (int j = 0; j < settingList.length; j++) {
						String columnField = settingList[j].substring(0, settingList[j].indexOf("="));
						if (settingList[j].indexOf(":") > -1) {
							if (configList.get(i).getColumnCode().equals(columnField)) {
								configList.get(i).setBandColumnField(
										settingList[j].substring(settingList[j].indexOf(":")+1, settingList[j].length()-1));
								configList.get(i).setDefaultValue("");
								break;
							}
						} else {
							if (configList.get(i).getColumnCode().equals(columnField)) {
								configList.get(i).setDefaultValue(settingList[j].substring(settingList[j].indexOf("=")+1, settingList[j].length()));
								break;
							}
						}
					}
				}
			}
			mv.addObject("setting", setting);
			mv.addObject("configList", configList);
			JSONArray arr = JSONArray.fromObject(configList);
			mv.addObject("configListJson", arr.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 报表定义
		ReportDesign reportDesign = this.newReportService
			.getReportDesignById(Integer.valueOf(reportId));
		// 报表数据列
		String querySql = reportDesign.getReportSql();
		List<ReportField> reportFieldList = this.newReportService
				.listReportField(querySql, reportDesign.getDataSourceId());
		mv.addObject("reportFieldList", reportFieldList);
		mv.addObject("reportId", reportId);
		mv.setViewName("newReport/linkSetting");
		return mv;
	}

	/**
	 * 选择子报表（用于下钻）
	 * @param request （reportId：报表ID）
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/selectChildReport")
	public ModelAndView selectChildReport(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String reportId = request.getParameter("reportId");
		if (reportId == null) {
			this.logger.warn("选择子报表,报表ID未知-->");
			mv.addObject("exception", "选择子报表失败,报表ID未知");
			return mv;
		}
		List<ReportDesign> reportList = reportService.listAllReports();
		JSONArray arr = JSONArray.fromObject(reportList);
		String json = arr.toString();
		json = json.replaceAll("reportId", "id")
				.replaceAll("reportName", "name")
				.replaceAll("parentId", "pId")
				.replaceAll("}", ",open:true}");
		mv.addObject("zTreeNodes", json);
		mv.addObject("reportId", reportId);
		mv.setViewName("newReport/selectChildReport");
		return mv;
	}

	/**
	 * 获取报表参数
	 * @param request （reportId：报表ID）
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getReportParameters")
	public void getReportParameters(HttpServletRequest request,
			HttpServletResponse response) {
		String reportId = request.getParameter("reportId");
		if (reportId == null) {
			this.logger.warn("选择子报表,报表ID未知-->");
			this.sendMsgToClient("fail", response);
			return;
		}
		List<ReportConfig> configList = this.getConfigList(reportId);
		JSONArray arr = JSONArray.fromObject(configList);
		this.sendMsgToClient(arr.toString(), response);
	}

	/**
	 * 数据源配置
	 *
	 * @param reportDataSource
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/dataSource{reportId}")
	public ModelAndView dataSource(@PathVariable Integer reportId,
			HttpServletRequest request, HttpServletResponse response,
			ReportDataSource reportDataSource) {
		ReportDataSource dataSource = null;
		if (reportId == null) {
			this.logger.warn("数据源配置,报表ID未知-->");
			ModelAndView mv = new ModelAndView();
			mv.addObject("exception", "数据源配置失败,报表ID未知");
			return mv;
		} else {
			/* 查询报表数据源BYID */
			ReportDesign reportDesign = this.newReportService
					.getReportDesignById(reportId);
			if (StringUtils.notNullAndSpace(reportDesign.getDataSourceId())) {
				dataSource = this.newReportService.getDataSourceById(Integer
						.valueOf(reportDesign.getDataSourceId()));
			}
		}
		ModelAndView mv = new ModelAndView();
		/* 查询已有数据源 */
		List<ReportDataSource> dataSourceList = this.newReportService
				.getListDataSource();
		/* 查询报表数据 */
		ReportDesign reportDesign = this.newReportService
				.getReportDesignById(reportId);
		mv.addObject("reportId", reportId);
		mv.addObject("reportDesign", reportDesign);
		mv.addObject("dataSource", dataSource);
		mv.addObject("dataSourceList", dataSourceList);
		mv.setViewName("newReport/dataSource_info");
		return mv;
	}

	/**
	 * 数据源测试链接
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/testDataBaseLink")
	public void testDataBaseLink(HttpServletRequest request,
			HttpServletResponse response) {
		boolean isSuccess = false;
		String msg = "1";
		String dataDriver = request.getParameter("dataDriver");// "com.mysql.jdbc.Driver";//
		String url = request.getParameter("url");// "jdbc:mysql://192.168.0.15:3306/portal?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull";
		String userName = request.getParameter("userName");// "root";
		String password = request.getParameter("password");// "root";
		ReportDataSource reportDataSource = new ReportDataSource();
		reportDataSource.setDataDriver(dataDriver);
		reportDataSource.setUrl(url);
		reportDataSource.setUserName(userName);
		reportDataSource.setPassword(password);
		isSuccess = newReportService.testDataSource(reportDataSource);
		if (isSuccess == true) {// 成功
			msg = "0";
		} else if (isSuccess == false) {// 失败
			msg = "1";
		}
		this.sendMsgToClient(msg, response);

	}

	/**
	 * 报表语句测试链接
	 *
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/testReportSql")
	public void testReportSql(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		String msg = "1";
		String reportSql = request.getParameter("reportSql");
		String dataDriver = request.getParameter("dataDriver");// "com.mysql.jdbc.Driver";//
		String url = request.getParameter("url");// "jdbc:mysql://192.168.0.15:3306/portal?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull";
		String userName = request.getParameter("userName");// "root";
		String password = request.getParameter("password");// "root";
		ReportDataSource reportDataSource = new ReportDataSource();
		reportDataSource.setDataDriver(dataDriver);
		reportDataSource.setUrl(url);
		reportDataSource.setUserName(userName);
		reportDataSource.setPassword(password);
		int count = newReportService.testReportSql(reportDataSource, reportSql);
		if (count >= 0) {// 成功
			msg = "0";
		} else if (count == -1) {// 失败
			msg = "-1";
		} else if (count == -2) {// 失败
			msg = "-2";
		}
		this.sendMsgToClient(msg, response);

	}

	/**
	 * 编辑保存数据源
	 *
	 * @param dataSource
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/dataSourceSave")
	public String dataSourceSave(ReportDataSource dataSource,
			ReportDesign reportDesign, HttpServletRequest req,
			HttpServletResponse res, Model model) {
		this.logger.info("保存数据源");
		// 根据选择的sourceID(以有数据源项)判断是否新增
		newReportService.saveDataSource(dataSource,
				reportDesign.getDataSourceId(), req);
		this.insertLog(req, "保存数据源信息");
		// 新增
		if (!StringUtils.notNullAndSpace(reportDesign.getDataSourceId())) {
			Integer dataSourceId = dataSource.getId();
			reportDesign.setDataSourceId(dataSourceId.toString());
		} else {
			reportDesign.setDataSourceId(reportDesign.getDataSourceId());
		}
		boolean isSuccess = true;
		try {
			// 保存报表数据源
			newReportService.updateReportSql(reportDesign);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
			this.logger.error(e.getMessage(), e);
		}
		this.insertLog(req, "保存报表SQL语句");
		model.addAttribute("msg", isSuccess ? this.isSuccess : this.isFailed);
		return "common/save_result";
	}

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
	 * 获取报表查询条件
	 *
	 * @param reportId
	 * @return
	 */
	public List<ReportConfig> getConfigList(String reportId) {
		if (!StringUtils.notNullAndSpace(reportId)) {
			this.logger.warn("查询报表条件失败，未知的报表ID.");
			return null;
		}
		List<ReportConfig> configList = reportService
				.listPageConfigById(reportId);
		if (configList != null && !configList.isEmpty()) {
			for (int i = 0; i < configList.size(); i++) {
				if (configList.get(i) == null
						|| !StringUtils.notNullAndSpace(configList.get(i)
								.getCode()))
					continue;
				if (configList.get(i).getControlType() == 6) {

					try {
						// 维度型（下拉项）
						List<DimensionDetail> dimensionDetail = reportService
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
						List<DimensionDetail> dimensionDetail = reportService
								.listDimensionDetail(Integer
										.parseInt(configList.get(i).getCode()));
						// 二级维度
						List<DimensionDetailSec> dimensionDetailSecList = dimensionalityService
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
	 * 导出所有信息
	 *
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws ServletException
	 * @throws IOException
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
		ReportDesign reportDesign = reportService.getReportById(Integer
				.valueOf(reportId));
		if (reportDesign == null) {
			this.logger.warn("未找到报表信息:" + reportId);
			return null;
		}
		// 查询列
		// 生成报表头
		List<ReportCell> reportCellList = new ArrayList<ReportCell>();
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
		final List<ReportCell> reportCellList1 = mergeCellList;
		// 报表定义
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
			reportDesign.setPageSize(reportDefine.getPageSize());
		}
		/* 查询条件需要修改 */
		String queryWhere = new String(request.getParameter("queryWhere")
				.getBytes("ISO-8859-1"), "UTF-8");
		if (StringUtils.notNullAndSpace(queryWhere)) {
			if (queryWhere.toLowerCase().trim().startsWith("and ")) {
				reportDesign.setQueryWhere(queryWhere);
			} else {
				reportDesign.setQueryWhere(" AND " + queryWhere);
			}
		}

		// 查询数据
		List<List<String>> reportDataList = this.reportService.queryReport(
				reportDesign, pageNo, reportCellList);
		/* 下载excel名称 */
		String fileName = reportDesign.getReportName() + "-下载日期为：" + exportDate;
		if (!StringUtils.notNullAndSpace(fileName)) {
			fileName = "报表数据" + "-下载日期为：" + exportDate;
		}
		modelMap.put("fileName", fileName);
		/*
		 * List<String> excelTitleList = new ArrayList<String>(); for
		 * (ReportCell reportCell : reportCellList) { if (reportCell == null)
		 * continue; if (excelTitleList.toString().length() > 0) {
		 * excelTitleList.add(reportCell.getContent()); } }
		 * modelMap.put("excelTitleList", excelTitleList);
		 */
		modelMap.put("reportCellList", reportCellList);
		modelMap.put("mergeCellList", mergeCellList);
		if (reportDataList != null) {
			modelMap.put("reportDataList", reportDataList);
		}
		this.insertLog(request, "用户下载报表ID为：" + reportId + "的数据，下载文件名为："
				+ fileName);
		return new ModelAndView(new SelfExcelDownloadController(), modelMap);
	}

	/**
	 * 导出excel报表
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
	 * @param response
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

	@RequestMapping(value = "/updateColumnSort")
	public void updateColumnSort(HttpServletRequest request,
			HttpServletResponse response)throws Exception {
		String ids = request.getParameter("ids");
		if(StringUtils.notNullAndSpace(ids)  && ids.indexOf(",")>0){
			String[] theIds = ids.split(",");
			String beforeId = theIds[0];
			String afterId = theIds[1];
			try {
				reportConfigService.updateColumnSort(beforeId, afterId);
				this.sendMsgToClient(this.isSuccess, response);
			} catch (Exception e) {
				e.printStackTrace();
				this.sendMsgToClient(this.isFailed, response);
			}

		}else{
			this.sendMsgToClient(this.isFailed, response);
		}
	}
}