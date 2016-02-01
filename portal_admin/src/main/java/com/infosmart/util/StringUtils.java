package com.infosmart.util;

import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	public static boolean notNullAndSpace(String str) {
		if ((str != null) && (!"".equals(str.trim()))
				&& (!str.equalsIgnoreCase("null"))) {
			if (str.length() == 0)
				return false;
			if (str.trim().equalsIgnoreCase("null")) {
				return false;
			}
			return str.trim().length() != 0;
		}

		return false;
	}

	public static String substringBeforeLastIndexOf(String sourceStr,
			String indexStr) {
		if ((sourceStr != null) && (sourceStr.lastIndexOf(indexStr) > -1)) {
			return sourceStr.substring(0, sourceStr.lastIndexOf(indexStr));
		}
		return null;
	}

	public static String substringAfterLastIndexOf(String sourceStr,
			String indexStr) {
		if ((sourceStr != null) && (sourceStr.lastIndexOf(indexStr) > -1)) {
			return sourceStr.substring(sourceStr.lastIndexOf(indexStr) + 1);
		}
		return null;
	}

	public static String replace(String value, String key, String replaceValue) {
		if ((value != null) && (key != null) && (replaceValue != null)) {
			int pos = value.indexOf(key);
			if (pos >= 0) {
				int length = value.length();
				int start = pos;
				int end = pos + key.length();
				if (length == key.length())
					value = replaceValue;
				else if (end == length)
					value = value.substring(0, start) + replaceValue;
				else {
					value = value.substring(0, start) + replaceValue
							+ replace(value.substring(end), key, replaceValue);
				}
			}
		}
		return value;
	}

	/**
	 * java实现不区分大小写替换
	 * 
	 * @param source
	 * @param oldstring
	 * @param newstring
	 * @return
	 */
	public static String IgnoreCaseReplace(String source, String oldstring,
			String newstring) {
		//System.out.println("------------>java实现不区分大小写替换");
		try {
			String newSource = java.net.URLEncoder.encode(source, "UTF-8");
			String newOldstring = java.net.URLEncoder.encode(oldstring, "UTF-8");
			String newNewstring = java.net.URLEncoder.encode(newstring, "UTF-8");

			Pattern p = Pattern.compile(newOldstring, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(newSource);
			String ret = m.replaceAll(newNewstring);
			return java.net.URLDecoder.decode(ret, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return source;
		}
	}

	public static String replaceAll(String strSource, String strFrom,
			String strTo) {
		if (strSource == null) {
			return null;
		}
		int i = 0;
		if ((i = strSource.indexOf(strFrom, i)) >= 0) {
			char[] cSrc = strSource.toCharArray();
			char[] cTo = strTo.toCharArray();
			int len = strFrom.length();
			StringBuffer buf = new StringBuffer(cSrc.length);
			buf.append(cSrc, 0, i).append(cTo);
			i += len;
			int j = i;
			while ((i = strSource.indexOf(strFrom, i)) > 0) {
				buf.append(cSrc, j, i - j).append(cTo);
				i += len;
				j = i;
			}
			buf.append(cSrc, j, cSrc.length - j);
			return buf.toString();
		}
		return strSource;
	}

	public static String findReplace(String aSrc, String aSub,
			String aReplaceWith) {
		String dest = aSrc;
		int fromIndex = 0;
		while ((fromIndex = dest.indexOf(aSub, fromIndex)) != -1) {
			dest = dest.substring(0, fromIndex) + aReplaceWith
					+ dest.substring(fromIndex + aSub.length());
			fromIndex += aReplaceWith.length();
		}
		return dest;
	}

	public static int getFieldCount(String aSrc, String aDelim) {
		if ((aSrc == null) || (aSrc.equals(""))) {
			return 0;
		}

		int fromIndex = 0;
		int fieldCount = 1;
		while ((fromIndex = aSrc.indexOf(aDelim, fromIndex)) != -1) {
			fromIndex++;
			fieldCount++;
		}
		return fieldCount;
	}

	public static String getField(String aSrc, String aDelim, long aFieldNum) {
		if ((aSrc == null) || (aFieldNum < 0L)) {
			return null;
		}

		int beginIndex = 0;
		int endIndex = 0;
		int fieldNum = 0;

		if ((aSrc.indexOf(aDelim) == -1) && (aFieldNum == 0L)) {
			return aSrc;
		}

		if (aFieldNum >= getFieldCount(aSrc, aDelim)) {
			return "";
		}

		while (aSrc.indexOf(aDelim, endIndex) != -1) {
			endIndex = aSrc.indexOf(aDelim, endIndex);
			if (fieldNum == aFieldNum) {
				break;
			}
			endIndex += aDelim.length();
			beginIndex = endIndex;
			fieldNum++;
		}

		if ((beginIndex == endIndex) && (aSrc.indexOf(aDelim, endIndex) == -1)) {
			endIndex = aSrc.length();
		}

		return aSrc.substring(beginIndex, endIndex);
	}

	public static long indexOf(String aSrc, String aDelim, String aField) {
		long fieldCount = getFieldCount(aSrc, aDelim);
		for (int i = 0; i < fieldCount; i++) {
			String field = getField(aSrc, aDelim, i);
			if (aField.equalsIgnoreCase(field)) {
				return i;
			}
		}
		return -1L;
	}

	public static String[] Split(String as_Value, String as_SubStr)
			throws Exception {
		String[] ls_Out = (String[]) null;
		StringTokenizer lo_ST = null;
		Vector lo_Out = new Vector();
		boolean lb_Return = false;
		int li_Count = 0;
		int li_Start = 0;
		int i = 0;
		try {
			if ((as_Value != null) && (!as_Value.equals(""))
					&& (as_SubStr != null) && (!as_SubStr.equals("")))
				lb_Return = true;
			while (lb_Return) {
				li_Count = as_Value.indexOf(as_SubStr, li_Start);
				if (li_Count == -1) {
					li_Count = as_Value.length();
					lb_Return = false;
				}
				lo_Out.add(as_Value.substring(li_Start, li_Count));
				li_Start = li_Count + as_SubStr.length();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("CommonUtil:Split Error!\n" + e.getMessage());
		}
		if (lo_Out.toArray().length > 0) {
			Object[] lo_Temp = lo_Out.toArray();
			ls_Out = new String[lo_Temp.length];
			for (i = 0; i < lo_Temp.length; i++) {
				ls_Out[i] = ((String) lo_Temp[i]);
			}
		}
		return ls_Out;
	}

	public static String changeFirstCharToLowerCase(String str) {
		String result = null;
		if (notNullAndSpace(str)) {
			char[] chars = str.toCharArray();
			if ((chars[0] > '@') && (chars[0] < '[')) {
				chars[0] = (char) (chars[0] + ' ');
			}
			result = new String(chars);
		}
		return result;
	}

	/**
	 * 截取时间
	 * 
	 * @param str
	 * @param start
	 * @return
	 */
	public static String substring(String str, int start) {
		if (str == null) {
			return null;
		}

		if (start < 0) {
			start = str.length() + start;
		}

		if (start < 0) {
			start = 0;
		}

		if (start > str.length()) {
			return "";
		}

		return str.substring(start);
	}

	/**
	 * 截取时间
	 * 
	 * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	public static String substring(String str, int start, int end) {
		if (str == null) {
			return null;
		}

		if (end < 0) {
			end = str.length() + end;
		}

		if (start < 0) {
			start = str.length() + start;
		}

		if (end > str.length()) {
			end = str.length();
		}

		if (start > end) {
			return "";
		}

		if (start < 0) {
			start = 0;
		}

		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

}
