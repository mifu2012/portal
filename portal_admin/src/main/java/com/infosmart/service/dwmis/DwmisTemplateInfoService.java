package com.infosmart.service.dwmis;

import java.util.List;

import com.infosmart.po.dwmis.DwmisTemplateInfo;

public interface DwmisTemplateInfoService {

	/**
	 * 查询模板信息
	 * 
	 * @param dwmisTemplateInfo
	 * @return
	 */
	public List<DwmisTemplateInfo> listPageTemplateInfo(
			DwmisTemplateInfo dwmisTemplateInfo);

	/**
	 * 根据模板Id删除模板
	 * 
	 * @param templateId
	 *            模板ID
	 */
	public void delDwmisTemplateInfo(String templateId);

	/**
	 * 修改模板信息
	 * 
	 * @param dwmisTemplateInfo
	 */
	public void editDwmisTemplateInfo(DwmisTemplateInfo dwmisTemplateInfo);

	/**
	 * 根据模板Id查询模板信息
	 * 
	 * @param templateId
	 *            模板Id
	 * @return
	 */
	public DwmisTemplateInfo getDwmisTemplateInfoById(String templateId);

	/**
	 * 新增模板信息
	 * 
	 * @param dwmisTemplateInfo
	 */
	public void addDwmisTemplateInfo(DwmisTemplateInfo dwmisTemplateInfo);

	/**
	 * 复制模板信息
	 * 
	 * @param dwmisTemplateInfo
	 */
	public void saveCopyTemplateInfo(DwmisTemplateInfo dwmisTemplateInfo);

	/**
	 * 根据模板ID删除模板信息
	 * 
	 * @param templateId
	 */
	public void deleteTemplate(String templateId);

	/**
	 * 列出所有模板信息
	 * 
	 * @return
	 */
	List<DwmisTemplateInfo> listAllTemplateInfo();
}
