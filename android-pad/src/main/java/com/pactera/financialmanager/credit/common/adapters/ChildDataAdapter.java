package com.pactera.financialmanager.credit.common.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.pactera.financialmanager.R;

import java.util.List;

/**
 * Created by dysen on 2017/7/5.
 */

public class ChildDataAdapter extends RecyclerView.Adapter<ChildDataAdapter.ViewHolder> {

	List<Object> mList;
	Context mContext;
	int index;

	// 申明一个点击事件接口变量
	private OnItemClickCallback callback = null;

	public ChildDataAdapter(Context context) {
		mContext = context;
	}

	public ChildDataAdapter(Context context, List<Object> list) {
		mContext = context;
		mList = list;
	}

	public ChildDataAdapter(Context context, List<Object> list, OnItemClickCallback<Object> onItemClickCallback) {
		mContext = context;
		mList = list;
		callback = onItemClickCallback;
	}

	public void setmList(List<Object> list) {

//		for (int i = 0; i < list.size(); i++) {
//			LogUtils.d(list.size()+"list size :"+list.get(i));
//		}
		mList = list;
	}

	/**
	 * item显示类型
	 *
	 * @param parent
	 * @param viewType
	 * @return
	 */
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		View view = LayoutInflater.from(mContext).inflate(R.layout.data_item, parent, false);
		//view.setBackgroundColor(Color.RED);
		ViewHolder viewHolder = new ViewHolder(view);
		return viewHolder;
	}

	/**
	 * 数据的绑定显示
	 *
	 * @param viewHolder
	 * @param position
	 */
	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position) {

		index = position;
//		LogUtils.d(mList.get(position).getStr()+"\tlist size :"+position);

		viewHolder.item_tv.setText(mList.get(position)+"");

		// 给item中的指定控件添加点击事件（可以是item的子控件，也可以是itemView本身）
		viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				callback.onClick(view, mList.get(index));
//				LogUtils.d("onclick \t当前点击的位置："+position);
			}
		});
	}

	@Override
	public int getItemCount() {

//		LogUtils.d("list 的大小：" + mList.size());
		return mList.size();
	}

	//自定义的ViewHolder，持有每个Item的的所有界面元素
	public static class ViewHolder extends RecyclerView.ViewHolder {

		public TextView item_tv;

		public ViewHolder(View view) {
			super(view);
			item_tv = (TextView) view.findViewById(R.id.data_item_tv);
		}
	}


}
