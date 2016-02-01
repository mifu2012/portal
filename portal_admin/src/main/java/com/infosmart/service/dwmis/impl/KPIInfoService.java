package com.infosmart.service.dwmis.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.infosmart.po.dwmis.DwmisKpiInfo;
import com.infosmart.service.impl.BaseServiceImpl;
@Service
public class KPIInfoService  extends BaseServiceImpl{
	
	public List<DwmisKpiInfo> getKPIInfo(DwmisKpiInfo dwmisKpiInfo){
		return  myBatisDao.getList("dwmisKpiInfoMapper.listKpiInfo",dwmisKpiInfo);
	}
}
