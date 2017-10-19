package com.pactera.financialmanager.ui.fragment;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
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
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.model.SignRecordInfo;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.baidu.mapapi.map.MapStatusUpdateFactory.newLatLng;

/**
 * 签到记录
 * @author JAY
 *
 */
public class SigninRecordActivity extends ParentActivity implements OnClickListener {

	// MapView 是地图主控件
	private MapView mMapView = null;
	ListView signinrecord_listView;
	EditText signinrecord_editText;
	ImageView signinrecord_img;
	LinearLayout signinrecord_edit_ll;
    private LinearLayout layNodata;

    // 地图管理
	private BaiduMap mBaiduMap;
	private LayoutInflater factory;
	private InfoWindow infoWindow;// 显示信息框
	private View signinView;// 地址信息view
	private HConnection HCon;
	private final int returnIndex = 1;
	private final int returnIndex2 = 2;
	private TextView record_name,record_address, record_con, record_time;
	private int aPic =0;
	private String btFlag ;
	// 定位相关
	private LocationClient mLocClient;
	// 当前位置经纬度
	private LatLng ll;
	// 定位监听事件
	public MyLocationListenner myListener = new MyLocationListenner();
	// 自定义红色定位图标

	private  String currentSelectColor = "#CC0000";
	BitmapDescriptor icon_maker = BitmapDescriptorFactory.fromResource(R.drawable.one2x);
	List<BitmapDescriptor> icon_list = new ArrayList<>();
	// 自定义蓝色定位图标
	BitmapDescriptor icon_maker_blue = BitmapDescriptorFactory.fromResource(R.drawable.map_coordinate_select);
	List<SignRecordInfo> currentMapViewListData = null;

