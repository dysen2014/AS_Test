package com.pactera.financialmanager.credit.main.service;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.model.Customer;
import com.pactera.financialmanager.ui.model.SignRecordInfo;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.LogUtils;
import com.pactera.financialmanager.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.baidu.mapapi.map.MapStatusUpdateFactory.newLatLng;

/**
 * Created by dysen on 2017/8/24.
 */

public class CheckIn extends ParentActivity implements View.OnClickListener {

    // MapView 是地图主控件
    private MapView mMapView;
    // 定位、删除按钮
    private Button LocBtn, DelBtn;
    // 地图管理
    private BaiduMap mBaiduMap;
    // 定位相关
    private LocationClient mLocClient;
    // 定位监听事件
    public MyLocationListenner myListener = new MyLocationListenner();
    // 当前位置经纬度
    private LatLng ll;

    public ListView listinfo;
    private HConnection HCon;
    private final int returnIndex = 1;
    List<Customer> returnInfo = new ArrayList<Customer>();
    private LayoutInflater factory;
    //	private View signinView;// 地址信息view
//	private TextView signinInfo;// 当前地址信息
    private InfoWindow infoWindow;// 显示定位地址信息框
    private String LocStr;// 当前地址

    private View conView;// 当前view
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 弹出框
    private PopupWindow popup;
    private EditText regmap_customer;// 客户名
    private EditText regmap_contents;// 内容
    private TextView regmap_address, regmap_time;// 地址、时间
    private Button regmap_ok_btn;// 确认
    private ImageView regmap_close_btn;// 关闭

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragmentmysignin);

        findViews();
        setListener();
        // 获取地图控制
        mBaiduMap = mMapView.getMap();
        // 设置普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        // 显示地图建筑
        mBaiduMap.setBuildingsEnabled(true);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位图标
        BitmapDescriptor MarkerBitmap = BitmapDescriptorFactory.fromResource(R.drawable.map_coordinate);
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, false, MarkerBitmap));

        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);//注册监听，写一个监听implements BDLocationListener
        //	配置定位
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setIsNeedAddress(true);// 设置是否需要地址信息，默认为无地址
        option.setIsNeedLocationPoiList(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setAddrType("all");//返回所有信息包括地址
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
        option.setScanSpan(4000);//浏览的间隔时间 （4S定位一次）
        mLocClient.setLocOption(option);
        if (!mLocClient.isStarted()) {
            mLocClient.start();// 开始定位
        }
        // 设置定位点点击后弹出信息框
        mBaiduMap.setOnMyLocationClickListener(new BaiduMap.OnMyLocationClickListener() {
            InfoWindow.OnInfoWindowClickListener OnInfoWindowListter;

            @Override
            public boolean onMyLocationClick() {
                LatLng llNew = new LatLng(ll.latitude + 0.0005, ll.longitude + 0.0005);
                OnInfoWindowListter = new InfoWindow.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick() {
                        // 点击签到按钮后弹出popupView
                        showSignin();
                    }
                };
//				signinInfo.setText(LocStr);
                // infoWindow = new InfoWindow(signinView, ll,OnInfoWindowListter);
//				infoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(signinView), llNew, -30, OnInfoWindowListter);
                infoWindow = new InfoWindow(BitmapDescriptorFactory.fromResource(R.drawable.map_sign_infowindow), llNew, -25, OnInfoWindowListter);
                mBaiduMap.showInfoWindow(infoWindow);

                return true;
            }
        });

        // 隐藏弹出框
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                // TODO Auto-generated method stub
                toast(arg0.getName());
                return true;
            }

            @Override
            public void onMapClick(LatLng arg0) {
                // TODO Auto-generated method stub
                mBaiduMap.hideInfoWindow();

            }
        });
    }

    //文字覆盖物及圆形覆盖物的demo
    private void draw() {

        // 构造折线点坐标
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(new LatLng(39.965, 116.404));
        points.add(new LatLng(39.925, 116.454));
        points.add(new LatLng(39.955, 116.494));
        points.add(new LatLng(39.905, 116.554));
        points.add(new LatLng(39.965, 116.604));
        points.add(new LatLng(39.935, 116.610));
        points.add(new LatLng(39.930, 116.625));

//构建分段颜色索引数组
        List<Integer> colors = new ArrayList<>();
        colors.add(Integer.valueOf(Color.rgb(170, 170, 170)));
//		colors.add(Integer.valueOf(Color.RED));
        colors.add(Integer.valueOf(Color.parseColor("#87CEFA")));
        OverlayOptions ooPolyline = new PolylineOptions().width(10).colorsValues(colors).points(points);
        //添加在地图中
        Polyline mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
    }

    private void setListener() {
        LocBtn.setOnClickListener(this);
        DelBtn.setOnClickListener(this);
    }

    private void findViews() {
        factory = LayoutInflater.from(this);
        mMapView = (MapView) findViewById(R.id.signin_bmapView);
        LocBtn = (Button) findViewById(R.id.fm_mysignin_btn_loc);
        DelBtn = (Button) findViewById(R.id.fm_mysignin_btn_del);
//		signinView = factory.inflate(R.layout.signin_marker_info, null);
//
//		signinInfo = (TextView) signinView
//				.findViewById(R.id.signin_marker_info);

    }

    /**
     * 点击签到按钮后弹出popupView
     * 签到现场
     */
    public void showSignin() {
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str_nowtime = formatter.format(curDate);
        if (popup == null) {
            View popupView = factory.inflate(R.layout.signin_marker_pop, null);
            // 创建一个PopuWidow对象
            popup = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            // 设置焦点在弹窗上
            popup.setFocusable(true);
            popup.setTouchInterceptor(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        popup.dismiss();
                        return true;
                    }
                    return false;

                }
            });
            // 初始化控件
            initView(popupView);
            // 注册事件
            initListener();
        }
        if (!popup.isShowing()) {
            regmap_contents.setText("");
            regmap_address.setText(LocStr);
            regmap_time.setText(str_nowtime);
            popup.showAtLocation(mMapView, Gravity.CENTER, 0, 0);

        }
    }

    // 注册事件（pop窗口）
    private void initListener() {
        regmap_ok_btn.setOnClickListener(this);
        regmap_close_btn.setOnClickListener(this);
    }

    // 初始化控件（pop窗口）
    private void initView(View popupView) {
        // TODO Auto-generated method stub
        regmap_contents = (EditText) popupView
                .findViewById(R.id.regmap_contents);
        regmap_address = (TextView) popupView.findViewById(R.id.regmap_address);
        regmap_time = (TextView) popupView.findViewById(R.id.regmap_time);
        regmap_customer = (EditText) popupView
                .findViewById(R.id.regmap_customer);
        regmap_close_btn = (ImageView) popupView
                .findViewById(R.id.regmap_close);
        regmap_ok_btn = (Button) popupView.findViewById(R.id.regmap_btn);
        regmap_address.setEnabled(false);
        regmap_time.setEnabled(false);

    }

    @Override
    public void onConnected(Message msg) {
        super.onConnected(msg);
        LogUtils.d(connectionIndex + "******" + msg);
        switch (connectionIndex) {
            //注销break 使回车签到也能成功
            case HConnection.RESPONSE_ERROR:
                toast("签到内容有误，请重新输入！");
                break;
            case returnIndex:
                HResponse res = HCon
                        .getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (res == null || res.dataJsonObject == null) {
                    LogUtils.d(res + "===" + res.dataJsonObject);
                    return;
                }
                JSONObject tmpJsonObject = res.dataJsonObject;
                LogUtils.d("login respons:" + tmpJsonObject.toString());
                readJson(tmpJsonObject);
                break;
        }
    }

    private void readJson(JSONObject tmpJsonObject) {
        // TODO Auto-generated method stub
        String retCode = "";
        //Log.i("1111111", tmpJsonObject.toString());
        if (tmpJsonObject.has("retCode")) { // 返回标志
            try {
                retCode = tmpJsonObject.getString("retCode");

            } catch (JSONException e1) {
                e1.printStackTrace();
                return;
            }
        }

        if (retCode.equals("0000")) {
            if (popup != null) {
                popup.dismiss();
            }
            Toast.makeText(this, "签到成功!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "签到失败!", Toast.LENGTH_SHORT).show();
            if (popup != null) {
                popup.dismiss();

            }
        }

    }

    @Override
    public void onPause() {
        mMapView.onPause();//官方文档此处还添加了mMapView。setVisibity(view.Invisibale)
        super.onPause();
    }

//    @Override
//    public void onResume() {
//        mMapView.onResume();//官方文档此处还添加了mMapView。setVisibity(view.Visibale)
//        super.onResume();
//    }

    @Override
    public void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
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
                        .newLatLngZoom(ll, 19);
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
            List<Poi> list = location.getPoiList();
            if (null != list) {
                String temp = list.get(0).getName();
                LocStr = location.getAddrStr() + "-" + temp + "附近";
            } else {
                LocStr = location.getAddrStr();
            }

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.regmap_btn:
                setData();//签到框的确定提交
                break;
            case R.id.regmap_close:
                if (popup != null) {
                    popup.dismiss();
                }
                break;
            case R.id.fm_mysignin_btn_loc://点击定位按钮的操作
                if (!mLocClient.isStarted()) {
                    mLocClient.start();
                    Toast.makeText(this, "正在定位中...", Toast.LENGTH_SHORT)
                            .show();
                }

                if (infoWindow != null) {
                    mBaiduMap.showInfoWindow(infoWindow);
                }
                if (ll != null) {
                    MapStatusUpdate u = newLatLng(ll);
                    mBaiduMap.animateMapStatus(u);
                }
                break;
            case R.id.fm_mysignin_btn_del:
                if (!mLocClient.isStarted()) {
                    mLocClient.stop();
                    mBaiduMap.setMyLocationEnabled(false);
                }
                mBaiduMap.hideInfoWindow();
                break;

            default:
                break;
        }
    }

    //点击提交
    private void setData() {
        SignRecordInfo newRecord = new SignRecordInfo();
        String content = regmap_contents.getText().toString();
        if (content.equals("")) {
            ShowDialog(this, "签到内容不能为空！！！");
        } else {

//			if (content.indexOf("\r\n") != -1)//去掉回车
//				content.replace("\r\n", "");
            content = StringUtils.replaceBlank(content);
            LogUtils.d(content + "\tcontent length:" + content.length());
            newRecord.WORDESC = content;// 工作内容
            newRecord.SIGNINADD = regmap_address.getText().toString();// 签到地址
            newRecord.CREATE_TIME = regmap_time.getText().toString();// 签到时间
            // newRecord.CUSTNAME = regmap_customer.getText().toString();// 客户姓名
            newRecord.LONGITUDE = String.valueOf(ll.longitude);// 精度
            newRecord.LATITUDE = String.valueOf(ll.latitude);// 维度
            LogUtils.d(content + "***" + content.length() + "\n:" + newRecord.toString());
            HCon = RequestInfo(this, Constants.requestType.Other, InterfaceInfo.Signin_Creat, newRecord.toString(), returnIndex);
        }
    }
}
