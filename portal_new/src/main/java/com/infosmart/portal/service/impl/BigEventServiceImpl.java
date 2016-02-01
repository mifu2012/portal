package com.infosmart.portal.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.BigEventPo;
import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCSysParam;
import com.infosmart.portal.service.BigEventService;
import com.infosmart.portal.util.StringUtils;

/**
 * 
 * 大事记管理器实现类
 * 
 * 
 */
@Service
public class BigEventServiceImpl extends BaseServiceImpl implements
		BigEventService {

	@Override
	public List<BigEventPo> listLatelyBigEventAndRelateKpiInfo(int count,
			String queryDate) {
		Map map = new HashMap();
		map.put("num", count);
		map.put("queryDate", queryDate);
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.BigEventPo.listLatelyBigEventAndRelateKpiInfo",
						map);
	}

	@Override
	public List<BigEventPo> listLatelyBigEventAndRelateKpiInfoById(
			Integer eventId) {
		Map map = new HashMap();
		map.put("eventId", eventId);
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.BigEventPo.listLatelyBigEventAndRelateKpiInfoById",
						map);
	}

	/**
	 * 
	 * 获取大事记记录
	 * 
	 * @param BigEventPo
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
	public void deleteEventMessageByIds(List<Long> ids) {
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
	public void deleteEventMessageById(Serializable id) {
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
		BigEventPo eventBO = new BigEventPo();
		try {// com.infosmart.mapper.BigEventPo.getMisEventById
			eventBO = myBatisDao.get(
					"com.infosmart.mapper.BigEventPo.getMisEventById", eventId);
		} catch (Exception e) {
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
		try {
			myBatisDao.update(
					"com.infosmart.mapper.BigEventPo.upMisEventByEnentId",
					bigEventPo);
		} catch (Exception e) {
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
		try {
			myBatisDao.save("com.infosmart.mapper.BigEventPo.insertMisEvent",
					mEventDO);
		} catch (Exception e) {
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
	public List<DwpasCKpiInfo> getUnRelativeEventKPI(Serializable eventId)
			throws Exception {
		List<DwpasCKpiInfo> kpiUnRelList = null;
		try {
			kpiUnRelList = myBatisDao
					.getList(
							"com.infosmart.mapper.KpiEventRelevanceMapper.getUnRelativeEventKPI",
							eventId);
		} catch (Exception e) {
			logger.error("BigEventServiceImpl.getRelativeEventKPI()访问数据库出错", e);

			return null;
		}
		return kpiUnRelList;
	}

	@Override
	public void deleteRelativeEventKPI(Serializable eventId) throws Exception {
		myBatisDao
				.delete("com.infosmart.mapper.KpiEventRelevanceMapper.deleteRelativeEventKPI",
						eventId);

	}

	@Override
	public void insertRelativeEventKPI(Map<?, ?> DwpasCKpiInfoList)
			throws Exception {
		try {
			myBatisDao
					.save("com.infosmart.mapper.KpiEventRelevanceMapper.insertRelativeEventKPI",
							DwpasCKpiInfoList);
		} catch (Exception e) {
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

		List<BigEventPo> eventInfoList = new ArrayList<BigEventPo>();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("kpiCode", kpiCode);
			param.put("num", num);
			// 1.根据kpiCode获得大事件的信息
			if (num < 0) {
				// 1.1如果num小于0，获得全部大事件的信息

				eventInfoList = myBatisDao
						.getList(
								"com.infosmart.mapper.BigEventPo.queryNewEventsByKpiCode",
								param);

			} else {
				// 1.2如果num大于0，获得N条最近大事件的信息
				eventInfoList = myBatisDao
						.getList(
								"com.infosmart.mapper.BigEventPo.queryNewEventsByKpiCode",
								param);
			}

			return eventInfoList;
		} catch (Exception e) {
			// 3.异常时，记录日志返回没有内容的列表对象
			logger.error("指标数据趋势图中获得指标相关的大事件信息错误，可能的原因：传入kpiCode错误或数据库记录有误", e);
			return eventInfoList;
		}
	}

	@Override
	public List<BigEventPo> listEventByCodes(
			String EventSearchKey, List<String> kpiCodeList, String eventType,
			String isPrd) {
		this.logger.info("查找多指标关联的大事件："+isPrd);
		Map<String, Object> param = new HashMap<String, Object>();
		if(kpiCodeList!=null){
			param.put("kpiCodes", kpiCodeList);
		}else{
			param.put("kpiCodes",null);
		}
		param.put("title", EventSearchKey);
		param.put("eventType", eventType);
		List<BigEventPo> eventInfoList = this.myBatisDao.getList(
				"com.infosmart.mapper.BigEventPo.listEventByKpiCodes", param);
		if (eventInfoList == null || eventInfoList.isEmpty()) {
			return  new  ArrayList<BigEventPo>();
		}
//		Map<String, List<BigEventPo>> eventMap = new HashMap<String, List<BigEventPo>>();
//		List<BigEventPo> eventList = null;
//		for (BigEventPo event : eventInfoList) {
//			if (eventMap.containsKey(event.getKpiCode())) {
//				eventMap.get(event.getKpiCode()).add(event);
//			} else {
//				eventList = new ArrayList<BigEventPo>();
//				eventList.add(event);
//				eventMap.put(event.getKpiCode(), eventList);
//			}
//		}
		return eventInfoList;
	}

	/**
	 * 产品聚焦模块获得最近N条产品相关信息<br/>
	 * <br/>
	 * 根据来源为‘dwpas’和传入的信息条数n，返回n条产品相关的大事件信息列表。<br/>
	 * 所有系统异常均在dwmis中捕获并处理，返回size=0的List，不向接口外抛出异常。<br/>
	 * 但需要将异常打入日志中方便查看。<br/>
	 * 
	 * 1.根据来源获得大事件的信息 <br/>
	 * 1.1如果num小于0，获得全部大事件的信息<br/>
	 * 1.2如果num大于0，获得N条最近大事件的信息<br/>
	 * 2. 遍历获得的大事件的信息<br/>
	 * 3.异常时，记录日志返回空对象<br/>
	 * 
	 * @param source
	 *            大事件来源
	 * @param num
	 *            返回信息条数，如果<0则返回全量数据
	 * @return List<EventInfo>大事件信息列表
	 * @author wb-yingpf
	 * 
	 */
	@SuppressWarnings("unused")
	public List<BigEventPo> queryEventsByNum(int num) {
		List<BigEventPo> eventInfoList = new ArrayList<BigEventPo>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("num", num);
		try {
			// 1.根据来源获得大事件的信息
			if (num < 0) {
				// 1.1如果num小于0，获得全部大事件的信息
				eventInfoList = myBatisDao
						.getList("com.infosmart.mapper.BigEventPo.queryNewEventsByNum");
			} else {
				// 1.2如果num大于0，获得N条最近大事件的信息
				eventInfoList = myBatisDao.getList(
						"com.infosmart.mapper.BigEventPo.queryNewEventsByNum",
						param);
			}
			return eventInfoList;
		} catch (Exception e) {
			// 异常时，记录日志返回没有内容的列表对象
			logger.error("指标数据趋势图中获得指标相关的大事件信息错误，可能的原因：传入的来源错误或数据库记录有误", e);
		}

		return eventInfoList;

	}

	@Override
	public DwpasCSysParam qryOneByParamId(long paramId)
			throws DataAccessException {

		return myBatisDao.get(
				"com.infosmart.mapper.BigEventPo.querySysTypeById", paramId);
	}

	@Override
	public List<DwpasCKpiInfo> queryEventRelKpiCodeByNum(int num) {
		List<DwpasCKpiInfo> eventRelKpiCodeList = new ArrayList<DwpasCKpiInfo>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("num", num);
		try {
			// 1.根据来源获得大事件的信息
			if (num < 0) {
				// 1.1如果num小于0，获得全部大事件的信息
				eventRelKpiCodeList = myBatisDao
						.getList("com.infosmart.mapper.BigEventPo.queryEventRelKpiCodeByNum");
			} else {
				// 1.2如果num大于0，获得N条最近大事件的信息
				eventRelKpiCodeList = myBatisDao
						.getList(
								"com.infosmart.mapper.BigEventPo.queryEventRelKpiCodeByNum",
								param);
			}
			return eventRelKpiCodeList;
		} catch (Exception e) {
			// 异常时，记录日志返回没有内容的列表对象
			logger.error("指标数据趋势图中获得指标相关的大事件信息错误，可能的原因：传入的来源错误或数据库记录有误", e);
		}

		return eventRelKpiCodeList;
	}

}
