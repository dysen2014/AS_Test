package com.pactera.financialmanager.credit;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.bean.CreditLoginBean;
import com.pactera.financialmanager.credit.main.MineFragment;
import com.pactera.financialmanager.credit.main.ReportFragment;
import com.pactera.financialmanager.credit.main.ServiceFragment;
import com.pactera.financialmanager.credit.main.warn.WarnMainFragment;
import com.yinglan.alphatabs.AlphaTabView;
import com.yinglan.alphatabs.AlphaTabsIndicator;
import com.yinglan.alphatabs.OnTabChangedListner;

import org.json.JSONObject;

import java.util.ArrayList;
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
    @Bind(R.id.alphaIndicator)
    AlphaTabsIndicator alphaIndicator;
    @Bind(R.id.lay_back)
    LinearLayout layBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;

    private FragmentManager fragmentManager;
    private WarnMainFragment warnMainFragment;//提醒首页
    private ServiceFragment serviceFragment;//服务
    private ReportFragment reportFragment;//报表
    private MineFragment mineFragment;//我的
    private List<CreditLoginBean> listData = new ArrayList<>();
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment_tab);
        ButterKnife.bind(this);

        initTitle(this, "", false,"");

        if (layBack != null) {
            layBack.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    finish();
                }
            });
        }
        fragmentManager = getSupportFragmentManager();

        layBack.setVisibility(View.INVISIBLE);
        setFragmentAll(tab0);

//        sendRequest(LogoActivity.user.getCERTCODE());
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
                }
            }
        });
    }

    //先隐藏其他所有的fragment
    private void hideFragments(FragmentTransaction transaction) {

        if (warnMainFragment != null)
            transaction.hide(warnMainFragment);

        if (serviceFragment != null)
            transaction.hide(serviceFragment);

        if (mineFragment != null)
            transaction.hide(mineFragment);
    }

    // 提醒
    private void setFragmentWarn() {
        txtTitle.setText(getString(R.string.tab_warn));
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (warnMainFragment == null) {
            warnMainFragment = new WarnMainFragment();
            transaction.add(R.id.frameLayout_tab_credit, warnMainFragment);
        } else {
            transaction.show(warnMainFragment);
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
                if (warnMainFragment == null) {
                    warnMainFragment = new WarnMainFragment();
                    transaction.add(R.id.frameLayout_tab_credit, warnMainFragment);
                } else
                    transaction.show(warnMainFragment);
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
