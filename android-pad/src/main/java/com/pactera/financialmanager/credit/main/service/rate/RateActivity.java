package com.pactera.financialmanager.credit.main.service.rate;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dysen.common_res.common.adapter.MyRecycleViewAdapter;
import com.dysen.common_res.common.base.ParentActivity;
import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.common_res.common.utils.Utils;
import com.example.zhouwei.library.CustomPopWindow;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.bean.rate.BussType;
import com.pactera.financialmanager.credit.common.bean.rate.Customer;
import com.pactera.financialmanager.credit.common.bean.rate.Industry;
import com.pactera.financialmanager.credit.main.service.rate.adapter.BussAdapter;
import com.pactera.financialmanager.credit.main.service.rate.adapter.IndustyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.dysen.common_res.common.utils.HttpThread.MEDIA_TYPE_JSON;

public class RateActivity extends ParentActivity {
    private static final String TAG = "RateActivity";
    private static final int COMPLETED = 0;
    @Bind(R.id.tv_custName)
    TextView tvCustName;
    @Bind(R.id.et_custName)
    Button etCustName;
    @Bind(R.id.tv_custType)
    TextView tvCustType;
    @Bind(R.id.et_custType)
    Button etCustType;
    @Bind(R.id.tv_certNo)
    TextView tvCertNo;
    @Bind(R.id.et_certNo)
    EditText etCertNo;
    @Bind(R.id.spinner1)
    Spinner spinner1;
    @Bind(R.id.spinner2)
    Spinner spinner2;
    @Bind(R.id.spinner3)
    Spinner spinner3;
    @Bind(R.id.spinner4)
    Spinner spinner4;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    @Bind(R.id.ll_custName)
    LinearLayout llCustName;
    @Bind(R.id.ll_custType)
    LinearLayout llCustType;
    
    String errorMsg = null;
    private ArrayAdapter<CharSequence> adapteEdu = null;
    private List<CharSequence> dataEdu = null;
    private List<BussType> bussType = new ArrayList<>();
    private List<Customer> customer = new ArrayList<>();
    private JSONObject rateTol = null;      //测试结果
    private String bussTypeVal = null;      //业务品种的值
    private String sortNoVal = null;      //业务品种的值
    private String industryItemno = null;      //投向行业的值
    private String industryType = null;      //投向行业
    private JSONArray professionCanal = null;      //业务渠道
    private JSONArray irscreditlevel = null;      //信用等级
    private JSONArray vouchType = null;      //贷款方式
    private JSONArray manageInfo = null;      //账户类型
    private JSONArray businessCond = null;      //与我行往来情况
    private JSONArray creditCond = null;      //信用情况
    private JSONArray billNum = null;      //购房套数
    private JSONArray wagesCond = null;      //工资账户在我行情况
    private String businessSum = null;      //贷款额度
    private String fundSubside = null;      //近三个月日均存贷比
    private String firstPaySum = null;      //首付比例
    private JSONArray flag1301 = null;      //开通网上银行
    private JSONArray flag1302 = null;      //开通卡乐付
    private JSONArray flag1303 = null;      //开通手机银行
    private JSONArray flag1304 = null;      //开通短信银行
    private JSONArray flag1305 = null;      //开通微信银行
    private JSONArray flag1401 = null;      //借款人个人工资代发
    private JSONArray flag1402 = null;      //借款单位开通员工工资代发
    private JSONArray flag1403 = null;      //农商行员工
    private JSONArray flag1404 = null;      //承贷农商行股东
    private JSONArray project = null;      //合作商
    private JSONArray guarantee = null;      //担保公司
    private JSONArray iTermMonth = null;      //期数

    //定义动态id
    @IdRes
    int TAG2 = 2, TAG3 = 3, TAG4 = 4, TAG5 = 5, TAG6 = 6, TAG7 = 7, TAG8 = 8, TAG9 = 9, TAG10 = 10, TAG11 = 11, TAG12 = 12, TAG1301 = 1301,
            TAG1302 = 1302, TAG1303 = 1303, TAG1304 = 1304, TAG1305 = 1305, TAG1401 = 1401, TAG1402 = 1402, TAG1403 = 1403, TAG1404 = 1404, TAG15 = 15, TAG16 = 16;

    private String sortNo;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //业务品种
            if (msg.what == COMPLETED) {
                reqBussType();
            }
            //加载利率测算项
            if (msg.what == 1) {
                //设置头
                //rateEstimate();
                //设置内容
                rateContent();
            }

            //利率测算结果
            if (msg.what == 2) {
                //测算结果
                dialog();
            }
            //清空利率测算项
            if (msg.what == 3) {
                //设置头
                //rateEstimate();
                //设置内容
                removeRate();
            }

            //加载客户
            if (msg.what == 5) {

                if (customer != null && customer.size() > 0) {

                    ContactCustomerActivity.setData(customer);
                    startActivityForResult(new Intent(RateActivity.this, ContactCustomerActivity.class), 99);
                }
            }

            //提示
            if (msg.what == 9) {
                Log.d(TAG, "errorMsg: " + errorMsg);
                AlertDialog.Builder builder = new AlertDialog.Builder(RateActivity.this);
                builder.setMessage(errorMsg);
                builder.setTitle("提示");
                builder.setPositiveButton("确认", null);
                builder.create().show();
            }
        }
    };
    private List<String> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        ButterKnife.bind(this);
        Random random = new Random();
        int mColor = 0xff000000 | random.nextInt(0xffffff);
