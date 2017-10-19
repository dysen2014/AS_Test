package com.dysen.warn_demo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dysen.common_excel.adapters.ChildDataAdapter;
import com.dysen.common_excel.adapters.CommonTxtDataAdapter;
import com.dysen.common_excel.adapters.DataAdapter;
import com.dysen.common_excel.adapters.LeftAdapter;
import com.dysen.common_excel.adapters.TopDataAdapter;
import com.dysen.common_excel.beans.AfterTheLoan;
import com.dysen.common_excel.beans.BeanHeader;
import com.dysen.common_excel.utils.InitData;
import com.dysen.common_res.common.base.ParentActivity;
import com.dysen.common_res.common.utils.HttpThread;
import com.dysen.common_res.common.utils.LogUtils;
import com.dysen.common_res.common.utils.OnItemClickCallback;
import com.dysen.common_res.common.utils.ParamUtils;
import com.dysen.common_res.common.views.NoscrollListView;
import com.dysen.common_res.common.views.SyncHorizontalScrollView;
import com.dysen.common_res.common.views.TextViewMarquee;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 贷后提醒
 */
public class WarnDemoActivity extends ParentActivity implements BaseRefreshListener {

	@Bind(R.id.top_data)
	RecyclerView topData;
	@Bind(R.id.header_horizontal)
	SyncHorizontalScrollView headerHorizontal;
	@Bind(R.id.left_data)
	NoscrollListView leftData;
	@Bind(R.id.lv_data)
	NoscrollListView lvData;
	@Bind(R.id.data_horizontal)
	SyncHorizontalScrollView dataHorizontal;
	@Bind(R.id.ptr_layout)
	PullToRefreshLayout ptrLayout;
	@Bind(R.id.common_txt_0)
	TextView commonTxt0;
	@Bind(R.id.common_txt_1)
	TextView commonTxt1;
	@Bind(R.id.common_txt_2)
	TextView commonTxt2;
	@Bind(R.id.common_txt_3)
	TextView commonTxt3;
	@Bind(R.id.left_top_item_tv)
	TextViewMarquee leftTopItemTv;
	@Bind(R.id.progress_loading)
	ProgressBar progressLoading;
	@Bind(R.id.common_txt_rlv)
	RecyclerView commonTxtRlv;

	private LinearLayoutManager mLayoutManager;
	private TopDataAdapter mTopDataAdapter;
	private DataAdapter mDataAdapter;
	private ChildDataAdapter childDataAdapter;
	private ListAdapter mLeftAdapter;

	private List<String> mListLeft = new ArrayList<>();
	private List<String> mListTop = new ArrayList<>();
	private List<List<Object>> mListData = new ArrayList<>();

	private static List<String>  ItemNo = new ArrayList<>();
	private static List<String> topName = new ArrayList<>();
	private int index1;
	final int PAGE_SIZE = 10;
	int curPage = 1;

