package com.pactera.financialmanager.credit.main.mine;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
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
    @Bind(R.id.lay_back)
    LinearLayout layBack;
    @Bind(R.id.txt_)
    TextView txt;
    @Bind(R.id.tv_about)
    TextView tvAbout;
    @Bind(R.id.wb)
    WebView wb;

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
        wb.setVisibility(View.VISIBLE);
        wb.loadUrl(String.valueOf(R.string.app_download));
    }
}
