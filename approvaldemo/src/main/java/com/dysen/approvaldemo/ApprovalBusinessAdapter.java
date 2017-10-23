package com.dysen.approvaldemo;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.views.ViewUtils;

import java.util.List;

/**
 * Created by dysen on 2017/10/17.
 */

class ApprovalBusinessAdapter extends RecyclerView.Adapter<ApprovalBusinessAdapter.ViewHolder>{

    Context context;
    List<ApprovalBean.ApplyInfoBean> list;
    OnItemClickCallback callback;

    public ApprovalBusinessAdapter(Context context, List<ApprovalBean.ApplyInfoBean> list) {
        this.context = context;
        this.list = list;
    }

    public ApprovalBusinessAdapter(Context context, List<ApprovalBean.ApplyInfoBean> list, OnItemClickCallback<Integer> callback) {
        this.context = context;
        this.list = list;
        this.callback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.data_list_item, parent, false);
        //view.setBackgroundColor(Color.RED);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.dataListTxtName.setText(list.get(position).getKeyName() + ":");
        holder.dataListTxtValue.setText(list.get(position).getValue());
//        holder.dataListTxtName.setBackgroundResource(R.color.lvtopbg);

        if (ViewUtils.getText(holder.dataListTxtName).equals("联系电话:")){
            holder.dataListTxtValue.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
            holder.dataListTxtValue.setTextColor(context.getResources().getColor(R.color.blue));
            holder.dataListTxtValue.setTextSize(26);
            holder.dataListTxtValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onClick(view, position);
//                    CallAndSMS.call(context, list.get(position).getValue());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dataListTxtName;
        TextView dataListTxtValue;

        public ViewHolder(View itemView) {
            super(itemView);

            dataListTxtName = (TextView) itemView.findViewById(R.id.data_list_txt_name);
            dataListTxtValue = (TextView) itemView.findViewById(R.id.data_list_txt_value);
        }
    }
}
