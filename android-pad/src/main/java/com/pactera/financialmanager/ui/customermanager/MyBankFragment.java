package com.pactera.financialmanager.ui.customermanager;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentFragment;

/**
 * 我行业务往来fragment
 */

public class MyBankFragment extends ParentFragment implements OnClickListener {
	Button mybank_nextbtn;
	LinearLayout[] business_myBank_ll = new LinearLayout[10];
	ImageView[] business_myBank_img = new ImageView[10];
	int[] mybankLL = { R.id.business_mybank_ll_1, R.id.business_mybank_ll_2,
			R.id.business_mybank_ll_3, R.id.business_mybank_ll_4,
			R.id.business_mybank_ll_5, R.id.business_mybank_ll_6,
			R.id.business_mybank_ll_7, R.id.business_mybank_ll_8,
			R.id.business_mybank_ll_9, R.id.business_mybank_ll_10 };

	int[] mybankImg = { R.id.business_mybank_img_1,
			R.id.business_mybank_img_2, R.id.business_mybank_img_3,
			R.id.business_mybank_img_4, R.id.business_mybank_img_5,
			R.id.business_mybank_img_6, R.id.business_mybank_img_7,
			R.id.business_mybank_img_8, R.id.business_mybank_img_9,
			R.id.business_mybank_img_10 };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_business_mybank, null);
		setView(view);
		setListener();
		return view;
	}

	private void setListener() {
		// TODO Auto-generated method stub
		mybank_nextbtn.setOnClickListener(this);
		for (int i = 0; i < business_myBank_ll.length; i++) {
			business_myBank_ll[i].setOnClickListener(this);

		}
	}

	private void setView(View view) {
		// TODO Auto-generated method stub
		mybank_nextbtn = (Button) view
				.findViewById(R.id.business_mybank_nextbtn);
		
		for (int i = 0; i < business_myBank_ll.length; i++) {
			business_myBank_ll[i] = (LinearLayout) view
					.findViewById(mybankLL[i]);
			business_myBank_img[i] = (ImageView) view
					.findViewById(mybankImg[i]);
		}
		for (int i = 0; i < BusinessFragment.IndexMybank.length; i++) {
			if (BusinessFragment.IndexMybank[i]==isTrue) {
				business_myBank_img[i]
						.setImageResource(R.drawable.business_ok);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.business_mybank_nextbtn:
			Message msg =BusinessFragment.theBusiness.handler.obtainMessage();
			Bundle data = new Bundle();
			data.putString("value", "" + BusinessFragment.NextIndex);
			msg.setData(data);
			BusinessFragment.theBusiness.handler.sendMessage(msg);
			break;
		default:
			break;
		}
		
		for (int i = 0; i < business_myBank_ll.length; i++) {
			if (v.getId()==mybankLL[i]) {
				setSelect(i);
				break;
			}
		}
	}
	
	private void setSelect(int index) {
		// TODO Auto-generated method stub
		if (BusinessFragment.IndexMybank[index] == isTrue) {
			business_myBank_img[index]
					.setImageResource(R.drawable.business_cancel);
			BusinessFragment.IndexMybank[index] = isFalse;
		} else if(BusinessFragment.IndexMybank[index] == isFalse){
			business_myBank_img[index]
					.setImageResource(R.drawable.business_ok);
			BusinessFragment.IndexMybank[index] = isTrue;
		}
	}
}