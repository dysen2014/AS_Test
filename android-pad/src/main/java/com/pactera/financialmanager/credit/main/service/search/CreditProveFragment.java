package com.pactera.financialmanager.credit.main.service.search;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.pullloadmore_recyclerview.PullLoadMoreRecyclerView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 贷款证(农户)
 * Created by lenovo on 2017/7/17.
 */

public class CreditProveFragment extends ParentFragment {

    @Bind(R.id.pgb)
    ProgressBar pgb;
    @Bind(R.id.tv_hide_data)
    TextView tvHideData;
    @Bind(R.id.pull_load_more)
    PullLoadMoreRecyclerView pullLoadMore;
    private String customerType, customerId;
    private List<InfoItem> InfoItem;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("msg", String.valueOf(msg.obj));
            pgb.setVisibility(View.GONE);
            if (msg.what == -1) {
//                    ShowDialog(QueryList.this, "无数据");
//                    toast("无数据");
                tvHideData.setVisibility(View.VISIBLE);
                tvHideData.setText("请求" + msg.obj);
                return;
            } else if (msg.what == -100) {
                tvHideData.setVisibility(View.VISIBLE);
            }

            if (msg.obj != null) {
                List<ListCreditProve> list = new ArrayList<>();
                try {
                    list = parseList(HttpThread.parseJSON(msg.obj.toString()).getJSONArray("array")
                            .getJSONObject(0).getJSONArray("groupColArray").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (list != null)
                    initData(list);
            }
        }
    };

    private void initData(List<ListCreditProve> list) {
        for (int i = 0; i < list.size(); i++) {
            InfoItem.add(new InfoItem(
                    list.get(i).getKeyName(),
                    list.get(i).getValue()
            ));
        }

        pullLoadMore.setAdapter(new MyAdaptorInfo.CommonListAdapter(getActivity(), InfoItem));
    }

    protected List<ListCreditProve> parseList(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<ListCreditProve> list = gson.fromJson(jsonData, new TypeToken<List<ListCreditProve>>() {
            }.getType());

            return list;
        } else
            return null;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
//        inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.query_details_info, container, false);
        ButterKnife.bind(this, v);

        initView();
        sendRequest();

        return v;
    }

    protected void sendRequest() {
        //客户查询table-基本信息 crmCustomerInfo CustomerID：客户id
        //客户查询tab-贷款证（农户） crmCreditProve  CustomerID：客户id(目前1.100里，只有20070927000004 有数据)
        JSONObject jsonObject = ParamUtils.setParams("CustomerSearch", "crmCreditProve", new Object[]{customerId}, 1);
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handler);
    }

    private void initView() {

        InfoItem = new ArrayList<>();

        if (QueryDetails.type.equals("customer")) {
            customerType = QueryDetails.listCustomer.get(QueryDetails.index).getCustomerType();
            customerId = QueryDetails.listCustomer.get(QueryDetails.index).getCustomerId();
        } else if (QueryDetails.type.equals("business")) {
            customerType = QueryDetails.listBusiness.get(BusinessDetails.index).getCustomerType();
            customerId = QueryDetails.listBusiness.get(BusinessDetails.index).getCustomerID();
        }
        pullLoadMore.setGridLayout(2);
        pullLoadMore.setPullRefreshEnable(false);
        pullLoadMore.setPushRefreshEnable(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
