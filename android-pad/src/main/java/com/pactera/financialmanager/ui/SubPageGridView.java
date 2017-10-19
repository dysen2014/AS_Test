package com.pactera.financialmanager.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class SubPageGridView extends GridView {

	public SubPageGridView(Context context) {
		super(context);
	}
	
	public SubPageGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public SubPageGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override 
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 
        int expandSpec = MeasureSpec.makeMeasureSpec(  
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);  
        super.onMeasure(widthMeasureSpec, expandSpec);  
 
    } 
}
