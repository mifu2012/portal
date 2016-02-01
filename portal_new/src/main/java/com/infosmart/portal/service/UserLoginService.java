package com.infosmart.portal.service;

import java.io.Serializable;
import java.util.List;

import com.infosmart.portal.pojo.DwpasCTemplateInfo;
import com.infosmart.portal.pojo.User;

public interface UserLoginService {
	
	/**查询用户登录信息
	 * 
	 * @param loginName
	 * @param passWord
	 * @return
	 */
	User getUser (String loginName, String passWord);
	User getUserAndRoleById(Integer userId);
    DwpasCTemplateInfo  getRoleTemplate(Serializable  rid);
    void updatePassWord(String passWord, Integer userId);
    
    List<DwpasCTemplateInfo> getTemplateInfos(List<String> templateIdList);
}
