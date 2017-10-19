package com.pactera.financialmanager.credit.common.bean.warn;

/**
 * Created by dysen on 2017/9/5.
 */

public class WarnCrmInfoBean {
    /**
     * 客户违约提醒
     */
    public static class DefaultInfoBean{
        public static class Daikuan {

            /**
             * RR : 1
             * LOAN_ACC : 31010000022329447
             * OVERDUE_DT : 04-20
             * OVERDUE_AMOUNT : 149723.17
             * PROD_NAME : 固率-农户经营贷款
             * CST_NAME : 李东明
             */

            private String RR;
            private String LOAN_ACC;
            private String OVERDUE_DT;
            private String OVERDUE_AMOUNT;
            private String PROD_NAME;
            private String CST_NAME;

            public String getRR() {
                return RR;
            }

            public void setRR(String RR) {
                this.RR = RR;
            }

            public String getLOAN_ACC() {
                return LOAN_ACC;
            }

            public void setLOAN_ACC(String LOAN_ACC) {
                this.LOAN_ACC = LOAN_ACC;
            }

            public String getOVERDUE_DT() {
                return OVERDUE_DT;
            }

            public void setOVERDUE_DT(String OVERDUE_DT) {
                this.OVERDUE_DT = OVERDUE_DT;
            }

            public String getOVERDUE_AMOUNT() {
                return OVERDUE_AMOUNT;
            }

            public void setOVERDUE_AMOUNT(String OVERDUE_AMOUNT) {
                this.OVERDUE_AMOUNT = OVERDUE_AMOUNT;
            }

            public String getPROD_NAME() {
                return PROD_NAME;
            }

            public void setPROD_NAME(String PROD_NAME) {
                this.PROD_NAME = PROD_NAME;
            }

            public String getCST_NAME() {
                return CST_NAME;
            }

            public void setCST_NAME(String CST_NAME) {
                this.CST_NAME = CST_NAME;
            }
        }

        public static class XinYongKa{

            private String OVERDUE_DT;
            private String OVERDUE_AMOUNT;
            private String CREDIT_CARD;
            private String CST_NAME;

            public String getOVERDUE_DT() {
                return OVERDUE_DT;
            }

            public void setOVERDUE_DT(String OVERDUE_DT) {
                this.OVERDUE_DT = OVERDUE_DT;
            }

            public String getOVERDUE_AMOUNT() {
                return OVERDUE_AMOUNT;
            }

            public void setOVERDUE_AMOUNT(String OVERDUE_AMOUNT) {
                this.OVERDUE_AMOUNT = OVERDUE_AMOUNT;
            }

            public String getCREDIT_CARD() {
                return CREDIT_CARD;
            }

            public void setCREDIT_CARD(String CREDIT_CARD) {
                this.CREDIT_CARD = CREDIT_CARD;
            }

            public String getCST_NAME() {
                return CST_NAME;
            }

            public void setCST_NAME(String CST_NAME) {
                this.CST_NAME = CST_NAME;
            }
        }
    }
    /**
     * 客户异动提醒
     */
    public static class TransactionInfoBean{
        public static class YEBZ{
            private String CST_NAME;
            private String LOAN_ACC;
            private String YHBAL;
            private String AMOUNT_BAL;
            private String BACKDATE;

            public String getCST_NAME() {
                return CST_NAME;
            }

            public void setCST_NAME(String CST_NAME) {
                this.CST_NAME = CST_NAME;
            }

            public String getLOAN_ACC() {
                return LOAN_ACC;
            }

            public void setLOAN_ACC(String LOAN_ACC) {
                this.LOAN_ACC = LOAN_ACC;
            }

            public String getYHBAL() {
                return YHBAL;
            }

            public void setYHBAL(String YHBAL) {
                this.YHBAL = YHBAL;
            }

            public String getAMOUNT_BAL() {
                return AMOUNT_BAL;
            }

            public void setAMOUNT_BAL(String AMOUNT_BAL) {
                this.AMOUNT_BAL = AMOUNT_BAL;
            }

            public String getBACKDATE() {
                return BACKDATE;
            }

            public void setBACKDATE(String BACKDATE) {
                this.BACKDATE = BACKDATE;
            }
        }
        public static class TRANSACTION{
            private String CST_NAME;
            private String LOAN_ACC;
            private String TRANS_TYPE;
            private String TRADE_DT;

            public String getCST_NAME() {
                return CST_NAME;
            }

            public void setCST_NAME(String CST_NAME) {
                this.CST_NAME = CST_NAME;
            }

            public String getLOAN_ACC() {
                return LOAN_ACC;
            }

