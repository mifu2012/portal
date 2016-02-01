package com.infosmart.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.infosmart.po.BigEventPo;
import com.infosmart.po.DwpasCKpiInfo;
import com.infosmart.po.DwpasCSysType;
import com.infosmart.po.dwmis.DwmisKpiInfo;
import com.infosmart.service.BigEventService;

/**
 * 
 * 大事记管理器实现类
 * 
 * 
 */
@Service
public class BigEventServiceImpl extends BaseServiceImpl implements
		BigEventService {
	/**
	 * 
	 * 获取大事记记录
	 * 
	 * @param MisEventPo
	 * @return
	 */
	@Override
	public List<BigEventPo> getlistPageAndBigEventPo(Object param) {
		return myBatisDao
				.getList(
						"com.infosmart.mapper.BigEventPo.getALLMisEventlistPage",
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
	public void deleteEventMessageByIds(List<String> ids) throws Exception {
		if (ids == null || ids.isEmpty()) {
			this.logger.warn("批量删除大事记失败:参数大事记id列表为空");
			throw new Exception("批量删除大事记失败:参数大事记id列表为空");
		}
		myBatisDao
				.delete("com.infosmart.mapper.BigEventPo.deleteMisEventByEnentIds",
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
	public void deleteEventMessageById(Serializable id) throws Exception {
		if (id == null) {
			this.logger.warn("删除大事记失败:参数大事记id为空");
			throw new Exception("删除大事记失败:参数大事记id为空");
		}
		myBatisDao.delete(
				"com.infosmart.mapper.BigEventPo.deleteMisEventByEnentId", id);
	}

	/**
	 * 根据ID获取对应的大事记记录
	 * 
	 * @see com.alipay.dwmis.biz.shared.MISEventManager#getMISEventById(int)
	 */
	@Override
	public BigEventPo getMISEventById(Serializable eventId) {
		if (eventId == null) {
			this.logger.warn("根据ID获取对应的大事记记录：参数为空");
			return null;
		}
		BigEventPo eventBO = new BigEventPo();
		try {// com.infosmart.mapper.BigEventPo.getMisEventById
			eventBO = myBatisDao.get(
					"com.infosmart.mapper.BigEventPo.getMisEventById", eventId);
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
	public void updateEvent(BigEventPo bigEventPo) throws Exception {
		if (bigEventPo == null) {
			this.logger.warn("修改大事记失败:参数bigEventPo为空");
			throw new Exception("修改大事记失败:参数bigEventPo为空");
		}
		try {
			myBatisDao.update(
					"com.infosmart.mapper.BigEventPo.upMisEventByEnentId",
					bigEventPo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("eventId=" + bigEventPo.getEventId() + "的大事记记录更新失败", e);
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
	public void insertEvent(BigEventPo mEventDO) throws Exception {
		if (mEventDO == null) {
			this.logger.warn("插入相关大事记失败：参数mEventDo为空");
			throw new Exception("插入相关大事记失败：参数mEventDo为空");
		}
		try {
			myBatisDao.save("com.infosmart.mapper.BigEventPo.insertMisEvent",
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
	public List<DwpasCKpiInfo> getRelativeEventKPI(Serializable eventId)
			throws Exception {
		List<DwpasCKpiInfo> kpiRelList = null;
		try {
			kpiRelList = myBatisDao
					.getList(
							"com.infosmart.mapper.KpiEventRelevanceMapper.getRelativeEventKPI",
							eventId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BigEventServiceImpl.getRelativeEventKPI()访问数据库出错", e);
			return null;
		}
		return kpiRelList;
	}

	/**
	 * 根据kpiType查询指标信息
	 */
	@Override
	public List<DwpasCKpiInfo> listAllKpiInfoByKpiType(String kpiType) {
		List<DwpasCKpiInfo> kpiRelList = null;
		try {
			kpiRelList = myBatisDao
					.getList(
							"com.infosmart.mapper.KpiEventRelevanceMapper.listAllKpiInfoByKpiType",
							kpiType);
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
	public List<DwpasCKpiInfo> getUnRelativeEventKPI(String eventId)
			throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("eventId", eventId);
		List<DwpasCKpiInfo> kpiUnRelList = null;
		long time = System.currentTimeMillis();
		try {
			kpiUnRelList = myBatisDao
					.getList(
							"com.infosmart.mapper.KpiEventRelevanceMapper.getUnRelativeEventKPI",
							param);
			this.logger.info("shijia---------------"
					+ String.valueOf(System.currentTimeMillis() - time));
			time = System.currentTimeMillis();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BigEventServiceImpl.getRelativeEventKPI()访问数据库出错", e);
			return null;
		}
		return kpiUnRelList;
	}

	@Override
	public void deleteRelativeEventKPI(Serializable eventId) throws Exception {
		if (eventId == null) {
			this.logger.warn("删除此事记相关指标失败：参数eventId为空");
			throw new Exception("删除此事记相关指标失败：参数eventId为空");
		}
		myBatisDao
				.delete("com.infosmart.mapper.KpiEventRelevanceMapper.deleteRelativeEventKPI",
						eventId);

	}

	@Override
	public void insertRelativeEventKPI(Map DwpasCKpiInfoList) throws Exception {
		if (DwpasCKpiInfoList == null || DwpasCKpiInfoList.isEmpty()) {
			this.logger.warn("插入此事记关联指标失败：参数DwpasCKpiInfoList为空");
			throw new Exception("插入此事记关联指标失败：参数DwpasCKpiInfoList为空");
		}
		try {
			myBatisDao
					.save("com.infosmart.mapper.KpiEventRelevanceMapper.insertRelativeEventKPI",
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

	public List<BigEventPo> queryEventsByKpiCode(String kpiCode, int num) {

		ArrayList<BigEventPo> eventInfoList = new ArrayList<BigEventPo>();
		try {
			List<BigEventPo> misEventDOList = null;
			Map param = new HashMap();
			param.put("kpiCode", kpiCode);
			param.put("num", num);
			// 1.根据kpiCode获得大事件的信息
			if (num < 0) {
				// 1.1如果num小于0，获得全部大事件的信息

				misEventDOList = myBatisDao
						.getList(
								"com.infosmart.mapper.BigEventPo.queryNewEventsByKpiCode",
								param);

			} else {
				// 1.2如果num大于0，获得N条最近大事件的信息
				misEventDOList = myBatisDao
						.getList(
								"com.infosmart.mapper.BigEventPo.queryNewEventsByKpiCode",
								param);
			}

			return eventInfoList;
		} catch (Exception e) {
			e.printStackTrace();
			// 3.异常时，记录日志返回没有内容的列表对象
			logger.error("指标数据趋势图中获得指标相关的大事件信息错误，可能的原因：传入kpiCode错误或数据库记录有误:"
					+ e.getMessage(), e);
			return eventInfoList;
		}
	}

	@Override
	public List<DwpasCSysType> getEventTypeName(Object param) {
		if (param == null) {
			this.logger.warn("查询失败，参数为null！");
			return null;
		}
		return myBatisDao.getList(
				"com.infosmart.mapper.BigEventPo.getEventsTypeName", param);
	}

	@Override
	public List<DwpasCSysType> getEventsTypeNameById(Serializable id) {
		Map param = new HashMap();
		param.put("id", id);
		return myBatisDao.getList(
				"com.infosmart.mapper.BigEventPo.getEventsTypeNameById", param);
	}

	@Override
	public List<DwpasCKpiInfo> getUnRelativeEventKPIByKpiCodeOrKpiName(
			String linkedKpiCode, String KeyCode, String kpiType) {
		/*
		 * if (StringUtils.isBlank(id)) {
		 * this.logger.warn("查询大事记不关联指标信息失败：大事记Id为空"); return null; }
		 */
		String[] str = null;
		if (!StringUtils.isBlank(linkedKpiCode)) {
			str = linkedKpiCode.split(",");
		}
		List<String> kpiCodesList = new ArrayList<String>();
		if (str != null && str.length > 0) {
			for (int i = 0; i < str.length; i++) {
				kpiCodesList.add(str[i].trim());
			}
		}else {
			kpiCodesList.add("");
		}
		List<DwpasCKpiInfo> dwmisKpiInfos = null;
		Map map = new HashMap();
		// map.put("eventId", id);
		map.put("keyCode", KeyCode);
		map.put("kpiCodesList", kpiCodesList);
		map.put("kpiType", kpiType);
		dwmisKpiInfos = this.myBatisDao
				.getList(
						"com.infosmart.mapper.KpiEventRelevanceMapper.getUnRelativeEventKPIByKpiCodeOrKpiName",
						map);
		return dwmisKpiInfos;

	}

	@Override
	public void deleteEventTypeById(Serializable typeId) throws Exception {
		if (typeId == null) {
			this.logger.warn("删除事件类型失败：参数typeId为空");
			throw new Exception("删除事件类型失败：参数typeId为空");
		}
		myBatisDao.delete(
				"com.infosmart.mapper.BigEventPo.deleteEventTypeById", typeId);
	}

	@Override
	public DwpasCSysType getDwpasCSysTypeById(Serializable typeId) {

		return myBatisDao.get(
				"com.infosmart.mapper.BigEventPo.getDwpasCSysTypeById", typeId);
	}

	@Override
	public void updateEventType(DwpasCSysType dwpasCSysType) throws Exception {
		if (dwpasCSysType == null) {
			this.logger.warn("修改维度信息从表列表失败：参数dwpasCSysType为空");
			throw new Exception("修改维度信息从表列表失败：参数dwpasCSysType为空");
		}
		try {
			myBatisDao.update(
					"com.infosmart.mapper.BigEventPo.updateEventType",
					dwpasCSysType);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("typeId=" + dwpasCSysType.getTypeId() + "的事件类型记录更新失败",
					e);
			throw e;
		}

	}

	@Override
	public void insertEventType(DwpasCSysType dwpasCSysType) throws Exception {
		if (dwpasCSysType == null) {
			this.logger.warn("插入事件类型记录失败:参数dwpasCSysType为空");
			throw new Exception("插入事件类型记录失败:参数dwpasCSysType为空");
		}
		try {
			myBatisDao.save("com.infosmart.mapper.BigEventPo.insertEventType",
					dwpasCSysType);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("typeId=" + dwpasCSysType.getTypeId() + "的事件类型记录插入失败",
					e);
			throw e;
		}

	}

	/**
	 * 验证类型名称是否已存在
	 * 
	 * @param DwpasCSysType
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<DwpasCSysType> checkTypeName(String typeName) {
		return this.myBatisDao.getList(
				"com.infosmart.mapper.BigEventPo.checkTypeName", typeName);
	}

	@Override
	public List<DwpasCKpiInfo> getUnRelKpiInfoList(String eventId){
		if(StringUtils.isBlank(eventId)){
			return null;
		}
		return this.myBatisDao.getList("com.infosmart.mapper.KpiEventRelevanceMapper.getUnRelKpiInfoList", eventId);
	}
}
