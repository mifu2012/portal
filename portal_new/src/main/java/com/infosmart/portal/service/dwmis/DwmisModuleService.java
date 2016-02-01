package com.infosmart.portal.service.dwmis;

import java.util.List;

import com.infosmart.portal.pojo.dwmis.DwmisModuleInfo;
import com.infosmart.portal.pojo.dwmis.DwmisSubjectInfo;

public interface DwmisModuleService {

	/**
	 * 根据主题Id查询模块列表
	 * 
	 * @param moduleId
	 * @return
	 */
	public List<DwmisModuleInfo> getModluleInfoListBySubjectId(String subjectId);

	/**
	 * 根据模块Id查询模块信息
	 * 
	 * @param ModuleId
	 *            模块ID
	 */
	public DwmisModuleInfo getModuleInfoByModuleId(String ModuleId);

	/**
	 * 根据模板Id查询所有主题
	 * 
	 * @param templateId
	 * @return
	 */
	public List<DwmisSubjectInfo> getAllSubjectByTempId(String templateId);

	/**
	 * 根据主题Id查询主题信息
	 * @param subjectId
	 * @return
	 */
	public DwmisSubjectInfo getSubjectInfoById(String subjectId);
}
