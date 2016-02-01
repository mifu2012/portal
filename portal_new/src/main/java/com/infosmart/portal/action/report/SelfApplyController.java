package com.infosmart.portal.action.report;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.portal.action.BaseController;
import com.infosmart.portal.pojo.User;
import com.infosmart.portal.pojo.report.SelfApply;
import com.infosmart.portal.pojo.report.SelfReport;
import com.infosmart.portal.service.report.SelfApplyService;
import com.infosmart.portal.util.Const;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.dwpas.RightsHelper;

@Controller
@RequestMapping(value = "/selfApply")
public class SelfApplyController extends BaseController {
	@Autowired
	private SelfApplyService selfApplyService;

	@RequestMapping(value = "/indexSearch")
	public ModelAndView indexsSearch(@RequestParam String reportName,
			@RequestParam String state, HttpSession session,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		// req.setCharacterEncoding("ISO8859-1");
		reportName = getBytes(reportName);// 乱码需要转换
		User user = this.getCrtUser(session);
		Map<Object, Object> map = new HashMap<Object, Object>();
		ModelAndView mav = new ModelAndView();
		List<String> list = new ArrayList<String>();
		if (!(state.equals(""))) {
			// map.put("states", Arrays.asList(state.trim()));
			for (int i = 0; i < state.replaceAll(",", "").length(); i++) {
				list.add(i,
						state.replaceAll(",", "").toString()
								.substring(i, i + 1));
			}
			mav.addObject("stateList", list);
			map.put("states", list);
		}
		map.put("reportName", reportName);
		map.put("userId", user.getUserId());
		List<SelfReport> reportList = selfApplyService.queryReports(map);
		mav.addObject("reportList", reportList);
		mav.addObject("reportName", reportName);
		mav.setViewName("newReport/indexSearch");
		this.insertLog(req, "查询自服务报表");
		return mav;
	}

	/* 申请 */
	@RequestMapping(value = "/apply")
	public ModelAndView apply(@RequestParam String reportName,
			@RequestParam String state, HttpServletRequest req,
			HttpServletResponse res, HttpSession session, SelfReport selfReport) {
		SelfApply selfApply = new SelfApply();
		User user = this.getCrtUser(session);
		reportName = "";
		this.logger.info("state===" + state);
		try {
			selfApply.setReportId(selfReport.getReportId());
			selfApply.setReportName(getBytes(selfReport.getReportName()));
			selfApply.setState(2);
			selfApply.setUserId(user.getUserId());
			selfApply.setUserName(user.getUserName());
			List<SelfApply> self = selfApplyService.getSelfApplyById(selfApply);
			if (null != self) {
				selfApplyService.insertApplyReport(selfApply);
			}
		} catch (Exception e) {
			this.logger.info("**********报表申请错误***********", e);
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/selfApply/indexSearch?&reportName="
				+ reportName + "&state=" + state);
		this.insertLog(req, "用户申请自服务报表,报表名称：" + selfReport.getReportName());
		return mav;

	}

	/**
	 * 自服务报表栏目
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/reportMenuList")
	public String reportMenuList(HttpSession session, Model model) {
		/* 校验权限 */
		String selfRights = "";
		User user = this.getCrtUser(session);
		if (session.getAttribute(Const.SESSION_SECURITY_CODE) == null) {
			/*
			 * User userSelfRights = selfApplyService.getSelfRights(user
			 * .getUserId()); selfRights = userSelfRights.getSelfRights();
			 */
			selfRights = selfApplyService.getSelfRights(user.getUserId());
			/* 避免每次拦截用户操作时查询数据库，以下将用户所属角色权限、用户权限都存入session */
			session.setAttribute(Const.SESSION_SELF_RIGHTS, selfRights); // 将用户自助权限存入session
		} else {
			selfRights = session.getAttribute(Const.SESSION_SELF_RIGHTS)
					.toString();
		}

