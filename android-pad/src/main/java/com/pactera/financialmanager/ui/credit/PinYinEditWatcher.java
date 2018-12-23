package com.pactera.financialmanager.ui.credit;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by xh on 2016-01-11.
 */
public class PinYinEditWatcher implements TextWatcher {

    private boolean f = false;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {

        if (!f) {
            f = true;

            StringBuilder sb = new StringBuilder();
            char[] arr = s.toString().toCharArray();
            for (int i = 0; i < arr.length; i++) {
                char c = arr[i];
                if (('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z')) {
                    sb.append(Character.toUpperCase(c));
                }
                if (c == ' ') {
                    if (i == 0) continue;
                    if (i > 0 && arr[i-1] == ' ') continue;
                    sb.append(c);
                }
            }

            s.clear();
            s.append(sb.toString());

            f = false;
        }
    }
}
