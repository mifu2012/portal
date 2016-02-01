package com.infosmart.service.dwmis;

import java.util.List;

import com.infosmart.po.dwmis.DwmisMisType;

public interface DwmisMisTypeService {
	/**
	 * 根据groupId获得mis_type集合
	 * 
	 * @param groupdId
	 * @return
	 */
	public List<DwmisMisType> getAllDwmisMisTypes(int groupdId);

}
