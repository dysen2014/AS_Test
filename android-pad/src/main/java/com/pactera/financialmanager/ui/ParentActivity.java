package com.pactera.financialmanager.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.dysen.common_res.common.utils.FormatUtil;
import com.jaeger.library.StatusBarUtil;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.app.MyApplication;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog.OnDateSetListener;
import com.pactera.financialmanager.datetimepicker.TimePickerDialog;
import com.pactera.financialmanager.datetimepicker.TimePickerDialog.OnTimeSetListener;
import com.pactera.financialmanager.ui.CitySelectWindow.CallBackCity;
import com.pactera.financialmanager.ui.model.User;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HRequest;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.DialogAlert;
import com.pactera.financialmanager.util.Tool;
import com.pactera.financialmanager.util.common_permission.PermissionUtils;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import q.rorbin.badgeview.QBadgeView;

public class ParentActivity extends FragmentActivity {
	// 正式发布
	public static boolean IS_OUTlINE = true; // 是否离线
	public static final ArrayList<Activity> activityList = new ArrayList<Activity>();
	public static final int DIALOG_ID = 10001;
	private ProgressDialog loadingDialog;
	private String dialogMsg = "loading...";
	public Handler handler;
	public int connectionIndex;

	public static String TMP_ROOT_DIR = "pactera";
	public static String ROOT_DIR;
	public static final String PDF_SUB_PAG_TAG = "pdf_sub_pag_tag";
	/** 接口类型 */
	public static final int HREQUEST_TYPE_HOST00 = 0;
	public static final int HREQUEST_TYPE_HOST01 = 1;
	public static final int HREQUEST_TYPE_HOSTPHOTO = 2;
	public static final int HREQUEST_TYPE_FILEHOST = 10;

	/** 产品中心 */
	public static final int ACTIVITY_TYPE_PC = 0;
	public static final String PC_PATH = "productcenter";
	// public static final String PC_JSONSAVE = "productcenter.bin";
	public static final String PC_BAOXIAN_JSONSAVE01 = "pc_baoxian01.bin";
	public static final String PC_BAOXIAN_JSONSAVE02 = "pc_baoxian02.bin";
	public static final String PC_BAOXIAN_JSONSAVE03 = "pc_baoxian03.bin";

	public static final String PC_CHUXU_JSONSAVE01 = "pc_chuxu01.bin";
	public static final String PC_CHUXU_JSONSAVE02 = "pc_chuxu02.bin";
	public static final String PC_CHUXU_JSONSAVE03 = "pc_chuxu03.bin";
	public static final String PC_CHUXU_JSONSAVE04 = "pc_chuxu04.bin";
	public static final String PC_CHUXU_JSONSAVE05 = "pc_chuxu05.bin";
	public static final String PC_CHUXU_JSONSAVE06 = "pc_chuxu06.bin";

	public static final String PC_EBANK_JSONSAVE01 = "pc_ebank01.bin";
	public static final String PC_EBANK_JSONSAVE02 = "pc_ebank02.bin";
	public static final String PC_EBANK_JSONSAVE03 = "pc_ebank03.bin";
	public static final String PC_EBANK_JSONSAVE04 = "pc_ebank04.bin";
	public static final String PC_EBANK_JSONSAVE05 = "pc_ebank05.bin";
	public static final String PC_EBANK_JSONSAVE06 = "pc_ebank06.bin";
	public static final String PC_EBANK_JSONSAVE07 = "pc_ebank07.bin";
	/** pc-对公业务 */
	public static final String PC_DUIGONG_JSONSAVE01 = "pc_duigong01.bin";
	public static final String PC_DUIGONG_JSONSAVE02 = "pc_duigong02.bin";
	public static final String PC_DUIGONG_JSONSAVE03 = "pc_duigong03.bin";
	public static final String PC_DUIGONG_JSONSAVE04 = "pc_duigong04.bin";

