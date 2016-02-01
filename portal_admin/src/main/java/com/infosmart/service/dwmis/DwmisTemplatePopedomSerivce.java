package com.infosmart.service.dwmis;

import java.util.List;

import com.infosmart.po.Role;
import com.infosmart.po.User;

public interface DwmisTemplatePopedomSerivce {
	/**
	 * 获得用户信息
	 * 
	 * @return
	 */
	List<User> listTBUsers();

	/**
	 * 获得角色信息
	 * 
	 * @return
	 */
	List<Role> listTBRoles();

	/**
	 * 根据用户更新经纬仪权限
	 * 
	 * @param user
	 */
	void updateUserTemplate(User user)throws Exception;
	
	/**
	 * 根据用户更新经纬仪权限(没有模板)
	 * 
	 * @param user
	 */
	void updateUserNoTemplate(User user) throws Exception;

	/**
	 * 根据角色更新经纬仪权限
	 * 
	 * @param role
	 */
	void updateRoleTemplate(Role role) throws Exception;
	
	/**
	 * 根据角色更新经纬仪权限(没有模板)
	 * 
	 * @param role
	 */
	void updateRoleNpTemplate(Role role) throws Exception;
}
