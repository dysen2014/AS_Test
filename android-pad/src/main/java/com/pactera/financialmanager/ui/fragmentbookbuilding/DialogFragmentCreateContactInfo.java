package com.pactera.financialmanager.ui.fragmentbookbuilding;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.adapter.SpinnerAdapter;
import com.pactera.financialmanager.adapter.SpinnerAdapter.CallBackItemClickListener;
import com.pactera.financialmanager.callback.BookbuildingContactCallback;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog;
import com.pactera.financialmanager.ui.model.ContantsInfoEntity;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

import java.io.Serializable;
import java.util.Calendar;

public class DialogFragmentCreateContactInfo extends DialogFragment implements
		OnClickListener {
	private EditText etx_memberName, etx_memberIdNum, etx_memberposition,
			etx_memberPhoneNum;
	private TextView txt_membermastertime, txt_memberemploytime;
	private Button btnContactYes, btnContactNo;
	private EditText spinnerRelationType, spinnerCertificatesType;
	private BookbuildingContactCallback callback;
	private String tagRelTypeID, tagCertTypeID, spStudyId;
	private ContantsInfoEntity entity;
	private boolean isEdit = false, isCanEdit = true;// 是否是编辑，false,不是；true,是。
	private CheckBox cboxEdit;
	private TextView tvTitle;


	public static DialogFragmentCreateContactInfo getNewInstance() {
		DialogFragmentCreateContactInfo fragment = new DialogFragmentCreateContactInfo();
		return fragment;
	}

	public static DialogFragmentCreateContactInfo getNewInstance(
			Boolean isEdit, Serializable entity) {
		DialogFragmentCreateContactInfo fragment = new DialogFragmentCreateContactInfo();
		Bundle bundle = new Bundle();
		bundle.putBoolean("isEdit", isEdit);
		bundle.putSerializable("entity", entity);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View view = View.inflate(getActivity(),
				R.layout.fragmentdialog_createcontactinfo, null);
		Bundle bundle = getArguments();
		setupView(view);
		// setupListener();
		if (bundle != null) {
			cboxEdit.setVisibility(View.VISIBLE);
			isEdit = bundle.getBoolean("isEdit");
			entity = (ContantsInfoEntity) bundle.getSerializable("entity");
			setData();
		}
		setupListener();
		Dialog dialog = new Dialog(getActivity(), R.style.dialog1);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		int w = Constants.SCREEN_WIDTH * 969 / 2048;
		int h = w * 926 / 949;
		Log.i("width.....height......", "width:" + w + " ; " + "height:" + h);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, h);
		dialog.addContentView(view, params);

		return dialog;
	}

	// 反显数据
	private void setData() {
		canNotEdit();
		tvTitle.setText("查看关键成员信息");
		etx_memberName.setText(entity.getKEY_MEM_NAME());
		String type = entity.getKEY_CERT_TYPE();// 证件类型
		String value = NewCatevalue.getLabel(getActivity(), NewCatevalue.CERTTP, type);
		tagCertTypeID = type;
		spinnerCertificatesType.setText(value);
		etx_memberIdNum.setText(entity.getKEY_CERT_NO());
		String type1 = entity.getREL_TYPE();// 关系类型
		String value1 = NewCatevalue.getLabel(getActivity(),
				NewCatevalue.key_mem_type, type1);

		tagRelTypeID = type1;
		spinnerRelationType.setText(value1);

		txt_membermastertime.setText(entity.getMASTERTIME());// 任职时间
		txt_memberemploytime.setText(entity.getEMPLOYTIME());// 从业时间
		etx_memberposition.setText(entity.getKEY_CONDITION());// 担任职务
		etx_memberPhoneNum.setText(entity.getCONTACT_PHONE());// 联系电话

		// String top_DEGREE = entity.getEMPLOYTIME();//从业时间
		// String topDegree = Catevalue.getName(top_DEGREE, CompayValue.infoStr,
		// CompayValue.infoValue);
		// txt_memberemploytime.setText(topDegree);// 最高学历 目前为空，后台没有返回数据
	}

	private void setupListener() {
		txt_membermastertime.setOnClickListener(this);
		txt_memberemploytime.setOnClickListener(this);
		spinnerCertificatesType.setOnClickListener(this);
		spinnerRelationType.setOnClickListener(this);
		btnContactYes.setOnClickListener(this);
		btnContactNo.setOnClickListener(this);
		cboxEdit.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					cboxEdit.setText("取消");
					canEdit();
					tvTitle.setText("编辑关键成员信息");
				} else {
					cboxEdit.setText("编辑");
					canNotEdit();
					tvTitle.setText("查看关键成员信息");
				}
			}
		});
	}

	private void canEdit() {
		isCanEdit = true;
		etx_memberName.setEnabled(true);
		spinnerCertificatesType.setEnabled(true);
		etx_memberIdNum.setEnabled(true);
		spinnerRelationType.setEnabled(true);
		txt_membermastertime.setEnabled(true);
		etx_memberposition.setEnabled(true);
		etx_memberPhoneNum.setEnabled(true);
		txt_memberemploytime.setEnabled(true);
	}

	private void canNotEdit() {
		isCanEdit = false;
		etx_memberName.setEnabled(false);
		spinnerCertificatesType.setEnabled(false);
		etx_memberIdNum.setEnabled(false);
		spinnerRelationType.setEnabled(false);
		txt_membermastertime.setEnabled(false);
		etx_memberposition.setEnabled(false);
		etx_memberPhoneNum.setEnabled(false);
		txt_memberemploytime.setEnabled(false);
	}

	private void setupView(View view) {
		tvTitle = (TextView) view.findViewById(R.id.tv_title);
		etx_memberName = (EditText) view
				.findViewById(R.id.et_dialog_member_name);
		etx_memberIdNum = (EditText) view.findViewById(R.id.et_dialog_id_num);// 证件号码
		txt_membermastertime = (TextView) view
				.findViewById(R.id.et_dialog_begin_job_time);// 任职时间
		etx_memberposition = (EditText) view.findViewById(R.id.et_dialog_job);// 职务
		etx_memberPhoneNum = (EditText) view.findViewById(R.id.et_dialog_tel);// 电话
		txt_memberemploytime = (TextView) view
				.findViewById(R.id.et_dialog_working_time);// 从业时间
		spinnerRelationType = (EditText) view
				.findViewById(R.id.spinner_relation_type);// 关系类型
		spinnerCertificatesType = (EditText) view
				.findViewById(R.id.spinner_certificates_type);// 证件类型

		cboxEdit = (CheckBox) view.findViewById(R.id.cbox_edit);
		cboxEdit.setVisibility(View.GONE);

		btnContactYes = (Button) view.findViewById(R.id.dialogcontact_btnYes);
		btnContactNo = (Button) view.findViewById(R.id.dialogcontact_btnNo);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialogcontact_btnYes:
			String strName = etx_memberName.getText().toString().trim();
			String strIdNum = etx_memberIdNum.getText().toString().trim();
			String strMasterTime = txt_membermastertime.getText().toString().trim();
			String strposition = etx_memberposition.getText().toString().trim();
			String strPhoneNum = etx_memberPhoneNum.getText().toString().trim();
			String strEmployTime = txt_memberemploytime.getText().toString().trim();
			if (isCanEdit) {
				if (Tool.checkInputStr(strName)) {
					Toast.makeText(getActivity(), "请输入关键成员姓名",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (Tool.checkInputStr(tagRelTypeID)) {
					Toast.makeText(getActivity(), "请选择关系类型", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (Tool.checkInputStr(tagCertTypeID)) {
					Toast.makeText(getActivity(), "请选择证件类型", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (Tool.checkInputStr(strIdNum)) {
					Toast.makeText(getActivity(), "请输入证件号码", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (Tool.checkInputStr(strPhoneNum)) {
					Toast.makeText(getActivity(), "请输入联系电话", Toast.LENGTH_SHORT)
							.show();
					return;
				}

				if (Tool.checkInputStr(strposition)) {
					Toast.makeText(getActivity(), "请输入关键人情况", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (Tool.checkInputStr(strMasterTime) || strMasterTime.equals("请选择任职时间")) {
					Toast.makeText(getActivity(), "请选择任职时间", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (Tool.checkInputStr(strEmployTime)|| strEmployTime.equals("请选择从业时间")) {
					Toast.makeText(getActivity(), "请选择从业时间", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				setFunction(isEdit, strName, tagRelTypeID, tagCertTypeID, strIdNum,
						strPhoneNum, strposition, strMasterTime, strEmployTime);
			}
			dismiss();
			break;
		case R.id.dialogcontact_btnNo:
			dismiss();
			break;
		case R.id.spinner_relation_type:// 关系类型
			SpinnerAdapter.showSelectDialog(getActivity(),
					NewCatevalue.key_mem_type, spinnerRelationType,
					new CallBackItemClickListener() {
						@Override
						public void CallBackItemClick(String spinnerID) {
							tagRelTypeID = spinnerID;
						}
					});
			break;
		case R.id.spinner_certificates_type:// 证件类型
			SpinnerAdapter.showSelectDialog(getActivity(), NewCatevalue.CERTTP,
					spinnerCertificatesType, new CallBackItemClickListener() {
						@Override
						public void CallBackItemClick(String spinnerID) {
							tagCertTypeID = spinnerID;
						}
					});
			break;
		case R.id.et_dialog_begin_job_time:// 任职日期
			DatePickerShow(new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(String year, String month, String day) {
					String dayate = year + "-" + month + "-" + day;
					txt_membermastertime.setText(dayate);
				}
			});
			break;
		case R.id.et_dialog_working_time:// 从业日期
			DatePickerShow(new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(String year, String month, String day) {
					String dayate = year + "-" + month + "-" + day;
					txt_memberemploytime.setText(dayate);
				}
			});
			break;
		default:
			break;
		}
	}

	public void setCallBack(BookbuildingContactCallback callback) {
		this.callback = callback;
	}

	/**
	 * 这是实现创建联络人信息的回调实现
	 * 
	 * @param info52
	 * @param info42
	 * @param info32
	 * @param spStyle
	 * @param spState
	 * @param info22
	 * @param info12
	 * @param info6
	 */
	public void setFunction(Boolean isEdit, String info1, String info2,
			String info3, String info4, String info5, String info6,
			String info7, String info8) {
		callback.bookbuildingContact(isEdit, info1, info2, info3, info4, info5,
				info6, info7, info8);
	}

	/**
	 * 日期选择器
	 * 
	 * @param onDateSetListener
	 */
	public void DatePickerShow(
			DatePickerDialog.OnDateSetListener onDateSetListener) {
		Calendar calendar = Calendar.getInstance();
		DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
				onDateSetListener, calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		datePickerDialog.show(
				((FragmentActivity) getActivity()).getSupportFragmentManager(),
				"datepicker");

	}

}
