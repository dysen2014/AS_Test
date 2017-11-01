package com.pactera.financialmanager.credit.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.common_res.common.base.ParentFragment;
import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.common_res.common.views.ViewUtils;
import com.example.zhouwei.library.CustomPopWindow;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.main.service.CheckIn;
import com.pactera.financialmanager.credit.main.service.approval.ApprovalActivity;
import com.pactera.financialmanager.credit.main.service.rate.RateActivity;
import com.pactera.financialmanager.credit.main.service.search.Query;
import com.pactera.financialmanager.credit.main.service.study.LearningBookcaseActivity;
import com.pactera.financialmanager.ui.DiscoverCardActivity;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.QuerycusActivity;
import com.pactera.financialmanager.ui.ReturnCusActivity;
import com.pactera.financialmanager.ui.commontool.BranchMapActivity;
import com.pactera.financialmanager.ui.commontool.CustomerMapActivity;
import com.pactera.financialmanager.ui.customermanager.CusArchivingStepOne;
import com.pactera.financialmanager.ui.customermanager.MyscusActivity;
import com.pactera.financialmanager.ui.fragment.SigninRecordActivity;
import com.pactera.financialmanager.ui.hallfirst.HallFirstActivity;
import com.pactera.financialmanager.ui.productcenter.HotProductActivity;
import com.pactera.financialmanager.ui.productcenter.PromotionsActivity;
import com.pactera.financialmanager.util.LimitsUtil;
import com.pactera.financialmanager.util.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dysen on 2017/8/16.
 */

public class ServiceFragment extends ParentFragment {