//        StatusBarUtil.setColorDiff(this, mColor);
        txtTitle.setText("选定客户");

        //获取业务品种信息
        reqBussBody(null);
    }

    /**
     * 请求业务品种接口
     *
     * @param
     */
    void reqBussBody(BussType bussType) {

        if (bussType == null)
            sortNo = "";
        else
            sortNo = bussType.getSortNo();
        JSONObject jsonObject = ParamUtils.setParams("search", "businessType", new Object[]{ParamUtils.UserId, sortNo, "C"}, 3);
        if (bussType == null) {
            sendRequestWithOkHttp(ParamUtils.url, jsonObject, "buss", "");
        }else {
            sendRequestWithOkHttp(ParamUtils.url, jsonObject, "buss", bussType.getTypeNo());
        }
    }

    /**
     * 获取利率测算项
     *
     * @param sortNo
     */
    void reqRateBody(String sortNo) {

        JSONObject jsonObject = ParamUtils.setParams("rate", "crmInterest", new Object[]{ParamUtils.UserId, ParamUtils.orgId, "", "", sortNo, "010"}, 66);//66为了和6区分

        if (sortNo != null && sortNo.length() > 0) {
            bussTypeVal = sortNo;
        }
        Log.d(TAG, "json:" + "" + ParamUtils.url + "\n" + jsonObject.toString());
        sendRequestWithOkHttp(ParamUtils.url, jsonObject, "rate", sortNo);
    }

    /**
     * 获取利率测算结果
     *
     * @param interesArray
     */
    void reqRateTol(String interesArray) {
        if (bussTypeVal == null && bussTypeVal.length() > 0) {
            bussTypeVal = sortNoVal;
        }
        JSONObject jsonObject = ParamUtils.setParams("rate", "crmInterest", new Object[]{ParamUtils.UserId, ParamUtils.orgId, "", ""
                , bussTypeVal, "020", interesArray}, 7);
        sendRequestWithOkHttp(ParamUtils.url, jsonObject, "rateTol", "");
    }

    /**
     * 选定客户
     */
    void reqCustBody() {
        //利率测算(选择客户)		UserId:登陆用户id,CustomerName:客户名称，CustomerType:客户类型, CertID:证件号码，CurPage:页码, PageSize:每页条数
        JSONObject jsonObject = ParamUtils.setParams("rate", "crmCustomerList", new Object[]{ParamUtils.UserId, "", "", "", curPage, ParamUtils.pageSize}, 6);

        sendRequestWithOkHttp(ParamUtils.url, jsonObject, "cust", "");
    }

    /**
     * post请求方式
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public void sendRequestWithOkHttp(final String url, final JSONObject json, final String type, final String sortNo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json.toString());
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url).post(body).build();
                    LogUtils.d("request: " + request + "" + json);
                    okhttp3.Response response = client.newCall(request).execute();
                    //Log.d(TAG, "body: "+response.body());
                    String responseData = response.body().string();
                    LogUtils.d("responseData: " + responseData);
                    if (sortNo != null && sortNo.length() > 0) {
                        bussTypeVal = sortNo;
                    }
                    if ("buss".equals(type)) {        //业务品种
                        parseJSONWithGson(responseData, sortNo);
                    } else if ("cust".equals(type)) {      //客户信息
                        parseJSONWithGsonCust(responseData);
                    } else if ("rate".equals(type)) {      //测算项信息
                        parseJSONWithGsonRate(responseData);
                    } else if ("rateTol".equals(type)) {       //测算结果
                        parseJSONWithGsonTol(responseData);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 解析业务品种json数据
     *
     * @param jsonData
     * @throws JSONException
     */
    private void parseJSONWithGson(String jsonData, String sortNo) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        if (jsonObject.getJSONObject("ResponseParams").toString().length() == 2) {
            //如果有利率测算项，清空后加载
            LinearLayout layout = (LinearLayout) findViewById(R.id.estimate);
            Log.d(TAG, "getChildCount: " + layout.getChildCount());
            int count = layout.getChildCount();
            if (count > 0) {
                Message msg = new Message();
                msg.what = 3;
                handler.sendMessage(msg);
            }
            //获取业务品种
            sortNoVal = sortNo;
            //加载利率测算项
            if (sortNo != null && sortNo.length() > 0) {
                bussTypeVal = sortNo;
            }
            reqRateBody(sortNo);
            return;
        }

        String jsonArray = jsonObject.getJSONObject("ResponseParams").getJSONArray("array").toString();
        Log.d(TAG, "jsonObject: " + jsonArray);
        Gson gson = new Gson();
        bussType = gson.fromJson(jsonArray, new TypeToken<List<BussType>>() {
        }.getType());
        Message msg = new Message();
        msg.what = COMPLETED;
        handler.sendMessage(msg);
    }

    /**
     * 解析利率测算项json数据
     *
     * @param jsonData
     * @throws JSONException
     */
    private void parseJSONWithGsonRate(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        //返回值为空，返回
        if (jsonObject.getJSONObject("ResponseParams").toString().length() == 2) {
            return;
        }
        errorMsg = jsonObject.getJSONObject("ResponseParams").getString("ErrorMsg");
        if (errorMsg != null && errorMsg.length() > 0) {
            Message msg = new Message();
            msg.what = 9;
            handler.sendMessage(msg);
            return;
        }
        JSONArray jsonArray = jsonObject.getJSONObject("ResponseParams").getJSONArray("array");
        Log.d(TAG, "jsonArray: " + jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonRate = jsonArray.getJSONObject(i);
            String id = jsonRate.getString("ID");
            if ("001".equals(id)) {
                industryType = "001";
            }
            if ("002".equals(id)) {
                professionCanal = new JSONArray(jsonRate.getString("ProfessionCanal"));
            }
            if ("003".equals(id)) {
                irscreditlevel = new JSONArray(jsonRate.getString("Irscreditlevel"));
            }
            if ("004".equals(id)) {
                vouchType = new JSONArray(jsonRate.getString("VouchType"));
            }
            if ("007".equals(id)) {
                manageInfo = new JSONArray(jsonRate.getString("ManageInfo"));
            }
            if ("008".equals(id)) {
                businessCond = new JSONArray(jsonRate.getString("BusinessCond"));
            }
            if ("009".equals(id)) {
                creditCond = new JSONArray(jsonRate.getString("CreditCond"));
            }
            if ("010".equals(id)) {
                billNum = new JSONArray(jsonRate.getString("BillNum"));
            }
            if ("012".equals(id)) {
                wagesCond = new JSONArray(jsonRate.getString("WagesCond"));
            }
            if ("013".equals(id)) {
                //网上支付
                flag1301 = new JSONArray(jsonRate.getString("Flag1301"));
                flag1302 = new JSONArray(jsonRate.getString("Flag1301"));
                flag1303 = new JSONArray(jsonRate.getString("Flag1301"));
                flag1304 = new JSONArray(jsonRate.getString("Flag1301"));
                flag1305 = new JSONArray(jsonRate.getString("Flag1301"));
            }
            if ("014".equals(id)) {
                //代发
                flag1401 = new JSONArray(jsonRate.getString("Flag1401"));
                flag1402 = new JSONArray(jsonRate.getString("Flag1401"));
                flag1403 = new JSONArray(jsonRate.getString("Flag1401"));
                flag1404 = new JSONArray(jsonRate.getString("Flag1401"));
                //irscreditlevel = new JSONArray(jsonRate.getString("Irscreditlevel"));
            }
            //文本框
            if ("005".equals(id)) {
                businessSum = "00";
            }
            if ("006".equals(id)) {
                fundSubside = "00";
            }
            if ("011".equals(id)) {
                firstPaySum = "00";
            }
            if ("015".equals(id)) {
                //合作商
                project = new JSONArray(jsonRate.getString("Project"));
            }
            if ("16".equals(id)) {
                //担保公司
                guarantee = new JSONArray(jsonRate.getString("Guarantee"));
            }
            if ("100".equals(id)) {
                //期数
                //irscreditlevel = new JSONArray(jsonRate.getString("Irscreditlevel"));
            }
        }
        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);
    }

    /**
     * 解析业务品种json数据
     *
     * @param jsonData
     * @throws JSONException
     */
    private void parseJSONWithGsonTol(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        if (jsonObject.getJSONObject("ResponseParams").toString().length() == 2) {
            //Toast.makeText(RateActivity.this,"您选择的月份是：",Toast.LENGTH_SHORT).show();
            return;
        }
        errorMsg = jsonObject.getJSONObject("ResponseParams").getString("ErrorMsg");
        if (errorMsg != null && errorMsg.length() > 0) {
            Message msg = new Message();
            msg.what = 9;
            handler.sendMessage(msg);
            return;
        }

        JSONArray jsonArray = jsonObject.getJSONObject("ResponseParams").getJSONArray("array");
        Log.d(TAG, "jsonObject: " + jsonArray);
        rateTol = jsonArray.getJSONObject(jsonArray.length() - 1);
        //Log.d(TAG, "jsonObject: "+tolObj.getString("基础利率 （%）"));
        /*Gson gson = new Gson();
        bussType = gson.fromJson(jsonArray, new TypeToken<List<bussType>>(){}.getType());
        Log.d(TAG, "bussType: "+bussType);*/

        Message msg = new Message();
        msg.what = 2;
        handler.sendMessage(msg);
    }

    /**
     * 解析客户json数据
     *
     * @param jsonData
     * @throws JSONException
     */
    public void parseJSONWithGsonCust(String jsonData) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonData);
        if (jsonObject.getJSONObject("ResponseParams").toString().length() == 2) {
            return;
        }

        JSONArray jsonArray = jsonObject.getJSONObject("ResponseParams").getJSONArray("array");
        LogUtils.d("jsonObject: " + jsonArray);
        Gson gson = new Gson();
        customer = gson.fromJson(jsonArray.toString(), new TypeToken<List<Customer>>() {
        }.getType());
        LogUtils.d("customer: " + customer);

        Message msg = new Message();
        msg.what = 5;
        handler.sendMessage(msg);
    }

    void reqBussType() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.busstype);
