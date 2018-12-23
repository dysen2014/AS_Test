package com.pactera.financialmanager.credit.main.service.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.adapters.ChildDataAdapter;
import com.pactera.financialmanager.credit.common.adapters.DataAdapter;
import com.pactera.financialmanager.credit.common.adapters.DataAdapterNew;
import com.pactera.financialmanager.credit.common.adapters.LeftAdapter;
import com.pactera.financialmanager.credit.common.adapters.TopDataAdapter;
import com.pactera.financialmanager.credit.common.bean.InitData;
import com.pactera.financialmanager.credit.common.bean.http.BeanHeader;
import com.pactera.financialmanager.credit.common.bean.search.LoanAfterBean;
import com.pactera.financialmanager.credit.common.views.SyncHorizontalScrollView;
import com.pactera.financialmanager.credit.main.warn.DataListActivity;
import com.pactera.financialmanager.ui.ParentFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 未结清授信业务
 * Created by dysen on 2017/8/29.
 */

public class LoanAfterFragment extends ParentFragment implements BaseRefreshListener {
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
    @Bind(R.id.ll_lay)
    LinearLayout llLay;
    @Bind(R.id.left_header_horizontal)
    SyncHorizontalScrollView leftHeaderHorizontal;
    @Bind(R.id.tv_hide_data)
    TextView tvHideData;

    private TopDataAdapter mTopDataAdapter;
    private DataAdapter mDataAdapter;
    private LinearLayoutManager mLayoutManager;
    private ChildDataAdapter childDataAdapter;
    private LeftAdapter mLeftAdapter;

    private List<String> mListLeft = new ArrayList<>();
    private List<String> mListTop = new ArrayList<>();
    private List<List<String>> mListData = new ArrayList<>();

    private int index1;
    final int PAGE_SIZE = 10;
    int CurPage = 1;
    private List<BeanHeader> beanListHeader = new ArrayList<>();
    private List<LoanAfterBean> listData = new ArrayList<>();
    private String CustomerId;
    private String CustomerType;
    private boolean flagCustomer;

    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.obj != null) {

                try {
                    parseJSONWithGson((String) msg.obj, mHandler);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//				progressLoading.setVisibility(View.GONE);
            }
        }
    };

    private void parseJSONWithGson(String jsonData, Handler handler) throws JSONException {
        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            JSONObject jsonObject2 = new JSONObject(jsonData);
            String jsonHeader = jsonObject2.getJSONObject("ResponseParams").getJSONArray("header").toString();
            String jsonArray = jsonObject2.getJSONObject("ResponseParams").getJSONArray("array").toString();

//            LogUtils.d(jsonHeader + "========json parse==========" + jsonArray);
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("header", jsonHeader);
            bundle.putString("data", jsonArray);
            msg.setData(bundle);
            handler.sendMessage(msg);
        } else
            return;
    }

    private int count;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            LogUtils.d(msg.getData() + "=============================================" + msg.obj);
            if (msg.getData() != null) {
                Bundle bundle = msg.getData();
                progressLoading.setVisibility(View.GONE);
                beanListHeader = parseListHeader(bundle.getString("header"));
                listData = parseListData(bundle.getString("data"));
                count = parseListData(bundle.getString("data")).size();
                if (beanListHeader != null && beanListHeader.size() > 0) {
//					LogUtils.d(beanListHeader.get(0).getNAME() + "-----------------------errfegfer------------------" + beanListHeader.size());
                    initView();
                }
            }
        }
    };


    protected List<BeanHeader> parseListHeader(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();
            LogUtils.d("jsonData===============" + jsonData);
            List<BeanHeader> list = gson.fromJson(jsonData, new TypeToken<List<BeanHeader>>() {
            }.getType());

            return list;
        } else
            return null;
    }

    protected List<LoanAfterBean> parseListData(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<LoanAfterBean> list = gson.fromJson(jsonData, new TypeToken<List<LoanAfterBean>>() {
            }.getType());

            return list;
        } else
            return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_key_loanafter, container, false);
        ButterKnife.bind(this, view);

        syncScroll(leftData, lvData);

        if (QueryDetails.type.equals("customer")) {
            CustomerType = QueryDetails.listCustomer.get(BusinessDetails.index).getCustomerType();
            CustomerId = QueryDetails.listCustomer.get(BusinessDetails.index).getCustomerId();
        } else if (QueryDetails.type.equals("approval_business")) {
            CustomerType = QueryDetails.listBusiness.get(BusinessDetails.index).getCustomerType();
            CustomerId = QueryDetails.listBusiness.get(BusinessDetails.index).getCustomerID();
        }
        sendRequest();
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
    private void sendRequest() {

        JSONObject jsonObject = ParamUtils.setParams("CustomerSearch", "crmLoanAfter", new Object[]{CustomerId, CurPage, PAGE_SIZE}, 3);
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handler);
    }

    private void initData() {
        mListTop = InitData.getTopHeaderData(beanListHeader);
        if (listData != null && listData.size() > 0) {
            mListLeft = InitData.getLeftHeaderData(listData.size());
            mListData = InitData.LoanAfter.getCellData(beanListHeader, listData);
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
        QueryDetails.layBacks.setEnabled(true);
    }

    @Override
    public void refresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CurPage = 1;
                if (listData != null)
                    checkObjValid(new Object());
                listData.clear();
                sendRequest();
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
                sendRequest();
                // 结束加载更多
                ptrLayout.finishLoadMore();
            }
        }, 2000);
    }else {
        toast("已全部加载完毕！");
        ptrLayout.finishLoadMore();
    }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
