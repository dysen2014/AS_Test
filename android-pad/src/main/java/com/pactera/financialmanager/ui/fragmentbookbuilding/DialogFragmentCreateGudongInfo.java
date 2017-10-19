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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.adapter.SpinnerAdapter;
import com.pactera.financialmanager.callback.BookbuildingGudonginfoCallback;
import com.pactera.financialmanager.callback.BookbuildingZBGCinfoCallback;
import com.pactera.financialmanager.ui.model.GuQuanTouZiEntity;
import com.pactera.financialmanager.ui.model.ZiBenGouChengEntity;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

import java.io.Serializable;

/**
 * 股权投资的dialog
 */
public class DialogFragmentCreateGudongInfo extends DialogFragment implements OnClickListener {

    private Button btnSave, btnNo;
    private ImageView btnIsOwner;
    private int isNo = 1;
    private BookbuildingGudonginfoCallback callback;
    private CheckBox cboxEdit;
    private Boolean isEdit = false, isCanEdit = true;// 是否是编辑
    private GuQuanTouZiEntity entity;
    private TextView tvTitle;
    private LinearLayout llIsowner;

    private EditText etInfo1, etInfo2, etInfo3, etInfo5, etInfo6, etInfo7, etInfo8, etInfo9;
    private String strInfo5ID, strInfo7ID;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragmentdialog_creategudonginfo, null);
        setupView(view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            isEdit = bundle.getBoolean("isEdit");
            cboxEdit.setVisibility(View.VISIBLE);
            entity = (GuQuanTouZiEntity) bundle.getSerializable("entity");
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

    // 设置数据
    private void setData() {
        canNotEdit();
        tvTitle.setText("查看股权投资信息");
        etInfo1.setText(entity.getINVEST_ENTER_NAME());
        etInfo2.setText(entity.getRATIO_STOCK());
        etInfo3.setText(entity.getINVEST_BAL());
        etInfo6.setText(entity.getORG_CODE());
        etInfo8.setText(entity.getLOAN_ACC());
        etInfo9.setText(entity.getDES());

        String type = entity.getINVEST_TYPE();
        String typeVal = NewCatevalue.getLabel(getActivity(), NewCatevalue.fundingType, (null == type) ? "" : type);
        etInfo5.setText(typeVal);
        strInfo5ID = type;

        type = entity.getCURRENCY();
        typeVal = NewCatevalue.getLabel(getActivity(), NewCatevalue.CRCYCD, (null == type) ? "" : type);
        etInfo7.setText(typeVal);
        strInfo7ID = type;

        String is_CONTROL = entity.getIS_CONTROL();
        if ("Toast.LENGTH_SHORT".equals(is_CONTROL)) {
            btnIsOwner.setEnabled(false);
            isNo = Toast.LENGTH_SHORT;
        } else {
            btnIsOwner.setEnabled(true);
            isNo = 1;
        }
    }

    // 不可编辑
    private void canNotEdit() {
        isCanEdit = false;
        etInfo1.setEnabled(false);
        etInfo2.setEnabled(false);
        etInfo3.setEnabled(false);
        etInfo5.setEnabled(false);
        etInfo6.setEnabled(false);
        etInfo7.setEnabled(false);
        etInfo8.setEnabled(false);
        etInfo9.setEnabled(false);
        llIsowner.setEnabled(false);
    }

    // 可编辑
    private void canEdit() {
        isCanEdit = true;
        etInfo1.setEnabled(true);
        etInfo2.setEnabled(true);
        etInfo3.setEnabled(true);
        etInfo5.setEnabled(true);
        etInfo6.setEnabled(true);
        etInfo7.setEnabled(true);
        etInfo8.setEnabled(true);
        etInfo9.setEnabled(true);
        llIsowner.setEnabled(true);

    }

    private void setListener() {
        llIsowner.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnNo.setOnClickListener(this);
        etInfo5.setOnClickListener(this);
        etInfo7.setOnClickListener(this);
        cboxEdit.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    cboxEdit.setText("取消");
                    canEdit();
                    tvTitle.setText("编辑股权投资信息");
                } else {
                    cboxEdit.setText("编辑");
                    canNotEdit();
                    tvTitle.setText("查看股权投资信息");
                }
            }
        });
    }

    private void setupView(View view) {
        tvTitle = (TextView) view.findViewById(R.id.tv_title_gudong);
        cboxEdit = (CheckBox) view.findViewById(R.id.cbox_edit);// 编辑
        cboxEdit.setVisibility(View.GONE);
        etInfo1 = (EditText) view.findViewById(R.id.et_gudonginfo1);
        etInfo2 = (EditText) view.findViewById(R.id.et_gudonginfo2);
        etInfo3 = (EditText) view.findViewById(R.id.et_gudonginfo3);
        etInfo5 = (EditText) view.findViewById(R.id.et_gudonginfo5);
        etInfo6 = (EditText) view.findViewById(R.id.et_gudonginfo6);
        etInfo7 = (EditText) view.findViewById(R.id.et_gudonginfo7);
        etInfo8 = (EditText) view.findViewById(R.id.et_gudonginfo8);
        etInfo9 = (EditText) view.findViewById(R.id.et_gudonginfo9);

        btnIsOwner = (ImageView) view.findViewById(R.id.btn_isowner);
        btnSave = (Button) view.findViewById(R.id.btnYes);
        btnNo = (Button) view.findViewById(R.id.btnNo);
        llIsowner = (LinearLayout) view.findViewById(R.id.ll_isowner);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnYes:
                if (isCanEdit) {
                    String inputInfo1 = etInfo1.getText().toString().trim();
                    String inputInfo2 = etInfo2.getText().toString().trim();
                    String inputInfo3 = etInfo3.getText().toString().trim();
                    String inputInfo6 = etInfo6.getText().toString().trim();
                    String inputInfo8 = etInfo8.getText().toString().trim();
                    String inputInfo9 = etInfo9.getText().toString().trim();


                    if (Tool.checkInputStr(inputInfo1)) {
                        Toast.makeText(getActivity(), "请输入股东名称", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(inputInfo2)) {
                        Toast.makeText(getActivity(), "请输入股权比例", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!Tool.checkValidRatio(inputInfo2)) {
                        Toast.makeText(getActivity(), "请输入有效股权比例", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(inputInfo3)) {
                        Toast.makeText(getActivity(), "请输入投资金额", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(strInfo5ID)) {
                        Toast.makeText(getActivity(), "请选择出资方式", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(inputInfo6)) {
                        Toast.makeText(getActivity(), "请输入组织机构代码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(strInfo7ID)) {
                        Toast.makeText(getActivity(), "请选择币种", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(inputInfo8)) {
                        Toast.makeText(getActivity(), "请输入贷款卡号", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    setFunction(inputInfo1, inputInfo2, inputInfo3, strInfo5ID,
                            inputInfo6, strInfo7ID, inputInfo8, inputInfo9);
                }
                dismiss();
                break;
            case R.id.btnNo:
                dismiss();
                break;
            case R.id.ll_isowner:
                if (isNo == 1) {
                    btnIsOwner.setEnabled(false);
                    isNo = Toast.LENGTH_SHORT;
                } else {
                    btnIsOwner.setEnabled(true);
                    isNo = 1;
                }
                break;
            case R.id.et_gudonginfo5:
                SpinnerAdapter.showSelectDialog(getActivity(),
                        NewCatevalue.fundingType, etInfo5,
                        new SpinnerAdapter.CallBackItemClickListener() {
                            @Override
                            public void CallBackItemClick(String spinnerID) {
                                strInfo5ID = spinnerID;
                            }
                        });
                break;
            case R.id.et_gudonginfo7:
                SpinnerAdapter.showSelectDialog(getActivity(),
                        NewCatevalue.CRCYCD, etInfo7,
                        new SpinnerAdapter.CallBackItemClickListener() {
                            @Override
                            public void CallBackItemClick(String spinnerID) {
                                strInfo7ID = spinnerID;
                            }
                        });
                break;
            default:
                break;
        }
    }

    public void setCallBack(BookbuildingGudonginfoCallback callback) {
        this.callback = callback;
    }


    public static DialogFragmentCreateGudongInfo getNewInstance() {
        DialogFragmentCreateGudongInfo fragment = new DialogFragmentCreateGudongInfo();
        return fragment;
    }

    public static DialogFragmentCreateGudongInfo getNewInstance(Boolean isEdit, Serializable listEntity) {
        DialogFragmentCreateGudongInfo fragment = new DialogFragmentCreateGudongInfo();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isEdit", isEdit);
        bundle.putSerializable("entity", listEntity);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setFunction(String inputInfo1, String inputInfo2, String inputInfo3, String inputInfo5,
                            String inputInfo6, String inputInfo7, String inputInfo8, String inputInfo9) {
        if (isNo == 1) {
            callback.bookbuildingGugongInfo(isEdit,
                    inputInfo1, inputInfo2, inputInfo3, "1", inputInfo5,
                    inputInfo6, inputInfo7, inputInfo8, inputInfo9);
        } else {
            callback.bookbuildingGugongInfo(isEdit,
                    inputInfo1, inputInfo2, inputInfo3, "Toast.LENGTH_SHORT", inputInfo5,
                    inputInfo6, inputInfo7, inputInfo8, inputInfo9);
        }
    }

}
