package com.pactera.financialmanager.util;

import  java.io.File;

import com.pactera.financialmanager.db.AreaDao;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;

/**
 * 获取版本号
 * @author Administrator
 *
 */
public class VersionUtils {

	public static int getVersionCode(Context context) {
		int code = 0;
		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo packInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			code = packInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return code;
	}

	public static String getVersionName(Context context) {
		String versionName = "1.0.0";
		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo packInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			versionName = packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}
	
	
	/**
	 * 获取版本号
	 * @param context
	 * @return
	 */
	public static int getVersionNo(Context context) {
		int versionCode = 0;
		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo packInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			versionCode = packInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}
	
	
	/**
	 * 校验是否下载新版本
	 * @param sysVersionNo
	 * @return
	 */
	public static boolean isUploadNewVersion(Context context, String serverVersion){
		// 只要后台返回版本号和我当前版本号，则提示返回更新
		String locationVersion = getVersionName(context);
		if(!locationVersion.equals(serverVersion)){
			// 删除本地db文件
			delLocFiles(AreaDao.pathStr);
			delLocFiles(CrashHandler.path);
		}
		
		String[] sysVerson = serverVersion.split("\\.");		
		String[] curVerson = locationVersion.split("\\.");	
		
		// 防止数组越界，取最短长度
		int vLength = 0;
		if(sysVerson.length <= curVerson.length){
			vLength = sysVerson.length;
		}else{
			vLength = curVerson.length;
		}
		
		boolean isBreak = false;
		for(int i=0; i<vLength; i++){
			Integer sys = Integer.parseInt(sysVerson[i]);
			Integer cur = Integer.parseInt(curVerson[i]);
			System.out.println(vLength+"num:"+sys + "-" +cur);
			// 如果当前版本小于系统版本，返回true
			if(sys > cur){
				return true;
			}else if(sys < cur){
				isBreak = true;
				break;
			}
			isBreak = false;
		}
		
		// 当循环结束，未跳出循环，系统长度大于当前长度则返回true
		if(!isBreak && sysVerson.length > curVerson.length){
			return true;
		}
		return false;
	}
	

	/**
	 * 删除本地文件
	 * @param filePath
	 */
	private static void delLocFiles(String filePath){
		File pathDb = Environment.getExternalStorageDirectory();
		File file = new File(pathDb+filePath);
		FiledUtil.deleteFileByFile(file);
	}

}
