package com.pactera.financialmanager.credit.main.warn;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dysen.common_res.common.utils.CallAndSMS;
import com.dysen.common_res.common.utils.FormatUtil;
import com.dysen.common_res.common.views.ViewUtils;
import com.dysen.pullloadmore_recyclerview.PullLoadMoreRecyclerView;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.adapter.SpinnerAdapter;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.fragment.FragmentFinancialDemand;
import com.pactera.financialmanager.ui.hallfirst.CMMarketingActivity2;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.model.FinancialDemandInfo;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.DialogAlert;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.LogUtils;
import com.pactera.financialmanager.util.OnItemClickCallback;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenQ on 16/6/17.
 */
public class FinancialLoanActivity extends ParentActivity {

    private final int INDEX_SEACH_USER = 1;//搜索客户经理
    private final int INDEX_OPINION = 2;//普通岗位提交处理意见
    private final int INDEX_UPLOAD_USERID = 3;//管理岗选择客户经理处理后提交

    @Bind(R.id.txt_back)
    TextView txtBack;
    @Bind(R.id.lay_back)
    LinearLayout layBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_)
    TextView txt;
    @Bind(R.id.tv_fd_handleman)
    TextView tvFdHandleman;
    @Bind(R.id.etxt_fd_submit)
    EditText etxtFdSubmit;
    @Bind(R.id.btn_fd_submit)
    Button btnFdSubmit;
    @Bind(R.id.data_recyclerView)
    PullLoadMoreRecyclerView dataRecyclerView;
    @Bind(R.id.btn_fd_search)
    Button btnFdSearch;
    @Bind(R.id.deal_with)
    LinearLayout dealWith;

    private boolean isManager = false;//是否是管理岗
    private int upload_username_length = 0;//如果输入框字数不变，则认为用户去提交
    private String uploadUserID = null;

    // 接口请求
    private HConnection HCon;

    private boolean isSubmitSuccess = false;
    private static List<String> mListDataValue;
    private static List<String> mListDataName;
    private int CurPage = 1;
    private List<FinancialDemandInfo> listData;
    private String pkId;
    private List<UserInfo> resultUserList;
    private static boolean allocates;
    private static boolean isManagers;
    private static String CST_IDs;
    private static String brCodes;
    private static String SUBTYPE;

    public static void setData(List<String> listDataName, List<String> listDataValue) {
        mListDataValue = listDataValue;
        mListDataName = listDataName;
    }

    public static void setAllocate(boolean allocate) {
        allocates = allocate;
    }

    public static void setIsManager(boolean isManager) {
        isManagers = isManager;
    }

    public static void setSUBTYPE(String SUBTYPEs) {
        SUBTYPE = SUBTYPEs;
    }

    public static void setCST_ID(String CST_ID) {
        CST_IDs = CST_ID;
    }

    public static void setBrCode(String brCode) {
        brCodes = brCode;
    }


    @OnClick({R.id.btn_fd_search, R.id.btn_fd_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_fd_search:
                if (!TextUtils.isEmpty(etxtFdSubmit.getText().toString())) {
                    sendRequestForPerson(etxtFdSubmit.getText().toString());
                } else
                    sendRequestForPerson("");
                break;
            case R.id.btn_fd_submit:
                if (isManager) {
                    if (!TextUtils.isEmpty(uploadUserID)) {
                        uploadUserID(uploadUserID);//选择客户经理进行处理
                    } else {
                        sendRequestForPerson(etxtFdSubmit.getText().toString());//防止用户自己输入错误，未取到spinnerId，随即调用搜索客户经理的方法
                    }
                } else {
                    if (!TextUtils.isEmpty(etxtFdSubmit.getText().toString()))
                        sendRequestForOpinion(etxtFdSubmit.getText().toString());//提交处理意见
                }
                break;
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        List<String> nameList;
        List<String> valueList;
        Context mContext;
        // 申明一个点击事件接口变量
        private OnItemClickCallback callback = null;

        public MyAdapter(List<String> nameList, List<String> valueList, Context mContext, OnItemClickCallback<Integer> onItemClickCallback) {
            this.nameList = nameList;
            this.valueList = valueList;
            this.mContext = mContext;
            callback = onItemClickCallback;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.info_list_item, parent,
                    false);
            //view.setBackgroundColor(Color.RED);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.tvName.setText("\t" + nameList.get(position) + ":");
            holder.tvValue.setText("\t" + valueList.get(position) + "");

            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (nameList.get(position).equals("客户名称")) {
                        callback.onClick(view, position);
                    }
                }
            });
            if (ViewUtils.getText(holder.tvName).equals("\t手机:") || ViewUtils.getText
                    (holder.tvName).equals("\t电话:") || ViewUtils.getText
                    (holder.tvName).equals("\t客户电话:")) {
                boolean numeric = FormatUtil.isNumeric(valueList.get(position).toString());
                if (numeric)
                    if (FormatUtil.isMobileNO(valueList.get(position).toString())) {

                        holder.tvValue.setText(Html.fromHtml("\t<u>" + valueList.get(position) +
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

            if (ViewUtils.getText(holder.tvName).equals("\t预约编号:")) {
                pkId = valueList.get(position).toString();
            }
        }

        @Override
        public int getItemCount() {
            return nameList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.tv_name)
            TextView tvName;
            @Bind(R.id.tv_value)
            TextView tvValue;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_loan);
        ButterKnife.bind(this);

        // 检查是否是管理岗
        checkIsManager();

        //填充数据
        initView();

    }

    private void initView() {

        if (SUBTYPE.equals("wcl")) {
            dealWith.setVisibility(View.VISIBLE);
            tvFdHandleman.setText(isManager ? "处理人:" : "处理意见:");
        }
        etxtFdSubmit.setSelection(etxtFdSubmit.getText().length());
        dataRecyclerView.setGridLayout(2);
        if (!isManager) {
            btnFdSearch.setVisibility(View.INVISIBLE);
        }
        if (layBack != null) {
            layBack.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    finish();
                }
            });
        }
        dataRecyclerView.setAdapter(new MyAdapter(mListDataName, mListDataValue, this, new
                OnItemClickCallback<Integer>() {
                    @Override
                    public void onClick(View view, Integer info) {
//                        http://192.168.2.111:7001/services/T000217?method=getJSON&userCode=UR01030&brCode=BRHBRCU&seriNo=11&chnlCode=02&transCode=T000217&spareOne=EN0000KF&spareTwo=ST000111
                        Bundle bundle = new Bundle();
//
//
                        if (CST_IDs.indexOf("PN") != -1) { //等于-1表示这个字符串中没有PN这个字符
                            bundle.putBoolean(CMMarketingActivity2.IS_FORPERSON, true);

                        } else if (CST_IDs.indexOf("EN") != -1) {
                            bundle.putBoolean(CMMarketingActivity2.IS_FORPERSON, false);

                        }
//
//
                        bundle.putString(CMMarketingActivity2.CUSTOMER_ID, CST_IDs);
                        bundle.putBoolean("allocate", allocates);
                        gotoNextActivity(CMMarketingActivity2.class, bundle);

                    }

                    @Override
                    public void onLongClick(View view, Integer info) {

                    }
                }));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isSubmitSuccess) {
            exitOnSuccessSubmit();
        }
    }

    /**
     * 搜索处理人
     */
    public void sendRequestForPerson(String name) {
        if (Tool.haveNet(this)) {
            String requestType = InterfaceInfo.WORKWARN_FD_Person_Get;// 客户金融处理人查询
            String theRequestInfo = "{\"PKID\":\"" + pkId + "\",\"USERNAME\":\"" + name + "\"}";

            HCon = RequestInfo(this, Constants.requestType.JsonData,
                    requestType, theRequestInfo, INDEX_SEACH_USER, brCodes);
        }
    }

    /**
     * 选择处理的客户经理后提交
     */
    public void uploadUserID(String uploadUserID) {
        if (Tool.haveNet(this)) {
            String requestType = InterfaceInfo.WORKWARN_FD_OPINION;//选择处理的客户经理后提交
            String info = "&spareTwo=" + uploadUserID + "&jsonData={PKID:\"" + pkId + "\"}";
            HCon = RequestInfo(this, Constants.requestType.Other, requestType, info,
                    INDEX_UPLOAD_USERID, true);
        }
    }

    /**
     * 提交意见
     */
    public void sendRequestForOpinion(String msg) {
        if (Tool.haveNet(this)) {
            String requestType = InterfaceInfo.WORKWARN_FD_OPINION;
            String theRequestInfo = "{" +
                    "\"PKID\":\"" + pkId + "\"," +
                    "\"OPINION\":\"" + msg +
                    "\"}";
            HCon = RequestInfo(this, Constants.requestType.JsonData, requestType, theRequestInfo, INDEX_OPINION);
        }
    }

    @Override
    public void onConnected(Message msg) {
        super.onConnected(msg);

        HResponse res = null;
        switch (connectionIndex) {
            case HConnection.RESPONSE_ERROR:
                Toast.makeText(this, "请求失败.", Toast.LENGTH_SHORT).show();
                break;

            case INDEX_SEACH_USER:
                res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (res == null || res.dataJsonObject == null) {
                    return;
                }
                readJson(res.dataJsonObject, INDEX_SEACH_USER);
                break;
            case INDEX_OPINION:
                res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (res == null || res.dataJsonObject == null) {
                    return;
                }
                readJson(res.dataJsonObject, INDEX_OPINION);
                break;
            case INDEX_UPLOAD_USERID:
                res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (res == null || res.dataJsonObject == null) {
                    return;
                }
                readJson(res.dataJsonObject, INDEX_UPLOAD_USERID);
                break;
        }
    }


    /**
     * 解析接口数据
     *
     * @param tmpJsonObject
     */
    private void readJson(JSONObject tmpJsonObject, int index) {
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
            if (INDEX_SEACH_USER == index) {
                try {
                    String group = tmpJsonObject.getString("group");
                    LogUtils.d("respons:" + group);
                    resultUserList = JSON.parseArray(group, UserInfo.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!resultUserList.isEmpty() && resultUserList.size() > 0) {
                    showSearchResult();//进入界面后，如果返回的客户经理不为0则直接进行搜索
                } else {
                    DialogAlert dialog = new DialogAlert(this, closeLisenter);
                    dialog.show();
                    dialog.setMsg("提交失败，请检查当前处理人是否正确");
                }
            } else if (INDEX_UPLOAD_USERID == index) {
                isSubmitSuccess = true;
                DialogAlert dialog = new DialogAlert(this, closeLisenter);
                dialog.show();
                dialog.setMsg("已经成功安排客房经理：" + etxtFdSubmit.getText().toString() + "\n处理需求");
            } else if (INDEX_OPINION == index) {
                isSubmitSuccess = true;
                DialogAlert dialog = new DialogAlert(this, closeLisenter);
                dialog.show();
                dialog.setMsg("已成功提交处理意见");
            }
        } else {
            Toast.makeText(this, "请求失败! 错误代码:" + retCode, Toast.LENGTH_SHORT).show();
        }
    }

    //点击金融需求的条目后通过onCreat（）方法初始化就进入搜索经理的SpinnerAdapter
    public void showSearchResult() {
        int size = resultUserList.size();
        String[] resultName = new String[size];
        String[] resultUserID = new String[size];

        for (int i = 0; i < size; i++) {
            resultName[i] = resultUserList.get(i).getUSERNAME();
            resultUserID[i] = resultUserList.get(i).getUSERID();
        }
        SpinnerAdapter.showSelectDialog(FinancialLoanActivity.this,
                resultName, resultUserID, etxtFdSubmit,
                new SpinnerAdapter.CallBackItemClickListener() {
                    @Override
                    public void CallBackItemClick(String userID) {
                        uploadUserID = userID;
                        for (int i = 0, size = resultUserList.size(); i < size; i++) {
                            if (userID.equals(resultUserList.get(i).getUSERID())) {
                                etxtFdSubmit.setText(resultUserList.get(i).getUSERNAME());
                                upload_username_length = etxtFdSubmit.getText().toString().length();
                            }
                        }
                    }
                });
    }

    DialogAlert.DialogOnClickListener closeLisenter = new DialogAlert.DialogOnClickListener() {
        @Override
        public void onClick(View v) {
            exitOnSuccessSubmit();
        }
    };

    private void exitOnSuccessSubmit() {
        FinancialLoanActivity.this.finish();
        if (!isManager)
            FragmentFinancialDemand.isRefresh = true;
    }

    public static class UserInfo {
        private String USERCODE;
        private String USERID;
        private String USERNAME;

        public String getUSERCODE() {
            return USERCODE;
        }

        public void setUSERCODE(String USERCODE) {
            this.USERCODE = USERCODE;
        }

        public String getUSERID() {
            return USERID;
        }

        public void setUSERID(String USERID) {
            this.USERID = USERID;
        }

        public String getUSERNAME() {
            return USERNAME;
        }

        public void setUSERNAME(String USERNAME) {
            this.USERNAME = USERNAME;
        }
    }
}
