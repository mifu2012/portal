package com.infosmart.controller.dwmis;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.controller.BaseController;
import com.infosmart.po.Role;
import com.infosmart.po.User;
import com.infosmart.po.dwmis.DwmisSubjectInfo;
import com.infosmart.po.dwmis.DwmisTemplateInfo;
import com.infosmart.service.RoleService;
import com.infosmart.service.UserService;
import com.infosmart.service.dwmis.DwmisSubjectService;
import com.infosmart.service.dwmis.DwmisTemplateInfoService;
import com.infosmart.service.dwmis.DwmisTemplatePopedomSerivce;

@Controller
@RequestMapping("/dwmisTemplateInfo")
public class DwmisTemplateInfoController extends BaseController {

	@Autowired
	private DwmisTemplateInfoService dwmisTemplateInfoService;
	@Autowired
	private DwmisSubjectService dwmisSubjectService;
	@Autowired
	private DwmisTemplatePopedomSerivce dwmisPopedomService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;

	/**
	 * 模板列表
	 * 
	 * @param request
	 * @param dwmisTemplateInfo
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request,
			DwmisTemplateInfo dwmisTemplateInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<DwmisTemplateInfo> templateInfoList = dwmisTemplateInfoService
				.listPageTemplateInfo(dwmisTemplateInfo);
		map.put("templateInfoList", templateInfoList);
		map.put("dwmisTemplateInfo", dwmisTemplateInfo);
		return new ModelAndView("dwmis/template/templateList", map);
	}

	/**
	 * 显示模板下主题列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/listMenus")
	public ModelAndView listMenus(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		String templateId = request.getParameter("templateId");
		if (templateId == null) {
			this.logger.warn("获取当前系统所有父级菜单时传递的模板id为null");
			return new ModelAndView(this.NOT_FOUND_ACTION);
		}
		List<DwmisSubjectInfo> subjectList = dwmisSubjectService
				.getSubjectInfoByTemplateId(templateId);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/dwmis/template/dwmisMenu");
		mv.addObject("templateId", templateId);
		mv.addObject("subjectList", subjectList);
		String templateName = request.getParameter("templateName");
		if (templateName != null && !"".equals(templateName)) {
			templateName = new String(templateName.getBytes("ISO-8859-1"),
					"UTF-8");
		}
		mv.addObject("templateName", templateName);
		return mv;
	}

	/**
	 * 准备拷贝模板
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/toCopy")
	public ModelAndView toCopy(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String templateId = request.getParameter("templateId");
		DwmisTemplateInfo dwmisTemplateInfo = dwmisTemplateInfoService
				.getDwmisTemplateInfoById(templateId);
		map.put("dwmisTemplateInfo", dwmisTemplateInfo);
		return new ModelAndView("dwmis/template/copyTemplate", map);
	}

	/**
	 * 拷贝模板
	 * 
	 * @param request
	 * @param response
	 * @param dwmisTemplateInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/copy")
	public ModelAndView copyTemplate(HttpServletRequest request,
			HttpServletResponse response, DwmisTemplateInfo dwmisTemplateInfo)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		boolean isSuccess = true;
		// 复制模板
		try {
			if (dwmisTemplateInfo != null) {
				dwmisTemplateInfoService
						.saveCopyTemplateInfo(dwmisTemplateInfo);
			} else {
				isSuccess = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			isSuccess = false;
		}
		mv.addObject("msg", isSuccess ? this.isSuccess : this.isFailed);
		mv.setViewName(SUCCESS_ACTION);
		return mv;
	}

	/**
	 * 删除模板
	 * 
	 * @param out
	 * @param request
	 * @param response
	 */
	@RequestMapping("/del")
	public void delTemplate(HttpServletRequest request,
			HttpServletResponse response) {
		String templateId = request.getParameter("templateId");
		try {
			dwmisTemplateInfoService.deleteTemplate(templateId);
			this.sendMsgToClient(this.isSuccess, response);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			this.sendMsgToClient(this.isFailed, response);
		}
	}

