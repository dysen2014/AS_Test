package com.pactera.financialmanager.credit.main.service.search;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017/7/17.
 */

public class BusinessCountractFragment extends Fragment {

    @Bind(R.id.pgb)
    ProgressBar pgb;
    @Bind(R.id.tv_hide_data)
    TextView tvHideData;
    @Bind(R.id.pull_load_more)
    PullLoadMoreRecyclerView pullLoadMore;

    private Context mContext;
    private List<InfoItem> DuebillInfoItem = new ArrayList<>();
    private String SerialNo, BusinessType;

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
                List<ListDuebillInfo> list = new ArrayList<>();
                try {
                    list = parseList(HttpThread.parseJSON(msg.obj.toString()).getJSONArray("array")
                            .getJSONObject(0).getJSONArray("groupColArray").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pgb.setVisibility(View.INVISIBLE);
                if (list != null)
                    initData(list);
            }
        }
    };

    private void initData(List<ListDuebillInfo> list) {
        for (int i = 0; i < list.size(); i++) {
            DuebillInfoItem.add(new InfoItem(
                    list.get(i).getKeyName(),
                    list.get(i).getValue()
            ));
        }

        pullLoadMore.setAdapter(new MyAdaptorInfo.CommonListAdapter(mContext, DuebillInfoItem));
    }

    protected List<ListDuebillInfo> parseList(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<ListDuebillInfo> list = gson.fromJson(jsonData, new TypeToken<List<ListDuebillInfo>>() {
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
        //crmBusinessCountract   业务查询table-合同详情  SerialNo：合同流水号(取业务查询里的ContractNo)，BusinessType：业务品种(取业务查询里的TypeNo)
        JSONObject jsonObject = ParamUtils.setParams("search", "crmBusinessCountract", new Object[]{SerialNo, BusinessType}, 2);
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handler);
    }

    private void initView() {
        DuebillInfoItem = new ArrayList<>();
        mContext = getActivity();
//        SerialNo = BusinessDetails.listData.get(BusinessDetails.index).getSerialNo();
        SerialNo = BusinessDetails.listData.get(BusinessDetails.index).getContractNo();
        BusinessType = BusinessDetails.listData.get(BusinessDetails.index).getTypeNo();
        pullLoadMore.setGridLayout(2);
        pullLoadMore.setPushRefreshEnable(false);
        pullLoadMore.setPullRefreshEnable(false);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
