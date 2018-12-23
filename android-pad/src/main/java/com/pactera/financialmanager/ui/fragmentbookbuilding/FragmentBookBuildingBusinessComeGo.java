package com.pactera.financialmanager.ui.fragmentbookbuilding;

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
import com.pactera.financialmanager.callback.BookbuildingotherbankLoanInfoCallback;
import com.pactera.financialmanager.callback.BookbuildingotherbankSignProductCallback;
import com.pactera.financialmanager.callback.BookbuildingotherbanklicaiCallback;
import com.pactera.financialmanager.callback.BookbuildingotherbankmoneyCallback;
import com.pactera.financialmanager.callback.WorkPlaceItemChange;
import com.pactera.financialmanager.ui.DeleteDialog;
import com.pactera.financialmanager.ui.DeleteDialog.IsDeleteListener;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.SwipeListView;
import com.pactera.financialmanager.ui.bookbuildingfrocompany.ActivityBaseInfo;
import com.pactera.financialmanager.ui.model.OtherBankLiCaiEntity;
import com.pactera.financialmanager.ui.model.OtherBankLoanInfoPublic;
import com.pactera.financialmanager.ui.model.OtherBankMoneyEntity;
import com.pactera.financialmanager.ui.model.OtherBankSignProductPublic;
import com.pactera.financialmanager.ui.newcommonadapter.CommonAdapter1;
import com.pactera.financialmanager.ui.newcommonadapter.CommonViewHolder;
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
 * 这是第三步，业务往来信息的fragment
 */
public class FragmentBookBuildingBusinessComeGo extends ParentFragment implements OnClickListener {

    // 查询全部信息
//	private final int companyBenBankMoneyFlag = 80;
    private final int companyOtherBankMoneyFlag = 82;
    private final int companyOtherBankLiCaiFlag = 84;
    private final int companyOtherBankLoanInfoFlag = 884;
    private final int companyOtherBankSignProductFlag = 885;
    // 创建单条信息
//	private final int companyOwnBankCreateFlag = 81;
    private final int companyOtherBankCreateFlag = 83;
    private final int companyOtherBankLiCaiCreateFlag = 85;
    private final int companyOtherBankLoanInfoCreateFlag = 886;
    private final int companyOtherBankSignProductCreateFlag = 887;
    // 编辑单条信息
//	private final int companyOwnBankEditFlag = 89;
    private final int companyOtherBankMoneyEditFlag = 90;
    private final int companyOtherBankLiCaiEditFlag = 91;
    private final int companyOtherBankLoanInfoEditFlag = 911;
    private final int companyOtherBankSignProductEditFlag = 912;
    // 删除单条信息
//	private final int companyOwnBankMoneyDeleteFlag = 86;
    private final int companyOtherBankMoneyDeleteFlag = 87;
    private final int companyOtherBankLiCaiDeleteFlag = 88;
    private final int companyOtherBankLoanInfoDeleteFlag = 889;
    private final int companyOtherBankSignProductDeleteFlag = 890;
    private SwipeListView lvOtherBankDeposit;
    private SwipeListView lvOtherBankLiCai;
    private SwipeListView lvOtherBankLoan;
    private SwipeListView lvOtherBankSignProduct;
    private Button btnNext, btnCreateOtherBankLoan, btnCreateOtherBankDeposit, btnLiCai, btnOtherBankSignProduct;
    //	private List<OwnBankMoneyEntity> listOwnBank;
    private List<OtherBankMoneyEntity> listOtherBank;
    private List<OtherBankLoanInfoPublic> listOtherBankLoanInfo;
    private List<OtherBankLiCaiEntity> listOtherBankLiCai;
    private List<OtherBankSignProductPublic> listOtherBankSignProduct;
    private FragmentManager fm;
    private DeleteDialog deleteDialog;
    //	private HConnection companyBenBankMoneyHConn;
    private HConnection companyOtherBankMoneyHConn;
    private HConnection companyOtherBankLiCaiHConn;
    private HConnection companyOtherBankLoanInfoHConn;
    private HConnection companyOtherBankSignProductHConn;
    //	private HConnection companyOwnBankMoneyCreateHConn;
    private HConnection companyOtherBankMoneyCreateHConn;
    private HConnection companyOtherBankLiCaiCreateHConn;
    private HConnection companyOtherBankLoanInfoCreateHConn;
    private HConnection companyOtherBankSignProductCreateHConn;
    //    private HConnection companyOwnBankMoneyEditHConn;
    private HConnection companyOtherBankMoneyEditHConn;
    private HConnection companyOtherBankLiCaiEditHConn;
    private HConnection companyOtherBankLoanInfoEditHConn;
    private HConnection companyOtherBankSignProductEditHConn;
    //	private HConnection companyOwnBankMoneyDeleteHConn;
    private HConnection companyOtherBankMoneyDeleteHConn;
    private HConnection companyOtherBankLiCaiDeleteHConn;
    private HConnection companyOtherBankLoanInfoDeleteHConn;
    private HConnection companyOtherBankSignProductDeleteHConn;

    // 单条唯一ID
//    private String pkidOwnBankMoney;
    private String pkidOtherBankMoney;
    private String pkidOtherBankLiCai;
    private String pkidOtherBankLoanInfo;
    private String pkidOtherBankSignProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_businesscomego, null);
        setupView(view);
        fm = getFragmentManager();
//		listOwnBank = new ArrayList<OwnBankMoneyEntity>();
        listOtherBank = new ArrayList<OtherBankMoneyEntity>();
        listOtherBankLiCai = new ArrayList<OtherBankLiCaiEntity>();
        listOtherBankLoanInfo = new ArrayList<OtherBankLoanInfoPublic>();
        listOtherBankSignProduct = new ArrayList<OtherBankSignProductPublic>();
        setListener();

//		sendRequestForBenBankMoney();// 查询本行存款
        sendRequestForOtherBankMoney();// 查询他行存款
        sendRequestForOtherLiCaiInfo();// 查询他行理财信息
        sendRequestForOtherBankLoanInfo();// 查询行外贷款信息
        sendRequestForOtherBankSignProduct();// 查询签约产品信息
        return view;
    }

    /*************
     * ----------- 查询列表信息 ----------- | | | | | | | \|/ .
     ************
     */

    /**
     * 查询本行存款
     */
