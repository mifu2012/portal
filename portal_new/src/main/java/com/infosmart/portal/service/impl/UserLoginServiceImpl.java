package com.infosmart.portal.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasCTemplateInfo;
import com.infosmart.portal.pojo.User;
import com.infosmart.portal.service.UserLoginService;
import com.infosmart.portal.util.db.CustomerContextHolder;

@Service
public class UserLoginServiceImpl extends BaseServiceImpl implements
		UserLoginService {
	@Override
	public User getUser(String loginName, String passWord) {
		try {
			this.logger.info("CUSTOMER_TYPE:--------------------------->"
					+ this.myBatisDao.getSqlSession().getConnection().getMetaData()
							.getURL());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map map = new HashMap();
		map.put("loginName", loginName);
		map.put("passWord", passWord);
		return this.myBatisDao.get("com.infosmart.mapper.User.getUser", map);

	}

	@Override
	public DwpasCTemplateInfo getRoleTemplate(Serializable rid) {
		return this.myBatisDao.get("com.infosmart.mapper.User.getRoleTemplate",
				rid);
	}

	@Override
	public User getUserAndRoleById(Integer userId) {
		User user = (User) this.myBatisDao.get(
				"com.infosmart.mapper.User.getUserAndRoleById", userId);
		return user;
	}

	@Override
	public void updatePassWord(String passWord, Integer userId) {
		Map map = new HashMap();
		map.put("passWord", passWord);
		map.put("userId", userId);
		this.myBatisDao.update("com.infosmart.mapper.User.updatePassWord", map);
	}

	@Override
	public List<DwpasCTemplateInfo> getTemplateInfos(List<String> templateIdList) {
		return this.myBatisDao.getList(
				"com.infosmart.mapper.User.getTemplateInfos", templateIdList);
	}
}
