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

import com.infosmart.po.MisTypeInfo;
import com.infosmart.po.PrdMngInfo;
import com.infosmart.service.MisTypeInfoService;
import com.infosmart.service.PrdMngInfoService;

@Controller
@RequestMapping("/proMarked")
public class DwpasProMarkedController extends BaseController {
	@Autowired
	private MisTypeInfoService misTypeInfoService;
	@Autowired
	private PrdMngInfoService prdMngInfoService;

	/**
	 * 获取产品类型
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/getProductInfo")
	public ModelAndView getProductInfo(HttpServletRequest req,
			HttpServletResponse res) {
		ModelAndView mv = new ModelAndView();
		// 根据模板Id查询产品信息列表
		List<MisTypeInfo> misTypelist = misTypeInfoService.getMisTypeProdlist();
		mv.addObject("misTypelist", misTypelist);
		mv.setViewName("/admin/editProMarkType/proMarkType");
		this.insertLog(req, "指标模板中查看产品关联维护页面");
		return mv;
	}

	/**
	 * 获取已关联产品
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping("/getMarkedPro")
	public void getMarkedPro(HttpServletRequest req, HttpServletResponse res) {
		String typeId = req.getParameter("typeId").trim();
		List<PrdMngInfo> markedProList = prdMngInfoService.getMarkedPro(typeId);
		this.sendJsonMsgToClient(markedProList, res);
	}

	/**
	 * 获取未关联产品
	 * 
	 * @param req
	 * @param res
	 * @throws Exception
	 */

	@RequestMapping("/getUnMarkedPro")
	public void getUnMarkedPro(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String typeId = req.getParameter("typeId").trim();
		String markedPro = req.getParameter("markedPro");
		String keyCode = req.getParameter("keyCode");
		//传入中文转换格式
		if (keyCode != null) {
			keyCode = keyCode.trim();
			keyCode = new String(keyCode.getBytes("ISO-8859-1"), "UTF-8");
		}

		//所有为关联产品
		List<PrdMngInfo> allUnMarkedProList = prdMngInfoService.getUnMarkedPro(
				typeId, keyCode);
		List<String> allUnMarkedProids = new ArrayList<String>();
		if (allUnMarkedProList != null && allUnMarkedProList.size() > 0) {
			for (PrdMngInfo allMarkedPro : allUnMarkedProList) {
				allUnMarkedProids.add(allMarkedPro.getProductId());
			}
			if (markedPro != null) {
				//以选择为关联产品ids
				String[] markedProList = markedPro.split(",");
				//从所用未关联产品中删除已关联产品id
				for (int i = 0; i < markedProList.length; i++) {
					if (allUnMarkedProids.contains(markedProList[i])) {
						allUnMarkedProids.remove(markedProList[i]);
					}
				}
				//查询未关联产品
				List<PrdMngInfo> unMarkedProList = prdMngInfoService
						.getPrdMngInfoByProids(allUnMarkedProids);
				this.sendJsonMsgToClient(unMarkedProList, res);
			} else {
				this.sendJsonMsgToClient(allUnMarkedProList, res);
			}
		}
	}

	/**
	 * 保存关联产品
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping("/saveMarkedPro")
	public void saveMaredPro(HttpServletRequest req, HttpServletResponse res) {
		String typeId = req.getParameter("typeId");
		
		String markedPro = req.getParameter("markedPro");
		String unMarkePro = req.getParameter("unMarkePro");
		//已关联产品ids
		String[] markedProList = markedPro.split(",");
		//为关联产品ids
		String[] unMarkedProList = unMarkePro.split(",");
		if (typeId != null) {
			try {
				if (markedProList != null) {
					prdMngInfoService.updateMarkedPro(typeId,
							Arrays.asList(markedProList));
				}
				if (unMarkedProList != null) {
					prdMngInfoService.updateUnMarkedPro(typeId,
							Arrays.asList(unMarkedProList));
				}
				this.sendMsgToClient("success", res);
			} catch (Exception e) {
				this.sendMsgToClient("failed", res);
				e.printStackTrace();
			}
		}
	}

}
