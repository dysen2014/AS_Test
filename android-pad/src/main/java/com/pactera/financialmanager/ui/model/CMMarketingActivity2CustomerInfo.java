package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 客户信息返回的 Javabena ， 在CMMarketingActivity2类中用到的
 */
public class CMMarketingActivity2CustomerInfo implements Serializable {

	private static final long serialVersionUID = -7860604500343396206L;

	private String DUE_ON_DEMAND_ALL;//活期存款余额
	private String MAIN_CONTACT_PHONE;//客户经理电话
	private String LGL_PSN_CON_PHONE;//法人手机号码
	private String THR_MON_CUR_DAVG_DEP;//金融资产总余额
	private String PAYROLL_SNG;//代发工资签约标志     
	private String USERNAME;//
//	private String OWNER_BRID;//
	private String FIXED_TIME_DEPOSIT_ALL;//定期存款余额
	private String CUST_CARD_TYPE;//证件类型
	private String BGD_HOLD;//承兑汇票持有标志     
	private String LGL_PSN_NM;//法人代表姓名
	private String XYB_FNC_HOLD;//兴业银行理财持有标志 
	private String NEW_TIME_YAMT;//贷款余额
	private String CUST_VALUE;//
	private String ORG_LGL_NM;//公司名称
	private String CUST_CARD_NUMBER;
	private String OWNERID;//
	private String DIS_HOLD;//贴现持有标志 
	private String BUSINESS_LICENCE;//营业执照号
	private String CUR_YR_LOAN_DAVG;//理财产品余额
	private String UNIT_CARD_SNG;//单位结算卡持有标志   
	private String STFF_F; //是否本行员工标示
	private String PHONE_NO;//电话号码
	private String CUST_PSN_CARD_NUMBER;//身份证号码
	
	private String CUSTID;// 客户id
	private String IDV_CHN_NM;// 客户姓名
	private String CUST_VALUE_RATE;// 客户评级
	private String RSK_CTRL;// 风险偏好
	private String owner_brid;// 所属机构
	private String BRNAME;// 机构名称
	private String IDV_BRTH_DT_GL;// 生日
	private String IDV_GND_ID;// 性别
	private String OWNERNAME;// 客户经理
	private String OWNERPHONE;// 客户经理电话
	private String MOBL_BANK_SGN;// 手机银行签约标志
	private String B_MOBL_BANK_SGN;// 商务手机银行签约标志
	private String TEL_BANK_SGN;// 电话银行签约标志
	private String SMS_BANK_SGN;// 短信银行签约标志
	private String POS_SGN;// POS机签约标志
	private String KLF_SGN;// 卡乐付签约标志
	private String WXBANK_SGN;// 微信银行签约标志
	private String EBANK_SGN;// 网银签约标志
	private String EBANK_PAD_SGN;// 网银PAD版签约标志
	private String UNPAY_SGN;// 第三方支付-银联在线签约标
	private String ALIPAY_SGN;// 第三方支付-支付宝签约标志
	private String JDPAY_SGN;// 第三方支付-京东签约标志
	private String TENPAY_SGN;// 第三方支付-财付通签约标志
	private String BDPAY_SGN;// 第三方支付-百付宝签约标志
	private String PWR_SGN;// 代缴电费签约标志
	private String WRT_SGN;// 代缴水费签约标志
	private String TPS_SGN;// 省财税库行签约标志
	private String DEBIT_CARD_HOLD;// 借记卡持有标志
	private String DEBIT_CARD_LVL_CD;// 借记卡最高等级标志
	private String CRDT_CARD_HOLD;// 信用卡持有标志
	private String CRDT_CARD_LVL_CD;// 信用卡最高等级
	private String CURR_DPST_HOLD;// 活期存款持有标志
	private String TM_DPST_HOLD;// 定期存款持有标志
	private String LOAN_HOLD;// 贷款持有标志
	private String FNC_HOLD;// 行内理财持有标志
	private String SGN_PROD_COUNT;// 签约产品数量
	private String HOLD_PROD_COUNT;// 持有产品数量
	private String PRO_PRO_AMT;// 客户总资产
	private String DEPOSIT_PRO_AMT_ALL;// 存款余额
	private String FUND_AMT;// 基金余额
	private String MMM_PRO_AMT;// 理财余额
	private String NEW_LC_MAMT;// 个贷余额

