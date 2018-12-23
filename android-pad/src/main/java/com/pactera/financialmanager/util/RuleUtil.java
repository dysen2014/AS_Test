package com.pactera.financialmanager.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 规则判断工具类
 * 
 * @author DengGuang
 *
 */
public class RuleUtil {

	/**
	 * 判断手机号码
	 * 
	 * @param phoneNum
	 * @return
	 */
	public static boolean checkPhoneNum(Context context, String phoneNum) {
		String alert01 = "抱歉，手机号码不能为空！";
		String alert02 = "抱歉，手机号码位数不正确！";
		String alert03 = "抱歉，手机号码格式不正确！";

		if (TextUtils.isEmpty(phoneNum)) {
			toast(context, alert01);
			return false;
		}
		if (phoneNum.length() != 11) {
			toast(context, alert02);
			return false;
		}
		Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(phoneNum);
		if (!m.matches()) {
			toast(context, alert03);
			return false;
		}

		return true;
	}

	/**
	 * 判断输入值是否为空
	 * @param context
	 * @param value
	 * @param alert
     * @return
     */
	public static boolean checkInputValueIsEmpty(Context context, String value, String alert){
		if(TextUtils.isEmpty(value)){
			toast(context, alert);
			return false;
		}

		return true;
	}


	/**
	 * 判断短信验证码
	 * 
	 * @param context
	 * @param identify
	 * @return
	 */
	public static boolean checkIdentify(Context context, String identify) {
		String alert01 = "抱歉，短信验证码不能为空！";
		if (TextUtils.isEmpty(identify)) {
			toast(context, alert01);
			return false;
		}
		return true;
	}

	/**
	 * 图片验证码
	 * 
	 * @param context
	 * @param checkCode
	 * @return
	 */
	public static boolean checkImgCode(Context context, String checkCode) {
		String alert01 = "抱歉，验证码不能为空！";
		if (TextUtils.isEmpty(checkCode)) {
			toast(context, alert01);
			return false;
		}
		return true;
	}

	/**
	 * 判断输入的姓名是否为空
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static boolean checkName(Context context, String name) {
		String alert01 = "姓名输入不规范！";
		String alert02 = "抱歉，您的输入姓名不能为空！";
		// 判断是否为空
		if (TextUtils.isEmpty(name)) {
			toast(context, alert02);
			return false;
		}
		// 判断姓名输入是否合法，包括汉字和 ·
		String regex = "^[\u4e00-\u9fa5|·.]{2,15}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(name);
		if (!m.matches()) {
			toast(context, alert01);
			return false;
		}
		return true;
	}

	/**
	 * 校验身份证号码
	 * 
	 * @param context
	 * @param idNumber
	 */
	@SuppressLint("SimpleDateFormat")
	public static boolean checkIDNumber(Context context, String idNumber) {
		String alert01 = "身份证号码长度应该为15位或18位！";
		String alert02 = "身份证号码格式错误！";
		String alert03 = "抱歉，身份证号码不能为空！";

		// 身份证号码不能为空
		if (TextUtils.isEmpty(idNumber)) {
			toast(context, alert03);
			return false;
		}

		// 判断号码的长度 15位或18位
		if (!(idNumber.length() == 15 || idNumber.length() == 18)) {
			toast(context, alert01);
			return false;
		}
		// 将身份证的x替换成X
		if (idNumber.length() != 0 && idNumber.contains("x")) {
			idNumber = idNumber.replace("x", "X");
		}
		// 18位身份证前17位位数字，如果是15位的身份证则所有号码都为数字
		String ai = "";
		if (idNumber.length() == 18) {
			ai = idNumber.substring(0, 17);
		} else if (idNumber.length() == 15) {
			ai = idNumber.substring(0, 6) + "19" + idNumber.substring(6, 15);
		}
		if (isNumeric(ai) == false) {
			toast(context, alert02);
			return false;
		}

		// 判断出生年月是否有效
		String strYear = ai.substring(6, 10);// 年份
		String strMonth = ai.substring(10, 12);// 月份
		String strDay = ai.substring(12, 14);// 日期
		if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
			toast(context, alert02);
			return false;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				toast(context, alert02);
				return false;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			toast(context, alert02);
			return false;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			toast(context, alert02);
			return false;
		}

