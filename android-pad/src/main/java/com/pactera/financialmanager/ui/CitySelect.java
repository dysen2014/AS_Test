package com.pactera.financialmanager.ui;

/**
 * Created by pengmeng on 2016/9/27.
 */

import android.view.Gravity;

        import android.app.Activity;
        import android.os.Handler;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.View;
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
public class CitySelect {

    private static AreaDao dao;
    //	private String strLocation1 = "北京市";
//	private String strLocation2 = "市辖区";
//	private String strLocation3 = "东城区";
//	private String strLocation4 = "东华门街道办事处";
//	private String strLocation5 = "";
    private String strLocation1 = "湖北省";
    private String strLocation2 = "武汉市";
    private String strLocation3 = "市辖区";
    private String strLocation4 = "";
    private String strLocation5 = "";
    private static List<AreaEntity> listProvince = new ArrayList<AreaEntity>();// 省
    private List<AreaEntity> listCity = new ArrayList<AreaEntity>();// 市
    private List<AreaEntity> listArea = new ArrayList<AreaEntity>();// 区
    private List<AreaEntity> listCounty = new ArrayList<AreaEntity>();// 县
    private List<AreaEntity> listStreet = new ArrayList<AreaEntity>();// 街道

    private String location1, location2, location3, location4,
            location5 = "420101000000";//默认武汉市辖区
    //			location5 = "110101001";//默认北京东华门街道办事处
    private String location;
    private String theLocation = "0";
    private static View viewLocation;
    private LocationSelectWindow mLocationSelect;
    private PopupWindow popupWindow;
    Activity activity;
    View showView;

    private int indexHuBei = 0;//默认选湖北省


    public CitySelect(Activity activity,View showView) {
        super();
        this.activity = activity;
        this.showView=showView;
        mLocationSelect = new LocationSelectWindow(activity);
    }

    public String getID() {
        return theLocation;
    }

    public String getLocation() {

        return location;
    }

