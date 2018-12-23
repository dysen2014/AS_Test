package com.pactera.financialmanager.credit.common.bean;

/**
 * Created by dysen on 2017/12/25.
 *
 * @Info
 */

public class CommonBean {

    /**
     * 提醒
     */
    public static class WarnBean{
        /**
         *  逾期贷款
         */
        public static class OverDueWarn{
            /**
             * ItemNo : 010
             * name : 金额(逾期)
             * count : 0
             */

            private String itemno;
            private String name;
            private String count;

            public String getItemno() {
                return itemno;
            }

            public OverDueWarn setItemno(String itemno) {
                this.itemno = itemno;
                return this;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }
        }
    }

    /**
     * 黑灰名单
     */
    public static class ScreeningBean {
        /**
         * CustomerName：客户名称，
         * CertId：证件号码，
         * data：录入时间和类型（例：2017-12-03进入黑名单）,
         * Remark：录入原因
         *  SpecialType:类型(01为黑名单，02为灰名单),
         SpecialName:类型名称。
         */
        String CustomerName;
        String CertID;
        String data;
        String Remark;
        String SpecialType;
        String SpecialName;
        String CustomerType;
        String CertTypeName;

        public String getCertID() {
            return CertID;
        }

        public void setCertID(String certID) {
            CertID = certID;
        }

        public String getCustomerType() {
            return CustomerType;
        }

        public void setCustomerType(String customerType) {
            CustomerType = customerType;
        }

        public String getCertTypeName() {
            return CertTypeName;
        }

        public void setCertTypeName(String certTypeName) {
            CertTypeName = certTypeName;
        }


        public ScreeningBean(String customerName, String certId, String data,
                             String remark, String specialType, String specialName) {
            CustomerName = customerName;
            CertID = certId;
            this.data = data;
            Remark = remark;
            SpecialType = specialType;
            SpecialName = specialName;
        }

        public String getSpecialType() {
            return SpecialType;
        }

        public void setSpecialType(String specialType) {
            SpecialType = specialType;
        }

        public String getSpecialName() {
            return SpecialName;
        }

        public void setSpecialName(String specialName) {
            SpecialName = specialName;
        }

        public String getCustomerName() {
            return CustomerName;
        }

