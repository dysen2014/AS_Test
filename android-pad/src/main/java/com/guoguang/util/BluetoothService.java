package com.guoguang.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

public abstract class BluetoothService {
	//参数设置，酌情实现   

	public abstract void setArgs(Context context, String macAddr, Handler initBTHandler);

	//连接蓝牙

	public abstract boolean connBlueTooth(); 

	//开启身份证模块的电源，酌情实现。该接口的实现是因为身份证模块耗电相对较大，尽在需要时才开启电源。

	public abstract boolean openPower();

	//关闭身份证模块的电源，酌情实现。该接口的实现是因为身份证模块耗电相对较大，尽在需要时才开启电源。

	public abstract boolean closePower();

	//读身份证，返回身份证信息，包括姓名、身份证号、小头像等。

	public abstract IdentityCardInfo readIDCard();

	//读指纹，返回指纹图片

	public abstract Bitmap readFingerPrint();

	
	//断开蓝牙

	public abstract void disConnect();


}
