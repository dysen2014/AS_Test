package com.pactera.financialmanager.ui.credit;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.pactera.financialmanager.R;

/**
 * Created by xh on 2015/12/24.
 */
public class CheckResultView extends ImageView {

    private static int UNCHECKED = R.drawable.anim_p1;
    private static int CHECKED_OK = R.drawable.business_ok;
    private static int CHECKED_ERR = R.drawable.unsign2x;

    public CheckResultView(Context context) {
        super(context);
    }

    public CheckResultView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckResultView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void uncheck() {
        this.setImageResource(UNCHECKED);
    }

    public void ok() {
        this.setImageResource(CHECKED_OK);
    }

    public void err() {
        this.setImageResource(CHECKED_ERR);
    }
}