            public void setLOAN_ACC(String LOAN_ACC) {
                this.LOAN_ACC = LOAN_ACC;
            }

            public String getTRANS_TYPE() {
                return TRANS_TYPE;
            }

            public void setTRANS_TYPE(String TRANS_TYPE) {
                this.TRANS_TYPE = TRANS_TYPE;
            }

            public String getTRADE_DT() {
                return TRADE_DT;
            }

            public void setTRADE_DT(String TRADE_DT) {
                this.TRADE_DT = TRADE_DT;
            }
        }
        public static class ZJDB{

            /**
             * RR : 1
             * LOAN_ACC : 81010000025430244
             * TRADE_TIME : 09-11 00:00:00
             * BAL : 200000
             * CST_NAME : 谭科
             */

            private String RR;
            private String LOAN_ACC;
            private String TRADE_TIME;
            private String BAL;
            private String CST_NAME;

            public String getRR() {
                return RR;
            }

            public void setRR(String RR) {
                this.RR = RR;
            }

            public String getLOAN_ACC() {
                return LOAN_ACC;
            }

            public void setLOAN_ACC(String LOAN_ACC) {
                this.LOAN_ACC = LOAN_ACC;
            }

            public String getTRADE_TIME() {
                return TRADE_TIME;
            }

            public void setTRADE_TIME(String TRADE_TIME) {
                this.TRADE_TIME = TRADE_TIME;
            }

            public String getBAL() {
                return BAL;
            }

            public void setBAL(String BAL) {
                this.BAL = BAL;
            }

            public String getCST_NAME() {
                return CST_NAME;
            }

            public void setCST_NAME(String CST_NAME) {
                this.CST_NAME = CST_NAME;
            }
        }
        public static class ZJDR{

            /**
             * RR : 1
             * BAL : 203050
             * TRADE_DT : 09-11
             * CST_NAME : 谭科
             */

            private String RR;
            private String BAL;
            private String TRADE_DT;
            private String CST_NAME;

            public String getRR() {
                return RR;
            }

            public void setRR(String RR) {
                this.RR = RR;
            }

            public String getBAL() {
                return BAL;
            }

            public void setBAL(String BAL) {
                this.BAL = BAL;
            }

            public String getTRADE_DT() {
                return TRADE_DT;
            }

            public void setTRADE_DT(String TRADE_DT) {
                this.TRADE_DT = TRADE_DT;
            }

            public String getCST_NAME() {
                return CST_NAME;
            }

            public void setCST_NAME(String CST_NAME) {
                this.CST_NAME = CST_NAME;
            }
        }
    }
    /**
     * 客户关怀提醒
     */
    public static class CareInfoBean{
        public static class KHSR{
            /**
             * RR : 1
             * BIRTHDAY : 1986-09-16
             * NL : 30
             * CUST_LEVEL : 0
             * CST_NAME : 陈绪兴
             * SR : 09-16
             */

            private String RR;
            private String BIRTHDAY;
            private String NL;
            private String CUST_LEVEL;
            private String CST_NAME;
            private String SR;

            public String getRR() {
                return RR;
            }

            public void setRR(String RR) {
                this.RR = RR;
            }

            public String getBIRTHDAY() {
                return BIRTHDAY;
            }

            public void setBIRTHDAY(String BIRTHDAY) {
                this.BIRTHDAY = BIRTHDAY;
            }

            public String getNL() {
                return NL;
            }

            public void setNL(String NL) {
                this.NL = NL;
            }

            public String getCUST_LEVEL() {
                return CUST_LEVEL;
            }

            public void setCUST_LEVEL(String CUST_LEVEL) {
                this.CUST_LEVEL = CUST_LEVEL;
            }

            public String getCST_NAME() {
                return CST_NAME;
            }

            public void setCST_NAME(String CST_NAME) {
                this.CST_NAME = CST_NAME;
            }

            public String getSR() {
                return SR;
            }

            public void setSR(String SR) {
                this.SR = SR;
            }
        }
        public static class kHSJJ{
            /**
             * RR : 1
             * LAST_LEVEL : 0
             * EVENT_TYPE : 01
             * CURRENT_LEVEL : 2
             * CST_NAME : 郭昌军
             */

            private String RR;
            private String LAST_LEVEL;
            private String EVENT_TYPE;
            private String CURRENT_LEVEL;
            private String CST_NAME;

            public String getRR() {
                return RR;
            }

            public void setRR(String RR) {
                this.RR = RR;
            }

            public String getLAST_LEVEL() {
                return LAST_LEVEL;
            }

