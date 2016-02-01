package com.infosmart.portal.action.dwmis;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.portal.action.BaseController;
import com.infosmart.portal.pojo.dwmis.DwmisModuleInfo;
import com.infosmart.portal.pojo.dwmis.DwmisSubjectInfo;
import com.infosmart.portal.service.dwmis.DwmisModuleService;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.dwmis.CoreConstant;

@Controller
public class DwmisHomePageController extends BaseController {

	@Autowired
	DwmisModuleService dwmisModuleService;

	/**
	 * 显示图形页面
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/dwmisHomePage/gotoHomePage")
	public ModelAndView toIndexPag(HttpServletRequest req,
			HttpServletResponse res) {
		ModelAndView mv = new ModelAndView();
		// 主题ID
		String subjectId = req.getParameter("subjectId");
		// 日期类型
		String dateType = req.getParameter("dateType");
		if (!StringUtils.notNullAndSpace(dateType)) {
			// 默认为当期值
			dateType = String.valueOf(CoreConstant.DAY_PERIOD);
		}
		// 查询日期
		String queryDate = req.getParameter("queryDate");
		if (StringUtils.notNullAndSpace(queryDate)) {
			this.setCrtQueryDateOfDwmis(req, queryDate);
		} else {
			queryDate = this.getCrtQueryDateOfDwmis(req);
		}
		// 该主题下的所有模块信息
		List<DwmisModuleInfo> moduleInfoList = dwmisModuleService
				.getModluleInfoListBySubjectId(subjectId);
		if (moduleInfoList == null || moduleInfoList.isEmpty()) {
			moduleInfoList = new ArrayList<DwmisModuleInfo>();
			this.logger.warn("当前主题" + subjectId + "没有关联任何模块");
		}
		// 当前主题信息
		DwmisSubjectInfo subjectInfo = dwmisModuleService
				.getSubjectInfoById(subjectId);
		mv.addObject("subjectInfo", subjectInfo);
		mv.addObject("moduleInfoList", moduleInfoList);
		mv.addObject("subjectId", subjectId);
		mv.addObject("dateType", Integer.valueOf(dateType));
		mv.addObject("queryDate", queryDate);
		mv.setViewName("/dwmis/index/showChart");
		return mv;
	}

	/**
	 * 所有主题信息
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/dwmisHomePage/getAllSubjects")
	public ModelAndView getAllSubjects(HttpServletRequest req,
			HttpServletResponse res) {
		ModelAndView mv = new ModelAndView();
		String queryDate = req.getParameter("queryDate");
		if (StringUtils.notNullAndSpace(queryDate)) {
			this.setCrtQueryDateOfDwmis(req, queryDate);
		} else {
			queryDate = this.getCrtQueryDateOfDwmis(req);
		}
		// 通过用户信息取得模板Id
		String templateId = this.getCrtUser(req.getSession()).getDwmisRights();
		if ("0".equals(templateId)) {
			templateId = this.getCrtUser(req.getSession()).getRole()
					.getDwmisRights();
		}
		// 该模版的所有主题信息
		List<DwmisSubjectInfo> subjectInfoList = dwmisModuleService
				.getAllSubjectByTempId(templateId);
		if (subjectInfoList == null || subjectInfoList.isEmpty()) {
			this.logger.warn("该模板未配置主题信息!");
			mv.setViewName("/dwmis/index/noModule");
		} else {
			String firstSubjectId = subjectInfoList.get(0).getSubjectId();
			mv.addObject("queryDate", queryDate);
			// mv.addObject("date",
			// DateUtils.fotmatDate4(TimeFormatProcessor.getSysDate(2)));
			mv.addObject("subjectInfoList", subjectInfoList);
			mv.addObject("firstSubjectId", firstSubjectId);
			mv.setViewName("/dwmis/index/dwmisIndex");
		}
		return mv;
	}
}
