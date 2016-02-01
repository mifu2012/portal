package com.infosmart.controller.dwmis;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.controller.BaseController;
import com.infosmart.po.dwmis.DwmisKpiInfo;
import com.infosmart.po.dwmis.DwmisLegendCategory;
import com.infosmart.po.dwmis.DwmisLegendInfo;
import com.infosmart.po.dwmis.DwmisMisType;
import com.infosmart.service.dwmis.DwmisMisDptmntService;
import com.infosmart.service.dwmis.DwmisMisTypeService;
import com.infosmart.service.dwmis.LegendCategoryService;
import com.infosmart.service.dwmis.LegendInfoService;
import com.infosmart.service.dwmis.impl.KPIInfoService;
import com.infosmart.util.dwmis.CoreConstant;

@Controller
@RequestMapping("/LegendInfo")
public class DwmisLegendInfoController extends BaseController {

	@Autowired
	private LegendInfoService legendInfoService;
	@Autowired
	private LegendCategoryService legendCategoryService;
	@Autowired
	private DwmisMisDptmntService dwmisMisDptmntService;
	@Autowired
	private KPIInfoService kpiInfoService;
	@Autowired
	private DwmisMisTypeService dwmisMisTypeService;

	/**
	 * 图例管理列表
	 * 
	 * @param request
	 * @param dwmisLegendInfo
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request,
			DwmisLegendInfo dwmisLegendInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (dwmisLegendInfo.getLegendName() != null
				&& !"".equals(dwmisLegendInfo.getLegendName())) {
			dwmisLegendInfo.setLegendName(dwmisLegendInfo.getLegendName()
					.trim());
		}
		List<DwmisLegendInfo> legendInfoList = legendInfoService
				.list(dwmisLegendInfo);
		List<DwmisLegendCategory> legendCategoryList = legendCategoryService
				.list(new DwmisLegendCategory());
		map.put("dwmisLegendInfo", dwmisLegendInfo);
		map.put("legendInfoList", legendInfoList);
		map.put("legendCategoryList", legendCategoryList);
		return new ModelAndView("/dwmis/legendInfo/legendInfo", map);
	}

	/**
	 * 准备新增图列信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add")
	public ModelAndView add(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<DwmisLegendCategory> legendCategoryList = legendCategoryService
				.list(new DwmisLegendCategory());
		// 所有指标
		List<DwmisKpiInfo> allKpiInfos = dwmisMisDptmntService.listKpiInfos();
		// 统计方式
		List<DwmisMisType> misTypeList = dwmisMisTypeService
				.getAllDwmisMisTypes(CoreConstant.KPI_STAT);// groupId:2000
		map.put("legendCategoryList", legendCategoryList);
		map.put("allKpiInfos", allKpiInfos);
		map.put("misTypeList", misTypeList);
		return new ModelAndView("/dwmis/legendInfo/legendInfo_info", map);
	}

	/**
	 * 准备修改图例信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String legendId = request.getParameter("legendId");
		DwmisLegendInfo dwmisLegendInfo = legendInfoService.getById(legendId);
		List<DwmisLegendCategory> legendCategoryList = legendCategoryService
				.list(new DwmisLegendCategory());
		// 所有指标
		List<DwmisKpiInfo> allKpiInfos = dwmisMisDptmntService.listKpiInfos();
		// 统计方式
		List<DwmisMisType> misTypeList = dwmisMisTypeService
				.getAllDwmisMisTypes(CoreConstant.KPI_STAT);// groupId:2000
		// 已关联的指标
		List<DwmisKpiInfo> kpiInfoList = legendInfoService
				.getKpiInfoByLegendId(legendId);
		map.put("legendCategoryList", legendCategoryList);
		map.put("allKpiInfos", allKpiInfos);
		map.put("misTypeList", misTypeList);
		map.put("dwmisLegendInfo", dwmisLegendInfo);
		map.put("kpiInfoList", kpiInfoList);
		return new ModelAndView("/dwmis/legendInfo/legendInfo_info", map);
	}

	/**
	 * 查询所有指标
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/listAllCommKpiCode")
	public void listAllCommKpiCode(HttpServletRequest request,
			HttpServletResponse response, DwmisKpiInfo dwmisKpiInfo) {
		List<DwmisKpiInfo> codeList = kpiInfoService.getKPIInfo(dwmisKpiInfo);
		if (codeList == null)
			codeList = new ArrayList<DwmisKpiInfo>();
		this.sendJsonMsgToClient(codeList, response);
	}

	/**
	 * 保存图例信息
	 * 
	 * @param request
	 * @param dwmisLegendInfo
	 * @return
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request,
			DwmisLegendInfo dwmisLegendInfo) {
		this.logger.info("保存或更新图例信息");
		String[] codeList = request.getParameterValues("kpiCodes");
		String chartType = request.getParameter("chartTypeDesc");
		if (chartType != null || ("").equals(chartType)) {
			if (chartType.indexOf(",") > 0) {
				dwmisLegendInfo.setChartType(Integer.valueOf(chartType
						.substring(0, chartType.indexOf(","))));
				if (Integer.valueOf(chartType.substring(0,
						chartType.indexOf(","))) == 4) {
					dwmisLegendInfo
							.setChartTypeDesc(chartType.substring(
									chartType.indexOf(",") + 1,
									chartType.length() - 1));
				} else {
					dwmisLegendInfo.setChartTypeDesc(chartType.substring(
							chartType.indexOf(",") + 1, chartType.length()));
				}
			}
		}
		if (codeList != null && codeList.length > 0) {
			dwmisLegendInfo.setRelateKpiCodeList(Arrays.asList(codeList));
		}
		try {
			this.legendInfoService.saveDwmisLegendInfo(dwmisLegendInfo);
			request.setAttribute("msg", this.isSuccess);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			request.setAttribute("msg", this.isFailed);
		}
		return "common/save_result";
	}

	/**
	 * 已选择指标
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("showKpi")
	public ModelAndView showKpi(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/dwmis/legendInfo/choiceKpi");
		String selectedKpi = request.getParameter("selectedKpi");
		String id = request.getParameter("id");
		// 已选择的指标
		mv.addObject("selectedKpi", selectedKpi);
		mv.addObject("InfoId", id);
		return mv;
	}

	/**
	 * 删除图例信息
	 * 
	 * @param request
	 * @param out
	 * @param response
	 */
	@RequestMapping("del")
	public void del(HttpServletRequest request, PrintWriter out,
			HttpServletResponse response) {
		String legendId = request.getParameter("legendId");
		response.setContentType("text/html;charset=UTF-8");
		try {
			legendInfoService.delLegendKpiR(legendId);
			legendInfoService.del(legendId);
			out.write("success");
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			out.write("failed");
		}
		out.close();
	}
}
