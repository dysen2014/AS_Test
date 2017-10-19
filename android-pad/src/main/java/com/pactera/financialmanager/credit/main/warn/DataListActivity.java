package com.pactera.financialmanager.credit.main.warn;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dysen.common_res.common.base.ParentActivity;
import com.dysen.common_res.common.utils.CallAndSMS;
import com.dysen.common_res.common.utils.GridDivider;
import com.dysen.common_res.common.views.ViewUtils;
import com.pactera.financialmanager.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dysen on 2017/9/12.
 */

public class DataListActivity extends ParentActivity {

    private static List<Object> mListDataValue;
    private static List<String> mListDataName;
    @Bind(R.id.data_recyclerView)
    RecyclerView dataRecyclerView;
    @Bind(R.id.txt_title)
    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data_list);
        ButterKnife.bind(this);

        txtTitle.setText("查看详情");
        initView();
    }

    private void initView() {

        //设置颜色分割线
        LinearLayoutManager mManagerColor = new GridLayoutManager(this, 2);
        dataRecyclerView.setLayoutManager(mManagerColor);
//        dataRecyclerView.addItemDecoration(new GridDivider(this, 2, this.getResources().getColor(R.color.colorAccent)));

        //设置图片分割线
//        LinearLayoutManager mManagerDrawable = new GridLayoutManager(this, 2);
//        dataRecyclerView.setLayoutManager(mManagerDrawable);
//        Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.u800);
//        dataRecyclerView.addItemDecoration(new GridDivider(this, 20, drawable));

        dataRecyclerView.setAdapter(new MyAdapter(mListDataName, mListDataValue, this));
    }

    public static void setData(List<String> listDataName, List<Object> listDataValue) {
        mListDataValue = listDataValue;
        mListDataName = listDataName;
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        List<String> nameList;
        List<Object> valueList;
        Context mContext;

        public MyAdapter(List<String> nameList, List<Object> valueList, Context mContext) {
            this.nameList = nameList;
            this.valueList = valueList;
            this.mContext = mContext;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.data_list_item, parent, false);
            //view.setBackgroundColor(Color.RED);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.dataListTxtName.setText(nameList.get(position) + ":");
            holder.dataListTxtValue.setText(valueList.get(position) + "");
            holder.dataListTxtName.setBackgroundResource(R.color.lvtopbg);

            if (ViewUtils.getText(holder.dataListTxtName).equals("客户电话:")){
                holder.dataListTxtValue.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
                holder.dataListTxtValue.setTextColor(getResources().getColor(R.color.blue));
                holder.dataListTxtValue.setTextSize(26);
                holder.dataListTxtValue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CallAndSMS.call2(mContext, valueList.get(position).toString());
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return nameList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.data_list_txt_name)
            TextView dataListTxtName;
            @Bind(R.id.data_list_txt_value)
            TextView dataListTxtValue;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
