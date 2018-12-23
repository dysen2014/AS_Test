package com.dysen.common_res.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.dysen.common_res.R;
import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.utils.TabSwitchHelper;

import java.util.List;

//import butterknife.Bind;

/**
 * Created by dysen on 2017/10/25.
 *
 * @Info
 */

public class CommonTabAdapter extends RecyclerView.Adapter<CommonTabAdapter.MyViewHolder> {

    private List<String> mDataList;

    // 申明一个点击事件接口变量
    private OnItemClickCallback callback = null;
    private Context mContext;

    public CommonTabAdapter(Context context, List<String> datalist) {
        mDataList = datalist;
        mContext = context;
    }

    public CommonTabAdapter(Context context, List<String> datalist, OnItemClickCallback<Integer> onItemClickCallback) {

        mDataList = datalist;
        callback = onItemClickCallback;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.common_tab_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.rbTab.setVisibility(View.VISIBLE);
        holder.rbTab.setText(mDataList.get(position));
        holder.rbTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    callback.onClick(view, position);
                if (position % 2 == 0){
                    holder.rbTab.setTextColor(mContext.getResources().getColor(R.color.common_tab_bg));
                }else {
                    holder.rbTab.setTextColor(mContext.getResources().getColor(R.color.darkgray));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        RadioButton rbTab;

        public MyViewHolder(View itemView) {
            super(itemView);

            rbTab = (RadioButton) itemView.findViewById(R.id.rb_tab);
        }
    }
}