    public void showCity(final CallBackCitySelect callBack) {

        mLocationSelect
                .setOnLocationSelectListener(new OnLocationSelectListener() {
                    @Override
                    public void setOnLocationSelectListener1(String id,
                                                             String name) {
                        location1 = id;
                        strLocation1 = name;

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

                        if (listArea.size() > 0) {
                            strLocation3 = listArea.get(0).getName();
                            location3 = listArea.get(0).getId();
                            listCounty = dao.getAllMessage(location3);
//							Log.i("1111111", "1_2:" + location3);
                        } else {
                            strLocation3 = "";
                            strLocation4 = "";
                            strLocation5 = "";
                            location3 = location2;
                            listCounty.clear();
                            listStreet.clear();
                        }

                        if (listCounty.size() > 0) {
                            strLocation4 = listCounty.get(0).getName();
                            location4 = listCounty.get(0).getId();
                            listStreet = dao.getAllMessage(location4);
//							Log.i("1111111", "1_3:" + location4);
                        } else {
                            strLocation4 = "";
                            strLocation5 = "";
                            location4 = location3;
                            listStreet.clear();
                        }

                        if (listStreet.size() > 0) {
                            strLocation5 = listStreet.get(0).getName();
                            location5 = listStreet.get(0).getId();
                        } else {
                            strLocation5 = "";
                            location5 = location4;
                        }

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

                        listArea = dao.getAllMessage(location2);
                        if (listArea.size() > 0) {
                            strLocation3 = listArea.get(0).getName();
                            location3 = listArea.get(0).getId();
                            listCounty = dao.getAllMessage(location3);
                            Log.i("1111111", "2_1:" + listArea.get(0).getId());
                        } else {
                            strLocation3 = "";
                            strLocation4 = "";
                            strLocation5 = "";
                            location3 = location2;
                            listCounty.clear();
                            listStreet.clear();
                        }

                        if (listCounty.size() > 0) {
                            strLocation4 = listCounty.get(0).getName();
                            location4 = listCounty.get(0).getId();
                            listStreet = dao.getAllMessage(location4);
                            Log.i("1111111", "2_2:" + location4);
                        } else {
                            strLocation4 = "";
                            strLocation5 = "";
                            location4 = location3;
                            listStreet.clear();
                        }

                        if (listStreet.size() > 0) {
                            strLocation5 = listStreet.get(0).getName();
                            location5 = listStreet.get(0).getId();
                        } else {
                            strLocation5 = "";
                            location5 = location4;
                        }

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

                        listCounty = dao.getAllMessage(location3);
                        if (listCounty.size() > 0) {
                            strLocation4 = listCounty.get(0).getName();
                            location4 = listCounty.get(0).getId();
                            listStreet = dao.getAllMessage(listCounty.get(0)
                                    .getId());
                        } else {
                            strLocation4 = "";
                            strLocation5 = "";
                            location4 = location3;
                            listStreet.clear();
                        }

                        if (listStreet.size() > 0) {
                            strLocation5 = listStreet.get(0).getName();
                            location5 = listStreet.get(0).getId();
                        } else {
                            strLocation5 = "";
                            location5 = location4;
                        }

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

                        listStreet = dao.getAllMessage(location4);
                        if (listStreet.size() > 0) {
                            strLocation5 = listStreet.get(0).getName();
                            location5 = listStreet.get(0).getId();
                        } else {
                            strLocation5 = "";
                            location5 = location4;
                        }

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

        mLocationSelect.setCallBack(new CallBackInterface() {
            @Override
            public void execute(String where) {
                if (where.equals("button")) {
                    theLocation = location5;
                    location = strLocation1 + strLocation2 + strLocation3
                            + strLocation4 + strLocation5;
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
                    listCity = dao.getAllMessage(listProvince.get(indexHuBei).getId());
                    if (!listCity.isEmpty())
                        listArea = dao.getAllMessage(listCity.get(0).getId());
                    if (!listArea.isEmpty())
                        listCounty = dao.getAllMessage(listArea.get(0).getId());
                    if (!listCounty.isEmpty())
                        listStreet = dao.getAllMessage(listCounty.get(0).getId());

                    mHandler.sendEmptyMessage(1);
                }

                // if(listProvince == null || listProvince.size() == 0){
                // return;
                // }
                //
                // listCity = dao.getAllMessage(listProvince.get(0).getId());
                // if(listCity == null || listCity.size() == 0){
                // return;
                // }
                //
                // listArea = dao.getAllMessage(listCity.get(0).getId());
                // if(listArea == null || listArea.size() == 0){
                // return;
                // }
                //
                // listCounty = dao.getAllMessage(listArea.get(0).getId());
                // if(listCounty == null || listCounty.size() == 0){
                // return;
                // }
                //
                // listStreet = dao.getAllMessage(listCounty.get(0).getId());
                // mHandler.sendEmptyMessage(1);
            };
        }.start();
    }

    private MyHandlerSelect mHandler = new MyHandlerSelect(activity) {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                // 显示省份列表
                mLocationSelect.setProvince(listProvince,indexHuBei);
                mLocationSelect.setCity(listCity);
                mLocationSelect.setArea(listArea);
                mLocationSelect.setCounty(listCounty);
                mLocationSelect.setStreet(listStreet);
            }
            if (msg.what == 2) {
                mLocationSelect.setCity(listCity);
                mLocationSelect.setArea(listArea);
                mLocationSelect.setCounty(listCounty);
                mLocationSelect.setStreet(listStreet);
            }
            if (msg.what == 3) {
                mLocationSelect.setArea(listArea);
                mLocationSelect.setCounty(listCounty);
                mLocationSelect.setStreet(listStreet);
            }
            if (msg.what == 4) {
                mLocationSelect.setCounty(listCounty);
                mLocationSelect.setStreet(listStreet);
            }
            if (msg.what == 5) {
                mLocationSelect.setStreet(listStreet);
            }
        };
    };

    private void showLocationWindow() {
        // manager = (WindowManager) activity
        // .getSystemService(Context.WINDOW_SERVICE);
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

        popupWindow = new PopupWindow(viewLocation, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        // 设置焦点在弹窗上
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(showView, Gravity.BOTTOM,
                0, 0);

    }

    // 回调
    public interface CallBackCitySelect {
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

class MyHandlerSelect extends Handler {
    WeakReference<Activity> mActivityReference;

    public MyHandlerSelect(Activity activity) {
        mActivityReference = new WeakReference<Activity>(activity);
    }

}
