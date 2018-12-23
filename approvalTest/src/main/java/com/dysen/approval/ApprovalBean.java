package com.dysen.approval;

/**
 * Created by dysen on 2017/10/16.
 */

public class ApprovalBean {

    class ExamineBean{

        /**
         * ObjectNo : BA20171016000002
         * ApprovePerson : 已否决
         * PhaseName : 已否决
         * CustomerName : 田总
         * BusinessName : 农户小额担保贷款
         * BusinessSum : 1.00万元
         * ApplyPhase : 支行长
         * OrgName : 黄石农村商业银行公司部
         * FlowNo : CreditFlow2
         * FlowTaskNo : 2017101600000008
         * PhaseValue :
         * CustomerType : 040
         * ObjectType : CreditApply
         * CustomerID : 2017040100000001
         * FlowName : 授信业务审批流程(定制版)
         * BusinessType : 308050
         * Attribute10 :
         * OldFlowTaskNo : 2017101600000007
         * PhaseNo : 0060
         */

        private String ObjectNo;
        private String ApprovePerson;
        private String PhaseName;
        private String CustomerName;
        private String BusinessName;
        private String BusinessSum;
        private String ApplyPhase;
        private String OrgName;
        private String FlowNo;
        private String FlowTaskNo;
        private String PhaseValue;
        private String CustomerType;
        private String ObjectType;
        private String CustomerID;
        private String FlowName;
        private String BusinessType;
        private String Attribute10;
        private String OldFlowTaskNo;
        private String PhaseNo;

        public String getObjectNo() {
            return ObjectNo;
        }

        public void setObjectNo(String ObjectNo) {
            this.ObjectNo = ObjectNo;
        }

        public String getApprovePerson() {
            return ApprovePerson;
        }

        public void setApprovePerson(String ApprovePerson) {
            this.ApprovePerson = ApprovePerson;
        }

        public String getPhaseName() {
            return PhaseName;
        }

        public void setPhaseName(String PhaseName) {
            this.PhaseName = PhaseName;
        }

        public String getCustomerName() {
            return CustomerName;
        }

        public void setCustomerName(String CustomerName) {
            this.CustomerName = CustomerName;
        }

        public String getBusinessName() {
            return BusinessName;
        }

        public void setBusinessName(String BusinessName) {
            this.BusinessName = BusinessName;
        }

        public String getBusinessSum() {
            return BusinessSum;
        }

        public void setBusinessSum(String BusinessSum) {
            this.BusinessSum = BusinessSum;
        }

        public String getApplyPhase() {
            return ApplyPhase;
        }

        public void setApplyPhase(String ApplyPhase) {
            this.ApplyPhase = ApplyPhase;
        }

        public String getOrgName() {
            return OrgName;
        }

        public void setOrgName(String OrgName) {
            this.OrgName = OrgName;
        }

        public String getFlowNo() {
            return FlowNo;
        }

        public void setFlowNo(String FlowNo) {
            this.FlowNo = FlowNo;
        }

        public String getFlowTaskNo() {
            return FlowTaskNo;
        }

        public void setFlowTaskNo(String FlowTaskNo) {
            this.FlowTaskNo = FlowTaskNo;
        }

        public String getPhaseValue() {
            return PhaseValue;
        }

        public void setPhaseValue(String PhaseValue) {
            this.PhaseValue = PhaseValue;
        }

        public String getCustomerType() {
            return CustomerType;
        }

        public void setCustomerType(String CustomerType) {
            this.CustomerType = CustomerType;
        }

        public String getObjectType() {
            return ObjectType;
        }

        public void setObjectType(String ObjectType) {
            this.ObjectType = ObjectType;
        }

        public String getCustomerID() {
            return CustomerID;
        }

        public void setCustomerID(String CustomerID) {
            this.CustomerID = CustomerID;
        }

        public String getFlowName() {
            return FlowName;
        }

        public void setFlowName(String FlowName) {
            this.FlowName = FlowName;
        }

        public String getBusinessType() {
            return BusinessType;
        }

        public void setBusinessType(String BusinessType) {
            this.BusinessType = BusinessType;
        }

        public String getAttribute10() {
            return Attribute10;
        }

        public void setAttribute10(String Attribute10) {
            this.Attribute10 = Attribute10;
        }

        public String getOldFlowTaskNo() {
            return OldFlowTaskNo;
        }

