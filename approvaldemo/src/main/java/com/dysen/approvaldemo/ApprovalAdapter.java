package com.dysen.approvaldemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.common_res.common.base.ParentActivity;
import com.dysen.common_res.common.utils.OnItemClickCallback;

import java.util.List;

/**
 * Created by dysen on 2017/10/16.
 */

public class ApprovalAdapter extends RecyclerView.Adapter<ApprovalAdapter.ViewHolder> {

    Context context;
    List<ApprovalBean.ExamineBean> list;
    OnItemClickCallback callback;

    public ApprovalAdapter(Context context, List<ApprovalBean.ExamineBean> list) {
        this.context = context;
        this.list = list;
    }

    public ApprovalAdapter(Context context, List<ApprovalBean.ExamineBean> list, OnItemClickCallback callback) {
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

        String imgName = ParentActivity.getTypeName(list.get(position).getCustomerType());

        holder.approvalImg.setText(imgName);
        holder.approvalName.setText("客户名称:\t"+list.get(position).getCustomerName());
        holder.approvalBusinessSum.setText("授信金额:\t"+list.get(position).getBusinessSum());
        holder.approvalBusinessName.setText(list.get(position).getBusinessName());
        holder.approvalOrgName.setText("登记机构:\t"+list.get(position).getOrgName());

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
