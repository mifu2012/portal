package com.infosmart.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.po.ComKpiInfo;
import com.infosmart.po.DwpasCColumnInfo;
import com.infosmart.po.DwpasCComKpiInfo;
import com.infosmart.po.DwpasCPrdInfo;
import com.infosmart.po.DwpasCSystemMenu;
import com.infosmart.po.DwpasCmoduleInfo;
import com.infosmart.po.KpiInfo;
import com.infosmart.po.report.ReportDesign;
import com.infosmart.service.DwpasCColumnInfoService;
import com.infosmart.service.DwpasCModuleInfoService;
import com.infosmart.service.DwpasCSystemMenuService;
import com.infosmart.service.KpiInfoService;
import com.infosmart.service.PrdMngInfoService;
import com.infosmart.service.report.ReportService;
import com.infosmart.util.Const;
import com.infosmart.util.StringUtils;

@Controller
public class DefinedTemplateController extends BaseController {

	@Autowired
	private DwpasCSystemMenuService systemMenuService;

	@Autowired
	private DwpasCModuleInfoService moduleInfoService;

	@Autowired
	private DwpasCColumnInfoService columnInfoService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private KpiInfoService kpiInfoService;
	@Autowired
	private PrdMngInfoService prdMngInfoService;

	/**
	 * http://localhost:8080/designTemplate/designIndexMenu.html?menuId=
	 * FVGEc1uqF7wk3imRiRtNBBfpjjFrav2F&dateType=D
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/designTemplate/designIndexMenu")
	public ModelAndView designIndexMenu(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String menuId = request.getParameter("menuId");
		this.logger.info("列出某菜单的所有模块信息:" + menuId);
		String dateType = request.getParameter("dateType");
		DwpasCSystemMenu systemMenu = this.systemMenuService
				.getDwpasCSystemMenuById(menuId, dateType);
		if (systemMenu != null) {
			systemMenu.setDateType(dateType);
			if (systemMenu.getModuleInfoList() != null
					&& systemMenu.getModuleInfoList().size() > 0) {
				for (Iterator<DwpasCmoduleInfo> it = systemMenu
						.getModuleInfoList().listIterator(); it.hasNext();) {
					DwpasCmoduleInfo moduleInfo = it.next();
					if (moduleInfo.getModuleId() == null) {
						it.remove();
					}
				}
			}
		}
		Map map = new HashMap();
		map.put("systemMenu", systemMenu);
		map.put("templateName", new String(request.getParameter("templateName")
				.getBytes("ISO-8859-1"), "UTF-8"));
		map.put("menuName", new String(request.getParameter("menuName")
				.getBytes("ISO-8859-1"), "UTF-8"));
		return new ModelAndView("/admin/dwpas/editIndexTemplate", map);
	}

	/**
	 * 保存首页配置信息
	 * 
	 * @param request
	 * @param response
	 * @param systemMenu
	 * @return
	 */
	@RequestMapping(value = "/designTemplate/saveIndexPageConfig")
	public ModelAndView saveIndexPageConfig(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("indexMenuForm") DwpasCSystemMenu systemMenu) {
		this.logger.info("保存首页模板设置信息");
		ModelAndView mv = new ModelAndView(this.SUCCESS_ACTION);
		try {
			this.systemMenuService.saveDwpasCSystemMenu(systemMenu);
			mv.addObject("msg", this.isSuccess);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			mv.addObject("msg", this.isFailed);
		}
		return mv;
	}

