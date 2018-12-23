package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * Created by zhangfanghan on 2016/7/13.
 */
public class DemandCPZX implements Serializable {
    private String CUSTID;//客户号
    private String PKID;//主键
    private String KIND;//种类
    private String OCCUR_TIME;//发生时间
    private String DES;//内容描述

    public void setOCCUR_TIME(String OCCUR_TIME) {
        this.OCCUR_TIME = OCCUR_TIME;
    }

    public void setCUSTID(String CUSTID) {
        this.CUSTID = CUSTID;
    }

    public void setPKID(String PKID) {
        this.PKID = PKID;
    }

    public void setKIND(String KIND) {
        this.KIND = KIND;
    }

    public void setDES(String DES) {
        this.DES = DES;
    }


    public String getOCCUR_TIME() {
        return OCCUR_TIME;
    }

    public String getCUSTID() {
        return CUSTID;
    }

    public String getPKID() {
        return PKID;
    }

    public String getKIND() {
        return KIND;
    }

    public String getDES() {
        return DES;
    }


}
