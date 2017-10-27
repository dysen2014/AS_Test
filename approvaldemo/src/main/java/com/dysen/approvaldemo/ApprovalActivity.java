package com.dysen.approvaldemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.test.espresso.core.deps.guava.reflect.TypeToken;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dysen.common_res.common.base.ParentActivity;
import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.common_res.common.views.uber.UberProgressView;
import com.dysen.pullloadmore_recyclerview.PullLoadMoreRecyclerView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApprovalActivity extends ParentActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener{

    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.rbtn_0)
    RadioButton rbtn0;
    @Bind(R.id.rbtn_1)
    RadioButton rbtn1;
//    @Bind(R.id.rlv_data)
//    RecyclerView rlvData;
    @Bind(R.id.ptr_layout)
    PullToRefreshLayout ptrLayout;

    boolean finishedFlag = false;
    String FinishedFlag;
    @Bind(R.id.tv_hide_data)
    TextView tvHideData;
    @Bind(R.id.uber_pgsview)
    UberProgressView uberPgsview;
    @Bind(R.id.pull_load_more)
    PullLoadMoreRecyclerView pullLoadMore;

    private List<ApprovalBean.ExamineBean> listData = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == -1) {
//                toast("请求失败！");
                tvHideData.setText("请求错误！");
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
        }else {
            tvHideData.setVisibility(View.VISIBLE);
            listData.clear();
        }
        BusinessApprovalActivity.setData(listData);
        approvalAdapter = new ApprovalAdapter(this, listData, new OnItemClickCallback<Integer>() {
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

    private ApprovalAdapter approvalAdapter;

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
        txtTitle.setText("贷款审批");
        initView();
        sendRequest();
    }

    protected void sendRequest() {

        if (finishedFlag) {
            FinishedFlag = "Y";
        } else
            FinishedFlag = "N";
        //{"deviceType":"Android","RequestParams":{"BSTypeFlag":"credit","UserId":"B129103","FinishedFlag":"Y","SearchKey":"","CurPage":"1","PageSize":"20"}}
        JSONObject jsonObject = ParamUtils.setParams("examine", "examine", new Object[]{"credit", ParamUtils.UserIdApproval, FinishedFlag, "", curPage, ParamUtils.pageSize}, 6);

        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handler);
    }

    private void initView() {

        rbtn0.setText("待审批");
        rbtn1.setText("已审批");
        pullLoadMore.setGridLayout(2);
        pullLoadMore.setOnPullLoadMoreListener(this);
//        ptrLayout.setRefreshListener(this);
    }

    @OnClick({R.id.rbtn_0, R.id.rbtn_1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rbtn_0://未审批
                finishedFlag = false;
                break;
            case R.id.rbtn_1://已审批
                finishedFlag = true;
                break;
        }
        curPage = 1;
        sendRequest();
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
}
