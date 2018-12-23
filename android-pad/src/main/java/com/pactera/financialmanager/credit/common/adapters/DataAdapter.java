package com.pactera.financialmanager.credit.common.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.pactera.financialmanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dysen on 2017/7/11.
 */

public class DataAdapter extends BaseAdapter {

	Context mContext;
	private List<List<Object>> dataList = new ArrayList<>();

	// 申明一个点击事件接口变量
	private OnItemClickCallback callback = null;

	private ChildDataAdapter childDataAdapter;
	private LinearLayoutManager mLayoutManager;

	public DataAdapter(Context context, List<List<Object>> list) {

		mContext = context;
		dataList = list;
		childDataAdapter = new ChildDataAdapter(mContext);
	}

	public DataAdapter(Context context, List<List<Object>> list, OnItemClickCallback<Integer> onItemClickCallback) {

		mContext = context;
		dataList = list;
		childDataAdapter = new ChildDataAdapter(mContext);
		callback = onItemClickCallback;
	}

	@Override
	public int getCount() {
		if (dataList.size() > 0)
			return dataList.size();
		else
			return -1;
	}

	@Override
	public Object getItem(int position) {

		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	ViewHolder viewHolder;
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.data2_item, null);
			holder.recyclerView = (RecyclerView) convertView.findViewById(R.id.recycler_data);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		viewHolder = holder;

		if (position % 2 != 0) {
			convertView.setBackgroundResource(R.color.white);
		} else {
			convertView.setBackgroundResource(R.color.lvtopbg);
		}
		childDataAdapter = new ChildDataAdapter(mContext, dataList.get(position), new OnItemClickCallback<Object>() {
			@Override
			public void onClick(View view, Object info) {
				callback.onClick(view, position);
			}

			@Override
			public void onLongClick(View view, Object info) {

			}

			@Override
			public void onClick(View view, int position, int index) {

			}
		});

			childDataAdapter.setmList(dataList.get(position));
			setRecyclerView(holder.recyclerView).setAdapter(childDataAdapter);

		return convertView;
	}

	class ViewHolder {

		RecyclerView recyclerView;
	}

	private RecyclerView setRecyclerView(RecyclerView recyclerView) {

		//设置固定大小
		recyclerView.setHasFixedSize(true);
		//创建线性布局
		mLayoutManager = new LinearLayoutManager(mContext);
		//垂直方向
		mLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
		//添加分割线
//		recyclerView.addItemDecoration(new TestDecoration(this));
		//给RecyclerView设置布局管理器
		recyclerView.setLayoutManager(mLayoutManager);

		return recyclerView;
	}
}
