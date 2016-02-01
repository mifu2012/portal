package com.infosmart.service;

import java.util.List;

import com.infosmart.po.User;

public interface UserService {
	/**
	 * 根据id获得用户对象
	 * 
	 * @param userId
	 * @return
	 */
	User getUserById(Integer userId);

	/**
	 * 添加user对象
	 * 
	 * @param user
	 * @return
	 */
	boolean insertUser(User user);

	/**
	 * 更新用户对象
	 * 
	 * @param user
	 */
	void updateUser(User user)throws Exception;

	/**
	 * 根据用户名和密码得到用户对象
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	User getUserByNameAndPwd(String username, String password);

	/**
	 * 更新用户基本信息
	 * 
	 * @param user
	 */
	void updateUserBaseInfo(User user)throws Exception;

	/**
	 * 更新用户权限
	 * 
	 * @param user
	 */
	void updateUserRights(User user)throws Exception;

	/**
	 * 更新用户报表权限
	 * 
	 * @param user
	 */
	void updateReportRights(User user)throws Exception;

	/**
	 * 更新经纬仪权限
	 * 
	 * @param user
	 */
	void updateDwpasRights(User user)throws Exception;
	
	/**
	 * 更新瞭望塔权限
	 * 
	 * @param user
	 */
	void updateDwmisRights(User user)throws Exception;
	
	
	/**
	 * 根据id删除用户对象
	 * 
	 * @param userId
	 */
	void deleteUser(int userId);
	
	/**
	 * 根据角色Id删除用户对象
	 * @param roleId
	 */
	void deleteUserByRoleId(int roleId);

	/**
	 * 获得分页的用户对象
	 * 
	 * @param user
	 * @return
	 */
	List<User> listPageUser(User user);

	/**
	 * 更新用户最后登录时间
	 * 
	 * @param user
	 */
	void updateLastLogin(User user)throws Exception;

	/**
	 * 根据id获得用户角色对象
	 * 
	 * @param userId
	 * @return
	 */
	User getUserAndRoleById(Integer userId);

	/**
	 * 获得所有用户角色对象
	 * 
	 * @return
	 */
	List<User> listAllUser();
}
