package com.infosmart.portal.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.infosmart.portal.pojo.DwpasCPrdInfo;
import com.infosmart.portal.service.ProdInfoService;

/**
 * 产品选择
 * 
 * @author infosmart
 * 
 */
@Controller
public class ProdoductInfoController extends BaseController {

	// 产品选择
	@Autowired
	private ProdInfoService prodInfoService;

	/**
	 * 选择产品信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/ProdoductInfo/choiceProductInfo")
	public void choiceProductInfo(HttpServletRequest request,
			HttpServletResponse response) {
		this.logger.info("列出所有的产品（非文件夹）");
		// 列出所有的子产品
		List<DwpasCPrdInfo> prdInfoList = this.prodInfoService
				.getAllProducts(this.getCrtUserTemplateId(request));
		if (prdInfoList == null) {
			this.logger.error("没有找到产品");
			prdInfoList = new ArrayList<DwpasCPrdInfo>();
		}
		this.sendJsonMsgToClient(prdInfoList, response);
	}
}
