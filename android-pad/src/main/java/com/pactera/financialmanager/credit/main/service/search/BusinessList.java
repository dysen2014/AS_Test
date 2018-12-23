package com.pactera.financialmanager.credit.main.service.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.pullloadmore_recyclerview.PullLoadMoreRecyclerView;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017/7/28.
 */

public class BusinessList extends ParentActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {
    @Bind(R.id.txt_back)
    TextView txtBack;
    @Bind(R.id.lay_back)
    LinearLayout layBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_)
    TextView txt;
    @Bind(R.id.pgb)
    ProgressBar pgb;
    @Bind(R.id.tv_hide_data)
    TextView tvHideData;
    @Bind(R.id.pull_load_more)
    PullLoadMoreRecyclerView pullLoadMore;

    private Context mContext;
    int count;
    private List<ListBusinessList> listData = new ArrayList<>();
    private String CustomerType, CustomerName, CertType, CertID, MobileTelephone, BusinessTypeBg,
            SortNo
    , ClassifyResult, BusinessSum, OverDueDay;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pgb.setVisibility(View.INVISIBLE);
            if (msg.what == -100) {
//                    ShowDialog(BusinessList.this, "无数据");
//                    toast("无数据");
                tvHideData.setVisibility(View.VISIBLE);
                return;
            }
            if (msg.obj != null) {
                String json = null;
                try {
                    JSONObject jsonObject = HttpThread.parseJSON(msg.obj.toString());
                    if (jsonObject.has("array")) {
                        if (jsonObject.getString("array").equals("")) {

                            tvHideData.setVisibility(View.VISIBLE);
                            return;
                        }
                    }
                    json = HttpThread.parseJSONWithGson(msg.obj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<ListBusinessList> list = HttpThread.parseList(json, ListBusinessList.class);
                count = list.size();
                for (ListBusinessList business : list) {
                    listData.add(business);
                }
                if (listData != null && listData.size() > 0) {
                    initData(listData);
                }
            }
        }
    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_list);
        ButterKnife.bind(this);
        initTitle(this, "", false,"");

        initView();
        sendRequest();
    }

    protected void sendRequest() {
        //crmBusinessQuery 业务查询
        // UserID：登陆用户, BusinessSum:合同金额, OverDueDay:逾期天数, CertType:证件类型, OverDueBalance:逾期金额, LcaTimes:逾期期数, ClassifyResult:五级分类
        // , BusinessTypeBg:业务产品, CustomerName:客户名称,CustomerType:客户类型,MobileTelephone:手机号码, Balance:贷款余额,ActualMaturity:到期日期
        // ,ActualPutOutDate:发放日期,CertID:证件号码, CurPage:页码, PageSize:每页条数
        JSONObject jsonObject = ParamUtils.setParams("search", "crmBusinessQuery", new Object[]{ParamUtils.UserId, BusinessSum
                , OverDueDay, CertType, "", "", ClassifyResult, SortNo, CustomerName, CustomerType,
                MobileTelephone, ""
                , "", "", CertID, curPage, ParamUtils.pageSize}, 17);
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handler);
    }

    private void initView() {
        txtTitle.setText(getString(R.string.business_list));
        mContext = this;
        final Bundle bundle = this.getIntent().getExtras();
        CustomerType = bundle.getString("CustomerType");
        CustomerName = bundle.getString("CustomerName");
        CertType = bundle.getString("CertType");
        CertID = bundle.getString("CertID");
        MobileTelephone = bundle.getString("MobileTelephone");
//        BusinessTypeBg = bundle.getString("BusinessTypeBg");
        SortNo = bundle.getString("SortNo");
        ClassifyResult = bundle.getString("ClassifyResult");
        BusinessSum = bundle.getString("BusinessSum");
        OverDueDay = bundle.getString("OverDueDay");

        pullLoadMore.setGridLayout(2);
        pullLoadMore.setOnPullLoadMoreListener(this);
        if (layBack != null) {
            layBack.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    finish();
                }
            });
        }
    }

    private void initData(final List<ListBusinessList> listData) {

        pullLoadMore.setAdapter(new MyAdapter.BusinessListAdapter(mContext, listData, new OnItemClickCallback<Integer>() {
            @Override
            public void onClick(View view, Integer info) {
                //获得选中项的值
                Intent intent = new Intent(mContext, BusinessDetails.class);
                BusinessDetails.setData(listData, info);
//                    Bundle bundle1 = new Bundle();
//                    bundle1.putString("CustomerName", list.get(i).getCustomerName());
//                    bundle1.putString("CustomerType", list.get(i).getCustomerType());
//                    bundle1.putString("BusinessName", list.get(i).getBusinessName());
//                    bundle1.putString("BusinessSum", list.get(i).getBusinessSum());
//                    bundle1.putString("Balance", list.get(i).getBalance());
//                    bundle1.putString("OverDueBalance", list.get(i).getOverDueBalance());
//                    bundle1.putString("InterestBalance", list.get(i).getInterestBalance());
//                    bundle1.putString("CustomerID", list.get(i).getCustomerID());
//                    bundle1.putString("SerialNo", list.get(i).getSerialNo());
//                    bundle1.putString("TypeNo", list.get(i).getTypeNo());
//                    bundle1.putString("ContractNo", list.get(i).getContractNo());
//                    intent1.putExtras(bundle1);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, Integer info) {

            }

            @Override
            public void onClick(View view, int position, int index) {

            }
        }));
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
}