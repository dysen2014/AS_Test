package com.pactera.financialmanager.credit.main.warn;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.common_res.common.views.TextViewMarquee;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.adapters.DataAdapter;
import com.pactera.financialmanager.credit.common.adapters.DataAdapterNew;
import com.pactera.financialmanager.credit.common.adapters.LeftAdapter;
import com.pactera.financialmanager.credit.common.adapters.TopDataAdapter;
import com.pactera.financialmanager.credit.common.bean.InitData;
import com.pactera.financialmanager.credit.common.bean.http.BeanHeader;
import com.pactera.financialmanager.credit.common.bean.warn.WarnCrmInfoBean;
import com.pactera.financialmanager.credit.common.bean.warn.WarnCrmParamBean;
import com.pactera.financialmanager.credit.common.views.SyncHorizontalScrollView;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 产品到期提醒
 * Created by dysen on 2017/8/17.
 */

public class ProductExpiresWarnFragment extends ParentFragment implements BaseRefreshListener {

    @Bind(R.id.common_txt_rlv2)
    RecyclerView commonTxtRlv2;
    @Bind(R.id.common_txt_0)
    TextView commonTxt0;
    @Bind(R.id.common_txt_1)
    TextView commonTxt1;
    @Bind(R.id.common_txt_2)
    TextView commonTxt2;
    @Bind(R.id.common_txt_3)
    TextView commonTxt3;
    @Bind(R.id.common_txt_ll_0)
    LinearLayout commonTxtLl0;
    @Bind(R.id.common_txt_00)
    TextView commonTxt00;
    @Bind(R.id.common_txt_01)
    TextView commonTxt01;
    @Bind(R.id.common_txt_02)
    TextView commonTxt02;
    @Bind(R.id.common_txt_03)
    TextView commonTxt03;
    @Bind(R.id.common_txt_04)
    TextView commonTxt04;
    @Bind(R.id.common_txt_ll_00)
    LinearLayout commonTxtLl00;
    @Bind(R.id.left_top_item_tv)
    TextViewMarquee leftTopItemTv;
    @Bind(R.id.top_data)
    RecyclerView topData;
    @Bind(R.id.header_horizontal)
    SyncHorizontalScrollView headerHorizontal;
    @Bind(R.id.left_data)
    RecyclerView leftData;
    @Bind(R.id.lv_data)
    RecyclerView lvData;
    @Bind(R.id.data_horizontal)
    SyncHorizontalScrollView dataHorizontal;
    @Bind(R.id.ptr_layout)
    PullToRefreshLayout ptrLayout;
    @Bind(R.id.progress_loading)
    ProgressBar progressLoading;
    @Bind(R.id.left_top_item_lay)
    LinearLayout leftTopItemLay;
    @Bind(R.id.tv_hide_data)
    TextView tvHideData;

    private TopDataAdapter mTopDataAdapter;
    private DataAdapter mDataAdapter;
    private LeftAdapter mLeftAdapter;

    private List<String> mListLeft = new ArrayList<>();
    private List<String> mListTop = new ArrayList<>();
    private List<List<Object>> mListData = new ArrayList<>();
    private List<WarnCrmParamBean> warnCrmParamList = new ArrayList<>();
    private List<WarnCrmInfoBean.ProductInfoBean.DQCK> warnCrmInfoListDQCK = new ArrayList<>();
    private List<WarnCrmInfoBean.ProductInfoBean.LCCP> warnCrmInfoListLCCP = new ArrayList<>();
    private List<WarnCrmInfoBean.ProductInfoBean.DKCP> warnCrmInfoListDKCP = new ArrayList<>();
    private List<BeanHeader> warnCrmInfoHeaderList = new ArrayList<>();

    private int index1 = 1;
    private int itemIndex = 0;
    private int itemIndexType = 11;
    final private int paramIndex = 1;
    final private int infoIndex1 = 11;
    final private int infoIndex2 = 12;
    final private int infoIndex3 = 13;

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            progressLoading.setVisibility(View.GONE);
            if (msg.what == -1) {
//                    toast("请求超时！");
                tvHideData.setVisibility(View.VISIBLE);
                tvHideData.setText("请求超时！");
            } else {
                if (msg.obj != null) {
                    JSONObject jsonObject = ((JSONObject) msg.obj);
                    try {
                        readJson(jsonObject, msg.what);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };
    private TextView textView;
    private String itenName0, itenName1, itenName2;
    private String count;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_default_warn, container, false);
        ButterKnife.bind(this, view);
        sendRequestCrm(initParam("cpdq"), paramIndex);
        return view;
    }

