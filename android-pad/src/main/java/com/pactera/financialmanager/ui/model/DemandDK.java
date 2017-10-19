package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 贷款需求
 * Created by Administrator on 2015/12/4.
 */
public class DemandDK implements Serializable{

    private String PKID;//主键

    private String KIND;//贷款业务种类类型(贷款方式)

    private String WARRANT;//可提供的担保方式

    private String BUSI_CATE;//业务品种

    private String AMOUNT;//金额

    private String OCCUR_TIME;//预约时间

    private String PURPOSE;//用途

    private String TURN_OVER;//周转期限

    private String STATUS;//状态

    private String DES;//内容描述

//    private String REMIND_TIME;//提醒时间

    private String RECORD_USER;//记录人

    private String RECORD_TIME;//记录时间

    public String getPKID() {
        return PKID;
    }

    public void setPKID(String PKID) {
        this.PKID = PKID;
    }

    public String getKIND() {
        return KIND;
    }

    public void setKIND(String KIND) {
        this.KIND = KIND;
    }

    public String getWARRANT() {
        return WARRANT;
    }

    public void setWARRANT(String WARRANT) {
        this.WARRANT = WARRANT;
    }

    public String getBUSI_CATE() {
        return BUSI_CATE;
    }

    public void setBUSI_CATE(String BUSI_CATE) {
        this.BUSI_CATE = BUSI_CATE;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getOCCUR_TIME() {
        return OCCUR_TIME;
    }

    public void setOCCUR_TIME(String OCCUR_TIME) {
        this.OCCUR_TIME = OCCUR_TIME;
    }

    public String getPURPOSE() {
        return PURPOSE;
    }

    public void setPURPOSE(String PURPOSE) {
        this.PURPOSE = PURPOSE;
    }

    public String getTURN_OVER() {
        return TURN_OVER;
    }

    public void setTURN_OVER(String TURN_OVER) {
        this.TURN_OVER = TURN_OVER;
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
