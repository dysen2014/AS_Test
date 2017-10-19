package com.pactera.financialmanager.ui.hallfirst;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.db.CateDao;
import com.pactera.financialmanager.entity.CustomerBasicInfo;
import com.pactera.financialmanager.entity.Matchs;
import com.pactera.financialmanager.entity.PercentInfo;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.PieView;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HRequest;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.SortCompare;

//finish处清空
public class CMMarketingActivity extends ParentActivity implements
		OnClickListener {
	// 三个切换viewFlipper页面的 按钮
	private Button bt_cm_khqy;
	private ViewFlipper cm_flipper;
	private Button bt_cm_test;// 风险测评
//	private StringBuilder url_ext_customerMoreInfo,url_ext_customerMoreInfo3;
	private LinearLayout iv_back_fromcmMarketing;// 左上角返回键
	private ListView lv_hold_server_option;
	private LinearLayout customer_marketing_product_unit_img;// 资产比例饼状图
	private ImageView iv_cm_pic;// 头像
	private int windowWidth;
	private boolean isShowingDialog = false;
	// 客户具体信息部分
	private TextView tv_cm_name, tv_cm_age, tv_cm_preferslc, tv_cm_preferscrm,
			tv_cm_rank, tv_cm_manager, tv_cm_managertel, tv_cm_branch,
			tv_cm_interest;
	// 饼图下方
	private ImageView iv_holdserver_1lc, iv_holdserver_2hqck,
			iv_holdserver_3dqck;
	private TextView tv_holdserver_1lc, customer_hold_product_money1,
			customer_hold_product_percent1, tv_holdserver_2hqck,
			customer_hold_product_money2, customer_hold_product_percent2,
			tv_holdserver_3dqck, customer_hold_product_money3,
			customer_hold_product_percent3;
	private CustomerBasicInfo customerBasicInfo;
	private DecimalFormat df01 = new DecimalFormat("#0.00");
	private DecimalFormat df02 = new DecimalFormat("#,##0.00");

	private ArrayList<PercentInfo> percentInfos;
	private double[] primary;// 存储360视图中三种资产类型的资金数目的数组
	private Double[] sort;// 存储360视图中三种资产类型的资金数目的数组

	private double money_hqck;// 活期存款金额

	private HashMap<String, String> sexs, ranks, prefers, interests;
	private int lastIndex = -1,// ll1中最后一child的位置
			count = 10,// 每次加载的数量
			sum = 0;// 加载后的child数量
	private int lastIndex2 = -1,// ll1中最后一child的位置
			count2 = 10,// 每次加载的数量
			sum2 = 0;// 加载后的child数量
			// boolean isListenning = false;//标志 是否有一个检测位置的定期执行器在执行
			// int position1,position2,position3;
	private int miniItemCount = 15,// ListView显示的最小条目数（只要保证能满屏显示即可）
			originItemCount = 0;// ListView 原始条目数。
	private boolean isTjcpDownload = false;
	
	private String custID,queueNum;//带过来的客户id,排队号

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sch_cm);
		System.gc();// 及时清理其他页面残留占用的内存
		findDisplay();
		Intent intent = getIntent();
		custID = intent.getStringExtra("custID");
		queueNum = intent.getStringExtra("queueNum");
		initView();
		bindListener();
		initButton2(bt_cm_khqy);
		initCate();

		// 客户基本信息
		getCustomerMoreData1();// 获取第一批数据1
		// 资产结构
