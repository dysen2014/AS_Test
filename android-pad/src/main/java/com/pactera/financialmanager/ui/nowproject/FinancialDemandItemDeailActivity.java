package com.pactera.financialmanager.ui.nowproject;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.adapter.SpinnerAdapter;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.fragment.FragmentFinancialDemand;
import com.pactera.financialmanager.ui.model.FinancialDemandInfo;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.DialogAlert;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by chenQ on 16/6/17.
 */
public class FinancialDemandItemDeailActivity extends ParentActivity implements View.OnClickListener {

    private final int INDEX_SEACH_USER = 1;//搜索客户经理
    private final int INDEX_OPINION = 2;//普通岗位提交处理意见
    private final int INDEX_UPLOAD_USERID = 3;//管理岗选择客户经理处理后提交

    private TextView
            tv_fd_handleman,//处理人,或者处理意见
            tv_cust_name,//客户姓名
            tv_chl_type,//渠道
            tv_cust_phone,//客户联系电话
            tv_cert_num,//证件号码
            tv_demand_type,//需求类型
            tv_fd_column7_amount,//预约金额框名
            tv_date_amount,//预约金额
            tv_date,//预约日期
            tv_status,//处理状态
            tv_suggetion,//处理意见
            tv_real_amount,//实际发生金额
            tv_real_date,//实际发生日期
            tv_progress,//预约完成情况
            tv_des;//备注

    private EditText et_input_msg;
    private Button btnSubmit;
    private FinancialDemandInfo item = null;
    private boolean isManager = false;//是否是管理岗
    private int upload_username_length = 0;//如果输入框字数不变，则认为用户去提交
    private String uploadUserID = null;
    private List<UserInfo> resultUserList = null;

    // 接口请求
    private HConnection HCon;

