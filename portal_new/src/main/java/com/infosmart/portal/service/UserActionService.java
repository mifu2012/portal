package com.infosmart.portal.service;

import java.util.List;

import com.infosmart.portal.pojo.DwpasCUserActionInfo;
import com.infosmart.portal.pojo.UserAction;


public interface UserActionService {
	
	/**
	 * 通过用户行为Code获取关联产品交叉用户信息
	 * 
	 * @param ActionCode
	 *            用户行为Code
	 * @return 与ActionCode相关的交叉用户行为信息List
	 */
	public List<UserAction> getUserActionCrossInfo( String actionCode , String queryDate);
	
	public DwpasCUserActionInfo getUserActionInfoById(String actionCode);

}
