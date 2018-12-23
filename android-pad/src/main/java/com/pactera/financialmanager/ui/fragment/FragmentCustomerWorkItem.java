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
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.PullToRefreshLayout;
import com.pactera.financialmanager.ui.PullableListView;
import com.pactera.financialmanager.ui.model.CustEvent;
import com.pactera.financialmanager.ui.model.CustomerWork;
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
 * 这是 用户工作事项 对应的子fragment
 * 
 * 用户工作事项 目前只显示一个子项目
 */
public class FragmentCustomerWorkItem extends ParentFragment {

	private TextView tvTitle;
	private LinearLayout layLoading;
	private LinearLayout layNodata;
//	private ListView lvInfoShow;
	private PullToRefreshLayout ptrl;
	private PullableListView lvList;
	private CustomerWorkAdapter customerWorkAdapter;

	private boolean isForperson = true;
    private int offset = 1;
    private boolean isLoadmore = false;			// 是否上拉加载更多


    // 接口请求
	private HConnection HCon;
	private static final int INDEX_CUSORDER = 1;
	private List<CustomerWork> workForperson =  new ArrayList<CustomerWork>(); // 对个人
	private List<CustomerWork> workForcommon = new ArrayList<CustomerWork>(); // 对公

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
			// 营销活动
			case INDEX_CUSORDER:
				List<CustomerWork> resultList = (ArrayList<CustomerWork>) msg.obj;
				if (isForperson) {
					workForperson.addAll(resultList);
					setCusorderChange(workForperson);
				} else {
					workForcommon.addAll(resultList);
					setCusorderChange(workForcommon);
				}
				break;

			default:
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_cusworkitem, null);
		setupView(view);

		setTabTitle();
		sendRequestForInfo("15", "01");

		return view;
	}

	private void setupView(View view) {
		tvTitle = (TextView) view.findViewById(R.id.tv_title);
		layLoading = (LinearLayout) view.findViewById(R.id.lay_loading);
		layNodata = (LinearLayout) view.findViewById(R.id.lay_nodata);
//		lvInfoShow = (ListView) view.findViewById(R.id.lv_workplaceicon82);
        ptrl = ((PullToRefreshLayout) view.findViewById(R.id.refresh_view));
        lvList = (PullableListView) view.findViewById(R.id.lv_pulllist);


        customerWorkAdapter = new CustomerWorkAdapter();
//		lvInfoShow.setAdapter(customerWorkAdapter);
        lvList.setAdapter(customerWorkAdapter);
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

                if (isForperson) {
                    if (workForperson == null) {
                        sendRequestForInfo("15", "01");
                    } else {
                        setCusorderChange(workForperson);
                    }
                } else {
                    if (workForcommon == null) {
                        sendRequestForInfo("32", "02");
                    } else {
                        setCusorderChange(workForcommon);
                    }
                }
            }
        });
	}

	/**
	 * 设置是否为对个人状态
	 * 
	 * @param isForperson
	 */
	public void setIsForperson(boolean isForperson) {
		this.isForperson = isForperson;
		if (getActivity() == null) {
			return;
		}

		setTabTitle();
		if (isForperson) {
			if (workForperson == null) {
				sendRequestForInfo("15", "01");
			} else {
				setCusorderChange(workForperson);
			}
		} else {
			if (workForcommon == null) {
				sendRequestForInfo("32", "02");
			} else {
				setCusorderChange(workForcommon);
			}
		}
	}

	/**
	 * 设置二级标签标题
	 */
	private void setTabTitle() {
		// 消息提醒头集合（改变二级tab标题）
		List<CustEvent> events = WorkWarnFragment.tempEventsForPerson;
		if (!isForperson) {
			events = WorkWarnFragment.tempEventsForCommon;
		}

		if (events != null) {
			// 迭代获取标题名称
			for (CustEvent event : events) {
				String eventType = event.getEvent_type(); // 类型
				String eventName = event.getEvent_type_name(); // 名称
				// 营销活动
				if (eventType.equals("15") || eventType.equals("32")) {
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

	private void setCusorderChange(List<CustomerWork> cusorderList) {
		if (cusorderList == null || cusorderList.size() == 0) {
			hiddenLoading(true);
			return;
		}

		hiddenLoading(false);
		customerWorkAdapter.setInfolist(cusorderList);
		customerWorkAdapter.notifyDataSetChanged();
	}

	/**
	 * 方法请求
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
                    "&size=20"+"&offset="+offset
                    +"&spareOne=" + spareOne + "&spareTwo=" + spareTwo
					+ "&jsonData={staid:\"" + staid + "\"}";
			HCon = RequestInfo(this, Constants.requestType.Other, requestType,
					info, INDEX_CUSORDER, false);
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

		case INDEX_CUSORDER:
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
			List<CustomerWork> overList = null;
			String group = "";
			try {
				switch (connectionIndex) {
				case INDEX_CUSORDER:
					group = tmpJsonObject.getString("group");
					overList = JSON.parseArray(group, CustomerWork.class);
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
	private class CustomerWorkAdapter extends BaseAdapter {

		private List<CustomerWork> infolist;

		@Override
		public int getCount() {
			if (infolist != null) {
				return infolist.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			if (infolist != null && infolist.size() > 0) {
				return infolist.get(position);
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			HolderView holder;
			if (convertView == null) {
				convertView = View.inflate(getActivity(),
						R.layout.workplacelvtop_custwork, null);
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
			CustomerWork overdue = infolist.get(position);
			if (overdue != null) {
				// 编码转值
				String actType = NewCatevalue.getLabel(getActivity(),
						NewCatevalue.prjType, overdue.getACT_TYPE());
				holder.lvItemName.setText(overdue.getACT_NAME());
				holder.lvItemInfo2.setText(actType);
				holder.lvItemInfo3.setText(overdue.getSTART_DT());
				holder.lvItemInfo4.setText(overdue.getEND_DT());
			}
			holder.line1.setVisibility(View.GONE);
			holder.line2.setVisibility(View.GONE);
			holder.line3.setVisibility(View.GONE);

			return convertView;
		}

		public void setInfolist(List<CustomerWork> infolist) {
			this.infolist = infolist;
		}
	}

}
