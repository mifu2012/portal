package com.infosmart.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.infosmart.po.PrdDimInfo;
import com.infosmart.po.PrdMngInfo;
import com.infosmart.service.KpiInfoService;
import com.infosmart.service.PrdDimInfoService;
import com.infosmart.service.PrdMngInfoService;

@Controller
@RequestMapping("/prddim")
public class PrdDimInfoController extends BaseController {
	@Autowired
	private PrdDimInfoService prdDimManager; // 产品健康度信息service
	@Autowired
	private PrdMngInfoService prdInfoManager; // 产品信息管理service
	@Autowired
	private KpiInfoService kpiInfoManager; // 指标信息service

	/**
	 * 产品健康度信息管理默认页面
	 * 
	 * @param modelMap
	 */
	@RequestMapping
	public ModelAndView qryPrdInfoList(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PrdMngInfo> prdList = prdInfoManager.qryPrdInfo();
		map.put("prdList", prdList);
		this.insertLog(req, "查询产品健康度列表");
		return new ModelAndView("admin/prddim/prddim", map);

	}

	/** 进入更新 */
	@RequestMapping(value = "/editPrd{productId}")
	public ModelAndView editPrd(@PathVariable String productId,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if(StringUtils.isBlank(productId)){
			this.logger.warn("更新时传递的productId为null");
		}
		PrdDimInfo prddim = prdDimManager.qryPrdDimByProductId(productId);
		PrdMngInfo prdinfo = prdInfoManager.getPrdInfo(productId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/prdinfo/prddiminfo");
		mav.addObject("prddim", prddim);
		mav.addObject("prdinfo", prdinfo);
		mav.addObject("productId", productId);
		return mav;
	}

	/**
	 * 保存
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/savePrddim1", method = RequestMethod.POST)
	public ModelAndView savePrddim1(HttpServletRequest request,
			HttpServletResponse response, PrdDimInfo prddim,PrdMngInfo prdinfo)
			throws IllegalAccessException, InvocationTargetException {
		if(prddim == null || prdinfo == null){
			this.logger.warn("保存时传递的PrdDimInfo或者PrdMngInfo为null");
		}
		ModelAndView mv = new ModelAndView();
		PrdDimInfo prddim1 = prdDimManager.qryPrdDimByProductId(prdinfo
				.getProductId());
		if(prddim1 == null){
			prddim.setProductId(prdinfo.getProductId());
			prdDimManager.savePrdDim(prddim);
		}{
			prdDimManager.updatePrddimInfo(prddim);
		}
		mv.setViewName("admin/prdinfo/prddiminfo");
		mv.addObject("msg", "success");
		mv.addObject("prddim", prddim);
		mv.addObject("prddim1", prddim1);
		return mv;
	}

	/**
	 * 获得指定产品id的健康度信息
	 * 
	 * @param modelMap
	 * @param productId
	 *            产品id
	 * @throws Exception
	 */
	@RequestMapping("/qryPrdDimByProductId")
	public ModelAndView qryPrdDimByProductId(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 通过productId获得产品相关信息
		PrdDimInfo cPrdDimForm = new PrdDimInfo();
		PrdMngInfo dwpasCPrdInfoDO = prdInfoManager.getPrdInfo(cPrdDimForm
				.getProductId());
		// 获得指定产品id的健康度信息
		PrdDimInfo dwpasCPrdDimDO = prdDimManager
				.qryPrdDimByProductId(cPrdDimForm.getProductId());
		List<String> kpiList = new ArrayList<String>();
		if (dwpasCPrdDimDO != null) {
			kpiList.add(dwpasCPrdDimDO.getDim1Code());
			kpiList.add(dwpasCPrdDimDO.getDim2Code());
			kpiList.add(dwpasCPrdDimDO.getDim3Code());
			kpiList.add(dwpasCPrdDimDO.getDim4Code());
			kpiList.add(dwpasCPrdDimDO.getDim5Code());
			kpiList.add(dwpasCPrdDimDO.getDim6Code());
			// 根据CODE集合获取指标配置信息列表
			// Map<String, KpiInfo> kpiMap = kpiInfoComponent
			// .qryKpiInfosByKpiCodes(kpiList);
			// map.put("kpiMap", kpiMap);
		}

		map.put("dwpasCPrdDimDO", dwpasCPrdDimDO);
		// 查询产品信息列表
		map.put("prdList", prdInfoManager.qryPrdInfo());
		// 查询指标列表
		// map.put("kpiList", kpiInfoManager.queryKpiInfos());
		// 对url传入的中文转码
		// cPrdDimForm.setKeyWord(URLDecoder.decode(cPrdDimForm.getKeyWord(),
		// "UTF-8"));
		// 设操作标识为修改操作
		cPrdDimForm.setOperFlag("modify");
		map.put("cPrdDimForm", cPrdDimForm);
		map.put("dwpasCPrdInfoDO", dwpasCPrdInfoDO);
		return new ModelAndView("admin/prdinfo/prddiminfo", map);
	}

	/**
	 * 获得指标列表
	 * 
	 * @param modelMap
	 */
	@RequestMapping("/kpilist{sign}")
	public ModelAndView kpilist(@PathVariable String sign,
			HttpServletRequest request, HttpServletResponse response) {
		if(StringUtils.isBlank(sign)){
			this.logger.warn("获得指标列表时传递的sign为null");
		}
		List<KpiInfo> kpiinfoList = kpiInfoManager.queryKpiInfos1();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/prdinfo/kpilist1");
		mav.addObject("kpiinfoList", kpiinfoList);
		mav.addObject("sign", sign);
		return mav;
	}
}
