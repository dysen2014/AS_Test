package com.pactera.financialmanager.credit.main.service.cus_screening;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.common_res.common.views.uber.UberProgressView;
import com.dysen.pullloadmore_recyclerview.PullLoadMoreRecyclerView;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.bean.CommonBean;
import com.pactera.financialmanager.credit.main.service.search.BusinessDetails;
import com.pactera.financialmanager.credit.main.service.search.ListBusinessList;
import com.pactera.financialmanager.credit.main.service.search.MyAdapter;
import com.pactera.financialmanager.ui.ParentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ScreeningListActivity extends ParentActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {

    @Bind(R.id.txt_back)
    TextView txtBack;
    @Bind(R.id.lay_back)
    LinearLayout layBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_)
    TextView txt;
    @Bind(R.id.pull_load_more)
    PullLoadMoreRecyclerView pullLoadMore;
    @Bind(R.id.tv_hide_data)
    TextView tvHideData;
    @Bind(R.id.uber_pgsview)
    UberProgressView uberPgsview;

    private String transType;
    private String cusId;
    private String CusType;
    private String CusName;
    private String CertTypeName;
    private String CertID;
    private int count, transIndex;

    private JSONObject params;
    List<CommonBean.ScreeningBean> listScreening = new ArrayList<>();
    List<CommonBean.RelevanceBean> listRelevance = new ArrayList<>();
    List<ListBusinessList> listData = new ArrayList<>();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            uberPgsview.setVisibility(View.INVISIBLE);
            if (msg.what == -1) {
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
                        if (transType.equals(getString(R.string.cus_screening))){
                            if (jsonObject.getString("array").equals("[]")){

                                jsonObject = new JSONObject(msg.obj.toString()).getJSONObject("ResponseParams");
                                String jsonArray = jsonObject.getString("ErrorMsg");
                                if (TextUtils.isEmpty(jsonArray)){
                                    tvHideData.setText("此客户不在本行黑灰名单中");
                                }else {
                                    tvHideData.setText(jsonArray);
                                }
                            }
                        }else {
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
                switch (msg.what){
                    case 0:
                        List<CommonBean.ScreeningBean> list0 = HttpThread.parseList(json,
                                CommonBean.ScreeningBean.class);
                        if (list0 != null){
                            count = list0.size();
                            for (CommonBean.ScreeningBean bean : list0){
                                listScreening.add(bean);
                            }
                            if (listScreening != null && listScreening.size() > 0) {
                                initData(0);
                            }
                        }

                    break;
                    case 1:
                        List<CommonBean.RelevanceBean> list1 = HttpThread.parseList(json,
                                CommonBean.RelevanceBean.class);
                        if (list1 != null){
                            count = list1.size();
                            for (CommonBean.RelevanceBean bean : list1){
                                listRelevance.add(bean);
                            }
                            if (listRelevance != null && listRelevance.size() >0){
                                initData(1);
                            }
                        }

                    break;
                }
            }
        }
    };

    private void initData(int index) {
        if (index == 0){
            pullLoadMore.setAdapter(new MyAdapter.ScreeningAdapter(this, listScreening));
        }else if (index == 1){
            pullLoadMore.setAdapter(new MyAdapter.RelevanceAdapter(this, listRelevance, new OnItemClickCallback<Integer>() {
                @Override
                public void onClick(View view, Integer info) {
                CommonBean.RelevanceBean relevanceBean = listRelevance.get(info);
                    LogUtils.d(info+"\tlistRelevance="+listRelevance+"\nSerialNo="+relevanceBean
                            .getSerialNo());
                    Intent intent = new Intent(aty, BusinessDetails.class);
                    listData.clear();
                    listData.add(new ListBusinessList("", "", "", "", "", "", relevanceBean.getCustomerType(),
                            relevanceBean.getCustomerID(), relevanceBean.getSerialNo(),
                            relevanceBean.getBusinessType(), relevanceBean.getContractNo()));
                    BusinessDetails.setData(listData, 0);//此处用0替代info下标
                    // 是因为此处的listData是自己添加数据的，listData始终只有一条数据
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening_list);
        ButterKnife.bind(this);
        initTitle(this, "", false,"");
        initViews();
    }

    private void initViews() {
        backActivity(this, layBack);

        Bundle bundle = this.getIntent().getExtras();
        transType = bundle.getString("transType");
        txtTitle.setText(transType);

        pullLoadMore.setGridLayout(2);
        pullLoadMore.setOnPullLoadMoreListener(this);
//        for (int i = 0; i < 20; i++) {
//
//        listScreening.add(new CommonBean.ScreeningBean("sendy"+i, "42212619881103151"+(i%10), new
//                Date()+"\t录入",
//                "marksawdfer jirig nreig nioernfgier mer " +
//                        "kfjweio偶尔of而哦哦耳机哦oreoosdferflk90wefokdlk盘丝洞佛看破of恐怕"));
//        listRelevance.add(new CommonBean.RelevanceBean("sendy"+i, "mebing", "业务品种" ,"300000",
//                "1000000", "27天", "借据流水号", "客户ID", "客户类型", "合同流水号", "业务品种"));
//        }
        if (transType.equals(getString(R.string.cus_screening))) {
            CusName = bundle.getString("CustomerName");
            CertTypeName = bundle.getString("CertTypeName");
            CertID = bundle.getString("CertID");
            initData(0);
        } else if (transType.equals(getString(R.string.relevance_search))) {
            CusType = bundle.getString("CustomerType");
            cusId = bundle.getString("cusId");
            initData(1);
        }
        initParam();
    }

    private void sendRequest(JSONObject params, int index) {
        LogUtils.d("*******************ParamUtils.url="+ParamUtils.url);
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, params, handler, index);
    }

    private void initParam() {
        if (transType.equals(getString(R.string.cus_screening))) {//黑灰名单
            //UserId：登陆用户,CustomerName：客户名称，CertTypeName：证件类型, CertId：证件号, CurPage:页码, PageSize:每页条数
             params = ParamUtils.setParams("crmAlarmBlackQuery", "crmAlarmBlackQuery",
                    new Object[]{ParamUtils.UserId, CusName, CertTypeName, CertID, curPage, ParamUtils
                            .pageSize}, 6);
            transIndex = 0;
        } else if (transType.equals(getString(R.string.relevance_search))) {//关联查询
            //UserId：登陆用户,CustomerID：客户ID(由“客户查询”获取)
            params = ParamUtils.setParams("crmCustomerRelation", "crmCustomerRelation", new Object[]{
                    ParamUtils.UserId, cusId}, 2);
//            params = ParamUtils.setParams("crmCustomerRelation", "crmCustomerRelation", new Object[]{
//                    "system1", "20091109001056"}, 2);
            transIndex = 1;
        }
        sendRequest(params, transIndex);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                curPage = 1;
                listData.clear();
                sendRequest(params, transIndex);
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
                    sendRequest(params, transIndex);
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
