package com.pactera.financialmanager.ui.credit;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by xh on 2015/12/24.
 */
public class MaritalRadioGroup extends RadioGroup {

    private static String[] statusText = new String[] {"未婚", "已婚", "离异"};
    private static String[] statusCode = new String[] {"10", "20", "40", "90"};

    public MaritalRadioGroup(Context context) {
        super(context);
    }

    public MaritalRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initText() {
        for (int i = 0; i < statusText.length; i++) {
            ((RadioButton)getChildAt(i)).setText(statusText[i]);
        }
    }

    public void setByCode(String code) {
        for (int i = 0; i < statusCode.length - 1; i++) {
            if (code.equals(statusCode[i])) {
                ((RadioButton)getChildAt(i)).setChecked(true);
            } else {
                ((RadioButton)getChildAt(i)).setChecked(false);
            }
        }
    }

    public String getCode() {
        for (int i = 0; i < statusText.length; i++) {
            if (((RadioButton)getChildAt(i)).isChecked()) {
                return statusCode[i];
            }
        }
        return statusCode[statusCode.length - 1];
    }
}
