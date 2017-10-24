package com.pactera.financialmanager.credit;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

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
import com.pactera.financialmanager.ui.ParentActivity;
import com.yinglan.alphatabs.AlphaTabView;
import com.yinglan.alphatabs.AlphaTabsIndicator;
import com.yinglan.alphatabs.OnTabChangedListner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.dysen.common_res.common.utils.HttpThread.parseJSON;
import static com.dysen.common_res.common.utils.ParamUtils.UserId;

public class CreditActivity extends ParentActivity {

    @Bind(R.id.txt_tab)
    TextView txtTab;
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

    private FragmentManager fragmentManager;
    private WarnFragment warnFragment;//提醒
    private ServiceFragment serviceFragment;//服务
    private ReportFragment reportFragment;//报表
    private MineFragment mineFragment;//我的
    private List<CreditLoginBean> dataList;
    JSONObject jsonObject;

    View[] views = new AlphaTabView[]{tab1, tab1, tab2, tab3};

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj != null) {

                try {
                    if (parseJSON((String) msg.obj).has("return")) {
                        if (parseJSON((String) msg.obj).get("return").equals("Y")) {
                            dataList = parseList(parseJSON((String) msg.obj).toString());
                            LogUtils.d("test", "crmName:" + LogoActivity.user.getUsername() + "\tcrmOrg" + LogoActivity.user.getBrName() +
                                    "\tcredit id" + dataList.get(0).getUserID());
                        } else {
//                            ShowDialog(CreditActivity.this, parseJSON((String) msg.obj).get("return").toString());
                        }
                    } else {
//                        dataList = parseList(HttpThread.parseJSON((String) msg.obj).toString());
                        jsonObject = HttpThread.parseJSON((String) msg.obj);
                        ParamUtils.UserId = (String) jsonObject.get("UserID");
                        ParamUtils.userName = (String) jsonObject.get("UserName");
                        ParamUtils.orgId = (String) jsonObject.get("OrgID");
                        ParamUtils.orgName = (String) jsonObject.get("OrgName");
                        ParamUtils.approvals = (String) jsonObject.get("Approvals");
                        LogUtils.d("test", "crmName:" + LogoActivity.user.getUsername() + "\tcrmOrg" + LogoActivity.user.getBrName() +
                                "\tcredit id" + UserId);
                    }
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
        txtTab.setText(getString(R.string.tab_warn));
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
        txtTab.setText(getString(R.string.tab_service));
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
        txtTab.setText(getString(R.string.tab_report));
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
        txtTab.setText(getString(R.string.tab_mine));
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
                txtTab.setText(getString(R.string.tab_warn));
                if (warnFragment == null) {
                    warnFragment = new WarnFragment();
                    transaction.add(R.id.frameLayout_tab_credit, warnFragment);
                } else
                    transaction.show(warnFragment);
                break;
            case R.id.tab_1:
                txtTab.setText(getString(R.string.tab_service));
                if (serviceFragment == null) {
                    serviceFragment = new ServiceFragment();
                    transaction.add(R.id.frameLayout_tab_credit, serviceFragment);
                } else
                    transaction.show(serviceFragment);
                break;
            case R.id.tab_2:
                txtTab.setText(getString(R.string.tab_report));
                if (reportFragment == null) {
                    reportFragment = new ReportFragment();
                    transaction.add(R.id.frameLayout_tab_credit, reportFragment);
                } else
                    transaction.show(reportFragment);
                break;
            case R.id.tab_3:
                txtTab.setText(getString(R.string.tab_mine));
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
