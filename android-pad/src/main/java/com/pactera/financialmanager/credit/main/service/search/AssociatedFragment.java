package com.pactera.financialmanager.credit.main.service.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dysen.common_res.common.base.ParentFragment;
import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.pullloadmore_recyclerview.PullLoadMoreRecyclerView;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.bean.CommonBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AssociatedFragment extends ParentFragment implements PullLoadMoreRecyclerView.PullLoadMoreListener {


    @Bind(R.id.pull_load_more)
    PullLoadMoreRecyclerView pullLoadMore;
    @Bind(R.id.pgb)
    ProgressBar pgb;
    @Bind(R.id.tv_hide_data)
    TextView tvHideData;

    private List<CommonBean.RelevanceBean> listData = new ArrayList<>();
    ;

    private String CustomerID;

    private int count;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pgb.setVisibility(View.INVISIBLE);
            if (msg.what == -1) {
//                    ShowDialog(QueryList.this, "无数据");
//                    toast("请求超时！");
                tvHideData.setVisibility(View.VISIBLE);
                tvHideData.setText("请求超时！");
                return;
            } else if (msg.what == -100) {
                tvHideData.setVisibility(View.VISIBLE);
            }
            if (msg.obj != null) {
                String json = null;
                try {
                    JSONObject jsonObject = HttpThread.parseJSON(msg.obj.toString());
                    if (jsonObject.has("array")) {
//                        if (transType.equals(getString(R.string.relevance_search))) {
                        if (jsonObject.getString("array").equals("[]")) {

                            jsonObject = new JSONObject(msg.obj.toString()).getJSONObject("ResponseParams");
                            String jsonArray = jsonObject.getString("ErrorMsg");
                            if (TextUtils.isEmpty(jsonArray)) {
                                tvHideData.setText("没有找到相关信息，请确保输入的客户名称及证件号均正确。");
                            } else {
                                tvHideData.setText(jsonArray);
                            }
                        }else {
                            json = HttpThread.parseJSONWithGson(msg.obj.toString());
                        }
//                        } else {
//                            if (jsonObject.getString("array").equals("")) {
//
//                                tvHideData.setVisibility(View.VISIBLE);
//                                return;
//                            }
//                        }

                    } else {

                        tvHideData.setVisibility(View.VISIBLE);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<CommonBean.RelevanceBean> list = HttpThread.parseList(json, CommonBean.RelevanceBean.class);
                if (list != null) {
                    count = list.size();

                    for (CommonBean.RelevanceBean relevanceBean : list) {
                        listData.add(relevanceBean);
                    }

                    if (listData != null && listData.size() > 0) {
                        initData(listData);
                    }
                }
            }
        }
    };
    private String transType;

    private void initData(final List<CommonBean.RelevanceBean> listData) {

        pullLoadMore.setAdapter(new AssociatedAdpater(getContext(), listData, new
                OnItemClickCallback<Integer>() {

            /**
             * 关联关系点击事件
             * @param view
             * @param info
             */

            @Override
            public void onClick(View view, Integer info) {

//                Bundle bundle = new Bundle();
//                bundle.putString("cusId", listData.get(info).getCustomerId());
//                intent.putExtra("CustomerTypes", listData.get(info).getCustomerType());
//                intent.putExtras(bundle);

                //获得选中项的值
//                Intent intent = new Intent(getContext(), QueryDetails.class);
////                toast(info+"~~~~"+listData.get(info).getCustomerName());
//                intent.putExtra("type", "Associated");
//                QueryDetails.setData(null, null, listData, info);

//                Bundle bundle1 = new Bundle();
//                bundle1.putString("CertID", listData.get(i).getCertID());
//                bundle1.putString("CustomerID", listData.get(i).getCustomerId());
//                bundle1.putString("CustomerType", listData.get(i).getCustomerType());
//                bundle1.putString("MobilePhone", listData.get(i).getMobilePhone());
//                bundle1.putString("IrscreditLevel", listData.get(i).getIrscreditLevel());
//                bundle1.putString("CustomerName", listData.get(i).getCustomerName());
//                intent1.putExtras(bundle1);
//                startActivity(intent);
//                if (transType.equals(getString(R.string.relevance_search))) {
//                    Intent intent = new Intent(mContext, ScreeningListActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("cusId", listData.get(info).getCustomerId());
//                    bundle.putString("transType", transType);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                }else {
//                    //获得选中项的值
//                    Intent intent = new Intent(mContext, QueryDetails.class);
////                toast(info+"~~~~"+listData.get(info).getCustomerName());
//                    intent.putExtra("type", "customer");
//                    QueryDetails.setData(listData, null, info);
////                Bundle bundle1 = new Bundle();
////                bundle1.putString("CertID", listData.get(i).getCertID());
////                bundle1.putString("CustomerID", listData.get(i).getCustomerId());
////                bundle1.putString("CustomerType", listData.get(i).getCustomerType());
////                bundle1.putString("MobilePhone", listData.get(i).getMobilePhone());
////                bundle1.putString("IrscreditLevel", listData.get(i).getIrscreditLevel());
////                bundle1.putString("CustomerName", listData.get(i).getCustomerName());
////                intent1.putExtras(bundle1);
//                    startActivity(intent);
//                }
//                http://192.168.1.100:9080/ALS7M/JSONService?method=crmRelationList{"deviceType":"Android","RequestParams":{"UserId":"system1","CustomerID":"20070108000052"}}
            }

            @Override
            public void onLongClick(View view, Integer info) {

            }

            @Override
            public void onClick(View view, int position, int index) {

            }
        }));
    }

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_associated_fragment, container, false);

        ButterKnife.bind(this, v);

        pullLoadMore.setGridLayout(2);
        pullLoadMore.setOnPullLoadMoreListener(this);
        sendRequest();

        return v;
    }

    /**
     * 关系查询
     */
    protected void sendRequest() {

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            CustomerID = bundle.getString("cusId");
        }

        JSONObject jsonObject = ParamUtils.setParams("crmCustomerRelation", "crmRelationList",
                new Object[]{ParamUtils.UserId, CustomerID}, 2);


        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handler);
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                curPage = 1;
                listData.clear();
                sendRequest();
                // 结束刷新
                pullLoadMore.setPullLoadMoreCompleted();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        if (count != 0 && count % Integer.parseInt(ParamUtils.pageSize) == 0) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    curPage++;
                    sendRequest();
                    // 结束刷新
                    pullLoadMore.setPullLoadMoreCompleted();
                }
            }, 2000);
        } else {
            toast("已全部加载完毕！");
            // 结束刷新
            pullLoadMore.setPullLoadMoreCompleted();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
