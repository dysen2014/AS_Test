package com.pactera.financialmanager.ui.hallfirst;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.DeployDialog;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.model.CMMarketingActivity2CustomerInfo;
import com.pactera.financialmanager.ui.model.CreateDeployInfo;
import com.pactera.financialmanager.ui.model.QueryDeployInfo;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.Constants.requestType;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.LimitsUtil;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONObject;

/**
 * 这是目前新的客户详情、客户购买力以及推荐的产品的页面
 *
 * @author Administrator
 */
public class CMMarketingActivity2 extends ParentActivity implements
        OnClickListener {

    private RadioGroup rgrpTopTabs;
    private RadioButton rbtnInfo, rbtnPower, rbtnProduct;
    private TextView tvCustomerName, tvSex, tvBirthday;
    private LinearLayout layPersonInfo, layCommonInfo;
    private ImageView imvPhoto;
    private TextView tvFRXM, tvFRDB, tvFRDH;
    private TextView tvYYZZ, tvJZPJ;
    private TextView tvCard, tvStyle, tvLevel, tvOrgName;
    private TextView tvSTFF_F;
    private TextView tvManager, tvManagerNum;
    private TextView tvPhone, tvCardID;

    private FragmentManager fragmentManager;
    private HallCustomerInfoFragment customerinfo;
    private HallCustomerPowerFragment customerpower;
    private HallCustomerlicaiproFragment licaipro;

    private HConnection customerInfoCon;
    private HConnection queryDeployCon, createDeployCon, deleteDeployCon;
    private final int customerInfoFlag = 0x03;
    private final int addressIndex = 2;// 地址返回
    private final int queryDeployFlag = 91;
    private final int createDeployFlag = 92;//
    private final int deleteDeployFlag = 93;//

    // 定位相关
    private LocationClient mLocClient;
    // 当前位置经纬度
    private static LatLng ll;
    // 当前位置
    private static String locStr;
    ImageView address_btn;// 上传地址
    Button btn_apply_for_deploy;// 申请调配

    // 客户ID号
    private String customerID = "";
    private boolean isForperson = true;
    private boolean isQuery = true;
    private boolean isOwener = false;


    private boolean isCreateDeployNew = false;
    private QueryDeployInfo mDepoyInfo;
    private String mCustID = "", mReason = "", mCreateDeployDes = "";
    private String mOwnerID = "";//客户经理ID
    private String mOwnerBRID = "";//客户所属机构ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sch_cm2);
        initTitle(this, R.drawable.hallword);

        initView();
        setListener();

        fragmentManager = getSupportFragmentManager();
        customerID = this.getIntent().getStringExtra("custID");
        isForperson = this.getIntent().getBooleanExtra("isForperson", true);
        isQuery = this.getIntent().getBooleanExtra("isQuery", true);

        if (!isQuery) {
            address_btn.setVisibility(View.GONE);
        }

        hiddentTabForCommon();//三个头部按钮的显示或隐藏
        setFragmentCustomerInfo();// 客户信息Fragment
        sendRequestForCustomerInfo();
        queryDeploy();

        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setIsNeedAddress(true);// 设置是否需要地址信息，默认为无地址
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setAddrType("all");
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
        option.setScanSpan(4000);
        mLocClient.setLocOption(option);
        if (!mLocClient.isStarted()) {
            mLocClient.start();// 开始定位
        }
    }

    private void initView() {
        // 选项卡
        rgrpTopTabs = (RadioGroup) findViewById(R.id.rgrp_top_tabs);
        rbtnInfo = (RadioButton) findViewById(R.id.rbtn_info);
        rbtnPower = (RadioButton) findViewById(R.id.rbtn_power);
        rbtnProduct = (RadioButton) findViewById(R.id.rbtn_product);
        layPersonInfo = (LinearLayout) findViewById(R.id.lay_personInfo);
        layCommonInfo = (LinearLayout) findViewById(R.id.lay_commonInfo);
        tvFRXM = (TextView) findViewById(R.id.tv_frxm);
        tvFRDB = (TextView) findViewById(R.id.tv_frdb);
        tvFRDH = (TextView) findViewById(R.id.tv_frdh);
        tvYYZZ = (TextView) findViewById(R.id.tv_yyzz);
        tvJZPJ = (TextView) findViewById(R.id.tv_jzpj);

        // 客户基本信息
        imvPhoto = (ImageView) findViewById(R.id.imv_photo);
        tvCustomerName = (TextView) findViewById(R.id.tv_customername);
        tvSex = (TextView) findViewById(R.id.tvsex);
        tvBirthday = (TextView) findViewById(R.id.tvbirthday);
        tvCard = (TextView) findViewById(R.id.tvlevel);
        tvStyle = (TextView) findViewById(R.id.tvfengxianpianhao);
        tvLevel = (TextView) findViewById(R.id.tvzonghepingji);
        tvOrgName = (TextView) findViewById(R.id.tvjigou);
        tvManager = (TextView) findViewById(R.id.tvmanager);
        tvManagerNum = (TextView) findViewById(R.id.tvmanagernum);
        tvSTFF_F = (TextView) findViewById(R.id.isBankstaff);
        tvPhone = (TextView) findViewById(R.id.tvphone);
        tvCardID = (TextView) findViewById(R.id.tvcardID);
        address_btn = (ImageView) findViewById(R.id.img_customerinfo_address);
        btn_apply_for_deploy = (Button) findViewById(R.id.btn_customerinfo_apply_for_deploy);

        // 只有客户经理显示定位按钮
        String staid = LogoActivity.user.getStaId();
        if (LimitsUtil.checkIsCustManager(staid)) {
            address_btn.setVisibility(View.VISIBLE);
        } else {
            address_btn.setVisibility(View.GONE);
        }
    }

    private void setListener() {
        rbtnInfo.setOnClickListener(this);
        rbtnPower.setOnClickListener(this);
        rbtnProduct.setOnClickListener(this);
        address_btn.setOnClickListener(this);
        btn_apply_for_deploy.setOnClickListener(this);
    }

    /**
     * 隐藏部分对公权限
     */
    private void hiddentTabForCommon() {
        int hiddentIndex = 0;// 统计是否都隐藏
        // 基本信息
        boolean isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T0201, false);
        if (!isRight) {
            rbtnInfo.setVisibility(View.GONE);
            hiddentIndex++;
        }
        // 客户购买力
        isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T0202, false);
        if (isRight && isForperson) {
            rbtnPower.setVisibility(View.VISIBLE);
        } else {
            rbtnPower.setVisibility(View.GONE);
            hiddentIndex++;
        }
        // 推荐理财产品
        isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T0203, false);
        if (isRight && isForperson) {
            rbtnProduct.setVisibility(View.VISIBLE);
        } else {
            rbtnProduct.setVisibility(View.GONE);
            hiddentIndex++;
        }

        if (isForperson) {
            layPersonInfo.setVisibility(View.VISIBLE);
            layCommonInfo.setVisibility(View.GONE);
        } else {
            layPersonInfo.setVisibility(View.GONE);
            layCommonInfo.setVisibility(View.VISIBLE);
        }

        // 如果三个单选卡都隐藏，则把顶部选项栏隐藏
        if (hiddentIndex == 3) {
            rgrpTopTabs.setVisibility(View.INVISIBLE);
        } else {
            rgrpTopTabs.setVisibility(View.VISIBLE);
        }
    }
