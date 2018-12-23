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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.callback.BookbuildingCaiWuInfoCallback;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog.OnDateSetListener;
import com.pactera.financialmanager.ui.model.FuzaiInfoEntity;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.Tool;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 这是客户建档（对公）基本信息－财务信息的dialog
 */
public class DialogFragmentCreateCaiWuInfo extends DialogFragment implements OnClickListener {
    private Button btnYes, btnNo;
    private TextView tvTitle;
    private EditText etContent1, etContent2, etContent3,
            etContent4, etContent5, etContent6,
            etContent7, etContent8, etContent9;
    // isEdit,用于判断是否是编辑。false,不是编辑，即创建；true，是编辑。默认为false。它是传过来的。
    // isCanEdit,用于判断是否在编辑。true,表示可以编辑；false，表示不可编辑。若不可编辑，则点击确定按钮，无效。
    private Boolean isEdit = false, isCanEdit = true;
    private FuzaiInfoEntity entity;
    private CheckBox cbox;
    private BookbuildingCaiWuInfoCallback callback;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragmentdialog_createcaiwuinfo, null);
        setupView(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isEdit = bundle.getBoolean("isEdit");
            cbox.setVisibility(View.VISIBLE);
            entity = (FuzaiInfoEntity) bundle.getSerializable("entity");
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

    private void setData() {
        canNotEdit();
        tvTitle.setText("查看财务信息");
        etContent1.setText(entity.getLAST_ANNUAL_INCOME());
        etContent2.setText(entity.getLAST_ANNUAL_OUTPUT());
        etContent3.setText(entity.getPROFIT());
        etContent4.setText(entity.getHAND_TAX());
        etContent5.setText(entity.getFIX_ASSET());
        etContent6.setText(entity.getHAND_CREDIT());
        etContent7.setText(entity.getINVENTORY());
        etContent8.setText(entity.getPAY_CREDIT());
        etContent9.setText(entity.getDEBT_RENTAL());
    }

    private void setupListener() {
        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);

        cbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbox.setText("取消");
                    canEdit();
                    tvTitle.setText("编辑财务信息");
                } else {
                    cbox.setText("编辑");
                    canNotEdit();
                    tvTitle.setText("查看财务信息");
                }
            }
        });


    }

    private void canEdit() {
        isCanEdit = true;
        etContent1.setEnabled(true);
        etContent2.setEnabled(true);
        etContent3.setEnabled(true);
        etContent4.setEnabled(true);
        etContent5.setEnabled(true);
        etContent6.setEnabled(true);
        etContent7.setEnabled(true);
        etContent8.setEnabled(true);
        etContent9.setEnabled(true);
    }

    private void canNotEdit() {
        isCanEdit = false;
        etContent1.setEnabled(false);
        etContent2.setEnabled(false);
        etContent3.setEnabled(false);
        etContent4.setEnabled(false);
        etContent5.setEnabled(false);
        etContent6.setEnabled(false);
        etContent7.setEnabled(false);
        etContent8.setEnabled(false);
        etContent9.setEnabled(false);
    }

    private void setupView(View view) {
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        etContent1 = (EditText) view.findViewById(R.id.et_caiwuinfo1);
        etContent2 = (EditText) view.findViewById(R.id.et_caiwuinfo2);
        etContent3 = (EditText) view.findViewById(R.id.et_caiwuinfo3);
        etContent4 = (EditText) view.findViewById(R.id.et_caiwuinfo4);
        etContent5 = (EditText) view.findViewById(R.id.et_caiwuinfo5);
        etContent6 = (EditText) view.findViewById(R.id.et_caiwuinfo6);
        etContent7 = (EditText) view.findViewById(R.id.et_caiwuinfo7);
        etContent8 = (EditText) view.findViewById(R.id.et_caiwuinfo8);
        etContent9 = (EditText) view.findViewById(R.id.et_caiwuinfo9);

        btnYes = (Button) view.findViewById(R.id.caiwuinfo_btnYes);
        btnNo = (Button) view.findViewById(R.id.caiwuinfo_btnNo);

        cbox = (CheckBox) view.findViewById(R.id.cbox_edit);
        cbox.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.caiwuinfo_btnYes:
                String etInputInfo1 = etContent1.getText().toString().trim();
                String etInputInfo2 = etContent2.getText().toString().trim();
                String etInputInfo3 = etContent3.getText().toString().trim();
                String etInputInfo4 = etContent4.getText().toString().trim();
                String etInputInfo5 = etContent5.getText().toString().trim();
                String etInputInfo6 = etContent6.getText().toString().trim();
                String etInputInfo7 = etContent7.getText().toString().trim();
                String etInputInfo8 = etContent8.getText().toString().trim();
                String etInputInfo9 = etContent9.getText().toString().trim();
                if (isCanEdit) {
                    if (Tool.checkInputStr(etInputInfo1)) {
                        Toast.makeText(getActivity(), "请输入上年营业收入", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(etInputInfo2)) {
                        Toast.makeText(getActivity(), "请输入上年总成本", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(etInputInfo3)) {
                        Toast.makeText(getActivity(), "请输入上年净利润", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(etInputInfo4)) {
                        Toast.makeText(getActivity(), "请输入上年上交税收", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(etInputInfo5)) {
                        Toast.makeText(getActivity(), "请输入固定资产", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(etInputInfo6)) {
                        Toast.makeText(getActivity(), "请输入应收账款", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(etInputInfo7)) {
                        Toast.makeText(getActivity(), "请输入存货", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(etInputInfo8)) {
                        Toast.makeText(getActivity(), "请输入应付账款", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(etInputInfo9)) {
                        Toast.makeText(getActivity(), "请输入负债总额", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    setFunction(etInputInfo1, etInputInfo2, etInputInfo3, etInputInfo4,
                            etInputInfo5, etInputInfo6, etInputInfo7, etInputInfo8, etInputInfo9);
                }
                dismiss();
                break;
            case R.id.caiwuinfo_btnNo:
                dismiss();
                break;
            default:
                break;
        }
    }

//    public Boolean DateCompare(String s1, String s2) throws ParseException {
//        //设定时间的模板
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        //得到指定模范的时间
//        Date d1 = sdf.parse(s1);
//        Date d2 = sdf.parse(s2);
//        if ((d1.getTime() - d2.getTime()) / (24 * 3600 * 1000) > 0) {
//            return true;
//        } else {
//            return false;
//        }
//    }

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

    public static DialogFragmentCreateCaiWuInfo getNewInstance() {
        DialogFragmentCreateCaiWuInfo fragment = new DialogFragmentCreateCaiWuInfo();
        return fragment;
    }

    public static DialogFragmentCreateCaiWuInfo getNewInstance(Boolean isEdit, Serializable entity) {
        DialogFragmentCreateCaiWuInfo fragment = new DialogFragmentCreateCaiWuInfo();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isEdit", isEdit);
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setCallBack(BookbuildingCaiWuInfoCallback callback) {
        this.callback = callback;
    }

    /**
     * 这是实现创建联络人信息的回调实现
     */
    public void setFunction(String etInputInfo1, String etInputInfo2,
                            String spStyle2, String etInputInfo4, String etInputInfo5,
                            String etInputInfo6, String etInputInfo7,
                            String etInputInfo8, String etInputInfo9) {
        callback.bookbuildingCaiWuInfo(isEdit, etInputInfo1, etInputInfo2, spStyle2,
                etInputInfo4, etInputInfo5, etInputInfo6, etInputInfo7, etInputInfo8, etInputInfo9);
    }
}
