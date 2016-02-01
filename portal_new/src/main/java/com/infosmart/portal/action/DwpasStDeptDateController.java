package com.infosmart.portal.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasStDeptKpiData;
import com.infosmart.portal.service.DwpasStDeptKpiDateService;
import com.infosmart.portal.util.StringUtils;

@Controller
@RequestMapping("/dwpasStDeptKpiData")
public class DwpasStDeptDateController extends BaseController {
	@Autowired
	private DwpasStDeptKpiDateService dwpasStDeptKpiDateService;

	//
	// @RequestMapping("/testIndex/showIndex")
	// public ModelAndView showIndex(HttpServletRequest request,
	// HttpServletResponse response) {
	// // 测试数据(实际从session中取或参数中取)
	// final String productId = this.getCrtProductIdOfReport(request);
	// // 指标类型
	// // 产品发展趋势业务量 ,KPI类型为日,根据实际情况作修改
	// DwpasCColumnInfo productSaleAmountColumn = this.columnInfoService
	// .getColumnAndKpiInfoByColumnCodeAndPrdId(this
	// .getCrtUserTemplateId(request),
	// SystemColumnEnum.PRODUCT_TOTAL_TRANSACTIONS_AMOUNT
	// .getColumnCode(), productId,
	// DwpasCKpiInfo.KPI_TYPE_OF_DAY);
	// request.setAttribute("productSaleAmountColumn", productSaleAmountColumn);
	// // 产品发展趋势业务笔 ,KPI类型为日,根据实际情况作修改
	// DwpasCColumnInfo productSaleTimesColumn = this.columnInfoService
	// .getColumnAndKpiInfoByColumnCodeAndPrdId(this
	// .getCrtUserTemplateId(request),
	// SystemColumnEnum.PRODUCT_TOTAL_TRANSACTIONS_TIMES
	// .getColumnCode(), productId,
	// DwpasCKpiInfo.KPI_TYPE_OF_DAY);
	// request.setAttribute("productSaleTimesColumn", productSaleTimesColumn);
	// // 全年用户构造情况栏目 KPI类型为日,根据实际情况作修改
	// DwpasCColumnInfo userDistributionColumn = this.columnInfoService
	// .getColumnAndKpiInfoByColumnCodeAndPrdId(
	// this.getCrtUserTemplateId(request),
	// SystemColumnEnum.PRODUCT_HEALTH_FULL_YEAR_USER_KEEP_PIE_CHART
	// .getColumnCode(), productId,
	// DwpasCKpiInfo.KPI_TYPE_OF_MONTH);
	// request.setAttribute("userDistributionColumn", userDistributionColumn);
	// // 返回当前产品ID
	// request.setAttribute("productId", productId);
	// // 转到/jsp/testPage.jsp
	// return new ModelAndView("/testPage");
	// }

	/*
	 * 部门指标监控
	 * 
	 * @param request
	 * 
	 * @param response
	 * 
	 * @return
	 */
	@RequestMapping("/showKpiMonitor")
	public ModelAndView showKpiDataMonitor(HttpServletRequest request,
			HttpServletResponse response) {
		// 获取日期=====================================
		String queryDate = request.getParameter("queryDate");
		String kpiType = request.getParameter("kpiType");
		if (kpiType == null) {
			kpiType = "3";
		}
		if (Integer.parseInt(kpiType) == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
			if (StringUtils.notNullAndSpace(queryDate)) {
				// 变更了日期=====================================
				this.setCrtQueryDateOfReport(request, queryDate);
			} else {
				// 取默认日期=====================================
				queryDate = this.getCrtQueryDateOfReport(request);
			}

		} else {
			if (StringUtils.notNullAndSpace(queryDate)) {
				// 变更了日期=====================================
				this.setCrtQueryMonthOfReport(request, queryDate);
			} else {
				// 取默认日期=====================================
				queryDate = this.getCrtQueryMonthOfReport(request);
			}
		}
		String queryValueDate = queryDate.replace("-", "");
		Map map = new HashMap();
		// request.setAttribute("kpiType", "3");
		// 列出部门名称及部门id
		List<DwpasStDeptKpiData> dwpasStDeptNames = dwpasStDeptKpiDateService
				.getDwpasStDeptNames(queryValueDate);
		String tomonth = queryValueDate.substring(0, 6);
		map.put("dwpasStDeptNames", dwpasStDeptNames);
		map.put("date", queryDate);
		map.put("tomonth", tomonth);
		return new ModelAndView("/KpiInfoMan/DeptKpiMonitor", map);
	}

	/**
	 * 显示部门数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/loadDeptKpiData")
	public ModelAndView loadDeptKpiDat(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		String deptId = request.getParameter("deptId");
		String reportDate = this.getCrtQueryMonthOfReport(request);
		// 根据部门ID获得部门数据
		List<DwpasStDeptKpiData> dwpasStDeptKpiDateList = dwpasStDeptKpiDateService
				.getAllDwpasStDeptDates(deptId, reportDate);
		map.put("dwpasStDeptKpiDateList", dwpasStDeptKpiDateList);
		return new ModelAndView("/KpiInfoMan/ListDeptKpiDates", map);
	}
}
