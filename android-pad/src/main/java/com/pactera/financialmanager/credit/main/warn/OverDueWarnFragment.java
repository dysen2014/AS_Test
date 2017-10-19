package com.pactera.financialmanager.credit.main.warn;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.common_res.common.views.TextViewMarquee;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.adapters.ChildDataAdapter;
import com.pactera.financialmanager.credit.common.adapters.CommonTxtDataAdapter;
import com.pactera.financialmanager.credit.common.adapters.DataAdapter;
import com.pactera.financialmanager.credit.common.adapters.DataAdapterNew;
import com.pactera.financialmanager.credit.common.adapters.LeftAdapter;
import com.pactera.financialmanager.credit.common.adapters.TopDataAdapter;
import com.pactera.financialmanager.credit.common.bean.InitData;
import com.pactera.financialmanager.credit.common.bean.http.BeOverdue;
import com.pactera.financialmanager.credit.common.bean.http.BeanHeader;
import com.pactera.financialmanager.credit.common.bean.http.OverDueReminders;
import com.pactera.financialmanager.credit.common.bean.http.OverdueCustomer;
import com.pactera.financialmanager.credit.common.views.NoscrollListView;
import com.pactera.financialmanager.credit.common.views.SyncHorizontalScrollView;
import com.pactera.financialmanager.ui.ParentFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * 逾期提醒
 */
public class OverDueWarnFragment extends ParentFragment implements BaseRefreshListener {

    @Bind(R.id.common_txt_0)
    TextView commonTxt0;
    @Bind(R.id.common_txt_1)
    TextView commonTxt1;
    @Bind(R.id.common_txt_2)
    TextView commonTxt2;
    @Bind(R.id.common_txt_3)
    TextView commonTxt3;
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
    @Bind(R.id.common_txt_rlv)
    RecyclerView commonTxtRlv;
    @Bind(R.id.common_txt_rlv2)
    RecyclerView commonTxtRlv2;
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
    @Bind(R.id.common_txt_ll_0)
    LinearLayout commonTxtLl0;
    @Bind(R.id.common_txt_ll_00)
    LinearLayout commonTxtLl00;
    @Bind(R.id.progress_loading)
    ProgressBar progressLoading;

    final int PAGE_SIZE = 10;
    int curPage = 1;
    @Bind(R.id.tv_hide_data)
    TextView tvHideData;
    private int index1, index2;
    private boolean overDueFlag;
    private static String mItemNo;

    public OverDueWarnFragment() {
        // Required empty public constructor
    }

    private TopDataAdapter mTopDataAdapter;
    private DataAdapter mDataAdapter;
    private LinearLayoutManager mLayoutManager;
    private ChildDataAdapter childDataAdapter;
    private LeftAdapter mLeftAdapter;
    private CommonTxtDataAdapter commonTxtDataAdapter;

    private List<String> mListLeft = new ArrayList<>();
    private List<String> mListTop = new ArrayList<>();
    private List<List<Object>> mListData = new ArrayList<>();

    private static List<String> ItemNo = new ArrayList<>();
    private static List<String> mOverDayItemNo = new ArrayList<>();
    private static List<String> topName = new ArrayList<>();
    private static List<String> topName2 = new ArrayList<>();

