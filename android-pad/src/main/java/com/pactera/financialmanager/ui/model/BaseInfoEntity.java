package com.pactera.financialmanager.ui.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 客户基本信息返回的数据
 */
public class BaseInfoEntity implements Serializable {
    private String custID;//客户ID
    private String ORG_SCL_TP_ID;//企业规模
    private String CUST_TYPE;//客户类型
    private String COMPANY_PEROPERTY;//企业性质
    private String INDUSTRY_BIG;//行业大类
    private String INDUSTRY_DETAIL;//行业细类
    private String LGL_PSN_NM;//法人代表姓名
    private String LGL_PSN_ID_TP_ID;//法人代表证件
    private String CON_CHN_NM;//实际控制人姓名
    private String BUSI_ADDR;//办公地址
    private String OPER_SCOPE;//经营范围
    private String OPER_PRD;//经营产品
    private String BUSINESS_CONDITION;//"经营状况 01正常经营	02经营困难 03关停倒闭04其他"
    private String MANUFACT_ADDR;//生产地址
    private String ENT_EMPLOY;//企业员工
    private String RGS_CAPITAL;//注册资本
    private String RGST_ADR_STR;//注册地址
    private String LN_CRD_NO;//贷款卡号
    private String S_CODE;//中征码
    private String PDIN_CPTL_CCY_ID;//国税登记证号码
    private String ESTB_CCY_ID;//地税登记证号码
    private String BSC_AC_OPN_BNK;//基本账户开户行
    private String ORG_CREDIT;//机构信用代码证(人行专用)
    private String BUSINESS_LICENCE;//营业执照
    private String ORG_CODE_CERT;//组织结构代码证

    private String LGL_PSN_CON_PHONE;//法人代表联系电话
    private String EMPE_NBR;//企业人数
    private String YEAR_SALE;//企业年销售量
    private String CONTACTNAME ;// 联系人姓名
    private String LGL_PSN_NO;//法人代表身份证号码
    private String PHONENUM;//联系人电话
    private String RGST_AREA ;//  注册面积
    public String IS_REL_FARM  ;//  是否涉农
    public String IS_RESTRIC_ENT  ;//  是否限制性企业
    private String BSC_AC_AR_ID    ;//  基本账户账号
    private String BSC_AC_OPN_DT   ;// 基本账户开户日期
    private String SPECIAL_ID_NAME  ;// 其他证件名称
    private String SPECIAL_ID_CODE ;//  其他证件号码
    private String RGST_AREA_TRUE  ;//  实际经营面积
    public String IS_PATENT   ;//  是否有专利
    public String STK_AVLB_FLAG ;//   是否上市公司
    private String STOCK_CODE  ;//  股票代码
    private String LIST_EXCHANGE;// 上市地
    private String RGST_ADR_TRUE_STR;//实际经营地址
    public String STOCKHOLDER;//是否股东
    private String RECORD_DATE ;// 登记日期
    private String EXPIRED_DT ;// 到期日期

    @JSONField(name = "LGL_PSN_NO")
    public String getLGL_PSN_NO() {return LGL_PSN_NO;}
    @JSONField(name = "LGL_PSN_NO")
    public void setLGL_PSN_NO(String lgl_psn_no) {this.LGL_PSN_NO = lgl_psn_no;}

