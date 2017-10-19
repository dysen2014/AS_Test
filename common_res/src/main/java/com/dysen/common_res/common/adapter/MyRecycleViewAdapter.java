package com.dysen.common_res.common.adapter;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dysen.common_res.R;
import com.dysen.common_res.common.utils.OnItemClickCallback;

import java.util.List;

/**
 * Created by dysen on 2017/10/10.
 *
 * @Info
 */

public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyViewHolder> {

    private static View oldView;
    private LayoutInflater mLayoutInflater;
    private List<String> mDataList;
    private List<Object> list;
    private int mItemLayout;

    int index;
    // 申明一个点击事件接口变量
    private OnItemClickCallback callback = null;
    private Context mContext;

    public MyRecycleViewAdapter(Context context, int itemLayout, List<String> datalist) {
        mLayoutInflater = LayoutInflater.from(context);
        mItemLayout = itemLayout;
        mDataList = datalist;
        mContext = context;
    }

    public MyRecycleViewAdapter(Context context, List<Object> datalist) {
        mLayoutInflater = LayoutInflater.from(context);
        mItemLayout = -1;
        list = datalist;
        mContext = context;
    }

    public MyRecycleViewAdapter(Context context, int itemLayout, List<String> datalist, OnItemClickCallback<String> onItemClickCallback) {
        mLayoutInflater = LayoutInflater.from(context);
        mItemLayout = itemLayout;
        mDataList = datalist;
        callback = onItemClickCallback;
        mContext = context;
    }

    @Override
    public MyRecycleViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mItemLayout != -1) {
            return new MyViewHolder(mLayoutInflater.inflate(mItemLayout, parent, false));
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.common_adapter_item, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(view);

            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(final MyRecycleViewAdapter.MyViewHolder holder, int position) {

        index = position;
        if (mDataList != null) {

            holder.mTextView.setText(mDataList.get(position) + "");
            holder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSelectorItemColor(holder.mTextView, R.color.gray);
                    if (callback != null)
                        callback.onClick(view, mDataList.get(index));
                }
            });
        }
        if (list != null)
            holder.mTextView.setText(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    public static void setSelectorItemColor(View view, @ColorRes @DrawableRes int colorId) {

//		LogUtils.d("colorId:"+colorId);
        if (oldView == null) {
            //第一次点击
            oldView = view;
            view.setBackgroundResource(colorId <= Long.parseLong("7F000000", 16) ? R.color.common_yellow : colorId);
        } else {
            //非第一次点击
            //把上一次点击的 变化
            view.setBackgroundResource(colorId <= Long.parseLong("7F000000", 16) ? R.color.common_yellow : colorId);
            oldView = view;
        }
    }
}
