package com.pactera.financialmanager.ui.fragmentbookbuilding;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.adapter.SpinnerAdapter;
import com.pactera.financialmanager.callback.WorkPlaceItemChange;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog;
import com.pactera.financialmanager.datetimepicker.TimePickerDialog;
import com.pactera.financialmanager.ui.DeleteDialog;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.SwipeListView;
import com.pactera.financialmanager.ui.bookbuildingfrocompany.ActivityBaseInfo;
import com.pactera.financialmanager.ui.model.DemandCK;
import com.pactera.financialmanager.ui.model.DemandCPZX;
import com.pactera.financialmanager.ui.model.DemandDK;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这是第四步，金融服务信息的fragment
 */
public class FragmentBookBuildingFinancialServieces extends ParentFragment
        implements OnClickListener, AdapterView.OnItemClickListener {

    //    private CheckBox cbProitems[] = new CheckBox[9];
    private Button btnAddCK, btnAddDK;
    private SwipeListView lvCKList;
    private SwipeListView lvDKList;
    private SwipeListView lvDZCPList;
    private Button btnNext;
    private Button btnAddDZCPZX;
    private HConnection HConDemandEBank;
    private HConnection HConDemandCK;
    private HConnection HConDemandDK;
    private final int EBANK_QUERY = 1;//电子银行产品-查询
    //    private final int DEMANHConDemandCKDDZCP_QUERY = 10;
    private final int EBANK_INSERT = 11;
    private final int EBANK_UPDATE = 12;
    private final int EBANK_DELETE = 13;

    private final int DEMANDCK_QUERY = 2;//存款需求-查询
    private final int DEMANDCK_INSERT = 3;//新增
    private final int DEMANDCK_UPDATE = 4;//修改
    private final int DEMANDCK_DELETE = 5;//删除
    private final int DEMANDDK_QUERY = 6;//存款需求-查询
    private final int DEMANDDK_INSERT = 7;//新增
    private final int DEMANDDK_UPDATE = 8;//修改
    private final int DEMANDDK_DELETE = 9;//删除
    private final String EBANK_TYPE_SET = "1";//电子银行产品-修改
    private final String EBANK_TYPE_GET = "2";//获取
    private String EBANK_TYPE_SELECT = EBANK_TYPE_GET;

    // 适配器内容
    private List<DemandCK> demandCKLists = new ArrayList<DemandCK>();
    private List<DemandDK> demandDKLists = new ArrayList<DemandDK>();
    private List<DemandCPZX> demandCPZXList = new ArrayList<DemandCPZX>();
    private DemandDZCPAdapter dzcpAdapter = null;
    private DemandCKAdapter ckAdapter = null;
    private DemandDKAdapter dkAdapter = null;
    private DialogAddDemandCK dialogAddCK = null;
    private DialogAddDemandDK dialogAddDK = null;
    private DialogAddDemandDZCP dialogAddDZCP = null;

    // 默认选中第0项
    private int selectIndex = 0;
    private DeleteDialog delDialog = null;
//    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// 时间格式


    private Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                // 电子银行产品
               /*  case EBANK_QUERY:
                    // 请求成功后进入第五步
                   if (EBANK_TYPE_SELECT.equals(EBANK_TYPE_SET)) {
                        if (getActivity() instanceof WorkPlaceItemChange) {
                            ((WorkPlaceItemChange) getActivity()).workPlacestyleChange(5);
                        }
                    } else {
                        String products = (String) msg.obj;
                        setCbChecksStatus(products);
                    }
                    break;*/

                // 电子银行产品-查询
                case EBANK_QUERY:
                    demandCPZXList = (ArrayList<DemandCPZX>) msg.obj;
                    refreshDZCPList();
                    break;
                // 【新增|修改】
                case EBANK_INSERT:
                case EBANK_UPDATE:
                    queryFinancialInfos(EBANK_QUERY);
                    dialogAddDZCP.dismiss();
                    break;
                // 删除
                case EBANK_DELETE:
                    demandCPZXList.remove(selectIndex);
                    refreshDZCPList();
                    break;

                // 存款需求-查询
                case DEMANDCK_QUERY:
                    demandCKLists = (ArrayList<DemandCK>) msg.obj;
                    refreshCKList();
                    break;
                // 【新增|修改】
                case DEMANDCK_INSERT:
                case DEMANDCK_UPDATE:
                    queryFinancialInfos(DEMANDCK_QUERY);
                    dialogAddCK.dismiss();
                    break;
                // 删除
                case DEMANDCK_DELETE:
                    demandCKLists.remove(selectIndex);
                    refreshCKList();
                    break;

                /******************************************************************/

                // 贷款需求-查询
                case DEMANDDK_QUERY:
                    demandDKLists = (ArrayList<DemandDK>) msg.obj;
                    refreshDKList();
                    break;
                // 【新增|修改】
                case DEMANDDK_INSERT:
                case DEMANDDK_UPDATE:
                    queryFinancialInfos(DEMANDDK_QUERY);
                    dialogAddDK.dismiss();
                    break;
                // 删除
                case DEMANDDK_DELETE:
                    demandDKLists.remove(selectIndex);
                    refreshDKList();
                    break;
                default:

                    break;
            }

        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_financialservices, null);
        setupView(view);
        setListener();

        // 查询产品
        EBANK_TYPE_SELECT = EBANK_TYPE_GET;
