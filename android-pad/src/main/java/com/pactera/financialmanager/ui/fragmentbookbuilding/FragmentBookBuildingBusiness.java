package com.pactera.financialmanager.ui.fragmentbookbuilding;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.callback.BookbuildingCaiWuInfoCallback;
import com.pactera.financialmanager.callback.BookbuildingContentCallback;
import com.pactera.financialmanager.callback.BookbuildingGudonginfoCallback;
import com.pactera.financialmanager.callback.BookbuildingZBGCinfoCallback;
import com.pactera.financialmanager.callback.WorkPlaceItemChange;
import com.pactera.financialmanager.ui.DeleteDialog;
import com.pactera.financialmanager.ui.DeleteDialog.IsDeleteListener;
import com.pactera.financialmanager.ui.LogoActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.SwipeListView;
import com.pactera.financialmanager.ui.bookbuildingfrocompany.ActivityBaseInfo;
import com.pactera.financialmanager.ui.model.EightFieldEntity;
import com.pactera.financialmanager.ui.model.FourFieldEntity;
import com.pactera.financialmanager.ui.model.FuzaiInfoEntity;
import com.pactera.financialmanager.ui.model.GuQuanTouZiEntity;
import com.pactera.financialmanager.ui.model.GudingZichanEntity;
import com.pactera.financialmanager.ui.model.ZiBenGouChengEntity;
import com.pactera.financialmanager.ui.newcommonadapter.CommonAdapter1;
import com.pactera.financialmanager.ui.newcommonadapter.CommonViewHolder;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HRequest;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户建档中的资产负债信息的 fragment
 */
public class FragmentBookBuildingBusiness extends ParentFragment implements
		OnClickListener {

	private Button  btnCreateFuzhai, btnNext,
			btnCreateGuquantouzi, btnCreateZibengoucheng;
	private SwipeListView lvZibengoucheng, lvGuquantouzi,lvFuzhai;
	private List<EightFieldEntity> list, list2;
	private FragmentManager fm;
//	private DialogFragmentCreateGdzichan createGdzichanFragment;
//	private DialogFragmentCreateGdzichan createFuzhaiFragment;
	private HConnection companyGzCheckHConn, companyFuzaiCheckHConn,
			companyGudzcHConn, companyFzHConn, companyZibenGouChengHConn,
			companyZiBenCreateHConn, companyGuQuanTouZiHConn,
			companyGuQuanTouZiCreateHConn, companyGuQuanTouZiDeleteHConn,
			companyZiBenGouChengDeleteHConn, companyFuzaiDeleteHConn,
			companyGuDingDeleteHConn, companyGzEditHConn, companyFzEditHConn,
			companyZiBenEditHConn, companyGuQuanTouZiEditHConn;
	private final int companyGzCheckFlag = 96, companyFuzaiCheckFlag = 95,
			companyGdzcFlag = 94, companyFzFlag = 93,
			companyZibenGouChengFlag = 92, companyZiBenCreateFlag = 91,
			companyGuQuanTouZiFlag = 90, companyGuQuanTouZiCreateFlag = 97,
			companyGuQuanTouZiDeleteFlag = 98,
			companyZiBenGouChengDeleteFlag = 99, companyFuzaiDeleteFlag = 100,
			companyGuDingDeleteFlag = 101, companyGzEditFlag = 102,
			companyFzEditFlag = 103, companyZiBenEditFlag = 104,
			companyGuQuanTouZiEditFlag = 105;
	//	private List<GudingZichanEntity> listGuz;
	// private List<FourFieldEntity> listZibengoucheng;
	private List<FuzaiInfoEntity> listFuzai;
	private List<ZiBenGouChengEntity> listZiBenGouCheng;
	private List<GuQuanTouZiEntity> listGuquanTouZi;
	private DeleteDialog deleteDialog;
	private String pkidGuzi, pkidFuzai, pkidZibengoucheng, pkidGuQuanTouZi;
	private RelativeLayout rlBaseInfo;
	private RelativeLayout rlOtherInfo;

	// private List<FourFieldEntity> listGuquantouzi;


	private String info9_CaiWu;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_baseinfocom, null);
		setupView(view);
		fm = getFragmentManager();
//		listGuz = new ArrayList<GudingZichanEntity>();
		listFuzai = new ArrayList<FuzaiInfoEntity>();
		listZiBenGouCheng = new ArrayList<ZiBenGouChengEntity>();
		listGuquanTouZi = new ArrayList<GuQuanTouZiEntity>();
		// setDefaultInfo();
		setListener();
