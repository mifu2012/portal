package com.infosmart.service.report;

import java.util.List;

import com.infosmart.po.User;
import com.infosmart.po.report.ReportDesign;
import com.infosmart.po.report.SelfApply;

public interface SelfApplyService {
	/**
	 * 分页查询自助服务
	 * 
	 * @param selfApply
	 * @return
	 */
	List<SelfApply> queryItemPages(SelfApply selfApply);

	/**
	 * 通过ID单一查询
	 * 
	 * @param id
	 * @return
	 */
	SelfApply getSelfApplyById(Integer id);

	/**
	 * 更新自助服务信息
	 * 
	 * @param selfApply
	 */
	void updateSelfApply(SelfApply selfApply);

	/**
	 * 获取所有子节点
	 * date 2012-06-04 infosmart
	 * @return
	 */
	List<ReportDesign> getChildReport();

	/**
	 * 更新自服务权限
	 * 
	 * @param user
	 */
	void updateSelfRights(User user);

	/**
	 * 获取自助服务权限
	 * 
	 * @param userId
	 * @return
	 */
	String getSelfRights(Integer userId);

	/**
	 * 获取所有申请通过的自服务报表
	 * 
	 * @param userId
	 * @return
	 */
	List<SelfApply> querySelfApplyByUsrID(int userId);

	/**
	 * 报表名称同步
	 * 
	 * @param report
	 */
	void updateSelfReportName(ReportDesign report);

	/**
	 * 主表删除报表自服务报表同步
	 *  infosmart 20120604
	 * @param reportIds
	 */
	void deleteSelfApplyByIds(List<Integer> reportIds);
}