//        final LinearLayout layout2 = new LinearLayout(this);
//        layout2.setOrientation(LinearLayout.HORIZONTAL);
        final GridLayout layout2 = new GridLayout(this);
        GridLayoutManager grid = new GridLayoutManager(this, 2);
        layout2.setColumnCount(2);
        layout2.setRowCount(2);
        TextView textView1 = new TextView(this);
        layout2.addView(textView1);
        Spinner spinner = new Spinner(this);
        /*this.dataEdu = new ArrayList<CharSequence>();
        this.dataEdu.add("请选择");
        for(int i=0; i<bussType.size(); i++){
            this.dataEdu.add(bussType.get(i).getTypeName());
        }
        this.adapteEdu=new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item,this.dataEdu);
        this.adapteEdu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
        bussType.add(0, new BussType("请选择", "", "", "", ""));
        BussAdapter bussAdapter = new BussAdapter(this, bussType);
        spinner.setAdapter(bussAdapter);
        layout2.addView(spinner);
        layout.addView(layout2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Log.d(TAG, "getTypeName: "+bussType.get(position).getTypeName());
                //Log.d(TAG, "getSortNo: "+bussType.get(position).getSortNo());
                LinearLayout layout11 = (LinearLayout) findViewById(R.id.busstype);
                int count = layout11.getChildCount();
                //Log.d(TAG, "length: "+bussType.get(position).getSortNo().length());
                Log.d(TAG, "count: " + layout11.getChildCount());
                Log.d(TAG, "id: " + id);
                Log.d(TAG, "position: " + position);
                Log.d(TAG, "view: " + parent.getItemAtPosition(position).toString());
                BussType bussType1 = (BussType) parent.getItemAtPosition(position);
                String sortNo = bussType1.getSortNo();
                if (0 != id) {
                    reqBussBody(bussType1);
                }
                //选择第一级删除后面几级下拉框
                if (sortNo.length() == 1 && !sortNo.startsWith("4")) {
                    for (int j = 2; count > j; j++) {
                        layout11.removeViewAt(2);
                    }
                } else if (sortNo.length() == 4 && !sortNo.startsWith("4")) {
                    for (int j = 3; count > j; j++) {
                        layout11.removeViewAt(3);
                    }
                } else if (sortNo.length() == 7 && !sortNo.startsWith("4")) {
                    for (int j = 4; count > j; j++) {
                        layout11.removeViewAt(4);
                    }
                } else if (sortNo.length() == 10 && !sortNo.startsWith("4")) {
                    for (int j = 5; count > j; j++) {
                        layout11.removeViewAt(5);
                    }
                }

                if (sortNo.length() == 4 && sortNo.startsWith("4")) {
                    for (int j = 2; count > j; j++) {
                        layout11.removeViewAt(2);
                    }
                }


                /*if(count >= 5 && bussType.get(position).getSortNo().length()==1){
                    layout11.removeViewAt(4);
                }*/

                //Toast.makeText(RateActivity.this,"您选择的月份是："+bussType.get(position),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void rateEstimate() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.estimate);
        final LinearLayout layout2 = new LinearLayout(this);
        layout2.setOrientation(LinearLayout.HORIZONTAL);
        TextView textView = new TextView(this);
        textView.setText("担保信息：");
        layout2.addView(textView);
        TextView textView1 = new TextView(this);
        //textView1.setText("请选择");
        layout2.addView(textView1);
        Spinner spinner = new Spinner(this);
        this.dataEdu = new ArrayList<CharSequence>();
        this.dataEdu.add("担保");
        /*for(int i=0; i<bussType.size(); i++){
            this.dataEdu.add(bussType.get(i).getTypeName());
        }*/
        this.adapteEdu = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, this.dataEdu);
        this.adapteEdu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(this.adapteEdu);
        layout2.addView(spinner);
        layout.addView(layout2);
    }

    void rateContent() {
        //投向行业
        setSpinner("001", "投向行业：");
        //下拉框
        if (professionCanal != null) {
            setSpinner("002", "业务渠道：");
        }
        if (irscreditlevel != null) {
            setSpinner("003", "信用等级：");
        }
        if (vouchType != null) {
            setSpinner("004", "担保方式：");
        }
        //文本框
        if (businessSum != null) {
            setEditText("005", "贷款额度：");
        }
        if (fundSubside != null) {
            setEditText("006", "近三个月日均存贷比：");
        }
        if (manageInfo != null) {
            setSpinner("007", "账户类型：");
        }
        if (businessCond != null) {
            setSpinner("008", "与我行来往情况：");
        }
        if (creditCond != null) {
            setSpinner("009", "信用状况：");
        }
        if (billNum != null) {
            setSpinner("010", "购房套数：");
        }
        if (firstPaySum != null) {
            setEditText("011", "首付比例：");
        }
        if (wagesCond != null) {
            setSpinner("012", "工资账户在我行情况：");
        }
        if (flag1301 != null) {
            setSpinner("1301", "开通网上银行：");
        }
        if (flag1302 != null) {
            setSpinner("1302", "开通卡乐付：");
        }
        if (flag1303 != null) {
            setSpinner("1303", "开通手机银行：");
        }
        if (flag1304 != null) {
            setSpinner("1304", "开通短信银行：");
        }
        if (flag1305 != null) {
            setSpinner("1305", "开通微信银行：");
        }
        if (flag1401 != null) {
            setSpinner("1401", "借款人个人工资代发：");
        }
        if (flag1402 != null) {
            setSpinner("1402", "借款单位开通员工工资代发：");
        }
        if (flag1403 != null) {
            setSpinner("1403", "农商行员工：");
        }
        if (flag1404 != null) {
            setSpinner("1404", "承贷农商行股东：");
        }
        if (project != null) {
            setSpinner("015", "合作商：");
        }
        if (guarantee != null) {
            setSpinner("016", "担保公司：");
        }
        /*if(irscreditlevel != null){
            setSpinner("003","信用等级：");
        }*/


    }

    /**
     * 自动生成下拉框
     *
     * @param id
     * @param title
     */
    void setSpinner(String id, String title) {
        try {
            if ("001".equals(id)) {
                LinearLayout industryLoyout = (LinearLayout) findViewById(R.id.id_industry);//投向行业
                if (industryType != null) {
                    industryLoyout.setVisibility(View.VISIBLE);
                    //获取第一级数据
                    loadIndutry("", spinner1);
                    return;
                } else {
                    industryLoyout.setVisibility(View.INVISIBLE);
                    spinner2.setVisibility(View.GONE);
                    spinner3.setVisibility(View.GONE);
                    spinner4.setVisibility(View.GONE);
                    return;
                }
            }
            LinearLayout layout = (LinearLayout) findViewById(R.id.estimate);//利率测算项
//            GridLayout layout = (GridLayout) findViewById(R.id.estimate);
            final LinearLayout layout2 = new LinearLayout(this);
            layout2.setOrientation(LinearLayout.HORIZONTAL);
            TextView textView = new TextView(this);
            textView.setText(title);
            layout2.addView(textView);
            TextView textView1 = new TextView(this);
            //textView1.setText("请选择");
            layout2.addView(textView1);
            Spinner spinner = new Spinner(this);
            spinner.setId(Integer.parseInt(id));
            this.dataEdu = new ArrayList<CharSequence>();
            if ("002".equals(id)) {
                for (int i = 0; i < professionCanal.length(); i++) {
                    JSONObject proJson = professionCanal.getJSONObject(i);
                    this.dataEdu.add(proJson.getString("val"));
                }
            }
            if ("003".equals(id)) {
                for (int i = 0; i < irscreditlevel.length(); i++) {
                    JSONObject proJson = irscreditlevel.getJSONObject(i);
                    this.dataEdu.add(proJson.getString("val"));
                }
            }
            if ("004".equals(id)) {
                for (int i = 0; i < vouchType.length(); i++) {
                    JSONObject proJson = vouchType.getJSONObject(i);
                    this.dataEdu.add(proJson.getString("val"));
                }
            }
            if ("007".equals(id)) {
                for (int i = 0; i < manageInfo.length(); i++) {
                    JSONObject proJson = manageInfo.getJSONObject(i);
                    this.dataEdu.add(proJson.getString("val"));
                }
            }
            if ("008".equals(id)) {
                for (int i = 0; i < businessCond.length(); i++) {
                    JSONObject proJson = businessCond.getJSONObject(i);
                    this.dataEdu.add(proJson.getString("val"));
                }
            }
            if ("009".equals(id)) {
                for (int i = 0; i < creditCond.length(); i++) {
                    JSONObject proJson = creditCond.getJSONObject(i);
                    this.dataEdu.add(proJson.getString("val"));
                }
            }
            if ("010".equals(id)) {
                for (int i = 0; i < billNum.length(); i++) {
                    JSONObject proJson = billNum.getJSONObject(i);
                    this.dataEdu.add(proJson.getString("val"));
                }
            }
            if ("012".equals(id)) {
                for (int i = 0; i < wagesCond.length(); i++) {
                    JSONObject proJson = wagesCond.getJSONObject(i);
                    this.dataEdu.add(proJson.getString("val"));
                }
            }
            if ("1301".equals(id) || "1302".equals(id) || "1303".equals(id) || "1304".equals(id) || "1305".equals(id)) {
                for (int i = 0; i < flag1301.length(); i++) {
                    JSONObject proJson = flag1301.getJSONObject(i);
                    this.dataEdu.add(proJson.getString("val"));
                }
            }
            if ("1401".equals(id) || "1402".equals(id) || "1403".equals(id) || "1404".equals(id)) {
                for (int i = 0; i < flag1401.length(); i++) {
                    JSONObject proJson = flag1401.getJSONObject(i);
                    this.dataEdu.add(proJson.getString("val"));
                }
            }
            if ("015".equals(id)) {
                for (int i = 0; i < project.length(); i++) {
                    JSONObject proJson = project.getJSONObject(i);
                    this.dataEdu.add(proJson.getString("val"));
                }
            }
            if ("016".equals(id)) {
                for (int i = 0; i < guarantee.length(); i++) {
                    JSONObject proJson = guarantee.getJSONObject(i);
                    this.dataEdu.add(proJson.getString("val"));
                }
            }

            this.adapteEdu = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, this.dataEdu);
            this.adapteEdu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(this.adapteEdu);
            layout2.addView(spinner);
            layout.addView(layout2);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 自动生成文本框
     *
     * @param id
     * @param title
     */
    void setEditText(String id, String title) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.estimate);
        final LinearLayout layout2 = new LinearLayout(this);
        layout2.setOrientation(LinearLayout.HORIZONTAL);
        layout2.setWeightSum(2);
        Log.d(TAG, "professionCanal: " + professionCanal);
        TextView textView = new TextView(this);
        textView.setText(title);
        layout2.addView(textView);
        DisplayMetrics dm = new DisplayMetrics();
        EditText editText = new EditText(this);
        editText.setId(Integer.parseInt(id));
        //editText.setMinWidth(dm.widthPixels / 4);
        //editText.setMaxWidth(dm.widthPixels / 2);
        layout2.addView(editText);
        layout.addView(layout2);
    }

    /**
     * 获取测算结果
     *
     * @param view
     */
    public void clickSubmmit(View view) {
        //获取利率测算值
        String interesArray = getRateUrl();
        EditText editText1 = (EditText) findViewById(R.id.et_term);
        String term = editText1.getText().toString();
        if (term != null && term.length() > 0) {
            interesArray = interesArray + "&iTermMonth@100@" + term;
            //获取测算结果
            reqRateTol(interesArray);
        } else {
            Message msg = new Message();
            msg.what = 9;
            errorMsg = "请输入期数！！！";
            handler.sendMessage(msg);
        }
    }

    public void viewCustomerClick(View view) {
        showPopListView();
    }

    private void showPopListView() {

        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_list, null);
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(layoutManager);
        //处理popWindow 显示内容
        recyclerView.setAdapter(new MyRecycleViewAdapter(this, R.layout.common_adapter_item, listData));
        //创建并显示popWindow
        CustomPopWindow mListPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)//显示大小
                .enableBackgroundDark(true)
                .setBgDarkAlpha(0.6f)
                .create()
                .showAsDropDown(etCustType, 0, 200);
    }

    public String getRateUrl() {
        StringBuffer sb = new StringBuffer();
        try {
            //投向行业
            if (industryItemno != null) {
                sb.append("&IndustryType@001@" + industryItemno);
            }
            Spinner s002 = (Spinner) findViewById(TAG2);
            if (s002 != null) {
                String t002 = s002.getSelectedItem().toString();
                for (int i = 0; i < professionCanal.length(); i++) {
                    JSONObject obj = professionCanal.getJSONObject(i);
                    if (t002.equals(obj.getString("val"))) {
                        sb.append("&ProfessionCanal@002@" + obj.getString("key"));
                    }
                }
            }
            Spinner s003 = (Spinner) findViewById(TAG3);
            if (s003 != null) {
                String t003 = s003.getSelectedItem().toString();
                for (int i = 0; i < irscreditlevel.length(); i++) {
                    JSONObject obj = irscreditlevel.getJSONObject(i);
                    if (t003.equals(obj.getString("val"))) {
                        sb.append("&Irscreditlevel@003@" + obj.getString("key"));
                    }
                }
            }
            Spinner s004 = (Spinner) findViewById(TAG4);
            if (s004 != null) {
                String t004 = s004.getSelectedItem().toString();
                for (int i = 0; i < vouchType.length(); i++) {
                    JSONObject obj = vouchType.getJSONObject(i);
                    if (t004.equals(obj.getString("val"))) {
                        sb.append("&VouchType@004@" + obj.getString("key"));
                    }
                }
            }
            Spinner s007 = (Spinner) findViewById(TAG7);
            if (s007 != null) {
                String t007 = s007.getSelectedItem().toString();
                for (int i = 0; i < manageInfo.length(); i++) {
                    JSONObject obj = manageInfo.getJSONObject(i);
                    if (t007.equals(obj.getString("val"))) {
                        sb.append("&ManageInfo@007@" + obj.getString("key"));
                    }
                }
            }
            Spinner s008 = (Spinner) findViewById(TAG8);
            if (s008 != null) {
                String t008 = s008.getSelectedItem().toString();
                for (int i = 0; i < businessCond.length(); i++) {
                    JSONObject obj = businessCond.getJSONObject(i);
                    if (t008.equals(obj.getString("val"))) {
                        sb.append("&BusinessCond@008@" + obj.getString("key"));
                    }
                }
            }
            Spinner s009 = (Spinner) findViewById(TAG9);
            if (s009 != null) {
                String t009 = s009.getSelectedItem().toString();
                for (int i = 0; i < creditCond.length(); i++) {
                    JSONObject obj = creditCond.getJSONObject(i);
                    if (t009.equals(obj.getString("val"))) {
                        sb.append("&CreditCond@009@" + obj.getString("key"));
                    }
                }
            }
            Spinner s010 = (Spinner) findViewById(TAG10);
            if (s010 != null) {
                String t010 = s010.getSelectedItem().toString();
                for (int i = 0; i < billNum.length(); i++) {
                    JSONObject obj = billNum.getJSONObject(i);
                    if (t010.equals(obj.getString("val"))) {
                        sb.append("&BillNum@010@" + obj.getString("key"));
                    }
                }
            }
            Spinner s012 = (Spinner) findViewById(TAG12);
            if (s012 != null) {
                String t012 = s012.getSelectedItem().toString();
                for (int i = 0; i < wagesCond.length(); i++) {
                    JSONObject obj = wagesCond.getJSONObject(i);
                    if (t012.equals(obj.getString("val"))) {
                        sb.append("&WagesCond@012@" + obj.getString("key"));
                    }
                }
            }
            Spinner s1301 = (Spinner) findViewById(TAG1301);
            if (s1301 != null) {
                String t1301 = s1301.getSelectedItem().toString();
                for (int i = 0; i < flag1301.length(); i++) {
                    JSONObject obj = flag1301.getJSONObject(i);
                    if (t1301.equals(obj.getString("val"))) {
                        sb.append("&Flag1301@013@" + obj.getString("key"));
                    }
                }
            }
            Spinner s1302 = (Spinner) findViewById(TAG1302);
            if (s1302 != null) {
                String t1302 = s1302.getSelectedItem().toString();
                for (int i = 0; i < flag1302.length(); i++) {
                    JSONObject obj = flag1302.getJSONObject(i);
                    if (t1302.equals(obj.getString("val"))) {
                        sb.append("&Flag1302@013@" + obj.getString("key"));
                    }
                }
            }
            Spinner s1303 = (Spinner) findViewById(TAG1303);
            if (s1303 != null) {
                String t1303 = s1303.getSelectedItem().toString();
                for (int i = 0; i < flag1303.length(); i++) {
                    JSONObject obj = flag1303.getJSONObject(i);
                    if (t1303.equals(obj.getString("val"))) {
                        sb.append("&Flag1303@013@" + obj.getString("key"));
                    }
                }
            }
            Spinner s1304 = (Spinner) findViewById(TAG1304);
            if (s1304 != null) {
                String t1304 = s1304.getSelectedItem().toString();
                for (int i = 0; i < flag1304.length(); i++) {
                    JSONObject obj = flag1304.getJSONObject(i);
                    if (t1304.equals(obj.getString("val"))) {
                        sb.append("&Flag1304@013@" + obj.getString("key"));
                    }
                }
            }
            Spinner s1305 = (Spinner) findViewById(TAG1305);
            if (s1305 != null) {
                String t1305 = s1305.getSelectedItem().toString();
                for (int i = 0; i < flag1305.length(); i++) {
                    JSONObject obj = flag1305.getJSONObject(i);
                    if (t1305.equals(obj.getString("val"))) {
                        sb.append("&Flag1305@013@" + obj.getString("key"));
                    }
                }
            }
            Spinner s1401 = (Spinner) findViewById(TAG1401);
            if (s1401 != null) {
                String t1401 = s1401.getSelectedItem().toString();
                for (int i = 0; i < flag1401.length(); i++) {
                    JSONObject obj = flag1401.getJSONObject(i);
                    if (t1401.equals(obj.getString("val"))) {
                        sb.append("&Flag1401@014@" + obj.getString("key"));
                    }
                }
            }
            Spinner s1402 = (Spinner) findViewById(TAG1402);
            if (s1402 != null) {
                String t1402 = s1402.getSelectedItem().toString();
                for (int i = 0; i < flag1402.length(); i++) {
                    JSONObject obj = flag1402.getJSONObject(i);
                    if (t1402.equals(obj.getString("val"))) {
                        sb.append("&Flag1402@014@" + obj.getString("key"));
                    }
                }
            }
            Spinner s1403 = (Spinner) findViewById(TAG1403);
            if (s1403 != null) {
                String t1403 = s1403.getSelectedItem().toString();
                for (int i = 0; i < flag1403.length(); i++) {
                    JSONObject obj = flag1403.getJSONObject(i);
                    if (t1403.equals(obj.getString("val"))) {
                        sb.append("&Flag1403@014@" + obj.getString("key"));
                    }
                }
            }
            Spinner s1404 = (Spinner) findViewById(TAG1404);
            if (s1404 != null) {
                String t1404 = s1404.getSelectedItem().toString();
                for (int i = 0; i < flag1404.length(); i++) {
                    JSONObject obj = flag1404.getJSONObject(i);
                    if (t1404.equals(obj.getString("val"))) {
                        sb.append("&Flag1404@014@" + obj.getString("key"));
                    }
                }
            }
            Spinner s015 = (Spinner) findViewById(TAG15);
            if (s015 != null) {
                String t015 = s015.getSelectedItem().toString();
                for (int i = 0; i < project.length(); i++) {
                    JSONObject obj = project.getJSONObject(i);
                    if (t015.equals(obj.getString("val"))) {
                        sb.append("&Project@015@" + obj.getString("key"));
                    }
                }
            }
            Spinner s016 = (Spinner) findViewById(TAG16);
            if (s016 != null) {
                String t016 = s016.getSelectedItem().toString();
                for (int i = 0; i < guarantee.length(); i++) {
                    JSONObject obj = guarantee.getJSONObject(i);
                    if (t016.equals(obj.getString("val"))) {
                        sb.append("&Guarantee@016@" + obj.getString("key"));
                    }
                }
            }

            EditText et005 = (EditText) findViewById(TAG5);
            if (et005 != null) {
                String vs005 = et005.getText().toString();
                Log.d(TAG, "vs005: " + vs005);
                if (vs005 != null && vs005.length() > 0) {
                    sb.append("&BusinessSum@005@" + vs005);
                }
            }
            EditText et006 = (EditText) findViewById(TAG6);
            if (et006 != null) {
                String vs006 = et006.getText().toString();
                if (vs006 != null && vs006.length() > 0) {
                    sb.append("&FundSubside@005@" + vs006);
                }
            }
            EditText et011 = (EditText) findViewById(TAG11);
            if (et011 != null) {
                String vs011 = et011.getText().toString();
                if (vs011 != null && vs011.length() > 0) {
                    sb.append("&FirstPaySum@011@" + vs011);
                }
            }
            //Log.d(TAG, "sb: "+sb.substring(1));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String str = null;
        if (sb != null && sb.length() > 0) {
            str = sb.substring(1);
        }
        return str;
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        try {
            builder.setMessage("基础利率 （%）：" + rateTol.getString("BaseRate") +
                    "\n测算执行利率 （%）：" + rateTol.get("ExerciseRate") +
                    "\n测算浮动比例（%）：" + rateTol.get("FloatingRatio"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        builder.setTitle("利率测算结果");
        builder.setPositiveButton("确认", null);

        builder.create().show();
    }

    void removeRate() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.estimate);
        int count = layout.getChildCount();
        for (int i = 0; i < count; i++) {
            layout.removeViewAt(0);
        }
        //初始化每项的值
        bussTypeVal = null;
        industryItemno = null;
        industryType = null;      //投向行业
        professionCanal = null;      //业务渠道
        irscreditlevel = null;      //信用等级
        vouchType = null;      //贷款方式
        manageInfo = null;      //账户类型
        businessCond = null;      //与我行往来情况
        creditCond = null;      //信用情况
        billNum = null;      //购房套数
        wagesCond = null;      //工资账户在我行情况
        businessSum = null;      //贷款额度
        fundSubside = null;      //近三个月日均存贷比
        firstPaySum = null;      //首付比例
        flag1301 = null;      //开通网上银行
        flag1302 = null;      //开通卡乐付
        flag1303 = null;      //开通手机银行
        flag1304 = null;      //开通短信银行
        flag1305 = null;      //开通微信银行
        flag1401 = null;      //借款人个人工资代发
        flag1402 = null;      //借款单位开通员工工资代发
        flag1403 = null;      //农商行员工
        flag1404 = null;      //承贷农商行股东
        project = null;      //合作商
        guarantee = null;      //担保公司
        iTermMonth = null;      //期数
        //影藏投向行业
        LinearLayout industryLoyout = (LinearLayout) findViewById(R.id.id_industry);
        industryLoyout.setVisibility(View.INVISIBLE);
        spinner2.setVisibility(View.GONE);
        spinner3.setVisibility(View.GONE);
        spinner4.setVisibility(View.GONE);

    }

    public void loadIndutry(String itemno, Spinner spinner) {
        Data data = new Data();
        data.initData(this);
        List<Industry> industryList = data.getIndustry(itemno);
        if (industryList == null) {
            data.insertData();
            industryList = data.getIndustry(itemno);
        }
        for (Industry industry : industryList) {
            Log.d(TAG, "clickSelData: " + industry.getEtemname());
        }
        industryList.add(0, new Industry("", "", "请选择"));
        IndustyAdapter industyAdapter = new IndustyAdapter(this, industryList);
        spinner.setAdapter(industyAdapter);
        spinner.setVisibility(View.VISIBLE);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Log.d(TAG, "getTypeName: "+bussType.get(position).getTypeName());
                //Log.d(TAG, "getitemno: "+bussType.get(position).getitemno());
                //Log.d(TAG, "length: "+bussType.get(position).getitemno().length());
                Log.d(TAG, "id: " + id);
                Log.d(TAG, "position: " + position);
                Log.d(TAG, "view: " + parent.getItemAtPosition(position).toString());
                Industry industry = (Industry) parent.getItemAtPosition(position);
                String itemno = industry.getItemno();
                if (0 != id && itemno.length() == 1) {
                    loadIndutry(itemno, spinner2);
                } else if (0 != id && itemno.length() == 3) {
                    loadIndutry(itemno, spinner3);
                } else if (0 != id && itemno.length() == 4) {
                    loadIndutry(itemno, spinner4);
                } else if (0 != id && itemno.length() == 4) {
                    //获取投向行业的值
                    industryItemno = itemno;
                }
                //选择第一级隐藏后面几级下拉框
                if (itemno.length() == 1) {
                    spinner3.setVisibility(View.GONE);
                    spinner4.setVisibility(View.GONE);
                } else if (itemno.length() == 3) {
                    spinner4.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @OnClick(R.id.ll_custName)
    public void onViewClicked() {
        reqCustBody();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        LogUtils.v(resultCode + "~~~~~~~~~~~~~~~~~~~~" + requestCode);
        if (resultCode == 0)
            if (requestCode == 99) {
                Bundle bundle = data.getBundleExtra("data");
                String name = bundle.getString("name");
                String id = bundle.getString("id");
                listData.clear();
                for (Customer cus : customer) {
                    LogUtils.v(name + "===" + cus.getCustomerName() + "\t\t" + id + "===" + cus.getCertID());
                    if (cus.getCustomerName().equals(name) && cus.getCertID().equals(id)) {
                        listData.add(0, cus.getCustomerID());
                        listData.add(1, cus.getCustomerName());
                        String customerType = Utils.getTypeName(cus.getCustomerType());
                        listData.add(2, customerType);
                        listData.add(3, cus.getCertID());
                        etCustName.setText(cus.getCustomerName());
                        etCustType.setText(customerType);
                        llCustType.setClickable(true);
                    }
                }
            }
    }
}

