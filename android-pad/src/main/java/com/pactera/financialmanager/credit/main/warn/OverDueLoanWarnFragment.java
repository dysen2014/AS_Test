package com.pactera.financialmanager.credit.main.warn;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.common_res.common.utils.OnItemClick;
import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.common_res.common.views.TextViewMarquee;
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
import com.pactera.financialmanager.credit.common.bean.http.OverdueCustomer;
import com.pactera.financialmanager.credit.common.views.SyncHorizontalScrollView;
import com.pactera.financialmanager.credit.main.WarnActivity;
import com.pactera.financialmanager.ui.ParentFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * level_a simple {@link Fragment} subclass.
 * 逾期提醒
 */
public class OverDueLoanWarnFragment extends ParentFragment implements BaseRefreshListener {

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
    @Bind(R.id.tv_hide_data)
    TextView tvHideData;
    @Bind(R.id.ll_lay)
    LinearLayout llLay;

    final int PAGE_SIZE = 10;
    int CurPage = 1;
    private int index1;
    private static String mItemNo;
    private int itemIndex = 0;

    public OverDueLoanWarnFragment() {
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
    private List<List<String>> mListData = new ArrayList<>();

    private static List<String> ItemNo = new ArrayList<>();
    private static List<String> topName = new ArrayList<>();

    List<BeOverdue> beOverdueList = new ArrayList<>();
    List<OverdueCustomer> overdueCustomersList = new ArrayList<>();
    List<BeanHeader> listHeader = new ArrayList<>();

    private int count;
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (progressLoading != null)
                progressLoading.setVisibility(View.GONE);
            if (tvHideData != null)
                tvHideData.setVisibility(View.GONE);
            if(msg.what == -1){
                tvHideData.setVisibility(View.VISIBLE);
                tvHideData.setText(R.string.request_timeout);
            } else if (msg.what == -100){

                tvHideData.setVisibility(View.VISIBLE);
            }else {
                if (msg.obj != null) {

                    try {
                        beOverdueList = HttpThread.parseList(HttpThread.parseJSONWithGson(msg.obj
                                .toString
                                ()), BeOverdue.class);
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
        }
    };

    Handler handler3 = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (llLay != null)
            llLay.setVisibility(View.VISIBLE);
            if (progressLoading != null)
                progressLoading.setVisibility(View.GONE);
            if (tvHideData != null)
                tvHideData.setVisibility(View.GONE);
           if(msg.what == -1){
               tvHideData.setVisibility(View.VISIBLE);
               tvHideData.setText(R.string.request_timeout);
           } else if (msg.what == -100){

               mListData.clear();
                tvHideData.setVisibility(View.VISIBLE);
            }

            WarnActivity.layBacks.setVisibility(View.VISIBLE);
            if (msg.obj != null) {

                parseData(msg);
            }

        }
    };


    private void parseData(Message msg) {
        List<String> listName;

        try {
//                    HttpThread.parseJSONWithGson((String) msg.obj, mHandler);
            JSONObject jsonObject = HttpThread.parseJSON(msg.obj.toString());
            listHeader = HttpThread.parseList(jsonObject.getJSONArray
                    ("header").toString(), BeanHeader.class);
            JSONArray jsonArray = jsonObject.getJSONArray
                    ("array");
            count = jsonArray.length();
            if (jsonArray != null && jsonArray.length() >0){
            for (int i = 0; i < jsonArray.length(); i++) {
                listName = new ArrayList<>();
                for (int j = 0; j < listHeader.size(); j++) {

                    listName.add(jsonArray.getJSONObject(i).getString(listHeader
                            .get(j).getID()));
                }
                mListData.add(listName);
            }
            }else {
                mListData.clear();
                tvHideData.setVisibility(View.VISIBLE);
            }
            if (mListData != null)
                initView();

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

        syncScroll(leftData, lvData);
        sendRequest();
        lvData.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dx > 0){//向左滑动
recyclerView.setNestedScrollingEnabled(false);
                }else if (dx < 0){//向右滑动

                }
                if(dy > 0){//向上滑动

                }else if (dy < 0){//向下滑动

                }
            }
        });
        return view;
    }
    public void syncScroll(final RecyclerView leftList,  final RecyclerView rightList) {
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
    public void sendRequest() {//逾期提醒(金额)

        progressLoading.setVisibility(View.VISIBLE);
        JSONObject jsonObject = ParamUtils.setParams("warn", "crmBeOverdue", new Object[]{ParamUtils.UserId}, 1);
//        LogUtils.d("url:" + ParamUtils.url + "\tjsonOnject:" + jsonObject.toString());
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handler);
    }



    private void sendRequest(int index) {//逾期提醒详情
        LogUtils.d(ItemNo.get(index) + "-------------" );
        mItemNo = ItemNo.get(index);

        progressLoading.setVisibility(View.VISIBLE);
        JSONObject jsonObject = ParamUtils.setParams("warn", "crmOverdueCustomer", new Object[]{ParamUtils.UserId, mItemNo, PAGE_SIZE, CurPage}, 4);
        LogUtils.d("url:" + ParamUtils.url + "\tjsonOnject:" + jsonObject.toString());
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handler3);
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

        llLay.setVisibility(View.VISIBLE);
