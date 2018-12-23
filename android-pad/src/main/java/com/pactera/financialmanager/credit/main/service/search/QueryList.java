package com.pactera.financialmanager.credit.main.service.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.pullloadmore_recyclerview.PullLoadMoreRecyclerView;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.main.service.cus_screening.ScreeningListActivity;
import com.pactera.financialmanager.ui.ParentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017/7/11.
 */

public class QueryList extends ParentActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {
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
    private List<ListPerson> listData = new ArrayList<>();
    ;
    private String CustomerType;
    private String CustomerName;
    private String CertID;
    private String MobilePhone;
    private String CertTypeName;
    private String IrscreditLevel;

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
                        if (transType.equals(getString(R.string.relevance_search))) {
                            if (jsonObject.getString("array").equals("[]")) {

                                jsonObject = new JSONObject(msg.obj.toString()).getJSONObject("ResponseParams");
                                String jsonArray = jsonObject.getString("ErrorMsg");
                                if (TextUtils.isEmpty(jsonArray)) {
                                    tvHideData.setText("没有找到相关信息，请确保输入的客户名称及证件号均正确。");
                                } else {
                                    tvHideData.setText(jsonArray);
                                }
                            }
                        } else {
                            if (jsonObject.getString("array").equals("")) {

                                tvHideData.setVisibility(View.VISIBLE);
                                return;
                            }
                        }

                    }
                    json = HttpThread.parseJSONWithGson(msg.obj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<ListPerson> list = HttpThread.parseList(json, ListPerson.class);
                count = list.size();
                for (ListPerson person : list) {
                    listData.add(person);
                }

                if (listData != null && listData.size() > 0) {
                    initData(listData);
                }
            }
        }
    };
    private String transType;

    private void initData(final List<ListPerson> listData) {

        pullLoadMore.setAdapter(new MyAdapter.CustomerListAdapter(mContext, listData, new OnItemClickCallback<Integer>() {

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


                if (transType.equals(getString(R.string.relevance_search))) {
//                    Intent intent = new Intent(mContext, ScreeningListActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("cusId", listData.get(info).getCustomerId());
//                    bundle.putString("transType", transType);
//                    intent.putExtras(bundle);
//                    startActivity(intent);

                    Intent intent = new Intent();
                    intent.putExtra("cusId",listData.get(info).getCustomerId());
                    intent.putExtra("CustomerTypes",listData.get(info).getCustomerType());
                    intent.setClass(mContext, CorrelationActivity.class);
                    startActivity(intent);
                }else {
                    //获得选中项的值
                    Intent intent = new Intent(mContext, QueryDetails.class);
//                toast(info+"~~~~"+listData.get(info).getCustomerName());
                    intent.putExtra("type", "customer");
                    QueryDetails.setData(listData, null,null, info);
//                Bundle bundle1 = new Bundle();
//                bundle1.putString("CertID", listData.get(i).getCertID());
//                bundle1.putString("CustomerID", listData.get(i).getCustomerId());
//                bundle1.putString("CustomerType", listData.get(i).getCustomerType());
//                bundle1.putString("MobilePhone", listData.get(i).getMobilePhone());
//                bundle1.putString("IrscreditLevel", listData.get(i).getIrscreditLevel());
//                bundle1.putString("CustomerName", listData.get(i).getCustomerName());
//                intent1.putExtras(bundle1);
                    startActivity(intent);
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

    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_list);
        ButterKnife.bind(this);
        initTitle(this, "", false, "");
        initView();

    }

    /**
     * 客户查询
     */
    protected void sendRequest() {
        //客户查询    crmCustomerQuery    CustomerType:客户类型（必输项，不能为空）,CustomerName:客户名称, CertTypeName:证件类型,
        // CertID:证件号码, MobilePhone:手机号码, IrscreditLevel:评级等级, UserID:登陆用户id CurPage:当前页码, PageSize:每页显示的条数
        JSONObject jsonObject = ParamUtils.setParams("CustomerSearch", "crmCustomerQuery", new Object[]{CustomerType
                , CustomerName, CertTypeName, CertID, MobilePhone, IrscreditLevel, ParamUtils.UserId, curPage, ParamUtils.pageSize}, 9);

        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handler);
    }

    /**
     * 关联关系
     */
    protected void correlationRequest() {
        JSONObject jsonObject = ParamUtils.setParams("CustomerSearch", "crmCustomerQuery",
                new Object[]{CustomerType, ParamUtils.orgId, "", "G", CustomerName, CertTypeName, CertID,
                        "", ParamUtils.UserId, ParamUtils.pageSize, curPage}, 10);

//        new Object[]{intent.getStringExtra("CustomerIndex"), RemindingActivity.creditModel.getOrgID()
//                ,"","G",intent.getStringExtra("customerName"),
//                intent.getStringExtra("TypeNumberIndex"), intent.getStringExtra("numberId"),""
//                , RemindingActivity.creditModel.getUserID(), ParamUtils.PAGE_SIZE5, curPage}, 10);

        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handler);
    }

    private void initView() {
        mContext = this;
        txtTitle.setText(getString(R.string.customer_list));
        Bundle bundle = this.getIntent().getExtras();
        transType = bundle.getString("transType");
        if (transType.equals(getString(R.string.relevance_search))) {
            txtTitle.setText(transType);
            CustomerType = bundle.getString("CustomerType");
            CustomerName = bundle.getString("CustomerName");
            CertTypeName = bundle.getString("CertTypeName");
            CertID = bundle.getString("CertID");
            MobilePhone = "";
            IrscreditLevel = "";
            correlationRequest();
        } else {
            CustomerType = bundle.getString("CustomerType");
            CustomerName = bundle.getString("CustomerName");
            CertTypeName = bundle.getString("CertTypeName");
            CertID = bundle.getString("CertID");
            MobilePhone = bundle.getString("MobilePhone");
//            IrscreditLevel = bundle.getString("IrscreditLevel");
            IrscreditLevel = "";
            sendRequest();
        }

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
