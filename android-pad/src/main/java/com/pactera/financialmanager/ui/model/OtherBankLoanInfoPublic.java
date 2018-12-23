package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 他行贷款产品  对公
 *
 * @author JAY
 */
public class OtherBankLoanInfoPublic implements Serializable {

    public String CUSTID = "";// 客户号
    public String pkid;// 主键
    public String LOAN_TYPE = "";// 贷款方式
    public String LOAN_PERIOD = "";// 贷款期限
    public String LOAN_AMOUNT = "";// 贷款金额
    public String LOAN_BANK = "";// 贷款行
    public String END_DATE = "";// 到期日期
    public String REMIND_DATE = "";// 提醒日期
    public String LOAN_RATE = "";// 贷款利率

    @Override
    public String toString() {
        return "{\"pkid\":\"" + pkid + "\",\"CUSTID\":\"" + CUSTID
                + "\",\"LOAN_TYPE\":\"" + LOAN_TYPE + "\",\"LOAN_PERIOD\":\""
                + LOAN_PERIOD + "\",\"LOAN_RATE\":\"" + LOAN_RATE
                + "\",\"LOAN_AMOUNT\":\"" + LOAN_AMOUNT + "\",\"LOAN_BANK\":\""
                + LOAN_BANK + "\",\"END_DATE\":\""
                + END_DATE + "\",\"REMIND_DATE\":\"" + REMIND_DATE + "\"}";
    }

}
