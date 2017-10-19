package com.pactera.financialmanager.credit.main.warn;

import android.annotation.SuppressLint;
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
import android.view.ViewStub;
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
import com.pactera.financialmanager.credit.common.adapters.ChildDataAdapter;
import com.pactera.financialmanager.credit.common.adapters.CommonTxtDataAdapter;
import com.pactera.financialmanager.credit.common.adapters.DataAdapter;
import com.pactera.financialmanager.credit.common.adapters.DataAdapterNew;
import com.pactera.financialmanager.credit.common.adapters.LeftAdapter;
import com.pactera.financialmanager.credit.common.adapters.TopDataAdapter;
import com.pactera.financialmanager.credit.common.bean.InitData;
import com.pactera.financialmanager.credit.common.bean.http.AfterTheLoan;
import com.pactera.financialmanager.credit.common.bean.http.BeanHeader;
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
 * 贷后提醒
 */

public class AfterWarnFragment extends ParentFragment implements BaseRefreshListener {

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
    //	@Bind(R.id.stub)
    ViewStub stub;
    @Bind(R.id.progress_loading)
    ProgressBar progressLoading;
    @Bind(R.id.common_txt_rlv)
    RecyclerView commonTxtRlv;
    @Bind(R.id.tv_hide_data)
    TextView tvHideData;

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
    private static List<String> topName = new ArrayList<>();
    private int index1;
    int curPage = 1;

    private List<BeanHeader> afterTheLoanListHeader = new ArrayList<>();
    private List<AfterTheLoan> afterTheLoanList = new ArrayList<>();

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj != null) {

                try {
                    HttpThread.parseJSONWithGson((String) msg.obj, mHandler);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressLoading.setVisibility(View.GONE);
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
                afterTheLoanListHeader = parseListHeader(bundle.getString("header"));
//                List<AfterTheLoan> list = parseList(bundle.getString("data"));
//
//                for (int i = 0; i < list.size(); i++) {
//
//                    afterTheLoanList.add(list.get(i));
//                }
                afterTheLoanList = parseList(bundle.getString("data"));
                if (afterTheLoanListHeader.size() > 0)
                    initView();
            }
        }
    };
    private static String mItemNo;

    protected List<BeanHeader> parseListHeader(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<BeanHeader> list = gson.fromJson(jsonData, new TypeToken<List<BeanHeader>>() {
            }.getType());

            return list;
        } else
            return null;
    }

    protected List<AfterTheLoan> parseList(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<AfterTheLoan> list = gson.fromJson(jsonData, new TypeToken<List<AfterTheLoan>>() {
            }.getType());

            return list;
        } else
            return null;
    }

    private void setList(List<AfterTheLoan> dataList) {
        afterTheLoanList = dataList;
    }

    public static void setItenNo(String itemNo) {
        mItemNo = itemNo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_later_warn, null);
        ButterKnife.bind(this, view);
        sendRequest();

        return view;
    }

    private void sendRequest() {
        JSONObject jsonObject = ParamUtils.setParams("warn", "crmAfterTheLoan", new Object[]{ParamUtils.UserId, mItemNo, ParamUtils.pageSize, curPage}, 4);
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handler);
    }

    private void initData() {

        mListTop = InitData.getTopHeaderData(afterTheLoanListHeader);
        if (checkObjValid(afterTheLoanList)) {
            checkDataValid(afterTheLoanList.size());
            mListLeft = InitData.getLeftHeaderData(afterTheLoanList.size());
            mListData = InitData.AfterWarn.getCellData(afterTheLoanListHeader, afterTheLoanList);
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
                sendRequest();
                // 结束刷新
                ptrLayout.finishRefresh();
            }
        }, 2000);
    }

    @Override
    public void loadMore() {
//        if (mListLeft.size() < Integer.parseInt(afterTheLoanList.get(0).getCount())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    curPage++;
                    sendRequest();
                    // 结束加载更多
                    ptrLayout.finishLoadMore();
                }
            }, 2000);
//        }else {
//            toast("已全部加载完毕！");
//            ptrLayout.finishLoadMore();
//        }
    }

    private void clearList() {

        afterTheLoanList.clear();
        afterTheLoanListHeader.clear();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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

//        afterTheLoanList.clear();
        sendRequest();
        setBgAndTextColor(commonTxt0);
    }
}
