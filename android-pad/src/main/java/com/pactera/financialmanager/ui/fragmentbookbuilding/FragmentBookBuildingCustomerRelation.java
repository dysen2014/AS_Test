package com.pactera.financialmanager.ui.fragmentbookbuilding;

import android.support.v4.app.FragmentManager;
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

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.callback.BookbuildingContactCallback;
import com.pactera.financialmanager.callback.BookbuildingHelpInfoCallback;
import com.pactera.financialmanager.callback.BookbuildingTeamCallback;
import com.pactera.financialmanager.callback.WorkPlaceItemChange;
import com.pactera.financialmanager.entity.OtherBankEntity;
import com.pactera.financialmanager.ui.DeleteDialog;
import com.pactera.financialmanager.ui.DeleteDialog.IsDeleteListener;
import com.pactera.financialmanager.ui.login.LogoActivity;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.SwipeListView;
import com.pactera.financialmanager.ui.bookbuildingfrocompany.ActivityBaseInfo;
import com.pactera.financialmanager.ui.model.ContantsInfoEntity;
import com.pactera.financialmanager.ui.model.DanBaoInfoEntity;
import com.pactera.financialmanager.ui.model.FiveFieldEntity;
import com.pactera.financialmanager.ui.model.FourFieldEntity;
import com.pactera.financialmanager.ui.model.SevenFieldEntity;
import com.pactera.financialmanager.ui.model.TeamInfoEntity;
import com.pactera.financialmanager.ui.newcommonadapter.CommonAdapter;
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
 * 这是第二步，联络信息的fragmen
 */

