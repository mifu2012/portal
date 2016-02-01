package com.infosmart.service.dwmis.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.infosmart.po.Role;
import com.infosmart.po.User;
import com.infosmart.service.dwmis.DwmisTemplatePopedomSerivce;
import com.infosmart.service.impl.BaseServiceImpl;

@Service
public class DwmisTemplatePopedomServiceImpl extends BaseServiceImpl implements
		DwmisTemplatePopedomSerivce {

	@Override
	public List<User> listTBUsers() {
		return myBatisDao.getList("DwmisTemplatePopedomMapper.listTBuser");
	}

	@Override
	public List<Role> listTBRoles() {
		return myBatisDao.getList("DwmisTemplatePopedomMapper.listTBrole");
	}

	@Override
	public void updateUserTemplate(User user) throws Exception {
		if (user == null) {
			this.logger.warn("updateUserTemplate方法失败：参数User为空");
			throw new Exception("updateUserTemplate方法失败：参数User为空");
		}
		myBatisDao.update("DwmisTemplatePopedomMapper.updateUserTemp", user);
	}

	@Override
	public void updateUserNoTemplate(User user) throws Exception {
		if (user == null) {
			this.logger.warn("updateUserTemplate方法失败：参数User为空");
			throw new Exception("updateUserTemplate方法失败：参数User为空");
		}
		logger.info("修改当前用户没有选定模板:" + user.getUsername());
		myBatisDao.update("DwmisTemplatePopedomMapper.updateUserNoTemplate",
				user);
	}

	@Override
	public void updateRoleTemplate(Role role) throws Exception {
		if (role == null) {
			this.logger.warn("updateRoleTemplate方法失败：参数Role为空");
			throw new Exception("updateRoleTemplate方法失败：参数Role为空");
		}
		myBatisDao.update("DwmisTemplatePopedomMapper.updateRoleTemp", role);
	}

	@Override
	public void updateRoleNpTemplate(Role role) throws Exception {
		if (role == null) {
			this.logger.warn("updateRoleTemplate方法失败：参数Role为空");
			throw new Exception("updateRoleTemplate方法失败：参数Role为空");
		}
		myBatisDao.update("DwmisTemplatePopedomMapper.updateRoleNoTemplate",
				role);
	}

}
