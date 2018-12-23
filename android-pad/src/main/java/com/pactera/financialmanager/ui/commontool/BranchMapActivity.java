package com.pactera.financialmanager.ui.commontool;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
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
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.model.BranchInfo;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.Tool;
import com.pactera.financialmanager.util.Constants.requestType;

public class BranchMapActivity extends ParentActivity {

	// MapView 是地图主控件
	private MapView mMapView = null;
	ListView branchInfo_listView;
	EditText branchInfo_editText;
	ImageView branchInfo_img;

	// 地图管理
	private BaiduMap mBaiduMap;
	private LayoutInflater factory;

	private HConnection HCon;
	private final int returnIndex = 1;

	private View branchView;// 地址信息view
	private TextView branchName, branchAddress, branchPhone;

	// 定位相关
	private LocationClient mLocClient;
	// 当前位置经纬度
	private LatLng ll;
	// 定位监听事件
	public MyLocationListenner myListener = new MyLocationListenner();

	// BitmapDescriptor icon_maker = BitmapDescriptorFactory
	// .fromResource(R.drawable.icon_gcoding);
	// 定位图标
	BitmapDescriptor icon_maker = BitmapDescriptorFactory.fromResource(R.drawable.map_coordinate);
	// 定位图标
	BitmapDescriptor icon_maker_blue = BitmapDescriptorFactory.fromResource(R.drawable.map_coordinate_select);

	List<BranchInfo> returnInfo = new ArrayList<BranchInfo>();
	List<BranchInfo> ViewInfo = new ArrayList<BranchInfo>();
	BranchInfoAdapter branchInfoAdapter;
	List<Marker> ooMarker = new ArrayList<Marker>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_branchmap);
//		initTitle(this, "常用工具");
		Intent intent = getIntent();
		String Name = intent.getStringExtra("Name");
		String NameInfo = intent.getStringExtra("NameInfo");
		initTitle(this, Name, true,NameInfo);
		// 初始化界面
		setView();
		// 获取地图控制
		mBaiduMap = mMapView.getMap();
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
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
		option.setAddrType("all");
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(4000);
		mLocClient.setLocOption(option);
		if (!mLocClient.isStarted()) {
			mLocClient.start();// 开始定位
		}

		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker markerInfo) {
				mBaiduMap.hideInfoWindow();
				for (Marker itemMarker : ooMarker) {
					itemMarker.setIcon(icon_maker);
				}
				// mMapView.refreshDrawableState();
				markerInfo.setIcon(icon_maker_blue);
				int markerIndex = markerInfo.getZIndex();
				for (BranchInfo item : returnInfo) {
					// Log.i("1111111", item.BranchIndex+",markerIndex");
					if (item.BranchIndex == markerIndex && !TextUtils.isEmpty(item.LATITUDE)
							&& !TextUtils.isEmpty(item.LONGITUDE)) {
						double lati = Double.parseDouble(item.LATITUDE);
						double loti = Double.parseDouble(item.LONGITUDE);
						LatLng llNew = new LatLng(lati, loti);

						// 显示信息
						branchName.setText(item.BRNAME);
						branchAddress.setText(item.ADDRESS);
						branchPhone.setText(item.PHONENO);
						// record_time.setText(item.CREATE_TIME);

						InfoWindow infoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(branchView), llNew, -47,
								null);
						// infoWindow = new InfoWindow(signinView, llNew, null);
						mBaiduMap.showInfoWindow(infoWindow);
						return true;
					}
				}

				return false;
			}
		});

		// 监听地图的点击事件隐藏弹出框
		mBaiduMap.setOnMapClickListener(new OnMapClickListener() {
			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				Toast.makeText(BranchMapActivity.this, arg0.getName(), Toast.LENGTH_SHORT).show();
				return true;
			}

			@Override
			public void onMapClick(LatLng arg0) {
				mBaiduMap.hideInfoWindow();

			}
		});
