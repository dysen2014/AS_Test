package com.pactera.financialmanager.ui.swipemenulistview;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pactera.financialmanager.R;

public class AdapterNewCommon extends BaseAdapter {
	private List list;//数据集合
	private int layoutId;//布局文件
	private Context context;
	
//	public AdapterNewCommon(List list, int layoutId, Context context) {
//		super();
//		this.list = list;
//		this.layoutId = layoutId;
//		this.context = context;
//	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=View.inflate(context, layoutId, null);
			new ViewHolder(convertView);
		}
		return null;
	}
	
	class ViewHolder {
		private TextView info1;
		private TextView info2;
		private TextView info3;
		private TextView info4;
		private TextView info5;
		private TextView info6;
		private TextView info7;
		private TextView info8;

		public ViewHolder(View view) {
//			info1 = (TextView) view.findViewById(R.id.info1);
//			info1 = (TextView) view.findViewById(R.id.info2);
//			info1 = (TextView) view.findViewById(R.id.info3);
//			info1 = (TextView) view.findViewById(R.id.info4);
//			info1 = (TextView) view.findViewById(R.id.info5);
//			info1 = (TextView) view.findViewById(R.id.info6);
//			info1 = (TextView) view.findViewById(R.id.info7);
//			info1 = (TextView) view.findViewById(R.id.info8);
			view.setTag(this);
		}
	}

}
