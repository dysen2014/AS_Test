package com.pactera.financialmanager.credit.main;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.common_res.common.views.ViewUtils;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.bean.http.DailyReminder;
import com.pactera.financialmanager.credit.main.warn.AfterManagementWarnFragment;
import com.pactera.financialmanager.credit.main.warn.CreditWarnFragment;
import com.pactera.financialmanager.credit.main.warn.InsuffcientBalanceWarnFragment;
import com.pactera.financialmanager.credit.main.warn.OverDueLoanWarnFragment;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.yinglan.alphatabs.AlphaTabView;
import com.yinglan.alphatabs.AlphaTabsIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * level_a simple {@link Fragment} subclass.
 */
public class WarnActivity extends ParentActivity {

    @Bind(R.id.txt_back)
    TextView txtBack;
    @Bind(R.id.lay_back)
    LinearLayout layBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_)
    TextView txt;
    @Bind(R.id.tab_0)
    AlphaTabView tab0;//贷款预约
    @Bind(R.id.tab_1)
    AlphaTabView tab1;//余额不足
    @Bind(R.id.tab_2)
    AlphaTabView tab2;//资金异动
    @Bind(R.id.tab_3)
    AlphaTabView tab3;//存款/理财到期
    @Bind(R.id.tab_4)
    AlphaTabView tab4;//信用卡逾期
    @Bind(R.id.tab_5)
    AlphaTabView tab5;//客户评级变动
    @Bind(R.id.tab_6)
    AlphaTabView tab6;//生日关怀
    @Bind(R.id.tab_7)
    AlphaTabView tab7;//账户异常
    @Bind(R.id.tab_8)
    AlphaTabView tab8;//大额提醒
    @Bind(R.id.tab_9)
    AlphaTabView tab9;//逾期贷款
    @Bind(R.id.tab_10)
    AlphaTabView tab10;//到期贷款
    @Bind(R.id.tab_11)
    AlphaTabView tab11;//贷后管理
    @Bind(R.id.alphaIndicator)
    AlphaTabsIndicator alphaIndicator;
    @Bind(R.id.fl_warn)
    FrameLayout flWarn;
    @Bind(R.id.rlv_tab)
    RecyclerView rlvTab;
    @Bind(R.id.tab_img_0)
    ImageView tabImg0;
    @Bind(R.id.tab_name_0)
    TextView tabName0;
    @Bind(R.id.ll_lay_0)
    LinearLayout llLay0;
    @Bind(R.id.tab_img_1)
    ImageView tabImg1;
    @Bind(R.id.tab_name_1)
    TextView tabName1;
    @Bind(R.id.ll_lay_1)
    LinearLayout llLay1;
    @Bind(R.id.tab_img_2)
    ImageView tabImg2;
    @Bind(R.id.tab_name_2)
    TextView tabName2;
    @Bind(R.id.ll_lay_2)
    LinearLayout llLay2;
    @Bind(R.id.tab_img_3)
    ImageView tabImg3;
    @Bind(R.id.tab_name_3)
    TextView tabName3;
    @Bind(R.id.ll_lay_3)
    LinearLayout llLay3;
    @Bind(R.id.tab_img_4)
    ImageView tabImg4;
    @Bind(R.id.tab_name_4)
    TextView tabName4;
    @Bind(R.id.ll_lay_4)
    LinearLayout llLay4;
    @Bind(R.id.tab_img_5)
    ImageView tabImg5;
    @Bind(R.id.tab_name_5)
    TextView tabName5;
    @Bind(R.id.ll_lay_5)
    LinearLayout llLay5;
    @Bind(R.id.tab_img_6)
    ImageView tabImg6;
    @Bind(R.id.tab_name_6)
    TextView tabName6;
    @Bind(R.id.ll_lay_6)
    LinearLayout llLay6;
    @Bind(R.id.tab_img_7)
    ImageView tabImg7;
    @Bind(R.id.tab_name_7)
    TextView tabName7;
    @Bind(R.id.ll_lay_7)
    LinearLayout llLay7;
    @Bind(R.id.tab_img_8)
    ImageView tabImg8;
    @Bind(R.id.tab_name_8)
    TextView tabName8;
    @Bind(R.id.ll_lay_8)
    LinearLayout llLay8;
    @Bind(R.id.tab_img_9)
    ImageView tabImg9;
    @Bind(R.id.tab_name_9)
    TextView tabName9;
    @Bind(R.id.ll_lay_9)
    LinearLayout llLay9;
    @Bind(R.id.tab_img_10)
    ImageView tabImg10;
    @Bind(R.id.tab_name_10)
    TextView tabName10;
    @Bind(R.id.ll_lay_10)
    LinearLayout llLay10;

    public static String funcType;
    public static String tabName;
    public static String messInfo;
    public static boolean iscrm;
    private boolean isManager;

    private FragmentManager fragmentManager;
    private InsuffcientBalanceWarnFragment insuffcientBalanceWarnFragment;//余额不足
    private AfterManagementWarnFragment afterManagementWarnFragment;        //待后管理

    private List<DailyReminder> mDailyRemindersListCredit;
    private String transCode;

    public static int index = -1;

    Handler handlerCredit = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == -1) {
                toast(getResources().getString(R.string.request_timeout));
            } else if (msg.what == -100) {

                toast(getResources().getString(R.string.request_nodata));
            } else {
                if (msg.obj != null) {
                    List<DailyReminder> dataList = new ArrayList<>();
                    try {
                        dataList = HttpThread.parseList(HttpThread.parseJSONWithGson((String) msg
                                .obj), DailyReminder.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (checkObjValid(dataList) && dataList.size() > 0)
                        setData(dataList);
                }
            }
        }
    };

    private void setData(List<DailyReminder> list) {
        mDailyRemindersListCredit = list;
        initData();
    }

    private void initData() {

        if (mDailyRemindersListCredit.size() <= 0) {
            tab5.setVisibility(View.INVISIBLE);
            tab6.setVisibility(View.INVISIBLE);
            tab7.setVisibility(View.INVISIBLE);
        } else {
            if (mDailyRemindersListCredit.size() == 3) {


                ViewUtils.setText(this, mDailyRemindersListCredit.get(0).getName(), tab9);
                ViewUtils.setText(this, mDailyRemindersListCredit.get(1).getName(), tab8);
                ViewUtils.setText(this, mDailyRemindersListCredit.get(2).getName(), tab7);

                LogUtils.d(mDailyRemindersListCredit.get(0).getItemNo() + "===" + mDailyRemindersListCredit.get(1).getItemNo()
                        + "===" + mDailyRemindersListCredit.get(2).getItemNo());
                AfterManagementWarnFragment.setItenNo(mDailyRemindersListCredit.get(0).getItemNo());
                OverDueLoanWarnFragment.setItenNo(mDailyRemindersListCredit.get(1).getItemNo());
                CreditWarnFragment.setItenNo(mDailyRemindersListCredit.get(2).getItemNo());
            }
        }
    }

    public static LinearLayout layBacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_warn);
        ButterKnife.bind(this);
        layBacks = layBack;
        layBacks.setVisibility(View.INVISIBLE);
        initView();

        setFragmentAll(iscrm);

        ParamUtils.funcType = funcType;

    }

    protected void sendRequest() {

        JSONObject jsonObject = ParamUtils.setParams("warn", "crmDailyReminders", new Object[]{ParamUtils.UserId}, 1);
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handlerCredit);
    }

    private void initView() {

        fragmentManager = getSupportFragmentManager();
        txtTitle.setText(tabName);

        Intent intent = new Intent();
        String Name = intent.getStringExtra("Name");
        String NameInfo = intent.getStringExtra("NameInfo");
        initTitle(this, Name, true,messInfo);

        txtBack.setText("提醒");
        backActivity(this, layBacks);
    }

    //先隐藏其他所有的fragment
    private void hideFragments(FragmentTransaction transaction) {

        if (insuffcientBalanceWarnFragment != null) {
            transaction.hide(insuffcientBalanceWarnFragment);
        }
        if (afterManagementWarnFragment != null) {
            transaction.hide(afterManagementWarnFragment);
        }

    }

    //all
    private void setFragmentAll(boolean IScrm) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        insuffcientBalanceWarnFragment = null;
