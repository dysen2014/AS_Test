package com.pactera.financialmanager.ui.commontool;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.poi.BaiduMapPoiSearch;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.adapter.SpinnerAdapter;
import com.pactera.financialmanager.adapter.SpinnerAdapter.CallBackItemClickListener;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.model.CustomerMapInfo;
import com.pactera.financialmanager.ui.model.QueryOneCusInfo;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HRequest;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;
import com.pactera.financialmanager.util.Constants.requestType;

import static com.baidu.mapapi.map.MapStatusUpdateFactory.newLatLng;

/**
 * 客户分布
 *
 * @author JAY
 */
public class CustomerMapActivity extends ParentActivity implements
        OnClickListener {

    private WebView customerWebView;// 客户分布web
    private Dialog thePopWeb;// 弹出框（hopmap）
    private Dialog thePopCus;// 弹出框（单一客户查询）
    private LayoutInflater factory;
    private ImageView customemap_close;
    private MapView custome_bmapView;
    private Button custome_hotWeb;
    private RadioGroup rgp_check_cus;// 对公对私切换按钮
    private String indexCheck = "01";// 对私（1），对公（2）
    private Button customemap_querycus;// 查询我的管辖客户信息
    private Button customemap_thecus;// 单一客户查询

    private TextView[] customermap_tv = new TextView[10];
    private int[] customermap_tv_id = {R.id.customermap_tv_1,
            R.id.customermap_tv_2, R.id.customermap_tv_3,
            R.id.customermap_tv_4, R.id.customermap_tv_5,
            R.id.customermap_tv_6, R.id.customermap_tv_7,
            R.id.customermap_tv_8, R.id.customermap_tv_9,
            R.id.customermap_tv_10};
    ArrayList<CustomerMapInfo> customerMapInfo = new ArrayList<CustomerMapInfo>();
    ArrayList<CustomerMapInfo> customerQueryInfo = new ArrayList<CustomerMapInfo>();
    ArrayList<CustomerMapInfo> tempMapInfo = new ArrayList<CustomerMapInfo>();

    List<Marker> ooMarker = new ArrayList<Marker>();
    QueryCustomerAdapter cusAdapter;
    private InfoWindow infoWindow;// 显示信息框
    private View theInfoView;// 信息view
    private TextView infoName, InfoPhone, InfoCardType, InfoCardNo;

    /**
     * 单一客户查询
     */
    private EditText querycustome_name;// 客户姓名
    private EditText querycustome_phone;// 客户电话
    private TextView querycustome_cardtype;// 客户证件类型
    private EditText querycustome_cardnum;// 客户证件号码
    private Button querycustome_btn;// 查询按钮
    private Button querycustome_clear;// 清除按钮
    private ListView querycustome_list;// 信息列表
    private String cardTypeNum;// 证件类型码值
    private ImageView querycustome_close;// 关闭按钮

    // 信息框
    private Button customermap_num_btn;
    private LinearLayout customermap_ll;

    private HConnection QueryCusHCon;
    private HConnection QueryOneCusHCon;
    private final int queryCusIndex = 1;
    private final int queryOneCusIndex = 2;

    // 地图管理
    private BaiduMap mBaiduMap;
    // 定位相关
    private LocationClient mLocClient;
    // 当前位置经纬度
    private LatLng ll;
    // 定位监听事件
    public MyLocationListenner myListener = new MyLocationListenner();

    // 图标
    BitmapDescriptor icon_maker = BitmapDescriptorFactory
            .fromResource(R.drawable.map_coordinate);
    // 图标
    BitmapDescriptor icon_maker_blue = BitmapDescriptorFactory
            .fromResource(R.drawable.map_coordinate_select);
    private MySDKBrodcastReceiver mySDKBrodcastReceiver;
    private Button customemap_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_customemap);
        initTitle(this, R.drawable.changyonggongju);
        // 初始化控件
        setView();
        initListenner();
        custome_bmapView.showScaleControl(false);//隐藏比例尺
