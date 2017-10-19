package com.pactera.financialmanager.util;

import android.text.TextUtils;

/**
 * Created by chen on 16/7/21.
 */
public class DateUtil {

    /**
     * 格式化数字，如果位数小于2，前面填充0
     *
     * @param num
     * @return
     */
    public static String formatNum(String num) {
        if (!TextUtils.isEmpty(num) && num.length() < 2) {
            num = "0" + num;
        }
        return num;
    }
}