//搜索分支机构
		branchInfo_img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String theEdit = branchInfo_editText.getText().toString();

				if (!TextUtils.isEmpty(theEdit)) {
					ViewInfo.clear();
					getData(theEdit);
				} else {
					getData("");
					ViewInfo = returnInfo;
				}

			}
		});

		branchInfo_listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				BranchInfo theInfo = branchInfoAdapter.getItem(position);

				LatLng thell = new LatLng(Tool.DoubleParse(theInfo.LATITUDE), Tool.DoubleParse(theInfo.LONGITUDE));
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(thell, 19);
				mBaiduMap.animateMapStatus(u);

				// 清除所有图层
				mMapView.getMap().clear();
				ooMarker.clear();

				MarkerOptions theMarker = new MarkerOptions().position(thell).icon(icon_maker).zIndex(theInfo.BranchIndex)
						.draggable(true);
				Marker mMarker = (Marker) (mBaiduMap.addOverlay(theMarker));
				ooMarker.add(mMarker);
				mMapView.refreshDrawableState();

			}
		});
	}

	private void setView() {
		// TODO Auto-generated method stub
		factory = LayoutInflater.from(this);
		mMapView = (MapView) findViewById(R.id.branchmap_bmapView);
		branchInfo_listView = (ListView) findViewById(R.id.branchmap_listView);
		branchInfo_editText = (EditText) findViewById(R.id.branchmap_editText);
		branchInfo_img = (ImageView) findViewById(R.id.branchmap_img);//搜索图片

		branchView = factory.inflate(R.layout.brachmap_marker_info, null);
		branchName = (TextView) branchView.findViewById(R.id.brachmap_marker_name);
		branchAddress = (TextView) branchView.findViewById(R.id.brachmap_marker_address);
		branchPhone = (TextView) branchView.findViewById(R.id.brachmap_marker_phone);

		branchInfoAdapter = new BranchInfoAdapter(this);
		branchInfo_listView.setAdapter(branchInfoAdapter);
	}

	/**
	 * 获取数据
	 */
	private void getData(String theBrachName) {
		if (ll != null && ll.longitude != 0 && ll.latitude != 0) {
			String theInfo = "&spareOne=" + theBrachName + "&spareTwo=5&jsonData={\"LONGITUDE\"=\"" + ll.longitude+ "\",\"LATITUDE\"=\"" + ll.latitude + "\"}";

			HCon = RequestInfo(this, requestType.Other, InterfaceInfo.BranchMap_Get, theInfo, returnIndex);
		}

	}

	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			break;
		case returnIndex:
			HResponse res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			JSONObject tmpJsonObject = res.dataJsonObject;
			Log.i("", tmpJsonObject.toString());
			readJson(tmpJsonObject);
			break;
		}
	}

	private void readJson(JSONObject tmpJsonObject) {
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
			Toast.makeText(this, "获取成功！", Toast.LENGTH_SHORT).show();
			JSONArray theInfo;
			try {
				theInfo = tmpJsonObject.getJSONArray("group");
				if (theInfo.length() < 0) {
					Toast.makeText(this, "没有数据!", Toast.LENGTH_SHORT).show();
					return;
				} else {
					returnInfo.clear();
					//限制显示100个
					for (int i = 0; i < theInfo.length()&&i<99; i++) {
						JSONObject temps = (JSONObject) theInfo.opt(i);
						BranchInfo braInfo = new BranchInfo();
						braInfo.BRID = Tool.getJsonValue(temps, "BRID");// 编号
						braInfo.BRNAME = Tool.getJsonValue(temps, "BRNAME");// 名字
						braInfo.ADDRESS = Tool.getJsonValue(temps, "ADDRESS");// 地址
						braInfo.PHONENO = Tool.getJsonValue(temps, "PHONENO");// 电话
						braInfo.LATITUDE = Tool.getJsonValue(temps, "LATITUDE");// 纬度
						braInfo.LONGITUDE = Tool.getJsonValue(temps, "LONGITUDE");// 经度
						braInfo.POSTCODE = Tool.getJsonValue(temps, "POSTCODE");// 邮编
						returnInfo.add(braInfo);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			addInfo();
		}
	}

	//筛选数据
	private void addInfo() {
		// 清除所有图层
		mMapView.getMap().clear();
		ooMarker.clear();
		List<LatLng> points = new ArrayList<LatLng>();
		int indexID = 0;
		ViewInfo.clear();
		for (BranchInfo item : returnInfo) {
			if (!TextUtils.isEmpty(item.LATITUDE) && !TextUtils.isEmpty(item.LONGITUDE)) {
				// Log.i("111111", "item.BranchLatitude:" + item.BranchLatitude
				// + ",item.BranchLongitude:" + item.BranchLongitude);
				double lati = Tool.DoubleParse(item.LATITUDE);
				double loti = Tool.DoubleParse(item.LONGITUDE);
				// int indexID = Integer.parseInt(item.SIGNINID);
				Log.i("111111", "lati:" + lati + ",loti:" + loti);
				if (lati != 0 && loti != 0) {
					item.BranchIndex = indexID;
					LatLng llpoint = new LatLng(lati, loti);
					points.add(llpoint);
					MarkerOptions theMarker = new MarkerOptions().position(llpoint).icon(icon_maker).zIndex(indexID).draggable(true);
					Marker mMarker = (Marker) (mBaiduMap.addOverlay(theMarker));
					ooMarker.add(mMarker);
					indexID++;
					ViewInfo.add(item);
				}
			}

		}

		for (Marker itemMarker : ooMarker) {
			itemMarker.setIcon(icon_maker);
		}

		if (ViewInfo != null && !ViewInfo.isEmpty()) {
			LatLng thell = new LatLng(Tool.DoubleParse(ViewInfo.get(0).LATITUDE), Tool.DoubleParse(ViewInfo.get(0).LONGITUDE));
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(thell, 11);//进入界面的初始比例尺
			mBaiduMap.animateMapStatus(u);
		}

		branchInfoAdapter.setInfolist(ViewInfo);
		branchInfoAdapter.notifyDataSetChanged();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 退出时销毁定位
		mLocClient.stop();
		// MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		mMapView.onDestroy();
		// 回收 bitmap 资源
		icon_maker.recycle();

	}

	/**
	 * 定位SDK监听函数
	 */
	class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (ll != null && ll.latitude != 0 && ll.longitude != 0) {
				LatLng theNew = new LatLng(location.getLatitude(), location.getLongitude());
				if (theNew != ll) {
					// ll = theNew;
					// // MapStatusUpdate u =
					// MapStatusUpdateFactory.newLatLng(ll);
					// MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(
					// ll, 11);
					// mBaiduMap.animateMapStatus(u);
					// getData();
				}
			} else {
				ll = new LatLng(location.getLatitude(), location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 19);
				mBaiduMap.animateMapStatus(u);
				getData("");
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
 * 网点分布Adapter
 * 
 * @author JAY
 * 
 */
class BranchInfoAdapter extends BaseAdapter {
	private Context context;
	private List<BranchInfo> data;

	public BranchInfoAdapter(Context context) {
		super();
		this.context = context;
	}

	@Override
	public int getCount() {
		if (data != null && data.size() > 0) {
			return data.size();
		} else {
			return 0;
		}
	}


	public BranchInfo getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = View.inflate(context, R.layout.item_recordmap, null);
		TextView item_num = (TextView) vi.findViewById(R.id.signinrecord_item_num);
		TextView item_name = (TextView) vi.findViewById(R.id.signinrecord_item_name);
		BranchInfo theInfo = new BranchInfo();
		theInfo = data.get(position);
		item_num.setText(String.valueOf(theInfo.BranchIndex + 1));
		item_name.setText(theInfo.BRNAME);
		return vi;
	}

	public void setInfolist(List<BranchInfo> infolist) {
		this.data = infolist;
	}

}
