package com.pactera.financialmanager.credit.common.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dysen.common_res.R;
import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.pactera.financialmanager.credit.common.bean.CommonBean;
import com.pactera.financialmanager.credit.main.service.search.MyAdapter;

import java.util.List;

/**
 * Created by dysen on 11/30/2017.
 * 公用Adapter
 */
public class CommonAdapter {

    public class BaseAdapter extends BaseQuickAdapter<Class<?>, BaseViewHolder> {

        public BaseAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Class<?> item) {
            helper.setText(R.id.text, "");
            helper.setImageResource(R.id.icon, 0);
        }
    }

    public static class BranshesAdpater2 extends RecyclerView.Adapter<BranshesAdpater2.ViewHolder>{
        Context context;
        OnItemClickCallback callback;
        private List<CommonBean.BranshesModel> mForCustomerList;

        public BranshesAdpater2(Context context, List<CommonBean.BranshesModel> forDetailList) {

            this.context = context;
            mForCustomerList = forDetailList;
        }

        public BranshesAdpater2(Context context, List<CommonBean.BranshesModel> mForCustomerList,
                                OnItemClickCallback<Integer> onItemClickCallback) {
            this.context = context;
            this.callback = onItemClickCallback;
            this.mForCustomerList = mForCustomerList;
        }

        @Override
        public BranshesAdpater2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(com.pactera.financialmanager.R
                    .layout.branshes_list_item, null);

            CommonAdapter.BranshesAdpater2.ViewHolder viewHolder = new CommonAdapter.BranshesAdpater2.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final BranshesAdpater2.ViewHolder holder, final int position) {
            holder.title_name.setText(mForCustomerList.get(position).getOrgName().toString());

            holder.title_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onClick(view, position);
                    holder.title_name.setBackgroundResource(R.drawable.shape_txt_bg_selected);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mForCustomerList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView title_name;

            public ViewHolder(View itemView) {
                super(itemView);
                title_name = (TextView) itemView.findViewById(R.id.title);
            }
        }
    }
//    public static class BranshesAdpater extends android.widget.BaseAdapter {
//
//        Context context;
//        private List<CommonBean.BranshesModel> mForCustomerList;
//
//        public BranshesAdpater(Context context, List<CommonBean.BranshesModel> forDetailList) {
//
//            this.context = context;
//            mForCustomerList = forDetailList;
//        }
//
//        @Override
//        public int getCount() {
//            return mForCustomerList.size();
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return mForCustomerList.get(i);
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return i;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup viewGroup) {
//
//            ViewHolder holder;
//
//            if (convertView == null) {
//                holder = new ViewHolder();
//                convertView = LayoutInflater.from(context).inflate(R.layout.branshes_list_item, null);
//                holder.title_name = (TextView) convertView.findViewById(R.id.title);
//
//                convertView.setTag(holder);
//            }else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//
//            holder.title_name.setText(mForCustomerList.get(position).getOrgName().toString());
//
//            return convertView;
//        }
//
//        class ViewHolder{
//            private TextView title_name;
//        }
//    }
}