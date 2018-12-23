package com.pactera.financialmanager.credit.main.service.search;

/**
 * Created by lenovo on 2017/7/31.
 */

public class ListBusinessList {
    private String CustomerName;
    private String BusinessName;
    private String BusinessSum;
    private String Balance;
    private String OverDueBalance;
    private String InterestBalance;
    private String CustomerType;
    private String CustomerID;
    private String SerialNo;
    private String TypeNo;
    private String ContractNo;
    private String CertID;
    private String CertTypeName;

    public String getCertID() {
        return CertID;
    }

    public void setCertID(String certID) {
        CertID = certID;
    }

    public String getCertTypeName() {
        return CertTypeName;
    }

    public void setCertTypeName(String certTypeName) {
        CertTypeName = certTypeName;
    }

    public ListBusinessList(String customerType, String customerID, String serialNo, String typeNo) {
        CustomerType = customerType;
        CustomerID = customerID;
        SerialNo = serialNo;
        TypeNo = typeNo;
    }

    public ListBusinessList(String customerName, String businessName, String businessSum, String balance, String overDueBalance, String interestBalance, String customerType, String customerID, String serialNo, String typeNo, String contractNo) {
        CustomerName = customerName;
        BusinessName = businessName;
        BusinessSum = businessSum;
        Balance = balance;
        OverDueBalance = overDueBalance;
        InterestBalance = interestBalance;
        CustomerType = customerType;
        CustomerID = customerID;
        SerialNo = serialNo;
        TypeNo = typeNo;
        ContractNo = contractNo;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        CustomerName = CustomerName;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String BusinessName) {
        BusinessName = BusinessName;
    }

    public String getBusinessSum() {
        return BusinessSum;
    }

    public void setBusinessSum(String BusinessSum) {
        BusinessSum = BusinessSum;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String Balance) {
        Balance = Balance;
    }

    public String getOverDueBalance() {
        return OverDueBalance;
    }

    public void setOverDueBalance(String OverDueBalance) {
        OverDueBalance = OverDueBalance;
    }

    public String getInterestBalance() {
        return InterestBalance;
    }

    public void setInterestBalance(String InterestBalance) {
        InterestBalance = InterestBalance;
    }

    public String getCustomerType() {
        return CustomerType;
    }

    public void setCustomerType(String CustomerType) {
        CustomerType = CustomerType;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String CustomerID) {
        CustomerID = CustomerID;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String SerialNo) {
        SerialNo = SerialNo;
    }

    public String getTypeNo() {
        return TypeNo;
    }

    public void setTypeNo(String TypeNo) {
        TypeNo = TypeNo;
    }

    public String getContractNo() {
        return ContractNo;
    }

    public void setContractNo(String ContractNo) {
        ContractNo = ContractNo;
    }
}
