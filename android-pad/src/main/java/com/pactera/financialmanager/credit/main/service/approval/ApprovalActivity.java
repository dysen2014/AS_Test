package com.pactera.financialmanager.credit.main.service.approval;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.common_res.common.base.ParentActivity;
import com.dysen.common_res.common.utils.ActivityManagerApplication;
import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.common_res.common.views.uber.UberProgressView;
import com.dysen.pullloadmore_recyclerview.PullLoadMoreRecyclerView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.pactera.financialmanager.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApprovalActivity extends ParentActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {

    @Bind(R.id.txt_back)
    TextView txtBack;
    @Bind(R.id.lay_back)
    LinearLayout layBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_)
    TextView txt;
    @Bind(R.id.tab_0)
    TextView tab0;
    @Bind(R.id.tab_1)
    TextView tab1;
    @Bind(R.id.tab_2)
    TextView tab2;
    @Bind(R.id.tab_3)
    TextView tab3;
    @Bind(R.id.tab_4)
    TextView tab4;
    @Bind(R.id.ll_tab)
    LinearLayout llTab;
    @Bind(R.id.pull_load_more)
    PullLoadMoreRecyclerView pullLoadMore;
    @Bind(R.id.tv_hide_data)
    TextView tvHideData;
    @Bind(R.id.uber_pgsview)
    UberProgressView uberPgsview;

    boolean finishedFlag = false;
    String strFinishedFlag;

    private List<ApprovalBean.ExamineBean> listData = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == -1) {
//                    toast("请求超时！");
                tvHideData.setVisibility(View.VISIBLE);
                tvHideData.setText("请求超时！");
            }
            uberPgsview.setVisibility(View.INVISIBLE);
            if (msg.obj != null) {
                listData = parseList(HttpThread.parseJSONWithGson(msg.obj.toString()));
                initData(listData);
            } else {
                tvHideData.setVisibility(View.VISIBLE);
            }
        }
    };

    private void initData(List<ApprovalBean.ExamineBean> listData) {
        if (listData != null && listData.size() > 0) {
            tvHideData.setVisibility(View.INVISIBLE);
        } else {
            tvHideData.setVisibility(View.VISIBLE);
            listData.clear();
        }
        BusinessApprovalActivity.setData(listData);
        approvalAdapter = new ApprovalAdapter.ApprovalMain(this, listData, new OnItemClickCallback<Integer>() {
            @Override
            public void onClick(View view, Integer index) {
                Intent intent = new Intent(ApprovalActivity.this, BusinessApprovalActivity.class);
                intent.putExtra("index", index);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, Integer index) {

            }
        });
        pullLoadMore.setAdapter(approvalAdapter);
    }

    private ApprovalAdapter.ApprovalMain approvalAdapter;

    public static List<ApprovalBean.ExamineBean> parseList(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<ApprovalBean.ExamineBean> list = gson.fromJson(jsonData, new TypeToken<List<ApprovalBean.ExamineBean>>() {
            }.getType());

            return list;
        } else
            return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_approval);
        ButterKnife.bind(this);

        initView();
        sendRequest();
    }

    protected void sendRequest() {

        if (finishedFlag) {
            strFinishedFlag = "Y";
        } else
            strFinishedFlag = "N";
        //{"deviceType":"Android","RequestParams":{"BSTypeFlag":"credit","UserId":"B129103","FinishedFlag":"Y","SearchKey":"","CurPage":"1","PageSize":"20"}}
        JSONObject jsonObject = ParamUtils.setParams("examine", "examine", new Object[]{"credit", ParamUtils.UserIdApproval, strFinishedFlag, "", curPage, ParamUtils.pageSize}, 6);

        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handler);
    }

    private void initView() {

        ActivityManagerApplication.addActivity(this);

        txtTitle.setText("贷款审批");
        tab0.setText("待审批");
        tab1.setText("已审批");
        tab2.setVisibility(View.GONE);
        tab3.setVisibility(View.GONE);
        tab4.setVisibility(View.GONE);

        showViewBg(tab0);
        pullLoadMore.setGridLayout(2);
        pullLoadMore.setOnPullLoadMoreListener(this);
//        ptrLayout.setRefreshListener(this);
    }

    @OnClick({R.id.tab_0, R.id.tab_1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tab_0://未审批
                finishedFlag = false;
                break;
            case R.id.tab_1://已审批
                finishedFlag = true;
                break;
        }
        showViewBg(view);
        curPage = 1;
        sendRequest();
    }

    private void showViewBg(View view) {

        tab0.setTextColor(getResources().getColor(R.color.common_left_txt));
        tab1.setTextColor(getResources().getColor(R.color.common_left_txt));
        tab2.setTextColor(getResources().getColor(R.color.common_left_txt));
        tab3.setTextColor(getResources().getColor(R.color.common_left_txt));
        tab4.setTextColor(getResources().getColor(R.color.common_left_txt));
        tab0.setBackgroundResource(R.drawable.common_tab_selector);
        tab1.setBackgroundResource(R.drawable.common_tab_selector);
        tab2.setBackgroundResource(R.drawable.common_tab_selector);
        tab3.setBackgroundResource(R.drawable.common_tab_selector);
        tab4.setBackgroundResource(R.drawable.common_tab_selector);

//        if (view.getId() == R.id.tab_0)
//            view.setBackgroundResource(R.drawable.shape_bg_red_left);
//        else
//            view.setBackgroundResource(R.drawable.shape_bg_red_right);
        view.setBackgroundResource(R.drawable.tab_bg_selected);
        ((TextView) view).setTextColor(getResources().getColor(R.color.common_tab_bg));
    }

    private void clearList() {
        listData.clear();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                curPage = 1;
                clearList();
                sendRequest();
                // 结束刷新
                pullLoadMore.setPullLoadMoreCompleted();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                curPage++;
                clearList();
                sendRequest();
                // 结束刷新
                pullLoadMore.setPullLoadMoreCompleted();
            }
        }, 2000);
    }

    public static void setData(String name, String pwd) {
//        loginUserId = name;
//        loginPwd = pwd;
    }
}
