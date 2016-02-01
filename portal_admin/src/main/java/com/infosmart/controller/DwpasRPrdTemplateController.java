package com.infosmart.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.po.DwpasCPrdInfo;
import com.infosmart.po.DwpasRPrdTemplate;
import com.infosmart.po.MisTypeInfo;
import com.infosmart.service.DwpasRPrdTemplateService;
import com.infosmart.service.MisTypeInfoService;

/**
 * 大事记管理控制类
 * 
 * 
 */

@Controller
public class DwpasRPrdTemplateController extends BaseController {
	@Autowired
	private MisTypeInfoService mistypeService;
	@Autowired
	private DwpasRPrdTemplateService rPrdTemplateService;
	
	private final String SUCCESS_ACTION = "/common/save_result";

	/**
	 * 列出所有产品以及模板关联的产品/渠道
	 * 
	 * @param templateId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/dwpas/listAllProducts{templateId}")
	public ModelAndView listAllProducts(@PathVariable Integer templateId,
			HttpServletRequest request, HttpServletResponse response) {
		String prodType = "";
		if (templateId == null) {
			this.logger.warn("列出所有产品以及模板关联的产品/渠道时传递的模板id为null");
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/dwpas/prodConnectTemp");
		List<MisTypeInfo> misTypelist = mistypeService.getMisTypeProdlist();
		List<DwpasCPrdInfo> connlist = rPrdTemplateService
				.getProdInfoByTemplateId(templateId);
		List<DwpasCPrdInfo> prodlist = new ArrayList<DwpasCPrdInfo>();
		if (connlist == null || connlist.size() == 0) {
			prodType = new String("4001");
			prodlist = rPrdTemplateService.getAllProduct(prodType);
		} else {
			prodType = connlist.get(0).getProductMark();
			prodlist = rPrdTemplateService.getAllProduct(prodType);
			for (DwpasCPrdInfo conn : connlist) {
				if (prodlist != null && prodlist.size() > 0) {
					for (DwpasCPrdInfo prod : prodlist) {
						if (conn.getProductId().equals(prod.getProductId())) {
							prodlist.remove(prod);
							break;
						}
					}
				}
			}
		}
		mv.addObject("prodlist", prodlist);
		mv.addObject("templateId", templateId);
		mv.addObject("connlist", connlist);
		mv.addObject("misTypelist", misTypelist);
		mv.addObject("prodType", prodType);
		return mv;
	}

	@RequestMapping("/dwpas/saveConnectProd")
	public ModelAndView saveConnectProd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		String[] connProduct = request.getParameterValues("proIds");
		Integer templateId = Integer.parseInt(request
				.getParameter("templateId"));
		rPrdTemplateService.deleteDwpasRPrdTemplate(templateId);
		if (connProduct != null) {
			for (String proId : connProduct) {
				DwpasRPrdTemplate rPrdTemplate = new DwpasRPrdTemplate();
				rPrdTemplate.setProductId(proId);
				rPrdTemplate.setTemplateId(templateId);
				rPrdTemplateService.insertDwpasRPrdTemplate(rPrdTemplate);
			}
		}
		/*
		 * //开始 // 产品交叉 渠道 用户行为专用 zy String prodType=""; List<DwpasCPrdInfo>
		 * connlist=null; if(templateId!=null && !"".equals(templateId)){
		 * connlist= rPrdTemplateService .getProdInfoByTemplateId(templateId); }
		 * //获得typeId判断typeName为产品交叉 渠道 用户行为 if(connlist== null ||
		 * connlist.size()==0){ prodType = new String("4001"); }else{ prodType =
		 * connlist.get(0).getProductMark(); } //根据prodType(typeId)查询type_name
		 * dwpas_c_sys_type表 MisTypeInfo
		 * misTypeInfo=mistypeService.getMisTypeInfo(prodType); DwpasCSystemMenu
		 * systemMenu=new DwpasCSystemMenu();
		 * systemMenu.setMenuId(templateId+"_0306");
		 * systemMenu.setMenuName(misTypeInfo.getTypeName());
		 * if(misTypeInfo.getTypeName().indexOf("产品")>=0){
		 * systemMenu.setMenuUrl("/CrossUser/doGet.html?kpiType=3"); }else
		 * if(misTypeInfo.getTypeName().indexOf("用户")>=0){
		 * systemMenu.setMenuUrl("/UserAction/doGet.html?1=1"); }else
		 * if(misTypeInfo.getTypeName().indexOf("渠道")>=0){
		 * systemMenu.setMenuUrl("/CrossUser/doGet.html?kpiType=3"); }
		 * menuService.updateMenuNameAndUrl(systemMenu); //结束
		 */mv.setViewName(SUCCESS_ACTION);
		mv.addObject("msg", "success");
		return mv;
	}

	/** 删除 已经关联的产品 */
	@RequestMapping(value = "/dwpas/deleteConnectProd{id}")
	public void delete(@PathVariable Integer id, PrintWriter out,
			HttpServletResponse response) {
		response.setContentType("text/plain");
		if (id == null) {
			this.logger.warn("删除关联产品时传递的id为null");
		}
		rPrdTemplateService.deleteDwpasRPrdTemplate(id);
		out.write("success");
		out.close();
	}

	@RequestMapping(value = "/dwpas/queryByProdId")
	public void queryByProdId(HttpServletRequest req, HttpServletResponse res) {
		this.logger.info("按产品类型查询产品信息");
		String prodType = req.getParameter("prodType");
		if (prodType == null || prodType == " ") {
			logger.error("产品类型参数prodType为空!");
		}
		List<DwpasCPrdInfo> prdInfolist = new ArrayList<DwpasCPrdInfo>();
		try {
			prdInfolist = rPrdTemplateService.getAllProduct(prodType);
			this.sendJsonMsgToClient(prdInfolist, res);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}

}
