package com.pactera.financialmanager.credit.main.warn;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.common_res.common.utils.FormatUtil;
import com.dysen.common_res.common.views.TextViewMarquee;
import com.pactera.financialmanager.ui.ParentActivity;
import com.dysen.common_res.common.utils.CallAndSMS;
import com.dysen.common_res.common.views.ViewUtils;
import com.pactera.financialmanager.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dysen on 2017/9/12.
 */

public class DataListActivity extends ParentActivity {

    private static List<String> mListDataValue;
    private static List<String> mListDataName;
    @Bind(R.id.data_recyclerView)
    RecyclerView dataRecyclerView;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.lay_back)
    LinearLayout layBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data_list);
        ButterKnife.bind(this);

        initTitle(this, "", false,"");
        txtTitle.setText("查看详情");
        initView();
    }

    private void initView() {

        //设置颜色分割线
        LinearLayoutManager mManagerColor = new GridLayoutManager(this, 1);
        dataRecyclerView.setLayoutManager(mManagerColor);
//        dataRecyclerView.addItemDecoration(new GridDivider(this, 2, this.getResources().getColor(R.color.colorAccent)));

        //设置图片分割线
//        LinearLayoutManager mManagerDrawable = new GridLayoutManager(this, 2);
//        dataRecyclerView.setLayoutManager(mManagerDrawable);
//        Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.u800);
//        dataRecyclerView.addItemDecoration(new GridDivider(this, 20, drawable));

        dataRecyclerView.setAdapter(new MyAdapter(mListDataName, mListDataValue, this));
        if (layBack != null) {
            layBack.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    finish();
                }
            });
        }
    }

    public static void setData(List<String> listDataName, List<String> listDataValue) {
        mListDataValue = listDataValue;
        mListDataName = listDataName;
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        List<String> nameList;
        List<String> valueList;
        Context mContext;

        public MyAdapter(List<String> nameList, List<String> valueList, Context mContext) {
            this.nameList = nameList;
            this.valueList = valueList;
            this.mContext = mContext;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.info_list_item_new, parent,
                    false);
            //view.setBackgroundColor(Color.RED);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.tvName.setText( nameList.get(position) + ":");
            holder.tvValue.setText(valueList.get(position) + "");

            if (position %2 == 0){
                holder.tvValue.setText(valueList.get(position)+"");
            }
            if (ViewUtils.getText(holder.tvName).equals("手机:") || ViewUtils.getText
                    (holder.tvName).equals("电话:") || ViewUtils.getText
                    (holder.tvName).equals("客户电话:")) {
                boolean numeric = FormatUtil.isNumeric(valueList.get(position).toString());
                if (numeric)
                    if (FormatUtil.isMobileNO(valueList.get(position).toString())) {

                        holder.tvValue.setText(Html.fromHtml("<u>" + valueList.get(position) +
                                "</u>"));
                        holder.tvValue.setTextColor(getResources().getColor(R.color.blue));
                        holder.tvValue.setTextSize(26);
                        holder.tvValue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CallAndSMS.call(mContext, valueList.get(position).toString());
                            }
                        });
                    }
            }
        }

        @Override
        public int getItemCount() {
            return nameList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.tv_name)
            TextViewMarquee tvName;
            @Bind(R.id.tv_value)
            TextView tvValue;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
