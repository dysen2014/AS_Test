package com.pactera.financialmanager.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.PullToRefreshLayout;
import com.pactera.financialmanager.ui.PullableListView;
import com.pactera.financialmanager.ui.model.CustEvent;
import com.pactera.financialmanager.ui.model.CustomerBirthday;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户生日 的fragment
 */
public class FragmentCustomerBirthday extends ParentFragment {

	private TextView tvTitle;
	private LinearLayout layLoading;
	private LinearLayout layNodata;
//	private ListView lvInfoShow;
	private PullToRefreshLayout ptrl;
	private PullableListView lvList;
	private CusbirthdayAdapter cusbirthdayAdapter;

    private boolean isLoadmore = false;			// 是否上拉加载更多
    private int offset = 1;


    // 接口请求
	private HConnection HCon;
	private static final int INDEX_CUSBIRTHDAY = 1;
	// 客户生日
	private List<CustomerBirthday> cusbirthdayForperson = new ArrayList<CustomerBirthday>(); // 对个人

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
            if(isLoadmore){
                // 隐藏地步
                ptrl.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            switch (msg.arg1) {
			// 客户生日
			case INDEX_CUSBIRTHDAY:
                cusbirthdayForperson.addAll((List<CustomerBirthday>) msg.obj);
				setCusorderChange(cusbirthdayForperson);
				break;

			default:
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_customerbirthday, null);
		setupView(view);

		setTitle();
		sendRequestForInfo("10", "01");
		return view;
	}