//		getCustomerMoreData3();// 获取第一批数据
		// LogoActivity.activityList2.add(this);
	}

	private void findDisplay() {
		windowWidth = this.getWindowManager().getDefaultDisplay().getWidth();
	}

	private void initCate() {
		CateDao dao = new CateDao(CMMarketingActivity.this);
		sexs = dao.getMapData("性别");
		ranks = dao.getMapData("客户评级");
		prefers = dao.getMapData("客户风险偏好");
		interests = dao.getMapDataBaseName("interest");
		// Log.i("interests.size()", interests.size()+"");
	}

	private void initButton2(Button v) {
		bt_cm_khqy.setEnabled(true);
		bt_cm_khqy.setTextColor(getResources().getColor(R.color.black));
		v.setEnabled(false);
		v.setTextColor(getResources().getColor(R.color.white));
	}

	private void bindListener() {
		// ViewFlipper上面三个按钮
		bt_cm_khqy.setOnClickListener(this);
		// 测评和返回按钮
		bt_cm_test.setOnClickListener(this);
		iv_back_fromcmMarketing.setOnClickListener(this);
	}

	private void initView() {
		percentInfos = new ArrayList<PercentInfo>(3);// 低于10
													 // 不用指定容量，因为初始建立默认10容量。
		// viewFlipper上面的三个标签按钮
		bt_cm_khqy = (Button) findViewById(R.id.bt_cm_khqy);// 客户签约

		bt_cm_khqy.setEnabled(false);
		cm_flipper = (ViewFlipper) findViewById(R.id.cm_flipper);
		cm_flipper.setDisplayedChild(0);
		// 左上角返回键
		iv_back_fromcmMarketing = (LinearLayout) findViewById(R.id.iv_back_fromcmMarketing);
		// 右半部分客户信息详情
		iv_cm_pic = (ImageView) findViewById(R.id.iv_cm_pic);
		tv_cm_name = (TextView) findViewById(R.id.tv_cm_name);
		tv_cm_age = (TextView) findViewById(R.id.tv_cm_age);
		tv_cm_preferslc = (TextView) findViewById(R.id.tv_cm_preferslc);
		tv_cm_preferscrm = (TextView) findViewById(R.id.tv_cm_preferscrm);
		bt_cm_test = (Button) findViewById(R.id.bt_cm_test);
		tv_cm_rank = (TextView) findViewById(R.id.tv_cm_rank);
		tv_cm_manager = (TextView) findViewById(R.id.tv_cm_manager);
		tv_cm_managertel = (TextView) findViewById(R.id.tv_cm_managertel);
		tv_cm_branch = (TextView) findViewById(R.id.tv_cm_branch);
		tv_cm_interest = (TextView) findViewById(R.id.tv_cm_interest);
		// 第一个界面
		lv_hold_server_option = (ListView) findViewById(R.id.lv_hold_server_option);
		customer_marketing_product_unit_img = (LinearLayout) findViewById(R.id.customer_marketing_product_unit_img);
		// 饼图下方
		iv_holdserver_1lc = (ImageView) findViewById(R.id.iv_holdserver_1lc);
		tv_holdserver_1lc = (TextView) findViewById(R.id.tv_holdserver_1lc);
		customer_hold_product_money1 = (TextView) findViewById(R.id.sch_customer_hold_product_money1);
		customer_hold_product_percent1 = (TextView) findViewById(R.id.sch_customer_hold_product_percent1);
		iv_holdserver_2hqck = (ImageView) findViewById(R.id.iv_holdserver_2hqck);
		tv_holdserver_2hqck = (TextView) findViewById(R.id.tv_holdserver_2hqck);
		customer_hold_product_money2 = (TextView) findViewById(R.id.sch_customer_hold_product_money2);
		customer_hold_product_percent2 = (TextView) findViewById(R.id.sch_customer_hold_product_percent2);
		iv_holdserver_3dqck = (ImageView) findViewById(R.id.iv_holdserver_3dqck);
		tv_holdserver_3dqck = (TextView) findViewById(R.id.tv_holdserver_3dqck);
		customer_hold_product_money3 = (TextView) findViewById(R.id.sch_customer_hold_product_money3);
		customer_hold_product_percent3 = (TextView) findViewById(R.id.sch_customer_hold_product_percent3);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 三个viewFlipper选择按钮
		case R.id.bt_cm_khqy:
			initButton2(bt_cm_khqy);
			cm_flipper.setDisplayedChild(0);
			break;
		case R.id.bt_cm_test:// 风险偏好（CRM）
			// //访问网络或者打开新的界面 待定
			// intent = new Intent(CMMarketingActivity.this,
			// FinancialRiskAppraisal.class);
			// intent.putExtra("custID",custID);
			// intent.putExtra("fromCMM", "true");
			// startActivity(intent);
			// // LogoActivity.activityList2.clear();
			// finish();
			break;
		case R.id.iv_back_fromcmMarketing:
			// 返回或者结束
			// intent = new Intent(CMMarketingActivity.this,
			// HallFirst1Activity.class);
			// // intent.putExtra("", "");
			// startActivity(intent);
			// // LogoActivity.activityList2.clear();
			// finish();
			break;
		default:
			break;
		}
	}
	
	// 第一次获取数据 客户详细信息
	private HConnection customerMoreCon1;
	private final int customerInfoFlag = 1;

	private void getCustomerMoreData1() {
		HRequest request = new HRequest("T000006?method=getJSON&userCode="
				+ LogoActivity.user.getUserCode()
				+ "&brCode="
				+ LogoActivity.user.getBrCode()
				+ "&seriNo="
				+ "DLXJT06FF18P"
				+ "&chnlCode=02&transCode="
				+ "T000006"
				+ "&custID="
				+ custID,
				"GET");
		try {
			HConnection.isShowLoadingProcess = true;
			customerMoreCon1 = new HConnection(this, request,
					LogoActivity.user, HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			customerMoreCon1.setIndex(customerInfoFlag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 资产结构   即饼状图的数据来源
	 */
	
	private HConnection customerMoreCon3;
	private final int customerMoneyStructure = 2;

	private void getCustomerMoreData3() {
//		HRequest request = new HRequest(url_ext_customerMoreInfo3.toString(),
//				"GET");
//		try {
//			HConnection.isShowLoadingProcess = true;
//			customerMoreCon3 = new HConnection(this, request,
//					LogoActivity.user, HConnection.RESPONSE_TYPE_COMPLEX_JSON);
//			customerMoreCon3.setIndex(customerMoneyStructure);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	@Override
	public void onConnected(Message msg) {

		super.onConnected(msg);
		showDialog(DIALOG_ID);
		isShowingDialog = true;
		switch (connectionIndex) {

		case HConnection.RESPONSE_ERROR:
			// Log.i("Error", "取消进度框");
			if (isShowingDialog) {
				dismissDialog(DIALOG_ID);
				isShowingDialog = false;
			}
		case customerInfoFlag:
			HResponse res = customerMoreCon1
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				if (isShowingDialog) {
					dismissDialog(DIALOG_ID);
					isShowingDialog = false;
				}
				return;
			}
			JSONObject tmpJsonObject = res.dataJsonObject;
			String jsonString = tmpJsonObject.toString();
			readJson01(tmpJsonObject);
			break;
		case customerMoneyStructure:
			HResponse res3 = customerMoreCon3
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res3 == null || res3.dataJsonObject == null) {
				return;
			}
			JSONObject tmpJsonObject3 = res3.dataJsonObject;
			readJson03(tmpJsonObject3);
			break;
		}
	}

	private void readJson01(JSONObject tmpJsonObject) {
		//对返回的客户数据信息进行解析
		
//		JSONArray tmpJsonArray = null;
//		if (tmpJsonObject.has("data")) {
//			try {
//				tmpJsonArray = tmpJsonObject.getJSONArray("data");
//				if (tmpJsonArray != null && tmpJsonArray.length() > 0) {
//					customerBasicInfo = new CustomerBasicInfo();
//					JSONObject tmpJsonObject02 = (JSONObject) tmpJsonArray
//							.get(0);
//					if (tmpJsonObject02.has("sex"))
//						customerBasicInfo.sex = tmpJsonObject02
//								.getString("sex").trim();
//					if (tmpJsonObject02.has("custName"))
//						customerBasicInfo.name = tmpJsonObject02.getString(
//								"custName").trim();
//					if (tmpJsonObject02.has("age"))
//						customerBasicInfo.age = tmpJsonObject02
//								.getString("age").trim();
//					if (tmpJsonObject02.has("fininvStyle"))
//						customerBasicInfo.preferslc = tmpJsonObject02
//								.getString("fininvStyle").trim();
//					if (tmpJsonObject02.has("invstyle"))
//						customerBasicInfo.preferscrm = tmpJsonObject02
//								.getString("invstyle").trim();
//					if (tmpJsonObject02.has("custClass"))
//						customerBasicInfo.rank = tmpJsonObject02.getString(
//								"custClass").trim();
//					if (tmpJsonObject02.has("userName"))
//						customerBasicInfo.manager = tmpJsonObject02.getString(
//								"userName").trim();
//					if (tmpJsonObject02.has("userMobilePhone"))
//						customerBasicInfo.managertel = tmpJsonObject02
//								.getString("userMobilePhone").trim();
//					if (tmpJsonObject02.has("interest"))
//						customerBasicInfo.interest = tmpJsonObject02.getString(
//								"interest").trim();
//					if (tmpJsonObject02.has("brName"))
//						customerBasicInfo.branch = tmpJsonObject02.getString(
//								"brName").trim();
//				}
//				setCustomerInforText1();
//			} catch (JSONException e) {
//				e.printStackTrace();
//				if (isShowingDialog) {
//					dismissDialog(DIALOG_ID);
//					isShowingDialog = false;
//				}
//			}
//		}
//		if (isShowingDialog) {
//			dismissDialog(DIALOG_ID);
//			isShowingDialog = false;
//		}
	}

	private List<Matchs> qianyue = new ArrayList<Matchs>();

	// 获取饼图比例数据
	private Map<String, String> percentInfo = new HashMap<String, String>();

	private void readJson03(JSONObject tmpJsonObject) {
		JSONArray tmpJsonArray = null;
		if (tmpJsonObject.has("data")) {
			try {
				tmpJsonArray = tmpJsonObject.getJSONArray("data");
				if (tmpJsonArray != null && tmpJsonArray.length() > 0) {
					JSONObject tmpJsonObject02 = (JSONObject) tmpJsonArray
							.get(0);
					if (tmpJsonObject02.has("deptbal")) {
						percentInfo.put("deptbal",
								tmpJsonObject02.getString("deptbal"));
					}
					if (tmpJsonObject02.has("finabal")) {
						percentInfo.put("finabal",
								tmpJsonObject02.getString("finabal"));
					}
					if (tmpJsonObject02.has("curbal")) {
						percentInfo.put("curbal",
								tmpJsonObject02.getString("curbal"));
					}
				}
				double percent01 = 0.00d;
				double percent02 = 0.00d;
				double percent03 = 0.00d;
				double percentTotal;
				float[] percent;

				// 第1个PercentInfo
				String finabal = percentInfo.get("finabal");// 理财
				if (finabal != null && !"".equals(finabal)
						&& !"null".equalsIgnoreCase(finabal)) {
					percent01 = Double.parseDouble(finabal);
				}

				// 第2个PercentInfo
				String curbal = percentInfo.get("curbal");// 活期存款
				if (curbal != null && !"".equals(curbal)
						&& !"null".equalsIgnoreCase(curbal)) {
					money_hqck = percent02 = Double.parseDouble(curbal);
				}

				// 第3个PercentInfo
				String deptbal = percentInfo.get("deptbal");// 定期存款
				if (deptbal != null && !"".equals(deptbal)
						&& !"null".equalsIgnoreCase(deptbal)) {
					percent03 = Double.parseDouble(deptbal);
				}

				primary = new double[] { percent01, percent02, percent03 };// 原始的参照顺序
				sort = new Double[] { percent01, percent02, percent03 };// 用于比较排序
																		// 比较后会从大到小排序

				percentTotal = percent01 + percent02 + percent03;
				// =====下面========装载PercentInfo===================
				// 装载第1个PercentInfo
				PercentInfo p1 = new PercentInfo();
				p1.IV_BACKGROUDCOLOR = R.color.hf_cm_piecolor01;
				p1.TV_TYPE = "理财";
				p1.TV_MONEY = percent01 + "";
				p1.TV_PERCENT = (percentTotal != 0 ? df01.format(percent01
						* 100 / percentTotal) : "0.00")
						+ "%";
				percentInfos.add(p1);
				p1 = null;
				// 装载第2个PercentInfo
				PercentInfo p2 = new PercentInfo();
				p2.IV_BACKGROUDCOLOR = R.color.hf_cm_piecolor02;
				p2.TV_TYPE = "活期存款";
				p2.TV_MONEY = percent02 + "";
				p2.TV_PERCENT = (percentTotal != 0 ? df01.format(percent02
						* 100 / percentTotal) : "0.00")
						+ "%";
				percentInfos.add(p2);
				p2 = null;
				// 装载第1个PercentInfo
				PercentInfo p3 = new PercentInfo();
				p3.IV_BACKGROUDCOLOR = R.color.hf_cm_piecolor03;
				p3.TV_TYPE = "定期存款";
				p3.TV_MONEY = percent03 + "";
				p3.TV_PERCENT = (percentTotal != 0 ? df01.format(percent03
						* 100 / percentTotal) : "0.00")
						+ "%";
				percentInfos.add(p3);
				p3 = null;
				for (int i = 0; i < percentInfos.size(); i++) {
					PercentInfo p = percentInfos.get(i);
				}
				// ======下面========显示资产比例360视图====================

				float tmpPercent01 = 0f;
				float tmpPercent02 = 0f;
				float tmpPercent03 = 0f;
				float tmpPercent11 = 360f;
				if (percentTotal != 0) {
					tmpPercent01 = (float) (percent01 * 360 / percentTotal);
					tmpPercent02 = (float) (percent02 * 360 / percentTotal);
					tmpPercent03 = (float) (percent03 * 360 / percentTotal);
					tmpPercent11 = 0f;
				}
				percent = new float[] { tmpPercent01, tmpPercent02,
						tmpPercent03, tmpPercent11 };
				int[] colors = {
						getResources().getColor(R.color.hf_cm_piecolor01),//
						getResources().getColor(R.color.hf_cm_piecolor02),
						getResources().getColor(R.color.hf_cm_piecolor03),//
						getResources().getColor(R.color.hf_cm_piecolor11) };
				int[] shade_colors = {
						getResources().getColor(R.color.hf_cm_piecolor_dark01),//
						getResources().getColor(R.color.hf_cm_piecolor_dark02),
						getResources().getColor(R.color.hf_cm_piecolor_dark03),//
						getResources().getColor(R.color.hf_cm_piecolor_dark11) };
				customer_marketing_product_unit_img.addView(shou3DBing(colors,
						shade_colors, percent)); // 添加饼状图
				// =======下面=================设置3中资产的由大至小顺序显示=======================================
				// 排序-----其他主类的代码中还有对bean更简单的排序方法，冒泡排序法，为了省事就没将此处再做修改。参见：AdiviceActivity/ProductCenter1Activity
				sort(sort);
				for (int i = 0; i < 3; i++) {
					PercentInfo p = compare(primary, sort[i]);// 自动拆箱 jdk1.5新特性
																// 若jdk版本低于1.5
																// 则用上面方法
					if (i == 0) {
						iv_holdserver_1lc
								.setBackgroundResource(p.IV_BACKGROUDCOLOR);
						tv_holdserver_1lc.setText(p.TV_TYPE);
						// customer_hold_product_money1.setText(p.TV_MONEY);
						customer_hold_product_percent1.setText(p.TV_PERCENT);

					} else if (i == 1) {
						iv_holdserver_2hqck
								.setBackgroundResource(p.IV_BACKGROUDCOLOR);
						tv_holdserver_2hqck.setText(p.TV_TYPE);
						// customer_hold_product_money2.setText(p.TV_MONEY);
						customer_hold_product_percent2.setText(p.TV_PERCENT);
					} else if (i == 2) {
						iv_holdserver_3dqck
								.setBackgroundResource(p.IV_BACKGROUDCOLOR);
						tv_holdserver_3dqck.setText(p.TV_TYPE);
						// customer_hold_product_money3.setText(p.TV_MONEY);
						customer_hold_product_percent3.setText(p.TV_PERCENT);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				if (isShowingDialog) {
					dismissDialog(DIALOG_ID);
					isShowingDialog = false;
				}
			}
		}
		if (isShowingDialog) {
			dismissDialog(DIALOG_ID);
			isShowingDialog = false;
		}
	}
	
	private void setCustomerInforText1() {

		if (customerBasicInfo == null)
			return;
		if (customerBasicInfo.name != null) {

			tv_cm_name.setText(customerBasicInfo.name);
		}
		if (customerBasicInfo.age != null) {

			tv_cm_age.setText(customerBasicInfo.age);
		}
		if (customerBasicInfo.preferslc != null) {

			tv_cm_preferslc.setText(prefers.get(customerBasicInfo.preferslc));
		}
		if (customerBasicInfo.preferscrm != null) {

			tv_cm_preferscrm.setText(prefers.get(customerBasicInfo.preferscrm));
		}
		if (customerBasicInfo.rank != null) {

			tv_cm_rank.setText(ranks.get(customerBasicInfo.rank));
		}
		if (customerBasicInfo.manager != null) {

			tv_cm_manager.setText(customerBasicInfo.manager);
		}
		if (customerBasicInfo.managertel != null) {

			tv_cm_managertel.setText(customerBasicInfo.managertel);
		}
		if (customerBasicInfo.branch != null) {

			tv_cm_branch.setText(customerBasicInfo.branch);
		}
		if (customerBasicInfo.interest != null
				&& !"".equals(customerBasicInfo.interest)) {
			String interest = customerBasicInfo.interest;
			String[] ins = interest.split(",");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < ins.length; i++) {
				sb.append(interests.get(ins[i]) + "、");
			}
			sb.deleteCharAt(sb.length() - 1);
			tv_cm_interest.setText(sb.toString());
		}
		if (customerBasicInfo.manager != null) {

			tv_cm_manager.setText(customerBasicInfo.manager);
		}

		if (customerBasicInfo.sex != null && !"".equals(customerBasicInfo.sex)) {
			String realsex = sexs.get(customerBasicInfo.sex);
			// ------------------测试取值--------------------
			// Log.i("realsex", realsex+";"+customerBasicInfo.sex);
			// for (String key:sexs.keySet()) {
			// Log.i("sexs.get(key)", sexs.get(key)+"\r\n");
			// }
			// ------------------测试取值--------------------
			if ("男".equals(realsex)) {
				iv_cm_pic.setImageDrawable(getResources().getDrawable(
						R.drawable.fm_male));
			} else if ("女".equals(realsex)) {
				iv_cm_pic.setImageDrawable(getResources().getDrawable(
						R.drawable.fm_female));
			} else {
				iv_cm_pic.setImageDrawable(getResources().getDrawable(
						R.drawable.fm_nore));
			}
		}
		if (isShowingDialog) {
			dismissDialog(DIALOG_ID);
			isShowingDialog = false;
		}
	}

	private PieView shou3DBing(int[] colors, int[] shade_colors, float[] percent) {
		return new PieView(this, colors, shade_colors, percent);
	}

	/**
	 * 处理返回键
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			// Intent intent = new Intent(this, HallFirst1Activity.class);
			// startActivity(intent);
			// LogoActivity.activityList2.clear();
			// finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	// 对double类型的数组利用Arrays类实现降序排序 首先实现Comparator类：见util包
	public void sort(Double[] a) {
		Arrays.sort(a, new SortCompare());
	}

	public PercentInfo compare(double[] primary, double m) {// 是哪一产品就返回该产品对应信息
		for (int i = 0; i < primary.length; i++) {
			if (primary[i] == m) {
				primary[i] = -1;// 避免相同金额返回同一个percentInfo
				return percentInfos.get(i);
			}
		}
		return null;
	}

	// 简化一些的 使检测执行的更快
	public boolean isShown(ViewGroup parent, int lastPosition, int screenWidth) {
		// 获取最后一个子View
		int[] location = new int[2];
		parent.getChildAt(lastPosition / 2).getLocationOnScreen(location);
		if (location[0] <= screenWidth) {
			return true;
		}
		return false;
	}

	// 获取最后一个child当前的绝对位置
	public int getLastPosition(ViewGroup parent, int lastPosition,
			int screenWidth) {
		int[] location = new int[2];
		parent.getChildAt(lastPosition / 2).getLocationOnScreen(location);
		return location[0];
	}

	@Override
	protected void onDestroy() {
		// 主动清理资源，避免挑剔客户进行 长时间连续狂点 页面切换按钮 时可能会出现的oom异常
		// 个人感觉没什么用 因为Activit中智了 这些变量就会消失了 原来所引用对象在内存不足时会自动被清理 但还是写上了
		// 最好能在程序中及时释放 才有效
		if (percentInfos != null) {
			percentInfos.clear();
			percentInfos = null;
		}
		if (qianyue != null) {
			qianyue.clear();
			qianyue = null;
		}
		if (percentInfo != null) {
			percentInfo.clear();
			percentInfo = null;
		}
		System.gc();
		super.onDestroy();
	}

}
