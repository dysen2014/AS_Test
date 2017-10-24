package com.dysen.contact_library.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.common_res.common.views.ViewUtils;
import com.dysen.contact_library.R;
import com.dysen.contact_library.bean.ContactBean;
import com.dysen.contact_library.utils.OnItemClickCallback;

import java.util.List;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Created by dysen on 2017/10/10.
 *
 * @Info
 */

public class ContactCustomerAdp extends RecyclerView.Adapter<ContactCustomerAdp.MyViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<ContactBean> mDataList;
    private int mItemLayout;
    Context mContext;

    int index;
    // 申明一个点击事件接口变量
    private OnItemClickCallback callback = null;
    private String temp = "";

    public ContactCustomerAdp(Context context, int itemLayout, List<ContactBean> datalist) {
        mLayoutInflater = LayoutInflater.from(context);
        mItemLayout = itemLayout;
        mDataList = datalist;
        mContext = context;
    }

    public ContactCustomerAdp(Context context, int itemLayout, List<ContactBean> datalist, OnItemClickCallback<Integer> onItemClickCallback) {
        mLayoutInflater = LayoutInflater.from(context);
        mItemLayout = itemLayout;
        mDataList = datalist;
        callback = onItemClickCallback;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mItemLayout != -1) {
            return new MyViewHolder(mLayoutInflater.inflate(mItemLayout, parent, false));
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.contact_customer_item, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(view);

            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        index = position;

        if (!temp.equals(mDataList.get(position).getHeaderWord())) {
            holder.tvWord.setText(mDataList.get(position).getHeaderWord());
            temp = mDataList.get(position).getHeaderWord();
        }else {
            holder.tvWord.setVisibility(View.GONE);
        }
//            holder.tvName.setText(mDataList.get(position).getName()+"---"+mDataList.get(position).getPinyin());
            holder.tvName.setText(mDataList.get(position).getName());

        holder.tvNameBg.setText(ViewUtils.getText(holder.tvName).substring(0, 1));
        holder.tvNameId.setText(mDataList.get(position).getCertID());
        holder.contactItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends ViewHolder {

        TextView tvWord;
        TextView tvName;
        TextView tvNameBg;
        TextView tvNameId;
        LinearLayout contactItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvWord = (TextView) itemView.findViewById(R.id.tv_word);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvNameBg = (TextView) itemView.findViewById(R.id.tv_name_bg);
            tvNameId = (TextView) itemView.findViewById(R.id.tv_name_id);
            contactItem = (LinearLayout) itemView.findViewById(R.id.contact_item);
        }
    }
}
