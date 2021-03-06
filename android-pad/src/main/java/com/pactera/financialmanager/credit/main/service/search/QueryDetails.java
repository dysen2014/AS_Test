package com.pactera.financialmanager.credit.main.service.search;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**	查询详情
 * Created by lenovo on 2017/7/17.
 */

public class QueryDetails extends ParentActivity {

	@Bind(R.id.txt_back)
	TextView txtBack;
	@Bind(R.id.lay_back)
	LinearLayout layBack;
	@Bind(R.id.txt_title)
	TextView txtTitle;
	@Bind(R.id.txt_)
	TextView txt;
	@Bind(R.id.crmCustomerInfo)
	RadioButton crmCustomerInfo;
	@Bind(R.id.keyman)
	RadioButton keyman;
	@Bind(R.id.loanafter)
	RadioButton loanafter;
	@Bind(R.id.crmCreditProve)
	RadioButton crmCreditProve;
	@Bind(R.id.crmEntOwner)
	RadioButton crmEntOwner;
	@Bind(R.id.frameLayout_tab_details)
	FrameLayout frameLayoutTabDetails;

	private FragmentManager fragmentManager;
	private InfoFragment infoFragment;//基本信息
	private KeyPersonFragment keyPersonFragment;//关键人
	private LoanAfterFragment loanAfterFragment;//未结清授信业务
	private CreditProveFragment creditProveFragment;//贷款证(农户)

	public static int index;
	public static String type;
	public static List<ListPerson> listCustomer = new ArrayList<>();
	public static List<ListBusinessList> listBusiness= new ArrayList<>();
	public static List<CommonBean.RelevanceBean> relevanceBeanList = new ArrayList<>();

	public static LinearLayout layBacks;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_details);
		ButterKnife.bind(this);
		layBacks = layBack;
		layBacks.setEnabled(false);
		initView();
		setFragmentAll(crmCustomerInfo);
	}

	private void initView() {

		txtBack.setText(getString(R.string.business_details));
		initTitle(this, "", false,"");
		fragmentManager = getSupportFragmentManager();
		type = getIntent().getStringExtra("type");

		backActivity(this, layBack);
	}

	@OnClick({R.id.crmCustomerInfo, R.id.keyman, R.id.loanafter, R.id.crmCreditProve, R.id.crmEntOwner})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.crmCustomerInfo:
				break;
			case R.id.keyman:
				break;
			case R.id.loanafter:
				break;
			case R.id.crmCreditProve:
				break;
//			case R.id.crmEntOwner:
//				break;
		}

		setFragmentAll(view);
		showFragment(view);
	}

	private void showFragment(View view) {
//		view.setSelected(true);
//		setSelectorItemColor(view, R.drawable.bg_selector);
		crmCreditProve.setTextColor(getResources().getColor(R.color.gray));
		crmCustomerInfo.setTextColor(getResources().getColor(R.color.gray));
		crmEntOwner.setTextColor(getResources().getColor(R.color.gray));
		loanafter.setTextColor(getResources().getColor(R.color.gray));
		keyman.setTextColor(getResources().getColor(R.color.gray));
		view.setBackgroundResource(R.drawable.tab_bg_selector);
		((RadioButton)view).setTextColor(getResources().getColor(R.color.common_tab_bg));
	}

	//先隐藏其他所有的fragment
	private void hideFragments(FragmentTransaction transaction) {

		if (infoFragment != null)
			transaction.hide(infoFragment);

		if (keyPersonFragment != null)
			transaction.hide(keyPersonFragment);

		if (loanAfterFragment != null)
			transaction.hide(loanAfterFragment);

		if (creditProveFragment != null)
			transaction.hide(creditProveFragment);
	}

	//all
	private void setFragmentAll(View view) {

		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);

		switch (view.getId()) {
			case R.id.crmCustomerInfo:
				if (infoFragment == null) {
					infoFragment = new InfoFragment();
					transaction.add(R.id.frameLayout_tab_details, infoFragment);
				} else
					transaction.show(infoFragment);
				break;
			case R.id.keyman:
				if (keyPersonFragment == null) {
					keyPersonFragment = new KeyPersonFragment();
					transaction.add(R.id.frameLayout_tab_details, keyPersonFragment);
				} else
					transaction.show(keyPersonFragment);
				break;
			case R.id.loanafter:
				if (loanAfterFragment == null) {
					loanAfterFragment = new LoanAfterFragment();
					transaction.add(R.id.frameLayout_tab_details, loanAfterFragment);
				} else
					transaction.show(loanAfterFragment);
				break;
			case R.id.crmCreditProve:
				if (creditProveFragment == null) {
					creditProveFragment = new CreditProveFragment();
					transaction.add(R.id.frameLayout_tab_details, creditProveFragment);
				} else
					transaction.show(creditProveFragment);
				break;
		}

		transaction.commitAllowingStateLoss();
	}

	public static void setData(List<ListPerson> list, List<ListBusinessList> list2,
							   List<CommonBean.RelevanceBean> list3, int
			i) {
		listCustomer = list;
		listBusiness = list2;
		relevanceBeanList = list3;
		index = i;
	}
}
