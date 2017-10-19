package com.pactera.financialmanager.credit.common.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.pactera.financialmanager.R;


/**
 * Created by dysen on 2017/7/14.
 */

public class TitleLayout extends LinearLayout {

	public TitleLayout(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.com_title, this);

	}
}
