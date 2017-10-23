package com.dysen.common_res.common.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dysen.common_res.R;
import com.dysen.common_res.common.utils.DialogAlert;
import com.dysen.common_res.common.utils.FormatUtil;
import com.jaeger.library.StatusBarUtil;

import q.rorbin.badgeview.QBadgeView;


public class ParentActivity extends FragmentActivity {

	static Context context;
	protected int curPage = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		context = this;
		StatusBarUtil.setColor(this, Color.parseColor("#ea452f"), 0);
//		StatusBarUtil.setColorDiff(this, Color.parseColor("#ea452f"));
	}
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		setStatusBar();
	}

	protected void setStatusBar() {
//		StatusBarUtil.setTranslucentDiff(this);

	}
protected void backActivity(View v){
	finish();
}

	public static String getTypeName(String customerType) {

		String name = "";
		if (customerType.equals("010"))
			name = "公";
		if (customerType.equals("030"))
			name = "个";
		if (customerType.equals("040"))
			name = "农";

		return name;
	}

	/**
	 * 绑定 View
	 * */
	@SuppressWarnings("unchecked")
	protected <T extends View> T bindView(int id) {
		return (T) findViewById(id);
	}

	@SuppressWarnings("unchecked")
	protected <T extends View> T bindView(View view, int id) {
		return (T) view.findViewById(id);
	}

	private static View oldView;
	static int oldColor ;
	/**
	 * 设置item 被选中时的效果
	 */
	public static void setSelectorItemColor(View view) {

//		LogUtils.d("colorId:"+colorId);
		if (oldView == null){
			//第一次点击
			oldView = view;
			oldColor = view.getDrawingCacheBackgroundColor();//当前 iew 的颜色
			view.setBackgroundResource(R.color.common_yellow);
		}else {
			//非第一次点击
			//把上一次点击的 变化
			oldView.setBackgroundResource(oldColor);
			view.setBackgroundResource(R.color.common_yellow);
			oldView = view;
		}
	}
	/**
	 * 设置item 被选中时的效果
	 */
	public static void setSelectorItemColor(View view, @ColorRes@DrawableRes int colorId) {

//		LogUtils.d("colorId:"+colorId);
		if (oldView == null){
			//第一次点击
			oldView = view;
			oldColor = view.getDrawingCacheBackgroundColor();//当前 iew 的颜色
			view.setBackgroundResource(colorId <= Long.parseLong("7F000000", 16)? R.color.common_yellow : colorId);
		}else {
			//非第一次点击
			//把上一次点击的 变化
			oldView.setBackgroundResource(oldColor);
			view.setBackgroundResource(colorId <= Long.parseLong("7F000000", 16) ? R.color.common_yellow : colorId);
			oldView = view;
		}
	}

	public static void setSelectorItemColor(View view, @ColorRes @DrawableRes int colorId, @ColorRes @DrawableRes int oldColorId) {

//		LogUtils.d("colorId:"+colorId);
		if (oldView == null) {
			//第一次点击
			oldView = view;
			oldColor = oldColorId;//当前 iew 的颜色
			view.setBackgroundResource(colorId <= Long.parseLong("7F000000", 16) ? com.dysen.common_res.R.color.common_yellow : colorId);
		} else {
			//非第一次点击
			//把上一次点击的 变化
			oldView.setBackgroundResource(oldColor);
			view.setBackgroundResource(colorId <= Long.parseLong("7F000000", 16) ? com.dysen.common_res.R.color.common_yellow : colorId);
			oldView = view;
		}
	}
	private long lastTime = 0;

	protected boolean checkObjValid(Object obj) {
		if (obj != null)
			return true;
		else
			return false;
	}

	/**
	 * Toast消息提示
	 * @param text
	 */
	public void toast(CharSequence text){
		// 2s内只提示一次
		long currentTime = System.currentTimeMillis();
		if(currentTime - lastTime > 2000){
			lastTime = currentTime;
			Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
		}
	}

	private LinearLayoutManager mLayoutManager;
	protected RecyclerView setRecyclerView(RecyclerView recyclerView) {

		//设置固定大小
		recyclerView.setHasFixedSize(true);
		//创建线性布局
		mLayoutManager = new LinearLayoutManager(this);
		//垂直方向
		mLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
		//添加分割线
//		recyclerView.addItemDecoration(new TestDecoration(this));
		//给RecyclerView设置布局管理器
		recyclerView.setLayoutManager(mLayoutManager);

		return recyclerView;
	}

	protected RecyclerView setRecyclerView(RecyclerView recyclerView, int orientation) {

		//设置固定大小
		recyclerView.setHasFixedSize(true);
		//创建线性布局
		mLayoutManager = new LinearLayoutManager(context);
		//垂直方向 OrientationHelper.HORIZONTAL 0 OrientationHelper.VERTICAL 1
		mLayoutManager.setOrientation(orientation);
		//添加分割线
//		recyclerView.addItemDecoration(new TestDecoration(this));
		//给RecyclerView设置布局管理器
		recyclerView.setLayoutManager(mLayoutManager);

		return recyclerView;
	}
	protected RecyclerView setRecyclerView(RecyclerView recyclerView, RecyclerView.LayoutManager mLayoutManager) {

		//设置固定大小
		recyclerView.setHasFixedSize(true);
		//创建线性布局
//		mLayoutManager = new LinearLayoutManager(this);
		//垂直方向
//		mLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
		//添加分割线
//		recyclerView.addItemDecoration(new TestDecoration(this));
		//给RecyclerView设置布局管理器
		recyclerView.setLayoutManager(mLayoutManager);

		return recyclerView;
	}

	/**
	 * 提示框
	 * @param context
	 * @param str
	 * @return
	 */
	protected DialogAlert ShowDialog(Context context, String str){
		DialogAlert dialog = new DialogAlert(context);
		dialog.show();
		dialog.setMsg(str);
//		dialog.setMsg("抱歉，查询条件不能为空！");

		return dialog;
	}

	/**
	 * 显示/隐藏 View 设值
	 *
	 * @param view
	 * @param text
	 * @param bl
	 */
	public void setTextHide(View view, String text, boolean bl) {
		TextView mTextView = null;
		Button btn = null;
		if (bl)
			view.setVisibility(View.INVISIBLE);
		else {
			view.setVisibility(View.VISIBLE);
			if (view instanceof TextView)
				mTextView.setText(text);
			else if (view instanceof Button)
				btn.setText(text);
		}
	}

	/**
	 * 弹出软键盘
	 */
	private void showKeyboard(View view){
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.showSoftInput(view, 0);
	}

	protected  void mySetResult(int resultCode, Intent intent){
		setResult(resultCode, intent);
		finish();
	}
	/**
	 * 关闭软键盘
	 */
	protected void closeKeyboard() {
		View view = getWindow().peekDecorView();
		if (view != null) {
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	public void setBadgeView(View view, String text) {

		QBadgeView badge = new QBadgeView(context);
		badge.bindTarget(view);
		if (FormatUtil.isNumeric(text))
			badge.setBadgeNumber(Integer.parseInt(text));
		else
			badge.setBadgeText(text);
	}
}