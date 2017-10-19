package com.pactera.financialmanager.ui.fragmentbookbuilding;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
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

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.adapter.SpinnerAdapter;
import com.pactera.financialmanager.adapter.SpinnerAdapter.CallBackItemClickListener;
import com.pactera.financialmanager.callback.BookbuildingownbankmoneyCallback;
import com.pactera.financialmanager.ui.model.OwnBankMoneyEntity;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.NewCatevalue;

import java.io.Serializable;

/**
 * 
 * 创建本行存款信息的dialog 创建人和创建时间
 * 
 */
public class DialogFragmentCreateOwnBankMoney extends DialogFragment implements
		OnClickListener {
	private EditText spinStyle, spinDate;
	private EditText etMoney;
	private Button btnSave, btnNo;
	private BookbuildingownbankmoneyCallback callback;
	private String ownBankStyleId;// 存款类型
	private String ownBankDateId;// 存款期限
	private Boolean isEdit = false;
	private Boolean isCanEdit = true;
	private OwnBankMoneyEntity entity;
	private CheckBox cbox;
	private TextView tvTitle;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View view = View.inflate(getActivity(),
				R.layout.fragmentdialog_createownbankmoney, null);
		setupView(view);
		Bundle bundle = getArguments();
		if (bundle != null) {
			cbox.setVisibility(View.VISIBLE);
			isEdit = bundle.getBoolean("isEdit");
			entity = (OwnBankMoneyEntity) bundle.getSerializable("entity");
			setData();
		}
		setListener();
		Dialog dialog = new Dialog(getActivity(), R.style.dialog1);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		int w = Constants.SCREEN_WIDTH * 969 / 2048;
		int h = w * 926 / 1600;
		Log.i("width.....height......", "width:" + w + " ; " + "height:" + h);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, h);
		dialog.addContentView(view, params);

		return dialog;
	}

	private void setData() {
		canNotEdit();
		tvTitle.setText("查看本行存款");
		String moneyStyle = entity.getDEPOSITTYPE();
		String amount = entity.getDEPOSIT_AMOUNT();
		String period = entity.getDEPOSIT_PERIOD();
		String user = entity.getCREATE_USER();
		String date = entity.getCREATE_DATE();
		String moneyStyleVal = NewCatevalue.getLabel(getActivity(),
				NewCatevalue.depositType, moneyStyle);

		spinStyle.setText(moneyStyleVal);
		etMoney.setText(amount);

		String periodVal = NewCatevalue.getLabel(getActivity(),
				NewCatevalue.depositPeriod, period);
		spinDate.setText(periodVal);
		// info4.setText();//创建人
		// info5.setText();//创建时间
	}

	private void canEdit() {
		isCanEdit = true;
		spinStyle.setEnabled(true);
		spinDate.setEnabled(true);
		etMoney.setEnabled(true);
	}

	private void canNotEdit() {
		isCanEdit = false;
		spinStyle.setEnabled(false);
		spinDate.setEnabled(false);
		etMoney.setEnabled(false);
	}

	private void setListener() {
		spinDate.setOnClickListener(this);// 存款期限
		spinStyle.setOnClickListener(this);// 存款类型
		btnSave.setOnClickListener(this);
		btnNo.setOnClickListener(this);
		cbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					cbox.setText("取消");
					canEdit();
					tvTitle.setText("编辑本行存款");
				} else {
					cbox.setText("编辑");
					canNotEdit();
				}
			}
		});
	}

	private void setupView(View view) {
		tvTitle = (TextView) view.findViewById(R.id.tv_title);
		spinStyle = (EditText) view.findViewById(R.id.spin_moneystyle);
		spinDate = (EditText) view.findViewById(R.id.spin_bankmoneydate);
		etMoney = (EditText) view.findViewById(R.id.et_bankmoney1);

		cbox = (CheckBox) view.findViewById(R.id.cbox_edit);
		cbox.setVisibility(View.GONE);

		btnSave = (Button) view.findViewById(R.id.btnYes);
		btnNo = (Button) view.findViewById(R.id.btnNo);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnYes:
			String styleId = spinStyle.getText().toString().trim();
			String dateId = spinDate.getText().toString().trim();
			String money = etMoney.getText().toString().trim();
			if (isCanEdit) {
				/**
				 * 时间紧迫，现在不做判断
				 */
				setFunction(ownBankStyleId, money, ownBankDateId, "", "");
			}
			dismiss();
			break;
		case R.id.btnNo:
			dismiss();
			break;
		case R.id.spin_moneystyle:// 存款类型
			SpinnerAdapter.showSelectDialog(getActivity(),
					NewCatevalue.depositType, spinStyle,
					new CallBackItemClickListener() {
						@Override
						public void CallBackItemClick(String spinnerID) {
							ownBankStyleId = spinnerID;
						}
					});
			break;
		case R.id.spin_bankmoneydate:// 存款期限
			SpinnerAdapter.showSelectDialog(getActivity(),
					NewCatevalue.depositPeriod, spinDate,
					new CallBackItemClickListener() {
						@Override
						public void CallBackItemClick(String spinnerID) {
							ownBankDateId = spinnerID;
						}
					});
			break;
		default:
			break;
		}
	}

	public static DialogFragmentCreateOwnBankMoney getNewInstance() {
		DialogFragmentCreateOwnBankMoney fragment = new DialogFragmentCreateOwnBankMoney();
		return fragment;
	}

	public static DialogFragmentCreateOwnBankMoney getNewInstance(
			Boolean isEdit, Serializable entity) {
		DialogFragmentCreateOwnBankMoney fragment = new DialogFragmentCreateOwnBankMoney();
		Bundle bundle = new Bundle();
		bundle.putBoolean("isEdit", isEdit);
		bundle.putSerializable("entity", entity);
		fragment.setArguments(bundle);
		return fragment;
	}

	public void setCallBack(BookbuildingownbankmoneyCallback callback) {
		this.callback = callback;
	}

	public void setFunction(String info1, String info2, String info3,
			String info4, String info5) {
		callback.bookbuildingOwnBankMoney(isEdit, info1, info2, info3, info4,
				info5);
	}

}
