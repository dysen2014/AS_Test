package com.pactera.financialmanager.ui;

import com.pactera.financialmanager.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 删除提醒的Dialog
 * 
 * @author JAY
 * 
 */
public class DeleteDialog extends Dialog implements View.OnClickListener {

	public static final int DEFAULT_STYLE = R.style.DialogTheme;
	private Context context;
	private TextView tvDialogTitle;
	private TextView tvContent;
	private Button btnOK, btnCancel;
	private IsDeleteListener theListener;

	public DeleteDialog(Context context, IsDeleteListener theIsDeleteListener) {
		this(context, DEFAULT_STYLE, theIsDeleteListener);
	}

	public DeleteDialog(Context context, int style,
			IsDeleteListener theIsDeleteListener) {
		super(context, style);
		this.context = context;
		this.setCanceledOnTouchOutside(false);
		this.theListener = theIsDeleteListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_del);

		findViews();
	}

	private void findViews() {
		tvDialogTitle = (TextView) findViewById(R.id.tv_dialog_title);
		tvContent = (TextView) findViewById(R.id.tv_dialog_msg);
		btnOK = (Button) findViewById(R.id.btn_ok);
		btnCancel = (Button) findViewById(R.id.btn_cancel);
		btnOK.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
	}

	public void close() {
		if (this != null && this.isShowing()) {
			dismiss();
		}
	}
	
	public void setMsg(String msg){
		tvContent.setText(msg);
	}
	
	public void setTitle(String title){
		tvDialogTitle.setText(title);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ok:
			if (theListener != null) {
				theListener.onClickDeleteListener(true);
			}
			close();
			break;

		case R.id.btn_cancel:
			if (theListener != null) {
				theListener.onClickDeleteListener(false);
			}
			close();
			break;

		default:
			break;
		}
	}

	public interface IsDeleteListener {
		public void onClickDeleteListener(boolean isDelete);
	}
}
