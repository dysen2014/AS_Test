package com.pactera.financialmanager.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.KJHttp;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.PullToRefreshLayout;
import com.pactera.financialmanager.ui.PullToRefreshLayout.OnRefreshListener;
import com.pactera.financialmanager.ui.PullableListView;
import com.pactera.financialmanager.ui.model.CustEvent;
import com.pactera.financialmanager.ui.model.FinancialDemandInfo;
import com.pactera.financialmanager.ui.nowproject.FinancialDemandItemDeailActivity;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.LogUtils;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作提醒中的 金融需求 下的界面
 *
 * @author cq 06-17
 */
public class FragmentFinancialDemand extends ParentFragment implements OnClickListener {

    private static final int INDEX_CKYY = 1;//存款预约
    private static final int INDEX_LCYY = 2;//理财预约
    private static final int INDEX_DKXQ = 3;//贷款需求
    private static final int INDEX_DZYH = 4;//电子银行产品需求
    private static final int INDEX_OTHER = 5;//其他需求

    //对私类型码
    private static final String TYPE_CKYY_PERSONNAL = "12";//存款预约
    private static final String TYPE_LCYY_PERSONNAL = "13";//理财预约
    private static final String TYPE_DKXQ_PERSONNAL = "14";//贷款需求
    private static final String TYPE_DZYH_PERSONNAL = "60";//电子银行产品需求
    private static final String TYPE_OTHER_PERSONNAL = "61";//其他需求

    //对公类型码
    private static final String TYPE_CKYY_COMMON = "29";//存款预约
    private static final String TYPE_DKXQ_COMMON = "30";//贷款需求war
    private static final String TYPE_OTHER_COMMON = "31";//其他需求


    // 加载布局
    private LinearLayout layLoading;
    private LinearLayout layNodata;
    private LinearLayout llLvTopOut;
    // 选项卡(存款预约、理财预约、贷款需求、电子银行产品需求,其它需求)
    private TextView tvCK, tvLC, tvDK, tvEbank;
    //            tvOther;
    private PullToRefreshLayout ptrl;
    private PullableListView lvList;

    // 接口请求
    private HConnection HCon;
    // 存款预约
    private List<FinancialDemandInfo> depositDateList = new ArrayList<FinancialDemandInfo>();
    // 理财预约
    private List<FinancialDemandInfo> liCaiList = new ArrayList<FinancialDemandInfo>();
    // 贷款需求
    private List<FinancialDemandInfo> loanDemandsList = new ArrayList<FinancialDemandInfo>();
    // 电子银行产品需求
    private List<FinancialDemandInfo> eBankProductList = new ArrayList<FinancialDemandInfo>();
    // 其它需求
    private List<FinancialDemandInfo> otherDemandsList = new ArrayList<FinancialDemandInfo>();

    private FDAdapter depositListAdapter = new FDAdapter();
    private FDAdapter liCaiListAdapter = new FDAdapter();
    private FDAdapter loanListAdapter = new FDAdapter();
    private FDAdapter eBankListdapter = new FDAdapter();
    private FDAdapter otherDemandListAdapter = new FDAdapter();

    private int selectIndex = INDEX_CKYY;
    private String selectType = "12";
    private int offset = 1;
    private boolean isLoadmore = false;            // 是否上拉加载更多
    public static boolean isRefresh = false;
    public static boolean isDZYHClicked = false; //是否是电子银行产品需求

    private boolean isManager = false;//是否是管理岗


