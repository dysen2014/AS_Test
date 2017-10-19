package com.pactera.financialmanager.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 下拉、上拉刷新列表
 * @author Administrator
 *
 */
public class PullableListView extends ListView implements Pullable {
	
	// 是否可以下拉刷新
	private boolean isNeedPulldown = true;
	// 是否可以上拉加载
	private boolean isNeedPullup = false;

	public PullableListView(Context context) {
		this(context, null);
	}

	public PullableListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PullableListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean canPullDown() {
		if(!isNeedPulldown){
			return false;
		}
		
		if (getCount() == 0) {
			// 没有item的时候也可以下拉刷新
			return true;
		} else if (getFirstVisiblePosition() == 0
				&& getChildAt(0).getTop() >= 0) {
			// 滑到ListView的顶部了
			return true;
		} else{
			return false;
		}
	}

	@Override
	public boolean canPullUp() {
		if(!isNeedPullup){
			return false;
		}
		
		if (getCount() == 0) {
			// 没有item的时候也可以上拉加载
			return true;
		} else if (getLastVisiblePosition() == (getCount() - 1)) {
			// 滑到底部了
			if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
					&& getChildAt(
							getLastVisiblePosition()
									- getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
				return true;
		}
		return false;
	}
	
	/**
	 * 设置上拉下拉状态
	 * @param isNeedPulldown
	 * @param isPullup
	 */
	@Override
	public void setPullstatus(boolean isNeedPulldown, boolean isPullup){
		this.isNeedPulldown = isNeedPulldown;
		this.isNeedPullup = isPullup;
	}
}