public class FragmentBookBuildingCustomerRelation extends ParentFragment
		implements OnClickListener {
	private Button btnContactInfo, btnTeamRelation, btnHelpInfo;
	private Button btnBusinessRelation;// 其他关联方情况
	private Button btnNext;
	private FragmentManager fm;
	private List<FourFieldEntity> list1;
	private List<FiveFieldEntity> list2;
	private List<SevenFieldEntity> list3, list4, list5;
	private SwipeListView lvNewContactInfo, lvTeamRelation;
	private SwipeListView lvHelpInfo;// 对外担保信息
	private SwipeListView lvBusinessRelation;// 其它关联方情况
	private final int companyContantCheckFlag = 93,
			companyContantCreateFlag = 92, companyDanBaoInfoFlag = 91,
			companyDanBaoInfoCreateFlag = 90, companyTeamInfoFlag = 89,
			companyTeamInfoCreateFlag = 88, companyContactDeleteFlag = 87,
			companyDanBaoDeleteFlag = 86, companyJiTuanDeleteFlag = 85,
			companyOtherBankCheckFlag = 84,
			companyOtherBankInfoCreateFlag = 83,
			companyOtherBankDeleteFlag = 82, companyContantEditFlag = 81,
			companyDanBaoInfoEditFlag = 80, companyOtherBankInfoEditFlag = 79,
			companyTeamInfoEditFlag = 78;
	private HConnection companyContantHConn, companyContantCreateHConn,
			companyDanBaoInfoHConn, companyDanBaoInfoCreateHConn,
			companyTeamInfoHConn, companyOtherBankInfoEditHConn,
			companyTeamInfoCreateHConn, companyContantEditHConn,
			companyContactDeleteHConn, companyDanBaoDeleteHConn,
			companyJiTuanDeleteHConn, companyOtherBankCheckHConn,
			companyOtherBankInfoCreateHConn, companyOtherBankDeleteHConn,
			companyDanBaoInfoEditHConn, companyTeamInfoEditHConn;
	private List<ContantsInfoEntity> listContants;
	private List<DanBaoInfoEntity> listDanBao;
	private List<TeamInfoEntity> listTeam;
	private List<OtherBankEntity> listOtherBank;
	// private DialogFragmentCreateContactInfo dialog;
	private DeleteDialog deleteDialog;
	private String pkidContact, pkidDanBao, pkidOtherBank, pkidJiTuan;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_customerrelation, null);
		setupView(view);
		fm = getFragmentManager();
		listContants = new ArrayList<ContantsInfoEntity>();
		listDanBao = new ArrayList<DanBaoInfoEntity>();
		listTeam = new ArrayList<TeamInfoEntity>();
		listOtherBank = new ArrayList<OtherBankEntity>();
		setListener();
		setDefaultInfo();
		sendRequestForContantInfo();// 查询关键成员信息
		sendRequestForDanbaoInfo();// 查询对外担保信息
		sendRequestForOtherBankInfo();// 查询其他关联方信息
		sendRequestForTeamInfo();// 查询客户所属集团信息
		return view;
	}

	// 查询客户所属集团信息
	private void sendRequestForTeamInfo() {
		HRequest hrObj = null;
		String requestStr;
		String custId = ActivityBaseInfo.custID;
		if (Tool.haveNet(getActivity())) {
			requestStr = Tool.getRequestStr("T011119", custId);
			hrObj = new HRequest(requestStr, "GET");
			Log.i("1111", "2222chaxun jituan info:" + requestStr);
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyTeamInfoHConn = new HConnection(this, hrObj,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyTeamInfoHConn.setIndex(companyTeamInfoFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			toast("当前无网络连接！");
		}
	}

	// 编辑客户所属集团信息的
	private void editTeamInfo(String info1, String info2, String info3,
			String info4, String info5) {
		HRequest hrObj = null;
		String requestStr;
		if (Tool.haveNet(getActivity())) {
			requestStr = Tool.getRequest("T011122");
			requestStr = requestStr + "&jsonData={PKID:\"" + pkidJiTuan
					+ "\",GROUP_NAME:\"" // 集团总公司名称
					+ info1 + "\",HIGHER_NAME:\"" // 上级企业名称
					+ info2 + "\",GROUP_RELATION:\"" // 关联关系
					+ info3 + "\",HOLDER_PERTAGE:\"" // 持股比例
					+ info4 + "\",DES:\"" // 备注
					+ info5 + "\"}";
			hrObj = new HRequest(requestStr, "GET");
			Log.i("1111", "2222 edit team info....:" + requestStr);
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyTeamInfoEditHConn = new HConnection(this, hrObj,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyTeamInfoEditHConn.setIndex(companyTeamInfoEditFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			toast("当前无网络连接！");
		}
	}

	// 创建客户所属集团信息的
	private void createTeamInfo(String info1, String info2, String info3,
			String info4, String info5) {
		HRequest hrObj = null;
		String requestStr;
		String custId = ActivityBaseInfo.custID;
		if (Tool.haveNet(getActivity())) {
			requestStr = Tool.getRequest("T011120");
			requestStr = requestStr + "&jsonData={CUSTID:\"" + custId
					+ "\",GROUP_NAME:\"" // 集团总公司名称
					+ info1 + "\",HIGHER_NAME:\"" // 上级企业名称
					+ info2 + "\",GROUP_RELATION:\"" // 关联关系
					+ info3 + "\",HOLDER_PERTAGE:\"" // 持股比例
					+ info4 + "\",DES:\"" // 备注
					+ info5 + "\"}";
			hrObj = new HRequest(requestStr, "GET");
			Log.i("1111", "2222 create team info....:" + requestStr);
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyTeamInfoCreateHConn = new HConnection(this, hrObj,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyTeamInfoCreateHConn.setIndex(companyTeamInfoCreateFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			toast("当前无网络连接！");
		}
	}

	// 查询其他关联方信息
	private void sendRequestForOtherBankInfo() {
		HRequest hrObj = null;
		String requestStr;
		String custId = ActivityBaseInfo.custID;
		if (Tool.haveNet(getActivity())) {
			requestStr = Tool.getRequestStr("T011161", custId);
			hrObj = new HRequest(requestStr, "GET");
			Log.i("1111", "2222chaxun qitaguanlianfang info:" + requestStr);
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyOtherBankCheckHConn = new HConnection(this, hrObj,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyOtherBankCheckHConn.setIndex(companyOtherBankCheckFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			toast("当前无网络连接！");
		}
	}

	/*
	 * 创建其它关联方情况信息
	 */
	private void createOtherBankRelationInfo(String info1, String info2,
			String info3, String info4, String info5, String info6,String info7) {
		HRequest hrObj = null;
		String requestStr;
		String custId = ActivityBaseInfo.custID;
		if (Tool.haveNet(getActivity())) {
			requestStr = Tool.getRequest("T011162");
			requestStr = requestStr + "&jsonData={CUSTID:\"" + custId
					+ "\",RELATE_NAME:\"" // 关联方证件名称
					+ info1 + "\",RELATE_CERT_TYPE:\"" // 关联方证件类型
					+ info2 + "\",RELATE_CERT_NO:\"" // 关联方证件号码
					+ info3 + "\",RELATIONS:\"" // 关联关系
					+ info4 + "\",F_COMPANY:\"" // 法人代表公司
					+ info5 + "\",SETTLE_TYPE:\"" // 结算方式
					+ info6 + "\",DES:\"" // 备注
					+ info7 + "\"}";
			hrObj = new HRequest(requestStr, "GET");
			Log.i("1111", "2222create qitaguanlianfang info....:" + requestStr);
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyOtherBankInfoCreateHConn = new HConnection(this, hrObj,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyOtherBankInfoCreateHConn
						.setIndex(companyOtherBankInfoCreateFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			toast("当前无网络连接！");
		}
	}

	// 编辑其它关联方情况信息
	private void editOtherBankRelationInfo(String info1, String info2,
			String info3, String info4, String info5, String info6,String info7) {
		HRequest hrObj = null;
		String requestStr;
		if (Tool.haveNet(getActivity())) {
			requestStr = Tool.getRequest("T011164");
			requestStr = requestStr + "&jsonData={PKID:\"" + pkidOtherBank
                    + "\",RELATE_NAME:\"" // 关联方证件名称
                    + info1 + "\",RELATE_CERT_TYPE:\"" // 关联方证件类型
                    + info2 + "\",RELATE_CERT_NO:\"" // 关联方证件号码
                    + info3 + "\",RELATIONS:\"" // 关联关系
                    + info4 + "\",F_COMPANY:\"" // 法人代表公司
                    + info5 + "\",SETTLE_TYPE:\"" // 结算方式
                    + info6 + "\",DES:\"" // 备注
                    + info7 + "\"}";
			hrObj = new HRequest(requestStr, "GET");
			Log.i("1111", "2222edit qitaguanlianfang info....:" + requestStr);
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyOtherBankInfoEditHConn = new HConnection(this, hrObj,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyOtherBankInfoEditHConn
						.setIndex(companyOtherBankInfoEditFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			toast("当前无网络连接！");
		}
	}

	// 编辑对外担保信息
	private void editDanBaoInfo(String info1, String info2, String info3,
			String info4, String info5, String info6, String info7) {
		HRequest hrObj = null;
		String requestStr;
		if (Tool.haveNet(getActivity())) {
			requestStr = Tool.getRequest("T011134");
			requestStr = requestStr + "&jsonData={PKID:\"" + pkidDanBao
					+ "\",WARRANT_NAME:\"" // 被担保人名称
					+ info1 + "\",INSURE_MAN:\"" // 担保人
					+ info2 + "\",BUSI_INDUSTRY:\"" // 业务种类
					+ info3 + "\",START_DT:\"" // 起始日期
					+ info4 + "\",EXPIRED_DT:\"" // 到期日期
					+ info5 + "\",WARRANT_AMOUNT:\"" // 担保金额
					+ info6 + "\",BUSI_ORG:\"" // 经办机构
					+ info7 + "\"}";
			hrObj = new HRequest(requestStr, "GET");
			Log.i("1111", "2222 edit danbaoinfo....:" + requestStr);
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyDanBaoInfoEditHConn = new HConnection(this, hrObj,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyDanBaoInfoEditHConn.setIndex(companyDanBaoInfoEditFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			toast("当前无网络连接！");
		}
	}

	// 创建对外担保信息
	private void createDanBaoInfo(String info1, String info2, String info3,
			String info4, String info5, String info6, String info7) {
		HRequest hrObj = null;
		String requestStr;
		String custId = ActivityBaseInfo.custID;
		if (Tool.haveNet(getActivity())) {
			requestStr = Tool.getRequest("T011132");
			requestStr = requestStr + "&jsonData={CUSTID:\"" + custId
					+ "\",WARRANT_NAME:\"" // 被担保人名称
					+ info1 + "\",INSURE_MAN:\"" // 担保人
					+ info2 + "\",BUSI_INDUSTRY:\"" // 业务种类
					+ info3 + "\",START_DT:\"" // 起始日期
					+ info4 + "\",EXPIRED_DT:\"" // 到期日期
					+ info5 + "\",WARRANT_AMOUNT:\"" // 担保金额
					+ info6 + "\",BUSI_ORG:\"" // 经办机构
					+ info7 + "\"}";
			hrObj = new HRequest(requestStr, "GET");
			Log.i("1111", "2222createdanbaoinfo....:" + requestStr);
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyDanBaoInfoCreateHConn = new HConnection(this, hrObj,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyDanBaoInfoCreateHConn
						.setIndex(companyDanBaoInfoCreateFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			toast("当前无网络连接！");
		}
	}

	// 查询对外担保信息
	private void sendRequestForDanbaoInfo() {
		HRequest hrObj = null;
		String requestStr;
		String custId = ActivityBaseInfo.custID;
		if (Tool.haveNet(getActivity())) {
			requestStr = Tool.getRequestStr("T011131", custId);
			hrObj = new HRequest(requestStr, "GET");
			Log.i("1111", "2222duiwaidanbaoinfo:" + requestStr);
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyDanBaoInfoHConn = new HConnection(this, hrObj,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyDanBaoInfoHConn.setIndex(companyDanBaoInfoFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			toast("当前无网络连接！");
		}
	}

	// 编辑关键成员信息
	private void editContactInfo(String info1, String info2, String info3,
			String info4, String info5, String info6, String info7, String info8) {
		HRequest hrObj = null;
		String requestStr;
		if (Tool.haveNet(getActivity())) {
			requestStr = Tool.getRequest("T011114");
			requestStr = requestStr + "&jsonData={PKID:\"" + pkidContact
					+ "\",KEY_MEM_NAME:\"" // 成员姓名
					+ info1 + "\",REL_TYPE:\"" // 关系类型
					+ info2 + "\",KEY_CERT_TYPE:\"" // 证件类型
					+ info3 + "\",KEY_CERT_NO:\"" // 证件号码
					+ info4 + "\",CONTACT_PHONE:\"" // 联系电话
					+ info5 + "\",KEY_CONDITION:\"" // 关键人情况
					+ info6 + "\",MASTERTIME:\"" // 任职时间
					+ info7 + "\",EMPLOYTIME:\"" // 从业时间
					+ info8 + "\"}";
			hrObj = new HRequest(requestStr, "GET");
			Log.i("1111", "2222 contants edit....:" + requestStr);
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyContantEditHConn = new HConnection(this, hrObj,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyContantEditHConn.setIndex(companyContantEditFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			toast("当前无网络连接！");
		}
	}

	// 创建 // 这里缺包括电话在内的几个字段
	private void createContantsInfo(String info1, String info2, String info3,
			String info4, String info5, String info6, String info7, String info8) {
		HRequest hrObj = null;
		String requestStr;
		String custId = ActivityBaseInfo.custID;
		if (Tool.haveNet(getActivity())) {
			requestStr = Tool.getRequest("T011111");
			requestStr = requestStr + "&jsonData={CUSTID:\"" + custId
					+ "\",KEY_MEM_NAME:\"" // 成员姓名
					+ info1 + "\",REL_TYPE:\"" // 关系类型
					+ info2 + "\",KEY_CERT_TYPE:\"" // 证件类型
					+ info3 + "\",KEY_CERT_NO:\"" // 证件号码
					+ info4 + "\",CONTACT_PHONE:\"" // 联系电话
					+ info5 + "\",KEY_CONDITION:\"" // 关健人情况
					+ info6 + "\",MASTERTIME:\"" // 任职时间
					+ info7 + "\",EMPLOYTIME:\"" // 从业时间
					+ info8 + "\"}";

			hrObj = new HRequest(requestStr, "GET");
			Log.i("1111", "2222contantschuangjian....:" + requestStr);
			try {
				HConnection.isShowLoadingProcess = true; // 不显示loading
				companyContantCreateHConn = new HConnection(this, hrObj,
						LogoActivity.user,
						HConnection.RESPONSE_TYPE_COMPLEX_JSON);
				companyContantCreateHConn.setIndex(companyContantCreateFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			toast("当前无网络连接！");
		}
	}

	// 查询关键成员信息
	private void sendRequestForContantInfo() {
		HRequest hrObj = null;
		String requestStr;
		String custId = ActivityBaseInfo.custID;
		if (Tool.haveNet(getActivity())) {
			requestStr = Tool.getRequestStr("T011112", custId);
			requestStr = requestStr + "&size=" + "10" + "&offset=" + "1";
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
		case companyTeamInfoCreateFlag:
			HResponse companyTeamCreateRes = companyTeamInfoCreateHConn
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (companyTeamCreateRes == null
					|| companyTeamCreateRes.dataJsonObject == null) {
				return;
			}
			JSONObject tmpJsonTeamCreateObject = companyTeamCreateRes.dataJsonObject;
			Log.i("1111", "....company teaminfo create....:"
					+ tmpJsonTeamCreateObject.toString());
			readContantJson(tmpJsonTeamCreateObject, connectionIndex);
			break;
		case companyTeamInfoFlag:// 查询客户所属集团信息
			HResponse companyTeamRes = companyTeamInfoHConn
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (companyTeamRes == null || companyTeamRes.dataJsonObject == null) {
				return;
			}
			JSONObject tmpJsonTeamObject = companyTeamRes.dataJsonObject;
			Log.i("1111",
					"....companychaxun....:" + tmpJsonTeamObject.toString());
			readContantJson(tmpJsonTeamObject, connectionIndex);
			break;
		case companyContantCheckFlag:// 查询关键客户联系人
			HResponse companyContantRes = companyContantHConn
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (companyContantRes == null
					|| companyContantRes.dataJsonObject == null) {
				return;
			}
			JSONObject tmpJsonGdObject = companyContantRes.dataJsonObject;
			Log.i("1111", "....companychaxun....:" + tmpJsonGdObject.toString());
			readContantJson(tmpJsonGdObject, connectionIndex);
			break;
		case companyContantCreateFlag:// 创建关键客户联系人
			HResponse companyContantCRes = companyContantCreateHConn
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (companyContantCRes == null
					|| companyContantCRes.dataJsonObject == null) {
				return;
			}
			JSONObject tmpJsonCrObject = companyContantCRes.dataJsonObject;
			Log.i("1111",
					"....companychuangjianguz....:"
							+ tmpJsonCrObject.toString());
			readContantJson(tmpJsonCrObject, connectionIndex);
			break;
		case companyDanBaoInfoFlag:// 查询对外担保信息
			HResponse companyDanBaoRes = companyDanBaoInfoHConn
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (companyDanBaoRes == null
					|| companyDanBaoRes.dataJsonObject == null) {
				return;
			}
			JSONObject dataJsonObject = companyDanBaoRes.dataJsonObject;
			Log.i("1111",
					"....companydanbaoinfo....:" + dataJsonObject.toString());
			readContantJson(dataJsonObject, connectionIndex);
			break;
		case companyDanBaoInfoCreateFlag:// 创建对外担保信息
			HResponse companyDanBaoCreateRes = companyDanBaoInfoCreateHConn
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (companyDanBaoCreateRes == null
					|| companyDanBaoCreateRes.dataJsonObject == null) {
				return;
			}
			JSONObject dataJsonDanBaoObject = companyDanBaoCreateRes.dataJsonObject;
			Log.i("1111",
					"....companydanbaoinfo....:"
							+ dataJsonDanBaoObject.toString());
			readContantJson(dataJsonDanBaoObject, connectionIndex);
			break;
		case companyContactDeleteFlag:// 删除联系人记录
			getServiceInfoTemp(companyContactDeleteHConn);
			break;
		case companyDanBaoDeleteFlag:// 删除联系人记录
			getServiceInfoTemp(companyDanBaoDeleteHConn);
			break;
		case companyJiTuanDeleteFlag:// 删除集团关系记录
			getServiceInfoTemp(companyJiTuanDeleteHConn);
			break;
		case companyOtherBankInfoCreateFlag:// 创建其他关联方情况信息的
			getServiceInfoTemp(companyOtherBankInfoCreateHConn);
			break;
		case companyOtherBankCheckFlag:// 查询其他行关联关系的
			getServiceInfoTemp(companyOtherBankCheckHConn);
			break;
		case companyOtherBankDeleteFlag:// 删除其他行信息的
			getServiceInfoTemp(companyOtherBankDeleteHConn);
			break;
		case companyContantEditFlag:// 编辑关键联系人信息
			getServiceInfoTemp(companyContantEditHConn);
			break;
		case companyDanBaoInfoEditFlag:// 编辑担保信息
			getServiceInfoTemp(companyDanBaoInfoEditHConn);
			break;
		case companyOtherBankInfoEditFlag:// 编辑其他行信息
			getServiceInfoTemp(companyOtherBankInfoEditHConn);
			break;
		case companyTeamInfoEditFlag:// 编辑集团信息
			getServiceInfoTemp(companyTeamInfoEditHConn);
			break;
		default:
			break;
		}
	}

	private void readContantJson(JSONObject tmpJsonGdObject, int connectionIndex) {
		String retCode = null;
		try {
			if (tmpJsonGdObject.has("retCode")) {
				retCode = tmpJsonGdObject.getString("retCode");
			}

			if ("0000".equals(retCode)) {
				switch (connectionIndex) {
				case companyTeamInfoCreateFlag:// 创建集团客户
				case companyJiTuanDeleteFlag:// 删除集团客户信息
				case companyTeamInfoEditFlag:// 编辑集团客户信息
					sendRequestForTeamInfo();
					break;
				case companyDanBaoInfoCreateFlag:// 创建外行担保信息
				case companyDanBaoDeleteFlag:// 删除担保信息
				case companyDanBaoInfoEditFlag:// 编辑担保信息
					sendRequestForDanbaoInfo();// 查询外行担保信息
					break;
				case companyContantCreateFlag:// 创建客户关键信息
				case companyContactDeleteFlag:// 删除客户关键信息
				case companyContantEditFlag:// 编辑客户关键信息
					sendRequestForContantInfo();// 查询客户关键信息
					break;
				case companyOtherBankInfoCreateFlag:// 创建其它关联方情况信息
				case companyOtherBankDeleteFlag:// 删除其它关联方情况信息
				case companyOtherBankInfoEditFlag:// 编辑其它关联方情况信息
					sendRequestForOtherBankInfo();
					break;
				case companyOtherBankCheckFlag:// 查询其他行关联关系
					listOtherBank.clear();
					if (tmpJsonGdObject.has("group")) {
						String otherBankInfo = tmpJsonGdObject
								.getString("group");
						if (TextUtils.isEmpty(otherBankInfo)) {
							Log.i("1111", "....teaminfo.....:" + "返回数据为空");
							return;
						} else {
							listOtherBank = JSON.parseArray(otherBankInfo,
									OtherBankEntity.class);
							Log.i("1111", "otherbankinfo.....chaxun.....:"
									+ listOtherBank.toString());
							showOtherBankInfo();
						}
					}
					break;
				case companyTeamInfoFlag:// 查询客户所属集团信息
					listTeam.clear();
					if (tmpJsonGdObject.has("group")) {
						String teamInfo = tmpJsonGdObject.getString("group");
						if (TextUtils.isEmpty(teamInfo)) {
							Log.i("1111", "....teaminfo.....:" + "返回数据为空");
							return;
						} else {
							listTeam = JSON.parseArray(teamInfo,
									TeamInfoEntity.class);
							Log.i("1111", "teaminfo.....chaxun.....:"
									+ listTeam.toString());
							showTeamInfo();
						}
					}
					break;
				case companyDanBaoInfoFlag:// 查询对外担保信息
					listDanBao.clear();
					if (tmpJsonGdObject.has("group")) {
						String danbaoInfo = tmpJsonGdObject.getString("group");
						if (TextUtils.isEmpty(danbaoInfo)) {
							Log.i("1111", "....danbaoinfo.....:" + "返回数据为空");
							return;
						} else {
							listDanBao = JSON.parseArray(danbaoInfo,
									DanBaoInfoEntity.class);
							Log.i("1111", "danbaoinfo.....chaxun.....:"
									+ listDanBao.toString());
							showDanBaoInfo();
						}
					}
					break;
				case companyContantCheckFlag:// 查询客户关键信息
					listContants.clear();
					if (tmpJsonGdObject.has("group")) {
						String contantStr = tmpJsonGdObject.getString("group");
						if (TextUtils.isEmpty(contantStr)) {
							Log.i("1111", "....contantretinfo:" + "返回数据为空");
							return;
						} else {
							listContants = JSON.parseArray(contantStr,
									ContantsInfoEntity.class);
							Log.i("1111", "contact......chaxun........:"
									+ listContants.toString());
							showContantRetInfo();
						}
					}
					break;

				default:
					break;
				}
			} else {
				Log.i("1111", "...........no info.....");

				/**
				 * 
				 * 从这一点可以看出，无论返回的是否是0000，都要走showOtherBankInfo();这一步，因为要正常展示界面啊。。
				 * 。。。。
				 * 
				 */
				switch (connectionIndex) {
				case companyOtherBankCheckFlag:// 查询其他行关联关系
					showOtherBankInfo();
					break;
				case companyContantCheckFlag:// 查询客户关键信息
					showContantRetInfo();
					break;
				case companyDanBaoInfoFlag:// 查询对外担保信息
					showDanBaoInfo();
					break;
				case companyTeamInfoFlag:// 查询客户所属集团信息
					showTeamInfo();
					break;
				default:
					break;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void showNowListViewHeight(int connectionIndex) {
		switch (connectionIndex) {
		case companyOtherBankInfoCreateFlag:
		case companyOtherBankCheckFlag:
			setOtherBankListViewHeight(lvBusinessRelation, listOtherBank);
			break;

		default:
			break;
		}
	}

	// 显示查询其他行关联信息的
	private void showOtherBankInfo() {

        // 如果集合大小为0，则隐藏listView
        if (listOtherBank.size() == 0) {
            lvBusinessRelation.setVisibility(View.INVISIBLE);
//            return;
        } else {
            lvBusinessRelation.setVisibility(View.VISIBLE);
        }

        final int mRightWidth = lvBusinessRelation.getRightViewWidth();
		final CommonAdapter1<OtherBankEntity> adapterTeam = new CommonAdapter1<OtherBankEntity>(
				getActivity(), listOtherBank, R.layout.lv_otherbankguanlian) {
			@Override
			public void convert(CommonViewHolder helper, OtherBankEntity item,
					final int i) {
				TextView info1 = helper.getView(R.id.tv_info1);
				TextView info2 = helper.getView(R.id.tv_info2);
				TextView info3 = helper.getView(R.id.tv_info3);
				TextView info4 = helper.getView(R.id.tv_info4);
				TextView info5 = helper.getView(R.id.tv_info5);
				TextView info6 = helper.getView(R.id.tv_info6);

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
											String pkid = listOtherBank.get(i)
													.getPkid();
											companyOtherBankDeleteHConn = sendRequestForDeleteInfo(
													"T011163", pkid,
													companyOtherBankDeleteFlag);
											if (companyOtherBankDeleteHConn == null) {
												return;
											}
										}
									}
								});
						deleteDialog.show();

						// listTeam.remove(i);
						// notifyDataSetChanged();
						// lvTeamRelation.hiddenShowView();
						// setTeamListViewHeight(lvTeamRelation, listTeam);
					}
				});
                info1.setText(item.getRELATE_NAME());
                String cert_TYPE = item.getRELATE_CERT_TYPE();
                String typeVal = NewCatevalue.getLabel(getActivity(), NewCatevalue.CERTTP, cert_TYPE);
                info2.setText(typeVal);
				info3.setText(item.getRELATE_CERT_NO());
				String relations = item.getRELATIONS();

//                String relationsVal = Catevalue.getName(relations, CompayValue.infoStr, CompayValue.infoValue);
				String relationsVal = NewCatevalue.getLabel(getActivity(), NewCatevalue.ent_other_rel, relations);

				info4.setText(relationsVal);
				info5.setText(item.getF_COMPANY());
				info6.setText(item.getDES());
			}
		};
		lvBusinessRelation.setPullstatus(false, false);// 设置是否可以上下拉动
		lvBusinessRelation.setAdapter(adapterTeam);// 显示数据
		setOtherBankListViewHeight(lvBusinessRelation, listOtherBank);
		lvBusinessRelation.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// toast(position + "条目点击", 0).show();
				String pkid = listOtherBank.get(0).getPkid();
				if ("".equals(pkid) || TextUtils.isEmpty(pkid)) {
					return;
				}
				OtherBankEntity entity = listOtherBank.get(position);
				pkidOtherBank = entity.getPkid();
				DialogFragmentCreateBusinessFri fragmentbusfri = DialogFragmentCreateBusinessFri
						.getNewInstance(true, entity);
				fragmentbusfri.show(fm, "fmb");
				setCallBackBusFri(fragmentbusfri);
			}
		});
	}

	/**
	 * 显示查询客户所属集团信息的
	 */
	private void showTeamInfo() {

        // 如果集合大小为0，则隐藏listView
        if (listTeam.size() == 0) {
            lvTeamRelation.setVisibility(View.INVISIBLE);
//            return;
        } else {
            lvTeamRelation.setVisibility(View.VISIBLE);
        }

        final int mRightWidth = lvTeamRelation.getRightViewWidth();
		final CommonAdapter1<TeamInfoEntity> adapterTeam = new CommonAdapter1<TeamInfoEntity>(
				getActivity(), listTeam, R.layout.lv_fivefield) {
			@Override
			public void convert(CommonViewHolder helper, TeamInfoEntity item,
					final int i) {
				TextView info1 = helper.getView(R.id.tv_info1);
				TextView info2 = helper.getView(R.id.tv_info2);
				TextView info3 = helper.getView(R.id.tv_info3);
				TextView info4 = helper.getView(R.id.tv_info4);
				TextView info5 = helper.getView(R.id.tv_info5);

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
											String pkid = listTeam.get(i)
													.getPkid();
											companyJiTuanDeleteHConn = sendRequestForDeleteInfo(
													"T011121", pkid,
													companyJiTuanDeleteFlag);
											if (companyJiTuanDeleteHConn == null) {
												return;
											}
										}
									}
								});
						deleteDialog.show();

						// listTeam.remove(i);
						// notifyDataSetChanged();
						// lvTeamRelation.hiddenShowView();
						// setTeamListViewHeight(lvTeamRelation, listTeam);
					}
				});
				info1.setText(item.getGROUP_NAME());
				info2.setText(item.getHIGHER_NAME());
				String group_RELATION = item.getGROUP_RELATION();
                String teamRelaVal= NewCatevalue.getLabel(getActivity(), NewCatevalue.group_rel_type, group_RELATION);
				info3.setText(teamRelaVal);
				info4.setText(item.getHOLDER_PERTAGE());
				info5.setText(item.getDES());
			}
		};
		lvTeamRelation.setPullstatus(false, false);// 设置是否可以上下拉动
		lvTeamRelation.setAdapter(adapterTeam);// 显示数据
		setTeamListViewHeight(lvTeamRelation, listTeam);
		lvTeamRelation.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// toast(position + "条目点击", 0).show();
				String pkid = listTeam.get(0).getPkid();
				if ("".equals(pkid) || TextUtils.isEmpty(pkid)) {
					return;
				}
				TeamInfoEntity entity = listTeam.get(position);
				pkidJiTuan = entity.getPkid();
				DialogFragmentCreateTeamInfo fragmentteam = DialogFragmentCreateTeamInfo
						.getNewInstance(true, entity);
				fragmentteam.show(fm, "fmt");
				setCallBackFunctionTeam(fragmentteam);
			}
		});
	}

	/**
	 * 显示查询对外担保信息的结果的
	 */
	private void showDanBaoInfo() {
        // 如果集合大小为0，则隐藏listView
        if (listDanBao.size() == 0) {
            lvHelpInfo.setVisibility(View.INVISIBLE);
//            return;
        } else {
            lvHelpInfo.setVisibility(View.VISIBLE);
        }

		final int mRightWidth = lvHelpInfo.getRightViewWidth();
		final CommonAdapter1<DanBaoInfoEntity> adapter5 = new CommonAdapter1<DanBaoInfoEntity>(
				getActivity(), listDanBao, R.layout.lv_itemcustomerrelation) {
			@Override
			public void convert(CommonViewHolder helper, DanBaoInfoEntity item,
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
						deleteDialog = new DeleteDialog(getActivity(),
								new IsDeleteListener() {

									@Override
									public void onClickDeleteListener(
											boolean isDelete) {
										if (isDelete) {
											String pkid = listDanBao.get(i)
													.getPkid();
											companyDanBaoDeleteHConn = sendRequestForDeleteInfo(
													"T011133", pkid,
													companyDanBaoDeleteFlag);
											if (companyDanBaoDeleteHConn == null) {
												return;
											}
										}
									}
								});
						deleteDialog.show();

						// listDanBao.remove(i);
						// notifyDataSetChanged();
						// lvHelpInfo.hiddenShowView();
						// setDanBaoListViewHeight(lvHelpInfo, listDanBao);
					}
				});

				info1.setText(item.getWARRANT_NAME());
				info2.setText(item.getINSURE_MAN());
				String industry = item.getBUSI_INDUSTRY();
//				String industryVal = Catevalue.getName(industry,
//						CompayValue.infoStr, CompayValue.infoValue);
				String industryVal = NewCatevalue.getLabel(getActivity(), NewCatevalue.out_warrant_type, industry);

				info3.setText(industryVal);
				info4.setText(item.getSTART_DT());
				info5.setText(item.getEXPIRED_DT());
				info6.setText(item.getWARRANT_AMOUNT());
				info7.setText(item.getBUSI_ORG());
			}
		};
		lvHelpInfo.setPullstatus(false, false);// 设置是否可以上下拉动
		lvHelpInfo.setAdapter(adapter5);// 显示数据
		setDanBaoListViewHeight(lvHelpInfo, listDanBao);
		lvHelpInfo.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// toast(position + "条目点击", 0).show();
				String pkid = listDanBao.get(0).getPkid();
				if ("".equals(pkid) || TextUtils.isEmpty(pkid)) {
					return;
				}
				DanBaoInfoEntity entity = listDanBao.get(position);
				pkidDanBao = entity.getPkid();
				DialogFragmentCreateHelpInfo fragmenthelpinfo = DialogFragmentCreateHelpInfo
						.getNewInstance(true, entity);
				fragmenthelpinfo.show(fm, "fmh");
				setCallBackHelp(fragmenthelpinfo);
			}
		});
	}

	/**
	 * 这个联系人信息的适配器里的地方要改的，现在先把界面改一下，稍后再来改这个
	 */
	private void showContantRetInfo() {
        // 如果集合大小为0，则隐藏listView
        if (listContants.size() == 0) {
            lvNewContactInfo.setVisibility(View.INVISIBLE);
//            return;
        } else {
            lvNewContactInfo.setVisibility(View.VISIBLE);
        }

        final int mRightWidth = lvNewContactInfo.getRightViewWidth();
		final CommonAdapter1 adapterTest = new CommonAdapter1<ContantsInfoEntity>(
				getActivity(), listContants, R.layout.lv_sevenfieldcontactinfo) {

			@Override
			public void convert(CommonViewHolder helper,
					ContantsInfoEntity item, final int i) {
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
											String pkid = listContants.get(i)
													.getPKID();
											companyContactDeleteHConn = sendRequestForDeleteInfo(
													"T011113", pkid,
													companyContactDeleteFlag);
											if (companyContactDeleteHConn == null) {
												return;
											}
										}
									}
								});
						deleteDialog.show();

						//
						// listContants.remove(i);
						// notifyDataSetChanged();
						// lvNewContactInfo.hiddenShowView();
						// setNewContactListViewHeight(lvNewContactInfo,
						// listContants);
					}
				});
				info1.setText(item.getKEY_MEM_NAME());

                String rel_TYPE = NewCatevalue.getLabel(getActivity(), NewCatevalue.key_mem_type, item.getREL_TYPE());
				info2.setText(rel_TYPE);

                String certTypeVal =  NewCatevalue.getLabel(getActivity(), NewCatevalue.CERTTP, item.getKEY_CERT_TYPE());
                info3.setText(certTypeVal);

				info4.setText(item.getKEY_CERT_NO());
				info5.setText(item.getCONTACT_PHONE());// 联系电话
				info6.setText(item.getKEY_CONDITION());// 关键人情况
				info7.setText(item.getMASTERTIME());// 任职时间
				info8.setText(item.getEMPLOYTIME());// 从业时间
				// String top_DEGREE = item.getTOP_DEGREE();
				// String topDegree = Catevalue.getName(top_DEGREE,
				// CompayValue.infoStr, CompayValue.infoValue);
				// info8.setText(topDegree);// 最高学历 目前为空，后台没有返回数据
			}
		};
		lvNewContactInfo.setPullstatus(false, false);// 设置是否可以上下拉动
		lvNewContactInfo.setAdapter(adapterTest);// 显示数据
		setNewContactListViewHeight(lvNewContactInfo, listContants);
		lvNewContactInfo.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// toast(position + "条目点击", 0).show();
				String pkid = listContants.get(0).getPKID();
				if ("".equals(pkid) || TextUtils.isEmpty(pkid)) {
					return;
				}
				ContantsInfoEntity entity = listContants.get(position);
				pkidContact = entity.getPKID();
				DialogFragmentCreateContactInfo editDialog = DialogFragmentCreateContactInfo
						.getNewInstance(true, entity);
				editDialog.show(fm, "editContact");
				setDialogCallback(editDialog);
			}
		});
	}

	private void setOtherBankListViewHeight(ListView listView, List list) {
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
			OtherBankEntity entity = new OtherBankEntity("", "", "", "", "",
					"", "", "", "", "", "");
			listOtherBank.add(entity);
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (mAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

	private void setTeamListViewHeight(ListView listView, List list) {
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
			TeamInfoEntity entity = new TeamInfoEntity("", "", "", "", "", "");
			listTeam.add(entity);
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (mAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

	private void setNewContactListViewHeight(ListView listView, List list) {
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
			ContantsInfoEntity entity = new ContantsInfoEntity("", "", "", "",
					"", "", "", "", "", "", "", "");
			listContants.add(entity);
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (mAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

	private void setDanBaoListViewHeight(ListView listView, List list) {
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
			DanBaoInfoEntity entity = new DanBaoInfoEntity("", "", "", "", "",
					"", "", "", "");
			listDanBao.add(entity);
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (mAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

	private void setDefaultInfo() {
		// BaseAdapter<FourFieldEntity> adapter1=new
		// BaseAdapter<FourFieldEntity>(getActivity(),list1,R.layout.lv_fourfield)
		// {
		// @Override
		// public void convert(CommonViewHolder helper, FourFieldEntity item) {
		// TextView info1 = helper.getView(R.id.tv_info1);
		// TextView info2 = helper.getView(R.id.tv_info2);
		// TextView info3 = helper.getView(R.id.tv_info3);
		// TextView info4 = helper.getView(R.id.tv_info4);
		// info1.setText(item.getInfo1());
		// info2.setText(item.getInfo2());
		// info3.setText(item.getInfo3());
		// info4.setText(item.getInfo4());
		// }
		// };

		// BaseAdapter<FiveFieldEntity> adapter2 = new
		// BaseAdapter<FiveFieldEntity>(
		// getActivity(), list2, R.layout.lv_fivefield) {
		// @Override
		// public void convert(CommonViewHolder helper, FiveFieldEntity item) {
		// TextView info1 = helper.getView(R.id.tv_info1);
		// TextView info2 = helper.getView(R.id.tv_info2);
		// TextView info3 = helper.getView(R.id.tv_info3);
		// TextView info4 = helper.getView(R.id.tv_info4);
		// TextView info5 = helper.getView(R.id.tv_info5);
		// info1.setText(item.getInfo1());
		// info2.setText(item.getInfo2());
		// info3.setText(item.getInfo3());
		// info4.setText(item.getInfo4());
		// info5.setText(item.getInfo5());
		//
		// }
		// };

		// BaseAdapter<SevenFieldEntity> adapter3 = new
		// BaseAdapter<SevenFieldEntity>(
		// getActivity(), list3, R.layout.lv_othergulianfanginfo) {
		// @Override
		// public void convert(CommonViewHolder helper, SevenFieldEntity item) {
		// TextView info1 = helper.getView(R.id.tv_info1);
		// TextView info2 = helper.getView(R.id.tv_info2);
		// TextView info3 = helper.getView(R.id.tv_info3);
		// TextView info4 = helper.getView(R.id.tv_info4);
		// TextView info5 = helper.getView(R.id.tv_info5);
		// TextView info6 = helper.getView(R.id.tv_info6);
		// info1.setText(item.getInfo1());
		// info2.setText(item.getInfo2());
		// info3.setText(item.getInfo3());
		// info4.setText(item.getInfo4());
		// info5.setText(item.getInfo5());
		// info6.setText(item.getInfo6());
		// }
		// };

		CommonAdapter<SevenFieldEntity> adapter4 = new CommonAdapter<SevenFieldEntity>(
				getActivity(), list4, R.layout.lv_sevenfieldcustomerrelation2) {
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

		// BaseAdapter<SevenFieldEntity> adapter5 = new
		// BaseAdapter<SevenFieldEntity>(
		// getActivity(), list5, R.layout.lv_itemcustomerrelation) {
		// @Override
		// public void convert(CommonViewHolder helper, SevenFieldEntity item) {
		// TextView info1 = helper.getView(R.id.tv_info1);
		// TextView info2 = helper.getView(R.id.tv_info2);
		// TextView info3 = helper.getView(R.id.tv_info3);
		// TextView info4 = helper.getView(R.id.tv_info4);
		// TextView info5 = helper.getView(R.id.tv_info5);
		// TextView info6 = helper.getView(R.id.tv_inAfo6);
		// TextView info7 = helper.getView(R.id.tv_info7);
		// TextView info8 = helper.getView(R.id.tv_info8);
		// info1.setText(item.getInfo1());
		// info2.setText(item.getInfo2());
		// info3.setText(item.getInfo3());
		// info4.setText(item.getInfo4());
		// info5.setText(item.getInfo5());
		// info6.setText(item.getInfo6());
		// info7.setText(item.getInfo7());
		// info8.setText("");
		// }
		// };

		// lvGudongInfo.setAdapter(adapter1);
		// lvTeamRelation.setAdapter(adapter2);
		// lvBusinessRelation.setAdapter(adapter3);
		// lvHelpInfo.setAdapter(adapter5);
	}

	private void setListener() {
		btnNext.setOnClickListener(this);
		btnContactInfo.setOnClickListener(this);
		btnTeamRelation.setOnClickListener(this);
		btnBusinessRelation.setOnClickListener(this);
		btnHelpInfo.setOnClickListener(this);
	}

	private void setupView(View view) {
		btnContactInfo = (Button) view.findViewById(R.id.btn_createcontactinfo);
		btnTeamRelation = (Button) view
				.findViewById(R.id.btn_creategteamrelation);
		btnBusinessRelation = (Button) view
				.findViewById(R.id.btn_businessrelation);// 其他关联方情况
		btnHelpInfo = (Button) view.findViewById(R.id.btn_helpinfo);

		lvNewContactInfo = (SwipeListView) view
				.findViewById(R.id.lv_gudonginfo);
		lvTeamRelation = (SwipeListView) view
				.findViewById(R.id.lv_teamrelation);
		lvBusinessRelation = (SwipeListView) view
				.findViewById(R.id.lv_businessrealtion);
		lvHelpInfo = (SwipeListView) view.findViewById(R.id.lv_helpinfo);

		btnNext = (Button) view.findViewById(R.id.btn_next);

		// list1 = new ArrayList<FourFieldEntity>();
		// for (int i = 0; i < 4; i++) {
		// FourFieldEntity entity=new FourFieldEntity("张三", "董事长", "60%", "是");
		// list1.add(entity);
		// }
		// int num = list1.size();
		// LayoutParams params = lvGudongInfo.getLayoutParams();
		// params.height=Tool.dip2px(getActivity(),
		// 45)*num+Tool.dip2px(getActivity(), 6);
		// lvGudongInfo.setLayoutParams(params);

		list2 = new ArrayList<FiveFieldEntity>();
		for (int i = 0; i < 4; i++) {
			FiveFieldEntity entity = new FiveFieldEntity("晓天汇利股份有限公司",
					"晓天汇利集团", "合作关系", "40%", "重要合作伙伴");
			list2.add(entity);
		}
		int num2 = list2.size();
		setLvHeight(lvTeamRelation, num2);

		list3 = new ArrayList<SevenFieldEntity>();
		for (int i = 0; i < 5; i++) {
			SevenFieldEntity entity = new SevenFieldEntity("大成济民集团", "D12121",
					"合作关系", "营业执照", "11111222222333333", "现金结算", "123213354313");
			list3.add(entity);
		}
		int num3 = list3.size();
		setLvHeight(lvBusinessRelation, num3);

		list4 = new ArrayList<SevenFieldEntity>();
		for (int i = 0; i < 5; i++) {
			SevenFieldEntity entity = new SevenFieldEntity("大成济民集团", "D12121",
					"合作关系", "营业执照", "11111222222333333", "现金结算", "123213354313");
			list4.add(entity);
		}
		int num4 = list4.size();
		// setLvHeight(lvCompeteRelation, num4);

		list5 = new ArrayList<SevenFieldEntity>();
		for (int i = 0; i < 5; i++) {
			SevenFieldEntity entity = new SevenFieldEntity("大成济民集团", "D12121",
					"合作业务", "2015-9-7", "2016-8-9", "1000000.00",
					"123213354313");
			list5.add(entity);
		}
		int num5 = list5.size();
		setLvHeight(lvHelpInfo, num5);
	}

	public void setLvHeight(ListView lv, int num) {
		LayoutParams params = lv.getLayoutParams();
		params.height = Tool.dip2px(getActivity(), 45) * num
				+ Tool.dip2px(getActivity(), 6);
		lv.setLayoutParams(params);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_createcontactinfo://创建关键成员信息
			// DialogFragmentCreateGudongInfo
			// fragmentgudong=DialogFragmentCreateGudongInfo.getNewInstance();
			// fragmentgudong.show(fm, "fm");
			// setCallBackFunction(fragmentgudong);
			DialogFragmentCreateContactInfo dialog = DialogFragmentCreateContactInfo
					.getNewInstance();
			dialog.show(fm, "ssdsds");
			setDialogCallback(dialog);
			break;
		case R.id.btn_creategteamrelation:// 创建客户所属集团
			DialogFragmentCreateTeamInfo fragmentteam = DialogFragmentCreateTeamInfo
					.getNewInstance();
			fragmentteam.show(fm, "fmt");
			setCallBackFunctionTeam(fragmentteam);
			break;
		case R.id.btn_businessrelation:// 创建其他关联方情况
			DialogFragmentCreateBusinessFri fragmentbusfri = DialogFragmentCreateBusinessFri
					.getNewInstance();
			fragmentbusfri.show(fm, "fmb");
			setCallBackBusFri(fragmentbusfri);
			break;
		// case R.id.btn_competerelation:
		// DialogFragmentCreateComplete fragmentcomplete =
		// DialogFragmentCreateComplete
		// .getNewInstance();
		// fragmentcomplete.show(fm, "fmc");
		// setCallBackCom(fragmentcomplete);
		// break;
		case R.id.btn_helpinfo:
			DialogFragmentCreateHelpInfo fragmenthelpinfo = DialogFragmentCreateHelpInfo
					.getNewInstance();
			fragmenthelpinfo.show(fm, "fmh");
			setCallBackHelp(fragmenthelpinfo);
			break;
		case R.id.btn_next:
			if (getActivity() instanceof WorkPlaceItemChange) {
				((WorkPlaceItemChange) getActivity()).workPlacestyleChange(3);
			}
			break;
		default:
			break;
		}
	}

	private void setDialogCallback(DialogFragmentCreateContactInfo dialog2) {
		dialog2.setCallBack(new BookbuildingContactCallback() {
			@Override
			public void bookbuildingContact(Boolean isEdit, String info1,
					String info2, String info3, String info4, String info5,
					String info6, String info7, String info8) {
				// toast("3333333", 1).show();
				if (!isEdit) {
					// 创建
					createContantsInfo(info1, info2, info3, info4, info5,
							info6, info7, info8);
				} else {
					// 编辑
					editContactInfo(info1, info2, info3, info4, info5, info6,
							info7, info8);
				}
			}
		});
	}

	private void setCallBackHelp(DialogFragmentCreateHelpInfo fragmenthelpinfo) {
		fragmenthelpinfo.setCallBack(new BookbuildingHelpInfoCallback() {
			@Override
			public void bookbuildingHelpInfo(Boolean isEdit, String info1,
					String info2, String info3, String info4, String info5,
					String info6, String info7) {
				if (!isEdit) {
					// 创建
					createDanBaoInfo(info1, info2, info3, info4, info5, info6,
							info7);// 创建对外担保信息
				} else {
					// 编辑
					editDanBaoInfo(info1, info2, info3, info4, info5, info6,
							info7);
				}
			}
		});
	}

	// private void setCallBackCom(DialogFragmentCreateComplete
	// fragmentcomplete) {
	// fragmentcomplete.setCallBack(new BookbuildingContactCallback() {
	// @Override
	// public void bookbuildingContact(String info1, String info2,
	// String info3, String info4, String info5, String info6,
	// String info7) {
	// toast(info7, 0).show();
	// }
	// });
	// }

	/**
	 * @param fragmentbusfri
	 */
	private void setCallBackBusFri(
			DialogFragmentCreateBusinessFri fragmentbusfri) {
		fragmentbusfri.setCallBack(new BookbuildingContactCallback() {
			@Override
			public void bookbuildingContact(Boolean isEdit, String info1,
					String info2, String info3, String info4, String info5,
					String info6, String info7, String info8) {
				// 这个接口里只有前7个字段是有用的，第八个字段是没有用的
				// toast(info7, 0).show();
				if (!isEdit) {
					createOtherBankRelationInfo(info1, info2, info3, info4,
							info5, info6,info7);
				} else {
					editOtherBankRelationInfo(info1, info2, info3, info4,
							info5, info6,info7);
				}
			}
		});
	}

	private void setCallBackFunctionTeam(
			DialogFragmentCreateTeamInfo fragmentteam) {
		fragmentteam.setCallBack(new BookbuildingTeamCallback() {
			@Override
			public void bookbuildingTeam(Boolean isEdit, String info1,
					String info2, String info3, String info4, String info5) {
				if (!isEdit) {
					/**
					 * 创建客户所属集团信息
					 */
					createTeamInfo(info1, info2, info3, info4, info5);
				} else {
					// 编辑
					editTeamInfo(info1, info2, info3, info4, info5);
				}
			}
		});
	}

	// private void setCallBackFunction(DialogFragmentCreateGudongInfo fragment)
	// {
	// fragment.setCallBack(new BookbuildingGudonginfoCallback() {
	// @Override
	// public void bookbuildingGugongInfo(String info1, String info2,
	// String info3, String info4) {
	// toast(info4, 0).show();
	// }
	// });
	// }

	@Override
	public void onResume() {
		if (fm == null) {
			fm = getFragmentManager();
		}
		super.onResume();
	}

	private HConnection sendRequestForDeleteInfo(String transcode, String pkid,
			int flag) {
		HRequest requestHr = null;
		String requestStr = "";
		HConnection tempHConn = null;
		if (Tool.haveNet(getActivity())) {
			String request = Tool.getRequestStr(transcode, pkid);
			requestHr = new HRequest(request, "GET");
			Log.i("1111", "2222 tongyongshanchu ....:" + requestStr);
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
		Log.i("1111", "....companyfuzaidel....:" + tmpJsonObject.toString());
		readContantJson(tmpJsonObject, connectionIndex);
	}

}
