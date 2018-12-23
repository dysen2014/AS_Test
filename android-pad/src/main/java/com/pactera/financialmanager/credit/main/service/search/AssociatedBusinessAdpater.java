package com.pactera.financialmanager.credit.main.service.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.bean.CommonBean;

import java.util.List;

/**
 * Created by Tiger on 2018/5/18.
 */

public class AssociatedBusinessAdpater extends RecyclerView.Adapter<AssociatedBusinessAdpater.ViewHolder> {

    Context context;
    List<CommonBean.RelevanceBean> list;
    OnItemClickCallback callback;


    public AssociatedBusinessAdpater(Context mContext, List<CommonBean.RelevanceBean> listData,
                                     OnItemClickCallback<Integer> onItemClickCallback) {

        this.context = mContext;
        this.list = listData;
        this.callback = onItemClickCallback;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.associated_business_recly_item, null);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CommonBean.RelevanceBean relevanceBean = list.get(position);


        imagebetween(relevanceBean.getRelationId(), holder.image_icon);

        holder.customer_name.setText(relevanceBean.getCustomerName());
        holder.relationship_text.setText(relevanceBean.getRelationNamem());
        holder.Business_text.setText("业务品种：" + relevanceBean.getBusinessName());
        holder.overdue_text.setText("逾期金额：" + relevanceBean.getOverDueBalance());
        holder.balance_text.setText("贷款余额：" + relevanceBean.getBalance());
        holder.overdue_days_text.setText("逾期天数：" + relevanceBean.getOverDueDays());


        holder.llLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callback.onClick(view, position);
            }
        });
    }


    private void imagebetween(String RelationId, ImageView image_icon) {
        switch (RelationId) {
            case "01":   //关键人
                image_icon.setBackgroundResource(R.drawable.jtkhcy_w);
                break;
            case "03":   //家庭关系
                image_icon.setBackgroundResource(R.drawable.jtgx_w);
                break;
            case "04":   //关联集团
            case "54":
                image_icon.setBackgroundResource(R.drawable.gljt_w);
                break;
            case "05":   //联保小组
            case "55":
                image_icon.setBackgroundResource(R.drawable.hbxz_w);
                break;
            case "06":   //集团客户群成员
            case "56":
                image_icon.setBackgroundResource(R.drawable.jtkhcy_w);
                break;
            case "51":   //任职
                image_icon.setBackgroundResource(R.drawable.rz_w);
                break;
            case "52":   //资本构成
                image_icon.setBackgroundResource(R.drawable.zbgc_w);
                break;
            case "02":   //投资
                image_icon.setBackgroundResource(R.drawable.tz_w);
                break;
            case "99":   //其他
                image_icon.setBackgroundResource(R.drawable.qt_w);
                break;
            case "000":
                image_icon.setBackgroundResource(R.drawable.variety);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout llLay;
        private ImageView image_icon;
        private TextView customer_name;
        private TextView relationship_text;
        private TextView Business_text;
        private TextView overdue_text;
        private TextView balance_text;
        private TextView overdue_days_text;

        public ViewHolder(View itemView) {
            super(itemView);


            llLay = (RelativeLayout) itemView.findViewById(R.id.llLay);
            image_icon = (ImageView) itemView.findViewById(R.id.image_icon);
            customer_name = (TextView) itemView.findViewById(R.id.customer_name);
            relationship_text = (TextView) itemView.findViewById(R.id.relationship_text);
            Business_text = (TextView) itemView.findViewById(R.id.Business_text);
            overdue_text = (TextView) itemView.findViewById(R.id.overdue_text);
            balance_text = (TextView) itemView.findViewById(R.id.balance_text);
            overdue_days_text = (TextView) itemView.findViewById(R.id.overdue_days_text);

        }
    }
}