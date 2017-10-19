package com.pactera.financialmanager.ui.model;

import java.io.Serializable;

/**
 * 资产实体类
 *
 * @author Administrator
 */
public class Propertiesinfo implements Serializable {

    private static final long serialVersionUID = 5421471583555369055L;

    private String CUST_NUM; //客户数
    private String PSN_CUST_NUM;//个人客户数
    private String ENT_CUST_NUM;//公司客户数

    private String BAL;//金融资产余额
    private String PSN_BAL;//金融资产个人余额
    private String ENT_BAL;//金融资产对公余额
    private String CUR_DEP_BAL;//活期存款余额
    private String PSN_CUR_DEPS_BAL;//个人活期存款余额
    private String ENT_CUR_DEPS_BAL;//对公活期存款余额
    private String RMB_FIX_DEP_BAL;//定期存款余额
    private String PSN_FIX_DEPS_BAL;//个人定期期存款余额
    private String ENT_FIX_DEPS_BAL;//对公定期期存款余额
    private String DEPS_BAL;//存款余额
    private String PSN_DEPS_BAL;//个人存款余额
    private String ENT_DEPS_BAL;//对公存款余额
    private String LOAN_BAL;//贷款余额
    private String PSN_LOAN_BAL;//个人贷款余额
    private String ENT_LOAN_BAL;//对公贷款余额
    private String FINA_BAL;//理财余额
    private String PSN_FINA_BAL;//个人理财余额
    private String ENT_FINA_BAL;//对公理财余额
    private String TJ_DATE;//统计时间

//    private String PSN_FIX_DEPS_BAL;//管户个人定期存款余额
    private String PSN_FIX_DEPS_LDBAL;//管户个人定期存款余额比上日
    private String PSN_FIX_DEPS_LMBAL;//管户个人定期存款余额比上月
    private String PSN_FIX_DEPS_LQBAL;//管户个人定期存款余额比上季
    private String PSN_FIX_DEPS_LYBAL;//管户个人定期存款余额比上年
    //    private String ENT_FIX_DEPS_BAL;//管户对公定期存款余额
    private String ENT_FIX_DEPS_LDBAL;//管户对公定期存款余额比上日
    private String ENT_FIX_DEPS_LMBAL;//管户对公定期存款余额比上月
    private String ENT_FIX_DEPS_LQBAL;//管户对公定期存款余额比上季
    private String ENT_FIX_DEPS_LYBAL;//管户对公定期存款余额比上年

    //    private String PSN_CUR_DEPS_BAL;//管户个人活期存款余额
    private String PSN_CUR_DEPS_LDBAL;//管户个人活期存款余额比上日
    private String PSN_CUR_DEPS_LMBAL;//管户个人活期存款余额比上月
    private String PSN_CUR_DEPS_LQBAL;//管户个人活期存款余额比上季
    private String PSN_CUR_DEPS_LYBAL;//管户个人活期存款余额比上年
    //    private String ENT_CUR_DEPS_BAL;//管户对公活期存款余额
    private String ENT_CUR_DEPS_LDBAL;//管户对公活期存款余额比上日
    private String ENT_CUR_DEPS_LMBAL;//管户对公活期存款余额比上月
    private String ENT_CUR_DEPS_LQBAL;//管户对公活期存款余额比上季
    private String ENT_CUR_DEPS_LYBAL;//管户对公活期存款余额比上年

    //    private String PSN_FINA_BAL;//个人理财余额
    private String PSN_FINA_LDBAL;//个人理财余额比上日
    private String PSN_FINA_LMBAL;//个人理财余额比上月
    private String PSN_FINA_LQBAL;//个人理财余额比上季
    private String PSN_FINA_LYBAL;//个人理财余额比上年
    //    private String ENT_FINA_BAL;//对公理财余额
    private String ENT_FINA_LDBAL;//对公理财余额比上日
    private String ENT_FINA_LMBAL;//对公理财余额比上月
    private String ENT_FINA_LQBAL;//对公理财余额比上季
    private String ENT_FINA_LYBAL;//对公理财余额比上年

