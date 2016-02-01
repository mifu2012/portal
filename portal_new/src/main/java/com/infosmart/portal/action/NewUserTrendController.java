package com.infosmart.portal.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.portal.pojo.TrendListDTO;
import com.infosmart.portal.service.AskWindVaneManager;
import com.infosmart.portal.service.PrdCrossStockChartService;
import com.infosmart.portal.util.dwpas.KpiTypeEnum;
import com.infosmart.portal.util.dwpas.TrendTypeEnum;
import com.infosmart.portal.vo.Chart;
import com.infosmart.portal.vo.dwpas.PrdCrossDataTypeEnum;
import com.infosmart.portal.vo.dwpas.PrdCrossParam;

/**
 * 新用户趋势Controller
 * 
 * @author yufei.sun
 * @version $Id: NewUserTrendController.java, v 0.1 2011-12-8 上午10:59:31
 *          yufei.sun Exp $
 */
@Controller
public class NewUserTrendController extends BaseController {
	@Autowired
	private AskWindVaneManager askWindVaneManager;
	/** 页面地址 */
	@RequestMapping("/trentAmchart/getTrent")
	public ModelAndView getTrentAmchart(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		String date = request.getParameter("queryDate");
		List<TrendListDTO> leftList = askWindVaneManager.init();
		map.put("leftList", leftList);
		map.put("date", date);
		map.put("type", TrendTypeEnum.NORMAL_CHART.getCode());
		return new ModelAndView("/Trend/trend", map);
	}
	
}