    @Bind(R.id.txt_customerProfile)
    TextView txtCustomerProfile;
    @Bind(R.id.txt_mineProfile)
    TextView txtMineProfile;
    @Bind(R.id.txt_customerDistribution)
    TextView txtCustomerDistribution;
    @Bind(R.id.txt_customerData)
    TextView txtCustomerData;
    @Bind(R.id.txt_customerSearch)
    TextView txtCustomerSearch;
    @Bind(R.id.txt_serviceRecord)
    TextView txtServiceRecord;
    @Bind(R.id.txt_hallManagement)
    TextView txtHallManagement;
    @Bind(R.id.txt_promotions)
    TextView txtPromotions;
    @Bind(R.id.txt_hotProducts)
    TextView txtHotProducts;
    @Bind(R.id.txt_creditIncoming)
    TextView txtCreditIncoming;
    @Bind(R.id.txt_creditSearch)
    TextView txtCreditSearch;
    @Bind(R.id.txt_interestRateCalculation)
    TextView txtInterestRateCalculation;
    @Bind(R.id.txt_study)
    TextView txtStudy;
    @Bind(R.id.txt_outletsMap)
    TextView txtOutletsMap;
    @Bind(R.id.txt_checkIn)
    TextView txtCheckIn;
    @Bind(R.id.txt_approval)
    TextView txtApproval;
    @Bind(R.id.lay_back)
    LinearLayout layBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_checkInRecord)
    TextView txtCheckInRecord;
    private String certcode;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            ApprovalsLogon:审批密码验证(Y或者N)\
            if (msg.obj != null) {
                try {
                    String loginState = HttpThread.parseJSON(msg.obj.toString()).getString("ApprovalsLogon");
                    if (loginState.equals("Y"))

                        startActivity(new Intent(getActivity(), ApprovalActivity.class));
                    else if (loginState.equals("N"))
                        toast("登录失败!");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_service, null);
        ButterKnife.bind(this, view);

        txtTitle.setText("服务");
        layBack.setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(getContext());
    }

    @OnClick({R.id.txt_customerProfile, R.id.txt_mineProfile, R.id.txt_customerDistribution, R.id.txt_customerData, R.id.txt_customerSearch,
            R.id.txt_serviceRecord, R.id.txt_hallManagement, R.id.txt_promotions, R.id.txt_hotProducts, R.id.txt_creditIncoming,
            R.id.txt_creditSearch, R.id.txt_interestRateCalculation, R.id.txt_study, R.id.txt_approval, R.id.txt_outletsMap, R.id.txt_checkIn})
    public void onViewClicked(View view) {

        boolean isRight = false;
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.txt_customerProfile://客户建档
                isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T0302);
                if (isRight) {
                    intent.setClass(getContext(), CusArchivingStepOne.class);
                    startActivity(intent);
                }
                break;
            case R.id.txt_mineProfile://我的建档
                isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T0301);
                if (isRight) {
                    intent.setClass(getContext(), MyscusActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.txt_customerDistribution://客户分布
                isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T0601);
                if (isRight) {
                    intent.setClass(getContext(), CustomerMapActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.txt_customerData://客户数据
                break;
            case R.id.txt_customerSearch://客户查询
                isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T02);
                if (isRight) {
                    intent.setClass(getContext(), QuerycusActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.txt_serviceRecord://服务记录
                isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T04);
                if (isRight) {
                    intent.setClass(getContext(), ReturnCusActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.txt_hallManagement://厅堂管理
                isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T01);
                if (isRight) {
                    intent.setClass(getContext(), HallFirstActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.txt_promotions://优惠活动
                isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T0801);
                if (isRight) {
                    intent.setClass(getContext(), PromotionsActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.txt_hotProducts://热销产品
                isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T0802);
                if (isRight) {
                    intent.setClass(getContext(), HotProductActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.txt_creditIncoming://信用卡进件
                certcode = LogoActivity.user.getCERTCODE();
                isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T07);
                if (isRight) {
                    intent.setClass(getContext(), DiscoverCardActivity.class);
                    intent.putExtra("certcode", certcode);
                    startActivity(intent);
                }
                break;
            case R.id.txt_creditSearch://信贷查询
                startActivity(new Intent(getContext(), Query.class));
                break;
            case R.id.txt_interestRateCalculation://利率测算
                startActivity(new Intent(getContext(), RateActivity.class));
                break;
            case R.id.txt_study://学习园地
                startActivity(new Intent(getContext(), LearningBookcaseActivity.class));
                break;
            case R.id.txt_approval://审批
                if (ParamUtils.approvals.equals("Y")) {
                    showPopListView(view);
                } else if (ParamUtils.approvals.equals("N")) {
                    toast("您,暂无审批权限!");
                }
                break;
            case R.id.txt_outletsMap://网点查询
                isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T0602);
                if (isRight) {
                    intent.setClass(getContext(), BranchMapActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.txt_checkIn://签到
                isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T0501, true);
//                if(isRight) {
                startActivity(new Intent(getContext(), CheckIn.class));
//                }
                break;
            case R.id.txt_checkInRecord://签到记录
                isRight = LimitsUtil.checkUserLimit(getContext(), LimitsUtil.T0502, false);
                if (!isRight) {
                    startActivity(new Intent(getContext(), SigninRecordActivity.class));
                }
                break;
        }
        setSelectorItemColor(view, R.drawable.service_selected_bg, R.drawable.service_normal_bg);
    }

    private void showPopListView(View view) {

        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_login, null);

        TextView approvalTitleName = (TextView) contentView.findViewById(R.id.approval_title_name);
        final TextView approvalName = (TextView) contentView.findViewById(R.id.approval_name);
        final EditText approvalPwd = (EditText) contentView.findViewById(R.id.approval_pwd);
        Button btnCancel = (Button) contentView.findViewById(R.id.btn_cancel);
        Button btnOk = (Button) contentView.findViewById(R.id.btn_ok);

        approvalName.setText("B129103");
        final String name = ViewUtils.getText(approvalName);

        //创建并显示popWindow
        final CustomPopWindow mListPopWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)//显示大小
                .enableBackgroundDark(true)
                .setBgDarkAlpha(0.6f)
                .create()
                .showAtLocation(view, Gravity.CENTER, 200, 200);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListPopWindow.dissmiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pwd = ViewUtils.getText(approvalPwd);
                approvalName.setText("帐号：\t" + "B129103");
                if (!name.equals("") || !pwd.equals("")) {
                    sendRequest(name, pwd);
                    toast("审批登录请求中！");
                } else {
                    toast("帐号，密码不能为空");
                }
            }
        });
    }

    private void sendRequest(String name, String pwd) {
        LogUtils.v(name+"-------name----------pwd-------"+pwd);
        //{"deviceType":"Android","RequestParams":{UserID:登陆用户, PassWord:登陆密码}}
        JSONObject jsonObject = ParamUtils.setParams("login", "crmApprovalsLogon", new Object[]{name, pwd}, 2);
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, mHandler);
    }
}
