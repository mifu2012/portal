package com.infosmart.controller.dwmis;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.controller.BaseController;
import com.infosmart.po.dwmis.DwmisSubjectInfo;
import com.infosmart.service.dwmis.DwmisSubjectService;

@Controller
@RequestMapping("/subjectInfo")
public class DwmisSubjectController extends BaseController {

	@Autowired
	private DwmisSubjectService dwmisSubjectService;

	/**
	 * 准备编辑主题
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/editSubject")
	public ModelAndView editSubject(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String subjectId = request.getParameter("subjectId");
		String templateId = request.getParameter("templateId");
		if (subjectId != null || "".equals(subjectId)) {
			DwmisSubjectInfo subjectInfo = dwmisSubjectService
					.getSubjectInfoById(subjectId);
			map.put("subjectInfo", subjectInfo);
		}
		map.put("templateId", templateId);
		return new ModelAndView("dwmis/subject/subject_info", map);
	}

	/**
	 * 保存主题编辑信息
	 * 
	 * @param request
	 * @param dwmisSubjectInfo
	 * @return
	 */
	@RequestMapping("/save")
	public String saveSubject(HttpServletRequest request,
			DwmisSubjectInfo dwmisSubjectInfo) {
		boolean isSuccess = true;
		try {
			if (dwmisSubjectInfo.getSubjectId() == null
					|| "".equals(dwmisSubjectInfo.getSubjectId())) {
				dwmisSubjectService.addSubjectInfo(dwmisSubjectInfo);
			} else {
				dwmisSubjectService.updateSubjectInfo(dwmisSubjectInfo);
			}
		} catch (Exception e) {
			isSuccess = false;
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
		request.setAttribute("msg", isSuccess ? this.isSuccess : this.isFailed);
		return this.SUCCESS_ACTION;
	}

	/**
	 * 删除主题
	 * 
	 * @param subjectId
	 * @param out
	 * @param request
	 * @param response
	 */
	@RequestMapping("/del{subjectId}")
	public void delete(@PathVariable String subjectId, PrintWriter out,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		if (subjectId == null) {
			this.logger.warn("删除菜单时传递的subjectId为null");
		}
		boolean isSuccess = true;
		try {
			dwmisSubjectService.deleteSubjectInfo(subjectId);
		} catch (Exception e) {
			isSuccess = false;
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
		out.write(isSuccess ? this.isSuccess : this.isFailed);
		out.flush();
		out.close();

	}

	/**
	 * 根据模板ID查询主题
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getSubjectByTemplateId")
	public String getSubjectByTemplateId(HttpServletRequest request,
			HttpServletResponse response) {
		String templateId = request.getParameter("templateId");
		List<DwmisSubjectInfo> subjectList = dwmisSubjectService
				.getSubjectInfoByTemplateId(templateId);
		JSONArray arr = JSONArray.fromObject(subjectList);
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
		return this.SUCCESS_ACTION;
	}

	/**
	 * 保存主题信息
	 * 
	 * @param request
	 * @param response
	 * @param subjectInfo
	 * @return
	 */
	@RequestMapping("/saveSubject")
	public ModelAndView saveIndexPageConfig(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("subjectManForm") DwmisSubjectInfo subjectInfo) {
		this.logger.info("------------>保存主题下的所有模块信息");
		ModelAndView mv = new ModelAndView(this.SUCCESS_ACTION);
		try {
			this.dwmisSubjectService.saveSubjectInfo(subjectInfo);
			mv.addObject("msg", this.isSuccess);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			mv.addObject("msg", this.isFailed);
		}
		return mv;
	}
}
