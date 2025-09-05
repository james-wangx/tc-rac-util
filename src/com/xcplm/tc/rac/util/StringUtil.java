package com.xcplm.tc.rac.util;

public class StringUtil {

	public static boolean isNullOrEmpty(String[] strs) {
		return (strs == null) || (strs.length == 0);
	}

	public static boolean isNullOrEmpty(String str) {
		return (str == null) || (str.trim().isEmpty());
	}

	public static String getBeforeChar(String str, char ch) {
		int index = str.indexOf(ch);
		if (index != -1) {
			return str.substring(0, index);
		} else {
			return str;
		}
	}

}
