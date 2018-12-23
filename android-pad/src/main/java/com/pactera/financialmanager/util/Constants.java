package com.pactera.financialmanager.util;

import android.Manifest;

/**
 * 全局变量类，常量类
 */
public class Constants {
	public static final String MAP_AK = "pNfq9dz99WuB6p7ncaY4oYtc";// 百度地图AK
	// public static double TheLatitude, TheLongitude;// 经纬度
	// public static String LocStr = "";//定位的位置

	public static Boolean isLoadCateInfo = false;// 是否已经把分类码表下载到本地了
	public static final int cateIndex = 1;
	public static int SCREEN_WIDTH, SCREEN_HEIGHT;
	public static final int TAKE_PICTURE_FROM_CAMERA = 0x01;
	public static final int TAKE_PICTURE_FROM_PHOTO_ALBUM = 0x11;
	public static final int PHOTO_ZOOM = 0x16;// 22
	public static final int PHOTO_DELETE = 0x15;// 22

	public static String BRID;// 机构号

	public static String[] RIGHTSTR;//登录时返回的权限

	public static final String CHANNEL_CODE="01";//渠道号01平板，02手机
	public static final String[] PERMISSIONS = {
			Manifest.permission.ACCESS_COARSE_LOCATION,
			Manifest.permission.ACCESS_FINE_LOCATION,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.READ_PHONE_STATE
	};

	// 请求类型，查、添加、删除、修改、其他请求(jsondata数据格式)
	public static enum requestType {
		Search, Insert, Delete, Update, Other, JsonData, Paging;
	};

}
