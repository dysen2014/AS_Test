package com.pactera.financialmanager.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class MarqueeText extends TextView implements Runnable {
	private int currentScrollX;// 当前滚动的位置
	private boolean isStop = false;
	private int textWidth;
	private boolean isMeasure = false;

	public MarqueeText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MarqueeText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MarqueeText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if (!isMeasure) {// 文字宽度只需获取一次就可以了
			getTextWidth();
			isMeasure = true;
		}
	}

	/**
	 * 获取文字宽度
	 */
	private void getTextWidth() {
		Paint paint = this.getPaint();
		String str = this.getText().toString();
		textWidth = (int) paint.measureText(str);
		currentScrollX = -this.getWidth();
		;
	}

	@Override
	public void run() {
		currentScrollX += 3;// 滚动速度
		scrollTo(currentScrollX, 0);
		if (isStop) {
			return;
		}
		/*
		 * if (getScrollX() <= -(this.getWidth())) { scrollTo(textWidth, 0);
		 * currentScrollX = textWidth; // return; }
		 */

		if (getScrollX() > textWidth + this.getWidth()) {
			currentScrollX = -this.getWidth();

			scrollTo(currentScrollX, 0);
			// return;
		}

		postDelayed(this, 3);
	}

	// 开始滚动
	public void startScroll() {

		isStop = false;
		this.removeCallbacks(this);
		post(this);
		// 获取文子宽度
		getTextWidth();

	}

	// 停止滚动
	public void stopScroll() {
		isStop = true;
	}

	// 从头开始滚动
	public void startFor0() {
		currentScrollX = -this.getWidth();
		;
		startScroll();
	}
}