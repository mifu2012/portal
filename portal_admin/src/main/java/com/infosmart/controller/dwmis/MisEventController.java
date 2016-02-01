package com.infosmart.controller.dwmis;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.controller.BaseController;

import com.infosmart.po.DwpasCSysType;
import com.infosmart.po.dwmis.DwmisMisTypePo;
import com.infosmart.po.dwmis.DwmisSystemParamType;
import com.infosmart.po.dwmis.MisEventPo;
import com.infosmart.service.dwmis.DwmisSystemParamTypeService;
import com.infosmart.service.dwmis.MisEventService;

/**
 * 大事记管理控制类
 * 
 * 
 */

@Controller
@RequestMapping("/MisEvent")
public class MisEventController extends BaseController {

	@Autowired
	private MisEventService misEventService;
	@Autowired
	private DwmisSystemParamTypeService dwmisSystemParamTypeService;
	private final String SUCCESS_ACTION = "/common/save_result";
	private final Integer GROUP_ID = 7000;

	/**
	 * 所有大事记信息列表
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping
	public ModelAndView showEventMessage(HttpServletRequest request,
			HttpServletResponse response, MisEventPo misEventPo) {
		String title = misEventPo.getTitle();
		if (!"".equals(title) && title != null) {
			misEventPo.setTitle(title.trim());
		}
		DwmisMisTypePo dwpasCSysType = new DwmisMisTypePo();
		List<MisEventPo> misEventList = misEventService
				.getlistPageAndBigEventPo(misEventPo);
		List<DwmisMisTypePo> eventTypeList = misEventService
				.getEventTypeName(dwpasCSysType);
		/* 查询typename类型 hgt */
		List<DwmisSystemParamType> typeNameList = dwmisSystemParamTypeService
				.listSystemParamByGroupId(GROUP_ID);
		ModelAndView mav = new ModelAndView();
		mav.addObject("misEventList", misEventList);
		mav.addObject("eventTypeList", eventTypeList);
		mav.addObject("bigEventPo", misEventPo);
		mav.addObject("typeNameList", typeNameList);
		mav.setViewName("/dwmis/misEvent/event");
		// this.insertLog(request, "查询大事件信息");
		return mav;
	}

	/**
	 * 批量删除大事记信息
	 * 
	 * @return
	 */
	@RequestMapping("/deleteList")
	public ModelAndView batchDeleteEvent(HttpServletRequest request,
			HttpServletResponse response) {
		String idList = request.getParameter("idList");
		String[] ids = null;
		if (idList != null && idList.length() > 0) {
			ids = idList.split(",");
		}
		if (ids == null) {
			this.logger.warn("批量删除大事记时传递的ids为null");
		}
		if (ids != null && ids.length > 0) {
			try {
				misEventService.deleteEventMessageByIds(Arrays.asList(ids));
				this.sendMsgToClient(isSuccess, response);
			} catch (Exception e) {
				this.sendMsgToClient(isFailed, response);
			}

		}
		// this.insertLog(request, "批量删除大事件信息");
		return new ModelAndView("redirect:/MisEvent.html");
	}

	/**
	 * 删除大事记信息
	 * 
	 * @return
	 */

	@RequestMapping(value = "/delete{id}")
	public void deleteEvent(@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response, PrintWriter out) {
		try {
			misEventService.deleteEventMessageById(id);
			this.sendMsgToClient(isSuccess, response);
		} catch (Exception e) {
			this.sendMsgToClient(isFailed, response);
		}

		out.close();

	}

	/**
	 * 修改大事记信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/editEvent{id}")
	public ModelAndView editEvent(@PathVariable Integer id,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MisEventPo misEventPo = misEventService.getMISEventById(id);
		List<DwmisMisTypePo> eventTypeList = misEventService
				.getEventsTypeNameById(misEventPo.getEventId());
		ModelAndView mav = new ModelAndView();
		mav.addObject("eventTypeList", eventTypeList);
		mav.addObject("mEventInfo", misEventPo);
		mav.setViewName("/dwmis/misEvent/eventInfo");
		// this.insertLog(request, "修改大事记信息");
		return mav;

	}

	/**
	 * 添加大事记信息跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addEvent")
	public ModelAndView addEvent(HttpServletRequest request,
			HttpServletResponse response, MisEventPo misEventPo)
			throws Exception {
		// this.insertLog(request, "添加大事记信息");
		DwmisMisTypePo dwpasCSysType = new DwmisMisTypePo();
		List<DwmisMisTypePo> eventTypeList = misEventService
				.getEventTypeName(dwpasCSysType);
		ModelAndView mav = new ModelAndView();
		mav.addObject("eventTypeList", eventTypeList);
		mav.setViewName("/dwmis/misEvent/eventInfo");
		return mav;
	}

	/**
	 * 保存修改或者添加大事件
	 * 
	 * 
	 * */
	@RequestMapping(value = "/saveEvent", method = RequestMethod.POST)
	public ModelAndView saveEvent(MisEventPo misEventPo) throws Exception {
		ModelAndView mav = new ModelAndView();
		if (misEventPo == null) {
			this.logger.warn("添加大事记时传递的MisEventPo对象为nul");
		}
		if (misEventPo != null) {
			if (misEventService.getMISEventById(misEventPo.getEventId()) == null) {
				if (misEventService.getMISEventById(misEventPo.getEventId()) != null) {
					mav.addObject("msg", "failed");
				} else {
					try {
						misEventService.insertEvent(misEventPo);
						mav.addObject("msg", "success");
					} catch (Exception e) {
						mav.addObject("msg", "failed");
					}

				}
			} else {
				MisEventPo newbigEventPo = misEventService
						.getMISEventById(misEventPo.getEventId());
				BeanUtils.copyProperties(misEventPo, newbigEventPo);
				try {
					misEventService.updateEvent(newbigEventPo);
					mav.addObject("msg", "success");
				} catch (Exception e) {
					mav.addObject("msg", "failed");
				}

			}
		}
		// this.insertLog(request, "添加大事记信息");
		mav.setViewName(SUCCESS_ACTION);
		return mav;
	}

	/**
	 * 验证类型名称是否已存在 zy
	 * 
	 * @param DwpasCSysType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkTypeName")
	public void checkTypeName(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String typeName = request.getParameter("typeName");
		List<DwmisMisTypePo> eventTypeList = null;
		if (typeName != null && !"".equals(typeName)) {
			typeName = new String(typeName.getBytes("ISO-8859-1"), "UTF-8");
			eventTypeList = misEventService.checkTypeName(typeName);
		}
		if (eventTypeList.size() <=0) {
			this.sendMsgToClient("success", response);
		} else {
			this.sendMsgToClient("failed", response);
		}
	}

	/**
	 * 事件类型维护
	 * 
	 * */
	@RequestMapping("/showEventType")
	public ModelAndView editEventType(HttpServletResponse response)
			throws Exception {
		DwmisMisTypePo dwpasCSysType = new DwmisMisTypePo();
		List<DwmisMisTypePo> eventTypeList = misEventService
				.getEventTypeName(dwpasCSysType);
		ModelAndView mav = new ModelAndView();
		mav.addObject("eventTypeList", eventTypeList);
		mav.setViewName("/dwmis/misEvent/eventTypeShow");
		return mav;
	}

	/**
	 * 删除事件类型
	 * 
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteEventType{id}")
	public void deleteDimension(@PathVariable Integer id, PrintWriter out)
			throws Exception {
		if (id == null) {
			this.logger.warn("删除事件类型时传递的id为null");
		}
		try {
			misEventService.deleteEventTypeById(id);
			out.write("success");
		} catch (Exception e) {
			this.logger.error("删除事件类型失败：" + e.getMessage(), e);
			out.write(this.isFailed);
		}

		out.close();
	}

	/**
	 * 添加事件类型跳转
	 * 
	 * @param DwmisMisTypePo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addEventType")
	public ModelAndView addDimension(DwmisMisTypePo dwmisMisTypePo)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		List<DwmisMisTypePo> eventTypeList = null;
		eventTypeList = misEventService.getEventTypeName(dwmisMisTypePo);
		Integer typeId = null;
		// 类型ID 是主键 满足大事记的类型组为7000 类型ID在7000到8000之间
		// 如果添加时满足条件的List为空 默认类型ID为7001 否则取类型ID最大的+1
		if (eventTypeList == null) {
			typeId = 7001;
		} else {
			List<Integer> typeIdList = new ArrayList<Integer>();
			for (DwmisMisTypePo sysType : eventTypeList) {
				typeId = sysType.getTypeId();
				typeIdList.add(typeId);
			}
			typeId = Collections.max(typeIdList) + 1;
		}
		mav.addObject("typeId", typeId);
		mav.addObject("eventType", "add");
		mav.setViewName("/dwmis/misEvent/eventTypeInfo");
		return mav;
	}

	/**
	 * 修改事件类型跳转
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editEventType{id}")
	public ModelAndView editDimension(@PathVariable Integer id)
			throws Exception {
		if (id == null) {
			this.logger.warn("Dimension的id为null");
		}
		DwmisMisTypePo dwpasCSysTypeById = misEventService
				.getDwmisMisTypePoById(id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("dwpasCSysType", dwpasCSysTypeById);
		mav.addObject("eventType", "select");
		mav.setViewName("/dwmis/misEvent/eventTypeInfo");
		return mav;
	}

	/**
	 * 保存事件类型的添加或者修改
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveEventType", method = RequestMethod.POST)
	public ModelAndView saveEventType(HttpServletRequest request,
			HttpServletResponse response, DwmisMisTypePo dwmisMisTypePo)
			throws Exception {
		if (dwmisMisTypePo == null) {
			this.logger.warn("dwpasCSysType为null");
		}
		ModelAndView mav = new ModelAndView();
		if (dwmisMisTypePo != null) {
			if (misEventService.getDwmisMisTypePoById(dwmisMisTypePo
					.getTypeId()) == null) {
				if (misEventService.getDwmisMisTypePoById(dwmisMisTypePo
						.getTypeId()) != null) {
					mav.addObject("msg", "failed");
				} else {
					try {
						misEventService.insertEventType(dwmisMisTypePo);
						mav.addObject("msg", "success");
					} catch (Exception e) {
						mav.addObject("msg", "failed");
					}

				}
			} else {
				DwmisMisTypePo eventTypeMessage = misEventService
						.getDwmisMisTypePoById(dwmisMisTypePo.getTypeId());
				BeanUtils.copyProperties(dwmisMisTypePo, eventTypeMessage);
				try {
					misEventService.updateEventType(dwmisMisTypePo);
					mav.addObject("msg", "success");
				} catch (Exception e) {
					mav.addObject("msg", "failed");
				}

			}
		}
		return new ModelAndView("/common/save_result");
	}

}
