package com.infosmart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.po.DwpasCSystemMenu;
import com.infosmart.po.KpiInfo;
import com.infosmart.po.PrdDimInfo;
import com.infosmart.po.PrdMngInfo;
import com.infosmart.service.DwpasCSystemMenuService;
import com.infosmart.service.KpiInfoService;
import com.infosmart.service.PrdDimInfoService;
import com.infosmart.service.PrdMngInfoService;
import com.infosmart.util.Const;

@Controller
@RequestMapping("/prddim1")
public class DwpasPrdDimInfoController extends BaseController {
	@Autowired
	private PrdDimInfoService prdDimManager; // 产品健康度信息service
	@Autowired
	private PrdMngInfoService prdInfoManager; // 产品信息管理service
	@Autowired
	private KpiInfoService kpiInfoManager; // 指标信息service
	@Autowired
	private DwpasCSystemMenuService dwpasCSystemMenuService;
	private final String SUCCESS_ACTION = "/common/save_result";

	/**
	 * 产品健康度信息管理默认页面
	 * 
	 * @param modelMap
	 */
	@RequestMapping
	public ModelAndView qryPrdInfoList(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PrdMngInfo> prdList = prdInfoManager.qryHelthPrdInfo();
		map.put("prdList", prdList);
		this.insertLog(req, "查询产品健康度列表");
		return new ModelAndView("admin/prddim/prddim", map);

	}

	// 根据productId查询
	@RequestMapping("/getPrddimInfo")
	public void getPrdInfo(HttpServletRequest req, HttpServletResponse res) {
		JSONObject jSON = null;
		// List<PrdMngInfo> prdList = prdInfoManager.qryPrdInfo();
		String productId = req.getParameter("productId");
		PrdDimInfo prddim = new PrdDimInfo();
		prddim = prdDimManager.qryPrdDimByProductId(productId);
		this.insertLog(req, "根据productId查询产品健康度");
		jSON = JSONObject.fromObject(prddim);
		PrintWriter out;
		try {
			res.setContentType("text/html");
			out = res.getWriter();
			out.print(jSON.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 保存
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/savePrddim1", method = RequestMethod.POST)
	public ModelAndView savePrddim1(HttpServletRequest request,
			HttpServletResponse response, PrdDimInfo prddim, String productId)
			throws IllegalAccessException, InvocationTargetException {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(SUCCESS_ACTION);
		if (prddim == null || productId == null) {
			this.logger.warn("保存时传递的PrdDimInfo或者PrdMngInfo为null");
			mv.addObject("msg", isFailed);
			return mv;
		}
		PrdDimInfo prdDimInfo= prdDimManager.qryPrdDimByProductId(productId);
		boolean success;
		try {
			if(prdDimInfo==null){
				success=prdDimManager.savePrdDim(prddim);
				this.insertLog(request, "新增产品健康度信息");
			}else{
				success = prdDimManager.updatePrddimInfo(prddim);
				this.insertLog(request, "更新产品健康度信息");
			}
			mv.addObject("msg", success?this.isSuccess:this.isFailed);
		} catch (Exception e) {

			e.printStackTrace();
			this.logger.error("更新或者新增失败："+e.getMessage(), e);
			mv.addObject("msg", isFailed);
		}
		
		
		
		// mv.addObject("prddim", prddim);
		// mv.addObject("prddim1", prddim1);
		// mv.addObject("prdList", prdList);
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
		return new ModelAndView("admin/prddim/prddim", map);
	}

	/**
	 * 获得指标列表
	 * 
	 * @param modelMap
	 */
	@RequestMapping("/kpilist")
	public ModelAndView kpilist(HttpServletRequest request,
			HttpServletResponse response) {
		String sign = request.getParameter("sign");
		String kpiName = request.getParameter("kpiName");
		String productId= request.getParameter("productId");
		if (StringUtils.isBlank(sign) || StringUtils.isBlank(kpiName)||StringUtils.isBlank(productId)) {
			this.logger.warn("获得指标列表时传递的值为null");
		}
		List<KpiInfo> kpiinfoList = kpiInfoManager.queryKpiInfoByProductId(productId);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/prddim/kpilist1");
		mav.addObject("kpiinfoList", kpiinfoList);
		mav.addObject("sign", sign);
		mav.addObject("kpiName", kpiName);
		return mav;
	}
	
	/**
	 * 进入模板页面中的产品六维度编辑页面
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/tempEditHealth")
	public ModelAndView tempEditHealth(HttpServletRequest req,
			HttpServletResponse res)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String templateId=req.getParameter("templateId");
		String templateName=req.getParameter("templateName");
		templateName = new String(templateName.getBytes("ISO-8859-1"), "UTF-8");	
		DwpasCSystemMenu menuInfo = new DwpasCSystemMenu();
		menuInfo.setTemplateId(Integer.parseInt(templateId));
		menuInfo.setMenuPid(templateId+"_03");
		List<PrdMngInfo> prdList = prdInfoManager.qryHelthPrdInfoByTemplateId(templateId);
		List<DwpasCSystemMenu> menuInfoList = dwpasCSystemMenuService.listChildrenSystemMemus(menuInfo);
		//将维度 信息放入session
		map.put("prdList", prdList);
		map.put("menuInfoList", menuInfoList);
		map.put("templateName", templateName);
		map.put("menuName",new String(req.getParameter("menuName").getBytes("ISO-8859-1"),"UTF-8"));
		map.put("menuId", req.getParameter("menuId"));
		map.put("templateId", templateId);
		this.insertLog(req, "指标模板中查询产品健康度列表");
		return new ModelAndView("admin/editPrdHealth/prdHealth", map);

	}
}
