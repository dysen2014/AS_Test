package com.dysen.approvaldemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.test.espresso.core.deps.guava.reflect.TypeToken;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dysen.common_res.common.base.ParentActivity;
import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.utils.ParamUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApprovalActivity extends ParentActivity implements BaseRefreshListener {

    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.rbtn_0)
    RadioButton rbtn0;
    @Bind(R.id.rbtn_1)
    RadioButton rbtn1;
    @Bind(R.id.rlv_data)
    RecyclerView rlvData;
    @Bind(R.id.ptr_layout)
    PullToRefreshLayout ptrLayout;

    boolean finishedFlag = false;
    String FinishedFlag;

    private List<ApprovalBean.ExamineBean> listData = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == -1) {
                toast("请求失败！");
            }

            if (msg.obj != null) {
                try {
                    listData = parseList(HttpThread.parseJSONWithGson(msg.obj.toString()));
                    if (listData != null && listData.size() > 0) {
                        LogUtils.d(listData.size() + "---------" + listData.get(0).getCustomerName());
                        initData(listData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void initData(List<ApprovalBean.ExamineBean> listData) {
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
        rlvData.setAdapter(approvalAdapter);
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
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rlvData.setLayoutManager(layoutManager);
        ptrLayout.setRefreshListener(this);
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
        sendRequest();
    }

    @Override
    public void refresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                curPage = 1;
                clearList();
                sendRequest();
                // 结束刷新
                ptrLayout.finishRefresh();
            }
        }, 2000);
    }

    @Override
    public void loadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                curPage ++;
                clearList();
                sendRequest();
                // 结束刷新
                ptrLayout.finishLoadMore();
            }
        }, 2000);
    }

    private void clearList() {
        listData.clear();
    }
}
