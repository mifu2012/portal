package com.infosmart.portal.service.dwmis;

import com.infosmart.portal.pojo.dwmis.DwmisMisTypePo;

public interface DwmisMisTypeService {
	/**
	 * 
	 * @param typeId
	 * @return
	 */
	DwmisMisTypePo getMisTypeByTypeId(int typeId);
}
