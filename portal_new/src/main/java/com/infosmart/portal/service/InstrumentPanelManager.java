package com.infosmart.portal.service;

import com.infosmart.portal.pojo.DwpasStPlatformData;


public interface InstrumentPanelManager {

    /**
     * 查询平台用户占比表
     * 
     * @param queryDate 查询日期
     * @return 平台用户占比表DTO
     */
	DwpasStPlatformData queryPlatFormUser(String queryDate);

}