    //	public BaseInfoEntity(String custID, String oRG_SCL_TP_ID,
//			String cUST_TYPE, String cOMPANY_PEROPERTY, String iNDUSTRY_BIG,
//			String iNDUSTRY_DETAIL, String lGL_PSN_NM, String lGL_PSN_ID_TP_ID,
//			String cON_CHN_NM, String bUSI_ADDR, String oPER_SCOPE,
//			String oPER_PRD, String bUSINESS_CONDITION, String mANUFACT_ADDR,
//			String eNT_EMPLOY, String rGS_CAPITAL, String rGST_ADR_STR,
//			String lN_CRD_NO, String s_CODE, String pDIN_CPTL_CCY_ID,
//			String eSTB_CCY_ID, String sPECIAL_ID_CODE, String oRG_CREDIT,
//			String bUSINESS_LICENCE, String oRG_CODE_CERT) {
//		super();
//		this.custID = custID;
//		ORG_SCL_TP_ID = oRG_SCL_TP_ID;
//		CUST_TYPE = cUST_TYPE;
//		COMPANY_PEROPERTY = cOMPANY_PEROPERTY;
//		INDUSTRY_BIG = iNDUSTRY_BIG;
//		INDUSTRY_DETAIL = iNDUSTRY_DETAIL;
//		LGL_PSN_NM = lGL_PSN_NM;
//		LGL_PSN_ID_TP_ID = lGL_PSN_ID_TP_ID;
//		CON_CHN_NM = cON_CHN_NM;
//		BUSI_ADDR = bUSI_ADDR;
//		OPER_SCOPE = oPER_SCOPE;
//		OPER_PRD = oPER_PRD;
//		BUSINESS_CONDITION = bUSINESS_CONDITION;
//		MANUFACT_ADDR = mANUFACT_ADDR;
//		ENT_EMPLOY = eNT_EMPLOY;
//		RGS_CAPITAL = rGS_CAPITAL;
//		RGST_ADR_STR = rGST_ADR_STR;
//		LN_CRD_NO = lN_CRD_NO;
//		S_CODE = s_CODE;
//		PDIN_CPTL_CCY_ID = pDIN_CPTL_CCY_ID;
//		ESTB_CCY_ID = eSTB_CCY_ID;
//		BSC_AC_OPN_BNK = sPECIAL_ID_CODE;
//		ORG_CREDIT = oRG_CREDIT;
//		BUSINESS_LICENCE = bUSINESS_LICENCE;
//		ORG_CODE_CERT = oRG_CODE_CERT;
//	}
    public BaseInfoEntity() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    @JSONField(name = "CONTACTNAME")
    public String getContactname() {return CONTACTNAME;}

    @JSONField(name = "CONTACTNAME")
    public void setContactname(String contactname) {this.CONTACTNAME = contactname;
    }

    @JSONField(name = "ORG_SCL_TP_ID")
    public String getORG_SCL_TP_ID() {
        return ORG_SCL_TP_ID;
    }
    @JSONField(name = "ORG_SCL_TP_ID")
    public void setORG_SCL_TP_ID(String oRG_SCL_TP_ID) {
        ORG_SCL_TP_ID = oRG_SCL_TP_ID;
    }
    @JSONField(name = "PHONENUM")
    public String getPHONENUM() {return PHONENUM;}
    @JSONField(name = "PHONENUM")
    public void setPHONENUM(String phonenum) {this.PHONENUM = phonenum;
    }
    @JSONField(name = "CUST_TYPE")
    public String getCUST_TYPE() {
        return CUST_TYPE;
    }

    @JSONField(name = "CUST_TYPE")
    public void setCUST_TYPE(String cUST_TYPE) {
        CUST_TYPE = cUST_TYPE;
    }
    @JSONField(name = "RECORD_DATE")
    public String getRECORD_DATE() {return RECORD_DATE;}
    @JSONField(name = "RECORD_DATE")
    public void setRECORD_DATE(String record_date) {this.RECORD_DATE = record_date;
    }

    @JSONField(name = "COMPANY_PEROPERTY")
    public String getCOMPANY_PEROPERTY() {
        return COMPANY_PEROPERTY;
    }
    @JSONField(name = "COMPANY_PEROPERTY")
    public void setCOMPANY_PEROPERTY(String company_peroperty) {
        COMPANY_PEROPERTY = company_peroperty;
    }

    @JSONField(name = "RGST_AREA")
    public String getRGST_AREA() {
        return RGST_AREA;
    }
    @JSONField(name = "RGST_AREA")
    public void setRGST_AREA(String rgst_area) {
        RGST_AREA = rgst_area;
    }

