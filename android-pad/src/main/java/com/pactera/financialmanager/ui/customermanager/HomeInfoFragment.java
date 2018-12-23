package com.pactera.financialmanager.ui.customermanager;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.adapter.ArchivingItemAdapter;
import com.pactera.financialmanager.adapter.ArchivingItemAdapter.OnRightItemClickListener;
import com.pactera.financialmanager.adapter.SpinnerAdapter;
import com.pactera.financialmanager.adapter.SpinnerAdapter.CallBackItemClickListener;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog.OnDateSetListener;
import com.pactera.financialmanager.entity.CatevalueEntity;
import com.pactera.financialmanager.ui.DeleteDialog;
import com.pactera.financialmanager.ui.DeleteDialog.IsDeleteListener;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.SwipeListView;
import com.pactera.financialmanager.ui.model.HomeInfo;
import com.pactera.financialmanager.ui.model.ImMovablesInfo;
import com.pactera.financialmanager.ui.model.ItemModel;
import com.pactera.financialmanager.ui.model.MovablesInfo;
import com.pactera.financialmanager.ui.model.NewRelationshipInfo;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.CheckIdCardUtils;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.Constants.requestType;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 个人建档下家庭信息fragment
 * 
 * @author JAY
 * 
 */
public class HomeInfoFragment extends ParentFragment implements OnClickListener {

	private LayoutInflater factory;
	/*
	 * 家庭情况
	 */
	EditText homeInfo_homepeople;// 家庭人数
	EditText homeInfo_dependentPersons;// 供养人数
	EditText homeInfo_family_addr;// 家庭住址
	EditText homeInfo_totalIncome_ly;// 上年家庭总收入
	EditText homeInfo_totalOutcome_ly;// 上年家庭总支出
	EditText homeInfo_totalMoney;// 家庭资产总额
	EditText homeInfo_financialMoney;// 金融资产
	EditText homeInfo_accountsReceivable;// 应收账款
	EditText homeInfo_otherMoney;// 其他资产
	EditText homeInfo_totalFamily;// 家庭负责总额
	EditText homeInfo_myCredit;// 本行贷款
	EditText homeInfo_otherCredit;// 他行贷款
	EditText homeInfo_NPL;// 不良贷款
	EditText homeInfo_otherLiabilities;// 其他负债
	ImageView homeInfo_creat_relationship;// 创建关系人信息
	// ListView relationship_list;// 关系人ListView
	SwipeListView relationship_list;

	DecimalFormat df = new DecimalFormat("0.###");

	double theTotalMoney = 0;// 家庭资产总额
	double theFinancialMoney = 0;// 金融资产
	double theAccountsReceivable = 0;// 应收账款
	double theOtherMoney = 0;// 其他资产

	double theTotalFamily = 0;// 家庭负债总额
	double theMyCredit = 0;// 本行贷款
	double theOtherCredit = 0;// 他行贷款
	double theNPL = 0;// 不良贷款
	double theOtherLiabilities = 0;// 其他负债

	ImageView homeInfo_creat_movables;// 创建动产信息
	ImageView homeInfo_creat_immovables;// 创建不动产信息
	SwipeListView movables_list;// 动产ListView
	SwipeListView immovables_list;// 不动产ListView
	ArrayList<ItemModel> immovablesInfo = new ArrayList<ItemModel>();
	ArrayList<ItemModel> movablesInfo = new ArrayList<ItemModel>();
	ArrayList<ItemModel> relationshipInfo = new ArrayList<ItemModel>();

	HomeInfo theHome;// 家庭信息

	Button homeInfo_nextbtn;// 下一步

	// 弹出框
	private Dialog popup_contacts;
	private Dialog popup_movable;
	private Dialog popup_immovable;
	/**
	 * 创建客户关系人
	 */
	EditText rel_name;// 关系人姓名
	TextView rel_type;// 关系人类型
	EditText rel_phone;// 联系电话
	EditText rel_cer_no;// 关系人证件号码
	TextView rel_cer_type;// 关系人证件类型
	EditText contacts_address;// 联系人地址
	ImageView is_client;// 是否为本行客户
	ImageView is_holder;// 是否为本行股东
	// EditText sex;// 性别
	// EditText birth_day;// 出生日期
	// EditText education;// 教育程度
	// EditText work_unit;// 工作单位
	// EditText work_profession;// 单位类别
	// EditText position;// 职位
	// EditText occupation;// 职业
	// EditText entry_date;// 入职时间
	// EditText main_earning;// 主要经济来源
	// EditText other_earning;// 其他经济来源
	// EditText monthly_income;// 月收入
	Button creat_contacts_btn_ok;// 确认按钮
	Button creat_contacts_btn_cancel;// 取消按钮
	int isClient = isTrue;
	int isHolder = isTrue;
	TextView contacts_title;// 标题
	LinearLayout contacts_edit_ll;// 编辑按钮
	TextView contacts_edit_text;// 编辑文字
	String[] contactsTitle = { "创建关系人信息", "查看关系人信息", "编辑关系人信息" };// 标题名称
	Constants.requestType contactsRType = requestType.Search;// 默认当前状态为查询
	boolean contactsIsEdit = false;
	static NewRelationshipInfo tempContacts;

	/**
	 * 创建动产信息
	 */
	TextView movable_name;// 动产名称
	TextView buy_date;// 购买时间
	EditText buy_price;// 购置价格
	EditText des;// 备注/其他资产
	Button creat_movable_btn_ok;// 确认按钮
	Button creat_movable_btn_cancel;// 取消按钮
	TextView movable_title;// 标题
	LinearLayout movable_edit_ll;// 编辑按钮
	TextView movable_edit_text;// 编辑文字
	String[] movableTitle = { "创建动产信息", "查看动产信息", "编辑动产信息" };// 标题名称
	Constants.requestType movableRType = requestType.Search;// 默认当前状态为查询
	boolean MovableIsEdit = false;
	static MovablesInfo tempMovables;

	/**
	 * 创建不动产信息
	 */
	EditText immovable_name;// 不动产名字
	EditText immovable_address;// 不动产地址
	EditText immovable_area;// 不动产面积
	ImageView immovable_isaccreditation;// 是否办证
	int isAccreditation = isTrue;
	TextView immovable_type;// 不动产类型
	TextView immovable_buy_date;// 购买时间
	EditText immovable_buy_price;// 购置价格
	EditText immovable_des;// 备注/其他资产
	Button immovable_btn_ok;// 确认按钮
	Button immovable_btn_cancel;// 取消按钮
	TextView immovable_title;// 标题
	LinearLayout immovable_edit_ll;// 编辑按钮
	TextView immovable_edit_text;// 编辑文字
	String[] ImmovableTitle = { "创建不动产信息", "查看不动产信息", "编辑不动产信息" };// 标题名称
	Constants.requestType ImmovableRType = requestType.Search;// 默认当前状态为查询
	boolean ImmovableIsEdit = false;
	static ImMovablesInfo tempImMovables;

	/**
	 * 返回码值
	 */
	private String rel_typeID;// 关系人类型
	// private String sexID, educationID, work_professionID;
	private String immovable_ID, movable_ID;
	private String rel_cer_typeID = "";// 证件类型
	private HConnection HCon;
	private HConnection getContactsHCon;
	private HConnection getMovableHCon;
	private HConnection getImMovableHCon;
	private HConnection getHomeInfoHCon;

	/**
	 * 请求返回码
	 */
	private final int creatContactsIndex = 101;
	private final int creatMovableIndex = 102;
	private final int creatImMovableIndex = 103;
	private final int creatHomeInfoIndex = 104;
	private final int getContactsIndex = 105;
	private final int getMovableIndex = 106;
	private final int getImMovableIndex = 107;
	private final int getHomeInfoIndex = 108;
	private final int delContactsIndex = 109;
	private final int delMovableIndex = 110;
	private final int delImMovableIndex = 111;
	private final int updateContactsIndex = 112;
	private final int updateMovableIndex = 113;
	private final int updateImMovableIndex = 114;

	ArrayList<NewRelationshipInfo> ContactsList = new ArrayList<NewRelationshipInfo>();
	ArrayList<ImMovablesInfo> ImMovablesList = new ArrayList<ImMovablesInfo>();
	ArrayList<MovablesInfo> MovablesList = new ArrayList<MovablesInfo>();

