package com.pactera.financialmanager.ui.fragmentbookbuilding;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import com.pactera.financialmanager.callback.BookbuildingotherbankmoneyCallback;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog.OnDateSetListener;
import com.pactera.financialmanager.datetimepicker.TimePickerDialog;
import com.pactera.financialmanager.datetimepicker.TimePickerDialog.OnTimeSetListener;
import com.pactera.financialmanager.ui.model.OtherBankMoneyEntity;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 
 * 创建他行存款信息的dialog
 * 
 */

public class DialogFragmentCreateOtherBankMoney extends DialogFragment
		implements OnClickListener {
	private EditText spinStyle, spinTime;
	private EditText spinBankName;
	private EditText etMoneyAmount;
	private TextView spinDate;
	private TextView tvOutDate;
	private Button btnSave, btnNo;
	private BookbuildingotherbankmoneyCallback callback;
	private String styleId;// 存款类型
	private String styleDuring;// 存款期限
	private String styleBank;// 存款行

	private Boolean isEdit = false;
	private Boolean isCanEdit = true;
	private CheckBox cbox;
	private OtherBankMoneyEntity entity;
	private TextView tvTitle;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View view = View.inflate(getActivity(),
				R.layout.fragmentdialog_createotherbankmoney, null);
		setupView(view);
		Bundle bundle = getArguments();
		if (bundle != null) {
			cbox.setVisibility(View.VISIBLE);
			isEdit = bundle.getBoolean("isEdit");
			entity = (OtherBankMoneyEntity) bundle.getSerializable("entity");
			setData();
		}
		setListener();
		Dialog dialog = new Dialog(getActivity(), R.style.dialog1);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		int w = Constants.SCREEN_WIDTH * 969 / 2048;
		int h = w * 926 / 949;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, h);
		dialog.addContentView(view, params);

		return dialog;
	}

	private void setListener() {
		tvOutDate.setOnClickListener(this);
		spinDate.setOnClickListener(this);
		spinBankName.setOnClickListener(this);
		spinTime.setOnClickListener(this);
		spinStyle.setOnClickListener(this);

		btnSave.setOnClickListener(this);
		btnNo.setOnClickListener(this);
		cbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					cbox.setText("取消");
					canEdit();
					tvTitle.setText("编辑他行存款");
				} else {
					cbox.setText("编辑");
					canNotEdit();
					tvTitle.setText("查看他行存款");

				}
			}
		});
	}

	private void setData() {
		canNotEdit();
		tvTitle.setText("查看他行存款");
		String deposit_type = entity.getDeposit_type();// 类型
		String deposit_period = entity.getDeposit_period();// 期限
		String deposit_amount = entity.getDeposit_amount();// 金额
		String deposit_bank = entity.getDeposit_bank();
		String remind_date = entity.getRemind_date();
		styleId = deposit_type;
		styleDuring = deposit_period;
        styleBank = deposit_bank;

		String moneyStyle = NewCatevalue.getLabel(getActivity(),
				NewCatevalue.depositType, deposit_type);

		String moneyPeriod = NewCatevalue.getLabel(getActivity(),
				NewCatevalue.depositPeriod, deposit_period);
		// 存款行
		String bankName ="";

        if(null != deposit_bank)
        bankName =  NewCatevalue.getLabel(getActivity(),
				NewCatevalue.signBank, deposit_bank);

		spinStyle.setText(moneyStyle);
		spinTime.setText(moneyPeriod);
		etMoneyAmount.setText(deposit_amount);
		spinBankName.setText(bankName);
		spinDate.setText(remind_date);
        tvOutDate.setText(entity.getDeposit_maturity_date());

	}

	private void canNotEdit() {
		isCanEdit = false;
		spinStyle.setEnabled(false);
		spinTime.setEnabled(false);
		etMoneyAmount.setEnabled(false);
		spinBankName.setEnabled(false);
		spinDate.setEnabled(false);
		tvOutDate.setEnabled(false);
	}

	private void canEdit() {
		isCanEdit = true;
		spinStyle.setEnabled(true);
		spinTime.setEnabled(true);
		etMoneyAmount.setEnabled(true);
		spinBankName.setEnabled(true);
		spinDate.setEnabled(true);
        tvOutDate.setEnabled(true);
    }

	private void setupView(View view) {
		tvTitle = (TextView) view.findViewById(R.id.tv_title);
		spinStyle = (EditText) view.findViewById(R.id.spin_othermoneystyle);// 存款类型
		spinTime = (EditText) view.findViewById(R.id.spin_othermoneydate);// 存款期限
		spinBankName = (EditText) view.findViewById(R.id.spin_otherbankname);// 存款行
		tvOutDate = (TextView) view.findViewById(R.id.tv_outdate);// 存款到期日
		spinDate = (TextView) view.findViewById(R.id.tv_warndate);// 提醒日期

		etMoneyAmount = (EditText) view.findViewById(R.id.et_otherbank_money_amount);//贷款金额

		cbox = (CheckBox) view.findViewById(R.id.cbox_edit);
		cbox.setVisibility(View.GONE);

		btnSave = (Button) view.findViewById(R.id.btnYes);
		btnNo = (Button) view.findViewById(R.id.btnNo);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnYes:
			String money = etMoneyAmount.getText().toString().trim();
			String outDate = tvOutDate.getText().toString().trim();
            String remindDate = spinDate.getText().toString().trim();



			if (Tool.checkInputStr(styleId)) {
				Toast.makeText(getActivity(), "请选择存款类型", Toast.LENGTH_SHORT).show();
				return;
			}
			if (Tool.checkInputStr(money)) {
				Toast.makeText(getActivity(), "请输入存款金额", Toast.LENGTH_SHORT).show();
				return;
			}
			if (Tool.checkInputStr(styleDuring)) {
				Toast.makeText(getActivity(), "请选择存款期限", Toast.LENGTH_SHORT).show();
				return;
			}
			if (Tool.checkInputStr(styleBank)) {
				Toast.makeText(getActivity(), "请选择存款行", Toast.LENGTH_SHORT).show();
				return;
			}

			if (Tool.checkInputStr(outDate) || outDate.equals("请选择到期日期")) {
				Toast.makeText(getActivity(), "请选择到期日期", Toast.LENGTH_SHORT).show();
				return;
			}
			if (Tool.checkInputStr(remindDate) || remindDate.equals("请选择提醒日期")) {
				Toast.makeText(getActivity(), "请选择提醒日期", Toast.LENGTH_SHORT).show();
				return;
			}

			if (isCanEdit) {
				setFunction(styleId, styleDuring, money, styleBank, outDate,remindDate);
			}
			dismiss();
			break;
		case R.id.btnNo:
			dismiss();
			break;
		case R.id.spin_othermoneystyle:// 存款类型
			SpinnerAdapter.showSelectDialog(getActivity(),
                    NewCatevalue.depositType,
                    spinStyle, new CallBackItemClickListener() {
                        @Override
                        public void CallBackItemClick(String spinnerID) {
                            styleId = spinnerID;
                        }
                    });
			break;
		case R.id.spin_othermoneydate:// 存款期限
			SpinnerAdapter.showSelectDialog(getActivity(),
					NewCatevalue.depositPeriod, spinTime,
					new CallBackItemClickListener() {
						@Override
						public void CallBackItemClick(String spinnerID) {
							styleDuring = spinnerID;
						}
					});
			break;
		case R.id.spin_otherbankname:// 存款行
			SpinnerAdapter.showSelectDialog(getActivity(),
					NewCatevalue.signBank, spinBankName,
					new CallBackItemClickListener() {
						@Override
						public void CallBackItemClick(String spinnerID) {
							styleBank = spinnerID;
						}
					});
			break;
		case R.id.tv_outdate:// 存款到期日
			setDatetimePickerDialog(tvOutDate, false);
			break;
        case R.id.tv_warndate:// 提醒日期
			setDatetimePickerDialog(spinDate, false);
			break;
		default:
			break;
		}
	}

	/**
	 * 设置时间
	 * 
	 * @param etText
	 */
	private void setDatetimePickerDialog(final TextView etText,
			final boolean isHasTime) {
		/*
		 * // 滚轮方法（摒弃） new WheelDateTimePickerDialog(context, new
		 * OnDateTimeSetListener() {
		 * 
		 * @Override public void onDateTimeSet(int year, int monthOfYear, int
		 * dayOfMonth, int hour, int minute) { String birthStr =
		 * year+"-"+monthOfYear+"-"+dayOfMonth+" "+hour+":"+minute+":00";
		 * etText.setText(birthStr); } }).show();
		 */
		DatePickerShow(new OnDateSetListener() {
			@Override
			public void onDateSet(String year, String month, String day) {
				String dateStr = year + "-" + month + "-" + day;
				if (isHasTime) {
					setTimePickerDialog(etText, dateStr);
				} else {
					etText.setText(dateStr);
				}
			}
		});
	}

	private void setTimePickerDialog(final TextView etText, final String dateStr) {
		TimePickerShow(new OnTimeSetListener() {
			@Override
			public void onTimeSet(int hourOfDay, int minute) {
				String date = dateStr + " " + hourOfDay + ":" + minute + ":00";
				etText.setText(date);
			}
		});
	}

	/**
	 * 日期选择器
	 * 
	 * @param onDateSetListener
	 */
	public void DatePickerShow(OnDateSetListener onDateSetListener) {
		Calendar calendar = Calendar.getInstance();
		DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
				onDateSetListener, calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		datePickerDialog.show(
				((FragmentActivity) getActivity()).getSupportFragmentManager(),
				"datepicker");
	}

	/**
	 * 时间选择器
	 * 
	 * @param OnTimeSetListener
	 */
	public void TimePickerShow(OnTimeSetListener OnTimeSetListener) {
		Calendar calendar = Calendar.getInstance();
		TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
				OnTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), true, false);
		timePickerDialog.show(
				((FragmentActivity) getActivity()).getSupportFragmentManager(),
				"timepicker");
	}

	public static DialogFragmentCreateOtherBankMoney getNewInstance() {
		DialogFragmentCreateOtherBankMoney fragment = new DialogFragmentCreateOtherBankMoney();
		return fragment;
	}

	public static DialogFragmentCreateOtherBankMoney getNewInstance(
			Boolean isEdit, Serializable entity) {
		DialogFragmentCreateOtherBankMoney fragment = new DialogFragmentCreateOtherBankMoney();
		Bundle bundle = new Bundle();
		bundle.putBoolean("isEdit", isEdit);
		bundle.putSerializable("entity", entity);
		fragment.setArguments(bundle);
		return fragment;
	}

	public void setCallBack(BookbuildingotherbankmoneyCallback callback) {
		this.callback = callback;
	}

	public void setFunction(String info1, String info2, String info3,
			String info4, String info5, String info6) {

		callback.bookbuildingOtherBankMoney(isEdit, info1, info2, info3, info4,
				info5,info6);
	}

}