	//保存选中按钮的数据
	HashMap<String,List<SignRecordInfo>> signRecordMapList = new HashMap<>();
	RecordAdapter theRecord;
	RecordAdapter1 theRecord1;
	List<Marker> ooMarker = new ArrayList<Marker>();
	private String[] str;//资源文件中获取图片
	ArrayList<String> markLng = new ArrayList<String>();//暂且定25个定位点
	private Button bt1;
	private Button bt2;
	private Button bt3;
	private Button bt4;
	private Button bt5;
	private Button bt6;
	private Button bt7;
	private HashMap iconMap;
	private ImageView record_query_maneger;
	private LinearLayout record_query_maneger_ll;
	private EditText signinrecord_editText_manager;
	private ImageView signinrecord_img_manager;
	private ListView signinrecord_listView_manager;
	private String userCode;
	private String userManagerCode;
	private boolean isHaddlerConntion = false;
	private LinearLayout layBoard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_signinrecordmap);
		// 初始化界面
		setView();
		initTitle(this, R.drawable.workplacesmallicon);
        if (null!= LogoActivity.user ) {
			String theStaId = LogoActivity.user.getStaId();
//管理岗
			if (theStaId.equals("ST000111") || theStaId.equals("ST000301")|| theStaId.equals("ST000701")|| theStaId.equals("ST000901")
					|| theStaId.equals("ST000501")|| theStaId.equals("ST001101")){
				userManagerCode = "-1";
				record_query_maneger.setVisibility(View.VISIBLE);
				record_query_maneger_ll.setVisibility(View.VISIBLE);

			}else{

				record_query_maneger.setVisibility(View.GONE);
				userCode = LogoActivity.user.getUserCode();// 如果是普通的客户经理，则直接获取签到数据
				btFlag = "100";
				userManagerCode = userCode;
				HaddlerClickRequest(bt1); //默认显示第一天
				record_query_maneger_ll.setVisibility(View.GONE);//普通职员不显示查询按钮
			}
		}
		intAndputIconInMap();//获取图片并放进map集合
		initMap();//初始化百度地图及开始定位
		bindOnClicklisentener();
	}
	//获取图片并放进map集合
	private void intAndputIconInMap() {
		BitmapDescriptor icon_maker1 = BitmapDescriptorFactory.fromResource(R.drawable.one2x);
		BitmapDescriptor icon_maker2 = BitmapDescriptorFactory.fromResource(R.drawable.two2x);
		BitmapDescriptor icon_maker3 = BitmapDescriptorFactory.fromResource(R.drawable.three2x);
		BitmapDescriptor icon_maker4 = BitmapDescriptorFactory.fromResource(R.drawable.four2x);
		BitmapDescriptor icon_maker5 = BitmapDescriptorFactory.fromResource(R.drawable.five2x);
		BitmapDescriptor icon_maker6 = BitmapDescriptorFactory.fromResource(R.drawable.six2x);
		BitmapDescriptor icon_maker7 = BitmapDescriptorFactory.fromResource(R.drawable.seven2x);
		iconMap = new HashMap();
		iconMap.put("100",icon_maker1);
		iconMap.put("101",icon_maker2);
		iconMap.put("102",icon_maker3);
		iconMap.put("103",icon_maker4);
		iconMap.put("104",icon_maker5);
		iconMap.put("105",icon_maker6);
		iconMap.put("106",icon_maker7);
	}

	private void setView() {
		factory = LayoutInflater.from(this);
		RadioGroup rg = (RadioGroup) findViewById(R.id.singinrecord_rg);
		rg.setAlpha(0.7f);
		bt1 = (Button) findViewById(R.id.onebutton);
		//默认选择第一个
//		bt1.setBackgroundColor(Color.parseColor("#CC0000"));
		bt2 = (Button) findViewById(R.id.twobutton);
		bt3 = (Button) findViewById(R.id.threebutton);
		bt4 = (Button) findViewById(R.id.fourbutton);
		bt5 = (Button) findViewById(R.id.fivebutton);
		bt6 = (Button) findViewById(R.id.sixbutton);
		bt7 = (Button) findViewById(R.id.sevenbutton);

		record_query_maneger = (ImageView) findViewById(R.id.record_query_maneger);//点击签到记录按钮
		record_query_maneger_ll = (LinearLayout) findViewById(R.id.record_query_maneger_ll);//作为动画布局弹出来
		layBoard = (LinearLayout) findViewById(R.id.lay_board);
		signinrecord_editText_manager = (EditText) findViewById(R.id.signinrecord_editText_manager);//客户经理查询编辑框
		signinrecord_img_manager = (ImageView) findViewById(R.id.signinrecord_img_manager);//搜索图片按钮
		signinrecord_listView_manager = (ListView) findViewById(R.id.signinrecord_listView_manager);
		mMapView = (MapView) findViewById(R.id.signinrecord_bmapView);//左侧客户经理的listview界面
		signinrecord_listView = (ListView) findViewById(R.id.signinrecord_listView);
		signinrecord_editText = (EditText) findViewById(R.id.signinrecord_editText);
		signinrecord_img = (ImageView) findViewById(R.id.signinrecord_img);//搜索客户经理
		signinView = factory.inflate(R.layout.signinrecord_marker_info, null);//点击红色定位标记后弹出的框
		record_name = (TextView) signinView.findViewById(R.id.signinrecord_marker_name);
		record_address = (TextView) signinView.findViewById(R.id.signinrecord_marker_address);
		record_con = (TextView) signinView.findViewById(R.id.signinrecord_marker_con);
		record_time = (TextView) signinView.findViewById(R.id.signinrecord_marker_time);
		signinrecord_edit_ll=(LinearLayout) findViewById(R.id.signinrecord_edit_ll);
        layNodata = (LinearLayout)findViewById(R.id.lay_nodata);
	}
	//查询下属客户经理
	private void getCustermerManager(String edit) {
		String info = "&spareOne=" + edit;
		HCon = RequestInfo(this, Constants.requestType.Other, InterfaceInfo.SignenRecord_Get_Header, info, returnIndex2);

	}

	private void initMap(){
		// 获取地图控制
		mBaiduMap = mMapView.getMap();
//		mMapView.showZoomControls(false);可以让放大缩小的按钮不显示
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
		option.setOpenGps(true);// 打开gps
		option.setIsNeedAddress(true);// 设置是否需要地址信息，默认为无地址
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//高精度的定位模式
		option.setAddrType("all");//
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(4000);//扫描的间隔时间
		mLocClient.setLocOption(option);
		if (!mLocClient.isStarted()) {
			mLocClient.start();// 开始定位

		// 隐藏弹出框
		mBaiduMap.setOnMapClickListener(new OnMapClickListener() {
			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				Toast.makeText(SigninRecordActivity.this, arg0.getName(),Toast.LENGTH_SHORT).show();
				return true;
			}
			@Override
			public void onMapClick(LatLng arg0) {
				mBaiduMap.hideInfoWindow();

			}
		});

	}


		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker markerInfo) {
				if (infoWindow != null) {
					mBaiduMap.hideInfoWindow();
				}
				/*for (Marker itemMarker : ooMarker) {
					itemMarker.setIcon(icon_maker);
				}*/     //注销后再点击弹kuang就不会被红色定位带你覆盖其他定位点的颜色
				mMapView.refreshDrawableState();

				int markerIndex = markerInfo.getZIndex();
				markerInfo.setIcon(icon_maker_blue);//点击红色定位图标后变蓝色图片

				for (SignRecordInfo item : currentMapViewListData) {
					if (item.markerIndex == markerIndex) {
						double lati = Double.parseDouble(item.LATITUDE);
						double loti = Double.parseDouble(item.LONGITUDE);
						LatLng llNew = new LatLng(lati, loti);
						// 显示信息
						record_name.setText(item.CREATEUSERNAME);
						record_address.setText(item.SIGNINADD);
						record_con.setText(item.WORDESC);
						record_time.setText(item.CREATE_TIME);
						infoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(signinView), llNew, -47, null);//点击红色定位标记之后充出来的弹框
						mBaiduMap.showInfoWindow(infoWindow);
						return true;
					}
				}

				return false;
			}
		});}
	//查询客户经理与左侧listview的点击
	private void bindOnClicklisentener(){
		bt1.setOnClickListener(this);
		bt2.setOnClickListener(this);
		bt3.setOnClickListener(this);
		bt4.setOnClickListener(this);
		bt5.setOnClickListener(this);
		bt6.setOnClickListener(this);
		bt7.setOnClickListener(this);
		record_query_maneger.setOnClickListener(this);//点击按钮弹出动画
		signinrecord_img_manager.setOnClickListener(this);//点击查询客户经理按钮的操作

	/*	signinrecord_img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String theEdit = signinrecord_editText.getText().toString();

				// 通过输入的名称，过滤listview结果集
				if (!TextUtils.isEmpty(theEdit)) {
					viewInfo.clear();
					for (int i = 0; i < returnInfo.size(); i++) {
						SignRecordInfo item = returnInfo.get(i);

						if (item.CREATEUSERNAME.indexOf(theEdit) != -1) {
							viewInfo.add(item);
						}
					}
				} else {
					viewInfo = returnInfo;
				}
				theRecord = new RecordAdapter(SigninRecordActivity.this, viewInfo);
				signinrecord_listView.setAdapter(theRecord);

				if (!TextUtils.isEmpty(theEdit)) {
					viewInfo.clear();
					String userCode = LogoActivity.user.getUserCode();
					getCustermerManager(userCode, theEdit);

				}else{
					Toast.makeText(SigninRecordActivity.this, "客户经理名字不能为空!!!", Toast.LENGTH_SHORT).show();
				}

		});*/
 //点击地址listview定位的同时弹出信息显示框
		signinrecord_listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				SignRecordInfo theInfo = theRecord.getItem(position);
				if(theInfo.CREATE_TIME == null){
					return;
				}
				LatLng thell = new LatLng(Double.parseDouble(theInfo.LATITUDE),Double.parseDouble(theInfo.LONGITUDE));
				MapStatusUpdate u = newLatLng(ll);//设置比例尺大小

				mBaiduMap.animateMapStatus(u);
				record_name.setText(theInfo.CREATEUSERNAME);
				record_address.setText(theInfo.SIGNINADD);
				record_con.setText(theInfo.WORDESC);
				record_time.setText(theInfo.CREATE_TIME);
				infoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(signinView), thell, -47, null);//点击红色定位标记之后充出来的弹框
				mBaiduMap.showInfoWindow(infoWindow);
			}
		});

		//点击查询经理的条目，把参数传给七个button
		signinrecord_listView_manager.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				SignRecordInfo theInfo = theRecord1.getItem(position);
				 userCode = theInfo.USERID;
				signRecordMapList.clear();
				userManagerCode=userCode;//判断当前y用户的记录
				clearSelectedButton();
				bt1.performClick();


			}
		});
	}
	private ArrayList<String> clearSelectedButton(){
		ArrayList<String> arrList = new ArrayList<>();
		if(bt1.getTag() != null && "isSetColor".equals(bt1.getTag())){
			clearButtonBackgroundColor(bt1);
		}
		if(bt2.getTag() != null && "isSetColor".equals(bt2.getTag())){
			clearButtonBackgroundColor(bt2);
		}
		if(bt3.getTag() != null && "isSetColor".equals(bt3.getTag())){
			clearButtonBackgroundColor(bt3);
		}
		if(bt4.getTag() != null && "isSetColor".equals(bt4.getTag())){
			clearButtonBackgroundColor(bt4);
		}
		if(bt5.getTag() != null && "isSetColor".equals(bt5.getTag())){
			clearButtonBackgroundColor(bt5);
		}
		if(bt6.getTag() != null && "isSetColor".equals(bt6.getTag())){
			clearButtonBackgroundColor(bt6);
		}
		if(bt7.getTag() != null && "isSetColor".equals(bt7.getTag())){
			clearButtonBackgroundColor(bt7);
		}
		return arrList;

	}

	@Override
	public void onConnected(Message msg) {

		isHaddlerConntion = false; //不忙碌

		HResponse res = null;
		JSONObject tmpJsonObject = null;
		super.onConnected(msg);
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			break;
		case returnIndex:
			 res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			 tmpJsonObject = res.dataJsonObject;
			Log.i("", tmpJsonObject.toString());
			readJson(tmpJsonObject,returnIndex);
			break;
		case returnIndex2:
				 res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				if (res == null || res.dataJsonObject == null) {
					return;
				}
				 tmpJsonObject = res.dataJsonObject;
				Log.i("", tmpJsonObject.toString());
				readJson(tmpJsonObject,returnIndex2);
				break;

		}

	}


	private void readJson(JSONObject tmpJsonObject,int readType) {
		String retCode = "";
		if (tmpJsonObject.has("retCode")) { // 返回标志
			try {
				retCode = tmpJsonObject.getString("retCode");
			} catch (JSONException e1) {
				e1.printStackTrace();
				return;
			}
		}
		if (retCode.equals("0000")) {
			JSONArray theInfo;
			try {
				theInfo = tmpJsonObject.getJSONArray("group");
				if (theInfo.length() <= 0) {
				//	signinrecord_listView.setVisibility(View.GONE);
				//	layNodata.setVisibility(View.VISIBLE);
					Toast.makeText(this, "当天无签到数据", Toast.LENGTH_SHORT).show();
					return;
				} else {
					List<SignRecordInfo> tempInfo = new ArrayList<SignRecordInfo>();
					signinrecord_listView.setVisibility(View.VISIBLE);
					// layNodata.setVisibility(View.GONE);
					for (int i = 0; i < theInfo.length(); i++) {
						JSONObject temps = (JSONObject) theInfo.opt(i);
						SignRecordInfo sigInfo = new SignRecordInfo();
						// 序号
						if (temps.has("SIGNINID")) {
							sigInfo.SIGNINID = temps.getString("SIGNINID");
						}
						// 创建人姓名
						if (temps.has("CREATEUSERNAME")) {
							sigInfo.CREATEUSERNAME = temps.getString("CREATEUSERNAME");
						}
						// 创建人姓名
						if (temps.has("USERNAME")) {
							sigInfo.USERNAME = temps.getString("USERNAME");

						}
						// 创建人姓名
						if (temps.has("USERID")) {
							sigInfo.USERID = temps.getString("USERID");
						}
						// 工作内容
						if (temps.has("WORDESC")) {
							sigInfo.WORDESC = temps.getString("WORDESC");
						}
						// 签到地址
						if (temps.has("SIGNINADD")) {
							sigInfo.SIGNINADD = temps.getString("SIGNINADD");
						}
						// 创建人id
						if (temps.has("CREATEUSERID")) {

							sigInfo.CREATEUSERID = temps.getString("CREATEUSERID");

						}
						// 签到时间
						if (temps.has("CREATE_TIME")) {
							sigInfo.CREATE_TIME = temps
									.getString("CREATE_TIME");
						}
						// 客户ID
						if (temps.has("CUSTID")) {
							sigInfo.CUSTID = temps.getString("CUSTID");
						}
						// 客户姓名
						if (temps.has("CUSTNAME")) {
							sigInfo.CUSTNAME = temps.getString("CUSTNAME");

						}
						// 精度
						if (temps.has("LONGITUDE")) {
							sigInfo.LONGITUDE = temps.getString("LONGITUDE");

						}
						// 维度
						if (temps.has("LATITUDE")) {
							sigInfo.LATITUDE = temps.getString("LATITUDE");
						}
						sigInfo.color = currentSelectColor;
						if(btFlag != null){
							sigInfo.icon_maker = (BitmapDescriptor) iconMap.get(btFlag);
						}
						tempInfo.add(sigInfo);
					}

					if(readType ==1){
						signRecordMapList.put(btFlag,tempInfo);
					}else {
						addMarker(readType,tempInfo);
						return;
					}
				}

			} catch (JSONException e){
				e.printStackTrace();
			}
			HaddlerAllSelectBtn(readType);
		}
	}
	private  void HaddlerAllSelectBtn(int readType){
		ArrayList<SignRecordInfo> allSelectsignRecord = new ArrayList<SignRecordInfo>();
	    for(Map.Entry setItem : signRecordMapList.entrySet()){
			List<SignRecordInfo>	signRecordInfos = (List<SignRecordInfo>) setItem.getValue();
		 int day =	Integer.valueOf(setItem.getKey().toString().substring(2,3));
			SignRecordInfo signRecordInfo = new SignRecordInfo();
			String dateString = signRecordInfos.get(0).CREATE_TIME.substring(0,10);
			String showName = dateString;
			if(day ==0){
				showName = dateString;
			}
			signRecordInfo.SIGNINADD = showName;
			allSelectsignRecord.add(signRecordInfo);
			allSelectsignRecord.addAll(signRecordInfos);
		}
		addMarker(readType,allSelectsignRecord);
	}

	private void addMarker(int readType,	List<SignRecordInfo> selectinfoData) {
		// 清除所有图层
		mMapView.getMap().clear();
		ooMarker.clear();
		 currentMapViewListData = selectinfoData;
		List<SignRecordInfo> viewInfo = new ArrayList<SignRecordInfo>();
		List<LatLng> points = new ArrayList<LatLng>();
		int indexID = 0;
		for (SignRecordInfo item : selectinfoData) {
			if (item.LATITUDE != null && item.LONGITUDE != null) {
				double lati = Tool.DoubleParse(item.LATITUDE);
				double loti = Tool.DoubleParse(item.LONGITUDE);
				// int indexID = Integer.parseInt(item.SIGNINID);
				if (lati == 0 || loti == 0) {
					continue;
				}
				// int indexID = Integer.parseInt(item.SIGNINID);
				item.markerIndex = indexID;
				LatLng llpoint = new LatLng(lati, loti);
				points.add(llpoint);
				MarkerOptions theMarker = new MarkerOptions().position(llpoint).icon(icon_maker).zIndex(indexID).draggable(true);
				Marker mMarker = (Marker) (mBaiduMap.addOverlay(theMarker));
				if(item.icon_maker != null) { //不为空
					mMarker.setIcon(item.icon_maker);
				}else if(btFlag != null){
					BitmapDescriptor icon_maker = (BitmapDescriptor) iconMap.get(btFlag);
					mMarker.setIcon(icon_maker);
				}
				ooMarker.add(mMarker);//往Marker集合里边添加
				List<Integer> colors = new ArrayList<>();
				colors.add(Color.parseColor(item.color));//绘制定位点之间的连线颜色parseColor(currentSelectColor)/ rgb(170,170,170)
				if(points.size() > 1){
					OverlayOptions ooPolyline = new PolylineOptions().width(5).colorsValues(colors).points(points);
					//添加在地图中
					Polyline mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
				}
				indexID++;
				viewInfo.add(item);

			}
			//用于标记分组显示的
			else if(item.SIGNINADD != null){
				item.markerIndex = -1;
				viewInfo.add(item);
			}
		}
		if(readType == 1) {
			theRecord = new RecordAdapter(this, viewInfo);
			signinrecord_listView.setAdapter(theRecord);//展示签到地址的适配器
			theRecord.notifyDataSetChanged();
		}else if(readType == 2){
				theRecord1 = new RecordAdapter1(this,selectinfoData);
				signinrecord_listView_manager.setAdapter(theRecord1);//查询经理签到记录的适配器
			theRecord1.notifyDataSetChanged();
		}
	}

	@Override
	public void onDestroy() {
		// MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		mMapView.onDestroy();
		// 退出时销毁定位
		mLocClient.stop();
		// 回收 bitmap 资源
		icon_maker.recycle();
		icon_maker_blue.recycle();
		super.onDestroy();

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()){
			case R.id.onebutton:
				 btFlag = "100";
				currentSelectColor = "#CC0000";
				HaddlerClickRequest(bt1);
				break;
			case R.id.twobutton:
				btFlag = "101" ;
				currentSelectColor = "#FF0000";
				HaddlerClickRequest(bt2);

				break;
			case R.id.threebutton:
				btFlag = "102" ;
				currentSelectColor = "#FF3300";
				HaddlerClickRequest(bt3);

				break;
			case R.id.fourbutton:
				btFlag = "103" ;
				currentSelectColor = "#FF6600";
				HaddlerClickRequest(bt4);

				break;
			case R.id.fivebutton:
				btFlag = "104" ;
				currentSelectColor = "#FF9900";
				HaddlerClickRequest(bt5);

				break;
			case R.id.sixbutton:
				btFlag = "105" ;
				currentSelectColor = "#FFCC00";
				HaddlerClickRequest(bt6);

				break;
			case R.id.sevenbutton:
				btFlag = "106" ;
				currentSelectColor = "#FFFF00";
				HaddlerClickRequest(bt7);
				break;
			case R.id.record_query_maneger://点击签到记录后弹出的动画
				openQueryBoard();

				break;
			case R.id.signinrecord_img_manager://点击放大镜图片进行搜索，最多展示50个客户经理
				queryCustermerManager();//查询辖下客户经理，
				break;
			default:

				break;
		}
	}
