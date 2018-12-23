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
import com.pactera.financialmanager.callback.BookbuildingotherbankSignProductCallback;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog.OnDateSetListener;
import com.pactera.financialmanager.datetimepicker.TimePickerDialog;
import com.pactera.financialmanager.datetimepicker.TimePickerDialog.OnTimeSetListener;
import com.pactera.financialmanager.ui.model.OtherBankSignProductPublic;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 创建行外签约产品信息的dialog
 */

public class DialogFragmentCreateOtherBankSignProduct extends DialogFragment implements OnClickListener {
    private EditText etSignName, etSignPhone, spinnerSignProduct, spinnerSignBank;
    private TextView tvSignDate;
    private Button btnSave, btnNo;
    private BookbuildingotherbankSignProductCallback callback;
    private String signProductId;// 签约产品
    private String signProductBankId;// 签约银行

    private Boolean isEdit = false;
    private Boolean isCanEdit = true;
    private CheckBox cbox;
    private OtherBankSignProductPublic entity;
    private TextView tvTitle;

    public static DialogFragmentCreateOtherBankSignProduct getNewInstance() {
        DialogFragmentCreateOtherBankSignProduct fragment = new DialogFragmentCreateOtherBankSignProduct();
        return fragment;
    }

    public static DialogFragmentCreateOtherBankSignProduct getNewInstance(Boolean isEdit, Serializable entity) {
        DialogFragmentCreateOtherBankSignProduct fragment = new DialogFragmentCreateOtherBankSignProduct();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isEdit", isEdit);
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragmentdialog_createotherbanksignproduct, null);
        setupView(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            cbox.setVisibility(View.VISIBLE);
            isEdit = bundle.getBoolean("isEdit");
            entity = (OtherBankSignProductPublic) bundle.getSerializable("entity");
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

    private void setupView(View view) {
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        etSignName = (EditText) view.findViewById(R.id.et_sign_name);//签约名称
        etSignPhone = (EditText) view.findViewById(R.id.et_sign_phone_num);// 签约手机号
        spinnerSignProduct = (EditText) view.findViewById(R.id.spin_other_signproduct);// 签约产品
        spinnerSignBank = (EditText) view.findViewById(R.id.spin_bank_signproduct);// 签约银行
        tvSignDate = (TextView) view.findViewById(R.id.tv_sign_date);// 签约时间

        cbox = (CheckBox) view.findViewById(R.id.cbox_edit);
        cbox.setVisibility(View.GONE);

        btnSave = (Button) view.findViewById(R.id.btnYes);
        btnNo = (Button) view.findViewById(R.id.btnNo);
    }

    private void setListener() {
        etSignName.setOnClickListener(this);
        etSignPhone.setOnClickListener(this);
        spinnerSignProduct.setOnClickListener(this);
        spinnerSignBank.setOnClickListener(this);
        tvSignDate.setOnClickListener(this);

        btnSave.setOnClickListener(this);
        btnNo.setOnClickListener(this);
        cbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    cbox.setText("取消");
                    canEdit();
                    tvTitle.setText("编辑行外签约产品信息");
                } else {
                    cbox.setText("编辑");
                    canNotEdit();
                    tvTitle.setText("查看行外签约产品信息");
                }
            }
        });
    }

    private void setData() {
        canNotEdit();
        tvTitle.setText("查看行外签约产品信息");
        String signAcc = entity.SIGN_ACC;// 签约名称
        String signPrd = entity.SIGN_PRD;// 签约产品
        String signBank = entity.SIGN_ORG;// 签约银行
        String signPhone = entity.SIGN_PHONE;// 签约手机号
        String signTime = entity.SIGN_TIME;// 签约时间

        signProductId = signPrd;
        signProductBankId = signBank;

        String strLoanType = NewCatevalue.getLabel(getActivity(), NewCatevalue.signType, signPrd);
        String strSignBank = NewCatevalue.getLabel(getActivity(), NewCatevalue.signBank, signBank);

        etSignName.setText(signAcc);
        spinnerSignProduct.setText(strLoanType);
        spinnerSignBank.setText(strSignBank);
        etSignPhone.setText(signPhone);
        tvSignDate.setText(signTime);

    }

    private void canNotEdit() {
        isCanEdit = false;
        etSignName.setEnabled(false);
        etSignPhone.setEnabled(false);
        spinnerSignProduct.setEnabled(false);
        spinnerSignBank.setEnabled(false);
        tvSignDate.setEnabled(false);
    }

    private void canEdit() {
        isCanEdit = true;
        etSignName.setEnabled(true);
        etSignPhone.setEnabled(true);
        spinnerSignProduct.setEnabled(true);
        spinnerSignBank.setEnabled(true);
        tvSignDate.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnYes:
                String name = etSignName.getText().toString().trim();
                String phone = etSignPhone.getText().toString().trim();
                String signDate = tvSignDate.getText().toString().trim();


                if (Tool.checkInputStr(name)) {
                    Toast.makeText(getActivity(), "请输入签约名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Tool.checkInputStr(signProductId)) {
                    Toast.makeText(getActivity(), "请选择签约产品", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Tool.checkInputStr(signProductBankId)) {
                    Toast.makeText(getActivity(), "请选择签约机构", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Tool.checkInputStr(phone)) {
                    Toast.makeText(getActivity(), "请输入签约手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Tool.checkInputStr(signDate) || signDate.equals("请选择日期")) {
                    Toast.makeText(getActivity(), "请选择日期", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isCanEdit) {
                    setFunction(name, signProductId, signProductBankId, phone, signDate);
                }
                dismiss();
                break;
            case R.id.btnNo:
                dismiss();
                break;
            case R.id.spin_other_signproduct:// 签约产品
                SpinnerAdapter.showSelectDialog(getActivity(),
                        NewCatevalue.signType,
                        spinnerSignProduct, new CallBackItemClickListener() {
                            @Override
                            public void CallBackItemClick(String spinnerID) {
                                signProductId = spinnerID;
                            }
                        });
                break;
            case R.id.spin_bank_signproduct:// 签约银行
                SpinnerAdapter.showSelectDialog(getActivity(),
                        NewCatevalue.signBank, spinnerSignBank,
                        new CallBackItemClickListener() {
                            @Override
                            public void CallBackItemClick(String spinnerID) {
                                signProductBankId = spinnerID;
                            }
                        });
                break;
            case R.id.tv_sign_date:// 签约时间
                setDatetimePickerDialog(tvSignDate, false);
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

    public void setCallBack(BookbuildingotherbankSignProductCallback callback) {
        this.callback = callback;
    }

    public void setFunction(String info1, String info2, String info3,
                            String info4, String info5) {

        callback.bookbuildingOtherBankSignProductInfo(isEdit, info1, info2, info3, info4, info5);
    }

}
