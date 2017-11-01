package com.pactera.financialmanager.credit.main.service.approval;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.common_res.common.base.ParentActivity;
import com.dysen.common_res.common.utils.ActivityManagerApplication;
import com.dysen.common_res.common.utils.CallAndSMS;
import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.common_res.common.views.uber.UberProgressView;
import com.dysen.pullloadmore_recyclerview.PullLoadMoreRecyclerView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.pactera.financialmanager.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dysen on 2017/10/17.
 */

class BusinessApprovalActivity extends ParentActivity {

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
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    String[] tabNames = new String[]{"申请信息", "客户信息", "担保信息", "", "处理意见"};
    JSONObject jsonObject;
    private static int tabIndex, index;
    List<String> data = new ArrayList<>();

    private static List<ApprovalBean.ExamineBean> examineBeanList = new ArrayList<>();
    private List<ApprovalBean.ApplyInfoBean> listData = new ArrayList<>();

    private List<ApprovalBean.GuarantyInfoBean> listDataGuaranty = new ArrayList<>();
    private List<ApprovalBean.OpinionInfoBean> listDataOpinion = new ArrayList<>();
    ApprovalAdapter.ApprovalOpinion approvalOpinion = null;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            uberPgsview.setVisibility(View.INVISIBLE);
            tvHideData.setVisibility(View.INVISIBLE);
            try {
                if (msg.obj != null) {
                    initData(msg.what, msg.obj.toString());
                } else {
                    tvHideData.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    private JSONArray jsonArray;
    private String customerId;

    private void initData(int tabIndex, String jsonStr) throws JSONException {
        switch (tabIndex) {

            case -1:
                tvHideData.setVisibility(View.VISIBLE);
                tvHideData.setText(R.string.request_timeout);
                break;
            case -100:
                tvHideData.setVisibility(View.VISIBLE);
                break;
            case 0:
            case 1:
                jsonArray = HttpThread.parseJSON(jsonStr).getJSONArray("array");
                listData = parseListApplyInfo(jsonArray.getJSONObject(0).getJSONArray("groupColArray").toString());
                pullLoadMore.setAdapter(new ApprovalAdapter.ApprovalBusiness(this, listData, new OnItemClickCallback<Integer>() {
                    @Override
                    public void onClick(View view, Integer info) {

                        CallAndSMS.call(BusinessApprovalActivity.this, listData.get(info).getValue());
                    }

                    @Override
                    public void onLongClick(View view, Integer info) {

                    }
                }));

                break;
            case 2:
                listDataGuaranty = parseListGuarantyInfo(HttpThread.parseJSONWithGson(jsonStr));
                pullLoadMore.setAdapter(new ApprovalAdapter.ApprovalGuaranty(this, listDataGuaranty));
                break;
            case 3:
                break;
            case 4:
                jsonArray = HttpThread.parseJSON(jsonStr).getJSONArray("array");
                listDataOpinion = parseListOpinionInfo(jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    data.add(listDataOpinion.get(i).getUserName());
                    data.add(listDataOpinion.get(i).getOrgName());
                    data.add(listDataOpinion.get(i).getBeginTime());
                    data.add(listDataOpinion.get(i).getPhaseName());
                    data.add(listDataOpinion.get(i).getUserName());
                    data.add(listDataOpinion.get(i).getPhaseOpinion());
                    data.add(listDataOpinion.get(i).getEndTime());
                    data.add(listDataOpinion.get(i).getIdea());
                }
                approvalOpinion = new ApprovalAdapter.ApprovalOpinion(BusinessApprovalActivity.this, listDataOpinion);
                pullLoadMore.setAdapter(approvalOpinion);
                break;
        }
    }

    public static List<ApprovalBean.GuarantyInfoBean> parseListGuarantyInfo(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<ApprovalBean.GuarantyInfoBean> list = gson.fromJson(jsonData, new TypeToken<List<ApprovalBean.GuarantyInfoBean>>() {
            }.getType());

            return list;
        } else
            return null;
    }

    public static List<ApprovalBean.ApplyInfoBean> parseListApplyInfo(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<ApprovalBean.ApplyInfoBean> list = gson.fromJson(jsonData, new TypeToken<List<ApprovalBean.ApplyInfoBean>>() {
            }.getType());

            return list;
        } else
            return null;
    }

