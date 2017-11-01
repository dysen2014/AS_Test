package com.pactera.financialmanager.credit.main.service.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.pactera.financialmanager.R;

import java.util.List;

/**
 * Created by lenovo on 2017/7/14.
 */

public class MyAdapter {

    static class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ViewHolder> {

        Context context;
        List<ListPerson> list;
        OnItemClickCallback callback;
        int[] imgId = new int[]{R.drawable.level_a, R.drawable.level_b, R.drawable.level_c, R.drawable.level_no};

        public CustomerListAdapter(Context context, List<ListPerson> list) {
            this.context = context;
            this.list = list;
        }

        public CustomerListAdapter(Context context, List<ListPerson> list, OnItemClickCallback<Integer> callback) {
            this.context = context;
            this.list = list;
            this.callback = callback;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.customer_list_item, null);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            holder.CustomerName.setText(list.get(position).getCustomerName());
            holder.CertTypeName.setText(list.get(position).getCertID());
            holder.MobilePhone.setText(list.get(position).getMobilePhone());
//            holder.IrscreditLevel.setText(list.get(position).getIrscreditLevel());
            holder.IrscreditLevel.setBackgroundResource(switchImg(list.get(position).getIrscreditLevel()));
            holder.llLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    callback.onClick(view, position);
                }
            });
        }

        private int switchImg(String irscreditLevel) {
            int id = -1;
            switch (irscreditLevel.toUpperCase().substring(0, 1)) {
                case "A":
                    id = imgId[0];
                    break;
                case "B":
                    id = imgId[1];
                    break;
                case "C":
                case "D":
                    id = imgId[2];
                    break;
                case "Z":
                    id = imgId[3];
                    break;
            }
            return id;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            LinearLayout llLay;
            TextView IrscreditLevel, CustomerName, CertTypeName, MobilePhone;

            public ViewHolder(View itemView) {
                super(itemView);
                llLay = (LinearLayout) itemView.findViewById(R.id.ll_lay);
                IrscreditLevel = (TextView) itemView.findViewById(R.id.IrscreditLevel);
                CustomerName = (TextView) itemView.findViewById(R.id.CustomerName);
                CertTypeName = (TextView) itemView.findViewById(R.id.CertTypeName);
                MobilePhone = (TextView) itemView.findViewById(R.id.MobilePhone);
            }
        }
    }

    static class BusinessListAdapter extends RecyclerView.Adapter<BusinessListAdapter.ViewHolder> {

        Context context;
        List<ListBusinessList> list;
        OnItemClickCallback callback;
        int[] imgId = new int[]{R.drawable.level_a, R.drawable.level_b, R.drawable.level_c, R.drawable.level_no};

        public BusinessListAdapter(Context context, List<ListBusinessList> list) {
            this.context = context;
            this.list = list;
        }

        public BusinessListAdapter(Context context, List<ListBusinessList> list, OnItemClickCallback<Integer> callback) {
            this.context = context;
            this.list = list;
            this.callback = callback;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.business_list_item, null);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @SuppressLint("ResourceType")
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            holder.CustomerName.setText(list.get(position).getCustomerName());
            holder.BusinessType.setText(list.get(position).getBusinessName());
            holder.BusinessType.setTextColor(context.getResources().getColor(R.color.common_tab_bg));
            holder.BusinessSum.setText(list.get(position).getBusinessSum());
            holder.Balance.setText(list.get(position).getBalance());
            holder.OverDueBalance.setText(list.get(position).getOverDueBalance());
            holder.InterestBalance.setText(list.get(position).getInterestBalance());

            if (position % 2 != 0) {//第二项 0开始的


            }

//            holder.picture.setBackgroundResource(switchImg(list.get(position).get()));
            holder.llLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    callback.onClick(view, position);
                }
            });
        }

        private int switchImg(String irscreditLevel) {
            int id = -1;
            switch (irscreditLevel.toUpperCase().substring(0, 1)) {
                case "A":
                    id = imgId[0];
                    break;
                case "B":
                    id = imgId[1];
                    break;
                case "C":
                case "D":
                    id = imgId[2];
                    break;
                case "Z":
                    id = imgId[3];
                    break;
            }
            return id;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            LinearLayout llLay;
            TextView picture, CustomerName, BusinessType, BusinessSum, Balance, OverDueBalance, InterestBalance;

            public ViewHolder(View itemView) {
                super(itemView);
                llLay = (LinearLayout) itemView.findViewById(R.id.ll_lay);
                picture = (TextView) itemView.findViewById(R.id.picture);
                CustomerName = (TextView) itemView.findViewById(R.id.CustomerName);
                BusinessType = (TextView) itemView.findViewById(R.id.businessType);
                BusinessSum = (TextView) itemView.findViewById(R.id.BusinessSum);
                Balance = (TextView) itemView.findViewById(R.id.Balance);
                OverDueBalance = (TextView) itemView.findViewById(R.id.OverDueBalance);
                InterestBalance = (TextView) itemView.findViewById(R.id.InterestBalance);
            }
        }
    }
}
