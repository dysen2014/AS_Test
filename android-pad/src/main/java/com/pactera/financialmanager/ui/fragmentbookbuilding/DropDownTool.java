package com.pactera.financialmanager.ui.fragmentbookbuilding;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.util.Tool;

/**
 * 通用的下拉工具类，目前先用这个，查一下有没有更好的，再做修改
 */
public class DropDownTool {

	public static void showDropDownPop(Context context, final String[] infoStr,
			final String[] infoID, final EditText theEdit,
			final CallBackItemClickListener theCallBack) {
		int n=1;
		ListView lv = new ListView(context);
		lv.setBackgroundResource(R.drawable.listview_background);
		lv.setVerticalScrollBarEnabled(false);
		lv.setDividerHeight(0);
		List<String> sourceInfo = Tool.toList(infoStr);
		NumbersAdapter adapter = new NumbersAdapter(context, sourceInfo);
		lv.setAdapter(adapter);
		if (infoStr.length != 0) {
			n = infoStr.length;
		}
		if (n >= 9) {
			n = 10;
		}
		int height = Tool.dip2px(context, 38);
		height=height*n;
		Log.i("1111", "pop height:"+height);
		final PopupWindow popwin=new PopupWindow(lv, theEdit.getWidth(), height);
		popwin.setOutsideTouchable(true);
		popwin.setBackgroundDrawable(new BitmapDrawable());
		popwin.setFocusable(true);
		popwin.showAsDropDown(theEdit, 0, 0);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (theCallBack != null) {
					theCallBack.CallBackItemClick(infoID[position]);
				}
				theEdit.setText(infoStr[position]);
				popwin.dismiss();
			}
		});
	}

	public interface CallBackItemClickListener {
		public void CallBackItemClick(String spinnerID);
	}

}

class NumbersAdapter extends BaseAdapter {
	private Context context;
	private List<String> list;

	public NumbersAdapter(Context context, List<String> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		int size = list.size();
		if (size == 0) {
			list.add("             ");
		}
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NumberViewHolder holder = null;
		if (convertView == null) {
			holder = new NumberViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.user_item, null);
			holder.tvNumber = (TextView) convertView
					.findViewById(R.id.tv_number);
			convertView.setTag(holder);
		} else {
			holder = (NumberViewHolder) convertView.getTag();
		}
		holder.tvNumber.setText(list.get(position));
		return convertView;
	}

	class NumberViewHolder {
		public TextView tvNumber;
	}
}