//	private void sendRequestForBenBankMoney() {
//		if (Tool.haveNet(getActivity())) {
//			String requestType = InterfaceInfo.BUSINESSCOMEGO_BHCK_Get;
//			String info = ActivityBaseInfo.custID;
//
//			companyBenBankMoneyHConn = RequestInfo(this,
//					Constants.requestType.Search, requestType, info,
//					companyBenBankMoneyFlag, true);
//		} else {
//			Toast.makeText(getActivity(), "当前无网络连接！", Toast.LENGTH_SHORT)
//					.show();
//		}
//	}

    /**
     * 查询他行存款信息
     */
    private void sendRequestForOtherBankMoney() {
        if (Tool.haveNet(getActivity())) {
            String requestType = InterfaceInfo.BUSINESSCOMEGO_THCK_Get;
            String info = ActivityBaseInfo.custID;

            companyOtherBankMoneyHConn = RequestInfo(this,
                    Constants.requestType.Search, requestType, info,
                    companyOtherBankMoneyFlag, true);
        } else {
            Toast.makeText(getActivity(), "当前无网络连接！", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * 查询他行理财信息
     */
    private void sendRequestForOtherLiCaiInfo() {
        if (Tool.haveNet(getActivity())) {
            String requestType = InterfaceInfo.BUSINESSCOMEGO_THLCCK_Get;
            String info = ActivityBaseInfo.custID;

            companyOtherBankLiCaiHConn = RequestInfo(this,
                    Constants.requestType.Search, requestType, info,
                    companyOtherBankLiCaiFlag, true);
        } else {
            Toast.makeText(getActivity(), "当前无网络连接！", Toast.LENGTH_SHORT)
                    .show();
        }
    }


    /**
     * 查询行外贷款信息
     */
    private void sendRequestForOtherBankLoanInfo() {
        if (Tool.haveNet(getActivity())) {
            String requestType = InterfaceInfo.BUSINESSCOMEGO_THDK_Get;
            String info = ActivityBaseInfo.custID;

            companyOtherBankLoanInfoHConn = RequestInfo(this,
                    Constants.requestType.Search, requestType, info,
                    companyOtherBankLoanInfoFlag, true);
        } else {
            Toast.makeText(getActivity(), "当前无网络连接！", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * 查询签约产品信息
     */
    private void sendRequestForOtherBankSignProduct() {
        if (Tool.haveNet(getActivity())) {
            String requestType = InterfaceInfo.BUSINESSCOMEGO_SIGN_Get;
            String info = ActivityBaseInfo.custID;

            companyOtherBankSignProductHConn = RequestInfo(this,
                    Constants.requestType.Search, requestType, info,
                    companyOtherBankSignProductFlag, true);
        } else {
            Toast.makeText(getActivity(), "当前无网络连接！", Toast.LENGTH_SHORT)
                    .show();
        }
    }
    /**
     * 查询行外
     */
//	private void sendRequestForOtherLiCaiInfo() {
//		if (Tool.haveNet(getActivity())) {
//			String requestType = InterfaceInfo.BUSINESSCOMEGO_THLCCK_Get;
//			String info = ActivityBaseInfo.custID;
//
//			companyOtherBankLiCaiHConn = RequestInfo(this,
//					Constants.requestType.Search, requestType, info,
//					companyOtherBankLiCaiFlag, true);
//		} else {
//			Toast.makeText(getActivity(), "当前无网络连接！", Toast.LENGTH_SHORT)
//					.show();
//		}
//	}

    /*************
     * ----------- 创建信息 ----------- | | | | | | | \|/ .
     ************
     */
    /**
     * 创建本行存款
     *
     * @param info1
     *            存款类型
     * @param info2
     *            存款金额
     * @param info3
     *            存款期限
     * @param info4
     * @param info5
     */
//	private void createOwnBankMoney(String info1, String info2, String info3,
//			String info4, String info5) {
//		if (Tool.haveNet(getActivity())) {
//			String custId = ActivityBaseInfo.custID;
//			String requestType = InterfaceInfo.BUSINESSCOMEGO_BHCK_Create;
//			JSONObject jsinfo = null;
//			try {
//				jsinfo = new JSONObject();
//				jsinfo.put("CUSTID", custId);
//				jsinfo.put("DEPOSITTYPE", info1);
//				jsinfo.put("DEPOSIT_AMOUNT", info2);
//				jsinfo.put("DEPOSIT_PERIOD", info3);
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			String info = jsinfo.toString();
//			companyOwnBankMoneyCreateHConn = RequestInfo(this,
//					Constants.requestType.Insert, requestType, info,
//					companyOwnBankCreateFlag, true);
//		} else {
//			Toast.makeText(getActivity(), "当前无网络连接！", Toast.LENGTH_SHORT)
//					.show();
//		}
//	}

    /**
     * 创建他行存款
     *
     * @param info1 存款类型
     * @param info2 存款金额
     * @param info3 存款期限
     * @param info4 存款行
     * @param info5 提醒日期
     */
    private void createOtherBankMoney(String info1, String info2, String info3,
                                      String info4, String info5, String info6) {
        if (Tool.haveNet(getActivity())) {
            String custId = ActivityBaseInfo.custID;
            String requestType = InterfaceInfo.BUSINESSCOMEGO_THCK_Create;
            JSONObject jsinfo = null;
            try {
                jsinfo = new JSONObject();
                jsinfo.put("CUSTID", custId);
                jsinfo.put("deposit_type", info1);
                jsinfo.put("deposit_period", info2);
                jsinfo.put("deposit_amount", info3);
                jsinfo.put("deposit_bank", info4);
                jsinfo.put("deposit_maturity_date", info5);
                jsinfo.put("remind_date", info6);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String info = jsinfo.toString();
            companyOtherBankMoneyCreateHConn = RequestInfo(this,
                    Constants.requestType.Insert, requestType, info,
                    companyOtherBankCreateFlag, true);
        } else {
            Toast.makeText(getActivity(), "当前无网络连接！", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * 创建行外贷款
     *
     * @param info1 贷款方式
     * @param info2 贷款期限
     * @param info3 贷款金额
     * @param info4 贷款行
     * @param info5 到期日
     * @param info6 提醒日期
     * @param info7 贷款利率
     */
    private void createOtherBankLoanInfo(String info1, String info2, String info3,
                                         String info4, String info5, String info6, String info7) {
        if (Tool.haveNet(getActivity())) {
            String custId = ActivityBaseInfo.custID;
            String requestType = InterfaceInfo.BUSINESSCOMEGO_THDK_Create;
            JSONObject jsinfo = null;
            try {
                jsinfo = new JSONObject();
                jsinfo.put("CUSTID", custId);
                jsinfo.put("LOAN_TYPE", info1);
                jsinfo.put("LOAN_PERIOD", info2);
                jsinfo.put("LOAN_RATE", info3);
                jsinfo.put("LOAN_AMOUNT", info4);
                jsinfo.put("LOAN_BANK", info5);
                jsinfo.put("END_DATE", info6);
                jsinfo.put("REMIND_DATE", info7);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String info = jsinfo.toString();
            companyOtherBankLoanInfoCreateHConn = RequestInfo(this,
                    Constants.requestType.Insert, requestType, info,
                    companyOtherBankLoanInfoCreateFlag, true);
        } else {
            Toast.makeText(getActivity(), "当前无网络连接！", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * 创建行外签约产品
     */
    private void createOtherBankSignProduct(String info1, String info2, String info3,
                                            String info4, String info5) {
        if (Tool.haveNet(getActivity())) {
            String custId = ActivityBaseInfo.custID;
            String requestType = InterfaceInfo.BUSINESSCOMEGO_SIGN_Create;
            JSONObject jsinfo = null;
            try {
                jsinfo = new JSONObject();
                jsinfo.put("CUSTID", custId);
                jsinfo.put("SIGN_ACC", info1);
                jsinfo.put("SIGN_PRD", info2);
                jsinfo.put("SIGN_ORG", info3);
                jsinfo.put("SIGN_PHONE", info4);
                jsinfo.put("SIGN_TIME", info5);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String info = jsinfo.toString();
            companyOtherBankSignProductCreateHConn = RequestInfo(this,
                    Constants.requestType.Insert, requestType, info,
                    companyOtherBankSignProductCreateFlag, true);
        } else {
            Toast.makeText(getActivity(), "当前无网络连接！", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * 创建他行理财信息
     *
     * @param info1 产品名称
     * @param info2 认购金额
     * @param info3 认购日期
     * @param info4 到期日期
     * @param info5 提醒日期
     * @param info6 预期收益率
     */
    private void createOtherBankLiCaiInfo(String info1, String info2,
                                          String info3, String info4, String info5, String info6) {
        if (Tool.haveNet(getActivity())) {
            String custId = ActivityBaseInfo.custID;
            String requestType = InterfaceInfo.BUSINESSCOMEGO_THLCCK_Create;
            JSONObject jsinfo = null;
            try {
                jsinfo = new JSONObject();
                jsinfo.put("CUSTID", custId);
                jsinfo.put("PRD_NAME", info1);
                jsinfo.put("EXP_AMOUNT", info2);
                jsinfo.put("EXP_DATE", info3);
                jsinfo.put("END_DATE", info4);
                jsinfo.put("REMIND_DATE", info5);
                jsinfo.put("EXP_PROFIT", info6);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String info = jsinfo.toString();
            companyOtherBankLiCaiCreateHConn = RequestInfo(this,
                    Constants.requestType.Insert, requestType, info,
                    companyOtherBankLiCaiCreateFlag, true);
        } else {
            Toast.makeText(getActivity(), "当前无网络连接！", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /*************
     * ----------- 编辑信息 ----------- | | | | | | | \|/ .
     ************
     */
    /**
     * 编辑本行存款
     *
     * @param info1
     *            存款类型
     * @param info2
     *            存款金额
     * @param info3
     *            存款期限
     * @param info4
     * @param info5
     */
//	private void editOwnBankMoney(String info1, String info2, String info3,
//			String info4, String info5) {
//		if (Tool.haveNet(getActivity())) {
//			String requestType = InterfaceInfo.BUSINESSCOMEGO_BHCK_Update;
//			JSONObject jsinfo = null;
//			try {
//				jsinfo = new JSONObject();
//				jsinfo.put("PKID", pkidOwnBankMoney);
//				jsinfo.put("DEPOSITTYPE", info1);
//				jsinfo.put("DEPOSIT_AMOUNT", info2);
//				jsinfo.put("DEPOSIT_PERIOD", info3);
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			String info = jsinfo.toString();
//			companyOwnBankMoneyEditHConn = RequestInfo(this,
//					Constants.requestType.Update, requestType, info,
//					companyOwnBankEditFlag, true);
//		} else {
//			Toast.makeText(getActivity(), "当前无网络连接！", Toast.LENGTH_SHORT)
//					.show();
//		}
//	}

    /**
     * 编辑行外贷款
     *
     * @param info1 存款类型
     * @param info2 存款金额
     * @param info3 存款期限
     * @param info4
     * @param info5
     */
    private void editOtherBankSignProduct(String info1, String info2, String info3,
                                          String info4, String info5) {
        if (Tool.haveNet(getActivity())) {
            String requestType = InterfaceInfo.BUSINESSCOMEGO_SIGN_Update;
            JSONObject jsinfo = null;
            try {
                jsinfo = new JSONObject();
                jsinfo.put("PKID", pkidOtherBankSignProduct);
                jsinfo.put("SIGN_ACC", info1);
                jsinfo.put("SIGN_PRD", info2);
                jsinfo.put("SIGN_ORG", info3);
                jsinfo.put("SIGN_PHONE", info4);
                jsinfo.put("SIGN_TIME", info5);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String info = jsinfo.toString();
            companyOtherBankSignProductEditHConn = RequestInfo(this,
                    Constants.requestType.Update, requestType, info,
                    companyOtherBankSignProductEditFlag, true);
        } else {
            Toast.makeText(getActivity(), "当前无网络连接！", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * 编辑行外贷款
     *
     * @param info1 存款类型
     * @param info2 存款金额
     * @param info3 存款期限
     * @param info4
     * @param info5
     */
    private void editOtherBankLoanInfo(String info1, String info2, String info3,
                                       String info4, String info5, String info6, String info7) {
        if (Tool.haveNet(getActivity())) {
            String requestType = InterfaceInfo.BUSINESSCOMEGO_THDK_Update;
            JSONObject jsinfo = null;
            try {
                jsinfo = new JSONObject();
                jsinfo.put("PKID", pkidOtherBankLoanInfo);
                jsinfo.put("LOAN_TYPE", info1);
                jsinfo.put("LOAN_PERIOD", info2);
                jsinfo.put("LOAN_RATE", info3);
                jsinfo.put("LOAN_AMOUNT", info4);
                jsinfo.put("LOAN_BANK", info5);
                jsinfo.put("END_DATE", info6);
                jsinfo.put("REMIND_DATE", info7);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String info = jsinfo.toString();
            companyOtherBankLoanInfoEditHConn = RequestInfo(this,
                    Constants.requestType.Update, requestType, info,
                    companyOtherBankLoanInfoEditFlag, true);
        } else {
            Toast.makeText(getActivity(), "当前无网络连接！", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * 编辑他行存款
     *
     * @param info1 存款类型
     * @param info2 存款金额
     * @param info3 存款期限
     * @param info4 存款行
     * @param info5 提醒日期
     */
    private void editOtherBankMoney(String info1, String info2, String info3,
                                    String info4, String info5, String info6) {
        if (Tool.haveNet(getActivity())) {
            String requestType = InterfaceInfo.BUSINESSCOMEGO_THCK_Update;
            JSONObject jsinfo = null;
            try {
                jsinfo = new JSONObject();
                jsinfo.put("pkid", pkidOtherBankMoney);
                jsinfo.put("deposit_type", info1);
                jsinfo.put("deposit_period", info2);
                jsinfo.put("deposit_amount", info3);
                jsinfo.put("deposit_bank", info4);
                jsinfo.put("deposit_maturity_date", info5);
                jsinfo.put("remind_date", info6);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String info = jsinfo.toString();
            companyOtherBankMoneyEditHConn = RequestInfo(this,
                    Constants.requestType.Update, requestType, info,
                    companyOtherBankMoneyEditFlag, true);
        } else {
            Toast.makeText(getActivity(), "当前无网络连接！", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * 编辑他行理财信息
     *
     * @param info1 产品名称
     * @param info2 认购金额
     * @param info3 认购日期
     * @param info4 到期日期
     * @param info5 提醒日期
     * @param info6 预期收益率
     */
    private void editOtherBankLiCaiInfo(String info1, String info2,
                                        String info3, String info4, String info5, String info6) {
        if (Tool.haveNet(getActivity())) {
            String requestType = InterfaceInfo.BUSINESSCOMEGO_THLCCK_Update;
            JSONObject jsinfo = null;
            try {
                jsinfo = new JSONObject();
                jsinfo.put("PKID", pkidOtherBankLiCai);
                jsinfo.put("PRD_NAME", info1);
                jsinfo.put("EXP_AMOUNT", info2);
                jsinfo.put("EXP_DATE", info3);
                jsinfo.put("END_DATE", info4);
                jsinfo.put("REMIND_DATE", info5);
                jsinfo.put("EXP_PROFIT", info6);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String info = jsinfo.toString();
            companyOtherBankLiCaiEditHConn = RequestInfo(this,
                    Constants.requestType.Update, requestType, info,
                    companyOtherBankLiCaiEditFlag, true);
        } else {
            Toast.makeText(getActivity(), "当前无网络连接！", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /*************
     * ----------- 编辑信息 ----------- | | | | | | | \|/ .
     ************
     */
    /**
     * 删除单条信息
     *
     * @param transcode
     * @param pkid
     * @param flag
     * @return
     */
    private HConnection sendRequestForDeleteInfo(String transcode, String pkid,
                                                 int flag) {
        HConnection tempHConn = null;
        if (Tool.haveNet(getActivity())) {
            String info = pkid;
            tempHConn = RequestInfo(this, Constants.requestType.Delete,
                    transcode, info, flag, true);
        } else {
            Toast.makeText(getActivity(), "当前无网络连接！", Toast.LENGTH_SHORT)
                    .show();
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
        readJson(tmpJsonObject, connectionIndex);
    }

    /******************************************************************/
    /******************************************************************/
    /******************************************************************/

    @Override
    public void onConnected(Message msg) {
        super.onConnected(msg);
        switch (connectionIndex) {
            // 查询本行存款信息
    /*	case companyBenBankMoneyFlag:
            HResponse companyBenBankMoneyRes = companyBenBankMoneyHConn
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (companyBenBankMoneyRes == null
					|| companyBenBankMoneyRes.dataJsonObject == null) {
				return;
			}
			JSONObject tmpJsonOwnBankObject = companyBenBankMoneyRes.dataJsonObject;
			readJson(tmpJsonOwnBankObject, connectionIndex);
			break;
		case companyOwnBankCreateFlag:
			HResponse companyOwnBankCreateRes = companyOwnBankMoneyCreateHConn
					.getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
			if (companyOwnBankCreateRes == null
					|| companyOwnBankCreateRes.dataJsonObject == null) {
				return;
			}
			JSONObject tmpJsonOwnBankCreateObject = companyOwnBankCreateRes.dataJsonObject;
			readJson(tmpJsonOwnBankCreateObject, connectionIndex);
			break;
		case companyOwnBankEditFlag:
			getServiceInfoTemp(companyOwnBankMoneyEditHConn);
			break;
		case companyOwnBankMoneyDeleteFlag:
			getServiceInfoTemp(companyOwnBankMoneyDeleteHConn);
			break;
*/
            //查询
            // 查询他行卡存款信息
            case companyOtherBankMoneyFlag:
                HResponse companyOtherBankCheckRes = companyOtherBankMoneyHConn
                        .getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (companyOtherBankCheckRes == null
                        || companyOtherBankCheckRes.dataJsonObject == null) {
                    return;
                }
                JSONObject tmpJsonOtherBankMoneyCheckObject = companyOtherBankCheckRes.dataJsonObject;
                readJson(tmpJsonOtherBankMoneyCheckObject, connectionIndex);
                break;
            // 查询他行理财信息
            case companyOtherBankLiCaiFlag:
                HResponse companyOtherBankLiCaiRes = companyOtherBankLiCaiHConn
                        .getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (companyOtherBankLiCaiRes == null
                        || companyOtherBankLiCaiRes.dataJsonObject == null) {
                    return;
                }
                JSONObject tmpJsonOtherBankMoneyLiCaiObject = companyOtherBankLiCaiRes.dataJsonObject;
                readJson(tmpJsonOtherBankMoneyLiCaiObject, connectionIndex);
                break;
            // 查询他行贷款
            case companyOtherBankLoanInfoFlag:
                HResponse companyOtherBankLoanInfo = companyOtherBankLoanInfoHConn
                        .getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (companyOtherBankLoanInfo == null
                        || companyOtherBankLoanInfo.dataJsonObject == null) {
                    return;
                }
                JSONObject tmpJsonOtherBankLoanInfoCheckObject = companyOtherBankLoanInfo.dataJsonObject;
                readJson(tmpJsonOtherBankLoanInfoCheckObject, connectionIndex);
                break;
            // 查询他行签约产品信息
            case companyOtherBankSignProductFlag:
                HResponse companyOtherBankSign = companyOtherBankSignProductHConn
                        .getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (companyOtherBankSign == null
                        || companyOtherBankSign.dataJsonObject == null) {
                    return;
                }
                JSONObject tmpJsonOtherBankSignCheckObject = companyOtherBankSign.dataJsonObject;
                readJson(tmpJsonOtherBankSignCheckObject, connectionIndex);
                break;


            //创建
            case companyOtherBankCreateFlag:
                HResponse companyOtherBankCreateRes = companyOtherBankMoneyCreateHConn
                        .getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (companyOtherBankCreateRes == null
                        || companyOtherBankCreateRes.dataJsonObject == null) {
                    return;
                }
                JSONObject tmpJsonOtherBankMoneyCreateObject = companyOtherBankCreateRes.dataJsonObject;
                readJson(tmpJsonOtherBankMoneyCreateObject, connectionIndex);
                break;
            case companyOtherBankLiCaiCreateFlag:
                HResponse companyOtherBankLiCaiCreateRes = companyOtherBankLiCaiCreateHConn
                        .getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (companyOtherBankLiCaiCreateRes == null
                        || companyOtherBankLiCaiCreateRes.dataJsonObject == null) {
                    return;
                }
                JSONObject tmpJsonOtherBankMoneyLiCaiCreateObject = companyOtherBankLiCaiCreateRes.dataJsonObject;
                readJson(tmpJsonOtherBankMoneyLiCaiCreateObject, connectionIndex);
                break;
            case companyOtherBankLoanInfoCreateFlag:
                HResponse companyOtherBankLoanInfoCreateRes = companyOtherBankLoanInfoCreateHConn
                        .getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (companyOtherBankLoanInfoCreateRes == null
                        || companyOtherBankLoanInfoCreateRes.dataJsonObject == null) {
                    return;
                }
                JSONObject tmpJsonOtherBankMoneyLoanInfoCreateObject = companyOtherBankLoanInfoCreateRes.dataJsonObject;
                readJson(tmpJsonOtherBankMoneyLoanInfoCreateObject, connectionIndex);
                break;
            case companyOtherBankSignProductCreateFlag:
                HResponse companyOtherBankSignProductCreateRes = companyOtherBankSignProductCreateHConn
                        .getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (companyOtherBankSignProductCreateRes == null
                        || companyOtherBankSignProductCreateRes.dataJsonObject == null) {
                    return;
                }
                JSONObject tmpJsonOtherBankSignProductCreateRes = companyOtherBankSignProductCreateRes.dataJsonObject;
                readJson(tmpJsonOtherBankSignProductCreateRes, connectionIndex);
                break;


            //编辑
            case companyOtherBankMoneyEditFlag:
                getServiceInfoTemp(companyOtherBankMoneyEditHConn);
                break;
            case companyOtherBankLiCaiEditFlag:
                getServiceInfoTemp(companyOtherBankLiCaiEditHConn);
                break;
            case companyOtherBankLoanInfoEditFlag:
                getServiceInfoTemp(companyOtherBankLoanInfoEditHConn);
                break;
            case companyOtherBankSignProductEditFlag:
                getServiceInfoTemp(companyOtherBankSignProductEditHConn);
                break;

            //删除
            case companyOtherBankMoneyDeleteFlag:
                getServiceInfoTemp(companyOtherBankMoneyDeleteHConn);
                break;
            case companyOtherBankLiCaiDeleteFlag:
                getServiceInfoTemp(companyOtherBankLiCaiDeleteHConn);
                break;
            case companyOtherBankLoanInfoDeleteFlag:
                getServiceInfoTemp(companyOtherBankLoanInfoDeleteHConn);
                break;
            case companyOtherBankSignProductDeleteFlag:
                getServiceInfoTemp(companyOtherBankSignProductDeleteHConn);
                break;
            default:
                break;
        }
    }

    /**
     * 解析json
     *
     * @param tmpJsonObject
     * @param connectionIndex
     */
    private void readJson(JSONObject tmpJsonObject, int connectionIndex) {
        String retCode = null;
        try {
            if (tmpJsonObject.has("retCode")) {
                retCode = tmpJsonObject.getString("retCode");
            }

            if ("0000".equals(retCode)) {
                switch (connectionIndex) {
                    case companyOtherBankLiCaiCreateFlag:// 创建他行理财信息
                    case companyOtherBankLiCaiDeleteFlag:// 删除他行理财信息
                    case companyOtherBankLiCaiEditFlag:// 删除他行理财信息
                        sendRequestForOtherLiCaiInfo();
                        break;
                    case companyOtherBankCreateFlag:// 创建他行存款信息
                    case companyOtherBankMoneyDeleteFlag:// 删除他行存款信息
                    case companyOtherBankMoneyEditFlag:// 编辑他行存款信息
                        sendRequestForOtherBankMoney();
                        break;
                    case companyOtherBankLoanInfoCreateFlag:// 创建行外贷款信息
                    case companyOtherBankLoanInfoDeleteFlag:// 删除行外贷款信息
                    case companyOtherBankLoanInfoEditFlag:// 编辑行外贷款信息
                        sendRequestForOtherBankLoanInfo();
                        break;
                    case companyOtherBankSignProductCreateFlag:// 创建签约产品信息
                    case companyOtherBankSignProductDeleteFlag:// 删除签约产品信息
                    case companyOtherBankSignProductEditFlag:// 编辑签约产品信息
                        sendRequestForOtherBankSignProduct();
                        break;
//				case companyOwnBankCreateFlag:// 创建本行存款信息
//				case companyOwnBankMoneyDeleteFlag:// 删除本行存款信息
//				case companyOwnBankEditFlag:// 编辑本行存款信息
//					sendRequestForBenBankMoney();
//					break;

//				case companyBenBankMoneyFlag:// 查询本行存款信息
//					listOwnBank.clear();
//					if (tmpJsonObject.has("group")) {
//						String teamInfo = tmpJsonObject.getString("group");
//						if (TextUtils.isEmpty(teamInfo)) {
//							return;
//						} else {
//							listOwnBank = JSON.parseArray(teamInfo,
//									OwnBankMoneyEntity.class);
//							showOwnBankMoneyInfo();
//						}
//					}
//					break;


                    case companyOtherBankMoneyFlag:// 查询他行存款信息
                        listOtherBank.clear();
                        if (tmpJsonObject.has("group")) {
                            String teamInfo = tmpJsonObject.getString("group");
                            if (TextUtils.isEmpty(teamInfo)) {
                                return;
                            } else {
                                listOtherBank = JSON.parseArray(teamInfo, OtherBankMoneyEntity.class);
                                showOtherBankMoneyInfo();
                            }
                        }
                        break;
                    case companyOtherBankLiCaiFlag:// 查询他行理财信息
                        listOtherBankLiCai.clear();
                        if (tmpJsonObject.has("group")) {
                            String teamInfo = tmpJsonObject.getString("group");
                            if (TextUtils.isEmpty(teamInfo)) {
                                return;
                            } else {
                                listOtherBankLiCai = JSON.parseArray(teamInfo, OtherBankLiCaiEntity.class);
                                showOtherBankLiCaiInfo();
                            }
                        }
                        break;
                    case companyOtherBankLoanInfoFlag:// 查询行外贷款信息
                        listOtherBankLoanInfo.clear();
                        if (tmpJsonObject.has("group")) {
                            String teamInfo = tmpJsonObject.getString("group");
                            if (TextUtils.isEmpty(teamInfo)) {
                                return;
                            } else {
                                listOtherBankLoanInfo = JSON.parseArray(teamInfo, OtherBankLoanInfoPublic.class);
                                showOtherBankLoanInfo();
                            }
                        }
                        break;
                    case companyOtherBankSignProductFlag:// 查询签约产品信息
                        listOtherBankSignProduct.clear();
                        if (tmpJsonObject.has("group")) {
                            String teamInfo = tmpJsonObject.getString("group");
                            if (TextUtils.isEmpty(teamInfo)) {
                                return;
                            } else {
                                listOtherBankSignProduct = JSON.parseArray(teamInfo, OtherBankSignProductPublic.class);
                                showOtherBankSignProduct();
                            }
                        }
                        break;
                    default:
                        break;
                }
            } else {
                Toast.makeText(getActivity(), "错误码：" + retCode,
                        Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示他行理财集合
     */
    private void showOtherBankLiCaiInfo() {
        if (listOtherBankLiCai.size() == 0) {
            lvOtherBankLiCai.setVisibility(View.INVISIBLE);
            return;
        } else {
            lvOtherBankLiCai.setVisibility(View.VISIBLE);
        }

        final int mRightWidth = lvOtherBankLiCai.getRightViewWidth();
        final CommonAdapter1<OtherBankLiCaiEntity> adapterOtherLiCai = new CommonAdapter1<OtherBankLiCaiEntity>(
                getActivity(), listOtherBankLiCai,
                R.layout.lv_eightfieldbusinesscomgo) {

            @Override
            public void convert(CommonViewHolder helper,
                                OtherBankLiCaiEntity item, final int position) {
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
                        delCompanyOtherBankLiCai(position);
                    }
                });
                info1.setText(item.getPRD_NAME());
                info2.setText(item.getEXP_AMOUNT());
                info3.setText(item.getEXP_DATE());
                info4.setText(item.getEND_DATE());
                info5.setText(item.getREMIND_DATE());
                info6.setText(item.getEXP_PROFIT());
                info7.setText(item.getCREATE_USER());
                info8.setText(item.getCREATE_TIME());
            }
        };

        lvOtherBankLiCai.setPullstatus(false, false);// 设置是否可以上下拉动
        lvOtherBankLiCai.setAdapter(adapterOtherLiCai);// 显示数据
        setOtherBankLiCaiListViewHeight(lvOtherBankLiCai, listOtherBankLiCai);
        lvOtherBankLiCai.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String pkid = listOtherBankLiCai.get(0).getPkid();
                if ("".equals(pkid) || TextUtils.isEmpty(pkid)) {
                    return;
                }
                OtherBankLiCaiEntity entity = listOtherBankLiCai.get(position);
                pkidOtherBankLiCai = entity.getPkid();
                DialogFragmentCreateLiCai fragmentLiCai = DialogFragmentCreateLiCai
                        .getNewInstance(true, entity);
                fragmentLiCai.show(fm, "fmlicai");
                setCallBackOtherLiCai(fragmentLiCai);
            }
        });
    }

    /**
     * 显示他行贷款信息集合
     */
    private void showOtherBankLoanInfo() {
        if (listOtherBankLoanInfo.size() == 0) {
            lvOtherBankLoan.setVisibility(View.INVISIBLE);
            return;
        } else {
            lvOtherBankLoan.setVisibility(View.VISIBLE);
        }

        final int mRightWidth = lvOtherBankLoan.getRightViewWidth();
        final CommonAdapter1<OtherBankLoanInfoPublic> adapterOtherLoanInfo = new CommonAdapter1<OtherBankLoanInfoPublic>(
                getActivity(), listOtherBankLoanInfo,
                R.layout.lv_sevenfieldbusinesscomgo) {

            @Override
            public void convert(CommonViewHolder helper,
                                OtherBankLoanInfoPublic item, final int position) {
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

                LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(mRightWidth, LayoutParams.MATCH_PARENT);
                llLeft.setLayoutParams(lp1);
                rlRight.setLayoutParams(lp2);
                rlRight.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delCompanyOtherBankLoanInfo(position);
                    }
                });

                String strLoanType = NewCatevalue.getLabel(getActivity(), NewCatevalue.loanType, item.LOAN_TYPE);
                String strLoanBank = NewCatevalue.getLabel(getActivity(), NewCatevalue.depositBank, item.LOAN_BANK);

                info1.setText(strLoanType);
                info2.setText(item.LOAN_PERIOD);
                info3.setText(item.LOAN_RATE);
                info4.setText(item.LOAN_AMOUNT);
                info5.setText(strLoanBank);
                info6.setText(item.END_DATE);
                info7.setText(item.REMIND_DATE);
            }
        };

        lvOtherBankLoan.setPullstatus(false, false);// 设置是否可以上下拉动
        lvOtherBankLoan.setAdapter(adapterOtherLoanInfo);// 显示数据
        setOtherBankLoanInfoListViewHeight(lvOtherBankLoan, listOtherBankLoanInfo);
        lvOtherBankLoan.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String pkid = listOtherBankLoanInfo.get(0).pkid;
                if ("".equals(pkid) || TextUtils.isEmpty(pkid)) {
                    return;
                }
                OtherBankLoanInfoPublic entity = listOtherBankLoanInfo.get(position);
                pkidOtherBankLoanInfo = entity.pkid;
                DialogFragmentCreateOtherBankLoanInfo fragmentLoanInfo = DialogFragmentCreateOtherBankLoanInfo
                        .getNewInstance(true, entity);
                fragmentLoanInfo.show(fm, "fmloan");
                setCallBackLoanInfo(fragmentLoanInfo);
            }
        });
    }

    /**
     * 显示他行签约产品集合
     */
    private void showOtherBankSignProduct() {
        if (listOtherBankSignProduct.size() == 0) {
            lvOtherBankSignProduct.setVisibility(View.INVISIBLE);
            return;
        } else {
            lvOtherBankSignProduct.setVisibility(View.VISIBLE);
        }

        final int mRightWidth = lvOtherBankSignProduct.getRightViewWidth();
        final CommonAdapter1<OtherBankSignProductPublic> adapterOtherSignProduct = new CommonAdapter1<OtherBankSignProductPublic>(
                getActivity(), listOtherBankSignProduct,
                R.layout.lv_fivefieldbusinesscomgo) {

            @Override
            public void convert(CommonViewHolder helper,
                                OtherBankSignProductPublic item, final int position) {
                TextView info1 = helper.getView(R.id.tv_info1);
                TextView info2 = helper.getView(R.id.tv_info2);
                TextView info3 = helper.getView(R.id.tv_info3);
                TextView info4 = helper.getView(R.id.tv_info4);
                TextView info5 = helper.getView(R.id.tv_info5);

                LinearLayout llLeft = helper.getView(R.id.item_left);
                RelativeLayout rlRight = helper.getView(R.id.item_right);
                TextView tvRight = helper.getView(R.id.item_right_txt);

                LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(mRightWidth, LayoutParams.MATCH_PARENT);
                llLeft.setLayoutParams(lp1);
                rlRight.setLayoutParams(lp2);
                rlRight.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delCompanyOtherBankSignProduct(position);
                    }
                });


                String strLoanType = NewCatevalue.getLabel(getActivity(), NewCatevalue.signType, item.SIGN_PRD);
                String strSignBank = NewCatevalue.getLabel(getActivity(), NewCatevalue.signBank, item.SIGN_ORG);

                info1.setText(item.SIGN_ACC);
                info2.setText(strLoanType);
                info3.setText(strSignBank);
                info4.setText(item.SIGN_PHONE);
                info5.setText(item.SIGN_TIME);
            }
        };

        lvOtherBankSignProduct.setPullstatus(false, false);// 设置是否可以上下拉动
        lvOtherBankSignProduct.setAdapter(adapterOtherSignProduct);// 显示数据
        setOtherBankLoanInfoListViewHeight(lvOtherBankSignProduct, listOtherBankSignProduct);
        lvOtherBankSignProduct.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String pkid = listOtherBankSignProduct.get(0).pkid;
                if ("".equals(pkid) || TextUtils.isEmpty(pkid)) {
                    return;
                }
                OtherBankSignProductPublic entity = listOtherBankSignProduct.get(position);
                pkidOtherBankSignProduct = entity.pkid;
                DialogFragmentCreateOtherBankSignProduct fragmentSignProduct = DialogFragmentCreateOtherBankSignProduct.getNewInstance(true, entity);
                fragmentSignProduct.show(fm, "fsign");
                setCallBackSignProduct(fragmentSignProduct);
            }
        });
    }

    /**
     * 删除他行存款单条信息
     *
     * @param position
     */
    private void delCompanyOtherBankMoney(final int position) {
        deleteDialog = new DeleteDialog(getActivity(), new IsDeleteListener() {
            @Override
            public void onClickDeleteListener(boolean isDelete) {
                if (isDelete) {
                    String pkid = listOtherBank.get(position).getPkid();
                    companyOtherBankMoneyDeleteHConn = sendRequestForDeleteInfo(
                            InterfaceInfo.BUSINESSCOMEGO_THCK_Delete,
                            pkid, companyOtherBankMoneyDeleteFlag);
                    if (companyOtherBankMoneyDeleteHConn == null) {
                        return;
                    }
                }
            }
        });
        deleteDialog.show();
    }

    /**
     * 删除他行理财信息
     *
     * @param position
     */
    private void delCompanyOtherBankLiCai(final int position) {
        deleteDialog = new DeleteDialog(getActivity(), new IsDeleteListener() {
            @Override
            public void onClickDeleteListener(boolean isDelete) {
                if (isDelete) {
                    String pkid = listOtherBankLiCai.get(position).getPkid();
                    companyOtherBankLiCaiDeleteHConn = sendRequestForDeleteInfo(
                            InterfaceInfo.BUSINESSCOMEGO_THLCCK_Delete,
                            pkid, companyOtherBankLiCaiDeleteFlag);

                    if (companyOtherBankLiCaiDeleteHConn == null) {
                        return;
                    }
                }
            }
        });
        deleteDialog.show();
    }


    /**
     * 删除行外贷款信息
     *
     * @param position
     */
    private void delCompanyOtherBankLoanInfo(final int position) {
        deleteDialog = new DeleteDialog(getActivity(), new IsDeleteListener() {
            @Override
            public void onClickDeleteListener(boolean isDelete) {
                if (isDelete) {
                    String pkid = listOtherBankLoanInfo.get(position).pkid;
                    companyOtherBankLoanInfoDeleteHConn = sendRequestForDeleteInfo(
                            InterfaceInfo.BUSINESSCOMEGO_THDK_Delete,
                            pkid, companyOtherBankLoanInfoDeleteFlag);
                    if (companyOtherBankLoanInfoDeleteHConn == null) {
                        return;
                    }
                }
            }
        });
        deleteDialog.show();
    }

    /**
     * 删除行外签约产品信息
     *
     * @param position
     */
    private void delCompanyOtherBankSignProduct(final int position) {
        deleteDialog = new DeleteDialog(getActivity(), new IsDeleteListener() {
            @Override
            public void onClickDeleteListener(boolean isDelete) {
                if (isDelete) {
                    String pkid = listOtherBankSignProduct.get(position).pkid;
                    companyOtherBankSignProductDeleteHConn = sendRequestForDeleteInfo(
                            InterfaceInfo.BUSINESSCOMEGO_SIGN_Delete,
                            pkid, companyOtherBankSignProductDeleteFlag);
                    if (companyOtherBankSignProductDeleteHConn == null) {
                        return;
                    }
                }
            }
        });
        deleteDialog.show();
    }


    /**
     * 显示他行存款集合
     */
    private void showOtherBankMoneyInfo() {
        if (listOtherBank.size() == 0) {
            lvOtherBankDeposit.setVisibility(View.INVISIBLE);
        } else {
            lvOtherBankDeposit.setVisibility(View.VISIBLE);
        }

        final int mRightWidth = lvOtherBankDeposit.getRightViewWidth();
        final CommonAdapter1<OtherBankMoneyEntity> adapterOther = new CommonAdapter1<OtherBankMoneyEntity>(
                getActivity(), listOtherBank,
                R.layout.lv_eightfieldbusinesscomgo) {

            @Override
            public void convert(CommonViewHolder helper,
                                OtherBankMoneyEntity item, final int position) {
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
                        delCompanyOtherBankMoney(position);
                    }
                });

                String deposit_type = item.getDeposit_type();// 类型
                String deposit_period = item.getDeposit_period();// 期限
                String deposit_bank = item.getDeposit_bank();//存款行

                String moneyStyle = NewCatevalue.getLabel(getActivity(),
                        NewCatevalue.depositType, deposit_type);

                String moneyPeriod = NewCatevalue.getLabel(getActivity(),
                        NewCatevalue.depositPeriod, deposit_period);

                String bankName = "";
                if (null != deposit_bank) {
                    // 存款行
                    bankName = NewCatevalue.getLabel(getActivity(),
                            NewCatevalue.signBank, deposit_bank);
                }

                info1.setText(moneyStyle);
                info2.setText(item.getDeposit_amount());
                info3.setText(moneyPeriod);
                info4.setText(bankName);
                info5.setText(item.getDeposit_maturity_date());
                info6.setText(item.getRemind_date());
                info7.setText(item.getCreatename());
                info8.setText(item.getCreatetime());
            }
        };
        lvOtherBankDeposit.setPullstatus(false, false);// 设置是否可以上下拉动
        lvOtherBankDeposit.setAdapter(adapterOther);// 显示数据
        setOtherBankMoneyListViewHeight(lvOtherBankDeposit, listOtherBank);
        lvOtherBankDeposit.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String pkid = listOtherBank.get(0).getPkid();
                if ("".equals(pkid) || TextUtils.isEmpty(pkid)) {
                    return;
                }
                OtherBankMoneyEntity entity = listOtherBank.get(position);
                pkidOtherBankMoney = entity.getPkid();
                DialogFragmentCreateOtherBankMoney fragmentother = DialogFragmentCreateOtherBankMoney.getNewInstance(true, entity);
                fragmentother.show(fm, "fmother");
                setCallBackOther(fragmentother);
            }
        });
    }


    /**
     * 显示本行存款集合
     */
/*	private void showOwnBankMoneyInfo() {
        // 如果集合大小为0，则隐藏listView
		if (listOwnBank.size() == 0) {
			lvOwnBankMoney.setVisibility(View.INVISIBLE);
		} else {
			lvOwnBankMoney.setVisibility(View.VISIBLE);
		}

		final int mRightWidth = lvOwnBankMoney.getRightViewWidth();
		final CommonAdapter1<OwnBankMoneyEntity> adapterTest = new CommonAdapter1<OwnBankMoneyEntity>(
				getActivity(), listOwnBank, R.layout.lv_fivefieldbusinesscomgo) {
			@Override
			public void convert(CommonViewHolder helper,
					OwnBankMoneyEntity item, final int position) {
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
						delCompanyOwnBankMoney(position);
					}
				});
				String moneyStyle = item.getDEPOSITTYPE();
				String moneyStyleVal = NewCatevalue.getLabel(getActivity(),
						NewCatevalue.depositType, moneyStyle);

				info1.setText(moneyStyleVal);
				info2.setText(item.getDEPOSIT_AMOUNT());
				String period = item.getDEPOSIT_PERIOD();
				String periodVal = NewCatevalue.getLabel(getActivity(),
						NewCatevalue.depositPeriod, period);
				info3.setText(periodVal);
				info4.setText(item.getCREATE_USER());
				info5.setText(item.getCREATE_DATE());
			}
		};
		lvOwnBankMoney.setPullstatus(false, false);// 设置是否可以上下拉动
		lvOwnBankMoney.setAdapter(adapterTest);// 显示数据
		setOwnBankMoneyListViewHeight(lvOwnBankMoney, listOwnBank);
		lvOwnBankMoney.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String pkid = listOwnBank.get(0).getPKID();
                if ("".equals(pkid) || TextUtils.isEmpty(pkid)) {
                    return;
                }
                OwnBankMoneyEntity entity = listOwnBank.get(position);
                pkidOwnBankMoney = entity.getPKID();
                DialogFragmentCreateOwnBankMoney fragmentown = DialogFragmentCreateOwnBankMoney
                        .getNewInstance(true, entity);
                fragmentown.show(fm, "fmown");
                setCallBackOwn(fragmentown);
            }
        });
	}*/

    /**
     * 删除本行存款单条信息
     *
     * @param
     */
/*	private void delCompanyOwnBankMoney(final int position) {
        deleteDialog = new DeleteDialog(getActivity(), new IsDeleteListener() {
			@Override
			public void onClickDeleteListener(boolean isDelete) {
				if (isDelete) {
					String pkid = listOwnBank.get(position).getPKID();
					companyOwnBankMoneyDeleteHConn = sendRequestForDeleteInfo(
							"T011141", pkid, companyOwnBankMoneyDeleteFlag);

					if (companyOwnBankMoneyDeleteHConn == null) {
						return;
					}
				}
			}
		});
		deleteDialog.show();
	}*/
    private void setOtherBankMoneyListViewHeight(ListView listView, List list) {
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
            OtherBankMoneyEntity entity = new OtherBankMoneyEntity("", "", "",
                    "", "", "", " ", "", "");
            listOtherBank.add(entity);
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void setOtherBankLiCaiListViewHeight(ListView listView, List list) {
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
            OtherBankLiCaiEntity entity = new OtherBankLiCaiEntity("", "", "",
                    "", "", "", "", "", "");
            listOtherBankLiCai.add(entity);
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void setOtherBankLoanInfoListViewHeight(ListView listView, List list) {
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
            OtherBankLiCaiEntity entity = new OtherBankLiCaiEntity("", "", "",
                    "", "", "", "", "", "");
            listOtherBankLiCai.add(entity);
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

/*	private void setOwnBankMoneyListViewHeight(ListView listView, List list) {
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
			OwnBankMoneyEntity entity = new OwnBankMoneyEntity("", "", "", "",
					"", "");
			listOwnBank.add(entity);
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (mAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}*/

    private void setListener() {
        btnNext.setOnClickListener(this);
        btnOtherBankSignProduct.setOnClickListener(this);
        btnCreateOtherBankLoan.setOnClickListener(this);
        btnCreateOtherBankDeposit.setOnClickListener(this);
        btnLiCai.setOnClickListener(this);
    }

    private void setupView(View view) {
        btnCreateOtherBankDeposit = (Button) view.findViewById(R.id.btn_create_other_bank_deposit);
        btnLiCai = (Button) view.findViewById(R.id.btn_otherbanklicai);
        btnCreateOtherBankLoan = (Button) view.findViewById(R.id.btn_create_other_bank_loan);
        btnOtherBankSignProduct = (Button) view.findViewById(R.id.btn_other_bank_sign_product);

        btnNext = (Button) view.findViewById(R.id.btn_next);
        // spinOpenAccountBank = (Spinner) view
        // .findViewById(R.id.spin_openaccountbank);

        lvOtherBankDeposit = (SwipeListView) view.findViewById(R.id.lv_create_other_bank_deposit);
        lvOtherBankLiCai = (SwipeListView) view.findViewById(R.id.lv_otherbanklicai);
        lvOtherBankLoan = (SwipeListView) view.findViewById(R.id.lv_create_other_bank_loan);
        lvOtherBankSignProduct = (SwipeListView) view.findViewById(R.id.lv_otherbanksign);

    }

    /**
     * 动态设置ListView高度
     *
     * @param lv
     * @param num
     */
    public void setLvHeight(ListView lv, int num) {
        LayoutParams params = lv.getLayoutParams();
        params.height = Tool.dip2px(getActivity(), 45) * num
                + Tool.dip2px(getActivity(), 6);
        lv.setLayoutParams(params);
    }

    /*************
     * ----------- 创建对话框 ----------- | | | | | | | \|/ .
     ************
     */

    /**
     * 创建本行存款对话框
     *
     * @param fragmentown
     */
/*
    private void setCallBackOwn(DialogFragmentCreateOwnBankMoney fragmentown) {
		fragmentown.setCallBack(new BookbuildingownbankmoneyCallback() {
			@Override
			public void bookbuildingOwnBankMoney(Boolean isEdit, String info1,
					String info2, String info3, String info4, String info5) {
				if (!isEdit) {
					createOwnBankMoney(info1, info2, info3, info4, info5);
				} else {
					// 编辑
					editOwnBankMoney(info1, info2, info3, info4, info5);
				}
			}
		});
	}
*/

    /**
     * 创建行外签约产品对话框
     *
     * @param fragmentown
     */
    private void setCallBackSignProduct(DialogFragmentCreateOtherBankSignProduct fragmentown) {
        fragmentown.setCallBack(new BookbuildingotherbankSignProductCallback() {
            @Override
            public void bookbuildingOtherBankSignProductInfo(Boolean isEdit, String info1,
                                                             String info2, String info3, String info4, String info5) {
                if (!isEdit) {
                    createOtherBankSignProduct(info1, info2, info3, info4, info5);
                } else {
                    // 编辑
                    editOtherBankSignProduct(info1, info2, info3, info4, info5);
                }
            }
        });
    }

    /**
     * 创建行外贷款对话框
     *
     * @param fragmentown
     */
    private void setCallBackLoanInfo(DialogFragmentCreateOtherBankLoanInfo fragmentown) {
        fragmentown.setCallBack(new BookbuildingotherbankLoanInfoCallback() {
            @Override
            public void bookbuildingOtherBanLoaninfo(Boolean isEdit, String info1,
                                                     String info2, String info3, String info4,
                                                     String info5, String info6, String info7) {
                if (!isEdit) {
                    createOtherBankLoanInfo(info1, info2, info3, info4, info5, info6, info7);
                } else {
                    // 编辑
                    editOtherBankLoanInfo(info1, info2, info3, info4, info5, info6, info7);
                }
            }
        });
    }

    /**
     * 创建他行存款对话框
     *
     * @param fragmentother
     */
    private void setCallBackOther(
            DialogFragmentCreateOtherBankMoney fragmentother) {
        fragmentother.setCallBack(new BookbuildingotherbankmoneyCallback() {
            @Override
            public void bookbuildingOtherBankMoney(Boolean isEdit,
                                                   String info1, String info2, String info3, String info4,
                                                   String info5, String info6) {
                if (!isEdit) {
                    // 创建
                    createOtherBankMoney(info1, info2, info3, info4, info5, info6);
                } else {
                    // 编辑
                    editOtherBankMoney(info1, info2, info3, info4, info5, info6);
                }
            }
        });
    }

    /**
     * 创建他行理财信息对话框
     *
     * @param fragmentLiCai
     */
    private void setCallBackOtherLiCai(DialogFragmentCreateLiCai fragmentLiCai) {
        fragmentLiCai.setCallBack(new BookbuildingotherbanklicaiCallback() {
            @Override
            public void bookbuildingOtherBankLiCai(Boolean isEdit,
                                                   String info1, String info2, String info3, String info4,
                                                   String info5, String info6, String info7, String info8) {
                if (!isEdit) {
                    // 创建
                    createOtherBankLiCaiInfo(info1, info2, info3, info4, info5,
                            info6);
                } else {
                    // 编辑
                    editOtherBankLiCaiInfo(info1, info2, info3, info4, info5,
                            info6);
                }
            }
        });
    }

    /*********************************************************************************/
    /*********************************************************************************/
    /*********************************************************************************/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 创建行外产品签约
//		case R.id.btn_otherBankProductContract:
//			DialogFragmentCreateOwnBankMoney fragmenOtherBankContract = DialogFragmentCreateOwnBankMoney.getNewInstance();
//            fragmenOtherBankContract.show(fm, "fmown");
//			setCallBackOwn(fragmenOtherBankContract);
//			break;
            // 创建本行存款
//		case R.id.btn_createownbankmoney:
//			DialogFragmentCreateOwnBankMoney fragmentown = DialogFragmentCreateOwnBankMoney
//					.getNewInstance();
//			fragmentown.show(fm, "fmown");
//			setCallBackOwn(fragmentown);
//			break;
            // 创建行外签约产品信息
            case R.id.btn_other_bank_sign_product:
                DialogFragmentCreateOtherBankSignProduct fragmentOtherSignProduct = DialogFragmentCreateOtherBankSignProduct
                        .getNewInstance();
                fragmentOtherSignProduct.show(fm, "fsign");
                setCallBackSignProduct(fragmentOtherSignProduct);
                break;

            // 创建行外贷款信息
            case R.id.btn_create_other_bank_loan:
                DialogFragmentCreateOtherBankLoanInfo fragmentLoanInfo = DialogFragmentCreateOtherBankLoanInfo
                        .getNewInstance();
                fragmentLoanInfo.show(fm, "floaninfo");
                setCallBackLoanInfo(fragmentLoanInfo);
                break;
//		// 创建他行存款
//		case R.id.btn_otherBankLoanInformation:
//			DialogFragmentCreateOtherBankMoney fragmentotherBankLoanInfo = DialogFragmentCreateOtherBankMoney
//					.getNewInstance();
//            fragmentotherBankLoanInfo.show(fm, "fmother");
//			setCallBackOther(fragmentotherBankLoanInfo);
//			break;
            // 创建行外存款信息
            case R.id.btn_create_other_bank_deposit:
                DialogFragmentCreateOtherBankMoney fragmentother = DialogFragmentCreateOtherBankMoney
                        .getNewInstance();
                fragmentother.show(fm, "fmother");
                setCallBackOther(fragmentother);
                break;
            // 创建他行理财
            case R.id.btn_otherbanklicai:
                DialogFragmentCreateLiCai fragmentLiCai = DialogFragmentCreateLiCai
                        .getNewInstance();
                fragmentLiCai.show(fm, "fmlicai");
                setCallBackOtherLiCai(fragmentLiCai);
                break;
            case R.id.btn_next:
                if (getActivity() instanceof WorkPlaceItemChange) {
                    ((WorkPlaceItemChange) getActivity()).workPlacestyleChange(4);
                }
                break;

            default:
                break;
        }
    }

}
