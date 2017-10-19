package com.pactera.financialmanager.ui.customermanager;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Message;
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
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.SwipeListView;
import com.pactera.financialmanager.ui.model.FinanceDeposit;
import com.pactera.financialmanager.ui.model.FinanceLicai;
import com.pactera.financialmanager.ui.model.FinanceLoan;
import com.pactera.financialmanager.ui.model.FinanceOther;
import com.pactera.financialmanager.ui.model.FinanceProduct;
import com.pactera.financialmanager.ui.model.ItemModel;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * 金融需求fragment
 * 
 * @author JAY
 * 
 */
public class FinanceNeedFragment extends ParentFragment implements
		OnClickListener {

	Button financeneed_nextbtn;

	SwipeListView financeneed_other_list;// 其他需求(6个元素)
	SwipeListView financeneed_product_list;// 电子产品(6个元素)
	SwipeListView financeneed_deposit_list;// 存款需求(6个元素)
	SwipeListView financeneed_loan_list;// 贷款需求(7个元素)
	SwipeListView financeneed_licai_list;// 理财需求(6个元素)

	ImageView creat_product;
	ImageView creat_other;
	ImageView creat_deposit;
	ImageView creat_loan;
	ImageView creat_licai;

	// 弹出框
	private Dialog popup_product;
	private Dialog popup_deposit;
	private Dialog popup_loan;
	private Dialog popup_other;
	private Dialog popup_licai;
	private LayoutInflater factory;

	/**
	 * 预约存款
	 */
	Button finance_deposit_btn_cancel;// 取消
	Button finance_deposit_btn_ok;// 确认
	EditText finance_deposit_money;// 存款金额
	TextView finance_deposit_date;// 存款时间
//	TextView finance_deposit_reminddate;// 提醒时间
	EditText finance_deposit_info;// 内容描述

	TextView deposit_title;// 标题
	LinearLayout deposit_edit_ll;// 编辑按钮
	TextView deposit_edit_text;// 编辑文字
	String[] depositTitle = { "创建存款预约", "查看存款预约", "编辑存款预约" };// 标题名称
	Constants.requestType depositRType = requestType.Search;// 默认当前状态为查询
	boolean depositIsEdit = false;
	static FinanceDeposit tempDeposit;

	/**
	 * 贷款需求
	 */
	EditText finance_loan_amount;// 金额
	// EditText finance_loan_kind;// 种类
	TextView finance_loan_purpose;// 用途
	TextView finance_loan_warrant;// 可提供的担保方式
	EditText finance_loan_turn_over;// 周转期限
    private LinearLayout ll_finance_loan_status;
    TextView finance_loan_status;// 状态
	TextView finance_loan_occur_time;// 发生时间
//	TextView finance_loan_remind_time;// 提醒时间
	EditText finance_loan_des;// 备注
	Button finance_loan_btn_cancel;// 取消
	Button finance_loan_btn_ok;// 确认


	TextView loan_title;// 标题
	LinearLayout loan_edit_ll;// 编辑按钮
	TextView loan_edit_text;// 编辑文字
	String[] loanTitle = { "创建贷款需求", "查看贷款需求", "编辑贷款需求" };// 标题名称
	Constants.requestType loanRType = requestType.Search;// 默认当前状态为查询
	boolean loanIsEdit = false;
	static FinanceLoan tempLoan;

	/**
	 * 电子产品需求
	 */
	Button finance_product_btn_cancel;// 取消
	Button finance_product_btn_ok;// 确认
	TextView finance_product_kind;// 种类
	TextView finance_product_occur_time;// 发生日期
//	TextView finance_product_remind_time;// 提醒时间
	EditText finance_product_des;// 备注
	TextView finance_product_state;// 状态
	TextView product_title;// 标题
	LinearLayout product_edit_ll;// 编辑按钮
	TextView product_edit_text;// 编辑文字
	String[] productTitle = { "创建电子产品需求", "查看电子产品需求", "编辑电子产品需求" };// 标题名称
	Constants.requestType productRType = requestType.Search;// 默认当前状态为查询
	boolean productIsEdit = false;
	static FinanceProduct tempproduct;
    private LinearLayout ll_finance_product_status;

    /**
	 * 理财需求
	 */
	TextView finance_licai_title;// 标题
	LinearLayout finance_licai_edit_ll;// 编辑按钮
	TextView licai_edit_text;// 编辑文字
	EditText finance_licai_money;// 金额
	TextView finance_licai_occur_time;// 预约时间
	TextView finance_licai_state;// 状态
	LinearLayout ll_licai_status;// 状态
//	TextView finance_licai_remind_time;// 提醒时间
	EditText finance_licai_des;// 备注
	Button finance_licai_btn_ok;// 确认按钮
	Button finance_licai_btn_cancel;// 取消按钮

	String[] licaiTitle = { "创建理财需求", "查看理财需求", "编辑理财需求" };// 标题名称
	Constants.requestType licaiRType = requestType.Search;// 默认当前状态为查询
	boolean licaiIsEdit = false;
	static FinanceLicai tempLicai;

	/**
	 * 其他需求
	 */
	Button finance_other_btn_cancel;// 取消
	Button finance_other_btn_ok;// 确认
	EditText finance_other_prd_name;// 产品名称
	TextView finance_other_occur_time;// 发生时间
//	TextView finance_other_remind_time;// 提醒时间
	EditText finance_other_des;// 描述

	TextView other_title;// 标题
	LinearLayout other_edit_ll;// 编辑按钮
	TextView other_edit_text;// 编辑文字
	String[] otherTitle = { "创建其他需求", "查看其他需求", "编辑其他需求" };// 标题名称
	Constants.requestType otherRType = requestType.Search;// 默认当前状态为查询
	boolean otherIsEdit = false;
	static FinanceOther tempother;

	/**
	 * 各类码值
	 */
	private String product_kindID = "";
	// private String loan_kindID = "";
	private String loan_statusID = "";
	// private String loan_turn_overID = "";
	private String loan_warrantID = "";// 可提供的担保方式
	private String loan_purposeID = "";// 用途
	private String licai_stateID = "";// 理财状态
	private String product_stateID = "";// 电子产品状态

	ArrayList<FinanceOther> OtherList = new ArrayList<FinanceOther>();
	ArrayList<FinanceLoan> LoanList = new ArrayList<FinanceLoan>();
	ArrayList<FinanceProduct> ProductList = new ArrayList<FinanceProduct>();
	ArrayList<FinanceDeposit> DepositList = new ArrayList<FinanceDeposit>();
	ArrayList<FinanceLicai> LicaiList = new ArrayList<FinanceLicai>();

	private HConnection HCon;
	private HConnection getOtherHCon;
	private HConnection getLoanHCon;
	private HConnection getProductHCon;
	private HConnection getDepositHCon;
	private HConnection getLicaiHCon;

	private final int creatOtherInfo = 1;
	private final int getOtherInfo = 2;
	private final int creatLoanInfo = 3;
	private final int getLoanInfo = 4;
	private final int creatProductInfo = 5;
	private final int getProductInfo = 6;
	private final int creatDepositInfo = 7;
	private final int getDepositInfo = 8;
	private final int delOtherInfo = 9;
	private final int delLoanInfo = 10;
	private final int delProductInfo = 11;
	private final int delDepositInfo = 12;

	private final int delLicaiInfo = 13;
	private final int creatLicaiInfo = 14;
	private final int getLicaiInfo = 15;

	DeleteDialog deleteDialog;
	String[] comTitle = { "编辑", "取消" };

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// 时间格式

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(
				R.layout.fragment_archiving_financeneedinfo, null);
		setView(view);
		setListener();
		getDepositInfo();
		getLoanInfo();
		getProductInfo();
		getOtherInfo();
		getLicaiInfo();
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.financeneed_nextbtn:
			Message msg = ((ParentActivity) getActivity()).handler
					.obtainMessage();
			Bundle data = new Bundle();
			data.putString("value", "" + PersonArchiving.FinanceNeedIndex);
			msg.setData(data);
			((ParentActivity) getActivity()).handler.sendMessage(msg);
			break;
		case R.id.financeneed_creat_product:
			productRType = requestType.Insert;
			showProductView(productRType,true);
			break;
		case R.id.financeneed_creat_other:
			otherRType = requestType.Insert;
			showOtherView(otherRType);
			break;
		case R.id.financeneed_creat_deposit:
			depositRType = requestType.Insert;
			showDepositView(depositRType);
			break;
		case R.id.financeneed_creat_loan:
			loanRType = requestType.Insert;
			showLoanView(loanRType);
			break;
		case R.id.popwindow_finance_deposit_btn_ok:
			creatDeposit();//点击创建存款需求
			break;
		case R.id.popwindow_finance_product_btn_ok:
			creatProduct();
			break;
		case R.id.popwindow_finance_loan_btn_ok:
			creatLoan();
			break;
		case R.id.popwindow_finance_other_btn_ok:
			creatOther();
			break;
		case R.id.popwindow_finance_deposit_btn_cancel:
			if (popup_deposit != null) {
				popup_deposit.dismiss();
			}
			break;
		case R.id.popwindow_finance_loan_btn_cancel:
			if (popup_loan != null) {
				popup_loan.dismiss();
			}
			break;

		case R.id.popwindow_finance_product_btn_cancel:
			if (popup_product != null) {
				popup_product.dismiss();
			}
			break;

		case R.id.popwindow_finance_other_btn_cancel:
			if (popup_other != null) {
				popup_other.dismiss();
			}
			break;
		case R.id.popwindow_finance_product_kind:// 产品类型
			if (productIsEdit) {
				SpinnerAdapter.showSelectDialog(getActivity(),
						NewCatevalue.kindType, finance_product_kind,
						new CallBackItemClickListener() {

							@Override
							public void CallBackItemClick(String spinnerID) {
								product_kindID = spinnerID;
							}
						});
			}
			break;
		case R.id.popwindow_finance_product_occur_time:// 预约时间
			if (productIsEdit) {
				DatePickerShow(new OnDateSetListener() {

					@Override
					public void onDateSet(String year, String month, String day) {
						String theDate = year + "-" + month + "-" + day;
						finance_product_occur_time.setText(theDate);
					}
				});
			}
			break;
//		case R.id.popwindow_finance_product_remind_time:// 提醒时间
//			if (productIsEdit) {
//				DatePickerShow(new OnDateSetListener() {
//
//					@Override
//					public void onDateSet(int year, int month, int day) {
//						// TODO Auto-generated method stub
//						String theDate = year + "-" + formatNum(month+"") + "-" + formatNum(day+"");
//						finance_product_remind_time.setText(theDate);
//					}
//				});
//			}
//			break;
		case R.id.popwindow_finance_other_occur_time:// 其他需求预约时间
			if (otherIsEdit) {

				DatePickerShow(new OnDateSetListener() {

					@Override
					public void onDateSet(String year, String month, String day) {
						// TODO Auto-generated method stub
						String theDate = year + "-" +month+"-" + day;
						finance_other_occur_time.setText(theDate);
					}
				});
			}
			break;
//		case R.id.popwindow_finance_other_remind_time:// 其他需求提醒时间
//			if (otherIsEdit) {
//				DatePickerShow(new OnDateSetListener() {
//
//					@Override
//					public void onDateSet(int year, int month, int day) {
//						// TODO Auto-generated method stub
//						String theDate = year + "-" + formatNum(month+"") + "-" + formatNum(day+"");
//						finance_other_remind_time.setText(theDate);
//					}
//				});
//			}
//			break;
		// case R.id.popwindow_finance_loan_kind:// 种类
		// if (loanIsEdit) {
		// SpinnerAdapter.showSelectDialog(getActivity(),
		// NewCatevalue.loanCateType, finance_loan_kind,
		// new CallBackItemClickListener() {
		//
		// @Override
		// public void CallBackItemClick(String spinnerID) {
		// // TODO Auto-generated method stub
		// loan_kindID = spinnerID;
		// }
		// });
		// }
		// break;
		case R.id.popwindow_finance_loan_purpose:// 用途
			if (loanIsEdit) {
				SpinnerAdapter.showSelectDialog(getActivity(),
						NewCatevalue.loanUseType, finance_loan_purpose,
						new CallBackItemClickListener() {

							@Override
							public void CallBackItemClick(String spinnerID) {
								// TODO Auto-generated method stub
								loan_purposeID = spinnerID;
							}
						});
			}
			break;
		case R.id.popwindow_finance_loan_warrant:// 可提供的担保方式
			if (loanIsEdit) {
				SpinnerAdapter.showSelectDialog(getActivity(),
						NewCatevalue.warrantType, finance_loan_warrant,
						new CallBackItemClickListener() {

							@Override
							public void CallBackItemClick(String spinnerID) {
								loan_warrantID = spinnerID;
							}
						});
			}
			break;
		// case R.id.popwindow_finance_loan_turn_over:// 周转期限
		// if (loanIsEdit) {
		// SpinnerAdapter.showSelectDialog(getActivity(),
		// NewCatevalue.turnOverType, finance_loan_turn_over,
		// new CallBackItemClickListener() {
		//
		// @Override
		// public void CallBackItemClick(String spinnerID) {
		// // TODO Auto-generated method stub
		// loan_turn_overID = spinnerID;
		// }
		// });
		// }
		// break;
//		case R.id.popwindow_finance_loan_status:// 状态
//			if (loanIsEdit) {
//				SpinnerAdapter.showSelectDialog(getActivity(),
//						NewCatevalue.fkstatus, finance_loan_status,
//						new CallBackItemClickListener() {
//
//							@Override
//							public void CallBackItemClick(String spinnerID) {
//								// TODO Auto-generated method stub
//								loan_statusID = spinnerID;
//							}
//						});
//			}
//			break;
		case R.id.popwindow_finance_loan_occur_time:// 发生时间
			if (loanIsEdit) {
				DatePickerShow(new OnDateSetListener() {

					@Override
					public void onDateSet(String year, String month, String day) {
						// TODO Auto-generated method stub
						String theDate = year + "-" + month+ "-" + day;
						finance_loan_occur_time.setText(theDate);
					}
				});
			}
			break;
//		case R.id.popwindow_finance_loan_remind_time:// 提醒时间
//			if (loanIsEdit) {
//				DatePickerShow(new OnDateSetListener() {
//
//					@Override
//					public void onDateSet(int year, int month, int day) {
//						// TODO Auto-generated method stub
//						String theDate = year + "-" + formatNum(month+"") + "-" + formatNum(day+"");
//						finance_loan_remind_time.setText(theDate);
//					}
//				});
//			}
//			break;
		case R.id.popwindow_finance_deposit_date:// 存款时间
			if (depositIsEdit) {
				DatePickerShow(new OnDateSetListener() {

					@Override
					public void onDateSet(String year, String month, String day) {
						// TODO Auto-generated method stub
						String theDate = year + "-" + month+ "-" + day;
						finance_deposit_date.setText(theDate);
					}
				});
			}
			break;
//		case R.id.popwindow_finance_deposit_reminddate:// 提醒时间
//			if (depositIsEdit) {
//				DatePickerShow(new OnDateSetListener() {
//					@Override
//					public void onDateSet(int year, int month, int day) {
//						// TODO Auto-generated method stub
//						String theDate = year + "-" + formatNum(month+"") + "-" + formatNum(day+"");
//						finance_deposit_reminddate.setText(theDate);
//					}
//				});
//			}
//			break;
		case R.id.popwindow_finance_licai_occur_time:// 预约时间
			if (licaiIsEdit) {
				DatePickerShow(new OnDateSetListener() {
					@Override
					public void onDateSet(String year, String month, String day) {
						// TODO Auto-generated method stub
						String theDate = year + "-" + month + "-" + day;
						finance_licai_occur_time.setText(theDate);
					}
				});
			}
			break;

//		case R.id.popwindow_finance_licai_remind_time:// 提醒时间
//			if (licaiIsEdit) {
//				DatePickerShow(new OnDateSetListener() {
//					@Override
//					public void onDateSet(int year, int month, int day) {
//						// TODO Auto-generated method stub
//						String theDate = year + "-" + formatNum(month+"") + "-" + formatNum(day+"");
//						finance_licai_remind_time.setText(theDate);
//					}
//				});
//			}
//			break;

		case R.id.finance_deposit_edit_ll:
			depositIsEdit = !depositIsEdit;
			setDepositView(depositIsEdit);
			break;
		case R.id.finance_loan_edit_ll:
			loanIsEdit = !loanIsEdit;
			setLoanView(loanIsEdit);
			break;
		case R.id.finance_licai_edit_ll:
			licaiIsEdit = !licaiIsEdit;
			setLicaiView(licaiIsEdit);
			break;
		case R.id.finance_product_edit_ll:
			productIsEdit = !productIsEdit;
			setProductView(productIsEdit);
			break;
		case R.id.finance_other_edit_ll:
			otherIsEdit = !otherIsEdit;
			setOtherView(otherIsEdit);
			break;
		case R.id.financeneed_creat_licai:
			licaiRType = requestType.Insert;
			showLicaiView(licaiRType);
			break;

		case R.id.popwindow_finance_licai_btn_cancel:
			if (popup_licai != null) {
				popup_licai.dismiss();
			}
			break;
		case R.id.popwindow_finance_licai_btn_ok:
			creatLicai();
			break;
//		case R.id.popwindow_finance_licai_state:
//			if (licaiIsEdit) {
//				SpinnerAdapter.showSelectDialog(getActivity(),
//						NewCatevalue.fin_status, finance_licai_state,
//						new CallBackItemClickListener() {
//							@Override
//							public void CallBackItemClick(String spinnerID) {
//								// TODO Auto-generated method stub
//								licai_stateID = spinnerID;
//							}
//						});
//			}
//			break;
//		case R.id.popwindow_finance_product_state:
//			if (productIsEdit) {
//				SpinnerAdapter.showSelectDialog(getActivity(),
//						NewCatevalue.fin_status, finance_product_state,
//						new CallBackItemClickListener() {
//							@Override
//							public void CallBackItemClick(String spinnerID) {
//								// TODO Auto-generated method stub
//								product_stateID = spinnerID;
//							}
//						});
//			}
//			break;
		default:
			break;
		}
	}

	// 创建/修改理财需求
	private void creatLicai() {
		// TODO Auto-generated method stub
		FinanceLicai newLicai = new FinanceLicai();
		if (licaiRType == requestType.Search) {
			newLicai = tempLicai;
		}
		newLicai.CUSTID = PersonArchiving.custID;// 客户号
		newLicai.STATUS = licai_stateID;// 状态
		newLicai.OCCUR_TIME = finance_licai_occur_time.getText().toString();// 预约日期
//		newLicai.REMIND_TIME = finance_licai_remind_time.getText().toString();// 提醒日期
		newLicai.DES = Tool.getTextValue(finance_licai_des);// 内容描述
		newLicai.AMOUNT = Tool.getTextValue(finance_licai_money);// 金额

//		if (!TextUtils.isEmpty(newLicai.OCCUR_TIME)
//				&& !TextUtils.isEmpty(newLicai.REMIND_TIME)) {
//			try {
//				Date d2 = formatter.parse(newLicai.OCCUR_TIME);
//				Date d1 = formatter.parse(newLicai.REMIND_TIME);
//
//				if (!Tool.daysBetween(d1, d2)) {
//					Toast.makeText(getActivity(), "提醒时间要小于等于预约时间!",
//							Toast.LENGTH_SHORT).show();
//					return;
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

		if (licaiRType == requestType.Insert) {
            newLicai.STATUS = "01";// 状态默认为"未处理"
            HCon = RequestInfo(FinanceNeedFragment.this,
					Constants.requestType.Insert,
					InterfaceInfo.Finance_Licai_Creat, newLicai.toString(),
					creatLicaiInfo);

		} else if (licaiRType == requestType.Search) {
			if (licaiIsEdit) {
				HCon = RequestInfo(FinanceNeedFragment.this,
						Constants.requestType.Update,
						InterfaceInfo.Finance_Licai_Update,
						newLicai.toString(), creatLicaiInfo);
			} else {
				if (popup_licai != null) {
					popup_licai.dismiss();
				}
			}

		}

	}

	private void creatOther() {
		FinanceOther newOther = new FinanceOther();
		if (otherRType == requestType.Search) {
			newOther = tempother;
		}
		newOther.prd_name = Tool.getTextValue(finance_other_prd_name);// 产品名称
		newOther.occur_time = finance_other_occur_time.getText().toString();// 预约时间
//		newOther.remind_time = finance_other_remind_time.getText().toString();// 提醒日期
		newOther.des = Tool.getTextValue(finance_other_des);// 备注
		newOther.custid = PersonArchiving.custID;// 客户号

//		if (!TextUtils.isEmpty(newOther.occur_time)
//				&& !TextUtils.isEmpty(newOther.remind_time)) {
//			try {
//				Date d2 = formatter.parse(newOther.occur_time);
//				Date d1 = formatter.parse(newOther.remind_time);
//
//				if (!Tool.daysBetween(d1, d2)) {
//					Toast.makeText(getActivity(), "提醒时间要小于等于预约时间!",
//							Toast.LENGTH_SHORT).show();
//					return;
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

		if (otherRType == requestType.Insert) {
			HCon = RequestInfo(FinanceNeedFragment.this,
					Constants.requestType.Insert,
					InterfaceInfo.Finance_Other_Creat, newOther.toString(),
					creatOtherInfo);

		} else if (otherRType == requestType.Search) {
			if (otherIsEdit) {
				HCon = RequestInfo(FinanceNeedFragment.this,
						Constants.requestType.Update,
						InterfaceInfo.Finance_Other_Update,
						newOther.toString(), creatOtherInfo);
			} else {
				if (popup_other != null) {
					popup_other.dismiss();
				}
			}

		}

	}

	/**
	 * 贷款需求
	 */
	private void creatLoan() {

		FinanceLoan newLoan = new FinanceLoan();
		if (loanRType == requestType.Search) {
			newLoan = tempLoan;
		}

		newLoan.occur_time = finance_loan_occur_time.getText().toString();// 发生时间
//		newLoan.remind_time = finance_loan_remind_time.getText().toString();// 提醒日期
		newLoan.des = Tool.getTextValue(finance_loan_des);// 备注
		newLoan.custid = PersonArchiving.custID;// 客户号
		// newLoan.kind = loan_kindID;// 种类
		newLoan.amount = Tool.getTextValue(finance_loan_amount);// 金额
		newLoan.purpose = loan_purposeID;// 用途
		newLoan.warrant = loan_warrantID;// 可提供的担保方式
		newLoan.turn_over = Tool.getTextValue(finance_loan_turn_over);// 周转期限
		newLoan.status = loan_statusID;// 状态
//
//		if (!TextUtils.isEmpty(newLoan.occur_time)
//				&& !TextUtils.isEmpty(newLoan.remind_time)) {
//			try {
//				Date d2 = formatter.parse(newLoan.occur_time);
//				Date d1 = formatter.parse(newLoan.remind_time);
//
//				if (!Tool.daysBetween(d1, d2)) {
//					Toast.makeText(getActivity(), "提醒时间要小于等于预约时间!",
//							Toast.LENGTH_SHORT).show();
//					return;
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

		if (loanRType == requestType.Insert) {
            newLoan.status = "01";//状态默认为"未处理"
			HCon = RequestInfo(FinanceNeedFragment.this,
					Constants.requestType.Insert,
					InterfaceInfo.Finance_Loan_Creat, newLoan.toString(),
					creatLoanInfo);

		} else if (loanRType == requestType.Search) {
			if (loanIsEdit) {
				HCon = RequestInfo(FinanceNeedFragment.this,
						Constants.requestType.Update,
						InterfaceInfo.Finance_Loan_Update, newLoan.toString(),
						creatLoanInfo);

			} else {
				if (popup_loan != null) {
					popup_loan.dismiss();
				}
			}

		}

	}

	/**
	 * 电子产品需求
	 */
	private void creatProduct() {
		// TODO Auto-generated method stub
		FinanceProduct newProduct = new FinanceProduct();
		if (productRType == requestType.Search) {
			newProduct = tempproduct;
		}

		newProduct.custid = PersonArchiving.custID;
		newProduct.occur_time = finance_product_occur_time.getText().toString();// 发生时间
//		newProduct.remind_time = finance_product_remind_time.getText().toString();// 提醒日期
		newProduct.des = Tool.getTextValue(finance_product_des);// 备注
		newProduct.kind = product_kindID;// 种类
		newProduct.status = product_stateID;// 状态
//
//		if (newProduct.occur_time != null && newProduct.remind_time != null) {
//			try {
//				Date d2 = formatter.parse(newProduct.occur_time);
//				Date d1 = formatter.parse(newProduct.remind_time);
//
//				if (!Tool.daysBetween(d1, d2)) {
//					Toast.makeText(getActivity(), "提醒时间要小于等于预约时间!",
//							Toast.LENGTH_SHORT).show();
//					return;
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

		if (productRType == requestType.Insert) {
            newProduct.status = "01";// 状态默认为"未处理"
            HCon = RequestInfo(FinanceNeedFragment.this,
					Constants.requestType.Insert,
					InterfaceInfo.Finance_Product_Creat, newProduct.toString(),
					creatProductInfo);

		} else if (productRType == requestType.Search) {
			if (productIsEdit) {
				HCon = RequestInfo(FinanceNeedFragment.this,
						Constants.requestType.Update,
						InterfaceInfo.Finance_Product_Update,
						newProduct.toString(), creatProductInfo);
			} else {
				if (popup_product != null) {
					popup_product.dismiss();
				}
			}

		}

	}

	/**
	 * 创建存款预约
	 */
	private void creatDeposit() {
		FinanceDeposit newDeposit = new FinanceDeposit();
		if (depositRType == requestType.Search) {
			newDeposit = tempDeposit;
		}

		newDeposit.custid = PersonArchiving.custID;
		newDeposit.deposit_amount = Tool.getTextValue(finance_deposit_money);// 存款金额
		newDeposit.ap_time = finance_deposit_date.getText().toString();// 预约时间
//		newDeposit.remind_time = finance_deposit_reminddate.getText().toString();// 提醒日期
		newDeposit.des = Tool.getTextValue(finance_deposit_info);// 备注

//		if (!TextUtils.isEmpty(newDeposit.ap_time)) {
//			try {
//				Date d2 = formatter.parse(newDeposit.ap_time);
//				Date d1 = formatter.parse(newDeposit.remind_time);
//
//				if (!Tool.daysBetween(d1, d2)) {
//					Toast.makeText(getActivity(), "提醒时间要小于等于预约时间!",
//							Toast.LENGTH_SHORT).show();
//					return;
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

		if (depositRType == requestType.Insert) {
			HCon = RequestInfo(FinanceNeedFragment.this,
					Constants.requestType.Insert,
					InterfaceInfo.Finance_Deposit_Creat, newDeposit.toString(),
					creatDepositInfo);

		} else if (depositRType == requestType.Search) {
			if (depositIsEdit) {
				HCon = RequestInfo(FinanceNeedFragment.this,
						Constants.requestType.Update,
						InterfaceInfo.Finance_Deposit_Update,
						newDeposit.toString(), creatDepositInfo);
			} else {
				if (popup_deposit != null) {
					popup_deposit.dismiss();
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
		case creatOtherInfo:
		case creatLoanInfo:
		case creatProductInfo:
		case creatDepositInfo:
		case creatLicaiInfo:
		case delOtherInfo:
		case delLoanInfo:
		case delProductInfo:
		case delDepositInfo:
		case delLicaiInfo:
			res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);

			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readJson(tmpJsonObject, connectionIndex);
			break;

		case getLicaiInfo:
			res = getLicaiHCon
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);

			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readJson(tmpJsonObject, connectionIndex);
			break;
		case getOtherInfo:
			res = getOtherHCon
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
		case getDepositInfo://返回存款需求
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
				if (connectionIndex == creatOtherInfo
						|| connectionIndex == delOtherInfo) {
					getOtherInfo();
				} else if (connectionIndex == getOtherInfo) {
					OtherList.clear();
					JSONArray theInfo = tmpJsonObject.getJSONArray("group");
					if (theInfo.length() < 0) {
						return;
					} else {
						for (int i = 0; i < theInfo.length(); i++) {
							JSONObject temps = (JSONObject) theInfo.opt(i);
							FinanceOther tempsOther = new FinanceOther();
							tempsOther.custid = PersonArchiving.custID;// 客户号
							tempsOther.pkid = Tool.getJsonValue(temps, "pkid");// 主键
							tempsOther.prd_name = Tool.getJsonValue(temps,
									"prd_name");// 产品名称
							tempsOther.occur_time = Tool.getJsonValue(temps,
									"occur_time");// 预约时间
//							tempsOther.remind_time = Tool.getJsonValue(temps,
//									"remind_time");// 提醒日期
							tempsOther.des = Tool.getJsonValue(temps, "des");// 备注
							tempsOther.RECORD_USER = Tool.getJsonValue(temps,
									"record_user");
							tempsOther.RECORD_USERNAME = Tool.getJsonValue(
									temps, "RECORD_USERNAME");
							tempsOther.RECORD_TIME = Tool.getJsonValue(temps,
									"RECORD_TIME");
							OtherList.add(tempsOther);
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
							FinanceLoan tempsLoan = new FinanceLoan();
							tempsLoan.custid = PersonArchiving.custID;// 客户号
							tempsLoan.pkid = Tool.getJsonValue(temps, "pkid");// 主键

							tempsLoan.occur_time = Tool.getJsonValue(temps,
									"occur_time");// 发生时间
//							tempsLoan.remind_time = Tool.getJsonValue(temps,
//									"remind_time");// 提醒日期
							tempsLoan.des = Tool.getJsonValue(temps, "des");// 备注

							// tempsLoan.kind = Tool.getJsonValue(temps,
							// "kind");// 种类
							tempsLoan.amount = Tool.getJsonValue(temps,
									"amount");// 金额
							tempsLoan.purpose = Tool.getJsonValue(temps,
									"purpose");// 用途
							tempsLoan.warrant = Tool.getJsonValue(temps,
									"warrant");// 可提供的担保方式
							tempsLoan.turn_over = Tool.getJsonValue(temps,
									"turn_over");// 周转期限
							tempsLoan.status = Tool.getJsonValue(temps,
									"status");// 状态

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
							FinanceProduct tempsPro = new FinanceProduct();
							tempsPro.custid = PersonArchiving.custID;// 客户号
							tempsPro.pkid = Tool.getJsonValue(temps, "pkid");// 主键
							tempsPro.occur_time = Tool.getJsonValue(temps,
									"occur_time");// 发生时间
//							tempsPro.remind_time = Tool.getJsonValue(temps,
//									"remind_time");// 提醒日期
							tempsPro.des = Tool.getJsonValue(temps, "des");// 备注
							tempsPro.kind = Tool.getJsonValue(temps, "kind");// 种类
							tempsPro.RECORD_USER = Tool.getJsonValue(temps,
									"RECORD_USER");
							tempsPro.RECORD_TIME = Tool.getJsonValue(temps,
									"RECORD_TIME");
							tempsPro.RECORD_USERNAME = Tool.getJsonValue(temps,
									"RECORD_USERNAME");
							tempsPro.status = Tool
									.getJsonValue(temps, "status");
							ProductList.add(tempsPro);
						}

					}

				} else if (connectionIndex == creatDepositInfo|| connectionIndex == delDepositInfo) {
					getDepositInfo();
				} else if (connectionIndex == getDepositInfo) {
					DepositList.clear();
					JSONArray theInfo = tmpJsonObject.getJSONArray("group");
					if (theInfo.length() < 0) {
						return;
					} else {
						for (int i = 0; i < theInfo.length(); i++) {
							JSONObject temps = (JSONObject) theInfo.opt(i);
							FinanceDeposit tempsDeposit = new FinanceDeposit();
							tempsDeposit.custid = PersonArchiving.custID;// 客户号
							tempsDeposit.pkid = Tool
									.getJsonValue(temps, "pkid");// 主键
							tempsDeposit.deposit_amount = Tool.getJsonValue(
									temps, "deposit_amount");// 存款金额
							tempsDeposit.ap_time = Tool.getJsonValue(temps,
									"ap_time");// 预约时间
//							tempsDeposit.remind_time = Tool.getJsonValue(temps,
//									"remind_time");// 提醒日期
							tempsDeposit.des = Tool.getJsonValue(temps, "des");// 备注
							tempsDeposit.RECORD_USER = Tool.getJsonValue(temps,
									"record_user");
							tempsDeposit.RECORD_TIME = Tool.getJsonValue(temps,
									"record_time");
							DepositList.add(tempsDeposit);
						}

					}
				} else if (connectionIndex == creatLicaiInfo
						|| connectionIndex == delLicaiInfo) {
					getLicaiInfo();
				} else if (connectionIndex == getLicaiInfo) {
					LicaiList.clear();
					JSONArray theInfo = tmpJsonObject.getJSONArray("group");
					if (theInfo.length() < 0) {
						return;
					} else {
						for (int i = 0; i < theInfo.length(); i++) {
							JSONObject temps = (JSONObject) theInfo.opt(i);
							FinanceLicai tempsLicai = new FinanceLicai();
							tempsLicai.CUSTID = PersonArchiving.custID;// 客户号
							tempsLicai.pkid = Tool.getJsonValue(temps, "PKID");// 主键

							tempsLicai.STATUS = Tool.getJsonValue(temps,
									"STATUS");// 状态
							tempsLicai.OCCUR_TIME = Tool.getJsonValue(temps,
									"OCCUR_TIME");// 预约日期
//							tempsLicai.REMIND_TIME = Tool.getJsonValue(temps,
//									"REMIND_TIME");// 提醒日期
							tempsLicai.DES = Tool.getJsonValue(temps, "DES");// 内容描述
							tempsLicai.AMOUNT = Tool.getJsonValue(temps,
									"AMOUNT");// 金额

							LicaiList.add(tempsLicai);
						}

					}

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			Toast.makeText(getActivity(), "数据错误！", Toast.LENGTH_SHORT).show();
		}

		// 进行数据刷新
		refreshInfo(connectionIndex);
		if (popup_other != null) {
			popup_other.dismiss();
		}
		if (popup_loan != null) {
			popup_loan.dismiss();
		}
		if (popup_deposit != null) {
			popup_deposit.dismiss();
		}
		if (popup_product != null) {
			popup_product.dismiss();
		}
		if (popup_licai != null) {
			popup_licai.dismiss();
		}
	}

	private void getOtherInfo() {
		getOtherHCon = RequestInfo(FinanceNeedFragment.this,
				Constants.requestType.Search, InterfaceInfo.Finance_Other_Get,
				PersonArchiving.custID, getOtherInfo);
	}

	private void getLoanInfo() {
		getLoanHCon = RequestInfo(FinanceNeedFragment.this,
				Constants.requestType.Search, InterfaceInfo.Finance_Loan_Get,
				PersonArchiving.custID, getLoanInfo);

	}

	// 获取理财需求信息
	private void getLicaiInfo() {
		getLicaiHCon = RequestInfo(FinanceNeedFragment.this,
				Constants.requestType.Search, InterfaceInfo.Finance_Licai_Get,
				PersonArchiving.custID, getLicaiInfo);

	}

	private void getProductInfo() {
		getProductHCon = RequestInfo(FinanceNeedFragment.this,
				Constants.requestType.Search,
				InterfaceInfo.Finance_Product_Get, PersonArchiving.custID,
				getProductInfo);

	}

	private void getDepositInfo() {

		getDepositHCon = RequestInfo(FinanceNeedFragment.this,
				Constants.requestType.Search,
				InterfaceInfo.Finance_Deposit_Get, PersonArchiving.custID,
				getDepositInfo);
	}

	private void refreshInfo(int connectionIndex) {
		// TODO Auto-generated method stub
		ArrayList<ItemModel> ViewInfo = new ArrayList<ItemModel>();
		switch (connectionIndex) {
		case getOtherInfo:
			if (OtherList.size() > 0) {
				for (FinanceOther item : OtherList) {
					ItemModel theInfo = new ItemModel();
					theInfo.theItemModel[0] = item.prd_name;
					theInfo.theItemModel[1] = item.des;
					theInfo.theItemModel[2] = item.occur_time;
//					theInfo.theItemModel[3] = item.remind_time;
					theInfo.theItemModel[3] = item.RECORD_USERNAME;
					theInfo.theItemModel[4] = item.RECORD_TIME;
					theInfo.pkid = item.pkid;
					ViewInfo.add(theInfo);
				}
				final ArchivingItemAdapter ViewAdapter_1 = new ArchivingItemAdapter(
						getActivity(),
						financeneed_other_list.getRightViewWidth(), ViewInfo,
						5, new OnRightItemClickListener() {

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
															FinanceNeedFragment.this,
															Constants.requestType.Delete,
															InterfaceInfo.Finance_Other_Delete,
															pkid, delOtherInfo);

												}
											}
										});
								deleteDialog.show();
							}

						});
				financeneed_other_list.setPullstatus(false, true);// 设置是否可以上下拉动
				financeneed_other_list.setAdapter(ViewAdapter_1);
				financeneed_other_list
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								ItemModel theInfo = ViewAdapter_1
										.getItem(position);

								for (FinanceOther item : OtherList) {
									if (item.pkid.equals(theInfo.pkid)) {
										tempother = item;
										otherRType = requestType.Search;
										showOtherView(otherRType);

									}

								}
							}
						});
				ViewAdapter_1.setListViewHeight(financeneed_other_list);
			} else {
				makeData(connectionIndex);
			}
			break;
		case getLoanInfo:
			if (LoanList.size() > 0) {
				for (FinanceLoan item : LoanList) {
					ItemModel theInfo = new ItemModel();
					theInfo.theItemModel[0] = item.amount;
					// theInfo.theItemModel[1] = NewCatevalue
					// .getLabel(getActivity(), NewCatevalue.loanCateType,
					// item.kind);

					theInfo.theItemModel[1] = NewCatevalue.getLabel(
							getActivity(), NewCatevalue.loanUseType,
							item.purpose);

					theInfo.theItemModel[2] = NewCatevalue.getLabel(
							getActivity(), NewCatevalue.warrantType,
							item.warrant);

					theInfo.theItemModel[3] = item.turn_over;

					theInfo.theItemModel[4] = item.occur_time;
//					theInfo.theItemModel[5] = item.remind_time;
					theInfo.theItemModel[5] = item.des;
					theInfo.pkid = item.pkid;
					ViewInfo.add(theInfo);
				}
				final ArchivingItemAdapter ViewAdapter_2 = new ArchivingItemAdapter(
						getActivity(),
						financeneed_loan_list.getRightViewWidth(), ViewInfo, 6,
						new OnRightItemClickListener() {

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
															FinanceNeedFragment.this,
															Constants.requestType.Delete,
															InterfaceInfo.Finance_Loan_Delete,
															pkid, delLoanInfo);

												}
											}
										});
								deleteDialog.show();
							}
						});
				financeneed_loan_list.setPullstatus(false, true);// 设置是否可以上下拉动
				financeneed_loan_list.setAdapter(ViewAdapter_2);
				financeneed_loan_list
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								ItemModel theInfo = ViewAdapter_2
										.getItem(position);

								for (FinanceLoan item : LoanList) {
									if (item.pkid.equals(theInfo.pkid)) {
										tempLoan = item;
										loanRType = requestType.Search;
										showLoanView(loanRType);

									}
								}
							}
						});
				ViewAdapter_2.setListViewHeight(financeneed_loan_list);
			} else {
				makeData(connectionIndex);
			}
			break;

		case getProductInfo:
			if (ProductList.size() > 0) {
				for (FinanceProduct item : ProductList) {
					ItemModel theInfo = new ItemModel();
					theInfo.theItemModel[0] = NewCatevalue.getLabel(
							getActivity(), NewCatevalue.kindType, item.kind);

					theInfo.theItemModel[1] = item.des;
//					theInfo.theItemModel[2] = item.remind_time;
					theInfo.theItemModel[2] = item.occur_time;
					theInfo.theItemModel[3] = item.RECORD_USERNAME;
					theInfo.theItemModel[4] = item.RECORD_TIME;
					theInfo.pkid = item.pkid;
					ViewInfo.add(theInfo);
				}
				final ArchivingItemAdapter ViewAdapter_3 = new ArchivingItemAdapter(
						getActivity(),
						financeneed_product_list.getRightViewWidth(), ViewInfo,
						5, new OnRightItemClickListener() {

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
															FinanceNeedFragment.this,
															Constants.requestType.Delete,
															InterfaceInfo.Finance_Product_Delete,
															pkid,
															delProductInfo);
												}
											}
										});
								deleteDialog.show();
							}

						});
				financeneed_product_list.setPullstatus(false, true);// 设置是否可以上下拉动
				financeneed_product_list.setAdapter(ViewAdapter_3);
				financeneed_product_list
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								ItemModel theInfo = ViewAdapter_3
										.getItem(position);

								for (FinanceProduct item : ProductList) {
									if (item.pkid.equals(theInfo.pkid)) {
										tempproduct = item;
										productRType = requestType.Search;
										showProductView(productRType,false);

									}
								}
							}
						});
				ViewAdapter_3.setListViewHeight(financeneed_product_list);
			} else {
				makeData(connectionIndex);
			}
			break;
		case getDepositInfo:
			if (DepositList.size() > 0) {
				for (FinanceDeposit item : DepositList) {
					ItemModel theInfo = new ItemModel();
					theInfo.theItemModel[0] = item.deposit_amount;
					theInfo.theItemModel[1] = item.ap_time;
//					theInfo.theItemModel[2] = item.remind_time;
					theInfo.theItemModel[2] = item.des;
					theInfo.theItemModel[3] = item.RECORD_USER;
					theInfo.theItemModel[4] = item.RECORD_TIME;
					theInfo.pkid = item.pkid;
					ViewInfo.add(theInfo);//添加条目
				}
				final ArchivingItemAdapter ViewAdapter_4 = new ArchivingItemAdapter(
						getActivity(),
						financeneed_deposit_list.getRightViewWidth(), ViewInfo,
						5, new OnRightItemClickListener() {

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
															FinanceNeedFragment.this,
															Constants.requestType.Delete,
															InterfaceInfo.Finance_Deposit_Delete,
															pkid,
															delDepositInfo);

												}
											}
										});
								deleteDialog.show();
							}

						});
				financeneed_deposit_list.setPullstatus(false, true);// 设置是否可以上下拉动
				financeneed_deposit_list.setAdapter(ViewAdapter_4);
				financeneed_deposit_list
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								ItemModel theInfo = ViewAdapter_4
										.getItem(position);

								for (FinanceDeposit item : DepositList) {
									if (item.pkid.equals(theInfo.pkid)) {
										tempDeposit = item;
										depositRType = requestType.Search;
										showDepositView(depositRType);

									}
								}
							}
						});
				ViewAdapter_4.setListViewHeight(financeneed_deposit_list);
			} else {
				makeData(connectionIndex);
			}
			break;
		case getLicaiInfo:
			if (LicaiList.size() > 0) {
				for (FinanceLicai item : LicaiList) {
					ItemModel theInfo = new ItemModel();
					theInfo.theItemModel[0] = item.AMOUNT;

					// fin_status
					String statusInfo = NewCatevalue.getLabel(getActivity(),
							NewCatevalue.fin_status, item.STATUS);
					theInfo.theItemModel[1] = statusInfo;
					theInfo.theItemModel[2] = item.OCCUR_TIME;
//					theInfo.theItemModel[3] = item.REMIND_TIME;
					theInfo.theItemModel[3] = item.DES;

					theInfo.pkid = item.pkid;
					ViewInfo.add(theInfo);
				}
				final ArchivingItemAdapter ViewAdapter_5 = new ArchivingItemAdapter(
						getActivity(),
						financeneed_licai_list.getRightViewWidth(), ViewInfo,
						4, new OnRightItemClickListener() {

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
															FinanceNeedFragment.this,
															Constants.requestType.Delete,
															InterfaceInfo.Finance_Licai_Delete,
															pkid, delLicaiInfo);

												}
											}
										});
								deleteDialog.show();
							}

						});
				financeneed_licai_list.setPullstatus(false, true);// 设置是否可以上下拉动
				financeneed_licai_list.setAdapter(ViewAdapter_5);
				financeneed_licai_list
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								ItemModel theInfo = ViewAdapter_5
										.getItem(position);

								for (FinanceLicai item : LicaiList) {
									if (item.pkid.equals(theInfo.pkid)) {
										tempLicai = item;
										licaiRType = requestType.Search;
										showLicaiView(licaiRType);

									}
								}
							}
						});
				ViewAdapter_5.setListViewHeight(financeneed_licai_list);
			} else {
				makeData(connectionIndex);
			}
			break;

		default:
			break;
		}
	}

	// 创建电子产品需求信息弹出框
	private void showProductView(Constants.requestType theType,boolean isCreateNew) {
		if (popup_product == null) {
			View popupView = factory.inflate(
					R.layout.popwindow_finance_product, null);
			// 创建一个PopuWidow对象
			popup_product = new Dialog(getActivity(), R.style.DialogTheme);
			popup_product.setContentView(popupView);
			// 初始化控件
			initProductView(popupView);
			setProductListener();
		}
		if (!popup_product.isShowing()) {
			if (theType == requestType.Insert) {
				productIsEdit = true;
				clearProduct(isCreateNew);
                ll_finance_product_status.setVisibility(View.GONE);
            } else if (theType == requestType.Search) {
				productIsEdit = false;
				setProductView(productIsEdit);
                ll_finance_product_status.setVisibility(View.VISIBLE);
            }
			popup_product.show();
		}
	}

	private void setProductView(boolean IsEdit) {
		// TODO Auto-generated method stub
		finance_product_kind.setClickable(IsEdit);// 种类
		finance_product_occur_time.setClickable(IsEdit);// 发生日期
//		finance_product_remind_time.setClickable(IsEdit);// 提醒时间
		finance_product_des.setEnabled(IsEdit);// 备注
//		finance_product_state.setClickable(IsEdit);//
//        finance_product_state.setEnabled(IsEdit);
        product_edit_ll.setVisibility(View.VISIBLE);
		if (IsEdit) {
			product_title.setText(productTitle[2]);
			product_edit_text.setText(comTitle[1]);
		} else {
			product_title.setText(productTitle[1]);
			product_edit_text.setText(comTitle[0]);
		}
		if (tempproduct != null) {
			finance_product_occur_time.setText(tempproduct.occur_time);// 发生日期
//			finance_product_remind_time.setText(tempproduct.remind_time);// 提醒时间
			finance_product_des.setText(tempproduct.des);// 备注
			product_kindID=tempproduct.kind;
			finance_product_kind.setText(NewCatevalue.getLabel(getActivity(),
					NewCatevalue.kindType, tempproduct.kind));
			product_stateID=tempproduct.status;
			finance_product_state.setText(NewCatevalue.getLabel(getActivity(),
                    NewCatevalue.fin_status, tempproduct.status));
		}

	}

	// 创建理财需求信息弹出框
	private void showLicaiView(Constants.requestType theType) {
		if (popup_licai == null) {
			View popupView = factory.inflate(R.layout.popwindow_finance_licai,
					null);
			// 创建一个PopuWidow对象
			popup_licai = new Dialog(getActivity(), R.style.DialogTheme);
			popup_licai.setContentView(popupView);
			// 初始化控件
			initLicaiView(popupView);
			setLicaiListener();
		}
		if (!popup_licai.isShowing()) {
			if (theType == requestType.Insert) {
				licaiIsEdit = true;
				clearLicai();
				ll_licai_status.setVisibility(View.GONE);
			} else if (theType == requestType.Search) {
				licaiIsEdit = false;
				setLicaiView(licaiIsEdit);
				ll_licai_status.setVisibility(View.VISIBLE);
			}
			popup_licai.show();
		}
	}

	// 设置理财需求显示值
	private void setLicaiView(boolean IsEdit) {
		// TODO Auto-generated method stub

		finance_licai_edit_ll.setVisibility(View.VISIBLE);
		finance_licai_money.setEnabled(IsEdit);// 金额
		finance_licai_occur_time.setClickable(IsEdit);// 预约时间
//		finance_licai_state.setClickable(IsEdit);// 状态
//		finance_licai_remind_time.setClickable(IsEdit);// 提醒时间
		finance_licai_des.setEnabled(IsEdit);// 备注

		if (IsEdit) {
			finance_licai_title.setText(licaiTitle[2]);
			licai_edit_text.setText(comTitle[1]);
		} else {
			finance_licai_title.setText(licaiTitle[1]);
			licai_edit_text.setText(comTitle[0]);
		}

		if (tempLicai != null) {
			finance_licai_money.setText(tempLicai.AMOUNT);// 金额
			finance_licai_occur_time.setText(tempLicai.OCCUR_TIME);// 预约时间
//			finance_licai_remind_time.setText(tempLicai.REMIND_TIME);// 提醒时间
			finance_licai_des.setText(tempLicai.DES);// 备注
			licai_stateID = tempLicai.STATUS;
			String theState = NewCatevalue.getLabel(getActivity(),
					NewCatevalue.fin_status, licai_stateID);
			finance_licai_state.setText(theState);// 状态
		}
	}

	private void clearLicai() {
		finance_licai_money.setText("");// 金额
		finance_licai_occur_time.setText("");// 预约时间
		finance_licai_state.setText("");// 状态
//		finance_licai_remind_time.setText("");// 提醒时间
		finance_licai_des.setText("");// 备注
		finance_licai_money.setEnabled(true);// 金额
		finance_licai_occur_time.setClickable(true);// 预约时间
//		finance_licai_state.setClickable(true);// 状态
		finance_licai_state.setClickable(false);// 状态
		finance_licai_state.setText("未处理");
//		finance_licai_remind_time.setClickable(true);// 提醒时间
		finance_licai_des.setEnabled(true);// 备注
		finance_licai_edit_ll.setVisibility(View.GONE);
		finance_licai_title.setText(licaiTitle[0]);
	}

	// 理财需求事件
	private void setLicaiListener() {
		// TODO Auto-generated method stub
		finance_licai_edit_ll.setOnClickListener(this);
//		finance_licai_state.setOnClickListener(this);
		finance_licai_btn_ok.setOnClickListener(this);
		finance_licai_btn_cancel.setOnClickListener(this);
//		finance_licai_remind_time.setOnClickListener(this);
		finance_licai_occur_time.setOnClickListener(this);
	}

	// 初始化理财需求弹出框控件
	private void initLicaiView(View popupView) {
		// TODO Auto-generated method stub
		finance_licai_title = (TextView) popupView
				.findViewById(R.id.finance_licai_title);// 标题
		finance_licai_edit_ll = (LinearLayout) popupView
				.findViewById(R.id.finance_licai_edit_ll);// 编辑按钮
		licai_edit_text = (TextView) popupView
				.findViewById(R.id.finance_licai_edit_text);// 编辑文字
		finance_licai_money = (EditText) popupView
				.findViewById(R.id.popwindow_finance_licai_money);// 金额
		finance_licai_occur_time = (TextView) popupView
				.findViewById(R.id.popwindow_finance_licai_occur_time);// 预约时间
		finance_licai_state = (TextView) popupView
				.findViewById(R.id.popwindow_finance_licai_state);// 状态
		ll_licai_status = (LinearLayout) popupView
				.findViewById(R.id.ll_licai_status);// 状态
//		finance_licai_remind_time = (TextView) popupView
//				.findViewById(R.id.popwindow_finance_licai_remind_time);// 提醒时间
		finance_licai_des = (EditText) popupView
				.findViewById(R.id.popwindow_finance_licai_des);// 备注
		finance_licai_btn_ok = (Button) popupView
				.findViewById(R.id.popwindow_finance_licai_btn_ok);// 确认按钮
		finance_licai_btn_cancel = (Button) popupView
				.findViewById(R.id.popwindow_finance_licai_btn_cancel);// 取消按钮
	}

	// 创建贷款需求信息弹出框
	private void showLoanView(Constants.requestType theType) {
		if (popup_loan == null) {
			View popupView = factory.inflate(R.layout.popwindow_finance_loan,
					null);
			// 创建一个PopuWidow对象
			popup_loan = new Dialog(getActivity(), R.style.DialogTheme);
			popup_loan.setContentView(popupView);
			// 初始化控件
			initLoanView(popupView);
			setLoanListener();
		}
		if (!popup_loan.isShowing()) {
			if (theType == requestType.Insert) {
				loanIsEdit = true;
				clearLoan();
			} else if (theType == requestType.Search) {
				loanIsEdit = false;
				setLoanView(loanIsEdit);
			}
			popup_loan.show();
		}
	}

	private void setLoanView(boolean IsEdit) {
		// TODO Auto-generated method stub
		finance_loan_amount.setEnabled(IsEdit);// 金额
		// finance_loan_kind.setClickable(IsEdit);// 种类
		finance_loan_purpose.setClickable(IsEdit);// 用途
		finance_loan_warrant.setClickable(IsEdit);// 可提供的担保方式
		finance_loan_turn_over.setEnabled(IsEdit);// 周转期限
//		finance_loan_status.setClickable(IsEdit);// 状态
        ll_finance_loan_status.setVisibility(View.VISIBLE);
        finance_loan_occur_time.setClickable(IsEdit);// 发生时间
//		finance_loan_remind_time.setClickable(IsEdit);// 提醒时间
		finance_loan_des.setEnabled(IsEdit);// 备注
		loan_edit_ll.setVisibility(View.VISIBLE);
		if (IsEdit) {
			loan_title.setText(loanTitle[2]);
			loan_edit_text.setText(comTitle[1]);
		} else {
			loan_title.setText(loanTitle[1]);
			loan_edit_text.setText(comTitle[0]);
		}

		if (tempLoan != null) {
			finance_loan_amount.setText(tempLoan.amount);// 金额
			finance_loan_occur_time.setText(tempLoan.occur_time);// 发生时间
//			finance_loan_remind_time.setText(tempLoan.remind_time);// 提醒时间
			finance_loan_des.setText(tempLoan.des);// 备注
			// finance_loan_kind.setText(NewCatevalue.getLabel(getActivity(),
			// NewCatevalue.loanCateType, tempLoan.kind));// 种类
			loan_purposeID=tempLoan.purpose;
			finance_loan_purpose.setText(NewCatevalue.getLabel(getActivity(),
					NewCatevalue.loanUseType, tempLoan.purpose));// 用途
			loan_warrantID=tempLoan.warrant;
			finance_loan_warrant.setText(NewCatevalue.getLabel(getActivity(),
					NewCatevalue.warrantType, tempLoan.warrant));// 可提供的担保方式
			
			finance_loan_turn_over.setText(tempLoan.turn_over);// 周转期限
			loan_statusID= tempLoan.status;
			finance_loan_status.setText(NewCatevalue.getLabel(getActivity(),
					NewCatevalue.fin_status, tempLoan.status));// 状态
		}


    }

	// 创建其他需求信息弹出框
	private void showOtherView(Constants.requestType theType) {
		if (popup_other == null) {
			View popupView = factory.inflate(R.layout.popwindow_finance_other,
					null);
			// 创建一个PopuWidow对象
			popup_other = new Dialog(getActivity(), R.style.DialogTheme);
			popup_other.setContentView(popupView);
			// 初始化控件
			initOtherView(popupView);
			setOtherListener();
		}
		if (!popup_other.isShowing()) {
			if (theType == requestType.Insert) {
				otherIsEdit = true;
				clearOther();
			} else if (theType == requestType.Search) {
				otherIsEdit = false;
				setOtherView(otherIsEdit);
			}
			popup_other.show();
		}
	}

	private void setOtherView(boolean IsEdit) {
		// TODO Auto-generated method stub

		finance_other_prd_name.setEnabled(IsEdit);// 种类
		finance_other_occur_time.setClickable(IsEdit);// 发生日期
//		finance_other_remind_time.setClickable(IsEdit);// 提醒时间
		finance_other_des.setEnabled(IsEdit);// 备注
		other_edit_ll.setVisibility(View.VISIBLE);
		if (IsEdit) {
			other_title.setText(otherTitle[2]);
			other_edit_text.setText(comTitle[1]);
		} else {
			other_title.setText(otherTitle[1]);
			other_edit_text.setText(comTitle[0]);
		}
		if (tempother != null) {
			finance_other_prd_name.setText(tempother.prd_name);// 种类
			finance_other_occur_time.setText(tempother.occur_time);// 发生日期
//			finance_other_remind_time.setText(tempother.remind_time);// 提醒时间
			finance_other_des.setText(tempother.des);// 备注
		}

	}

	// 创建存款需求信息弹出框
	private void showDepositView(Constants.requestType theType) {
		if (popup_deposit == null) {
			View popupView = factory.inflate(
					R.layout.popwindow_finance_deposit, null);
			// 创建一个PopuWidow对象
			popup_deposit = new Dialog(getActivity(), R.style.DialogTheme);
			popup_deposit.setContentView(popupView);
			// 初始化控件
			initDepositView(popupView);
			setDepositListener();
		}
		if (!popup_deposit.isShowing()) {
			if (theType == requestType.Insert) {
				depositIsEdit = true;
				clearDeposit();
			} else if (theType == requestType.Search) {
				depositIsEdit = false;
				setDepositView(depositIsEdit);
			}
			popup_deposit.show();
		}
	}

	private void setDepositView(boolean IsEdit) {
		deposit_edit_ll.setVisibility(View.VISIBLE);
		finance_deposit_money.setEnabled(IsEdit);
		finance_deposit_date.setClickable(IsEdit);
//		finance_deposit_reminddate.setClickable(IsEdit);
		finance_deposit_info.setEnabled(IsEdit);
		if (IsEdit) {
			deposit_title.setText(depositTitle[2]);
			deposit_edit_text.setText(comTitle[1]);
		} else {
			deposit_title.setText(depositTitle[1]);
			deposit_edit_text.setText(comTitle[0]);
		}

		if (tempDeposit != null) {
			finance_deposit_money.setText(tempDeposit.deposit_amount);// 存款金额
			finance_deposit_date.setText(tempDeposit.ap_time);// 存款时间
//			finance_deposit_reminddate.setText(tempDeposit.remind_time);// 提醒时间
			finance_deposit_info.setText(tempDeposit.des);// 内容描述
		}

	}

	/**
	 * 造一组空数据
	 */
	private void makeData(int index) {

		ItemModel itemInfo = new ItemModel();
		ArrayList<ItemModel> ViewInfo = new ArrayList<ItemModel>();
		ViewInfo.add(itemInfo);

		ArchivingItemAdapter fourViewAdapter = new ArchivingItemAdapter(
				getActivity(), 0, ViewInfo, 4, null);
		ArchivingItemAdapter fiveViewAdapter = new ArchivingItemAdapter(
				getActivity(), 0, ViewInfo, 5, null);
		ArchivingItemAdapter sixViewAdapter = new ArchivingItemAdapter(
				getActivity(), 0, ViewInfo, 6, null);
		ArchivingItemAdapter sevenViewAdapter = new ArchivingItemAdapter(
				getActivity(), 0, ViewInfo, 7, null);
		switch (index) {
		case getOtherInfo:
			financeneed_other_list.setAdapter(fiveViewAdapter);
			sixViewAdapter.setListViewHeight(financeneed_other_list);
			break;
		case getLoanInfo:
			financeneed_loan_list.setAdapter(sixViewAdapter);
			sevenViewAdapter.setListViewHeight(financeneed_loan_list);
			break;
		case getProductInfo:
			financeneed_product_list.setAdapter(fiveViewAdapter);
			sixViewAdapter.setListViewHeight(financeneed_product_list);
			break;
		case getDepositInfo:
			financeneed_deposit_list.setAdapter(fiveViewAdapter);
			sixViewAdapter.setListViewHeight(financeneed_deposit_list);
			break;
		case getLicaiInfo:
			financeneed_licai_list.setAdapter(fourViewAdapter);
			sixViewAdapter.setListViewHeight(financeneed_licai_list);
			break;

		default:
			break;
		}

	}

	private void setOtherListener() {
		// TODO Auto-generated method stub
		finance_other_btn_ok.setOnClickListener(this);
		finance_other_btn_cancel.setOnClickListener(this);
//		finance_other_remind_time.setOnClickListener(this);
		finance_other_occur_time.setOnClickListener(this);
		other_edit_ll.setOnClickListener(this);
	}

	private void clearOther() {
		// TODO Auto-generated method stub
		finance_other_prd_name.setText("");// 产品名称
		finance_other_occur_time.setText("");// 发生时间
//		finance_other_remind_time.setText("");// 提醒时间
		finance_other_des.setText("");// 描述

		finance_other_prd_name.setEnabled(true);// 种类
		finance_other_occur_time.setClickable(true);// 发生日期
//		finance_other_remind_time.setClickable(true);// 提醒时间
		finance_other_des.setEnabled(true);// 备注

		other_edit_ll.setVisibility(View.GONE);
		other_title.setText(otherTitle[0]);
	}

	private void initOtherView(View popupView) {
		// TODO Auto-generated method stub
		finance_other_btn_cancel = (Button) popupView
				.findViewById(R.id.popwindow_finance_other_btn_cancel);// 取消
		finance_other_btn_ok = (Button) popupView
				.findViewById(R.id.popwindow_finance_other_btn_ok);// 确认
		finance_other_prd_name = (EditText) popupView
				.findViewById(R.id.popwindow_finance_other_prd_name);// 产品名称
		finance_other_occur_time = (TextView) popupView
				.findViewById(R.id.popwindow_finance_other_occur_time);// 发生时间
//		finance_other_remind_time = (TextView) popupView
//				.findViewById(R.id.popwindow_finance_other_remind_time);// 提醒时间
		finance_other_des = (EditText) popupView
				.findViewById(R.id.popwindow_finance_other_des);// 描述

		other_title = (TextView) popupView
				.findViewById(R.id.finance_other_title);// 标题
		other_edit_ll = (LinearLayout) popupView
				.findViewById(R.id.finance_other_edit_ll);// 编辑按钮
		other_edit_text = (TextView) popupView
				.findViewById(R.id.finance_other_edit_text);// 编辑文字
	}

	private void setLoanListener() {
		// TODO Auto-generated method stub
		finance_loan_btn_ok.setOnClickListener(this);
		finance_loan_btn_cancel.setOnClickListener(this);
		// finance_loan_kind.setOnClickListener(this);
		finance_loan_purpose.setOnClickListener(this);
		finance_loan_warrant.setOnClickListener(this);
		// finance_loan_turn_over.setOnClickListener(this);
//		finance_loan_status.setOnClickListener(this);
		finance_loan_occur_time.setOnClickListener(this);
//		finance_loan_remind_time.setOnClickListener(this);
		loan_edit_ll.setOnClickListener(this);
	}

	private void clearLoan() {
		// TODO Auto-generated method stub
		finance_loan_amount.setText("");// 金额
		// finance_loan_kind.setText("");// 种类
		finance_loan_purpose.setText("");// 用途
		finance_loan_warrant.setText("");// 可提供的担保方式
		finance_loan_turn_over.setText("");// 周转期限
//		finance_loan_status.setText("");// 状态
		finance_loan_occur_time.setText("");// 发生时间
//		finance_loan_remind_time.setText("");// 提醒时间
		finance_loan_des.setText("");// 备注

		finance_loan_amount.setEnabled(true);// 金额
		// finance_loan_kind.setClickable(true);// 种类
		finance_loan_purpose.setClickable(true);// 用途
		finance_loan_warrant.setClickable(true);// 可提供的担保方式
		finance_loan_turn_over.setEnabled(true);// 周转期限
//		finance_loan_status.setClickable(true);// 状态
		finance_loan_occur_time.setClickable(true);// 发生时间
//		finance_loan_remind_time.setClickable(true);// 提醒时间
		finance_loan_des.setEnabled(true);// 备注

		loan_edit_ll.setVisibility(View.GONE);
		loan_title.setText(loanTitle[0]);

		// loan_kindID = "";
//		loan_statusID = "";
		// loan_turn_overID = "";
		loan_warrantID = "";// 可提供的担保方式
		loan_purposeID = "";// 用途

        ll_finance_loan_status.setVisibility(View.GONE);
	}

	private void initLoanView(View popupView) {
		// TODO Auto-generated method stub
		finance_loan_amount = (EditText) popupView
				.findViewById(R.id.popwindow_finance_loan_amount);// 金额
		// finance_loan_kind = (EditText) popupView
		// .findViewById(R.id.popwindow_finance_loan_kind);// 种类
		finance_loan_purpose = (TextView) popupView
				.findViewById(R.id.popwindow_finance_loan_purpose);// 用途
		finance_loan_warrant = (TextView) popupView
				.findViewById(R.id.popwindow_finance_loan_warrant);// 可提供的担保方式
		finance_loan_turn_over = (EditText) popupView
				.findViewById(R.id.popwindow_finance_loan_turn_over);// 周转期限
		finance_loan_status = (TextView) popupView
				.findViewById(R.id.popwindow_finance_loan_status);// 状态
		finance_loan_occur_time = (TextView) popupView
				.findViewById(R.id.popwindow_finance_loan_occur_time);// 发生时间
//		finance_loan_remind_time = (TextView) popupView
//				.findViewById(R.id.popwindow_finance_loan_remind_time);// 提醒时间
		finance_loan_des = (EditText) popupView
				.findViewById(R.id.popwindow_finance_loan_des);// 备注
		finance_loan_btn_cancel = (Button) popupView
				.findViewById(R.id.popwindow_finance_loan_btn_cancel);// 取消
		finance_loan_btn_ok = (Button) popupView
				.findViewById(R.id.popwindow_finance_loan_btn_ok);// 确认
		loan_title = (TextView) popupView.findViewById(R.id.finance_loan_title);// 标题
		loan_edit_ll = (LinearLayout) popupView
				.findViewById(R.id.finance_loan_edit_ll);// 编辑按钮
		loan_edit_text = (TextView) popupView
				.findViewById(R.id.finance_loan_edit_text);// 编辑文字
        ll_finance_loan_status = (LinearLayout) popupView
				.findViewById(R.id.ll_loan_status);// 贷款状态
	}

	private void setDepositListener() {
		// TODO Auto-generated method stub
		finance_deposit_btn_cancel.setOnClickListener(this);
		finance_deposit_btn_ok.setOnClickListener(this);
		finance_deposit_date.setOnClickListener(this);
//		finance_deposit_reminddate.setOnClickListener(this);
		deposit_edit_ll.setOnClickListener(this);
	}

	private void clearDeposit() {
		// TODO Auto-generated method stub

		finance_deposit_money.setText("");// 存款金额
		finance_deposit_date.setText("");// 存款时间
//		finance_deposit_reminddate.setText("");// 提醒时间
		finance_deposit_info.setText("");// 内容描述
		finance_deposit_money.setEnabled(true);
		finance_deposit_date.setClickable(true);
//		finance_deposit_reminddate.setClickable(true);
		finance_deposit_info.setEnabled(true);
		deposit_edit_ll.setVisibility(View.GONE);
		deposit_title.setText(depositTitle[0]);
	}

	private void initDepositView(View popupView) {
		// TODO Auto-generated method stub
		finance_deposit_btn_cancel = (Button) popupView
				.findViewById(R.id.popwindow_finance_deposit_btn_cancel);// 取消
		finance_deposit_btn_ok = (Button) popupView
				.findViewById(R.id.popwindow_finance_deposit_btn_ok);// 确认
		finance_deposit_money = (EditText) popupView
				.findViewById(R.id.popwindow_finance_deposit_money);// 存款金额
		finance_deposit_date = (TextView) popupView
				.findViewById(R.id.popwindow_finance_deposit_date);// 存款时间
//		finance_deposit_reminddate = (TextView) popupView
//				.findViewById(R.id.popwindow_finance_deposit_reminddate);// 提醒时间
		finance_deposit_info = (EditText) popupView
				.findViewById(R.id.popwindow_finance_deposit_info);// 内容描述

		deposit_title = (TextView) popupView
				.findViewById(R.id.finance_deposit_title);// 标题
		deposit_edit_ll = (LinearLayout) popupView
				.findViewById(R.id.finance_deposit_edit_ll);// 编辑按钮
		deposit_edit_text = (TextView) popupView
				.findViewById(R.id.finance_deposit_edit_text);// 编辑文字
	}

	private void clearProduct(boolean isCreateNew) {

        finance_product_kind.setText("");// 种类
        finance_product_occur_time.setText("");// 发生日期
//        finance_product_remind_time.setText("");// 提醒时间
        finance_product_des.setText("");// 备注
//        if (isCreateNew)
//            finance_product_state.setText("未处理");// 状态
//        else
//            finance_product_state.setText("");// 状态
        product_kindID = "";
        product_stateID = "";

        finance_product_kind.setClickable(true);// 种类
        finance_product_occur_time.setClickable(true);// 发生日期
//        finance_product_remind_time.setClickable(true);// 提醒时间
        finance_product_des.setEnabled(true);// 备注
        finance_product_state.setClickable(false);// 状态
//        if (isCreateNew) {
//            finance_product_state.setClickable(false);// 状态
//            finance_product_state.setEnabled(false);// 状态
//        }
        product_edit_ll.setVisibility(View.GONE);
        product_title.setText(productTitle[0]);

	}

	private void initProductView(View popupView) {
		// TODO Auto-generated method stub
		finance_product_btn_cancel = (Button) popupView
				.findViewById(R.id.popwindow_finance_product_btn_cancel);// 取消
		finance_product_btn_ok = (Button) popupView
				.findViewById(R.id.popwindow_finance_product_btn_ok);// 确认
		finance_product_kind = (TextView) popupView
				.findViewById(R.id.popwindow_finance_product_kind);// 种类
		finance_product_occur_time = (TextView) popupView
				.findViewById(R.id.popwindow_finance_product_occur_time);// 发生日期
//		finance_product_remind_time = (TextView) popupView
//				.findViewById(R.id.popwindow_finance_product_remind_time);// 提醒时间
		finance_product_des = (EditText) popupView
				.findViewById(R.id.popwindow_finance_product_des);// 备注
		finance_product_state = (TextView) popupView
				.findViewById(R.id.popwindow_finance_product_state);// 状态
        ll_finance_product_status = (LinearLayout) popupView.findViewById(R.id.ll_ebank_status);// 状态

		product_title = (TextView) popupView
				.findViewById(R.id.finance_product_title);// 标题
		product_edit_ll = (LinearLayout) popupView
				.findViewById(R.id.finance_product_edit_ll);// 编辑按钮
		product_edit_text = (TextView) popupView
				.findViewById(R.id.finance_product_edit_text);// 编辑文字
	}

	private void setProductListener() {
		// TODO Auto-generated method stub
		finance_product_btn_cancel.setOnClickListener(this);
		finance_product_btn_ok.setOnClickListener(this);
		finance_product_kind.setOnClickListener(this);
		finance_product_occur_time.setOnClickListener(this);
//		finance_product_remind_time.setOnClickListener(this);
		product_edit_ll.setOnClickListener(this);
//		finance_product_state.setOnClickListener(this);
	}

	private void setListener() {
		// TODO Auto-generated method stub
		financeneed_nextbtn.setOnClickListener(this);
		creat_product.setOnClickListener(this);
		creat_other.setOnClickListener(this);
		creat_loan.setOnClickListener(this);
		creat_deposit.setOnClickListener(this);
		creat_licai.setOnClickListener(this);
	}

	private void setView(View view) {
		// TODO Auto-generated method stub
		financeneed_nextbtn = (Button) view
				.findViewById(R.id.financeneed_nextbtn);
		financeneed_product_list = (SwipeListView) view
				.findViewById(R.id.financeneed_product_list);// 产品需求
		financeneed_other_list = (SwipeListView) view
				.findViewById(R.id.financeneed_other_list);// 其他需求
		financeneed_deposit_list = (SwipeListView) view
				.findViewById(R.id.financeneed_deposit_list);// 存款需求
		financeneed_loan_list = (SwipeListView) view
				.findViewById(R.id.financeneed_loan_list);// 贷款需求
		financeneed_licai_list = (SwipeListView) view
				.findViewById(R.id.financeneed_licai_list);// 理财需求
		creat_product = (ImageView) view
				.findViewById(R.id.financeneed_creat_product);
		creat_other = (ImageView) view
				.findViewById(R.id.financeneed_creat_other);
		creat_deposit = (ImageView) view
				.findViewById(R.id.financeneed_creat_deposit);
		creat_licai = (ImageView) view
				.findViewById(R.id.financeneed_creat_licai);

		creat_loan = (ImageView) view.findViewById(R.id.financeneed_creat_loan);
		factory = LayoutInflater.from(getActivity());

	}

}