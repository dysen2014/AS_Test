package com.pactera.financialmanager.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.model.MycusCommon;
import com.pactera.financialmanager.util.NewCatevalue;

/**
 * 我的建档客户-对公客户
 * @author Administrator
 *
 */
public class CommonCustomerAdapter extends BaseAdapter {
	
	private List<MycusCommon> infolist;
	private Context context;

	public CommonCustomerAdapter(Context context) {
		super();
		this.context = context;
	}

	@Override
	public int getCount() {
		if (infolist != null && infolist.size() > 0) {
			return infolist.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return infolist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HolderView holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.myscus_common_item, null);
			holder = new HolderView();
			holder.lvItemInfo1 = (TextView) convertView.findViewById(R.id.lvitem_info1);
			holder.lvItemInfo2 = (TextView) convertView.findViewById(R.id.lvitem_info2);
			holder.lvItemInfo3 = (TextView) convertView.findViewById(R.id.lvitem_info3);
			holder.lvItemInfo4 = (TextView) convertView.findViewById(R.id.lvitem_info4);
			holder.lvItemInfo5 = (TextView) convertView.findViewById(R.id.lvitem_info5);
			holder.lvItemInfo6 = (TextView) convertView.findViewById(R.id.lvitem_info6);
			holder.lvItemInfo7 = (TextView) convertView.findViewById(R.id.lvitem_info7);
			holder.lvItemInfo8 = (TextView) convertView.findViewById(R.id.lvitem_info8);
			holder.view1 = (View) convertView.findViewById(R.id.view1);
			holder.view2 = (View) convertView.findViewById(R.id.view2);
			holder.view3 = (View) convertView.findViewById(R.id.view3);
			holder.view4 = (View) convertView.findViewById(R.id.view4);
			holder.view5 = (View) convertView.findViewById(R.id.view5);
			holder.view6 = (View) convertView.findViewById(R.id.view6);
			holder.view7 = (View) convertView.findViewById(R.id.view7);
			convertView.setTag(holder);
		} else {
			holder = (HolderView) convertView.getTag();
		}
		if (position % 2 != 0) {
			convertView.setBackgroundColor(context.getResources().getColor(R.color.lvtopbg));
		} else {
			convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
		}
		
		// 将获取到的数据进行展示
		MycusCommon mycus = infolist.get(position);
		//企业名称
		String companyName = mycus.getORG_LGL_NM(); 
		if(TextUtils.isEmpty(companyName)){
			companyName = "--";
		}
		//企业规模
		String orgSize = NewCatevalue.getLabel(context,
				NewCatevalue.enterScale, mycus.getORG_SCL_TP_ID());
		if(TextUtils.isEmpty(orgSize)){
			orgSize = "--";
		}
		// 企业性质
		String companyPeroperty = NewCatevalue.getLabel(context,
				NewCatevalue.OrgType, mycus.getCOMPANY_PEROPERTY());
		if(TextUtils.isEmpty(companyPeroperty)){
			companyPeroperty = "--";
		}
		// 客户级别
		String custValue = NewCatevalue.getLabel(context,
				NewCatevalue.ent_cust_value_level, mycus.getCUST_VALUE());
		if(TextUtils.isEmpty(custValue)){
			custValue = "--";
		}
		// 培植方向
		String cultivateDirct = NewCatevalue.getLabel(context,
				NewCatevalue.cultivate_dire, mycus.getCULTIVATE_DIRCT());
		if(TextUtils.isEmpty(cultivateDirct)){
			cultivateDirct = "--";
		}
		// 客户类型
		String custType = NewCatevalue.getLabel(context, NewCatevalue.custClassType,
				mycus.getCUST_TYPE());
		if(TextUtils.isEmpty(custType)){
			custType = "--";
		}
		String  archiveTime = mycus.getARCHIVE_UPDATEDATE();

		// 修改时间
		String updateTime = mycus.getLAST_UPDATE_TIME();

		if(TextUtils.isEmpty(updateTime)){
			if (archiveTime.equals("")){
				updateTime = "--";
			}
			updateTime = archiveTime;
		}
		// 建档时间
		String createTime = mycus.getCREATE_TIME();
		if(TextUtils.isEmpty(createTime)){
			createTime = "--";
		}

		if (!updateTime.equals("")&&!updateTime.equals("--")){
			createTime = updateTime;
		}else {
			if (!createTime.equals("")){
				createTime = "--";
			}
			createTime = mycus.getCREATE_TIME();
		}
		holder.lvItemInfo1.setText(companyName);
		holder.lvItemInfo2.setText(orgSize);
		holder.lvItemInfo3.setText(companyPeroperty);
		holder.lvItemInfo4.setText(custValue);
		holder.lvItemInfo5.setText(cultivateDirct);
		holder.lvItemInfo6.setText(custType);
		holder.lvItemInfo7.setText(createTime);
		holder.lvItemInfo8.setText(updateTime);
		holder.view1.setVisibility(View.GONE);
		holder.view2.setVisibility(View.GONE);
		holder.view3.setVisibility(View.GONE);
		holder.view4.setVisibility(View.GONE);
		holder.view5.setVisibility(View.GONE);
		holder.view6.setVisibility(View.GONE);
		holder.view7.setVisibility(View.GONE);
		return convertView;
	}

	public void setInfolist(List<MycusCommon> infolist) {
		this.infolist = infolist;
	}
	

	private class HolderView {
		private TextView lvItemInfo1;
		private TextView lvItemInfo2;
		private TextView lvItemInfo3;
		private TextView lvItemInfo4;
		private TextView lvItemInfo5;
		private TextView lvItemInfo6;
		private TextView lvItemInfo7;
		private TextView lvItemInfo8;
		private View view1;
		private View view2;
		private View view3;
		private View view4;
		private View view5;
		private View view6;
		private View view7;
	}

	
}