    //    private String PSN_BAL;//个人合计余额
    private String PSN_LDBAL;//比上日
    private String PSN_LMBAL;//比上月
    private String PSN_LQBAL;//比上季
    private String PSN_LYBAL;//比上年
    //    private String ENT_BAL;//对公合计余额
    private String ENT_LDBAL;//比上日
    private String ENT_LMBAL;//比上月
    private String ENT_LQBAL;//比上季
    private String ENT_LYBAL;//比上年
    //    private String BAL;//总计
    private String LDBAL;//比上日
    private String LMBAL;//比上月
    private String LQBAL;//比上季
    private String LYBAL;//比上年

//    private String PSN_LOAN_BAL;//管户个人贷款余额
    private String PSN_LOAN_LDBAL;//管户个人贷款余额比上日
    private String PSN_LOAN_LMBAL;//管户个人贷款余额比上月
    private String PSN_LOAN_LQBAL;//管户个人贷款余额比上季
    private String PSN_LOAN_LYBAL;//管户个人贷款余额比上年
//    private String ENT_LOAN_BAL;//管户对公贷款余额
    private String ENT_LOAN_LDBAL;//管户对公贷款余额比上日
    private String ENT_LOAN_LMBAL;//管户对公贷款余额比上月
    private String ENT_LOAN_LQBAL;//管户对公贷款余额比上季
    private String ENT_LOAN_LYBAL;//管户对公贷款余额比上年

//    private String LOAN_BAL;//贷款余额
    private String LOAN_LDBAL;//贷款余额比上日
    private String LOAN_LMBAL;//贷款余额比上月
    private String LOAN_LQBAL;//贷款余额比上季
    private String LOAN_LYBAL;//贷款余额比上年

    public String getCUST_NUM() {
        return CUST_NUM;
    }

    public void setCUST_NUM(String cUST_NUM) {
        CUST_NUM = cUST_NUM;
    }

    public String getPSN_FIX_DEPS_LDBAL() {
        return PSN_FIX_DEPS_LDBAL;
    }

    public String getPSN_LOAN_LDBAL() {
        return PSN_LOAN_LDBAL;
    }

    public void setPSN_LOAN_LDBAL(String PSN_LOAN_LDBAL) {
        this.PSN_LOAN_LDBAL = PSN_LOAN_LDBAL;
    }

    public String getPSN_LOAN_LMBAL() {
        return PSN_LOAN_LMBAL;
    }

    public void setPSN_LOAN_LMBAL(String PSN_LOAN_LMBAL) {
        this.PSN_LOAN_LMBAL = PSN_LOAN_LMBAL;
    }

    public String getPSN_LOAN_LQBAL() {
        return PSN_LOAN_LQBAL;
    }

    public void setPSN_LOAN_LQBAL(String PSN_LOAN_LQBAL) {
        this.PSN_LOAN_LQBAL = PSN_LOAN_LQBAL;
    }

    public String getPSN_LOAN_LYBAL() {
        return PSN_LOAN_LYBAL;
    }

    public void setPSN_LOAN_LYBAL(String PSN_LOAN_LYBAL) {
        this.PSN_LOAN_LYBAL = PSN_LOAN_LYBAL;
    }

    public String getENT_LOAN_LDBAL() {
        return ENT_LOAN_LDBAL;
    }

    public void setENT_LOAN_LDBAL(String ENT_LOAN_LDBAL) {
        this.ENT_LOAN_LDBAL = ENT_LOAN_LDBAL;
    }

    public String getENT_LOAN_LMBAL() {
        return ENT_LOAN_LMBAL;
    }

    public void setENT_LOAN_LMBAL(String ENT_LOAN_LMBAL) {
        this.ENT_LOAN_LMBAL = ENT_LOAN_LMBAL;
    }

    public String getENT_LOAN_LQBAL() {
        return ENT_LOAN_LQBAL;
    }

    public void setENT_LOAN_LQBAL(String ENT_LOAN_LQBAL) {
        this.ENT_LOAN_LQBAL = ENT_LOAN_LQBAL;
    }

