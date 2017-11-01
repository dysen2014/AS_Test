package com.pactera.financialmanager.credit.main.mine;

import android.os.Bundle;
import android.widget.TextView;

import com.dysen.common_res.common.base.ParentActivity;
import com.pactera.financialmanager.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends ParentActivity {

    @Bind(R.id.txt_back)
    TextView txtBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        txtBack.setText(R.string.tab_mine);
        txtTitle.setText("关于");
    }
}
