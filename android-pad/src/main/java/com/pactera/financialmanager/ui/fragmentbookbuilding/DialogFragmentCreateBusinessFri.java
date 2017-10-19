package com.pactera.financialmanager.ui.fragmentbookbuilding;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
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
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.adapter.SpinnerAdapter;
import com.pactera.financialmanager.adapter.SpinnerAdapter.CallBackItemClickListener;
import com.pactera.financialmanager.callback.BookbuildingContactCallback;
import com.pactera.financialmanager.entity.OtherBankEntity;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

import java.io.Serializable;

/**
 * 创建其他关联方情况的dialog
 */
public class DialogFragmentCreateBusinessFri extends DialogFragment implements
        OnClickListener {
    private EditText etInfo1, etInfo3, etInfo5, etInfo6, etInfo7, etInfo8, etInfo9, etInfo10, etInfo11;
    private Button btnContactYes, btnContactNo;
    private EditText spinRelationStyle, spinCertSytle;
    private BookbuildingContactCallback callback;
    private String spRelationStateId, spZhengJianStateId;
    private CheckBox cboxEdit;
    private Boolean isEdit = false;
    private Boolean isCanEdit = true;
    private OtherBankEntity entity;
    private TextView tvTitle;

    public static DialogFragmentCreateBusinessFri getNewInstance() {
        DialogFragmentCreateBusinessFri fragment = new DialogFragmentCreateBusinessFri();
        return fragment;
    }

    public static DialogFragmentCreateBusinessFri getNewInstance(
            Boolean isEdit, Serializable entity) {
        DialogFragmentCreateBusinessFri fragment = new DialogFragmentCreateBusinessFri();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isEdit", isEdit);
        bundle.putSerializable("entity", entity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragmentdialog_createconbusinessfri, null);
        setupView(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            cboxEdit.setVisibility(View.VISIBLE);
            isEdit = bundle.getBoolean("isEdit");
            entity = (OtherBankEntity) bundle.getSerializable("entity");
            setData();
        }
        setupListener();
        Dialog dialog = new Dialog(getActivity(), R.style.dialog1);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

//        int w = Constants.SCREEN_WIDTH * 1109 / 2048;
//        int h = w * 800 / 969;
        int w = Constants.SCREEN_WIDTH * 969 / 2048;
        int h = w * 926 / 949;
        Log.i("width.....height......", "width:" + w + " ; " + "height:" + h);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, h);
        dialog.addContentView(view, params);

        return dialog;
    }

    private void setData() {
        canNotEdit();
        tvTitle.setText("查看其它关联方情况");

        String cert_TYPE = entity.getRELATE_CERT_TYPE();
        String typeVal = NewCatevalue.getLabel(getActivity(), NewCatevalue.CERTTP, cert_TYPE);
        spZhengJianStateId = cert_TYPE;
        spinCertSytle.setText(typeVal);

        String relations = entity.getRELATIONS();
//		String relaVal = Catevalue.getName(relations, CompayValue.infoStr, CompayValue.infoValue);
        String relaVal = NewCatevalue.getLabel(getActivity(), NewCatevalue.ent_other_rel, relations);

        spRelationStateId = relations;
        spinRelationStyle.setText(relaVal);

        etInfo1.setText(entity.getRELATE_NAME());
        etInfo3.setText(entity.getRELATE_CERT_NO());
        etInfo5.setText(entity.getF_COMPANY());// 法人
        etInfo6.setText(entity.getSETTLE_TYPE());// 结算方式
        etInfo7.setText(entity.getDES());// 备注
        etInfo8.setText(entity.getREGISTER_NAME());
        etInfo9.setText(entity.getREGISTER_ORG());
        etInfo10.setText(entity.getREGISTER_TIME());
        etInfo11.setText(entity.getUPDATE_TIME());
    }

    private void setupListener() {
        spinCertSytle.setOnClickListener(this);
        spinRelationStyle.setOnClickListener(this);
        btnContactYes.setOnClickListener(this);
        btnContactNo.setOnClickListener(this);
        cboxEdit.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    cboxEdit.setText("取消");
                    canEdit();
                    tvTitle.setText("编辑其它关联方情况");
                } else {
                    cboxEdit.setText("编辑");
                    canNotEdit();
                    tvTitle.setText("查看其它关联方情况");
                }
            }
        });
    }

    private void canEdit() {
        isCanEdit = true;
        spinCertSytle.setEnabled(true);
        etInfo1.setEnabled(true);
        etInfo3.setEnabled(true);
        spinRelationStyle.setEnabled(true);
        etInfo5.setEnabled(true);
        etInfo6.setEnabled(true);
        etInfo7.setEnabled(false);
        etInfo8.setEnabled(false);
        etInfo9.setEnabled(false);
        etInfo10.setEnabled(false);
        etInfo11.setEnabled(false);
    }

    private void canNotEdit() {
        isCanEdit = false;
        spinCertSytle.setEnabled(false);
        etInfo1.setEnabled(false);
        etInfo3.setEnabled(false);
        spinRelationStyle.setEnabled(false);
        etInfo5.setEnabled(false);
        etInfo6.setEnabled(false);
        etInfo7.setEnabled(false);
        etInfo8.setEnabled(false);
        etInfo9.setEnabled(false);
        etInfo10.setEnabled(false);
        etInfo11.setEnabled(false);
    }

    private void setupView(View view) {
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        etInfo1 = (EditText) view.findViewById(R.id.et_dialog_otherrelation_1);
        etInfo3 = (EditText) view.findViewById(R.id.et_dialog_otherrelation_3);
        etInfo5 = (EditText) view.findViewById(R.id.et_dialog_otherrelation_5);
        etInfo6 = (EditText) view.findViewById(R.id.et_dialog_otherrelation_6);
        etInfo7 = (EditText) view.findViewById(R.id.et_dialog_otherrelation_7);
        etInfo8 = (EditText) view.findViewById(R.id.et_dialog_otherrelation_8);
        etInfo9 = (EditText) view.findViewById(R.id.et_dialog_otherrelation_9);
        etInfo10 = (EditText) view.findViewById(R.id.et_dialog_otherrelation_10);
        etInfo11 = (EditText) view.findViewById(R.id.et_dialog_otherrelation_11);

        cboxEdit = (CheckBox) view.findViewById(R.id.cbox_edit);
        cboxEdit.setVisibility(View.GONE);

        // 关联关系
        spinRelationStyle = (EditText) view.findViewById(R.id.et_dialog_otherrelation_4);
        // 关联方证件类型
        spinCertSytle = (EditText) view.findViewById(R.id.et_dialog_otherrelation_2);
        btnContactYes = (Button) view.findViewById(R.id.dialogbusinessfri_btnYes);
        btnContactNo = (Button) view.findViewById(R.id.dialogbusinessfri_btnNo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialogbusinessfri_btnYes:
                String info1 = etInfo1.getText().toString().trim();
                String info3 = etInfo3.getText().toString().trim();
                String info5 = etInfo5.getText().toString().trim();
                String info6 = etInfo6.getText().toString().trim();
                String info7 = etInfo7.getText().toString().trim();


                if (Tool.checkInputStr(info1)) {
                    Toast.makeText(getActivity(), "请输入关联方名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Tool.checkInputStr(spZhengJianStateId)) {
                    Toast.makeText(getActivity(), "请选择关联方证件类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Tool.checkInputStr(info3)) {
                    Toast.makeText(getActivity(), "请输入关联方证件号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Tool.checkInputStr(spRelationStateId)) {
                    Toast.makeText(getActivity(), "请选择关联关系", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Tool.checkInputStr(info5)) {
                    Toast.makeText(getActivity(), "请输入法人代表公司", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Tool.checkInputStr(info6)) {
                    Toast.makeText(getActivity(), "请输入结算方式", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isCanEdit) {
                    if (null == spZhengJianStateId)
                        spZhengJianStateId = "";
                    if (null == spRelationStateId)
                        spRelationStateId = "";
                    setFunction(info1, spZhengJianStateId, info3, spRelationStateId, info5, info6, info7);
                }
                dismiss();
                break;
            case R.id.dialogbusinessfri_btnNo:
                dismiss();
                break;
            case R.id.et_dialog_otherrelation_4:// 关联关系
                SpinnerAdapter.showSelectDialog(getActivity(),
                        NewCatevalue.ent_other_rel,
                        spinRelationStyle,
                        new CallBackItemClickListener() {
                            @Override
                            public void CallBackItemClick(String spinnerID) {
                                spRelationStateId = spinnerID;

                            }
                        });
                break;
            case R.id.et_dialog_otherrelation_2:// 证件类型
                SpinnerAdapter.showSelectDialog(getActivity(),
                        NewCatevalue.CERTTP, spinCertSytle,
                        new CallBackItemClickListener() {
                            @Override
                            public void CallBackItemClick(String spinnerID) {
                                spZhengJianStateId = spinnerID;
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
     */
    public void setFunction(String info1, String info2,
                            String info3, String info4, String info5, String info6, String info7) {
        callback.bookbuildingContact(isEdit, info1, info2,
                info3, info4, info5, info6, info7, "");
    }

}
