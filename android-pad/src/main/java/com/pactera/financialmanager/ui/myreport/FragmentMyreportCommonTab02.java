package com.pactera.financialmanager.ui.myreport;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.model.Organization;
import com.pactera.financialmanager.ui.model.Property;

/**
 * 我的业绩-对公-机构和排名
 * @author dg
 */
public class FragmentMyreportCommonTab02 extends ParentFragment implements OnClickListener{

	private RadioButton rbtnItempro1, rbtnItempro2, rbtnItempro3;
	private ListView lvProperty;
	private LinearLayout layTopTitle;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_myreport_common_tab02, null);
		findView(view);
		bindOnclick();
		
		setLvtopTitle(R.layout.myreport_common_tab02_item);
		addTestData1();
		return view;
	}
	
	private void findView(View view){
		rbtnItempro1 = (RadioButton) view.findViewById(R.id.rbt_itempro1);
		rbtnItempro2 = (RadioButton) view.findViewById(R.id.rbt_itempro2);
		rbtnItempro3 = (RadioButton) view.findViewById(R.id.rbt_itempro3);
		layTopTitle = (LinearLayout) view.findViewById(R.id.ll_lvtop);
		lvProperty = (ListView) view.findViewById(R.id.lv_property);
	}
	
	private void bindOnclick(){
		rbtnItempro1.setOnClickListener(this);
		rbtnItempro2.setOnClickListener(this);
		rbtnItempro3.setOnClickListener(this);
	}
	
	private void setLvtopTitle(int includeLayID) {
		layTopTitle.removeAllViews();
		// listview顶部的标题栏
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		View view = LayoutInflater.from(getActivity()).inflate(includeLayID, null);
		LinearLayout llWorkPlaceLvTopItem = (LinearLayout) view.findViewById(R.id.ll_myreport_person_tab01_item1);
		layTopTitle.addView(llWorkPlaceLvTopItem, params);
	}
	
	private void addTestData1(){
		// 给ListView添加值
		List<Property> listProperties = new ArrayList<Property>();
		for (int i = 0; i < 10; i++) {
			Property property = new Property();
			property.setProperty("1,069,000");
			property.setCkyejz("1.27%");
			property.setDkye("1,120,000");
			property.setDkyejz("2.56%");
			property.setLcye("2,134,000");
			listProperties.add(property);
		}
		PropertyAdapter adapter = new PropertyAdapter(listProperties);
		lvProperty.setAdapter(adapter);
		
	}
	
	class HolderView{
		private TextView tvValue1;
		private TextView tvValue2;
		private TextView tvValue3;
		private TextView tvValue4;
		private TextView tvValue5;
	}
	
	/**
	 * ListView 适配器
	 * @author Administrator
	 *
	 */
	class PropertyAdapter extends BaseAdapter{

		private List<Property> listProperties;
		
		public PropertyAdapter(List<Property> listProperties) {
			super();
			this.listProperties = listProperties;
		}

		@Override
		public int getCount() {
			return listProperties.size();
		}

		@Override
		public Object getItem(int arg0) {
			return listProperties.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			Property property = listProperties.get(position);
			
			HolderView holder;
			if (convertView == null) {
				convertView = View.inflate(getActivity(), R.layout.myreport_common_tab02_item, null);
				holder = new HolderView();
				holder.tvValue1 = (TextView) convertView.findViewById(R.id.lvitem_info1);
				holder.tvValue2 = (TextView) convertView.findViewById(R.id.lvitem_info2);
				holder.tvValue3 = (TextView) convertView.findViewById(R.id.lvitem_info3);
				holder.tvValue4 = (TextView) convertView.findViewById(R.id.lvitem_info4);
				holder.tvValue5 = (TextView) convertView.findViewById(R.id.lvitem_info5);
				convertView.setTag(holder);
			}else{
				holder = (HolderView) convertView.getTag();
			}
			// 设置单行背景颜色
			if (position % 2 != 0) {
				convertView.setBackgroundColor(getResources().getColor(R.color.lvtopbg));
			} else {
				convertView.setBackgroundColor(getResources().getColor(R.color.white));
			}
			
			// 设置值
			holder.tvValue1.setText(property.getProperty());
			holder.tvValue2.setText(property.getCkyejz());
			holder.tvValue3.setText(property.getDkye());
			holder.tvValue4.setText(property.getDkyejz());
			holder.tvValue5.setText(property.getLcye());
			
			return convertView;
		}
	}

	
	/**
	 * ListView 适配器
	 * @author Administrator
	 *
	 */
	class OrganizationAdapter extends BaseAdapter{

		private List<Organization> listProperties;

		@Override
		public int getCount() {
			if(listProperties == null){
				return 0;
			}
			return listProperties.size();
		}

		@Override
		public Object getItem(int arg0) {
			if(listProperties == null){
				return null;
			}
			return listProperties.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			Organization property = listProperties.get(position);
			
			HolderView holder;
			if (convertView == null) {
				convertView = View.inflate(getActivity(), R.layout.myreport_common_tab02_item3, null);
				holder = new HolderView();
				holder.tvValue1 = (TextView) convertView.findViewById(R.id.lvitem_info1);
				holder.tvValue2 = (TextView) convertView.findViewById(R.id.lvitem_info2);
				holder.tvValue3 = (TextView) convertView.findViewById(R.id.lvitem_info3);
				holder.tvValue4 = (TextView) convertView.findViewById(R.id.lvitem_info4);
				holder.tvValue5 = (TextView) convertView.findViewById(R.id.lvitem_info5);
				convertView.setTag(holder);
			}else{
				holder = (HolderView) convertView.getTag();
			}
			// 设置单行背景颜色
			if (position % 2 != 0) {
				convertView.setBackgroundColor(getResources().getColor(R.color.lvtopbg));
			} else {
				convertView.setBackgroundColor(getResources().getColor(R.color.white));
			}
			
			// 设置值
//			holder.tvValue1.setText(property.getProperty());
//			holder.tvValue2.setText(property.getCkyejz());
//			holder.tvValue3.setText(property.getDkye());
//			holder.tvValue4.setText(property.getDkyejz());
//			holder.tvValue5.setText(property.getLcye());
			
			return convertView;
		}

		public void setListProperties(List<Organization> listProperties) {
			this.listProperties = listProperties;
		}
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rbt_itempro1:
			setLvtopTitle(R.layout.myreport_common_tab02_item);
			addTestData1();
			break;
			
		case R.id.rbt_itempro2:
			setLvtopTitle(R.layout.myreport_common_tab02_item);
			addTestData1();
			break;
			
		case R.id.rbt_itempro3:
			setLvtopTitle(R.layout.myreport_common_tab02_item3);
			OrganizationAdapter adapter = new OrganizationAdapter();
			lvProperty.setAdapter(adapter);
			break;
			
		default:
			break;
		}
	}

}
