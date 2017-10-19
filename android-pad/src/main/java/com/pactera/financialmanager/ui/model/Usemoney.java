package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 账户余额实体类
 *
 * @author Administrator
 */
public class Usemoney implements Serializable {

    private static final long serialVersionUID = 769201627701945364L;

    private String CST_ID; // 客户号

    private String CST_NAME; // 客户名称

    private String REPAYMENT_ACC; // 还款账号

    private String AMOUNT_BAL; // 账户金额

    private String LOAN_ACC; // 贷款账号

    private String SHOULD_BAL; // 应还金额

    private String BRID; // 客户所在机构

    private String SHOULD_ACCRUAL; // 应还利息

    private String LOAN_BAL; // 贷款金额

    private String BACKDATE; // 还款日


    @Override
    public String toString() {
        return "Usemoney{" +
                "CST_ID='" + CST_ID + '\'' +
                ", CST_NAME='" + CST_NAME + '\'' +
                ", REPAYMENT_ACC='" + REPAYMENT_ACC + '\'' +
                ", AMOUNT_BAL='" + AMOUNT_BAL + '\'' +
                ", LOAN_ACC='" + LOAN_ACC + '\'' +
                ", SHOULD_BAL='" + SHOULD_BAL + '\'' +
                ", BRID='" + BRID + '\'' +
                ", SHOULD_ACCRUAL='" + SHOULD_ACCRUAL + '\'' +
                ", LOAN_BAL='" + LOAN_BAL + '\'' +
                ", BACKDATE='" + BACKDATE + '\'' +
                '}';
    }

    public Usemoney(String CST_ID, String CST_NAME, String REPAYMENT_ACC, String AMOUNT_BAL, String LOAN_ACC, String SHOULD_BAL, String BRID, String SHOULD_ACCRUAL, String LOAN_BAL, String BACKDATE) {
        this.CST_ID = CST_ID;
        this.CST_NAME = CST_NAME;
        this.REPAYMENT_ACC = REPAYMENT_ACC;
        this.AMOUNT_BAL = AMOUNT_BAL;
        this.LOAN_ACC = LOAN_ACC;
        this.SHOULD_BAL = SHOULD_BAL;
        this.BRID = BRID;
        this.SHOULD_ACCRUAL = SHOULD_ACCRUAL;
        this.LOAN_BAL = LOAN_BAL;
        this.BACKDATE = BACKDATE;
    }


    public Usemoney() {
        super();
    }

    public String getCST_ID() {
        return CST_ID;
    }

    public void setCST_ID(String CST_ID) {
        this.CST_ID = CST_ID;
    }

    public String getCST_NAME() {
        return CST_NAME;
    }

    public void setCST_NAME(String CST_NAME) {
        this.CST_NAME = CST_NAME;
    }

    public String getREPAYMENT_ACC() {
        return REPAYMENT_ACC;
    }

    public void setREPAYMENT_ACC(String REPAYMENT_ACC) {
        this.REPAYMENT_ACC = REPAYMENT_ACC;
    }

    public String getAMOUNT_BAL() {
        return AMOUNT_BAL;
    }

    public void setAMOUNT_BAL(String AMOUNT_BAL) {
        this.AMOUNT_BAL = AMOUNT_BAL;
    }

    public String getLOAN_ACC() {
        return LOAN_ACC;
    }

    public void setLOAN_ACC(String LOAN_ACC) {
        this.LOAN_ACC = LOAN_ACC;
    }

    public String getSHOULD_BAL() {
        return SHOULD_BAL;
    }

    public void setSHOULD_BAL(String SHOULD_BAL) {
        this.SHOULD_BAL = SHOULD_BAL;
    }

    public String getBRID() {
        return BRID;
    }

    public void setBRID(String BRID) {
        this.BRID = BRID;
    }

    public String getSHOULD_ACCRUAL() {
        return SHOULD_ACCRUAL;
    }

    public void setSHOULD_ACCRUAL(String SHOULD_ACCRUAL) {
        this.SHOULD_ACCRUAL = SHOULD_ACCRUAL;
    }

    public String getLOAN_BAL() {
        return LOAN_BAL;
    }

    public void setLOAN_BAL(String LOAN_BAL) {
        this.LOAN_BAL = LOAN_BAL;
    }

    public String getBACKDATE() {
        return BACKDATE;
    }

    public void setBACKDATE(String BACKDATE) {
        this.BACKDATE = BACKDATE;
    }
}
