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

import com.dysen.common_res.common.base.ParentActivity;
import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.pullloadmore_recyclerview.PullLoadMoreRecyclerView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.pactera.financialmanager.R;

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
                String json = HttpThread.parseJSONWithGson(msg.obj.toString());
                listData = parseList(json);

                if (listData != null && listData.size() > 0) {
                    initData(listData);
                }
            }
        }
    };

    private void initData(final List<ListPerson> listData) {

        pullLoadMore.setAdapter(new MyAdapter.CustomerListAdapter(mContext, listData, new OnItemClickCallback<Integer>() {
            @Override
            public void onClick(View view, Integer info) {
                //获得选中项的值
                Intent intent = new Intent(mContext, QueryDetails.class);
                toast(info+"~~~~"+listData.get(info).getCustomerName());
                intent.putExtra("type", "customer");
                QueryDetails.setData(listData, null, info);
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

            @Override
            public void onLongClick(View view, Integer info) {

            }
        }));
    }

    protected List<ListPerson> parseList(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<ListPerson> listData = gson.fromJson(jsonData, new TypeToken<List<ListPerson>>() {
            }.getType());

            return listData;
        } else
            return null;
    }

    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_list);
        ButterKnife.bind(this);

        initView();
        sendRequest();
    }

    protected void sendRequest() {
        //客户查询    crmCustomerQuery    CustomerType:客户类型（必输项，不能为空）,CustomerName:客户名称, CertTypeName:证件类型,
        // CertID:证件号码, MobilePhone:手机号码, IrscreditLevel:评级等级, UserID:登陆用户id CurPage:当前页码, PageSize:每页显示的条数
        JSONObject jsonObject = ParamUtils.setParams("CustomerSearch", "crmCustomerQuery", new Object[]{CustomerType
                , CustomerName, CertTypeName, CertID, MobilePhone, IrscreditLevel, ParamUtils.UserId, curPage, ParamUtils.pageSize}, 9);
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handler);
    }

    private void initView() {
        mContext = this;
        txtTitle.setText(getString(R.string.customer_list));
        Bundle bundle = this.getIntent().getExtras();
        CustomerType = bundle.getString("CustomerType");
        CustomerName = bundle.getString("CustomerName");
        CertTypeName = bundle.getString("CertTypeName");
        CertID = bundle.getString("CertID");
        MobilePhone = bundle.getString("MobilePhone");
        IrscreditLevel = bundle.getString("IrscreditLevel");

        pullLoadMore.setGridLayout(2);
        pullLoadMore.setOnPullLoadMoreListener(this);

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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                curPage++;
                sendRequest();
                // 结束刷新
                pullLoadMore.setPullLoadMoreCompleted();
            }
        }, 2000);
    }
}
