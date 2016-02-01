package com.infosmart.util;

import bsh.Interpreter;

public class InterpreterUtil {
	private static Interpreter interpreter = null;

	public InterpreterUtil() {
		if (interpreter == null)
			interpreter = new Interpreter();
	}

	public static Object eval(String evalString) {
		Object returnObj = null;
		try {
			if (interpreter == null) {
				interpreter = new Interpreter();
			}
			returnObj = interpreter.eval(evalString);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnObj;
	}
}