package com.infosmart.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.po.DwpasCKpiInfo;
import com.infosmart.po.DwpasCSysType;
import com.infosmart.po.MisTypeInfo;
import com.infosmart.service.ParentTypeRelKpiService;

/**
 * 大类指标与小类指标关联
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/typeRelKpi")
public class ParentTypeRelKpiController extends BaseController {
	@Autowired
	private ParentTypeRelKpiService parentTypeRelKpiService;

	@RequestMapping("/allParentType")
	public ModelAndView getAllParentType(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		// 所有大类指标
		List<DwpasCSysType> parentTypes = parentTypeRelKpiService
				.getAllParentKpi();
		mv.addObject("parentTypes", parentTypes);
		mv.setViewName("admin/perentRalKpi/perentRalKpi");
		return mv;
	}

	/**
	 * 查询与此大类指标相关联的指标信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getRelKpi")
	public void getRelRelKpi(HttpServletRequest request,
			HttpServletResponse response) {
		String typeId = request.getParameter("typeId");
		List<DwpasCKpiInfo> relKpiInfos;
		try {
			relKpiInfos = parentTypeRelKpiService.getRelativeKpi(typeId);
			this.sendJsonMsgToClient(relKpiInfos, response);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 查询与此大类指标不关联的指标信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getUnRelKpi")
	public void getUnRelKpi(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String typeId = request.getParameter("typeId");
		
		List<DwpasCKpiInfo> allUnRelKpiInfos = parentTypeRelKpiService
				.getUnRelativeKpi(typeId);
		
	    this.sendJsonMsgToClient(allUnRelKpiInfos, response);
		

	}
	/**
	 * 点击搜索获得未关联指标列表(所有指标除去关联列表里的指标)
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/searchUnRelKpi")
	public void searchUnRelKpi(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		//搜索条件
		String keyCode = request.getParameter("keyCode");
		if (keyCode != null) {
			keyCode = keyCode.trim();
			keyCode = new String(keyCode.getBytes("ISO-8859-1"), "UTF-8");
		}
		// 获取已关联指标Code
		String relKpiCode = request.getParameter("relKpiCode");
		List<String> kpiCodeList = new ArrayList<String>();
		String[] str = null;
		if(relKpiCode!=null &&relKpiCode.length()>0){
			str = relKpiCode.split(",");
		}
		if(str!=null){
			for(int i=0; i<str.length; i++){
				kpiCodeList.add(str[i]);
			}
		}else{
			kpiCodeList.add("");
		}
		
		List<DwpasCKpiInfo> searchUnRelKpiList = null;
		try {
			searchUnRelKpiList = parentTypeRelKpiService.getUnRelKpiBySerach( keyCode, kpiCodeList);
			
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("查询未关联指标失败："+e.getMessage(), e);

		}
		this.sendJsonMsgToClient(searchUnRelKpiList, response);
	}

	/**
	 * 更新此大类指标的关联指标
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping("/update")
	public void updateRelation(HttpServletRequest req, HttpServletResponse res) {

		String typeId = req.getParameter("typeId");
		String relKpiCode = req.getParameter("relKpiCode");
		String unRelKpiCode = req.getParameter("unRelCode");
		String[] relkpiCodeList = null;
		String[] unRelKpiCodeList = null;
		if (relKpiCode != null && relKpiCode.length() > 0) {
			relkpiCodeList = relKpiCode.split(",");
		}
		if (unRelKpiCode != null && unRelKpiCode.length() > 0) {
			unRelKpiCodeList = unRelKpiCode.split(",");
		}

		if (typeId != null) {
			typeId = typeId.trim();
			try {
				if (relkpiCodeList != null) {
					parentTypeRelKpiService.updateRelateKpiInfo(typeId,
							Arrays.asList(relkpiCodeList));
				}
				if (unRelKpiCodeList != null) {
					parentTypeRelKpiService.updateUnRelateKpiInfo(typeId,
							Arrays.asList(unRelKpiCodeList));
				}
				this.sendMsgToClient("success", res);
			} catch (Exception e) {
				this.sendMsgToClient("failed", res);
				e.printStackTrace();
			}
		} else {
			this.logger.warn("更新此大类指标的关联指标失败，typeId为null");
			this.sendMsgToClient(isFailed, res);
			return;
		}
	}

	/**
	 * 进入新增页面
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/insert")
	public ModelAndView insertParentKpi(HttpServletRequest req,
			HttpServletResponse res) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/perentRalKpi/perentKpi_add");
		return mav;
	}

	/**
	 * 进入编辑页面
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView updateParentKpi(HttpServletRequest req,
			HttpServletResponse res) {
		String typeId = req.getParameter("typeId");
		MisTypeInfo parentKpi = parentTypeRelKpiService
				.getParentKpiByid(typeId);
		ModelAndView mav = new ModelAndView();
		mav.addObject("parentKpi", parentKpi);
		mav.setViewName("/admin/perentRalKpi/perentKpi_update");
		return mav;
	}

	/**
	 * 保存修改或新增
	 * 
	 * @param req
	 * @param res
	 * @param parentKpi
	 * @return
	 */
	@RequestMapping(value = "/save")
	public ModelAndView saveParentKpi(HttpServletRequest req,
			HttpServletResponse res, MisTypeInfo parentKpi) {
		ModelAndView mav = new ModelAndView();
		String temp = req.getParameter("temp");
		mav.setViewName("/common/save_result");
		boolean isSuccuss = false;
		if (parentKpi == null) {
			this.logger.warn("保存失败,参数parentKpi为null");
			mav.addObject("msg", isFailed);
			return mav;
		} else {
			try {
				if (temp.equals("1")) {
					isSuccuss = parentTypeRelKpiService
							.insertParentKPI(parentKpi);
					mav.addObject("msg", isSuccuss ? "success" : "failed");
				} else {
					isSuccuss = parentTypeRelKpiService
							.updateParentKpi(parentKpi);
					mav.addObject("msg", isSuccuss ? "success" : "failed");
				}
			} catch (Exception e) {
				e.printStackTrace();
				mav.addObject("msg", isFailed);
				this.logger.error("保存失败：" + e.getMessage(), e);
			}
		}
		return mav;
	}

	/**
	 * 删除
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping(value = "/delete")
	public void deleteParentKpi(HttpServletRequest req, HttpServletResponse res) {
		String typeId = req.getParameter("typeId");
		try {
			parentTypeRelKpiService.deleteParentKpi(typeId);
			this.sendMsgToClient(isSuccess, res);
		} catch (Exception e) {
			this.logger.error("删除大类指标信息失败：" + e.getMessage(), e);
			e.printStackTrace();
			this.sendMsgToClient(isFailed, res);
		}

	}

}
