package com.pactera.financialmanager.credit.main.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initTitle(this, "", false,"");

        initView();
    }

    private void initView() {
        txtBack.setText(R.string.tab_mine);
        txtTitle.setText("关于");

        if (layBack != null) {
            layBack.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    finish();
                }
            });
        }
    }
}