	public static final String PC_GERENDAIKUAN_JSONSAVE01 = "pc_gerendaikuan01.bin";
	public static final String PC_GERENDAIKUAN_JSONSAVE02 = "pc_gerendaikuan02.bin";
	public static final String PC_GERENDAIKUAN_JSONSAVE03 = "pc_gerendaikuan03.bin";
	public static final String PC_GERENDAIKUAN_JSONSAVE04 = "pc_gerendaikuan04.bin";
	public static final String PC_GERENDAIKUAN_JSONSAVE05 = "pc_gerendaikuan05.bin";
	public static final String PC_GERENDAIKUAN_JSONSAVE06 = "pc_gerendaikuan06.bin";
	public static final String PC_GERENDAIKUAN_JSONSAVE07 = "pc_gerendaikuan07.bin";
	public static final String PC_GERENDAIKUAN_JSONSAVE08 = "pc_gerendaikuan08.bin";
	public static final String PC_GERENDAIKUAN_JSONSAVE09 = "pc_gerendaikuan09.bin";
	public static final String PC_GERENDAIKUAN_JSONSAVE10 = "pc_gerendaikuan10.bin";
	public static final String PC_GERENDAIKUAN_JSONSAVE11 = "pc_gerendaikuan11.bin";

	public static final String PC_GONGMUJIJIN_JSONSAVE01 = "pc_gongmujijin01.bin";
	public static final String PC_GONGMUJIJIN_JSONSAVE02 = "pc_gongmujijin02.bin";
	public static final String PC_GONGMUJIJIN_JSONSAVE03 = "pc_gongmujijin03.bin";
	public static final String PC_GONGMUJIJIN_JSONSAVE04 = "pc_gongmujijin04.bin";
	public static final String PC_GONGMUJIJIN_JSONSAVE05 = "pc_gongmujijin05.bin";
	public static final String PC_GONGMUJIJIN_JSONSAVE06 = "pc_gongmujijin06.bin";
	public static final String PC_GONGMUJIJIN_JSONSAVE07 = "pc_gongmujijin07.bin";

	public static final String PC_GUIJINSHU_JSONSAVE01 = "pc_guijinshu01.bin";
	public static final String PC_GUIJINSHU_JSONSAVE02 = "pc_guijinshu02.bin";
	public static final String PC_GUIJINSHU_JSONSAVE03 = "pc_guijinshu03.bin";

	public static final String PC_GUOZHAI_JSONSAVE01 = "pc_guozhai01.bin";
	public static final String PC_GUOZHAI_JSONSAVE02 = "pc_guozhai02.bin";

	public static final String PC_JINRONGTONGYE_JSONSAVE01 = "pc_jinrongtongye01.bin";
	public static final String PC_JINRONGTONGYE_JSONSAVE02 = "pc_jinrongtongye02.bin";
	public static final String PC_JINRONGTONGYE_JSONSAVE03 = "pc_jinrongtongye03.bin";
	public static final String PC_JINRONGTONGYE_JSONSAVE04 = "pc_jinrongtongye04.bin";

	public static final String PC_JINSUIJIEJIKA_JSONSAVE01 = "pc_jinsuijiejika01.bin";
	public static final String PC_JINSUIJIEJIKA_JSONSAVE02 = "pc_jinsuijiejika02.bin";
	public static final String PC_JINSUIJIEJIKA_JSONSAVE03 = "pc_jinsuijiejika03.bin";

	public static final String PC_LICAICHANPIN_JSONSAVE01 = "pc_licaichanpin01.bin";
	public static final String PC_LICAICHANPIN_JSONSAVE02 = "pc_licaichanpin02.bin";
	public static final String PC_LICAICHANPIN_JSONSAVE03 = "pc_licaichanpin03.bin";
	public static final String PC_LICAICHANPIN_JSONSAVE04 = "pc_licaichanpin04.bin";
	public static final String PC_LICAICHANPIN_JSONSAVE05 = "pc_licaichanpin05.bin";
	public static final String PC_LICAICHANPIN_JSONSAVE06 = "pc_licaichanpin06.bin";
	public static final String PC_LICAICHANPIN_JSONSAVE07 = "pc_licaichanpin07.bin";
	public static final String PC_LICAICHANPIN_JSONSAVE08 = "pc_licaichanpin08.bin";
	public static final String PC_LICAICHANPIN_JSONSAVE09 = "pc_licaichanpin09.bin";
	public static final String PC_LICAICHANPIN_JSONSAVE10 = "pc_licaichanpin10.bin";

