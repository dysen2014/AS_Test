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
import com.dysen.common_res.common.utils.OnItemClick;
import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.common_res.common.views.TextViewMarquee;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.adapters.DataAdapterNew;
import com.pactera.financialmanager.credit.common.adapters.LeftAdapter;
import com.pactera.financialmanager.credit.common.adapters.TopDataAdapter;
import com.pactera.financialmanager.credit.common.bean.InitData;
import com.pactera.financialmanager.credit.common.bean.http.BeanHeader;
import com.pactera.financialmanager.credit.common.bean.warn.WarnCrmParamBean;
import com.pactera.financialmanager.credit.common.views.SyncHorizontalScrollView;
import com.pactera.financialmanager.credit.main.WarnActivity;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.DownComparator;
import com.pactera.financialmanager.util.LogUtils;
import com.pactera.financialmanager.util.Tool;
import com.pactera.financialmanager.util.UpComparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dysen on 2017/8/17.
 */

public class InsuffcientBalanceWarnFragment extends ParentFragment implements BaseRefreshListener {

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
    @Bind(R.id.left_top_item_lay)
    LinearLayout leftTopItemLay;
    @Bind(R.id.top_data)
    RecyclerView topData;
    @Bind(R.id.header_horizontal)
    SyncHorizontalScrollView headerHorizontal;
    @Bind(R.id.left_data)
    RecyclerView leftData;
    @Bind(R.id.left_header_horizontal)
    SyncHorizontalScrollView leftHeaderHorizontal;
    @Bind(R.id.lv_data)
    RecyclerView lvData;
    @Bind(R.id.data_horizontal)
    SyncHorizontalScrollView dataHorizontal;
    @Bind(R.id.ptr_layout)
    PullToRefreshLayout ptrLayout;
    @Bind(R.id.tv_hide_data)
    TextView tvHideData;
    @Bind(R.id.progress_loading)
    ProgressBar progressLoading;

    int count;
    @Bind(R.id.common_txt_4)
    TextView commonTxt4;
    @Bind(R.id.ll_lay)
    LinearLayout llLay;
    private int CurPage = 1;
    private int itemIndex = 0;
    final private int paramIndex = 1;
    private List<WarnCrmParamBean.TabBean> tabList = new ArrayList<>();
    private List<BeanHeader> listHeader = new ArrayList<>();
    private List<List<String>> mListData = new ArrayList<>();
    private HResponse response;
    private String itenName0, itenName1, itenName2, itenName3, itenName4;
    private int itemIndexType = 11;
    final private int infoIndex1 = 11, infoIndex2 = 12, infoIndex3 = 13, infoIndex4 = 14,
            infoIndex5 = 15;
    private List<String> mListTop = new ArrayList<>();
    private List<String> mListLeft = new ArrayList<>();
    private TextView textView;
    private String brCode, BRNAME;
    private String next;
    private JSONArray jsonArray;
    private String transCode;
    private boolean isManagers;
    private String CST_ID;
    private String sssss;
    private TopDataAdapter topDataAdapter;
    String AscText = "↑";
    String DescText = "↓";
    boolean sortingBool = true;

    public void setCodeType(String transCode) {
        this.transCode = transCode;
    }

    public void setIsManager(boolean isManager) {
        this.isManagers = isManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_default_warn, container, false);
        ButterKnife.bind(this, view);
//        initListener();
        syncScroll(leftData, lvData);

        if (TextUtils.isEmpty(brCode)) {
            brCode = ParamUtils.brCode;
        }

