package com.infosmart.portal.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.portal.service.UserConsultingService;
import com.infosmart.portal.util.MathUtils;
import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.vo.dwpas.ProdKpiData;

/**
 * 用户咨询情况
 * 
 * @author infosmart
 * 
 */
@Controller
public class UserConsultingController extends BaseController {
	@Autowired
	private UserConsultingService userConsultingService;

	// 用户咨询情况方法
	@RequestMapping("/UserConsulting/UserConsultingShow")
	public ModelAndView UserConsultingShow(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取日期
		String queryDate = request.getParameter("queryDate");
		if (queryDate == null || "".equals(queryDate) || queryDate == "") {
			queryDate = this.getCrtQueryDateOfReport(request);
		}
		// 把日期放入到session当中
		request.getSession().setAttribute("queryDate", queryDate);
		map.put("date", queryDate);
		map.put("queryType", String.valueOf(DwpasCKpiInfo.KPI_TYPE_OF_DAY));
		// 格式化queryDate传到后台
		String queryValueDate = queryDate.replace("-", "");
		// 用户求助情况
		List<ProdKpiData> queryProductClasses = userConsultingService
				.queryProductClasse(this.getCrtUserTemplateId(request),
						queryDate, DwpasCKpiInfo.KPI_TYPE_OF_DAY);
		// 查询所有产品线
		map.put("productCatalogs", queryProductClasses);
		// 获取所有产品线的预警，警告，最大，最小值
		map.put("allProdRateHelp", queryProductClasses);
		return new ModelAndView("/homePage/index", map);
	}

	private String createFlashPar(ProdKpiData prodKpiDataDTO) {
		StringBuffer sbUrl = new StringBuffer();
		sbUrl.append("max=")
				.append(MathUtils.div_100(prodKpiDataDTO.getMaxValue())
						.doubleValue()).append("&");
		sbUrl.append("min=")
				.append(MathUtils.div_100(prodKpiDataDTO.getMinValue())
						.doubleValue()).append("&");
		sbUrl.append("value=")
				.append(MathUtils.div_100(prodKpiDataDTO.getBaseValue())
						.doubleValue()).append("&");
		sbUrl.append("warn=")
				.append(MathUtils.div_100(prodKpiDataDTO.getAlertValue())
						.doubleValue()).append("&");
		sbUrl.append("product_name=").append(encodeName(prodKpiDataDTO))
				.append("&");
		sbUrl.append("color=").append(getColor(prodKpiDataDTO));

		return sbUrl.toString();
	}

	private String getColor(ProdKpiData prodKpiDataDTO) {
		if (prodKpiDataDTO.isOverAlert()) {
			return "#CF1111";// "red";
		}
		return "#79BA16";// "green";
	}

	private String encodeName(ProdKpiData prodKpiDataDTO) {
		if (null == prodKpiDataDTO.getProductName()) {
			return prodKpiDataDTO.getParentId();
		}
		try {
			return URLEncoder.encode(prodKpiDataDTO.getProductName(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return prodKpiDataDTO.getProductId();
		}
	}

	public UserConsultingService getUserConsultingService() {
		return userConsultingService;
	}

	public void setUserConsultingService(
			UserConsultingService userConsultingService) {
		this.userConsultingService = userConsultingService;
	}

}
