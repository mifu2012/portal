package com.infosmart.portal.util;

import com.opensymphony.xwork.util.OgnlValueStack;

public class OgnlValueStackUtils {
	private static final OgnlValueStack stack = new OgnlValueStack();

	public static Object getValue(Object obj, String property) {
		Object result = null;
		stack.push(obj);
		result = stack.findValue(property);
		stack.pop();
		return result;
	}
}