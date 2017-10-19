package com.pactera.financialmanager.ui.bookbuildingfrocompany;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.callback.WorkPlaceItemChange;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.customermanager.CustomerFragment;
import com.pactera.financialmanager.ui.fragmentbookbuilding.FragmentBookBuildingBaseInfo;
import com.pactera.financialmanager.ui.fragmentbookbuilding.FragmentBookBuildingBusiness;
import com.pactera.financialmanager.ui.fragmentbookbuilding.FragmentBookBuildingBusinessComeGo;
import com.pactera.financialmanager.ui.fragmentbookbuilding.FragmentBookBuildingContactInfo;
import com.pactera.financialmanager.ui.fragmentbookbuilding.FragmentBookBuildingCustomerRelation;
import com.pactera.financialmanager.ui.fragmentbookbuilding.FragmentBookBuildingFinancialServieces;
import com.pactera.financialmanager.ui.fragmentbookbuilding.FragmentBookBuildingOtherinfo;
import com.pactera.financialmanager.ui.fragmentbookbuilding.FragmentBookBuildingUploadPic;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.BitmapUtils;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.Constants.requestType;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 这是对公客户建档的 客户信息 的activity
 */
public class ActivityBaseInfo extends ParentActivity implements
        WorkPlaceItemChange, OnClickListener {

    private FragmentManager fragmentManager;
    private FragmentBookBuildingBaseInfo fragmentbaseinfo;
    private FragmentBookBuildingBusiness fragmentbusiness;
    private FragmentBookBuildingOtherinfo fragmentotherinfo;
    private FragmentBookBuildingContactInfo fragmentcontactinfo;
    private FragmentBookBuildingCustomerRelation fragmentcustomerrelation;
    private FragmentBookBuildingBusinessComeGo fragmentbusinesscomgo;
    private FragmentBookBuildingFinancialServieces fragmentfinancialservices;
    private FragmentBookBuildingUploadPic fragmentuploadpic;
    int[] imgResource_yellow = {R.drawable.person_1_yellow,
            R.drawable.company_3_yellow, R.drawable.company_4_yellow,
            R.drawable.company_5_yellow, R.drawable.company_6_yellow};
    int[] imgResource_gray = {R.drawable.person_1_gray,
            R.drawable.company_3_gray, R.drawable.company_4_gray,
            R.drawable.company_5_gray, R.drawable.company_6_gray};
    private ImageView[] imgNavigation = new ImageView[5];
    private int index;// 当前所在的步
    private TextView baseinfo_companyname;// 公司名称
    //	private TextView baseinfo_star;// 客户评级
    private TextView baseinfo_name;// 联络人姓名
    private TextView baseinfo_phone;// 联络人电话
    private TextView baseinfo_buildname;// 证件类型
    private TextView baseinfo_buildtime;// 证件号码

    public static String custName = "";// 姓名
    public static String cust_psn_card_type = "";// 证件类型
    public static String cust_psn_card_number = "";// 证件号码
    public static String CONTACTNAME = "";// 联系人姓名
    public static String phone_no = "";// 手机号码
    public static boolean isEdit = true;// 是否可编辑
    public static String custID = "";// 客户ID
    public static String cust_value_rate = "";// 客户星级
    public static RatingBar rating;

    private HConnection HCon;
    private final int returnIndex = 1;// 查询返回
    private final int addressIndex = 2;// 地址返回
    // 定位相关
    private LocationClient mLocClient;
    // 当前位置经纬度
    private static LatLng ll;
    // 当前位置
    private static String locStr;
    ImageView company_address;// 上传地址


    ImageView[] ArchivingStar = new ImageView[5];
    int[] theStar = {R.id.cusarchiving_person_star_1,
            R.id.cusarchiving_person_star_2, R.id.cusarchiving_person_star_3,
            R.id.cusarchiving_person_star_4, R.id.cusarchiving_person_star_5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookbuildingbaseinfo);
        initTitle(this, R.drawable.customermanagercon);

        fragmentManager = getSupportFragmentManager();
        setupView();
        setListener();
        setData();
        /**
         * 测试之用的，先注释一下
         */
        setDeafultFragment();
        findDefaultScreen();

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

    private void setListener() {
        for (int i = 0; i < imgNavigation.length; i++) {
            imgNavigation[i].setOnClickListener(this);
        }
        company_address.setOnClickListener(this);
    }

    @SuppressWarnings("deprecation")
    private void findDefaultScreen() {
        WindowManager manager = getWindowManager();
        int width = manager.getDefaultDisplay().getWidth();
        int height = manager.getDefaultDisplay().getHeight();
        Constants.SCREEN_WIDTH = width;
        Constants.SCREEN_HEIGHT = height;
    }

    private void setupView() {
        // tvCompanyName=(TextView)findViewById(R.id.tv_companyname);
        // tvStyle=(TextView)findViewById(R.id.tv_style);
        // tvStleNum=(TextView)findViewById(R.id.tv_stylenum);
        // tvName=(TextView)findViewById(R.id.tv_name);
        // tvNumber=(TextView)findViewById(R.id.tv_nubmer);
        imgNavigation[0] = (ImageView) findViewById(R.id.img_navigationicon1);
        imgNavigation[1] = (ImageView) findViewById(R.id.img_navigationicon2);
        imgNavigation[2] = (ImageView) findViewById(R.id.img_navigationicon3);
        imgNavigation[3] = (ImageView) findViewById(R.id.img_navigationicon4);
        imgNavigation[4] = (ImageView) findViewById(R.id.img_navigationicon5);

        baseinfo_companyname = (TextView) findViewById(R.id.bookbuildingbaseinfo_companyname);// 公司名称
        for (int i = 0; i < ArchivingStar.length; i++) {
            ArchivingStar[i] = (ImageView) findViewById(theStar[i]);// 客户星级
        }
//		baseinfo_star = (TextView) findViewById(R.id.bookbuildingbaseinfo_star);// 客户评级
        baseinfo_name = (TextView) findViewById(R.id.bookbuildingbaseinfo_name);// 联络人姓名
        baseinfo_phone = (TextView) findViewById(R.id.bookbuildingbaseinfo_phone);// 联络人电话
        baseinfo_buildname = (TextView) findViewById(R.id.bookbuildingbaseinfo_buildname);// 证件类型
        baseinfo_buildtime = (TextView) findViewById(R.id.bookbuildingbaseinfo_buildtime);// 证件号码
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        baseinfo_buildtime.setText(formatter.format(curDate));

        company_address = (ImageView) findViewById(R.id.cusarchiving_company_address);
        int ImgWidth = Tool.getScreenWidth(this) * 40 / 1280; // 宽度

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ImgWidth, ImgWidth);
        company_address.setLayoutParams(lp);
    }

    // 获取客户信息
    private void setData() {
        // TODO Auto-generated method stub
        custName = "";// 姓名
        cust_psn_card_type = "";// 证件类型
        cust_psn_card_number = "";// 证件号码
        phone_no = "";// 手机号码
        cust_value_rate = "";// 客户星级
        CONTACTNAME = "";// 联系人姓名
        custID = "";
        Intent intent = getIntent(); // 获取客户ID
        custID = intent.getStringExtra("custID");
        HCon = RequestInfo(this, requestType.Search,
                InterfaceInfo.ActivityBaseInfo_Get, custID, returnIndex);
    }

    @Override
    public void onConnected(Message msg) {
        super.onConnected(msg);
        switch (connectionIndex) {
            case HConnection.RESPONSE_ERROR:
                break;
            case returnIndex:
            case addressIndex:
                HResponse res = HCon
                        .getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (res == null || res.dataJsonObject == null) {
                    return;
                }
                JSONObject tmpJsonObject = res.dataJsonObject;
                readJson(tmpJsonObject, connectionIndex);
                break;
            case Constants.PHOTO_DELETE:
                Message theMsg = FragmentBookBuildingUploadPic.bookBuildingUploadPic.handler
                        .obtainMessage();
                Bundle deleteData = new Bundle();
                deleteData.putString("value", ""
                        + FragmentBookBuildingUploadPic.DeleteInfo);
                theMsg.setData(deleteData);
                FragmentBookBuildingUploadPic.bookBuildingUploadPic.handler
                        .sendMessage(theMsg);
                break;
        }
    }

    // 解析返回json
    private void readJson(JSONObject tmpJsonObject, int index) {
        // TODO Auto-generated method stub
        String retCode = Tool.getJsonValue(tmpJsonObject, "retCode");
        if (retCode.equals("0000")) {


            if (index == returnIndex) {

                JSONArray theInfo;

                try {
                    theInfo = tmpJsonObject.getJSONArray("group");
                    if (theInfo.length() < 0) {
                        return;
                    } else {
                        JSONObject temps = (JSONObject) theInfo.opt(0);
                        custName = Tool.getJsonValue(temps, "ENAME");// 姓名

                        CONTACTNAME = Tool.getJsonValue(temps, "CONTACTNAME");// 联系人姓名
                        cust_psn_card_type = Tool.getJsonValue(temps, "IDENTITYTYPE");// 证件类型
                        cust_psn_card_number = Tool.getJsonValue(temps, "IDENTITYNUM");// 证件号码
                        phone_no = Tool.getJsonValue(temps, "PHONENUM");// 手机号码
                        cust_value_rate = Tool.getJsonValue(temps, "CUST_VALUE_RATE");// 客户星级
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                baseinfo_companyname.setText(custName);// 公司名称
//			baseinfo_star.setText(cust_value_rate);// 客户评级
                if (TextUtils.isEmpty(cust_value_rate))
                    setStar("01");
                else
                    setStar(cust_value_rate);

                baseinfo_name.setText(CONTACTNAME);// 联络人姓名
                baseinfo_phone.setText(phone_no);// 联络人电话
                baseinfo_buildname.setText(TextUtils.isEmpty(cust_psn_card_type) ? "" : NewCatevalue.getLabel(this, NewCatevalue.C_CERTTP, cust_psn_card_type));
                baseinfo_buildtime.setText(cust_psn_card_number);// 证件号码
//			if (LogoActivity.user != null) {
//				baseinfo_buildname.setText(LogoActivity.user.getUsername());
//			}
                // baseinfo_buildtime.setText();//建档时间
            }

        } else if (index == addressIndex) {
            Toast.makeText(this, "地址信息上传成功！", Toast.LENGTH_SHORT)
                    .show();
        }

    }


    /**
     * 显示星级
     *
     * @param index
     */
    private void setStar(String index) {
        int starIndex = 0;
        if (index.equals("01")) {
            starIndex = 1;
        } else if (index.equals("02")) {
            starIndex = 2;
        } else if (index.equals("03")) {
            starIndex = 3;
        } else if (index.equals("04")) {
            starIndex = 4;
        } else if (index.equals("05")) {
            starIndex = 5;
        }
        for (int i = 0; i < starIndex; i++) {
            Log.i("1111111", "starIndex:" + starIndex + ",i=" + i);
            ArchivingStar[i].setImageResource(R.drawable.archiving_person_star);
        }
    }


    private void setBusinessFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideSonsFragment(transaction);
        if (fragmentbusiness == null) {
            fragmentbusiness = new FragmentBookBuildingBusiness();
            transaction.add(R.id.fl_bookbuilding_fragment, fragmentbusiness);
        } else {
            transaction.show(fragmentbusiness);
        }
        transaction.commitAllowingStateLoss();
    }

    private void setDeafultFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideSonsFragment(transaction);
        if (fragmentbaseinfo == null) {
            fragmentbaseinfo = new FragmentBookBuildingBaseInfo();
            transaction.add(R.id.fl_bookbuilding_fragment, fragmentbaseinfo);
        } else {
            transaction.show(fragmentbaseinfo);
        }

        transaction.commitAllowingStateLoss();
    }

    private void setOtherinfoFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideSonsFragment(transaction);
        if (fragmentotherinfo == null) {
            fragmentotherinfo = new FragmentBookBuildingOtherinfo();
            transaction.add(R.id.fl_bookbuilding_fragment, fragmentotherinfo);
        } else {
            transaction.show(fragmentotherinfo);
        }

        transaction.commitAllowingStateLoss();
    }

    // 创建客户联络信息的
    // private void setContactinfoFragment() {
    // FragmentTransaction transaction = fragmentManager.beginTransaction();
    // hideSonsFragment(transaction);
    // if(fragmentcontactinfo==null){
    // fragmentcontactinfo=new FragmentBookBuildingContactInfo();
    // transaction.add(R.id.fl_bookbuilding_fragment, fragmentcontactinfo);
    // }else{
    // transaction.show(fragmentcontactinfo);
    // }
    // transaction.commitAllowingStateLoss();
    // }

    private void setCustomerRelationFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideSonsFragment(transaction);
        if (fragmentcustomerrelation == null) {
            fragmentcustomerrelation = new FragmentBookBuildingCustomerRelation();
            transaction.add(R.id.fl_bookbuilding_fragment,
                    fragmentcustomerrelation);
        } else {
            transaction.show(fragmentcustomerrelation);
        }
        transaction.commitAllowingStateLoss();
    }

    private void setBusinessComGoFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideSonsFragment(transaction);
        if (fragmentbusinesscomgo == null) {
            fragmentbusinesscomgo = new FragmentBookBuildingBusinessComeGo();
            transaction.add(R.id.fl_bookbuilding_fragment,
                    fragmentbusinesscomgo);
        } else {
            transaction.show(fragmentbusinesscomgo);
        }
        transaction.commitAllowingStateLoss();
    }

    private void setFinancialServices() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideSonsFragment(transaction);
        if (fragmentfinancialservices == null) {
            fragmentfinancialservices = new FragmentBookBuildingFinancialServieces();
            transaction.add(R.id.fl_bookbuilding_fragment,
                    fragmentfinancialservices);
        } else {
            transaction.show(fragmentfinancialservices);
        }
        transaction.commitAllowingStateLoss();
    }

    private void setUploadCustomerPic() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideSonsFragment(transaction);
        if (fragmentuploadpic == null) {
            fragmentuploadpic = new FragmentBookBuildingUploadPic();
            transaction.add(R.id.fl_bookbuilding_fragment, fragmentuploadpic);
        } else {
            transaction.show(fragmentuploadpic);
        }
        transaction.commitAllowingStateLoss();
    }

    private void hideSonsFragment(FragmentTransaction transaction) {
        if (fragmentbaseinfo != null) {
            transaction.hide(fragmentbaseinfo);
        }
        if (fragmentbusiness != null) {
            transaction.hide(fragmentbusiness);
        }
        if (fragmentotherinfo != null) {
            transaction.hide(fragmentotherinfo);
        }
        if (fragmentcontactinfo != null) {
            transaction.hide(fragmentcontactinfo);
        }
        if (fragmentcustomerrelation != null) {
            transaction.hide(fragmentcustomerrelation);
        }
        if (fragmentbusinesscomgo != null) {
            transaction.hide(fragmentbusinesscomgo);
        }
        if (fragmentfinancialservices != null) {
            transaction.hide(fragmentfinancialservices);
        }
        if (fragmentuploadpic != null) {
            transaction.hide(fragmentuploadpic);
        }
    }

    @Override
    public void workPlacestyleChange(int tag) {
        switch (tag) {
            case 0:// 这是进入到负债信息的界面的回调
                setBusinessFragment();
                index = 1;
                break;
            case 1:// 这是进入到基本信息的界面的回调
                setDeafultFragment();
                break;
            case 2:
                // setContactinfoFragment();
                setCustomerRelationFragment();
                index = 2;
                break;
            case 3:
                setBusinessComGoFragment();
                index = 3;
                break;
            case 4:
                setFinancialServices();
                index = 4;
                break;
            case 5:
                setUploadCustomerPic();
                index = 5;
                break;
            default:
                break;
        }
        changeNavigationIcon();
    }

    public FragmentManager getActivityFragmentManager() {
        return fragmentManager;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (data != null)
                Log.i("info", "data0=" + data.getData());
            switch (requestCode) {
                case Constants.TAKE_PICTURE_FROM_CAMERA:
                    BitmapUtils.startPhotoZoom(this, BitmapUtils.getLastGenerateUri());
                    break;
                case Constants.TAKE_PICTURE_FROM_PHOTO_ALBUM:
                    BitmapUtils.startPhotoZoom(this, data.getData());
                    break;
                case Constants.PHOTO_ZOOM:
                    if (data != null) {
                        Message msg = FragmentBookBuildingUploadPic.bookBuildingUploadPic.handler.obtainMessage();
                        Bundle theData = new Bundle();
                        theData.putParcelable("data", data.getData());
                        theData.putString("value", "" + CustomerFragment.PiCInfo);
                        msg.setData(theData);
                        FragmentBookBuildingUploadPic.bookBuildingUploadPic.handler.sendMessage(msg);
                    }
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        fragmentManager = null;

        // 退出时销毁定位
        mLocClient.stop();

        Log.i("1111111", "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        fragmentManager = null;
        super.onStop();
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        super.onRestart();
    }

    @Override
    public void workMyreportPersonChange(int tag, boolean isForperson) {

    }

    @Override
    public void workPlacestyleChange(int tag, boolean isForPerson) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_navigationicon1:
                setDeafultFragment();
                index = 1;
                break;
            case R.id.img_navigationicon2:
                setCustomerRelationFragment();
                index = 2;
                break;
            case R.id.img_navigationicon3:
                setBusinessComGoFragment();
                index = 3;
                break;
            case R.id.img_navigationicon4:
                setFinancialServices();
                index = 4;
                break;
            case R.id.img_navigationicon5:
                setUploadCustomerPic();
                index = 5;
                break;
            case R.id.cusarchiving_company_address:
                if (TextUtils.isEmpty(locStr)) {
                    Toast.makeText(this, "正在定位中,请稍后再上传……", Toast.LENGTH_SHORT)
                            .show();
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
        changeNavigationIcon();
    }

    // 上传地址
    private void sendAddress() {
        // TODO Auto-generated method stub
        String CUSTTYPE = "02";// 客户类型
        String CUSTID = custID;// 客户id
        String LONGITUDE = String.valueOf(ll.longitude);// 经度
        String LATITUDE = String.valueOf(ll.latitude);// 纬度

        String requestStr = "{\"CUSTTYPE\":\"" + CUSTTYPE + "\",\"CUSTID\":\""
                + CUSTID + "\",\"LONGITUDE\":\"" + LONGITUDE
                + "\",\"LATITUDE\":\"" + LATITUDE + "\"}";
        ;

        HCon = RequestInfo(this, requestType.JsonData,
                InterfaceInfo.CusArchiving_UpAddress, requestStr, addressIndex);
    }

    private void changeNavigationIcon() {
        for (int i = 0; i < imgNavigation.length; i++) {
            if (i < index) {
                imgNavigation[i].setImageResource(imgResource_yellow[i]);
            } else {
                imgNavigation[i].setImageResource(imgResource_gray[i]);
            }
        }
    }

    // 对公
    public static String toJsonForCompany() {
        return "{\"ENAME\":\"" + custName + "\",\"CONTACTNAME\":\""
                + CONTACTNAME + "\",\"IDENTITYTYPE\":\"" + cust_psn_card_type
                + "\",\"IDENTITYNUM\":\"" + cust_psn_card_number
                + "\",\"PHONENUM\":\"" + phone_no + "\",\"custID\":\"" + custID
                + "\"}";

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

}
