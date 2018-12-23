package com.pactera.financialmanager.ui.fragmentbookbuilding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.callback.WorkPlaceItemChange;
import com.pactera.financialmanager.ui.ParentFragment;

public class FragmentBookBuildingOtherinfo extends ParentFragment implements OnClickListener {
	private EditText etInfo1,etInfo2,etInfo3,etInfo4,etInfo6,etInfo5,etInfo7,etInfo8,etInfo9,etInfo10;
	private Button btnOtherinfo;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_otherinfocom, null);
		setupView(view);
		setListener();
		return view;
	}

	private void setListener() {
		btnOtherinfo.setOnClickListener(this);
	}

	private void setupView(View view) {
		etInfo1=(EditText)view.findViewById(R.id.et_info1);
		etInfo9=(EditText)view.findViewById(R.id.et_info9);
		etInfo8=(EditText)view.findViewById(R.id.et_info8);
		etInfo7=(EditText)view.findViewById(R.id.et_info7);
		etInfo6=(EditText)view.findViewById(R.id.et_info6);
		etInfo5=(EditText)view.findViewById(R.id.et_info5);
		etInfo4=(EditText)view.findViewById(R.id.et_info4);
		etInfo3=(EditText)view.findViewById(R.id.et_info3);
		etInfo2=(EditText)view.findViewById(R.id.et_info2);
		etInfo10=(EditText)view.findViewById(R.id.et_info10);
		btnOtherinfo=(Button)view.findViewById(R.id.btn_otherinfonext);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_otherinfonext:
			if(getActivity() instanceof WorkPlaceItemChange){
				((WorkPlaceItemChange)getActivity()).workPlacestyleChange(2);
			}
			break;

		default:
			break;
		}
	}
}
