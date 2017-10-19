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

public class MyAdaptorLoanAfter extends BaseAdapter  {
    private List<LoanAfterItem> mList;
    private LayoutInflater mInflater;
    public MyAdaptorLoanAfter(Context context, List<LoanAfterItem> list){
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
            convertView = mInflater.inflate(R.layout.loanafter_list_item,null);
            Log.i("convertView", String.valueOf(convertView));
        }
        TextView SerialNo = (TextView) convertView.findViewById(R.id.SerialNo);
        TextView BusinessType = (TextView) convertView.findViewById(R.id.BusinessType);
        TextView ArtificialNo = (TextView) convertView.findViewById(R.id.ArtificialNo);
        TextView OccurTypeName = (TextView) convertView.findViewById(R.id.OccurTypeName);
        TextView BusinessTypeName = (TextView) convertView.findViewById(R.id.BusinessTypeName);
        TextView Currency = (TextView) convertView.findViewById(R.id.Currency);
        TextView BusinessSum = (TextView) convertView.findViewById(R.id.BusinessSum);
        TextView ActualPutOutSum = (TextView) convertView.findViewById(R.id.ActualPutOutSum);
        TextView BailRatio = (TextView) convertView.findViewById(R.id.BailRatio);
        TextView Balance = (TextView) convertView.findViewById(R.id.Balance);
        TextView OverdueBalance = (TextView) convertView.findViewById(R.id.OverdueBalance);
        TextView PutOutDate = (TextView) convertView.findViewById(R.id.PutOutDate);
        TextView Maturity = (TextView) convertView.findViewById(R.id.Maturity);
        TextView OperateOrgName = (TextView) convertView.findViewById(R.id.OperateOrgName);
        LoanAfterItem bean = mList.get(position);
        SerialNo.setText(bean.SerialNo);
        BusinessType.setText(bean.BusinessType);
        ArtificialNo.setText(bean.ArtificialNo);
        OccurTypeName.setText(bean.OccurTypeName);
        BusinessTypeName.setText(bean.BusinessTypeName);
        Currency.setText(bean.Currency);
        BusinessSum.setText(bean.BusinessSum);
        ActualPutOutSum.setText(bean.ActualPutOutSum);
        BailRatio.setText(bean.BailRatio);
        Balance.setText(bean.Balance);
        OverdueBalance.setText(bean.OverdueBalance);
        PutOutDate.setText(bean.PutOutDate);
        Maturity.setText(bean.Maturity);
        OperateOrgName.setText(bean.OperateOrgName);
        return convertView;
    }
}
