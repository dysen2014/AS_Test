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
import android.widget.Spinner;

import com.dysen.common_res.common.base.ParentFragment;
import com.dysen.common_res.common.utils.ParamUtils;
import com.pactera.financialmanager.R;

/**
 * Created by lenovo on 2017/7/27.
 */

public class QueryCustomerFragment extends ParentFragment implements View.OnClickListener {
	private Context mContext;
	private Spinner customerType;
	private EditText customerName;
	private Spinner cardType;
	private EditText cardNumber;
	private EditText telephoneNumber;
	private Spinner Level;
	private Button submit;
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
		mContext = getActivity();
		customerType = (Spinner) v.findViewById(R.id.customerType);
		customerName = (EditText) v.findViewById(R.id.customerName);
		cardType = (Spinner) v.findViewById(R.id.cardType);
		cardNumber = (EditText) v.findViewById(R.id.cardNumber);
		telephoneNumber = (EditText) v.findViewById(R.id.telephoneNumber);
		Level = (Spinner) v.findViewById(R.id.level);
		submit = (Button) v.findViewById(R.id.submit);
		ArrayAdapter<CharSequence> custtype =
				ArrayAdapter.createFromResource(mContext, R.array.customer_type, android.R.layout.simple_spinner_item);
		custtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		customerType.setAdapter(custtype);
		ArrayAdapter<CharSequence> cardtype =
				ArrayAdapter.createFromResource(mContext, R.array.card_type, android.R.layout.simple_spinner_item);
		cardtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cardType.setAdapter(cardtype);
		ArrayAdapter<CharSequence> level =
				ArrayAdapter.createFromResource(mContext, R.array.level, android.R.layout.simple_spinner_item);
		level.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Level.setAdapter(level);
		submit.setOnClickListener(this);
		return v;
	}

	@Override
	public void onClick(View view) {
		customerType_name = (String) customerType.getSelectedItem();
		customerNamer = customerName.getText().toString();
		cardNumbers = cardNumber.getText().toString();
		telephoneNumbers = telephoneNumber.getText().toString();
		Level_name = (String) Level.getSelectedItem();
		Log.i("cardNumbers", cardNumbers);
		Arrays arrays = new Arrays();
		cardType_name = (String) cardType.getSelectedItem();
		String[] customer_type = getResources().getStringArray(R.array.customer_type);
		customerType_value = arrays.customerType(customerType_name, customer_type);
		String[] card_type = getResources().getStringArray(R.array.card_type);
		cardType_value = arrays.cardType(cardType_name, card_type);
		String[] level_type = getResources().getStringArray(R.array.level);
		Level_value = arrays.getLevel(Level_name, level_type);
		Log.i("tag1", customerType_value);
		Intent intent = new Intent(mContext, QueryList.class);
		Bundle bundle = new Bundle();
		bundle.putString("CustomerType", customerType_value);
		bundle.putString("CustomerName", customerNamer);
		bundle.putString("CertTypeName", cardType_value);
		bundle.putString("CertID", cardNumbers);
		bundle.putString("MobilePhone", telephoneNumbers);
		bundle.putString("IrscreditLevel", Level_value);
		bundle.putString("UserID", ParamUtils.UserId);
		intent.putExtras(bundle);
		/*
         * 1 是请求的一个标志  requestCode
        */
		startActivity(intent);
	}
}
