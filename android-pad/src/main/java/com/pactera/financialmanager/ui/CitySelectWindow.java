package com.pactera.financialmanager.ui;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow;

import com.pactera.financialmanager.db.AreaDao;
import com.pactera.financialmanager.entity.AreaEntity;
import com.pactera.financialmanager.ui.LocationSelectWindow.CallBackInterface;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 地址选择器
 *
 * @author JAY
 *
 */
public class CitySelectWindow {

	private static AreaDao dao;
	//	private String strLocation1 = "北京市";
//	private String strLocation2 = "市辖区";
//	private String strLocation3 = "东城区";
//	private String strLocation4 = "东华门街道办事处";
//	private String strLocation5 = "";
	private String strLocation1 = "";//默认设值已全部取消
	private String strLocation2 = "";
	private String strLocation3 = "";
	private String strLocation4 = "";
	private String strLocation5 = "";
	private static List<AreaEntity> listProvince = new ArrayList<AreaEntity>();// 省
	private List<AreaEntity> listCity = new ArrayList<AreaEntity>();// 市
	private List<AreaEntity> listArea = new ArrayList<AreaEntity>();// 区
	private List<AreaEntity> listCounty = new ArrayList<AreaEntity>();// 县
	private List<AreaEntity> listStreet = new ArrayList<AreaEntity>();// 街道




	private boolean isShowcity = true;
	private  boolean isShowArea = true;
	private  boolean isShowCounty = true;
	private  boolean isShowStreet = true;

	public boolean isShowcity() {
		return isShowcity;
	}

	public void setShowcity(boolean showcity) {
		isShowcity = showcity;
	}

	public boolean isShowArea() {
		return isShowArea;
	}

	public void setShowArea(boolean showArea) {
		isShowArea = showArea;
	}

	public boolean isShowCounty() {
		return isShowCounty;
	}

	public void setShowCounty(boolean showCounty) {
		isShowCounty = showCounty;
	}

	public boolean isShowStreet() {
		return isShowStreet;
	}

	public void setShowStreet(boolean showStreet) {
		isShowStreet = showStreet;
	}

	private String location1, location2, location3, location4,
	//			location5 = "110101001";//
	location5 = "";//默认武汉市辖区
//				location5 = "110101001";//默认北京东华门街道办事处


	private String location;
	private String theLocation = "0";
	private static View viewLocation;
	private LocationSelectWindow mLocationSelect;
	private PopupWindow popupWindow;
	Activity activity;
	View view;

	private int indexHuBei = 0;//默认选湖北省


	public CitySelectWindow(Activity activity,View view) {
		super();
		this.activity = activity;
		this.view=view;
		mLocationSelect = new LocationSelectWindow(activity);
	}

	public String getID() {
		return theLocation;
	}

	public String getLocation() {

		return location;
	}

