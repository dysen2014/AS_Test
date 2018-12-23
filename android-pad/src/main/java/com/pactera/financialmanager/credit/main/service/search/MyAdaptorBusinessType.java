package com.pactera.financialmanager.credit.main.service.search;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.pactera.financialmanager.R;

import java.util.List;

/**
 * Created by lenovo on 2017/7/26.
 */

public class MyAdaptorBusinessType extends BaseAdapter {
    private List<BusinessTypeItem> mList;
    private LayoutInflater mInflater;
//    private String type;
    public MyAdaptorBusinessType( List<BusinessTypeItem> list){
        this.mList = list;
//        mInflater = LayoutInflater.from(context);
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
            convertView = mInflater.inflate(R.layout.business_type_bg,null);
            Log.i("convertView", String.valueOf(convertView));
        }
        Button Type = (Button) convertView.findViewById(R.id.Type_left);
        TextView Name = (TextView) convertView.findViewById(R.id.Type);
        BusinessTypeItem bean = mList.get(position);
//        Arrays arrays = new Arrays();
//        String thisType = arrays.getCustomerType_name(type);
        Type.setText(bean.TypeName);
        Name.setText(bean.SortNo);
        return convertView;
    }
}