    @JSONField(name = "RGST_ADR_TRUE_STR")
    public String getRGST_ADR_TRUE_STR() {
        return RGST_ADR_TRUE_STR;
    }
    @JSONField(name = "RGST_ADR_TRUE_STR")
    public void setRGST_ADR_TRUE_STR(String rgst_adr_true_str) {
        RGST_ADR_TRUE_STR = rgst_adr_true_str;
    }
    @JSONField(name = "EXPIRED_DT")
    public String getEXPIRED_DT() {
        return EXPIRED_DT;
    }
    @JSONField(name = "EXPIRED_DT")
    public void setEXPIRED_DT(String expired_dt) {
        EXPIRED_DT = expired_dt;
    }


    @JSONField(name = "INDUSTRY_BIG")
    public String getINDUSTRY_BIG() {
        return INDUSTRY_BIG;
    }
    @JSONField(name = "INDUSTRY_BIG")
    public void setINDUSTRY_BIG(String iNDUSTRY_BIG) {
        INDUSTRY_BIG = iNDUSTRY_BIG;
    }

    @JSONField(name = "INDUSTRY_DETAIL")
    public String getINDUSTRY_DETAIL() {
        return INDUSTRY_DETAIL;
    }

    @JSONField(name = "INDUSTRY_DETAIL")
    public void setINDUSTRY_DETAIL(String iNDUSTRY_DETAIL) {
        INDUSTRY_DETAIL = iNDUSTRY_DETAIL;
    }

    @JSONField(name = "LGL_PSN_NM")
    public String getLGL_PSN_NM() {
        return LGL_PSN_NM;
    }

    @JSONField(name = "LGL_PSN_NM")
    public void setLGL_PSN_NM(String lGL_PSN_NM) {
        LGL_PSN_NM = lGL_PSN_NM;
    }

    @JSONField(name = "LGL_PSN_ID_TP_ID")
    public String getLGL_PSN_ID_TP_ID() {
        return LGL_PSN_ID_TP_ID;
    }

    @JSONField(name = "LGL_PSN_ID_TP_ID")
    public void setLGL_PSN_ID_TP_ID(String lGL_PSN_ID_TP_ID) {
        LGL_PSN_ID_TP_ID = lGL_PSN_ID_TP_ID;
    }

    @JSONField(name = "CON_CHN_NM")
    public String getCON_CHN_NM() {
        return CON_CHN_NM;
    }

    @JSONField(name = "CON_CHN_NM")
    public void setCON_CHN_NM(String cON_CHN_NM) {
        CON_CHN_NM = cON_CHN_NM;
    }

    @JSONField(name = "BUSI_ADDR")
    public String getBUSI_ADDR() {
        return BUSI_ADDR;
    }

    @JSONField(name = "BUSI_ADDR")
    public void setBUSI_ADDR(String bUSI_ADDR) {
        BUSI_ADDR = bUSI_ADDR;
    }

    @JSONField(name = "OPER_SCOPE")
    public String getOPER_SCOPE() {
        return OPER_SCOPE;
    }

    @JSONField(name = "OPER_SCOPE")
    public void setOPER_SCOPE(String oPER_SCOPE) {
        OPER_SCOPE = oPER_SCOPE;
    }

    @JSONField(name = "OPER_PRD")
    public String getOPER_PRD() {
        return OPER_PRD;
    }

    @JSONField(name = "OPER_PRD")
    public void setOPER_PRD(String oPER_PRD) {
        OPER_PRD = oPER_PRD;
    }

    @JSONField(name = "BUSINESS_CONDITION")
    public String getBUSINESS_CONDITION() {
        return BUSINESS_CONDITION;
    }

    @JSONField(name = "BUSINESS_CONDITION")
    public void setBUSINESS_CONDITION(String bUSINESS_CONDITION) {
        BUSINESS_CONDITION = bUSINESS_CONDITION;
    }

    @JSONField(name = "MANUFACT_ADDR")
    public String getMANUFACT_ADDR() {
        return MANUFACT_ADDR;
    }

    @JSONField(name = "MANUFACT_ADDR")
    public void setMANUFACT_ADDR(String mANUFACT_ADDR) {
        MANUFACT_ADDR = mANUFACT_ADDR;
    }

