package com.pactera.financialmanager.ui.fragmentbookbuilding;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.adapter.SpinnerAdapter;
import com.pactera.financialmanager.adapter.SpinnerAdapter.CallBackItemClickListener;
import com.pactera.financialmanager.callback.BookbuildingTeamCallback;
import com.pactera.financialmanager.ui.model.TeamInfoEntity;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

import java.io.Serializable;
/**
 * 创建 集团关系的 dialog
 */
public class DialogFragmentCreateTeamInfo extends DialogFragment implements OnClickListener {
	private TextView tvTeamInfo1,tvTeamInfo2,tvTeamInfo3,tvTeamInfo4,tvTitle;
	private Button btnTeamYes,btnTeamNo;
	private EditText spinTeamState;
	private BookbuildingTeamCallback callback;
	
	private CheckBox cbox;
	private String spStyleRel;
	private Boolean isEdit=false;
	private Boolean isCanEdit=true;
	private TeamInfoEntity entity;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View  view=View.inflate(getActivity(), R.layout.fragmentdialog_createteaminfo, null);
		Bundle bundle = getArguments();
		setupView(view);
		if(bundle!=null){
			cbox.setVisibility(View.VISIBLE);
			isEdit=bundle.getBoolean("isEdit");
			entity=(TeamInfoEntity) bundle.getSerializable("entity");
			setData();
		}
		setupListener();
		Dialog dialog=new Dialog(getActivity(),R.style.dialog1);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		int w=Constants.SCREEN_WIDTH*969/2048;
		int h=w*926/949;
		Log.i("width.....height......", "width:"+w+" ; "+"height:"+h);
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(w, h);
		dialog.addContentView(view, params);
		
		return dialog;
	}

	private void setData() {
		canNotEdit();
		tvTitle.setText("查看集团客户信息");
		tvTeamInfo1.setText(entity.getGROUP_NAME());
		tvTeamInfo2.setText(entity.getHIGHER_NAME());
		String group_RELATION = entity.getGROUP_RELATION();
		String teamRelaVal= NewCatevalue.getLabel(getActivity(), NewCatevalue.group_rel_type, group_RELATION);
		spStyleRel=group_RELATION;
        spinTeamState.setText(teamRelaVal);
		tvTeamInfo3.setText(entity.getHOLDER_PERTAGE());
		tvTeamInfo4.setText(entity.getDES());
	}
	
	private void canEdit() {
		isCanEdit=true;
		tvTeamInfo1.setEnabled(true);
		tvTeamInfo2.setEnabled(true);
		spinTeamState.setEnabled(true);
		tvTeamInfo3.setEnabled(true);
		tvTeamInfo4.setEnabled(true);
	}
	
	private void canNotEdit() {
		isCanEdit=false;
		tvTeamInfo1.setEnabled(false);
		tvTeamInfo2.setEnabled(false);
		spinTeamState.setEnabled(false);
		tvTeamInfo3.setEnabled(false);
		tvTeamInfo4.setEnabled(false);
	}
	

	private void setupListener() {
		spinTeamState.setOnClickListener(this);
		btnTeamYes.setOnClickListener(this);
		btnTeamNo.setOnClickListener(this);
		cbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					cbox.setText("取消");
					canEdit();
					tvTitle.setText("编辑集团客户信息");
				} else {
					cbox.setText("编辑");
					canNotEdit();
				}
			}
		});
	}

	private void setupView(View view) {
		tvTitle=(TextView)view.findViewById(R.id.tv_title);
		tvTeamInfo1=(TextView)view.findViewById(R.id.et_teaminfo1);
		tvTeamInfo2=(TextView)view.findViewById(R.id.et_teaminfo2);
		tvTeamInfo3=(TextView)view.findViewById(R.id.et_teaminfo3);
		tvTeamInfo4=(TextView)view.findViewById(R.id.et_teaminfo4);
		spinTeamState =(EditText)view.findViewById(R.id.spin_teamstate);
		btnTeamYes=(Button)view.findViewById(R.id.dialogteam_btnYes);
		btnTeamNo=(Button)view.findViewById(R.id.dialogteam_btnNo);
		cbox=(CheckBox)view.findViewById(R.id.cbox_edit);
		cbox.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.spin_teamstate:
			SpinnerAdapter.showSelectDialog(getActivity(),
					NewCatevalue.group_rel_type,
                    spinTeamState, new CallBackItemClickListener() {
						@Override
						public void CallBackItemClick(String spinnerID) {
							spStyleRel = spinnerID;
						}
					});
			break;
		case R.id.dialogteam_btnYes:
			String info1 = tvTeamInfo1.getText().toString().trim();
			String info2 = tvTeamInfo2.getText().toString().trim();
			String info3 = tvTeamInfo3.getText().toString().trim();
			String info4 = tvTeamInfo4.getText().toString().trim();
			String spinState = spinTeamState.getText().toString().trim();


			if (Tool.checkInputStr(info1)) {
				Toast.makeText(getActivity(), "请输入集团公司名称", Toast.LENGTH_SHORT).show();
				return;
			}
			if (Tool.checkInputStr(info2)) {
				Toast.makeText(getActivity(), "请输入上级企业名称", Toast.LENGTH_SHORT).show();
				return;
			}
			if (Tool.checkInputStr(spinState)) {
				Toast.makeText(getActivity(), "请选择关联关系", Toast.LENGTH_SHORT).show();
				return;
			}
			if (Tool.checkInputStr(info3)) {
				Toast.makeText(getActivity(), "请输入持股比例", Toast.LENGTH_SHORT).show();
				return;
			}
			if (!Tool.checkValidRatio(info3)) {
				Toast.makeText(getActivity(), "请输入有效持股比例", Toast.LENGTH_SHORT).show();
				return;
			}


			if(isCanEdit){
                if( null == spStyleRel){
                    spStyleRel = "";
                }
				/**
				 * 时间所限，现在都没有加判断了
				 */
				setFunction(info1,info2,spStyleRel,info3,info4);
			}
			dismiss();
			break;
		case R.id.dialogteam_btnNo:
			dismiss();
			break;
		default:
			break;
		}
	}
	
	public static DialogFragmentCreateTeamInfo getNewInstance(){
		DialogFragmentCreateTeamInfo  fragment=new DialogFragmentCreateTeamInfo();
		return fragment;
	}
	
	public static DialogFragmentCreateTeamInfo getNewInstance(Boolean isEdit,Serializable entity){
		DialogFragmentCreateTeamInfo  fragment=new DialogFragmentCreateTeamInfo();
		Bundle bundle=new Bundle();
		bundle.putBoolean("isEdit", isEdit);
		bundle.putSerializable("entity", entity);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	public void setCallBack(BookbuildingTeamCallback callback){
		this.callback=callback;
	}
	
	/**
	 * 这是实现创建联络人信息的回调实现
	 * @param info42 
	 * @param info32 
	 * @param spinId 
	 * @param info22 
	 * @param info12 
	 */
	public void setFunction(String info12, String info22, String spinId, String info32, String info42){
		callback.bookbuildingTeam(isEdit,info12, info22, spinId, info32, info42);
	}
	
}
















