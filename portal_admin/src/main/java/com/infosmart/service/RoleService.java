package com.infosmart.service;

import java.util.List;

import com.infosmart.po.Role;

public interface RoleService {
	/**
	 * 查询所有角色
	 * 
	 * @return
	 */
	List<Role> listAllRoles();

	/**
	 * 根据id查找相关角色
	 * 
	 * @param roleId
	 * @return
	 */
	Role getRoleById(int roleId);

	/**
	 * 添加角色
	 * 
	 * @param role
	 * @return
	 */
	boolean insertRole(Role role);

	/**
	 * 更新角色信息
	 * 
	 * @param role
	 * @return
	 */
	boolean updateRoleBaseInfo(Role role);

	/**
	 * 根据id删除角色
	 * 
	 * @param roleId
	 */
	void deleteRoleById(int roleId);
	/**
	 * 根据id删除角色以及其下面的所有用户
	 * @param roleId
	 */
	void deleteRoleAndUsersById(int roleId) throws Exception;

	/**
	 * 根据角色更新角色权限
	 * 
	 * @param role
	 */
	void updateRoleRights(Role role) throws Exception;

	/**
	 * 更新角色报表权限
	 * 
	 * @param role
	 */
	void updateReportRights(Role role) throws Exception;

	/**
	 * 更新角色经纬仪权限
	 * 
	 * @param role
	 */
	void updateDwpasRights(Role role) throws Exception;
	
	/**
	 * 更新角色瞭望塔权限
	 * 
	 * @param role
	 */
	void updateDwmisRights(Role role) throws Exception;
}
