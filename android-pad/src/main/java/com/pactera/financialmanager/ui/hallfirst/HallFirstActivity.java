package com.pactera.financialmanager.ui.hallfirst;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.callback.BtnClickListener;
import com.pactera.financialmanager.entity.PadQueueInfoBean;
import com.pactera.financialmanager.ui.HeadRefreshListView;
import com.pactera.financialmanager.ui.HeadRefreshListView.OnRefreshListener;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.NewLoginActivity;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.dgfragment.MyCpDialogFragmentButtons;
import com.pactera.financialmanager.ui.model.OtherLineChannelEntity;
import com.pactera.financialmanager.ui.model.SerachCustomerInfo;
import com.pactera.financialmanager.ui.model.User;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HRequest;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.LimitsUtil;
import com.pactera.financialmanager.util.QuitAppDialog;
import com.pactera.financialmanager.util.QuitLoginDialog;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/***
 * 厅堂首页
 * 
 * @author Administrator
 * 
 */

@SuppressLint("HandlerLeak")
public class HallFirstActivity extends ParentActivity implements
		OnClickListener, BtnClickListener {
	private final int QUEUE_CUST_STYLE00 = 0;
	private final int QUEUE_CUST_STYLE01 = 1;

	private final int GUIUPDATEIDENTIFIER = 2;
	private final int PLAYTHEVOICEOFVIP = 3;

	private final int HALL_FIRST_EXIT = 0;
	private int QUEUE_CUST_STYLE = QUEUE_CUST_STYLE01;

	// private List<CustomerInfo> tmpCustomerInfos;// 普通客户，vip客户，临时表格
	private List<PadQueueInfoBean> padQueueInfoBeanList; // 普通客户
	private List<PadQueueInfoBean> tmpCustomerInfo; // vip客户
	private List<PadQueueInfoBean> tmpCustome; // 临时表格
	private boolean isHeadRefresh = true;
	private int screenWidth;// 屏幕宽度 　
	private int screenHeight;// 屏幕高度
	@SuppressWarnings("unused")
	private String retCode;// 返回值

	// 设置排队信息启动和间隔时间常量
	private final long STARTTIME = 500; // 毫秒
	private final long DURATION = 30 * 1000;
	private Timer timer;
	private TimerTask timeTask;
	private HFListAdapter hFListAdapter;
	private int flag = 1;
	private Handler myHandler;
	private MediaPlayer mMediaPlayer;

	private FragmentManager manager;
	private AlertDialog dialogOtherLine;

	private ImageView imgSearch;
	private EditText etSearch;
	private HeadRefreshListView recentList;

	private HConnection searchDataCon;
	private final int searchFlag = 99;// 查询客户信息
	private List<OtherLineChannelEntity> otherLineChannelCheckList;

	private Bitmap mapOtherlineno;
	private Bitmap mapOtherlineover;

	private PadQueueInfoBean queueInfoForShunt;
	private Set<String> shunted = new HashSet<String>();

	private String queueInfoLastUpdateTime = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hall_first);
		initTitle(this, R.drawable.hallword);
		
		padQueueInfoBeanList = new ArrayList<PadQueueInfoBean>();
		tmpCustome = new ArrayList<PadQueueInfoBean>();
		manager = getFragmentManager();
		findDisplayScreen();
		findViews();

		addOnClickListeners();

		hFListAdapter = new HFListAdapter();
		recentList.setAdapter(hFListAdapter);

		OnRefreshListener onRefreshListener = new OnRefreshListener() {
			public void onRefresh() {
				if (isHeadRefresh)
					getCustomerData();
			}
		};

		recentList.setOnRefreshListener(onRefreshListener);
		otherLineChannelCheckList = new ArrayList<OtherLineChannelEntity>();

		// isHeadRefresh = true;
		getCustomerData();
		
		myHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case HALL_FIRST_EXIT:
					// hall_first_exit.setEnabled(true);
					break;
				case GUIUPDATEIDENTIFIER:
					/*
					 * flag++; Toast.makeText(HallFirstActivity.this,
					 * "正在更新客户信息第" + flag + "次", Toast.LENGTH_LONG).show();
					 */
					// 更新数据
					// hall_first_custStyle00.setClickable(false);
					// hall_first_custStyle01.setClickable(false);
					if (isHeadRefresh) {
						getCustomerData();
					}
					// 加载展示数据
					// recentList.setAdapter(new HFListAdapter());
					// hFListAdapter.notifyDataSetChanged();

					break;
				case PLAYTHEVOICEOFVIP:
					mMediaPlayer.start();
					// queueNO = customerInfos02.get(0).queueNum.trim();
					break;
				default:
					break;
				}
				super.handleMessage(msg);
			}
		};
		
		{
			List<Bitmap> list = new ArrayList<Bitmap>();
			list.add(Tool.readBitMap(getApplicationContext(), R.drawable.one));
			list.add(Tool.readBitMap(getApplicationContext(), R.drawable.two));
			list.add(Tool.readBitMap(getApplicationContext(), R.drawable.three));
			list.add(Tool.readBitMap(getApplicationContext(), R.drawable.four));
			list.add(Tool.readBitMap(getApplicationContext(), R.drawable.five));
			list.add(Tool.readBitMap(getApplicationContext(), R.drawable.six));
			mapOtherlineno = Tool.readBitMap(getApplicationContext(),
					R.drawable.otherlineno);
			mapOtherlineover = Tool.readBitMap(getApplicationContext(),
					R.drawable.otherlineover);
		}

	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		// 回到页面-开启定时器
		timer = new Timer(true);
		timeTask = new TimerTask() {
			@Override
			public void run() {
				Message message = new Message();
				message.what = GUIUPDATEIDENTIFIER;
				myHandler.sendMessage(message);
			}

		};
		timer.schedule(timeTask, STARTTIME, DURATION);
	}
	

	@Override
	protected void onPause() {
		super.onPause();
		// 离开页面-停止定时器
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}
	

	@SuppressWarnings("deprecation")
	private void findDisplayScreen() {
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();

		screenWidth = display.getWidth();
		screenHeight = display.getHeight();

		Constants.SCREEN_WIDTH = screenWidth;
		Constants.SCREEN_HEIGHT = screenHeight;
	}

	private HConnection customerCon, customerOtherLine, customerOtherLineCheck;
	private static final int customerIndex = 1; // 排队信息
	private static final int customerOlFlag = 2; // 客户分流
	private static final int customerOtherLineCheckFlag = 3; // 客户分流查询
	private TextView tvQueueNum;

	private synchronized void getCustomerData() {
		if (isHeadRefresh) {
			recentList.dofirstRefresh();
		}
		isHeadRefresh = false;

		HRequest request;
		if (queueInfoLastUpdateTime == "") {
			request = new HRequest("T000003?method=getJSON&userCode="
					+ LogoActivity.user.getUserid() + "&seriNo=" + seriNo()
					+ "&chnlCode=" + "02" + "&transCode=T000003&brCode="
					+ LogoActivity.user.getBrCode() + "&maxTime="
					+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
					"GET");
		} else {
			request = new HRequest("T000003?method=getJSON&userCode="
					+ LogoActivity.user.getUserid() + "&seriNo=" + seriNo()
					+ "&chnlCode=" + "02" + "&transCode=T000003&brCode="
					+ LogoActivity.user.getBrCode() + "&maxTime="
					+ URLEncoder.encode(queueInfoLastUpdateTime), "GET");
		}

		Log.i("1111", "LoadingList -->url:" + request.getURL());
		try {
			HConnection.isShowLoadingProcess = false;
			customerCon = new HConnection(this, request, LogoActivity.user,
					HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			customerCon.setIndex(customerIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addOnClickListeners() {
		// 搜索按钮
		imgSearch.setOnClickListener(this);
	}

	private void findViews() {
		// 带下拉的ListView
		recentList = (HeadRefreshListView) findViewById(R.id.hf_list);
		// 搜索按钮
		imgSearch = (ImageView) findViewById(R.id.imgsearch);
		etSearch = (EditText) findViewById(R.id.et_search);
		tvQueueNum = (TextView) findViewById(R.id.tv_queuenum);// 排队人数
	}

	private class HFListAdapter extends BaseAdapter {

		private String today;

		public HFListAdapter() {
			today = new SimpleDateFormat("MMdd").format(new Date());
		}

		@Override
		public int getCount() {
			if(padQueueInfoBeanList != null){
				return padQueueInfoBeanList.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			if(padQueueInfoBeanList != null && padQueueInfoBeanList.size()>0){
				return padQueueInfoBeanList.get(position);
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder;
			if (convertView == null) {
				holder = new Holder();
				convertView = View.inflate(HallFirstActivity.this, R.layout.hf_visit_customer_item02, null);
				holder.llOtherLine = (LinearLayout) convertView.findViewById(R.id.ll_item_otherline);
				holder.hf_vc_item_num = (TextView) convertView.findViewById(R.id.hf_vc_item_num); // 排队号码
				holder.img_item_otherline = (ImageView) convertView.findViewById(R.id.img_item_otherline);// 分流的图标
				holder.hf_vc_item_waittime = (TextView) convertView.findViewById(R.id.hf_vc_item_waittime); // 等待时间
				holder.hf_vc_item_name = (Button) convertView.findViewById(R.id.hf_vc_item_name); // 客户姓名
				holder.hall_birthdayicon = (ImageView) convertView.findViewById(R.id.hall_birthdayicon); // 客户生日图标
				holder.hf_vc_item_sex = (TextView) convertView.findViewById(R.id.hf_vc_item_sex); // 客户性别
				holder.hf_vc_item_level = new ImageView[] {
						(ImageView) convertView.findViewById(R.id.s1),
						(ImageView) convertView.findViewById(R.id.s2),
						(ImageView) convertView.findViewById(R.id.s3),
						(ImageView) convertView.findViewById(R.id.s4),
						(ImageView) convertView.findViewById(R.id.s5), }; // 客户评级
				holder.hf_vc_item_customerlevel = (ImageView) convertView.findViewById(R.id.hf_vc_item_customerlevel); // 贵宾卡级别
				holder.hf_vc_item_manager = (TextView) convertView.findViewById(R.id.hf_vc_item_manager); // 所属客户经理
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			holder.hall_birthdayicon.setVisibility(View.GONE);// 生日的图标， 先隐藏它

			if (position % 2 == 0) {
				convertView.setBackgroundResource(R.drawable.list_item_white_bg);
			} else {
				convertView.setBackgroundResource(R.drawable.list_item_lightwhite_bg);
			}

			final PadQueueInfoBean padQueueInfoBean = padQueueInfoBeanList.get(position);
			if(padQueueInfoBean != null){
				initShuntItem(padQueueInfoBean, holder);
				switch (padQueueInfoBean.queueStatus()) {
				case PadQueueInfoBean.QUEUEING:
					holder.hf_vc_item_waittime.setText(padQueueInfoBean.getWaitTimeInSecond() / 60 + "分钟");
					break;
				case PadQueueInfoBean.CALLED:
					holder.hf_vc_item_waittime.setText(padQueueInfoBean.getWindowNo() + "窗在办");
					break;
				case PadQueueInfoBean.FINISH:
					holder.hf_vc_item_waittime.setText("已结束");
					break;
				}

				holder.hf_vc_item_num.setText(padQueueInfoBean.getQueueCode());

				// 根据返回的是否是客户生日，决定生日的小图标是否显示
				if (today.equals(padQueueInfoBean.getBirthMonDay())) {
					holder.hall_birthdayicon.setVisibility(View.VISIBLE);
				} else {
					holder.hall_birthdayicon.setVisibility(View.GONE);
				}

				{
					int sexFlag = gender(padQueueInfoBean.getCustSex());
					if (1 == sexFlag) {
						holder.hf_vc_item_sex.setText("男");
					} else if (2  == sexFlag) {
						holder.hf_vc_item_sex.setText("女");
					} else {
						holder.hf_vc_item_sex.setText("");
					}
				}

				{
					String custLevel = padQueueInfoBean.getCustLevel();
					int lvl = 0;
					try {
						lvl = Integer.parseInt(custLevel);
					} catch (Exception e) {
					}
					for (int i = lvl; i < 5; i++) {
						holder.hf_vc_item_level[i].setVisibility(View.INVISIBLE);
					}
				}

				{
					// TODO card level map to Bitm
					Bitmap bitmapVipLevel = null;
					String vipLevel = padQueueInfoBean.getVipLevel().trim();
					if("0".equals(vipLevel)){
						//bitmapVipLevel = Tool.readBitMap(getApplicationContext(), R.drawable.putongka);//惠农卡2017/1/6号添加
					}else if("1".equals(vipLevel)) {
						bitmapVipLevel = Tool.readBitMap(getApplicationContext(), R.drawable.putongka);//福满社区卡2017/1/6号添加
					}else if ("2".equals(vipLevel)) {
						bitmapVipLevel = Tool.readBitMap(getApplicationContext(), R.drawable.goldcard1);//黄金卡
					} else if ("3".equals(vipLevel)) {
						bitmapVipLevel = Tool.readBitMap(getApplicationContext(), R.drawable.silvercard1);//铂金卡
					} else if ("4".equals(vipLevel)) {
						bitmapVipLevel = Tool.readBitMap(getApplicationContext(), R.drawable.diamondcard1);//钻石卡
					}
					holder.hf_vc_item_customerlevel.setImageBitmap(bitmapVipLevel);
				}

				holder.hf_vc_item_manager.setText(padQueueInfoBean.getOwnerName());//所属客户经理

				if (!TextUtils.isEmpty(padQueueInfoBean.getCustCHNName())) {
					holder.hf_vc_item_name.setVisibility(View.VISIBLE);
					holder.hf_vc_item_name.setText(padQueueInfoBean.getCustCHNName());
					holder.hf_vc_item_name.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									// 这是进入到客户详情的页面
									// 这里应该是条目点击事件
									if (padQueueInfoBean != null) {
										Intent intent = new Intent(HallFirstActivity.this, CMMarketingActivity2.class);
										intent.putExtra("custID", padQueueInfoBean.getCustCode());// 客户ID
										intent.putExtra("isQuery", false);// 是否为查询跳转
										startActivity(intent);
									}
								}
							});
				} else {
					holder.hf_vc_item_name.setVisibility(View.GONE);
				}
			}
			return convertView;
		}

		private void initShuntItem(final PadQueueInfoBean bean,
				final Holder holder) {

			if (bean.queueStatus() == PadQueueInfoBean.FINISH) {
				holder.img_item_otherline.setImageBitmap(null);
				return;
			}

			if (bean.queueStatus() == PadQueueInfoBean.QUEUEING) {
				if (bean.isShunted()|| shunted.contains(bean.uniqKeyInBranchBank())) {
					holder.img_item_otherline.setImageBitmap(mapOtherlineover);//对勾图标表示VIP客户
				} else {
					holder.img_item_otherline.setImageBitmap(mapOtherlineno);
					holder.llOtherLine.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									queueInfoForShunt = bean;
									String code =bean.getMachineCode();
									sendRequestForShuntChannel(code);
								}
							});
				}
			} else {
				holder.img_item_otherline.setImageBitmap(null);
			}
		}

		private class Holder {
			private TextView hf_vc_item_num; // 排队号码
			private ImageView img_item_otherline; // 分流
			private TextView hf_vc_item_waittime; // 等待时间
			private Button hf_vc_item_name; // 客户姓名
			private ImageView hall_birthdayicon; // 客户生日图标
			private TextView hf_vc_item_sex; // 性别
			private ImageView[] hf_vc_item_level;// 客户评级
			private ImageView hf_vc_item_customerlevel;// 贵宾卡级别
			private TextView hf_vc_item_manager;// 所属客户经理

			private LinearLayout llOtherLine;// 分流图标外面的包裹层
		}
	}

	private void sendRequestForShuntChannel(String machineCode) {

		try {
			HRequest hRObj = new HRequest("T000007?method=getJSON&userCode="
					+ LogoActivity.user.getUserid() + "&seriNo=" + seriNo()
					+ "&chnlCode=" + "02" + "&transCode=T000007&brCode="
					+ LogoActivity.user.getBrCode() + "&machineCode="
					+ machineCode, "GET");

			Log.i("1111", "T000007 -->url:" + hRObj.getURL());
			customerOtherLineCheck = new HConnection(this, hRObj,
					LogoActivity.user, HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			customerOtherLineCheck.setIndex(customerOtherLineCheckFlag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void exitLoginUser(final int btn) {
		findViewById(btn).setEnabled(false);
		QuitLoginDialog dialog = new QuitLoginDialog(this,
				R.layout.quit_user_dialog, R.id.dialog_linear, R.id.bt_Yes,
				R.id.bt_No, R.drawable.common_quit_dialog, screenWidth,
				screenHeight, R.style.dialog);
		dialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				try {
					LogoActivity.user = new User(HallFirstActivity.this);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Intent intent1 = new Intent();

				intent1.setClass(HallFirstActivity.this, NewLoginActivity.class);
				startActivity(intent1);
				finish();
			}
		});
		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				myHandler.sendEmptyMessage(HALL_FIRST_EXIT);
			}
		});
		dialog.show();
	}

	@SuppressWarnings("unused")
	private void exitAppABC(final int btn) {

		findViewById(btn).setEnabled(false);
		QuitAppDialog dialog = new QuitAppDialog(this,
				R.layout.quit_app_dialog, R.id.dialog_linear, R.id.bt_Yes,
				R.id.bt_No, R.drawable.common_quit_dialog, screenWidth,
				screenHeight, R.style.dialog);

		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				myHandler.sendEmptyMessage(HALL_FIRST_EXIT);

			}
		});
		dialog.show();

	}


	// 客户信息一键查询的接口，目前传的参数中差一个custid，不知道custid是从哪里获取到的
	// 接口文档中输入报文中却又是没有custid这个参数， 要找后台的人确认一下了
	private void searchDataInfo(String search_code) {
//		if (Tool.haveNet(this)) {
			String requestType = InterfaceInfo.HALLFIRST_QUERY;
			searchDataCon = RequestInfo(this, Constants.requestType.Search, requestType, search_code, searchFlag, false); 
//		}
	}

	private void readJson(JSONObject tmpJsonObject) {
		if (padQueueInfoBeanList == null) {
			padQueueInfoBeanList = new ArrayList<PadQueueInfoBean>();
		}

		String code = null;
		int totalSize = 0;
		try {
			if (tmpJsonObject.has("totalSize")) {
				totalSize = tmpJsonObject.getInt("totalSize");
			}
			if (tmpJsonObject.has("retCode")) {
				code = tmpJsonObject.getString("retCode");
			}
			if (totalSize != 0 && "0000".equals(code)) {
				String padQueueInfo = tmpJsonObject.getString("group");
				if (padQueueInfo != null) {
					padQueueInfoBeanList = mergePadQueueInfoBeanList(JSON.parseArray(padQueueInfo, PadQueueInfoBean.class));
				}
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		// remove expired data
		{
			// if endTime < (currentTime - 10minutes), then dismiss
			final String limitationTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System
					.currentTimeMillis() - 10 * 60 * 1000));
			for (Iterator<PadQueueInfoBean> itr = padQueueInfoBeanList.iterator(); itr.hasNext();) {
				PadQueueInfoBean b = itr.next();
				if (b.queueStatus() == PadQueueInfoBean.FINISH
						&& b.getEndTime().compareTo(limitationTime) < 0) {
					itr.remove();
				}
			}
		}

		// statics
		{
			int queueSize = 0;
			String lastUpdateTime = "";
			for (PadQueueInfoBean b : padQueueInfoBeanList) {
				if (PadQueueInfoBean.QUEUEING == b.queueStatus())
					queueSize++;
				if (b.getLastUpdateTime().compareTo(lastUpdateTime) > 0)
					lastUpdateTime = b.getLastUpdateTime();
			}
			// update queueing number
			tvQueueNum.setText(String.valueOf(queueSize));
			// update last update time
			queueInfoLastUpdateTime = lastUpdateTime;
		}

		hFListAdapter = new HFListAdapter();
		recentList.setAdapter(hFListAdapter);
		recentList.onRefreshComplete();
		isHeadRefresh = true;
	}

	private List<PadQueueInfoBean> mergePadQueueInfoBeanList(List<PadQueueInfoBean> tmpCus) {

		Map<String, PadQueueInfoBean> queueInfoMap = new HashMap<String, PadQueueInfoBean>();
		for (PadQueueInfoBean b : padQueueInfoBeanList) {
			queueInfoMap.put(b.uniqKeyInBranchBank(), b);
		}
		for (PadQueueInfoBean b : tmpCus){
			queueInfoMap.put(b.uniqKeyInBranchBank(), b);
		}
		List<PadQueueInfoBean> newCustomerInfo = new ArrayList<PadQueueInfoBean>(queueInfoMap.values());
		Collections.sort(newCustomerInfo, new Comparator<PadQueueInfoBean>() {
			@Override
			public int compare(PadQueueInfoBean lhs, PadQueueInfoBean rhs) {
				int lscore = lhs.queueStatus() == PadQueueInfoBean.FINISH ? -10 : 0;
				int rscore = rhs.queueStatus() == PadQueueInfoBean.FINISH ? -10 : 0;
				lscore += lhs.getBeginTime().compareTo(rhs.getBeginTime()) >= 0 ? 1 : -1;
				return rscore - lscore;
			}
		});

		return newCustomerInfo;
	}

	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			padQueueInfoBeanList = null;
			// hFListAdapter.notifyDataSetChanged();
			hFListAdapter = new HFListAdapter();
			recentList.setAdapter(hFListAdapter);
			isHeadRefresh = true;
			recentList.onRefreshComplete();
			break;
		case customerIndex:
			HResponse res = customerCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				isHeadRefresh = true;
				return;
			}
			JSONObject tmpJsonObject = res.dataJsonObject;
			readJson(tmpJsonObject);
			break;
		case customerOlFlag:
			HResponse resOl = customerOtherLine.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			readOtherLineJosn(resOl);
			break;
		case searchFlag:
			HResponse searchRes = searchDataCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (searchRes == null || searchRes.dataJsonObject == null) {
				return;
			}
			JSONObject searchDataObject = searchRes.dataJsonObject;
			// 对客户信息查询返回的数据进行解析
			readSearchDataJosn(searchDataObject);
			break;
		case customerOtherLineCheckFlag:// 分流类型接口查询
			HResponse otherLineCheckRes = customerOtherLineCheck
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (otherLineCheckRes == null
					|| otherLineCheckRes.dataJsonObject == null) {
				return;
			}
			JSONObject otherLineDataObject = otherLineCheckRes.dataJsonObject;
			// 对客户信息查询返回的数据进行解析
			readOtherLineDataJson(otherLineDataObject);
			break;
		default:
			break;
		}
	}
