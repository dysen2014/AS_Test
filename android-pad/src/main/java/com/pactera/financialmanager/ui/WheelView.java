package com.pactera.financialmanager.ui;

import java.util.ArrayList;
import java.util.List;

import com.pactera.financialmanager.entity.AreaEntity;
import com.pactera.financialmanager.util.Tool;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 地址选择器
 * 
 * @author JAY
 * 
 */
public class WheelView extends ScrollView {
	public static final String TAG = WheelView.class.getSimpleName();

	public static class OnWheelViewListener {
		public void onSelected(String selectedIndex, String item) {
		};
	}

	private Context context;
	// private ScrollView scrollView;

	private LinearLayout views;

	public WheelView(Context context) {
		super(context);
		init(context);
	}

	public WheelView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public WheelView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	// String[] items;
	// List<String> items;
	List<AreaEntity> items;

	public List<AreaEntity> getItems() {
		return items;
	}

	public void setItems(List<AreaEntity> list) {

		if (list == null || list.size() == 0) {
			list = new ArrayList<AreaEntity>();
			list.add(new AreaEntity("000000", ""));
		}
		if (null == items) {
			items = new ArrayList<AreaEntity>();
		}
		items.clear();
		items.add(new AreaEntity("000000", ""));
		items.addAll(list);
		items.add(new AreaEntity("000000", ""));

		initData();
	}

	public static final int OFF_SET_DEFAULT = 1;
	int offset = OFF_SET_DEFAULT; // 偏移量（需要在最前面和最后面补全）

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	int displayItemCount; // 每页显示的纵向地址数量
	int selectedIndex = 1;

	private void init(Context context) {
		this.context = context;

		this.setVerticalScrollBarEnabled(false);

		views = new LinearLayout(context);
		views.setOrientation(LinearLayout.VERTICAL);
		this.addView(views);

		scrollerTask = new Runnable() {

			public void run() {

				int newY = getScrollY();
				if (initialY - newY == 0) { // stopped
					final int remainder = initialY % itemHeight;
					final int divided = initialY / itemHeight;
					Log.i("1111111", "offset:" + offset + ",selectedIndex="
							+ selectedIndex + ",initialY=" + initialY
							+ ",remainder=" + remainder + ",itemHeight="
							+ itemHeight+",divided="+divided);
					if (remainder == 0) {
						selectedIndex = divided + offset;

						onSeletedCallBack();
					} else {
						if (remainder > itemHeight / 2) {
							WheelView.this.post(new Runnable() {
								@Override
								public void run() {
									WheelView.this.smoothScrollTo(0, initialY
											- remainder + itemHeight);
									selectedIndex = divided + offset + 1;
									onSeletedCallBack();
								}
							});
						} else {
							WheelView.this.post(new Runnable() {
								@Override
								public void run() {
									WheelView.this.smoothScrollTo(0, initialY
											- remainder);
									selectedIndex = divided + offset;
									onSeletedCallBack();
								}
							});
						}
					}
				} else {
					initialY = getScrollY();
					WheelView.this.postDelayed(scrollerTask, newCheck);
				}
			}
		};
	}

	int initialY;

	Runnable scrollerTask;
	int newCheck = 50;

	public void startScrollerTask() {
		initialY = getScrollY();
		this.postDelayed(scrollerTask, newCheck);
	}

	private void initData() {
		if (views != null) {
			views.removeAllViews();
		}
		displayItemCount = offset * 2 + 2;//加载显示几行地址信息，此处是加载四行信息
		for (AreaEntity entity : items) {
			String item = entity.getName();
			views.addView(createView(item));
		}

		refreshItemView(0);
	}

	int itemHeight = 0;

