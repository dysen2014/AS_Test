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

public class MyAdaptorBusinessList extends BaseAdapter {
    private List<BusinessListItem> mList;
    private LayoutInflater mInflater;
    public MyAdaptorBusinessList(Context context, List<BusinessListItem> list){
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
            convertView = mInflater.inflate(R.layout.business_list_item,null);
        }
        TextView CustomerName = (TextView)convertView.findViewById(R.id.CustomerName);
        TextView BusinessType = (TextView) convertView.findViewById(R.id.businessType);
        TextView BusinessSum = (TextView) convertView.findViewById(R.id.BusinessSum);
        TextView Balance = (TextView) convertView.findViewById(R.id.Balance);
        TextView OverDueBalance = (TextView) convertView.findViewById(R.id.OverDueBalance);
        TextView InterestBalance = (TextView) convertView.findViewById(R.id.InterestBalance);
        BusinessListItem bean = mList.get(position);
        CustomerName.setText(bean.CustomerName);
        BusinessType.setText(bean.BusinessName);
        BusinessSum.setText(bean.BusinessSum);
        Balance.setText(bean.Balance);
        OverDueBalance.setText(bean.OverDueBalance);
        InterestBalance.setText(bean.InterestBalance);
        return convertView;
    }
}