	/**
	 * 进入授权
	 * 
	 * @param templateId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("accessControl{templateId}")
	public ModelAndView accessControl(@PathVariable String templateId,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (templateId == null) {
			this.logger.warn("进入瞭望塔授权时传递的模板id为null");
			return new ModelAndView(this.NOT_FOUND_ACTION);
		}
		List<User> ulist = dwmisPopedomService.listTBUsers();
		List<Role> rlist = dwmisPopedomService.listTBRoles();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/dwmis/template/accessControl");
		mv.addObject("ulist", ulist);
		mv.addObject("rlist", rlist);
		mv.addObject("templateId", templateId);
		return mv;
	}

	/**
	 * 模板权限
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/managePopedom")
	public ModelAndView managePopedom(HttpServletRequest request,
			HttpServletResponse response) {
		String tid = request.getParameter("templateId");
		String[] roleIds = request.getParameterValues("roleIds");
		String[] userIds = request.getParameterValues("userIds");
		List<Role> rolelist = roleService.listAllRoles();
		List<User> userlist = userService.listAllUser();
		boolean isSuccess = true;
		if (roleIds != null && roleIds.toString() != "") {
			for (int i = 0; i < roleIds.length; i++) {
				if (rolelist != null && rolelist.size() > 0) {
					for (Role r : rolelist) {
						if (r.getRoleId().intValue() == Integer
								.parseInt(roleIds[i])) {
							rolelist.remove(r);
							break;
						}
					}
				}
				Role role = new Role();
				role.setRoleId(Integer.parseInt(roleIds[i]));
				role.setDwmisRights(tid);
				try {
					dwmisPopedomService.updateRoleTemplate(role);
				} catch (Exception e) {
					e.printStackTrace();
					this.logger.error(e.getMessage(), e);
					isSuccess = false;
					break;
				}
				role = roleService.getRoleById(role.getRoleId());
				this.insertLog(request, "更改角色" + role.getRoleName() + "瞭望台权限");
			}
			if (!isSuccess) {
				this.logger.warn("保存角色的瞭望台权限失败");
				ModelAndView mv = new ModelAndView();
				mv.addObject("msg", this.isFailed);
				mv.setViewName(SUCCESS_ACTION);
				return mv;
			}
		}
		if (userIds != null && userIds.toString() != "") {
			for (int i = 0; i < userIds.length; i++) {
				if (userlist != null && userlist.size() > 0) {
					for (User u : userlist) {
						if (u.getUserId().intValue() == Integer
								.parseInt(userIds[i])) {
							userlist.remove(u);
							break;
						}
					}
				}
				User user = new User();
				user.setUserId(Integer.parseInt(userIds[i]));
				user.setDwmisRights(tid);
				try {
					dwmisPopedomService.updateUserTemplate(user);
				} catch (Exception e) {
					e.printStackTrace();
					this.logger.error(e.getMessage(), e);
					isSuccess = false;
					break;
				}
				user = userService.getUserById(user.getUserId());
				this.insertLog(request, "更改用户" + user.getUsername() + "瞭望台权限");
			}
			if (!isSuccess) {
				this.logger.warn("保存用户的瞭望台权限失败");
				ModelAndView mv = new ModelAndView();
				mv.addObject("msg", this.isFailed);
				mv.setViewName(SUCCESS_ACTION);
				return mv;
			}
		}
		if (userlist != null && userlist.size() > 0) {
			for (User u : userlist) {
				this.logger.info("设置当前用户没有权限：" + u.getLoginname());
				this.logger.info("设置当前用户没有权限,原来的模板ID：" + tid);
				// 没有权限
				u.setDwmisRights(tid);
				try {
					dwmisPopedomService.updateUserNoTemplate(u);
				} catch (Exception e) {
					e.printStackTrace();
					this.logger.error(e.getMessage(), e);
					isSuccess = false;
					break;
				}
				this.insertLog(request, "更改用户" + u.getUsername() + "瞭望台权限");
			}
			if (!isSuccess) {
				this.logger.warn("保存角色的瞭望台权限失败");
				ModelAndView mv = new ModelAndView();
				mv.addObject("msg", this.isFailed);
				mv.setViewName(SUCCESS_ACTION);
				return mv;
			}
		}
		if (rolelist != null && rolelist.size() > 0) {
			for (Role r : rolelist) {
				r.setDwmisRights(tid);
				try {
					dwmisPopedomService.updateRoleNpTemplate(r);
				} catch (Exception e) {
					e.printStackTrace();
					this.logger.error(e.getMessage(), e);
					isSuccess = false;
					break;
				}
				this.insertLog(request, "更改角色" + r.getRoleName() + "瞭望台权限");
			}
			if (!isSuccess) {
				this.logger.warn("保存用户的瞭望台权限失败");
				ModelAndView mv = new ModelAndView();
				mv.addObject("msg", this.isFailed);
				mv.setViewName(SUCCESS_ACTION);
				return mv;
			}
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("msg", "success");
		mv.setViewName(SUCCESS_ACTION);
		return mv;
	}
}
