package com.pactera.financialmanager.ui.commontool;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewMarquee extends TextView {

	public TextViewMarquee(Context context) {
		super(context);
		this.setEllipsize(TruncateAt.MARQUEE);
		this.setSingleLine(true);
	}
	
	public TextViewMarquee(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setEllipsize(TruncateAt.MARQUEE);
		this.setSingleLine(true);
	}

	public TextViewMarquee(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setEllipsize(TruncateAt.MARQUEE);
		this.setSingleLine(true);
	}
	

	@Override
    public boolean isFocused() {
        return true;
    }

}
