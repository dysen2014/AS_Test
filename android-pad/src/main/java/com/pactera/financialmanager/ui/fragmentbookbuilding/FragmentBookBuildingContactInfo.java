package com.pactera.financialmanager.ui.fragmentbookbuilding;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.callback.BookbuildingContactCallback;
import com.pactera.financialmanager.callback.WorkPlaceItemChange;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.PullableListView;
import com.pactera.financialmanager.ui.SwipeListView;
import com.pactera.financialmanager.ui.bookbuildingfrocompany.ActivityBaseInfo;
import com.pactera.financialmanager.ui.model.BaseCustomerInfo;
import com.pactera.financialmanager.ui.model.ContantsInfoEntity;
import com.pactera.financialmanager.ui.model.SevenFieldEntity;
import com.pactera.financialmanager.ui.newcommonadapter.CommonAdapter;
import com.pactera.financialmanager.ui.newcommonadapter.CommonAdapter1;
import com.pactera.financialmanager.ui.newcommonadapter.CommonViewHolder;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HRequest;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.Tool;

/**
 * 第二大步，联络信息的fragment  目前这一步去掉。
 */
public class FragmentBookBuildingContactInfo extends ParentFragment implements
		OnClickListener {
	private SwipeListView lvContactInfo;
	private Button btnContactInfoNext, btnContactCreate;
	private List<SevenFieldEntity> list;
	private DialogFragmentCreateContactInfo dialog;
	private FragmentManager fm;
	private final int companyContantCheckFlag = 93;
	private HConnection companyContantHConn;
	private List<ContantsInfoEntity> listContants;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_contactinfocom, null);
		setupView(view);
		fm = getFragmentManager();
		listContants=new ArrayList<ContantsInfoEntity>();
		setListener();
		sendRequestForContantInfo();
		// setDefaultInfo();
		return view;
	}

	private void sendRequestForContantInfo() {
		HRequest hrObj = null;
		String requestStr;
		String custId = ActivityBaseInfo.custID;
		if (Tool.haveNet(getActivity())) {
			requestStr = Tool.getRequestStr("T011112", custId);
			requestStr = requestStr + "&size=" + "10" + "&offset=" + "0";
			hrObj = new HRequest(requestStr, "GET");
			Log.i("1111", "2222contants:" + requestStr);
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyContantHConn = new HConnection(this, hrObj,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyContantHConn.setIndex(companyContantCheckFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			toast("当前无网络连接！");
		}
	}
	//创建   // 这里缺一个电话
	private void createContantsInfo(String info1, String info2,
			String info3, String info4, String info5, String info6,
			String info7) {
		HRequest hrObj = null;
		String requestStr;
		String custId = ActivityBaseInfo.custID;
		if (Tool.haveNet(getActivity())) {
			requestStr = Tool.getRequest("T011111");
			requestStr = requestStr + "&jsonData={CUSTID:\"" + custId
					+ "\",KEY_MEM_NAME:\"" // 成员姓名
					+ info1 + "\",REL_ID:\"" //关联客户号
					+ info2 + "\",REL_TYPE:\"" //关系类型
					+ info3 + "\",KEY_CERT_TYPE:\"" //关键人证件类型
					+ info4 + "\",KEY_CERT_NO:\"" //证件号码
					+ info5 + "\",POSITION:\"" // 关键人信息
					+ info6 + "\",DES:\"" // 备注
					+ info7 + "\"}";
			

			hrObj = new HRequest(requestStr, "GET");
			Log.i("1111", "2222contants:" + requestStr);
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyContantHConn = new HConnection(this, hrObj,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyContantHConn.setIndex(companyContantCheckFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			toast("当前无网络连接！");
		}
	}

	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		switch (connectionIndex) {
		case companyContantCheckFlag:// 查询
			HResponse companyContantRes = companyContantHConn
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (companyContantRes == null
					|| companyContantRes.dataJsonObject == null) {
				return;
			}
			JSONObject tmpJsonGdObject = companyContantRes.dataJsonObject;
			Log.i("1111",
					"....companychuangjianguz....:"
							+ tmpJsonGdObject.toString());
			readContantJson(tmpJsonGdObject, connectionIndex);
			break;

		default:
			break;
		}
	}

	private void readContantJson(JSONObject tmpJsonGdObject, int connectionIndex) {
		String retCode=null;
		try {
			if (tmpJsonGdObject.has("retCode")) {
				retCode = tmpJsonGdObject.getString("retCode");
			}
			
			if("0000".equals(retCode)){
				switch (connectionIndex) {
				case companyContantCheckFlag:
					listContants.clear();
					if(tmpJsonGdObject.has("group")){
						String contantStr = tmpJsonGdObject.getString("group");
						if(TextUtils.isEmpty(contantStr)){
							Log.i("1111","....contantretinfo:"+ "返回数据为空");
							return;
						}else{
							listContants=JSON.parseArray(contantStr, ContantsInfoEntity.class);
							Log.i("1111", "contatnsret:"+listContants.toString());
							showContantRetInfo();
						}
					}
					break;

				default:
					break;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 这个联系人信息的适配器里的地方要改的，现在先把界面改一下，稍后再来改这个
	 */
	private void showContantRetInfo() {
		final int mRightWidth = lvContactInfo.getRightViewWidth();
		final CommonAdapter1 adapterTest = new CommonAdapter1<ContantsInfoEntity>(
				getActivity(), listContants, R.layout.lv_sevenfieldcontactinfo) {

			@Override
			public void convert(CommonViewHolder helper, ContantsInfoEntity item,
					final int i) {
				TextView info1 = helper.getView(R.id.tv_info1);
				TextView info2 = helper.getView(R.id.tv_info2);
				TextView info3 = helper.getView(R.id.tv_info3);
				TextView info4 = helper.getView(R.id.tv_info4);
				TextView info5 = helper.getView(R.id.tv_info5);
				TextView info6 = helper.getView(R.id.tv_info6);
				TextView info7 = helper.getView(R.id.tv_info7);
				LinearLayout llLeft = helper.getView(R.id.item_left);
				RelativeLayout rlRight = helper.getView(R.id.item_right);
				TextView tvRight = helper.getView(R.id.item_right_txt);

				LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
						mRightWidth, LayoutParams.MATCH_PARENT);
				llLeft.setLayoutParams(lp1);
				rlRight.setLayoutParams(lp2);
				rlRight.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Log.i("positon....", "...." + i);
						listContants.remove(i);
						notifyDataSetChanged();
						lvContactInfo.hiddenShowView();
						setFuzaiListViewHeight(lvContactInfo, listContants);
					}
				});
				info1.setText(item.getKEY_MEM_NAME());
				info2.setText(item.getREL_ID());
				info3.setText(item.getREL_TYPE());
				info4.setText(item.getKEY_CERT_TYPE());
				info5.setText(item.getKEY_CERT_NO());
//				info6.setText(item.getHAND_CREDIT());少一个联系电话
				info7.setText(item.getDES());
			}
		};
		lvContactInfo.setPullstatus(false, false);// 设置是否可以上下拉动
		lvContactInfo.setAdapter(adapterTest);// 显示数据
		setFuzaiListViewHeight(lvContactInfo, listContants);
		lvContactInfo.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				toast(position + "条目点击");
			}
		});
	}
	private void setFuzaiListViewHeight(ListView listView, List list) {
		int count1 = list.size();
		ListAdapter mAdapter = listView.getAdapter();
		if (mAdapter == null) {
			return;
		}
		int totalHeight = 0;
		int count = mAdapter.getCount();
		for (int i = 0; i < count; i++) {
			View mView = mAdapter.getView(i, null, listView);
			mView.measure(
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			// mView.measure(0, 0);
			totalHeight += mView.getMeasuredHeight();
			Log.w("HEIGHT" + i, String.valueOf(totalHeight));
		}
		if (count1 == 0) {
			totalHeight = Tool.dip2px(getActivity(), 45);
			ContantsInfoEntity entity = new ContantsInfoEntity("", "",
					"", "", "", "", "","", "", "", "","");
			listContants.add(entity);
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (mAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}
	private void setListViewHeight1(ListView listView, List list) {
		int count1 = list.size();
		ListAdapter mAdapter = listView.getAdapter();
		if (mAdapter == null) {
			return;
		}
		int totalHeight = 0;
		int count = mAdapter.getCount();
		for (int i = 0; i < count; i++) {
			View mView = mAdapter.getView(i, null, listView);
			mView.measure(
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			// mView.measure(0, 0);
			totalHeight += mView.getMeasuredHeight();
			Log.w("HEIGHT" + i, String.valueOf(totalHeight));
		}
		if (count1 == 0) {
			totalHeight = Tool.dip2px(getActivity(), 45);
			ContantsInfoEntity entity=new ContantsInfoEntity("", "","","", "", "", "", "","", "", "", "");
			listContants.add(entity);
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (mAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

	private void setDefaultInfo() {
		CommonAdapter<SevenFieldEntity> adapter = new CommonAdapter<SevenFieldEntity>(
				getActivity(), list, R.layout.lv_sevenfieldcontactinfo) {
			@Override
			public void convert(CommonViewHolder helper, SevenFieldEntity item) {
				TextView info1 = helper.getView(R.id.tv_info1);
				TextView info2 = helper.getView(R.id.tv_info2);
				TextView info3 = helper.getView(R.id.tv_info3);
				TextView info4 = helper.getView(R.id.tv_info4);
				TextView info5 = helper.getView(R.id.tv_info5);
				TextView info6 = helper.getView(R.id.tv_info6);
				TextView info7 = helper.getView(R.id.tv_info7);
				info1.setText(item.getInfo1());
				info2.setText(item.getInfo2());
				info3.setText(item.getInfo3());
				info4.setText(item.getInfo4());
				info5.setText(item.getInfo5());
				info6.setText(item.getInfo6());
				info7.setText(item.getInfo7());
			}
		};
		lvContactInfo.setAdapter(adapter);
	}

	private void setListener() {
		btnContactInfoNext.setOnClickListener(this);
		btnContactCreate.setOnClickListener(this);
	}

	private void setupView(View view) {
		lvContactInfo = (SwipeListView) view
				.findViewById(R.id.lv_contactinfo);
		btnContactInfoNext = (Button) view
				.findViewById(R.id.btn_contactinfonext);
		btnContactCreate = (Button) view.findViewById(R.id.btn_contactcreate);

		list = new ArrayList<SevenFieldEntity>();
		for (int i = 0; i < 4; i++) {
			SevenFieldEntity entity = new SevenFieldEntity("周敏", "C00001",
					"财务负责人", "身份证", "0123456789123456789", "12345678901",
					"备注备注备");
			list.add(entity);
		}

		int count = list.size();
		LayoutParams layoutParams = lvContactInfo.getLayoutParams();
		layoutParams.height = Tool.dip2px(getActivity(), 45) * count
				+ Tool.dip2px(getActivity(), 10);
		lvContactInfo.setLayoutParams(layoutParams);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_contactinfonext:
			if (getActivity() instanceof WorkPlaceItemChange) {
				((WorkPlaceItemChange) getActivity()).workPlacestyleChange(2);
			}
			break;
		case R.id.btn_contactcreate:
			dialog = DialogFragmentCreateContactInfo.getNewInstance();
			dialog.show(fm, "ssdsds");
			setDialogCallback();
			break;
		default:
			break;
		}
	}

	private void setDialogCallback() {
		dialog.setCallBack(new BookbuildingContactCallback() {
			@Override
			public void bookbuildingContact(Boolean isEdit,String info1, String info2,
					String info3, String info4, String info5, String info6,
					String info7,String info8) {
//				toast("3333333", 1).show();
				//创建
				createContantsInfo(info1,info2,info3,info4,info5,info6,info7);
			}
		});
	}
}