//分流的解析
	private void readOtherLineDataJson(JSONObject otherLineDataObject) {
		String retCode = null;
		if (otherLineDataObject.has("retCode")) {
			try {
				retCode = otherLineDataObject.getString("retCode");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if ("0000".equals(retCode)) {
			otherLineChannelCheckList.clear();
			/**
			 * 客户信息查询返回的数据解析保存地方
			 */
			try {
				String typesStr = otherLineDataObject.getString("types");
				otherLineChannelCheckList = JSON.parseArray(typesStr,OtherLineChannelEntity.class);
//点击分流弹框
				MyCpDialogFragmentButtons dialog = MyCpDialogFragmentButtons.getNewInstance(queueInfoForShunt,(Serializable) otherLineChannelCheckList);
				dialog.show(manager, "sdd");

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void readSearchDataJosn(JSONObject searchDataObject) {
		String retCode = null;
		if (searchDataObject.has("retCode")) {
			try {
				retCode = searchDataObject.getString("retCode");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if ("0000".equals(retCode)) {
			/**
			 * 客户信息查询返回的数据解析保存地方
			 */
			String searchInfo = searchDataObject.toString();
			SerachCustomerInfo searchCustomer = JSON.parseObject(searchInfo,SerachCustomerInfo.class);
			/*
			DialogFragmentQueryCustomerInfo querycustomerinfo = DialogFragmentQueryCustomerInfo
					.getNewInstance(searchCustomer);
			querycustomerinfo.show(manager, "fmquery");
			*/
			
			// 客户查询成功后，直接跳转到详细信息页面
			Intent intent = new Intent(this, CMMarketingActivity2.class);
			intent.putExtra("custID", searchCustomer.getCUSTID());// 客户ID
			startActivity(intent);
		}
	}

	private void readOtherLineJosn(HResponse response) {
		if (response != null && response.dataJsonObject != null) {
			String otherLineCodeRet = null;
			try {
				otherLineCodeRet = response.dataJsonObject.getString("retCode");
			} catch (JSONException e) {
				e.printStackTrace();
			}

			if ("0000".equals(otherLineCodeRet)) {
				shunted.add(queueInfoForShunt.uniqKeyInBranchBank());

				hFListAdapter = new HFListAdapter();
				recentList.setAdapter(hFListAdapter);
				isHeadRefresh = true;
				recentList.onRefreshComplete();
				return;
			}
		}
		Toast.makeText(HallFirstActivity.this, "分流操作失败！", Toast.LENGTH_SHORT)
				.show();
	}

	private String seriNo() {
        return LogoActivity.user.getImei();
	}

	// 0: unknown  1:male  2:female
	private int gender(String g) {
		if (g == null || g.length() == 0) return 0;
		char c = g.charAt(g.length() - 1);
		if (c == '1') return 1;
		if (c == '2') return 2;
		return 0;
	}

	private void showOtherLineDialog() {
		final AlertDialog.Builder builder = new Builder(HallFirstActivity.this);
		View view = View.inflate(getApplicationContext(),
				R.layout.otherlinedialogfragment, null);
		int h = (screenHeight * 3 / 4);
		int w = (screenWidth * 2 / 5);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, h);

		dialogOtherLine = builder.create();
		dialogOtherLine.addContentView(view, params);
		dialogOtherLine.show();
	}

	@Override
	public void btnClick(String chnlCode, int pt) {
		if ("00".equals(chnlCode)) {
			return;
		}

		if (!TextUtils.isEmpty(chnlCode)) {
			final PadQueueInfoBean b = queueInfoForShunt;

			HRequest request = new HRequest(
					"T000004?method=getJSON"
							+ "&userCode=" + LogoActivity.user.getUserid()
							+ "&seriNo=" + seriNo()
							+ "&chnlCode=" + "02"
							+ "&transCode=T000004&brCode="
							+ LogoActivity.user.getBrCode() + "&serialNo="
							+ b.getSerialNo() + "&machineCode="
							+ b.getMachineCode() + "&newQueueType=" + chnlCode,
					"GET");
			Log.i("1111", "T000004 -->url:" + request.getURL());
			try {
				customerOtherLine.isShowLoadingProcess = true;
				customerOtherLine = new HConnection(this, request,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				customerOtherLine.setIndex(customerOlFlag);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgsearch: // 搜索按钮
			boolean isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T02);
			if(!isRight){
				return;
			}
			
			if (Tool.haveNet(this)) {
				String search_code = etSearch.getText().toString().trim();
				if (search_code == null || TextUtils.isEmpty(search_code)) {
					Toast.makeText(this, "未输入查询条件", Toast.LENGTH_SHORT).show();
					return;
				}
				searchDataInfo(search_code);
			}
			break;
			
		default:
			break;
		}
	}
}