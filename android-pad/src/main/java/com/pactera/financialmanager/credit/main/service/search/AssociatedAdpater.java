package com.pactera.financialmanager.credit.main.service.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.bean.CommonBean;

import java.util.List;

/**
 * Created by Tiger on 2018/5/18.
 */

public class AssociatedAdpater extends RecyclerView.Adapter<AssociatedAdpater.ViewHolder> {

    Context context;
    List<CommonBean.RelevanceBean> list;
    OnItemClickCallback callback;



    public AssociatedAdpater(Context mContext, List<CommonBean.RelevanceBean> listData,
                             OnItemClickCallback<Integer> onItemClickCallback) {

        this.context = mContext;
        this.list = listData;
        this.callback = onItemClickCallback;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.associated_recly_item, null);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CommonBean.RelevanceBean relevanceBean = list.get(position);

        holder.CustomerName.setText(relevanceBean.getCustomerName());
        holder.CertTypeName.setText(relevanceBean.getCertID());
        holder.MobilePhone.setText(relevanceBean.getRelationNamem());


        imagebetween(relevanceBean.getRelationId(), holder.IrscreditLevel);

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

        LinearLayout llLay;
        TextView CustomerName, CertTypeName, MobilePhone;
        ImageView IrscreditLevel;

        public ViewHolder(View itemView) {
            super(itemView);

            llLay = (LinearLayout) itemView.findViewById(R.id.ll_lay);
            IrscreditLevel = (ImageView) itemView.findViewById(R.id.IrscreditLevel);
            CustomerName = (TextView) itemView.findViewById(R.id.CustomerName);
            CertTypeName = (TextView) itemView.findViewById(R.id.CertTypeName);
            MobilePhone = (TextView) itemView.findViewById(R.id.MobilePhone);

        }
    }
}