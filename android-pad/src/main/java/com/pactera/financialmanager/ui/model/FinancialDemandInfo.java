package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 金融需求－－存款预约 返回的数据的javabean
 */
public class FinancialDemandInfo implements Serializable {

    private static final long serialVersionUID = -1645173670931815021L;

    public final static String TAG = "FinancialDemandInfo";

    private String RR;
    private String PKID;
    private String CUSTNAME;// 客户姓名
    private String CHLTYP;// 渠道
    private String CUSTHPONE;// 手机号码
    private String CERTID; //证件号码
    private String DEMDTYP;//需求类型(主类)
    private String SUBDEMDTYP;//需求类型(子类)
    private String AMOUNT;// 预约金额
    private String BESPEAKDATE;// 预约日期
    private String STATUS;// 处理状态
    private String OPINION;//处理意见
    private String FINAL_AMT;//实际发生金额
    private String FINAL_DATE;//实际发生日期
    private String CPL_STATUS;//预约完成情况
    private String REMARKS;//备注
    private String DEAL_USERID;//处理人id
    private String USERNAME;//处理人姓名
    private String PURPOSE;

    @Override
    public String toString() {
        return "FinancialDemandInfo{" +
                "RR='" + RR + '\'' +
                ", PKID='" + PKID + '\'' +
                ", CUSTNAME='" + CUSTNAME + '\'' +
                ", CHLTYP='" + CHLTYP + '\'' +
                ", CUSTHPONE='" + CUSTHPONE + '\'' +
                ", CERTID='" + CERTID + '\'' +
                ", DEMDTYP='" + DEMDTYP + '\'' +
                ", SUBDEMDTYP='" + SUBDEMDTYP + '\'' +
                ", AMOUNT='" + AMOUNT + '\'' +
                ", BESPEAKDATE='" + BESPEAKDATE + '\'' +
                ", STATUS='" + STATUS + '\'' +
                ", OPINION='" + OPINION + '\'' +
                ", FINAL_AMT='" + FINAL_AMT + '\'' +
                ", FINAL_DATE='" + FINAL_DATE + '\'' +
                ", CPL_STATUS='" + CPL_STATUS + '\'' +
                ", REMARKS='" + REMARKS + '\'' +
                ", DEAL_USERID='" + DEAL_USERID + '\'' +
                ", USERNAME='" + USERNAME + '\'' +
                ", PURPOSE='" + PURPOSE + '\'' +
                '}';
    }

    public static String getTAG() {
        return TAG;
    }

    public String getRR() {
        return RR;
    }

    public void setRR(String RR) {
        this.RR = RR;
    }

    public String getPKID() {
        return PKID;
    }

    public void setPKID(String PKID) {
        this.PKID = PKID;
    }

    public String getCUSTNAME() {
        return CUSTNAME;
    }

    public void setCUSTNAME(String CUSTNAME) {
        this.CUSTNAME = CUSTNAME;
    }

    public String getCHLTYP() {
        return CHLTYP;
    }

    public void setCHLTYP(String CHLTYP) {
        this.CHLTYP = CHLTYP;
    }

    public String getCUSTHPONE() {
        return CUSTHPONE;
    }

    public void setCUSTHPONE(String CUSTHPONE) {
        this.CUSTHPONE = CUSTHPONE;
    }

    public String getCERTID() {
        return CERTID;
    }

    public void setCERTID(String CERTID) {
        this.CERTID = CERTID;
    }

    public String getDEMDTYP() {
        return DEMDTYP;
    }

    public void setDEMDTYP(String DEMDTYP) {
        this.DEMDTYP = DEMDTYP;
    }

    public String getSUBDEMDTYP() {
        return SUBDEMDTYP;
    }

    public void setSUBDEMDTYP(String SUBDEMDTYP) {
        this.SUBDEMDTYP = SUBDEMDTYP;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getBESPEAKDATE() {
        return BESPEAKDATE;
    }

    public void setBESPEAKDATE(String BESPEAKDATE) {
        this.BESPEAKDATE = BESPEAKDATE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getOPINION() {
        return OPINION;
    }

    public void setOPINION(String OPINION) {
        this.OPINION = OPINION;
    }

    public String getFINAL_AMT() {
        return FINAL_AMT;
    }

    public void setFINAL_AMT(String FINAL_AMT) {
        this.FINAL_AMT = FINAL_AMT;
    }

    public String getFINAL_DATE() {
        return FINAL_DATE;
    }

    public void setFINAL_DATE(String FINAL_DATE) {
        this.FINAL_DATE = FINAL_DATE;
    }

    public String getCPL_STATUS() {
        return CPL_STATUS;
    }

    public void setCPL_STATUS(String CPL_STATUS) {
        this.CPL_STATUS = CPL_STATUS;
    }

    public String getREMARKS() {
        return REMARKS;
    }

    public void setREMARKS(String REMARKS) {
        this.REMARKS = REMARKS;
    }

    public String getDEAL_USERID() {
        return DEAL_USERID;
    }

    public void setDEAL_USERID(String DEAL_USERID) {
        this.DEAL_USERID = DEAL_USERID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPURPOSE() {
        return PURPOSE;
    }

    public void setPURPOSE(String PURPOSE) {
        this.PURPOSE = PURPOSE;
    }
}