	private TextView createView(String item) {
		TextView tv = new TextView(context);
		tv.setLayoutParams(new LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		tv.setSingleLine(true);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);//中间显示的字体的大小
		tv.setText(item);
		tv.setGravity(Gravity.CENTER);
		int padding = Tool.dip2px(context, 10);//字体与容器 的内边距 padding
		tv.setPadding(padding, padding, padding, padding);
		if (0 == itemHeight) {
			tv.measure(MeasureSpec.EXACTLY, MeasureSpec.UNSPECIFIED);
			itemHeight = tv.getMeasuredHeight();

			views.setLayoutParams(new LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT, itemHeight
							* displayItemCount));
			LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this
					.getLayoutParams();
			this.setLayoutParams(new LinearLayout.LayoutParams(lp.width,
					itemHeight * displayItemCount));
		}
		return tv;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);

		refreshItemView(t);

		if (t > oldt) {
			scrollDirection = SCROLL_DIRECTION_DOWN;
		} else {
			scrollDirection = SCROLL_DIRECTION_UP;
		}
	}

	private void refreshItemView(int y) {
		int position = y / itemHeight + offset;
		int remainder = y % itemHeight;
		int divided = y / itemHeight;

		if (remainder == 0) {
			position = divided + offset;
		} else {
			if (remainder > itemHeight / 2) {
				position = divided + offset + 1;
			}

		}

		int childSize = views.getChildCount();
		for (int i = 0; i < childSize; i++) {
			TextView itemView = (TextView) views.getChildAt(i);
			if (null == itemView) {
				return;
			}
			if (position == i) {
				itemView.setTextColor(Color.parseColor("#505050"));
			} else {
				itemView.setTextColor(Color.parseColor("#bbbbbb"));
			}
		}

	}

	/**
	 * 获取选中区域的边界
	 */
	int[] selectedAreaBorder;

	private int[] obtainSelectedAreaBorder() {
		if (null == selectedAreaBorder) {
			selectedAreaBorder = new int[2];
			selectedAreaBorder[0] = itemHeight * offset;
			selectedAreaBorder[1] = itemHeight * (offset + 1);
//			selectedAreaBorder[1] = itemHeight * (offset + 2);   //12.26号，在调试滑轮时，中间横线位置会显示两个地区如湖北湖南省都在中间
		}
		return selectedAreaBorder;
	}

	private int scrollDirection = -1;
	private static final int SCROLL_DIRECTION_UP = 0;
	private static final int SCROLL_DIRECTION_DOWN = 1;

	Paint paint;
	int viewWidth;

	@Override
	public void setBackgroundDrawable(Drawable background) {

		if (viewWidth == 0) {
			viewWidth = ((Activity) context).getWindowManager()
					.getDefaultDisplay().getWidth();

		}

		if (null == paint) {
			paint = new Paint();
			paint.setColor(Color.parseColor("#ff9900"));
			paint.setStrokeWidth((float) Tool.dip2px(context, 1));
		}

		background = new Drawable() {
			@Override
			public void draw(Canvas canvas) {
				canvas.drawLine(viewWidth * 1 / 6,
						obtainSelectedAreaBorder()[0], viewWidth * 5 / 6,
						obtainSelectedAreaBorder()[0], paint);
				canvas.drawLine(viewWidth * 1 / 6,
						obtainSelectedAreaBorder()[1], viewWidth * 5 / 6,
						obtainSelectedAreaBorder()[1], paint);
			}

			@Override
			public void setAlpha(int alpha) {

			}

			@Override
			public void setColorFilter(ColorFilter cf) {

			}

			@Override
			public int getOpacity() {
				return 0;
			}
		};
		super.setBackgroundDrawable(background);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		viewWidth = w;
		setBackgroundDrawable(null);
	}

	/**
	 * 选中回调
	 */
	private void onSeletedCallBack() {

		if (null != onWheelViewListener) {
			// onWheelViewListener.onSelected(selectedIndex,
			// items.get(selectedIndex));

			if (items.size() >= selectedIndex && selectedIndex >= 0) {
				onWheelViewListener.onSelected(
						items.get(selectedIndex).getId(),
						items.get(selectedIndex).getName());
			}
		}
	}

	public void setSeletion(int position) {
		final int p = position;
		selectedIndex = p + offset;
		this.post(new Runnable() {
			@Override
			public void run() {
				WheelView.this.smoothScrollTo(0, p * itemHeight);
			}
		});

	}

	public AreaEntity getSeletedItem() {
		return items.get(selectedIndex);
	}

	public int getSeletedIndex() {
		return selectedIndex - offset;
	}

	@Override
	public void fling(int velocityY) {
		super.fling(velocityY / 3);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_UP) {

			startScrollerTask();
		}
		return super.onTouchEvent(ev);
	}

	private OnWheelViewListener onWheelViewListener;

	public OnWheelViewListener getOnWheelViewListener() {
		return onWheelViewListener;
	}

	public void setOnWheelViewListener(OnWheelViewListener onWheelViewListener) {
		this.onWheelViewListener = onWheelViewListener;
	}
}
