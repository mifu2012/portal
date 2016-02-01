package com.infosmart.portal.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCPrdDim;
import com.infosmart.portal.pojo.DwpasCPrdInfo;
import com.infosmart.portal.pojo.DwpasCSystemMenu;
import com.infosmart.portal.pojo.DwpasStKpiData;
import com.infosmart.portal.pojo.ProductDimHealth;
import com.infosmart.portal.service.DwpasCPrdDimService;
import com.infosmart.portal.service.DwpasCSystemMenuService;
import com.infosmart.portal.service.DwpasPageSettingService;
import com.infosmart.portal.service.ProductHealthService;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.StringUtils;

/**
 * 转到产品健康度主界面
 * 
 * @author infosmart
 * 
 */
@Controller
public class ProductHealthController extends BaseController {

	protected final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private DwpasCPrdDimService dwpasCPrdDimService;

	@Autowired
	private ProductHealthService productHealthService;
    @Autowired
    private DwpasPageSettingService pageSettingService;
    @Autowired 
    private DwpasCSystemMenuService  menuService;
	/**
	 * http://localhost:8080/testProj/productHealth/showProductHealthMainPage?productId=2001&crtYearMonth=2011-06&nextYearMonth=2011-07
	 * 
	 * @param request
	 * @param response
	 * @param productId
	 *            URL后的参数:产品ID
	 * @param crtYearMonth
	 *            URL后的参数:统计时间(年月),�?2011-11
	 * @param nextYearMonth
	 *            URL后的参数:统计时间(年月),�?2011-11
	 * @return
	 */
	@RequestMapping("/productHealth/showProductHealth")
	public ModelAndView showProductHealth(HttpServletRequest request,
			HttpServletResponse response) {
		String menuId = request.getParameter("menuId");//菜单ID
		String productId = request.getParameter("productId");
		if (!StringUtils.notNullAndSpace(productId)) {
			// 如果为空的话,则取session的产品�??
			productId = this.getCrtProductIdOfReport(request);
			if(!StringUtils.notNullAndSpace(productId)){
				return new ModelAndView("/common/noProduct");
			}
		}
		this.logger.info("开始显示产品健康度信息:" + productId);
		// 日期格式为yyyy-MM
		String crtYearMonth = request.getParameter("crtYearMonth");
		// 日期格式为yyyy-MM
		String nextYearMonth = request.getParameter("nextYearMonth");
		if (!StringUtils.notNullAndSpace(crtYearMonth)) {
			// 默认取session中的�?
			crtYearMonth = this.getCrtQueryMonthOfReport(request);
			this.logger.info("默认取SESSION查询年月值:" + crtYearMonth);
		}else{
			this.setCrtQueryMonthOfReport(request,crtYearMonth);
		}
		if (!StringUtils.notNullAndSpace(nextYearMonth)) {
			// 默认为上个月
			nextYearMonth = DateUtils.formatUtilDate(DateUtils
					.getPreviousDate(DateUtils.parseByFormatRule(crtYearMonth,
							"yyyy-MM")), "yyyy-MM");
			this.logger.info("默认为比较上个月的日期");

		}
		this.logger.info("显示某个月的六维度图:" + crtYearMonth);
		// 查询产品信息
		DwpasCPrdInfo produnctInfo = this.dwpasCPrdInfoService
				.getDwpasCPrdInfoByProductId(productId);
		if (produnctInfo == null) {
			// 没有找到该产品信�?
			this.logger.error("没有找到该产品信息:" + productId);
			ModelAndView modelView = new ModelAndView(this.NOT_FOUND_PAGE);
			modelView.addObject("errorMsg", "没有找到该产品信息:" + productId);
			// 转到/jsp/NoFound.jsp
			return modelView;
		} else {
			// 保存在session�?
			this.setCrtProductIdOfReport(request, productId);
		}
		// 查询产品六维度配置信�?
		DwpasCPrdDim dwpasCPrdDim = this.dwpasCPrdDimService
				.getDwpasCPrdDimByProductId(productId,this.getCrtUserTemplateId(request));
		if (dwpasCPrdDim == null) {
			this.logger.error("没有找到该产品六纬度配置信息:" + productId);
			ModelAndView modelView = new ModelAndView(this.NOT_FOUND_PAGE);
			modelView.addObject("errorMsg", " 该产品"+"("+produnctInfo.getProductName()+")"+"未配置维度信息，故页面无法加载！\n"+"建议管理员在后台为此产品配置维度信息。");
			// 转到/jsp/NoFound.jsp
			return modelView;
		}
		this.logger.info("该产品六维度信息指标1:" + dwpasCPrdDim.getDim1Code());
		// 第一个日期六维度数据
		ProductDimHealth crtMonthProdHealthData = this.productHealthService
				.getDwpasCPrdDim(dwpasCPrdDim, StringUtils.replace(
						crtYearMonth, "-", ""),
						DwpasStKpiData.DATE_TYPE_OF_MONTH);
		// 对比日期的六维度数据
		ProductDimHealth nextMonthProdHealthData = this.productHealthService
				.getDwpasCPrdDim(dwpasCPrdDim, StringUtils.replace(
						nextYearMonth, "-", ""),
						DwpasStKpiData.DATE_TYPE_OF_MONTH);
		//当前菜单的所有模块信息
		Map<String, Object> moduleInfoMap = pageSettingService
				.getModuleInfoByMenuIdAndDateType(menuId,DwpasCKpiInfo.KPI_TYPE_OF_MONTH);
		//经纬仪menu_type='2'的所有菜单
		Map<String,String> menumap = new HashMap<String, String>();
		List<DwpasCSystemMenu> menulist = menuService.listOneDwpasCSystemMenu(Integer.parseInt(this.getCrtUserTemplateId(request)),"2");
		   if(menulist!=null && menulist.size()>0){
			   for(DwpasCSystemMenu menu:menulist){
				   menumap.put("m_"+menu.getMenuCode(),menu.getMenuId());  
			   }
		   }
		// 当前产品信息
		request.setAttribute("prodInfo", produnctInfo);
		// 当前年月
		request.setAttribute("crtYearMonth", crtYearMonth);
		// 对比年月
		request.setAttribute("nextYearMonth", nextYearMonth);
		// 当前六纬度指标信�?
		request.setAttribute("dwpasCPrdDim", dwpasCPrdDim);
		// 当前月数�?
		request.setAttribute("prodHealthOfData1", crtMonthProdHealthData);
		// 对比月数�?
		request.setAttribute("prodHealthOfData2", nextMonthProdHealthData);
		//菜单ID
		request.setAttribute("menuId",menuId);
		//模块集合
		request.setAttribute("modulemap",moduleInfoMap);
		//菜单集合 
		request.setAttribute("menumap", menumap);
		// 转到 /jsp/ProductHealth/ProductHealth.jsp
		return new ModelAndView("/ProductHealth/ProductHealth");
	}

