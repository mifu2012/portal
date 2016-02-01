package com.infosmart.service.dwmis.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.infosmart.po.dwmis.DwmisSystemParamType;
import com.infosmart.service.dwmis.DwmisSystemParamTypeService;
import com.infosmart.service.impl.BaseServiceImpl;

@Service
public class DwmisSystemParamTypeServiceImpl extends BaseServiceImpl implements
		DwmisSystemParamTypeService {

	@Override
	public List<DwmisSystemParamType> listSystemParamByGroupId(int groupId) {
		return this.myBatisDao
				.getList(
						"com.infosmart.dwmis.SystemParamMapping.listSystemParamTypeByGroupId",
						groupId);
	}

	@Override
	public DwmisSystemParamType getSystemParamTypeByTypeId(int typeId) {
		return this.myBatisDao
				.get("com.infosmart.dwmis.SystemParamMapping.getSystemParamTypeByTypeId",
						typeId);
	}

	@Override
	public void updateSystemParamType(DwmisSystemParamType dwmisSystemParamType) {
		if(dwmisSystemParamType == null){
			this.logger.warn("更新DwmisSystemParamType失败:参数dwmisSystemParamType为空");
			return;
		}
		this.myBatisDao.update(
				"com.infosmart.dwmis.SystemParamMapping.updateSystemParamType",
				dwmisSystemParamType);
	}

	@Override
	public void deleteSystemParamType(int typeId) {
		this.myBatisDao.delete(
				"com.infosmart.dwmis.SystemParamMapping.deleteSystemParamType",
				typeId);
	}

	@Override
	public void saveSystemparamTypeGroup(Object parm) {
		if(parm == null){
			this.logger.warn("保存SystemparamTypeGroup失败:参数parm为空");
			return;
		}
		this.myBatisDao.save(
				"com.infosmart.dwmis.SystemParamMapping.insertSystemTypeGroup",
				parm);
	}

	@Override
	public String getGroupName(int typeId) {
		return this.myBatisDao.get(
				"com.infosmart.dwmis.SystemParamMapping.getGroupName", typeId);
	}

	@Override
	public void delSystemTypeGroup(int groupId) {
		this.myBatisDao
				.delete("com.infosmart.dwmis.SystemParamMapping.deleteSystemParamTypeGroup",
						groupId);
	}
	
	@Override
	public void delSystemTypeGroupNode(int typeId) {
		this.myBatisDao
				.delete("com.infosmart.dwmis.SystemParamMapping.deleteSystemParamTypeGroupNode",
						typeId);
	}

}
