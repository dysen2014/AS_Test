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
 * Created by lenovo on 2017/7/21.
 */

public class MyAdaptorKyeman extends BaseAdapter {
    private List<KeymanItem> mList;
    private LayoutInflater mInflater;
    private Boolean aBoolean;
    public MyAdaptorKyeman(Context context, List<KeymanItem> list,Boolean aBoolean){
        this.mList = list;
        mInflater = LayoutInflater.from(context);
        this.aBoolean = aBoolean;
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
            convertView = mInflater.inflate(R.layout.keyman_list_item,null);
            Log.i("convertView", String.valueOf(convertView));
        }
        Log.i("ddddd", String.valueOf(aBoolean));
//        array = new String[]{"CertName","CertID","CustomerName","RelationName","UserName","OrgName","InputDate","UpdateDate","Telephone","OfficeTime","PractitionerTime"};
        TextView CertName = (TextView)convertView.findViewById(R.id.CertName);
        TextView CertID = (TextView) convertView.findViewById(R.id.CertID);
        TextView CustomerName = (TextView) convertView.findViewById(R.id.CustomerName);
        TextView RelationName = (TextView) convertView.findViewById(R.id.RelationName);
        TextView UserName = (TextView) convertView.findViewById(R.id.UserName);
        TextView OrgName = (TextView) convertView.findViewById(R.id.OrgName);
        TextView InputDate = (TextView) convertView.findViewById(R.id.InputDate);
        TextView UpdateDate = (TextView) convertView.findViewById(R.id.UpdateDate);
        TextView Telephone = (TextView) convertView.findViewById(R.id.Telephone);
        TextView OfficeTime = (TextView) convertView.findViewById(R.id.OfficeTime);
        TextView PractitionerTime = (TextView) convertView.findViewById(R.id.PractitionerTime);
        TextView InputDate_left = (TextView) convertView.findViewById(R.id.InputDate_left);
        TextView UpdateDate_left = (TextView) convertView.findViewById(R.id.UpdateDate_left);
        TextView Telephone_left = (TextView) convertView.findViewById(R.id.Telephone_left);
        TextView OfficeTime_left = (TextView) convertView.findViewById(R.id.OfficeTime_left);
        TextView PractitionerTime_left = (TextView) convertView.findViewById(R.id.PractitionerTime_left);
        KeymanItem bean = mList.get(position);
        CertName.setText(bean.CertName);
        CertID.setText(bean.CertID);
        CustomerName.setText(bean.CustomerName);
        RelationName.setText(bean.RelationName);
        UserName.setText(bean.UserName);
        OrgName.setText(bean.OrgName);
        InputDate.setText(bean.InputDate);
        UpdateDate.setText(bean.UpdateDate);
        Telephone.setText(bean.Telephone);
        OfficeTime.setText(bean.OfficeTime);
        PractitionerTime.setText(bean.PractitionerTime);
        if(aBoolean == true){
            OfficeTime.setVisibility(View.GONE);
            OfficeTime_left.setVisibility(View.GONE);
            Telephone_left.setVisibility(View.GONE);
            Telephone.setVisibility(View.GONE);
            PractitionerTime.setVisibility(View.GONE);
            PractitionerTime_left.setVisibility(View.GONE);
            InputDate.setVisibility(View.VISIBLE);
            InputDate_left.setVisibility(View.VISIBLE);
            UpdateDate.setVisibility(View.VISIBLE);
            UpdateDate_left.setVisibility(View.VISIBLE);
        }else{
            InputDate.setVisibility(View.GONE);
            InputDate_left.setVisibility(View.GONE);
            UpdateDate.setVisibility(View.GONE);
            UpdateDate_left.setVisibility(View.GONE);
            OfficeTime.setVisibility(View.VISIBLE);
            OfficeTime_left.setVisibility(View.VISIBLE);
            Telephone_left.setVisibility(View.VISIBLE);
            Telephone.setVisibility(View.VISIBLE);
            PractitionerTime.setVisibility(View.VISIBLE);
            PractitionerTime_left.setVisibility(View.VISIBLE);
        }
        return convertView;
    }
}
