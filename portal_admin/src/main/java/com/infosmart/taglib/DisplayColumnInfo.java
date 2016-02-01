package com.infosmart.taglib;

public class DisplayColumnInfo
{
  private String property;
  private String align;
  private String title;
  private String pattern;
  private String format;
  private String expression;
  private String width;
  private boolean canOrder = false;
  private String columnStyleClass;
  private String orderProperty;
  private int columnIndex = 1;

  public String getExpression() {
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

  public String getAlign() {
    return this.align;
  }

  public void setAlign(String align) {
    this.align = align;
  }

  public boolean isCanOrder() {
    return this.canOrder;
  }

  public void setCanOrder(boolean canOrder) {
    this.canOrder = canOrder;
  }

  public int getColumnIndex() {
    return this.columnIndex;
  }

  public void setColumnIndex(int columnIndex) {
    this.columnIndex = columnIndex;
  }

  public String getColumnStyleClass() {
    return this.columnStyleClass;
  }

  public void setColumnStyleClass(String columnStyleClass) {
    this.columnStyleClass = columnStyleClass;
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

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public String getProperty() {
    return this.property;
  }

  public void setProperty(String property) {
    this.property = property;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getWidth() {
    return this.width;
  }

  public void setWidth(String width) {
    this.width = width;
  }
}