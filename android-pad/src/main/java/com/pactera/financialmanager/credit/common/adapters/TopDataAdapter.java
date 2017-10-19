package com.pactera.financialmanager.credit.common.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dysen.common_res.common.utils.LogUtils;
import com.pactera.financialmanager.R;

import java.util.List;

/**
 * Created by dysen on 2017/7/5.
 */

public class TopDataAdapter extends RecyclerView.Adapter<TopDataAdapter.ViewHolder>{

	List<String> topList;
	Context mContext;

	public TopDataAdapter(Context context, List<String> list){
		mContext = context;
		topList = list;
	}
	/**
	 * item显示类型
	 * @param parent
	 * @param viewType
	 * @return
	 */
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		View view = LayoutInflater.from(mContext).inflate(R.layout.top_header_item, parent, false);
		//view.setBackgroundColor(Color.RED);
		ViewHolder viewHolder=new ViewHolder(view);
		return viewHolder;
	}
	/**
	 * 数据的绑定显示
	 * @param holder
	 * @param position
	 */
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {

		holder.item_tv.setText(topList.get(position));
		holder.item_tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				LogUtils.d("onclick ");
			}
		});
	}

	@Override
	public int getItemCount() {

		return topList.size();
	}

	//自定义的ViewHolder，持有每个Item的的所有界面元素
	public static class ViewHolder extends RecyclerView.ViewHolder {

		public TextView item_tv;

		public ViewHolder(View view){
			super(view);
			item_tv = (TextView)view.findViewById(R.id.top_header_item_tv);
		}
	}
}