        if (ParamUtils.funcType.equals("zhtx")) {
            transCode = "XD014";
        }
        sendRequestCrm(initParam(CurPage + "", ParamUtils.funcType), paramIndex);
        return view;
    }

    public void syncScroll(final RecyclerView leftList, final RecyclerView rightList) {
        leftList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                    // note: scrollBy() not trigger OnScrollListener
                    // This is a known issue. It is caused by the fact that RecyclerView does not know how LayoutManager will handle the scroll or if it will handle it at all.
                    rightList.scrollBy(dx, dy);
                }
            }
        });

        rightList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                    leftList.scrollBy(dx, dy);
                }
            }
        });
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (progressLoading != null)
                progressLoading.setVisibility(View.GONE);
            if (tvHideData != null)
                tvHideData.setVisibility(View.GONE);

            if (msg.what == -1) {
//                    toast("请求超时！");
                tvHideData.setVisibility(View.VISIBLE);
                tvHideData.setText("请求超时！");
//                return;
            } else if (msg.what == -100) {
                if (tvHideData != null)
                    tvHideData.setVisibility(View.VISIBLE);
            }

//            WarnActivity.layBacks.setEnabled(true);
            WarnActivity.layBacks.setVisibility(View.VISIBLE);
            if (msg.obj != null) {
                JSONObject jsonObject = ((JSONObject) msg.obj);
                try {
                    if (jsonObject.has("retCode")) {
                        String retCode = jsonObject.getString("retCode");
                        if (retCode.equals("2008")) {
                            if (tvHideData != null) {
                                tvHideData.setVisibility(View.VISIBLE);
                                tvHideData.setText("CRM请求异常 ");
                            }
                        }
                    }
                    readJson(jsonObject, msg.what);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    };


    private String initParam(String offset, String spareOne) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("STAID", ParamUtils.staId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String params = ParamUtils.setParamsCrm(brCode, "01", String
                        .valueOf(jsonObject).replace(":", "%3A"), offset, LogoActivity.user.getImei(),
                spareOne, transCode, LogoActivity.user.getUserCode());
        return params;
    }

    private String initParam(String offset, String spareOne, String spareTwo) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("STAID", ParamUtils.staId);

//            LogUtils.d(jsonObject+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+jsonObject.toString().replace(":", "%3A")+"============="+String.valueOf(jsonObject));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String params = ParamUtils.setParamsCrm(brCode, "01", String
                        .valueOf(jsonObject).replace(":", "%3A"), offset, LogoActivity.user.getImei(), spareOne,
                spareTwo, transCode, LogoActivity.user.getUserCode());
        return params;
    }

    public void sendRequestCrm(String param, int warnType) {
        ParentActivity.MyUtils.initLoad(listHeader, mListData, tvHideData, leftTopItemTv);
        initView();
        progressLoading.setVisibility(View.VISIBLE);
        if (Tool.haveNet(getActivity())) {
            HttpThread.sendRequestGet(ParamUtils.urlTempCrm, param, handler, warnType);
        } else {
            tvHideData.setVisibility(View.VISIBLE);
            tvHideData.setText(R.string.unNetwork);
        }
    }

    private void parseData(JSONObject jsonObject) {
        List<String> listName;

        if (jsonObject.has("header") || jsonObject.has("group")) {
            try {

                listHeader = HttpThread.parseList(jsonObject.getJSONArray
                        ("header").toString(), BeanHeader.class);
                LogUtils.i(listHeader.size() + "==========listHeader============" + listHeader);
                jsonArray = jsonObject.getJSONArray
                        ("group");
                next = "2";
                if (transCode.equals("XD020")) {
                    next = jsonObject.getString("next");
                }
                count = jsonArray.length();

                if (next.equals("1") || next.equals("2")) {
                    if (jsonArray != null && jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            listName = new ArrayList<>();
                            for (int j = 0; j < listHeader.size(); j++) {

                                listName.add(jsonArray.getJSONObject(i).getString(listHeader
                                        .get(j).getID()));
                            }
//                        mListData.add(i, listName);
                            mListData.add(listName);
                        }
                    }
                    if (jsonArray == null || jsonArray.length() <= 0) {
                        mListData.clear();
                        tvHideData.setVisibility(View.VISIBLE);
                    }

                    if (listHeader != null && listHeader.size() > 0) {
                        leftTopItemTv.setText("序号");
                        initView();
                    }
                } else if (next.equals("0")) {

                    if (transCode.equals("XD020") || transCode.equals("XD014")) {
                        transCode = "XD012";
                    }
                    sendRequestCrm(initParam(CurPage + "", ParamUtils.funcType), paramIndex);


                }

            } catch (JSONException e) {
                e.printStackTrace();
                LogUtils.d("报错了吗?"+e);
            }
        }
    }

    private void readJson(JSONObject tmpJsonObject, int index) throws JSONException {
        if (index == paramIndex) {
            if (tmpJsonObject.has("group")) {
                if (tmpJsonObject.has("header")) {
                    parseData(tmpJsonObject);
                } else {//有 tab 标签

                    JSONArray jsonArray = tmpJsonObject.getJSONArray("group");

                    tabList = HttpThread.parseList(jsonArray.toString(), WarnCrmParamBean.TabBean
                            .class);
                    if (tabList != null && tabList.size() > 0) {
                        if (tabList.size() == 1) {
                            commonTxt0.setVisibility(View.VISIBLE);
                            commonTxt0.setText(tabList.get(0).getTITLENAME() + "(" + tabList.get(0).getRows() + ")");
                            itenName0 = tabList.get(0).getSUBTYPE();
                        } else if (tabList.size() == 2) {
                            commonTxt0.setVisibility(View.VISIBLE);
                            commonTxt1.setVisibility(View.VISIBLE);
                            commonTxt0.setText(tabList.get(0).getTITLENAME() + "(" + tabList.get(0).getRows() + ")");
                            commonTxt1.setText(tabList.get(1).getTITLENAME() + "(" + tabList.get(1).getRows() + ")");
                            itenName0 = tabList.get(0).getSUBTYPE();
                            itenName1 = tabList.get(1).getSUBTYPE();
                        } else if (tabList.size() == 3) {
                            commonTxt0.setVisibility(View.VISIBLE);
                            commonTxt1.setVisibility(View.VISIBLE);
                            commonTxt2.setVisibility(View.VISIBLE);
                            commonTxt0.setText(tabList.get(0).getTITLENAME() + "(" + tabList.get(0).getRows() + ")");
                            commonTxt1.setText(tabList.get(1).getTITLENAME() + "(" + tabList.get(1).getRows() + ")");
                            commonTxt2.setText(tabList.get(2).getTITLENAME() + "(" + tabList.get(2)
                                    .getRows() + ")");
                            itenName0 = tabList.get(0).getSUBTYPE();
                            itenName1 = tabList.get(1).getSUBTYPE();
                            itenName2 = tabList.get(2).getSUBTYPE();
                        } else if (tabList.size() == 4) {
                            commonTxt0.setVisibility(View.VISIBLE);
                            commonTxt1.setVisibility(View.VISIBLE);
                            commonTxt2.setVisibility(View.VISIBLE);
                            commonTxt3.setVisibility(View.VISIBLE);
                            commonTxt0.setText(tabList.get(0).getTITLENAME() + "(" + tabList.get(0).getRows() + ")");
                            commonTxt1.setText(tabList.get(1).getTITLENAME() + "(" + tabList.get(1).getRows() + ")");
                            commonTxt2.setText(tabList.get(2).getTITLENAME() + "(" + tabList.get(2)
                                    .getRows() + ")");
                            commonTxt3.setText(tabList.get(3).getTITLENAME() + "(" + tabList.get(3)
                                    .getRows() + ")");
                            itenName0 = tabList.get(0).getSUBTYPE();
                            itenName1 = tabList.get(1).getSUBTYPE();
                            itenName2 = tabList.get(2).getSUBTYPE();
                            itenName3 = tabList.get(3).getSUBTYPE();
                        } else if (tabList.size() == 5) {
                            commonTxt0.setVisibility(View.VISIBLE);
                            commonTxt1.setVisibility(View.VISIBLE);
                            commonTxt2.setVisibility(View.VISIBLE);
                            commonTxt3.setVisibility(View.VISIBLE);
                            commonTxt4.setVisibility(View.VISIBLE);

                            commonTxt0.setText(tabList.get(0).getTITLENAME() + "(" + tabList.get(0).getRows() + ")");
                            commonTxt1.setText(tabList.get(1).getTITLENAME() + "(" + tabList.get(1).getRows() + ")");
                            commonTxt2.setText(tabList.get(2).getTITLENAME() + "(" + tabList.get(2)
                                    .getRows() + ")");
                            commonTxt3.setText(tabList.get(3).getTITLENAME() + "(" + tabList.get(3)
                                    .getRows() + ")");
                            commonTxt4.setText(tabList.get(4).getTITLENAME() + "(" + tabList.get(3)
                                    .getRows() + ")");

                            itenName0 = tabList.get(0).getTYPE();
                            itenName1 = tabList.get(1).getTYPE();
                            itenName2 = tabList.get(2).getTYPE();
                            itenName3 = tabList.get(3).getTYPE();
                            itenName4 = tabList.get(4).getTYPE();
                        }
                        setSelectView(commonTxt0, itemIndex, infoIndex1);
                    }
                }
            }
        } else if (index == infoIndex1) {
            parseData(tmpJsonObject);
        } else if (index == infoIndex2) {
            parseData(tmpJsonObject);
        } else if (index == infoIndex3) {
            parseData(tmpJsonObject);
        } else if (index == infoIndex4) {
            parseData(tmpJsonObject);
        } else if (index == infoIndex5) {
            parseData(tmpJsonObject);
        }
    }

    private void initView() {
        headerHorizontal.setScrollView(dataHorizontal);
        dataHorizontal.setScrollView(headerHorizontal);
        initData();
        //LogUtils.i(listHeader.get(0).getNAME()+"---------------------------"+mListTop);
        topDataAdapter = new TopDataAdapter(getActivity(), mListTop, new OnItemClick() {
            @Override
            public void onClick(View view, Object obj, TextView item_tv) {



                if (sortingBool) {
                    sortingBool = false;
//                    item_tv.setText(mListTop.get((Integer) obj) + AscText);
                    UpComparator comparator = new UpComparator((Integer) obj);
                    Collections.sort(mListData, comparator);
                } else {
                    sortingBool = true;
//                    item_tv.setText(mListTop.get((Integer) obj) + DescText);
                    DownComparator comparator = new DownComparator((Integer) obj);
                    Collections.sort(mListData, comparator);
                }

//                topDataAdapter.notifyDataSetChanged();
                LogUtils.d("mListData=====" + mListData);
//                lvData.setNestedScrollingEnabled(false);
                setRecyclerView(lvData, 1).setAdapter(new DataAdapterNew(getActivity(), mListData, new OnItemClickCallback<Integer>() {
                    @Override
                    public void onClick(View view, Integer info) {

                        if (next.equals("1")) {
                            try {
                                brCode = jsonArray.getJSONObject(info).getString("BRID");
                                BRNAME = jsonArray.getJSONObject(info).getString("BRNAME");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (!BRNAME.equals("合计")) {
                                clearList();
                                sendRequestCrm(initParam(CurPage + "", ParamUtils.funcType), paramIndex);
                            }


                        } else {
                            List<String> listDataName = mListTop;
                            List<String> listDataValue = null;
                            listDataValue = mListData.get(info);
                            if (WarnActivity.tabName.equals("贷款预约")) {
                                try {
                                    CST_ID = jsonArray.getJSONObject(info).getString("CST_ID");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                FinancialLoanActivity.setData(listDataName, listDataValue);
                                //是否隐藏申请调配
                                FinancialLoanActivity.setAllocate(true);
                                FinancialLoanActivity.setIsManager(isManagers);
                                FinancialLoanActivity.setCST_ID(CST_ID);
                                FinancialLoanActivity.setSUBTYPE(sssss);
                                FinancialLoanActivity.setBrCode(brCode);
                                Intent intent = new Intent(getActivity(), FinancialLoanActivity.class);
                                startActivity(intent);
                            } else {
                                DataListActivity.setData(listDataName, listDataValue);
                                Intent intent = new Intent(getActivity(), DataListActivity.class);
                                startActivity(intent);
                            }
                        }


                    }

                    @Override
                    public void onLongClick(View view, Integer info) {

                    }

                    @Override
                    public void onClick(View view, int position, int index) {

                    }

                }));
            }
        });
        LogUtils.d("mListTop======" + mListTop);
        LogUtils.d("mListLeft=====" + mListLeft);


        setRecyclerView(topData).setAdapter(topDataAdapter);
        setRecyclerView(leftData, 1).setAdapter(new LeftAdapter(getActivity(), mListLeft));
//        lvData.setAdapter(mDataAdapter);

        lvData.setNestedScrollingEnabled(false);
        setRecyclerView(lvData, 1).setAdapter(new DataAdapterNew(getActivity(), mListData, new OnItemClickCallback<Integer>() {
            @Override
            public void onClick(View view, Integer info) {

                if (next.equals("1")) {
                    try {
                        brCode = jsonArray.getJSONObject(info).getString("BRID");
                        BRNAME = jsonArray.getJSONObject(info).getString("BRNAME");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (!BRNAME.equals("合计")) {
                        clearList();
                        sendRequestCrm(initParam(CurPage + "", ParamUtils.funcType), paramIndex);
                    }


                } else {
                    List<String> listDataName = mListTop;
                    List<String> listDataValue = null;
                    listDataValue = mListData.get(info);
                    if (WarnActivity.tabName.equals("贷款预约")) {
                        try {
                            CST_ID = jsonArray.getJSONObject(info).getString("CST_ID");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        FinancialLoanActivity.setData(listDataName, listDataValue);
                        //是否隐藏申请调配
                        FinancialLoanActivity.setAllocate(true);
                        FinancialLoanActivity.setIsManager(isManagers);
                        FinancialLoanActivity.setCST_ID(CST_ID);
                        FinancialLoanActivity.setSUBTYPE(sssss);
                        FinancialLoanActivity.setBrCode(brCode);
                        Intent intent = new Intent(getActivity(), FinancialLoanActivity.class);
                        startActivity(intent);
                    } else {
                        DataListActivity.setData(listDataName, listDataValue);
                        Intent intent = new Intent(getActivity(), DataListActivity.class);
                        startActivity(intent);
                    }
                }


            }

            @Override
            public void onLongClick(View view, Integer info) {

            }

            @Override
            public void onClick(View view, int position, int index) {

            }

        }));
        checkDataValid(mListData.size());

        ptrLayout.setRefreshListener(this);
    }

    private void initData() {

        mListTop = InitData.getTopHeaderData(listHeader);
        if (checkObjValid(mListData)) {
            checkDataValid(mListData.size());
            mListLeft = InitData.getLeftHeaderData(mListData.size());
//            mListData = InitData.OverDueWarn.getCellData(listHeader, overdueCustomersList);
        }
    }

    /**
     * 判断数据内容是否为空
     *
     * @param count
     */
    private void checkDataValid(int count) {
        if (count <= 0) {
            ptrLayout.setVisibility(View.GONE);
            tvHideData.setVisibility(View.VISIBLE);
            return;
        } else {
            ptrLayout.setVisibility(View.VISIBLE);
            tvHideData.setVisibility(View.INVISIBLE);
        }
    }

    private void setSelectView(TextView commonTxt, int index, int warnType) {
        String itemIndex = null;
        switch (index) {
            case 0:
                itemIndex = itenName0;
                break;
            case 1:
                itemIndex = itenName1;
                break;
            case 2:
                itemIndex = itenName2;
                break;
            case 3:
                itemIndex = itenName3;
                break;
            case 4:
                itemIndex = itenName4;
                break;
        }
        if (transCode.equals("XD012")) {
            transCode = "XD013";
        } else if (transCode.equals("XD014")) {
            transCode = "XD012";
        }
        if (ParamUtils.funcType.equals("zhtx")) {
            sendRequestCrm(initParam(CurPage + "", itemIndex), warnType);

        } else {
            sendRequestCrm(initParam(CurPage + "", ParamUtils.funcType, itemIndex), warnType);

        }
        sssss = itemIndex;
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
        commonTxt4.setEnabled(true);

        commonTxt0.setTextColor(getResources().getColor(R.color.gray));
        commonTxt1.setTextColor(getResources().getColor(R.color.gray));
        commonTxt2.setTextColor(getResources().getColor(R.color.gray));
        commonTxt3.setTextColor(getResources().getColor(R.color.gray));
        commonTxt4.setTextColor(getResources().getColor(R.color.gray));

        commonTxt0.setBackgroundResource(R.drawable.tab_bg_normal);
        commonTxt1.setBackgroundResource(R.drawable.tab_bg_normal);
        commonTxt2.setBackgroundResource(R.drawable.tab_bg_normal);
        commonTxt3.setBackgroundResource(R.drawable.tab_bg_normal);
        commonTxt4.setBackgroundResource(R.drawable.tab_bg_normal);

        tv.setEnabled(false);
        tv.setTextColor(getResources().getColor(R.color.common_tab_bg));
        tv.setBackgroundResource(R.drawable.tab_bg_selected);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.common_txt_0, R.id.common_txt_1, R.id.common_txt_2, R.id.common_txt_3, R.id
            .common_txt_4})
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
            case R.id.common_txt_3:
                itemIndex = 3;
                itemIndexType = infoIndex4;
                break;
            case R.id.common_txt_4:
                itemIndex = 4;
                itemIndexType = infoIndex5;
                break;
        }
        CurPage = 1;
        mListData.clear();//防止数据叠加
        textView = (TextView) view;
        setSelectView((TextView) view, itemIndex, itemIndexType);
    }

    @Override
    public void refresh() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CurPage = 1;
                clearList();

                sendRequestCrm(initParam(CurPage + "", ParamUtils.funcType, sssss), paramIndex);

                // 结束刷新
                ptrLayout.finishRefresh();
            }
        }, 2000);
    }

    @Override
    public void loadMore() {
        LogUtils.d(count + "===count======" + ParamUtils.pageSize);
        if (count != 0 && count % Integer.parseInt(ParamUtils.pageSize) == 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    CurPage++;
//                    if (sssss == null){
//                        sssss = ParamUtils.funcType;
//                    }
//                    sendRequestCrm(initParam(CurPage + "", sssss), paramIndex);
                    sendRequestCrm(initParam(CurPage + "", ParamUtils.funcType, sssss), paramIndex);

                    LogUtils.d("mListData ===== "+mListData);
                    // 结束加载更多
                    ptrLayout.finishLoadMore();
                }
            }, 2000);
        } else {
            toast("已全部加载完毕！");
            ptrLayout.finishLoadMore();
        }
    }

    private void clearList() {

        listHeader.clear();
        mListData.clear();
        mListLeft.clear();
        mListTop.clear();
    }
}
//        http://192.168.2.111:7001/services/XDAppService?method=getJSON&brCode=BR38160&chnlCode=01&jsonData={%22STAID%22%3A%22ST001101%22}&offset=1&seriNo=F2LT971FHFY1&spareOne=dkyy&spareTwo=wcl&transCode=XD013&userCode=UR36010}
//        http://192.168.2.111:7001/services/XDAppService?method=getJSON&brCode=BR38160&chnlCode=01&jsonData={%22STAID%22%3A%22ST001101%22}&offset=1&seriNo=F2LT971FHFY1&spareOne=wcl&transCode=XD013&userCode=UR36010}