package com.pactera.financialmanager.credit.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.main.service.CheckIn;
import com.pactera.financialmanager.credit.main.service.approval.ApprovalActivity;
import com.pactera.financialmanager.credit.main.service.rate.RateActivity;
import com.pactera.financialmanager.credit.main.service.search.Query;
import com.pactera.financialmanager.credit.main.service.study.StudyActivity;
import com.pactera.financialmanager.ui.DiscoverCardActivity;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.ParentFragment;
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
    private String certcode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_service, null);
        ButterKnife.bind(this, view);

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
                startActivity(new Intent(getContext(), StudyActivity.class));
                break;
            case R.id.txt_approval://审批
                startActivity(new Intent(getContext(), ApprovalActivity.class));
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
}
