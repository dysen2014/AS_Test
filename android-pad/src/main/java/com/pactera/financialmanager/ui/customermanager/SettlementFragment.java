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
import com.pactera.financialmanager.ui.service.HConnection;

/**
 * 
 * 资金结算偏好fragment
 * 
 * @author JAY
 * 
 */
public class SettlementFragment extends ParentFragment implements
		OnClickListener {
	/**
	 * 资金结算偏好
	 */
	LinearLayout[] business_settlement_ll = new LinearLayout[6];
	ImageView[] business_settlement_img = new ImageView[6];
	int[] settlementLL = { R.id.business_settlement_ll_1,
			R.id.business_settlement_ll_2, R.id.business_settlement_ll_3,
			R.id.business_settlement_ll_4, R.id.business_settlement_ll_5,
			R.id.business_settlement_ll_6 };

	int[] settlementImg = { R.id.business_settlement_img_1,
			R.id.business_settlement_img_2, R.id.business_settlement_img_3,
			R.id.business_settlement_img_4, R.id.business_settlement_img_5,
			R.id.business_settlement_img_6 };

	Button settlement_nextbtn;
	public static SettlementFragment settlement;
	public static final int IndexView = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_business_settlement,
				null);
		settlement=this;
		setView(view);
		setListener();
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.business_settlement_nextbtn:
			Message msg = BusinessFragment.theBusiness.handler.obtainMessage();
			Bundle data = new Bundle();
			data.putString("value", "" + BusinessFragment.NextIndex);
			msg.setData(data);
			BusinessFragment.theBusiness.handler.sendMessage(msg);
			break;
		}

		for (int i = 0; i < business_settlement_ll.length; i++) {
			if (v.getId() == settlementLL[i]) {
				setSelect(i);
				break;
			}
		}
	}

	private void setSelect(int index) {
		// TODO Auto-generated method stub
		if (BusinessFragment.IndexBusiness[index] == 1) {
			business_settlement_img[index]
					.setImageResource(R.drawable.business_cancel);
			BusinessFragment.IndexBusiness[index] = 0;
		} else {
			business_settlement_img[index]
					.setImageResource(R.drawable.business_ok);
			BusinessFragment.IndexBusiness[index] = 1;
		}
	}

	private void setListener() {
		// TODO Auto-generated method stub
		for (int i = 0; i < business_settlement_ll.length; i++) {
			business_settlement_ll[i].setOnClickListener(this);
		}
		settlement_nextbtn.setOnClickListener(this);
	}

	private void setView(View view) {

		for (int i = 0; i < business_settlement_ll.length; i++) {
			business_settlement_ll[i] = (LinearLayout) view
					.findViewById(settlementLL[i]);
			business_settlement_img[i] = (ImageView) view
					.findViewById(settlementImg[i]);
		}

		settlement_nextbtn = (Button) view
				.findViewById(R.id.business_settlement_nextbtn);
	}

	//设置显示
	private void setcheck() {
		for (int i = 0; i < BusinessFragment.IndexBusiness.length; i++) {
			if (BusinessFragment.IndexBusiness[i] == isTrue) {
				if (business_settlement_img[i]!=null) {
					business_settlement_img[i]
							.setImageResource(R.drawable.business_ok);
				}
			
			}
		}
	}

	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);

		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			break;
		case IndexView:
			setcheck();
			break;
		}
	}

}