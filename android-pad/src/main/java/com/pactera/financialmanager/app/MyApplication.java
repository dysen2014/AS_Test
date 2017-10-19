package com.pactera.financialmanager.app;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.Calendar;

public class MyApplication extends Application{


    private static SharedPreferences sPreference;
    private static final long MIN_SAVE_TIME = 1000;
    private static final String PREF_KEY_LAST_ACTIVE = "last_active";

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onCreate() {
        super.onCreate();

        /*6.26 暂时注释这三行 显示 Caused by*/
        // 日志错误记录
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
//
//        CrashReport.initCrashReport(getApplicationContext(), "549a50f7ab", false);

        // 地图服务注册
        SDKInitializer.initialize(getApplicationContext());
        initImageLoader(getApplicationContext());


        //初始化定时器
        sPreference = getSharedPreferences(PREF_KEY_LAST_ACTIVE, MODE_PRIVATE);
    }

    /**
     * 初始化ImageLoader
     * 版本1.9.5
     *
     * @param context
     */

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
//        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }


    /**
     * 初始化ImageLoader
     */
   /* public static void initImageLoader(Context context) {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true) // 1.8.6包使用时候，括号里面传入参数true
                .cacheOnDisc(true) // 同上
                .build();

        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "imageloader/Cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
                .build();
        ImageLoader.getInstance().init(config);
    }*/
    public static void initTimeStamp() {
        sPreference.edit().putLong(PREF_KEY_LAST_ACTIVE, timeNow()).commit();
    }
  //01030
    public static void saveTimeStamp() {
        if (getElapsedTime() > MIN_SAVE_TIME) {
            sPreference.edit().putLong(PREF_KEY_LAST_ACTIVE, timeNow()).commit();
        }
    }

    public static long getElapsedTime() {
        return timeNow() - sPreference.getLong(PREF_KEY_LAST_ACTIVE, 0);
    }

    private static long timeNow() {
        return Calendar.getInstance().getTimeInMillis();
    }
}
