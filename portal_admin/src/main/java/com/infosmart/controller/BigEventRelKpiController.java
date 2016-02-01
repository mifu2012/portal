package com.infosmart.controller;

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

import com.infosmart.po.BigEventPo;
import com.infosmart.po.DwpasCKpiInfo;
import com.infosmart.service.BigEventService;

/**
 * 大事记管理控制类
 * 
 * 
 */

@Controller
@RequestMapping(value = "/BigEventRelKpi")
public class BigEventRelKpiController extends BaseController {

	@Autowired
	private BigEventService bigEventService;

	/**
	 * 指标关联显示
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/showEventRelKpi{eventId}")
	public ModelAndView showEventRelKpi(@PathVariable String eventId,
			HttpServletRequest request, HttpServletResponse response,
			DwpasCKpiInfo dwpasCKpiInfo) {
		String kpiType=request.getParameter("kpiType");
		if(kpiType==null||kpiType.length()==0){
			kpiType="1";
		}
		if (dwpasCKpiInfo == null) {
			this.logger.warn("DwpasCKpiInfo对象为null");
		}
		this.logger.info("--------------->指标关联显示");
		ModelAndView mav = new ModelAndView();
		List<DwpasCKpiInfo> kpiRelList = null;
		List<DwpasCKpiInfo> kpiUnRelList = null;
		String title = "";
		try {
			//kpiRelList = bigEventService.getRelativeEventKPI(eventId);
			if (eventId != null && eventId != "") {
				//关联指标
				kpiRelList = bigEventService.getRelativeEventKPI(eventId);
				this.logger
						.info("--------------->已关联指标：" + kpiRelList == null ? 0
								: kpiRelList.size());
				//不关联指标
				kpiUnRelList = bigEventService.getUnRelKpiInfoList(eventId);
				BigEventPo bigEventPo = bigEventService.getMISEventById(eventId);
				if(bigEventPo!=null){
					title = bigEventPo.getTitle();
				}
			} else {
				this.logger.warn("---------------->事件ID为空");
				kpiRelList = new ArrayList<DwpasCKpiInfo>();
				kpiUnRelList = bigEventService.listAllKpiInfoByKpiType(kpiType);
				title = "";
			}
		} catch (Exception e) {
			logger.error("大事记与指标关联页面出错"+e.getMessage(), e);
			mav.addObject("exception", e);
			e.printStackTrace();
		}
		this.insertLog(request, "查询大事件指标绑定信息");
		mav.addObject("kpiRelList", kpiRelList);
		mav.addObject("kpiUnRelList", kpiUnRelList);
		mav.addObject("title", title);
		mav.addObject("eventId", eventId);
		mav.addObject("kpiType", kpiType);
		mav.setViewName("/bigEvent/eventRelKpi");
		return mav;
	}

	@RequestMapping(value = "/searchEventRelKpi")
	public void search(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String eventId = request.getParameter("eventId");
		String keyCode = request.getParameter("keyCode");
		String kpiType = request.getParameter("kpiType");
		String linkedKpiCode=request.getParameter("linkedKpiCode");
		if (null != keyCode && "" != keyCode) {
			keyCode = new String(keyCode.getBytes("ISO-8859-1"), "UTF-8");
		}
		// 与该部门不管关联的指标
		List<DwpasCKpiInfo> kpiUnRelList = null;
		try {
			kpiUnRelList = bigEventService.getUnRelativeEventKPIByKpiCodeOrKpiName(
					linkedKpiCode, keyCode,kpiType);
		} catch (Exception e) {

			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
		
//		List<DwpasCKpiInfo> dwmisKpiInfoList = new ArrayList<DwpasCKpiInfo>();
//		DwpasCKpiInfo dwmisKpiInfo = null;
//		for (DwpasCKpiInfo CkpiInfo : kpiUnRelList) {
//			dwmisKpiInfo = new DwpasCKpiInfo();
//			dwmisKpiInfo.setKpiCode(CkpiInfo.getKpiCode());
//			dwmisKpiInfo.setKpiName(CkpiInfo.getKpiName());
//			dwmisKpiInfoList.add(dwmisKpiInfo);
//		}
		// List对象转json
		JSONArray jSONArray = null;

		jSONArray = JSONArray.fromObject(kpiUnRelList);
		PrintWriter out;
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			out.print(jSONArray.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新当前大事记与它关联的指标关系
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/updateKPIRelativeByKPICode")
	public ModelAndView updateKPIRelativeByKPICode(HttpServletRequest request,
			HttpServletResponse response) {
		String[] arrayList = request.getParameterValues("kpiRelInfo");
		String eventId = request.getParameter("eventId");
		Map DwpasCKpiInfoMap = new HashMap();
		ModelAndView mav = new ModelAndView();
		try {
			if (arrayList != null) {
				bigEventService.deleteRelativeEventKPI(Integer
						.parseInt(eventId));
				for (String temp : arrayList) {
					DwpasCKpiInfo kInfo = new DwpasCKpiInfo();
					kInfo.setEventId(Integer.parseInt(eventId));
					kInfo.setKpiCode(temp);
					DwpasCKpiInfoMap.put("eventIdAndkpiCode", kInfo);
					bigEventService.insertRelativeEventKPI(DwpasCKpiInfoMap);
				}
			} else {
				bigEventService.deleteRelativeEventKPI(Integer
						.parseInt(eventId));
			}
			mav.addObject("msg", "success");
		} catch (Exception e) {
			logger.error("eventId=" + eventId + "大事记与指标关联建立失败", e);
			mav.addObject("msg", "failed");
			mav.addObject("exception", e);
			e.printStackTrace();
		}
		this.insertLog(request, "更新大事记与它关联的指标关系");
		return new ModelAndView("/common/save_result");
		// return new
		// ModelAndView("redirect:/BigEventRelKpi/showEventRelKpi"+eventId+".html");
	}
}
