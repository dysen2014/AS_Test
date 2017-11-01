package com.pactera.financialmanager.credit.main.service.search;

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
 * Created by lenovo on 2017/7/26.
 */

public class MyAdaptorInfo {

    static class CommonListAdapter extends RecyclerView.Adapter<CommonListAdapter.ViewHolder> {

        Context context;
        List<InfoItem> list;
        OnItemClickCallback callback;
        int[] imgId = new int[]{R.drawable.level_a, R.drawable.level_b, R.drawable.level_c, R.drawable.level_no};

        public CommonListAdapter(Context context, List<InfoItem> lists) {
            this.context = context;
            this.list = lists;
        }

        public CommonListAdapter(Context context, List<InfoItem> lists, OnItemClickCallback<Integer> callback) {
            this.context = context;
            this.list = lists;
            this.callback = callback;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.info_list_item, null);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            holder.tvName.setText("\t"+list.get(position).getKeyName());
            holder.tvValue.setText("\t"+list.get(position).getValue());

            holder.llLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    callback.onClick(view, position);
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
            TextView tvName, tvValue;

            public ViewHolder(View itemView) {
                super(itemView);
                llLay = (LinearLayout) itemView.findViewById(R.id.ll_lay);
                tvName = (TextView) itemView.findViewById(R.id.tv_name);
                tvValue = (TextView) itemView.findViewById(R.id.tv_value);
            }
        }
    }
}
