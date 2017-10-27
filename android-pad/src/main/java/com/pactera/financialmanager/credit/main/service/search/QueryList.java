package com.pactera.financialmanager.credit.main.service.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dysen.common_res.common.base.ParentActivity;
import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.ParamUtils;
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

public class QueryList extends ParentActivity {
    @Bind(R.id.txt_back)
    TextView txtBack;
    @Bind(R.id.lay_back)
    LinearLayout layBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_)
    TextView txt;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.pgb)
    ProgressBar pgb;
    @Bind(R.id.tv_hide_data)
    TextView tvHideData;

    private Context mContext;
    private List<CustomerItem> customerItem = new ArrayList<>();;
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
            if (msg.what == -1) {
//                    ShowDialog(QueryList.this, "无数据");
//                    toast("无数据");
                tvHideData.setVisibility(View.VISIBLE);
                tvHideData.setText("请求"+msg.obj);
                return;
            }else if (msg.what == -100){
                tvHideData.setVisibility(View.VISIBLE);
            }
            pgb.setVisibility(View.INVISIBLE);
            if (msg.obj != null) {
                String json = HttpThread.parseJSONWithGson(msg.obj.toString());
                List<ListPerson> list = parseList(json);

                if (list != null && list.size() > 0){
                    initData(list);
                }
            }
        }
    };

    private void initData(final List<ListPerson> list) {

        for (int i = 0; i < list.size(); i++) {
            customerItem.add(new CustomerItem(
                    list.get(i).getIrscreditLevel(),
                    list.get(i).getCustomerName(),
                    list.get(i).getCertID(),
                    list.get(i).getMobilePhone()
            ));
        }

        listView.setAdapter(new MyAdaptor(mContext, customerItem));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //获得选中项的值

                Intent intent = new Intent(mContext, QueryDetails.class);

                intent.putExtra("type", "customer");
                QueryDetails.setData(list, null, i);
//                Bundle bundle1 = new Bundle();
//                bundle1.putString("CertID", list.get(i).getCertID());
//                bundle1.putString("CustomerID", list.get(i).getCustomerId());
//                bundle1.putString("CustomerType", list.get(i).getCustomerType());
//                bundle1.putString("MobilePhone", list.get(i).getMobilePhone());
//                bundle1.putString("IrscreditLevel", list.get(i).getIrscreditLevel());
//                bundle1.putString("CustomerName", list.get(i).getCustomerName());
//                intent1.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }

    protected List<ListPerson> parseList(String jsonData) throws JsonSyntaxException {

        if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
            Gson gson = new Gson();

            List<ListPerson> list = gson.fromJson(jsonData, new TypeToken<List<ListPerson>>() {
            }.getType());

            return list;
        } else
            return null;
    }

    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_list);
        ButterKnife.bind(this);
        mContext = this;
        txtTitle.setText(getString(R.string.customer_list));

        initView();
        sendRequest();
    }

    protected void sendRequest() {
        //客户查询    crmCustomerQuery    CustomerType:客户类型（必输项，不能为空）,CustomerName:客户名称, CertTypeName:证件类型,
        // CertID:证件号码, MobilePhone:手机号码, IrscreditLevel:评级等级, UserID:登陆用户id CurPage:当前页码, PageSize:每页显示的条数
        JSONObject jsonObject = ParamUtils.setParams("CustomerSearch", "crmCustomerQuery", new Object[]{CustomerType
                        , CustomerName, CertTypeName, CertID, MobilePhone, IrscreditLevel,ParamUtils.UserId, curPage, ParamUtils.pageSize}, 9);
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handler);
    }

    private void initView() {
        Bundle bundle = this.getIntent().getExtras();
        CustomerType = bundle.getString("CustomerType");
        CustomerName = bundle.getString("CustomerName");
        CertTypeName = bundle.getString("CertTypeName");
        CertID = bundle.getString("CertID");
        MobilePhone = bundle.getString("MobilePhone");
        IrscreditLevel = bundle.getString("IrscreditLevel");
    }
}
