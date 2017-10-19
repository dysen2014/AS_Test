package com.pactera.financialmanager.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.callback.WorkPlaceItemChange;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.util.LimitsUtil;

/**
 * 我的业绩
 * @author dg
 */
public class FragmentMyreport extends ParentFragment implements OnClickListener {
	
	private RadioButton rbtnWarnPerson,rbtnWarnCommon;
	private RadioButton rbtnTab00, rbtnTab01, rbtnTab02, rbtnTab03, rbtnTab04, rbtnTab05;
	
	private boolean isForperson = true;
	private int selectTabIndex = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_myreport, null);

		findView(view);
		bindOnclick();
		hiddentRadiobtn();
		workMyreportPersonChange(selectTabIndex);
		return view;
	}
	
	private void findView(View view){
		rbtnWarnPerson = (RadioButton) view.findViewById(R.id.rbtn_warnperson);
		rbtnWarnCommon = (RadioButton) view.findViewById(R.id.rbtn_warncommon);
		rbtnTab00 = (RadioButton) view.findViewById(R.id.rbtn_person_tab00);
		rbtnTab01 = (RadioButton) view.findViewById(R.id.rbtn_person_tab01);
		rbtnTab02 = (RadioButton) view.findViewById(R.id.rbtn_person_tab02);
		rbtnTab03 = (RadioButton) view.findViewById(R.id.rbtn_person_tab03);
		rbtnTab04 = (RadioButton) view.findViewById(R.id.rbtn_person_tab04);
		rbtnTab05 = (RadioButton) view.findViewById(R.id.rbtn_person_tab05);
	}
	
	private void bindOnclick(){
		rbtnWarnPerson.setOnClickListener(this);
		rbtnWarnCommon.setOnClickListener(this);
		rbtnTab00.setOnClickListener(this);
		rbtnTab01.setOnClickListener(this);
		rbtnTab02.setOnClickListener(this);
		rbtnTab03.setOnClickListener(this);
		rbtnTab04.setOnClickListener(this);
		rbtnTab05.setOnClickListener(this);
	}

	/**
	 * 根据权限隐藏
	 */
	private void hiddentRadiobtn(){
		// 管理岗-金融资产权限
		boolean isRight = LimitsUtil.checkUserLimit(getActivity(), LimitsUtil.T0510, false);
		if(isRight){
			rbtnTab00.setVisibility(View.VISIBLE);
			selectTabIndex = 0;
		}else{
			rbtnTab00.setVisibility(View.GONE);
		}
		// 客户经理-金融资产权限
		isRight = LimitsUtil.checkUserLimit(getActivity(), LimitsUtil.T0509, false);
		if(isRight){
			rbtnTab01.setVisibility(View.VISIBLE);
			selectTabIndex = 1;
		}else{
			rbtnTab01.setVisibility(View.GONE);
		}
		// 当年贷款回收情况
		isRight = LimitsUtil.checkUserLimit(getActivity(), LimitsUtil.T0511, false);
		if(isRight){
			rbtnTab05.setVisibility(View.VISIBLE);
		}else{
			rbtnTab05.setVisibility(View.INVISIBLE);
		}

		// 如果第一项隐藏，则第二项显示选中状态
		if(rbtnTab00.getVisibility() == View.GONE ){
			// 如果第一二项隐藏，则第三项显示选中状态
			if(rbtnTab01.getVisibility() == View.GONE){
				rbtnTab02.setChecked(true);
				selectTabIndex = 2;
			}else {
				rbtnTab01.setChecked(true);
			}
		}


	}

	/**
	 * 选择选项卡
	 * @param index
	 */
	private void workMyreportPersonChange(int index){
		if(getActivity() instanceof WorkPlaceItemChange){
			((WorkPlaceItemChange)getActivity()).workMyreportPersonChange(index, isForperson);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 对个人业务
		case R.id.rbtn_warnperson:
			isForperson = true;
			workMyreportPersonChange(selectTabIndex);
			break;

		// 对公业务
		case R.id.rbtn_warncommon:
			isForperson = false;
			workMyreportPersonChange(selectTabIndex);
			break;

		// 管理岗-金融资产
		case R.id.rbtn_person_tab00:
			selectTabIndex = 0;
			workMyreportPersonChange(0);
			break;
		// 客户经理-金融资产
		case R.id.rbtn_person_tab01:
			selectTabIndex = 1;
			workMyreportPersonChange(1);
			break;
		// 存款趋势图
		case R.id.rbtn_person_tab02:
			selectTabIndex = 2;
			workMyreportPersonChange(2);
			break;
		// 渠道签约情况
		case R.id.rbtn_person_tab03:
			selectTabIndex = 3;
			workMyreportPersonChange(3);
			break;
		// 客户价值评级占比
		case R.id.rbtn_person_tab04:
			selectTabIndex = 4;
			workMyreportPersonChange(4);
			break;
		// 当年到期贷款收回
		case R.id.rbtn_person_tab05:
			selectTabIndex = 5;
			workMyreportPersonChange(5);
			break;
		default:
			break;
		}
	}
	
}