    private String initParam(String spareOne) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("STAID", LogoActivity.user.getStaId());

//            LogUtils.d(jsonObject+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+jsonObject.toString().replace(":", "%3A")+"============="+String.valueOf(jsonObject));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String params = ParamUtils.setParamsCrm(LogoActivity.user.getBrCode(), "01", String.valueOf(jsonObject).replace(":", "%3A"), "111111", spareOne, "XD002", ParamUtils.UserId);
        return params;
    }

    private String initParam(String offset, String spareOne, String spareTwo) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("STAID", LogoActivity.user.getStaId());

//            LogUtils.d(jsonObject+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+jsonObject.toString().replace(":", "%3A")+"============="+String.valueOf(jsonObject));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String params = ParamUtils.setParamsCrm(LogoActivity.user.getBrCode(), "01", String.valueOf(jsonObject).replace(":", "%3A"), offset, "111111", spareOne, spareTwo, "XD003", ParamUtils.UserId);
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

    private void readJson(JSONObject tmpJsonObject, int index) throws JSONException {
        if (index == paramIndex) {
            if (tmpJsonObject.has("group")) {
                JSONArray jsonArray = tmpJsonObject.getJSONArray("group");

                warnCrmParamList = paramList(jsonArray.toString());
                if (warnCrmParamList != null && warnCrmParamList.size() > 0) {
                    if (warnCrmParamList.size() == 3) {
                        commonTxt0.setVisibility(View.VISIBLE);
                        commonTxt1.setVisibility(View.VISIBLE);
                        commonTxt2.setVisibility(View.VISIBLE);
                        count = warnCrmParamList.get(itemIndex).getRows();
                        commonTxt0.setText(warnCrmParamList.get(0).getTITLENAME()+"("+ warnCrmParamList.get(0).getRows()+")");
                        commonTxt1.setText(warnCrmParamList.get(1).getTITLENAME()+"("+ warnCrmParamList.get(1).getRows()+")");
                        commonTxt2.setText(warnCrmParamList.get(2).getTITLENAME()+"("+ warnCrmParamList.get(2).getRows()+")");
                        itenName0 = warnCrmParamList.get(0).getSUBTYPE();
                        itenName1 = warnCrmParamList.get(1).getSUBTYPE();
                        itenName2 = warnCrmParamList.get(2).getSUBTYPE();
                        setSelectView(commonTxt0, itemIndex, infoIndex1);
                    }
                }
            }
        } else if (index == infoIndex1) {
            if (tmpJsonObject.has("header") || tmpJsonObject.has("group")) {
                JSONArray jsonArrayHeader = tmpJsonObject.getJSONArray("header");
                JSONArray jsonArray = tmpJsonObject.getJSONArray("group");
                warnCrmInfoListDQCK = infoListDQCK(jsonArray.toString());
                warnCrmInfoHeaderList = infoHeaderList(jsonArrayHeader.toString());
                if (checkObjValid(warnCrmInfoHeaderList) && warnCrmInfoHeaderList.size() > 0
                        || checkObjValid(warnCrmInfoListDQCK) && warnCrmInfoListDQCK.size() > 0) {
                    initView();
                } else {
                    tvHideData.setVisibility(View.VISIBLE);
                }
            }
        } else if (index == infoIndex2) {
            if (tmpJsonObject.has("header") || tmpJsonObject.has("group")) {
                JSONArray jsonArrayHeader = tmpJsonObject.getJSONArray("header");
                JSONArray jsonArray = tmpJsonObject.getJSONArray("group");
                warnCrmInfoListLCCP = infoListLCCP(jsonArray.toString());
                warnCrmInfoHeaderList = infoHeaderList(jsonArrayHeader.toString());
                if (checkObjValid(warnCrmInfoHeaderList) && warnCrmInfoHeaderList.size() > 0
                        || checkObjValid(warnCrmInfoListLCCP) && warnCrmInfoListLCCP.size() > 0) {
                    initView();
                } else {
                    ShowDialog(getActivity(), "无数据");
                }
            }
        } else if (index == infoIndex3) {
            if (tmpJsonObject.has("header") || tmpJsonObject.has("group")) {
                JSONArray jsonArrayHeader = tmpJsonObject.getJSONArray("header");
                JSONArray jsonArray = tmpJsonObject.getJSONArray("group");
                warnCrmInfoListDKCP = infoListDKCP(jsonArray.toString());
                warnCrmInfoHeaderList = infoHeaderList(jsonArrayHeader.toString());
                if (checkObjValid(warnCrmInfoHeaderList) && warnCrmInfoHeaderList.size() > 0
                        || checkObjValid(warnCrmInfoListDKCP) && warnCrmInfoListDKCP.size() > 0) {
                    initView();
                } else {
                    tvHideData.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void initView() {
        headerHorizontal.setScrollView(dataHorizontal);
        dataHorizontal.setScrollView(headerHorizontal);

//		commonTxtRlv2.setVisibility(View.VISIBLE);
        initData();
        mTopDataAdapter = new TopDataAdapter(getActivity(), mListTop);
        mLeftAdapter = new LeftAdapter(getActivity(), mListLeft);

        setRecyclerView(topData).setAdapter(mTopDataAdapter);
        setRecyclerView(leftData, 1).setAdapter(mLeftAdapter);
//        lvData.setAdapter(mDataAdapter);
        setRecyclerView(lvData, 1).setAdapter(new DataAdapterNew(getActivity(), mListData, new OnItemClickCallback<Integer>() {
            @Override
            public void onClick(View view, Integer info) {

                List<String> listDataName = mListTop;
                List<Object> listDataValue = null;
                listDataValue = mListData.get(info);
                DataListActivity.setData(listDataName, listDataValue);
//				setSelectorItemColor(view, R.color.colorAccent);
                Intent intent = new Intent(getActivity(), DataListActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, Integer info) {

            }
        }));

        ptrLayout.setRefreshListener(this);
    }

    protected List<WarnCrmInfoBean.ProductInfoBean.DQCK> infoListDQCK(String jsonData) throws JsonSyntaxException {
        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();
            List<WarnCrmInfoBean.ProductInfoBean.DQCK> list = gson.fromJson(jsonData, new TypeToken<List<WarnCrmInfoBean.ProductInfoBean.DQCK>>() {
            }.getType());
            return list;
        } else return null;
    }

    protected List<WarnCrmInfoBean.ProductInfoBean.LCCP> infoListLCCP(String jsonData) throws JsonSyntaxException {
        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();
            List<WarnCrmInfoBean.ProductInfoBean.LCCP> list = gson.fromJson(jsonData, new TypeToken<List<WarnCrmInfoBean.ProductInfoBean.LCCP>>() {
            }.getType());
            return list;
        } else return null;
    }

    protected List<WarnCrmInfoBean.ProductInfoBean.DKCP> infoListDKCP(String jsonData) throws JsonSyntaxException {
        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();
            List<WarnCrmInfoBean.ProductInfoBean.DKCP> list = gson.fromJson(jsonData, new TypeToken<List<WarnCrmInfoBean.ProductInfoBean.DKCP>>() {
            }.getType());
            return list;
        } else return null;
    }

    protected List<BeanHeader> infoHeaderList(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<BeanHeader> list = gson.fromJson(jsonData, new TypeToken<List<BeanHeader>>() {
            }.getType());

            return list;
        } else
            return null;
    }

    protected List<WarnCrmParamBean> paramList(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<WarnCrmParamBean> list = gson.fromJson(jsonData, new TypeToken<List<WarnCrmParamBean>>() {
            }.getType());

            return list;
        } else
            return null;
    }

    private void initData() {

        mListTop = InitData.getTopHeaderData(warnCrmInfoHeaderList);
        if (warnCrmInfoListDQCK != null && itemIndex == 0) {
            checkDataValid(warnCrmInfoListDQCK.size());
            mListLeft = InitData.getLeftHeaderData(warnCrmInfoListDQCK.size());
            mListData = InitData.ProductExpiresWarn.getCellDataDQCK(warnCrmInfoHeaderList, warnCrmInfoListDQCK);
        }
        if (warnCrmInfoListLCCP != null && itemIndex == 1) {
            checkDataValid(warnCrmInfoListLCCP.size());
            mListLeft = InitData.getLeftHeaderData(warnCrmInfoListLCCP.size());
            mListData = InitData.ProductExpiresWarn.getCellDataLCCP(warnCrmInfoHeaderList, warnCrmInfoListLCCP);
        }
        if (warnCrmInfoListDKCP != null && itemIndex == 2) {
            checkDataValid(warnCrmInfoListDKCP.size());
            mListLeft = InitData.getLeftHeaderData(warnCrmInfoListDKCP.size());
            mListData = InitData.ProductExpiresWarn.getCellDataDKCP(warnCrmInfoHeaderList, warnCrmInfoListDKCP);
        }
    }

    /**
     * 判断数据内容是否为空
     * @param count
     */
    private void checkDataValid(int count) {
        if (count <= 0) {
            ptrLayout.setVisibility(View.GONE);
//            toast("no data");
            tvHideData.setVisibility(View.VISIBLE);
            return;
        }else {
            ptrLayout.setVisibility(View.VISIBLE);
            tvHideData.setVisibility(View.INVISIBLE);
        }
    }
    private void setSelectView(TextView commonTxt, int index, int warnType) {
        String itemIndex = null;
        switch (index) {
            case 0:
                itemIndex = itenName0;
                if (checkObjValid(warnCrmInfoListDQCK))
//                    warnCrmInfoListDQCK.clear();
                break;
            case 1:
                itemIndex = itenName1;
                if (checkObjValid(warnCrmInfoListLCCP))
//                    warnCrmInfoListLCCP.clear();
                break;
            case 2:
                itemIndex = itenName2;
                if (checkObjValid(warnCrmInfoListDKCP))
//                    warnCrmInfoListDKCP.clear();
                break;
        }
        sendRequestCrm(initParam(index1 + "", "cpdq", itemIndex), warnType);
        setBgAndTextColor(commonTxt);
    }

    /**
     * 设置头背景
     */
    private void setBgAndTextColor(TextView tv) {
        commonTxt0.setEnabled(true);
        commonTxt1.setEnabled(true);
        commonTxt2.setEnabled(true);
        commonTxt3.setEnabled(true);

        commonTxt0.setTextColor(getResources().getColor(R.color.gray));
        commonTxt1.setTextColor(getResources().getColor(R.color.gray));
        commonTxt2.setTextColor(getResources().getColor(R.color.gray));
        commonTxt3.setTextColor(getResources().getColor(R.color.gray));
        commonTxt0.setBackgroundResource(R.drawable.tab_bg_normal);
        commonTxt1.setBackgroundResource(R.drawable.tab_bg_normal);
        commonTxt2.setBackgroundResource(R.drawable.tab_bg_normal);
        commonTxt3.setBackgroundResource(R.drawable.tab_bg_normal);
        tv.setEnabled(false);
        tv.setTextColor(getResources().getColor(R.color.common_tab_bg));
        tv.setBackgroundResource(R.drawable.tab_bg_selected);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.common_txt_0, R.id.common_txt_1, R.id.common_txt_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_txt_0:
                itemIndex = 0;
                itemIndexType = infoIndex1;
                break;
            case R.id.common_txt_1:
                itemIndex = 1;
                itemIndexType = infoIndex2;
                break;
            case R.id.common_txt_2:
                itemIndex = 2;
                itemIndexType = infoIndex3;
                break;
        }
        textView = (TextView) view;
        setSelectView(textView, itemIndex, itemIndexType);
    }

    @Override
    public void refresh() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                index1 = 1;

                clearList();
                setSelectView(textView == null ? commonTxt0 : textView, itemIndex, itemIndexType);
                // 结束刷新
                ptrLayout.finishRefresh();
            }
        }, 2000);
    }



    @Override
    public void loadMore() {

        if (mListLeft.size() < Integer.parseInt(count)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    index1++;
                    setSelectView(textView == null ? commonTxt0 : textView, itemIndex, itemIndexType);
                    // 结束加载更多
                    ptrLayout.finishLoadMore();
                }
            }, 2000);
        }else {
            toast("已全部加载完毕！");
            ptrLayout.finishLoadMore();
        }
    }

    private void clearList() {

        warnCrmParamList.clear();
        warnCrmInfoHeaderList.clear();
        warnCrmInfoListDQCK.clear();
        warnCrmInfoListLCCP.clear();
        warnCrmInfoListDKCP.clear();
        mListData.clear();
        mListLeft.clear();
        mListTop.clear();
    }
}
