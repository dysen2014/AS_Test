package com.pactera.financialmanager.ui.credit;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by xh on 2016-01-13.
 */
public class InputCheckUtils {

    public static boolean checkZipCode(String zipCode) {
        if (TextUtils.isEmpty(zipCode)) return true;
        if (zipCode.length() != 6) return false;

        try {
            Integer.valueOf(zipCode);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean checkAge(String age) {
        if (TextUtils.isEmpty(age)) return true;
        try {
            int a = Integer.valueOf(age);
            if (a > 0 && a < 200) return true;
        } catch (Exception e) {
        }
        return false;
    }


    private static final Pattern PATTERN_EMAIL = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9]+(-[a-zA-Z0-9]+)?\\.)+[a-zA-Z]+$");
    public static boolean checkEmail(String email) {
        if (TextUtils.isEmpty(email)) return true;
        return PATTERN_EMAIL.matcher(email).matches();
    }

    private static final Pattern PATTERN_MOBILE = Pattern.compile("^1[0-9]{10}$");
    public static boolean checkMobilePhoneNumber(String s) {
        if (TextUtils.isEmpty(s)) return true;
        return PATTERN_MOBILE.matcher(s).matches();
    }

    private static final Pattern PATTERN_PHONE = Pattern.compile("^[0-9]+\\-[0-9]+$");
    public static boolean checkHomePhoneNumber(String s) {
        if (TextUtils.isEmpty(s)) return true;
        return PATTERN_PHONE.matcher(s).matches();
    }
}
