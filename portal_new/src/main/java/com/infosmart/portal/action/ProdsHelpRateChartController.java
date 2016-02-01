package com.infosmart.portal.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.service.UserConsultingService;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.vo.dwpas.ProdKpiData;

@Controller
public class ProdsHelpRateChartController extends BaseController {
	@Autowired
	private UserConsultingService userConsultingService;

	@RequestMapping("/ProdsHelpRateChart/instrumentPanelShow")
	public ModelAndView instrumentPanelShow(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		Map map = new HashMap();
		String productId = req.getParameter("productId");
		String queryDate = req.getParameter("queryDate");
		if (StringUtils.notNullAndSpace(queryDate)) {
			queryDate = queryDate.replace("-", "");
		}
		List<ProdKpiData> prodKpiDatas = userConsultingService
				.queryChildProductClasseByPrdId(this.getCrtUserTemplateId(req),
						productId, queryDate, DwpasCKpiInfo.KPI_TYPE_OF_DAY);
		BigDecimal maxVal = Constants.ZERO;
		if (prodKpiDatas == null)
			return null;
		for (ProdKpiData prodKpiDataDTO : prodKpiDatas) {
			setColor(prodKpiDataDTO);
			if (prodKpiDataDTO.getBaseValue().compareTo(maxVal) >= 0) {
				maxVal = prodKpiDataDTO.getBaseValue();
			}
		}
		map.put("maxRate", maxVal);
		map.put("prodsHelpRates", prodKpiDatas);
		return new ModelAndView("/homePage/prodsHelpRateChart2", map);
	}

	/**
	 * 设置颜色
	 * 
	 * @param prodKpiDataDTO
	 * @param queryProdHelpRate
	 */
	private void setColor(ProdKpiData prodKpiDataDTO) {
		BigDecimal baseValue = prodKpiDataDTO.getBaseValue() == null ? Constants.ZERO
				: prodKpiDataDTO.getBaseValue().multiply(Constants.ONE_HANDREN);
		BigDecimal preAlertValue = prodKpiDataDTO.getPreAlertValue() == null ? Constants.ZERO
				: prodKpiDataDTO.getPreAlertValue().multiply(
						Constants.ONE_HANDREN);
		BigDecimal alertValue = prodKpiDataDTO.getAlertValue() == null ? Constants.ZERO
				: prodKpiDataDTO.getAlertValue()
						.multiply(Constants.ONE_HANDREN);
		prodKpiDataDTO.setBaseValue(baseValue);
		prodKpiDataDTO.setPreAlertValue(preAlertValue);
		prodKpiDataDTO.setAlertValue(alertValue);

		if (baseValue.compareTo(alertValue) >= 0) {
			prodKpiDataDTO.setColor(Constants.Help_Rate_Alert);
		} else if (baseValue.compareTo(preAlertValue) >= 0
				&& baseValue.compareTo(alertValue) < 0) {
			// 预警已经不要了，因此预警颜色和普通颜色一致
			prodKpiDataDTO.setColor(Constants.Help_Rate_Normal);
		} else {
			prodKpiDataDTO.setColor(Constants.Help_Rate_Normal);
		}
	}
}