//        eBankRequest("");
        queryFinancialInfos(DEMANDCK_QUERY);
        queryFinancialInfos(DEMANDDK_QUERY);
        queryFinancialInfos(EBANK_QUERY);
        return view;
    }

    private void setupView(View view) {
      /*  进行电子产品中心勾选状态打钩
       cbProitems[0] = (CheckBox) view.findViewById(R.id.pro_item1);
        cbProitems[1] = (CheckBox) view.findViewById(R.id.pro_item2);
        cbProitems[2] = (CheckBox) view.findViewById(R.id.pro_item3);
        cbProitems[3] = (CheckBox) view.findViewById(R.id.pro_item4);
        cbProitems[4] = (CheckBox) view.findViewById(R.id.pro_item5);
        cbProitems[5] = (CheckBox) view.findViewById(R.id.pro_item6);
        cbProitems[6] = (CheckBox) view.findViewById(R.id.pro_item7);
        cbProitems[7] = (CheckBox) view.findViewById(R.id.pro_item8);
        cbProitems[8] = (CheckBox) view.findViewById(R.id.pro_item9);*/
        btnAddDZCPZX = (Button) view.findViewById(R.id.btn_dzcpzx_addCK);
        btnAddCK = (Button) view.findViewById(R.id.btn_addCK);
        btnAddDK = (Button) view.findViewById(R.id.btn_addDK);
        lvDZCPList = (SwipeListView) view.findViewById(R.id.lv_dzcpzx_ebank);
        lvCKList = (SwipeListView) view.findViewById(R.id.lv_ck);
        lvDKList = (SwipeListView) view.findViewById(R.id.lv_dk);
        btnNext = (Button) view.findViewById(R.id.btn_next);
    }

    private void setListener() {
        dzcpAdapter = new DemandDZCPAdapter(getActivity(), lvDZCPList.getRightViewWidth());
        ckAdapter = new DemandCKAdapter(getActivity(), lvCKList.getRightViewWidth());
        dkAdapter = new DemandDKAdapter(getActivity(), lvDKList.getRightViewWidth());
        lvCKList.setAdapter(ckAdapter);
        lvDKList.setAdapter(dkAdapter);
        lvDZCPList.setAdapter(dzcpAdapter);
        btnAddDZCPZX.setOnClickListener(this);
        btnAddCK.setOnClickListener(this);
        btnAddDK.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        lvCKList.setOnItemClickListener(this);
        lvDKList.setOnItemClickListener(this);
        lvDZCPList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                DemandCPZX dzcpDemand = demandCPZXList.get(position);
                if (dzcpDemand != null) {
                    dialogAddDZCP = new DialogAddDemandDZCP(getActivity());
                    dialogAddDZCP.show();
                    dialogAddDZCP.setValue(dzcpDemand);
                    dialogAddDZCP.setEditStatus(false, false);
                    dialogAddDZCP.setIsUpdate(true);
                }
            }
        });
    }

    /**
     * 刷新存款列表
     */
    private void refreshCKList() {
        ckAdapter.setCkLists(demandCKLists);
        ckAdapter.notifyDataSetChanged();
        setLvHeight(lvCKList, demandCKLists.size());
        selectIndex = 0;
    }


    /**
     * 刷新贷款列表
     */
    private void refreshDKList() {
        dkAdapter.setDkLists(demandDKLists);
        dkAdapter.notifyDataSetChanged();
        setLvHeight(lvDKList, demandDKLists.size());
    }

    /**
     * 刷新电子产品中心列表
     */
    private void refreshDZCPList() {
        dzcpAdapter.setDzcpList(demandCPZXList);
        dzcpAdapter.notifyDataSetChanged();
        setLvHeight(lvDZCPList, demandCPZXList.size());
    }

    /**
     * 设置多选框状态
     *
     * @param products
     */
//    private void setCbChecksStatus(String products) {
//        // 空数据直接返回
//        if (TextUtils.isEmpty(products)) {
//            return;
//        }
//
//        int index = 0;
//        // 多个数据
//        String[] eBanks = products.split(",");
//        if (eBanks.length > 1) {
//            for (String p : eBanks) {
//                // 数组下标
//                index = Integer.parseInt(p) - 1;
//                if (index >= 0 && index < 9) {
//                    cbProitems[index].setChecked(true);
//                }
//            }
//        }
//        // 单个数据
//        else {
//            index = Integer.parseInt(products) - 1;
//            if (index >= 0 && index < 9) {
//                cbProitems[index].setChecked(true);
//            }
//        }
//    }


    /*********************************************************************/
    /*********************************************************************/
    /*********************************************************************/

    /**
     * 电子银行产品
     *
     * @param products
     */
