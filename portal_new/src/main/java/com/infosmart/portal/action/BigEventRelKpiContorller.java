package com.infosmart.portal.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.portal.pojo.BigEventPo;
import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.service.BigEventService;
import com.infosmart.portal.service.DwpasCKpiInfoService;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.StringUtils;

@Controller
public class BigEventRelKpiContorller extends BaseController {
	@Autowired
	private DwpasCKpiInfoService dwpasCKpiInfoService;

	@Autowired
	private BigEventService bigEventService;

	public ModelAndView queryEventRelKpiCode(HttpServletRequest request,
			HttpServletResponse response, BigEventPo bigEventPo)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		String queryDate = request.getParameter("queryDate");
		if (StringUtils.notNullAndSpace(queryDate)) {
			// 变更了日期
			this.setCrtQueryDateOfReport(request, queryDate);
		} else {
			// 取默认日期
			queryDate = this.getCrtQueryDateOfReport(request);
		}
		if (StringUtils.notNullAndSpace(queryDate)) {
			String queryValueDate = queryDate.replace("-", "");
			mav.addObject("date", queryValueDate);
		}
		List<DwpasCKpiInfo> eventRelKpiCodeList = dwpasCKpiInfoService
				.queryEventRelKpiCode(bigEventPo.getEventId());

		return null;
	}

	/**
	 * 查看大事记详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/BigEvent/showBigEventDetails")
	public ModelAndView showBigEventDetails(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/homePage/eventPopup");
		// ID
		String eventId = request.getParameter("eventId");
		if (!StringUtils.notNullAndSpace(eventId)) {
			return new ModelAndView(this.NOT_FOUND_ACTION);
		}
		BigEventPo bigEveng = null;
		// 查询关联指标
		StringBuffer relKpiCodes = new StringBuffer();
		try {
			// 查询大事记
			List<BigEventPo> bigEvengList = this.bigEventService
					.listLatelyBigEventAndRelateKpiInfoById(new Integer(eventId));
			if (bigEvengList != null && bigEvengList.size() > 0) {
				bigEveng = bigEvengList.get(0);
				if (bigEveng != null)
					this.logger.info("事件标题：" + bigEveng.getTitle());
				// this.logger.info("关联指标:" + bigEveng.getKpiInfoList().size());
				if (bigEveng != null && bigEveng.getKpiInfoList() != null) {
					for (DwpasCKpiInfo kpiInfo : bigEveng.getKpiInfoList()) {
						if (relKpiCodes.toString().length() > 0)
							relKpiCodes.append(";");
						relKpiCodes.append(kpiInfo.getKpiCode());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
		mv.addObject("bigEvent", bigEveng);
		mv.addObject("relKpiCodes", relKpiCodes.toString());
		return mv;
	}

}
