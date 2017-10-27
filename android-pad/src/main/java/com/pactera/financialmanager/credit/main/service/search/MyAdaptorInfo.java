package com.pactera.financialmanager.credit.main.service.search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pactera.financialmanager.R;

import java.util.List;

/**
 * Created by lenovo on 2017/7/26.
 */

public class MyAdaptorInfo extends BaseAdapter {
    private List<InfoItem> mList;
    private LayoutInflater mInflater;
    private String type;
    public MyAdaptorInfo(Context context, List<InfoItem> list,String type){
        this.mList = list;
        mInflater = LayoutInflater.from(context);
        this.type = type;
    }
    public MyAdaptorInfo(Context context, List<InfoItem> list){
        this.mList = list;
        mInflater = LayoutInflater.from(context);
        this.type = type;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.info_list_item,null);
            Log.i("convertView", String.valueOf(convertView));
        }
        TextView Type = (TextView) convertView.findViewById(R.id.Type_left);
        TextView Name = (TextView) convertView.findViewById(R.id.Type);
        Name.setText(mList.get(position).getValue());
        Name.setHeight(48);
        Type.setText(mList.get(position).getKeyName()+"：");
        return convertView;
    }
}
