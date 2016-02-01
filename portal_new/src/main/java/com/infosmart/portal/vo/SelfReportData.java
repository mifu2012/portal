package com.infosmart.portal.vo;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsDateJsonBeanProcessor;

public class SelfReportData {

	private List data;

	private ReportPageInfo pageInfo;

	private String exception;

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public ReportPageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(ReportPageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SelfReportData reportData = new SelfReportData();
		//
		List dataList = new ArrayList();
		dataList.add("3333");
		dataList.add("2011-02-03");
		dataList.add(45);

		List<List> list = new ArrayList();
		list.add(dataList);
		list.add(dataList);
		//
		reportData.setData(list);
		//
		reportData.setException("异常");
		//
		ReportPageInfo pageInfo = new ReportPageInfo();
		pageInfo.setTotalRowNum(100);
		reportData.setPageInfo(pageInfo);

		JSONObject data = new JSONObject();
		data.put("data", list);
		data.put("exception", "异常信息");
		data.put("pageInfo", pageInfo);
		//
		JsDateJsonBeanProcessor beanProcessor = new JsDateJsonBeanProcessor();
		JsonConfig config = new JsonConfig();
		config.registerJsonBeanProcessor(java.sql.Date.class, beanProcessor);
		JSONObject jsonObj = JSONObject.fromObject(data, config);
		JSON json = JSONSerializer.toJSON(jsonObj, config);
		System.out.println("json=" + json.toString());
	}

}