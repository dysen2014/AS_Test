package com.pactera.financialmanager.credit.common.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.common_res.common.views.uber.UberProgressView;
import com.dysen.pullloadmore_recyclerview.PullLoadMoreRecyclerView;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.WarnMainBean;
import com.pactera.financialmanager.credit.common.bean.warn.WarnCrmParamBean;
import com.pactera.financialmanager.credit.main.warn.WarnMainFragment;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.util.LimitsUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dysen on 11/3/2017.
 */

public class WarnMainAdapter {
    public static class MyWarnAdapter extends RecyclerView.Adapter<MyWarnAdapter.ViewHolder> {

        public int index;
        Context context;
        List<WarnCrmParamBean.TitleBean> listCounts;
        List<WarnCrmParamBean.creditCount> creditCounts;
        List<WarnMainBean> listData;
        OnItemClickCallback callback;
        private int COL_SIZE = 4;

        public MyWarnAdapter(Context context, List<WarnMainBean> list) {
            this.context = context;
            this.listData = list;
        }

        public MyWarnAdapter(Context context, List<WarnMainBean> list, List<WarnCrmParamBean.TitleBean> listCount,
                             List<WarnCrmParamBean.creditCount> creditCountlist, OnItemClickCallback<Integer> callback) {
            this.context = context;
            this.listData = list;
            this.listCounts = listCount;
            this.creditCounts = creditCountlist;
            this.callback = callback;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.warn_main_item, parent,
                    false);
            //view.setBackgroundColor(Color.RED);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            WarnMainBean warnMainBean = listData.get(position);
            holder.pullLoadMore.setGridLayout(COL_SIZE);
            holder.pullLoadMore.setPushRefreshEnable(false);
            holder.pullLoadMore.setPullRefreshEnable(false);
            holder.tvTitleColor.setBackgroundResource(warnMainBean.titleColor);
            holder.tvTitle.setText(warnMainBean.title);

//            if (listData.get(position).list.size() <= COL_SIZE) {
//                LinearLayout.LayoutParams lp;
//                lp = (LinearLayout.LayoutParams) holder.llContentLay.getLayoutParams();
//                lp.height = 260;
//                holder.llContentLay.setLayoutParams(lp);
//            } else if (4 < listData.get(position).list.size() && listData.get(position).list.size
//                    () <= 8) {
                LinearLayout.LayoutParams lp;
                lp = (LinearLayout.LayoutParams) holder.llContentLay.getLayoutParams();
                lp.height = 480;
                holder.llContentLay.setLayoutParams(lp);
//            }
            holder.pullLoadMore.setAdapter(new MyContentAdapter(context, "warn", position,
                    warnMainBean.list, listCounts, creditCounts, new OnItemClickCallback<Integer>() {

                @Override
                public void onClick(View view, final Integer info) {
                    index = info;
//                                    LogUtils.v(position == 0 ? (position + "===" + index) :
//                                            (position + "===" + index + "===" + (listData.get(0)
//                                                    .list.size() + index)));
//                    if (position == 0)

//                    else if (position == 1)
//                        callback.onClick(view, listData.get(position - 1).list.size() +
//                                index);
//                    else
                        ;
                }

                @Override
                public void onLongClick(View view, Integer info) {

                }

                @Override
                public void onClick(View view, int position, int index) {
                    LogUtils.d("我走这里没有");

                    callback.onClick(view, position, index);
                }
            }));
        }

