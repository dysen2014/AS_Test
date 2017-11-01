package com.dysen.common_res.common.utils;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dysen on 11/1/2017.
 */

public class ActivityManagerApplication extends Application {

    private static List<Activity> activitiyList = new LinkedList<Activity>();

    public ActivityManagerApplication(){

    }

    /**
     * 取得Acitity 并添加到List中
     * */
    public static void addActivity(Activity activity){
        activitiyList.add(activity);
    }

    /**
     * 遍历并finish掉所有List中的Activity
     * */
    public static void finishActivity() {
        for (Activity activity : activitiyList) {
            activity.finish();
        }
        /**
         * 判空
         * */
        if (activitiyList.size() == 0){
            activitiyList.clear();
        }
    }

}