    @JSONField(name = "ENT_EMPLOY")
    public String getENT_EMPLOY() {
        return ENT_EMPLOY;
    }

    @JSONField(name = "ENT_EMPLOY")
    public void setENT_EMPLOY(String eNT_EMPLOY) {
        ENT_EMPLOY = eNT_EMPLOY;
    }

    @JSONField(name = "RGS_CAPITAL")
    public String getRGS_CAPITAL() {
        return RGS_CAPITAL;
    }

    @JSONField(name = "RGS_CAPITAL")
    public void setRGS_CAPITAL(String rGS_CAPITAL) {
        RGS_CAPITAL = rGS_CAPITAL;
    }

    @JSONField(name = "STOCKHOLDER")
    public String getSTOCKHOLDER() {
        return STOCKHOLDER;
    }
    @JSONField(name = "STOCKHOLDER")
    public void setSTOCKHOLDER(String stockholder) {
        STOCKHOLDER = stockholder;
    }

    @JSONField(name = "RGST_ADR_STR")
    public String getRGST_ADR_STR() {
        return RGST_ADR_STR;
    }

    @JSONField(name = "RGST_ADR_STR")
    public void setRGST_ADR_STR(String rGST_ADR_STR) {
        RGST_ADR_STR = rGST_ADR_STR;
    }

    @JSONField(name = "LN_CRD_NO")
    public String getLN_CRD_NO() {
        return LN_CRD_NO;
    }

    @JSONField(name = "LN_CRD_NO")
    public void setLN_CRD_NO(String lN_CRD_NO) {
        LN_CRD_NO = lN_CRD_NO;
    }

    @JSONField(name = "S_CODE")
    public String getS_CODE() {
        return S_CODE;
    }

    @JSONField(name = "S_CODE")
    public void setS_CODE(String s_CODE) {
        S_CODE = s_CODE;
    }

    @JSONField(name = "PDIN_CPTL_CCY_ID")
    public String getPDIN_CPTL_CCY_ID() {
        return PDIN_CPTL_CCY_ID;
    }

    @JSONField(name = "PDIN_CPTL_CCY_ID")
    public void setPDIN_CPTL_CCY_ID(String pDIN_CPTL_CCY_ID) {
        PDIN_CPTL_CCY_ID = pDIN_CPTL_CCY_ID;
    }

    @JSONField(name = "ESTB_CCY_ID")
    public String getESTB_CCY_ID() {
        return ESTB_CCY_ID;
    }

    @JSONField(name = "ESTB_CCY_ID")
    public void setESTB_CCY_ID(String eSTB_CCY_ID) {
        ESTB_CCY_ID = eSTB_CCY_ID;
    }

    @JSONField(name = "BSC_AC_OPN_BNK")
    public String getBSC_AC_OPN_BNK() {
        return BSC_AC_OPN_BNK;
    }

    @JSONField(name = "BSC_AC_OPN_BNK")
    public void setBSC_AC_OPN_BNK(String sPECIAL_ID_CODE) {
        BSC_AC_OPN_BNK = sPECIAL_ID_CODE;
    }

    @JSONField(name = "ORG_CREDIT")
    public String getORG_CREDIT() {
        return ORG_CREDIT;
    }

    @JSONField(name = "ORG_CREDIT")
    public void setORG_CREDIT(String oRG_CREDIT) {
        ORG_CREDIT = oRG_CREDIT;
    }

    @JSONField(name = "BUSINESS_LICENCE")
    public String getBUSINESS_LICENCE() {
        return BUSINESS_LICENCE;
    }

    @JSONField(name = "BUSINESS_LICENCE")
    public void setBUSINESS_LICENCE(String bUSINESS_LICENCE) {
        BUSINESS_LICENCE = bUSINESS_LICENCE;
    }

    @JSONField(name = "ORG_CODE_CERT")
    public String getORG_CODE_CERT() {
        return ORG_CODE_CERT;
    }

