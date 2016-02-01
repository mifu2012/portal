package com.infosmart.controller.report;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.controller.BaseController;
import com.infosmart.po.User;
import com.infosmart.po.report.ReportDesign;
import com.infosmart.po.report.SelfApply;
import com.infosmart.service.report.SelfApplyService;
import com.infosmart.util.Const;
import com.infosmart.util.RightsHelper;

/**
 * 自服务报表
 * date 2012-06-04 
 * @author infosmart
 * 
 */
@Controller
@RequestMapping(value = "/selfApply")
public class SelfApplyController extends BaseController {
	@Autowired
	private SelfApplyService selfApplyService;
	
	/**
	 * 查询子服务清单
	 * @param req
	 * @param res
	 * @param selfApply
	 * @return
	 */
	@RequestMapping
	public ModelAndView list(HttpServletRequest req, HttpServletResponse res,
			SelfApply selfApply) {
		if (selfApply != null) {
			// 默认为申请中
			if (selfApply.getState() == null || selfApply.getState() == 0) {
				selfApply.setState(2);
			} else if (selfApply.getState() == -1) {// query all
				selfApply.setState(null);
			}
			if(selfApply.getReportName()!=null&&selfApply.getReportName().length()>0){
				selfApply.setReportName(selfApply.getReportName().trim());
			}
			if(selfApply.getUserName()!=null&&selfApply.getUserName().length()>0){
				selfApply.setUserName(selfApply.getUserName().trim());
			}
		}
		List<SelfApply> itemList = selfApplyService.queryItemPages(selfApply);
		ModelAndView mav = new ModelAndView();
		mav.addObject("itemList", itemList);
		mav.addObject("selfApply", selfApply);
		mav.setViewName("selfApply/selfApply");
		this.insertLog(req, "进入自服务报表后台");
		return mav;
	}

	/**
	 * 进入审核页面
	 * @author infosmart
	 * @param Id
	 * @return
	 */
	@RequestMapping(value = "/edit{id}")
	public ModelAndView toEdit(@PathVariable int id, HttpServletRequest req) {
		ModelAndView mv = new ModelAndView();
		/* 查询审核信息 */
		SelfApply selfApply = selfApplyService.getSelfApplyById(id);
		mv.addObject("selfApply", selfApply);
		mv.setViewName("selfApply/selfApply_info");
		this.insertLog(req, "进入审核页面");
		return mv;
	}

	/**
	 * 审核
	 * date 2012-06-04 
	 * @author infosmart
	 * @param request
	 * @param selfApply
	 * @return
	 */
	@RequestMapping(value = "/saveSelfApply", method = RequestMethod.POST)
	public ModelAndView saveSelfApply(HttpServletRequest request,
			SelfApply selfApply) {
		if (selfApply == null) {
			this.logger.warn("审核用户时传递的SelfApply对象为null");
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("common/save_result");
		if (selfApply != null) {
			/* 判断用户不存在不做任何操作 */
			if (null == selfApply.getUserId()) {
				return mv;
			}
			Integer reportId = selfApply.getReportId();
			User user = (User) request.getSession().getAttribute(
					Const.SESSION_USER);
			selfApply.setUpdateId(user.getUserId());
			selfApplyService.updateSelfApply(selfApply);
			/* 自服务报表权限 */
			String selfRights = selfApplyService.getSelfRights(selfApply
					.getUserId());
			/* 查询所有报表遍历出有权限的的reportIds */
			List<ReportDesign> report = selfApplyService.getChildReport();
			List<ReportDesign> reportList = this.reportChecked(report, selfRights);
			List<Integer> reportIds = this.reportIds(reportList, reportId,
					selfApply.getState());
			BigInteger rights = new BigInteger("0");
			if (reportIds != null && reportIds.size() > 0) {
				for (int i = 0; i < reportIds.size(); i++) {
					rights = rights.setBit(reportIds.get(i));
				}
			}
			// User user = new User();
			user.setUserId(selfApply.getUserId());
			user.setSelfRights(rights.toString());
			try {
				selfApplyService.updateSelfRights(user);
				mv.addObject("msg", "success");
				if(selfApply != null){
					if(selfApply.getState()==3){
						this.insertLog(request, "用户："+user.getUsername()+" ,审核自服务报表，结果：审核通过");
					}else if(selfApply.getState()==4){
						this.insertLog(request, "用户："+user.getUsername()+" ,审核自服务报表，结果：审核驳回");
					}
				}
				this.insertLog(request, "用户："+user.getUsername()+" ,修改报表自服务权限："+user.getSelfRights());
			} catch (Exception e) {

				mv.addObject("msg", "failed");
				this.insertLog(request, "用户："+user.getUsername()+" ,审核自服务报表，结果：审核驳回");
			}

		}
		return mv;
	}

	/**
	 * 递归选中报表ID,无权限报表删除
	 * date 2012-06-04
	 * @author infosmart 
	 * @param reportList
	 * @param reportRights
	 * @return
	 */
	private List<ReportDesign> reportChecked(List<ReportDesign> reportList,
			String reportRights) {
		ReportDesign report = null;
		if (reportList == null)
			return reportList;
		for (int i = 0; i < reportList.size(); i++) {
			report = reportList.get(i);
			if (RightsHelper.testRights(reportRights, report.getReportId())) {
				reportChecked(report.getReportIds(), reportRights);
			} else {
				reportList.remove(i);
				i--;
			}
		}
		return reportList;
	}

	/**
	 * 遍历获取报表ID
	 * date 2012-06-04
	 * @author infosmart
	 * @param reportIdsList
	 * @param reportId
	 * @return
	 */
	private List<Integer> reportIds(List<ReportDesign> reportIdsList,
			Integer reportId, Integer state) {

		List<Integer> reportIds = new ArrayList<Integer>();
		if (reportIdsList == null) {
			reportIds.add(reportId);
			return reportIds;
		}
		if (null != reportId && state != 4) {
			reportIds.add(reportId);
		}
		for (int i = 0; i < reportIdsList.size(); i++) {
			int id = reportIdsList.get(i).getReportId();
			/* 判断权限里是否存在报表ID */
			if (id != reportId) {
				reportIds.add(id);
			}
		}
		return reportIds;
	}

}
