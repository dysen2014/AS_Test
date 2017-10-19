package com.pactera.financialmanager.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.commontool.CommonToolActivity;
import com.pactera.financialmanager.ui.customermanager.CusArchivingActivity;
import com.pactera.financialmanager.ui.hallfirst.HallFirstActivity;
import com.pactera.financialmanager.ui.model.User;
import com.pactera.financialmanager.ui.nowproject.WorkPlaceActivity;
import com.pactera.financialmanager.ui.productcenter.ProductIndexActivity;
import com.pactera.financialmanager.util.LimitsUtil;

/**
 * 系统首页
 * 
 * @author YPJ
 * 
 */

@SuppressLint("HandlerLeak")
public class MainActivity extends ParentActivity implements OnClickListener {
	private ImageButton[] touchBankBtns = new ImageButton[7];
	private ImageButton LoginOut;
	private String certcode;

	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		certcode = LogoActivity.user.getCERTCODE();
		// 初始化
		LogoActivity.user.getStaId();//ST001103
		setView();
		// 绑定监听器
		bindOnClickListener();
	}

	private void setView() {
		LoginOut = (ImageButton) findViewById(R.id.main_loginout_btn);
		touchBankBtns[0] = (ImageButton) findViewById(R.id.main_work);
		touchBankBtns[1] = (ImageButton) findViewById(R.id.main_cusmanage);
		touchBankBtns[2] = (ImageButton) findViewById(R.id.main_hallManage);
		touchBankBtns[3] = (ImageButton) findViewById(R.id.main_querycard);
		touchBankBtns[4] = (ImageButton) findViewById(R.id.main_querycus);
		touchBankBtns[5] = (ImageButton) findViewById(R.id.main_saleManage);
		touchBankBtns[6] = (ImageButton) findViewById(R.id.main_tools);
	}

	private void bindOnClickListener() {
		for (int i = 0; i < touchBankBtns.length; i++) {
			touchBankBtns[i].setOnClickListener(this);
		}
		LoginOut.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		boolean isRight = false;
		Intent intent = new Intent();
		switch (v.getId()) {
		// 注销
		case R.id.main_loginout_btn:
			intent.setClass(MainActivity.this, NewLoginActivity.class);
			startActivity(intent);
			finish();
			break;
		// 工作平台
		case R.id.main_work:
			isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T05);
			if(isRight){
				intent.setClass(MainActivity.this, WorkPlaceActivity.class);
				startActivity(intent);
			}
			break;
		// 客户建档首页
		case R.id.main_cusmanage:
			intent.setClass(MainActivity.this, CusArchivingActivity.class);
			startActivity(intent);
			break;
		// 厅堂管理
		case R.id.main_hallManage:
			isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T01);
			if(isRight){
				intent.setClass(MainActivity.this, HallFirstActivity.class);
				startActivity(intent);
			}
			break;
		// 信用卡查询
		case R.id.main_querycard:
			isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T07);
			if(isRight){
				intent.setClass(MainActivity.this, DiscoverCardActivity.class);
				intent.putExtra("certcode",certcode);
				startActivity(intent);
			}
			break;
		// 客户查询
		case R.id.main_querycus:
			isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T02);
			if(isRight){
				intent.setClass(MainActivity.this, QuerycusActivity.class);
				startActivity(intent);
			}
			break;
		// 营销管理
		case R.id.main_saleManage:
			isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T08);
			if(isRight){
				intent.setClass(MainActivity.this, ProductIndexActivity.class);
				startActivity(intent);
			}
			break;
		// 常用工具
		case R.id.main_tools:
			isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T06);
			if(isRight){
				intent.setClass(MainActivity.this, CommonToolActivity.class);
				startActivity(intent);
			}
			break;
		default:
			break;
		}
	}

}