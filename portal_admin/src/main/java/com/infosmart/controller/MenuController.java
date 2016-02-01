package com.infosmart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.infosmart.po.Menu;
import com.infosmart.service.MenuService;

@Controller
@RequestMapping(value = "/menu")
public class MenuController extends BaseController {

	@Autowired
	private MenuService menuService;

	/**
	 * 显示菜单列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping
	public String list(Model model, HttpServletRequest request) {
		List<Menu> menuList = menuService.listAllParentMenu();
		model.addAttribute("menuList", menuList);
		this.insertLog(request, "查看菜单");
		return "menu/menus";
	}

	/**
	 * 请求新增菜单页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String toAdd(Model model) {
		List<Menu> menuList = menuService.listAllParentMenu();
		model.addAttribute("menuList", menuList);
		return "menu/menu_info";
	}

	/**
	 * 请求编辑菜单页面
	 * 
	 * @param menuId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit{menuId}")
	public String toEdit(@PathVariable Integer menuId, Model model) {
		if (menuId == null) {
			this.logger.warn("编辑菜单时传递的菜单id为null");
		}
		Menu menu = menuService.getMenuById(menuId);
		model.addAttribute("menu", menu);
		if (menu.getParentId() != null && menu.getParentId().intValue() > 0) {
			List<Menu> menuList = menuService.listAllParentMenu();
			model.addAttribute("menuList", menuList);
		}
		return "menu/menu_info";
	}

	/**
	 * 保存菜单信息
	 * 
	 * @param menu
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save")
	public String save(Menu menu, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		if (menu == null) {
			this.logger.warn("保存菜单时传递的菜单对象为null");
		}
		try {
			menuService.saveMenu(menu);
			model.addAttribute("msg", "success");
			this.insertLog(request, "编辑菜单信息");
		} catch (Exception e) {

			model.addAttribute("msg", "failed");
		}

		return "common/save_result";
	}

	/**
	 * 获取当前菜单的所有子菜单
	 * 
	 * @param menuId
	 * @param response
	 */
	@RequestMapping(value = "/sub{menuId}")
	public void getSub(@PathVariable Integer menuId,
			HttpServletResponse response) {
		if (menuId == null) {
			this.logger.warn("获取当前菜单的子菜单时传递的menuid为null");
		}
		List<Menu> subMenuList = menuService.listSubMenuByParentId(menuId);
		JSONArray arr = JSONArray.fromObject(subMenuList);
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
	 * 删除菜单
	 * 
	 * @param menuId
	 * @param out
	 */
	@RequestMapping(value = "/del{menuId}")
	public void delete(@PathVariable Integer menuId, PrintWriter out,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		if (menuId == null) {
			this.logger.warn("删除菜单时传递的menuId为null");
			this.sendMsgToClient(isFailed, response);
			return;
		}
		try {
			menuService.deleteMenuById(menuId);
			out.write("success");
			this.insertLog(request, "删除菜单");
		} catch (Exception e) {

			out.write("failed");
			this.insertLog(request, "删除菜单失败");
		}
		out.flush();
		out.close();

	}

	/**
	 * 校验菜单名的唯一性
	 */
	@RequestMapping("/alidateMenuName")
	public void alidateMenuName(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String menuName = req.getParameter("menuName");
		menuName = new String(menuName.getBytes("ISO-8859-1"), "UTF-8");
		Menu menu = menuService.getMenuByName(menuName);
		if (menu == null) {
			this.sendMsgToClient("0", res);
		} else {
			this.sendMsgToClient("1", res);
		}
	}
}
