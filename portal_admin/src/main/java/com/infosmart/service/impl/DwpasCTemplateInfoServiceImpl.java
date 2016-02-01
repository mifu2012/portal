package com.infosmart.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.infosmart.po.DwpasCTemplateInfo;
import com.infosmart.service.DwpasCTemplateInfoService;
@Service
public class DwpasCTemplateInfoServiceImpl extends BaseServiceImpl implements DwpasCTemplateInfoService{

	@Override
	public List<DwpasCTemplateInfo> listPageTemplateInfo(
			DwpasCTemplateInfo templateInfo) {
		return myBatisDao.getList("DwpasCTemplateInfoMapper.listPageDwpasCTemplateInfo", templateInfo);
	}

	@Override
	public DwpasCTemplateInfo getTemplateByID(Serializable templateId) {
		return myBatisDao.get("DwpasCTemplateInfoMapper.getTemplateById", templateId);
	}

	@Override
	public void saveTemplateInfo(DwpasCTemplateInfo templateInfo) {
		if(templateInfo == null){
			this.logger.warn("saveTemplateInfo方法失败：参数templateInfo为空");
			return;
		}
		myBatisDao.save("DwpasCTemplateInfoMapper.insertTemplate", templateInfo);
	}

	@Override
	public void deleteTemplateInfo(Serializable templateId) {
		if(templateId == null){
			this.logger.warn("deleteTemplateInfo方法失败：参数templateId为空");
			return;
		}
		 myBatisDao.delete("DwpasCTemplateInfoMapper.deleteTemplate", templateId);
	}

	@Override
	public void updateTemplateInfo(DwpasCTemplateInfo templateInfo) {
		if(templateInfo == null){
			this.logger.warn("updateTemplateInfo方法失败:参数templateInfo为空");
			return;
		}
		 myBatisDao.update("DwpasCTemplateInfoMapper.updateTemplate", templateInfo);
	}

	@Override
	public List<DwpasCTemplateInfo> listAllTemplateInfo(
			DwpasCTemplateInfo templateInfo) {
		return myBatisDao.getList("DwpasCTemplateInfoMapper.listAllDwpasCTemplateInfo", templateInfo);
	}

	@Override
	public List<DwpasCTemplateInfo> listAllTemplateInfo() {
		return myBatisDao.getList("DwpasCTemplateInfoMapper.listAllDwpasCTemplateInfo1");
	}

}
