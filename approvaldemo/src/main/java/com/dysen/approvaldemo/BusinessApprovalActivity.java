package com.dysen.approvaldemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dysen.common_res.common.base.ParentActivity;
import com.dysen.common_res.common.utils.CallAndSMS;
import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.utils.ParamUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.yinglan.alphatabs.AlphaTabView;
import com.yinglan.alphatabs.AlphaTabsIndicator;
import com.yinglan.alphatabs.OnTabChangedListner;

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

    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.tab_0)
    AlphaTabView tab0;
    @Bind(R.id.tab_1)
    AlphaTabView tab1;
    @Bind(R.id.tab_2)
    AlphaTabView tab2;
    @Bind(R.id.tab_3)
    AlphaTabView tab3;
    @Bind(R.id.tab_4)
    AlphaTabView tab4;
    @Bind(R.id.alphaIndicator)
    AlphaTabsIndicator alphaIndicator;
    @Bind(R.id.rlv_data)
    RecyclerView rlvData;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    AlphaTabView[] tabViews = new AlphaTabView[]{tab0, tab1, tab2, tab3, tab4};
    String[] tabNames = new String[]{"申请信息", "客户信息", "担保信息", "调查报告", "处理意见"};
    JSONObject jsonObject;
    private static int tabIndex, index;
    List<String> data = new ArrayList<>();

    private static List<ApprovalBean.ExamineBean> examineBeanList = new ArrayList<>();
    private List<ApprovalBean.ApplyInfoBean> listData = new ArrayList<>();
    private ApprovalBusinessAdapter approvalBusinessAdapter;

    private List<ApprovalBean.GuarantyInfoBean> listDataGuaranty = new ArrayList<>();
    private List<ApprovalBean.OpinionInfoBean> listDataOpinion = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == -1) {

            } else {
                try {
                    initData(msg.what, msg.obj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private JSONArray jsonArray;
    private String customerId;

    private void initData(int tabIndex, String jsonStr) throws JSONException {
        switch (tabIndex) {

            case 0:
            case 1:
                    jsonArray = HttpThread.parseJSON(jsonStr).getJSONArray("array");
                    listData = parseListApplyInfo(jsonArray.getJSONObject(0).getJSONArray("groupColArray").toString());
                    rlvData.setAdapter(new ApprovalBusinessAdapter(this, listData, new OnItemClickCallback<Integer>() {
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
                try {
                    listDataGuaranty = parseListGuarantyInfo(HttpThread.parseJSONWithGson(jsonStr));
                    rlvData.setAdapter(new BusinessGuarantyAdapter(this, listDataGuaranty));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
//                rlvData.setAdapter(new MyRecycleViewAdapter(BusinessApprovalActivity.this, -1,  data));
                rlvData.setAdapter(new ApprovalOpinionAdapter(BusinessApprovalActivity.this,  listDataOpinion, new OnItemClickCallback<Integer>() {
                    @Override
                    public void onClick(View view, Integer info) {
                    }

                    @Override
                    public void onLongClick(View view, Integer info) {

                    }
                }));
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

        index = getIntent().getIntExtra("index", 0);
        LogUtils.v("index" + index);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rlvData.setLayoutManager(layoutManager);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("签署意见" + tabNames[tabIndex]);
                OpinionActivity.setData(examineBeanList, index);
                startActivity(new Intent(BusinessApprovalActivity.this, OpinionActivity.class));
            }
        });

        alphaIndicator.setOnTabChangedListner(new OnTabChangedListner() {
            @Override
            public void onTabSelected(int tabNum) {
                switch (tabNum) {
                    case 0:
                        tabIndex = 0;//SerialNo:贷款流水号
                        jsonObject = ParamUtils.setParams("applyInfo", "applyInfo", new Object[]{examineBeanList.get(index).getObjectNo()}, 1);
                        break;
                    case 1:
                        tabIndex = 1;//CustomerID:客户编号
                        if (listData.get(0).getKeyName().equals("客户编号"))
                            customerId = listData.get(0).getValue();
                        jsonObject = ParamUtils.setParams("customerInfo", "customerInfo", new Object[]{customerId}, 1);
                        break;
                    case 2:
                        tabIndex = 2;//SerialNo:贷款流水号
                        jsonObject = ParamUtils.setParams("guarantyInfo", "guarantyInfo", new Object[]{examineBeanList.get(index).getObjectNo()}, 1);
                        break;
                    case 3:
                        tabIndex = 3;//SerialNo:贷款流水号，objectType:贷款类型
                        jsonObject = ParamUtils.setParams("customerReport", "customerReport", new Object[]{examineBeanList.get(index).getObjectNo(), examineBeanList.get(index).getObjectType()}, 2);
                        break;
                    case 4:
                        tabIndex = 4;//FlowTaskNo:贷款流程树编号,ObjectNo:贷款流水号，ObjectType:贷款类型, PhaseNo:阶段号，FlowNo：流程数据编号，

                        jsonObject = ParamUtils.setParams("opinionInfo", "opinionInfo", new Object[]{examineBeanList.get(index).getFlowTaskNo(),
                                examineBeanList.get(index).getObjectNo(), examineBeanList.get(index).getObjectType(), examineBeanList.get(index).getPhaseNo(),
                                examineBeanList.get(index).getFlowNo()}, 5);
                        break;
                }
                toast("tabIndex" + tabIndex);
                sendRequest(jsonObject, tabIndex);
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
                jsonObject = ParamUtils.setParams("customerInfo", "customerInfo", new Object[]{listData.get(0).getValue()}, 1);
                break;
            case R.id.tab_2:
                tabIndex = 2;
                jsonObject = ParamUtils.setParams("examine", "examine", new Object[]{"credit", "B129103", "Y", "", curPage, ParamUtils.pageSize}, 1);
                break;
            case R.id.tab_3:
                tabIndex = 3;
                jsonObject = ParamUtils.setParams("examine", "examine", new Object[]{"credit", "B129103", "Y", "", curPage, ParamUtils.pageSize}, 1);
                break;
            case R.id.tab_4:
                tabIndex = 4;
                jsonObject = ParamUtils.setParams("examine", "examine", new Object[]{"credit", "B129103", "Y", "", curPage, ParamUtils.pageSize}, 1);
                break;
        }
//        toast("tabIndex" + tabIndex);
        sendRequest(jsonObject, tabIndex);
    }

    public static void setData(List<ApprovalBean.ExamineBean> listData) {
        examineBeanList = listData;
    }
}