    public String getENT_LOAN_LYBAL() {
        return ENT_LOAN_LYBAL;
    }

    public void setENT_LOAN_LYBAL(String ENT_LOAN_LYBAL) {
        this.ENT_LOAN_LYBAL = ENT_LOAN_LYBAL;
    }

    public String getLOAN_LDBAL() {
        return LOAN_LDBAL;
    }

    public void setLOAN_LDBAL(String LOAN_LDBAL) {
        this.LOAN_LDBAL = LOAN_LDBAL;
    }

    public String getLOAN_LMBAL() {
        return LOAN_LMBAL;
    }

    public void setLOAN_LMBAL(String LOAN_LMBAL) {
        this.LOAN_LMBAL = LOAN_LMBAL;
    }

    public String getLOAN_LQBAL() {
        return LOAN_LQBAL;
    }

    public void setLOAN_LQBAL(String LOAN_LQBAL) {
        this.LOAN_LQBAL = LOAN_LQBAL;
    }

    public String getLOAN_LYBAL() {
        return LOAN_LYBAL;
    }

    public void setLOAN_LYBAL(String LOAN_LYBAL) {
        this.LOAN_LYBAL = LOAN_LYBAL;
    }

    public void setPSN_FIX_DEPS_LDBAL(String PSN_FIX_DEPS_LDBAL) {
        this.PSN_FIX_DEPS_LDBAL = PSN_FIX_DEPS_LDBAL;
    }

    public String getPSN_FIX_DEPS_LMBAL() {
        return PSN_FIX_DEPS_LMBAL;
    }

    public void setPSN_FIX_DEPS_LMBAL(String PSN_FIX_DEPS_LMBAL) {
        this.PSN_FIX_DEPS_LMBAL = PSN_FIX_DEPS_LMBAL;
    }

    public String getPSN_FIX_DEPS_LQBAL() {
        return PSN_FIX_DEPS_LQBAL;
    }

    public void setPSN_FIX_DEPS_LQBAL(String PSN_FIX_DEPS_LQBAL) {
        this.PSN_FIX_DEPS_LQBAL = PSN_FIX_DEPS_LQBAL;
    }

    public String getPSN_FIX_DEPS_LYBAL() {
        return PSN_FIX_DEPS_LYBAL;
    }

    public void setPSN_FIX_DEPS_LYBAL(String PSN_FIX_DEPS_LYBAL) {
        this.PSN_FIX_DEPS_LYBAL = PSN_FIX_DEPS_LYBAL;
    }

    public String getENT_FIX_DEPS_LDBAL() {
        return ENT_FIX_DEPS_LDBAL;
    }

    public void setENT_FIX_DEPS_LDBAL(String ENT_FIX_DEPS_LDBAL) {
        this.ENT_FIX_DEPS_LDBAL = ENT_FIX_DEPS_LDBAL;
    }

    public String getENT_FIX_DEPS_LMBAL() {
        return ENT_FIX_DEPS_LMBAL;
    }

    public void setENT_FIX_DEPS_LMBAL(String ENT_FIX_DEPS_LMBAL) {
        this.ENT_FIX_DEPS_LMBAL = ENT_FIX_DEPS_LMBAL;
    }

    public String getENT_FIX_DEPS_LQBAL() {
        return ENT_FIX_DEPS_LQBAL;
    }

    public void setENT_FIX_DEPS_LQBAL(String ENT_FIX_DEPS_LQBAL) {
        this.ENT_FIX_DEPS_LQBAL = ENT_FIX_DEPS_LQBAL;
    }

    public String getENT_FIX_DEPS_LYBAL() {
        return ENT_FIX_DEPS_LYBAL;
    }

    public void setENT_FIX_DEPS_LYBAL(String ENT_FIX_DEPS_LYBAL) {
        this.ENT_FIX_DEPS_LYBAL = ENT_FIX_DEPS_LYBAL;
    }

    public String getPSN_CUR_DEPS_LDBAL() {
        return PSN_CUR_DEPS_LDBAL;
    }

