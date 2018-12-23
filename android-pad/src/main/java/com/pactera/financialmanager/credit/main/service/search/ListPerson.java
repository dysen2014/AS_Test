package com.pactera.financialmanager.credit.main.service.search;

/**
 * Created by lenovo on 2017/7/19.
 */

public class ListPerson{
    private String IrscreditLevel;
    private String CustomerType;
    private String CustomerId;
    private String CustomerName;
    private  String MobilePhone;
    private String CertID;
    private String CertTypeName;

    public String getCertTypeName() {
        return CertTypeName;
    }

    public void setCertTypeName(String certTypeName) {
        CertTypeName = certTypeName;
    }

    public ListPerson(String irscreditLevel, String customerType, String customerId, String customerName, String mobilePhone, String certID) {
        IrscreditLevel = irscreditLevel;
        CustomerType = customerType;
        CustomerId = customerId;
        CustomerName = customerName;
        MobilePhone = mobilePhone;
        CertID = certID;
    }

    public String getCertID() {
        return CertID;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public String getCustomerType() {
        return CustomerType;
    }

    public String getIrscreditLevel() {
        return IrscreditLevel;
    }

    public String getMobilePhone() {
        return MobilePhone;
    }

    public void setCertID(String certID) {
        CertID = certID;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public void setCustomerType(String customerType) {
        CustomerType = customerType;
    }

    public void setIrscreditLevel(String irscreditLevel) {
        IrscreditLevel = irscreditLevel;
    }

    public void setMobilePhone(String mobilePhone) {
        MobilePhone = mobilePhone;
    }
}

