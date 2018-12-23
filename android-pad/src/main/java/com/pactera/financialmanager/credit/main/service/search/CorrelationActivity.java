package com.pactera.financialmanager.credit.main.service.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dysen.common_res.common.base.ParentActivity;
import com.dysen.common_res.common.base.ParentFragment;
import com.pactera.financialmanager.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CorrelationActivity extends ParentActivity {

    @Bind(R.id.txt_back)
    TextView txtBack;
    @Bind(R.id.lay_back)
    LinearLayout layBack;
    @Bind(R.id.rbtn_0)
    RadioButton rbtn0;
    @Bind(R.id.rbtn_1)
    RadioButton rbtn1;
    @Bind(R.id.fragment_Correlation)
    FrameLayout fragmentCorrelation;

    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correlation);
        ButterKnife.bind(this);

        bundle = new Bundle();
        if (bundle != null) {
            bundle.putString("CustomerID", getIntent().getStringExtra("cusId"));
            bundle.putString("CustomerTypes",getIntent().getStringExtra("CustomerTypes"));
            customerReplaceFrament(new AssociatedFragment());
        }

        initView();

    }

    private void customerReplaceFrament(ParentFragment frament) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        frament.setArguments(bundle);
        transaction.replace(R.id.fragment_Correlation, frament);
        transaction.commit();
    }

    private void initView() {

        txtBack.setText("返回");
        rbtn0.setText("关系");
        rbtn1.setText("业务");

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

    @OnClick({R.id.rbtn_0, R.id.rbtn_1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rbtn_0:
                //关系
                customerReplaceFrament(new AssociatedFragment());
                break;
            case R.id.rbtn_1:
                //业务
                customerReplaceFrament(new AssociatedBusinessFragment());
                break;
        }
        showFragment(view);
    }

    private void showFragment(View view) {

        rbtn0.setTextColor(getResources().getColor(R.color.white));
        rbtn1.setTextColor(getResources().getColor(R.color.white));
        if (view.getId() == R.id.rbtn_0) {
            view.setBackgroundResource(R.drawable.rbt_bg_selector_left);
            ((RadioButton) view).setTextColor(getResources().getColor(R.color.common_tab_bg));

        }else {
            view.setBackgroundResource(R.drawable.rbt_bg_selector_right);
            ((RadioButton) view).setTextColor(getResources().getColor(R.color.common_tab_bg));
        }
    }

}
