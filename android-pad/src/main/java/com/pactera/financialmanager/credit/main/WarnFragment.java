package com.pactera.financialmanager.credit.main;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.common_res.common.views.ViewUtils;
import com.dysen.common_res.common.views.uber.UberProgressView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.bean.http.DailyReminder;
import com.pactera.financialmanager.credit.main.warn.AfterWarnFragment;
import com.pactera.financialmanager.credit.main.warn.CustomerApprovalWarnFragment;
import com.pactera.financialmanager.credit.main.warn.CustomerCareWarnFragment;
import com.pactera.financialmanager.credit.main.warn.CustomerDefaultWarnFragment;
import com.pactera.financialmanager.credit.main.warn.CustomerTransactionWarnFragment;
import com.pactera.financialmanager.credit.main.warn.ExpirationWarnFragment;
import com.pactera.financialmanager.credit.main.warn.FinancialNeedsWarnFragment;
import com.pactera.financialmanager.credit.main.warn.InsuffcientBalanceWarnFragment;
import com.pactera.financialmanager.credit.main.warn.OverDueWarnFragment;
import com.pactera.financialmanager.credit.main.warn.ProductExpiresWarnFragment;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HRequest;
import com.yinglan.alphatabs.AlphaTabView;
import com.yinglan.alphatabs.AlphaTabsIndicator;
import com.yinglan.alphatabs.OnTabChangedListner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class WarnFragment extends ParentFragment {

    @Bind(R.id.fl_warn)
    FrameLayout flWorkplacewrokwarn;
    @Bind(R.id.progress_loading)
    ProgressBar progressLoading;
    @Bind(R.id.tab_0)
    AlphaTabView tab0;//客户违约
    @Bind(R.id.tab_1)
    AlphaTabView tab1;//客户异动
    @Bind(R.id.tab_2)
    AlphaTabView tab2;//客户关怀
    @Bind(R.id.tab_3)
    AlphaTabView tab3;//金融需求
    @Bind(R.id.tab_4)
    AlphaTabView tab4;//产品到期
    @Bind(R.id.tab_5)
    AlphaTabView tab5;//贷款逾期
    @Bind(R.id.tab_6)
    AlphaTabView tab6;//贷款到期
    @Bind(R.id.tab_7)
    AlphaTabView tab7;//日常贷后
    @Bind(R.id.tab_8)
    AlphaTabView tab8;//客户审批
    @Bind(R.id.tab_9)
    AlphaTabView tab9;//余额不足
    @Bind(R.id.alphaIndicator)
    AlphaTabsIndicator alphaIndicator;
    @Bind(R.id.dot_num_0)
    ImageView dotNum0;
    @Bind(R.id.dot_num_1)
    ImageView dotNum1;
    @Bind(R.id.dot_indicator)
    LinearLayout dotIndicator;
    @Bind(R.id.uber)
    UberProgressView uber;
//    @Bind(R.id.uber)
//    UberProgressView uber;

    private FragmentManager fragmentManager;

    private CustomerDefaultWarnFragment customerDefaultWarnFragment;//客户违约
    private CustomerTransactionWarnFragment customerTransactionWarnFragment;//客户异动
    private CustomerCareWarnFragment customerCareWarnFragment;//客户关怀
    private FinancialNeedsWarnFragment financialNeedsWarnFragment;//金融需求
    private ProductExpiresWarnFragment productExpiresWarnFragment;//产品到期
    private OverDueWarnFragment overDueWarnFragment;    //逾期提醒
    private ExpirationWarnFragment expirationWarnFragment;    //贷款到期
    private AfterWarnFragment afterWarnFragment;        //待后管理
    private CustomerApprovalWarnFragment customerApprovalWarnFragment;//客户审批
    private InsuffcientBalanceWarnFragment insuffcientBalanceWarnFragment;//余额不足

    private List<DailyReminder> mDailyRemindersList;
    private List<DailyReminder> mDailyRemindersListCrm;
    private List<DailyReminder> mDailyRemindersListCredit;
    private String sPastDue, sMatured, sLater;

    private boolean flagCredit;
    public static int index = -1;

    Handler handlerCredit = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == -1) {
                ShowDialog(getActivity(), "请求超时！");
                if (progressLoading != null)
                    progressLoading.setVisibility(View.GONE);
            }
            if (msg.obj != null) {
                List<DailyReminder> dataList = new ArrayList<>();
                try {
                    dataList = parseList(HttpThread.parseJSONWithGson((String) msg.obj));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (checkObjValid(dataList) || dataList.size() > 0)
                    setData(dataList);
//                setFragmentAfterWarn();
            }
        }
    };

    protected List<DailyReminder> parseList(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<DailyReminder> list = gson.fromJson(jsonData, new TypeToken<List<DailyReminder>>() {
            }.getType());

            return list;
        } else
            return null;
    }

    private void setData(List<DailyReminder> list) {
        mDailyRemindersListCredit = list;
        initData();
    }

    private void initData() {

        if (mDailyRemindersListCredit.size() <= 0) {
            flWorkplacewrokwarn.setVisibility(View.INVISIBLE);
            ShowDialog(getActivity(), "无数据！");
        } else {
            if (mDailyRemindersListCredit.size() == 3) {
                flagCredit = false;
                sPastDue = mDailyRemindersListCredit.get(0).getCount();
                sMatured = mDailyRemindersListCredit.get(1).getCount();
                sLater = mDailyRemindersListCredit.get(2).getCount();
//                setBadgeView(item8Txt, sPastDue);
//                setBadgeView(item7Txt, sMatured);
//                setBadgeView(item6Txt, sLater);
                ViewUtils.setText(getContext(), mDailyRemindersListCredit.get(0).getName(), tab8);
                ViewUtils.setText(getContext(), mDailyRemindersListCredit.get(1).getName(), tab7);
                ViewUtils.setText(getContext(), mDailyRemindersListCredit.get(2).getName(), tab6);

//LogUtils.d(mDailyRemindersListCredit.get(0).getItemNo()+"==="+mDailyRemindersListCredit.get(1).getItemNo()
//        +"==="+mDailyRemindersListCredit.get(2).getItemNo());
                AfterWarnFragment.setItenNo(mDailyRemindersListCredit.get(0).getItemNo());
                ExpirationWarnFragment.setItenNo(mDailyRemindersListCredit.get(1).getItemNo());
                OverDueWarnFragment.setItenNo(mDailyRemindersListCredit.get(2).getItemNo());
            } else {
                flagCredit = true;
//                hideCreditWarn();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_warn, null);
        ButterKnife.bind(this, view);
        setupView(view);

        setFragmentAll(tab0);
        hiddenTabItems();

        sendRequest();
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
                    case 4:
                        setFragmentAll(tab4);
                        break;
                    case 5:
                        setFragmentAll(tab5);
                        break;
                    case 6:
                        setFragmentAll(tab6);
                        break;
                    case 7:
                        setFragmentAll(tab7);
                        break;
                    case 8:
                        setFragmentAll(tab8);
                        break;
                    case 9:
                        setFragmentAll(tab9);
                        break;
                }
            }
        });
        return view;
    }

    protected void sendRequest() {

        JSONObject jsonObject = ParamUtils.setParams("warn", "crmDailyReminders", new Object[]{ParamUtils.UserId}, 1);
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handlerCredit);
    }

    public static void sendRequestCrm(Context context, String param, Handler handler, int warnType) {

        LogUtils.d(warnType + "\tsendRequestCrm\t" + param);
        HRequest request = null;
        request = new HRequest(param, "GET");
        try {
            HConnection.isShowLoadingProcess = true; // 显示loading
            HConnection warnCrmConn = null;
            warnCrmConn = new HConnection(context, request,
                    LogoActivity.user,
                    HConnection.RESPONSE_TYPE_COMPLEX_JSON);
//            warnCrmConn.setIndex(warnType);
            Message msg = new Message();
            msg.what = warnType;
            msg.obj = warnCrmConn;
            handler.sendMessage(msg);
        } catch (Exception e) {
            LogUtils.d("exception", "" + e);
            e.printStackTrace();
        }
    }

    private void setupView(View view) {

        fragmentManager = getFragmentManager();

        tab8.setVisibility(View.GONE);
        tab9.setVisibility(View.GONE);
    }

    /**
     * 根据对公对私来显示不同头选项卡
     */
    private void hiddenTabItems() {

    }

    //先隐藏其他所有的fragment
    private void hideFragments(FragmentTransaction transaction) {
        if (customerApprovalWarnFragment != null) {
            transaction.hide(customerApprovalWarnFragment);
        }
        if (customerDefaultWarnFragment != null) {
            transaction.hide(customerDefaultWarnFragment);
        }
        if (customerTransactionWarnFragment != null) {
            transaction.hide(customerTransactionWarnFragment);
        }
        if (customerCareWarnFragment != null) {
            transaction.hide(customerCareWarnFragment);
        }
        if (financialNeedsWarnFragment != null) {
            transaction.hide(financialNeedsWarnFragment);
        }
        if (insuffcientBalanceWarnFragment != null) {
            transaction.hide(insuffcientBalanceWarnFragment);
        }
        if (productExpiresWarnFragment != null) {
            transaction.hide(productExpiresWarnFragment);
        }
        if (afterWarnFragment != null) {
            transaction.hide(afterWarnFragment);
        }
        if (expirationWarnFragment != null) {
            transaction.hide(expirationWarnFragment);
        }
        if (overDueWarnFragment != null) {
            transaction.hide(overDueWarnFragment);
        }
    }

    //all
    private void setFragmentAll(View view) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);

        switch (view.getId()) {
            case R.id.tab_0://客户违约提醒
                if (customerDefaultWarnFragment == null) {
                    customerDefaultWarnFragment = new CustomerDefaultWarnFragment();
                    transaction.add(R.id.fl_warn, customerDefaultWarnFragment);
                } else {
                    transaction.show(customerDefaultWarnFragment);
                }
                break;
            case R.id.tab_1:// 客户异动提醒
                if (customerTransactionWarnFragment == null) {
                    customerTransactionWarnFragment = new CustomerTransactionWarnFragment();
                    transaction.add(R.id.fl_warn, customerTransactionWarnFragment);
                } else {
                    transaction.show(customerTransactionWarnFragment);
                }
                break;
            case R.id.tab_2://客户关怀提醒
                if (customerCareWarnFragment == null) {
                    customerCareWarnFragment = new CustomerCareWarnFragment();
                    transaction.add(R.id.fl_warn, customerCareWarnFragment);
                } else {
                    transaction.show(afterWarnFragment);
                }
                break;
            case R.id.tab_3://金融需求提醒
                if (financialNeedsWarnFragment == null) {
                    financialNeedsWarnFragment = new FinancialNeedsWarnFragment();
                    transaction.add(R.id.fl_warn, financialNeedsWarnFragment);
                } else {
                    transaction.show(afterWarnFragment);
                }
                break;
            case R.id.tab_4://产品到期提醒
                if (productExpiresWarnFragment == null) {
                    productExpiresWarnFragment = new ProductExpiresWarnFragment();
                    transaction.add(R.id.fl_warn, productExpiresWarnFragment);
                } else {
                    transaction.show(productExpiresWarnFragment);
                }
                break;
            case R.id.tab_5:// 逾期提醒
                if (overDueWarnFragment == null) {
                    overDueWarnFragment = new OverDueWarnFragment();
                    transaction.add(R.id.fl_warn, overDueWarnFragment);
                } else {
                    transaction.show(overDueWarnFragment);
                }
                break;
            case R.id.tab_6://到期提醒
                if (expirationWarnFragment == null) {
                    expirationWarnFragment = new ExpirationWarnFragment();
                    transaction.add(R.id.fl_warn, expirationWarnFragment);
                } else {
                    transaction.show(expirationWarnFragment);
                }
                break;
            case R.id.tab_7://贷后提醒
                if (afterWarnFragment == null) {
                    afterWarnFragment = new AfterWarnFragment();
                    transaction.add(R.id.fl_warn, afterWarnFragment);
                } else {
                    transaction.show(afterWarnFragment);
                }
                break;
            case R.id.tab_8:// 客户审批提醒
                if (customerApprovalWarnFragment == null) {
                    customerApprovalWarnFragment = new CustomerApprovalWarnFragment();
                    transaction.add(R.id.fl_warn, customerApprovalWarnFragment);
                } else {
                    transaction.show(customerApprovalWarnFragment);
                }
                break;
            case R.id.tab_9://余额不足提醒
                if (insuffcientBalanceWarnFragment == null) {
                    insuffcientBalanceWarnFragment = new InsuffcientBalanceWarnFragment();
                    transaction.add(R.id.fl_warn, insuffcientBalanceWarnFragment);
                } else {
                    transaction.show(insuffcientBalanceWarnFragment);
                }
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