		List<SelfReport> reportMenuList = null;
		/* 避免每次拦截用户操作时查询数据库，以下将用户所属报表菜单都存入session */
		if (session.getAttribute(Const.SESSION_SELF_REPORT_MENU_LIST) == null) {
			/* 获取所有报表叶子节点 */
			// List<Report> reportMenuList1 = selfApplyService.getChildReport();
			reportMenuList = selfApplyService.getreportMenuList(user
					.getUserId());
			if (StringUtils.notNullAndSpace(selfRights)) {
				reportChecked(reportMenuList, selfRights);/* 校验是否有权限 */
			}
			if (reportMenuList != null && reportMenuList.size() > 0)
				session.setAttribute(Const.SESSION_SELF_REPORT_MENU_LIST,
						reportMenuList);
		} else {
			reportMenuList = (List<SelfReport>) session
					.getAttribute(Const.SESSION_SELF_REPORT_MENU_LIST);
		}
		if (reportMenuList != null && reportMenuList.size() > 0) {
			JSONArray arr = JSONArray.fromObject(reportMenuList);
			String json = arr.toString();
			json = json.replaceAll("reportDesId", "id").replaceAll(
					"reportName", "name");
			model.addAttribute("zTreeNodes", json);
			return "newReport/selfApplyMenu";
		} else {
			return "common/noReport";
		}
	}

	// 递归选中报表权限
	private List<SelfReport> reportChecked(List<SelfReport> reportList,
			String selfRights) {
		SelfReport report = null;
		if (reportList == null)
			return reportList;
		for (int i = 0; i < reportList.size(); i++) {
			report = reportList.get(i);
			if (report == null)
				continue;
			/* 判断是否有权限，无权限remove */
			if (RightsHelper.testRights(selfRights, report.getReportId())) {
				reportChecked(report.getSubReport(), selfRights);
			} else {
				reportList.remove(i);
				i--;
			}
		}
		return reportList;
	}

	/**
	 * 字符编码转换
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public String getBytes(String object) throws Exception {
		return new String(object.getBytes("ISO8859-1"), "UTF-8");
	}

	/**
	 * 根据用户ID和报表ID 查询是否收藏
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getSaveReports")
	public void getSaveReports(HttpServletRequest request,
			HttpServletResponse response) {
		String reportId = request.getParameter("reportId");
		if (reportId.length() == 0 || reportId == null) {
			this.sendMsgToClient("0", response);
		}
		SelfApply selfApply = new SelfApply();
		User user = this.getCrtUser(request.getSession());
		selfApply.setReportId(Integer.valueOf(reportId));
		selfApply.setUserId(user.getUserId());
		List<SelfApply> selfApplyList = selfApplyService
				.getSelfApplyById(selfApply);
		this.sendMsgToClient(String.valueOf(selfApplyList.size()), response);

	}

	/**
	 * 收藏报表
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping(value = "/collectReport")
	public void collectReport(HttpSession session, HttpServletRequest req,
			HttpServletResponse res, PrintWriter out) {
		String reportId = req.getParameter("reportId");
		String reportName = "";
		try {
			reportName = new String(req.getParameter("reportName").getBytes(
					"ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		SelfApply selfApply = new SelfApply();
		User user = this.getCrtUser(session);
		if (StringUtils.notNullAndSpace(reportId)) {
			selfApply.setReportId(Integer.valueOf(reportId));
			selfApply.setUserId(user.getUserId());
		}
		List<SelfApply> selfApplyList = selfApplyService
				.getSelfApplyById(selfApply);

		if (selfApplyList == null || selfApplyList.isEmpty()) {
			try {
				// selfApply插入数据
				selfApply.setReportName(reportName);
				selfApply.setState(3);
				selfApply.setUserName(user.getUserName());
				selfApplyService.insertApplyReport(selfApply);
				// 插入权限
				List<SelfReport> reportMenuList = selfApplyService
						.getreportMenuList(user.getUserId());
				String selfApplyRights = selfApplyService.getSelfRights(user
						.getUserId());
				if (StringUtils.notNullAndSpace(selfApplyRights)) {
					reportChecked(reportMenuList, selfApplyRights);/* 校验是否有权限 */
				}
				List<String> reportIds = new ArrayList<String>();
				if (reportMenuList != null && !(reportMenuList.isEmpty())) {
					for (SelfReport selfReport : reportMenuList) {
						if (selfReport == null
								|| selfReport.getReportId() == null)
							continue;
						reportIds.add(selfReport.getReportId().toString());
					}
				}
				reportIds.add(reportId);

				BigInteger selfRights = RightsHelper.sumRights(reportIds
						.toArray(new String[] {}));
				user.setSelfRights(selfRights.toString());
				this.insertLog(req, "收藏报表，报表ID为：" + reportId + ",报表名称为："
						+ reportName);
				selfApplyService.updateSelfRights(user);
				this.sendMsgToClient(this.isSuccess, res);
			} catch (Exception e) {
				e.printStackTrace();
				this.logger.error(e.getMessage(), e);
				this.sendMsgToClient(this.isFailed, res);
			}
		}

	}
}
