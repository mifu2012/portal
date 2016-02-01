package com.infosmart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infosmart.po.DwpasCPrdInfo;
import com.infosmart.po.DwpasRPrdTemplate;
import com.infosmart.service.DwpasRPrdTemplateService;

/**
 * 
 * 模板与产品,渠道关联实现类
 * 
 * 
 */
@Service
public class DwpasRPrdTemplateServiceImpl extends BaseServiceImpl implements
		DwpasRPrdTemplateService {

	@Override
	public void insertDwpasRPrdTemplate(Map<String, Integer> map)
			throws Exception {
		if (map == null) {
			this.logger.warn("拷贝模板关联的产品信息失败，参数为空");
			throw new Exception("拷贝模板关联的产品信息失败，参数为空");
		}
		this.myBatisDao
				.save("com.infosmart.mapper.DwpasRPrdTemplateMapper.COPY_DWPAS_R_PRD_TEMPLATE",
						map);
	}

	@Override
	public List<DwpasCPrdInfo> getAllProduct(String productMark) {
		List<DwpasCPrdInfo> list = null;
		list = this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasRPrdTemplateMapper.GET_ALL_PRODUCTS",
						productMark);
		if (list == null || list.size() == 0) {
			logger.info("获取产品，渠道信息集合为空!");
			list = new ArrayList<DwpasCPrdInfo>();
		}
		return list;
	}

	@Override
	public List<DwpasCPrdInfo> getProdInfoByTemplateId(Integer templateId) {
		List<DwpasCPrdInfo> list = null;
		list = this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasRPrdTemplateMapper.GET_CONNECTED_PRODUCTS",
						templateId);
		if (list == null || list.size() == 0) {
			logger.info("获取与模板关联的产品，渠道信息集合为空!");
			list = new ArrayList<DwpasCPrdInfo>();
		}
		return list;
	}

	@Override
	public List<DwpasCPrdInfo> getProdInfo() {
		List<DwpasCPrdInfo> list = null;
		list = this.myBatisDao
				.getList("com.infosmart.mapper.DwpasRPrdTemplateMapper.	GET_CONNECTED_PRODUCTS_All");
		if (list == null || list.size() == 0) {
			logger.info("获取与模板关联的产品，渠道信息集合为空!");
			list = new ArrayList<DwpasCPrdInfo>();
		}
		return list;
	}

	@Override
	public void deleteDwpasRPrdTemplate(Integer templateId) {
		this.myBatisDao
				.delete("com.infosmart.mapper.DwpasRPrdTemplateMapper.DELETE_DWPAS_R_PRD_TEMPLATE_BY_TEMPLATEID",
						templateId);
	}

	@Override
	public void insertDwpasRPrdTemplate(DwpasRPrdTemplate rPrdTemplate)
			throws Exception {
		if (rPrdTemplate == null) {
			this.logger.warn("保存模板关联的所有产品及渠道信息，参数为空");
			throw new Exception("保存模板关联的所有产品及渠道信息，参数为空");
		}
		this.myBatisDao
				.save("com.infosmart.mapper.DwpasRPrdTemplateMapper.INSERT_DWPAS_R_PRD_TEMPLATE",
						rPrdTemplate);
	}

}
