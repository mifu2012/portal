package com.infosmart.portal.service.dwmis.impl;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.dwmis.DwmisMisTypePo;
import com.infosmart.portal.service.dwmis.DwmisMisTypeService;
import com.infosmart.portal.service.impl.BaseServiceImpl;

@Service
public class DwmisMisTypeServiceImpl extends BaseServiceImpl implements
		DwmisMisTypeService {

	@Override
	public DwmisMisTypePo getMisTypeByTypeId(int typeId) {
		this.logger.info("得到定义信息:" + typeId);
		return this.myBatisDao.get(
				"com.infosmart.dwmis.DwmisMisTypeMapper.getMisTypeByTypeId",
				typeId);
	}

}