//		sendRequestForGudingzichan();// 查询固定资产信息
		sendRequestForFuzai();// 查询负债信息
		sendRequestForZibenGouChengInfo();// 查询资本构成信息
		sendRequestForGuQuauTouZiInfo();// 查询股权投资
		// showZibengouchengInfo();//资本构成
		// showGuquantouziInfo();// 股权投资
		return view;
	}

	// 编辑股权投资和资本构成信息
	private void editZiBenGouChengOrGuQuanTouZiInfo(int flag,
                                                    String info1, String info2, String info3, String info4,
                                                    String info5, String info6, String info7, String info8,
                                                    String info9, String info10) {
        HRequest request = null;
        String requestStr;
        if (flag == 1) {// 资本构成编辑
            requestStr = Tool.getRequest("T011156");
            requestStr = requestStr + "&jsonData={PKID:\"" + pkidZibengoucheng
                    + "\",STOCK_NAME:\"" // 股东名称
                    + info1 + "\",SHAREHOLDER_TYPE:\"" // 股东类型
                    + info2 + "\",RATIO_INVEST:\"" //投资比例
                    + info3 + "\",IS_CONTROL:\""   //是否实际控制人
                    + info4 + "\",CERT_TYPE:\""  //证件类型
                    + info5 + "\",CERT_NO:\""    //证件号码
                    + info6 + "\",PAY_TYPE:\""    //出资方式
                    + info7 + "\",CURRENCY:\""    //币种
                    + info8 + "\",BAL:\""        //金额
                    + info9 + "\",DES:\""        //备注
                    + info10 + "\"}";
            request = new HRequest(requestStr, "GET");
            try {
                HConnection.isShowLoadingProcess = true; // 不显示loading
                companyZiBenEditHConn = new HConnection(this, request,
                        LogoActivity.user,
                        HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                companyZiBenEditHConn.setIndex(companyZiBenEditFlag);
            } catch (Exception e) {
                e.printStackTrace();
            }
		} else {// 股权投资
			requestStr = Tool.getRequest("T011160");
			requestStr = requestStr + "&jsonData={PKID:\"" + pkidGuQuanTouZi
					+ "\",INVEST_ENTER_NAME:\"" // 投向企业名称
					+ info1 + "\",RATIO_STOCK:\"" // 股权比例
					+ info2 + "\",INVEST_BAL:\"" // 投资金额
					+ info3 + "\",IS_CONTROL:\"" // 是否实际控制人
					+ info4 + "\",INVEST_TYPE:\""
					+ info5 +"\",ORG_CODE:\""
					+ info6 +"\",CURRENCY:\""
					+ info7 +"\",LOAN_ACC:\""
					+ info8 +"\",DES:\""
					+ info9 +"\"}";
			request = new HRequest(requestStr, "GET");
			Log.i("1111", "2222 guquantouzi...:" + requestStr);
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyGuQuanTouZiEditHConn = new HConnection(this, request,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyGuQuanTouZiEditHConn
						.setIndex(companyGuQuanTouZiEditFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 查询股权投资信息
	private void sendRequestForGuQuauTouZiInfo() {
		HRequest requestHr = null;
		String requestStr = "";
		String custID = ActivityBaseInfo.custID;
		if (Tool.haveNet(getActivity())) {
			String request = Tool.getRequestStr("T011157", custID);
			// requestStr = request + "&size=10" + "&offset=" + 1;
			// requestHr = new HRequest(requestStr, "GET");
			requestHr = new HRequest(request, "GET");
			Log.i("1111", "2222 guquantouzi....:" + requestStr);
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyGuQuanTouZiHConn = new HConnection(this, requestHr,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyGuQuanTouZiHConn.setIndex(companyGuQuanTouZiFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			toast("当前无网络连接！");
		}
	}

	// 资本构成和股权投资的创建 0开，1关
	private void createZiBenGouChengOrGuQuanTouZiInfo(int flag,
                                                      String info1,String info2, String info3, String info4,
                                                      String info5,String info6, String info7, String info8,
                                                      String info9,String info10) {
		HRequest request = null;
		String requestStr;
		String custId = ActivityBaseInfo.custID;
		if (flag == 1) {// 资本构成 创建
			requestStr = Tool.getRequest("T011154");
			requestStr = requestStr + "&jsonData={CUSTID:\"" + custId
                    + "\",STOCK_NAME:\"" // 股东名称
                    + info1 + "\",SHAREHOLDER_TYPE:\"" // 股东类型
                    + info2 + "\",RATIO_INVEST:\"" //投资比例
                    + info3 + "\",IS_CONTROL:\""   //是否实际控制人
                    + info4 + "\",CERT_TYPE:\""  //证件类型
                    + info5 + "\",CERT_NO:\""    //证件号码
                    + info6 + "\",PAY_TYPE:\""    //出资方式
                    + info7 + "\",CURRENCY:\""    //币种
                    + info8 + "\",BAL:\""        //金额
                    + info9 + "\",DES:\""        //备注
                    + info10 + "\"}";
			request = new HRequest(requestStr, "GET");
			Log.i("1111", "2222 zibengoucheng...:" + requestStr);
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyZiBenCreateHConn = new HConnection(this, request,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyZiBenCreateHConn.setIndex(companyZiBenCreateFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {// 股权投资
			requestStr = Tool.getRequest("T011158");
			requestStr = requestStr + "&jsonData={CUSTID:\"" + custId
					+ "\",INVEST_ENTER_NAME:\"" // 投向企业名称
					+ info1 + "\",RATIO_STOCK:\"" // 股权比例
					+ info2 + "\",INVEST_BAL:\"" // 投资金额
					+ info3 + "\",IS_CONTROL:\"" // 是否实际控制人
					+ info4 + "\",INVEST_TYPE:\""
                    + info5 +"\",ORG_CODE:\""
                    + info6 +"\",CURRENCY:\""
                    + info7 +"\",LOAN_ACC:\""
                    + info8 +"\",DES:\""
                    + info9 +"\"}";
			request = new HRequest(requestStr, "GET");
			Log.i("1111", "2222 guquantouzi...:" + requestStr);
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyGuQuanTouZiCreateHConn = new HConnection(this, request,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyGuQuanTouZiCreateHConn
						.setIndex(companyGuQuanTouZiCreateFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 编辑固定资产信息和负债信息
	 * 
	 * @param i
	 *            1，固定资产信息；2，负债信息
	 * @param info1
	 * @param info2
	 * @param info3
	 * @param info4
	 * @param info5
	 * @param info6
	 * @param info7
	 * @param info8
	 */
	private void editGuZiChanOrFuzaiInfo(int i, String info1, String info2,
			String info3, String info4, String info5, String info6,
			String info7, String info8) {
		HRequest request = null;
		String requestStr;
		if (i == 1) {// 编辑固定资产信息
			requestStr = Tool.getRequest("T011107");
			requestStr = requestStr + "&jsonData={PKID:\"" + pkidGuzi
					+ "\",PROPERTY_TYPE:\"" // 资产类型
					+ info1 + "\",PROPERTY_NAME:\"" // 资产名称
					+ info2 + "\",PURCHASE_DATE:\"" // 购买日期
					+ info3 + "\",PURCHASE_PRICE:\"" // 购买价格
					+ info4 + "\",AREA:\"" // 平方数
					+ info5 + "\",CERTIFIED_AREA:\"" // 已办证面积
					+ info6 + "\",QUANTITY:\"" // 数量
					+ info7 + "\",ACCESS_VALUE:\"" // 评估价值
					+ info8 + "\"}";
			request = new HRequest(requestStr, "GET");
			Log.i("1111", "2222gudingzichanedit:" + requestStr);
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyGzEditHConn = new HConnection(this, request,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyGzEditHConn.setIndex(companyGzEditFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {// 编辑负债信息
			requestStr = Tool.getRequest("T011110");
			requestStr = requestStr + "&jsonData={PKID:\"" + pkidFuzai
					+ "\",LAST_ANNUAL_INCOME:\""
					+ info1 + "\",LAST_ANNUAL_OUTPUT:\""
					+ info2 + "\",PROFIT:\""
					+ info3 + "\",HAND_TAX:\""
					+ info4 + "\",FIX_ASSET:\""
					+ info5 + "\",HAND_CREDIT:\""
					+ info6 + "\",INVENTORY:\""
					+ info7 + "\",PAY_CREDIT:\""
					+ info8+ "\",DEBT_RENTAL:\""
					+ info9_CaiWu + "\"}";
			request = new HRequest(requestStr, "GET");
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyFzEditHConn = new HConnection(this, request,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyFzEditHConn.setIndex(companyFzEditFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建固定资产信息和负债信息
	 * 
	 * @param i
	 *            1，固定资产；2，负债
	 * @param info1
	 * @param info2
	 * @param info3
	 * @param info4
	 * @param info5
	 * @param info6
	 * @param info7
	 * @param info8
	 */
	private void createGuZiChanOrFuzaiInfo(int i, String info1, String info2,
			String info3, String info4, String info5, String info6,
			String info7, String info8) {
		HRequest request = null;
		String requestStr;
		String custId = ActivityBaseInfo.custID;
		if (i == 1) {// 创建固定资产信息
			requestStr = Tool.getRequest("T011102");
			requestStr = requestStr + "&jsonData={CUSTID:\"" + custId
					+ "\",PROPERTY_TYPE:\"" // 资产类型
					+ info1 + "\",PROPERTY_NAME:\"" // 资产名称
					+ info2 + "\",PURCHASE_DATE:\"" // 购买日期
					+ info3 + "\",PURCHASE_PRICE:\"" // 购买价格
					+ info4 + "\",AREA:\"" // 平方数
					+ info5 + "\",CERTIFIED_AREA:\"" // 已办证面积
					+ info6 + "\",QUANTITY:\"" // 数量
					+ info7 + "\",ACCESS_VALUE:\"" // 评估价值
					+ info8 + "\"}";
			request = new HRequest(requestStr, "GET");
			Log.i("1111", "2222gudingzichan:" + requestStr);
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyGudzcHConn = new HConnection(this, request,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyGudzcHConn.setIndex(companyGdzcFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {// 创建负债信息
			requestStr = Tool.getRequest("T011103");
			requestStr = requestStr + "&jsonData={CUSTID:\"" + custId
					+ "\",LAST_ANNUAL_INCOME:\""
					+ info1 + "\",LAST_ANNUAL_OUTPUT:\""
					+ info2 + "\",PROFIT:\""
					+ info3 + "\",HAND_TAX:\""
					+ info4 + "\",FIX_ASSET:\""
					+ info5 + "\",HAND_CREDIT:\""
					+ info6 + "\",INVENTORY:\""
					+ info7 + "\",PAY_CREDIT:\""
					+ info8+ "\",DEBT_RENTAL:\""
					+ info9_CaiWu + "\"}";
			request = new HRequest(requestStr, "GET");
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyFzHConn = new HConnection(this, request,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyFzHConn.setIndex(companyFzFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 查询资本构成信息
	private void sendRequestForZibenGouChengInfo() {
		HRequest requestHr = null;
		String requestStr = "";
		String custID = ActivityBaseInfo.custID;
		if (Tool.haveNet(getActivity())) {
			String request = Tool.getRequestStr("T011153", custID);
			requestStr = request + "&size=10" + "&offset=" + 1;
			requestHr = new HRequest(requestStr, "GET");
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyZibenGouChengHConn = new HConnection(this, requestHr,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyZibenGouChengHConn.setIndex(companyZibenGouChengFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			toast("当前无网络连接！");
		}
	}

/*
	// 查询固定资产信息
	private void sendRequestForGudingzichan() {
		HRequest requestHr = null;
		String requestStr = "";
		String custID = ActivityBaseInfo.custID;
		if (Tool.haveNet(getActivity())) {
			String request = Tool.getRequestStr("T011105", custID);
			requestStr = request + "&size=10" + "&offset=" + 1;
			requestHr = new HRequest(requestStr, "GET");
			Log.i("1111", "2222guzi:" + requestStr);
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyGzCheckHConn = new HConnection(this, requestHr,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyGzCheckHConn.setIndex(companyGzCheckFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Toast.makeText(getActivity(), "当前无网络连接！", 0).show();
		}
	}
*/

	// 查询负债信息
	private void sendRequestForFuzai() {
		HRequest requestHr = null;
		String requestStr = "";
		String custID = ActivityBaseInfo.custID;
		if (Tool.haveNet(getActivity())) {
			requestStr = Tool.getRequestStr("T011108", custID);
			requestHr = new HRequest(requestStr, "GET");
			Log.i("1111", "2222fuzai:" + requestStr);
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyFuzaiCheckHConn = new HConnection(this, requestHr,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyFuzaiCheckHConn.setIndex(companyFuzaiCheckFlag);
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
		case HConnection.RESPONSE_ERROR:

			break;
		case companyGdzcFlag:// 创建固定资产....companyfuzaicreate....
			HResponse companyGdRes = companyGudzcHConn
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (companyGdRes == null || companyGdRes.dataJsonObject == null) {
				return;
			}
			JSONObject tmpJsonGdObject = companyGdRes.dataJsonObject;
			Log.i("1111",
					"....companychuangjianguz....:"
							+ tmpJsonGdObject.toString());
			readCompanyJson(tmpJsonGdObject, connectionIndex);
			break;
		case companyFzFlag:// 创建负债
			HResponse companyFzRes = companyFzHConn
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (companyFzRes == null || companyFzRes.dataJsonObject == null) {
				return;
			}
			JSONObject tmpJsonFzObject = companyFzRes.dataJsonObject;
			Log.i("1111",
					"....companyfuzaicreate....:" + tmpJsonFzObject.toString());
			readCompanyJson(tmpJsonFzObject, connectionIndex);
			break;
		case companyZiBenCreateFlag:// 创建资本类型
			HResponse companyZiBenCreateRes = companyZiBenCreateHConn
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (companyZiBenCreateRes == null
					|| companyZiBenCreateRes.dataJsonObject == null) {
				return;
			}
			JSONObject tmpJsonZiBenCreateObject = companyZiBenCreateRes.dataJsonObject;
			Log.i("1111", "....companyzibencreate....:"
					+ tmpJsonZiBenCreateObject.toString());
			readCompanyJson(tmpJsonZiBenCreateObject, connectionIndex);
			break;
		case companyGuQuanTouZiCreateFlag:// 创建股权投资
			HResponse companyGuQuanCreateRes = companyGuQuanTouZiCreateHConn
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (companyGuQuanCreateRes == null
					|| companyGuQuanCreateRes.dataJsonObject == null) {
				return;
			}
			JSONObject tmpJsonGuQuanCreateObject = companyGuQuanCreateRes.dataJsonObject;
			Log.i("1111", "....companyzibencreate....:"
					+ tmpJsonGuQuanCreateObject.toString());
			readCompanyJson(tmpJsonGuQuanCreateObject, connectionIndex);
			break;
		case companyGzCheckFlag:// 查询固定资产信息
			HResponse companyRes = companyGzCheckHConn
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (companyRes == null || companyRes.dataJsonObject == null) {
				return;
			}
			JSONObject tmpJsonObject = companyRes.dataJsonObject;
			Log.i("1111",
					"....companyguzichaxun....:" + tmpJsonObject.toString());
			readCompanyJson(tmpJsonObject, connectionIndex);
			break;
		case companyFuzaiCheckFlag:// 查询负债信息
			HResponse companyFuzaiRes = companyFuzaiCheckHConn
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (companyFuzaiRes == null
					|| companyFuzaiRes.dataJsonObject == null) {
				return;
			}
			JSONObject tmpFuzaiJsonObject = companyFuzaiRes.dataJsonObject;
			Log.i("1111",
					"....companyfuzaichaxun....:"
							+ tmpFuzaiJsonObject.toString());
			readCompanyJson(tmpFuzaiJsonObject, connectionIndex);
			break;
		case companyZibenGouChengFlag:// 查询资本构成
			HResponse companyZiBenGouChengRes = companyZibenGouChengHConn
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (companyZiBenGouChengRes == null
					|| companyZiBenGouChengRes.dataJsonObject == null) {
				return;
			}
			JSONObject tmpZiBenGouChengJsonObject = companyZiBenGouChengRes.dataJsonObject;
			Log.i("1111", "....companyfuzaichaxun....:"
					+ tmpZiBenGouChengJsonObject.toString());
			readCompanyJson(tmpZiBenGouChengJsonObject, connectionIndex);
			break;
		case companyGuQuanTouZiFlag:// 查询股权投资信息
			HResponse companyGuQuanTouZiRes = companyGuQuanTouZiHConn
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (companyGuQuanTouZiRes == null
					|| companyGuQuanTouZiRes.dataJsonObject == null) {
				return;
			}
			JSONObject tmpGuQuanTouZiJsonObject = companyGuQuanTouZiRes.dataJsonObject;
			Log.i("1111", "....companyfuzaichaxun....:"
					+ tmpGuQuanTouZiJsonObject.toString());
			readCompanyJson(tmpGuQuanTouZiJsonObject, connectionIndex);
			break;
		case companyGuQuanTouZiDeleteFlag:// 删除股权投资
			HResponse companyGuQuanTouZiDeleteRes = companyGuQuanTouZiDeleteHConn
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (companyGuQuanTouZiDeleteRes == null
					|| companyGuQuanTouZiDeleteRes.dataJsonObject == null) {
				return;
			}
			JSONObject tmpGuQuanTouZiDelJsonObject = companyGuQuanTouZiDeleteRes.dataJsonObject;
			Log.i("1111", "....companyfuzaidel....:"
					+ tmpGuQuanTouZiDelJsonObject.toString());
			readCompanyJson(tmpGuQuanTouZiDelJsonObject, connectionIndex);
			break;
		case companyZiBenGouChengDeleteFlag:
			getServiceInfoTemp(companyZiBenGouChengDeleteHConn);
			break;
		case companyFuzaiDeleteFlag:
			getServiceInfoTemp(companyFuzaiDeleteHConn);
			break;
		case companyGuDingDeleteFlag:
			getServiceInfoTemp(companyGuDingDeleteHConn);
			break;
		case companyGzEditFlag:// 编辑固定资产
			getServiceInfoTemp(companyGzEditHConn);
			break;
		case companyFzEditFlag:// 编辑负债信息
			getServiceInfoTemp(companyFzEditHConn);
			break;
		case companyZiBenEditFlag:// 编辑资本构成信息
			getServiceInfoTemp(companyZiBenEditHConn);
			break;
		case companyGuQuanTouZiEditFlag:// 编辑股权投资信息
			getServiceInfoTemp(companyGuQuanTouZiEditHConn);
			break;
		}
	}

	/**
	 * 对返回数据进行解析的方法
	 * 
	 * @param tmpJsonObject
	 * @param connectionIndex
	 */
	private void readCompanyJson(JSONObject tmpJsonObject, int connectionIndex) {
		String retCode = null;
		try {
			if (tmpJsonObject.has("retCode")) {
				retCode = tmpJsonObject.getString("retCode");
			}

			if ("0000".equals(retCode)) {
				switch (connectionIndex) {
//				case companyGdzcFlag:// 创建固定资产，创建完了要查询，更新界面
//				case companyGuDingDeleteFlag:// 删除固定资产信息
//				case companyGzEditFlag:// 编辑固定资产
//					sendRequestForGudingzichan();
//					break;
				case companyFzFlag:// 创建负债，创建完了要查询，更新界面
				case companyFuzaiDeleteFlag:// 删除负债信息
				case companyFzEditFlag:// 编辑负债信息
					sendRequestForFuzai();
					break;
				case companyZiBenCreateFlag:// 创建资本类型，创建完了要查询，更新界面
				case companyZiBenGouChengDeleteFlag:// 删除资本构成信息
				case companyZiBenEditFlag:// 编辑资本构成信息
					sendRequestForZibenGouChengInfo();
					break;
				case companyGuQuanTouZiCreateFlag:// 创建股权投资
				case companyGuQuanTouZiDeleteFlag:// 删除股权投资
				case companyGuQuanTouZiEditFlag:// 编辑股权投资
					sendRequestForGuQuauTouZiInfo();
					break;
//				case companyGzCheckFlag:// 查询固定资产
//					listGuz.clear();
//					String groupStr = tmpJsonObject.getString("group");
//					if (TextUtils.isEmpty(groupStr)) {
//						Log.i("gudingchaxun......", "gudingchaxun......"
//								+ groupStr);
//						return;
//					} else {
//						listGuz = JSON.parseArray(groupStr,
//								GudingZichanEntity.class);
//						Log.i("gudingchaxun......", "gudingchaxun1......"
//								+ groupStr);
//						Log.i("gudingchaxun......", "gudingchaxun2......"
//								+ listGuz.toString());
//						// showGzInfo();
//					}
//					break;
				case companyFuzaiCheckFlag:// 查询负债
					listFuzai.clear();
					String groupFuzaiStr = tmpJsonObject.getString("group");
					if (TextUtils.isEmpty(groupFuzaiStr)) {
						return;
					} else {
						listFuzai = JSON.parseArray(groupFuzaiStr, FuzaiInfoEntity.class);
						// showGzInfo();
					}
					break;
				case companyZibenGouChengFlag:// 查询资本构成
					listZiBenGouCheng.clear();
					String groupZiBenGouChengStr = tmpJsonObject
							.getString("group");
					if (TextUtils.isEmpty(groupZiBenGouChengStr)) {
//						Log.i("zibengoucheng  chaxun......",
//								"zibengoucheng......" + groupZiBenGouChengStr);
						return;
					} else {
						listZiBenGouCheng = JSON.parseArray(
								groupZiBenGouChengStr,
								ZiBenGouChengEntity.class);
//						Log.i("zibengoucheng  chaxun......",
//								"zibengoucheng......" + groupZiBenGouChengStr);
//						Log.i("zibengoucheng chaxun......",
//								"zibengoucheng ....."
//										+ listZiBenGouCheng.toString());
						// showGzInfo();
					}
					break;
				case companyGuQuanTouZiFlag:
					listGuquanTouZi.clear();
					String groupGuQuanTouZiStr = tmpJsonObject
							.getString("group");
					if (TextUtils.isEmpty(groupGuQuanTouZiStr)) {
//						Log.i("guquantouzi  chaxun......", "guquantouzi......"
//								+ groupGuQuanTouZiStr);
						return;
					} else {
						listGuquanTouZi = JSON.parseArray(groupGuQuanTouZiStr,
								GuQuanTouZiEntity.class);
//						Log.i("guquantouzi  chaxun......", "guquantouzi......"
//								+ groupGuQuanTouZiStr);
//						Log.i("guquantouzi chaxun......", "guquantouzi ....."
//								+ listGuquanTouZi.toString());
						// showGzInfo();
					}
					break;
				default:
					break;
				}
				showRetInfo(connectionIndex);
			} else {
				switch (connectionIndex) {
//				case companyGzCheckFlag:
//					showGzInfo();
//					break;
				case companyFuzaiCheckFlag:
					// 设适配器
					showFuzaiInfo();
					break;
				case companyZibenGouChengFlag:// 显示资本构成查询得到的数据
					showZibengouchengInfo();
					break;
				case companyGuQuanTouZiFlag:// 显示查询到的股权投资信息
					showGuQuanTouZiInfo();
					break;
				default:
					break;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void showRetInfo(int connectionIndex) {
		switch (connectionIndex) {
//		case companyGzCheckFlag:
//			showGzInfo();
//			break;
		case companyFuzaiCheckFlag:
			// 设适配器
			showFuzaiInfo();
			break;
		case companyZibenGouChengFlag:// 显示资本构成查询得到的数据
			showZibengouchengInfo();
			break;
		case companyGuQuanTouZiFlag:// 显示查询到的股权投资信息
			showGuQuanTouZiInfo();
			break;
		default:
			break;
		}
	}

	private void showGuQuanTouZiInfo() {

		// 如果集合大小为0，则隐藏listView
		if (null == listGuquanTouZi || listGuquanTouZi.size() == 0) {
			lvGuquantouzi.setVisibility(View.INVISIBLE);
            return;
		} else {
			lvGuquantouzi.setVisibility(View.VISIBLE);
		}

		final int mRightWidth = lvGuquantouzi.getRightViewWidth();
		final CommonAdapter1 adapterTest = new CommonAdapter1<GuQuanTouZiEntity>(
				getActivity(), listGuquanTouZi,
				R.layout.lv_fourfieldguquantouzi) {

			@Override
			public void convert(CommonViewHolder helper,
					GuQuanTouZiEntity item, final int i) {
				TextView info1 = helper.getView(R.id.tv_info1);
				TextView info2 = helper.getView(R.id.tv_info2);
				TextView info3 = helper.getView(R.id.tv_info3);
				TextView info4 = helper.getView(R.id.tv_info4);

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
						deleteDialog = new DeleteDialog(getActivity(),
								new IsDeleteListener() {
									@Override
									public void onClickDeleteListener(
											boolean isDelete) {
										if (isDelete) {
											String pkid = listGuquanTouZi
													.get(i).getPkid();
											companyGuQuanTouZiDeleteHConn = sendRequestForDeleteInfo(
													"T011159", pkid,
													companyGuQuanTouZiDeleteFlag);
											if (companyGuQuanTouZiDeleteHConn == null) {
												return;
											}
										}
									}
								});
						deleteDialog.show();

						// listGuquanTouZi.remove(i);
						// notifyDataSetChanged();
						// lvGuquantouzi.hiddenShowView();
						// setGuquantouziListViewHeight(lvGuquantouzi,
						// listGuquanTouZi);
					}
				});
				info1.setText(item.getINVEST_ENTER_NAME());
				info2.setText(item.getRATIO_STOCK());
                info3.setText(item.getINVEST_BAL());
                String is_CONTROL = item.getIS_CONTROL();
				if ("0".equals(is_CONTROL)) {
					info4.setText("否");
				} else if ("1".equals(is_CONTROL)) {
					info4.setText("是");
				}
			}

		};
		lvGuquantouzi.setPullstatus(false, false);// 设置是否可以上下拉动
		lvGuquantouzi.setAdapter(adapterTest);// 显示数据
		setGuquantouziListViewHeight(lvGuquantouzi, listGuquanTouZi);
		lvGuquantouzi.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Toast.makeText(getActivity(), position + "条目点击", 0).show();
				String pkid = listGuquanTouZi.get(0).getPkid();
				if ("".equals(pkid) || TextUtils.isEmpty(pkid)) {
					return;
				}
				GuQuanTouZiEntity entity = listGuquanTouZi.get(position);
				pkidGuQuanTouZi = entity.getPkid();

                DialogFragmentCreateGudongInfo editDialog = DialogFragmentCreateGudongInfo.getNewInstance(true, entity);
                editDialog.show(fm, "editGuQuan");
                setCallBackFunction(editDialog);
			}
		});

	}

	//

	// 资本构成信息对应的adapter
	private void showZibengouchengInfo() {

		// 如果集合大小为0，则隐藏listView
		if (null == listZiBenGouCheng || listZiBenGouCheng.size() == 0) {
			lvZibengoucheng.setVisibility(View.INVISIBLE);
			return;
		} else {
			lvZibengoucheng.setVisibility(View.VISIBLE);
		}

		final int mRightWidth = lvZibengoucheng.getRightViewWidth();
		final CommonAdapter1 adapterTest = new CommonAdapter1<ZiBenGouChengEntity>(
				getActivity(), listZiBenGouCheng,
				R.layout.lv_fourfieldzibengoucheng) {

			@Override
			public void convert(CommonViewHolder helper,
					ZiBenGouChengEntity item, final int i) {
				TextView info1 = helper.getView(R.id.tv_info1);
				TextView info2 = helper.getView(R.id.tv_info2);
				TextView info3 = helper.getView(R.id.tv_info3);
				TextView info4 = helper.getView(R.id.tv_info4);

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
                        deleteDialog = new DeleteDialog(getActivity(),
                                new IsDeleteListener() {
                                    @Override
                                    public void onClickDeleteListener(
                                            boolean isDelete) {
                                        if (isDelete) {
                                            String pkid = listZiBenGouCheng
                                                    .get(i).getPKID();
                                            companyZiBenGouChengDeleteHConn = sendRequestForDeleteInfo(
                                                    "T011155", pkid,
                                                    companyZiBenGouChengDeleteFlag);
                                            if (companyZiBenGouChengDeleteHConn == null) {
                                                return;
                                            }
                                        }
                                    }
                                });
                        deleteDialog.show();

                        // listZiBenGouCheng.remove(i);
                        // notifyDataSetChanged();
                        // lvZibengoucheng.hiddenShowView();
                        // setZibengouchengListViewHeight(lvZibengoucheng,
                        // listZiBenGouCheng);
                    }
                });
				info1.setText(item.getSTOCK_NAME());
				String shareholder_TYPE = item.getSHAREHOLDER_TYPE();
				String type = NewCatevalue.getLabel(getActivity(),
						NewCatevalue.stock_type, shareholder_TYPE);
				info2.setText(type);
				info3.setText(item.getRATIO_INVEST());
				String is_CONTROL = item.getIS_CONTROL();
				/**
				 * 先试一下，看看0 和1 分别代表的是什么
				 */
				Log.i("1111", "is_CONTROL:" + is_CONTROL);
				if ("0".equals(is_CONTROL)) {
					info4.setText("否");
				} else if ("1".equals(is_CONTROL)) {
					info4.setText("是");
				}
			}
		};
		lvZibengoucheng.setPullstatus(false, false);// 设置是否可以上下拉动
		lvZibengoucheng.setAdapter(adapterTest);// 显示数据
		setZibengouchengListViewHeight(lvZibengoucheng, listZiBenGouCheng);
		lvZibengoucheng.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Toast.makeText(getActivity(), position + "条目点击", 0).show();
                String pkid = listZiBenGouCheng.get(0).getPKID();
                if ("".equals(pkid) || TextUtils.isEmpty(pkid)) {
                    return;
                }
                ZiBenGouChengEntity entity = listZiBenGouCheng.get(position);
                pkidZibengoucheng = entity.getPKID();

                DialogFragmentCreateZBGCInfo editDialog = DialogFragmentCreateZBGCInfo.getNewInstance(true, entity);
                editDialog.show(fm, "editGudonggoucheng");
                setCallBackFunction(editDialog);
            }
        });
	}

	private void showFuzaiInfo() {

		// 如果集合大小为0，则隐藏listView
		if (null == listFuzai || listFuzai.size() == 0) {
			lvFuzhai.setVisibility(View.INVISIBLE);
			return;
		} else {
			lvFuzhai.setVisibility(View.VISIBLE);
		}

		final int mRightWidth = lvFuzhai.getRightViewWidth();
		final CommonAdapter1 adapterTest = new CommonAdapter1<FuzaiInfoEntity>(
				getActivity(), listFuzai, R.layout.lv_ninefieldcaiwuinfo) {

			@Override
			public void convert(CommonViewHolder helper, FuzaiInfoEntity item,
					final int i) {
				TextView info1 = helper.getView(R.id.tv_info1);
				TextView info2 = helper.getView(R.id.tv_info2);
				TextView info3 = helper.getView(R.id.tv_info3);
				TextView info4 = helper.getView(R.id.tv_info4);
				TextView info5 = helper.getView(R.id.tv_info5);
				TextView info6 = helper.getView(R.id.tv_info6);
				TextView info7 = helper.getView(R.id.tv_info7);
				TextView info8 = helper.getView(R.id.tv_info8);
				TextView info9 = helper.getView(R.id.tv_info9);
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
						deleteDialog = new DeleteDialog(getActivity(),
								new IsDeleteListener() {
									@Override
									public void onClickDeleteListener(
											boolean isDelete) {
										if (isDelete) {
											String pkid = listFuzai.get(i)
													.getPkid();
											companyFuzaiDeleteHConn = sendRequestForDeleteInfo(
													"T011109", pkid,
													companyFuzaiDeleteFlag);
											if (companyFuzaiDeleteHConn == null) {
												return;
											}
										}
									}
								});
						deleteDialog.show();

						// listFuzai.remove(i);
						// notifyDataSetChanged();
						// lvFuzhai.hiddenShowView();
						// setFuzaiListViewHeight(lvFuzhai, listFuzai);
					}
				});
				info1.setText(item.getLAST_ANNUAL_INCOME());
				info2.setText(item.getLAST_ANNUAL_OUTPUT());
				info3.setText(item.getPROFIT());
				info4.setText(item.getHAND_TAX());
				info5.setText(item.getFIX_ASSET());
				info6.setText(item.getHAND_CREDIT());
				info7.setText(item.getINVENTORY());
				info8.setText(item.getPAY_CREDIT());
				info9.setText(item.getDEBT_RENTAL());
			}
		};
		lvFuzhai.setPullstatus(false, false);// 设置是否可以上下拉动
		lvFuzhai.setAdapter(adapterTest);// 显示数据
		setFuzaiListViewHeight(lvFuzhai, listFuzai);
		lvFuzhai.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Toast.makeText(getActivity(), position + "条目点击", 0).show();
                String pkid = listFuzai.get(0).getPkid();
                if ("".equals(pkid) || TextUtils.isEmpty(pkid)) {
                    return;
                }
                FuzaiInfoEntity entity = listFuzai.get(position);
                pkidFuzai = entity.getPkid();
                DialogFragmentCreateCaiWuInfo editDialog = DialogFragmentCreateCaiWuInfo.getNewInstance(true, entity);
                editDialog.show(fm, "editCaiWu");
                setDialogCallBack(editDialog);
            }
        });
	}

	/*private void showGzInfo() {
		final int mRightWidth = lvZichan.getRightViewWidth();
		final CommonAdapter1 adapterTest = new CommonAdapter1<GudingZichanEntity>(
				getActivity(), listGuz, R.layout.lv_eightfieldnew) {

			@Override
			public void convert(CommonViewHolder helper,
					GudingZichanEntity item, final int i) {
				TextView info1 = helper.getView(R.id.tv_info1);
				TextView info2 = helper.getView(R.id.tv_info2);
				TextView info3 = helper.getView(R.id.tv_info3);
				TextView info4 = helper.getView(R.id.tv_info4);
				TextView info5 = helper.getView(R.id.tv_info5);
				TextView info6 = helper.getView(R.id.tv_info6);
				TextView info7 = helper.getView(R.id.tv_info7);
				TextView info8 = helper.getView(R.id.tv_info8);
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
						deleteDialog = new DeleteDialog(getActivity(),
								new IsDeleteListener() {
									@Override
									public void onClickDeleteListener(
											boolean isDelete) {
										if (isDelete) {
											String pkid = listGuz.get(i)
													.getPKID();
											companyGuDingDeleteHConn = sendRequestForDeleteInfo(
													"T011106", pkid,
													companyGuDingDeleteFlag);
											if (companyGuDingDeleteHConn == null) {
												return;
											}
										}
									}
								});
						deleteDialog.show();

						// listGuz.remove(i);
						// notifyDataSetChanged();
						// lvZichan.hiddenShowView();
						// setListViewHeight1(lvZichan, listGuz);
					}
				});
				String propertyType = item.getPROPERTY_TYPE();
				String propertyVal = Catevalue.getName(propertyType,
						CompayValue.infoStr, CompayValue.infoValue);
				info1.setText(propertyVal);
				info2.setText(item.getPROPERTY_NAME());
				info3.setText(item.getPURCHASE_DATE());
				info4.setText(item.getPURCHASE_PRICE());
				info5.setText(item.getAREA());
				info6.setText(item.getCERTIFIED_AREA());
				info7.setText(item.getQUANTITY());
				info8.setText(item.getACCESS_VALUE());
			}
		};
		lvZichan.setPullstatus(false, false);// 设置是否可以上下拉动
		lvZichan.setAdapter(adapterTest);// 显示数据
		setListViewHeight1(lvZichan, listGuz);
		lvZichan.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Toast.makeText(getActivity(), position + "..条目点击.."+ id,
				// 0).show();
				String pkid = listGuz.get(0).getPKID();
				if ("".equals(pkid) || TextUtils.isEmpty(pkid)) {
					return;
				}
				GudingZichanEntity entity = listGuz.get(position);
				pkidGuzi = entity.getPKID();
				DialogFragmentCreateGdzichan editDialog = DialogFragmentCreateGdzichan
						.getNewInstance(1, true, entity);
				editDialog.show(fm, "editGuzi");
				setDialogCallBack(editDialog);
			}
		});
	}
*/
	private void setListViewHeight(ListView lv, List list) {
		int count = list.size();
		if (count == 0) {
			GudingZichanEntity entity = new GudingZichanEntity("", "", "", "",
					"", "", "", "", "", "");
			list.add(entity);
			count = 1;
		}
		LayoutParams params2 = lv.getLayoutParams();
		params2.height = Tool.dip2px(getActivity(), 45) * count
				+ Tool.dip2px(getActivity(), 2);
		lv.setLayoutParams(params2);
	}

	private void setGuquantouziListViewHeight(ListView listView, List list) {
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
			GuQuanTouZiEntity entity = new GuQuanTouZiEntity("", "", "", "",
					"", "", "", "", "", "", "");
			listGuquanTouZi.add(entity);
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (mAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

	private void setZibengouchengListViewHeight(ListView listView, List list) {
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
//			ZiBenGouChengEntity entity = new ZiBenGouChengEntity("", "", "",
//					"", "", "", "", "", "", "", "", "");
//			listZiBenGouCheng.add(entity);
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (mAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
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
//			FuzaiInfoEntity entity = new FuzaiInfoEntity("", "", "", "", "",
//					"", "", "", "", "");
//			listFuzai.add(entity);
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (mAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

/*
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
			GudingZichanEntity entity = new GudingZichanEntity("", "", "", "",
					"", "", "", "", "", "");
			listGuz.add(entity);
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (mAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}
*/

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.btn_creategudingzichan:
//			createGdzichanFragment = DialogFragmentCreateGdzichan
//					.getNewInstance(1);
//			createGdzichanFragment.show(fm, "creategudingzichan");
//			setDialogCallBack(createGdzichanFragment);
//			break;
        case R.id.btn_createcaiwuinfo://财务信息
            DialogFragmentCreateCaiWuInfo createFuzhaiFragment = DialogFragmentCreateCaiWuInfo.getNewInstance();
            createFuzhaiFragment.show(fm, "createfuzhai");
            setDialogCallBack(createFuzhaiFragment);
            break;
        case R.id.btn_createzibengoucheng:// 资本构成
            DialogFragmentCreateZBGCInfo fragmentzibengoucheng = DialogFragmentCreateZBGCInfo.getNewInstance();
            fragmentzibengoucheng.show(fm, "fm");
            setCallBackFunction(fragmentzibengoucheng);
            break;
        case R.id.btn_createguquantouzi:// 股权投资
            DialogFragmentCreateGudongInfo fragmentguquantouzi = DialogFragmentCreateGudongInfo.getNewInstance();
            fragmentguquantouzi.show(fm, "fm");
            setCallBackFunction(fragmentguquantouzi);
            break;
		case R.id.btn_next:
			if (getActivity() instanceof WorkPlaceItemChange) {
				((WorkPlaceItemChange) getActivity()).workPlacestyleChange(2);
			}
			break;
		case R.id.rl_bookbuildingbaseinfo:
			if (getActivity() instanceof WorkPlaceItemChange) {
				((WorkPlaceItemChange) getActivity()).workPlacestyleChange(1);
			}
			break;
		default:
			break;
		}
	}


    private void setCallBackFunction(DialogFragmentCreateZBGCInfo fragment) {
        fragment.setCallBack(new BookbuildingZBGCinfoCallback() {

            @Override
            public void bookbuildingzbgcInfo(Boolean isEdit, String info1, String info2, String info3, String info4,
                                             String info5, String info6, String info7, String info8,
                                             String info9, String info10) {
                if (!isEdit) {// 创建
                    createZiBenGouChengOrGuQuanTouZiInfo(1,
                            info1, info2, info3, info4,
                            info5, info6, info7, info8, info9, info10);
                } else {// 编辑
                    editZiBenGouChengOrGuQuanTouZiInfo(1,
                            info1, info2, info3, info4,
                            info5, info6, info7, info8, info9, info10);
                }
            }
        });
    }

    private void setCallBackFunction(DialogFragmentCreateGudongInfo fragment) {
        fragment.setCallBack(new BookbuildingGudonginfoCallback() {
            @Override
            public void bookbuildingGugongInfo(Boolean isEdit, String info1, String info2, String info3, String info4,
                                               String info5, String info6, String info7, String info8,
                                               String info9) {
                if (!isEdit) {// 创建
                    createZiBenGouChengOrGuQuanTouZiInfo(2,
                            info1, info2, info3, info4,
                            info5, info6, info7, info8, info9, "");
                } else {// 编辑
                    editZiBenGouChengOrGuQuanTouZiInfo(2,
                            info1, info2, info3, info4,
                            info5, info6, info7, info8, info9, "");
                }


            }
        });
    }

	class PullUserAdapter extends BaseAdapter {
		private List list;// 数据集合
		private Context context;
		private int mRightWidth = 0;

		public PullUserAdapter(List list, Context context, int mRightWidth) {
			super();
			this.list = list;
			this.context = context;
			this.mRightWidth = mRightWidth;
		}

		@Override
		public int getCount() {
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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			HolderView holder;
			if (convertView == null) {
				convertView = View.inflate(context, R.layout.lv_eightfieldnew,
						null);
				new HolderView(convertView);
			}
			holder = (HolderView) convertView.getTag();

			if (position % 2 != 0) {
				convertView.setBackgroundColor(context.getResources().getColor(
						R.color.lvtopbg));
			} else {
				convertView.setBackgroundColor(context.getResources().getColor(
						R.color.white));
			}

			EightFieldEntity item = (EightFieldEntity) getItem(position);
			holder.info1.setText(item.getOne());
			holder.info2.setText(item.getTwo());
			holder.info3.setText(item.getThree());
			holder.info4.setText(item.getFour());
			holder.info5.setText(item.getFive());
			holder.info6.setText(item.getSix());
			holder.info7.setText(item.getSeven());
			holder.info8.setText(item.getEight());

			LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
					mRightWidth, LayoutParams.MATCH_PARENT);
			holder.item_left.setLayoutParams(lp1);
			holder.item_right.setLayoutParams(lp2);
			holder.item_right.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.i("positon....", "...." + position);
					list.remove(position);
					notifyDataSetChanged();
//					lvZichan.hiddenShowView();
//					setListViewHeight(lvZichan, list);
				}
			});
			return convertView;
		}

		private class HolderView {
			private LinearLayout item_left;
			private RelativeLayout item_right;
			private TextView item_right_txt;
			private TextView info1;
			private TextView info2;
			private TextView info3;
			private TextView info4;
			private TextView info5;
			private TextView info6;
			private TextView info7;
			private TextView info8;

			public HolderView(View view) {
				info1 = (TextView) view.findViewById(R.id.tv_info1);
				info2 = (TextView) view.findViewById(R.id.tv_info2);
				info3 = (TextView) view.findViewById(R.id.tv_info3);
				info4 = (TextView) view.findViewById(R.id.tv_info4);
				info5 = (TextView) view.findViewById(R.id.tv_info5);
				info6 = (TextView) view.findViewById(R.id.tv_info6);
				info7 = (TextView) view.findViewById(R.id.tv_info7);
				info8 = (TextView) view.findViewById(R.id.tv_info8);
				item_left = (LinearLayout) view.findViewById(R.id.item_left);
				item_right = (RelativeLayout) view
						.findViewById(R.id.item_right);
				item_right_txt = (TextView) view
						.findViewById(R.id.item_right_txt);
				view.setTag(this);
			}
		}

	}


	public void setDialogCallBack(DialogFragmentCreateCaiWuInfo fragment) {
		fragment.setCallBack(new BookbuildingCaiWuInfoCallback() {

			@Override
			public void bookbuildingCaiWuInfo(Boolean isEdit, String info1, String info2,
											  String info3, String info4, String info5, String info6,
											  String info7, String info8, String info9) {
				info9_CaiWu = info9;
				if (!isEdit) {
					// 创建
					createGuZiChanOrFuzaiInfo(2, info1, info2, info3, info4,
							info5, info6, info7, info8);
				} else {
					// 编辑
					editGuZiChanOrFuzaiInfo(2, info1, info2, info3, info4,
							info5, info6, info7, info8);
				}
			}
		});
	}


			/**
	 * 利用回调，将dialog中的数据返回过来，将返回的数据保存起来之后，由于listview数据源变化了，所以还必须得重新刷新页面才行。
	 * 数据源一旦变化了，就要重新刷新页面，该如何刷新页面，这个倒要好好想想啊。
	 */
	public void setDialogCallBack(DialogFragmentCreateGdzichan fragment) {
		fragment.setCallBack(new BookbuildingContentCallback() {

			@Override
			public void bookbuildingContent1(Boolean isEdit, String info1,
					String info2, String info3, String info4, String info5,
					String info6, String info7, String info8) {
				Log.i("1111", "dialog retval1:" + info1 + "..." + info2 + "..."
						+ info3 + "..." + info4 + "..." + info5 + "..." + info6
						+ "..." + info7 + "..." + info8);
				if (!isEdit) {
					// 创建
					createGuZiChanOrFuzaiInfo(2, info1, info2, info3, info4,
							info5, info6, info7, info8);
				} else {
					// 编辑
					editGuZiChanOrFuzaiInfo(2, info1, info2, info3, info4,
							info5, info6, info7, info8);
				}
			}

			@Override
			public void bookbuildingContent(Boolean isEdit, String info1,
					String info2, String info3, String info4, String info5,
					String info6, String info7, String info8) {
				Log.i("1111", "dialog retval2:" + info1 + "..." + info2 + "..."
						+ info3 + "..." + info4 + "..." + info5 + "..." + info6
						+ "..." + info7 + "..." + info8);
				if (!isEdit) {
					// 创建
					createGuZiChanOrFuzaiInfo(1, info1, info2, info3, info4,
							info5, info6, info7, info8);
				} else {
					// 编辑
					editGuZiChanOrFuzaiInfo(1, info1, info2, info3, info4,
							info5, info6, info7, info8);
				}
			}
		});
	}

	private void setDefaultInfo() {
		for (int i = 0; i < 4; i++) {
			FourFieldEntity entity = new FourFieldEntity("abcd", "abce",
					"abcd", "abce");
			// listZibengoucheng.add(entity);
			// listGuquantouzi.add(entity);
		}
	}

	private void setupView(View view) {
//		lvZichan = (SwipeListView) view.findViewById(R.id.lv_gudingzichan);
//		btnCreateGudingzichan = (Button) view.findViewById(R.id.btn_creategudingzichan);
		lvFuzhai = (SwipeListView) view.findViewById(R.id.lv_fuzhai);
		lvZibengoucheng = (SwipeListView) view.findViewById(R.id.lv_zibenleixing);
		lvGuquantouzi = (SwipeListView) view.findViewById(R.id.lv_guquantouzi);
		btnCreateFuzhai = (Button) view.findViewById(R.id.btn_createcaiwuinfo);
		btnCreateZibengoucheng = (Button) view.findViewById(R.id.btn_createzibengoucheng);
		btnCreateGuquantouzi = (Button) view.findViewById(R.id.btn_createguquantouzi);
		btnNext = (Button) view.findViewById(R.id.btn_next);
		rlBaseInfo = (RelativeLayout) view.findViewById(R.id.rl_bookbuildingbaseinfo);
		rlOtherInfo = (RelativeLayout) view.findViewById(R.id.rl_bookbuildingother);
	}

	private void setListener() {
//		btnCreateGudingzichan.setOnClickListener(this);
		btnCreateZibengoucheng.setOnClickListener(this);
		btnCreateGuquantouzi.setOnClickListener(this);
		btnCreateFuzhai.setOnClickListener(this);
		btnNext.setOnClickListener(this);
		rlBaseInfo.setOnClickListener(this);
		rlOtherInfo.setOnClickListener(this);
	}

	// private int dip2px(int dp) {
	// return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
	// getResources().getDisplayMetrics());
	// }

	// class AdapterNewCommon extends BaseAdapter {
	// private List list;// 数据集合
	// private Context context;
	//
	// public AdapterNewCommon(List list, Context context) {
	// super();
	// this.list = list;
	// this.context = context;
	// }
	//
	// @Override
	// public int getCount() {
	// // TODO Auto-generated method stub
	// return list.size();
	// }
	//
	// @Override
	// public EightFieldEntity getItem(int position) {
	// // TODO Auto-generated method stub
	// return (EightFieldEntity) list.get(position);
	// }
	//
	// @Override
	// public long getItemId(int position) {
	// // TODO Auto-generated method stub
	// return position;
	// }
	//
	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	// if (convertView == null) {
	// convertView = View.inflate(context, R.layout.lv_eightfield,
	// null);
	// new ViewHolder(convertView);
	// }
	// ViewHolder holder = (ViewHolder) convertView.getTag();
	// EightFieldEntity item = getItem(position);
	// holder.info1.setText(item.getOne());
	// holder.info2.setText(item.getTwo());
	// holder.info3.setText(item.getThree());
	// holder.info4.setText(item.getFour());
	// holder.info5.setText(item.getFive());
	// holder.info6.setText(item.getSix());
	// holder.info7.setText(item.getSeven());
	// holder.info8.setText(item.getEight());
	// return convertView;
	// }
	//
	// }

	// class ViewHolder {
	// private TextView info1;
	// private TextView info2;
	// private TextView info3;
	// private TextView info4;
	// private TextView info5;
	// private TextView info6;
	// private TextView info7;
	// private TextView info8;
	//
	// public ViewHolder(View view) {
	// info1 = (TextView) view.findViewById(R.id.tv_info1);
	// info2 = (TextView) view.findViewById(R.id.tv_info2);
	// info3 = (TextView) view.findViewById(R.id.tv_info3);
	// info4 = (TextView) view.findViewById(R.id.tv_info4);
	// info5 = (TextView) view.findViewById(R.id.tv_info5);
	// info6 = (TextView) view.findViewById(R.id.tv_info6);
	// info7 = (TextView) view.findViewById(R.id.tv_info7);
	// info8 = (TextView) view.findViewById(R.id.tv_info8);
	// view.setTag(this);
	// }
	// }

	// 股权投资信息对应的adapter
	// private void showGuquantouziInfo() {
	// final int mRightWidth = lvGuquantouzi.getRightViewWidth();
	// final CommonAdapter1 adapterTest = new CommonAdapter1<FourFieldEntity>(
	// getActivity(), listGuquantouzi,
	// R.layout.lv_fourfieldguquantouzi) {
	//
	// @Override
	// public void convert(CommonViewHolder helper, FourFieldEntity item,
	// final int i) {
	// TextView info1 = helper.getView(R.id.tv_info1);
	// TextView info2 = helper.getView(R.id.tv_info2);
	// TextView info3 = helper.getView(R.id.tv_info3);
	// TextView info4 = helper.getView(R.id.tv_info4);
	//
	// LinearLayout llLeft = helper.getView(R.id.item_left);
	// RelativeLayout rlRight = helper.getView(R.id.item_right);
	// TextView tvRight = helper.getView(R.id.item_right_txt);
	//
	// LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
	// LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	// LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
	// mRightWidth, LayoutParams.MATCH_PARENT);
	// llLeft.setLayoutParams(lp1);
	// rlRight.setLayoutParams(lp2);
	// rlRight.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// Log.i("positon....", "...." + i);
	// listGuquantouzi.remove(i);
	// notifyDataSetChanged();
	// lvGuquantouzi.hiddenShowView();
	// setGuquantouziListViewHeight(lvGuquantouzi,
	// listGuquantouzi);
	// }
	// });
	// info1.setText(item.getInfo1());
	// info2.setText(item.getInfo2());
	// info3.setText(item.getInfo3());
	// info4.setText(item.getInfo4());
	// }
	// };
	// lvGuquantouzi.setPullstatus(false, false);// 设置是否可以上下拉动
	// lvGuquantouzi.setAdapter(adapterTest);// 显示数据
	// setGuquantouziListViewHeight(lvGuquantouzi, listGuquantouzi);
	// lvGuquantouzi.setOnItemClickListener(new OnItemClickListener() {
	// @Override
	// public void onItemClick(AdapterView<?> parent, View view,
	// int position, long id) {
	// Toast.makeText(getActivity(), position + "条目点击", 0).show();
	// }
	// });
	// }

	private HConnection sendRequestForDeleteInfo(String transcode, String pkid,
			int flag) {
		HRequest requestHr = null;
		String requestStr = "";
		HConnection tempHConn = null;
		if (Tool.haveNet(getActivity())) {
			String request = Tool.getRequestStr(transcode, pkid);
			requestHr = new HRequest(request, "GET");
//			Log.i("1111", "2222 tongyongshanchu ....:" + requestStr);
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				tempHConn = new HConnection(this, requestHr, LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				tempHConn.setIndex(flag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			toast("当前无网络连接！");
		}
		return tempHConn;
	}

	private void getServiceInfoTemp(HConnection tempHConn) {
		HResponse companyRes = tempHConn
				.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
		if (companyRes == null || companyRes.dataJsonObject == null) {
			return;
		}
		JSONObject tmpJsonObject = companyRes.dataJsonObject;
//		Log.i("1111", "....companyfuzaidel....:" + tmpJsonObject.toString());
		readCompanyJson(tmpJsonObject, connectionIndex);
	}

}