	/**
	 * 列出某模块关联的栏目
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/designTemplate/listColumnInfo")
	public ModelAndView listColumnInfoByModuleId(HttpServletRequest request,
			HttpServletResponse response) {
		String moduleId = request.getParameter("moduleId");
		String dateType = request.getParameter("dateType");
		this.logger.info("列出某模块关联的栏目:" + moduleId);
		List<DwpasCColumnInfo> columnInfoList = null;
		DwpasCmoduleInfo moduleInfo = null;
		if (StringUtils.notNullAndSpace(moduleId)) {
			moduleInfo = this.moduleInfoService
					.getDwpasCmoduleInfoById(moduleId);
			// 查询模块信息
			columnInfoList = this.columnInfoService
					.listCColumnInfoAndRelCommKpiInfoByModuleId(moduleId);
			// 查询产品
		} else {
			this.logger.info("新增自定义模块");
			moduleInfo = new DwpasCmoduleInfo();
			moduleInfo.setModuleName("自定义模块");
			// 默认显示
			moduleInfo.setIsShow(1);
			// 默认高度
			moduleInfo.setHeight(300);
			moduleInfo.setMenuId(request.getParameter("menuId"));
			moduleInfo.setModuleCode(UUID.randomUUID().toString());
			// 默认是最后一个
			moduleInfo.setModuleSort(99);
			// 默认是自定义模块
			moduleInfo.setModuleType(DwpasCmoduleInfo.MODULE_TYPE_SELF);
			// 默认是底部的宽度
			moduleInfo.setWidth(900);
			// 默认是底部
			moduleInfo.setPosition(DwpasCmoduleInfo.POSITION_BOTTOM);
			// 默认是最后一个
			moduleInfo.setPositionX(227);
			moduleInfo.setPositionY(999);
			moduleInfo.setDateType(request.getParameter("dateType"));
		}
		if (columnInfoList == null) {
			columnInfoList = new ArrayList<DwpasCColumnInfo>();
		}
		List<ReportDesign> reportlist = reportService.listAllReports();
		List<ReportDesign> childlist = new ArrayList<ReportDesign>();
		for (int i = 0; i < reportlist.size(); i++) {
			ReportDesign r = reportlist.get(i);
			// 如果是报表
			//this.logger.info("----->是否定制:" + r.getRptFlag500w());
			if (r.getIsReport().equals("1")
					&& "0".equalsIgnoreCase(r.getRptFlag500w() + "")) {
				childlist.add(r);
			}
		}
		ModelAndView mv = new ModelAndView("/admin/dwpas/editIndexColumn");
		mv.addObject("columnInfoList", columnInfoList);
		mv.addObject("moduleInfo", moduleInfo);
		mv.addObject("childlist", childlist);
		mv.addObject("moduleCode", moduleInfo.getModuleCode());
		mv.addObject("reportlist", reportlist);
		mv.addObject("dateType", dateType);
		return mv;
	}

	/**
	 * 保存首页栏目配置信息
	 * 
	 * @param request
	 * @param response
	 * @param systemMenu
	 * @return
	 */
	@RequestMapping(value = "/designTemplate/saveColumnConfig")
	public ModelAndView saveColumnConfig(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("indexMenuForm") DwpasCmoduleInfo moduleInfo) {
		this.logger.info("保存首页模块关联栏目设置信息" + request.getParameter("moduleId"));
		ModelAndView mv = new ModelAndView(this.SUCCESS_ACTION);
		try {
			columnInfoService.saveDwpasCColumnInfoByBatch(moduleInfo);
			moduleInfoService.updateDwpasCModuleInfo(moduleInfo);
			mv.addObject("msg", this.isSuccess);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			mv.addObject("msg", this.isFailed);
		}
		return mv;
	}

