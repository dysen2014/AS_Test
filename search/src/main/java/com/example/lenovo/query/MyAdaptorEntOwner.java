package com.example.lenovo.query;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lenovo on 2017/7/25.
 */

public class MyAdaptorEntOwner extends BaseAdapter  {
    private List<EntOwnerItem> mList;
    private LayoutInflater mInflater;
    public MyAdaptorEntOwner(Context context, List<EntOwnerItem> list){
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
            convertView = mInflater.inflate(R.layout.entowner_list_item,null);
            Log.i("convertView", String.valueOf(convertView));
        }
        TextView CustomerName = (TextView) convertView.findViewById(R.id.CustomerName);
        TextView RelationShipName = (TextView) convertView.findViewById(R.id.RelationShipName);
        TextView InvestmentProp = (TextView) convertView.findViewById(R.id.InvestmentProp);
        TextView CurrencyTypeName = (TextView) convertView.findViewById(R.id.CurrencyTypeName);
        TextView OughtSum = (TextView) convertView.findViewById(R.id.OughtSum);
        TextView InvestmentSum = (TextView) convertView.findViewById(R.id.InvestmentSum);
        TextView InvestDate = (TextView) convertView.findViewById(R.id.InvestDate);
        TextView OrgName = (TextView) convertView.findViewById(R.id.OrgName);
        TextView UserName = (TextView) convertView.findViewById(R.id.UserName);
        EntOwnerItem bean = mList.get(position);
        CustomerName.setText(bean.CustomerName);
        RelationShipName.setText(bean.RelationShipName);
        InvestmentProp.setText(bean.InvestmentProp);
        CurrencyTypeName.setText(bean.CurrencyTypeName);
        OughtSum.setText(bean.OughtSum);
        InvestmentSum.setText(bean.InvestmentSum);
        InvestDate.setText(bean.InvestDate);
        OrgName.setText(bean.OrgName);
        UserName.setText(bean.UserName);
        return convertView;
    }
}
