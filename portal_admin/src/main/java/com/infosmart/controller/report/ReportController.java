package com.infosmart.controller.report;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.infosmart.controller.BaseController;
import com.infosmart.po.report.ReportDesign;
import com.infosmart.service.report.ReportService;
import com.infosmart.service.report.SelfApplyService;
import com.infosmart.util.StringUtils;

/**
 * 报表结构管理
 * 
 * @author infosmart
 * 
 */
@Controller
@RequestMapping(value = "/report")
public class ReportController extends BaseController {
	protected final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private ReportService reportService;
	@Autowired
	private SelfApplyService selfApplyService;

	/**
	 * 显示报表列表 
	 * date 2012-06-04 
	 * @author infosmart
	 * @param model
	 * @return
	 */
	@RequestMapping
	public String list(HttpServletRequest request, Model model) {
		List<ReportDesign> reportList = reportService.listAllParentReport();
		model.addAttribute("reportList", reportList);
		this.insertLog(request, "查看报表列表");
		return "report/report";
	}

	/**
	 * 请求新增页面 
	 * date 2012-06-04 
	 * @author infosmart
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String toAdd(Model model) {
		List<ReportDesign> reportList = reportService.listAllToAdd();
		model.addAttribute("reportList", reportList);
		return "report/addReport";
	}

	/**
	 * 保存信息 
	 * date 2012-06-04 
	 * @author infosmart
	 * @param report
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, ReportDesign report,
			Model model) {
		try {
			reportService.saveReport(report);
			model.addAttribute("msg", "success");
			if (report.getReportId() != null
					&& report.getReportId().intValue() > 0) {
				if (StringUtils.notNullAndSpace(report.getIsReport())
						&& report.getIsReport().equals("1")) {
					this.insertLog(request,
							"编辑报表信息,报表名称：" + report.getReportName());
				} else if (StringUtils.notNullAndSpace(report.getIsReport())
						&& report.getIsReport().equals("0")) {
					this.insertLog(request,
							"编辑目录信息,目录名称：" + report.getReportName());
				}
			} else {
				if (StringUtils.notNullAndSpace(report.getIsReport())
						&& report.getIsReport().equals("1")) {
					this.insertLog(request,
							"新增报表信息,报表名称：" + report.getReportName());
				} else if (StringUtils.notNullAndSpace(report.getIsReport())
						&& report.getIsReport().equals("0")) {
					this.insertLog(request,
							"新增目录信息,目录名称：" + report.getReportName());
				}
			}

		} catch (Exception e) {
			model.addAttribute("msg", "failed");
		}
		return "common/save_result";
	}

	/**
	 * 获取当前报表的所有子报表 
	 * date 2012-06-04 
	 * @author infosmart
	 * @param
	 * @param response
	 */
	@RequestMapping(value = "/sub{reportId}")
	public void getSub(@PathVariable Integer reportId,
			HttpServletResponse response) {
		if (reportId == null) {
			this.logger.warn("获取当前报表的所有子报表传递的reportId为null");
		}
		List<ReportDesign> subReportList = reportService
				.listSubReportByParentId(reportId);// 展开二级菜单
		JSONArray arr = JSONArray.fromObject(subReportList);
		PrintWriter out;
		try {
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			String json = arr.toString();
			out.write(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除报表 
	 * date 2012-06-04 
	 * @author infosmart
	 * @param reportId
	 * @param out
	 */
	@RequestMapping(value = "/del{reportId}")
	public void delete(@PathVariable Integer reportId, PrintWriter out,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		if (reportId == null) {
			this.logger.warn("删除报表时传递的reportId为null");
		}
		try {
			List<ReportDesign> subReportList = reportService
					.listSubReportByParentId(reportId);
			ReportDesign reportInfo = reportService.queryReportById(reportId
					.toString());
			reportService.deleteReportById(reportId);
			List<Integer> reportIds = new ArrayList<Integer>();
			if (!(reportInfo.equals(""))) {
				reportIds.add(reportInfo.getReportId());
			}
			if (subReportList != null && !(subReportList.isEmpty())) {
				for (ReportDesign report : subReportList) {
					reportIds.add(report.getReportId());
				}
			}
			selfApplyService.deleteSelfApplyByIds(reportIds);
			out.write("success");
			this.insertLog(request, "删除报表信息");
		} catch (Exception e) {

			out.write("failed");
		}
		out.flush();
		out.close();

	}

	/**
	 * 编辑
	 * date 2012-06-04 
	 * @author infosmart
	 * @param reportId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit{reportId}")
	public String toEdit(@PathVariable Integer reportId, Model model) {
		if (reportId == null) {
			this.logger.warn("编辑报表时传递的reportId为null");
		}
		ReportDesign report = reportService.getReportById(reportId);
		model.addAttribute("report", report);
		List<ReportDesign> reportList = reportService.listAllToAdd();
		model.addAttribute("reportList", reportList);
		return "report/editReport";
	}
}