	public String getCUSTID() {
		return CUSTID;
	}

	public void setCUSTID(String cUSTID) {
		CUSTID = cUSTID;
	}

	public String getIDV_CHN_NM() {
		return IDV_CHN_NM;
	}

	public void setIDV_CHN_NM(String iDV_CHN_NM) {
		IDV_CHN_NM = iDV_CHN_NM;
	}

	public String getSTFF_F() {
		return STFF_F;
	}

	public void setSTFF_F(String sTFF_F) {
		STFF_F = sTFF_F;
	}

	public String getCUST_VALUE_RATE() {
		return CUST_VALUE_RATE;
	}

	public void setCUST_VALUE_RATE(String cUST_VALUE_RATE) {
		CUST_VALUE_RATE = cUST_VALUE_RATE;
	}

	public String getRSK_CTRL() {
		return RSK_CTRL;
	}

	public void setRSK_CTRL(String rSK_CTRL) {
		RSK_CTRL = rSK_CTRL;
	}

	public String getPHONE_NO() {
		return PHONE_NO;
	}

	public void setPHONE_NO(String pHONE_NO) {
		PHONE_NO = pHONE_NO;
	}

	public String getCUST_PSN_CARD_NUMBER() {
		return CUST_PSN_CARD_NUMBER;
	}

	public void setCUST_PSN_CARD_NUMBER(String cUST_PSN_CARD_NUMBER) {
		CUST_PSN_CARD_NUMBER = cUST_PSN_CARD_NUMBER;
	}

	public String getOwner_brid() {
		return owner_brid;
	}

	public void setOwner_brid(String owner_brid) {
		this.owner_brid = owner_brid;
	}

	public String getBRNAME() {
		return BRNAME;
	}

	public void setBRNAME(String bRNAME) {
		BRNAME = bRNAME;
	}

	public String getIDV_BRTH_DT_GL() {
		return IDV_BRTH_DT_GL;
	}

	public void setIDV_BRTH_DT_GL(String iDV_BRTH_DT_GL) {
		IDV_BRTH_DT_GL = iDV_BRTH_DT_GL;
	}

	public String getIDV_GND_ID() {
		return IDV_GND_ID;
	}

	public void setIDV_GND_ID(String iDV_GND_ID) {
		IDV_GND_ID = iDV_GND_ID;
	}

	public String getOWNERNAME() {
		return OWNERNAME;
	}

	public void setOWNERNAME(String oWNERNAME) {
		OWNERNAME = oWNERNAME;
	}

	public String getOWNERPHONE() {
		return OWNERPHONE;
	}

	public void setOWNERPHONE(String oWNERPHONE) {
		OWNERPHONE = oWNERPHONE;
	}

	public String getMOBL_BANK_SGN() {
		return MOBL_BANK_SGN;
	}

	public void setMOBL_BANK_SGN(String mOBL_BANK_SGN) {
		MOBL_BANK_SGN = mOBL_BANK_SGN;
	}

	public String getB_MOBL_BANK_SGN() {
		return B_MOBL_BANK_SGN;
	}

	public void setB_MOBL_BANK_SGN(String b_MOBL_BANK_SGN) {
		B_MOBL_BANK_SGN = b_MOBL_BANK_SGN;
	}

	public String getTEL_BANK_SGN() {
		return TEL_BANK_SGN;
	}

	public void setTEL_BANK_SGN(String tEL_BANK_SGN) {
		TEL_BANK_SGN = tEL_BANK_SGN;
	}

	public String getSMS_BANK_SGN() {
		return SMS_BANK_SGN;
	}

