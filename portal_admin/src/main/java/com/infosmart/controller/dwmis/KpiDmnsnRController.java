package com.infosmart.controller.dwmis;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.po.dwmis.DwmisKpiInfo;
import com.infosmart.service.dwmis.KPIInfoService;
/**
 * KPI与维度关联
 * 目前不需要此模块
 * @author hgt
 *
 */
@Controller
@RequestMapping("/kpiDmnsnr")
public class KpiDmnsnRController {
	@Autowired
	private KPIInfoService kpiInfoService;
	
	/** 列表 */
	@RequestMapping("/kpiInfo")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response,DwmisKpiInfo dwmisKpiInfo) {
		List<DwmisKpiInfo> kpiInfoList = kpiInfoService.getKPIInfoPages(dwmisKpiInfo);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/dwmis/kpiDmnsnr/kpiInfo");
		mav.addObject("kpiInfoList", kpiInfoList);
		mav.addObject("dwmisKpiInfo", dwmisKpiInfo);
		return mav;
	}
	
	@RequestMapping(value = "/showKpiDmnsn{kpiCode}")
	public ModelAndView showKpiDmnsn(@PathVariable String kpiCode,HttpServletRequest request,
			HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		//参照大事记（未完成）
		/*List<DwpasCKpiInfo> kpiRelList = null ;
		
		List<DwpasCKpiInfo> kpiUnRelList = null;
		String title = null;
		try {
//			kpiRelList = bigEventService.getRelativeEventKPI(eventId);//关联到的
//			kpiUnRelList = bigEventService.getUnRelativeEventKPI(eventId);//为关联到的
//			title = bigEventService.getMISEventById(eventId).getTitle();
		} catch (Exception e) {
			mav.addObject("exception", e);
			e.printStackTrace();
		}		
		mav.addObject("kpiRelList", kpiRelList);
		mav.addObject("kpiUnRelList", kpiUnRelList);
		mav.addObject("title", title);
		mav.addObject("kpiCode", kpiCode);
		mav.setViewName("/bigEvent/eventRelKpi");*/

		return mav;
	}
}
