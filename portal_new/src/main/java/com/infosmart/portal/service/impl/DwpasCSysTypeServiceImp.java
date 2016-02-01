package com.infosmart.portal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasCSysType;
import com.infosmart.portal.service.DwpasCSysTypeService;

@Service
public class DwpasCSysTypeServiceImp extends BaseServiceImpl implements
		DwpasCSysTypeService {

	public List<DwpasCSysType> getParentKpi()  throws Exception{
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("groupId", "1000");
		return this.myBatisDao.getList(
				"com.infosmart.mapper.DwpasCSysTypeMapper.getParentKpi",
				paramMap);

	}

}
