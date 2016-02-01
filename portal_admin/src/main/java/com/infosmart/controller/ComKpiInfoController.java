package com.infosmart.controller;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.po.ComKpiInfo;
import com.infosmart.service.ComKpiInfoService;

@Controller
@RequestMapping("/comkpiinfo")
public class ComKpiInfoController extends BaseController {
	private final String SUCCESS_ACTION = "/common/save_result";
	@Autowired
	private ComKpiInfoService comKpiInfoService;

	/**
	 * 通用指标管理默认页面
	 * 
	 * @param modelMap
	 */
	@RequestMapping
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response, ComKpiInfo comkpiinfo) {
		if(comkpiinfo == null){
			this.logger.warn("查询失败，参数comkpiinfo为空");
			return new ModelAndView("common/error");
		}
		List<ComKpiInfo> comkpiinfos = comKpiInfoService
				.listPageComKpiInfo(comkpiinfo);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/comkpiinfo/comkpiinfo");
		mav.addObject("comkpiinfos", comkpiinfos);
		mav.addObject("comkpiinfo", comkpiinfo);
		this.insertLog(request, "查询通用指标列表");
		return mav;
	}

	/** 进入新增 */
	@RequestMapping(value = "/add")
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/comkpiinfo/comkpiinfo_add");
	}

	/** 进入更新 */
	@RequestMapping(value = "/edit{comKpiCode}")
	public ModelAndView edit(@PathVariable String comKpiCode,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (StringUtils.isBlank(comKpiCode)) {
			this.logger.warn("通用指标更新失败，通用指标码为null");
			return new ModelAndView("common/error");
		}
		ComKpiInfo comkpiinfo = comKpiInfoService.getComkpiInfo(comKpiCode);
		return new ModelAndView("admin/comkpiinfo/comkpiinfo_edit",
				"comkpiinfo", comkpiinfo);
	}

	/**
	 * 保存
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveExample(HttpServletRequest request,
			HttpServletResponse response, ComKpiInfo comkpiinfo)
			throws IllegalAccessException, InvocationTargetException {
		if (comkpiinfo == null) {
			this.logger.warn("通用指标信息为null");
			return new ModelAndView("common/error");
		}
		ModelAndView mv = new ModelAndView();
		if (comkpiinfo != null) {
			ComKpiInfo comkpi = comKpiInfoService.getComkpiInfo(comkpiinfo
					.getComKpiCode());
			if (comkpi == null) {
				Boolean flag = comKpiInfoService.insertCommonKPIConfig(comkpiinfo);
				if(flag){
					mv.addObject("msg", "success");
					this.insertLog(request, "新增通用指标");
				}else{
					mv.addObject("msg", "failed");
				}
				
			} else {
				try {
					comKpiInfoService.updateComKpiInfo(comkpiinfo);
					this.insertLog(request, "修改通用指标");
					mv.addObject("msg", "success");
				} catch (Exception e) {

					e.printStackTrace();
					this.logger.error(e.getMessage(), e);
					mv.addObject("msg", "failed");
					
				}
				
				
			}
		}
		mv.setViewName(SUCCESS_ACTION);
		return mv;
	}

	/** 删除 */
	@RequestMapping(value = "/delete{comKpiCode}")
	public void delete(@PathVariable String comKpiCode, PrintWriter out,
			HttpServletRequest request, HttpServletResponse response) {
		if(comKpiCode==null||comKpiCode.length()==0){
			this.logger.warn("删除失败，参数comKpiCode为空！");
			this.sendMsgToClient(isFailed, response);
			return;
		}
		try {
			comKpiInfoService.deleteCommonKPI(comKpiCode);
			this.insertLog(request, "删除通用指标");
			this.sendMsgToClient(isSuccess, response);
		} catch (Exception e) {

			e.printStackTrace();
			this.sendMsgToClient(isFailed, response);
		}
		
		
		
	}
}
