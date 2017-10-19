package com.pactera.financialmanager.credit.main.service.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pactera.financialmanager.R;

import java.util.List;

/**
 * Created by lenovo on 2017/7/14.
 */

public class MyAdaptor extends BaseAdapter {
    private List<CustomerItem> mList;
    private LayoutInflater mInflater;
    public MyAdaptor(Context context, List<CustomerItem> list){
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
            convertView = mInflater.inflate(R.layout.customer_list_item,null);
        }
        TextView IrscreditLevel = (TextView)convertView.findViewById(R.id.IrscreditLevel);
        TextView CustomerName = (TextView) convertView.findViewById(R.id.CustomerName);
        TextView CertTypeName = (TextView) convertView.findViewById(R.id.CertTypeName);
        TextView MobilePhone = (TextView) convertView.findViewById(R.id.MobilePhone);
        CustomerItem bean = mList.get(position);
        IrscreditLevel.setText(bean.IrscreditLevel);
        CustomerName.setText(bean.CustomerName);
        CertTypeName.setText(bean.CertTypeName);
        MobilePhone.setText(bean.MobilePhone);
        return convertView;
    }
}
