package com.infosmart.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.infosmart.controller.DwpasKpiInfoController;
import com.infosmart.po.BigEventPo;
import com.infosmart.po.Dimension;
import com.infosmart.po.DwpasCKpiInfo;
import com.infosmart.po.DwpasCSysType;

public interface BigEventService {
	/**
	 * 
	 * 获取大事记记录
	 * 
	 * @param MisEventPo
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
	public void deleteEventMessageByIds(List<String> ids) throws Exception;

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
	public void deleteEventMessageById(Serializable id) throws Exception;

	/**
	 * 列出所有的指标信息
	 * 
	 * @return
	 */
	List<DwpasCKpiInfo> listAllKpiInfoByKpiType(String kpiType);

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
	public List<DwpasCKpiInfo> getUnRelativeEventKPI(String id)
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
	public List<BigEventPo> queryEventsByKpiCode(String kpiCode, int num);

	/**
	 * 获得大事记类型列表
	 * 
	 * @param param
	 * @return
	 */
	List<DwpasCSysType> getEventTypeName(Object param);
	/**
	 * 验证类型名称是否已存在
	 * 
	 * @param DwpasCSysType
	 * @return
	 * @throws Exception
	 */
	public List<DwpasCSysType> checkTypeName(String typeName);

	/**
	 * 根据大事记类型ID获得大事记类型列表
	 * 
	 * @param id
	 * @return
	 */
	List<DwpasCSysType> getEventsTypeNameById(Serializable id);

	/**
	 * 根据kpiCode或kpiName获取此事记不关联的指标
	 * 
	 * @param id
	 * @return
	 */
	List<DwpasCKpiInfo> getUnRelativeEventKPIByKpiCodeOrKpiName(String linkedKpiCode,
			String KpiCode,String kpiType);

	/**
	 * 删除事件类型
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	public void deleteEventTypeById(Serializable typeId) throws Exception;

	DwpasCSysType getDwpasCSysTypeById(Serializable id);

	/**
	 * 更新事件类型
	 * 
	 * @param map
	 * @return
	 */
	public void updateEventType(DwpasCSysType dwpasCSysType) throws Exception;

	/**
	 * 添加事件类型
	 * 
	 * @param map
	 * @return
	 */
	public void insertEventType(DwpasCSysType dwpasCSysType) throws Exception;
	
	/**
	 * 根据大事件Id获取与该大事件不关联的所有指标
	 * @param eventId
	 * @return
	 */
	List<DwpasCKpiInfo> getUnRelKpiInfoList(String eventId);
}