	public void showCity(final CallBackCity callBack) {

		mLocationSelect
				.setOnLocationSelectListener(new OnLocationSelectListener() {
					@Override
					public void setOnLocationSelectListener1(String id,
															 String name) {
						location1 = id;
						strLocation1 = name;

						setCityData();

						setAreaData();
						setCountyData();
						setStreetData();

						new Thread() {
							public void run() {
								listCity = dao.getAllMessage(location1);
								mHandler.sendEmptyMessage(2);
							};
						}.start();
					}

					@Override
					public void setOnLocationSelectListener2(String id,
															 String name) {
						location2 = id;
						strLocation2 = name;

						setAreaData();
						setCountyData();
						setStreetData();

						new Thread() {
							public void run() {
								listArea = dao.getAllMessage(location2);
								if (listArea.size() > 0) {
									location3 = listArea.get(0).getId();
								} else {
									location3 = location2;
								}

								mHandler.sendEmptyMessage(3);
							};
						}.start();
					}

					@Override
					public void setOnLocationSelectListener3(String id,
															 String name) {
						location3 = id;
						strLocation3 = name;

						setCountyData();
						setStreetData();

						new Thread() {
							public void run() {
								listCounty = dao.getAllMessage(location3);
								if (listCounty.size() > 0) {
									location4 = listCounty.get(0).getId();
								} else {
									location4 = location3;
								}

								mHandler.sendEmptyMessage(4);
							};
						}.start();
					}

					@Override
					public void setOnLocationSelectListener4(String id,
															 String name) {

						location4 = id;
						strLocation4 = name;
						setStreetData();

						new Thread() {
							public void run() {
								listStreet = dao.getAllMessage(location4);
								if (listStreet.size() > 0) {
									location5 = listStreet.get(0).getId();
								} else {
									location5 = location4;
								}

								mHandler.sendEmptyMessage(5);
							};
						}.start();
					}

					@Override
					public void setOnLocationSelectListener5(String id,
															 String name) {
						// TODO Auto-generated method stub
						location5 = id;
						strLocation5 = name;

					}
				});
		//根据显示的级别设置级别的id
		mLocationSelect.setCallBack(new CallBackInterface() {
			@Override
			public void execute(String where) {
				if (where.equals("button")) {
					if (!isShowcity) {
						theLocation = location1;
						location = strLocation1;
					} else if (!isShowArea) {
						theLocation = location2;
						if(strLocation1.equals(strLocation2)) {
							location = strLocation1;
						}else {
							location = strLocation1 + strLocation2;
						}

					} else if (!isShowCounty) {
						theLocation = location3;
						location = strLocation1 + strLocation2 + strLocation3;
						if(strLocation1.equals(strLocation2)) {
							location = strLocation1;
						}else {
							location = strLocation1 + strLocation2;
						}
						if(strLocation2.equals(strLocation3)) {
							location = strLocation1;
						}else {
							location = location + strLocation3;
						}
					} else if (!isShowStreet) {
						theLocation = location4;
						location = strLocation1 + strLocation2 + strLocation3
								+ strLocation4;
					} else {
						theLocation = location5;
						location = strLocation1 + strLocation2 + strLocation3
								+ strLocation4 + strLocation5;
					}
					// viewLocation.setVisibility(View.GONE);
					if (popupWindow != null) {
						popupWindow.dismiss();
					}
					if (callBack != null) {
						callBack.CallBackInfo(location, theLocation);
					}
				}
			}
		});

		// if (listProvince.size() <= 0) {
		// dao = new AreaDao(activity);
		// openRowDbank();
		// }

		// if (popupWindow != null && !popupWindow.isShowing()) {
		dao = new AreaDao(activity);
		openRowDbank();
		showLocationWindow();
		// }
	}

	private void setCityData() {
		if (isShowcity) {
			listCity = dao.getAllMessage(location1);
			if (listCity.size() > 0) {
				strLocation2 = listCity.get(0).getName();
				location2 = listCity.get(0).getId();
				listArea = dao.getAllMessage(location2);
			} else {
				strLocation2 = "";
				strLocation3 = "";
				strLocation4 = "";
				strLocation5 = "";
				location2 = location1;
				listCity.clear();
				listArea.clear();
				listCounty.clear();
				listStreet.clear();
			}
		}
	}

	private void setAreaData() {
		if (isShowcity && isShowArea) {

			if (listArea.size() > 0) {
				strLocation3 = listArea.get(0).getName();
				location3 = listArea.get(0).getId();
				listCounty = dao.getAllMessage(location3);
				Log.i("1111111", "1_2:" + location3);
			} else {
				strLocation3 = "";
				strLocation4 = "";
				strLocation5 = "";
				location3 = location2;
				listCounty.clear();
				listStreet.clear();
			}
		}
	}

	private void setCountyData() {
		if (isShowcity && isShowArea && isShowCounty) {


			if (listCounty.size() > 0) {
				strLocation4 = listCounty.get(0).getName();
				location4 = listCounty.get(0).getId();
				listStreet = dao.getAllMessage(location4);
				Log.i("1111111", "1_3:" + location4);
			} else {
				strLocation4 = "";
				strLocation5 = "";
				location4 = location3;
				listStreet.clear();
			}
		}
	}