    @JSONField(name = "ORG_CODE_CERT")
    public void setORG_CODE_CERT(String oRG_CODE_CERT) {
        ORG_CODE_CERT = oRG_CODE_CERT;
    }

  @JSONField(name = "EMPE_NBR")
    public String getEMPE_NBR() {
        return EMPE_NBR;
    }

    @JSONField(name = "EMPE_NBR")
    public void setEMPE_NBR(String eMPE_NBR) {
        EMPE_NBR = eMPE_NBR;
    }


    @JSONField(name = "YEAR_SALE")
    public String getYEAR_SALE() {
        return YEAR_SALE;
    }
    @JSONField(name = "YEAR_SALE")
    public void setYEAR_SALE(String yEAR_SALE) {
        YEAR_SALE = yEAR_SALE;
    }

    @JSONField(name = "SPECIAL_ID_NAME")
    public String getSPECIAL_ID_NAME() {
        return SPECIAL_ID_NAME;
    }
    @JSONField(name = "SPECIAL_ID_NAME")
    public void setSPECIAL_ID_NAME(String special_id_name) {
        SPECIAL_ID_NAME = special_id_name;
    }
    @JSONField(name = "SPECIAL_ID_CODE")
    public String getSPECIAL_ID_CODE() {
        return SPECIAL_ID_CODE;
    }
    @JSONField(name = "SPECIAL_ID_CODE")
    public void setSPECIAL_ID_CODE(String special_id_code) {
        SPECIAL_ID_CODE = special_id_code;
    }

    @JSONField(name = "BSC_AC_OPN_DT")
    public String getBSC_AC_OPN_DT() {
        return BSC_AC_OPN_DT;
    }
    @JSONField(name = "BSC_AC_OPN_DT")
    public void setBSC_AC_OPN_DT(String bsc_ac_opn_dt) {
        BSC_AC_OPN_DT = bsc_ac_opn_dt;
    }


    @JSONField(name = "RGST_AREA_TRUE")
    public String getRGST_AREA_TRUE() {
        return RGST_AREA_TRUE;
    }
    @JSONField(name = "RGST_AREA_TRUE")
    public void setRGST_AREA_TRUE(String rgst_area_true) {
        RGST_AREA_TRUE =rgst_area_true;
    }
    @JSONField(name = "IS_PATENT")
    public String getIS_PATENT() {
        return IS_PATENT;
    }
    @JSONField(name = "IS_PATENT")
    public void setIS_PATENT(String is_patent) {
        IS_PATENT = is_patent;
    }
    @JSONField(name = "STK_AVLB_FLAG")
    public String getSTK_AVLB_FLAG() {
        return STK_AVLB_FLAG;
    }
    @JSONField(name = "STK_AVLB_FLAG")
    public void setSTK_AVLB_FLAG(String stk_avlb_flag) {
        STK_AVLB_FLAG =stk_avlb_flag;
    }
    @JSONField(name = "STOCK_CODE")
    public String getSTOCK_CODE() {
        return STOCK_CODE;
    }
    @JSONField(name = "STOCK_CODE")
    public void setSTOCK_CODE(String stock_code) {
        STOCK_CODE =stock_code;
    }
    @JSONField(name = "LIST_EXCHANGE")
    public String getLIST_EXCHANGE() {
        return LIST_EXCHANGE;
    }
    @JSONField(name = "LIST_EXCHANGE")
    public void setLIST_EXCHANGE(String list_exchange) {
        LIST_EXCHANGE =list_exchange;
    }
    @JSONField(name = "IS_RESTRIC_ENT")
    public String getIS_RESTRIC_ENT() {
        return IS_RESTRIC_ENT;
    }
    @JSONField(name = "IS_RESTRIC_ENT")
    public void setIS_RESTRIC_ENT(String is_restric_ent) {IS_RESTRIC_ENT =is_restric_ent;}


  @JSONField(name = "LGL_PSN_CON_PHONE")
    public String getLGL_PSN_CON_PHONE() {
        return LGL_PSN_CON_PHONE;
    }
    @JSONField(name = "LGL_PSN_CON_PHONE")
    public void setLGL_PSN_CON_PHONE(String lGL_PSN_CON_PHONE) {
        LGL_PSN_CON_PHONE = lGL_PSN_CON_PHONE;
    }

