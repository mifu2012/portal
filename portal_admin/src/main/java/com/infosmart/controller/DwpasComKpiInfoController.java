package com.infosmart.controller;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.po.ComKpiInfo;
import com.infosmart.po.DwpasCKpiInfo;
import com.infosmart.po.dwmis.DwmisKpiInfo;
import com.infosmart.service.ComKpiInfoService;

@Controller
@RequestMapping("/comkpiinfo1")
public class DwpasComKpiInfoController extends BaseController {
	private final String SUCCESS_ACTION = "/common/save_result";
	@Autowired
	private ComKpiInfoService comKpiInfoService;

	/**
	 * 通用指标管理默认页面
	 * 
	 * @param modelMap
	 */
	@RequestMapping
	public ModelAndView searchCommonKPI(HttpServletRequest req,
			HttpServletResponse res) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 通用指标列表查询
		List<ComKpiInfo> ckpiList = comKpiInfoService.searchCommonKPI();
		map.put("ckpiList", ckpiList);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/comkpiinfo/comkpiinfo1");
		mv.addAllObjects(map);
		mv.addObject("operFlag", "save");
		this.insertLog(req, "查询通用指标列表");
		return mv;
	}

	/**
	 * 
	 * 通用指标是否在龙虎榜显示批量处理
	 * 
	 */
	@RequestMapping("/getTigerComKpiCodes")
	public ModelAndView getTigerShow(HttpServletRequest req,
			HttpServletResponse res) {
		List<ComKpiInfo> tigerShowList = new ArrayList<ComKpiInfo>();
		List<ComKpiInfo> tigerNotShowList = new ArrayList<ComKpiInfo>();
		// 获得所有 已经在龙虎榜中显示的通用指标列表
		String moduleId = req.getParameter("moduleId");
		tigerShowList = comKpiInfoService.tigerIsShowComKpiInfo(moduleId);
		// 获得所有未在龙虎榜中显示的通用指标列表
		Map<String, ComKpiInfo> showCommKpiMap = new HashMap<String, ComKpiInfo>();
		if (tigerShowList != null) {
			for (ComKpiInfo comKpiInfo : tigerShowList) {
				showCommKpiMap.put(comKpiInfo.getComKpiCode(), comKpiInfo);
			}
		}
		List<ComKpiInfo> allCommKpiList = comKpiInfoService.searchCommonKPI();
		if (allCommKpiList != null) {
			for (ComKpiInfo comKpiInfo : allCommKpiList) {
				if (showCommKpiMap.containsKey(comKpiInfo.getComKpiCode())) {
					continue;
				}
				tigerNotShowList.add(comKpiInfo);
			}
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("tigerShowList", tigerShowList);
		mv.addObject("tigerNotShowList", tigerNotShowList);
		mv.addObject("moduleId", moduleId);
		mv.setViewName("admin/dwpas/tigerComkpiinfo");
		return mv;
	}

	// 搜索未在龙虎榜中显示的通用指标列表
	@RequestMapping("/searchTigerComKpiCodes")
	public void searchTigerNotShow(HttpServletRequest req,
			HttpServletResponse res) {
		List<ComKpiInfo> tigerNotShowList = new ArrayList<ComKpiInfo>();
		String comKpiCode = req.getParameter("comKpiCode");
		String linkedKpiCode = req.getParameter("linkedKpiCode");
		if (null != comKpiCode && "" != comKpiCode) {
			try {
				comKpiCode = new String(comKpiCode.getBytes("ISO-8859-1"),
						"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		tigerNotShowList = comKpiInfoService.searchTigerNotShow(comKpiCode,
				linkedKpiCode);
		// List对象转json
		JSONArray jSONArray = null;
		DwmisKpiInfo kpiInfo = null;
		jSONArray = JSONArray.fromObject(tigerNotShowList);
		this.sendMsgToClient(jSONArray.toString(), res);
	}

	/**
	 * 批量修改是否在龙虎榜中显示
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping("/updateShowTigerComKpiCodes")
	public void updateShowTigerComKpiCodes(HttpServletRequest req,
			HttpServletResponse res) {
		this.logger.info("批量修改是否在龙虎榜中显示");
		String showKpiCode = req.getParameter("showKpiCode");
		String moduleId = req.getParameter("moduleId");
		try {
			comKpiInfoService.updatePrdRanking(
					Arrays.asList(showKpiCode.split(",")), moduleId);
			this.sendMsgToClient("success", res);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.logger.error(ex.getMessage(), ex);
			this.sendMsgToClient("failed", res);
		}
	}

	// 根据comkpiCode查询
	@RequestMapping("/select")
	public ModelAndView getComKpiInfo(HttpServletRequest req,
			HttpServletResponse res) {
		String comKpiCode = req.getParameter("comKpiCode");
		ComKpiInfo comkpiinfo = new ComKpiInfo();
		comkpiinfo = comKpiInfoService.getComkpiInfo(comKpiCode);
		// 通用指标列表查询
		List<ComKpiInfo> ckpiList = comKpiInfoService.searchCommonKPI();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/comkpiinfo/comkpiinfo1");
		mv.addObject("comKpiCode", comKpiCode);
		mv.addObject("comkpiinfo", comkpiinfo);
		mv.addObject("ckpiList", ckpiList);
		mv.addObject("operFlag", "select");
		return mv;
	}

	/** 进入新增 */
	@RequestMapping(value = "/add")
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/comkpiinfo/comkpiinfo_add");
	}

	/** 进入更新 */
	@RequestMapping(value = "/edit{comKpiCode}")
	public ModelAndView edit(@PathVariable String comKpiCode,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ComKpiInfo comkpiinfo = comKpiInfoService.getComkpiInfo(comKpiCode);
		return new ModelAndView("admin/comkpiinfo/comkpiinfo_edit",
				"comkpiinfo", comkpiinfo);
	}

	/**
	 * 保存
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveExample(HttpServletRequest request,
			HttpServletResponse response, ComKpiInfo comkpiinfo)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(SUCCESS_ACTION);
		if (comkpiinfo == null) {
			this.logger.warn("通用指标信息对象为null");
			this.sendMsgToClient(isFailed, response);
			return mv;
		}
		if (comkpiinfo != null) {
			ComKpiInfo comkpi = comKpiInfoService.getComkpiInfo(comkpiinfo
					.getComKpiCode());
			if (comkpi == null) {
				comKpiInfoService.insertCommonKPIConfig(comkpiinfo);
				this.insertLog(request, "增加通用指标");
				mv.addObject("msg", "success");
			} else {
				String isUpdate = request.getParameter("isUpdate");
				if ("1".equals(isUpdate)) {
					comKpiInfoService.updateComKpiInfo(comkpiinfo);
					this.insertLog(request, "修改通用指标");
					mv.addObject("msg", "success");
				} else {
					mv.addObject("msg", "failed");
				}
			}
		}

		return mv;
	}

	/** 删除 */
	@RequestMapping(value = "/deleteKpiCode")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		String comKpiCode = request.getParameter("comKpiCode");
		try {
			comKpiInfoService.deleteCommonKPI(comKpiCode);
			this.insertLog(request, "删除通用指标");
			this.sendMsgToClient(isSuccess, response);
		} catch (Exception e) {

			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			this.sendMsgToClient(isFailed, response);
		}

	}

	/**
	 * 通用指标与指标关联信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/showKpi")
	public void showKpi(HttpServletRequest request, HttpServletResponse response) {
		String linkedKpiCode = "";
		String kpiCode = request.getParameter("kpiCode");
		// 隐藏值 baseKpiCode
		String baseKpiCode = request.getParameter("baseKpiCode");
		// kpicode搜索的关键字前台jsp放到了data里 不用转换了
		// if (null != kpiCode && "" != kpiCode) {
		// try {
		// kpiCode = new String(kpiCode.getBytes("ISO-8859-1"), "UTF-8");
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		// }
		List<DwpasCKpiInfo> kpiUnRelList = null;
		if (baseKpiCode == null || "".equals(baseKpiCode)) {
			// 点击左边链接查询未关联指标
			kpiUnRelList = comKpiInfoService.getNotRelateKpiBySetValue(kpiCode);

		} else {
			linkedKpiCode = request.getParameter("linkedKpiCode");
			// 搜索未关联指标
			try {
				kpiUnRelList = comKpiInfoService.getUnRelativeKPI(
						linkedKpiCode, kpiCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		JSONArray jSONArray = null;
		jSONArray = JSONArray.fromObject(kpiUnRelList);
		this.sendMsgToClient(jSONArray.toString(), response);
	}

	// 显示指标关联指标
	@RequestMapping(value = "/showRelateKpi")
	public void showRelateKpi(HttpServletRequest request,
			HttpServletResponse response) {
		String comKpiCode = request.getParameter("comKpiCode");
		List<DwpasCKpiInfo> kpiRelList = null;
		if (comKpiCode != null && comKpiCode != "") {
			try {
				kpiRelList = comKpiInfoService.getRelativeKPI(comKpiCode);
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
		String comKpiCode = req.getParameter("comKpiCode").trim();
		String kpiCode = req.getParameter("kpiCode").trim();
		// 首先删除kpiCode及联联指标则删除
		if (comKpiCode != null && !"".equals(comKpiCode)) {
			try {
				comKpiInfoService.deleteRelativeKPI(comKpiCode);
				if (kpiCode != null && !"".equals(kpiCode)) {
					comKpiInfoService.updateRelateKpiInfo(comKpiCode, kpiCode);
				}
				this.sendMsgToClient("success", res);
			} catch (Exception e) {
				this.sendMsgToClient("failed", res);
				e.printStackTrace();
			}
		}
	}

	// 查询通用指标
	@RequestMapping(value = "/showComKpi")
	public void showComKpi(HttpServletRequest request,
			HttpServletResponse response) {
		String comKpiCode = request.getParameter("comKpiCode");
		if (null != comKpiCode && "" != comKpiCode) {
			try {
				comKpiCode = comKpiCode.trim();
				comKpiCode = new String(comKpiCode.getBytes("ISO-8859-1"),
						"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		List<ComKpiInfo> kpiRelList = null;
		ComKpiInfo kpiInfo = new ComKpiInfo();
		try {
			kpiInfo.setComKpiCode(comKpiCode);
			kpiRelList = comKpiInfoService.selKpiCodeByCode(kpiInfo);
			JSONArray jSONArray = null;
			jSONArray = JSONArray.fromObject(kpiRelList);
			PrintWriter out;
			response.setContentType("text/html");
			out = response.getWriter();
			out.print(jSONArray.toString());
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//
	// // 更新当前通用指标与它关联的指标关系
	// @RequestMapping(value = "/updateKPIRelativeByKPICode")
	// public ModelAndView updateKPIRelativeByKPICode(HttpServletRequest req,
	// HttpServletResponse res) throws Exception {
	// String[] arrayList = req.getParameterValues("kpiRelInfo");
	// String comKpiCode = req.getParameter("comKpiCode");
	// Map<String,Object> DwpasCKpiInfoMap = new HashMap<String,Object>();
	// ModelAndView mav = new ModelAndView();
	// try {
	// if (arrayList != null) {
	// comKpiInfoService.deleteRelativeKPI(comKpiCode);
	// for (String temp : arrayList) {
	// DwpasCKpiInfo kInfo = new DwpasCKpiInfo();
	// kInfo.setKpiCode(temp);
	// kInfo.setComKpiCode(comKpiCode);
	// DwpasCKpiInfoMap.put("comAndkpiCode",kInfo);
	// comKpiInfoService.insertRelativeKPI(DwpasCKpiInfoMap);
	// }
	// } else {
	// comKpiInfoService.deleteRelativeKPI(comKpiCode);
	// }
	// } catch (Exception e) {
	// logger.error("comKpiCode=" + comKpiCode + "通用指标与指标关联建立失败", e);
	// mav.addObject("exception", e);
	// }
	// // return this.showRelKpi(req, res);
	// return null;
	// }
	/**
	 * 校验comKpiCode的唯一性
	 */
	@RequestMapping("/alidatecomKpiCode")
	public void alidatecomKpiCode(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		String comKpiCode = req.getParameter("comKpiCode");
		ComKpiInfo cominfo = comKpiInfoService.getComkpiInfo(comKpiCode);
		if (cominfo == null) {
			this.sendMsgToClient("0", res);
		} else {
			this.sendMsgToClient("1", res);
		}
	}
}