    public static List<ApprovalBean.OpinionInfoBean> parseListOpinionInfo(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<ApprovalBean.OpinionInfoBean> list = gson.fromJson(jsonData, new TypeToken<List<ApprovalBean.OpinionInfoBean>>() {
            }.getType());

            return list;
        } else
            return null;
    }

    public static List<ApprovalBean> parseList(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<ApprovalBean> list = gson.fromJson(jsonData, new TypeToken<List<ApprovalBean>>() {
            }.getType());

            return list;
        } else
            return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_approval);
        ButterKnife.bind(this);

        txtTitle.setText("业务审批");
        initView();
        jsonObject = ParamUtils.setParams("applyInfo", "applyInfo", new Object[]{examineBeanList.get(index).getObjectNo()}, 1);
        sendRequest(jsonObject, 0);
    }

    private void initView() {

        ActivityManagerApplication.addActivity(this);
        pullLoadMore.setPullRefreshEnable(false);
        pullLoadMore.setPushRefreshEnable(false);
        pullLoadMore.setGridLayout(2);
        showViewBg(tab0);
        index = getIntent().getIntExtra("index", 0);
        LogUtils.v("index" + index);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpinionActivity.setData(examineBeanList, index);
                startActivity(new Intent(BusinessApprovalActivity.this, OpinionActivity.class));
            }
        });
    }

    protected void sendRequest(JSONObject jsonObject, int tabIndex) {

        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handler, tabIndex);
    }

    @OnClick({R.id.tab_0, R.id.tab_1, R.id.tab_2, R.id.tab_3, R.id.tab_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tab_0:
                tabIndex = 0;//SerialNo:贷款流水号
                jsonObject = ParamUtils.setParams("applyInfo", "applyInfo", new Object[]{examineBeanList.get(index).getObjectNo()}, 1);
                break;
            case R.id.tab_1:
                tabIndex = 1;//CustomerID:客户编号
                if (listData.get(0).getKeyName().equals("客户编号"))
                    customerId = listData.get(0).getValue();
                jsonObject = ParamUtils.setParams("customerInfo", "customerInfo", new Object[]{customerId}, 1);
                break;
            case R.id.tab_2:
                tabIndex = 2;//SerialNo:贷款流水号
                jsonObject = ParamUtils.setParams("guarantyInfo", "guarantyInfo", new Object[]{examineBeanList.get(index).getObjectNo()}, 1);
                break;
            case R.id.tab_3:
                tabIndex = 3;//SerialNo:贷款流水号，objectType:贷款类型
                jsonObject = ParamUtils.setParams("customerReport", "customerReport", new Object[]{examineBeanList.get(index).getObjectNo(), examineBeanList.get(index).getObjectType()}, 2);
                break;
            case R.id.tab_4:
                tabIndex = 4;//FlowTaskNo:贷款流程树编号,ObjectNo:贷款流水号，ObjectType:贷款类型, PhaseNo:阶段号，FlowNo：流程数据编号，
                jsonObject = ParamUtils.setParams("opinionInfo", "opinionInfo", new Object[]{examineBeanList.get(index).getFlowTaskNo(),
                        examineBeanList.get(index).getObjectNo(), examineBeanList.get(index).getObjectType(), examineBeanList.get(index).getPhaseNo(),
                        examineBeanList.get(index).getFlowNo()}, 5);
                break;
        }
//        toast("tabIndex" + tabIndex);
        showViewBg(view);
        sendRequest(jsonObject, tabIndex);
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

    public static void setData(List<ApprovalBean.ExamineBean> listData) {
        examineBeanList = listData;
    }
}