//隐藏或显示客户购买力
    private void showOrHideCustomerPower(String OwenerID) {
        if (!LogoActivity.user.getUserCode().equals(OwenerID)) {
            rbtnPower.setVisibility(View.GONE);
            address_btn.setVisibility(View.GONE);// 上传地址
            btn_apply_for_deploy.setVisibility(View.VISIBLE);// 申请调配
        } else {
            btn_apply_for_deploy.setVisibility(View.GONE);
            address_btn.setVisibility(View.VISIBLE);
            rbtnPower.setVisibility(View.VISIBLE);

        }
    }

    private void sendRequestForCustomerInfo() {
        if (Tool.haveNet(CMMarketingActivity2.this)) {
            // 客户基本信息查询
            String requestType = InterfaceInfo.HALLFIRST_INFO_QUERY;
            if (!isForperson) {
                requestType = InterfaceInfo.HALLFIRST_INFO_QUERY_COMMON;
            }
            customerInfoCon = RequestInfo(this, Constants.requestType.Search,
                    requestType, customerID, customerInfoFlag);
        }
    }

    private void queryDeploy() {
        String info = "&spareOne=" + customerID + "&spareTwo=" + LogoActivity.user.getStaId();
        queryDeployCon = RequestInfo(this, requestType.Other,
                InterfaceInfo.APPLY_FOR_DEPLOY_QUERY, info, queryDeployFlag);
    }

    private void createDeploy() {
        if (TextUtils.isEmpty(mCustID) || TextUtils.isEmpty(mReason)) {
            return;
        }
        if (mDepoyInfo.APPLYSTATUS.equals("01")) {
            return;
        }
        CreateDeployInfo newDEPLOY = new CreateDeployInfo();
        newDEPLOY.setCUSTID(mCustID);
        newDEPLOY.setAPPLYREASON(mReason);
        newDEPLOY.setAPPLYSTAID(LogoActivity.user.getStaId());
        newDEPLOY.setDES(mCreateDeployDes);
        newDEPLOY.setOLDBRID(mOwnerBRID);
        newDEPLOY.setOLDOWENERID(mOwnerID);

        createDeployCon = RequestInfo(this,
                Constants.requestType.Insert,
                InterfaceInfo.APPLY_FOR_DEPLOY_CREATE, newDEPLOY.toString(),
                createDeployFlag);
    }

    private void deleteDeploy() {
        String info = "&spareOne=" + mDepoyInfo.DEPLOYID + "&spareTwo=";
        deleteDeployCon = RequestInfo(this, requestType.Other,
                InterfaceInfo.APPLY_FOR_DEPLOY_DELETE, info, deleteDeployFlag);
    }


    @Override
    public void onConnected(Message msg) {
        super.onConnected(msg);
        switch (connectionIndex) {
            case HConnection.RESPONSE_ERROR:
                customerinfo.setNodateStatus("error:\n获取客户信息失败");
                Toast.makeText(this, "获取客户信息失败", Toast.LENGTH_SHORT).show();
                break;

            case customerInfoFlag:
            case addressIndex:
                HResponse res = customerInfoCon
                        .getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (res == null || res.dataJsonObject == null) {
                    Toast.makeText(this, "返回数据为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                JSONObject tmpJsonObject = res.dataJsonObject;
                readJson(tmpJsonObject, connectionIndex);
                break;
            case queryDeployFlag:
                HResponse resDeployQuery = queryDeployCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (resDeployQuery == null || resDeployQuery.dataJsonObject == null) {
                    Toast.makeText(this, "返回数据为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                readJson(resDeployQuery.dataJsonObject, connectionIndex);
                break;
            case createDeployFlag:
                HResponse resDeployCreate = createDeployCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (resDeployCreate == null || resDeployCreate.dataJsonObject == null) {
                    Toast.makeText(this, "返回数据为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                readJson(resDeployCreate.dataJsonObject, connectionIndex);
                break;
            case deleteDeployFlag:
                HResponse resDeployDelete = deleteDeployCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (resDeployDelete == null || resDeployDelete.dataJsonObject == null) {
                    Toast.makeText(this, "返回数据为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                readJson(resDeployDelete.dataJsonObject, connectionIndex);
                break;
        }
    }

    private void readJson(JSONObject tmpJsonObject, int index) {
        String code = Tool.getJsonValue(tmpJsonObject, "retCode");

        if ("0000".equals(code)) {
            if (index == customerInfoFlag) {
                // 将后台返回的数据保存到javabean中去
                String info = tmpJsonObject.toString();
                CMMarketingActivity2CustomerInfo cus = JSON.parseObject(info, CMMarketingActivity2CustomerInfo.class);
                // 对获取到的数据进行展示，和传递到fragment中
                showCutomerInfo(cus);
                customerinfo.setCutomerInfo(isForperson, cus);
            } else if (index == addressIndex) {
                Toast.makeText(this, "地址信息上传成功！", Toast.LENGTH_SHORT).show();
            } else if (index == queryDeployFlag) {
                mDepoyInfo = JSON.parseObject(tmpJsonObject.toString(), QueryDeployInfo.class);
                changeDeployBtnBg(mDepoyInfo.APPLYSTATUS.equals("01") ? true : false);
            } else if (index == createDeployFlag) {
                changeDeployBtnBg(true);
                queryDeploy();
                Toast.makeText(this, tvCustomerName.getText().toString()+"   已发起调配申请", Toast.LENGTH_SHORT).show();
            } else if (index == deleteDeployFlag) {
                changeDeployBtnBg(false);
                queryDeploy();
                Toast.makeText(this, tvCustomerName.getText().toString()+"   取消调配", Toast.LENGTH_SHORT).show();
            }
        } else {
            customerinfo.setNodateStatus("\nerrorCode：" + code);
            Toast.makeText(getApplicationContext(), "错误码：" + code,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void changeDeployBtnBg(boolean isDeoloyed) {
        isCreateDeployNew = !isDeoloyed;
        if (isDeoloyed) {
            btn_apply_for_deploy.setText("已申请调配");
            btn_apply_for_deploy.setBackgroundResource(R.drawable.dialog_bg);
            btn_apply_for_deploy.setTextColor(getResources().getColor(R.color.black));
        } else {
            btn_apply_for_deploy.setText("申请调配");
            btn_apply_for_deploy.setBackgroundResource(R.drawable.bookbuildingbtnbg);
            btn_apply_for_deploy.setTextColor(getResources().getColor(R.color.white));

        }
    }

    /**
     * 设置客户基本信息
     *
     * @param customerinfo2
     */
    private void showCutomerInfo(CMMarketingActivity2CustomerInfo customerinfo2) {
        String custRateValue = customerinfo2.getCUST_VALUE_RATE(); // 价值评级
        if (!TextUtils.isEmpty(custRateValue)) {
            custRateValue = NewCatevalue.getLabel(CMMarketingActivity2.this,
                    NewCatevalue.CUSTVALUELEVEL, custRateValue);
        }
        String cardLeval = customerinfo2.getDEBIT_CARD_LVL_CD();    // 贵宾卡级别
        if (!TextUtils.isEmpty(cardLeval)) {
            cardLeval = NewCatevalue.getLabel(CMMarketingActivity2.this,
                    NewCatevalue.debitCardType, cardLeval);
        }
        mCustID = customerinfo2.getCUSTID();
        mOwnerID = customerinfo2.getOWNERID();
        mOwnerBRID = customerinfo2.getOwner_brid();
//        mOwnerBRID = customerinfo2.getOWNER_BRID();
        showOrHideCustomerPower(customerinfo2.getOWNERID());//隐藏或显示客户购买力
        if (isForperson) {
            String sex = customerinfo2.getIDV_GND_ID();
            if (!TextUtils.isEmpty(sex)) {
                sex = NewCatevalue.getLabel(CMMarketingActivity2.this,
                        NewCatevalue.CONTACT_SEX, sex);
            }//int值不能超过11位
            if (Tool.parseInt(customerinfo2.getCUST_PSN_CARD_NUMBER().substring(16,17))%2!=0) {
                imvPhoto.setImageResource(R.drawable.archiving_person_info_head_man);
            } else  {
                imvPhoto.setImageResource(R.drawable.archiving_person_info_head_women);
            }
            tvCustomerName.setText(customerinfo2.getIDV_CHN_NM());
            tvSex.setText(sex);
            // 生日截取
            String birthday = customerinfo2.getIDV_BRTH_DT_GL();
            if (!TextUtils.isEmpty(birthday)) {
                birthday = birthday.substring(0, birthday.indexOf(" "));
            }
            tvBirthday.setText(birthday);
            tvCard.setText(cardLeval);
            // 风险偏好
            String rskCtrl = customerinfo2.getRSK_CTRL();
            if (!TextUtils.isEmpty(rskCtrl)) {
                rskCtrl = NewCatevalue.getLabel(CMMarketingActivity2.this,
                        NewCatevalue.riskBear, rskCtrl);
            }
            tvStyle.setText(rskCtrl);
            tvLevel.setText(custRateValue);
            tvManager.setText(customerinfo2.getOWNERNAME());
            tvManagerNum.setText(customerinfo2.getOWNERPHONE());
            tvPhone.setText(customerinfo2.getPHONE_NO());
            tvCardID.setText(customerinfo2.getCUST_PSN_CARD_NUMBER());

            // 0不是/1是（是否为本行员工）
            String stff_f = customerinfo2.getSTFF_F();
            if (stff_f == null) {
                stff_f = "";
            } else if (stff_f.equals("0")) {
                stff_f = "否";
            } else if (stff_f.equals("1")) {
                stff_f = "是";
            }
            tvSTFF_F.setText(stff_f);
        } else {
            tvFRXM.setText(customerinfo2.getORG_LGL_NM());
            tvFRDB.setText(customerinfo2.getLGL_PSN_NM());
            tvFRDH.setText(customerinfo2.getLGL_PSN_CON_PHONE());
            tvYYZZ.setText(customerinfo2.getBUSINESS_LICENCE());
            tvJZPJ.setText(custRateValue);
            tvManager.setText(customerinfo2.getUSERNAME());
            tvManagerNum.setText(customerinfo2.getMAIN_CONTACT_PHONE());
        }
        tvOrgName.setText(customerinfo2.getBRNAME());
    }

    /************************************************************************/
    /************************************************************************/

    // 客户信息Fragment
    private void setFragmentCustomerInfo() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (customerinfo == null) {
            customerinfo = new HallCustomerInfoFragment();
            transaction.add(R.id.fm_container, customerinfo);
        } else {
            transaction.show(customerinfo);
        }
        transaction.commitAllowingStateLoss();
    }

    // 客户购买力
    private void setFragmentCustomerPower(String custID) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (customerpower == null) {
            customerpower = new HallCustomerPowerFragment();
            customerpower.setCustID(custID);
            transaction.add(R.id.fm_container, customerpower);
        } else {
            transaction.show(customerpower);
        }
        transaction.commitAllowingStateLoss();
    }

    // 推荐理财产品
    private void setFragmentLiCaiPro(String custID) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (licaipro == null) {
            licaipro = new HallCustomerlicaiproFragment();
            licaipro.setCustID(custID);
            transaction.add(R.id.fm_container, licaipro);
        } else {
            transaction.show(licaipro);
        }
        transaction.commitAllowingStateLoss();
    }

    // 隐藏其他所有的fragment
    private void hideFragments(FragmentTransaction transaction) {
        if (customerinfo != null) {
            transaction.hide(customerinfo);
        }
        if (customerpower != null) {
            transaction.hide(customerpower);
        }
        if (licaipro != null) {
            transaction.hide(licaipro);
        }
    }

    /************************************************************************/
    /************************************************************************/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 客户信息
            case R.id.rbtn_info:
                setFragmentCustomerInfo();
                break;

            // 客户购买力
            case R.id.rbtn_power:
                setFragmentCustomerPower(customerID);
                break;

            // 推荐理财产品
            case R.id.rbtn_product:
                setFragmentLiCaiPro(customerID);
                break;
            case R.id.btn_customerinfo_apply_for_deploy:
                createDeployDialog();
                break;
            case R.id.img_customerinfo_address:
                if (TextUtils.isEmpty(locStr)) {
                    Toast.makeText(this, "正在定位中,请稍后再上传……", Toast.LENGTH_SHORT).show();
                    if (!mLocClient.isStarted()) {
                        mLocClient.start();
                    }
                } else {
                    if (mLocClient.isStarted()) {
                        mLocClient.stop();
                    }
                    sendAddress();
                }
                break;
            default:
                break;
        }
    }

    private void createDeployDialog() {
        if (isCreateDeployNew) {
            DeployDialog dialog = new DeployDialog(this, new DeployDialog.DialogOnClickListener() {
                @Override
                public void deleteStatus(boolean isOKBtn, String reasonID, String reasondes) {
                    if (isOKBtn) {
                        if (isCreateDeployNew) {
                            mReason = reasonID;
                            mCreateDeployDes = reasondes;
                            createDeploy();
                        } else {
                            deleteDeploy();
                        }
                    }
                }
            }, true, mDepoyInfo.APPROVEUSERNAME, mDepoyInfo.APPROVEBRNAME, "", "", "", "");
            dialog.show();
        } else {
            DeployDialog dialog = new DeployDialog(this, new DeployDialog.DialogOnClickListener() {
                @Override
                public void deleteStatus(boolean isOKBtn, String reasonID, String reasondes) {
                    if (isOKBtn) {
                        if (isCreateDeployNew) {
                            mReason = reasonID;
                            mCreateDeployDes = reasondes;
                            createDeploy();
                        } else {
                            deleteDeploy();
                        }
                    }
                }
            }, false, mDepoyInfo.APPROVEUSERNAME, mDepoyInfo.APPROVEBRNAME, mDepoyInfo.APPLYTIME, mDepoyInfo.APPLYSTATUS, mDepoyInfo.APPLYREASON, mDepoyInfo.DES);
            dialog.show();
        }
    }
//上传地址
    private void sendAddress() {
        // TODO Auto-generated method stub
        String CUSTTYPE = "01";// 客户类型(个人)
        if (isForperson) {
            CUSTTYPE = "02";// （对公）
        }

        String CUSTID = customerID;// 客户id
        String LONGITUDE = String.valueOf(ll.longitude);// 经度
        String LATITUDE = String.valueOf(ll.latitude);// 纬度

        String requestStr = "{\"CUSTTYPE\":\"" + CUSTTYPE + "\",\"CUSTID\":\""
                + CUSTID + "\",\"LONGITUDE\":\"" + LONGITUDE
                + "\",\"LATITUDE\":\"" + LATITUDE + "\"}";
        ;

        customerInfoCon = RequestInfo(this, requestType.JsonData,
                InterfaceInfo.CusArchiving_UpAddress, requestStr, addressIndex);
    }

    // 定位监听事件
    private BDLocationListener myListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            locStr = location.getAddrStr();
            ll = new LatLng(location.getLatitude(), location.getLongitude());
        }
    };

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        super.onDestroy();
    }

}
