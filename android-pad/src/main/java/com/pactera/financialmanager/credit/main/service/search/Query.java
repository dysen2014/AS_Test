package com.pactera.financialmanager.credit.main.service.search;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.dysen.common_res.common.base.ParentActivity;
import com.pactera.financialmanager.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Query extends ParentActivity {
	@Bind(R.id.rbtn_0)
	RadioButton rbtn0;
	@Bind(R.id.rbtn_1)
	RadioButton rbtn1;
	@Bind(R.id.fragment_query)
	FrameLayout fragmentQuery;

	private FragmentManager fragmentManager;
	QueryCustomerFragment queryCustomerFragment;
	QueryBusinessFragment queryBusinessFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query);
		ButterKnife.bind(this);

		initView();
		setFragmentAll(rbtn0);
	}

	private void initView() {

		rbtn0.setText(getString(R.string.s_customer));
		rbtn1.setText(getString(R.string.s_bussiness));
		fragmentManager = getSupportFragmentManager();
	}

	@OnClick({R.id.rbtn_0, R.id.rbtn_1})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.rbtn_0:
				break;
			case R.id.rbtn_1:
				break;
		}
		setFragmentAll(view);
		showFragment(view);
	}

	private void showFragment(View view) {

		rbtn0.setTextColor(getResources().getColor(R.color.white));
		rbtn1.setTextColor(getResources().getColor(R.color.white));
		if (view.getId() == R.id.rbtn_0)
			view.setBackgroundResource(R.drawable.rbt_bg_selector_left);
		else
			view.setBackgroundResource(R.drawable.rbt_bg_selector_right);
		((RadioButton)view).setTextColor(getResources().getColor(R.color.common_tab_bg));
	}

	//先隐藏其他所有的fragment
	private void hideFragments(FragmentTransaction transaction) {

		if (queryBusinessFragment != null)
			transaction.hide(queryBusinessFragment);

		if (queryCustomerFragment != null)
			transaction.hide(queryCustomerFragment);
	}

	//all
	private void setFragmentAll(View view) {
		showFragment(view);
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);

		switch (view.getId()) {
			case R.id.rbtn_0:
				if (queryCustomerFragment == null) {
					queryCustomerFragment = new QueryCustomerFragment();
					transaction.add(R.id.fragment_query, queryCustomerFragment);
				} else
					transaction.show(queryCustomerFragment);
				break;
			case R.id.rbtn_1:
				if (queryBusinessFragment == null) {
					queryBusinessFragment = new QueryBusinessFragment();
					transaction.add(R.id.fragment_query, queryBusinessFragment);
				} else
					transaction.show(queryBusinessFragment);
				break;
		}
		transaction.commitAllowingStateLoss();
	}
}