//        custome_bmapView.showZoomControls(false);//隐藏缩放图标
        // 获取地图控制
        mBaiduMap = custome_bmapView.getMap();
        // 设置普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        // 显示地图建筑
        mBaiduMap.setBuildingsEnabled(true);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        IntentFilter filter = new IntentFilter();
        filter.addAction(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE);
        filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mySDKBrodcastReceiver = new MySDKBrodcastReceiver();
        registerReceiver(mySDKBrodcastReceiver, filter);
        // option.setOpenGps(true);// 打开gps
        option.setIsNeedAddress(true);// 设置是否需要地址信息，默认为无地址
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setAddrType("all");
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        if (!mLocClient.isStarted()) {
            mLocClient.start();// 开始定位
        }
        //点击标记后弹出的框
        mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker markerInfo) {
                if (infoWindow != null) {
                    mBaiduMap.hideInfoWindow();
                }
                for (Marker itemMarker : ooMarker) {
                    itemMarker.setIcon(icon_maker);
                }
                custome_bmapView.refreshDrawableState();

                int markerIndex = markerInfo.getZIndex();
                markerInfo.setIcon(icon_maker_blue);
                Log.i("1111111", "" + markerIndex);
                for (CustomerMapInfo item : tempMapInfo) {
                    if (item.MapInfoInfex == markerIndex) {

                        double lati = Double.parseDouble(item.LATITUDE);
                        double loti = Double.parseDouble(item.LONGITUDE);
                        LatLng llNew = new LatLng(lati, loti);

                        // 显示信息
                        infoName.setText(item.CUSTNAME);
                        InfoPhone.setText(item.PHONE_NO);
                        if (!TextUtils.isEmpty(item.CUST_CARD_TYPE)) {
                            InfoCardType.setText(NewCatevalue.getLabel(
                                    CustomerMapActivity.this,
                                    NewCatevalue.CERTTP, item.CUST_CARD_TYPE));
                        } else {
                            InfoCardType.setText("");
                        }
                        InfoCardNo.setText(item.CUST_CARD_NUMBER);

                        infoWindow = new InfoWindow(BitmapDescriptorFactory
                                .fromView(theInfoView), llNew, -47, null);
                        // infoWindow = new InfoWindow(signinView, llNew, null);
                        mBaiduMap.showInfoWindow(infoWindow);
                        return true;
                    }
                }

                return false;
            }
        });

        // 隐藏弹出框
        mBaiduMap.setOnMapClickListener(new OnMapClickListener() {
            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(CustomerMapActivity.this, arg0.getName(),
                        Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public void onMapClick(LatLng arg0) {
                // TODO Auto-generated method stub
                mBaiduMap.hideInfoWindow();
            }
        });
    }

    //后期添加的AK及网络监听
    private class MySDKBrodcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE)) {
                Toast.makeText(getApplicationContext(), "error_code+AK有误", Toast.LENGTH_SHORT).show();
            } else if (intent.getAction().equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                Toast.makeText(getApplicationContext(), "网络有误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initListenner() {
        custome_hotWeb.setOnClickListener(this);
        customermap_num_btn.setOnClickListener(this);
        customemap_position.setOnClickListener(this);
        customemap_querycus.setOnClickListener(this);
        customemap_thecus.setOnClickListener(this);
        rgp_check_cus.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.customemap_rbtn_warnperson:
                        indexCheck = "01";
                        break;
                    case R.id.customemap_rbtn_warncommon:
                        indexCheck = "02";
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void setView() {

        custome_bmapView = (MapView) findViewById(R.id.customemap_bmapView);
        //去掉百度默认图标
        View child = custome_bmapView.getChildAt(1);
        if (child!=null&&(child instanceof ImageView || child instanceof ZoomControls)){
            child.setVisibility(View.GONE);
        }
        custome_hotWeb = (Button) findViewById(R.id.customemap_hotmap);
        rgp_check_cus = (RadioGroup) findViewById(R.id.customemap_check_cus);
        customermap_num_btn = (Button) findViewById(R.id.customermap_num_btn);
        customermap_ll = (LinearLayout) findViewById(R.id.customermap_ll);
        customemap_thecus = (Button) findViewById(R.id.customemap_thecus);
        customemap_querycus = (Button) findViewById(R.id.customemap_querycus);
        customemap_position = (Button) findViewById(R.id.customemap_position);
        for (int i = 0; i < customermap_tv.length; i++) {
            customermap_tv[i] = (TextView) findViewById(customermap_tv_id[i]);
        }
        factory = LayoutInflater.from(this);
        theInfoView = factory.inflate(R.layout.customermap_marker_info, null);// 点击定位按钮弹出信息view
        infoName = (TextView) theInfoView
                .findViewById(R.id.customermap_marker_name);
        InfoPhone = (TextView) theInfoView
                .findViewById(R.id.customermap_marker_phone);
        InfoCardType = (TextView) theInfoView
                .findViewById(R.id.customermap_marker_cardtype);
        InfoCardNo = (TextView) theInfoView
                .findViewById(R.id.customermap_marker_cardno);

    }

    // //省市客户分布弹框
    private void showPopView() {
        if (thePopWeb == null) {
            View popupView = factory.inflate(R.layout.popwindow_customemap,
                    null);
            // 创建一个PopuWidow对象
            thePopWeb = new Dialog(this, R.style.DialogTheme);
            thePopWeb.setContentView(popupView);
            Window dialogWindow = thePopWeb.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = Tool.getScreenWidth(this) * 4 / 5; // 宽度
            lp.height = Tool.getScreenHeight(this) * 4 / 5; // 高度

            dialogWindow.setAttributes(lp);

            // 初始化控件
            initPopView(popupView);
            setListener();

            customerWebView.getSettings().setJavaScriptEnabled(true);
            String htmlText = HRequest.BMC_URL_DOMAIN
                    + "hotmap/index.jsp?type=" + indexCheck;
            customerWebView.loadUrl(htmlText);
            customerWebView.setWebViewClient(new myWebViewClient());
        }
        if (!thePopWeb.isShowing()) {
            thePopWeb.show();
        }
    }

    // 创建弹出框(单一客户查询)
    private void showCusPopView() {
        if (thePopCus == null) {
            View popupView = factory.inflate(R.layout.popwindow_querycustome,
                    null);
            // 创建一个PopuWidow对象
            thePopCus = new Dialog(this, R.style.DialogTheme);
            thePopCus.setContentView(popupView);
            Window dialogWindow = thePopCus.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = Tool.getScreenWidth(this) * 4 / 5; // 宽度
            lp.height = Tool.getScreenHeight(this) * 4 / 5; // 高度

            dialogWindow.setAttributes(lp);

            // 初始化控件
            initCusPopView(popupView);
            setCusPopListener();
        }
        if (!thePopCus.isShowing()) {
            thePopCus.show();
        }
    }

    private void setCusPopListener() {
        querycustome_cardtype.setOnClickListener(this);
        querycustome_btn.setOnClickListener(this);
        querycustome_clear.setOnClickListener(this);
        querycustome_close.setOnClickListener(this);
    }

    //初始单一客户查询界面
    private void initCusPopView(View popupView) {
        querycustome_name = (EditText) popupView
                .findViewById(R.id.popwindow_querycustome_name);// 客户姓名
        querycustome_phone = (EditText) popupView
                .findViewById(R.id.popwindow_querycustome_phone);// 客户电话
        querycustome_cardtype = (TextView) popupView
                .findViewById(R.id.popwindow_querycustome_cardtype);// 客户证件类型
        querycustome_cardnum = (EditText) popupView
                .findViewById(R.id.popwindow_querycustome_cardnum);// 客户证件号码
        querycustome_btn = (Button) popupView
                .findViewById(R.id.popwindow_querycustome_btn);// 查询按钮
        querycustome_clear = (Button) popupView
                .findViewById(R.id.popwindow_querycustome_clear);// 清除按钮
        querycustome_list = (ListView) popupView
                .findViewById(R.id.popwindow_querycustome_list);// 信息列表
        querycustome_close = (ImageView) popupView
                .findViewById(R.id.popwindow_querycustome_close);// 清除按钮

        cusAdapter = new QueryCustomerAdapter(this);
        querycustome_list.setAdapter(cusAdapter);
        customerQueryInfo.clear();
        cusAdapter.setInfolist(customerQueryInfo);
        cusAdapter.notifyDataSetChanged();

        querycustome_list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                CustomerMapInfo theItem = cusAdapter.getItem(position);
                tempMapInfo.clear();
                tempMapInfo.add(theItem);
                setMark(tempMapInfo);

                if (theItem.LATITUDE != null && theItem.LONGITUDE != null) {

                    double lati = Tool.DoubleParse(theItem.LATITUDE);
                    double loti = Tool.DoubleParse(theItem.LONGITUDE);
                    if (lati == 0 || loti == 0) {
                        return;
                    }
                    // 中心点移动到所选择坐标
                    LatLng llpoint = new LatLng(lati, loti);
                    MapStatusUpdate mapStatusUpdate =  newLatLng(llpoint);
                    mBaiduMap.animateMapStatus(mapStatusUpdate);
                }

                if (thePopCus != null) {
                    thePopCus.dismiss();
                }
            }
        });

    }

    private void setListener() {
        customemap_close.setOnClickListener(this);

    }

    private void initPopView(View popupView) {
        customerWebView = (WebView) popupView
                .findViewById(R.id.customemap_webView);
        customemap_close = (ImageView) popupView
                .findViewById(R.id.customemap_close);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.customemap_close:
                if (thePopWeb != null) {
                    thePopWeb.dismiss();
                }
                break;
            case R.id.customemap_hotmap://省市客户查询按钮点击
                showPopView();

                break;
            case R.id.customermap_num_btn://点击后弹出大众至五星级客户按钮
                // customermap_ll.setVisibility(View.VISIBLE);
                float height = customermap_ll.getHeight();
                if (customermap_ll.getY() == 0) {
                    ObjectAnimator translationUp = ObjectAnimator.ofFloat(customermap_ll, "Y", -height);
                    translationUp.setDuration(800);
                    translationUp.start();
                    customermap_num_btn.setBackgroundResource(R.drawable.customemap_xiala_up);

                } else {
                    ObjectAnimator translationDown = ObjectAnimator.ofFloat(customermap_ll, "Y", 0f);
                    translationDown.setDuration(800);
                    translationDown.start();
                    customermap_num_btn.setBackgroundResource(R.drawable.customemap_xiala_close);
                }
                break;
            case R.id.customemap_querycus://附近的客户
                if (ll != null && ll.latitude != 0 && ll.longitude != 0) {
                    QueryCus();
                  MapStatusUpdate  mapStatusUpdate = newLatLng(ll);
                    mBaiduMap.animateMapStatus(mapStatusUpdate);
                } else {
                    Toast.makeText(this, "正在定位中...", Toast.LENGTH_SHORT).show();
                    if (!mLocClient.isStarted()) {
                        mLocClient.start();// 开始定位
                    }
                }
                break;
            case R.id.customemap_thecus:
                showCusPopView();
                break;
            case R.id.popwindow_querycustome_btn://单一客户查询按钮
                QueryOneCus();
                break;
            case R.id.popwindow_querycustome_clear:
                ClearOneCus();
                break;
            case R.id.popwindow_querycustome_cardtype://证件类型
                SpinnerAdapter.showSelectDialog(this, NewCatevalue.CERTTP,
                        querycustome_cardtype, new CallBackItemClickListener() {
                            @Override
                            public void CallBackItemClick(String spinnerID) {
                                cardTypeNum = spinnerID;
                            }
                        });
                break;
            case R.id.popwindow_querycustome_close:
                if (thePopCus != null) {
                    thePopCus.dismiss();
                }
                break;
            case R.id.customemap_position:
                if (!mLocClient.isStarted()) {
                    mLocClient.start();// 开始定位
                }
                MapStatusUpdate mapStatusUpdate = newLatLng(ll);
//                if (ll != null) {
//                    MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 19);//设置中心点及缩放级别
                    mBaiduMap.animateMapStatus(mapStatusUpdate);//设置中心点
//                }
            default:
                break;
        }
    }

    private void ClearOneCus() {
        //

        querycustome_name.setText("");// 客户姓名
        querycustome_phone.setText("");// 客户电话
        querycustome_cardtype.setText("");// 客户证件类型
        querycustome_cardnum.setText("");// 客户证件号码
        cardTypeNum = "";
        customerQueryInfo.clear();
        cusAdapter.setInfolist(customerQueryInfo);
        cusAdapter.notifyDataSetChanged();
    }

    //点击单一客户后的模糊查询
    private void QueryOneCus() {
        QueryOneCusInfo theCusInfo = new QueryOneCusInfo();
        theCusInfo.CUSTNAME = Tool.getTextValue(querycustome_name);// 客户名称
        theCusInfo.phone = Tool.getTextValue(querycustome_phone);// 手机号
        theCusInfo.CUST_PSN_CARD_NUMBER = Tool
                .getTextValue(querycustome_cardnum);// 证件号码
        theCusInfo.CUSTTYPE = indexCheck;// 对公对私(01:对私，02:对公)

        String theInfo = "&spareOne=" + LogoActivity.user.getStaId()
                + "&jsonData=" + theCusInfo.toString();
        QueryOneCusHCon = RequestInfo(this, requestType.Other,
                InterfaceInfo.Customer_Map_QueryOneCus, theInfo,
                queryOneCusIndex);
    }

    // 查询1000米周边客户
    private void QueryCus() {
        String theInfo = "&spareOne=" + LogoActivity.user.getStaId()
                + "&spareTwo=1&jsonData={" + "\"CUSTTYPE\"=\"" + indexCheck
                + "\",\"LONGITUDE\"=\"" + ll.longitude + "\",\"LATITUDE\"=\""
                + ll.latitude + "\"}";

        QueryCusHCon = RequestInfo(this, requestType.Other,
                InterfaceInfo.Customer_Map_QueryCus, theInfo, queryCusIndex);

    }

    @Override
    public void onConnected(Message msg) {
        super.onConnected(msg);
        HResponse res = null;
        JSONObject tmpJsonObject = null;
        switch (connectionIndex) {
            case HConnection.RESPONSE_ERROR:
                break;
            case queryCusIndex:
                res = QueryCusHCon
                        .getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (res == null || res.dataJsonObject == null) {
                    return;
                }
                tmpJsonObject = res.dataJsonObject;
                Log.i("", tmpJsonObject.toString());
                readJson(tmpJsonObject, connectionIndex);
                break;
            case queryOneCusIndex:
                res = QueryOneCusHCon
                        .getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (res == null || res.dataJsonObject == null) {
                    return;
                }
                tmpJsonObject = res.dataJsonObject;
                Log.i("", tmpJsonObject.toString());
                readJson(tmpJsonObject, connectionIndex);
                break;
        }
    }

    private void readJson(JSONObject tmpJsonObject, int connectionIndex) {
        String retCode = Tool.getJsonValue(tmpJsonObject, "retCode");

        if (retCode.equals("0000")) {
            Toast.makeText(this, "获取成功！", Toast.LENGTH_SHORT).show();
            JSONArray theInfo;
            if (connectionIndex == queryCusIndex) {
                customermap_tv[0].setText(Tool.getJsonValue(tmpJsonObject,
                        "CUSTVALUELEVEL0"));// 0所属城市大众客户总数
                customermap_tv[1].setText(Tool.getJsonValue(tmpJsonObject,
                        "CUSTVALUELEVEL1"));// 1一星客户客户总数
                customermap_tv[2].setText(Tool.getJsonValue(tmpJsonObject,
                        "CUSTVALUELEVEL2"));// 2二星客户客户总数
                customermap_tv[3].setText(Tool.getJsonValue(tmpJsonObject,
                        "CUSTVALUELEVEL3"));// 3三星客户客户总数
                customermap_tv[4].setText(Tool.getJsonValue(tmpJsonObject,
                        "CUSTVALUELEVEL4"));// 4四星客户客户总数
                customermap_tv[5].setText(Tool.getJsonValue(tmpJsonObject,
                        "CUSTVALUELEVEL5"));// 5五星客户客户总数
                customermap_tv[7].setText(Tool.getJsonValue(tmpJsonObject,
                        "AREATYPE01"));// 01,社区客户总数
                customermap_tv[8].setText(Tool.getJsonValue(tmpJsonObject,
                        "AREATYPE02"));// 02:园区客户总数
                customermap_tv[6].setText(Tool.getJsonValue(tmpJsonObject,
                        "AREATYPE03"));// 03,农区客户总数
                customermap_tv[9].setText(Tool.getJsonValue(tmpJsonObject,
                        "AREATYPE04"));// 04,商区客户总数

                try {
                    theInfo = tmpJsonObject.getJSONArray("group");
                    if (theInfo.length() < 0) {
                        return;
                    } else {
                        customerMapInfo.clear();
                        for (int i = 0; i < theInfo.length(); i++) {
                            JSONObject temps = (JSONObject) theInfo.opt(i);
                            CustomerMapInfo theCustomerInfo = new CustomerMapInfo();
                            theCustomerInfo.CUSTID = Tool.getJsonValue(temps,
                                    "CUSTID");// 客户号
                            theCustomerInfo.CUSTNAME = Tool.getJsonValue(temps,
                                    "CUSTNAME");// 客户姓名
                            theCustomerInfo.LONGITUDE = Tool.getJsonValue(
                                    temps, "LONGITUDE");// 经度
                            theCustomerInfo.LATITUDE = Tool.getJsonValue(temps,
                                    "LATITUDE");// 纬度
                            theCustomerInfo.CUST_CARD_TYPE = Tool.getJsonValue(
                                    temps, "CUST_CARD_TYPE");// 证件类型
                            theCustomerInfo.CUST_CARD_NUMBER = Tool
                                    .getJsonValue(temps, "CUST_CARD_NUMBER");// 证件号码
                            theCustomerInfo.PHONE_NO = Tool.getJsonValue(
                                    temps, "PHONE_NO");// 联系电话
                            theCustomerInfo.MapInfoInfex = i;// 自定义索引
                            customerMapInfo.add(theCustomerInfo);

                        }
                        tempMapInfo.clear();
                        tempMapInfo.addAll(customerMapInfo);//此处可以注销，因为通过setMark()方法把集合已经传递下去
                        setMark(customerMapInfo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else if (connectionIndex == queryOneCusIndex) {
                try {
                    theInfo = tmpJsonObject.getJSONArray("group");
                    if (theInfo.length() < 0) {
                        return;
                    } else {
                        customerQueryInfo.clear();
                        for (int i = 0; i < theInfo.length(); i++) {
                            JSONObject temps = (JSONObject) theInfo.opt(i);
                            CustomerMapInfo theCustomerInfo = new CustomerMapInfo();
                            theCustomerInfo.CUSTID = Tool.getJsonValue(temps,
                                    "CUSTID");// 客户号
                            theCustomerInfo.CUSTNAME = Tool.getJsonValue(temps,
                                    "CUSTNAME");// 客户姓名
                            theCustomerInfo.LONGITUDE = Tool.getJsonValue(
                                    temps, "LONGITUDE");// 经度
                            theCustomerInfo.LATITUDE = Tool.getJsonValue(temps,
                                    "LATITUDE");// 纬度

                            theCustomerInfo.CUST_CARD_TYPE = Tool.getJsonValue(
                                    temps, "CUST_CARD_TYPE");// 证件类型
                            theCustomerInfo.CUST_CARD_NUMBER = Tool
                                    .getJsonValue(temps, "CUST_CARD_NUMBER");// 证件号码
                            theCustomerInfo.PHONE_NO = Tool.getJsonValue(
                                    temps, "PHONE_NO");// 联系电话
                            customerQueryInfo.add(theCustomerInfo);
                        }

                        cusAdapter.setInfolist(customerQueryInfo);
                        cusAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        }
    }

    // 添加mark点
    private void setMark(ArrayList<CustomerMapInfo> theMapInfo) {
        // 清除所有图层
        custome_bmapView.getMap().clear();
        ooMarker.clear();
        List<LatLng> points = new ArrayList<LatLng>();
        int indexID = 0;
        for (CustomerMapInfo item : theMapInfo) {
            if (item.LATITUDE != null && item.LONGITUDE != null) {

                double lati = Tool.DoubleParse(item.LATITUDE);
                double loti = Tool.DoubleParse(item.LONGITUDE);
                if (lati == 0 || loti == 0) {
                    continue;
                }
                item.MapInfoInfex = indexID;
                LatLng llpoint = new LatLng(lati, loti);
                points.add(llpoint);

                MarkerOptions theMarker = new MarkerOptions().position(llpoint)
                        .icon(icon_maker).zIndex(indexID).draggable(false)
                        .perspective(true);

                Marker mMarker = (Marker) (mBaiduMap.addOverlay(theMarker));
                ooMarker.add(mMarker);
                indexID++;
                mBaiduMap.addOverlay(theMarker);
            }
        }

        custome_bmapView.refreshDrawableState();
    }

    /**
     * 定位SDK监听函数
     */
    class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            if (location == null || custome_bmapView == null)
                return;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (ll != null) {

            } else {
                ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory
                        .newLatLngZoom(ll, 19);//点击进入客户分布的界面即显示此比例次
                mBaiduMap.animateMapStatus(u);
            }
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
            }

            Log.i("", sb.toString());

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 退出时销毁定位
        mLocClient.stop();
        // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
        custome_bmapView.onDestroy();
        // 回收 bitmap 资源
        icon_maker.recycle();
        icon_maker_blue.recycle();
        unregisterReceiver(mySDKBrodcastReceiver);//解绑AK与网络的广播
    }

    class QueryCustomerAdapter extends BaseAdapter {
        private Activity context;
        private List<CustomerMapInfo> infolist;

        public QueryCustomerAdapter(Activity context) {
            super();
            this.context = context;
        }

        @Override
        public int getCount() {
            if (infolist != null && infolist.size() > 0) {
                return infolist.size();
            } else {
                return 0;
            }
        }

        @Override
        public CustomerMapInfo getItem(int position) {
            return infolist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            HolderView holder;
            if (convertView == null) {
                convertView = View.inflate(context,
                        R.layout.item_fiveitem_nodelete, null);
                holder = new HolderView();
                holder.lvItemInfo1 = (TextViewMarquee) convertView
                        .findViewById(R.id.item_1);
                holder.lvItemInfo2 = (TextView) convertView
                        .findViewById(R.id.item_2);
                holder.lvItemInfo3 = (TextView) convertView
                        .findViewById(R.id.item_3);
                holder.lvItemInfo4 = (TextView) convertView
                        .findViewById(R.id.item_4);
                holder.lvItemInfo5 = (TextViewMarquee) convertView
                        .findViewById(R.id.item_5);

                convertView.setTag(holder);
            } else {
                holder = (HolderView) convertView.getTag();
            }

            // 单个item值
            CustomerMapInfo cus = infolist.get(position);
            // 将获取到的数据进行展示
            holder.lvItemInfo1.setText(cus.CUSTID);
            holder.lvItemInfo2.setText(cus.CUSTNAME);
            holder.lvItemInfo3.setText(cus.PHONE_NO);
            String custType = cus.CUST_CARD_TYPE;
            holder.lvItemInfo4.setText(NewCatevalue.getLabel(context,
                    NewCatevalue.CERTTP, custType));

            holder.lvItemInfo5.setText(cus.CUST_CARD_NUMBER);

            return convertView;
        }

        public void setInfolist(List<CustomerMapInfo> infolist) {
            this.infolist = infolist;
        }

        class HolderView {
            private TextView lvItemInfo1;
            private TextView lvItemInfo2;
            private TextView lvItemInfo3;
            private TextView lvItemInfo4;
            private TextViewMarquee lvItemInfo5;

        }
    }
}

class myWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // TODO Auto-generated method stub
        view.loadUrl(url);
        return true;
    }

}