	/**
	 * 显示产品健康度六维度�?
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/productHealth/showProductHealthSixDimensionChar")
	public void showProductHealthSixDimensionChar(HttpServletRequest request,
			HttpServletResponse response) {
		// 产品ID
		String productId = request.getParameter("productId");
		if (!StringUtils.notNullAndSpace(productId)) {
			// 如果为空的话,则取session的产品ID??
			productId = this.getCrtProductIdOfReport(request);
		}
		// 日期1:年月
		String crtYearMonth = request.getParameter("crtYearMonth");
		this.logger.info("日期1:" + crtYearMonth);
		// 日期2
		String nextYearMonth = request.getParameter("nextYearMonth");
		if (!StringUtils.notNullAndSpace(crtYearMonth)) {
			// 默认取session中的日期?
			crtYearMonth = this.getCrtQueryMonthOfReport(request);
		}
		if (!StringUtils.notNullAndSpace(nextYearMonth)) {
			// 默认为上个月
			nextYearMonth = DateUtils.formatUtilDate(DateUtils
					.getPreviousDate(DateUtils.parseByFormatRule(crtYearMonth,
							"yyyy-MM")), "yyyy-MM");
		}
		this.logger.info("日期2:" + nextYearMonth);
		this.logger.info("开始始显示产品健康六纬图:" + productId);
		Date crtDate = DateUtils.parseByFormatRule(crtYearMonth, "yyyy-MM");
		Date nextDate = DateUtils.parseByFormatRule(nextYearMonth, "yyyy-MM");
		crtYearMonth = DateUtils.formatByFormatRule(crtDate, "yyyyMM");
		nextYearMonth = DateUtils.formatByFormatRule(nextDate, "yyyyMM");
		// 查询产品信息
		DwpasCPrdInfo produnctInfo = this.dwpasCPrdInfoService
				.getDwpasCPrdInfoByProductId(productId);
		if (produnctInfo == null) {
			// 没有找到该产品信息
			this.logger.error("没有找到该产品信息:" + productId);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print("没有找到该产品信息:" + productId);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		} else {
			// 保存在session�?
			this.setCrtProductIdOfReport(request, productId);
		}
		// 查询产品六维度配置信息?
		DwpasCPrdDim dwpasCPrdDim = this.dwpasCPrdDimService
				.getDwpasCPrdDimByProductId(productId, this.getCrtUserTemplateId(request));
		if (dwpasCPrdDim == null) {
			this.logger.error("没有找到该产品六纬度配置信息:" + productId);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print("没有找到该产品六纬度配置信息:" + productId);
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		this.logger.info("开始查询六维度数据");
		// 第一个日期六维度数据
		ProductDimHealth crtMonthProdHealthData = this.productHealthService
				.getDwpasCPrdDim(dwpasCPrdDim, StringUtils.replace(
						crtYearMonth, "-", ""),
						DwpasStKpiData.DATE_TYPE_OF_MONTH);
		// 对比日期的六维度数据
		ProductDimHealth nextMonthProdHealthData = this.productHealthService
				.getDwpasCPrdDim(dwpasCPrdDim, StringUtils.replace(
						nextYearMonth, "-", ""),
						DwpasStKpiData.DATE_TYPE_OF_MONTH);
		try {
			// 生成六维度图XML数据
			VelocityEngine ve = new VelocityEngine();
			Properties properties = new Properties();
			// properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH,cfgMap.get("templatePath")+"/prodana/screen/amchartDataVM");
			properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, request
					.getSession().getServletContext().getRealPath("/")
					+ Constants.VM_FILE_PATH);
			ve.init(properties);

			Template template = ve
					.getTemplate("prodHealthMainData.vm", "UTF-8");
			VelocityContext context = new VelocityContext();
			// 六维度数�?
			context.put("data1", crtMonthProdHealthData);// 当前�?
			context.put("data2", nextMonthProdHealthData);// 对比�?
			context.put("year1", DateUtils.formatByFormatRule(crtDate, "yyyy"));
			// 当前年月
			context.put("month1", DateUtils.formatByFormatRule(crtDate, "MM"));
			context.put("year2", DateUtils.formatByFormatRule(crtDate, "yyyy"));
			// 比较年月
			context.put("month2", DateUtils.formatByFormatRule(nextDate, "MM"));
			// 输出�?
			StringWriter writer = new StringWriter();
			// 转换输出
			template.merge(context, writer);
			response.setContentType("text/xml;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(writer.toString());
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
		this.logger.info("结束显示产品健康六纬度:" + productId);
	}
}
