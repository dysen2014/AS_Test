package com.pactera.financialmanager.credit.main.service.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dysen.common_res.common.base.ParentActivity;
import com.pactera.financialmanager.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**	业务查询详情
 * Created by lenovo on 2017/8/1.
 */

public class BusinessDetails extends ParentActivity {

	@Bind(R.id.txt_back)
	TextView txtBack;
	@Bind(R.id.lay_back)
	LinearLayout layBack;
	@Bind(R.id.txt_title)
	TextView txtTitle;
	@Bind(R.id.txt_)
	TextView txt;
	@Bind(R.id.crmDuebillInfo)
	RadioButton crmDuebillInfo;
	@Bind(R.id.crmBusinessCountract)
	RadioButton crmBusinessCountract;
	@Bind(R.id.crmCustomerInfo)
	RadioButton crmCustomerInfo;
	@Bind(R.id.frameLayout_business_details)
	FrameLayout frameLayoutBusinessDetails;

	private FragmentManager fragmentManager;
	private DuebillInfoFragment duebillInfoFragment;//
	private BusinessCountractFragment businessCountractFragment;//
	private InfoFragment infoFragment;//
	public static Bundle bundle;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.business_details);
		ButterKnife.bind(this);

		initView();

		setFragmentAll(crmDuebillInfo);
	}


	private void initView() {

		txtTitle.setText(getString(R.string.customer_details));
		fragmentManager = getSupportFragmentManager();
		bundle = this.getIntent().getExtras();
	}

	@OnClick({R.id.crmDuebillInfo, R.id.crmBusinessCountract, R.id.crmCustomerInfo})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.crmDuebillInfo:
				break;
			case R.id.crmBusinessCountract:
				break;
			case R.id.crmCustomerInfo:
				break;
		}

		setFragmentAll(view);
		showFragment(view);
	}

	private void showFragment(View view) {
		view.setBackgroundResource(R.drawable.bg_selector);
	}

	//先隐藏其他所有的fragment
	private void hideFragments(FragmentTransaction transaction) {

		if (infoFragment != null)
			transaction.hide(infoFragment);

		if (duebillInfoFragment != null)
			transaction.hide(duebillInfoFragment);

		if (businessCountractFragment != null)
			transaction.hide(businessCountractFragment);

		if (infoFragment != null)
			transaction.hide(infoFragment);
	}

	//all
	private void setFragmentAll(View view) {

		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);

		switch (view.getId()) {
			case R.id.crmDuebillInfo:
				if (duebillInfoFragment == null) {
					duebillInfoFragment = new DuebillInfoFragment();
					transaction.add(R.id.frameLayout_business_details, duebillInfoFragment);
				} else
					transaction.show(duebillInfoFragment);
				break;
			case R.id.crmBusinessCountract:
				if (businessCountractFragment == null) {
					businessCountractFragment = new BusinessCountractFragment();
					transaction.add(R.id.frameLayout_business_details, businessCountractFragment);
				} else
					transaction.show(businessCountractFragment);
				break;
			case R.id.crmCustomerInfo:
//				if (infoFragment == null) {
//					infoFragment = new InfoFragment();
//					transaction.add(R.id.frameLayout_business_details, infoFragment);
//				} else
//					transaction.show(infoFragment);
//				break;
				Intent intent = new Intent(getApplicationContext(), QueryDetails.class);
				intent.putExtras(bundle);
				startActivity(intent);
		}

		transaction.commitAllowingStateLoss();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setFragmentAll(crmDuebillInfo);
	}
}
