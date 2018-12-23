package com.pactera.financialmanager.credit.main.service.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.common_res.common.views.ViewUtils;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.bean.CommonBean;
import com.pactera.financialmanager.credit.main.service.cus_screening.ScreeningListActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017/7/14.
 */

public class MyAdapter {

    //客户查询
    static class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ViewHolder> {

        Context context;
        List<ListPerson> list;
        OnItemClickCallback callback;
        int[] imgId = new int[]{R.mipmap.level_a, R.mipmap.level_b, R.mipmap.level_c, R.mipmap
                .level_no};

        public CustomerListAdapter(Context context, List<ListPerson> list) {
            this.context = context;
            this.list = list;
        }

        public CustomerListAdapter(Context context, List<ListPerson> list, OnItemClickCallback<Integer> callback) {
            this.context = context;
            this.list = list;
            this.callback = callback;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.customer_list_item, null);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {


            final ListPerson listPerson = list.get(position);
            holder.CustomerName.setText(listPerson.getCustomerName());
            holder.CertTypeName.setText(listPerson.getCertID());
            holder.MobilePhone.setText(listPerson.getMobilePhone());
            holder.IrscreditLevel.setText(listPerson.getIrscreditLevel());
            holder.IrscreditLevel.setBackgroundResource(switchImg(listPerson.getIrscreditLevel()));
            holder.llLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    callback.onClick(view, position);
                }
            });

            //客户查询
            holder.customer_query.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(view.getContext(), QueryList.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("transType", "queryCustomer");
                    bundle.putString("CustomerType", listPerson.getCustomerType());
                    bundle.putString("CustomerName", listPerson.getCustomerName());
                    bundle.putString("CertTypeName", listPerson.getCertTypeName());
                    bundle.putString("CertID", listPerson.getCertID());

                    intent.putExtras(bundle);
        /*
         * 1 是请求的一个标志  requestCode
        */
                    view.getContext().startActivity(intent);
                }
            });
            //业务查询
            holder.Business_queries.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), BusinessList.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("CustomerType", listPerson.getCustomerType());
                    bundle.putString("CustomerName", listPerson.getCustomerName());
                    bundle.putString("CertTypeName", listPerson.getCertTypeName());
                    bundle.putString("CertID", listPerson.getCertID());

                    intent.putExtras(bundle);

        /*
         * 1 是请求的一个标志  requestCode
        */
                    view.getContext().startActivity(intent);
                }
            });
            //黑灰名单
            holder.Dark_grey_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ScreeningListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("transType", "黑灰名单");
                    bundle.putString("CustomerName", listPerson.getCustomerName());
                    bundle.putString("CertTypeName", listPerson.getCertTypeName());
                    bundle.putString("CertID", listPerson.getCertID());
                    intent.putExtras(bundle);
        /*
         * 1 是请求的一个标志  requestCode
        */
                    view.getContext().startActivity(intent);
                }
            });
            //关联查询
            holder.Associated_query.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), AssocQuryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("transType", "关联查询");
                    bundle.putString("CustomerType", listPerson.getCustomerType());
                    bundle.putString("CustomerName", listPerson.getCustomerName());
                    bundle.putString("CertTypeName", listPerson.getCertTypeName());
                    bundle.putString("CertID", listPerson.getCertID());
                    intent.putExtras(bundle);
        /*
         * 1 是请求的一个标志  requestCode
        */
                    view.getContext().startActivity(intent);
                }
            });

            holder.llLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onClick(view, position);
                }
            });
        }

        private int switchImg(String irscreditLevel) {

            int id = -1;
            if (!irscreditLevel.equals(""))
                switch (irscreditLevel.toUpperCase().substring(0, 1)) {
                    case "A":
                        id = imgId[0];
                        break;
                    case "B":
                        id = imgId[1];
                        break;
                    case "C":
                    case "D":
                        id = imgId[2];
                        break;
                    case "Z":
                        id = imgId[3];
                        break;
                }
            else {
                id = imgId[3];
            }
            return id;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            LinearLayout llLay;
            TextView IrscreditLevel, CustomerName, CertTypeName, MobilePhone;
            private TextView customer_query;
            private TextView Business_queries;
            private TextView Dark_grey_list;
            private TextView Associated_query;

            public ViewHolder(View itemView) {
                super(itemView);
                llLay = (LinearLayout) itemView.findViewById(R.id.ll_lay);
                IrscreditLevel = (TextView) itemView.findViewById(R.id.IrscreditLevel);
                CustomerName = (TextView) itemView.findViewById(R.id.CustomerName);
                CertTypeName = (TextView) itemView.findViewById(R.id.CertTypeName);
                MobilePhone = (TextView) itemView.findViewById(R.id.MobilePhone);

                customer_query = (TextView) itemView.findViewById(R.id.customer_query);
                Business_queries = (TextView) itemView.findViewById(R.id.Business_queries);
                Dark_grey_list = (TextView) itemView.findViewById(R.id.Dark_grey_list);
                Associated_query = (TextView) itemView.findViewById(R.id.Associated_query);
            }
        }
    }

    //业务查询
    static class BusinessListAdapter extends RecyclerView.Adapter<BusinessListAdapter.ViewHolder> {

        Context context;
        List<ListBusinessList> list;
        OnItemClickCallback callback;
        int[] imgId = new int[]{R.mipmap.level_a, R.mipmap.level_b, R.mipmap.level_c, R.mipmap
                .level_no};

        public BusinessListAdapter(Context context, List<ListBusinessList> list) {
            this.context = context;
            this.list = list;
        }

        public BusinessListAdapter(Context context, List<ListBusinessList> list, OnItemClickCallback<Integer> callback) {
            this.context = context;
            this.list = list;
            this.callback = callback;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.business_list_item, null);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @SuppressLint("ResourceType")
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            final ListBusinessList businessList = list.get(position);
            holder.CustomerName.setText(businessList.getCustomerName());
            holder.BusinessType.setText(businessList.getBusinessName());
            holder.BusinessType.setTextColor(context.getResources().getColor(R.color.common_tab_bg));
            holder.BusinessSum.setText(businessList.getBusinessSum());
            holder.Balance.setText(businessList.getBalance());
            holder.OverDueBalance.setText(businessList.getOverDueBalance());
            holder.InterestBalance.setText(businessList.getInterestBalance());

            if (Float.valueOf(businessList.getOverDueBalance()) > 0
                    || Float.valueOf(businessList.getInterestBalance()) > 0) {
                holder.picture.setBackgroundResource(R.mipmap.overdue_);
            }
            if (position % 2 != 0) {//第二项 0开始的

            }

//            holder.picture.setBackgroundResource(switchImg(list.get(position).get()));
            holder.llLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    callback.onClick(view, position);
                }
            });

            //客户查询
            holder.customer_query.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(view.getContext(), QueryList.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("transType", "queryCustomer");
                    bundle.putString("CustomerType", businessList.getCustomerType());
                    bundle.putString("CustomerName", businessList.getCustomerName());
                    bundle.putString("CertTypeName", businessList.getCertTypeName());
                    bundle.putString("CertID", businessList.getCertID());

                    intent.putExtras(bundle);
        /*
         * 1 是请求的一个标志  requestCode
        */
                    view.getContext().startActivity(intent);
                }
            });
            //业务查询
            holder.Business_queries.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), BusinessList.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("CustomerType", businessList.getCustomerType());
                    bundle.putString("CustomerName", businessList.getCustomerName());
                    bundle.putString("CertTypeName", businessList.getCertTypeName());
                    bundle.putString("CertID", businessList.getCertID());

                    intent.putExtras(bundle);

        /*
         * 1 是请求的一个标志  requestCode
        */
                    view.getContext().startActivity(intent);
                }
            });
            //黑灰名单
            holder.Dark_grey_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ScreeningListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("transType", "黑灰名单");
                    bundle.putString("CustomerName", businessList.getCustomerName());
                    bundle.putString("CertTypeName", businessList.getCertTypeName());
                    bundle.putString("CertID", businessList.getCertID());
                    intent.putExtras(bundle);
        /*
         * 1 是请求的一个标志  requestCode
        */
                    view.getContext().startActivity(intent);
                }
            });
            //关联查询
            holder.Associated_query.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), AssocQuryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("transType", "关联查询");
                    bundle.putString("CustomerType", businessList.getCustomerType());
                    bundle.putString("CustomerName", businessList.getCustomerName());
                    bundle.putString("CertTypeName", businessList.getCertTypeName());
                    bundle.putString("CertID", businessList.getCertID());
                    intent.putExtras(bundle);
        /*
         * 1 是请求的一个标志  requestCode
        */
                    view.getContext().startActivity(intent);
                }
            });

            holder.llLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onClick(view, position);
                }
            });
        }

        private int switchImg(String irscreditLevel) {
            int id = -1;
            switch (irscreditLevel.toUpperCase().substring(0, 1)) {
                case "A":
                    id = imgId[0];
                    break;
                case "B":
                    id = imgId[1];
                    break;
                case "C":
                case "D":
                    id = imgId[2];
                    break;
                case "Z":
                    id = imgId[3];
                    break;
            }
            return id;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            LinearLayout llLay;
            TextView picture, CustomerName, BusinessType, BusinessSum, Balance, OverDueBalance, InterestBalance;

            private TextView customer_query;
            private TextView Business_queries;
            private TextView Dark_grey_list;
            private TextView Associated_query;

            public ViewHolder(View itemView) {
                super(itemView);
                llLay = (LinearLayout) itemView.findViewById(R.id.ll_lay);
                picture = (TextView) itemView.findViewById(R.id.picture);
                CustomerName = (TextView) itemView.findViewById(R.id.CustomerName);
                BusinessType = (TextView) itemView.findViewById(R.id.businessType);
                BusinessSum = (TextView) itemView.findViewById(R.id.BusinessSum);
                Balance = (TextView) itemView.findViewById(R.id.Balance);
                OverDueBalance = (TextView) itemView.findViewById(R.id.OverDueBalance);
                InterestBalance = (TextView) itemView.findViewById(R.id.InterestBalance);

                customer_query = (TextView) itemView.findViewById(R.id.customer_query);
                Business_queries = (TextView) itemView.findViewById(R.id.Business_queries);
                Dark_grey_list = (TextView) itemView.findViewById(R.id.Dark_grey_list);
                Associated_query = (TextView) itemView.findViewById(R.id.Associated_query);


            }
        }
    }

    //黑灰名单
    public static class ScreeningAdapter extends RecyclerView.Adapter<ScreeningAdapter.ViewHolder> {
        Context context;
        List<CommonBean.ScreeningBean> list;
        OnItemClickCallback callback;

        public ScreeningAdapter(Context context, List<CommonBean.ScreeningBean> list) {
            this.context = context;
            this.list = list;
        }

        public ScreeningAdapter(Context context, List<CommonBean.ScreeningBean> list, OnItemClickCallback callback) {
            this.context = context;
            this.list = list;
            this.callback = callback;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.screening_list_item, null);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            final CommonBean.ScreeningBean screeningBean = list.get(position);

            holder.cusName.setText(screeningBean.getCustomerName());
            holder.certId.setText(screeningBean.getCertID());
            holder.remarkDate.setText(screeningBean.getData());
            holder.remarkInfo.setText(screeningBean.getRemark());

            if (screeningBean.getSpecialType() != null && screeningBean.getSpecialType().equals
                    ("01")) {
                holder.picture.setBackgroundResource(R.mipmap.cus_screening_black);
            } else {

                holder.picture.setBackgroundResource(R.mipmap.cus_screening_gray);
            }

            //客户查询
            holder.customer_query.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(view.getContext(), QueryList.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("transType", "queryCustomer");
                    bundle.putString("CustomerType", screeningBean.getCustomerType());
                    bundle.putString("CustomerName", screeningBean.getCustomerName());
                    bundle.putString("CertTypeName", screeningBean.getCertTypeName());
                    bundle.putString("CertID", screeningBean.getCertID());

                    intent.putExtras(bundle);
        /*
         * 1 是请求的一个标志  requestCode
        */
                    view.getContext().startActivity(intent);
                }
            });
            //业务查询
            holder.Business_queries.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), BusinessList.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("CustomerType", screeningBean.getCustomerType());
                    bundle.putString("CustomerName", screeningBean.getCustomerName());
                    bundle.putString("CertTypeName", screeningBean.getCertTypeName());
                    bundle.putString("CertID", screeningBean.getCertID());

                    intent.putExtras(bundle);

        /*
         * 1 是请求的一个标志  requestCode
        */
                    view.getContext().startActivity(intent);
                }
            });
            //黑灰名单
            holder.Dark_grey_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ScreeningListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("transType", "黑灰名单");
                    bundle.putString("CustomerName", screeningBean.getCustomerName());
                    bundle.putString("CertTypeName", screeningBean.getCertTypeName());
                    bundle.putString("CertID", screeningBean.getCertID());
                    intent.putExtras(bundle);
        /*
         * 1 是请求的一个标志  requestCode
        */
                    view.getContext().startActivity(intent);
                }
            });
            //关联查询
            holder.Associated_query.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), AssocQuryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("transType", "关联查询");
                    bundle.putString("CustomerType", screeningBean.getCustomerType());
                    bundle.putString("CustomerName", screeningBean.getCustomerName());
                    bundle.putString("CertTypeName", screeningBean.getCertTypeName());
                    bundle.putString("CertID", screeningBean.getCertID());
                    intent.putExtras(bundle);
        /*
         * 1 是请求的一个标志  requestCode
        */
                    view.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.picture)
            TextView picture;
            @Bind(R.id.cus_name)
            TextView cusName;
            @Bind(R.id.cert_id)
            TextView certId;
            @Bind(R.id.remark_date)
            TextView remarkDate;
            @Bind(R.id.remark_name)
            TextView remarkName;
            @Bind(R.id.remark_info)
            TextView remarkInfo;
            @Bind(R.id.ll_lay)
            LinearLayout llLay;

            private TextView customer_query;
            private TextView Business_queries;
            private TextView Dark_grey_list;
            private TextView Associated_query;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);

                customer_query = (TextView) itemView.findViewById(R.id.customer_query);
                Business_queries = (TextView) itemView.findViewById(R.id.Business_queries);
                Dark_grey_list = (TextView) itemView.findViewById(R.id.Dark_grey_list);
                Associated_query = (TextView) itemView.findViewById(R.id.Associated_query);

            }
        }
    }


    //关联关系
    public static class RelevanceAdapter extends RecyclerView.Adapter<RelevanceAdapter.ViewHolder> {
        Context context;
        List<CommonBean.RelevanceBean> list;
        OnItemClickCallback callback;

        public RelevanceAdapter(Context context, List<CommonBean.RelevanceBean> list) {
            this.context = context;
            this.list = list;
        }

        public RelevanceAdapter(Context context, List<CommonBean.RelevanceBean> list,
                                OnItemClickCallback<Integer> callback) {
            this.context = context;
            this.list = list;
            this.callback = callback;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.relevance_list_item, null);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            final CommonBean.RelevanceBean relevanceBean = list.get(position);
            holder.cusName.setText(relevanceBean.getCustomerName());
            holder.relationship.setText(relevanceBean.getRelationNamem());
            holder.businessTypes.setText(relevanceBean.getBusinessName());
            holder.overdueAmount.setText(relevanceBean.getOverDueBalance());
            holder.loanAmount.setText(relevanceBean.getBalance());
            holder.overdueDays.setText(relevanceBean.getOverDueDays());

            imagebetween(relevanceBean.getRelationId(), holder.picture);

            //客户查询
            holder.customer_query.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(view.getContext(), QueryList.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("transType", "queryCustomer");
                    bundle.putString("CustomerType", relevanceBean.getCustomerType());
                    bundle.putString("CustomerName", relevanceBean.getCustomerName());
                    bundle.putString("CertTypeName", relevanceBean.getCertTypeName());
                    bundle.putString("CertID", relevanceBean.getCertID());

                    intent.putExtras(bundle);
        /*
         * 1 是请求的一个标志  requestCode
        */
                    view.getContext().startActivity(intent);
                }
            });
            //业务查询
            holder.Business_queries.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), BusinessList.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("CustomerType", relevanceBean.getCustomerType());
                    bundle.putString("CustomerName", relevanceBean.getCustomerName());
                    bundle.putString("CertTypeName", relevanceBean.getCertTypeName());
                    bundle.putString("CertID", relevanceBean.getCertID());

                    intent.putExtras(bundle);

        /*
         * 1 是请求的一个标志  requestCode
        */
                    view.getContext().startActivity(intent);
                }
            });
            //黑灰名单
            holder.Dark_grey_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ScreeningListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("transType", "黑灰名单");
                    bundle.putString("CustomerName", relevanceBean.getCustomerName());
                    bundle.putString("CertTypeName", relevanceBean.getCertTypeName());
                    bundle.putString("CertID", relevanceBean.getCertID());
                    intent.putExtras(bundle);
        /*
         * 1 是请求的一个标志  requestCode
        */
                    view.getContext().startActivity(intent);
                }
            });
            //关联查询
            holder.Associated_query.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), AssocQuryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("transType", "关联查询");
                    bundle.putString("CustomerType", relevanceBean.getCustomerType());
                    bundle.putString("CustomerName", relevanceBean.getCustomerName());
                    bundle.putString("CertTypeName", relevanceBean.getCertTypeName());
                    bundle.putString("CertID", relevanceBean.getCertID());
                    intent.putExtras(bundle);
        /*
         * 1 是请求的一个标志  requestCode
        */
                    view.getContext().startActivity(intent);
                }
            });

            holder.llLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onClick(view, position);
                }
            });
        }

        private void imagebetween(String RelationId, TextView picture) {
            switch (RelationId) {
                case "01":   //关键人
                    picture.setBackgroundResource(R.mipmap.relevance_group_cus_member);
                    break;
                case "03":   //家庭关系
                    picture.setBackgroundResource(R.mipmap.relevance_family);
                    break;
                case "04":   //关联集团
                case "54":
                    picture.setBackgroundResource(R.mipmap.relevance_group);
                    break;
                case "05":   //联保小组
                case "55":
                    picture.setBackgroundResource(R.mipmap.relevance_inprofor_team);
                    break;
                case "06":   //集团客户群成员
                case "56":
                    picture.setBackgroundResource(R.mipmap.relevance_group_cus_member);
                    break;
                case "51":   //任职
                    picture.setBackgroundResource(R.mipmap.relevance_serving);
                    break;
                case "52":   //资本构成
                    picture.setBackgroundResource(R.mipmap.relevance_capital_composition);
                    break;
                case "02":   //投资
                    picture.setBackgroundResource(R.mipmap.relevance_investment);
                    break;
                case "99":   //其他
                    picture.setBackgroundResource(R.mipmap.relevance_other);
                    break;
                case "000":  //组合
                    picture.setBackgroundResource(R.drawable.variety);
                    break;
            }
        }


        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.picture)
            TextView picture;
            @Bind(R.id.cus_name)
            TextView cusName;
            @Bind(R.id.relationship)
            TextView relationship;
            @Bind(R.id.business_types)
            TextView businessTypes;
            @Bind(R.id.overdue_amount)
            TextView overdueAmount;
            @Bind(R.id.loan_amount)
            TextView loanAmount;
            @Bind(R.id.overdue_days)
            TextView overdueDays;
            @Bind(R.id.ll_lay)
            LinearLayout llLay;

            private TextView customer_query;
            private TextView Business_queries;
            private TextView Dark_grey_list;
            private TextView Associated_query;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);

                customer_query = (TextView) itemView.findViewById(R.id.customer_query);
                Business_queries = (TextView) itemView.findViewById(R.id.Business_queries);
                Dark_grey_list = (TextView) itemView.findViewById(R.id.Dark_grey_list);
                Associated_query = (TextView) itemView.findViewById(R.id.Associated_query);
            }
        }
    }
}