            public void setLAST_LEVEL(String LAST_LEVEL) {
                this.LAST_LEVEL = LAST_LEVEL;
            }

            public String getEVENT_TYPE() {
                return EVENT_TYPE;
            }

            public void setEVENT_TYPE(String EVENT_TYPE) {
                this.EVENT_TYPE = EVENT_TYPE;
            }

            public String getCURRENT_LEVEL() {
                return CURRENT_LEVEL;
            }

            public void setCURRENT_LEVEL(String CURRENT_LEVEL) {
                this.CURRENT_LEVEL = CURRENT_LEVEL;
            }

            public String getCST_NAME() {
                return CST_NAME;
            }

            public void setCST_NAME(String CST_NAME) {
                this.CST_NAME = CST_NAME;
            }
        }
    }
    /**
     * 金融需求提醒
     */
    public static class FinancialNeedsInfoBean{
        public static class CK{
            private String CST_NAME;
            private String CHLTYP;
            private String APPOINT_AMOUNT;
            private String APPOINT_DATE;

            public String getCST_NAME() {
                return CST_NAME;
            }

            public void setCST_NAME(String CST_NAME) {
                this.CST_NAME = CST_NAME;
            }

            public String getCHLTYP() {
                return CHLTYP;
            }

            public void setCHLTYP(String CHLTYP) {
                this.CHLTYP = CHLTYP;
            }

            public String getAPPOINT_AMOUNT() {
                return APPOINT_AMOUNT;
            }

            public void setAPPOINT_AMOUNT(String APPOINT_AMOUNT) {
                this.APPOINT_AMOUNT = APPOINT_AMOUNT;
            }

            public String getAPPOINT_DATE() {
                return APPOINT_DATE;
            }

            public void setAPPOINT_DATE(String APPOINT_DATE) {
                this.APPOINT_DATE = APPOINT_DATE;
            }
        }
        public static class LC{
            private String CST_NAME;
            private String CHLTYP;
            private String APPOINT_AMOUNT;
            private String APPOINT_DATE;

            public String getCST_NAME() {
                return CST_NAME;
            }

            public void setCST_NAME(String CST_NAME) {
                this.CST_NAME = CST_NAME;
            }

            public String getCHLTYP() {
                return CHLTYP;
            }

            public void setCHLTYP(String CHLTYP) {
                this.CHLTYP = CHLTYP;
            }

            public String getAPPOINT_AMOUNT() {
                return APPOINT_AMOUNT;
            }

            public void setAPPOINT_AMOUNT(String APPOINT_AMOUNT) {
                this.APPOINT_AMOUNT = APPOINT_AMOUNT;
            }

            public String getAPPOINT_DATE() {
                return APPOINT_DATE;
            }

            public void setAPPOINT_DATE(String APPOINT_DATE) {
                this.APPOINT_DATE = APPOINT_DATE;
            }}
        public static class DK{
            private String CST_NAME;
            private String CHLTYP;
            private String APPOINT_AMOUNT;
            private String APPOINT_DATE;

            public String getCST_NAME() {
                return CST_NAME;
            }

            public void setCST_NAME(String CST_NAME) {
                this.CST_NAME = CST_NAME;
            }

            public String getCHLTYP() {
                return CHLTYP;
            }

            public void setCHLTYP(String CHLTYP) {
                this.CHLTYP = CHLTYP;
            }

            public String getAPPOINT_AMOUNT() {
                return APPOINT_AMOUNT;
            }

            public void setAPPOINT_AMOUNT(String APPOINT_AMOUNT) {
                this.APPOINT_AMOUNT = APPOINT_AMOUNT;
            }

            public String getAPPOINT_DATE() {
                return APPOINT_DATE;
            }

            public void setAPPOINT_DATE(String APPOINT_DATE) {
                this.APPOINT_DATE = APPOINT_DATE;
            }
        }
        public static class DZCP{
            private String CST_NAME;
            private String CHLTYP;
            private String XQLX;
            private String APPOINT_AMOUNT;
            private String APPOINT_DATE;

            public String getCST_NAME() {
                return CST_NAME;
            }

            public void setCST_NAME(String CST_NAME) {
                this.CST_NAME = CST_NAME;
            }

            public String getCHLTYP() {
                return CHLTYP;
            }

            public void setCHLTYP(String CHLTYP) {
                this.CHLTYP = CHLTYP;
            }

            public String getXQLX() {
                return XQLX;
            }

            public void setXQLX(String XQLX) {
                this.XQLX = XQLX;
            }

            public String getAPPOINT_AMOUNT() {
                return APPOINT_AMOUNT;
            }

