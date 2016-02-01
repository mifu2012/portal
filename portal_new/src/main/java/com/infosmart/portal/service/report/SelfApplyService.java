package com.infosmart.portal.service.report;

import java.util.List;
import java.util.Map;

import com.infosmart.portal.pojo.User;
import com.infosmart.portal.pojo.report.ReportDesign;
import com.infosmart.portal.pojo.report.SelfApply;
import com.infosmart.portal.pojo.report.SelfReport;

public interface SelfApplyService {
	List<ReportDesign> queryReportPages(ReportDesign report);

	List<SelfReport> queryReports(Map<Object, Object> map);

	String getSelfRights(Integer userId);

	List<ReportDesign> getChildReport();

	List<SelfReport> getreportMenuList(Integer userId);

	void insertApplyReport(SelfApply selfApply);

	List<SelfApply> getSelfApplyById(SelfApply selfApply);

	void updateSelfRights(User user);
}
