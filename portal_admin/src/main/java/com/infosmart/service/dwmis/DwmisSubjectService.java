package com.infosmart.service.dwmis;

import java.util.List;

import com.infosmart.po.dwmis.DwmisSubjectInfo;

public interface DwmisSubjectService {

	/**
	 * 查询所有主题信息
	 * 
	 * @return
	 */
	public List<DwmisSubjectInfo> getAllSubjectInfos();

	/**
	 * 保存主题信息
	 * 
	 * @param subjectInfo
	 */
	public void saveSubjectInfo(DwmisSubjectInfo subjectInfo);

	/**
	 * 根据主题Id查询主题信息
	 * 
	 * @param subjectid
	 * @return
	 */
	public DwmisSubjectInfo getSubjectInfoById(String subjectid);

	/**
	 * 根据模板ID查询主题信息
	 * 
	 * @param subjectid
	 * @return
	 */
	public List<DwmisSubjectInfo> getSubjectInfoByTemplateId(String templateId);

	/**
	 * 新增主题信息
	 * 
	 * @param dwmisSubjectInfo
	 */
	public void addSubjectInfo(DwmisSubjectInfo dwmisSubjectInfo);

	/**
	 * 修改主题信息
	 * 
	 * @param dwmisSubjectInfo
	 */
	public void updateSubjectInfo(DwmisSubjectInfo dwmisSubjectInfo);

	/**
	 * 删除主题信息
	 * 
	 * @param subjectId
	 */
	public void deleteSubjectInfo(String subjectId);

}
