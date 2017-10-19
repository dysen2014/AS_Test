package com.pactera.financialmanager.entity;

import java.io.Serializable;

/**
 * 其它行关联情况
 */
public class OtherBankEntity implements Serializable {

    private static final long serialVersionUID = -9164909796451821004L;

    private String pkid;// 主键
    private String RELATE_NAME;// 关联方名称
    private String RELATE_CERT_TYPE;// 关联方证件类型
    private String RELATE_CERT_NO;// 关联方证件号码
    private String RELATIONS;// 关联关系
    private String REGISTER_NAME;// 登记人
    private String REGISTER_ORG;// 登记机构
    private String REGISTER_TIME;// 登记日期
    private String F_COMPANY;// 法人代表公司
    private String SETTLE_TYPE;// 结算方式
    private String DES;// 备注
    private String UPDATE_TIME;// 更新日期

    public String getSETTLE_TYPE() {
        return SETTLE_TYPE;
    }

    public void setSETTLE_TYPE(String settle_type) {
        this.SETTLE_TYPE = settle_type;
    }

    public String getREGISTER_TIME() {
        return REGISTER_TIME;
    }

    public void setREGISTER_TIME(String rEGISTER_TIME) {
        REGISTER_TIME = rEGISTER_TIME;
    }

    public String getDES() {
        return DES;
    }

    public void setDES(String dES) {
        DES = dES;
    }

    public String getF_COMPANY() {
        return F_COMPANY;
    }

    public void setF_COMPANY(String f_COMPANY) {
        F_COMPANY = f_COMPANY;
    }

    public String getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(String uPDATE_TIME) {
        UPDATE_TIME = uPDATE_TIME;
    }

    public String getPkid() {
        return pkid;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    public String getRELATE_CERT_TYPE() {
        return RELATE_CERT_TYPE;
    }

    public void setRELATE_CERT_TYPE(String rELATE_CERT_TYPE) {
        RELATE_CERT_TYPE = rELATE_CERT_TYPE;
    }

    public String getRELATE_CERT_NO() {
        return RELATE_CERT_NO;
    }

    public void setRELATE_CERT_NO(String rELATE_CERT_NO) {
        RELATE_CERT_NO = rELATE_CERT_NO;
    }

    public String getRELATE_NAME() {
        return RELATE_NAME;
    }

    public void setRELATE_NAME(String rELATE_NAME) {
        RELATE_NAME = rELATE_NAME;
    }

    public String getRELATIONS() {
        return RELATIONS;
    }

    public void setRELATIONS(String rELATIONS) {
        RELATIONS = rELATIONS;
    }

    public String getREGISTER_NAME() {
        return REGISTER_NAME;
    }

    public void setREGISTER_NAME(String rEGISTER_NAME) {
        REGISTER_NAME = rEGISTER_NAME;
    }

    public String getREGISTER_ORG() {
        return REGISTER_ORG;
    }

    public void setREGISTER_ORG(String rEGISTER_ORG) {
        REGISTER_ORG = rEGISTER_ORG;
    }

    public OtherBankEntity(String pkid, String rELATE_CERT_TYPE,
                           String rELATE_CERT_NO, String rELATE_NAME, String rELATIONS,
                           String rEGISTER_NAME, String rEGISTER_ORG, String REGISTER_TIME,
                           String DES, String F_COMPANY, String UPDATE_TIME) {
        super();
        this.pkid = pkid;
        this.RELATE_CERT_TYPE = rELATE_CERT_TYPE;
        this.RELATE_CERT_NO = rELATE_CERT_NO;
        this.RELATE_NAME = rELATE_NAME;
        this.RELATIONS = rELATIONS;
        this.REGISTER_NAME = rEGISTER_NAME;
        this.REGISTER_ORG = rEGISTER_ORG;
        this.REGISTER_TIME = REGISTER_TIME;
        this.DES = DES;
        this.F_COMPANY = F_COMPANY;
        this.UPDATE_TIME = UPDATE_TIME;
    }

    public OtherBankEntity() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "OtherBankEntity [pkid=" + pkid + ", RELATE_CERT_TYPE="
                + RELATE_CERT_TYPE + ", RELATE_CERT_NO=" + RELATE_CERT_NO
                + ", RELATE_NAME=" + RELATE_NAME + ", RELATIONS=" + RELATIONS
                + ", REGISTER_NAME=" + REGISTER_NAME + ", REGISTER_ORG="
                + REGISTER_ORG + ", REGISTER_TIME=" + REGISTER_TIME + ", DES="
                + DES + ", F_COMPANY=" + F_COMPANY + ", UPDATE_TIME="
                + UPDATE_TIME + "]";
    }
}