//    private void eBankRequest(String products) {
//        if (Tool.haveNet(getActivity())) {
//            String custID = ActivityBaseInfo.custID;
//            String info = "";
//            try {
//                JSONObject jsonObj = new JSONObject();
//                jsonObj.put("type", EBANK_TYPE_SELECT);
//                jsonObj.put("custid", custID);
//                jsonObj.put("USERED_PRODUCT", products);
//                info = jsonObj.toString();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            // 电子银行
//            HConDemandEBank = RequestInfo(this,
//                    Constants.requestType.JsonData, InterfaceInfo.FINANCIAL_EBANK_QUERY, info,
//                    EBANK_QUERY, true);
//
//        }
//    }


    /**
     * 查询信息
     *
     * @param queryType
     */
    private void queryFinancialInfos(int queryType) {
        if (Tool.haveNet(getActivity())) {
            String info = ActivityBaseInfo.custID;
            //电子产品中心
            if (queryType == EBANK_QUERY) {
                HConDemandEBank = RequestInfo(this, Constants.requestType.Search, InterfaceInfo.FINANCIAL_EBANK_QUERY, info, queryType, true);
            }
            // 存款需求
            if (queryType == DEMANDCK_QUERY) {
                HConDemandCK = RequestInfo(this,
                        Constants.requestType.Search, InterfaceInfo.FINANCIAL_DEMANDCK_QUERY, info,
                        queryType, true);
            }

            // 贷款需求
            if (queryType == DEMANDDK_QUERY) {
                HConDemandDK = RequestInfo(this,
                        Constants.requestType.Search, InterfaceInfo.FINANCIAL_DEMANDDK_QUERY, info,
                        queryType, true);
            }
        }
    }

    /**
     * 【新增|修改】记录
     *
     * @param requestType
     * @param obj
     */
    private void insertNewRecord(int requestType, Object obj) {
        if (Tool.haveNet(getActivity())) {
            try {
                String custID = ActivityBaseInfo.custID;
                String info = "";
                // 存款需求
                if (obj instanceof DemandCK) {
                    DemandCK ck = (DemandCK) obj;
                    //用Json工具类将DemandCK这个类转成Json
                    JSONObject jsonObj = new JSONObject();

                    jsonObj.put("PKID", ck.getPKID());
                    jsonObj.put("CUSTID", custID);
                    jsonObj.put("STATUS", ck.getSTATUS());
                    jsonObj.put("OCCUR_TIME", ck.getOCCUR_TIME());
//                    jsonObj.put("REMIND_TIME", ck.getREMIND_TIME());
                    jsonObj.put("DES", ck.getDES());
                    jsonObj.put("DEPOSIT_AMOUNT", ck.getDEPOSIT_AMOUNT());

                    info = jsonObj.toString();
                    // 新增
                    if (requestType == DEMANDCK_INSERT) {
                        HConDemandCK = RequestInfo(this,
                                Constants.requestType.Insert, InterfaceInfo.FINANCIAL_DEMANDCK_INSERT, info,
                                DEMANDCK_INSERT, true);
                    }
                    // 修改
                    else if (requestType == DEMANDCK_UPDATE) {
                        HConDemandCK = RequestInfo(this,
                                Constants.requestType.Update, InterfaceInfo.FINANCIAL_DEMANDCK_UPDATE, info,
                                DEMANDCK_UPDATE);
                    }
                }
                //电子产品
                if (obj instanceof DemandCPZX) {
                    DemandCPZX dzcp = (DemandCPZX) obj;
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("PKID", dzcp.getPKID());
                    jsonObject.put("CUSTID", custID);
                    jsonObject.put("KIND", dzcp.getKIND());
                    jsonObject.put("OCCUR_TIME", dzcp.getOCCUR_TIME());
                    jsonObject.put("DES", dzcp.getDES());

                    info = jsonObject.toString();
                    //新增
                    if (requestType == EBANK_INSERT) {
                        HConDemandEBank = RequestInfo(this, Constants.requestType.Insert, InterfaceInfo.FINANCIAL_EBANK_INSERT, info, EBANK_INSERT, true);
                    }
                    //修改
                    else if (requestType == EBANK_UPDATE) {
                        HConDemandEBank = RequestInfo(this, Constants.requestType.Update, InterfaceInfo.FINANCIAL_EBANK_UPDATE, info, EBANK_UPDATE);
                    }
                }
                // 贷款需求
                if (obj instanceof DemandDK) {
                    DemandDK dk = (DemandDK) obj;

                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("PKID", dk.getPKID());
                    jsonObj.put("CUSTID", custID);
                    jsonObj.put("KIND", dk.getKIND());
                    jsonObj.put("AMOUNT", dk.getAMOUNT());
                    jsonObj.put("PURPOSE", dk.getPURPOSE());
                    jsonObj.put("WARRANT", dk.getWARRANT());
                    jsonObj.put("TURN_OVER", dk.getTURN_OVER());
                    jsonObj.put("STATUS", dk.getSTATUS());
                    jsonObj.put("OCCUR_TIME", dk.getOCCUR_TIME());
                    jsonObj.put("DES", dk.getDES());
//                    jsonObj.put("REMIND_TIME", dk.getREMIND_TIME());
                    jsonObj.put("BUSI_CATE", dk.getBUSI_CATE());


                    info = jsonObj.toString();
                    // 新增
                    if (requestType == DEMANDDK_INSERT) {
                        HConDemandDK = RequestInfo(this,
                                Constants.requestType.Insert, InterfaceInfo.FINANCIAL_DEMANDDK_INSERT, info,
                                DEMANDDK_INSERT, true);
                    }
                    // 修改
                    else if (requestType == DEMANDDK_UPDATE) {
                        HConDemandDK = RequestInfo(this,
                                Constants.requestType.Update, InterfaceInfo.FINANCIAL_DEMANDDK_UPDATE, info,
                                DEMANDDK_UPDATE);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), "抱歉，请检查您的网络！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 删除选择项
     *
     * @param obj
     * @param position
     */
    private void delItemLine(Object obj, int position) {
        if (Tool.haveNet(getActivity())) {
            String pkID = "";
            selectIndex = position;
            //电子银行
            if (obj instanceof DemandCPZX) {
                pkID = ((DemandCPZX) obj).getPKID();
                HConDemandEBank = RequestInfo(this, Constants.requestType.Delete, InterfaceInfo.FINANCIAL_EBANK_DELETE, pkID, EBANK_DELETE, true);
            }
            // 存款需求
            if (obj instanceof DemandCK) {
                pkID = ((DemandCK) obj).getPKID();
                HConDemandCK = RequestInfo(this,
                        Constants.requestType.Delete, InterfaceInfo.FINANCIAL_DEMANDCK_DELETE, pkID,
                        DEMANDCK_DELETE, true);
            }
            // 贷款需求
            if (obj instanceof DemandDK) {
                pkID = ((DemandDK) obj).getPKID();
                HConDemandDK = RequestInfo(this,
                        Constants.requestType.Delete, InterfaceInfo.FINANCIAL_DEMANDDK_DELETE, pkID,
                        DEMANDDK_DELETE, true);
            }
        }
    }


    @Override
    public void onConnected(Message msg) {
        super.onConnected(msg);
        switch (connectionIndex) {
            case HConnection.RESPONSE_ERROR://500-
                Toast.makeText(getActivity(), "获取信息失败", Toast.LENGTH_SHORT).show();
                break;
            // 电子银行产品
            case EBANK_QUERY:
            case EBANK_INSERT:
            case EBANK_UPDATE:
            case EBANK_DELETE:
                HResponse resEBank = HConDemandEBank.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (resEBank == null || resEBank.dataJsonObject == null) {
                    return;
                }
                readJson(resEBank.dataJsonObject, connectionIndex);
                break;

            // 存款需求
            case DEMANDCK_QUERY:
            case DEMANDCK_INSERT:
            case DEMANDCK_UPDATE:
            case DEMANDCK_DELETE:
                HResponse resCK = HConDemandCK.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (resCK == null || resCK.dataJsonObject == null) {
                    return;
                }
                readJson(resCK.dataJsonObject, connectionIndex);
                break;

            // 贷款需求
            case DEMANDDK_QUERY:
            case DEMANDDK_INSERT:
            case DEMANDDK_UPDATE:
            case DEMANDDK_DELETE:
                HResponse resDK = HConDemandDK.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (resDK == null || resDK.dataJsonObject == null) {
                    return;
                }
                readJson(resDK.dataJsonObject, connectionIndex);
                break;
        }
    }

    /**
     * 解析结果
     *
     * @param tmpJsonObject
     * @param connectionIndex
     */
    private void readJson(JSONObject tmpJsonObject, int connectionIndex) {
        String retCode = "";
        try {
            if (tmpJsonObject.has("retCode")) { // 返回标志
                retCode = tmpJsonObject.getString("retCode");
            }
            // 获取接口成功
            if (retCode.equals("0000")) {
                Message msg = new Message();
                msg.arg1 = connectionIndex;
                // 电子银行产品
                if (connectionIndex == EBANK_QUERY) {
                   /* if (tmpJsonObject.has("USERED_PRODUCT")) {
                        msg.obj = tmpJsonObject.getString("USERED_PRODUCT");
                    }*/
                    String group = tmpJsonObject.getString("group");
                    List<DemandCPZX> mCKList = JSON.parseArray(group, DemandCPZX.class);
                    msg.obj = mCKList;
                }
                // 存款需求-查询
                if (connectionIndex == DEMANDCK_QUERY) {
                    String group = tmpJsonObject.getString("group");
                    List<DemandCK> mCKList = JSON.parseArray(group, DemandCK.class);
                    msg.obj = mCKList;
                }
                // 贷款需求-查询
                if (connectionIndex == DEMANDDK_QUERY) {
                    String group = tmpJsonObject.getString("group");
                    List<DemandDK> mDKList = JSON.parseArray(group, DemandDK.class);
                    msg.obj = mDKList;
                }
                handler.sendMessage(msg);
            } else {
                Toast.makeText(getActivity(), "操作失败! 错误:" + retCode, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
            return;
        }
    }


    /*********************************************************************/
    /*********************************************************************/
    /*********************************************************************/

    class HolderView {
        private LinearLayout item_left;
        private RelativeLayout item_right;
        private TextView lvItemInfo1;
        private TextView lvItemInfo2;
        private TextView lvItemInfo3;
        private TextView lvItemInfo4;
        private TextView lvItemInfo5;
        private TextView lvItemInfo6;

    }

    /**
     * 存款需求适配器
     */
    class DemandCKAdapter extends BaseAdapter {

        private Context context = null;
        private List<DemandCK> ckLists = null;
        private int mRightWidth = 0;

        public DemandCKAdapter(Context context, int rightWidth) {
            this.context = context;
            this.mRightWidth = rightWidth;
        }

        @Override
        public int getCount() {
            if (ckLists != null) {
                return ckLists.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (ckLists != null && ckLists.size() > 0) {
                return ckLists.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            HolderView holder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_ckxq, null);
                holder = new HolderView();
                holder.item_left = (LinearLayout) convertView.findViewById(R.id.item_left);
                holder.item_right = (RelativeLayout) convertView.findViewById(R.id.item_right);
                holder.lvItemInfo1 = (TextView) convertView.findViewById(R.id.lvitem_info1);
                holder.lvItemInfo2 = (TextView) convertView.findViewById(R.id.lvitem_info2);
                holder.lvItemInfo3 = (TextView) convertView.findViewById(R.id.lvitem_info3);
                holder.lvItemInfo4 = (TextView) convertView.findViewById(R.id.lvitem_info4);
                holder.lvItemInfo5 = (TextView) convertView.findViewById(R.id.lvitem_info5);
                convertView.setTag(holder);
            } else {
                holder = (HolderView) convertView.getTag();
            }

            if (position % 2 != 0) {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.lvtopbg));
            } else {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
            }

            DemandCK demandCK = ckLists.get(position);
            if (demandCK != null) {
                holder.lvItemInfo1.setText(demandCK.getDEPOSIT_AMOUNT() + "元"); //金额
                holder.lvItemInfo2.setText(demandCK.getDES());    //内容
                holder.lvItemInfo3.setText(demandCK.getOCCUR_TIME());    //预约时间
//                holder.lvItemInfo4.setText(demandCK.getREMIND_TIME());    //提醒时间

                String type = demandCK.getSTATUS().trim();
                String typeValue = NewCatevalue.getLabel(getActivity(), NewCatevalue.fin_status, type);
                holder.lvItemInfo5.setText(typeValue);    //状态
            }

            // 侧滑
            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(mRightWidth, LinearLayout.LayoutParams.MATCH_PARENT);
            holder.item_left.setLayoutParams(lp1);
            holder.item_right.setLayoutParams(lp2);
            holder.item_right.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    DemandCK item = ckLists.get(position);
                    delListviewItem(item, position);
                }
            });

            return convertView;
        }

        public void setCkLists(List<DemandCK> ckLists) {
            this.ckLists = ckLists;
        }
    }

    /**
     * 电子产品中心适配器
     */
    class DemandDZCPAdapter extends BaseAdapter {
        private Context context = null;
        private int mRightWidth = 0;
        private List<DemandCPZX> dzcpList = null;

        public DemandDZCPAdapter(Context context, int rightWidth) {
            this.context = context;
            this.mRightWidth = rightWidth;
        }

        @Override
        public int getCount() {
            if (dzcpList != null) {
                return dzcpList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (dzcpList != null && dzcpList.size() > 0) {
                dzcpList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            HolderView holder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_dzcpzx, null);
                holder = new HolderView();
                holder.item_left = (LinearLayout) convertView.findViewById(R.id.item_left);
                holder.lvItemInfo1 = (TextView) convertView.findViewById(R.id.lvitem_info1);
                holder.lvItemInfo2 = (TextView) convertView.findViewById(R.id.lvitem_info2);
                holder.lvItemInfo3 = (TextView) convertView.findViewById(R.id.lvitem_info3);
                holder.item_right = (RelativeLayout) convertView.findViewById(R.id.item_right);
                convertView.setTag(holder);
            } else {
                holder = (HolderView) convertView.getTag();
            }
            if (position % 2 != 0) {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.lvtopbg));
            } else {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
            }
            DemandCPZX demandCPZX = dzcpList.get(position);
            if (demandCPZX != null) {
                String typeValue = NewCatevalue.getLabel(getActivity(), NewCatevalue.kindTypeEn, demandCPZX.getKIND());
                holder.lvItemInfo1.setText(typeValue);//种类
                holder.lvItemInfo2.setText(demandCPZX.getOCCUR_TIME());//发生时间
                holder.lvItemInfo3.setText(demandCPZX.getDES());//描述内容

            }
            // 侧滑
            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(mRightWidth, LinearLayout.LayoutParams.MATCH_PARENT);
            holder.item_left.setLayoutParams(lp1);
            holder.item_right.setLayoutParams(lp2);
            holder.item_right.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    DemandCPZX itemDK = dzcpList.get(position);
                    delListviewItem(itemDK, position);
                }
            });

            return convertView;
        }

        public void setDzcpList(List<DemandCPZX> dzcpList) {
            this.dzcpList = dzcpList;
        }


    }

    /**
     * 贷款需求适配器
     */
    class DemandDKAdapter extends BaseAdapter {

        private Context context = null;
        private List<DemandDK> dkLists = null;
        private int mRightWidth = 0;

        public DemandDKAdapter(Context context, int rightWidth) {
            this.context = context;
            this.mRightWidth = rightWidth;
        }

        @Override
        public int getCount() {
            if (dkLists != null) {
                return dkLists.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (dkLists != null && dkLists.size() > 0) {
                return dkLists.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            HolderView holder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_dkxq, null);
                holder = new HolderView();
                holder.item_left = (LinearLayout) convertView.findViewById(R.id.item_left);
                holder.item_right = (RelativeLayout) convertView.findViewById(R.id.item_right);
                holder.lvItemInfo1 = (TextView) convertView.findViewById(R.id.lvitem_info1);
                holder.lvItemInfo2 = (TextView) convertView.findViewById(R.id.lvitem_info2);
                holder.lvItemInfo3 = (TextView) convertView.findViewById(R.id.lvitem_info3);
                holder.lvItemInfo4 = (TextView) convertView.findViewById(R.id.lvitem_info4);
                holder.lvItemInfo5 = (TextView) convertView.findViewById(R.id.lvitem_info5);
                holder.lvItemInfo6 = (TextView) convertView.findViewById(R.id.lvitem_info7);
//                holder.lvItemInfo7 = (TextView) convertView.findViewById(R.id.lvitem_info7);
                convertView.setTag(holder);
            } else {
                holder = (HolderView) convertView.getTag();
            }

            if (position % 2 != 0) {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.lvtopbg));
            } else {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
            }

            DemandDK demandDK = dkLists.get(position);
            if (demandDK != null) {
                holder.lvItemInfo2.setText(demandDK.getAMOUNT() + "元");//金额
                holder.lvItemInfo3.setText(demandDK.getOCCUR_TIME());//预约时间
                holder.lvItemInfo5.setText(demandDK.getTURN_OVER());//周转期限
//                holder.lvItemInfo6.setText(demandDK.getREMIND_TIME());//提醒时间

                String type = demandDK.getKIND().trim();
                String typeValue = NewCatevalue.getLabel(getActivity(), NewCatevalue.loanType, type);
                holder.lvItemInfo1.setText(typeValue);    //贷款方式

                type = demandDK.getSTATUS().trim();
                typeValue = NewCatevalue.getLabel(getActivity(), NewCatevalue.fin_status, type);
                holder.lvItemInfo6.setText(typeValue);    //状态

                type = demandDK.getPURPOSE().trim();
                typeValue = NewCatevalue.getLabel(getActivity(), NewCatevalue.loanUseType, type);
                holder.lvItemInfo4.setText(typeValue);    //用途
            }

            // 侧滑
            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(mRightWidth, LinearLayout.LayoutParams.MATCH_PARENT);
            holder.item_left.setLayoutParams(lp1);
            holder.item_right.setLayoutParams(lp2);
            holder.item_right.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    DemandDK itemDK = dkLists.get(position);
                    delListviewItem(itemDK, position);
                }
            });

            return convertView;
        }

        public void setDkLists(List<DemandDK> dkLists) {
            this.dkLists = dkLists;
        }
    }

    /**
     * 动态计算listview高度
     *
     * @param lv
     */
    public void setLvHeight(ListView lv, int num) {
        ListAdapter mAdapter = lv.getAdapter();
        if (mAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < num; i++) {
            totalHeight += Tool.dip2px(getActivity(), 40);
        }
        // 如果count数量为0，
        if (num == 0) {
            totalHeight = Tool.dip2px(getActivity(), 40);
        }
        ViewGroup.LayoutParams params = lv.getLayoutParams();
        params.height = totalHeight + (lv.getDividerHeight() * (mAdapter.getCount() - 1));
        lv.setLayoutParams(params);
        lv.requestLayout();
    }

    /**
     * 删除单项内容
     *
     * @param obj
     * @param position
     */
    private void delListviewItem(final Object obj, final int position) {
        if (obj == null) {
            return;
        }

        delDialog = new DeleteDialog(getActivity(), new DeleteDialog.IsDeleteListener() {
            @Override
            public void onClickDeleteListener(boolean isDelete) {
                if (!isDelete) {
                    return;
                }

                lvCKList.hiddenShowView();
                // 存款需求
                delItemLine(obj, position);
            }
        });
        delDialog.show();
    }

    /*********************************************************************/
    /*********************************************************************/
    /*********************************************************************/


    /**
     * 创建存款需求
     */
    class DialogAddDemandCK extends Dialog implements OnClickListener {
        private Button btnEdit;//编辑按钮
        private EditText etBal;//金额
        private EditText etStatus;//状态
        private TextView tvAppointmentTime;//预约时间
        private EditText etContent;//内容
        //        private TextView tvAlertTime;//提醒日期
        private TextView tvCreater;//创建者
        private TextView tvCreateTime;//创建时间
        private Button btnSave;
        private Button btnClose;
        // 临时变量
        private boolean isUpdate = false;
        private DemandCK tempCK = new DemandCK();

        private String strStatusCK;
        private TextView tvTitle;

        private boolean isCreateNew = false;

        public static final int DEFAULT_STYLE = R.style.DialogTheme;
        private Context context;

        public DialogAddDemandCK(Context context) {
            this(context, DEFAULT_STYLE);
        }

        public DialogAddDemandCK(Context context, int theme) {
            super(context, theme);
            this.context = context;
            this.setCanceledOnTouchOutside(false);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_demandck);

            findView();
            bindOnClickListener();
        }

        private void findView() {
            tvTitle = (TextView) findViewById(R.id.tv_title);
            tvTitle.setText("创建存款需求");
            btnEdit = (Button) findViewById(R.id.btn_edit);
            etBal = (EditText) findViewById(R.id.et_bal);
            etStatus = (EditText) findViewById(R.id.et_status);
            tvAppointmentTime = (TextView) findViewById(R.id.tv_appointment_date);
            etContent = (EditText) findViewById(R.id.et_content);
//            tvAlertTime = (TextView) findViewById(R.id.tv_alert_time);
            tvCreater = (TextView) findViewById(R.id.tv_creater);
            tvCreateTime = (TextView) findViewById(R.id.tv_create_time);
            btnSave = (Button) findViewById(R.id.btn_save);
            btnClose = (Button) findViewById(R.id.btn_close);

            // 新增默认显示当前用户
            tvCreater.setText(LogoActivity.user.getUsername());
        }

        private void bindOnClickListener() {
            tvAppointmentTime.setOnClickListener(this);
//            tvAlertTime.setOnClickListener(this);
            btnEdit.setOnClickListener(this);
            btnSave.setOnClickListener(this);
            btnClose.setOnClickListener(this);
            etStatus.setOnClickListener(this);
        }

        /**
         * 设置界面值
         *
         * @param ckDemand
         */
        public void setValue(DemandCK ckDemand) {
            tvTitle.setText("查看存款需求");
            tempCK = ckDemand;
            btnEdit.setVisibility(View.VISIBLE);
            // 创建人
            String creater = ckDemand.getRECORD_USER();
            if (TextUtils.isEmpty(creater)) {
                creater = LogoActivity.user.getUsername();
            }
            etBal.setText(ckDemand.getDEPOSIT_AMOUNT());
            String strStatusCK = ckDemand.getSTATUS().trim();
            String typeValue = NewCatevalue.getLabel(getActivity(), NewCatevalue.fin_status, strStatusCK);
            etStatus.setText(typeValue);    //状态

            tvAppointmentTime.setText(ckDemand.getOCCUR_TIME());
            etContent.setText(ckDemand.getDES());
//            tvAlertTime.setText(ckDemand.getREMIND_TIME());
            tvCreater.setText(creater);
            tvCreateTime.setText(ckDemand.getRECORD_TIME());
        }

        /**
         * 设置是否修改值
         *
         * @param isUpdate
         */
        private void setIsUpdate(boolean isUpdate) {
            this.isUpdate = isUpdate;
        }

        /**
         * 设置编辑状态
         *
         * @param isEdit
         */
        public void setEditStatus(boolean isEdit, boolean isCreateNew) {
            this.isCreateNew = isCreateNew;
            etBal.setEnabled(isEdit);
            etStatus.setEnabled(isEdit);
            tvAppointmentTime.setEnabled(isEdit);
            etContent.setEnabled(isEdit);
//            tvAlertTime.setEnabled(isEdit);
            tvCreater.setEnabled(isEdit);
            tvCreateTime.setEnabled(isEdit);
            if (isEdit) {
                btnEdit.setText("取消");
                tvTitle.setText("编辑存款需求");
            } else {
                btnEdit.setText("编辑");
                tvTitle.setText("查看存款需求");
            }
            if (isCreateNew) {
                etStatus.setText("未处理");
                etStatus.setEnabled(false);
                tvTitle.setText("创建存款需求");
            }
        }

        /**
         * 保存输入框内容
         */
        private void saveTempValue() {
            String content = etContent.getText().toString().trim();
            String recoderUser = tvCreater.getText().toString();
            // 文字转码
            try {
                content = Tool.getChineseEncode(content);
                recoderUser = Tool.getChineseEncode(recoderUser);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            tempCK.setDEPOSIT_AMOUNT(etBal.getText().toString().trim());
            tempCK.setSTATUS((null == strStatusCK) ? etStatus.getText().toString().trim() : strStatusCK);
            if (isCreateNew) {
                tempCK.setSTATUS("01");
            }
            tempCK.setOCCUR_TIME(tvAppointmentTime.getText().toString());
            tempCK.setDES(content);
//            tempCK.setREMIND_TIME(tvAlertTime.getText().toString());
            tempCK.setRECORD_TIME(tvCreateTime.getText().toString());
            tempCK.setRECORD_USER(recoderUser);

        }

        /**
         * 判断输入框内容
         *
         * @param tempCK
         * @return
         */
        private boolean checkInputValue(DemandCK tempCK) {
            String bal = tempCK.getDEPOSIT_AMOUNT();
            if (TextUtils.isEmpty(bal)) {
                Toast.makeText(context, "抱歉，金额不能为空！", Toast.LENGTH_SHORT).show();
                return false;
            }
//            if (!TextUtils.isEmpty(tempCK.getOCCUR_TIME()) && !TextUtils.isEmpty(tempCK.getREMIND_TIME())) {
//                try {
//                    Date d2 = formatter.parse(tempCK.getOCCUR_TIME());
//                    Date d1 = formatter.parse(tempCK.getREMIND_TIME());
//                    if (!Tool.daysBetween(d1,d2)) {
//                        Toast.makeText(getActivity(), "提醒时间要小于等于预约时间!", Toast.LENGTH_SHORT).show();
//                        return false;
//                    }else{
//                        return true;
//                    }
//                } catch (ParseException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
            return true;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                // 编辑
                case R.id.btn_edit:
                    if (btnEdit.getText().equals("编辑")) {
                        setEditStatus(true, false);
                    } else {
                        setEditStatus(false, false);
                    }
                    break;

                // 预约时间
                case R.id.tv_appointment_date:
                    setDatetimePickerDialog(tvAppointmentTime, false);
                    break;

//                // 提醒时间
//                case R.id.tv_alert_time:
//                    setDatetimePickerDialog(tvAlertTime, false);
//                    break;

                // 状态
                case R.id.et_status:
                    SpinnerAdapter.showSelectDialog(getActivity(),
                            NewCatevalue.fin_status, etStatus,
                            new SpinnerAdapter.CallBackItemClickListener() {
                                @Override
                                public void CallBackItemClick(String spinnerID) {
                                    strStatusCK = spinnerID;
                                }
                            });
                    break;

                // 保存
                case R.id.btn_save:
                    // 按钮为编辑，则取消修改
                    if (btnEdit.getText().equals("编辑")) {
                        dialogAddCK.dismiss();
                    } else {
                        saveTempValue();
                        if (checkInputValue(tempCK)) {
                            int requestType = DEMANDCK_INSERT;// 新增标签
                            if (isUpdate) {
                                requestType = DEMANDCK_UPDATE;// 修改
                            }
                            insertNewRecord(requestType, tempCK);
                        }
                    }
                    break;

                // 关闭
                case R.id.btn_close:
                    if (dialogAddCK != null) {
                        dialogAddCK.dismiss();
                    }
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * 创建电子产品中心需求
     */
    class DialogAddDemandDZCP extends Dialog implements OnClickListener {
        private Context context;
        public static final int DEFAULT_STYLE = R.style.DialogTheme;
        private TextView tvTitle;
        private Button btnEdit;
        private EditText etCatogry;
        private TextView tvChooseTime;
        private EditText etDescripsms;
        private Button btnSave;
        private Button btnClose;
        private String strCatogryDZCP;
        private Boolean isUpdate = false;
        private boolean isCreateNew = false;
        private DemandCPZX temDZCP = new DemandCPZX();

        public DialogAddDemandDZCP(Context context) {
            this(context, DEFAULT_STYLE);
        }

        public DialogAddDemandDZCP(Context context, int theme) {
            super(context, theme);
            this.context = context;
            this.setCanceledOnTouchOutside(false);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_demanddzcpzx);
            findView();
            bindOnClickListener();
        }

        private void findView() {
            tvTitle = (TextView) findViewById(R.id.tv_title);
            btnEdit = (Button) findViewById(R.id.btn_edit);
            etCatogry = (EditText) findViewById(R.id.et_catogry);
            tvChooseTime = (TextView) findViewById(R.id.tv_choosetime);
            etDescripsms = (EditText) findViewById(R.id.et_descripsms);
            btnSave = (Button) findViewById(R.id.btn_save);
            btnClose = (Button) findViewById(R.id.btn_close);
        }

        private void bindOnClickListener() {
            btnSave.setOnClickListener(this);
            btnClose.setOnClickListener(this);
            etCatogry.setOnClickListener(this);
            tvChooseTime.setOnClickListener(this);
            btnEdit.setOnClickListener(this);
        }

        private void setIsUpdate(boolean isUpdate) {
            this.isUpdate = isUpdate;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_edit:
                    if (btnEdit.getText().equals("编辑")) {
                        setEditStatus(true, false);
                    } else {
                        setEditStatus(false, false);
                    }
                    break;
                case R.id.et_catogry:
                    SpinnerAdapter.showSelectDialog(getActivity(),
                            NewCatevalue.kindTypeEn, etCatogry,
                            new SpinnerAdapter.CallBackItemClickListener() {
                                @Override
                                public void CallBackItemClick(String spinnerID) {
                                    strCatogryDZCP = spinnerID;
                                }
                            });
                    break;
                case R.id.tv_choosetime:
                    setDatetimePickerDialog(tvChooseTime, false);

                    break;
                case R.id.btn_save:
                    // 按钮为编辑，则取消修改
                    if (btnEdit.getText().equals("编辑")) {
                        dialogAddDK.dismiss();
                    } else {
                        saveTempValue();//保存内容
                        if (checkInputValue(temDZCP)) {
                            int requestType = EBANK_INSERT;// 新增标签
                            if (isUpdate) {
                                requestType = EBANK_UPDATE;// 修改
                            }
                            insertNewRecord(requestType, temDZCP);
                        }
                    }
                    break;
                case R.id.btn_close:
                    if (dialogAddDZCP != null) {
                        dialogAddDZCP.dismiss();
                    }
                    break;
                default:
                    break;
            }
        }

        private void setEditStatus(boolean isEdit, boolean isCreateNew) {

            this.isCreateNew = isCreateNew;
            etCatogry.setEnabled(isEdit);
            tvChooseTime.setEnabled(isEdit);
            etDescripsms.setEnabled(isEdit);

            if (isEdit) {
                btnEdit.setText("取消");
                tvTitle.setText("编辑电子产品");
            } else {
                btnEdit.setText("编辑");
                tvTitle.setText("查看电子产品");
            }
            if (isCreateNew) {
                tvTitle.setText("创建电子产品");
            }
        }

        /**
         * 界面赋值
         */
        public void setValue(DemandCPZX dzcpDemand) {
            tvTitle.setText("查看电子产品");
            temDZCP = dzcpDemand;
            btnEdit.setVisibility(View.VISIBLE);
           /* // 创建人
            String creater = dzcpDemand.getRECORD_USER();
            if (TextUtils.isEmpty(creater)) {
                creater = LogoActivity.user.getUsername();
            }*/
            String strCatogery = dzcpDemand.getKIND().trim();
            String typeValue = NewCatevalue.getLabel(getActivity(), NewCatevalue.kindTypeEn, strCatogery);
            etCatogry.setText(typeValue);
            tvChooseTime.setText(dzcpDemand.getOCCUR_TIME());
            etDescripsms.setText(dzcpDemand.getDES());


        }

        /**
         * 保存输入框的内容
         */
        private void saveTempValue() {
            Boolean isCreateNew = false;


            temDZCP.setKIND((null == strCatogryDZCP) ? etCatogry.getText().toString().trim() : strCatogryDZCP);

            if (isCreateNew) {
                temDZCP.setKIND("0");
            }
            temDZCP.setOCCUR_TIME(tvChooseTime.getText().toString().trim());
            temDZCP.setDES(etDescripsms.getText().toString().trim());


        }

        /**
         * 判断输入框内容
         */
        private boolean checkInputValue(DemandCPZX temDZCP) {
            String time = temDZCP.getOCCUR_TIME();
            if (TextUtils.isEmpty(time)) {
                Toast.makeText(context, "抱歉，时间不能为空！", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;

        }


    }


    /**
     * 创建贷款需求
     */
    class DialogAddDemandDK extends Dialog implements OnClickListener {

        private Button btnEdit;
        private EditText etKind;
        private EditText etAmount;
        private EditText etStatus;
        private TextView tvOccurTime;
        private EditText etPurpose;
        private EditText etTurnover;
        //        private TextView tvRemindTime;
        private TextView tvRecordUser;
        private TextView tvRecordTime;
        private Button btnSave;
        private Button btnClose;
        // 是否为修改
        private boolean isUpdate = false;
        private DemandDK tempDK = new DemandDK();

        private TextView tvTitle;


        public static final int DEFAULT_STYLE = R.style.DialogTheme;
        private Context context;

        private String strStatusDK, strLoanKindDK, strPurposeDK;

        private boolean isCreateNew = false;


        public DialogAddDemandDK(Context context) {
            this(context, DEFAULT_STYLE);
        }

        public DialogAddDemandDK(Context context, int theme) {
            super(context, theme);
            this.context = context;
            this.setCanceledOnTouchOutside(false);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_demanddk);

            findView();
            bindOnClickListener();
        }

        private void findView() {
            tvTitle = (TextView) findViewById(R.id.tv_title);
            tvTitle.setText("创建贷款需求");
            btnEdit = (Button) findViewById(R.id.btn_edit);
            etKind = (EditText) findViewById(R.id.et_kind);
            etAmount = (EditText) findViewById(R.id.et_amount);
            etStatus = (EditText) findViewById(R.id.et_status);
            tvOccurTime = (TextView) findViewById(R.id.tv_occurtime);
            etPurpose = (EditText) findViewById(R.id.et_purpose);
            etTurnover = (EditText) findViewById(R.id.et_turnover);
//            tvRemindTime = (TextView) findViewById(R.id.tv_remindtime);
            tvRecordUser = (TextView) findViewById(R.id.tv_recorduser);
            tvRecordTime = (TextView) findViewById(R.id.tv_recordtime);
            btnSave = (Button) findViewById(R.id.btn_save);
            btnClose = (Button) findViewById(R.id.btn_close);

            // 新增默认显示当前用户
            tvRecordUser.setText(LogoActivity.user.getUsername());

        }

        private void bindOnClickListener() {
            btnEdit.setOnClickListener(this);
            tvOccurTime.setOnClickListener(this);
//            tvRemindTime.setOnClickListener(this);
            btnSave.setOnClickListener(this);
            btnClose.setOnClickListener(this);
            etStatus.setOnClickListener(this);
            etKind.setOnClickListener(this);
            etPurpose.setOnClickListener(this);
        }

        private void setIsUpdate(boolean isUpdate) {
            this.isUpdate = isUpdate;
        }

        /**
         * 编辑状态
         *
         * @param isEdit
         */
        private void setEditStatus(boolean isEdit, boolean isCreateNew) {

            this.isCreateNew = isCreateNew;
            etStatus.setEnabled(isEdit);
            etKind.setEnabled(isEdit);
            etAmount.setEnabled(isEdit);
            tvOccurTime.setEnabled(isEdit);
            etPurpose.setEnabled(isEdit);
            etTurnover.setEnabled(isEdit);
//            tvRemindTime.setEnabled(isEdit);
            tvRecordUser.setEnabled(isEdit);
            tvRecordTime.setEnabled(isEdit);

            if (isEdit) {
                btnEdit.setText("取消");
                tvTitle.setText("编辑贷款需求");
            } else {
                btnEdit.setText("编辑");
                tvTitle.setText("查看贷款需求");
            }

            if (isCreateNew) {
                etStatus.setText("未处理");
                etStatus.setEnabled(false);
                tvTitle.setText("创建贷款需求");
            }
        }

        /**
         * 页面赋值
         *
         * @param dkDemand
         */
        public void setValue(DemandDK dkDemand) {
            tvTitle.setText("查看贷款需求");

            tempDK = dkDemand;
            btnEdit.setVisibility(View.VISIBLE);
            // 创建人
            String creater = dkDemand.getRECORD_USER();
            if (TextUtils.isEmpty(creater)) {
                creater = LogoActivity.user.getUsername();
            }
            etAmount.setText(dkDemand.getAMOUNT());
            strLoanKindDK = dkDemand.getKIND().trim();
            String typeValue = NewCatevalue.getLabel(getActivity(), NewCatevalue.loanType, strLoanKindDK);
            etKind.setText(typeValue);    //贷款方式
            strStatusDK = dkDemand.getSTATUS().trim();
            typeValue = NewCatevalue.getLabel(getActivity(), NewCatevalue.fin_status, strStatusDK);
            etStatus.setText(typeValue);    //状态
            strPurposeDK = dkDemand.getPURPOSE().trim();
            typeValue = NewCatevalue.getLabel(getActivity(), NewCatevalue.loanUseType, strPurposeDK);
            etPurpose.setText(typeValue);    //用途

            tvOccurTime.setText(dkDemand.getOCCUR_TIME());
            etTurnover.setText(dkDemand.getTURN_OVER());
//            tvRemindTime.setText(dkDemand.getREMIND_TIME());
            tvRecordUser.setText(creater);
            tvRecordTime.setText(dkDemand.getRECORD_TIME());
        }


        /**
         * 保存输入框内容
         */
        private void saveTempValue() {
            String recoderUser = tvRecordUser.getText().toString();//记录人
            // 文字转码
            try {
                recoderUser = Tool.getChineseEncode(recoderUser);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            tempDK.setPURPOSE((null == strPurposeDK) ? etPurpose.getText().toString().trim() : strPurposeDK);
            tempDK.setKIND((null == strLoanKindDK) ? etKind.getText().toString().trim() : strLoanKindDK);
            tempDK.setSTATUS((null == strStatusDK) ? etStatus.getText().toString().trim() : strStatusDK);
            if (isCreateNew) {
                tempDK.setSTATUS("01");
            }
            tempDK.setAMOUNT(etAmount.getText().toString().trim());
            tempDK.setOCCUR_TIME(tvOccurTime.getText().toString());
            tempDK.setTURN_OVER(etTurnover.getText().toString());
//            tempDK.setREMIND_TIME(tvRemindTime.getText().toString());
            tempDK.setRECORD_USER(recoderUser);
            tempDK.setRECORD_TIME(tvRecordTime.getText().toString());
        }

        /**
         * 判断输入框内容
         *
         * @param tempDK
         * @return
         */
        private boolean checkInputValue(DemandDK tempDK) {
            String amount = tempDK.getAMOUNT();
            if (TextUtils.isEmpty(amount)) {
                Toast.makeText(context, "抱歉，金额不能为空！", Toast.LENGTH_SHORT).show();
                return false;
            }
//            if (!TextUtils.isEmpty(tempDK.getOCCUR_TIME()) && !TextUtils.isEmpty(tempDK.getREMIND_TIME())) {
//                try {
//                    Date d2 = formatter.parse(tempDK.getOCCUR_TIME());
//                    Date d1 = formatter.parse(tempDK.getREMIND_TIME());
//                    if (!Tool.daysBetween(d1,d2)) {
//                        Toast.makeText(getActivity(), "提醒时间要小于等于预约时间!", Toast.LENGTH_SHORT).show();
//                        return false;
//                    }else{
//                        return true;
//                    }
//                } catch (ParseException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
            return true;
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                // 编辑按钮
                case R.id.btn_edit:
                    if (btnEdit.getText().equals("编辑")) {
                        setEditStatus(true, false);
                    } else {
                        setEditStatus(false, false);
                    }
                    break;

                // 预约时间
                case R.id.tv_occurtime:
                    setDatetimePickerDialog(tvOccurTime, false);
                    break;
//                // 提醒时间
//                case R.id.tv_remindtime:
//                    setDatetimePickerDialog(tvRemindTime, false);
//                    break;
                // 贷款方式
                case R.id.et_kind:
                    SpinnerAdapter.showSelectDialog(getActivity(),
                            NewCatevalue.loanType, etKind,
                            new SpinnerAdapter.CallBackItemClickListener() {
                                @Override
                                public void CallBackItemClick(String spinnerID) {
                                    strLoanKindDK = spinnerID;
                                }
                            });
                    break;
                // 状态
                case R.id.et_status:
                    SpinnerAdapter.showSelectDialog(getActivity(),
                            NewCatevalue.fin_status, etStatus,
                            new SpinnerAdapter.CallBackItemClickListener() {
                                @Override
                                public void CallBackItemClick(String spinnerID) {
                                    strStatusDK = spinnerID;
                                }
                            });
                    break;
                // 用途
                case R.id.et_purpose:
                    SpinnerAdapter.showSelectDialog(getActivity(),
                            NewCatevalue.loanUseType, etPurpose,
                            new SpinnerAdapter.CallBackItemClickListener() {
                                @Override
                                public void CallBackItemClick(String spinnerID) {
                                    strPurposeDK = spinnerID;
                                }
                            });
                    break;

                // 保存
                case R.id.btn_save:
                    // 按钮为编辑，则取消修改
                    if (btnEdit.getText().equals("编辑")) {
                        dialogAddDK.dismiss();
                    } else {

                        saveTempValue();

                        if (checkInputValue(tempDK)) {
                            int requestType = DEMANDDK_INSERT;// 新增标签
                            if (isUpdate) {
                                requestType = DEMANDDK_UPDATE;// 修改
                            }
                            insertNewRecord(requestType, tempDK);
                        }
                    }
                    break;

                // 关闭
                case R.id.btn_close:
                    if (dialogAddDK != null) {
                        dialogAddDK.dismiss();
                    }
                    break;

                default:
                    break;
            }
        }

    }

    /**
     * 设置时间
     *
     * @param view
     */
    private void setDatetimePickerDialog(final TextView view, final boolean isHasTime) {
        DatePickerShow(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(String year, String month, String day) {
                String dateStr = year + "-" + month + "-" + day;
                if (isHasTime) {
                    setTimePickerDialog(view, dateStr);
                } else {
                    view.setText(dateStr);
                }
            }
        });
    }

    private void setTimePickerDialog(final TextView view, final String dateStr) {
        TimePickerShow(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(int hourOfDay, int minute) {
                String date = dateStr + " " + hourOfDay + ":" + minute + ":00";
                view.setText(date);
            }
        });
    }


    /*********************************************************************/
    /*********************************************************************/
    /*********************************************************************/


    /**
     * 取多选框中的值
     *
     * @return
     */
