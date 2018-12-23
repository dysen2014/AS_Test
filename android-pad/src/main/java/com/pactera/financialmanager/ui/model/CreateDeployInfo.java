package com.pactera.financialmanager.ui.model;


public class CreateDeployInfo {

    private String CUSTID;
    private String APPLYREASON;
    private String APPLYSTAID;
    private String OLDOWENERID;
    private String OLDBRID;//
    private String DES;//


    public String getCUSTID() {
        return CUSTID;
    }

    public void setCUSTID(String CUSTID) {
        this.CUSTID = CUSTID;
    }

    public String getAPPLYREASON() {
        return APPLYREASON;
    }

    public void setAPPLYREASON(String APPLYREASON) {
        this.APPLYREASON = APPLYREASON;
    }

    public String getAPPLYSTAID() {
        return APPLYSTAID;
    }

    public void setAPPLYSTAID(String APPLYSTAID) {
        this.APPLYSTAID = APPLYSTAID;
    }

    public String getOLDOWENERID() {
        return OLDOWENERID;
    }

    public void setOLDOWENERID(String OLDOWENERID) {
        this.OLDOWENERID = OLDOWENERID;
    }

    public String getOLDBRID() {
        return OLDBRID;
    }

    public void setOLDBRID(String OLDBRID) {
        this.OLDBRID = OLDBRID;
    }

    public String getDES() {
        return DES;
    }

    public void setDES(String DES) {
        this.DES = DES;
    }

    @Override
    public String toString() {
        return "{" +
                "CUSTID='" + CUSTID + '\'' +
                ", APPLYREASON='" + APPLYREASON + '\'' +
                ", APPLYSTAID='" + APPLYSTAID + '\'' +
                ", OLDOWENERID='" + OLDOWENERID + '\'' +
                ", OLDBRID='" + OLDBRID + '\'' +
                ", DES='" + DES + '\'' +
                '}';
    }
}