	private List<BeanHeader> listHeader = new ArrayList<>();
	private List<AfterTheLoan> afterTheLoanList = new ArrayList<>();

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.obj != null) {

				try {
					HttpThread.parseJSONWithGson((String) msg.obj, mHandler);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				progressLoading.setVisibility(View.GONE);
			}
		}
	};

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			if (msg.getData() != null) {
				Bundle bundle = msg.getData();
				progressLoading.setVisibility(View.GONE);
				listHeader = parseListHeader(bundle.getString("header"));
				List<AfterTheLoan> list = parseList(bundle.getString("data"));

				for (int i = 0; i < list.size(); i++) {

					afterTheLoanList.add(list.get(i));
				}

				if (listHeader.size() > 0)
					initView();
			}
		}
	};
	private static String mItemNo;
	private CommonTxtDataAdapter commonTxtDataAdapter;

	protected List<BeanHeader> parseListHeader(String jsonData) throws JsonSyntaxException {

		if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
			Gson gson = new Gson();

			List<BeanHeader> list = gson.fromJson(jsonData, new TypeToken<List<BeanHeader>>() {
			}.getType());

			return list;
		} else
			return null;
	}

	protected  List<AfterTheLoan> parseList(String jsonData) throws JsonSyntaxException {

		if (!TextUtils.isEmpty(jsonData) || jsonData != null) {
			Gson gson = new Gson();

			List<AfterTheLoan> list = gson.fromJson(jsonData, new TypeToken<List<AfterTheLoan>>(){}.getType());

			return list;
		}else
			return null;
	}

	private void setList(List<AfterTheLoan> dataList) {
		afterTheLoanList = dataList;
	}

	public static void setItenNo(String itemNo) {
		mItemNo = itemNo;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_warn_demo, null);
		ButterKnife.bind(this, view);
		LogUtils.d("======================================="+mItemNo);
		sendRequest();

		return view;
	}

	private void sendRequest() {
		LogUtils.d("sendrequest======================================="+mItemNo);
		JSONObject jsonObject = ParamUtils.setParams("warn", "crmAfterTheLoan", new Object[]{ParamUtils.UserId, mItemNo, PAGE_SIZE, curPage}, 4);
		HttpThread.sendRequestWithOkHttp(ParamUtils.url, jsonObject, handler);
	}

	private void initData() {

		mListTop = InitData.AfterWarn.getTopHeaderData(listHeader);
		mListLeft = InitData.AfterWarn.getLeftHeaderData(afterTheLoanList);
		mListData = InitData.AfterWarn.getCellData(listHeader, afterTheLoanList);

//		if (topName.size()>0) {
//			commonTxt0.setVisibility(View.VISIBLE);
//			commonTxt0.setText(topName.get(0));
//		}
//		setSelectView(commonTxt0, index1);
	}

	private void initView() {

		headerHorizontal.setScrollView(dataHorizontal);
		dataHorizontal.setScrollView(headerHorizontal);

		initData();
		mTopDataAdapter = new TopDataAdapter(getActivity(), mListTop);
		mLeftAdapter = new LeftAdapter(getActivity(), mListLeft);
		childDataAdapter = new ChildDataAdapter(getActivity());
		mDataAdapter = new DataAdapter(getActivity(), mListData, new OnItemClickCallback<Integer>() {
			@Override
			public void onClick(View view, Integer info) {

				toast("第：" + info + "行被点击！！！");
				LogUtils.d("第：" + info + "行被点击！！！");
			}

			@Override
			public void onLongClick(View view, Integer info) {

			}
		});

		commonTxtDataAdapter = new CommonTxtDataAdapter(getActivity(), topName, new OnItemClickCallback<Integer>() {
			@Override
			public void onClick(View view, Integer index) {
				index1 = index;
				sendRequest();
			}

			@Override
			public void onLongClick(View view, Integer index) {

			}
		});
		setRecyclerView(commonTxtRlv).setAdapter(commonTxtDataAdapter);
		setRecyclerView(topData).setAdapter(mTopDataAdapter);
		leftData.setAdapter(mLeftAdapter);

		lvData.setAdapter(mDataAdapter);

		ptrLayout.setRefreshListener(this);
	}

	@Override
	public void refresh() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				curPage = 1;
				afterTheLoanList.clear();
				sendRequest();
				// 结束刷新
				ptrLayout.finishRefresh();
			}
		}, 2000);
	}

	@Override
	public void loadMore() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				curPage++;
				sendRequest();
				// 结束加载更多
				ptrLayout.finishLoadMore();
			}
		}, 2000);
	}

	/**
	 * 设置头背景
	 *
	 * @param tv
	 */
	private void setBgAndTextColor(TextView tv) {
		commonTxt0.setEnabled(true);
		commonTxt1.setEnabled(true);
		commonTxt2.setEnabled(true);
		commonTxt3.setEnabled(true);

		commonTxt0.setTextColor(getResources().getColor(R.color.separatelightredline));
		commonTxt1.setTextColor(getResources().getColor(R.color.separatelightredline));
		commonTxt2.setTextColor(getResources().getColor(R.color.separatelightredline));
		commonTxt3.setTextColor(getResources().getColor(R.color.separatelightredline));
		tv.setEnabled(false);
		tv.setTextColor(getResources().getColor(R.color.white));
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	@OnClick({R.id.common_txt_0, R.id.common_txt_1, R.id.common_txt_2, R.id.common_txt_3})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.common_txt_0:
				setSelectView(commonTxt0, 0);
				break;
			case R.id.common_txt_1:
				setSelectView(commonTxt1, 1);
				break;
			case R.id.common_txt_2:
				setSelectView(commonTxt2, 2);
				break;
			case R.id.common_txt_3:
				setSelectView(commonTxt3, 3);
				break;
		}
	}

	private void setSelectView(TextView commonTxt0, int i) {

		afterTheLoanList.clear();
		sendRequest();
		setBgAndTextColor(commonTxt0);
	}
}