//    private String getCheckboxValue() {
//        // 选择的选项
//        Boolean cbChecks[] = new Boolean[]{false, false, false, false, false, false, false, false, false};
//        cbChecks[0] = cbProitems[0].isChecked();
//        cbChecks[1] = cbProitems[1].isChecked();
//        cbChecks[2] = cbProitems[2].isChecked();
//        cbChecks[3] = cbProitems[3].isChecked();
//        cbChecks[4] = cbProitems[4].isChecked();
//        cbChecks[5] = cbProitems[5].isChecked();
//        cbChecks[6] = cbProitems[6].isChecked();
//        cbChecks[7] = cbProitems[7].isChecked();
//        cbChecks[8] = cbProitems[8].isChecked();
//
//        String checkedProduts = "";
//        for (int i = 1; i <= cbChecks.length; i++) {
//            boolean isChecked = cbChecks[i - 1];
//            if (isChecked) {
//                checkedProduts += i + ",";
//            }
//        }
//
//        // 去掉最后一个逗号分隔符（,）
//        if (checkedProduts.length() > 1) {
//            checkedProduts = checkedProduts.substring(0, checkedProduts.length() - 1);
//        }
//        return checkedProduts;
//    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object itemObj = parent.getItemAtPosition(position);
        //电子产品中心
