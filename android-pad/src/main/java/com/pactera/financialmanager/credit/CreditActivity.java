package com.pactera.financialmanager.credit;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.common_res.common.base.ParentActivity;
import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.common_res.common.utils.ParamUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.bean.CreditLoginBean;
import com.pactera.financialmanager.credit.main.MineFragment;
import com.pactera.financialmanager.credit.main.ReportFragment;
import com.pactera.financialmanager.credit.main.ServiceFragment;
import com.pactera.financialmanager.credit.main.WarnFragment;
import com.pactera.financialmanager.ui.LogoActivity;
import com.yinglan.alphatabs.AlphaTabView;
import com.yinglan.alphatabs.AlphaTabsIndicator;
import com.yinglan.alphatabs.OnTabChangedListner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreditActivity extends ParentActivity {

    @Bind(R.id.tab_0)
    AlphaTabView tab0;
    @Bind(R.id.tab_1)
    AlphaTabView tab1;
    @Bind(R.id.tab_2)
    AlphaTabView tab2;
    @Bind(R.id.tab_3)
    AlphaTabView tab3;
    @Bind(R.id.alphaIndicator)
    AlphaTabsIndicator alphaIndicator;
    @Bind(R.id.lay_back)
    LinearLayout layBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;

    private FragmentManager fragmentManager;
    private WarnFragment warnFragment;//提醒
    private ServiceFragment serviceFragment;//服务
    private ReportFragment reportFragment;//报表
    private MineFragment mineFragment;//我的
    private List<CreditLoginBean> listData = new ArrayList<>();
    JSONObject jsonObject;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == -1){
                toast("信贷登录请求超时");
            }else if (msg.what == -100){

                toast("信贷登录请求返回为空");
            }
            LogUtils.v("what="+msg.what);
            if (msg.obj != null) {
                try {
                    jsonObject = HttpThread.parseJSON(msg.obj.toString());
//                    if (jsonObject.has("return")) {
//                        if (jsonObject.get("return").equals("Y")) {
//                            listData = parseList(jsonObject.toString());
//                            ParamUtils.UserId = jsonObject.get("UserID").toString();
//                            ParamUtils.userName = jsonObject.get("UserName").toString();
//                            ParamUtils.orgId = jsonObject.get("OrgID").toString();
//                            ParamUtils.orgName = jsonObject.get("OrgName").toString();
//                            ParamUtils.approvals = jsonObject.get("Approvals").toString();
//                            LogUtils.d("test", "crmName:" + LogoActivity.user.getUsername() + "\tcrmOrg" + LogoActivity.user.getBrName() +
//                                    "\tcredit id" + UserId + "\torgId" + ParamUtils.orgId);
//                            LogUtils.v(listData.get(0).getOrgID() + "====================" + listData.get(0).getApprovals());
//                            LogUtils.d("test", "crmName:" + LogoActivity.user.getUsername() + "\tcrmOrg" + LogoActivity.user.getBrName() +
//                                    "\tcredit id" + listData.get(0).getUserID());
//                        } else {
//                            ShowDialog(CreditActivity.this, jsonObject.get("return").toString());
//                        }
//                    }
//                    listData = parseList(jsonObject.toString());
                    Gson gson = new Gson();
                    listData = Arrays.asList(gson.fromJson(jsonObject.toString(),CreditLoginBean.class));

//                    CreditLoginBean creditLoginBean = gson.fromJson(jsonObject.toString(), CreditLoginBean.class);
                    ParamUtils.UserId = jsonObject.get("UserID").toString();
                    ParamUtils.userName = jsonObject.get("UserName").toString();
                    ParamUtils.orgId = jsonObject.get("OrgID").toString();
                    ParamUtils.orgName = jsonObject.get("OrgName").toString();
                    ParamUtils.approvals = jsonObject.get("Approvals").toString();
                    LogUtils.d("test", "crmName:" + LogoActivity.user.getUsername() + "\tcrmOrg" + LogoActivity.user.getBrName() +
                            "\tcredit id" + ParamUtils.UserId + "\torgId" + ParamUtils.orgId);
                    LogUtils.v(listData.get(0).getOrgID() + "====================" + listData.get(0).getApprovals());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    protected List<CreditLoginBean> parseList(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<CreditLoginBean> list = gson.fromJson(jsonData, new TypeToken<List<CreditLoginBean>>() {
            }.getType());

            return list;
        } else
            return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_tab);
        ButterKnife.bind(this);
        tab2.setVisibility(View.GONE);
        fragmentManager = getSupportFragmentManager();

        layBack.setVisibility(View.INVISIBLE);
        setFragmentAll(tab0);

        sendRequest(LogoActivity.user.getCERTCODE());
        alphaIndicator.setOnTabChangedListner(new OnTabChangedListner() {
            @Override
            public void onTabSelected(int tabNum) {
                switch (tabNum) {

                    case 0:
                        setFragmentAll(tab0);
                        break;
                    case 1:
                        setFragmentAll(tab1);
                        break;
                    case 2:
                        setFragmentAll(tab2);
                        break;
                    case 3:
                        setFragmentAll(tab3);
                        break;
                }
            }
        });
    }

    /**
     * 信贷登录接口
     *
     * @param id 从CRM登陆 获取到的身份证
     */
    private void sendRequest(String id) {
        JSONObject jsonObject = ParamUtils.setParams("login", "crmlogon", new Object[]{id}, 1);
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, mHandler);
    }

    //先隐藏其他所有的fragment
    private void hideFragments(FragmentTransaction transaction) {

        if (warnFragment != null)
            transaction.hide(warnFragment);

        if (serviceFragment != null)
            transaction.hide(serviceFragment);

        if (reportFragment != null)
            transaction.hide(reportFragment);

        if (mineFragment != null)
            transaction.hide(mineFragment);
    }

    // 提醒
    private void setFragmentWarn() {
        txtTitle.setText(getString(R.string.tab_warn));
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (warnFragment == null) {
            warnFragment = new WarnFragment();
            transaction.add(R.id.frameLayout_tab_credit, warnFragment);
        } else {
            transaction.show(warnFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    //服务
    private void setFragmentService() {
        txtTitle.setText(getString(R.string.tab_service));
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (serviceFragment == null) {
            serviceFragment = new ServiceFragment();
            transaction.add(R.id.frameLayout_tab_credit, serviceFragment);
        } else {
            transaction.show(serviceFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    //报表
    private void setFragmentReport() {
        txtTitle.setText(getString(R.string.tab_report));
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (reportFragment == null) {
            reportFragment = new ReportFragment();
            transaction.add(R.id.frameLayout_tab_credit, reportFragment);
        } else {
            transaction.show(reportFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    //我的
    private void setFragmentMine() {
        txtTitle.setText(getString(R.string.tab_mine));
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (mineFragment == null) {
            mineFragment = new MineFragment();
            transaction.add(R.id.frameLayout_tab_credit, mineFragment);
        } else {
            transaction.show(mineFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    //all
    private void setFragmentAll(View view) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);

        switch (view.getId()) {
            case R.id.tab_0:
                txtTitle.setText(getString(R.string.tab_warn));
                if (warnFragment == null) {
                    warnFragment = new WarnFragment();
                    transaction.add(R.id.frameLayout_tab_credit, warnFragment);
                } else
                    transaction.show(warnFragment);
                break;
            case R.id.tab_1:
                txtTitle.setText(getString(R.string.tab_service));
                if (serviceFragment == null) {
                    serviceFragment = new ServiceFragment();
                    transaction.add(R.id.frameLayout_tab_credit, serviceFragment);
                } else
                    transaction.show(serviceFragment);
                break;
            case R.id.tab_2:
                txtTitle.setText(getString(R.string.tab_report));
                if (reportFragment == null) {
                    reportFragment = new ReportFragment();
                    transaction.add(R.id.frameLayout_tab_credit, reportFragment);
                } else
                    transaction.show(reportFragment);
                break;
            case R.id.tab_3:
                txtTitle.setText(getString(R.string.tab_mine));
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.frameLayout_tab_credit, mineFragment);
                } else
                    transaction.show(mineFragment);
                break;
        }

        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
