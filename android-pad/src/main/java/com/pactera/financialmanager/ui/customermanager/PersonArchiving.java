package com.pactera.financialmanager.ui.customermanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;
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

/**
 * 客户建档（个人建档）
 *
 * @author JAY
 */
public class PersonArchiving extends ParentActivity implements OnClickListener {

    TextView PersonName;// 客户姓名
    TextView PersonCardType;// 证件类型
    TextView PersonCardNum;// 证件号码
    TextView PersonPhone;// 联系方式
    // TextView PersonId;// 客户ID
    ImageView personImg;// 头像
    ImageView person_address;// 上传地址

    ImageView[] ArchivingIndex = new ImageView[6];
    int[] imgResource_yellow = {R.drawable.person_1_yellow,
            R.drawable.person_2_yellow, R.drawable.person_3_yellow,
            R.drawable.person_4_yellow, R.drawable.person_5_yellow,
            R.drawable.person_6_yellow};
    int[] imgResource_gray = {R.drawable.person_1_gray,
            R.drawable.person_2_gray, R.drawable.person_3_gray,
            R.drawable.person_4_gray, R.drawable.person_5_gray,
            R.drawable.person_6_gray};

    ImageView[] ArchivingStar = new ImageView[5];
    int[] theStar = {R.id.cusarchiving_person_star_1,
            R.id.cusarchiving_person_star_2, R.id.cusarchiving_person_star_3,
            R.id.cusarchiving_person_star_4, R.id.cusarchiving_person_star_5};

    FragmentManager fragmentManager;
    PersonInfoFragment personInfoFragment;// 个人基本信息
    HomeInfoFragment homeInfoFragment;// 家庭信息
    ContactInfoFragment contactInfoFragment;// 联络人信息
    BusinessFragment businessFragment;// 业务往来
    FinanceNeedFragment financeNeedFragment;// 金融需求
    CustomerFragment customerFragment;// 客户评价

    public final static int PersonInfoIndex = 0;
    public final static int HomeInfoIndex = 1;
    public final static int ContactIndex = 2;
    public final static int BusinessIndex = 3;
    public final static int FinanceNeedIndex = 4;
    public final static int CustomerIndex = 5;
    private HConnection HCon;
    private final int returnIndex = 6;// 查询返回
    private final int addressIndex = 7;// 地址返回
    private int theIndex = 0;// 选择导航

    public static String custName = "";// 姓名
    public static String idv_gnd_id = "";// 性别
    public static String cust_psn_card_type = "";// 证件类型
    public static String cust_psn_card_number = "";// 证件号码
    public static String phone_no = "";// 手机号码
    public static boolean isEdit = true;// 是否可编辑
    public static String custID = "";// 客户ID
    public static String cust_value_rate = "";// 客户星级

