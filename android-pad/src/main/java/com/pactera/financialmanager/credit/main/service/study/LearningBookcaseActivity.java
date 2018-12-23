package com.pactera.financialmanager.credit.main.service.study;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.common_res.common.views.uber.UberProgressView;
import com.dysen.pullloadmore_recyclerview.PullLoadMoreRecyclerView;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.main.service.study.adapter.BookCaseAdpater;
import com.pactera.financialmanager.credit.main.service.study.bean.BookCaseModel;
import com.pactera.financialmanager.ui.ParentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LearningBookcaseActivity extends ParentActivity {

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

    private List<BookCaseModel> bookCaseModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_bookcase);
        ButterKnife.bind(this);
        initTitle(this, "", false,"");


        initView();
        sendRequest();
    }

    private void initView() {
        pullLoadMore.setStaggeredGridLayout(3);
        pullLoadMore.setPushRefreshEnable(false);
        pullLoadMore.setPullRefreshEnable(false);
        Intent intent = getIntent();
        String Name = intent.getStringExtra("Name");
        String NameInfo = intent.getStringExtra("NameInfo");
        initTitle(this, Name, true,NameInfo);

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


    /**
     * 数据初始化
     */
    private void sendRequest() {
//http://192.168.1.100:9080/ALS7M/JSONService?method=crmStudyType
//        String url = "http://192.168.1.100:9080/ALS7M/JSONService?method=crmStudyType";
        JSONObject jsonObject = ParamUtils.setParams("study", "crmStudyType",
                new Object[]{ParamUtils.UserId}, 1);
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, conditionsHandler);
    }

    /**
     * 解析数据
     */
    @SuppressLint("HandlerLeak")
    private Handler conditionsHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            uberPgsview.setVisibility(View.GONE);
            if (msg.what == -1) {
                tvHideData.setVisibility(View.VISIBLE);
                tvHideData.setText(R.string.request_timeout);
            } else if (msg.what == -100) {

                tvHideData.setVisibility(View.VISIBLE);
            }

            if (msg.obj != null) {

                String CustomerQueryGson = null;
                try {
                    CustomerQueryGson = HttpThread.parseJSONWithGson(msg.obj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                bookCaseModelList = HttpThread.parseList(CustomerQueryGson, BookCaseModel.class);

                BookCaseAdpater adpater = new BookCaseAdpater(bookCaseModelList,
                        LearningBookcaseActivity.this);
                pullLoadMore.setAdapter(adpater);
            }
        }
    };
}
