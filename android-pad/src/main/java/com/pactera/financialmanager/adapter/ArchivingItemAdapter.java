package com.pactera.financialmanager.adapter;

import java.util.List;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.model.ItemModel;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * 建档中显示元素的Adapter
 * 
 * @author JAY
 * @param <T>
 * 
 */
public class
ArchivingItemAdapter extends BaseAdapter {
	private Activity activity;
	private List<ItemModel> data;// 数据源
	private int index;// 显示几个元素
	private OnRightItemClickListener theListener;
	private int mRightWidth = 0;

	public ArchivingItemAdapter(Activity a, int rightWidth, List<ItemModel> d,
			int i, OnRightItemClickListener theListener) {
		this.activity = a;
		this.data = d;
		this.index = i;
		this.theListener = theListener;
		this.mRightWidth = rightWidth;
	}

	public int getCount() {
		return data.size();
	}

	public ItemModel getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public void setListViewHeight(ListView listView) {

		if (listView == null) {
			return;
		}
		ListAdapter listAdp = listView.getAdapter();
		if (listAdp == null) {
			return;
		}
		int totalH = 0;
		for (int i = 0; i < listAdp.getCount(); i++) {
			View listItem = listAdp.getView(i, null, listView);
			if (listItem == null) {
				return;
			}
			listItem.measure(0, 0);
			totalH += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalH
				+ (listView.getDividerHeight() * (listAdp.getCount() - 1));
		listView.setLayoutParams(params);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
		if (activity == null) {
			return vi;
		}
		if (index == 4) {
			vi = LayoutInflater.from(activity).inflate(R.layout.item_fouritem,
					null);
		} else if (index == 5) {
			vi = LayoutInflater.from(activity).inflate(R.layout.item_fiveitem,
					null);
		} else if (index == 6) {
			vi = LayoutInflater.from(activity).inflate(R.layout.item_sixitem,
					null);
		} else if (index == 7) {
			vi = LayoutInflater.from(activity).inflate(R.layout.item_sevenitem,
					null);
		} else if (index == 8) {
			vi = LayoutInflater.from(activity).inflate(R.layout.item_eightitem,
					null);
		}
		TextView[] item_info = new TextView[8];
		item_info[0] = (TextView) vi.findViewById(R.id.item_1);
		item_info[1] = (TextView) vi.findViewById(R.id.item_2);
		item_info[2] = (TextView) vi.findViewById(R.id.item_3);
		item_info[3] = (TextView) vi.findViewById(R.id.item_4);
		item_info[4] = (TextView) vi.findViewById(R.id.item_5);
		item_info[5] = (TextView) vi.findViewById(R.id.item_6);
		item_info[6] = (TextView) vi.findViewById(R.id.item_7);
		item_info[7] = (TextView) vi.findViewById(R.id.item_8);

		LinearLayout item_left = (LinearLayout) vi.findViewById(R.id.item_left);
		LinearLayout item_right = (LinearLayout) vi
				.findViewById(R.id.item_right);
		LinearLayout.LayoutParams lp1 = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		LinearLayout.LayoutParams lp2 = new LayoutParams(mRightWidth,
				LayoutParams.MATCH_PARENT);
		item_left.setLayoutParams(lp1);
		item_right.setLayoutParams(lp2);

		final ItemModel itemInfo = data.get(position);
		for (int i = 0; i < index && index <= 8; i++) {
			if (item_info[i] != null) {
				item_info[i].setText(itemInfo.theItemModel[i]);
			}
		}

		item_right.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (theListener != null) {
					theListener.onRightItemClick(v, itemInfo.pkid);
				}
			}
		});

		return vi;
	}

	public interface OnRightItemClickListener {
		void onRightItemClick(View v, String pkid);
	}
}
