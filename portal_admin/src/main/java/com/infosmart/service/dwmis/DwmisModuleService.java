package com.infosmart.service.dwmis;

import java.util.List;

import com.infosmart.po.dwmis.DwmisLegendInfo;
import com.infosmart.po.dwmis.DwmisModuleInfo;

public interface DwmisModuleService {

	/**
	 * 根据主题Id查询模块列表
	 * 
	 * @param moduleId
	 * @return
	 */
	public List<DwmisModuleInfo> getModluleInfoListBySubjectIdAndTemplateId(String subjectId,String templateId);

	/**
	 * 保存模块信息
	 * 
	 * @param dwmisModuleInfo
	 *            模块信息
	 * @param subjectId
	 *            关联主题ID
	 */
	public void saveModuleInfo(DwmisModuleInfo dwmisModuleInfo);

	/**
	 * 修改模块信息
	 * 
	 * @param dwmisModuleInfo
	 *            模块信息
	 * @param subjectId
	 *            主题ID
	 */
	public void updateModuleInfo(DwmisModuleInfo dwmisModuleInfo);

	/**
	 * 根据模块Id查询模块信息
	 * 
	 * @param ModuleId
	 *            模块ID
	 */
	public DwmisModuleInfo getModuleInfoByModuleId(String ModuleId);

	/**
	 * 删除模块信息
	 * 
	 * @param moduleId
	 *            模块ID
	 */
	public void deleteModuleInfo(String moduleId);

	/**
	 * 查询与该模块不关联的图例
	 * 
	 * @param ModuleId
	 * @return
	 */
	public List<DwmisLegendInfo> getUnRelLegendInfoByModuleId(String moduleId);

	/**
	 * 查询与该模块关联的图例
	 * 
	 * @param ModuleId
	 * @return
	 */
	public List<DwmisLegendInfo> getRelLegendInfoByModuleId(String moduleId);
	
}