        public void setCustomerName(String customerName) {
            CustomerName = customerName;
        }


        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
        }
    }

    /**
     * 关联查询
     */
    public static class RelevanceBean {
        /**
         * CustomerName:客户名称,
         * RelationName:关联人类型,
         * BusinessName:业务品种名称,
         * OverDueBalance:逾期金额,
         * Balance:贷款余额,
         * OverDueDays:逾期天数,
         * SerialNo:借据流水号,
         * CustomerID:客户ID,
         * CustomerType:客户类型,
         * ContractNo:合同流水号,
         * BusinessType:业务品种
         * RelationId
         */
        String CustomerName;
        String RelationName;
        String BusinessName;
        String OverDueBalance;
        String Balance;
        String OverDueDays;
        String SerialNo;
        String CustomerID;
        String CustomerType;
        String ContractNo;
        String BusinessType;
        String RelationId;
        String RelativeID;
        String RelationShip;
        String CertID;
        String CustoemrType;
        String CertTypeName;

        public String getRelativeID() {
            return RelativeID;
        }

        public void setRelativeID(String relativeID) {
            RelativeID = relativeID;
        }

        public String getRelationShip() {
            return RelationShip;
        }

        public void setRelationShip(String relationShip) {
            RelationShip = relationShip;
        }

        public String getCertID() {
            return CertID;
        }

        public void setCertID(String certID) {
            CertID = certID;
        }

        public String getCustoemrType() {
            return CustoemrType;
        }

        public void setCustoemrType(String custoemrType) {
            CustoemrType = custoemrType;
        }

        public String getCertTypeName() {
            return CertTypeName;
        }

        public void setCertTypeName(String certTypeName) {
            CertTypeName = certTypeName;
        }

        public RelevanceBean(String customerName, String relationNamem, String businessName,
                             String overDueBalance, String balance, String overDueDays, String serialNo,
                             String customerID, String customerType, String contractNo, String
                                     businessType, String relationId) {
            CustomerName = customerName;
            RelationName = relationNamem;
            BusinessName = businessName;
            OverDueBalance = overDueBalance;
            Balance = balance;
            OverDueDays = overDueDays;
            SerialNo = serialNo;
            CustomerID = customerID;
            CustomerType = customerType;
            ContractNo = contractNo;
            BusinessType = businessType;
            RelationId = relationId;
        }

        public String getRelationId() {
            return RelationId;
        }

        public void setRelationId(String relationId) {
            RelationId = relationId;
        }

        public String getCustomerName() {
            return CustomerName;
        }

        public void setCustomerName(String customerName) {
            CustomerName = customerName;
        }

        public String getRelationNamem() {
            return RelationName;
        }

        public void setRelationNamem(String relationNamem) {
            RelationName = relationNamem;
        }

        public String getBusinessName() {
            return BusinessName;
        }

        public void setBusinessName(String businessName) {
            BusinessName = businessName;
        }

        public String getOverDueBalance() {
            return OverDueBalance;
        }

        public void setOverDueBalance(String overDueBalance) {
            OverDueBalance = overDueBalance;
        }

        public String getBalance() {
            return Balance;
        }

        public void setBalance(String balance) {
            Balance = balance;
        }

        public String getOverDueDays() {
            return OverDueDays;
        }

        public void setOverDueDays(String overDueDays) {
            OverDueDays = overDueDays;
        }

        public String getSerialNo() {
            return SerialNo;
        }

        public void setSerialNo(String serialNo) {
            SerialNo = serialNo;
        }

        public String getCustomerID() {
            return CustomerID;
        }

        public void setCustomerID(String customerID) {
            CustomerID = customerID;
        }

        public String getCustomerType() {
            return CustomerType;
        }

        public void setCustomerType(String customerType) {
            CustomerType = customerType;
        }

        public String getContractNo() {
            return ContractNo;
        }

        public void setContractNo(String contractNo) {
            ContractNo = contractNo;
        }

        public String getBusinessType() {
            return BusinessType;
        }

        public void setBusinessType(String businessType) {
            BusinessType = businessType;
        }
    }

    /**
     * 业务品种(xin)
     */
    public class BussType {
        private String TypeName;
        private String SortNo;
        private String TypeProductML;
        private String key;
        private String value;

        public BussType(String typeName, String sortNo, String typeProductML, String key, String value) {
            TypeName = typeName;
            SortNo = sortNo;
            TypeProductML = typeProductML;
            this.key = key;
            this.value = value;
        }

        public String getTypeName() {
            return TypeName;
        }

        public void setTypeName(String typeName) {
            TypeName = typeName;
        }

        public String getSortNo() {
            return SortNo;
        }

        public void setSortNo(String sortNo) {
            SortNo = sortNo;
        }

        public String getTypeProductML() {
            return TypeProductML;
        }

        public void setTypeProductML(String typeProductML) {
            TypeProductML = typeProductML;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     *  所属网点
     */
    public static class OwnedOutlets{
    // OrgId:机构ID,OrgName:机构名称
        String OrgId, OrgName;

        public OwnedOutlets(String orgId, String orgName) {
            OrgId = orgId;
            OrgName = orgName;
        }

        public String getOrgId() {
            return OrgId;
        }

        public void setOrgId(String orgId) {
            OrgId = orgId;
        }

        public String getOrgName() {
            return OrgName;
        }

        public void setOrgName(String orgName) {
            OrgName = orgName;
        }
    }
    public class BranshesModel {

//    {
//        OrgId:机构ID,
//                OrgName:机构名称,
//    }

        private String QueryOrgId;
        private String OrgName;
        private String OrgType;

        public String getQueryOrgId() {
            return QueryOrgId;
        }

        public void setQueryOrgId(String queryOrgId) {
            QueryOrgId = queryOrgId;
        }

        public String getOrgType() {
            return OrgType;
        }

        public void setOrgType(String orgType) {
            OrgType = orgType;
        }


        public String getOrgName() {
            return OrgName;
        }

        public void setOrgName(String orgName) {
            OrgName = orgName;
        }
    }
}
