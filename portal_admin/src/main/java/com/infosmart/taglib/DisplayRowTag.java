package com.infosmart.taglib;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.BeanUtils;

/**
 * �б�ǩ
 * 
 * @author infosmart
 * 
 */
public class DisplayRowTag extends TagSupport {
	private String onClick = "javascript:clickcheck(this);";

	private String onDblClick = "";

	private String onMouseOver = "javascript:onMouseOver(this);";

	private String onMouseOut = "javascript:onMouseOut(this);";

	private String headStyleClass = "";

	private String bodyStyleClass = "";

	public String getOnMouseOver() {
		return this.onMouseOver;
	}

	public String getHeadStyleClass() {
		return this.headStyleClass;
	}

	public String getOnClick() {
		return this.onClick;
	}

	public String getBodyStyleClass() {
		return this.bodyStyleClass;
	}

	public String getOnMouseOut() {
		return this.onMouseOut;
	}

	public String getOnDblClick() {
		return this.onDblClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public void setBodyStyleClass(String bodyStyleClass) {
		this.bodyStyleClass = bodyStyleClass;
	}

	public void setHeadStyleClass(String headStyleClass) {
		this.headStyleClass = headStyleClass;
	}

	public void setOnMouseOut(String onMouseOut) {
		this.onMouseOut = onMouseOut;
	}

	public void setOnMouseOver(String onMouseOver) {
		this.onMouseOver = onMouseOver;
	}

	public void setOnDblClick(String onDblClick) {
		this.onDblClick = onDblClick;
	}

	public void release() {
		this.bodyStyleClass = "";
		this.headStyleClass = "";
		this.onClick = "";
		this.onDblClick = "";
		this.onMouseOut = "";
		this.onMouseOver = "";
	}

	public int doStartTag() throws JspException {
		super.doStartTag();
		return 1;
	}

	public int doEndTag() throws JspTagException {
		Tag tag = findAncestorWithClass(this, DisplayTableTag.class);
		try {
			DisplayTableTag displayTableTag = (DisplayTableTag) tag;
			DisplayRowInfo displayRowInfo = new DisplayRowInfo();
			BeanUtils.copyProperties(displayRowInfo, this);
			displayTableTag.setDisplayRowInfo(displayRowInfo);
		} catch (InvocationTargetException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 6;
	}
}
