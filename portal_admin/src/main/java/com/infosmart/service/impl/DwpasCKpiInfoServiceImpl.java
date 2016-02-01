package com.infosmart.service.impl;

import org.springframework.stereotype.Service;

import com.infosmart.po.DwpasCKpiInfo;
import com.infosmart.service.DwpasCKpiInfoService;
import com.infosmart.view.PageInfo;

@Service
public class DwpasCKpiInfoServiceImpl extends BaseServiceImpl implements
		DwpasCKpiInfoService {

	@Override
	public PageInfo listDwpasCKpiInfoByPagination(DwpasCKpiInfo dwpasCKpiInfo,
			int pageNo, int pageSize) {
		return this.myBatisDao
				.getListByPagination(
						"com.infosmart.mapper.DwpasCKpiInfoMapper.listKpiInfoByPagination",
						dwpasCKpiInfo, pageNo, pageSize);
	}

	@Override
	public DwpasCKpiInfo getDwpasCKpiInfoByCode(String kpiCode) {
		return this.myBatisDao.get(
				"com.infosmart.mapper.DwpasCKpiInfoMapper.queryOneByKpiCode",
				kpiCode);
	}

}
