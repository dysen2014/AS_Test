package com.pactera.financialmanager.ui.commontool;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.service.HConnection;

/**
 * 测评（对公）
 * 
 * @author JAY
 * 
 */
public class FragmentMeasureForCompany extends ParentFragment {
	private static OnCheckedChangeListener ChangeListener = null;
	RadioGroup[] measureGroup = new RadioGroup[10];
	int[] selectNum = new int[10];
	Button measure_btn;//确认按钮
	int ForCompanyResult;//最后结果
	Bundle data = new Bundle();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_measure_company, null);
		// 初始化对象
		findView(view);
		// 注册选择方法
		ChangeListener = new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				selectRadio(checkedId);
			}

		};
		setListener();
		return view;
	}

	private void setListener() {
		// TODO Auto-generated method stub
		for (int i = 0; i < measureGroup.length; i++) {
			measureGroup[i].setOnCheckedChangeListener(ChangeListener);
		}
		
		measure_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				ForCompanyResult=0;
				for (int i = 0; i < selectNum.length; i++) {
					ForCompanyResult=ForCompanyResult+selectNum[i];
				}
				Message msg = ((ParentActivity) getActivity()).handler.obtainMessage();
				data.clear();
				data.putString("value", "" + MeasureActivity.ResultIndex);
				msg.setData(data);
				MeasureActivity.MeasureResult=ForCompanyResult;
				((ParentActivity) getActivity()).handler.sendMessage(msg);
				
			}
		});
	}

	private void findView(View view) {
		// TODO Auto-generated method stub
		measureGroup[0] = (RadioGroup) view
				.findViewById(R.id.measure_company_rg_1);
		measureGroup[1] = (RadioGroup) view
				.findViewById(R.id.measure_company_rg_2);
		measureGroup[2] = (RadioGroup) view
				.findViewById(R.id.measure_company_rg_3);
		measureGroup[3] = (RadioGroup) view
				.findViewById(R.id.measure_company_rg_4);
		measureGroup[4] = (RadioGroup) view
				.findViewById(R.id.measure_company_rg_5);
		measureGroup[5] = (RadioGroup) view
				.findViewById(R.id.measure_company_rg_6);
		measureGroup[6] = (RadioGroup) view
				.findViewById(R.id.measure_company_rg_7);
		measureGroup[7] = (RadioGroup) view
				.findViewById(R.id.measure_company_rg_8);
		measureGroup[8] = (RadioGroup) view
				.findViewById(R.id.measure_company_rg_9);
		measureGroup[9] = (RadioGroup) view
				.findViewById(R.id.measure_company_rg_10);
		measure_btn=(Button) view
				.findViewById(R.id.measure_company_btn);
	}

	public void selectRadio(int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {

		case R.id.measure_company_rb_1_1:
			selectNum[0] = 1;
			break;
		case R.id.measure_company_rb_1_2:
			selectNum[0] = 2;
			break;
		case R.id.measure_company_rb_1_3:
			selectNum[0] = 3;
			break;
		case R.id.measure_company_rb_1_4:
			selectNum[0] = 4;
			break;
		case R.id.measure_company_rb_2_1:
			selectNum[1] = 0;
			break;
		case R.id.measure_company_rb_2_2:
			selectNum[1] = 1;
			break;
		case R.id.measure_company_rb_2_3:
			selectNum[1] = 2;
			break;
		case R.id.measure_company_rb_2_4:
			selectNum[1] = 3;
			break;
		case R.id.measure_company_rb_3_1:
			selectNum[2] = 1;
			break;
		case R.id.measure_company_rb_3_2:
			selectNum[2] = 2;
			break;
		case R.id.measure_company_rb_3_3:
			selectNum[2] = 3;
			break;
		case R.id.measure_company_rb_4_1:
			selectNum[3] = 1;
			break;
		case R.id.measure_company_rb_4_2:
			selectNum[3] = 2;
			break;
		case R.id.measure_company_rb_4_3:
			selectNum[3] = 3;
			break;
		case R.id.measure_company_rb_4_4:
			selectNum[3] = 4;
			break;
		case R.id.measure_company_rb_4_5:
			selectNum[3] = 6;
			break;
		case R.id.measure_company_rb_5_1:
			selectNum[4] = 1;
			break;
		case R.id.measure_company_rb_5_2:
			selectNum[4] = 2;
			break;
		case R.id.measure_company_rb_5_3:
			selectNum[4] = 3;
			break;
		case R.id.measure_company_rb_5_4:
			selectNum[4] = 4;
			break;
		case R.id.measure_company_rb_6_1:
			selectNum[5] = 1;
			break;
		case R.id.measure_company_rb_6_2:
			selectNum[5] = 2;
			break;
		case R.id.measure_company_rb_6_3:
			selectNum[5] = 3;
			break;
		case R.id.measure_company_rb_6_4:
			selectNum[5] = 4;
			break;
		case R.id.measure_company_rb_6_5:
			selectNum[5] = 5;
			break;
		case R.id.measure_company_rb_7_1:
			selectNum[6] = 1;
			break;
		case R.id.measure_company_rb_7_2:
			selectNum[6] = 2;
			break;
		case R.id.measure_company_rb_7_3:
			selectNum[6] = 3;
			break;
		case R.id.measure_company_rb_7_4:
			selectNum[6] = 6;
			break;
		case R.id.measure_company_rb_8_1:
			selectNum[7] = 1;
			break;
		case R.id.measure_company_rb_8_2:
			selectNum[7] = 2;
			break;
		case R.id.measure_company_rb_8_3:
			selectNum[7] = 3;
			break;
		case R.id.measure_company_rb_9_1:
			selectNum[8] = 0;
			break;
		case R.id.measure_company_rb_9_2:
			selectNum[8] = 1;
			break;
		case R.id.measure_company_rb_9_3:
			selectNum[8] = 3;
			break;
		case R.id.measure_company_rb_9_4:
			selectNum[8] = 4;
			break;
		case R.id.measure_company_rb_10_1:
			selectNum[9] = 1;
			break;
		case R.id.measure_company_rb_10_2:
			selectNum[9] = 2;
			break;
		case R.id.measure_company_rb_10_3:
			selectNum[9] = 4;
			break;
		case R.id.measure_company_rb_10_4:
			selectNum[9] = 5;
			break;
		case R.id.measure_company_rb_10_5:
			selectNum[9] = 6;
			break;
		default:
			break;
		}
	}
}
