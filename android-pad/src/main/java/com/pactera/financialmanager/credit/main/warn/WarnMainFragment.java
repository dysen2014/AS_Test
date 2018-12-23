package com.pactera.financialmanager.credit.main.warn;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.common_res.common.base.ParentFragment;
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
import com.pactera.financialmanager.credit.WarnMainBean;
import com.pactera.financialmanager.credit.common.adapters.WarnMainAdapter;
import com.pactera.financialmanager.credit.common.bean.CreditLoginBean;
import com.pactera.financialmanager.credit.common.bean.warn.WarnCrmParamBean;
import com.pactera.financialmanager.credit.main.WarnActivity;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.model.CreditFwMenu;
import com.pactera.financialmanager.ui.model.CreditTxMenu;
import com.pactera.financialmanager.ui.model.TxMenu;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WarnMainFragment extends ParentFragment {

    @Bind(R.id.txt_back)
    TextView txtBack;
    @Bind(R.id.lay_back)
    LinearLayout layBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_)
    TextView txt;
    @Bind(R.id.tv_hide_data)
    TextView tvHideData;
    @Bind(R.id.uber_pgsview)
    UberProgressView uberPgsview;
    @Bind(R.id.pull_load_more)
    PullLoadMoreRecyclerView pullLoadMore;

    String[] titles = new String[]{"客户关系", "信贷业务"};
    int[] titleColor = new int[]{R.color.warn_khgx, R.color.warn_xd};

    public static String[] funcType = new String[]{"dkyy", "yebz", "yd", "dq", "xykyq", "khjzpj",
            "srgh", "zhyc", "detx", "yqdk", "dqdk", "dhgl", "txdk"};

    List<WarnMainBean.Content> listCrm = new ArrayList<>();
    List<WarnMainBean.Content> listCredit = new ArrayList<>();
    List<WarnMainBean> listData = new ArrayList<>();
    List<WarnMainBean> listDataCrm = new ArrayList<>();
    List<WarnMainBean> listDataCredit = new ArrayList<>();
    List<CreditTxMenu> txMenuList = new ArrayList<>();
    public static List<CreditFwMenu> fwMenuList;
    private List<WarnCrmParamBean.creditCount> creditCountlist = new ArrayList<>();
    List<TxMenu> txMenu;

    private List<CreditLoginBean> listLogin = new ArrayList<>();
    JSONObject jsonObject;
    WarnMainAdapter.MyWarnAdapter myWarnAdapter;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (uberPgsview != null)
                uberPgsview.setVisibility(View.GONE);
            if (tvHideData != null)
                tvHideData.setVisibility(View.GONE);
            if (msg.what == -1) {
                tvHideData.setVisibility(View.VISIBLE);
                tvHideData.setText("信贷登录请求超时");
                toast("信贷登录请求超时");
            } else if (msg.what == -100) {
                tvHideData.setVisibility(View.VISIBLE);
                toast("信贷登录请求返回为空");
            } else {
                LogUtils.v("what=" + msg.what);
                if (msg.obj != null) {
                    try {
                        jsonObject = HttpThread.parseJSON(msg.obj.toString());

                        ParamUtils.UserId = jsonObject.get("UserID").toString();
                        ParamUtils.userName = jsonObject.get("UserName").toString();
                        ParamUtils.orgId = jsonObject.get("OrgID").toString();
                        ParamUtils.orgName = jsonObject.get("OrgName").toString();
                        ParamUtils.returnCredit = jsonObject.get("return").toString();

                        String creditTxMenuStr = jsonObject.get("menu1").toString();
                        txMenuList = parseTxMenuList(creditTxMenuStr);
                        String creditFwMenuStr = jsonObject.get("menu2").toString();
                        fwMenuList = parseCreditFwList(creditFwMenuStr);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            sendRequestCrm(initParam(), paramIndex);
        }
    };

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (uberPgsview != null)
                uberPgsview.setVisibility(View.GONE);
            if (tvHideData != null)
                tvHideData.setVisibility(View.GONE);
            if (msg.what == -1) {
                tvHideData.setVisibility(View.VISIBLE);
                tvHideData.setText("CRM请求超时");
                toast("CRM请求超时");
            } else if (msg.what == -100) {

                tvHideData.setVisibility(View.VISIBLE);
                toast("CRM请求返回为空");
            }
            if (msg.obj != null) {
                JSONObject jsonObject = ((JSONObject) msg.obj);

                try {
                    if (jsonObject.has("retCode")) {
                        String retCode = jsonObject.getString("retCode");
                        if (retCode.equals("2008")) {
                            if (tvHideData != null) {
//                            tvHideData.setVisibility(View.VISIBLE);
//                            tvHideData.setText("CRM请求异常 ");
                            }

                            initData();
                        }
                    }
                    readJson(jsonObject, msg.what);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    };
    private List<WarnCrmParamBean.TitleBean> titleList = new ArrayList<>();

    private void readJson(JSONObject tmpJsonObject, int index) throws JSONException {
        if (index == paramIndex) {
            if (tmpJsonObject.has("group")) {
                JSONArray jsonArray = tmpJsonObject.getJSONArray("group");

                titleList = HttpThread.parseList(jsonArray.toString(), WarnCrmParamBean.TitleBean
                        .class);
                if (titleList != null && titleList.size() > 0) {
                    sendCreditHttpWith();

                }
            }
        }
    }

    /**
     * 信贷count请求
     */
    private void sendCreditHttpWith() {
        JSONObject jsonObject = ParamUtils.setParams("warn", "crmRemindCount",
                new Object[]{ParamUtils.orgId, ParamUtils.UserId}, 9);
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, creditCountHandler);
    }


    private Handler creditCountHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj != null) {
                try {
                    String ss = HttpThread.parseJSONWithGson(msg.obj.toString());
                    creditCountlist = parseCrediCountList(ss);

//                    if (menu.size() <= 0){
//                        xindai_title.setVisibility(View.INVISIBLE);
//                    }
                    initData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    protected List<WarnCrmParamBean.creditCount> parseCrediCountList(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<WarnCrmParamBean.creditCount> list = gson.fromJson(jsonData, new TypeToken<List<WarnCrmParamBean.creditCount>>() {
            }.getType());

            return list;
        } else
            return null;
    }

    private int paramIndex = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_warn_main,
                container, false);
        ButterKnife.bind(this, view);

        sendRequest(ParamUtils.CERTCODE);
        initView();
        return view;
    }

    private String initParam() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("STAID", ParamUtils.staId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String params = ParamUtils.setParamsCrm(ParamUtils.brCode, "01", String
                .valueOf(jsonObject).replace(":", "%3A"), LogoActivity.user.getImei(), "XD011", LogoActivity
                .user.getUserCode());
        return params;
    }

    public void sendRequestCrm(String param, int warnType) {
        if (Tool.haveNet(getActivity())) {
            HttpThread.sendRequestGet(ParamUtils.urlTempCrm, param, handler, warnType);
        } else {
            tvHideData.setVisibility(View.VISIBLE);
            tvHideData.setText(R.string.unNetwork);
        }
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

    private void initData() {

        txMenu = LogoActivity.user.getTx_menu();

        if (txMenu != null){
            int menuSize = txMenu.size();
            for (int i = 0; i < menuSize; i++) {

                try {
                    Field field = R.drawable.class.getField("warn_" + txMenu.get(i)
                            .getMENU_PICTURE_ID());
                    int key = field.getInt(new R.drawable());

                    WarnMainBean.Content warnMainBean = new WarnMainBean.Content(key, txMenu.get(i).getMENU_NAME(),
                            txMenu.get(i).getMENU_MESSAGE(), txMenu.get(i).getMENU_PICTURE_ID(), txMenu.get(i)
                            .getMENU_PICTURE_ID());
                    listCrm.add(warnMainBean);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


            }
        }

        for (int i = 0; i < txMenuList.size(); i++) {

            try {
                Field field = R.drawable.class.getField(
                        "warn_" + txMenuList.get(i).getMENU_PICTURE_ID());
                int key = field.getInt(new R.drawable());

                WarnMainBean.Content warnMainBean = new WarnMainBean.Content(key, txMenuList
                        .get(i).getMENU_NAME(), txMenuList.get(i).getMENU_MESSAGE(), txMenuList
                        .get(i).getMENU_PICTURE_ID(), txMenuList.get(i).getMENU_PICTURE_ID());
                listCredit.add(warnMainBean);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        listDataCrm.add(new WarnMainBean(titles[0], titleColor[0], listCrm));
        listData.add(new WarnMainBean(titles[0], titleColor[0], listCrm));

        if (listCredit.size() > 0){
            listDataCredit.add(new WarnMainBean(titles[1], titleColor[1], listCredit));
            listData.add(new WarnMainBean(titles[1], titleColor[1], listCredit));
        }

        myWarnAdapter = new WarnMainAdapter.MyWarnAdapter(getActivity(), listData, titleList, creditCountlist,
                new
                        OnItemClickCallback<Integer>() {
                            @Override
                            public void onClick(View view, Integer info) {


                            }

                            @Override
                            public void onLongClick(View view, Integer info) {

                            }

                            @Override
                            public void onClick(View view, int position, int index) {
                                LogUtils.d("我进了哪里");

                                if (index == txMenu.size()) {

                                    WarnActivity.setData(true, txMenu.get(position).getMENU_PICTURE_ID(),
                                            txMenu.get(position).getMENU_NAME().toString(), txMenu
                                                    .get(position).getMENU_MESSAGE());
                                    startActivity(new Intent(getContext(), WarnActivity.class));

                                } else if (index == txMenuList.size()) {
                                    WarnActivity.setData(false, txMenuList.get(position).getMENU_PICTURE_ID(),
                                            txMenuList.get(position).getMENU_NAME(), txMenuList
                                                    .get(position).getMENU_MESSAGE());
                                    startActivity(new Intent(getContext(), WarnActivity.class));
                                }
                            }
                        });
        pullLoadMore.setAdapter(myWarnAdapter);
    }

    private void initView() {
        txtTitle.setText(R.string.tab_warn);
        layBack.setVisibility(View.INVISIBLE);
        uberPgsview.setVisibility(View.VISIBLE);

        pullLoadMore.setGridLayout(1);
        pullLoadMore.setPushRefreshEnable(false);
        pullLoadMore.setPullRefreshEnable(false);
    }

    public static String spareOne(int index) {
        String spareOne = WarnMainFragment.funcType[index];
        return spareOne;
    }

    protected List<CreditTxMenu> parseTxMenuList(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<CreditTxMenu> list = gson.fromJson(jsonData, new TypeToken<List<CreditTxMenu>>() {
            }.getType());


            return list;
        } else
            return null;
    }

    protected List<CreditFwMenu> parseCreditFwList(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<CreditFwMenu> list = gson.fromJson(jsonData, new TypeToken<List<CreditFwMenu>>() {
            }.getType());


            return list;
        } else
            return null;
    }
}
