package com.pactera.financialmanager.credit.main.service.cus_screening;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.baidu.ocr.sdk.model.IDCardResult;
import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.common_res.common.views.ViewUtils;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.credit.common.ocr_new.IDCardInfoReaderActivity;
import com.pactera.financialmanager.credit.main.service.search.Arrays;
import com.pactera.financialmanager.ui.ParentActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 黑灰名单
 */
public class CusScreeningActivity extends ParentActivity {

    @Bind(R.id.txt_back)
    TextView txtBack;
    @Bind(R.id.lay_back)
    LinearLayout layBack;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_)
    TextView txt;
    @Bind(R.id.customerName)
    EditText customerName;
    @Bind(R.id.iv_ocr)
    ImageView ivOcr;
    @Bind(R.id.cardType)
    Spinner cardType;
    @Bind(R.id.cardNumber)
    EditText cardNumber;
    @Bind(R.id.submit)
    Button submit;

    private String cusNamer;
    private String cardNumbers;
    private String cardType_name;
    private String cardType_value;
    private IDCardResult idCard;

    //处理照片的数据
    private void setValue(IDCardResult idCard) {
        this.idCard = idCard;
        if (idCard != null) {
            // 设置页面信息
            String name = idCard.getName().toString();
            String cardNo = idCard.getIdNumber().toString();
            if (TextUtils.isEmpty(name)) {
                name = "";
            }
            if (TextUtils.isEmpty(cardNo)) {
                cardNo = "";
            }
            customerName.setText(name);
            cardNumber.setText(cardNo);

            // 自动查询相对应的值
//                    if (checkQueryValue()) {
//                        loading();
//                        getCusDatas();
//                    }
        } else {
            toast("身份证信息识别失败");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_screening);
        ButterKnife.bind(this);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (idCard != null) {
            customerName.setText(idCard.getName().toString());
            cardNumber.setText(idCard.getIdNumber().toString());
        }
    }

    private void initView() {

        Intent intent = getIntent();
        String Name = intent.getStringExtra("Name");
        String NameInfo = intent.getStringExtra("NameInfo");
        initTitle(this, Name, true,NameInfo);
        txtTitle.setText(getString(R.string.cus_screening));
        backActivity(this, layBack);

        ArrayAdapter<CharSequence> cardtype =
                ArrayAdapter.createFromResource(this, R.array.card_type, android.R.layout
                        .simple_spinner_item);
        cardtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardType.setAdapter(cardtype);
    }

    @OnClick({R.id.iv_ocr, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_ocr:
                openOCR();
                break;
            case R.id.submit:
                screening();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onAtyResult(requestCode, resultCode, data, customerName, cardNumber);
    }

    private void screening() {
        cusNamer = customerName.getText().toString();
        cardNumbers = cardNumber.getText().toString();
        Log.i("cardNumbers", cardNumbers);
        Arrays arrays = new Arrays();
        cardType_name = (String) cardType.getSelectedItem();
        String[] card_type = getResources().getStringArray(R.array.card_type);
        cardType_value = arrays.cardType(cardType_name, card_type);
        LogUtils.d("CustomerName=" + cusNamer + "\tCertTypeName=" + cardType_value + "\tCertID=",
                cardNumbers);
        Intent intent = new Intent(this, ScreeningListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("transType", ViewUtils.getText(txtTitle));
        bundle.putString("CustomerName", cusNamer);
        bundle.putString("CertTypeName", cardType_value);
        bundle.putString("CertID", cardNumbers);
        intent.putExtras(bundle);
        /*
         * 1 是请求的一个标志  requestCode
        */
        startActivity(intent);
    }
}
