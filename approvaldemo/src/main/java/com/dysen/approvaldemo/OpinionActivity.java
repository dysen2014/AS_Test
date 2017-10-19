package com.dysen.approvaldemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.common_res.common.base.ParentActivity;
import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.views.ViewUtils;
import com.yinglan.alphatabs.AlphaTabView;
import com.yinglan.alphatabs.AlphaTabsIndicator;
import com.yinglan.alphatabs.OnTabChangedListner;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OpinionActivity extends ParentActivity {

    @Bind(R.id.txt_back)
    TextView txtBack;
    @Bind(R.id.lay_back)
    LinearLayout layBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_)
    TextView txt;
    @Bind(R.id.rlv_data)
    RecyclerView rlvData;
    @Bind(R.id.tab_0)
    AlphaTabView tab0;
    @Bind(R.id.tab_1)
    AlphaTabView tab1;
    @Bind(R.id.alphaIndicator)
    AlphaTabsIndicator alphaIndicator;
    @Bind(R.id.opinion_edt)
    EditText opinionEdt;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    private static List<ApprovalBean.ExamineBean> examineBeanList;
    private String opinionType = "同意";
    private Intent intent;
    static int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);
        ButterKnife.bind(this);

        initView();

        alphaIndicator.setOnTabChangedListner(new OnTabChangedListner() {
            @Override
            public void onTabSelected(int tabNum) {
                switch (tabNum) {
                    case 0:
                        opinionType = "同意";
                        break;
                    case 1:
                        opinionType = "不同意";
                        break;
                }
            }
        });
    }

    private void initView() {

        txtTitle.setText("签署意见");
        btnSubmit.setText("保存并提交");

        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rlvData.setLayoutManager(layoutManager);
        rlvData.setAdapter(new OpiniosAdapter(this, examineBeanList));
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {

        if (!ViewUtils.getText(opinionEdt).equals("")){
            intent = new Intent(OpinionActivity.this, OpinionSubmitActivity.class);
//            intent.putExtra("index", index);
//            intent.putExtra("opinion_type", opinionType);
//            intent.putExtra("opinion_content", ViewUtils.getText(opinionEdt));
            OpinionSubmitActivity.setData(examineBeanList, index, opinionType, ViewUtils.getText(opinionEdt));
            startActivity(intent);
        }else
            toast("请填写签署意见！");
    }

    public static void setData(List<ApprovalBean.ExamineBean> listData, int mIndex) {
        examineBeanList = listData;
        index = mIndex;
    }

    private class OpiniosAdapter extends RecyclerView.Adapter<OpiniosAdapter.ViewHolder> {

        Context context;
        List<ApprovalBean.ExamineBean> list;
        OnItemClickCallback callback;

        public OpiniosAdapter(Context context, List<ApprovalBean.ExamineBean> list) {
            this.context = context;
            this.list = list;
        }

        public OpiniosAdapter(Context context, List<ApprovalBean.ExamineBean> list, OnItemClickCallback callback) {
            this.context = context;
            this.list = list;
            this.callback = callback;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.approval_item, null);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            holder.approvalImg.setVisibility(View.GONE);

            holder.approvalBusinessName.setText("业务品种:\t" + list.get(position).getBusinessName());
            holder.approvalName.setText("客户名称:\t" + list.get(position).getCustomerName());
            holder.approvalBusinessSum.setText("申请金额:\t" + list.get(position).getBusinessSum());
            holder.approvalOrgName.setText("登记机构:\t" + list.get(position).getOrgName());

            holder.approvalLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    callback.onClick(view, position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView approvalImg;
            TextView approvalName;
            TextView approvalBusinessSum;
            TextView approvalBusinessName;
            TextView approvalOrgName;
            LinearLayout approvalLay;

            public ViewHolder(View itemView) {
                super(itemView);
                approvalImg = (TextView) itemView.findViewById(R.id.approval_img);
                approvalName = (TextView) itemView.findViewById(R.id.approval_name);
                approvalBusinessName = (TextView) itemView.findViewById(R.id.approval_businessName);
                approvalBusinessSum = (TextView) itemView.findViewById(R.id.approval_businessSum);
                approvalOrgName = (TextView) itemView.findViewById(R.id.approval_orgName);
                approvalLay = (LinearLayout) itemView.findViewById(R.id.approval_lay);
            }
        }
    }
}
