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
import com.pactera.financialmanager.callback.BookbuildingotherbankLoanInfoCallback;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog.OnDateSetListener;
import com.pactera.financialmanager.datetimepicker.TimePickerDialog;
import com.pactera.financialmanager.datetimepicker.TimePickerDialog.OnTimeSetListener;
import com.pactera.financialmanager.ui.model.OtherBankLoanInfoPublic;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 创建行外贷款信息的dialog
 */

public class DialogFragmentCreateOtherBankLoanInfo extends DialogFragment implements OnClickListener {
    private EditText spinStyle, spinTime;
    private EditText spinBankName;
    private EditText etMoney, etLoanRate;
    private TextView tvRemindDate;
    private TextView tvOutDate;
    private Button btnSave, btnNo;
    private BookbuildingotherbankLoanInfoCallback callback;
    private String styleId;// 贷款方式
//    private String styleDuring;// 贷款期限
    private String styleBank;// 贷款行

    private Boolean isEdit = false;
    private Boolean isCanEdit = true;
    private CheckBox cbox;
    private OtherBankLoanInfoPublic entity;
    private TextView tvTitle;

    public static DialogFragmentCreateOtherBankLoanInfo getNewInstance() {
        DialogFragmentCreateOtherBankLoanInfo fragment = new DialogFragmentCreateOtherBankLoanInfo();
        return fragment;
    }

    public static DialogFragmentCreateOtherBankLoanInfo getNewInstance(
            Boolean isEdit, Serializable entity) {
        DialogFragmentCreateOtherBankLoanInfo fragment = new DialogFragmentCreateOtherBankLoanInfo();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isEdit", isEdit);
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getActivity(),
                R.layout.fragmentdialog_createotherbankloaninfo, null);
        setupView(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            cbox.setVisibility(View.VISIBLE);
            isEdit = bundle.getBoolean("isEdit");
            entity = (OtherBankLoanInfoPublic) bundle.getSerializable("entity");
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
        tvRemindDate.setOnClickListener(this);
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
        String loan_type = entity.LOAN_TYPE;// 贷款方式
        String loan_period = entity.LOAN_PERIOD;// 贷款期限
        String loanRate = entity.LOAN_RATE;// 贷款利率
        String loanAmount = entity.LOAN_AMOUNT;// 贷款金额
        String loanBank = entity.LOAN_BANK;// 贷款行
        String end_date = entity.END_DATE;// 到期日期
        String remindDate = entity.REMIND_DATE;// 提醒日期

        styleId = loan_type;
        styleBank = loanBank;
        String strLoanType = NewCatevalue.getLabel(getActivity(), NewCatevalue.loanType, loan_type);
        String strLoanBank = NewCatevalue.getLabel(getActivity(), NewCatevalue.depositBank, loanBank);


        spinStyle.setText(strLoanType);
        spinTime.setText(loan_period);
        etLoanRate.setText(loanRate);
        etMoney.setText(loanAmount);
        spinBankName.setText(strLoanBank);
        tvOutDate.setText(end_date);
        tvRemindDate.setText(remindDate);

    }

    private void canNotEdit() {
        isCanEdit = false;
        spinStyle.setEnabled(false);
        spinTime.setEnabled(false);
        etMoney.setEnabled(false);
        spinBankName.setEnabled(false);
        tvRemindDate.setEnabled(false);
        tvOutDate.setEnabled(false);
        etLoanRate.setEnabled(false);
    }

    private void canEdit() {
        isCanEdit = true;
        spinStyle.setEnabled(true);
        spinTime.setEnabled(true);
        etMoney.setEnabled(true);
        spinBankName.setEnabled(true);
        tvRemindDate.setEnabled(true);
        tvOutDate.setEnabled(true);
        etLoanRate.setEnabled(true);
    }

    private void setupView(View view) {
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        spinStyle = (EditText) view.findViewById(R.id.spin_othermoneystyle);// 存款类型
        spinTime = (EditText) view.findViewById(R.id.spin_othermoneydate);// 存款期限
        spinBankName = (EditText) view.findViewById(R.id.spin_otherbankname);// 存款行
        tvOutDate = (TextView) view.findViewById(R.id.tv_outdate);// 存款到期日
        tvRemindDate = (TextView) view.findViewById(R.id.tv_warndate);// 提醒日期

        etMoney = (EditText) view.findViewById(R.id.et_otherbank_loan_money_amount);//贷款金额
        etLoanRate = (EditText) view.findViewById(R.id.et_loan_rate);//贷款利率

        cbox = (CheckBox) view.findViewById(R.id.cbox_edit);
        cbox.setVisibility(View.GONE);

        btnSave = (Button) view.findViewById(R.id.btnYes);
        btnNo = (Button) view.findViewById(R.id.btnNo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnYes:
                String money = etMoney.getText().toString().trim();
                String remindDate = tvRemindDate.getText().toString().trim();
                String outDate = tvOutDate.getText().toString().trim();
                String loanRate = etLoanRate.getText().toString().trim();
                String loanPeriod = spinTime.getText().toString().trim();


                if (Tool.checkInputStr(styleId)) {
                    Toast.makeText(getActivity(), "请选择贷款方式", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Tool.checkInputStr(loanPeriod)) {
                    Toast.makeText(getActivity(), "请选择贷款期限", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Tool.checkInputStr(loanRate)) {
                    Toast.makeText(getActivity(), "请输入贷款利率", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Tool.checkValidRatio(loanRate)) {
                    Toast.makeText(getActivity(), "请输入有效贷款利率", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Tool.checkInputStr(money)) {
                    Toast.makeText(getActivity(), "请输入贷款金额", Toast.LENGTH_SHORT).show();
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
                    setFunction(styleId, loanPeriod,loanRate, money, styleBank, outDate, remindDate);
                }
                dismiss();
                break;
            case R.id.btnNo:
                dismiss();
                break;
            case R.id.spin_othermoneystyle:// 贷款方式
                SpinnerAdapter.showSelectDialog(getActivity(),
                        NewCatevalue.loanType,
                        spinStyle, new CallBackItemClickListener() {
                            @Override
                            public void CallBackItemClick(String spinnerID) {
                                styleId = spinnerID;
                            }
                        });
                break;
//            case R.id.spin_othermoneydate:// 贷款期限
//                SpinnerAdapter.showSelectDialog(getActivity(),
//                        NewCatevalue.depositPeriod,
//                        spinTime, new CallBackItemClickListener() {
//                            @Override
//                            public void CallBackItemClick(String spinnerID) {
//                                styleDuring = spinnerID;
//                            }
//                        });
//                break;
            case R.id.spin_otherbankname:// 贷款行
                SpinnerAdapter.showSelectDialog(getActivity(),
                        NewCatevalue.depositBank, spinBankName,
                        new CallBackItemClickListener() {
                            @Override
                            public void CallBackItemClick(String spinnerID) {
                                styleBank = spinnerID;
                            }
                        });
                break;
            case R.id.tv_outdate:// 贷款到期日
                setDatetimePickerDialog(tvOutDate, false);
                break;
            case R.id.tv_warndate:// 提醒日期
                setDatetimePickerDialog(tvRemindDate, false);
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

    public void setCallBack(BookbuildingotherbankLoanInfoCallback callback) {
        this.callback = callback;
    }

    public void setFunction(String info1, String info2, String info3,
                            String info4, String info5, String info6, String info7) {

        callback.bookbuildingOtherBanLoaninfo(isEdit, info1, info2, info3, info4,
                info5, info6, info7);
    }

}
