package com.pactera.financialmanager.credit.main.service.approval;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dysen.common_res.common.base.ParentActivity;
import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.ParamUtils;
import com.pactera.financialmanager.R;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dysen on 2017/10/16.
 */
public class ApprovalActivity extends ParentActivity {

    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.rbtn_0)
    RadioButton rbtn0;
    @Bind(R.id.rbtn_1)
    RadioButton rbtn1;
    @Bind(R.id.rlv_data)
    RecyclerView rlvData;

    boolean finishedFlag = false;
    String FinishedFlag;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.obj != null){

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_approval);
        ButterKnife.bind(this);
        txtTitle.setText("审批");
        sendRequest();
        initView();
    }

    protected void sendRequest() {

        if (finishedFlag){
            FinishedFlag = "Y";
        }else
            FinishedFlag = "N";
        //{"deviceType":"Android","RequestParams":{"UserId":"B129103", "BSTypeFlag":"credit","FinishedFlag":"Y","SearchKey":"","CurPage":"1","PageSize":"10"}}
        JSONObject jsonObject = ParamUtils.setParams("examine", "examine", new Object[]{"credit", "B129103", "Y", "", curPage, ParamUtils.pageSize}, 1);
        HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handler);
    }

    private void initView() {

        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rlvData.setLayoutManager(layoutManager);

//        rlvData.setAdapter(new MyAdapter(this, ));
    }

    @OnClick({R.id.rbtn_0, R.id.rbtn_1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rbtn_0://未审批
                finishedFlag = false;
                break;
            case R.id.rbtn_1://已审批
                finishedFlag = true;
                break;
        }
            sendRequest();
    }

   /* private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        Context context;
        List<>

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }*/
}
