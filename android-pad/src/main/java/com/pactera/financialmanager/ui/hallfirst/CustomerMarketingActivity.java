package com.pactera.financialmanager.ui.hallfirst;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.DefiDialog;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.PieView;
import com.pactera.financialmanager.ui.model.CustomerMarketingInfo;
import com.pactera.financialmanager.ui.model.CustomerMoreInfo;
import com.pactera.financialmanager.ui.model.CustomerPurchase;
import com.pactera.financialmanager.ui.model.LiCaiInfo;
import com.pactera.financialmanager.ui.service.CodeTable;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HRequest;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Tool;

/**
 * 客户详情页
 */

public class CustomerMarketingActivity extends ParentActivity implements
		android.view.View.OnClickListener {

	@SuppressWarnings("unused")
	private int visibleLastIndex = 0;
	@SuppressWarnings("unused")
	private int datasize = 38;
	@SuppressWarnings("unused")
	private View loadMoreView;
	private ViewFlipper customer_marketing_option;
	String id;
	private LinearLayout ls;
	// private ListView lv_hold_server;
	private ExpandableListView lv_hold_server;
	private ListView cm_info_item02_listview, lv_marketing_clew;
	private GridView cm_info_item03_gridview;
	private Button bt_hold_service;
	private Button bt_marketing_clew;
	private Button bt_marketing_item03;
	private Button bt_marketing_item04;
	private Button comm_msg;
	// private HoldServerAdapter hs_Adapter;
	// private MarketingClewAdapter marketingClewAdapter;
	@SuppressWarnings("unused")
	private MarketingDialog showDialog = null;
	@SuppressWarnings("unused")
	private TextView tv_customer_manager, tv_customer_name,
			tv_customer_birthday, tv_cm_sex, tv_cm_age, cm_mobiletel, cm_haddr,
			cm_qianyue, cm_guanhu, staffname0, staffname1, cm_mphone0,
			cm_mphone1, cm_orgnamezh0, cm_orgnamezh1, cm_orgname0, cm_orgname1,
			cm_newdate;
	private ImageView cm_genderImage;
	private WebView cm_genderPhoto;

	private int[][] cutomer_infor = {
			{ R.string.tv_cm_staffname1, R.string.tv_cm_mphone1,
					R.string.tv_cm_orgnamezh1, R.string.tv_cm_orgname1,
					R.string.tv_cm_orgid1, R.string.tv_cm_maporg1 },
			{ R.string.tv_cm_staffname2, R.string.tv_cm_mphone2,
					R.string.tv_cm_orgnamezh2, R.string.tv_cm_orgname2,
					R.string.tv_cm_orgid2, R.string.tv_cm_maporg2 } };

	private int[] tv_customer_services_group = { R.string.tv_cm_goup01,
			R.string.tv_cm_goup02, R.string.tv_cm_goup03 };
	private int[][] tv_customer_services_child1 = {
			{ /* R.string.tv_cm_rsn, */R.string.tv_cm_cardtype,
			/* R.string.tv_cm_cardid, */R.string.tv_cm_rank },
			{ R.string.tv_cm_maxdpsttm, R.string.tv_cm_maxloantm,
					R.string.tv_cm_fayavg, R.string.tv_cm_famavg,
					R.string.tv_cm_fachgsenday, R.string.tv_cm_fachgmonaul,
					R.string.tv_cm_curdpst },
			{ R.string.tv_cm_credit, R.string.tv_cm_online,
					R.string.tv_cm_mobiel, R.string.tv_cm_phone,
					R.string.tv_cm_sms, R.string.tv_cm_trsf } };
	private int[][] tv_customer_services_gravity = {
			{ /* Gravity.CENTER, */Gravity.CENTER, /* Gravity.CENTER, */
			Gravity.CENTER },
			{ Gravity.RIGHT | Gravity.CENTER_VERTICAL,
					Gravity.RIGHT | Gravity.CENTER_VERTICAL,
					Gravity.RIGHT | Gravity.CENTER_VERTICAL,
					Gravity.RIGHT | Gravity.CENTER_VERTICAL,
					Gravity.RIGHT | Gravity.CENTER_VERTICAL,
					Gravity.RIGHT | Gravity.CENTER_VERTICAL,
					Gravity.RIGHT | Gravity.CENTER_VERTICAL },
			{ Gravity.CENTER, Gravity.CENTER, Gravity.CENTER, Gravity.CENTER,
					Gravity.CENTER, Gravity.CENTER } };

	private StringBuilder url_ext_customerMoreInfo, url_ext_customerPurchase,
			url_ext_liCaiInfo, url_ext_marketingClew;
	private CustomerMoreInfo customerMoreInfo;
	private List<CustomerPurchase> customerPurchases;
	private List<LiCaiInfo> liCaiInfos;
	private List<CustomerMarketingInfo> customerMarketingInfos;
	private TextView customer_hold_product_percent1,
			customer_hold_product_percent2, customer_hold_product_percent3,
			customer_hold_product_percent4, customer_hold_product_percent5,
			customer_hold_product_percent6, customer_hold_product_percent7,
			customer_hold_product_percent8, customer_hold_product_percent9,
			customer_hold_product_percent10, customer_hold_product_percent11,
			customer_hold_product_money1, customer_hold_product_money2,
			customer_hold_product_money3, customer_hold_product_money4,
			customer_hold_product_money5, customer_hold_product_money6,
			customer_hold_product_money7, customer_hold_product_money8,
			customer_hold_product_money9, customer_hold_product_money10;
	private Intent intent;
	private DecimalFormat df01 = new DecimalFormat("#0.00");
	private DecimalFormat df02 = new DecimalFormat("#,##0.00");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.cm);
		intent = getIntent();
		url_ext_customerMoreInfo = new StringBuilder();
		url_ext_customerMoreInfo.append("T000006?method=getJSON&userCode=");
		url_ext_customerMoreInfo.append(LogoActivity.user.getUserid());
		url_ext_customerMoreInfo.append("&password=");
		url_ext_customerMoreInfo.append(LogoActivity.user.getPassword());
		url_ext_customerMoreInfo.append("&seriNo=");
		url_ext_customerMoreInfo.append(LogoActivity.user.getImei());
		url_ext_customerMoreInfo.append("&custID=");
		url_ext_customerMoreInfo.append(intent.getStringExtra("custID"));
		url_ext_customerMoreInfo.append("&brCode="
				+ LogoActivity.user.getBrID()
				+ "&chnlCode=eee&transCode=T000006");
		findView();
		bindOnClickListener();
		bt_hold_service.setEnabled(false);
		bt_marketing_clew.setEnabled(true);
		bt_marketing_item03.setEnabled(true);
		bt_marketing_item04.setEnabled(true);

		// lv_hold_server.addFooterView(loadMoreView);

		// initializeAdapter();
		// customerServices = new ArrayList<CustomerServices>();
		// getCustomerServicesData();
		System.out.println("getCustomerServicesData");
		// hs_Adapter = new HoldServerAdapter(customerServices);
		// lv_hold_server.setAdapter(hs_Adapter);
		// lv_hold_server.setOnScrollListener((OnScrollListener) this);
		//
		// lv_hold_server.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		// // TODO Auto-generated method stub
		// // showDialog();
		// Toast.makeText(getApplicationContext(), "点击了第" + arg2 + "项目。",
		// 0).show();
		// }
		// });
		// lv_marketing_clew.setAdapter(new LvMarketingAdapter());

		// pic_width = ls.getWidth();
		// pic_high = ls.getHeight();
		// ls.addView(chartPanel); 添加饼图

		/*
		 * private static final long serialVersionUID = -8264189042790240748L;
		 * public String custID; //客户ID public String queueNum; //排队号 public
		 * String cnName; //客户姓名 public String sex; //客户性别 public String
		 * queueTime; //排队时间 public String callTime; //叫号时间 public String
		 * waitTime; //等待时间 public String stateFlag; // public String birthDay;
		 * //客户生日 public String rank; //客户级别
		 */
		// startLogin(bundle);
		// CustomerInfo customerInfo = new CustomerInfo();

		getCustomerMoreData();
	}

	private void bindOnClickListener() {

		bt_hold_service.setOnClickListener(this);
		bt_marketing_clew.setOnClickListener(this);
		bt_marketing_item03.setOnClickListener(this);
		bt_marketing_item04.setOnClickListener(this);

		WebSettings setting = cm_genderPhoto.getSettings();
		setting.setJavaScriptEnabled(true);
		setting.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		cm_genderPhoto.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) { // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
				view.loadUrl(url);
				return false;
			}
		});

		cm_qianyue.setOnClickListener(this);

		cm_guanhu.setOnClickListener(this);
		comm_msg.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		// Intent intent;
		switch (view.getId()) {
		case R.id.bt_hold_service:
			bt_hold_service.setEnabled(false);
			bt_marketing_clew.setEnabled(true);
			bt_marketing_item03.setEnabled(true);
			bt_marketing_item04.setEnabled(true);
			bt_hold_service.setTextColor(Color.BLACK);
			bt_marketing_clew.setTextColor(Color.GRAY);
			bt_marketing_item03.setTextColor(Color.GRAY);
			bt_marketing_item04.setTextColor(Color.GRAY);
			customer_marketing_option.setDisplayedChild(0);

			break;

		case R.id.bt_marketing_clew:
			bt_hold_service.setEnabled(true);
			bt_marketing_clew.setEnabled(false);
			bt_marketing_item03.setEnabled(true);
			bt_marketing_item04.setEnabled(true);
			bt_hold_service.setTextColor(Color.GRAY);
			bt_marketing_clew.setTextColor(Color.BLACK);
			bt_marketing_item03.setTextColor(Color.GRAY);
			bt_marketing_item04.setTextColor(Color.GRAY);
			customer_marketing_option.setDisplayedChild(1);
			if (customerPurchases == null) {
				getCustomerPurchase();
			}
			break;
		case R.id.bt_marketing_item03:
			bt_hold_service.setEnabled(true);
			bt_marketing_clew.setEnabled(true);
			bt_marketing_item03.setEnabled(false);
			bt_marketing_item04.setEnabled(true);
			bt_hold_service.setTextColor(Color.GRAY);
			bt_marketing_clew.setTextColor(Color.GRAY);
			bt_marketing_item03.setTextColor(Color.BLACK);
			bt_marketing_item04.setTextColor(Color.GRAY);
			customer_marketing_option.setDisplayedChild(2);
			if (liCaiInfos == null) {
				getLiCaiInfo();
			}
			break;
		case R.id.bt_marketing_item04:
			bt_hold_service.setEnabled(true);
			bt_marketing_clew.setEnabled(true);
			bt_marketing_item03.setEnabled(true);
			bt_marketing_item04.setEnabled(false);
			bt_hold_service.setTextColor(Color.GRAY);
			bt_marketing_clew.setTextColor(Color.GRAY);
			bt_marketing_item03.setTextColor(Color.GRAY);
			bt_marketing_item04.setTextColor(Color.BLACK);
			customer_marketing_option.setDisplayedChild(3);
			if (customerMarketingInfos == null) {
				getMarketingClewInfo();
			}
			break;

		case R.id.cusmar_btn_back_2hallfirst:
			// startActivity(new Intent(getApplicationContext(),
			// HallFirstActivity.class));
			// this.finish();
			// onBackPressed();
			finish();
			break;
		/*
		 * ImageView fundBtn11, fundBtn12, fundBtn21, fundBtn22, productBtn11,
		 * productBtn12, productBtn21, productBtn22, secureBtn11;
		 */

		case R.id.cm_qianyue:
			cm_qianyue.setEnabled(false);
			cm_guanhu.setEnabled(true);
			cm_qianyue.setTextColor(Color.BLACK);
			cm_guanhu.setTextColor(Color.GRAY);
			setCustomerInforText(1);

			break;
		case R.id.cm_guanhu:
			setCustomerInforText(2);
			cm_qianyue.setEnabled(true);
			cm_guanhu.setEnabled(false);
			cm_qianyue.setTextColor(Color.GRAY);
			cm_guanhu.setTextColor(Color.BLACK);

			break;
		case R.id.comm_msg:
			getMsgInfo();
			// comm_msg.setEnabled(false);

			break;
		default:
			break;
		}
	}

	private HConnection customerMoreCon;
	private final int customerMoreIndex = 1;

	private void getCustomerMoreData() {
		HRequest request = new HRequest(url_ext_customerMoreInfo.toString(),
				"GET");
		try {
			customerMoreCon = new HConnection(this, request, LogoActivity.user,
					HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			customerMoreCon.setIndex(customerMoreIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 最近购买力
	private HConnection customerPurchaseConn;
	public final int customerPurchaseIndex = 2;

	private void getCustomerPurchase() {
		if (url_ext_customerPurchase == null) {
			url_ext_customerPurchase = new StringBuilder();
			url_ext_customerPurchase.append("T000021?method=getJSON&userCode=");
			url_ext_customerPurchase.append(LogoActivity.user.getUserid());
			url_ext_customerPurchase.append("&password=");
			url_ext_customerPurchase.append(LogoActivity.user.getPassword());
			url_ext_customerPurchase.append("&seriNo=");
			url_ext_customerPurchase.append(LogoActivity.user.getImei());
			url_ext_customerPurchase.append("&custID=");
			url_ext_customerPurchase.append(intent.getStringExtra("custID"));
			url_ext_customerPurchase.append("&brCode="
					+ LogoActivity.user.getBrID()
					+ "&chnlCode=eee&transCode=T000021");
		}
		HRequest request = new HRequest(url_ext_customerPurchase.toString(),
				"GET");
		try {
			customerPurchaseConn = new HConnection(
					CustomerMarketingActivity.this, request, LogoActivity.user,
					HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			customerPurchaseConn.setIndex(customerPurchaseIndex);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// 推荐理财产品
	HConnection pCProductConn;
	public final int pCProductIndex = 3;

	private void getLiCaiInfo() {
		if (url_ext_liCaiInfo == null) {
			url_ext_liCaiInfo = new StringBuilder();
			url_ext_liCaiInfo.append("T000026?method=getJSON&userCode=");
			url_ext_liCaiInfo.append(LogoActivity.user.getUserid());
			url_ext_liCaiInfo.append("&password=");
			url_ext_liCaiInfo.append(LogoActivity.user.getPassword());
			url_ext_liCaiInfo.append("&seriNo=");
			url_ext_liCaiInfo.append(LogoActivity.user.getImei());
			url_ext_liCaiInfo.append("&custID=");
			url_ext_liCaiInfo.append(intent.getStringExtra("custID"));
			url_ext_liCaiInfo.append("&brCode=" + LogoActivity.user.getBrID()
					+ "&chnlCode=eee&transCode=T000026");
		}
		HRequest request = new HRequest(url_ext_liCaiInfo.toString(), "GET");
		try {
			pCProductConn = new HConnection(CustomerMarketingActivity.this,
					request, LogoActivity.user,
					HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			pCProductConn.setIndex(pCProductIndex);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// 销售线索
	HConnection marketingClewConn;
	public final int marketingClewIndex = 4;

	private void getMarketingClewInfo() {
		if (url_ext_marketingClew == null) {
			url_ext_marketingClew = new StringBuilder();
			url_ext_marketingClew.append("T000025?method=getJSON&userCode=");
			url_ext_marketingClew.append(LogoActivity.user.getUserid());
			url_ext_marketingClew.append("&password=");
			url_ext_marketingClew.append(LogoActivity.user.getPassword());
			url_ext_marketingClew.append("&seriNo=");
			url_ext_marketingClew.append(LogoActivity.user.getImei());
			url_ext_marketingClew.append("&custID=");
			url_ext_marketingClew.append(intent.getStringExtra("custID"));
			url_ext_marketingClew.append("&brCode="
					+ LogoActivity.user.getBrID()
					+ "&chnlCode=eee&transCode=T000025");
		}
		HRequest request = new HRequest(url_ext_marketingClew.toString(), "GET");
		try {
			marketingClewConn = new HConnection(CustomerMarketingActivity.this,
					request, LogoActivity.user,
					HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			marketingClewConn.setIndex(marketingClewIndex);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// 短信平台
	HConnection msgConn;
	public final int msgIndex = 5;

	private void getMsgInfo() {
		if (url_ext_marketingClew == null) {
			url_ext_marketingClew = new StringBuilder();
			url_ext_marketingClew.append("T000102?method=getJSON&userCode=");
			url_ext_marketingClew.append(LogoActivity.user.getUserid());
			url_ext_marketingClew.append("&password=");
			url_ext_marketingClew.append(LogoActivity.user.getPassword());
			url_ext_marketingClew.append("&seriNo=");
			url_ext_marketingClew.append(LogoActivity.user.getImei());
			url_ext_marketingClew.append("&custID=");
			url_ext_marketingClew.append(intent.getStringExtra("custID"));
			url_ext_marketingClew.append("&brCode="
					+ LogoActivity.user.getBrID()
					+ "&chnlCode=eee&transCode=T000102");
			url_ext_marketingClew.append("&queueNum="
					+ intent.getStringExtra("queueNum"));

			url_ext_marketingClew.append("&STAFFNAME1="
					+ getHexString(customerMoreInfo.STAFFNAME1) + "&MPHONE1="
					+ customerMoreInfo.MPHONE1 + "&STAFFNAME2="
					+ getHexString(customerMoreInfo.STAFFNAME2) + "&MPHONE2="
					+ customerMoreInfo.MPHONE2 + "&cnName="
					+ getHexString(customerMoreInfo.cnName) + "&cardType="
					+ customerMoreInfo.cardType + "&rank="
					+ customerMoreInfo.rank + "&brName2="
					+ getHexString(LogoActivity.user.getBrName2()));

		}
		HRequest request = new HRequest(url_ext_marketingClew.toString(), "GET");

		try {

			msgConn = new HConnection(CustomerMarketingActivity.this, request,
					LogoActivity.user, HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			msgConn.setIndex(msgIndex);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private String getHexString(String str) {
		StringBuilder wordHex = new StringBuilder();
		if (str != null) {
			char[] wordArry = str.toCharArray();
			
			for (int i = 0; i < wordArry.length; i++) {

				
				String temp=Integer.toHexString((int) wordArry[i]);
				if (temp.length()==2) {
					temp="00"+temp;
				}
				wordHex.append(temp);


			}
			return wordHex.toString();
		} else {
			return "";
		}
	}

	// private void showCustomerMsg() {
	//
	// AlertDialog.Builder builder = new Builder(getParent());
	// builder.setTitle("温馨提示");
	// builder.setMessage("正在开发中…");
	// builder.setPositiveButton(getString(R.string.dialog_sure),
	// new OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// dialog.dismiss();
	// }
	// });
	//
	// builder.create().show();
	//
	// }

	// private void initializeAdapter() {
	// List<CustomerSigningService> users = new
	// ArrayList<CustomerSigningService>();
	// List<CustomerSigningService> users = getCustomerServies("001");
	// List<CustomerSigningService> customerService = customerHoldServiceXml
	// .getCustomerHoldServiceXML(new ByteArrayInputStream(
	// resHoldService.xmlString.getBytes()));
	// for (int i = 0; i < 5; i++) {
	// CustomerSigningService items = new CustomerSigningService();
	// items.setServiceName("Title" + i);
	// items.setStatus("This is News Content" + i);
	// // users.add(items);
	// }
	// hs_Adapter = new HoldServerAdapter(getApplicationContext(),
	// customerService);
	// }

	// private void loadMoreDate() {
	// int count = hs_Adapter.getCount();
	// if (count + 10 <= datasize) {
	// for (int i = count + 1; i <= count + 10; i++) {
	// CustomerSigningService item = new CustomerSigningService();
	// item.setServiceName("" + i);
	// item.setStatus("This is News Content" + i);
	// hs_Adapter.addUserItem(item);
	// }
	// } else {
	// for (int i = count + 1; i <= datasize; i++) {
	// CustomerSigningService item = new CustomerSigningService();
	// item.setServiceName("Title" + i);
	// item.setStatus("This is News Content" + i);
	// hs_Adapter.addUserItem(item);
	// }
	// }
	// }

	private void findView() {
		// TODO Auto-generated method stub
		customer_marketing_option = (ViewFlipper) findViewById(R.id.customer_marketing_flipper);
		ls = (LinearLayout) findViewById(R.id.customer_marketing_product_unit_img);
		lv_hold_server = (ExpandableListView) findViewById(R.id.lv_hold_server_option);
		cm_info_item02_listview = (ListView) findViewById(R.id.cm_info_item02_listview);
		cm_info_item03_gridview = (GridView) findViewById(R.id.cm_info_item03_gridview);
		lv_marketing_clew = (ListView) findViewById(R.id.lv_marketing_clew);
		bt_hold_service = (Button) findViewById(R.id.bt_hold_service);
		bt_marketing_clew = (Button) findViewById(R.id.bt_marketing_clew);
		bt_marketing_item03 = (Button) findViewById(R.id.bt_marketing_item03);
		bt_marketing_item04 = (Button) findViewById(R.id.bt_marketing_item04);

		customer_hold_product_percent1 = (TextView) findViewById(R.id.customer_hold_product_percent1);
		customer_hold_product_percent2 = (TextView) findViewById(R.id.customer_hold_product_percent2);
		customer_hold_product_percent3 = (TextView) findViewById(R.id.customer_hold_product_percent3);
		customer_hold_product_percent4 = (TextView) findViewById(R.id.customer_hold_product_percent4);
		customer_hold_product_percent5 = (TextView) findViewById(R.id.customer_hold_product_percent5);
		customer_hold_product_percent6 = (TextView) findViewById(R.id.customer_hold_product_percent6);
		customer_hold_product_percent7 = (TextView) findViewById(R.id.customer_hold_product_percent7);
		customer_hold_product_percent8 = (TextView) findViewById(R.id.customer_hold_product_percent8);
		customer_hold_product_percent9 = (TextView) findViewById(R.id.customer_hold_product_percent9);
		customer_hold_product_percent10 = (TextView) findViewById(R.id.customer_hold_product_percent10);
		customer_hold_product_percent11 = (TextView) findViewById(R.id.customer_hold_product_percent11);
		customer_hold_product_money1 = (TextView) findViewById(R.id.customer_hold_product_money1);
		customer_hold_product_money2 = (TextView) findViewById(R.id.customer_hold_product_money2);
		customer_hold_product_money3 = (TextView) findViewById(R.id.customer_hold_product_money3);
		customer_hold_product_money4 = (TextView) findViewById(R.id.customer_hold_product_money4);
		customer_hold_product_money5 = (TextView) findViewById(R.id.customer_hold_product_money5);
		customer_hold_product_money6 = (TextView) findViewById(R.id.customer_hold_product_money6);
		customer_hold_product_money7 = (TextView) findViewById(R.id.customer_hold_product_money7);
		customer_hold_product_money8 = (TextView) findViewById(R.id.customer_hold_product_money8);
		customer_hold_product_money9 = (TextView) findViewById(R.id.customer_hold_product_money9);
		customer_hold_product_money10 = (TextView) findViewById(R.id.customer_hold_product_money10);

		cm_genderImage = (ImageView) findViewById(R.id.cm_genderImage);
		cm_genderPhoto = (WebView) findViewById(R.id.cm_genderPhoto);

		tv_customer_name = (TextView) findViewById(R.id.tv_customer_name);
		tv_customer_birthday = (TextView) findViewById(R.id.tv_customer_birthday);

		tv_cm_sex = (TextView) findViewById(R.id.tv_cm_sex);
		tv_cm_age = (TextView) findViewById(R.id.tv_cm_age);
		cm_mobiletel = (TextView) findViewById(R.id.cm_mobiletel);

		cm_haddr = (TextView) findViewById(R.id.cm_haddr);
		comm_msg = (Button) findViewById(R.id.comm_msg);
		/*
		 * cm_qianyue, cm_guanhu, staffname0, staffname1, cm_mphone0,
		 * cm_mphone1, cm_orgnamezh0, cm_orgnamezh1, cm_orgname0, cm_orgname1,
		 * cm_orgid0, cm_orgid1, cm_maporg0, cm_maporg1;
		 */

		cm_qianyue = (TextView) findViewById(R.id.cm_qianyue);
		cm_guanhu = (TextView) findViewById(R.id.cm_guanhu);
		staffname0 = (TextView) findViewById(R.id.staffname0);
		staffname1 = (TextView) findViewById(R.id.staffname1);
		cm_mphone0 = (TextView) findViewById(R.id.cm_mphone0);
		cm_mphone1 = (TextView) findViewById(R.id.cm_mphone1);
		cm_orgnamezh0 = (TextView) findViewById(R.id.cm_orgnamezh0);
		cm_orgnamezh1 = (TextView) findViewById(R.id.cm_orgnamezh1);
		cm_orgname0 = (TextView) findViewById(R.id.cm_orgname0);
		cm_orgname1 = (TextView) findViewById(R.id.cm_orgname1);
		cm_newdate = (TextView) findViewById(R.id.new_date1);

	}

	// 显示dialog最有从下到上的移动的动画效果
	// private void showDialog() {
	// dialog = new DefiDialog(CustomerMarketingActivity.this,
	// R.style.definitionDialog);
	// View view = View.inflate(CustomerMarketingActivity.this,
	// R.layout.customer_defi_dialog, null);
	// view.findViewById(R.id.imbt_id).setOnClickListener(
	// new View.OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// if (dialog.isShowing())
	// dialog.dismiss();
	// }
	// });
	// Animation ai = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
	// 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
	// Animation.RELATIVE_TO_PARENT, 1.0f,
	// Animation.RELATIVE_TO_PARENT, 0.0f);
	// ai.setDuration(1000);
	// view.startAnimation(ai);
	// dialog.setContentView(view);
	// dialog.show();
	// }

	@Override
	public void onDestroy() {
		if (customerPurchases != null) {
			customerPurchases.clear();
			customerPurchases = null;
		}
		if (liCaiInfos != null) {
			liCaiInfos.clear();
			liCaiInfos = null;
		}
		if (customerMarketingInfos != null) {
			customerMarketingInfos.clear();
			customerMarketingInfos = null;
		}
		super.onDestroy();
	}

	@Override
	public void onConnected(Message msg) {
		// TODO Auto-generated method stub
		super.onConnected(msg);
		switch (connectionIndex) {
		case customerMoreIndex:
			HResponse res = customerMoreCon
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			JSONObject tmpJsonObject = res.dataJsonObject;
			// String jsonString = tmpJsonObject.toString();
			readJson01(tmpJsonObject);
			break;
		case customerPurchaseIndex:
			res = customerPurchaseConn
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readJson02(tmpJsonObject);
			break;
		case pCProductIndex:
			res = pCProductConn
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readJson03(tmpJsonObject);
			break;
		case marketingClewIndex:
			res = marketingClewConn
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readJson04(tmpJsonObject);
			break;
		case msgIndex:
			res = msgConn.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readJson05(tmpJsonObject);
			break;

		}
	}

	private void readJson01(JSONObject tmpJsonObject) {
		JSONArray tmpJsonArray = null;
		if (tmpJsonObject.has("group")) {
			try {
				tmpJsonArray = tmpJsonObject.getJSONArray("group");
				if (tmpJsonArray != null && tmpJsonArray.length() > 0) {
					customerMoreInfo = new CustomerMoreInfo();
					JSONObject tmpJsonObject02 = (JSONObject) tmpJsonArray
							.get(0);
					if (tmpJsonObject02.has("maxLoanTm"))
						customerMoreInfo.maxLoanTm = tmpJsonObject02.getString(
								"maxLoanTm").trim();
					if (tmpJsonObject02.has("futures"))
						customerMoreInfo.futures = tmpJsonObject02.getString(
								"futures").trim();
					if (tmpJsonObject02.has("debt"))
						customerMoreInfo.debt = tmpJsonObject02.getString(
								"debt").trim();
					if (tmpJsonObject02.has("faYavg"))
						customerMoreInfo.faYavg = tmpJsonObject02.getString(
								"faYavg").trim();
					if (tmpJsonObject02.has("ORGNAMEzh1"))
						customerMoreInfo.ORGNAMEzh1 = tmpJsonObject02
								.getString("ORGNAMEzh1").trim();
					if (tmpJsonObject02.has("ORGNAMEzh2"))
						customerMoreInfo.ORGNAMEzh2 = tmpJsonObject02
								.getString("ORGNAMEzh2").trim();
					if (tmpJsonObject02.has("cnName"))
						customerMoreInfo.cnName = tmpJsonObject02.getString(
								"cnName").trim();
					if (tmpJsonObject02.has("mobileTel"))
						customerMoreInfo.mobileTel = tmpJsonObject02.getString(
								"mobileTel").trim();
					if (tmpJsonObject02.has("fex"))
						customerMoreInfo.fex = tmpJsonObject02.getString("fex")
								.trim();
					if (tmpJsonObject02.has("faMavg"))
						customerMoreInfo.faMavg = tmpJsonObject02.getString(
								"faMavg").trim();
					if (tmpJsonObject02.has("didNo"))
						customerMoreInfo.didNo = tmpJsonObject02.getString(
								"didNo").trim();
					if (tmpJsonObject02.has("MAPORG1"))
						customerMoreInfo.MAPORG1 = tmpJsonObject02.getString(
								"MAPORG1").trim();
					if (tmpJsonObject02.has("MAPORG2"))
						customerMoreInfo.MAPORG2 = tmpJsonObject02.getString(
								"MAPORG2").trim();
					if (tmpJsonObject02.has("cardID"))
						customerMoreInfo.cardID = tmpJsonObject02.getString(
								"cardID").trim();
					if (tmpJsonObject02.has("rank"))
						customerMoreInfo.rank = tmpJsonObject02.getString(
								"rank").trim();
					if (tmpJsonObject02.has("curdpst"))
						customerMoreInfo.curdpst = tmpJsonObject02.getString(
								"curdpst").trim();
					if (tmpJsonObject02.has("custID"))
						customerMoreInfo.custID = tmpJsonObject02.getString(
								"custID").trim();
					if (tmpJsonObject02.has("MPHONE1"))
						customerMoreInfo.MPHONE1 = tmpJsonObject02.getString(
								"MPHONE1").trim();
					if (tmpJsonObject02.has("MPHONE2"))
						customerMoreInfo.MPHONE2 = tmpJsonObject02.getString(
								"MPHONE2").trim();
					if (tmpJsonObject02.has("ORGID1"))
						customerMoreInfo.ORGID1 = tmpJsonObject02.getString(
								"ORGID1").trim();
					if (tmpJsonObject02.has("ORGID2"))
						customerMoreInfo.ORGID2 = tmpJsonObject02.getString(
								"ORGID2").trim();
					if (tmpJsonObject02.has("ORGNAME1"))
						customerMoreInfo.ORGNAME1 = tmpJsonObject02.getString(
								"ORGNAME1").trim();
					if (tmpJsonObject02.has("ORGNAME2"))
						customerMoreInfo.ORGNAME2 = tmpJsonObject02.getString(
								"ORGNAME2").trim();
					if (tmpJsonObject02.has("fund"))
						customerMoreInfo.fund = tmpJsonObject02.getString(
								"fund").trim();
					if (tmpJsonObject02.has("STAFFNAME1"))
						customerMoreInfo.STAFFNAME1 = tmpJsonObject02
								.getString("STAFFNAME1").trim();
					if (tmpJsonObject02.has("STAFFNAME2"))
						customerMoreInfo.STAFFNAME2 = tmpJsonObject02
								.getString("STAFFNAME2").trim();
					if (tmpJsonObject02.has("maxDpstTm"))
						customerMoreInfo.maxDpstTm = tmpJsonObject02.getString(
								"maxDpstTm").trim();
					if (tmpJsonObject02.has("fp"))
						customerMoreInfo.fp = tmpJsonObject02.getString("fp");
					if (tmpJsonObject02.has("rsn"))
						customerMoreInfo.rsn = tmpJsonObject02.getString("rsn")
								.trim();
					if (tmpJsonObject02.has("mobiel"))
						customerMoreInfo.mobiel = tmpJsonObject02.getString(
								"mobiel").trim();
					if (tmpJsonObject02.has("trsf"))
						customerMoreInfo.trsf = tmpJsonObject02.getString(
								"trsf").trim();
					if (tmpJsonObject02.has("faChgMonAul"))
						customerMoreInfo.faChgMonAul = tmpJsonObject02
								.getString("faChgMonAul").trim();
					if (tmpJsonObject02.has("online"))
						customerMoreInfo.online = tmpJsonObject02.getString(
								"online").trim();
					if (tmpJsonObject02.has("sms"))
						customerMoreInfo.sms = tmpJsonObject02.getString("sms")
								.trim();
					if (tmpJsonObject02.has("didTp"))
						customerMoreInfo.didTp = tmpJsonObject02.getString(
								"didTp").trim();
					if (tmpJsonObject02.has("isrc"))
						customerMoreInfo.isrc = tmpJsonObject02.getString(
								"isrc").trim();
					if (tmpJsonObject02.has("gold"))
						customerMoreInfo.gold = tmpJsonObject02.getString(
								"gold").trim();
					if (tmpJsonObject02.has("sex"))
						customerMoreInfo.sex = tmpJsonObject02.getString("sex")
								.trim();
					if (tmpJsonObject02.has("faChgSenDay"))
						customerMoreInfo.faChgSenDay = tmpJsonObject02
								.getString("faChgSenDay").trim();
					if (tmpJsonObject02.has("third"))
						customerMoreInfo.third = tmpJsonObject02.getString(
								"third").trim();
					if (tmpJsonObject02.has("cardType"))
						customerMoreInfo.cardType = tmpJsonObject02.getString(
								"cardType").trim();
					if (tmpJsonObject02.has("dpst"))
						customerMoreInfo.dpst = tmpJsonObject02.getString(
								"dpst").trim();
					if (tmpJsonObject02.has("newDate"))
						customerMoreInfo.newDate = tmpJsonObject02.getString(
								"newDate").trim();
					if (tmpJsonObject02.has("phone"))
						customerMoreInfo.phone = tmpJsonObject02.getString(
								"phone").trim();
					if (tmpJsonObject02.has("loan"))
						customerMoreInfo.loan = tmpJsonObject02.getString(
								"loan").trim();
					if (tmpJsonObject02.has("hAddr"))
						customerMoreInfo.hAddr = tmpJsonObject02.getString(
								"hAddr").trim();
					if (tmpJsonObject02.has("credit"))
						customerMoreInfo.credit = tmpJsonObject02.getString(
								"credit").trim();
					if (tmpJsonObject02.has("birthday"))
						customerMoreInfo.birthday = tmpJsonObject02.getString(
								"birthday").trim();
					if (tmpJsonObject02.has("age"))
						customerMoreInfo.age = tmpJsonObject02.getString("age")
								.trim();

					/*
					 * tv_customer_manager, tv_customer_name,
					 * tv_customer_birthday, tv_cm_sex, cm_newdate, cm_custid,
					 * cm_didtp, cm_didno, cm_mobiletel, cm_haddr;
					 */

				}
				setCustomerInforText(1);
				lv_hold_server.setAdapter(new HoldServerAdapter());
				lv_hold_server.expandGroup(0);
				lv_hold_server.expandGroup(1);
				lv_hold_server.expandGroup(2);
				lv_hold_server.setGroupIndicator(null);

				double percent01 = 0.00d;
				double percent02 = 0.00d;
				double percent03 = 0.00d;
				double percent04 = 0.00d;
				double percent05 = 0.00d;
				double percent06 = 0.00d;
				double percent07 = 0.00d;
				double percent08 = 0.00d;
				double percent09 = 0.00d;
				double percent10 = 0.00d;
				double percentTotal;
				float[] percent;
				if (customerMoreInfo.dpst != null
						&& !"".equals(customerMoreInfo.dpst)
						&& !"null".equalsIgnoreCase(customerMoreInfo.dpst)) {
					percent01 = Double.parseDouble(customerMoreInfo.dpst);
				}
				if (customerMoreInfo.loan != null
						&& !"".equals(customerMoreInfo.loan)
						&& !"null".equalsIgnoreCase(customerMoreInfo.loan)) {
					percent02 = Double.parseDouble(customerMoreInfo.loan);
				}
				if (customerMoreInfo.fund != null
						&& !"".equals(customerMoreInfo.fund)
						&& !"null".equalsIgnoreCase(customerMoreInfo.fund)) {
					percent03 = Double.parseDouble(customerMoreInfo.fund);
				}
				if (customerMoreInfo.debt != null
						&& !"".equals(customerMoreInfo.debt)
						&& !"null".equalsIgnoreCase(customerMoreInfo.debt)) {
					percent04 = Double.parseDouble(customerMoreInfo.debt);
				}
				if (customerMoreInfo.fp != null
						&& !"".equals(customerMoreInfo.fp)
						&& !"null".equalsIgnoreCase(customerMoreInfo.fp)) {
					percent05 = Double.parseDouble(customerMoreInfo.fp);
				}
				if (customerMoreInfo.fex != null
						&& !"".equals(customerMoreInfo.fex)
						&& !"null".equalsIgnoreCase(customerMoreInfo.fex)) {
					percent06 = Double.parseDouble(customerMoreInfo.fex);
				}
				if (customerMoreInfo.isrc != null
						&& !"".equals(customerMoreInfo.isrc)
						&& !"null".equalsIgnoreCase(customerMoreInfo.isrc)) {
					percent07 = Double.parseDouble(customerMoreInfo.isrc);
				}
				if (customerMoreInfo.third != null
						&& !"".equals(customerMoreInfo.third)
						&& !"null".equalsIgnoreCase(customerMoreInfo.third)) {
					percent08 = Double.parseDouble(customerMoreInfo.third);
				}
				if (customerMoreInfo.futures != null
						&& !"".equals(customerMoreInfo.futures)
						&& !"null".equalsIgnoreCase(customerMoreInfo.futures)) {
					percent09 = Double.parseDouble(customerMoreInfo.futures);
				}
				if (customerMoreInfo.gold != null
						&& !"".equals(customerMoreInfo.gold)
						&& !"null".equalsIgnoreCase(customerMoreInfo.gold)) {
					percent10 = Double.parseDouble(customerMoreInfo.gold);
				}
				percentTotal = percent01 + percent02 + percent03 + percent04
						+ percent05 + percent06 + percent07 + percent08
						+ percent09 + percent10;
				float tmpPercent01 = 0f;
				float tmpPercent02 = 0f;
				float tmpPercent03 = 0f;
				float tmpPercent04 = 0f;
				float tmpPercent05 = 0f;
				float tmpPercent06 = 0f;
				float tmpPercent07 = 0f;
				float tmpPercent08 = 0f;
				float tmpPercent09 = 0f;
				float tmpPercent10 = 0f;
				float tmpPercent11 = 360f;
				if (percentTotal != 0) {
					tmpPercent01 = (float) (percent01 * 360 / percentTotal);
					tmpPercent02 = (float) (percent02 * 360 / percentTotal);
					tmpPercent03 = (float) (percent03 * 360 / percentTotal);
					tmpPercent04 = (float) (percent04 * 360 / percentTotal);
					tmpPercent05 = (float) (percent05 * 360 / percentTotal);
					tmpPercent06 = (float) (percent06 * 360 / percentTotal);
					tmpPercent07 = (float) (percent07 * 360 / percentTotal);
					tmpPercent08 = (float) (percent08 * 360 / percentTotal);
					tmpPercent09 = (float) (percent09 * 360 / percentTotal);
					tmpPercent10 = (float) (percent10 * 360 / percentTotal);
					tmpPercent11 = 0f;
				}
				percent = new float[] { tmpPercent01, tmpPercent02,
						tmpPercent03, tmpPercent04, tmpPercent05, tmpPercent06,
						tmpPercent07, tmpPercent08, tmpPercent09, tmpPercent10,
						tmpPercent11 };
				int[] colors = {
						getResources().getColor(R.color.hf_cm_piecolor01),//
						getResources().getColor(R.color.hf_cm_piecolor02),
						getResources().getColor(R.color.hf_cm_piecolor03),//
						getResources().getColor(R.color.hf_cm_piecolor04),
						getResources().getColor(R.color.hf_cm_piecolor05),//
						getResources().getColor(R.color.hf_cm_piecolor06),
						getResources().getColor(R.color.hf_cm_piecolor07),//
						getResources().getColor(R.color.hf_cm_piecolor08),
						getResources().getColor(R.color.hf_cm_piecolor09),
						getResources().getColor(R.color.hf_cm_piecolor10),
						getResources().getColor(R.color.hf_cm_piecolor11) };
				int[] shade_colors = {
						getResources().getColor(R.color.hf_cm_piecolor_dark01),//
						getResources().getColor(R.color.hf_cm_piecolor_dark02),
						getResources().getColor(R.color.hf_cm_piecolor_dark03),//
						getResources().getColor(R.color.hf_cm_piecolor_dark04),
						getResources().getColor(R.color.hf_cm_piecolor_dark05),//
						getResources().getColor(R.color.hf_cm_piecolor_dark06),
						getResources().getColor(R.color.hf_cm_piecolor_dark07),//
						getResources().getColor(R.color.hf_cm_piecolor_dark08),
						getResources().getColor(R.color.hf_cm_piecolor_dark09),
						getResources().getColor(R.color.hf_cm_piecolor_dark10),
						getResources().getColor(R.color.hf_cm_piecolor_dark11) };
				ls.addView(shou3DBing(colors, shade_colors, percent)); // 添加饼图

				// System.out.println(df.format(f));
				customer_hold_product_percent1
						.setText((percentTotal != 0 ? df01.format(percent01
								* 100 / percentTotal) : "0.00")
								+ "%");
				customer_hold_product_money1.setText(df02.format(percent01)
						+ "元");

				customer_hold_product_percent2
						.setText((percentTotal != 0 ? df01.format(percent02
								* 100 / percentTotal) : "0.00")
								+ "%");
				customer_hold_product_money2.setText(df02.format(percent02)
						+ "元");

				customer_hold_product_percent3
						.setText((percentTotal != 0 ? df01.format(percent03
								* 100 / percentTotal) : "0.00")
								+ "%");
				customer_hold_product_money3.setText(df02.format(percent03)
						+ "元");

				customer_hold_product_percent4
						.setText((percentTotal != 0 ? df01.format(percent04
								* 100 / percentTotal) : "0.00")
								+ "%");
				customer_hold_product_money4.setText(df02.format(percent04)
						+ "元");
				customer_hold_product_percent5
						.setText((percentTotal != 0 ? df01.format(percent05
								* 100 / percentTotal) : "0.00")
								+ "%");
				customer_hold_product_money5.setText(df02.format(percent05)
						+ "元");

				customer_hold_product_percent6
						.setText((percentTotal != 0 ? df01.format(percent06
								* 100 / percentTotal) : "0.00")
								+ "%");
				customer_hold_product_money6.setText(df02.format(percent06)
						+ "元");

				customer_hold_product_percent7
						.setText((percentTotal != 0 ? df01.format(percent07
								* 100 / percentTotal) : "0.00")
								+ "%");
				customer_hold_product_money7.setText(df02.format(percent07)
						+ "元");

				customer_hold_product_percent8
						.setText((percentTotal != 0 ? df01.format(percent08
								* 100 / percentTotal) : "0.00")
								+ "%");
				customer_hold_product_money8.setText(df02.format(percent08)
						+ "元");
				customer_hold_product_percent9
						.setText((percentTotal != 0 ? df01.format(percent09
								* 100 / percentTotal) : "0.00")
								+ "%");
				customer_hold_product_money9.setText(df02.format(percent09)
						+ "元");
				customer_hold_product_percent10
						.setText((percentTotal != 0 ? df01.format(percent10
								* 100 / percentTotal) : "0.00")
								+ "%");
				customer_hold_product_money10.setText(df02.format(percent10)
						+ "元");

				customer_hold_product_percent11.setText("");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void readJson02(JSONObject tmpJsonObject) {
		//因为修改了CustomerPurchase.java这个类，所以，不得不将下面的这几句注释了
//		JSONArray tmpJsonArray = null;
//		if (tmpJsonObject.has("group")) {
//
//			try {
//				tmpJsonArray = tmpJsonObject.getJSONArray("group");
//				if (tmpJsonArray != null && tmpJsonArray.length() > 0) {
//					customerPurchases = new ArrayList<CustomerPurchase>();
//					CustomerPurchase tmpCustomerPurchase;
//					for (int i = 0; i < tmpJsonArray.length(); i++) {
//						tmpCustomerPurchase = new CustomerPurchase();
//						JSONObject tmpJsonObject02 = (JSONObject) tmpJsonArray
//								.get(i);
//						if (tmpJsonObject02.has("custID"))
//							tmpCustomerPurchase.custID = tmpJsonObject02
//									.getString("custID").trim();
//						if (tmpJsonObject02.has("prdNo"))
//							tmpCustomerPurchase.prdNo = tmpJsonObject02
//									.getString("prdNo").trim();
//						if (tmpJsonObject02.has("prdNam"))
//							tmpCustomerPurchase.prdName = tmpJsonObject02
//									.getString("prdNam").trim();
//						if (tmpJsonObject02.has("tranAmt"))
//							tmpCustomerPurchase.tranAmt = tmpJsonObject02
//									.getString("tranAmt").trim();
//						if (tmpJsonObject02.has("tranDat"))
//							tmpCustomerPurchase.tranDat = tmpJsonObject02
//									.getString("tranDat").trim();
//						if (tmpJsonObject02.has("clsDat"))
//							tmpCustomerPurchase.clsDat = tmpJsonObject02
//									.getString("clsDat").trim();
//						customerPurchases.add(tmpCustomerPurchase);
//						tmpCustomerPurchase = null;
//					}
//				}
//
//				cm_info_item02_listview.setAdapter(new LvPurchaseAdapter());
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//		}
	}

	private void readJson03(JSONObject tmpJsonObject) {
		JSONArray tmpJsonArray = null;
		if (tmpJsonObject.has("group")) {

			try {
				tmpJsonArray = tmpJsonObject.getJSONArray("group");
				if (tmpJsonArray != null && tmpJsonArray.length() > 0) {

					liCaiInfos = new ArrayList<LiCaiInfo>();
					LiCaiInfo tmpPCProductInfo;
					JSONObject tmpJsonObject02;
					for (int i = 0; i < tmpJsonArray.length(); i++) {
						tmpPCProductInfo = new LiCaiInfo();
						tmpJsonObject02 = (JSONObject) tmpJsonArray.get(i);
						if (tmpJsonObject02.has("prodId"))
							tmpPCProductInfo.prodID = tmpJsonObject02
									.getString("prodId");
						if (tmpJsonObject02.has("prodName"))
							tmpPCProductInfo.prodName = tmpJsonObject02
									.getString("prodName");
						if (tmpJsonObject02.has("saleDate"))
							tmpPCProductInfo.saleDate = tmpJsonObject02
									.getString("saleDate");
						if (tmpJsonObject02.has("riskType"))
							tmpPCProductInfo.riskType = tmpJsonObject02
									.getString("riskType");
						if (tmpJsonObject02.has("incomeRate"))
							tmpPCProductInfo.incomeRate = tmpJsonObject02
									.getString("incomeRate");
						liCaiInfos.add(tmpPCProductInfo);
					}
					tmpPCProductInfo = null;
					tmpJsonObject02 = null;
				}

				cm_info_item03_gridview
						.setAdapter(new PCProductInfoGridviewAdapter());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void readJson04(JSONObject tmpJsonObject) {
		JSONArray tmpJsonArray = null;
		if (tmpJsonObject.has("group")) {

			try {
				tmpJsonArray = tmpJsonObject.getJSONArray("group");
				if (tmpJsonArray != null && tmpJsonArray.length() > 0) {
					customerMarketingInfos = new ArrayList<CustomerMarketingInfo>();
					CustomerMarketingInfo tmpCustomerMarketingInfo;
					for (int i = 0; i < tmpJsonArray.length(); i++) {
						tmpCustomerMarketingInfo = new CustomerMarketingInfo();
						JSONObject tmpJsonObject02 = (JSONObject) tmpJsonArray
								.get(i);
						if (tmpJsonObject02.has("custID"))
							tmpCustomerMarketingInfo.custID = tmpJsonObject02
									.getString("custID").trim();
						if (tmpJsonObject02.has("prdtID"))
							tmpCustomerMarketingInfo.prdtID = tmpJsonObject02
									.getString("prdtID").trim();
						if (tmpJsonObject02.has("prdtNam"))
							tmpCustomerMarketingInfo.prdtNam = tmpJsonObject02
									.getString("prdtNam").trim();
						if (tmpJsonObject02.has("prdtRsn"))
							tmpCustomerMarketingInfo.prdtRsn = tmpJsonObject02
									.getString("prdtRsn").trim();
						if (tmpJsonObject02.has("APPMSG"))
							tmpCustomerMarketingInfo.appMSG = tmpJsonObject02
									.getString("APPMSG").trim();
						customerMarketingInfos.add(tmpCustomerMarketingInfo);
						tmpCustomerMarketingInfo = null;
					}
				}

				lv_marketing_clew.setAdapter(new LvMarketingAdapter());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void readJson05(JSONObject tmpJsonObject) {
		JSONArray tmpJsonArray = null;
		if (tmpJsonObject.has("group")) {

			try {
				tmpJsonArray = tmpJsonObject.getJSONArray("group");
				if (tmpJsonArray != null && tmpJsonArray.length() > 0) {
					JSONObject msgJSON = (JSONObject)tmpJsonArray.get(0);
					if(msgJSON.has("MsgRetCode")) {
						String msgStr = msgJSON.getString("MsgRetCode");
						if(msgStr!=null && msgStr.equals("0000")) {
							Toast.makeText(CustomerMarketingActivity.this, R.string.cm_send_msg_success, Toast.LENGTH_SHORT).show();
							comm_msg.setEnabled(false);
							return;
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		Toast.makeText(CustomerMarketingActivity.this, R.string.cm_send_msg_folder, Toast.LENGTH_SHORT).show();
		comm_msg.setEnabled(true);
	}

	private void setCustomerInforText(int flag) {
		if (customerMoreInfo.cnName != null) {

			tv_customer_name.setText(customerMoreInfo.cnName);
		}
		if (customerMoreInfo.age != null) {

			tv_cm_age.setText(customerMoreInfo.age);
		}

		if (customerMoreInfo.birthday != null) {

			tv_customer_birthday.setText(customerMoreInfo.birthday);
		}
		if (customerMoreInfo.mobileTel != null) {

			cm_mobiletel.setText(customerMoreInfo.mobileTel);
		}
		if (customerMoreInfo.hAddr != null) {

			cm_haddr.setText(customerMoreInfo.hAddr);
		}
		if (customerMoreInfo.newDate != null) {

			cm_newdate.setText(customerMoreInfo.newDate);
		}

		for (int i = 0; i < CodeTable.HF_CRANK_CUSTOMER_GENDEER.length; i++) {
			if (customerMoreInfo.sex != null
					&& customerMoreInfo.sex
							.equals(CodeTable.HF_CRANK_CUSTOMER_GENDEER[i])) {
				tv_cm_sex.setText(CodeTable.HF_CRANK_CUSTOMER_GENDEER_NAME[i]);
				switch (i) {
				case 0:
					cm_genderImage.setImageDrawable(getResources().getDrawable(
							R.drawable.fm_male));
					break;
				case 1:
					cm_genderImage.setImageDrawable(getResources().getDrawable(
							R.drawable.fm_female));
					break;
				case 2:
				case 3:
					cm_genderImage.setImageDrawable(getResources().getDrawable(
							R.drawable.fm_nore));
					break;
				default:
					break;
				}
			}
		}
		cm_genderImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				cm_genderImage.setVisibility(View.GONE);
				String photo_url = new HRequest(
						"pages/webview/custPhoto.jsp?certname="
								+ customerMoreInfo.cnName + "&certno="
								+ customerMoreInfo.didNo, "GET",
						HREQUEST_TYPE_HOSTPHOTO).getURL();
				Log.i("photo_url", photo_url);
				cm_genderPhoto.loadUrl(photo_url);
			}
		});

		/*
		 * staffname0, staffname1, cm_mphone0, cm_mphone1, cm_orgnamezh0,
		 * cm_orgnamezh1, cm_orgname0, cm_orgname1, cm_orgid0, cm_orgid1,
		 * cm_maporg0, cm_maporg1;
		 */
		if (flag == 1) {

			staffname1.setText(customerMoreInfo.STAFFNAME1);
			cm_mphone1.setText(customerMoreInfo.MPHONE1);
			cm_orgnamezh1.setText(customerMoreInfo.ORGNAMEzh1);
			cm_orgname1.setText(customerMoreInfo.ORGNAME1);

			staffname0.setText(cutomer_infor[0][0]);
			cm_mphone0.setText(cutomer_infor[0][1]);
			cm_orgnamezh0.setText(cutomer_infor[0][2]);
			cm_orgname0.setText(cutomer_infor[0][3]);
			if (!customerMoreInfo.MAPORG1.equals("")
					&& customerMoreInfo.MAPORG1 != null) {
				comm_msg.setVisibility(View.VISIBLE);
				if (customerMoreInfo.MAPORG1.equals(LogoActivity.user
						.getBrID2())) {
					comm_msg.setEnabled(true);

				} else {
					comm_msg.setEnabled(false);
				}
			} else {
				comm_msg.setVisibility(View.INVISIBLE);
			}

		} else if (flag == 2) {
			staffname1.setText(customerMoreInfo.STAFFNAME2);
			cm_mphone1.setText(customerMoreInfo.MPHONE2);
			cm_orgnamezh1.setText(customerMoreInfo.ORGNAMEzh2);
			cm_orgname1.setText(customerMoreInfo.ORGNAME2);

			staffname0.setText(cutomer_infor[1][0]);
			cm_mphone0.setText(cutomer_infor[1][1]);
			cm_orgnamezh0.setText(cutomer_infor[1][2]);
			cm_orgname0.setText(cutomer_infor[1][3]);

			if (!customerMoreInfo.MAPORG2.equals("")
					&& customerMoreInfo.MAPORG2 != null) {

				comm_msg.setVisibility(View.VISIBLE);
				if (customerMoreInfo.MAPORG2.equals(LogoActivity.user
						.getBrID2())) {
					comm_msg.setEnabled(true);

				} else {
					comm_msg.setEnabled(false);
				}
			} else {
				comm_msg.setVisibility(View.INVISIBLE);
			}

		}

	}

	private PieView shou3DBing(int[] colors, int[] shade_colors, float[] percent) {
		return new PieView(this, colors, shade_colors, percent);
	}

	// 显示dialog最有从下到上的移动的动画效果
	public void showLvMarketingDialog(int titleId, int contentId) {
		final DefiDialog dialog = new DefiDialog(
				CustomerMarketingActivity.this.getParent(),
				R.style.definitionDialog);
		View view = View.inflate(CustomerMarketingActivity.this.getParent(),
				R.layout.marketing_clew_surgery_dialog, null);
		Window win = dialog.getWindow();
		dialog.setCanceledOnTouchOutside(true); // 设置触摸对话框意外的地方取消对话框
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		win.setBackgroundDrawableResource(R.drawable.icon_cust_skdetail);

		TextView tvPdcName = (TextView) view.findViewById(R.id.tv_product_name);
		TextView tvMessage1 = (TextView) view.findViewById(R.id.tv_message1);

		tvPdcName.setText(titleId);
		tvMessage1.setText(contentId);

		view.findViewById(R.id.imbt_id).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (dialog.isShowing())
							dialog.dismiss();
					}
				});
		Animation ai = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
				0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		ai.setDuration(1000);
		view.startAnimation(ai);
		dialog.setContentView(view);
		dialog.show();
	}

	public class MarketingDialog1 extends Dialog implements
			View.OnClickListener {
		private TextView tvPdcName;
		private TextView tvMessage1;
		private Button imbt_id;
		private int tvPdcNameId, tvMessage1Id;

		public MarketingDialog1(Context context, int tvPdcNameId,
				int tvMessage1Id) {
			super(context);
			this.tvPdcNameId = tvPdcNameId;
			this.tvMessage1Id = tvMessage1Id;

		}

		public MarketingDialog1(Context context) {
			super(context);

		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setCanceledOnTouchOutside(true); // 设置触摸对话框意外的地方取消对话框
			// 用于为该Dialog设置布局文件
			setContentView(R.layout.marketing_clew_surgery_dialog);
			this.getWindow().setBackgroundDrawableResource(
					R.drawable.icon_cust_skdetail);

			findViews();

			setListeners();

			tvPdcName.setText(tvPdcNameId);
			tvMessage1.setText(tvMessage1Id);

		}

		private void setListeners() {
			imbt_id.setOnClickListener(this);
		}

		private void findViews() {
			tvPdcName = (TextView) findViewById(R.id.tv_product_name);
			tvMessage1 = (TextView) findViewById(R.id.tv_message1);
			imbt_id = (Button) findViewById(R.id.imbt_id);
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.imbt_id:
				if (isShowing()) {

					dismiss();
				}

				break;

			default:
				break;
			}
		}
	}

	private class LvPurchaseAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (customerPurchases == null)
				return 0;
			else
				return customerPurchases.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = View.inflate(CustomerMarketingActivity.this,
						R.layout.cm_info_item02_listview_item, null);
			}

			TextView cm_info_item02_lv_prdName = (TextView) convertView
					.findViewById(R.id.cm_info_item02_lv_prdName);
			//因为修改了CustomerPurchase.java这个类，所以，不得不将下面的这几句注释了
//			cm_info_item02_lv_prdName
//					.setText(customerPurchases.get(position).prdName);
//			TextView cm_info_item02_lv_clsDat = (TextView) convertView
//					.findViewById(R.id.cm_info_item02_lv_clsDat);
//			cm_info_item02_lv_clsDat
//					.setText(customerPurchases.get(position).clsDat);
//			TextView cm_info_item02_lv_tranAmt = (TextView) convertView
//					.findViewById(R.id.cm_info_item02_lv_tranAmt);
//			cm_info_item02_lv_tranAmt
//					.setText(customerPurchases.get(position).tranAmt);
			if (position % 2 != 0) {
				convertView.setBackgroundResource(R.color.et_backgroud_gray);
			} else {
				convertView.setBackgroundResource(R.color.white);
			}
			return convertView;

		}
	}

	private class PCProductInfoGridviewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (liCaiInfos != null)
				return liCaiInfos.size();
			else
				return 0;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(CustomerMarketingActivity.this,
						R.layout.product_centre_gv_item, null);
			}
			TextView pc_licai_prodname = (TextView) convertView
					.findViewById(R.id.pc_licai_prodname);
			pc_licai_prodname.setText(liCaiInfos.get(position).prodName);

			TextView pc_licai_saledate = (TextView) convertView
					.findViewById(R.id.pc_licai_saledate);
			pc_licai_saledate.setText("上架时间："
					+ liCaiInfos.get(position).saleDate);

			// String tmpRiskType = liCaiInfos.get(position).riskType;
			// String tmpRiskType01 = "";
			// if (tmpRiskType != null && tmpRiskType.equals("01")) {
			// tmpRiskType01 = "激进型";
			// } else if (tmpRiskType != null && tmpRiskType.equals("02")) {
			// tmpRiskType01 = "平衡型";
			// } else if (tmpRiskType != null && tmpRiskType.equals("03")) {
			// tmpRiskType01 = "保守型";
			// }
			TextView pc_licai_risktype = (TextView) convertView
					.findViewById(R.id.pc_licai_risktype);
			pc_licai_risktype.setText("风险等级："
					+ liCaiInfos.get(position).riskType);
			// tmpRiskType = null;
			// tmpRiskType01 = null;
			TextView pc_licai_incomerate = (TextView) convertView
					.findViewById(R.id.pc_licai_incomerate);
			String incomeRate = liCaiInfos.get(position).incomeRate;
			if (incomeRate == null)
				incomeRate = "";
			pc_licai_incomerate.setText("收益率：    " + incomeRate);
			final String prodID = liCaiInfos.get(position).prodID;
			// final int tmpPositon = position;
			convertView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!IS_OUTlINE
							&& Tool.haveNet(CustomerMarketingActivity.this)
							&& prodID != null && !"".equals(prodID)
							&& !"null".equalsIgnoreCase(prodID)) {
//						Intent intent = new Intent(
//								CustomerMarketingActivity.this,
//								PCLiCaiChanPinShowInforActivity.class);
						intent.putExtra("prodID", prodID);
						startActivity(intent);
					}
				}
			});
			return convertView;
		}
	}

	private class LvMarketingAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// return lv_marketing_clew_title.length;
			if (customerMarketingInfos == null)
				return 0;
			else
				return customerMarketingInfos.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = View.inflate(CustomerMarketingActivity.this,
						R.layout.customer_marketing_info_listview_item, null);
			}

			TextView cm_listview_item1 = (TextView) convertView
					.findViewById(R.id.customer_marketing_info_listview_item01);
			cm_listview_item1.setText((position + 1) + "");
			TextView cm_listview_item2 = (TextView) convertView
					.findViewById(R.id.customer_marketing_info_listview_item02);
			cm_listview_item2
					.setText(customerMarketingInfos.get(position).prdtNam);
			TextView cm_listview_item3 = (TextView) convertView
					.findViewById(R.id.customer_marketing_info_listview_item03);
			cm_listview_item3
					.setText(customerMarketingInfos.get(position).appMSG);
			TextView cm_listview_item4 = (TextView) convertView
					.findViewById(R.id.customer_marketing_info_listview_item04);
			cm_listview_item4
					.setText(customerMarketingInfos.get(position).prdtRsn);
			if (position % 2 != 0) {
				convertView.setBackgroundResource(R.color.et_backgroud_gray);
			} else {
				convertView.setBackgroundResource(R.color.white);
			}
			// final int finalPos = position;
			// convertView.setOnClickListener(new View.OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// /*
			// * showDialog = new MarketingDialog(
			// * CustomerMarketingActivity.this); showDialog.show();
			// */
			//
			// showDialog = new MarketingDialog(
			// CustomerMarketingActivity.this.getParent(),
			// lv_marketing_clew_title[finalPos],
			// lv_marketing_clew_content[finalPos]);
			//
			// Window window = showDialog.getWindow();
			// window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
			// window.setWindowAnimations(R.style.mystyle); // 添加动画
			//
			// showDialog.show();
			//
			// /*
			// * showLvMarketingDialog(lv_marketing_clew_title[finalPos],
			// * lv_marketing_clew_content[finalPos]);
			// */
			//
			// }
			// });
			return convertView;

		}
	}

	private class HoldServerAdapter extends BaseExpandableListAdapter {

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return null;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return 0;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(CustomerMarketingActivity.this,
						R.layout.hold_server_listview_item, null);
			}
			TextView tv_customer_services = (TextView) convertView
					.findViewById(R.id.tv_customer_services);
			TextView tv_customer_services_have = (TextView) convertView
					.findViewById(R.id.tv_customer_services_have);
			String tmpCardType = "";
			for (int i = 0; i < CodeTable.HF_CUSTOMERMORE_CARDTYPE.length; i++) {
				if (customerMoreInfo.cardType != null
						&& customerMoreInfo.cardType
								.equals(CodeTable.HF_CUSTOMERMORE_CARDTYPE[i])) {
					tmpCardType = CodeTable.HF_CUSTOMERMORE_CARDTYPE_NAME[i];
					break;
				}
			}
			String tmpRank = "";
			for (int i = 0; i < CodeTable.HF_CUSTOMERMORE_RANK.length; i++) {
				if (customerMoreInfo.rank != null
						&& customerMoreInfo.rank
								.equals(CodeTable.HF_CUSTOMERMORE_RANK[i])) {
					tmpRank = CodeTable.HF_CUSTOMERMORE_RANK_NAME[i];
					break;
				}
			}
			String[][] tv_customer_services_child2 = {
					{ /* customerMoreInfo.rsn, */tmpCardType,
					/* customerMoreInfo.cardID, */tmpRank },
					{ customerMoreInfo.maxDpstTm, customerMoreInfo.maxLoanTm,
							customerMoreInfo.faYavg, customerMoreInfo.faMavg,
							customerMoreInfo.faChgSenDay,
							customerMoreInfo.faChgMonAul,
							customerMoreInfo.curdpst },
					{ customerMoreInfo.credit, customerMoreInfo.online,
							customerMoreInfo.mobiel, customerMoreInfo.phone,
							customerMoreInfo.sms, customerMoreInfo.trsf } };
			int maxDpstTmColor = R.color.black;
			int maxLoanTmColor = R.color.black;
			int faYavgColor = R.color.black;
			int faMavgColor = R.color.black;
			int faChgSenDayColor = R.color.black;
			int faChgMonAulColor = R.color.black;
			int curdpstColor = R.color.black;
			if (customerMoreInfo.maxDpstTm != null
					&& !"".equals(customerMoreInfo.maxDpstTm)
					&& Double.parseDouble(customerMoreInfo.maxDpstTm) < 0) {
				maxDpstTmColor = R.color.green;
			}
			if (customerMoreInfo.maxLoanTm != null
					&& !"".equals(customerMoreInfo.maxLoanTm)
					&& Double.parseDouble(customerMoreInfo.maxLoanTm) < 0) {
				maxLoanTmColor = R.color.green;
			}
			if (customerMoreInfo.faYavg != null
					&& !"".equals(customerMoreInfo.faYavg)
					&& Double.parseDouble(customerMoreInfo.faYavg) < 0) {
				faYavgColor = R.color.green;
			}
			if (customerMoreInfo.faMavg != null
					&& !"".equals(customerMoreInfo.faMavg)
					&& Double.parseDouble(customerMoreInfo.faMavg) < 0) {
				faMavgColor = R.color.green;
			}
			if (customerMoreInfo.faChgSenDay != null
					&& !"".equals(customerMoreInfo.faChgSenDay)
					&& Double.parseDouble(customerMoreInfo.faChgSenDay) < 0) {
				faChgSenDayColor = R.color.green;
			}
			if (customerMoreInfo.faChgMonAul != null
					&& !"".equals(customerMoreInfo.faChgMonAul)
					&& Double.parseDouble(customerMoreInfo.faChgMonAul) < 0) {
				faChgMonAulColor = R.color.green;
			}
			if (customerMoreInfo.curdpst != null
					&& !"".equals(customerMoreInfo.curdpst)
					&& Double.parseDouble(customerMoreInfo.curdpst) < 0) {
				curdpstColor = R.color.green;
			}
			int[][] tv_customer_services_color = {
					{ R.color.black, R.color.black },
					{ maxDpstTmColor, maxLoanTmColor, faYavgColor, faMavgColor,
							faChgSenDayColor, faChgMonAulColor, curdpstColor },
					{ R.color.black, R.color.black, R.color.black,
							R.color.black, R.color.black, R.color.black } };

			tv_customer_services
					.setText(tv_customer_services_child1[groupPosition][childPosition]);
			tv_customer_services.setTextColor(getResources().getColor(
					tv_customer_services_color[groupPosition][childPosition]));

			/*
			 * tv_customer_services_have .setText(df02.format(Double
			 * .parseDouble
			 * (tv_customer_services_child2[groupPosition][childPosition])));
			 */

			if (groupPosition == 1) {
				tv_customer_services_have
						.setText(df02.format(Double
								.parseDouble(tv_customer_services_child2[groupPosition][childPosition])));
			} else {

				tv_customer_services_have
						.setText(tv_customer_services_child2[groupPosition][childPosition]);
			}

			tv_customer_services_have.setTextColor(getResources().getColor(
					tv_customer_services_color[groupPosition][childPosition]));
			tv_customer_services_have
					.setGravity(tv_customer_services_gravity[groupPosition][childPosition]);
			LinearLayout tv_customer_services_childview = (LinearLayout) convertView
					.findViewById(R.id.ll_hold_service);
			if (childPosition % 2 != 0) {
				tv_customer_services_childview
						.setBackgroundResource(R.color.et_backgroud_gray);
			} else {
				tv_customer_services_childview
						.setBackgroundResource(R.color.white);
			}
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			if (customerMoreInfo == null)
				return 0;
			else
				return tv_customer_services_child1[groupPosition].length;
		}

		@Override
		public Object getGroup(int groupPosition) {
			return null;
		}

		@Override
		public int getGroupCount() {
			if (customerMoreInfo == null)
				return 0;
			else
				return tv_customer_services_group.length;
		}

		@Override
		public long getGroupId(int groupPosition) {
			return 0;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(CustomerMarketingActivity.this,
						R.layout.hold_server_listview_item_group, null);
			}
			TextView tv_customer_services_group_name = (TextView) convertView
					.findViewById(R.id.hold_server_group_name);
			ImageView tv_customer_services_group_arrow = (ImageView) convertView
					.findViewById(R.id.hold_server_group_arrow);
			tv_customer_services_group_arrow
					.setImageResource(R.drawable.hold_server_group_arrow02);
			// 更换展开分组图片
			if (!isExpanded) {
				tv_customer_services_group_arrow
						.setImageResource(R.drawable.hold_server_group_arrow01);
			}

			tv_customer_services_group_name
					.setText(tv_customer_services_group[groupPosition]);
			// if (groupPosition % 2 != 0) {
			// convertView
			// .setBackgroundResource(R.color.whitesmoke);
			// } else {
			// convertView.setBackgroundResource(R.color.et_backgroud_gray);
			// }
			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return false;
		}
	}
}