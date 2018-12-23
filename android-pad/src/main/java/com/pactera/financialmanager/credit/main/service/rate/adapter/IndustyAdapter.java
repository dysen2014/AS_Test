package com.pactera.financialmanager.credit.main.service.rate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.bean.rate.Industry;

import java.util.List;



public class IndustyAdapter extends BaseAdapter {
    private List<Industry> mList;
    private Context mContext;

    public IndustyAdapter(Context pContext, List<Industry> pList) {
        this.mContext = pContext;
        this.mList = pList;
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
    /**
     * 下面是重要代码
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater=LayoutInflater.from(mContext);
        convertView=_LayoutInflater.inflate(R.layout.bussadapter, null);
        if(convertView!=null)
        {
            TextView _TextView1=(TextView)convertView.findViewById(R.id.textView1);
            TextView _TextView2=(TextView)convertView.findViewById(R.id.textView2);
            _TextView1.setText(mList.get(position).getEtemname());
            _TextView2.setText(mList.get(position).getItemno());
        }
        return convertView;
    }
}