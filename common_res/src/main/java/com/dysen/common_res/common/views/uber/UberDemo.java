package com.dysen.common_res.common.views.uber;

import android.os.Bundle;

import com.dysen.common_res.R;
import com.dysen.common_res.common.base.ParentActivity;


public class UberDemo extends ParentActivity {

    UberProgressView uber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uber_demo);

        init();
    }

    protected void init() {

        uber = bindView(R.id.uber);

    }
}