//		commonTxtRlv2.setVisibility(View.VISIBLE);
        initData();
        mTopDataAdapter = new TopDataAdapter(getActivity(), mListTop, new OnItemClick() {
            @Override
            public void onClick(View view, Object obj, TextView item_tv) {

            }
        });
        mLeftAdapter = new LeftAdapter(getActivity(), mListLeft);

        setRecyclerView(topData).setAdapter(mTopDataAdapter);
        setRecyclerView(leftData, 1).setAdapter(mLeftAdapter);
//        lvData.setAdapter(mDataAdapter);
        lvData.setNestedScrollingEnabled(false);
        setRecyclerView(lvData, 1).setAdapter(new DataAdapterNew(getActivity(), mListData, new OnItemClickCallback<Integer>() {
            @Override
            public void onClick(View view, Integer info) {

                List<String> listDataName = mListTop;
                List<String> listDataValue = null;
                listDataValue = mListData.get(info);
                DataListActivity.setData(listDataName, listDataValue);
//				setSelectorItemColor(view, R.color.colorAccent);
                Intent intent = new Intent(getActivity(), DataListActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, Integer info) {

            }

            @Override
            public void onClick(View view, int position, int index) {

            }
        }));
        ((LinearLayoutManager)lvData.getLayoutManager()).scrollToPositionWithOffset(Integer.parseInt(ParamUtils.pageSize)*(CurPage - 1),0);
        ((LinearLayoutManager)leftData.getLayoutManager()).scrollToPositionWithOffset(Integer.parseInt
                (ParamUtils.pageSize)*(CurPage - 1),0);
        ptrLayout.setRefreshListener(this);
//        WarnActivity.layBacks.setEnabled(true);
    }

    @Override
    public void refresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CurPage = 1;
                clearList();
                sendRequest(index1);
                // 结束刷新
                ptrLayout.finishRefresh();
            }
        }, 2000);
    }

    @Override
    public void loadMore() {
        if (count % Integer.parseInt(ParamUtils.pageSize) == 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    CurPage++;
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
        listHeader.clear();
        mListData.clear();
        mListLeft.clear();
        mListTop.clear();
    }
    /**
     * 设置头背景c
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
    /**
     * 设置头背景
     */
    private void setBgAndTextColor2(TextView tv) {
        commonTxt00.setEnabled(true);
        commonTxt01.setEnabled(true);
        commonTxt02.setEnabled(true);
        commonTxt03.setEnabled(true);

        commonTxt00.setTextColor(getResources().getColor(R.color.gray));
        commonTxt01.setTextColor(getResources().getColor(R.color.gray));
        commonTxt02.setTextColor(getResources().getColor(R.color.gray));
        commonTxt03.setTextColor(getResources().getColor(R.color.gray));
        commonTxt00.setBackgroundResource(R.drawable.tab_bg_normal);
        commonTxt01.setBackgroundResource(R.drawable.tab_bg_normal);
        commonTxt02.setBackgroundResource(R.drawable.tab_bg_normal);
        commonTxt03.setBackgroundResource(R.drawable.tab_bg_normal);
        tv.setEnabled(false);
        tv.setTextColor(getResources().getColor(R.color.common_tab_bg));
        tv.setBackgroundResource(R.drawable.tab_bg_selected);
    }

    @OnClick({R.id.common_txt_0, R.id.common_txt_1, R.id.common_txt_2, R.id.common_txt_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_txt_0:
                itemIndex = 0;
                break;
            case R.id.common_txt_1:
                itemIndex = 1;
                break;
            case R.id.common_txt_2:
                itemIndex = 2;
                break;
            case R.id.common_txt_3:
                itemIndex = 3;
                break;
        }
        setSelectView((TextView) view, itemIndex);
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
