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

import com.pactera.financialmanager.credit.common.bean.CommonBean;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.R;

import java.util.List;

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
	private DuebillInfoFragment duebillInfoFragment;//借据
	private BusinessCountractFragment businessCountractFragment;//合同
	private InfoFragment infoFragment;//客户

	public static List<ListBusinessList> listData;
	public static List<CommonBean.RelevanceBean> relevanceBeanList;
	public static int index;
	private int tabIndex;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.business_details);
		ButterKnife.bind(this);

		initView();

		setFragmentAll(crmDuebillInfo);
	}


	private void initView() {

		txtTitle.setText(getString(R.string.business_details));
		fragmentManager = getSupportFragmentManager();

		backActivity(this, layBack);
	}

	@OnClick({R.id.crmDuebillInfo, R.id.crmBusinessCountract, R.id.crmCustomerInfo})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.crmDuebillInfo:
				tabIndex = 0;
				break;
			case R.id.crmBusinessCountract:
				tabIndex = 1;
				break;
			case R.id.crmCustomerInfo:
				break;
		}

		setFragmentAll(view);
		showFragment(view);
	}

	private void showFragment(View view) {
//		view.setSelected(true);
//		setSelectorItemColor(view, R.drawable.bg_selector);
		crmDuebillInfo.setTextColor(getResources().getColor(R.color.gray));
		crmCustomerInfo.setTextColor(getResources().getColor(R.color.gray));
		crmBusinessCountract.setTextColor(getResources().getColor(R.color.gray));
		view.setBackgroundResource(R.drawable.tab_bg_selector);
		((RadioButton)view).setTextColor(getResources().getColor(R.color.common_tab_bg));
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
				QueryDetails.setData(null, listData, relevanceBeanList, index);
				intent.putExtra("type", "approval_business");
				startActivity(intent);
		}

		transaction.commitAllowingStateLoss();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (tabIndex == 0) {
			showFragment(crmDuebillInfo);
			setFragmentAll(crmDuebillInfo);
			crmDuebillInfo.setChecked(true);
		} else {
			showFragment(crmBusinessCountract);
			setFragmentAll(crmBusinessCountract);
			crmBusinessCountract.setChecked(true);
		}
	}

	public static void setData(List<ListBusinessList> list, int i) {
		listData = list;
		index = i ;
	}

	public static void setRelevanceBeanData(List<CommonBean.RelevanceBean> list, int i) {
		relevanceBeanList = list;
		index = i ;
	}
}
