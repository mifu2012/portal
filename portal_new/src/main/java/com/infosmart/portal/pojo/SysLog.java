package com.infosmart.portal.pojo;

import java.util.Date;

public class SysLog {
	/**
	 * id
	 */
	private String id;
	/**
	 * 用户ID
	 */
	private String user_id;
	/**
	 * 用户编码
	 */
	private String user_code;
	/**
	 * 用户名
	 */
	private String user_name;
	/**
	 * 操作内容
	 */
	private String operator_content;
	/**
	 * 操作时间
	 */
	private Date operator_time;
	/**
	 * 用户IP
	 */
	private String user_ip;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_code() {
		return user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getOperator_content() {
		return operator_content;
	}

	public void setOperator_content(String operator_content) {
		this.operator_content = operator_content;
	}

	public Date getOperator_time() {
		return operator_time;
	}

	public void setOperator_time(Date operator_time) {
		this.operator_time = operator_time;
	}

	public String getUser_ip() {
		return user_ip;
	}

	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}

}
