package com.infosmart.portal.service.impl;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasStPlatformData;
import com.infosmart.portal.service.InstrumentPanelManager;

@Service
public class InstrumentPanelManagerImp extends BaseServiceImpl implements
		InstrumentPanelManager {

	/**
	 * 查询平台用户占比表
	 * 
	 * @param queryDate
	 *            查询日期
	 * @return 平台用户占比表DTO
	 */
	@Override
	public DwpasStPlatformData queryPlatFormUser(String queryDate) {
		return  this.myBatisDao
				.get("com.infosmart.mapper.DwpasStPlatFormData.queryPlatFormUserByDate",
						queryDate);
	}

}
