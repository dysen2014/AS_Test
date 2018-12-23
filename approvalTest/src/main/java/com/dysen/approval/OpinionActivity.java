package com.dysen.approval;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.common_res.common.adapter.MyRecycleViewAdapter;
import com.pactera.financialmanager.ui.ParentActivity;
import com.dysen.common_res.common.utils.ActivityManagerApplication;
import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.common_res.common.views.ViewUtils;
import com.dysen.common_res.common.views.uber.UberProgressView;
import com.dysen.pullloadmore_recyclerview.PullLoadMoreRecyclerView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.pactera.financialmanager.R;

import org.json.JSONException;
import org.json.JSONObject;

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
    @Bind(R.id.pull_load_more)
    PullLoadMoreRecyclerView pullLoadMore;
    @Bind(R.id.tv_hide_data)
    TextView tvHideData;
    @Bind(R.id.uber_pgsview)
    UberProgressView uberPgsview;

    @Bind(R.id.opinion_edt)
    EditText opinionEdt;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    private static List<ApprovalBean.ExamineBean> examineBeanList;
    @Bind(R.id.tab_0)
    TextView tab0;
    @Bind(R.id.tab_1)
    TextView tab1;
    @Bind(R.id.alphaIndicator)
    LinearLayout alphaIndicator;
    private String opinionType = "";
    private Intent intent;
    static int index;
    String riskFlag;
    List<ApprovalBean.RiskSignalBean> listData = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == -1) {
                tvHideData.setVisibility(View.VISIBLE);
                tvHideData.setText(R.string.unNetwork);
            }
            if (msg.what == -100) {

                tvHideData.setVisibility(View.VISIBLE);
            }
            if (msg.obj != null) {
                try {
                    riskFlag = HttpThread.parseJSON(msg.obj.toString()).getString("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    listData = parseList(HttpThread.parseJSONWithGson(msg.obj.toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (checkListValid(listData)) {
                    if (riskFlag.equals("Y")) {
                        RiskSianalActivity.setData(listData);
                        startActivity(new Intent(OpinionActivity.this, RiskSianalActivity.class));
                    } else if (riskFlag.equals("N")) {
                        btnSubmit.setEnabled(true);
                    }
                }
            }
        }
    };

    public static List<ApprovalBean.RiskSignalBean> parseList(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<ApprovalBean.RiskSignalBean> list = gson.fromJson(jsonData, new TypeToken<List<ApprovalBean.RiskSignalBean>>() {
            }.getType());

            return list;
        } else
            return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);
        ButterKnife.bind(this);

        initView();
        sendRequest();
    }

    private void sendRequest() {
        //crmRiskSignalTX   SerialNo:贷款流水号,UserId:登陆用户ID
        JSONObject jsonObject = ParamUtils.setParams("crmRiskSignalTX", "crmRiskSignalTX", new Object[]{"BA20170727000003", ParamUtils.UserId}, 2);
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handler);
    }

    private void initView() {

        ActivityManagerApplication.addActivity(this);
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

    public static void setData(List<ApprovalBean.ExamineBean> listData, int mIndex) {
        examineBeanList = listData;
        index = mIndex;
    }

    @OnClick({R.id.btn_submit, R.id.tab_0, R.id.tab_1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                subMit();
                break;
            case R.id.tab_0:
                opinionType = "同意";
                showViewBg(view);
                break;
            case R.id.tab_1:
                opinionType = "不同意";
                showViewBg(view);
                break;
        }
    }

    private void subMit() {

        if (!ViewUtils.getText(opinionEdt).equals("") && !opinionType.equals("")) {
            intent = new Intent(OpinionActivity.this, OpinionSubmitActivity.class);
//            intent.putExtra("index", index);
//            intent.putExtra("opinion_type", opinionType);
//            intent.putExtra("opinion_content", ViewUtils.getText(opinionEdt));
            OpinionSubmitActivity.setData(examineBeanList, index, opinionType, ViewUtils.getText(opinionEdt));
            startActivity(intent);
        } else if (ViewUtils.getText(opinionEdt).equals(""))
            toast("请填写签署意见详情！");
        else if (opinionType.equals(""))
            toast("请填写签署意见！");
    }


    private void showViewBg(View view) {

        tab0.setTextColor(getResources().getColor(R.color.common_left_txt));
        tab1.setTextColor(getResources().getColor(R.color.common_left_txt));
        tab0.setBackgroundResource(R.drawable.common_tab_selector);
        tab1.setBackgroundResource(R.drawable.common_tab_selector);

        if (view.getId() == R.id.tab_0)
            view.setBackgroundResource(R.drawable.shape_bg_red_left);
        else
            view.setBackgroundResource(R.drawable.shape_bg_red_right);
        ((TextView) view).setTextColor(getResources().getColor(R.color.white));
    }
}
