package com.pactera.financialmanager.ui.dgfragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.callback.BtnClickListener;
import com.pactera.financialmanager.entity.PadQueueInfoBean;
import com.pactera.financialmanager.ui.model.OtherLineChannelEntity;
import com.pactera.financialmanager.util.Constants;

import java.io.Serializable;
import java.util.List;

/**
 * 分流的弹出框
 */
public class MyCpDialogFragmentButtons extends DialogFragment {

	private TextView txtQueueCode;
	private Button[] btn=new Button[11];
	private PadQueueInfoBean queueInfoForShunt;
	//根据得到的字段信息，来确定哪些分流渠道是没有的，来确定按钮的显示和影藏。。。。。。    这一部分目前还没有弄，因为现在没有返回的字段。
	private List<OtherLineChannelEntity> otherLineChannelCheckList;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View view=View.inflate(getActivity(), R.layout.otherlinedialogfragment, null);
		queueInfoForShunt = (PadQueueInfoBean)getArguments().getSerializable("forShunt");
		otherLineChannelCheckList=(List<OtherLineChannelEntity>) getArguments().getSerializable("machineCode");
		setUpView(view);
		Dialog dialog=new Dialog(getActivity(),R.style.dialog1);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		int w=(int)Constants.SCREEN_WIDTH*3/12;
		int h=(int)Constants.SCREEN_HEIGHT*5/9;
//		Log.i(".....widht....height.....", ":::"+w+"...."+h);
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(w, h);
		dialog.addContentView(view, params);


		return dialog;
	}

	/**
	 * 分流的接口中传的分流渠道，就是otherLineChannelCheckList集合中每一个对象的业务类型id
	 * 
	 * 这个目前还没有改，因为没有值，稍后再改
	 */
	private void setUpView(View view) {

		txtQueueCode = (TextView)view.findViewById(R.id.queue_code);
		if (!TextUtils.isEmpty(queueInfoForShunt.getCustCHNName())) {
			txtQueueCode.setText(queueInfoForShunt.getCustCHNName());
		} else {
			txtQueueCode.setText(queueInfoForShunt.getQueueCode());
		}

		btn[0]=(Button)view.findViewById(R.id.otherline_btn0);//取消按钮
		btn[1]=(Button)view.findViewById(R.id.otherline_btn1);//各支行的窗口展示，三峡农商八个窗口，其它支行大都四个以内
		btn[2]=(Button)view.findViewById(R.id.otherline_btn2);
		btn[3]=(Button)view.findViewById(R.id.otherline_btn3);
		btn[4]=(Button)view.findViewById(R.id.otherline_btn4);
		btn[5]=(Button)view.findViewById(R.id.otherline_btn5);
		btn[6]=(Button)view.findViewById(R.id.otherline_btn6);
		btn[7]=(Button)view.findViewById(R.id.otherline_btn7);
		btn[8]=(Button)view.findViewById(R.id.otherline_btn8);
		
		//从1开始，0是取消按钮
		for (int i = 0; i < otherLineChannelCheckList.size(); i++) {
			OtherLineChannelEntity entity = otherLineChannelCheckList.get(i);
			final String typeStr = entity.getType();
			if (queueInfoForShunt.getQueueType().equals(typeStr)) {
				continue;
			}

			String descInfo = entity.getDesc();
			btn[i+1].setVisibility(View.VISIBLE);//从1 开始是因为0是取消按钮
			btn[i+1].setText(descInfo);
			btn[i+1].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(getActivity() instanceof BtnClickListener){
						((BtnClickListener)getActivity()).btnClick(typeStr, -1);
					}
					dismiss();
				}
			});
		}
		
		btn[0].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}
	
	public static MyCpDialogFragmentButtons getNewInstance(PadQueueInfoBean bean ,Serializable entity){
		MyCpDialogFragmentButtons fragment=new MyCpDialogFragmentButtons();
		Bundle bundle=new Bundle();
		bundle.putSerializable("forShunt", bean);
		bundle.putSerializable("machineCode", entity);
		fragment.setArguments(bundle);
		return fragment;
	}

}
