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

import com.infosmart.po.KpiInfo;
import com.infosmart.po.MisTypeInfo;
import com.infosmart.service.KpiInfoService;

@Controller
@RequestMapping("/kpiinfo")
public class KpiInfoController extends BaseController {
	private final String SUCCESS_ACTION = "/common/save_result";
	@Autowired
	private KpiInfoService kpiInfoService;

	/**
	 * 指标管理默认页面
	 * 
	 * @param modelMap
	 */
	@RequestMapping
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response, KpiInfo kpiinfo) {
		List<KpiInfo> kpiList = kpiInfoService.queryKpiInfos(kpiinfo);
		List<MisTypeInfo> cSysTypeDOList = kpiInfoService.qryCSysType();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/kpiinfo/kpiinfo");
		mav.addObject("kpiList", kpiList);
		mav.addObject("kpiinfo", kpiinfo);
		mav.addObject("cSysTypeDOList", cSysTypeDOList);
		return mav;
	}

	/** 进入新增 */
	@RequestMapping(value = "/add")
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<MisTypeInfo> cSysTypeDOList = kpiInfoService.qryCSysType();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/kpiinfo/kpiinfo_add");
		mav.addObject("cSysTypeDOList", cSysTypeDOList);
		return mav;
	}

	/** 进入更新 */
	@RequestMapping(value = "/edit{kpiCode}")
	public ModelAndView edit(@PathVariable String kpiCode,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if(StringUtils.isBlank(kpiCode)){
			this.logger.warn("更新kpiInfo时传递的kpiCode为null");
		}
		KpiInfo kpiinfo = kpiInfoService.queryKpiInfoByCode(kpiCode);
		List<MisTypeInfo> cSysTypeDOList = kpiInfoService.qryCSysType();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/kpiinfo/kpiinfo_edit");
		mav.addObject("cSysTypeDOList", cSysTypeDOList);
		mav.addObject("kpiinfo", kpiinfo);
		return mav;
	}

	/**
	 * 保存
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveKpiinfo(HttpServletRequest request,
			HttpServletResponse response, KpiInfo kpiinfo)
			throws IllegalAccessException, InvocationTargetException {
		ModelAndView mv = new ModelAndView();
		// String isCal = "";// 是否符合计算规则
		List<MisTypeInfo> cSysTypeDOList = kpiInfoService.qryCSysType();
		KpiInfo existKpiInfo = null;
		if (kpiinfo != null) {
			existKpiInfo = kpiInfoService.queryKpiInfoByCode(kpiinfo.getKpiCode());
		}
		if (existKpiInfo == null) {
			// isCal = kpiInfoService.checkRule(kpiinfo.getRoleFormula());
			kpiInfoService.insertKpiInfo(kpiinfo);
			mv.addObject("msg", "success");
		} else {
			kpiInfoService.updateKpiInfo(kpiinfo);
			mv.addObject("msg", "success");
		}
		mv.setViewName(SUCCESS_ACTION);
		mv.addObject("cSysTypeDOList", cSysTypeDOList);
		return mv;
	}

	/** 删除 */
	@RequestMapping(value = "/delete{kpiCode}")
	public void delete(@PathVariable String kpiCode, PrintWriter out) {
		if(StringUtils.isBlank(kpiCode)){
			this.logger.warn("删除指标信息时传递的kpiCode为null");
		}
		kpiInfoService.deleteKpiInfoByCode(kpiCode);
		out.write("success");
		out.close();
	}

}
