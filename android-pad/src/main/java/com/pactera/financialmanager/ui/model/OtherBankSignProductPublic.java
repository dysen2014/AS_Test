package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 行外签约产品  对公
 *
 * @author JAY
 */
public class OtherBankSignProductPublic implements Serializable {

    public String CUSTID = "";// 客户号
    public String pkid;// 主键
    public String SIGN_ACC = "";// 签约名称
    public String SIGN_PRD = "";// 签约产品
    public String SIGN_ORG  = "";// 签约银行
    public String SIGN_PHONE = "";// 签约手机号
    public String SIGN_TIME = "";// 签约时间

    @Override
    public String toString() {
        return "{\"PKID\":\"" + pkid + "\",\"CUSTID\":\"" + CUSTID
                + "\",\"SIGN_ACC\":\""
                + SIGN_ACC + "\",\"SIGN_PRD\":\"" + SIGN_PRD
                + "\",\"SIGN_PHONE\":\"" + SIGN_PHONE + "\",\"SIGN_ORG\":\""
                + SIGN_ORG + "\",\"SIGN_TIME\":\""
                + SIGN_TIME + "\"}";
    }

}
