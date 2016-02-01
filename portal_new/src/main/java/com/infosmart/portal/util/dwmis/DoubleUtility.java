package com.infosmart.portal.util.dwmis;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DoubleUtility {
	/**
	 * double值相减会失真，可使用以下方法保证不失真
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static double minus(double x, double y) {

		NumberFormat format = new DecimalFormat("0.0000");

		String str = format.format(x - y);

		return Double.parseDouble(str);
	}
}
