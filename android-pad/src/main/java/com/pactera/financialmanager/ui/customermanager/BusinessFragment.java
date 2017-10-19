package com.pactera.financialmanager.ui.customermanager;

import org.json.JSONObject;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.model.BusinessInfo;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.Tool;

/**
 * 个人建档下业务往来fragment
 * 
 * @author JAY
 * 
 *         备注：隐藏了我行业务往来
 */
public class BusinessFragment extends ParentFragment implements OnClickListener {
	/**
	 * 资金结算偏好、我行业务往来、他行业务往来
	 */
	RelativeLayout[] businessFragment_rl = new RelativeLayout[3];
	ImageView[] businessFragment_img = new ImageView[3];
	TextView[] businessFragment_tv = new TextView[3];

	public static int[] IndexBusiness = { isFalse, isFalse, isFalse, isFalse,
			isFalse, isFalse };// 资金结算偏好对应选项（0未选中，1为选中）
	public static int[] IndexMybank = { isFalse, isFalse, isFalse, isFalse,
			isFalse, isFalse, isFalse, isFalse, isFalse, isFalse };// 我行业务（0未选中，1为选中）

	FragmentManager fragmentManager;
	SettlementFragment settlementFragment;// 资金结算偏好
	MyBankFragment myBankFragment;// 我行业务往来
	OtherBankFragment otherBankFragment;// 他行业务往来

