package com.pactera.financialmanager.ui.fragmentbookbuilding;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.pactera.financialmanager.callback.BookbuildingZBGCinfoCallback;
import com.pactera.financialmanager.ui.model.ZiBenGouChengEntity;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

import java.io.Serializable;

/**
 * 资本构成的dialog
 */
public class DialogFragmentCreateZBGCInfo extends DialogFragment implements OnClickListener {

    private Button btnSave, btnNo;
    private ImageView btnIsOwner;
    private int isNo = 1;
    private BookbuildingZBGCinfoCallback callback;
    private CheckBox cboxEdit;
    private Boolean isEdit = false, isCanEdit = true;// 是否是编辑
    private ZiBenGouChengEntity zibenEntity;
    private TextView tvTitle;
    private LinearLayout llIsowner;

    private EditText etInfo1, etInfo2, etInfo3, etInfo5, etInfo6, etInfo7, etInfo8, etInfo9, etInfo10;
    private String strInfo2ID, strInfo5ID, strInfo7ID, strInfo8ID;


    private boolean isStockTypePrivate = false;//股东类型是不是个人 ,false则为公司 ,true为个人

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragmentdialog_createzbgcinfo, null);
        setupView(view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            isEdit = bundle.getBoolean("isEdit");
            cboxEdit.setVisibility(View.VISIBLE);
            zibenEntity = (ZiBenGouChengEntity) bundle.getSerializable("entity");
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
        tvTitle.setText("查看资本构成信息");
        etInfo1.setText(zibenEntity.getSTOCK_NAME());

        String type = zibenEntity.getSHAREHOLDER_TYPE();
        String typeVal = NewCatevalue.getLabel(getActivity(), NewCatevalue.stock_type, (null == type) ? "" : type);
        etInfo2.setText(typeVal);
        setStockType(typeVal);
        strInfo2ID = type;

        type = zibenEntity.getCERT_TYPE();
        typeVal = NewCatevalue.getLabel(getActivity(), isStockTypePrivate ? NewCatevalue.CERTTP : NewCatevalue.C_CERTTP, (null == type) ? "" : type);
        etInfo5.setText(typeVal);
        strInfo5ID = type;

        type = zibenEntity.getPAY_TYPE();
        typeVal = NewCatevalue.getLabel(getActivity(), NewCatevalue.fundingType, (null == type) ? "" : type);
        etInfo7.setText(typeVal);
        strInfo7ID = type;

        type = zibenEntity.getCURRENCY();
        typeVal = NewCatevalue.getLabel(getActivity(), NewCatevalue.CRCYCD, (null == type) ? "" : type);
        etInfo8.setText(typeVal);
        strInfo8ID = type;

        etInfo3.setText(zibenEntity.getRATIO_INVEST());// 比例
        etInfo6.setText(zibenEntity.getCERT_NO());
        etInfo9.setText(zibenEntity.getBAL());
        etInfo10.setText(zibenEntity.getDES());

        String is_CONTROL = zibenEntity.getIS_CONTROL();
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
        etInfo10.setEnabled(false);
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
        etInfo10.setEnabled(true);
        llIsowner.setEnabled(true);

    }

    private void setListener() {
        llIsowner.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnNo.setOnClickListener(this);
        etInfo2.setOnClickListener(this);
        etInfo5.setOnClickListener(this);
        etInfo7.setOnClickListener(this);
        etInfo8.setOnClickListener(this);
        cboxEdit.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    cboxEdit.setText("取消");
                    canEdit();
                    tvTitle.setText("编辑资本构成信息");
                } else {
                    cboxEdit.setText("编辑");
                    canNotEdit();
                    tvTitle.setText("查看资本构成信息");
                }
            }
        });
    }

    private void setupView(View view) {
        tvTitle = (TextView) view.findViewById(R.id.tv_title_zbgc);
        cboxEdit = (CheckBox) view.findViewById(R.id.cbox_edit);// 编辑
        cboxEdit.setVisibility(View.GONE);
        etInfo1 = (EditText) view.findViewById(R.id.et_zbgcinfo1);
        etInfo2 = (EditText) view.findViewById(R.id.et_zbgcinfo2);
        etInfo3 = (EditText) view.findViewById(R.id.et_zbgcinfo3);
        etInfo5 = (EditText) view.findViewById(R.id.et_zbgcinfo5);
        etInfo6 = (EditText) view.findViewById(R.id.et_zbgcinfo6);
        etInfo7 = (EditText) view.findViewById(R.id.et_zbgcinfo7);
        etInfo8 = (EditText) view.findViewById(R.id.et_zbgcinfo8);
        etInfo9 = (EditText) view.findViewById(R.id.et_zbgcinfo9);
        etInfo10 = (EditText) view.findViewById(R.id.et_zbgcinfo10);
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
                    String inputInfo3 = etInfo3.getText().toString().trim();
                    String inputInfo6 = etInfo6.getText().toString().trim();
                    String inputInfo9 = etInfo9.getText().toString().trim();
                    String inputInfo10 = etInfo10.getText().toString().trim();


                    if (Tool.checkInputStr(inputInfo1)) {
                        Toast.makeText(getActivity(), "请输入股东名称", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(strInfo2ID)) {
                        Toast.makeText(getActivity(), "请选择股东类型", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(inputInfo3)) {
                        Toast.makeText(getActivity(), "请输入投资比例", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!Tool.checkValidRatio(inputInfo3)) {
                        Toast.makeText(getActivity(), "请输入有效投资比例", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(strInfo5ID)) {
                        Toast.makeText(getActivity(), "请选择证件类型", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(inputInfo6)) {
                        Toast.makeText(getActivity(), "请输入证件号码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(strInfo7ID)) {
                        Toast.makeText(getActivity(), "请选择出资方式", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(strInfo8ID)) {
                        Toast.makeText(getActivity(), "请选择币种", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Tool.checkInputStr(inputInfo9)) {
                        Toast.makeText(getActivity(), "请输入金额", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    setFunction(inputInfo1, strInfo2ID, inputInfo3, strInfo5ID,
                            inputInfo6, strInfo7ID, strInfo8ID, inputInfo9, inputInfo10);
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
            case R.id.et_zbgcinfo2:
                SpinnerAdapter.showSelectDialog(getActivity(),
                        NewCatevalue.stock_type,
                        etInfo2, new SpinnerAdapter.CallBackItemClickListener() {
                            @Override
                            public void CallBackItemClick(String spinnerID) {
                                //检查有没有改变过股东类型，改了重新选择证件类型
                                if (!TextUtils.isEmpty(strInfo2ID) && !strInfo2ID.equals(spinnerID)) {
                                    strInfo5ID = null;
                                    etInfo5.setText("");
                                }
                                //设置股东类型:  个人或者企业
                                strInfo2ID = spinnerID;
                                if (!TextUtils.isEmpty(strInfo2ID)) {
                                    String typeVal = NewCatevalue.getLabel(getActivity(), NewCatevalue.stock_type, strInfo2ID);
                                    setStockType(typeVal);
                                }
                            }
                        });


                break;
            case R.id.et_zbgcinfo5:
                SpinnerAdapter.showSelectDialog(getActivity(),
                        isStockTypePrivate ? NewCatevalue.CERTTP : NewCatevalue.C_CERTTP,
                        etInfo5,
                        new SpinnerAdapter.CallBackItemClickListener() {
                            @Override
                            public void CallBackItemClick(String spinnerID) {
                                strInfo5ID = spinnerID;
                            }
                        });
                break;
            case R.id.et_zbgcinfo7:
                SpinnerAdapter.showSelectDialog(getActivity(),
                        NewCatevalue.fundingType, etInfo7,
                        new SpinnerAdapter.CallBackItemClickListener() {
                            @Override
                            public void CallBackItemClick(String spinnerID) {
                                strInfo7ID = spinnerID;
                            }
                        });
                break;
            case R.id.et_zbgcinfo8:
                SpinnerAdapter.showSelectDialog(getActivity(),
                        NewCatevalue.CRCYCD, etInfo8,
                        new SpinnerAdapter.CallBackItemClickListener() {
                            @Override
                            public void CallBackItemClick(String spinnerID) {
                                strInfo8ID = spinnerID;
                            }
                        });
                break;
            default:
                break;
        }
    }


    /***
     * 设置股东类型:  个人或者企业
     **/
    public void setStockType(String type) {

        if (TextUtils.isEmpty(type)) {
            return;
        }
        if ("企业".equals(type)) {
            isStockTypePrivate = false;
        } else {
            isStockTypePrivate = true;
        }
    }

    public void setCallBack(BookbuildingZBGCinfoCallback callback) {
        this.callback = callback;
    }


    public static DialogFragmentCreateZBGCInfo getNewInstance() {
        DialogFragmentCreateZBGCInfo fragment = new DialogFragmentCreateZBGCInfo();
        return fragment;
    }

    public static DialogFragmentCreateZBGCInfo getNewInstance(Boolean isEdit, Serializable listEntity) {
        DialogFragmentCreateZBGCInfo fragment = new DialogFragmentCreateZBGCInfo();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isEdit", isEdit);
        bundle.putSerializable("entity", listEntity);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setFunction(String inputInfo1, String inputInfo2, String inputInfo3, String inputInfo5,
                            String inputInfo6, String inputInfo7, String inputInfo8, String inputInfo9, String inputInfo10) {
        if (isNo == 1) {
            callback.bookbuildingzbgcInfo(
                    isEdit, inputInfo1, inputInfo2, inputInfo3, "1", inputInfo5,
                    inputInfo6, inputInfo7, inputInfo8, inputInfo9, inputInfo10);
        } else {
            callback.bookbuildingzbgcInfo(
                    isEdit, inputInfo1, inputInfo2, inputInfo3, "Toast.LENGTH_SHORT", inputInfo5,
                    inputInfo6, inputInfo7, inputInfo8, inputInfo9, inputInfo10);
        }
    }

}
