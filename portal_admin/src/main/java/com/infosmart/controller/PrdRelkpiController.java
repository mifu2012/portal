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

import com.infosmart.po.DwpasCKpiInfo;
import com.infosmart.po.PrdMngInfo;
import com.infosmart.service.PrdMngInfoService;

@Controller
@RequestMapping(value = "/PrdRelKpi")
public class PrdRelkpiController extends BaseController {

	@Autowired
	private PrdMngInfoService prdService;	
	/**
	 * 指标关联显示
	 * 
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/showRelKpi{productId}")
	public ModelAndView showRelKpi(@PathVariable String productId,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		List<DwpasCKpiInfo> kpiRelList = null ;
		List<DwpasCKpiInfo> kpiUnRelList = null;
		String kpiCode = request.getParameter("kpiCode");
		PrdMngInfo prdinfo = new PrdMngInfo();
		if (null != kpiCode && "" != kpiCode) {
			kpiCode = kpiCode.toString().trim();
		}
		if (productId != null && productId != "") {
			kpiRelList = prdService
					.getRelativeKPI(productId);
			kpiUnRelList = prdService
					.getUnRelativeKPI(productId, kpiCode);
			prdinfo = prdService.getPrdInfo(productId);
		}		
		mav.addObject("kpiRelList", kpiRelList);
		mav.addObject("kpiUnRelList", kpiUnRelList);
		mav.addObject("productId", productId);
		mav.addObject("kpiCode", kpiCode);
		mav.addObject("prdinfo", prdinfo);
		mav.setViewName("admin/prdinfo/prdRelkpi");
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
		String productId = request.getParameter("productId");
		Map<String,Object> DwpasCKpiInfoMap = new HashMap<String,Object>();
		ModelAndView mav = new ModelAndView();
		try {
			if (arrayList != null) {
				prdService.deleteRelativeKPI(productId);
				for (String temp : arrayList) {
					DwpasCKpiInfo kInfo = new DwpasCKpiInfo();
					kInfo.setKpiCode(temp);
					kInfo.setProductId(productId);
					DwpasCKpiInfoMap.put("prdAndkpiCode",kInfo);
					prdService.insertRelativeKPI(DwpasCKpiInfoMap);
				}
			} else {
				prdService.deleteRelativeKPI(productId);
			}
		} catch (Exception e) {
				logger.error("productId=" + productId + "产品与指标关联建立失败", e);
				mav.addObject("exception", e);
				e.printStackTrace();
		}
		return new ModelAndView("redirect:/PrdRelKpi/showRelKpi"+productId+".html");
	}

}
