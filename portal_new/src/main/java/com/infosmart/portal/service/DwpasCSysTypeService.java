package com.infosmart.portal.service;

import java.util.List;

import com.infosmart.portal.pojo.DwpasCSysType;

/**
 * 系统类型信息
 * 
 * @author Administrator
 * 
 */

public interface DwpasCSysTypeService {
	/**
	 * 查询所有父类KPi
	 */
	List<DwpasCSysType> getParentKpi() throws Exception;

}
