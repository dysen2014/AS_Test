package com.pactera.financialmanager.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.adapter.ReturnCustomerAdapter;
import com.pactera.financialmanager.adapter.SpinnerAdapter;
import com.pactera.financialmanager.adapter.SpinnerAdapter.CallBackItemClickListener;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog.OnDateSetListener;
import com.pactera.financialmanager.datetimepicker.TimePickerDialog.OnTimeSetListener;
import com.pactera.financialmanager.ui.PullToRefreshLayout.OnRefreshListener;
import com.pactera.financialmanager.ui.model.CustmerQuery;
import com.pactera.financialmanager.ui.model.ReturnCustomer;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Catevalue;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.LimitsUtil;
import com.pactera.financialmanager.util.LogUtils;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户服务查看
 * @author Administrator
 *
 */
public class ReturnCusActivity extends ParentActivity implements
		OnClickListener, OnItemClickListener {

	// 对公对私选择
	private RadioButton rbtnWarnPerson, rbtnWarnCommon;
	// 标题信息
	private TextView[] tvText = new TextView[4];
	private TextView tvTitleName;
	// 输入内容
	private EditText etCusname;
	private TextView etType1, etType2, etType3;
	private Button btnQuery, btnClear, btnAdd;
	private LinearLayout layLoading;
	private LinearLayout layNodata;
	private PullToRefreshLayout ptrl;
	private SwipeListView lvList;
	private boolean isLoadmore = false;
	// 是否选择对个人
	private boolean isForPerson = true;
	// 查询结果
	private List<ReturnCustomer> returncusPersonLists = new ArrayList<ReturnCustomer>();
	private List<ReturnCustomer> returncusCommonLists = new ArrayList<ReturnCustomer>();
	private ReturnCustomerAdapter cusAdapter = null;
	private int selectPoisition = 0;// 单条信息选中项
	// 类型参数
	private String strCusname = "";
	private String strServeMode = "", strServeType = "", strCsstatus = "";
	
	// 数据请求
	private HConnection HCon;
	// 对私客户
	private static final int INDEX_P_RETURNLISTS = 1;
	private static final int INDEX_P_UPDATEINFO = 2;
	private static final int INDEX_P_ADDNEWRETURNCUS = 3;
	private static final int INDEX_P_DELRETURNCUS = 4;
	// 对公客户
	private static final int INDEX_C_RETURNLISTS = 5;
	private static final int INDEX_C_UPDATEINFO = 6;
	private static final int INDEX_C_ADDNEWRETURNCUS = 7;
	private static final int INDEX_C_DELRETURNCUS = 8;
	// 客户信息查询
	private static final int INDEX_P_QUERYCUSTMERS = 9;
	private static final int INDEX_C_QUERYCUSTMERS = 10;
	
	private final int QUERY_SIZE = 10;   // 查询条数
	private int CURRENT_PAGE_P = 1;		// 当前页
	private int CURRENT_PAGE_C = 1;		// 当前页
	
	// 临时输入框内容
	private ReturnEditView dialog = null;
	private ReturnCustomer tempCus = null;//判断临时输入内容是否上传成功,上传成功后置为null
	private DialogDel delDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_returncus);
		initTitle(this, R.drawable.customermanagercon);
		
		findViews();
		bindOnClickListener();
		
		hiddenLoading(true);
