package com.infosmart.service.dwmis;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.infosmart.po.dwmis.DwmisKpiInfo;
import com.infosmart.po.dwmis.DwmisMisTypePo;
import com.infosmart.po.dwmis.MisEventPo;

public interface MisEventService {
	/**
	 * 
	 * 获取大事记记录
	 * 
	 * @param MisEventPo
	 * @return
	 */
	public List<MisEventPo> getlistPageAndBigEventPo(Object param);

	/**
	 * 
	 * 批量删除大事记
	 * 
	 * @param ids
	 * @return
	 */
	public void deleteEventMessageByIds(List<String> ids);

	/**
	 * 
	 * 根据ID获取对应的大事记记录
	 * 
	 * @param eventId
	 * @return
	 */
	public MisEventPo getMISEventById(Serializable eventId);
	/**
	 * 验证类型名称是否已存在
	 * 
	 * @param DwpasCSysType
	 * @return
	 * @throws Exception
	 */
	public List<DwmisMisTypePo> checkTypeName(String typeName);

	/**
	 * 修改相关大事记
	 * 
	 * @param mEventDO
	 * @throws Exception
	 */
	public void updateEvent(MisEventPo mEventDO) throws Exception;

	/**
	 * 插入相关大事记
	 * 
	 * @param mEventDO
	 * @throws Exception
	 */
	public void insertEvent(MisEventPo mEventDO) throws Exception;

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
	public List<DwmisKpiInfo> getRelativeEventKPI(Serializable id)
			throws Exception;

	/**
	 * 获取此事记不关联的指标
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	public List<DwmisKpiInfo> getUnRelativeEventKPI(String id)
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
	public List<MisEventPo> queryEventsByKpiCode(String kpiCode, int num);

	/**
	 * 获得大事记名字
	 * 
	 * @param param
	 * @return
	 */
	List<DwmisMisTypePo> getEventTypeName(Object param);
	
	/**
	 * 根据大事记类型ID获得大事记类型列表
	 * 
	 * @param id
	 * @return
	 */
	List<DwmisMisTypePo> getEventsTypeNameById(Serializable id);
	
	/**
	 * 根据kpiCode或kpiName获取此事记不关联的指标
	 * 
	 * @param id
	 * @return
	 */
	List<DwmisKpiInfo> getUnRelativeEventKPIByKpiCodeOrKpiName(String id,String KpiCode);
	
	/**
	 * 删除事件类型
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	public void deleteEventTypeById(Serializable typeId) throws Exception;
	
	DwmisMisTypePo getDwmisMisTypePoById(Serializable id);
	
	/**
	 * 更新事件类型
	 * 
	 * @param map
	 * @return
	 */
	public void updateEventType(DwmisMisTypePo dwmisMisTypePo) throws Exception;
	
	/**
	 * 添加事件类型
	 * 
	 * @param map
	 * @return
	 */
	public void insertEventType(DwmisMisTypePo dwmisMisTypePo) throws Exception;
}
