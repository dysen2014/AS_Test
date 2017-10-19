package com.pactera.financialmanager.ui.productcenter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;
import com.pactera.financialmanager.ui.model.ProductInfo;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;
import com.pactera.financialmanager.util.Constants.requestType;

/**
 * 热销产品
 * 
 * @author YPJ
 * 
 */

public class HotProductActivity extends ParentActivity {

	List<ProductInfo> theProductInfo = new ArrayList<ProductInfo>();
	private HConnection HCon;
	private final int returnIndex = 1;
	GridView hotProduct_gridView;
	Intent intent = new Intent();
	HotProductAdapter infoAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotproducts);
		initTitle(this, R.drawable.yingxiaoguanli);
		findViews();
		getData(20, 0, "");
		hotProduct_gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ProductInfo theItem=infoAdapter.getItem(position);

				intent.putExtra("PRD_CLASS", theItem.PRD_CLASS);
				intent.putExtra("PRD_ID", theItem.PRD_ID);
				intent.setClass(HotProductActivity.this,
						HotProductInfoActivity.class);
				startActivity(intent);
			}
		});

	}

	/**
	 * 
	 * @param size
	 *            每页记录数
	 * @param offset
	 *            查询页码
	 * @param hotflag
	 *            查询标志
	 */
	private void getData(int size, int offset, String hotflag) {
		// TODO Auto-generated method stub

		HCon = RequestInfo(this, requestType.Other,
				InterfaceInfo.HotProduct_Get, "", returnIndex);

	}

	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			break;
		case returnIndex:
			HResponse res = HCon
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			JSONObject tmpJsonObject = res.dataJsonObject;
			Log.i("", tmpJsonObject.toString());
			readJson(tmpJsonObject);
			break;
		}
	}

	private void readJson(JSONObject tmpJsonObject) {
		String retCode = Tool.getJsonValue(tmpJsonObject, "retCode");

		if (retCode.equals("0000")) {
			try {
				JSONArray theInfo = tmpJsonObject.getJSONArray("group");
				if (theInfo.length() < 0) {
					Toast.makeText(HotProductActivity.this, "没有数据!",
							Toast.LENGTH_SHORT).show();
					return;
				} else {
					theProductInfo.clear();
					for (int i = 0; i < theInfo.length(); i++) {
						JSONObject temps = (JSONObject) theInfo.opt(i);
						ProductInfo tempHotProduct = new ProductInfo();
						tempHotProduct.PRD_ID=Tool.getJsonValue(temps, "PRD_ID");// 产品id
						tempHotProduct.PRD_NAME=Tool.getJsonValue(temps, "PRD_NAME");// 产品名称
						tempHotProduct.PRD_CLASS=Tool.getJsonValue(temps, "PRD_CLASS");// 产品分类（所属目录）
						tempHotProduct.ZGNSYL=Tool.getJsonValue(temps, "ZGNSYL");// 最高年收益率
						tempHotProduct.CPFXDJ=Tool.getJsonValue(temps, "CPFXDJ");// 产品风险等级
						tempHotProduct.MJQSR=Tool.getJsonValue(temps, "MJQSR");// 募集起始日
						tempHotProduct.CPQXR=Tool.getJsonValue(temps, "CPQXR");// 产品气息日
						tempHotProduct.CPQX=Tool.getJsonValue(temps, "CPQX");// 产品期限
					
						theProductInfo.add(tempHotProduct);

					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (theProductInfo.size()>0) {
				infoAdapter.notifyDataSetChanged();
			}
		} else if (retCode.equals("2001")) {
			Toast.makeText(HotProductActivity.this, "数据为空!", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(HotProductActivity.this, "获取失败!", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void findViews() {
		// TODO Auto-generated method stub
		hotProduct_gridView = (GridView) findViewById(R.id.hotproduct_gridview);
		infoAdapter = new HotProductAdapter(this);
		hotProduct_gridView.setAdapter(infoAdapter);
		infoAdapter.setData(theProductInfo);
		
//		ProductInfo testInfo=new ProductInfo();
//		testInfo.PRD_ID="1";// 产品id
//		testInfo.PRD_NAME="测试产品";// 产品名称
//		testInfo.PRD_CLASS="ceshi";// 产品分类（所属目录）
//		testInfo.ZGNSYL="8.212";// 最高年收益率
//		testInfo.CPFXDJ="一级";// 产品风险等级
//		testInfo.MJQSR="2012-10-11";// 募集起始日
//		testInfo.CPQXR="2011-11-11";// 产品气息日
//		testInfo.CPQX="101";// 产品期限
//
//		theProductInfo.add(testInfo);
//		theProductInfo.add(testInfo);
//		theProductInfo.add(testInfo);
//		theProductInfo.add(testInfo);
//		theProductInfo.add(testInfo);
	}

}

/**
 * 
 * @author JAY
 * 
 */
class HotProductAdapter extends BaseAdapter {
	private HotProductActivity activity;
	private List<ProductInfo> data;

	public HotProductAdapter(HotProductActivity a) {
		activity = a;
	}

	public int getCount() {
		if (data==null) {
			return 0;
		}
		return data.size();
	}

	public ProductInfo getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = View.inflate(activity, R.layout.item_hotproduct, null);
		
		TextView item_name= (TextView) vi.findViewById(R.id.item_hotproduct_name);//产品名称
		TextView item_level= (TextView) vi.findViewById(R.id.item_hotproduct_level);//风险等级
		TextView item_income = (TextView) vi.findViewById(R.id.item_hotproduct_income);//收益率
		TextView item_time= (TextView) vi.findViewById(R.id.item_hotproduct_time);//募集起始日
		TextView item_prodate= (TextView) vi.findViewById(R.id.item_hotproduct_prodate);//产品期限

		item_name.setText(data.get(position).PRD_NAME);
		String theLevel=NewCatevalue.getLabel(activity, NewCatevalue.product_cpfxdj, data.get(position).CPFXDJ);
		item_level.setText(theLevel);
		double theIcome=Tool.DoubleParse(data.get(position).ZGNSYL)*100;
		item_income.setText(""+theIcome);
		item_time.setText(data.get(position).MJQSR);
		item_prodate.setText(data.get(position).CPQX);

		return vi;
	}
	
	public void setData(List<ProductInfo> theData){
		this.data=theData;
	}

}