    public void setPSN_CUR_DEPS_LDBAL(String PSN_CUR_DEPS_LDBAL) {
        this.PSN_CUR_DEPS_LDBAL = PSN_CUR_DEPS_LDBAL;
    }

    public String getPSN_CUR_DEPS_LMBAL() {
        return PSN_CUR_DEPS_LMBAL;
    }

    public void setPSN_CUR_DEPS_LMBAL(String PSN_CUR_DEPS_LMBAL) {
        this.PSN_CUR_DEPS_LMBAL = PSN_CUR_DEPS_LMBAL;
    }

    public String getPSN_CUR_DEPS_LQBAL() {
        return PSN_CUR_DEPS_LQBAL;
    }

    public void setPSN_CUR_DEPS_LQBAL(String PSN_CUR_DEPS_LQBAL) {
        this.PSN_CUR_DEPS_LQBAL = PSN_CUR_DEPS_LQBAL;
    }

    public String getPSN_CUR_DEPS_LYBAL() {
        return PSN_CUR_DEPS_LYBAL;
    }

    public void setPSN_CUR_DEPS_LYBAL(String PSN_CUR_DEPS_LYBAL) {
        this.PSN_CUR_DEPS_LYBAL = PSN_CUR_DEPS_LYBAL;
    }

    public String getENT_CUR_DEPS_LDBAL() {
        return ENT_CUR_DEPS_LDBAL;
    }

    public void setENT_CUR_DEPS_LDBAL(String ENT_CUR_DEPS_LDBAL) {
        this.ENT_CUR_DEPS_LDBAL = ENT_CUR_DEPS_LDBAL;
    }

    public String getENT_CUR_DEPS_LMBAL() {
        return ENT_CUR_DEPS_LMBAL;
    }

    public void setENT_CUR_DEPS_LMBAL(String ENT_CUR_DEPS_LMBAL) {
        this.ENT_CUR_DEPS_LMBAL = ENT_CUR_DEPS_LMBAL;
    }

    public String getENT_CUR_DEPS_LQBAL() {
        return ENT_CUR_DEPS_LQBAL;
    }

    public void setENT_CUR_DEPS_LQBAL(String ENT_CUR_DEPS_LQBAL) {
        this.ENT_CUR_DEPS_LQBAL = ENT_CUR_DEPS_LQBAL;
    }

    public String getENT_CUR_DEPS_LYBAL() {
        return ENT_CUR_DEPS_LYBAL;
    }

    public void setENT_CUR_DEPS_LYBAL(String ENT_CUR_DEPS_LYBAL) {
        this.ENT_CUR_DEPS_LYBAL = ENT_CUR_DEPS_LYBAL;
    }

    public String getPSN_FINA_LDBAL() {
        return PSN_FINA_LDBAL;
    }

    public void setPSN_FINA_LDBAL(String PSN_FINA_LDBAL) {
        this.PSN_FINA_LDBAL = PSN_FINA_LDBAL;
    }

    public String getPSN_FINA_LMBAL() {
        return PSN_FINA_LMBAL;
    }

    public void setPSN_FINA_LMBAL(String PSN_FINA_LMBAL) {
        this.PSN_FINA_LMBAL = PSN_FINA_LMBAL;
    }

    public String getPSN_FINA_LQBAL() {
        return PSN_FINA_LQBAL;
    }

    public void setPSN_FINA_LQBAL(String PSN_FINA_LQBAL) {
        this.PSN_FINA_LQBAL = PSN_FINA_LQBAL;
    }

    public String getPSN_FINA_LYBAL() {
        return PSN_FINA_LYBAL;
    }

    public void setPSN_FINA_LYBAL(String PSN_FINA_LYBAL) {
        this.PSN_FINA_LYBAL = PSN_FINA_LYBAL;
    }

    public String getENT_FINA_LDBAL() {
        return ENT_FINA_LDBAL;
    }

    public void setENT_FINA_LDBAL(String ENT_FINA_LDBAL) {
        this.ENT_FINA_LDBAL = ENT_FINA_LDBAL;
    }

    public String getENT_FINA_LMBAL() {
        return ENT_FINA_LMBAL;
    }

