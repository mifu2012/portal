package com.infosmart.mapper;

import java.util.List;

import com.infosmart.po.Role;

public interface RoleMapper {
	List<Role> listAllRoles();

	Role getRoleById(int roleId);

	void insertRole(Role role);

	void updateRoleBaseInfo(Role role);

	void deleteRoleById(int roleId);

	int getCountByName(Role role);

	void updateRoleRights(Role role);
	void updateReportRights(Role role);
	void updateDwpasRights(Role role);
	void updateDwmisRights(Role role);
}
