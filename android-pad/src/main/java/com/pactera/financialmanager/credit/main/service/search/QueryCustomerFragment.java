package com.pactera.financialmanager.credit.main.service.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.dysen.common_res.common.utils.LogUtils;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.CitySelect;
import com.pactera.financialmanager.ui.ParentFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2017/7/27.
 */

public class QueryCustomerFragment extends ParentFragment {
    @Bind(R.id.iv_ocr)
    ImageView ivOcr;
    @Bind(R.id.customerType)
    Spinner customerType;
    @Bind(R.id.customerName)
    EditText customerName;
    @Bind(R.id.cardType)
    Spinner cardType;
    @Bind(R.id.cardNumber)
    EditText cardNumber;
    @Bind(R.id.btn_owned_outlets)
    Button btnOwnedOutlets;
    @Bind(R.id.ll_owned_outlets)
    LinearLayout llOwnedOutlets;
    @Bind(R.id.owned_outlets)
    Spinner ownedOutlets;
    @Bind(R.id.telephoneNumber)
    EditText telephoneNumber;
    @Bind(R.id.level)
    Spinner level;
    @Bind(R.id.submit)
    Button submit;
    private Context mContext;
    private String customerType_name;
    private String customerType_value;
    private String customerNamer;
    private String cardNumbers;
    private String telephoneNumbers;
    private String cardType_name;
    private String cardType_value;
    private String Level_name;
    private String Level_value;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.query_customer, container, false);

        ButterKnife.bind(this, v);
        initView();
        return v;
    }

    private void initView() {
        mContext = getActivity();
        ArrayAdapter<CharSequence> custtype =
                ArrayAdapter.createFromResource(mContext, R.array.customer_associated_type, android.R.layout.simple_spinner_item);
        custtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerType.setAdapter(custtype);
        ArrayAdapter<CharSequence> cardtype =
                ArrayAdapter.createFromResource(mContext, R.array.card_type, android.R.layout.simple_spinner_item);
        cardtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardType.setAdapter(cardtype);
        ArrayAdapter<CharSequence> level1 =
                ArrayAdapter.createFromResource(mContext, R.array.level, android.R.layout.simple_spinner_item);
        level1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        level.setAdapter(level1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.iv_ocr, R.id.submit, R.id.ll_owned_outlets, R.id.btn_owned_outlets})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_ocr:
                openOCR();
//                toast("open ocr");
                break;
            case R.id.submit:
//                toast("chaxun");
                search();
                break;
            case R.id.btn_owned_outlets:
            case R.id.ll_owned_outlets:
                CityPicker(btnOwnedOutlets, new CitySelect.CallBackCitySelect() {
                    @Override
                    public void CallBackInfo(String location, String theLocation) {
                        btnOwnedOutlets.setText(location);
                        LogUtils.d(location + "===" + theLocation);
                    }
                });
//                selectAddress();
                break;
        }
    }

    private void search() {
        customerType_name = (String) customerType.getSelectedItem();
        customerNamer = customerName.getText().toString();
        cardNumbers = cardNumber.getText().toString();
        telephoneNumbers = telephoneNumber.getText().toString();
        Level_name = (String) level.getSelectedItem();
        Log.i("cardNumbers", cardNumbers);
        Arrays arrays = new Arrays();
        cardType_name = (String) cardType.getSelectedItem();
        String[] customer_type = getResources().getStringArray(R.array.customer_associated_type);
        customerType_value = arrays.customerType(customerType_name, customer_type);
        String[] card_type = getResources().getStringArray(R.array.card_type);
        cardType_value = arrays.cardType(cardType_name, card_type);
        if (cardType_value.equals("0")){
            cardType_value = "";
        }
        String[] level_type = getResources().getStringArray(R.array.level);
        Level_value = arrays.getLevel(Level_name, level_type);
        Log.i("tag1", customerType_value);
        Intent intent = new Intent(mContext, QueryList.class);
        Bundle bundle = new Bundle();
        bundle.putString("transType", "queryCustomer");
        bundle.putString("CustomerType", customerType_value);
        bundle.putString("CustomerName", customerNamer);
        bundle.putString("CertTypeName", cardType_value);
        bundle.putString("CertID", cardNumbers);
        bundle.putString("MobilePhone", telephoneNumbers);
        bundle.putString("IrscreditLevel", Level_value);
        intent.putExtras(bundle);
        /*
         * 1 是请求的一个标志  requestCode
        */
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        onAtyResult(requestCode, resultCode, data, customerName, cardNumber);
    }
}
