package com.dysen.common_res.common.utils;

import android.view.View;
import android.widget.TextView;

public interface OnItemClick<T> {

	// 点击事件
	void onClick(View view, T obj, TextView item_tv);
}
