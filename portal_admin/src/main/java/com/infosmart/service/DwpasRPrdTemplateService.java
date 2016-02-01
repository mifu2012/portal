package com.infosmart.service;

import java.util.List;
import java.util.Map;

import com.infosmart.po.DwpasCPrdInfo;
import com.infosmart.po.DwpasRPrdTemplate;

public interface DwpasRPrdTemplateService {
	/**
	 * 获取所有产品和渠道信息集合
	 * @return
	 */
	public List<DwpasCPrdInfo> getAllProduct(String productMark);
	/**
	 * 以模板ID为参数查找关联的所有产品及渠道信息
	 * @param templateId
	 * @return
	 */
	public List<DwpasCPrdInfo> getProdInfoByTemplateId(Integer templateId);
	/**
	 * 获取所有产品信息
	 * @return
	 */
	public List<DwpasCPrdInfo> getProdInfo();
	/**
	 * 删除模板关联的所有产品及渠道信息
	 */
	public void deleteDwpasRPrdTemplate(Integer templateId);
	/**
	 *  保存模板关联的所有产品及渠道信息
	 */
	public void insertDwpasRPrdTemplate(DwpasRPrdTemplate rPrdTemplate) throws Exception;
	/**
	 * 拷贝模板关联的产品信息
	 * @param map
	 */
	public void insertDwpasRPrdTemplate(Map<String,Integer> map) throws Exception;
	
}
