package com.dysen.approvaldemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.common_res.common.adapter.MyRecycleViewAdapter;
import com.dysen.common_res.common.base.ParentActivity;
import com.dysen.common_res.common.views.ViewUtils;
import com.dysen.common_res.common.views.uber.UberProgressView;
import com.dysen.pullloadmore_recyclerview.PullLoadMoreRecyclerView;
import com.yinglan.alphatabs.AlphaTabView;
import com.yinglan.alphatabs.AlphaTabsIndicator;
import com.yinglan.alphatabs.OnTabChangedListner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OpinionActivity extends ParentActivity {

    @Bind(R.id.txt_back)
    TextView txtBack;
    @Bind(R.id.lay_back)
    LinearLayout layBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_)
    TextView txt;
    @Bind(R.id.rlv_data)
    RecyclerView rlvData;
    @Bind(R.id.tab_0)
    AlphaTabView tab0;
    @Bind(R.id.tab_1)
    AlphaTabView tab1;
    @Bind(R.id.alphaIndicator)
    AlphaTabsIndicator alphaIndicator;
    @Bind(R.id.opinion_edt)
    EditText opinionEdt;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    private static List<ApprovalBean.ExamineBean> examineBeanList;
    @Bind(R.id.uber_pgsview)
    UberProgressView uberPgsview;
    @Bind(R.id.tv_hide_data)
    TextView tvHideData;
    @Bind(R.id.pull_load_more)
    PullLoadMoreRecyclerView pullLoadMore;
    private String opinionType = "同意";
    private Intent intent;
    static int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);
        ButterKnife.bind(this);

        initView();

        alphaIndicator.setOnTabChangedListner(new OnTabChangedListner() {
            @Override
            public void onTabSelected(int tabNum) {
                switch (tabNum) {
                    case 0:
                        opinionType = "同意";
                        break;
                    case 1:
                        opinionType = "不同意";
                        break;
                }
            }
        });
    }

    private void initView() {

        txtTitle.setText("签署意见");
        btnSubmit.setText("保存并提交");

        pullLoadMore.setGridLayout(2);
        pullLoadMore.setPushRefreshEnable(false);
        pullLoadMore.setPullRefreshEnable(false);
        if (examineBeanList != null && examineBeanList.size() > 0) {
            List<String> list = new ArrayList<>();
            list.add("业务品种:\t" + examineBeanList.get(index).getBusinessName());
            list.add("客户名称:\t" + examineBeanList.get(index).getCustomerName());
            list.add("申请金额:\t" + examineBeanList.get(index).getBusinessSum());
            list.add("登记机构:\t" + examineBeanList.get(index).getOrgName());
            pullLoadMore.setAdapter(new MyRecycleViewAdapter(this, -1, list));
        } else
            tvHideData.setVisibility(View.VISIBLE);
        uberPgsview.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {

        if (!ViewUtils.getText(opinionEdt).equals("")) {
            intent = new Intent(OpinionActivity.this, OpinionSubmitActivity.class);
//            intent.putExtra("index", index);
//            intent.putExtra("opinion_type", opinionType);
//            intent.putExtra("opinion_content", ViewUtils.getText(opinionEdt));
            OpinionSubmitActivity.setData(examineBeanList, index, opinionType, ViewUtils.getText(opinionEdt));
            startActivity(intent);
        } else
            toast("请填写签署意见！");
    }

    public static void setData(List<ApprovalBean.ExamineBean> listData, int mIndex) {
        examineBeanList = listData;
        index = mIndex;
    }
}
