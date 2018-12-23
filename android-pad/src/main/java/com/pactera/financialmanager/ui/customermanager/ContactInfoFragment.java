package com.pactera.financialmanager.ui.customermanager;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.TextView;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.adapter.ArchivingItemAdapter;
import com.pactera.financialmanager.adapter.SpinnerAdapter;
import com.pactera.financialmanager.adapter.ArchivingItemAdapter.OnRightItemClickListener;
import com.pactera.financialmanager.adapter.SpinnerAdapter.CallBackItemClickListener;
import com.pactera.financialmanager.ui.DeleteDialog;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.DeleteDialog.IsDeleteListener;
import com.pactera.financialmanager.ui.SwipeListView;
import com.pactera.financialmanager.ui.model.AddressContact;
import com.pactera.financialmanager.ui.model.ItemModel;
import com.pactera.financialmanager.ui.model.OtherContact;
import com.pactera.financialmanager.ui.model.PhoneContact;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;
import com.pactera.financialmanager.util.Constants.requestType;

/**
 * 个人建档下联系信息fragment
 */

public class ContactInfoFragment extends ParentFragment implements
		OnClickListener {

	private LayoutInflater factory;
	private Dialog popup;

	Button contactinfo_nextbtn;// 下一步
	ImageView creat_ohter;// 其他联系方式
	ImageView creat_address;// 住址信息
	ImageView creat_phone;// 电话信息
	SwipeListView other_list;
	SwipeListView address_list;
	SwipeListView phonelist;
	/**
	 * 创建信息
	 */
	TextView popwindow_contact_title;// 标题
	TextView popwindow_contact_tv_1;// 类型题目
	TextView popwindow_contact_type;// 类型
	ImageView popwindow_contact_isfirst;// 是否首选
	EditText popwindow_contact_info;// 详细信息
	Button popwindow_contact_btn_ok;// 确认
	Button popwindow_contact_btn_cancel;// 取消
	int pop_index = 0;// (0电话信息，1住址信息，2其他联系方式)
	private final int PhoneIndex = 0;
	private final int AddressIndex = 1;
	private final int OtherIndex = 2;
	private final int getPhoneInfoIndex = 3;
	private final int getAddressInfoIndex = 4;
	private final int getOtherInfoIndex = 5;
	private final int delPhoneInfoIndex = 6;
	private final int delAddressInfoIndex = 7;
	private final int delOtherInfoIndex = 8;

	String[] popType = { "电话类型：", "地址类型：", "联系类型：" };
	int isFirst = isFalse;

	private String phoneID, addressID, otherID;
	// private String theName, theTime, TheType = "安卓客户端";

	ArrayList<PhoneContact> phoneList = new ArrayList<PhoneContact>();
	ArrayList<AddressContact> addressContactList = new ArrayList<AddressContact>();
	ArrayList<OtherContact> otherContactList = new ArrayList<OtherContact>();
	private HConnection HCon;
	private HConnection getPhoneHCon;
	private HConnection getAddressHCon;
	private HConnection getOtherHCon;

	DeleteDialog deleteDialog;

	LinearLayout contact_edit_ll;
	TextView contact_edit_text;
	String[] popTitle_creat = { "创建联系人电话", "创建联系地址", "创建其他联系方式" };
	String[] popTitle_look = { "查看联系人电话", "查看联系地址", "查看其他联系方式" };// 标题名称
	String[] popTitle_updata = { "编辑联系人电话", "编辑联系地址", "编辑其他联系方式" };// 标题名称

	Constants.requestType contactsRType = requestType.Search;// 默认当前状态为查询
	boolean contactsIsEdit = false;
	String[] comTitle = { "编辑", "取消" };
	static PhoneContact tempPhoneInfo;
	static AddressContact tempAddressInfo;
	static OtherContact tempOtherInfo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_archiving_contactinfo,
				null);
		setView(view);
		setListener();
		getOtherInfo();
		getPhoneInfo();
		getAddressInfo();
		return view;
	}

	private void setListener() {
		// TODO Auto-generated method stub
		creat_ohter.setOnClickListener(this);
		creat_address.setOnClickListener(this);
		creat_phone.setOnClickListener(this);
		contactinfo_nextbtn.setOnClickListener(this);
	}

	// 造个空格，图片效果
	private void makeData(int index) {
		ItemModel theInfo = new ItemModel();
		ArrayList<ItemModel> ViewInfo = new ArrayList<ItemModel>();
		ViewInfo.add(theInfo);
		ArchivingItemAdapter ViewAdapter = new ArchivingItemAdapter(
				getActivity(), 0, ViewInfo, 6, null);

		switch (index) {
		case getPhoneInfoIndex:
			phonelist.setAdapter(ViewAdapter);
			ViewAdapter.setListViewHeight(phonelist);
			break;
		case getAddressInfoIndex:
			address_list.setAdapter(ViewAdapter);
			ViewAdapter.setListViewHeight(address_list);
			break;
		case getOtherInfoIndex:
			other_list.setAdapter(ViewAdapter);
			ViewAdapter.setListViewHeight(address_list);
			break;
		default:
			break;
		}
	}

	private void setView(View view) {
		// TODO Auto-generated method stub

		creat_ohter = (ImageView) view
				.findViewById(R.id.contactinfofragment_creat_ohter);// 其他联系方式
		creat_address = (ImageView) view
				.findViewById(R.id.contactinfofragment_creat_address);// 住址信息
		creat_phone = (ImageView) view
				.findViewById(R.id.contactinfofragment_creat_phone);// 电话信息
		other_list = (SwipeListView) view
				.findViewById(R.id.contactinfofragment_other_list);
		address_list = (SwipeListView) view
				.findViewById(R.id.contactinfofragment_address_list);
		phonelist = (SwipeListView) view
				.findViewById(R.id.contactinfofragment_phone_list);
		contactinfo_nextbtn = (Button) view
				.findViewById(R.id.contactinfofragment_nextbtn);
		factory = LayoutInflater.from(getActivity());

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.contactinfofragment_nextbtn:
			Message msg = ((ParentActivity) getActivity()).handler
					.obtainMessage();
			Bundle data = new Bundle();
			data.putString("value", "" + PersonArchiving.ContactIndex);
			msg.setData(data);
			((ParentActivity) getActivity()).handler.sendMessage(msg);
			break;
		case R.id.popwindow_contact_btn_cancel:
			if (popup != null) {
				popup.dismiss();
			}
			break;
		case R.id.popwindow_contact_btn_ok:
			creatInfo();
			break;
		case R.id.contactinfofragment_creat_phone:
			pop_index = PhoneIndex;
			contactsRType = requestType.Insert;
			showPopView(contactsRType);
			break;
		case R.id.contactinfofragment_creat_address:
			pop_index = AddressIndex;
			contactsRType = requestType.Insert;
			showPopView(contactsRType);
			break;
		case R.id.contactinfofragment_creat_ohter:
			pop_index = OtherIndex;
			contactsRType = requestType.Insert;
			showPopView(contactsRType);
			break;
		case R.id.popwindow_contact_isfirst:
			isFirst = isFirst == isTrue ? isFalse : isTrue;
			setSelect(popwindow_contact_isfirst, isFirst);
			break;
		case R.id.popwindow_contact_type:
			if (contactsIsEdit) {

				if (pop_index == PhoneIndex) {
					SpinnerAdapter.showSelectDialog(getActivity(),
							NewCatevalue.phoneType, popwindow_contact_type,
							new CallBackItemClickListener() {
								@Override
								public void CallBackItemClick(String spinnerID) {
									// TODO Auto-generated method stub
									phoneID = spinnerID;
								}
							});
				} else if (pop_index == AddressIndex) {
					SpinnerAdapter.showSelectDialog(getActivity(),
							NewCatevalue.addrType, popwindow_contact_type,
							new CallBackItemClickListener() {

								@Override
								public void CallBackItemClick(String spinnerID) {
									// TODO Auto-generated method stub
									addressID = spinnerID;
								}
							});
				} else if (pop_index == OtherIndex) {
					SpinnerAdapter.showSelectDialog(getActivity(),
							NewCatevalue.conType, popwindow_contact_type,
							new CallBackItemClickListener() {

								@Override
								public void CallBackItemClick(String spinnerID) {
									// TODO Auto-generated method stub
									otherID = spinnerID;
								}
							});
				}
			}
			break;
		case R.id.contact_edit_ll:
			contactsIsEdit = !contactsIsEdit;
			setContactsView(contactsIsEdit);
			break;
		default:
			break;
		}
	}

	private void creatInfo() {
		PhoneContact newPhone = new PhoneContact();
		AddressContact newAddress = new AddressContact();
		OtherContact newOther = new OtherContact();

		if (pop_index == PhoneIndex) {
			if (contactsRType == requestType.Search) {
				newPhone = tempPhoneInfo;
			}
			newPhone.phone_type = phoneID;
			newPhone.is_frist_selected = String.valueOf(isFirst);
			newPhone.contact_info = Tool.getTextValue(popwindow_contact_info);
			newPhone.custid = PersonArchiving.custID;

			if (contactsRType == requestType.Insert) {
				HCon = RequestInfo(ContactInfoFragment.this,
						Constants.requestType.Insert,
						InterfaceInfo.Contact_Phone_Creat, newPhone.toString(),
						PhoneIndex);
			} else if (contactsRType == requestType.Search) {
				if (contactsIsEdit) {
					HCon = RequestInfo(ContactInfoFragment.this,
							Constants.requestType.Update,
							InterfaceInfo.Contact_Phone_Update,
							newPhone.toString(), PhoneIndex);
				}
			}

		} else if (pop_index == AddressIndex) {
			if (contactsRType == requestType.Search) {
				newAddress = tempAddressInfo;
			}
			newAddress.addr_type = addressID;
			newAddress.is_frist_selected = String.valueOf(isFirst);
			newAddress.contact_info = Tool.getTextValue(popwindow_contact_info);
			newAddress.custid = PersonArchiving.custID;

			if (contactsRType == requestType.Insert) {
				HCon = RequestInfo(ContactInfoFragment.this,
						Constants.requestType.Insert,
						InterfaceInfo.Contact_Address_Creat,
						newAddress.toString(), AddressIndex);
			} else if (contactsRType == requestType.Search) {
				if (contactsIsEdit) {
					HCon = RequestInfo(ContactInfoFragment.this,
							Constants.requestType.Update,
							InterfaceInfo.Contact_Address_Update,
							newAddress.toString(), AddressIndex);
				}
			}

		} else if (pop_index == OtherIndex) {
			if (contactsRType == requestType.Search) {
				newOther = tempOtherInfo;
			}
			newOther.con_type = otherID;
			newOther.is_frist_selected = String.valueOf(isFirst);
			newOther.contact_info = Tool.getTextValue(popwindow_contact_info);
			newOther.custid = PersonArchiving.custID;

			if (contactsRType == requestType.Insert) {
				HCon = RequestInfo(ContactInfoFragment.this,
						Constants.requestType.Insert,
						InterfaceInfo.Contact_Other_Creat, newOther.toString(),
						OtherIndex);
			} else if (contactsRType == requestType.Search) {
				if (contactsIsEdit) {
					HCon = RequestInfo(ContactInfoFragment.this,
							Constants.requestType.Update,
							InterfaceInfo.Contact_Other_Update,
							newOther.toString(), OtherIndex);
				}
			}
		}

		if (popup != null) {
			popup.dismiss();
		}

	}

	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		HResponse res;
		JSONObject tmpJsonObject;
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			break;
		case PhoneIndex:
		case AddressIndex:
		case OtherIndex:
		case delPhoneInfoIndex:
		case delAddressInfoIndex:
		case delOtherInfoIndex:
			res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readJson(tmpJsonObject, connectionIndex);
			break;
		case getPhoneInfoIndex:
			res = getPhoneHCon
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readJson(tmpJsonObject, connectionIndex);
			break;
		case getAddressInfoIndex:
			res = getAddressHCon
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readJson(tmpJsonObject, connectionIndex);
			break;
		case getOtherInfoIndex:
			res = getOtherHCon
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
		String retCode = "";

		if (tmpJsonObject.has("retCode")) { // 返回标志
			try {
				retCode = tmpJsonObject.getString("retCode");
				Log.i("1111111", "index:" + connectionIndex + ",retCode:"
						+ retCode);
			} catch (JSONException e1) {
				e1.printStackTrace();
				return;
			}
		}
		if (retCode.equals("0000")) {
			try {
				if (connectionIndex == PhoneIndex
						|| connectionIndex == delPhoneInfoIndex) {
					getPhoneInfo();

				} else if (connectionIndex == AddressIndex
						|| connectionIndex == delAddressInfoIndex) {
					getAddressInfo();

				} else if (connectionIndex == OtherIndex
						|| connectionIndex == delOtherInfoIndex) {
					getOtherInfo();

				} else if (connectionIndex == getPhoneInfoIndex) {
					phoneList.clear();

					JSONArray theInfo = tmpJsonObject.getJSONArray("group");
					if (theInfo.length() < 0) {
						return;
					} else {
						for (int i = 0; i < theInfo.length(); i++) {
							JSONObject temps = (JSONObject) theInfo.opt(i);
							PhoneContact tempsPhone = new PhoneContact();
							tempsPhone.custid = PersonArchiving.custID;// 客户id
							tempsPhone.phone_type = Tool.getJsonValue(temps,
									"phone_type");// 电话类型
							tempsPhone.is_frist_selected = Tool.getJsonValue(
									temps, "is_frist_selected");// 是否首选
							tempsPhone.contact_info = Tool.getJsonValue(temps,
									"contact_info");// 联系信息
							tempsPhone.create_date = Tool.getJsonValue(temps,
									"create_date");// 创建时间

							String data_source = Tool.getJsonValue(temps,
									"data_source");
							tempsPhone.data_source = NewCatevalue.getLabel(
									getActivity(), NewCatevalue.dataSourceType,
									data_source);// 数据来源

							tempsPhone.creatorname = Tool.getJsonValue(temps,
									"creatorname");// 创建人名字
							tempsPhone.creator = Tool.getJsonValue(temps,
									"creator");// 创建人ID
							tempsPhone.pkid = Tool.getJsonValue(temps, "pkid");// ID
							phoneList.add(tempsPhone);
						}

					}

				} else if (connectionIndex == getAddressInfoIndex) {
					addressContactList.clear();

					JSONArray theInfo = tmpJsonObject.getJSONArray("group");
					if (theInfo.length() < 0) {
						return;
					} else {
						for (int i = 0; i < theInfo.length(); i++) {
							JSONObject temps = (JSONObject) theInfo.opt(i);
							AddressContact tempAddress = new AddressContact();
							tempAddress.custid = PersonArchiving.custID;// 客户id
							tempAddress.addr_type = Tool.getJsonValue(temps,
									"addr_type");// 地址类型
							tempAddress.is_frist_selected = Tool.getJsonValue(
									temps, "is_frist_selected");// 是否首选
							tempAddress.contact_info = Tool.getJsonValue(temps,
									"contact_info");// 联系信息
							tempAddress.create_date = Tool.getJsonValue(temps,
									"create_date");// 创建时间

							String data_source = Tool.getJsonValue(temps,
									"data_source");
							tempAddress.data_source = NewCatevalue.getLabel(
									getActivity(), NewCatevalue.dataSourceType,
									data_source);// 数据来源
							tempAddress.creatorname = Tool.getJsonValue(temps,
									"creatorname");// 创建人名字
							tempAddress.pkid = Tool.getJsonValue(temps, "pkid");// ID
							tempAddress.creator = Tool.getJsonValue(temps,
									"creator");// 创建人ID
							addressContactList.add(tempAddress);
						}

					}

				} else if (connectionIndex == getOtherInfoIndex) {
					otherContactList.clear();

					JSONArray theInfo = tmpJsonObject.getJSONArray("group");
					if (theInfo.length() < 0) {
						return;
					} else {
						for (int i = 0; i < theInfo.length(); i++) {
							JSONObject temps = (JSONObject) theInfo.opt(i);
							OtherContact tempsOther = new OtherContact();
							tempsOther.custid = PersonArchiving.custID;// 客户id
							tempsOther.con_type = Tool.getJsonValue(temps,
									"con_type");// 电话类型
							tempsOther.is_frist_selected = Tool.getJsonValue(
									temps, "is_frist_selected");// 是否首选
							tempsOther.contact_info = Tool.getJsonValue(temps,
									"contact_info");// 联系信息
							tempsOther.create_date = Tool.getJsonValue(temps,
									"create_date");// 创建时间
							String data_source = Tool.getJsonValue(temps,
									"data_source");
							tempsOther.data_source = NewCatevalue.getLabel(
									getActivity(), NewCatevalue.dataSourceType,
									data_source);// 数据来源

							tempsOther.creatorname = Tool.getJsonValue(temps,
									"creatorname");// 创建人名字
							tempsOther.pkid = Tool.getJsonValue(temps, "pkid");// ID
							tempsOther.creator = Tool.getJsonValue(temps,
									"creator");// 创建人ID
							otherContactList.add(tempsOther);
						}

					}

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		refreshInfo(connectionIndex);
		if (popup != null) {
			popup.dismiss();
		}

	}

	private void refreshInfo(int connectionIndex) {
		// TODO Auto-generated method stub
		ArrayList<ItemModel> ViewInfo = new ArrayList<ItemModel>();
		switch (connectionIndex) {
		case getPhoneInfoIndex:
			if (phoneList.size() > 0) {
				for (PhoneContact item : phoneList) {
					ItemModel theInfo = new ItemModel();
					theInfo.theItemModel[0] = NewCatevalue.getLabel(
							getActivity(), NewCatevalue.phoneType,
							item.phone_type);

					theInfo.theItemModel[1] = item.contact_info;
					theInfo.theItemModel[2] = item.creatorname;
					theInfo.theItemModel[3] = item.data_source;
					theInfo.theItemModel[4] = item.create_date;
					theInfo.pkid = item.pkid;
					if (item.is_frist_selected.equals(String.valueOf(isTrue))) {
						theInfo.theItemModel[5] = "是";
					} else {
						theInfo.theItemModel[5] = "否";
					}
					ViewInfo.add(theInfo);
					final ArchivingItemAdapter ViewAdapter_1 = new ArchivingItemAdapter(
							getActivity(), phonelist.getRightViewWidth(),
							ViewInfo, 6, new OnRightItemClickListener() {

								@Override
								public void onRightItemClick(View v,
										final String pkid) {
									// TODO Auto-generated method stub
									deleteDialog = new DeleteDialog(
											getActivity(),
											new IsDeleteListener() {

												@Override
												public void onClickDeleteListener(
														boolean isDelete) {
													if (isDelete) {

														HCon = RequestInfo(
																ContactInfoFragment.this,
																Constants.requestType.Delete,
																InterfaceInfo.Contact_Phone_Delete,
																pkid,
																delPhoneInfoIndex);

													}
												}
											});
									deleteDialog.show();
								}

							});
					phonelist.setPullstatus(false, true);// 设置是否可以上下拉动
					phonelist.setAdapter(ViewAdapter_1);
					phonelist.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							ItemModel theInfo = ViewAdapter_1.getItem(position);

							for (PhoneContact item : phoneList) {
								if (item.pkid.equals(theInfo.pkid)) {
									tempPhoneInfo = item;
									contactsRType = requestType.Search;
									pop_index = PhoneIndex;
									showPopView(contactsRType);
								}

							}
						}
					});
					ViewAdapter_1.setListViewHeight(phonelist);
				}
			} else {
				makeData(getPhoneInfoIndex);
			}

			break;
		case getAddressInfoIndex:
			if (addressContactList.size() > 0) {
				for (AddressContact item : addressContactList) {
					ItemModel theInfo = new ItemModel();
					theInfo.theItemModel[0] = NewCatevalue.getLabel(
							getActivity(), NewCatevalue.addrType,
							item.addr_type);

					theInfo.theItemModel[1] = item.contact_info;
					theInfo.theItemModel[2] = item.creatorname;
					theInfo.theItemModel[3] = item.data_source;
					theInfo.theItemModel[4] = item.create_date;
					theInfo.pkid = item.pkid;
					if (item.is_frist_selected.equals(String.valueOf(isTrue))) {
						theInfo.theItemModel[5] = "是";
					} else {
						theInfo.theItemModel[5] = "否";
					}
					ViewInfo.add(theInfo);
					final ArchivingItemAdapter ViewAdapter_2 = new ArchivingItemAdapter(
							getActivity(), address_list.getRightViewWidth(),
							ViewInfo, 6, new OnRightItemClickListener() {

								@Override
								public void onRightItemClick(View v,
										final String pkid) {
									// TODO Auto-generated method stub
									deleteDialog = new DeleteDialog(
											getActivity(),
											new IsDeleteListener() {

												@Override
												public void onClickDeleteListener(
														boolean isDelete) {
													if (isDelete) {

														HCon = RequestInfo(
																ContactInfoFragment.this,
																Constants.requestType.Delete,
																InterfaceInfo.Contact_Address_Delete,
																pkid,
																delAddressInfoIndex);
													}
												}
											});
									deleteDialog.show();
								}

							});
					address_list.setPullstatus(false, true);// 设置是否可以上下拉动
					address_list.setAdapter(ViewAdapter_2);
					address_list
							.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {
									// TODO Auto-generated method stub
									ItemModel theInfo = ViewAdapter_2
											.getItem(position);

									for (AddressContact item : addressContactList) {
										if (item.pkid.equals(theInfo.pkid)) {
											tempAddressInfo = item;
											contactsRType = requestType.Search;
											pop_index = AddressIndex;
											showPopView(contactsRType);
										}
									}
								}
							});
					ViewAdapter_2.setListViewHeight(address_list);
				}
			} else {
				makeData(getAddressInfoIndex);
			}
			break;
		case getOtherInfoIndex:
			if (otherContactList.size() > 0) {
				for (OtherContact item : otherContactList) {
					ItemModel theInfo = new ItemModel();
					theInfo.theItemModel[0] = NewCatevalue.getLabel(
							getActivity(), NewCatevalue.conType, item.con_type);

					theInfo.theItemModel[1] = item.contact_info;
					theInfo.theItemModel[2] = item.creatorname;
					theInfo.theItemModel[3] = item.data_source;
					theInfo.theItemModel[4] = item.create_date;
					theInfo.pkid = item.pkid;
					if (item.is_frist_selected.equals(String.valueOf(isTrue))) {
						theInfo.theItemModel[5] = "是";
					} else {
						theInfo.theItemModel[5] = "否";
					}
					ViewInfo.add(theInfo);
					final ArchivingItemAdapter ViewAdapter_3 = new ArchivingItemAdapter(
							getActivity(), other_list.getRightViewWidth(),
							ViewInfo, 6, new OnRightItemClickListener() {

								@Override
								public void onRightItemClick(View v,
										final String pkid) {
									// TODO Auto-generated method stub
									deleteDialog = new DeleteDialog(
											getActivity(),
											new IsDeleteListener() {

												@Override
												public void onClickDeleteListener(
														boolean isDelete) {
													if (isDelete) {

														HCon = RequestInfo(
																ContactInfoFragment.this,
																Constants.requestType.Delete,
																InterfaceInfo.Contact_Other_Delete,
																pkid,
																delOtherInfoIndex);
													}
												}
											});
									deleteDialog.show();
								}

							});
					other_list.setPullstatus(false, true);// 设置是否可以上下拉动
					other_list.setAdapter(ViewAdapter_3);
					other_list
							.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {
									// TODO Auto-generated method stub
									ItemModel theInfo = ViewAdapter_3
											.getItem(position);

									for (OtherContact item : otherContactList) {
										if (item.pkid.equals(theInfo.pkid)) {
											tempOtherInfo = item;
											contactsRType = requestType.Search;
											pop_index = OtherIndex;
											showPopView(contactsRType);
										}
									}
								}
							});

					ViewAdapter_3.setListViewHeight(other_list);
				}
			} else {
				makeData(getOtherInfoIndex);
			}
			break;
		default:
			break;
		}
	}

	private void getOtherInfo() {
		getOtherHCon = RequestInfo(ContactInfoFragment.this,
				Constants.requestType.Search, InterfaceInfo.Contact_Other_Get,
				PersonArchiving.custID, getOtherInfoIndex);
	}

	private void getAddressInfo() {
		getAddressHCon = RequestInfo(ContactInfoFragment.this,
				Constants.requestType.Search,
				InterfaceInfo.Contact_Address_Get, PersonArchiving.custID,
				getAddressInfoIndex);
	}

	private void getPhoneInfo() {
		getPhoneHCon = RequestInfo(ContactInfoFragment.this,
				Constants.requestType.Search, InterfaceInfo.Contact_Phone_Get,
				PersonArchiving.custID, getPhoneInfoIndex);
	}

	// 选择切换
	private void setSelect(ImageView theImg, int theflag) {
		if (theflag == isTrue) {
			theImg.setImageResource(R.drawable.cusarchiving_swatch_on);
		} else {
			theImg.setImageResource(R.drawable.cusarchiving_swatch_off);
		}
	}

	public void showPopView(Constants.requestType theType) {
		if (popup == null) {
			View popupView = factory.inflate(R.layout.popwindow_contactinfo,
					null);
			popup = new Dialog(getActivity(), R.style.DialogTheme);

			popup.setContentView(popupView);
			// 初始化控件
			initPopView(popupView);
			setPopListener();
		}
		if (!popup.isShowing()) {
			if (theType == requestType.Insert) {
				contactsIsEdit = true;
				clearPopView();
			} else if (theType == requestType.Search) {
				contactsIsEdit = false;
				setContactsView(contactsIsEdit);
			}
			popup.show();
		}
	}

	// 设置查看初始化
	private void setContactsView(boolean isEdit) {
		// TODO Auto-generated method stub
		contact_edit_ll.setVisibility(View.VISIBLE);
		popwindow_contact_tv_1.setText(popType[pop_index]);
		popwindow_contact_isfirst.setClickable(isEdit);
		popwindow_contact_info.setEnabled(isEdit);
		popwindow_contact_type.setClickable(isEdit);
		if (isEdit) {
			popwindow_contact_title.setText(popTitle_updata[pop_index]);
			contact_edit_text.setText(comTitle[1]);
		} else {
			popwindow_contact_title.setText(popTitle_look[pop_index]);
			contact_edit_text.setText(comTitle[0]);
		}
		// (0电话信息，1住址信息，2其他联系方式)
		switch (pop_index) {
		case 0:
			if (tempPhoneInfo != null) {
				popwindow_contact_type.setText(NewCatevalue.getLabel(
						getActivity(), NewCatevalue.phoneType,
						tempPhoneInfo.phone_type));// 类型
				phoneID = tempPhoneInfo.phone_type;
				if (tempPhoneInfo.is_frist_selected.equals(String
						.valueOf(isTrue))) {
					isFirst = isTrue;
					setSelect(popwindow_contact_isfirst, isTrue);
				} else {
					isFirst = isFalse;
					setSelect(popwindow_contact_isfirst, isFalse);
				}
				popwindow_contact_info.setText(tempPhoneInfo.contact_info);// 详细信息
			}
			break;
		case 1:
			if (tempAddressInfo != null) {
				popwindow_contact_type.setText(NewCatevalue.getLabel(
						getActivity(), NewCatevalue.addrType,
						tempAddressInfo.addr_type));// 类型
				addressID = tempAddressInfo.addr_type;
				if (tempAddressInfo.is_frist_selected.equals(String
						.valueOf(isTrue))) {
					isFirst = isTrue;
					setSelect(popwindow_contact_isfirst, isTrue);
				} else {
					isFirst = isFalse;
					setSelect(popwindow_contact_isfirst, isFalse);
				}
				popwindow_contact_info.setText(tempAddressInfo.contact_info);// 详细信息
			}
			break;
		case 2:
			if (tempOtherInfo != null) {
				popwindow_contact_type.setText(NewCatevalue.getLabel(
						getActivity(), NewCatevalue.conType,
						tempOtherInfo.con_type));// 类型
				otherID = tempOtherInfo.con_type;
				if (tempOtherInfo.is_frist_selected.equals(String
						.valueOf(isTrue))) {
					isFirst = isTrue;
					setSelect(popwindow_contact_isfirst, isTrue);
				} else {
					isFirst = isFalse;
					setSelect(popwindow_contact_isfirst, isFalse);
				}
				popwindow_contact_info.setText(tempOtherInfo.contact_info);// 详细信息
			}
			break;
		default:
			break;
		}

	}

	private void setPopListener() {
		// TODO Auto-generated method stub
		popwindow_contact_btn_cancel.setOnClickListener(this);
		popwindow_contact_btn_ok.setOnClickListener(this);
		popwindow_contact_isfirst.setOnClickListener(this);
		popwindow_contact_type.setOnClickListener(this);
		contact_edit_ll.setOnClickListener(this);
	}

	private void clearPopView() {
		popwindow_contact_title.setText(popTitle_creat[pop_index]);
		popwindow_contact_tv_1.setText(popType[pop_index]);
		popwindow_contact_type.setText("");
		setSelect(popwindow_contact_isfirst, isFalse);
		popwindow_contact_info.setText("");
		popwindow_contact_type.setClickable(true);
		setSelect(popwindow_contact_isfirst, isFalse);
		popwindow_contact_isfirst.setClickable(true);
		popwindow_contact_info.setEnabled(true);
		contact_edit_ll.setVisibility(View.GONE);
		isFirst = isFalse;
	}

	private void initPopView(View popupView) {
		// TODO Auto-generated method stub
		popwindow_contact_title = (TextView) popupView
				.findViewById(R.id.popwindow_contact_title);// 标题
		popwindow_contact_tv_1 = (TextView) popupView
				.findViewById(R.id.popwindow_contact_tv_1);// 类型题目
		popwindow_contact_type = (TextView) popupView
				.findViewById(R.id.popwindow_contact_type);// 类型
		popwindow_contact_isfirst = (ImageView) popupView
				.findViewById(R.id.popwindow_contact_isfirst);// 是否首选
		popwindow_contact_info = (EditText) popupView
				.findViewById(R.id.popwindow_contact_info);// 详细信息
		popwindow_contact_btn_ok = (Button) popupView
				.findViewById(R.id.popwindow_contact_btn_ok);// 确认
		popwindow_contact_btn_cancel = (Button) popupView
				.findViewById(R.id.popwindow_contact_btn_cancel);// 取消
		// theName = LogoActivity.user.getUsername();
		contact_edit_ll = (LinearLayout) popupView
				.findViewById(R.id.contact_edit_ll);
		contact_edit_text = (TextView) popupView
				.findViewById(R.id.contact_edit_text);
	}

}