	/**
	 * 根据ID删除模块信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/designTemplate/deleteModuleById")
	public void deleteModuleInfoById(HttpServletRequest request,
			HttpServletResponse response) {
		String moduleId = request.getParameter("moduleId");
		this.logger.info("删除模块：" + moduleId);
		boolean isSuccess = false;
		try {
			if (StringUtils.notNullAndSpace(moduleId)) {
				this.moduleInfoService.deleteDwpasCmoduleInfoById(moduleId);
				isSuccess = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
		this.sendMsgToClient(isSuccess ? "1" : "0", response);
	}

	/**
	 * 转到选择指标或通用指标
	 * 
	 * @param request
	 * @param response
	 * @param kpiType
	 *            指标类型
	 * @param selectedKpi
	 *            已关联的指标
	 * @param relKpiKind
	 *            关联指标类型：大盘指标/通用指标
	 * @return
	 */
	@RequestMapping(value = "/designTemplate/showChoiceKpi")
	public ModelAndView showChoiceKpi(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "kpiType", required = true) String kpiType,
			@RequestParam(value = "selectedKpi", required = false) String selectedKpi,
			@RequestParam(value = "graphValues", required = true) String graphValues,
			@RequestParam(value = "relKpiKind", required = true) String relKpiKind) {
		String moduleCode = request.getParameter("moduleCode");
		// 获取通用指标信息
		try {
			Integer.parseInt(kpiType);
		} catch (Exception e) {
			kpiType = "1";
		}
		List<ComKpiInfo> selectComKpiInfoList = new ArrayList<ComKpiInfo>();

		if (selectedKpi != null && selectedKpi.length() > 0) {
			String comKpiAndPrdArray[] = selectedKpi.split(",");
			for (String commKpiAndPrd : comKpiAndPrdArray) {
				if (!StringUtils.notNullAndSpace(commKpiAndPrd))
					continue;
				String comKpiInfos[] = commKpiAndPrd.split("\\^");
				ComKpiInfo comKpiInfo = new ComKpiInfo();
				if (comKpiInfos.length > 0) {
					comKpiInfo.setComKpiCode(comKpiInfos[0]);
				} else {
					comKpiInfo.setComKpiCode("无指标");
				}
				if (comKpiInfos.length > 1) {
					comKpiInfo.setProductId(comKpiInfos[1]);
				} else {
					comKpiInfo.setProductId("无产品");
				}

				selectComKpiInfoList.add(comKpiInfo);
			}
		} else {
			selectComKpiInfoList = null;
		}
		DwpasCComKpiInfo dwpasCComKpiInfo = new DwpasCComKpiInfo();
		dwpasCComKpiInfo.setKpiType(kpiType);
		// 查询所有的指标
		List<DwpasCComKpiInfo> commonKpiCodeList = columnInfoService
				.getAllDwpasCComKpiInfo(dwpasCComKpiInfo);

		// 从session获取templateId
		String templateId = request.getSession()
				.getAttribute(Const.SESSION_TEMPLATE_ID).toString();

		List<DwpasCPrdInfo> productInfoList = prdMngInfoService
				.getAllProInfosByTemplateId(templateId);
		// ModelAndView mv = new ModelAndView("/admin/dwpas/choiceKpi");
		ModelAndView mv = new ModelAndView("/admin/dwpas/choiceKpi_new");
		// 指标类型
		mv.addObject("kpiType", kpiType);
		// 已选择的指标
		mv.addObject("selectedKpi", selectedKpi);
		mv.addObject("selectComKpiInfoList", selectComKpiInfoList);
		// 关联指标类型
		mv.addObject("relKpiKind", relKpiKind);
		mv.addObject("productInfoList", productInfoList);
		mv.addObject("commonKpiCodeList", commonKpiCodeList);
		mv.addObject("graphValues", graphValues);
		mv.addObject("moduleCode", moduleCode);
		return mv;
	}

	/**
	 * 列出所有的通用指标
	 * 
	 * @param request
	 * @param response
	 * @param kpiType
	 */
	@RequestMapping(value = "/designTemplate/listAllCommKpiCode")
	public void listAllCommKpiCode(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "kpiType", required = true) String kpiType) {
		try {
			Integer.parseInt(kpiType);
		} catch (Exception e) {
			kpiType = "1";
		}
		DwpasCComKpiInfo dwpasCComKpiInfo = new DwpasCComKpiInfo();
		dwpasCComKpiInfo.setKpiType(kpiType);
		// 查询所有的指标
		List<DwpasCComKpiInfo> commonkpiCodeList = columnInfoService
				.getAllDwpasCComKpiInfo(dwpasCComKpiInfo);
		if (commonkpiCodeList == null)
			commonkpiCodeList = new ArrayList<DwpasCComKpiInfo>();
		this.sendJsonMsgToClient(commonkpiCodeList, response);
	}

	/**
	 * 列出所有的大盘指标
	 * 
	 * @param request
	 * @param response
	 * @param kpiType
	 */
	@RequestMapping(value = "/designTemplate/listAllOverallKpiCode")
	public void listAllOverallKpiCode(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "kpiType", required = true) String kpiType) {
		try {
			Integer.parseInt(kpiType);
		} catch (Exception e) {
			kpiType = "1";
		}
		List<KpiInfo> kpiInfoList = this.kpiInfoService
				.listAllOverallKpiInfo(Integer.parseInt(kpiType));
		if (kpiInfoList == null)
			kpiInfoList = new ArrayList<KpiInfo>();
		this.sendJsonMsgToClient(kpiInfoList, response);
	}
}
