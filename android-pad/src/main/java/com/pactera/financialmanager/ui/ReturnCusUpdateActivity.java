package com.pactera.financialmanager.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog.OnDateSetListener;
import com.pactera.financialmanager.datetimepicker.TimePickerDialog.OnTimeSetListener;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.model.CustmerQuery;
import com.pactera.financialmanager.ui.model.ReturnCustomer;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Catevalue;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.DialogAlert;
import com.pactera.financialmanager.util.FormatUtil;
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
public class ReturnCusUpdateActivity extends ParentActivity implements OnClickListener, PullToRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

	// 对私客户
	private static final int INDEX_P_UPDATEINFO = 2;
	private static final int INDEX_P_ADDNEWRETURNCUS = 3;
	// 对公客户
	private static final int INDEX_C_UPDATEINFO = 6;
	private static final int INDEX_C_ADDNEWRETURNCUS = 7;
	// 客户信息查询
	private static final int INDEX_P_QUERYCUSTMERS = 9;
	private static final int INDEX_C_QUERYCUSTMERS = 10;

	public static final int DEFAULT_STYLE = R.style.DialogTheme;

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
	RelativeLayout rlBntAction;
	private LinearLayout layLine10, layLine11, layLine12;
	private Button btnSave, btnCancel, btnClose, btnEdit;
	// 客户信息对象
	private ReturnCustomer customer;

	public boolean isAddNewrecode = false; // 是否新增新记录
	private String tempCustomerID = ""; // 客户唯一ID
	private String tempSelectCustomername = "";// 客户选择名称
	// 是否选择对个人
	private boolean isForPerson = true;
	// 数据请求
	private HConnection HCon;
	private ReturnCustomer tempCus = null;//判断临时输入内容是否上传成功,上传成功后置为null

	//    private ListView lvResult;
	private PullToRefreshLayout ptrl;
	private PullableListView lvList;
	private LinearLayout layLoading;
	private LinearLayout layNodatas;
	private LinearLayout llPull, llViewDate;
	private int offset = 1;
	private boolean isLoadmore = false;            // 是否上拉加载更多

	private CustmerAdapter adapter;
	private String strCustname;
	List<CustmerQuery> forPersonResults = new ArrayList<CustmerQuery>();
	List<CustmerQuery> forCommonResults = new ArrayList<CustmerQuery>();

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (isLoadmore) {
				// 隐藏地步
				ptrl.refreshFinish(PullToRefreshLayout.SUCCEED);
			}

			switch (msg.arg1) {
				// 客户查询
				case INDEX_P_QUERYCUSTMERS:
				case INDEX_C_QUERYCUSTMERS:
					// 结果集
					List<CustmerQuery> results = (ArrayList<CustmerQuery>) msg.obj;
					if (results != null && results.size() > 0) {
						// 如果查询结果为一条信息，则自动跳转到下一个页面
						if(results.size() == 1){
							CustmerQuery custmer = results.get(0);
							tempCustomerID = custmer.getCUSTID();
							etCusname.setText(custmer.getCUSTNAME());
						}

						if (isForPerson) {
							forPersonResults.addAll(results);
							adapter.setInfolist(forPersonResults);
						} else {
							forCommonResults.addAll(results);
							adapter.setInfolist(forCommonResults);
						}
//						LogUtils.d(forPersonResults.size()+"**************"+results.size()+"****************"+forCommonResults.size());

						adapter.notifyDataSetChanged();
						hiddentLoading(false);//不能放在判断条件的里边，否则在查询无数据时，进度条会一直转
					} else {
						hiddentLoading(true);
						toast("抱歉，没有找到对应的客户\n请先建档！");
					}
					break;
				/* 修改客户信息 */
				case INDEX_P_UPDATEINFO:
				case INDEX_C_UPDATEINFO:

						if(isForPerson){


							loading();

						}else{


							loading();

						}
						tempCus = null;
						toast("客户服务记录修改成功!!!");
					finish();
					break;

			/* 新增建档客户 */
				case INDEX_P_ADDNEWRETURNCUS:
				case INDEX_C_ADDNEWRETURNCUS:

						if(isForPerson){
							loading();

						}else{


							loading();

						}
						tempCus = null;
						toast("成功新增一条客户服务记录!!!");
					finish();
					break;
				default:
					break;
			}
		}
	};
	private View oldView;
	private String cusNameStr;
	private boolean touchFlag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_returncus_update);
		initTitle(this, "客户建档");

		findViews();
		bindOnClickListener();
		hiddentLoading(true);
		layNodatas.setVisibility(View.GONE);
		Bundle bundle = this.getIntent().getExtras();
		if(bundle != null){
			customer = (ReturnCustomer) bundle.getSerializable("customer");
			LogUtils.d("customer:"+customer);
			if (customer == null)
				llViewDate.setVisibility(View.INVISIBLE);
			else{
				llViewDate.setVisibility(View.VISIBLE);
				showTextValue(customer);
			}
			isForPerson = bundle.getBoolean("isForPerson");
		}
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

		btnSave = bindView(R.id.returncus_save);
		btnCancel = (Button) findViewById(R.id.returncus_cancel);
		btnCancel.setVisibility(View.INVISIBLE);
		btnClose = (Button) findViewById(R.id.returncus_close);
		btnEdit = (Button) findViewById(R.id.returncus_edit);

		//        lvResult = (ListView) findViewById(R.id.lv_results);
		ptrl = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
		lvList = (PullableListView) findViewById(R.id.lv_pulllist);
		layLoading = (LinearLayout) findViewById(R.id.lay_loading);
		layNodatas = (LinearLayout) findViewById(R.id.lay_nodata);
		llPull = bindView(R.id.ll_pull);
		llPull.setVisibility(View.INVISIBLE);
		llViewDate = bindView(R.id.ll_view_date);

		rlBntAction = bindView(R.id.returncus_ll_action);
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

		lvList.setOnItemClickListener(this);

		lvList.setDividerHeight(0);

		adapter = new CustmerAdapter();
