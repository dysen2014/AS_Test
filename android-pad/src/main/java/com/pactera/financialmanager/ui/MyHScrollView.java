package com.pactera.financialmanager.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class MyHScrollView extends HorizontalScrollView {
    public MyHScrollView(Context context) {
        super(context);
    }

    public MyHScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyHScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private OnScrollListener l;

    public void setOnScrollListener(OnScrollListener l) {
        this.l = l;
    }

    /*
    public OnScrollListener getOnScrollListener(){
        return l;
    }
    */

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (this.l != null)
            this.l.onScrollChanged(l, t, oldl, oldt);
    }

    public interface OnScrollListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }
}