    // 定位相关
    private LocationClient mLocClient;
    // 当前位置经纬度
    private static LatLng ll;
    // 当前位置
    private static String locStr;
    private LinearLayout bookbuildingbaseinfoStarLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cusarchiving_preson);
        // 初始化
        findViews();
        setListener();
        setData();
        initTitle(this, "客户建党");
        fragmentManager = getSupportFragmentManager();
        setFragmentPersonInfo();

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

    // 设置个人信息fragment
    private void setFragmentPersonInfo() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (personInfoFragment == null) {
            personInfoFragment = new PersonInfoFragment();
            transaction.add(R.id.cusarchiving_person_frame, personInfoFragment);
        } else {
            transaction.show(personInfoFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    // 设置家庭信息fragment
    private void setFragmentHomeInfo() {
        // TODO Auto-generated method stub
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (homeInfoFragment == null) {
            homeInfoFragment = new HomeInfoFragment();
            transaction.add(R.id.cusarchiving_person_frame, homeInfoFragment);
        } else {
            transaction.show(homeInfoFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    // 设置联系方式fragment
    private void setFragmentContactInfo() {
        // TODO Auto-generated method stub
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (contactInfoFragment == null) {
            contactInfoFragment = new ContactInfoFragment();
            transaction
                    .add(R.id.cusarchiving_person_frame, contactInfoFragment);
        } else {
            transaction.show(contactInfoFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    // 设置业务往来fragment
    private void setFragmentBusinessInfo() {
        // TODO Auto-generated method stub
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (businessFragment == null) {
            businessFragment = new BusinessFragment();
            transaction.add(R.id.cusarchiving_person_frame, businessFragment);
        } else {
            transaction.show(businessFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    // 设置金融服务fragment
    private void setFragmentFinanceNeed() {
        // TODO Auto-generated method stub
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (financeNeedFragment == null) {
            financeNeedFragment = new FinanceNeedFragment();
            transaction
                    .add(R.id.cusarchiving_person_frame, financeNeedFragment);
        } else {
            transaction.show(financeNeedFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    // 设置客户评价fragment
    private void setFragmentCustomer() {
        // TODO Auto-generated method stub
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (customerFragment == null) {
            customerFragment = new CustomerFragment();
            transaction.add(R.id.cusarchiving_person_frame, customerFragment);
        } else {
            transaction.show(customerFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    private void hideFragments(FragmentTransaction transaction) {
        // TODO Auto-generated method stub
        if (personInfoFragment != null) {
            transaction.hide(personInfoFragment);
        }
        if (homeInfoFragment != null) {
            transaction.hide(homeInfoFragment);
        }
        if (contactInfoFragment != null) {
            transaction.hide(contactInfoFragment);
        }
        if (businessFragment != null) {
            transaction.hide(businessFragment);
        }
        if (financeNeedFragment != null) {
            transaction.hide(financeNeedFragment);
        }
        if (customerFragment != null) {
            transaction.hide(customerFragment);
        }
    }

    private void findViews() {

        PersonName = (TextView) findViewById(R.id.cusarchiving_person_name);// 客户姓名
        PersonCardType = (TextView) findViewById(R.id.cusarchiving_person_cardtype);// 证件类型
        PersonCardNum = (TextView) findViewById(R.id.cusarchiving_person_cardnum);// 证件号码
        PersonPhone = (TextView) findViewById(R.id.cusarchiving_person_phone);// 联系方式
        for (int i = 0; i < ArchivingStar.length; i++) {
            ArchivingStar[i] = (ImageView) findViewById(theStar[i]);// 客户星级
        }
        person_address = (ImageView) findViewById(R.id.cusarchiving_person_address);
        bookbuildingbaseinfoStarLl = (LinearLayout) findViewById(R.id.bookbuildingbaseinfo_tar_ll);
        ArchivingIndex[0] = (ImageView) findViewById(R.id.cusarchiving_person_index_1);
        ArchivingIndex[1] = (ImageView) findViewById(R.id.cusarchiving_person_index_2);
        ArchivingIndex[2] = (ImageView) findViewById(R.id.cusarchiving_person_index_3);
        ArchivingIndex[3] = (ImageView) findViewById(R.id.cusarchiving_person_index_4);
        ArchivingIndex[4] = (ImageView) findViewById(R.id.cusarchiving_person_index_5);
        ArchivingIndex[5] = (ImageView) findViewById(R.id.cusarchiving_person_index_6);
        personImg = (ImageView) findViewById(R.id.cusarchiving_person_img);

        int ImgWidth = Tool.getScreenWidth(this) * 40 / 1280; // 宽度

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ImgWidth,
                ImgWidth);
        person_address.setLayoutParams(lp);
    }

    // 获取客户信息
    private void setData() {
        // TODO Auto-generated method stub
        custName = "";// 姓名
        idv_gnd_id = "";// 性别
        cust_psn_card_type = "";// 证件类型
        cust_psn_card_number = "";// 证件号码
        phone_no = "";// 手机号码
        cust_value_rate = "";// 客户星级
        custID = "";

        Intent intent = getIntent(); // 获取客户ID
        custID = intent.getStringExtra("custID");
        HCon = RequestInfo(this, requestType.Search,
                InterfaceInfo.PersonArchiving_Get, custID, returnIndex);

    }

    // 注册事件
    private void setListener() {
        for (int i = 0; i < ArchivingIndex.length; i++) {
            ArchivingIndex[i].setOnClickListener(this);
        }
        person_address.setOnClickListener(this);
    }

    @Override
    public void onConnected(Message msg) {
        super.onConnected(msg);
        switch (connectionIndex) {
            case HConnection.RESPONSE_ERROR:
                break;
            case PersonInfoIndex:
                setFragmentHomeInfo();
                theIndex = 1;
                setIndex(theIndex);
                break;
            case HomeInfoIndex:
                setFragmentContactInfo();
                theIndex = 2;
                setIndex(theIndex);
                break;
            case ContactIndex:
                setFragmentBusinessInfo();
                theIndex = 3;
                setIndex(theIndex);
                break;
            case BusinessIndex:
                setFragmentFinanceNeed();
                theIndex = 4;
                setIndex(theIndex);
                break;
            case FinanceNeedIndex:
                setFragmentCustomer();
                theIndex = 5;
                setIndex(theIndex);
                break;
            case CustomerIndex:
                Toast.makeText(this, "完成客户建档", Toast.LENGTH_LONG).show();
                finish();
            case Constants.PHOTO_DELETE:
                Message theMsg = CustomerFragment.theCustomerFragment.handler
                        .obtainMessage();
                Bundle deleteData = new Bundle();
                deleteData.putString("value", "" + CustomerFragment.DeleteInfo);
                theMsg.setData(deleteData);
                CustomerFragment.theCustomerFragment.handler.sendMessage(theMsg);
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
        }
    }

    // 解析返回json
    private void readJson(JSONObject tmpJsonObject, int index) {
        // TODO Auto-generated method stub
        String retCode = Tool.getJsonValue(tmpJsonObject, "retCode");

        if (index == returnIndex) {
            if (retCode.equals("0000")) {
                JSONArray theInfo;
                try {
                    theInfo = tmpJsonObject.getJSONArray("group");
                    if (theInfo.length() < 0) {
                        return;
                    } else {

                        JSONObject temps = (JSONObject) theInfo.opt(0);
                        custName = Tool.getJsonValue(temps, "custName");// 姓名

                        idv_gnd_id = Tool.getJsonValue(temps, "idv_gnd_id");// 性别
                        cust_psn_card_type = Tool.getJsonValue(temps,
                                "cust_psn_card_type");// 证件类型
                        cust_psn_card_number = Tool.getJsonValue(temps,
                                "cust_psn_card_number");// 证件号码
                        phone_no = Tool.getJsonValue(temps, "phone_no");// 手机号码
                        cust_value_rate = Tool.getJsonValue(temps,
                                "cust_value_rate");// 客户星级
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            PersonName.setText(custName);
            String cardType = NewCatevalue.getLabel(this, NewCatevalue.CERTTP,
                    cust_psn_card_type);

            PersonCardType.setText(cardType);
            PersonPhone.setText(phone_no);
            PersonCardNum.setText(cust_psn_card_number);

            setStar(cust_value_rate);

            // 设置性别显示头像
        /*	List<CatevalueEntity> sexInfo = new ArrayList<CatevalueEntity>();
            sexInfo = NewCatevalue.getCatevalue(this, NewCatevalue.CONTACT_SEX);
			for (CatevalueEntity theEntity : sexInfo) {
				if (idv_gnd_id.equals(theEntity.getValue())) {
					if (theEntity.getLabel().equals("女")) {
						personImg
								.setImageResource(R.drawable.archiving_person_info_head_women);
					} else if (theEntity.getLabel().equals("男")) {
						personImg
								.setImageResource(R.drawable.archiving_person_info_head_man);
					} else {
						personImg
								.setImageResource(R.drawable.archiving_person_info_head_unknown);
					}
					break;
				}
			}*/
            if (Tool.parseInt(cust_psn_card_number.substring(16, 17)) % 2 != 0) {
                personImg.setImageResource(R.drawable.archiving_person_info_head_man);
            } else {
                personImg.setImageResource(R.drawable.archiving_person_info_head_women);
            }

        } else if (index == addressIndex) {
            if (retCode.equals("0000")) {
                Toast.makeText(this, "地址信息上传成功！", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "地址信息上传失败，请重新上传！", Toast.LENGTH_LONG)
                        .show();

            }
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.cusarchiving_person_index_1:
                setFragmentPersonInfo();
                theIndex = 0;
                setIndex(theIndex);
                break;
            case R.id.cusarchiving_person_index_2:
                setFragmentHomeInfo();
                theIndex = 1;
                setIndex(theIndex);
                break;
            case R.id.cusarchiving_person_index_3:
                setFragmentContactInfo();
                theIndex = 2;
                setIndex(theIndex);
                break;
            case R.id.cusarchiving_person_index_4:
                setFragmentBusinessInfo();
                theIndex = 3;
                setIndex(theIndex);
                break;
            case R.id.cusarchiving_person_index_5:
                setFragmentFinanceNeed();
                theIndex = 4;
                setIndex(theIndex);
                break;
            case R.id.cusarchiving_person_index_6:
                setFragmentCustomer();
                theIndex = 5;
                setIndex(theIndex);
                break;
            case R.id.cusarchiving_person_address:
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
    }

    // 上传地址
    private void sendAddress() {
        String CUSTTYPE = "01";// 客户类型
        String CUSTID = custID;// 客户id
        String LONGITUDE = String.valueOf(ll.longitude);// 经度
        String LATITUDE = String.valueOf(ll.latitude);// 纬度

        String requestStr = "{\"CUSTTYPE\":\"" + CUSTTYPE + "\",\"CUSTID\":\""
                + CUSTID + "\",\"LONGITUDE\":\"" + LONGITUDE
                + "\",\"LATITUDE\":\"" + LATITUDE + "\"}";

        HCon = RequestInfo(this, requestType.JsonData,
                InterfaceInfo.CusArchiving_UpAddress, requestStr, addressIndex);
    }

    /**
     * 设置导航图片显示
     *
     * @param index
     */
    private void setIndex(int index) {
        for (int i = 0; i < ArchivingIndex.length; i++) {
            if (i <= index) {
                ArchivingIndex[i].setImageResource(imgResource_yellow[i]);
            } else {
                ArchivingIndex[i].setImageResource(imgResource_gray[i]);
            }
        }
    }

    /**
     * 显示星级
     * 0-5个星级 返回为0时默认灰色其它依次亮色星星为背景
     *
     * @param index
     */
    private void setStar(String index) {
        int starIndex = 0;
        if (index.equals("01") || index.equals("1")) {
            starIndex = 1;
        } else if (index.equals("02") || index.equals("2")) {
            starIndex = 2;
        } else if (index.equals("03") || index.equals("3")) {
            starIndex = 3;
        } else if (index.equals("04") || index.equals("4")) {
            starIndex = 4;
        } else if (index.equals("05") || index.equals("5")) {
            starIndex = 5;
        } else if (index.equals("0") || index.equals("00")) {
            for (int i = 0; i <= 4; i++) {
                ArchivingStar[i].setVisibility(View.VISIBLE);
            }
        } else if (index.equals("")||index==null) {
            bookbuildingbaseinfoStarLl.setVisibility(View.GONE);
        }
        for (int i = 0; i < starIndex; i++) {
            ArchivingStar[i].setImageResource(R.drawable.archiving_person_star);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (data != null)
                Log.i("info", "data0=" + data.getData());
            switch (requestCode) {
                case Constants.TAKE_PICTURE_FROM_CAMERA:
                    BitmapUtils.startPhotoZoom(this,
                            BitmapUtils.getLastGenerateUri());
                    break;
                case Constants.TAKE_PICTURE_FROM_PHOTO_ALBUM:
                    BitmapUtils.startPhotoZoom(this, data.getData());
                    break;
                case Constants.PHOTO_ZOOM:
                    if (data != null) {
                        Message msg = CustomerFragment.theCustomerFragment.handler
                                .obtainMessage();
                        Bundle theData = new Bundle();
                        theData.putParcelable("data", data.getData());
                        theData.putString("value", "" + CustomerFragment.PiCInfo);
                        msg.setData(theData);
                        CustomerFragment.theCustomerFragment.handler
                                .sendMessage(msg);
                    }
                    break;
            }
        }
    }

    // 对私转换JSON数据
    public static String toJsonForPerson() {
        return "{\"custName\":\"" + custName + "\",\"idv_gnd_id\":\""
                + idv_gnd_id + "\",\"cust_psn_card_type\":\""
                + cust_psn_card_type + "\",\"cust_psn_card_number\":\""
                + cust_psn_card_number + "\",\"phone_no\":\"" + phone_no
                + "\",\"custID\":\"" + custID + "\"}";

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
    public void onDestroy() {
        super.onDestroy();
        // 退出时销毁定位
        mLocClient.stop();
    }
}
