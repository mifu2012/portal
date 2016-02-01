package com.infosmart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.po.DwpasCKpiInfo;
import com.infosmart.po.HelprateShow;
import com.infosmart.po.MisTypeInfo;
import com.infosmart.po.PrdMngInfo;
import com.infosmart.service.MisTypeInfoService;
import com.infosmart.service.PrdMngInfoService;

@Controller
@RequestMapping("/prdinfo1")
public class DwpasPrdMngInfoController extends BaseController {
	@Autowired
	private PrdMngInfoService prdMngInfoService;
	@Autowired
	private MisTypeInfoService misTypeInfoService;
	private final String SUCCESS_ACTION = "/common/save_result";

	/**
	 * 查询已关联或未关联的指标信息
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping("/queryRelOrNoRelKpiInfo")
	public void queryRelOrNoRelKpiInfo(HttpServletRequest req,
			HttpServletResponse res) {
		this.logger.info("查询某产品关联或未关联的指标信息");
		String productId = req.getParameter("productId");
		String kpiCode = req.getParameter("kpiCode");
		List<DwpasCKpiInfo> relAndNoRelKpiList = new ArrayList<DwpasCKpiInfo>();
		try {
			// 查询已关联的指标
			List<DwpasCKpiInfo> isRelKpiList = this.prdMngInfoService
					.getRelativeKPI(productId);
			if (isRelKpiList != null) {
				relAndNoRelKpiList.addAll(isRelKpiList);
			}
			// 查询未关联的指标
			List<DwpasCKpiInfo> noRelKpiList = this.prdMngInfoService
					.getUnRelativeKPI(productId, kpiCode);
			if (noRelKpiList != null) {
				relAndNoRelKpiList.addAll(noRelKpiList);
			}
			this.sendJsonMsgToClient(relAndNoRelKpiList, res);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 产品信息管理默认页面
	 * 
	 * @param modelMap
	 */
	@RequestMapping
	public ModelAndView qryPrdInfo(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询产品目录列表
		// List<PrdMngInfo> prdFolderList = prdMngInfoService.qryPrdFolder();
		// map.put("prdFolderList", prdFolderList);
		// 查询产品信息列表
		List<PrdMngInfo> prdList = prdMngInfoService.qryPrdInfo();
		map.put("prdList", prdList);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/prdinfo/prdinfo1");
		mv.addAllObjects(map);
		mv.addObject("operFlag", "save");
		this.insertLog(req, "查询产品信息列表");
		return mv;
	}