    public void setENT_FINA_LMBAL(String ENT_FINA_LMBAL) {
        this.ENT_FINA_LMBAL = ENT_FINA_LMBAL;
    }

    public String getENT_FINA_LQBAL() {
        return ENT_FINA_LQBAL;
    }

    public void setENT_FINA_LQBAL(String ENT_FINA_LQBAL) {
        this.ENT_FINA_LQBAL = ENT_FINA_LQBAL;
    }

    public String getENT_FINA_LYBAL() {
        return ENT_FINA_LYBAL;
    }

    public void setENT_FINA_LYBAL(String ENT_FINA_LYBAL) {
        this.ENT_FINA_LYBAL = ENT_FINA_LYBAL;
    }

    public String getPSN_LDBAL() {
        return PSN_LDBAL;
    }

    public void setPSN_LDBAL(String PSN_LDBAL) {
        this.PSN_LDBAL = PSN_LDBAL;
    }

    public String getPSN_LMBAL() {
        return PSN_LMBAL;
    }

    public void setPSN_LMBAL(String PSN_LMBAL) {
        this.PSN_LMBAL = PSN_LMBAL;
    }

    public String getPSN_LQBAL() {
        return PSN_LQBAL;
    }

    public void setPSN_LQBAL(String PSN_LQBAL) {
        this.PSN_LQBAL = PSN_LQBAL;
    }

    public String getPSN_LYBAL() {
        return PSN_LYBAL;
    }

    public void setPSN_LYBAL(String PSN_LYBAL) {
        this.PSN_LYBAL = PSN_LYBAL;
    }

    public String getENT_LDBAL() {
        return ENT_LDBAL;
    }

    public void setENT_LDBAL(String ENT_LDBAL) {
        this.ENT_LDBAL = ENT_LDBAL;
    }

    public String getENT_LMBAL() {
        return ENT_LMBAL;
    }

    public void setENT_LMBAL(String ENT_LMBAL) {
        this.ENT_LMBAL = ENT_LMBAL;
    }

    public String getENT_LQBAL() {
        return ENT_LQBAL;
    }

    public void setENT_LQBAL(String ENT_LQBAL) {
        this.ENT_LQBAL = ENT_LQBAL;
    }

    public String getENT_LYBAL() {
        return ENT_LYBAL;
    }

    public void setENT_LYBAL(String ENT_LYBAL) {
        this.ENT_LYBAL = ENT_LYBAL;
    }

    public String getLDBAL() {
        return LDBAL;
    }

    public void setLDBAL(String LDBAL) {
        this.LDBAL = LDBAL;
    }

    public String getLMBAL() {
        return LMBAL;
    }

    public void setLMBAL(String LMBAL) {
        this.LMBAL = LMBAL;
    }

    public String getLQBAL() {
        return LQBAL;
    }

    public void setLQBAL(String LQBAL) {
        this.LQBAL = LQBAL;
    }

    public String getLYBAL() {
        return LYBAL;
    }

    public void setLYBAL(String LYBAL) {
        this.LYBAL = LYBAL;
    }

    public String getENT_CUST_NUM() {
        return ENT_CUST_NUM;
    }

    public void setENT_CUST_NUM(String eNT_CUST_NUM) {
        ENT_CUST_NUM = eNT_CUST_NUM;
    }

    public String getPSN_CUST_NUM() {
        return PSN_CUST_NUM;
    }

    public void setPSN_CUST_NUM(String pSN_CUST_NUM) {
        PSN_CUST_NUM = pSN_CUST_NUM;
    }

    public String getBAL() {
        return BAL;
    }

    public void setBAL(String bAL) {
        BAL = bAL;
    }

    public String getPSN_BAL() {
        return PSN_BAL;
    }

    public void setPSN_BAL(String pSN_BAL) {
        PSN_BAL = pSN_BAL;
    }

    public String getENT_BAL() {
        return ENT_BAL;
    }

    public void setENT_BAL(String eNT_BAL) {
        ENT_BAL = eNT_BAL;
    }

