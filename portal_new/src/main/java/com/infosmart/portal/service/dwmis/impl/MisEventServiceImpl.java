package com.infosmart.portal.service.dwmis.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.dwmis.DwmisMisTypePo;
import com.infosmart.portal.pojo.dwmis.MisEventPo;
import com.infosmart.portal.service.dwmis.MisEventService;
import com.infosmart.portal.service.impl.BaseServiceImpl;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.Constants;

@Service
public class MisEventServiceImpl extends BaseServiceImpl implements
		MisEventService {

	@Override
	public List<MisEventPo> getEventMassage(MisEventPo misEventPo) {
		return myBatisDao.getList(
				"com.infosmart.mapper.MisEventPo.getEventsMessagelistPage",
				misEventPo);
	}

	@Override
	public List<DwmisMisTypePo> getEventTypeName(Object param) {
		return myBatisDao.getList(
				"com.infosmart.mapper.MisEventPo.getEventsTypeName", param);
	}

	@Override
	public List<MisEventPo> getEventsByKpiCode(String title, String kpiCode,
			String eventType) {

		List<MisEventPo> eventInfoList = new ArrayList<MisEventPo>();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("kpiCode", kpiCode);
			param.put("title", title);
			param.put("eventType", eventType);

			eventInfoList = myBatisDao
					.getList(
							"com.infosmart.mapper.MisEventPo.getEventsByKpiCode",
							param);

			return eventInfoList;
		} catch (Exception e) {
			// 3.异常时，记录日志返回没有内容的列表对象
			logger.error("指标数据趋势图中获得指标相关的大事件信息错误，可能的原因：传入kpiCode错误或数据库记录有误", e);
			return eventInfoList;
		}
	}

	@Override
	public Map<String, List<MisEventPo>> listEventByCodes(
			String EventSearchKey, List<String> kpiCodeList, String eventType) {
		this.logger.info("查找多指标关联的大事件");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("kpiCodes", kpiCodeList);
		param.put("title", EventSearchKey);
		param.put("eventType", eventType);
		List<MisEventPo> eventInfoList = this.myBatisDao.getList(
				"com.infosmart.mapper.MisEventPo.listEventByKpiCodes", param);
		if (eventInfoList == null || eventInfoList.isEmpty()) {
			return null;
		}
		Map<String, List<MisEventPo>> eventMap = new HashMap<String, List<MisEventPo>>();
		List<MisEventPo> eventList = null;
		Map<String, String> lineColorMap = new HashMap<String, String>();
		for (MisEventPo event : eventInfoList) {
			if (!lineColorMap.containsKey(event.getEventType())) {
				lineColorMap.put(event.getEventType(),
						Constants.CHART_COLOR_LIST.get(lineColorMap.size()));
			}
			event.setEventColor(lineColorMap.get(event.getEventType()));
			if (eventMap.containsKey(event.getKpiCode())) {
				eventMap.get(event.getKpiCode()).add(event);
			} else {
				eventList = new ArrayList<MisEventPo>();
				eventList.add(event);
				eventMap.put(event.getKpiCode(), eventList);
			}
		}
		return eventMap;
	}

	@Override
	public MisEventPo getEventMessageById(String eventId) {
		if (!StringUtils.notNullAndSpace(eventId)) {
			this.logger.warn("根据事件Id查询大事件信息失败：eventId为空!");
			return null;
		}
		return myBatisDao.get(
				"com.infosmart.mapper.MisEventPo.getEventMessageById",
				Integer.valueOf(eventId));
	}

}
