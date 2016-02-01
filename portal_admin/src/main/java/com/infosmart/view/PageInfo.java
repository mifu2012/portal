package com.infosmart.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 分页信息
 * @author Administrator
 *
 */
public class PageInfo implements Serializable {
	public static final String PROPERTY_LIST_NAME = "request_attribute_data_list";
	public static final String PAGE_ORDER_FIELD = "orderField";
	public static final String PAGE_PAGE_NO = "pageNo";
	public static final int DEFAULT_PAGE_SIZE = 15;
	public static final String QUERY_CONDITION = "queryCondition";
	// 数据列表
	private List dataList = new ArrayList();
	// 排序字段
	private String orderField = "";
	// 当前页面
	private int pageNo = 1;
	// 每页大小
	private int pageSize = 15;
	// 总的记录数
	private int totalSize = 0;
	// 页数
	private int pageCount = 0;

	public PageInfo() {
	}

	public PageInfo(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public PageInfo(String orderField, int pageNo, int pageSize) {
		this.orderField = orderField;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public String getOrderField() {
		return this.orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public List getDataList() {
		return this.dataList == null ? new ArrayList() : this.dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public int getPageNo() {
		return this.pageNo <= 0 ? 1 : this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return this.pageSize <= 0 ? DEFAULT_PAGE_SIZE : this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalSize() {
		return this.totalSize <= 0 ? 0 : this.totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getPageCount() {
		if (this.pageSize <= 0) {
			this.pageSize = 10;
		}
		this.pageCount = (this.totalSize / this.pageSize);
		this.pageCount += (this.totalSize == this.pageCount * this.pageSize ? 0
				: 1);
		return this.pageCount <= 0 ? 0 : this.pageCount;
	}
}

