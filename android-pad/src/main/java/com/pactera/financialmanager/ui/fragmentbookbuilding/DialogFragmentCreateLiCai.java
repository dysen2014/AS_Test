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
import com.pactera.financialmanager.callback.BookbuildingotherbanklicaiCallback;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog.OnDateSetListener;
import com.pactera.financialmanager.ui.model.OtherBankLiCaiEntity;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.Tool;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 
 * 创建他行理财 信息的dialog
 * 
 */

public class DialogFragmentCreateLiCai extends DialogFragment
		implements OnClickListener {
	private EditText eLiCaiInfo1,eLiCaiInfo2,eLiCaiInfo6;
	private TextView eLiCaiInfo3,eLiCaiInfo4,eLiCaiInfo5;
	private Button btnSave, btnNo;
	private BookbuildingotherbanklicaiCallback callback;
	
	private Boolean isEdit=false;
	private Boolean isCanEdit=true;
	private CheckBox cbox;
	private OtherBankLiCaiEntity entity;
	private TextView tvTitle;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View view = View.inflate(getActivity(),
				R.layout.fragmentdialog_createotherbanklicai, null);
		setupView(view);
		Bundle bundle = getArguments();
		if(bundle!=null){
			cbox.setVisibility(View.VISIBLE);
			isEdit=bundle.getBoolean("isEdit");
			entity=(OtherBankLiCaiEntity) bundle.getSerializable("entity");
			setData();
		}
		setListener();
		Dialog dialog = new Dialog(getActivity(), R.style.dialog1);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		int w = Constants.SCREEN_WIDTH * 969 / 2048;
		int h = w * 926 / 949;
		Log.i("width.....height......", "width:" + w + " ; " + "height:" + h);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, h);
		dialog.addContentView(view, params);

		return dialog;
	}

	private void setData() {
		canNotEdit();
		tvTitle.setText("查看行外理财信息");
		eLiCaiInfo1.setText(entity.getPRD_NAME());
		eLiCaiInfo2.setText(entity.getEXP_AMOUNT());
		eLiCaiInfo3.setText(entity.getEXP_DATE());
		eLiCaiInfo4.setText(entity.getEND_DATE());
		eLiCaiInfo5.setText(entity.getREMIND_DATE());
		eLiCaiInfo6.setText(entity.getEXP_PROFIT());
		// info7.setText(item.getCREATE_DATE());//还缺 创建人 和 创建时间
		// info8.setText(item.getCREATE_DATE());
	}
	
	private void canNotEdit() {
		isCanEdit=false;
		eLiCaiInfo1.setEnabled(false);
		eLiCaiInfo2.setEnabled(false);
		eLiCaiInfo3.setEnabled(false);
		eLiCaiInfo4.setEnabled(false);
		eLiCaiInfo5.setEnabled(false);
		eLiCaiInfo6.setEnabled(false);
	}

	private void canEdit() {
		isCanEdit=true;
		eLiCaiInfo1.setEnabled(true);
		eLiCaiInfo2.setEnabled(true);
		eLiCaiInfo3.setEnabled(true);
		eLiCaiInfo4.setEnabled(true);
		eLiCaiInfo5.setEnabled(true);
		eLiCaiInfo6.setEnabled(true);
	}

	private void setListener() {
		eLiCaiInfo5.setOnClickListener(this);
		eLiCaiInfo4.setOnClickListener(this);
		eLiCaiInfo3.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		btnNo.setOnClickListener(this);
		cbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					cbox.setText("取消");
					canEdit();
					tvTitle.setText("编辑行外理财信息");
				} else {
					cbox.setText("编辑");
					canNotEdit();
					tvTitle.setText("查看行外理财信息");
				}
			}
		});
	}

	private void setupView(View view) {
		tvTitle=(TextView)view.findViewById(R.id.tv_title);
		eLiCaiInfo1 = (EditText) view.findViewById(R.id.et_otherbanklicai1);
		eLiCaiInfo2 = (EditText) view.findViewById(R.id.et_otherbanklicai2);
		eLiCaiInfo3 = (TextView) view.findViewById(R.id.et_otherbanklicai3);
		eLiCaiInfo4 = (TextView) view.findViewById(R.id.et_otherbanklicai4);
		eLiCaiInfo5 = (TextView) view.findViewById(R.id.et_otherbanklicai5);
		eLiCaiInfo6 = (EditText) view.findViewById(R.id.et_otherbanklicai6);

		btnSave = (Button) view.findViewById(R.id.btnYes);
		btnNo = (Button) view.findViewById(R.id.btnNo);
		
		cbox=(CheckBox)view.findViewById(R.id.cbox_edit);
		cbox.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnYes:
			String info1 = eLiCaiInfo1.getText().toString().trim();
			String info2 = eLiCaiInfo2.getText().toString().trim();
			String info3 = eLiCaiInfo3.getText().toString().trim();
			String info4 = eLiCaiInfo4.getText().toString().trim();
			String info5 = eLiCaiInfo5.getText().toString().trim();
			String info6 = eLiCaiInfo6.getText().toString().trim();


			if (Tool.checkInputStr(info1)) {
				Toast.makeText(getActivity(), "请输入产品名称", 0).show();
				return;
			}
			if (Tool.checkInputStr(info2)) {
				Toast.makeText(getActivity(), "请输入认购金额", 0).show();
				return;
			}
			if (Tool.checkInputStr(info3) || info3.equals("请选择日期")) {
				Toast.makeText(getActivity(), "请选择认购日期", 0).show();
				return;
			}
			if (Tool.checkInputStr(info4) || info4.equals("请选择日期")) {
				Toast.makeText(getActivity(), "请选择到期日期", 0).show();
				return;
			}
			if (Tool.checkInputStr(info5) || info5.equals("请选择日期")) {
				Toast.makeText(getActivity(), "请选择提醒日期", 0).show();
				return;
			}
			if (Tool.checkInputStr(info6)) {
				Toast.makeText(getActivity(), "请输入预期收益率", 0).show();
				return;
			}
			if (!Tool.checkValidRatio(info6)) {
				Toast.makeText(getActivity(), "请输入有效预期收益率", 0).show();
				return;
			}

			if(isCanEdit){
				setFunction(info1, info2, info3, info4, info5, info6, "", "");
			}
			dismiss();
			break;
		case R.id.btnNo:
			dismiss();
			break;
		case R.id.et_otherbanklicai3:
			DatePickerShow(new OnDateSetListener() {
				@Override
				public void onDateSet(String year, String month, String day) {
					// TODO Auto-generated method stub
					String dayate = year + "-" + month + "-" + day;
					eLiCaiInfo3.setText(dayate);
				}
			});
			break;
		case R.id.et_otherbanklicai4:
			DatePickerShow(new OnDateSetListener() {
				@Override
				public void onDateSet(String year, String month, String day) {
					// TODO Auto-generated method stub
					String dayate = year + "-" + month + "-" + day;
					eLiCaiInfo4.setText(dayate);
				}
			});
			break;
		case R.id.et_otherbanklicai5:
			DatePickerShow(new OnDateSetListener() {
				@Override
				public void onDateSet(String year, String month, String day) {
					// TODO Auto-generated method stub
					String dayate = year + "-" + month + "-" + day;
					eLiCaiInfo5.setText(dayate);
				}
			});
			break;
		default:
			break;
		}
	}
	
	/**
	 * 日期选择器
	 * @param onDateSetListener
	 */
	public void DatePickerShow(OnDateSetListener onDateSetListener){
		Calendar calendar = Calendar.getInstance();
		DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		datePickerDialog.show(((FragmentActivity) getActivity()).getSupportFragmentManager(), "datepicker");
	}

	public static DialogFragmentCreateLiCai getNewInstance() {
		DialogFragmentCreateLiCai fragment = new DialogFragmentCreateLiCai();
		return fragment;
	}
	
	public static DialogFragmentCreateLiCai getNewInstance(Boolean isEdit,Serializable entity) {
		DialogFragmentCreateLiCai fragment = new DialogFragmentCreateLiCai();
		Bundle bundle=new Bundle();
		bundle.putBoolean("isEdit", isEdit);
		bundle.putSerializable("entity", entity);
		fragment.setArguments(bundle);
		return fragment;
	}

	public void setCallBack(BookbuildingotherbanklicaiCallback callback) {
		this.callback = callback;
	}

	public void setFunction(String info1, String info2, String info3,
			String info4, String info5, String info6, String info7,String info8) {
		callback.bookbuildingOtherBankLiCai(isEdit,info1, info2, info3, info4, info5, info6, info7, info8);
	}

}
