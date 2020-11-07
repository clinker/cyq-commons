/**
 * Copyright (C) 2009-2010 Yichuan, Fuchun All rights reserved.
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * @(#) IdcardUtils.java Date: 2010-06-17
 */
package com.github.clinker.commons.util.idcard;

import java.util.HashMap;
import java.util.Map;

/**
 * 身份证工具类
 *
 * @author June
 * @version 1.0, 2010-06-17
 */
public class IdCardUtils {

	/** 中国公民身份证号码长度 */
	private static final int ID_LENGTH = 18;

	/** 每位加权因子 */
	private static final int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	private static Map<String, String> cityCodes = new HashMap<>();

	static {
		cityCodes.put("11", "北京");
		cityCodes.put("12", "天津");
		cityCodes.put("13", "河北");
		cityCodes.put("14", "山西");
		cityCodes.put("15", "内蒙古");
		cityCodes.put("21", "辽宁");
		cityCodes.put("22", "吉林");
		cityCodes.put("23", "黑龙江");
		cityCodes.put("31", "上海");
		cityCodes.put("32", "江苏");
		cityCodes.put("33", "浙江");
		cityCodes.put("34", "安徽");
		cityCodes.put("35", "福建");
		cityCodes.put("36", "江西");
		cityCodes.put("37", "山东");
		cityCodes.put("41", "河南");
		cityCodes.put("42", "湖北");
		cityCodes.put("43", "湖南");
		cityCodes.put("44", "广东");
		cityCodes.put("45", "广西");
		cityCodes.put("46", "海南");
		cityCodes.put("50", "重庆");
		cityCodes.put("51", "四川");
		cityCodes.put("52", "贵州");
		cityCodes.put("53", "云南");
		cityCodes.put("54", "西藏");
		cityCodes.put("61", "陕西");
		cityCodes.put("62", "甘肃");
		cityCodes.put("63", "青海");
		cityCodes.put("64", "宁夏");
		cityCodes.put("65", "新疆");
		cityCodes.put("71", "台湾");
		cityCodes.put("81", "香港");
		cityCodes.put("82", "澳门");
		cityCodes.put("91", "国外");
	}

	/**
	 * 将power和值与11取模获得余数进行校验码判断
	 *
	 * @param iSum
	 * @return 校验位
	 */
	private static String checkCode(final int iSum) {
		String sCode = "";
		switch (iSum % 11) {
		case 10:
			sCode = "2";
			break;
		case 9:
			sCode = "3";
			break;
		case 8:
			sCode = "4";
			break;
		case 7:
			sCode = "5";
			break;
		case 6:
			sCode = "6";
			break;
		case 5:
			sCode = "7";
			break;
		case 4:
			sCode = "8";
			break;
		case 3:
			sCode = "9";
			break;
		case 2:
			sCode = "x";
			break;
		case 1:
			sCode = "0";
			break;
		case 0:
			sCode = "1";
			break;
		}
		return sCode;
	}

	/**
	 * 将字符数组转换成数字数组
	 *
	 * @param ca 字符数组
	 * @return 数字数组
	 */
	private static int[] converCharToInt(final char[] ca) {
		final int len = ca.length;
		final int[] array = new int[len];
		try {
			for (int i = 0; i < len; i++) {
				array[i] = Integer.parseInt(String.valueOf(ca[i]));
			}
		} catch (final NumberFormatException e) {
			e.printStackTrace();
		}
		return array;
	}

	/**
	 * 据身份编号获取生日，格式：yyyy-MM-dd。
	 *
	 * @param idCard 身份编号
	 * @return 生日，例如：2099-01-02
	 */
	public static String getBirthday(final String idCard) {
		if (idCard == null | idCard.isBlank() || idCard.length() < ID_LENGTH) {
			return null;
		}

		final String ymd = idCard.substring(6, 14);
		return String.format("%s-%s-%s", ymd.substring(0, 4), ymd.substring(4, 6), ymd.substring(6, 8));
	}

	/**
	 * 根据身份编号获取性别
	 *
	 * @param idCard 身份编号
	 * @return 性别(M-男，F-女，N-未知)
	 */
	public static String getGender(final String idCard) {
		String sGender = "N";
		final String sCardNum = idCard.substring(16, 17);
		if (Integer.parseInt(sCardNum) % 2 != 0) {
			sGender = "M";
		} else {
			sGender = "F";
		}
		return sGender;
	}

	/**
	 * 将身份证的每位和对应位的加权因子相乘之后，再得到和值。
	 *
	 * @param array
	 * @return 身份证编码
	 */
	private static int getPowerSum(final int[] array) {
		int iSum = 0;
		if (power.length == array.length) {
			for (int i = 0; i < array.length; i++) {
				for (int j = 0; j < power.length; j++) {
					if (i == j) {
						iSum = iSum + array[i] * power[j];
					}
				}
			}
		}
		return iSum;
	}

	/**
	 * 数字验证
	 *
	 * @param val
	 * @return 提取的数字。
	 */
	private static boolean isNum(final String val) {
		return val == null || "".equals(val) ? false : val.matches("^[0-9]*$");
	}

	/**
	 * 验证18位身份编码是否合法。
	 *
	 * @param idCard 身份编码
	 * @return 是否合法
	 */
	public static boolean validate(final String idCard) {
		if (idCard == null || idCard.length() != ID_LENGTH) {
			return false;
		}
		boolean result = false;
		// 前17位
		final String code17 = idCard.substring(0, 17);
		// 第18位
		final String code18 = idCard.substring(17, ID_LENGTH);
		if (isNum(code17)) {
			final char[] cArr = code17.toCharArray();
			if (cArr != null) {
				final int[] iCard = converCharToInt(cArr);
				final int iSum17 = getPowerSum(iCard);
				// 获取校验位
				final String val = checkCode(iSum17);
				if (val.length() > 0) {
					if (val.equalsIgnoreCase(code18)) {
						result = true;
					}
				}
			}
		}
		return result;
	}

	private IdCardUtils() {

	}

}