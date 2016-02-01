package com.infosmart.service;

import java.io.Serializable;
import java.util.List;

import com.infosmart.po.DwpasCTemplateInfo;

public interface DwpasCTemplateInfoService {
	/**
	 * 列出分页的模板信息
	 * 
	 * @param templateInfo
	 * @return
	 */
	List<DwpasCTemplateInfo> listPageTemplateInfo(
			DwpasCTemplateInfo templateInfo);

	/**
	 * 根据id获得模板信息
	 * 
	 * @param templateId
	 * @return
	 */
	DwpasCTemplateInfo getTemplateByID(Serializable templateId);

	/**
	 * 插入模板信息
	 * 
	 * @param templateInfo
	 */
	void saveTemplateInfo(DwpasCTemplateInfo templateInfo);

	/**
	 * 根据id删除模板信息
	 * 
	 * @param templateId
	 */
	void deleteTemplateInfo(Serializable templateId);

	/**
	 * 根据id更新模板信息
	 * 
	 * @param templateInfo
	 */
	void updateTemplateInfo(DwpasCTemplateInfo templateInfo);

	/**
	 * 根据条件列出所有模板信息
	 * 
	 * @param templateInfo
	 * @return
	 */
	List<DwpasCTemplateInfo> listAllTemplateInfo(DwpasCTemplateInfo templateInfo);

	/**
	 * 列出所有模板信息
	 * 
	 * @return
	 */
	List<DwpasCTemplateInfo> listAllTemplateInfo();
}
