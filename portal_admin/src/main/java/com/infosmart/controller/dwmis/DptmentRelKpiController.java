package com.infosmart.controller.dwmis;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.controller.BaseController;
import com.infosmart.po.dwmis.DwmisKpiInfo;
import com.infosmart.po.dwmis.DwmisMisDptmnt;
import com.infosmart.service.dwmis.DwmisMisDptmntService;
import com.infosmart.util.StringUtils;
import com.infosmart.util.Tools;

/**
 * 部门与KPI关联控制类
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/dptmentRelKpi")
public class DptmentRelKpiController extends BaseController {
	@Autowired
	private DwmisMisDptmntService dwmisMisDptmntService;

	@RequestMapping(value = "/showDptmentRelKpi{depId}")
	public ModelAndView showDptmentRelKpi(@PathVariable String depId,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		// 未关联指标
		List<DwmisKpiInfo> kpiUnRelList = dwmisMisDptmntService
				.getUnRelativeDwmisMisDptmntKPIByKey(depId, null);
		// 与该部门关联的指标
		List<DwmisKpiInfo> kpiRelList = null;

		DwmisMisDptmnt dwmisMisDptmnt = new DwmisMisDptmnt();
		if (depId != null && depId != "") {
			kpiRelList = dwmisMisDptmntService
					.getRelativeDwmisMisDptmntKPI(depId);
			dwmisMisDptmnt = dwmisMisDptmntService
					.queryDwmisMisDptmntBydepId(depId);
		}
		Map<Integer, String> groupMap = new HashMap<Integer, String>();
		if (kpiUnRelList != null && !kpiUnRelList.isEmpty()) {
			for (DwmisKpiInfo kpiRel : kpiUnRelList) {
				if (kpiRel.getGoalType() == null) {
					continue;
				}
				groupMap.put(kpiRel.getGoalType(), kpiRel.getGoalTypeDesc());
			}
		}
		mv.addObject("groupMap", groupMap);
		mv.addObject("kpiRelList", kpiRelList);
		mv.addObject("kpiUnRelList", kpiUnRelList);
		mv.addObject("dwmisMisDptmnt", dwmisMisDptmnt);
		mv.addObject("depId", depId);
		mv.setViewName("/dwmis/depmanage/dptmentRelKpi");
		return mv;
	}

	@RequestMapping(value = "/searchKpiCodes")
	public void search(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String depId = request.getParameter("depId");
		String keyCode = request.getParameter("keyCode");
		String isChooseKpi = request.getParameter("isChooseKpi");
		String[] ChooseCodes = isChooseKpi.split(",");
		if (null != keyCode && "" != keyCode) {
			keyCode = keyCode.trim();
			keyCode = new String(keyCode.getBytes("ISO-8859-1"), "UTF-8");
		}
		if (depId != null && !"".equals(depId)) {
			depId = depId.trim();
		}
		// 与该部门不关联的指标
		List<DwmisKpiInfo> kpiUnRelAlList = null;
		kpiUnRelAlList = dwmisMisDptmntService
				.getUnRelativeDwmisMisDptmntKPIByKey(depId, keyCode);
		// 为关联指标的指标Code
		List<String> UnRelCodes = new ArrayList<String>();
		if (kpiUnRelAlList != null) {
			for (DwmisKpiInfo kpiUnRel : kpiUnRelAlList) {
				if (!StringUtils.notNullAndSpace(kpiUnRel.getKpiCode()))
					continue;
				UnRelCodes.add(kpiUnRel.getKpiCode());
			}
		}

		if (ChooseCodes != null) {
			for (String tempCode : ChooseCodes) {
				if (UnRelCodes.contains(tempCode)) {
					UnRelCodes.remove(tempCode);
				}
			}
		}
		List<DwmisKpiInfo> dwmisKpiInfoList = new ArrayList<DwmisKpiInfo>();
		List<DwmisKpiInfo> kpiUnRelList = dwmisMisDptmntService
				.getKPIInfoByCodes(UnRelCodes);
		DwmisKpiInfo dwmisKpiInfo = null;
		if (kpiUnRelList != null) {
			for (DwmisKpiInfo kpiInfo : kpiUnRelList) {
				if (kpiInfo == null)
					continue;
				dwmisKpiInfo = new DwmisKpiInfo();
				dwmisKpiInfo.setKpiCode(kpiInfo.getKpiCode());
				dwmisKpiInfo.setKpiName(kpiInfo.getKpiName());
				dwmisKpiInfo.setGoalType(kpiInfo.getGoalType());
				dwmisKpiInfo.setGoalTypeDesc(kpiInfo.getGoalTypeDesc());
				dwmisKpiInfoList.add(dwmisKpiInfo);
			}
		}

		// List对象转json
		JSONArray jSONArray = null;
		jSONArray = JSONArray.fromObject(dwmisKpiInfoList);
		PrintWriter out;
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			out.print(jSONArray.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/updateKPIRelativeByKPICode")
	public ModelAndView updateKPIRelativeByKPICode(HttpServletRequest request,
			HttpServletResponse response) {
		String[] codeList = request.getParameterValues("kpiRelInfo");
		ModelAndView mav = new ModelAndView();
		String depId = request.getParameter("depId");
		try {
			if (codeList != null) {
				// 去除添加时重选的元素
				codeList = Tools.toDiffArray(codeList);
				dwmisMisDptmntService.deleteRelativeDwmisMisDptmntKPI(depId);
				// 批量插入关联指标
				dwmisMisDptmntService.insertRelativeDwmisMisDptmntKPI(depId,
						codeList);
			} else {
				dwmisMisDptmntService.deleteRelativeDwmisMisDptmntKPI(depId);
			}
			mav.addObject("msg", "success");
		} catch (Exception e) {
			this.logger.info("绑定关联指标失败！");
			e.printStackTrace();
			mav.addObject("msg", "failed");
			mav.addObject("exception", e);
		}
		mav.setViewName("/common/save_result");
		return mav;
	}
}
