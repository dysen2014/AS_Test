package com.dysen.approvaldemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.common_res.common.utils.OnItemClickCallback;

import java.util.List;

/**
 * Created by dysen on 2017/10/10.
 *
 * @Info
 */

public class ApprovalOpinionAdapter extends RecyclerView.Adapter<ApprovalOpinionAdapter.MyViewHolder> {

    private List<ApprovalBean.OpinionInfoBean> list;

    // 申明一个点击事件接口变量
    private OnItemClickCallback callback = null;
    private Context mContext;
    String[] mNanes = new String[]{"处理人所属机构:\t", "收到时间:\t", "处理阶段:\t", "处理人:\t", "处理意见:\t", "完成时间:\t", "意见详情:\t"};
    private int count;

    public ApprovalOpinionAdapter(Context context, List<ApprovalBean.OpinionInfoBean> datalist) {
        list = datalist;
        mContext = context;
    }

    public ApprovalOpinionAdapter( Context mContext, List<ApprovalBean.OpinionInfoBean> list, OnItemClickCallback callback) {
        this.list = list;
        this.callback = callback;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.opinion_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (list != null) {

            holder.dataName.setText(list.get(position).getUserName());
            holder.llName.setBackgroundResource(R.color.gray2);
            holder.dataName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    callback.onClick(v, position);
                    if (count % 2 == 0)
                        holder.llContent.setVisibility(View.GONE);
                    else
                        holder.llContent.setVisibility(View.VISIBLE);
                    count++;
                }
            });
            holder.data0.setText(mNanes[0]);
            holder.data1.setText(list.get(position).getOrgName());
            holder.data2.setText(mNanes[1]);
            holder.data3.setText(list.get(position).getBeginTime());
            holder.data4.setText(mNanes[2]);
            holder.data5.setText(list.get(position).getPhaseName());
            holder.data6.setText(mNanes[3]);
            holder.data7.setText(list.get(position).getUserName());
            holder.data8.setText(mNanes[4]);
            holder.data9.setText(list.get(position).getPhaseOpinion());
            holder.data10.setText(mNanes[5]);
            holder.data11.setText(list.get(position).getEndTime());
            holder.data12.setText(mNanes[6]);
            holder.data13.setText(list.get(position).getIdea());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dataName;
        LinearLayout llContent, llName;
        TextView data0,data1,data2,data3,data4,data5,data6,data7,data8,data9,data10,data11,data12,data13;

        public MyViewHolder(View itemView) {
            super(itemView);

            dataName = (TextView) itemView.findViewById(R.id.data_name);
            data0 = (TextView) itemView.findViewById(R.id.data_0);
            data1 = (TextView) itemView.findViewById(R.id.data_1);
            data2 = (TextView) itemView.findViewById(R.id.data_2);
            data3 = (TextView) itemView.findViewById(R.id.data_3);
            data4 = (TextView) itemView.findViewById(R.id.data_4);
            data5 = (TextView) itemView.findViewById(R.id.data_5);
            data6 = (TextView) itemView.findViewById(R.id.data_6);
            data7 = (TextView) itemView.findViewById(R.id.data_7);
            data8 = (TextView) itemView.findViewById(R.id.data_8);
            data9 = (TextView) itemView.findViewById(R.id.data_9);
            data10 = (TextView) itemView.findViewById(R.id.data_10);
            data11 = (TextView) itemView.findViewById(R.id.data_11);
            data12 = (TextView) itemView.findViewById(R.id.data_12);
            data13 = (TextView) itemView.findViewById(R.id.data_13);
            llName = (LinearLayout) itemView.findViewById(R.id.ll_name);
            llContent = (LinearLayout)itemView.findViewById(R.id.ll_content);
        }
    }
}
