package com.infosmart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.po.DwpasCColumnInfo;
import com.infosmart.po.DwpasCSystemMenu;
import com.infosmart.po.DwpasCmoduleInfo;
import com.infosmart.service.DwpasCColumnInfoService;
import com.infosmart.service.DwpasCModuleInfoService;
import com.infosmart.service.DwpasCSystemMenuService;
import com.infosmart.service.DwpasRColumnComKpiService;
import com.infosmart.service.MenuService;
import com.infosmart.util.Const;

@Controller
public class DwpasCSystemMenuController extends BaseController {
	@Autowired
	private DwpasCSystemMenuService memuService;
	@Autowired
	private DwpasCModuleInfoService moduleService;
	@Autowired
	private DwpasCColumnInfoService columnInfoService;
	@Autowired
	private DwpasRColumnComKpiService ComKpiService;

	/**
	 * 获取当前系统所有父级菜单
	 * 
	 * @param templateId
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/dwpas/listJingWeiYiMenus{templateId}")
	public ModelAndView listJingWeiYiMenus(@PathVariable Long templateId,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		if (templateId == null) {
			this.logger.warn("获取当前系统所有父级菜单时传递的模板id为null");
		}
		//将templateId放入session，在后面页面获取产品列表用
		request.getSession().setAttribute(Const.SESSION_TEMPLATE_ID, templateId);
		
		List<DwpasCSystemMenu> parentlist = memuService
				.getAllParentMenu(templateId);
		List<DwpasCSystemMenu> childlist = memuService
				.getChildMenus(templateId);
		// List<DwpasCSystemMenu> plist =
		// memuService.getParentMenusAndDateType(templateId);
		// List<DwpasCSystemMenu> clist =
		// memuService.getChildMenusAndDateType(templateId);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/dwpas/dwpasMenu");
		mv.addObject("templateId", templateId);
		mv.addObject("parentlist", parentlist);
		mv.addObject("childlist", childlist);
		// mv.addObject("plist",plist );
		String templateName = request.getParameter("templateName");
		if (templateName != null && !"".equals(templateName)) {
			templateName = new String(templateName.getBytes("ISO-8859-1"),
					"UTF-8");
		}
		mv.addObject("templateName", templateName);
		// mv.addObject("clist",clist );
		return mv;
	}

	/**
	 * 获取当前菜单的子菜单
	 * 
	 * @param menuId
	 * @param response
	 */
	@RequestMapping(value = "/dwpas/childMenus{menuId}")
	public void getChildMenus(@PathVariable String menuId,
			HttpServletResponse response) {
		if (StringUtils.isBlank(menuId)) {
			this.logger.warn("获取当前菜单的子菜单时传递的菜单id为null");
			return;
		}
		List<DwpasCSystemMenu> memulist = memuService
				.getAllChildrenMenu(menuId);
		JSONArray arr = new JSONArray();
		if(memulist!=null && memulist.size()>0){
			arr = JSONArray.fromObject(memulist);
		}
		PrintWriter out;
		try {
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			String json = arr.toString();
			out.write(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除子菜单
	 * 
	 * @param menuId
	 * @param out
	 */
	@RequestMapping(value = "/dwpas/delSystemMenu{menuId}")
	public void deleteSystemMenu(@PathVariable String menuId, PrintWriter out,
			HttpServletResponse response)throws Exception {
		response.setContentType("text/plain");
		if (StringUtils.isBlank(menuId)) {
			this.logger.warn("删除菜单时传递的菜单id为null");
			this.sendMsgToClient(isFailed, response);
			return;
		}
		memuService.deleteMenu(menuId);
		String[] moduleids = null;
		String[] columnids = null;
		List<DwpasCmoduleInfo> moduleList = moduleService
				.getDwpasCmoduleInfoByMenuIds(Arrays.asList(menuId));
		moduleService.deleteDwpasCmoduleInfo(Arrays.asList(menuId));
		if (moduleList != null && moduleList.size() > 0) {
			moduleids = new String[moduleList.size()];
			for (int i = 0; i < moduleList.size(); i++) {
				moduleids[i] = moduleList.get(i).getModuleId();
			}
		}
		if (moduleids != null && moduleids.length > 0) {
			List<DwpasCColumnInfo> columnList = columnInfoService
					.getBYModuleIds(Arrays.asList(moduleids));
			columnInfoService.deleteDwpasCColumnInfo(Arrays.asList(moduleids));// 删除栏目
			if (columnList != null && columnList.size() > 0) {
				columnids = new String[columnList.size()];
				for (int i = 0; i < columnList.size(); i++) {
					columnids[i] = columnList.get(i).getColumnId();
				}
			}
		}
		if (columnids != null && columnids.length > 0) {
			ComKpiService.deleteDwpasRColumnComKpi(Arrays.asList(columnids));
		}
		out.write("success");
		out.flush();
		out.close();
	}

	/**
	 * 删除父菜单以及子菜单
	 * 
	 * @param menuId
	 * @param out
	 * @param response
	 */
	@RequestMapping(value = "/dwpas/delParentAndChildMenu{menuId}")
	public void delParentAndChildMenu(@PathVariable String menuId,
			PrintWriter out, HttpServletResponse response) throws Exception {
		response.setContentType("text/plain");
		if (StringUtils.isBlank(menuId)) {
			this.logger.warn("删除菜单时传递的菜单id为null");
			this.sendMsgToClient(isFailed, response);
			return;
		}
		List<String> mids = new ArrayList<String>();// 父子菜单ID集合
		List<DwpasCSystemMenu> childlist = memuService
				.getAllChildrenMenu(menuId);
		mids.add(menuId);
		if (childlist != null && childlist.size() > 0) {
			for (DwpasCSystemMenu menu : childlist) {
				mids.add(menu.getMenuId());
			}
		}
		memuService.deleteParentAndChildMenu(mids);
		String[] moduleids = null;
		String[] columnids = null;
		List<DwpasCmoduleInfo> moduleList = moduleService
				.getDwpasCmoduleInfoByMenuIds(mids);
		moduleService.deleteDwpasCmoduleInfo(Arrays.asList(menuId));
		if (moduleList != null && moduleList.size() > 0) {
			moduleids = new String[moduleList.size()];
			for (int i = 0; i < moduleList.size(); i++) {
				moduleids[i] = moduleList.get(i).getModuleId();
			}
		}
		if (moduleids != null && moduleids.length > 0) {
			List<DwpasCColumnInfo> columnList = columnInfoService
					.getBYModuleIds(Arrays.asList(moduleids));
			columnInfoService.deleteDwpasCColumnInfo(Arrays.asList(moduleids));// 删除栏目
			if (columnList != null && columnList.size() > 0) {
				columnids = new String[columnList.size()];
				for (int i = 0; i < columnList.size(); i++) {
					columnids[i] = columnList.get(i).getColumnId();
				}
			}
		}
		if (columnids != null && columnids.length > 0) {
			ComKpiService.deleteDwpasRColumnComKpi(Arrays.asList(columnids));
		}
		out.write("success");
		out.flush();
		out.close();
	}

	/**
	 * 新增菜单
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/dwpas/addMenu{templateId}")
	public ModelAndView addMenu(@PathVariable Long templateId,
			HttpServletRequest req, HttpServletResponse res) {
		ModelAndView mv = new ModelAndView();
		// 目前只考虑到二级菜单，无三级菜单,列表经纬仪前台所有父级菜单
		List<DwpasCSystemMenu> menuList = new ArrayList<DwpasCSystemMenu>();
		menuList = memuService.getAllParentMenu(templateId);
		mv.addObject("menuList", menuList);
		mv.addObject("templateId", templateId);
		mv.setViewName("/admin/dwpas/addmenu");
		return mv;
	}

	/**
	 * 保存菜单信息
	 * 
	 * @param req
	 * @param res
	 * @param menu
	 * @return
	 */
	@RequestMapping(value = "/dwpas/saveMenu")
	public ModelAndView saveMenu(HttpServletRequest req,
			HttpServletResponse res, DwpasCSystemMenu menu)throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(SUCCESS_ACTION);
		if (menu == null) {
			this.logger.warn("保存时传递的menu对象为null");
			this.sendMsgToClient(isFailed, res);
			return mv;
		}
		menu.setMenuOrder(new Integer(99));
		menu.setIsShow(new Integer(1));
		if (menu.getTemplateId() == null) {
			mv.addObject("msg", "failed");
		} else {
			String menuCode = unieqeNumberChar();
			menu.setMenuCode(menuCode);
			menu.setMenuId(menu.getTemplateId() + "_" + menuCode);
			memuService.saveDwpascMenu(menu);
			mv.addObject("msg", "success");
		}
		return mv;
	}

	/**
	 * 生成确保不重复的由字母和数字组成的随机码
	 * 
	 * @return
	 */
	private String unieqeNumberChar() {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < 10; i++)
			list.add(i);
		for (char c = 'a'; c <= 'z'; c++)
			list.add(c);
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < Math.random() * 36 + 1; i++) {
			int mathInt;
			mathInt = (int) (Math.random() * list.size());
			s.append(list.get(mathInt));
			list.remove(mathInt);
		}
		return s.toString();
	}
}
