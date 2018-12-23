package com.pactera.financialmanager.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.View;

public class PieView extends View {
	private int areaX = 1;
	private int areaY = 22;
	private int areaWidth;
	private int areaHight;
	private int colors[];
	private int shade_colors[];
//	private int percent[];
	private float percent[];
	private int thickness = 20;
	public int width;
	public int height;

	/**
	 * @param context 上下文
	 * @param colors 最上面颜色数组
	 * @param shade_colors 阴影颜色数组
	 * @param percent 百分比 (和必须是360)
	 */
	public PieView(Context context){
		super(context);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		height = View.MeasureSpec.getSize(heightMeasureSpec);
		width = View.MeasureSpec.getSize(widthMeasureSpec);
		// 必须调用setMeasuredDimension方法
		// 否则当控件放置时会引发一个运行时异常（测试后没有出现问题？）
		setMeasuredDimension(width, height);
	}
	public PieView(Context context, int[] colors, int[] shade_colors,
			float[] percent) {
		super(context);
		this.colors = colors;
		this.shade_colors = shade_colors;
		this.percent = percent;
	}

	// 设置厚度
	public void setThickness(int thickness) {
		this.thickness = thickness;
		areaY = thickness + 2;
		// 更新
		this.invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		areaWidth = width - 2;
		areaHight = height - 2;
		Paint paint = new Paint();
		// 设置颜色----貌似没用
		// paint.setColor(Color.RED);
		// 设置样式-填充
		paint.setStyle(Style.FILL);
		// 抗锯齿
		paint.setAntiAlias(true);
		// 设置画笔粗细
		// paint.setStrokeWidth(1);
		for (int i = 0; i <= thickness; i++) {
			int tempAngle = 0;
			for (int j = 0; j < percent.length; j++) {
				paint.setColor(shade_colors[j]);
				/**
				 * 调用方法画圆弧 第一个参数oval为RectF类型，即圆弧显示区域，
				 * 第二个参数startAngle和sweepAngle均为float类型，分别表示圆弧起始角度和圆弧度数,3点钟方向为0度，
				 * 第三个参数useCenter设置是否显示圆心，boolean类型， 第四个参数paint为画笔
				 */
				// RectF创建新的矩形包含坐标？左上右下
				canvas.drawArc(new RectF(areaX, areaY - i, areaX + areaWidth,
						areaHight - i), tempAngle, percent[j], true, paint);
				tempAngle += percent[j];
			}
			if (i == thickness) {
				tempAngle = 0;
				for (int j = 0; j < percent.length; j++) {
					paint.setColor(colors[j]);
					canvas.drawArc(new RectF(areaX, areaY - i, areaX
							+ areaWidth, areaHight - i), tempAngle, percent[j],
							true, paint);
					tempAngle += percent[j];
				}
			}
		}
	}
}

