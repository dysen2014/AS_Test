package com.pactera.financialmanager.ui.customermanager;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.adapter.ArchivingItemAdapter;
import com.pactera.financialmanager.adapter.ArchivingItemAdapter.OnRightItemClickListener;
import com.pactera.financialmanager.adapter.SpinnerAdapter;
import com.pactera.financialmanager.adapter.SpinnerAdapter.CallBackItemClickListener;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog.OnDateSetListener;
import com.pactera.financialmanager.ui.DeleteDialog;
import com.pactera.financialmanager.ui.DeleteDialog.IsDeleteListener;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.SwipeListView;
import com.pactera.financialmanager.ui.model.ItemModel;
import com.pactera.financialmanager.ui.model.OtherBankDepositInfo;
import com.pactera.financialmanager.ui.model.OtherBankLiCaiInfo;
import com.pactera.financialmanager.ui.model.OtherBankLoanInfo;
import com.pactera.financialmanager.ui.model.OtherBankProductInfo;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.Constants.requestType;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 他行业务往来fragment
 */

public class OtherBankFragment extends ParentFragment implements
		OnClickListener {
	Button otherbank_nextbtn;
	SwipeListView otherproduct_list;// 行外产品签约(4个元素)
	SwipeListView otherlicai_list;// 行外理财产品(6个元素)
	SwipeListView otherdeposit_list;// 行外存款(5个元素)
	SwipeListView otherloan_list;// 行外贷款(6个元素)
	ImageView creat_otherproduct;
	ImageView creat_otherlicai;
	ImageView creat_otherdeposit;
	ImageView creat_otherloan;
	/**
	 * 行外产品签约
	 */
	EditText otherproduct_name;// 产品名称
	TextView otherproduct_type;// 产品类型
	TextView otherproduct_bank;// 签约银行
	TextView otherproduct_time;// 签约时间
	EditText otherProduct_phone;// 签约电话
	Button otherproduct_btn_ok;// 确定
	Button otherproduct_btn_cancel;// 取消

	TextView otherproduct_title;// 标题
	LinearLayout otherproduct_edit_ll;// 编辑按钮
	TextView otherproduct_edit_text;// 编辑文字
	String[] otherProductTitle = { "创建行外产品签约", "查看行外产品签约", "编辑行外产品签约" };// 标题名称
	Constants.requestType otherProductRType = requestType.Search;// 默认当前状态为查询
	boolean otherProductIsEdit = false;
	static OtherBankProductInfo tempProduct;

	/**
	 * 行外贷款
	 */
	TextView otherloan_type;// 贷款方式
	EditText otherloan_datelines;// 贷款期限
	EditText otherloan_rate;// 贷款利率
	EditText otherloan_money;// 贷款金额
	TextView otherloan_endday;// 还款日期
	TextView otherloan_bank;// 贷款银行
	TextView otherloan_reminddate;// 提醒日期
	Button otherloan_btn_ok;// 确认
	Button otherloan_btn_cancel;// 取消

	TextView otherloan_title;// 标题
	LinearLayout otherloan_edit_ll;// 编辑按钮
	TextView otherloan_edit_text;// 编辑文字
	String[] otherLoanTitle = { "创建行外贷款信息", "查看行外贷款信息", "编辑行外贷款信息" };// 标题名称
	Constants.requestType otherLoanRType = requestType.Search;// 默认当前状态为查询
	boolean otherLoanIsEdit = false;
	static OtherBankLoanInfo tempLoan;

	/**
	 * 行外存款
	 */
	RadioGroup otherdeposit_rg;// 存款类型
	EditText otherdeposit_money;// 存款金额
	TextView otherdeposit_dateline;// 存钱期限
	TextView otherdeposit_enddate;// 到期日
	TextView otherdeposit_bank;// 存款银行
	TextView otherdeposit_reminddate;// 提示日期
	Button otherdeposit_btn_ok;// 确认
	Button otherdeposit_btn_cancel;// 取消
	RadioButton otherdeposit_rb_1;// 活期按钮
	RadioButton otherdeposit_rb_2;// 活期按钮

	TextView otherDeposit_title;// 标题
	LinearLayout otherDeposit_edit_ll;// 编辑按钮
	TextView otherDeposit_edit_text;// 编辑文字
	String[] otherDepositTitle = { "创建行外存款信息", "查看行外存款信息", "编辑行外存款信息" };// 标题名称
	Constants.requestType otherDepositRType = requestType.Search;// 默认当前状态为查询
	boolean otherDepositIsEdit = false;
	static OtherBankDepositInfo tempDeposit;

	/**
	 * 行外理财产品
	 */
	EditText otherlicai_name;// 理财产品名称
	EditText otherlicai_money;// 购买金额
	TextView otherlicai_buyday;// 购买时间
	TextView otherlicai_enddate;// 到期时间
	TextView otherlicai_reminddate;// 提醒时间
	EditText otherlicai_rate;// 预计收益率
	Button otherlicai_btn_ok;// 确认按钮
	Button otherlicai_btn_cancel;// 取消

	TextView otherlicai_title;// 标题
	LinearLayout otherlicai_edit_ll;// 编辑按钮
	TextView otherlicai_edit_text;// 编辑文字
	String[] otherLicaiTitle = { "创建行外理财信息", "查看行外理财信息", "编辑行外理财信息" };// 标题名称
	Constants.requestType otherLicaiRType = requestType.Search;// 默认当前状态为查询
	boolean otherLicaiIsEdit = false;
	static OtherBankLiCaiInfo tempLicai;

	// 弹出框
	private Dialog popup_otherproduct;
	private Dialog popup_otherdeposit;
	private Dialog popup_otherloan;
	private Dialog popup_otherlicai;

	private LayoutInflater factory;

	/**
	 * 各种码值
	 */
	private String otherdeposit_datelineID = "";
	private String otherdeposit_bankID = "";
	private String otherloan_typeID = "";
	private String otherloan_bankID = "";
	private String otherproduct_bankID = "";
	private String otherproduct_typeID = "";
	private String IndexRadio = "0";// (0为活期，1为定期)

	ArrayList<OtherBankLiCaiInfo> LiCaiList = new ArrayList<OtherBankLiCaiInfo>();
	ArrayList<OtherBankLoanInfo> LoanList = new ArrayList<OtherBankLoanInfo>();
	ArrayList<OtherBankProductInfo> ProductList = new ArrayList<OtherBankProductInfo>();
	ArrayList<OtherBankDepositInfo> DepositList = new ArrayList<OtherBankDepositInfo>();

	private HConnection HCon;
	private HConnection getLiCaiHCon;
	private HConnection getLoanHCon;
	private HConnection getProductHCon;
	private HConnection getDepositHCon;

	private final int creatLiCaiInfo = 1;
	private final int getLiCaiInfo = 2;
	private final int creatLoanInfo = 3;
	private final int getLoanInfo = 4;
	private final int creatProductInfo = 5;
	private final int getProductInfo = 6;
	private final int creatDepositInfo = 7;
	private final int getDepositInfo = 8;
	private final int delLiCaiInfo = 9;
	private final int delLoanInfo = 10;
	private final int delProductInfo = 11;
	private final int delDepositInfo = 12;

	DeleteDialog deleteDialog;
	String[] comTitle = { "编辑", "取消" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_business_otherbank, null);
		setView(view);
		setListener();
		getProductInfo();
		getLiCaiInfo();
		getLoanInfo();
		getDepositInfo();
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.business_otherbank_nextbtn:
			Message msg = BusinessFragment.theBusiness.handler.obtainMessage();
			Bundle data = new Bundle();
			data.putString("value", "" + BusinessFragment.NextIndex);
			msg.setData(data);
			BusinessFragment.theBusiness.handler.sendMessage(msg);
			break;
		case R.id.business_otherbank_creat_otherproduct:
			otherProductRType = requestType.Insert;
			showOtherProductView(otherProductRType);
			break;
		case R.id.business_otherbank_creat_otherlicai:
			otherLicaiRType = requestType.Insert;
			showOtherLicaiView(otherLicaiRType);
			break;
		case R.id.business_otherbank_creat_otherdeposit:
			otherDepositRType = requestType.Insert;
			showOtherDepositView(otherDepositRType);
			break;
		case R.id.business_otherbank_creat_otherloan:
			otherLoanRType = requestType.Insert;
			showOtherLoanView(otherLoanRType);
			break;
		case R.id.creat_otherlicai_btn_ok:
			creatLicai();
			break;
		case R.id.creat_otherloan_btn_ok:
			creatLoan();
			break;
		case R.id.creat_otherdeposit_btn_ok:
			creatDeposit();
			break;
		case R.id.creat_otherproduct_btn_ok:
			creatProduct();
			break;
		case R.id.creat_otherlicai_btn_cancel:
			if (popup_otherlicai != null) {
				popup_otherlicai.dismiss();
			}
			break;

		case R.id.creat_otherloan_btn_cancel:
			if (popup_otherloan != null) {
				popup_otherloan.dismiss();
			}
			break;
		case R.id.creat_otherdeposit_btn_cancel:
			if (popup_otherdeposit != null) {
				popup_otherdeposit.dismiss();
			}
			break;
		case R.id.creat_otherproduct_btn_cancel:
			if (popup_otherproduct != null) {
				popup_otherproduct.dismiss();
			}
			break;
		case R.id.creat_otherdeposit_dateline:// 存款期限
			if (otherDepositIsEdit) {

				SpinnerAdapter.showSelectDialog(getActivity(),
						NewCatevalue.depositPeriod, otherdeposit_dateline,
						new CallBackItemClickListener() {

							@Override
							public void CallBackItemClick(String spinnerID) {
								// TODO Auto-generated method stub
								otherdeposit_datelineID = spinnerID;
							}
						});
			}
			break;
		case R.id.creat_otherdeposit_bank:// 存款银行
			if (otherDepositIsEdit) {
				SpinnerAdapter.showSelectDialog(getActivity(),
						NewCatevalue.signBank, otherdeposit_bank,
						new CallBackItemClickListener() {

							@Override
							public void CallBackItemClick(String spinnerID) {
								// TODO Auto-generated method stub
								otherdeposit_bankID = spinnerID;
							}
						});
			}
			break;
		case R.id.creat_otherdeposit_enddate:// 到期日
			if (otherDepositIsEdit) {
				DatePickerShow(new OnDateSetListener() {

					@Override
					public void onDateSet(String year, String month, String day) {
						// TODO Auto-generated method stub
						String data = year + "-" + month+ "-" + day;
						otherdeposit_enddate.setText(data);
					}
				});
			}
			break;
		case R.id.creat_otherdeposit_reminddate:// 存款提示日期
			if (otherDepositIsEdit) {
				DatePickerShow(new OnDateSetListener() {

					@Override
					public void onDateSet(String year, String month, String day) {
						// TODO Auto-generated method stub
						String data = year + "-" + month + "-" + day;
						otherdeposit_reminddate.setText(data);
					}
				});
			}
			break;
		case R.id.creat_otherlicai_buyday:// 理财购买时间
			if (otherLicaiIsEdit) {
				DatePickerShow(new OnDateSetListener() {

					@Override
					public void onDateSet(String year, String month, String day) {
						// TODO Auto-generated method stub
						String data = year + "-" +month + "-" + day;
						otherlicai_buyday.setText(data);
					}
				});
			}
			break;
		case R.id.creat_otherlicai_enddate:// 理财到期时间
			if (otherLicaiIsEdit) {
				DatePickerShow(new OnDateSetListener() {

					@Override
					public void onDateSet(String year, String month, String day) {
						// TODO Auto-generated method stub
						String data = year + "-" + month + "-" + day;
						otherlicai_enddate.setText(data);
					}
				});
			}
			break;
		case R.id.creat_otherlicai_reminddate:// 理财提醒时间
			if (otherLicaiIsEdit) {
				DatePickerShow(new OnDateSetListener() {

					@Override
					public void onDateSet(String year, String month, String day) {
						// TODO Auto-generated method stub
						String data = year + "-" + month + "-" + day;
						otherlicai_reminddate.setText(data);
					}
				});
			}
			break;
		case R.id.creat_otherloan_type:// 贷款方式
			if (otherLoanIsEdit) {
				SpinnerAdapter.showSelectDialog(getActivity(),
						NewCatevalue.loanType, otherloan_type,
						new CallBackItemClickListener() {

							@Override
							public void CallBackItemClick(String spinnerID) {
								// TODO Auto-generated method stub
								otherloan_typeID = spinnerID;
							}
						});
			}
			break;
		case R.id.creat_otherloan_endday:// 还款日期
			if (otherLoanIsEdit) {
				DatePickerShow(new OnDateSetListener() {

					@Override
					public void onDateSet(String year, String month, String day) {
						// TODO Auto-generated method stub
						String data = year + "-" + month + "-" + day;
						otherloan_endday.setText(data);
					}
				});
			}
			break;
		case R.id.creat_otherloan_bank:// 贷款银行
			if (otherLoanIsEdit) {
				SpinnerAdapter.showSelectDialog(getActivity(),
						NewCatevalue.signBank, otherloan_bank,
						new CallBackItemClickListener() {

							@Override
							public void CallBackItemClick(String spinnerID) {
								// TODO Auto-generated method stub
								otherloan_bankID = spinnerID;
								Log.i("1111111", "otherloan_bankID"
										+ otherloan_bankID);
							}
						});
			}
			break;
		case R.id.creat_otherloan_reminddate:// 贷款提醒日期
			if (otherLoanIsEdit) {
				DatePickerShow(new OnDateSetListener() {

					@Override
					public void onDateSet(String year, String month, String day) {
						// TODO Auto-generated method stub
						String data = year + "-" + month + "-" + day;
						otherloan_reminddate.setText(data);
					}
				});
			}
			break;
		case R.id.creat_otherproduct_type:// 签约类型
			if (otherProductIsEdit) {
				SpinnerAdapter.showSelectDialog(getActivity(),
						NewCatevalue.signType, otherproduct_type,
						new CallBackItemClickListener() {

							@Override
							public void CallBackItemClick(String spinnerID) {
								// TODO Auto-generated method stub
								otherproduct_typeID = spinnerID;
							}
						});
			}
			break;
		case R.id.creat_otherproduct_bank:// 产品签约银行
			if (otherProductIsEdit) {
				SpinnerAdapter.showSelectDialog(getActivity(),
						NewCatevalue.signBank, otherproduct_bank,
						new CallBackItemClickListener() {

							@Override
							public void CallBackItemClick(String spinnerID) {
								// TODO Auto-generated method stub
								otherproduct_bankID = spinnerID;
							}
						});
			}
			break;
		case R.id.creat_otherproduct_time:// 产品签约时间
			if (otherProductIsEdit) {
				DatePickerShow(new OnDateSetListener() {

					@Override
					public void onDateSet(String year, String month, String day) {
						// TODO Auto-generated method stub
						String data = year + "-" + month + "-" + day;
						otherproduct_time.setText(data);
					}
				});
			}
			break;
		case R.id.otherproduct_edit_ll:
			otherProductIsEdit = !otherProductIsEdit;
			setProductView(otherProductIsEdit);
			break;
		case R.id.otherloan_edit_ll:
			otherLoanIsEdit = !otherLoanIsEdit;
			setLoanView(otherLoanIsEdit);
			break;
		case R.id.otherdeposit_edit_ll:
			otherDepositIsEdit = !otherDepositIsEdit;
			setDepositView(otherDepositIsEdit);
			break;
		case R.id.otherlicai_edit_ll:
			otherLicaiIsEdit = !otherLicaiIsEdit;
			setLicaiView(otherLicaiIsEdit);
			break;
		default:
			break;
		}
	}

	/**
	 * 他行存款
	 */
	private void creatDeposit() {

		OtherBankDepositInfo newDeposit = new OtherBankDepositInfo();

		if (otherDepositRType == requestType.Search) {
			newDeposit = tempDeposit;
		}

		newDeposit.custid = PersonArchiving.custID;// 客户号
		newDeposit.deposit_type = IndexRadio;// 存款类型
		newDeposit.deposit_amount = Tool.getTextValue(otherdeposit_money);// 存款余额
		newDeposit.deposit_period = otherdeposit_datelineID;// 存款期限
		newDeposit.deposit_maturity_date = otherdeposit_enddate.getText().toString();// 存款到期日
		newDeposit.deposit_bank = otherdeposit_bankID;// 存款行
		newDeposit.remind_date = otherdeposit_reminddate.getText().toString();// 提醒日期

		if (otherDepositRType == requestType.Insert) {
			HCon = RequestInfo(OtherBankFragment.this,
					Constants.requestType.Insert,
					InterfaceInfo.OtherBank_Deposit_Creat,
					newDeposit.toString(), creatDepositInfo);

		} else if (otherDepositRType == requestType.Search) {
			if (otherDepositIsEdit) {
				HCon = RequestInfo(OtherBankFragment.this,
						Constants.requestType.Update,
						InterfaceInfo.OtherBank_Deposit_Update,
						newDeposit.toString(), creatDepositInfo);
			} else {
				if (popup_otherdeposit != null) {
					popup_otherdeposit.dismiss();
				}
			}

		}

	}

	/**
	 * 
	 * 行外产品
	 */
	private void creatProduct() {
		// TODO Auto-generated method stub
		OtherBankProductInfo newProduct = new OtherBankProductInfo();
		if (otherProductRType == requestType.Search) {
			newProduct = tempProduct;
		}
		newProduct.CUSTID = PersonArchiving.custID;// 客户号
		newProduct.SIGN_NAME = Tool.getTextValue(otherproduct_name);// 签约名称
		newProduct.SIGN_PRD = otherproduct_typeID;// 签约类型
		newProduct.SIGN_ORG = otherproduct_bankID;// 签约银行
		newProduct.SIGN_TIME = otherproduct_time.getText().toString();// 签约日期
		newProduct.SIGN_PHONE = Tool.getTextValue(otherProduct_phone);

		if (otherProductRType == requestType.Insert) {
			HCon = RequestInfo(OtherBankFragment.this,
					Constants.requestType.Insert,
					InterfaceInfo.OtherBank_Product_Creat,
					newProduct.toString(), creatProductInfo);

		} else if (otherProductRType == requestType.Search) {
			if (otherProductIsEdit) {
				HCon = RequestInfo(OtherBankFragment.this,
						Constants.requestType.Update,
						InterfaceInfo.OtherBank_Product_Update,
						newProduct.toString(), creatProductInfo);
			} else {
				if (popup_otherproduct != null) {
					popup_otherproduct.dismiss();
				}
			}

		}
	}

	/**
	 * 行外贷款
	 */
	private void creatLoan() {
		// TODO Auto-generated method stub
		OtherBankLoanInfo newLoan = new OtherBankLoanInfo();
		if (otherLoanRType == requestType.Search) {
			newLoan = tempLoan;
		}

		newLoan.custid = PersonArchiving.custID;// 客户号
		newLoan.loan_type = otherloan_typeID;// 贷款方式
		newLoan.loan_period = Tool.getTextValue(otherloan_datelines);// 贷款期限
		newLoan.loan_rate = Tool.getTextValue(otherloan_rate);// 贷款利率
		newLoan.loan_amount = Tool.getTextValue(otherloan_money);// 贷款金额
		newLoan.loan_bank = otherloan_bankID;// 贷款行
		newLoan.remind_date = otherloan_reminddate.getText().toString();// 提醒日期
		newLoan.CREDIT_EXPIRY_DATE = otherloan_endday.getText().toString();// 提醒日期

		if (otherLoanRType == requestType.Insert) {
			HCon = RequestInfo(OtherBankFragment.this,
					Constants.requestType.Insert,
					InterfaceInfo.OtherBank_Loan_Creat, newLoan.toString(),
					creatLoanInfo);

		} else if (otherLoanRType == requestType.Search) {
			if (otherLoanIsEdit) {
				HCon = RequestInfo(OtherBankFragment.this,
						Constants.requestType.Update,
						InterfaceInfo.OtherBank_Loan_Update,
						newLoan.toString(), creatLoanInfo);
			} else {
				if (popup_otherloan != null) {
					popup_otherloan.dismiss();
				}
			}

		}

	}

	/**
	 * 行外理财
	 */
	private void creatLicai() {
		// TODO Auto-generated method stub
		OtherBankLiCaiInfo newLicai = new OtherBankLiCaiInfo();

		if (otherLicaiRType == requestType.Search) {
			newLicai = tempLicai;
		}
		newLicai.custid = PersonArchiving.custID;
		newLicai.prd_name = Tool.getTextValue(otherlicai_name);// 产品名称
		newLicai.exp_amount = Tool.getTextValue(otherlicai_money);// 认购金额
		newLicai.exp_date = otherlicai_buyday.getText().toString();// 认购日期
		newLicai.end_date = otherlicai_enddate.getText().toString();// 到期日
		newLicai.remind_date = otherlicai_reminddate.getText().toString();// 提醒日期
		newLicai.exp_profit = Tool.getTextValue(otherlicai_rate);// 预期收益率

		if (otherLicaiRType == requestType.Insert) {
			HCon = RequestInfo(OtherBankFragment.this,
					Constants.requestType.Insert,
					InterfaceInfo.OtherBank_LiCai_Creat, newLicai.toString(),
					creatLiCaiInfo);

		} else if (otherLicaiRType == requestType.Search) {
			if (otherLicaiIsEdit) {
				HCon = RequestInfo(OtherBankFragment.this,
						Constants.requestType.Update,
						InterfaceInfo.OtherBank_LiCai_Update,
						newLicai.toString(), creatLiCaiInfo);
			} else {
				if (popup_otherlicai != null) {
					popup_otherlicai.dismiss();
				}
			}

		}

	}

	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		HResponse res = null;
		JSONObject tmpJsonObject = null;
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			break;
		case creatLiCaiInfo:
		case creatProductInfo:
		case creatLoanInfo:
		case creatDepositInfo:
		case delLiCaiInfo:
		case delLoanInfo:
		case delProductInfo:
		case delDepositInfo:
			res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);

			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readJson(tmpJsonObject, connectionIndex);
			break;
		case getLiCaiInfo:
			res = getLiCaiHCon
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);

			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readJson(tmpJsonObject, connectionIndex);
			break;
		case getLoanInfo:
			res = getLoanHCon
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);

			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readJson(tmpJsonObject, connectionIndex);
			break;
		case getProductInfo:
			res = getProductHCon
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);

			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readJson(tmpJsonObject, connectionIndex);
			break;
		case getDepositInfo:
			res = getDepositHCon
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);

			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readJson(tmpJsonObject, connectionIndex);
			break;
		}

	}

	private void readJson(JSONObject tmpJsonObject, int connectionIndex) {
		// TODO Auto-generated method stub
		String retCode = Tool.getJsonValue(tmpJsonObject, "retCode");

		if (retCode.equals("0000")) {
			try {
				if (connectionIndex == creatLiCaiInfo
						|| connectionIndex == delLiCaiInfo) {
					getLiCaiInfo();
				} else if (connectionIndex == getLiCaiInfo) {
					LiCaiList.clear();
					JSONArray theInfo = tmpJsonObject.getJSONArray("group");
					if (theInfo.length() < 0) {
						return;
					} else {
						for (int i = 0; i < theInfo.length(); i++) {
							JSONObject temps = (JSONObject) theInfo.opt(i);
							OtherBankLiCaiInfo tempsLicai = new OtherBankLiCaiInfo();
							tempsLicai.custid = PersonArchiving.custID;// 客户号
							tempsLicai.pkid = Tool.getJsonValue(temps, "pkid");// 主键
							tempsLicai.prd_name = Tool.getJsonValue(temps,
									"prd_name");// 产品名称
							tempsLicai.exp_amount = Tool.getJsonValue(temps,
									"exp_amount");// 认购金额
							tempsLicai.exp_date = Tool.getJsonValue(temps,
									"exp_date");// 认购日期
							tempsLicai.end_date = Tool.getJsonValue(temps,
									"end_date");// 到期日
							tempsLicai.remind_date = Tool.getJsonValue(temps,
									"remind_date");// 提醒日期
							tempsLicai.exp_profit = Tool.getJsonValue(temps,
									"exp_profit");// 预期收益率
							LiCaiList.add(tempsLicai);
						}

					}

				} else if (connectionIndex == creatLoanInfo
						|| connectionIndex == delLoanInfo) {
					getLoanInfo();
				} else if (connectionIndex == getLoanInfo) {
					LoanList.clear();
					JSONArray theInfo = tmpJsonObject.getJSONArray("group");
					if (theInfo.length() < 0) {
						return;
					} else {
						for (int i = 0; i < theInfo.length(); i++) {
							JSONObject temps = (JSONObject) theInfo.opt(i);
							OtherBankLoanInfo tempsLoan = new OtherBankLoanInfo();
							tempsLoan.custid = PersonArchiving.custID;// 客户号
							tempsLoan.pkid = Tool.getJsonValue(temps, "pkid");// 主键
							tempsLoan.loan_type = Tool.getJsonValue(temps,
									"loan_type");// 贷款方式
							tempsLoan.loan_period = Tool.getJsonValue(temps,
									"loan_period");// 贷款期限
							tempsLoan.loan_rate = Tool.getJsonValue(temps,
									"loan_rate");// 贷款利率
							tempsLoan.loan_amount = Tool.getJsonValue(temps,
									"loan_amount");// 贷款金额
							tempsLoan.loan_bank = Tool.getJsonValue(temps,
									"loan_bank");// 贷款行
							tempsLoan.remind_date = Tool.getJsonValue(temps,
									"remind_date");// 提醒日期
							tempsLoan.CREDIT_EXPIRY_DATE = Tool.getJsonValue(
									temps, "CREDIT_EXPIRY_DATE");// 到期日期
							LoanList.add(tempsLoan);
						}

					}

				} else if (connectionIndex == creatProductInfo
						|| connectionIndex == delProductInfo) {
					getProductInfo();
				} else if (connectionIndex == getProductInfo) {
					ProductList.clear();
					JSONArray theInfo = tmpJsonObject.getJSONArray("group");
					if (theInfo.length() < 0) {
						return;
					} else {
						for (int i = 0; i < theInfo.length(); i++) {
							JSONObject temps = (JSONObject) theInfo.opt(i);
							OtherBankProductInfo tempsPro = new OtherBankProductInfo();
							tempsPro.CUSTID = PersonArchiving.custID;// 客户号
							tempsPro.pkid = Tool.getJsonValue(temps, "PKID");// 主键
							tempsPro.SIGN_NAME = Tool.getJsonValue(temps,
									"SIGN_NAME");// 签约名称
							tempsPro.SIGN_PRD = Tool.getJsonValue(temps,
									"SIGN_PRD");// 签约类型
							tempsPro.SIGN_ORG = Tool.getJsonValue(temps,
									"SIGN_ORG");// 签约银行
							tempsPro.SIGN_TIME = Tool.getJsonValue(temps,
									"SIGN_TIME");// 签约日期
							tempsPro.SIGN_PHONE = Tool.getJsonValue(temps,
									"SIGN_PHONE");// 签约电话
							ProductList.add(tempsPro);
						}

					}

				} else if (connectionIndex == creatDepositInfo
						|| connectionIndex == delDepositInfo) {
					getDepositInfo();
				} else if (connectionIndex == getDepositInfo) {
					DepositList.clear();
					JSONArray theInfo = tmpJsonObject.getJSONArray("group");
					if (theInfo.length() < 0) {
						return;
					} else {
						for (int i = 0; i < theInfo.length(); i++) {
							JSONObject temps = (JSONObject) theInfo.opt(i);
							OtherBankDepositInfo tempsDeposit = new OtherBankDepositInfo();
							tempsDeposit.custid = PersonArchiving.custID;// 客户号
							tempsDeposit.pkid = Tool
									.getJsonValue(temps, "pkid");// 主键
							tempsDeposit.deposit_type = Tool.getJsonValue(
									temps, "deposit_type");// 存款类型
							tempsDeposit.deposit_amount = Tool.getJsonValue(
									temps, "deposit_amount");// 存款余额
							tempsDeposit.deposit_period = Tool.getJsonValue(
									temps, "deposit_period");// 存款期限
							tempsDeposit.deposit_maturity_date = Tool
									.getJsonValue(temps,
											"deposit_maturity_date");// 存款到期日
							tempsDeposit.deposit_bank = Tool.getJsonValue(
									temps, "deposit_bank");// 存款行
							tempsDeposit.remind_date = Tool.getJsonValue(temps,
									"remind_date");// 提醒日期

							DepositList.add(tempsDeposit);
						}

					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 进行数据刷新

		} else {
			Toast.makeText(getActivity(), "数据错误！", Toast.LENGTH_SHORT).show();
		}

		refreshInfo(connectionIndex);
		if (popup_otherlicai != null) {
			popup_otherlicai.dismiss();
		}
		if (popup_otherloan != null) {
			popup_otherloan.dismiss();
		}
		if (popup_otherdeposit != null) {
			popup_otherdeposit.dismiss();
		}
		if (popup_otherproduct != null) {
			popup_otherproduct.dismiss();
		}

	}

	/**
	 * 获取他行存款
	 */
	private void getDepositInfo() {
		getDepositHCon = RequestInfo(OtherBankFragment.this,
				Constants.requestType.Search,
				InterfaceInfo.OtherBank_Deposit_Get, PersonArchiving.custID,
				getDepositInfo);
	}

	/**
	 * 获取产品信息
	 */
	private void getProductInfo() {
		getProductHCon = RequestInfo(OtherBankFragment.this,
				Constants.requestType.Search,
				InterfaceInfo.OtherBank_Product_Get, PersonArchiving.custID,
				getProductInfo);
	}

	/**
	 * 获取行外贷款信息
	 */
	private void getLoanInfo() {
		// TODO Auto-generated method stub
		getLoanHCon = RequestInfo(OtherBankFragment.this,
				Constants.requestType.Search, InterfaceInfo.OtherBank_Loan_Get,
				PersonArchiving.custID, getLoanInfo);
	}

	/**
	 * 获取行外理财信息
	 */
	private void getLiCaiInfo() {

		getLiCaiHCon = RequestInfo(OtherBankFragment.this,
				Constants.requestType.Search,
				InterfaceInfo.OtherBank_LiCai_Get, PersonArchiving.custID,
				getLiCaiInfo);
	}

	/**
	 * List数据刷新
	 * 
	 * @param theIndex
	 */
	private void refreshInfo(int theIndex) {
		ArrayList<ItemModel> ViewInfo = new ArrayList<ItemModel>();
		switch (theIndex) {
		case getLiCaiInfo:
			if (LiCaiList.size() > 0) {

				for (OtherBankLiCaiInfo item : LiCaiList) {
					ItemModel theInfo = new ItemModel();
					theInfo.theItemModel[0] = item.prd_name;
					theInfo.theItemModel[1] = item.exp_amount;
					theInfo.theItemModel[2] = item.exp_date;
					theInfo.theItemModel[3] = item.end_date;
					theInfo.theItemModel[4] = item.remind_date;
					theInfo.theItemModel[5] = item.exp_profit;
					theInfo.pkid = item.pkid;
					ViewInfo.add(theInfo);
				}
				final ArchivingItemAdapter ViewAdapter_1 = new ArchivingItemAdapter(
						getActivity(), otherlicai_list.getRightViewWidth(),
						ViewInfo, 6, new OnRightItemClickListener() {

							@Override
							public void onRightItemClick(View v,
									final String pkid) {
								deleteDialog = new DeleteDialog(getActivity(),
										new IsDeleteListener() {

											@Override
											public void onClickDeleteListener(
													boolean isDelete) {
												if (isDelete) {

													HCon = RequestInfo(
															OtherBankFragment.this,
															Constants.requestType.Delete,
															InterfaceInfo.OtherBank_LiCai_Delete,
															pkid, delLiCaiInfo);
												}
											}
										});
								deleteDialog.show();
							}

						});
				otherlicai_list.setPullstatus(false, true);// 设置是否可以上下拉动
				otherlicai_list.setAdapter(ViewAdapter_1);
				otherlicai_list
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								ItemModel theInfo = ViewAdapter_1
										.getItem(position);

								for (OtherBankLiCaiInfo item : LiCaiList) {
									if (item.pkid.equals(theInfo.pkid)) {
										tempLicai = item;
										otherLicaiRType = requestType.Search;
										showOtherLicaiView(otherLicaiRType);
									}
								}
							}
						});
				ViewAdapter_1.setListViewHeight(otherlicai_list);
			} else {
				makeData(theIndex);
			}
			break;
		case getLoanInfo:
			if (LoanList.size() > 0) {
				for (OtherBankLoanInfo item : LoanList) {
					ItemModel theInfo = new ItemModel();
					theInfo.theItemModel[0] = NewCatevalue.getLabel(
							getActivity(), NewCatevalue.loanType,
							item.loan_type);

					theInfo.theItemModel[1] = item.loan_period;
					theInfo.theItemModel[2] = item.loan_rate;
					theInfo.theItemModel[3] = item.loan_amount;
					theInfo.theItemModel[4] = item.CREDIT_EXPIRY_DATE;
					theInfo.theItemModel[5] = NewCatevalue.getLabel(
							getActivity(), NewCatevalue.signBank,
							item.loan_bank);
					theInfo.pkid = item.pkid;
					ViewInfo.add(theInfo);
				}
				final ArchivingItemAdapter ViewAdapter_2 = new ArchivingItemAdapter(
						getActivity(), otherloan_list.getRightViewWidth(),
						ViewInfo, 6, new OnRightItemClickListener() {

							@Override
							public void onRightItemClick(View v,
									final String pkid) {
								deleteDialog = new DeleteDialog(getActivity(),
										new IsDeleteListener() {

											@Override
											public void onClickDeleteListener(
													boolean isDelete) {
												if (isDelete) {
													HCon = RequestInfo(
															OtherBankFragment.this,
															Constants.requestType.Delete,
															InterfaceInfo.OtherBank_Loan_Delete,
															pkid, delLoanInfo);
												}
											}
										});
								deleteDialog.show();
							}

						});
				otherloan_list.setPullstatus(false, true);// 设置是否可以上下拉动
				otherloan_list.setAdapter(ViewAdapter_2);
				otherloan_list
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								ItemModel theInfo = ViewAdapter_2
										.getItem(position);
								Log.i("1111111",
										"LoanList:" + LoanList.toString());
								for (OtherBankLoanInfo item : LoanList) {
									if (item.pkid.equals(theInfo.pkid)) {
										tempLoan = item;
										otherLoanRType = requestType.Search;
										showOtherLoanView(otherLoanRType);
									}
								}
							}
						});
				ViewAdapter_2.setListViewHeight(otherloan_list);
			} else {
				makeData(theIndex);
			}
			break;

		case getProductInfo:

			if (ProductList.size() > 0) {
				for (OtherBankProductInfo item : ProductList) {
					ItemModel theInfo = new ItemModel();
					theInfo.theItemModel[0] = item.SIGN_NAME;
					theInfo.theItemModel[1] = NewCatevalue.getLabel(
							getActivity(), NewCatevalue.signType,
							item.SIGN_PRD);

					theInfo.theItemModel[2] = NewCatevalue.getLabel(
							getActivity(), NewCatevalue.signBank,
							item.SIGN_ORG);
					theInfo.theItemModel[3] = item.SIGN_TIME;
					theInfo.pkid = item.pkid;
					ViewInfo.add(theInfo);
				}
				final ArchivingItemAdapter ViewAdapter_3 = new ArchivingItemAdapter(
						getActivity(), otherproduct_list.getRightViewWidth(),
						ViewInfo, 4, new OnRightItemClickListener() {

							@Override
							public void onRightItemClick(View v,
									final String pkid) {
								deleteDialog = new DeleteDialog(getActivity(),
										new IsDeleteListener() {

											@Override
											public void onClickDeleteListener(
													boolean isDelete) {
												if (isDelete) {

													HCon = RequestInfo(
															OtherBankFragment.this,
															Constants.requestType.Delete,
															InterfaceInfo.OtherBank_Product_Delete,
															pkid,
															delProductInfo);
												}
											}
										});
								deleteDialog.show();
							}

						});
				otherproduct_list.setPullstatus(false, true);// 设置是否可以上下拉动
				otherproduct_list.setAdapter(ViewAdapter_3);
				otherproduct_list
						.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								ItemModel theInfo = ViewAdapter_3
										.getItem(position);

								for (OtherBankProductInfo item : ProductList) {
									if (item.pkid.equals(theInfo.pkid)) {
										tempProduct = item;
										otherProductRType = requestType.Search;
										showOtherProductView(otherProductRType);
									}
								}
							}
						});
				ViewAdapter_3.setListViewHeight(otherproduct_list);
			} else {
				makeData(theIndex);
			}
			break;
		case getDepositInfo:
			if (DepositList.size() > 0) {
				for (OtherBankDepositInfo item : DepositList) {
					ItemModel theInfo = new ItemModel();
					theInfo.theItemModel[0] = NewCatevalue.getLabel(
							getActivity(), NewCatevalue.depositType,
							item.deposit_type);

					theInfo.theItemModel[1] = item.deposit_amount;
					theInfo.theItemModel[2] = NewCatevalue.getLabel(
							getActivity(), NewCatevalue.depositPeriod,
							item.deposit_period);
					theInfo.theItemModel[3] = item.deposit_maturity_date;

					theInfo.theItemModel[4] = NewCatevalue.getLabel(
							getActivity(), NewCatevalue.signBank,
							item.deposit_bank);
					theInfo.pkid = item.pkid;
					ViewInfo.add(theInfo);
				}
				final ArchivingItemAdapter ViewAdapter_4 = new ArchivingItemAdapter(
						getActivity(), otherdeposit_list.getRightViewWidth(),
						ViewInfo, 5, new OnRightItemClickListener() {

							@Override
							public void onRightItemClick(View v,
									final String pkid) {
								deleteDialog = new DeleteDialog(getActivity(),
										new IsDeleteListener() {

											@Override
											public void onClickDeleteListener(
													boolean isDelete) {
												if (isDelete) {

													HCon = RequestInfo(
															OtherBankFragment.this,
															Constants.requestType.Delete,
															InterfaceInfo.OtherBank_Deposit_Delete,
															pkid,
															delDepositInfo);
												}
											}
										});
								deleteDialog.show();
							}

						});
				otherdeposit_list.setPullstatus(false, true);// 设置是否可以上下拉动
				otherdeposit_list.setAdapter(ViewAdapter_4);
				otherdeposit_list
						.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								ItemModel theInfo = ViewAdapter_4
										.getItem(position);

								for (OtherBankDepositInfo item : DepositList) {
									if (item.pkid.equals(theInfo.pkid)) {
										tempDeposit = item;
										otherDepositRType = requestType.Search;
										showOtherDepositView(otherDepositRType);
									}
								}
							}
						});
				ViewAdapter_4.setListViewHeight(otherdeposit_list);
			} else {
				makeData(theIndex);
			}
			break;
		default:
			break;
		}
	}

	// 创建他行产品信息弹出框
	private void showOtherProductView(Constants.requestType theType) {
		if (popup_otherproduct == null) {
			View popupView = factory.inflate(
					R.layout.popwindow_business_otherproduct, null);
			// 创建一个PopuWidow对象
			popup_otherproduct = new Dialog(getActivity(), R.style.DialogTheme);
			popup_otherproduct.setContentView(popupView);

			// 初始化控件
			initOtherProductView(popupView);
			setOtherProductListener();
		}
		if (!popup_otherproduct.isShowing()) {
			if (theType == requestType.Insert) {
				otherProductIsEdit = true;
				clearOtherProduct();
			} else if (theType == requestType.Search) {
				otherProductIsEdit = false;
				setProductView(otherProductIsEdit);
			}
			popup_otherproduct.show();
		}
	}

	private void setProductView(boolean IsEdit) {
		// TODO Auto-generated method stub

		otherproduct_edit_ll.setVisibility(View.VISIBLE);// 编辑按钮
		otherproduct_name.setEnabled(IsEdit);
		otherproduct_type.setClickable(IsEdit);// 产品类型
		otherproduct_bank.setClickable(IsEdit);// 签约银行
		otherproduct_time.setClickable(IsEdit);// 签约时间
		otherProduct_phone.setEnabled(IsEdit);
		if (IsEdit) {
			otherproduct_title.setText(otherProductTitle[2]);
			otherproduct_edit_text.setText(comTitle[1]);
		} else {
			otherproduct_title.setText(otherProductTitle[1]);
			otherproduct_edit_text.setText(comTitle[0]);
		}

		if (tempProduct != null) {
			otherproduct_name.setText(tempProduct.SIGN_NAME);
			otherproduct_type.setText(NewCatevalue.getLabel(getActivity(),
					NewCatevalue.signType, tempProduct.SIGN_PRD));// 签约类型
			otherproduct_typeID=tempProduct.SIGN_PRD;
			otherproduct_bank.setText(NewCatevalue.getLabel(getActivity(),
					NewCatevalue.signBank, tempProduct.SIGN_ORG));// 签约银行
			otherproduct_bankID=tempProduct.SIGN_ORG;
			otherproduct_time.setText(tempProduct.SIGN_TIME);// 签约时间
			otherProduct_phone.setText(tempProduct.SIGN_PHONE);// 签约时间
		}
	}

	// 创建他行贷款信息弹出框
	private void showOtherLoanView(Constants.requestType theType) {
		if (popup_otherloan == null) {
			View popupView = factory.inflate(
					R.layout.popwindow_business_otherloan, null);
			// 创建一个PopuWidow对象
			popup_otherloan = new Dialog(getActivity(), R.style.DialogTheme);
			popup_otherloan.setContentView(popupView);
			// 初始化控件
			initOtherLoanView(popupView);
			setOtherLoanListener();
		}
		if (!popup_otherloan.isShowing()) {
			if (theType == requestType.Insert) {
				otherLoanIsEdit = true;
				clearOtherLoan();
			} else if (theType == requestType.Search) {
				otherLoanIsEdit = false;
				setLoanView(otherLoanIsEdit);
			}
			popup_otherloan.show();
		}
	}

	private void setLoanView(boolean isEdit) {
		otherloan_edit_ll.setVisibility(View.VISIBLE);
		otherloan_type.setClickable(isEdit);// 贷款方式
		otherloan_datelines.setEnabled(isEdit);// 贷款期限
		otherloan_rate.setEnabled(isEdit);// 贷款利率
		otherloan_money.setEnabled(isEdit);// 贷款金额
		otherloan_endday.setClickable(isEdit);// 还款日期
		otherloan_bank.setClickable(isEdit);// 贷款银行
		otherloan_reminddate.setClickable(isEdit);// 提醒日期

		if (isEdit) {
			otherloan_title.setText(otherLoanTitle[2]);
			otherloan_edit_text.setText(comTitle[1]);
		} else {
			otherloan_title.setText(otherLoanTitle[1]);
			otherloan_edit_text.setText(comTitle[0]);
		}

		if (tempLoan != null) {
			otherloan_type.setText(NewCatevalue.getLabel(getActivity(),
					NewCatevalue.loanType, tempLoan.loan_type));// 贷款方式
			otherloan_typeID=tempLoan.loan_type;
			otherloan_datelines.setText(tempLoan.loan_period);// 贷款期限
			otherloan_rate.setText(tempLoan.loan_rate);// 贷款利率
			otherloan_money.setText(tempLoan.loan_amount);// 贷款金额
			otherloan_endday.setText(tempLoan.CREDIT_EXPIRY_DATE);// 还款日期
			otherloan_bank.setText(NewCatevalue.getLabel(getActivity(),
					NewCatevalue.signBank, tempLoan.loan_bank));// 贷款银行
			otherloan_bankID=tempLoan.loan_bank;
			otherloan_reminddate.setText(tempLoan.remind_date);// 提醒日期
		}
	}

	// 创建他行理财信息弹出框
	private void showOtherLicaiView(Constants.requestType theType) {
		if (popup_otherlicai == null) {
			View popupView = factory.inflate(
					R.layout.popwindow_business_otherlicai, null);
			// 创建一个PopuWidow对象
			popup_otherlicai = new Dialog(getActivity(), R.style.DialogTheme);
			popup_otherlicai.setContentView(popupView);
			// 初始化控件
			initOtherLicaiView(popupView);
			setOtherLicaiListener();
		}
		if (!popup_otherlicai.isShowing()) {
			if (theType == requestType.Insert) {
				otherLicaiIsEdit = true;
				clearOtherLicai();
			} else if (theType == requestType.Search) {
				otherLicaiIsEdit = false;
				setLicaiView(otherLicaiIsEdit);
			}
			popup_otherlicai.show();
		}
	}

	// 创建理财信息弹出框
	private void setLicaiView(boolean isEdit) {
		// TODO Auto-generated method stub
		otherlicai_edit_ll.setVisibility(View.VISIBLE);
		otherlicai_name.setEnabled(isEdit);// 理财产品名称
		otherlicai_money.setEnabled(isEdit);// 购买金额
		otherlicai_buyday.setClickable(isEdit);// 购买时间
		otherlicai_enddate.setClickable(isEdit);// 到期时间
		otherlicai_reminddate.setClickable(isEdit);// 提醒时间
		otherlicai_rate.setEnabled(isEdit);// 预计收益率

		if (isEdit) {
			otherlicai_title.setText(otherLicaiTitle[2]);
			otherlicai_edit_text.setText(comTitle[1]);
		} else {
			otherlicai_title.setText(otherLicaiTitle[1]);
			otherlicai_edit_text.setText(comTitle[0]);
		}

		if (tempLicai != null) {
			otherlicai_name.setText(tempLicai.prd_name);// 理财产品名称
			otherlicai_money.setText(tempLicai.exp_amount);// 购买金额
			otherlicai_buyday.setText(tempLicai.exp_date);// 购买时间
			otherlicai_enddate.setText(tempLicai.end_date);// 到期时间
			otherlicai_reminddate.setText(tempLicai.remind_date);// 提醒时间
			otherlicai_rate.setText(tempLicai.exp_profit);// 预计收益率
		}

	}

	// 创建他行存款信息弹出框
	private void showOtherDepositView(Constants.requestType theType) {
		if (popup_otherdeposit == null) {
			View popupView = factory.inflate(
					R.layout.popwindow_business_otherdeposit, null);
			// 创建一个PopuWidow对象
			popup_otherdeposit = new Dialog(getActivity(), R.style.DialogTheme);
			popup_otherdeposit.setContentView(popupView);
			// 初始化控件
			initOtherDepositView(popupView);
			setOtherDepositListener();
		}
		if (!popup_otherdeposit.isShowing()) {

			if (theType == requestType.Insert) {
				otherDepositIsEdit = true;
				clearOtherDeposit();
			} else if (theType == requestType.Search) {
				otherDepositIsEdit = false;
				setDepositView(otherDepositIsEdit);
			}

			popup_otherdeposit.show();
		}
	}

	/**
	 * 存款设置查看和编辑状态
	 * 
	 * @param isEdit
	 */
	private void setDepositView(boolean isEdit) {
		// TODO Auto-generated method stub
		otherDeposit_edit_ll.setVisibility(View.VISIBLE);
		otherdeposit_rg.setClickable(isEdit);
		otherdeposit_money.setEnabled(isEdit);
		otherdeposit_dateline.setClickable(isEdit);// 存钱期限
		otherdeposit_enddate.setClickable(isEdit);// 到期日
		otherdeposit_bank.setClickable(isEdit);// 存款银行
		otherdeposit_reminddate.setClickable(isEdit);// 提示日期
		if (isEdit) {
			otherDeposit_title.setText(otherDepositTitle[2]);
			otherDeposit_edit_text.setText(comTitle[1]);
		} else {
			otherDeposit_title.setText(otherDepositTitle[1]);
			otherDeposit_edit_text.setText(comTitle[0]);
		}
		if (tempDeposit != null) {
			if (tempDeposit.deposit_type.equals("0")) {
				otherdeposit_rb_1.setChecked(true);
				otherdeposit_rb_2.setChecked(false);
			} else {
				otherdeposit_rb_2.setChecked(true);
				otherdeposit_rb_1.setChecked(false);
			}
			otherdeposit_reminddate.setText(tempDeposit.remind_date);
			otherdeposit_enddate.setText(tempDeposit.deposit_maturity_date);
			otherdeposit_money.setText(tempDeposit.deposit_amount);
			otherdeposit_bank.setText(NewCatevalue.getLabel(getActivity(),
					NewCatevalue.signBank, tempDeposit.deposit_bank));
			otherdeposit_bankID=tempDeposit.deposit_bank;
			
			otherdeposit_dateline.setText(NewCatevalue.getLabel(getActivity(),
					NewCatevalue.depositPeriod, tempDeposit.deposit_period));
			otherdeposit_datelineID=tempDeposit.deposit_period;
		}
	}

	/**
	 * 造一组空数据
	 */
	private void makeData(int index) {
		// TODO Auto-generated method stub

		ItemModel sixInfo = new ItemModel();
		ArrayList<ItemModel> ViewInfo = new ArrayList<ItemModel>();
		ViewInfo.add(sixInfo);
		ArchivingItemAdapter fourViewAdapter = new ArchivingItemAdapter(
				getActivity(), 0, ViewInfo, 4, null);
		ArchivingItemAdapter fiveViewAdapter = new ArchivingItemAdapter(
				getActivity(), 0, ViewInfo, 5, null);
		ArchivingItemAdapter sixViewAdapter = new ArchivingItemAdapter(
				getActivity(), 0, ViewInfo, 6, null);

		switch (index) {
		case getLiCaiInfo:
			otherlicai_list.setAdapter(sixViewAdapter);
			fourViewAdapter.setListViewHeight(otherlicai_list);
			break;
		case getLoanInfo:
			otherloan_list.setAdapter(sixViewAdapter);
			fourViewAdapter.setListViewHeight(otherloan_list);
			break;
		case getProductInfo:
			otherproduct_list.setAdapter(fourViewAdapter);
			fourViewAdapter.setListViewHeight(otherproduct_list);
			break;
		case getDepositInfo:
			otherdeposit_list.setAdapter(fiveViewAdapter);
			fourViewAdapter.setListViewHeight(otherdeposit_list);
			break;

		default:
			break;
		}

	}

	private void setOtherLicaiListener() {
		// TODO Auto-generated method stub
		otherlicai_btn_ok.setOnClickListener(this);
		otherlicai_btn_cancel.setOnClickListener(this);
		otherlicai_buyday.setOnClickListener(this);
		otherlicai_enddate.setOnClickListener(this);
		otherlicai_reminddate.setOnClickListener(this);
		otherlicai_edit_ll.setOnClickListener(this);
	}

	private void initOtherLicaiView(View popupView) {
		// TODO Auto-generated method stub
		otherlicai_name = (EditText) popupView
				.findViewById(R.id.creat_otherlicai_name);// 理财产品名称
		otherlicai_money = (EditText) popupView
				.findViewById(R.id.creat_otherlicai_money);// 购买金额
		otherlicai_buyday = (TextView) popupView
				.findViewById(R.id.creat_otherlicai_buyday);// 购买时间
		otherlicai_enddate = (TextView) popupView
				.findViewById(R.id.creat_otherlicai_enddate);// 到期时间
		otherlicai_reminddate = (TextView) popupView
				.findViewById(R.id.creat_otherlicai_reminddate);// 提醒时间
		otherlicai_rate = (EditText) popupView
				.findViewById(R.id.creat_otherlicai_rate);// 预计收益率
		otherlicai_btn_ok = (Button) popupView
				.findViewById(R.id.creat_otherlicai_btn_ok);// 确认按钮
		otherlicai_btn_cancel = (Button) popupView
				.findViewById(R.id.creat_otherlicai_btn_cancel);// 取消
		otherlicai_title = (TextView) popupView
				.findViewById(R.id.otherlicai_title);// 标题
		otherlicai_edit_ll = (LinearLayout) popupView
				.findViewById(R.id.otherlicai_edit_ll);// 编辑按钮
		otherlicai_edit_text = (TextView) popupView
				.findViewById(R.id.otherlicai_edit_text);// 编辑文字
	}

	private void clearOtherLicai() {
		// TODO Auto-generated method stub
		otherlicai_name.setText("");// 理财产品名称
		otherlicai_money.setText("");// 购买金额
		otherlicai_buyday.setText("");// 购买时间
		otherlicai_enddate.setText("");// 到期时间
		otherlicai_reminddate.setText("");// 提醒时间
		otherlicai_rate.setText("");// 预计收益率

		otherlicai_name.setEnabled(true);// 理财产品名称
		otherlicai_money.setEnabled(true);// 购买金额
		otherlicai_buyday.setClickable(true);// 购买时间
		otherlicai_enddate.setClickable(true);// 到期时间
		otherlicai_reminddate.setClickable(true);// 提醒时间
		otherlicai_rate.setEnabled(true);// 预计收益率

		otherlicai_title.setText(otherLicaiTitle[0]);
		otherlicai_edit_ll.setVisibility(View.GONE);
	}

	private void setOtherLoanListener() {
		// TODO Auto-generated method stub
		otherloan_btn_ok.setOnClickListener(this);
		otherloan_btn_cancel.setOnClickListener(this);
		otherloan_type.setOnClickListener(this);
		otherloan_bank.setOnClickListener(this);
		otherloan_endday.setOnClickListener(this);
		otherloan_reminddate.setOnClickListener(this);
		otherloan_edit_ll.setOnClickListener(this);
	}

	private void clearOtherLoan() {
		otherloan_type.setText("");// 贷款方式
		otherloan_datelines.setText("");// 贷款期限
		otherloan_rate.setText("");// 贷款利率
		otherloan_money.setText("");// 贷款金额
		otherloan_endday.setText("");// 还款日期
		otherloan_bank.setText("");// 贷款银行
		otherloan_reminddate.setText("");// 提醒日期
		otherloan_bankID = "";
		otherloan_typeID = "";

		otherloan_type.setClickable(true);// 贷款方式
		otherloan_datelines.setEnabled(true);// 贷款期限
		otherloan_rate.setEnabled(true);// 贷款利率
		otherloan_money.setEnabled(true);// 贷款金额
		otherloan_endday.setClickable(true);// 还款日期
		otherloan_bank.setClickable(true);// 贷款银行
		otherloan_reminddate.setClickable(true);// 提醒日期
		otherloan_title.setText(otherLoanTitle[0]);
		otherloan_edit_ll.setVisibility(View.GONE);
	}

	private void initOtherLoanView(View popupView) {
		// TODO Auto-generated method stub
		otherloan_type = (TextView) popupView
				.findViewById(R.id.creat_otherloan_type);// 贷款方式
		otherloan_datelines = (EditText) popupView
				.findViewById(R.id.creat_otherloan_datelines);// 贷款期限
		otherloan_rate = (EditText) popupView
				.findViewById(R.id.creat_otherloan_rate);// 贷款利率
		otherloan_money = (EditText) popupView
				.findViewById(R.id.creat_otherloan_money);// 贷款金额
		otherloan_endday = (TextView) popupView
				.findViewById(R.id.creat_otherloan_endday);// 还款日期
		otherloan_bank = (TextView) popupView
				.findViewById(R.id.creat_otherloan_bank);// 贷款银行
		otherloan_reminddate = (TextView) popupView
				.findViewById(R.id.creat_otherloan_reminddate);// 提醒日期
		otherloan_btn_ok = (Button) popupView
				.findViewById(R.id.creat_otherloan_btn_ok);// 确认
		otherloan_btn_cancel = (Button) popupView
				.findViewById(R.id.creat_otherloan_btn_cancel);// 取消
		otherloan_title = (TextView) popupView
				.findViewById(R.id.otherloan_title);// 标题
		otherloan_edit_ll = (LinearLayout) popupView
				.findViewById(R.id.otherloan_edit_ll);// 编辑按钮
		otherloan_edit_text = (TextView) popupView
				.findViewById(R.id.otherloan_edit_text);// 编辑文字
	}

	private void setOtherDepositListener() {
		// TODO Auto-generated method stub
		otherdeposit_btn_ok.setOnClickListener(this);
		otherdeposit_btn_cancel.setOnClickListener(this);
		otherdeposit_rg
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						if (!otherDepositIsEdit) {
							return;
						}
						switch (checkedId) {
						case R.id.creat_otherdeposit_rb_1:
							IndexRadio = "0";
							break;
						case R.id.creat_otherdeposit_rb_2:
							IndexRadio = "1";
							break;
						default:
							break;
						}
					}
				});

		otherdeposit_dateline.setOnClickListener(this);
		otherdeposit_bank.setOnClickListener(this);
		otherdeposit_enddate.setOnClickListener(this);
		otherdeposit_reminddate.setOnClickListener(this);
		otherDeposit_edit_ll.setOnClickListener(this);
	}

	private void clearOtherDeposit() {
		// TODO Auto-generated method stub
		otherdeposit_rg.setClickable(true);
		otherdeposit_rb_1.setChecked(true);
		otherdeposit_money.setText("");// 存款金额
		otherdeposit_dateline.setText("");// 存钱期限
		otherdeposit_enddate.setText("");// 到期日
		otherdeposit_bank.setText("");// 存款银行
		otherdeposit_reminddate.setText("");// 提示日期
		otherdeposit_datelineID = "";
		otherdeposit_bankID = "";
		
		otherdeposit_enddate.setClickable(true);// 到期日
		otherdeposit_reminddate.setClickable(true);// 提示日期
		otherdeposit_money.setEnabled(true);
		otherdeposit_dateline.setClickable(true);// 存钱期限
		otherdeposit_bank.setClickable(true);// 存款银行
		otherDeposit_title.setText(otherDepositTitle[0]);
		otherDeposit_edit_ll.setVisibility(View.GONE);
	}

	private void initOtherDepositView(View popupView) {
		// TODO Auto-generated method stub
		otherdeposit_rg = (RadioGroup) popupView
				.findViewById(R.id.creat_otherdeposit_rg);// 存款类型
		otherdeposit_money = (EditText) popupView
				.findViewById(R.id.creat_otherdeposit_money);// 存款金额
		otherdeposit_dateline = (TextView) popupView
				.findViewById(R.id.creat_otherdeposit_dateline);// 存钱期限
		otherdeposit_enddate = (TextView) popupView
				.findViewById(R.id.creat_otherdeposit_enddate);// 到期日
		otherdeposit_bank = (TextView) popupView
				.findViewById(R.id.creat_otherdeposit_bank);// 存款银行
		otherdeposit_reminddate = (TextView) popupView
				.findViewById(R.id.creat_otherdeposit_reminddate);// 提示日期
		otherdeposit_btn_ok = (Button) popupView
				.findViewById(R.id.creat_otherdeposit_btn_ok);// 确认
		otherdeposit_btn_cancel = (Button) popupView
				.findViewById(R.id.creat_otherdeposit_btn_cancel);// 取消
		otherdeposit_rb_1 = (RadioButton) popupView
				.findViewById(R.id.creat_otherdeposit_rb_1);
		otherdeposit_rb_2 = (RadioButton) popupView
				.findViewById(R.id.creat_otherdeposit_rb_2);
		otherDeposit_title = (TextView) popupView
				.findViewById(R.id.otherdeposit_title);// 标题
		otherDeposit_edit_ll = (LinearLayout) popupView
				.findViewById(R.id.otherdeposit_edit_ll);// 编辑按钮
		otherDeposit_edit_text = (TextView) popupView
				.findViewById(R.id.otherdeposit_edit_text);// 编辑文字
	}

	private void initOtherProductView(View popupView) {
		// TODO Auto-generated method stub
		otherproduct_name = (EditText) popupView
				.findViewById(R.id.creat_otherproduct_name);// 产品名称
		otherproduct_type = (TextView) popupView
				.findViewById(R.id.creat_otherproduct_type);// 产品类型
		otherproduct_bank = (TextView) popupView
				.findViewById(R.id.creat_otherproduct_bank);// 签约银行
		otherproduct_time = (TextView) popupView
				.findViewById(R.id.creat_otherproduct_time);// 签约时间
		otherproduct_btn_ok = (Button) popupView
				.findViewById(R.id.creat_otherproduct_btn_ok);// 确定
		otherproduct_btn_cancel = (Button) popupView
				.findViewById(R.id.creat_otherproduct_btn_cancel);// 取消

		otherProduct_phone = (EditText) popupView
				.findViewById(R.id.creat_otherproduct_phone);// 签约电话

		otherproduct_title = (TextView) popupView
				.findViewById(R.id.otherproduct_title);// 标题
		otherproduct_edit_ll = (LinearLayout) popupView
				.findViewById(R.id.otherproduct_edit_ll);// 编辑按钮
		otherproduct_edit_text = (TextView) popupView
				.findViewById(R.id.otherproduct_edit_text);// 编辑文字
	}

	private void clearOtherProduct() {
		otherproduct_name.setText("");// 产品名称
		otherproduct_type.setText("");// 产品类型
		otherproduct_bank.setText("");// 签约银行
		otherproduct_time.setText("");// 签约时间
		otherProduct_phone.setText("");// 签约电话
		otherproduct_bankID = "";
		otherproduct_typeID = "";
		otherproduct_title.setText(otherProductTitle[0]);
		otherproduct_edit_ll.setVisibility(View.GONE);// 编辑按钮
		otherproduct_name.setEnabled(true);
		otherproduct_type.setClickable(true);// 产品类型
		otherproduct_bank.setClickable(true);// 签约机构
		otherproduct_time.setClickable(true);// 签约时间
		otherProduct_phone.setEnabled(true);
	}

	private void setOtherProductListener() {
		// TODO Auto-generated method stub
		otherproduct_btn_ok.setOnClickListener(this);
		otherproduct_btn_cancel.setOnClickListener(this);
		otherproduct_type.setOnClickListener(this);
		otherproduct_bank.setOnClickListener(this);
		otherproduct_time.setOnClickListener(this);
		otherproduct_edit_ll.setOnClickListener(this);
	}

	private void setListener() {
		// TODO Auto-generated method stub
		otherbank_nextbtn.setOnClickListener(this);
		creat_otherproduct.setOnClickListener(this);
		creat_otherlicai.setOnClickListener(this);
		creat_otherloan.setOnClickListener(this);
		creat_otherdeposit.setOnClickListener(this);
	}

	private void setView(View view) {
		// TODO Auto-generated method stub
		otherbank_nextbtn = (Button) view
				.findViewById(R.id.business_otherbank_nextbtn);
		otherproduct_list = (SwipeListView) view
				.findViewById(R.id.business_otherproduct_list);// 行外产品签约
		otherlicai_list = (SwipeListView) view
				.findViewById(R.id.business_otherlicai_list);// 行外理财产品
		otherdeposit_list = (SwipeListView) view
				.findViewById(R.id.business_otherdeposit_list);// 行外存款
		otherloan_list = (SwipeListView) view
				.findViewById(R.id.business_otherloan_list);// 行外贷款
		creat_otherproduct = (ImageView) view
				.findViewById(R.id.business_otherbank_creat_otherproduct);
		creat_otherlicai = (ImageView) view
				.findViewById(R.id.business_otherbank_creat_otherlicai);
		creat_otherdeposit = (ImageView) view
				.findViewById(R.id.business_otherbank_creat_otherdeposit);
		creat_otherloan = (ImageView) view
				.findViewById(R.id.business_otherbank_creat_otherloan);
		factory = LayoutInflater.from(getActivity());
	}

}