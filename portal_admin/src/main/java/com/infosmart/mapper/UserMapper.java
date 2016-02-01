package com.infosmart.mapper;

import java.util.List;

import com.infosmart.po.User;

public interface UserMapper {
	List<User> listAllUser();

	User getUserById(Integer userId);

	void insertUser(User user);

	void updateUser(User user);

	User getUserInfo(User user);

	void updateUserBaseInfo(User user);

	void updateUserRights(User user);
	void updateReportRights(User user);
	void updateDwpasRights(User user);
	void updateDwmisRights(User user);

	int getCountByName(String loginname);

	void deleteUser(int userId);
	
	void deleteUserByRoleId(int roleId);

	int getCount(User user);

	List<User> listPageUser(User user);

	User getUserAndRoleById(Integer userId);

	void updateLastLogin(User user);
}
