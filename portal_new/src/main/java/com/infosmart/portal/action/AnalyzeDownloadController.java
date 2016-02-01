/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2011 All Rights Reserved.
 */
package com.infosmart.portal.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCPrdInfo;
import com.infosmart.portal.pojo.ProkpiAnalyze;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.StringUtils;

/**
 * 产品指标分析Excel下载Controller
 * 
 * @author
 * @version $Id: AnalyzeDownloadController.java, v 0.1 2011-10-23 ����04:36:39
 *          yufei.sun Exp $
 */
@Controller
public class AnalyzeDownloadController extends BaseController {

	@RequestMapping("/AnalyzeDownload/downloadData")
	public ModelAndView downloadData(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// 产品ID，默认从页面参数取产品ID（用于选择产品）
		// String productId = request.getParameter("productId");

		String kpiType = request.getParameter("kpiType");
		// if (!StringUtils.notNullAndSpace(productId)) {
		// // 如果没取到产品ID,则取session中产品ID，初始是web.xml定义的的产品ID
		// productId = this.getCrtProductIdOfReport(request);
		// }
		// 查询日期,从URL参数取得(用于变更日期)
		String queryDate = request.getParameter("queryDate");
		if (!StringUtils.notNullAndSpace(queryDate)) {
			// 默认是取session中日期，初始是当前日期
			if (Integer.valueOf(kpiType) == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
				// 按日
				queryDate = this.getCrtQueryDateOfReport(request);
			} else {
				// 按月
				queryDate = this.getCrtQueryMonthOfReport(request);
			}
		} else {
			queryDate = queryDate.replace("-", "");
		}
		String beginDate = "";
		// 默认为两年前的数据
		if (Integer.valueOf(kpiType) == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
			beginDate = DateUtils
					.formatByFormatRule(
							DateUtils.getPreviousMonth(DateUtils
									.parseByFormatRule(
											queryDate.replace("-", ""),
											"yyyyMMdd"), 24), "yyyyMMdd");
		} else {
			beginDate = DateUtils.formatByFormatRule(
					DateUtils.getPreviousMonth(
							DateUtils.parseByFormatRule(
									queryDate.replace("-", ""), "yyyyMM"), 24),
					"yyyyMM");
		}
		List<String> downloadkpiCodeList = new ArrayList<String>();
		String downloadkpiCodes = request.getParameter("downloadkpiCodes");
		this.logger.info("下载指标数据:" + downloadkpiCodes);
		if (StringUtils.notNullAndSpace(downloadkpiCodes)) {
			for (String kpiCode : StringUtils.split(downloadkpiCodes, ";")) {
				if (StringUtils.notNullAndSpace(kpiCode)) {
					downloadkpiCodeList.add(kpiCode);
				}
			}
		}
		// 获取要下载的数据
		List<ProkpiAnalyze> listKpiDates = dwpasStKpiDataService.getHistory(
				downloadkpiCodeList, kpiType, beginDate, queryDate);
		// DwpasCPrdInfo dwpasCPrdInfo = dwpasCPrdInfoService
		// .getDwpasCPrdInfoByProductId(productId);

		String kpiInfoNames = "";
		for (int i = 0; i < downloadkpiCodeList.size(); i++) {
			DwpasCKpiInfo kpiInfo = dwpasCKpiInfoService
					.getDwpasCKpiInfoByCode(downloadkpiCodeList.get(i));
			if (kpiInfo == null)
				continue;
			if (i < downloadkpiCodeList.size() - 1) {
				kpiInfoNames += kpiInfo.getDispName() + ",";
			} else {
				kpiInfoNames += kpiInfo.getDispName();
			}
		}
		// // 防止空指标
		// dwpasCPrdInfo = dwpasCPrdInfo == null ? new DwpasCPrdInfo()
		// : dwpasCPrdInfo;
		// 生成的文件名
		String fileName = "指标：" + kpiInfoNames + "--截止日期为：" + queryDate;
		if (!StringUtils.notNullAndSpace(fileName)) {
			fileName = "指标分析" + "--截止日期为：" + queryDate;
		}
		modelMap.addObject("fileName", fileName);
		if (listKpiDates != null) {
			modelMap.addObject("listKpiDates", listKpiDates);
		}
		this.insertLog(request, "下载" + fileName + "的数据");
		return new ModelAndView(new AnalyzeExcelDownloadController(), modelMap);
	}
}
