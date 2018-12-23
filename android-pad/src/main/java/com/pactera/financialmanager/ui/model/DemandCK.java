package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 存款需求
 * Created by Administrator on 2015/12/4.
 */
public class DemandCK implements Serializable{

    private String PKID; //表主键

    private String DEPOSIT_AMOUNT;//金额

    private String OCCUR_TIME;//预约时间

    private String STATUS;//状态

    private String DES;//内容

//    private String REMIND_TIME;//提醒时间

    private String RECORD_USER;//记录人

    private String RECORD_TIME;//记录时间

    public String getPKID() {
        return PKID;
    }

    public void setPKID(String PKID) {
        this.PKID = PKID;
    }

    public String getDEPOSIT_AMOUNT() {
        return DEPOSIT_AMOUNT;
    }

    public void setDEPOSIT_AMOUNT(String DEPOSIT_AMOUNT) {
        this.DEPOSIT_AMOUNT = DEPOSIT_AMOUNT;
    }

    public String getOCCUR_TIME() {
        return OCCUR_TIME;
    }

    public void setOCCUR_TIME(String OCCUR_TIME) {
        this.OCCUR_TIME = OCCUR_TIME;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getDES() {
        return DES;
    }

    public void setDES(String DES) {
        this.DES = DES;
    }

//    public String getREMIND_TIME() {
//        return REMIND_TIME;
//    }
//
//    public void setREMIND_TIME(String REMIND_TIME) {
//        this.REMIND_TIME = REMIND_TIME;
//    }

    public String getRECORD_USER() {
        return RECORD_USER;
    }

    public void setRECORD_USER(String RECORD_USER) {
        this.RECORD_USER = RECORD_USER;
    }

    public String getRECORD_TIME() {
        return RECORD_TIME;
    }

    public void setRECORD_TIME(String RECORD_TIME) {
        this.RECORD_TIME = RECORD_TIME;
    }

}
