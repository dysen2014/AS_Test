package com.pactera.financialmanager.ui.fragmentbookbuilding;

import java.io.Serializable;
import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import com.pactera.financialmanager.callback.BookbuildingContentCallback;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog.OnDateSetListener;
import com.pactera.financialmanager.ui.model.FuzaiInfoEntity;
import com.pactera.financialmanager.ui.model.GudingZichanEntity;
import com.pactera.financialmanager.util.Catevalue;
import com.pactera.financialmanager.util.CompayValue;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

/**
 * 这是客户建档（对公）中的创建客户资产和创建负债情况的dialog
 */
public class DialogFragmentCreateGdzichan extends DialogFragment implements
		OnClickListener {
	private Button btnYes, btnNo;
	private TextView tvTitle, tvTitleInfo1, tvTitleInfo2, tvTitleInfo3,
			tvTitleInfo4, tvTitleInfo5, tvTitleInfo6, tvTitleInfo7,
			tvTitleInfo8, tvTitleInfo9;
	private EditText etContent3, etContent4, etContent5, etContent6,
			etContent7, etContent8, etContent9, etContent2;
	private EditText spSrange;
	private int flag;
	private String spSrangeId = "";// 资产类别的返回码

	// isEdit,用于判断是否是编辑。false,不是编辑，即创建；true，是编辑。默认为false。它是传过来的。
	// isCanEdit,用于判断是否在编辑。true,表示可以编辑；false，表示不可编辑。若不可编辑，则点击确定按钮，无效。
	private Boolean isEdit = false, isCanEdit = true;
	private GudingZichanEntity entityGuz;
	private FuzaiInfoEntity entityFuzai;
	private CheckBox cboxEdit;

	private BookbuildingContentCallback callback;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View view = View.inflate(getActivity(),
				R.layout.fragmentdialog_createzichan, null);
		Bundle bundle = getArguments();
		flag = bundle.getInt("Flag");
		isEdit = bundle.getBoolean("isEdit");
		setupView(view);
		setListener();
		if (flag == 1 && isEdit) {
			cboxEdit.setVisibility(View.VISIBLE);
			entityGuz = (GudingZichanEntity) bundle.getSerializable("list");
			setData();
		} else if (flag == 2 && isEdit) {
			cboxEdit.setVisibility(View.VISIBLE);
			entityFuzai = (FuzaiInfoEntity) bundle.getSerializable("list");
			setData();
		}
		
		Dialog dialog = new Dialog(getActivity(), R.style.dialog1);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// android.view.WindowManager.LayoutParams attributes =
		// dialog.getWindow().getAttributes();
		// dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
		// WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		// dialog.getWindow().setAttributes(attributes);
		// attributes.alpha=1.0f;
		// android.view.WindowManager.LayoutParams lp =
		// getActivity().getWindow().getAttributes();
		// lp.alpha=0.5f;
		// lp.dimAmount=1.0f;
		// getActivity().getWindow().setAttributes(lp);

		// InputMethodManager imm = (InputMethodManager)
		// getSystemService(INPUT_METHOD_SERVICE);

		int w = Constants.SCREEN_WIDTH * 969 / 2048;
		int h = w*926/949;
		Log.i("width.....height......", "width:" + w + " ; " + "height:" + h);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, h);
		dialog.addContentView(view, params);

		// 我想隐藏dialog弹出之后也会弹出的软键盘，但现在还没有解决这个问题。
		// View view2 = dialog.getCurrentFocus();
		// if(view2!=null){
		// InputMethodManager im= (InputMethodManager)
		// getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		// im.hideSoftInputFromWindow(view2.getWindowToken(), 0);
		// }

		return dialog;
	}

	// 设置固定资产数据
	private void setData() {
		canNotEdit();
		if (flag == 1) {
			String propertyType = entityGuz.getPROPERTY_TYPE();
//			String propertyValue = Catevalue.getValue(propertyType, CompayValue.infoStr, CompayValue.infoValue);
			String propertyValue = NewCatevalue.getLabel(getActivity(), NewCatevalue.ent_other_rel, propertyType);

			tvTitle.setText("查看固定资产信息");// tvTitle.setText("创建负债信息");
			spSrange.setText(propertyValue);
			etContent3.setText(entityGuz.getPROPERTY_NAME());
			tvTitleInfo4.setText(entityGuz.getPURCHASE_DATE());
			etContent5.setText(entityGuz.getPURCHASE_PRICE());
			etContent6.setText(entityGuz.getAREA());
			etContent7.setText(entityGuz.getCERTIFIED_AREA());
			etContent8.setText(entityGuz.getQUANTITY());
			etContent9.setText(entityGuz.getACCESS_VALUE());
		} else if (flag == 2) {
			tvTitle.setText("查看负债情况");
			etContent2.setText(entityFuzai.getDEBT_RENTAL());
			etContent3.setText(entityFuzai.getLAST_ANNUAL_INCOME());
			etContent4.setText(entityFuzai.getLAST_ANNUAL_OUTPUT());
			etContent5.setText(entityFuzai.getPROFIT());
			etContent6.setText(entityFuzai.getHAND_TAX());
			etContent7.setText(entityFuzai.getHAND_CREDIT());
			etContent8.setText(entityFuzai.getPAY_CREDIT());
			etContent9.setText(entityFuzai.getINVENTORY());
		}
	}

	// 不可编辑
	private void canNotEdit() {
		if (flag == 1) {
			tvTitle.setText("编辑固定资产信息");
		} else if (flag == 2) {
			tvTitle.setText("编辑负债情况");
		}

		isCanEdit=false;
		spSrange.setEnabled(false);
		etContent2.setEnabled(false);
		etContent3.setEnabled(false);
		etContent4.setEnabled(false);
		tvTitleInfo4.setEnabled(false);
		etContent5.setEnabled(false);
		etContent6.setEnabled(false);
		etContent7.setEnabled(false);
		etContent8.setEnabled(false);
		etContent9.setEnabled(false);
	}

	// 可编辑
	private void canEdit() {
		isCanEdit=true;
		spSrange.setEnabled(true);
		etContent2.setEnabled(true);
		etContent3.setEnabled(true);
		etContent4.setEnabled(true);
		tvTitleInfo4.setEnabled(true);
		etContent5.setEnabled(true);
		etContent6.setEnabled(true);
		etContent7.setEnabled(true);
		etContent8.setEnabled(true);
		etContent9.setEnabled(true);
	}

	private void setListener() {
		tvTitleInfo4.setOnClickListener(this);
		spSrange.setOnClickListener(this);
		btnYes.setOnClickListener(this);
		btnNo.setOnClickListener(this);
		cboxEdit.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					cboxEdit.setText("取消");
					canEdit();
				} else {
					cboxEdit.setText("编辑");
					canNotEdit();
				}
			}
		});
	}

	private void setupView(View view) {
		btnYes = (Button) view.findViewById(R.id.btnYes);
		btnNo = (Button) view.findViewById(R.id.btnNo);
		spSrange = (EditText) view.findViewById(R.id.spin_businesstate);
		
		cboxEdit = (CheckBox) view.findViewById(R.id.cbox_edit);// 编辑
		cboxEdit.setVisibility(View.GONE);

		tvTitle = (TextView) view.findViewById(R.id.tv_title);
		tvTitleInfo1 = (TextView) view
				.findViewById(R.id.tv_buildingdialoginfo1);
		tvTitleInfo2 = (TextView) view
				.findViewById(R.id.tv_buildingdialoginfo2);
		tvTitleInfo3 = (TextView) view
				.findViewById(R.id.tv_buildingdialoginfo3);
		tvTitleInfo4 = (TextView) view
				.findViewById(R.id.tv_buildingdialoginfo4);
		tvTitleInfo5 = (TextView) view
				.findViewById(R.id.tv_buildingdialoginfo5);
		tvTitleInfo6 = (TextView) view
				.findViewById(R.id.tv_buildingdialoginfo6);
		tvTitleInfo7 = (TextView) view
				.findViewById(R.id.tv_buildingdialoginfo7);
		tvTitleInfo8 = (TextView) view
				.findViewById(R.id.tv_buildingdialoginfo8);
		tvTitleInfo9 = (TextView) view
				.findViewById(R.id.tv_buildingdialoginfo9);

		etContent2 = (EditText) view.findViewById(R.id.et_buildingdialoginfo2);
		etContent3 = (EditText) view.findViewById(R.id.et_buildingdialoginfo3);
		etContent4 = (EditText) view.findViewById(R.id.et_buildingdialoginfo4);
		etContent5 = (EditText) view.findViewById(R.id.et_buildingdialoginfo5);
		etContent6 = (EditText) view.findViewById(R.id.et_buildingdialoginfo6);
		etContent7 = (EditText) view.findViewById(R.id.et_buildingdialoginfo7);
		etContent8 = (EditText) view.findViewById(R.id.et_buildingdialoginfo8);
		etContent9 = (EditText) view.findViewById(R.id.et_buildingdialoginfo9);
		if (flag == 1) {
			tvTitle.setText("创建固定资产信息");
			tvTitleInfo1.setText("资产类别：");
			spSrange.setVisibility(View.VISIBLE);
			etContent2.setVisibility(View.GONE);
			tvTitleInfo2.setText("资产名称：");
			tvTitleInfo3.setText("资产购置日期：");
			tvTitleInfo4.setVisibility(View.VISIBLE);
			etContent4.setVisibility(View.GONE);
			tvTitleInfo5.setText("资产购置原价：");
			tvTitleInfo6.setText("实际面积：");
			tvTitleInfo7.setText("已办证面积：");
			tvTitleInfo8.setText("数量：");
			tvTitleInfo9.setText("评估价值：");
		} else if (flag == 2) {
			tvTitle.setText("创建负债信息");
			tvTitleInfo1.setText("负债总额：");
			spSrange.setVisibility(View.GONE);
			etContent2.setVisibility(View.VISIBLE);
			tvTitleInfo2.setText("上年营业收入：");
			tvTitleInfo3.setText("上年总成本：");
			tvTitleInfo4.setVisibility(View.GONE);
			etContent4.setVisibility(View.VISIBLE);
			tvTitleInfo5.setText("上年利润：");
			tvTitleInfo6.setText("上年上交税收：");
			tvTitleInfo7.setText("应收账款：");
			tvTitleInfo8.setText("应付账款：");
			tvTitleInfo9.setText("存货：");
		}
	}

	/**
	 * 这仅仅只是创建时所使用弹出框的构造函数
	 * 
	 * @param Flag
	 *            ,
	 * @return
	 */
	public static DialogFragmentCreateGdzichan getNewInstance(int Flag) {
		DialogFragmentCreateGdzichan fragment = new DialogFragmentCreateGdzichan();
		Bundle bundle = new Bundle();
		bundle.putInt("Flag", Flag);
		fragment.setArguments(bundle);
		return fragment;
	}

	/**
	 * 这仅仅只是编辑时所使用弹出框的构造函数
	 * 
	 * @param Flag
	 *            1，2
	 * @param isEdit
	 *            false,代表不可编辑；true,代表可以编辑
	 * @param list
	 *            传递过来的集合，数据都在集合中
	 * @return
	 */
	public static DialogFragmentCreateGdzichan getNewInstance(int Flag,
			Boolean isEdit, Serializable listEntity) {
		DialogFragmentCreateGdzichan fragment = new DialogFragmentCreateGdzichan();
		Bundle bundle = new Bundle();
		bundle.putInt("Flag", Flag);
		bundle.putBoolean("isEdit", isEdit);
		bundle.putSerializable("list", listEntity);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnYes:// 确定按钮
			if (flag == 1 && isCanEdit) {
				Toast.makeText(getActivity(), tvTitleInfo9.isEnabled()+"....", 0).show();
				String spRange = spSrange.getText().toString().trim();
				if (Tool.checkInputStr(spRange)) {
					Toast.makeText(getActivity(), "请选择资产类别", 0).show();
					return;
				}
				String etZichanName = etContent3.getText().toString().trim();
				if (Tool.checkInputStr(etZichanName)) {
					Toast.makeText(getActivity(), "请输入资产名称", 0).show();
					return;
				}
				String tvZichanDate = tvTitleInfo4.getText().toString().trim();
				if (Tool.checkInputStr(tvZichanDate)) {
					Toast.makeText(getActivity(), "请选择资产购置日期", 0).show();
					return;
				}
				String etZichanMoney = etContent5.getText().toString().trim();
				if (Tool.checkInputStr(etZichanMoney)) {
					Toast.makeText(getActivity(), "请输入资产购置原价", 0).show();
					return;
				}
				String etInfo6 = etContent6.getText().toString().trim();
				if (Tool.checkInputStr(etInfo6)) {
					Toast.makeText(getActivity(), "请输入实际面积", 0).show();
					return;
				}
				String etInfo7 = etContent7.getText().toString().trim();
				if (Tool.checkInputStr(etInfo7)) {
					Toast.makeText(getActivity(), "请输入已办证面积", 0).show();
					return;
				}
				String etInfo8 = etContent8.getText().toString().trim();
				if (Tool.checkInputStr(etInfo8)) {
					Toast.makeText(getActivity(), "请输入已办证面积", 0).show();
					return;
				}
				String etInfo9 = etContent9.getText().toString().trim();
				if (Tool.checkInputStr(etInfo8)) {
					Toast.makeText(getActivity(), "请输入评估价值", 0).show();
					return;
				}
				setFunction(spSrangeId, etZichanName, tvZichanDate,
						etZichanMoney, etInfo6, etInfo7, etInfo8, etInfo9);
			} else if (flag == 2 && isCanEdit) {
				String etInfo2 = etContent2.getText().toString().trim();
				if (Tool.checkInputStr(etInfo2)) {
					Toast.makeText(getActivity(), "请输入负债总额", 0).show();
					return;
				}
				String etInfo3 = etContent3.getText().toString().trim();
				if (Tool.checkInputStr(etInfo3)) {
					Toast.makeText(getActivity(), "请输入上年营业收入", 0).show();
					return;
				}
				String etInfo4 = etContent4.getText().toString().trim();
				if (Tool.checkInputStr(etInfo4)) {
					Toast.makeText(getActivity(), "请输入上年总成本", 0).show();
					return;
				}
				String etInfo5 = etContent5.getText().toString().trim();
				if (Tool.checkInputStr(etInfo5)) {
					Toast.makeText(getActivity(), "请输入上年利润", 0).show();
					return;
				}
				String etInfo6 = etContent6.getText().toString().trim();
				if (Tool.checkInputStr(etInfo6)) {
					Toast.makeText(getActivity(), "请输入上年上交税收", 0).show();
					return;
				}
				String etInfo7 = etContent7.getText().toString().trim();
				if (Tool.checkInputStr(etInfo7)) {
					Toast.makeText(getActivity(), "请输入应收账款", 0).show();
					return;
				}
				String etInfo8 = etContent8.getText().toString().trim();
				if (Tool.checkInputStr(etInfo8)) {
					Toast.makeText(getActivity(), "请输入应付账款", 0).show();
					return;
				}
				String etInfo9 = etContent9.getText().toString().trim();
				if (Tool.checkInputStr(etInfo9)) {
					Toast.makeText(getActivity(), "请输入存货", 0).show();
					return;
				}
				setFunction1(etInfo2, etInfo3, etInfo4, etInfo5, etInfo6,
						etInfo7, etInfo8, etInfo9);
			}
			dismiss();
			break;

		case R.id.btnNo:// 取消按钮
			dismiss();
			break;
		case R.id.spin_businesstate:// 资产类别的选择
			SpinnerAdapter.showSelectDialog(getActivity(),
					NewCatevalue.ent_other_rel,
					spSrange,
					new CallBackItemClickListener() {
						@Override
						public void CallBackItemClick(String spinnerID) {
							spSrangeId = spinnerID;
						}
					});
			break;
		case R.id.tv_buildingdialoginfo4:// 选择日期
			DatePickerShow(new OnDateSetListener() {
				@Override
				public void onDateSet(String year, String month, String day) {
					// TODO Auto-generated method stub
					String dayate = year + "-" + month + "-" + day;
					tvTitleInfo4.setText(dayate);
				}
			});
			break;
		default:
			break;
		}
	}

	/**
	 * 日期选择器
	 * 
	 * @param onDateSetListener
	 */
	public void DatePickerShow(OnDateSetListener onDateSetListener) {
		Calendar calendar = Calendar.getInstance();
		DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
				onDateSetListener, calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		datePickerDialog.show(
				((FragmentActivity) getActivity()).getSupportFragmentManager(),
				"datepicker");

	}

	public void setCallBack(BookbuildingContentCallback callback) {
		this.callback = callback;
	}

	/**
	 * 这是创建固定资产的回调
	 * 
	 * @param etInfo9
	 * @param etInfo8
	 * @param etInfo7
	 * @param etInfo6
	 * @param etZichanMoney
	 * @param tvZichanDate
	 * @param etZichanName
	 * @param spSrangeId2
	 */
	public void setFunction(String spSrangeId2, String etZichanName,
			String tvZichanDate, String etZichanMoney, String etInfo6,
			String etInfo7, String etInfo8, String etInfo9) {
		callback.bookbuildingContent(isEdit, spSrangeId2, etZichanName,
				tvZichanDate, etZichanMoney, etInfo6, etInfo7, etInfo8, etInfo9);
	}

	/**
	 * 这是创建负债信息的回调
	 * 
	 * @param etInfo8
	 * @param etInfo7
	 * @param etInfo6
	 * @param etInfo5
	 * @param etInfo4
	 * @param etInfo3
	 * @param etInfo2
	 * @param etInfo9
	 */
	public void setFunction1(String etInfo2, String etInfo3, String etInfo4,
			String etInfo5, String etInfo6, String etInfo7, String etInfo8,
			String etInfo9) {
		callback.bookbuildingContent1(isEdit, etInfo2, etInfo3, etInfo4,
				etInfo5, etInfo6, etInfo7, etInfo8, etInfo9);
	}

}
