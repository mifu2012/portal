package com.infosmart.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.po.DwpasCColumnInfo;
import com.infosmart.service.DwpasCColumnInfoService;

@Controller
public class DwpasCColumnInfoController extends BaseController{
	@Autowired
	private DwpasCColumnInfoService dwpasCColumnInfoService;
	private final String SUCCESS_ACTION = "/common/save_result";
	/**
	 * 栏目列表
	 * @param request
	 * @param response
	 * @param example
	 * @return
	 */
	@RequestMapping("/dwpas/listPageDwpasCColumnInfo")
	public ModelAndView listPageDwpasCColumnInfo(HttpServletRequest request,
			HttpServletResponse response, DwpasCColumnInfo columnInfo) {
		List<DwpasCColumnInfo> columnInfos = dwpasCColumnInfoService.listPageDwpasCColumnInfo(columnInfo);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/dwpas/columnInfo");
		mv.addObject("columnInfos", columnInfos);
		mv.addObject("columnInfo", columnInfo);
		return mv;
	}
	/**
	 */
	@RequestMapping("/dwpas/copyColumnInfo")
	public ModelAndView copyColumnInfo(HttpServletRequest request,
			HttpServletResponse response,DwpasCColumnInfo columnInfo) {
		//columnInfo.setIsShow(1);
		if(columnInfo!= null){
			columnInfo.setGmtCreate(new Date());
		}
		ModelAndView mv = new ModelAndView();
		//dwpasCColumnInfoService.saveDwpasCColumnInfo(columnInfo);
		mv.addObject("msg", "success");
		mv.setViewName(SUCCESS_ACTION);
		return mv;
	}

}
