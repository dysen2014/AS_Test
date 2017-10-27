package com.pactera.financialmanager.credit.common.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.views.TextViewMarquee;
import com.pactera.financialmanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dysen on 2017/10/07.
 */

public class DataAdapterNew extends RecyclerView.Adapter<DataAdapterNew.ViewHolder> {

    Context mContext;
    private List<List<Object>> dataList = new ArrayList<>();
    int index;

    // 申明一个点击事件接口变量
    private OnItemClickCallback callback = null;

    public DataAdapterNew(Context context, List<List<Object>> list) {

        mContext = context;
        dataList = list;
    }

    public DataAdapterNew(Context context, List<List<Object>> list, OnItemClickCallback onItemClickCallback) {

        mContext = context;
        dataList = list;
        callback = onItemClickCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.data_item_new, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        index = position;
        final List<Object> listData = dataList.get(position);
        if (listData.size() > 0) {

            for (int i = 0; i < listData.size(); i++) {
                if (viewHolder.views.length <= i){
                    return;
                }
                viewHolder.views[i].setVisibility(View.VISIBLE);
                viewHolder.views[i].setText(listData.get(i) + "");
                viewHolder.views[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        LogUtils.v(position+"index:"+index);
                        callback.onClick(view, position);
                    }
                });

                if (position % 2 != 0) {
                    viewHolder.views[i].setBackgroundResource(R.color.white);
                } else {
                    viewHolder.views[i].setBackgroundResource(R.color.lvtopbg);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

//自定义的ViewHolder，持有每个Item的的所有界面元素
public static class ViewHolder extends RecyclerView.ViewHolder {

    public TextViewMarquee dataItemNewTv0;
    public TextViewMarquee dataItemNewTv1;
    public TextViewMarquee dataItemNewTv2;
    public TextViewMarquee dataItemNewTv3;
    public TextViewMarquee dataItemNewTv4;
    public TextViewMarquee dataItemNewTv5;
    public TextViewMarquee dataItemNewTv6;
    public TextViewMarquee dataItemNewTv7;
    public TextViewMarquee dataItemNewTv8;
    public TextViewMarquee dataItemNewTv9;
    public TextViewMarquee dataItemNewTv10;
    public TextViewMarquee[] views = {};

    public ViewHolder(View view) {
        super(view);
        dataItemNewTv0 = (TextViewMarquee) view.findViewById(R.id.data_item_new_tv_0);
        dataItemNewTv1 = (TextViewMarquee) view.findViewById(R.id.data_item_new_tv_1);
        dataItemNewTv2 = (TextViewMarquee) view.findViewById(R.id.data_item_new_tv_2);
        dataItemNewTv3 = (TextViewMarquee) view.findViewById(R.id.data_item_new_tv_3);
        dataItemNewTv4 = (TextViewMarquee) view.findViewById(R.id.data_item_new_tv_4);
        dataItemNewTv5 = (TextViewMarquee) view.findViewById(R.id.data_item_new_tv_5);
        dataItemNewTv6 = (TextViewMarquee) view.findViewById(R.id.data_item_new_tv_6);
        dataItemNewTv7 = (TextViewMarquee) view.findViewById(R.id.data_item_new_tv_7);
        dataItemNewTv8 = (TextViewMarquee) view.findViewById(R.id.data_item_new_tv_8);
        dataItemNewTv9 = (TextViewMarquee) view.findViewById(R.id.data_item_new_tv_9);
        dataItemNewTv10 = (TextViewMarquee) view.findViewById(R.id.data_item_new_tv_10);
        views = new TextViewMarquee[]{dataItemNewTv0, dataItemNewTv1, dataItemNewTv2, dataItemNewTv3, dataItemNewTv4,
                dataItemNewTv5, dataItemNewTv6, dataItemNewTv7, dataItemNewTv8, dataItemNewTv9, dataItemNewTv10};
    }
}

}