package com.pactera.financialmanager.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.dysen.common_res.common.utils.LogUtils;
import com.lljjcoder.style.citylist.utils.CityListLoader;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends MultiDexApplication{


    private static SharedPreferences sPreference;
    private static final long MIN_SAVE_TIME = 1000;
    private static final String PREF_KEY_LAST_ACTIVE = "last_active";
    public static int stateCount;

    /**
     * 阿里云意见反馈
     */
    public final static String DEFAULT_APPKEY = "24784235";
    public final static String DEFAULT_APPSECRET = "cdce7e72893a3206138bbc4d75a41f11 ";

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onCreate() {
        super.onCreate();

        initActivityLife();

        /*6.26 暂时注释这三行 显示 Caused by*/
        // 日志错误记录
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
//
//        CrashReport.initCrashReport(getApplicationContext(), "8cb03dba48", false);//sendy的qq
//        CrashReport.initCrashReport(getApplicationContext(), "549a50f7ab", false);

        // 地图服务注册
        SDKInitializer.initialize(this);
        initImageLoader(this);

        //初始化定时器
        sPreference = getSharedPreferences(PREF_KEY_LAST_ACTIVE, MODE_PRIVATE);

        //极光推送
        JPushInterface.setDebugMode(true);//正式版的时候设置false，关闭调试
        JPushInterface.init(this);
        //建议添加tag标签，发送消息的之后就可以指定tag标签来发送了
        Set<String> set = new HashSet<>();
        set.add("sendy");//名字任意，可多添加几个,能区别就好了
//        JPushInterface.setAlias(this, "sendy", null);
        JPushInterface.setTags(this, set, null);//设置标签

        //ocr
        initOCR();

        //picker
        initPicker();

        /**
         * 建议 SDK 初始化
         */
        FeedbackAPI.init(this, DEFAULT_APPKEY, DEFAULT_APPSECRET);
    }

    /**
     * 网点选择器
     */
    private void initPicker() {
        /**
         * 预先加载仿iOS滚轮实现的全部数据
         */
        CityPickerView.getInstance().init(this);

        /**
         * 预先加载一级列表所有城市的数据
         */
        CityListLoader.getInstance().loadCityData(this);

        /**
         * 预先加载三级列表显示省市区的数据
         */
        CityListLoader.getInstance().loadProData(this);
    }

    /**
     * OCR
     */
    private void initOCR() {
        OCR.getInstance().initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
            }

            @Override
            public void onError(OCRError ocrError) {
                ocrError.printStackTrace();
//                Toast.makeText(getApplicationContext(), "licence方式获取token失败"+ocrError.getErrorCode(), Toast
//                        .LENGTH_SHORT).show();
                LogUtils.d("licence方式获取token失败"+ocrError.getErrorCode());
            }
        }, getApplicationContext());
//        OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
//            @Override
//            public void onResult(AccessToken accessToken) {
//                String token = accessToken.getAccessToken();
//            }
//
//            @Override
//            public void onError(OCRError ocrError) {
//                ocrError.printStackTrace();
//                Toast.makeText(getApplicationContext(), "licence方式获取token失败"+ocrError.getErrorCode(), Toast.LENGTH_SHORT).show();
//            }
//        },
////                getApplicationContext(), "fj5IsMhsKIqzvt3eyo1cmiow", "zS33awUGleXCLBfUgHawl9gIQZNO65om");
//        getApplicationContext(), "dwwUeuGGD1NGb5VpbNh2gX6U", "gXGhjPXxWGbDoIUXGc5wN0USkQydMX0D");
    }

    private void initActivityLife() {
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                stateCount++;
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                CustomActivityManager.getInstance().setTopActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                stateCount--;

                CustomActivityManager.getInstance().setTopActivity(null);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public static class CustomActivityManager {

        private static CustomActivityManager customActivityManager = new CustomActivityManager();
        private WeakReference<Activity> topActivity;

        private CustomActivityManager() {

        }

        public static CustomActivityManager getInstance(){
            return customActivityManager;
        }

        public Activity getTopActivity() {
            if (topActivity!=null){
                return topActivity.get();
            }
            return null;
        }

        public void setTopActivity(Activity topActivity) {
            this.topActivity = new WeakReference<>(topActivity);
        }

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