//查询辖下的客户经理
	private void queryCustermerManager() {
		String edit =signinrecord_editText_manager.getText().toString();
		getCustermerManager(edit);
	}

	/**
	 * 打开查询面板
	 */
	private void openQueryBoard() {
		Animation animTranslate = null;

		// 根据是否已隐藏判断是否打开
		if (layBoard.getVisibility() != View.VISIBLE) {
			animTranslate = AnimationUtils.loadAnimation(this, R.anim.right_in);
			record_query_maneger_ll.startAnimation(animTranslate);
			layBoard.setVisibility(View.VISIBLE);
			queryCustermerManager();//默认进入即展示辖下客户经理
			//Toast.makeText(this, "打开", Toast.LENGTH_SHORT).show();
		} else {
			animTranslate = AnimationUtils.loadAnimation(this, R.anim.right_out);
			animTranslate.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					layBoard.setVisibility(View.GONE);
				}

				@Override
				public void onAnimationRepeat(Animation animation) {

				}
			});
			record_query_maneger_ll.startAnimation(animTranslate);
			//Toast.makeText(this, "关闭", Toast.LENGTH_SHORT).show();
		}
	}

	private void HaddlerClickRequest(Button btn) {

		String theStaId = LogoActivity.user.getStaId();
		if(theStaId.equals("ST000111") || theStaId.equals("ST000301")|| theStaId.equals("ST000701")|| theStaId.equals("ST000901")
				|| theStaId.equals("ST000501")|| theStaId.equals("ST001101")) {
			if(userManagerCode == null || "-1".equals(userManagerCode)){
				Toast.makeText(SigninRecordActivity.this, "请点击签到记录",Toast.LENGTH_SHORT).show();
				isHaddlerConntion = false;
				return;
			}
		}

		if(isHaddlerConntion){
			Toast.makeText(SigninRecordActivity.this, "正在处理中……",Toast.LENGTH_SHORT).show();
			isHaddlerConntion = false;
			return;
		}
		if("isSetColor".equals(btn.getTag())){
			if(signRecordMapList.get(btFlag) != null){
				signRecordMapList.remove(btFlag); //如果之前选择了就删除当前的数据
				//加载地图和listview
				HaddlerAllSelectBtn(returnIndex);
			}
		}else{
			//请求网络设置忙碌，当请求完成就设置非忙碌
			isHaddlerConntion = true;
			queryDataByTime(userCode, btFlag);
		}
		//点击按钮切换按钮颜色
		if(btn.getTag() == null && !"isSetColor".equals(btn.getTag()) ) {
			btn.setBackgroundColor(Color.parseColor(currentSelectColor));
			btn.setTag("isSetColor");

        }else {
			clearButtonBackgroundColor(btn);
        }
	}

	private void clearButtonBackgroundColor(Button btn) {
		GradientDrawable drawable = new GradientDrawable();
		drawable.setShape(GradientDrawable.RECTANGLE); // 画框
		drawable.setStroke(2, Color.parseColor(currentSelectColor)); // 边框粗细及颜色
		drawable.setColor(0xFFFFFFFF); // 边框内部颜色
		btn.setBackgroundDrawable(drawable);
		btn.setTag(null);
	}

	private void queryDataByTime(String userCode, String timeCode) {
	    /* List<String> arrList = 	getIsSelectedButton();
		String strTimeCodes="";
		if(arrList.size()>0){
			for(String item: arrList){
				strTimeCodes+=item+",";
			}
			strTimeCodes = strTimeCodes.substring(0,strTimeCodes.length()-1);
		}*/
		//当选择第一个和第三天 100,102

		String info = "&spareOne=" + userCode + "&spareTwo=" + timeCode;
		HCon = RequestInfo(this, Constants.requestType.Other, InterfaceInfo.SigninRecord_Get, info, returnIndex);
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
			MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);

			if (ll != null) {
//				LatLng theNew = new LatLng(location.getLatitude(),
//						location.getLongitude());
//				if (theNew != ll) {
//					ll = theNew;
//					// MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
//					MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(
//							ll, 11);
//					mBaiduMap.animateMapStatus(u);
//				}
			} else {
				ll = new LatLng(location.getLatitude(), location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 17);//设置比例尺的大小
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

			// Constants.TheLatitude = location.getLatitude();
			// Constants.TheLongitude = location.getLongitude();
		}

	}

}

