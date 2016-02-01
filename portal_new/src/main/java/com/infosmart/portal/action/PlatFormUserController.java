package com.infosmart.portal.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.portal.pojo.DwpasStPlatformData;
import com.infosmart.portal.service.InstrumentPanelManager;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.MathUtils;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.vo.dwpas.PlatFormData;

/**
 * 产品全图饼图
 * 
 * @author Administrator
 * 
 */
@Controller
public class PlatFormUserController extends BaseController {
	@Autowired
	private InstrumentPanelManager instrumentPanelManager;

	@RequestMapping(value = "/productAmchart/chartShow")
	public ModelAndView chartShow(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		Map<String, List<PlatFormData>> map = new HashMap<String, List<PlatFormData>>();
		String queryDate = req.getParameter("queryDate");
		if (!StringUtils.notNullAndSpace(queryDate)) {
			// 默认是取session中日期，初始是当前日期
			queryDate = this.getCrtQueryDateOfReport(req);
		} else {
			queryDate = queryDate.replace("-", "");
		}
		List<String> colorList = Constants.CHART_COLOR_LIST;
		DwpasStPlatformData platFormDataDTO = instrumentPanelManager
				.queryPlatFormUser(queryDate);
		List<PlatFormData> patFormDataVOs = new ArrayList<PlatFormData>();
		PlatFormData platFormDataTB = null;
		PlatFormData platFormDataIN = null;
		PlatFormData platFormDataOUT = null;
		if (platFormDataDTO != null) {
			platFormDataTB = new PlatFormData("WAP",
					getValueDisplay(platFormDataDTO.getTaobaoRate()),
					platFormDataDTO.getTaobaoRateTrend().intValue());
			platFormDataTB.setColor(colorList.get(0));
			platFormDataIN = new PlatFormData("站内",
					getValueDisplay(platFormDataDTO.getInnerRate()),
					platFormDataDTO.getInnerRate().intValue());
			platFormDataIN.setColor(colorList.get(1));
			platFormDataOUT = new PlatFormData("外部",
					getValueDisplay(platFormDataDTO.getOutRate()),
					platFormDataDTO.getOutRateTrend().intValue());
			platFormDataOUT.setColor(colorList.get(2));
		}
		patFormDataVOs.add(platFormDataTB);
		patFormDataVOs.add(platFormDataIN);
		patFormDataVOs.add(platFormDataOUT);
		map.put("platFormUsers", patFormDataVOs);
		this.insertLog(req, "查看产品全图饼图");
		return new ModelAndView("/homePage/platFormUser1", map);
	}

	private String getValueDisplay(BigDecimal rate) {
		if (rate == null) {
			return "-";
		}
		return String.valueOf(MathUtils.multi(Constants.ONE_HANDREN, rate)
				.setScale(2, BigDecimal.ROUND_HALF_UP));
	}
}
