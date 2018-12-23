package com.pactera.financialmanager.ui.credit;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by xh on 2016-01-14.
 */
public class SimpleInputEditWatcher implements TextWatcher {

    public static final int EMAIL = 1;

    public static final int HOME_PHONE = 2;

    public static final int MOBILE_PHONE = 3;

    public static final int ZIP_CODE = 4;

    public static final int AGE = 5;

    private int editType;

    private EditText editText;

    private WatcherResultManager wrm;

    public SimpleInputEditWatcher(EditText editText, int editTYPE, WatcherResultManager r) {
        this.editText = editText;
        this.editType = editTYPE;
        this.wrm = r;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        boolean result = true;
        switch (editType) {
            case EMAIL: {
                result = InputCheckUtils.checkEmail(s.toString());
                break;
            }
            case HOME_PHONE: {
                result = InputCheckUtils.checkHomePhoneNumber(s.toString());
                break;
            }
            case MOBILE_PHONE: {
                result = InputCheckUtils.checkMobilePhoneNumber(s.toString());
                break;
            }
            case ZIP_CODE: {
                result = InputCheckUtils.checkZipCode(s.toString());
                break;
            }
            case AGE: {
                result = InputCheckUtils.checkAge(s.toString());
                break;
            }
        }

        if (!result) {
            editText.setError("输入有误");
            wrm.setError(this);
        } else {
            wrm.setOk(this);
        }
    }

}
