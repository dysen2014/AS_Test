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

public class MyAdaptorDuebillInfo extends BaseAdapter {
    private List<DuebillInfoItem> mList;
    private LayoutInflater mInflater;
    public MyAdaptorDuebillInfo(Context context, List<DuebillInfoItem> list){
        this.mList = list;
        mInflater = LayoutInflater.from(context);
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
        DuebillInfoItem bean = mList.get(position);
//        Arrays arrays = new Arrays();
//        String thisType = arrays.getCustomerType_name(type);
        Name.setText(bean.Value);
        Type.setText(bean.KeyName+"：");
        return convertView;
    }
}
