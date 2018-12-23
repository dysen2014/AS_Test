package com.dysen.common_res.common.utils;

import android.view.View;
import android.widget.TextView;

/**
 * Created by dysen on 2017/7/13.
 */

public interface OnItemClickCallback<T> {
	// 点击事件
	void onClick(View view, T obj);
	// 长按事件
	void onLongClick(View view, T obj);

    void onClick(View view, int position, int index);

}


