package com.pactera.financialmanager.ui.myreport;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.callback.WorkPlaceItemChange;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.model.Propertiesinfo;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.Tool;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 我的业绩-管理岗-全行金融资产
 * @author dg
 */
public class FragmentMyreportTab00 extends ParentFragment{

	private LinearLayout layPerson, layCommon;
	private TextView tvTypePerson, tvTypeCommon;
	private TextView[] tvsDQPerson = new TextView[5];
	private TextView[] tvsHQPerson = new TextView[5];
	private TextView[] tvsLCPerson = new TextView[5];
	private TextView[] tvsTotalPerson = new TextView[5];
	private TextView[] tvsDQCommon = new TextView[5];
	private TextView[] tvsHQCommon = new TextView[5];
	private TextView[] tvsLCCommon = new TextView[5];
	private TextView[] tvsTotalCommon = new TextView[5];
	private TextView[] tvsAll = new TextView[5];
	private TextView[] tvsDKPerson = new TextView[5];
	private TextView[] tvsDKCommon = new TextView[5];
	private TextView[] tvsDKTotal = new TextView[5];


	private HConnection HConQH;
	private static final int INDEX_QH = 1; 	// 全行资产
	private Propertiesinfo Propertyinfo = null;  	// 全行数据

	private boolean isForperson = true;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.arg1) {
			/* 全行资产列表 */
			case INDEX_QH:
				Propertyinfo = (Propertiesinfo) msg.obj;
				setPropertyInfo(Propertyinfo);
				if(Propertyinfo == null){
					Toast.makeText(getActivity(), "全行资产数据解析失败！！！", Toast.LENGTH_SHORT).show();
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
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_myreport_tab00, null);
		findView(view);
		if(getActivity() instanceof WorkPlaceItemChange){
			((WorkPlaceItemChange)getActivity()).workMyreportPersonChange(0, isForperson);
		}
		
		getQHDatas();
		return view;
	}
	

	private void findView(View view){
		// 个人业务-定期
		layPerson = (LinearLayout) view.findViewById(R.id.lay_person);
		tvTypePerson = (TextView) layPerson.findViewById(R.id.tv_type);// 个人业务标题
		tvTypePerson.setText("个人业务");
		tvsDQPerson[0] = (TextView) layPerson.findViewById(R.id.tv_dq_bal);
		tvsDQPerson[1] = (TextView) layPerson.findViewById(R.id.tv_dq_day);
		tvsDQPerson[2] = (TextView) layPerson.findViewById(R.id.tv_dq_month);
		tvsDQPerson[3] = (TextView) layPerson.findViewById(R.id.tv_dq_quarter);
		tvsDQPerson[4] = (TextView) layPerson.findViewById(R.id.tv_dq_year);
		// 活期
		tvsHQPerson[0] = (TextView) layPerson.findViewById(R.id.tv_hq_bal);
		tvsHQPerson[1] = (TextView) layPerson.findViewById(R.id.tv_hq_day);
		tvsHQPerson[2] = (TextView) layPerson.findViewById(R.id.tv_hq_month);
		tvsHQPerson[3] = (TextView) layPerson.findViewById(R.id.tv_hq_quarter);
		tvsHQPerson[4] = (TextView) layPerson.findViewById(R.id.tv_hq_year);
		// 理财
		tvsLCPerson[0] = (TextView) layPerson.findViewById(R.id.tv_lc_bal);
		tvsLCPerson[1] = (TextView) layPerson.findViewById(R.id.tv_lc_day);
		tvsLCPerson[2] = (TextView) layPerson.findViewById(R.id.tv_lc_month);
		tvsLCPerson[3] = (TextView) layPerson.findViewById(R.id.tv_lc_quarter);
		tvsLCPerson[4] = (TextView) layPerson.findViewById(R.id.tv_lc_year);
		// 合计
		tvsTotalPerson[0] = (TextView) layPerson.findViewById(R.id.tv_total_bal);
		tvsTotalPerson[1] = (TextView) layPerson.findViewById(R.id.tv_total_day);
		tvsTotalPerson[2] = (TextView) layPerson.findViewById(R.id.tv_total_month);
		tvsTotalPerson[3] = (TextView) layPerson.findViewById(R.id.tv_total_quarter);
		tvsTotalPerson[4] = (TextView) layPerson.findViewById(R.id.tv_total_year);

		// 对公业务-定期
		layCommon = (LinearLayout) view.findViewById(R.id.lay_common);
		tvTypeCommon = (TextView) layCommon.findViewById(R.id.tv_type);// 对公业务标题
		tvTypeCommon.setText("对公业务");
		tvsDQCommon[0] = (TextView) layCommon.findViewById(R.id.tv_dq_bal);
		tvsDQCommon[1] = (TextView) layCommon.findViewById(R.id.tv_dq_day);
		tvsDQCommon[2] = (TextView) layCommon.findViewById(R.id.tv_dq_month);
		tvsDQCommon[3] = (TextView) layCommon.findViewById(R.id.tv_dq_quarter);
		tvsDQCommon[4] = (TextView) layCommon.findViewById(R.id.tv_dq_year);
		// 活期
		tvsHQCommon[0] = (TextView) layCommon.findViewById(R.id.tv_hq_bal);
		tvsHQCommon[1] = (TextView) layCommon.findViewById(R.id.tv_hq_day);
		tvsHQCommon[2] = (TextView) layCommon.findViewById(R.id.tv_hq_month);
		tvsHQCommon[3] = (TextView) layCommon.findViewById(R.id.tv_hq_quarter);
		tvsHQCommon[4] = (TextView) layCommon.findViewById(R.id.tv_hq_year);
		// 理财
		tvsLCCommon[0] = (TextView) layCommon.findViewById(R.id.tv_lc_bal);
		tvsLCCommon[1] = (TextView) layCommon.findViewById(R.id.tv_lc_day);
		tvsLCCommon[2] = (TextView) layCommon.findViewById(R.id.tv_lc_month);
		tvsLCCommon[3] = (TextView) layCommon.findViewById(R.id.tv_lc_quarter);
		tvsLCCommon[4] = (TextView) layCommon.findViewById(R.id.tv_lc_year);
		// 合计
		tvsTotalCommon[0] = (TextView) layCommon.findViewById(R.id.tv_total_bal);
		tvsTotalCommon[1] = (TextView) layCommon.findViewById(R.id.tv_total_day);
		tvsTotalCommon[2] = (TextView) layCommon.findViewById(R.id.tv_total_month);
		tvsTotalCommon[3] = (TextView) layCommon.findViewById(R.id.tv_total_quarter);
		tvsTotalCommon[4] = (TextView) layCommon.findViewById(R.id.tv_total_year);

		// 个人业务&对公业务 总合计
		tvsAll[0] = (TextView) view.findViewById(R.id.tv_all_bal);
		tvsAll[1] = (TextView) view.findViewById(R.id.tv_all_day);
		tvsAll[2] = (TextView) view.findViewById(R.id.tv_all_month);
		tvsAll[3] = (TextView) view.findViewById(R.id.tv_all_quarter);
		tvsAll[4] = (TextView) view.findViewById(R.id.tv_all_year);

		// 贷款-个人业务
		tvsDKPerson[0] = (TextView) view.findViewById(R.id.tv_dkp_bal);
		tvsDKPerson[1] = (TextView) view.findViewById(R.id.tv_dkp_day);
		tvsDKPerson[2] = (TextView) view.findViewById(R.id.tv_dkp_month);
		tvsDKPerson[3] = (TextView) view.findViewById(R.id.tv_dkp_quarter);
		tvsDKPerson[4] = (TextView) view.findViewById(R.id.tv_dkp_year);
		// 对公业务
		tvsDKCommon[0] = (TextView) view.findViewById(R.id.tv_dkc_bal);
		tvsDKCommon[1] = (TextView) view.findViewById(R.id.tv_dkc_day);
		tvsDKCommon[2] = (TextView) view.findViewById(R.id.tv_dkc_month);
		tvsDKCommon[3] = (TextView) view.findViewById(R.id.tv_dkc_quarter);
		tvsDKCommon[4] = (TextView) view.findViewById(R.id.tv_dkc_year);
		// 合计
		tvsDKTotal[0] = (TextView) view.findViewById(R.id.tv_dkt_bal);
		tvsDKTotal[1] = (TextView) view.findViewById(R.id.tv_dkt_day);
		tvsDKTotal[2] = (TextView) view.findViewById(R.id.tv_dkt_month);
		tvsDKTotal[3] = (TextView) view.findViewById(R.id.tv_dkt_quarter);
		tvsDKTotal[4] = (TextView) view.findViewById(R.id.tv_dkt_year);
	}
	
	public void setIsForperson(boolean isForperson){
		this.isForperson = isForperson;
		if(getActivity() == null){
			return;
		}
	}
	
	/**
	 * 显示管行数据
	 * @param propertyinfo
	 */
	private void setPropertyInfo(Propertiesinfo propertyinfo){
		if(propertyinfo != null){
			// 个人业务-定期
			tvsDQPerson[0].setText(setFormatValue(propertyinfo.getPSN_FIX_DEPS_BAL()));
			tvsDQPerson[1].setText(setFormatValue(propertyinfo.getPSN_FIX_DEPS_LDBAL()));
			tvsDQPerson[2].setText(setFormatValue(propertyinfo.getPSN_FIX_DEPS_LMBAL()));
			tvsDQPerson[3].setText(setFormatValue(propertyinfo.getPSN_FIX_DEPS_LQBAL()));
			tvsDQPerson[4].setText(setFormatValue(propertyinfo.getPSN_FIX_DEPS_LYBAL()));
			// 活期
			tvsHQPerson[0].setText(setFormatValue(propertyinfo.getPSN_CUR_DEPS_BAL()));
			tvsHQPerson[1].setText(setFormatValue(propertyinfo.getPSN_CUR_DEPS_LDBAL()));
			tvsHQPerson[2].setText(setFormatValue(propertyinfo.getPSN_CUR_DEPS_LMBAL()));
			tvsHQPerson[3].setText(setFormatValue(propertyinfo.getPSN_CUR_DEPS_LQBAL()));
			tvsHQPerson[4].setText(setFormatValue(propertyinfo.getPSN_CUR_DEPS_LYBAL()));
			// 理财
			tvsLCPerson[0].setText(setFormatValue(propertyinfo.getPSN_FINA_BAL()));
			tvsLCPerson[1].setText(setFormatValue(propertyinfo.getPSN_FINA_LDBAL()));
			tvsLCPerson[2].setText(setFormatValue(propertyinfo.getPSN_FINA_LMBAL()));
			tvsLCPerson[3].setText(setFormatValue(propertyinfo.getPSN_FINA_LQBAL()));
			tvsLCPerson[4].setText(setFormatValue(propertyinfo.getPSN_FINA_LYBAL()));
			// 合计
			tvsTotalPerson[0].setText(setFormatValue(propertyinfo.getPSN_BAL()));
			tvsTotalPerson[1].setText(setFormatValue(propertyinfo.getPSN_LDBAL()));
			tvsTotalPerson[2].setText(setFormatValue(propertyinfo.getPSN_LMBAL()));
			tvsTotalPerson[3].setText(setFormatValue(propertyinfo.getPSN_LQBAL()));
			tvsTotalPerson[4].setText(setFormatValue(propertyinfo.getPSN_LYBAL()));

			// 对公业务-定期
			tvsDQCommon[0].setText(setFormatValue(propertyinfo.getENT_FIX_DEPS_BAL()));
			tvsDQCommon[1].setText(setFormatValue(propertyinfo.getENT_FIX_DEPS_LDBAL()));
			tvsDQCommon[2].setText(setFormatValue(propertyinfo.getENT_FIX_DEPS_LMBAL()));
			tvsDQCommon[3].setText(setFormatValue(propertyinfo.getENT_FIX_DEPS_LQBAL()));
			tvsDQCommon[4].setText(setFormatValue(propertyinfo.getENT_FIX_DEPS_LYBAL()));
			// 活期
			tvsHQCommon[0].setText(setFormatValue(propertyinfo.getENT_CUR_DEPS_BAL()));
			tvsHQCommon[1].setText(setFormatValue(propertyinfo.getENT_CUR_DEPS_LDBAL()));
			tvsHQCommon[2].setText(setFormatValue(propertyinfo.getENT_CUR_DEPS_LMBAL()));
			tvsHQCommon[3].setText(setFormatValue(propertyinfo.getENT_CUR_DEPS_LQBAL()));
			tvsHQCommon[4].setText(setFormatValue(propertyinfo.getENT_CUR_DEPS_LYBAL()));
			// 理财
			tvsLCCommon[0].setText(setFormatValue(propertyinfo.getENT_FINA_BAL()));
			tvsLCCommon[1].setText(setFormatValue(propertyinfo.getENT_FINA_LDBAL()));
			tvsLCCommon[2].setText(setFormatValue(propertyinfo.getENT_FINA_LMBAL()));
			tvsLCCommon[3].setText(setFormatValue(propertyinfo.getENT_FINA_LQBAL()));
			tvsLCCommon[4].setText(setFormatValue(propertyinfo.getENT_FINA_LYBAL()));
			// 合计
			tvsTotalCommon[0].setText(setFormatValue(propertyinfo.getENT_BAL()));
			tvsTotalCommon[1].setText(setFormatValue(propertyinfo.getENT_LDBAL()));
			tvsTotalCommon[2].setText(setFormatValue(propertyinfo.getENT_LMBAL()));
			tvsTotalCommon[3].setText(setFormatValue(propertyinfo.getENT_LQBAL()));
			tvsTotalCommon[4].setText(setFormatValue(propertyinfo.getENT_LYBAL()));

			// 个人业务&对公业务 总合计
			tvsAll[0].setText(setFormatValue(propertyinfo.getBAL()));
			tvsAll[1].setText(setFormatValue(propertyinfo.getLDBAL()));
			tvsAll[2].setText(setFormatValue(propertyinfo.getLMBAL()));
			tvsAll[3].setText(setFormatValue(propertyinfo.getLQBAL()));
			tvsAll[4].setText(setFormatValue(propertyinfo.getLYBAL()));

			// 贷款-个人业务
			tvsDKPerson[0].setText(setFormatValue(propertyinfo.getPSN_LOAN_BAL()));
			tvsDKPerson[1].setText(setFormatValue(propertyinfo.getPSN_LOAN_LDBAL()));
			tvsDKPerson[2].setText(setFormatValue(propertyinfo.getPSN_LOAN_LMBAL()));
			tvsDKPerson[3].setText(setFormatValue(propertyinfo.getPSN_LOAN_LQBAL()));
			tvsDKPerson[4].setText(setFormatValue(propertyinfo.getPSN_LOAN_LYBAL()));
			// 对公业务
			tvsDKCommon[0].setText(setFormatValue(propertyinfo.getENT_LOAN_BAL()));
			tvsDKCommon[1].setText(setFormatValue(propertyinfo.getENT_LOAN_LDBAL()));
			tvsDKCommon[2].setText(setFormatValue(propertyinfo.getENT_LOAN_LMBAL()));
			tvsDKCommon[3].setText(setFormatValue(propertyinfo.getENT_LOAN_LQBAL()));
			tvsDKCommon[4].setText(setFormatValue(propertyinfo.getENT_LOAN_LYBAL()));
			// 合计
			tvsDKTotal[0].setText(setFormatValue(propertyinfo.getLOAN_BAL()));
			tvsDKTotal[1].setText(setFormatValue(propertyinfo.getLOAN_LDBAL()));
			tvsDKTotal[2].setText(setFormatValue(propertyinfo.getLOAN_LMBAL()));
			tvsDKTotal[3].setText(setFormatValue(propertyinfo.getLOAN_LQBAL()));
			tvsDKTotal[4].setText(setFormatValue(propertyinfo.getLOAN_LYBAL()));
		}
	}

	/**
	 * 将数据格式化
	 * @param value
	 * @return
	 */
	private String setFormatValue(String value){
		value = Tool.setFormatValue(value);
		return value;
	}
	
	/**
	 * 查询全行管辖资产List列表信息
	 */
	private void getQHDatas() {
		String staid = LogoActivity.user.getStaId();// 岗位ID
		String requestType = InterfaceInfo.MYREPORTTAB01_QH_Get;// 全行管辖资产
		
		String info = "&spareOne=" + staid;
		HConQH = RequestInfo(this, Constants.requestType.Other, requestType,
				info, INDEX_QH, false);
	}
	
	@Override
	public void onConnected(Message msg) {
		super.onConnected(msg);
		HResponse res = null;
		switch (connectionIndex) {
		case HConnection.RESPONSE_ERROR:
			Toast.makeText(getActivity(), "请求失败.", Toast.LENGTH_SHORT).show();
			break;
		// 解析返回结果列表
		case INDEX_QH:
			res = HConQH.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (res == null || res.dataJsonObject == null) {
				return;
			}
			readJson(res.dataJsonObject, connectionIndex);
			break;

		default:
			break;
		}
	}

	private void readJson(JSONObject tmpJsonObject, int connectionIndex) {
		String retCode = "";
		Log.e("RETURNCUS", tmpJsonObject.toString());
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
			// 获取列表
			Propertiesinfo property = JSON.parseObject(tmpJsonObject.toString(), Propertiesinfo.class);
			Message msg = new Message();
			msg.arg1 = connectionIndex;
			msg.obj = property;
			handler.sendMessage(msg);
		} else {
			Toast.makeText(getActivity(), "请求失败! 错误代码:"+retCode, Toast.LENGTH_SHORT).show();
		}
	}
}
