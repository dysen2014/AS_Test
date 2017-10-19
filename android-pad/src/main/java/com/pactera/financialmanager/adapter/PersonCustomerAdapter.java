package com.pactera.financialmanager.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.commontool.TextViewMarquee;
import com.pactera.financialmanager.ui.model.MycusPerson;
import com.pactera.financialmanager.util.NewCatevalue;

/**
 * 我的建档客户-对私客户
 * 
 * @author Administrator
 * 
 */
public class PersonCustomerAdapter extends BaseAdapter {
	private Context context;
	private List<MycusPerson> infolist;

	public PersonCustomerAdapter(Activity context) {
		super();
		this.context = context;
	}

	@Override
	public int getCount() {
		if (infolist != null) {
			return infolist.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (infolist != null && infolist.size() > 0) {
			return infolist.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		HolderView holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.myscus_person_item, null);
			holder = new HolderView();
			holder.lvItemInfo1 = (TextView) convertView
					.findViewById(R.id.lvitem_info1);
			holder.lvItemInfo2 = (TextView) convertView
					.findViewById(R.id.lvitem_info2);
			holder.lvItemInfo3 = (TextView) convertView
					.findViewById(R.id.lvitem_info3);
			holder.lvItemInfo4 = (TextView) convertView
					.findViewById(R.id.lvitem_info4);
			holder.lvItemInfo5 = (TextViewMarquee) convertView
					.findViewById(R.id.lvitem_info5);
			holder.lvItemInfo6 = (TextView) convertView
					.findViewById(R.id.lvitem_info6);
			holder.lvItemInfo7 = (TextView) convertView.findViewById(R.id.lvitem_info7);
			holder.view1 = (View) convertView.findViewById(R.id.view1);
			holder.view2 = (View) convertView.findViewById(R.id.view2);
			holder.view3 = (View) convertView.findViewById(R.id.view3);
			holder.view4 = (View) convertView.findViewById(R.id.view4);
			holder.view5 = (View) convertView.findViewById(R.id.view5);
			holder.view6 = (View) convertView.findViewById(R.id.view6);
			convertView.setTag(holder);
		} else {
			holder = (HolderView) convertView.getTag();
		}
		if (position % 2 != 0) {
			convertView.setBackgroundColor(context.getResources().getColor(
					R.color.lvtopbg));
		} else {
			convertView.setBackgroundColor(context.getResources().getColor(
					R.color.white));
		}

		// 单个item值
		MycusPerson cus = infolist.get(position);
		// 客户姓名
		String cusname = cus.getIDV_CHN_NM();
		if (TextUtils.isEmpty(cusname)) {
			cusname = "--";
		}
		// 性别

		 String sex = "";
		try{//"01"  男    "02""0" 女  此处把"04" "4"替换成"0""00"如果不行就把数据库  ""是什么性别？
			String GND_ID =  cus.getIDV_GND_ID();
		if (cus.getIDV_GND_ID().equals("01")||cus.getIDV_GND_ID().equals("02")) {
			sex = NewCatevalue.getLabel(context, NewCatevalue.CONTACT_SEX, GND_ID);
		}
		}catch (Exception ex){
			sex = "**";
		}
		if (TextUtils.isEmpty(sex)) {
			sex = "--";
		}
		// 客户级别(客户价值评级)
		String rageValue = NewCatevalue.getLabel(context,
				NewCatevalue.CUSTVALUELEVEL, cus.getCUST_VALUE_RATE());

		if (TextUtils.isEmpty(rageValue)) {
			rageValue = "--";
		}
		// 培植方向
		String cultivateDirct = NewCatevalue.getLabel(context,
				NewCatevalue.cultivate_dire, cus.getCULTIVATE_DIRCT());
		if (TextUtils.isEmpty(cultivateDirct)) {
			cultivateDirct = "--";
		}
		// 客户类型
		String cusType = NewCatevalue.getLabel(context, NewCatevalue.custClass,
				cus.getCUST_CLASS());
		if (TextUtils.isEmpty(cusType)) {
			cusType = "--";
		}

		//新定时间属性
		String archivingTime = cus.getARCHIVE_UPDATEDATE();
		// 修改时间
		String updateTime = cus.getLAST_UPDATE_TIME();
		if (TextUtils.isEmpty(updateTime)) {
			updateTime = "--";
		}
		// 建档日期
		String createTime =cus.getFOUND_DT();
	if(createTime.equals("")||createTime==null){
		createTime=updateTime;
	}else {
		createTime = cus.getFOUND_DT();
	}
		// 将获取到的数据进行展示
		holder.lvItemInfo1.setText(cusname);
		holder.lvItemInfo2.setText(sex);
		holder.lvItemInfo3.setText(rageValue);
		holder.lvItemInfo4.setText(cultivateDirct);
		holder.lvItemInfo5.setText(cusType);
		holder.lvItemInfo6.setText(createTime);
		holder.lvItemInfo7.setText(updateTime);
		// 隐藏分割线
		holder.view1.setVisibility(View.GONE);
		holder.view2.setVisibility(View.GONE);
		holder.view3.setVisibility(View.GONE);
		holder.view4.setVisibility(View.GONE);
		holder.view5.setVisibility(View.GONE);
		holder.view6.setVisibility(View.GONE);
		return convertView;
	}

	public void setInfolist(List<MycusPerson> infolist) {
		this.infolist = infolist;
	}

	class HolderView {
		private TextView lvItemInfo1;
		private TextView lvItemInfo2;
		private TextView lvItemInfo3;
		private TextView lvItemInfo4;
		private TextViewMarquee lvItemInfo5;
		private TextView lvItemInfo6;
		private TextView lvItemInfo7;
		private View view1;
		private View view2;
		private View view3;
		private View view4;
		private View view5;
		private View view6;
	}
}