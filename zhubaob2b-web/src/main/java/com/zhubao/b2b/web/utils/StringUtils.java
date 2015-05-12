package com.zhubao.b2b.web.utils;

public class StringUtils extends org.apache.commons.lang.StringUtils {

	public static boolean equalsn(String str_1, String str_2, int length) {
		if (length < 0) {
			new IllegalArgumentException("length could not less than 0");
		}

		if (str_1 == null || str_1.length() < length || str_2 == null
				|| str_2.length() < length) {
			return false;
		}

		return str_1.substring(0, length).equals(str_2.substring(0, length));
	}

	public static boolean equalsnIgnoreCase(String str_1, String str_2,
			int length) {
		if (length < 0) {
			new IllegalArgumentException("length could not less than 0");
		}

		if (str_1 == null || str_1.length() < length || str_2 == null
				|| str_2.length() < length) {
			return false;
		}

		return str_1.substring(0, length).equalsIgnoreCase(
				str_2.substring(0, length));
	}

}