/**
 * 签到记录Adapter
 *
 * @author JAY
 *
 */
//界面左侧的定位点的显示
class RecordAdapter extends BaseAdapter {
	private Activity activity;
	private List<SignRecordInfo> data;
	private static LayoutInflater inflater = null;

	public RecordAdapter(Activity a, List<SignRecordInfo> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}
	public int getCount() {
		return data.size();
	}

	public SignRecordInfo getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = View.inflate(activity, R.layout.item_recordmap, null);
		RelativeLayout relativeLayout = (RelativeLayout) vi
				.findViewById(R.id.recoder_ico);
		TextView item_num = (TextView) vi
				.findViewById(R.id.signinrecord_item_num);
		TextView item_name = (TextView) vi
				.findViewById(R.id.signinrecord_item_name);

		SignRecordInfo theInfo = new SignRecordInfo();
		theInfo = data.get(position);
		if(theInfo.markerIndex == -1){
			relativeLayout.setVisibility(View.GONE);

		}else {
			item_num.setText(String.valueOf(theInfo.markerIndex + 1));
		}

		item_name.setText(theInfo.SIGNINADD);//地址
		return vi;
	}
	public void setInfolist(List<SignRecordInfo> data) {
		this.data = data;
	}
}
class RecordAdapter1 extends BaseAdapter{
	private Activity activity;
	private List<SignRecordInfo> data;
	private LayoutInflater inflater = null;

	public RecordAdapter1(Activity a, List<SignRecordInfo> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return data.size();
	}

	public SignRecordInfo getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = View.inflate(activity, R.layout.item_recordmap, null);
		TextView item_num = (TextView) vi.findViewById(R.id.signinrecord_item_num);
		TextView item_name = (TextView) vi.findViewById(R.id.signinrecord_item_name);
		RelativeLayout rl = (RelativeLayout) vi.findViewById(R.id.recoder_ico);
		SignRecordInfo theInfo = new SignRecordInfo();
		theInfo = data.get(position);
		if(theInfo.markerIndex == 0){
			rl.setVisibility(View.GONE);//没有数据时图片都不显示
		}else {
			item_num.setText(String.valueOf(theInfo.markerIndex + 1));
		}
		item_name.setText(theInfo.USERNAME);//客户经理
		item_name.setTag(theInfo.USERID);
		return vi;
	}
	 public void setInfolist(List<SignRecordInfo> data) {
		this.data = data;
	}

}