package com.infosmart.service.dwmis.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.infosmart.po.DwpasCKpiInfo;
import com.infosmart.po.DwpasCSysType;
import com.infosmart.po.dwmis.DwmisKpiInfo;
import com.infosmart.po.dwmis.DwmisMisTypePo;
import com.infosmart.po.dwmis.MisEventPo;
import com.infosmart.service.dwmis.MisEventService;
import com.infosmart.service.impl.BaseServiceImpl;

/**
 * 
 * 大事记管理器实现类
 * 
 * 
 */
@Service
public class MisEventServiceImpl extends BaseServiceImpl implements
		MisEventService {
	/**
	 * 
	 * 获取大事记记录
	 * 
	 * @param MisEventPo
	 * @return
	 */
	@Override
	public List<MisEventPo> getlistPageAndBigEventPo(Object param) {
		return myBatisDao.getList(
				"com.infosmart.mapper.dwmis.MisEventPo.getALLMisEventlistPage",
				param);
	}

	/**
	 * 
	 * 批量删除大事记
	 * 
	 * @param ids
	 * @return
	 */
	@Override
	public void deleteEventMessageByIds(List<String> ids) {
		if (ids == null || ids.isEmpty()) {
			this.logger.warn("批量删除大事记失败:参数ids为空");
			return;
		}
		myBatisDao
				.delete("com.infosmart.mapper.dwmis.MisEventPo.deleteMisEventByEnentIds",
						ids);
	}

	/**
	 * 
	 * 删除一条大事记
	 * 
	 * @param ids
	 * @return
	 */
	@Override
	public void deleteEventMessageById(Serializable id) {
		if (id == null) {
			this.logger.warn("删除大事记失败:参数id为空");
			return;
		}
		myBatisDao
				.delete("com.infosmart.mapper.dwmis.MisEventPo.deleteMisEventByEnentId",
						id);
	}

	/**
	 * 根据ID获取对应的大事记记录
	 * 
	 * @see com.alipay.dwmis.biz.shared.MISEventManager#getMISEventById(int)
	 */
	@Override
	public MisEventPo getMISEventById(Serializable eventId) {
		MisEventPo eventBO = new MisEventPo();
		try {// com.infosmart.mapper.dwmis.MisEventPo.getMisEventById
			eventBO = myBatisDao.get(
					"com.infosmart.mapper.dwmis.MisEventPo.getMisEventById",
					eventId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BigEventServiceImpl.getMISEventById()访问数据库出错", e);
			return null;
		}
		return eventBO;
	}

	/**
	 * 修改相关大事记
	 * 
	 * @param mEventDO
	 * @throws Exception
	 */
	@Override
	public void updateEvent(MisEventPo MisEventPo) throws Exception {
		 if(MisEventPo == null){
			 this.logger.warn("更新大事记失败:参数MisEventPo为空");
			 return;
		 }
		try {
			myBatisDao.update(
					"com.infosmart.mapper.dwmis.MisEventPo.upMisEventByEnentId",
					MisEventPo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("eventId=" + MisEventPo.getEventId() + "的大事记记录更新失败", e);
			throw e;
		}
	}

	/**
	 * 插入相关大事记
	 * 
	 * @param mEventDO
	 * @throws Exception
	 */
	@Override
	public void insertEvent(MisEventPo mEventDO) throws Exception {
		if(mEventDO == null){
			this.logger.warn("插入大事记失败:参数MisEventPo为空");
			return;
		}
		try {
			myBatisDao.save(
					"com.infosmart.mapper.dwmis.MisEventPo.insertMisEvent",
					mEventDO);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("eventId=" + mEventDO.getEventId() + "的大事记记录插入失败", e);
			throw e;
		}
	}

	/**
	 * 获取此事记关联的指标
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<DwmisKpiInfo> getRelativeEventKPI(Serializable eventId)
			throws Exception {
		List<DwmisKpiInfo> kpiRelList = null;
		try {
			kpiRelList = myBatisDao
					.getList(
							"com.infosmart.mapper.dwmis.KpiEventRelevanceMapper.getRelativeEventKPI",
							eventId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BigEventServiceImpl.getRelativeEventKPI()访问数据库出错", e);
			return null;
		}
		return kpiRelList;

	}

	/**
	 * 获取此事记不关联的指标
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<DwmisKpiInfo> getUnRelativeEventKPI(String eventId) throws Exception {
		if(StringUtils.isBlank(eventId)){
			this.logger.warn("获取此事记不关联的指标失败：大事件Id为空");
			return null;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("eventId", eventId);
		List<DwmisKpiInfo> kpiUnRelList = null;
		try {
			kpiUnRelList = myBatisDao
					.getList(
							"com.infosmart.mapper.dwmis.KpiEventRelevanceMapper.getUnRelativeEventKPI",
							param);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BigEventServiceImpl.getRelativeEventKPI()访问数据库出错", e);
			return null;
		}
		return kpiUnRelList;
	}

	@Override
	public void deleteRelativeEventKPI(Serializable eventId) throws Exception {
		if(eventId == null){
			this.logger.warn("删除事记关联指标失败:参数eventId为空");
			return;
		}
		myBatisDao
				.delete("com.infosmart.mapper.dwmis.KpiEventRelevanceMapper.deleteRelativeEventKPI",
						eventId);

	}

	@Override
	public void insertRelativeEventKPI(Map DwpasCKpiInfoList) throws Exception {
		if(DwpasCKpiInfoList == null || DwpasCKpiInfoList.isEmpty()){
			this.logger.warn("插入事记关联指标失败:参数DwpasCKpiInfoList为空");
			return;
		}
		try {
			myBatisDao
					.save("com.infosmart.mapper.dwmis.KpiEventRelevanceMapper.insertRelativeEventKPI",
							DwpasCKpiInfoList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入关联指标信息出错", e);
			throw e;
		}

	}

	/**
	 * 指标数据趋势图中获得指标相关的大事件信息<br/>
	 * <br/>
	 * 根据指标编码和传入的信息条数n，返回n条指定指标的大事件信息列表。<br/>
	 * 所有系统异常均在dwmis中捕获并处理，返回size=0的List，不向接口外抛出异常。<br/>
	 * 但需要将异常打入日志中方便查看。<br/>
	 * 
	 * 1.根据kpiCode获得大事件的信息 <br/>
	 * 1.1如果num小于0，获得全部大事件的信息<br/>
	 * 1.2如果num大于0，获得N条最近大事件的信息<br/>
	 * 2. 遍历获得的大事件的信息<br/>
	 * 3.异常时，记录日志返回空对象<br/>
	 * 
	 * 
	 * @param kpiCode
	 *            指标编码
	 * @param num
	 *            返回信息条数,如果<0则返回全量数据
	 * @return List<EventInfo>大事件信息列表
	 * @author wb-yingpf
	 * 
	 */

	public List<MisEventPo> queryEventsByKpiCode(String kpiCode, int num) {
		if(StringUtils.isBlank(kpiCode)){
			this.logger.warn("查询大事件信息列表失败：指标Code为空！");
			return null;
		}
		ArrayList<MisEventPo> eventInfoList = new ArrayList<MisEventPo>();
		try {
			List<MisEventPo> misEventDOList = null;
			Map param = new HashMap();
			param.put("kpiCode", kpiCode);
			param.put("num", num);
			// 1.根据kpiCode获得大事件的信息
			if (num < 0) {
				// 1.1如果num小于0，获得全部大事件的信息

				misEventDOList = myBatisDao
						.getList(
								"com.infosmart.mapper.dwmis.MisEventPo.queryNewEventsByKpiCode",
								param);

			} else {
				// 1.2如果num大于0，获得N条最近大事件的信息
				misEventDOList = myBatisDao
						.getList(
								"com.infosmart.mapper.dwmis.MisEventPo.queryNewEventsByKpiCode",
								param);
			}

			return eventInfoList;
		} catch (Exception e) {
			e.printStackTrace();
			// 3.异常时，记录日志返回没有内容的列表对象
			logger.error("指标数据趋势图中获得指标相关的大事件信息错误，可能的原因：传入kpiCode错误或数据库记录有误", e);
			return eventInfoList;
		}
	}

	@Override
	public List<DwmisMisTypePo> getEventTypeName(Object param) {
		return myBatisDao.getList(
				"com.infosmart.mapper.dwmis.MisEventPo.getEventsTypeName",
				param);
	}


	@Override
	public List<DwmisMisTypePo> getEventsTypeNameById(Serializable id) {
		Map param = new HashMap();
		param.put("id", id);
		return myBatisDao.getList(
				"com.infosmart.mapper.dwmis.MisEventPo.getEventsTypeNameById", param);
	}
	
	@Override
	public List<DwmisKpiInfo> getUnRelativeEventKPIByKpiCodeOrKpiName(
			String id, String KeyCode) {
		if(StringUtils.isBlank(id)){
			this.logger.warn("查询大事记不关联指标信息失败：大事记Id为空");
			return null;
		}
		List<DwmisKpiInfo> dwmisKpiInfos = null;
		Map<String, String> map= new HashMap<String, String>();
		map.put("eventId", id);
		map.put("keyCode", KeyCode);
		dwmisKpiInfos = this.myBatisDao
				.getList(
						"com.infosmart.mapper.dwmis.KpiEventRelevanceMapper.getUnRelativeEventKPIByKpiCodeOrKpiName",
						map);
		return dwmisKpiInfos;
	}

	@Override
	public void deleteEventTypeById(Serializable typeId) throws Exception {
		if(typeId == null){
			this.logger.warn("删除事件类型失败：参数typeId为空");
			return;
		}
		myBatisDao
				.delete("com.infosmart.mapper.dwmis.MisEventPo.deleteEventTypeById",
						typeId);
		
	}

	@Override
	public DwmisMisTypePo getDwmisMisTypePoById(Serializable typeId) {
		
		return myBatisDao.get("com.infosmart.mapper.dwmis.MisEventPo.getDwmisMisTypePoById", typeId);
	}

	@Override
	public void updateEventType(DwmisMisTypePo dwmisMisTypePo) throws Exception {
		if(dwmisMisTypePo == null){
			this.logger.warn("修改维度信息从表列表失败：参数dwpasCSysType为空");
			return;
		}
		try {
			myBatisDao.update(
					"com.infosmart.mapper.dwmis.MisEventPo.updateEventType",
					dwmisMisTypePo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("typeId=" + dwmisMisTypePo.getTypeId()+ "的事件类型记录更新失败", e);
			throw e;
		}
		
	}

	@Override
	public void insertEventType(DwmisMisTypePo dwmisMisTypePo) throws Exception {
		if(dwmisMisTypePo == null){
			this.logger.warn("插入事件类型记录失败:参数dwpasCSysType为空");
			return;
		}
		try {
			myBatisDao.save("com.infosmart.mapper.dwmis.MisEventPo.insertEventType",
					dwmisMisTypePo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("typeId=" + dwmisMisTypePo.getTypeId() + "的事件类型记录插入失败", e);
			throw e;
		}
		
	}

	@Override
	public List<DwmisMisTypePo> checkTypeName(String typeName) {
		return this.myBatisDao.getList("com.infosmart.mapper.dwmis.MisEventPo.checkTypeName", typeName);
	}

}
