package com.infosmart.controller.dwmis;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.controller.BaseController;
import com.infosmart.po.dwmis.DwmisLegendCategory;
import com.infosmart.po.dwmis.DwmisLegendInfo;
import com.infosmart.po.dwmis.DwmisModuleInfo;
import com.infosmart.po.dwmis.DwmisSubjectInfo;
import com.infosmart.po.dwmis.DwmisTemplateInfo;
import com.infosmart.service.dwmis.DwmisModuleService;
import com.infosmart.service.dwmis.DwmisSubjectService;
import com.infosmart.service.dwmis.DwmisTemplateInfoService;
import com.infosmart.service.dwmis.LegendCategoryService;
import com.infosmart.service.dwmis.LegendInfoService;
import com.infosmart.util.StringUtils;

@Controller
public class DwmisModuleController extends BaseController {

	@Autowired
	private DwmisModuleService dwmisModuleService;
	@Autowired
	private DwmisSubjectService dwmisSubjectService;
	@Autowired
	private LegendCategoryService legendCategoryService;
	@Autowired
	private LegendInfoService legendInfoService;
	@Autowired
	private DwmisTemplateInfoService dwmisTemplateInfoService;

	/**
	 * 根据主题Id获取关联的模块信息
	 * 
	 * @param res
	 * @param rsp
	 * @return
	 */
	@RequestMapping("/dwmisModule/getModuleInfos")
	public ModelAndView getModuleInfos(HttpServletRequest req,
			HttpServletResponse res) {
		String subjectId = req.getParameter("subjectId");
		String templateId = req.getParameter("templateId");
		ModelAndView mv = new ModelAndView();
		// 所有模块信息
		List<DwmisModuleInfo> moduleInfos = dwmisModuleService
				.getModluleInfoListBySubjectIdAndTemplateId(subjectId,
						templateId);
		// 主题信息
		DwmisSubjectInfo subjectInfo = dwmisSubjectService
				.getSubjectInfoById(subjectId);
		// 模板信息
		DwmisTemplateInfo dwmisTemplateInfo = dwmisTemplateInfoService
				.getDwmisTemplateInfoById(templateId);
		mv.addObject("dwmisTemplateInfo", dwmisTemplateInfo);
		mv.addObject("moduleInfos", moduleInfos);
		mv.addObject("subjectInfo", subjectInfo);
		mv.addObject("subjectId", subjectId);
		mv.setViewName("dwmis/module/editIndexModule");
		return mv;
	}

	/**
	 * 跳转到编辑页面
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/dwmisModule/edit")
	public ModelAndView updateModleInfo(HttpServletRequest req,
			HttpServletResponse res) {
		String moduleId = req.getParameter("moduleId");
		ModelAndView mv = new ModelAndView();
		List<DwmisLegendInfo> legendInfoUnRelList = null;
		List<DwmisLegendInfo> legendInfoRelList = null;
		// 新增时列出所有图例
		// if (!StringUtils.notNullAndSpace(moduleId)) {
		legendInfoUnRelList = legendInfoService.getAllLegendInfos();
		// } else {
		// legendInfoUnRelList = dwmisModuleService
		// .getUnRelLegendInfoByModuleId(moduleId);
		// 与该模块关联图例
		legendInfoRelList = dwmisModuleService
				.getRelLegendInfoByModuleId(moduleId);
		// }
		// 图例分类
		List<DwmisLegendCategory> legendCategoryList = legendCategoryService
				.getAllCategory();
		mv.addObject("legendInfoUnRelList", legendInfoUnRelList);
		mv.addObject("legendInfoRelList", legendInfoRelList);
		mv.addObject("legendCategoryList", legendCategoryList);
		mv.addObject("moduleId", moduleId);
		mv.addObject("subjectId", req.getParameter("subjectId"));
		mv.setViewName("dwmis/module/moduleInfo");
		return mv;
	}

	/**
	 * 保存模块信息
	 * 
	 * @param req
	 * @param res
	 * @param moduleInfo
	 * @return
	 */
	@RequestMapping(value = "/dwmisModule/saveModuleInfo", method = RequestMethod.POST)
	public ModelAndView saveModuleInfo(HttpServletRequest req,
			HttpServletResponse res, DwmisModuleInfo moduleInfo) {
		ModelAndView mv = new ModelAndView();
		// 如果ModuleId为空则为新增 否则为修改
		if (!StringUtils.notNullAndSpace(moduleInfo.getModuleId())) {
			try {
				dwmisModuleService.saveModuleInfo(moduleInfo);
				mv.addObject("msg", "success");
			} catch (Exception e) {
				this.logger.error("保存模块失败！");
				this.logger.error(e.getMessage(), e);
				e.printStackTrace();
				mv.addObject("msg", "failed");
				mv.addObject("exception", e);
			}
		} else {
			try {
				dwmisModuleService.updateModuleInfo(moduleInfo);
				mv.addObject("msg", "success");
			} catch (Exception e) {
				this.logger.error("保存模块失败！");
				this.logger.error(e.getMessage(), e);
				e.printStackTrace();
				mv.addObject("msg", "failed");
				mv.addObject("exception", e);
			}
		}
		mv.setViewName(this.SUCCESS_ACTION);
		return mv;
	}

	/**
	 * 删除模块
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping("/dwmisModule/deleteModuleInfo")
	public void deleteModuleInfo(HttpServletRequest req, HttpServletResponse res) {
		String moduleId = req.getParameter("moduleId");
		boolean isSuccess = false;
		try {
			dwmisModuleService.deleteModuleInfo(moduleId);
			isSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
		this.sendMsgToClient(isSuccess ? "1" : "0", res);
	}
}
