package com.pactera.financialmanager.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.pactera.financialmanager.R;

/**
 * 自定义Dialog
 */

public class DefiDialog extends Dialog {
	private Window window = null;

	public DefiDialog(Context context, int theme) {
		super(context, theme);
	}

	// public MyDialog(Context context) {
	// super(context);
	// }
	public void showDialog(View view, int grivity) {
		setContentView(view);

		windowDeploy(grivity);

		setCanceledOnTouchOutside(true); // 设置触摸对话框意外的地方取消对话框
		show();
	}

	// 设置窗口显示
	public void windowDeploy(int grivity) {
		window = getWindow(); // 得到对话框
		window.setWindowAnimations(R.style.activity_push_bottom); // 设置窗口弹出动画
		// window.setBackgroundDrawableResource(R.color.vifrification);
		// //设置对话框背景为透明
		WindowManager.LayoutParams wl = window.getAttributes();
		// 根据x，y坐标设置窗口需要显示的位置
		// wl.x = x; //x小于0左移，大于0右移
		// wl.y = y; //y小于0上移，大于0下移
		// wl.alpha = 0.6f; //设置透明度
		wl.gravity = grivity; // 设置重力
		window.setAttributes(wl);
	}
}