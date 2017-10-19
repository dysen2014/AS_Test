package com.pactera.financialmanager.credit.main.warn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pactera.financialmanager.ui.ParentFragment;

import butterknife.ButterKnife;

/**
 * Created by dysen on 2017/8/17.
 */

public class CustomerApprovalWarnFragment extends ParentFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		View view = inflater.inflate(R.layout.fragment_matured_warn, container, false);
//		ButterKnife.bind(this, view);

//        sendRequest(index1);
		return null;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}
}
