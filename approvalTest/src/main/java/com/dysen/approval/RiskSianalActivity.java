package com.dysen.approval;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.approvaldemo.R;
import com.dysen.common_res.common.base.ParentActivity;
import com.dysen.common_res.common.utils.ActivityManagerApplication;
import com.dysen.common_res.common.views.uber.UberProgressView;
import com.dysen.pullloadmore_recyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RiskSianalActivity extends ParentActivity {

    @Bind(R.id.txt_back)
    TextView txtBack;
    @Bind(R.id.lay_back)
    LinearLayout layBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_)
    TextView txt;
    @Bind(R.id.pull_load_more)
    PullLoadMoreRecyclerView pullLoadMore;
    @Bind(R.id.tv_hide_data)
    TextView tvHideData;
    @Bind(R.id.uber_pgsview)
    UberProgressView uberPgsview;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    ApprovalAdapter.RiskSignal riskSignal = null;
    private static List<ApprovalBean.RiskSignalBean> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_signal);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        ActivityManagerApplication.addActivity(this);
        txtTitle.setText("风险提示");
        layBack.setVisibility(View.INVISIBLE);
        btnSubmit.setText("我已悉知风险并继续");
        uberPgsview.setVisibility(View.INVISIBLE);

        pullLoadMore.setGridLayout(1);
        pullLoadMore.setPullRefreshEnable(false);
        pullLoadMore.setPushRefreshEnable(false);

        if (checkListValid(listData)) {
            riskSignal = new ApprovalAdapter.RiskSignal(this, listData);
        }
        pullLoadMore.setAdapter(riskSignal);

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

    @OnClick({R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_submit:
                finish();
                break;
        }
    }

    public static void setData(List<ApprovalBean.RiskSignalBean> list) {
        listData = list;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
