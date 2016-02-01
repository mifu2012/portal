package com.infosmart.portal.action.dwmis;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.portal.action.BaseController;
import com.infosmart.portal.pojo.dwmis.DwmisMisTypePo;
import com.infosmart.portal.pojo.dwmis.MisEventPo;
import com.infosmart.portal.service.dwmis.MisEventService;
import com.infosmart.portal.util.StringUtils;

@Controller
@RequestMapping("/MisEvent")
public class MisEventController extends BaseController {
	@Autowired
	private MisEventService misEventService;

	@RequestMapping("/showEventMessage")
	public ModelAndView showEventMessage(HttpServletRequest request,
			MisEventPo misEventPo) {
		DwmisMisTypePo dwmisMisTypePo = new DwmisMisTypePo();
		ModelAndView mav = new ModelAndView();
		// 查询日期
		String queryDate = request.getParameter("queryDate");
		if (StringUtils.notNullAndSpace(queryDate)) {
			this.setCrtQueryDateOfDwmis(request, queryDate);
		} else {
			queryDate = this.getCrtQueryDateOfDwmis(request);
		}
		List<MisEventPo> eventList = misEventService
				.getEventMassage(misEventPo);
		List<DwmisMisTypePo> eventTypeList = misEventService
				.getEventTypeName(dwmisMisTypePo);

		String MisKpiCodes = request.getSession().getServletContext()
				.getInitParameter("MisKpiCodes");

		mav.addObject("misEventPo", misEventPo);
		mav.addObject("queryDate", queryDate);
		mav.addObject("eventList", eventList);
		mav.addObject("eventTypeList", eventTypeList);
		mav.addObject("MisKpiCodes", MisKpiCodes);
		mav.setViewName("/dwmis/MisEvent/MisEvent");
		return mav;
	}

	@RequestMapping("/showEventMessageByEventId")
	public void search(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String eventId = request.getParameter("eventId");
		// 根据eventId 查询大事记信息
		MisEventPo misEvent = misEventService.getEventMessageById(eventId);
		// List对象转json
		this.sendJsonMsgToClient(misEvent, response);
	}

}
