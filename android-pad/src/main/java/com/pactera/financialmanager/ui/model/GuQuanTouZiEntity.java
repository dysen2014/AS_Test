package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 查询股权投资返回的数据保存的javabean
 */
public class GuQuanTouZiEntity implements Serializable {
    private String pkid;//主键
    private String OWNERSHIP_CERT_NO;//股权证号
    private String INVEST_ENTER_NAME;//投向企业名称
    private String INVEST_TYPE;//出资方式
    private String ORG_CODE;//组织机构代码
    private String LOAN_ACC;//贷款卡号
    private String IS_CONTROL;//是否实际控制人
    private String RATIO_STOCK;//股权比例
    private String CURRENCY;//币种
    private String INVEST_BAL;//投资金额
    private String DES;//备注

    public String getPkid() {
        return pkid;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    public String getOWNERSHIP_CERT_NO() {
        return OWNERSHIP_CERT_NO;
    }

    public void setOWNERSHIP_CERT_NO(String oWNERSHIP_CERT_NO) {
        OWNERSHIP_CERT_NO = oWNERSHIP_CERT_NO;
    }

    public String getINVEST_ENTER_NAME() {
        return INVEST_ENTER_NAME;
    }

    public void setINVEST_ENTER_NAME(String iNVEST_ENTER_NAME) {
        INVEST_ENTER_NAME = iNVEST_ENTER_NAME;
    }

    public String getINVEST_TYPE() {
        return INVEST_TYPE;
    }

    public void setINVEST_TYPE(String iNVEST_TYPE) {
        INVEST_TYPE = iNVEST_TYPE;
    }

    public String getORG_CODE() {
        return ORG_CODE;
    }

    public void setORG_CODE(String oRG_CODE) {
        ORG_CODE = oRG_CODE;
    }

    public String getLOAN_ACC() {
        return LOAN_ACC;
    }

    public void setLOAN_ACC(String lOAN_ACC) {
        LOAN_ACC = lOAN_ACC;
    }

    public String getIS_CONTROL() {
        return IS_CONTROL;
    }

    public void setIS_CONTROL(String iS_CONTROL) {
        IS_CONTROL = iS_CONTROL;
    }

    public String getRATIO_STOCK() {
        return RATIO_STOCK;
    }

    public void setRATIO_STOCK(String rATIO_STOCK) {
        RATIO_STOCK = rATIO_STOCK;
    }

    public String getCURRENCY() {
        return CURRENCY;
    }

    public void setCURRENCY(String cURRENCY) {
        CURRENCY = cURRENCY;
    }

    public String getINVEST_BAL() {
        return INVEST_BAL;
    }

    public void setINVEST_BAL(String iNVEST_BAL) {
        INVEST_BAL = iNVEST_BAL;
    }

    public String getDES() {
        return DES;
    }

    public void setDES(String dES) {
        DES = dES;
    }

    public GuQuanTouZiEntity(String pkid, String oWNERSHIP_CERT_NO,
                             String iNVEST_ENTER_NAME, String iNVEST_TYPE, String oRG_CODE,
                             String lOAN_ACC, String iS_CONTROL, String rATIO_STOCK,
                             String cURRENCY, String iNVEST_BAL, String dES) {
        super();
        this.pkid = pkid;
        OWNERSHIP_CERT_NO = oWNERSHIP_CERT_NO;
        INVEST_ENTER_NAME = iNVEST_ENTER_NAME;
        INVEST_TYPE = iNVEST_TYPE;
        ORG_CODE = oRG_CODE;
        LOAN_ACC = lOAN_ACC;
        IS_CONTROL = iS_CONTROL;
        RATIO_STOCK = rATIO_STOCK;
        CURRENCY = cURRENCY;
        INVEST_BAL = iNVEST_BAL;
        DES = dES;
    }

    public GuQuanTouZiEntity() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "GuQuanTouZiEntity [pkid=" + pkid + ", OWNERSHIP_CERT_NO="
                + OWNERSHIP_CERT_NO + ", INVEST_ENTER_NAME="
                + INVEST_ENTER_NAME + ", INVEST_TYPE=" + INVEST_TYPE
                + ", ORG_CODE=" + ORG_CODE + ", LOAN_ACC=" + LOAN_ACC
                + ", IS_CONTROL=" + IS_CONTROL + ", RATIO_STOCK=" + RATIO_STOCK
                + ", CURRENCY=" + CURRENCY + ", INVEST_BAL=" + INVEST_BAL
                + ", DES=" + DES + "]";
    }
}
