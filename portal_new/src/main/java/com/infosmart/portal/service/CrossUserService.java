package com.infosmart.portal.service;

import java.util.List;

import com.infosmart.portal.pojo.CrossUser;

public interface CrossUserService {

	/**
	 * 通过产品ID获取关联产品交叉用户信息
	 * 
	 * @param productId
	 *            产品ID
	 * @return 与productId相关的交叉用户信息List
	 */
	public List<CrossUser> getCrossUserInfo(String templateId,
			String productId, String queryDate);

	public List<CrossUser> getCrossUserActionInfo(String productId,
			String queryDate);

}
