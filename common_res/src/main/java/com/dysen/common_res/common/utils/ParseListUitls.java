package com.dysen.common_res.common.utils;

import android.text.TextUtils;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dysen on 11/7/2017.
 */

public class ParseListUitls {

    /**
     * json字符串 转成实体类
     * @param <T>
     * @param jsonData
     * @return
     */
    public static <T> List<T> parseList(String jsonData, Class<T> cls) {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();
            List<T> list = new ArrayList<T>();
            JsonArray arry = new JsonParser().parse(jsonData).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(gson.fromJson(jsonElement, cls));
            }
            return list ;

        } else
            return null;
    }
}
