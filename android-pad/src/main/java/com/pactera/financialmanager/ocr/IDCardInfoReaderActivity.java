package com.pactera.financialmanager.ocr;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.pactera.financialmanager.R;
import com.ym.idcard.reg.IDCard;
import com.ym.idcard.reg.OcrEngine;


public class IDCardInfoReaderActivity extends Activity implements View.OnClickListener {

    private Button btn_getphoto;
    private Button btnBack;
    private Button btnOK;
    private EditText edit_name;
    private EditText edit_sex;
    private EditText edit_address;
    private EditText edit_create;
    private EditText edit_date;
    private EditText edit_id;
    private EditText edit_limit_date_end;
    private EditText edit_limit_date_start;
    private EditText edit_type;

    private OCRAnalysis ocrAnalysis = null;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ocrAnalysis.toastInfo(msg);

            // 解析对象
            if(msg.what == OcrEngine.RECOG_OK) {
                IDCard idCard = (IDCard) msg.obj;
                if (idCard != null) {
                    setUIValue(idCard);
                    OCRAnalysis.idCard = idCard;  // 保存解析成功的值
                }
            }
        }
    };

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.read_idcard_info_new);
        initUI();

        ocrAnalysis = new OCRAnalysis(this);
        ocrAnalysis.readIdCardInfo(handler);
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        this.btnBack = (Button) findViewById(R.id.btn_back);
        this.edit_name = ((EditText) findViewById(R.id.edit_name));
        this.edit_id = ((EditText) findViewById(R.id.edit_id));
        this.edit_sex = ((EditText) findViewById(R.id.edit_sex));
        this.edit_type = ((EditText) findViewById(R.id.edit_type));
        this.edit_date = ((EditText) findViewById(R.id.edit_date));
        this.edit_address = ((EditText) findViewById(R.id.edit_address));
        this.edit_create = ((EditText) findViewById(R.id.edit_create));
        this.edit_limit_date_start = ((EditText) findViewById(R.id.edit_limit_date_start));
        this.edit_limit_date_end = ((EditText) findViewById(R.id.edit_limit_date_end));
        this.btn_getphoto = ((Button) findViewById(R.id.btn_get_idcard_photo));
        this.btnOK = (Button) findViewById(R.id.btn_ok);
        this.btn_getphoto.setOnClickListener(this);
        this.btnBack.setOnClickListener(this);
        this.btnOK.setOnClickListener(this);
    }

    /**
     * 设置页面上的值
     * @param idCard
     */
    private void setUIValue(IDCard idCard){
        if(idCard == null){
            return;
        }

        if (idCard.getName() != null) {
            edit_name.setText(idCard.getName());
        }
        if (idCard.getCardNo() != null) {
            edit_id.setText(idCard.getCardNo());
        }
        if (idCard.getSex() != null) {
            edit_sex.setText(idCard.getSex());
        }
        if (idCard.getBirth() != null) {
            edit_date.setText(idCard.getBirth());
        }
        if (idCard.getAddress() != null) {
            edit_address.setText(idCard.getAddress());
        }
        if (idCard.getEthnicity() != null) {
            edit_type.setText(idCard.getEthnicity());
        }
        if (idCard.getAuthority() != null) {
            edit_create.setText(idCard.getAuthority());
        }
        if ((idCard.getPeriod() != null) && (!idCard.getPeriod().trim().equals(""))
                && (idCard.getPeriod().contains("-"))) {
            String dateStart = idCard.getPeriod().substring(0, idCard.getPeriod().indexOf("-"));
            String dateEnd = idCard.getPeriod().substring(1 + idCard.getPeriod().indexOf("-"), idCard.getPeriod().length());
            edit_limit_date_start.setText(dateStart);
            edit_limit_date_end.setText(dateEnd);
        }
    }


    public void onClick(View v) {
        if (v.getId() == R.id.btn_get_idcard_photo) {
            ocrAnalysis.takePhoto("IDCardinfoReaderActivity");
        }else if(v.getId() == R.id.btn_back) {
            OCRAnalysis.idCard = null;
        }
        finish();
    }


}