	public void setSMS_BANK_SGN(String sMS_BANK_SGN) {
		SMS_BANK_SGN = sMS_BANK_SGN;
	}

	public String getPOS_SGN() {
		return POS_SGN;
	}

	public void setPOS_SGN(String pOS_SGN) {
		POS_SGN = pOS_SGN;
	}

	public String getKLF_SGN() {
		return KLF_SGN;
	}

	public void setKLF_SGN(String kLF_SGN) {
		KLF_SGN = kLF_SGN;
	}

	public String getWXBANK_SGN() {
		return WXBANK_SGN;
	}

	public void setWXBANK_SGN(String wXBANK_SGN) {
		WXBANK_SGN = wXBANK_SGN;
	}

	public String getEBANK_SGN() {
		return EBANK_SGN;
	}

	public void setEBANK_SGN(String eBANK_SGN) {
		EBANK_SGN = eBANK_SGN;
	}

	public String getEBANK_PAD_SGN() {
		return EBANK_PAD_SGN;
	}

	public void setEBANK_PAD_SGN(String eBANK_PAD_SGN) {
		EBANK_PAD_SGN = eBANK_PAD_SGN;
	}

	public String getUNPAY_SGN() {
		return UNPAY_SGN;
	}

	public void setUNPAY_SGN(String uNPAY_SGN) {
		UNPAY_SGN = uNPAY_SGN;
	}

	public String getALIPAY_SGN() {
		return ALIPAY_SGN;
	}

	public void setALIPAY_SGN(String aLIPAY_SGN) {
		ALIPAY_SGN = aLIPAY_SGN;
	}

	public String getJDPAY_SGN() {
		return JDPAY_SGN;
	}

	public void setJDPAY_SGN(String jDPAY_SGN) {
		JDPAY_SGN = jDPAY_SGN;
	}

	public String getTENPAY_SGN() {
		return TENPAY_SGN;
	}

	public void setTENPAY_SGN(String tENPAY_SGN) {
		TENPAY_SGN = tENPAY_SGN;
	}

	public String getBDPAY_SGN() {
		return BDPAY_SGN;
	}

	public void setBDPAY_SGN(String bDPAY_SGN) {
		BDPAY_SGN = bDPAY_SGN;
	}

	public String getPWR_SGN() {
		return PWR_SGN;
	}

	public void setPWR_SGN(String pWR_SGN) {
		PWR_SGN = pWR_SGN;
	}

	public String getWRT_SGN() {
		return WRT_SGN;
	}

	public void setWRT_SGN(String wRT_SGN) {
		WRT_SGN = wRT_SGN;
	}

	public String getTPS_SGN() {
		return TPS_SGN;
	}

	public void setTPS_SGN(String tPS_SGN) {
		TPS_SGN = tPS_SGN;
	}

	public String getDEBIT_CARD_HOLD() {
		return DEBIT_CARD_HOLD;
	}

	public void setDEBIT_CARD_HOLD(String dEBIT_CARD_HOLD) {
		DEBIT_CARD_HOLD = dEBIT_CARD_HOLD;
	}

	public String getDEBIT_CARD_LVL_CD() {
		return DEBIT_CARD_LVL_CD;
	}

	public void setDEBIT_CARD_LVL_CD(String dEBIT_CARD_LVL_CD) {
		DEBIT_CARD_LVL_CD = dEBIT_CARD_LVL_CD;
	}

	public String getCRDT_CARD_HOLD() {
		return CRDT_CARD_HOLD;
	}

	public void setCRDT_CARD_HOLD(String cRDT_CARD_HOLD) {
		CRDT_CARD_HOLD = cRDT_CARD_HOLD;
	}

	public String getCRDT_CARD_LVL_CD() {
		return CRDT_CARD_LVL_CD;
	}

	public void setCRDT_CARD_LVL_CD(String cRDT_CARD_LVL_CD) {
		CRDT_CARD_LVL_CD = cRDT_CARD_LVL_CD;
	}

