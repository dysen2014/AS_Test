package com.pactera.financialmanager.credit.common.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.pactera.financialmanager.R;

import java.util.List;

/**
 * Created by dysen on 2017/7/5.
 */

public class CommonTxtDataAdapter extends RecyclerView.Adapter<CommonTxtDataAdapter.ViewHolder>{

	List<String> dataList;
	Context mContext;
	// 申明一个点击事件接口变量
	private OnItemClickCallback callback = null;
	private int index;

	public CommonTxtDataAdapter(Context context, List<String> list){
		mContext = context;
		dataList = list;
	}
	public CommonTxtDataAdapter(Context context, List<String> list, OnItemClickCallback<Integer> onItemClickCallback){
		mContext = context;
		dataList = list;
		callback = onItemClickCallback;
	}

	/**
	 * item显示类型
	 * @param parent
	 * @param viewType
	 * @return
	 */
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		View view = LayoutInflater.from(mContext).inflate(R.layout.common_txt_item, parent, false);
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

		index = position;

		holder.item_tv.setText(dataList.get(position));
		for (int i = 0; i < dataList.size(); i++) {

		LogUtils.d(position+"onclick "+dataList.size()
		+"\n name;"+dataList.get(i));
		}
		holder.item_tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				LogUtils.d("onclick "+index);
				callback.onClick(view, index);
			}
		});
	}

	@Override
	public int getItemCount() {

		return dataList.size();
	}

	//自定义的ViewHolder，持有每个Item的的所有界面元素
	public static class ViewHolder extends RecyclerView.ViewHolder {

		public TextView item_tv;

		public ViewHolder(View view){
			super(view);
			item_tv = (TextView)view.findViewById(R.id.common_txt_item_tv);
		}
	}
}