	DeleteDialog deleteDialog;

	String[] comTitle = { "编辑", "取消" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_archiving_homeinfo, null);
		setView(view);
		homeInfo_totalMoney.setFocusable(false);
		homeInfo_totalFamily.setFocusable(false);
		setListener();
		getHomeInfo();// 获取家庭信息
		getImMovableInfo();// 获取不动产信息
		getMovableInfo();// 获取动产信息
		getContactsInfo();// 获取联系人信息
		return view;
	}

	// 主页面监听事件
	private void setListener() {
		// TODO Auto-generated method stub
		homeInfo_creat_relationship.setOnClickListener(this);
		homeInfo_creat_immovables.setOnClickListener(this);
		homeInfo_creat_movables.setOnClickListener(this);
		homeInfo_nextbtn.setOnClickListener(this);

		homeInfo_financialMoney.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String tempInfo = s.toString();
				theFinancialMoney = Tool.DoubleParse(tempInfo);
				theTotalMoney = theFinancialMoney + theAccountsReceivable
						+ theOtherMoney;
				homeInfo_totalMoney.setText(String.valueOf(df
						.format(theTotalMoney)));
			}
		});

		homeInfo_accountsReceivable.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String tempInfo = s.toString();
				theAccountsReceivable = Tool.DoubleParse(tempInfo);
				theTotalMoney = theFinancialMoney + theAccountsReceivable
						+ theOtherMoney;
				homeInfo_totalMoney.setText(String.valueOf(df
						.format(theTotalMoney)));
			}
		});

		homeInfo_otherMoney.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String tempInfo = s.toString();
				theOtherMoney = Tool.DoubleParse(tempInfo);
				theTotalMoney = theFinancialMoney + theAccountsReceivable
						+ theOtherMoney;
				homeInfo_totalMoney.setText(String.valueOf(df
						.format(theTotalMoney)));
			}
		});
		homeInfo_myCredit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String tempInfo = s.toString();
				theMyCredit = Tool.DoubleParse(tempInfo);

				theTotalFamily = theMyCredit + theOtherCredit + theNPL
						+ theOtherLiabilities;
				homeInfo_totalFamily.setText(String.valueOf(df
						.format(theTotalFamily)));
			}
		});
		homeInfo_otherCredit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String tempInfo = s.toString();
				theOtherCredit = Tool.DoubleParse(tempInfo);

				theTotalFamily = theMyCredit + theOtherCredit + theNPL
						+ theOtherLiabilities;
				homeInfo_totalFamily.setText(String.valueOf(df
						.format(theTotalFamily)));
			}
		});
		homeInfo_NPL.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String tempInfo = s.toString();
				theNPL = Tool.DoubleParse(tempInfo);

				theTotalFamily = theMyCredit + theOtherCredit + theNPL
						+ theOtherLiabilities;
				homeInfo_totalFamily.setText(String.valueOf(df
						.format(theTotalFamily)));
			}
		});
		homeInfo_otherLiabilities.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String tempInfo = s.toString();
				theOtherLiabilities = Tool.DoubleParse(tempInfo);

				theTotalFamily = theMyCredit + theOtherCredit + theNPL
						+ theOtherLiabilities;
				homeInfo_totalFamily.setText(String.valueOf(df
						.format(theTotalFamily)));
			}
		});

	}

	// 获取家庭信息
	private void getHomeInfo() {
		getHomeInfoHCon = RequestInfo(HomeInfoFragment.this,
				Constants.requestType.Search,
				InterfaceInfo.HomeInfo_HomeInfo_Get, PersonArchiving.custID,
				getHomeInfoIndex);
	}

	// 造个空格，图片效果
	private void makeData(int index) {
		ItemModel theInfo = new ItemModel();
		ArrayList<ItemModel> ViewInfo = new ArrayList<ItemModel>();
		ViewInfo.add(theInfo);
		ArchivingItemAdapter ViewAdapter = new ArchivingItemAdapter(
				getActivity(), 0, ViewInfo, 4, null);

		switch (index) {
		case getContactsIndex:
			relationship_list.setAdapter(ViewAdapter);
			ViewAdapter.setListViewHeight(relationship_list);
			break;
		case getMovableIndex:
			movables_list.setAdapter(ViewAdapter);
			ViewAdapter.setListViewHeight(movables_list);
			break;
		case getImMovableIndex:
			immovables_list.setAdapter(ViewAdapter);
			ViewAdapter.setListViewHeight(immovables_list);
			break;
		default:
			break;
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.homeInfofragment_nextbtn:
			sendData();
			break;
		case R.id.creat_contacts_is_client:
			isClient = isClient == isTrue ? isFalse : isTrue;
			Tool.setSelect(is_client, isClient);
			break;
		case R.id.creat_contacts_is_holder:
			isHolder = isHolder == isTrue ? isFalse : isTrue;
			Tool.setSelect(is_holder, isHolder);
			break;
		case R.id.creat_immovable_isaccreditation:
			if (ImmovableIsEdit) {
				isAccreditation = isAccreditation == isTrue ? isFalse : isTrue;
				Tool.setSelect(immovable_isaccreditation, isAccreditation);
			}
			break;
		case R.id.creat_contacts_btn_cancel:
			if (popup_contacts != null) {
				popup_contacts.dismiss();
			}
			break;
		case R.id.creat_contacts_btn_ok:
			setContacts();
			break;
		case R.id.homeInfofragment_creat_relationship:
			contactsRType = requestType.Insert;
			showContactsView(contactsRType);
			break;
		case R.id.creat_movable_btn_ok:
			setMovable();
			break;
		case R.id.creat_movable_btn_cancel:
			if (popup_movable != null) {
				popup_movable.dismiss();
			}
			break;
		case R.id.homeInfofragment_creat_movables:
			movableRType = requestType.Insert;
			showMovableView(movableRType);
			break;
		case R.id.creat_immovable_btn_ok:
			setImmovable();
			break;
		case R.id.creat_immovable_btn_cancel:
			if (popup_immovable != null) {
				popup_immovable.dismiss();
			}
			break;
		case R.id.homeInfofragment_creat_immovables:
			ImmovableRType = requestType.Insert;
			showImMovableView(ImmovableRType);
			break;

		// 关系类型
		case R.id.creat_contacts_rel_type:
			if (contactsIsEdit) {
				SpinnerAdapter.showSelectDialog(getActivity(),
						NewCatevalue.relType, rel_type,
						new CallBackItemClickListener() {

							@Override
							public void CallBackItemClick(String spinnerID) {
								// TODO Auto-generated method stub
								rel_typeID = spinnerID;
								rel_cer_typeID = "";
								rel_cer_type.setText("");

							}
						});
			}
			break;

		// 证件类型
		case R.id.creat_contacts_rel_cer_type:
			if (contactsIsEdit) {
				List<CatevalueEntity> catevalueInfo = new ArrayList<CatevalueEntity>();
				catevalueInfo = NewCatevalue.getCatevalue(getActivity(),
						NewCatevalue.key_mem_type);
				boolean isCom = false;

				if (TextUtils.isEmpty(rel_typeID)) {
					Toast.makeText(getActivity(), "请选择关系类型！",
							Toast.LENGTH_SHORT).show();
					return;
				}

				for (CatevalueEntity catevalueEntity : catevalueInfo) {
					if (rel_typeID.equals(catevalueEntity.getValue())) {
						isCom = true;
						break;
					}
				}
				if (isCom) {
					SpinnerAdapter.showSelectDialog(getActivity(),
							NewCatevalue.C_CERTTP, rel_cer_type,
							new CallBackItemClickListener() {

								@Override
								public void CallBackItemClick(String spinnerID) {
									// TODO Auto-generated method stub
									rel_cer_typeID = spinnerID;
								}
							});
				} else {
					SpinnerAdapter.showSelectDialog(getActivity(),
							NewCatevalue.CERTTP, rel_cer_type,
							new CallBackItemClickListener() {

								@Override
								public void CallBackItemClick(String spinnerID) {
									// TODO Auto-generated method stub
									rel_cer_typeID = spinnerID;
								}
							});
				}

			}
			break;
		// case R.id.creat_contacts_sex:
		// if (contactsIsEdit) {
		// SpinnerAdapter.showSelectDialog(getActivity(),
		// Catevalue.sexStr, Catevalue.sexID, sex,
		// new CallBackItemClickListener() {
		//
		// @Override
		// public void CallBackItemClick(String spinnerID) {
		// // TODO Auto-generated method stub
		// sexID = spinnerID;
		// }
		// });
		// }
		// break;
		// case R.id.creat_contacts_education:
		// if (contactsIsEdit) {
		// SpinnerAdapter.showSelectDialog(getActivity(),
		// NewCatevalue.EDUEXPERIENCE, education,
		// new CallBackItemClickListener() {
		//
		// @Override
		// public void CallBackItemClick(String spinnerID) {
		// // TODO Auto-generated method stub
		// educationID = spinnerID;
		// }
		// });
		// }
		// break;
		// case R.id.creat_contacts_work_profession:
		// if (contactsIsEdit) {
		// SpinnerAdapter.showSelectDialog(getActivity(),
		// NewCatevalue.OrgType, work_profession,
		// new CallBackItemClickListener() {
		//
		// @Override
		// public void CallBackItemClick(String spinnerID) {
		// // TODO Auto-generated method stub
		// work_professionID = spinnerID;
		// }
		// });
		// }
		// break;
		case R.id.creat_immovable_type:
			if (ImmovableIsEdit) {
				SpinnerAdapter.showSelectDialog(getActivity(),
						NewCatevalue.estateType, immovable_type,
						new CallBackItemClickListener() {

							@Override
							public void CallBackItemClick(String spinnerID) {
								// TODO Auto-generated method stub
								immovable_ID = spinnerID;
							}
						});
			}
			break;
		case R.id.creat_movable_name:
			if (MovableIsEdit) {
				SpinnerAdapter.showSelectDialog(getActivity(),
						NewCatevalue.movableType, movable_name,
						new CallBackItemClickListener() {

							@Override
							public void CallBackItemClick(String spinnerID) {
								// TODO Auto-generated method stub
								movable_ID = spinnerID;
							}
						});
			}
			break;
		case R.id.creat_movable_time:
			if (MovableIsEdit) {
				DatePickerShow(new OnDateSetListener() {

					@Override
					public void onDateSet(String year, String month, String day) {
						// TODO Auto-generated method stub
						String DateStr= year + "-"+month+ "-"+day;
						buy_date.setText(DateStr);
					}
				});
			}
			break;
		case R.id.creat_immovable_time:
			if (ImmovableIsEdit) {
				DatePickerShow(new OnDateSetListener() {

					@Override
					public void onDateSet(String year, String month, String day) {
						// TODO Auto-generated method stub
						String DateStr= year + "-"+month+ "-"+day;
						immovable_buy_date.setText(DateStr);
					}
				});
			}
			break;
		// case R.id.creat_contacts_birth_day:
		// if (contactsIsEdit) {
		// DatePickerShow(new OnDateSetListener() {
		//
		// @Override
		// public void onDateSet(int year, int month, int day) {
		// // TODO Auto-generated method stub
		// String DateStr = year + "-" + month + "-" + day;
		// birth_day.setText(DateStr);
		// }
		// });
		// }
		// break;
		// case R.id.creat_contacts_entry_date:
		// if (contactsIsEdit) {
		// DatePickerShow(new OnDateSetListener() {
		//
		// @Override
		// public void onDateSet(int year, int month, int day) {
		// // TODO Auto-generated method stub
		// String DateStr = year + "-" + month + "-" + day;
		// entry_date.setText(DateStr);
		// }
		// });
		// }
		// break;
		case R.id.immovable_edit_ll:
			ImmovableIsEdit = !ImmovableIsEdit;
			setImmovableView(ImmovableIsEdit);
			break;
		case R.id.movable_edit_ll:
			MovableIsEdit = !MovableIsEdit;
			setMovableView(MovableIsEdit);
			break;
		case R.id.contacts_edit_ll:
			contactsIsEdit = !contactsIsEdit;
			setContactsView(contactsIsEdit);
			break;
		default:
			break;
		}
	}

	// 创建家庭信息
	private void sendData() {
		// TODO Auto-generated method stub
		HomeInfo newHomeInfo = new HomeInfo();

		newHomeInfo.custID = PersonArchiving.custID;// 客户ID
		newHomeInfo.family_num = Tool.getTextValue(homeInfo_homepeople);// 家庭人口数
		newHomeInfo.support_num = Tool.getTextValue(homeInfo_dependentPersons);// 供养人数
		newHomeInfo.family_addr = Tool.getTextValue(homeInfo_family_addr);//家庭地址

		newHomeInfo.harf_year_earning = Tool
				.getTextValue(homeInfo_totalIncome_ly);// 上年家庭总收入
		newHomeInfo.harf_year_consume = Tool
				.getTextValue(homeInfo_totalOutcome_ly);// 上年家庭总支出
		newHomeInfo.family_asset = Tool.getTextValue(homeInfo_totalMoney);// 家庭资产总额
		newHomeInfo.finance_asset = Tool.getTextValue(homeInfo_financialMoney);// 金融资产
		newHomeInfo.account_recv = Tool
				.getTextValue(homeInfo_accountsReceivable);// 应收账款
		newHomeInfo.rest_asset = Tool.getTextValue(homeInfo_otherMoney);// 其他资产
		newHomeInfo.family_debt = Tool.getTextValue(homeInfo_totalFamily);// 家庭负债总额
		newHomeInfo.local_loan = Tool.getTextValue(homeInfo_myCredit);// 本行贷款
		newHomeInfo.other_loan = Tool.getTextValue(homeInfo_otherCredit);// 他行贷款
		newHomeInfo.bad_loan = Tool.getTextValue(homeInfo_NPL);// 不良贷款情况
		newHomeInfo.other_burden = Tool.getTextValue(homeInfo_otherLiabilities);// 其他负债

		HCon = RequestInfo(HomeInfoFragment.this, Constants.requestType.Insert,
				InterfaceInfo.HomeInfo_HomeInfo_Creat, newHomeInfo.toString()
						+ "&spareOne=" + PersonArchiving.toJsonForPerson(),
				creatHomeInfoIndex);
		Log.d("HCon",HCon+"");
	}

	// 动产信息处理
	private void setMovable() {
		MovablesInfo newMovables = null;
		if (movableRType == requestType.Insert) {
			newMovables = new MovablesInfo();
		} else if (movableRType == requestType.Search) {
			newMovables = tempMovables;
		}
		newMovables.custid = PersonArchiving.custID;
		newMovables.buy_date = buy_date.getText().toString();// 购买时间
		newMovables.buy_price = Tool.getTextValue(buy_price);// 购置价格
		newMovables.des = Tool.getTextValue(des);// 备注/其他资产
		if (!TextUtils.isEmpty(movable_name.getText().toString())) {
			newMovables.movable_name = movable_ID;// 动产ID
		}

		if (movableRType == requestType.Insert) {
			HCon = RequestInfo(HomeInfoFragment.this,
					Constants.requestType.Insert,
					InterfaceInfo.HomeInfo_Movable_Creat,
					newMovables.toString(), creatMovableIndex);
		} else if (movableRType == requestType.Search) {

			if (MovableIsEdit) {
				HCon = RequestInfo(HomeInfoFragment.this,
						Constants.requestType.Update,
						InterfaceInfo.HomeInfo_Movable_Update,
						newMovables.toString(), updateMovableIndex);
			} else {
				if (popup_movable != null) {
					popup_movable.dismiss();
				}
			}
		}

	}

	// 不动产数据处理
	private void setImmovable() {
		// TODO Auto-generated method stub
		ImMovablesInfo newImMovables = null;
		if (ImmovableRType == requestType.Insert) {
			newImMovables = new ImMovablesInfo();
		} else if (ImmovableRType == requestType.Search) {
			newImMovables = tempImMovables;
		}
		newImMovables.custid = PersonArchiving.custID;
		newImMovables.buy_date = immovable_buy_date.getText().toString();// 购买时间
		newImMovables.buy_price = Tool.getTextValue(immovable_buy_price);// 购置价格
		newImMovables.des = Tool.getTextValue(immovable_des);// 备注/其他资产
		newImMovables.estate_name = Tool.getTextValue(immovable_name);// 不动产名称
		newImMovables.estate_addr = Tool.getTextValue(immovable_address);// 不动产地址
		newImMovables.estate_area = Tool.getTextValue(immovable_area);// 不动产面积
		newImMovables.has_property_cer = String.valueOf(isAccreditation);// 是否办证

		if (!TextUtils.isEmpty(immovable_type.getText().toString())) {
			newImMovables.estate_type = immovable_ID;// 不动产类型
		}

		if (ImmovableRType == requestType.Insert) {
			HCon = RequestInfo(HomeInfoFragment.this,
					Constants.requestType.Insert,
					InterfaceInfo.HomeInfo_ImMovable_Creat,
					newImMovables.toString(), creatImMovableIndex);
		} else if (ImmovableRType == requestType.Search) {

			if (ImmovableIsEdit) {
				HCon = RequestInfo(HomeInfoFragment.this,
						Constants.requestType.Update,
						InterfaceInfo.HomeInfo_ImMovable_Update,
						newImMovables.toString(), updateImMovableIndex);
			} else {
				if (popup_immovable != null) {
					popup_immovable.dismiss();
				}
			}
		}

	}

	/**
	 * 创建客户关系人
	 */
	private void setContacts() {
		// TODO Auto-generated method stub
		NewRelationshipInfo newContacts = null;
		if (contactsRType == requestType.Insert) {
			newContacts = new NewRelationshipInfo();
		} else if (contactsRType == requestType.Search) {
			newContacts = tempContacts;
		}

		newContacts.custid = PersonArchiving.custID;
		newContacts.rel_name = Tool.getTextValue(rel_name);// 关系人姓名
		newContacts.rel_phone = Tool.getTextValue(rel_phone);// 联系电话
		newContacts.rel_cer_no = Tool.getTextValue(rel_cer_no);// 关系人证件号码
		newContacts.family_addr = Tool.getTextValue(contacts_address);// 联系地址

		if (!TextUtils.isEmpty(rel_type.getText().toString())) {
			newContacts.rel_type = rel_typeID;// 关系人类型
		}else{
			Toast.makeText(getActivity(), "请选择关系类型!", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if (!TextUtils.isEmpty(rel_cer_type.getText().toString())) {
			newContacts.rel_cer_type = rel_cer_typeID;// 关系人证件类型
		}else{
			Toast.makeText(getActivity(), "关系人证件类型!", Toast.LENGTH_SHORT).show();
			return;
		}

		//验证身份证
		if (rel_cer_typeID.equals("01")||rel_cer_typeID.equals("02")) {
			if(!CheckIdCardUtils.validateCard(newContacts.rel_cer_no)){
				Toast.makeText(getActivity(), "请输入正确的身份证号码！", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		
		// newContacts.work_unit = Tool.getTextValue(work_unit);// 工作单位
		// newContacts.occupation = Tool.getTextValue(occupation);// 职业
		// newContacts.entry_date = Tool.getTextValue(entry_date);// 入职时间
		// newContacts.main_earning = Tool.getTextValue(main_earning);// 主要经济来源
		// newContacts.birth_day = Tool.getTextValue(birth_day);// 出生日期
		// newContacts.other_earning = Tool.getTextValue(other_earning);//
		// 其他经济来源
		// newContacts.monthly_income = Tool.getTextValue(monthly_income);// 月收入
		newContacts.is_client = String.valueOf(isClient);// 是否为本行客户
		newContacts.is_holder = String.valueOf(isHolder);// 是否为本行股东
		// newContacts.position = Tool.getTextValue(position);// 职位

		// if (!TextUtils.isEmpty(sex.getText().toString())) {
		// newContacts.sex = sexID;// 性别
		// }
		// if (!TextUtils.isEmpty(education.getText().toString())) {
		// newContacts.education = educationID;// 教育程度
		// }

		// if (!TextUtils.isEmpty(work_profession.getText().toString())) {
		// newContacts.work_profession = work_professionID;// 单位类别
		// }
		if (contactsRType == requestType.Insert) {
			HCon = RequestInfo(HomeInfoFragment.this,
					Constants.requestType.Insert,
					InterfaceInfo.HomeInfo_Contacts_Creat,
					newContacts.toString(), creatContactsIndex);
		} else if (contactsRType == requestType.Search) {
			if (contactsIsEdit) {
				HCon = RequestInfo(HomeInfoFragment.this,
						Constants.requestType.Update,
						InterfaceInfo.HomeInfo_Contacts_Update,
						newContacts.toString(), creatContactsIndex);
			} else {
				if (popup_contacts != null) {
					popup_contacts.dismiss();
				}
			}
		}

	}

	/**
	 * 获取关系人信息
	 */
	private void getContactsInfo() {
		getContactsHCon = RequestInfo(HomeInfoFragment.this,
				Constants.requestType.Search,
				InterfaceInfo.HomeInfo_Contacts_Get, PersonArchiving.custID,
				getContactsIndex);
	}

	/**
	 * 获取动产信息
	 */
	private void getMovableInfo() {
		getMovableHCon = RequestInfo(HomeInfoFragment.this,
				Constants.requestType.Search,
				InterfaceInfo.HomeInfo_Movable_Get, PersonArchiving.custID,
				getMovableIndex);
	}

	/**
	 * 获取不动产信息
	 */
	private void getImMovableInfo() {
		getImMovableHCon = RequestInfo(HomeInfoFragment.this,
				Constants.requestType.Search,
				InterfaceInfo.HomeInfo_ImMovable_Get, PersonArchiving.custID,
				getImMovableIndex);
	}

	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		HResponse res;
		JSONObject tmpJsonObject;
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			break;
		case creatHomeInfoIndex:
		case creatContactsIndex:
		case creatMovableIndex:
		case creatImMovableIndex:
		case delContactsIndex:
		case delMovableIndex:
		case delImMovableIndex:
		case updateContactsIndex:
		case updateMovableIndex:
		case updateImMovableIndex:
			res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readJson(tmpJsonObject, connectionIndex);
			break;

		case getContactsIndex:
			res = getContactsHCon
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readJson(tmpJsonObject, connectionIndex);
			break;
		case getMovableIndex:
			res = getMovableHCon
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readJson(tmpJsonObject, connectionIndex);
			break;
		case getImMovableIndex:
			res = getImMovableHCon
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readJson(tmpJsonObject, connectionIndex);
			break;
		case getHomeInfoIndex:
			res = getHomeInfoHCon
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readJson(tmpJsonObject, connectionIndex);
			break;
		}

	}

	private void readJson(JSONObject tmpJsonObject, int index) {
		// TODO Auto-generated method stub
		String retCode = Tool.getJsonValue(tmpJsonObject, "retCode");// 返回标志

		if (retCode.equals("0000")) {
			try {
				if (index == creatContactsIndex || index == delContactsIndex
						|| index == updateContactsIndex) {
					getContactsInfo();
				} else if (index == creatMovableIndex
						|| index == delMovableIndex
						|| index == updateMovableIndex) {
					getMovableInfo();
				} else if (index == creatImMovableIndex
						|| index == delImMovableIndex
						|| index == updateImMovableIndex) {
					getImMovableInfo();
				} else if (index == creatHomeInfoIndex) {
					Message msg = ((ParentActivity) getActivity()).handler
							.obtainMessage();
					Bundle data = new Bundle();
					data.putString("value", "" + PersonArchiving.HomeInfoIndex);
					msg.setData(data);
					((ParentActivity) getActivity()).handler.sendMessage(msg);
				} else if (index == getContactsIndex) {
					ContactsList.clear();
					JSONArray theInfo;

					theInfo = tmpJsonObject.getJSONArray("group");
					if (theInfo.length() < 0) {
						return;
					} else {
						for (int i = 0; i < theInfo.length(); i++) {
							JSONObject temps = (JSONObject) theInfo.opt(i);
							NewRelationshipInfo tempRel = new NewRelationshipInfo();
							// 主键
							tempRel.pkid = Tool.getJsonValue(temps, "pkid");
							// 关系人姓名
							tempRel.rel_name = Tool.getJsonValue(temps,
									"rel_name");
							// 关系人类型
							tempRel.rel_type = Tool.getJsonValue(temps,
									"rel_type");
							// // 性别
							// tempRel.sex = Tool.getJsonValue(temps, "sex");
							// // 出生日期
							// tempRel.birth_day = Tool.getJsonValue(temps,
							// "birth_day");
							// 联系电话
							tempRel.rel_phone = Tool.getJsonValue(temps,
									"rel_phone");
							// 关系人证件类型
							tempRel.rel_cer_type = Tool.getJsonValue(temps,
									"rel_cer_type");

							// 关系人证件号码
							tempRel.rel_cer_no = Tool.getJsonValue(temps,
									"rel_cer_no");

							// // 教育程度
							// tempRel.education = Tool.getJsonValue(temps,
							// "education");
							//
							// // 工作单位
							// tempRel.work_unit = Tool.getJsonValue(temps,
							// "work_unit");
							//
							// // 单位类别
							// tempRel.work_profession =
							// Tool.getJsonValue(temps,
							// "work_profession");
							//
							// // 职业
							// tempRel.occupation = Tool.getJsonValue(temps,
							// "occupation");
							// // 职位
							// tempRel.position = Tool.getJsonValue(temps,
							// "position");
							// // 入职时间
							// tempRel.entry_date = Tool.getJsonValue(temps,
							// "entry_date");
							//
							// // 主要经济来源
							// tempRel.main_earning = Tool.getJsonValue(temps,
							// "main_earning");
							//
							// // 其他经济来源
							// tempRel.other_earning = Tool.getJsonValue(temps,
							// "other_earning");
							//
							// // 月收入
							// tempRel.monthly_income = Tool.getJsonValue(temps,
							// "monthly_income");

							// 是否为本行客户

							tempRel.is_client = Tool.getJsonValue(temps,
									"is_client");

							// 是否为本行股东
							tempRel.is_holder = Tool.getJsonValue(temps,
									"is_holder");

							tempRel.family_addr = Tool.getJsonValue(temps,
									"family_addr");
							tempRel.custid = PersonArchiving.custID;

							ContactsList.add(tempRel);
						}

					}

				} else if (index == getMovableIndex) {
					MovablesList.clear();
					JSONArray theInfo = tmpJsonObject.getJSONArray("group");
					if (theInfo.length() < 0) {
						return;
					} else {
						for (int i = 0; i < theInfo.length(); i++) {
							JSONObject temps = (JSONObject) theInfo.opt(i);
							MovablesInfo tempMovables = new MovablesInfo();
							// 主键
							tempMovables.pkid = Tool
									.getJsonValue(temps, "pkid");

							// 动产名称
							tempMovables.movable_name = Tool.getJsonValue(
									temps, "movable_name");
							// 购买时间

							tempMovables.buy_date = Tool.getJsonValue(temps,
									"buy_date");

							// 购置价格
							tempMovables.buy_price = Tool.getJsonValue(temps,
									"buy_price");

							// 备注/其他资产
							tempMovables.des = Tool.getJsonValue(temps, "des");

							MovablesList.add(tempMovables);
						}

					}

				} else if (index == getImMovableIndex) {
					ImMovablesList.clear();
					JSONArray theInfo = tmpJsonObject.getJSONArray("group");
					if (theInfo.length() < 0) {
						return;
					} else {
						for (int i = 0; i < theInfo.length(); i++) {
							JSONObject temps = (JSONObject) theInfo.opt(i);
							ImMovablesInfo tempMovables = new ImMovablesInfo();
							// 主键
							tempMovables.pkid = Tool
									.getJsonValue(temps, "pkid");

							// 不动产类型
							tempMovables.estate_type = Tool.getJsonValue(temps,
									"estate_type");

							// 不动产名称
							tempMovables.estate_name = Tool.getJsonValue(temps,
									"estate_name");

							// 不动产地址
							tempMovables.estate_addr = Tool.getJsonValue(
									temps, "estate_addr");

							// 购买时间
							tempMovables.buy_date = Tool.getJsonValue(temps,
									"buy_date");

							// 购置价格
							tempMovables.buy_price = Tool.getJsonValue(temps,
									"buy_price");

							// 备注/其他资产
							tempMovables.des = Tool.getJsonValue(temps, "des");

							// 是否办证
							tempMovables.has_property_cer = Tool.getJsonValue(
									temps, "has_property_cer");
							// 面积
							tempMovables.estate_area = Tool.getJsonValue(
									temps, "estate_area");
							
							ImMovablesList.add(tempMovables);
						}

					}

				} else if (index == getHomeInfoIndex) {
					JSONArray theInfo = tmpJsonObject.getJSONArray("group");
					if (theInfo.length() < 0) {
						return;
					} else {
						JSONObject temps = (JSONObject) theInfo.opt(0);

						homeInfo_homepeople.setText(Tool.getJsonValue(temps,
								"family_num"));// 家庭人数
						homeInfo_dependentPersons.setText(Tool.getJsonValue(
								temps, "support_num"));// 供养人数
						homeInfo_family_addr.setText(Tool.getJsonValue(
								temps, "family_addr"));// 家庭地址
						homeInfo_totalIncome_ly.setText(Tool.getJsonValue(
								temps, "harf_year_earning"));// 上年家庭总收入
						homeInfo_totalOutcome_ly.setText(Tool.getJsonValue(
								temps, "harf_year_consume"));// 上年家庭总支出
						homeInfo_totalMoney.setText(Tool.getJsonValue(temps,
								"family_asset"));// 家庭资产总额
						homeInfo_financialMoney.setText(Tool.getJsonValue(
								temps, "finance_asset"));// 金融资产
						homeInfo_accountsReceivable.setText(Tool.getJsonValue(
								temps, "account_recv"));// 应收账款
						homeInfo_otherMoney.setText(Tool.getJsonValue(temps,
								"rest_asset"));// 其他资产
						homeInfo_totalFamily.setText(Tool.getJsonValue(temps,
								"family_debt"));// 家庭负责总额
						homeInfo_myCredit.setText(Tool.getJsonValue(temps,
								"local_loan"));// 本行贷款
						homeInfo_otherCredit.setText(Tool.getJsonValue(temps,
								"other_loan"));// 他行贷款
						homeInfo_NPL.setText(Tool.getJsonValue(temps,
								"bad_loan"));// 不良贷款情况
						homeInfo_otherLiabilities.setText(Tool.getJsonValue(
								temps, "other_burden"));// 其他负债

					}
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			if(retCode.equals("V0001")){
				retCode = "关系人类型和证件类型不匹配!";
			}else if(retCode.equals("V0002")){
				retCode = "关系记录已经存在!";
			}
			Toast.makeText(getActivity(), "错误："+retCode, Toast.LENGTH_SHORT).show();
		}
		
		refreshInfo(index);
		if (popup_immovable != null) {
			popup_immovable.dismiss();
		}
		if (popup_movable != null) {
			popup_movable.dismiss();
		}
		if (popup_contacts != null) {
			popup_contacts.dismiss();
		}

	}

	/**
	 * 列表刷新
	 * 
	 * @param theIndex
	 */
	private void refreshInfo(int theIndex) {
		ArrayList<ItemModel> ViewInfo = new ArrayList<ItemModel>();

		switch (theIndex) {
		case getContactsIndex:
			if (ContactsList.size() > 0) {
				for (NewRelationshipInfo item : ContactsList) {
					ItemModel theInfo = new ItemModel();
					theInfo.theItemModel[0] = item.rel_name;
					theInfo.theItemModel[1] = NewCatevalue.getLabel(
							getActivity(), NewCatevalue.relType, item.rel_type);

					theInfo.theItemModel[2] = item.rel_phone;
					theInfo.theItemModel[3] = item.rel_cer_no;
					theInfo.pkid = item.pkid;
					ViewInfo.add(theInfo);
				}

				final ArchivingItemAdapter ViewAdapter = new ArchivingItemAdapter(
						getActivity(), relationship_list.getRightViewWidth(),
						ViewInfo, 4, new OnRightItemClickListener() {

							@Override
							public void onRightItemClick(View v,
									final String pkid) {
								// TODO Auto-generated method stub
								deleteDialog = new DeleteDialog(getActivity(),
										new IsDeleteListener() {

											@Override
											public void onClickDeleteListener(
													boolean isDelete) {
												if (isDelete) {
													HCon = RequestInfo(
															HomeInfoFragment.this,
															Constants.requestType.Delete,
															InterfaceInfo.HomeInfo_Contacts_Delete,
															pkid,
															delContactsIndex);

												}
											}
										});
								deleteDialog.show();
							}
						});
				relationship_list.setPullstatus(false, true);// 设置是否可以上下拉动
				relationship_list.setAdapter(ViewAdapter);
				relationship_list
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								ItemModel theInfo = ViewAdapter
										.getItem(position);

								for (NewRelationshipInfo item : ContactsList) {
									if (item.pkid.equals(theInfo.pkid)) {
										tempContacts = item;
										contactsRType = requestType.Search;
										showContactsView(contactsRType);
									}
								}
							}
						});

				ViewAdapter.setListViewHeight(relationship_list);
			} else {
				makeData(getContactsIndex);
			}
			break;
		case getImMovableIndex:
			if (ImMovablesList.size() > 0) {
				for (ImMovablesInfo item : ImMovablesList) {
					ItemModel theInfo = new ItemModel();
					theInfo.theItemModel[1] = item.buy_date;
					theInfo.theItemModel[2] = item.buy_price;
					theInfo.theItemModel[3] = item.des;
					theInfo.theItemModel[0] = NewCatevalue.getLabel(
							getActivity(), NewCatevalue.estateType,
							item.estate_type);

					theInfo.pkid = item.pkid;
					ViewInfo.add(theInfo);
				}
				final ArchivingItemAdapter ViewAdapter_1 = new ArchivingItemAdapter(
						getActivity(), immovables_list.getRightViewWidth(),
						ViewInfo, 4, new OnRightItemClickListener() {

							@Override
							public void onRightItemClick(View v,
									final String pkid) {
								// TODO Auto-generated method stub
								deleteDialog = new DeleteDialog(getActivity(),
										new IsDeleteListener() {

											@Override
											public void onClickDeleteListener(
													boolean isDelete) {
												if (isDelete) {

													HCon = RequestInfo(
															HomeInfoFragment.this,
															Constants.requestType.Delete,
															InterfaceInfo.HomeInfo_ImMovable_Delete,
															pkid,
															delImMovableIndex);
												}
											}
										});
								deleteDialog.show();
							}

						});
				immovables_list.setPullstatus(false, true);// 设置是否可以上下拉动
				immovables_list.setAdapter(ViewAdapter_1);
				immovables_list
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								ItemModel theInfo = ViewAdapter_1
										.getItem(position);

								for (ImMovablesInfo item : ImMovablesList) {
									if (item.pkid.equals(theInfo.pkid)) {
										tempImMovables = item;
										ImmovableRType = requestType.Search;
										showImMovableView(ImmovableRType);
									}
								}
							}
						});
				ViewAdapter_1.setListViewHeight(immovables_list);
			} else {
				makeData(getImMovableIndex);
			}
			break;
		case getMovableIndex:
			if (MovablesList.size() > 0) {
				for (MovablesInfo item : MovablesList) {
					ItemModel theInfo = new ItemModel();
					theInfo.theItemModel[1] = item.buy_date;
					theInfo.theItemModel[2] = item.buy_price;
					theInfo.theItemModel[3] = item.des;
					theInfo.theItemModel[0] = NewCatevalue.getLabel(
							getActivity(), NewCatevalue.movableType,
							item.movable_name);

					theInfo.pkid = item.pkid;
					ViewInfo.add(theInfo);
				}
				final ArchivingItemAdapter ViewAdapter_2 = new ArchivingItemAdapter(
						getActivity(), immovables_list.getRightViewWidth(),
						ViewInfo, 4, new OnRightItemClickListener() {

							@Override
							public void onRightItemClick(View v,
									final String pkid) {
								// TODO Auto-generated method stub
								deleteDialog = new DeleteDialog(getActivity(),
										new IsDeleteListener() {

											@Override
											public void onClickDeleteListener(
													boolean isDelete) {
												if (isDelete) {
													HCon = RequestInfo(
															HomeInfoFragment.this,
															Constants.requestType.Delete,
															InterfaceInfo.HomeInfo_Movable_Delete,
															pkid,
															delMovableIndex);
												}
											}
										});
								deleteDialog.show();
							}

						});
				movables_list.setPullstatus(false, true);// 设置是否可以上下拉动
				movables_list.setAdapter(ViewAdapter_2);
				movables_list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						ItemModel theInfo = ViewAdapter_2.getItem(position);

						for (MovablesInfo item : MovablesList) {
							if (item.pkid.equals(theInfo.pkid)) {
								tempMovables = item;
								movableRType = requestType.Search;
								showMovableView(ImmovableRType);
							}
						}
					}
				});
				ViewAdapter_2.setListViewHeight(movables_list);
			} else {
				makeData(getMovableIndex);
			}
			break;
		default:
			break;
		}
	}

	public void showContactsView(Constants.requestType theType) {

		if (popup_contacts == null) {
			View popupView = factory.inflate(
					R.layout.popwindow_homeinfo_newcontacts, null);
			popup_contacts = new Dialog(getActivity(), R.style.DialogTheme);
			popup_contacts.setContentView(popupView);
			initContactsView(popupView);
			setContactsListener();
		}
		if (!popup_contacts.isShowing()) {
			if (theType == requestType.Insert) {
				contactsIsEdit = true;
				clearContactsView();
			} else if (theType == requestType.Search) {
				contactsIsEdit = false;
				setContactsView(contactsIsEdit);
			}
			popup_contacts.show();
		}

	}

	private void setContactsView(boolean isEdit) {
		// TODO Auto-generated method stub
		rel_name.setEnabled(isEdit);
		rel_type.setClickable(isEdit);
		// sex.setClickable(isEdit);
		// birth_day.setClickable(isEdit);
		rel_phone.setEnabled(isEdit);
		rel_cer_no.setEnabled(isEdit);
		rel_cer_type.setClickable(isEdit);
		contacts_address.setEnabled(isEdit);

		// education.setClickable(isEdit);
		// work_unit.setEnabled(isEdit);
		// work_profession.setClickable(isEdit);
		// occupation.setEnabled(isEdit);
		// entry_date.setClickable(isEdit);
		// main_earning.setEnabled(isEdit);
		// other_earning.setEnabled(isEdit);
		// monthly_income.setEnabled(isEdit);
		is_client.setClickable(isEdit);
		is_holder.setClickable(isEdit);
		// position.setEnabled(isEdit);

		contacts_edit_ll.setVisibility(View.VISIBLE);
		if (isEdit) {
			contacts_title.setText(contactsTitle[2]);// 标题
			contacts_edit_text.setText(comTitle[1]);

		} else {
			contacts_title.setText(contactsTitle[1]);// 标题
			contacts_edit_text.setText(comTitle[0]);
		}

		if (tempContacts != null) {
			rel_name.setText(tempContacts.rel_name);
			rel_phone.setText(tempContacts.rel_phone);
			rel_cer_no.setText(tempContacts.rel_cer_no);
			contacts_address.setText(tempContacts.family_addr);

			rel_type.setText(NewCatevalue.getLabel(getActivity(),
					NewCatevalue.relType, tempContacts.rel_type));

			rel_cer_type.setText(NewCatevalue.getLabel(getActivity(),
					NewCatevalue.CERTTPALL, tempContacts.rel_cer_type));
		
			rel_typeID= tempContacts.rel_type;// 关系人类型
			
			rel_cer_typeID=tempContacts.rel_cer_type;// 证件类型
		
			
			if (tempContacts.is_client.equals(String.valueOf(isTrue))) {
				Tool.setSelect(is_client, isTrue);
				 isClient = isTrue;
				
			} else {
				Tool.setSelect(is_client, isFalse);
				 isClient = isFalse;
			}

			if (tempContacts.is_holder.equals(String.valueOf(isTrue))) {
				Tool.setSelect(is_holder, isTrue);
				 isHolder = isTrue;
			} else {
				Tool.setSelect(is_holder, isFalse);
				isHolder = isFalse;
			}

		}
	}

	public void showMovableView(Constants.requestType theType) {
		if (popup_movable == null) {
			View popupView = factory.inflate(
					R.layout.popwindow_homeinfo_movables, null);
			// 创建一个PopuWidow对象
			popup_movable = new Dialog(getActivity(), R.style.DialogTheme);
			// 设置焦点在弹窗上
			popup_movable.setContentView(popupView);
			// 初始化控件
			initMovableView(popupView);
			setMovableListener();
		}
		if (!popup_movable.isShowing()) {
			if (theType == requestType.Insert) {
				MovableIsEdit = true;
				clearMovableView();
			} else if (theType == requestType.Search) {
				MovableIsEdit = false;
				setMovableView(MovableIsEdit);
			}
			popup_movable.show();

		}
	}

	private void setMovableView(boolean isEdit) {

		Log.i("1111111", "setMovableView:" + String.valueOf(isEdit));

		movable_edit_ll.setVisibility(View.VISIBLE);
		movable_name.setClickable(isEdit);
		buy_date.setClickable(isEdit);
		buy_price.setEnabled(isEdit);
		des.setEnabled(isEdit);
		if (isEdit) {
			movable_title.setText(movableTitle[2]);// 标题
			movable_edit_text.setText(comTitle[1]);

		} else {
			movable_title.setText(movableTitle[1]);// 标题
			movable_edit_text.setText(comTitle[0]);
		}

		if (tempMovables != null) {
			movable_name.setText(NewCatevalue.getLabel(getActivity(),
					NewCatevalue.movableType, tempMovables.movable_name));
			movable_ID=tempMovables.movable_name;
			buy_date.setText(tempMovables.buy_date);
			buy_price.setText(tempMovables.buy_price);
			des.setText(tempMovables.des);
		}

	}

	public void showImMovableView(Constants.requestType theType) {
		if (popup_immovable == null) {
			View popupView = factory.inflate(
					R.layout.popwindow_homeinfo_immovables, null);
			// 创建一个PopuWidow对象
			popup_immovable = new Dialog(getActivity(), R.style.DialogTheme);
			// 设置焦点在弹窗上
			popup_immovable.setContentView(popupView);
			// 初始化控件
			initImMovableView(popupView);
			setImMovableListener();
		}
		if (!popup_immovable.isShowing()) {
			if (theType == requestType.Insert) {
				ImmovableIsEdit = true;
				clearImMovableView();
			} else if (theType == requestType.Search) {
				ImmovableIsEdit = false;
				setImmovableView(ImmovableIsEdit);
			}
			popup_immovable.show();
		}
	}

	private void setImmovableView(boolean isEdit) {

		immovable_edit_ll.setVisibility(View.VISIBLE);
		immovable_type.setClickable(isEdit);
		immovable_buy_date.setClickable(isEdit);
		immovable_buy_price.setEnabled(isEdit);
		immovable_des.setEnabled(isEdit);
		immovable_name.setEnabled(isEdit);
		immovable_address.setEnabled(isEdit);
		immovable_area.setEnabled(isEdit);
		immovable_isaccreditation.setClickable(isEdit);

		if (isEdit) {
			immovable_title.setText(ImmovableTitle[2]);// 标题
			immovable_edit_text.setText(comTitle[1]);

		} else {
			immovable_title.setText(ImmovableTitle[1]);// 标题
			immovable_edit_text.setText(comTitle[0]);
		}

		if (tempImMovables != null) {
			immovable_type.setText(NewCatevalue.getLabel(getActivity(),
					NewCatevalue.estateType, tempImMovables.estate_type));

			immovable_ID=tempImMovables.estate_type;
			
			immovable_buy_date.setText(tempImMovables.buy_date);
			immovable_buy_price.setText(tempImMovables.buy_price);
			immovable_des.setText(tempImMovables.des);
			immovable_name.setText(tempImMovables.estate_name);
			immovable_address.setText(tempImMovables.estate_addr);
			immovable_area.setText(tempImMovables.estate_area);
			
			if (tempImMovables.has_property_cer.equals(String.valueOf(isTrue))) {
				Tool.setSelect(immovable_isaccreditation, isTrue);
				isAccreditation=isTrue;
			} else {
				Tool.setSelect(immovable_isaccreditation, isFalse);
				isAccreditation=isFalse;
			}
		}

	}

	// 清除控件信息
	private void clearImMovableView() {
		immovable_type.setText("");
		immovable_buy_date.setText("");
		immovable_buy_price.setText("");
		immovable_des.setText("");
		immovable_name.setText("");
		immovable_address.setText("");
		immovable_area.setText("");
		immovable_title.setText(ImmovableTitle[0]);// 标题
		immovable_edit_ll.setVisibility(View.GONE);
		boolean isEdit = true;
		immovable_type.setClickable(isEdit);
		immovable_isaccreditation.setClickable(isEdit);
		immovable_buy_date.setClickable(isEdit);
		immovable_buy_price.setEnabled(isEdit);
		immovable_des.setEnabled(isEdit);
		immovable_name.setEnabled(isEdit);
		immovable_address.setEnabled(isEdit);
		immovable_area.setEnabled(isEdit);
	}

	private void setImMovableListener() {
		// TODO Auto-generated method stub
		immovable_btn_ok.setOnClickListener(this);
		immovable_btn_cancel.setOnClickListener(this);
		immovable_type.setOnClickListener(this);
		immovable_buy_date.setOnClickListener(this);
		immovable_edit_ll.setOnClickListener(this);
		immovable_isaccreditation.setOnClickListener(this);
	}

	private void initImMovableView(View popupView) {
		// TODO Auto-generated method stub
		immovable_type = (TextView) popupView
				.findViewById(R.id.creat_immovable_type);// 不动产类型
		immovable_buy_date = (TextView) popupView
				.findViewById(R.id.creat_immovable_time);// 购买时间
		immovable_buy_price = (EditText) popupView
				.findViewById(R.id.creat_immovable_money);// 购置价格
		immovable_des = (EditText) popupView
				.findViewById(R.id.creat_immovable_des);// 备注/其他资产
		immovable_btn_ok = (Button) popupView
				.findViewById(R.id.creat_immovable_btn_ok);// 确认按钮
		immovable_btn_cancel = (Button) popupView
				.findViewById(R.id.creat_immovable_btn_cancel);// 取消按钮

		immovable_title = (TextView) popupView
				.findViewById(R.id.immovable_title);// 标题
		immovable_edit_ll = (LinearLayout) popupView
				.findViewById(R.id.immovable_edit_ll);// 编辑按钮
		immovable_edit_text = (TextView) popupView
				.findViewById(R.id.immovable_edit_text);// 编辑文字

		immovable_name = (EditText) popupView
				.findViewById(R.id.creat_immovable_name);// 不动产名称
		immovable_address = (EditText) popupView
				.findViewById(R.id.creat_immovable_address);// 不动产名地址
		immovable_area = (EditText) popupView
				.findViewById(R.id.creat_immovable_area);// 不动产名面积

		immovable_isaccreditation = (ImageView) popupView
				.findViewById(R.id.creat_immovable_isaccreditation);// 是否办证
	}

	private void setMovableListener() {
		// TODO Auto-generated method stub
		creat_movable_btn_ok.setOnClickListener(this);
		creat_movable_btn_cancel.setOnClickListener(this);
		movable_name.setOnClickListener(this);
		buy_date.setOnClickListener(this);
		movable_edit_ll.setOnClickListener(this);
	}

	private void initMovableView(View popupView) {
		// TODO Auto-generated method stub
		movable_name = (TextView) popupView
				.findViewById(R.id.creat_movable_name);// 动产名称
		buy_date = (TextView) popupView.findViewById(R.id.creat_movable_time);// 购买时间
		buy_price = (EditText) popupView.findViewById(R.id.creat_movable_money);// 购置价格
		des = (EditText) popupView.findViewById(R.id.creat_movable_other);// 备注/其他资产
		creat_movable_btn_ok = (Button) popupView
				.findViewById(R.id.creat_movable_btn_ok);// 确认按钮
		creat_movable_btn_cancel = (Button) popupView
				.findViewById(R.id.creat_movable_btn_cancel);// 取消按钮
		movable_title = (TextView) popupView.findViewById(R.id.movable_title);// 标题
		movable_edit_ll = (LinearLayout) popupView
				.findViewById(R.id.movable_edit_ll);// 编辑按钮
		movable_edit_text = (TextView) popupView
				.findViewById(R.id.movable_edit_text);// 编辑文字

	}

	// 清除控件信息
	private void clearMovableView() {
		movable_name.setText("");
		buy_date.setText("");
		buy_price.setText("");
		des.setText("");

		boolean isEdit = true;
		movable_name.setClickable(isEdit);
		buy_date.setClickable(isEdit);
		buy_price.setEnabled(isEdit);
		des.setEnabled(isEdit);

		movable_title.setText(movableTitle[0]);// 标题
		movable_edit_ll.setVisibility(View.GONE);
	}

	private void setContactsListener() {
		// TODO Auto-generated method stub
		creat_contacts_btn_ok.setOnClickListener(this);
		creat_contacts_btn_cancel.setOnClickListener(this);
		is_client.setOnClickListener(this);
		is_holder.setOnClickListener(this);
		rel_type.setOnClickListener(this);
		rel_cer_type.setOnClickListener(this);
		// sex.setOnClickListener(this);
		// work_profession.setOnClickListener(this);
		// education.setOnClickListener(this);
		// birth_day.setOnClickListener(this);
		// entry_date.setOnClickListener(this);
		contacts_edit_ll.setOnClickListener(this);

	}

	// 清空数据
	private void clearContactsView() {

		boolean isEdit = true;
		rel_name.setEnabled(isEdit);
		rel_type.setClickable(isEdit);
		rel_phone.setEnabled(isEdit);
		rel_cer_no.setEnabled(isEdit);
		contacts_address.setEnabled(isEdit);
		rel_cer_type.setClickable(isEdit);
		is_client.setClickable(isEdit);
		is_holder.setClickable(isEdit);
		rel_phone.setText("");
		rel_cer_no.setText("");
		rel_name.setText("");
		rel_type.setText("");
		contacts_address.setText("");
		rel_cer_type.setText("");
		// sex.setClickable(isEdit);
		// birth_day.setClickable(isEdit);
		// education.setClickable(isEdit);
		// work_unit.setEnabled(isEdit);
		// work_profession.setClickable(isEdit);
		// occupation.setEnabled(isEdit);
		// entry_date.setClickable(isEdit);
		// main_earning.setEnabled(isEdit);
		// other_earning.setEnabled(isEdit);
		// monthly_income.setEnabled(isEdit);
		// position.setEnabled(isEdit);
		// position.setText("");
		// sex.setText("");
		// birth_day.setText("");
		// education.setText("");
		// work_unit.setText("");
		// work_profession.setText("");
		// occupation.setText("");
		// entry_date.setText("");
		// main_earning.setText("");
		// other_earning.setText("");
		// monthly_income.setText("");
		Tool.setSelect(is_client, isTrue);
		Tool.setSelect(is_holder, isTrue);

		contacts_title.setText(contactsTitle[0]);// 标题
		contacts_edit_ll.setVisibility(View.GONE);
	}

	// 创建客户关系人控件初始化
	private void initContactsView(View View) {
		rel_name = (EditText) View.findViewById(R.id.creat_contacts_rel_name);// 关系人姓名
		rel_type = (TextView) View.findViewById(R.id.creat_contacts_rel_type);// 关系人类型
		rel_cer_type = (TextView) View
				.findViewById(R.id.creat_contacts_rel_cer_type);// 关系人类型
		rel_phone = (EditText) View.findViewById(R.id.creat_contacts_rel_phone);// 联系电话
		rel_cer_no = (EditText) View
				.findViewById(R.id.creat_contacts_rel_cer_no);// 关系人证件号码
		contacts_address = (EditText) View
				.findViewById(R.id.creat_contacts_address);// 关系人证件号码
		is_client = (ImageView) View
				.findViewById(R.id.creat_contacts_is_client);// 是否为本行客户
		is_holder = (ImageView) View
				.findViewById(R.id.creat_contacts_is_holder);// 是否为本行股东

		// sex = (EditText) View.findViewById(R.id.creat_contacts_sex);// 性别
		// birth_day = (EditText)
		// View.findViewById(R.id.creat_contacts_birth_day);// 出生日期

		// education = (EditText)
		// View.findViewById(R.id.creat_contacts_education);// 教育程度
		// work_unit = (EditText)
		// View.findViewById(R.id.creat_contacts_work_unit);// 工作单位
		// work_profession = (EditText) View
		// .findViewById(R.id.creat_contacts_work_profession);// 单位类别
		// position = (EditText)
		// View.findViewById(R.id.creat_contacts_position);// 职位
		// occupation = (EditText) View
		// .findViewById(R.id.creat_contacts_occupation);// 职业
		// entry_date = (EditText) View
		// .findViewById(R.id.creat_contacts_entry_date);// 入职时间
		// main_earning = (EditText) View
		// .findViewById(R.id.creat_contacts_main_earning);// 主要经济来源
		// other_earning = (EditText) View
		// .findViewById(R.id.creat_contacts_other_earning);// 其他经济来源
		// monthly_income = (EditText) View
		// .findViewById(R.id.creat_contacts_monthly_income);// 月收入

		creat_contacts_btn_ok = (Button) View
				.findViewById(R.id.creat_contacts_btn_ok);// 确认按钮
		creat_contacts_btn_cancel = (Button) View
				.findViewById(R.id.creat_contacts_btn_cancel);// 取消按钮

		contacts_title = (TextView) View.findViewById(R.id.contacts_title);// 标题
		contacts_edit_ll = (LinearLayout) View
				.findViewById(R.id.contacts_edit_ll);// 编辑按钮
		contacts_edit_text = (TextView) View
				.findViewById(R.id.contacts_edit_text);// 编辑文字
	}

	private void setView(View view) {
		// TODO Auto-generated method stub
		homeInfo_homepeople = (EditText) view
				.findViewById(R.id.homeInfofragment_homepeople);
		homeInfo_dependentPersons = (EditText) view
				.findViewById(R.id.homeInfofragment_dependentPersons);
		homeInfo_family_addr = (EditText) view
				.findViewById(R.id.homeInfofragment_family_addr);
		homeInfo_totalIncome_ly = (EditText) view
				.findViewById(R.id.homeInfofragment_totalIncome_ly);
		homeInfo_totalOutcome_ly = (EditText) view
				.findViewById(R.id.homeInfofragment_totalOutcome_ly);
		homeInfo_totalMoney = (EditText) view
				.findViewById(R.id.homeInfofragment_totalMoney);
		homeInfo_financialMoney = (EditText) view
				.findViewById(R.id.homeInfofragment_financialMoney);
		homeInfo_totalFamily = (EditText) view
				.findViewById(R.id.homeInfofragment_totalFamily);
		homeInfo_accountsReceivable = (EditText) view
				.findViewById(R.id.homeInfofragment_accountsReceivable);
		homeInfo_otherMoney = (EditText) view
				.findViewById(R.id.homeInfofragment_otherMoney);
		homeInfo_otherCredit = (EditText) view
				.findViewById(R.id.homeInfofragment_otherCredit);
		homeInfo_myCredit = (EditText) view
				.findViewById(R.id.homeInfofragment_myCredit);
		homeInfo_NPL = (EditText) view.findViewById(R.id.homeInfofragment_NPL);
		homeInfo_otherLiabilities = (EditText) view
				.findViewById(R.id.homeInfofragment_otherLiabilities);
		homeInfo_creat_relationship = (ImageView) view
				.findViewById(R.id.homeInfofragment_creat_relationship);
		homeInfo_creat_movables = (ImageView) view
				.findViewById(R.id.homeInfofragment_creat_movables);
		homeInfo_creat_immovables = (ImageView) view
				.findViewById(R.id.homeInfofragment_creat_immovables);
		// relationship_list = (ListView) view
		// .findViewById(R.id.homeInfofragment_relationship_list);
		relationship_list = (SwipeListView) view
				.findViewById(R.id.homeInfofragment_relationship_list);
		movables_list = (SwipeListView) view
				.findViewById(R.id.homeInfofragment_movables_list);
		immovables_list = (SwipeListView) view
				.findViewById(R.id.homeInfofragment_immovables_list);

		homeInfo_nextbtn = (Button) view
				.findViewById(R.id.homeInfofragment_nextbtn);
		factory = LayoutInflater.from(getActivity());

	}


}