//		loading();
//		getReturncusDatas(CURRENT_PAGE_P);
	}
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.arg1) {
			// 对私客户列表
			case INDEX_P_RETURNLISTS:
			case INDEX_C_RETURNLISTS:
				if(isLoadmore){
					ptrl.refreshFinish(PullToRefreshLayout.SUCCEED);
				}
				List<ReturnCustomer> infolist = (ArrayList<ReturnCustomer>)msg.obj;
				if(isForPerson){ // 对个人列表
					if(infolist != null){
						returncusPersonLists.addAll(infolist);
					}
					setListViewIteminfo(returncusPersonLists);
				}else{
					if(infolist != null){
						returncusCommonLists.addAll(infolist);
					}
					setListViewIteminfo(returncusCommonLists);
				}
				break;
				
			/* 修改客户信息 */ 
			case INDEX_P_UPDATEINFO:
			case INDEX_C_UPDATEINFO:
				if(dialog != null){
					if(isForPerson){
						CURRENT_PAGE_P = 1;
						returncusPersonLists.clear();
						
						loading();
						getReturncusDatas(CURRENT_PAGE_P);
					}else{
						CURRENT_PAGE_C = 1;
						returncusCommonLists.clear();

						loading();
						getReturncusDatas(CURRENT_PAGE_C);
					}
					tempCus = null;
					dialog.close();// 关闭对话框
					Toast.makeText(ReturnCusActivity.this, "客户服务记录修改成功!!!", Toast.LENGTH_SHORT).show();
				}
				break;
				
			/* 新增建档客户 */ 
			case INDEX_P_ADDNEWRETURNCUS:
			case INDEX_C_ADDNEWRETURNCUS:
				if(dialog != null){
					if(isForPerson){
						CURRENT_PAGE_P = 1;
						returncusPersonLists.clear();

						loading();
						getReturncusDatas(CURRENT_PAGE_P);
					}else{
						CURRENT_PAGE_C = 1;
						returncusCommonLists.clear();

						loading();
						getReturncusDatas(CURRENT_PAGE_C);
					}
					
					tempCus = null;
					dialog.close();// 关闭对话框
					Toast.makeText(ReturnCusActivity.this, "成功新增一条客户服务记录!!!", Toast.LENGTH_SHORT).show();
				}
				break;
				
			/* 删除建档客户 */ 
			case INDEX_P_DELRETURNCUS:
			case INDEX_C_DELRETURNCUS:
				if(delDialog != null){
					// 对私记录
					if(isForPerson){
						returncusPersonLists.remove(selectPoisition);
					}else{
						returncusCommonLists.remove(selectPoisition);
					}
					cusAdapter.notifyDataSetChanged();
					lvList.hiddenShowView();
					delDialog.close();
					
					// 重置选中状态
					selectPoisition = 0;
					Toast.makeText(ReturnCusActivity.this, "删除记录成功", Toast.LENGTH_SHORT).show();
				}
				break;
				
			// 客户查询
			case INDEX_P_QUERYCUSTMERS:
			case INDEX_C_QUERYCUSTMERS:
				List<CustmerQuery> resultLists = (ArrayList<CustmerQuery>)msg.obj;
				if((resultLists != null && resultLists.size() > 0) && dialog!=null){
					dialog.queryReturncusID(resultLists);
				}
				break;
				
			default:
				break;
			}
		}
	};

	private void findViews() {
		rbtnWarnPerson = (RadioButton) findViewById(R.id.rbtn_warnperson);
		rbtnWarnCommon = (RadioButton) findViewById(R.id.rbtn_warncommon);
		tvTitleName = (TextView) findViewById(R.id.lvitem_info1);
		tvText[0] = (TextView) findViewById(R.id.returncus_text1);
		tvText[1] = (TextView) findViewById(R.id.returncus_text2);
		tvText[2] = (TextView) findViewById(R.id.returncus_text3);
		tvText[3] = (TextView) findViewById(R.id.returncus_text4);
		etCusname = (EditText) findViewById(R.id.returncus_cusname);
		etType1 = (TextView) findViewById(R.id.returncus_type1);
		etType2 = (TextView) findViewById(R.id.returncus_type2);
		etType3 = (TextView) findViewById(R.id.returncus_type3);

		btnQuery = (Button) findViewById(R.id.returncus_query);
		btnClear = (Button) findViewById(R.id.returncus_clear);
		btnAdd = (Button) findViewById(R.id.returncus_add);
		layLoading = (LinearLayout) findViewById(R.id.lay_loading);
		layNodata = (LinearLayout) findViewById(R.id.lay_nodata);
		ptrl = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
		lvList = (SwipeListView) findViewById(R.id.lv_pulllist);
		cusAdapter = new ReturnCustomerAdapter(this, lvList.getRightViewWidth());

		lvList.setPullstatus(false, true);// 设置是否可以上下拉动
		lvList.setAdapter(cusAdapter);// 显示数据
		// 无删除权限，隐藏按钮
		boolean isDelRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T0404, false);
		cusAdapter.setHiddentDel(isDelRight);
	}

	private void bindOnClickListener() {
		rbtnWarnPerson.setOnClickListener(this);
		rbtnWarnCommon.setOnClickListener(this);
		btnQuery.setOnClickListener(this);
		btnClear.setOnClickListener(this);
		btnAdd.setOnClickListener(this);
		etType1.setOnClickListener(this);
		etType2.setOnClickListener(this);
		etType3.setOnClickListener(this);
		
		lvList.setOnItemClickListener(this);
		ptrl.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				//Toast.makeText(ReturnCusActivity.this, "onRefresh", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
				isLoadmore = true;
				if(isForPerson){
					CURRENT_PAGE_P ++;
					getReturncusDatas(CURRENT_PAGE_P);
				}else{
					CURRENT_PAGE_C ++;
					getReturncusDatas(CURRENT_PAGE_C);
				}
			}
		});
		
		cusAdapter.setOnRightItemClickListener(new ReturnCustomerAdapter.OnRightItemClickListener() {
			@Override
			public void onRightItemClick(View v, int position) {
				delDialog = new DialogDel(ReturnCusActivity.this);
				if(isForPerson){
					if(returncusPersonLists != null && returncusPersonLists.size()>0){
						delDialog.show();
						delDialog.setCustserID(returncusPersonLists.get(position), position);
					}
				}else{
					if(returncusCommonLists != null && returncusCommonLists.size()>0){
						delDialog.show();
						delDialog.setCustserID(returncusCommonLists.get(position), position);
					}
				}
			}
		});
	}
	
	
	/**
	 * 加载进度条
	 */
	private void loading(){
		btnQuery.setEnabled(false);
		layLoading.setVisibility(View.VISIBLE);
		layNodata.setVisibility(View.GONE);
		ptrl.setVisibility(View.GONE);
	}
	
	
	/**
	 * 隐藏进度条
	 * @param noData
	 */
	private void hiddenLoading(boolean noData){
		btnQuery.setEnabled(true);
		layLoading.setVisibility(View.GONE);
		if(noData){
			layNodata.setVisibility(View.VISIBLE);
			ptrl.setVisibility(View.GONE);
		}else{
			layNodata.setVisibility(View.GONE);
			ptrl.setVisibility(View.VISIBLE);
		}
	}
	
	
	/****************************************************************/
	/****************************************************************/
	/****************************************************************/
	
	
	/**
	 * 查询对私客户列表信息
	 * @param currentPage
	 */
	private void getReturncusDatas(int currentPage) {
		if (Tool.haveNet(this)) {
			int offset = CURRENT_PAGE_P;
			int index = INDEX_P_RETURNLISTS;
			String requestType = InterfaceInfo.RETURNCUS_FORPERSON_QUERY;
			// 对公
			if (!isForPerson) {
				offset = CURRENT_PAGE_C;
				index = INDEX_C_RETURNLISTS;
				requestType = InterfaceInfo.RETURNCUS_FORCOMMON_QUERY;
			}

			// 添加查询条件
			String info = "&spareOne="+LogoActivity.user.getStaId()+"&size=" + QUERY_SIZE + "&offset=" + offset;
			if (!strCusname.equals("")) {
				String cusname = strCusname;
				try {
					cusname = Tool.getChineseEncode(strCusname);// 格式转码
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if(isForPerson){
					info += "&CUSTID=" + cusname;
				}else{
					info += "&CORPID=" + cusname;
				}
			}
			
			
			if (!strServeMode.equals("")) {
				info += "&SERVEMODE=" + strServeMode;
			}
			if (!strServeType.equals("")) {
				info += "&SERVETYPE=" + strServeType;
			}
			if (!strCsstatus.equals("")) {
				info += "&CSSTATUS=" + strCsstatus;
			}
			HCon = RequestInfo(this, Constants.requestType.Other, requestType, info, index, false);
		}
	}
	
	
	/**
	 * 添加新的服务记录
	 * @param cus
	 */
	private void addNewReturncus(ReturnCustomer cus){
		if (Tool.haveNet(ReturnCusActivity.this)) {
			int index = INDEX_P_ADDNEWRETURNCUS;
			String requestType = InterfaceInfo.RETURNCUS_FORPERSON_INSERT;
			String custID = "&CUSTID=" + cus.getCUSTID();
			if (!isForPerson) {
				index = INDEX_C_ADDNEWRETURNCUS;
				requestType = InterfaceInfo.RETURNCUS_FORCOMMON_INSERT;
				custID = "&CORPID=" + cus.getCUSTID();
			}

			// 标题和内容进行转码
			String serverTitle = cus.getSERVETITLE();
			String serverContent = cus.getPLANSERVECON();
			try {
				serverTitle = Tool.getChineseEncode(serverTitle);
				serverContent = Tool.getChineseEncode(serverContent);
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			String info = "&AWOKEDATE=" + cus.getAWOKEDATE() 
					+ "&CSSTATUS=" + cus.getCSSTATUS() 
					+ "&SERVEMODE=" + cus.getSERVEMODE()
					+ "&SERVETYPE=" + cus.getSERVETYPE() 
					+ "&SERVETITLE=" + serverTitle 
					+ "&PLANBGDATE=" + cus.getPLANBGDATE()
					+ "&PLANENDDATE=" + cus.getPLANENDDATE() 
					+ "&PLANSERVECON=" + serverContent 
					+ custID;
			
			HCon = RequestInfo(this, Constants.requestType.Other, requestType, info, index, false);
		}
	}
	
	/**
	 * 修改客户服务信息
	 * @param cus
	 */
	private void updateReturncusInfo(ReturnCustomer cus){
		if (Tool.haveNet(ReturnCusActivity.this)) {
			int index = INDEX_P_UPDATEINFO;
			String requestType = InterfaceInfo.RETURNCUS_FORPERSON_UPDATE;
			String custID = "&custID=" + cus.getCUSTID();
			if(!isForPerson){
				index = INDEX_C_UPDATEINFO;
				requestType = InterfaceInfo.RETURNCUS_FORCOMMON_UPDATE;
				custID = "&CORPID=" + cus.getCUSTID();
			}
			
			// 标题和内容进行转码
			String serverTitle = cus.getSERVETITLE();
			String serverContent = cus.getPLANSERVECON();
			try {
				serverTitle = Tool.getChineseEncode(serverTitle);
				serverContent = Tool.getChineseEncode(serverContent);
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			String info = "&CUSTSERVEID=" + cus.getCUSTSERVEID()
					+"&CSSTATUS=" + cus.getCSSTATUS() 
					+"&custName=" + cus.getCUSTNAME()
					+"&SERVEMODE=" + cus.getSERVEMODE()
					+"&SERVETYPE=" + cus.getSERVETYPE()
					+"&SERVETITLE=" + serverTitle
					+"&PLANBGDATE=" + cus.getPLANBGDATE()
					+"&PLANENDDATE=" + cus.getPLANENDDATE()
					+"&AWOKEDATE=" + cus.getAWOKEDATE()
					+"&PLANSERVECON=" + serverContent
					+custID;
			HCon = RequestInfo(this, Constants.requestType.Other, requestType, info, index, false);	
		}
	}
	
	/**
	 * 删除单条信息
	 * @param customer
	 * @param position
	 */
	private void delReturninfo(ReturnCustomer customer, int position){
		if (Tool.haveNet(ReturnCusActivity.this)) {
			String custserveID = customer.getCUSTSERVEID();
			int index = INDEX_P_DELRETURNCUS;
			String requestType = InterfaceInfo.RETURNCUS_FORPERSON_DEL;
			// 对公
			if(!isForPerson){
				index = INDEX_C_DELRETURNCUS;
				requestType = InterfaceInfo.RETURNCUS_FORCOMMON_DEL;
			}
			
			String info = "&spareOne=" + custserveID;
			HCon = RequestInfo(this, Constants.requestType.Other, requestType, info, index, false);	
		}
	}

	
	/**
	 * 根据客户姓名查询客户列表
	 * @param custmerName
	 */
	private void queryCustmerLists(String custmerName){
		// 109001 111111
		if (Tool.haveNet(this)) {
			String staID = LogoActivity.user.getStaId();
			String requestType = InterfaceInfo.QUERYCUS_FORPERSON_MYCUS;
			int index = INDEX_P_QUERYCUSTMERS;
			// 对公
			if(!isForPerson){
				requestType = InterfaceInfo.QUERYCUS_FORCOMMON_MYCUS;
				index = INDEX_C_QUERYCUSTMERS;
			}
			try {
				custmerName = Tool.getChineseEncode(custmerName); // 中文转码
				JSONObject jsinfo = new JSONObject();
				jsinfo.put("CUSTID", "");
				jsinfo.put("CUSTNAME", custmerName);
				jsinfo.put("USERNAME", "");
				jsinfo.put("PHONE_NO", "");
				jsinfo.put("BRNAME", "");
				jsinfo.put("CARDNUMBER", "");
				String info = "&spareOne=" + staID
						+ "&jsonData=" + jsinfo.toString();
				HCon = RequestInfo(this, Constants.requestType.Other, requestType, info, index, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		HResponse res = null;
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			hiddenLoading(true);
			Toast.makeText(ReturnCusActivity.this, "系统请求错误", Toast.LENGTH_SHORT).show();
			break;
			
		// 解析返回结果列表
		case INDEX_P_RETURNLISTS:
		case INDEX_P_UPDATEINFO:
		case INDEX_P_ADDNEWRETURNCUS:
		case INDEX_P_DELRETURNCUS:
		case INDEX_C_RETURNLISTS:
		case INDEX_C_UPDATEINFO:
		case INDEX_C_ADDNEWRETURNCUS:
		case INDEX_C_DELRETURNCUS:
		case INDEX_P_QUERYCUSTMERS:
		case INDEX_C_QUERYCUSTMERS:
			res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			readJson(res.dataJsonObject, connectionIndex);
			break;
			
		default:
			break;
		}
	}

	private void readJson(JSONObject tmpJsonObject, int connectionIndex) {
		String retCode = "";
		LogUtils.d(tmpJsonObject.toString());
		try {
			if (tmpJsonObject.has("retCode")) { // 返回标志
				retCode = tmpJsonObject.getString("retCode");
			}

			// 获取接口成功
			if (retCode.equals("0000")) {
				Message msg = new Message();
				msg.arg1 = connectionIndex;
				// 获取列表
				if (connectionIndex == INDEX_P_RETURNLISTS
						|| connectionIndex == INDEX_C_RETURNLISTS) {
					String rows= tmpJsonObject.getString("group");
					List<ReturnCustomer> returncusInfo = JSON.parseArray(rows, ReturnCustomer.class);
					msg.obj = returncusInfo;
				}
				
				// 获取客户姓名列表
				if(connectionIndex == INDEX_P_QUERYCUSTMERS
						|| connectionIndex == INDEX_C_QUERYCUSTMERS){
					// 结果集合
					String group = tmpJsonObject.getString("group");
					List<CustmerQuery> resultLists = JSON.parseArray(group, CustmerQuery.class);
					msg.obj = resultLists;
				}
				handler.sendMessage(msg);
			} else {
				hiddenLoading(true);
				Toast.makeText(ReturnCusActivity.this, "操作失败! 错误:" + retCode,
						Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
			return;
		}
	}
	
	
	
	/****************************************************************/
	/****************************************************************/
	/****************************************************************/
	
	
	/**
	 * 查询数据
	 */
	private void queryData() {
		strCusname = etCusname.getText().toString().trim();
		if (isForPerson) {
			loading();
			getReturncusDatas(CURRENT_PAGE_P);
		} else {
			loading();
			getReturncusDatas(CURRENT_PAGE_C);
		}
	}

	/**
	 * 设置ListviewItem
	 * @param lists
	 */
	private void setListViewIteminfo(List<ReturnCustomer> lists){
		if(isForPerson){
			// 无数据
			if(lists == null || lists.size() == 0){
				hiddenLoading(true);
			}else{
				hiddenLoading(false);
				
				cusAdapter.setInfolist(lists);
				cusAdapter.notifyDataSetChanged();
			}
		}else{
			// 对公列表信息
			if(lists == null || lists.size() == 0){
				hiddenLoading(true);
			}else{
				hiddenLoading(false);
				
				cusAdapter.setInfolist(lists);
				cusAdapter.notifyDataSetChanged();
			}
		}
	}

	/**
	 * 清空输入框
	 */
	private void clearInputvalue() {
		etType1.setText("");
		etType2.setText("");
		etType3.setText("");
		etCusname.setText("");
		
		strServeMode = "";
		strServeType = "";
		strCsstatus = "";
	}


	/**
	 * 设置时间
	 * @param etText
	 */
	private void setDatetimePickerDialog(final TextView etText, final boolean isHasTime){
		/*
		// 滚轮方法（摒弃）
		new WheelDateTimePickerDialog(context, new OnDateTimeSetListener() {
			@Override
			public void onDateTimeSet(int year, int monthOfYear, int dayOfMonth, 
					int hour, int minute) {
				String birthStr = year+"-"+monthOfYear+"-"+dayOfMonth+" "+hour+":"+minute+":00";
				etText.setText(birthStr);
			}
		}).show();
		*/
		DatePickerShow(new OnDateSetListener() {
			@Override
			public void onDateSet(String year, String month, String day) {
				String dateStr = year + "-" +month+"-" +day;
				if(isHasTime){
					setTimePickerDialog(etText, dateStr);
				}else{
					etText.setText(dateStr);
				}
			}
		});
	}
	
	private void setTimePickerDialog(final TextView etText, final String dateStr){
		TimePickerShow(new OnTimeSetListener() {
			@Override
			public void onTimeSet(int hourOfDay, int minute) {
				String date = dateStr+" " + hourOfDay+":"+minute+":00";
				etText.setText(date);
			}
		});
	}
	
	/**
	 * 删除对话框
	 * @author Administrator
	 */
	public class DialogDel extends Dialog implements
			android.view.View.OnClickListener {

		public static final int DEFAULT_STYLE = R.style.DialogTheme;
		public Context context;
		private Button btnOK, btnCancel;
		
		private ReturnCustomer customer;
		private int position;

		public DialogDel(Context context) {
			this(context, DEFAULT_STYLE);
		}

		public DialogDel(Context context, int style) {
			super(context, style);
			this.context = context;
			this.setCanceledOnTouchOutside(false);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.dialog_del);

			findViews();
		}

		private void findViews() {
			btnOK = (Button) findViewById(R.id.btn_ok);
			btnCancel = (Button) findViewById(R.id.btn_cancel);
			btnOK.setOnClickListener(this);
			btnCancel.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_ok:
				selectPoisition = position;
				delReturninfo(customer, position);
				break;
				
			case R.id.btn_cancel:
				selectPoisition = 0;
				close();
				break;

			default:
				break;
			}
		}
		
		/**
		 * 设置单条信息
		 */
		private void setCustserID(ReturnCustomer customer, int position){
			this.customer = customer;
			this.position = position;
		}

		public void close() {
			if (this != null && this.isShowing()) {
				dismiss();
			}
		}
	}

	/**
	 * 编辑对话框
	 * @author Administrator
	 *
	 */
	public class ReturnEditView extends Dialog implements
			android.view.View.OnClickListener {
		public static final int DEFAULT_STYLE = R.style.DialogTheme;
		private Context context;
		
		private TextView tvEditTitleName;
		private TextView tvReturncusTitle;
		private TextView etBeforeAlerttime, etStarttime, etEndtime;
		private EditText etCusname, etServetitle,etServecontent;
		private Button btnSearch;
		private RadioGroup grpServemodel, grpServetype, grpCsstatus;
		private RadioButton rbtnServemodels[] = new RadioButton[8];
		private RadioButton rbtnServetypes[] = new RadioButton[4];
		private RadioButton rbtnCsstatus[] = new RadioButton[2];
		private TextView tvCusname, tvServemodel, tvServetype;
		private TextView tvServetitle, tvStarttime, tvEndtime, tvServeContent,
				tvBeforeAlerttime;
		private TextView tvCsstatus, tvCreater, tvCreatetime, tvUpdatetime;
		private LinearLayout layLine10, layLine11, layLine12;
		private Button btnSave, btnCancel, btnClose, btnEdit;
		// 客户信息对象
		private ReturnCustomer customer;

		public boolean isAddNewrecode = false; // 是否新增新记录
		private String tempCustomerID = ""; // 客户唯一ID
		private String tempSelectCustomername = "";// 客户选择名称
		
		public ReturnEditView(Context context) {
			this(context, DEFAULT_STYLE);
		}

		public ReturnEditView(Context context, int style) {
			super(context, style);
			this.context = context;
			this.setCanceledOnTouchOutside(false);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.returncus_edit);

			findViews();
			bindOnClickListener();
		}

		private void findViews() {
			tvEditTitleName = (TextView) findViewById(R.id.tv_TitleName);
			tvReturncusTitle = (TextView) findViewById(R.id.returncus_title);
			btnSearch = (Button) findViewById(R.id.returncus_btn_search);
			etCusname = (EditText) findViewById(R.id.returncus_et_guestname);
			etServetitle = (EditText) findViewById(R.id.returncus_et_servetitle);
			etStarttime = (TextView) findViewById(R.id.returncus_et_starttime);
			etEndtime = (TextView) findViewById(R.id.returncus_et_endtime);
			etServecontent = (EditText) findViewById(R.id.returncus_et_servercontent);
			etBeforeAlerttime = (TextView) findViewById(R.id.returncus_et_beforetime);
			grpServemodel = (RadioGroup) findViewById(R.id.returncus_chlgroup);
			rbtnServemodels[0] = (RadioButton) findViewById(R.id.rbt_servemodel1);
			rbtnServemodels[1] = (RadioButton) findViewById(R.id.rbt_servemodel2);
			rbtnServemodels[2] = (RadioButton) findViewById(R.id.rbt_servemodel3);
			rbtnServemodels[3] = (RadioButton) findViewById(R.id.rbt_servemodel4);
			rbtnServemodels[4] = (RadioButton) findViewById(R.id.rbt_servemodel5);
			rbtnServemodels[5] = (RadioButton) findViewById(R.id.rbt_servemodel6);
			rbtnServemodels[6] = (RadioButton) findViewById(R.id.rbt_servemodel7);
			rbtnServemodels[7] = (RadioButton) findViewById(R.id.rbt_servemodel8);
			grpServetype = (RadioGroup) findViewById(R.id.returncus_servetypegroup);
			rbtnServetypes[0] = (RadioButton) findViewById(R.id.rbt_servetype1);
			rbtnServetypes[1] = (RadioButton) findViewById(R.id.rbt_servetype2);
			rbtnServetypes[2] = (RadioButton) findViewById(R.id.rbt_servetype3);
			rbtnServetypes[3] = (RadioButton) findViewById(R.id.rbt_servetype4);
			grpCsstatus = (RadioGroup) findViewById(R.id.returncus_csstatusgroup);
			rbtnCsstatus[0] = (RadioButton) findViewById(R.id.rbt_csstatus1);
			rbtnCsstatus[1] = (RadioButton) findViewById(R.id.rbt_csstatus2);
			tvCusname = (TextView) findViewById(R.id.returncus_tv_guestname);
			tvServemodel = (TextView) findViewById(R.id.returncus_tv_chl);
			tvServetype = (TextView) findViewById(R.id.returncus_tv_servetype);
			tvServetitle = (TextView) findViewById(R.id.returncus_tv_servetitle);
			tvStarttime = (TextView) findViewById(R.id.returncus_tv_starttime);
			tvEndtime = (TextView) findViewById(R.id.returncus_tv_endtime);
			tvServeContent = (TextView) findViewById(R.id.returncus_tv_servercontent);
			tvBeforeAlerttime = (TextView) findViewById(R.id.returncus_tv_beforetime);
			tvCsstatus = (TextView) findViewById(R.id.returncus_tv_csstatus);
			tvCreater = (TextView) findViewById(R.id.returncus_tv_creater);
			tvCreatetime = (TextView) findViewById(R.id.returncus_tv_createtime);
			tvUpdatetime = (TextView) findViewById(R.id.returncus_tv_updatetime);
			layLine10 = (LinearLayout) findViewById(R.id.edit_line_10);
			layLine11 = (LinearLayout) findViewById(R.id.edit_line_11);
			layLine12 = (LinearLayout) findViewById(R.id.edit_line_12);
			
			btnSave = (Button) findViewById(R.id.returncus_save);
			btnCancel = (Button) findViewById(R.id.returncus_cancel);
			btnClose = (Button) findViewById(R.id.returncus_close);
			btnEdit = (Button) findViewById(R.id.returncus_edit);
		}
		

		/**
		 * 绑定点击事件
		 */
		private void bindOnClickListener() {
			btnSearch.setOnClickListener(this);
			etStarttime.setOnClickListener(this);
			etEndtime.setOnClickListener(this);
			etBeforeAlerttime.setOnClickListener(this);
			btnSave.setOnClickListener(this);
			btnCancel.setOnClickListener(this);
			btnClose.setOnClickListener(this);
			btnEdit.setOnClickListener(this);
		}
		
		private void showDialogView(int type){
			btnSearch.setVisibility(View.VISIBLE);
			etCusname.setVisibility(View.VISIBLE);
			// 标题
			if(isForPerson){
				tvEditTitleName.setText("客户姓名:");
			}else{
				tvEditTitleName.setText("企业名称:");
			}
			
			int visibility1 = View.VISIBLE;
			int visibility2 = View.GONE;
			// 类型1:[查看]
			if(type == 0){
				tvReturncusTitle.setText("查看客户服务记录");
				visibility1 = View.GONE;
				visibility2  = View.VISIBLE;
				btnClose.setVisibility(View.VISIBLE);
				etCusname.setVisibility(visibility1);
				btnSearch.setVisibility(visibility1);
				tvCusname.setVisibility(visibility2);
				
				// 是否可以编辑权限
				boolean isUpdateRight = LimitsUtil.checkUserLimit(ReturnCusActivity.this, LimitsUtil.T0402);
				if(isUpdateRight){
					btnEdit.setVisibility(View.VISIBLE);
				}else{
					btnEdit.setVisibility(View.GONE);
				}
			}
			// 类型2:[编辑]
			else if(type == 1){
				tvReturncusTitle.setText("编辑客户服务记录");
				visibility1 = View.VISIBLE;
				visibility2  = View.GONE;
				btnClose.setVisibility(View.INVISIBLE);
				btnEdit.setVisibility(View.GONE);
				
				btnSearch.setVisibility(visibility2);
				etCusname.setVisibility(visibility2);
				tvCusname.setVisibility(visibility1);
			}
			// 按钮显示状态
			btnSave.setVisibility(visibility1);
			btnCancel.setVisibility(visibility1);
			// 输入框显示
			etServetitle.setVisibility(visibility1);
			etStarttime.setVisibility(visibility1);
			etEndtime.setVisibility(visibility1);
			etServecontent.setVisibility(visibility1);
			etBeforeAlerttime.setVisibility(visibility1);
			grpServemodel.setVisibility(visibility1);
			grpServetype.setVisibility(visibility1);
			grpCsstatus.setVisibility(visibility1);
			// 显示值
			tvServemodel.setVisibility(visibility2);
			tvServetype.setVisibility(visibility2);
			tvServetitle.setVisibility(visibility2);
			tvStarttime.setVisibility(visibility2);
			tvEndtime.setVisibility(visibility2);
			tvServeContent.setVisibility(visibility2);
			tvBeforeAlerttime.setVisibility(visibility2);
			tvCsstatus.setVisibility(visibility2);
			// 状态3:[新增]
			if(type == 2){
				tempCustomerID = ""; // 创建人ID
				tempSelectCustomername = "";
				tvReturncusTitle.setText("创建客户服务记录");
				btnSave.setVisibility(View.VISIBLE);
				btnCancel.setVisibility(View.VISIBLE);
				btnClose.setVisibility(View.INVISIBLE);
				btnEdit.setVisibility(View.GONE);
				// 新增状态不显示创建人&创建时间
				visibility2 = View.GONE;
			}else{
				visibility2 = View.VISIBLE;
			}
			layLine10.setVisibility(visibility2);
			layLine11.setVisibility(visibility2);
			layLine12.setVisibility(visibility2);
		}
		
		/**
		 * 新增服务
		 * @return
		 */
		private ReturnCustomer saveReturncusInfo(){
			// 页面是输入框的值
			String cusname = etCusname.getText().toString();
			String servetitle = etServetitle.getText().toString();
			String starttime = etStarttime.getText().toString();
			String endtime = etEndtime.getText().toString();
			String servecontext = etServecontent.getText().toString();
			String beforealerttime = etBeforeAlerttime.getText().toString();
			// 取联络渠道的值
			int servermodelID = grpServemodel.getCheckedRadioButtonId();
			RadioButton servemodelRbtn = (RadioButton) findViewById(servermodelID);
			String servemodelCode = Catevalue.getCode(servemodelRbtn.getText().toString(),
					Catevalue.serveModelID,
					Catevalue.serveModelStr);
//			String servemodelCode = NewCatevalue.getLabel(context, NewCatevalue.serveMode, servemodelRbtn.getText().toString());

			// 取客服服务类型
			int servertypeID = grpServetype.getCheckedRadioButtonId();
			RadioButton servetypeRbtn = (RadioButton) findViewById(servertypeID);
			String servetypeCode = Catevalue.getCode(servetypeRbtn.getText().toString(),
					Catevalue.serveTypeID,
					Catevalue.serveTypeStr);
//			String servetypeCode = NewCatevalue.getLabel(context, NewCatevalue.serveType, servetypeRbtn.getText().toString());

			// 取执行状态的值
			int cusstatusID = grpCsstatus.getCheckedRadioButtonId();
			RadioButton cusstatusRbtn = (RadioButton) findViewById(cusstatusID);
			String cusstatusCode = Catevalue.getCode(cusstatusRbtn.getText().toString(),
					Catevalue.csstatusID,
					Catevalue.csstatusStr);
//			String cusstatusCode = NewCatevalue.getLabel(context, NewCatevalue.csStatus, cusstatusRbtn.getText().toString());


			// 临时对象保存值
			ReturnCustomer cus = new ReturnCustomer();
			cus.setCUSTID(tempCustomerID);
			cus.setCUSTNAME(cusname);
			cus.setSERVEMODE(servemodelCode);
			cus.setSERVETYPE(servetypeCode);
			cus.setSERVETITLE(servetitle);
			cus.setPLANBGDATE(starttime);
			cus.setPLANENDDATE(endtime);
			cus.setPLANSERVECON(servecontext);
			cus.setAWOKEDATE(beforealerttime);
			cus.setCSSTATUS(cusstatusCode);
			/*Toast.makeText(
					context,
					cusname + "->" + servemodelCode + " ->" + servetypeCode
							+ "-->" + servetitle + " ->" + starttime
							+ " ->" + endtime + " ->" + servecontext
							+ " ->" + beforealerttime + " ->" + cusstatusCode,
					Toast.LENGTH_SHORT).show();*/
			
			return cus;
		}
		
		/**
		 * 校验是否上传至后台服务
		 * @param cus
		 * @return
		 */
		private boolean isSave2Server(ReturnCustomer cus){
			if(isAddNewrecode){
				// 判断输入条件是否为空
				if(TextUtils.isEmpty(cus.getCUSTID())){
					Toast.makeText(context, "抱歉，请选择一个客户！", Toast.LENGTH_SHORT).show();
					return false;
				}
				if(!tempSelectCustomername.equals(cus.getCUSTNAME())){
					Toast.makeText(context, "抱歉，输入框名称与选择客户名称不一致！", Toast.LENGTH_SHORT).show();
					return false;
				}
			}
			if(TextUtils.isEmpty(cus.getSERVETITLE())){
				Toast.makeText(context, "‘服务标题’不能为空", Toast.LENGTH_SHORT).show();
				return false;
			}
			if(TextUtils.isEmpty(cus.getPLANBGDATE())){
				Toast.makeText(context, "请选择‘开始时间’", Toast.LENGTH_SHORT).show();
				return false;
			}
			if(TextUtils.isEmpty(cus.getPLANENDDATE())){
				Toast.makeText(context, "请选择‘结束时间’", Toast.LENGTH_SHORT).show();
				return false;
			}/*
			if(TextUtils.isEmpty(cus.getPlanServeCon())){
				Toast.makeText(context, "‘服务内容’不能为空", Toast.LENGTH_SHORT).show();
				return false;
			}*/
			
			return true;
		}
		
		/**
		 * 编辑服务
		 * @param cus
		 */
		private void showEditValue(ReturnCustomer cus){
			if(cus == null){
				return;
			}
			tempCustomerID = cus.getCUSTID();
			tempSelectCustomername = cus.getCUSTNAME();
			etCusname.setText(cus.getCUSTNAME());
			etServetitle.setText(cus.getSERVETITLE());
			etStarttime.setText(cus.getPLANBGDATE());
			etEndtime.setText(cus.getPLANENDDATE());
			etServecontent.setText(cus.getPLANSERVECON());
			etBeforeAlerttime.setText(cus.getAWOKEDATE());
			tvCreater.setText(cus.getCREATORID());
			tvCreatetime.setText(cus.getCREATEDATE());
			tvUpdatetime.setText(cus.getMODIFYDATE());
			
			// 设置单选按钮状态
			String servemodel = cus.getSERVEMODE();
			if(!TextUtils.isEmpty(servemodel)){
				int servemodeIndex = Integer.parseInt(servemodel);
				if (servemodeIndex <= rbtnServemodels.length) {
					rbtnServemodels[servemodeIndex-1].setChecked(true);
				}
			}
			String servetype = cus.getSERVETYPE();
			if(!TextUtils.isEmpty(servetype)){
				int servetypeIndex = Integer.parseInt(servetype);
				if(servetypeIndex <= rbtnServetypes.length){
					rbtnServetypes[servetypeIndex-1].setChecked(true);
				}
			}
			// 设置单选执行状态
			String csstatus = cus.getCSSTATUS();
			if(!TextUtils.isEmpty(csstatus)){
				if(csstatus.equals("1")){ //未执行状态
					rbtnCsstatus[0].setChecked(true);
				}else{
					rbtnCsstatus[1].setChecked(true);
				}
			}
		}
		
		/**
		 * 查看服务记录
		 */
		private void showTextValue(ReturnCustomer customer){
			this.customer = customer;
			// 联络渠道转义成文字
//			String servemode = Catevalue.getName(customer.getSERVEMODE(),
//					Catevalue.serveModelID,
//					Catevalue.serveModelStr);
			String servemode = NewCatevalue.getLabel(context, NewCatevalue.serveMode, customer.getSERVEMODE());

			// 服务类型
//			String servetype = Catevalue.getName(customer.getSERVETYPE(),
//					Catevalue.serveTypeID,
//					Catevalue.serveTypeStr);
			String servetype = NewCatevalue.getLabel(context, NewCatevalue.serveType,customer.getSERVETYPE());

			// 执行状态
			String csstatus = NewCatevalue.getLabel(context, NewCatevalue.csStatus, customer.getCSSTATUS());

			tvCusname.setText(customer.getCUSTNAME());
			tvServemodel.setText(servemode);
			tvServetype.setText(servetype);
			tvServetitle.setText(customer.getSERVETITLE());
			tvStarttime.setText(customer.getPLANBGDATE());
			tvEndtime.setText(customer.getPLANENDDATE());
			tvServeContent.setText(customer.getPLANSERVECON());
			tvBeforeAlerttime.setText(customer.getAWOKEDATE());
			tvCsstatus.setText(csstatus);
			tvCreater.setText(customer.getCREATORID());
			tvCreatetime.setText(customer.getCREATEDATE());
			tvUpdatetime.setText(customer.getMODIFYDATE());
		}

		public void close() {
			if (this != null && this.isShowing()) {
				dismiss();
			}
		}

		/**
		 * 根据用户名查询ID
		 * @param custLists
		 */
		public void queryReturncusID(final List<CustmerQuery> custLists){
			// 下拉列表
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setIcon(R.drawable.buildingicon);
			builder.setTitle("查询结果");
			if(custLists.size() > 0){

				final List<String> custNams = new ArrayList<String>();
				for(CustmerQuery cus : custLists){
					// 添加身份证
					if(isForPerson){
						custNams.add(cus.getCUSTNAME()+"["+cus.getCUST_PSN_CARD_NUMBER()+"]");
					}else{
						custNams.add(cus.getCUSTNAME()+"["+cus.getCUST_PSN_CARD_NUMBER()+"]");
					}
				}

				final String names[] = new String[custLists.size()];
				for (int i = 0; i < custNams.size(); i++) {
					names[i] = custNams.get(i);
				}
				
				builder.setItems(names, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						tempCustomerID = custLists.get(which).getCUSTID(); // 测试ID
//						tempSelectCustomername = custNames[which];
						tempSelectCustomername = custLists.get(which).getCUSTNAME();
						etCusname.setText(tempSelectCustomername);
					}
				});
				builder.setNeutralButton("取消", null); // 确定按钮
			}else{
				builder.setNeutralButton("确定", null); // 确定按钮
				builder.setMessage("抱歉，没有找到对应的客户姓名！");
			}
			builder.show();
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			// 客户姓名模糊查询
			case R.id.returncus_btn_search:

				String cusNameStr = etCusname.getText().toString().trim();
				if(TextUtils.isEmpty(cusNameStr)){
					Toast.makeText(context, "抱歉，【客户名称】为空无法查询！", Toast.LENGTH_SHORT).show();
				}else{

					//queryReturncusID(cusNameStr);
					queryCustmerLists(cusNameStr);
				}
				break;
				
			// 显示时间控件
			case R.id.returncus_et_starttime:
				setDatetimePickerDialog(etStarttime, true);
				break;
			case R.id.returncus_et_endtime:
				setDatetimePickerDialog(etEndtime, true);
				break;
			case R.id.returncus_et_beforetime:
				setDatetimePickerDialog(etBeforeAlerttime, false);
				break;
				
			// 保存数据
			case R.id.returncus_save:
				tempCus = saveReturncusInfo();
				boolean isOK = isSave2Server(tempCus);
				if(isOK){
					if(customer != null){
						// 将temp值保存到传递过来的编辑对象中
						customer.setCUSTID(tempCus.getCUSTID());
						customer.setCUSTNAME(tempCus.getCUSTNAME());
						customer.setSERVEMODE(tempCus.getSERVEMODE());
						customer.setSERVETYPE(tempCus.getSERVETYPE());
						customer.setSERVETITLE(tempCus.getSERVETITLE());
						customer.setPLANBGDATE(tempCus.getPLANBGDATE());
						customer.setPLANENDDATE(tempCus.getPLANENDDATE());
						customer.setPLANSERVECON(tempCus.getPLANSERVECON());
						customer.setAWOKEDATE(tempCus.getAWOKEDATE());
						customer.setCSSTATUS(tempCus.getCSSTATUS());
						// 修改客户服务信息
						updateReturncusInfo(customer);
					}else{
						// 新增客户信息
						addNewReturncus(tempCus);
					}
				}
				break;
			// 关闭对话框
			case R.id.returncus_cancel:
			case R.id.returncus_close:
				tempCus = saveReturncusInfo();
				// 如果是编辑原来ListItem数据，则不做临时保存
				if(customer != null){
					tempCus = null;
				}
				close();
				break;
			//编辑按钮
			case R.id.returncus_edit:
				isAddNewrecode = false;
				showDialogView(1);
				showEditValue(customer);
				break;

			default:
				break;
			}
		}
	}
	
	


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 对私客户
		case R.id.rbtn_warnperson:
			isForPerson = true;
			tvText[0].setText("客户姓名：");
			etCusname.setHint("请输入客户姓名...");
			tvTitleName.setText("客户姓名");
			
			clearInputvalue();
			// 有数据显示
//			if(returncusPersonLists.size() != 0){
//				setListViewIteminfo(returncusPersonLists);
//			}else{
//				// 无数据则请求
//				isLoadmore = false;
//				loading();
//				getReturncusDatas(CURRENT_PAGE_P);
//			}
			setListViewIteminfo(returncusPersonLists);
			break;
		// 对公客户	
		case R.id.rbtn_warncommon:
			isForPerson = false;
			tvText[0].setText("企业名称：");
			etCusname.setHint("请输入企业名称...");
			tvTitleName.setText("企业名称");
			
			clearInputvalue();
//			if(returncusCommonLists.size() != 0){
//				setListViewIteminfo(returncusCommonLists);
//			}else{
//				isLoadmore = false;
//				loading();
//				getReturncusDatas(CURRENT_PAGE_C);
//			}
			setListViewIteminfo(returncusCommonLists);
			break;

		// 联络渠道
		case R.id.returncus_type1:
			SpinnerAdapter.showSelectDialog(this,
					Catevalue.serveModelStr,
					Catevalue.serveModelID, etType1,
					new CallBackItemClickListener() {
						@Override
						public void CallBackItemClick(String spinnerID) {
							strServeMode = spinnerID;
						}
					});
			break;
		// 服务类型
		case R.id.returncus_type2:
			SpinnerAdapter.showSelectDialog(this,
					Catevalue.serveTypeStr,
					Catevalue.serveTypeID, etType2,
					new CallBackItemClickListener() {
						@Override
						public void CallBackItemClick(String spinnerID) {
							strServeType = spinnerID;
						}
					});
			break;
		// 执行状态
		case R.id.returncus_type3:
			SpinnerAdapter.showSelectDialog(this,
					NewCatevalue.csStatus, etType3,
					new CallBackItemClickListener() {
						@Override
						public void CallBackItemClick(String spinnerID) {
							strCsstatus = spinnerID;
						}
					});
			break;
			
		//  [查询]
		case R.id.returncus_query:
			boolean isQueryRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T0403);
			if(!isQueryRight){
				return;
			}
			
			if(isForPerson){
				CURRENT_PAGE_P = 1;
				returncusPersonLists.clear();
			}else{
				CURRENT_PAGE_C = 1;
				returncusCommonLists.clear();
			}
			closeKeyboard();
			queryData();
			break;
		// ----->>> [清空]
		case R.id.returncus_clear:
			if(isForPerson){
				CURRENT_PAGE_P = 1;
				returncusPersonLists.clear();
			}else{
				CURRENT_PAGE_C = 1;
				returncusCommonLists.clear();
			}
			clearInputvalue();
			break;
		// ----->>> [新增]
		case R.id.returncus_add:
			boolean isAddRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T0401);
			if(!isAddRight){
				return;
			}

			gotoReturnCusUpdateActivity(null);
//			 新增服务悬浮框
//			dialog = new ReturnEditView(this);
//			dialog.isAddNewrecode = true;
//			dialog.show();
//			dialog.showDialogView(2);
//			if(tempCus != null){
//				dialog.showEditValue(tempCus);
//			}
			break;
			
		default:
			break;
		}
	}


	private void gotoReturnCusUpdateActivity(ReturnCustomer customer){
		Bundle bundle = new Bundle();
		bundle.putSerializable("customer", customer);
		bundle.putBoolean("isForPerson", isForPerson);
		gotoNextActivity(ReturnCusUpdateActivity.class, bundle);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		ReturnCustomer customer = (ReturnCustomer)adapterView.getItemAtPosition(position);
		if(customer != null){
			gotoReturnCusUpdateActivity(customer);
//			dialog = new ReturnEditView(this);
//			dialog.show();
//			dialog.showDialogView(0);
//			dialog.showTextValue(customer);
		}
	}

}