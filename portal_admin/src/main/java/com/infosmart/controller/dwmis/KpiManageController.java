package com.infosmart.controller.dwmis;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.controller.BaseController;
import com.infosmart.po.dwmis.DwmisKpiInfo;
import com.infosmart.po.dwmis.DwmisSystemParamType;
import com.infosmart.service.dwmis.DwmisSystemParamTypeService;
import com.infosmart.service.dwmis.KPIInfoService;
import com.infosmart.util.dwmis.CoreConstant;

@Controller
@RequestMapping("/kpiManage")
public class KpiManageController extends BaseController {
	@Autowired
	private DwmisSystemParamTypeService systemParamTypeService;
	@Autowired
	private KPIInfoService kPIInfoService;

	@RequestMapping("/getAllKpi")
	public ModelAndView getAllKpi(HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap();
		// 查询所有的绩效类别、
		List<DwmisSystemParamType> goal = this.systemParamTypeService
				.listSystemParamByGroupId(CoreConstant.KPI_GOAL_TYPE);
		// 指标类型、
		List<DwmisSystemParamType> type = this.systemParamTypeService
				.listSystemParamByGroupId(CoreConstant.KPI_TYPE);
		// 指标单位、
		List<DwmisSystemParamType> unit = this.systemParamTypeService
				.listSystemParamByGroupId(CoreConstant.KPI_UNIT);
		// 指标数量级、
		List<DwmisSystemParamType> size = this.systemParamTypeService
				.listSystemParamByGroupId(CoreConstant.KPI_SIZE);
		// 一级指标类别、
		List<DwmisSystemParamType> level = this.systemParamTypeService
				.listSystemParamByGroupId(CoreConstant.KPI_LEVEL_TYPE_ONE);
		// 二级指标类别
		List<DwmisSystemParamType> level2 = this.systemParamTypeService
				.listSystemParamByGroupId(CoreConstant.KPI_LEVEL_TYPE_TWO);
		// 查询所有指标数据
		List<DwmisKpiInfo> dwmisKpiInfoList = new ArrayList<DwmisKpiInfo>();
		// 搜索时传进kpiCode
		String Ktype = req.getParameter("Ktype");
		String kpiCode = "";
		if (Ktype != null && !"".equals(Ktype)) {
			kpiCode = req.getParameter("kpiCode");
		}
		DwmisKpiInfo dwmisKpiInfo = new DwmisKpiInfo();
		dwmisKpiInfo.setKpiCode(kpiCode);
		dwmisKpiInfoList = kPIInfoService.getKPIInfo(dwmisKpiInfo);

		map.put("goal", goal);
		map.put("type", type);
		map.put("unit", unit);
		map.put("size", size);
		map.put("level", level);
		map.put("level2", level2);
		map.put("dwmisKpiInfoList", dwmisKpiInfoList);
		return new ModelAndView("/dwmis/kpiManage/kpiManage", map);
	}

	/**
	 * 根据kpiCode查询
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping("/select")
	public void getKpiInfoBykpiCode(HttpServletRequest req,
			HttpServletResponse res) {
		JSONObject jSON = null;
		String kpiCode = req.getParameter("kpiCode");
		DwmisKpiInfo dwmisKpiInfo = new DwmisKpiInfo();
		dwmisKpiInfo = kPIInfoService.selectKpiInfo(kpiCode);
		jSON = JSONObject.fromObject(dwmisKpiInfo);
		this.sendMsgToClient(jSON.toString(), res);
	}

	/**
	 * 验证数据库是否有此KpiCode的记录
	 * 
	 * @param request
	 * @param response
	 * @param dwmisKpiInfo
	 */
	@RequestMapping("/checkKpiCode")
	public void checkKpiCode(HttpServletRequest request,
			HttpServletResponse response) {
		DwmisKpiInfo dwmisKpiInfo = null;
		DwmisKpiInfo dwmisKpiInfoByKpiName = null;
		String kpiCode = request.getParameter("kpiCode");
		// 判断是修改校验还是添加校验
		String isAdd = request.getParameter("isAdd");
		String kpiName = request.getParameter("kpiName");
		// 判断输入的kpiName和原始的kpiName是否相等，要相等就不需要从数据库里面查找
		String myKpiName = request.getParameter("myKpiName");
		if ("1".equals(isAdd)) {
			if (kpiCode != null && !"".equals(kpiCode)) {
				dwmisKpiInfo = kPIInfoService.selectKpiInfo(kpiCode);
			}
		}
		if (!myKpiName.equals(kpiName)) {
			if (kpiName != null && !"".equals(kpiName)) {
				dwmisKpiInfoByKpiName = kPIInfoService
						.seleKpiInfoByName(kpiName);
			}
		}
		PrintWriter out = null;
		response.setContentType("text/html");
		try {
			out = response.getWriter();
			if (dwmisKpiInfo == null && dwmisKpiInfoByKpiName == null) {
				out.print("success");
			} else if (dwmisKpiInfo != null) {
				out.print("faild1");
			} else if (dwmisKpiInfoByKpiName != null) {
				out.print("faild2");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}

	/**
	 * 添加数据
	 * 
	 * @param req
	 * @param res
	 * @param dwmisKpiInfo
	 * @return
	 */
	@RequestMapping("/add")
	public void addKpiInfo(HttpServletRequest req, HttpServletResponse res,
			DwmisKpiInfo dwmisKpiInfo) {
		if (dwmisKpiInfo != null) {
			if (dwmisKpiInfo.getPeriod() != null
					&& dwmisKpiInfo.getPeriod().length() > 0) {
				String[] str = dwmisKpiInfo.getPeriod().split(",");
				String period = "";
				for (int i = 0; i < str.length; i++) {
					period = period + str[i];
				}
				dwmisKpiInfo.setPeriod(period);
				try {
					kPIInfoService.addKpiInfo(dwmisKpiInfo);
					this.sendMsgToClient("success", res);
				} catch (Exception ex) {
					this.sendMsgToClient("failed", res);
				}
			}
		}

	}

	/**
	 * 修改数据
	 * 
	 * @param req
	 * @param res
	 * @param dwmisKpiInfo
	 * @return
	 */
	@RequestMapping("/update")
	public void updateKpiInfo(HttpServletRequest req, HttpServletResponse res,
			DwmisKpiInfo dwmisKpiInfo) {
		if (dwmisKpiInfo != null) {
			if (dwmisKpiInfo.getPeriod() != null
					&& dwmisKpiInfo.getPeriod().length() > 0) {
				String[] str = dwmisKpiInfo.getPeriod().split(",");
				String period = "";
				for (int i = 0; i < str.length; i++) {
					period = period + str[i];
				}
				dwmisKpiInfo.setPeriod(period);
				try {
					kPIInfoService.updateKpiInfo(dwmisKpiInfo);
					this.sendMsgToClient("success", res);
				} catch (Exception ex) {
					this.sendMsgToClient("failed", res);
				}
			}
		}
	}

	/**
	 * 删除数据
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/del")
	public void deleteKpiInfo(HttpServletRequest req,
			HttpServletResponse res) {
		String kpiCode = req.getParameter("kpiCode");
		try{
			kPIInfoService.deleteKpiInfo(kpiCode);
			this.sendMsgToClient("success", res);
		}catch(Exception ex){
			this.sendMsgToClient("failed", res);
		}
	}
}
