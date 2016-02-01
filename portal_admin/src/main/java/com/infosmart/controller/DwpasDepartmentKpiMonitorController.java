package com.infosmart.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.po.DwpasDepartmentKpiInfo;
import com.infosmart.service.DwpasDepartmentKpiMonitorService;
import com.infosmart.util.StringUtils;

/**
 * 部门指标监控
 * 
 */
@Controller
@RequestMapping(value = "departmentMonitor")
public class DwpasDepartmentKpiMonitorController extends BaseController {
	@Autowired
	private DwpasDepartmentKpiMonitorService departmentKpiMonitorService;

	/**
	 * 显示列表
	 * 
	 * @param request
	 * @param response
	 * @param departmentKpiInfo
	 * @return
	 */
	@RequestMapping
	public ModelAndView showDepartmentKpiMonitorInfo(
			HttpServletRequest request, HttpServletResponse response,
			DwpasDepartmentKpiInfo departmentKpiInfo) {
		if (departmentKpiInfo == null) {
			this.logger.warn("查询部门指标信息失败，参数departmentKpiInfo为null");
		}
		ModelAndView mv = new ModelAndView();
		String reportDate = "";
		if (departmentKpiInfo.getReportDate() != null
				&& departmentKpiInfo.getReportDate().length() > 0) {
			reportDate = departmentKpiInfo.getReportDate();
			departmentKpiInfo.setReportDate(reportDate.replace("-", "").trim());
		}
		List<DwpasDepartmentKpiInfo> dwpasDepartmentKpiList = departmentKpiMonitorService
				.listPageAllDepartmentKpiInfos(departmentKpiInfo);
		mv.setViewName("admin/departmentKpi/departmentKpiInfo");
		mv.addObject("dwpasDepartmentKpiList", dwpasDepartmentKpiList);
		mv.addObject("departmentKpiInfo", departmentKpiInfo);
		mv.addObject("reportDate", reportDate);
		return mv;
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/delete")
	public void deleteInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		try {
			departmentKpiMonitorService.deleteDepartmentKpiInfoById(id);
			this.sendMsgToClient(isSuccess, response);
		} catch (Exception e) {

			e.printStackTrace();
			this.logger.error("删除部门指标信息失败：" + e.getMessage(), e);
			this.sendMsgToClient(isFailed, response);

		}
	}

	/**
	 * 添加信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/add")
	public ModelAndView addInfo(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String type = request.getParameter("type");

		// 年份选择 数组
		Calendar cal = Calendar.getInstance();
		Integer year = cal.get(Calendar.YEAR) - 5;
		List<Integer> yearList = new ArrayList<Integer>();
		for (int i = 0; i <= 10; i++) {
			yearList.add(year + i);
		}
		mv.addObject("type", type);
		mv.addObject("yearList", yearList);
		mv.setViewName("admin/departmentKpi/addDepartmentKpiInfo");
		return mv;
	}

	/**
	 * 修改部门指标监控信息(modify by mf 2012/10/16)
	 * 
	 * @param request
	 * @param response
	 * @param departmentKpiInfo
	 * @return
	 */
	@RequestMapping(value = "/update{id}")
	public ModelAndView upDateInfo(@PathVariable String id,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		// 部门指标信息
		DwpasDepartmentKpiInfo departmentKpiInfo = departmentKpiMonitorService
				.getDepartmentKpiInfoById(id);
		String type = "";
		if (departmentKpiInfo.getReportDate().length() == 4) {
			// 年类型
			type = "Y";
		}

		// 年份选择 数组
		Calendar cal = Calendar.getInstance();
		Integer year = cal.get(Calendar.YEAR) - 5;
		List<Integer> yearList = new ArrayList<Integer>();
		for (int i = 0; i <= 10; i++) {
			yearList.add(year + i);
		}
		mv.addObject("type", type);
		mv.addObject("yearList", yearList);
		mv.addObject("departmentKpiInfo", departmentKpiInfo);
		mv.setViewName("admin/departmentKpi/addDepartmentKpiInfo");
		return mv;
	}

	/**
	 * 保存操作 (modify by mf 2012/10/16)
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response,
			DwpasDepartmentKpiInfo departmentKpiInfo) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("common/save_result");
		if (departmentKpiInfo == null) {
			this.logger.warn("保存部门指标信息失败，参数departmentKpiInfo为null");
			mv.addObject("msg", isFailed);
			return mv;
		}
		String reportDate = "";
		if (departmentKpiInfo.getReportDate() != null
				&& departmentKpiInfo.getReportDate().length() > 0) {
			reportDate = departmentKpiInfo.getReportDate();
			departmentKpiInfo.setReportDate(reportDate.replace("-", "").trim());
		}
		if (departmentKpiInfo.getDeptName() != null
				&& departmentKpiInfo.getDeptName().length() > 0) {
			String deptName = departmentKpiInfo.getDeptName();
			String deptId = "";
			if (deptName.equals("运营部")) {
				deptId = "dept1";
			} else if (deptName.equals("产品部")) {
				deptId = "dept2";
			} else if (deptName.equals("财务部")) {
				deptId = "dept3";
			} else if (deptName.equals("市场部")) {
				deptId = "dept4";
			} else if (deptName.equals("客服部")) {
				deptId = "dept5";
			}
			departmentKpiInfo.setDeptId(deptId);
		}
		if (!StringUtils.notNullAndSpace(departmentKpiInfo.getId())) {
			// id 为空 新增操作
			try {
				departmentKpiMonitorService
						.insertDepartmentKpiInfo(departmentKpiInfo);
				mv.addObject("msg", "success");
			} catch (Exception e) {
				e.printStackTrace();
				mv.addObject("msg", "failed");
			}
		} else {
			// id 不为空 修改操作
			try {
				departmentKpiMonitorService
						.updateDepartmentKpiInfo(departmentKpiInfo);
				mv.addObject("msg", "success");
			} catch (Exception e) {
				e.printStackTrace();
				mv.addObject("msg", "failed");
			}
		}
		return mv;

	}

}