	public static final String PC_QUANSHANGLICAI_JSONSAVE01 = "pc_quanshanglicai01.bin";
	public static final String PC_QUANSHANGLICAI_JSONSAVE02 = "pc_quanshanglicai02.bin";

	public static final String PC_XINYONGKA_JSONSAVE01 = "pc_xinyongkaactivity01.bin";
	public static final String PC_XINYONGKA_JSONSAVE02 = "pc_xinyongkaactivity02.bin";
	public static final String PC_XINYONGKA_JSONSAVE03 = "pc_xinyongkaactivity03.bin";
	public static final String PC_XINYONGKA_JSONSAVE04 = "pc_xinyongkaactivity04.bin";
	public static final String PC_XINYONGKA_JSONSAVE05 = "pc_xinyongkaactivity05.bin";
	public static final String PC_XINYONGKA_JSONSAVE06 = "pc_xinyongkaactivity06.bin";

	public static final String PC_JUJIAOCHANPIN_JSONSAVE01 = "pc_jujiaochanpin01.bin";
	public static final String PC_TUIJIANCHANPIN_JSONSAVE02 = "pc_tuijianchanpin02.bin";
	public static final String PC_ZUIXINCHANPIN_JSONSAVE03 = "pc_zuixinchanpin03.bin";
	public static final String PC_REXIAOCHANPIN_JSONSAVE04 = "pc_rexiaochanpin04.bin";

	public static final String PC_YIDUIDUOZHUANHU_JSONSAVE01 = "pc_yiduiduozhuanhuactivity01.bin";

	/** 理财产品 */
	public static final int ACTIVITY_TYPE_PC_MANFINA = 1;
	public static final int ACTIVITY_TYPE_URL_PCINFO = 2;
	public static final int ACTIVITY_TYPE_URL_IMAGE_NODOWNLOAD = 3;
	public static final int IMAGE_TYPE_WK_PDF0101 = 604;

	// public static final String [] HF_CRANK_ORDINARY_CUSTOMER = {"CA", "CB"};
	// public static final String [] HF_CRANK_VIP_CUSTOMER = {"CC"};

	@SuppressWarnings("rawtypes")
	public static final HashMap<Integer, SoftReference> srImageCache = new HashMap<Integer, SoftReference>();
	@SuppressWarnings("rawtypes")
	public static final HashMap<String, SoftReference> srStringImageCache = new HashMap<String, SoftReference>();
	/** 优惠活动 */
	public static final String OFFER_PATH = "offers";
	public static final String OFFER_JSONSAVE = "offers.bin";
	/** 咨询信息 */
	public static final String FI_PATH = "financialinfor";
	public static final String FI_JSONSAVE01 = "financial_infor01.bin";
	public static final String FI_JSONSAVE02 = "financial_infor02.bin";
	public static final String FI_JSONSAVE03 = "financial_infor03.bin";
	public static final String FI_JSONSAVE04 = "financial_infor04.bin";
	public static final String FI_JSONSAVE05 = "financial_infor05.bin";
	/** 敏捷营销 */
	public static final int ACTIVITY_TYPE_AM = 700;
	public static final String AM_PATH = "agilemarketing";
	public static final String AM_JSONSAVE = "agile_marketing.bin";

	public static final String APK_PATH = "apkfile";

