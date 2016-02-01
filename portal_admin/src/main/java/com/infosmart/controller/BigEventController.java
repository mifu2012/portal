package com.infosmart.controller;

import java.io.PrintWriter;
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
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.po.BigEventPo;
import com.infosmart.po.DwpasCSysType;
import com.infosmart.po.dwmis.DwmisSystemParamType;
import com.infosmart.service.BigEventService;
import com.infosmart.service.dwmis.DwmisSystemParamTypeService;

/**
 * 大事记管理控制类
 * 
 * 
 */

@Controller
@RequestMapping(value = "/BigEvent")
public class BigEventController extends BaseController {

	@Autowired
	private BigEventService bigEventService;
	@Autowired
	private DwmisSystemParamTypeService dwmisSystemParamTypeService;
	private final Integer GROUP_ID = 7000;

	/**
	 * 所有大事记信息列表
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping
	public ModelAndView showEventMessage(HttpServletRequest request,
			HttpServletResponse response, BigEventPo bigEventPo) {
		if(bigEventPo == null){
			this.logger.warn("查询失败，参数为空");
		}
		String title = bigEventPo.getTitle();
		if (!"".equals(title) && title != null) {
			bigEventPo.setTitle(title.trim());
		}
		DwpasCSysType dwpasCSysType = new DwpasCSysType();
		List<BigEventPo> misEventList = bigEventService
				.getlistPageAndBigEventPo(bigEventPo);
		List<DwpasCSysType> eventTypeList = bigEventService
				.getEventTypeName(dwpasCSysType);
		/* 查询typename类型 hgt */
		List<DwmisSystemParamType> typeNameList = dwmisSystemParamTypeService
				.listSystemParamByGroupId(GROUP_ID);
		ModelAndView mav = new ModelAndView();
		mav.addObject("misEventList", misEventList);
		mav.addObject("eventTypeList", eventTypeList);
		mav.addObject("bigEventPo", bigEventPo);
		mav.addObject("typeNameList", typeNameList);
		mav.setViewName("/bigEvent/event");
		this.insertLog(request, "查询大事件信息");
		return mav;
	}

	/**
	 * 批量删除大事记信息
	 * 
	 * @return
	 */
	@RequestMapping("/deleteList")
	public void batchDeleteEvent(HttpServletRequest request,
			HttpServletResponse response, PrintWriter out) {
		response.setContentType("text/html;charset=UTF-8");
		String ids = request.getParameter("idList");
		if(ids == null || ids.length()==0){
			this.logger.error("大事件批量删除失败，参数ids为空！");
			this.sendMsgToClient(isFailed, response);
			return;
		}
		String[] idList = null;
		if (ids != null && ids.length() > 0) {
			idList = ids.split(",");
		}
		if (idList != null && idList.length > 0) {
			try {
				bigEventService.deleteEventMessageByIds(Arrays.asList(idList));
				this.insertLog(request, "批量删除大事件信息");
				this.sendMsgToClient(isSuccess, response);

			} catch (Exception e) {

				e.printStackTrace();
				this.sendMsgToClient(isFailed, response);
				this.logger.error("批量删除大事记失败"+e.getMessage(),e);
			}

		}
	}

	/**
	 * 删除大事记信息
	 * 
	 * @return
	 */

	@RequestMapping(value = "/delete{id}")
	public void deleteEvent(@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response, PrintWriter out) {
		response.setContentType("text/html;charset=UTF-8");
		try {
			bigEventService.deleteEventMessageById(id);
			this.insertLog(request, "删除大事件信息");
			out.write("success");
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("删除大事记信息失败：" + e.getMessage(), e);
			out.write(this.isFailed);
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
		BigEventPo bigEventPo = bigEventService.getMISEventById(id);
		List<DwpasCSysType> eventTypeList = null;
		if (bigEventPo != null) {
			eventTypeList = bigEventService.getEventsTypeNameById(bigEventPo
					.getEventType());
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("eventTypeList", eventTypeList);
		mav.addObject("mEventInfo", bigEventPo);
		mav.setViewName("/bigEvent/eventInfo");
		return mav;

	}

	/**
	 * 添加大事记信息跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addEvent")
	public ModelAndView addEvent(HttpServletRequest request,
			HttpServletResponse response, BigEventPo bigEventPo)
			throws Exception {
		// this.insertLog(request, "添加大事记信息");
		DwpasCSysType dwpasCSysType = new DwpasCSysType();
		List<DwpasCSysType> eventTypeList = bigEventService
				.getEventTypeName(dwpasCSysType);
		ModelAndView mav = new ModelAndView();
		mav.addObject("eventTypeList", eventTypeList);
		mav.setViewName("/bigEvent/eventInfo");
		return mav;
	}

	/**
	 * 保存大事件
	 * 
	 * 
	 * */
	@RequestMapping(value = "/saveEvent", method = RequestMethod.POST)
	public ModelAndView saveEvent(HttpServletRequest request,
			BigEventPo bigEventPo) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(SUCCESS_ACTION);
		if (bigEventPo == null) {
			this.logger.warn("保存的大事记为空：saveEvent方法中参数大事记对象为null");
			mav.addObject("msg", "failed");
			return mav;
		}
		
		if (bigEventService.getMISEventById(bigEventPo.getEventId()) == null) {
			if (bigEventService.getMISEventById(bigEventPo.getEventId()) != null) {
				mav.addObject("msg", "failed");
			} else {
				try {
					bigEventService.insertEvent(bigEventPo);
					mav.addObject("msg", "success");
					this.insertLog(request, "新增大事件信息");
				} catch (Exception e) {

					e.printStackTrace();
					this.logger.error("新增大事件信息失败：" + e.getMessage(), e);
					mav.addObject("msg", "failed");
				}

			}
		} else {
			BigEventPo newbigEventPo = bigEventService
					.getMISEventById(bigEventPo.getEventId());
			BeanUtils.copyProperties(bigEventPo, newbigEventPo);
			try {
				bigEventService.updateEvent(newbigEventPo);
				this.insertLog(request, "修改大事件信息");
				mav.addObject("msg", "success");
			} catch (Exception e) {

				e.printStackTrace();
				this.logger.error("修改大事件信息失败：" + e.getMessage(), e);
				mav.addObject("msg", "failed");
			}

		}
		return mav;
	}

	/**
	 * 事件类型维护
	 * 
	 * */
	@RequestMapping("/showEventType")
	public ModelAndView editEventType(HttpServletResponse response)
			throws Exception {
		DwpasCSysType dwpasCSysType = new DwpasCSysType();
		List<DwpasCSysType> eventTypeList = bigEventService
				.getEventTypeName(dwpasCSysType);
		ModelAndView mav = new ModelAndView();
		mav.addObject("eventTypeList", eventTypeList);
		mav.setViewName("/bigEvent/eventTypeShow");
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
	public void deleteDimension(@PathVariable Integer id,
			HttpServletRequest request, HttpServletResponse response,
			PrintWriter out) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		if (id == null) {
			this.logger.warn("删除事件类型时传递的id为null");
			out.write(this.isFailed);
			return;
		}
		try {
			bigEventService.deleteEventTypeById(id);
			this.insertLog(request, "删除事件类型");
			out.write("success");
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("删除事件类型失败：" + e.getMessage(), e);
			out.write(this.isFailed);
		}
		out.close();
	}

	/**
	 * 添加事件类型跳转
	 * 
	 * @param DwpasCSysType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addEventType")
	public ModelAndView addDimension(DwpasCSysType dwpasCSysType)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		List<DwpasCSysType> eventTypeList = null;
		eventTypeList = bigEventService.getEventTypeName(dwpasCSysType);
		Integer typeId = null;
		// 类型ID 是主键 满足大事记的类型组为7000 类型ID在7000到8000之间
		// 如果添加时满足条件的List为空 默认类型ID为7001 否则取类型ID最大的+1
		if (eventTypeList == null) {
			typeId = 7001;
		} else {
			List<Integer> typeIdList = new ArrayList<Integer>();
			for (DwpasCSysType sysType : eventTypeList) {
				typeId = sysType.getTypeId();
				typeIdList.add(typeId);
			}
			typeId = Collections.max(typeIdList) + 1;
		}
		mav.addObject("typeId", typeId);
		mav.addObject("eventType", "add");
		mav.setViewName("/bigEvent/eventTypeInfo");
		return mav;
	}

	/**
	 * 验证类型名称是否已存在
	 * 
	 * @param DwpasCSysType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkTypeName")
	public void checkTypeName(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String typeName = request.getParameter("typeName");
		List<DwpasCSysType> eventTypeList = new ArrayList<DwpasCSysType>();
		if (typeName != null && !"".equals(typeName)) {
			typeName = new String(typeName.getBytes("ISO-8859-1"), "UTF-8");
			eventTypeList = bigEventService.checkTypeName(typeName);
		}
		if (eventTypeList.size() <= 0) {
			this.sendMsgToClient("success", response);
		} else {
			this.sendMsgToClient("failed", response);
		}
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
		DwpasCSysType dwpasCSysTypeById = bigEventService
				.getDwpasCSysTypeById(id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("dwpasCSysType", dwpasCSysTypeById);
		mav.addObject("eventType", "select");
		mav.setViewName("/bigEvent/eventTypeInfo");
		return mav;
	}

	/**
	 * 保存事件类型
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveEventType", method = RequestMethod.POST)
	public ModelAndView saveEventType(HttpServletRequest request,
			HttpServletResponse response, DwpasCSysType dwpasCSysType)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(SUCCESS_ACTION);
		if (dwpasCSysType == null) {
			this.logger.warn("dwpasCSysType为null");
			mav.addObject("msg", "failed");
			return mav;
		}
		
		if (dwpasCSysType != null) {
			if (bigEventService.getDwpasCSysTypeById(dwpasCSysType.getTypeId()) == null) {
				if (bigEventService.getDwpasCSysTypeById(dwpasCSysType
						.getTypeId()) != null) {
					mav.addObject("msg", "failed");
				} else {
					try {
						bigEventService.insertEventType(dwpasCSysType);
						this.insertLog(request, "新增事件类型");
						mav.addObject("msg", "success");
					} catch (Exception e) {

						mav.addObject("msg", "failed");
					}

				}
			} else {
				DwpasCSysType eventTypeMessage = bigEventService
						.getDwpasCSysTypeById(dwpasCSysType.getTypeId());
				BeanUtils.copyProperties(dwpasCSysType, eventTypeMessage);
				try {
					bigEventService.updateEventType(dwpasCSysType);
					this.insertLog(request, "修改事件类型");
					mav.addObject("msg", "success");
				} catch (Exception e) {

					mav.addObject("msg", "failed");
				}

			}
		}
		return mav;
	}

}
