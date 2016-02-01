package com.infosmart.portal.vo.dwmis;

import java.util.Date;

public class LineElem {
	// 数据日期
	public Date date;

	// 数据值
	public double value;

	// 重写equals方法
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof LineElem)) {
			return false;
		}

		LineElem other = (LineElem) obj;

		// 如果日期相等这线元素就相等
		if (date.getTime() == other.date.getTime()) {
			return true;
		} else {
			return false;
		}
	}
}
