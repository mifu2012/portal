package com.infosmart.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasCPrdInfo;
import com.infosmart.portal.pojo.DwpasStKpiData;
import com.infosmart.portal.service.DwpasCColumnInfoService;
import com.infosmart.portal.service.DwpasCKpiInfoService;
import com.infosmart.portal.service.DwpasStKpiDataService;
import com.infosmart.portal.service.ProductDevService;

@Service
public class ProductDevServiceImp extends BaseServiceImpl implements ProductDevService {
	

	@Autowired
	protected DwpasCColumnInfoService columnInfoService;

	@Autowired
	protected DwpasCKpiInfoService dwpasCKpiInfoService;

	@Autowired
	protected DwpasStKpiDataService dwpasStKpiDataService;


	@Override
	public Map<String, DwpasStKpiData> calculateKpiDataToDay(List<String> codeList,
			String queryDate) {

		// 根据指标列表查询指标数据 key = kpicode ；value = DwpasStKpiData
		Map<String, DwpasStKpiData> kpiDatas = dwpasStKpiDataService
				.listDwpasStKpiDataByKpiCode(codeList, queryDate, DwpasStKpiData.DATE_TYPE_OF_DAY);
		return kpiDatas;

	}
	
	@Override
	public Map<String, DwpasStKpiData> calculateKpiDataToMonth(
			List<String> codeList, String queryDate) {
		// 根据指标列表查询指标数据 key = kpicode ；value = DwpasStKpiData
				Map<String, DwpasStKpiData> kpiDatas = dwpasStKpiDataService
						.listDwpasStKpiDataByKpiCode(codeList, queryDate, DwpasStKpiData.DATE_TYPE_OF_MONTH);
				return kpiDatas;
	}

	@Override
	public String queryProdNameByID(String prodID) {
		DwpasCPrdInfo qryOneByCode = myBatisDao.get(
				"com.infosmart.mapper.DwpasCPrdInfoMapper.getProdInfoById",
				prodID);
		return qryOneByCode.getProductName();
	}

	

}