    /**
     * 是否为对个人提醒 (默认显示对个人)
     */
    private boolean isForperson = true;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isLoadmore) {
                // 隐藏地步
                ptrl.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            switch (msg.arg1) {
                // 1.存款预约
                case INDEX_CKYY:
                    List<FinancialDemandInfo> resultCK = (ArrayList<FinancialDemandInfo>) msg.obj;
                    depositDateList.addAll(resultCK);
                    setDepositOrder(depositDateList);
                    break;

                // 2.理财预约
                case INDEX_LCYY:
                    List<FinancialDemandInfo> resultLC = (ArrayList<FinancialDemandInfo>) msg.obj;
                      liCaiList.addAll(resultLC);
                setLiCaiOrder(liCaiList);
                break;

                // 3.贷款需求
                case INDEX_DKXQ:
                    List<FinancialDemandInfo> resultDK = (ArrayList<FinancialDemandInfo>) msg.obj;
                    loanDemandsList.addAll(resultDK);
                    setLoanDemands(loanDemandsList);
                    break;

                // 4.电子银行产品需求
                case INDEX_DZYH:
                    List<FinancialDemandInfo> resultEBank = (ArrayList<FinancialDemandInfo>) msg.obj;
                    eBankProductList.addAll(resultEBank);
                    setEBankDemands(eBankProductList);
                    break;
                // 5.其他需求
                case INDEX_OTHER:
                    List<FinancialDemandInfo> resultOther = (ArrayList<FinancialDemandInfo>) msg.obj;
                    otherDemandsList.addAll(resultOther);
                    setOtherDemands(otherDemandsList);
                    break;

                default:
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_financial_demand, null);
        setupViews(view);
        setListener();

        //要在这里设置第一个默认被选中的状态
        setTabTitle();
        setBgAndTextColor(tvCK);
        sendRequestForInfo(INDEX_CKYY);//"T000068"; // 消息提醒内容

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (isRefresh)
            refreshData();
    }

    private void setupViews(View view) {
        // 未来十天内定期存款到期 这三个
        tvCK = (TextView) view.findViewById(R.id.tv_fd_itempro1);
        tvLC = (TextView) view.findViewById(R.id.tv_fd_itempro2);
        tvDK = (TextView) view.findViewById(R.id.tv_fd_itempro3);
        tvEbank = (TextView) view.findViewById(R.id.tv_fd_itempro4);
//        tvOther = (TextView) view.findViewById(R.id.tv_fd_itempro5);
        layLoading = (LinearLayout) view.findViewById(R.id.lay_loading);
        layNodata = (LinearLayout) view.findViewById(R.id.lay_nodata);

        // listview，届时，不同的条目对应不同的适配器
//		lvPro = (ListView) view.findViewById(R.id.lv_workplace);
        ptrl = ((PullToRefreshLayout) view.findViewById(R.id.refresh_view));
        lvList = (PullableListView) view.findViewById(R.id.lv_pulllist);
        lvList.setAdapter(depositListAdapter);

        //只能将顶部的标题栏通过代码动态的添加到界面上了
        llLvTopOut = (LinearLayout) view.findViewById(R.id.ll_lvtop);

        lvList.setPullstatus(false, true);
        ptrl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                // 上拉加载更多
                isLoadmore = true;
                offset++;

                sendRequestForInfo(selectIndex); //"T000068"; 消息提醒内容
            }
        });
