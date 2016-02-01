package com.infosmart.service.dwmis.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.infosmart.po.dwmis.DwmisMisType;
import com.infosmart.service.dwmis.DwmisMisTypeService;
import com.infosmart.service.impl.BaseServiceImpl;

@Service
public class DwmisMisTypeServiceImp extends BaseServiceImpl implements
		DwmisMisTypeService {

	@Override
	public List<DwmisMisType> getAllDwmisMisTypes(int groupId) {
		return this.myBatisDao
				.getList(
						"com.infosmart.DwmisMisTypeMapper.listDwmisMisTypesByGroupId",
						groupId);
	}

}
