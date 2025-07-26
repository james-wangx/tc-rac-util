package com.codicefun.tc.rac.util;

public class NumberUtil {

	private static final String[] chineseDigits = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };

	public static String numberToChinese(int number) {
		if (number < 0 || number > 99) {
			throw new IllegalArgumentException("Only supports 0~99");
		}

		if (number < 10) {
			return chineseDigits[number];
		} else if (number < 20) {
			if (number == 10)
				return "十";
			return "十" + chineseDigits[number % 10];
		} else {
			int tens = number / 10;
			int ones = number % 10;
			return chineseDigits[tens] + "十" + (ones > 0 ? chineseDigits[ones] : "");
		}
	}

}
