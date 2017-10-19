package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 他行存款信息查询之后返回的数据
 */
public class OtherBankMoneyEntity implements Serializable {
    private String pkid;// 主键
    private String deposit_type;// 存款类型
    private String deposit_amount;// 存款余额
    private String deposit_period;// 存款期限
    private String deposit_bank;// 存款行
    private String deposit_maturity_date;// 存款到期日
    private String remind_date;// 提醒日期
    private String createname;// 创建人
    private String createtime;// 创建时间

    public String getPkid() {
        return pkid;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    public String getDeposit_type() {
        return deposit_type;
    }

    public void setDeposit_type(String deposit_type) {
        this.deposit_type = deposit_type;
    }

    public String getDeposit_amount() {
        return deposit_amount;
    }

    public void setDeposit_amount(String deposit_amount) {
        this.deposit_amount = deposit_amount;
    }

    public String getDeposit_period() {
        return deposit_period;
    }

    public void setDeposit_period(String deposit_period) {
        this.deposit_period = deposit_period;
    }

    public String getDeposit_bank() {
        return deposit_bank;
    }

    public void setDeposit_bank(String deposit_bank) {
        this.deposit_bank = deposit_bank;
    }

    public String getDeposit_maturity_date() {
        return deposit_maturity_date;
    }

    public void setDeposit_maturity_date(String deposit_maturity_date) {
        this.deposit_maturity_date = deposit_maturity_date;
    }

    public String getRemind_date() {
        return remind_date;
    }

    public void setRemind_date(String remind_date) {
        this.remind_date = remind_date;
    }

    public String getCreatename() {
        return createname;
    }

    public void setCreatename(String createname) {
        this.createname = createname;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public OtherBankMoneyEntity(String pkid, String deposit_type, String deposit_amount, String deposit_period, String deposit_bank, String deposit_maturity_date, String remind_date, String createname, String createtime) {
        this.pkid = pkid;
        this.deposit_type = deposit_type;
        this.deposit_amount = deposit_amount;
        this.deposit_period = deposit_period;
        this.deposit_bank = deposit_bank;
        this.deposit_maturity_date = deposit_maturity_date;
        this.remind_date = remind_date;
        this.createname = createname;
        this.createtime = createtime;
    }

    public OtherBankMoneyEntity() {
    }
}
