package com.infosmart.controller.dwmis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.controller.BaseController;
import com.infosmart.po.dwmis.DwmisKpiInfo;
import com.infosmart.service.dwmis.KPIInfoService;

@Controller
@RequestMapping("/kpiRelateManage")
public class KpiRelateManageController extends BaseController {
	@Autowired
	private KPIInfoService kPIInfoService;

	@RequestMapping("/getAllRelateKpi")
	public ModelAndView getAllKpi(HttpServletRequest req,
			HttpServletResponse res) {
		Map map = new HashMap();
		// 查询所有指标数据
		List<DwmisKpiInfo> dwmisKpiInfoList = new ArrayList<DwmisKpiInfo>();
		List<DwmisKpiInfo> dwmisRelateKpiInfoList = new ArrayList<DwmisKpiInfo>();
		dwmisKpiInfoList = kPIInfoService.getKPIInfo(new DwmisKpiInfo());
		dwmisRelateKpiInfoList = dwmisKpiInfoList;
		map.put("dwmisKpiInfoList", dwmisKpiInfoList);
		map.put("dwmisRelateKpiInfoList", dwmisRelateKpiInfoList);
		return new ModelAndView("/dwmis/kpiManage/kpiRelateManage", map);
	}

	/**
	 * 修改指标关联指标
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping("/update")
	public void updateRelateKpi(HttpServletRequest req, HttpServletResponse res) {
		String kpiCode = req.getParameter("kpiCode");
		String linkedKpiCode = req.getParameter("linkedKpiCode");
		// 首先删除kpiCode及联联指标则删除
		if (kpiCode != null && !"".equals(kpiCode)) {
			try {
				kPIInfoService.deleteRelateKpiInfo(kpiCode);
				if (linkedKpiCode != null && !"".equals(linkedKpiCode)) {
					kPIInfoService.updateRelateKpiInfo(kpiCode, linkedKpiCode);
				}
				this.sendMsgToClient("success", res);
			} catch (Exception ex) {
				this.sendMsgToClient("failed", res);
			}
		}
	}

	/**
	 * 根据指标搜索数据
	 * 
	 * @param req
	 * @param res
	 * @param resp
	 * @throws Exception
	 */
	// 搜索指标列表
	@RequestMapping("/searchKpi")
	public void searchKpi(HttpServletRequest req, HttpServletResponse res,
			HttpServletResponse resp) throws Exception {
		String kpiCode = "";
		kpiCode = req.getParameter("kpiCode");
		if (null != kpiCode && "" != kpiCode) {
			kpiCode = kpiCode.trim();
			kpiCode = new String(kpiCode.getBytes("ISO-8859-1"), "UTF-8");
		}
		List<DwmisKpiInfo> dwmisKpiInfoList = new ArrayList<DwmisKpiInfo>();
		DwmisKpiInfo dwmisKpiInfo = new DwmisKpiInfo();
		dwmisKpiInfo.setKpiCode(kpiCode);
		dwmisKpiInfo.setKpiName(kpiCode);
		dwmisKpiInfoList = kPIInfoService.getKPIInfo(dwmisKpiInfo);
		JSONArray jSONArray = null;
		jSONArray = JSONArray.fromObject(dwmisKpiInfoList);
		this.sendMsgToClient(jSONArray.toString(), res);

	}

	// 获得已关联数据
	@RequestMapping("/searchRelate")
	public void searchRelateKpiCodes(HttpServletRequest req,
			HttpServletResponse res, HttpServletResponse resp) throws Exception {
		String kpiCode = "";
		kpiCode = req.getParameter("kpiCode");
		List<DwmisKpiInfo> dwmisKpiInfoList = new ArrayList<DwmisKpiInfo>();
		dwmisKpiInfoList = kPIInfoService.getRelateKpiInfo(kpiCode);
		// List对象转json
		JSONArray jSONArray = null;
		jSONArray = JSONArray.fromObject(dwmisKpiInfoList);
		this.sendMsgToClient(jSONArray.toString(), res);
	}

	// 获得未关联数据
	@RequestMapping("/searchNotRelate")
	public void searchNotRelate(HttpServletRequest req,
			HttpServletResponse res, HttpServletResponse resp) throws Exception {
		List<DwmisKpiInfo> dwmisKpiInfoList = new ArrayList<DwmisKpiInfo>();
		// 判断是点击链接查询还是搜索
		// 隐藏指标
		String baseKpiCode = "";
		// 搜索指标
		String kpiCode = "";
		String linkedKpiCode = "";
		kpiCode = req.getParameter("kpiCode");
		if (null != kpiCode && "" != kpiCode) {
			kpiCode = kpiCode.trim();
			kpiCode = new String(kpiCode.getBytes("ISO-8859-1"), "UTF-8");
		}
		baseKpiCode = req.getParameter("baseKpiCode");
		// 如果隐藏指标为空则为点击链接查询数据
		if (baseKpiCode == null || "".equals(baseKpiCode)) {
			dwmisKpiInfoList = kPIInfoService
					.searchNotRelateBySetValue(kpiCode);
		} else {
			// 不为空则为搜索未关联指标
			// 已关联指标
			linkedKpiCode = req.getParameter("linkedKpiCode");
			dwmisKpiInfoList = kPIInfoService.searchNotRelateKpiCodes(kpiCode,
					linkedKpiCode);
		}
		JSONArray jSONArray = null;
		jSONArray = JSONArray.fromObject(dwmisKpiInfoList);
		this.sendMsgToClient(jSONArray.toString(), res);
	}
}