	public static final int isTrue = 1;
	public static final int isFalse = 0;
	private CitySelectWindow thisCity;
	private PermissionUtils permissionsUtils;
	protected BluetoothAdapter mBlueToothAdapter;
	
	Context context;
	private Toast mToast;
	public static final String EXTRA_IS_TRANSPARENT = "is_transparent";
	private boolean isTransparent;

	public ParentActivity() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				onConnected(msg);
			}
		};
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		setStatusBar();
	}

	protected void setStatusBar() {
		StatusBarUtil.setTranslucentDiff(this);
	}

	/***
	 * 定时器*start
	 **/

	private boolean isDetectPaused = false;//ActivityPaused后是不是要暂停
	private boolean isLoginActivity  = false;
	private final long INTERVAL = 3000;
	private static long MAX_TIMEOUT = 5*1000;
	private static long getMax_timeout(){
		String eixtTime = LogoActivity.user.getEixtTime();
		if(!TextUtils.isEmpty(eixtTime)){//eixtTime 登录后后台返回的 值是5.
			MAX_TIMEOUT = Long.parseLong(eixtTime)*60*1000;//5×分钟
//			MAX_TIMEOUT = Long.parseLong(eixtTime)*36*1000;//
		}
		return MAX_TIMEOUT;
	}
	
	
	private Runnable disconnectCallback = new Runnable() {
		@Override
		public void run() {
            if (!isDetectPaused) {
                if (MyApplication.getElapsedTime() > getMax_timeout() && !isLoginActivity) {
                    for (Activity activity : activityList) {
                        activity.finish();
                    }
                    MyApplication.saveTimeStamp();
                    gotoNextActivity();
                } else {
                    handler.postDelayed(disconnectCallback, INTERVAL);
                }
            }
        }
    };

    /**
     * 绑定 View
    * */
	@SuppressWarnings("unchecked")
	protected <T extends View> T bindView(int id) {
		return (T) findViewById(id);
	}

	@SuppressWarnings("unchecked")
	protected <T extends View> T bindView(View view, int id) {
		return (T) view.findViewById(id);
	}

	public void gotoNextActivity(){
		startActivity(new Intent(this, NewLoginActivity.class));
	}

	protected boolean checkObjValid(Object obj) {
		if (obj != null)
			return true;
		else
			return false;
	}

	/**
	 * 弹出软键盘
	 */
	private void showKeyboard(View view){
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.showSoftInput(view, 0);
	}

	/**
	 * 关闭软键盘
	 */
	protected void closeKeyboard() {
		View view = getWindow().peekDecorView();
		if (view != null) {
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	protected DialogAlert ShowDialog(Context context, String str){
		DialogAlert dialog = new DialogAlert(context);
		dialog.show();
		dialog.setMsg(str);
//		dialog.setMsg("抱歉，查询条件不能为空！");

		return dialog;
	}

	private static View oldView;
	/**
	 * 设置item 被选中时的效果
	 */
	protected static boolean setSelectorItemColor(View view) {

		if (oldView == null){
			//第一次点击
			oldView = view;
			view.setBackgroundResource(R.color.common_yellow);
		}else {
			//非第一次点击
			//把上一次点击的 变化
			oldView.setBackgroundResource(R.color.transparent);
			view.setBackgroundResource(R.color.common_yellow);
			oldView = view;
		}
		return true;
	}

	public static void setSelectorItemColor(View view, @ColorRes @DrawableRes int colorId, @ColorRes @DrawableRes int oldColorId) {

//		LogUtils.d("colorId:"+colorId);
		if (oldView == null) {
			//第一次点击
			oldView = view;
			oldColor = oldColorId;//当前 iew 的颜色
			view.setBackgroundResource(colorId <= Long.parseLong("7F000000", 16) ? com.dysen.common_res.R.color.common_yellow : colorId);
		} else {
			//非第一次点击
			//把上一次点击的 变化
			oldView.setBackgroundResource(oldColor);
			view.setBackgroundResource(colorId <= Long.parseLong("7F000000", 16) ? com.dysen.common_res.R.color.common_yellow : colorId);
			oldView = view;
		}
	}

	/**
	 * 页面跳转
	 * @param cls
	 */
	public void gotoNextActivity(Class<?> cls){
		Intent intent = new Intent();
		intent.setClass(this, cls);
		startActivity(intent);
	}

	/**
	 * 页面跳转传参数
	 * @param cls
	 * @param bundle
	 */
	public void gotoNextActivity(Class<?> cls, Bundle bundle){
		Intent intent = new Intent();
		intent.setClass(this, cls);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	public void onUserInteraction() {
		super.onUserInteraction();
		MyApplication.saveTimeStamp();
	}

	/***
	 * 定时器**end
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		StatusBarUtil.setColor(this, Color.parseColor("#ea452f"), 0);
		mToast = new Toast(this);
		mBlueToothAdapter = BluetoothAdapter.getDefaultAdapter();

//		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
//			//未授权
//			ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
//		}
		// 内存处理
		System.gc();
		Runtime.getRuntime().freeMemory();
		Formatter.formatFileSize(ParentActivity.this, Runtime.getRuntime()
				.freeMemory());// 格式化输出
		// 地图服务注册
		SDKInitializer.initialize(getApplicationContext());
		if (LogoActivity.user == null)
			try {
				LogoActivity.user = new User(this);
				SharedPreferences userInfo = getSharedPreferences("user_info",
						0);
				if (userInfo != null) {
					LogoActivity.user.setUserid(userInfo
							.getString("userid", ""));
					LogoActivity.user.setPassword(userInfo.getString(
							"password", ""));
					LogoActivity.user.setBrID(userInfo.getString("brID", ""));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		// 将Activity 添加到activityList中
		activityList.add(this);

		isLoginActivity = this instanceof NewLoginActivity;
	}

	public static final String[] PERMISSIONS = {
			Manifest.permission.ACCESS_COARSE_LOCATION,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.READ_PHONE_STATE,
			Manifest.permission.CAMERA
//			Manifest.permission_group.LOCATION,
//			Manifest.permission_group.CAMERA,
//			Manifest.permission_group.STORAGE,
//			Manifest.permission_group.PHONE

	};

    /**
     * 用户交互监听
     * */
    public void startDetectUserInterAction(){
         handler.post(disconnectCallback);
    }

	@SuppressWarnings("deprecation")
	public void onConnected(Message msg) {
		try {
			switch (msg.what) {
			case HConnection.RESPONSE_ERROR:
				Bundle data = msg.getData();
				// String error = data.getString("value");
				// showError(this, error);
				connectionIndex = HConnection.RESPONSE_ERROR;
				break;

			default:
				data = msg.getData();
				connectionIndex = Integer.parseInt(data.getString("value"));
				break;
			}
			// dismissDialog(DIALOG_ID);
			removeDialog(DIALOG_ID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_ID:
			loadingDialog = new ProgressDialog(this);
			loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			loadingDialog.setMessage(dialogMsg);
			loadingDialog.setCancelable(false);
			return loadingDialog;
		}
		return super.onCreateDialog(id);
	}

	@SuppressWarnings("deprecation")
	public void onConnectStart() {
		showDialog(DIALOG_ID);
	}

	/**
	 * 完完全全退出应用程序
	 *
	 */

	public void exitApp() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("提示");
		builder.setMessage("您确定要退出程序吗?");
		builder.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (activityList.size() > 0) {
					for (Activity activity : activityList) {

						activity.finish();
					}

					android.os.Process.killProcess(android.os.Process.myPid());

				}

			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		builder.show();
	}

	/*
	 * public void onAttachedToWindow() {
	 * this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
	 * 
	 * super.onAttachedToWindow(); }
	 */
	/**
	 * 屏蔽一系列实体键
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_HOME:
			return true;
			// case KeyEvent.KEYCODE_BACK:
			// return true;
		case KeyEvent.KEYCODE_CALL:
			return true;
		case KeyEvent.KEYCODE_SYM:
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			return true;
		case KeyEvent.KEYCODE_VOLUME_UP:
			return true;
		case KeyEvent.KEYCODE_STAR:
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	/** 通过图片id获取获取Bitmap，退出该Activity时必须调用清理方法 */
	protected Bitmap getBitmapFromCache(Integer imageId) {
		Bitmap tmpBitmap;
		if (srImageCache.containsKey(imageId)
				&& srImageCache.get(imageId) != null) {
			tmpBitmap = (Bitmap) srImageCache.get(imageId).get();
		} else {
			tmpBitmap = Tool.readBitMap(this, imageId);
			srImageCache.put(imageId, new SoftReference<Bitmap>(tmpBitmap));
		}
		return tmpBitmap;
	}

	// /** 通过图片String型Id(URL)获取获取Bitmap，退出该Activity时必须调用清理方法 */
	// protected Bitmap getBitmapFromCache(String urlImageId) {
	// Bitmap tmpBitmap;
	// if (srStringImageCache.containsKey(urlImageId) &&
	// srStringImageCache.get(urlImageId) != null) {
	// tmpBitmap = (Bitmap) srStringImageCache.get(urlImageId).get();
	// } else {
	// tmpBitmap = Tool
	// .readBitMap(this, urlImageId);
	// srStringImageCache.put(urlImageId, new SoftReference<Bitmap>(tmpBitmap));
	// }
	// return tmpBitmap;
	// }
	/** 清理Bitmap */
	@SuppressWarnings("unchecked")
	protected void cleanBitmaFromCache(Integer imageId) {
		if (imageId != null)
			if (srImageCache.containsKey(imageId)) {
				SoftReference<Bitmap> reference = srImageCache.get(imageId);
				Bitmap bitmap = reference.get();
				if (bitmap != null && !bitmap.isRecycled()) {
					bitmap.recycle();
					bitmap = null;
				}
				srImageCache.remove(imageId);
			}
	}

	@SuppressWarnings("unchecked")
	protected void cleanBitmaFromCache(String urlImageId) {
		if (urlImageId != null)
			// synchronized (urlImageId) {
			if (srStringImageCache.containsKey(urlImageId)) {
				SoftReference<Bitmap> reference = srStringImageCache
						.get(urlImageId);
				Bitmap bitmap = reference.get();
				if (bitmap != null && !bitmap.isRecycled()) {
					bitmap.recycle();
					bitmap = null;
				}
				srStringImageCache.remove(urlImageId);
			}
		// }
	}

	/**
	 * 隐藏键盘
	 */
	InputMethodManager imm;

	protected void hideSoftInput() {
		View view = this.getCurrentFocus();
		if (view != null)
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	//
	/**
	 * 初始化标题栏
	 * 
	 * @param activity
	 *            当前Activity
	 * @param titleId
	 *            初始化顶部公共标题
	 */
	public void initTitle(final Activity activity, int titleId) {
		ImageView backImg = (ImageView) findViewById(R.id.com_img_back);
		ImageView titleImg = (ImageView) findViewById(R.id.com_title_img);
		TextView username = (TextView) findViewById(R.id.com_tv_adminname);
		TextView out = (TextView) findViewById(R.id.com_tv_out);

		if (username != null && LogoActivity.user != null) {
			username.setText(LogoActivity.user.getUsername());
		}
		if (titleImg != null) {
			titleImg.setImageResource(titleId);
		}
		if (out != null) {
			out.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					exitApp();
				}
			});
		}
		if (backImg != null) {
			backImg.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					activity.finish();
				}
			});
		}

	}

	/**
	 * 请求同样方式
	 * 
	 * @param context
	 *            请求的Fragment
	 * @param requestType
	 *            请求类型
	 * @param requestCode
	 *            接口
	 * @param info
	 *            请求实体内容
	 * @param Index
	 *            返回index
	 */
	public HConnection RequestInfo(ParentActivity context,
			Constants.requestType requestType, String requestCode, String info,
			int Index,boolean isShow) {
		HConnection HCon = null;
		if (Tool.haveNet(this)) {
			String requestHttp = Tool.requestHttp(requestCode, "getJSON");
			switch (requestType) {
			case Search:
			case Delete:
				requestHttp = requestHttp + "&spareOne=" + info;
				break;
			case Insert:
			case Update:
			case JsonData:
				requestHttp = requestHttp + "&jsonData=" + info;
				break;
			case Paging:
				requestHttp = requestHttp + "&size=20&offset=1"+info;
				break;
			case Other:
				requestHttp = requestHttp + info;
				break;
			default:
				break;
			}
			HRequest request = new HRequest(requestHttp, "GET");
			try {
				HConnection.isShowLoadingProcess = isShow; // 不显示loading
				HCon = new HConnection(context, request,
						LogoActivity.user);
				HCon.setIndex(Index);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return HCon;
	}
	
	public HConnection RequestInfo(ParentActivity context,
			Constants.requestType requestType, String requestCode, String info,
			int Index) {
		return RequestInfo(context, requestType, requestCode, info, Index, true);
	}

	/**
	 * 请求测试
	 * @param context
	 * @param url http 地址
	 * @param isShow 是否显示加载
	 */
	public void httpGet(ParentActivity context, String url, boolean isShow){

		HRequest request = new HRequest(url, "GET");
//			LogUtils.i("请求类型："+requestCode+"\t请求url："+request.getURL());
		try {
			HConnection.isShowLoadingProcess = isShow; // 不显示loading
			HConnection HCon = new HConnection(context, request);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 日期选择器
	 * 
	 * @param onDateSetListener
	 */
	public void DatePickerShow(OnDateSetListener onDateSetListener) {
		Calendar calendar = Calendar.getInstance();
		DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
				onDateSetListener, calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		datePickerDialog.show(getSupportFragmentManager(), "datepicker");

	}

	/**
	 * 时间选择器
	 * 
	 * @param OnTimeSetListener
	 */
	public void TimePickerShow(OnTimeSetListener OnTimeSetListener) {
		Calendar calendar = Calendar.getInstance();
		TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
				OnTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), true, false);
		timePickerDialog.show(getSupportFragmentManager(), "timepicker");
	}

	/**
	 * 城市选择器
	 * 
	 * @param callBackInfo
	 */
	public void CityPickerShow(View view,CallBackCity callBackInfo) {
		thisCity = new CitySelectWindow(this,view);
		thisCity.showCity(callBackInfo);
	}
	public void CityPicker(View view,CitySelect.CallBackCitySelect callBackInf){
		CitySelect citySelect = new CitySelect(this,view);
		citySelect.showCity(callBackInf);
	}
	/**
	 *
	 * @param view
	 * @param showAddressLevel  显示地址的级别，1只是显示省份，2显示省份和城市，3 表示显示省份、…… 4 , 5
	 * @param callBackInfo
	 */
	public void CityPickerShow(View view,int showAddressLevel ,CallBackCity callBackInfo) {

		thisCity = new CitySelectWindow(this,view);
		if(showAddressLevel==1){
			thisCity.setShowcity(false);
		}else if(showAddressLevel ==2 ){
			thisCity.setShowArea(false);
		}else if(showAddressLevel == 3){
			thisCity.setShowCounty(false);
		}else if(showAddressLevel == 4){
			thisCity.setShowStreet(false);
		}

		thisCity.showCity(callBackInfo);
	}

	/**
	 * Toast消息提示
	 * @param text
	 */
	public void toast2(CharSequence text){
		// 2s内只提示一次
		long currentTime = System.currentTimeMillis();
		if(currentTime - lastTime > 2000){
			lastTime = currentTime;
			Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
		}
	}
	static int oldColor ;
	/**
	 * 设置item 被选中时的效果
	 */
	public static void setSelectorItemColor(View view, @ColorRes @DrawableRes int colorId) {

//		LogUtils.d("colorId:"+colorId);
		if (oldView == null){
			//第一次点击
			oldView = view;
			oldColor = view.getDrawingCacheBackgroundColor();//当前 iew 的颜色
			view.setBackgroundResource(colorId <= Long.parseLong("7F000000", 16)? com.dysen.common_res.R.color.common_yellow : colorId);
		}else {
			//非第一次点击
			//把上一次点击的 变化
			oldView.setBackgroundResource(oldColor);
			view.setBackgroundResource(colorId <= Long.parseLong("7F000000", 16) ? com.dysen.common_res.R.color.common_yellow : colorId);
			oldView = view;
		}
	}

	private long lastTime = 0;

	/**
	 * Toast消息提示
	 * @param text
	 */
	public void toast(CharSequence text){
		// 2s内只提示一次
		long currentTime = System.currentTimeMillis();
		if(currentTime - lastTime > 2000){
			lastTime = currentTime;
			Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
		}
	}

	private LinearLayoutManager mLayoutManager;
	protected RecyclerView setRecyclerView(RecyclerView recyclerView) {

		//设置固定大小
		recyclerView.setHasFixedSize(true);
		//创建线性布局
		mLayoutManager = new LinearLayoutManager(this);
		//垂直方向
		mLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
		//添加分割线
//		recyclerView.addItemDecoration(new TestDecoration(this));
		//给RecyclerView设置布局管理器
		recyclerView.setLayoutManager(mLayoutManager);

		return recyclerView;
	}
	protected RecyclerView setRecyclerView(RecyclerView recyclerView, int orientation) {

		//设置固定大小
		recyclerView.setHasFixedSize(true);
		//创建线性布局
		mLayoutManager = new LinearLayoutManager(context);
		//垂直方向 OrientationHelper.HORIZONTAL 0 OrientationHelper.VERTICAL 1
		mLayoutManager.setOrientation(orientation);
		//添加分割线
//		recyclerView.addItemDecoration(new TestDecoration(this));
		//给RecyclerView设置布局管理器
		recyclerView.setLayoutManager(mLayoutManager);

		return recyclerView;
	}
	protected RecyclerView setRecyclerView(RecyclerView recyclerView, RecyclerView.LayoutManager mLayoutManager) {

		//设置固定大小
		recyclerView.setHasFixedSize(true);
		//创建线性布局
//		mLayoutManager = new LinearLayoutManager(this);
		//垂直方向
//		mLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
		//添加分割线
//		recyclerView.addItemDecoration(new TestDecoration(this));
		//给RecyclerView设置布局管理器
		recyclerView.setLayoutManager(mLayoutManager);

		return recyclerView;
	}

	/**
	 * 显示/隐藏 View 设值
	 *
	 * @param view
	 * @param text
	 * @param bl
	 */
	public void setTextHide(View view, String text, boolean bl) {
		TextView mTextView = null;
		Button btn = null;
		if (bl)
			view.setVisibility(View.INVISIBLE);
		else {
			view.setVisibility(View.VISIBLE);
			if (view instanceof TextView)
				mTextView.setText(text);
			else if (view instanceof Button)
				btn.setText(text);
		}
	}

	public void setBadgeView(View view, String text) {

		QBadgeView badge = new QBadgeView(context);
		badge.bindTarget(view);
		if (FormatUtil.isNumeric(text))
			badge.setBadgeNumber(Integer.parseInt(text));
		else
			badge.setBadgeText(text);
	}

    @Override
    protected void onPause() {
        super.onPause();
        isDetectPaused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isDetectPaused = false;
        startDetectUserInterAction();
    }

    @Override
	protected void onDestroy() {
		if (thisCity != null) {
			thisCity.destory();
		}
		super.onDestroy();
		handler.removeCallbacks(disconnectCallback); //停止Timer
	}

}
