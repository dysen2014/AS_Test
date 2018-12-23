package com.pactera.financialmanager.ui.productcenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.util.LimitsUtil;

/**
 * 营销管理
 * 
 * @author JAY
 * 
 */
public class ProductIndexActivity extends ParentActivity implements
		OnClickListener {

	ImageView favorable;// 优惠活动
	ImageView sales;// 热销产品

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_products);
		// 初始化
		findViews();
		// 绑定监听器
		bindOnClickListener();
		initTitle(this, "营销管理");
	}

	private void bindOnClickListener() {
		favorable.setOnClickListener(this);
		sales.setOnClickListener(this);
	}

	private void findViews() {
		favorable = (ImageView) findViewById(R.id.products_index_favorable);
		sales = (ImageView) findViewById(R.id.products_index_sales);
	}

	@Override
	public void onClick(View v) {
		boolean isRight = false;
		Intent intent = new Intent();
		switch (v.getId()) {
		// 优惠活动
		case R.id.products_index_favorable:
			isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T0801);
			if(isRight){
				intent.setClass(ProductIndexActivity.this, PromotionsActivity.class);
				startActivity(intent);
			}
			break;
		// 热销产品
		case R.id.products_index_sales:
			isRight = LimitsUtil.checkUserLimit(this, LimitsUtil.T0802);
			if(isRight){
				intent.setClass(ProductIndexActivity.this, HotProductActivity.class);
				startActivity(intent);
			}
			break;
		default:
			break;
		}
	}

}