	public static final int NextIndex = 1;
	private static final int ConIndex = 2;
	private static final int getDataIndex = 3;
	private HConnection HCon;
	public static BusinessFragment theBusiness;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_archiving_business, null);
		setView(view);
		setListener();
		getData();
		fragmentManager = getFragmentManager();
		setFragmentSettlement();

		return view;
	}

	/**
	 * 显示结算偏好
	 */
	private void setFragmentSettlement() {
		// TODO Auto-generated method stub
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		if (settlementFragment == null) {
			settlementFragment = new SettlementFragment();
			transaction.add(R.id.businessframe_fragment, settlementFragment);
		} else {
			transaction.show(settlementFragment);
		}
		transaction.commitAllowingStateLoss();
	}

	/**
	 * 显示我行业务往来
	 */
	private void setFragmentMyBank() {
		// TODO Auto-generated method stub
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		if (myBankFragment == null) {
			myBankFragment = new MyBankFragment();
			transaction.add(R.id.businessframe_fragment, myBankFragment);
		} else {
			transaction.show(myBankFragment);
		}
		transaction.commitAllowingStateLoss();
	}

	/**
	 * 显示他行业务往来
	 */
	private void setFragmentOtherBank() {
		// TODO Auto-generated method stub
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		if (otherBankFragment == null) {
			otherBankFragment = new OtherBankFragment();
			transaction.add(R.id.businessframe_fragment, otherBankFragment);
		} else {
			transaction.show(otherBankFragment);
		}
		transaction.commitAllowingStateLoss();
	}

	private void hideFragments(FragmentTransaction transaction) {
		// TODO Auto-generated method stub
		if (settlementFragment != null) {
			transaction.hide(settlementFragment);
		}
		if (myBankFragment != null) {
			transaction.hide(myBankFragment);
		}
		if (otherBankFragment != null) {
			transaction.hide(otherBankFragment);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.businessframe_1_rl:
			setChoose(0);
			setFragmentSettlement();
			break;
		case R.id.businessframe_2_rl:
			setChoose(1);
			setFragmentMyBank();
			break;
		case R.id.businessframe_3_rl:
			setChoose(2);
			setFragmentOtherBank();
			break;
		}
	}

	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);

		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			break;
		case NextIndex:
			sentData();
			break;
		case ConIndex:
		case getDataIndex:
			HResponse res = HCon
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			JSONObject tmpJsonObject;
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			tmpJsonObject = res.dataJsonObject;
			readJson(tmpJsonObject, connectionIndex);
			break;
		}
	}

	private void readJson(JSONObject tmpJsonObject, int connectionIndex) {
		// TODO Auto-generated method stub
		String retCode = Tool.getJsonValue(tmpJsonObject, "retCode");

		if (retCode.equals("0000")) {
			if (connectionIndex == ConIndex) {
				Message msg = ((ParentActivity) getActivity()).handler
						.obtainMessage();
				Bundle data = new Bundle();
				data.putString("value", "" + PersonArchiving.BusinessIndex);
				msg.setData(data);
				((ParentActivity) getActivity()).handler.sendMessage(msg);
			} else if (connectionIndex == getDataIndex) {

				// String MONEY_SETTLE = Tool.getJsonValue(tmpJsonObject,
				// "REL_BUSI_INFO");
				String REL_BUSI_INFO = Tool.getJsonValue(tmpJsonObject,
						"MONEY_SETTLE");
				String[] theBusinessStr = REL_BUSI_INFO.split(",");
				// String[] theMybankStr = MONEY_SETTLE.split(",");

				for (int i = 0; i < theBusinessStr.length; i++) {
					String theIndex = theBusinessStr[i];
					if (!TextUtils.isEmpty(theIndex) && Tool.isNumber(theIndex)) {

						int selectIndex = Tool.parseInt(theBusinessStr[i]) - 1;
						Log.i("1111111", "selectIndex:" + selectIndex);
						if (selectIndex < IndexBusiness.length) {
							IndexBusiness[selectIndex] = isTrue;
						}

					}
				}

				// for (int i = 0; i < theMybankStr.length; i++) {
				// String theIndex = theMybankStr[i];
				// if (!TextUtils.isEmpty(theIndex) && Tool.isNumber(theIndex))
				// {
				//
				// int selectIndex = Tool.parseInt(theIndex);
				// IndexMybank[selectIndex] = isTrue;
				//
				// }
				//
				// }

				Message msg = SettlementFragment.settlement.handler
						.obtainMessage();
				Bundle data = new Bundle();
				data.putString("value", "" + SettlementFragment.IndexView);
				msg.setData(data);
				((ParentFragment) SettlementFragment.settlement).handler
						.sendMessage(msg);
			}
		}
	}

	private void sentData() {
		String BusinessStr = "";
		String MybankStr = "";

		for (int i = 0; i < IndexBusiness.length; i++) {
			if (IndexBusiness[i] == isTrue) {
				if (TextUtils.isEmpty(BusinessStr)) {
					BusinessStr = "" + (i + 1);
				} else {
					BusinessStr = BusinessStr + "," + (i + 1);
				}

			}
		}

		for (int i = 0; i < IndexMybank.length; i++) {
			if (IndexMybank[i] == isTrue) {
				if (TextUtils.isEmpty(MybankStr)) {
					MybankStr = "" + (i + 1);
				} else {
					MybankStr = MybankStr + "," + (i + 1);
				}

			}
		}

		// Toast.makeText(getActivity(),
		// "BusinessStr:" + BusinessStr + ",MybankStr:" + MybankStr,
		// Toast.LENGTH_SHORT).show();
		BusinessInfo theBusiness = new BusinessInfo();
		theBusiness.custid = PersonArchiving.custID;
		theBusiness.REL_BUSI_INFO = MybankStr;
		theBusiness.MONEY_SETTLE = BusinessStr;
		theBusiness.type = "1";

		HCon = RequestInfo(BusinessFragment.this, Constants.requestType.Update,
				InterfaceInfo.Business_Update_Get, theBusiness.toString(),
				ConIndex);

	}

	private void getData() {
		// TODO Auto-generated method stub
		BusinessInfo theBusiness = new BusinessInfo();
		theBusiness.custid = PersonArchiving.custID;
		theBusiness.type = "2";
		HCon = RequestInfo(BusinessFragment.this, Constants.requestType.Update,
				InterfaceInfo.Business_Update_Get, theBusiness.toString(),
				getDataIndex);
	}

	private void setListener() {
		// TODO Auto-generated method stub
		businessFragment_rl[0].setOnClickListener(this);
		businessFragment_rl[1].setOnClickListener(this);
		businessFragment_rl[2].setOnClickListener(this);
	}

	private void setView(View view) {
		// TODO Auto-generated method stub
		businessFragment_rl[0] = (RelativeLayout) view
				.findViewById(R.id.businessframe_1_rl);
		businessFragment_rl[1] = (RelativeLayout) view
				.findViewById(R.id.businessframe_2_rl);
		businessFragment_rl[2] = (RelativeLayout) view
				.findViewById(R.id.businessframe_3_rl);
		businessFragment_img[0] = (ImageView) view
				.findViewById(R.id.businessframe_1_img);
		businessFragment_img[1] = (ImageView) view
				.findViewById(R.id.businessframe_2_img);
		businessFragment_img[2] = (ImageView) view
				.findViewById(R.id.businessframe_3_img);
		businessFragment_tv[0] = (TextView) view
				.findViewById(R.id.businessframe_1_tv);
		businessFragment_tv[1] = (TextView) view
				.findViewById(R.id.businessframe_2_tv);
		businessFragment_tv[2] = (TextView) view
				.findViewById(R.id.businessframe_3_tv);
		theBusiness = this;
	}

	// 选择设置
	public void setChoose(int index) {

		for (int i = 0; i < businessFragment_tv.length; i++) {
			if (i == index) {
				if (index == businessFragment_tv.length - 1) {
					businessFragment_img[i]
							.setBackgroundResource(R.drawable.itembg5);
				} else if (i == 0) {
					businessFragment_img[i]
							.setBackgroundResource(R.drawable.itembg4444);
				} else {
					businessFragment_img[i].setBackgroundColor(this
							.getResources()
							.getColor(R.color.nowworkplacewordbg));
				}
				businessFragment_tv[i].setTextColor(this.getResources()
						.getColor(R.color.white));
			} else {
				businessFragment_img[i].setBackgroundColor(getResources()
						.getColor(R.color.transparent));
				businessFragment_tv[i].setTextColor(this.getResources()
						.getColor(R.color.nowworkplacewordbg));
			}
		}
	}

}