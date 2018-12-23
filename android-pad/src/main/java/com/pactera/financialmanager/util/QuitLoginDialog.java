package com.pactera.financialmanager.util;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pactera.financialmanager.R;

public class QuitLoginDialog extends Dialog implements View.OnClickListener {
	private int layoutId, showImageId, btnYesId, btnNoId, linearId;

	private int screenWidth;// 屏幕宽度 　
	private int screenHeight;// 屏幕高度
	private Window window = null;
	private TextView bt_YES, bt_NO;
	private LinearLayout linear;

	/**
	 * @Miles 用于弹出dialog 交互或说明页
	 * ***/

	public QuitLoginDialog(Context context, int layoutId, int linearId,
			int btnYesId, int btnNoId, int showImageId, int screenWidth,
			int screenHeight, int theme) {
		super(context, theme);
		this.layoutId = layoutId;
		this.showImageId = showImageId;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.btnYesId = btnYesId;
		this.btnNoId = btnNoId;
		this.linearId = linearId;

	}

	public QuitLoginDialog(Context context) {
		super(context);

	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCanceledOnTouchOutside(false); // 设置触摸对话框意外的地方取消对话框
		// 声明资源对象
		Resources resources = this.getContext().getResources();

		window = getWindow(); // 得到对话框
		window.setWindowAnimations(R.style.activity_push_bottom); // 设置窗口弹出动画
		// 全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 用于为该Dialog设置布局文件
		setContentView(layoutId);
		linear = (LinearLayout) findViewById(linearId);

		// 获取Bitmap对象
		Bitmap bmp = BitmapFactory.decodeResource(resources, showImageId);

		// 获得图片的宽高

		int width = bmp.getWidth();

		int height = bmp.getHeight();

		// 设置想要的大小

		int newWidth = screenWidth;

		int newHeight = screenHeight;
		// 计算缩放比例

		float scaleWidth = ((float) newWidth) / width;

		float scaleHeight = ((float) newHeight) / height;

		// 取得想要缩放的matrix参数

		Matrix matrix = new Matrix();

		matrix.postScale(scaleWidth, scaleHeight);

		// 得到新的图片

		Bitmap newbm = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix,

		true);

		// 这是背景图片

		linear.setBackgroundDrawable(new BitmapDrawable(newbm));
		bt_YES = (TextView) QuitLoginDialog.this.findViewById(btnYesId);
		bt_NO = (TextView) QuitLoginDialog.this.findViewById(btnNoId);
		TextPaint tp1 = bt_YES.getPaint();
		tp1.setFakeBoldText(true);
		TextPaint tp2 = bt_NO.getPaint();
		tp2.setFakeBoldText(true);
		bt_YES.setOnClickListener(this);
		bt_NO.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == btnYesId) {
			cancel();
			
		}
		if (id == btnNoId) {
			dismiss();
			

		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:

			dismiss();

			break;
		case KeyEvent.KEYCODE_HOME:

			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
}