	/**
	 * 根据productId查询
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/select")
	public ModelAndView getPrdInfo(HttpServletRequest req,
			HttpServletResponse res) {
		String productId = req.getParameter("productId");
		PrdMngInfo prdinfo = new PrdMngInfo();
		prdinfo = prdMngInfoService.getPrdInfo(productId);
		// 查询产品目录列表
		List<PrdMngInfo> prdFolderList = prdMngInfoService.qryPrdFolder();
		// 查询产品信息列表
		List<PrdMngInfo> prdList = prdMngInfoService.qryPrdInfo();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/prdinfo/prdinfo1");
		mv.addObject("productId", productId);
		mv.addObject("prdinfo", prdinfo);
		mv.addObject("prdFolderList", prdFolderList);
		mv.addObject("prdList", prdList);
		mv.addObject("operFlag", "select");
		return mv;
	}

	/** 进入新增 */
	@RequestMapping(value = "/add")
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		List<MisTypeInfo> misTypeList = misTypeInfoService
				.getMisTypeInfoByGroupId(4000);
		mav.setViewName("admin/prdinfo/prdinfo_add");
		mav.addObject("misTypeList", misTypeList);
		return mav;
	}

	/** 进入更新 */
	@RequestMapping(value = "/edit{id}")
	public ModelAndView edit(@PathVariable String id,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (StringUtils.isBlank(id)) {
			this.logger.warn("更新产品信息时传递的id为null");
		}
		PrdMngInfo prdinfo = prdMngInfoService.getPrdInfo(id);
		List<MisTypeInfo> misTypeList = misTypeInfoService
				.getMisTypeInfoByGroupId(4000);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/prdinfo/prdinfo_edit");
		mav.addObject("misTypeList", misTypeList);
		mav.addObject("prdinfo", prdinfo);
		return mav;
	}

	/** 删除 */
	@RequestMapping(value = "/delete{productId}")
	public void delete(@PathVariable String productId, HttpServletRequest request, HttpServletResponse response)throws Exception {
		PrdMngInfo pInfo = prdMngInfoService.getPrdInfo(productId);
		PrintWriter out;
		response.setContentType("text/html");
		Boolean flag =false;
		try {
			flag=prdMngInfoService.deletePrdInfo(productId);			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(flag){
			this.insertLog(request, "删除产品信息_" + pInfo.getProductName());
			out = response.getWriter();
			out.print("success");
			out.flush();
			out.close();
		}

	}

	/** 产品信息新增保存 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView addPrdInfo(HttpServletRequest req,
			HttpServletResponse res, PrdMngInfo prdinfo) throws Exception {
		ModelAndView mv = new ModelAndView();
		String helpRate = req.getParameter("helpRate");
		String parentId = req.getParameter("parentId");
		if (parentId.equals("0")) {
			prdinfo.setParentId(null);
		}
		try {
			// 通过productId获得产品相关信息
			if (prdinfo != null) {
				PrdMngInfo dwpasCPrdInfoDO1 = prdMngInfoService
						.getPrdInfo(prdinfo.getProductId());
				if (dwpasCPrdInfoDO1 == null) {
					
						try {
							prdMngInfoService.insertPrdInfo(prdinfo);
							this.insertLog(req,
									"添加产品信息_" + prdinfo.getProductName());
							mv.addObject("msg", "success");
						} catch (Exception e) {

							mv.addObject("msg", "failed");
						}

					
				} else {
					try {
							prdMngInfoService.savePrdInfo(prdinfo);
							this.insertLog(req,
									"修改产品信息" + prdinfo.getProductName());
						mv.addObject("msg", "success");
					} catch (Exception e) {

						mv.addObject("msg", "failed");
					}
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("产品信息新增失败", e);
		}
		mv.setViewName(SUCCESS_ACTION);
		mv.addObject("prdinfo", prdinfo);
		return mv;
	}

	/**
	 * 产品与指标关联信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/showKpi")
	public void showKpi(HttpServletRequest request, HttpServletResponse response) {
		String productId = null;
		String baseProductId = request.getParameter("baseProductId");
		String linkedKpiCode = "";
		String kpiCode = request.getParameter("kpiCode");
		List<DwpasCKpiInfo> kpiUnRelList = null;
		if (null != kpiCode && "" != kpiCode) {
			try {
				kpiCode = new String(kpiCode.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if (baseProductId == null || "".equals(baseProductId)) {
			// 点击链接查询未关联指标
			productId = request.getParameter("productId");
			try {
				kpiUnRelList = prdMngInfoService.getUnRelativeKPI(productId,
						kpiCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// 搜索未关联指标
			linkedKpiCode = request.getParameter("linkedKpiCode");
			kpiUnRelList = prdMngInfoService.searchNotRelateKpiCodes(kpiCode,
					linkedKpiCode);
		}
		JSONArray jSONArray = null;
		jSONArray = JSONArray.fromObject(kpiUnRelList);
		this.sendMsgToClient(jSONArray.toString(), response);
	}

	// 显示产品关联指标
	@RequestMapping(value = "/showRelateKpi")
	public void showRelateKpi(HttpServletRequest request,
			HttpServletResponse response) {
		String productId = request.getParameter("productId");
		List<DwpasCKpiInfo> kpiRelList = null;
		if (productId != null && productId != "") {
			try {
				kpiRelList = prdMngInfoService.getRelativeKPI(productId);
				JSONArray jSONArray = null;
				jSONArray = JSONArray.fromObject(kpiRelList);
				this.sendMsgToClient(jSONArray.toString(), response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 保存关联指标
	@RequestMapping("/update")
	public void updateRelateKpi(HttpServletRequest req, HttpServletResponse res) {
		String productId = req.getParameter("productId").trim();
		String kpiCode = req.getParameter("kpiCode").trim();
		// 首先删除kpiCode及联联指标则删除
		if (productId != null && !"".equals(productId)) {
			try {
				prdMngInfoService.deleteRelativeKPI(productId);
				if (kpiCode != null && !"".equals(kpiCode)) {
					prdMngInfoService.updateRelateKpiInfo(productId, kpiCode);
				}
				this.sendMsgToClient("success", res);
			} catch (Exception e) {
				this.sendMsgToClient("failed", res);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 校验productId的唯一性
	 */
	@RequestMapping("/alidateProductId")
	public void alidateProductId(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String productId = req.getParameter("productId");
		PrdMngInfo prdinfo = prdMngInfoService.getPrdInfo(productId);
		if (prdinfo == null) {
			this.sendMsgToClient("0", res);
		} else {
			this.sendMsgToClient("1", res);
		}
	}

	/**
	 * 进入指标模板中产品信息标记维护页面
	 * 
	 * @param modelMap
	 */
	@RequestMapping("/markProductInfo")
	public ModelAndView markProductInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String[] productsIsmark = request.getParameterValues("prdIsMark");
		String[] productsUnmark = request.getParameterValues("prdUnMark");
		ModelAndView mv = new ModelAndView();
		try {
			if (productsIsmark != null && productsIsmark.length > 0) {
				prdMngInfoService.updateProInfoByProductIdList("1",
						Arrays.asList(productsIsmark));
			}
			if (productsUnmark != null && productsUnmark.length > 0) {
				prdMngInfoService.updateProInfoByProductIdList("2",
						Arrays.asList(productsUnmark));
			}
			mv.addObject("msg", this.isSuccess);
			this.insertLog(request, "编辑产品信息中的标记值");
		} catch (Exception e) {

			mv.addObject("msg", this.isFailed);
		}
		mv.setViewName(SUCCESS_ACTION);
		return mv;
	}

}
