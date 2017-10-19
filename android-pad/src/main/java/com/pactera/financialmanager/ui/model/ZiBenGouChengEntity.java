package com.pactera.financialmanager.ui.model;


import java.io.Serializable;

/**
 * 资本构成的数据保存的javabean
 */
public class ZiBenGouChengEntity implements Serializable {

    private String PKID;//	主键
    private String STOCK_NAME;//股东名称
    private String SHAREHOLDER_TYPE;//股东类型
    private String CERT_TYPE;//证件类型
    private String CERT_NO;//证件号码
    private String RATIO_INVEST;//投资比例
    private String IS_CONTROL;//是否实际控制人
    private String PAY_TYPE;//出资方式
    private String BAL;//金额
    private String DES;//备注
    private String CURRENCY;//币种

    public ZiBenGouChengEntity() {
    }

    public String getPKID() {
        return PKID;
    }

    public void setPKID(String PKID) {
        this.PKID = PKID;
    }

    public String getSTOCK_NAME() {
        return STOCK_NAME;
    }

    public void setSTOCK_NAME(String STOCK_NAME) {
        this.STOCK_NAME = STOCK_NAME;
    }

    public String getSHAREHOLDER_TYPE() {
        return SHAREHOLDER_TYPE;
    }

    public void setSHAREHOLDER_TYPE(String SHAREHOLDER_TYPE) {
        this.SHAREHOLDER_TYPE = SHAREHOLDER_TYPE;
    }

    public String getCERT_TYPE() {
        return CERT_TYPE;
    }

    public void setCERT_TYPE(String CERT_TYPE) {
        this.CERT_TYPE = CERT_TYPE;
    }

    public String getCERT_NO() {
        return CERT_NO;
    }

    public void setCERT_NO(String CERT_NO) {
        this.CERT_NO = CERT_NO;
    }

    public String getRATIO_INVEST() {
        return RATIO_INVEST;
    }

    public void setRATIO_INVEST(String RATIO_INVEST) {
        this.RATIO_INVEST = RATIO_INVEST;
    }

    public String getIS_CONTROL() {
        return IS_CONTROL;
    }

    public void setIS_CONTROL(String IS_CONTROL) {
        this.IS_CONTROL = IS_CONTROL;
    }

    public String getPAY_TYPE() {
        return PAY_TYPE;
    }

    public void setPAY_TYPE(String PAY_TYPE) {
        this.PAY_TYPE = PAY_TYPE;
    }

    public String getBAL() {
        return BAL;
    }

    public void setBAL(String BAL) {
        this.BAL = BAL;
    }

    public String getDES() {
        return DES;
    }

    public void setDES(String DES) {
        this.DES = DES;
    }

    public String getCURRENCY() {
        return CURRENCY;
    }

    public void setCURRENCY(String CURRENCY) {
        this.CURRENCY = CURRENCY;
    }

    @Override
    public String toString() {
        return "ZiBenGouChengEntity{" +
                "PKID='" + PKID + '\'' +
                ", STOCK_NAME='" + STOCK_NAME + '\'' +
                ", SHAREHOLDER_TYPE='" + SHAREHOLDER_TYPE + '\'' +
                ", CERT_TYPE='" + CERT_TYPE + '\'' +
                ", CERT_NO='" + CERT_NO + '\'' +
                ", RATIO_INVEST='" + RATIO_INVEST + '\'' +
                ", IS_CONTROL='" + IS_CONTROL + '\'' +
                ", PAY_TYPE='" + PAY_TYPE + '\'' +
                ", BAL='" + BAL + '\'' +
                ", DES='" + DES + '\'' +
                ", CURRENCY='" + CURRENCY + '\'' +
                '}';
    }
}