            public void setAPPOINT_AMOUNT(String APPOINT_AMOUNT) {
                this.APPOINT_AMOUNT = APPOINT_AMOUNT;
            }

            public String getAPPOINT_DATE() {
                return APPOINT_DATE;
            }

            public void setAPPOINT_DATE(String APPOINT_DATE) {
                this.APPOINT_DATE = APPOINT_DATE;
            }
        }
    }
    /**
     * 产品到期提醒
     */
    public static class ProductInfoBean{
        public static class DQCK{
            /**
             * RR : 1
             * EVENT_BAL : 14000
             * EXPIRE_DT : 09-15
             * CST_NAME : 崔邦金
             * ACC_DEP_ACC : 81020000361149796
             */

            private String RR;
            private String EVENT_BAL;
            private String EXPIRE_DT;
            private String CST_NAME;
            private String ACC_DEP_ACC;

            public String getRR() {
                return RR;
            }

            public void setRR(String RR) {
                this.RR = RR;
            }

            public String getEVENT_BAL() {
                return EVENT_BAL;
            }

            public void setEVENT_BAL(String EVENT_BAL) {
                this.EVENT_BAL = EVENT_BAL;
            }

            public String getEXPIRE_DT() {
                return EXPIRE_DT;
            }

            public void setEXPIRE_DT(String EXPIRE_DT) {
                this.EXPIRE_DT = EXPIRE_DT;
            }

            public String getCST_NAME() {
                return CST_NAME;
            }

            public void setCST_NAME(String CST_NAME) {
                this.CST_NAME = CST_NAME;
            }

            public String getACC_DEP_ACC() {
                return ACC_DEP_ACC;
            }

            public void setACC_DEP_ACC(String ACC_DEP_ACC) {
                this.ACC_DEP_ACC = ACC_DEP_ACC;
            }
        }
        public static class LCCP{
            private String CST_NAME;
            private String PROD_NAME;
            private String EXPECTED_YIELD;
            private String EVENT_BAL;
            private String EXPIRE_DT;

            public String getCST_NAME() {
                return CST_NAME;
            }

            public void setCST_NAME(String CST_NAME) {
                this.CST_NAME = CST_NAME;
            }

            public String getPROD_NAME() {
                return PROD_NAME;
            }

            public void setPROD_NAME(String PROD_NAME) {
                this.PROD_NAME = PROD_NAME;
            }

            public String getEXPECTED_YIELD() {
                return EXPECTED_YIELD;
            }

            public void setEXPECTED_YIELD(String EXPECTED_YIELD) {
                this.EXPECTED_YIELD = EXPECTED_YIELD;
            }

            public String getEVENT_BAL() {
                return EVENT_BAL;
            }

            public void setEVENT_BAL(String EVENT_BAL) {
                this.EVENT_BAL = EVENT_BAL;
            }

            public String getEXPIRE_DT() {
                return EXPIRE_DT;
            }

            public void setEXPIRE_DT(String EXPIRE_DT) {
                this.EXPIRE_DT = EXPIRE_DT;
            }
        }
        public static class DKCP{
            /**
             * RR : 1
             * EVENT_BAL : 100000
             * LOAN_ACC : 31010000023331579
             * EXPIRE_DT : 09-18
             * PROD_NAME : 固率-农户经营贷款
             * CST_NAME : 柳会江
             */

            private String RR;
            private String EVENT_BAL;
            private String LOAN_ACC;
            private String EXPIRE_DT;
            private String PROD_NAME;
            private String CST_NAME;

            public String getRR() {
                return RR;
            }

            public void setRR(String RR) {
                this.RR = RR;
            }

            public String getEVENT_BAL() {
                return EVENT_BAL;
            }

            public void setEVENT_BAL(String EVENT_BAL) {
                this.EVENT_BAL = EVENT_BAL;
            }

            public String getLOAN_ACC() {
                return LOAN_ACC;
            }

            public void setLOAN_ACC(String LOAN_ACC) {
                this.LOAN_ACC = LOAN_ACC;
            }

            public String getEXPIRE_DT() {
                return EXPIRE_DT;
            }

            public void setEXPIRE_DT(String EXPIRE_DT) {
                this.EXPIRE_DT = EXPIRE_DT;
            }

            public String getPROD_NAME() {
                return PROD_NAME;
            }

            public void setPROD_NAME(String PROD_NAME) {
                this.PROD_NAME = PROD_NAME;
            }

            public String getCST_NAME() {
                return CST_NAME;
            }

            public void setCST_NAME(String CST_NAME) {
                this.CST_NAME = CST_NAME;
            }
        }
    }
}
