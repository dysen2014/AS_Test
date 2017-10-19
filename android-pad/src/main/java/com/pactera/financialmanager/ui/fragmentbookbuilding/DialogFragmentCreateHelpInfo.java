package com.pactera.financialmanager.ui.fragmentbookbuilding;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
import com.pactera.financialmanager.callback.BookbuildingHelpInfoCallback;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog.OnDateSetListener;
import com.pactera.financialmanager.ui.model.DanBaoInfoEntity;
import com.pactera.financialmanager.util.Catevalue;
import com.pactera.financialmanager.util.CompayValue;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

/**
 * 对外担保关系的dialog
 */
public class DialogFragmentCreateHelpInfo extends DialogFragment implements
        OnClickListener {
    private EditText etHelp1, etHelp2, etHelp3, etHelp6, etHelp7;
    private TextView etHelp4, etHelp5, tvTitle;
    private Button btnContactYes, btnContactNo;
    private BookbuildingHelpInfoCallback callback;
    private String spStyle;// 业务种类
    private Boolean isEdit = false;// 默认的是false,false是创建；true，就是编辑。
    private Boolean isCanEdit = true;// 默认的是true,true是可以编辑；false，就是不可以编辑。
    private DanBaoInfoEntity entity;
    private CheckBox cbox;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getActivity(),
                R.layout.fragmentdialog_createhelpinfo, null);
        setupView(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            cbox.setVisibility(View.VISIBLE);
            isEdit = bundle.getBoolean("isEdit");
            entity = (DanBaoInfoEntity) bundle.getSerializable("entity");
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
        tvTitle.setText("查看对外担保关系");
        etHelp1.setText(entity.getWARRANT_NAME());
        etHelp2.setText(entity.getINSURE_MAN());
        String industry = entity.getBUSI_INDUSTRY();
//		String industryVal = Catevalue.getName(industry, CompayValue.infoStr,
//				CompayValue.infoValue);
        String industryVal = NewCatevalue.getLabel(getActivity(), NewCatevalue.out_warrant_type, industry);


        spStyle = industry;
        etHelp3.setText(industryVal);
        etHelp4.setText(entity.getSTART_DT());
        etHelp5.setText(entity.getEXPIRED_DT());
        etHelp6.setText(entity.getWARRANT_AMOUNT());
        etHelp7.setText(entity.getBUSI_ORG());
//		etHelp8.setText(entity.getDES());
    }

    private void setupListener() {
        etHelp5.setOnClickListener(this);
        etHelp4.setOnClickListener(this);
        etHelp3.setOnClickListener(this);
        btnContactYes.setOnClickListener(this);
        btnContactNo.setOnClickListener(this);
        cbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    cbox.setText("取消");
                    canEdit();
                    tvTitle.setText("编辑对外担保关系");
                } else {
                    cbox.setText("编辑");
                    canNotEdit();
                    tvTitle.setText("查看对外担保关系");
                }
            }
        });
    }

    private void canEdit() {
        isCanEdit = true;
        etHelp1.setEnabled(true);
        etHelp2.setEnabled(true);
        etHelp3.setEnabled(true);
        etHelp4.setEnabled(true);
        etHelp5.setEnabled(true);
        etHelp6.setEnabled(true);
        etHelp7.setEnabled(true);
//		etHelp8.setEnabled(true);
    }

    private void canNotEdit() {
        isCanEdit = false;
        etHelp1.setEnabled(false);
        etHelp2.setEnabled(false);
        etHelp3.setEnabled(false);
        etHelp4.setEnabled(false);
        etHelp5.setEnabled(false);
        etHelp6.setEnabled(false);
        etHelp7.setEnabled(false);
//		etHelp8.setEnabled(false);
    }

    private void setupView(View view) {
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        etHelp1 = (EditText) view.findViewById(R.id.et_helpinfo1);
        etHelp2 = (EditText) view.findViewById(R.id.et_helpinfo2);
        etHelp3 = (EditText) view.findViewById(R.id.et_helpinfo3);
        etHelp4 = (TextView) view.findViewById(R.id.tv_startdate4);// 起始日期
        etHelp5 = (TextView) view.findViewById(R.id.tv_enddate5);// 到期日期
        etHelp6 = (EditText) view.findViewById(R.id.et_helpinfo6);
        etHelp7 = (EditText) view.findViewById(R.id.et_helpinfo7);

        cbox = (CheckBox) view.findViewById(R.id.cbox_edit);
        cbox.setVisibility(View.GONE);

        btnContactYes = (Button) view.findViewById(R.id.helpinfo_btnYes);
        btnContactNo = (Button) view.findViewById(R.id.helpinfo_btnNo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.helpinfo_btnYes:
                String etInputInfo1 = etHelp1.getText().toString().trim();
                String etInputInfo2 = etHelp2.getText().toString().trim();
                String etInputInfo3 = etHelp3.getText().toString().trim();
                String etInputInfo4 = etHelp4.getText().toString().trim();
                String etInputInfo5 = etHelp5.getText().toString().trim();
                String etInputInfo6 = etHelp6.getText().toString().trim();
                String etInputInfo7 = etHelp7.getText().toString().trim();
                if (isCanEdit) {
                    if (Tool.checkInputStr(etInputInfo1)) {
                        Toast.makeText(getActivity(), "请输入被担保人名称", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(etInputInfo2)) {
                        Toast.makeText(getActivity(), "请输入担保人名称", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(etInputInfo3)) {
                        Toast.makeText(getActivity(), "请选择业务种类", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(etInputInfo4) || etInputInfo4.equals("请选择起始日期")) {
                        Toast.makeText(getActivity(), "请选择起始日期", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (Tool.checkInputStr(etInputInfo5) || etInputInfo5.equals("请选择到期日期")) {
                        Toast.makeText(getActivity(), "请选择到期日期", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(etInputInfo6)) {
                        Toast.makeText(getActivity(), "请输入担保金额", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(etInputInfo7)) {
                        Toast.makeText(getActivity(), "请输入经办机构", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Boolean isRightTime;
                    try {
                        isRightTime = DateCompare(etInputInfo5, etInputInfo4);
                        if (!isRightTime) {
                            Toast.makeText(getActivity(), "起始日期要小于到期日期", Toast.LENGTH_LONG).show();
                            return;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    setFunction(etInputInfo1, etInputInfo2, spStyle, etInputInfo4,
                            etInputInfo5, etInputInfo6, etInputInfo7);
                }
                dismiss();
                break;
            case R.id.helpinfo_btnNo:
                dismiss();
                break;
            case R.id.et_helpinfo3:// 业务种类
                SpinnerAdapter.showSelectDialog(getActivity(), NewCatevalue.out_warrant_type, etHelp3,
                        new SpinnerAdapter.CallBackItemClickListener() {
                            @Override
                            public void CallBackItemClick(String spinnerID) {
                                spStyle = spinnerID;
                            }
                        });
                break;
            case R.id.tv_startdate4:// 起始日期
                DatePickerShow(new OnDateSetListener() {
                    @Override
                    public void onDateSet(String year, String month, String day) {
                        // TODO Auto-generated method stub
                        String dayate = year + "-" + month + "-" + day;
                        etHelp4.setText(dayate);
                    }
                });
                break;
            case R.id.tv_enddate5:// 到期日期
                DatePickerShow(new OnDateSetListener() {
                    @Override
                    public void onDateSet(String year, String month, String day) {
                        // TODO Auto-generated method stub
                        String dayate = year + "-" + month + "-" + day;
                        etHelp5.setText(dayate);
                    }
                });
                break;
            default:
                break;
        }
    }

    public Boolean DateCompare(String s1, String s2) throws ParseException {
        //设定时间的模板
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //得到指定模范的时间
        Date d1 = sdf.parse(s1);
        Date d2 = sdf.parse(s2);
        if ((d1.getTime() - d2.getTime()) / (24 * 3600 * 1000) > Toast.LENGTH_SHORT) {
            return true;
        } else {
            return false;
        }
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

    public static DialogFragmentCreateHelpInfo getNewInstance() {
        DialogFragmentCreateHelpInfo fragment = new DialogFragmentCreateHelpInfo();
        return fragment;
    }

    public static DialogFragmentCreateHelpInfo getNewInstance(Boolean isEdit,
                                                              Serializable entity) {
        DialogFragmentCreateHelpInfo fragment = new DialogFragmentCreateHelpInfo();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isEdit", isEdit);
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setCallBack(BookbuildingHelpInfoCallback callback) {
        this.callback = callback;
    }

    /**
     * 这是实现创建联络人信息的回调实现
     *
     * @param etInputInfo8
     * @param etInputInfo7
     * @param etInputInfo6
     * @param etInputInfo5
     * @param etInputInfo4
     * @param spStyle2
     * @param etInputInfo2
     * @param etInputInfo1
     */
    public void setFunction(String etInputInfo1, String etInputInfo2,
                            String spStyle2, String etInputInfo4, String etInputInfo5,
                            String etInputInfo6, String etInputInfo7) {
        callback.bookbuildingHelpInfo(isEdit, etInputInfo1, etInputInfo2, spStyle2,
                etInputInfo4, etInputInfo5, etInputInfo6, etInputInfo7);
    }
}
