package com.infosmart.controller.dwmis;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.controller.BaseController;
import com.infosmart.po.dwmis.DwmisLegendCategory;
import com.infosmart.service.dwmis.LegendCategoryService;

@Controller
@RequestMapping("/LegendCategory")
public class LegendCategoryController extends BaseController {

	@Autowired
	private LegendCategoryService legendCategoryService;

	/**
	 * 图例分类列表
	 * 
	 * @param request
	 * @param dwmisLegendCategory
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView getList(HttpServletRequest request,
			DwmisLegendCategory dwmisLegendCategory) {
		Map<String, Object> map = new HashMap<String, Object>();
		String pid = dwmisLegendCategory.getCategoryPid();
		String name = dwmisLegendCategory.getCategoryName();
		if (pid != null && !"".equals(pid)) {
			dwmisLegendCategory.setCategoryPid(pid.trim());
		}
		if (name != null && !"".equals(name)) {
			dwmisLegendCategory.setCategoryName(name.trim());
		}
		List<DwmisLegendCategory> list = legendCategoryService
				.list(dwmisLegendCategory);
		map.put("dwmisLegendCategory", dwmisLegendCategory);
		map.put("list", list);
		return new ModelAndView("/dwmis/legendCategory/legendCategory", map);
	}

	/**
	 * 准备新增图例分类
	 * 
	 * @param request
	 * @param dwmisLegendCategory
	 * @return
	 */
	@RequestMapping("/add")
	public ModelAndView toadd(HttpServletRequest request,
			DwmisLegendCategory dwmisLegendCategory) {

		return new ModelAndView("/dwmis/legendCategory/legendCategory_info");
	}

	/**
	 * 准备修改图例分类
	 * 
	 * @param categoryId
	 * @return
	 */
	@RequestMapping(value = "/edit{categoryId}")
	public ModelAndView toEdit(@PathVariable String categoryId) {
		Map<String, Object> map = new HashMap<String, Object>();
		DwmisLegendCategory dwmisLegendCategory = legendCategoryService
				.getById(categoryId);
		map.put("dwmisLegendCategory", dwmisLegendCategory);
		return new ModelAndView("/dwmis/legendCategory/legendCategory_info",
				map);
	}

	/**
	 * 保存图例分类信息
	 * 
	 * @param request
	 * @param dwmisLegendCategory
	 * @return
	 */
	@RequestMapping("/save")
	public String saveLegendCategory(HttpServletRequest request,
			DwmisLegendCategory dwmisLegendCategory) {
		if (dwmisLegendCategory.getCategoryId() == null
				|| dwmisLegendCategory.getCategoryId() == "") {
			String id = UUID.randomUUID().toString();
			dwmisLegendCategory.setCategoryId(id);
			try {
				legendCategoryService.add(dwmisLegendCategory);
				request.setAttribute("msg", this.isSuccess);
			} catch (Exception e) {
				e.printStackTrace();
				this.logger.error(e.getMessage(), e);
				request.setAttribute("msg", this.isFailed);
			}
		} else {
			try {
				legendCategoryService.edit(dwmisLegendCategory);
				request.setAttribute("msg", this.isSuccess);
			} catch (Exception e) {
				e.printStackTrace();
				this.logger.error(e.getMessage(), e);
				request.setAttribute("msg", this.isFailed);
			}
		}
		return "common/save_result";
	}

	/**
	 * 删除图例分类
	 * 
	 * @param categoryId
	 * @param request
	 * @param out
	 * @param response
	 */
	@RequestMapping("/del{categoryId}")
	public void delLegendCategory(@PathVariable String categoryId,
			HttpServletRequest request, PrintWriter out,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		try {
			legendCategoryService.del(categoryId);
			out.write("success");
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			out.write("failed");
		}
		out.close();

	}
}
