package com.pactera.financialmanager.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.adapter.SpinnerAdapter;
import com.pactera.financialmanager.util.NewCatevalue;


/**
 * 申请调配的Dialog
 *
 * @author JAY
 */
public class DeployDialog extends Dialog implements View.OnClickListener {

    public static final int DEFAULT_STYLE = R.style.DialogTheme;
    private Context context;
    private TextView tvDialogTitle;
    private TextView tvContent, tvSpinnerReason;
    private TextView tvUserName, txUserDept, tvDeployTime, txTitleOther;
    private Button btnOK, btnCancel;
    private DialogOnClickListener theListener;
    private String mStrDeloyReasonID = "", mStrDeloyReasonDES = "";
    private EditText mEtxOther;//最少输入10个字
    private String mCustomer, mCustomerDept, mDeployTime, mDeployStatus, mReason, mDes;

    private LinearLayout ll_deploy_time, ll_spinner_reason, ll_spinner_other;
    private boolean isCreateDeployNew;


    public DeployDialog(Context context, DialogOnClickListener theIsDeleteListener, boolean isCreateDeployNew,
                        String cus, String dept, String time, String status, String reason, String des) {
        this(context, DEFAULT_STYLE, theIsDeleteListener);
        this.isCreateDeployNew = isCreateDeployNew;
        this.mCustomer = cus;
        this.mCustomerDept = dept;
        this.mDeployTime = time;
        this.mDeployStatus = status;
        this.mReason = reason;
        this.mDes = des;
    }

    public DeployDialog(Context context, int style, DialogOnClickListener theIsDeleteListener) {
        super(context, style);
        this.context = context;
        this.setCanceledOnTouchOutside(false);
        this.theListener = theIsDeleteListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_deploy);

        findViews();
    }

    private void findViews() {
        ll_deploy_time = (LinearLayout) findViewById(R.id.ll_deploy_time);
        ll_spinner_reason = (LinearLayout) findViewById(R.id.ll_spinner_reason);
        ll_spinner_other = (LinearLayout) findViewById(R.id.ll_spinner_other);
        ll_deploy_time.setVisibility(isCreateDeployNew ? View.GONE : View.VISIBLE);
        tvDialogTitle = (TextView) findViewById(R.id.tv_dialog_title);
        tvContent = (TextView) findViewById(R.id.tv_dialog_msg);
        tvSpinnerReason = (TextView) findViewById(R.id.spinner_apply_reason);
        tvUserName = (TextView) findViewById(R.id.userName);
        txUserDept = (TextView) findViewById(R.id.userDept);
        txTitleOther = (TextView) findViewById(R.id.txt_title_other);
        tvDeployTime = (TextView) findViewById(R.id.deploy_time);
        mEtxOther = (EditText) findViewById(R.id.edit_other);
        btnOK = (Button) findViewById(R.id.btn_ok);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnOK.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        tvSpinnerReason.setOnClickListener(this);


        ll_spinner_other.setVisibility(View.INVISIBLE);
        tvUserName.setText(mCustomer);
        txUserDept.setText(mCustomerDept);
        tvDeployTime.setText(mDeployTime);

        tvSpinnerReason.setText(NewCatevalue.getLabel(context, NewCatevalue.applyForDeploy, mReason));
        if (isCreateDeployNew) {
            txTitleOther.setText("其它");
            tvSpinnerReason.setEnabled(true);
        } else {
            ll_spinner_reason.setVisibility(View.GONE);
            mEtxOther.setEnabled(false);
            tvSpinnerReason.setEnabled(false);
            tvDialogTitle.setText("调配信息");
            txTitleOther.setText("申请理由");
            btnOK.setText("取消调配");
            ll_spinner_other.setVisibility(View.VISIBLE);
            mEtxOther.setText(NewCatevalue.getLabel(context, NewCatevalue.applyForDeploy, mReason));
        }

        if ("99".equals(mReason)) {
            mEtxOther.setText(mDes);
            ll_spinner_other.setVisibility(View.VISIBLE);
        }

    }


    public void close() {
        if (this != null && this.isShowing()) {
            dismiss();
        }
    }

    public void setMsg(String msg) {
        tvContent.setText(msg);
    }

    public void setTitle(String title) {
        tvDialogTitle.setText(title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.spinner_apply_reason:
                SpinnerAdapter.showSelectDialog((Activity) context, NewCatevalue.applyForDeploy, tvSpinnerReason,
                        new SpinnerAdapter.CallBackItemClickListener() {
                            @Override
                            public void CallBackItemClick(String spinnerID) {
                                mStrDeloyReasonID = spinnerID;
                                if ("99".equals(mStrDeloyReasonID)) {
                                    mEtxOther.setEnabled(true);
                                    ll_spinner_other.setVisibility(View.VISIBLE);
                                } else {
                                    ll_spinner_other.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                break;

            case R.id.btn_ok:
                if (theListener != null) {
                    if (mStrDeloyReasonID.equals("99")) {
                        if (TextUtils.isEmpty(mEtxOther.getText().toString().trim()) || mEtxOther.getText().toString().trim().length() < 1) {
                            Toast.makeText(context, "请输入其它申请理由，至少1个字", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(tvSpinnerReason.getText().toString().trim())) {
                            Toast.makeText(context, "请选择申请理由", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    theListener.deleteStatus(true, mStrDeloyReasonID, mEtxOther.getText().toString().trim());
                }
                close();
                break;

            case R.id.btn_cancel:
                if (theListener != null) {
                    theListener.deleteStatus(false, mStrDeloyReasonID, mEtxOther.getText().toString().trim());
                }
                close();
                break;

            default:
                break;
        }
    }


    /**
     * boolean isOK 是否确定
     * boolean isCancelDeloy 是否取消调配
     **/
    public interface DialogOnClickListener {
        public void deleteStatus(boolean isOK, String reasonID, String reasondes);
    }
}
