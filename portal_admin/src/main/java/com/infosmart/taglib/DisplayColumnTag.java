package com.infosmart.taglib;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.BeanUtils;
/**
 * �б�ǩ
 * @author infosmart
 *
 */
public class DisplayColumnTag extends TagSupport
{
  private String property;
  private String align;
  private String title;
  private String pattern;
  private String format;
  private String expression;
  private String width;
  private String columnStyleClass;
  private boolean canOrder = false;
  private String orderProperty;

  public String getExpression()
  {
    return this.expression;
  }

  public void setExpression(String expression) {
    this.expression = expression;
  }

  public String getFormat() {
    return this.format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public String getOrderProperty() {
    return this.orderProperty;
  }

  public void setOrderProperty(String orderProperty) {
    this.orderProperty = orderProperty;
  }

  public String getPattern() {
    return this.pattern;
  }

  public String getColumnStyleClass() {
    return this.columnStyleClass;
  }

  public String getProperty() {
    return this.property;
  }

  public String getAlign() {
    return this.align;
  }

  public String getTitle() {
    return this.title;
  }

  public boolean isCanOrder() {
    return this.canOrder;
  }

  public String getWidth() {
    return this.width;
  }

  public void setProperty(String property) {
    this.property = property;
  }

  public void setColumnStyleClass(String columnStyleClass) {
    this.columnStyleClass = columnStyleClass;
  }

  public void setWidth(String width) {
    this.width = width;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setAlign(String align) {
    this.align = align;
  }

  public void setCanOrder(boolean canOrder) {
    this.canOrder = canOrder;
  }

  public DisplayColumnTag() {
    this.property = "";
    this.align = "";
    this.columnStyleClass = "";
    this.pattern = "";
    this.title = "";
    this.width = "";
  }

  public void release() {
    this.property = "";
    this.align = "";
    this.columnStyleClass = "";
    this.pattern = "";
    this.title = "";
    this.width = "";
  }

  public int doEndTag() throws JspTagException {
    Tag tag = findAncestorWithClass(this, DisplayTableTag.class);
    try {
      DisplayTableTag displayTableTag = (DisplayTableTag)tag;
      DisplayColumnInfo displayColumnInfo = new DisplayColumnInfo();
      BeanUtils.copyProperties(displayColumnInfo, this);
      displayTableTag.setDisplayColumnInfo(displayColumnInfo);
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
