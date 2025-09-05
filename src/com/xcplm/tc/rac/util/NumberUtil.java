package com.xcplm.tc.rac.util;

public class NumberUtil {

	private static final String[] chineseDigits = { "��", "һ", "��", "��", "��", "��", "��", "��", "��", "��" };

	public static String numberToChinese(int number) {
		if (number < 0 || number > 99) {
			throw new IllegalArgumentException("Only supports 0~99");
		}

		if (number < 10) {
			return chineseDigits[number];
		} else if (number < 20) {
			if (number == 10)
				return "ʮ";
			return "ʮ" + chineseDigits[number % 10];
		} else {
			int tens = number / 10;
			int ones = number % 10;
			return chineseDigits[tens] + "ʮ" + (ones > 0 ? chineseDigits[ones] : "");
		}
	}

}
