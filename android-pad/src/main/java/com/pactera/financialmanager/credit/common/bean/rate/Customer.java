package com.pactera.financialmanager.credit.common.bean.rate;

/**
 * Created by admin on 2017-8-10.
 */

public class Customer {
    private String CustomerType;
    private String CustomerName;
    private String CustomerID;
    private String CertID;
    public Customer(String CustomerType, String CustomerName, String CustomerID, String CertID){
        this.CustomerType = CustomerType;
        this.CustomerName = CustomerName;
        this.CustomerID = CustomerID;
        this.CertID = CertID;
    }
    public String getCustomerType(){
        return CustomerType;
    }
    public void setCustomerType(){
        CustomerType = CustomerType;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getCertID() {
        return CertID;
    }

    public void setCertID(String certID) {
        CertID = certID;
    }
}
