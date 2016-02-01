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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.controller.BaseController;
import com.infosmart.po.DwpasCKpiInfo;
import com.infosmart.po.dwmis.DwmisKpiInfo;
import com.infosmart.service.dwmis.MisEventService;

/**
 * 大事记管理控制类
 * 
 * 
 */

@Controller
@RequestMapping("/MisEventRelKpi")
public class MisEventRelKpiController extends BaseController {

	@Autowired
	private MisEventService misEventService;

	/**
	 * 指标关联显示
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/showEventRelKpi{eventId}")
	public ModelAndView showEventRelKpi(@PathVariable String eventId,
			HttpServletRequest request, HttpServletResponse response,
			DwmisKpiInfo dwpasCKpiInfo) {
		ModelAndView mav = new ModelAndView();
		List<DwmisKpiInfo> kpiRelList = null;
		List<DwmisKpiInfo> kpiUnRelList = null;
		String title = null;
		String eventId1 = request.getParameter("eventId");
		try {
			kpiRelList = misEventService.getRelativeEventKPI(eventId);
			if (eventId1 != null && eventId1 != "") {
				kpiRelList = misEventService.getRelativeEventKPI(eventId1);
				kpiUnRelList = misEventService.getUnRelativeEventKPI(eventId1);
				title = misEventService.getMISEventById(eventId1).getTitle();
			} else {
				kpiRelList = misEventService.getRelativeEventKPI(eventId);
				kpiUnRelList = misEventService.getUnRelativeEventKPI(eventId);
				title = misEventService.getMISEventById(eventId).getTitle();
			}
		} catch (Exception e) {
			logger.error("大事记与指标关联页面出错", e);
			mav.addObject("exception", e);
			e.printStackTrace();
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
		mav.addObject("groupMap", groupMap);
		mav.addObject("kpiRelList", kpiRelList);
		mav.addObject("kpiUnRelList", kpiUnRelList);
		mav.addObject("title", title);
		mav.addObject("eventId", eventId);
		mav.setViewName("/dwmis/misEvent/eventRelKpi");
		return mav;
	}

	// 搜索不与此大事记绑定的kpi
	@RequestMapping(value = "/searchEventRelKpi")
	public void search(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String eventId = request.getParameter("eventId");
		String keyCode = request.getParameter("keyCode");
		if (null != keyCode && "" != keyCode) {
			keyCode = new String(keyCode.getBytes("ISO-8859-1"), "UTF-8");
		}
		// 与该部门不管关联的指标
		List<DwmisKpiInfo> kpiUnRelList = null;
		kpiUnRelList = misEventService.getUnRelativeEventKPIByKpiCodeOrKpiName(
				eventId, keyCode);
		List<DwmisKpiInfo> dwmisKpiInfoList = new ArrayList<DwmisKpiInfo>();
		DwmisKpiInfo dwmisKpiInfo = null;
		for (DwmisKpiInfo kpiInfo : kpiUnRelList) {
			dwmisKpiInfo = new DwmisKpiInfo();
			dwmisKpiInfo.setKpiCode(kpiInfo.getKpiCode());
			dwmisKpiInfo.setKpiName(kpiInfo.getKpiName());
			dwmisKpiInfo.setGoalType(kpiInfo.getGoalType());
			dwmisKpiInfo.setGoalTypeDesc(kpiInfo.getGoalTypeDesc());
			dwmisKpiInfoList.add(dwmisKpiInfo);
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

	/**
	 * 更新当前大事记与它关联的指标关系
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/updateKPIRelativeByKPICode", method = RequestMethod.POST)
	public ModelAndView updateKPIRelativeByKPICode(HttpServletRequest request,
			HttpServletResponse response) {
		String[] arrayList = request.getParameterValues("kpiRelInfo");
		String eventId = request.getParameter("eventId");
		Map DwpasCKpiInfoMap = new HashMap();
		ModelAndView mav = new ModelAndView();
		try {
			if (arrayList != null) {
				misEventService.deleteRelativeEventKPI(Integer
						.parseInt(eventId));
				for (String temp : arrayList) {
					DwpasCKpiInfo kInfo = new DwpasCKpiInfo();
					kInfo.setEventId(Integer.parseInt(eventId));
					kInfo.setKpiCode(temp);
					DwpasCKpiInfoMap.put("eventIdAndkpiCode", kInfo);
					misEventService.insertRelativeEventKPI(DwpasCKpiInfoMap);
				}
			} else {
				misEventService.deleteRelativeEventKPI(Integer
						.parseInt(eventId));
			}
			mav.addObject("msg", "success");
		} catch (Exception e) {
			logger.error("eventId=" + eventId + "大事记与指标关联建立失败", e);
			mav.addObject("msg", "failed");
			mav.addObject("exception", e);
		}
		return new ModelAndView("/common/save_result");
		// return new
		// ModelAndView("redirect:/MisEventRelKpi/showEventRelKpi"+eventId+".html");
	}

}
