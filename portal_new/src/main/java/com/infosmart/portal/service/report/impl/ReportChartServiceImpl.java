package com.infosmart.portal.service.report.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.report.ReportChartFiled;
import com.infosmart.portal.service.impl.BaseServiceImpl;
import com.infosmart.portal.service.report.ReportChartService;

@Service
public class ReportChartServiceImpl extends BaseServiceImpl implements
		ReportChartService {


	@Override
	public List<ReportChartFiled> getAllReportChart(int reportId) {
		return this.myBatisDao.getList(
				"reportChartFiledMapper.getAllReportChart", reportId);
	}
	@Override
	public ReportChartFiled getIsSelOrNotTableFiled(int chartId) {
		return this.myBatisDao.get(
				"reportChartFiledMapper.getIsSelOrNotTableFiled", chartId);
	}
}
