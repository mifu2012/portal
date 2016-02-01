package com.infosmart.service.impl;

import java.util.List;

import com.infosmart.mapper.RoleMapper;
import com.infosmart.po.Role;
import com.infosmart.service.RoleService;

public class RoleServiceImpl extends BaseServiceImpl implements RoleService {

	private RoleMapper roleMapper;

	public List<Role> listAllRoles() {
		return roleMapper.listAllRoles();
	}

	public void deleteRoleById(int roleId) {
		roleMapper.deleteRoleById(roleId);
	}

	/**
	 * 删除角色以及其下面的所有用户
	 */
	public void deleteRoleAndUsersById(int roleId) throws Exception {
		try {
			this.myBatisDao.delete(
					"com.infosmart.mapper.RoleMapper.deleteRoleById", roleId);
			this.myBatisDao.delete(
					"com.infosmart.mapper.UserMapper.deleteUserByRoleId",
					roleId);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("删除角色以及其下的用户失败：" + e.getMessage(), e);
			throw e;
		}
	}

	public Role getRoleById(int roleId) {
		return roleMapper.getRoleById(roleId);
	}

	public boolean insertRole(Role role) {
		if (role == null) {
			this.logger.warn("insertRole方法执行失败：参数role为空");
			return false;
		}
		if (roleMapper.getCountByName(role) > 0)
			return false;
		else {
			roleMapper.insertRole(role);
			return true;
		}
	}

	public boolean updateRoleBaseInfo(Role role) {
		if (role == null) {
			this.logger.warn("updateRoleBaseInfo方法执行失败：参数role为空");
			return false;
		}
		if (roleMapper.getCountByName(role) > 0)
			return false;
		else {
			roleMapper.updateRoleBaseInfo(role);
			return true;
		}
	}

	public void updateRoleRights(Role role) throws Exception {
		if (role == null) {
			this.logger.warn("updateRoleRights方法执行失败：参数role为空");
			throw new Exception("updateRoleRights方法执行失败：参数role为空");
		}
		roleMapper.updateRoleRights(role);
	}

	public RoleMapper getRoleMapper() {
		return roleMapper;
	}

	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

	@Override
	public void updateReportRights(Role role) throws Exception {
		if (role == null) {
			this.logger.warn("updateReportRights方法执行失败：参数role为空");
			throw new Exception("updateReportRights方法执行失败：参数role为空");
		}
		roleMapper.updateReportRights(role);

	}

	@Override
	public void updateDwpasRights(Role role) throws Exception {
		if (role == null) {
			this.logger.warn("updateDwpasRights失败：参数role为空");
			throw new Exception("updateDwpasRights方法执行失败：参数role为空");
		}
		roleMapper.updateDwpasRights(role);

	}

	@Override
	public void updateDwmisRights(Role role) throws Exception {
		if (role == null) {
			this.logger.warn("updateDwmisRights失败：参数role为空");
			throw new Exception("updateDwmisRights方法执行失败：参数role为空");
		}
		roleMapper.updateDwmisRights(role);
		
	}

}
