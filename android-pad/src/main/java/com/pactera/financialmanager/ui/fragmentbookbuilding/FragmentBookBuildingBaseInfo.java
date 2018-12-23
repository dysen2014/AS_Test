package com.pactera.financialmanager.ui.fragmentbookbuilding;

import android.os.Bundle;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.pactera.financialmanager.R;
import com.pactera.financialmanager.adapter.SpinnerAdapter;
import com.pactera.financialmanager.adapter.SpinnerAdapter.CallBackItemClickListener;
import com.pactera.financialmanager.callback.WorkPlaceItemChange;
import com.pactera.financialmanager.datetimepicker.DatePickerDialog;
import com.pactera.financialmanager.ui.ParentFragment;
import com.pactera.financialmanager.ui.bookbuildingfrocompany.ActivityBaseInfo;
import com.pactera.financialmanager.ui.model.BaseInfoEntity;
import com.pactera.financialmanager.ui.service.HConnection;
import com.pactera.financialmanager.ui.service.HResponse;
import com.pactera.financialmanager.util.Constants.requestType;
import com.pactera.financialmanager.util.InterfaceInfo;
import com.pactera.financialmanager.util.NewCatevalue;
import com.pactera.financialmanager.util.Tool;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import ccb.ygqd.centerm.util.StringUtil;

/**
 * 客户建档中的基础信息的 fragment
 */
