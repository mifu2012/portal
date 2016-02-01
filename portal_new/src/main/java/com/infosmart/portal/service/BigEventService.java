package com.infosmart.portal.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.infosmart.portal.pojo.BigEventPo;
import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCSysParam;

public interface BigEventService {

	/**
	 * 列出最近几条大事件及其指标信息
	 * 
	 * @param count
	 * @return
	 */
	List<BigEventPo> listLatelyBigEventAndRelateKpiInfo(int count , String queryDate);

	/**
	 * 
	 * 获取大事记记录
	 * 
	 * @param BigEventPo
	 * @return
	 */
	public List<BigEventPo> getlistPageAndBigEventPo(Object param);

	/**
	 * 
	 * 批量删除大事记
	 * 
	 * @param ids
	 * @return
	 */
	public void deleteEventMessageByIds(List<Long> ids);

	/**
	 * 
	 * 根据ID获取对应的大事记记录
	 * 
	 * @param eventId
	 * @return
	 */
	public BigEventPo getMISEventById(Serializable eventId);

	/**
	 * 修改相关大事记
	 * 
	 * @param mEventDO
	 * @throws Exception
	 */
	public void updateEvent(BigEventPo mEventDO) throws Exception;

	/**
	 * 插入相关大事记
	 * 
	 * @param mEventDO
	 * @throws Exception
	 */
	public void insertEvent(BigEventPo mEventDO) throws Exception;

	/**
	 * 
	 * 删除一条大事记
	 * 
	 * @param ids
	 * @return
	 */
	public void deleteEventMessageById(Serializable id);

	/**
	 * 获取此事记关联的指标
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	public List<DwpasCKpiInfo> getRelativeEventKPI(Serializable id)
			throws Exception;

	/**
	 * 获取此事记不关联的指标
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	public List<DwpasCKpiInfo> getUnRelativeEventKPI(Serializable id)
			throws Exception;

	/**
	 * 删除此事记关联的指标
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	public void deleteRelativeEventKPI(Serializable eventId) throws Exception;

	/**
	 * 插入此事记关联的指标
	 * 
	 * @param kList
	 * @return
	 * @throws Exception
	 */
	public void insertRelativeEventKPI(Map<?, ?> DwpasCKpiInfoList)
			throws Exception;

	public List<BigEventPo> queryEventsByKpiCode(String kpiCode, int num);

	/**
	 * 列出多个指标关联的大事件<kpiCode,eventList>
	 * 
	 * @param EventSearchKey
	 * @param kpiCodeList
	 * @param eventType
	 * @return
	 */
	List<BigEventPo> listEventByCodes(String EventSearchKey,
			List<String> kpiCodeList, String eventType, String isPrd);

	public List<BigEventPo> queryEventsByNum(int num);

	public List<DwpasCKpiInfo> queryEventRelKpiCodeByNum(int num);

	public DwpasCSysParam qryOneByParamId(long paramId)
			throws DataAccessException;

	List<BigEventPo> listLatelyBigEventAndRelateKpiInfoById(Integer eventId);
}