    List<BeOverdue> beOverdueList = new ArrayList<>();
    List<OverDueReminders> overDueRemindersList = new ArrayList<>();
    List<OverdueCustomer> overdueCustomersList = new ArrayList<>();
    List<BeanHeader> overdueCustomersListHeader = new ArrayList<>();

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.obj != null) {
                progressLoading.setVisibility(View.GONE);

                try {
                    beOverdueList = parseList(HttpThread.parseJSONWithGson((String) msg.obj));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < beOverdueList.size(); i++) {
                    ItemNo.add(beOverdueList.get(i).getItemno());
                    topName.add(beOverdueList.get(i).getName());

                    LogUtils.d("ItemNo:" + ItemNo.get(i) + "\t topName:" + topName.get(i));
                }
                if (topName.size() > 0) {
//                    setBadgeView(commonTxt0, beOverdueList.get(0).getCount());
//                    setBadgeView(commonTxt1, beOverdueList.get(1).getCount());
//                    setBadgeView(commonTxt2, beOverdueList.get(2).getCount());
//                    setBadgeView(commonTxt3, beOverdueList.get(3).getCount());
                    commonTxt0.setVisibility(View.VISIBLE);
                    commonTxt0.setText(topName.get(0) + "(" + beOverdueList.get(0).getCount() + ")");
                    commonTxt1.setVisibility(View.VISIBLE);
                    commonTxt1.setText(topName.get(1) + "(" + beOverdueList.get(1).getCount() + ")");
                    commonTxt2.setVisibility(View.VISIBLE);
                    commonTxt2.setText(topName.get(2) + "(" + beOverdueList.get(2).getCount() + ")");
                    commonTxt3.setVisibility(View.VISIBLE);
                    commonTxt3.setText(topName.get(3) + "(" + beOverdueList.get(3).getCount() + ")");
                    setSelectView(commonTxt0, 0);
                }
            }
        }
    };

    protected List<BeOverdue> parseList(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<BeOverdue> list = gson.fromJson(jsonData, new TypeToken<List<BeOverdue>>() {
            }.getType());

            return list;
        } else
            return null;
    }

    Handler handler3 = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.obj != null) {
                progressLoading.setVisibility(View.GONE);
                try {
                    HttpThread.parseJSONWithGson((String) msg.obj, mHandler);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    };

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.getData() != null) {
                Bundle bundle = msg.getData();
                progressLoading.setVisibility(View.GONE);
                overdueCustomersListHeader = parseList3Header(bundle.getString("header"));
                List<OverdueCustomer> list = parseList3(bundle.getString("data"));

                for (int i = 0; i < list.size(); i++) {

                    overdueCustomersList.add(list.get(i));
                }

                if (overdueCustomersListHeader.size() > 0)
                    initView();
            }
        }
    };

    protected List<BeanHeader> parseList3Header(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<BeanHeader> list = gson.fromJson(jsonData, new TypeToken<List<BeanHeader>>() {
            }.getType());

            return list;
        } else
            return null;
    }

    protected List<OverdueCustomer> parseList3(String jsonData) throws JsonSyntaxException {

        LogUtils.d("------------------jsonData:" + jsonData);
        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<OverdueCustomer> list = gson.fromJson(jsonData, new TypeToken<List<OverdueCustomer>>() {
            }.getType());

            return list;
        } else
            return null;
    }

    public static void setItenNo(String itemNo) {
        mItemNo = itemNo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_past_due_warn, container, false);
        ButterKnife.bind(this, view);

        sendRequest();

        return view;
    }

    public void sendRequest() {//逾期提醒(金额)

        overDueFlag = false;
        progressLoading.setVisibility(View.VISIBLE);
        JSONObject jsonObject = ParamUtils.setParams("warn", "crmBeOverdue", new Object[]{ParamUtils.UserId}, 1);
        LogUtils.d("url:" + ParamUtils.url + "\tjsonOnject:" + jsonObject.toString());
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handler);
    }



    private void sendRequest(int index) {//逾期提醒详情
        LogUtils.d(ItemNo.get(index) + "-------------" );
        overDueFlag = true;

        progressLoading.setVisibility(View.VISIBLE);
        JSONObject jsonObject = ParamUtils.setParams("warn", "crmOverdueCustomer", new Object[]{ParamUtils.UserId, mItemNo, PAGE_SIZE, curPage}, 4);
        LogUtils.d("url:" + ParamUtils.url + "\tjsonOnject:" + jsonObject.toString());
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handler3);
    }

    private void initData() {

        mListTop = InitData.getTopHeaderData(overdueCustomersListHeader);
        if (checkObjValid(overdueCustomersList)) {
            checkDataValid(overdueCustomersList.size());
            mListLeft = InitData.getLeftHeaderData(overdueCustomersList.size());
            mListData = InitData.OverDueWarn.getCellData(overdueCustomersListHeader, overdueCustomersList);
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

    @Override
    public void refresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                curPage = 1;
                clearList();
                sendRequest(index1);
                // 结束刷新
                ptrLayout.finishRefresh();
            }
        }, 2000);
    }

    @Override
    public void loadMore() {
        if (mListLeft.size() < Integer.parseInt(beOverdueList.get(0).getCount())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    curPage++;
                    sendRequest(index1);
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

        overdueCustomersList.clear();
        overdueCustomersListHeader.clear();
        overDueRemindersList.clear();
        mListData.clear();
        mListLeft.clear();
        mListTop.clear();
    }
    /**
     * 设置头背景
     *
     * @param tv
     */
    private void setBgAndTextColor(TextView tv) {
        commonTxt0.setEnabled(true);
        commonTxt1.setEnabled(true);
        commonTxt2.setEnabled(true);
        commonTxt3.setEnabled(true);

        commonTxt0.setTextColor(getResources().getColor(R.color.separatelightredline));
        commonTxt1.setTextColor(getResources().getColor(R.color.separatelightredline));
        commonTxt2.setTextColor(getResources().getColor(R.color.separatelightredline));
        commonTxt3.setTextColor(getResources().getColor(R.color.separatelightredline));
        tv.setEnabled(false);
        tv.setTextColor(getResources().getColor(R.color.white));
    }

    private void setBgAndTextColor2(TextView tv) {
        commonTxt00.setEnabled(true);
        commonTxt01.setEnabled(true);
        commonTxt02.setEnabled(true);
        commonTxt03.setEnabled(true);
        commonTxt04.setEnabled(true);
        tv.setEnabled(false);
        tv.setTextColor(getResources().getColor(R.color.white));
    }

    @OnClick({R.id.common_txt_0, R.id.common_txt_1, R.id.common_txt_2, R.id.common_txt_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_txt_0:
                setSelectView(commonTxt0, 0);
                break;
            case R.id.common_txt_1:
                setSelectView(commonTxt1, 1);
                break;
            case R.id.common_txt_2:
                setSelectView(commonTxt2, 2);
                break;
            case R.id.common_txt_3:
                setSelectView(commonTxt3, 3);
                break;
        }
    }

    private void setSelectView(TextView commonTxt0, int i) {

        index1 = i;
        overdueCustomersList.clear();
        sendRequest(index1);
        setBgAndTextColor(commonTxt0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