public class FragmentBookBuildingBaseInfo extends ParentFragment implements
        OnClickListener {
//    private EditText spinModuel;// 企业规模
    private EditText spinPro;// 企业性质
    private EditText businessSrange, businessState;
//    private EditText etCompanyOwner;// 企业法人
    private EditText etRealOwner;// 实际控制人
    private Button btnNext, btnSave;
    private EditText etInfo1;// 注册资本
    private EditText etInfo4;// 中征码
    private EditText etInfo6;// 地税税务登记代码
    private EditText etInfo5;// 国税税务登记代码
    private EditText etAcountOpenBank;// 基本账户开户行
    private EditText etInfo8;// 机构信用代码证
    private EditText etInfo9;// 营业执照
    private EditText spinStyle;// 客户类型
    private EditText etAnnualIncome;// 企业年销售量

    private String customerStyleId = "";// 企业类型码值
    //    private String rangeId = "";// 经营范围码值
    //    private String proId = "";// 经营产品码值
    private String stateId = "";// 经营状况码值
    private String companyMoudleId = "";// 企业规模码值
    private String propertyId = "";// 企业性质码值
    private String tvHangyeDaleiId = "";// 行业大类码值
    private String tv_hangyexileiId = "";// 行业系类码值
    private String OpenBankId ="";//基本账户开户行
    private EditText etEmployeerAmount;// 企业人数
    private HConnection companyBaseHConn, companyBaseInfoCheckHConn;
    private final int companyBaseFlag = 97, companyBaseInfoCheckFlag = 110;
    private List<BaseInfoEntity> listBaseinfo = new ArrayList<BaseInfoEntity>();
    private RelativeLayout rlBaseInfo;
    private RelativeLayout rlOtherInfo;
    private EditText contactname;//联系人姓名
    private EditText et_lgl_psn_no;//法人代表身份证账号
    private EditText et_rgst_adr_str;//注册地址
    private EditText et_phonenum;//联系人电话
    private EditText et_lgl_psn_nm;//法人代表姓名
    private EditText et_rgst_area;//注册面积
    private EditText et_bsc_ac_ar_id;//基本账户账号
    private EditText et_org_code_cert;//组织结构代码证
    private EditText et_special_id_name;  //其它证件名称
    private EditText et_special_id_code;//其它证件号码
    private TextView et_bsc_ac_opn_dt;//基本账户开户日期
    private EditText tvHangyeDalei;//行业大类
    private EditText et_rgst_adr_true_str;//实际经营地址
    private EditText et_oper_scope ;//经营范围
    private EditText et_rgst_area_true;//实际经营面积
    private EditText et_list_exchange;//上市地
    private EditText et_stock_code;//股票代码
    private ImageView iv_stk_avlb_flag;//是否上市公司

    int iv_stk =isTrue;//是否上市公司
    int is_rel_farm = isTrue;//是否涉农
    int is_patent= isTrue;//是否有专利
    int is_restric_ent=isTrue;//限制性行业
    int is_stockholder = isTrue;//是否股东
    private ImageView iv_is_rel_farm;//是否涉农
    private ImageView iv_is_patent;//是否限制性行业
    private ImageView iv_is_restric_ent;//是否有专利
    private ImageView iv_stockholder;//是否本行股东

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_businesscom, null);
        setupView(view);
        setListener();
        sendRequestForBaseInfo();// 查询客户基本信息
        return view;
    }

    // 查询客户基本信息
    private void sendRequestForBaseInfo() {

        String custID = ActivityBaseInfo.custID;

        companyBaseInfoCheckHConn = RequestInfo(this, requestType.Search,
                InterfaceInfo.BookBuildingBaseInfo_Get, custID,
                companyBaseInfoCheckFlag);

        // if (Tool.haveNet(getActivity())) {
        // String request = Tool.getRequestStr("T011148", custID);
        // requestHr = new HRequest(request, "GET");
        // Log.i("1111", "2222 1baseinfo....:" + requestStr);
        // try {btn_next
        // HConnection.isShowLoadingProcess = true; // 不显示loading
        // companyBaseInfoCheckHConn = new HConnection(this, requestHr,
        // LogoActivity.user,
        // HConnection.RESPONSE_TYPE_COMPLEX_JSON);
        // companyBaseInfoCheckHConn.setIndex();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // } else {
        // Toast.makeText(getActivity(), "当前无网络连接！", 0).show();
        // }
    }

    private void setListener() {
        iv_stockholder.setOnClickListener(this);
        iv_is_patent.setOnClickListener(this);
        iv_is_restric_ent.setOnClickListener(this);
        et_bsc_ac_opn_dt.setOnClickListener(this);
        iv_is_rel_farm.setOnClickListener(this);
        iv_stk_avlb_flag.setOnClickListener(this);
        etAcountOpenBank.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        spinStyle.setOnClickListener(this);
//        spinModuel.setOnClickListener(this);
        spinPro.setOnClickListener(this);
//        businessSrange.setOnClickListener(this);
//        businessPro.setOnClickListener(this);
        businessState.setOnClickListener(this);
        rlBaseInfo.setOnClickListener(this);
        rlOtherInfo.setOnClickListener(this);
        tvHangyeDalei.setOnClickListener(this);// 行业大类
//        tvHangyeXielei.setOnClickListener(this);// 行业细类
    }

    private void setupView(View view) {

        iv_stockholder = (ImageView) view.findViewById(R.id.iv_stockholder); //是否本行股东
        iv_is_patent = (ImageView) view.findViewById(R.id.iv_is_patent);//是否限制性行业
        iv_is_restric_ent = (ImageView) view.findViewById(R.id.iv_is_restric_ent);//是否有专利
        iv_is_rel_farm = (ImageView) view.findViewById(R.id.iv_is_rel_farm);//是否涉农
        iv_stk_avlb_flag = (ImageView) view.findViewById(R.id.iv_stk_avlb_flag);//是否上市公司
        et_stock_code = (EditText) view.findViewById(R.id.et_stock_code);//股票代码
        et_list_exchange = (EditText) view.findViewById(R.id.et_list_exchange);//上市地
        et_rgst_adr_true_str = (EditText) view.findViewById(R.id.et_rgst_adr_true_str);//实际经营地址
        et_oper_scope = (EditText) view.findViewById(R.id.et_oper_scope);//经营范围
        et_rgst_area_true = (EditText) view.findViewById(R.id.et_rgst_area_true);//实际经营面积
        tvHangyeDalei = (EditText) view.findViewById(R.id.tv_hangyedalei);//行业大类
        et_bsc_ac_opn_dt = (TextView) view.findViewById(R.id.et_bsc_ac_opn_dt);//基本账户开户日期
        et_special_id_code = (EditText) view.findViewById(R.id.et_special_id_code);//其它证件号码
        et_special_id_name = (EditText) view.findViewById(R.id.et_special_id_name);//其它证件名称
        et_org_code_cert = (EditText) view.findViewById(R.id.et_org_code_cert);//组织结构代码证
        et_bsc_ac_ar_id = (EditText) view.findViewById(R.id.et_bsc_ac_ar_id);//基本账户账号
        et_rgst_area = (EditText) view.findViewById(R.id.et_rgst_area);//注册面积
        et_lgl_psn_nm = (EditText) view.findViewById(R.id.et_LGL_PSN_NM);//法人代表姓名
        et_phonenum = (EditText) view.findViewById(R.id.et_PHONENUM);//联系人电话
        et_rgst_adr_str = (EditText) view.findViewById(R.id.et_rgst_adr_str);//注册地址
        et_lgl_psn_no = (EditText) view.findViewById(R.id.et_lgl_psn_no);//法人代表身份证号
        contactname = (EditText) view.findViewById(R.id.et_contactname);//联系人地址
        spinStyle = (EditText) view.findViewById(R.id.spin_baseinfostyle);// 客户类型的那三个
//        spinModuel = (EditText) view.findViewById(R.id.spin_baseinfomodel);//    企业规模
        spinPro = (EditText) view.findViewById(R.id.spin_baseinfoproperty);//  企业性质
//        etCompanyOwner = (EditText) view.findViewById(R.id.et_companyowner)//企业法人
//        etAddress = (EditText) view.findViewById(R.id.et_companyeara);
        businessState = (EditText) view.findViewById(R.id.spin_businessstate);
        etInfo1 = (EditText) view.findViewById(R.id.et_info1);//注册资本
        etInfo9 = (EditText) view.findViewById(R.id.et_info9);
        etAcountOpenBank = (EditText) view.findViewById(R.id.et_acount_open_bank);//基本账户开户行
        etInfo6 = (EditText) view.findViewById(R.id.et_info6);
        etInfo5 = (EditText) view.findViewById(R.id.et_info5);
        etInfo4 = (EditText) view.findViewById(R.id.et_info4);
        etInfo8 = (EditText) view.findViewById(R.id.et_info8);//机构信用代码
        etAnnualIncome = (EditText) view.findViewById(R.id.et_annual_income);


//        tvHangyeXielei = (EditText) view.findViewById(R.id.tv_hangyexilei);// 行业细类
        etEmployeerAmount = (EditText) view.findViewById(R.id.et_employeer_amount);// 行业细类

        btnNext = (Button) view.findViewById(R.id.btn_next);
        btnSave = (Button) view.findViewById(R.id.btn_save);

        rlBaseInfo = (RelativeLayout) view.findViewById(R.id.rl_bookbuildingbaseinfo);
        rlOtherInfo = (RelativeLayout) view.findViewById(R.id.rl_bookbuildingother);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if (getActivity() instanceof WorkPlaceItemChange) {
                    ((WorkPlaceItemChange) getActivity()).workPlacestyleChange(0);
                }
                break;
            case R.id.btn_save:
                sendRequestToServices();
                break;
            case R.id.spin_baseinfostyle:// 客户类型
                SpinnerAdapter.showSelectDialog(getActivity(),
                        NewCatevalue.custClassType, spinStyle,
                        new CallBackItemClickListener() {
                            @Override
                            public void CallBackItemClick(String spinnerID) {
                                customerStyleId = spinnerID;
                            }
                        });
                break;
            case R.id.et_bsc_ac_opn_dt://账户开户日期
                DatePickerShow(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(String year, String month, String day) {

                        String dayate = year + "-" + month + "-" + day;
                        et_bsc_ac_opn_dt.setText(dayate);
                    }
                },et_bsc_ac_opn_dt.getText().toString());
                break;

            case R.id.spin_baseinfoproperty:// 企业性质
                SpinnerAdapter.showSelectDialog(getActivity(),
                        NewCatevalue.OrgType, spinPro,
                        new CallBackItemClickListener() {
                            @Override
                            public void CallBackItemClick(String spinnerID) {
                                propertyId = spinnerID;
                            }
                        });
                // propertyId=
                break;
            case R.id.tv_hangyedalei://行业大类（选定为类型）
                SpinnerAdapter.showSelectDialog(getActivity(),
                        NewCatevalue.corptp, tvHangyeDalei,
                        new CallBackItemClickListener() {
                            @Override
                            public void CallBackItemClick(String spinnerID) {
                                tvHangyeDaleiId =spinnerID;
                            }
                        }
                );
                break;

		case R.id.et_acount_open_bank:// 基本帐户开户行
			SpinnerAdapter.showSelectDialog(getActivity(),
					NewCatevalue.busi_condition, etAcountOpenBank,
					new CallBackItemClickListener() {
						@Override
						public void CallBackItemClick(String spinnerID) {
                            OpenBankId = spinnerID;
						}
					});
			break;
            case R.id.spin_businessstate:// 经营状况
                SpinnerAdapter.showSelectDialog(getActivity(),
                        NewCatevalue.busi_condition, businessState,
                        new CallBackItemClickListener() {
                            @Override
                            public void CallBackItemClick(String spinnerID) {
                                stateId = spinnerID;
                            }
                        });
                break;
            case R.id.rl_bookbuildingother:
                if (getActivity() instanceof WorkPlaceItemChange) {
                    ((WorkPlaceItemChange) getActivity()).workPlacestyleChange(0);
                }
                break;
            case R.id.iv_stk_avlb_flag://是否上市公司
                iv_stk = iv_stk == isTrue? isFalse:isTrue;
                Tool.setSelect(iv_stk_avlb_flag,iv_stk);
                break;
            case R.id.iv_is_rel_farm://是否涉农
                is_rel_farm = is_rel_farm==isTrue? isFalse:isTrue;
                Tool.setSelect(iv_is_rel_farm,is_rel_farm);
            case R.id.iv_is_patent://是否有专利
                is_patent = is_patent==isTrue? isFalse:isTrue;
                Tool.setSelect(iv_is_patent,is_patent);
                break;
            case R.id.iv_is_restric_ent://限制性行业
                is_restric_ent = is_restric_ent==isTrue?isFalse:isTrue;
                Tool.setSelect(iv_is_restric_ent,is_restric_ent);
                break;
            case R.id.iv_stockholder:
                is_stockholder=is_stockholder==isTrue?isFalse:isTrue;
                Tool.setSelect(iv_stockholder,is_stockholder);
                break;

            default:
                break;
        }
    }