	public String getCURR_DPST_HOLD() {
		return CURR_DPST_HOLD;
	}

	public void setCURR_DPST_HOLD(String cURR_DPST_HOLD) {
		CURR_DPST_HOLD = cURR_DPST_HOLD;
	}

	public String getTM_DPST_HOLD() {
		return TM_DPST_HOLD;
	}

	public void setTM_DPST_HOLD(String tM_DPST_HOLD) {
		TM_DPST_HOLD = tM_DPST_HOLD;
	}

	public String getLOAN_HOLD() {
		return LOAN_HOLD;
	}

	public void setLOAN_HOLD(String lOAN_HOLD) {
		LOAN_HOLD = lOAN_HOLD;
	}

	public String getFNC_HOLD() {
		return FNC_HOLD;
	}

	public void setFNC_HOLD(String fNC_HOLD) {
		FNC_HOLD = fNC_HOLD;
	}

	public String getSGN_PROD_COUNT() {
		return SGN_PROD_COUNT;
	}

	public void setSGN_PROD_COUNT(String sGN_PROD_COUNT) {
		SGN_PROD_COUNT = sGN_PROD_COUNT;
	}

	public String getHOLD_PROD_COUNT() {
		return HOLD_PROD_COUNT;
	}

	public void setHOLD_PROD_COUNT(String hOLD_PROD_COUNT) {
		HOLD_PROD_COUNT = hOLD_PROD_COUNT;
	}

	public String getPRO_PRO_AMT() {
		return PRO_PRO_AMT;
	}

	public void setPRO_PRO_AMT(String pRO_PRO_AMT) {
		PRO_PRO_AMT = pRO_PRO_AMT;
	}

	public String getDEPOSIT_PRO_AMT_ALL() {
		return DEPOSIT_PRO_AMT_ALL;
	}

	public void setDEPOSIT_PRO_AMT_ALL(String dEPOSIT_PRO_AMT_ALL) {
		DEPOSIT_PRO_AMT_ALL = dEPOSIT_PRO_AMT_ALL;
	}

	public String getFUND_AMT() {
		return FUND_AMT;
	}

	public void setFUND_AMT(String fUND_AMT) {
		FUND_AMT = fUND_AMT;
	}

	public String getMMM_PRO_AMT() {
		return MMM_PRO_AMT;
	}

	public void setMMM_PRO_AMT(String mMM_PRO_AMT) {
		MMM_PRO_AMT = mMM_PRO_AMT;
	}

	public String getNEW_LC_MAMT() {
		return NEW_LC_MAMT;
	}

	public void setNEW_LC_MAMT(String nEW_LC_MAMT) {
		NEW_LC_MAMT = nEW_LC_MAMT;
	}

	public String getDUE_ON_DEMAND_ALL() {
		return DUE_ON_DEMAND_ALL;
	}

	public void setDUE_ON_DEMAND_ALL(String dUE_ON_DEMAND_ALL) {
		DUE_ON_DEMAND_ALL = dUE_ON_DEMAND_ALL;
	}

	public String getMAIN_CONTACT_PHONE() {
		return MAIN_CONTACT_PHONE;
	}

	public void setMAIN_CONTACT_PHONE(String mAIN_CONTACT_PHONE) {
		MAIN_CONTACT_PHONE = mAIN_CONTACT_PHONE;
	}

	public String getLGL_PSN_CON_PHONE() {
		return LGL_PSN_CON_PHONE;
	}

	public void setLGL_PSN_CON_PHONE(String lGL_PSN_CON_PHONE) {
		LGL_PSN_CON_PHONE = lGL_PSN_CON_PHONE;
	}

	public String getTHR_MON_CUR_DAVG_DEP() {
		return THR_MON_CUR_DAVG_DEP;
	}

	public void setTHR_MON_CUR_DAVG_DEP(String tHR_MON_CUR_DAVG_DEP) {
		THR_MON_CUR_DAVG_DEP = tHR_MON_CUR_DAVG_DEP;
	}

