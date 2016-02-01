package com.infosmart.portal.service.dwmis;

import java.util.List;
import java.util.Map;

import com.infosmart.portal.pojo.dwmis.DwmisMisTypePo;
import com.infosmart.portal.pojo.dwmis.MisEventPo;

public interface MisEventService {

	public List<MisEventPo> getEventMassage(MisEventPo misEventPo);

	/**
	 * 根据事件Id查询大事件信息
	 * 
	 * @param eventId
	 * @return
	 */
	public MisEventPo getEventMessageById(String eventId);

	public List<DwmisMisTypePo> getEventTypeName(Object param);

	public List<MisEventPo> getEventsByKpiCode(String EventSearchKey,
			String kpiCode, String eventType);

	/**
	 * 列出多个指标关联的大事件<kpiCode,eventList>
	 * 
	 * @param EventSearchKey
	 * @param kpiCodeList
	 * @param eventType
	 * @return
	 */
	Map<String, List<MisEventPo>> listEventByCodes(String EventSearchKey,
			List<String> kpiCodeList, String eventType);
}