//        {"PARENT_MENU_CODE":"提醒","MENU_NAME":"贷后管理","MENU_PICTURE_ID":"dhgl","MENU_CODE":"01010"},
//        {"PARENT_MENU_CODE":"提醒","MENU_NAME":"预警特许","MENU_PICTURE_ID":"yjtx","MENU_CODE":"01015"},
//        {"PARENT_MENU_CODE":"提醒","MENU_NAME":"贷款提醒","MENU_PICTURE_ID":"detx","MENU_CODE":"01020"}
        if (IScrm) {
            // 机构ID
            String staId = LogoActivity.user.getStaId();
            String detailsStation = LogoActivity.user.getDetailsStation();
//            if (staId.equals("ST000901") || staId.equals("ST001101") || staId.equals("ST001103")
//                    || staId.equals("ST001102") || staId.equals("ST001104")) {
            if (detailsStation.contains(staId)){
                transCode = "XD012";
            } else {

                transCode = "XD020";
            }

            if (staId.equals("ST000111") || staId.equals("ST000301")
                    || staId.equals("ST000701") || staId.equals("ST000901")
                    || staId.equals("ST001101") || staId.equals("ST000501")) {
                isManager = true;
            }else {
                isManager = false;
            }

            if (insuffcientBalanceWarnFragment == null) {
                insuffcientBalanceWarnFragment = new InsuffcientBalanceWarnFragment();
                insuffcientBalanceWarnFragment.setCodeType(transCode);
                insuffcientBalanceWarnFragment.setIsManager(isManager);
                transaction.add(R.id.fl_warn, insuffcientBalanceWarnFragment);
            } else {
                transaction.show(insuffcientBalanceWarnFragment);
            }
        } else {

            if (afterManagementWarnFragment == null) {
                afterManagementWarnFragment = new AfterManagementWarnFragment();
                afterManagementWarnFragment.setCodeType(funcType);
                transaction.add(R.id.fl_warn, afterManagementWarnFragment);
            } else {
                transaction.show(afterManagementWarnFragment);
            }

        }

        transaction.commitAllowingStateLoss();
    }


    public static void setData(boolean isCrm, String funcTypes, String names, String messInfos) {
        iscrm = isCrm;
        funcType = funcTypes;
        tabName = names;
        messInfo = messInfos;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