//        lvResult.setAdapter(adapter);
		lvList.setAdapter(adapter);
		lvList.setPullstatus(false, true);
		ptrl.setOnRefreshListener(this);
	}

	private void hiddentLoading(boolean nodata) {

		btnSearch.setEnabled(true);
		layLoading.setVisibility(View.GONE);
		if (nodata && offset == 1) {
			layNodatas.setVisibility(View.VISIBLE);
//            lvResult.setVisibility(View.GONE);
			ptrl.setVisibility(View.GONE);
		} else {
			layNodatas.setVisibility(View.GONE);
//            lvResult.setVisibility(View.VISIBLE);
			ptrl.setVisibility(View.VISIBLE);
		}
	}



	/**
	 * 检查查询条件
	 *
	 * @return
	 */
	private boolean checkQueryValue() {

		// 判断是否为空
		if (!TextUtils.isEmpty(cusNameStr) ) {
			return true;
		} else {
			DialogAlert dialog = new DialogAlert(this);
			dialog.show();
			dialog.setMsg("抱歉，查询条件不能为空！");
			return false;
		}
	}


	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {

		touchFlag = setSelectorItemColor(view);

		CustmerQuery custmer = (CustmerQuery) adapterView
				.getItemAtPosition(position);

		// 选择的用户
		if(custmer != null){
			tempSelectCustomername = custmer.getCUSTNAME();
			tempCustomerID = custmer.getCUSTID();
			etCusname.setText(tempSelectCustomername);
		}else {

		}
	}

	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
		// 下拉刷新
	}

	@Override
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
		// 上拉加载更多
		isLoadmore = true;
		offset++;

		if (checkQueryValue()) {
			queryCustmerLists(cusNameStr);
		}else {
			return;
		}
	}

	private class CustmerAdapter extends BaseAdapter {

		private List<CustmerQuery> infolist;

		@Override
		public int getCount() {
			if (infolist != null && infolist.size() > 0) {
				return infolist.size();
			} else {
				return 0;
			}
		}

		@Override
		public Object getItem(int position) {
			if (infolist != null && infolist.size() > 0) {
				return infolist.get(position);
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			HolderView holder;
			if (convertView == null) {
				convertView = View.inflate(getApplication(),
						R.layout.retun_item, null);
				holder = new HolderView();
				holder.tvValue1 = (TextView) convertView
						.findViewById(R.id.tv_value1);
				holder.tvValue2 = (TextView) convertView
						.findViewById(R.id.tv_value2);
				holder.tvValue3 = (TextView) convertView
						.findViewById(R.id.tv_value3);
				holder.view1 = (View) convertView.findViewById(R.id.view1);
				holder.view2 = (View) convertView.findViewById(R.id.view2);
				convertView.setTag(holder);
			} else {
				holder = (HolderView) convertView.getTag();
			}
			if (position % 2 != 0) {
				convertView.setBackgroundColor(getResources().getColor(
						R.color.lvtopbg));
			} else {
				convertView.setBackgroundColor(getResources().getColor(
						R.color.white));
			}
			// 将获取到的数据进行展示
			CustmerQuery overdue = infolist.get(position);
			LogUtils.d("记录序号："+position);
			holder.tvValue1.setText(String.valueOf(position+1));//序号 position 下标 从 0 开始的
			holder.tvValue2.setText(overdue.getCUSTNAME());
			holder.tvValue3.setText(overdue.getCUST_PSN_CARD_NUMBER());//生产跟测试比没有——PSN
			if (isForPerson) {
				holder.tvValue3.setText(overdue.getCUST_PSN_CARD_NUMBER());
				holder.tvValue3.setVisibility(View.VISIBLE);
			} else {
				holder.tvValue3.setText(overdue.getCUST_PSN_CARD_NUMBER());
				holder.tvValue3.setVisibility(View.VISIBLE);
			}
			holder.view1.setVisibility(View.GONE);
			holder.view2.setVisibility(View.GONE);

			return convertView;
		}

		public void setInfolist(List<CustmerQuery> infolist) {
			this.infolist = infolist;
		}
	}

	private static class HolderView {
		private TextView tvValue0;
		private TextView tvValue1;
		private TextView tvValue2;
		private TextView tvValue3;
		private View view1;
		private View view2;
		private View view0;
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
			boolean isUpdateRight = LimitsUtil.checkUserLimit(ReturnCusUpdateActivity.this, LimitsUtil.T0402);
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
				toast("抱歉，请选择一个客户！");
				return false;
			}
			if(!tempSelectCustomername.equals(cus.getCUSTNAME())){
				toast("抱歉，输入框名称与选择客户名称不一致！");
				return false;
			}
		}
		if(TextUtils.isEmpty(cus.getSERVETITLE())){
			toast("‘服务标题’不能为空");
			return false;
		}
		if(TextUtils.isEmpty(cus.getPLANBGDATE())){
			toast("请选择‘开始时间’");
			return false;
		}
		if(TextUtils.isEmpty(cus.getPLANENDDATE())){
			toast("请选择‘结束时间’");
			return false;
		}/*
			if(TextUtils.isEmpty(cus.getPlanServeCon())){
				toast("‘服务内容’不能为空");
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

		/*编辑 时  显示编辑框*/
		etCusname.setVisibility(View.GONE);
		btnSearch.setVisibility(View.GONE);
		grpServemodel.setVisibility(View.VISIBLE);
		grpServetype.setVisibility(View.VISIBLE);
		etServecontent.setVisibility(View.VISIBLE);
		etServetitle.setVisibility(View.VISIBLE);
		etStarttime.setVisibility(View.VISIBLE);
		etEndtime.setVisibility(View.VISIBLE);
		tvCusname.setText(customer.getCUSTNAME());
		tvCusname.setFocusable(false);
		tvServemodel.setText("");
		tvServetype.setText("");
		tvServetitle.setText("");
		tvStarttime.setText("");
		tvEndtime.setText("");
		tvServeContent.setText("");
		rlBntAction.setVisibility(View.VISIBLE);
		btnClose.setVisibility(View.INVISIBLE);
		btnSave.setVisibility(View.VISIBLE);
		btnCancel.setVisibility(View.VISIBLE);
		btnEdit.setVisibility(View.INVISIBLE);
	}

	/**
	 * 查看服务记录
	 */
	private void showTextValue(ReturnCustomer customer){

		// 联络渠道转义成文字
//			String servemode = Catevalue.getName(customer.getSERVEMODE(),
//					Catevalue.serveModelID,
//					Catevalue.serveModelStr);
		String servemode = NewCatevalue.getLabel(this, NewCatevalue.serveMode, customer.getSERVEMODE());

		// 服务类型
//			String servetype = Catevalue.getName(customer.getSERVETYPE(),
//					Catevalue.serveTypeID,
//					Catevalue.serveTypeStr);
		String servetype = NewCatevalue.getLabel(this, NewCatevalue.serveType,customer.getSERVETYPE());

		// 执行状态
		String csstatus = NewCatevalue.getLabel(this, NewCatevalue.csStatus, customer.getCSSTATUS());

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

		/*view 时  不可编辑 隐藏编辑框 */
		etCusname.setVisibility(View.GONE);
		btnSearch.setVisibility(View.GONE);
		grpServemodel.setVisibility(View.GONE);
		grpServetype.setVisibility(View.GONE);
		etServecontent.setVisibility(View.GONE);
		etServetitle.setVisibility(View.GONE);
		etStarttime.setVisibility(View.GONE);
		etEndtime.setVisibility(View.GONE);
		rlBntAction.setVisibility(View.INVISIBLE);
		btnEdit.setVisibility(View.VISIBLE);
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
			LogUtils.d("客户姓名："+custmerName);
			try {
				custmerName = Tool.getChineseEncode(custmerName); // 中文转码
				JSONObject jsinfo = new JSONObject();
				jsinfo.put("CUSTID", "");
				jsinfo.put("CUSTNAME", custmerName);
				jsinfo.put("USERNAME", "");
				jsinfo.put("PHONE_NO", "");
				jsinfo.put("BRNAME", "");
				jsinfo.put("CARDNUMBER", "");
				String info = "&offset=" + offset + "&size=20" + "&spareOne=" + staID
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
				toast("系统请求错误");
				break;

			// 解析返回结果列表
			case INDEX_P_UPDATEINFO:
			case INDEX_C_UPDATEINFO:
			case INDEX_P_ADDNEWRETURNCUS:
			case INDEX_C_ADDNEWRETURNCUS:
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
				toast("操作失败! 错误:");
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
			return;
		}
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
	 * 添加新的服务记录
	 * @param cus
	 */
	private void addNewReturncus(ReturnCustomer cus){

		if (FormatUtil.isNumeric(cus.getCUSTNAME())){
			toast("【客户名称】不正确！");
		}else {
			if (!touchFlag){//查询到多条是没选择客户，直接点击保存时
				toast("请先选择客户信息");//2017.7.19改
			}else {
				if (Tool.haveNet(ReturnCusUpdateActivity.this)) {
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
//					LogUtils.d(cus.getCUSTNAME() + "**********************\n" + info);
					HCon = RequestInfo(this, Constants.requestType.Other, requestType, info, index, false);
				}
			}
		}
	}

	/**
	 * 修改客户服务信息
	 * @param cus
	 */
	private void updateReturncusInfo(ReturnCustomer cus){
		if (Tool.haveNet(ReturnCusUpdateActivity.this)) {
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

	private void loading() {
		btnSearch.setEnabled(false);
		layLoading.setVisibility(View.VISIBLE);
		layNodatas.setVisibility(View.GONE);
//        lvResult.setVisibility(View.GONE);
		if (offset == 1) {
			ptrl.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			// 客户姓名模糊查询
			case R.id.returncus_btn_search:
				offset = 1;

				cusNameStr = etCusname.getText().toString().trim();
				if(TextUtils.isEmpty(cusNameStr)){
					toast("抱歉，【客户名称】为空无法查询！");
				}else{
					//查询前 先清空列表//查询前 先清空列表
					forCommonResults.clear();
					forPersonResults.clear();
					llPull.setVisibility(View.VISIBLE);
					closeKeyboard();
					//queryReturncusID(cusNameStr);
					loading();
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
			// 取消对话框
			case R.id.returncus_cancel:
				showTextValue(customer);
				break;
			//关闭按钮
			case R.id.returncus_close:
				tempCus = saveReturncusInfo();
				// 如果是编辑原来ListItem数据，则不做临时保存
				if(customer != null){
					tempCus = null;
				}
				finish();
				break;
			//编辑按钮
			case R.id.returncus_edit:
				isAddNewrecode = false;
//				showDialogView(1);
				showEditValue(customer);
				break;

			default:
				break;
		}
	}
}