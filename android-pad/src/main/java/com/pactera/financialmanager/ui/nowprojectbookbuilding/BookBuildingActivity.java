package com.pactera.financialmanager.ui.nowprojectbookbuilding;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentActivity;
/**
 * 客户建档的第一个界面（对公和对私用同一个）
 */
public class BookBuildingActivity extends ParentActivity implements OnClickListener {
	private  ImageView imgCity,imgArea,imgStreet,areaStyle,areaAt;
	private  Button  btnPerson,btnCommon;
	private  TextView  tvCity,tvArea,tvStreet,tvAreaStyle,tvAreaAt;
	
	private JSONObject mJsonObj;//把全国的省市区的信息以json的格式保存，解析完成后赋值为null
	private ArrayList<String> mProvinceDatas;//所有省的集合
	private Map<String,ArrayList<String>>  mCityDatasMap=new HashMap<String,ArrayList<String>>();
	private Map<String,ArrayList<String>>  mAreasDatasMap=new HashMap<String,ArrayList<String>>(); 
	private String mCurrentProviceName;//当前省的名称
	private String mCurrentAreaName ="";//当前区的名称

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bookbuilding);
		setupView();
		setListener();
		initJsonData();
	}

	private void setListener() {
		imgCity.setOnClickListener(this);
		imgArea.setOnClickListener(this);
		imgStreet.setOnClickListener(this);
		areaStyle.setOnClickListener(this);
		areaAt.setOnClickListener(this);
		btnPerson.setOnClickListener(this);
		btnCommon.setOnClickListener(this);
	}

	private void setupView() {
		imgCity=(ImageView)findViewById(R.id.img_city);
		imgArea=(ImageView)findViewById(R.id.img_area);
		imgStreet=(ImageView)findViewById(R.id.img_street);
		areaStyle=(ImageView)findViewById(R.id.img_areastyle);
		areaAt=(ImageView)findViewById(R.id.img_areaat);
		tvCity=(TextView)findViewById(R.id.tv_city);
		tvArea=(TextView)findViewById(R.id.tv_area);
		tvStreet=(TextView)findViewById(R.id.tv_street);
		
		btnPerson=(Button)findViewById(R.id.btn_person);
		btnCommon=(Button)findViewById(R.id.btn_commonality);
		
		tvAreaStyle=(TextView)findViewById(R.id.tv_areastyle);
		tvAreaAt=(TextView)findViewById(R.id.tv_areaat);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_city:
			showSelectCity();
			break;
		case R.id.img_area:
			
			break;
		case R.id.img_street:
			
			break;
		case R.id.img_areastyle:
			
			break;
		case R.id.btn_person:
			
			break;
		case R.id.btn_commonality:
			
			break;
		default:
			break;
		}
	}
	//弹出选择城市的下拉框
	private void showSelectCity() {
		
	}
	
	//将资源文件中的省市区信息读取到JSONObject对象中来，并对其进行解析，将相应的数据保存下来
	private void initJsonData() {
		try {
			InputStream is = getAssets().open("city.json");
			int len=-1;
			byte[] buf=new byte[1024];
			StringBuilder sb=new StringBuilder();
			if((len=is.read(buf))!=-1){
				sb.append(new String(buf, 0, len, "gb2312"));
			}
			mJsonObj=new JSONObject(sb.toString());
			if(mJsonObj!=null){
				JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
				mProvinceDatas=new ArrayList<String>(jsonArray.length());
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jo = (JSONObject) jsonArray.get(i);
					String province = jo.getString("p");// 省名字
					mProvinceDatas.add(i, province);
					JSONArray jsonArrayCity=null;
					try {
						jsonArrayCity=jo.getJSONArray("c");
					} catch (Exception e1) {
						continue;
					}
					ArrayList<String> cityDatas=new ArrayList<String>(jsonArrayCity.length());
					for (int j = 0; j < jsonArrayCity.length(); j++) {
						JSONObject joCitys=(JSONObject) jsonArrayCity.get(j);
						String cityName= joCitys.getString("n");//市名字
						cityDatas.add(j, cityName);
						JSONArray  jsonAreas=null;
						try {
							jsonAreas= joCitys.getJSONArray("a");
						} catch (Exception e2) {
							continue;
						}
						ArrayList<String> areas=new ArrayList<String>(jsonAreas.length());
						for (int k = 0; k < jsonAreas.length(); k++) {
							JSONObject joArea=(JSONObject) jsonAreas.get(k);
							String areaName = joArea.getString("s");//区域名称
							areas.add(k, areaName);
						}
						mAreasDatasMap.put(cityName, areas);
					}
					mCityDatasMap.put(province, cityDatas);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mJsonObj=null;
	}

}
