package com.infosmart.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.infosmart.mapper.UserMapper;
import com.infosmart.po.User;
import com.infosmart.service.UserService;

public class UserServiceImpl extends BaseServiceImpl implements UserService {

	private UserMapper userMapper;

	public User getUserById(Integer userId) {
		return userMapper.getUserById(userId);
	}

	public boolean insertUser(User user) {
		if(user == null){
			this.logger.warn("添加用户失败：参数user为空");
			return false;
		}
		int count = userMapper.getCountByName(user.getLoginname());
		if (count > 0) {
			return false;
		} else {
			userMapper.insertUser(user);
			return true;
		}

	}

	public List<User> listPageUser(User user) {
		if(user==null){
			this.logger.warn("查询失败，参数user为空！");
			return null;
		}
		return userMapper.listPageUser(user);
	}

	public void updateUser(User user)throws Exception {
		if(user == null){
			this.logger.warn("更新用户失败：参数user为空");
			throw new Exception("更新用户失败：参数user为空");
		}
		userMapper.updateUser(user);
	}

	public void updateUserBaseInfo(User user)throws Exception {
		if(user == null){
			this.logger.warn("更新用户基本信息失败：参数user为空");
			throw new Exception("更新用户基本信息失败：参数user为空");
		}
		userMapper.updateUserBaseInfo(user);
	}

	public void updateUserRights(User user)throws Exception {
		if(user == null){
			this.logger.warn("更新用户权限失败：参数user为空");
			throw new Exception("更新用户权限失败：参数user为空");
		}
		userMapper.updateUserRights(user);
	}

	public User getUserByNameAndPwd(String loginname, String password) {
		if(StringUtils.isBlank(loginname) || StringUtils.isBlank(password)){
			this.logger.warn("getUserByNameAndPwd失败：参数用户名或者密码为空");
			return null;
		}
		User user = new User();
		user.setLoginname(loginname);
		user.setPassword(password);
		return userMapper.getUserInfo(user);
	}

	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public void deleteUser(int userId) {
		userMapper.deleteUser(userId);
	}
	
	@Override
	public void deleteUserByRoleId(int roleId){
		userMapper.deleteUserByRoleId(roleId);
	}

	public User getUserAndRoleById(Integer userId) {
		return userMapper.getUserAndRoleById(userId);
	}

	public void updateLastLogin(User user)throws Exception {
		if(user == null){
			this.logger.warn("更新用户最后登录时间失败：参数user为空");
			throw new Exception("更新用户最后登录时间失败：参数user为空");
		}
		userMapper.updateLastLogin(user);
	}

	public List<User> listAllUser() {
		return userMapper.listAllUser();
	}

	@Override
	public void updateReportRights(User user)throws Exception {
		if(user == null){
			this.logger.warn("更新用户报表权限失败：参数user为空");
			throw new Exception("更新用户报表权限失败：参数user为空");
		}
		userMapper.updateReportRights(user);
		
	}
	

	@Override
	public void updateDwpasRights(User user)throws Exception {
		if(user == null){
			this.logger.warn("更新经纬仪权限失败：参数user为空");
			throw new Exception("更新经纬仪权限失败：参数user为空");
		}
		userMapper.updateDwpasRights(user);
		
	}

	@Override
	public void updateDwmisRights(User user) throws Exception {
		if(user == null){
			this.logger.warn("更新瞭望塔权限失败：参数user为空");
			throw new Exception("更新瞭望塔权限失败：参数user为空");
		}
		userMapper.updateDwmisRights(user);
		
	}

}