        @Override
        public int getItemCount() {
            return listData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.tv_hide_data)
            TextView tvHideData;
            @Bind(R.id.uber_pgsview)
            UberProgressView uberPgsview;
            @Bind(R.id.tv_title_color)
            TextView tvTitleColor;
            @Bind(R.id.tv_title)
            TextView tvTitle;
            @Bind(R.id.ll_title)
            LinearLayout llTitle;
            @Bind(R.id.pull_load_more)
            PullLoadMoreRecyclerView pullLoadMore;
            @Bind(R.id.ll_card)
            LinearLayout llCard;
            @Bind(R.id.ll_content_lay)
            LinearLayout llContentLay;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                uberPgsview.setVisibility(View.INVISIBLE);
//                tvHideData.setVisibility(View.VISIBLE);
            }
        }
    }

    public static class MyServiceAdapter extends RecyclerView.Adapter<MyServiceAdapter.ViewHolder> {

        public int index;
        Context context;
        List<WarnMainBean> listData;
        OnItemClickCallback callback;
        private int COL_SIZE = 4;

        public MyServiceAdapter(Context context, List<WarnMainBean> list) {
            this.context = context;
            this.listData = list;
        }

        public MyServiceAdapter(Context context, List<WarnMainBean> list, OnItemClickCallback<Integer> callback) {
            this.context = context;
            this.listData = list;
            this.callback = callback;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.warn_main_item, parent,
                    false);
            //view.setBackgroundColor(Color.RED);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            WarnMainBean warnMainBean = listData.get(position);
            holder.pullLoadMore.setGridLayout(COL_SIZE);
            holder.pullLoadMore.setPushRefreshEnable(false);
            holder.pullLoadMore.setPullRefreshEnable(false);

            holder.tvTitleColor.setBackgroundResource(warnMainBean.titleColor);
            holder.tvTitle.setText(warnMainBean.title);

            LinearLayout.LayoutParams lp;
            lp = (LinearLayout.LayoutParams) holder.llContentLay.getLayoutParams();
            lp.height = 640;
            holder.llContentLay.setLayoutParams(lp);

            holder.pullLoadMore.setAdapter(new MyContentAdapter(context, "service", position,
                    warnMainBean.list, new
                    OnItemClickCallback<Integer>() {

                        @Override
                        public void onClick(View view, final Integer info) {
                            index = info;
//                            LogUtils.v(position == 0 ? (position + "===" + index) :
//                                    (position + "===" + index + "===" + (listData.get(0)
//                                            .list.size() + index)));
//
//                            LogUtils.d("position"+position);
//                            LogUtils.d("index"+index);
//                            LogUtils.d("listdata==="+(listData.get(0)
//                                    .list.size() + index));
////                            if (position == 0)//0~~8
//                            LogUtils.d(""+listData.get(position).list.get(index).getName_picture());
                                callback.onClick(view, position,index);
//                            else if (position == 1)//9~~12
//                                callback.onClick(view, listData.get(position - 1).list.size() +
//                                        index);
//                            else if (position == 2)//13~~15
//                                callback.onClick(view, listData.get(position - 2).list.size() +
//                                        listData.get(position - 1).list.size() +
//                                        index);
                        }

                        @Override
                        public void onLongClick(View view, Integer info) {

                        }


                        //服务页面的点击事件

                        @Override
                        public void onClick(View view, int ssaa, int sss) {

                            LogUtils.d("点击的下标"+ssaa);
                            LogUtils.d("sss"+sss);
                            callback.onClick(view, position,ssaa);

                        }
                    }));
        }

        @Override
        public int getItemCount() {
            return listData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.tv_hide_data)
            TextView tvHideData;
            @Bind(R.id.uber_pgsview)
            UberProgressView uberPgsview;
            @Bind(R.id.tv_title_color)
            TextView tvTitleColor;
            @Bind(R.id.tv_title)
            TextView tvTitle;
            @Bind(R.id.ll_title)
            LinearLayout llTitle;
            @Bind(R.id.pull_load_more)
            PullLoadMoreRecyclerView pullLoadMore;
            @Bind(R.id.ll_card)
            LinearLayout llCard;
            @Bind(R.id.ll_content_lay)
            LinearLayout llContentLay;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                uberPgsview.setVisibility(View.INVISIBLE);