    private boolean isSubmitSuccess = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_financial_demand_item_detail);
        initTitle(this, "工作平台");


        // 检查是否是管理岗
        String staID = LogoActivity.user.getStaId().toUpperCase();
        if (staID.equals("ST000111") || staID.equals("ST000301")
                || staID.equals("ST000701") || staID.equals("ST000901")
                || staID.equals("ST001101") || staID.equals("ST000501")) {

            isManager = true;
        }

        try {
            if (getIntent() != null && getIntent().getExtras().containsKey(FinancialDemandInfo.TAG))
                item = (FinancialDemandInfo) getIntent().getSerializableExtra(FinancialDemandInfo.TAG);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null != item) {
            // 初始化
            findViews();
            // 绑定监听器
            bindOnClickListener();
            //填充数据
            setViews();
        }

    }

    private void findViews() {
        et_input_msg = (EditText) findViewById(R.id.etxt_fd_submit);
        tv_fd_handleman = (TextView) findViewById(R.id.tv_fd_handleman);
        tv_cust_name = (TextView) findViewById(R.id.tv_fd_item_1);
        tv_chl_type = (TextView) findViewById(R.id.tv_fd_item_2);
        tv_cust_phone = (TextView) findViewById(R.id.tv_fd_item_3);
        tv_cert_num = (TextView) findViewById(R.id.tv_fd_item_4);
        tv_demand_type = (TextView) findViewById(R.id.tv_fd_item_5);
//        tv_fd_column6_type = (TextView) findViewById(R.id.tv_fd_item_6_column);
//        tv_demand_type_2 = (TextView) findViewById(R.id.tv_fd_item_6);
        tv_fd_column7_amount = (TextView) findViewById(R.id.tv_fd_column7_amount);
        tv_date_amount = (TextView) findViewById(R.id.tv_fd_item_7);
        tv_date = (TextView) findViewById(R.id.tv_fd_item_8);
        tv_status = (TextView) findViewById(R.id.tv_fd_item_9);
        tv_suggetion = (TextView) findViewById(R.id.tv_fd_item_10);
        tv_real_amount = (TextView) findViewById(R.id.tv_fd_item_11);
        tv_real_date = (TextView) findViewById(R.id.tv_fd_item_12);
        tv_progress = (TextView) findViewById(R.id.tv_fd_item_13);
        tv_des = (TextView) findViewById(R.id.tv_fd_item_15);
        btnSubmit = (Button) findViewById(R.id.btn_fd_submit);

    }

    private void setViews() {
        tv_fd_handleman.setText(isManager ? "处理人:" : "处理意见:");
        tv_cust_name.setText(item.getCUSTNAME());
        tv_chl_type.setText(TextUtils.isEmpty(item.getCHLTYP()) ? "" : NewCatevalue.getLabel(this, NewCatevalue.CHLTYPE, item.getCHLTYP()));
        tv_cust_phone.setText(item.getCUSTHPONE());
        tv_cert_num.setText(item.getCERTID());
        tv_date_amount.setText(Tool.setFormatValue(item.getAMOUNT()));
        tv_date.setText(item.getBESPEAKDATE());
        tv_status.setText(TextUtils.isEmpty(item.getSTATUS()) ? "" : NewCatevalue.getLabel(this, NewCatevalue.STATUS, item.getSTATUS()));
        tv_suggetion.setText(item.getOPINION());
        tv_real_amount.setText(Tool.setFormatValue(item.getFINAL_AMT()));
        tv_real_date.setText(item.getFINAL_DATE());
        tv_des.setText(item.getREMARKS());

        if (item.getCPL_STATUS().equals("00")) {
            tv_progress.setText("否");
        } else if (item.getCPL_STATUS().equals("01")) {
            tv_progress.setText("是");
        } else {
            if (FragmentFinancialDemand.isDZYHClicked)
                tv_progress.setText(TextUtils.isEmpty(item.getCPL_STATUS()) ? "否" : "");
            else
                tv_progress.setText(TextUtils.isEmpty(item.getCPL_STATUS()) ? "" : item.getCPL_STATUS() + "%");
        }


        if ("02".equals(item.getSTATUS())) {
            item.setSTATUS("03");
        }

        if ("03".equals(item.getSTATUS())) {
            et_input_msg.setEnabled(false);
            btnSubmit.setText("更改");
        }

        if (FragmentFinancialDemand.isDZYHClicked) {
            tv_fd_column7_amount.setText("");
            tv_date_amount.setText("");
            tv_demand_type.setText(TextUtils.isEmpty(item.getSUBDEMDTYP()) ? "" : NewCatevalue.getLabel(this, NewCatevalue.SUBDEMDTYP, item.getSUBDEMDTYP()));
        } else {
            tv_demand_type.setText(TextUtils.isEmpty(item.getDEMDTYP()) ? "" : NewCatevalue.getLabel(this, NewCatevalue.DEMDTYP, item.getDEMDTYP()));
        }
    }

    private void bindOnClickListener() {
        btnSubmit.setOnClickListener(this);
        if (isManager) {
//            et_input_msg.addTextChangedListener(new DelayedTextWatcher(2000) {
//                @Override
//                public void afterTextChangedDelayed(Editable s) {
//                    if (!TextUtils.isEmpty(s.toString()) && s.toString().length() != upload_username_length)
                        sendRequestForPerson(et_input_msg.getText().toString());
//                }
//            });
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fd_submit:
                if (isManager) {
                    if (!TextUtils.isEmpty(uploadUserID)) {
                        uploadUserID(uploadUserID);//选择客户经理进行处理
                    }else {
                        sendRequestForPerson(et_input_msg.getText().toString());//防止用户自己输入错误，未取到spinnerId，随即调用搜索客户经理的方法
                    }

                } else {
                    if ("03".equals(item.getSTATUS())) {
                        et_input_msg.setEnabled(true);
                        btnSubmit.setText("提交");
                    }
                    if (!TextUtils.isEmpty(et_input_msg.getText().toString()))
                        sendRequestForOpinion(et_input_msg.getText().toString());//提交处理意见
                }
                break;

            default:
                break;
        }
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
            String theRequestInfo = "{\"PKID\":\"" + item.getPKID() + "\",\"USERNAME\":\"" + name + "\"}";

            HCon = RequestInfo(this, Constants.requestType.JsonData,
                    requestType, theRequestInfo, INDEX_SEACH_USER);
        }
    }

    /**
     * 选择处理的客户经理后提交
     */
    public void uploadUserID(String uploadUserID) {
        if (Tool.haveNet(this)) {
            String requestType = InterfaceInfo.WORKWARN_FD_OPINION;//选择处理的客户经理后提交
            String info = "&spareTwo=" + uploadUserID + "&jsonData={PKID:\"" + item.getPKID() + "\"}";
            HCon = RequestInfo(this, Constants.requestType.Other, requestType, info, INDEX_UPLOAD_USERID, false);
        }
    }

    /**
     * 提交意见
     */
    public void sendRequestForOpinion(String msg) {
        if (Tool.haveNet(this)) {
            String requestType = InterfaceInfo.WORKWARN_FD_OPINION;
            String theRequestInfo = "{" +
                    "\"PKID\":\"" + item.getPKID() + "\"," +
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
                    resultUserList = JSON.parseArray(group, UserInfo.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!resultUserList.isEmpty() && resultUserList.size() > 0) {
                    showSearchResult();//进入界面后，如果返回的客户经理不为0则直接进行搜索
                }
            } else if (INDEX_UPLOAD_USERID == index) {
                isSubmitSuccess = true;
                DialogAlert dialog = new DialogAlert(this, closeLisenter);
                dialog.show();
                dialog.setMsg("已经成功安排客房经理：" + et_input_msg.getText().toString() + "\n处理需求");
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
        SpinnerAdapter.showSelectDialog(FinancialDemandItemDeailActivity.this,
                resultName, resultUserID, et_input_msg,
                new SpinnerAdapter.CallBackItemClickListener() {
                    @Override
                    public void CallBackItemClick(String userID) {
                        uploadUserID = userID;
                        for (int i = 0, size = resultUserList.size(); i < size; i++) {
                            if (userID.equals(resultUserList.get(i).getUSERID())) {
                                et_input_msg.setText(resultUserList.get(i).getUSERNAME());
                                upload_username_length = et_input_msg.getText().toString().length();
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
        FinancialDemandItemDeailActivity.this.finish();
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
