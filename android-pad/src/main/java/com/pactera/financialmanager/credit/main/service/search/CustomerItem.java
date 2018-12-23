package com.pactera.financialmanager.credit.main.service.search;

/**
 * Created by lenovo on 2017/7/14.
 */

public class CustomerItem{
    public String IrscreditLevel;
    public String CustomerName;
    public String CertTypeName;
    public String MobilePhone;

    public CustomerItem(String IrscreditLevel,String CustomerName,String CertTypeName,String MobilePhone){
        this.IrscreditLevel = IrscreditLevel;
        this.CustomerName = CustomerName;
        this.CertTypeName = CertTypeName;
        this.MobilePhone = MobilePhone;
    }

    public String getIrscreditLevel() {
        return IrscreditLevel;
    }

    public CustomerItem setIrscreditLevel(String irscreditLevel) {
        IrscreditLevel = irscreditLevel;
        return this;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public CustomerItem setCustomerName(String customerName) {
        CustomerName = customerName;
        return this;
    }

    public String getCertTypeName() {
        return CertTypeName;
    }

    public CustomerItem setCertTypeName(String certTypeName) {
        CertTypeName = certTypeName;
        return this;
    }

    public String getMobilePhone() {
        return MobilePhone;
    }

    public CustomerItem setMobilePhone(String mobilePhone) {
        MobilePhone = mobilePhone;
        return this;
    }
}
