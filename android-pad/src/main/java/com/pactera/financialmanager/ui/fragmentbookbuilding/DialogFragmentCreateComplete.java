package com.pactera.financialmanager.ui.fragmentbookbuilding;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.callback.BookbuildingContactCallback;
import com.pactera.financialmanager.util.Constants;

/**
 * 创建竞争对手关系的dialog
 */
public class DialogFragmentCreateComplete extends DialogFragment implements OnClickListener {
	private EditText etInfo1,etInfo2,etInfo3,etInfo4;
	private Button btnContactYes,btnContactNo;
	private Spinner spinState,spinInfo,spinStyle;
	private BookbuildingContactCallback callback;
	/**
	 * 这是dialog中输入的信息
	 */
	private String info1,info2,info3,info4,info5,info6,info7;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View  view=View.inflate(getActivity(), R.layout.fragmentdialog_createcomplete, null);
		setupView(view);
		setupListener();
		Dialog dialog=new Dialog(getActivity(),R.style.dialog1);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		int w=Constants.SCREEN_WIDTH*1109/2048;
		int h=w*800/969;
		Log.i("width.....height......", "width:"+w+" ; "+"height:"+h);
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(w, h);
		dialog.addContentView(view, params);
		
		return dialog;
	}

	private void setupListener() {
		btnContactYes.setOnClickListener(this);
		btnContactNo.setOnClickListener(this);
	}

	private void setupView(View view) {
		etInfo1=(EditText)view.findViewById(R.id.et_complete1);
		etInfo2=(EditText)view.findViewById(R.id.et_complete2);
		etInfo3=(EditText)view.findViewById(R.id.et_complete3);
		etInfo4=(EditText)view.findViewById(R.id.et_complete4);
		
		//竞争伙伴合作关系
		spinState=(Spinner)view.findViewById(R.id.spin_complete1);
		//竞争伙伴证件类型
		spinStyle=(Spinner)view.findViewById(R.id.spin_complete2);
		//结算方式
		spinInfo=(Spinner)view.findViewById(R.id.spin_complete3);
		
		btnContactYes=(Button)view.findViewById(R.id.complete_btnYes);
		btnContactNo=(Button)view.findViewById(R.id.complete_btnNo);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.complete_btnYes:
			setFunction();
			dismiss();
			break;
		case R.id.complete_btnNo:
			dismiss();
			break;
		default:
			break;
		}
	}
	
	public static DialogFragmentCreateComplete getNewInstance(){
		DialogFragmentCreateComplete  fragment=new DialogFragmentCreateComplete();
		return fragment;
	}
	
	public void setCallBack(BookbuildingContactCallback callback){
		this.callback=callback;
	}
	
	/**
	 * 这是实现创建联络人信息的回调实现
	 */
	public void setFunction(){
		callback.bookbuildingContact(false,info1, info2, info3, info4, info5, info6, info7,"");
	}
	
}
















