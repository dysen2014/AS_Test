package com.pactera.financialmanager.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.pactera.financialmanager.R;
import com.pactera.financialmanager.db.CatevalueDao;
import com.pactera.financialmanager.entity.CatevalueEntity;
import com.pactera.financialmanager.util.Tool;

import java.util.ArrayList;
import java.util.List;

/**
 * 下拉通用类
 * 
 * @author JAY
 * 
 */
public class SpinnerAdapter {
	private static PopupWindow popupWindow;
	private static ListView spinnerInfo;
	private static TheSpinnerAdapter infoAdapter;

	/**
	 * 显示下来菜单
	 * 
	 * @param activity
	 *            当前activity
	 * @param infoStr
	 *            数据源
	 * @param infoID
	 *            数据对应码值
	 * @param theEdit
	 *            当前输入框
	 * @param theCallBack
	 *            回调返回
	 */
	public static void showSelectDialog(Activity activity,
			final String[] infoStr, final String[] infoID,
			final TextView theEdit, final CallBackItemClickListener theCallBack) {
		LayoutInflater factory = LayoutInflater.from(activity);
		View popupView = factory.inflate(R.layout.list_stringitem, null);
		// 创建一个PopuWidow对象
		// if (popupWindow == null) {
		popupWindow = new PopupWindow(popupView, theEdit.getWidth(),
				LayoutParams.WRAP_CONTENT);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置焦点在弹窗上
		popupWindow.setFocusable(true);
		spinnerInfo = (ListView) popupView.findViewById(R.id.list_spinner);
		// }

		popupWindow.setTouchInterceptor(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
//				Log.i("1111111", "event.getAction()=" + event.getAction());
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					popupWindow.dismiss();
					return true;
				}
				return false;
			}
		});

		spinnerInfo.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (theCallBack != null) {
					theCallBack.CallBackItemClick(infoID[position]);
				}

				// TODO Auto-generated method stub
				theEdit.setText(infoStr[position]);
				// theEdit.setTag(infoID[position]);
				popupWindow.dismiss();
				popupWindow = null;
			}
		});
		
		if (popupWindow!=null) {
			popupWindow.dismiss();
		}
		infoAdapter = new TheSpinnerAdapter(activity, Tool.toList(infoStr));
		spinnerInfo.setAdapter(infoAdapter);
		spinnerInfo.setDividerHeight(0);

		ListAdapter listAdp = spinnerInfo.getAdapter();
		if (listAdp != null) {

			int totalH = 0;
			for (int i = 0; i < listAdp.getCount()&&i<=3; i++) {
				View listItem = listAdp.getView(i, null, spinnerInfo);
				listItem.measure(0, 0);
				totalH += listItem.getMeasuredHeight();
			}
			ViewGroup.LayoutParams params = spinnerInfo.getLayoutParams();
			
			params.height = totalH
					+ (spinnerInfo.getDividerHeight() * (listAdp.getCount() - 1));

			spinnerInfo.setLayoutParams(params);
		}

		// popupWindow.showAtLocation(theEdit, Gravity.BOTTOM, 0, 0);
		popupWindow.showAsDropDown(theEdit, 0, 0); // 显示

	}

	

	public static void showSelectDialog(Activity activity,
			String CatevalueCode,
			final TextView theEdit, final CallBackItemClickListener theCallBack) {
		LayoutInflater factory = LayoutInflater.from(activity);
		View popupView = factory.inflate(R.layout.list_stringitem, null);
		CatevalueDao theCatevalue=new CatevalueDao(activity);
		List<CatevalueEntity> list=new ArrayList<CatevalueEntity>();
		list=theCatevalue.getCatevalue(CatevalueCode);
		final String[] infoStr=new String[list.size()];
		final String[] infoID =new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			infoStr[i]=list.get(i).getLabel();
			infoID[i]=list.get(i).getValue();
		}
		theCatevalue.closeDataBase();
		// 创建一个PopuWidow对象
		// if (popupWindow == null) {
		popupWindow = new PopupWindow(popupView, theEdit.getWidth(),
				LayoutParams.WRAP_CONTENT);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置焦点在弹窗上
		popupWindow.setFocusable(true);
		spinnerInfo = (ListView) popupView.findViewById(R.id.list_spinner);
		// }

		popupWindow.setTouchInterceptor(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent event) {
				Log.i("1111111", "event.getAction()=" + event.getAction());
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					popupWindow.dismiss();
					return true;
				}
				return false;
			}
		});

		spinnerInfo.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (theCallBack != null) {
					theCallBack.CallBackItemClick(infoID[position]);
				}

				// TODO Auto-generated method stub
				theEdit.setText(infoStr[position]);
				// theEdit.setTag(infoID[position]);
				popupWindow.dismiss();
				popupWindow = null;
				
			}
		});
		popupWindow.dismiss();
		infoAdapter = new TheSpinnerAdapter(activity, Tool.toList(infoStr));
		spinnerInfo.setAdapter(infoAdapter);
		spinnerInfo.setDividerHeight(0);

		ListAdapter listAdp = spinnerInfo.getAdapter();
		if (listAdp != null) {

			int totalH = 0;
			for (int i = 0; i < listAdp.getCount()&&i<=3; i++) {
				View listItem = listAdp.getView(i, null, spinnerInfo);
				listItem.measure(0, 0);
				totalH += listItem.getMeasuredHeight();
			}
			ViewGroup.LayoutParams params = spinnerInfo.getLayoutParams();
			
			params.height = totalH
					+ (spinnerInfo.getDividerHeight() * (listAdp.getCount() - 1));
			spinnerInfo.setLayoutParams(params);
		}

		// popupWindow.showAtLocation(theEdit, Gravity.BOTTOM, 0, 0);
		popupWindow.showAsDropDown(theEdit, 0, 0); // 显示

	}

	
	
	
	// 回调
	public interface CallBackItemClickListener {
		public void CallBackItemClick(String spinnerID);
	}
}

class TheSpinnerAdapter extends BaseAdapter {
	private Activity activity;
	private List<String> data;// 数据源
	private static LayoutInflater inflater = null;

	public TheSpinnerAdapter(Activity a, List<String> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return data.get(position - 1);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = View.inflate(activity, R.layout.item_stringitem, null);

		TextView item_info = (TextView) vi.findViewById(R.id.item_1);

		String itemStr = data.get(position);
		item_info.setText(itemStr);
		return vi;
	}
}