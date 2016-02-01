package com.infosmart.portal.util;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

public class NumberFormatter {

	public static Map<String, DecimalFormat> hm = new HashMap<String, DecimalFormat>();

	private static String doFormat(DecimalFormat decimalFormat, String value) {

		return decimalFormat.format(Double.parseDouble(value));
	}

	public static String format_p(Object value) {
		if (value == null || !StringUtils.hasText(value.toString())) {
			return "-";
		}
		return NumberFormatter.format(value,
				NumberFormatter.getPrecision(value))
				+ "%";
	}

	public static String format_p(Object value, int precision) {
		if (value == null || !StringUtils.hasText(value.toString())) {
			return "-";
		}
		return NumberFormatter.format(value, precision) + "%";
	}

	public static String format(Object value) {
		if (value == null || !StringUtils.hasText(value.toString())) {
			return "-";
		}
		return NumberFormatter.format(value,
				NumberFormatter.getPrecision(value));
	}

	public static String format(Object value, int precision) {
		if (value == null || !StringUtils.hasText(value.toString())) {
			return "-";
		}
		return NumberFormatter.format(String.valueOf(value), precision);
	}

	private static String format(String value, int percision) {

		try {
			if (NumberFormatter.hm.get(String.valueOf(percision)) == null) {
				if (percision == 0) {
					StringBuffer sbEx = new StringBuffer("##,###,###,###,##0");
					DecimalFormat formatter = new DecimalFormat(sbEx.toString());
					NumberFormatter.hm.put(String.valueOf(percision),
							formatter);

				} else {
					if (NumberFormatter.hm.get(percision) == null) {
						StringBuffer sbEx = new StringBuffer(
								"##,###,###,###,##0.");
						for (int i = 0; i < percision; i++) {
							sbEx.append("0");
						}
						DecimalFormat formatter = new DecimalFormat(
								sbEx.toString());
						NumberFormatter.hm.put(String.valueOf(percision),
								formatter);
					}

				}
			}
			return NumberFormatter.doFormat(
					NumberFormatter.hm.get(String.valueOf(percision)), value);
		} catch (Exception ex) {
			return value;
		}
	}

	private static int getPrecision(Object valueOf) {

		int indexOf = valueOf.toString().indexOf(".");
		if (indexOf == -1) {
			return 0;
		}
		int precistion = valueOf.toString().substring(indexOf + 1).length();
		return precistion;
	}

}
