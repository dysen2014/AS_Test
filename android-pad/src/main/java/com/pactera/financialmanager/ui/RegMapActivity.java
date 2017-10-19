//package com.pactera.financialmanager.ui;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import com.baidu.mapapi.BMapManager;
//import com.baidu.mapapi.cloud.CloudListener;
//import com.baidu.mapapi.cloud.CloudSearchResult;
//import com.baidu.mapapi.cloud.DetailSearchResult;
//import com.baidu.mapapi.map.ItemizedOverlay;
//import com.baidu.mapapi.map.MKMapTouchListener;
//import com.baidu.mapapi.map.MKMapViewListener;
//import com.baidu.mapapi.map.MapController;
//import com.baidu.mapapi.map.MapPoi;
//import com.baidu.mapapi.map.MapView;
//import com.baidu.mapapi.map.MyLocationOverlay;
//import com.baidu.mapapi.map.OverlayItem;
//import com.baidu.mapapi.map.PopupClickListener;
//import com.baidu.mapapi.map.PopupOverlay;
//import com.baidu.platform.comapi.basestruct.GeoPoint;
//import com.pactera.financialmanager.R;
//import com.pactera.financialmanager.util.Constants;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.graphics.Bitmap;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.MeasureSpec;
//import android.widget.EditText;
//import android.widget.Toast;
//
///**
// * 签到功能
// * 
// * @author YPJ
// * 
// */
//public class RegMapActivity extends ParentActivity implements CloudListener {
//
//	/**
//	 * MapView 是地图主控件
//	 */
//	private MapView mMapView = null;
//	/**
//	 * 地图覆盖物
//	 */
//	private MyLocationOverlay mLocationOverlay;
//	/**
//	 * 用MapController完成地图控制
//	 */
//	private MapController mMapController = null;
//	/**
//	 * MKMapViewListener 用于处理地图事件回调
//	 */
//	// 定义搜索服务类
//	MKMapViewListener mMapListener = null;
//	MKMapTouchListener TouchListener = null;
//
//	private BMapManager mBMapMan = null;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		mBMapMan = new BMapManager(getApplication());
//		mBMapMan.init(Constants.MAP_AK, null);
//		setContentView(R.layout.activity_regmap);
//		mMapView = (MapView) findViewById(R.id.bmapView);
//
//		/**
//		 * 获取地图控制器
//		 */
//		mMapController = mMapView.getController();
//		 // 设置中心点
//		 mMapController.setCenter(new GeoPoint(
//		 (int) (Constants.TheLatitude * 1E6),
//		 (int) (Constants.TheLongitude * 1E6)));
////		// 设置中心点
////		mMapController.setCenter(new GeoPoint((int) (30.542295 * 1E6),
////				(int) (114.351662 * 1E6)));
//		/**
//		 * 设置地图是否响应点击事件 .
//		 */
//		mMapController.enableClick(true);
//		// 设置是否可以拖动
//		mMapController.setScrollGesturesEnabled(false);
//		/**
//		 * 设置地图缩放级别
//		 */
//		mMapController.setZoom(15);
//		// 获取当前位置的覆盖物
//		mLocationOverlay = new MyLocationOverlay(this.mMapView);
//		// 添加定位覆盖物
//		mMapView.getOverlays().add(mLocationOverlay);
//		mMapView.setSatellite(false); // 不显示卫星路线
//		// mMapView.setTraffic(true); // 显示交通路线
//		mMapView.setBuiltInZoomControls(true);
//
//		// 点击事件
//		TouchListener = new MKMapTouchListener() {
//			@Override
//			public void onMapLongClick(GeoPoint arg0) {
//				// TODO Auto-generated method stub
//				Toast.makeText(RegMapActivity.this, "1:" + arg0.toString(),
//						Toast.LENGTH_SHORT).show();
//			}
//
//			@Override
//			public void onMapDoubleClick(GeoPoint arg0) {
//
//				// TODO Auto-generated method stub
//				Toast.makeText(RegMapActivity.this, "2:" + arg0.toString(),
//						Toast.LENGTH_SHORT).show();
//			}
//
//			@Override
//			public void onMapClick(GeoPoint arg0) {
//				// TODO Auto-generated method stub
//				Toast.makeText(RegMapActivity.this, "3:" + arg0.toString(),
//						Toast.LENGTH_SHORT).show();
//				// 设置中心点
//				mMapController.setCenter(arg0);
//			}
//		};
//		/**
//		 * MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
//		 */
//		mMapListener = new MKMapViewListener() {
//			@Override
//			public void onMapMoveFinish() {
//				/**
//				 * 在此处理地图移动完成回调 缩放，平移等操作完成后，此回调被触发
//				 */
//			}
//
//			@Override
//			public void onClickMapPoi(MapPoi mapPoiInfo) {
//				/**
//				 * 在此处理底图poi点击事件 显示底图poi名称并移动至该点 设置过：
//				 * mMapController.enableClick(true); 时，此回调才能被触发
//				 * 
//				 */
//				String title = "";
//				if (mapPoiInfo != null) {
//					title = mapPoiInfo.strText;
//					Toast.makeText(RegMapActivity.this, title,
//							Toast.LENGTH_SHORT).show();
//					mMapController.animateTo(mapPoiInfo.geoPt);
//				}
//			}
//
//			@Override
//			public void onGetCurrentMap(Bitmap b) {
//				/**
//				 * 当调用过 mMapView.getCurrentMap()后，此回调会被触发 可在此保存截图至存储设备
//				 */
//			}
//
//			@Override
//			public void onMapAnimationFinish() {
//				/**
//				 * 地图完成带动画的操作（如: animationTo()）后，此回调被触发
//				 */
//			}
//
//			/**
//			 * 在此处理地图载完成事件
//			 */
//			@Override
//			public void onMapLoadFinish() {
//				Toast.makeText(RegMapActivity.this, "地图加载完成",
//						Toast.LENGTH_SHORT).show();
//
//			}
//		};
//
//		mMapView.regMapTouchListner(TouchListener);
//		mMapView.regMapViewListener(mBMapMan, mMapListener);
//		addAllMarker();
//	}
//
//	@Override
//	public void onGetDetailSearchResult(DetailSearchResult result, int arg1) {
//		// TODO Auto-generated method stub
//		if (result != null) {
//			if (result.poiInfo != null) {
//				Toast.makeText(RegMapActivity.this, result.poiInfo.title,
//						Toast.LENGTH_SHORT).show();
//			} else {
//				Toast.makeText(RegMapActivity.this, "status:" + result.status,
//						Toast.LENGTH_SHORT).show();
//			}
//		}
//	}
//
//	@Override
//	public void onGetSearchResult(CloudSearchResult arg0, int arg1) {
//		// TODO Auto-generated method stub
//
//	}
//
//	/**
//	 * 添加地图标记点
//	 */
//	private void addAllMarker() {
//		// TODO Auto-generated method stub
//
//		mMapView.getOverlays().clear();
//		CloudOverlay ov = new CloudOverlay(this, mMapView);
//
//		int latitude = (int) (Constants.TheLatitude * 1E6);
//		int longitude = (int) (Constants.TheLongitude * 1E6);
//
//		Drawable d = getResources().getDrawable(R.drawable.icon_marka);
//		OverlayItem item = new OverlayItem(new GeoPoint(latitude, longitude),
//				"", Constants.LocStr);
//		item.setMarker(d);
//
//		ov.addItem(item);
//
//		mMapView.getOverlays().add(ov);
//		mMapView.refresh();
//
//	}
//
//	/**
//	 * 删除所有标记
//	 */
//	public void removeAllMarker() {
//		mMapView.getOverlays().clear();
//		mMapView.refresh();
//	}
//
//}
//
///**
// * 添加POI点
// * 
// * @author JAY
// * 
// */
//class CloudOverlay extends ItemizedOverlay {
//
//	Activity mContext;
//	PopupOverlay pop = null;
//	EditText regmap_customer, regmap_contents, regmap_address, regmap_time;
//	private int clickedTapIndex = -1;
//
//	public CloudOverlay(Activity context, MapView mMapView) {
//		super(null, mMapView);
//		mContext = context;
//		pop = new PopupOverlay(mMapView, new PopupClickListener() {
//			@Override
//			public void onClickedPopup(int arg0) {
//				// TODO Auto-generated method stub
//
//			}
//
//		});
//	}
//
//	@Override
//	protected Object clone() throws CloneNotSupportedException {
//		// TODO Auto-generated method stub
//		return super.clone();
//	}
//
//	@Override
//	protected boolean onTap(int index) {
//		// if (TextUtils.isEmpty(Constants.LocStr)) {
//		// // 点击自己位置marker，不做任何处理
//		// return true;
//		// }
//		clickedTapIndex = index;
//		SimpleDateFormat formatter = new SimpleDateFormat(
//				"yyyy年MM月dd日   HH:mm:ss     ");
//		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
//		final String str_nowtime = formatter.format(curDate);
//
//		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//		LayoutInflater factory = LayoutInflater.from(mContext);
//		final View textEntryView = factory.inflate(R.layout.signin_marker_pop,
//				null);
//		regmap_customer = (EditText) textEntryView
//				.findViewById(R.id.regmap_customer);
//		regmap_contents = (EditText) textEntryView
//				.findViewById(R.id.regmap_contents);
//		regmap_address = (EditText) textEntryView
//				.findViewById(R.id.regmap_address);
//		regmap_time = (EditText) textEntryView.findViewById(R.id.regmap_time);
//		regmap_address.setEnabled(false);
//		regmap_time.setEnabled(false);
//		regmap_address.setText(Constants.LocStr);
//		regmap_time.setText(str_nowtime);
//		builder.setView(textEntryView);
//		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int whichButton) {
//				showDialog("客户名字：" + regmap_customer.getText().toString()
//						+ ",工作信息：" + regmap_contents.getText().toString()
//						+ ",工作地点：" + Constants.LocStr + ",工作时间：" + str_nowtime);
//
//			}
//		});
//		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int whichButton) {
//
//			}
//		});
//
//		builder.create().show();
//
//		super.onTap(index);
//		return false;
//	}
//
//	private void showDialog(String str) {
//		new AlertDialog.Builder(mContext).setMessage(str).show();
//	}
//
//	/*
//	 * 覆盖物其他区域点击回调
//	 */
//	public boolean onTap(GeoPoint pt, MapView mapView) {
//		if (pop != null) {
//			clickedTapIndex = -1;
//			pop.hidePop();
//		}
//		super.onTap(pt, mapView);
//		return false;
//	}
//
//	private Bitmap convertViewToBitMap(View v) {
//		// 启用绘图缓存
//		v.setDrawingCacheEnabled(true);
//		// 调用下面这个方法非常重要，如果没有调用这个方法，得到的bitmap为null
//		v.measure(MeasureSpec.makeMeasureSpec(210, MeasureSpec.EXACTLY),
//				MeasureSpec.makeMeasureSpec(120, MeasureSpec.EXACTLY));
//		// 这个方法也非常重要，设置布局的尺寸和位置
//		v.layout(0, 0, v.getMeasuredWidth() + 20, v.getMeasuredHeight());
//		// 获得绘图缓存中的Bitmap
//		v.buildDrawingCache();
//		return v.getDrawingCache();
//	}
//
//}