        public void setOldFlowTaskNo(String OldFlowTaskNo) {
            this.OldFlowTaskNo = OldFlowTaskNo;
        }

        public String getPhaseNo() {
            return PhaseNo;
        }

        public void setPhaseNo(String PhaseNo) {
            this.PhaseNo = PhaseNo;
        }
    }

    class ApplyInfoBean{

        /**
         * KeyId : CustomerID
         * KeyName : 客户编号
         * Value : 2017040100000001
         */

        private String KeyId;
        private String KeyName;
        private String Value;

        public String getKeyId() {
            return KeyId;
        }

        public void setKeyId(String KeyId) {
            this.KeyId = KeyId;
        }

        public String getKeyName() {
            return KeyName;
        }

        public void setKeyName(String KeyName) {
            this.KeyName = KeyName;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String Value) {
            this.Value = Value;
        }
    }

    class GuarantyInfoBean{

        /**
         * GuarantyValue : 10000.00
         * SerialNo : 20171016000004
         * GuarantorName : 测试用户20061226000862
         * GuarantyTypeName : 保函保证
         * ContractTypeName : 一般担保合同
         */

        private String GuarantyValue;
        private String SerialNo;
        private String GuarantorName;
        private String GuarantyTypeName;
        private String ContractTypeName;

        public String getGuarantyValue() {
            return GuarantyValue;
        }

        public void setGuarantyValue(String GuarantyValue) {
            this.GuarantyValue = GuarantyValue;
        }

        public String getSerialNo() {
            return SerialNo;
        }

        public void setSerialNo(String SerialNo) {
            this.SerialNo = SerialNo;
        }

        public String getGuarantorName() {
            return GuarantorName;
        }

        public void setGuarantorName(String GuarantorName) {
            this.GuarantorName = GuarantorName;
        }

        public String getGuarantyTypeName() {
            return GuarantyTypeName;
        }

        public void setGuarantyTypeName(String GuarantyTypeName) {
            this.GuarantyTypeName = GuarantyTypeName;
        }

        public String getContractTypeName() {
            return ContractTypeName;
        }

        public void setContractTypeName(String ContractTypeName) {
            this.ContractTypeName = ContractTypeName;
        }
    }

    class RiskSignalBean{

        /**
         * ModelName : 当前客户云控名单检查
         * MessAge : 存在：被执行企业名单、频繁涉诉企业名单中，涉及负面信用记录。
         */

        private String ModelName;
        private String MessAge;

        public String getModelName() {
            return ModelName;
        }

        public void setModelName(String ModelName) {
            this.ModelName = ModelName;
        }

        public String getMessAge() {
            return MessAge;
        }

        public void setMessAge(String MessAge) {
            this.MessAge = MessAge;
        }
    }

    class OpinionInfoBean{

        /**
         * PhaseName : 客户经理
         * BeginTime : 2017/10/09 09:43:09
         * UserName : 刘健
         * Idea : 同意
         * PhaseOpinion : 321
         * EndTime : 2017/10/09 09:53:21
         * OrgName : 黄石农村商业银行公司部
         */

        private String PhaseName;
        private String BeginTime;
        private String UserName;
        private String Idea;
        private String PhaseOpinion;
        private String EndTime;
        private String OrgName;

        public String getPhaseName() {
            return PhaseName;
        }

        public void setPhaseName(String PhaseName) {
            this.PhaseName = PhaseName;
        }

        public String getBeginTime() {
            return BeginTime;
        }

        public void setBeginTime(String BeginTime) {
            this.BeginTime = BeginTime;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getIdea() {
            return Idea;
        }

        public void setIdea(String Idea) {
            this.Idea = Idea;
        }

        public String getPhaseOpinion() {
            return PhaseOpinion;
        }

        public void setPhaseOpinion(String PhaseOpinion) {
            this.PhaseOpinion = PhaseOpinion;
        }

        public String getEndTime() {
            return EndTime;
        }

        public void setEndTime(String EndTime) {
            this.EndTime = EndTime;
        }

        public String getOrgName() {
            return OrgName;
        }

        public void setOrgName(String OrgName) {
            this.OrgName = OrgName;
        }
    }

    class OpinionSubmitInfo{

        /**
         * ActionValue : 否决
         */

        private String ActionValue;

        public String getActionValue() {
            return ActionValue;
        }

        public void setActionValue(String ActionValue) {
            this.ActionValue = ActionValue;
        }
    }
}
