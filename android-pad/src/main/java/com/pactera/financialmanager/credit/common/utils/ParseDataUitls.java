package com.pactera.financialmanager.credit.common.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by dysen on 11/7/2017.
 */

public class ParseDataUitls {

    public static <T> T parseHeaderList(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            return gson.fromJson(jsonData, new TypeToken<List<T>>() {}.getType());
        } else
            return null;
    }
}
