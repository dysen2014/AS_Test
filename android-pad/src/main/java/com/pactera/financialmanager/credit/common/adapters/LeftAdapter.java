package com.pactera.financialmanager.credit.common.adapters;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.pactera.financialmanager.R;

import java.util.ArrayList;
import java.util.List;

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
 * Created by dysen on 2017/7/11.
 */

public class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.ViewHolder> {

	private Context mContext;
		private List<String> mList = new ArrayList<>();

		public LeftAdapter(Context context, List<String> list){
			mContext = context;
			mList = list;
		}
		int index;

		// 申明一个点击事件接口变量
		private OnItemClickCallback callback = null;


		/**
		 * item显示类型
		 *
		 * @param parent
		 * @param viewType
		 * @return
		 */
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

			View view = LayoutInflater.from(mContext).inflate(R.layout.left_top_item, parent, false);
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

			viewHolder.tvLeft.setText(mList.get(position));
			if (position % 2 != 0) {
				viewHolder.tvLeft.setBackgroundResource(R.color.white);
			} else {
				viewHolder.tvLeft.setBackgroundResource(R.color.lvtopbg);
			}
		}

		@Override
		public int getItemCount() {

//		LogUtils.d("list 的大小：" + mList.size());
			return mList.size();
		}

		//自定义的ViewHolder，持有每个Item的的所有界面元素
		public static class ViewHolder extends RecyclerView.ViewHolder {

			public TextView tvLeft;

			public ViewHolder(View view) {
				super(view);
				tvLeft = (TextView) view.findViewById(R.id.left_top_item_tv);
			}
		}
}