//点击条目跳转
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent();
                mIntent.setClass(getActivity(), FinancialDemandItemDeailActivity.class);
                Bundle mBundle = new Bundle();
                isDZYHClicked = false;

                switch (selectIndex) {
                    case INDEX_CKYY:
                        mBundle.putSerializable(FinancialDemandInfo.TAG, depositDateList.isEmpty() ? "" : depositDateList.get(position));
                        break;
                    case INDEX_LCYY:
                        mBundle.putSerializable(FinancialDemandInfo.TAG, liCaiList.isEmpty() ? "" : liCaiList.get(position));
                        break;
                    case INDEX_DKXQ:
                        mBundle.putSerializable(FinancialDemandInfo.TAG, loanDemandsList.isEmpty() ? "" : loanDemandsList.get(position));
                        break;
                    case INDEX_DZYH:
                        isDZYHClicked = true;
                        mBundle.putSerializable(FinancialDemandInfo.TAG, eBankProductList.isEmpty() ? "" : eBankProductList.get(position));
                        break;
                    case INDEX_OTHER:
                        mBundle.putSerializable(FinancialDemandInfo.TAG, otherDemandsList.isEmpty() ? "" : otherDemandsList.get(position));
                        break;
                }
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });


        checkIsManager();
    }


    private void checkIsManager() {
        // 检查是否是管理岗
        String staID = LogoActivity.user.getStaId().toUpperCase();
        if (staID.equals("ST000111") || staID.equals("ST000301")
                || staID.equals("ST000701") || staID.equals("ST000901")
                || staID.equals("ST001101") || staID.equals("ST000501")) {
            isManager = true;
        }
    }


    private void refreshData() {
        isRefresh = false;
        switch (selectIndex) {
            case INDEX_CKYY:
                depositDateList.removeAll(depositDateList);
                break;
            case INDEX_LCYY:
                liCaiList.removeAll(liCaiList);
                break;
            case INDEX_DKXQ:
                loanDemandsList.removeAll(loanDemandsList);
                break;
            case INDEX_DZYH:
                eBankProductList.removeAll(eBankProductList);
                break;
            case INDEX_OTHER:
                otherDemandsList.removeAll(otherDemandsList);
                break;
        }
        sendRequestForInfo(selectIndex); //"T000068"; 消息提醒内容
    }


    private void setListener() {
        // 三个文字的点击事件，在这里要做的是切换背景，和为listview换不同的适配器
        tvCK.setOnClickListener(this);
        tvLC.setOnClickListener(this);
        tvDK.setOnClickListener(this);
        tvEbank.setOnClickListener(this);
//        tvOther.setOnClickListener(this);
    }


    /**
     * 设置二级标签标题
     * 改变二级tab标题
     */
    private void setTabTitle() {
        List<CustEvent> events = null;
        if (isForperson) {
            events = WorkWarnFragment.tempEventsForPerson;
            tvLC.setVisibility(View.VISIBLE);
            tvEbank.setVisibility(View.VISIBLE);
        } else {
            events = WorkWarnFragment.tempEventsForCommon;
            tvLC.setVisibility(View.GONE);
            tvEbank.setVisibility(View.GONE);
        }
        if (events != null) {
            // 迭代获取标题名称
            for (CustEvent event : events) {
                String eventType = event.getEvent_type(); // 类型
                String eventName = event.getEvent_type_name(); // 名称
                //存款预约
                if (eventType.equals(TYPE_CKYY_PERSONNAL) || eventType.equals(TYPE_CKYY_COMMON)) {
                    tvCK.setText(eventName);
                }
                //理财需求
                if (eventType.equals(TYPE_LCYY_PERSONNAL)) {
                    tvLC.setText(eventName);
                }
                //贷款需求
                if (eventType.equals(TYPE_DKXQ_PERSONNAL) || eventType.equals(TYPE_DKXQ_COMMON)) {
                    tvDK.setText(eventName);
                }
                //电子产品需求
                if (eventType.equals(TYPE_DZYH_PERSONNAL)) {
                    tvEbank.setText(eventName);
                }
                //其它需求
//                if (eventType.equals(TYPE_OTHER_PERSONNAL) || eventType.equals(TYPE_OTHER_COMMON)) {
//                    tvOther.setText(eventName);
//                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        offset = 1;

        switch (v.getId()) {
            case R.id.tv_fd_itempro1:
                selectIndex = INDEX_CKYY;
                lvList.setAdapter(depositListAdapter);
                setBgAndTextColor(tvCK);
                depositDateList.removeAll(depositDateList);
                if (isForperson) {
                    selectType = TYPE_CKYY_PERSONNAL;
                } else {
                    selectType = TYPE_CKYY_COMMON;
                }
                break;
            case R.id.tv_fd_itempro2:
                selectIndex = INDEX_LCYY;
                lvList.setAdapter(liCaiListAdapter);
                setBgAndTextColor(tvLC);
                liCaiList.removeAll(liCaiList);
                if (isForperson) {
                    selectType = TYPE_LCYY_PERSONNAL;
                }
                break;
            case R.id.tv_fd_itempro3:
                selectIndex = INDEX_DKXQ;
                lvList.setAdapter(loanListAdapter);
                setBgAndTextColor(tvDK);
                loanDemandsList.removeAll(loanDemandsList);
                if (isForperson) {
                    selectType = TYPE_DKXQ_PERSONNAL;
                } else {
                    selectType = TYPE_DKXQ_COMMON;
                }
                break;
            case R.id.tv_fd_itempro4:
                selectIndex = INDEX_DZYH;
                lvList.setAdapter(eBankListdapter);
                setBgAndTextColor(tvEbank);
                eBankProductList.removeAll(eBankProductList);
                if (isForperson) {
                    selectType = TYPE_DZYH_PERSONNAL;
                }
                break;
//            case R.id.tv_fd_itempro5:
//                selectIndex = INDEX_OTHER;
//                lvList.setAdapter(otherDemandListAdapter);
//                setBgAndTextColor(tvOther);
//                otherDemandsList.removeAll(otherDemandsList);
//                if (isForperson) {
//                    selectType = TYPE_OTHER_PERSONNAL;
//                } else {
//                    selectType = TYPE_OTHER_COMMON;
//                }
//                break;
            default:
                break;
        }

        sendRequestForInfo(selectIndex); //"T000068"; 消息提醒内容四个需求按钮都进行消息内容查询
    }


    /**
     * 显示进度条
     */
    private void loading() {
        layLoading.setVisibility(View.VISIBLE);
        //lvPro.setVisibility(View.GONE);
        if (offset == 1) {
            ptrl.setVisibility(View.GONE);
        }
        layNodata.setVisibility(View.GONE);
    }

    /**
     * 隐藏进度条
     *
     * @param noDatas
     */
    private void hiddenLoading(boolean noDatas) {
        layLoading.setVisibility(View.GONE);
        if (noDatas) {
            layNodata.setVisibility(View.VISIBLE);
            //lvPro.setVisibility(View.GONE);
            ptrl.setVisibility(View.GONE);
        } else {
            layNodata.setVisibility(View.GONE);
            //lvPro.setVisibility(View.VISIBLE);
            ptrl.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 设置是否为对个人状态-->默认显示第一个标签
     *
     * @param isForperson
     */
    public void setIsForperson(boolean isForperson) {
        this.isForperson = isForperson;
        if (getActivity() == null) {
            return;
        }
        selectIndex = INDEX_CKYY;
        setTabTitle();
        setBgAndTextColor(tvCK);
        offset = 1;
        lvList.setAdapter(depositListAdapter);
        if (isForperson) {
            selectType = TYPE_CKYY_PERSONNAL;
        } else {
            selectType = TYPE_CKYY_COMMON;
        }
        depositDateList.removeAll(depositDateList);
        sendRequestForInfo(INDEX_CKYY); //"T000068"; 消息提醒内容
    }


    /**
     * 请求方法
     *
     * @param index
     */
    public void sendRequestForInfo(int index) {
        String spareTwo = "01";
        if (!isForperson) {
            spareTwo = "02";
        }
        if (Tool.haveNet(getActivity())) {
            loading();
            String staid = LogoActivity.user.getStaId();
            String requestType = InterfaceInfo.PRODATA_Get;
            String info = "&size=20" + "&offset=" + offset
                    + "&spareOne=" + selectType
                    + "&spareTwo=" + spareTwo
                    + "&jsonData={staid:\"" + staid + "\"}";
            HCon = RequestInfo(this, Constants.requestType.Other, requestType, info, index, false);
        }
    }

    @Override
    public void onConnected(Message msg) {
        super.onConnected(msg);

        HResponse res = null;
        switch (connectionIndex) {
            case HConnection.RESPONSE_ERROR:
                hiddenLoading(true);
                Toast.makeText(getActivity(), "请求失败.", Toast.LENGTH_SHORT).show();
                break;

            case INDEX_CKYY:
            case INDEX_LCYY:
            case INDEX_DKXQ:
            case INDEX_DZYH:
            case INDEX_OTHER:
                res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (res == null || res.dataJsonObject == null) {
                    return;
                }
                readJson(res.dataJsonObject, connectionIndex);
                break;
        }
    }

    /**
     * 解析接口数据
     *
     * @param tmpJsonObject
     * @param connectionIndex
     */
    private void readJson(JSONObject tmpJsonObject, int connectionIndex) {
        String retCode = "";
        if (tmpJsonObject.has("retCode")) { // 返回标志
            try {
                retCode = tmpJsonObject.getString("retCode");
            } catch (JSONException e1) {
                e1.printStackTrace();
                return;
            }
        }

        // 获取接口成功
        if (retCode.equals("0000")) {
            List<FinancialDemandInfo> prodateCKForperson = null;
            Message msg = new Message();
            msg.arg1 = connectionIndex;

            try {
                String group = tmpJsonObject.getString("group");
                prodateCKForperson = JSON.parseArray(group, FinancialDemandInfo.class);
                msg.obj = prodateCKForperson;
            } catch (Exception e) {
                e.printStackTrace();
            }
            handler.sendMessage(msg);

            String url= null;
            try {
                url = tmpJsonObject.getString("URL");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(null!=url){
                LogUtils.d("---------------------请求地址："+url);
                KJHttp kjHttp = new KJHttp();
                kjHttp.get(url, null);
            }

        } else {
            hiddenLoading(true);
            Toast.makeText(getActivity(), "请求失败! 错误代码:" + retCode, Toast.LENGTH_SHORT).show();
        }
    }





	/* ********************************************************************************* */
    /* ********************************************************************************* */
    /* ********************************************************************************* */


    /**
     * 1.存款预约
     *
     * @param prodateCK
     */
    private void setDepositOrder(List<FinancialDemandInfo> prodateCK) {
        CommonSetList(prodateCK, INDEX_CKYY);
    }

    /**
     * 2.理财预约
     *
     * @param prodateCK
     */
    private void setLiCaiOrder(List<FinancialDemandInfo> prodateCK) {
        CommonSetList(prodateCK, INDEX_LCYY);
    }

    /**
     * 3.贷款需求
     *
     * @param prodateCK
     */
    private void setLoanDemands(List<FinancialDemandInfo> prodateCK) {
        CommonSetList(prodateCK, INDEX_DKXQ);
    }

    /**
     * 4.电子银行产品需求
     *
     * @param prodateCK
     */
    private void setEBankDemands(List<FinancialDemandInfo> prodateCK) {
        CommonSetList(prodateCK, INDEX_DZYH);
    }

    /**
     * 5.其它需求
     *
     * @param prodateCK
     */
    private void setOtherDemands(List<FinancialDemandInfo> prodateCK) {
        CommonSetList(prodateCK, INDEX_OTHER);
    }

    private void CommonSetList(List<FinancialDemandInfo> prodateCK, int index) {
        if (getActivity() == null) {
            return;
        }

        int layoutID = R.layout.workplacelvtop_financial_demand_1;
        if (index == INDEX_DZYH) {
            layoutID = R.layout.workplacelvtop_financial_demand_2;
        } else if (index == INDEX_OTHER) {
            layoutID = R.layout.workplacelvtop_financial_demand_3;
        }

        //listview顶部的标题栏
        View view = LayoutInflater.from(getActivity()).inflate(layoutID, null);
        LinearLayout llWorkPlaceLvTopItem = (LinearLayout) view.findViewById(R.id.ll_workplace_title);
        if (isManager) {
            View view_fd_column7 = (View) view.findViewById(R.id.view_fd_column7);
            TextView tv_fd_column7 = (TextView) view.findViewById(R.id.tv_fd_column7);
            view_fd_column7.setVisibility(View.VISIBLE);
            tv_fd_column7.setVisibility(View.VISIBLE);
        }
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        llLvTopOut.removeAllViews();
        llLvTopOut.addView(llWorkPlaceLvTopItem, params);

        // 设置数据
        if (prodateCK == null || prodateCK.size() == 0) {
            hiddenLoading(true);
        } else {
            hiddenLoading(false);
            if (index == INDEX_CKYY) {
                depositListAdapter.setInfolist(prodateCK, layoutID, INDEX_CKYY);
                depositListAdapter.notifyDataSetChanged();
            } else if (index == INDEX_LCYY) {
                liCaiListAdapter.setInfolist(prodateCK, layoutID, INDEX_LCYY);
                liCaiListAdapter.notifyDataSetChanged();
            } else if (index == INDEX_DKXQ) {
                loanListAdapter.setInfolist(prodateCK, layoutID, INDEX_DKXQ);
                loanListAdapter.notifyDataSetChanged();
            } else if (index == INDEX_DZYH) {
                eBankListdapter.setInfolist(prodateCK, layoutID, INDEX_DZYH);
                eBankListdapter.notifyDataSetChanged();
            } else if (index == INDEX_OTHER) {
                otherDemandListAdapter.setInfolist(prodateCK, layoutID, INDEX_OTHER);
                otherDemandListAdapter.notifyDataSetChanged();
            }
        }
    }

    /* ********************************************************************************* */
    /* ********************************************************************************* */
    /* ********************************************************************************* */


    private static class HolderView {
        private TextView lvItemInfo1;
        private TextView lvItemInfo2;
        private TextView lvItemInfo3;
        private TextView lvItemInfo4;
        private TextView lvItemInfo5;
        private TextView lvItemInfo6;
        private View view_fd_column7;
        private TextView lvItemInfo7;
    }

    public class FDAdapter extends BaseAdapter {
        private List<FinancialDemandInfo> infolist;
        private int layoutID = R.layout.workplacelvtop_financial_demand_1;
        private int index;

        public void setInfolist(List<FinancialDemandInfo> infolist, int layoutID, int index) {
            this.infolist = infolist;
            this.layoutID = layoutID;
            this.index = index;
        }

        @Override
        public int getCount() {
            if (infolist != null) {
                return infolist.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            if (infolist != null && infolist.size() > 0) {
                return infolist.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HolderView holder;
            if (convertView == null) {
                holder = new HolderView();
                convertView = View.inflate(getActivity(), layoutID, null);
                holder.lvItemInfo1 = (TextView) convertView.findViewById(R.id.tv_fd_column1);
                holder.lvItemInfo2 = (TextView) convertView.findViewById(R.id.tv_fd_column2);
                holder.lvItemInfo3 = (TextView) convertView.findViewById(R.id.tv_fd_column3);
                holder.lvItemInfo4 = (TextView) convertView.findViewById(R.id.tv_fd_column4);
                holder.lvItemInfo5 = (TextView) convertView.findViewById(R.id.tv_fd_column5);
                holder.lvItemInfo6 = (TextView) convertView.findViewById(R.id.tv_fd_column6);
                if (isManager) {
                    holder.view_fd_column7 = (View) convertView.findViewById(R.id.view_fd_column7);
                    holder.lvItemInfo7 = (TextView) convertView.findViewById(R.id.tv_fd_column7);
                    holder.view_fd_column7.setVisibility(View.VISIBLE);
                    holder.lvItemInfo7.setVisibility(View.VISIBLE);
                }
                convertView.setTag(holder);
            } else {
                holder = (HolderView) convertView.getTag();
            }
            if (position % 2 != 0) {
                convertView.setBackgroundColor(getResources().getColor(R.color.lvtopbg));
            } else {
                convertView.setBackgroundColor(getResources().getColor(R.color.white));
            }

            // 将获取到的数据进行展示
            FinancialDemandInfo dqck = infolist.get(position);
            //对私对公没有"处理中",处理为"己受理"
                if (dqck.getSTATUS().equals("02")) {
                    dqck.setSTATUS("03");
                }
            if (dqck != null) {
                holder.lvItemInfo1.setText(dqck.getCUSTNAME());
                holder.lvItemInfo2.setText(TextUtils.isEmpty(dqck.getCHLTYP()) ? "" : NewCatevalue.getLabel(getActivity(), NewCatevalue.CHLTYPE, dqck.getCHLTYP()));// 渠道
                holder.lvItemInfo3.setText(dqck.getCUSTHPONE());// 手机号码
                holder.lvItemInfo4.setText(dqck.getBESPEAKDATE());// 预约日期

                if (index == INDEX_DZYH) {
                    String demdtyp = "", subDemdtyp = "";
                    if (!TextUtils.isEmpty(dqck.getSUBDEMDTYP()))
                        subDemdtyp = NewCatevalue.getLabel(getActivity(), NewCatevalue.SUBDEMDTYP, dqck.getSUBDEMDTYP());//需求类型(子类)

                    if (!TextUtils.isEmpty(subDemdtyp)) {
                        holder.lvItemInfo5.setText(subDemdtyp);
                    } else {
                        if (!TextUtils.isEmpty(dqck.getDEMDTYP()))
                            demdtyp = NewCatevalue.getLabel(getActivity(), NewCatevalue.DEMDTYP, dqck.getDEMDTYP());
                        holder.lvItemInfo5.setText(TextUtils.isEmpty(demdtyp) ? "" : demdtyp);
                    }
                } else if (index == INDEX_OTHER) {
                    holder.lvItemInfo5.setText(dqck.getREMARKS());
                } else {
                    holder.lvItemInfo5.setText(Tool.setFormatValue(dqck.getAMOUNT()));
                }

                holder.lvItemInfo6.setText(TextUtils.isEmpty(dqck.getSTATUS()) ? "" : NewCatevalue.getLabel(getActivity(), NewCatevalue.STATUS, dqck.getSTATUS()));
                if (isManager) {
                    holder.lvItemInfo7.setText(dqck.getUSERNAME());
                }
            }
            return convertView;
        }
    }


	/* ********************************************************************************* */
    /* ********************************************************************************* */
    /* ********************************************************************************* */


    /**
     * 设置背景颜色
     *
     * @param tv
     */
    private void setBgAndTextColor(TextView tv) {
        tvCK.setEnabled(true);
        tvLC.setEnabled(true);
        tvDK.setEnabled(true);
        tvEbank.setEnabled(true);
//        tvOther.setEnabled(true);
        tvCK.setTextColor(getResources().getColor(R.color.separatelightredline));
        tvLC.setTextColor(getResources().getColor(R.color.separatelightredline));
        tvDK.setTextColor(getResources().getColor(R.color.separatelightredline));
        tvEbank.setTextColor(getResources().getColor(R.color.separatelightredline));
//        tvOther.setTextColor(getResources().getColor(R.color.separatelightredline));
        // 选中项
        tv.setEnabled(false);
        tv.setTextColor(getResources().getColor(R.color.white));
    }


}
