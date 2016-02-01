package com.infosmart.portal.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.taglib.PageInfo;

/**
产品管理
 * 
 * @author infosmart
 * 
 */
@Controller
public class KpiInfoManController extends BaseController {
	/**
	 * 转到查询页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/kpiInfoMan/searchPage")
	public ModelAndView searchPage(HttpServletRequest request,
			HttpServletResponse response) {
		/**
		 * 测试产品选择 hgt
		 */
		/*List<DwpasCPrdInfo> list=prodInfoComponent.getAllProducts();
		String productId= "2001";
		DwpasCPrdInfo cPrdInfo=prodInfoComponent.getProdInfoById(productId);
		request.setAttribute("prodInfos", list);
		return new ModelAndView("/Product/selprod");*/
		return new ModelAndView("/KpiInfoMan/SearchPage");
	}

	/**
	 * 分页查询指标信息
	 * 
	 * @param request
	 * @param response
	 * @param dwpasCKpiInfo
	 * @return
	 */
	@RequestMapping("/kpiInfoMan/listKpiInfo")
	public ModelAndView listKpiInfo(HttpServletRequest request,
			HttpServletResponse response, DwpasCKpiInfo dwpasCKpiInfo) {
		// 页码
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(request
					.getParameter(PageInfo.PAGE_PAGE_NO));
		} catch (Exception e) {

		}
		PageInfo pageInfo = this.dwpasCKpiInfoService
				.listDwpasCKpiInfoByPagination(dwpasCKpiInfo, pageNo,
						PageInfo.DEFAULT_PAGE_SIZE);
		request.setAttribute(PageInfo.PROPERTY_LIST_NAME, pageInfo);
		return new ModelAndView("/KpiInfoMan/ListDataInfo");
	}
}