	public String getPAYROLL_SNG() {
		return PAYROLL_SNG;
	}

	public void setPAYROLL_SNG(String pAYROLL_SNG) {
		PAYROLL_SNG = pAYROLL_SNG;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

//	public String getOWNER_BRID() {
//		return OWNER_BRID;
//	}
//
//	public void setOWNER_BRID(String oWNER_BRID) {
//		OWNER_BRID = oWNER_BRID;
//	}

	public String getFIXED_TIME_DEPOSIT_ALL() {
		return FIXED_TIME_DEPOSIT_ALL;
	}

	public void setFIXED_TIME_DEPOSIT_ALL(String fIXED_TIME_DEPOSIT_ALL) {
		FIXED_TIME_DEPOSIT_ALL = fIXED_TIME_DEPOSIT_ALL;
	}

	public String getCUST_CARD_TYPE() {
		return CUST_CARD_TYPE;
	}

	public void setCUST_CARD_TYPE(String cUST_CARD_TYPE) {
		CUST_CARD_TYPE = cUST_CARD_TYPE;
	}

	public String getBGD_HOLD() {
		return BGD_HOLD;
	}

	public void setBGD_HOLD(String bGD_HOLD) {
		BGD_HOLD = bGD_HOLD;
	}

	public String getLGL_PSN_NM() {
		return LGL_PSN_NM;
	}

	public void setLGL_PSN_NM(String lGL_PSN_NM) {
		LGL_PSN_NM = lGL_PSN_NM;
	}

	public String getXYB_FNC_HOLD() {
		return XYB_FNC_HOLD;
	}

	public void setXYB_FNC_HOLD(String xYB_FNC_HOLD) {
		XYB_FNC_HOLD = xYB_FNC_HOLD;
	}

	public String getNEW_TIME_YAMT() {
		return NEW_TIME_YAMT;
	}

	public void setNEW_TIME_YAMT(String nEW_TIME_YAMT) {
		NEW_TIME_YAMT = nEW_TIME_YAMT;
	}

	public String getCUST_VALUE() {
		return CUST_VALUE;
	}

	public void setCUST_VALUE(String cUST_VALUE) {
		CUST_VALUE = cUST_VALUE;
	}

	public String getORG_LGL_NM() {
		return ORG_LGL_NM;
	}

	public void setORG_LGL_NM(String oRG_LGL_NM) {
		ORG_LGL_NM = oRG_LGL_NM;
	}

	public String getCUST_CARD_NUMBER() {
		return CUST_CARD_NUMBER;
	}

	public void setCUST_CARD_NUMBER(String cUST_CARD_NUMBER) {
		CUST_CARD_NUMBER = cUST_CARD_NUMBER;
	}

	public String getOWNERID() {
		return OWNERID;
	}

	public void setOWNERID(String oWNERID) {
		OWNERID = oWNERID;
	}

	public String getDIS_HOLD() {
		return DIS_HOLD;
	}

	public void setDIS_HOLD(String dIS_HOLD) {
		DIS_HOLD = dIS_HOLD;
	}

	public String getBUSINESS_LICENCE() {
		return BUSINESS_LICENCE;
	}

	public void setBUSINESS_LICENCE(String bUSINESS_LICENCE) {
		BUSINESS_LICENCE = bUSINESS_LICENCE;
	}

	public String getCUR_YR_LOAN_DAVG() {
		return CUR_YR_LOAN_DAVG;
	}

	public void setCUR_YR_LOAN_DAVG(String cUR_YR_LOAN_DAVG) {
		CUR_YR_LOAN_DAVG = cUR_YR_LOAN_DAVG;
	}

	public String getUNIT_CARD_SNG() {
		return UNIT_CARD_SNG;
	}

	public void setUNIT_CARD_SNG(String uNIT_CARD_SNG) {
		UNIT_CARD_SNG = uNIT_CARD_SNG;
	}

}