//        if (itemObj instanceof DemandCPZX){
//            DemandCPZX dzcpDemand = (DemandCPZX) itemObj;
//            if (dzcpDemand != null) {
//                dialogAddDZCP = new DialogAddDemandDZCP(getActivity());
//                dialogAddDZCP.show();
//                dialogAddDZCP.setValue(dzcpDemand);
//                dialogAddDZCP.setEditStatus(false, false);
//                dialogAddDZCP.setIsUpdate(true);
//            }
//        }
        // 存款需求
        if (itemObj instanceof DemandCK) {
            DemandCK ckDemand = (DemandCK) itemObj;
            if (ckDemand != null) {
                dialogAddCK = new DialogAddDemandCK(getActivity());
                dialogAddCK.show();
                dialogAddCK.setValue(ckDemand);
                dialogAddCK.setEditStatus(true, false);
                dialogAddCK.setIsUpdate(true);
            }
        }
        // 贷款需求
        if (itemObj instanceof DemandDK) {
            DemandDK dkDemand = (DemandDK) itemObj;
            if (dkDemand != null) {
                dialogAddDK = new DialogAddDemandDK(getActivity());
                dialogAddDK.show();
                dialogAddDK.setValue(dkDemand);
                dialogAddDK.setEditStatus(true, false);
                dialogAddDK.setIsUpdate(true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //电子产品中心
            case R.id.btn_dzcpzx_addCK:
                dialogAddDZCP = new DialogAddDemandDZCP(getActivity());
                dialogAddDZCP.show();
                dialogAddDZCP.setEditStatus(true, true);
                dialogAddDZCP.setIsUpdate(false);//dialog里得有这个方法
                break;
            // 存款需求
            case R.id.btn_addCK:
                dialogAddCK = new DialogAddDemandCK(getActivity());
                dialogAddCK.show();
                dialogAddCK.setEditStatus(true, true);
                dialogAddCK.setIsUpdate(false);
                break;

            // 贷款需求
            case R.id.btn_addDK:
                dialogAddDK = new DialogAddDemandDK(getActivity());
                dialogAddDK.show();
                dialogAddDK.setEditStatus(true, true);
                dialogAddDK.setIsUpdate(false);
                break;

            // 下一步
            case R.id.btn_next:
                EBANK_TYPE_SELECT = EBANK_TYPE_SET;
                // 提交
               /* String products = getCheckboxValue();
                eBankRequest(products);*/
                if (getActivity() instanceof WorkPlaceItemChange) {
                    ((WorkPlaceItemChange) getActivity()).workPlacestyleChange(5);
                }


                break;

            default:
                break;
        }
    }
}
