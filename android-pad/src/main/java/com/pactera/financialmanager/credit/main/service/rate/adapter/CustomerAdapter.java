package com.pactera.financialmanager.credit.main.service.rate.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.bean.rate.Customer;

import java.util.List;

/**
 * Created by admin on 2017-7-5.
 */

public class CustomerAdapter extends ArrayAdapter<Customer> {

    private static final String TAG = "CustomerAdapter";
    private int resourceId;

    public CustomerAdapter(Context context, int textViewResourceId, List<Customer> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    public View getView(int postion, View convertView, ViewGroup parent){
        Customer customer = getItem(postion);
        Log.d(TAG, "filestudy: "+customer);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView textView1 = (TextView) view.findViewById(R.id.cs_textView1);
        TextView textView2 = (TextView) view.findViewById(R.id.cs_textView2);
        TextView textView3 = (TextView) view.findViewById(R.id.cs_textView3);
        TextView textView4 = (TextView) view.findViewById(R.id.cs_textView4);
        textView1.setText(customer.getCustomerID());
        textView2.setText(customer.getCustomerName());
        textView3.setText(customer.getCustomerType());
        textView4.setText(customer.getCertID());
        return view;
    }
}
