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

import com.dysen.common_res.common.adapter.MyRecycleViewAdapter;
import com.pactera.financialmanager.ui.ParentActivity;
import com.dysen.common_res.common.utils.ActivityManagerApplication;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OpinionSubmitActivity extends ParentActivity {

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

    private static List<ApprovalBean.ExamineBean> examineBeanList;
    private static String opinionType, opinionContent;
    private boolean isFlag;
    private String IsFlag;
    private static int index;
    private JSONObject jsonObject;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (uberPgsview != null)
            uberPgsview.setVisibility(View.INVISIBLE);
            if (tvHideData != null)
                tvHideData.setVisibility(View.INVISIBLE);
            LogUtils.v(msg.obj.toString());
            try {
                if (msg.obj != null) {
                    initData(msg);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    private String idea;
    private List<ApprovalBean.OpinionSubmitInfo> listData = new ArrayList<>();
    private String phaseAction = null;

    private void initData(Message msg) throws JSONException {
        switch (msg.what) {
            case -1:
                tvHideData.setVisibility(View.VISIBLE);
                tvHideData.setText(R.string.unNetwork);
                break;
            case -100:
                tvHideData.setVisibility(View.VISIBLE);
                break;
            case 0:
                idea = HttpThread.parseJSON(msg.obj.toString()).getString("Idea");
                //nexNewPhaseAction:给空值即可（流程定制需要次参数，目前无需给值）,UserId:登陆用户id,
                // ftSerialNo:贷款流程树编号,phaseOpinion:("同意"||"不同意")获取上个界面Idea
                jsonObject = ParamUtils.setParams("nextActionOperator", "nextActionOperator", new Object[]{
                        "", ParamUtils.UserIdApproval, examineBeanList.get(index).getFlowTaskNo(), idea}, 4);
                sendRequest(jsonObject, 1);
                break;
            case 1://下一步
                listData = parseList(HttpThread.parseJSON(msg.obj.toString()).getJSONArray("array").toString());
                List<String> list = new ArrayList<>();
                for (int i = 0; i < listData.size(); i++) {
                    list.add(listData.get(i).getActionValue());
                }
                pullLoadMore.setAdapter(new MyRecycleViewAdapter(OpinionSubmitActivity.this, -1, list, new OnItemClickCallback<String>() {
                    @Override
                    public void onClick(View view, String info) {

                        phaseAction = info;
                        setSelectorItemColor(view, R.color.common_tab_bg, R.color.transparent);
                    }

                    @Override
                    public void onLongClick(View view, String info) {

                    }

                    @Override
                    public void onClick(View view, int position, int index) {

                    }
                }));
                break;
            case 2://提交
                startActivity(new Intent(this, ApprovalActivity.class));
                ActivityManagerApplication.finishActivity();
                break;
            case 3://返回上一步
                toast(HttpThread.parseJSON(msg.obj.toString()).getString("OperationMsg"));
                finish();
                break;
        }
    }

    public static List<ApprovalBean.OpinionSubmitInfo> parseList(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<ApprovalBean.OpinionSubmitInfo> list = gson.fromJson(jsonData, new TypeToken<List<ApprovalBean.OpinionSubmitInfo>>() {
            }.getType());

            return list;
        } else
            return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion_submit);
        ButterKnife.bind(this);

        initView();

        sendRequest(jsonObject, 0);
    }

    protected void sendRequest(JSONObject jsonObject, int index) {

        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handler, index);
    }

    private void initView() {

        ActivityManagerApplication.addActivity(this);
        txtTitle.setText("签署意见");
        btnSubmit.setText("保存并提交");
        txtBack.setText("上一步");

        pullLoadMore.setGridLayout(1);
        pullLoadMore.setPushRefreshEnable(false);
        pullLoadMore.setPullRefreshEnable(false);
//        rlvData.setAdapter(new OpiniosAdapter(this, examineBeanList));

        if (isFlag) {
            IsFlag = "true";//查看
            //FTSerialNo:贷款流程树编号,ObjectNo:贷款流水号, IsFlag:"true"
            jsonObject = ParamUtils.setParams("saveOpinion", "saveOpinion", new Object[]{examineBeanList.get(index).getFlowTaskNo(),
                    examineBeanList.get(index).getObjectNo(), "true"}, 3);
        } else {
            IsFlag = "false";//保存
            //FTSerialNo:贷款流程树编号, ObjectNo:贷款流水号, ObjectType:贷款类型，PhaseOpinion:意见内容（签署意见的内容），
//            Idea:选择的意见（给 "同意"||"不同意" 即可），FlowNo：流程数据编号，PhaseNo:阶段号，IsFlag:"false", UserId:登陆用户id

            jsonObject = ParamUtils.setParams("saveOpinion", "saveOpinion", new String[]{examineBeanList.get(index).getFlowTaskNo(),
                    examineBeanList.get(index).getObjectNo(), examineBeanList.get(index).getObjectType(), opinionContent, opinionType
                    , examineBeanList.get(index).getFlowNo(), examineBeanList.get(index).getPhaseNo(), "false", ParamUtils.UserIdApproval}, 9);
        }

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

    @OnClick({R.id.btn_submit, R.id.lay_back})
    public void onViewClicked(View v) {

        switch (v.getId()) {
            case R.id.btn_submit:
                if (phaseAction != null) {
                    //ftSerialNo:贷款流程树编号,phaseAction:下一步提交人（由下一步（获取提交人）获得）,phaseOpinion:选择的意见（"同意"||"不同意"  由签署意见里获得）
                    //        ,nexNewPhaseAction:给空（该参数为流程定制，目前无需给值）,UserId:登陆用户id
                    jsonObject = ParamUtils.setParams("doMySubmit", "doMySubmit", new String[]{examineBeanList.get(index).getFlowTaskNo(),
                            phaseAction, opinionType, "", ParamUtils.UserIdApproval}, 5);
                    sendRequest(jsonObject, 2);
                } else {
                    toast("请选择意见！");
                }
                break;
            case R.id.lay_back:
                //FlowTaskNo:贷款流程树编号,ObjectNo:贷款编号, UserId:登陆用户id
                jsonObject = ParamUtils.setParams("backToLast", "backToLast", new String[]{examineBeanList.get(index).getFlowTaskNo(),
                        examineBeanList.get(index).getObjectNo(), ParamUtils.UserIdApproval}, 3);
                sendRequest(jsonObject, 3);
                break;
        }

    }

    public static void setData(List<ApprovalBean.ExamineBean> listData, int mIndex, String mOpinionType, String mOpinionContent) {
        examineBeanList = listData;
        index = mIndex;
        opinionType = mOpinionType;
        opinionContent = mOpinionContent;
    }
}