	private void setupView(View view) {
		tvTitle = (TextView) view.findViewById(R.id.tv_title);
		layLoading = (LinearLayout) view.findViewById(R.id.lay_loading);
		layNodata = (LinearLayout) view.findViewById(R.id.lay_nodata);
//		lvInfoShow = (ListView) view.findViewById(R.id.lv_workplaceicon72);
        ptrl = ((PullToRefreshLayout) view.findViewById(R.id.refresh_view));
        lvList = (PullableListView) view.findViewById(R.id.lv_pulllist);


        cusbirthdayAdapter = new CusbirthdayAdapter();
//		lvInfoShow.setAdapter(cusbirthdayAdapter);
        lvList.setAdapter(cusbirthdayAdapter);
        lvList.setPullstatus(false, true);
        ptrl.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                // 上拉加载更多
                isLoadmore = true;
                offset++;

                sendRequestForInfo("10", "01");
            }
        });
	}

	private void setTitle() {
		List<CustEvent> events = WorkWarnFragment.tempEventsForPerson;
		if (events != null) {
			// 迭代获取标题名称
			for (CustEvent event : events) {
				String eventType = event.getEvent_type(); // 类型
				String eventName = event.getEvent_type_name(); // 名称

				if (eventType.equals("10")) {
					tvTitle.setText(eventName);
				}
			}
		}
	}

	/**
	 * 显示进度条
	 */
	private void loading() {
        layLoading.setVisibility(View.VISIBLE);
        //lvProInfo.setVisibility(View.GONE);
        if(offset == 1){
            ptrl.setVisibility(View.GONE);
        }
        layNodata.setVisibility(View.GONE);
	}

	/**
	 * 隐藏进度条
	 * 
	 * @param noDatas
	 */
	private void hiddenLoading(boolean noDatas) {
        layLoading.setVisibility(View.GONE);
        if (noDatas) {
            layNodata.setVisibility(View.VISIBLE);
            //lvProInfo.setVisibility(View.GONE);
            ptrl.setVisibility(View.GONE);
        } else {
            layNodata.setVisibility(View.GONE);
            //lvProInfo.setVisibility(View.VISIBLE);
            ptrl.setVisibility(View.VISIBLE);
        }
	}

	/**
	 * 请求数据
	 * 
	 * @param spareOne
	 * @param spareTwo
	 */
	private void sendRequestForInfo(String spareOne, String spareTwo) {
		if (Tool.haveNet(getActivity())) {
			loading();
			String staid = LogoActivity.user.getStaId();
			String requestType = InterfaceInfo.PRODATA_Get;
			String info =
                    "&size=20"+"&offset="+offset+
                    "&spareOne=" + spareOne + "&spareTwo=" + spareTwo
					+ "&jsonData={staid:\"" + staid + "\"}";

			HCon = RequestInfo(this, Constants.requestType.Other, requestType,
					info, INDEX_CUSBIRTHDAY, false);
		}
	}

	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);

		HResponse res = null;
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			hiddenLoading(true);
			Toast.makeText(getActivity(), "请求失败.", Toast.LENGTH_SHORT).show();
			break;

		case INDEX_CUSBIRTHDAY:
			res = HCon.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			readJson(res.dataJsonObject, connectionIndex);
			break;
		}
	}

	/**
	 * 解析接口数据
	 * 
	 * @param tmpJsonObject
	 * @param connectionIndex
	 */
	private void readJson(JSONObject tmpJsonObject, int connectionIndex) {
		String retCode = "";
		if (tmpJsonObject.has("retCode")) { // 返回标志
			try {
				retCode = tmpJsonObject.getString("retCode");
			} catch (JSONException e1) {
				e1.printStackTrace();
				return;
			}
		}

		// 获取接口成功
		if (retCode.equals("0000")) {
			Message msg = new Message();
			msg.arg1 = connectionIndex;

			// 结果集合
			List<CustomerBirthday> overList = null;
			String group = "";
			try {
				switch (connectionIndex) {
				case INDEX_CUSBIRTHDAY:
					group = tmpJsonObject.getString("group");
					overList = JSON.parseArray(group, CustomerBirthday.class);
					msg.obj = overList;
					break;

				default:
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			handler.sendMessage(msg);
		} else {
			hiddenLoading(true);
			Toast.makeText(getActivity(), "请求失败! 错误代码:" + retCode,
					Toast.LENGTH_SHORT).show();
		}
	}

	private void setCusorderChange(List<CustomerBirthday> cusorderList) {
		if (cusorderList == null || cusorderList.size() == 0) {
			hiddenLoading(true);
			return;
		}

		hiddenLoading(false);
		cusbirthdayAdapter.setInfolist(cusorderList);
		cusbirthdayAdapter.notifyDataSetChanged();
	}

	public void setTestDatas() {
		List<CustomerBirthday> list = new ArrayList<CustomerBirthday>();
		for (int i = 0; i < 9; i++) {
			CustomerBirthday entity = new CustomerBirthday("PN101093020" + i,
					"张三", "1982-10-10", "重点培植", "103,230,000", "HU110293");
			list.add(entity);
		}
		cusbirthdayAdapter.setInfolist(list);
		cusbirthdayAdapter.notifyDataSetChanged();
	}

	private static class HolderView {
		private TextView lvItemName;
		private TextView lvItemInfo2;
		private TextView lvItemInfo3;
		private TextView lvItemInfo4;
		private View line1;
		private View line2;
		private View line3;
	}

	/**
	 * 账户余额适配器
	 * 
	 * @author Administrator
	 */
	private class CusbirthdayAdapter extends BaseAdapter {

		private List<CustomerBirthday> infolist;

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
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			HolderView holder;
			if (convertView == null) {
				convertView = View.inflate(getActivity(),
						R.layout.workplacelvtop_cusorder, null);
				holder = new HolderView();
				holder.lvItemName = (TextView) convertView
						.findViewById(R.id.lvitem_name);
				holder.lvItemInfo2 = (TextView) convertView
						.findViewById(R.id.lvitem_info2);
				holder.lvItemInfo3 = (TextView) convertView
						.findViewById(R.id.lvitem_info3);
				holder.lvItemInfo4 = (TextView) convertView
						.findViewById(R.id.lvitem_info4);
				holder.line1 = (View) convertView.findViewById(R.id.line1);
				holder.line2 = (View) convertView.findViewById(R.id.line2);
				holder.line3 = (View) convertView.findViewById(R.id.line3);

				convertView.setTag(holder);
			} else {
				holder = (HolderView) convertView.getTag();
			}
			if (position % 2 != 0) {
				convertView.setBackgroundColor(getResources().getColor(
						R.color.lvtopbg));
			} else {
				convertView.setBackgroundColor(getResources().getColor(
						R.color.white));
			}
			// 将获取到的数据进行展示
			CustomerBirthday overdue = infolist.get(position);
			if (overdue != null) {
				String custLevel = NewCatevalue.getLabel(getActivity(),
						NewCatevalue.ent_cust_value_level,
						overdue.getCUST_LEVEL());
				holder.lvItemName.setText(overdue.getCST_NAME());
				holder.lvItemInfo2.setText(overdue.getBIRTHDAY());
				holder.lvItemInfo3.setText(custLevel);
				holder.lvItemInfo4.setText(Tool.setFormatValue(overdue.getDEPSOIT_BAL()));
			}
			holder.line1.setVisibility(View.GONE);
			holder.line2.setVisibility(View.GONE);
			holder.line3.setVisibility(View.GONE);

			return convertView;
		}

		public void setInfolist(List<CustomerBirthday> infolist) {
			this.infolist = infolist;
		}
	}
}
