package com.pactera.financialmanager.credit.main.service.approval;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.utils.Utils;
import com.dysen.common_res.common.views.ViewUtils;
import com.pactera.financialmanager.R;

import java.util.List;

/**
 * Created by dysen on 2017/10/16.
 */

public class ApprovalAdapter{

    /**
     * 审批主页(已审批/未审批)
     */
    static class ApprovalMain extends RecyclerView.Adapter<ApprovalMain.ViewHolder>{
        Context context;
        List<ApprovalBean.ExamineBean> list;
        OnItemClickCallback callback;

        public ApprovalMain(Context context, List<ApprovalBean.ExamineBean> list) {
            this.context = context;
            this.list = list;
        }

        public ApprovalMain(Context context, List<ApprovalBean.ExamineBean> list, OnItemClickCallback<Integer> callback) {
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

            String imgName = Utils.getTypeName(list.get(position).getCustomerType());

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

    /**
     * 业务审批
     */
    static class ApprovalBusiness extends  RecyclerView.Adapter<ApprovalBusiness.ViewHolder>{

        Context context;
        List<ApprovalBean.ApplyInfoBean> list;
        OnItemClickCallback callback;

        public ApprovalBusiness(Context context, List<ApprovalBean.ApplyInfoBean> list) {
            this.context = context;
            this.list = list;
        }

        public ApprovalBusiness(Context context, List<ApprovalBean.ApplyInfoBean> list, OnItemClickCallback<Integer> callback) {
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

    /**
     * 担保信息
     */
    static class ApprovalGuaranty extends  RecyclerView.Adapter<ApprovalGuaranty.MyViewHolder> {

        private List<ApprovalBean.GuarantyInfoBean> list;

        // 申明一个点击事件接口变量
        private OnItemClickCallback callback = null;
        private Context mContext;
        String[] mNanes = new String[]{"贷款流水号:\t", "担保客户:\t", "担保类型:\t", "担保方式:\t", "担保金额:\t"};

        public ApprovalGuaranty(Context context, List<ApprovalBean.GuarantyInfoBean> datalist) {
            list = datalist;
            mContext = context;
        }

        @Override
        public ApprovalGuaranty.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

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

    /**
     * 意见处理
     */
    static class ApprovalOpinion extends  RecyclerView.Adapter<ApprovalOpinion.MyViewHolder> {

        boolean clickFlag;
        private List<ApprovalBean.OpinionInfoBean> list;

        // 申明一个点击事件接口变量
        private OnItemClickCallback callback = null;
        private Context mContext;
        String[] mNanes = new String[]{"处理人所属机构:\t", "收到时间:\t", "处理阶段:\t", "处理人:\t", "处理意见:\t", "完成时间:\t", "意见详情:\t"};
        private int count;

        public ApprovalOpinion(Context context, List<ApprovalBean.OpinionInfoBean> datalist) {
            list = datalist;
            mContext = context;
        }

        public ApprovalOpinion( Context mContext, List<ApprovalBean.OpinionInfoBean> list,
                                OnItemClickCallback<Integer> callback) {
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
//                holder.llName.setBackgroundResource(R.color.gray2);
                holder.llName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        callback.onClick(v, position);
//                        if (count % 2 == 0)
//                            holder.llContent.setVisibility(View.GONE);
//                        else
//                            holder.llContent.setVisibility(View.VISIBLE);
//                        count++;
                        if (clickFlag){//折叠 (准备展开)
                            clickFlag = false;
                            holder.llContent.setVisibility(View.VISIBLE);
                            holder.mImage.setBackgroundResource(R.drawable.ic_left);
                        }else {//展开 (准备折叠)
                            clickFlag = true;
                            holder.llContent.setVisibility(View.GONE);
                            holder.mImage.setBackgroundResource(R.drawable.ic_bottom);
                        }
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

            ImageView mImage;
            TextView dataName;
            LinearLayout llContent, llName;
            TextView data0,data1,data2,data3,data4,data5,data6,data7,data8,data9,data10,data11,data12,data13;

            public MyViewHolder(View itemView) {
                super(itemView);

                mImage= (ImageView) itemView.findViewById(R.id.data_img);
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

    /**
     * 风险提示
     */
    static class RiskSignal extends  RecyclerView.Adapter<RiskSignal.MyViewHolder> {

        private List<ApprovalBean.RiskSignalBean> list;

        // 申明一个点击事件接口变量
        private OnItemClickCallback callback = null;
        private Context mContext;
        private boolean clickFlag;

        public RiskSignal(Context context, List<ApprovalBean.RiskSignalBean> datalist) {
            list = datalist;
            mContext = context;
        }
        public RiskSignal(Context context, List<ApprovalBean.RiskSignalBean> datalist,
                          OnItemClickCallback<Integer> onCall) {
            list = datalist;
            mContext = context;
            callback = onCall;
        }

        @Override
        public RiskSignal.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.risk_item, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(view);

            return viewHolder;

        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            if (list != null) {

                holder.mName.setText(list.get(position).getModelName());
                holder.mValue.setText(list.get(position).getMessAge());
                holder.mValue.setTextColor(mContext.getResources().getColor(R.color.common_tab_bg));
                holder.llLay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        callback.onClick(v, position);
                        if (clickFlag){//折叠 (准备展开)
                            clickFlag = false;
                            holder.mValue.setVisibility(View.VISIBLE);
                            holder.mImage.setBackgroundResource(R.drawable.ic_left);
                        }else {//展开 (准备折叠)
                            clickFlag = true;
                            holder.mValue.setVisibility(View.GONE);
                            holder.mImage.setBackgroundResource(R.drawable.ic_bottom);
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            LinearLayout llLay;
            ImageView mImage;
            TextView mName, mValue;

            public MyViewHolder(View itemView) {
                super(itemView);

                llLay = (LinearLayout) itemView.findViewById(R.id.ll_lay);
                mName = (TextView) itemView.findViewById(R.id.data_name);
                mValue= (TextView) itemView.findViewById(R.id.data_value);
                mImage= (ImageView) itemView.findViewById(R.id.data_img);
            }
        }
    }
}
