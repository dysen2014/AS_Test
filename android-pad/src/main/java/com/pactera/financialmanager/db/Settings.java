package com.pactera.financialmanager.db;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by xh on 2015/12/1.
 */
public class Settings {

    private static final String SETTINGS = "SETTINGS";

    private static final String ID_DEVICE = "ID_DEVICE";

    private static final String CREDIT_PARAM = "CREDIT_PARAM";

    public static void saveIdCardDeviceMac(Context context, String mac) {
        SharedPreferences settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(ID_DEVICE, mac);
        editor.commit();
    }


    public static String loadIdCardDeviceMac(Context context) {
        SharedPreferences settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return settings.getString(ID_DEVICE, null);
    }

    public static void saveCreditParam(Context context, String content) {
        SharedPreferences settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(CREDIT_PARAM, content);
        editor.commit();
    }

    public static String loadCreditParam(Context context) {
        SharedPreferences settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        return settings.getString(CREDIT_PARAM, null);
    }
}