//                tvHideData.setVisibility(View.VISIBLE);
            }
        }
    }

    public static class MyContentAdapter extends RecyclerView.Adapter<MyContentAdapter
            .ContentViewHolder> {

        Context context;
        private final String typeName;
        int index;
        List<WarnCrmParamBean.TitleBean> listCount;
        List<WarnCrmParamBean.creditCount> creditCountList;
        List<WarnMainBean.Content> list;
        OnItemClickCallback callback;
        private int numCount;

        public MyContentAdapter(Context context, String typeName, int position, List<WarnMainBean.Content> lists,
                                OnItemClickCallback<Integer> callback) {
            this.context = context;
            this.typeName = typeName;
            this.index = position;
            this.list = lists;
            this.callback = callback;
        }

        public MyContentAdapter(Context context, String typeName, int position, List<WarnMainBean
                .Content> lists, List<WarnCrmParamBean.TitleBean>
                                        listCounts, List<WarnCrmParamBean.creditCount> creditCounts, OnItemClickCallback<Integer> callback) {
            this.context = context;
            this.typeName = typeName;
            this.index = position;
            this.list = lists;
            this.callback = callback;
            this.listCount = listCounts;
            this.creditCountList = creditCounts;
        }

        @Override
        public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.warn_main_item_content, parent,
                    false);
            ContentViewHolder viewHolder = new ContentViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ContentViewHolder holder, final int position) {
            final WarnMainBean.Content content = list.get(position);
            holder.tvName.setText(content.getName());
            holder.tvNameInfo.setText(content.getNameInfo());
            holder.ivImg.setImageResource(content.getImgId());

            if (!content.getNumId().equals("1")) {
                for (int i = 0; i < listCount.size(); i++) {
                    if (content.getNumId().equals(listCount.get(i).getTYPE())) {
                        numCount = list.size();
                        new ParentActivity().setBadgeView(context, holder.ivImg, listCount.get(i)
                                .getRows());
                    }
                    int num = i - (listCount.size() - creditCountList.size());

                    if (num >= 0) {
                        if (content.getName()
                                .equals(creditCountList.get(num).getItemName())) {
                            numCount = creditCountList.size();
                            new ParentActivity().setBadgeView(context, holder.ivImg, creditCountList.get(num)
                                    .getCount());
                        }
                    }

                }

            }

//            if (typeName.equals("warn")) {
//                switch (index) {
//                    case 0:
//                        if (listCount != null && listCount.size() > 0) {//8个是 crm的
//                        }
//
//                        if (position == 3 || position == 1) {
////                                holder.cardItem.setVisibility(View.GONE);
////                                WarnMainFragment.setSelectorItemColor(holder.cardItem, R.color
////                                        .view_selected, R.drawable.cardview_bg_boder);
//                            LogUtils.d(list.size() + "===" + listCount.size() + "+++" + position);
////                                list.remove(position);
////                                listCount.remove(position);
//                        }
//
//                        break;
//                    case 1:
////                        if (content.getName().contains("贷") || content.getName().contains
////                                ("即将到期")) {//3个是信贷的
////                            if (ParamUtils.returnCredit.equals("N")) {//无信贷权限
//
//                                holder.ivTrials.setVisibility(View.INVISIBLE);
//                                holder.ivImg.setImageResource(hideImgIdWarn[position]);
//                            }
////                            holder.ivTrials.setVisibility(View.VISIBLE);
//                        }
//                        break;
//                    case 2:
//                        break;
//                }
//            } else if (typeName.equals("service")) {
//                switch (index) {
//                    case 0:
//                        if (position == 8)
//                            if (!LimitsUtil.checkUserLimit(context, limitsCrm[8])) {
//                                holder.ivImg.setImageResource(R.mipmap.service_grey_ttgl);
//                            }
//                        break;
//                    case 1:
//                        holder.ivTrials.setVisibility(View.VISIBLE);
//                        if (ParamUtils.returnCredit.equals("N")) {//无信贷权限
//                            holder.ivImg.setImageResource(hideImgIdService[position]);
//                        } else if (ParamUtils.returnCredit.equals("Y")) {
//                            if (position == 3) {//审批
//                                if (ParamUtils.approvals.equals("N")) {
//                                    holder.ivImg.setImageResource(hideImgIdService[position]);
//                                }
//                            }
//                        }
//                        break;
//                    case 2:
//                        break;
//                }
//            }

            holder.llWarnType.setOnClickListener(new View.OnClickListener() {
                @SuppressLint({"ResourceAsColor", "ResourceType"})
                @Override
                public void onClick(View view) {
                    callback.onClick(view, position, numCount);

                    LogUtils.d("view =="+view.getId());
                    ParentActivity.MyUtils.setSelItemColor(holder.llWarnType, R.color
                            .view_selected, R.drawable.cardview_bg_boder);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ContentViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.iv_img)
            ImageView ivImg;
            @Bind(R.id.tv_name)
            TextView tvName;
            @Bind(R.id.tv_name_info)
            TextView tvNameInfo;
            @Bind(R.id.card_item)
            CardView cardItem;
            @Bind(R.id.iv_trials)
            ImageView ivTrials;
            @Bind(R.id.ll_warn_type)
            LinearLayout llWarnType;

            public ContentViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    public static class TabAdapter extends RecyclerView.Adapter<TabAdapter
            .ContentViewHolder> {

        Context context;
        List<String> listName = new ArrayList<>();
        List<Integer> listImg = new ArrayList<>();

        OnItemClickCallback callback;

        public TabAdapter(Context context, String[] names, int[] imgAs) {
            for (int i = 0; i < names.length; i++) {
                listImg.add(imgAs[i]);
                listName.add(names[i]);
            }
            this.context = context;
        }

        public TabAdapter(Context context, String[] names, int[] imgAs,
                          OnItemClickCallback<Integer> callback) {
            for (int i = 0; i < names.length; i++) {
                listImg.add(imgAs[i]);
                listName.add(names[i]);
            }
            this.context = context;
            this.callback = callback;
        }

        @Override
        public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.tab_item, parent,
                    false);
            //view.setBackgroundColor(Color.RED);
            ContentViewHolder viewHolder = new ContentViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ContentViewHolder holder, final int position) {

            holder.tabName.setText(listName.get(position));
            holder.tabName.setTextColor(context.getResources().getColor(R.color.common_left_txt));
//            holder.tabImg.setImageResource(listImg.get(position));
//
            holder.llLay.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {
                    callback.onClick(view, position);
                    holder.tabName.setTextColor(context.getResources().getColor(R.color.common_tab_bg));
                    holder.tabImg.setImageResource(listImg.get(position));
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return listName.size();
        }

        public class ContentViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.tab_img)
            ImageView tabImg;
            @Bind(R.id.tab_name)
            TextView tabName;
            @Bind(R.id.ll_lay)
            LinearLayout llLay;

            public ContentViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
