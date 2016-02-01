package com.infosmart.service.dwmis;

import java.util.List;

import com.infosmart.po.dwmis.DwmisKpiDmnsnr;

public interface KpiDmnsnRService {
	/**
	 *  查询关联维度表
	 * @return
	 */
	List<DwmisKpiDmnsnr> listKpiDmnsnr();
}