		// 判断地区码是否有效
		Hashtable<String, String> areacode = GetAreaCode();
		// 如果身份证前两位的地区码不在Hashtable，则地区码有误
		if (areacode.get(ai.substring(0, 2)) == null) {
			toast(context, alert02);
			return false;
		}

		// 身份证校验码无效，不是合法的身份证号码
		if (isVarifyCode(ai, idNumber) == false) {
			toast(context, alert02);
			return false;
		}
		return true;
	}

	/*
	 * 判断第18位校验码是否正确 第18位校验码的计算方式： 1. 对前17位数字本体码加权求和 公式为：S = Sum(Ai * Wi), i =
	 * 0, ... , 16 其中Ai表示第i个位置上的身份证号码数字值，Wi表示第i位置上的加权因子，其各位对应的值依次为： 7 9 10 5 8 4
	 * 2 1 6 3 7 9 10 5 8 4 2 2. 用11对计算结果取模 Y = mod(S, 11) 3. 根据模的值得到对应的校验码
	 * 对应关系为： Y值： 0 1 2 3 4 5 6 7 8 9 10 校验码： 1 0 X 9 8 7 6 5 4 3 2
	 */
	private static boolean isVarifyCode(String Ai, String IDStr) {
		String[] VarifyCode = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
		int sum = 0;
		for (int i = 0; i < 17; i++) {
			sum = sum + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
		}
		int modValue = sum % 11;
		String strVerifyCode = VarifyCode[modValue];
		Ai = Ai + strVerifyCode;
		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 将所有地址编码保存在一个Hashtable中
	 * 
	 * @return Hashtable 对象
	 */
	private static Hashtable<String, String> GetAreaCode() {
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	/**
	 * 功能：判断字符串出生日期是否符合正则表达式：包括年月日，闰年、平年和每月31天、30天和闰月的28天或者29天
	 * 
	 * @param strDate
	 * @return
	 */
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern.compile(
				"^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断字符串是否为数字,0-9重复0次或者多次
	 * 
	 * @param strnum
	 * @return
	 */
	public static boolean isNumeric(String strnum) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(strnum);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 校验银行卡号
	 * 
	 * @param context
	 * @param bankNo
	 * @return
	 */
	public static boolean checkBankNo(Context context, String bankNo) {
		String alert01 = "抱歉，银行卡号不能为空！";
		String alert02 = "抱歉，银行卡号位数不正确！";

		if (TextUtils.isEmpty(bankNo)) {
			toast(context, alert01);
			return false;
		}
		if (!(bankNo.length() == 16 || bankNo.length() == 19)) {
			toast(context, alert02);
			return false;
		}
		return true;
	}

	/**
	 * 校验有效日期
	 * 
	 * @param context
	 * @param exDate
	 * @return
	 */
	public static boolean checkCreditExdate(Context context, String exDate) {
		String alert01 = "抱歉，信用卡有效日期不能为空！";
		String alert02 = "抱歉，有效日期格式不正确.";
		if (TextUtils.isEmpty(exDate)) {
			toast(context, alert01);
			return false;
		}
		// 月份校验
		String strMonth = exDate.substring(0, 2);
		int m = Integer.parseInt(strMonth);
		if (m > 12) {
			toast(context, alert02);
			return false;
		}
		return true;
	}

	/**
	 * 判断微信格式
	 * 
	 * @param
	 * @return
	 */
	public static boolean checkWechat(String wechat) {
		String check = "^[a-zA-Z][a-zA-Z0-9]{4,18}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(wechat);
		return matcher.matches();
	}

	/**
	 * 验证邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(email);
		return matcher.matches();
	}

	/**
	 * Toast提示
	 * 
	 * @param context
	 * @param alert
	 */
	public static void toast(Context context, String alert) {
		Toast.makeText(context, alert, Toast.LENGTH_SHORT).show();
	}

}