    @JSONField(name = "IS_REL_FARM")
    public String getIS_REL_FARM() {
        return IS_REL_FARM;
    }
    @JSONField(name = "LGL_PSN_CON_PHONE")
    public void setIS_REL_FARM(String is_rel_farm) {
        IS_REL_FARM = is_rel_farm;
    }

    @JSONField(name = "BSC_AC_AR_ID")
    public String getBSC_AC_AR_ID() {
        return BSC_AC_AR_ID;
    }
    @JSONField(name = "BSC_AC_AR_ID")
    public void setBSC_AC_AR_ID(String bsc_ac_ar_id) {BSC_AC_AR_ID = bsc_ac_ar_id;
    }
    @Override
    public String toString() {
        String strResult = "BaseInfoEntity [custID=" + custID
                + ", ORG_SCL_TP_ID="+ ORG_SCL_TP_ID  + ", IS_REL_FARM="+ IS_REL_FARM   + ", EXPIRED_DT="+ EXPIRED_DT   + ", RECORD_DATE="+ RECORD_DATE
                + ", CUST_TYPE=" + CUST_TYPE + ", STK_AVLB_FLAG=" + STK_AVLB_FLAG   + ", STOCK_CODE=" + STOCK_CODE   + ", LIST_EXCHANGE=" + LIST_EXCHANGE
                +", CONTACTNAME=" + CONTACTNAME   +", RGST_AREA_TRUE=" + RGST_AREA_TRUE  + ", IS_PATENT=" + IS_PATENT
                + ", COMPANY_PEROPERTY=" + COMPANY_PEROPERTY+ ",PHONENUM=" + RGST_AREA+ ", =" + RGST_AREA
                + ", INDUSTRY_BIG=" + INDUSTRY_BIG   + ", RGST_ADR_TRUE_STR=" + RGST_ADR_TRUE_STR
                + ", INDUSTRY_DETAIL="+ INDUSTRY_DETAIL
                + ", SPECIAL_ID_CODE="+ SPECIAL_ID_CODE        + ", SPECIAL_ID_NAME="+ SPECIAL_ID_NAME   + ", BSC_AC_OPN_DT="+ BSC_AC_OPN_DT
                + ", LGL_PSN_NM=" + LGL_PSN_NM
                + ", LGL_PSN_ID_TP_ID=" + LGL_PSN_ID_TP_ID + ", CON_CHN_NM="
                + CON_CHN_NM + ", BUSI_ADDR=" + BUSI_ADDR + ", OPER_SCOPE="
                + OPER_SCOPE + ", OPER_PRD=" + OPER_PRD
                + ", BUSINESS_CONDITION=" + BUSINESS_CONDITION
                + ", MANUFACT_ADDR=" + MANUFACT_ADDR + ", ENT_EMPLOY="
                + ENT_EMPLOY + ", RGS_CAPITAL=" + RGS_CAPITAL
                + ", RGST_ADR_STR=" + RGST_ADR_STR + ", LN_CRD_NO=" + LN_CRD_NO
                + ", S_CODE=" + S_CODE + ", PDIN_CPTL_CCY_ID="
                + PDIN_CPTL_CCY_ID + ", ESTB_CCY_ID=" + ESTB_CCY_ID
                + ", BSC_AC_OPN_BNK=" + BSC_AC_OPN_BNK + ", ORG_CREDIT="
                + ORG_CREDIT + ", BUSINESS_LICENCE=" + BUSINESS_LICENCE
                + ", LGL_PSN_CON_PHONE=" + LGL_PSN_CON_PHONE
                + ", LGL_PSN_NO=" + LGL_PSN_NO
                + ", EMPE_NBR=" + EMPE_NBR
                + ", YEAR_SALE=" + YEAR_SALE
                + ", ORG_CODE_CERT=" + ORG_CODE_CERT + "]";
        return strResult;
    }

}
