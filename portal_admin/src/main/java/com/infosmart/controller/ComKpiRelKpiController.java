package com.infosmart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.po.ComKpiInfo;
import com.infosmart.po.DwpasCKpiInfo;
import com.infosmart.service.ComKpiInfoService;

@Controller
@RequestMapping(value = "/ComkpiRelKpi")
public class ComKpiRelKpiController extends BaseController {

	@Autowired
	private ComKpiInfoService comkpiService;	
	/**
	 * 指标关联显示
	 * 
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/showRelKpi{comKpiCode}")
	public ModelAndView showRelKpi(@PathVariable String comKpiCode,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		List<DwpasCKpiInfo> kpiRelList = null ;
		List<DwpasCKpiInfo> kpiUnRelList = null;
		ComKpiInfo comkpiinfo = new ComKpiInfo();
		String kpiCode = request.getParameter("kpiCode");
		if (null != kpiCode && "" != kpiCode) {
			kpiCode = kpiCode.toString().trim();
		}
		if (comKpiCode != null && comKpiCode != "") {
			kpiRelList = comkpiService
					.getRelativeKPI(comKpiCode);
			kpiUnRelList = comkpiService
					.getUnRelativeKPI(comKpiCode, kpiCode);
			comkpiinfo = comkpiService.getComkpiInfo(comKpiCode);		
		}	
		mav.addObject("kpiRelList", kpiRelList);
		mav.addObject("kpiUnRelList", kpiUnRelList);
		mav.addObject("comKpiCode", comKpiCode);
		mav.addObject("kpiCode", kpiCode);
		mav.addObject("comkpiinfo", comkpiinfo);
		mav.setViewName("admin/comkpiinfo/comkpiRelkpi");
		return mav;
	}
	/**
	 * 更新当前通用指标与它关联的指标关系
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/updateKPIRelativeByKPICode")
	public ModelAndView updateKPIRelativeByKPICode(HttpServletRequest request,
					HttpServletResponse response) {
		String[] arrayList = request.getParameterValues("kpiRelInfo");
		String comKpiCode = request.getParameter("comKpiCode");
		Map<String,Object> DwpasCKpiInfoMap = new HashMap<String,Object>();
		ModelAndView mav = new ModelAndView();
		try {
			if (arrayList != null) {
				comkpiService.deleteRelativeKPI(comKpiCode);
				for (String temp : arrayList) {
					DwpasCKpiInfo kInfo = new DwpasCKpiInfo();
					kInfo.setKpiCode(temp);
					kInfo.setComKpiCode(comKpiCode);
					DwpasCKpiInfoMap.put("comAndkpiCode",kInfo);
					comkpiService.insertRelativeKPI(DwpasCKpiInfoMap);
				}
			} else {
				comkpiService.deleteRelativeKPI(comKpiCode);
			}
		} catch (Exception e) {
				logger.error("comKpiCode=" + comKpiCode + "通用指标与指标关联建立失败", e);
				mav.addObject("exception", e);
				e.printStackTrace();
		}
		return new  ModelAndView("redirect:/ComkpiRelKpi/showRelKpi"+comKpiCode+".html");
	}
}
