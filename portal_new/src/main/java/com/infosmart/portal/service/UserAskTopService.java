package com.infosmart.portal.service;

import java.util.List;

import com.infosmart.portal.pojo.DwpasRColumnComKpi;
import com.infosmart.portal.pojo.UserAskTop;

public interface UserAskTopService {

	/**
	 * 查询日指标下用户求助信息
	 * 
	 * @param productid
	 *            产品ID
	 * @param queryDate
	 *            查询日期
	 * @param userType
	 *            用户类型
	 * @return 用户求助信息UserAskTop
	 */
	List<UserAskTop> listUserTop10Date(String productId, String queryDate,
			List<String> dayUserTypeList);

	/**
	 * 查询月指标下用户求助信息
	 * 
	 * @param productid
	 *            产品ID
	 * @param queryDate
	 *            查询日期
	 * @param userType
	 *            用户类型
	 * @return 用户求助信息UserAskTop
	 */
	List<UserAskTop> listUserTop10Month(String productId, String queryDate,
			List<String> monthUserTypeList);

	/**
	 * 查询通用栏目指标信息
	 * 
	 * @param kpiCode
	 * 
	 * @param columnCode
	 * 
	 * @return 用户通用栏目信息UserAskTop
	 */
	List<DwpasRColumnComKpi> queryComKpiInfoByColumnCodeAndKpiCode(String menuId,String moduleCode,String kpiCode,
			String dateType);
}
