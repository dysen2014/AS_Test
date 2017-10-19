package com.dysen.approvaldemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dysen.common_res.common.utils.OnItemClickCallback;

import java.util.List;

/**
 * Created by dysen on 2017/10/10.
 *
 * @Info
 */

public class BusinessGuarantyAdapter extends RecyclerView.Adapter<BusinessGuarantyAdapter.MyViewHolder> {

    private List<ApprovalBean.GuarantyInfoBean> list;

    // 申明一个点击事件接口变量
    private OnItemClickCallback callback = null;
    private Context mContext;
    String[] mNanes = new String[]{"贷款流水号:\t", "担保客户:\t", "担保类型:\t", "担保方式:\t", "担保金额:\t"};

    public BusinessGuarantyAdapter(Context context, List<ApprovalBean.GuarantyInfoBean> datalist) {
        list = datalist;
        mContext = context;
    }

    @Override
    public BusinessGuarantyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.guaranty_item, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(view);

            return viewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (list != null) {

            holder.mName.setText(list.get(position).getGuarantorName());
            holder.mName.setBackgroundResource(R.color.gray2);
            holder.mCreditNum.setText(mNanes[0]);
            holder.mCreditNumVale.setText(list.get(position).getSerialNo());
            holder.mGuarantyCustomser.setText(mNanes[1]);
            holder.mGuarantyCustomserValue.setText(list.get(position).getGuarantorName());
            holder.mGuarantyType.setText(mNanes[2]);
            holder.mGuarantyTypeValue.setText(list.get(position).getGuarantyTypeName());
            holder.mGuarantyStyle.setText(mNanes[3]);
            holder.mGuarantyStyleValue.setText(list.get(position).getContractTypeName());
            holder.mGuarantyAmount.setText(mNanes[4]);
            holder.mGuarantyAmountValue.setText(list.get(position).getGuarantyValue());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mName, mCreditNum, mCreditNumVale, mGuarantyCustomser, mGuarantyCustomserValue, mGuarantyType, mGuarantyTypeValue
        ,mGuarantyStyle, mGuarantyStyleValue, mGuarantyAmount, mGuarantyAmountValue;

        public MyViewHolder(View itemView) {
            super(itemView);

            mName = (TextView) itemView.findViewById(R.id.data_name);
            mCreditNum= (TextView) itemView.findViewById(R.id.data_credit_num);
            mCreditNumVale= (TextView) itemView.findViewById(R.id.data_credit_num_value);
            mGuarantyCustomser= (TextView) itemView.findViewById(R.id.data_guaranty_customser);
            mGuarantyCustomserValue= (TextView) itemView.findViewById(R.id.data_guaranty_customser_value);
            mGuarantyType= (TextView) itemView.findViewById(R.id.data_guaranty_type);
            mGuarantyTypeValue= (TextView) itemView.findViewById(R.id.data_guaranty_type_value);
            mGuarantyStyle= (TextView) itemView.findViewById(R.id.data_guaranty_style);
            mGuarantyStyleValue= (TextView) itemView.findViewById(R.id.data_guaranty_style_value);
            mGuarantyAmount= (TextView) itemView.findViewById(R.id.data_guaranty_amount);
            mGuarantyAmountValue= (TextView) itemView.findViewById(R.id.data_guaranty_amount_value);
        }
    }
}