//发送数据给服务器
    private void sendRequestToServices() {

        if(TextUtils.isEmpty(Tool.getTextValue(et_org_code_cert)) ){
            Toast.makeText(getActivity(), "组织机构代码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        BaseInfoEntity newBaseInfo = new BaseInfoEntity();

       // newBaseInfo.STK_AVLB_FLAG = String.valueOf(iv_stk);//是否上市公司
        newBaseInfo.IS_RESTRIC_ENT= Tool.ToString(iv_is_restric_ent.getTag(),"0");//是否限制性行业
        newBaseInfo.IS_PATENT = Tool.ToString(iv_is_patent.getTag(),"0");//是否专利
        newBaseInfo.STOCKHOLDER = Tool.ToString(iv_stockholder.getTag(),"0");//是否股东
        newBaseInfo.IS_REL_FARM = Tool.ToString(iv_is_rel_farm.getTag(),"0");  //是否涉农
        newBaseInfo.STK_AVLB_FLAG = Tool.ToString(iv_stk_avlb_flag.getTag(),"0");//是否上市公司
        newBaseInfo.setCustID(ActivityBaseInfo.custID);// 客户ID
        newBaseInfo.setORG_SCL_TP_ID(companyMoudleId);// 企业规模
        newBaseInfo.setCUST_TYPE(customerStyleId);// 客户类型
        newBaseInfo.setCOMPANY_PEROPERTY(propertyId);// 企业性质
       /* try {
            newBaseInfo.setINDUSTRY_BIG(Tool.getChineseEncode(tvHangyeDalei.getText().toString().trim()));// 行业大类
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
		newBaseInfo.setINDUSTRY_BIG(tvHangyeDaleiId);// 行业大类
        newBaseInfo.setINDUSTRY_DETAIL(tv_hangyexileiId);// 行业细类
        newBaseInfo.setLGL_PSN_NM(Tool.getTextValue(et_lgl_psn_nm));// 法人代表姓名
        newBaseInfo.setCON_CHN_NM(Tool.getTextValue(etRealOwner));// 实际控制人姓名
        newBaseInfo.setPHONENUM(Tool.getTextValue(et_phonenum));//联系人电话
        newBaseInfo.setBUSINESS_CONDITION(stateId);// "经营状况 01正常经营	02经营困难 03关停倒闭04其他"
        newBaseInfo.setRGS_CAPITAL(Tool.getTextValue(etInfo1));// 注册资本
        newBaseInfo.setRGST_AREA(Tool.getTextValue(et_rgst_area));//注册面积
        newBaseInfo.setBSC_AC_AR_ID(Tool.getTextValue(et_bsc_ac_ar_id));//基本账户账号
        newBaseInfo.setS_CODE(Tool.getTextValue(etInfo4));// 中征码
        newBaseInfo.setContactname(Tool.getTextValue(contactname));//联系人姓名
        newBaseInfo.setLGL_PSN_NO(Tool.getTextValue(et_lgl_psn_no));//法人代表身份证号
        newBaseInfo.setRGST_ADR_STR(Tool.getTextValue(et_rgst_adr_str));//注册地址
        newBaseInfo.setPDIN_CPTL_CCY_ID(Tool.getTextValue(etInfo5));// 国税登记证号码
        newBaseInfo.setESTB_CCY_ID(Tool.getTextValue(etInfo6));// 地税登记证号码
        newBaseInfo.setBSC_AC_OPN_BNK(OpenBankId);// 基本账户开户行
        newBaseInfo.setORG_CREDIT(Tool.getTextValue(etInfo8));// 机构信用代码证(人行专用)
        newBaseInfo.setBUSINESS_LICENCE(Tool.getTextValue(etInfo9));// 营业执照
        newBaseInfo.setORG_CODE_CERT(Tool.getTextValue(et_org_code_cert));// 组织结构代码证
        newBaseInfo.setSPECIAL_ID_NAME(Tool.getTextValue(et_special_id_name));//其它证件名称
        newBaseInfo.setSPECIAL_ID_CODE(Tool.getTextValue(et_special_id_code));//其它证件号码
        newBaseInfo.setBSC_AC_OPN_DT(Tool.getTextValue(et_bsc_ac_opn_dt) );//基本账户开户开户日期
        newBaseInfo.setRGST_ADR_TRUE_STR(Tool.getTextValue(et_rgst_adr_true_str));//实际经营地址
        newBaseInfo.setOPER_SCOPE(Tool.getTextValue(et_oper_scope));//经营范围
        newBaseInfo.setRGST_AREA_TRUE(Tool.getTextValue(et_rgst_area_true));////实际经营面积
        newBaseInfo.setLIST_EXCHANGE(Tool.getTextValue(et_list_exchange));//上市地
        newBaseInfo.setSTOCK_CODE(Tool.getTextValue(et_stock_code));//股票代码

        newBaseInfo.setEMPE_NBR(Tool.getTextValue(etEmployeerAmount));// 企业人数
        newBaseInfo.setYEAR_SALE(Tool.getTextValue(etAnnualIncome));//企业年销售量
        companyBaseHConn = RequestInfo(this, requestType.Insert,
                InterfaceInfo.BookBuildingBaseInfo_Creat,
                JSON.toJSONString(newBaseInfo), companyBaseFlag);

        // HRequest requestHr = null;
        // String requestStr = "";
        // String custID = BaseCustomerInfo.custID;
        // String zhengjianStyle = BaseCustomerInfo.cust_psn_card_type;
        // if (Tool.haveNet(getActivity())) {
        // requestStr = "T011104?method=getJSON&userCode=" + "109011" //
        // 目前测试数据之用的，
        // // 以后这种测试数据之用的都要删掉的
        // // + LogoActivity.user.getUserCode()
        // + "&seriNo=" + "DMRJRUWQF182" // 目前测试数据之用的， 以后这种测试数据之用的都要删掉的
        // // + LogoActivity.user.getImei()
        // + "&chnlCode=" + "DMRJRUWQF182" // 这里似乎应该是02
        // + "&transCode=T011104&brCode=" + "1101707" // 目前测试数据之用的，
        // // 以后这种测试数据之用的都要删掉的
        // // + LogoActivity.user.getBrCode()
        // + "&spareOne=123" + "&jsonData={custID:\"" + custID // custid
        // + "\",CUST_TYPE:\"" + customerStyleId2 // 客户类型
        // + "\",COMPANY_PEROPERTY:\"" // 企业性质
        // + propertyId2 + "\",INDUSTRY_BIG:\"" // 行业大类
        // + hangyedalei + "\",INDUSTRY_DETAIL:\"" // 行业细类
        // + hangyexilei + "\",LGL_PSN_NM:\"" // 法人代表姓名
        // + companyOwner + "\",LGL_PSN_ID_TP_ID:\"" // 法人代表证件类型
        // + zhengjianStyle + "\",CON_CHN_NM:\"" // 实际控制人姓名
        // + realOwner + "\",BUSI_ADDR:\"" // 办公地址
        // + address + "\",OPER_SCOPE:\"" // 经营范围
        // + rangeId + "\",OPER_PRD:\"" // 经营产品
        // + proId + "\",BUSINESS_CONDITION:\"" // 经营状况
        // + stateId + "\",MANUFACT_ADDR:\"" // 生产地址
        // + add + "\",ENT_EMPLOY:\"" // 企业员工
        // + employeer + "\",RGS_CAPITAL:\"" // 注册资本
        // + info1 + "\",RGST_ADR_STR:\"" // 注册地址
        // + info2 + "\",LN_CRD_NO:\"" // 贷款卡号
        // + info3 + "\",S_CODE:\"" // 中征码
        // + info4 + "\",PDIN_CPTL_CCY_ID:\"" // 国税登记证号码
        // + info5 + "\",ESTB_CCY_ID:\"" // 地税登记证号码
        // + info6 + "\",SPECIAL_ID_CODE:\"" // 其他证件号码(特种证件)
        // + info7 + "\",ORG_CREDIT:\"" // 机构信用代码证(人行专用)
        // + info8 + "\",BUSINESS_LICENCE:\"" // 营业执照
        // + info9 + "\",ORG_CODE_CERT:\"" // 组织结构代码证
        // + info10 + "\"}";
        // requestHr = new HRequest(requestStr, "GET");
        // Log.i("1111", "2222quyu:" + requestStr);
        // try {
        // HConnection.isShowLoadingProcess = true; // 不显示loading
        // companyBaseHConn = new HConnection(this, requestHr,
        // LogoActivity.user,
        // HConnection.RESPONSE_TYPE_COMPLEX_JSON);
        // companyBaseHConn.setIndex(companyBaseFlag);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // } else {
        // Toast.makeText(getActivity(), "当前无网络连接！", 0).show();
        // }
    }

    @Override
    public void onConnected(Message msg) {
        super.onConnected(msg);
        switch (connectionIndex) {
            case HConnection.RESPONSE_ERROR:

                break;
            case companyBaseInfoCheckFlag:// 查询客户基本信息
                HResponse companyBaseCheckRes = companyBaseInfoCheckHConn
                        .getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (companyBaseCheckRes == null
                        || companyBaseCheckRes.dataJsonObject == null) {
                    return;
                }
                JSONObject tmpJsonBaseCheckObject = companyBaseCheckRes.dataJsonObject;
                Log.i("1111",
                        "....companybase....:" + tmpJsonBaseCheckObject.toString());
                readCompanyBaseJson(tmpJsonBaseCheckObject, connectionIndex);
                break;
            case companyBaseFlag:
                HResponse companyBaseRes = companyBaseHConn
                        .getResponse(HConnection.RESPONSE_TYPE_COMPLEX_JSON);
                if (companyBaseRes == null || companyBaseRes.dataJsonObject == null) {
                    return;
                }
                JSONObject tmpJsonObject = companyBaseRes.dataJsonObject;
                Log.i("1111", "....companybase....:" + tmpJsonObject.toString());
                readCompanyBaseJson(tmpJsonObject, connectionIndex);
                break;
        }
    }

    private void readCompanyBaseJson(JSONObject tmpJsonObject,
                                     int connectionIndex) {
        String retCode = "";
        try {
            if (tmpJsonObject.has("retCode")) {
                retCode = tmpJsonObject.getString("retCode");
            }
            if ("0000".equals(retCode)) {
                switch (connectionIndex) {
                    case companyBaseFlag:
                        if (getActivity() instanceof WorkPlaceItemChange) {
                            ((WorkPlaceItemChange) getActivity())
                                    .workPlacestyleChange(0);
                        }
                        break;
                    case companyBaseInfoCheckFlag:
                        listBaseinfo.clear();
                        String groupStr = tmpJsonObject.getString("group");
                        if (TextUtils.isEmpty(groupStr)) {
                            Log.i("1baseInfo chaxun......",
                                    "1baseInfo chaxun......" + groupStr);
                            return;
                        } else {
                            listBaseinfo = JSON.parseArray(groupStr,
                                    BaseInfoEntity.class);
                            Log.i("gudingchaxun......", "gudingchaxun1......"
                                    + groupStr);
                            Log.i("gudingchaxun......", "gudingchaxun2......"
                                    + listBaseinfo.toString());
                            // 将请求到的数据反显
                            showCustomerBaseInfo(listBaseinfo);
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

    // 将查询到的数据反显
    private void showCustomerBaseInfo(List<BaseInfoEntity> listBaseinfo2) {
        if (listBaseinfo2 == null || listBaseinfo2.size() == 0) {
            return;
        }

        BaseInfoEntity entity = listBaseinfo2.get(0);
        // 客户类型
        String customerStyle = NewCatevalue.getLabel(getActivity(),
                NewCatevalue.custClassType, entity.getCUST_TYPE());
        spinStyle.setText(customerStyle);
        customerStyleId = entity.getCUST_TYPE();

        // 企业规模
      /*  String customerMoudle = NewCatevalue.getLabel(getActivity(),
                NewCatevalue.enterScale, entity.getORG_SCL_TP_ID());
        spinModuel.setText(customerMoudle);*/
//        companyMoudleId = entity.getORG_SCL_TP_ID();

        //企业性质
        String com_property = NewCatevalue.getLabel(getActivity(), NewCatevalue.OrgType, entity.getCOMPANY_PEROPERTY());
        spinPro.setText(com_property);
        propertyId = entity.getCOMPANY_PEROPERTY();
//
        // 行业大类
		String indu_big = NewCatevalue.getLabel(getActivity(), NewCatevalue.corptp, entity.getINDUSTRY_BIG());
		tvHangyeDalei.setText(indu_big);
        tvHangyeDaleiId = entity.getINDUSTRY_BIG();
//        tvHangyeDalei.setText(entity.getINDUSTRY_BIG());

//        // 行业细类
//        String indu_sec = NewCatevalue.getLabel(getActivity(), NewCatevalue.industy_sec, entity.getINDUSTRY_DETAIL());
//        tvHangyeXielei.setText(indu_sec);

        //基本账户开户行
        String openBanckCount = NewCatevalue.getLabel(getActivity(),NewCatevalue.signBank,entity.getBSC_AC_OPN_BNK());
        etAcountOpenBank.setText(openBanckCount);
        OpenBankId = entity.getBSC_AC_OPN_BNK();

      // Tool.IntegerParse(Tool.getJsonValue(entity.STK_AVLB_FLAG+"","STK_AVLB_FLAG"));//是否上市公司
//       String isAvlb =   entity.STK_AVLB_FLAG;
//        int avlbFlag = 0;
//        if(isAvlb != null || !"".equals(isAvlb)){
//            avlbFlag = Tool.IntegerParse(entity.STK_AVLB_FLAG);
//        }
        Tool.setSelect(iv_is_patent,Tool.parseInt(entity.IS_PATENT));//是否专利
        Tool.setSelect(iv_is_restric_ent,Tool.parseInt(entity.IS_RESTRIC_ENT));//限制性行业
        Tool.setSelect(iv_stockholder,Tool.parseInt(entity.STOCKHOLDER));//股东
        Tool.setSelect(iv_stk_avlb_flag,Tool.parseInt(entity.STK_AVLB_FLAG));//是否上市公司
        Tool.setSelect(iv_is_rel_farm,Tool.parseInt(entity.IS_REL_FARM));//是否涉农

        et_stock_code.setText(entity.getSTOCK_CODE());//股票代码
        et_list_exchange.setText(entity.getLIST_EXCHANGE());//上市地
        et_rgst_area_true.setText(entity.getRGST_AREA_TRUE());//实际经营面积
        et_rgst_adr_true_str.setText(entity.getRGST_ADR_TRUE_STR());//实际经营地址
        et_oper_scope.setText(entity.getOPER_SCOPE());//经营范围
        et_bsc_ac_opn_dt.setText(entity.getBSC_AC_OPN_DT());//基本账户开户日期
        et_bsc_ac_ar_id.setText(entity.getBSC_AC_AR_ID());//基本账户账号
        et_lgl_psn_nm.setText(entity.getLGL_PSN_NM());              //  法人代表姓名
                                                                    //注册资本
         et_rgst_area.setText(entity.getRGST_AREA());   //注册面积
        et_rgst_adr_str.setText(entity.getRGST_ADR_STR());//注册地址
        et_lgl_psn_no.setText(entity.getLGL_PSN_NO());//法人代表身份证号码
        et_phonenum.setText(entity.getPHONENUM());//联系人电话
        // 经营范围 经营范围目前也没有数据码值
//		String businessRan = Catevalue.getValue(entity.getOPER_SCOPE(), CompayValue.infoStr, CompayValue.infoValue);
//        String businessRan = NewCatevalue.getLabel(getActivity(), NewCatevalue.ent_other_rel, entity.getOPER_SCOPE());
//        businessSrange.setText(businessRan);
//        rangeId = entity.getOPER_SCOPE();



        // 经营状况
        String strState = NewCatevalue.getLabel(getActivity(), NewCatevalue.busi_condition, entity.getBUSINESS_CONDITION());

        businessState.setText(strState);
        stateId = entity.getBUSINESS_CONDITION();



        // 注册资本
        contactname.setText(entity.getContactname());
        etInfo1.setText(entity.getRGS_CAPITAL());
        etInfo4.setText(entity.getS_CODE());
        etInfo5.setText(entity.getPDIN_CPTL_CCY_ID());
        etInfo6.setText(entity.getESTB_CCY_ID());
        etInfo8.setText(entity.getORG_CREDIT());//xiny信用
        etInfo9.setText(entity.getBUSINESS_LICENCE());
        et_org_code_cert.setText(entity.getORG_CODE_CERT());
        et_special_id_name.setText(entity.getSPECIAL_ID_NAME());
        et_special_id_code.setText(entity.getSPECIAL_ID_CODE());
        etEmployeerAmount.setText(entity.getEMPE_NBR());
        etAnnualIncome.setText(entity.getYEAR_SALE());
    }
}