    public String getCUR_DEP_BAL() {
        return CUR_DEP_BAL;
    }

    public void setCUR_DEP_BAL(String cUR_DEP_BAL) {
        CUR_DEP_BAL = cUR_DEP_BAL;
    }

    public String getPSN_CUR_DEPS_BAL() {
        return PSN_CUR_DEPS_BAL;
    }

    public void setPSN_CUR_DEPS_BAL(String pSN_CUR_DEPS_BAL) {
        PSN_CUR_DEPS_BAL = pSN_CUR_DEPS_BAL;
    }

    public String getENT_CUR_DEPS_BAL() {
        return ENT_CUR_DEPS_BAL;
    }

    public void setENT_CUR_DEPS_BAL(String eNT_CUR_DEPS_BAL) {
        ENT_CUR_DEPS_BAL = eNT_CUR_DEPS_BAL;
    }

    public String getRMB_FIX_DEP_BAL() {
        return RMB_FIX_DEP_BAL;
    }

    public void setRMB_FIX_DEP_BAL(String rMB_FIX_DEP_BAL) {
        RMB_FIX_DEP_BAL = rMB_FIX_DEP_BAL;
    }

    public String getPSN_FIX_DEPS_BAL() {
        return PSN_FIX_DEPS_BAL;
    }

    public void setPSN_FIX_DEPS_BAL(String pSN_FIX_DEPS_BAL) {
        PSN_FIX_DEPS_BAL = pSN_FIX_DEPS_BAL;
    }

    public String getENT_FIX_DEPS_BAL() {
        return ENT_FIX_DEPS_BAL;
    }

    public void setENT_FIX_DEPS_BAL(String eNT_FIX_DEPS_BAL) {
        ENT_FIX_DEPS_BAL = eNT_FIX_DEPS_BAL;
    }

    public String getDEPS_BAL() {
        return DEPS_BAL;
    }

    public void setDEPS_BAL(String dEPS_BAL) {
        DEPS_BAL = dEPS_BAL;
    }

    public String getPSN_DEPS_BAL() {
        return PSN_DEPS_BAL;
    }

    public void setPSN_DEPS_BAL(String pSN_DEPS_BAL) {
        PSN_DEPS_BAL = pSN_DEPS_BAL;
    }

    public String getENT_DEPS_BAL() {
        return ENT_DEPS_BAL;
    }

    public void setENT_DEPS_BAL(String eNT_DEPS_BAL) {
        ENT_DEPS_BAL = eNT_DEPS_BAL;
    }

    public String getLOAN_BAL() {
        return LOAN_BAL;
    }

    public void setLOAN_BAL(String lOAN_BAL) {
        LOAN_BAL = lOAN_BAL;
    }

    public String getPSN_LOAN_BAL() {
        return PSN_LOAN_BAL;
    }

    public void setPSN_LOAN_BAL(String pSN_LOAN_BAL) {
        PSN_LOAN_BAL = pSN_LOAN_BAL;
    }

    public String getENT_LOAN_BAL() {
        return ENT_LOAN_BAL;
    }

    public void setENT_LOAN_BAL(String eNT_LOAN_BAL) {
        ENT_LOAN_BAL = eNT_LOAN_BAL;
    }

    public String getFINA_BAL() {
        return FINA_BAL;
    }

    public void setFINA_BAL(String fINA_BAL) {
        FINA_BAL = fINA_BAL;
    }

    public String getENT_FINA_BAL() {
        return ENT_FINA_BAL;
    }

    public void setENT_FINA_BAL(String eNT_FINA_BAL) {
        ENT_FINA_BAL = eNT_FINA_BAL;
    }

    public String getPSN_FINA_BAL() {
        return PSN_FINA_BAL;
    }

    public void setPSN_FINA_BAL(String pSN_FINA_BAL) {
        PSN_FINA_BAL = pSN_FINA_BAL;
    }

    public String getTJ_DATE() {
        return TJ_DATE;
    }

    public void setTJ_DATE(String tJ_DATE) {
        TJ_DATE = tJ_DATE;
    }

}