	private void setStreetData() {
		if (isShowcity && isShowArea && isShowCounty && isShowStreet) {
			if (listStreet.size() > 0) {
				strLocation5 = listStreet.get(0).getName();
				location5 = listStreet.get(0).getId();
			} else {
				strLocation5 = "";
				location5 = location4;
			}

			new Thread() {
				public void run() {
					listCity = dao.getAllMessage(location5);
					mHandler.sendEmptyMessage(2);
				}

				;
			}.start();
		}
	}

	/** 获取所有省份列表 */
	private void openRowDbank() {
		new Thread() {
			public void run() {
				listProvince = dao.getAllMessage("000000");
				if (!listProvince.isEmpty()) {
					for (int i = 0, size = listProvince.size(); i < size; i++) {
						if (listProvince.get(i).getName().equals("湖北省")) {
							indexHuBei = i;
						}
					}
					if(isShowcity) {
						listCity = dao.getAllMessage(listProvince.get(indexHuBei).getId());
					}
					if (!listCity.isEmpty() && isShowArea)
						listArea = dao.getAllMessage(listCity.get(0).getId());
					if (!listArea.isEmpty() && isShowCounty)
						listCounty = dao.getAllMessage(listArea.get(0).getId());
					if (!listCounty.isEmpty() && isShowStreet)
						listStreet = dao.getAllMessage(listCounty.get(0).getId());

					mHandler.sendEmptyMessage(1);
				}
			}
		}.start();
	}

	private MyHandler mHandler = new MyHandler(activity) {
		public void handleMessage(android.os.Message msg) {
			if (msg.what < 2) {
				// 显示省份列表
				mLocationSelect.setProvince(listProvince,indexHuBei);
			}
			if (msg.what  < 3) {
				if(isShowcity) {
					mLocationSelect.setCity(listCity);
				}else{
					mLocationSelect.setCity(null);
				}
			}
			if (msg.what < 4) {
				if(isShowcity && isShowArea) {
					mLocationSelect.setArea(listArea);
				}else{
					mLocationSelect.setArea(null);
				}
			}
			if (msg.what < 5) {
				if(isShowcity && isShowArea &&  isShowCounty) {
					mLocationSelect.setCounty(listCounty);
				}else{
					mLocationSelect.setCounty(null);
				}
			}
			if (msg.what < 6) {
				if(isShowcity && isShowArea &&  isShowCounty && isShowStreet) {
					mLocationSelect.setStreet(listStreet);
				}else{
					mLocationSelect.setStreet(null);
				}
			}
		};
	};
	int showSelectNum = 3;

	private void showLocationWindow() {
		// manager = (WindowManager) activity
		// .getSystemService(Context.WINDOW_SERVICE);
//		if(!isShowcity){
//			showSelectNum = 1;
//		}else if(!isShowArea){
//			showSelectNum = 2;
//		}else if(!isShowCounty){
//			showSelectNum = 3;
//		}else if(!isShowStreet ){
//			showSelectNum = 4;
//		}
		viewLocation = mLocationSelect.getWindowView();
		//
		// LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.WRAP_CONTENT,
		// WindowManager.LayoutParams.FIRST_SUB_WINDOW,
		// WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
		// PixelFormat.RGBA_8888);
		// params.gravity = Gravity.BOTTOM;
		// params.dimAmount = 0.7f;
		// // 使用系统自带动画
		// manager.addView(viewLocation, params);

		popupWindow = new PopupWindow(viewLocation, WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.WRAP_CONTENT);
		// 设置焦点在弹窗上
		popupWindow.setFocusable(true);
		popupWindow.showAtLocation(view, Gravity.BOTTOM,
				0, 0);

	}

	// 回调
	public interface CallBackCity {
		public void CallBackInfo(String location, String theLocation);
	}

	public void destory() {
		// if (manager != null) {
		// manager.removeViewImmediate(viewLocation);
		// }
		if (popupWindow != null) {
			popupWindow.dismiss();
		}
		this.activity = null;
		dao.closeDataBase();
		// mLocationSelect.destory();
		// viewLocation.destroyDrawingCache();
	}
}

      class MyHandler extends Handler {
	WeakReference<Activity> mActivityReference;

	public MyHandler(Activity activity) {
		mActivityReference = new WeakReference<Activity>(activity);
	}
}
